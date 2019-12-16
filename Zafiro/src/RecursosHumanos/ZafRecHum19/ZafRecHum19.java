

package RecursosHumanos.ZafRecHum19;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
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
import RecursosHumanos.ZafRecHum53.ZafRecHum53;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Justificaciones de atrasos.
 * @author  Roberto Flores
 * Guayaquil 17/09/2012
 */
public class ZafRecHum19 extends javax.swing.JInternalFrame
{
    
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_CHKSEL=1;
    private static final int INT_TBL_DAT_CO_EMP=2;
    private static final int INT_TBL_DAT_NOM_EMP=3;
    private static final int INT_TBL_DAT_FECHA=4;
    private static final int INT_TBL_DAT_COD_TRA=5;
    private static final int INT_TBL_DAT_NOM_APE_TRA=6;
    private static final int INT_TBL_DAT_HOR_ENT_EST=7;
    private static final int INT_TBL_DAT_HOR_SAL_EST=8;
    private static final int INT_TBL_DAT_HOR_ENT_MAR=9;
    private static final int INT_TBL_DAT_HOR_SAL_MAR=10;
    private static final int INT_TBL_DAT_CHK_ATR=11;
    private static final int INT_TBL_DAT_HOR_TIEMPO_ATRASO=12;
    private static final int INT_TBL_DAT_CHK_JUS_ATR=13;
    private static final int INT_TBL_DAT_HOR_TIE_ATRASO_JUS=14;
    private static final int INT_TBL_DAT_HOR_ENT_JUS=15;
    private static final int INT_TBL_DAT_MOT=16;
    private static final int INT_TBL_DAT_COD_MOT=17;
    private static final int INT_TBL_DAT_BUT_MOT=18;
    private static final int INT_TBL_DAT_OBS=19;
    private static final int INT_TBL_DAT_BUT_OBS=20;
    private static final int INT_TBL_DAT_ST_JUSTATR=21;
    

    

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafTblEdi objTblEdi;                                                        //Editor: Editor del JTable.
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                                            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                                            //Editor: JTextField en celda.
    private ZafTblCelEdiChk zafTblCelEdiChkLab;                                         //Editor de Check Box para campo Laborable
    private ZafTblCelRenChk zafTblCelRenChkLab;                                         //Renderer de Check Box para campo Laborable
    private boolean blnCon;                                                             //true: Continua la ejecución del hilo.
    private ZafTblCelEdiChk objTblCelEdiChk;                                            //Editor de Check Box 
    private ZafTblCelEdiChk objTblCelEdiChk2;                                           //Editor de Check Box 
    private ZafTblCelRenChk objTblCelRenChkLab;                                         //Renderer de Check Box
    
    private ZafMouMotAda objMouMotAda;                                                  //ToolTipText en TableHeader.
//    private ZafMouMotAdaMovReg objMouMotAdaMovReg;                                    //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                                                  //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblTot objTblTot;                                                        //JTable de totales.
    private ZafVenCon vcoEmp;                                                           //Ventana de consulta.
    private ZafVenCon vcoDep;                                                           //Ventana de consulta.
    private ZafVenCon vcoTra;
    private ZafVenCon vcoMotJusAsi;                                                     //Ventana de consulta.
    private ZafVenCon vcoOfi;                                                           //Ventana de consulta.
    
    private ZafTblCelRenBut objTblCelRenBut;                                           //Render: Presentar JButton en JTable.

    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        

    private String strCodEmp, strNomEmp;
    private String strCodDep = "";
    private String strDesLarDep = "";
    private String strCodTra = "";
    private String strNomTra = "";
    private String strCodMotJus, strDesMot;
    private String strCodOfi = "";
    private String strDesLarOfi = "";

    static final int INT_TBL_DAT_NUM_TOT_CDI=22;                            //Número total de columnas dinámicas.
    
    private ZafPerUsr objPerUsr;
    private ZafTblBus objTblBus;
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoCta;                                   //Editor: JButton en celda.
    
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVco;           //Editor: JTextField de consulta en celda.
    
    private String strVersion="v1.10";
        
    /** Crea una nueva instancia de la clase ZafRecHum19. */
    public ZafRecHum19(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            
            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);
            
