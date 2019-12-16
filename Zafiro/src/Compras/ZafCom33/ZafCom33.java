/*
 * ZafCom33.java
 * 
 * Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom33;

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
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafEvt.ZafAsiDiaEvent;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import java.sql.DriverManager;

/**
 *
 * @author Ingrid Lino
 */
public class ZafCom33 extends javax.swing.JInternalFrame 
{
    //Variables generales.
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblCelRenChk objTblCelRenChkVisBue;      //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkVisBue;      //Editor: JCheckBox en celda.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoResCon;                        //Ventana de consulta "Responsable Conteo".
    private ZafVenCon vcoBod;                           //Ventana de consulta "Bodega".
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    private Connection con, conCab, conRemTmp;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorRes, strDesLarRes;          //Contenido del campo al obtener el foco.
    private int intSig = 1;                             //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.

    private String strNumDoc;
    private String strCodBod, strNomBod;                //Contenido del campo al obtener el foco.

    private MiToolBar objTooBar;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiBut;

    private ZafCom33_01 objCom33_01;

    //Constantes: Columnas del JTable.
    static final int INT_TBL_DAT_LIN = 0;
    static final int INT_TBL_DAT_COD_EMP = 1;
    static final int INT_TBL_DAT_COD_ITM_MAE = 2;
    static final int INT_TBL_DAT_COD_ITM = 3;
    static final int INT_TBL_DAT_COD_ALT_ITM = 4;
    static final int INT_TBL_DAT_COD_LET_ITM = 5;
    static final int INT_TBL_DAT_BUT_ANE_UNO = 6;
    static final int INT_TBL_DAT_NOM_ITM = 7;
    static final int INT_TBL_DAT_STK_ACT = 8;
    static final int INT_TBL_DAT_DES_COR_UNI_MED = 9;
    static final int INT_TBL_DAT_DES_LAR_UNI_MED = 10;
    static final int INT_TBL_DAT_UBI_ITM = 11;
    static final int INT_TBL_DAT_CAN_ING_IMP = 12;
    static final int INT_TBL_DAT_CAN_CON = 13;
    
    private ArrayList arlReg, arlDat;
    static final int INT_ARL_COD_EMP = 0;
    static final int INT_ARL_COD_ITM_MAE = 1;
    static final int INT_ARL_COD_ITM = 2;
    static final int INT_ARL_COD_ALT_ITM = 3;
    static final int INT_ARL_COD_LET_ITM = 4;
    static final int INT_ARL_NOM_ITM = 5;
    static final int INT_ARL_STK_ACT = 6;
    static final int INT_ARL_DES_COR_UNI_MED = 7;
    static final int INT_ARL_DES_LAR_UNI_MED = 8;
    static final int INT_ARL_UBI_ITM = 9;

    private ArrayList arlRegItmModTbl, arlDatItmModTbl;
    static final int INT_ARL_MOD_TBL_COD_EMP = 0;
    static final int INT_ARL_MOD_TBL_COD_ITM_MAE = 1;
    static final int INT_ARL_MOD_TBL_COD_ITM = 2;

    private String strSQLExe;
    private boolean blnIsRegEli;

    private ArrayList arlReg_CfgRegCnxRem, arlDat_CfgRegCnxRem;
    private final int INT_ARL_COD_REG_ORI = 0;
    private final int INT_ARL_COD_REG_DES = 1;
    private int INT_COD_GRP_CNF = 5;

    private ArrayList arlDatCodItmMae;
    private int INT_ARL_FAL_COD_ITM_MAE = 0;

    private ArrayList arlRegPed, arlDatPed;
    private int INT_ARL_PED_COD_EMP = 0;
    private int INT_ARL_PED_COD_LOC = 1;
    private int INT_ARL_PED_COD_TIP_DOC = 2;
    private int INT_ARL_PED_COD_DOC = 3;
    private int INT_ARL_PED_NUM_PED = 4;
    private int INT_ARL_PED_IND_SEL = 5;
    private int INT_ARL_PED_COD_IMP = 6;

    private int intCboPedSelCodEmp;
    private int intCboPedSelCodLoc;
    private int intCboPedSelCodTipDoc;
    private int intCboPedSelCodDoc;

