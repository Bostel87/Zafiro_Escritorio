package RecursosHumanos.ZafRecHum52;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import Maestros.ZafMae07.ZafMae07_01;
import RecursosHumanos.ZafRecHum51.ZafRecHum51;
import java.io.StreamTokenizer;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Justificaciones de marcaciones incompletas.
 *
 * @author Roberto Flores Guayaquil 05/09/2012
 */
public class ZafRecHum52 extends javax.swing.JInternalFrame {

    private static final int INT_TBL_DAT_LIN = 0;
    private static final int INT_TBL_DAT_CHKSEL = 1;
    private static final int INT_TBL_DAT_CO_EMP = 2;
    private static final int INT_TBL_DAT_NOM_EMP = 3;
    private static final int INT_TBL_DAT_FECHA = 4;
    private static final int INT_TBL_DAT_COD_TRA = 5;
    private static final int INT_TBL_DAT_NOM_APE_TRA = 6;
    private static final int INT_TBL_DAT_HOR_ENT_EST = 7;
    private static final int INT_TBL_DAT_HOR_SAL_EST = 8;
    private static final int INT_TBL_DAT_HOR_ENT_MAR = 9;
    private static final int INT_TBL_DAT_HOR_SAL_MAR = 10;
    private static final int INT_TBL_DAT_CHK_MAR_INC = 11;
    private static final int INT_TBL_DAT_CHK_JUS_MAR_INC = 12;
    private static final int INT_TBL_DAT_HOR_ENT_JUS = 13;
    private static final int INT_TBL_DAT_HOR_SAL_JUS = 14;
    private static final int INT_TBL_DAT_MOT = 15;
    private static final int INT_TBL_DAT_COD_MOT = 16;
    private static final int INT_TBL_DAT_BUT_MOT = 17;
    private static final int INT_TBL_DAT_OBS = 18;
    private static final int INT_TBL_DAT_BUT_OBS = 19;
    private static final int INT_TBL_DAT_ST_JUS_MAR_INC=20;

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafTblEdi objTblEdi;                                            //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                //Editor: JTextField en celda.
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                             //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                             //Renderer de Check Box para campo Laborable
    private boolean blnCon;                                                             //true: Continua la ejecución del hilo.
    private ZafTblCelEdiChk objTblCelEdiChk;                                //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                               //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                             //Renderer de Check Box

    private ZafMouMotAda objMouMotAda;                                      //ToolTipText en TableHeader.
//    private ZafMouMotAdaMovReg objMouMotAdaMovReg;                        //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                      //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblTot objTblTot;                                            //JTable de totales.
    private ZafVenCon vcoEmp;                                               //Ventana de consulta.
    private ZafVenCon vcoDep;                                               //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafVenCon vcoOfi;                                               //Ventana de consulta.
    private ZafVenCon vcoMotJusAsi;                                         //Ventana de consulta.

    private ZafTblCelRenBut objTblCelRenBut;                                //Render: Presentar JButton en JTable.

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;

    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strCodOfi = "";
    private String strDesLarOfi = "";
    private String strCodMotJus, strDesMot;

    static final int INT_TBL_DAT_NUM_TOT_CDI = 22;                            //Número total de columnas dinámicas.

    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;

    private ZafTblCelEdiButVco objTblCelEdiButVcoCta;                       //Editor: JButton en celda.

    private String strVersion = "v1.09";

    /**
     * Crea una nueva instancia de la clase ZafRecHum53.
     */
    public ZafRecHum52(ZafParSis obj) {
        try {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objPerUsr = new ZafPerUsr(objParSis);

            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);

            if (objPerUsr.isOpcionEnabled(3294)) {
                butCon.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3295)) {
                butGua.setVisible(true);
            }
            if (objPerUsr.isOpcionEnabled(3296)) {
                butCer.setVisible(true);
            }