            if(objPerUsr.isOpcionEnabled(2910)){
                butCon.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(2911)){
                butGua.setVisible(true);
            }
            if(objPerUsr.isOpcionEnabled(2912)){
                butCer.setVisible(true);
            }
            
            
            
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e)
        {
            {objUti.mostrarMsgErr_F1(this, e);}
        }
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
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
        jChkEmpTieAtra = new javax.swing.JCheckBox();
        jChkEmpNoTieAtr = new javax.swing.JCheckBox();
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
        txtHorEntJus = new javax.swing.JTextField();
        txtTieJus = new javax.swing.JTextField();
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
        optFil.setText("Sólo los empleados que cumplan con el criterio seleccionado");
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

        jChkEmpTieAtra.setSelected(true);
        jChkEmpTieAtra.setText("Mostrar los empleados que tienen atrasos");
        jChkEmpTieAtra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpTieAtraActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpTieAtra);
        jChkEmpTieAtra.setBounds(24, 196, 400, 23);

        jChkEmpNoTieAtr.setText("Mostrar los empleados que no tienen atrasos");
        jChkEmpNoTieAtr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jChkEmpNoTieAtrActionPerformed(evt);
            }
        });
        panFil.add(jChkEmpNoTieAtr);
        jChkEmpNoTieAtr.setBounds(24, 216, 400, 23);

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

        jLabel1.setText("Tiempo justificado:");
        panCabRpt.add(jLabel1);
        jLabel1.setBounds(20, 10, 90, 14);

        jLabel2.setText("Hora de entrada justificada:");
        panCabRpt.add(jLabel2);
        jLabel2.setBounds(20, 30, 180, 14);

        jLabel3.setText("Motivo:");
        panCabRpt.add(jLabel3);
        jLabel3.setBounds(20, 50, 36, 14);

        jLabel7.setText("Observación:");
        panCabRpt.add(jLabel7);
        jLabel7.setBounds(20, 80, 64, 14);

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
        txtHorEntJus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtHorEntJusKeyPressed(evt);
            }
        });
        panCabRpt.add(txtHorEntJus);
        txtHorEntJus.setBounds(200, 30, 80, 20);

        txtTieJus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTieJusActionPerformed(evt);
            }
        });
        txtTieJus.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTieJusFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTieJusFocusLost(evt);
            }
        });
        txtTieJus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTieJusKeyPressed(evt);
            }
        });
        panCabRpt.add(txtTieJus);
        txtTieJus.setBounds(200, 10, 80, 20);

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
                blnCon=true;
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }else{
            blnCon=false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private boolean validaDiasConsulta(){
        boolean blnRes=true;
        
        try{
            
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
    //        int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
    //        if(fecDoc!=null){
    //            objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
    //            objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
    //            objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
    //        }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            
            if(objParSis.getCodigoUsuario()!=1){
                objFecPagActual.add(java.util.Calendar.DATE, -7 );
            }

            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            
        }catch(Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if(!verificaUsuario()){
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0){
                        if(guardarDat())
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                        else
                            mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
            }else{
                mostrarMsgInf("Usuario no está autorizado para realizar sus propias justificaciones.");
                objThrGUI=null;
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }else{
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
        }
    }//GEN-LAST:event_butGuaActionPerformed
   
    private boolean verificaUsuario(){
        boolean blnRes=false;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            
            if(con!=null){
                
                stmLoc = con.createStatement();
                
                if(objParSis.getCodigoUsuario()!=1){
            
                    for(int i=0; i<tblDat.getRowCount();i++){
                        
                        if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
                    
                            System.out.println("empleado que se intenta justificar: " + Integer.valueOf(tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString()));

                            String strSql="select c.co_usr, c.tx_usr,co_tra";
                            strSql+=" from ";
                            strSql+=" (select d.co_usr, upper(d.tx_usr) as tx_usr ";
                            strSql+=" from tbm_usr as d ";
                            strSql+=" where d.st_reg='A') as c, ";
                            strSql+=" (select b.co_dep, a.co_tra, trim(substr(a.tx_nom,1,1) || substr(a.tx_ape, 1, case when position(' ' in a.tx_ape) = 0 then length(a.tx_ape) else position(' ' in a.tx_ape) end)) as tx_usr ";
                            strSql+=" from tbm_tra as a ";
                            strSql+=" inner join tbm_traemp as b on (b.co_tra=a.co_tra and b.st_reg='A') ";
                            strSql+=" where a.st_reg='A') as e ";
                            strSql+=" where c.tx_usr=e.tx_usr";
                            strSql+=" and e.tx_usr="+objUti.codificar(objParSis.getNombreUsuario().toUpperCase());
                            System.out.println("usuario jus: " + strSql);

                            rstLoc=stmLoc.executeQuery(strSql);
                            
                            if(rstLoc.next()){
                                if(tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString().compareTo(rstLoc.getString("co_tra"))==0){
                                    return true;
                                }
                            }
                            
                        }
                    }
                }
            }
        }catch(Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }finally{
            try{stmLoc.close();}catch(Throwable ignore){}
            try{rstLoc.close();}catch(Throwable ignore){}
            try{con.close();}catch(Throwable ignore){}
        }
        return blnRes;
    }
    
    private boolean guardarDat(){
        boolean blnRes=true;
        java.sql.Connection con = null; 
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSql="";
        
        try{
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
      
            if(con!=null){
                
                con.setAutoCommit(false);
                stmLoc=con.createStatement();
                
                for(int i=0; i<tblDat.getRowCount();i++){
                    System.out.println("i = " + i + " ---> " + tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString());
                    if(tblDat.getValueAt(i, INT_TBL_DAT_LIN).toString().compareTo("M")==0){
                        
                        if( !verificarFecha( objTblMod.getValueAt(i, INT_TBL_DAT_FECHA).toString()) ){
                            blnRes = false;
                            break;
                        }
                        
                        Object objCodMot=objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT);
                        String strCoMot="";
                        if(objCodMot!=null){
                            strCoMot=objTblMod.getValueAt(i, INT_TBL_DAT_COD_MOT).toString();
                            if(strCoMot.equals("")){
                                strCoMot="null";
                            }
                        }else{
                            strCoMot="null";
                        }                        
                        
                        String strJusFal="";
                        if(objTblMod.getValueAt(i, INT_TBL_DAT_CHK_JUS_ATR).equals(true)){
                            strJusFal=objUti.codificar("S");
                        }else{
                            strJusFal="null";
                        }
                        if(objTblMod.getValueAt(i, INT_TBL_DAT_ST_JUSTATR).toString().compareTo("0") != 0){
                            mostrarMsgInf("No puede Desjustificar un atraso");
                            blnRes = false ;
                            break;
                        }
                        //David
                        if(objTblMod.getValueAt(i, INT_TBL_DAT_CHK_JUS_ATR).equals(true)){
                            double valorDevolver = getValorDevolver(con, objTblMod.getValueAt(i, INT_TBL_DAT_CO_EMP).toString(),
                                     objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString(), 
                                     objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA)),  strJusFal);
                             valorDevolver = objUti.redondear(valorDevolver, objParSis.getDecimalesMostrar());
                             if (valorDevolver > 0){
                                 String txObs = "Zafiro: Justificación Entradas (" + tblDat.getValueAt(i, INT_TBL_DAT_FECHA).toString() + ")" ;
                                 if (!grabarIngProgramado(con,  objTblMod.getValueAt(i, INT_TBL_DAT_CO_EMP).toString(),
                                     objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString(), valorDevolver, txObs)){
                                    blnRes = false ;
                                    break;
                                 }
                             }
                        }
                        
                        strSql="";
                        strSql+="select * from tbm_cabconasitra";
                        strSql+=" where co_tra=" + tblDat.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                        strSql+=" and fe_dia="+objUti.codificar(tblDat.getValueAt(i, INT_TBL_DAT_FECHA).toString());
                        rstLoc=stmLoc.executeQuery(strSql);
                        
                        
                        
                        if(rstLoc.next()){
                            strSql="";
                            strSql+="UPDATE tbm_cabconasitra SET";
                            strSql+=" ho_entjus= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_ENT_JUS)) +" , ";
                            //strSql+=" ho_saljus= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_SAL_JUS)) +" , ";
                            strSql+=" st_jusAtr= "+ strJusFal +" , ";
                            strSql+=" co_motjusAtr= "+ strCoMot  +" , ";
                            strSql+=" tx_obsjusAtr= "+ objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) +" , ";
                            strSql+=" fe_jusAtr= current_timestamp" + " , ";
                            strSql+=" co_usrjusAtr= " + objParSis.getCodigoUsuario() +" , ";
                            strSql+=" tx_comjusAtr= " + objUti.codificar(objParSis.getDireccionIP());
                            strSql+=" WHERE";
                            strSql+=" co_tra= " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString();
                            strSql+=" and fe_dia= " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA));
                            System.out.println("act atraso:" + strSql);
                            stmLoc.executeUpdate(strSql);
                        }else{
                            strSql="";
                            strSql+="INSERT INTO tbm_cabconasitra(";
                            strSql+=" co_tra, fe_dia, st_jusatr, tx_obsjusatr, fe_jusatr,";
                            strSql+=" co_usrjusatr, tx_comjusatr,  ho_entjus,";
                            strSql+=" co_motjusatr)";
                            strSql+=" VALUES (";
                            strSql+= objTblMod.getValueAt(i, INT_TBL_DAT_COD_TRA).toString() + " , ";
                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_FECHA)) + " , ";
                            strSql+= strJusFal + " , ";
                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " , ";
                            strSql+=" current_timestamp" + " , ";
                            strSql+= objParSis.getCodigoUsuario() + " , ";
                            strSql+= objUti.codificar(objParSis.getDireccionIP()) + " , ";
                            strSql+= objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_HOR_ENT_JUS)) +" , ";
                            strSql+= strCoMot  +")";
                            System.out.println("insert atraso:" + strSql);
                            stmLoc.executeUpdate(strSql);
                        }
                    }
                }
                
                if(blnRes){
                    con.commit();
                    new RRHHDao(objUti, objParSis).callServicio9();
                    //Inicializo el estado de las filas.
                    objTblMod.initRowsState();
                    objTblMod.setDataModelChanged(false);
                }
            }
        }catch(java.sql.SQLException Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }catch(Exception Evt) {
            try {
                con.rollback();
            } catch (SQLException ex) {
                objUti.mostrarMsgErr_F1(this, Evt);
            }
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        }finally {
        try{stmLoc.close();}catch(Throwable ignore){}
        try{rstLoc.close();}catch(Throwable ignore){}
        try{con.close();}catch(Throwable ignore){}
        }
    return blnRes;
}
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
        jChkEmpTieAtra.setSelected(false);
        jChkEmpNoTieAtr.setSelected(false);
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
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
   }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
    if (txtNomEmp.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
            {
                if (txtNomEmp.getText().equals(""))
                {
                    txtCodEmp.setText("");
                    txtNomEmp.setText("");
                }
                else
                {
                    mostrarVenConEmp(2);
                }
            }
            else
                txtNomEmp.setText(strNomEmp);
        }
   }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        strCodEmp=txtCodEmp.getText();
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

     public void BuscarOfi(String campo,String strBusqueda,int tipo){
        
        vcoOfi.setTitle("Listado de Oficinas");
        if(vcoOfi.buscar(campo, strBusqueda )) {
            txtCodOfi.setText(vcoOfi.getValueAt(1));
            txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
            vcoOfi.setCampoBusqueda(tipo);
            vcoOfi.cargarDatos();
            vcoOfi.show();
            if (vcoOfi.getSelectedButton()==vcoOfi.INT_BUT_ACE) {
                txtCodOfi.setText(vcoOfi.getValueAt(1));
                txtNomOfi.setText(vcoOfi.getValueAt(2));
        }else{
                txtCodOfi.setText(strCodOfi);
                txtNomOfi.setText(strDesLarOfi);
  }}}
    
    public void BuscarEmp(String campo,String strBusqueda,int tipo){
        
        vcoEmp.setTitle("Listado de Empresas");
        if(vcoEmp.buscar(campo, strBusqueda )) {
            txtCodEmp.setText(vcoEmp.getValueAt(1));
            txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
            vcoEmp.setCampoBusqueda(tipo);
            vcoEmp.cargarDatos();
            vcoEmp.show();
            if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE) {
                txtCodEmp.setText(vcoEmp.getValueAt(1));
                txtNomEmp.setText(vcoEmp.getValueAt(2));
        }else{
                txtCodEmp.setText(strCodEmp);
                txtNomEmp.setText(strNomEmp);
  }}}
    
    public void BuscarDep(String campo,String strBusqueda,int tipo){
        
        vcoDep.setTitle("Listado de Departamentos");
        if(vcoDep.buscar(campo, strBusqueda )) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton()==vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtNomDep.setText(vcoDep.getValueAt(3));
        }else{
                txtCodDep.setText(strCodDep);
                txtNomDep.setText(strDesLarDep);
  }}}
    
    public void BuscarTra(String campo,String strBusqueda,int tipo){
        
        vcoTra.setTitle("Listado de Empleados");
        if(vcoTra.buscar(campo, strBusqueda )) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        }else{
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
  }}}
    
    public void BuscarMot(String campo,String strBusqueda,int tipo){
        
        vcoMotJusAsi.setTitle("Listado de Motivos");
        if(vcoMotJusAsi.buscar(campo, strBusqueda )) {
            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
        }else{
            vcoMotJusAsi.setCampoBusqueda(tipo);
            vcoMotJusAsi.cargarDatos();
            vcoMotJusAsi.show();
            if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE) {
                txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
        }else{
                txtCodMot.setText(strCodDep);
                txtDesMot.setText(strDesLarDep);
  }}}
    
    private void txtNomDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDepActionPerformed
        // TODO add your handling code here:
        txtNomDep.transferFocus();
    }//GEN-LAST:event_txtNomDepActionPerformed

    private void txtNomDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusGained
        // TODO add your handling code here:
        strDesLarDep=txtNomDep.getText();
        txtNomDep.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
        
    }//GEN-LAST:event_txtNomDepFocusGained

    private void txtNomDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDepFocusLost
        // TODO add your handling code here:
        if (txtNomDep.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomDep.getText().equalsIgnoreCase(strDesLarDep))
            {
                if (txtNomDep.getText().equals(""))
                {
                    txtCodDep.setText("");
                    txtNomDep.setText("");
                }
                else
                {
                    mostrarVenConDep(2);
                }
            }
            else
                txtNomDep.setText(strDesLarDep);
        }
    }//GEN-LAST:event_txtNomDepFocusLost

    private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
        strCodDep=txtCodDep.getText();
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
        strNomTra=txtNomTra.getText();
        txtNomTra.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra))
            {
                if (txtNomTra.getText().equals(""))
                {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                }
                else
                {
                    mostrarVenConTra(2);
                }
            }
            else
                txtNomTra.setText(strNomTra);
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed

        strCodTra=txtCodTra.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConTra(0);
    }//GEN-LAST:event_butTraActionPerformed

    private void jChkEmpNoTieAtrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpNoTieAtrActionPerformed
        // TODO add your handling code here:
        jChkEmpTieAtra.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpNoTieAtrActionPerformed

    private void txtHorEntJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHorEntJusActionPerformed

        // TODO add your handling code here:
        txtCodMot.requestFocus();
        txtCodMot.selectAll();
    }//GEN-LAST:event_txtHorEntJusActionPerformed

    private void txtHorEntJusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorEntJusFocusGained
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtHorEntJusFocusGained

    private void txtHorEntJusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHorEntJusFocusLost
        // TODO add your handling code here:
        
        try{
            txtHorEntJus.setText(txtHorEntJus.getText().trim());
            if(txtHorEntJus.getText().compareTo("")!=0){
                String strHorIng = objUti.parseString(txtHorEntJus.getText());

                int intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                int intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));

                if((intHH>=0 && intHH<=24)){
                    if(!(intMM>=0 && intMM<=59)){
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        if(strHorIng.length()==5){
                            strHorIng = strHorIng + ":00";
                        }
                        java.sql.Time t = SparseToTime(strHorIng);
                    }
                }
                else{
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtHorEntJus.setText("");
                txtHorEntJus.requestFocus();
                }
            }
        }catch(Exception e){
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Hora de salida justificada contiene formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtHorEntJus.setText("");
            txtHorEntJus.requestFocus();
        }
        
    }//GEN-LAST:event_txtHorEntJusFocusLost

    private void txtTieJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTieJusActionPerformed
        // TODO add your handling code here:
        txtHorEntJus.requestFocus();
        txtHorEntJus.selectAll();
    }//GEN-LAST:event_txtTieJusActionPerformed

    private void txtTieJusFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTieJusFocusGained
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_txtTieJusFocusGained

    private void txtTieJusFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTieJusFocusLost
        // TODO add your handling code here:
        try{
            txtTieJus.setText(txtTieJus.getText().trim());
            if(txtTieJus.getText().compareTo("")!=0){
                String strHorIng = objUti.parseString(txtTieJus.getText());

                int intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                int intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));

                if((intHH>=0 && intHH<=24)){
                    if(!(intMM>=0 && intMM<=59)){
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        if(strHorIng.length()==5){
                            strHorIng = strHorIng + ":00";
                        }
                        java.sql.Time t = SparseToTime(strHorIng);
                    }
                }
                else{
                String strTit = "Mensaje del sistema Zafiro";
                String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
                JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                txtTieJus.setText("");
                txtTieJus.requestFocus();
                }
            }
        }catch(Exception e){
            String strTit = "Mensaje del sistema Zafiro";
            String strMen = "Hora de entrada justificada contiene formato erroneo. Revisar e intentar nuevamente.";
            JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
            txtTieJus.setText("");
            txtTieJus.requestFocus();
        }
    }//GEN-LAST:event_txtTieJusFocusLost

    private void butJusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butJusActionPerformed

        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
        {
              
            for(int intFil=0; intFil < tblDat.getRowCount(); intFil++){
            
                if(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHKSEL).equals(true)){

                    objTblMod.setValueAt(Boolean.TRUE, intFil, INT_TBL_DAT_CHK_JUS_ATR);
                    objTblMod.setValueAt(txtTieJus.getText(), intFil, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                    objTblMod.setValueAt(txtHorEntJus.getText(), intFil, INT_TBL_DAT_HOR_ENT_JUS);
                    objTblMod.setValueAt(txtCodMot.getText(), intFil, INT_TBL_DAT_COD_MOT);
                    objTblMod.setValueAt(txtDesMot.getText(), intFil, INT_TBL_DAT_MOT);
                    objTblMod.setValueAt(txtObs1.getText(), intFil, INT_TBL_DAT_OBS);

                }
            }
        }
    }//GEN-LAST:event_butJusActionPerformed

    private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
        // TODO add your handling code here:
        
        strCodMotJus=txtCodMot.getText();

        mostrarVenConMot(0);
        
    }//GEN-LAST:event_butMotActionPerformed

    private void jChkEmpTieAtraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jChkEmpTieAtraActionPerformed
        // TODO add your handling code here:
        jChkEmpNoTieAtr.setSelected(Boolean.FALSE);
    }//GEN-LAST:event_jChkEmpTieAtraActionPerformed

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
        strDesMot=txtDesMot.getText();
        txtDesMot.selectAll();
    }//GEN-LAST:event_txtDesMotFocusGained

    private void txtDesMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesMotFocusLost
        // TODO add your handling code here:
        if (txtDesMot.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtDesMot.getText().equalsIgnoreCase(strDesMot))
            {
                if (txtDesMot.getText().equals(""))
                {
                    txtCodMot.setText("");
                    txtDesMot.setText("");
                }
                else
                {
                    mostrarVenConMot(2);
                }
            }
            else
            txtDesMot.setText(strDesMot);
        }
    }//GEN-LAST:event_txtDesMotFocusLost

    private void txtTieJusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTieJusKeyPressed
        if(txtHorEntJus.getText().length()>0){
            txtHorEntJus.setText("");
            //txtTieJus.selectAll();
        }
    }//GEN-LAST:event_txtTieJusKeyPressed

    private void txtHorEntJusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtHorEntJusKeyPressed
        if(txtTieJus.getText().length()>0){
            //txtHorEntJus.selectAll();
            txtTieJus.setText("");
        }
    }//GEN-LAST:event_txtHorEntJusKeyPressed

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
        strDesLarOfi=txtNomOfi.getText();
        txtNomOfi.selectAll();
        optFil.setSelected(true);
        optTod.setSelected(false);
    }//GEN-LAST:event_txtNomOfiFocusGained

    private void txtNomOfiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomOfiFocusLost
        // TODO add your handling code here:
        if (txtNomOfi.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomOfi.getText().equalsIgnoreCase(strDesLarOfi))
            {
                if (txtNomOfi.getText().equals(""))
                {
                    txtCodOfi.setText("");
                    txtNomOfi.setText("");
                }
                else
                {
                    mostrarVenConOfi(2);
                }
            }
            else
            txtNomOfi.setText(strDesLarOfi);
        }
    }//GEN-LAST:event_txtNomOfiFocusLost

    private void butOfiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butOfiActionPerformed
        strCodOfi=txtCodOfi.getText();
        optFil.setSelected(true);
        optTod.setSelected(false);
        mostrarVenConOfi(0);
    }//GEN-LAST:event_butOfiActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
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
    private javax.swing.JCheckBox jChkEmpNoTieAtr;
    private javax.swing.JCheckBox jChkEmpTieAtra;
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
    private javax.swing.JTextField txtNomDep;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomOfi;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextArea txtObs1;
    private javax.swing.JTextField txtTieJus;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " " + strVersion);
            lblTit.setText(strAux);
            
            //Configurar las ZafVenCon.
            configurarVenConDep();
            configurarVenConTra();
            configurarVenConEmp();
            configurarVenConJustificarMotivos();
            configurarVenConOfi();

            //Configurar los JTables.
            configuraTbl();
            agregarColTblDat();
            
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Rango de fechas");
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panCab.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)this.getParent()),"d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag =  new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(),"d/m/y");
            int fecDoc [] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if(fecDoc!=null){
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
            objFecPagActual.add(java.util.Calendar.DATE, -30 );
            dtePckPag.setAnio( objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes( objFecPagActual.get(java.util.Calendar.MONTH)+1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(),"dd/MM/yyyy","yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha,"yyyy/MM/dd");

            objSelFec.setFechaDesde( objUti.formatearFecha(fe1, "dd/MM/yyyy") );
            //*******************************************************************************

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configuraTbl(){
        
        boolean blnRes=false;
        
        try{
            
        
        
        //Configurar JTable: Establecer el modelo.
        vecCab = new Vector();
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_LIN,"");
        vecCab.add(INT_TBL_DAT_CHKSEL,"");
        vecCab.add(INT_TBL_DAT_CO_EMP,"Cód.Emp.");
        vecCab.add(INT_TBL_DAT_NOM_EMP,"Empresa");
        vecCab.add(INT_TBL_DAT_FECHA,"Fecha");
        vecCab.add(INT_TBL_DAT_COD_TRA,"Código");
        vecCab.add(INT_TBL_DAT_NOM_APE_TRA,"Empleado");
        vecCab.add(INT_TBL_DAT_HOR_ENT_EST,"Entrada");
        vecCab.add(INT_TBL_DAT_HOR_SAL_EST,"Salida");
        vecCab.add(INT_TBL_DAT_HOR_ENT_MAR,"Entrada");
        vecCab.add(INT_TBL_DAT_HOR_SAL_MAR,"Salida");
        vecCab.add(INT_TBL_DAT_CHK_ATR,"¿Atraso?");
        vecCab.add(INT_TBL_DAT_HOR_TIEMPO_ATRASO,"Tiempo");
        vecCab.add(INT_TBL_DAT_CHK_JUS_ATR,"¿Justificar?");
        vecCab.add(INT_TBL_DAT_HOR_TIE_ATRASO_JUS,"Tie.Jus.");
        vecCab.add(INT_TBL_DAT_HOR_ENT_JUS,"Hor.Ent.Jus.");
        vecCab.add(INT_TBL_DAT_MOT,"Motivo");
        vecCab.add(INT_TBL_DAT_COD_MOT,"Cód.Mot.");
        vecCab.add(INT_TBL_DAT_BUT_MOT,"");
        vecCab.add(INT_TBL_DAT_OBS,"Observación");
        vecCab.add(INT_TBL_DAT_BUT_OBS,"");
        vecCab.add(INT_TBL_DAT_ST_JUSTATR,"xD");

        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        
        //Configurar JTable: Establecer el modelo de la tabla.
        tblDat.setModel(objTblMod);
        
        //Configurar JTable: Establecer tipo de selección.
        tblDat.setRowSelectionAllowed(true);
        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
        
        //Configurar JTable: Establecer el msenú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblDat);
        
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        
        
        //Configurar JTable: Establecer el ancho de las columnas.
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_DAT_FECHA).setPreferredWidth(90);
        tcmAux.getColumn(INT_TBL_DAT_COD_TRA).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_NOM_APE_TRA).setPreferredWidth(260);
        tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_EST).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_EST).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_MAR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_HOR_SAL_MAR).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_DAT_HOR_TIEMPO_ATRASO).setPreferredWidth(50);
        tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_ATR).setPreferredWidth(65);
        tcmAux.getColumn(INT_TBL_DAT_HOR_TIE_ATRASO_JUS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_JUS).setPreferredWidth(80);
        tcmAux.getColumn(INT_TBL_DAT_MOT).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setPreferredWidth(20);
        tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(100);
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);
        
        //Configurar JTable: Ocultar columnas del sistema.
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CO_EMP, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MOT, tblDat);
        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ST_JUSTATR, tblDat);
        //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST, tblDat);
        
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        tblDat.getTableHeader().setReorderingAllowed(false);

        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
        objMouMotAda=new ZafMouMotAda();
        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux=new Vector();
        vecAux.add("" + INT_TBL_DAT_CHKSEL);
        vecAux.add("" + INT_TBL_DAT_CHK_JUS_ATR);
        vecAux.add("" + INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
        vecAux.add("" + INT_TBL_DAT_HOR_ENT_JUS);
        vecAux.add("" + INT_TBL_DAT_BUT_MOT);
        vecAux.add("" + INT_TBL_DAT_BUT_OBS);
        objTblMod.addColumnasEditables(vecAux);
        vecAux=null;

        //Configurar JTable: Editor de la tabla.
        objTblEdi=new ZafTblEdi(tblDat);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellRenderer(zafTblCelRenChkLab);
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHKSEL).setCellEditor(zafTblCelEdiChkLab);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_ATR).setCellRenderer(zafTblCelRenChkLab);
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_ATR).setCellEditor(zafTblCelEdiChkLab);
        
        zafTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_ATR).setCellRenderer(zafTblCelRenChkLab);
        zafTblCelEdiChkLab = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_ATR).setCellEditor(zafTblCelEdiChkLab);
        
        
        

       objTblCelRenChkLab = new ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_ATR).setCellRenderer(objTblCelRenChkLab);
        objTblCelEdiChk=new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_DAT_CHK_JUS_ATR).setCellEditor(objTblCelEdiChk);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFilSel = tblDat.getSelectedRow();
                //tblDat.setValueAt( new Boolean(true), intFilSel , INT_TBL_CHKAUT);
                //tblDat.setValueAt( new Boolean(false), intFilSel , INT_TBL_CHKDEN);
                
