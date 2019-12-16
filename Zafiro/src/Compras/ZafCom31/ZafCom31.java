/*
 * ZafCom31.java
 *
 * Created on 23 de julio de 2009, 03:03 PM
 */
package Compras.ZafCom31;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafVenCon.ZafVenCon;
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
public class ZafCom31 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_SEG=1;                     //Código de seguimiento.
    static final int INT_TBL_DAT_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=3;                     //Código del local.
    static final int INT_TBL_DAT_NOM_LOC=4;                     //Nombre del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=5;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=6;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=7;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=8;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_NUM_DOC=9;                     //Número del documento (Preimpreso).
    static final int INT_TBL_DAT_COD_EMP_REL=10;                //Código de Empresa (documento relacionado).
    static final int INT_TBL_DAT_COD_LOC_REL=11;                //Código del local (documento relacionado).
    static final int INT_TBL_DAT_COD_TIP_DOC_REL=12;            //Código del tipo de documento (documento relacionado).
    static final int INT_TBL_DAT_COD_DOC_REL=13;                //Código de documento (documento relacionado).
    static final int INT_TBL_DAT_FEC_DOC=14;                    //Fecha del documento.
    static final int INT_TBL_DAT_COD_PRV=15;                    //Código del proveedor.
    static final int INT_TBL_DAT_NOM_PRV=16;                    //Nombre del proveedor.
    static final int INT_TBL_DAT_DIR_PRV=17;                    //Direccion del proveedor
    static final int INT_TBL_DAT_COD_ITM=18;                    //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=19;                    //Código alterno del ítem.
    static final int INT_TBL_DAT_COD_ALT_DOS=20;                //Código alterno 2 del ítem.
    static final int INT_TBL_DAT_NOM_ITM=21;                    //Nombre del item.
    static final int INT_TBL_DAT_COD_BOD=22;                    //Código de la bodega.
    static final int INT_TBL_DAT_DEL_BOD=23;                    //Descripción de la bodega.
    static final int INT_TBL_DAT_CAN=24;                        //Cantidad.
    static final int INT_TBL_DAT_DEC_UNI=25;                    //Descripción corta de la unidad de medida.
    static final int INT_TBL_DAT_CON_PEN=26;                    //Confirmación: Pendiente.
    static final int INT_TBL_DAT_CON_PAR=27;                    //Confirmación: Parcial.
    static final int INT_TBL_DAT_CON_TOT=28;                    //Confirmación: Total.
    static final int INT_TBL_DAT_CAN_TOT_CON=29;                //Confirmación: Cantidad total confirmada.
    static final int INT_TBL_DAT_CAN_TOT_NUN_REC=30;            //Confirmación: Cantidad total nunca recibida.
    static final int INT_TBL_DAT_CAN_TOT_TRA=31;                //Confirmación: Cantidad total en tránsito.
    static final int INT_TBL_DAT_CAN_TOT_PEN=32;                //Cantidad total pendiente de confirmación.

    //Variables
    private ZafSelFec objSelFec;
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
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strNomItm, strCodAlt2;    //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    private int intEst=0;
    
    /** Crea una nueva instancia de la clase ZafCom31. */
    public ZafCom31(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
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
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panCodAltItmDesHas = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        chkMosItmPenCon = new javax.swing.JCheckBox();
        chkMosItmConPar = new javax.swing.JCheckBox();
        chkMosItmConTot = new javax.swing.JCheckBox();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 340));
        panFil.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

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

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panFil.add(panBod);
        panBod.setBounds(4, 4, 660, 92);

        bgrFil.add(optTod);
        optTod.setText("Todos los items");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 96, 400, 20);

        bgrFil.add(optFil);
        optFil.setSelected(true);
        optFil.setText("Sólo los items que cumplan el criterio seleccionado");
        optFil.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optFilItemStateChanged(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(4, 116, 400, 20);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(24, 136, 100, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(88, 136, 40, 20);

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
        txtCodAlt.setBounds(128, 136, 92, 20);

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
        txtCodAlt2.setBounds(220, 136, 67, 20);

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
        txtNomItm.setBounds(287, 136, 353, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(640, 136, 20, 20);

        panCodAltItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmDesHas.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panCodAltItmDesHas.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panCodAltItmDesHas.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panCodAltItmDesHas.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panCodAltItmDesHas.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil.add(panCodAltItmDesHas);
        panCodAltItmDesHas.setBounds(24, 156, 328, 52);

        panCodAltItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panCodAltItmTer.setLayout(null);

        lblCodAltItmTer.setText("Terminan con:");
        panCodAltItmTer.add(lblCodAltItmTer);
        lblCodAltItmTer.setBounds(12, 20, 100, 20);

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
        txtCodAltItmTer.setBounds(112, 20, 184, 20);

        panFil.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(356, 156, 308, 52);

        chkMosItmPenCon.setSelected(true);
        chkMosItmPenCon.setText("Mostrar los items que están pendientes de confirmar");
        chkMosItmPenCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmPenConActionPerformed(evt);
            }
        });
        panFil.add(chkMosItmPenCon);
        chkMosItmPenCon.setBounds(24, 280, 400, 20);

        chkMosItmConPar.setSelected(true);
        chkMosItmConPar.setText("Mostrar los items que están confirmados parcialmente");
        chkMosItmConPar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmConParActionPerformed(evt);
            }
        });
        panFil.add(chkMosItmConPar);
        chkMosItmConPar.setBounds(24, 300, 400, 20);

        chkMosItmConTot.setText("Mostrar los items que están confirmados totalmente");
        chkMosItmConTot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosItmConTotActionPerformed(evt);
            }
        });
        panFil.add(chkMosItmConTot);
        chkMosItmConTot.setBounds(24, 320, 400, 20);

        spnFil.setViewportView(panFil);

        tabFrm.addTab("Filtro", spnFil);

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
        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        if(intEst==1)
        {
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
        }
    }//GEN-LAST:event_formInternalFrameOpened

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
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void chkMosItmPenConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmPenConActionPerformed
        if (chkMosItmPenCon.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkMosItmPenConActionPerformed

    private void chkMosItmConParActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmConParActionPerformed
        if (chkMosItmConPar.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkMosItmConParActionPerformed

    private void chkMosItmConTotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosItmConTotActionPerformed
        if (chkMosItmConTot.isSelected())
            optFil.setSelected(true);
}//GEN-LAST:event_chkMosItmConTotActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected()) {
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
            objSelFec.setCheckBoxChecked(false);
            chkMosItmPenCon.setSelected(true);
            chkMosItmConPar.setSelected(true);
            chkMosItmConTot.setSelected(true);
        }
        else
            optFil.setSelected(true);
}//GEN-LAST:event_optTodItemStateChanged

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
}//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
}//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt)) {
            if (txtCodAlt.getText().equals("")) {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            } else {
                mostrarVenConItm(1);
            }
        } else
            txtCodAlt.setText(strCodAlt);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
}//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
}//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm)) {
            if (txtNomItm.getText().equals("")) {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            } else {
                mostrarVenConItm(3);
            }
        } else
            txtNomItm.setText(strNomItm);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodItm.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butItmActionPerformed

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

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
}//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            //Por el momento hacer que sólo funcione para los tipos de documentos que corresponden a "Transferencias de inventario".
            switch (Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_TIP_DOC).toString()))
            {
                case 46:  //TRAINV: Transferencias de inventario.
                case 53:  //TRINAJ: Transferencias de inventario.
                case 58:  //TRINDI: Transferencias de inventario.
                case 60:  //TRINNO: Transferencias de inventario.
                case 66:  //TRINCA: Transferencias de inventario.
                case 85:  //TRINMA: Transferencias de inventario.
                case 96:  //TRIADI: Transferencias de inventario.
                case 97:  //TRIACQ: Transferencias de inventario.
                case 98:  //TRIACM: Transferencias de inventario.
                case 150: //TRINEA: Transferencias de inventario.
                case 151: //TRINIA: Transferencias de inventario.
                case 153: //TRINAU: Transferencias de inventario.
                case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                    evt.consume();
                    abrirFrm();
                    break;
                default:
                    switch (Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_TIP_DOC_REL).toString()))
                    {
                        case 46:
                        case 153:
                        case 172:
                           abrirFrm();
                           break;
                        default:
                             mostrarMsgInf("Esta opción sólo está habilitada para \"Transferencias de inventario\".");
                             break;
                    }
                    break;
            }
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            //Por el momento hacer que sólo funcione para los tipos de documentos que corresponden a "Transferencias de inventario".
            switch (Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_TIP_DOC).toString()))
            {
                case 46:  //TRAINV: Transferencias de inventario.
                case 53:  //TRINAJ: Transferencias de inventario.
                case 58:  //TRINDI: Transferencias de inventario.
                case 60:  //TRINNO: Transferencias de inventario.
                case 66:  //TRINCA: Transferencias de inventario.
                case 85:  //TRINMA: Transferencias de inventario.
                case 96:  //TRIADI: Transferencias de inventario.
                case 97:  //TRIACQ: Transferencias de inventario.
                case 98:  //TRIACM: Transferencias de inventario.
                case 150: //TRINEA: Transferencias de inventario.
                case 151: //TRINIA: Transferencias de inventario.
                case 153: //TRINAU: Transferencias de inventario.
                case 172: //TRINRB: Transferencias de inventario (Reposición desde otras bodegas).
                    abrirFrm();
                    break;
                default:
                    switch (Integer.parseInt(objTblMod.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_TIP_DOC_REL).toString()))
                    {
                        case 46:
                        case 153:
                        case 172:
                           abrirFrm();
                           break;
                        default:
                             mostrarMsgInf("Esta opción sólo está habilitada para \"Transferencias de inventario\".");
                             break;
                    }
                    break;
            }
        }
    }//GEN-LAST:event_tblDatMouseClicked

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtCodAlt2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAlt2FocusGained
        strCodAlt2 = txtCodAlt2.getText();
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
        {
            txtCodAlt2.setText(strCodAlt2);
        }

        if (txtCodAlt2.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void optFilItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optFilItemStateChanged
        if (optFil.isSelected()) {   
            optTod.setSelected(false);
        }             
        else
            optTod.setSelected(true);
    }//GEN-LAST:event_optFilItemStateChanged

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
    private javax.swing.JCheckBox chkMosItmConPar;
    private javax.swing.JCheckBox chkMosItmConTot;
    private javax.swing.JCheckBox chkMosItmPenCon;
    private javax.swing.JPanel jPanel6;
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
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmDesHas;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
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
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxChecked(false);
            panFil.add(objSelFec);
            objSelFec.setBounds(24, 208, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.8 ");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            cargarBod();
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
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCab);
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
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblBod);
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_NOM_EMP, tblBod);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
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
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
                    if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
                    {
                        //Mostrar columnas.
                        //objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
                    }
                    else
                    {
                        //Ocultar columnas.
                        //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
            vecCab=new Vector(33);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_SEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL,"Cód.Emp.Rel.");
            vecCab.add(INT_TBL_DAT_COD_LOC_REL,"Cód.Loc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_REL,"Cód.Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_DOC_REL,"Cód.Doc.Rel.");            
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");         
                              
            switch (objParSis.getCodigoMenu())
            {
                case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                    vecCab.add(INT_TBL_DAT_COD_PRV,"Cód.Prv.");
                    vecCab.add(INT_TBL_DAT_NOM_PRV,"Proveedor");
                    vecCab.add(INT_TBL_DAT_DIR_PRV,"Dir.Prv.");
                    break;
                case 2184: //Listado de egresos físicos de mercadería de Bodega...
                    vecCab.add(INT_TBL_DAT_COD_PRV,"Cód.Cli.");
                    vecCab.add(INT_TBL_DAT_NOM_PRV,"Cliente");
                    vecCab.add(INT_TBL_DAT_DIR_PRV,"Dir.Cli.");
                    break;
                default:
                    vecCab.add(INT_TBL_DAT_COD_PRV,"");
                    vecCab.add(INT_TBL_DAT_NOM_PRV,"");
                    vecCab.add(INT_TBL_DAT_DIR_PRV,"");
                    break;
            }
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_DEL_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_DEC_UNI,"Unidad");
            vecCab.add(INT_TBL_DAT_CON_PEN,"Pendiente");
            vecCab.add(INT_TBL_DAT_CON_PAR,"Parcial");
            vecCab.add(INT_TBL_DAT_CON_TOT,"Total");
            vecCab.add(INT_TBL_DAT_CAN_TOT_CON,"Can.Tot.Con.");
            switch (objParSis.getCodigoMenu())
            {
                case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                    vecCab.add(INT_TBL_DAT_CAN_TOT_NUN_REC,"Can.Tot.Nun.Rec.");
                    break;
                case 2184: //Listado de egresos físicos de mercadería de Bodega...
                    vecCab.add(INT_TBL_DAT_CAN_TOT_NUN_REC,"Can.Tot.Nun.Env.");
                    break;
                default:
                    vecCab.add(INT_TBL_DAT_CAN_TOT_NUN_REC,"");
                    break;
            }
            vecCab.add(INT_TBL_DAT_CAN_TOT_TRA,"Can.Tot.Tra.");
            vecCab.add(INT_TBL_DAT_CAN_TOT_PEN,"Can.Tot.Pen.");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_SEG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_PRV).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_PRV).setPreferredWidth(112);
            tcmAux.getColumn(INT_TBL_DAT_DIR_PRV).setPreferredWidth(112);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(66);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(112);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_BOD).setPreferredWidth(112);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_CON_PEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_PAR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CON_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_CON).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_NUN_REC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_TRA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_PEN).setPreferredWidth(80);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_REL, tblDat); 
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
            //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
            if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
            {
                //Mostrar columnas.
                //objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
            }
            else
            {
                //Ocultar columnas.
                //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
            }
            switch (objParSis.getCodigoMenu())
            {
                case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DIR_PRV, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_TRA, tblDat);
                    break;
                case 2184: //Listado de egresos físicos de mercadería de Bodega...
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DIR_PRV, tblDat);
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CAN_TOT_TRA, tblDat);
                    break;
                default:
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
            //Agrupar las columnas.
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpDatIng;
            switch (objParSis.getCodigoMenu())
            {
                case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                    objTblHeaColGrpDatIng =new ZafTblHeaColGrp("Datos del ingreso de mercadería");
                    break;
                case 2184: //Listado de egresos físicos de mercadería de Bodega...
                    objTblHeaColGrpDatIng=new ZafTblHeaColGrp("Datos del egreso de mercadería");
                    break;
                default:
                    objTblHeaColGrpDatIng=new ZafTblHeaColGrp("");
                    break;
            }
            objTblHeaColGrpDatIng.setHeight(16);
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_NOM_LOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_FEC_DOC));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_PRV));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_NOM_PRV));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_DIR_PRV));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_ITM));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_ALT));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_ALT_DOS));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_NOM_ITM));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_COD_BOD));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_DEL_BOD));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_CAN));
            objTblHeaColGrpDatIng.add(tcmAux.getColumn(INT_TBL_DAT_DEC_UNI));
            ZafTblHeaColGrp objTblHeaColGrpDatCon=new ZafTblHeaColGrp("Datos de la confirmación");
            objTblHeaColGrpDatCon.setHeight(16);
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CON_PEN));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CON_PAR));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CON_TOT));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_CON));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_NUN_REC));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_TRA));
            objTblHeaColGrpDatCon.add(tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_PEN));
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDatIng);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDatCon);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_PRV).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_DEC_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CON_PEN).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CON_PAR).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_CON_TOT).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_CON).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_NUN_REC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_TRA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_TOT_PEN).setCellRenderer(objTblCelRenLbl);
                    
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                    else
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                        strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND a3.st_reg IN ('A','P')";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                }
                else
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                    else
                    {
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" INNER JOIN (";
                        strSQL+=" SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod";
                        strSQL+=" FROM tbr_bodLocPrgUsr AS b1";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)";
                        strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND b1.co_mnu=" + objParSis.getCodigoMenu();
                        strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario();
                        strSQL+=" AND b1.st_reg IN ('A','P')";
                        strSQL+=" GROUP BY b2.co_empGrp, b2.co_bodGrp";
                        strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                        rst=stm.executeQuery(strSQL);
                    }
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,Boolean.TRUE);
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intNumFilTblBod, i, j;
        BigDecimal bgdCan, bgdCanCon, bgdCanNunRec, bgdCanTra;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Obtener las bodegas a consultar.
                intNumFilTblBod=objTblModBod.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblBod; j++)
                {
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                    {
                        if (i==0)
                            strAux+=" (a.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                        else
                            strAux+=" OR (a.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                        i++;
                    }
                }
                strConSQL+=" AND (" + strAux + ")";
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                {
                    strConSQL+=" AND ((LOWER(a.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                }
                if (txtCodAltItmTer.getText().length()>0)
                {
                    strConSQL+=getConSQLAdiCamTer("a.tx_codAlt", txtCodAltItmTer.getText());
                }
                
                //Rango de fecha
                if (objSelFec.isCheckBoxChecked())
                {
                    switch (objSelFec.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                //Estados de item.
                strAux="";
                if (chkMosItmPenCon.isSelected())
                {
                    switch (objParSis.getCodigoMenu())
                    {
                        case 2175: //Listado de ingresos físicos de mercadería de Bodega...
                            strAux+=" ( a.nd_canCon+a.nd_canNunRec=0)";             
                            break;
                        case 2184: //Listado de egresos físicos de mercadería de Bodega...
                            strAux+=" ( a.nd_canCon+a.nd_canNunRec+a.nd_CanTra=0)";
                            break;
                        default:
                            break;
                    }
                }
                if (chkMosItmConPar.isSelected())
                {
                    switch (objParSis.getCodigoMenu())
                    {
                        case 2175: //Listado de ingresos físicos de mercadería de Bodega...
                            strAux+=" OR ((a.nd_canCon+a.nd_canNunRec)<>0 AND (a.nd_canCon+a.nd_canNunRec)<ABS(a.nd_can))";
                            break;
                        case 2184: //Listado de egresos físicos de mercadería de Bodega...
                            strAux+=" OR ((a.nd_canCon+a.nd_canNunRec+a.nd_CanTra)<>0 AND (a.nd_canCon+a.nd_canNunRec+a.nd_CanTra)<ABS(a.nd_can))";
                            break;
                        default:
                            break;
                    }
                }
                if (chkMosItmConTot.isSelected())
                {
                    switch (objParSis.getCodigoMenu())
                    {
                        case 2175: //Listado de ingresos físicos de mercadería de Bodega...
                            strAux+=" OR ((a.nd_canCon+a.nd_canNunRec)=ABS(a.nd_can))";
                            break;
                        case 2184: //Listado de egresos físicos de mercadería de Bodega...
                            strAux+=" OR ((a.nd_canCon+a.nd_canNunRec+a.nd_CanTra)=ABS(a.nd_can))";
                            break;
                        default:
                            break;
                    }
                }
                if (!strAux.equals(""))
                {
                    strConSQL+=" AND (" + (strAux.startsWith(" OR")?strAux.substring(4):strAux) + ")";
                }
                switch (objParSis.getCodigoMenu())
                {
                    case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+=" SELECT a1.co_Seg, a.* , (a.nd_Can - (a.nd_CanCon + a.nd_canNunRec)) as nd_CanPen";
                        strSQL+=" FROM (";
                        strSQL+="    SELECT a1.ne_secEmp, a1.co_emp, a1.co_loc, a3.tx_nom AS tx_nomLoc, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, '' AS tx_dirCli, a1.st_conInv,";
                        strSQL+="           a2.co_Reg, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, a5.co_empGrp, a5.co_bodGrp, a2.co_bod, a6.tx_nom AS tx_nomBod, a2.nd_can,  a2.nd_canCon, a2.nd_canNunRec, a2.nd_canIngBod,";
                        strSQL+="           '' as co_empRel, '' as co_locRel, '' as co_tipDocRel, '' as co_docRel, 0 as nd_canTra";                       
                        strSQL+="    FROM tbm_cabMovInv AS a1";
                        strSQL+="    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+="    INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)";
                        strSQL+="    INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                        strSQL+="    INNER JOIN tbr_bodEmpBodGrp AS a5 ON (a1.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod) ";
                        strSQL+="    INNER JOIN tbm_bod AS a6 ON (a5.co_empGrp=a6.co_emp AND a5.co_bodGrp=a6.co_bod)";
                    
                        if (txtCodItm.getText().length()>0)
                        {
                            strSQL+="    INNER JOIN tbm_equInv AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_itm=a7.co_itm)";
                            strSQL+="    WHERE a7.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() +")";
                            strSQL+="    AND a1.st_reg='A'";
                        }
                        else
                        {
                            strSQL+="    WHERE a1.st_reg='A'";
                        }
                        strSQL+="    AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                        strSQL+="    AND a2.st_merIngEgrFisBod='S' AND a2.nd_can>0 AND a1.tx_tipMov IS NOT NULL ";
                        strSQL+=" ) as a ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as a1 ";
                        strSQL+=" ON (a1.co_empRelCabMovInv=a.co_emp AND a1.co_locRelCabMovInv=a.co_loc AND a1.co_tipDocRelCabMovInv=a.co_tipDoc AND a1.co_DocRelCabMovInv=a.co_Doc )";
                        strSQL+=" WHERE a.st_conInv not in ('F') ";
                        strSQL+=strConSQL;
                        strSQL+=" ORDER BY a.tx_codAlt, a.fe_doc, a.ne_secEmp, a.co_reg";
                        break;
                    case 2184: //Listado de egresos físicos de mercadería de Bodega...
                        //Armar la sentencia SQL.
                        strSQL ="";
                        strSQL+=" SELECT a1.co_Seg, a.* , (a.nd_Can - (a.nd_CanCon + a.nd_canNunRec + a.nd_canTra)) as nd_CanPen";
                        strSQL+=" FROM (";
                        strSQL+="    SELECT a1.co_emp as co_empRel, a1.co_loc as co_locRel, a1.co_tipDoc as co_tipDocRel,  a1.co_doc as co_DocRel, ";
                        strSQL+="           a4.co_emp,  a4.co_loc, a5.tx_nom AS tx_nomLoc, a4.co_tipDoc, a6.tx_desCor, a6.tx_desLar, a4.co_Doc,  ";
                        strSQL+="           a4.ne_numOrdDes AS ne_numDoc, a4.fe_doc, a4.co_cliDes AS co_cli,a1.st_Reg as st_RegMov, a7.co_empGrp, a7.co_bodGrp,  ";
                        strSQL+="           a4.co_ptoPar AS co_bod, a8.tx_nom AS tx_nomBod, a4.tx_nomCliDes AS tx_nomCli, a4.tx_dirCliDes AS tx_dirCli,";
                        strSQL+="           a2.co_itm, a2.co_reg, a2.tx_codAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.tx_uniMed, ABS(a2.nd_can) AS nd_can, ";
                        strSQL+="           CASE WHEN a3.nd_CanCon = 0 THEN abs(a2.nd_CanCon) ELSE abs(a3.nd_CanCon) END AS nd_CanCon,  ";
                        strSQL+="           CASE WHEN a2.nd_canNunRec = 0 THEN abs(a3.nd_CanNunRec) ELSE abs(a2.nd_canNunRec) END AS nd_canNunRec,";
                        strSQL+="           CASE WHEN a2.nd_canTra IS NULL THEN 0 ELSE abs(a2.nd_canTra) END as nd_canTra, ";       
                        strSQL+="           CASE WHEN a4.st_coninv in ('P','E') THEN case when  a1.st_conInv  is null then 'P' else  a1.st_conInv end ELSE a4.st_coninv END AS st_coninv";
                        strSQL+="    FROM tbm_cabMovInv as a1 ";
                        strSQL+="    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+="    INNER JOIN tbm_detGuiRem as a3 ON (a1.co_emp=a3.co_empRel AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_doc=a3.co_docRel AND a2.co_Reg=a3.co_regRel)";
                        strSQL+="    INNER JOIN tbm_cabGuiRem as a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc)";
                        strSQL+="    INNER JOIN tbm_loc AS a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc) ";
                        strSQL+="    INNER JOIN tbm_cabTipDoc AS a6 ON (a4.co_emp=a6.co_emp AND a4.co_loc=a6.co_loc AND a4.co_tipDoc=a6.co_tipDoc) ";   
                        strSQL+="    INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a4.co_emp=a7.co_emp AND a4.co_ptoPar=a7.co_bod) ";
                        strSQL+="    INNER JOIN tbm_bod AS a8 ON (a7.co_empGrp=a8.co_emp AND a7.co_bodGrp=a8.co_bod)";
                        if (txtCodItm.getText().length()>0)
                        {
                            strSQL+=" INNER JOIN tbm_equInv AS a9 ON (a2.co_emp=a9.co_emp AND a2.co_itm=a9.co_itm)";
                            strSQL+=" WHERE a9.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() +")";
                            strSQL+=" AND a4.st_reg='A'";
                        }
                        else
                        {
                            strSQL+=" WHERE a4.st_reg='A' ";
                        }    
                        strSQL+="    AND a2.nd_Can<0 AND a1.tx_tipMov IS NOT NULL AND a2.st_merIngEgrFisBod='S' AND a1.st_reg='A' ";
                        strSQL+=" ) as a ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as a1 ";
                        strSQL+=" ON (a1.co_empRelCabGuiRem=a.co_emp AND a1.co_locRelCabGuiRem=a.co_loc AND a1.co_tipDocRelCabGuiRem=a.co_tipDoc AND a1.co_DocRelCabGuiRem=a.co_Doc )";
                        strSQL+=" WHERE a.st_conInv not in ('F') ";
                        strSQL+=strConSQL;
                        strSQL+=" ORDER BY a.co_bodGrp, a.ne_numDoc, a.co_reg, a.tx_codAlt, a.fe_doc, a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc";
                        break;
                    default:
                        break;
                }
                //System.out.println("cargarDetReg: "+strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_SEG,rst.getString("co_Seg"));
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("tx_nomLoc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_REL,rst.getString("co_empRel"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_REL,rst.getString("co_locRel"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_REL,rst.getString("co_tipDocRel"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_REL,rst.getString("co_DocRel"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_PRV,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_PRV,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_DIR_PRV,rst.getString("tx_dircli"));
                        vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_DOS,rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_COD_BOD,rst.getString("co_bod"));
                        vecReg.add(INT_TBL_DAT_DEL_BOD,rst.getString("tx_nomBod"));
                        
                        bgdCan=rst.getBigDecimal("nd_can");
                        bgdCanCon=rst.getBigDecimal("nd_canCon");
                        bgdCanNunRec=rst.getBigDecimal("nd_canNunRec");
                        bgdCanTra=rst.getBigDecimal("nd_canTra");
                        
                        vecReg.add(INT_TBL_DAT_CAN,bgdCan);
                        vecReg.add(INT_TBL_DAT_DEC_UNI,rst.getString("tx_uniMed"));
                        
                        if ((bgdCanCon.add(bgdCanNunRec)).add(bgdCanTra).compareTo(BigDecimal.ZERO)==0)
                        {
                            vecReg.add(INT_TBL_DAT_CON_PEN, Boolean.TRUE);
                            vecReg.add(INT_TBL_DAT_CON_PAR,null);
                            vecReg.add(INT_TBL_DAT_CON_TOT,null);
                        }
                        else if ( (bgdCanCon.add(bgdCanNunRec)).add(bgdCanTra).compareTo(BigDecimal.ZERO)!=0 && (bgdCanCon.add(bgdCanNunRec)).add(bgdCanTra).compareTo(bgdCan)==-1)
                        {
                            vecReg.add(INT_TBL_DAT_CON_PEN,null);
                            vecReg.add(INT_TBL_DAT_CON_PAR, Boolean.TRUE);
                            vecReg.add(INT_TBL_DAT_CON_TOT,null);
                        }
                        else if ((bgdCanCon.add(bgdCanNunRec)).add(bgdCanTra).compareTo(bgdCan)==0)
                        {
                            vecReg.add(INT_TBL_DAT_CON_PEN,null);
                            vecReg.add(INT_TBL_DAT_CON_PAR,null);
                            vecReg.add(INT_TBL_DAT_CON_TOT, Boolean.TRUE);
                        }
                        else
                        {
                            vecReg.add(INT_TBL_DAT_CON_PEN,null);
                            vecReg.add(INT_TBL_DAT_CON_PAR,null);
                            vecReg.add(INT_TBL_DAT_CON_TOT,null);
                        }
                        vecReg.add(INT_TBL_DAT_CAN_TOT_CON, bgdCanCon);
                        vecReg.add(INT_TBL_DAT_CAN_TOT_NUN_REC, bgdCanNunRec);
                        vecReg.add(INT_TBL_DAT_CAN_TOT_TRA, bgdCanTra );
                        vecReg.add(INT_TBL_DAT_CAN_TOT_PEN, rst.getBigDecimal("nd_canPen"));
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar que esté seleccionada al menos una bodega.
        if (!objTblModBod.isCheckedAnyRow(INT_TBL_BOD_CHK))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBod.requestFocus();
            return false;
        }
        //Validar que esté seleccionada al menos una casilla de verificación.
        if (!chkMosItmPenCon.isSelected() && !chkMosItmConPar.isSelected() && !chkMosItmConTot.isSelected())
        {
            mostrarMsgInf("<HTML>Debe seleccionar al menos un estado de confirmación.<BR>Seleccione un estado y vuelva a intentarlo.</HTML>");
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
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
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
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
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Alterno2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
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
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
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
                //Mostrar las columnas "Cód.Bod." y "Bodega" sólo si hay seleccionada más de una bodega.
                if (objTblModBod.getRowCountChecked(INT_TBL_BOD_CHK)>1)
                {
                    //Mostrar columnas.
                    //objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                    objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
                }
                else
                {
                    //Ocultar columnas.
                    //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DEL_BOD, tblDat);
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

    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            invocarClase("Compras.ZafCom20.ZafCom20");
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean invocarClase(String clase)
    {
        int intFilSel;
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objParCla[]=new Class[5];
            objParCla[0]=objParSis.getClass();
            objParCla[1]=new Integer(0).getClass();
            objParCla[2]=new Integer(0).getClass();
            objParCla[3]=new Integer(0).getClass();
            objParCla[4]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objParCla);
            intFilSel=tblDat.getSelectedRow();
            //Inicializar el constructor que se obtuvo.
            Object objValCla[]=new Object[5];
            objValCla[0]=objParSis;
            switch (objParSis.getCodigoMenu())
            {
                case 2175: //Listado de ingresos físicos de mercadería a Bodega...
                    objValCla[1]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_EMP).toString());
                    objValCla[2]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_LOC).toString());
                    objValCla[3]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_TIP_DOC).toString());
                    objValCla[4]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_DOC).toString());
                    break;
                case 2184: //Listado de egresos físicos de mercadería de Bodega...
                    objValCla[1]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_EMP_REL).toString());
                    objValCla[2]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_LOC_REL).toString());
                    objValCla[3]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_TIP_DOC_REL).toString());
                    objValCla[4]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_DOC_REL).toString());
                default:
                    break;
            }
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objValCla);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.show();
        }
        catch (ClassNotFoundException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (NoSuchMethodException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (SecurityException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (InstantiationException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalAccessException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (IllegalArgumentException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (java.lang.reflect.InvocationTargetException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
                case INT_TBL_DAT_COD_SEG:
                    strMsg="Código de seguimiento";
                    break;
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
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
                case INT_TBL_DAT_COD_PRV:
                    strMsg="Código del " + (objParSis.getCodigoMenu()==2175?"proveedor":"cliente");
                    break;
                case INT_TBL_DAT_NOM_PRV:
                    strMsg="Nombre del " + (objParSis.getCodigoMenu()==2175?"proveedor":"cliente");
                    break;
                case INT_TBL_DAT_DIR_PRV:
                    strMsg="Direccion del " + (objParSis.getCodigoMenu()==2175?"proveedor":"cliente"); 
                    break;
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del item";
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
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código de la bodega (empresa)";
                    break;
                case INT_TBL_DAT_DEL_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                case INT_TBL_DAT_CON_PEN:
                    strMsg="Confirmación pendiente";
                    break;
                case INT_TBL_DAT_CON_PAR:
                    strMsg="Confirmación parcial";
                    break;
                case INT_TBL_DAT_CON_TOT:
                    strMsg="Confirmación total";
                    break;
                case INT_TBL_DAT_CAN_TOT_CON:
                    strMsg="Cantidad total confirmada";
                    break;
                case INT_TBL_DAT_CAN_TOT_NUN_REC:
                    strMsg="Cantidad total nunca " + (objParSis.getCodigoMenu()==2175?"recibida":"enviada"); 
                    break;
                case INT_TBL_DAT_CAN_TOT_TRA:
                    strMsg="Cantidad total en tránsito";
                    break;
                case INT_TBL_DAT_CAN_TOT_PEN:
                    strMsg="Cantidad total pendiente";
                    break;
                default:
                    strMsg=null;
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
    
}