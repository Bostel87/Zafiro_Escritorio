/*
 * ZafVen28.java
 *
 * Created on 18 de julio de 2011, 10:10 PM
 */

package Ventas.ZafVen18;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafVen18 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:

    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    static final int INT_TBL_DAT_COD_DOC=5;
    static final int INT_TBL_DAT_NUM_DOC=6;
    static final int INT_TBL_DAT_FEC_DOC=7;
    static final int INT_TBL_DAT_COD_CLI=8;
    static final int INT_TBL_DAT_RUC_CLI=9;
    static final int INT_TBL_DAT_NOM_CLI=10;
    static final int INT_TBL_DAT_FOR_PAG=11;
    static final int INT_TBL_DAT_SUB=12;
    static final int INT_TBL_DAT_TOT=13;
    static final int INT_TBL_DAT_SUB_TOT_FIL_ITM=14;
    static final int INT_TBL_DAT_EST_DOC=15;
    
    
    static final int INT_TBL_DET_LIN=0;
    static final int INT_TBL_DET_NOM_LOC=1;
    static final int INT_TBL_DET_TIP_DOC=2;
    static final int INT_TBL_DET_NUM_DOC=3;
    static final int INT_TBL_DET_COD_ITM=4;
    static final int INT_TBL_DET_COD_ALT_ITM=5;
    static final int INT_TBL_DET_NOM_ITM=6;
    static final int INT_TBL_DET_UNI_MED=7;
    static final int INT_TBL_DET_CAN=8;
    static final int INT_TBL_DET_PRE_UNI=9;
    static final int INT_TBL_DET_TOT=10;


    //Para la tabla de campos
    static final int INT_TBL_DAT_CAM_LIN=0;
    static final int INT_TBL_DAT_CAM_CHK=1;
    static final int INT_TBL_DAT_CAM_HEA=2;
    static final int INT_TBL_DAT_CAM_DES=3;
    static final int INT_TBL_DAT_CAM_COL_TBL=4;


    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod, objTblModCam, objTblModTot;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenCon vcoItm, vcoTipDoc,vcoLoc;                                   //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        //Para el stock de cada bodega.
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strNomItm;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.

    private String strCodEmp, strCodCli, strNomEmp, strNomCli;
    private ZafVenCon vcoEmp, vcoCli;

    private String strDesCorTipDoc, strDesLarTipDoc, strCodLoc, strNomLoc;
    private Vector vecCamDat, vecCamCab, vecCamReg, vecCamAux;
    private ZafMouMotAdaCam objMouMotAdaCam;
    private boolean blnMarTodChkTblCam;


    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;

    private java.util.Date datFecAux;
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen18(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            if (!configurarFrm())
                exitForm();
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

        bgrFil = new javax.swing.ButtonGroup();
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        panCon = new javax.swing.JPanel();
        panFec = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panCodAltItmTer = new javax.swing.JPanel();
        txtCodAltItmTer = new javax.swing.JTextField();
        lblCodAltItmTer = new javax.swing.JLabel();
        lblCodVen = new javax.swing.JLabel();
        txtAliVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        butCli = new javax.swing.JButton();
        lblEmp1 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        chkTotDocCli = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        cboBln = new javax.swing.JComboBox();
        cboTipForPag = new javax.swing.JComboBox();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        txtCodVen = new javax.swing.JTextField();
        txtRucCli = new javax.swing.JTextField();
        txtCodItmMae = new javax.swing.JTextField();
        panLoc = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCam = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panRptMovReg = new javax.swing.JPanel();
        chkMosMovReg = new javax.swing.JCheckBox();
        panMovReg = new javax.swing.JPanel();
        spnMovReg = new javax.swing.JScrollPane();
        tblMovReg = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butVisPre = new javax.swing.JButton();
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

        jPanel1.setAutoscrolls(true);
        jPanel1.setPreferredSize(new java.awt.Dimension(100, 150));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(100, 50));

        panCon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCon.setLayout(null);

        panFec.setPreferredSize(new java.awt.Dimension(100, 60));
        panFec.setLayout(new java.awt.BorderLayout());
        panCon.add(panFec);
        panFec.setBounds(0, 0, 660, 68);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(0, 16, 400, 14);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 2, 400, 14);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(20, 130, 60, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 15, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 15, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 15, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 15, 100, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(18, 150, 328, 40);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(60, 130, 56, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(120, 130, 70, 20);

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
        panFil.add(txtNomItm);
        txtNomItm.setBounds(190, 130, 360, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(550, 130, 20, 20);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 15, 184, 20);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 14, 100, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(350, 150, 308, 40);

        lblCodVen.setText("Vendedor:");
        lblCodVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblCodVen);
        lblCodVen.setBounds(20, 110, 90, 20);

        txtAliVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtAliVenActionPerformed(evt);
            }
        });
        txtAliVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtAliVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtAliVenFocusLost(evt);
            }
        });
        panFil.add(txtAliVen);
        txtAliVen.setBounds(120, 110, 70, 20);

        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(190, 110, 360, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(550, 110, 20, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmp);
        lblEmp.setBounds(20, 30, 60, 20);

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
        txtCodEmp.setBounds(120, 30, 70, 20);

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
        txtNomEmp.setBounds(190, 30, 360, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(550, 30, 20, 20);

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
        panFil.add(txtCodCli);
        txtCodCli.setBounds(120, 90, 70, 20);

        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panFil.add(txtNomCli);
        txtNomCli.setBounds(190, 90, 360, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Vendedor/Comprador");
        panFil.add(lblCli);
        lblCli.setBounds(20, 90, 60, 18);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(550, 90, 20, 20);

        lblEmp1.setText("Local:");
        lblEmp1.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmp1);
        lblEmp1.setBounds(20, 50, 60, 20);

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
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(120, 50, 70, 20);

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
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(190, 50, 360, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(550, 50, 20, 20);

        chkTotDocCli.setText("Totalizar documentos por cliente");
        chkTotDocCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkTotDocCliActionPerformed(evt);
            }
        });
        panFil.add(chkTotDocCli);
        chkTotDocCli.setBounds(16, 231, 550, 15);

        jLabel1.setText("Tipo de forma de pago:");
        panFil.add(jLabel1);
        jLabel1.setBounds(20, 214, 150, 14);

        cboBln.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Igual", "Diferente" }));
        panFil.add(cboBln);
        cboBln.setBounds(200, 210, 150, 20);

        cboTipForPag.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todas", "0=Negociación", "1=Contado", "2=Cheque", "3=Crédito" }));
        panFil.add(cboTipForPag);
        cboTipForPag.setBounds(350, 210, 190, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(20, 70, 110, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(88, 70, 32, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(120, 70, 70, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(190, 70, 360, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(550, 70, 20, 20);

        jLabel2.setText("Estado del documento:");
        panFil.add(jLabel2);
        jLabel2.setBounds(20, 194, 130, 14);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A:Activo", "I:Inactivo" }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(150, 190, 240, 20);
        panFil.add(txtCodVen);
        txtCodVen.setBounds(90, 110, 30, 20);
        panFil.add(txtRucCli);
        txtRucCli.setBounds(574, 90, 80, 20);
        panFil.add(txtCodItmMae);
        txtCodItmMae.setBounds(570, 130, 80, 20);

        panCon.add(panFil);
        panFil.setBounds(1, 68, 660, 246);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de campos"));
        panLoc.setAutoscrolls(true);
        panLoc.setPreferredSize(new java.awt.Dimension(0, 100));
        panLoc.setLayout(null);

        tblCam.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCam);

        panLoc.add(jScrollPane1);
        jScrollPane1.setBounds(8, 15, 640, 220);

        panCon.add(panLoc);
        panLoc.setBounds(0, 315, 660, 240);

        jScrollPane2.setViewportView(panCon);

        jPanel3.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", jPanel1);

        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.8);
        sppRpt.setOneTouchExpandable(true);

        panRptReg.setLayout(new java.awt.BorderLayout(0, 1));

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panRptReg.add(spnTot, java.awt.BorderLayout.SOUTH);

        sppRpt.setTopComponent(panRptReg);

        panRptMovReg.setLayout(new java.awt.BorderLayout());

        chkMosMovReg.setText("Mostrar movimientos del item");
        chkMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMovRegActionPerformed(evt);
            }
        });
        panRptMovReg.add(chkMosMovReg, java.awt.BorderLayout.NORTH);

        panMovReg.setBorder(javax.swing.BorderFactory.createTitledBorder("Movimientos del item"));
        panMovReg.setLayout(new java.awt.BorderLayout());

        tblMovReg.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnMovReg.setViewportView(tblMovReg);

        panMovReg.add(spnMovReg, java.awt.BorderLayout.CENTER);

        panRptMovReg.add(panMovReg, java.awt.BorderLayout.CENTER);

        sppRpt.setBottomComponent(panRptMovReg);

        panRpt.add(sppRpt, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butVisPre.setText("Vista Preliminar");
        butVisPre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVisPreActionPerformed(evt);
            }
        });
        panBot.add(butVisPre);

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
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

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0){
            optFil.setSelected(true);
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodItmMae.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void chkMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovRegActionPerformed
        if (chkMosMovReg.isSelected())
            cargarMovReg();
        else
            objTblModDab.removeAllRows();
    }//GEN-LAST:event_chkMosMovRegActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0){
            optFil.setSelected(true);
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodItmMae.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());

            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodItmMae.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodVen.setText("");
            txtAliVen.setText("");
            txtNomVen.setText("");
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
            //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
            if (butCon.getText().equals("Consultar"))
            {
                blnCon=true;
                if (objThrGUI==null)
                {
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }            
            }
            else
            {
                blnCon=false;
            }
        }
    }//GEN-LAST:event_butConActionPerformed

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

    private void txtAliVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtAliVenActionPerformed
        txtAliVen.transferFocus();
}//GEN-LAST:event_txtAliVenActionPerformed

    private void txtAliVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAliVenFocusGained
        strCodVen=txtAliVen.getText();
        txtAliVen.selectAll();
}//GEN-LAST:event_txtAliVenFocusGained

    private void txtAliVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtAliVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtAliVen.getText().equalsIgnoreCase(strCodVen)) {
            if (txtAliVen.getText().equals("")) {
                txtCodVen.setText("");
                txtAliVen.setText("");
                txtNomVen.setText("");
            } else {
                mostrarVenConVen(1);
            }
        } else
            txtAliVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtAliVen.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtAliVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)) {
            if (txtNomVen.getText().equals("")) {
                txtCodVen.setText("");
                txtAliVen.setText("");
                txtNomVen.setText("");
            } else {
                mostrarVenConVen(2);
            }
        } else
            txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtAliVen.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtAliVen.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodEmp.getText().equals("")){
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                panLoc.setVisible(false);
                objTblMod.removeAllRows();
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtNomCli.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodEmp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                objTblMod.removeAllRows();
                panLoc.setVisible(false);
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)){
            if (txtCodCli.getText().equals("")){
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConCli(1);
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strNomCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strNomCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtRucCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtNomCli.setText(strNomCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        // TODO add your handling code here:
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)){
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConLoc(1);
        }
        else
            txtCodLoc.setText(strCodLoc);
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        // TODO add your handling code here:
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

    private void chkTotDocCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkTotDocCliActionPerformed
        // TODO add your handling code here:
        if(chkTotDocCli.isSelected()){
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                objTblModDab.removeSystemHiddenColumn(INT_TBL_DET_TIP_DOC, tblMovReg);
                objTblModDab.removeSystemHiddenColumn(INT_TBL_DET_NUM_DOC, tblMovReg);
            }
            else{//por empresa no se muestra
                objTblModDab.addSystemHiddenColumn(INT_TBL_DET_TIP_DOC, tblMovReg);
                objTblModDab.addSystemHiddenColumn(INT_TBL_DET_NUM_DOC, tblMovReg);
            }
        }
        else{
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_TIP_DOC, tblMovReg);
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_NUM_DOC, tblMovReg);
        }
    }//GEN-LAST:event_chkTotDocCliActionPerformed

    private void butVisPreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVisPreActionPerformed
        // TODO add your handling code here:
        if (objThrGUIVisPre==null)
        {
            objThrGUIVisPre=new ZafThreadGUIVisPre();
            objThrGUIVisPre.setIndFunEje(1);
            objThrGUIVisPre.start();
        }
    }//GEN-LAST:event_butVisPreActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JButton butVisPre;
    private javax.swing.JComboBox cboBln;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JComboBox cboTipForPag;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JCheckBox chkTotDocCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblCodVen;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEmp1;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panMovReg;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptMovReg;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnMovReg;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCam;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblMovReg;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtAliVen;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    private javax.swing.JTextField txtRucCli;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFec.add(objSelFec);
            objSelFec.setBounds(4, 0, 472, 68);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.1");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.

            configurarVenConVen();
            configurarVenConItm();

            //Configurar los JTables.
            configurarTblDat();
            configurarTblDet();
            configurarVenConTipDoc();
            configurarVenConLoc();
            configurarTblCam();
            cargarCampos();


            configurarVenConCli();

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();
                
                txtCodEmp.setEditable(true);
                txtNomEmp.setEditable(true);
                butEmp.setEnabled(true);
            }
            else{
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
            }

            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta funci�n configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCam(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecCamDat=new Vector();    //Almacena los datos
            vecCamCab=new Vector(5);  //Almacena las cabeceras
            vecCamCab.clear();
            vecCamCab.add(INT_TBL_DAT_CAM_LIN,"");
            vecCamCab.add(INT_TBL_DAT_CAM_CHK,"");
            vecCamCab.add(INT_TBL_DAT_CAM_HEA,"Campo");
            vecCamCab.add(INT_TBL_DAT_CAM_DES,"Descripción");
            vecCamCab.add(INT_TBL_DAT_CAM_COL_TBL,"Columna");

            objTblModCam=new ZafTblMod();
            objTblModCam.setHeader(vecCamCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblCam.setModel(objTblModCam);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblCam.setRowSelectionAllowed(true);
            tblCam.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCam);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCam.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCam.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CAM_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CAM_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAM_HEA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAM_DES).setPreferredWidth(250);
            
            tcmAux.getColumn(INT_TBL_DAT_CAM_COL_TBL).setPreferredWidth(50);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCam.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaCam=new ZafMouMotAdaCam();
            tblCam.getTableHeader().addMouseMotionListener(objMouMotAdaCam);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblCam);
            tcmAux.getColumn(INT_TBL_DAT_CAM_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecCamAux=new Vector();
            vecCamAux.add("" + INT_TBL_DAT_CAM_CHK);
            objTblModCam.setColumnasEditables(vecCamAux);
            vecCamAux=null;
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CAM_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblCam);
            tcmAux.getColumn(INT_TBL_DAT_CAM_CHK).setCellEditor(objTblCelEdiChk);

            objTblModCam.setModoOperacion(objTblModCam.INT_TBL_EDI);


            //Configurar JTable: Ocultar columnas del sistema.
            //objTblModCam.addSystemHiddenColumn(INT_TBL_DAT_CAM_COL_TBL, tblCam);


            tblCam.getTableHeader().addMouseMotionListener(new ZafMouMotAdaCam());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblCam.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblCamMouseClicked(evt);
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
     * Esta función configura el JTable "tblDat".
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
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_RUC_CLI,"Ruc.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCab.add(INT_TBL_DAT_FOR_PAG,"Forma de pago");
            vecCab.add(INT_TBL_DAT_SUB,"Subtotal");
            vecCab.add(INT_TBL_DAT_TOT,"Total");
            vecCab.add(INT_TBL_DAT_SUB_TOT_FIL_ITM,"SubTot.Fil.Itm.");
            vecCab.add(INT_TBL_DAT_EST_DOC,"Est.Doc.");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);



            objTblModTot=new ZafTblMod();
            tblTot.setModel(objTblModTot);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_RUC_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_FOR_PAG).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_SUB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_SUB_TOT_FIL_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setPreferredWidth(30);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);

            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblTot);
            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblTot);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_SUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SUB_TOT_FIL_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_SUB, INT_TBL_DAT_TOT, INT_TBL_DAT_SUB_TOT_FIL_ITM};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLis());
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
     * Esta función configura el JTable "tblDet".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDet()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatMov=new Vector();    //Almacena los datos
            vecCabMov=new Vector(11);  //Almacena las cabeceras
            vecCabMov.clear();
            vecCabMov.add(INT_TBL_DET_LIN,"");
            vecCabMov.add(INT_TBL_DET_NOM_LOC,"Nom.Loc.");
            vecCabMov.add(INT_TBL_DET_TIP_DOC,"Tip.Doc.");
            vecCabMov.add(INT_TBL_DET_NUM_DOC,"Núm.Doc.");
            vecCabMov.add(INT_TBL_DET_COD_ITM,"Cód.Itm.");
            vecCabMov.add(INT_TBL_DET_COD_ALT_ITM,"Cód.Alt.");
            vecCabMov.add(INT_TBL_DET_NOM_ITM,"Nombre");
            vecCabMov.add(INT_TBL_DET_UNI_MED,"Uni.");
            vecCabMov.add(INT_TBL_DET_CAN,"Can.");
            vecCabMov.add(INT_TBL_DET_PRE_UNI,"Pre.Uni.");
            vecCabMov.add(INT_TBL_DET_TOT,"Total");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDab=new ZafTblMod();
            objTblModDab.setHeader(vecCabMov);
            tblMovReg.setModel(objTblModDab);
            //Configurar JTable: Establecer tipo de selección.
            tblMovReg.setRowSelectionAllowed(true);
            tblMovReg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblMovReg);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblMovReg.getColumnModel();
            tcmAux.getColumn(INT_TBL_DET_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_NOM_LOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_COD_ITM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DET_NOM_ITM).setPreferredWidth(160);
            tcmAux.getColumn(INT_TBL_DET_UNI_MED).setPreferredWidth(45);
            tcmAux.getColumn(INT_TBL_DET_CAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_PRE_UNI).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DET_TOT).setPreferredWidth(70);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_COD_ITM, tblMovReg);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblMovReg.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblMovReg);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_PRE_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            tcmAux=null;

            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                objTblModDab.addSystemHiddenColumn(INT_TBL_DET_NOM_LOC, tblMovReg);


        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                String strConSQLItm="";

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    if(txtCodEmp.getText().length()>0)
                        strConSQL+=" 			WHERE a1.co_emp=" + txtCodEmp.getText() + "";
                    else
                        strConSQL+=" 			WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                }
                else{//si es por empresa
                    strConSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }

                if(txtCodLoc.getText().length()>0)
                    strConSQL+=" 			AND a1.co_loc=" + txtCodLoc.getText() + "";


                if(txtCodTipDoc.getText().length()>0)
                    strConSQL+=" 			AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                
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
                if (txtCodVen.getText().length()>0)
                    strConSQL+=" AND a1.co_com=" + txtCodVen.getText();

                if (txtCodItm.getText().length()>0)
                {
                    strConSQLItm+=" AND a8.co_itmMae=" + txtCodItmMae.getText();
                }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                {
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strConSQLItm+=" AND ((LOWER(a6.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a6.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    else
                        strConSQLItm+=" AND ((LOWER(a6.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a6.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if (txtCodAltItmTer.getText().length()>0)
                {
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strConSQLItm+=getConSQLAdiCamTer("a6.tx_codAlt", txtCodAltItmTer.getText());
                    else
                        strConSQLItm+=getConSQLAdiCamTer("a6.tx_codAlt", txtCodAltItmTer.getText());
                }

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    if(txtRucCli.getText().length()>0)
                        strConSQL+="                   AND a1.tx_ide='" + txtRucCli.getText() + "'";
                }
                else{
                    if(txtCodCli.getText().length()>0)
                        strConSQL+="                   AND a1.co_cli=" + txtCodCli.getText() + "";
                }

                if(cboBln.getSelectedIndex()==0){//debe ser igual a lo que esta en el filtro
                    if(cboTipForPag.getSelectedIndex()==0){//todos

                    }
                    else if(cboTipForPag.getSelectedIndex()==1){//0:Negociación
                        strConSQL+="               AND a5.ne_tipForPag=0";
                    }
                    else if(cboTipForPag.getSelectedIndex()==2){//1:Contado
                        strConSQL+="               AND a5.ne_tipForPag=1";
                    }
                    else if(cboTipForPag.getSelectedIndex()==3){//2:Cheque
                        strConSQL+="               AND a5.ne_tipForPag=2";
                    }
                    else if(cboTipForPag.getSelectedIndex()==4){//3:Crédito
                        strConSQL+="               AND a5.ne_tipForPag=3";
                    }
                }
                else if(cboBln.getSelectedIndex()==1){//debe ser diferente a lo que esta en el filtro
                    if(cboTipForPag.getSelectedIndex()==0){//todos
                        strConSQL+="               AND a5.ne_tipForPag NOT IN(0,1,2,3)";
                    }
                    else if(cboTipForPag.getSelectedIndex()==1){//0:Negociación
                        strConSQL+="               AND a5.ne_tipForPag NOT IN(0)";
                    }
                    else if(cboTipForPag.getSelectedIndex()==2){//1:Contado
                        strConSQL+="               AND a5.ne_tipForPag NOT IN(1)";
                    }
                    else if(cboTipForPag.getSelectedIndex()==3){//2:Cheque
                        strConSQL+="               AND a5.ne_tipForPag NOT IN(2)";
                    }
                    else if(cboTipForPag.getSelectedIndex()==4){//3:Crédito
                        strConSQL+="               AND a5.ne_tipForPag NOT IN(3)";
                    }
                }

                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos exepto los eliminados
                    strConSQL+=" AND a1.st_reg NOT IN ('E')";
                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                    strConSQL+=" AND a1.st_reg IN ('A')";
                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los activos
                    strConSQL+=" AND a1.st_reg IN ('I')";


                    strSQL="";
                    if(chkTotDocCli.isSelected()){
                        strSQL+=" ";

                        strSQL+=" SELECT '' AS co_emp, '' AS co_loc, '' AS co_tipDoc, '' AS tx_desCor, '' AS co_doc, '' AS ne_numDoc";
                        strSQL+=" ,'' AS fe_doc";
                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" , 0 AS co_cli";
                        else
                            strSQL+=" ,e1.co_cli";
                        strSQL+=" , e1.tx_nomCli, e1.tx_ruc AS tx_ruc, '' AS tx_desforpag";
                        strSQL+=" , SUM(e1.nd_sub) AS nd_sub, SUM(e1.nd_tot) AS nd_tot, SUM(e1.nd_subTotFilItm) AS nd_subTotFilItm, e1.st_reg FROM(";
                    }
                    strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.co_cli";
                    strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli, b1.tx_desforpag, b1.nd_sub, b1.nd_tot, b1.st_reg";
                    if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                        strSQL+=" , SUM(b2.nd_subTotFilItm) AS nd_subTotFilItm FROM(";
                    }
                    else{
                        strSQL+=" , b1.nd_sub AS nd_subTotFilItm FROM(";
                    }
                    strSQL+=" 	SELECT c1.* FROM(";
                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli";
                    strSQL+=" 		, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, SUM(a1.nd_sub) AS nd_sub, SUM(a1.nd_tot) AS nd_tot, a1.st_reg";
                    strSQL+=" 		FROM	(         (       tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                    strSQL+=" 				      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+="             INNER JOIN tbr_tipDocPrg AS a4";
                        strSQL+="             ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc";
                    }
                    else{
                        strSQL+="             INNER JOIN tbr_tipDocUsr AS a4";
                        strSQL+="             ON a2.co_emp=a4.co_emp and a2.co_loc=a4.co_loc and a2.co_tipdoc=a4.co_tipdoc";
                    }
                    strSQL+="			     )";
                    strSQL+=" 			   LEFT OUTER JOIN tbm_cabForPag AS a5";
                    strSQL+=" 			   ON a1.co_emp=a5.co_emp AND a1.co_forpag=a5.co_forpag";
                    strSQL+=" 			)";
                    strSQL+=" 		 INNER JOIN tbm_cli AS a3";
                    strSQL+=" 		 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                            strSQL+=" INNER JOIN tbr_cliLoc AS a9";
                            strSQL+=" ON a3.co_emp=a9.co_emp AND a3.co_cli=a9.co_cli";
                        }
                    }
                    strSQL+=" 		 AND a4.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items, este no lleva el filtro por items.

                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                            if(txtCodLoc.getText().length()<=0)
                                strSQL+="    AND a9.co_loc=" + objParSis.getCodigoLocal() + "";
                        }
                    }
                    strSQL+=" 		 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc";
                    strSQL+=" 		 , a1.fe_doc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, a1.st_reg";
                    strSQL+=" 		 ORDER BY a1.tx_nomCli,a1.ne_numDoc";
                    strSQL+=" 	 ) AS c1";
                    if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                        strSQL+=" 	 INNER JOIN(";
                        strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                        strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                        strSQL+=" 		INNER JOIN tbm_inv AS a7";
                        strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                        strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                        strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                        strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                        strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                        strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                        strSQL+=" 	 ) AS c2";
                        strSQL+=" 	 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                    }
                    strSQL+=" ) AS b1";
                    if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                        strSQL+=" INNER JOIN(";
                        strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg, a6.nd_tot AS nd_subTotFilItm";
                        strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                        strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                        strSQL+=" 		INNER JOIN tbm_inv AS a7";
                        strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                        strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                        strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                        strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                        strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                        strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg,a6.nd_tot";
                        strSQL+=" ) AS b2";
                        strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                    }
                    strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.co_cli";
                    strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli, b1.tx_desforpag, b1.nd_sub, b1.nd_tot, b1.st_reg";
                    strSQL+=" ORDER BY b1.fe_doc";
                    if(chkTotDocCli.isSelected()){
                        strSQL+=" ) AS e1";
                        strSQL+=" GROUP BY ";
                        if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" e1.co_cli,";
                        strSQL+=" e1.tx_ruc, e1.tx_nomCli, e1.st_reg";
                        strSQL+=" ORDER BY e1.tx_nomCli";
                    }



                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