//                if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHK_ATR).equals(true)){
                    if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHK_JUS_ATR).equals(true)){
                        objTblMod.setValueAt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIEMPO_ATRASO).toString(), intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                        objTblMod.setValueAt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_EST).toString(), intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                    }else{
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                        objTblMod.setValueAt("", intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                    }
//                }
//                else{
//                    mostrarMsgInf("El empleado no tiene atraso.");
//                }
                
                
                
                
                
            }
        });
        
        
        
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        tcmAux.getColumn(INT_TBL_DAT_HOR_ENT_JUS).setCellEditor(objTblCelEdiTxt);
        tcmAux.getColumn(INT_TBL_DAT_HOR_TIE_ATRASO_JUS).setCellEditor(objTblCelEdiTxt);
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

                //Calendar calendario = new GregorianCalendar();
                String[] strFecha=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_FECHA).toString().split("-");
                int intMes =Integer.valueOf(strFecha[1]);
                int intAño = Integer.valueOf(strFecha[0]);
                int intDia = Integer.valueOf(strFecha[2]);
                
                intFilSel=tblDat.getSelectedRow();
                    int intHH = 0;
                    int intMM  = 0;
                    //DateFormat sdf = new SimpleDateFormat("hh:mm");
                    String strHorIng = null;
                    //Date date = null;

                    try {
                        
                        objTblMod.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                        
                        switch(tblDat.getSelectedColumn()){
                            case INT_TBL_DAT_HOR_ENT_JUS:
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_JUS));
                                if(!strHorIng.equals("")){
                                    intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                                    intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));
                                }
                                break;
                            case INT_TBL_DAT_HOR_TIE_ATRASO_JUS:
                                
                                strHorIng = objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS));
                                if(!strHorIng.equals("")){
                                    intHH = Integer.parseInt(strHorIng.replace(":", "").substring(0,2));
                                    intMM = Integer.parseInt(strHorIng.replace(":","").substring(2,4));
                                }
                                break;
                        }

                        if(!strHorIng.equals("")){
                            
                            if((intHH>=0 && intHH<=24)){
                            if(!(intMM>=0 && intMM<=59)){
                                {
                                    String strTit = "Mensaje del sistema Zafiro";
                                    String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                                    JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                    //objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                                    objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                                }
                            }else{

                                String[] strHorEntMar;
                                int intHorEntMar;
                                int intMinutosEntMar;
                                GregorianCalendar calEntMar;
                                
                                String[] strTiempoJustificado;
                                int intHorTieJus;
                                int intMinutosTieJus;
                                
                                String[] strTiempoAtraso;
                                int intHorAtr;
                                int intMinAtr;
                                
                                switch (tblDat.getSelectedColumn()){
                                    
                                    case INT_TBL_DAT_HOR_ENT_JUS:
                                        String strHorEnt=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_JUS));
                                        if(strHorEnt.length()==5){
                                            strHorEnt = strHorEnt + ":00";
                                        }
                                        java.sql.Time t = SparseToTime(strHorEnt);
                                        
                                        strHorEntMar = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_MAR).toString().split(":");
                                        intHorEntMar = Integer.valueOf(strHorEntMar[0]);
                                        intMinutosEntMar = Integer.valueOf(strHorEntMar[1]);
                                        calEntMar = new GregorianCalendar( intAño, intMes, intDia, intHorEntMar, intMinutosEntMar );
                                        
                                        strTiempoJustificado = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_JUS).toString().split(":");
                                        intHorTieJus = Integer.valueOf(strTiempoJustificado[0]);
                                        intMinutosTieJus = Integer.valueOf(strTiempoJustificado[1]);
                                        
                                        
//                                        if(intHorTieJus>=intHorEntMar){
                                            
                                            if(intMinutosTieJus<=intMinutosEntMar){
                                                
                                                if(intHorTieJus==intHorEntMar){
                                                    calEntMar.add(GregorianCalendar.MINUTE, (intMinutosTieJus*-1));
                                                }else{
                                                    calEntMar.add(GregorianCalendar.HOUR, (intHorTieJus*-1));
                                                    calEntMar.add(GregorianCalendar.MINUTE, (intMinutosTieJus*-1));
                                                }
                                                
                                                java.util.Date date=calEntMar.getTime();
                                
                                                java.text.SimpleDateFormat sDF= new SimpleDateFormat("hh:mm");
                                
                                                StringBuilder nowYYYYMMDD = new StringBuilder( sDF.format( date ) );
                                                String[] str=nowYYYYMMDD.toString().split(":");
                                                String strnowYYYYMMDD=nowYYYYMMDD.toString();
                                                
                                                if(str[0].equals(strHorEntMar[0])){
                                                    strnowYYYYMMDD = "00"+":"+str[1];
                                                }
                                                if(!objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS).toString().equals("00:00")){
                                                    objTblMod.setValueAt(strnowYYYYMMDD,intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                                }
                                                
                                                System.out.println(strnowYYYYMMDD);
                                                
                                            }
//                                            else{
//                                                String strTit = "Mensaje del sistema Zafiro";
//                                                String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(MM).";
//                                                JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
//                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
//                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
//                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
//                                                objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
//                                            }
                                            
//                                        }else{
//                                            String strTit = "Mensaje del sistema Zafiro";
//                                            String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(HH).";
//                                            JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
//                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
//                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
//                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
//                                            objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
//                                        }
                                        
                                        
                                        break;
                                        
                                    case INT_TBL_DAT_HOR_TIE_ATRASO_JUS:
                                        objTblMod.setValueAt(Boolean.TRUE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                                        String strHorSal=objUti.parseString(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS));
                                        if(strHorSal.length()==5){
                                            strHorSal = strHorSal + ":00";
                                        }
                                        t = SparseToTime(strHorSal);
                                        
                                        strHorEntMar = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_ENT_MAR).toString().split(":");
                                        intHorEntMar = Integer.valueOf(strHorEntMar[0]);
                                        intMinutosEntMar = Integer.valueOf(strHorEntMar[1]);
                                        calEntMar = new GregorianCalendar( intAño, intMes, intDia, intHorEntMar, intMinutosEntMar );
                                        
                                        strTiempoJustificado = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS).toString().split(":");
                                        intHorTieJus = (Integer.valueOf(strTiempoJustificado[0]))*-1;
                                        intMinutosTieJus = (Integer.valueOf(strTiempoJustificado[1])*-1);
                                        
                                        strTiempoAtraso=objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIEMPO_ATRASO).toString().split(":");
                                        intHorAtr = Integer.valueOf(strTiempoAtraso[0]);
                                        intMinAtr = Integer.valueOf(strTiempoAtraso[1]);
                                        
                                        if(intHorAtr>=intHorTieJus){
                                                int intMinTieJus=(intMinutosTieJus*-1);
                                            if(intMinTieJus<=intMinAtr){
                                                calEntMar.add(GregorianCalendar.HOUR, intHorTieJus);
                                                calEntMar.add(GregorianCalendar.MINUTE, intMinutosTieJus);
                                                java.util.Date date=calEntMar.getTime();
                                
                                                java.text.SimpleDateFormat sDF= new SimpleDateFormat("hh:mm");
                                
                                                StringBuilder nowYYYYMMDD = new StringBuilder( sDF.format( date ) );
                                                objTblMod.setValueAt(nowYYYYMMDD,intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                                System.out.println(nowYYYYMMDD);
                                            }else if(intMinTieJus>=intMinAtr){
                                                
                                                if(intHorAtr==intHorTieJus){
                                                    String strTit = "Mensaje del sistema Zafiro";
                                                    String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(MM).";
                                                    JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                                    objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                                                    objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);    
                                                }else if (intHorTieJus<intHorAtr){
                                                    int intHorTieJusR=intHorTieJus*-1;
                                                    if(intHorAtr<=intHorTieJusR){
                                                        String strTit = "Mensaje del sistema Zafiro";
                                                        String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(MM).";
                                                        JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                                                        objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);    
                                                    }else{
                                                        
                                                        strTiempoJustificado = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS).toString().split(":");
                                                        intHorTieJus = (Integer.valueOf(strTiempoJustificado[0]))*-1;
                                                        intMinutosTieJus = (Integer.valueOf(strTiempoJustificado[1])*-1);

                                                        calEntMar.add(GregorianCalendar.HOUR, intHorTieJus);
                                                        calEntMar.add(GregorianCalendar.MINUTE, intMinutosTieJus);
                                                        java.util.Date date=calEntMar.getTime();

                                                        java.text.SimpleDateFormat sDF= new SimpleDateFormat("hh:mm");

                                                        StringBuilder nowYYYYMMDD = new StringBuilder( sDF.format( date ) );
                                                        objTblMod.setValueAt(nowYYYYMMDD,intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                                        
                                                    }
                                                }
                                            }
                                            else{
                                                String strTit = "Mensaje del sistema Zafiro";
                                                String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(MM).";
                                                JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                                objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                                                objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                                            }
                                        }else{
                                            String strTit = "Mensaje del sistema Zafiro";
                                            String strMen = "Tiempo justificado ingresado es mayor que el tiempo de retraso(HH).";
                                            JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                                            objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                                        }
                                        break;
                                }
                            }
                        }else{

                            String strTit = "Mensaje del sistema Zafiro";
                            String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                            JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                            //objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                            objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                            }
                            
                        }else{
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                            objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                            objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                        }

                        
                    } catch (Exception ex) {
                        String strTit = "Mensaje del sistema Zafiro";
                        String strMen = "Horario ingresado contienen formato erroneo. Revisar e intentar nuevamente.";
                        JOptionPane.showMessageDialog(ZafRecHum19.this,strMen,strTit,JOptionPane.INFORMATION_MESSAGE);
                        //objTblMod.setValueAt("",intFilSel, tblDat.getSelectedColumn());
                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_ENT_JUS);
                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_HOR_TIE_ATRASO_JUS);
                        objTblMod.setValueAt("",intFilSel, INT_TBL_DAT_LIN);
                        objTblMod.setValueAt(Boolean.FALSE, intFilSel, INT_TBL_DAT_CHK_JUS_ATR);
                    }
            }
        });
                
       //Configurar JTable: Editor de búsqueda.
       objTblBus=new ZafTblBus(tblDat);
                
       //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
       //int intCol[]={};
       //objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
                
       
       
