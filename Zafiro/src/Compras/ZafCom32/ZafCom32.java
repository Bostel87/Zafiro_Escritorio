/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom32.java
 *
 * Created on Aug 24, 2009, 11:34:14 AM
 */
package Compras.ZafCom32;    


import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafPerUsr.ZafPerUsr;
import java.math.BigInteger;
import javax.swing.SpinnerNumberModel;



/**
 *
 * @author ilino
 */
public class ZafCom32 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private ZafUtil objUti;


    private String strCodBodDes,  strNomBodDes;
    private ZafVenCon vcoBodDes;
    private String strSQL, strAux;
    private final int INT_TBL_DAT_HIS_LIN=0;
    private final int INT_TBL_DAT_HIS_CHK=1;
    private final int INT_TBL_DAT_HIS_ANI=2;
    private final int INT_TBL_DAT_HIS_NUM_MES=3;
    private final int INT_TBL_DAT_HIS_MES=4;
    private Vector vecCabHis, vecRegHis, vecDatHis, vecAux;
    private Vector vecCab, vecReg, vecDat;
    private ZafTblMod objTblModHis, objTblMod;
    private ZafColNumerada objColNumHis, objColNum;
    private ZafTblFilCab objTblFilCabHis, objTblFilCab;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenChk objTblCelRenChkHis;
    private ZafTblCelEdiChk objTblCelEdiChkHis;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoItm;

    private boolean blnHayCam;
    private Connection con;
    private Statement stm;
    private ResultSet rst;

    private String strCodAlt, strNomItm;

    //PARA COLUMNAS ESTATICAS DE LA IZQUIERDA
    private final int INT_TBL_DAT_LIN=0;
    private final int INT_TBL_DAT_COD_ITM=1;
    private final int INT_TBL_DAT_COD_ALT_ITM=2;
    private final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;
    private final int INT_TBL_DAT_NOM_ITM=4;
    private final int INT_TBL_DAT_UNI_MED=5;
    private final int INT_TBL_DAT_STK_ACT_CEN_DIS=6;
    private final int INT_TBL_DAT_DIS_CEN_DIS=7;
    
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafTblOrd objTblOrd;
    private ArrayList arlRegAniAdd, arlDatAniAdd;
    private final int INT_ARL_ANI=0;
    private final int INT_ARL_NUM_MES=1;
    private final int INT_ARL_MES=2;
    private final int INT_ARL_COL=3;
    private boolean blnCon;
    private ZafThreadGUI objThrGUI;
    //private int intNumColAdi=0;


    private ZafTblCelRenLbl objTblCelRenLbl;
    private String strDesCorCla, strDesLarCla;
    private ZafVenCon vcoCla;

    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiBut;

    private ZafCom32_01 objCom32_01;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafPerUsr objPerUsr;

    private int intNumColFinModEst;//la ultima columna del modelo estatico
    private int intNumColAdiFec, intNumColIniFec, intNumColFinFec;//columnas a adicionar del filtro seleccionado por mes /anio
    private int intNumColAdiTot, intNumColIniTot, intNumColFinTot;//columnas a adicionar para totales(obtenidas por filtro de mes/anio)
    private int intNumColAdiStkAct, intNumColIniStkAct, intNumColFinStkAct;//columna del stock actual
    private int intNumColAdiProMen, intNumColIniProMen, intNumColFinProMen, intColPromMan;//columna de stock minimo
    private int intNumColAdiStkMinExc, intNumColIniStkMinExc, intNumColFinStkMinExc;//columna de stock minimo
    private int intNumColAdiObs, intNumColIniObs, intNumColFinStkObs;//columna de observacion
    private int intNumColAdiRep, intNumColIniStkRep, intNumColFinStkRep;//columna de reposicion
    private int intNumColAdiCanPenRep, intNumColIniCanPenRep, intNumColFinCanPenRep;//columna de reposicion

    private ZafTblCelEdiTxt objTblCelEdiTxtPro,objTblCelEdiTxtMin;
    
    private final int INT_COD_BOD_CEN_DIS=15;
    
    
    

    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom32(ZafParSis obj) {
        try {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            vecDatHis=new Vector();
            arlDatAniAdd=new ArrayList();


        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panCon = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panConFil = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        panParRep = new javax.swing.JPanel();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        panCanItmRep = new javax.swing.JPanel();
        panOptHis = new javax.swing.JPanel();
        chkHisVta = new javax.swing.JCheckBox();
        optHisMen = new javax.swing.JRadioButton();
        optHisAnu = new javax.swing.JRadioButton();
        panFilHis = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHis = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        panFacMin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtValMin = new javax.swing.JTextField();
        txtValMax = new javax.swing.JTextField();
        panItm = new javax.swing.JPanel();
        panFilItm = new javax.swing.JPanel();
        optTodReg = new javax.swing.JRadioButton();
        optFilReg = new javax.swing.JRadioButton();
        lblCla = new javax.swing.JLabel();
        txtCodCla = new javax.swing.JTextField();
        txtDesCorCla = new javax.swing.JTextField();
        txtDesLarCla = new javax.swing.JTextField();
        butCla = new javax.swing.JButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        lblNumMesRep = new javax.swing.JLabel();
        jspNumMesRep = new javax.swing.JSpinner();
        panItmDesHas = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblCodAltItmDes = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        lblCodAltItmHas = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        chkStkMinSug = new javax.swing.JCheckBox();
        chkHidColFec = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCalRep = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panCon.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panCon.add(lblTit, java.awt.BorderLayout.PAGE_START);

        panConFil.setLayout(new java.awt.BorderLayout());

        panRep.setPreferredSize(new java.awt.Dimension(710, 210));
        panRep.setLayout(new java.awt.BorderLayout());

        panParRep.setPreferredSize(new java.awt.Dimension(344, 24));
        panParRep.setLayout(null);

        lblBodDes.setText("Bodega:");
        lblBodDes.setToolTipText("Bodega en la que se debe hacer el conteo");
        panParRep.add(lblBodDes);
        lblBodDes.setBounds(8, 6, 70, 14);

        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        panParRep.add(txtCodBodDes);
        txtCodBodDes.setBounds(70, 2, 40, 20);

        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        panParRep.add(txtNomBodDes);
        txtNomBodDes.setBounds(110, 2, 270, 20);

        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panParRep.add(butBodDes);
        butBodDes.setBounds(380, 2, 20, 20);

        panRep.add(panParRep, java.awt.BorderLayout.NORTH);

        panCanItmRep.setPreferredSize(new java.awt.Dimension(350, 100));
        panCanItmRep.setLayout(new java.awt.BorderLayout());

        panOptHis.setPreferredSize(new java.awt.Dimension(258, 48));
        panOptHis.setLayout(null);

        chkHisVta.setSelected(true);
        chkHisVta.setText("Histórico de ventas");
        chkHisVta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkHisVtaActionPerformed(evt);
            }
        });
        panOptHis.add(chkHisVta);
        chkHisVta.setBounds(3, 0, 180, 14);

        optHisMen.setText("Histórico mensual");
        optHisMen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisMenActionPerformed(evt);
            }
        });
        panOptHis.add(optHisMen);
        optHisMen.setBounds(20, 30, 180, 14);

        optHisAnu.setSelected(true);
        optHisAnu.setText("Histórico anual");
        optHisAnu.setPreferredSize(new java.awt.Dimension(109, 23));
        optHisAnu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optHisAnuActionPerformed(evt);
            }
        });
        panOptHis.add(optHisAnu);
        optHisAnu.setBounds(20, 16, 180, 14);

        panCanItmRep.add(panOptHis, java.awt.BorderLayout.NORTH);

        panFilHis.setPreferredSize(new java.awt.Dimension(452, 360));
        panFilHis.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(452, 400));

        tblHis.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblHis);

        panFilHis.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panCanItmRep.add(panFilHis, java.awt.BorderLayout.CENTER);

        panRep.add(panCanItmRep, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(null);

        panFacMin.setBorder(javax.swing.BorderFactory.createTitledBorder("Factores"));
        panFacMin.setLayout(null);

        jLabel1.setText("Mínimo:");
        panFacMin.add(jLabel1);
        jLabel1.setBounds(20, 22, 60, 14);

        jLabel2.setText("Máximo:");
        panFacMin.add(jLabel2);
        jLabel2.setBounds(20, 42, 60, 14);
        panFacMin.add(txtValMin);
        txtValMin.setBounds(70, 18, 80, 20);
        panFacMin.add(txtValMax);
        txtValMax.setBounds(70, 40, 80, 20);

        jPanel1.add(panFacMin);
        panFacMin.setBounds(100, 10, 190, 80);

        panRep.add(jPanel1, java.awt.BorderLayout.CENTER);

        panConFil.add(panRep, java.awt.BorderLayout.CENTER);

        panItm.setPreferredSize(new java.awt.Dimension(100, 172));
        panItm.setLayout(new java.awt.BorderLayout());

        panFilItm.setPreferredSize(new java.awt.Dimension(420, 100));
        panFilItm.setLayout(null);

        optTodReg.setSelected(true);
        optTodReg.setText("Todos los items");
        optTodReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodRegActionPerformed(evt);
            }
        });
        panFilItm.add(optTodReg);
        optTodReg.setBounds(8, 26, 410, 14);

        optFilReg.setText("Sólo los items que cumplan el criterio seleccionado");
        optFilReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilRegActionPerformed(evt);
            }
        });
        panFilItm.add(optFilReg);
        optFilReg.setBounds(8, 42, 410, 14);

        lblCla.setText("Clasificación:");
        lblCla.setToolTipText("Bodega en la que se debe hacer el conteo");
        panFilItm.add(lblCla);
        lblCla.setBounds(30, 60, 78, 14);
        panFilItm.add(txtCodCla);
        txtCodCla.setBounds(70, 58, 40, 20);

        txtDesCorCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorClaActionPerformed(evt);
            }
        });
        txtDesCorCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorClaFocusLost(evt);
            }
        });
        panFilItm.add(txtDesCorCla);
        txtDesCorCla.setBounds(110, 58, 80, 20);

        txtDesLarCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarClaActionPerformed(evt);
            }
        });
        txtDesLarCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarClaFocusLost(evt);
            }
        });
        panFilItm.add(txtDesLarCla);
        txtDesLarCla.setBounds(190, 58, 260, 20);

        butCla.setText("...");
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        panFilItm.add(butCla);
        butCla.setBounds(450, 58, 20, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Bodega en la que se debe hacer el conteo");
        panFilItm.add(lblItm);
        lblItm.setBounds(30, 80, 60, 16);
        panFilItm.add(txtCodItm);
        txtCodItm.setBounds(70, 78, 40, 20);

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
        panFilItm.add(txtCodAltItm);
        txtCodAltItm.setBounds(110, 78, 80, 20);

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
        panFilItm.add(txtNomItm);
        txtNomItm.setBounds(190, 78, 260, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFilItm.add(butItm);
        butItm.setBounds(450, 78, 20, 20);

        lblNumMesRep.setText("Número de meses a reponer:");
        panFilItm.add(lblNumMesRep);
        lblNumMesRep.setBounds(10, 8, 180, 14);

        jspNumMesRep.setToolTipText("Para expresar decimales usar \",\" no \".\"");
        panFilItm.add(jspNumMesRep);
        jspNumMesRep.setBounds(200, 4, 80, 20);

        panItm.add(panFilItm, java.awt.BorderLayout.NORTH);

        panItmDesHas.setPreferredSize(new java.awt.Dimension(100, 44));
        panItmDesHas.setRequestFocusEnabled(false);
        panItmDesHas.setLayout(new java.awt.BorderLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        jPanel3.setPreferredSize(new java.awt.Dimension(100, 60));
        jPanel3.setLayout(null);

        lblCodAltItmDes.setText("Desde:");
        jPanel3.add(lblCodAltItmDes);
        lblCodAltItmDes.setBounds(30, 20, 60, 14);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(300, 16, 110, 20);

        lblCodAltItmHas.setText("Hasta:");
        jPanel3.add(lblCodAltItmHas);
        lblCodAltItmHas.setBounds(240, 20, 60, 14);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        jPanel3.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(90, 16, 110, 20);

        chkStkMinSug.setText("Sólo mostrar items que tienen stock mínimo sugerido");
        jPanel3.add(chkStkMinSug);
        chkStkMinSug.setBounds(3, 38, 340, 14);

        chkHidColFec.setSelected(true);
        chkHidColFec.setText("Ocultar columnas del Histórico de ventas");
        jPanel3.add(chkHidColFec);
        chkHidColFec.setBounds(3, 54, 340, 14);

        panItmDesHas.add(jPanel3, java.awt.BorderLayout.CENTER);

        panItm.add(panItmDesHas, java.awt.BorderLayout.CENTER);

        panConFil.add(panItm, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Filtro", panConFil);

        jPanel5.setLayout(new java.awt.BorderLayout());

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

        jPanel5.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", jPanel5);

        panCon.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPie.setPreferredSize(new java.awt.Dimension(685, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCalRep.setText("Calcular reposición");
        butCalRep.setToolTipText("Cambia el número de meses a reponer");
        butCalRep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCalRepActionPerformed(evt);
            }
        });
        panBot.add(butCalRep);

        butGua.setText("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setText("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panPie.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 22));
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

        panPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panCon.add(panPie, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panCon);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        // TODO add your handling code here:
        txtCodBodDes.transferFocus();
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
        // TODO add your handling code here:
        strCodBodDes = txtCodBodDes.getText();
        txtCodBodDes.selectAll();
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)) {
            if (txtCodBodDes.getText().equals("")) {
                txtCodBodDes.setText("");
                txtNomBodDes.setText("");
            } else {
                mostrarVenConBodDes(1);
            }
        } else
            txtCodBodDes.setText(strCodBodDes);
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        if(   (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==2332)  ){
            configurarFrm();
            agregarDocLis();
        }
        else if(   (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==1662)  ){
            mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Empresas.</HTML>");
            dispose();
        }
        else if(   (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==1662)  ){
            configurarFrm();
            agregarDocLis();
        }
        else if(   (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) &&  (objParSis.getCodigoMenu()==2332)  ){
            mostrarMsgInf("<HTML>Este programa sólo se puede ejecutar a través de Grupo.</HTML>");
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameOpened

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
        // TODO add your handling code here:
        txtNomBodDes.transferFocus();
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
        // TODO add your handling code here:
        strNomBodDes = txtNomBodDes.getText();
        txtNomBodDes.selectAll();
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
            if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)) {
                if (txtNomBodDes.getText().equals("")) {
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } else {
                    mostrarVenConBodDes(2);
                }
            }
            else
                txtNomBodDes.setText(strNomBodDes);
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        // TODO add your handling code here:
        mostrarVenConBodDes(0);
    }//GEN-LAST:event_butBodDesActionPerformed

    private void optHisAnuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisAnuActionPerformed
        // TODO add your handling code here:
        if(optHisAnu.isSelected()){
            chkHisVta.setSelected(true);
            optHisMen.setSelected(false);
            cargarAnios();
        }
        else{
            optHisMen.setSelected(true);
            chkHisVta.setSelected(true);
            cargarAniosMeses();
        }
    }//GEN-LAST:event_optHisAnuActionPerformed

    private void optHisMenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optHisMenActionPerformed
        // TODO add your handling code here:
        if(optHisMen.isSelected()){
            chkHisVta.setSelected(true);
            optHisAnu.setSelected(false);
            cargarAniosMeses();
        }
        else{
            chkHisVta.setSelected(true);
            optHisAnu.setSelected(true);
            cargarAnios();
        }
    }//GEN-LAST:event_optHisMenActionPerformed

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
        optFilReg.setSelected(true);
        optTodReg.setSelected(false);
        txtCodAltItmDes.setText("");
        txtCodAltItmHas.setText("");
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
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        // TODO add your handling code here:
        mostrarVenConItm(0);
        if(txtNomItm.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        // TODO add your handling code here:
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        // TODO add your handling code here:
    // TODO add your handling code here:
        if (txtCodAltItmDes.getText().length()>0)
        {
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);

            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");

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
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void optTodRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodRegActionPerformed
        // TODO add your handling code here:
        if(optTodReg.isSelected()){
            optFilReg.setSelected(false);
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
        }
    }//GEN-LAST:event_optTodRegActionPerformed

    private void optFilRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilRegActionPerformed
        // TODO add your handling code here:
        if(optFilReg.isSelected())
            optTodReg.setSelected(false);
    }//GEN-LAST:event_optFilRegActionPerformed

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
                dispose();
            }
        }
        catch (Exception e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        objTblMod.removeAllRows();
            if (butCon.getText().equals("Consultar")){
                //objTblTotales.isActivo(false);
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else{
                blnCon=false;
            }
     

    }//GEN-LAST:event_butConActionPerformed

    private void txtDesLarClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarClaActionPerformed
        // TODO add your handling code here:
        txtDesLarCla.transferFocus();
}//GEN-LAST:event_txtDesLarClaActionPerformed

    private void txtDesLarClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarClaFocusGained
        // TODO add your handling code here:
        strDesLarCla=txtDesLarCla.getText();
        txtDesLarCla.selectAll();
}//GEN-LAST:event_txtDesLarClaFocusGained

    private void txtDesLarClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarClaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCla.getText().equalsIgnoreCase(strDesLarCla)) {
            if (txtDesLarCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCorCla.setText("");
                txtDesLarCla.setText("");
            } else {
                mostrarVenConCla(2);
            }
        } else
            txtDesLarCla.setText(strDesLarCla);
}//GEN-LAST:event_txtDesLarClaFocusLost

    private void txtDesCorClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorClaActionPerformed
        // TODO add your handling code here:
        txtDesCorCla.transferFocus();
}//GEN-LAST:event_txtDesCorClaActionPerformed

    private void txtDesCorClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorClaFocusGained
        // TODO add your handling code here:
        strDesCorCla=txtDesCorCla.getText();
        txtDesCorCla.selectAll();
}//GEN-LAST:event_txtDesCorClaFocusGained

    private void txtDesCorClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorClaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCla.getText().equalsIgnoreCase(strDesCorCla)) {
            if (txtDesCorCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCorCla.setText("");
                txtDesLarCla.setText("");
            } else {
                mostrarVenConCla(1);
            }
        } else
            txtDesCorCla.setText(strDesCorCla);
}//GEN-LAST:event_txtDesCorClaFocusLost

    private void butClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaActionPerformed
        // TODO add your handling code here:
        mostrarVenConCla(0);
}//GEN-LAST:event_butClaActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        if(guardarDatos()){
            mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
            objTblMod.removeAllRows();
            cargarReg();
        }
        else{
            mostrarMsgInf("<HTML>La información no se pudo guardar.<BR>Verifique y vuelva a intentarlo.</HTML>");
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void chkHisVtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkHisVtaActionPerformed
        // TODO add your handling code here:
        if(chkHisVta.isSelected()){
            optHisAnu.setSelected(true);
            cargarAnios();
        }
        else{
            optHisAnu.setSelected(false);
            optHisMen.setSelected(false);
            objTblModHis.removeAllRows();
        }
    }//GEN-LAST:event_chkHisVtaActionPerformed

    private void butCalRepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCalRepActionPerformed
        // TODO add your handling code here:
        cambiarNumeroMesesReponer();
        calcularMinimoMaximo();
        calcularCantidadReponer();
        //Establecer el foco en el JTable sálo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
            tabFrm.setSelectedIndex(1);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butCalRepActionPerformed

    
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
        public void run()
        {
            if (!cargarReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }


   private boolean cargarReg(){ 
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                if(isCamVal()){
                    if(chkHisVta.isSelected()){
                        if(configurarColumnasAniosMeses()){
                            if(optHisMen.isSelected())
                            {
                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())//grupo
                                {
                                    if(consultarRegAniMesGrp()){
                                        calcularColumnaTotalesAniMes();
                                         calcularPromedioMensual();
                                         calcularCantidadReponer();
                                    }
                                }
                                else //por empresas
                                {
                                    if(consultarRegAniMesEmp()){
                                            calcularColumnaTotalesAniMes();
                                            //SOLO POR EMPRESAS PORQ POR GRUPO ESTA COLUMNA DE PROMEDIO CALCULADO NO EXISTE
                                            //para cuando no hay columnas de totales, sino esta informacion se calcula en "calcularColumnaTotalesAniMes"
                                            calcularColumnaPromedioCalculado();

                                    }
                                }
                            }
                            else if(optHisAnu.isSelected()) 
                            {
                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())//grupo
                                {
                                    if(consultarRegAniGrp()){
                                        calcularColumnaTotalesAniMes();
                                        calcularPromedioAnual();
                                        calcularCantidadReponer();
                                    }
                                }
                                else //empresas
                                {
                                    if(consultarRegAniEmp()){                                        
                                        calcularColumnaTotalesAniMes();
                                        //SOLO POR EMPRESAS PORQ POR GRUPO ESTA COLUMNA DE PROMEDIO CALCULADO NO EXISTE
                                        //para cuando no hay columnas de totales, sino esta informacion se calcula en "calcularColumnaTotalesAniMes"
                                        calcularColumnaPromedioCalculado();
                                    }
                                }

                            }
                        }
                    }
                    else{
                        if(configurarColumnasSinHistorico()){
                            if(consultarRegStkActGrp()){
                            }
                        }
                    }
                }
                objTblMod.initRowsState();
                if(chkHidColFec.isSelected()){
                    System.out.println("A");
                    //FECHA
                    for(int h=intNumColIniFec; h<intNumColFinFec; h++){
                        System.out.println("B");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.addUserHiddenColumn(h, tblDat);
                    }
                    //TOTALES
                    for(int h=intNumColIniTot; h<intNumColFinTot; h++){
                        System.out.println("C");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.addUserHiddenColumn(h, tblDat);
                    }
                }
                else{
                    System.out.println("D");
                    //FECHA
                    for(int h=intNumColIniFec; h<intNumColFinFec; h++){
                        System.out.println("E");
                        objTblMod.removeUserHiddenColumn(h, tblDat);
                    }
                    //TOTALES
                    for(int h=intNumColIniTot; h<intNumColFinTot; h++){
                        System.out.println("E");
                        //Configurar JTable: Ocultar columnas del sistema.
                        objTblMod.removeUserHiddenColumn(h, tblDat);
                    }
                }
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }  







    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butCalRep;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCla;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkHidColFec;
    private javax.swing.JCheckBox chkHisVta;
    private javax.swing.JCheckBox chkStkMinSug;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspNumMesRep;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblCla;
    private javax.swing.JLabel lblCodAltItmDes;
    private javax.swing.JLabel lblCodAltItmHas;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNumMesRep;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFilReg;
    private javax.swing.JRadioButton optHisAnu;
    private javax.swing.JRadioButton optHisMen;
    private javax.swing.JRadioButton optTodReg;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCanItmRep;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panConFil;
    private javax.swing.JPanel panFacMin;
    private javax.swing.JPanel panFilHis;
    private javax.swing.JPanel panFilItm;
    private javax.swing.JPanel panItm;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panOptHis;
    private javax.swing.JPanel panParRep;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblHis;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtDesCorCla;
    private javax.swing.JTextField txtDesLarCla;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtValMax;
    private javax.swing.JTextField txtValMin;
    // End of variables declaration//GEN-END:variables


    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            objUti = new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);
            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.3.8 ");
            lblTit.setText(strAux);
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());

            txtCodItm.setVisible(false);
            txtCodItm.setEditable(false);
            txtCodItm.setEnabled(false);

            //Configurar las ZafVenCon.
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDatHis();
            configurarTblDat();
            cargarAnios();            
            configurarVenConCla();
            configurarVenConItm();

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                chkStkMinSug.setVisible(true);
                chkStkMinSug.setEnabled(true);
            }
            else{
                chkStkMinSug.setVisible(false);
                chkStkMinSug.setEnabled(false);
            }

            txtCodCla.setVisible(false);
            txtCodCla.setEditable(false);
            txtCodCla.setEnabled(false);


            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);


            if(objParSis.getCodigoUsuario()==1){
                butCon.setVisible(true);
                butGua.setVisible(true);
                butCer.setVisible(true);
            }
            else{
                if(objParSis.getCodigoMenu()==2332){//bodegas
                    if(objPerUsr.isOpcionEnabled(2333)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2334)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(2335)){//cerrar
                        butCer.setVisible(true);
                    }
                }

                if(objParSis.getCodigoMenu()==1662){//proveedores
                    if(objPerUsr.isOpcionEnabled(1663)){//consultar
                         butCon.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(1664)){//guardar
                        butGua.setVisible(true);
                    }
                    if(objPerUsr.isOpcionEnabled(1665)){//cerrar
                        butCer.setVisible(true);
                    }
                }
            }

            //jspNumMesRep.setModel(new SpinnerNumberModel(1, 1, 100, 1));
            jspNumMesRep.setModel(new SpinnerNumberModel(1, 0.25, 100, 0.1));
            
            txtValMin.setEnabled(false);
            txtValMax.setEnabled(false);
            txtValMin.setText("0.50");
            txtValMax.setText("1.00");
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                lblNumMesRep.setVisible(false);
                jspNumMesRep.setVisible(false);
                jspNumMesRep.setEnabled(false);
                panFacMin.setVisible(false);
                butCalRep.setVisible(false);
                butCalRep.setEnabled(false);
            }



        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



        /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDatHis() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatHis = new Vector();    //Almacena los datos
            vecCabHis = new Vector(5);  //Almacena las cabeceras
            vecCabHis.clear();
            vecCabHis.add(INT_TBL_DAT_HIS_LIN, "");
            vecCabHis.add(INT_TBL_DAT_HIS_CHK, "");
            vecCabHis.add(INT_TBL_DAT_HIS_ANI, "Año");
            vecCabHis.add(INT_TBL_DAT_HIS_NUM_MES, "#");
            vecCabHis.add(INT_TBL_DAT_HIS_MES, "Mes");

            objTblModHis = new ZafTblMod();
            objTblModHis.setHeader(vecCabHis);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblHis.setModel(objTblModHis);
            //Configurar JTable: Establecer tipo de seleccián.
            tblHis.setRowSelectionAllowed(true);
            tblHis.getTableHeader().setReorderingAllowed(false);
            tblHis.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumHis = new ZafColNumerada(tblHis, INT_TBL_DAT_HIS_LIN);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblHis.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblHis.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setPreferredWidth(120);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_ANI).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_NUM_MES).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_HIS_MES).setResizable(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabHis = new ZafTblFilCab(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_LIN).setCellRenderer(objTblFilCabHis);

            vecAux=new Vector();
            objTblModHis.setModoOperacion(objTblModHis.INT_TBL_EDI);
            vecAux.add("" + INT_TBL_DAT_HIS_CHK);
            objTblModHis.setColumnasEditables(vecAux);
            vecAux=null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblHis);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkHis=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellRenderer(objTblCelRenChkHis);
            objTblCelRenChkHis=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkHis=new ZafTblCelEdiChk(tblHis);
            tcmAux.getColumn(INT_TBL_DAT_HIS_CHK).setCellEditor(objTblCelEdiChkHis);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblModHis.addSystemHiddenColumn(INT_TBL_DAT_HIS_NUM_MES, tblHis);



            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


        /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cod.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "Cod.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Nombre");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            if(objParSis.getCodigoMenu()==2332){//bodegas
                vecCab.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "Stk.Cen.Dis.");
                vecCab.add(INT_TBL_DAT_DIS_CEN_DIS, "Dis.Cen.Dis.");
                
            }    
                
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //  objTblPopMnu.setPegarEnabled(true);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            if(objParSis.getCodigoMenu()==2332){//bodegas
                tcmAux.getColumn(INT_TBL_DAT_STK_ACT_CEN_DIS).setPreferredWidth(70);
                tcmAux.getColumn(INT_TBL_DAT_DIS_CEN_DIS).setPreferredWidth(70);
                
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tcmAux.getColumn(INT_TBL_DAT_STK_ACT_CEN_DIS).setCellRenderer(objTblCelRenLbl);
                tcmAux.getColumn(INT_TBL_DAT_DIS_CEN_DIS).setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
            }            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);


            //Libero los objetos auxiliares.
            tcmAux = null;

            intNumColFinModEst=objTblMod.getColumnCount();

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);




        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txtCodItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItm.getDocument().addDocumentListener(objDocLis);
        txtNomItm.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmDes.getDocument().addDocumentListener(objDocLis);
        txtCodAltItmHas.getDocument().addDocumentListener(objDocLis);
    }


    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
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

                vcoBodDes = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
                vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

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
    private boolean mostrarVenConBodDes(int intTipBus) {
        boolean blnRes = true;
        try {
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.show();
                        if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBodDes.buscar("a1.co_bod", txtCodBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(0);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtCodBodDes.setText(strCodBodDes);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBodDes.buscar("a1.tx_nom", txtNomBodDes.getText())) {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        } else {
                            vcoBodDes.setCampoBusqueda(1);
                            vcoBodDes.setCriterio1(11);
                            vcoBodDes.cargarDatos();
                            vcoBodDes.show();
                            if (vcoBodDes.getSelectedButton() == vcoBodDes.INT_BUT_ACE) {
                                txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                                txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                            } else {
                                txtNomBodDes.setText(strNomBodDes);
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

    
    private boolean cargarAnios(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani";
                strSQL+=" FROM tbm_venMenInvBod";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" GROUP BY co_emp, ne_ani";
                strSQL+=" ORDER BY ne_ani";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecRegHis=new Vector();
                    vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                    vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                    vecDatHis.add(vecRegHis);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModHis.setData(vecDatHis);
                tblHis.setModel(objTblModHis);
                vecDatHis.clear();
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    

    private boolean cargarAniosMeses(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani, ne_mes";
                strSQL+=" FROM tbm_venMenInvBod";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" GROUP BY co_emp, ne_ani, ne_mes";
                strSQL+=" ORDER BY ne_ani, ne_mes";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecRegHis=new Vector();
                    vecRegHis.add(INT_TBL_DAT_HIS_LIN,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_CHK,"");
                    vecRegHis.add(INT_TBL_DAT_HIS_ANI,"" + rst.getString("ne_ani"));
                    vecRegHis.add(INT_TBL_DAT_HIS_NUM_MES, "" + rst.getInt("ne_mes"));
                    switch(rst.getInt("ne_mes")){
                        case 1:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Enero");
                            break;
                        case 2:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Febrero");
                            break;
                        case 3:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Marzo");
                            break;
                        case 4:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Abril");
                            break;
                        case 5:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Mayo");
                            break;
                        case 6:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Junio");
                            break;
                        case 7:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Julio");
                            break;
                        case 8:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Agosto");
                            break;
                        case 9:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Septiembre");
                            break;
                        case 10:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Octubre");
                            break;
                        case 11:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Noviembre");
                            break;
                        case 12:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"Diciembre");
                            break;
                        default:
                            vecRegHis.add(INT_TBL_DAT_HIS_MES,"");
                            break;

                    }
                    vecDatHis.add(vecRegHis);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModHis.setData(vecDatHis);
                tblHis.setModel(objTblModHis);
                vecDatHis.clear();
            }
        }
        catch (Exception e){
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
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
            if(txtCodCla.getText().equals(""))
                strSQL+=" LEFT OUTER JOIN";
            else
                strSQL+=" INNER JOIN";
            strSQL+=" tbr_invCla AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            
            strSQL+="   AND a1.st_reg NOT IN('U','T') AND a1.st_ser NOT IN('S','T') ";
            
            if( ! txtCodCla.getText().equals("")){
                strSQL+=" AND a3.co_cla=" + txtCodCla.getText() + "";
                strSQL+=" AND a3.st_reg IN('A','P')";
            }
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" AND (a1.tx_codAlt LIKE '%I' OR a1.tx_codAlt LIKE '%S')";
  
            }
            else
                strSQL+=" AND (a1.tx_codAlt LIKE '%S' OR a1.tx_codAlt LIKE '%L')";
            strSQL+=" GROUP BY a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a2.tx_desLar";
            strSQL+=" ORDER BY a1.tx_codAlt";
            System.out.println("configurarVenConItm: " + strSQL);
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
        String strFilItm="";
        try{
                if(configurarVenConItm()){
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




        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



   private boolean configurarColumnasAniosMeses(){
       boolean blnRes=true;
       try{
           if(con!=null){
               if(eliminaColumnasAdicionadasAnios()){
                   intNumColAdiFec=numeroColumnasAdicionar();
                   if(optHisAnu.isSelected()){
                       if(agregarColumnasAdicionadasAnios(intNumColAdiFec)){
                       }
                   }
                   else if(optHisMen.isSelected()){
                       if(agregarColumnasAdicionadasAniosMeses(intNumColAdiFec)){
                       }
                   }
                   if(agregarColumnasAdicionadasTotales()){
                   }
                   if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo_Exceso()){
                       }
                       if(agregarColumnaAdicionadaObservacion()){
                       }
                       if(agregarColumnaAdicionadaReposicion()){                           
                       }
                       if(agregarColAdiCanPenRep()){
                       }
                   }
                   else{//POR EMPRESA
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo()){
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


   private boolean configurarColumnasSinHistorico(){
       boolean blnRes=true;
       intNumColAdiFec=0;
       try{
           if(con!=null){
               if(eliminaColumnasAdicionadasAnios()){
                   if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnaAdicionadaObservacion()){
                       }
                   }
                   else{//POR EMPRESA
                       if(agregarColumnaStockActual()){
                       }
                       if(agregarColumnasAdicionadasPromedioMensualVtas()){
                       }
                       if(agregarColumnaStockMinimo()){
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





   private boolean eliminaColumnasAdicionadasAnios(){
        boolean blnRes=true;
        int intNumColTot=tblDat.getColumnCount();//obtiene el numero total de columnas en ese momento
        try{
            for (int i=(intNumColTot-1); i>=intNumColFinModEst; i--){
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


   private int numeroColumnasAdicionar(){
       String strLin;
       int intNumColAdd=0;
       arlDatAniAdd.clear();
       try{
           for(int i=0; i<objTblModHis.getRowCountTrue(); i++){
               strLin=objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_LIN).toString();
               if(strLin.equals("M")){
                   if(objTblModHis.isChecked(i, INT_TBL_DAT_HIS_CHK)){
                       intNumColAdd++;
                       arlRegAniAdd=new ArrayList();
                       arlRegAniAdd.add(INT_ARL_ANI, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_ANI));
                       arlRegAniAdd.add(INT_ARL_NUM_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_NUM_MES).toString());
                       arlRegAniAdd.add(INT_ARL_MES, "" + objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES)==null?"":objTblModHis.getValueAt(i, INT_TBL_DAT_HIS_MES).toString());
                       arlRegAniAdd.add(INT_ARL_COL, "" );
                       arlDatAniAdd.add(arlRegAniAdd);
                   }
               }
           }
           System.out.println("ARREGLO TIENE: " + arlDatAniAdd.toString());
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intNumColAdd;
   }



    private boolean agregarColumnasAdicionadasAnios(int numeroColumnasAdicionar){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAdiFec=numeroColumnasAdicionar;
        String strAni="", strMes="";
        int p=0, f=-1;
        intNumColIniFec=intNumColFinModEst;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<(intNumColAdiFec*3); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Unidades":(p==1?"Veces":(p==2?"Meses":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniFec+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                }
                objTblHeaColGrp.add(tbc);

                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);

                if(p==2){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLbl=null;
            intNumColFinFec=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }






    private boolean agregarColumnasAdicionadasAniosMeses(int numeroColumnasAdicionar){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        ZafTblHeaColGrp objTblHeaColSubGrp=null;
        intNumColAdiFec=numeroColumnasAdicionar;
        String strAni="", strMes="";
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        int p=0, f=-1, d=9999;
        intNumColIniFec=intNumColFinModEst;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<(intNumColAdiFec*3); i++){
                if(p==0)
                    f++;
                String strSubTit=(p==0?"Unidades":(p==1?"Veces":(p==2?"Meses":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniFec+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);

                if (!strAni.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString())){
                    objTblHeaColGrp=new ZafTblHeaColGrp("" + objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI));
                    objTblHeaColGrp.setHeight(16);
                    strAni=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_ANI).toString();
                    d=0;
                }
                else
                    d++;

                if (!strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString())) {
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                else if (     (strMes.equals(objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString()))  && d==0  ){
                    objTblHeaColSubGrp=new ZafTblHeaColGrp("" +  objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_MES));
                    objTblHeaColSubGrp.setHeight(16);
                    strMes=objUti.getStringValueAt(arlDatAniAdd, f, INT_ARL_NUM_MES).toString();
                    objTblHeaColGrp.add(objTblHeaColSubGrp);
                }
                
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
                objTblHeaColSubGrp.add(tcmAux.getColumn((tblDat.getColumnCount()-1)));

                if(p==2){
                    p=0;
                }
                else{
                    p++;
                }
            }
            objTblCelRenLbl=null;
            intNumColFinFec=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }




   private boolean agregarColumnasAdicionadasTotales(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAdiTot=3;
        intNumColIniTot=intNumColFinFec;

        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            for (int i=0; i<intNumColAdiTot; i++){
                String strSubTit=(i==0?"Unidades":(i==1?"Veces":(i==2?"Meses":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniTot+i);
                tbc.setHeaderValue("" + strSubTit);

                

                if(intNumColAdiFec>1){
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                }
                else{
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);
                    tbc.setResizable(false);
                }                



                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Totales");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinTot=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;

   }


   
   private boolean agregarColumnaStockActual(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkAct=2;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkAct=intNumColFinTot;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxt=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkAct; i++){
                String strSubTit=((i==0?"Stk.Act.":(i==1?"Dis.Act.":"")));
                tbc=new javax.swing.table.TableColumn(intNumColIniStkAct+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkAct=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }


   private boolean agregarColumnaStockMinimo_Exceso(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkMinExc=4;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkMinExc=intNumColFinProMen;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMin=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkMinExc; i++){
                String strSubTit=(i==0?"Fijo":(i==1?"Exceso":(i==2)?"Stk.Min.Sug.":"Obs.Stk.Min.Sug."));
                tbc=new javax.swing.table.TableColumn(intNumColIniStkMinExc+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtMin);
                objTblCelEdiTxtMin.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    String strValCel="";
                    int intFil=-1;
                    int intCol=-1;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFil=tblDat.getSelectedRow();
                        intCol=tblDat.getSelectedColumn();
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if((intNumColIniStkMinExc+3)==tblDat.getSelectedColumn()){//es la columna de observacion
                        }
                        else{
                            strValCel=objTblMod.getValueAt(intFil, intCol)==null?"":objTblMod.getValueAt(intFil, intCol).toString();
                            if(!strValCel.equals("")){
                                //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                                bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                                objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                                calcularMinimoMaximo();
                                calcularCantidadReponer();

                            }
                            else{
                                 objTblMod.setValueAt("", intFil, intCol);
                                 calcularMinimoMaximo();
                                 calcularCantidadReponer();
                            }
                        }

                    }

                });



                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkMinExc=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }




   private boolean agregarColumnaStockMinimo(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiStkMinExc=1;
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        intNumColIniStkMinExc=intNumColFinProMen;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMin=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiStkMinExc; i++){
                String strSubTit=(i==0?"Stk.Min.":"");
                tbc=new javax.swing.table.TableColumn(intNumColIniStkMinExc+i);
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtMin);


                objTblCelEdiTxtMin.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

                    BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                        if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){





                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }
                        else{
                            objTblMod.setValueAt(null, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                        }

                    }

                });



                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinStkMinExc=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }





   private boolean agregarColumnaAdicionadaObservacion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiObs=1;
        intNumColIniObs=intNumColFinStkMinExc;
        try{
            String strSubTit="Obs.Stk.Min.Sug";
            tbc=new javax.swing.table.TableColumn(intNumColIniObs);
            tbc.setHeaderValue("" + strSubTit);
            //Configurar JTable: Establecer el ancho de la columna.
            tbc.setPreferredWidth(30);
            objTblCelRenBut=new ZafTblCelRenBut();
            tbc.setCellRenderer(objTblCelRenBut);
            objCom32_01=new ZafCom32_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiBut= new ZafTblCelEdiButDlg(objCom32_01);
            tbc.setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCom32_01.setContenido(""+objTblMod.getValueAt(tblDat.getSelectedRow(), (tblDat.getSelectedColumn()-1)).toString());
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objCom32_01.getContenido(), tblDat.getSelectedRow(), (tblDat.getSelectedColumn()-1));
                }
            });
            objTblMod.addColumn(tblDat, tbc);
            objTblCelRenBut=null;
            intNumColFinStkObs=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
   }






   private boolean agregarColumnasAdicionadasPromedioMensualVtas()
   {
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        strAux="";
        intNumColAdiProMen=2;
        intNumColIniProMen=intNumColFinStkAct;
        intColPromMan=intNumColIniProMen+1;
        
        try
        {
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPro=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiProMen; i++)
            {
                String strSubTit=(i==0?"Pro.Cal.":(i==1?"Pro.Man.":""));
                tbc=new javax.swing.table.TableColumn(intNumColIniProMen+i); 
                tbc.setHeaderValue("" + strSubTit);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(50);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxtPro);

                //<editor-fold defaultstate="collapsed" desc="/* Opción Copiar y Pegar Pro.Man. */">
                objTblMod.setColumnDataType(intNumColIniStkMinExc, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 1, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 2, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(intNumColIniStkMinExc + 5, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                
                objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
                {
                    public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                    {
                        if (objTblPopMnu.isClickTipoSeleccionSeleccionCelda() ) 
                        //if (objTblPopMnu.isClickTipoSeleccionSeleccionCelda() && tblDat.isColumnSelected(intColPromMan)) 
                        {
                            System.out.println(" HABILITAR TIPO CELDA:" + intColPromMan);
                            objTblPopMnu.setPegarEnabled(true);
                            System.out.println(" HABILITAR PEGAR:" + intColPromMan);
                        }                
                        if (objTblPopMnu.isClickPegar()) 
                        {
                            System.out.println(" CLICK PEGAR:" + intColPromMan); //Pro.Man.
                            int intFilSel[], i;
                            String strFij, strStkExc, strStkMin, strObsStk, strNumMes;
                            intFilSel = tblDat.getSelectedRows();

                            for (i = 0; i < intFilSel.length; i++) 
                            {
                                strFij = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc).toString();
                                strStkExc = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 1).toString();
                                strStkMin = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 2).toString();
                                strObsStk = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 3).toString();
                                strNumMes = objTblMod.getValueAt(intFilSel[i], intNumColIniStkMinExc + 5).toString();

                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc); //Fijo
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 1); //Exceso
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 2); //Stk.Min.
                                objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 3); //Obs.Stk.
                                //objTblMod.setValueAt(null, intFilSel[i], intNumColIniStkMinExc + 5); //Num.Mes.

                                System.out.println("PEGAR=> Fijo.:" + strFij + " Stk.Exc.:" + strStkExc + " Stk.Min.:" + strStkMin + " Obs.Stk.:" + strObsStk + " Num.Mes.:" + strNumMes);
                            }
                        }
             
                    }
                }
                );
                //</editor-fold>
                
                objTblCelEdiTxtPro.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                BigDecimal bdeVal=new BigDecimal(BigInteger.ZERO);
                    String strValCel="";
                    int intFil=-1;
                    int intCol=-1;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFil=tblDat.getSelectedRow();
                        intCol=tblDat.getSelectedColumn();
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) { 
                        strValCel=objTblMod.getValueAt(intFil, intCol)==null?"":objTblMod.getValueAt(intFil, intCol).toString();
                        if(!strValCel.equals("")){
                            //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
                            bdeVal=  objUti.redondearBigDecimal( (objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString()), Integer.parseInt("0") );
                            objTblMod.setValueAt(""+bdeVal, tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                calcularMinimoMaximo();
                                calcularCantidadReponer();
                            }
                        }
                        else{
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), tblDat.getSelectedColumn());
                            calcularMinimoMaximo();
                            calcularCantidadReponer();
                        }



                    }
                });


                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Pro.Men.Vta.");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinProMen=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }



   private boolean agregarColumnaAdicionadaReposicion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrp=null;
        intNumColAdiRep=5;
        intNumColIniStkRep=intNumColFinStkObs;
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxt=new ZafTblCelEdiTxt();
            for (int i=0; i<intNumColAdiRep; i++){
                String strSubTit=(i==0?"Núm,Mes.Rep.":(i==1?"Mínimo":(i==2?"Máximo":(i==3?"Can.Rep.":"Val.Uti.Cal.Min.Max"))));
                tbc=new javax.swing.table.TableColumn(intNumColIniStkRep+i);
                tbc.setHeaderValue("" + strSubTit);
                if(i==4){
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);
                    tbc.setResizable(false);
                }
                else{
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(60);
                }
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);
                tbc.setCellEditor(objTblCelEdiTxt);

                objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if(intNumColIniStkRep==tblDat.getSelectedColumn()){//es la columna de observacion
                            calcularMinimoMaximo();
                            calcularCantidadReponer();
                        }
                    }
                });



                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0){
                    objTblHeaColGrp=new ZafTblHeaColGrp("Reposición");
                    objTblHeaColGrp.setHeight(16);
                }
                objTblHeaColGrp.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrp);
            }
            objTblCelRenLbl=null;
            intNumColFinStkRep=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }

   
   
   private boolean agregarColAdiCanPenRep(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiCanPenRep=2;
        intNumColIniCanPenRep=intNumColFinStkRep;
        
        try{
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            for (int i=0; i<intNumColAdiCanPenRep; i++){
                String strSubTit=(i==0?"Can.Pen.Rep.":"Can.Dis.Rep.");
                tbc=new javax.swing.table.TableColumn(intNumColIniCanPenRep+i);
                tbc.setHeaderValue("" + strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
//                if(i==0){
//                    tbc.setWidth(0);
//                    tbc.setMaxWidth(0);
//                    tbc.setMinWidth(0);
//                    tbc.setPreferredWidth(0);
//                    tbc.setResizable(false);
//                }
//                else{
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(70);
//                }                
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLbl);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intNumColFinCanPenRep=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
   }
   
   
   
   
   
   

    private boolean consultarRegAniMesGrp(){
        boolean blnRes=true;
        int intAni=0;
        int intMes=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        BigDecimal bdeStkActCal=new BigDecimal("0");
        BigDecimal bdeRepMaxCal=new BigDecimal("0");
        BigDecimal bdeRepMinCal=new BigDecimal("0");
        BigDecimal bdeStkExcCal=new BigDecimal("0");
        
        BigDecimal bdeStkDspBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMaxBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMinBodDstCal=new BigDecimal("0");
        BigDecimal bdeStkExcBodDstCal=new BigDecimal("0");
        BigDecimal bdeExiCanDspRepCal=new BigDecimal("0");
        try{
            if(con!=null){
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    strAux+="		LEFT OUTER JOIN(";
                    
                    
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + " AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     AND a4.ne_mes=" + intMes + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes";
                    strAux+="                     ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani, a4.ne_mes";
                    strAux+="               ORDER BY a4.co_itm, a4.ne_mes";
                    
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes" ;
                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T') ";
                strAux+="  AND (b1.tx_codAlt LIKE '%I' OR b1.tx_codAlt LIKE '%S')";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="           SELECT  b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_stkActCenDis, b1.nd_canDisCenDis";
                strSQL+="            " + strCamSel + ", 0, 0, 0, b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, b1.nd_stkExc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_nummesrep, b1.nd_repmin, b1.nd_repmax";
                strSQL+="            , b1.nd_stkExcBodCenDis, b1.nd_repMinBodCenDis, b1.nd_repMaxBodCenDis, b1.co_emp";
                strSQL+="           FROM(";
                strSQL+=" 		              SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a1.st_reg, a1.st_ser, z1.co_bod, a3.nd_stkMin";
                strSQL+=" 		              , a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_proMenVtaMan, z2.nd_stkActCenDis, z2.nd_canDisCenDis, z1.nd_stkAct, z1.nd_canDis, a3.nd_nummesrep";
                strSQL+=" 			      , a3.nd_stkexc, a3.nd_repmin, a3.nd_repmax, e3.nd_stkexc AS nd_stkExcBodCenDis, e3.nd_repmin AS nd_repMinBodCenDis, e3.nd_repmax AS nd_repMaxBodCenDis";
                strSQL+=" 		              FROM (     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2                 ON a1.co_uni=a2.co_reg)";
                strSQL+=" 					      INNER JOIN tbm_equInv AS x1 ON a1.co_emp=x1.co_emp AND a1.co_itm=x1.co_itm";
                strSQL+=" 					      INNER JOIN (";
                strSQL+=" 						      SELECT z.co_empGrp AS co_emp, z.co_bodGrp AS co_bod, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct, SUM(z.nd_canDis) AS nd_canDis";
                strSQL+=" 						      FROM(";
                strSQL+="                                                               SELECT x.*, y.co_empGrp, y.co_bodGrp FROM(";
                strSQL+="                                                                   SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae, a1.nd_canDis";                
                strSQL+="                                                                   FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+="                                                                   ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+="                                                               ) AS x";
                strSQL+="                                                               INNER JOIN(";
                strSQL+="                                                                   SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+="                                                                   FROM tbr_bodEmpBodGrp";
                strSQL+="                                                                   WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                                   AND co_bodGrp=" + txtCodBodDes.getText() + "";
                strSQL+="                                                                   ORDER BY co_emp";
                strSQL+="                                                               ) AS y";
                strSQL+="                                                               ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" 						      ) AS z";
                strSQL+=" 						      GROUP BY z.co_empGrp, z.co_bodGrp, z.co_itmMae";
                strSQL+=" 					      ) AS z1";
                strSQL+=" 					      ON x1.co_emp=z1.co_emp AND x1.co_itmMae=z1.co_itmMae";                
                //para saber el stock en inmaconsa
                strSQL+=" 					      INNER JOIN (";
                strSQL+=" 						      SELECT z.co_empGrp AS co_emp, z.co_bodGrp AS co_bod, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkActCenDis, SUM(z.nd_canDis) AS nd_canDisCenDis";
                strSQL+=" 						      FROM(";
                strSQL+=" 							   SELECT x.*, y.co_empGrp, y.co_bodGrp FROM(";
                strSQL+=" 							      SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae, a1.nd_canDis";
                strSQL+=" 							      FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+=" 							      ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 							      ) AS x";
                strSQL+=" 							      INNER JOIN(";
                strSQL+=" 							      SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+=" 							      FROM tbr_bodEmpBodGrp";
                strSQL+=" 							      WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 							      AND co_bodGrp=" + INT_COD_BOD_CEN_DIS + "";//INT_COD_BOD_CEN_DIS = Inmaconsa
                strSQL+=" 							      ORDER BY co_emp";
                strSQL+=" 							      ) AS y";
                strSQL+=" 							      ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" 						      ) AS z";
                strSQL+=" 						      GROUP BY z.co_empGrp, z.co_bodGrp, z.co_itmMae";
                strSQL+=" 					      ) AS z2";
                strSQL+=" 					      ON x1.co_emp=z2.co_emp AND x1.co_itmMae=z2.co_itmMae";
                //fin                
                strSQL+=" 				)";
                strSQL+=" 				INNER JOIN tbm_invBod AS a3";
                strSQL+=" 				ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm  ";
                strSQL+=" 		                AND a3.co_bod=" + txtCodBodDes.getText() + "";
                
                strSQL+=" 				INNER JOIN tbm_invBod AS e3";
                strSQL+=" 				ON a1.co_emp=e3.co_emp AND a1.co_itm=e3.co_itm  ";
                strSQL+=" 		                AND e3.co_bod=" + INT_COD_BOD_CEN_DIS + "";
                
                
                
                
                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               AND e3.co_bod=" + INT_COD_BOD_CEN_DIS + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";
                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }
                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                
                
                System.out.println("consultarRegAniMesGrp: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "" + rst.getString("nd_stkActCenDis"));
                        vecReg.add(INT_TBL_DAT_DIS_CEN_DIS,     "" + rst.getString("nd_canDisCenDis"));                        
                       

                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     rst.getObject("nd_proMenVtaMan")==null?"":rst.getString("nd_proMenVtaMan")); 
                            else
                                vecReg.add(j,     "");
                        }


                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     rst.getObject(j)==null?"": rst.getString(j));
                        }

                        //observacion - boton
                        vecReg.add(intNumColIniObs,     "");

                        //reposicion
                        for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                            if(j==intNumColIniStkRep)
                                vecReg.add(j,     rst.getObject("nd_nummesrep")==null?"":rst.getObject("nd_nummesrep"));
                            else if(j==(intNumColIniStkRep+1))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+2))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else
                                vecReg.add(j,     "");
                        }
                        //Bodega destino                        
                        bdeStkActCal=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                        bdeRepMaxCal=new BigDecimal(rst.getObject("nd_repMax")==null?"0":(rst.getString("nd_repMax").equals("")?"0":rst.getString("nd_repMax")));
                        bdeRepMinCal=new BigDecimal(rst.getObject("nd_repMin")==null?"0":(rst.getString("nd_repMin").equals("")?"0":rst.getString("nd_repMin")));
                        bdeStkExcCal=new BigDecimal(rst.getObject("nd_stkExc")==null?"0":(rst.getString("nd_stkExc").equals("")?"0":rst.getString("nd_stkExc")));
                        
                        //bodega centro distribucion
                        bdeStkDspBodDstCal=new BigDecimal(rst.getObject("nd_stkActCenDis")==null?"0":(rst.getString("nd_stkActCenDis").equals("")?"0":rst.getString("nd_stkActCenDis")));
                        bdeRepMaxBodDstCal=new BigDecimal(rst.getObject("nd_repMaxBodCenDis")==null?"0":(rst.getString("nd_repMaxBodCenDis").equals("")?"0":rst.getString("nd_repMaxBodCenDis")));
                        bdeStkExcBodDstCal=new BigDecimal(rst.getObject("nd_stkExcBodCenDis")==null?"0":(rst.getString("nd_stkExcBodCenDis").equals("")?"0":rst.getString("nd_stkExcBodCenDis")));
                        bdeRepMinBodDstCal=new BigDecimal(rst.getObject("nd_repMinBodCenDis")==null?"0":(rst.getString("nd_repMinBodCenDis").equals("")?"0":rst.getString("nd_repMinBodCenDis")));
                        
                        //bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMaxBodDstCal.add(bdeStkExcBodDstCal)));
                        bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMinBodDstCal.add(bdeStkExcBodDstCal)));
                        
                        //cantidad pendiente de reponer
                        for(int j=intNumColIniCanPenRep; j<intNumColFinCanPenRep;j++){
                            if((bdeRepMinCal.subtract(bdeStkActCal)).compareTo(new BigDecimal("0"))>0){
                                if(j==intNumColIniCanPenRep)
                                    vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                else{
                                    if(bdeExiCanDspRepCal.compareTo(new BigDecimal("0"))>0){
                                        if(bdeExiCanDspRepCal.compareTo((bdeRepMaxCal.subtract(bdeStkActCal)))>0)
                                            vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                        else
                                            vecReg.add(j,     bdeExiCanDspRepCal);
                                    }
                                    else
                                        vecReg.add(j,     "0");
                                }//can.dis.rep
                            }
                            else
                                vecReg.add(j,     "0");
                        }
                        
                        
                        
                        

                        vecDat.add(vecReg);
                      
                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                //para hacer editables las columnas
                if(objParSis.getCodigoUsuario()==1){
                    //minimo, ecexso, sugerido, observacion
                    for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                        vecAux.add("" + j);
                    }
                    //boton de observacion
                    vecAux.add(""+intNumColIniObs);
                    //promedio manual
                    for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                        if(j==(intNumColIniProMen+1))
                            vecAux.add("" + j);
                    }
                    for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                        if(j==intNumColIniStkRep)
                            vecAux.add("" + j);
                    }
                }
                else{
                    if(objParSis.getCodigoMenu()==2332){//bodegas
                        if(objPerUsr.isOpcionEnabled(2334)){//guardar
                            //minimo, ecexso, sugerido, observacion
                            for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                                vecAux.add("" + j);
                            }
                            //boton de observacion
                            vecAux.add(""+intNumColIniObs);
                            //promedio manual
                            for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                                if(j==(intNumColIniProMen+1))
                                    vecAux.add("" + j);
                            }
                            for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                                if(j==intNumColIniStkRep)
                                    vecAux.add("" + j);
                            }
                        }
                    }
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                //Asignar vectores al modelo.
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }



    private boolean consultarRegAniMesEmp(){
        boolean blnRes=true;
        int intAni=0;
        int intMes=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intNumColAdiFin=0;        
        try{
            if(con!=null){
                if(intNumColAdiFec>1)
                    intNumColAdiFin=6;
                else
                    intNumColAdiFin=3;
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    intMes=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_NUM_MES);
                    strAux+="		LEFT OUTER JOIN(";
                    
                    
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + "  AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani, a4.ne_mes";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     AND a4.ne_mes=" + intMes + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani, a4.ne_mes";
                    strAux+="                     ORDER BY a4.co_itm, a4.ne_mes";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani, a4.ne_mes";
                    strAux+="               ORDER BY a4.co_itm, a4.ne_mes";
                    
                    
                    
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , b" + (j+2) + ".nd_uniVen AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numVec AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , b" + (j+2) + ".ne_numMes AS " + "b" + (j+2) + "_nenumMes" ;

                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T','O') AND (b1.tx_codAlt LIKE '%S' OR b1.tx_codAlt LIKE '%L')";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor";
                strSQL+="            " + strCamSel + ", 0, 0, 0 , b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2,  a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_promenvtaman, a3.nd_stkAct, a3.nd_canDis";
                strSQL+=" 		FROM     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";

                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }

                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";


                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }

                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println(" consultarRegAniMesEmp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                

                
                
                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        
                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     "" + (rst.getObject("nd_proMenVtaMan")==null?"0":(rst.getString("nd_proMenVtaMan").equals("")?"0":rst.getString("nd_proMenVtaMan")))); 
                            else
                                vecReg.add(j,     "0");
                        }


                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }



                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