//                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,         rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_RUC_CLI,         rst.getString("tx_ruc"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,         rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_FOR_PAG,         rst.getString("tx_desforpag"));
                        vecReg.add(INT_TBL_DAT_SUB,             rst.getString("nd_sub"));
                        vecReg.add(INT_TBL_DAT_TOT,             rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_SUB_TOT_FIL_ITM, rst.getString("nd_subTotFilItm"));
                        vecReg.add(INT_TBL_DAT_EST_DOC,         rst.getString("st_reg"));
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
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
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);


                //ocultar columnas segun tabla de 'Listado de campos'
                String strColCam="";
                for(int k=0; k<objTblModCam.getRowCountTrue(); k++){
                    if(objTblModCam.isChecked(k, INT_TBL_DAT_CAM_CHK)){
                        strColCam=objTblModCam.getValueAt(k, INT_TBL_DAT_CAM_COL_TBL)==null?"":objTblModCam.getValueAt(k, INT_TBL_DAT_CAM_COL_TBL).toString();
                        objTblMod.removeSystemHiddenColumn(Integer.parseInt(strColCam), tblDat);
                        objTblModTot.removeSystemHiddenColumn(Integer.parseInt(strColCam), tblTot);



                    }
                    else{
                        strColCam=objTblModCam.getValueAt(k, INT_TBL_DAT_CAM_COL_TBL)==null?"":objTblModCam.getValueAt(k, INT_TBL_DAT_CAM_COL_TBL).toString();
                        objTblMod.addSystemHiddenColumn(Integer.parseInt(strColCam), tblDat);
                        objTblModTot.addSystemHiddenColumn(Integer.parseInt(strColCam), tblTot);
                    }
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
     * Esta función permite cargar el movimiento del item seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarMovReg(){
        boolean blnRes=true;
        int intFilSel=-1;
        String strEstDoc="";
        try{
            intFilSel=tblDat.getSelectedRow();
            if (intFilSel!=-1){
                strEstDoc=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_EST_DOC).toString();
                if( !(  (strEstDoc.equals("I")) || (strEstDoc.equals("E"))  )  ){
                    con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con!=null){
                        stm=con.createStatement();
                        //Obtener la condición.
                        strConSQL="";
                        switch (objSelFec.getTipoSeleccion()){
                            case 0: //Búsqueda por rangos
                                strConSQL+=" WHERE a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 1: //Fechas menores o iguales que "Hasta".
                                strConSQL+=" WHERE a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 2: //Fechas mayores o iguales que "Desde".
                                strConSQL+=" WHERE a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                            case 3: //Todo.
                                break;
                        }
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a6.co_reg, a16.tx_desCor AS tx_desCorTipDoc";
                        strSQL+=", a6.co_itm, a6.tx_codalt, a6.tx_nomitm, a6.tx_unimed, a6.nd_can, a6.nd_preuni, a6.nd_tot, a15.tx_nom AS tx_nomLoc";
                        strSQL+=" FROM (        ";
                        if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                            strSQL+="       (      (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a16 ON a1.co_emp=a16.co_emp ANd a1.co_loc=a16.co_loc AND a1.co_tipDoc=a16.co_tipDoc";

                            if(objParSis.getCodigoUsuario()==1){
                                strSQL+="               INNER JOIN tbr_tipDocPrg AS a4";
                                strSQL+="               ON a16.co_emp=a4.co_emp AND a16.co_loc=a4.co_loc AND a16.co_tipDoc=a4.co_tipDoc";
                            }
                            else{
                                strSQL+="               INNER JOIN tbr_tipDocUsr AS a4";
                                strSQL+="               ON a16.co_emp=a4.co_emp and a16.co_loc=a4.co_loc and a16.co_tipdoc=a4.co_tipdoc";
                            }

                            strSQL+="       )";
                            strSQL+="                   INNER JOIN tbm_loc AS a15 ON a1.co_emp=a15.co_emp AND a1.co_loc=a15.co_loc";
                            strSQL+="                   INNER JOIN tbr_cliLoc AS a9 ON a15.co_emp=a9.co_emp AND a15.co_loc=a9.co_loc)";
                        }
                        else{
                            strSQL+="       (     (tbm_cabMovInv AS a1  INNER JOIN tbm_cabTipDoc AS a16 ON a1.co_emp=a16.co_emp ANd a1.co_loc=a16.co_loc AND a1.co_tipDoc=a16.co_tipDoc";

                            if(objParSis.getCodigoUsuario()==1){
                                strSQL+="               INNER JOIN tbr_tipDocPrg AS a4";
                                strSQL+="               ON a16.co_emp=a4.co_emp AND a16.co_loc=a4.co_loc AND a16.co_tipDoc=a4.co_tipDoc";
                            }
                            else{
                                strSQL+="               INNER JOIN tbr_tipDocUsr AS a4";
                                strSQL+="               ON a16.co_emp=a4.co_emp and a16.co_loc=a4.co_loc and a16.co_tipdoc=a4.co_tipdoc";
                            }
                            strSQL+="       )";

                            strSQL+="      INNER JOIN tbm_loc AS a15 ON a1.co_emp=a15.co_emp AND a1.co_loc=a15.co_loc)";
                        }

                        strSQL+=" INNER JOIN tbm_detMovInv AS a6";
                        strSQL+="       ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                        strSQL+="       INNER JOIN tbm_inv AS a7";
                        strSQL+="       ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                        strSQL+="       INNER JOIN tbm_equInv AS a8";
                        strSQL+="       ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                        strSQL+=" )";
                        strSQL+=strConSQL;//fecha
                        if(chkTotDocCli.isSelected()){
                            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                strSQL+=" AND a1.tx_ruc='" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_RUC_CLI) + "'";
                                strSQL+=" AND a1.tx_nomCli='" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NOM_CLI) + "'";
                            }
                            else{
                                strSQL+=" AND a1.co_cli=" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_CLI) + "";
                            }
                        }
                        else{
                            strSQL+=" AND a1.co_emp="  + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP) + "";
                            strSQL+=" AND a1.co_loc="    + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC) + "";
                            strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC) + "";
                            strSQL+=" AND a1.co_doc="    + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC) + "";
                        }
                        strSQL+=" AND a1.st_reg NOT IN('E', 'I')";
                        strSQL+=" AND a4.co_mnu=" + objParSis.getCodigoMenu() + "";
                        if(objParSis.getCodigoUsuario()!=1)
                            strSQL+=" AND a4.co_usr=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a6.co_reg, a16.tx_desCor";
                        strSQL+=" , a6.co_itm, a6.tx_codalt, a6.tx_nomitm, a6.tx_unimed, a6.nd_can, a6.nd_preuni, a6.nd_tot, a15.tx_nom";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg";

                        System.out.println("cargarMovReg:" + strSQL);
                        rst=stm.executeQuery(strSQL);

                        //Limpiar vector de datos.
                        vecDatMov.clear();
                        //Obtener los registros.
                        while (rst.next())
                        {
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DET_LIN,"");
                            vecReg.add(INT_TBL_DET_NOM_LOC,rst.getString("tx_nomLoc"));
                            vecReg.add(INT_TBL_DET_TIP_DOC,rst.getString("tx_desCorTipDoc"));
                            vecReg.add(INT_TBL_DET_NUM_DOC,rst.getString("ne_numDoc"));
                            vecReg.add(INT_TBL_DET_COD_ITM,rst.getString("co_itm"));
                            vecReg.add(INT_TBL_DET_COD_ALT_ITM,rst.getString("tx_codalt"));
                            vecReg.add(INT_TBL_DET_NOM_ITM,rst.getString("tx_nomitm"));
                            vecReg.add(INT_TBL_DET_UNI_MED,rst.getString("tx_unimed"));
                            vecReg.add(INT_TBL_DET_CAN,rst.getString("nd_can"));
                            vecReg.add(INT_TBL_DET_PRE_UNI,rst.getString("nd_preuni"));
                            vecReg.add(INT_TBL_DET_TOT,rst.getString("nd_tot"));
                            vecDatMov.add(vecReg);
                        }
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        //Asignar vectores al modelo.
                        objTblModDab.setData(vecDatMov);
                        tblMovReg.setModel(objTblModDab);
                        vecDatMov.clear();
                    }
                }
                else{
                    objTblModDab.removeAllRows();
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        int intNumFilTblLoc, i, j;
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){

        }
        else{
//                tabFrm.setSelectedIndex(0);
//                mostrarMsgInf("<HTML>Debe seleccionar el filtro de ventas a mostrar.<BR>Seleccione <FONT COLOR=\"blue\">Ventas a clientes</FONT> y vuelva a intentarlo.</HTML>");
//                chkVtaCli.requestFocus();
//                return false;

        }




        return true;
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
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Vendedores".
     */
    private boolean configurarVenConVen()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("120");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL+=" FROM tbm_usr AS a1";
            strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoVen.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            arlCam.add("a3.co_itmMae");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            arlAli.add("cód.Itm.Mae.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor, a3.co_itmMae";
            strSQL+=" FROM (tbm_inv AS a1 INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(1);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtAliVen.setText(vcoVen.getValueAt(2));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.tx_usr", txtAliVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtAliVen.setText(vcoVen.getValueAt(2));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtAliVen.setText(vcoVen.getValueAt(2));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtAliVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtAliVen.setText(vcoVen.getValueAt(2));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtAliVen.setText(vcoVen.getValueAt(2));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtNomVen.setText(strNomVen);
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
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                        txtCodItmMae.setText(vcoItm.getValueAt(5));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                        txtCodItmMae.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                            txtCodItmMae.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                        txtCodItmMae.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                            txtCodItmMae.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
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
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
     */
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
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente";
                    break;
                case INT_TBL_DAT_RUC_CLI:
                    strMsg="Identificación del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente";
                    break;
                case INT_TBL_DAT_FOR_PAG:
                    strMsg="Forma de pago";
                    break;
                case INT_TBL_DAT_SUB:
                    strMsg="Subtotal";
                    break;
                case INT_TBL_DAT_TOT:
                    strMsg="Total";
                    break;
                case INT_TBL_DAT_SUB_TOT_FIL_ITM:
                    strMsg="Subtotal filtrado por item";
                    break;
                case INT_TBL_DAT_EST_DOC:
                    strMsg="Estado del documento";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    

    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLis implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosMovReg.isSelected())
                    cargarMovReg();
                else
                    objTblModDab.removeAllRows();
            }
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
                        txtCodCli.setEditable(true);
                        txtNomCli.setEditable(true);
                        butCli.setEnabled(true);
                        vcoLoc.limpiar();
                        vcoCli.limpiar();
                        vcoVen.limpiar();
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodCli.setText("");
                        txtRucCli.setText("");
                        txtNomCli.setText("");
                        txtCodVen.setText("");
                        txtAliVen.setText("");
                        txtNomVen.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodCli.setEditable(true);
                        txtNomCli.setEditable(true);
                        butCli.setEnabled(true);
                        vcoLoc.limpiar();
                        vcoCli.limpiar();
                        vcoVen.limpiar();
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodCli.setText("");
                        txtRucCli.setText("");
                        txtNomCli.setText("");
                        txtCodVen.setText("");
                        txtAliVen.setText("");
                        txtNomVen.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodCli.setEditable(true);
                            txtNomCli.setEditable(true);
                            butCli.setEnabled(true);
                            vcoLoc.limpiar();
                            vcoCli.limpiar();
                            vcoVen.limpiar();
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                            txtCodCli.setText("");
                            txtRucCli.setText("");
                            txtNomCli.setText("");
                            txtCodVen.setText("");
                            txtAliVen.setText("");
                            txtNomVen.setText("");
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
                        txtCodCli.setEditable(true);
                        txtNomCli.setEditable(true);
                        butCli.setEnabled(true);
                        vcoLoc.limpiar();
                        vcoCli.limpiar();
                        vcoVen.limpiar();
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                        txtCodCli.setText("");
                        txtRucCli.setText("");
                        txtNomCli.setText("");
                        txtCodVen.setText("");
                        txtAliVen.setText("");
                        txtNomVen.setText("");

                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodCli.setEditable(true);
                            txtNomCli.setEditable(true);
                            butCli.setEnabled(true);
                            vcoLoc.limpiar();
                            vcoCli.limpiar();
                            vcoVen.limpiar();
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
                            txtCodCli.setText("");
                            txtRucCli.setText("");
                            txtNomCli.setText("");
                            txtCodVen.setText("");
                            txtAliVen.setText("");
                            txtNomVen.setText("");

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

            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
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



     private boolean mostrarVenConCli(int intTipBus){
        boolean blnRes=true;
        String strConAdd="";
        try{
            strConAdd="";
            if(txtCodEmp.getText().length()>0){
                strConAdd+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
            }
            if(txtCodLoc.getText().length()>0){
                if(!(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))){
                    strConAdd+=" AND a2.co_loc=" + txtCodLoc.getText() + "";
                }
            }
            strConAdd+=" ORDER BY a1.tx_nom";
            vcoCli.setCondicionesSQL(strConAdd);

            
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else{
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    else{
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else{
                            txtNomCli.setText(strNomCli);
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




    private boolean configurarVenConCli(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Ruc");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            //Armar la sentencia SQL.

            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE  a1.st_cli='S' AND a1.st_reg='A'";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbr_cliLoc AS a2 INNER JOIN tbm_cli AS a1";
                strSQL+=" ON a2.co_emp=a1.co_emp AND a2.co_cli=a1.co_cli";
                strSQL+=" WHERE a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
            }

//            System.out.println("clientes: "  + strSQL);

            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Clientes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                System.out.println("ERROR EN ASIENTO: " +strSQL);
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc(){
        boolean blnRes=true;
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
            arlAncCol.add("414");

            strSQL="";
            strSQL+="SELECT a1.co_loc, a1.tx_nom";
            strSQL+=" FROM tbm_loc AS a1";
            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" INNER JOIN tbr_locusr AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
            }
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                strSQL+=" AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
            else
                strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";

            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" AND a2.co_usr IN(" + objParSis.getCodigoUsuario() + ")";
            }


            strSQL+="GROUP BY a1.co_loc, a1.tx_nom";
            System.out.println("local: " + strSQL);
            
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
     private boolean mostrarVenConLoc(int intTipBus){
        boolean blnRes=true;
        String strConAdd="";
        try{
            if(txtCodEmp.getText().length()>0){
                strConAdd+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
            }
            strConAdd+=" ORDER BY a1.tx_nom";
            vcoLoc.setCondicionesSQL(strConAdd);

            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.show();
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText())){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else{
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText())){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else{
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
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



    private boolean cargarCampos(){
        boolean blnRes=true;
        try{
                vecCamDat.clear();

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN ,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK, new Boolean(false));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Cód.Emp");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Código de empresa");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "1");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(false));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Cód.Loc.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Código del local");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "2");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Tip.Doc.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Tipo de Documento");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "4");
                vecCamDat.add(vecCamReg);
                
                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Núm.Doc.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Número de documento");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "6");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Fec.Doc.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Fecha del documento");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "7");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(false));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Cód.Cli.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Código del cliente");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "8");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Cliente");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Nombre de cliente");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "10");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(false));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Forma de pago");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Forma de pago");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "11");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Subtotal");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Subtotal");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "12");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(false));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Total");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Total");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "13");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "SubTot.Fil.Itm.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Subtotal total filtrado por item");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "14");
                vecCamDat.add(vecCamReg);

                vecCamReg=new Vector();
                vecCamReg.add(INT_TBL_DAT_CAM_LIN,"");
                vecCamReg.add(INT_TBL_DAT_CAM_CHK,new Boolean(true));
                vecCamReg.add(INT_TBL_DAT_CAM_HEA, "Est.Doc.");
                vecCamReg.add(INT_TBL_DAT_CAM_DES, "Estado del documento");
                vecCamReg.add(INT_TBL_DAT_CAM_COL_TBL, "15");
                vecCamDat.add(vecCamReg);

                //Asignar vectores al modelo.
                objTblModCam.setData(vecCamDat);
                tblCam.setModel(objTblModCam);

        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
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
    private class ZafMouMotAdaCam extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCam.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_CAM_HEA:
                    strMsg="Título que se presenta en la tabla";
                    break;
                case INT_TBL_DAT_CAM_DES:
                    strMsg="Descripción del campo";
                    break;
                case INT_TBL_DAT_CAM_COL_TBL:
                    strMsg="Tamaño de la columna";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblCam.getTableHeader().setToolTipText(strMsg);
        }
    }


    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblCamMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        try
        {
            intNumFil=objTblModCam.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblCam.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CAM_CHK)
            {
                if (blnMarTodChkTblCam)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModCam.setChecked(true, i, INT_TBL_DAT_CAM_CHK);
                    }
                    blnMarTodChkTblCam=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModCam.setChecked(false, i, INT_TBL_DAT_CAM_CHK);
                    }
                    blnMarTodChkTblCam=true;
                }
            }

        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
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

    /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt){

            String strRutRpt, strNomRpt;
            int i, intNumTotRpt;
            boolean blnRes=true;
            String strSQLRep="", strSQLSubRep="";//, strSQLSubRep2="";
            String strFecHorSer="";
            //Inicializar los parametros que se van a pasar al reporte.
            java.util.Map mapPar=new java.util.HashMap();

            Connection conIns;
            try{
                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try{
                    if(conIns!=null){

                        if(chkTotDocCli.isSelected()){
                            mostrarMsgInf("<HTML>El filtro <FONT COLOR=\"blue\">Totalizar documentos por cliente</FONT><BR> no es tomado en cuenta para el formato de impresión.<BR></HTML>");
                        }
                        lblMsgSis.setText("Cargando el reporte...");
                        ///objRptSis.cargarListadoReportes(conCab);
                        objRptSis.cargarListadoReportes();
                        objRptSis.show();
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
                                        case 20:
                                                strRutRpt=objRptSis.getRutaReporte(i);
                                                strNomRpt=objRptSis.getNombreReporte(i);

                                                strConSQL="";
                                                String strConSQLItm="";

                                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                                    if(txtCodEmp.getText().length()>0)
                                                        strConSQL+=" 			WHERE a1.co_emp=" + txtCodEmp.getText() + "";
                                                    else
                                                        strConSQL+=" 			WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                                                }
                                                else{//si es por empresa
                                                    strConSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                                }

                                                if(txtCodLoc.getText().length()>0)
                                                    strConSQL+=" 			AND a1.co_loc=" + txtCodLoc.getText() + "";


                                                if(txtCodTipDoc.getText().length()>0)
                                                    strConSQL+=" 			AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";

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
                                                if (txtCodVen.getText().length()>0)
                                                    strConSQL+=" AND a1.co_com=" + txtCodVen.getText();

                                                if (txtCodItm.getText().length()>0)
                                                {
                                                    strConSQLItm+=" AND a8.co_itmMae=" + txtCodItmMae.getText();
                                                }
                                                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                                                {
                                                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                                        strConSQLItm+=" AND ((LOWER(a6.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a6.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                                                    else
                                                        strConSQLItm+=" AND ((LOWER(a6.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a6.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                                                }
                                                if (txtCodAltItmTer.getText().length()>0)
                                                {
                                                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                                        strConSQLItm+=getConSQLAdiCamTer("a6.tx_codAlt", txtCodAltItmTer.getText());
                                                    else
                                                        strConSQLItm+=getConSQLAdiCamTer("a6.tx_codAlt", txtCodAltItmTer.getText());
                                                }

                                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                                    if(txtRucCli.getText().length()>0)
                                                        strConSQL+="                   AND a1.tx_ide='" + txtRucCli.getText() + "'";
                                                }
                                                else{
                                                    if(txtCodCli.getText().length()>0)
                                                        strConSQL+="                   AND a1.co_cli=" + txtCodCli.getText() + "";
                                                }

                                                if(cboBln.getSelectedIndex()==0){//debe ser igual a lo que esta en el filtro
                                                    if(cboTipForPag.getSelectedIndex()==0){//todos

                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==1){//0:Negociación
                                                        strConSQL+="               AND a5.ne_tipForPag=0";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==2){//1:Contado
                                                        strConSQL+="               AND a5.ne_tipForPag=1";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==3){//2:Cheque
                                                        strConSQL+="               AND a5.ne_tipForPag=2";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==4){//3:Crédito
                                                        strConSQL+="               AND a5.ne_tipForPag=3";
                                                    }
                                                }
                                                else if(cboBln.getSelectedIndex()==1){//debe ser diferente a lo que esta en el filtro
                                                    if(cboTipForPag.getSelectedIndex()==0){//todos
                                                        strConSQL+="               AND a5.ne_tipForPag NOT IN(0,1,2,3)";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==1){//0:Negociación
                                                        strConSQL+="               AND a5.ne_tipForPag NOT IN(0)";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==2){//1:Contado
                                                        strConSQL+="               AND a5.ne_tipForPag NOT IN(1)";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==3){//2:Cheque
                                                        strConSQL+="               AND a5.ne_tipForPag NOT IN(2)";
                                                    }
                                                    else if(cboTipForPag.getSelectedIndex()==4){//3:Crédito
                                                        strConSQL+="               AND a5.ne_tipForPag NOT IN(3)";
                                                    }
                                                }

                                                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos exepto los eliminados
                                                    strConSQL+=" AND a1.st_reg NOT IN ('E')";
                                                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                                                    strConSQL+=" AND a1.st_reg IN ('A')";
                                                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los activos
                                                    strConSQL+=" AND a1.st_reg IN ('I')";


                                                strSQL="";
                                                strSQL+="SELECT '' AS tx_desCor";
                                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                                    strSQL+=", 0 AS co_cli";
                                                else
                                                    strSQL+=", b1.co_cli";
                                                strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli FROM(";
                                                strSQL+=" 	SELECT c1.* FROM(";
                                                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli";
                                                strSQL+=" 		, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, SUM(a1.nd_sub) AS nd_sub, SUM(a1.nd_tot) AS nd_tot, a1.st_reg";
                                                strSQL+=" 		FROM	(         (       tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                                                strSQL+=" 				      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                                if(objParSis.getCodigoUsuario()==1){
                                                    strSQL+="             INNER JOIN tbr_tipDocPrg AS a4";
                                                    strSQL+="             ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc";
                                                }
                                                else{
                                                    strSQL+="             INNER JOIN tbr_tipDocUsr AS a4";
                                                    strSQL+="             ON a2.co_emp=a4.co_emp and a2.co_loc=a4.co_loc and a2.co_tipdoc=a4.co_tipdoc";
                                                }
                                                strSQL+="			     )";
                                                strSQL+=" 			   LEFT OUTER JOIN tbm_cabForPag AS a5";
                                                strSQL+=" 			   ON a1.co_emp=a5.co_emp AND a1.co_forpag=a5.co_forpag";
                                                strSQL+=" 			)";
                                                strSQL+=" 		 INNER JOIN tbm_cli AS a3";
                                                strSQL+=" 		 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                                    if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                                                        strSQL+=" INNER JOIN tbr_cliLoc AS a9";
                                                        strSQL+=" ON a3.co_emp=a9.co_emp AND a3.co_cli=a9.co_cli";
                                                    }
                                                }
                                                strSQL+=" 		 AND a4.co_mnu=" + objParSis.getCodigoMenu() + "";
                                                strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items, este no lleva el filtro por items.

                                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                                    if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                                                        if(txtCodLoc.getText().length()<=0)
                                                            strSQL+="    AND a9.co_loc=" + objParSis.getCodigoLocal() + "";
                                                    }
                                                }
                                                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc";
                                                strSQL+=" 		 , a1.fe_doc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, a1.st_reg";
                                                strSQL+=" 		 ORDER BY a1.tx_nomCli,a1.ne_numDoc";
                                                strSQL+=" 	 ) AS c1";
                                                if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                                                    strSQL+=" 	 INNER JOIN(";
                                                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                                    strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                                                    strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                                                    strSQL+=" 		INNER JOIN tbm_inv AS a7";
                                                    strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                                                    strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                                                    strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                                                    strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                                                    strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                                                    strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                                    strSQL+=" 	 ) AS c2";
                                                    strSQL+=" 	 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                                                }
                                                strSQL+=" ) AS b1";
                                                if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                                                    strSQL+=" INNER JOIN(";
                                                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg, a6.nd_tot AS nd_subTotFilItm";
                                                    strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                                                    strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                                                    strSQL+=" 		INNER JOIN tbm_inv AS a7";
                                                    strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                                                    strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                                                    strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                                                    strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                                                    strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                                                    strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg,a6.nd_tot";
                                                    strSQL+=" ) AS b2";
                                                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                                                }
                                                strSQL+=" GROUP BY b1.co_cli";
                                                strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli";
                                                strSQL+=" ORDER BY b1.tx_nomCli";
                                                strSQLRep=strSQL;
