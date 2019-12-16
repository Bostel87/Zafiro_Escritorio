/*
 * ZafCom90.java
 *
 * Created on 05 de enero de 2006, 10:10 PM
 */
package Compras.ZafCom90;
import Librerias.ZafHisTblBasDat.ZafHisTblBasDat;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.math.BigInteger;
import javax.swing.JScrollBar;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom90 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_DOS=4;                 //Código del item (Alterno2).
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_CHK_SER=6;                     //Nombre del item.
    static final int INT_TBL_DAT_COD_UNI=7;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_DAT_DEC_UNI=8;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_BUT_UNI=9;                     //Botón de consulta.
    static final int INT_TBL_DAT_PES_KGR=10;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_PES_KGR_AUX=11;                    //Peso en Kilogramos.
    static final int INT_TBL_DAT_STK_CON=12;                     //Stock consolidado.
    
    //Configurar columnas del JTable Fijo
    static final int INT_TBL_FIX_LIN=0;                         //Línea
    static final int INT_TBL_FIX_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_FIX_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_FIX_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_FIX_COD_ALT_DOS=4;                 //Código del item (Alterno2).
    static final int INT_TBL_FIX_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_FIX_CHK_SER=6;                     //Nombre del item.
    static final int INT_TBL_FIX_COD_UNI=7;                     //Código de la unidad de medida (Sistema).
    static final int INT_TBL_FIX_DEC_UNI=8;                     //Descripción corta de la unidad de medida.
    static final int INT_TBL_FIX_BUT_UNI=9;                     //Botón de consulta.
    static final int INT_TBL_FIX_PES_KGR=10;                    //Peso en Kilogramos.
    static final int INT_TBL_FIX_PES_KGR_AUX=11;                //Peso en Kilogramos.
    static final int INT_TBL_FIX_STK_CON=12;                    //Stock consolidado.
    
    static final int INT_TBL_DAT_NUM_TOT_CES=12;                //Número total de columnas estáticas.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafHisTblBasDat objHisTblBasDat;
    private ZafTblFilCab objTblFilCab, objTblFilCabFix;
    private ZafTblMod objTblMod, objTblModFix;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblFix;                                            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblDer, objTblCelRenLblDerFix;                                      //Render: Presentar JLabel en JTable (Derecha).
    private ZafTblCelRenLbl objTblCelRenLblNum, objTblCelRenLblNumFix;                                      //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblColDinIngImp, objTblCelRenLblColDinIngImpFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinCosUni, objTblCelRenLblColDinCosUniFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPedEmb, objTblCelRenLblColDinPedEmbFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinNotPed, objTblCelRenLblColDinNotPedFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinMarNotPed, objTblCelRenLblColDinMarNotPedFix;              //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPreReaMar, objTblCelRenLblColDinPreReaMarFix;              //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinFac, objTblCelRenLblColDinFacFix;                          //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinMarFac, objTblCelRenLblColDinMarFacFix;                    //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinPre, objTblCelRenLblColDinPreFix;                          //Render: Presentar JLabel en JTable (columnas dinámicas)
    private ZafTblCelRenLbl objTblCelRenLblColDinCanMarPreLisNew, objTblCelRenLblColDinCanMarPreLisNewFix;  //Render: Presentar JLabel en JTable (columnas dinámicas)
    
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButFix;                        //Render: Presentar JButton en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkFix;                        //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt, objTblCelEdiTxtFix, objTblCelEdiTxtFixItm, objTblCelEdiTxtFixUniMed;    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPreVta;                                      //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtFacCos;                                      //Editor: JTextField en celda.-
    private ZafTblCelEdiTxt objTblCelEdiTxtMarFacCos;                                   //Editor: JTextField en celda.
    //private ZafTblCelEdiTxt objTblCelEdiTxtMarUti;                                    //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPesKgr, objTblCelEdiTxtPesKgrFix;                                      //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtPreObjKil;                                   //Editor: JTextField en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxtCanMarPreLisNew;                             //Editor: JTextField en celda.
//    private ZafTblCelEdiTxt objTblCelEdiTxtFacCosUni;                                 //Editor: JTextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoUniMed, objTblCelEdiButVcoUniMedFix;   //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoUniMed, objTblCelEdiTxtVcoUniMedFix;   //Editor: JTextField de consulta en celda.
    private ZafMouMotAda objMouMotAda, objMouMotAdaFix;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuFix;         //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus, objTblBusFix;                                //Editor de búsqueda.
    //private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenCon vcoUniMed, vcoUniMedFix;                                //Ventana de consulta "Unidad de medida".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecDatFix, vecCab, vecReg, vecRegFix;
    private Vector vecAux, vecAuxFix;
    private boolean blnCon;                                    //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm;                       //Contenido del campo al obtener el foco.
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafImp objImp;
        
    private int intNumColAddIngImp, intNumColIniIngImp, intNumColFinIngImp;
    private int intNumColAddCosUni, intNumColIniCosUni, intNumColFinCosUni;
    private int intNumColAddPedEmb, intNumColIniPedEmb, intNumColFinPedEmb;
    private int intNumColAddNotPed, intNumColIniNotPed, intNumColFinNotPed;
    private int intNumColAddMarNotPed, intNumColIniMarNotPed, intNumColFinMarNotPed;
    private int intNumColAddPreReaMar, intNumColIniPreReaMar, intNumColFinPreReaMar;    
    private int intNumColAddFac, intNumColIniFac, intNumColFinFac;
    private int intNumColAddMarFac, intNumColIniMarFac, intNumColFinMarFac;
    private int intNumColAddPre, intNumColIniPre, intNumColFinPre;
    private int intNumColAddCanMarPreLisNew, intNumColIniCanMarPreLisNew, intNumColFinCanMarPreLisNew;
    
    private int intNumColEst;
    
    //Ingresos por Importación: Pedidos llegados
    private ArrayList arlRegIngImp, arlDatIngImp;    
    private final int INT_ARL_ING_IMP_COD_EMP=0;
    private final int INT_ARL_ING_IMP_COD_LOC=1;
    private final int INT_ARL_ING_IMP_COD_TIP_DOC=2;
    private final int INT_ARL_ING_IMP_COD_DOC=3;
    private final int INT_ARL_ING_IMP_NUM=4;
    private final int INT_ARL_ING_IMP_COL=5;
    private final int INT_ARL_ING_IMP_EST_COL=6;
    private final int INT_ARL_ING_IMP_FEC=7;
    private final int INT_ARL_ING_IMP_COD_EMP_PED_EMB=8;
    private final int INT_ARL_ING_IMP_COD_LOC_PED_EMB=9;
    private final int INT_ARL_ING_IMP_COD_TIP_DOC_PED_EMB=10;
    private final int INT_ARL_ING_IMP_COD_DOC_PED_EMB=11;
    
    //Pedido embarcado: Pedidos en tránsito
    private ArrayList arlRegPedEmb, arlDatPedEmb;
    
    private final int INT_ARL_PED_EMB_COD_EMP=0;
    private final int INT_ARL_PED_EMB_COD_LOC=1;
    private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
    private final int INT_ARL_PED_EMB_COD_DOC=3;
    private final int INT_ARL_PED_EMB_NUM=4;
    private final int INT_ARL_PED_EMB_COL=5;
    private final int INT_ARL_PED_EMB_EST_COL=6;
    private final int INT_ARL_PED_EMB_FEC=7;
//    private final int INT_ARL_PED_EMB_COD_EMP_ING_IMP=8;
//    private final int INT_ARL_PED_EMB_COD_LOC_ING_IMP=9;
//    private final int INT_ARL_PED_EMB_COD_TIP_DOC_ING_IMP=10;
//    private final int INT_ARL_PED_EMB_COD_DOC_ING_IMP=11;
    
    //Nota de pedido: Pedidos en tránsito
    private ArrayList arlRegNotPed, arlDatNotPed;    
    private final int INT_ARL_NOT_PED_COD_EMP=0;
    private final int INT_ARL_NOT_PED_COD_LOC=1;
    private final int INT_ARL_NOT_PED_COD_TIP_DOC=2;
    private final int INT_ARL_NOT_PED_COD_DOC=3;
    private final int INT_ARL_NOT_PED_NUM=4;
    private final int INT_ARL_NOT_PED_COL=5;
    private final int INT_ARL_NOT_PED_EST_COL=6;
    private final int INT_ARL_NOT_PED_FEC=7;
    private final int INT_ARL_NOT_PED_COD_EMP_PED_EMB=8;
    private final int INT_ARL_NOT_PED_COD_LOC_PED_EMB=9;
    private final int INT_ARL_NOT_PED_COD_TIP_DOC_PED_EMB=10;
    private final int INT_ARL_NOT_PED_COD_DOC_PED_EMB=11;
    
    private ArrayList arlRegExpExc, arlDatExpExc;
    private final int INT_ARL_EXP_EXC_DAT_LIN=0;
    private final int INT_ARL_EXP_EXC_DAT_COD_MAE=1;
    private final int INT_ARL_EXP_EXC_DAT_COD_SIS=2;
    private final int INT_ARL_EXP_EXC_DAT_COD_ALT=3;
    private final int INT_ARL_EXP_EXC_DAT_COD_ALT_DOS=4;
    private final int INT_ARL_EXP_EXC_DAT_NOM_ITM=5;
    private final int INT_ARL_EXP_EXC_DAT_CHK_SER=6;
    private final int INT_ARL_EXP_EXC_DAT_COD_UNI=7;
    private final int INT_ARL_EXP_EXC_DAT_DEC_UNI=8;
    private final int INT_ARL_EXP_EXC_DAT_BUT_UNI=9;
    private final int INT_ARL_EXP_EXC_DAT_PES_KGR=10;
    private final int INT_ARL_EXP_EXC_DAT_PES_KGR_AUX=11;
    private final int INT_ARL_EXP_EXC_DAT_STK_CON=12;
    
    private int intCodLocGrp=1;
    
    private BigDecimal bdePorIva=new BigDecimal("0");
    private JScrollBar barDatVer, barFixVer, barDatHor, barFixHor;
    
    private String strVersion=" v0.1.18.7";
   
    /** Crea una nueva instancia de la clase ZafCom90. */
    public ZafCom90(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatIngImp=new ArrayList();
            arlDatPedEmb=new ArrayList();
            arlDatNotPed=new ArrayList();
            arlDatExpExc=new ArrayList();
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        chkSolStk = new javax.swing.JCheckBox();
        chkMosValKil = new javax.swing.JCheckBox();
        chkMosValFac = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        panFix = new javax.swing.JPanel();
        spnFix = new javax.swing.JScrollPane();
        tblFix = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panMov = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
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
        butPreVen = new javax.swing.JButton();
        butExpExc = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 44, 120, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 44, 56, 20);

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
        txtCodAlt.setBounds(144, 44, 92, 20);

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
        txtNomItm.setBounds(236, 44, 424, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(660, 44, 20, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(24, 70, 328, 52);

        chkSolStk.setText("Mostrar sólo los items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(24, 130, 320, 16);

        chkMosValKil.setText("Mostrar valores por kilos");
        chkMosValKil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosValKilActionPerformed(evt);
            }
        });
        panFil.add(chkMosValKil);
        chkMosValKil.setBounds(24, 166, 320, 16);

        chkMosValFac.setText("Mostrar valores con factor");
        chkMosValFac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosValFacActionPerformed(evt);
            }
        });
        panFil.add(chkMosValFac);
        chkMosValFac.setBounds(24, 148, 320, 16);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Nota"));
        jPanel1.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 0, 0));
        jLabel1.setText("Reporte no presenta stock de Bodegas:   Faltantes y Dañados");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(12, 20, 420, 18);

        panFil.add(jPanel1);
        jPanel1.setBounds(10, 260, 450, 50);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,I\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panCodAltItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 20, 190, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(356, 70, 306, 52);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.GridLayout(1, 0));

        jSplitPane1.setDividerLocation(438);
        jSplitPane1.setDividerSize(3);

        panFix.setLayout(new java.awt.BorderLayout());

        tblFix.setModel(new javax.swing.table.DefaultTableModel(
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
        spnFix.setViewportView(tblFix);

        panFix.add(spnFix, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(panFix);

        panMov.setLayout(new java.awt.BorderLayout());

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

        panMov.add(spnDat, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(panMov);

        panRpt.add(jSplitPane1);

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
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butPreVen.setText("Pre.Vta.1");
        butPreVen.setToolTipText("Asigna el valor de \"Cos.Uni.Fac.\" a \"Pre.Vta.1.\" para las filas seleccionadas.");
        butPreVen.setPreferredSize(new java.awt.Dimension(92, 25));
        butPreVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPreVenActionPerformed(evt);
            }
        });
        panBot.add(butPreVen);

        butExpExc.setText("Exportar");
        butExpExc.setPreferredSize(new java.awt.Dimension(92, 25));
        butExpExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpExcActionPerformed(evt);
            }
        });
        panBot.add(butExpExc);

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

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
        }
    }//GEN-LAST:event_txtCodAltDesFocusLost

    private void txtCodAltHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusGained
        txtCodAltHas.selectAll();
    }//GEN-LAST:event_txtCodAltHasFocusGained

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if(isCamVal()){
                    if (actualizarDet())
                        mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                    else
                        mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                }
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void chkSolStkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkSolStkActionPerformed
        if (chkSolStk.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkSolStkActionPerformed

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
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            chkSolStk.setSelected(false);
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
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
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butPreVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPreVenActionPerformed
        int intFilSel[], i;
        if (mostrarMsgCon("<HTML>¿Está seguro que desear asignar <FONT COLOR=\"blue\">Cos.Uni.Fac.</FONT> a <FONT COLOR=\"blue\">Pre.Vta.1.</FONT>?<BR>Sólo se asignarán los valores en las filas seleccionadas.")==0)
        {
            intFilSel=tblDat.getSelectedRows();
            for (i=0; i<intFilSel.length; i++)
            {
                if (objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1)))!=0)
                    objTblMod.setValueAt(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1)), intFilSel[i], intNumColIniPreReaMar);
            }
            //Seleccionar la fila donde se encontró el valor buscado.
            tblDat.setRowSelectionInterval(intFilSel[0], intFilSel[intFilSel.length-1]);
            //Ubicar el foco en la fila seleccionada.
            tblDat.changeSelection(intFilSel[0], intNumColIniPreReaMar, true, true);
            tblDat.requestFocus();
        }
    }//GEN-LAST:event_butPreVenActionPerformed

    private void chkMosValKilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosValKilActionPerformed
        // TODO add your handling code here:
        ocultaColumnasPrecios();
        ocultaColumnasMargenPrecio();
        calcularValoresKilos(1);
        calcularMarNotPed();
        calcularMarPedEmb();
    }//GEN-LAST:event_chkMosValKilActionPerformed

    private void chkMosValFacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosValFacActionPerformed
        // TODO add your handling code here:
        ocultaColumnasFactor();
    }//GEN-LAST:event_chkMosValFacActionPerformed

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
        // TODO add your handling code here:
        java.io.File filExc;
        Process pro;
        BigDecimal bdeCanRepValExpExc=new BigDecimal("0");
        try{

            mostrarMsgInf("<HTML>Recuerde guardar el archivo con otro nombre y en otro directorio.<BR>Si no realiza este paso, al volver a exportar el archivo se sobreescribirá y Ud. perderá todos los cambios efectuados.</HTML>");

            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            if(System.getProperty("os.name").equals("Linux")){
                filExc=new java.io.File("/tmp/ZafCom90.xls");
            }
            else{
                filExc=new java.io.File("C:\\Zafiro\\Reportes\\Compras\\ZafCom90\\ZafCom90.xls");
            }
            String strNomHoj="Hoja1";

            arlDatExpExc.clear();
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                //estaticas
                arlRegExpExc=new ArrayList();
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_LIN, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_MAE, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_MAE)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_MAE).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_SIS, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_SIS).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_ALT, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_ALT_DOS, (objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_DOS).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_NOM_ITM, (objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_CHK_SER, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_COD_UNI, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_DEC_UNI, (objTblMod.getValueAt(i, INT_TBL_DAT_DEC_UNI)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_DEC_UNI).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_BUT_UNI, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_PES_KGR, (objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_PES_KGR_AUX, "");
                arlRegExpExc.add(INT_ARL_EXP_EXC_DAT_STK_CON, (objTblMod.getValueAt(i, INT_TBL_DAT_STK_CON)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_STK_CON).toString()));
                
                //dinamicas
                for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                    //System.out.println("Dato erroneo: " + (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                    arlRegExpExc.add(j, (objTblMod.getValueAt(i, j)==null?"":objTblMod.getValueAt(i, j).toString()));
                }
                arlDatExpExc.add(arlRegExpExc);                    
            }
            System.out.println("arlDatExpExc: " + arlDatExpExc.toString());

            ZafCom90_01 objCom90_01= new ZafCom90_01(arlDatExpExc, filExc, strNomHoj
                    , intNumColIniIngImp, intNumColFinIngImp
                    , intNumColIniCosUni, intNumColFinCosUni
                    , intNumColIniPedEmb, intNumColFinPedEmb
                    , intNumColIniNotPed, intNumColFinNotPed
                    , intNumColIniMarNotPed, intNumColFinMarNotPed
                    , intNumColIniPreReaMar, intNumColFinPreReaMar
                    , intNumColIniFac, intNumColFinFac
                    , intNumColIniMarFac, intNumColFinMarFac
                    , intNumColIniPre, intNumColFinPre
                    , intNumColIniCanMarPreLisNew, intNumColFinCanMarPreLisNew
                    , arlDatIngImp, arlDatPedEmb, arlDatNotPed
                    , chkMosValKil.isSelected()?true:false
                    , chkMosValFac.isSelected()?true:false
            );
            
            
            
            
            
            
            

            if(objCom90_01.export()){
                mostrarMsgInf("El archivo se cargó correctamente.");

                if(System.getProperty("os.name").equals("Linux")){
                    pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCom90.xls");
                    System.out.println("LINUX: " + pro.toString());
                }
                else{
                    pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Compras/ZafCom90/ZafCom90.xls");
                    System.out.println("WINDOWS: " + pro.toString());
                }

            }
            else
                mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpExcActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExpExc;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPreVen;
    private javax.swing.JCheckBox chkMosValFac;
    private javax.swing.JCheckBox chkMosValKil;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFix;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panMov;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFix;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblFix;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        Double dblValFacCal=0.00, dblValMinFacCal=0.01, dblValMaxFacCal=1.00, dblValIncFacCal=0.01;
        
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objHisTblBasDat=new ZafHisTblBasDat();
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            
            //PORCENTAJE IVA 
            bdePorIva=objUti.redondearBigDecimal(((objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))).divide(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            
            //Configurar las ZafVenCon.
            configurarVenConItm();
            configurarVenConUniMed();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblFix();
            
            butCon.setVisible(true);
            butGua.setVisible(true);
            butPreVen.setVisible(true);
            butCer.setVisible(true);
            if(objParSis.getCodigoUsuario()==1){
            }
            else{
                if(!objPerUsr.isOpcionEnabled(3865))
                    butCon.setVisible(false);
                if(!objPerUsr.isOpcionEnabled(3866))
                    butGua.setVisible(false);
                if(!objPerUsr.isOpcionEnabled(3867))
                    butPreVen.setVisible(false);
                if(!objPerUsr.isOpcionEnabled(3868))
                    butCer.setVisible(false);
                
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
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.Itm.Dos");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_CHK_SER,"Ser.");
            vecCab.add(INT_TBL_DAT_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_BUT_UNI,"");
            vecCab.add(INT_TBL_DAT_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_DAT_PES_KGR_AUX,"Peso(Kg)Aux.");
            vecCab.add(INT_TBL_DAT_STK_CON,"Stk.");
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                            }
                        }
                        else if (tblDat.isColumnSelected((intNumColIniFac+1)))
                        {
                            calcularCosUniFac();
                            calcularMarFac();
                        }
                        else if (tblDat.isColumnSelected(intNumColIniPreReaMar))
                        {
                            calcularMarUtiCos();
                            calcularPreVtaReaKil();
                        }
                    }
                    else if (objTblPopMnu.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblDat.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblDat.isColumnSelected(INT_TBL_DAT_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_COD_UNI);
                                objTblMod.setValueAt(null, intFilSel[i], INT_TBL_DAT_DEC_UNI);
                            }
                        }

                        else if (tblDat.isColumnSelected(intNumColIniPreReaMar))
                        {
                            calcularMarUtiCos();
                            calcularPreVtaReaKil();
                        }
                        
                        //nuevo
                        else if(tblDat.isColumnSelected(intNumColIniFac)){//se pego en columna "factor de costo"
                            calcularCosUniFacTbl();
                            calcularMarFacTbl();
                        }                        
                        else if(tblDat.isColumnSelected(intNumColIniMarFac)){//se pego en columna "margen con factor"
                            calcularCostoUnitarioFactor_MargenTbl();
                        }                       
                        
                        else if(tblDat.isColumnSelected(intNumColIniPre)){//se pego en columna "precio objetivo kilo incluido IVA - Factor precio en kilos"
                            calcularPreLisCalConsulta();
                            calcularMarPreLisCal();  
                        }
                        else if(tblDat.isColumnSelected(intNumColIniCanMarPreLisNew)){//se pego en columna "Margen precio lista calculado"
                            calcularPrecioListaCalculado_MargenTbl();
                        }
                        
                        
                        
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(36);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR_AUX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_SER, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_KGR_AUX, tblDat);
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_LIN, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ALT_DOS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEC_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_UNI, tblDat);
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PES_KGR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK_CON, tblDat);
            
            if (objParSis.getCodigoUsuario()!=1)
            {
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (!objPerUsr.isOpcionEnabled(2990))
                {
                    butPreVen.setVisible(false);
                }
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLbl.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLbl.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                    

                    
                }
            });
            
            objTblCelRenLblDer=new ZafTblCelRenLbl();
            objTblCelRenLblDer.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLblDer);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLblDer);
            objTblCelRenLblDer.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDer.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblDer.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblDer.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_STK_CON).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR_AUX).setCellRenderer(objTblCelRenLblNum);
            objTblCelRenLblNum.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), (intNumColIniPreReaMar+3)))));

                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblNum.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }                    
                    
                    //nuevo color
                    if(chkMosValKil.isSelected()){
                        BigDecimal bgdPesKil=(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_PES_KGR)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNum.getRowRender(), INT_TBL_DAT_PES_KGR))));

                        if(objTblCelRenLblNum.getColumnRender()==INT_TBL_DAT_PES_KGR){
                            if (bgdPesKil==null || bgdPesKil.compareTo(BigDecimal.ZERO)==0)
                                objTblCelRenLblNum.setBackground(java.awt.Color.GREEN);
                            else{
                                if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                                {
                                    objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                }
                                else{
                                    if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                                        objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                    }
                                    else{
                                        if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                            objTblCelRenLblNum.setBackground(new Color(255,151,151));
                                        }
                                        else{
                                            objTblCelRenLblNum.setBackground(javax.swing.UIManager.getColor("Table.background"));
                                        }                                    
                                    }
                                }
                            }
                        }
                    }
                    
                }
            });
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_SER).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChk.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenChk.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenChk.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_DAT_COD_UNI;
            intColTbl[1]=INT_TBL_DAT_DEC_UNI;
            objTblCelEdiTxtVcoUniMed=new ZafTblCelEdiTxtVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMed);
            
            objTblCelEdiButVcoUniMed=new ZafTblCelEdiButVco(tblDat, vcoUniMed, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMed);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellEditor(objTblCelEdiTxt);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt=null;
            
            objTblCelEdiTxtPesKgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgr);
            //objTblCelEdiTxtPesKgr=null;
            objTblCelEdiTxtPesKgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularReversionValoresKilos();
                    
                    calcularPreVtaReaKil();
                    calcularPreLisCal();
                    calcularMarPreLisCal_row();
                }
            });
            
            
            
            
            