            if (!configurarFrm()) {
                exitForm();
            }
        } catch (CloneNotSupportedException e) {
            {
                objUti.mostrarMsgErr_F1(this, e);
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

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtNomDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jChkEmpMarInc = new javax.swing.JCheckBox();
        jChkTodEmp = new javax.swing.JCheckBox();
        panCab = new javax.swing.JPanel();
        jLblOfi = new javax.swing.JLabel();
        txtCodOfi = new javax.swing.JTextField();
        txtNomOfi = new javax.swing.JTextField();
        butOfi = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        panCabRpt = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtHorSalJus = new javax.swing.JTextField();
        txtHorEntJus = new javax.swing.JTextField();
        butJus = new javax.swing.JButton();
        butMot = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObs1 = new javax.swing.JTextArea();
        txtCodMot = new javax.swing.JTextField();
        txtDesMot = new javax.swing.JTextField();
        scrTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setAutoscrolls(true);
        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panFil.setLayout(null);

        optTod.setText("Todos los empleados");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 76, 400, 20);

        optFil.setSelected(true);
        optFil.setText("Sólo los empleados que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        jLabel4.setText("Empresa:"); // NOI18N
        panFil.add(jLabel4);
        jLabel4.setBounds(24, 116, 120, 20);

        txtCodEmp.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(144, 116, 56, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 116, 460, 20);

        butEmp.setText(".."); // NOI18N
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(660, 116, 20, 20);

        jLabel5.setText("Departamento:"); // NOI18N
        panFil.add(jLabel5);
        jLabel5.setBounds(24, 156, 120, 20);

        txtCodDep.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(144, 156, 56, 20);

        txtNomDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDepActionPerformed(evt);
            }
        });
        txtNomDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDepFocusLost(evt);
            }
        });
        panFil.add(txtNomDep);
        txtNomDep.setBounds(200, 156, 460, 20);

        butDep.setText(".."); // NOI18N
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(660, 156, 20, 20);

        jLabel6.setText("Empleado:"); // NOI18N
        panFil.add(jLabel6);
        jLabel6.setBounds(24, 176, 120, 20);

        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        panFil.add(txtCodTra);
        txtCodTra.setBounds(144, 176, 56, 20);

        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        panFil.add(txtNomTra);
        txtNomTra.setBounds(200, 176, 460, 20);

        butTra.setText(".."); // NOI18N
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFil.add(butTra);
        butTra.setBounds(660, 176, 20, 20);

        jChkEmpMarInc.setSelected(true);
        jChkEmpMarInc.setText("Mostrar los empleados que tienen marcaciones incompletas");
        jChkEmpMarInc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpMarIncActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpMarInc);
        jChkEmpMarInc.setBounds(24, 196, 420, 23);

        jChkTodEmp.setText("Mostrar los empleados que no tienen marcaciones incompletas");
        jChkTodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkTodEmpActionPerformed(evt);
            }
        });
        panFil.add(jChkTodEmp);
        jChkTodEmp.setBounds(24, 216, 420, 23);

        panCab.setPreferredSize(new java.awt.Dimension(0, 130));
        panCab.setLayout(null);
        panFil.add(panCab);
        panCab.setBounds(0, 0, 690, 110);

        jLblOfi.setText("Oficina:"); // NOI18N
        panFil.add(jLblOfi);
        jLblOfi.setBounds(24, 136, 120, 20);

        txtCodOfi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodOfiActionPerformed(evt);
            }
        });
        txtCodOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodOfiFocusLost(evt);
            }
        });
        panFil.add(txtCodOfi);
        txtCodOfi.setBounds(144, 136, 56, 20);

        txtNomOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomOfiActionPerformed(evt);
            }
        });
        txtNomOfi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomOfiFocusLost(evt);
            }
        });
        panFil.add(txtNomOfi);
        txtNomOfi.setBounds(200, 136, 460, 20);

        butOfi.setText(".."); // NOI18N
        butOfi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butOfiActionPerformed(evt);
            }
        });
        panFil.add(butOfi);
        butOfi.setBounds(660, 136, 20, 20);

        tabFrm.addTab("Filtro", null, panFil, "Filtro");

        panRpt.setLayout(new java.awt.BorderLayout());

        panCabRpt.setPreferredSize(new java.awt.Dimension(0, 120));
        panCabRpt.setLayout(null);

        jLabel1.setText("Hora de entrada justificada:");
        panCabRpt.add(jLabel1);
        jLabel1.setBounds(20, 10, 135, 14);

        jLabel2.setText("Hora de salida justificada:");
        panCabRpt.add(jLabel2);
        jLabel2.setBounds(20, 30, 124, 14);

        jLabel3.setText("Motivo:");
        panCabRpt.add(jLabel3);
        jLabel3.setBounds(20, 50, 36, 14);

        jLabel7.setText("Observación:");
        panCabRpt.add(jLabel7);
        jLabel7.setBounds(20, 80, 64, 14);

        txtHorSalJus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorSalJusActionPerformed(evt);
            }
        });
        txtHorSalJus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorSalJusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorSalJusFocusLost(evt);
            }
        });
        panCabRpt.add(txtHorSalJus);
        txtHorSalJus.setBounds(200, 30, 80, 20);

        txtHorEntJus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHorEntJusActionPerformed(evt);
            }
        });
        txtHorEntJus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtHorEntJusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHorEntJusFocusLost(evt);
            }
        });
        panCabRpt.add(txtHorEntJus);
        txtHorEntJus.setBounds(200, 10, 80, 20);

        butJus.setText("Justificar"); // NOI18N
        butJus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butJusActionPerformed(evt);
            }
        });
        panCabRpt.add(butJus);
        butJus.setBounds(560, 10, 100, 20);

        butMot.setText(".."); // NOI18N
        butMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotActionPerformed(evt);
            }
        });
        panCabRpt.add(butMot);
        butMot.setBounds(540, 50, 20, 20);

        txtObs1.setColumns(20);
        txtObs1.setLineWrap(true);
        txtObs1.setRows(3);
        jScrollPane1.setViewportView(txtObs1);

        panCabRpt.add(jScrollPane1);
        jScrollPane1.setBounds(200, 70, 340, 50);

        txtCodMot.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMotActionPerformed(evt);
            }
        });
        txtCodMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMotFocusLost(evt);
            }
        });
        panCabRpt.add(txtCodMot);
        txtCodMot.setBounds(200, 50, 40, 20);

        txtDesMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesMotActionPerformed(evt);
            }
        });
        txtDesMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesMotFocusLost(evt);
            }
        });
        panCabRpt.add(txtDesMot);
        txtDesMot.setBounds(240, 50, 300, 20);

        panRpt.add(panCabRpt, java.awt.BorderLayout.PAGE_START);

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
        scrTbl.setViewportView(tblDat);

        panRpt.add(scrTbl, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
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

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {
            blnCon=true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
              
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private boolean validaDiasConsulta() {
        boolean blnRes = true;

        try {

            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
    //        int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            //        if(fecDoc!=null){
            //            objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
            //            objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
            //            objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            //        }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());

            if (objParSis.getCodigoUsuario() != 1) {
                objFecPagActual.add(java.util.Calendar.DATE, -7);
            }

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged()) {
            if (!verificaUsuario()) {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) {
                    if (guardarDat()) {
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    } else {
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                    }
                }
            } else {
                mostrarMsgInf("Usuario no está autorizado para realizar sus propias justificaciones.");
                objThrGUI = null;
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private boolean verificaUsuario() {
        boolean blnRes = false;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (con != null) {

                stmLoc = con.createStatement();

                if (objParSis.getCodigoUsuario() != 1) {

                    for (int i = 0; i < tblDat.getRowCount(); i++) {

                        if (tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M") == 0) {

                            System.out.println("empleado que se intenta justificar: " + Integer.valueOf(tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString()));

                            String strSql = "select c.co_usr, c.tx_usr,co_tra";
                            strSql += " from ";
                            strSql += " (select d.co_usr, upper(d.tx_usr) as tx_usr ";
                            strSql += " from tbm_usr as d ";
                            strSql += " where d.st_reg='A') as c, ";
                            strSql += " (select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr ";
                            strSql += " from tbm_tra as a ";
                            strSql += " inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A') ";
                            strSql += " where a.st_reg='A') as e ";
                            strSql += " where c.tx_usr=e.tx_usr";
                            strSql += " and e.tx_usr=" + objUti.codificar(objParSis.getNombreUsuario().toUpperCase());
                            System.out.println("usuario jus: " + strSql);

                            rstLoc = stmLoc.executeQuery(strSql);

                            if (rstLoc.next()) {
                                if (tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString().compareTo(rstLoc.getString("co_tra")) == 0) {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    private boolean guardarDat() {
        boolean blnRes = true;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql = "";
        ZafUtil zafUtil = new ZafUtil();
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {

                con.setAutoCommit(false);
                stmLoc = con.createStatement();

                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    System.out.println("i = " + i + " ---> " + tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString());
                    if (tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M") == 0) {

                        if (!verificarFecha(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA).toString())) {
                            blnRes = false;
                            break;
                        }

                        Object objCodMot = objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT);
                        String strCoMot = "";
                        if (objCodMot != null) {
                            strCoMot = objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString();
                            if (strCoMot.equals("")) {
                                strCoMot = "null";
                            }
                        } else {
                            strCoMot = "null";
                        }
                        //David
                        if(!objTblMod.getValueAt(i, INT_TBL_DAT_ST_JUS_MAR_INC).toString().equals("0")){
                            mostrarMsgInf("No puede Desjustificar el registro.");
                            blnRes = false ;
                            break;
                        }
                        String strJusMarcInc = "";
                        if (objTblMod.getValueAt(i, INT_TBL_DAT_CHK_JUS_MAR_INC).equals(true)) {
                            strJusMarcInc = objUti.codificar("S");
                            double valDevolver = getValorDevolver(con, tblDat.getValueAt(i, INT_TBL_DAT_CO_EMP).toString(), tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString(),
                                    zafUtil.codificar(tblDat.getValueAt(i, INT_TBL_DAT_FECHA).toString()));
                            valDevolver = zafUtil.redondear(valDevolver, objParSis.getDecimalesMostrar());
                            System.out.println("Valor Calculado" + valDevolver);
                            String txObs = "Zafiro: Justificación Marcación Incompleta (" + tblDat.getValueAt(i, INT_TBL_DAT_FECHA).toString() + ")";
                            if (valDevolver > 0) {
                                if (!grabarIngProgramado(con, tblDat.getValueAt(i, INT_TBL_DAT_CO_EMP).toString(),
                                        tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString(), valDevolver, txObs)) {
                                    blnRes = false;
                                    break;
                                }
                            }
                        } else {
                            strJusMarcInc = "null";
                        }

                        strSql = "";
                        strSql += "UPDATE tbm_cabconasitra SET";
                        strSql += " ho_entjus= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_ENT_JUS)) + " , ";
                        strSql += " ho_saljus= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_SAL_JUS)) + " , ";
                        strSql += " st_jusmarinc= " + strJusMarcInc + " , ";
                        strSql += " co_motjusmarinc= " + strCoMot + " , ";
                        strSql += " tx_obsjusmarinc= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                        strSql += " fe_jusmarinc= current_timestamp" + " , ";
                        strSql += " co_usrjusmarinc= " + objParSis.getCodigoUsuario() + " , ";
                        strSql += " tx_comjusmarinc= " + objUti.codificar(objParSis.getDireccionIP());
                        strSql += " WHERE";
                        strSql += " co_tra= " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                        strSql += " and fe_dia= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA));
                        System.out.println("act marcacion incom:" + strSql);
                        stmLoc.executeUpdate(strSql);
                    }
                }

                if (blnRes) {
                    con.commit();
                    new RRHHDao(objUti, objParSis).callServicio9();
                    //Inicializo el estado de las filas.
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                }
            }
        } catch (java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged

   }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");

        }
        optTod.setSelected(true);
        optFil.setSelected(false);
        jChkEmpMarInc.setSelected(false);
        jChkTodEmp.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilItemStateChanged

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected()) {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodDep.setText("");
            txtNomDep.setText("");
            txtCodTra.setText("");
            txtNomTra.setText("");

        }
        optTod.setSelected(false);
        optFil.setSelected(true);
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp = txtCodEmp.getText();
        txtCodEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)) {
            if (txtCodEmp.getText().equals("")) {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            } else {
                BuscarEmp("a1.co_emp", txtCodEmp.getText(), 0);
            }
        } else {
            txtCodEmp.setText(strCodEmp);
        }
   }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp = txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
   }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (txtNomEmp.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp)) {
                if (txtNomEmp.getText().equals("")) {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                } else {
                    mostrarVenConEmp(2);
                }
            } else {
                txtNomEmp.setText(strNomEmp);
            }
        }
   }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp = txtCodEmp.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
        txtCodDep.transferFocus();
    }//GEN-LAST:event_txtCodDepActionPerformed

    private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
        // TODO add your handling code here:
        strCodDep = txtCodDep.getText();
        txtCodDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodDepFocusGained

    private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
        // TODO add your handling code here:
        if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
            if (txtCodDep.getText().equals("")) {
                txtCodDep.setText("");
                txtNomDep.setText("");
            } else {
                BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
            }
        } else {
            txtCodDep.setText(strCodDep);
        }
    }//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarEmp(String campo, String strBusqueda, int tipo) {

        vcoEmp.setTitle("Listado de Empresas");
        if (vcoEmp.buscar(campo, strBusqueda)) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        } else {
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
            } else {
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
            }
        }
    }

    public void BuscarDep(String campo, String strBusqueda, int tipo) {

        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
            }
        }
    }

    public void BuscarOfi(String campo, String strBusqueda, int tipo) {

        vcoOfi.setTitle("Listado de Oficinas");
        if (vcoOfi.buscar(campo, strBusqueda)) {
            txtCodOfi.setText(vcoOfi.getValueAt(1));
            txtNomOfi.setText(vcoOfi.getValueAt(2));
        } else {
            vcoOfi.setCampoBusqueda(tipo);
            vcoOfi.cargarDatos();
            vcoOfi.show();
            if (vcoOfi.getSelectedButton() == vcoOfi.INT_BUT_ACE) {
                txtCodOfi.setText(vcoOfi.getValueAt(1));
                txtNomOfi.setText(vcoOfi.getValueAt(2));
            } else {
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strDesLarOfi);
            }
        }
    }

    public void BuscarTra(String campo, String strBusqueda, int tipo) {

        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
            }
        }
    }

    public void BuscarMot(String campo, String strBusqueda, int tipo) {

        vcoMotJusAsi.setTitle("Listado de Motivos");
        if (vcoMotJusAsi.buscar(campo, strBusqueda)) {
            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
        } else {
            vcoMotJusAsi.setCampoBusqueda(tipo);
            vcoMotJusAsi.cargarDatos();
            vcoMotJusAsi.show();
            if (vcoMotJusAsi.getSelectedButton() == vcoMotJusAsi.INT_BUT_ACE) {
                txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
            } else {
                txtCodMot.setText(strCodDep);
                txtDesMot.setText(strDesLarDep);
            }
        }
    }

    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep = txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);

    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep)) {
                if (txtNomDep.getText().equals("")) {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                } else {
                    mostrarVenConDep(2);
                }
            } else {
                txtNomDep.setText(strDesLarDep);
            }
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep = txtCodDep.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConDep(0);

    }//GEN-LAST:event_butDepActionPerformed


    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        // TODO add your handling code here:
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        // TODO add your handling code here:
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
            }
        } else {
            txtCodTra.setText(strCodTra);
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra = txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void jChkTodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkTodEmpActionPerformed
        // TODO add your handling code here:
        jChkEmpMarInc.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkTodEmpActionPerformed

    private void txtHorSalJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorSalJusActionPerformed

        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtHorSalJusActionPerformed

    private void txtHorSalJusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSalJusFocusGained
        // TODO add your handling code here:
        strDesLarDep = txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtHorSalJusFocusGained

    private void txtHorSalJusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorSalJusFocusLost
        // TODO add your handling code here:

        try {
            txtHorSalJus.setText(txtHorSalJus.getText().trim());
            if (txtHorSalJus.getText().compareTo("") != 0) {
                String strHorIng = objUti.parseString(txtHorSalJus.getText());

                int intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                int intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));

                if ((intHH >= 0 && intHH <= 24)) {
                    if (!(intMM >= 0 && intMM <= 59)) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (strHorIng.length() == 5) {
                            strHorIng = strHorIng + ":00";
                        }
                        java.sql.Time t = SparseToTime(strHorIng);
                    }
                } else {
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                    txtHorSalJus.setText("");
                    txtHorSalJus.requestFocus();
                }
            }
        } catch (Exception e) {
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
            txtHorSalJus.setText("");
            txtHorSalJus.requestFocus();
        }

    }//GEN-LAST:event_txtHorSalJusFocusLost

    private void txtHorEntJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorEntJusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorEntJusActionPerformed

    private void txtHorEntJusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorEntJusFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHorEntJusFocusGained

    private void txtHorEntJusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorEntJusFocusLost
        // TODO add your handling code here:
        try {
            txtHorEntJus.setText(txtHorEntJus.getText().trim());
            if (txtHorEntJus.getText().compareTo("") != 0) {
                String strHorIng = objUti.parseString(txtHorEntJus.getText());

                int intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                int intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));

                if ((intHH >= 0 && intHH <= 24)) {
                    if (!(intMM >= 0 && intMM <= 59)) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        if (strHorIng.length() == 5) {
                            strHorIng = strHorIng + ":00";
                        }
                        java.sql.Time t = SparseToTime(strHorIng);
                    }
                } else {
                    String strTit = "Mensaje del sistema Zafiro";
                    String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                    JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                    txtHorEntJus.setText("");
                    txtHorEntJus.requestFocus();
                }
            }
        } catch (Exception e) {
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
            txtHorEntJus.setText("");
            txtHorEntJus.requestFocus();
        }
    }//GEN-LAST:event_txtHorEntJusFocusLost

    private void butJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butJusActionPerformed

        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) {

            for (int intFil = 0; intFil < tblDat.getRowCount(); intFil++) {

                if (objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)) {

                    objTblMod.setValueAt(Boolean.TRUE, intFil, INT_TBL_DAT_CHK_JUS_MAR_INC);
                    objTblMod.setValueAt(txtHorEntJus.getText(), intFil, INT_TBL_DAT_HOR_ENT_JUS);
                    objTblMod.setValueAt(txtHorSalJus.getText(), intFil, INT_TBL_DAT_HOR_SAL_JUS);
                    objTblMod.setValueAt(txtCodMot.getText(), intFil, INT_TBL_DAT_COD_MOT);
                    objTblMod.setValueAt(txtDesMot.getText(), intFil, INT_TBL_DAT_MOT);
                    objTblMod.setValueAt(txtObs1.getText(), intFil, INT_TBL_DAT_OBS);
                    objTblMod.setValueAt(Boolean.TRUE, intFil, INT_TBL_DAT_CHK_JUS_MAR_INC);
                }
            }
        }
    }//GEN-LAST:event_butJusActionPerformed

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si y No. El usuario es quien determina lo que debe hacer el
     * sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
        // TODO add your handling code here:

        strCodMotJus = txtCodMot.getText();