//                        lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                if(objParSis.getCodigoUsuario()==1){
                    //promedio manual
                    vecAux.add("" + (intNumColIniProMen+1));
                    //minimo
                    vecAux.add("" + intNumColIniStkMinExc);
                }
                else{
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }

                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);


                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private int getRegHistoricoSeleccionados(){
        int intNumChkSel=0;
        String strLin="";
        for(int p=0; p<objTblModHis.getRowCountTrue();p++){
            strLin=objTblModHis.getValueAt(p, INT_TBL_DAT_HIS_LIN)==null?"":objTblModHis.getValueAt(p, INT_TBL_DAT_HIS_LIN).toString();
            if(strLin.equals("M")){
                if(objTblModHis.isChecked(p, INT_TBL_DAT_HIS_CHK)){
                    intNumChkSel++;
                }
            }
        }
        return intNumChkSel;
    }



    private boolean consultarRegAniGrp() 
    {
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        BigDecimal bdeStkActCal=new BigDecimal("0");
        BigDecimal bdeRepMaxCal=new BigDecimal("0");
        BigDecimal bdeRepMinCal=new BigDecimal("0");
        BigDecimal bdeStkExcCal=new BigDecimal("0");
        
        BigDecimal bdeStkDspBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMaxBodDstCal=new BigDecimal("0");
        BigDecimal bdeRepMinBodDstCal=new BigDecimal("0");
        BigDecimal bdeStkExcBodDstCal=new BigDecimal("0");
        BigDecimal bdeExiCanDspRepCal=new BigDecimal("0");
        
        
        try{
            if(con!=null){
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    
                    
                                        
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + " AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa 
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani";
                    strAux+="               ORDER BY a4.co_itm";
                    
                    
                    
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;
                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T') ";


                strAux+=" AND (b1.tx_codAlt LIKE '%I' OR b1.tx_codAlt LIKE '%S')";



                strAux+=" GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_stkActCenDis, b1.nd_canDisCenDis, b1.nd_stkAct, b1.nd_canDis, b1.nd_proMenVtaMan, b1.co_bod, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_nummesrep, b1.nd_repmin, b1.nd_repmax,b1.nd_stkExcBodCenDis, b1.nd_repMinBodCenDis, b1.nd_repMaxBodCenDis";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_stkActCenDis, b1.nd_canDisCenDis";
                strSQL+="            " + strCamSel + ", 0, 0, 0, b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_nummesrep, b1.nd_repmin, b1.nd_repmax, b1.nd_stkExcBodCenDis, b1.nd_repMinBodCenDis, b1.nd_repMaxBodCenDis";
                strSQL+=" FROM(";
                strSQL+=" 		              SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a1.st_reg, a1.st_ser, z1.co_bod, a3.nd_stkMin";
                strSQL+=" 		              , a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_proMenVtaMan";
                strSQL+=" 			      , z2.nd_stkActCenDis, z2.nd_canDisCenDis, z1.nd_stkAct, z1.nd_canDis, a3.nd_nummesrep, a3.nd_repmin, a3.nd_repmax";
                strSQL+=" 			      , e3.nd_stkexc AS nd_stkExcBodCenDis, e3.nd_repmin AS nd_repMinBodCenDis, e3.nd_repmax AS nd_repMaxBodCenDis";
                strSQL+=" 		              FROM (     (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2                 ON a1.co_uni=a2.co_reg)";
                strSQL+=" 					      INNER JOIN tbm_equInv AS x1 ON a1.co_emp=x1.co_emp AND a1.co_itm=x1.co_itm";
                strSQL+=" 					      INNER JOIN (";
                strSQL+=" 						      SELECT z.co_empGrp AS co_emp, z.co_bodGrp AS co_bod, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkAct, SUM(z.nd_canDis) AS nd_canDis";
                strSQL+=" 						      FROM(";
                strSQL+=" 							   SELECT x.*, y.co_empGrp, y.co_bodGrp FROM(";
                strSQL+=" 							      SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a1.nd_canDis, a2.co_itmMae";
                strSQL+=" 							      FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+=" 							      ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 							      ) AS x";
                strSQL+=" 							      INNER JOIN(";
                strSQL+=" 							      SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+=" 							      FROM tbr_bodEmpBodGrp";
                strSQL+=" 							      WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 							      AND co_bodGrp=" + txtCodBodDes.getText() + "";
                strSQL+=" 							      ORDER BY co_emp";
                strSQL+=" 							      ) AS y";
                strSQL+=" 							      ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" 						      ) AS z";
                strSQL+=" 						      GROUP BY z.co_empGrp, z.co_bodGrp, z.co_itmMae";
                strSQL+=" 					      ) AS z1";
                strSQL+=" 					      ON x1.co_emp=z1.co_emp AND x1.co_itmMae=z1.co_itmMae";
                
                //inicio-inmaconsa
                strSQL+=" 					      INNER JOIN (";
                strSQL+=" 						      SELECT z.co_empGrp AS co_emp, z.co_bodGrp AS co_bod, z.co_itmMae, SUM(z.nd_stkAct) AS nd_stkActCenDis, SUM(z.nd_canDis) AS nd_canDisCenDis";
                strSQL+=" 						      FROM(";
                strSQL+=" 							   SELECT x.*, y.co_empGrp, y.co_bodGrp FROM(";
                strSQL+=" 							      SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_stkAct, a2.co_itmMae, a1.nd_canDis";
                strSQL+=" 							      FROM tbm_invBod AS a1 INNER JOIN tbm_equInv AS a2";
                strSQL+=" 							      ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL+=" 							      ) AS x";
                strSQL+=" 							      INNER JOIN(";
                strSQL+=" 							      SELECT co_emp, co_bod, co_empGrp, co_bodGrp";
                strSQL+=" 							      FROM tbr_bodEmpBodGrp";
                strSQL+=" 							      WHERE co_empGrp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 							      AND co_bodGrp=" + INT_COD_BOD_CEN_DIS + "";//bODEGA DE CENTRO DE DISTRIBUCION - INMACONSA
                strSQL+=" 							      ORDER BY co_emp";
                strSQL+=" 							      ) AS y";
                strSQL+=" 							      ON x.co_emp=y.co_emp AND x.co_bod=y.co_bod";
                strSQL+=" 						      ) AS z";
                strSQL+=" 						      GROUP BY z.co_empGrp, z.co_bodGrp, z.co_itmMae";
                strSQL+=" 					      ) AS z2";
                strSQL+=" 					      ON x1.co_emp=z2.co_emp AND x1.co_itmMae=z2.co_itmMae";
                //fin
                strSQL+=" 				)";
                strSQL+=" 				INNER JOIN tbm_invBod AS a3";
                strSQL+=" 				ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm  ";
                strSQL+=" 		                AND a3.co_bod=" + txtCodBodDes.getText() + "";
                
                strSQL+=" 				INNER JOIN tbm_invBod AS e3";
                strSQL+=" 				ON a1.co_emp=e3.co_emp AND a1.co_itm=e3.co_itm  ";
                strSQL+=" 		                AND e3.co_bod=" + INT_COD_BOD_CEN_DIS + "";
                
                
                

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }

                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }

                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println("consultarRegAniGrp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                vecAux=new Vector();
                while(rst.next()){
                    if (blnCon){
                        
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_STK_ACT_CEN_DIS, "" + rst.getString("nd_stkActCenDis"));
                        vecReg.add(INT_TBL_DAT_DIS_CEN_DIS,     "" + rst.getString("nd_canDisCenDis"));
                
                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                            
                        }
                        
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1)){
                                    vecReg.add(j,     rst.getObject("nd_proMenVtaMan")==null?"":rst.getString("nd_proMenVtaMan")); 
                            }
                            else{
                                vecReg.add(j,     "");
                            }
                        }

                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     rst.getObject(j)==null?"":rst.getString(j));
                        }

                        //observacion - boton
                        vecReg.add(intNumColIniObs,     "");

                        //reposicion
                        for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                            if(j==intNumColIniStkRep)
                                vecReg.add(j,     rst.getObject("nd_nummesrep")==null?"":rst.getObject("nd_nummesrep"));
                            else if(j==(intNumColIniStkRep+1))
                                vecReg.add(j,     rst.getObject("nd_repmin")==null?"":rst.getObject("nd_repmin"));
                            else if(j==(intNumColIniStkRep+2))
                                vecReg.add(j,     rst.getObject("nd_repmax")==null?"":rst.getObject("nd_repmax"));
                            else
                                vecReg.add(j,     "");
                        }
                        
                        bdeStkActCal=new BigDecimal(rst.getObject("nd_stkAct")==null?"0":(rst.getString("nd_stkAct").equals("")?"0":rst.getString("nd_stkAct")));
                        bdeRepMaxCal=new BigDecimal(rst.getObject("nd_repMax")==null?"0":(rst.getString("nd_repMax").equals("")?"0":rst.getString("nd_repMax")));
                        bdeRepMinCal=new BigDecimal(rst.getObject("nd_repMin")==null?"0":(rst.getString("nd_repMin").equals("")?"0":rst.getString("nd_repMin")));
                        bdeStkExcCal=new BigDecimal(rst.getObject("nd_stkExc")==null?"0":(rst.getString("nd_stkExc").equals("")?"0":rst.getString("nd_stkExc")));
                        
                        //bodega centro distribucion
                        bdeStkDspBodDstCal=new BigDecimal(rst.getObject("nd_stkActCenDis")==null?"0":(rst.getString("nd_stkActCenDis").equals("")?"0":rst.getString("nd_stkActCenDis")));
                        bdeRepMaxBodDstCal=new BigDecimal(rst.getObject("nd_repMaxBodCenDis")==null?"0":(rst.getString("nd_repMaxBodCenDis").equals("")?"0":rst.getString("nd_repMaxBodCenDis")));
                        bdeStkExcBodDstCal=new BigDecimal(rst.getObject("nd_stkExcBodCenDis")==null?"0":(rst.getString("nd_stkExcBodCenDis").equals("")?"0":rst.getString("nd_stkExcBodCenDis")));
                        bdeRepMinBodDstCal=new BigDecimal(rst.getObject("nd_repMinBodCenDis")==null?"0":(rst.getString("nd_repMinBodCenDis").equals("")?"0":rst.getString("nd_repMinBodCenDis")));
                        
                        //bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMaxBodDstCal.add(bdeStkExcBodDstCal)));
                        bdeExiCanDspRepCal=(bdeStkDspBodDstCal.subtract(bdeRepMinBodDstCal.add(bdeStkExcBodDstCal)));
                        
                        //cantidad pendiente de reponer
                        for(int j=intNumColIniCanPenRep; j<intNumColFinCanPenRep;j++){
                            if((bdeRepMinCal.subtract(bdeStkActCal)).compareTo(new BigDecimal("0"))>0){
                                if(j==intNumColIniCanPenRep)
                                    vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                else{
                                    if(bdeExiCanDspRepCal.compareTo(new BigDecimal("0"))>0){
                                        if(bdeExiCanDspRepCal.compareTo((bdeRepMaxCal.subtract(bdeStkActCal)))>0)
                                            vecReg.add(j,     (bdeRepMaxCal.subtract(bdeStkActCal)));//can.pen.rep.
                                        else
                                            vecReg.add(j,     bdeExiCanDspRepCal);
                                    }
                                    else
                                        vecReg.add(j,     "0");
                                }//can.dis.rep
                            }
                            else
                                vecReg.add(j,     "0");
                        }

                        vecDat.add(vecReg);

                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                //para hacer editables las columnas
                if(objParSis.getCodigoUsuario()==1){
                    //minimo, ecexso, sugerido, observacion
                    for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                        vecAux.add("" + j);
                    }
                    //boton de observacion
                    vecAux.add(""+intNumColIniObs);
                    //promedio manual
                    for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                        if(j==(intNumColIniProMen+1))
                            vecAux.add("" + j);
                    }
                    for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                        if(j==intNumColIniStkRep)
                            vecAux.add("" + j);
                    }
                }
                else{
                    if(objParSis.getCodigoMenu()==2332){//bodegas
                        if(objPerUsr.isOpcionEnabled(2334)){//guardar
                            //minimo, ecexso, sugerido, observacion
                            for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                                vecAux.add("" + j);
                            }
                            //boton de observacion
                            vecAux.add(""+intNumColIniObs);
                            //promedio manual
                            for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                                if(j==(intNumColIniProMen+1))
                                    vecAux.add("" + j);
                            }
                            for(int j=intNumColIniStkRep; j<intNumColFinStkRep;j++){
                                if(j==intNumColIniStkRep)
                                    vecAux.add("" + j);
                            }
                        }
                    }
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }


                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");

            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }



    private boolean consultarRegAniEmp(){
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        String strFilItm="";
        int intNumColAdiFin=0;
        try{
            if(con!=null){
                if(intNumColAdiFec>1)
                    intNumColAdiFin=6;
                else
                    intNumColAdiFin=3;
                stm=con.createStatement();
                strAux="";
                strFilItm="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strFilItm+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strFilItm+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                for(int j=0; j<arlDatAniAdd.size(); j++){
                    intAni=objUti.getObjectValueAt(arlDatAniAdd, j, INT_ARL_ANI)==null?0:objUti.getIntValueAt(arlDatAniAdd, j, INT_ARL_ANI);
                    strAux+="		LEFT OUTER JOIN(";
                    strAux+="               SELECT  a4.co_emp, a4.co_itm, " + txtCodBodDes.getText() + " AS co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="               , SUM(a4.ne_numVec) AS ne_numVec, SUM(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="               FROM(";
                    strAux+="                     SELECT  a4.co_emp, a4.co_itm, a4.co_bod, SUM(a4.nd_uniVen) AS nd_uniVen";
                    strAux+="                     , SUM(a4.ne_numVec) AS ne_numVec, COUNT(a4.ne_numMes) AS ne_numMes, a4.ne_ani";
                    strAux+="                     FROM tbm_venMenInvBod AS a4";
                    strAux+="                     INNER JOIN tbm_inv AS a1";
                    strAux+="                     ON a4.co_emp=a1.co_emp AND a4.co_itm=a1.co_itm";
                    strAux+="                     WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    //if(txtCodBodDes.getText().equals("15"))
                    //    strAux+="                     AND a4.co_bod IN (1,2,3,5,11,28,15)";//California, Via a Daule, Quito, Manta, Santo domingo, Cuenca, Inmaconsa
                    //else
                        strAux+="                     AND a4.co_bod=" + txtCodBodDes.getText() + "";
                    strAux+="                     AND a4.ne_ani=" + intAni + "";
                    strAux+="                     " + strFilItm + "";
                    strAux+="                     GROUP BY a4.co_emp, a4.co_itm, a4.co_bod, a4.ne_ani";
                    strAux+="                     ORDER BY a4.co_itm";
                    strAux+="               ) AS a4";
                    strAux+="               GROUP BY a4.co_emp, a4.co_itm, a4.ne_ani";
                    strAux+="               ORDER BY a4.co_itm";
                    strAux+=" 		) AS b" + (j+2) + "";
                    strAux+=" 		ON b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_bod=b" + (j+2) + ".co_bod AND b1.co_itm=b" + (j+2) + ".co_itm";
                    strCamSel+=" , SUM(b" + (j+2) + ".nd_uniVen) AS " + "b" + (j+2) + "_nduniVen" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numVec) AS " + "b" + (j+2) + "_nenumVec" ;
                    strCamSel+=" , SUM(b" + (j+2) + ".ne_numMes) AS " + "b" + (j+2) + "_nenumMes" ;
                }
                strAux+="   WHERE b1.st_reg NOT IN('U','T') AND b1.st_ser NOT IN('S','T') AND (b1.tx_codAlt LIKE '%S' OR b1.tx_codAlt LIKE '%L')";
                strAux+=" GROUP BY b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b1.nd_stkAct, b1.nd_canDis, b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug, b1.nd_promenvtaman";
                strAux+=" ORDER BY b1.tx_codAlt";

                strSQL="";
                strSQL+="SELECT b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor";
                strSQL+="            " + strCamSel + ", 0, 0, 0 , b1.nd_stkAct, b1.nd_canDis, 0 AS nd_proMenVtaCal, b1.nd_proMenVtaMan";
                strSQL+="            , b1.nd_stkMin, b1.nd_stkexc, b1.nd_stkminsug, b1.tx_obsstkminsug";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug, a3.nd_promenvtaman, a3.nd_stkAct, a3.nd_canDis";
                strSQL+=" 		FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }

                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strFilItm + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }

                strSQL+=" 		) AS b1";
                strSQL+="" + strAux;
                System.out.println("consultarRegAniEmp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while(rst.next()){
                    if (blnCon){
                        vecAux=new Vector();
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getObject("tx_codAlt2")==null?"":rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));

                        for(int j=intNumColIniFec; j<intNumColFinFec;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //totales
                        for(int j=intNumColIniTot; j<intNumColFinTot;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }
                        //stock actual
                        for(int j=intNumColIniStkAct; j<intNumColFinStkAct;j++){
                            vecReg.add(j,     "" + rst.getObject(j)==null?"0":rst.getString(j));
                        }

                        //promedio mensual
                        for(int j=intNumColIniProMen; j<intNumColFinProMen;j++){
                            if(j==(intNumColIniProMen+1))
                                vecReg.add(j,     "" + (rst.getObject("nd_proMenVtaMan")==null?"0":(rst.getString("nd_proMenVtaMan").equals("")?"0":rst.getString("nd_proMenVtaMan"))));
                            else
                                vecReg.add(j,     "0");
                        }


                        //minimo y exceso
                        for(int j=intNumColIniStkMinExc; j<intNumColFinStkMinExc;j++){
                            vecReg.add(j,     "" + (rst.getObject(j)==null?"0":rst.getString(j)));
                        }


                        vecDat.add(vecReg);





                        i++;
                        pgrSis.setValue(i);
                        
                    }
                    else{
                        break;
                    }
                }
                lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                rst.close();
                stm.close();
                rst=null;
                stm=null;


                if(objParSis.getCodigoUsuario()==1){
                    //promedio manual
                    vecAux.add("" + (intNumColIniProMen+1));
                    //minimo
                    vecAux.add("" + intNumColIniStkMinExc);
                }
                else{
                    if(objParSis.getCodigoMenu()==1662){//proveedores
                        if(objPerUsr.isOpcionEnabled(1664)){//guardar
                            vecAux.add("" + (intNumColIniProMen+1));
                            vecAux.add("" + intNumColIniStkMinExc);
                        }
                    }
                }



                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }



    private boolean calcularColumnaTotalesAniMes(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeSumUni=new BigDecimal(0);
        BigDecimal bdeSumVec=new BigDecimal(0);
        BigDecimal bdeSumMes=new BigDecimal(0);
        
        double dblProCal=0.00;
        double dblIntAniDiv=0.00;

        try{
            if(optHisAnu.isSelected()) // José Marín M - 19-Nov-2014
                dblIntAniDiv=12*getRegHistoricoSeleccionados();
            else
                dblIntAniDiv=getRegHistoricoSeleccionados();
            // José Marín M - 19-Nov-2014
            
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=intNumColFinModEst; j<(intNumColFinModEst+(intNumColAdiFec*3)); j++){
                    if(p==0){
                        bdeSumUni=bdeSumUni.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p++;
                    }
                    else if(p==1){
                        bdeSumVec=bdeSumVec.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p++;
                    }
                    else if(p==2){
                        bdeSumMes=bdeSumMes.add(objTblMod.getValueAt(i, j)==null?new BigDecimal(0):(objTblMod.getValueAt(i, j).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, j).toString())));
                        p=0;
                    }
                }
                objTblMod.setValueAt("" + bdeSumUni, i, (intNumColFinModEst+(intNumColAdiFec*3)));
                objTblMod.setValueAt("" + bdeSumVec, i, ((intNumColFinModEst+(intNumColAdiFec*3))+1));
                objTblMod.setValueAt("" + bdeSumMes, i, ((intNumColFinModEst+(intNumColAdiFec*3))+2));

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                }
                else{//empresa
                    if(bdeSumUni.compareTo(new BigDecimal(0))>0){
                        //José Marín M. - 19-Nov-2014 - Promedio Calculado 
                        dblProCal=objUti.redondear((Double.parseDouble(""+bdeSumUni)/dblIntAniDiv), objParSis.getDecimalesBaseDatos());
                        objTblMod.setValueAt("" + dblProCal, i, ((intNumColFinModEst+(intNumColAdiFec*3))+4));
                          //José Marín M. - 19-Nov-2014 - Promedio Calculado  
                    }
                    dblProCal=0.00;
                }
                bdeSumUni=new BigDecimal(0);
                bdeSumVec=new BigDecimal(0);
                bdeSumMes=new BigDecimal(0);

            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    private boolean calcularColumnaPromedioCalculado(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeSumUni=new BigDecimal(0);
        BigDecimal bdeSumVec=new BigDecimal(0);
        BigDecimal bdeSumMes=new BigDecimal(0);

        double dblProCal=0.00;
        double dblIntAniDiv=0.00;

        try{
            dblIntAniDiv=12*getRegHistoricoSeleccionados();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){

                bdeSumUni=bdeSumUni.add(objTblMod.getValueAt(i, intNumColFinModEst)==null?new BigDecimal(0):(objTblMod.getValueAt(i, intNumColFinModEst).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, intNumColFinModEst).toString())));
                bdeSumVec=bdeSumVec.add(objTblMod.getValueAt(i, (intNumColFinModEst+1))==null?new BigDecimal(0):(objTblMod.getValueAt(i, (intNumColFinModEst+1)).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, (intNumColFinModEst+1)).toString())));
                bdeSumMes=bdeSumMes.add(objTblMod.getValueAt(i, (intNumColFinModEst+2))==null?new BigDecimal(0):(objTblMod.getValueAt(i, (intNumColFinModEst+2)).equals("")?new BigDecimal(0):new BigDecimal(objTblMod.getValueAt(i, (intNumColFinModEst+2)).toString())));

                if(bdeSumUni.compareTo(new BigDecimal(0))>0){
                    dblProCal=objUti.redondear((Double.parseDouble(""+bdeSumUni)/dblIntAniDiv), objParSis.getDecimalesBaseDatos());
                    objTblMod.setValueAt("" + dblProCal, i, (intNumColFinModEst+4));
                }
                dblProCal=0.00;

                bdeSumUni=new BigDecimal(0);
                bdeSumVec=new BigDecimal(0);
                bdeSumMes=new BigDecimal(0);
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConCla()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_desCorGrp");
            arlCam.add("a1.tx_desLarGrp");
            arlCam.add("a1.co_cla");
            arlCam.add("a1.tx_desCorCla");
            arlCam.add("a1.tx_desLarCla");

            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cod.Grp.");
            arlAli.add("Ali.Grp.");
            arlAli.add("Nom.Grp.");
            arlAli.add("Cod.Cla.");
            arlAli.add("Ali.Cla.");
            arlAli.add("Nom.Cla.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("28");
            arlAncCol.add("60");
            arlAncCol.add("190");
            arlAncCol.add("30");
            arlAncCol.add("60");
            arlAncCol.add("190");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_grp, a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp";
            strSQL+=" , a2.co_cla, a2.tx_desCor AS tx_desCorCla, a2.tx_desLar AS tx_desLarCla";
            strSQL+=" FROM tbm_claInv AS a2 LEFT OUTER JOIN tbm_grpClaInv AS a1";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" ORDER BY a1.co_grp, a2.co_cla";
            //Ocultar columnas.
            vcoCla=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clasificación", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCla.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
    private boolean mostrarVenConCla(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCla.setCampoBusqueda(2);
                    vcoCla.show();
                    if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Descripción corta".
                    if (vcoCla.buscar("a1.tx_desCorCla", txtDesCorCla.getText()))
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCla.setCampoBusqueda(0);
                        vcoCla.setCriterio1(11);
                        vcoCla.cargarDatos();
                        vcoCla.show();
                        if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                        {
                            txtCodCla.setText(vcoCla.getValueAt(4));
                            txtDesCorCla.setText(vcoCla.getValueAt(5));
                            txtDesLarCla.setText(vcoCla.getValueAt(6));
                            objTblMod.removeAllRows();
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCla.buscar("a1.tx_desLarCla", txtDesLarCla.getText()))
                    {
                        txtCodCla.setText(vcoCla.getValueAt(4));
                        txtDesCorCla.setText(vcoCla.getValueAt(5));
                        txtDesLarCla.setText(vcoCla.getValueAt(6));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCla.setCampoBusqueda(2);
                        vcoCla.setCriterio1(11);
                        vcoCla.cargarDatos();
                        vcoCla.show();
                        if (vcoCla.getSelectedButton()==vcoCla.INT_BUT_ACE)
                        {
                            txtCodCla.setText(vcoCla.getValueAt(4));
                            txtDesCorCla.setText(vcoCla.getValueAt(5));
                            txtDesLarCla.setText(vcoCla.getValueAt(6));
                            objTblMod.removeAllRows();
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



   private boolean guardarDatos(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(actualiza_tbmInvBod()){
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


   private boolean actualiza_tbmInvBod(){
        boolean blnRes=true;
        String strQueUpd;
        String strLin="";
        //BigDecimal bdeProMenVenMan=new BigDecimal(BigInteger.ZERO);
        try{
            if(con!=null){
                strQueUpd="";
                stm=con.createStatement();

                for(int i=0; i<objTblMod.getRowCountTrue();i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    //bdeProMenVenMan=new BigDecimal(objTblMod.getValueAt(i, (intNumColIniProMen+1))==null?"0":(objTblMod.getValueAt(i, (intNumColIniProMen+1)).toString().equals("")?"0": objTblMod.getValueAt(i, (intNumColIniProMen + 1)).toString()));
                    if(strLin.equals("M")){
                        strSQL="";
                        strSQL+="UPDATE tbm_invbod";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                            strSQL+=" SET    nd_stkmin=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc)), 0) + "";//fijo
                            strSQL+=",       nd_stkexc=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+1)), 0) + "";
                            strSQL+=",    nd_stkminsug=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+2)), 0) + "";
                            strSQL+=", tx_obsstkminsug=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc+3)), 0) + "";
                            //strSQL+=", nd_promenvtaman=" + objUti.redondearBigDecimal(bdeProMenVenMan, objParSis.getDecimalesBaseDatos())  + "";
                            strSQL+=", nd_promenvtaman=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniProMen+1)), 0)  + ""; 
                            strSQL+=", nd_nummesrep=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep)), 0) + "";
                            strSQL+=", nd_repmin=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+1)), 0) + "";
                            strSQL+=", nd_repmax=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkRep+2)), 0) + "";
                        }
                        else{
                            //strSQL+=" SET    nd_promenvtaman=" + objUti.redondearBigDecimal(bdeProMenVenMan, objParSis.getDecimalesBaseDatos())  + "";
                            strSQL+=" SET    nd_promenvtaman=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniProMen+1)), 0)  + "";
                            strSQL+=",             nd_stkmin=" + objUti.codificar(objTblMod.getValueAt(i, (intNumColIniStkMinExc)), 0) + "";
                        }          
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_bod=" + txtCodBodDes.getText() + "";
                        strSQL+=" AND co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                        strSQL+=";";
                        System.out.println("actualiza_tbmInvBod: " + strSQL);
                        strQueUpd+=strSQL;
                    }
                }
                stm.executeUpdate(strQueUpd);
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
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        //Validar el "Tipo de documento".
        if (txtCodBodDes.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBodDes.requestFocus();
            return false;
        }


        if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
            if( ! chkHisVta.isSelected()){
                mostrarMsgInf("<HTML>El selector <FONT COLOR=\"blue\">Histórico de Ventas </FONT> es obligatorio.<BR>Seleccionelo y escoga también una de las dos opciones y vuelva a intentarlo.</HTML>");
                return false;
            }
        }

        if(  (chkHisVta.isSelected()) &&  (getRegHistoricoSeleccionados()==0)  ){
            mostrarMsgInf("<HTML>Seleccione por lo menos un registro de la tabla de información histórica.</HTML>");
            return false;
        }



        return true;
    }


    private boolean consultarRegStkActGrp(){
        boolean blnRes=true;
        int intAni=0;
        String strCamSel="";
        int intNumTotReg=0, i;
        try{
            if(con!=null){
                stm=con.createStatement();
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";


                strSQL="";
                strSQL+=" 	SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser";
                strSQL+=" 	, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug";
                strSQL+=" 		FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
                strSQL+=" 		ON a1.co_uni=a2.co_reg)";
                strSQL+=" 		INNER JOIN tbm_invBod as a3";
                strSQL+=" 		ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           INNER JOIN tbr_invCla AS c1";
                    strSQL+="           ON a3.co_emp=c1.co_emp AND a3.co_itm=c1.co_itm";
                }


                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="               AND a3.co_bod=" + txtCodBodDes.getText() + "";
                strSQL+="               " + strAux + "";
                if(chkStkMinSug.isSelected())
                    strSQL+="               and a3.nd_stkminsug>=0";

                if( ! txtCodCla.getText().equals("")){
                    strSQL+="           AND c1.co_cla=" + txtCodCla.getText() + " AND c1.st_reg IN('A','P')";
                }


                strSQL+="   AND a1.st_reg NOT IN('U','T') AND a1.st_ser NOT IN('S','T') ";


                strSQL+=" AND (a1.tx_codAlt LIKE '%I' OR a1.tx_codAlt LIKE '%S')";




                strSQL+=" GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a3.co_bod, a1.st_reg, a1.st_ser, a3.nd_stkMin, a3.nd_stkexc, a3.nd_stkminsug, a3.tx_obsstkminsug";
                strSQL+=" ORDER BY a1.tx_codAlt";
                System.out.println("consultarRegStkActGrp: " + strSQL);

                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while(rst.next()){
                    if (blnCon){
                        vecAux=new Vector();
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         "" + rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     "" + rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS, "" + rst.getString(""));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         "" + rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         "" + rst.getString("tx_desCor")==null?"":rst.getString("tx_desCor"));

                        vecReg.add((INT_TBL_DAT_UNI_MED+2), "" + rst.getObject("nd_stkMin")==null?null:rst.getString("nd_stkMin"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+3), "" + rst.getObject("nd_stkexc")==null?null:rst.getString("nd_stkexc"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+4), "" + rst.getObject("nd_stkminsug")==null?null:rst.getString("nd_stkminsug"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+5), "" + rst.getObject("tx_obsstkminsug")==null?null:rst.getString("tx_obsstkminsug"));
                        vecReg.add((INT_TBL_DAT_UNI_MED+6), "");




                        if(objParSis.getCodigoUsuario()==1){
                            //para hacer editables las ultimas 5 columnas
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                            vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                        }
                        else{
                            if(objParSis.getCodigoMenu()==2332){//bodegas
                                if(objPerUsr.isOpcionEnabled(2334)){//guardar
                                    //para hacer editables las ultimas 5 columnas
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                                }
                            }

                            if(objParSis.getCodigoMenu()==1662){//proveedores
                                if(objPerUsr.isOpcionEnabled(1664)){//guardar
                                    //para hacer editables las ultimas 5 columnas
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+2));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+3));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+4));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+5));
                                    vecAux.add("" + (INT_TBL_DAT_UNI_MED+6));

                                }
                            }
                        }


                        vecDat.add(vecReg);

                        i++;
                        pgrSis.setValue(i);
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                    }
                    else{
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;

                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);

                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

   

    private void exitForm(){
        String strTit, strMsg;
        try{
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
    }
    
    


    private boolean calcularPromedioAnual() 
    {
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAdiFec);

            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal("12"), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProMen);
                bdeProAnuCal=new BigDecimal(0);
                bdeTotUni=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularPromedioMensual(){
        boolean blnRes=true;
        int p=0;
        BigDecimal bdeTotUni=new BigDecimal(0);
        BigDecimal bdeNumAniSel=new BigDecimal(0);
        BigDecimal bdeProAnuCal=new BigDecimal(0);

        try{
            bdeNumAniSel=new BigDecimal("" + intNumColAdiFec);

            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                //contiene el valor de la columna de UNIDADES en TOTALES
                bdeTotUni=new BigDecimal(objTblMod.getValueAt(i, intNumColIniTot)==null?"0":(objTblMod.getValueAt(i, intNumColIniTot).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniTot).toString()));
                bdeProAnuCal=(bdeTotUni.divide(bdeNumAniSel, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));//promedio anual calculado de ventas calculado por item
                objTblMod.setValueAt("" + objUti.redondearBigDecimal(bdeProAnuCal, Integer.parseInt("0")), i, intNumColIniProMen);
                bdeProAnuCal=new BigDecimal(0);
                 bdeTotUni=new BigDecimal(0);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    private boolean calcularCantidadReponer(){
        boolean blnRes=true;
        BigDecimal bdeStkAct=new BigDecimal(0);
        Object objCanRepCal="";
        Object objMin="";
        Object objMax="";
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeStkAct=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStkAct)==null?"":(objTblMod.getValueAt(i, intNumColIniStkAct).toString().equals("")?"0":objTblMod.getValueAt(i, intNumColIniStkAct).toString()));
                objMin=objTblMod.getValueAt(i, (intNumColIniStkRep+1))==null?"":objTblMod.getValueAt(i, (intNumColIniStkRep+1)).toString();
                objMax=objTblMod.getValueAt(i, (intNumColIniStkRep+2))==null?"":objTblMod.getValueAt(i, (intNumColIniStkRep+2)).toString();

                if(  (!objMin.equals("")) && (!objMax.equals(""))  ){
                    if(bdeStkAct.compareTo(new BigDecimal(objMin.toString()))<0){
                        objCanRepCal=""+new BigDecimal(objMax.toString()).subtract(bdeStkAct);
                    }
                    else{
                        objCanRepCal=""+new BigDecimal(BigInteger.ZERO);
                    }
                }
                objTblMod.setValueAt(objCanRepCal, i, (intNumColIniStkRep+3));
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean cambiarNumeroMesesReponer(){
        boolean blnRes=true;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objTblMod.setValueAt(jspNumMesRep.getValue(), i, intNumColIniStkRep);
            }

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean calcularMinimoMaximo(){
        boolean blnRes=true;
        Object objValUtiCalMinMax="";
        Object objPorMin="";
        Object objPorMax="";

        Object objFij="";
        Object objProAnuCal="";
        Object objProAnuMan="";
        BigDecimal bdeNumMesRep=new BigDecimal(0);

        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                objProAnuCal=objTblMod.getValueAt(i, intNumColIniProMen)==null?"":objTblMod.getValueAt(i, intNumColIniProMen).toString();
                objProAnuMan=objTblMod.getValueAt(i, (intNumColIniProMen+1))==null?"":objTblMod.getValueAt(i, (intNumColIniProMen+1));
                objFij=objTblMod.getValueAt(i, intNumColIniStkMinExc)==null?"":objTblMod.getValueAt(i, intNumColIniStkMinExc);
                bdeNumMesRep=new BigDecimal(objTblMod.getValueAt(i, intNumColIniStkRep)==null?"0":(objTblMod.getValueAt(i, intNumColIniStkRep).toString().equals("")?"0": objTblMod.getValueAt(i, intNumColIniStkRep).toString()));

                // 1.-     promedio manual * numero de meses a reponer - stock actual
                if(!objFij.equals("")){
                    //objValUtiCalMinMax="" + (new BigDecimal(objFij.toString()).multiply(bdeNumMesRep)  );
                    objValUtiCalMinMax="" + (new BigDecimal(objFij.toString()) );
                }
                // 2.-     promedio manual * numero de meses a reponer - stock actual
                else if(   (!objProAnuMan.equals(""))  && (objFij.equals(""))  ){
                    objValUtiCalMinMax="" + (new BigDecimal(objProAnuMan.toString()).multiply(bdeNumMesRep)  );
                }
                // 3.-      promedio calculado * numero de meses a reponer - stock actual
                else if(   (objProAnuMan.equals(""))  && (objFij.equals(""))  ){
                    objValUtiCalMinMax="" + (new BigDecimal(""+objProAnuCal).multiply(bdeNumMesRep)  );
                }
                objTblMod.setValueAt("" + objValUtiCalMinMax, i, (intNumColIniStkRep+4));
                if(!objValUtiCalMinMax.equals("")){
                    if(!objFij.equals("")){
                        objPorMin=objUti.redondearBigDecimal((new BigDecimal(""+objValUtiCalMinMax)), Integer.parseInt("0"));
                        objPorMax=new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMax.getText()));

                        //bdeMin
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMin.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+1));
                        //bdeMax
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMax.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+2));
                    }
                    else{
                        objPorMin=objUti.redondearBigDecimal((new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMin.getText()))), Integer.parseInt("0"));
                        objPorMax=new BigDecimal(""+objValUtiCalMinMax).multiply(new BigDecimal(txtValMax.getText()));

                        //bdeMin
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMin.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+1));
                        //bdeMax
                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(objPorMax.toString(), Integer.parseInt("0")), i, (intNumColIniStkRep+2));
                    }


                }
                else{
                    objPorMin="";
                    objPorMax="";

                    //bdeMin
                    objTblMod.setValueAt("", i, (intNumColIniStkRep+1));
                    //bdeMax
                    objTblMod.setValueAt("", i, (intNumColIniStkRep+2));

                }              



            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





}
