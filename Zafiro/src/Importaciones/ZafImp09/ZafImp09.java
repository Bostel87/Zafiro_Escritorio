/*
 * ZafImp08.java
 *
 * Created on 29 de mayo de 2014, 12:07 PM
 */
package Importaciones.ZafImp09;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.awt.Color;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp09 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_ITM=1;                     //Código del item. de la empresa
    static final int INT_TBL_DAT_COD_ITM_GRP=2;                //Muestra ventana con información de conteos realizados. 
    static final int INT_TBL_DAT_COD_ITM_MAE=3;                //Muestra ventana con información de conteos realizados. 
    static final int INT_TBL_DAT_COD_ALT_ITM=4;                     //Código alterno del item.
    static final int INT_TBL_DAT_COD_LET_ITM=5;                    //Código alterno dos del item.
    static final int INT_TBL_DAT_NOM_ITM=6;                     //Nombre del item.
    static final int INT_TBL_DAT_UNI_MED=7;                     //Unidad de medida.

    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoPed;                                   //Ventana de consulta "Clientes".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodPed, strPedImp;                     //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm, strNumPed;                        //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;
    private String strVersion="v0.2.2";
    
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
    private final int INT_ARL_ING_IMP_FOB_CFR=8;
    
    private int intNumColEst;
    private int intNumColAddIngImp, intNumColIniIngImp, intNumColFinIngImp;
    private ZafTblCelRenLbl objTblCelRenLblColDinIngImp, objTblCelRenLblColDinVar, objTblCelRenLblColDinIngImpVar, objTblCelRenLblColDinTerQua;              //Render: Presentar JLabel en JTable (columnas dinámicas)
    private int intNumColAdiPro, intNumColIniPro, intNumColFinPro;//columnas de valor ingresado para calculo de promedio
    private int intNumColAddIngImpVar, intNumColIniIngImpVar, intNumColFinIngImpVar;
    private int intNumColAdiTerQua, intNumColIniTerQua, intNumColFinTerQua;//
    private ZafTblCelEdiTxt objTblCelEdiTxtIngImp;
    
    private BigDecimal bdeValIngCalVar;
    
    private ArrayList arlRegValVarIngImp, arlDatValVarIngImp;
    private final int INT_ARL_VAL_VAR_ING_IMP=0;
    
    
    
    /** Crea una nueva instancia de la clase . */
    public ZafImp09(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                initComponents();
                arlDatIngImp=new ArrayList();
                bdeValIngCalVar=BigDecimal.ZERO;
                arlDatValVarIngImp=new ArrayList();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            //Inicializar objetos.
            
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
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        txtCodItmMae = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        txtCodPed = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        butPedImp = new javax.swing.JButton();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtCodItm = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodItmLet = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkCfr = new javax.swing.JCheckBox();
        chkFob = new javax.swing.JCheckBox();
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

        panFil.setPreferredSize(new java.awt.Dimension(0, 440));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 100, 400, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 80, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item");
        panFil.add(lblItm);
        lblItm.setBounds(20, 120, 50, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 42, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(54, 20, 80, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(146, 20, 42, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(186, 20, 80, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(20, 150, 274, 52);
        panFil.add(txtCodItmMae);
        txtCodItmMae.setBounds(56, 120, 30, 20);

        txtCodAlt.setToolTipText("Código Alterno");
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(120, 120, 80, 20);

        txtNomItm.setToolTipText("Nombre del Item");
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(300, 120, 340, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 120, 20, 20);

        lblEmp.setText("Importador:");
        lblEmp.setToolTipText("Empresa");
        panFil.add(lblEmp);
        lblEmp.setBounds(30, 210, 80, 20);

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
        txtCodEmp.setBounds(120, 210, 80, 20);

        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(200, 210, 440, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(640, 210, 20, 20);

        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        panFil.add(txtCodPed);
        txtCodPed.setBounds(75, 230, 45, 20);

        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        panFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(200, 230, 440, 20);

        lblCli.setText("Pedido:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(30, 230, 80, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(640, 230, 20, 20);

        txtCodAlt2.setToolTipText("Código Alterno 2 ");
        txtCodAlt2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAlt2FocusLost(evt);
            }
        });
        txtCodAlt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAlt2ActionPerformed(evt);
            }
        });
        panFil.add(txtCodAlt2);
        txtCodAlt2.setBounds(200, 120, 100, 20);

        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        panFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(120, 230, 80, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 120, 30, 20);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(" "));
        jPanel1.setLayout(null);

        jLabel1.setText("Código de item en letras:");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(10, 14, 146, 14);
        jPanel1.add(txtCodItmLet);
        txtCodItmLet.setBounds(156, 10, 48, 20);

        jLabel2.setText("Terminales:");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(210, 14, 70, 14);

        txtCodAltItmTer.setToolTipText("<HTML>\nSi desea consultar más de un terminal separe cada terminal por medio de una coma.\n<BR><FONT COLOR=\"blue\">Por ejemplo:</FONT> S,L,T,I\n</HTML>");
        txtCodAltItmTer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmTerActionPerformed(evt);
            }
        });
        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        jPanel1.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(282, 10, 80, 20);

        panFil.add(jPanel1);
        jPanel1.setBounds(292, 158, 370, 40);

        chkCfr.setSelected(true);
        chkCfr.setText("Mostrar CFR");
        chkCfr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCfrActionPerformed(evt);
            }
        });
        panFil.add(chkCfr);
        chkCfr.setBounds(30, 260, 110, 23);

        chkFob.setSelected(true);
        chkFob.setText("Mostrar FOB");
        chkFob.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkFobActionPerformed(evt);
            }
        });
        panFil.add(chkFob);
        chkFob.setBounds(30, 280, 110, 23);

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
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblDatMousePressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

        panBar.setPreferredSize(new java.awt.Dimension(320, 52));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

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

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItmMae.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItmMae.setText("");
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
        if (txtNomItm.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
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
                txtCodItmMae.setText("");
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
        if (txtCodAlt.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
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
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAltHasFocusLost

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if (txtCodAltDes.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
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

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) 
        {
            blnCon = true;
            if (objThrGUI == null)
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } 
        else
        {
            blnCon = false;
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

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarPedImp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
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
                mostrarPedImp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomEmp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);            
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarPedImp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodPedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPedActionPerformed
        txtCodPed.transferFocus();
    }//GEN-LAST:event_txtCodPedActionPerformed

    private void txtCodPedFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusGained
        strCodPed=txtCodPed.getText();
        txtCodPed.selectAll();
    }//GEN-LAST:event_txtCodPedFocusGained

    private void txtCodPedFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPedFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodPed.getText().equalsIgnoreCase(strCodPed))
        {
            if (txtCodPed.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                mostrarPedidos(1);
            }
        }
        else
            txtCodPed.setText(strCodPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodPedFocusLost

    private void txtPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPedIngImpActionPerformed
        txtPedIngImp.transferFocus();
    }//GEN-LAST:event_txtPedIngImpActionPerformed

    private void txtPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusGained
        strPedImp=txtPedIngImp.getText();
        txtPedIngImp.selectAll();
    }//GEN-LAST:event_txtPedIngImpFocusGained

    private void txtPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtPedIngImp.getText().equalsIgnoreCase(strPedImp))
        {
            if (txtPedIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtPedIngImp.setText("");
                txtNumDocIngImp.setText("");
            }
            else
            {
                mostrarPedidos(3);
            }
        }
        else
            txtPedIngImp.setText(strPedImp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtPedIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtPedIngImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        mostrarPedidos(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodPed.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butPedImpActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodPed.setText("");
            txtPedIngImp.setText("");
            txtNumDocIngImp.setText("");
            txtCodItmMae.setText("");
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
        txtCodAlt2.selectAll();
    }//GEN-LAST:event_txtCodAlt2FocusGained

    private void txtCodAlt2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusLost
        if (!txtCodAlt2.getText().equalsIgnoreCase(strCodAlt2))
        {
            if (txtCodAlt2.getText().equals(""))
            {
                txtCodItmMae.setText("");
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
        if (txtCodAlt2.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtNumDocIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocIngImpActionPerformed
        txtNumDocIngImp.transferFocus();
    }//GEN-LAST:event_txtNumDocIngImpActionPerformed

    private void txtNumDocIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusGained
        strNumPed=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumPed))
        {
            if (txtNumDocIngImp.getText().equals(""))
            {
                txtCodPed.setText("");
                txtNumDocIngImp.setText("");
                txtPedIngImp.setText("");
            }
            else
            {
                mostrarPedidos(2);
            }
        }
        else
            txtNumDocIngImp.setText(strNumPed);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNumDocIngImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

    private void txtCodAltItmTerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmTerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodAltItmTerActionPerformed

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
        optFil.setSelected(true);
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void tblDatMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMousePressed
        // TODO add your handling code here:
        calcularPorcentajeVariacion();
        calcularPromedio();
        calcularTercerQuartil();
        
        
    }//GEN-LAST:event_tblDatMousePressed

    private void chkCfrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCfrActionPerformed
        // TODO add your handling code here:
        mostrarColumnasCfr();
    }//GEN-LAST:event_chkCfrActionPerformed

    private void chkFobActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkFobActionPerformed
        // TODO add your handling code here:
        mostrarColumnasFob();
    }//GEN-LAST:event_chkFobActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPedImp;
    private javax.swing.JCheckBox chkCfr;
    private javax.swing.JCheckBox chkFob;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmLet;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtPedIngImp;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItmMae.setVisible(false);
            txtCodItm.setVisible(false);