    /**
     * Crea una nueva instancia de la clase ZafCon06.
     */
    public ZafCom33(ZafParSis obj) 
    {
        try 
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            arlDatPed = new ArrayList();
            initComponents();
            if (!configurarFrm()) 
            {
                exitForm();
            }
            agregarDocLis();

        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafCom33(ZafParSis obj, ArrayList arlCodItmMae, int codigoBodega) 
    {
        int intCodEmp = 0;
        try 
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objParSis.setCodigoEmpresa(intCodEmp);
            initComponents();
            if (!configurarFrm()) 
            {
                exitForm();
            }
            agregarDocLis();
            arlDatCodItmMae = new ArrayList();
            arlDatCodItmMae.clear();
            arlDatCodItmMae = arlCodItmMae;
            objParSis.setCodigoEmpresa(0);
            objParSis.setCodigoLocal(1);
            objParSis.setCodigoMenu(1944);
            objTooBar.clickInsertar();
            mostrarVenConRes(3);
            txtCodBod.setText("" + codigoBodega);
            mostrarVenConBod(1);
            llenarModeloDatos();
            objTooBar.beforeInsertar();
            objTooBar.insertar();
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtDesCorRes = new javax.swing.JTextField();
        txtDesLarRes = new javax.swing.JTextField();
        butRes = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        lblBen = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        txtNumDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        txtCodRes = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panDatPed = new javax.swing.JPanel();
        cboPedSel = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        chkConBodImp = new javax.swing.JCheckBox();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBarBot = new javax.swing.JPanel();

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
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 72));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setPreferredSize(new java.awt.Dimension(0, 70));
        panCabFil.setLayout(null);

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
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(118, 6, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 130, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(406, 6, 20, 20);

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
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(175, 6, 230, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(85, 6, 32, 20);

        lblPrv.setText("Responsable:");
        lblPrv.setToolTipText("Responsable de hacer el conteo");
        panCabFil.add(lblPrv);
        lblPrv.setBounds(4, 27, 90, 20);

        txtDesCorRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorResActionPerformed(evt);
            }
        });
        txtDesCorRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorResFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorResFocusLost(evt);
            }
        });
        panCabFil.add(txtDesCorRes);
        txtDesCorRes.setBounds(118, 27, 56, 20);

        txtDesLarRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarResActionPerformed(evt);
            }
        });
        txtDesLarRes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarResFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarResFocusLost(evt);
            }
        });
        panCabFil.add(txtDesLarRes);
        txtDesLarRes.setBounds(175, 27, 230, 20);

        butRes.setText("...");
        butRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butResActionPerformed(evt);
            }
        });
        panCabFil.add(butRes);
        butRes.setBounds(406, 27, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 110, 20);

        lblNumDoc1.setText("Número documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc1);
        lblNumDoc1.setBounds(432, 27, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(432, 48, 110, 20);

        lblBen.setText("Bodega:");
        lblBen.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBen);
        lblBen.setBounds(4, 48, 70, 20);

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
        panCabFil.add(txtCodBod);
        txtCodBod.setBounds(118, 48, 56, 20);

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
        panCabFil.add(txtNomBod);
        txtNomBod.setBounds(175, 48, 230, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCabFil.add(butBod);
        butBod.setBounds(406, 48, 20, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(550, 27, 120, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(550, 48, 120, 20);
        panCabFil.add(txtCodRes);
        txtCodRes.setBounds(85, 27, 32, 20);

        panGenCab.add(panCabFil, java.awt.BorderLayout.NORTH);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panDatPed.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos de Importación"));
        panDatPed.setPreferredSize(new java.awt.Dimension(100, 60));
        panDatPed.setLayout(null);

        cboPedSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPedSelActionPerformed(evt);
            }
        });
        panDatPed.add(cboPedSel);
        cboPedSel.setBounds(120, 16, 180, 20);

        jLabel1.setText("Pedido:");
        panDatPed.add(jLabel1);
        jLabel1.setBounds(30, 20, 60, 14);

        chkConBodImp.setText("No generar mas conteos(Generar conteo bodega Importaciones)");
        panDatPed.add(chkConBodImp);
        chkConBodImp.setBounds(25, 40, 430, 14);

        panGenDet.add(panDatPed, java.awt.BorderLayout.NORTH);

        panCon.add(panGenDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panCon.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panCon);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setPreferredSize(new java.awt.Dimension(320, 50));
        panBarBot.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /**
     * Cerrar la aplicacián.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexián si está abierta.
                if (rstCab != null) 
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    conRemTmp.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                    conRemTmp = null;
                }
                dispose();
            }
        } catch (java.sql.SQLException e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void txtDesCorResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorResActionPerformed
    txtDesCorRes.transferFocus();
}//GEN-LAST:event_txtDesCorResActionPerformed

private void txtDesCorResFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorResFocusGained
    strDesCorRes = txtDesCorRes.getText();
    txtDesCorRes.selectAll();
}//GEN-LAST:event_txtDesCorResFocusGained

private void txtDesCorResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorResFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    try 
    {
        if (!txtDesCorRes.getText().equalsIgnoreCase(strDesCorRes)) {
            if (txtDesCorRes.getText().equals("")) {
                txtCodRes.setText("");
                txtDesLarRes.setText("");
            } else {
                mostrarVenConRes(1);
            }
        } else {
            txtDesCorRes.setText(strDesCorRes);
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }

}//GEN-LAST:event_txtDesCorResFocusLost

private void txtDesLarResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarResActionPerformed
    txtDesLarRes.transferFocus();
}//GEN-LAST:event_txtDesLarResActionPerformed

private void txtDesLarResFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarResFocusGained
    strDesLarRes = txtDesLarRes.getText();
    txtDesLarRes.selectAll();
}//GEN-LAST:event_txtDesLarResFocusGained

private void txtDesLarResFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarResFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
    try 
    {
        if (!txtDesLarRes.getText().equalsIgnoreCase(strDesLarRes)) {
            if (txtDesLarRes.getText().equals("")) {
                txtCodRes.setText("");
                txtDesCorRes.setText("");
            } else {
                mostrarVenConRes(2);
            }
        } else {
            txtDesLarRes.setText(strDesLarRes);
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }

}//GEN-LAST:event_txtDesLarResFocusLost

private void butResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butResActionPerformed
    try 
    {
        mostrarVenConRes(0);
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }

}//GEN-LAST:event_butResActionPerformed

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
    strCodBod = txtCodBod.getText();
    txtCodBod.selectAll();

}//GEN-LAST:event_txtCodBodFocusGained


private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
        if (txtCodBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(1);
        }
    } else {
        txtCodBod.setText(strCodBod);
    }
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
    strNomBod = txtNomBod.getText();
    txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
        if (txtNomBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(2);
        }
    } else {
        txtNomBod.setText(strNomBod);
    }
}//GEN-LAST:event_txtNomBodFocusLost

private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
    mostrarVenConBod(0);
}//GEN-LAST:event_butBodActionPerformed

    private void cboPedSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPedSelActionPerformed
        int intIndSelTrs = -1;
        for (int j = 0; j < arlDatPed.size(); j++) 
        {
            intIndSelTrs = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
            if (cboPedSel.getSelectedIndex() == (intIndSelTrs + 1)) {
                intCboPedSelCodEmp = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_EMP);
                intCboPedSelCodLoc = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_LOC);
                intCboPedSelCodTipDoc = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_TIP_DOC);
                intCboPedSelCodDoc = objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_DOC);
                break;
            }
        }
    }//GEN-LAST:event_cboPedSelActionPerformed

    private boolean getCodTipDoc() 
    {
        boolean blnRes = true;
        if (txtCodTipDoc.getText().toString().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Cerrar la aplicacián.
     */
    private void exitForm() 
    {
        dispose();
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butRes;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboPedSel;
    private javax.swing.JCheckBox chkConBodImp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panDatPed;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodRes;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorRes;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarRes;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            arlDatItmModTbl = new ArrayList();
            arlDat_CfgRegCnxRem = new ArrayList();
            objUti = new ZafUtil();
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker: Desde
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(550, 6, 120, 20);

            objCom33_01 = new ZafCom33_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.4.6");
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            if (objParSis.getCodigoMenu() == 1944) {
                txtDesCorRes.setBackground(objParSis.getColorCamposObligatorios());
                txtDesLarRes.setBackground(objParSis.getColorCamposObligatorios());
            }

            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());

            txtCodTipDoc.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConResCon();
            //Configurar los JTables.
            configurarTblDat();

            txtCodRes.setVisible(false);
            txtCodRes.setEnabled(false);
            txtCodRes.setEditable(false);

            objTooBar = new MiToolBar(this);
            panBarBot.add(objTooBar);

            panDatPed.setVisible(false);
            if (objParSis.getCodigoMenu() != 1944) 
            {
                panDatPed.setVisible(true);
                objTooBar.setVisibleInsertar(false);
                objTooBar.setVisibleModificar(false);
                objTooBar.setVisibleAnular(false);
                objTooBar.setVisibleEliminar(false);
                chkConBodImp.setVisible(false);
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() 
    {
        boolean blnRes = true;
        try 
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            //vecCab = new Vector(14);  //Almacena las cabeceras --Rose
            vecCab= new Vector();
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cód.Emp");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM, "Cód.Itm.");           
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM, "Cód.Let.Itm.");
            vecCab.add(INT_TBL_DAT_BUT_ANE_UNO, "...");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_STK_ACT, "Stock.");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_DES_LAR_UNI_MED, "Uni.Med.Des.Lar.");
            vecCab.add(INT_TBL_DAT_UBI_ITM, "Ubicación");
            vecCab.add(INT_TBL_DAT_CAN_ING_IMP, "CAN.IMP.");
            vecCab.add(INT_TBL_DAT_CAN_CON, "CAN.CON.");

            objTblMod = new ZafTblMod();

            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_EMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_ITM, objTblMod.INT_COL_DBL, new Integer(0), null);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_UNO).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setPreferredWidth(70);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DES_LAR_UNI_MED, tblDat);
            
            if (objParSis.getCodigoMenu() == 1944)
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_IMP, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);

            vecAux = new Vector();