//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            intNumColEst=objTblMod.getColumnCount();
            
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
    private boolean configurarTblFix()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatFix=new Vector();    //Almacena los datos
            vecCab=new Vector(13);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_FIX_LIN,"");
            vecCab.add(INT_TBL_FIX_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_FIX_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_FIX_COD_ALT,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_FIX_COD_ALT_DOS,"Cód.Alt.Itm.Dos");
            vecCab.add(INT_TBL_FIX_NOM_ITM,"Item");
            vecCab.add(INT_TBL_FIX_CHK_SER,"Ser.");
            vecCab.add(INT_TBL_FIX_COD_UNI,"Cód.Uni.");
            vecCab.add(INT_TBL_FIX_DEC_UNI,"Uni.Med.");
            vecCab.add(INT_TBL_FIX_BUT_UNI,"");
            vecCab.add(INT_TBL_FIX_PES_KGR,"Peso(Kg)");
            vecCab.add(INT_TBL_FIX_PES_KGR_AUX,"Peso(Kg)Aux.");
            vecCab.add(INT_TBL_FIX_STK_CON,"Stk.");
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModFix=new ZafTblMod();
            objTblModFix.setHeader(vecCab);
            tblFix.setModel(objTblModFix);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblModFix.setColumnDataType(INT_TBL_FIX_PES_KGR, ZafTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            tblFix.setRowSelectionAllowed(true);
            tblFix.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); //Eddye: UNA VEZ CORREGIDO "PEGAR" CAMBIAR A MULTIPLE_INTERVAL_SELECTION.
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnuFix=new ZafTblPopMnu(tblFix);
            objTblPopMnuFix.setPegarEnabled(true);
            objTblPopMnuFix.setBorrarContenidoEnabled(true);
            objTblPopMnuFix.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnuFix.isClickBorrarContenido())
                    {
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblFix.isColumnSelected(INT_TBL_FIX_DEC_UNI))
                        {
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_COD_UNI);
                            }
                        }
                    }
                    else if (objTblPopMnuFix.isClickPegar())
                    {
                        //Eddye: Hay que mejorar esto porque no está validando correctamente cuando se pega la unidad de medida.
                        int intFilSel[], i;
                        intFilSel=tblFix.getSelectedRows();
                        //Al borrar lo que se encuentra en INT_TBL_DAT_DEC_UNI se debe borrar también lo que se encuentra en INT_TBL_DAT_COD_UNI.
                        if (tblFix.isColumnSelected(INT_TBL_FIX_DEC_UNI)){
                            for (i=0; i<intFilSel.length; i++)
                            {
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_COD_UNI);
                                objTblModFix.setValueAt(null, intFilSel[i], INT_TBL_FIX_DEC_UNI);
                            }
                        }
                    }
                }
            });
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblFix.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblFix.getColumnModel();
            tcmAux.getColumn(INT_TBL_FIX_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setPreferredWidth(36);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_FIX_CHK_SER).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setPreferredWidth(47);
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR_AUX).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FIX_STK_CON).setPreferredWidth(60);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFix.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_MAE, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_SIS, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_CHK_SER, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_COD_UNI, tblFix);
            objTblModFix.addSystemHiddenColumn(INT_TBL_FIX_PES_KGR_AUX, tblFix);
            if (objParSis.getCodigoUsuario()!=1){
                //2990: Ficha "Reporte": Tabla->Mostrar "Costo unitario".
                if (!objPerUsr.isOpcionEnabled(2990)){
                    butPreVen.setVisible(false);
                }
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaFix=new ZafMouMotAda();
            tblFix.getTableHeader().addMouseMotionListener(objMouMotAdaFix);
            //Configurar JTable: Editor de búsqueda.
            objTblBusFix=new ZafTblBus(tblFix);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabFix=new ZafTblFilCab(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_LIN).setCellRenderer(objTblFilCabFix);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblFix=new ZafTblCelRenLbl();
            objTblCelRenLblFix.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT_DOS).setCellRenderer(objTblCelRenLblFix);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellRenderer(objTblCelRenLblFix);
//            objTblCelRenLblFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), (intNumColIniPreReaMar+3)))));
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }
//                }
//            });
            
            objTblCelRenLblDerFix=new ZafTblCelRenLbl();
            objTblCelRenLblDerFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_FIX_COD_MAE).setCellRenderer(objTblCelRenLblDerFix);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellRenderer(objTblCelRenLblDerFix);
//            objTblCelRenLblDerFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblDerFix.getRowRender(), (intNumColIniPreReaMar+3)))));
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblDerFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblDerFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }
//                }
//            });
            
            objTblCelRenLblNumFix=new ZafTblCelRenLbl();
            objTblCelRenLblNumFix.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNumFix.setTipoFormato(objTblCelRenLblNumFix.INT_FOR_NUM);
            objTblCelRenLblNumFix.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_FIX_STK_CON).setCellRenderer(objTblCelRenLblNumFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellRenderer(objTblCelRenLblNumFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR_AUX).setCellRenderer(objTblCelRenLblNumFix);
//            objTblCelRenLblNumFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
//                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
//                    BigDecimal bgdMarUti, bgdMarUtiCos;
//                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+2)))));
//                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), (intNumColIniPreReaMar+3)))));
//
//                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                    {
//                        objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                    }
//                    else{
//                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                            objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                        }
//                        else{
//                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                objTblCelRenLblNumFix.setBackground(new Color(255,151,151));
//                            }
//                            else{
//                                objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                            }    
//                        }
//                    }                    
//                    
//                    //nuevo color
//                    if(chkMosValKil.isSelected()){
//                        BigDecimal bgdPesKil=(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), INT_TBL_FIX_PES_KGR)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblNumFix.getRowRender(), INT_TBL_FIX_PES_KGR))));
//
//                        if(objTblCelRenLblNumFix.getColumnRender()==INT_TBL_FIX_PES_KGR){
//                            if (bgdPesKil==null || bgdPesKil.compareTo(BigDecimal.ZERO)==0)
//                                objTblCelRenLblNumFix.setBackground(java.awt.Color.GREEN);
//                            else{
//                                if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
//                                {
//                                    objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                }
//                                else{
//                                    if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
//                                        objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                    }
//                                    else{
//                                        if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
//                                            objTblCelRenLblNumFix.setBackground(new Color(255,151,151));
//                                        }
//                                        else{
//                                            objTblCelRenLblNumFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
//                                        }                                    
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    
//                }
//            });
            
            objTblCelRenButFix=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellRenderer(objTblCelRenButFix);
            objTblCelRenButFix=null;
            
            objTblCelRenChkFix=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_FIX_CHK_SER).setCellRenderer(objTblCelRenChkFix);
            objTblCelRenChkFix.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenChkFix.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenChkFix.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenChkFix.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[2];
            intColVen[0]=1;
            intColVen[1]=2;
            int intColTbl[]=new int[2];
            intColTbl[0]=INT_TBL_FIX_COD_UNI;
            intColTbl[1]=INT_TBL_FIX_DEC_UNI;
            objTblCelEdiTxtVcoUniMedFix=new ZafTblCelEdiTxtVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtVcoUniMedFix);
            
            objTblCelEdiButVcoUniMedFix=new ZafTblCelEdiButVco(tblFix, vcoUniMedFix, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_FIX_BUT_UNI).setCellEditor(objTblCelEdiButVcoUniMedFix);
            intColVen=null;
            intColTbl=null;

            objTblCelEdiTxtFixItm=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_ALT).setCellEditor(objTblCelEdiTxtFixItm);
            tcmAux.getColumn(INT_TBL_FIX_NOM_ITM).setCellEditor(objTblCelEdiTxtFixItm);
            objTblCelEdiTxtFixItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                    intColSel=tblFix.getSelectedColumn();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_ALT), intFilSel, INT_TBL_DAT_COD_ALT);
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_NOM_ITM), intFilSel, INT_TBL_DAT_NOM_ITM);

                }
            });
            
            objTblCelEdiTxtFixUniMed=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_COD_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
            tcmAux.getColumn(INT_TBL_FIX_DEC_UNI).setCellEditor(objTblCelEdiTxtFixUniMed);
            objTblCelEdiTxtFixUniMed.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                    intColSel=tblFix.getSelectedColumn();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_COD_UNI), intFilSel, INT_TBL_DAT_COD_UNI);
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_DEC_UNI), intFilSel, INT_TBL_DAT_DEC_UNI);

                }
            });
            
            objTblCelEdiTxtPesKgrFix=new ZafTblCelEdiTxt(tblFix);
            tcmAux.getColumn(INT_TBL_FIX_PES_KGR).setCellEditor(objTblCelEdiTxtPesKgrFix);
            //objTblCelEdiTxtPesKgrFix=null;
            objTblCelEdiTxtPesKgrFix.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblFix.getSelectedRow();
                }
                    
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objTblModFix.getValueAt(intFilSel, INT_TBL_FIX_PES_KGR), intFilSel, INT_TBL_DAT_PES_KGR);

                    calcularReversionValoresKilos();
                    
                    calcularPreVtaReaKil();
                    calcularPreLisCal();
                    calcularMarPreLisCal_row();
                }
            });
                        
