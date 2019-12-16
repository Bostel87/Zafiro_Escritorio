/*
 * ZafCom14.java
 *
 * Created on 15 de junio de 2006, 10:10 PM
 */

package Compras.ZafCom14;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom14 extends javax.swing.JInternalFrame 
{
    //Constantes: 
    //Jtable: tblBod
    private static final int INT_TBL_BOD_LIN=0;                         //Línea.
    private static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    private static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    private static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    private static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    private static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    //Jtable: TblDat 
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    private static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    private static final int INT_TBL_DAT_COD_ALT=3;                     //Código del alterno del ítem.
    private static final int INT_TBL_DAT_COD_ALT_DOS=4;                 //Código del alterno 2 del ítem.
    private static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    private static final int INT_TBL_DAT_DEC_UNI=6;                     //Descripción corta de la unidad de medida.
    private static final int INT_TBL_DAT_CDI_STK=7;                     //Columna dinámica: Stock.

    //Constantes para manejar Columnas Dinámicas.
    private static final int INT_TBL_DAT_NUM_TOT_CES=7;                 //Número total de columnas estáticas.
    private static final int INT_TBL_DAT_FAC_NUM_CDI=1;                 //Factor para calcular total de columnas dinámicas. 
    
    private static final int INT_NUM_COL_ADI_TBL_DATGRP=3;              //Cantidad de Columnas Adicionales por Grupo.   [Stock-Disponible-Sobrante] 
    private static final int INT_NUM_COL_ADI_TBL_DATEMP=2;              //Cantidad de Columnas Adicionales por Empresa. [Stock-Disponible]    

    //Arraylist: Datos Bodega
    private ArrayList arlRegBod, arlDatBod;
    static final int INT_ARL_COD_EMP=0;
    static final int INT_ARL_NOM_EMP=1;
    static final int INT_ARL_COD_BOD=2;
    static final int INT_ARL_NOM_BOD=3;
    static final int INT_ARL_EST_BOD=4;
    static final int INT_ARL_STK_SOB=5;
    
    //Arraylist: Datos Grupo
    private ArrayList arlRegGrp, arlDatGrp;
    final int INT_ARL_COL_ADD_COD_EMP_GRP=0;
    final int INT_ARL_COL_ADD_NOM_EMP_GRP=1;
    final int INT_ARL_COL_ADD_COD_BOD_GRP=2;
    final int INT_ARL_COL_ADD_NOM_BOD_GRP=3;
    final int INT_ARL_COL_ADD_NUM_COL_GRP=4;

    //Arraylist: Datos Empresa
    private ArrayList arlRegEmp, arlDatEmp;
    final int INT_ARL_COL_ADD_COD_EMP=0;
    final int INT_ARL_COL_ADD_NOM_EMP=1;
    final int INT_ARL_COL_ADD_COD_BOD=2;
    final int INT_ARL_COL_ADD_NOM_BOD=3;
    final int INT_ARL_COL_ADD_NUM_COL=4;
        
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatBod, vecCabBod;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private boolean blnPerVerColStkSob;
    private String strCodAlt, strCodAlt2, strNomItm;    //Contenido del campo al obtener el foco.
    private int intNumColBodCodEmpGrp;
    private int intColModIni;
    private int intColGrpAdi, intColGrpIni, intColGrpFin;
    private int intColEmpAdi, intColEmpIni, intColEmpFin;
    private int intColSumStkDisAdi, intColSumStkIni, intColSumStkFin;
    private int intColStkFalSobAdi, intColStkFalSobIni, intColStkFalSobFin;

    private java.awt.Color colFonColNar=new java.awt.Color(255,221,187);
    private java.awt.Color colFonColVer=new java.awt.Color(228,228,203);
    
    /** Crea una nueva instancia de la clase ZafCom14. */
    public ZafCom14(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            arlDatGrp=new ArrayList();
            arlDatEmp=new ArrayList();
            objPerUsr=new ZafPerUsr(objParSis);
            if (!configurarFrm())
                exitForm();
        }
        catch (CloneNotSupportedException e){          this.setTitle(this.getTitle() + " [ERROR]");      }
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
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panDesHasItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panBod = new javax.swing.JPanel();
        panOptBod = new javax.swing.JPanel();
        optBodTod = new javax.swing.JRadioButton();
        optBodEmp = new javax.swing.JRadioButton();
        optBodGrp = new javax.swing.JRadioButton();
        optBodGrpIncRel = new javax.swing.JRadioButton();
        optStkFalSob = new javax.swing.JRadioButton();
        panLisBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkSolStk = new javax.swing.JCheckBox();
        chkMosSob = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
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
        butCer = new javax.swing.JButton();
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
        optTod.setBounds(4, 1, 400, 14);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 15, 400, 14);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 30, 70, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(75, 30, 30, 20);

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
        txtCodAlt.setBounds(105, 30, 92, 20);

        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(197, 30, 63, 20);

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
        txtNomItm.setBounds(260, 30, 400, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(660, 30, 20, 20);

        panDesHasItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panDesHasItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panDesHasItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 16, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 15, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panDesHasItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 16, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panDesHasItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 15, 100, 20);

        panFil.add(panDesHasItm);
        panDesHasItm.setBounds(24, 50, 328, 40);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 16, 100, 20);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T\n</HTML>");
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(112, 15, 204, 20);

        panFil.add(panItmTer);
        panItmTer.setBounds(356, 50, 328, 40);

        panBod.setLayout(new java.awt.BorderLayout());

        panOptBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panOptBod.setPreferredSize(new java.awt.Dimension(100, 92));
        panOptBod.setLayout(null);

        optBodTod.setSelected(true);
        optBodTod.setText("Todas las bodegas");
        optBodTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBodTodActionPerformed(evt);
            }
        });
        panOptBod.add(optBodTod);
        optBodTod.setBounds(6, 14, 440, 14);

        optBodEmp.setText("Sólo las bodegas de las empresas");
        optBodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBodEmpActionPerformed(evt);
            }
        });
        panOptBod.add(optBodEmp);
        optBodEmp.setBounds(6, 29, 440, 14);

        optBodGrp.setText("Sólo las bodegas del grupo");
        optBodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBodGrpActionPerformed(evt);
            }
        });
        panOptBod.add(optBodGrp);
        optBodGrp.setBounds(6, 44, 440, 14);

        optBodGrpIncRel.setText("Sólo las bodegas del grupo(Incluir en el reporte las bodegas relacionadas)");
        optBodGrpIncRel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optBodGrpIncRelActionPerformed(evt);
            }
        });
        panOptBod.add(optBodGrpIncRel);
        optBodGrpIncRel.setBounds(6, 60, 470, 14);

        optStkFalSob.setText("Sólo el stock, disponible, faltantes y sobrantes totales");
        optStkFalSob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optStkFalSobActionPerformed(evt);
            }
        });
        panOptBod.add(optStkFalSob);
        optStkFalSob.setBounds(6, 75, 440, 14);

        panBod.add(panOptBod, java.awt.BorderLayout.NORTH);

        panLisBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panLisBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panBod.add(panLisBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(24, 90, 660, 190);

        chkSolStk.setText("Mostrar sólo items con stock");
        chkSolStk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkSolStkActionPerformed(evt);
            }
        });
        panFil.add(chkSolStk);
        chkSolStk.setBounds(20, 290, 324, 14);

        chkMosSob.setSelected(true);
        chkMosSob.setText("Mostrar cantidades de sobrantes");
        chkMosSob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosSobActionPerformed(evt);
            }
        });
        panFil.add(chkMosSob);
        chkMosSob.setBounds(20, 310, 324, 14);

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 44));
        panBar.setLayout(new java.awt.BorderLayout());

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
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
                txtCodAlt2.setText("");
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

    private void txtCodAltHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltHasFocusLost
        if (txtCodAltHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtCodAltHas.getText().length()==0)
                txtCodAltHas.setText(txtCodAltDes.getText());
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
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            chkSolStk.setSelected(false);
            chkMosSob.setSelected(false);
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void optBodTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBodTodActionPerformed
        if(optBodTod.isSelected())
        {
            optBodEmp.setSelected(false);
            optBodGrp.setSelected(false);
            optBodGrpIncRel.setSelected(false);
            optStkFalSob.setSelected(false);
            optFil.setSelected(true);
            optTod.setSelected(false);
            intColGrpAdi=0;
        }
        else
            optBodTod.setSelected(true);
        cargarBod();
        seleccionaBodegas();
    }//GEN-LAST:event_optBodTodActionPerformed

    private void optBodGrpIncRelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBodGrpIncRelActionPerformed
        if(optBodGrpIncRel.isSelected())
        {
            optBodTod.setSelected(false);
            optBodEmp.setSelected(false);
            optBodGrp.setSelected(false);
            optStkFalSob.setSelected(false);
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        else
            optBodGrpIncRel.setSelected(true);
        cargarBod();
        seleccionaBodegas();
    }//GEN-LAST:event_optBodGrpIncRelActionPerformed

    private void optBodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBodEmpActionPerformed
        if(optBodEmp.isSelected())
        {
            optBodTod.setSelected(false);
            optBodGrp.setSelected(false);
            optBodGrpIncRel.setSelected(false);
            optStkFalSob.setSelected(false);
            chkMosSob.setSelected(false);
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        else
            optBodEmp.setSelected(true);
        cargarBod();
        seleccionaBodegas();
    }//GEN-LAST:event_optBodEmpActionPerformed

    private void optBodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optBodGrpActionPerformed
        if(optBodGrp.isSelected())
        {
            optBodTod.setSelected(false);
            optBodEmp.setSelected(false);
            optBodGrpIncRel.setSelected(false);
            optStkFalSob.setSelected(false);
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
        else
            optBodGrp.setSelected(true);
        cargarBod();
        seleccionaBodegas();
    }//GEN-LAST:event_optBodGrpActionPerformed

    private void optStkFalSobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optStkFalSobActionPerformed
        if(optStkFalSob.isSelected())
        {
            optBodTod.setSelected(false);
            optBodEmp.setSelected(false);
            optBodGrp.setSelected(false);
            optBodGrpIncRel.setSelected(false);
        }
        else
        {
            optStkFalSob.setSelected(true);
        }
        objTblModBod.removeAllRows();
    }//GEN-LAST:event_optStkFalSobActionPerformed

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2=txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodAlt2.setText(strCodAlt2);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void chkMosSobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosSobActionPerformed
        if (chkMosSob.isSelected())
            optFil.setSelected(true);
    }//GEN-LAST:event_chkMosSobActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkMosSob;
    private javax.swing.JCheckBox chkSolStk;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optBodEmp;
    private javax.swing.JRadioButton optBodGrp;
    private javax.swing.JRadioButton optBodGrpIncRel;
    private javax.swing.JRadioButton optBodTod;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optStkFalSob;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panDesHasItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panLisBod;
    private javax.swing.JPanel panOptBod;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
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
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v1.3.5 ");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();

            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
            {
                optBodTod.setVisible(false);
                optBodTod.setEnabled(false);

                optBodEmp.setVisible(false);
                optBodEmp.setEnabled(false);

                optBodGrp.setVisible(false);
                optBodGrp.setEnabled(false);

                optBodGrpIncRel.setVisible(false);
                optBodGrpIncRel.setEnabled(false);

                optStkFalSob.setVisible(false);
                optStkFalSob.setEnabled(false);
                
                chkMosSob.setVisible(false);
                chkMosSob.setEnabled(false);
                
                panOptBod.setPreferredSize(new java.awt.Dimension(100, 19));
            }

            blnPerVerColStkSob=true;

            if(!objPerUsr.isOpcionEnabled(658)){
                butCon.setVisible(false);
            }
            if(!objPerUsr.isOpcionEnabled(659)){
                butCer.setVisible(false);
            }
            if(!objPerUsr.isOpcionEnabled(2599)){
                blnPerVerColStkSob=false;
                optStkFalSob.setVisible(false);
                chkMosSob.setVisible(false);
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
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
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
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
                    
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Libero los objetos auxiliares.
            tcmAux=null;

            intColModIni=objTblMod.getColumnCount();

        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDatBod=new Vector();    //Almacena los datos
            vecCabBod=new Vector(6);   //Almacena las cabeceras
            vecCabBod.clear();
            vecCabBod.add(INT_TBL_BOD_LIN,"");
            vecCabBod.add(INT_TBL_BOD_CHK,"");
            vecCabBod.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCabBod.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCabBod.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCabBod.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCabBod);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_BOD_COD_TIP_DOC).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intCodEmpNotGrp;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        intCodEmpNotGrp=Integer.parseInt(objTblModBod.getValueAt(tblBod.getSelectedRow(), INT_TBL_BOD_COD_EMP).toString());
                        if(optBodTod.isSelected())
                            objTblCelEdiChk.setCancelarEdicion(false);
                        else{
                            if(intCodEmpNotGrp==objParSis.getCodigoEmpresaGrupo())
                                objTblCelEdiChk.setCancelarEdicion(false);
                            else
                                objTblCelEdiChk.setCancelarEdicion(true);
                        }

                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * Solo agrega columnas cuando el Código de la empresa de la bodega es Grupo.
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDatGrp()
    {
        javax.swing.table.TableColumn tbc;
        String strCodEmp="", strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        boolean blnRes=true;
        int intCodEmpBodGrp=-1;
        intColGrpAdi=0;
        int i;
        
        try
        {
            arlDatGrp.clear();
            intColGrpIni=objTblMod.getColumnCount();
            
            //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
            for(int j=0; j<objTblModBod.getRowCountTrue(); j++)
            {
                intCodEmpBodGrp=Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString());
                if(intCodEmpBodGrp==objParSis.getCodigoEmpresaGrupo())
                {
                    if(objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        intColGrpAdi++;
                        arlRegGrp=new ArrayList();
                        arlRegGrp.add(INT_ARL_COL_ADD_COD_EMP_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP));
                        arlRegGrp.add(INT_ARL_COL_ADD_NOM_EMP_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_EMP));
                        arlRegGrp.add(INT_ARL_COL_ADD_COD_BOD_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD));
                        arlRegGrp.add(INT_ARL_COL_ADD_NOM_BOD_GRP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_BOD));
                        arlRegGrp.add(INT_ARL_COL_ADD_NUM_COL_GRP, "");
                        arlDatGrp.add(arlRegGrp);
                    }
                }
            }
            System.out.println("Roseee.intColGrpAdi: "+intColGrpAdi);
            //</editor-fold>

            int p=0;//reutilizo la variable
            int q=0;
            for (i=0; i<(intColGrpAdi*INT_NUM_COL_ADI_TBL_DATGRP); i++)
            {
                boolean blnUltCol=false;
                intCodEmpBodGrp=objUti.getIntValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP);
                if(intCodEmpBodGrp==objParSis.getCodigoEmpresaGrupo())
                {
                    int intIndColGrp=(intColGrpIni+i*INT_TBL_DAT_FAC_NUM_CDI); //Obtengo Indice de la Columna.
                    tbc=new javax.swing.table.TableColumn(intIndColGrp);       //Creo la columna dinamica.
                    objUti.setStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NUM_COL_GRP, "" + (intIndColGrp)); //Actualiza en el Arraylist el último índice.

                    if((intIndColGrp-1)%INT_NUM_COL_ADI_TBL_DATGRP==0)  
                    {
                      tbc.setHeaderValue("Stock");
                    }
                    else if( (intIndColGrp+1) % INT_NUM_COL_ADI_TBL_DATGRP==0) // Verifico que el indice posterior haya sido multiplo de 3(stock).
                    {
                      tbc.setHeaderValue("Disponible");
                    } 
                    else if (intIndColGrp%INT_NUM_COL_ADI_TBL_DATGRP==0) //Si es multiplo de 3.
                    {
                      tbc.setHeaderValue("Sobrante");
                      blnUltCol=true;
                    }

                    //Configurar JTable: Establecer el ancho de la columna.
                    if(objParSis.getCodigoUsuario()==1)
                        tbc.setPreferredWidth(55);
                    else
                    {
                        if(blnPerVerColStkSob)
                            tbc.setPreferredWidth(55);
                        else
                        {
                            if( intIndColGrp  % INT_NUM_COL_ADI_TBL_DATGRP==0) //Sobrante
                            {
                                tbc.setWidth(0);
                                tbc.setMaxWidth(0);
                                tbc.setMinWidth(0);
                                tbc.setPreferredWidth(0);
                                tbc.setResizable(false);
                            }
                            else // Stock y Disponible
                            {
                                tbc.setPreferredWidth(55);
                            }
                        }
                    }

                    //Configurar JTable: Renderizar celdas.
                    objTblCelRenLbl=new ZafTblCelRenLbl();
                    objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                    objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                    objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                    objTblCelRenLbl.setBackground(colFonColVer);
                    tbc.setCellRenderer(objTblCelRenLbl);
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                    //Agrupar las columnas.
                    //Columnas Grupo y Empresas.
                    if (!strCodEmp.equals(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP)))
                    {
                        objTblHeaColGrpEmp=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NOM_EMP_GRP));
                        objTblHeaColGrpEmp.setHeight(16);
                        strCodEmp=objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_EMP_GRP);
                        objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                    }
                    //Columnas Bodegas
                    if (!strCodBod.equals(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_BOD_GRP)))
                    {                      
                        objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_NOM_BOD_GRP));
                        objTblHeaColGrpBod.setHeight(16*2);
                        strCodBod=objUti.getStringValueAt(arlDatGrp, p, INT_ARL_COL_ADD_COD_BOD_GRP);
                        objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                    }                   
                    
                    //Columnas Stock, Disponible y Sobrante
                    objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(intIndColGrp));
                    if(blnUltCol)
                        p++;

                }
            }
            objTblCelRenLbl=null;
            intColGrpFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {      
        int i;
        javax.swing.table.TableColumn tbc;
        String strCodEmp="", strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        boolean blnRes=true;
        //int p=0;//borrar p y colocar i donde estan las p, cuando sea solo 1 columna dinamica por bodega.Ej.:Stock.
        int intCodEmpBodEmp=-1;
        intColEmpAdi=0;

        java.awt.Color colFonCol;
   
        colFonCol=colFonColNar; 
        int intColor=0;

        try
        {
            arlDatEmp.clear();
            intColEmpIni=objTblMod.getColumnCount();
            
            //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
            for(int j=0; j<objTblModBod.getRowCountTrue(); j++)
            {
                intCodEmpBodEmp=Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString());
                if(intCodEmpBodEmp!=objParSis.getCodigoEmpresaGrupo())
                {
                    if(objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        intColEmpAdi++;
                        arlRegEmp=new ArrayList();
                        arlRegEmp.add(INT_ARL_COL_ADD_COD_EMP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP));
                        arlRegEmp.add(INT_ARL_COL_ADD_NOM_EMP, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_EMP));
                        arlRegEmp.add(INT_ARL_COL_ADD_COD_BOD, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD));
                        arlRegEmp.add(INT_ARL_COL_ADD_NOM_BOD, "" + objTblModBod.getValueAt(j, INT_TBL_BOD_NOM_BOD));
                        arlRegEmp.add(INT_ARL_COL_ADD_NUM_COL, "");
                        arlDatEmp.add(arlRegEmp);
                    }
                }
            }
            //</editor-fold>
            
            int p=0;//reutilizo la variable
            for (i=0; i<intColEmpAdi*INT_NUM_COL_ADI_TBL_DATEMP; i++)
            {
                boolean blnUltCol=false;
                boolean blnEmpDif=false;
                int intIndColEmp=intColEmpIni+i*INT_TBL_DAT_FAC_NUM_CDI;
                tbc=new javax.swing.table.TableColumn(intIndColEmp);
                objUti.setStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NUM_COL, "" + (intIndColEmp)); //Actualiza en el Arraylist el último índice.
                
                if((i%2)==0)
                {
                     tbc.setHeaderValue("Stock");                    
                }
                else
                {
                     tbc.setHeaderValue("Disponible");
                     blnUltCol=true;
                }
                
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(55);
                
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                //Agrupar las columnas.    
                //Empresas
                if (!strCodEmp.equals(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_EMP)))
                {
                    blnEmpDif=true;
                    objTblHeaColGrpEmp=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_EMP));
                    objTblHeaColGrpEmp.setHeight(16);
                   
                    strCodEmp=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_EMP);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                   

                    if((intColor%2)==0)
                    {
                        colFonCol=colFonColNar;
                        intColor++;
                    }
                    else
                    {
                        colFonCol=colFonColVer;
                        intColor++;
                    }                                     
                }
                objTblCelRenLbl.setBackground(colFonCol);
                
                if (!strCodBod.equals(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD)))
                {
                    objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_BOD));
                    objTblHeaColGrpBod.setHeight(16*2);
                    strCodBod=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD);
                    objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                }
                else
                {
                    if(blnEmpDif)
                    {
                        objTblHeaColGrpBod=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_NOM_BOD));
                        objTblHeaColGrpBod.setHeight(16*2);
                        strCodBod=objUti.getStringValueAt(arlDatEmp, p, INT_ARL_COL_ADD_COD_BOD);
                        objTblHeaColGrpEmp.add(objTblHeaColGrpBod);
                    }
                }
              
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(intIndColEmp));

                if(blnUltCol)
                    p++;
                
            }
            objTblCelRenLbl=null;
            intColEmpFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDatStkDisFalSob()
    {
        int i;
        javax.swing.table.TableColumn tbc;
        boolean blnRes=true;
        intColStkFalSobAdi=4;
        String strTit="";
        try
        {
            intColStkFalSobIni=objTblMod.getColumnCount();

            for (i=0; i<intColStkFalSobAdi; i++)
            {
                tbc=new javax.swing.table.TableColumn(intColStkFalSobIni+i*INT_TBL_DAT_FAC_NUM_CDI);
                strTit=( i==0?"Stock": (i==1?"Disponible": (i==2?"Faltantes": (i==3?"Sobrantes":"") ))  );
                tbc.setHeaderValue(strTit);
                
                //Configurar JTable: Establecer el ancho de la columna.
                if(objParSis.getCodigoUsuario()==1)
                    tbc.setPreferredWidth(80);
                else
                {
                    if(blnPerVerColStkSob)
                        tbc.setPreferredWidth(80);
                    else
                    {
                        if( (i==0) || (i==1) || (i==2) )  //stock
                        { 
                            tbc.setPreferredWidth(88);
                        }
                        else  //sobrante
                        {
                            tbc.setWidth(0);
                            tbc.setMaxWidth(0);
                            tbc.setMinWidth(0);
                            tbc.setPreferredWidth(0);
                            tbc.setResizable(false);
                        }
                    }
                }


                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
            }
            objTblCelRenLbl=null;
            intColStkFalSobFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColSumStkDis()
    {
        int i;
        javax.swing.table.TableColumn tbc;
        boolean blnRes=true;
        intColSumStkDisAdi=2;
        String strTitTbl="";
        ZafTblHeaGrp objTblHeaSumTot = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaSumTot.setHeight(16 * 4);
        ZafTblHeaColGrp objTblHeaColSumTot = null;
        try
        {
            intColSumStkIni=objTblMod.getColumnCount();
            for (i=0; i<intColSumStkDisAdi; i++)
            {
                strTitTbl = (i==0 ? "Stock" : (i==1 ? "Disponible" : ""));
                tbc=new javax.swing.table.TableColumn(intColSumStkIni+i*INT_TBL_DAT_FAC_NUM_CDI);
                tbc.setHeaderValue("" + strTitTbl + "");
                //Configurar JTable: Establecer el ancho de la columna.
                if(!optStkFalSob.isSelected())
                {
                    tbc.setPreferredWidth(80);
                }
                else
                {
                    tbc.setWidth(0);
                    tbc.setMaxWidth(0);
                    tbc.setMinWidth(0);
                    tbc.setPreferredWidth(0);
                }
                tbc.setResizable(false);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                if(i==0)
                {
                    objTblHeaColSumTot=new ZafTblHeaColGrp("Sumatoria Total");
                    objTblHeaColSumTot.setHeight(16*3);
                }
                objTblHeaSumTot.addColumnGroup(objTblHeaColSumTot);
                objTblHeaColSumTot.add(tblDat.getColumnModel().getColumn(intColSumStkIni+i));
            }
            objTblCelRenLbl=null;
            intColSumStkFin=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las bodegas.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo las bodegas de la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
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
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    else{//la empresa es grupo(0)
                        if(optBodTod.isSelected())
                            strSQL+="";
                        else if(optBodEmp.isSelected())
                            strSQL+=" WHERE a1.co_emp<>" + intCodEmp;
                        else if(optBodGrp.isSelected())
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        else if(optBodGrpIncRel.isSelected())
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    }
                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a2.st_reg, a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_bodlocprgusr AS a3";
                    strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";

                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    else{//la empresa es grupo(0)
                        if(optBodTod.isSelected())
                            strSQL+="";
                        else if(optBodEmp.isSelected())
                            strSQL+=" WHERE a1.co_emp<>" + intCodEmp;
                        else if(optBodGrp.isSelected())
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        else if(optBodGrpIncRel.isSelected())
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    }
                    strSQL+=" AND a1.st_reg NOT IN('I','E') AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a3.st_reg IN('A','P')";
                    strSQL+=" ORDER BY a1.co_emp, a3.ne_ord, a2.co_bod";
                }

                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatBod.clear();
                intNumColBodCodEmpGrp=0;
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    if (rst.getString("st_reg").equals("A"))
                        vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    else
                        vecReg.add(INT_TBL_BOD_CHK,null);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDatBod.add(vecReg);

                    if(rst.getInt("co_emp")==objParSis.getCodigoEmpresaGrupo())
                        intNumColBodCodEmpGrp++;

                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDatBod);
                tblBod.setModel(objTblModBod);
                vecDatBod.clear();
                blnMarTodChkTblBod=false;
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


    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                //Filtro: Sólo el stock, faltantes y sobrantes totales
                if(optStkFalSob.isSelected())
                {
                    if(eliminaTodasColumnasAdicionadas()){
                        if(agregarColTblDatStkDisFalSob()){
                            if(agregarColSumStkDis()){
                                if(cargarDetReg()){
                                    if(chkSolStk.isSelected()){
                                        if(quitaStockCero()){
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //Filtro: Sólo las bodegas del grupo(Incluir en el reporte las bodegas relacionadas)
                else if(optBodGrpIncRel.isSelected())
                {
                   //Permite agregar la bodega q se selecciono en el grupo para cada una de las empresas
                   //si se selecciona en el grupo->Bod.Quito, esta bodega se debe presentar en Grupo, Tuval, Castek, Nositol, Dimulti.
                    if(agregarFilasBodegasEmpresas()){
                        if(eliminaTodasColumnasAdicionadas()){
                            if(agregarColTblDatGrp()){
                                if(agregarColTblDat()){
                                    if(agregarColSumStkDis()){
                                        if(cargarDetReg()){
                                            if(eliminarFilasBodegasEmpresas()){
                                                 if(calcularTotalStkDis()){
                                                    if(chkSolStk.isSelected()){
                                                        if(quitaStockCero()){//permitira quitar las filas q no tienen stock
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
                //Todos los otros filtros: Trabaja con las bodegas seleccionadas directamente en la tabla de bodegas
                else
                {
                    if(eliminaTodasColumnasAdicionadas()){
                        if(agregarColTblDatGrp()){
                            if(agregarColTblDat()){
                                if(agregarColSumStkDis()){
                                    if(cargarDetReg()){
                                          if(calcularTotalStkDis()){
                                              if(chkSolStk.isSelected()){
                                                if(quitaStockCero()){
                                                }
                                            }                                       
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){       blnRes=false;       objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){        blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
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
        int intCodEmp, intNumFilTblBod, j;
        BigDecimal bdeValStk=new BigDecimal("0");
        BigDecimal bdeSumStk=new BigDecimal("0");
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblBod=objTblModBod.getRowCountTrue();       
            //BigDecimal bgdSumStk=new BigDecimal("0");
            int intSumStk=0;
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtCodItm.getText().length()>0)
                    strAux+=" AND a1.co_itm=" + txtCodItm.getText();
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    if(optStkFalSob.isSelected())
                    {
                        strSQL="";
                        strSQL+=" SELECT b1.co_itmMae, b1.co_emp, b1.co_itm, b1.tx_codAlt,  b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor, b3.nd_stkAct, b4.nd_canDis, b2.nd_stkFal, b1.nd_canSob \n";
                        strSQL+=" FROM( \n";
                        strSQL+=" 	SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt,  a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor, SUM(a4.nd_canSob) AS nd_canSob \n";
                        strSQL+=" 	FROM (tbm_inv AS a1 INNER JOIN tbm_invBod AS a4 ON a1.co_emp=a4.co_emp AND a1.co_itm=a4.co_itm) \n";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+=" 	LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n";
                        strSQL+=" 	WHERE a1.co_emp=" + intCodEmp + "";
                        strSQL+=" 	AND a1.st_reg='A'";
                        strSQL+="       "+strAux+" \n";
                        strSQL+=" 	GROUP BY a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor \n";
                        strSQL+=" 	ORDER BY a1.tx_codAlt \n";
                        strSQL+=" ) AS b1 \n";
                        strSQL+=" INNER JOIN( \n";
                        strSQL+=" 	SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkFal \n";
                        strSQL+=" 	FROM tbm_invBod AS a1 \n";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+=" 	INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)\n";
                        strSQL+=" 	WHERE a3.co_empGrp=" + intCodEmp + "";
                        strSQL+=" 	AND a3.co_bodGrp=(select co_bod from tbm_bod where co_emp=" + intCodEmp + " AND st_bodFal='S')";
                        strSQL+=" 	GROUP BY a2.co_itmMae \n";
                        strSQL+=" ) AS b2 ON b1.co_itmMae=b2.co_itmMae \n";
                        strSQL+=" INNER JOIN( \n";
                        strSQL+=" 	SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct \n";
                        strSQL+=" 	FROM tbm_inv AS a1 \n";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+=" 	WHERE a1.co_emp<>" + intCodEmp + "";
                        strSQL+=" 	GROUP BY a2.co_itmMae";
                        strSQL+=" ) AS b3 ON b1.co_itmMae=b3.co_itmMae \n";
                        strSQL+=" INNER JOIN( \n";
                        strSQL+=" 	SELECT a2.co_itmMae, SUM(a1.nd_canDis) AS nd_canDis \n";
                        strSQL+=" 	FROM tbm_invBod AS a1 \n";
                        strSQL+=" 	INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+=" 	WHERE a1.co_emp<>" + intCodEmp + "";
                        strSQL+=" 	GROUP BY a2.co_itmMae";
                        strSQL+=" ) AS b4 ON b1.co_itmMae=b4.co_itmMae \n";
                        strSQL+=" ORDER BY tx_codAlt \n";                    
                        //System.out.println("strSQL.Grupo.optStkFalSob: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                    }
                    else
                    {
                        //Obtener los datos del "Grupo".
                        strSQL="";
                        strSQL+=" SELECT *FROM( \n";
                        strSQL+=" SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor \n";
                        for (j=0; j<intNumFilTblBod; j++)
                        {
                            if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                            {
                                strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                                strSQL+=", b" + (j+2) + ".nd_canDis AS b" + (j+2) + "_nd_CanDis";
                                // if( (objParSis.getCodigoUsuario()==24) ||  (objParSis.getCodigoUsuario()==206) ||  (objParSis.getCodigoUsuario()==1) )
                                if(chkMosSob.isSelected())
                                {
                                    strSQL+=", b" + (j+2) + ".nd_stkSob AS b" + (j+2) + "_nd_stkSob \n";
                                }
                                else
                                    strSQL+=", 0 AS b" + (j+2) + "_nd_stkSob \n";
                            }
                        }
                        strSQL+=" FROM ( \n";
                        strSQL+="    SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a3.tx_desCor \n";
                        strSQL+="    FROM tbm_inv AS a1 \n";
                        strSQL+="    INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                        strSQL+="    LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg) \n";
                        strSQL+="    WHERE a1.co_emp=" + intCodEmp;
                        strSQL+="    AND a1.st_reg='A'";
                        strSQL+="    "+strAux+" \n";
                        strSQL+=" ) AS b1 \n";
                        for (j=0; j<intNumFilTblBod; j++)
                        {
                            if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                            {
                                if (Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString())==objParSis.getCodigoEmpresaGrupo())
                                {
                                    strSQL+=" INNER JOIN ( \n";
                                    strSQL+="    SELECT b1.co_itmMae, b1.nd_stkAct, b1.nd_canDis, b2.nd_canSob AS nd_stkSob \n";
                                    strSQL+="    FROM( \n";
                                    strSQL+="        SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct,  SUM(a1.nd_canDis) AS nd_canDis  \n";
                                    strSQL+="        FROM tbm_invBod AS a1 \n";
                                    strSQL+="        INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                                    strSQL+="        INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod) \n";
                                    strSQL+="        WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP)+"";
                                    strSQL+="        AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD)+" \n";
                                    strSQL+="        GROUP BY a2.co_itmMae \n";
                                    strSQL+="    ) AS b1 \n";
                                    strSQL+="    INNER JOIN( \n";
                                    strSQL+="        SELECT a2.co_itmMae, a1.nd_canSob \n";
                                    strSQL+="        FROM tbm_invBod AS a1 \n";
                                    strSQL+="        INNER JOIN tbm_equInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm \n";
                                    strSQL+="        WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + "";
                                    strSQL+="        AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + "\n";
                                    strSQL+="    ) AS b2 ON b1.co_itmMae=b2.co_itmMae \n";
                                    strSQL+="    ORDER BY b1.co_itmMae \n";
                                    strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae) \n";
                                }
                                else
                                {
                                    strSQL+=" INNER JOIN ( \n";
                                    strSQL+="    SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.nd_stkAct, a1.nd_canDis, 0 AS nd_stkSob \n";
                                    strSQL+="    FROM tbm_invBod AS a1 \n";
                                    strSQL+="    INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                                    strSQL+="    WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP)+"";
                                    strSQL+="    AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD)+"\n";
                                    strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae) \n";
                                }
                            }
                        }
                        strSQL+=" ORDER BY b1.co_itmMae  \n";
                        strSQL+=" ) AS c1 ORDER BY c1.tx_codAlt \n";
                        //System.out.println("strSQL.Grupo.Todos:" + strSQL);
                        rst=stm.executeQuery(strSQL);
                    }

                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT Null AS co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b1.tx_desCor \n";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                            strSQL+=", b" + (j+2) + ".nd_canDis AS b" + (j+2) + "_nd_canDis \n";
                        }
//                        else
//                        {
//                            strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
//                        }
                    }
                    strSQL+=" FROM ( \n";
                    strSQL+="      SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor \n";
                    strSQL+="      FROM tbm_inv AS a1 \n";
                    strSQL+="      LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg) \n";
                    strSQL+="      WHERE a1.co_emp=" + intCodEmp;
                    strSQL+="      AND a1.st_reg='A'";
                    strSQL+="      "+strAux+" \n";
                    strSQL+=" ) AS b1 \n";
                    for (j=0; j<intNumFilTblBod; j++)
                    {
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                        {
                            strSQL+=" INNER JOIN ( \n";
                            strSQL+="    SELECT a1.co_emp, a1.co_itm, a1.nd_stkAct, a1.nd_canDis \n";
                            strSQL+="    FROM tbm_invBod AS a1 \n";
                            strSQL+="    WHERE a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP)+"";
                            strSQL+="    AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD)+" \n";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_emp=b" + (j+2) + ".co_emp AND b1.co_itm=b" + (j+2) + ".co_itm) \n";
                        }
                    }
                    
                    strSQL+=" ORDER BY b1.tx_codAlt";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                int intCodEmpGrpCol=-1;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_desCor"));
                        int p=0;
                        if(optStkFalSob.isSelected())
                        {
                            vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)),rst.getString("nd_stkAct"));
                            p++;
                            vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)),rst.getString("nd_canDis"));
                            p++;
                            vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)),rst.getString("nd_stkFal"));
                            p++;
                            vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)),rst.getString("nd_canSob"));
                        }
                        else
                        {
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)) ,rst.getString("b" + (j+2) + "_nd_stkAct"));
                                    p++;
                                    
                                    vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)) ,rst.getString("b" + (j+2) + "_nd_canDis"));
                                    p++;
                                    
                                    intCodEmpGrpCol=Integer.parseInt(objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP).toString());
                                    if(intCodEmpGrpCol==objParSis.getCodigoEmpresaGrupo())
                                    {
                                        vecReg.add((INT_TBL_DAT_CDI_STK+(p*INT_TBL_DAT_FAC_NUM_CDI)),rst.getString("b" + (j+2) + "_nd_stkSob"));
                                        p++;
                                    }
               
                                }
                            }
                        }
                       
                        ///////////////////////////////////////////////////////////
                        for (int h = intColSumStkIni; h < intColSumStkFin; h++) 
                        {
                            vecReg.add(h, "");
                        }      
                        vecDat.add(vecReg);
                        
                    }
                    else
                    {
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
     * Esta función calcula el total de stock de las filas especificadas.
     * @return true: Si se pudo calcular el total de las filas especificadas sin
     * que ocurra ningún error. <BR>false: En el caso contrario.
     */
    private boolean calcularTotalStkDis()
    {
        System.out.println("calcularTotalStkDis!!!");
        boolean blnRes=true;
        BigDecimal bdeVal=new BigDecimal("0");
        BigDecimal bdeSumStk=new BigDecimal("0");
        BigDecimal bdeSumDis=new BigDecimal("0");
        int intTotColGrp, intTotColEmp;
        int i, j;
        try
        {
            System.gc();
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            
            if(!optStkFalSob.isSelected())
            {
                if ( (optBodTod.isSelected() && intColGrpAdi>0) || optBodGrp.isSelected() || optBodGrpIncRel.isSelected() )
                {
                    for(i=0; i<objTblMod.getRowCountTrue(); i++)
                    {
                        intTotColGrp=INT_TBL_DAT_CDI_STK+(intColGrpAdi*INT_NUM_COL_ADI_TBL_DATGRP);
                        for (j=INT_TBL_DAT_CDI_STK; j<intTotColGrp; j++)
                        {
                            if((j-1)%INT_NUM_COL_ADI_TBL_DATGRP==0)  //Stock
                            {
                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                 bdeSumStk=bdeSumStk.add(bdeVal);
                            }
                            else if( (j+1) % INT_NUM_COL_ADI_TBL_DATGRP==0) //Disponible
                            {
                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                 bdeSumDis=bdeSumDis.add(bdeVal);
                            } 
                        }
                        objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);
                        objTblMod.setValueAt(bdeSumDis, i, intColSumStkIni+1);
                        bdeSumStk=new BigDecimal("0");
                        bdeSumDis=new BigDecimal("0");
                    }
                }
                else if( (optBodTod.isSelected() && intColGrpAdi==0) || optBodEmp.isSelected() || (objParSis.getCodigoEmpresa()!= objParSis.getCodigoEmpresaGrupo()) )
                {
                    intTotColEmp=INT_TBL_DAT_CDI_STK+(intColEmpAdi*INT_NUM_COL_ADI_TBL_DATEMP);
                    for(i=0; i<objTblMod.getRowCountTrue(); i++)
                    {
                        for (j=INT_TBL_DAT_CDI_STK; j<intTotColEmp; j++)
                        {
                            if(j%2==0)  //Disponible
                            {
                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                 bdeSumDis=bdeSumDis.add(bdeVal);
                            }
                            else //Stock
                            {
                                 bdeVal=new BigDecimal(objTblMod.getValueAt(i,j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                                 bdeSumStk=bdeSumStk.add(bdeVal);
                            }
                        }
                        objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);
                        objTblMod.setValueAt(bdeSumDis, i, intColSumStkIni+1);
                        bdeSumStk=new BigDecimal("0");
                        bdeSumDis=new BigDecimal("0");
                    }
                }
            }
      
            if (blnCon)
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            else
                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
            
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
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
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv as a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg  IN ('A')";  
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
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
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
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
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt2.setText(strCodAlt2);
                        }
                    }
                    break;   
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil, intCodEmpGrpChk;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {

                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);

                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
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
            if (!cargarReg())
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
            int intColSel=-1, intColArl=-1;
            int veremos=0;

            int intCol=tblDat.columnAtPoint(evt.getPoint());
            //System.out.println("--------ToolTips----------");
            String strMsg="";
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_FAC_NUM_CDI+INT_TBL_DAT_NUM_TOT_CES;
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
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
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_DEC_UNI:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CDI_STK:
                    intColSel=tblDat.columnAtPoint(evt.getPoint());

                    if(optStkFalSob.isSelected()){
                        if(INT_TBL_DAT_CDI_STK==intColSel){
                            strMsg="Stock";
                        }
                        if((INT_TBL_DAT_CDI_STK+1)==intColSel){
                            strMsg="Disponible";
                        }
                        if((INT_TBL_DAT_CDI_STK+2)==intColSel){
                            strMsg="Faltantes";
                        }
                        if((INT_TBL_DAT_CDI_STK+3)==intColSel){
                            strMsg="Sobrantes";
                        }
                    }
                    else{
                        for(int j=0; j<intColGrpAdi; j++){
                            intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
                            if(intColArl==intColSel){
                                strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
                                veremos++;
                                break;
                            }
                        }

                        if(veremos==0){
                            for(int j=0; j<intColGrpAdi; j++){
                                intColArl=objUti.getIntValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NUM_COL_GRP);
                                intColArl=(intColArl-1);
                                if(intColArl==intColSel){
                                    strMsg="" + objUti.getStringValueAt(arlDatGrp, j, INT_ARL_COL_ADD_NOM_BOD_GRP);
                                    break;
                                }
                            }
                        }

                        for(int j=0; j<intColEmpAdi; j++){
                            intColArl=objUti.getIntValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NUM_COL);
                            if(intColArl==intColSel){
                                strMsg="" + objUti.getStringValueAt(arlDatEmp, j, INT_ARL_COL_ADD_NOM_BOD);
                                break;
                            }
                        }

                    }
                    //strMsg=objTblMod.getColumnName(tblDat.columnAtPoint(evt.getPoint()));
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
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }


    private boolean seleccionaBodegas()
    {
        boolean blnRes=true;
        int intCodEmpGrpChk=-1;
        try
        {
            for(int i=0; i<objTblModBod.getRowCountTrue(); i++){
                intCodEmpGrpChk=Integer.parseInt(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_EMP).toString());
                if(optBodTod.isSelected()){
                    objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                }
                else if(optBodEmp.isSelected()){
                    if(intCodEmpGrpChk==objParSis.getCodigoEmpresaGrupo())
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    else{
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                }                
                else if(optBodGrp.isSelected()){
                    if(intCodEmpGrpChk==objParSis.getCodigoEmpresaGrupo())
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    else{
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                }
                else if(optBodGrpIncRel.isSelected()){
                    if(intCodEmpGrpChk==objParSis.getCodigoEmpresaGrupo())
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    else{
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
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


    private boolean agregarFilasBodegasEmpresas()
    {
        boolean blnRes=true;
        String strRstCodEmp="", strRstCodBod="", strRstNomEmp="", strRstNomBod="";
        int intCodEmpGrpChk=-1;
        String strCodBodEmp="";
        int intIni=0;
        int intFilIns=-1;
        try
        {
            for(int i=0; i<objTblModBod.getRowCountTrue(); i++)
            {
                if(objTblModBod.isChecked(i, INT_TBL_BOD_CHK)){
                    intCodEmpGrpChk=Integer.parseInt(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_EMP).toString());
                    if(intCodEmpGrpChk==objParSis.getCodigoEmpresaGrupo()){
                        if(intIni==0){
                            strCodBodEmp="" + objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                            intIni++;
                        }
                        else
                            strCodBodEmp+="," + objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                    }
                }
            }
            //System.out.println("agregarFilasBodegasEmpresas.BodegasAntes: " + objTblModBod.getRowCountTrue());
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a3.tx_nom AS tx_nomEmp, a1.co_bod, a1.tx_nom AS tx_nomBod";
                strSQL+=" FROM (tbm_bod AS a1 INNER JOIN tbm_emp AS a3 ON a1.co_emp=a3.co_emp)";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+=" WHERE a2.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a2.co_bodGrp IN(" + strCodBodEmp + ") AND a1.st_reg NOT IN('E','I') AND a3.st_reg NOT IN('E','I')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_empGrp, a2.co_bod, a2.co_bodGrp";
                rst=stm.executeQuery(strSQL);
                objTblModBod.setModoOperacion(objTblModBod.INT_TBL_INS);
                while(rst.next())
                {
                    strRstCodEmp=rst.getString("co_emp");
                    strRstCodBod=rst.getString("co_bod");
                    strRstNomEmp=rst.getString("tx_nomEmp");
                    strRstNomBod=rst.getString("tx_nomBod");
                    
                    intFilIns=objTblModBod.getRowCountTrue();
                    objTblModBod.insertRow(intFilIns);
                    objTblModBod.setValueAt(new Boolean(true), intFilIns, INT_TBL_BOD_CHK);
                    objTblModBod.setValueAt(strRstCodEmp, intFilIns, INT_TBL_BOD_COD_EMP);
                    objTblModBod.setValueAt(strRstNomEmp, intFilIns, INT_TBL_BOD_NOM_EMP);
                    objTblModBod.setValueAt(strRstCodBod, intFilIns, INT_TBL_BOD_COD_BOD);
                    objTblModBod.setValueAt(strRstNomBod, intFilIns, INT_TBL_BOD_NOM_BOD);
                }
            }
        }
        catch(Exception e){        objUti.mostrarMsgErr_F1(this, e);      blnRes=false;       }
        return blnRes;
    }



    /**
     * Función que elimina las columnas adicionadas al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean eliminaTodasColumnasAdicionadas()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intColModIni); i--)
            {
                objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){  objUti.mostrarMsgErr_F1(this, e);    blnRes=false;      }
        return blnRes;
    }




    private boolean eliminarFilasBodegasEmpresas()
    {
        boolean blnRes=true;
        String strLin="";
        try
        {
            for (int i=(objTblModBod.getRowCountTrue()-1); i>=0; i--)
            {
                strLin=objTblModBod.getValueAt(i, INT_TBL_BOD_LIN)==null?"":objTblModBod.getValueAt(i, INT_TBL_BOD_LIN).toString();
                if(strLin.equals("I"))
                {
                    objTblModBod.removeRow(i);
                }
            }
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
            objTblModBod.removeEmptyRows();
        }
        catch(Exception e){    objUti.mostrarMsgErr_F1(this, e);        blnRes=false;        }
        return blnRes;
    }

    private boolean quitaStockCero()
    {
        boolean blnRes=true;
        BigDecimal bdeValStk=new BigDecimal("0");
        BigDecimal bdeSumStk=new BigDecimal("0");
        try
        {
            System.out.println("quitaStockCero!!!!!!!!!   "+intColGrpAdi);
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo() && (intColGrpAdi==0) ) //Cuando no se ha seleccionado bodegas de grupo.
            {
                for(int i=0; i<objTblMod.getRowCountTrue(); i++)
                {
                    for(int j=INT_TBL_DAT_CDI_STK; j<intColSumStkIni; j++)
                    {
                        bdeValStk=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                        bdeSumStk=bdeSumStk.add(bdeValStk);
                    }
                    objTblMod.setValueAt(bdeSumStk, i, intColSumStkIni);  //Comentado Rose
                    bdeSumStk=new BigDecimal("0");
                }
            }
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--)
            {
                bdeValStk=new BigDecimal(objTblMod.getValueAt(i, intColSumStkIni)==null?"0":(objTblMod.getValueAt(i, intColSumStkIni).equals("")?"0":objTblMod.getValueAt(i, intColSumStkIni).toString()));
                if(bdeValStk.compareTo(new BigDecimal("0"))<=0){
                    objTblMod.removeRow(i);
                }
            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
            objTblMod.removeEmptyRows();
            lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
        }
        catch(Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    


}