//       objTblCelRenBut=new ZafTblCelRenBut();
//       tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(objTblCelRenBut);
//                
//       int intColVen[]=new int[2];
//       intColVen[0]=1;
//       intColVen[1]=2;
////
//       int intColTbl[]=new int[2];
//       intColTbl[0]=INT_TBL_DAT_MOT;
//       intColTbl[1]=INT_TBL_DAT_COD_MOT;
//
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
       
       //objTblCelRenBut=new ZafTblCelRenBut();
       //tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(objTblCelRenBut);
       
       Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_MOT).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_MOT, "Motivo") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    int intFil = tblDat.getRowCount();

                    vcoMotJusAsi.setCampoBusqueda(2);
                    vcoMotJusAsi.setVisible(true);
                    if (vcoMotJusAsi.getSelectedButton()==ZafVenCon.INT_BUT_ACE){

                        
                        tblDat.setValueAt(vcoMotJusAsi.getValueAt(1),intSelFil, INT_TBL_DAT_COD_MOT);
                        tblDat.setValueAt(vcoMotJusAsi.getValueAt(2),intSelFil, INT_TBL_DAT_MOT);                        
                    }
                }
            };
       
       
        tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(objTblCelRenBut);

        zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUT_OBS, "Observación") {
            public void butCLick() {
                int intSelFil = tblDat.getSelectedRow();
                String strObs = (tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS) == null ? "" : tblDat.getValueAt(intSelFil, INT_TBL_DAT_OBS).toString());
                ZafMae07_01 zafMae07_01 = new ZafMae07_01(JOptionPane.getFrameForComponent(ZafRecHum19.this), true, strObs);
                zafMae07_01.show();
                if (zafMae07_01.getAceptar()) {
                    tblDat.setValueAt(zafMae07_01.getObser(), intSelFil, INT_TBL_DAT_OBS);
                }
            }
        };
       
       