//            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblFix);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblModFix.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            ZafTblHeaGrp objTblHeaGrpFix=(ZafTblHeaGrp)tblFix.getTableHeader();
            objTblHeaGrpFix.setHeight(16*3);
            
            
            //inicio
            //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
//            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modDat = tblFix.getSelectionModel();//se descomento
            tblDat.setSelectionModel(modDat);//se descomento

            //controla que la fila seleccionada en una tabla(datos) tambien se seleccione en la otra tabla(fixed)
            ListSelectionModel modFix = tblDat.getSelectionModel();
            tblFix.setSelectionModel(modFix);
            
            //Inicio*****
            //Evitar que aparezca la barra de desplazamiento vertical en el JTable Fijo
            spnFix.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            spnFix.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            
            //VERTICAL
                //Adicionar el listener que controla el desplazamiento del JTable de datos.
                barFixVer=spnFix.getVerticalScrollBar();
                barDatVer=spnDat.getVerticalScrollBar();
//                barFixHor=spnFix.getHorizontalScrollBar();
//                barDatHor=spnDat.getHorizontalScrollBar();
                
                //PARA DESPLAZAMIENTOS DE CELDAS
                barDatVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                        barFixVer.setValue(evt.getValue());
                    }
                });
                barFixVer.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
                    public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                        barDatVer.setValue(evt.getValue());
                    }
                });
            
            
            //Fin*****

            
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
        BigDecimal bgdPreVta, bgdPreVtaMarUtiPreVta, bgdCosUni, bgdFacCosUni, bgdPesUni, bgdPreObjKil=new BigDecimal("0");
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
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                    
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+="        AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (chkSolStk.isSelected())
                    strAux+=" AND a3.nd_stkAct>0";
                
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.st_ser, a1.co_reg, a1.tx_desCor, a1.nd_pesItmKgr, a1.nd_stkAct";
                //Ingresos por Importacion.
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+="	, a" + (i+2) + ".nd_can AS a" + (i+2) + "_nd_canIngImp, a" + (i+2) + ".nd_preUniImp AS a" + (i+2) + "_nd_preUniIngImp, a" + (i+2) + ".nd_cosUni AS a" + (i+2) + "_nd_cosUniIngImp";
                }
                
                //Costo unitario del item
                strSQL+=" 	, a1.nd_cosUni";
                
                //Pedidos Embarcados
                for(int i=0; i<arlDatPedEmb.size(); i++){
                    strSQL+=" 	, a" + (i+11) + ".nd_can AS a" + (i+11) + "_nd_canPedEmb, a" + (i+11) + ".nd_preUni AS a" + (i+11) + "_nd_preUniPedEmb, a" + (i+11) + ".nd_cosUni AS a" + (i+11) + "_nd_cosUniPedEmb";
                    i++;
                }
                
                //Notas de Pedido
                for(int i=0; i<arlDatNotPed.size(); i++){
                    strSQL+=" 	, a" + (i+20) + ".nd_can AS a" + (i+20) + "_nd_canNotPed, a" + (i+20) + ".nd_preUni AS a" + (i+20) + "_nd_preUniNotPed, a" + (i+20) + ".nd_cosUni AS a" + (i+20) + "_nd_cosUniNotPed";
                    i++;
                }                
                strSQL+=" 	, a1.nd_preVta1, a1.nd_marUti, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr";
                
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a4.co_reg, a4.tx_desCor, a1.st_ser, a3.nd_stkAct";
                strSQL+=" 	     , a1.nd_preVta1, a1.nd_marUti, a1.nd_pesItmKgr, a1.nd_cosUni, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr";
                strSQL+=" 	FROM tbm_inv AS a1";
                strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" 	LEFT OUTER JOIN (";
                //strSQL+=" 		 SELECT b1.co_itmMae, SUM(b2.nd_stkAct) AS nd_stkAct";
                //strSQL+=" 		 FROM tbm_equInv AS b1";
                //strSQL+=" 		 INNER JOIN tbm_inv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                //strSQL+=" 		 WHERE b2.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                //strSQL+=" 		 GROUP BY b1.co_itmMae";
                strSQL+="              SELECT b1.co_itmMae, SUM(b3.nd_stkAct) AS nd_stkAct";
                strSQL+="              FROM ";
                strSQL+="              (tbm_inv AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";
                strSQL+="              INNER JOIN tbm_invBod AS b3 ON b2.co_emp=b3.co_emp AND b2.co_itm=b3.co_itm";
                strSQL+="              WHERE b2.co_emp<>0";
                strSQL+="              AND (   CASE WHEN b3.co_emp=1 THEN b3.co_bod NOT IN(4,13,14,16,17,18,19,24,25,27,30)";//tuval
                strSQL+="                           WHEN b3.co_emp=2 THEN b3.co_bod NOT IN(6,13,14,16,17,18,19,24,25,27,30)";//castek
                strSQL+="                           WHEN b3.co_emp=4 THEN b3.co_bod NOT IN(4,12,14,16,17,18,19,24,25,27,30)";//dimulti
                strSQL+="                      END";
                strSQL+="                  )";
                strSQL+="              AND (b2.tx_codAlt like '%S' OR b2.tx_codAlt like '%I')";
                strSQL+="              GROUP BY b1.co_itmMae";
                strSQL+=" 	 ) AS a3";
                strSQL+=" 	 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" 	 LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg)";
                strSQL+=" 	 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	 AND a1.st_reg='A'";
                strSQL+="        " + strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+=" 	 ORDER BY a1.tx_codAlt";
                strSQL+=" ) AS a1";
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+="    SELECT * FROM (";  
                    strSQL+="         SELECT a.co_itmMae, a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, SUM (a.nd_can) as nd_can, a.nd_cosUni, 0 as nd_preUniImp"; 
                    strSQL+="         FROM (";  
                    //INIMPO
                    strSQL+="            SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni";
                    strSQL+="                 , b1.co_reg, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                    strSQL+="            FROM tbm_cabMovInv AS c1 INNER JOIN tbm_detMovInv AS b1";
                    strSQL+="            ON c1.co_emp=b1.co_emp AND c1.co_loc=b1.co_loc AND c1.co_tipDoc=b1.co_tipDoc AND c1.co_doc=b1.co_doc";
                    strSQL+="            INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+="            ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="            INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+="            WHERE b1.co_emp=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_EMP) + "";
                    strSQL+="            AND b1.co_loc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_LOC) + "";
                    strSQL+="            AND b1.co_tipDoc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_TIP_DOC) + "";
                    strSQL+="            AND b1.co_doc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_DOC) + "";
                    strSQL+="            GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="                   , b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni, b1.co_reg, c1.tx_numDoc2, c1.fe_doc ";
                    strSQL+="           UNION ALL";
                    //AJUSTE
                    strSQL+="            SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni";
                    strSQL+="                 , b1.co_reg, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                    strSQL+="            FROM tbm_cabMovInv AS c1 INNER JOIN tbm_detMovInv AS b1";
                    strSQL+="            ON c1.co_emp=b1.co_emp AND c1.co_loc=b1.co_loc AND c1.co_tipDoc=b1.co_tipDoc AND c1.co_doc=b1.co_doc";
                    strSQL+="            INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+="            ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="            INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+="            INNER JOIN tbr_cabMovInv as a4 ";
                    strSQL+="            ON c1.co_emp=a4.co_emp AND c1.co_loc=a4.co_loc AND c1.co_tipDoc=a4.co_tipDoc AND c1.co_doc=a4.co_doc";
                    strSQL+="            AND c1.co_tipDoc IN (select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+"";
                    strSQL+="                                 and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV+")";
                    strSQL+="            WHERE a4.co_empRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_EMP) + "";
                    strSQL+="            AND a4.co_locRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_LOC) + "";
                    strSQL+="            AND a4.co_tipDocRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_TIP_DOC) + "";
                    strSQL+="            AND a4.co_docRel=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_DOC) + "";
                    strSQL+="            AND c1.st_reg IN ('A')"; //26Ene2018: Se agrega validación para que solo muestre costos de items de los ajustes activos, tbm_cabMovInv <>'O'.                   
                    strSQL+="            AND (CASE WHEN b1.st_Reg IS NULL THEN 'A' ELSE b1.st_Reg END ) NOT IN ('I')";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                    strSQL+="            GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+="                   , b1.nd_can, b1.nd_preUniImp, b1.nd_cosUni, b1.co_reg, c1.tx_numDoc2, c1.fe_doc ";
                    strSQL+="         ) as a"; 
                    strSQL+="         GROUP BY a.co_itmMae, a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a.nd_cosUni"; 
                    strSQL+="    ) as b WHERE b.nd_can>0";
                    strSQL+=") AS a" + (i+2);
                    strSQL+=" ON a1.co_itmMae=a" + (i+2) + ".co_itmMae";
                }
                for(int i=0; i<arlDatPedEmb.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+=" 	, e1.nd_can, e1.nd_preUni";
                    strSQL+=" 	, e1.nd_cosUni, e1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+=" 	, d1.co_emp AS co_empPedEmb, d1.co_loc AS co_locPedEmb, d1.co_tipdoc AS co_tipDocPedEmb, d1.co_doc AS co_docPedEmb";
                    strSQL+=" 	, d1.tx_numDoc2 AS tx_numPedEmb, d1.fe_doc AS fe_docPedEmb, d1.co_ctaAct, d1.co_ctaPas";
                    strSQL+=" 	FROM tbm_cabPedEmbImp AS d1";
                    strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS e1 ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                    strSQL+=" 	INNER JOIN (( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+=" 	INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
                    strSQL+=" 	ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 	WHERE d1.st_reg='A'";
                    strSQL+=" 	AND d1.co_emp=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_EMP) + "";
                    strSQL+=" 	AND d1.co_loc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_LOC) + "";
                    strSQL+=" 	AND d1.co_tipDoc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_TIP_DOC) + "";
                    strSQL+=" 	AND d1.co_doc=" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COD_DOC) + "";
                    strSQL+=" 	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+=" 	, d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, e1.nd_can, e1.nd_preUni";
                    strSQL+="  , e1.nd_cosUni, e1.co_reg, a2.tx_desCor, d1.co_ctaAct, d1.co_ctaPas, d1.tx_numDoc2, d1.fe_doc";
                    strSQL+="  ORDER BY e1.co_reg";
                    strSQL+=" ) AS a" + (i+11);
                    strSQL+=" ON a1.co_itmMae=a" + (i+11) + ".co_itmMae";
                    i++;
                }
                for(int i=0; i<arlDatNotPed.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+=" 	SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+=" 	, e1.nd_can, e1.nd_preUni";
                    strSQL+=" 	, e1.nd_cosUni, e1.co_reg AS co_regNotPed, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+=" 	, d1.co_emp AS co_empNotPed, d1.co_loc AS co_locNotPed, d1.co_tipdoc AS co_tipDocNotPed, d1.co_doc AS co_docNotPed";
                    strSQL+=" 	, d1.tx_numDoc2 AS tx_numNotPed, d1.fe_doc AS fe_docNotPed, d1.co_ctaAct, d1.co_ctaPas";
                    strSQL+=" 	, d1.st_notpedlis, d1.st_cie";
                    strSQL+=" 	FROM tbm_cabNotPedImp AS d1 INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+=" 						ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                    strSQL+=" 						INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+=" 						)";
                    strSQL+=" 	ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                    strSQL+=" 	WHERE d1.st_reg='A'";
                    strSQL+=" 	AND d1.co_emp=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_EMP) + "";
                    strSQL+=" 	AND d1.co_loc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_LOC) + "";
                    strSQL+=" 	AND d1.co_tipDoc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_TIP_DOC) + "";
                    strSQL+=" 	AND d1.co_doc=" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COD_DOC) + "";
                    strSQL+=" ) AS a" + (i+20);
                    strSQL+=" ON a1.co_itmMae=a" + (i+20) + ".co_itmMae";
                    i++;
                }
                strSQL+=" ORDER BY a1.tx_codAlt";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                vecDatFix.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        //System.out.println("codigo maestros datos: " + rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecReg.add(INT_TBL_DAT_COD_UNI,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_BUT_UNI,null);
                        vecReg.add(INT_TBL_DAT_PES_KGR,rst.getString("nd_pesItmKgr"));
                        vecReg.add(INT_TBL_DAT_PES_KGR_AUX,rst.getString("nd_pesItmKgr"));
                        vecReg.add(INT_TBL_DAT_STK_CON,rst.getString("nd_stkAct"));                        
                        //adicionar las columnas dinamicas
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                            vecReg.add(j,     null);
                        }
                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecReg.add(j,     null);
                        }
                        
                        int i;
                        i=0;
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            vecReg.setElementAt(rst.getString("a" + (i+2) + "_nd_cosUniIngImp"), j);
                            i++;
                        }
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            vecReg.setElementAt(rst.getString("nd_cosUni"), j);
                        }
                        
                        i=0;
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            if((i%2)==0)//par
                                vecReg.setElementAt(rst.getString("a" + (i+11) + "_nd_cosUniPedEmb"), j);
                            i++;
                        }
                        
                        i=0;
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            if((i%2)==0)//par
                                vecReg.setElementAt(rst.getString("a" + (i+20) + "_nd_cosUniNotPed"), j);
                            i++;
                        }
                        
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            if(j==intNumColIniPreReaMar)
                                vecReg.setElementAt(rst.getString("nd_preVta1"), j);
                            if(j==(intNumColIniPreReaMar+3))
                                vecReg.setElementAt(rst.getString("nd_marUti"), j);
                        }
                        
                        bgdPreVta=rst.getBigDecimal("nd_preVta1");
                        bgdCosUni=rst.getBigDecimal("nd_cosUni");
                        bgdFacCosUni=rst.getBigDecimal("nd_facCosUni");
                        
                        vecReg.setElementAt(bgdFacCosUni, (intNumColIniFac));//factor de costo
                        
                        bgdPreVtaMarUtiPreVta=new BigDecimal("0");
                        if (bgdCosUni==null || bgdFacCosUni==null)
                            vecReg.setElementAt(null, (intNumColIniFac+1));
                        else{
                            //Precio de venta con factor
                            bgdPreVtaMarUtiPreVta=objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesBaseDatos());
                            vecReg.setElementAt(bgdPreVtaMarUtiPreVta, (intNumColIniFac+1));
                        }
                        
                        //margen de utilidad(minimo)
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, (intNumColIniPreReaMar+2));
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, (intNumColIniPreReaMar+2));
                            }
                            else{
                                vecReg.setElementAt(objUti.redondearBigDecimal((((bgdPreVta.multiply(BigDecimal.valueOf(0.75))).subtract(bgdCosUni)).divide((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), (intNumColIniPreReaMar+2));
                            }
                        }
                        
                        //factor
                        if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, intNumColIniMarFac);
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, intNumColIniMarFac);
                            }
                            else{
                                bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                                vecReg.setElementAt(objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intNumColIniMarFac);
                            }
                        }
                        
                        //precio venta real kilo
                        bgdPesUni=rst.getBigDecimal("nd_pesItmKgr");
                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, (intNumColIniPreReaMar+1));
                        }
                        else{
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, (intNumColIniPreReaMar+1));
                            }
                            else{
                                
                                //bgdPreObjKil=((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()));
                                bgdPreObjKil=((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()));
                                vecReg.setElementAt(bgdPreObjKil, (intNumColIniPreReaMar+1));
                            }
                        }
                                
                        //precio lista calculado
                        if (bgdPreObjKil==null || bgdPreObjKil.compareTo(BigDecimal.ZERO)==0){
                            vecReg.setElementAt(null, intNumColIniPre);
                        }
                        else{
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                                vecReg.setElementAt(null, intNumColIniPre);
                            }
                            else{
                                vecReg.setElementAt(rst.getString("nd_preVtaObjKgr"), intNumColIniPre);
                            }
                        }
                        vecDat.add(vecReg);
                        
                        //inicio fix
                        vecRegFix=new Vector();
                        vecRegFix.add(INT_TBL_FIX_LIN,"");
                        //System.out.println("codigo maestros fijo: " + rst.getString("co_itmMae"));
                        vecRegFix.add(INT_TBL_FIX_COD_MAE,rst.getString("co_itmMae"));
                        vecRegFix.add(INT_TBL_FIX_COD_SIS,rst.getString("co_itm"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT,rst.getString("tx_codAlt"));
                        vecRegFix.add(INT_TBL_FIX_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecRegFix.add(INT_TBL_FIX_NOM_ITM,rst.getString("tx_nomItm"));
                        vecRegFix.add(INT_TBL_FIX_CHK_SER,rst.getString("st_ser").equals("N")?null:Boolean.TRUE);
                        vecRegFix.add(INT_TBL_FIX_COD_UNI,rst.getString("co_reg"));
                        vecRegFix.add(INT_TBL_FIX_DEC_UNI,rst.getString("tx_desCor"));
                        vecRegFix.add(INT_TBL_FIX_BUT_UNI,null);
                        vecRegFix.add(INT_TBL_FIX_PES_KGR,rst.getString("nd_pesItmKgr"));
                        vecRegFix.add(INT_TBL_FIX_PES_KGR_AUX,rst.getString("nd_pesItmKgr"));
                        vecRegFix.add(INT_TBL_FIX_STK_CON,rst.getString("nd_stkAct"));
                        vecDatFix.add(vecRegFix);
                        //fin fix
                    }
                    else
                    {
                        break;
                    }
                    
//                    System.out.println("0-intNumColEst: " + intNumColEst);                    
//                    System.out.println("1-intNumColIniIngImp: " + intNumColIniIngImp);                    
//                    System.out.println("2-intNumColFinIngImp: " + intNumColFinIngImp);                    
//                    System.out.println("3-intNumColIniCosUni: " + intNumColIniCosUni);
//                    System.out.println("4-intNumColFinCosUni: " + intNumColFinCosUni);
//                    System.out.println("5-intNumColIniPedEmb: " + intNumColIniPedEmb);
//                    System.out.println("6-intNumColFinPedEmb: " + intNumColFinPedEmb);
//                    System.out.println("7-intNumColIniNotPed: " + intNumColIniNotPed);
//                    System.out.println("8-intNumColFinNotPed: " + intNumColFinNotPed);
//                    System.out.println("9-intNumColIniMarNotPed: " + intNumColIniMarNotPed);
//                    System.out.println("10-intNumColFinMarNotPed: " + intNumColFinMarNotPed);
//                    System.out.println("11-intNumColIniPreReaMar: " + intNumColIniPreReaMar);
//                    System.out.println("12-intNumColFinPreReaMar: " + intNumColFinPreReaMar);
//                    System.out.println("13-intNumColIniFac: " + intNumColIniFac);
//                    System.out.println("14-intNumColFinFac: " + intNumColFinFac);
//                    System.out.println("15-intNumColIniMarFac: " + intNumColIniMarFac);
//                    System.out.println("16-intNumColFinMarFac: " + intNumColFinMarFac);
//                    System.out.println("17-intNumColIniPre: " + intNumColIniPre);
//                    System.out.println("18-intNumColFinPre: " + intNumColFinPre);
//                    System.out.println("19-intNumColIniCanMarPreLisNew: " + intNumColIniCanMarPreLisNew);
//                    System.out.println("20-intNumColFinCanMarPreLisNew: " + intNumColFinCanMarPreLisNew);
		
                    //Configurar JTable: Establecer columnas editables.
                    vecAux=new Vector();
                    vecAuxFix=new Vector();
                    
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        vecAux.add("" + INT_TBL_DAT_COD_ALT);vecAuxFix.add("" + INT_TBL_FIX_COD_ALT);
                        vecAux.add("" + INT_TBL_DAT_NOM_ITM);vecAuxFix.add("" + INT_TBL_FIX_NOM_ITM);
                        vecAux.add("" + INT_TBL_DAT_DEC_UNI);vecAuxFix.add("" + INT_TBL_FIX_DEC_UNI);
                        vecAux.add("" + INT_TBL_DAT_BUT_UNI);vecAuxFix.add("" + INT_TBL_FIX_BUT_UNI);
                        vecAux.add("" + INT_TBL_DAT_PES_KGR);vecAuxFix.add("" + INT_TBL_FIX_PES_KGR);
                        
                        //Modificar "Precio de venta 1"
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            if(j==intNumColIniPreReaMar){
                                vecAux.add(""+j);
                            }//precio venta
                        }
                        //Modificar "Margen de utilidad".
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                            if(j==(intNumColIniPreReaMar+3)){
                                vecAux.add(""+j);
                            }//margen minimo - margen utilidad
                        }
                        //Modificar "Cantidad maxima para venta".
                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecAux.add(""+j);//cantidad maxima de precio de venta
                        }
                        
                    }
                    else
                    {
                        if(objParSis.getCodigoUsuario()==90){
                            vecAux.add("" + INT_TBL_DAT_COD_ALT);
                            vecAuxFix.add("" + INT_TBL_DAT_COD_ALT);
                        }
                        
                        //3869: Ficha "Reporte": Tabla->Modificar "Nombre del item".
                        if (objPerUsr.isOpcionEnabled(3869)){
                            vecAux.add("" + INT_TBL_DAT_NOM_ITM);
                            vecAuxFix.add("" + INT_TBL_DAT_NOM_ITM);
                        }
                        //3870: Ficha "Reporte": Tabla->Modificar "Unidad de medida".
                        if (objPerUsr.isOpcionEnabled(3870)){
                            vecAux.add("" + INT_TBL_DAT_BUT_UNI);
                            vecAuxFix.add("" + INT_TBL_DAT_BUT_UNI);
                        }
                        //3871: Ficha "Reporte": Tabla->Modificar "Precio de venta 1".
                        if (objPerUsr.isOpcionEnabled(3871)){
                            for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                                if(j==intNumColIniPreReaMar){
                                    vecAux.add(""+j);
                                }//precio venta
                            }
                        }
                        //3872: Ficha "Reporte": Tabla->Modificar "Margen de utilidad".
                        if (objPerUsr.isOpcionEnabled(3872)){
                            for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                                if(j==(intNumColIniPreReaMar+3)){
                                    vecAux.add(""+j);
                                }//margen minimo - margen utilidad
                            }
                        }
                        //3873: Ficha "Reporte": Tabla->Modificar "Peso(kg)"
                        if (objPerUsr.isOpcionEnabled(3873)){
                            vecAux.add("" + INT_TBL_DAT_PES_KGR);
                            vecAuxFix.add("" + INT_TBL_DAT_PES_KGR);
                        }
                        //3874: Ficha "Reporte": Tabla->Modificar "Cantidad maxima para venta".