//                                                System.out.println("strSQLRep: " + strSQLRep);

                                                //SUBREPORTE
                                                strSQL="";

                                                strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.co_doc, b1.ne_numDoc, b1.fe_doc";
                                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                                                    strSQL+=", 0 AS co_cli";
                                                }
                                                else{                                                    
                                                    strSQL+=", b1.co_cli";
                                                }

                                                strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli, b1.tx_desforpag ";
                                                strSQL+=" 		, CASE WHEN b1.tx_natDoc='I' THEN (b1.nd_sub*(-1)) ELSE (b1.nd_sub*(-1)) END AS nd_sub";
                                                strSQL+=" 		, CASE WHEN b1.tx_natDoc='I' THEN (b1.nd_tot*(-1)) ELSE (b1.nd_tot*(-1)) END AS nd_tot";
                                                strSQL+=" 		, b1.st_reg, b1.tx_natDoc";

                                                if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                                                    strSQL+=" , CASE WHEN b1.tx_natDoc='I' THEN (SUM(b2.nd_subTotFilItm*(-1) )) ELSE (SUM(b2.nd_subTotFilItm)*(-1)) END ";
                                                    strSQL+=" AS nd_subTotFilItm";
                                                    strSQL+=" FROM(";
                                                }
                                                else{
                                                    strSQL+=" , CASE WHEN b1.tx_natDoc='I' THEN  (b1.nd_sub*(-1)) ELSE (b1.nd_sub*(-1)) END  ";
                                                    strSQL+=" AS nd_subTotFilItm ";
                                                    strSQL+=" FROM(";
                                                }
                                                strSQL+=" 	SELECT c1.* FROM(";
                                                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli";
                                                strSQL+=" 		, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, SUM(a1.nd_sub) AS nd_sub, SUM(a1.nd_tot) AS nd_tot, a1.st_reg, a2.tx_natDoc";
                                                strSQL+=" 		FROM	(         (       tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a2";
                                                strSQL+=" 				      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                                                if(objParSis.getCodigoUsuario()==1){
                                                    strSQL+="             INNER JOIN tbr_tipDocPrg AS a4";
                                                    strSQL+="             ON a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc";
                                                }
                                                else{
                                                    strSQL+="             INNER JOIN tbr_tipDocUsr AS a4";
                                                    strSQL+="             ON a2.co_emp=a4.co_emp and a2.co_loc=a4.co_loc and a2.co_tipdoc=a4.co_tipdoc";
                                                }
                                                strSQL+="			     )";
                                                strSQL+=" 			   LEFT OUTER JOIN tbm_cabForPag AS a5";
                                                strSQL+=" 			   ON a1.co_emp=a5.co_emp AND a1.co_forpag=a5.co_forpag";
                                                strSQL+=" 			)";
                                                strSQL+=" 		 INNER JOIN tbm_cli AS a3";
                                                strSQL+=" 		 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli";
                                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                                    if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                                                        strSQL+=" INNER JOIN tbr_cliLoc AS a9";
                                                        strSQL+=" ON a3.co_emp=a9.co_emp AND a3.co_cli=a9.co_cli";
                                                    }
                                                }
                                                strSQL+=" 		 AND a4.co_mnu=" + objParSis.getCodigoMenu() + "";
                                                strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items, este no lleva el filtro por items.

                                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                                                    if(!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                                                        if(txtCodLoc.getText().length()<=0)
                                                            strSQL+="    AND a9.co_loc=" + objParSis.getCodigoLocal() + "";
                                                    }
                                                }
                                                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a1.co_doc, a1.ne_numDoc";
                                                strSQL+=" 		 , a1.fe_doc, a1.co_cli, a1.tx_ruc, a1.tx_nomCli, a1.tx_desforpag, a1.st_reg, a2.tx_natDoc";
                                                strSQL+=" 		 ORDER BY a1.tx_nomCli,a1.ne_numDoc";
                                                strSQL+=" 	 ) AS c1";
                                                if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                                                    strSQL+=" 	 INNER JOIN(";
                                                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                                    strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                                                    strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                                                    strSQL+=" 		INNER JOIN tbm_inv AS a7";
                                                    strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                                                    strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                                                    strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                                                    strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                                                    strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                                                    strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                                                    strSQL+=" 	 ) AS c2";
                                                    strSQL+=" 	 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                                                }
                                                strSQL+=" ) AS b1";
                                                if(   (txtCodItmMae.getText().length()>0) || (txtCodAltDes.getText().length()>0) || (txtCodAltHas.getText().length()>0) || (txtCodAltItmTer.getText().length()>0)    ){
                                                    strSQL+=" INNER JOIN(";
                                                    strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg, a6.nd_tot AS nd_subTotFilItm";
                                                    strSQL+=" 		FROM tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                                                    strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                                                    strSQL+=" 		INNER JOIN tbm_inv AS a7";
                                                    strSQL+=" 		ON a6.co_emp=a7.co_emp AND a6.co_itm=a7.co_itm";
                                                    strSQL+=" 		INNER JOIN tbm_equInv AS a8";
                                                    strSQL+=" 		ON a7.co_emp=a8.co_emp AND a7.co_itm=a8.co_itm";
                                                    strSQL+=strConSQL;//filtros varios menos los que tengan q ver con items
                                                    strSQL+=strConSQLItm;//los filtros q tengan q ver con items
                                                    strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a6.co_reg,a6.nd_tot";
                                                    strSQL+=" ) AS b2";
                                                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                                                }
                                                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                                                    strSQL+=" WHERE b1.tx_ruc='$P!{tx_ruc}' AND b1.tx_nomCli='$P!{tx_nomCli}'";//b1.tx_ruc='0991332537001' --$P!{tx_ruc}
                                                else
                                                    strSQL+=" WHERE b1.co_cli=$P!{co_cli}";//


                                                //strSQL+=" WHERE CASE WHEN b1.co_cli>0 THEN b1.co_cli=$P!{co_cli} ELSE b1.tx_ruc='$P!{tx_ruc}' END";


                                                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.co_cli";
                                                strSQL+=" 		, b1.tx_ruc, b1.tx_nomCli, b1.tx_desforpag, b1.nd_sub, b1.nd_tot, b1.st_reg, b1.tx_natDoc";
                                                strSQL+=" ORDER BY b1.fe_doc";
                                                strSQLSubRep=strSQL;
//                                                System.out.println("SUBREPORTE2: " + strSQLSubRep);

                                                mapPar.put("strSQLRep", strSQLRep);
                                                mapPar.put("strSQLSubRep", strSQLSubRep);
                                                mapPar.put("tx_nomEmp", objParSis.getNombreEmpresa());
                                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                                mapPar.put("strCamAudRpt",  " Impreso Fecha:" + objParSis.getNombreUsuario() + "      Hora:" + strFecHorSer + "      " + this.getClass().getName() + "      " +  strNomRpt + "");
                                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                        break;
                                        default:
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    conIns.close();
                    conIns=null;
                }
                catch (Exception e)
                {
                    blnRes=false;
                    objUti.mostrarMsgErr_F1(this, e);
                }
            }
            catch(SQLException ex)
            {
//                System.out.println("Error al conectarse a la base");
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, ex);
            }
            return blnRes;
    }



    
}