//       intColVen=null;
//       intColTbl=null;
                
       objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
       blnRes=true;
                
        }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
           
        return blnRes;
    }
    
    public static java.sql.Time SparseToTime(String hora){
    int h, m, s;

    h = Integer.parseInt(hora.charAt(0)+""+hora.charAt(1)) ;
    m = Integer.parseInt(hora.charAt(3)+""+hora.charAt(4)) ;
    s = Integer.parseInt(hora.charAt(6)+""+hora.charAt(7)) ;
    return (new java.sql.Time(h,m,s));
}
    
    private boolean agregarColTblDat()
    {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;

        try
        {

            intNumFil=objTblMod.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();

            for (i=0; i<1; i++)
            {
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
                objTblHeaColGrpEmp.setHeight(16);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKSEL+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CO_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECHA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NOM_APE_TRA+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Horario de trabajo");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_EST+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_EST+i*INT_TBL_DAT_NUM_TOT_CDI));

                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Marcaciones");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_MAR+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_SAL_MAR+i*INT_TBL_DAT_NUM_TOT_CDI));
                
                objTblHeaColGrpEmp=new ZafTblHeaColGrp("Justificación de atrasos");
                objTblHeaColGrpEmp.setHeight(16);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

                
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_ATR+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_TIEMPO_ATRASO+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_JUS_ATR+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_TIE_ATRASO_JUS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_HOR_ENT_JUS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_MOT+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_OBS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
            }
        }catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
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
   
    private class ZafThreadGUI extends Thread{
    public void run(){

        lblMsgSis.setText("Obteniendo datos...");
        pgrSis.setIndeterminate(true);

        if (!cargarDat())
        {
            //Inicializar objetos si no se pudo cargar los datos.
            //lblMsgSis.setText("Listo");
            pgrSis.setValue(0);
            butCon.setText("Consultar");
        }

        //Establecer el foco en el JTable sólo cuando haya datos.
        if (tblDat.getRowCount()>0)
        {
            tabFrm.setSelectedIndex(1);
            tblDat.setRowSelectionInterval(0, 0);
            tblDat.requestFocus();
        }

        objThrGUI=null;
    }
}
    
    /**
 * Se encarga de cargar la informacion en la tabla
 * @return  true si esta correcto y false  si hay algun error
 */
private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn=null;
  java.sql.Statement stmLoc=null;
  java.sql.Statement stmLocAux=null;
  java.sql.ResultSet rstLoc = null;
  java.sql.ResultSet rstLocAux = null;
  String strSql="";
  String strFilFec="";
  String sqlFilEmp = "";
  Vector vecDataCon;
  Vector vecData;
  Vector vecFecCon;
  try{
      butCon.setText("Detener");
      conn=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
          
      if(conn!=null){
          
          
          stmLoc=conn.createStatement();
          stmLocAux=conn.createStatement();
          vecDataCon = new Vector();
          vecData = new Vector();
          vecFecCon = new Vector();
          
          
          switch (objSelFec.getTipoSeleccion()){
              case 0: //Búsqueda por rangos
                  strFilFec+=" and e.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 1: //Fechas menores o iguales que "Hasta".
                  strFilFec+=" and e.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 2: //Fechas mayores o iguales que "Desde".
                  strFilFec+=" and e.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' ";
                  break;
              case 3: //Todo.
                  break;
          }
          
          

          if(txtCodEmp.getText().compareTo("")!=0){
              sqlFilEmp+= " and d.co_emp  = " + txtCodEmp.getText() + " ";
          }

          if(txtCodTra.getText().compareTo("")!=0){
              sqlFilEmp+= " AND d.co_tra  = " + txtCodTra.getText() + " ";
          }
          
          if(txtCodOfi.getText().compareTo("")!=0){
              sqlFilEmp+= " AND d.co_ofi  = " + txtCodOfi.getText() + " ";
          }

          String strDep="";
          if(txtCodDep.getText().compareTo("")!=0){
              strDep+= " AND b.co_dep  = " + txtCodDep.getText() + " ";
          }
          
          String strEmp="";
          if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
              strEmp=" and b.co_emp = "+ objParSis.getCodigoEmpresa();
          }
          
          String strAtr="";
          if(optFil.isSelected()){
              if(jChkEmpNoTieAtr.isSelected() && jChkEmpTieAtra.isSelected()){
                  strAtr+=" where ((case (f.ho_ent>e.ho_ent) when true then true else false end)=true) or";
                  strAtr+=" not ((case (f.ho_ent>e.ho_ent) when true then true else false end)=true) ";
            }
          
            if(jChkEmpNoTieAtr.isSelected() && !jChkEmpTieAtra.isSelected()){
                strAtr+=" where not ((case (f.ho_ent>e.ho_ent) when true then true else false end)=true) ";
            }
          
            if(!jChkEmpNoTieAtr.isSelected() && jChkEmpTieAtra.isSelected()){
                strAtr+=" where ((case (f.ho_ent>e.ho_ent) when true then true else false end)=true) ";
            }
          }
          //if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
          
          String strDepAut="";
          String strOfiAut="";
          
          if(objParSis.getCodigoUsuario()!=1){
              strDepAut="inner join tbr_depprgusr h on(d.co_dep=h.co_dep and h.co_dep in (select co_dep from tbr_depprgusr where co_usr = "+objParSis.getCodigoUsuario()+" "+
                                "and co_mnu="+objParSis.getCodigoMenu()+" )) ";
              
              if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                  strOfiAut="inner join tbr_ofiLocPrgUsr i on(i.co_emp=0 and i.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr where co_usr = "+objParSis.getCodigoUsuario()+" "+
                              " and co_mnu="+objParSis.getCodigoMenu()+" and st_reg='A')) ";
              }else{
                  strOfiAut="inner join tbr_ofiLocPrgUsr i on(i.co_emp=d.co_emp and i.co_loc="+objParSis.getCodigoLocal()+"and i.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr where co_emp="+objParSis.getCodigoEmpresa() +
                              " and co_usr = "+objParSis.getCodigoUsuario() +" and co_loc="+objParSis.getCodigoLocal() + " and co_mnu="+objParSis.getCodigoMenu()+" )) ";
              }
          }
              
          strSql="";
          strSql+="select coalesce(st_jusAtr,'0') as st_jus , d.co_emp,tx_empresa,d.co_tra,d.co_ofi,d.co_hor,d.co_dep,empleado,e.fe_dia, e.ho_ent as horaEntradaEstablecida,e.ho_sal as horaSalidaEstablecida," + "\n";
          strSql+=" f.ho_ent as ho_entEmp, f.ho_sal as ho_salEmp," + "\n";
          strSql+=" (case (f.ho_ent>e.ho_ent) when true then true else false end) as blnTieAtr," + "\n";
          strSql+=" (case (f.ho_ent>e.ho_ent) when true then (f.ho_ent-e.ho_ent) else null end) as tiempoAtraso," + "\n";
          strSql+=" (case st_jusAtr when 'S' then true else false end) as blnJusAtr," + "\n";
          strSql+=" (case (ho_entJus is not null and ((f.ho_ent-ho_entJus) != '00:00')) when true then (f.ho_ent-ho_entJus) else null end) as tiempoJustificado," + "\n"; //Rose Postgres 9.4
          strSql+=" ho_entJus, ho_salJus," + "\n";
          strSql+=" co_motJusAtr, g.tx_deslar , tx_obsJusAtr" + "\n";
          strSql+=" from ( " + "\n";
          strSql+=" select b.co_emp, b.co_tra, b.co_dep,b.co_ofi,b.co_hor,(a.tx_ape || ' ' || a.tx_nom) as empleado, c.tx_nom as tx_empresa from tbm_tra a" + "\n";
          strSql+=" inner join tbm_traemp b on (a.co_tra=b.co_tra and b.st_reg='A' and b.co_hor is not null " + strEmp + strDep +")" + "\n";
          strSql+=" inner join tbm_emp c on (b.co_emp=c.co_emp)" + "\n";
          strSql+=" order by (a.tx_ape || ' ' || a.tx_nom)) as d" + "\n";
          strSql+=" left outer join tbm_callab e on (e.co_hor=d.co_hor and e.st_dialab='S'  " + strFilFec+ " )" + "\n";
          strSql+=" left outer join tbm_cabconasitra f on (f.co_tra=d.co_tra and f.fe_dia=e.fe_dia)" + "\n";
          strSql+=" left outer join tbm_motjusconasi g on (f.co_motJusAtr=g.co_motjus)" + "\n";
          strSql+=" " + strDepAut + "\n";
          strSql+=" " + strOfiAut+ "\n";
          strSql+=" " + strAtr + "\n";
          strSql+=" " + sqlFilEmp + "\n";
          strSql+=" group by d.co_emp,tx_empresa,d.co_tra,d.co_ofi,d.co_hor,d.co_dep,empleado,e.fe_dia, e.ho_ent,e.ho_sal," + "\n";
          strSql+=" f.ho_ent,f.ho_sal,st_jusAtr,f.ho_saljus,f.ho_entjus,f.co_motjusatr,g.tx_deslar,f.tx_obsjusatr" + "\n";
          strSql+=" order by e.fe_dia , empleado" + "\n";
          //}

          System.out.println("q consul: " + strSql);
          rstLoc=stmLoc.executeQuery(strSql);
              
          vecDat = new Vector();
              
              
          //if(rstLoc.next()){
            rstLoc=stmLoc.executeQuery(strSql);
            while(rstLoc.next()){
                if (blnCon){
                java.util.Vector vecReg = new java.util.Vector();
                vecReg.add(INT_TBL_DAT_LIN,"");
                vecReg.add(INT_TBL_DAT_CHKSEL,Boolean.FALSE);
                vecReg.add(INT_TBL_DAT_CO_EMP,rstLoc.getInt("co_emp"));
                vecReg.add(INT_TBL_DAT_NOM_EMP,rstLoc.getString("tx_empresa"));
                vecReg.add(INT_TBL_DAT_FECHA,rstLoc.getString("fe_dia"));
                //vecFecCon.add(rstLoc.getString("fe_dia"));                
                
                vecReg.add(INT_TBL_DAT_COD_TRA,rstLoc.getInt("co_tra"));
                vecReg.add(INT_TBL_DAT_NOM_APE_TRA,rstLoc.getString("empleado"));
                vecReg.add(INT_TBL_DAT_HOR_ENT_EST,rstLoc.getString("horaEntradaEstablecida")==null?"":rstLoc.getString("horaEntradaEstablecida").substring(0,5));
                vecReg.add(INT_TBL_DAT_HOR_SAL_EST,rstLoc.getString("horaSalidaEstablecida")==null?"":rstLoc.getString("horaSalidaEstablecida").substring(0,5));

                vecReg.add(INT_TBL_DAT_HOR_ENT_MAR,rstLoc.getString("ho_entEmp")==null?"":rstLoc.getString("ho_entEmp").substring(0,5));
                vecReg.add(INT_TBL_DAT_HOR_SAL_MAR,rstLoc.getString("ho_salEmp")==null?"":rstLoc.getString("ho_salEmp").substring(0,5));

                vecReg.add(INT_TBL_DAT_CHK_ATR,rstLoc.getBoolean("blnTieAtr"));
                
                vecReg.add(INT_TBL_DAT_HOR_TIEMPO_ATRASO,rstLoc.getString("tiempoAtraso")==null?"":rstLoc.getString("tiempoAtraso").substring(0,5));

                vecReg.add(INT_TBL_DAT_CHK_JUS_ATR,rstLoc.getBoolean("blnJusAtr"));
                
                vecReg.add(INT_TBL_DAT_HOR_TIE_ATRASO_JUS,rstLoc.getString("tiempoJustificado")==null?"":rstLoc.getString("tiempoJustificado"));
                
                vecReg.add(INT_TBL_DAT_HOR_ENT_JUS,rstLoc.getString("ho_entJus")==null?"":rstLoc.getString("ho_entJus").substring(0,5));
                

                vecReg.add(INT_TBL_DAT_MOT,rstLoc.getString("tx_deslar"));
                vecReg.add(INT_TBL_DAT_COD_MOT,rstLoc.getString("co_motJusAtr"));
                vecReg.add(INT_TBL_DAT_BUT_MOT,"");

                vecReg.add(INT_TBL_DAT_OBS,rstLoc.getString("tx_obsJusAtr"));
                vecReg.add(INT_TBL_DAT_BUT_OBS,"");
                vecReg.add(INT_TBL_DAT_ST_JUSTATR,rstLoc.getString("st_jus"));                

                vecDataCon.add(vecReg);
                }else
                    break;
                
            }


            objTblMod.setData(vecDataCon);
            tblDat .setModel(objTblMod);
          
          
//          /***********************************************************************************************************************************/
//          if(vecDat.size()==0){
//              mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
//              lblMsgSis.setText("Listo");
//          }else{
              lblMsgSis.setText("Listo");
              //Asignar vectores al modelo.
              //objTblMod.setData(vecDat);
              //tblDat.setModel(objTblMod);
              tabFrm.setSelectedIndex(1);
              lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros");
//          }
          butCon.setText("Consultar");
          pgrSis.setIndeterminate(false);
          vecDat.clear();
      }
  }catch(java.sql.SQLException Evt) { Evt.printStackTrace(); blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { Evt.printStackTrace(); blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
  finally {
        try{rstLoc.close();}catch(Throwable ignore){}
        try{rstLocAux.close();}catch(Throwable ignore){}
        try{stmLoc.close();}catch(Throwable ignore){}
        try{stmLocAux.close();}catch(Throwable ignore){}
        try{conn.close();}catch(Throwable ignore){}
    }
    System.gc();
    return blnRes;
}
    

private long calcularDiferenciaFechas(String strFecDes, String strFecHas){
    
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
    strFecDes = strFecDes.replaceAll("-", "");
    strFecHas = strFecHas.replaceAll("-", "");
    int intFecDesAño = Integer.valueOf(strFecDes.substring(0, 4));
    int intFecDesMes = Integer.valueOf(strFecDes.substring(4, 6));;
    int intFecDesDia = Integer.valueOf(strFecDes.substring(6, 8));

    int intFecHasAño = Integer.valueOf(strFecHas.substring(0, 4));
    int intFecHasMes = Integer.valueOf(strFecHas.substring(4, 6));;
    int intFecHasDia = Integer.valueOf(strFecHas.substring(6, 8));

    Calendar calFecDes = new GregorianCalendar(intFecDesAño, intFecDesMes-1, intFecDesDia);
    Calendar calFecHas = new GregorianCalendar(intFecHasAño, intFecHasMes-1, intFecHasDia);

    java.sql.Date datFecDes = new java.sql.Date(calFecDes.getTimeInMillis());
    java.sql.Date datFecHas = new java.sql.Date(calFecHas.getTimeInMillis());

    return ( datFecHas.getTime() - datFecDes.getTime() )/MILLSECS_PER_DAY;

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
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CHKSEL:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CO_EMP:
                    strMsg="Código de la empresa";
                    break;    
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;    
                case INT_TBL_DAT_FECHA:
                    strMsg="Fecha";
                    break;
                case INT_TBL_DAT_COD_TRA:
                    strMsg="Código del empleado";
                    break;
                case INT_TBL_DAT_NOM_APE_TRA:
                    strMsg="Nombres y apellidos del empleado";
                    break;
                case INT_TBL_DAT_HOR_ENT_EST:
                    strMsg="Entrada";
                    break;
                case INT_TBL_DAT_HOR_SAL_EST:
                    strMsg="Salida";
                    break;
                case INT_TBL_DAT_HOR_ENT_MAR:
                    strMsg="Hora de entrada del empleado";
                    break;
                case INT_TBL_DAT_HOR_SAL_MAR:
                    strMsg="Hora de salida del empleado";
                    break;
                case INT_TBL_DAT_CHK_ATR:
                    strMsg="¿Tiene atraso?";
                    break;
                case INT_TBL_DAT_HOR_TIEMPO_ATRASO:
                    strMsg="Tiempo de atraso";
                    break;
                case INT_TBL_DAT_CHK_JUS_ATR:
                    strMsg="¿Justificar el atraso?";
                    break;
                case INT_TBL_DAT_HOR_TIE_ATRASO_JUS:
                    strMsg="Tiempo justificado";
                    break;
                case INT_TBL_DAT_HOR_ENT_JUS:
                    strMsg="Hora de entrada justificada";
                    break;
                case INT_TBL_DAT_MOT:
                    strMsg="Motivo del atraso";
                    break;
                case INT_TBL_DAT_COD_MOT:
                    strMsg="Código del motivo";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación del atraso";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

     private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
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
     
     private boolean mostrarVenConMot(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoMotJusAsi.setCampoBusqueda(2);
                    vcoMotJusAsi.show();
                    if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoMotJusAsi.buscar("a1.co_mot", txtCodMot.getText())){
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    }
                    else{
                        vcoMotJusAsi.setCampoBusqueda(0);
                        vcoMotJusAsi.setCriterio1(11);
                        vcoMotJusAsi.cargarDatos();
                        vcoMotJusAsi.show();
                        if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                        }
                        else{
                            txtCodMot.setText(strCodMotJus);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoMotJusAsi.buscar("a1.tx_deslar", txtDesMot.getText())){
                        txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                        txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                    }
                    else{
                        vcoMotJusAsi.setCampoBusqueda(1);
                        vcoMotJusAsi.setCriterio1(11);
                        vcoMotJusAsi.cargarDatos();
                        vcoMotJusAsi.show();
                        if (vcoMotJusAsi.getSelectedButton()==vcoMotJusAsi.INT_BUT_ACE){
                            txtCodMot.setText(vcoMotJusAsi.getValueAt(1));
                            txtDesMot.setText(vcoMotJusAsi.getValueAt(2));
                        }
                        else{
                            txtDesMot.setText(strDesMot);
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
     
     
     
     private boolean mostrarVenConTra(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())){
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                    }
                    else{
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton()==vcoTra.INT_BUT_ACE){
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2)+ " " + vcoTra.getValueAt(3));
                        }
                        else{
                            txtNomTra.setText(strNomTra);
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

    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

//            String strAux = "";
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                strAux = " and a1.co_emp in ("+objParSis.getCodigoEmpresa() +")";
//            }
            
            if(objParSis.getCodigoUsuario()==1){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                
            }
            else{
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.tx_nom";
//                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
//                strSQL+=" AND a1.st_reg NOT IN('I','E')" + strAux;
//                strSQL+=" ORDER BY a1.co_emp";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" WHERE a1.co_emp in ("+objParSis.getCodigoEmpresa() +")" + "";
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
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
    private boolean mostrarVenConDep(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.tx_desLar", txtNomDep.getText()))
                    {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtNomDep.setText(vcoDep.getValueAt(3));
                    }
                    else
                    {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtNomDep.setText(vcoDep.getValueAt(3));
                        }
                        else
                        {
                            txtNomDep.setText(strDesLarDep);
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConOfi(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoOfi.setCampoBusqueda(2);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    //vcoDep.setCampoBusqueda(0);
                    vcoOfi.setVisible(true);
                    if (vcoOfi.buscar("a1.co_ofi", txtCodOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(1);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoOfi.setCampoBusqueda(2);
                    //vcoDep.setVisible(true);
                    if (vcoOfi.buscar("a1.tx_nom", txtNomOfi.getText()))
                    {
                        txtCodOfi.setText(vcoOfi.getValueAt(1));
                        txtNomOfi.setText(vcoOfi.getValueAt(3));
                    }
                    else
                    {
                        vcoOfi.setCampoBusqueda(2);
                        vcoOfi.setCriterio1(11);
                        vcoOfi.cargarDatos();
                        vcoOfi.setVisible(true);
                        if (vcoOfi.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodOfi.setText(vcoOfi.getValueAt(1));
                            txtNomOfi.setText(vcoOfi.getValueAt(3));
                        }
                        else
                        {
                            txtNomOfi.setText(strDesLarOfi);
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConOfi()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_ofi");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            
            if(objParSis.getCodigoUsuario()==1){
                strSQL="SELECT co_ofi,tx_nom,st_reg FROM tbm_ofi WHERE st_reg LIKE 'A' ORDER BY co_ofi";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select co_ofi,tx_nom,st_reg FROM tbm_ofi WHERE co_ofi IN(SELECT co_ofi FROM tbr_ofiLocPrgUsr where co_emp = "+objParSis.getCodigoEmpresa()+" and co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+")";
            }

            vcoOfi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Oficinas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoOfi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            
            String strSQL="";
            if(objParSis.getCodigoUsuario()==1){
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            }else{
                /*strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";*/
                strSQL="select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr="+objParSis.getCodigoUsuario()+" "+
                        "and co_mnu="+objParSis.getCodigoMenu()+")";
            }
            
            vcoDep=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            //arlAncCol.add("40");
            
            String strSQL="";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                strSQL="select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) where b.st_reg like 'A' "+
                       "order by (a.tx_ape || ' ' || a.tx_nom)";
            }else{
                strSQL+="SELECT a.co_tra,a.tx_ape,a.tx_nom "+"\n";
                strSQL+="FROM tbm_tra a  "+"\n";
                strSQL+="INNER JOIN tbm_traemp b on(b.co_tra=a.co_tra AND b.st_reg = 'A')  "+"\n";
                strSQL+="WHERE  b.co_emp= "+objParSis.getCodigoEmpresa()+"\n";
                strSQL+="AND b.co_ofi in (select co_ofi from tbr_ofiLocPrgUsr"+"\n";
                strSQL+="WHERE co_emp="+objParSis.getCodigoEmpresa()+"\n";
                strSQL+="AND co_usr = "+objParSis.getCodigoUsuario()+"\n";
                strSQL+="AND co_loc="+objParSis.getCodigoLocal()+"\n";
                strSQL+="AND co_mnu="+objParSis.getCodigoMenu()+") "+"\n";
                strSQL+="ORDER BY (a.tx_ape || ' ' || a.tx_nom)";
            }
            
            vcoTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
//            vcoTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
//            vcoTra.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConJustificarMotivos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_motjus");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Larga");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("120");
            //Armar la sentencia SQL.
            String strSQL="";
            strSQL+="SELECT a1.co_motjus,a1.tx_desLar";
            strSQL+=" FROM tbm_motjusconasi AS a1";
            strSQL+=" WHERE a1.st_reg like 'A'";
            strSQL+=" ORDER BY a1.co_motjus";

            vcoMotJusAsi=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de motivos de justificaciones de asistencias", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoMotJusAsi.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoMotJusAsi.setCampoBusqueda(1);
            vcoMotJusAsi.setCriterio1(7);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
        //DAO
    public double calcularValor(Connection conex, String coEmpresa, String coTra, String fecAJustificar ){
        double valorDevolver = 0 ;
        try {
            String strQurey = "select p.co_emp, p.co_tra,(p.tx_ape || ' ' || p.tx_nom) as empleado, t.nd_valrub, sum(totalFavor) as totalFavor, sum(totalContra) as totalContra,sum(p1) as p1,\n" +
"             case (sum(totalFavor) < sum(totalContra)) when true then (sum(totalContra)-sum(totalFavor)) else null end as diferencia,\n" +
"             case (sum(totalFavor) < sum(totalContra)) when true then (extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor)))) else null end as cantMinutos,\n" +
"             (((t.nd_valrub/30)/8)/60) as valMinuto, \n" +
"             case (sum(totalFavor) < sum(totalContra)) when true then ((extract(HOUR from (sum(totalContra)-sum(totalFavor)))*60+extract(MINUTES from (sum(totalContra)-sum(totalFavor))))*(((t.nd_valrub/30)/8)/60)) else null end as nd_valDesAtrAntiguo,\n" +
"             case (sum(p1)>'00:00:00') when true then ((extract(HOUR from (sum(p1)))*60+extract(MINUTES from (sum(p1))))*(((t.nd_valrub/30)/8)/60)) else null end as nd_valDesAtr             \n" +
"             from (\n" +
"             select c.fe_dia,c.co_tra,c.tx_ape, c.tx_nom, g.ho_ent as horaEntrada, g.ho_sal as horaSalida, g.ho_mingraent as minutosGrac,\n" +
"             c.ho_ent, c.ho_sal, st_jusatr, fe_jusatr, st_jussalade,\n" +
"             (case (g.ho_ent > c.ho_ent) when true then (g.ho_ent-c.ho_ent) else null end) as minutosAdelantadoEntrada,\n" +
"             (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) as minutosAtrasadoEntrada,\n" +
"             (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else null end) as minutosAntesSalida,\n" +
"             (case (g.ho_sal< c.ho_sal) when true then (c.ho_sal-g.ho_sal) else null end) as minutosDespuesSalida,\n" +
"             case (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))>((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then\n" +
"             (c.ho_sal-g.ho_sal)-(c.ho_ent - g.ho_ent)\n" +
"             else null end as totalFavor,\n" +
"             case st_jusatr is null and (((g.ho_ent-c.ho_ent)+(c.ho_sal-g.ho_sal))<((c.ho_ent - g.ho_ent)+(g.ho_sal-c.ho_sal))) when true then\n" +
"             (c.ho_ent - g.ho_ent)-(c.ho_sal-g.ho_sal)\n" +
"             else null end as totalContra , f.co_emp ,\n" +
"             (case (st_jusatr is null and (g.ho_ent < c.ho_ent)) when true then (c.ho_ent-g.ho_ent) else '00:00:00' end) \n" +
//"             (case st_jussalade is null and (g.ho_sal > c.ho_sal) when true then (g.ho_sal-c.ho_sal) else '00:00:00' end)\n" +
"             as p1,\n" +
"             st_jussalade is null and (g.ho_sal > c.ho_sal)\n" +
"             FROM (\n" +
"             select a.fe_dia, a.co_tra, b.tx_nom, b.tx_ape, a.ho_ent, a.ho_sal, a.st_jusatr,a.tx_obsjusatr, a.st_jusfal,\n" +
"             a.tx_obsjusfal,b.st_reg, a.fe_jusatr, a.st_jussalade, a.fe_jussalade  from tbm_cabconasitra a \n" +
"             inner join tbm_tra b on (a.co_tra = b.co_tra) and b.st_reg like 'A' and a.co_tra = " + coTra + " ) c \n" +
"             inner join tbm_traemp f on (f.co_tra=c.co_tra) \n" +
"             left outer join tbm_dep d on d.co_dep=f.co_dep  \n" +
"             inner join tbm_callab g on(f.co_hor=g.co_hor and g.fe_dia=c.fe_dia) \n" +
"             WHERE c.fe_dia = " + fecAJustificar + " \n" +
"             AND not(c.ho_ent is null OR c.ho_sal is null) and not(c.ho_ent is null and c.ho_sal is null)\n" +
"             AND NOT (EXTRACT(DOW FROM c.fe_dia)=6 OR EXTRACT(DOW FROM c.fe_dia)=7) \n" +
"             AND  c.co_tra not in (SELECT DISTINCT co_tra FROM tbm_traemp  a WHERE co_hor IS NULL AND st_reg='A' )\n" +
"             AND ( ( (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) is not null\n" +
"             AND (case (g.ho_ent < c.ho_ent) when true then (c.ho_ent - g.ho_ent) else null end) > g.ho_mingraent)\n" +
"             OR ((case (g.ho_sal > c.ho_sal) when true then (g.ho_sal - c.ho_sal) else null end) IS NOT NULL \n" +
"             and (case (g.ho_sal > c.ho_sal) when true then (g.ho_sal - c.ho_sal) else null end) > '00:00:00' ) ) \n" +
"             order by fe_dia asc,tx_ape, tx_nom ) p\n" +
"             inner join tbm_suetra t on (t.co_emp=p.co_emp and t.co_tra=p.co_tra and t.co_rub = 1)\n" +
"             group by p.co_emp,p.co_tra, p.tx_ape || ' ' || p.tx_nom, t.nd_valrub\n" +
"             order by co_emp, co_tra, empleado";
            CallableStatement cs = conex.prepareCall(strQurey);
            ResultSet rs = cs.executeQuery();
            
            if(rs.next()){
                System.out.println( "Valor --->" +  rs.getString("nd_valDesAtr") );
                valorDevolver = rs.getDouble("nd_valDesAtr") ;
            }
            cs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorDevolver;
    }    
    public double getValorDevolver(Connection conex, String coEmpresa, String coTra, String fecAJustificar, String stJusmarinc ){
        double valorDevolver = 0 ;
        try {
            String strQurey = "select 1, st_jusmarinc, ne_per, ne_ani, ne_mes from tbm_fecCorRolPag f, tbm_cabconasitra a \n" +
                        "where f.co_emp = "+ coEmpresa +" and a.co_tra = "+ coTra + " \n" +
                        "	and "+ fecAJustificar + " between fe_des and fe_has\n" +
                        "	and "+ fecAJustificar + " = fe_dia \n" +
                        "	and st_revfal = 'S' and st_jusAtr is null";
            CallableStatement cs = conex.prepareCall(strQurey);
            ResultSet rs = cs.executeQuery();
            if(rs.next()){
                if (rs.getInt("ne_per") == 2){
                    valorDevolver = calcularValor(conex, coEmpresa, coTra, fecAJustificar);
                }else{
                    strQurey = "select 1 from tbm_cabRolPag where ne_ani=" + rs.getInt("ne_ani")+ " and ne_mes=" + rs.getInt("ne_mes") 
                                + " and ne_per = 2 " + " and co_emp = " + coEmpresa  + "and st_reg = 'A' and co_tipdoc = 192";
                    cs = conex.prepareCall(strQurey);
                    rs = cs.executeQuery();
                    if(rs.next())
                        valorDevolver = calcularValor(conex, coEmpresa, coTra, fecAJustificar);
                }    
            }
            cs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return valorDevolver;
    }
    
    public boolean grabarIngProgramado(Connection conex, String coEmp, String coTra, double valor, String txObs2){
        boolean resp = false ;
        try {
            int intCoEgr=0;
            String strSQL="SELECT case when (Max(co_egr)+1) is null then 1 else Max(co_egr)+1 end as co_egr  FROM tbm_cabingegrprgtra WHERE " +
                    " co_emp=" + coEmp ;
            java.sql.Statement stmLoc = conex.createStatement();
            ResultSet rs = stmLoc.executeQuery(strSQL);
             if(rs.next())
                 intCoEgr = rs.getInt("co_egr");

            String strTx_tipEgrPrg ="D";
            String strTx_tipCuo = "M";
            String strNeNumCuo = "1"; //registo activo
            String strSt_Reg = "A"; //registo activo

            String strSql="";
            strSql+="INSERT INTO tbm_cabingegrprgtra (co_emp , co_egr , co_tra , co_rub , fe_doc , "+"\n";
            strSql+="tx_tipegrprg , fe_pricuo , fe_ultcuo , nd_valegrprg , ne_numcuo , tx_tipcuo , st_reg , fe_ing , co_usring, tx_obs2 ) "+"\n";
            strSql+="VALUES ( " +  coEmp +" , " + intCoEgr +" , " + coTra +" , 22, current_date" +" , \n";
            strSql+=objUti.codificar(strTx_tipEgrPrg) +" , \n";
            Calendar cal = Calendar.getInstance();
            String strFePriCuo=cal.get(Calendar.YEAR)+"-"+((cal.get(Calendar.MONTH))+1)+"-"+"01";
            strSql+=objUti.codificar(strFePriCuo) +" , \n";
            strSql+=objUti.codificar(strFePriCuo) +" , \n";
            strSql+=valor +" , \n";
            strSql+=objUti.codificar(strNeNumCuo) +" , \n";
            strSql+=objUti.codificar(strTx_tipCuo) +" , \n";
            strSql+=objUti.codificar(strSt_Reg) +" , \n";
            strSql+="current_timestamp" +" , \n";
            strSql+=objParSis.getCodigoUsuario() + ",'" + txObs2 +"' );";
            stmLoc.executeUpdate(strSql);

            strSql="";
            strSql+="INSERT INTO tbm_detingegrprgtra (co_emp , co_egr , co_reg , fe_cuo , nd_valcuo , st_reg)"+"\n";
            strSql+="VALUES ( " + coEmp +" , " + intCoEgr +" , 1 , "+ objUti.codificar(strFePriCuo) +" , \n";
            strSql+=valor +" , " + objUti.codificar(strSt_Reg) + " );";
            stmLoc.executeUpdate(strSql);
            resp = true ;
        } catch (SQLException ex) {
            resp = false ;
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
        Calendar calFecReg=Calendar.getInstance();
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