//                        if (objPerUsr.isOpcionEnabled(3874)){

//                        }
                    }
                    
                    for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                        if(j==intNumColIniFac){
                            vecAux.add(""+j);
                        }                            
                        if(j==(intNumColFinFac-1)){
                            vecAux.add(""+j);
                        }
                    }
                    
                    for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                        if(j==intNumColIniPre){
                            vecAux.add(""+j);
                        }
                    }
                    
                    for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                            vecAux.add(""+j);
                    }
                    objTblMod.setColumnasEditables(vecAux);
                    vecAux=null;
                    objTblModFix.setColumnasEditables(vecAuxFix);
                    vecAuxFix=null;
                                        
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //System.out.println("vecDat: " +  vecDat.toString());
                //System.out.println("vecFix: " +  vecDatFix.toString());
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Asignar vectores al modelo
                objTblModFix.setData(vecDatFix);
                tblFix.setModel(objTblModFix);
                vecDatFix.clear();
                
                
                calcularValoresKilos(0);
                ocultaColumnasPrecios();
                ocultaColumnasMargenPrecio();
                ocultaColumnasFactor();
                calcularMarNotPed();
                calcularMarPedEmb();
                calcularPreLisCalConsulta();
                calcularMarPreLisCal();
                if(objParSis.getCodigoUsuario()!=1){
                    if(!objPerUsr.isOpcionEnabled(3875)){
                        ocultaColumnasCostoUnitario();
                        encerarColumnasCostoUnitario();//esta comentado el codigo - no hace nada el metodo
                    }
                }

                objTblMod.initRowsState();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                        
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        boolean blnRes=true;
        String strFecUltMod;
        int intNumFil, i;
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
                strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                intNumFil=objTblMod.getRowCountTrue();
                for (i=0; i<intNumFil;i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        //System.out.println("*****codigo maestro: " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE));
                        //Verifica que el item:codigo alterno no este ingresado en la db para poder guardarlo
                        if(objParSis.getCodigoUsuario()==90){
                            strSQL ="";
                            strSQL+=" SELECT tx_codAlt";
                            strSQL+=" FROM tbm_inv";
                            strSQL+=" WHERE tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase());
                            strSQL+=" GROUP BY tx_codAlt";
                            System.out.println("strSQL: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            if(rst.next()){
                                mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
                                return false;
                            } 
                        }
                        
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="UPDATE tbm_inv";
                        strSQL+=" SET tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase(),1);
                        strSQL+=", tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
                        strSQL+=", co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
                            if(j==intNumColIniPreReaMar)
                                strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                            if(j==(intNumColIniPreReaMar+3))
                                strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                        }
                        for(int j=intNumColIniFac; j<intNumColFinFac; j++){
                            if(j==intNumColIniFac)
                                strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                        }
                        for(int j=intNumColIniPre; j<intNumColFinPre; j++){
                            if(j==intNumColIniPre)
                                strSQL+=", nd_preVtaObjKgr=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                        }
                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew; j++){
                            if(j==intNumColIniCanMarPreLisNew)
                                strSQL+=", nd_canMaxVen=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
                        }
                        strSQL+=", nd_pesItmKgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_KGR),2);
                        strSQL+=", fe_ultMod='" + strFecUltMod + "'";
                        strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                        strSQL+=" FROM (";
                        strSQL+="       SELECT a1.co_emp, a2.co_itm";
                        strSQL+="       FROM tbm_inv AS a1";
                        strSQL+="       INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                        strSQL+="       WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
                        strSQL+=" ) AS b1";
                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
                        System.out.println("actualizarDet.TbmInv: " + strSQL);
                        stm.executeUpdate(strSQL);
                    }
                }
                //Inserta Histórico
                objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario());
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
    
     //<editor-fold defaultstate="collapsed" desc="/* Inserción de histórico transaccionado */">
