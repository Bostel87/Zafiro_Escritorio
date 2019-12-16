/*
 * ZafCom49.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */

package Contabilidad.ZafCon19;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafPerUsr.ZafPerUsr;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCon19 extends javax.swing.JInternalFrame 
{
    //para empresas
    static final int INT_TBL_EMP_LIN=0;                         //Línea.
    static final int INT_TBL_EMP_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_EMP_NOM_EMP=3;                     //Nombre de la empresa.

    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_NOM_EMP=2;
    final int INT_TBL_DAT_COD_CTA=3;
    final int INT_TBL_DAT_NUM_CTA=4;
    final int INT_TBL_DAT_NOM_CTA=5;
    final int INT_TBL_DAT_SAL_CTA_CTB=6;
    final int INT_TBL_DAT_CHQ_BAN_ASI=7;
    final int INT_TBL_DAT_BUT_CHQ_BAN_ASI=8;
    final int INT_TBL_DAT_VAL_CTA_TRN=9;
    final int INT_TBL_DAT_VAL_AUT_PAG=10;
    final int INT_TBL_DAT_BUT_VAL_AUT_PAG=11;
    final int INT_TBL_DAT_CHQ_FEC_EMI=12;
    final int INT_TBL_DAT_BUT_CHQ_FEC_EMI=13;
    final int INT_TBL_DAT_VAL_EGR_CUS=14;
    final int INT_TBL_DAT_BUT_VAL_EGR_CUS=15;
    final int INT_TBL_DAT_SAL_DIS=16;
    final int INT_TBL_DAT_BUT_SAL_DIS=17;
    final int INT_TBL_DAT_SAL_CON=18;
    final int INT_TBL_DAT_BUT_SAL_CON=19;
    final int INT_TBL_DAT_SAL_FIN_DIS=20;
    final int INT_TBL_DAT_SAL_FIN_CON=21;
    
    //Constantes para el arreglo arlDatCiu
    final int INT_ARL_CIU_COD_CIU=0;
    final int INT_ARL_CIU_FEC_COB=1;
        
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModEmp;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux, strFecCob_HaciaAtr_SalCon, strFecCob_ChqMisBco_SalDis, strFecCob_HaciaAtr_ChqBcoDif_SalDis, strFecAct;
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                     //true: Continua la ejecucián del hilo.
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblBus objTblBus;
   
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;

    private boolean blnButCon, blnButCer;
    private ZafPerUsr objPerUsr;
    private ZafMouMotAdaEmp objMouMotAdaEmp;                  //ToolTipText en TableHeader de empresas.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.

    private boolean blnMarTodChkTblEmp=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private ZafTblFilCab objTblFilCab;
    private ZafVenCon vcoCta;                           //Ventana de consulta "Item".
    private ZafTblCelRenBut objTblCelRenButChqBanAsi, objTblCelRenButValAutPag, objTblCelRenButChqFec;
    private ZafTblCelEdiButDlg objTblCelEdiButChqBanAsi, objTblCelEdiButValAutPag, objTblCelEdiButChqFec;
    private ZafTblCelRenBut objTblCelRenButValEgrCus, objTblCelRenButSalCon, objTblCelRenButSalDis;
    private ZafTblCelEdiButDlg objTblCelEdiButValEgrCus, objTblCelEdiButSalCon, objTblCelEdiButSalDis;

    private ZafCon19_01 objCon19_01, objCon19_02, objCon19_03, objCon19_04, objCon19_05, objCon19_06;
    private String strCodEmpChk;
    private ZafTblTot objTblTot;
    private String strDesLarCta, strDesCorCta;

    private final int INT_COD_GRP_BAN=8;

    //para almacenar los registros de la consulta de "cheques que tienen asignado un banco"
    private ArrayList arlRegChqBanAsi, arlDatChqBanAsi;
    private int INT_ARL_CHQ_BAN_ASI_COD_EMP=0;
    private int INT_ARL_CHQ_BAN_ASI_COD_LOC=1;
    private int INT_ARL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ=2;
    private int INT_ARL_CHQ_BAN_ASI_COD_DOC_REC_CHQ=3;
    private int INT_ARL_CHQ_BAN_ASI_COD_REG_REC_CHQ=4;
    private int INT_ARL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN=5;
    private int INT_ARL_CHQ_BAN_ASI_COD_CLI=6;
    private int INT_ARL_CHQ_BAN_ASI_NOM_CLI=7;
    private int INT_ARL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS=8;
    private int INT_ARL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS=9;
    private int INT_ARL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS=10;
    private int INT_ARL_CHQ_BAN_ASI_NUM_CTA=11;
    private int INT_ARL_CHQ_BAN_ASI_NUM_CHQ=12;
    private int INT_ARL_CHQ_BAN_ASI_VAL_CHQ=13;
    private int INT_ARL_CHQ_BAN_ASI_FEC_VEN=14;
    private int INT_ARL_CHQ_BAN_ASI_FEC_ASG_BCO=15;
    private int INT_ARL_CHQ_BAN_ASI_COD_CTA_BAN_ASG=16;

    //para almacenar los registros de la consulta de "valores autorizados a pagar"
    private ArrayList arlRegValAutPag, arlDatValAutPag;
    private int INT_ARL_VAL_AUT_PAG_COD_EMP=0;
    private int INT_ARL_VAL_AUT_PAG_COD_CLI=1;
    private int INT_ARL_VAL_AUT_PAG_NOM_CLI=2;
    private int INT_ARL_VAL_AUT_PAG_COD_LOC=3;
    private int INT_ARL_VAL_AUT_PAG_COD_TIP_DOC=4;
    private int INT_ARL_VAL_AUT_PAG_DES_COR_TIP_DOC=5;
    private int INT_ARL_VAL_AUT_PAG_DES_LAR_TIP_DOC=6;
    private int INT_ARL_VAL_AUT_PAG_COD_DOC=7;
    private int INT_ARL_VAL_AUT_PAG_COD_REG=8;
    private int INT_ARL_VAL_AUT_PAG_NUM_DOC=9;
    private int INT_ARL_VAL_AUT_PAG_FEC_DOC=10;
    private int INT_ARL_VAL_AUT_PAG_DIA_CRE=11;
    private int INT_ARL_VAL_AUT_PAG_FEC_VEN=12;
    private int INT_ARL_VAL_AUT_PAG_MON_PAG=13;
    private int INT_ARL_VAL_AUT_PAG_ABO=14;
    private int INT_ARL_VAL_AUT_PAG_VAL_PND=15;
    private int INT_ARL_VAL_AUT_PAG_COD_CTA=16;

    //para almacenar los registros de la consulta de "cheques emitidos"
    private ArrayList arlRegChqEmi, arlDatChqEmi;
    private int INT_ARL_CHQ_EMI_COD_EMP=0;
    private int INT_ARL_CHQ_EMI_COD_CLI=1;
    private int INT_ARL_CHQ_EMI_NOM_CLI=2;
    private int INT_ARL_CHQ_EMI_COD_LOC=3;
    private int INT_ARL_CHQ_EMI_COD_TIP_DOC=4;
    private int INT_ARL_CHQ_EMI_DES_COR_TIP_DOC=5;
    private int INT_ARL_CHQ_EMI_DES_LAR_TIP_DOC=6;
    private int INT_ARL_CHQ_EMI_COD_DOC=7;
    private int INT_ARL_CHQ_EMI_NUM_DOC_UNO=8;
    private int INT_ARL_CHQ_EMI_NUM_DOC_DOS=9;
    private int INT_ARL_CHQ_EMI_FEC_DOC=10;
    private int INT_ARL_CHQ_EMI_FEC_VEN=11;
    private int INT_ARL_CHQ_EMI_VAL_DOC=12;
    private int INT_ARL_CHQ_EMI_CTA_BAN=13;

    // Para almacenar los registros de la consulta "Cheques emitidos pendientes de entregar al proveedor"
    private ArrayList arlRegValEgrCus, arlDatValEgrCus;
    private int INT_ARL_VAL_EGR_CUS_COD_EMP=0;
    private int INT_ARL_VAL_EGR_CUS_COD_CTA=1;
    private int INT_ARL_VAL_EGR_CUS_COD_CLI=2;
    private int INT_ARL_VAL_EGR_CUS_NOM_CLI=3;
    private int INT_ARL_VAL_EGR_CUS_DES_COR_TIP_DOC=4;
    private int INT_ARL_VAL_EGR_CUS_DES_LAR_TIP_DOC=5;
    private int INT_ARL_VAL_EGR_CUS_NUM_DOC=6;
    private int INT_ARL_VAL_EGR_CUS_FEC_DOC=7;
    private int INT_ARL_VAL_EGR_CUS_FEC_VEN=8;
    private int INT_ARL_VAL_EGR_CUS_VAL_CHQ=9;
    
    // Para almacenar los registros de la consulta "Saldo contable"
    private ArrayList arlRegSalCon, arlDatSalCon;
    private int INT_ARL_SAL_CON_COD_EMP=0;
    private int INT_ARL_SAL_CON_COD_CTA=1;
    private int INT_ARL_SAL_CON_COD_CLI=2;
    private int INT_ARL_SAL_CON_NOM_CLI=3;
    private int INT_ARL_SAL_CON_COD_BCO=4;
    private int INT_ARL_SAL_CON_DES_COR_BCO=5;
    private int INT_ARL_SAL_CON_DES_LAR_BCO=6;
    private int INT_ARL_SAL_CON_NUM_CTACHQ=7;
    private int INT_ARL_SAL_CON_NUM_CHQ=8;
    private int INT_ARL_SAL_CON_MON_CHQ=9;
    private int INT_ARL_SAL_CON_FEC_VENCHQ=10;
    
    // Para almacenar los registros de la consulta "Saldo disponible"
    private ArrayList arlRegSalDis, arlDatSalDis;
    private int INT_ARL_SAL_DIS_COD_EMP=0;
    private int INT_ARL_SAL_DIS_COD_CTA=1;
    private int INT_ARL_SAL_DIS_COD_CLI=2;
    private int INT_ARL_SAL_DIS_NOM_CLI=3;
    private int INT_ARL_SAL_DIS_COD_BCO=4;
    private int INT_ARL_SAL_DIS_DES_COR_BCO=5;
    private int INT_ARL_SAL_DIS_DES_LAR_BCO=6;
    private int INT_ARL_SAL_DIS_NUM_CTACHQ=7;
    private int INT_ARL_SAL_DIS_NUM_CHQ=8;
    private int INT_ARL_SAL_DIS_MON_CHQ=9;
    private int INT_ARL_SAL_DIS_FEC_VENCHQ=10;
    
    private ArrayList arlRegCiu, arlDatCiu;
    private ZafDatePicker dtpFecDoc;
    private java.util.Date datFecAux;
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCon19(ZafParSis obj) 
    {
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            objPerUsr=new ZafPerUsr(objParSis);

            if (!configurarFrm())
                exitForm();
            
            configurarFil();
            arlDatChqBanAsi=new ArrayList();
            arlDatValAutPag=new ArrayList();
            arlDatChqEmi=new ArrayList();
            arlDatValEgrCus=new ArrayList();
            arlDatSalCon=new ArrayList();
            arlDatSalDis=new ArrayList();
            arlDatCiu=new ArrayList();
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

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panTblFil = new javax.swing.JPanel();
        panLisEmp = new javax.swing.JPanel();
        spnEmp = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        optFilReg = new javax.swing.JRadioButton();
        optTodReg = new javax.swing.JRadioButton();
        lblCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        panFilFec = new javax.swing.JPanel();
        optDia = new javax.swing.JRadioButton();
        optMes = new javax.swing.JRadioButton();
        cboMesCor = new javax.swing.JComboBox();
        cboAnoCor = new javax.swing.JComboBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTotal = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        panTblFil.setLayout(new java.awt.BorderLayout());

        panLisEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
        panLisEmp.setLayout(new java.awt.BorderLayout());

        spnEmp.setViewportView(tblEmp);

        panLisEmp.add(spnEmp, java.awt.BorderLayout.CENTER);

        panTblFil.add(panLisEmp, java.awt.BorderLayout.CENTER);

        panFil.add(panTblFil);
        panTblFil.setBounds(0, 140, 679, 30);

        jPanel3.setPreferredSize(new java.awt.Dimension(0, 150));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 64));
        jPanel1.setLayout(null);

        optFilReg.setText("Sólo las cuentas bancarias que cumplan el criterio seleccionado");
        jPanel1.add(optFilReg);
        optFilReg.setBounds(0, 20, 400, 16);

        optTodReg.setSelected(true);
        optTodReg.setText("Todas las cuentas bancarias");
        optTodReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodRegActionPerformed(evt);
            }
        });
        jPanel1.add(optTodReg);
        optTodReg.setBounds(0, 3, 400, 16);

        lblCta.setText("Cuenta:");
        jPanel1.add(lblCta);
        lblCta.setBounds(24, 38, 60, 20);
        jPanel1.add(txtCodCta);
        txtCodCta.setBounds(60, 38, 40, 20);

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
        jPanel1.add(txtDesCorCta);
        txtDesCorCta.setBounds(100, 38, 130, 20);

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
        jPanel1.add(txtDesLarCta);
        txtDesLarCta.setBounds(230, 38, 350, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        jPanel1.add(butCta);
        butCta.setBounds(580, 38, 20, 20);

        jPanel3.add(jPanel1, java.awt.BorderLayout.NORTH);

        panFil.add(jPanel3);
        jPanel3.setBounds(0, 170, 679, 150);

        panFilFec.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha de corte"));
        panFilFec.setPreferredSize(new java.awt.Dimension(100, 150));
        panFilFec.setLayout(null);

        optDia.setText("Día");
        optDia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optDiaActionPerformed(evt);
            }
        });
        panFilFec.add(optDia);
        optDia.setBounds(53, 20, 50, 20);

        optMes.setSelected(true);
        optMes.setText("Mes");
        optMes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optMesStateChanged(evt);
            }
        });
        optMes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optMesActionPerformed(evt);
            }
        });
        panFilFec.add(optMes);
        optMes.setBounds(53, 42, 50, 20);
        panFilFec.add(cboMesCor);
        cboMesCor.setBounds(110, 42, 180, 20);
        panFilFec.add(cboAnoCor);
        cboAnoCor.setBounds(292, 42, 64, 20);

        panFil.add(panFilFec);
        panFilFec.setBounds(0, 0, 679, 90);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTotal.setViewportView(tblTot);

        panRpt.add(spnTotal, java.awt.BorderLayout.SOUTH);

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

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    
                        
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if(isCamVal()){
            if (butCon.getText().equals("Consultar")){
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else{
                blnCon=false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicacián. */
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

    private void optTodRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodRegActionPerformed
        // TODO add your handling code here:
        if(optTodReg.isSelected()){
            optFilReg.setSelected(false);
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
        }
        else{
            optFilReg.setSelected(true);
        }
    }//GEN-LAST:event_optTodRegActionPerformed

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        // TODO add your handling code here:
        txtDesCorCta.transferFocus();
        setPuntosCta();
}//GEN-LAST:event_txtDesCorCtaActionPerformed

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        // TODO add your handling code here:
        strDesCorCta=txtDesCorCta.getText();
        txtDesCorCta.selectAll();
}//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
            if (txtDesCorCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
                objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(1);
            }
        } else
            txtDesCorCta.setText(strDesCorCta);

        if(txtCodCta.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
        }

    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
        // TODO add your handling code here:
        txtDesLarCta.transferFocus();
}//GEN-LAST:event_txtDesLarCtaActionPerformed

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
        // TODO add your handling code here:
        strDesLarCta=txtDesLarCta.getText();
        txtDesLarCta.selectAll();
}//GEN-LAST:event_txtDesLarCtaFocusGained

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta)) {
            if (txtDesLarCta.getText().equals("")) {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                objTblMod.removeAllRows();
            } else {
                mostrarVenConCta(2);
            }
        } else
            txtDesLarCta.setText(strDesLarCta);

        if(txtCodCta.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
        }

}//GEN-LAST:event_txtDesLarCtaFocusLost

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        // TODO add your handling code here:
        mostrarVenConCta(0);
        if(txtCodCta.getText().length()>0){
            optFilReg.setSelected(true);
            optTodReg.setSelected(false);
        }
}//GEN-LAST:event_butCtaActionPerformed

    private void optDiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optDiaActionPerformed
        if(optDia.isSelected())
        optMes.setSelected(false);
        else
        optMes.setSelected(true);
    }//GEN-LAST:event_optDiaActionPerformed

    private void optMesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optMesStateChanged
        if(optMes.isSelected())
        optDia.setSelected(false);
        else
        optDia.setSelected(true);
    }//GEN-LAST:event_optMesStateChanged

    private void optMesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optMesActionPerformed
        if(optMes.isSelected())
        optDia.setSelected(false);
        else
        optDia.setSelected(true);
    }//GEN-LAST:event_optMesActionPerformed




    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCta;
    private javax.swing.JComboBox cboAnoCor;
    private javax.swing.JComboBox cboMesCor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCta;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optDia;
    private javax.swing.JRadioButton optFilReg;
    private javax.swing.JRadioButton optMes;
    private javax.swing.JRadioButton optTodReg;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLisEmp;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panTblFil;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnEmp;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesLarCta;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            blnButCon=false;
            blnButCer=false;
            
            if(objPerUsr.isOpcionEnabled(724)){
                blnButCon=true;
            }
            if(objPerUsr.isOpcionEnabled(725)){
                blnButCer=true;
            }

            strAux=objParSis.getNombreMenu() + "v0.2.6";
            this.setTitle(strAux);
            lblTit.setText(strAux);
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_DAT_NOM_EMP,"Nom.Emp.");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cod.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Nombre");
            vecCab.add(INT_TBL_DAT_SAL_CTA_CTB,"Saldo");
            vecCab.add(INT_TBL_DAT_CHQ_BAN_ASI,"Chq.Ban.Asi.");
            vecCab.add(INT_TBL_DAT_BUT_CHQ_BAN_ASI,"");
            vecCab.add(INT_TBL_DAT_VAL_CTA_TRN,"Tránsito");
            vecCab.add(INT_TBL_DAT_VAL_AUT_PAG,"Val.Aut.Pag.");
            vecCab.add(INT_TBL_DAT_BUT_VAL_AUT_PAG,"");
            vecCab.add(INT_TBL_DAT_CHQ_FEC_EMI,"Chq.Fec.");
            vecCab.add(INT_TBL_DAT_BUT_CHQ_FEC_EMI,"");
            vecCab.add(INT_TBL_DAT_VAL_EGR_CUS,"Val.Egr.Cus.");
            vecCab.add(INT_TBL_DAT_BUT_VAL_EGR_CUS,"");
            vecCab.add(INT_TBL_DAT_SAL_DIS,"Sal.Dis.");
            vecCab.add(INT_TBL_DAT_BUT_SAL_DIS,"");
            vecCab.add(INT_TBL_DAT_SAL_CON,"Sal.Con.");
            vecCab.add(INT_TBL_DAT_BUT_SAL_CON,"");
            vecCab.add(INT_TBL_DAT_SAL_FIN_DIS,"Sal.Fin.Dis.");
            vecCab.add(INT_TBL_DAT_SAL_FIN_CON,"Sal.Fin.Con.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Tamaáo de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(24);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(144);
            tcmAux.getColumn(INT_TBL_DAT_SAL_CTA_CTB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHQ_BAN_ASI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_BAN_ASI).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CTA_TRN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_AUT_PAG).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_AUT_PAG).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_CHQ_FEC_EMI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_FEC_EMI).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EGR_CUS).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_EGR_CUS).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_SAL_DIS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_DIS).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_SAL_CON).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_CON).setPreferredWidth(16);
            tcmAux.getColumn(INT_TBL_DAT_SAL_FIN_DIS).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_SAL_FIN_CON).setPreferredWidth(90);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tcmAux.getColumn(INT_TBL_DAT_SAL_CTA_CTB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CHQ_BAN_ASI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CTA_TRN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_AUT_PAG).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CHQ_FEC_EMI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_EGR_CUS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_FIN_DIS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_FIN_CON).setCellRenderer(objTblCelRenLbl);
                                              
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_CTA).setResizable(false);

            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_CHQ_BAN_ASI);
            vecAux.add("" + INT_TBL_DAT_BUT_VAL_AUT_PAG);
            vecAux.add("" + INT_TBL_DAT_BUT_CHQ_FEC_EMI);
            vecAux.add("" + INT_TBL_DAT_BUT_VAL_EGR_CUS);
            vecAux.add("" + INT_TBL_DAT_BUT_SAL_DIS);
            vecAux.add("" + INT_TBL_DAT_BUT_SAL_CON);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("Cuenta por Pagar");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VAL_EGR_CUS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_EGR_CUS));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null;

            objTblHeaColGrpAmeSur = new ZafTblHeaColGrp("Cuentas por Cobrar");
            objTblHeaColGrpAmeSur.setHeight(16);
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAL_DIS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_DIS));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_SAL_CON));
            objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_CON));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
            objTblHeaColGrpAmeSur = null;
            
            objTblCelRenButChqBanAsi=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_BAN_ASI).setCellRenderer(objTblCelRenButChqBanAsi);
            objCon19_01=new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("1"));
            objTblCelEdiButChqBanAsi= new ZafTblCelEdiButDlg(objCon19_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_BAN_ASI).setCellEditor(objTblCelEdiButChqBanAsi);

            objTblCelEdiButChqBanAsi.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_01.setArregloDatos(arlDatChqBanAsi);
                    objCon19_01.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_01.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_01.cargarAsignacionBcos();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelRenButValAutPag=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_AUT_PAG).setCellRenderer(objTblCelRenButValAutPag);
            objCon19_02=new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("2"));
            objTblCelEdiButValAutPag= new ZafTblCelEdiButDlg(objCon19_02);
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_AUT_PAG).setCellEditor(objTblCelEdiButValAutPag);

            objTblCelEdiButValAutPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_02.setArregloDatos(arlDatValAutPag);
                    objCon19_02.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_02.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_02.cargarValoresAutorizados();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelRenButChqFec=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_FEC_EMI).setCellRenderer(objTblCelRenButChqFec);
            objCon19_03=new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("3"));
            objTblCelEdiButChqFec= new ZafTblCelEdiButDlg(objCon19_03);
            tcmAux.getColumn(INT_TBL_DAT_BUT_CHQ_FEC_EMI).setCellEditor(objTblCelEdiButChqFec);

            objTblCelEdiButChqFec.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_03.setArregloDatos(arlDatChqEmi);
                    objCon19_03.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_03.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_03.cargarChequesEmitidos();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
                        
            objTblCelRenButValEgrCus=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_EGR_CUS).setCellRenderer(objTblCelRenButValEgrCus);
            objCon19_04=new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("4"));
            
            objTblCelEdiButValEgrCus= new ZafTblCelEdiButDlg(objCon19_04);
            tcmAux.getColumn(INT_TBL_DAT_BUT_VAL_EGR_CUS).setCellEditor(objTblCelEdiButValEgrCus);

            objTblCelEdiButValEgrCus.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_04.setArregloDatos(arlDatValEgrCus);
                    objCon19_04.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_04.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_04.cargarValEgrCus();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            objTblCelRenButSalCon = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_CON).setCellRenderer(objTblCelRenButSalCon);
            objCon19_05 = new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("5"));
            
            objTblCelEdiButSalCon = new ZafTblCelEdiButDlg(objCon19_05);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_CON).setCellEditor(objTblCelEdiButSalCon);

            objTblCelEdiButSalCon.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_05.setArregloDatos(arlDatSalCon);
                    objCon19_05.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_05.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_05.cargarSalCon();
                }
            });
            
            objTblCelRenButSalDis = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_DIS).setCellRenderer(objTblCelRenButSalDis);
            objCon19_06 = new ZafCon19_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, Integer.parseInt("6"));
            
            objTblCelEdiButSalDis = new ZafTblCelEdiButDlg(objCon19_06);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SAL_DIS).setCellEditor(objTblCelEdiButSalDis);

            objTblCelEdiButSalDis.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objCon19_06.setArregloDatos(arlDatSalDis);
                    objCon19_06.setCodEmp(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP)));
                    objCon19_06.setCuenta(Integer.parseInt("" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CTA)));
                    objCon19_06.cargarSalDis();
                }
            });

            tcmAux=null;

            configurarVenConCta();
            txtCodCta.setVisible(false);
            txtCodCta.setEditable(false);


            //Configurar JTable: Ocultar columnas del sistema.
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo())
            {  objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
               objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_EMP, tblDat);
            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_CTA_TRN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHQ_BAN_ASI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CHQ_BAN_ASI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHQ_FEC_EMI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_CHQ_FEC_EMI, tblDat);
            
            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_SAL_CTA_CTB, INT_TBL_DAT_CHQ_BAN_ASI, INT_TBL_DAT_VAL_CTA_TRN,INT_TBL_DAT_VAL_AUT_PAG,INT_TBL_DAT_CHQ_FEC_EMI,INT_TBL_DAT_VAL_EGR_CUS,INT_TBL_DAT_SAL_DIS,INT_TBL_DAT_SAL_CON,INT_TBL_DAT_SAL_FIN_DIS,INT_TBL_DAT_SAL_FIN_CON};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTot, intCol);

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarTblEmp();
                cargarEmp();
            }
            else{
                panTblFil.setVisible(false);
                tblEmp.setVisible(false);
            }
            


        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
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



    /**
     * Esta función permite establecer la conexión para consultas DML
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        int i, j, k, intNumDiaLabBco_a_contar, intArlCiuCodCiu;
        boolean blnRes = true, blnSeguir, blnEsSab, blnEsDiaLab, blnEsDiaLabBco;
        java.util.Date datAux;
        
        strAux = "";
        
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            if (con!=null){
                if(! txtCodCta.getText().toString().equals(""))
                    strAux+="   WHERE e1.co_cta=" + txtCodCta.getText()  + "";
                
                stm=con.createStatement();
                
                blnSeguir = true;
                blnEsDiaLab = true;
                i = 0;
                strFecAct = "";
                strFecCob_HaciaAtr_SalCon = "";
                if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                {
                    String strFecCor="" + cboAnoCor.getSelectedItem() +"-"+ ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1)) +"-01";
                    strSQL ="select date_trunc('month','"+strFecCor+"'::date)+'1month'::interval-'1day'::interval as fe_srv,  date_trunc('month','"+strFecCor+"'::date)+'1month'::interval-'2day'::interval as fe_ptoini ";
                }else{
                    if (dtpFecDoc.isFecha())
                    strSQL = "select '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"' as fe_srv,  ('"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"'::date - 1) as fe_ptoini";
                }
                
                //strSQL = "select current_date as fe_srv, (current_date - 1) as fe_ptoini";
                
                rst = stm.executeQuery(strSQL);
                
                if (rst.next())
                {  strFecAct = rst.getString("fe_srv");
                   strFecCob_HaciaAtr_SalCon = rst.getString("fe_ptoini");
                }
                
                //Calculo de strFecCob_HaciaAtr_SalCon: Fecha de cobro hacia atras para obtener Saldo Contable
                //-------------------------------------
                //strFecAct = "2014-02-18"; //DBN_Prueba
                //strFecCob_HaciaAtr_SalCon = "2014-02-17"; //DBN_Prueba
                //Dentro del while se va a calcular la fecha de cobro hacia atras. Se tomara como punto de partida 
                //la (fecha actual - 1) y de ser necesario, se debe ir hacia atras.
                //Si la fecha es dia laborable (incluso sabado), esa es la fecha que se tomara como strFecCob_HaciaAtr_SalCon
                //Ej. 1: Si Fe_act = 17/02/2014 (lun), se retrocede un dia (dom) y ese dia no es laborable. Se retrocede un dia (sab) y ese dia es laborable.
                //       Entonces, strFecCob_HaciaAtr_SalCon debera ser 15/02/2014 (sab) debido a que en una de las condiciones del Where que trae los valores
                //       para Saldo Contable, hay un between entre strFecCob_HaciaAtr_SalCon y Fe_act.
                //Ej. 2: Si Fe_act = 18/02/2014 (mar), se retrocede un dia (lun) y ese dia es laborable. Ya no se debe retroceder y 
                //       strFecCob_HaciaAtr_SalCon = "" debido a que en una de las condiciones del Where que trae los valores para Saldo Contable, no se debe
                //       hacer between sino que se debe traer los cheques cobrados en la fecha indicada por Fe_act.
                while (blnSeguir == true)
                {  i++; //i va a indicar el num. de veces que se esta evaluando una fecha para determinar si es laborable o no.
                   //Se va a verificar si es un dia laborable
                   strSQL =  "SELECT     a2.tx_tipdia ";
                   strSQL += "FROM       tbm_loc as a1 ";
                   strSQL += "INNER JOIN tbm_calciu as a2 on a1.co_ciu = a2.co_ciu ";
                   strSQL += "WHERE      a2.tx_tipdia = 'L' ";
                   strSQL += "           and a2.fe_dia = '" + strFecCob_HaciaAtr_SalCon + "'";
                   
                   if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   {  strSQL += "        and a1.co_emp = 1";
                      strSQL += "        and a1.co_loc = 4";
                   }
                   else
                   {  strSQL += "        and a1.co_emp = " + objParSis.getCodigoEmpresa();
                      strSQL += "        and a1.co_loc = " + objParSis.getCodigoLocal();
                   }
                   
                   rst = stm.executeQuery(strSQL);

                   if (rst.next())
                      blnEsDiaLab = true;
                   else
                      blnEsDiaLab = false;
                   
                   if (blnEsDiaLab == true)
                   {  datAux = objUti.parseDate(strFecCob_HaciaAtr_SalCon, "yyyy-MM-dd");
                      j = datAux.getDay();
                      
                      if (i == 1 || (j >= 1 && j <= 5))
                      {  //Si i = 1, significa que la 1ra fecha evaluada (para determinar si fue laborable o no) resulto ser un dia laborable.
                         //Si j esta entre 1 y 5, significa que la fecha 'strFecCob_HaciaAtr_SalCon' esta entre lunes a viernes.
                         //Si alguna de estas 2 condiciones se cumple, no se necesita tomar una fecha hacia atras para obtener el Saldo Contable
                         strFecCob_HaciaAtr_SalCon = "";
                      }
                      //Se encontro una fecha que es dia laborable y por tanto se debe salir del bucle
                      blnSeguir = false;
                   }
                   else
                   {  //No es dia laborable.  Por tanto, se debe restar un dia a la fecha evaluada
                      strSQL = "SELECT cast('" + strFecCob_HaciaAtr_SalCon + "' as date) - 1 as fe_cob";
                      rst = stm.executeQuery(strSQL);
                      if (rst.next())
                         strFecCob_HaciaAtr_SalCon = rst.getString("fe_cob");
                   }
                }
                
                //En el arreglo arlDatCiu se va a guardar por cada ciudad una fecha de cobro (tbm_cabpag.fe_doc) basado en el calculo de (Fe_act - 2)
                //para obtener el saldo disponible para cheques de distintos bancos.
                //Se va a determinar en cuales ciudades se han efectuado cobro de cheques (uso de prog "Cobro de cheques") en el rango
                //de 14 dias hacia atras con respecto a la fecha actual. Se toma como referencia buscar los ultimos 14 dias porque no existe
                //en Ecuador un feriado tan largo que dure 14 dias.  Se va a realizar esto para determinar por cada ciudad la Fe_cobro para cheques de distintos
                //bancos para obtener el saldo disponible debido a que puede ser que, al calcular (Fe_act - 2) puede haber algun feriado local, 
                //y si existe dicho feriado entonces la fecha de cobro sera diferente en el local donde haya feriado con respecto a los otros locales
                strSQL =  "SELECT DISTINCT a2.co_ciu ";
                strSQL += "FROM       tbm_cabpag as a1 ";
                strSQL += "INNER JOIN tbm_loc as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc ";
                strSQL += "WHERE      a1.fe_doc between (cast ('" + strFecAct + "' as date) - 14) and '" + strFecAct + "'";
                strSQL += "           and a1.st_reg = 'A'";
                
                if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo())
                   strSQL += "        and a2.co_emp = " + objParSis.getCodigoEmpresa();
                
                rst = stm.executeQuery(strSQL);
                arlDatCiu.clear();
                
                while (rst.next())
                {  arlRegCiu = new ArrayList();
                   arlRegCiu.add(INT_ARL_CIU_COD_CIU, rst.getInt("co_ciu"));
                   arlRegCiu.add(INT_ARL_CIU_FEC_COB, "");
                   arlDatCiu.add(arlRegCiu);
                }
                
                if (arlDatCiu.size() == 0)
                {  //Si el arreglo arlDatCiu = 0, es un caso raro porque eso significa que en los ultimos 14 dias no se han efectuado cobro de cheques.
                   //Se va a emplear otro mecanismo para alimentar con datos a este arreglo
                   strSQL = "SELECT DISTINCT co_ciu FROM tbm_loc WHERE st_reg in ('A','P') and co_ciu is not null ";
                   
                   if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo())
                      strSQL += "and co_emp = " + objParSis.getCodigoEmpresa();
                   
                   rst = stm.executeQuery(strSQL);
                   arlDatCiu.clear();

                   while (rst.next())
                   {  arlRegCiu = new ArrayList();
                      arlRegCiu.add(INT_ARL_CIU_COD_CIU, rst.getInt("co_ciu"));
                      arlRegCiu.add(INT_ARL_CIU_FEC_COB, "");
                      arlDatCiu.add(arlRegCiu);
                   }
                }   
                
                //Calculo de strFecCob_HaciaAtr_ChqBcoDif_SalDis: Fecha de cobro hacia atras de chq de bancos diferentes para obtener Saldo Disponible
                //-----------------------------------------------
                //Se va a evaluar si la fecha actual es sabado
                datAux = objUti.parseDate(strFecAct, "yyyy-MM-dd");
                j = datAux.getDay();
                
                if (j == 6)
                {  //Como es sabado, el numero de dias laborables bancarios que se debera contar es 3. Esto es porque, si la fecha actual es sabado 08/03/2014,
                   //la variable 'strFecCob_HaciaAtr_ChqBcoDif_SalDis' al final del proceso debera tener la fecha del dia miercoles 05/03/2014 para realizar el calculo
                   //del saldo disponible, debido a que se debe retroceder hasta encontrar 2 dias laborables bancarios para que dicho saldo ya sea efectivo en la cuenta bancaria
                   intNumDiaLabBco_a_contar = 3;
                   strFecCob_ChqMisBco_SalDis = ""; //Al poner "", se indica que no se debera traer los chq del mismo banco porque la Fe_act es sabado
                }
                else
                {  //Como no es sabado, el numero de dias laborables bancarios que se debera contar es 2. Esto es porque, si la fecha actual es lunes 10/03/2014,
                   //la variable 'strFecCob_HaciaAtr_ChqBcoDif_SalDis' al final del proceso debera tener la fecha del dia jueves 06/03/2014 para realizar el calculo
                   //del saldo disponible, debido a que se debe retroceder hasta encontrar 2 dias laborables bancarios para que dicho saldo ya sea efectivo en la cuenta bancaria
                   intNumDiaLabBco_a_contar = 2;
                   strFecCob_ChqMisBco_SalDis = strFecAct;
                }
                
                for (k = 0; k < arlDatCiu.size(); k++)
                {  blnSeguir = true;
                   blnEsDiaLab = true;
                   blnEsSab = true;
                   i = 0;
                   strFecCob_HaciaAtr_ChqBcoDif_SalDis = "";
                   strSQL = "select cast('" + strFecAct + "' as date) - 1 as fe_ptoini";
                   rst = stm.executeQuery(strSQL);
                
                   if (rst.next())
                      strFecCob_HaciaAtr_ChqBcoDif_SalDis = rst.getString("fe_ptoini");
                   
                   //strFecCob_HaciaAtr_ChqBcoDif_SalDis = "2014-03-07"; //DBN_Prueba
                   //Dentro del while se va a calcular la fecha de cobro hacia atras. Se tomara como punto de partida
                   //la (fecha actual - 1) y de ser necesario, se debe ir hacia atras hasta encontrar los dias laborables bancarios indicado por intNumDiaLabBco_a_contar
                   while (blnSeguir == true)
                   {  blnEsDiaLabBco = false;
                      //Se va a verificar si es un dia laborable
                      strSQL =  "SELECT tx_tipdia ";
                      strSQL += "FROM   tbm_calciu ";
                      strSQL += "WHERE  co_ciu = " + objUti.getIntValueAt(arlDatCiu, k, INT_ARL_CIU_COD_CIU);
                      strSQL += "       and tx_tipdia = 'L' ";
                      strSQL += "       and fe_dia = '" + strFecCob_HaciaAtr_ChqBcoDif_SalDis + "'";

                      rst = stm.executeQuery(strSQL);
                   
                      if (rst.next())
                         blnEsDiaLab = true;
                      else
                         blnEsDiaLab = false;

                      if (blnEsDiaLab == true)
                      {  //Es dia laborable.  Ahora se va a evaluar si es sabado
                         datAux = objUti.parseDate(strFecCob_HaciaAtr_ChqBcoDif_SalDis, "yyyy-MM-dd");
                         j = datAux.getDay();

                         if (j == 6)
                            blnEsSab = true;
                         else
                         {  blnEsSab = false;
                            blnEsDiaLabBco = true; //Si es true, indica que se encontro un dia laborable bancario
                            i++; //Contador de las fechas laborables bancarias encontradas. Se saldra del bucle de acuerdo al valor de intNumDiaLabBco_a_contar
                         }
                      }

                      if (blnEsDiaLab == false || (blnEsDiaLab == true && blnEsSab == true) || (blnEsDiaLabBco == true && i < intNumDiaLabBco_a_contar))
                      {  //Aun se debe continuar en el bucle.  Se debe restar un dia a la fecha evaluada
                         strSQL = "SELECT cast('" + strFecCob_HaciaAtr_ChqBcoDif_SalDis + "' as date) - 1 as fe_cob";
                         rst = stm.executeQuery(strSQL);
                         if (rst.next())
                            strFecCob_HaciaAtr_ChqBcoDif_SalDis = rst.getString("fe_cob");
                      }

                      if (blnEsDiaLabBco == true && i == intNumDiaLabBco_a_contar)
                         blnSeguir = false;
                   } //while (blnSeguir == true)
                   
                   //Una vez que se encontro la fecha de cobro para la ciudad evaluada, se guarda esta fecha en el arreglo arlDatCiu
                   objUti.setStringValueAt(arlDatCiu, k, INT_ARL_CIU_FEC_COB, "" + strFecCob_HaciaAtr_ChqBcoDif_SalDis);
                } //for (k = 0; k < arlDatCiu.size(); k++)
                
                
                String strFecCor="" + cboAnoCor.getSelectedItem() + ((cboMesCor.getSelectedIndex()<9)?"0" + (cboMesCor.getSelectedIndex()+1):"" + (cboMesCor.getSelectedIndex()+1));
                //<editor-fold defaultstate="collapsed" desc="/* Filtro: MES */">
                    if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
                    {
                
                
                
                
                
                
                
                strSQL="";
                strSQL+="SELECT e1.co_emp, e1.tx_nomemp, e1.co_cta, e1.tx_codCta, e1.tx_desLar, e1.nd_salCta";
                strSQL+=", e2.nd_chqBanAsi, e3.nd_valAutPag, e4.nd_chqFec, e5.nd_ValEgrCus, e6.nd_salcon, e7.nd_saldis, ";
                strSQL+="   	( CASE WHEN e1.nd_salCta    IS NULL THEN 0 ELSE e1.nd_salCta    END";
                strSQL+=" 	- CASE WHEN e3.nd_valAutPag IS NULL THEN 0 ELSE e3.nd_valAutPag END";
                strSQL+=" 	+ CASE WHEN e5.nd_ValEgrCus IS NULL THEN 0 ELSE e5.nd_ValEgrCus END";
                strSQL+=" 	+ CASE WHEN e7.nd_salDis    IS NULL THEN 0 ELSE e7.nd_salDis    END ) AS nd_salFinDis, ";
                strSQL+="   	( CASE WHEN e1.nd_salCta    IS NULL THEN 0 ELSE e1.nd_salCta    END";
                strSQL+=" 	- CASE WHEN e3.nd_valAutPag IS NULL THEN 0 ELSE e3.nd_valAutPag END";
                strSQL+=" 	+ CASE WHEN e5.nd_ValEgrCus IS NULL THEN 0 ELSE e5.nd_ValEgrCus END";
                strSQL+=" 	+ CASE WHEN e6.nd_salCon    IS NULL THEN 0 ELSE e6.nd_salCon    END ) AS nd_salFinCon ";
                strSQL+=" FROM(";
                
                //--saldo de cuentas bancarias
                strSQL+=" 	SELECT     a1.co_emp, a3.tx_nom as tx_nomemp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, SUM(a2.nd_salcta) AS nd_salCta";
                strSQL+=" 	FROM       tbm_plaCta AS a1";
                strSQL+="       INNER JOIN tbm_salCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+="       LEFT  JOIN tbm_emp AS a3 ON a1.co_emp = a3.co_emp ";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                strSQL+=" 	AND a1.st_ctaBan='S'";//--AND a1.co_cta=2220
                strSQL+=" AND a2.co_per<=" + strFecCor;
//                if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
//                {
//                    strSQL+=" AND b1.co_per<=" + strFecCor;
//                }else //a una fecha que no es mes completo Ejm. 2006-01-12
//                {
//                
//                }
                
                strSQL+=" 	GROUP BY a1.co_emp, a3.tx_nom, a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" ) AS e1";
                
                strSQL+=" LEFT OUTER JOIN(";
                //--listado de cheques que tienen el banco asignado
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_monChq) AS nd_chqBanAsi FROM(";
                strSQL+=" 		SELECT b1.*, b2.co_cta, b2.co_tipdoc FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_tipDocCon";
                strSQL+=" 			, a2.co_cli, a3.tx_nom, a4.co_reg AS co_bco, a4.tx_desCor AS tx_desCorBco, a4.tx_desLar AS tx_desLarBco";
                strSQL+="			, a2.tx_numctachq, a2.nd_monchq, a2.fe_venchq, a2.fe_asitipdoccon AS fe_asiBco";
                strSQL+=" 			FROM tbm_cabrecdoc as a1 INNER JOIN (tbm_detrecdoc as a2 INNER JOIN tbm_var AS a4 ON a2.co_banChq=a4.co_reg AND a4.co_grp=8)";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_cli AS a3 ON a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                   WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+="                   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                         AND a1.st_reg='A' AND a2.st_reg='A' AND a2.co_tipdoccon IS NOT NULL";
                strSQL+=" 			AND a2.nd_valApl=0";
                strSQL+=" 			ORDER BY a1.co_doc, a2.co_reg";
                strSQL+=" 		) AS b1";
                strSQL+=" 		INNER JOIN(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_ctaDeb AS co_cta";
                strSQL+=" 			FROM tbm_cabtipdoc AS a1";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                         where a1.co_emp IN(" + strCodEmpChk + ")";//--and co_ctaDeb=2220
                else
                    strSQL+="                         where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";//--and co_ctaDeb=2220
                strSQL+="		) AS b2";
                strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDocCon=b2.co_tipdoc";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e2";
                strSQL+=" ON e1.co_emp=e2.co_emp AND e1.co_cta=e2.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //--documentos autorizados para pagar y asignado bancos
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_valPnd) AS nd_valAutPag FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" 		, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag";
                strSQL+="                 , a2.nd_abo, (a2.mo_pag + a2.nd_abo) AS nd_valPnd";
                strSQL+=" 		, a1.co_cli, a1.tx_nomCli, a2.co_ctaAutPag AS co_cta";
                strSQL+=" 		FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" 			ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 		INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" 		WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                 AND a1.st_reg NOT IN ('E','I') AND a2.st_reg IN ('A','C')";//--AND a2.co_ctaautpag=2220
                strSQL+=" 		AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e3";
                strSQL+=" ON e1.co_emp=e3.co_emp AND e1.co_cta=e3.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //--Cheques a fecha emitidos
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_monDoc) AS nd_chqFec FROM(";
//                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli";
//                strSQL+=" 		, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
//                strSQL+=" 		, a1.ne_numDoc1, a1.ne_numDoc2, a1.fe_doc, a1.fe_ven, ABS(a1.nd_monDoc) AS nd_monDoc, a1.co_cta";
//                strSQL+=" 		FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ";
//                strSQL+="                             ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" 		INNER JOIN tbm_detPag AS a2";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
//                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                    strSQL+=" 		WHERE a1.co_emp IN(" + strCodEmpChk + ")";
//                else
//                    strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="               AND a1.co_tipDoc IN(32,123) ";//--AND a1.co_cta=2220
//                strSQL+=" 		AND a1.st_reg NOT IN('E','I') AND a1.fe_ven>CURRENT_DATE";
                strSQL+="SELECT b1.*FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli";
                strSQL+=" 	, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" 	, a1.ne_numDoc1, a1.ne_numDoc2, a1.fe_doc, a1.fe_ven, ABS(a1.nd_monDoc) AS nd_monDoc, a1.co_cta";
                strSQL+=" 	FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" 	 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_detPag AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.st_reg NOT IN('E','I') AND a1.fe_ven>CURRENT_DATE";
                strSQL+=" 	ORDER BY a1.ne_numDoc1";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocPrg AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else{
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocUsr AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" 	AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";

                }
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e4";
                strSQL+=" ON e1.co_emp=e4.co_emp AND e1.co_cta=e4.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //----Col. Val.Egr.Cus: Cheques emitidos pendientes de entregar al proveedor
                strSQL+="SELECT     a1.co_emp, b2.co_cta, sum(a2.nd_monHab) as nd_ValEgrCus ";
                strSQL+="FROM       (tbm_cabDia AS a1 ";
                strSQL+="INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="INNER JOIN (tbm_detDia AS a2 ";
                strSQL+="INNER JOIN tbm_plaCta AS b2 ON a2.co_emp=b2.co_emp AND a2.co_cta=b2.co_cta) ";
                strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia ";
                strSQL+="LEFT  JOIN tbm_usr AS c1 ON a2.co_usrconban=c1.co_usr ";
                strSQL+="INNER JOIN tbm_cabpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc and a1.co_dia=a3.co_doc ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipDoc = d1.co_tipDoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipdoc = d1.co_tipdoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and d1.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+="        and a1.co_emp IN (" + strCodEmpChk + ") ";
                else
                   strSQL+="        and a1.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL+="        and b2.co_cta = " + txtCodCta.getText() + " ";
                
                //OJO: Dennis Betancourt puso la fecha quemada '2013-01-01', previa consulta realizada a Ingrid el dia 14/feb/2014
                strSQL+="           AND a1.st_reg NOT IN('E','I') AND a1.fe_dia BETWEEN '2013-01-01' AND '" + strFecAct + "' ";
                strSQL+="           AND b2.st_ctaBan='S' AND (a2.st_conban IS NULL OR a2.st_conban='N') and a2.nd_monhab > 0";
                strSQL+="           AND (a3.st_entchqben is null or a3.st_entchqben = 'N') ";
                
                strSQL+="GROUP BY a1.co_emp, b2.co_cta ";
                strSQL+=") as e5 ";
                strSQL+="ON e1.co_emp=e5.co_emp AND e1.co_cta=e5.co_cta ";
                strSQL+=" LEFT OUTER JOIN(";
                //----Saldo contable: Se presenta la suma de los cheques enviados al deposito en el dia actual
                strSQL+="SELECT b.co_emp, b.co_cta, sum(b.nd_monchq) as nd_salcon FROM ";
                strSQL+="  ( SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                strSQL+="               a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                strSQL+="               a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                strSQL+="               a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg ";
                strSQL+="    FROM       tbm_detrecdoc as a1 ";
                strSQL+="    INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                strSQL+="    INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                strSQL+="    INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                strSQL+="    INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                strSQL+="    LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                strSQL+="    LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                strSQL+="    INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+="        and a1.co_emp IN (" + strCodEmpChk + ")";
                else
                   strSQL+="        and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL+="            and a8.co_ctadeb = " + txtCodCta.getText();
                
                strSQL+="               and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                strSQL+="               and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
                
                if (strFecCob_HaciaAtr_SalCon.equals(""))
                {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Contable
                   strSQL+="            and a5.fe_doc = '" + strFecAct + "'";
                }
                else
                    if (dtpFecDoc.isFecha())
                         strSQL+=" AND (a5.fe_doc BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                   //strSQL+="            and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'";
                
                strSQL+="  ) as b ";
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+=" WHERE b.co_emp IN (" + strCodEmpChk + ")";
                else
                   strSQL+=" WHERE b.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                strSQL+="GROUP BY b.co_emp, b.co_cta ";
                strSQL+=") as e6 ";
                strSQL+="ON e1.co_emp=e6.co_emp AND e1.co_cta=e6.co_cta ";
                strSQL+=" LEFT OUTER JOIN(";
                //Saldo disponible: Se presentan los documentos que fueron depositados y que estan asociados a un banco diferente al seleccionado (por filtro o por fila)
                //donde fe_venChq este en el (dia_actual - 2); en conjunto con los documentos que son del mismo banco (fecha del dia)
                strSQL += "SELECT b.co_emp, b.co_cta, sum(b.nd_monchq) as nd_saldis FROM ";
                //1er. query: Todos los cheques depositados excepto los chq del banco seleccionado (por filtro o por fila) donde fe_venChq este en el (dia_actual - 2)
                strSQL += "( SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                strSQL += "             a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                strSQL += "             a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                strSQL += "             a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
                strSQL += "             (CASE ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                strSQL += "             END) as co_bcodepchq ";
                strSQL += "  FROM       tbm_detrecdoc as a1 ";
                strSQL += "  INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                strSQL += "  INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                strSQL += "  INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                strSQL += "  INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                strSQL += "  LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                strSQL += "  LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                strSQL += "  INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
                strSQL += "  INNER JOIN tbm_loc as a9 on a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and a10.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                else 
                   strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL += "          and a8.co_ctadeb = " + txtCodCta.getText();
                
                strSQL += "             and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                strSQL += "             and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
                
                if (arlDatCiu.size() > 0)
                {  strSQL += " and (case ";
                   for (i = 0; i < arlDatCiu.size(); i++)
                   {  intArlCiuCodCiu = objUti.getIntValueAt(arlDatCiu, i, INT_ARL_CIU_COD_CIU);
                      strFecCob_HaciaAtr_ChqBcoDif_SalDis = objUti.getStringValueAt(arlDatCiu, i, INT_ARL_CIU_FEC_COB);
                      strSQL += "when a9.co_ciu = " + intArlCiuCodCiu + " then a5.fe_doc = '" + strFecCob_HaciaAtr_ChqBcoDif_SalDis + "' ";
                   }
                   strSQL += " end)";
                }
                
                strSQL += "             and a1.co_banchq <> (CASE ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
		strSQL += "                                  END) "; //co_bcodepchq: Resultado del case
                
                if (strFecCob_ChqMisBco_SalDis.equals(""))
                {  //Si strFecCob_ChqMisBco_SalDis = "", significa que no se debera traer los chq del mismo banco debido a que la Fe_act es sabado
                   strSQL += ") ";
                }
                else
                {  //Si strFecCob_ChqMisBco_SalDis <> "", significa que es igual a Fe_act y no es sabado
                   strSQL += "  UNION ALL ";
                   //2do. query: Solamente los cheques depositados del banco seleccionado (por filtro o por fila) donde fe_venChq = fecha_actual
                   strSQL += "  SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                   strSQL += "             a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                   strSQL += "             a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                   strSQL += "             a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
                   strSQL += "             (CASE ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                   strSQL += "             END) as co_bcodepchq ";
                   strSQL += "  FROM       tbm_detrecdoc as a1 ";
                   strSQL += "  INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                   strSQL += "  INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                   strSQL += "  INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                   strSQL += "  INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                   strSQL += "  LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                   strSQL += "  LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                   strSQL += "  INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";

                   if (objParSis.getCodigoUsuario() == 1)
                   {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                      strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                   }
                   else
                   {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                      strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                      strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                   }

                   if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                      strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                   else
                      strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";

                   if (!txtCodCta.getText().equals(""))
                      strSQL += "          and a8.co_ctadeb = " + txtCodCta.getText();

                   strSQL += "             and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                   strSQL += "             and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";

                   if (strFecCob_HaciaAtr_SalCon.equals(""))
                   {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Disponible
                      //para cheques del mismo banco
                      strSQL += "          and a5.fe_doc = '" + strFecAct + "'";
                   }
                   else
                       if (dtpFecDoc.isFecha())
                            strSQL+=" AND (a5.fe_doc BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "')";
                      //strSQL += "          and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'"; tony
                   
                   strSQL += "             and a1.co_banchq = (CASE ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                   strSQL += "                                 END) "; //co_bcodepchq: Resultado del case
                   strSQL += ") ";
                } //if (strFecCob_ChqMisBco_SalDis.equals(""))
                
                strSQL += "  as b ";
                   
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL += " WHERE b.co_emp IN (" + strCodEmpChk + ") ";
                else 
                   strSQL += " WHERE b.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                      
                strSQL += "GROUP BY b.co_emp, b.co_cta ";
                strSQL += ") as e7 ";
                strSQL += "ON e1.co_emp=e7.co_emp AND e1.co_cta=e7.co_cta ";
                //Parte final del query que extrae info para las columnas del reporte
                strSQL+=strAux;
                strSQL+=" ORDER BY co_emp";
                
                
                
                   
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    
                    }else //a una fecha que no es mes completo Ejm. 2006-01-12
                    {
                    
                
                strSQL="";
                strSQL+="SELECT e1.co_emp, e1.tx_nomemp, e1.co_cta, e1.tx_codCta, e1.tx_desLar, e1.nd_salCta";
                strSQL+=", e2.nd_chqBanAsi, e3.nd_valAutPag, e4.nd_chqFec, e5.nd_ValEgrCus, e6.nd_salcon, e7.nd_saldis, ";
                strSQL+="   	( CASE WHEN e1.nd_salCta    IS NULL THEN 0 ELSE e1.nd_salCta    END";
                strSQL+=" 	- CASE WHEN e3.nd_valAutPag IS NULL THEN 0 ELSE e3.nd_valAutPag END";
                strSQL+=" 	+ CASE WHEN e5.nd_ValEgrCus IS NULL THEN 0 ELSE e5.nd_ValEgrCus END";
                strSQL+=" 	+ CASE WHEN e7.nd_salDis    IS NULL THEN 0 ELSE e7.nd_salDis    END ) AS nd_salFinDis, ";
                strSQL+="   	( CASE WHEN e1.nd_salCta    IS NULL THEN 0 ELSE e1.nd_salCta    END";
                strSQL+=" 	- CASE WHEN e3.nd_valAutPag IS NULL THEN 0 ELSE e3.nd_valAutPag END";
                strSQL+=" 	+ CASE WHEN e5.nd_ValEgrCus IS NULL THEN 0 ELSE e5.nd_ValEgrCus END";
                strSQL+=" 	+ CASE WHEN e6.nd_salCon    IS NULL THEN 0 ELSE e6.nd_salCon    END ) AS nd_salFinCon ";
                strSQL+=" FROM(";
                
                
                strSQL+="SELECT  b1.co_emp,b1.tx_nom as tx_nomemp, b1.co_cta, b1.tx_codCta, b1.tx_desLar,\n" +
"       ( (CASE WHEN b2.nd_salCta IS NULL THEN 0 ELSE b2.nd_salCta END) 		\n" +
"         + (CASE WHEN b3.nd_salCta IS NULL THEN 0 ELSE b3.nd_salCta END) 		\n" +
"         + (CASE WHEN b4.nd_salCta IS NULL THEN 0 ELSE b4.nd_salCta END)     ) AS nd_salCta\n" +
"  FROM( 	\n" +
"       SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, a1.tx_tipCta, \n" +
"              0 AS nd_salCta, e2.tx_nom 	\n" +
"       FROM tbm_plaCta AS a1\n" +
"       LEFT JOIN tbm_emp AS e2 ON (a1.co_emp=e2.co_emp) \n";
                        
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                        
strSQL+="         AND a1.st_reg NOT IN('I','E')          \n" +
"         AND a1.tx_niv1 IN ('1', '2', '3') \n" +
"         AND a1.st_reg='A' 	\n" +
"         AND a1.st_ctaBan='S'	\n" +
"        ORDER BY a1.tx_codCta ) AS b1 \n" +
"  LEFT OUTER JOIN( 		 \n" +
"                  SELECT b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar 			\n" +
"                       , SUM(salAct) AS nd_salCta \n" +
"                   FROM( 			\n" +
"                        SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar 			\n" +
"                              ,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-\n" +
"                              (CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct 			     \n" +
"                         FROM tbm_cabdia as a1  			\n" +
"                         INNER JOIN tbm_detdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia)\n" +
"                         INNER JOIN tbm_plaCta AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta)\n";
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
 if (dtpFecDoc.isFecha())
           strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15
//strSQL+="                   AND (a1.fe_dia BETWEEN '2017-05-01' AND '2017-05-10') 			\n" +
+"                          and a1.st_reg='A' \n" +
"                          AND a4.tx_tipCta='D'\n" +
"                          AND a4.st_ctaBan='S' 			\n" +
"                         GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar) AS b2 		 \n" +
"                    GROUP BY b2.co_emp, b2.co_cta, b2.ne_niv, b2.ne_pad, b2.tx_codCta, b2.tx_desLar 		 \n" +
"                    ORDER BY b2.tx_codCta ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cta=b2.co_cta)\n" +
"                     LEFT OUTER JOIN( 	    \n" +
"                                     SELECT a1.co_emp, a1.co_cta, a1.ne_niv, a1.ne_pad, a1.tx_codCta, a1.tx_desLar, \n" +
"                                            a3.nd_salCta AS nd_salCta 	     \n" +
"                                       FROM tbm_plaCta AS a1 	     \n" +
"                                      INNER JOIN tbm_salCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) 	     \n" +
"                                      INNER JOIN ( 	     \n" +
"                                      SELECT b1.co_emp, b1.co_cta, SUM(b1.nd_salCta) AS nd_salCta 	     \n" +
"                                      FROM tbm_salCta AS b1 	     \n";
                   
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE b1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                   
                   
                   strSQL+= "AND b1.co_per<= 	     \n" + getPeriodoAnterior() +
"                                      GROUP BY b1.co_emp, b1.co_cta 	     ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta) 	     \n";
                           
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
                           
strSQL+="                                           AND a2.co_per=	     \n" +getPeriodoAnterior()+
"                                           AND a1.ne_niv<=7 \n" +
"                                           AND a1.tx_niv1 IN ('1', '2', '3')                  \n" +
"                                           AND a1.tx_tipCta='D' 	     \n" +
"                                           AND (a2.nd_salCta<>0 OR a3.nd_salCta<>0) 	     \n" +
"                                           AND a1.st_ctaBan='S'\n" +
"                                           ORDER BY a1.tx_codCta ) AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cta=b3.co_cta)\n" +
"                                     LEFT OUTER JOIN( \n" +
"						     SELECT b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, \n" +
"						            b1.tx_desLar, SUM(b2.nd_salCta) AS nd_salCta \n" +
"						      FROM( 	\n" +
"						      SELECT a1.co_emp, a1.co_ctaRes AS co_cta, a2.ne_niv, a2.ne_pad, a2.tx_codCta, a2.tx_desLar 	\n" +
"						      FROM tbm_emp AS a1 \n" +
"						      INNER JOIN tbm_plaCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_ctaRes=a2.co_cta) 	\n" ;
        
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
        
        strSQL+= "AND a2.st_ctaBan='S'	) AS b1 \n" +
"				       INNER JOIN( 			\n" +
"				                  SELECT a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar 			\n" +
"				                        ,sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-\n" +
"				                            (CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as nd_salCta 			\n" +
"				                   FROM tbm_cabdia as a1 \n" +
"				                   INNER JOIN tbm_detdia AS a2 	ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND \n" +
"										    a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia) 			\n" +
"							INNER JOIN tbm_plaCta AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_cta=a4.co_cta) 			\n";
                
if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                }
                else{
                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }
  if (dtpFecDoc.isFecha())
           strSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(getFechaInicioMesCorte(dtpFecDoc.getText()),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'"//solo el mes del corte Ejm: corte al 2008/01/15   -> between 2008/01/01 and 2008/01/15              
//strSQL+="						AND a1.fe_dia BETWEEN '2017-05-01' AND '2017-05-10' 			\n" +
+"						and a1.st_reg='A'                         \n" +
"						AND a4.tx_niv1 IN ('4', '5', '6', '7', '8')                         \n" +
"						AND a4.tx_tipCta='D' AND a4.st_ctaBan='S'			\n" +
"			GROUP BY a1.co_emp, a4.co_cta, a4.ne_niv, a4.ne_pad, a4.tx_codCta, a4.tx_desLar 			\n" +
"			ORDER BY a4.tx_codCta ) AS b2 ON (b1.co_emp=b2.co_emp) \n" +
"			GROUP BY b1.co_emp, b1.co_cta, b1.ne_niv, b1.ne_pad, b1.tx_codCta, b1.tx_desLar ) AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_cta=b4.co_cta) \n" +
"			ORDER BY b1.tx_codCta";
                
                
                
////                //--saldo de cuentas bancarias
////                strSQL+=" 	SELECT     a1.co_emp, a3.tx_nom as tx_nomemp, a1.co_cta, a1.tx_codCta, a1.tx_desLar, SUM(a2.nd_salcta) AS nd_salCta";
////                strSQL+=" 	FROM       tbm_plaCta AS a1";
////                strSQL+="       INNER JOIN tbm_salCta AS a2 ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
////                strSQL+="       LEFT  JOIN tbm_emp AS a3 ON a1.co_emp = a3.co_emp ";
////                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
////                    strSQL+=" 	WHERE a1.co_emp IN(" + strCodEmpChk + ")";
////                }
////                else{
////                    strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
////                }
////                strSQL+=" 	AND a1.st_ctaBan='S'";//--AND a1.co_cta=2220
////             
//////                if(optMes.isSelected()) //la forma que hasta el momento se ha manejado
//////                {
//////                    strSQL+=" AND b1.co_per<=" + strFecCor;
//////                }else //a una fecha que no es mes completo Ejm. 2006-01-12
//////                {
//////                
//////                }
////                
////                strSQL+=" 	GROUP BY a1.co_emp, a3.tx_nom, a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" ) AS e1";
                
                strSQL+=" LEFT OUTER JOIN(";
                //--listado de cheques que tienen el banco asignado
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_monChq) AS nd_chqBanAsi FROM(";
                strSQL+=" 		SELECT b1.*, b2.co_cta, b2.co_tipdoc FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_tipDocCon";
                strSQL+=" 			, a2.co_cli, a3.tx_nom, a4.co_reg AS co_bco, a4.tx_desCor AS tx_desCorBco, a4.tx_desLar AS tx_desLarBco";
                strSQL+="			, a2.tx_numctachq, a2.nd_monchq, a2.fe_venchq, a2.fe_asitipdoccon AS fe_asiBco";
                strSQL+=" 			FROM tbm_cabrecdoc as a1 INNER JOIN (tbm_detrecdoc as a2 INNER JOIN tbm_var AS a4 ON a2.co_banChq=a4.co_reg AND a4.co_grp=8)";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_cli AS a3 ON a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                   WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+="                   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                         AND a1.st_reg='A' AND a2.st_reg='A' AND a2.co_tipdoccon IS NOT NULL";
                strSQL+=" 			AND a2.nd_valApl=0";
                strSQL+=" 			ORDER BY a1.co_doc, a2.co_reg";
                strSQL+=" 		) AS b1";
                strSQL+=" 		INNER JOIN(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_ctaDeb AS co_cta";
                strSQL+=" 			FROM tbm_cabtipdoc AS a1";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                         where a1.co_emp IN(" + strCodEmpChk + ")";//--and co_ctaDeb=2220
                else
                    strSQL+="                         where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";//--and co_ctaDeb=2220
                strSQL+="		) AS b2";
                strSQL+=" 		ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDocCon=b2.co_tipdoc";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e2";
                strSQL+=" ON e1.co_emp=e2.co_emp AND e1.co_cta=e2.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //--documentos autorizados para pagar y asignado bancos
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_valPnd) AS nd_valAutPag FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" 		, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag";
                strSQL+="                 , a2.nd_abo, (a2.mo_pag + a2.nd_abo) AS nd_valPnd";
                strSQL+=" 		, a1.co_cli, a1.tx_nomCli, a2.co_ctaAutPag AS co_cta";
                strSQL+=" 		FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" 			ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 		INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" 		WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                 AND a1.st_reg NOT IN ('E','I') AND a2.st_reg IN ('A','C')";//--AND a2.co_ctaautpag=2220
                strSQL+=" 		AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e3";
                strSQL+=" ON e1.co_emp=e3.co_emp AND e1.co_cta=e3.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //--Cheques a fecha emitidos
                strSQL+=" 	SELECT c1.co_emp, c1.co_cta, SUM(c1.nd_monDoc) AS nd_chqFec FROM(";
