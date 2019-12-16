/*
 * ZafVen04.java
 *
 * Created on 29 de diciembre de 2005, 10:10 PM
 */
package Ventas.ZafVen04;
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
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafVenCon.ZafVenConItm01;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafVen04 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_ITM=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_COD_ALT_2=4;                   //Código del item (Alterno 2).
    static final int INT_TBL_DAT_NOM_ITM=5;                     //Nombre del item.
    static final int INT_TBL_DAT_TOT_UNI=6;                     //Total de unidades vendidas.
    static final int INT_TBL_DAT_STK_ACT=7;                     //Stock actual.
    static final int INT_TBL_DAT_PRE_VTA1=8;                    //Precio de venta 1.
    static final int INT_TBL_DAT_VEN_TOT=9;                     //Venta total.
    static final int INT_TBL_DAT_COS_TOT=10;                    //Costo total.
    static final int INT_TBL_DAT_VAL_GAN=11;                    //Valor de ganancia.
    static final int INT_TBL_DAT_POR_GAN=12;                    //Porcentaje de ganancia.
    static final int INT_TBL_DAT_PES_TOT_KGR=13;                //Peso total en Kilogramos.
    
    static final int INT_TBL_DET_LIN=0;                         //Línea.
    static final int INT_TBL_DET_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_DET_NOM_LOC=2;                     //Nombre del local.
    static final int INT_TBL_DET_COD_TIP_D0C=3;                 //Código del tipo de documento.
    static final int INT_TBL_DET_DEC_TIP_DOC=4;                 //Descripción corta del Tipo de documento.
    static final int INT_TBL_DET_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_DET_NUM_DOC=6;                     //Número de documento.
    static final int INT_TBL_DET_FEC_DOC=7;                     //Fecha del documento.
    static final int INT_TBL_DET_COD_CLI=8;                     //Código del cliente.
    static final int INT_TBL_DET_NOM_CLI=9;                     //Nombre del cliente.
    static final int INT_TBL_DET_CAN=10;                        //Cantidad.
    static final int INT_TBL_DET_PRE_UNI=11;                    //Precio unitario.
    static final int INT_TBL_DET_COS_UNI=12;                    //Costo unitario.
    static final int INT_TBL_DET_VEN_TOT=13;                    //Venta total.
    static final int INT_TBL_DET_COS_TOT=14;                    //Costo total.
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDab;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoEmp;                                   //Ventana de consulta "Empresa".
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Clientes".
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafVenConItm01 objVenConItm01;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        //Para el stock de cada bodega.
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;

    /** Crea una nueva instancia de la clase ZafVen04. */
    public ZafVen04(ZafParSis obj)
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
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        chkMosLocIna = new javax.swing.JCheckBox();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        chkVenCli = new javax.swing.JCheckBox();
        chkVenRel = new javax.swing.JCheckBox();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        butCli = new javax.swing.JButton();
        chkPre = new javax.swing.JCheckBox();
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
        butConStkBod = new javax.swing.JButton();
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

        spnFil.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 156, 400, 20);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 136, 400, 20);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panLoc.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

        panFil.add(panLoc);
        panLoc.setBounds(24, 256, 640, 122);

        lblVen.setText("Vendedor/Comprador:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(24, 216, 120, 20);

        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(144, 216, 56, 20);

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
        txtNomVen.setBounds(200, 216, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 216, 20, 20);

        chkVenCli.setSelected(true);
        chkVenCli.setText("Ventas a clientes");
        panFil.add(chkVenCli);
        chkVenCli.setBounds(4, 76, 400, 20);

        chkVenRel.setSelected(true);
        chkVenRel.setText("Ventas a empresas relacionadas");
        panFil.add(chkVenRel);
        chkVenRel.setBounds(4, 96, 400, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Empresa");
        panFil.add(lblEmp);
        lblEmp.setBounds(24, 176, 120, 20);

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
        txtCodEmp.setBounds(144, 176, 56, 20);

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
        txtNomEmp.setBounds(200, 176, 440, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(640, 176, 20, 20);

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
        txtCodCli.setBounds(144, 196, 56, 20);

        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(200, 196, 440, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil.add(lblCli);
        lblCli.setBounds(24, 196, 120, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 196, 20, 20);

        chkPre.setSelected(true);
        chkPre.setText("Préstamos");
        panFil.add(chkPre);
        chkPre.setBounds(4, 116, 400, 20);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);
        spnFil.getAccessibleContext().setAccessibleParent(tabFrm);

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

        panBar.setPreferredSize(new java.awt.Dimension(320, 54));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 35));
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

        butConStkBod.setText("Movimientos");
        butConStkBod.setToolTipText("Mostrar movimientos del item seleccionado");
        butConStkBod.setPreferredSize(new java.awt.Dimension(100, 25));
        butConStkBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConStkBodActionPerformed(evt);
            }
        });
        panBot.add(butConStkBod);

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

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed

    private void chkMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovRegActionPerformed
        if (chkMosMovReg.isSelected())
            cargarMovReg();
        else
            objTblModDab.removeAllRows();
    }//GEN-LAST:event_chkMosMovRegActionPerformed

    private void butConStkBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConStkBodActionPerformed
        cargarMovReg();
    }//GEN-LAST:event_butConStkBodActionPerformed

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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
}//GEN-LAST:event_txtCodVenActionPerformed

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
}//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodVen.getText().equalsIgnoreCase(strCodVen))
        {
            if (txtCodVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(1);
            }
        }
        else
            txtCodVen.setText(strCodVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodVenFocusLost

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
}//GEN-LAST:event_txtNomVenActionPerformed

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
}//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomVen.getText().equalsIgnoreCase(strNomVen))
        {
            if (txtNomVen.getText().equals(""))
            {
                txtCodVen.setText("");
                txtNomVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
            txtNomVen.setText(strNomVen);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomVenFocusLost

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodVen.getText().length()>0)
        {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butVenActionPerformed

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
                mostrarVenConEmp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0)
        {
            optFil.setSelected(true);
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
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomEmp.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(1);
            }
        }
        else
            txtCodCli.setText(strCodCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtDesLarCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtCodVen.setText("");
            txtNomVen.setText("");
            objVenConItm01.limpiar();
        }
    }//GEN-LAST:event_optTodItemStateChanged

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
    private javax.swing.JButton butConStkBod;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JCheckBox chkPre;
    private javax.swing.JCheckBox chkVenCli;
    private javax.swing.JCheckBox chkVenRel;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panMovReg;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptMovReg;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnMovReg;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblMovReg;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomVen;
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
            panFil.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Configurar ZafVenConItm01:
            objVenConItm01=new ZafVenConItm01(objParSis, "a3.", optFil);
            panFil.add(objVenConItm01);
            objVenConItm01.setBounds(24, 236, 636, 20);
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.27");
            lblTit.setText(strAux);
            //Configurar objetos.
            switch (objParSis.getCodigoMenu())
            {
                case 226: //Listado de ventas por item...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(395))
                    {
                        butCon.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(396))
                    {
                        butCer.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3085))
                    {
                        chkVenCli.setSelected(false);
                        chkVenCli.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3086))
                    {
                        chkVenRel.setSelected(false);
                        chkVenRel.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3723))
                    {
                        chkPre.setSelected(false);
                        chkPre.setEnabled(false);
                    }
                    break;
                case 229: //Listado de ventas por item (Unidades)...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(401))
                    {
                        butCon.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(402))
                    {
                        butCer.setVisible(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3175))
                    {
                        chkVenCli.setSelected(false);
                        chkVenCli.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3176))
                    {
                        chkVenRel.setSelected(false);
                        chkVenRel.setEnabled(false);
                    }
                    if (!objPerUsr.isOpcionEnabled(3724))
                    {
                        chkPre.setSelected(false);
                        chkPre.setEnabled(false);
                    }
                    butConStkBod.setVisible(false);
                    break;
            }
            switch (objParSis.getCodigoMenu())
            {
                case 226: //Listado de ventas por item...
                    break;
                case 229: //Listado de ventas por item (Unidades)...
                default:
                    panRptMovReg.setVisible(false);
                    break;
            }
            //Configurar las ZafVenCon.
            configurarVenConEmp();
            configurarVenConCli();
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblDat();
            configurarTblDet();
            cargarLoc();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodCli.setEditable(false);
                txtDesLarCli.setEditable(false);
                butCli.setEnabled(false);
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
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Ordenar tabla
            objTblOrdLoc=new ZafTblOrd(tblLoc);
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch (Exception e)
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
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_2,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_TOT_UNI,"Uni.Ven.");
            vecCab.add(INT_TBL_DAT_STK_ACT,"Stock");
            vecCab.add(INT_TBL_DAT_PRE_VTA1,"Pre.Vta.1");
            vecCab.add(INT_TBL_DAT_VEN_TOT,"Ven.Tot.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_VAL_GAN,"Ganancia");
            vecCab.add(INT_TBL_DAT_POR_GAN,"Margen");
            vecCab.add(INT_TBL_DAT_PES_TOT_KGR,"Pes.Tot.(Kg)");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_TOT_UNI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VEN_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_GAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_GAN).setPreferredWidth(57);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setPreferredWidth(75);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 226: //Listado de ventas por item...
                    break;
                case 229: //Listado de ventas por item (Unidades)...
                default:
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PRE_VTA1, tblDat);
                    //Ficha "Reporte": Tabla->Mostrar "Venta total"
                    if (objPerUsr.isOpcionEnabled(3921))
                    {
                        tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(285);
                    }
                    else
                    {
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VEN_TOT, tblDat);
                        tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(365);
                    }
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_TOT, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_GAN, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_GAN, tblDat);
                    break;
            }
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_2).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_TOT_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VEN_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_GAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_GAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_KGR).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_TOT_UNI, INT_TBL_DAT_VEN_TOT, INT_TBL_DAT_COS_TOT, INT_TBL_DAT_PES_TOT_KGR};
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
            vecCabMov=new Vector(15);  //Almacena las cabeceras
            vecCabMov.clear();
            vecCabMov.add(INT_TBL_DET_LIN,"");
            vecCabMov.add(INT_TBL_DET_COD_LOC,"Cód.Loc.");
            vecCabMov.add(INT_TBL_DET_NOM_LOC,"Nom.Loc.");
            vecCabMov.add(INT_TBL_DET_COD_TIP_D0C,"Cód.Tip.Doc.");
            vecCabMov.add(INT_TBL_DET_DEC_TIP_DOC,"Tip.Doc.");
            vecCabMov.add(INT_TBL_DET_COD_DOC,"Cod.Doc.");
            vecCabMov.add(INT_TBL_DET_NUM_DOC,"Núm.Doc.");
            vecCabMov.add(INT_TBL_DET_FEC_DOC,"Fec.Doc.");
            vecCabMov.add(INT_TBL_DET_COD_CLI,"Cód.Cli.");
            vecCabMov.add(INT_TBL_DET_NOM_CLI,"Nom.Cli.");
            vecCabMov.add(INT_TBL_DET_CAN,"Can.");
            vecCabMov.add(INT_TBL_DET_PRE_UNI,"Pre.Uni.");
            vecCabMov.add(INT_TBL_DET_COS_UNI,"Cos.Uni.");
            vecCabMov.add(INT_TBL_DET_VEN_TOT,"Ven.Tot.");
            vecCabMov.add(INT_TBL_DET_COS_TOT,"Cos.Tot.");
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
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_NOM_LOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_D0C).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_NUM_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DET_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_NOM_CLI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_CAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_PRE_UNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_COS_UNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_VEN_TOT).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DET_COS_TOT).setPreferredWidth(55);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_COD_LOC, tblMovReg);
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_COD_TIP_D0C, tblMovReg);
            objTblModDab.addSystemHiddenColumn(INT_TBL_DET_COD_DOC, tblMovReg);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblMovReg.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblMovReg);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_PRE_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_VEN_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_COS_TOT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
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
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        if (!chkMosLocIna.isSelected())
                            strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    if (!chkMosLocIna.isSelected())
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFilTblBod, intNumDec, i, j;
        BigDecimal bgdUniVen, bgdVen, bgdCos, bgdPesItmKgr;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            intNumDec=objParSis.getDecimalesBaseDatos();
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
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
                //Filtro: Ventas a clientes, Ventas a empresas relacionadas, Préstamos.
                strAux="";
                if (chkVenCli.isSelected())
                {
                    strAux+="a4.co_cat IN (3, 4) AND a5.co_empGrp IS NULL";
                }
                if (chkVenRel.isSelected())
                {
                    strAux+=(strAux.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a5.co_empGrp IS NOT NULL";
                }
                if (chkPre.isSelected())
                {
                    strAux+=(strAux.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                }
                strConSQL+=" AND (" + strAux + ")";
                if (txtCodEmp.getText().length()>0)
                {
                    strConSQL+=" AND a1.co_emp=" + txtCodEmp.getText();
                }
                if (txtCodCli.getText().length()>0)
                {
                    strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                }
                if (txtCodVen.getText().length()>0)
                {
                    strConSQL+=" AND a1.co_com=" + txtCodVen.getText();
                }
                //Filtrar por "Item".
                if (objVenConItm01.isCondicionAplicada())
                {
                    strConSQL+=objVenConItm01.getCondicionesSQL();
                }
                //Obtener los locales a consultar.
                intNumFilTblBod=objTblModLoc.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                    {
                        if (i==0)
                            strAux+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        else
                            strAux+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                        i++;
                    }
                }
                if (!strAux.equals(""))
                {
                    strConSQL+=" AND (" + strAux + ")";
                }
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT b2.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2, b1.tx_nomItm, b3.nd_uniVen, b4.nd_stkAct, b1.nd_preVta1, b3.nd_venTot, b3.nd_cosTot, b1.nd_pesItmKgr";
                    strSQL+=" FROM tbm_inv AS b1";
                    strSQL+=" INNER JOIN tbm_equInv AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT a6.co_itmMae, -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen";
                    strSQL+=", -SUM(a2.nd_tot) AS nd_venTot, -SUM(a2.nd_cosTotGrp) AS nd_cosTot";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)";
                    strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)";
                    strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                    strSQL+=strConSQL;
                    strSQL+=" GROUP BY a6.co_itmMae";
                    strSQL+=" ) AS b3 ON (b2.co_itmMae=b3.co_itmMae)";
                    strSQL+=" INNER JOIN (";
                    strSQL+=" SELECT a1.co_itmMae, SUM(a2.nd_stkAct) AS nd_stkAct";
                    strSQL+=" FROM tbm_equInv AS a1";
                    strSQL+=" INNER JOIN tbm_inv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                    strSQL+=" WHERE a2.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" GROUP BY a1.co_itmMae";
                    strSQL+=" ) AS b4 ON (b2.co_itmMae=b4.co_itmMae)";
                    strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY b1.tx_codAlt";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT Null AS co_itmMae, a3.co_itm, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen";
                    strSQL+=", a3.nd_stkAct, a3.nd_preVta1, -SUM(a2.nd_tot) AS nd_venTot, -SUM(a2.nd_cosTot) AS nd_cosTot, a3.nd_pesItmKgr";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                    strSQL+=strConSQL;
                    strSQL+=" GROUP BY a3.co_itm, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a3.nd_stkAct, a3.nd_preVta1, a3.nd_pesItmKgr";
                    strSQL+=" ORDER BY a3.tx_codAlt";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_2,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        bgdUniVen=rst.getObject("nd_uniVen")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_uniVen");
                        vecReg.add(INT_TBL_DAT_TOT_UNI,"" + bgdUniVen);
                        vecReg.add(INT_TBL_DAT_STK_ACT,rst.getString("nd_stkAct"));
                        switch (objParSis.getCodigoMenu())
                        {
                            case 226: //Listado de ventas por item...
                                vecReg.add(INT_TBL_DAT_PRE_VTA1,rst.getString("nd_preVta1"));
                                bgdVen=rst.getObject("nd_venTot")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_venTot");
                                bgdCos=rst.getObject("nd_cosTot")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_cosTot");
                                vecReg.add(INT_TBL_DAT_VEN_TOT,"" + bgdVen);
                                vecReg.add(INT_TBL_DAT_COS_TOT,"" + bgdCos);

                                vecReg.add(INT_TBL_DAT_VAL_GAN,"" + (bgdVen.subtract(bgdCos)));
                                //Validar que no se produzca una dvisión por cero.
                                if (bgdVen.compareTo(new BigDecimal("0"))==0)
                                {
                                    if (bgdCos.compareTo(new BigDecimal("0"))==0)
                                        vecReg.add(INT_TBL_DAT_POR_GAN,"0");
                                    else
                                        vecReg.add(INT_TBL_DAT_POR_GAN,"-100");
                                }
                                else
                                    vecReg.add(INT_TBL_DAT_POR_GAN,"" + ((new BigDecimal("100").multiply((bgdVen.subtract(bgdCos)))).divide(bgdVen, intNumDec, BigDecimal.ROUND_HALF_UP)));
                                break;
                            case 229: //Listado de ventas por item (Unidades)...
                            default:
                                vecReg.add(INT_TBL_DAT_PRE_VTA1,null);
                                //Ficha "Reporte": Tabla->Mostrar "Venta total"
                                if (objPerUsr.isOpcionEnabled(3921))
                                {
                                    bgdVen=rst.getObject("nd_venTot")==null?BigDecimal.ZERO:rst.getBigDecimal("nd_venTot");
                                    vecReg.add(INT_TBL_DAT_VEN_TOT,"" + bgdVen);
                                }
                                else
                                {
                                    vecReg.add(INT_TBL_DAT_VEN_TOT,null);
                                }
                                vecReg.add(INT_TBL_DAT_COS_TOT,null);
                                vecReg.add(INT_TBL_DAT_VAL_GAN,null);
                                vecReg.add(INT_TBL_DAT_POR_GAN,null);
                                break;
                        }
                        bgdPesItmKgr=rst.getBigDecimal("nd_pesItmKgr");
                        if (bgdPesItmKgr==null)
                            vecReg.add(INT_TBL_DAT_PES_TOT_KGR,null);
                        else
                            vecReg.add(INT_TBL_DAT_PES_TOT_KGR,"" + objUti.redondearBigDecimal(bgdUniVen.multiply(bgdPesItmKgr), intNumDec));
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
    private boolean cargarMovReg()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    //Obtener la condición.
                    //Al dar click en el botón "Consultar" ya quedó almacenado el filtro en "strConSQL".
                    //Por lo tanto ya no es necesario volver a obtener el filtro.
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.co_loc, a7.tx_nom, a1.co_tipDoc, a4.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli";
                        strSQL+=", -(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_can";
                        strSQL+=", (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_preUni ELSE 0 END) AS nd_preUni";
                        strSQL+=", (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_cosUniGrp ELSE 0 END) AS nd_cosUni";
                        strSQL+=", -a2.nd_tot AS nd_venTot, -a2.nd_cosTotGrp AS nd_cosTot";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)";
                        strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)";
                        strSQL+=" INNER JOIN tbm_loc a7 ON (a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc)";
                        strSQL+=" WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_itm=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM) + ")";
                        strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                        strSQL+=strConSQL;
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc";
                        rst=stm.executeQuery(strSQL);
                    }
                    else
                    {
                        //Obtener los datos de la "Empresa seleccionada".
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_loc, a7.tx_nom, a1.co_tipDoc, a4.tx_desCor, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli";
                        strSQL+=", -(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_can";
                        strSQL+=", (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_preUni ELSE 0 END) AS nd_preUni";
                        strSQL+=", (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_cosUni ELSE 0 END) AS nd_cosUni";
                        strSQL+=", -a2.nd_tot AS nd_venTot, -a2.nd_cosTot AS nd_cosTot";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_inv a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_cli AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cli=a5.co_cli)";
                        strSQL+=" INNER JOIN tbm_loc a7 ON (a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a2.co_itm=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM);
                        strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                        strSQL+=strConSQL;
                        strSQL+=" ORDER BY a1.co_loc, a1.fe_doc";
                        rst=stm.executeQuery(strSQL);
                    }
                    //Limpiar vector de datos.
                    vecDatMov.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DET_LIN,"");
                        vecReg.add(INT_TBL_DET_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DET_NOM_LOC,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DET_COD_TIP_D0C,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DET_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DET_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DET_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DET_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DET_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DET_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DET_CAN,rst.getString("nd_can"));
                        vecReg.add(INT_TBL_DET_PRE_UNI,rst.getString("nd_preUni"));
                        vecReg.add(INT_TBL_DET_COS_UNI,rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DET_VEN_TOT,rst.getString("nd_venTot"));
                        vecReg.add(INT_TBL_DET_COS_TOT,rst.getString("nd_cosTot"));
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
        //Validar que esté seleccionada al menos un local.
        intNumFilTblLoc=objTblModLoc.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblLoc; j++)
        {
            if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos un local.<BR>Seleccione un local y vuelva a intentarlo.</HTML>");
            tblLoc.requestFocus();
            return false;
        }
        if ( (!chkVenCli.isSelected()) && (!chkVenRel.isSelected()) && (!chkPre.isSelected()) )
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El siguiente filtro es obligatorio:<BR><UL><LI>Ventas a clientes<LI>Ventas a empresas relacionadas<LI>Préstamos</UL>Seleccione al menos una de las opciones y vuelva a intentarlo.</HTML>");
            chkVenCli.requestFocus();
            return false;
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbr_usremp AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp";
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de empresas", strSQL, arlCam, arlAli, arlAncCol);
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
     * mostrar los "Clientes".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("394");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A' AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCli.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSQL+=" WHERE a1.st_reg='A' AND a2.st_ven='S'";
                strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
                strSQL+=" FROM tbm_usr AS a1";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a2.st_ven='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
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
    private boolean mostrarVenConCli(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.setVisible(true);
                    if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
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
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(2);
                    vcoVen.setVisible(true);
                    if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoVen.buscar("a1.co_usr", txtCodVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(0);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtNomVen.setText(vcoVen.getValueAt(3));
                        }
                        else
                        {
                            txtCodVen.setText(strCodVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoVen.buscar("a1.tx_nom", txtNomVen.getText()))
                    {
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtNomVen.setText(vcoVen.getValueAt(3));
                    }
                    else
                    {
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.setVisible(true);
                        if (vcoVen.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
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
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_2:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_TOT_UNI:
                    strMsg="Total de unidades vendidas";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg="Stock actual";
                    break;
                case INT_TBL_DAT_PRE_VTA1:
                    strMsg="Precio de venta 1";
                    break;
                case INT_TBL_DAT_VEN_TOT:
                    strMsg="Venta total";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg="Costo total";
                    break;
                case INT_TBL_DAT_VAL_GAN:
                    strMsg="Valor de ganancia";
                    break;
                case INT_TBL_DAT_POR_GAN:
                    strMsg="Porcentaje de ganancia";
                    break;
                case INT_TBL_DAT_PES_TOT_KGR:
                    strMsg="Peso total (Kg)";
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
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
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

}