//            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_UNO);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            objTblCelRenBut = new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_UNO).setCellRenderer(objTblCelRenBut);

            objTblCelEdiBut = new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_UNO).setCellEditor(objTblCelEdiBut);

            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel = 0;

                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //System.out.println("actionPerformed");
                    if (isCamposCabecera()) {
                        objCom33_01.setVisible(true);
                    } else {
                        objCom33_01.setVisible(false);
                    }
                }

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //System.out.println("beforeEdit");
                    objTblCelEdiBut.setCancelarEdicion(false);
                    objCom33_01.setCodigoBodega(txtCodBod.getText());
                    objCom33_01.setNombreBodega(txtNomBod.getText());
                    objCom33_01.setItemsSeleccionados(cargarDatosSeleccionados());
                    //se envia la informacion del pedido para cargar solo los items de ese pedido, esto sirve para las bodegas que no sean la de Importaciones
                    objCom33_01.setCodEmpPedidoSeleccionado(intCboPedSelCodEmp);
                    objCom33_01.setCodLocPedidoSeleccionado(intCboPedSelCodLoc);
                    objCom33_01.setCodTipDocPedidoSeleccionado(intCboPedSelCodTipDoc);
                    objCom33_01.setCodDocPedidoSeleccionado(intCboPedSelCodDoc);
                    intFilSel = tblDat.getSelectedRow();
                }

                ;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    arlDat = objCom33_01.getArlDat();
                    //System.out.println("ARRAYLIST EN CLASE PRINCIPAL: " + arlDat.toString());
                    cargarDatosAnexo(intFilSel);
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_IMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_STK_ACT).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_UBI_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;
            
            //Libero los objetos auxiliares.
            tcmAux = null;

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener 
    {
        public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn() != null) {
                intColSel = tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel >= 0) {
                    intAncCol = tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
        }
    }

    /**
     * Esta funcián determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        //Validar el "Responsable de conteo".
        if (objParSis.getCodigoMenu() == 1944) 
        {
            if (txtDesCorRes.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Responsable</FONT> es obligatorio.<BR>Escriba o seleccione un responsable de conteo y vuelva a intentarlo.</HTML>");
                txtDesCorRes.requestFocus();
                return false;
            }
        }

        if (objTblMod.getRowCountTrue() > 0) 
        {
            if (txtCodBod.getText().equals(""))
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
                return false;
            }
        }

        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }

        if (txtNumDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }

        if (objParSis.getCodigoMenu() == 1944) 
        {
            if (objTblMod.getRowCountTrue() <= 0) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe ingresar por lo menos un item en la orden de conteo.</HTML>");
                return false;
            }
        }
        else 
        {
            if (!chkConBodImp.isSelected()) 
            {
                if (objTblMod.getRowCountTrue() <= 0) {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>Debe ingresar por lo menos un item en la orden de conteo.</HTML>");
                    return false;
                }
            }
        }

        if (objParSis.getCodigoMenu() != 1944) 
        {
            if ((intCboPedSelCodEmp == 0) || (intCboPedSelCodEmp == 0) || (intCboPedSelCodEmp == 0) || (intCboPedSelCodEmp == 0)) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar algún Pedido.</HTML>");
                return false;
            }
        }

        return true;
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funcián muestra un mensaje de advertencia al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique que los datos se
     * han cargado con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) 
        {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() 
    {
        boolean blnRes = true;
        try
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) 
            {
                strSQL = "";
                strSQL += " SELECT (a2.co_Tipdoc) as co_TipDoc, a2.tx_DesCor, a2.tx_desLar, a2.ne_ultDoc, a2.tx_natDoc ";
                strSQL += " FROM tbr_tipdocPrg as a1 ";
                strSQL += " INNER JOIN tbm_CabTipDoc as a2 ON (a1.co_Emp=a2.co_Emp and a1.co_loc=a2.co_loc and a1.co_Tipdoc=a2.co_Tipdoc) ";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_mnu=" + objParSis.getCodigoMenu();
                
//                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
//                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
//                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
//                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
//                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
//                //strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
//                strSQL += " AND a1.st_reg NOT IN('I','E')";
//                strSQL += " ORDER BY a1.tx_desCor";
                
            } 
            else 
            {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConResCon() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Usuario");
            arlAli.add("Usuario");
            arlAli.add("Nombre Usuario");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL += " FROM tbm_usr AS a1";
            strSQL += " WHERE a1.st_reg NOT IN('I','E')";
            strSQL += " AND a1.co_usr IN(select co_usr from tbm_usr where co_grpUsr in (13,14) and st_Reg!='I')";
            strSQL += " ORDER BY a1.tx_nom";
            //System.out.println("SQL DE RESPONSABLE: " + strSQL);

            vcoResCon = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Usuarios", strSQL, arlCam, arlAli, arlAncCol);

            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoResCon.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Bodega");
            arlAli.add("Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) 
            {
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
                strSQL += " FROM tbm_bod AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }
            else
            {
                strSQL = "";
                strSQL += " SELECT a1.co_bod, a1.tx_nom";
//                strSQL+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodtipdocprgusr AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
//                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
//                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
//                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " FROM tbm_bod AS a1 INNER JOIN tbr_bodusr AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            }

            if (objParSis.getCodigoMenu() == 1944) 
            {
                if (txtCodRes.getText().equals("")) 
                {
                    mostrarMsgInf("<HTML>El campo de bodega requiere que haya seleccionado un responsable del conteo.<BR>Seleccione un responsable de conteo y vuelva a intentarlo.</HTML>");
                    txtCodBod.setText("");
                    txtNomBod.setText("");
                    txtDesLarRes.requestFocus();
                    txtDesLarRes.selectAll();
                    blnRes = false;
                } 
                else 
                {
                    if (objParSis.getCodigoUsuario() != 1) 
                    {
                        strSQL += " AND a2.co_usr=" + txtCodRes.getText() + "";
                    }
                    strSQL += " GROUP BY a1.co_bod, a1.tx_nom";
                    strSQL += " ORDER BY a1.co_bod, a1.tx_nom";
                    //System.out.println("error : " + strSQL);
                    vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
                    arlCam = null;
                    arlAli = null;
                    arlAncCol = null;
                    //Configurar columnas.
                    vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
                }
            } 
            else
            {
                strSQL += " GROUP BY a1.co_bod, a1.tx_nom";
                strSQL += " ORDER BY a1.co_bod, a1.tx_nom";

                vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
                arlCam = null;
                arlAli = null;
                arlAncCol = null;
                //Configurar columnas.
                vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus) 
    {
        boolean blnRes = true;
        try
        {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }

                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConRes(int intTipBus)
    {
        boolean blnRes = true;
        try 
        {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.

                    vcoResCon.setCampoBusqueda(1);
                    vcoResCon.show();
                    if (vcoResCon.getSelectedButton() == vcoResCon.INT_BUT_ACE) {
                        txtCodRes.setText(vcoResCon.getValueAt(1));
                        txtDesCorRes.setText(vcoResCon.getValueAt(2));
                        txtDesLarRes.setText(vcoResCon.getValueAt(3));
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoResCon.buscar("a1.tx_usr", txtDesCorRes.getText())) {
                        txtCodRes.setText(vcoResCon.getValueAt(1));
                        txtDesCorRes.setText(vcoResCon.getValueAt(2));
                        txtDesLarRes.setText(vcoResCon.getValueAt(3));
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    } else {
                        vcoResCon.setCampoBusqueda(1);
                        vcoResCon.setCriterio1(11);
                        vcoResCon.cargarDatos();
                        vcoResCon.show();
                        if (vcoResCon.getSelectedButton() == vcoResCon.INT_BUT_ACE) {
                            txtCodRes.setText(vcoResCon.getValueAt(1));
                            txtDesCorRes.setText(vcoResCon.getValueAt(2));
                            txtDesLarRes.setText(vcoResCon.getValueAt(3));
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        } else {
                            txtDesCorRes.setText(strDesCorRes);
                        }
                    }

                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoResCon.buscar("a1.tx_nom", txtDesLarRes.getText())) {
                        txtCodRes.setText(vcoResCon.getValueAt(1));
                        txtDesCorRes.setText(vcoResCon.getValueAt(2));
                        txtDesLarRes.setText(vcoResCon.getValueAt(3));
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    } else {
                        vcoResCon.setCampoBusqueda(2);
                        vcoResCon.setCriterio1(11);
                        vcoResCon.cargarDatos();
                        vcoResCon.show();
                        if (vcoResCon.getSelectedButton() == vcoResCon.INT_BUT_ACE) {
                            txtCodRes.setText(vcoResCon.getValueAt(1));
                            txtDesCorRes.setText(vcoResCon.getValueAt(2));
                            txtDesLarRes.setText(vcoResCon.getValueAt(3));
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        } else {
                            txtDesLarRes.setText(strDesLarRes);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "codigo".

                    if (vcoResCon.buscar("a1.co_usr", txtCodRes.getText())) {
                        txtCodRes.setText(vcoResCon.getValueAt(1));
                        txtDesCorRes.setText(vcoResCon.getValueAt(2));
                        txtDesLarRes.setText(vcoResCon.getValueAt(3));
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    } else {
                        vcoResCon.setCampoBusqueda(2);
                        vcoResCon.setCriterio1(11);
                        vcoResCon.cargarDatos();
                        vcoResCon.show();
                        if (vcoResCon.getSelectedButton() == vcoResCon.INT_BUT_ACE) {
                            txtCodRes.setText(vcoResCon.getValueAt(1));
                            txtDesCorRes.setText(vcoResCon.getValueAt(2));
                            txtDesLarRes.setText(vcoResCon.getValueAt(3));
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        } else {
                            txtDesLarRes.setText(strDesLarRes);
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBod(int intTipBus) 
    {
        boolean blnRes = true;
        try 
        {
            if (configurarVenConBod()) {
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
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta clase implementa la interface DocumentListener que observa los
     * cambios que se presentan en los objetos de tipo texto. Por ejemplo:
     * JTextField, JTextArea, etc. Se la usa en el sistema para determinar si
     * existe algán cambio que se deba grabar antes de abandonar uno de los
     * modos o desplazarse a otro registro. Por ejemplo: si se ha hecho cambios
     * a un registro y quiere cancelar o moverse a otro registro se presentará
     * un mensaje advirtiendo que si no guarda los cambios los perderá.
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
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los
     * objetos de tipo texto para poder determinar si su contenido a cambiado o
     * no.
     */
    private void agregarDocLis() 
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorRes.getDocument().addDocumentListener(objDocLis);
        txtDesLarRes.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) 
            {
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Código de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg = "Código del item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg = "Código en letras del item";
                    break;
                case INT_TBL_DAT_BUT_ANE_UNO:
                    strMsg = "Anexo 1";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del Item";
                    break;
                case INT_TBL_DAT_STK_ACT:
                    strMsg = "Stock Actual";
                    break;    
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Unidad de Medida";
                    break;
                case INT_TBL_DAT_DES_LAR_UNI_MED:
                    strMsg = "Unidad de Medida Descripción Larga";
                    break;
                case INT_TBL_DAT_UBI_ITM:
                    strMsg = "Ubicación del Item";
                    break;    
                case INT_TBL_DAT_CAN_ING_IMP:
                    strMsg = "Cantidad del ingreso por importación";
                    break;
                case INT_TBL_DAT_CAN_CON:
                    strMsg = "Cantidad ingresada por conteo";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private boolean isCamposCabecera() 
    {
        try 
        {
            if (txtCodTipDoc.getText().equals("")) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                return false;
            }
            if (objParSis.getCodigoMenu() == 1944) {
                if (txtCodRes.getText().equals("")) {
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Responsable</FONT> es obligatorio.<BR>Seleccione un responsable de conteo y vuelva a intentarlo.</HTML>");
                    return false;
                }
            }
            if (txtCodBod.getText().equals("")) {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
                return false;
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return true;
    }

    private class MiToolBar extends ZafToolBar 
    {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }

        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() 
        {
            //Configurar JFrame de acuerdo al estado del registro.
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam = false;
            return true;
        }

        public boolean afterModificar() 
        {
            blnHayCam = false;
            return true;
        }

        public boolean afterVistaPreliminar() 
        {
            return true;
        }

        public boolean anular() 
        {
            if (!anularReg()) {
                return false;
            }
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam = false;
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento ya esta ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeInsertar() {
            boolean blnRes = true;
            if (!isCamVal()) {
                blnRes = false;
            }
            return blnRes;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean cancelar() 
        {
            boolean blnRes = true;
            try {
                if (blnHayCam) {
                    if (objTooBar.getEstado() == 'n' || objTooBar.getEstado() == 'm') {
                        if (!isRegPro()) {
                            return false;
                        }
                    }
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam = false;
            return blnRes;
        }

        public void clickAceptar() {

        }

        public void clickAnterior() 
        {
            try
            {
                if (!rstCab.isFirst()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.previous();
                            cargarReg();

                        }
                    } else {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular() {
            cargarDetReg();
        }

        public void clickCancelar() {

        }

        public void clickConsultar() {

        }

        public void clickEliminar() {

        }

        public void clickFin() 
        {
            try 
            {
                if (!rstCab.isLast()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.last();
                            cargarReg();
                        }
                    } else {
                        rstCab.last();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir() {

        }

        public void clickInicio() 
        {
            try 
            {
                if (!rstCab.isFirst()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.first();
                            cargarReg();
                        }
                    } else {
                        rstCab.first();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() 
        {
            try 
            {
                if (blnHayCam) {
                    isRegPro();
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
//                txtMonDoc.setEditable(false);
                datFecAux = null;
                datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));
                //datFecAux=null;
                mostrarTipDocPre();

                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                cargarPedidoEmbarcado();
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar() {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            isCambioEstadoRegistro();

            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            txtCodDoc.setEditable(false);
            cboPedSel.setEnabled(false);
        }

        public void clickSiguiente() {
            try {
                if (!rstCab.isLast()) {
                    if (blnHayCam) {
                        if (isRegPro()) {
                            rstCab.next();
                            cargarReg();
                        }
                    } else {
                        rstCab.next();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() {

        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean eliminar() 
        {
            try 
            {
                if (!eliminarReg()) {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam = false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean insertar() {
            if (!insertarReg()) {
                return false;
            }
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg()) {
                return false;
            }
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

    }

    /**
     * Esta funcián muestra el tipo de documento predeterminado del programa.
     *
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre() 
    {
        boolean blnRes = true;
        try 
        {
            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1) 
                {
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL += ", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocPrg";
                    strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL += " AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                }
                else 
                {
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL += ", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL += " FROM tbm_cabTipDoc AS a1";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL += " AND a1.co_tipDoc=";
                    strSQL += " (";
                    strSQL += " SELECT co_tipDoc";
                    strSQL += " FROM tbr_tipDocUsr";
                    strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL += " AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL += " AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL += " AND st_reg='S'";
                    strSQL += " )";
                }
                rst = stm.executeQuery(strSQL);
                //System.out.println("TipoDocumento:"+strSQL);
                if (rst.next()) {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc") + 1));
                    intSig = (rst.getString("tx_natDoc").equals("I") ? 1 : -1);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los
     * objetos de tipo texto para poder determinar si su contenido a cambiado o
     * no.
     */
    private boolean isRegPro() 
    {
        boolean blnRes = true;
        strAux = "Desea guardar los cambios efectuados a áste registro?\n";
        strAux += "Si no guarda los cambios perderá toda la informacián que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes = objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes = objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam = false;
                blnRes = true;
                break;
            case 2: //CANCEL_OPTION
                blnRes = false;
                break;
        }
        return blnRes;
    }

    /**
     * Esta función permite limpiar el formulario.
     *
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() 
    {
        boolean blnRes = true;
        try 
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodRes.setText("");
            txtDesCorRes.setText("");
            txtDesLarRes.setText("");
            txtCodBod.setText("");
            txtNomBod.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
            cboPedSel.removeAllItems();
            arlDatPed.clear();
            intCboPedSelCodEmp = 0;
            intCboPedSelCodLoc = 0;
            intCboPedSelCodTipDoc = 0;
            intCboPedSelCodDoc = 0;
            chkConBodImp.setSelected(false);

        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean cargarDatosAnexo(int fila) 
    {
        boolean blnRes = true;
        int intTam = arlDat.size();
        int intFil = fila;
        try
        {
            for (int i = 0; i < arlDat.size(); i++) 
            {
                objTblMod.insertRow(intFil);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_EMP), intFil, INT_TBL_DAT_COD_EMP);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ITM_MAE), intFil, INT_TBL_DAT_COD_ITM_MAE);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ITM), intFil, INT_TBL_DAT_COD_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ALT_ITM), intFil, INT_TBL_DAT_COD_ALT_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_LET_ITM), intFil, INT_TBL_DAT_COD_LET_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_NOM_ITM), intFil, INT_TBL_DAT_NOM_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_STK_ACT), intFil, INT_TBL_DAT_STK_ACT);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_DES_COR_UNI_MED), intFil, INT_TBL_DAT_DES_COR_UNI_MED);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_DES_LAR_UNI_MED), intFil, INT_TBL_DAT_DES_LAR_UNI_MED);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_UBI_ITM), intFil, INT_TBL_DAT_UBI_ITM);
                intFil++;
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;

    }

    private ArrayList cargarDatosSeleccionados() 
    {
        String strLin = "";
        arlDatItmModTbl.clear();
        String strCodEmp = "";
        try
        {
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                strLin = objTblMod.getValueAt(i, INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                strCodEmp = objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString();
                if (strLin.equals("I")) {
                    if (!strCodEmp.equals("")) {
                        arlRegItmModTbl = new ArrayList();
                        arlRegItmModTbl.add(INT_ARL_MOD_TBL_COD_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP));
                        arlRegItmModTbl.add(INT_ARL_MOD_TBL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                        arlRegItmModTbl.add(INT_ARL_MOD_TBL_COD_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM));
                        arlDatItmModTbl.add(arlRegItmModTbl);
                    }
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDatItmModTbl;
    }

    private Connection cargarConexionRemota() 
    {
        int intCodRegEmpSel = 0;
        arlDat_CfgRegCnxRem.clear();
        String strConLoc = objParSis.getStringConexionCentral();
        String strUsrLoc = objParSis.getUsuarioConexionCentral();
        String strPswLoc = objParSis.getClaveConexionCentral();
        conRemTmp = null;//en quito pero de la db de aqui en instalaciones fisicas de Tuval
        Connection conCen;

        Statement stmCen;
        ResultSet rstCen;
        String strConRem, strUsrRem, strPswRem;
        try 
        {
            conCen = DriverManager.getConnection(strConLoc, strUsrLoc, strPswLoc);
            if (conCen != null) {
                stmCen = conCen.createStatement();
                strSQL = "";
                strSQL += "select co_reg from tbm_basdat";
                strSQL += " where tx_drvcon='" + objParSis.getDriverConexion() + "'    and tx_strCon='" + objParSis.getStringConexion() + "'";
                strSQL += "   and tx_usrcon='" + objParSis.getUsuarioBaseDatos() + "'    and tx_clacon='" + objParSis.getClaveBaseDatos() + "'";
                strSQL += "      and co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "      and tx_nom='" + objParSis.getNombreEmpresa() + "'";
                strSQL += "      and st_reg='A'";
                rstCen = stmCen.executeQuery(strSQL);
                if (rstCen.next()) {
                    intCodRegEmpSel = rstCen.getInt("co_reg");
                }

                strSQL = "";
                strSQL += "select co_reg, co_grp, co_regorg, co_regdes, tx_freact, st_reg";
                strSQL += " from tbm_cfgbasdatrep as a1";
                strSQL += " where a1.co_grp=" + INT_COD_GRP_CNF + "  and a1.st_reg='A' AND co_regOrg=" + intCodRegEmpSel + "";
                strSQL += " ORDER BY a1.co_regdes";
                rstCen = stmCen.executeQuery(strSQL);
                for (int i = 0; rstCen.next(); i++) {
                    arlReg_CfgRegCnxRem = new ArrayList();
                    arlReg_CfgRegCnxRem.add(INT_ARL_COD_REG_ORI, rstCen.getString("co_regorg"));
                    arlReg_CfgRegCnxRem.add(INT_ARL_COD_REG_DES, rstCen.getString("co_regdes"));
                    arlDat_CfgRegCnxRem.add(arlReg_CfgRegCnxRem);
                }
                if (arlDat_CfgRegCnxRem.size() > 0) {//siempre va a ser un solo registro segun configuracion
                    strSQL = "";
                    strSQL += "select co_reg, tx_drvcon, tx_strCon, tx_usrcon, tx_clacon, co_emp, tx_nom";
                    strSQL += " from tbm_basdat as a2";
                    strSQL += " where a2.co_reg=" + objUti.getStringValueAt(arlDat_CfgRegCnxRem, 0, INT_ARL_COD_REG_DES) + "";
                    strSQL += " and st_reg='A'";
                    rstCen = stmCen.executeQuery(strSQL);
                    if (rstCen.next()) {
                        strConRem = rstCen.getString("tx_strCon");
                        strUsrRem = rstCen.getString("tx_usrcon");
                        strPswRem = rstCen.getString("tx_clacon");
                        conRemTmp = DriverManager.getConnection(strConRem, strUsrRem, strPswRem);
                    }
                } else {
                    conRemTmp = null;
                }
                conCen.close();
                conCen = null;
                stmCen.close();
                stmCen = null;
                rstCen.close();
                rstCen = null;
            }
        } catch (java.sql.SQLException e) {
            //System.out.println("ERROR DE SQL EN cargarConfiguracionRemota: " + e);
        } catch (Exception e) {
            //System.out.println("ERROR EN cargarConfiguracionRemota: " + e);
        }
        return conRemTmp;
    }

    /**
     * Esta funcián inserta el registro en la base de datos.
     *
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes = false;
        try 
        {
            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                con.setAutoCommit(false);
                if (objTblMod.getRowCountTrue() > 0) {
                    if (insertar_tbmCabOrdConInv()) {
                        if (insertar_tbmDetOrdConInv()) {
                            if (insertar_tbmConInv()) {
                                if (actualizar_tbmCabTipDoc()) {
                                    con.commit();
                                    blnRes = true;
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                }
                if (chkConBodImp.isSelected()) {
                    blnRes = false;
                    if (insertar_tbmCabOrdConInvBodImp()) {
                        con.commit();
                        blnRes = true;
                    } else {
                        con.rollback();
                    }
                }

            }
            con.close();
            con = null;
            if (conRemTmp != null) {
                conRemTmp.close();
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean insertar_tbmCabOrdConInv() 
    {
        boolean blnRes = true;
        int intUltReg;
        strSQLExe = "";
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_doc) AS co_doc";
                strSQL += " FROM tbm_cabordconinv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    intUltReg = rst.getObject("co_doc") == null ? 0 : rst.getInt("co_doc");
                } else {
                    intUltReg = 0;
                }
                intUltReg++;

                txtCodDoc.setText("" + intUltReg);
                strSQL = "";
                strSQL += "INSERT INTO tbm_cabordconinv(";
                strSQL += " co_emp, co_loc, co_tipdoc,";
                strSQL += " co_doc, fe_doc, ne_numdoc,";
                strSQL += " co_usrrescon, co_bod, tx_obs1,";
                strSQL += " tx_obs2, st_reg, fe_ing,";
                strSQL += " fe_ultmod, co_usring,";
                strSQL += " co_usrmod, st_regrep";
                strSQL += " , co_empRel, co_locRel, co_tipDocRel, co_docRel)";
                strSQL += " VALUES (";
                strSQL += "" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL += "" + objParSis.getCodigoLocal() + ",";//co_loc
                strSQL += "" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL += "" + intUltReg + ",";//co_doc
                strSQL += "'" + objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL += "" + txtNumDoc.getText() + ",";//ne_numdoc
                strSQL += "" + (txtCodRes.getText().length() > 0 ? txtCodRes.getText() : "Null") + ",";//co_usrrescon
                strSQL += "" + txtCodBod.getText() + ",";//co_bod
                strSQL += "" + objUti.codificar(txaObs1.getText()) + ",";//tx_obs1
                strSQL += "" + objUti.codificar(txaObs2.getText()) + ",";//tx_obs2
                strSQL += "'A',";//st_reg
                strSQL += "CURRENT_TIMESTAMP,";//fe_ing
                strSQL += "CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL += "" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL += "" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL += "'I',";//st_regrep
                strSQL += "" + (intCboPedSelCodEmp == 0 ? "Null" : intCboPedSelCodEmp) + ",";//co_empRel
                strSQL += "" + (intCboPedSelCodLoc == 0 ? "Null" : intCboPedSelCodLoc) + ",";//co_locRel
                strSQL += "" + (intCboPedSelCodTipDoc == 0 ? "Null" : intCboPedSelCodTipDoc) + ",";//co_tipDocRel
                strSQL += "" + (intCboPedSelCodDoc == 0 ? "Null" : intCboPedSelCodDoc) + "";//co_docRel
                strSQL += ");";
                strSQLExe += strSQL;
                //System.out.println("insertar_tbmCabOrdConInv: " + strSQLExe);
                stm.executeUpdate(strSQLExe);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    private boolean insertar_tbmCabOrdConInvBodImp() 
    {
        boolean blnRes = true;
        int intUltReg;
        strSQLExe = "";
        try 
        {
            if (con != null)
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_doc) AS co_doc";
                strSQL += " FROM tbm_cabordconinv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    intUltReg = rst.getObject("co_doc") == null ? 0 : rst.getInt("co_doc");
                } else {
                    intUltReg = 0;
                }
                intUltReg++;

                txtCodDoc.setText("" + intUltReg);
                strSQL = "";
                strSQL += "INSERT INTO tbm_cabordconinv(";
                strSQL += " co_emp, co_loc, co_tipdoc,";
                strSQL += " co_doc, fe_doc, ne_numdoc,";
                strSQL += " co_usrrescon, co_bod, tx_obs1,";
                strSQL += " tx_obs2, st_reg, fe_ing,";
                strSQL += " fe_ultmod, co_usring,";
                strSQL += " co_usrmod, st_regrep";
                strSQL += " , co_empRel, co_locRel, co_tipDocRel, co_docRel)";
                strSQL += " VALUES (";
                strSQL += "" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL += "" + objParSis.getCodigoLocal() + ",";//co_loc
                strSQL += "" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL += "" + intUltReg + ",";//co_doc
                strSQL += "'" + objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL += "" + txtNumDoc.getText() + ",";//ne_numdoc
                strSQL += "Null,";//co_usrrescon
                strSQL += "6,";//co_bod
                strSQL += "Null,";//tx_obs1
                strSQL += "Null,";//tx_obs2
                strSQL += "'A',";//st_reg
                strSQL += "CURRENT_TIMESTAMP,";//fe_ing
                strSQL += "CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL += "" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL += "" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL += "'I',";//st_regrep
                strSQL += "" + intCboPedSelCodEmp + ",";//co_empRel
                strSQL += "" + intCboPedSelCodLoc + ",";//co_locRel
                strSQL += "" + intCboPedSelCodTipDoc + ",";//co_tipDocRel
                strSQL += "" + intCboPedSelCodDoc + "";//co_docRel
                strSQL += ");";
                strSQLExe += strSQL;
                //System.out.println("insertar_tbmCabOrdConInv: " + strSQLExe);
                stm.executeUpdate(strSQLExe);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    private boolean insertar_tbmDetOrdConInv()
    {
        boolean blnRes = true;
        strSQLExe = "";
        int j = 0;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                    j++;
                    strSQL = "";
                    strSQL += "INSERT INTO tbm_detordconinv(";
                    strSQL += " co_emp, co_loc, co_tipdoc,";
                    strSQL += " co_doc, co_reg, co_itm, st_regrep";
                    strSQL += ")";
                    strSQL += " VALUES (";
                    strSQL += "" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL += "" + objParSis.getCodigoLocal() + ",";//co_loc
                    strSQL += "" + txtCodTipDoc.getText() + ",";//co_tipdoc
                    strSQL += "" + txtCodDoc.getText() + ",";//co_doc
                    strSQL += "" + j + ",";//co_reg
                    strSQL += "" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + ",";//co_itm
                    strSQL += "'I'";//st_regrep
                    strSQL += ");";
                    strSQLExe += strSQL;
                }
                //System.out.println("insertar_tbmDetOrdConInv: " + strSQLExe);
                stm.executeUpdate(strSQLExe);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    private boolean insertar_tbmConInv()
    {
        boolean blnRes = true;
        strSQLExe = "";
        strAux = "";
        int intUltReg;
        int j = 0;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT MAX(a1.co_reg) AS co_reg";
                strSQL += " FROM tbm_coninv AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    intUltReg = rst.getObject("co_reg") == null ? 0 : rst.getInt("co_reg");
                } else {
                    intUltReg = 0;
                }
                //strAux=objParSis.getFuncionFechaHoraBaseDatos();
                for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                    j++;
                    intUltReg++;
                    strSQL = "";
                    strSQL += "INSERT INTO tbm_coninv(";
                    strSQL += " co_emp, co_reg, co_usrrescon,";
                    strSQL += " co_bod, co_itm, fe_solcon,";
                    strSQL += " tx_obs1, st_regrep, co_locRel, co_tipDocRel, co_docRel, co_regRel";
                    strSQL += ")";

                    strSQL += "VALUES (";
                    strSQL += "" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL += "" + intUltReg + ",";//co_reg
                    strSQL += "" + (txtCodRes.getText().length() > 0 ? txtCodRes.getText() : "Null") + ",";//co_usrrescon;
                    strSQL += "" + txtCodBod.getText() + ",";//co_bod;
                    strSQL += "" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + ",";//co_itm;
                    strSQL += "CURRENT_TIMESTAMP,";//fe_solcon;
                    strSQL += " Null,";//tx_obs1;
                    strSQL += "'I',";//st_regrep;
                    strSQL += "" + objParSis.getCodigoLocal() + ",";//co_locRel
                    strSQL += "" + txtCodTipDoc.getText() + ",";//co_tipDocRel
                    strSQL += "" + txtCodDoc.getText() + ",";//co_docRel
                    strSQL += "" + j + "";//co_regRel

                    strSQL += ");";
                    strSQLExe += strSQL;
                }
                //System.out.println("insertar_tbmConInv: " + strSQLExe);
                stm.executeUpdate(strSQLExe);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    private boolean actualizar_tbmCabTipDoc() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "UPDATE tbm_cabTipDoc";
                strSQL += " SET ne_ultDoc=ne_ultDoc+1";
                strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("actualizar_tbmCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta funcián obtiene la descripcián larga del estado del registro.
     *
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripcián larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funcián devuelve una
     * cadena vacáa.
     */
    private String getEstReg(String estado) 
    {
        if (estado == null) {
            estado = "";
        } else {
            switch (estado.charAt(0)) {
                case 'A':
                    estado = "Activo";
                    break;
                case 'I':
                    estado = "Anulado";
                    break;
                case 'E':
                    estado = "Eliminado";
                    break;
                case 'R':
                    estado = "Pendiente de impresión";
                    break;
                default:
                    estado = "Desconocido";
                    break;
            }
        }

        return estado;
    }

    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg() 
    {
        boolean blnRes = true;
        try {
            txtCodBod.setText("");
            txtNomBod.setText("");
            conCab = cargarConexionRemota();
            if (conCab == null) {//si es en tuval
                conCab = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (conCab != null) {
                stmCab = conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (txtCodTipDoc.getText().equals("")) {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                    strSQL += " FROM (tbm_cabOrdConInv AS a1";
                    if (objParSis.getCodigoMenu() == 1944) {
                        strSQL += "       LEFT OUTER JOIN";
                    } else {
                        strSQL += "       INNER JOIN";
                    }
                    strSQL += "       tbm_cabMovInv AS a3";
                    strSQL += "       ON a1.co_empRel=a3.co_emp AND a1.co_locRel=a3.co_loc AND a1.co_tipDocRel=a3.co_tipDoc AND a1.co_docRel=a3.co_doc)";
                    strSQL += " LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL += " AND a5.co_mnu=" + objParSis.getCodigoMenu();
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                    strSQL += " FROM (tbm_cabOrdConInv AS a1";
                    if (objParSis.getCodigoMenu() == 1944) {
                        strSQL += "       LEFT OUTER JOIN";
                    } else {
                        strSQL += "       INNER JOIN";
                    }
                    strSQL += "       tbm_cabMovInv AS a3";
                    strSQL += "       ON a1.co_empRel=a3.co_emp AND a1.co_locRel=a3.co_loc AND a1.co_tipDocRel=a3.co_tipDoc AND a1.co_docRel=a3.co_doc)";
                    strSQL += " LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strAux = txtCodTipDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_tipDoc=" + strAux + "";
                }

                strAux = txtCodBod.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_bod=" + strAux + "";
                }

                strAux = txtCodRes.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_usrResCon=" + strAux + "";
                }

                if (dtpFecDoc.isFecha()) {
                    strSQL += " AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                }

                strAux = txtCodDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.co_doc=" + strAux + "";
                }

                strAux = txtNumDoc.getText();
                if (!strAux.equals("")) {
                    strSQL += " AND a1.ne_numDoc=" + strAux + "";
                }

                strSQL += " AND a1.st_reg<>'E'";
                strSQL += " ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                //System.out.println("consultarReg: " + strSQL);
                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();

                    intCboPedSelCodEmp = rstCab.getInt("co_empRel");
                    intCboPedSelCodLoc = rstCab.getInt("co_locRel");
                    intCboPedSelCodTipDoc = rstCab.getInt("co_tipDocRel");
                    intCboPedSelCodDoc = rstCab.getInt("co_docRel");
                    cargarReg();
                } else {
                    mostrarMsgInf("No se ha encontrado ningán registro que cumpla el criterio de básqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite cargar el registro seleccionado.
     *
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() 
    {
        boolean blnRes = true;
        blnIsRegEli = false;
        try 
        {
            if (cargarCabReg()) {
                if (cargarDetReg()) {
                    if (objParSis.getCodigoMenu() != 1944) {
                        if (cargarPedidoEmbarcado()) {

                        }
                    }
                }
            }
            blnHayCam = false;
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     *
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() 
    {
        int intPosRel;
        boolean blnRes = true;
        try
        {

            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc,";
                strSQL += " a1.tx_obs1, a1.tx_obs2, a1.st_reg, c1.co_usr, c1.tx_usr, c1.tx_nom AS tx_nomUsr, b1.co_bod, b1.tx_nom AS tx_nomBod";
                strSQL += " , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
                strSQL += " FROM      (";
                strSQL += "                (tbm_cabOrdConInv AS a1 LEFT OUTER JOIN tbm_usr AS c1 ON a1.co_usrResCon=c1.co_usr) ";
                strSQL += "                 INNER JOIN tbm_bod AS b1 ON a1.co_emp=b1.co_emp AND a1.co_bod=b1.co_bod";
                strSQL += "           )";
                strSQL += "           INNER JOIN tbm_cabTipDoc AS a2 ";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND a1.co_doc=" + rstCab.getString("co_doc");
                //System.out.println("cargarCabReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next()) {
                    strAux = rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_natDoc");
                    intSig = (strAux.equals("I") ? 1 : -1);
                    strAux = rst.getString("co_doc");
                    txtCodDoc.setText((strAux == null) ? "" : strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"), "dd/MM/yyyy"));
                    strAux = rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("co_bod");
                    txtCodBod.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_nomBod");
                    txtNomBod.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("co_usr");
                    txtCodRes.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_usr");
                    txtDesCorRes.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_nomUsr");
                    txtDesLarRes.setText((strAux == null) ? "" : strAux);

                    strAux = rst.getString("tx_obs1");
                    txaObs1.setText((strAux == null) ? "" : strAux);
                    strAux = rst.getString("tx_obs2");
                    txaObs2.setText((strAux == null) ? "" : strAux);

                    intCboPedSelCodEmp = rst.getInt("co_empRel");
                    intCboPedSelCodLoc = rst.getInt("co_locRel");
                    intCboPedSelCodTipDoc = rst.getInt("co_tipDocRel");
                    intCboPedSelCodDoc = rst.getInt("co_docRel");

                    //Mostrar el estado del registro.
                    strAux = rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    if (objTooBar.getEstadoRegistro().equals("Eliminado")) {
                        limpiarFrm();
                        blnIsRegEli = true;
                    }
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes = false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            if (conRemTmp != null) {
                conRemTmp.close();
            }
            //Mostrar la posicián relativa del registro.
            intPosRel = rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite cargar el detalle del registro seleccionado.
     *
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes = true;
        try 
        {
            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                //Para los demás modos se muestra: sálo los documentos pagados.
                strSQL = "";

                strSQL += "";
                strSQL += "SELECT b1.*";
                if (objParSis.getCodigoMenu() != 1944) {
                    strSQL += ", b2.nd_canIngImp";
                }
                strSQL += " FROM(";
                strSQL += "       SELECT a1.co_emp, a2.co_itm, a6.co_itmMae, a4.tx_codAlt, a4.tx_codAlt2 ,a4.tx_nomItm, a5.tx_desCor, a5.tx_desLar";
                strSQL += "       ,a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a8.nd_stkcon AS nd_canOrdCon, a2.co_reg, a7.tx_ubi";
                strSQL += "       FROM tbm_cabOrdConInv AS a1 INNER JOIN (tbm_detOrdConInv AS a2";
                strSQL += "                               INNER JOIN tbm_conInv AS a8 ";
                strSQL += "                   ON a2.co_emp=a8.co_emp AND a2.co_loc=a8.co_locrel AND a2.co_tipdoc=a8.co_tipdocrel AND a2.co_doc=a8.co_docrel AND a2.co_reg=a8.co_regrel    )";
                strSQL += "       ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += "       INNER JOIN (tbm_invBod AS a3 INNER JOIN";
                strSQL += "                                         (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg) ";
                strSQL += "                                                             INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL += " 							ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm ";
                strSQL += "			LEFT OUTER JOIN  ";
		strSQL += "	( ";
		strSQL += "		SELECT DISTINCT b1.co_itmMae, b2.tx_ubi ";
		strSQL += "		FROM tbm_equInv as b1 ";
		strSQL += "		INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm) ";
		strSQL += "		INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod) ";
		strSQL += "		WHERE b3.co_empgrp="+objParSis.getCodigoEmpresaGrupo()+" AND b3.co_bodgrp = "+txtCodBod.getText();
		strSQL += "	) as a7 ON (a6.co_itmMae=a7.co_itmMae)";
                strSQL += "    )ON a2.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a2.co_itm=a3.co_itm";
                strSQL += "    WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL += "    AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL += "    AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += "    AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL += "    ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += ") AS b1";
                if (objParSis.getCodigoMenu() != 1944) {
                    strSQL += " LEFT OUTER JOIN(";
                    strSQL += "       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_itmMae, a1.nd_can AS nd_canIngImp";
                    strSQL += "       FROM tbm_detMovInv AS a1 INNER JOIN tbm_inv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL += "       INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL += "       WHERE a1.co_emp=" + intCboPedSelCodEmp + "";
                    strSQL += "       AND a1.co_loc=" + intCboPedSelCodLoc + "";
                    strSQL += "       AND a1.co_tipDoc=" + intCboPedSelCodTipDoc + "";
                    strSQL += "       AND a1.co_doc=" + intCboPedSelCodDoc + "";
                    strSQL += "       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.co_itmMae, a1.nd_can";
                    strSQL += ") AS b2";
                    strSQL += " ON b1.co_empRel=b2.co_emp AND b1.co_locRel=b2.co_loc AND b1.co_tipDocRel=b2.co_tipDoc AND b1.co_docRel=b2.co_doc AND b1.co_itmMae=b2.co_itmMae";
                }
                strSQL += " ORDER BY b1.co_reg";
                //System.out.println("CargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()) 
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP, "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE, "" + rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ITM, "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_LET_ITM, "" + rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BUT_ANE_UNO, "");
                    vecReg.add(INT_TBL_DAT_NOM_ITM, "" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_STK_ACT, "" );
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED, "" + rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_UBI_ITM, "" + rst.getString("tx_ubi")==null?"":rst.getString("tx_ubi"));
                
                    if (objParSis.getCodigoMenu() == 1944) 
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING_IMP, "0");
                        vecReg.add(INT_TBL_DAT_CAN_CON, "0");
                    } 
                    else 
                    {
                        vecReg.add(INT_TBL_DAT_CAN_ING_IMP, "" + rst.getObject("nd_canIngImp") == null ? "0" : rst.getString("nd_canIngImp"));
                        vecReg.add(INT_TBL_DAT_CAN_CON, "" + rst.getObject("nd_canOrdCon") == null ? "0" : rst.getString("nd_canOrdCon"));
                    }

                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                if (conRemTmp != null) {
                    conRemTmp.close();
                }
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                if (blnIsRegEli) {
                    objTblMod.removeAllRows();
                }
                vecDat.clear();
//                    objTooBar.setMenSis("Listo");
            }

        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean isCambioEstadoRegistro() {
        boolean blnRes = true;
        try {
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                objTblMod.setValueAt("I", i, INT_TBL_DAT_LIN);
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián actualiza el registro en la base de datos.
     *
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() 
    {
        boolean blnRes = false;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (actualizar_tbmCabOrdConInv()) {
                    if (eliminar_tbmConInvModifica()) {
                        if (eliminar_tbmDetOrdConInv()) {
                            if (insertar_tbmDetOrdConInv()) {
                                if (insertar_tbmConInv()) {
                                    con.commit();
                                    blnRes = true;
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                        } else {
                            con.rollback();
                        }
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite actualizar la cabecera de un registro.
     *
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabOrdConInv()
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strAux = objParSis.getFuncionFechaHoraBaseDatos();
                strSQL = "";
                strSQL += "UPDATE tbm_cabordconinv";
                strSQL += " SET fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(), "dd/MM/yyyy", objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL += ", ne_numDoc=" + objUti.codificar(txtNumDoc.getText(), 2);
                strSQL += ", co_usrrescon=" + txtCodRes.getText();
                strSQL += ", co_bod=" + txtCodBod.getText();
                strSQL += ", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL += ", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL += ", fe_ultMod=" + strAux + "";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite eliminar el detalle de un registro.
     *
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmDetOrdConInv() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "DELETE FROM tbm_detOrdConInv";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite eliminar el detalle de un registro.
     *
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmConInvModifica() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "DELETE FROM tbm_conInv";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_locRel=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDocRel=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_docRel=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián anula el registro de la base de datos.
     *
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg() 
    {
        boolean blnRes = false;
        try 
        {
            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                con.setAutoCommit(false);
                if (anular_tbmCabOrdConInv()) {
                    if (anular_tbmConInv()) {
                        con.commit();
                        blnRes = true;
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
            if (conRemTmp != null) {
                conRemTmp.close();
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite anular la cabecera de un registro.
     *
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabOrdConInv()
    {
        boolean blnRes = true;
        try
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabOrdConInv";
                strSQL += " SET st_reg='I'";
                strSQL += ", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite anular la informacion de la tabla tbm_conInv
     * relacionada con tbm_cabOrdConInv.
     *
     * @return true: Si se pudieron anular los registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmConInv() 
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_conInv";
                strSQL += " SET st_conrea='I'";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_locRel=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDocRel=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_docRel=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite anular la informacion de la tabla tbm_conInv
     * relacionada con tbm_cabOrdConInv.
     *
     * @return true: Si se pudieron anular los registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmConInv()
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_conInv";
                strSQL += " SET st_conrea='E'";
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_locRel=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDocRel=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_docRel=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
                datFecAux = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián elimina el registro de la base de datos.
     *
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg() 
    {
        boolean blnRes = false;
        try 
        {
            con = cargarConexionRemota();
            if (con == null) {//si es en tuval
                con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            }
            if (con != null) {
                con.setAutoCommit(false);
                if (eliminar_tbmCabOrdConInv()) {
                    if (eliminar_tbmConInv()) {
                        con.commit();
                        blnRes = true;
                    } else {
                        con.rollback();
                    }
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite eliminar la cabecera de un registro.
     *
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabOrdConInv()
    {
        boolean blnRes = true;
        try 
        {
            if (con != null) {
                stm = con.createStatement();
                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "UPDATE tbm_cabOrdConInv";
                strSQL += " SET st_reg='E'";
                strSQL += ", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL += ", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL += " WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL += " AND co_loc=" + rstCab.getString("co_loc");
                strSQL += " AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL += " AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm = null;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite llenar la tabla con los items que tienen faltantes
     *
     * @return true: Si se pudo llenar la tabla con los datos
     * <BR>false: En el caso contrario.
     */
    private boolean llenarModeloDatos() 
    {
        boolean blnRes = true;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_itm, a3.co_itmMae, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, b1.tx_desCor, b1.tx_desLar, a2.nd_stkAct, a2.tx_ubi";
                strSQL += " FROM (tbm_inv AS a1 INNER JOIN tbm_var AS b1 ON a1.co_uni=b1.co_reg)";
                strSQL += " INNER JOIN tbm_invBod AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                strSQL += " INNER JOIN tbm_equInv AS a3";
                strSQL += " ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_bod=" + txtCodBod.getText() + "";
                if (arlDatCodItmMae.size() > 0) {
                    strSQL += " AND a3.co_itmMae IN(";
                    for (int f = 0; f < arlDatCodItmMae.size(); f++) {
                        if (f == 0) {
                            strSQL += "" + objUti.getStringValueAt(arlDatCodItmMae, f, INT_ARL_FAL_COD_ITM_MAE) + "";
                        } else {
                            strSQL += ", " + objUti.getStringValueAt(arlDatCodItmMae, f, INT_ARL_FAL_COD_ITM_MAE) + "";
                        }
                        if (f == (arlDatCodItmMae.size() - 1)) {
                            strSQL += ")";
                        }
                    }
                }
                strSQL += " ORDER BY a1.co_emp, a2.co_bod";
                //System.out.println("llenarModeloDatos: " + strSQL);
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP, "" + rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE, "" + rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ITM, "" + rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM, "" + rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_LET_ITM, "" + rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BUT_ANE_UNO, null);
                    vecReg.add(INT_TBL_DAT_NOM_ITM, "" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_STK_ACT, "" + rst.getString("nd_stkAct")==null?"":rst.getString("nd_stkAct"));
                    vecReg.add(INT_TBL_DAT_UBI_ITM, "" + rst.getString("tx_ubi")==null?"":rst.getString("tx_ubi"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED, "" + rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_CAN_ING_IMP, "0");
                    vecReg.add(INT_TBL_DAT_CAN_CON, "0");
                    vecDat.add(vecReg);
                }
                con.close();
                con = null;
                stm.close();
                stm = null;
                rst.close();
                rst = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                objTblMod.initRowsState();

            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    private boolean cargarPedidoEmbarcado() 
    {
        boolean blnRes = true;
        int i = -1;
        try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            cboPedSel.removeAllItems();
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL += " , a1.ne_tipnotped, a1.co_exp, 	a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL += " , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp AS co_imp";
                strSQL += " , a3.tx_nom AS tx_nomImp, a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp, a8.co_bodGrp AS co_bod, a7.tx_nom AS tx_nomBod";
                strSQL += " FROM (tbm_cabMovInv AS a1 INNER JOIN ";
                strSQL += " 		(tbm_detMovInv AS a6";
                strSQL += " 			INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                strSQL += " 			INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                strSQL += " 		)";
                strSQL += " 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL += "       )";
                strSQL += " INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                strSQL += " LEFT OUTER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                if (objTooBar.getEstado() == 'n') {
                    strSQL += " WHERE a1.co_tipDoc IN(14,245)";
                    cboPedSel.addItem("----------");
                } else {
                    strSQL += " WHERE a1.co_emp=" + intCboPedSelCodEmp + "";
                    strSQL += " AND a1.co_loc=" + intCboPedSelCodLoc + "";
                    strSQL += " AND a1.co_tipDoc=" + intCboPedSelCodTipDoc + "";
                    strSQL += " AND a1.co_doc=" + intCboPedSelCodDoc + "";
                }

                strSQL += " AND a1.st_reg='A'";
                strSQL += " GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL += " , a1.ne_tipnotped, a1.co_exp, 	a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL += " , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp";
                strSQL += " , a3.tx_nom, a5.co_exp, a5.tx_nom, a5.tx_nom2, a8.co_bodGrp, a7.tx_nom";
                //System.out.println("cargarPedidoEmbarcado: " + strSQL);
                rst = stm.executeQuery(strSQL);

                arlDatPed.clear();
                for (i = 0; rst.next(); i++) {
                    cboPedSel.addItem("" + rst.getString("tx_numDoc2"));
                    //para saber cual pedido se ha seleccionado
                    arlRegPed = new ArrayList();
                    arlRegPed.add(INT_ARL_PED_COD_EMP, rst.getString("co_emp"));
                    arlRegPed.add(INT_ARL_PED_COD_LOC, rst.getString("co_loc"));
                    arlRegPed.add(INT_ARL_PED_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    arlRegPed.add(INT_ARL_PED_COD_DOC, rst.getString("co_doc"));
                    arlRegPed.add(INT_ARL_PED_NUM_PED, rst.getString("tx_numDoc2"));
                    arlRegPed.add(INT_ARL_PED_IND_SEL, i);
                    arlRegPed.add(INT_ARL_PED_COD_IMP, rst.getString("co_imp"));
                    arlDatPed.add(arlRegPed);
                }
                cboPedSel.setSelectedIndex(0);
                stm.close();
                stm = null;
                rst.close();
                rst = null;
                con.close();
                con = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;

    }

}