//                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli";
//                strSQL+=" 		, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
//                strSQL+=" 		, a1.ne_numDoc1, a1.ne_numDoc2, a1.fe_doc, a1.fe_ven, ABS(a1.nd_monDoc) AS nd_monDoc, a1.co_cta";
//                strSQL+=" 		FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3 ";
//                strSQL+="                             ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
//                strSQL+=" 		INNER JOIN tbm_detPag AS a2";
//                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
//                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
//                    strSQL+=" 		WHERE a1.co_emp IN(" + strCodEmpChk + ")";
//                else
//                    strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="               AND a1.co_tipDoc IN(32,123) ";//--AND a1.co_cta=2220
//                strSQL+=" 		AND a1.st_reg NOT IN('E','I') AND a1.fe_ven>CURRENT_DATE";
                strSQL+="SELECT b1.*FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli";
                strSQL+=" 	, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" 	, a1.ne_numDoc1, a1.ne_numDoc2, a1.fe_doc, a1.fe_ven, ABS(a1.nd_monDoc) AS nd_monDoc, a1.co_cta";
                strSQL+=" 	FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" 	 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_detPag AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.st_reg NOT IN('E','I') AND a1.fe_ven>CURRENT_DATE";
                strSQL+=" 	ORDER BY a1.ne_numDoc1";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocPrg AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else{
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocUsr AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" 	AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";

                }
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc";
                strSQL+=" 	) AS c1";
                strSQL+=" 	GROUP BY c1.co_emp, c1.co_cta";
                strSQL+=" ) AS e4";
                strSQL+=" ON e1.co_emp=e4.co_emp AND e1.co_cta=e4.co_cta";
                strSQL+=" LEFT OUTER JOIN(";
                //----Col. Val.Egr.Cus: Cheques emitidos pendientes de entregar al proveedor
                strSQL+="SELECT     a1.co_emp, b2.co_cta, sum(a2.nd_monHab) as nd_ValEgrCus ";
                strSQL+="FROM       (tbm_cabDia AS a1 ";
                strSQL+="INNER JOIN tbm_cabTipDoc AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="INNER JOIN (tbm_detDia AS a2 ";
                strSQL+="INNER JOIN tbm_plaCta AS b2 ON a2.co_emp=b2.co_emp AND a2.co_cta=b2.co_cta) ";
                strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia ";
                strSQL+="LEFT  JOIN tbm_usr AS c1 ON a2.co_usrconban=c1.co_usr ";
                strSQL+="INNER JOIN tbm_cabpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc and a1.co_dia=a3.co_doc ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipDoc = d1.co_tipDoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipdoc = d1.co_tipdoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and d1.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+="        and a1.co_emp IN (" + strCodEmpChk + ") ";
                else
                   strSQL+="        and a1.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL+="        and b2.co_cta = " + txtCodCta.getText() + " ";
                
                //OJO: Dennis Betancourt puso la fecha quemada '2013-01-01', previa consulta realizada a Ingrid el dia 14/feb/2014
                strSQL+="           AND a1.st_reg NOT IN('E','I') AND a1.fe_dia BETWEEN '2013-01-01' AND '" + strFecAct + "' ";
                strSQL+="           AND b2.st_ctaBan='S' AND (a2.st_conban IS NULL OR a2.st_conban='N') and a2.nd_monhab > 0";
                strSQL+="           AND (a3.st_entchqben is null or a3.st_entchqben = 'N') ";
                
                strSQL+="GROUP BY a1.co_emp, b2.co_cta ";
                strSQL+=") as e5 ";
                strSQL+="ON e1.co_emp=e5.co_emp AND e1.co_cta=e5.co_cta ";
                strSQL+=" LEFT OUTER JOIN(";
                //----Saldo contable: Se presenta la suma de los cheques enviados al deposito en el dia actual
                strSQL+="SELECT b.co_emp, b.co_cta, sum(b.nd_monchq) as nd_salcon FROM ";
                strSQL+="  ( SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                strSQL+="               a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                strSQL+="               a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                strSQL+="               a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg ";
                strSQL+="    FROM       tbm_detrecdoc as a1 ";
                strSQL+="    INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                strSQL+="    INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                strSQL+="    INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                strSQL+="    INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                strSQL+="    LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                strSQL+="    LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                strSQL+="    INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+="        and a1.co_emp IN (" + strCodEmpChk + ")";
                else
                   strSQL+="        and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL+="            and a8.co_ctadeb = " + txtCodCta.getText();
                
                strSQL+="               and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                strSQL+="               and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
                
                if (strFecCob_HaciaAtr_SalCon.equals(""))
                {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Contable
                   strSQL+="            and a5.fe_doc = '" + strFecAct + "'";
                }
                else
                   strSQL+="            and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'";
                
                strSQL+="  ) as b ";
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL+=" WHERE b.co_emp IN (" + strCodEmpChk + ")";
                else
                   strSQL+=" WHERE b.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                strSQL+="GROUP BY b.co_emp, b.co_cta ";
                strSQL+=") as e6 ";
                strSQL+="ON e1.co_emp=e6.co_emp AND e1.co_cta=e6.co_cta ";
                strSQL+=" LEFT OUTER JOIN(";
                //Saldo disponible: Se presentan los documentos que fueron depositados y que estan asociados a un banco diferente al seleccionado (por filtro o por fila)
                //donde fe_venChq este en el (dia_actual - 2); en conjunto con los documentos que son del mismo banco (fecha del dia)
                strSQL += "SELECT b.co_emp, b.co_cta, sum(b.nd_monchq) as nd_saldis FROM ";
                //1er. query: Todos los cheques depositados excepto los chq del banco seleccionado (por filtro o por fila) donde fe_venChq este en el (dia_actual - 2)
                strSQL += "( SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                strSQL += "             a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                strSQL += "             a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                strSQL += "             a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
                strSQL += "             (CASE ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                strSQL += "             END) as co_bcodepchq ";
                strSQL += "  FROM       tbm_detrecdoc as a1 ";
                strSQL += "  INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                strSQL += "  INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                strSQL += "  INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                strSQL += "  INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                strSQL += "  LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                strSQL += "  LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                strSQL += "  INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
                strSQL += "  INNER JOIN tbm_loc as a9 on a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and a10.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                else 
                   strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL += "          and a8.co_ctadeb = " + txtCodCta.getText();
                
                strSQL += "             and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                strSQL += "             and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
                
                if (arlDatCiu.size() > 0)
                {  strSQL += " and (case ";
                   for (i = 0; i < arlDatCiu.size(); i++)
                   {  intArlCiuCodCiu = objUti.getIntValueAt(arlDatCiu, i, INT_ARL_CIU_COD_CIU);
                      strFecCob_HaciaAtr_ChqBcoDif_SalDis = objUti.getStringValueAt(arlDatCiu, i, INT_ARL_CIU_FEC_COB);
                      strSQL += "when a9.co_ciu = " + intArlCiuCodCiu + " then a5.fe_doc = '" + strFecCob_HaciaAtr_ChqBcoDif_SalDis + "' ";
                   }
                   strSQL += " end)";
                }
                
                strSQL += "             and a1.co_banchq <> (CASE ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
		strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
		strSQL += "                                  END) "; //co_bcodepchq: Resultado del case
                
                if (strFecCob_ChqMisBco_SalDis.equals(""))
                {  //Si strFecCob_ChqMisBco_SalDis = "", significa que no se debera traer los chq del mismo banco debido a que la Fe_act es sabado
                   strSQL += ") ";
                }
                else
                {  //Si strFecCob_ChqMisBco_SalDis <> "", significa que es igual a Fe_act y no es sabado
                   strSQL += "  UNION ALL ";
                   //2do. query: Solamente los cheques depositados del banco seleccionado (por filtro o por fila) donde fe_venChq = fecha_actual
                   strSQL += "  SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                   strSQL += "             a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                   strSQL += "             a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                   strSQL += "             a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
                   strSQL += "             (CASE ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                   strSQL += "                 WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                   strSQL += "             END) as co_bcodepchq ";
                   strSQL += "  FROM       tbm_detrecdoc as a1 ";
                   strSQL += "  INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                   strSQL += "  INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                   strSQL += "  INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                   strSQL += "  INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                   strSQL += "  LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                   strSQL += "  LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                   strSQL += "  INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";

                   if (objParSis.getCodigoUsuario() == 1)
                   {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                      strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                   }
                   else
                   {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                      strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                      strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                   }

                   if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                      strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                   else
                      strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";

                   if (!txtCodCta.getText().equals(""))
                      strSQL += "          and a8.co_ctadeb = " + txtCodCta.getText();

                   strSQL += "             and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                   strSQL += "             and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";

                   if (strFecCob_HaciaAtr_SalCon.equals(""))
                   {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Disponible
                      //para cheques del mismo banco
                      strSQL += "          and a5.fe_doc = '" + strFecAct + "'";
                   }
                   else
                      strSQL += "          and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'";
                   
                   strSQL += "             and a1.co_banchq = (CASE ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                   strSQL += "                                     WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                   strSQL += "                                 END) "; //co_bcodepchq: Resultado del case
                   strSQL += ") ";
                } //if (strFecCob_ChqMisBco_SalDis.equals(""))
                
                strSQL += "  as b ";
                   
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL += " WHERE b.co_emp IN (" + strCodEmpChk + ") ";
                else 
                   strSQL += " WHERE b.co_emp = " + objParSis.getCodigoEmpresa() + " ";
                                      
                strSQL += "GROUP BY b.co_emp, b.co_cta ";
                strSQL += ") as e7 ";
                strSQL += "ON e1.co_emp=e7.co_emp AND e1.co_cta=e7.co_cta ";
                //Parte final del query que extrae info para las columnas del reporte
                strSQL+=strAux;
                strSQL+=" ORDER BY co_emp";
                
                
                
                    
                    
                    
                    }
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                
                rst=stm.executeQuery(strSQL);
                
                System.out.println("cargarDetReg: " +strSQL);
                
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                
                lblMsgSis.setText("Listo");
                
                while (rst.next()){
                    if (blnCon){
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_NOM_EMP,         "" + rst.getObject("tx_nomemp")==null?"":rst.getString("tx_nomemp"));
                        vecReg.add(INT_TBL_DAT_COD_CTA,         "" + rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,         "" + rst.getObject("tx_codCta")==null?"":rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_NOM_CTA,         "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_SAL_CTA_CTB,     "" + rst.getObject("nd_salCta")==null?"":rst.getString("nd_salCta"));
                        vecReg.add(INT_TBL_DAT_CHQ_BAN_ASI,     "" + rst.getObject("nd_chqBanAsi")==null?"":rst.getString("nd_chqBanAsi"));
                        vecReg.add(INT_TBL_DAT_BUT_CHQ_BAN_ASI, "");
                        vecReg.add(INT_TBL_DAT_VAL_CTA_TRN,     "");
                        vecReg.add(INT_TBL_DAT_VAL_AUT_PAG,     "" + rst.getObject("nd_valAutPag")==null?"":rst.getString("nd_valAutPag"));
                        vecReg.add(INT_TBL_DAT_BUT_VAL_AUT_PAG, "");
                        vecReg.add(INT_TBL_DAT_CHQ_FEC_EMI,     "" + rst.getObject("nd_chqFec")==null?"":rst.getString("nd_chqFec"));
                        vecReg.add(INT_TBL_DAT_BUT_CHQ_FEC_EMI, "");
                        vecReg.add(INT_TBL_DAT_VAL_EGR_CUS,     "" + rst.getObject("nd_ValEgrCus")==null?"":rst.getString("nd_ValEgrCus"));
                        vecReg.add(INT_TBL_DAT_BUT_VAL_EGR_CUS, "");
                        vecReg.add(INT_TBL_DAT_SAL_DIS,     "" + rst.getObject("nd_saldis")==null?"":rst.getString("nd_saldis"));
                        vecReg.add(INT_TBL_DAT_BUT_SAL_DIS, "");
                        vecReg.add(INT_TBL_DAT_SAL_CON,     "" + rst.getObject("nd_salcon")==null?"":rst.getString("nd_salcon"));
                        vecReg.add(INT_TBL_DAT_BUT_SAL_CON, "");
                        vecReg.add(INT_TBL_DAT_SAL_FIN_DIS, "" + rst.getObject("nd_salFinDis")==null?"":rst.getString("nd_salFinDis"));
                        vecReg.add(INT_TBL_DAT_SAL_FIN_CON, "" + rst.getObject("nd_salFinCon")==null?"":rst.getString("nd_salFinCon"));
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
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
//                objTblTot.calcularTotales();
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
     * Esta función permite establecer la conexión
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes = true;
        try
        {  con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if (con != null)
           {  if (cargarDetReg())
              {  objTblMod.initRowsState();
                 objTblTot.calcularTotales();
                 if (cargarArregloChqBanAsi())
                 {  if (cargarArregloValAutPag())
                    {  if (cargarArregloChqEmi())
                       {  if (cargarArregloValEgrCus())
                          {  if (cargarArregloSalCon())
                             {  if (cargarArregloSalDis())
                                {   }
                             }
                          }
                       }
                    }
                }
                con.close();
                con = null;
              }
           }
        }
        catch (Exception e){
            e.printStackTrace();
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la "fecha desde" que se presenta en el formulario.
     * @param fechaActual La fecha del presente día.
     * @return String: Contiene la fecha del primer día del mes anterior.
     */
    private String getMesAnterior(String fechaActual){
        Connection conMesAnt;
        Statement stmMesAnt;
        ResultSet rstMesAnt;
        String strMesAnt="";
        try{
            conMesAnt=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conMesAnt!=null){
                stmMesAnt=conMesAnt.createStatement();
                strSQL="";
                strSQL+=" select '01/' ||";
                strSQL+=" case when extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))<10 then '0'||extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))";
                strSQL+=" else ''||extract('month' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date)) end";
                strSQL+=" || '/'  ||";
                strSQL+=" extract('year' FROM cast('";
                strSQL+="" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "";
                strSQL+=" ' as date))  as fechaInicial";

                rstMesAnt=stmMesAnt.executeQuery(strSQL);
                if(rstMesAnt.next()){
                    strMesAnt=rstMesAnt.getString("fechaInicial");
                }
                conMesAnt.close();
                conMesAnt=null;
                stmMesAnt.close();
                stmMesAnt=null;
                rstMesAnt.close();
                rstMesAnt=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return objUti.formatearFecha(strMesAnt,"dd/MM/yyyy","dd/MM/yyyy");
    }

    /**
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intConChk=0;
        //validar empresa
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
            if (objTblModEmp.getRowCountChecked(INT_TBL_EMP_CHK)<=0){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Se debe seleccionar al menos una empresa para realizar la consulta<BR>Seleccione una empresa y vuelva a intentarlo.</HTML>");
                return false;
            }
            for(int i=0;i<objTblModEmp.getRowCountTrue(); i++){
                if(objTblModEmp.isChecked(i, INT_TBL_EMP_CHK)){
                    if(intConChk==0){
                        strCodEmpChk=objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                        intConChk++;
                    }
                    else{
                         strCodEmpChk+="," + objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                    }
                }
            }
        }
        return true;
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
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_DAT_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_DAT_COD_CTA:
                    strMsg="Codigo de la cuenta";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta bancaria";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta bancaria";
                    break;
                case INT_TBL_DAT_SAL_CTA_CTB:
                    strMsg="Saldo de la cuenta bancaria";
                    break;
                case INT_TBL_DAT_CHQ_BAN_ASI:
                    strMsg="Valor total de cheques que tienen asignado el banco donde deben ser depositados";
                    break;
                case INT_TBL_DAT_VAL_CTA_TRN:
                    strMsg="Valor total en cuenta de tránsito";
                    break;
                case INT_TBL_DAT_VAL_AUT_PAG:
                    strMsg="Valor autorizado a pagar con banco asociado, sin emisión de cheque";
                    break;
                case INT_TBL_DAT_BUT_VAL_AUT_PAG:
                    strMsg="Detalle de documentos autorizados a emitir pago con banco asociado";
                    break;
                case INT_TBL_DAT_CHQ_FEC_EMI:
                    strMsg="Valor total de cheques a fecha emitidos";
                    break;
                case INT_TBL_DAT_VAL_EGR_CUS:
                    strMsg="Valor del cheque emitido pendiente de entregar a proveedor";
                    break;
                case INT_TBL_DAT_BUT_VAL_EGR_CUS:
                    strMsg="Detalle de cheques emitidos pendientes de ser entregados al proveedor";
                    break;
                case INT_TBL_DAT_SAL_DIS:
                    strMsg="Saldo disponible por depósitos en cheque";
                    break;
                case INT_TBL_DAT_BUT_SAL_DIS:
                    strMsg="Detalle de documentos depositados en el banco seleccionado";
                    break;
                case INT_TBL_DAT_SAL_CON:
                    strMsg="Saldo contable por depósitos en cheque";
                    break;
                case INT_TBL_DAT_BUT_SAL_CON:
                    strMsg="Detalle de documentos depositados en el otro banco al seleccionado";
                    break;
                case INT_TBL_DAT_SAL_FIN_DIS:
                    strMsg="Saldo final de la cuenta bancaria disponible";
                    break;
                case INT_TBL_DAT_SAL_FIN_CON:
                    strMsg="Saldo final de la cuenta bancaria contable";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaEmp extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblEmp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Codigo de la empresa";
                    break;
                case INT_TBL_EMP_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblEmp.getTableHeader().setToolTipText(strMsg);
        }
    }


    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblEmp()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_LIN,"");
            vecCab.add(INT_TBL_EMP_CHK,"");
            vecCab.add(INT_TBL_EMP_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_EMP_NOM_EMP,"Empresa");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModEmp=new ZafTblMod();
            objTblModEmp.setHeader(vecCab);
            tblEmp.setModel(objTblModEmp);
            //Configurar JTable: Establecer tipo de selección.
            tblEmp.setRowSelectionAllowed(true);
            tblEmp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblEmp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_EMP_NOM_EMP).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblEmp.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblEmp.getTableHeader().addMouseMotionListener(new ZafMouMotAdaEmp());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblEmp.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblEmpMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_EMP_CHK);
            objTblModEmp.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    txtCodCta.setText("");
                    txtDesCorCta.setText("");
                    txtDesLarCta.setText("");
                    vcoCta.limpiar();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            //Configurar JTable: Establecer el modo de operación.
            objTblModEmp.setModoOperacion(objTblModEmp.INT_TBL_EDI);
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la empresa seleccionada en el el JTable de empresas.
     */
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        try{
            intNumFil=objTblModEmp.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblEmp.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK){
                if (blnMarTodChkTblEmp){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModEmp.setChecked(true, i, INT_TBL_EMP_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModEmp.setChecked(false, i, INT_TBL_EMP_CHK);
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


    /**
     * Esta función permite consultar las empresas de acuerdo al siguiente criterio:
     * El listado de empresas se presenta en función al permiso que tenga el usuario en tbr_usrEmp
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarEmp(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las empresas.
                    if (objParSis.getCodigoUsuario()==1){
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                        rst=stm.executeQuery(strSQL);
                    }
                    else{
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbr_usremp AS a2 ON a1.co_emp=a2.co_emp";
                        strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a1.st_reg NOT IN ('E','I') AND a2.st_reg NOT IN ('E','I')";
                        strSQL+=" ORDER BY a1.co_emp, a1.tx_nom";
                        rst=stm.executeQuery(strSQL);
                    }

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    vecReg.add(INT_TBL_EMP_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_EMP_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_EMP_NOM_EMP,rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModEmp.setData(vecDat);
                tblEmp.setModel(objTblModEmp);
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código empresa");
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";

            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.co_cta, a1.tx_codcta, a1.tx_deslar";
            strSQL+=" FROM tbm_plaCta AS a1";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
            else
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_ctaban='S'";
            strSQL+=" ORDER BY a1.co_emp, a1.tx_codalt";


            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
            vcoCta.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(3);
            vcoCta.setCriterio1(7);
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
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLAdi="";
        int intConChk=0;
        try
        {
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                for(int i=0;i<objTblModEmp.getRowCountTrue(); i++){
                    if(objTblModEmp.isChecked(i, INT_TBL_EMP_CHK)){
                        if(intConChk==0){
                            strCodEmpChk=objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                            intConChk++;
                        }
                        else{
                             strCodEmpChk+="," + objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                        }
                    }
                }

                strSQLAdi="";
                strSQLAdi+=" AND a1.co_emp IN(" + strCodEmpChk + ")";
                System.out.println("SQL ADICIONAL: " +strSQLAdi);
                vcoCta.setCondicionesSQL(strSQLAdi);
            }


            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(2);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                    {
                        txtCodCta.setText(vcoCta.getValueAt(2));
                        txtDesCorCta.setText(vcoCta.getValueAt(3));
                        txtDesLarCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                    {
                        txtCodCta.setText(vcoCta.getValueAt(2));
                        txtDesCorCta.setText(vcoCta.getValueAt(3));
                        txtDesLarCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(2));
                            txtDesCorCta.setText(vcoCta.getValueAt(3));
                            txtDesLarCta.setText(vcoCta.getValueAt(4));
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
                        txtCodCta.setText(vcoCta.getValueAt(2));
                        txtDesCorCta.setText(vcoCta.getValueAt(3));
                        txtDesLarCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCta.setCampoBusqueda(3);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(2));
                            txtDesCorCta.setText(vcoCta.getValueAt(3));
                            txtDesLarCta.setText(vcoCta.getValueAt(4));
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
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void setPuntosCta(){
        strAux=txtDesCorCta.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
            chrCtaOri=strCodCtaOri.charAt(1);
            if(chrCtaOri!='.'){
                for (int i=0; i < strCodCtaOri.length(); i++){
                    if(i==0){
                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                        strCodCtaDes=strCodCtaDes+".";
                    }
                    else{
                        if(  (strCodCtaOri.length() % 2) == 0 ){
                            if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                strCodCtaDes=strCodCtaDes+".";
                            }
                            if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            }
                            else{
                                if((i % 2)!= 0)
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            }
                        }
                        else{
                            if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                strCodCtaDes=strCodCtaDes+".";
                            }
                            if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            }
                            else{
                                if((i % 2)!= 0)
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            }
                        }
                    }
                }
                txtDesCorCta.setText(strCodCtaDes);
            }
        }
    }


    private boolean cargarArregloChqBanAsi(){
        boolean blnRes=true;
        arlDatChqBanAsi.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b1.*, b2.co_cta, b2.co_tipdoc FROM(";
                strSQL+="         SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.co_tipDocCon";
                strSQL+="         , a2.co_cli, a3.tx_nom, a4.co_reg AS co_bco, a4.tx_desCor AS tx_desCorBco, a4.tx_desLar AS tx_desLarBco";
                strSQL+="         , a2.tx_numctachq, a2.tx_numchq, a2.nd_monchq, a2.fe_venchq, a2.fe_asitipdoccon AS fe_asiBco";
                strSQL+="         FROM tbm_cabrecdoc as a1 INNER JOIN (tbm_detrecdoc as a2 INNER JOIN tbm_var AS a4 ON a2.co_banChq=a4.co_reg AND a4.co_grp=" + INT_COD_GRP_BAN + ")";
                strSQL+="         ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                strSQL+="         INNER JOIN tbm_cli AS a3 ON a2.co_emp=a3.co_emp AND a2.co_cli=a3.co_cli";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                   WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+="                   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="         AND a1.st_reg='A' AND a2.st_reg='A' AND a2.co_tipdoccon IS NOT NULL";
                strSQL+="         AND a2.nd_valApl=0";
                strSQL+="         ORDER BY a1.co_doc, a2.co_reg";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+="         SELECT co_emp, co_loc, co_tipdoc, co_ctaDeb AS co_cta";
                strSQL+="        FROM tbm_cabtipdoc ";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="         where co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+="         where co_emp=" + objParSis.getCodigoEmpresa() + "";
                if( ! txtCodCta.getText().equals(""))
                    strSQL+="         and co_ctaDeb=" + txtCodCta.getText() + "";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDocCon=b2.co_tipdoc";
                strSQL+=" ORDER BY b1.tx_numchq";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegChqBanAsi=new ArrayList();
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_EMP,            rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_LOC,            rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_TIP_DOC_REC_CHQ,rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_DOC_REC_CHQ,    rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_REG_REC_CHQ,    rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_TIP_DOC_DEP_BAN,    rst.getObject("co_tipDocCon")==null?"":rst.getString("co_tipDocCon"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_CLI,            rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_NOM_CLI,            rst.getObject("tx_nom")==null?"":rst.getString("tx_nom"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_BCO_CHQ_FIS,    rst.getObject("co_bco")==null?"":rst.getString("co_bco"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_DES_COR_BCO_CHQ_FIS,rst.getObject("tx_desCorBco")==null?"":rst.getString("tx_desCorBco"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_DES_LAR_BCO_CHQ_FIS,rst.getObject("tx_desLarBco")==null?"":rst.getString("tx_desLarBco"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_NUM_CTA,            rst.getObject("tx_numctachq")==null?"":rst.getString("tx_numctachq"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_NUM_CHQ,            rst.getObject("tx_numchq")==null?"":rst.getString("tx_numchq"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_VAL_CHQ,            rst.getObject("nd_monchq")==null?"":rst.getString("nd_monchq"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_FEC_VEN,            rst.getObject("fe_venchq")==null?"":rst.getString("fe_venchq"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_FEC_ASG_BCO,        rst.getObject("fe_asiBco")==null?"":rst.getString("fe_asiBco"));
                    arlRegChqBanAsi.add(INT_ARL_CHQ_BAN_ASI_COD_CTA_BAN_ASG,    rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    arlDatChqBanAsi.add(arlRegChqBanAsi);
                }

                stm.close();
                stm=null;
                rst.close();
                rst=null;

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


    private boolean cargarArregloValAutPag(){
        boolean blnRes=true;
        arlDatValAutPag.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=", a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.mo_pag, a2.nd_abo, (a2.mo_pag + a2.nd_abo) AS nd_valPnd";
                strSQL+=", a1.co_cli, a1.tx_nomCli, a2.co_ctaAutPag AS co_cta";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ";
                strSQL+="         ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN tbm_plaCta AS a4";
                strSQL+=" ON a2.co_emp=a4.co_emp AND a2.co_ctaAutPag=a4.co_cta";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_reg NOT IN ('E','I') AND a2.st_reg IN ('A','C') ";
                if( ! txtCodCta.getText().equals(""))
                    strSQL+=" AND a2.co_ctaautpag=" + txtCodCta.getText() + "";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                strSQL+=" ORDER BY ne_numDoc";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegValAutPag=new ArrayList();
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_EMP,            rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_CLI,            rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_NOM_CLI,            rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_LOC,            rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_TIP_DOC,        rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_DES_COR_TIP_DOC,    rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_DES_LAR_TIP_DOC,    rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_DOC,            rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_REG,            rst.getObject("co_reg")==null?"":rst.getString("co_reg"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_NUM_DOC,            rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_FEC_DOC,            rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_DIA_CRE,            rst.getObject("ne_diaCre")==null?"":rst.getString("ne_diaCre"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_FEC_VEN,            rst.getObject("fe_ven")==null?"":rst.getString("fe_ven"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_MON_PAG,            rst.getObject("mo_pag")==null?"":rst.getString("mo_pag"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_ABO,                rst.getObject("nd_abo")==null?"":rst.getString("nd_abo"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_VAL_PND,            rst.getObject("nd_valPnd")==null?"":rst.getString("nd_valPnd"));
                    arlRegValAutPag.add(INT_ARL_VAL_AUT_PAG_COD_CTA,            rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    arlDatValAutPag.add(arlRegValAutPag);
                }

                stm.close();
                stm=null;
                rst.close();
                rst=null;

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


    private boolean cargarArregloChqEmi(){
        boolean blnRes=true;
        arlDatChqEmi.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b1.*FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli, a1.tx_nomCli";
                strSQL+=" 	, a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                strSQL+=" 	, a1.ne_numDoc1, a1.ne_numDoc2, a1.fe_doc, a1.fe_ven, ABS(a1.nd_monDoc) AS nd_monDoc, a1.co_cta";
                strSQL+=" 	FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" 	 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" 	INNER JOIN tbm_detPag AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	AND a1.st_reg NOT IN('E','I') AND a1.fe_ven>CURRENT_DATE";
                strSQL+=" 	ORDER BY a1.ne_numDoc1";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocPrg AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                }
                else{
                    strSQL+=" 	SELECT co_emp, co_loc, co_tipDoc";
                    strSQL+=" 	FROM tbr_tipDocUsr AS a1";
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp IN(" + strCodEmpChk + ")";
                    else
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" 	AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" 	AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";

                }
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc";
                System.out.println("cargarArregloChqEmi: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegChqEmi=new ArrayList();
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_COD_EMP,            rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_COD_CLI,            rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_NOM_CLI,            rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_COD_LOC,            rst.getObject("co_loc")==null?"":rst.getString("co_loc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_COD_TIP_DOC,        rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_DES_COR_TIP_DOC,    rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_DES_LAR_TIP_DOC,    rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_COD_DOC,            rst.getObject("co_doc")==null?"":rst.getString("co_doc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_NUM_DOC_UNO,        rst.getObject("ne_numDoc1")==null?"":rst.getString("ne_numDoc1"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_NUM_DOC_DOS,        rst.getObject("ne_numDoc2")==null?"":rst.getString("ne_numDoc2"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_FEC_DOC,            rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_FEC_VEN,            rst.getObject("fe_ven")==null?"":rst.getString("fe_ven"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_VAL_DOC,            rst.getObject("nd_monDoc")==null?"":rst.getString("nd_monDoc"));
                    arlRegChqEmi.add(INT_ARL_CHQ_EMI_CTA_BAN,            rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    arlDatChqEmi.add(arlRegChqEmi);
                }

                stm.close();
                stm=null;
                rst.close();
                rst=null;

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

    private boolean cargarArregloValEgrCus(){
        boolean blnRes=true;
        arlDatValEgrCus.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT DISTINCT a1.co_emp, a2.co_cta, a3.co_cli, a3.tx_nomcli, b1.tx_descor as tx_desCorTipDoc, b1.tx_deslar as ";
                strSQL+="           tx_desLarTipDoc, a3.ne_numdoc1, a3.fe_doc, a3.fe_ven, a3.st_entchqben, a2.nd_monhab ";
                strSQL+="FROM       tbm_cabDia AS a1 ";
                strSQL+="INNER JOIN tbm_cabTipDoc AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc) ";
                strSQL+="INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia) ";
                strSQL+="INNER JOIN tbm_plaCta AS b2 ON (a2.co_emp=b2.co_emp AND a2.co_cta=b2.co_cta) ";
                strSQL+="LEFT  JOIN tbm_usr AS c1 ON (a2.co_usrconban=c1.co_usr) ";
                strSQL+="INNER JOIN tbm_cabpag AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc and a1.co_dia=a3.co_doc ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipDoc = d1.co_tipDoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS d1 ON b1.co_emp = d1.co_emp and b1.co_loc = d1.co_loc and b1.co_tipdoc = d1.co_tipdoc ";
                   strSQL += "WHERE d1.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and d1.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL+="       AND a1.co_emp IN(" + strCodEmpChk + ")";
                else
                    strSQL+="       AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                
                if(!txtCodCta.getText().equals(""))
                    strSQL+="       AND b2.co_cta=" + txtCodCta.getText() + "";
                
                //OJO: Dennis Betancourt puso la fecha quemada '2013-01-01', previa consulta realizada a Ingrid el dia 14/feb/2014
                strSQL+="           AND a1.st_reg NOT IN('E','I') AND a1.fe_dia BETWEEN '2013-01-01' AND '" + strFecAct + "' ";
                strSQL+="           AND b2.st_ctaBan='S' AND (a2.st_conban IS NULL OR a2.st_conban='N') and a2.nd_monhab > 0";
                strSQL+="           AND (a3.st_entchqben is null or a3.st_entchqben = 'N') ";
                strSQL+="ORDER BY   a3.ne_numdoc1";
                
                System.out.println("cargarArregloValEgrCus: " +strSQL);
                
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegValEgrCus=new ArrayList();
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_COD_EMP,         rst.getObject("co_emp")==null?"":rst.getString("co_emp"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_COD_CTA,         rst.getObject("co_cta")==null?"":rst.getString("co_cta"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_COD_CLI,         rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_NOM_CLI,         rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_DES_COR_TIP_DOC, rst.getObject("tx_desCorTipDoc")==null?"":rst.getString("tx_desCorTipDoc"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_DES_LAR_TIP_DOC, rst.getObject("tx_desLarTipDoc")==null?"":rst.getString("tx_desLarTipDoc"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_NUM_DOC,         rst.getObject("ne_numDoc1")==null?"":rst.getString("ne_numDoc1"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_FEC_DOC,         rst.getObject("fe_doc")==null?"":rst.getString("fe_doc"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_FEC_VEN,         rst.getObject("fe_ven")==null?"":rst.getString("fe_ven"));
                    arlRegValEgrCus.add(INT_ARL_VAL_EGR_CUS_VAL_CHQ,         rst.getObject("nd_monhab")==null?"":rst.getString("nd_monhab"));
                    arlDatValEgrCus.add(arlRegValEgrCus);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
    
    private boolean cargarArregloSalCon(){
        boolean blnRes = true;
        arlDatSalCon.clear();
        try{
            if (con != null){
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                strSQL += "           a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                strSQL += "           a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                strSQL += "           a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg ";
                strSQL += "FROM       tbm_detrecdoc as a1 ";
                strSQL += "INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                strSQL += "INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                strSQL += "INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                strSQL += "INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                strSQL += "LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                strSQL += "LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                strSQL += "INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
                
                if (objParSis.getCodigoUsuario() == 1)
                {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                }
                else
                {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                   strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                   strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                }
                
                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                   strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                else
                   strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
                
                if (!txtCodCta.getText().equals(""))
                   strSQL += "        and a8.co_ctadeb = " + txtCodCta.getText();
                
                strSQL += "           and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                strSQL += "           and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
                
                if (strFecCob_HaciaAtr_SalCon.equals(""))
                {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Contable
                   strSQL += "        and a5.fe_doc = '" + strFecAct + "'";
                }
                else
                   strSQL += "        and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'";
                
                System.out.println("cargarArregloSalCon: " +strSQL);
                rst = stm.executeQuery(strSQL);
                
                while (rst.next())
                {  arlRegSalCon = new ArrayList();
                   arlRegSalCon.add(INT_ARL_SAL_CON_COD_EMP,     rst.getObject("co_emp") == null? "" :rst.getString("co_emp"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_COD_CTA,     rst.getObject("co_cta") == null? "" :rst.getString("co_cta"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_COD_CLI,     rst.getObject("co_cli") == null? "" :rst.getString("co_cli"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_NOM_CLI,     rst.getObject("tx_nomCli") == null? "" :rst.getString("tx_nomCli"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_COD_BCO,     rst.getObject("co_banChq") == null? "" :rst.getString("co_banChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_DES_COR_BCO, rst.getObject("tx_desCor_BcoChq") == null? "" :rst.getString("tx_desCor_BcoChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_DES_LAR_BCO, rst.getObject("tx_desLar_BcoChq") == null? "" :rst.getString("tx_desLar_BcoChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_NUM_CTACHQ,  rst.getObject("tx_numCtaChq") == null? "" :rst.getString("tx_numCtaChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_NUM_CHQ,     rst.getObject("tx_numChq") == null? "" :rst.getString("tx_numChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_MON_CHQ,     rst.getObject("nd_monChq") == null? "" :rst.getString("nd_monChq"));
                   arlRegSalCon.add(INT_ARL_SAL_CON_FEC_VENCHQ,  rst.getObject("fe_venChq") == null? "" :rst.getString("fe_venChq"));
                   arlDatSalCon.add(arlRegSalCon);
                }
                stm.close();
                stm = null;
                rst.close();
                rst = null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }
    
    private boolean cargarArregloSalDis(){
        boolean blnRes = true;
        int i, intArlCiuCodCiu;
        
        arlDatSalDis.clear();
        try 
        {  if (con != null)
           {  stm = con.createStatement();
              //1er. query: Todos los cheques depositados excepto los chq del banco seleccionado (por filtro o por fila) donde fe_venChq este en el (dia_actual - 2)
              strSQL =  "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
              strSQL += "           a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
              strSQL += "           a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
              strSQL += "           a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
              strSQL += "           (CASE ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
              strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
              strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
              strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
              strSQL += "            END) as co_bcodepchq ";
              strSQL += "FROM       tbm_detrecdoc as a1 ";
              strSQL += "INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
              strSQL += "INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
              strSQL += "INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
              strSQL += "INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
              strSQL += "LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
              strSQL += "LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
              strSQL += "INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";
              strSQL += "INNER JOIN tbm_loc as a9 on a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc ";

              if (objParSis.getCodigoUsuario() == 1)
              {  strSQL += "INNER JOIN tbr_tipDocPrg AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                 strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu() + " ";
              }
              else
              {  strSQL += "INNER JOIN tbr_tipDocUsr AS a10 ON a10.co_emp = a5.co_emp and a10.co_loc = a5.co_loc and a10.co_tipDoc = a5.co_tipDoc ";
                 strSQL += "WHERE a10.co_mnu = " + objParSis.getCodigoMenu();
                 strSQL += "      and a10.co_usr = " + objParSis.getCodigoUsuario() + " ";
              }
              
              if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                 strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
              else 
                 strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";
              
              if (!txtCodCta.getText().equals(""))
                 strSQL += "      and a8.co_ctadeb = " + txtCodCta.getText();

              strSQL += "           and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
              strSQL += "           and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";
              
              if (arlDatCiu.size() > 0)
              {  strSQL += " and (case ";
                 for (i = 0; i < arlDatCiu.size(); i++)
                 {  intArlCiuCodCiu = objUti.getIntValueAt(arlDatCiu, i, INT_ARL_CIU_COD_CIU);
                    strFecCob_HaciaAtr_ChqBcoDif_SalDis = objUti.getStringValueAt(arlDatCiu, i, INT_ARL_CIU_FEC_COB);
                    strSQL += "when a9.co_ciu = " + intArlCiuCodCiu + " then a5.fe_doc = '" + strFecCob_HaciaAtr_ChqBcoDif_SalDis + "' ";
                 }
                 strSQL += " end)";
              }
              
              strSQL += "           and a1.co_banchq <> (CASE ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
              strSQL += "                                   WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
              strSQL += "                                   WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
              strSQL += "                                   WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
              strSQL += "                                END) "; //co_bcodepchq: Resultado del case
              
              if (!strFecCob_ChqMisBco_SalDis.equals(""))
              {  //Si strFecCob_ChqMisBco_SalDis <> "", significa que es igual a Fe_act y no es sabado
                 strSQL += "UNION ALL ";
                 //2do. query: Solamente los cheques depositados del banco seleccionado (por filtro o por fila) donde fe_venChq = fecha_actual
                 strSQL += "SELECT DISTINCT a1.co_emp, a1.co_loc, a1.co_tipdoc as co_tipdoc_DetRecDoc, a1.co_doc as co_doc_DetRecDoc, a5.co_tipdoc as co_tipdoc_CabPag, a5.co_doc as co_doc_CabPag, ";
                 strSQL += "           a1.co_tipdoccon, a8.co_ctadeb as co_cta, a8.tx_descor as tx_descor_bcodepchq, a8.tx_deslar as tx_deslar_bcodepchq, ";
                 strSQL += "           a1.co_cli, a6.tx_nom as tx_nomcli, a1.co_banchq, a7.tx_descor as tx_descor_bcochq, a7.tx_deslar as tx_deslar_bcochq, ";
                 strSQL += "           a1.tx_numctachq, a1.tx_numchq, a5.fe_doc as fe_doc_CabPag, a1.fe_recchq, a1.fe_venchq, a1.nd_monchq, a1.tx_obs1, a1.st_asidocrel, a1.st_regrep, a5.st_reg, ";
                 strSQL += "           (CASE ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                 strSQL += "               WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                 strSQL += "               WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                 strSQL += "               WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                 strSQL += "            END) as co_bcodepchq ";
                 strSQL += "FROM       tbm_detrecdoc as a1 ";
                 strSQL += "INNER JOIN tbr_detrecdocpagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc and a2.co_reg = a1.co_reg ";
                 strSQL += "INNER JOIN tbm_pagmovinv as a3 on a3.co_emp = a2.co_emprel and a3.co_loc = a2.co_locrel and a3.co_tipdoc = a2.co_tipdocrel and a3.co_doc = a2.co_docrel and a3.co_reg = a2.co_regrel ";
                 strSQL += "INNER JOIN tbm_detpag as a4 on a4.co_emp = a3.co_emp and a4.co_locpag = a3.co_loc and a4.co_tipdocpag = a3.co_tipdoc and a4.co_docpag = a3.co_doc and a4.co_regpag = a3.co_reg ";
                 strSQL += "INNER JOIN tbm_cabpag as a5 on a5.co_emp = a4.co_emp and a5.co_loc = a4.co_loc and a5.co_tipdoc = a4.co_tipdoc and a5.co_doc = a4.co_doc ";
                 strSQL += "LEFT  JOIN tbm_cli as a6 on a6.co_emp = a1.co_emp and a6.co_cli = a1.co_cli ";
                 strSQL += "LEFT  JOIN tbm_var as a7 on a7.co_reg = a1.co_banchq ";
                 strSQL += "INNER JOIN tbm_cabtipdoc as a8 on a8.co_emp = a1.co_emp and a8.co_loc = a1.co_loc and a8.co_tipdoc = a1.co_tipdoccon ";

                 if (objParSis.getCodigoUsuario() == 1)
                 {  strSQL += "INNER JOIN tbr_tipDocPrg AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                    strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu() + " ";
                 }
                 else
                 {  strSQL += "INNER JOIN tbr_tipDocUsr AS a9 ON a9.co_emp = a5.co_emp and a9.co_loc = a5.co_loc and a9.co_tipDoc = a5.co_tipDoc ";
                    strSQL += "WHERE a9.co_mnu = " + objParSis.getCodigoMenu();
                    strSQL += "      and a9.co_usr = " + objParSis.getCodigoUsuario() + " ";
                 }

                 if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                    strSQL += "      and a1.co_emp IN (" + strCodEmpChk + ")";
                 else
                    strSQL += "      and a1.co_emp = " + objParSis.getCodigoEmpresa() + "";

                 if (!txtCodCta.getText().equals(""))
                    strSQL += "      and a8.co_ctadeb = " + txtCodCta.getText();

                 strSQL += "           and a1.st_asiDocRel = 'S' and a1.co_tipdoccon is not null and a1.nd_valapl <> 0 ";
                 strSQL += "           and a1.st_reg = 'A' and a2.st_reg = 'A' and a3.st_reg in ('A','C') and a4.st_reg = 'A' and a5.st_reg = 'A' ";

                 if (strFecCob_HaciaAtr_SalCon.equals(""))
                 {  //Si strFecCob_HaciaAtr_SalCon = "", significa que no se necesita tomar una fecha hacia atras para obtener el Saldo Disponible
                    //para cheques del mismo banco
                    strSQL += "        and a5.fe_doc = '" + strFecAct + "'";
                 }
                 else
                    strSQL += "        and a5.fe_doc between '" + strFecCob_HaciaAtr_SalCon + "' and '" + strFecAct + "'";

                 strSQL += "           and a1.co_banchq = (CASE ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 15   THEN 36 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 19   THEN 53 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 2012 THEN 60 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 2220 THEN 38 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 2221 THEN 85 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 3236 THEN 68 ";
                 strSQL += "                                  WHEN a1.co_emp = 1 and a8.co_ctadeb = 3270 THEN 84 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 15   THEN 53 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 1004 THEN 85 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 1200 THEN 38 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 1350 THEN 84 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 1352 THEN 68 ";
                 strSQL += "                                  WHEN a1.co_emp = 2 and a8.co_ctadeb = 1401 THEN 36 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 1820 THEN 60 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 1916 THEN 38 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 1917 THEN 85 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 2222 THEN 53 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 2374 THEN 84 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 2384 THEN 68 ";
                 strSQL += "                                  WHEN a1.co_emp = 4 and a8.co_ctadeb = 2399 THEN 36 ";
                 strSQL += "                               END) "; //co_bcodepchq: Resultado del case
              } //if (!strFecCob_ChqMisBco_SalDis.equals(""))
              
              rst = stm.executeQuery(strSQL);
                   
              while (rst.next())
              {  arlRegSalDis = new ArrayList();
                 arlRegSalDis.add(INT_ARL_SAL_DIS_COD_EMP,     "" + rst.getObject("co_emp") == null? "" :rst.getString("co_emp"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_COD_CTA,     "" + rst.getObject("co_cta") == null? "" :rst.getString("co_cta"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_COD_CLI,     "" + rst.getObject("co_cli") == null? "" :rst.getString("co_cli"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_NOM_CLI,     "" + rst.getObject("tx_nomcli") == null? "" :rst.getString("tx_nomcli"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_COD_BCO,     "" + rst.getObject("co_banchq") == null? "" :rst.getString("co_banchq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_DES_COR_BCO, "" + rst.getObject("tx_descor_bcochq") == null? "" :rst.getString("tx_descor_bcochq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_DES_LAR_BCO, "" + rst.getObject("tx_deslar_bcochq") == null? "" :rst.getString("tx_deslar_bcochq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_NUM_CTACHQ,  "" + rst.getObject("tx_numctachq") == null? "" :rst.getString("tx_numctachq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_NUM_CHQ,     "" + rst.getObject("tx_numchq") == null? "" :rst.getString("tx_numchq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_MON_CHQ,     "" + rst.getObject("nd_monchq") == null? "" :rst.getString("nd_monchq"));
                 arlRegSalDis.add(INT_ARL_SAL_DIS_FEC_VENCHQ,  "" + rst.getObject("fe_venchq") == null? "" :rst.getString("fe_venchq"));
                 arlDatSalDis.add(arlRegSalDis);
              }
              
              rst.close();
              stm.close();
              rst = null;
              stm = null;
           }  //if (con != null)

           //Asignar vectores al modelo.
           objTblMod.setData(vecDat);
           tblDat.setModel(objTblMod);
        } //try
        
        catch (java.sql.SQLException e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /** Configurar el formulario. */
    private boolean configurarFil()
    {
        boolean blnRes=true;
        try
        {
            //Configurar el combo "Mes de corte".
            cboMesCor.addItem("Enero");
            cboMesCor.addItem("Febrero");
            cboMesCor.addItem("Marzo");
            cboMesCor.addItem("Abril");
            cboMesCor.addItem("Mayo");
            cboMesCor.addItem("Junio");
            cboMesCor.addItem("Julio");
            cboMesCor.addItem("Agosto");
            cboMesCor.addItem("Septiembre");
            cboMesCor.addItem("Octubre");
            cboMesCor.addItem("Noviembre");
            cboMesCor.addItem("Diciembre");
            cboMesCor.setSelectedIndex(0);
            cargarAnioCreados();
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            
            panFilFec.add(dtpFecDoc);
            dtpFecDoc.setBounds(110, 20, 120, 20);


            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));

                        

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean cargarAnioCreados(){
        boolean blnRes=true;
        int intUltAni=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT co_emp, ne_ani";
                strSQL+=" FROM tbm_anicresis";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" ORDER BY ne_ani";
                
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    cboAnoCor.addItem("" + rst.getString("ne_ani"));
                    intUltAni++;
                }
                cboAnoCor.setSelectedIndex(intUltAni);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
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
    private String getFechaInicioMesCorte(String fechaCorte){
        Connection conFecCor;
        Statement stmFecCor;
        ResultSet rstFecCor;
        String strFecIniMesCor="";

        try{
            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));

            cal.set(java.util.Calendar.DATE, cal.getActualMinimum(cal.DAY_OF_MONTH)    );
            strFecIniMesCor=objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy");


        }
//        catch (java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strFecIniMesCor;

    }
    private String getPeriodoAnterior(){
        String strPerAnt="";
        int intFecPerAnt[];
        try{
//            java.util.Calendar cal=java.util.Calendar.getInstance();
//            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
//            cal.set(java.util.Calendar.MONTH,cal.get((cal.MONTH))-1);
//            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));
//
//            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
//            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

//            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );

            

            java.util.Calendar cal=java.util.Calendar.getInstance();
            cal.setTime(objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"));
            //cal.set(java.util.Calendar.MONTH, cal.get((cal.MONTH))-1);

            cal.add(java.util.Calendar.MONTH, -1);

            intFecPerAnt=dtpFecDoc.getFecha(objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy"));

            System.out.println("periodo anterior:  "+ objUti.formatearFecha(cal.getTime(), "dd/MM/yyyy") );
            System.out.println("arreglo: " + intFecPerAnt[2] +  "/" + intFecPerAnt[1] );

            strPerAnt="" + (intFecPerAnt[2] + "" + (intFecPerAnt[1]<=9?"0" + intFecPerAnt[1]:"" + intFecPerAnt[1])  );


        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strPerAnt;
    }
}