//        optFil.setSelected(true);
//        optTod.setSelected(false);
        mostrarVenConMot(0);

    }//GEN-LAST:event_butMotActionPerformed

    private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
        txtCodMot.transferFocus();
    }//GEN-LAST:event_txtCodMotActionPerformed

    private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
        // TODO add your handling code here:
        strCodMotJus = txtCodMot.getText();
        txtCodMot.selectAll();
    }//GEN-LAST:event_txtCodMotFocusGained

    private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
        // TODO add your handling code here:
        if (!txtCodMot.getText().equalsIgnoreCase(strCodMotJus)) {
            if (txtCodMot.getText().equals("")) {
                txtCodMot.setText("");
                txtDesMot.setText("");
            } else {
                BuscarMot("a1.co_motjus", txtCodMot.getText(), 0);
            }
        } else {
            txtCodMot.setText(strCodMotJus);
        }
    }//GEN-LAST:event_txtCodMotFocusLost

    private void txtDesMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesMotActionPerformed

        // TODO add your handling code here:
        txtDesMot.transferFocus();
    }//GEN-LAST:event_txtDesMotActionPerformed

    private void txtDesMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusGained
        // TODO add your handling code here:
        strDesMot = txtDesMot.getText();
        txtDesMot.selectAll();
    }//GEN-LAST:event_txtDesMotFocusGained

    private void txtDesMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusLost
        // TODO add your handling code here:
        if (txtDesMot.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesMot.getText().equalsIgnoreCase(strDesMot)) {
                if (txtDesMot.getText().equals("")) {
                    txtCodMot.setText("");
                    txtDesMot.setText("");
                } else {
                    mostrarVenConMot(2);
                }
            } else {
                txtNomDep.setText(strDesLarDep);
            }
        }
    }//GEN-LAST:event_txtDesMotFocusLost

    private void txtCodOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodOfiActionPerformed
        txtCodOfi.transferFocus();
    }//GEN-LAST:event_txtCodOfiActionPerformed

    private void txtCodOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusGained
        // TODO add your handling code here:
        strCodOfi = txtCodOfi.getText();
        txtCodOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtCodOfiFocusGained

    private void txtCodOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodOfiFocusLost
        // TODO add your handling code here:
        if (!txtCodOfi.getText().equalsIgnoreCase(strCodOfi)) {
            if (txtCodOfi.getText().equals("")) {
                txtCodOfi.setText("");
                txtNomOfi.setText("");
            } else {
                BuscarOfi("a1.co_ofi", txtCodOfi.getText(), 0);
            }
        } else {
            txtCodOfi.setText(strCodOfi);
        }
    }//GEN-LAST:event_txtCodOfiFocusLost

    private void txtNomOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomOfiActionPerformed
        // TODO add your handling code here:
        txtNomOfi.transferFocus();
    }//GEN-LAST:event_txtNomOfiActionPerformed

    private void txtNomOfiFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusGained
        // TODO add your handling code here:
        strDesLarOfi = txtNomOfi.getText();
        txtNomOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (txtNomOfi.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomOfi.getText().equalsIgnoreCase(strDesLarOfi)) {
                if (txtNomOfi.getText().equals("")) {
                    txtCodOfi.setText("");
                    txtNomOfi.setText("");
                } else {
                    mostrarVenConOfi(2);
                }
            } else {
                txtNomOfi.setText(strDesLarOfi);
            }
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    private void butOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOfiActionPerformed
        strCodOfi = txtCodOfi.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConOfi(0);
    }//GEN-LAST:event_butOfiActionPerformed

    private void jChkEmpMarIncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpMarIncActionPerformed
        // TODO add your handling code here:
        jChkTodEmp.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpMarIncActionPerformed

    /**
     * Cerrar la aplicación.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butJus;
    private javax.swing.JButton butMot;
    private javax.swing.JButton butOfi;
    private javax.swing.JButton butTra;
    private javax.swing.JCheckBox jChkEmpMarInc;
    private javax.swing.JCheckBox jChkTodEmp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLblOfi;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabRpt;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane scrTbl;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodMot;
    private javax.swing.JTextField txtCodOfi;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtDesMot;
    private javax.swing.JTextField txtHorEntJus;
    private javax.swing.JTextField txtHorSalJus;
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextArea txtObs1;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {

            //Inicializar objetos.
            objUti = new ZafUtil();
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);

            //Configurar las ZafVenCon.
            configurarVenConOfi();
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            configurarVenConJustificarMotivos();

            //Configurar los JTables.
            configuraTbl();
            agregarColTblDat();

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());

//            if(objParSis.getCodigoUsuario()!=1){
//                objFecPagActual.add(java.util.Calendar.DATE, -7 );
//            }else{
//                objFecPagActual.add(java.util.Calendar.DATE, -30 );
//            }
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));
            //*******************************************************************************
//            configurarTblDet();

//            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
//                configurarVenConEmp();
//                
//                txtCodEmp.setEditable(true);
//                txtNomEmp.setEditable(true);
//                butEmp.setEnabled(true);
//            }
//            else{
//
//                  txtCodEmp.setEditable(false);
//                  txtNomEmp.setEditable(false);
//                  butEmp.setEnabled(false);
//            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configuraTbl() {

        boolean blnRes = false;

        try {

            //Configurar JTable: Establecer el modelo.
            vecCab = new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHKSEL, "");
            vecCab.add(INT_TBL_DAT_CO_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP, "Empresa");
            vecCab.add(INT_TBL_DAT_FECHA, "Fecha");
            vecCab.add(INT_TBL_DAT_COD_TRA, "Código");
            vecCab.add(INT_TBL_DAT_NOM_APE_TRA, "Empleado");
            vecCab.add(INT_TBL_DAT_HOR_ENT_EST, "Entrada");
            vecCab.add(INT_TBL_DAT_HOR_SAL_EST, "Salida");
            vecCab.add(INT_TBL_DAT_HOR_ENT_MAR, "Entrada");
            vecCab.add(INT_TBL_DAT_HOR_SAL_MAR, "Salida");
            vecCab.add(INT_TBL_DAT_CHK_MAR_INC, "¿Mar.Inc.?");
            vecCab.add(INT_TBL_DAT_CHK_JUS_MAR_INC, "¿Justificar?");
            vecCab.add(INT_TBL_DAT_HOR_ENT_JUS, "Hor.Ent.Jus.");
            vecCab.add(INT_TBL_DAT_HOR_SAL_JUS, "Hor.Sal.Jus.");
            vecCab.add(INT_TBL_DAT_MOT, "Motivo");
            vecCab.add(INT_TBL_DAT_COD_MOT, "Cód.Mot.");
            vecCab.add(INT_TBL_DAT_BUT_MOT, "");
            vecCab.add(INT_TBL_DAT_OBS, "Observación");
            vecCab.add(INT_TBL_DAT_BUT_OBS, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el msenú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_FECHA).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(260);
            tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_EST).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_EST).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_MAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_MAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_MAR_INC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_JUS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_JUS).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_MOT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CO_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ST_JUS_MAR_INC, tblDat);
        //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHKSEL);
            vecAux.add("" + INT_TBL_DAT_CHK_JUS_MAR_INC);
            vecAux.add("" + INT_TBL_DAT_HOR_ENT_JUS);
            vecAux.add("" + INT_TBL_DAT_HOR_SAL_JUS);
            vecAux.add("" + INT_TBL_DAT_BUT_MOT);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            objTblMod.addColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellRenderer(zafTblCelRenChkLab);
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellEditor(zafTblCelEdiChkLab);

            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_MAR_INC).setCellRenderer(zafTblCelRenChkLab);
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_MAR_INC).setCellEditor(zafTblCelEdiChkLab);

            zafTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC).setCellRenderer(zafTblCelRenChkLab);
            zafTblCelEdiChkLab = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC).setCellEditor(zafTblCelEdiChkLab);

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(zafTblDocCelRenBut);

            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS, "Observación") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                    ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum52.this), true, strObs);
                    zafMae07_01.show();
                    if (zafMae07_01.getAceptar()) {
                        tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                    }
                }
            };

            objTblCelRenChkLab = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC).setCellRenderer(objTblCelRenChkLab);
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                //tblDat.setValueAt( new Boolean(true), intFilSel , INT_TBL_CHKAUT);
                    //tblDat.setValueAt( new Boolean(false), intFilSel , INT_TBL_CHKDEN);
                    if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHK_JUS_MAR_INC).equals(true)) {
                        objTblMod.setValueAt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_EST).toString(), intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                        objTblMod.setValueAt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_SAL_EST).toString(), intFilSel, INT_TBL_DAT_HOR_SAL_JUS);
                    } else {
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_HOR_SAL_JUS);
                    }

                }
            });

            objTblCelEdiTxt = new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_JUS).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_JUS).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                /*public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                 intFilSel=tblDat.getSelectedRow();
                 /*switch (tblDat.getSelectedColumn())
                 {
                 case INT_TBL_MINGRA:
                 String strMinGra=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_MINGRA));
                 //System.out.println("que esta sucediendo:" + strCan);
                 if (strMinGra.equals("")){
                 objTblMod.setValueAt(txtMinGra.getText(), intFilSel, INT_TBL_MINGRA);
                 //objTblCelEdiTxtVcoItm.setCancelarEdicion(true);
                 objTblCelEdiTxt.setCancelarEdicion(true);
                 }
                 }*

                 }*/

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    intFilSel = tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM = 0;
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;
                    try {

                        switch (tblDat.getSelectedColumn()) {
                            case INT_TBL_DAT_HOR_ENT_JUS:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_JUS));
                                if (!strHorIng.equals("")) {
                                    intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                    intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                }
                                break;
                            case INT_TBL_DAT_HOR_SAL_JUS:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_SAL_JUS));
                                if (!strHorIng.equals("")) {
                                    intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0, 2));
                                    intMM = Integer.parseInt(strHorIng.replace(":", "").substring(2, 4));
                                }
                                break;
                        }

                        if (!strHorIng.equals("")) {

                            if ((intHH >= 0 && intHH <= 24)) {
                                if (!(intMM >= 0 && intMM <= 59)) {
                                    {
                                        String strTit = "Mensaje del sistema Zafiro";
                                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                        JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                                    }
                                } else {

                                    switch (tblDat.getSelectedColumn()) {

                                        case INT_TBL_DAT_HOR_ENT_JUS:
                                            String strHorEnt = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_JUS));
                                            if (strHorEnt.length() == 5) {
                                                strHorEnt = strHorEnt + ":00";
                                            }
                                            java.sql.Time t = SparseToTime(strHorEnt);
                                            break;
                                        case INT_TBL_DAT_HOR_SAL_JUS:

                                            String strHorSal = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_SAL_JUS));
                                            if (strHorSal.length() == 5) {
                                                strHorSal = strHorSal + ":00";
                                            }
                                            t = SparseToTime(strHorSal);
                                            break;
                                    }
                                }
                            } else {

                                String strTit = "Mensaje del sistema Zafiro";
                                String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                                objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                            }

                        } else {
                            objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                        }

                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum52.this, strMen, strTit, JOptionPane.INFORMATION_MESSAGE);
                        objTblMod.setValueAt("", intFilSel, tblDat.getSelectedColumn());
                    }
                }
            });

            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);

