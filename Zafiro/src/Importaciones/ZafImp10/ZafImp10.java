/*
 * ZafImp10.java
 *
 * Created on 22 de agosto de 2017, 15:54 PM
 */
package Importaciones.ZafImp10;
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
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author  Rosa Zambrano
 */
public class ZafImp10 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_DAT_LIN=0;                         //Línea
    private static final int INT_TBL_DAT_CODSEG = 1;                    //Código de Seguimiento.
    private static final int INT_TBL_DAT_COD_EMP = 2;                   //Código de Empresa. (ORCOIN)
    private static final int INT_TBL_DAT_COD_LOC = 3;                   //Código de Local.(ORCOIN)
    private static final int INT_TBL_DAT_NOM_IMP = 4;                   //Nombre del importador
    private static final int INT_TBL_DAT_COD_TIP_DOC = 5;               //Codigo Tipo de Documento.(ORCOIN)
    private static final int INT_TBL_DAT_DES_COR_TIP_DOC = 6;           //Descripción Corta Tipo de Documento.
    private static final int INT_TBL_DAT_DES_LAR_TIP_DOC = 7;           //Descripción Larga Tipo de Documento.
    private static final int INT_TBL_DAT_COD_DOC = 8;                   //Código del documento.
    private static final int INT_TBL_DAT_NUM_DOC = 9;                   //Número del documento.
    private static final int INT_TBL_DAT_FEC_DOC = 10;                  //Fecha del documento.
        
    private static final int INT_TBL_DAT_COD_EMP_ING_IMP=11;            //Código de Empresa del ingreso por importación .
    private static final int INT_TBL_DAT_COD_LOC_ING_IMP=12;            //Código de local del ingreso por importación .
    private static final int INT_TBL_DAT_COD_TIP_ING_IMP=13;            //Código de tipo de documento del ingreso por importación .
    private static final int INT_TBL_DAT_COD_DOC_ING_IMP=14;            //Codigo documento del ingreso por importación .
    private static final int INT_TBL_DAT_NUM_PED=15;                    //Número del pedido.    
    
    private static final int INT_TBL_DAT_COD_ITM = 16;                  //Código del ítem.
    private static final int INT_TBL_DAT_COD_ITM_MAE = 17;              //Código maestro del ítem.
    private static final int INT_TBL_DAT_COD_ALT_ITM = 18;              //Código alterno del ítem.
    private static final int INT_TBL_DAT_COD_ALT_DOS = 19;              //Código alterno 2 del ítem.
    private static final int INT_TBL_DAT_NOM_ITM = 20;                  //Nombre del ítem.
    private static final int INT_TBL_DAT_CAN_CON_BUE_EST = 21;          //Cantidad del item Contada en Buen Estado.
    private static final int INT_TBL_DAT_CAN_CON_MAL_EST = 22;          //Cantidad del item Contada en Mal Estado.
    private static final int INT_TBL_DAT_UNI_MED = 23;                  //Descripción Corta de Unidad de Medida.
    private static final int INT_TBL_DAT_COD_USR=24;                    //Codigo de Usuario que realiza el conteo.
    private static final int INT_TBL_DAT_TXT_USR=25;
    private static final int INT_TBL_DAT_NOM_USR=26;
    private static final int INT_TBL_DAT_FEC_REA_CON = 27;              //Fecha en que se realiza conteo.
    private static final int INT_TBL_DAT_BUT_HIS = 28;                  //Histórico
    private static final int INT_TBL_DAT_TXT_OBS = 29;                  //Observación
    private static final int INT_TBL_DAT_BUT_OBS = 30;                  //Observación de Conteo.
    
    //Constantes: Códigos de Menú.
    private static final int INT_COD_MNU_INGIMP=2889;                   //Ingresos por Importacion.
    
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
    private ZafVenCon vcoEmpImp;                                //Ventana de consulta "Empresa".
    private ZafVenCon vcoPed;                                   //Ventana de consulta "Pedidos".
    private ZafVenCon vcoItm;                                   //Ventana de consulta "Item".
    private ZafPerUsr objPerUsr;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    
    //Variables
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private int intCodEmpIngImp;
    private int intCodLocIngImp;
    private int intCodTipDocIngImp;
    private String strSQL, strAux;
    private String strCodEmp, strNomEmp;                        //Contenido del campo al obtener el foco.
    private String strCodPed, strPedImp;                        //Contenido del campo al obtener el foco.
    private String strCodAlt, strCodAlt2, strNomItm,strNumPed;  //Contenido del campo al obtener el foco.
    
    private String strVersion=" v0.1.4";

    /** Crea una nueva instancia de la clase . */
    public ZafImp10(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                initComponents();   
                if (!configurarFrm())
                    exitForm();
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
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
        txtCodItmMae = new javax.swing.JTextField();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panBusItm = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblPed = new javax.swing.JLabel();
        txtCodPed = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        chkItmNoCon = new javax.swing.JCheckBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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

        panFil.setPreferredSize(new java.awt.Dimension(0, 440));
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
        optTod.setBounds(10, 80, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 100, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Item");
        panFil.add(lblItm);
        lblItm.setBounds(30, 120, 50, 20);
        panFil.add(txtCodItmMae);
        txtCodItmMae.setBounds(70, 120, 30, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(100, 120, 30, 20);

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
        txtCodAlt.setBounds(130, 120, 100, 20);

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
        txtCodAlt2.setBounds(230, 120, 80, 20);

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
        txtNomItm.setBounds(310, 120, 330, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 120, 20, 20);

        panBusItm.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panBusItm.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panBusItm.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panBusItm.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panBusItm.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panBusItm.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panBusItm);
        panBusItm.setBounds(30, 150, 328, 50);

        lblEmpImp.setText("Importador:");
        lblEmpImp.setToolTipText("Empresa");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(30, 210, 80, 20);

        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(120, 210, 80, 20);

        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(200, 210, 440, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(640, 210, 20, 20);

        lblPed.setText("Pedido:");
        lblPed.setToolTipText("Cliente");
        panFil.add(lblPed);
        lblPed.setBounds(30, 230, 80, 20);

        txtCodPed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPedActionPerformed(evt);
            }
        });
        txtCodPed.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPedFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPedFocusLost(evt);
            }
        });
        panFil.add(txtCodPed);
        txtCodPed.setBounds(74, 230, 45, 20);

        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        panFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(120, 230, 80, 20);

        txtPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPedIngImpActionPerformed(evt);
            }
        });
        txtPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPedIngImpFocusLost(evt);
            }
        });
        panFil.add(txtPedIngImp);
        txtPedIngImp.setBounds(200, 230, 440, 20);

        butPedImp.setText("...");
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panFil.add(butPedImp);
        butPedImp.setBounds(640, 230, 20, 20);

        chkItmNoCon.setText("Mostrar items no contados");
        chkItmNoCon.setToolTipText("");
        chkItmNoCon.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                chkItmNoConItemStateChanged(evt);
            }
        });
        panFil.add(chkItmNoCon);
        chkItmNoCon.setBounds(30, 270, 340, 20);

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

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else
            {
                mostrarEmpImp(1);
            }
        }
        else
            txtCodImp.setText(strCodEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
            }
            else
            {
                mostrarEmpImp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtNomImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);            
        }
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        mostrarEmpImp(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butImpActionPerformed

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
            txtCodImp.setText("");
            txtNomImp.setText("");
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
            chkItmNoCon.setSelected(false);
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

    private void chkItmNoConItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_chkItmNoConItemStateChanged
        if (chkItmNoCon.isSelected() && !optFil.isSelected())
        optFil.setSelected(true);
    }//GEN-LAST:event_chkItmNoConItemStateChanged

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butImp;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butPedImp;
    private javax.swing.JCheckBox chkItmNoCon;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPed;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panBusItm;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodItmMae;
    private javax.swing.JTextField txtCodPed;
    private javax.swing.JTextField txtNomImp;
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
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTblCelRenBut = new ZafTblCelRenBut();
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
                        
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3227))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3228))
            {
                butCer.setVisible(false);
            }
                        
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setCheckBoxChecked(false);
            objSelFec.setTitulo("Fecha de realización del conteo");
            objSelFec.setBounds(4, 4, 472, 72);
            panFil.add(objSelFec);
            
            //Configurar objetos.
            txtCodItmMae.setVisible(false);
            txtCodItm.setVisible(false);
            //txtCodPed.setVisible(false);
            
            //Configurar las ZafVenCon.
            configurarVenConEmp();
            configurarPedidos();
            configurarVenConItm();
            
            //Configurar los JTables
            configurarTblDat();
            
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
            vecCab=new Vector(31);  //Almacena las cabeceras
            vecCab.clear();
            //Constantes: Columnas del JTable:            
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP,"Importador");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP,"Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP,"Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_ING_IMP,"Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP,"Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE,"Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Item");
            vecCab.add(INT_TBL_DAT_CAN_CON_BUE_EST,"Can.Ord.Con.Bue.Est.");
            vecCab.add(INT_TBL_DAT_CAN_CON_MAL_EST,"Can.Ord.Con.Mal.Est.");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Uni.Med.");
                        
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_TXT_USR,"Usr.");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Nom.Usr.");
            vecCab.add(INT_TBL_DAT_FEC_REA_CON,"Fec.Rea.Con.");
            vecCab.add(INT_TBL_DAT_BUT_HIS,"...");
            vecCab.add(INT_TBL_DAT_TXT_OBS,"Obs.");
            vecCab.add(INT_TBL_DAT_BUT_OBS,"...");
            
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
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_ING_IMP).setPreferredWidth(60); 
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(100);    
            
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_BUE_EST).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_MAL_EST).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(50);
            
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TXT_USR).setPreferredWidth(50);   
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(108);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REA_CON).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_TXT_OBS).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(30);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            if(objParSis.getCodigoUsuario()!=1)
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_IMP, tblDat);  
            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_CON_MAL_EST, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_ING_IMP, tblDat);  
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_BUE_EST).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_CON_MAL_EST).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_HIS);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            
            //Configurar JTable:  Botones.
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_HIS:
//                            if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_TXT_OBS).toString().equals(""))
//                                objTblCelRenBut.setText("");
//                            else
                                objTblCelRenBut.setText("...");
                        break;                        
                        case INT_TBL_DAT_BUT_OBS:
                            if(objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_TXT_OBS).toString().equals(""))
                                objTblCelRenBut.setText("");
                            else
                                objTblCelRenBut.setText("...");
                        break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new ZafTableAdapter() 
            {
                int intFilSel, intColSel;
                @Override
                public void beforeEdit(ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUT_OBS:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TXT_OBS).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                        }
                    }
                }

                @Override
                public void afterEdit(ZafTableEvent evt) 
                {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        switch (intColSel) 
                        {
                            case INT_TBL_DAT_BUT_HIS:
                                cargarVentanaHis( tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_EMP).toString()
                                                , tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_LOC).toString()
                                                , tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_TIP_DOC).toString()
                                                , tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_DOC).toString()
                                                , tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM_MAE).toString() );
                                break;                            
                            case INT_TBL_DAT_BUT_OBS:
                                cargarVentanaObsAut(tblDat.getSelectedRow(), INT_TBL_DAT_TXT_OBS );
                                break;
                        }
                    }
                }
            });
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
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
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código del seguimiento";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código  de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
               case INT_TBL_DAT_NOM_IMP:
                    strMsg="Nombre del importador";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg="Código de empresa del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg="Código de local del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_TIP_ING_IMP:
                    strMsg="Código de tipo de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg="Código de documento del Ingreso por Importación";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número de Pedido del Ingreso por Importación";
                    break; 
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item en el sistema";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg="Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_ALT_DOS:
                    strMsg="Código alterno 2 del ítem";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_CAN_CON_BUE_EST:
                    strMsg="Cantidad de Orden de Conteo en buen estado";
                    break;
                case INT_TBL_DAT_CAN_CON_MAL_EST:
                    strMsg="Cantidad de Orden de Conteo en mal estado";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida del item";
                    break;
                case INT_TBL_DAT_COD_USR:
                    strMsg="Código del usuario que realizó el conteo";
                    break;
                case INT_TBL_DAT_TXT_USR:
                     strMsg="Usuario que realizó el conteo";
                     break;     
                case INT_TBL_DAT_NOM_USR:
                     strMsg="Nombre del usuario que realizó el conteo";
                     break;                    
                case INT_TBL_DAT_FEC_REA_CON:
                    strMsg="Fecha de realización del conteo";
                    break;
                case INT_TBL_DAT_BUT_HIS:
                    strMsg="Observación del conteo";
                    break;                       
                case INT_TBL_DAT_TXT_OBS:
                    strMsg="Observación del conteo";
                    break;                   
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="Observación del conteo";
                    break;   
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Importadores".
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
            strSQL+=" SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmpImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de importadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmpImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
            strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc ";
            strSQL+=" WHERE a1.st_Reg='A' ";
            strSQL+=" AND a1.co_tipDoc in(select co_tipDoc from tbr_tiPDocPrg where co_emp=" + objParSis.getCodigoEmpresa() + " and co_loc=" + objParSis.getCodigoLocal() + " and co_mnu= "+INT_COD_MNU_INGIMP+")";
            strSQL+=" AND a1.co_mnu="+INT_COD_MNU_INGIMP;
            strSQL+=" ORDER BY a1.fe_doc DESC";
            
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
            strSQL+=" SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
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
    private boolean mostrarEmpImp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmpImp.setCampoBusqueda(2);
                    vcoEmpImp.setVisible(true);
                    if (vcoEmpImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodImp.setText(vcoEmpImp.getValueAt(1));
                        txtNomImp.setText(vcoEmpImp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmpImp.buscar("a1.co_emp", txtCodImp.getText()))
                    {
                        txtCodImp.setText(vcoEmpImp.getValueAt(1));
                        txtNomImp.setText(vcoEmpImp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmpImp.setCampoBusqueda(0);
                        vcoEmpImp.setCriterio1(11);
                        vcoEmpImp.cargarDatos();
                        vcoEmpImp.setVisible(true);
                        if (vcoEmpImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmpImp.getValueAt(1));
                            txtNomImp.setText(vcoEmpImp.getValueAt(2));
                        }
                        else
                        {
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmpImp.buscar("a1.tx_nom", txtNomImp.getText()))
                    {
                        txtCodImp.setText(vcoEmpImp.getValueAt(1));
                        txtNomImp.setText(vcoEmpImp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmpImp.setCampoBusqueda(1);
                        vcoEmpImp.setCriterio1(11);
                        vcoEmpImp.cargarDatos();
                        vcoEmpImp.setVisible(true);
                        if (vcoEmpImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmpImp.getValueAt(1));
                            txtNomImp.setText(vcoEmpImp.getValueAt(2));
                        }
                        else
                        {
                            txtNomImp.setText(strNomEmp);
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
                        intCodEmpIngImp = Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp = Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp = Integer.parseInt(vcoPed.getValueAt(3));

                    }
                    break;
                 case 1: //Búsqueda directa por "Código Pedido".
                    if (vcoPed.buscar("a1.co_doc", txtCodPed.getText()))
                    {
                        txtCodPed.setText(vcoPed.getValueAt(4));
                        txtNumDocIngImp.setText(vcoPed.getValueAt(5));
                        txtPedIngImp.setText(vcoPed.getValueAt(6));
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
                        intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                        intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                        intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
                            intCodEmpIngImp=Integer.parseInt(vcoPed.getValueAt(1));
                            intCodLocIngImp=Integer.parseInt(vcoPed.getValueAt(2));
                            intCodTipDocIngImp=Integer.parseInt(vcoPed.getValueAt(3));
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
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Histórico */">
    private void cargarVentanaHis(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodItmMae)
    {
        Importaciones.ZafImp10.ZafImp10_01 objImp10_01=new Importaciones.ZafImp10.ZafImp10_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis
                                                                                            , Integer.parseInt(strCodEmp), Integer.parseInt(strCodLoc)
                                                                                            , Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc)
                                                                                            , Integer.parseInt(strCodItmMae));
        objImp10_01.show();
        objImp10_01=null;   
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Observacion Autorizacion Solicitud */">
    private void cargarVentanaObsAut( int intFil, int intCol)
    {
        String strObs = (tblDat.getValueAt(intFil, intCol) == null ? "" : tblDat.getValueAt(intFil, intCol).toString());
        Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs obj = new Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiButObs(JOptionPane.getFrameForComponent(this), true, strObs);
        obj.show();
        if (obj.getAceptar()) 
        {
            tblDat.setValueAt(obj.getObser(), intFil, intCol);
        }
        obj = null;
    }
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
                strAux="";
                if(objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strAux+=" AND a.fe_con BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a.fe_con <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a.fe_con >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                                break;
                        case 3: //Todo....
                                break;
                    }
                }

                stm=con.createStatement();
                //Obtener la condición.
                strSQL="";
                strSQL+=" SELECT Seg.co_seg, a.* FROM ( \n";
                strSQL+="    SELECT a1.fe_Doc, a1.co_emp, a1.co_loc, a1.co_tipdoc, a8.tx_desCor, a8.tx_desLar, a1.co_doc, a1.ne_numDoc, a2.co_reg, a.co_itm, a5.co_itmMae \n";
                strSQL+="         , CASE WHEN a.co_itm IS NULL THEN '' ELSE a3.tx_codAlt END AS tx_codAlt \n";
                strSQL+="         , CASE WHEN a.co_itm IS NULL THEN '' ELSE a3.tx_codAlt2 END AS tx_codAlt2 \n ";   
                strSQL+="         , CASE WHEN a.co_itm IS NULL THEN a.tx_obs1 ELSE a3.tx_nomItm END AS tx_nomItm \n";
                strSQL+="         , a4.tx_desCor AS tx_uniMed, a.nd_stkCon as nd_CanBueEst, a.nd_canMalEst \n";
                strSQL+="         , a6.tx_numDoc2, a7.tx_nom as tx_nomEmp, a.st_conrea, a.tx_obs1 \n";
                strSQL+="         , CAST (a.fe_reaCon AS DATE) as fe_con, a.co_usrResCon as co_usr, a9.tx_usr, a9.tx_nom as tx_nomUsr, a.fe_reaCon \n";    
                strSQL+="         , a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a1.st_Reg \n";
                strSQL+="    FROM tbm_conInv AS a \n";
                strSQL+="    LEFT OUTER JOIN ( tbm_cabOrdConInv AS a1 INNER JOIN tbm_detOrdConInv AS a2 \n";
                strSQL+="                      ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc \n";
                strSQL+="    ) ON a.co_emp=a2.co_emp AND a.co_locrel=a2.co_loc AND a.co_tipdocrel=a2.co_tipdoc AND a.co_docrel=a2.co_doc AND a.co_regrel=a2.co_reg  \n";
                strSQL+="    LEFT OUTER JOIN ( tbm_inv AS a3 LEFT OUTER JOIN tbm_var AS a4 ON a3.co_uni=a4.co_reg \n";
                strSQL+="                      INNER JOIN tbm_equInv AS a5 ON a3.co_emp=a5.co_emp AND a3.co_itm=a5.co_itm \n";
                strSQL+="    ) ON (a.co_emp=a3.co_emp AND a.co_itm=a3.co_itm) \n";
                strSQL+="    INNER JOIN tbm_cabMovInv as a6 ON (a1.co_empRel=a6.co_emp AND a1.co_locRel=a6.co_loc AND a1.co_tipDocRel=a6.co_tipDoc AND a1.co_docRel=a6.co_doc) \n";
                strSQL+="    INNER JOIN tbm_emp as a7 ON (a1.co_empRel=a7.co_emp) \n";
                strSQL+="    INNER JOIN tbm_cabTipDoc as a8 ON (a1.co_emp=a8.co_emp AND a1.co_loc=a8.co_loc AND a1.co_tipDoc=a8.co_tipDoc) \n";
                strSQL+="    LEFT OUTER JOIN tbm_usr AS a9 ON (a.co_usrResCon=a9.co_usr)";
                strSQL+="    WHERE a1.st_reg='A' \n";
                if(!chkItmNoCon.isSelected()) {
                    strSQL+="    AND a.st_conRea IN ('S') AND a.nd_stkCon >0 \n";  //Presenta solo los items contados, cuando no se selecciona el chk.
                }
                strSQL+=" ) as a \n";
                
                strSQL+=" LEFT OUTER JOIN( \n";
                strSQL+="    SELECT co_seg, a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
                strSQL+="    FROM tbm_cabSegMovInv  as a \n";
                strSQL+="    INNER JOIN tbm_cabMovInv as a1 ON (a.co_empRelCabMovInv=a1.co_emp AND a.co_locRelCabMovInv=a1.co_loc AND a.co_tipDocRelCabMovInv=a1.co_tipDoc AND a.co_docRelCabMovInv=a1.co_doc) \n";
                strSQL+="    INNER JOIN tbr_tipDocPrg as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a2.co_mnu="+INT_COD_MNU_INGIMP+") \n";
                strSQL+=" ) AS Seg";
                strSQL+=" ON a.co_empRel=Seg.co_emp AND a.co_locRel=Seg.co_loc AND a.co_tipdocRel=Seg.co_tipDoc AND a.co_docRel=Seg.co_doc \n";
   
                //Filtro
                strSQL+=" WHERE a.st_Reg='A' ";
                if (txtCodItmMae.getText().length() > 0) {
                    strSQL+=" AND a.co_itmMae=" + txtCodItmMae.getText() + "";
                }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0){
                    strSQL+=" AND ((LOWER(a.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if(txtCodImp.getText().length()>0){
                    strSQL+=" AND a.co_empRel=" + txtCodImp.getText() +"\n";  
                }
                if(txtCodPed.getText().length()>0){
                    strSQL+=" AND a.co_empRel=" + intCodEmpIngImp + " AND a.co_locRel=" + intCodLocIngImp + " AND a.co_tipDocRel=" + intCodTipDocIngImp + " AND a.co_docRel=" + txtCodPed.getText() +"\n";  
                }
                strSQL+=" " + strAux+"\n";    //Fecha
             
                strSQL+=" ORDER BY a.fe_Doc, a.tx_numDoc2, a.tx_codAlt, a.fe_reaCon \n";
                System.out.println("conteos: "+strSQL);
                rst=stm.executeQuery(strSQL);
                
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
                        vecReg.add(INT_TBL_DAT_CODSEG,     rst.getString("co_seg") );
                        vecReg.add(INT_TBL_DAT_COD_EMP,    rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,    rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_IMP,    rst.getString("tx_nomEmp"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,    rst.getString("co_doc"));                        
                        vecReg.add(INT_TBL_DAT_NUM_DOC,    rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,    rst.getString("fe_doc"));
                        
                        vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,rst.getString("co_empRel"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,rst.getString("co_locRel"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_ING_IMP,rst.getString("co_tipdocRel"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,rst.getString("co_docRel"));
                        vecReg.add(INT_TBL_DAT_NUM_PED,    rst.getString("tx_numDoc2"));

                        vecReg.add(INT_TBL_DAT_COD_ITM,    rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,    rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_CAN_CON_BUE_EST,rst.getString("nd_CanBueEst"));
                        vecReg.add(INT_TBL_DAT_CAN_CON_MAL_EST,rst.getString("nd_canMalEst"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,    rst.getString("tx_uniMed"));
                        
                        vecReg.add(INT_TBL_DAT_COD_USR,    rst.getString("co_usr"));         //co_usr que realizó el conteo.
                        vecReg.add(INT_TBL_DAT_TXT_USR,    rst.getString("tx_usr"));         //usuario que realizó conteo.
                        vecReg.add(INT_TBL_DAT_NOM_USR,    rst.getString("tx_nomUsr"));      //nombre del usuario que realizó conteo.
                        vecReg.add(INT_TBL_DAT_FEC_REA_CON,rst.getString("fe_reaCon"));
                        vecReg.add(INT_TBL_DAT_BUT_HIS,   "");
                        vecReg.add(INT_TBL_DAT_TXT_OBS,    rst.getString("tx_obs1")==null?"":rst.getString("tx_obs1"));
                        vecReg.add(INT_TBL_DAT_BUT_OBS,   "");
                        
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

                if (blnCon) 
                {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                }
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
 
        
    

}