//    /**
//     * Esta función permite actualizar los registros del detalle.
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean actualizarDet()
//    {
//        boolean blnRes=true;
//        String strFecUltMod;
//        try
//        {
//            //Obtener la fecha del servidor.
//            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
//            if (datFecAux==null)
//                return false;
//            strFecUltMod=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            con.setAutoCommit(false);
//            if (con!=null)
//            {
//                //Actualiza el inventario
//                if(actualizaTbmInv(con, strFecUltMod)){
//                    //Insertar el histórico.
//                    if(objHisTblBasDat.insertarHistoricoMasivo(con, "tbm_inv", "tbh_inv", "WHERE a1.fe_ultMod='" + strFecUltMod + "' AND a1.co_usrMod=" + objParSis.getCodigoUsuario())) {
//                        blnRes=true; 
//                        con.commit();
//                    }
//                    else{ 
//                        con.rollback(); 
//                        blnRes=false; System.err.println("Error en insertarHistoricoMasivo");  
//                    }
//                }
//                else{  
//                    con.rollback(); 
//                    blnRes=false; System.err.println("Error en actualizaTbmInv");  
//                }
//         
//                con.close();
//                con=null;
//                datFecAux=null;
//                //Inicializo el estado de las filas.
//                objTblMod.initRowsState();
//                objTblMod.setDataModelChanged(false);
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
//        
//    /**
//     * Esta función permite actualizar los registros en la tabla tbm_inv
//     * @return true: Si se pudo actualizar los registros.
//     * <BR>false: En el caso contrario.
//     */
//    private boolean actualizaTbmInv(Connection conn, String strFecUltMod)
//    {
//        boolean blnRes=true;
//        int intNumFil, i;
//        try
//        {
//            if (conn!=null)
//            {
//                stm=conn.createStatement();
//                intNumFil=objTblMod.getRowCountTrue();
//                for (i=0; i<intNumFil;i++)
//                {
//                    if (objUti.parseString(objTblMod.getValueAt(i,INT_TBL_DAT_LIN)).equals("M"))
//                    {
//                        //System.out.println("*****codigo maestro: " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE));
//                        //verifica que el item:codigo alterno no este ingresado en la db para poder guardarlo
//                        if(objParSis.getCodigoUsuario()==90){
//                            strSQL ="";
//                            strSQL+=" SELECT tx_codAlt";
//                            strSQL+=" FROM tbm_inv";
//                            strSQL+=" WHERE tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase());
//                            strSQL+=" GROUP BY tx_codAlt";
//                            System.out.println("strSQL: " + strSQL);
//                            rst=stm.executeQuery(strSQL);
//                            if(rst.next()){
//                                mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
//                                return false;
//                            } 
//                        }
//                        //Armar la sentencia SQL.
//                        strSQL="";
//                        strSQL+=" UPDATE tbm_inv";
//                        strSQL+=" SET tx_codAlt=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT).toString().toUpperCase(),1);
//                        strSQL+=", tx_nomItm=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_NOM_ITM),1);
//                        strSQL+=", co_uni=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_COD_UNI),2);
//                        for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar; j++){
//                            if(j==intNumColIniPreReaMar)
//                                strSQL+=", nd_preVta1=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                            if(j==(intNumColIniPreReaMar+3))
//                                strSQL+=", nd_marUti=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniFac; j<intNumColFinFac; j++){
//                            if(j==intNumColIniFac)
//                                strSQL+=", nd_facCosUni=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniPre; j<intNumColFinPre; j++){
//                            if(j==intNumColIniPre)
//                                strSQL+=", nd_preVtaObjKgr=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew; j++){
//                            if(j==intNumColIniCanMarPreLisNew)
//                                strSQL+=", nd_canMaxVen=" + objUti.codificar(objTblMod.getValueAt(i,j),2);
//                        }
//                        strSQL+=", nd_pesItmKgr=" + objUti.codificar(objTblMod.getValueAt(i,INT_TBL_DAT_PES_KGR),2);
//                        strSQL+=", fe_ultMod='" + strFecUltMod + "'";
//                        strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
//                        strSQL+=" FROM (";
//                        strSQL+="       SELECT a1.co_emp, a2.co_itm";
//                        strSQL+="       FROM tbm_inv AS a1";
//                        strSQL+="       INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
//                        strSQL+="       WHERE a2.co_itmMae=" + objTblMod.getValueAt(i,INT_TBL_DAT_COD_MAE);
//                        strSQL+=" ) AS b1";
//                        strSQL+=" WHERE tbm_inv.co_emp=b1.co_emp AND tbm_inv.co_itm=b1.co_itm";
//                        System.out.println("actualizaTbmInv: " + strSQL);
//                        stm.executeUpdate(strSQL);
//                    }
//                }    
//                stm.close();           
//                stm=null;
//            }
//        }
//        catch (java.sql.SQLException e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (Exception e)
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
    //</editor-fold>
        
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar la "Unidad de medida".
     */
    private boolean configurarVenConUniMed()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_reg");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Siglas");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("414");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_reg, a1.tx_desCor, a1.tx_desLar";
            strSQL+=" FROM tbm_var AS a1";
            strSQL+=" WHERE a1.co_grp=5 AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_reg";
            vcoUniMed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de unidades de medida", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoUniMed.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoUniMed.setCampoBusqueda(1);
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
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
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
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
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
     * Esta función calcula el margen de utilidad sobre el costo de la fila.
     */
    private void calcularMarUtiCos()
    {
        int intFilSel[], i;
        BigDecimal bgdPreVta, bgdCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdPreVta=(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar))));
            bgdCosUni=(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni))));
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0)
            {
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+2));
            }
            else
            {
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0)
                {
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+2));
                }
                else
                {
                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniPreReaMar+2));
                }
            }
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
    private class ZafThreadGUI extends Thread
    {
        
        public void run()
        {
            if(configurarColumnasAdicionar()){
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
            int intColSel=-1;
            
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%1+INT_TBL_DAT_NUM_TOT_CES;
                        
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="<html><h3 style='text-align:center;'>Colores utilizados en la tabla</h3><table border='1'><tr><td><b>Fondo</b></td><td><b>Fuente</b></td><td><b>Observación</b></td></tr><tr><td style='background-color: #FF0000'>&nbsp;</td><td>&nbsp;</td><td>Indica que el \"Mar.Uti.Cos.\" es menor al \"Mar.Uti.\".</td></tr></table><br></html>";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno del item dos";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CHK_SER:
                    strMsg="Servicio";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_PES_KGR:
                    strMsg="Peso(Kg)";
                    break;
                case INT_TBL_DAT_STK_CON:
                    intColSel=tblDat.columnAtPoint(evt.getPoint());
                    
                    if(intColSel==INT_TBL_DAT_STK_CON){
                        strMsg="Stock";
                    }
                    else{
                        for(int i=intNumColIniIngImp; i<intNumColFinIngImp; i++){
                            if(intColSel==i){
                                strMsg="Ingresos por Importación";
                                break;
                            }
                        }
                        
                        for(int i=intNumColIniCosUni; i<intNumColFinCosUni; i++){
                            if(intColSel==i){
                                strMsg="Costo Promedio del item";
                                break;
                            }
                        }
                        for(int i=intNumColIniPedEmb; i<intNumColFinPedEmb; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Pedidos Embarcados";
                                break;
                            }
                        }
                        for(int i=intNumColIniPedEmb; i<intNumColFinPedEmb; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Pedidos Embarcados";
                                break;
                            }
                        }
                        for(int i=intNumColIniNotPed; i<intNumColFinNotPed; i++){
                            if(intColSel==i){
                                strMsg="Pedidos en Tránsito: Notas de Pedido";
                                break;
                            }
                        }
                        for(int i=intNumColIniMarNotPed; i<intNumColFinMarNotPed; i++){
                            if(intColSel==i){
                                strMsg="Margen de utilidad: Notas de Pedido";
                                break;
                            }
                        }
                        for(int i=intNumColIniPreReaMar; i<intNumColFinPreReaMar; i++){
                            if(intColSel==i){
                                if(i==intNumColIniPreReaMar){
                                    strMsg="Precio de venta";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+1)){
                                    strMsg="Precio de venta real por kilo incluido IVA";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+2)){
                                    strMsg="Margen de utilidad sobre el precio de venta real";
                                    break;
                                }
                                if(i==(intNumColIniPreReaMar+3)){
                                    strMsg="Margen de utilidad mínimo";
                                    break;
                                }
                                
                            }

                        }
                        for(int i=intNumColIniFac; i<intNumColFinFac; i++){
                            if(intColSel==i){
                                if(i==intNumColIniFac){
                                    strMsg="Factor de costo en unidades";
                                    break;
                                }
                                if(i==(intNumColIniFac+1)){
                                    strMsg="Costo unitario con factor - Precio de venta con factor";
                                    break;
                                }
                            }
                        }
                        
                        for(int i=intNumColIniMarFac; i<intNumColFinMarFac; i++){
                            strMsg="Margen con factor";
                            break;
                        }                        
                        
                        
                        for(int i=intNumColIniPre; i<intNumColFinPre; i++){
                            if(intColSel==i){
                                if(i==intNumColIniPre){
                                    strMsg="Factor de precio en kilos incluido IVA - Precio objetivo en kilos incluido IVA";
                                    break;
                                }
                                if(i==(intNumColIniPre+1)){
                                    strMsg="Precio de lista calculado";
                                    break;
                                }
                            }
                        }
                        for(int i=intNumColIniCanMarPreLisNew; i<intNumColFinCanMarPreLisNew; i++){
                            if(intColSel==i){
                                strMsg="Margen de precio de lista calculado";
                                break;
                            }
                        }
                    }                    
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    


    private boolean configurarColumnasAdicionar(){
        boolean blnRes=true;
        try{
            if(eliminaColumnasAdicionadas()){
                System.out.println("dentro de eliminaColumnasAdicionadas");
                if(cargarCabeceraIngresosImportacion()){
                    System.out.println("dentro de cargarCabeceraIngresosImportacion");
                    if(agregarColumnasIngresosImportacion()){
                        System.out.println("dentro de agregarColumnasIngresosImportacion");
                        if(agregarColumnasCostoUnitario()){//totales
                            System.out.println("dentro de agregarColumnasCostoUnitario");
                            if(cargarCabeceraPedidosEmbarcados()){
                                System.out.println("dentro de cargarCabeceraPedidosEmbarcados");
                                if(agregarColumnasPedidoEmbarcado()){
                                    System.out.println("dentro de agregarColumnasPedidoEmbarcado");
                                    if(cargarCabeceraNotasPedidos()){
                                        System.out.println("dentro de cargarCabeceraNotasPedidos");
                                        if(agregarColumnasNotaPedido()){
                                            if(agregarColumnasMargenNotaPedidoReciente()){
                                                if(agregarColumnasPrecioRealMargen()){
                                                    if(agregarColumnasFactor()){
                                                        if(agregarColumnasFactorMargen()){
                                                            if(agregarColumnasPrecio()){
                                                                if(agregarColumnasCantidadMargenPrecioListaNew()){

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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

   private boolean eliminaColumnasAdicionadas(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();            
     
            for (int i=(objTblMod.getColumnCount()-1); i>=intNumColEst; i--){               
               objTblMod.removeColumn(tblDat, i);
            }          
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   private boolean agregarColumnasIngresosImportacion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddIngImp=arlDatIngImp.size();//
        intNumColIniIngImp=intNumColEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomIngImp="";
        try{
            objTblCelRenLblColDinIngImp=new ZafTblCelRenLbl();
            objTblCelRenLblColDinIngImp.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinIngImp.setTipoFormato(objTblCelRenLblColDinIngImp.INT_FOR_NUM);
            objTblCelRenLblColDinIngImp.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinIngImp.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinIngImp.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinIngImp.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            
            
            
            for (int i=0; i<intNumColAddIngImp; i++){
                //strNomIngImp="" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM);
                strNomIngImp="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM) + "<BR>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_FEC) + "</DIV></HTML>";
                
                objUti.setStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COL, "" + (intNumColIniIngImp+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniIngImp+i);
                tbc.setHeaderValue(strNomIngImp);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinIngImp);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Lle.");
                    objTblHeaColGrpTit.setHeight(16);
                    
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);                
            }
            intNumColFinIngImp=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
   private boolean agregarColumnasCostoUnitario(){
        boolean blnRes=true;
        javax.swing.table.TableColumn tbc;
        intNumColAddCosUni=1;//
        intNumColIniCosUni=intNumColFinIngImp;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinCosUni=new ZafTblCelRenLbl();
            objTblCelRenLblColDinCosUni.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinCosUni.setTipoFormato(objTblCelRenLblColDinCosUni.INT_FOR_NUM);
            objTblCelRenLblColDinCosUni.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinCosUni.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCosUni.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinCosUni.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinCosUni.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            
            
            for (int i=0; i<intNumColAddCosUni; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniCosUni+i);
                tbc.setHeaderValue("Cos.Uni.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinCosUni);
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);                               
            }
            intNumColFinCosUni=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean agregarColumnasPedidoEmbarcado(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPedEmb=arlDatPedEmb.size();//
        intNumColIniPedEmb=intNumColFinCosUni;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomPed="";
        try{
            objTblCelRenLblColDinPedEmb=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPedEmb.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPedEmb.setTipoFormato(objTblCelRenLblColDinPedEmb.INT_FOR_NUM);
            objTblCelRenLblColDinPedEmb.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinPedEmb.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPedEmb.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPedEmb.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPedEmb.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddPedEmb; i++){
                
                if((i%2)==0)
                    strNomPed="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM) + "<BR>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_FEC) + "</DIV></HTML>";
                else
                    strNomPed="<HTML><DIV ALIGN=center>" + "Margen" + "<BR>" + objUti.getStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_NUM) + "</DIV></HTML>";
                    
                objUti.setStringValueAt(arlDatPedEmb, i, INT_ARL_PED_EMB_COL, "" + (intNumColIniPedEmb+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniPedEmb+i);
                tbc.setHeaderValue(strNomPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinPedEmb);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Emb.");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
            }
            
            intNumColFinPedEmb=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

   private boolean agregarColumnasNotaPedido(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddNotPed=arlDatNotPed.size();//
        intNumColIniNotPed=intNumColFinPedEmb;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomNotPed="";
        try{
            objTblCelRenLblColDinNotPed=new ZafTblCelRenLbl();
            objTblCelRenLblColDinNotPed.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinNotPed.setTipoFormato(objTblCelRenLblColDinNotPed.INT_FOR_NUM);
            objTblCelRenLblColDinNotPed.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinNotPed.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinNotPed.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddNotPed; i++){

                
                if((i%2)==0)
                    strNomNotPed="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_NUM) + "<BR>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_FEC) + "</DIV></HTML>" ;
                else
                    strNomNotPed="<HTML><DIV ALIGN=center>" + "Margen" + "<BR>" + objUti.getStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_NUM) + "</DIV></HTML>" ;
     
                objUti.setStringValueAt(arlDatNotPed, i, INT_ARL_NOT_PED_COL, "" + (intNumColIniNotPed+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniNotPed+i);
                
                tbc.setHeaderValue(strNomNotPed);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinNotPed);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Ped.Por.Emb.");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinNotPed=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   
   
   private boolean agregarColumnasMargenNotaPedidoReciente(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;

        intNumColAddMarNotPed=0;//
        intNumColIniMarNotPed=intNumColFinNotPed;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomNotPed="";
        try{
            objTblCelRenLblColDinMarNotPed=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarNotPed.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarNotPed.setTipoFormato(objTblCelRenLblColDinMarNotPed.INT_FOR_NUM);
            objTblCelRenLblColDinMarNotPed.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinMarNotPed.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarNotPed.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarNotPed.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinNotPed.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinMarNotPed.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinMarNotPed.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddMarNotPed; i++){
                    
                tbc=new javax.swing.table.TableColumn(intNumColIniMarNotPed+i);
                tbc.setHeaderValue("Mar.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarNotPed);

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
            }
            
            intNumColFinMarNotPed=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
   
   
   
   private boolean agregarColumnasPrecioRealMargen(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPreReaMar=4;//
        intNumColIniPreReaMar=intNumColFinMarNotPed;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinPreReaMar=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPreReaMar.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPreReaMar.setTipoFormato(objTblCelRenLblColDinPreReaMar.INT_FOR_NUM);
            objTblCelRenLblColDinPreReaMar.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtPreVta=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinPreReaMar.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPreReaMar.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPreReaMar.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPreReaMar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            
            for(int i=0; i<intNumColAddPreReaMar; i++){
                String strSubTit=(i==0?"Pre.Vta.":(i==1?"Pre.Vta.Rea.Kil.(IVA)":(i==2?"Mar.Pre.Vta.Rea.":"Mar.Pre.Vta.Min.")));
                tbc=new javax.swing.table.TableColumn(intNumColIniPreReaMar+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinPreReaMar);

                tbc.setCellEditor(objTblCelEdiTxtPreVta);
                objTblCelEdiTxtPreVta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        System.out.println("");
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        calcularMarUtiCos();
                        calcularPreVtaReaKil();

                    }
                });
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Precio de venta");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinPreReaMar=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
   private boolean agregarColumnasFactor(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddFac=2;//
        intNumColIniFac=intNumColFinPreReaMar;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinFac=new ZafTblCelRenLbl();
            objTblCelRenLblColDinFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinFac.setTipoFormato(objTblCelRenLblColDinFac.INT_FOR_NUM);
            objTblCelRenLblColDinFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtFacCos=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinFac.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinFac.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for(int i=0; i<intNumColAddFac; i++){
                String strSubTit=(i==0?"Fac.Cos.":"Pre.Vta.Fac.");
                tbc=new javax.swing.table.TableColumn(intNumColIniFac+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth((i==0)?56:68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinFac);
                tbc.setCellEditor(objTblCelEdiTxtFacCos);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("                    Datos con");
                    objTblHeaColGrpTit.setHeight(16);
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            
            objTblCelEdiTxtFacCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCosUniFac();
                    calcularMarFac();


                    
                }

            });
            
            
            
            
            intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   
   
   
   private boolean agregarColumnasFactorMargen(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddMarFac=1;//
        intNumColIniMarFac=intNumColFinFac;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinMarFac=new ZafTblCelRenLbl();
            objTblCelRenLblColDinMarFac.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinMarFac.setTipoFormato(objTblCelRenLblColDinMarFac.INT_FOR_NUM);
            objTblCelRenLblColDinMarFac.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelEdiTxtMarFacCos=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinMarFac.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinMarFac.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinMarFac.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinFac.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinMarFac.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinMarFac.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }                
            });
            
            for(int i=0; i<intNumColAddMarFac; i++){
                String strSubTit="Mar.Fac.";
                tbc=new javax.swing.table.TableColumn(intNumColIniMarFac+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinMarFac);
                tbc.setCellEditor(objTblCelEdiTxtMarFacCos);

                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("factor         ");//
                    objTblHeaColGrpTit.setHeight(16);
                }
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            
            objTblCelEdiTxtMarFacCos.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    calcularCostoUnitarioFactor_Margen();
                }

            });
            
            
            
            intNumColFinMarFac=objTblMod.getColumnCount();
            //intNumColFinFac=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   
   
   
   
   
   
   private boolean agregarColumnasPrecio(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
//        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
//        objTblHeaGrp.setHeight(16*3);
//        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddPre=2;//
        intNumColIniPre=intNumColFinMarFac;//numero de columnas que tiene el modelo antes de adicionar las columnas  *AQUI  "intNumColFinFac"
        try{
            objTblCelRenLblColDinPre=new ZafTblCelRenLbl();
            objTblCelRenLblColDinPre.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinPre.setTipoFormato(objTblCelRenLblColDinPre.INT_FOR_NUM);
            objTblCelRenLblColDinPre.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtPreObjKil=new ZafTblCelEdiTxt();
                   
            objTblCelRenLblColDinPre.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinPre.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinPre.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinPre.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for(int i=0; i<intNumColAddPre; i++){
                String strSubTit=(i==0?"Pre.Vta.Obj.Kil.(IVA)":"Pre.Vta.Lis.Cal.");
                tbc=new javax.swing.table.TableColumn(intNumColIniPre+i);
                tbc.setHeaderValue(strSubTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinPre);
                tbc.setCellEditor(objTblCelEdiTxtPreObjKil);
                
                objTblCelEdiTxtPreObjKil.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                            calcularPreLisCal();
                            //calcularMarPreObjKil();
                            calcularMarPreLisCal_row();                       
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
//                if(i==0){
//                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Datos de precios");
//                    objTblHeaColGrpTit.setHeight(16);
//                }
//                
//                objTblHeaColGrpTit.add(tbc);
//                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinPre=objTblMod.getColumnCount();

//            objTblHeaGrp=null;
//            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean agregarColumnasCantidadMargenPrecioListaNew(){
        boolean blnRes=true;

        javax.swing.table.TableColumn tbc;

        intNumColAddCanMarPreLisNew=1;//
        intNumColIniCanMarPreLisNew=intNumColFinPre;//numero de columnas que tiene el modelo antes de adicionar las columnas
        try{
            objTblCelRenLblColDinCanMarPreLisNew=new ZafTblCelRenLbl();
            objTblCelRenLblColDinCanMarPreLisNew.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinCanMarPreLisNew.setTipoFormato(objTblCelRenLblColDinCanMarPreLisNew.INT_FOR_NUM);
            objTblCelRenLblColDinCanMarPreLisNew.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelEdiTxtCanMarPreLisNew=new ZafTblCelEdiTxt();
            
            objTblCelRenLblColDinCanMarPreLisNew.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    BigDecimal bgdMarUti, bgdMarUtiCos;
                    bgdMarUti=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+2))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+2)))));
                    bgdMarUtiCos=(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+3))==null?null:BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(objTblCelRenLblColDinCanMarPreLisNew.getRowRender(), (intNumColIniPreReaMar+3)))));
                    if (bgdMarUti==null || bgdMarUti.compareTo(BigDecimal.ZERO)==0)
                    {
                        objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                    }
                    else{
                        if (bgdMarUtiCos==null || bgdMarUtiCos.compareTo(BigDecimal.ZERO)==0){
                            objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                        }
                        else{
                            if (bgdMarUti.compareTo(bgdMarUtiCos)<0){
                                objTblCelRenLblColDinCanMarPreLisNew.setBackground(new Color(255,151,151));
                            }
                            else{
                                objTblCelRenLblColDinCanMarPreLisNew.setBackground(javax.swing.UIManager.getColor("Table.background"));
                            }    
                        }
                    }
                }
            });
            
            for (int i=0; i<intNumColAddCanMarPreLisNew; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniCanMarPreLisNew+i);
                tbc.setHeaderValue("Mar.Pre.Lis.");
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(68);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinCanMarPreLisNew);
                tbc.setCellEditor(objTblCelEdiTxtCanMarPreLisNew);
                
                objTblCelEdiTxtCanMarPreLisNew.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        calcularPrecioListaCalculado_Margen();
                        
                        
                    }
                });
               
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);                               
            }
            intNumColFinCanMarPreLisNew=objTblMod.getColumnCount();

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
   private boolean cargarCabeceraIngresosImportacion(){
        boolean blnRes=true;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        try{
            conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conIngImp!=null){
                stmIngImp=conIngImp.createStatement();
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                    
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+="        AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                strSQL="";
                strSQL+=" SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="      , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="        , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+="   FROM(";
                //INIMPO
                strSQL+="        SELECT a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+="	      , c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                strSQL+="        FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1 ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                strSQL+="              INNER JOIN tbm_cabPedEmbImp AS d1";
                strSQL+="              ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc)";
                strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                strSQL+="        WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";
                strSQL+=strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+="	 GROUP BY a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+=" 	        , c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc2, c1.fe_doc";
                strSQL+="        UNION ALL ";
                //AJUSTE
                strSQL+="        SELECT a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c2.co_emprelpedembimp, c2.co_locrelpedembimp, c2.co_tipdocrelpedembimp, c2.co_docrelpedembimp ";     
                strSQL+="             , c2.co_emp AS co_empIngImp, c2.co_loc AS co_locIngImp, c2.co_tipDoc AS co_tipDocIngImp, c2.co_doc AS co_docIngImp, c2.tx_numDoc2 AS tx_numIngImp, c2.fe_doc AS fe_docIngImp";
                strSQL+="        FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1 ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                strSQL+="        INNER JOIN tbr_CabMovInv AS d1 ON c1.co_emp=d1.co_emp AND c1.co_loc=d1.co_loc AND c1.co_tipdoc=d1.co_tipDoc AND c1.co_doc=d1.co_doc";
                strSQL+="        INNER JOIN tbm_CabMovInv as c2 ON c2.co_emp=d1.co_empRel AND c2.co_loc=d1.co_locRel AND c2.co_tipdoc=d1.co_tipDocRel AND c2.co_doc=d1.co_docRel)";
                strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                strSQL+="        WHERE d1.st_reg='A'" ;
                strSQL+="        AND c1.co_tipDoc IN(select co_tipDoc from tbr_tipDocPrg where co_emp="+objParSis.getCodigoEmpresaGrupo()+"";
                strSQL+="                            and co_loc="+intCodLocGrp+" and co_mnu="+objImp.INT_COD_MNU_PRG_AJU_INV+")";
                strSQL+="        AND c1.st_reg IN ('A')"; //26Ene2018: Se agrega validación para que solo muestre costos de items de los ajustes activos, tbm_cabMovInv <>'O'. 
                strSQL+="        AND (CASE WHEN b1.st_Reg IS NULL THEN 'A' ELSE b1.st_Reg END ) NOT IN ('I') ";  //Uso del campo de tbm_DetMovInv.st_Reg para documentos de ajustes 21Ago2017
                strSQL+=strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+="        GROUP BY a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, c2.co_emprelpedembimp, c2.co_locrelpedembimp, c2.co_tipdocrelpedembimp, c2.co_docrelpedembimp"; 	
 	        strSQL+="               , c2.co_emp, c2.co_loc, c2.co_tipDoc, c2.co_doc, c2.tx_numDoc2, c2.fe_doc";
                strSQL+="   ) AS x";
                strSQL+="   GROUP BY x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="          , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+="   ORDER BY x.fe_docIngImp DESC LIMIT 5";
                strSQL+=") AS x";
                strSQL+=" ORDER BY x.fe_docIngImp ASC, x.tx_numIngImp";
                System.out.println("cargarCabeceraIngresosImportacion: " + strSQL);
                rstIngImp=stmIngImp.executeQuery(strSQL);
                arlDatIngImp.clear();
                for(int i=0; rstIngImp.next(); i++){
                    arlRegIngImp=new ArrayList();
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP,               "" + rstIngImp.getString("co_empIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC,               "" + rstIngImp.getString("co_locIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC,           "" + rstIngImp.getString("co_tipDocIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC,               "" + rstIngImp.getString("co_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_NUM,                   "" + rstIngImp.getString("tx_numIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegIngImp.add(INT_ARL_ING_IMP_EST_COL,               "");
                    arlRegIngImp.add(INT_ARL_ING_IMP_FEC,                   "" + rstIngImp.getString("fe_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP_PED_EMB,       "" + rstIngImp.getString("co_emprelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC_PED_EMB,       "" + rstIngImp.getString("co_locrelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC_PED_EMB,   "" + rstIngImp.getString("co_tipdocrelpedembimp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC_PED_EMB,       "" + rstIngImp.getString("co_docrelpedembimp"));
                    arlDatIngImp.add(arlRegIngImp);
                }
                conIngImp.close();
                conIngImp=null;
                rstIngImp.close();
                rstIngImp=null;
                stmIngImp.close();
                stmIngImp=null;
                System.out.println("fin--");
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }
 
   private boolean cargarCabeceraPedidosEmbarcados(){
        boolean blnRes=true;
        Connection conPedEmb;
        Statement stmPedEmb;
        ResultSet rstPedEmb;
        try{
            conPedEmb=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPedEmb!=null){
                stmPedEmb=conPedEmb.createStatement();
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                    
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+="        AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                              
                if (txtCodAltItmTer.getText().length()>0)
                {
                     strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }

                strSQL="";
                strSQL+="SELECT x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+=" , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+="   FROM(";
                strSQL+="          SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                strSQL+="         , e1.nd_can, e1.nd_preUni";
                strSQL+="         , e1.nd_cosUni, e1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                strSQL+="         , d1.co_emp AS co_empPedEmb, d1.co_loc AS co_locPedEmb, d1.co_tipdoc AS co_tipDocPedEmb, d1.co_doc AS co_docPedEmb";
                strSQL+="         , d1.tx_numDoc2 AS tx_numPedEmb, d1.fe_doc AS fe_docPedEmb, d1.fe_arr AS fe_arrPedEmb, d1.co_ctaAct, d1.co_ctaPas";
                strSQL+=" 	, c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp";
                strSQL+="         FROM (tbm_cabPedEmbImp AS d1 LEFT OUTER JOIN tbm_cabMovInv AS c1";
                strSQL+=" 	      ON d1.co_emp=c1.co_emprelpedembimp AND d1.co_loc=c1.co_locrelpedembimp AND d1.co_tipDoc=c1.co_tipdocrelpedembimp AND d1.co_doc=c1.co_docrelpedembimp";
                strSQL+=" 	      AND c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND c1.co_mnu IN("+objImp.INT_COD_MNU_PRG_ING_IMP+", "+objImp.INT_COD_MNU_PRG_COM_LOC+") )";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS e1 ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                strSQL+=" 	INNER JOIN (( tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm )";
                strSQL+=" 		    ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                strSQL+="         WHERE d1.st_reg='A'";
                strSQL+="" + strAux;                
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                
                strSQL+=" 	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                strSQL+=" 	, d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc";
                strSQL+=" 	, e1.nd_can, e1.nd_preUni, e1.nd_cosUni, e1.co_reg, a2.tx_desCor";
                strSQL+=" 	, d1.co_ctaAct, d1.co_ctaPas, d1.fe_arr";
                strSQL+=" 	, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, d1.tx_numDoc2, d1.fe_doc";
                strSQL+=" 	ORDER BY d1.fe_doc DESC";
                strSQL+="   ) AS x";
                strSQL+="   WHERE x.co_empIngImp IS NULL";
                strSQL+="   GROUP BY x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.tx_numPedEmb, x.fe_docPedEmb, x.fe_arrPedEmb";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp";
                strSQL+="   ORDER BY x.fe_docPedEmb DESC LIMIT 5";
                strSQL+=") AS x";
                strSQL+=" ORDER BY x.fe_docPedEmb ASC, x.tx_numPedEmb";
                
                
                System.out.println("cargarCabeceraPedidosEmbarcados: " + strSQL);
                rstPedEmb=stmPedEmb.executeQuery(strSQL);
                arlDatPedEmb.clear();
                for(int i=0; rstPedEmb.next(); i++){
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + rstPedEmb.getString("co_empPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + rstPedEmb.getString("co_locPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + rstPedEmb.getString("co_tipDocPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + rstPedEmb.getString("co_docPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + rstPedEmb.getString("tx_numPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + rstPedEmb.getString("fe_arrPedEmb"));//quite el valor de la fecha del documento y puse Fecha de arribo
                    arlDatPedEmb.add(arlRegPedEmb);
                    //Margen
                    arlRegPedEmb=new ArrayList();
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_EMP,               "" + rstPedEmb.getString("co_empPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_LOC,               "" + rstPedEmb.getString("co_locPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_TIP_DOC,           "" + rstPedEmb.getString("co_tipDocPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COD_DOC,               "" + rstPedEmb.getString("co_docPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_NUM,                   "" + rstPedEmb.getString("tx_numPedEmb"));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegPedEmb.add(INT_ARL_PED_EMB_EST_COL,               "");
                    arlRegPedEmb.add(INT_ARL_PED_EMB_FEC,                   "" + rstPedEmb.getString("fe_arrPedEmb"));//quite el valor de la fecha del documento y puse Fecha de arribo
                    arlDatPedEmb.add(arlRegPedEmb);
                }                
                conPedEmb.close();
                conPedEmb=null;
                rstPedEmb.close();
                rstPedEmb=null;
                stmPedEmb.close();
                stmPedEmb=null;
                
                System.out.println("arlDatPedEmb: " + arlDatPedEmb.toString());
                
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }

   private boolean cargarCabeceraNotasPedidos(){
        boolean blnRes=true;
        Connection conNotPed;
        Statement stmNotPed;
        ResultSet rstNotPed;
        try{
            conNotPed=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNotPed!=null){
                stmNotPed=conNotPed.createStatement();
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND b1.co_itm=" + txtCodItm.getText()  + "";
                    
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+="        AND ((LOWER(b1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                strSQL="";
                strSQL+="SELECT x.co_empNotPed";
                strSQL+=" , x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.tx_nomMesArr, x.co_ctaAct, x.co_ctaPas";
                strSQL+=" , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_empNotPed";
                strSQL+="   , x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.tx_nomMesArr, x.co_ctaAct, x.co_ctaPas";
                strSQL+="   , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                strSQL+="   FROM(";
                strSQL+=" 	SELECT b1.co_empNotPed, b1.co_locNotPed, b1.co_tipDocNotPed, b1.co_docNotPed";
                strSQL+=" 	, b1.tx_numNotPed, b1.fe_docNotPed, b1.tx_nomMesArr, b1.co_ctaAct, b1.co_ctaPas";
                strSQL+=" 	, b2.co_empPedEmb, b2.co_locPedEmb, b2.co_tipDocPedEmb, b2.co_docPedEmb, b1.st_notpedlis, b1.st_cie";
                strSQL+=" 	FROM(";
                strSQL+=" 		 SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                strSQL+=" 		, e1.nd_can, e1.nd_preUni";
                strSQL+=" 		, e1.nd_cosUni, e1.co_reg AS co_regNotPed, a2.tx_desCor AS tx_desCorUniMed";
                strSQL+=" 		, d1.co_emp AS co_empNotPed, d1.co_loc AS co_locNotPed, d1.co_tipdoc AS co_tipDocNotPed, d1.co_doc AS co_docNotPed";
                strSQL+=" 		, d1.tx_numDoc2 AS tx_numNotPed, d1.fe_doc AS fe_docNotPed, g1.tx_deslar AS tx_nomMesArr, d1.co_ctaAct, d1.co_ctaPas";
                strSQL+=" 		, d1.st_notpedlis, d1.st_cie";
                strSQL+=" 		FROM (tbm_cabNotPedImp AS d1 LEFT OUTER JOIN tbm_mesembimp AS g1 ON d1.co_mesarr=g1.co_mesemb)";
                strSQL+=" 		INNER JOIN (tbm_detNotPedImp AS e1 INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg) ";
                strSQL+=" 							ON e1.co_emp=a1.co_emp AND e1.co_itm=a1.co_itm";
                strSQL+=" 							INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                strSQL+=" 							)";
                strSQL+=" 		ON d1.co_emp=e1.co_emp AND d1.co_loc=e1.co_loc AND d1.co_tipDoc=e1.co_tipDoc AND d1.co_doc=e1.co_doc";
                strSQL+=" 		WHERE d1.st_reg='A'";
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                
                if (txtCodAltItmTer.getText().length()>0)
                {
                     strSQL+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                strSQL+=" 	) AS b1";
                strSQL+=" 	LEFT OUTER JOIN(";
                strSQL+=" 		SELECT c1.co_emp AS co_empPedEmb, c1.co_loc AS co_locPedEmb, c1.co_tipDoc AS co_tipDocPedEmb, c1.co_doc AS co_docPedEmb";
                strSQL+=" 		, e2.co_locRel, e2.co_tipDocRel, e2.co_docRel, e2.co_regRel";
                strSQL+=" 		FROM tbm_cabPedEmbImp AS c1 INNER JOIN tbm_detPedEmbImp AS e2";
                strSQL+=" 		ON c1.co_emp=e2.co_emp AND c1.co_loc=e2.co_loc AND c1.co_tipDoc=e2.co_tipDoc AND c1.co_doc=e2.co_doc";
                strSQL+=" 		WHERE  c1.st_reg='A'";
                strSQL+=" 	) AS b2";
                strSQL+="	ON b1.co_empNotPed=b2.co_empPedEmb AND b1.co_locNotPed=b2.co_locRel AND b1.co_tipDocNotPed=b2.co_tipDocRel AND b1.co_docNotPed=b2.co_docRel AND b1.co_regNotPed=b2.co_regRel";
                strSQL+="	WHERE b1.st_cie NOT IN('S')";
                strSQL+="" + strAux;
                strSQL+=" 	GROUP BY b1.co_empNotPed, b1.co_locNotPed, b1.co_tipDocNotPed, b1.co_docNotPed";
                strSQL+=" 	, b1.tx_numNotPed, b1.fe_docNotPed, b1.tx_nomMesArr, b1.co_ctaAct, b1.co_ctaPas";
                strSQL+=" 	, b2.co_empPedEmb, b2.co_locPedEmb, b2.co_tipDocPedEmb, b2.co_docPedEmb, b1.st_notpedlis, b1.st_cie";
                strSQL+=" 	ORDER BY b1.fe_docNotPed DESC";
                strSQL+="   ) AS x";
                strSQL+="   WHERE x.co_empPedEmb IS NULL";
                strSQL+="   GROUP BY x.co_empNotPed";
                strSQL+="   , x.co_locNotPed, x.co_tipDocNotPed, x.co_docNotPed, x.tx_numNotPed, x.fe_docNotPed, x.tx_nomMesArr, x.co_ctaAct, x.co_ctaPas";
                strSQL+="   , x.co_empPedEmb, x.co_locPedEmb, x.co_tipDocPedEmb, x.co_docPedEmb, x.st_notpedlis";
                strSQL+="   ORDER BY x.fe_docNotPed DESC LIMIT 5";
                strSQL+=" ) AS x";
                strSQL+=" ORDER BY x.fe_docNotPed ASC, x.tx_numNotPed";
                System.out.println("cargarCabeceraNotasPedidos: " + strSQL);
                rstNotPed=stmNotPed.executeQuery(strSQL);
                arlDatNotPed.clear();
                for(int i=0; rstNotPed.next(); i++){
                    arlRegNotPed=new ArrayList();
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_EMP,               "" + rstNotPed.getString("co_empNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_LOC,               "" + rstNotPed.getString("co_locNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_TIP_DOC,           "" + rstNotPed.getString("co_tipDocNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_DOC,               "" + rstNotPed.getString("co_docNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_NUM,                   "" + rstNotPed.getString("tx_numNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegNotPed.add(INT_ARL_NOT_PED_EST_COL,               "");
                    arlRegNotPed.add(INT_ARL_NOT_PED_FEC,                   "" + rstNotPed.getString("tx_nomMesArr"));//quite la fecha del documento "fe_docNotPed" para poner el mes de arribo "tx_nomMesArr"
                    arlDatNotPed.add(arlRegNotPed);
                    //Margen
                    arlRegNotPed=new ArrayList();
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_EMP,               "" + rstNotPed.getString("co_empNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_LOC,               "" + rstNotPed.getString("co_locNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_TIP_DOC,           "" + rstNotPed.getString("co_tipDocNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COD_DOC,               "" + rstNotPed.getString("co_docNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_NUM,                   "" + rstNotPed.getString("tx_numNotPed"));
                    arlRegNotPed.add(INT_ARL_NOT_PED_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegNotPed.add(INT_ARL_NOT_PED_EST_COL,               "");
                    arlRegNotPed.add(INT_ARL_NOT_PED_FEC,                   "" + rstNotPed.getString("tx_nomMesArr"));//quite la fecha del documento "fe_docNotPed" para poner el mes de arribo "tx_nomMesArr"
                    arlDatNotPed.add(arlRegNotPed);
                }                
                conNotPed.close();
                conNotPed=null;
                rstNotPed.close();
                rstNotPed=null;
                stmNotPed.close();
                stmNotPed=null;
                
                System.out.println("arlDatNotPed: " + arlDatNotPed.toString());
                
            }            
        }
        catch(Exception e){
            blnRes=false;
        }
        return blnRes;
    }


   /**
    * precio venta real kilo
    * precio objetivo kilo
    * precio lista calculado
    * @return 
    */
    boolean ocultaColumnasPrecios(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinPre-1); j>=intNumColIniPre; j--){
                if(chkMosValKil.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    boolean ocultaColumnasMargenPrecio(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinCanMarPreLisNew-1); j>=intNumColIniCanMarPreLisNew; j--){
                if(chkMosValKil.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
   
   /**
    * factor costo
    * precio venta factor
    * margen con factor
    * @return 
    */
    boolean ocultaColumnasFactor(){
        boolean blnRes=true;
        try{
            for(int j=(intNumColFinMarFac-1); j>=intNumColIniMarFac; j--){
                if(chkMosValFac.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }
            for(int j=(intNumColFinFac-1); j>=intNumColIniFac; j--){
                if(chkMosValFac.isSelected())
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                else
                    objTblMod.addSystemHiddenColumn(j, tblDat);
            }            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    boolean ocultaColumnasCostoUnitario(){
        boolean blnRes=true;
        try{            
            for(int j=(intNumColFinCosUni-1); j>=intNumColIniCosUni; j--){
                objTblMod.addSystemHiddenColumn(j, tblDat);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    boolean encerarColumnasCostoUnitario(){
        boolean blnRes=true;
//        try{
//            
//            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
//                for(int j=(intNumColFinCosUni-1); j>=intNumColIniCosUni; j--){
//                    objTblMod.setValueAt(null, i, j);
//                }
//            }
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
        return blnRes;
    }
    
    
    
    
    private boolean calcularValoresKilos(int intTipOpeCalKil){
        boolean blnRes=true;
        BigDecimal bgdPesUni=new BigDecimal("0");
        BigDecimal bgdCosUni=new BigDecimal("0");
        BigDecimal bgdIngImp=new BigDecimal("0");
        BigDecimal bgdPedEmb=new BigDecimal("0");
        BigDecimal bgdNotPed=new BigDecimal("0");
        BigDecimal bgdMarNotPed=new BigDecimal("0");
        
        BigDecimal bgdPreVtaFac=new BigDecimal("0");
        BigDecimal bgdPreVtaReaObjLisKil=new BigDecimal("0");
        
        try{
            if(chkMosValKil.isSelected()){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                    //ingresos por importacion
                    for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                        bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdIngImp.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //pedido embarcado
                    for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                        bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdPedEmb.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }                    
                    //nota de pedido
                    for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                        bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdNotPed.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    
//                    //margen de nota de pedido
//                    bgdMarNotPed=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinNotPed-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinNotPed-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinNotPed-1)).toString()));
//                    for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
//                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
//                            objTblMod.setValueAt(null, i, j);
//                        else{
//                            objTblMod.setValueAt(bgdMarNotPed.divide(bgdPesUni, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP), i, j);
//                        }
//                    }
                    
                    //costo unitario
                    for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                        bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                            objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                        else
                            objTblMod.setValueAt(bgdCosUni.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                    }
                    
                    //precio venta factura(factor)
                    for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                        if(j==(intNumColIniFac+1)){
                            bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(bgdPreVtaFac.divide(bgdPesUni, objParSis.getDecimalesBaseDatos()), i, j);
                        }
                    }
//                    //precio venta real kilo, precio objetivo kilo, precio lista calculado
//                    for(int j=intNumColIniPre; j<intNumColFinPre;j++){
//                        if(j<(intNumColFinPre-1)){
//                            bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
//                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
//                            else
//                                objTblMod.setValueAt(bgdPreVtaReaObjLisKil.divide(bgdPesUni, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP), i, j);
//                        }
//
//                    }
                }
            }
            else{
                System.out.println("intTipOpeCalKil: " + intTipOpeCalKil);
                if(intTipOpeCalKil==1){//solo se valida si no esta seleccionado porque si no esta seleccionado no debe reversar nada porque los valores a mostrar deben ser los obtenidos en la DB
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        bgdPesUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));

                        //ingresos por importacion
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdIngImp.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //pedido embarcado
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPedEmb.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }                    
                        //nota de pedido
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdNotPed.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }
                        
//                        //margen de nota de pedido
//                        bgdMarNotPed=new BigDecimal(objTblMod.getValueAt(i, (intNumColFinNotPed-1))==null?"0":(objTblMod.getValueAt(i, (intNumColFinNotPed-1)).toString().equals("")?"0":objTblMod.getValueAt(i, (intNumColFinNotPed-1)).toString()));
//                        for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
//                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
//                                objTblMod.setValueAt(null, i, j);
//                            else{
//                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdMarNotPed.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
//                            }
//                        }
                        
                        
                        //costo unitario
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdCosUni.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                        }

                        //precio venta factura(factor)
                        for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                            if(j==(intNumColIniFac+1)){
                                bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                                else
                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaFac.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
//                        //precio venta real kilo, precio objetivo kilo, precio lista calculado
//                        for(int j=intNumColIniPre; j<intNumColFinPre;j++){
//                            if(j<(intNumColFinPre-1)){
//                                bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
//                                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0)
//                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
//                                else
//                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaReaObjLisKil.multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, j);
//                            }
//                        }
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
    
    
    /**
     * Función que permite recalcular los valores si hay un cambio en la columna del peso
     * @return 
     */
    private boolean calcularReversionValoresKilos(){
        boolean blnRes=true;
        BigDecimal bgdPesUniOld=new BigDecimal("0");
        BigDecimal bgdPesUniNew=new BigDecimal("0");
        
        BigDecimal bgdCosUni=new BigDecimal("0");
        BigDecimal bgdIngImp=new BigDecimal("0");
        BigDecimal bgdPedEmb=new BigDecimal("0");
        BigDecimal bgdNotPed=new BigDecimal("0");
        
        BigDecimal bgdPreVta=new BigDecimal("0");
        BigDecimal bgdPreVtaFac=new BigDecimal("0");
        //BigDecimal bgdPreVtaReaKil=new BigDecimal("0"), bgdPreVtaObjKil=new BigDecimal("0"), bgdPreVtaLisCal=new BigDecimal("0");
        BigDecimal bgdPreVtaReaObjLisKil=new BigDecimal("0");
        try{
            if(chkMosValKil.isSelected()){
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bgdPesUniOld=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR_AUX).toString()));
                    bgdPesUniNew=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR).toString()));
                    if(bgdPesUniOld.compareTo(bgdPesUniNew)!=0){//peso db es diferente a peso tbl
                     
                        //ingresos por importacion
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdIngImp.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);//SE REVERSA
                                bgdIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdIngImp.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);//SE RECALCULA
                            }                            
                        }                    
                        //pedido embarcado
                        for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                            bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPedEmb.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdPedEmb=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdPedEmb.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //nota de pedido
                        for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                            bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdNotPed.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdNotPed=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdNotPed.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //costo unitario
                        for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                            bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                            else{
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bgdCosUni.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                bgdCosUni=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                objTblMod.setValueAt(bgdCosUni.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                            }
                        }
                        
                        //precio venta factura(factor)
                        for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                            if(j==(intNumColIniFac+1)){
                                bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                                else{
                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaFac.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                    bgdPreVtaFac=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                    objTblMod.setValueAt(bgdPreVtaFac.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                                }
                            }
                        }
                        
                        //precio venta real kilo, precio objetivo kilo, precio lista calculado
                        for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                            if(j==(intNumColIniPre+1)){
                                bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                if (bgdPesUniNew==null || bgdPesUniNew.compareTo(BigDecimal.ZERO)==0)
                                    objTblMod.setValueAt(null, i, j);//puede existir valor de precio de venta pero como peso unitario es cero entonces CERO
                                else{
                                    objTblMod.setValueAt(objUti.redondearBigDecimal(bgdPreVtaReaObjLisKil.multiply(bgdPesUniOld), objParSis.getDecimalesBaseDatos()), i, j);
                                    bgdPreVtaReaObjLisKil=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                    objTblMod.setValueAt(bgdPreVtaReaObjLisKil.divide(bgdPesUniNew, objParSis.getDecimalesBaseDatos()), i, j);
                                }
                            }
                        }
                        
                        //se actualiza el peso auxiliar con el nuevo peso ingresado
                    objTblMod.setValueAt(bgdPesUniNew, i, INT_TBL_DAT_PES_KGR_AUX);
                    
                    
                        
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
  
    
    /**
     * Esta función calcula el costo unitario con factor de la fila.
     */
    private void calcularCosUniFac()
    {
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdFacCosUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniFac)));
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniFac+1));
        }
    }
    
    
    
    
    
    /**
     * Esta función calcula el costo unitario con factor de toda la tabla
     */
    private void calcularCosUniFacTbl()
    {
        BigDecimal bgdCosUni, bgdFacCosUni;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdFacCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniFac)));
            System.out.println("bgdCosUni: " + bgdCosUni);
            System.out.println("bgdFacCosUni: " + bgdFacCosUni);
            objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdCosUni.multiply(bgdFacCosUni), objParSis.getDecimalesBaseDatos()), i, (intNumColIniFac+1));
        }
    }
    
    
    
    
    /**
     * Función que permite calcular el margen con factor de la fila seleccionada.
     * Este valor debe recalcularse cuando se modifica el factor del costo
     */
    private void calcularMarFac(){
        
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdPreVtaMarUtiPreVta;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdPreVtaMarUtiPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniFac+1))));
            
            if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniMarFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniMarFac);
                }
                else{
                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniMarFac);
                }
            }
        }
    }
    
    
    /**
     * Función que permite calcular el margen con factor de toda la tabla
     * Este valor debe recalcularse cuando se modifica el factor del costo
     */
    private void calcularMarFacTbl(){
        BigDecimal bgdCosUni, bgdPreVtaMarUtiPreVta;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdPreVtaMarUtiPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniFac+1))));
            
            if (bgdPreVtaMarUtiPreVta==null || bgdPreVtaMarUtiPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniMarFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniMarFac);
                }
                else{
                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, intNumColIniMarFac);
                }
            }
        }
    }
    
    
    /**
     * Función que permite calcular el margen de lista.
     * Este valor debe recalcularse cuando se modifica el precio objetivo kilo
     */
    private void calcularMarPreObjKil(){
        
//        int intFilSel[], i;
//        BigDecimal bgdCosUni, bgdPreVtaLis;
//        intFilSel=tblDat.getSelectedRows();
//        for (i=0; i<intFilSel.length; i++){
//            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
//            bgdPreVtaLis=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniPre+1))));
//            
//            if (bgdPreVtaLis==null || bgdPreVtaLis.compareTo(BigDecimal.ZERO)==0){
//                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+2));
//            }
//            else{
//                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
//                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+2));
//                }
//                else{
//                    bgdPreVtaMarUtiPreVta=bgdPreVtaMarUtiPreVta.multiply(BigDecimal.valueOf(0.75));
//                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaMarUtiPreVta.subtract(bgdCosUni)).divide(bgdPreVtaMarUtiPreVta, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesMostrar()), intFilSel[i], (intNumColIniFac+2));
//                }
//            }
//        }
    }
    
    
    /**
     * Función que permite calcular el margen de la nota de pedido mas reciente.
     * 
     */