//       objTblCelRenBut=new ZafTblCelRenBut();
//       tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(objTblCelRenBut);
//                
//       int intColVen[]=new int[2];
//       intColVen[0]=1;
//       intColVen[1]=2;
//       //intColVen[2]=3;
//       int intColTbl[]=new int[2];
//       intColTbl[0]=INT_TBL_DAT_MOT;
//       intColTbl[1]=INT_TBL_DAT_COD_MOT;
//       //intColTbl[2]=INT_TBL_DAT_NOM_CTA;
//                
//       objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDat, vcoMotJusAsi, intColVen, intColTbl);
//       tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellEditor(objTblCelEdiButVcoCta);
//       objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//           public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//           }
//           public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//               int intSelFil = tblDat.getSelectedRow();
//               if (vcoMotJusAsi.getSelectedButton()==ZafVenCon.INT_BUT_ACE){
//                   tblDat.setValueAt(vcoMotJusAsi.getValueAt(1),intSelFil, INT_TBL_DAT_COD_MOT);
//                   tblDat.setValueAt(vcoMotJusAsi.getValueAt(2),intSelFil, INT_TBL_DAT_MOT);
//               }
//           }
//       });
//       
//       intColVen=null;
//       intColTbl=null;
            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(zafTblDocCelRenBut);
            zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_MOT, "Motivo") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    int intFil = tblDat.getRowCount();

                    vcoMotJusAsi.setCampoBusqueda(2);
                    vcoMotJusAsi.setVisible(true);
                    if (vcoMotJusAsi.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                        tblDat.setValueAt(vcoMotJusAsi.getValueAt(1), intSelFil, INT_TBL_DAT_COD_MOT);
                        tblDat.setValueAt(vcoMotJusAsi.getValueAt(2), intSelFil, INT_TBL_DAT_MOT);
                    }
                }
            };

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            blnRes = true;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    public static java.sql.Time SparseToTime(String hora) {
        int h, m, s;

        h = Integer.parseInt(hora.charAt(0) + "" + hora.charAt(1));
        m = Integer.parseInt(hora.charAt(3) + "" + hora.charAt(4));
        s = Integer.parseInt(hora.charAt(6) + "" + hora.charAt(7));
        return (new java.sql.Time(h, m, s));
    }

    private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {

            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

            for (i = 0; i < 1; i++) {
                objTblHeaColGrpEmp = new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKSEL + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CO_EMP + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECHA + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TRA + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_APE_TRA + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Horario de trabajo");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_EST + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_EST + i * INT_TBL_DAT_NUM_TOT_CDI));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Marcaciones");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_MAR + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_MAR + i * INT_TBL_DAT_NUM_TOT_CDI));

                objTblHeaColGrpEmp = new ZafTblHeaColGrp("Justificación de marcaciones incompletas");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_MAR_INC + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_JUS_MAR_INC + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_JUS + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_JUS + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_MOT + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_MOT + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_MOT + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_OBS + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS + i * INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se
     * grabaron y que debe comunicar de este particular al administrador del
     * sistema.
     */
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private class ZafThreadGUI extends Thread {

        public void run() {

            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);

            limpia();

            if (!cargarDat()) {
            //Inicializar objetos si no se pudo cargar los datos.
                //lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }

            objThrGUI = null;
        }
    }

    private void limpia() {
        txtHorEntJus.setText("");
        txtHorSalJus.setText("");
        txtCodMot.setText("");
        txtDesMot.setText("");
        txtObs1.setText("");
    }

    /**
     * Se encarga de cargar la informacion en la tabla
     *
     * @return true si esta correcto y false si hay algun error
     */
    private boolean cargarDat() {
        boolean blnRes = false;
        java.sql.Connection conn = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql = "";
        String strFilFec = "";
        String sqlFilEmp = "";
        try {
            butCon.setText("Detener");
            conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conn != null) {

                stmLoc = conn.createStatement();

                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Búsqueda por rangos
                        strFilFec += " WHERE a.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strFilFec += " WHERE a.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strFilFec += " WHERE a.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                        break;
                    case 3: //Todo.
                        break;
                }

                if (txtCodEmp.getText().compareTo("") != 0) {
                    sqlFilEmp += " and c.co_emp  = " + txtCodEmp.getText() + " ";
                }

                if (txtCodTra.getText().compareTo("") != 0) {
                    sqlFilEmp += " AND c.co_tra  = " + txtCodTra.getText() + " ";
                }

                if (txtCodDep.getText().compareTo("") != 0) {
                    sqlFilEmp += " AND c.co_dep  = " + txtCodDep.getText() + " ";
                }

                if (txtCodOfi.getText().compareTo("") != 0) {
                    sqlFilEmp += " AND c.co_ofi  = " + txtCodOfi.getText() + " ";
                }

                String strEmp = "";
                if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                    strEmp = " and c.co_emp = " + objParSis.getCodigoEmpresa();
                }

                String strMarInc = "";
                if (optFil.isSelected()) {
                    if (jChkTodEmp.isSelected() && jChkEmpMarInc.isSelected()) {
                        strMarInc += " and ( ((a.ho_ent is null and a.ho_sal is not null) or (a.ho_ent is not null and a.ho_sal is null))";
                        strMarInc += " or (a.ho_ent is not null and a.ho_sal is not null) ) ";
                    }

                    if (jChkTodEmp.isSelected() && !jChkEmpMarInc.isSelected()) {
                        strMarInc += " and (a.ho_ent is not null and a.ho_sal is not null) ";
                    }

                    if (!jChkTodEmp.isSelected() && jChkEmpMarInc.isSelected()) {
                        strMarInc += " and ((a.ho_ent is null and a.ho_sal is not null) or (a.ho_ent is not null and a.ho_sal is null))";
                    }
                }

                String strDepAut = "";
                String strOfiAut = "";

                if (objParSis.getCodigoUsuario() != 1) {
                    strDepAut = "inner join tbr_depprgusr h on(c.co_dep=h.co_dep and h.co_dep in (select co_dep from tbr_depprgusr where co_usr = " + objParSis.getCodigoUsuario() + " "
                            + "and co_mnu=" + objParSis.getCodigoMenu() + " )) ";

                    if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                        strOfiAut = "inner join tbr_ofiLocPrgUsr i on(i.co_emp=0 and i.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr where co_usr = " + objParSis.getCodigoUsuario() + " "
                                + " and co_mnu=" + objParSis.getCodigoMenu() + " and st_reg='A')) ";
                    } else {
                        strOfiAut = "inner join tbr_ofiLocPrgUsr i on(i.co_emp=d.co_emp and i.co_loc=" + objParSis.getCodigoLocal() + "and i.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr where co_emp=" + objParSis.getCodigoEmpresa()
                                + " and co_usr = " + objParSis.getCodigoUsuario() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu=" + objParSis.getCodigoMenu() + " )) ";
                    }

                }

          //if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSql = "";
                strSql += "select coalesce(st_jusmarinc,'0') as st_jus, c.co_emp,d.tx_nom,c.co_ofi,c.co_tra,c.co_dep, a.fe_dia, (b.tx_ape || ' ' || b.tx_nom) as empleado," + "\n";
          //strSql+=" (case a.ho_ent when null then ho_entJus else a.ho_ent end) as ho_entEmp,";
                //strSql+=" (case a.ho_sal when null then ho_salJus else a.ho_sal end) as ho_salEmp,";
                strSql += " a.ho_ent as ho_entEmp, a.ho_sal as ho_salEmp," + "\n";
                strSql += " g.ho_ent as horaEntradaEstablecida,g.ho_sal as horaSalidaEstablecida," + "\n";
                strSql += " (case st_jusMarInc when 'S' then true else false end) as blnJusMarInc," + "\n";
                strSql += " ho_entJus, ho_salJus," + "\n";
                strSql += " co_motJusMarInc, e.tx_deslar ,tx_obsJusMarInc, " + "\n";
                strSql += " (case ((a.ho_ent is null and a.ho_sal is not null) or (a.ho_ent is not null and a.ho_sal is null))" + "\n";
                strSql += " when true then true else false end) as blnTieMarInc" + "\n";
                strSql += " from tbm_cabconasitra a" + "\n";
                strSql += " inner join tbm_tra b on (a.co_tra=b.co_tra)" + "\n";
                strSql += " inner join tbm_traemp c on (a.co_tra=c.co_tra " + strEmp + " and c.st_reg='A')" + "\n";
                strSql += " inner join tbm_emp d on (c.co_emp=d.co_emp)" + "\n";
                strSql += " left outer join tbm_motjusconasi e on (a.co_motJusMarInc=e.co_motjus)" + "\n";
                strSql += " inner join tbm_dethortra g on(c.co_hor=g.co_hor and g.ne_dia in(extract(dow from a.fe_dia)))" + "\n";
                strSql += " " + strDepAut + "\n";
                strSql += " " + strOfiAut + "\n";
                strSql += " " + strFilFec + "\n";
                strSql += " " + strMarInc + "\n";
                strSql += " " + sqlFilEmp + "\n";
                strSql += " group by c.co_emp,d.tx_nom,c.co_ofi,c.co_tra,c.co_hor,c.co_dep,empleado,fe_dia,a.ho_ent,a.ho_sal,g.ho_ent,g.ho_sal, st_jusMarInc," + "\n";
                strSql += " ho_saljus,ho_entjus,co_motjusMarInc,e.tx_deslar,tx_obsjusMarInc" + "\n";
                strSql += " order by fe_dia , empleado" + "\n";
          //}

                System.out.println("q consul: " + strSql);
                rstLoc = stmLoc.executeQuery(strSql);

                vecDat = new Vector();

                while (rstLoc.next()) {
                    if (blnCon){
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_CHKSEL, Boolean.FALSE);
                    vecReg.add(INT_TBL_DAT_CO_EMP, rstLoc.getInt("co_emp"));
                    vecReg.add(INT_TBL_DAT_NOM_EMP, rstLoc.getString("tx_nom"));
                    vecReg.add(INT_TBL_DAT_FECHA, rstLoc.getString("fe_dia"));
                    vecReg.add(INT_TBL_DAT_COD_TRA, rstLoc.getInt("co_tra"));
                    vecReg.add(INT_TBL_DAT_NOM_APE_TRA, rstLoc.getString("empleado"));
                    vecReg.add(INT_TBL_DAT_HOR_ENT_EST, rstLoc.getString("horaEntradaEstablecida").substring(0, 5));
                    vecReg.add(INT_TBL_DAT_HOR_SAL_EST, rstLoc.getString("horaSalidaEstablecida").substring(0, 5));

                    vecReg.add(INT_TBL_DAT_HOR_ENT_MAR, rstLoc.getString("ho_entEmp") == null ? "" : rstLoc.getString("ho_entEmp").substring(0, 5));
                    vecReg.add(INT_TBL_DAT_HOR_SAL_MAR, rstLoc.getString("ho_salEmp") == null ? "" : rstLoc.getString("ho_salEmp").substring(0, 5));

                    vecReg.add(INT_TBL_DAT_CHK_MAR_INC, rstLoc.getBoolean("blnTieMarInc"));

                    vecReg.add(INT_TBL_DAT_CHK_JUS_MAR_INC, rstLoc.getBoolean("blnJusMarInc"));

                    vecReg.add(INT_TBL_DAT_HOR_ENT_JUS, rstLoc.getString("ho_entJus") == null ? "" : rstLoc.getString("ho_entJus").substring(0, 5));
                    vecReg.add(INT_TBL_DAT_HOR_SAL_JUS, rstLoc.getString("ho_salJus") == null ? "" : rstLoc.getString("ho_salJus").substring(0, 5));

                    vecReg.add(INT_TBL_DAT_MOT, rstLoc.getString("tx_deslar"));
                    vecReg.add(INT_TBL_DAT_COD_MOT, rstLoc.getString("co_motJusMarInc"));
                    vecReg.add(INT_TBL_DAT_BUT_MOT, "");

                    vecReg.add(INT_TBL_DAT_OBS, rstLoc.getString("tx_obsJusMarInc"));
                    vecReg.add(INT_TBL_DAT_BUT_OBS, "");
                    vecReg.add(INT_TBL_DAT_ST_JUS_MAR_INC, rstLoc.getString("st_jus"));

                    vecDat.add(vecReg);
                    }else
                    break;
                }

                /**
                 * ********************************************************************************************************************************
                 */
                if (vecDat.size() == 0) {
                    mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                    lblMsgSis.setText("Listo");
                    objTblMod.removeAllRows();
                } else {
                    //Asignar vectores al modelo.
                    objTblMod.setData(vecDat);
                    tblDat.setModel(objTblMod);
                    //tabGen.setSelectedIndex(1);
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
                }
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                vecDat.clear();
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally{
            try {
                conn.close();
            } catch (SQLException ex) { }
        }
        System.gc();
        return blnRes;
    }

    private long calcularDiferenciaFechas(String strFecDes, String strFecHas) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
        strFecDes = strFecDes.replaceAll("-", "");
        strFecHas = strFecHas.replaceAll("-", "");
        int intFecDesAño = Integer.valueOf(strFecDes.substring(0, 4));
        int intFecDesMes = Integer.valueOf(strFecDes.substring(4, 6));;
        int intFecDesDia = Integer.valueOf(strFecDes.substring(6, 8));

        int intFecHasAño = Integer.valueOf(strFecHas.substring(0, 4));
        int intFecHasMes = Integer.valueOf(strFecHas.substring(4, 6));;
        int intFecHasDia = Integer.valueOf(strFecHas.substring(6, 8));

        Calendar calFecDes = new GregorianCalendar(intFecDesAño, intFecDesMes - 1, intFecDesDia);
        Calendar calFecHas = new GregorianCalendar(intFecHasAño, intFecHasMes - 1, intFecHasDia);

        java.sql.Date datFecDes = new java.sql.Date(calFecDes.getTimeInMillis());
        java.sql.Date datFecHas = new java.sql.Date(calFecHas.getTimeInMillis());

        return (datFecHas.getTime() - datFecDes.getTime()) / MILLSECS_PER_DAY;

    }

    private String[] incrementaDia() {

        long lngDif = calcularDiferenciaFechas(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()),
                objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
        int t = (int) lngDif;
        String[] info = new String[t + 1];
        String formato = "dd/MM/yyyy";

        try {

            SimpleDateFormat sdf = new SimpleDateFormat(formato);
            java.util.Date date = sdf.parse(objSelFec.getFechaDesde());
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.roll(Calendar.DAY_OF_YEAR, 1);
            boolean bandera = false;
            int d = 0;
            for (int intRecDifDia = 0; intRecDifDia <= lngDif; intRecDifDia++) {

                cal = Calendar.getInstance();
                cal.setTime(date);
                if (bandera) {
                    cal.add(Calendar.DAY_OF_YEAR, 1);
                }

                date = cal.getTime();
                SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
                StringBuilder nowYYYYMMDD = new StringBuilder(dateformatYYYYMMDD.format(date));
                info[intRecDifDia] = nowYYYYMMDD.toString();
                bandera = true;
                d++;
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return info;
    }

    private ArrayList<Integer> obtenerRubRolPag() {
        Connection conIns = null;
        Statement stmLoc = null;
        ResultSet rstLoc = null;
        ArrayList<Integer> arrLstRubRolPag = null;
        try {
            conIns = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (conIns != null) {
                strSQL = "";
                strSQL = "select distinct co_rub from tbm_rubRolPag where tx_tipRub like 'I' and st_reg like 'A' order by co_rub ";
                stmLoc = conIns.createStatement();
                rstLoc = stmLoc.executeQuery(strSQL);
                arrLstRubRolPag = new ArrayList<Integer>();
                while (rstLoc.next()) {
                    arrLstRubRolPag.add(rstLoc.getInt("co_rub"));
                }
            }
        } catch (SQLException ex) {
            objUti.mostrarMsgErr_F1(this, ex);
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                conIns.close();
            } catch (Throwable ignore) {
            }
        }
        return arrLstRubRolPag;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_CHKSEL:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_CO_EMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg = "Nombre de la empresa";
                    break;
                case INT_TBL_DAT_FECHA:
                    strMsg = "Fecha";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg = "Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg = "Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_HOR_ENT_EST:
                    strMsg = "Entrada";
                    break;
                case INT_TBL_DAT_HOR_SAL_EST:
                    strMsg = "Salida";
                    break;
                case INT_TBL_DAT_HOR_ENT_MAR:
                    strMsg = "Hora de entrada del empleado";
                    break;
                case INT_TBL_DAT_HOR_SAL_MAR:
                    strMsg = "Hora de salida del empleado";
                    break;
                case INT_TBL_DAT_CHK_MAR_INC:
                    strMsg = "¿Tiene marcación incompleta?";
                    break;
                case INT_TBL_DAT_CHK_JUS_MAR_INC:
                    strMsg = "¿Justificar la marcación incompleta?";
                    break;
                case INT_TBL_DAT_HOR_ENT_JUS:
                    strMsg = "Hora de entrada justificada";
                    break;
                case INT_TBL_DAT_HOR_SAL_JUS:
                    strMsg = "Hora de salida justificada";
                    break;
                case INT_TBL_DAT_MOT:
                    strMsg = "Motivo de la marcación incompleta";
                    break;
                case INT_TBL_DAT_COD_MOT:
                    strMsg = "Código del motivo";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg = "Observación de la marcación incompleta";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg = "";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean mostrarVenConEmp(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    } else {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        } else {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())) {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    } else {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton() == vcoEmp.INT_BUT_ACE) {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        } else {
                            txtNomEmp.setText(strNomEmp);
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

    private boolean mostrarVenConMot(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoMotJusAsi.setCampoBusqueda(2);
                    vcoMotJusAsi.show();
                    if (vcoMotJusAsi.getSelectedButton() == vcoMotJusAsi.INT_BUT_ACE) {
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoMotJusAsi.buscar("a1.co_mot", txtCodMot.getText())) {
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    } else {
                        vcoMotJusAsi.setCampoBusqueda(0);
                        vcoMotJusAsi.setCriterio1(11);
                        vcoMotJusAsi.cargarDatos();
                        vcoMotJusAsi.show();
                        if (vcoMotJusAsi.getSelectedButton() == vcoMotJusAsi.INT_BUT_ACE) {
                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                        } else {
                            txtCodMot.setText(strCodMotJus);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMotJusAsi.buscar("a1.tx_deslar", txtDesMot.getText())) {
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    } else {
                        vcoMotJusAsi.setCampoBusqueda(1);
                        vcoMotJusAsi.setCriterio1(11);
                        vcoMotJusAsi.cargarDatos();
                        vcoMotJusAsi.show();
                        if (vcoMotJusAsi.getSelectedButton() == vcoMotJusAsi.INT_BUT_ACE) {
                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                        } else {
                            txtDesMot.setText(strDesMot);
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
    private boolean mostrarVenConOfi(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(2);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText())) {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    } else {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        } else {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText())) {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    } else {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        } else {
                            txtNomOfi.setText(strDesLarOfi);
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

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        } else {
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp() {
        boolean blnRes = true;
        String strTitVenCon = "";
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }
            if (objParSis.getCodigoUsuario() == 1) {
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1";
                    strSQL += " WHERE a1.co_emp in (" + objParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }

            } else {
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.tx_nom";
                    strSQL += " FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL += " WHERE a1.co_emp in (" + objParSis.getCodigoEmpresa() + ")" + "";
                    strSQL += " AND a1.st_reg NOT IN('I','E')";
                    strSQL += " ORDER BY a1.co_emp";
                }
            }
            vcoEmp = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
    private boolean mostrarVenConDep(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtNomDep.setText(strDesLarDep);
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
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");

            String strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objParSis.getCodigoMenu() + ")";
            }

            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");

            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "SELECT co_ofi,tx_nom,st_reg FROM tbm_ofi WHERE st_reg LIKE 'A' ORDER BY co_ofi";
            } else {
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                 "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL = "select co_ofi,tx_nom,st_reg FROM tbm_ofi WHERE co_ofi IN(SELECT co_ofi FROM tbr_ofiLocPrgUsr where co_emp = " + objParSis.getCodigoEmpresa() + " and co_usr=" + objParSis.getCodigoUsuario() + " "
                        + "and co_mnu=" + objParSis.getCodigoMenu() + ")";
            }

            vcoOfi = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Oficinas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");

            String strSQL = "";
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                strSQL += "SELECT a.co_tra,a.tx_ape,a.tx_nom " + "\n";
                strSQL += "FROM tbm_tra a  " + "\n";
                strSQL += "INNER JOIN tbm_traemp b on(b.co_tra=a.co_tra AND b.st_reg = 'A')  " + "\n";
                strSQL += "WHERE  b.co_emp= " + objParSis.getCodigoEmpresa() + "\n";
                strSQL += "AND b.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr" + "\n";
                strSQL += "WHERE co_emp=" + objParSis.getCodigoEmpresa() + "\n";
                strSQL += "AND co_usr = " + objParSis.getCodigoUsuario() + "\n";
                strSQL += "AND co_loc=" + objParSis.getCodigoLocal() + "\n";
                strSQL += "AND co_mnu=" + objParSis.getCodigoMenu() + ") " + "\n";
                strSQL += "ORDER BY (a.tx_ape || ' ' || a.tx_nom)";
            }

            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada
     * para mostrar las "Cuentas".
     */
    private boolean configurarVenConJustificarMotivos() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_motjus");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("120");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL += "SELECT a1.co_motjus,a1.tx_desLar";
            strSQL += " FROM tbm_motjusconasi AS a1";
            strSQL += " WHERE a1.st_reg like 'A'";
            strSQL += " ORDER BY a1.co_motjus";

            vcoMotJusAsi = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de motivos de justificaciones de asistencias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoMotJusAsi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoMotJusAsi.setCampoBusqueda(1);
            vcoMotJusAsi.setCriterio1(7);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    //DAO
    public double getValorDevolver(Connection conex, String coEmpresa, String coTra, String fecAJustificar) {
        double valorDevolver = 0;
        try {
            String strQurey = "select 1, ((nd_valrub / 30) /8) * 2 as valDes, ne_ani, ne_mes, ne_per  from tbm_fecCorRolPag f, tbm_suetra s, tbm_cabconasitra a \n"
                    + "where f.co_emp = " + coEmpresa + " and s.co_tra = " + coTra + " and co_rub = 1\n"
                    + "	and " + fecAJustificar + " between fe_des and fe_has\n"
                    + "	and " + fecAJustificar + " = fe_dia \n"
                    + "	and f.co_emp = s.co_emp and s.co_tra = a.co_tra \n"
                    + "	and st_revfal = 'S' and st_jusmarinc is null";
            CallableStatement cs = conex.prepareCall(strQurey);
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                double valor = rs.getDouble("valDes");
                if (rs.getInt("ne_per") == 2) {
                    valorDevolver = valor;
                } else {
                    strQurey = "select 1 from tbm_cabRolPag where ne_ani=" + rs.getInt("ne_ani") + " and ne_mes=" + rs.getInt("ne_mes")
                            + " and ne_per = 2 " + " and co_emp = " + coEmpresa + "and st_reg = 'A' and co_tipdoc = 192";
                    cs = conex.prepareCall(strQurey);
                    rs = cs.executeQuery();
                    if (rs.next()) {
                        valorDevolver = valor;
                    }
                }
            }
            cs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorDevolver;
    }

    public boolean grabarIngProgramado(Connection conex, String coEmp, String coTra, double valor, String txObs2) {
        boolean resp = false;
        try {
            int intCoEgr = 0;
            String strSQL = "SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_egr  FROM tbm_cabingegrprgtra WHERE "
                    + " co_emp=" + coEmp;
            java.sql.Statement stmLoc = conex.createStatement();
            ResultSet rs = stmLoc.executeQuery(strSQL);
            if (rs.next()) {
                intCoEgr = rs.getInt("co_egr");
            }

            String strTx_tipEgrPrg = "D";
            String strTx_tipCuo = "M";
            String strNeNumCuo = "1"; //registo activo
            String strSt_Reg = "A"; //registo activo

            String strSql = "";
            strSql += "INSERT INTO tbm_cabingegrprgtra (co_emp , co_egr , co_tra , co_rub , fe_doc , " + "\n";
            strSql += "tx_tipegrprg , fe_pricuo , fe_ultcuo , nd_valegrprg , ne_numcuo , tx_tipcuo , st_reg , fe_ing , co_usring, tx_obs2 ) " + "\n";
            strSql += "VALUES ( " + coEmp + " , " + intCoEgr + " , " + coTra + " , 22, current_date" + " , \n";
            strSql += objUti.codificar(strTx_tipEgrPrg) + " , \n";
            Calendar cal = Calendar.getInstance();
            String strFePriCuo = cal.get(Calendar.YEAR) + "-" + ((cal.get(Calendar.MONTH)) + 1) + "-" + "01";
            strSql += objUti.codificar(strFePriCuo) + " , \n";
            strSql += objUti.codificar(strFePriCuo) + " , \n";
            strSql += valor + " , \n";
            strSql += objUti.codificar(strNeNumCuo) + " , \n";
            strSql += objUti.codificar(strTx_tipCuo) + " , \n";
            strSql += objUti.codificar(strSt_Reg) + " , \n";
            strSql += "current_timestamp" + " , \n";
            strSql += objParSis.getCodigoUsuario() + ",'" + txObs2 + "' );";
            stmLoc.executeUpdate(strSql);

            strSql = "";
            strSql += "INSERT INTO tbm_detingegrprgtra (co_emp , co_egr , co_reg , fe_cuo , nd_valcuo , st_reg)" + "\n";
            strSql += "VALUES ( " + coEmp + " , " + intCoEgr + " , 1 , " + objUti.codificar(strFePriCuo) + " , \n";
            strSql += valor + " , " + objUti.codificar(strSt_Reg) + " );";
            stmLoc.executeUpdate(strSql);
            resp = true;
        } catch (SQLException ex) {
            resp = false;
            ex.printStackTrace();
        }
        return resp;
    }

    /**
     * Esta función permite validar si se puede justificar la fecha recibida como parámetro.
     * Se solicitó que sólo se pueda justificar hasta 1 semana hacia atrás.
     * @param strFec Fecha que se va a validar.
     * @return true: Si se puede justificar.
     * <BR>false: En el caso contrario.
     */
    public boolean verificarFecha(String strFec)
    {
        if (objParSis.getCodigoUsuario()==1)
        {
            return true;
        }
        boolean resp=true;
        Calendar calFecAct=getFecServidor();
        calFecAct.add(Calendar.DATE, -7);
        //Eddye: Para poder autorizat 30 días hacia atrás.
        //calFecAct.add(Calendar.DATE, -30);
        Calendar calFecReg = Calendar.getInstance();
        int intAni=Integer.valueOf(strFec.split("-")[0]);
        int intMes=Integer.valueOf(strFec.split("-")[1]);
        int intDia=Integer.valueOf(strFec.split("-")[2]);
        calFecReg.set(Calendar.YEAR, intAni);
        calFecReg.set(Calendar.MONTH, intMes-1);
        calFecReg.set(Calendar.DATE, intDia);
        if (calFecAct.getTimeInMillis()>calFecReg.getTimeInMillis())
        {
            mostrarMsgInf("Sólo se puede justificar 7 días hacia atrás.");
            resp=false;
        }
        return resp;
    }
    
    public Calendar getFecServidor() {
        String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(),
                objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), "dd/M/yyyy hh:mm:ss");
        String fec = strFecSis.split(" ")[0];
        String hor = strFecSis.split(" ")[1];
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(fec.split("/")[2]), Integer.valueOf(fec.split("/")[1]) -1, Integer.valueOf(fec.split("/")[0]),
                Integer.valueOf(hor.split(":")[0]), Integer.valueOf(hor.split(":")[1]), Integer.valueOf(hor.split(":")[2]));
        System.out.print( "Fec: " + cal.getTime() );
        return cal;
    }    
}