//            txtCodPed.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConEmp();
            configurarPedidos();
            configurarVenConItm();
            //Configurar los JTables
            configurarTblDat();
            
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
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(8);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM,"Cód.Alt.Itm2.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");

            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            
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
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl=null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
             
            //Configurar JTable: Establecer el ListSelectionListener.
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        BigDecimal bgdPreVta, bgdPreVtaMarUtiPreVta, bgdCosUni, bgdFacCosUni, bgdPesUni, bgdPreObjKil=new BigDecimal("0");
        boolean blnRes=true;
        String strAuxFec="";
        BigDecimal bdeCanFobCfrTot=BigDecimal.ZERO;
        BigDecimal bdeCanItm=BigDecimal.ZERO;
        BigDecimal bdeCanFobCfrUni=BigDecimal.ZERO;
        try{
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
                
                if (txtCodAltItmTer.getText().length()>0){
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                
                if (txtCodItmLet.getText().length()>0){
                    strAux+="        AND ((LOWER(a1.tx_codAlt2) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt2) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                
                
                
                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAuxFec+=" AND c1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAuxFec+=" AND c1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAuxFec+=" AND c1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a1.tx_desCor";//, a1.nd_pesItmKgr, a1.nd_stkAct
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+="	, a" + (i+2) + ".nd_valTotFobCfr AS a" + (i+2) + "_nd_valTotFobCfr, a" + (i+2) + ".nd_valCfr AS a" + (i+2) + "_nd_valCfr";
                    strSQL+="	, a" + (i+2) + ".nd_can AS a" + (i+2) + "_nd_can";
                }
                               
                strSQL+=" 	, a1.nd_cosUni";
                strSQL+=" 	, a1.nd_preVta1, a1.nd_marUti, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a2.co_itmMae, a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a4.co_reg, a4.tx_desCor, a1.st_ser, a3.nd_stkAct, a1.nd_preVta1, a1.nd_marUti, a1.nd_pesItmKgr";
                strSQL+=" 	, a1.nd_cosUni, a1.nd_canMaxVen, a1.nd_facCosUni, a1.nd_preVtaObjKgr";
                strSQL+=" 	 FROM tbm_inv AS a1";
                strSQL+=" 	 INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" 	 LEFT OUTER JOIN (";
                strSQL+="               SELECT b1.co_itmMae, SUM(b3.nd_stkAct) AS nd_stkAct";
                strSQL+="               FROM ";
                strSQL+="               (tbm_inv AS b2 INNER JOIN tbm_equInv AS b1 ON b2.co_emp=b1.co_emp AND b2.co_itm=b1.co_itm)";
                strSQL+="               INNER JOIN tbm_invBod AS b3 ON b2.co_emp=b3.co_emp AND b2.co_itm=b3.co_itm";
                strSQL+="               WHERE b2.co_emp<>0";
                strSQL+="               AND (   CASE WHEN b3.co_emp=1 THEN b3.co_bod IN(4,13,14,16,17,18,19,24,25,27,30)";//tuval
                strSQL+="                            WHEN b3.co_emp=2 THEN b3.co_bod IN(6,13,14,16,17,18,19,24,25,27,30)";//castek
                strSQL+="                            WHEN b3.co_emp=4 THEN b3.co_bod IN(4,12,14,16,17,18,19,24,25,27,30)";//dimulti
                strSQL+="                       END";
                strSQL+="                   )";
                strSQL+="               AND (b2.tx_codAlt like '%S' OR b2.tx_codAlt like '%I')";
                strSQL+="                 GROUP BY b1.co_itmMae";
                strSQL+=" 	 ) AS a3";
                strSQL+=" 	 ON (a2.co_itmMae=a3.co_itmMae)";
                strSQL+=" 	 LEFT OUTER JOIN tbm_var AS a4 ON (a1.co_uni=a4.co_reg)";
                strSQL+=" 	 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 	 AND a1.st_reg='A'";
                strSQL+="" + strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+=" 	ORDER BY a1.tx_codAlt";
                strSQL+=" ) AS a1";
                for(int i=0; i<arlDatIngImp.size(); i++){
                    strSQL+=" LEFT OUTER JOIN(";
                    strSQL+="          SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, CASE WHEN a1.nd_pesitmkgr IS NULL THEN 0 ELSE a1.nd_pesitmkgr END AS nd_pesitmkgr";
                    strSQL+="         , CASE WHEN b1.nd_ara IS NULL THEN 0 ELSE b1.nd_ara END AS nd_ara";
                    strSQL+="         , b1.nd_can, b1.nd_preUniImp, b1.nd_canTonMet, b1.nd_valFle";
                    strSQL+="         , CASE WHEN c1.ne_tipnotped IN(2,4) THEN NULL ELSE b1.nd_valTotFobCfr END AS nd_valTotFobCfr";//TM CFR y PZA CFR
                    strSQL+="         , b1.nd_valCfr";
                    strSQL+="         , CASE WHEN b1.nd_valTotAra IS NULL THEN 0 ELSE b1.nd_valTotAra END AS nd_valTotAra";
                    strSQL+="         , b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor AS tx_desCorUniMed";
                    strSQL+="         , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp, b1.co_itmAct";
                    strSQL+="	      , c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                    strSQL+="         FROM tbm_cabMovInv AS c1 INNER JOIN tbm_detMovInv AS b1";
                    strSQL+="         ON c1.co_emp=b1.co_emp AND c1.co_loc=b1.co_loc AND c1.co_tipDoc=b1.co_tipDoc AND c1.co_doc=b1.co_doc";
                    strSQL+="         INNER JOIN (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2 ON a1.co_uni=a2.co_reg)";
                    strSQL+="         ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                    strSQL+="         INNER JOIN tbm_equInv AS a3 ON a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm";
                    strSQL+="         WHERE b1.co_emp=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_EMP) + "";
                    strSQL+="         AND b1.co_loc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_LOC) + "";
                    strSQL+="         AND b1.co_tipDoc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_TIP_DOC) + "";
                    strSQL+="         AND b1.co_doc=" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COD_DOC) + "";
                    strSQL+=strAuxFec;
                    strSQL+=" 	GROUP BY a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_pesitmkgr";
                    strSQL+=" 	, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                    strSQL+=" 	, b1.nd_ara, b1.nd_can, b1.nd_preUniImp, b1.nd_valTotFobCfr, b1.nd_canTonMet, b1.nd_valFle";
                    strSQL+="	, b1.nd_valCfr, b1.nd_valTotAra, b1.nd_valTotGas, b1.nd_cosTot, b1.nd_cosUni, b1.co_reg, a2.tx_desCor, b1.co_itmAct";
                    strSQL+="	, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc2, c1.fe_doc, c1.ne_tipnotped";
                    strSQL+=" 	ORDER BY c1.fe_doc, b1.co_reg, a1.tx_codAlt";
                    strSQL+=") AS a" + (i+2);
                    strSQL+=" ON a1.co_itmMae=a" + (i+2) + ".co_itmMae";
                }
                strSQL+=" ORDER BY a1.tx_codAlt";
                System.out.println("consultarReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_LET_ITM,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_desCor"));
                        //adicionar las columnas dinamicas
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            vecReg.add(j,     null);
                        }
                        
                        //columnas de ingresos por importacion
                        for(int j=intNumColIniIngImpVar; j<intNumColFinIngImpVar;j++){
                            vecReg.add(j,     null);
                        }
                        
                        //columnas de ingresos por importacion
                        for(int j=intNumColIniPro; j<intNumColFinPro;j++){
                            vecReg.add(j,     null);
                        }
                        
                        //columnas de quartiles
                        for(int j=intNumColIniTerQua; j<intNumColFinTerQua;j++){
                            vecReg.add(j,     null);
                        }
                
                        int i;
                        i=0;
                        for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                            bdeCanItm=new BigDecimal(rst.getObject("a" + (i+2) + "_nd_can")==null?"0":(rst.getString("a" + (i+2) + "_nd_can").equals("")?"0":rst.getString("a" + (i+2) + "_nd_can")));
                            if((j%2)==0){                                
                                bdeCanFobCfrTot=new BigDecimal(rst.getObject("a" + (i+2) + "_nd_valTotFobCfr")==null?"0":(rst.getString("a" + (i+2) + "_nd_valTotFobCfr").equals("")?"0":rst.getString("a" + (i+2) + "_nd_valTotFobCfr")));
                                if(bdeCanItm.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    bdeCanFobCfrUni=bdeCanFobCfrTot.divide(bdeCanItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                else
                                    bdeCanFobCfrUni=BigDecimal.ZERO;
                                if(bdeCanFobCfrUni.compareTo(BigDecimal.ZERO)!=0)
                                    vecReg.setElementAt(bdeCanFobCfrUni, j);
                                else
                                    vecReg.setElementAt(null, j);
                            }
                            else{
                                bdeCanFobCfrTot=new BigDecimal(rst.getObject("a" + (i+2) + "_nd_valCfr")==null?"0":(rst.getString("a" + (i+2) + "_nd_valCfr").equals("")?"0":rst.getString("a" + (i+2) + "_nd_valCfr")));
                                if(bdeCanItm.compareTo(new BigDecimal(BigInteger.ZERO))>0)
                                    bdeCanFobCfrUni=bdeCanFobCfrTot.divide(bdeCanItm, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                                else
                                    bdeCanFobCfrUni=BigDecimal.ZERO;
                                if(bdeCanFobCfrUni.compareTo(BigDecimal.ZERO)!=0)
                                    vecReg.setElementAt(bdeCanFobCfrUni, j);
                                else
                                    vecReg.setElementAt(null, j);
                            }
                            i++;
                        }

                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                    
                    //Configurar JTable: Establecer columnas editables.
                    vecAux=new Vector();
                    for(int j=intNumColIniIngImpVar; j<intNumColFinIngImpVar;j++){
                            vecAux.add(""+j);
                    }
                    objTblMod.setColumnasEditables(vecAux);
                    vecAux=null;
                                        
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                System.out.println("vecDat: " +  vecDat.toString());
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                
                objTblMod.initRowsState();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                
                mostrarColumnasFob();
                mostrarColumnasCfr();
                        
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
     * mostrar las "Empresas".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
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
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Pedidos".
     */
    private boolean configurarPedidos()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Cód.Tip.Doc.");
            arlAli.add("Cód.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Ped.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("100");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" FROM tbm_cabMovInv AS a1";
            strSQL+=" WHERE a1.co_tipDoc in(14,245) and a1.co_mnu=2889";
            strSQL+=" ORDER BY a1.fe_doc DESC";
            //System.out.println("configurarPedidos: " + strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            
            vcoPed=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Pedidos", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPed.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoPed.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
            arlCam.add("d1.co_itmMae");
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2"); 
            arlAli.add("Nom.Itm."); 
            arlAli.add("Uni.Med.");  
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("80");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
            strSQL+=" ORDER BY a1.tx_codAlt";
            
            //Ocultar Columnas
            int intColOcu[]=new int[1];
            intColOcu[0]=2;  // Cód.Itm.
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
           // vcoItm.setCampoBusqueda(2);
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
    private boolean mostrarPedImp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.setVisible(true);
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
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
    private boolean mostrarPedidos(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPed.setCampoBusqueda(5);
                    vcoPed.setVisible(true);
                    if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));

                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(3);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                        }
                        else
                        {
                            txtCodPed.setText(strCodPed); 
                        }
                    }
                    break;
               
                case 2: //Búsqueda directa por "Número Documento".
                    if (vcoPed.buscar("a1.ne_numDoc", txtNumDocIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(4);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                        }
                        else
                        {
                            txtNumDocIngImp.setText(strNumPed); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoPed.buscar("a1.tx_numDoc2", txtPedIngImp.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                    }
                    else
                    {
                        vcoPed.setCampoBusqueda(5);
                        vcoPed.setCriterio1(11);
                        vcoPed.cargarDatos();
                        vcoPed.setVisible(true);
                        if (vcoPed.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodPed.setText(vcoPed.getValueAt(4));
                            txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                            txtPedIngImp.setText(vcoPed.getValueAt(6));
                        }
                        else
                        {
                            txtPedIngImp.setText(strPedImp);
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
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5)); 
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
                        txtCodItmMae.setText(vcoItm.getValueAt(1));
                        txtCodItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt.setText(vcoItm.getValueAt(3));
                        txtCodAlt2.setText(vcoItm.getValueAt(4));
                        txtNomItm.setText(vcoItm.getValueAt(5)); 
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(4);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItmMae.setText(vcoItm.getValueAt(1));
                            txtCodItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt.setText(vcoItm.getValueAt(3));
                            txtCodAlt2.setText(vcoItm.getValueAt(4));
                            txtNomItm.setText(vcoItm.getValueAt(5));
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
        public void run(){
            if(configurarColumnasAdicionar()){
                if (!cargarDetReg()){
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                //Establecer el foco en el JTable sólo cuando haya datos.
                if (tblDat.getRowCount()>0){
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
            switch (intCol)
            {
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg="Código del item en letras";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida del item";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
  
    
    
   /**
    * Función que permite obtener ingresos por importación
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */ 
   private boolean cargarIngresosImportacion(){
        boolean blnRes=true;
        Connection conIngImp;
        Statement stmIngImp;
        ResultSet rstIngImp;
        String strTitFobCfrColUno="", strTitFobCfrColDos="";
        String strAuxFec="";
        try{
            conIngImp=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conIngImp!=null){
                stmIngImp=conIngImp.createStatement();
                strAux="";
                if(!  txtCodItm.getText().toString().equals(""))
                    strAux+="   AND a1.co_itm=" + txtCodItm.getText()  + "";
                    
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+="        AND ((LOWER(a1.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (txtCodItmLet.getText().length()>0)
                    strAux+="        AND ((LOWER(a1.tx_codAlt2) IN '" + txtCodItmLet.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_codAlt2) LIKE '" + txtCodItmLet.getText().replaceAll("'", "''").toLowerCase() + "%')";
                
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strAux+=getConSQLAdiCamTer("a1.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAuxFec+=" AND c1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAuxFec+=" AND c1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAuxFec+=" AND c1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }

                
                
                
                
                strSQL="";
                strSQL+="SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+=" , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+=" FROM(";
                strSQL+="   SELECT x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                strSQL+="   FROM(";
                strSQL+="        SELECT a1.tx_codAlt, a1.tx_nomItm";
                strSQL+="        , c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+="	 , c1.co_emp AS co_empIngImp, c1.co_loc AS co_locIngImp, c1.co_tipDoc AS co_tipDocIngImp, c1.co_doc AS co_docIngImp, c1.tx_numDoc2 AS tx_numIngImp, c1.fe_doc AS fe_docIngImp";
                strSQL+="        FROM (tbm_detMovInv AS b1 INNER JOIN tbm_cabMovInv AS c1";
                strSQL+="                  ON b1.co_emp=c1.co_emp AND b1.co_loc=c1.co_loc AND b1.co_tipDoc=c1.co_tipDoc AND b1.co_doc=c1.co_doc";
                strSQL+="        INNER JOIN tbm_cabPedEmbImp AS d1";
                strSQL+="        ON c1.co_emprelpedembimp=d1.co_emp AND c1.co_locrelpedembimp=d1.co_loc AND c1.co_tipdocrelpedembimp=d1.co_tipDoc AND c1.co_docrelpedembimp=d1.co_doc)";
                strSQL+="        INNER JOIN tbm_inv AS a1 ON b1.co_emp=a1.co_emp AND b1.co_itm=a1.co_itm";
                strSQL+="        WHERE c1.co_tipDoc IN(14,245) AND c1.st_reg='A' AND d1.st_reg='A'";
                strSQL+=strAux;
                strSQL+="        AND (a1.tx_codAlt like '%S' OR a1.tx_codAlt like '%I')";
                strSQL+=strAuxFec;
                strSQL+="	 GROUP BY a1.tx_codAlt, a1.tx_nomItm";
                strSQL+=" 	, c1.co_emprelpedembimp, c1.co_locrelpedembimp, c1.co_tipdocrelpedembimp, c1.co_docrelpedembimp";
                strSQL+=" 	, c1.co_emp, c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.tx_numDoc2, c1.fe_doc";
                strSQL+="	ORDER BY c1.fe_doc, a1.tx_codAlt";
                strSQL+="   ) AS x";
                strSQL+="   GROUP BY x.co_emprelpedembimp, x.co_locrelpedembimp, x.co_tipdocrelpedembimp, x.co_docrelpedembimp";
                strSQL+="   , x.co_empIngImp, x.co_locIngImp, x.co_tipDocIngImp, x.co_docIngImp, x.tx_numIngImp, x.fe_docIngImp";
                //strSQL+="   ORDER BY x.fe_docIngImp DESC LIMIT 3";
                strSQL+="   ORDER BY x.fe_docIngImp";
                strSQL+=") AS x";
                strSQL+=" ORDER BY x.fe_docIngImp ASC, x.tx_numIngImp";
                System.out.println("cargarCabeceraIngresosImportacion: " + strSQL);
                rstIngImp=stmIngImp.executeQuery(strSQL);
                arlDatIngImp.clear();
                for(int i=0; rstIngImp.next(); i++){
//                    if((i%2)==0){
//                        strTitFobCfrColUno="CFR";
//                        strTitFobCfrColDos="FOB";
//                    }//impar
//                    else
                        strTitFobCfrColUno="FOB";
                        strTitFobCfrColDos="CFR";
                    
                    //primera columna
                    arlRegIngImp=new ArrayList();
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP,               "" + rstIngImp.getString("co_empIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC,               "" + rstIngImp.getString("co_locIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC,           "" + rstIngImp.getString("co_tipDocIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC,               "" + rstIngImp.getString("co_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_NUM,                   "" + rstIngImp.getString("tx_numIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegIngImp.add(INT_ARL_ING_IMP_EST_COL,               "");
                    arlRegIngImp.add(INT_ARL_ING_IMP_FEC,                   "" + rstIngImp.getString("fe_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_FOB_CFR,               "" + strTitFobCfrColUno);
                    arlDatIngImp.add(arlRegIngImp);
                    //segunda columna
                    arlRegIngImp=new ArrayList();
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_EMP,               "" + rstIngImp.getString("co_empIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_LOC,               "" + rstIngImp.getString("co_locIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_TIP_DOC,           "" + rstIngImp.getString("co_tipDocIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COD_DOC,               "" + rstIngImp.getString("co_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_NUM,                   "" + rstIngImp.getString("tx_numIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_COL,                   "" + ((i==0)?"S":"" + ((i==1)?"S": ""+ ((i==2)?"S":"") )));
                    arlRegIngImp.add(INT_ARL_ING_IMP_EST_COL,               "");
                    arlRegIngImp.add(INT_ARL_ING_IMP_FEC,                   "" + rstIngImp.getString("fe_docIngImp"));
                    arlRegIngImp.add(INT_ARL_ING_IMP_FOB_CFR,               "" + strTitFobCfrColDos);
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
 
    
    
    
    
    
    /**
     * Función que permite agregar al modelo las columnas de los ingresos por importación
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
   private boolean agregarColumnasIngresosImportacion(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
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
                    objTblCelRenLblColDinIngImp.setBackground(new Color(165,226,166));
//                    objTblCelRenLblColDinIngImp.setBackground(new Color(255,151,151));
//                    objTblCelRenLblColDinIngImp.setBackground(javax.swing.UIManager.getColor("Table.background"));
                }
            });            
            
            for (int i=0; i<intNumColAddIngImp; i++){
                //por primera columna
                //strNomIngImp="<HTML><DIV ALIGN=center>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM) + "<BR>" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_FOB_CFR) + "</DIV></HTML>";
                strNomIngImp="" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_FOB_CFR) + "";
                
                objUti.setStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COL, "" + (intNumColIniIngImp+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniIngImp+i);
                tbc.setHeaderValue(strNomIngImp);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinIngImp);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if((i%2)==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM));
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
   
   
   
    
    private boolean configurarColumnasAdicionar(){
        boolean blnRes=false;
        try{
            if(eliminaColumnasAdicionadas()){
                if(cargarIngresosImportacion()){
                    if(agregarColumnasIngresosImportacion()){                        
                        if(agregarColumnasIngresoImportacionVariación()){
                            if(agregarColumnaCalculoPromedio()){
                                if(agregarColumnaCalculoTercerQuartil()){
                                    blnRes=true;
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

   /**
    * Función que permite 
    * @return 
    */
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
    
    
    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColumnaCalculoPromedio(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiPro=1;
        try{
            intNumColIniPro=objTblMod.getColumnCount();
            objTblCelRenLblColDinVar=new ZafTblCelRenLbl();
            objTblCelRenLblColDinVar.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinVar.setTipoFormato(objTblCelRenLblColDinVar.INT_FOR_NUM);
            objTblCelRenLblColDinVar.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            objTblCelRenLblColDinVar.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //objTblCelRenLblColDinVar.setBackground(new Color(165,226,166));
                    //objTblCelRenLblColDinVar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                }
            });   
            
            
            for (int i=0; i<intNumColAdiPro; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniPro+i);
                switch(i){
                    case 0:
                        tbc.setHeaderValue("Pro.Cal.");
                        break;
                }
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinVar);
//                tbc.setCellEditor(objTblCelEdiTxtVar);
//                objTblCelEdiTxtVar.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                    int intNumFilSel=-1;
//                    BigDecimal bdeValVarFilSel=BigDecimal.ZERO;
//                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                        System.out.println("1 beforeEdit - agregarColumnasImportacionPedido1: ");
//                        intNumFilSel=objTblCelRenLblColDinVar.getRowRender();
//                    }
//                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                        System.out.println("1 afterEdit - agregarColumnasImportacionPedido1: ");
//                        bdeValVarFilSel=new BigDecimal(objTblMod.getValueAt(intNumFilSel, intNumColIniPro)==null?"0":(objTblMod.getValueAt(intNumFilSel, intNumColIniPro).toString().equals("")?"0":objTblMod.getValueAt(intNumFilSel, intNumColIniPro).toString()));
//                        
//                        if(bdeValIngCalVar.compareTo(new BigDecimal("0"))!=0){
//                            if(bdeValVarFilSel.compareTo(new BigDecimal("0"))!=0){
//                                mostrarMsgInf("<HTML>Existe un valor ingresado para el cálculo.<BR>Elimine ese valor y vuelva a intentarlo</HTML>");
//                                objTblMod.setValueAt(null, intNumFilSel, tblDat.getSelectedColumn());
//                            }
//                            else{
//                                bdeValIngCalVar=BigDecimal.ZERO;
//                            }
//                        }
//                        else{
//                            if(objUti.isNumero(objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()).toString())){
//                                //el valor ingresado es cambiado al +- valor entero(se quitan los decimales)
//                                bdeValIngCalVar=new BigDecimal(objTblMod.getValueAt(intNumFilSel, intNumColIniPro)==null?"0":(objTblMod.getValueAt(intNumFilSel, intNumColIniPro).toString().equals("")?"0":objTblMod.getValueAt(intNumFilSel, intNumColIniPro).toString()));
//                            }
//                            else{
//                                objTblMod.setValueAt(null, intNumFilSel, tblDat.getSelectedColumn());
//                                if(bdeValVarFilSel.compareTo(new BigDecimal("0"))==0){
//                                    bdeValIngCalVar=BigDecimal.ZERO;
//                                }
//                            }
//                                
//                        }
//                        
//                        
//                    }
//                });
                
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                tbc=null;
            }
            intNumColFinPro=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite agregar al modelo las columnas de los ingresos por importación con variación
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
   private boolean agregarColumnasIngresoImportacionVariación(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;

        intNumColAddIngImpVar=arlDatIngImp.size();//
        intNumColIniIngImpVar=objTblMod.getColumnCount();//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomIngImpVar="";
        try{
            objTblCelRenLblColDinIngImpVar=new ZafTblCelRenLbl();
            objTblCelRenLblColDinIngImpVar.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinIngImpVar.setTipoFormato(objTblCelRenLblColDinIngImpVar.INT_FOR_NUM);
            objTblCelRenLblColDinIngImpVar.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblColDinIngImpVar.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    objTblCelRenLblColDinIngImpVar.setBackground(new Color(255,221,187));
                }
            });            
            objTblCelEdiTxtIngImp=new ZafTblCelEdiTxt();
            
            for (int i=0; i<intNumColAddIngImpVar; i++){
                strNomIngImpVar="" + objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_FOB_CFR) + "(.Var.)";
                
                objUti.setStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_COL, "" + (intNumColIniIngImpVar+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniIngImpVar+i);
                tbc.setHeaderValue(strNomIngImpVar);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinIngImpVar);
                tbc.setCellEditor(objTblCelEdiTxtIngImp);
                objTblCelEdiTxtIngImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    }
                });

                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                if((i%2)==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp(objUti.getStringValueAt(arlDatIngImp, i, INT_ARL_ING_IMP_NUM));
                    objTblHeaColGrpTit.setHeight(16);                    
                }
                                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                
            }
            intNumColFinIngImpVar=objTblMod.getColumnCount();
            
            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
   
    /**
     * Función que obtiene el número de columnas que se van a adicionar al modelo en tiempo de ejecución
     * @return true: Si se pudo efectuar la operación
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColumnaCalculoTercerQuartil(){//para las columnas que van despues de las columnas chk de bancos
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        intNumColAdiTerQua=1;
        try{
            intNumColIniTerQua=objTblMod.getColumnCount();
            objTblCelRenLblColDinTerQua=new ZafTblCelRenLbl();
            objTblCelRenLblColDinTerQua.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColDinTerQua.setTipoFormato(objTblCelRenLblColDinTerQua.INT_FOR_NUM);
            objTblCelRenLblColDinTerQua.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            objTblCelRenLblColDinTerQua.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    //objTblCelRenLblColDinVar.setBackground(new Color(165,226,166));
                    //objTblCelRenLblColDinVar.setBackground(javax.swing.UIManager.getColor("Table.background"));
                }
            });   
            
            
            for (int i=0; i<intNumColAdiTerQua; i++){
                tbc=new javax.swing.table.TableColumn(intNumColIniTerQua+i);
                switch(i){
                    case 0:
                        tbc.setHeaderValue("Q3");
                        break;
                }
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                
                //Configurar JTable: Renderizar celdas.
                tbc.setCellRenderer(objTblCelRenLblColDinTerQua);
                
                
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                tbc=null;
            }
            intNumColFinTerQua=objTblMod.getColumnCount();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

   
   
   
   
   
   /**
    * Función que permite calcular los porcentajes de variación de los pedidos
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */
    private boolean calcularPorcentajeVariacion(){
        boolean blnRes=true;
        int intFilCelSel=tblDat.getSelectedRow();
        int intColCelSel=tblDat.getSelectedColumn();
        BigDecimal bdeValSelCalVar=BigDecimal.ZERO;
        int intPorItmSel=100;
        //columnas de ingreso por importacion
        int intColPedCalIngImp=-1;
        BigDecimal bdeValColPedValIngImp=BigDecimal.ZERO;
        //columnas de ingreso por importacion calculo variacion
        //int intColPedCalIngImpVar=-1;
        BigDecimal bdeValColPedValIngImpVar=BigDecimal.ZERO;
        try{
            System.out.println("Celda seleccionada: " + objTblMod.getValueAt(intFilCelSel, intColCelSel));
            if(  (intColCelSel>=intNumColIniIngImp) && (intColCelSel<=intNumColFinIngImp)  ){
                //limpiar columnas del modelo de las variaciones antes calculadas
                for(int j=intNumColIniIngImpVar; j<intNumColFinIngImpVar; j++){
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        objTblMod.setValueAt(null, i, j);
                    }
                }
                
                
                
                
                //generar calculos
                for(int j=intNumColIniIngImp; j<intNumColFinIngImp; j++){
                    //bdeValSelCalVar=new BigDecimal(objTblMod.getValueAt(intFilCelSel, intColCelSel)==null?"0":(objTblMod.getValueAt(intFilCelSel, intColCelSel).toString().equals("")?"0":objTblMod.getValueAt(intFilCelSel, intColCelSel).toString()));
                    bdeValSelCalVar=new BigDecimal(objTblMod.getValueAt(intFilCelSel, j)==null?"0":(objTblMod.getValueAt(intFilCelSel, j).toString().equals("")?"0":objTblMod.getValueAt(intFilCelSel, j).toString()));
                    intColPedCalIngImp++;
                    if(bdeValSelCalVar.compareTo(BigDecimal.ZERO)>0){
                        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                            bdeValColPedValIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                            if(bdeValColPedValIngImp.compareTo(BigDecimal.ZERO)>0){
                                for(int k=intNumColIniIngImpVar; k<intNumColFinIngImpVar; k++){
                                    //bdeValColPedValIngImpVar=((bdeValSelCalVar.multiply(new BigDecimal("100"))).divide(bdeValColPedValIngImp, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
                                    bdeValColPedValIngImpVar=((bdeValColPedValIngImp.multiply(new BigDecimal("100"))).divide(bdeValSelCalVar, objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP));
                                    objTblMod.setValueAt(bdeValColPedValIngImpVar, i, (k+intColPedCalIngImp));
                                    bdeValColPedValIngImpVar=BigDecimal.ZERO;
                                    break;
                                }
                            }
                        }
                    }
                    //intColPedCalIngImp=-1;    

                }
                
            }
            else{
                System.out.println("NO ");
            }
            
            
            
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   
    
   /**
    * Función que permite calcular los porcentajes de variación de los pedidos
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */
    private boolean calcularPromedio(){
        boolean blnRes=true;
        BigDecimal bdeValColPedValIngImpVar=BigDecimal.ZERO;
        BigDecimal bdeValColPedValIngImpVarTot;
        int intColDat=0;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeValColPedValIngImpVarTot=BigDecimal.ZERO;
                intColDat=0;
                
                for(int j=intNumColIniIngImpVar; j<intNumColFinIngImpVar; j++){
                    bdeValColPedValIngImpVar=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
                    if(bdeValColPedValIngImpVar.compareTo(BigDecimal.ZERO)>0){
                        intColDat++;
                        bdeValColPedValIngImpVarTot=bdeValColPedValIngImpVarTot.add(bdeValColPedValIngImpVar);
                    }
                }
                if(intColDat>0)
                    bdeValColPedValIngImpVarTot=bdeValColPedValIngImpVarTot.divide(new BigDecimal(""+intColDat), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
                //bdeValColPedValIngImpVarTot=objUti.redondearBigDecimal(bdeValColPedValIngImpVarTot, objParSis.getDecimalesMostrar());
                objTblMod.setValueAt(bdeValColPedValIngImpVarTot, i, intNumColIniPro);
            }
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
   /**
    * Función que permite calcular los porcentajes de variación de los pedidos
    * @return true Si se pudo realizar la operación
    * <BR> false Caso contrario
    */
    private boolean calcularTercerQuartil(){
        boolean blnRes=true;
//        BigDecimal bdeValColPedValIngImp=BigDecimal.ZERO;
//        BigDecimal bdeValColPedValIngImpVarTot;
//        int intColDat=0;
//        try{
//            //ordenar datos
//            arlDatValVarIngImp.clear();
////            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
////                for(int j=intNumColIniIngImp; j<intNumColFinIngImp; j++){
////                    bdeValColPedValIngImp=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
////                    if(bdeValColPedValIngImp.compareTo(BigDecimal.ZERO)>0){
////                        arlRegValVarIngImp=new ArrayList();
////                        arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, bdeValColPedValIngImp);
////                        arlDatValVarIngImp.add(arlRegValVarIngImp);
////                    }
////
////                }
////            }
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "4");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "8");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "2");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "1");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "5");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "3");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "6");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            arlRegValVarIngImp=new ArrayList();
//            arlRegValVarIngImp.add(INT_ARL_VAL_VAR_ING_IMP, "7");
//            arlDatValVarIngImp.add(arlRegValVarIngImp);
//            
//            
//            
//            
//            System.out.println("arlDatValVarIngImp-Antes:  " + arlDatValVarIngImp.toString());
//            java.util.Collections.sort(arlDatValVarIngImp);
//            System.out.println("arlDatValVarIngImp-Despues:  " + arlDatValVarIngImp.toString());
//
//            
////            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
////                bdeValColPedValIngImpVarTot=BigDecimal.ZERO;
////                intColDat=0;
////                
////                for(int j=intNumColIniTerQua; j<intNumColFinTerQua; j++){
////                    bdeValColPedValIngImpVar=new BigDecimal(objTblMod.getValueAt(i, j)==null?"0":(objTblMod.getValueAt(i, j).toString().equals("")?"0":objTblMod.getValueAt(i, j).toString()));
////                    if(bdeValColPedValIngImpVar.compareTo(BigDecimal.ZERO)>0){
////                        intColDat++;
////                        bdeValColPedValIngImpVarTot=bdeValColPedValIngImpVarTot.add(bdeValColPedValIngImpVar);
////                    }
////                }
////                if(intColDat>0)
////                    bdeValColPedValIngImpVarTot=bdeValColPedValIngImpVarTot.divide(new BigDecimal(""+intColDat), objParSis.getDecimalesMostrar(), BigDecimal.ROUND_HALF_UP);
////                //bdeValColPedValIngImpVarTot=objUti.redondearBigDecimal(bdeValColPedValIngImpVarTot, objParSis.getDecimalesMostrar());
////                objTblMod.setValueAt(bdeValColPedValIngImpVarTot, i, intNumColIniPro);
////            }
//        }
//        catch(Exception e){
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
        return blnRes;
    }
    
    
    
    
   //FOB   CFR
    boolean mostrarColumnasCfr(){
        boolean blnRes=true;
        try{
            //columnas de ingreso por importacion
            for(int j=(intNumColFinIngImp-1); j>=intNumColIniIngImp; j--){
                System.out.println("j: " + j);
                if(chkCfr.isSelected()){
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                }
                else{
                    objTblMod.addSystemHiddenColumn(j, tblDat);
                }
                j--;
            }
            //columnas de ingreso por importacion de variacion
            for(int j=(intNumColFinIngImpVar-1); j>=intNumColIniIngImpVar; j--){
                if(chkCfr.isSelected()){
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                }
                else{
                    objTblMod.addSystemHiddenColumn(j, tblDat);
                }
                j--;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
   
    boolean mostrarColumnasFob(){
        boolean blnRes=true;
        try{
            //columnas de ingreso por importacion
            for(int j=(intNumColFinIngImp-1); j>=intNumColIniIngImp; j--){
                j--;
                if(chkFob.isSelected()){
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                }
                else{
                    objTblMod.addSystemHiddenColumn(j, tblDat);
                }
                
            }
            //columnas de ingreso por importacion de variacion
            for(int j=(intNumColFinIngImpVar-1); j>=intNumColIniIngImpVar; j--){
                j--;
                if(chkFob.isSelected()){
                    objTblMod.removeSystemHiddenColumn(j, tblDat);
                }
                else{
                    objTblMod.addSystemHiddenColumn(j, tblDat);
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