//    private void calcularMarNotPed(){
//        int i;
//        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
//        if(arlDatNotPed.size()>0){
//            for (i=0; i<objTblMod.getRowCountTrue(); i++){
//                bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColFinNotPed-1))));
//                bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
//                bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
//
//                if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
//                    objTblMod.setValueAt(null, i, );//intNumColIniMarNotPed
//                }
//                else{
//                    if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
//                        objTblMod.setValueAt(null, i, );//intNumColIniMarNotPed
//                    }
//                    else{
//                        if(chkMosValKil.isSelected())
//                            bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
//                        else
//                            bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
//                        objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, );//intNumColIniMarNotPed
//                    }
//                }
//            }
//        }
//
//    }
    private void calcularMarNotPed(){
        int k=0;
        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
        if(arlDatNotPed.size()>0){
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                k=0;
                for(int j=intNumColIniNotPed; j<intNumColFinNotPed; j++){
                    if(!((k%2)==0)){//devuelve impar
                        bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (j-1))));//obtener el costo
                        bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                        bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                            }
                            else{
                                if(chkMosValKil.isSelected())
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                                else
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, j);//intNumColIniMarNotPed
                            }
                        }
                    }
                    k++;
                }
            }
        }

    }
    
    
    
    
    /**
     * Función que permite calcular el margen del pedido embarcado mas reciente.
     * 
     */
    private void calcularMarPedEmb(){
        int k=0;
        BigDecimal bgdCosUni, bgdPreVta, bgdPesUni;
        if(arlDatPedEmb.size()>0){
            for (int i=0; i<objTblMod.getRowCountTrue(); i++){
                k=0;
                for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb; j++){
                    if(!((k%2)==0)){//devuelve impar
                        bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (j-1))));//obtener el costo
                        bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPreReaMar)));
                        bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

                        if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                            objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                        }
                        else{
                            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                                objTblMod.setValueAt(null, i, j);//intNumColIniMarNotPed
                            }
                            else{
                                if(chkMosValKil.isSelected()){
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                                }
                                else{
                                    bgdPreVta=bgdPreVta.multiply(BigDecimal.valueOf(0.75));
                                }
                                objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVta.subtract(bgdCosUni)).divide(bgdPreVta, objParSis.getDecimalesBaseDatos())).multiply(BigDecimal.valueOf(100)), objParSis.getDecimalesBaseDatos()), i, j);//intNumColIniMarNotPed
                            }
                        }
                    }
                    k++;
                }
            }
        }

    }
    
    
    
    private void calcularPreVtaReaKil(){
        int intFilSel[], i;
        BigDecimal bgdPesUni, bgdPreVta;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            bgdPreVta=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPreReaMar)));
            
            if (bgdPreVta==null || bgdPreVta.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPreReaMar+1));
                }
                else{
                    objTblMod.setValueAt("" + ((objUti.redondearBigDecimal((bgdPreVta.multiply(BigDecimal.valueOf(0.75))), objParSis.getDecimalesBaseDatos()).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos())), intFilSel[i], (intNumColIniPreReaMar+1));
                }
            }
        }
    }
        
    
    private void calcularPreLisCal(){
        int intFilSel[], i;
        BigDecimal bgdPesUni, bgdPreVtaObj, bgdPreLisCal, bgdFacIva;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            bgdPreVtaObj=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniPre)));
            bgdPreLisCal=new BigDecimal("0");
            
            if (bgdPreVtaObj==null || bgdPreVtaObj.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
                }
                else{
                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj.multiply(new BigDecimal("100")));
                    bgdPreLisCal=bgdPreLisCal.multiply(bgdPesUni);
                    bgdFacIva=objUti.redondearBigDecimal( (objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    bgdPreLisCal=bgdPreLisCal.divide( (bgdFacIva.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdPreLisCal, objParSis.getDecimalesBaseDatos()), intFilSel[i], (intNumColIniPre+1));
                }
            }
        }
    }
    
    
    private void calcularPreLisCalConsulta(){
        BigDecimal bgdPesUni, bgdPreVtaObj, bgdPreLisCal, bgdFacIva;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
            bgdPreVtaObj=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniPre)));
            bgdPreLisCal=new BigDecimal("0");
            
            if (bgdPreVtaObj==null || bgdPreVtaObj.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniPre+1));
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniPre+1));
                }
                else{
                    //objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreVtaObj.divide(bdePorIva, objParSis.getDecimalesBaseDatos())).divide(BigDecimal.valueOf(0.75), objParSis.getDecimalesBaseDatos())).multiply(bgdPesUni), objParSis.getDecimalesBaseDatos()), i, (intNumColIniPre+1));
                    bgdPreLisCal=bgdPreLisCal.add(bgdPreVtaObj.multiply(new BigDecimal("100")));
                    bgdPreLisCal=bgdPreLisCal.multiply(bgdPesUni);
                    bgdFacIva=objUti.redondearBigDecimal( (objParSis.getPorcentajeIvaCompras().add(BigDecimal.valueOf(100))), objParSis.getDecimalesMostrar() );
                    bgdPreLisCal=bgdPreLisCal.divide( (bgdFacIva.multiply(new BigDecimal("0.75")))  , objParSis.getDecimalesBaseDatos());
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(bgdPreLisCal, objParSis.getDecimalesBaseDatos()), i, (intNumColIniPre+1));
                }
            }
        }
        
    }
    
    
    
    
    
    
    /**
     * Función que permite calcular el precio de lista basado en el margen que ingrese el usuario de la fila seleccionada
     */
    private void calcularPrecioListaCalculado_Margen(){
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdMarPreLis, bgdPreLisCal, bgdPesUni;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdMarPreLis=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCanMarPreLisNew)));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));
            
            //bgdPreLisCal=objUti.redondearBigDecimal((bgdCosUni.divide((BigDecimal.valueOf(0.75).multiply(new BigDecimal("1").subtract(bgdMarPreLis.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP )))), BigDecimal.ROUND_HALF_UP)), objParSis.getDecimalesBaseDatos());
            
            bgdPreLisCal=BigDecimal.valueOf(0.0);
            bgdPreLisCal=bgdPreLisCal.add( bgdMarPreLis.multiply(new BigDecimal("0.01"))    );
            bgdPreLisCal=BigDecimal.valueOf(1).subtract(bgdPreLisCal);
            bgdPreLisCal=BigDecimal.valueOf(0.75).multiply(bgdPreLisCal);
            bgdPreLisCal=bgdCosUni.divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos());
            bgdPreLisCal=objUti.redondearBigDecimal((bgdPreLisCal.multiply(bgdPesUni)), objParSis.getDecimalesBaseDatos());
                   
            //precio lista calculado
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
            }
            else{
                if (bgdMarPreLis==null || bgdMarPreLis.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniPre+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdPreLisCal, intFilSel[i], (intNumColIniPre+1));
                }
            }

            //precio objetivo en kilos
            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniPre);
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniPre);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniPre);
                }
            }                
        }
    }
    
    
    /**
     * Función que permite calcular el precio de lista basado en el margen que ingrese el usuario de toda la tabla
     */
    private void calcularPrecioListaCalculado_MargenTbl(){
        BigDecimal bgdCosUni, bgdMarPreLis, bgdPreLisCal, bgdPesUni;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdMarPreLis=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCanMarPreLisNew)));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));
            
            bgdPreLisCal=BigDecimal.valueOf(0.0);
            bgdPreLisCal=bgdPreLisCal.add( bgdMarPreLis.multiply(new BigDecimal("0.01"))    );
            bgdPreLisCal=BigDecimal.valueOf(1).subtract(bgdPreLisCal);
            bgdPreLisCal=BigDecimal.valueOf(0.75).multiply(bgdPreLisCal);
            bgdPreLisCal=bgdCosUni.divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos());
            bgdPreLisCal=objUti.redondearBigDecimal((bgdPreLisCal.multiply(bgdPesUni)), objParSis.getDecimalesBaseDatos());
                   
            //precio lista calculado
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniPre+1));
            }
            else{
                if (bgdMarPreLis==null || bgdMarPreLis.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniPre+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdPreLisCal, i, (intNumColIniPre+1));
                }
            }

            //precio objetivo en kilos
            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniPre);
            }
            else{
                if (bgdPesUni==null || bgdPesUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniPre);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  (bgdPreLisCal.multiply(BigDecimal.valueOf(0.75)).multiply(bdePorIva)).divide(bgdPesUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), i, intNumColIniPre);
                }
            }                
        }
    }
    
    

    /**
     * Función que permite calcular el margen según el precio de lista calculado
     */
    private void calcularMarPreLisCal(){
        int i;
        BigDecimal bgdCosUni, bgdPreLisCal, bgdPesUni;
        for (i=0; i<objTblMod.getRowCountTrue(); i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdPreLisCal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, (intNumColIniPre+1))));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_KGR)));

            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniCanMarPreLisNew);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniCanMarPreLisNew);
                }
                else{
                    if(chkMosValKil.isSelected())
                        bgdPreLisCal=(bgdPreLisCal.multiply(BigDecimal.valueOf(0.75))).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                    else
                        bgdPreLisCal=bgdPreLisCal.multiply(BigDecimal.valueOf(0.75));
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreLisCal.subtract(bgdCosUni)).multiply(BigDecimal.valueOf(100)).divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos())), objParSis.getDecimalesBaseDatos()), i, intNumColIniCanMarPreLisNew);
                    
                    
                    
                }
            }
        }
    }
    

    

    /**
     * Función que permite calcular el margen según el precio de lista calculado
     */
    private void calcularMarPreLisCal_row(){
        int intFilSel[], i;
        intFilSel=tblDat.getSelectedRows();
        BigDecimal bgdCosUni, bgdPreLisCal, bgdPesUni;
        for (i=0; i<intFilSel.length; i++){
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdPreLisCal=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], (intNumColIniPre+1))));
            bgdPesUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], INT_TBL_DAT_PES_KGR)));

            if (bgdPreLisCal==null || bgdPreLisCal.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniCanMarPreLisNew);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniCanMarPreLisNew);
                }
                else{
                    if(chkMosValKil.isSelected())
                        bgdPreLisCal=(bgdPreLisCal.multiply(BigDecimal.valueOf(0.75))).divide(bgdPesUni, objParSis.getDecimalesBaseDatos());
                    else
                        bgdPreLisCal=bgdPreLisCal.multiply(BigDecimal.valueOf(0.75));
                    
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal(((bgdPreLisCal.subtract(bgdCosUni)).multiply(BigDecimal.valueOf(100)).divide(bgdPreLisCal, objParSis.getDecimalesBaseDatos())), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniCanMarPreLisNew);
                }
            }
        }
    }
    

    /**
     * Función que permite calcular el costo unitario con factor basado en el margen 
     * que ingrese el usuario de la fila seleccionada
     */
    private void calcularCostoUnitarioFactor_Margen(){
        int intFilSel[], i;
        BigDecimal bgdCosUni, bgdMarFac, bgdCosUniFac;
        intFilSel=tblDat.getSelectedRows();
        for (i=0; i<intFilSel.length; i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniCosUni)));
            bgdMarFac=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(intFilSel[i], intNumColIniMarFac)));
            
            bgdCosUniFac=BigDecimal.valueOf(0.0);
            bgdCosUniFac=bgdCosUniFac.add( bgdMarFac.multiply(new BigDecimal("0.01"))    );
            bgdCosUniFac=BigDecimal.valueOf(1).subtract(bgdCosUniFac);
            bgdCosUniFac=BigDecimal.valueOf(0.75).multiply(bgdCosUniFac);
            bgdCosUniFac=bgdCosUni.divide(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
            bgdCosUniFac=objUti.redondearBigDecimal(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
                   
            //costo unitario con factor
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+1));
            }
            else{
                if (bgdMarFac==null || bgdMarFac.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], (intNumColIniFac+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdCosUniFac, intFilSel[i], (intNumColIniFac+1));
                }
            }

            //factor costo
            if (bgdCosUniFac==null || bgdCosUniFac.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, intFilSel[i], intNumColIniFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, intFilSel[i], intNumColIniFac);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  bgdCosUniFac.divide(bgdCosUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), intFilSel[i], intNumColIniFac);
                }
            }                
        }
    }

    /**
     * Función que permite calcular el costo unitario con factor basado en el margen 
     * que ingrese el usuario de toda la tabla
     */
    private void calcularCostoUnitarioFactor_MargenTbl(){
        BigDecimal bgdCosUni, bgdMarFac, bgdCosUniFac;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            //columna precio lista calculado
            bgdCosUni=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniCosUni)));
            bgdMarFac=BigDecimal.valueOf(objUti.parseDouble(objTblMod.getValueAt(i, intNumColIniMarFac)));
            
            bgdCosUniFac=BigDecimal.valueOf(0.0);
            bgdCosUniFac=bgdCosUniFac.add( bgdMarFac.multiply(new BigDecimal("0.01"))    );
            bgdCosUniFac=BigDecimal.valueOf(1).subtract(bgdCosUniFac);
            bgdCosUniFac=BigDecimal.valueOf(0.75).multiply(bgdCosUniFac);
            bgdCosUniFac=bgdCosUni.divide(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
            bgdCosUniFac=objUti.redondearBigDecimal(bgdCosUniFac, objParSis.getDecimalesBaseDatos());
                   
            //costo unitario con factor
            if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, (intNumColIniFac+1));
            }
            else{
                if (bgdMarFac==null || bgdMarFac.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, (intNumColIniFac+1));
                }
                else{
                    objTblMod.setValueAt("" + bgdCosUniFac, i, (intNumColIniFac+1));
                }
            }

            //factor costo
            if (bgdCosUniFac==null || bgdCosUniFac.compareTo(BigDecimal.ZERO)==0){
                objTblMod.setValueAt(null, i, intNumColIniFac);
            }
            else{
                if (bgdCosUni==null || bgdCosUni.compareTo(BigDecimal.ZERO)==0){
                    objTblMod.setValueAt(null, i, intNumColIniFac);
                }
                else{
                    objTblMod.setValueAt("" + objUti.redondearBigDecimal((  bgdCosUniFac.divide(bgdCosUni, objParSis.getDecimalesBaseDatos()) ), objParSis.getDecimalesBaseDatos()), i, intNumColIniFac);
                }
            }                
        }
    }
    
    
    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if(isItmModDat()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        return true;
    }
    
    
    /**
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veces
     * <BR> false: Caso contrario
     */
    private boolean isItmModDat(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                System.out.println("i: " + i);
                System.out.println("1: " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ALT));
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
                    System.out.println("j: " + j);
                    System.out.println("2: " + objTblMod.getValueAt(j,INT_TBL_DAT_COD_ALT));
                    if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ALT, j, INT_TBL_DAT_COD_ALT)){
                        blnRes=true;
                        break;
                    }

                }
                if(blnRes)
                    break;
            }            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
}