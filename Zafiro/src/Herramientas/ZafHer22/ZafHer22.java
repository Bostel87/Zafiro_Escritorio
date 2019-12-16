/*
 * ZafHer22.java
 *
 * Created on 18 de julio de 2007, 12:05 PM
 */
package Herramientas.ZafHer22;
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
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafHer22 extends javax.swing.JInternalFrame 
{
    /*Simbología:
     *  CON: Controles
     *  DAT: Datos
     *  DET: Detalle del registro seleccionado
     **/
    //Constantes: Columnas del JTable:
    static final int INT_TBL_CON_LIN=0;                         //Línea.
    static final int INT_TBL_CON_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_CON_NOM=2;                         //Nombre del control.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_CLI=2;                     //Código del cliente.
    static final int INT_TBL_DAT_IDE_CLI=3;                     //Identificación del cliente.
    static final int INT_TBL_DAT_NOM_CLI=4;                     //Nombre del cliente.
    static final int INT_TBL_DAT_ABO_VEN=5;                     //Abono en el módulo de "Ventas".
    static final int INT_TBL_DAT_ABO_CXC=6;                     //Abono en el módulo de "Cuentas por cobrar".
    static final int INT_TBL_DAT_DIF=7;                         //Diferencia.
    
    static final int INT_TBL_DET_LIN=0;                         //Línea.
    static final int INT_TBL_DET_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_DET_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_DET_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DET_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DET_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_DET_COD_REG=6;                     //Código del registro (Sistema).
    static final int INT_TBL_DET_NUM_DOC=7;                     //Número del documento.
    static final int INT_TBL_DET_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_DET_DIA_CRE=9;                     //Días de crédito.
    static final int INT_TBL_DET_FEC_VEN=10;                    //Fecha de vencimiento.
    static final int INT_TBL_DET_POR_RET=11;                    //Porcentaje de retención.
    static final int INT_TBL_DET_ABO_VEN=12;                    //Abono en el módulo de "Ventas".
    static final int INT_TBL_DET_ABO_CXC=13;                    //Abono en el módulo de "Cuentas por cobrar".
    static final int INT_TBL_DET_DIF=14;                        //Diferencia.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLisCon;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModDet;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ResultSetMetaData rmd;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatCre, vecCabCre;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.

    int intNumTotCon;
    private String strTitCon[];
    private String strSQLCon[];
    private javax.swing.JPanel panRptCon[];
    private javax.swing.JLabel lblTitCon[];
    private javax.swing.JScrollPane spnDatCon[];
    private javax.swing.JTable tblDatCon[];
    private ZafTblMod objTblModCon[];
    private javax.swing.JScrollPane spnSenSQLCon[];
    private javax.swing.JTextArea txaSenSQLCon[];
    private boolean blnMarTodChkTblCon=true;                    //Marcar todas las casillas de verificación del JTable de controles.
    
    /** Crea una nueva instancia de la clase ZafHer22. */
    public ZafHer22(ZafParSis obj) 
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
    private void initComponents() {//GEN-BEGIN:initComponents
        bgrFil = new javax.swing.ButtonGroup();
        bgrFilAbo = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        chkMosCliSalCer = new javax.swing.JCheckBox();
        panCon = new javax.swing.JPanel();
        spnCon = new javax.swing.JScrollPane();
        tblCon = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        sppRpt = new javax.swing.JSplitPane();
        panRptReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panRptMovReg = new javax.swing.JPanel();
        panRptMovRegFil = new javax.swing.JPanel();
        chkMosMovReg = new javax.swing.JCheckBox();
        optTodAbo = new javax.swing.JRadioButton();
        optAboDif = new javax.swing.JRadioButton();
        panMovReg = new javax.swing.JPanel();
        spnMovReg = new javax.swing.JScrollPane();
        tblMovReg = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabFrmStateChanged(evt);
            }
        });

        panFil.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        bgrFil.add(optTod);
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });

        panFil.add(optTod);
        optTod.setBounds(4, 96, 400, 20);

        optFil.setText("S\u00f3lo los clientes que cumplan el criterio seleccionado");
        bgrFil.add(optFil);
        panFil.add(optFil);
        optFil.setBounds(4, 116, 400, 20);

        panNomCli.setLayout(null);

        panNomCli.setBorder(new javax.swing.border.TitledBorder("Nombre de cliente"));
        lblNomCliDes.setText("Desde:");
        panNomCli.add(lblNomCliDes);
        lblNomCliDes.setBounds(12, 20, 44, 20);

        txtNomCliDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliDesFocusLost(evt);
            }
        });

        panNomCli.add(txtNomCliDes);
        txtNomCliDes.setBounds(56, 20, 268, 20);

        lblNomCliHas.setText("Hasta:");
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(336, 20, 44, 20);

        txtNomCliHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliHasFocusLost(evt);
            }
        });

        panNomCli.add(txtNomCliHas);
        txtNomCliHas.setBounds(380, 20, 268, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(24, 156, 660, 52);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(24, 136, 120, 20);

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
        txtCodCli.setBounds(144, 136, 56, 20);

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
        txtDesLarCli.setBounds(200, 136, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });

        panFil.add(butCli);
        butCli.setBounds(660, 136, 20, 20);

        chkMosCliSalCer.setText("Mostrar los clientes que tienen saldo cero");
        panFil.add(chkMosCliSalCer);
        chkMosCliSalCer.setBounds(4, 208, 400, 20);

        panCon.setLayout(new java.awt.BorderLayout());

        panCon.setBorder(new javax.swing.border.TitledBorder("Listado de controles"));
        panCon.setPreferredSize(new java.awt.Dimension(10, 92));
        tblCon.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCon.setViewportView(tblCon);

        panCon.add(spnCon, java.awt.BorderLayout.CENTER);

        panFil.add(panCon);
        panCon.setBounds(0, 0, 688, 92);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        sppRpt.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppRpt.setResizeWeight(0.5);
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

        panRptMovRegFil.setLayout(null);

        panRptMovRegFil.setPreferredSize(new java.awt.Dimension(0, 60));
        chkMosMovReg.setText("Mostrar los abonos del cliente seleccionado");
        chkMosMovReg.setPreferredSize(new java.awt.Dimension(303, 20));
        chkMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosMovRegActionPerformed(evt);
            }
        });

        panRptMovRegFil.add(chkMosMovReg);
        chkMosMovReg.setBounds(0, 0, 400, 20);

        optTodAbo.setSelected(true);
        optTodAbo.setText("Mostrar todos los abonos");
        bgrFilAbo.add(optTodAbo);
        optTodAbo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodAboActionPerformed(evt);
            }
        });

        panRptMovRegFil.add(optTodAbo);
        optTodAbo.setBounds(20, 20, 400, 20);

        optAboDif.setText("Mostrar s\u00f3lo los abonos donde hay diferencias");
        bgrFilAbo.add(optAboDif);
        optAboDif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optAboDifActionPerformed(evt);
            }
        });

        panRptMovRegFil.add(optAboDif);
        optAboDif.setBounds(20, 40, 400, 20);

        panRptMovReg.add(panRptMovRegFil, java.awt.BorderLayout.NORTH);

        panMovReg.setLayout(new java.awt.BorderLayout());

        panMovReg.setBorder(new javax.swing.border.TitledBorder("Abonos realizados por el cliente"));
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

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void tabFrmStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabFrmStateChanged
        int intTabSel;
        intTabSel=tabFrm.getSelectedIndex();
        if (intTabSel!=-1 && tblDatCon!=null)
        {
            switch (intTabSel)
            {
                case 0:
                    lblMsgSis.setText("Listo");
                    break;
                case 1:
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                    break;
                default:
                    lblMsgSis.setText("Se encontraron " + tblDatCon[intTabSel-2].getRowCount() + " registros.");
            }
        }
    }//GEN-LAST:event_tabFrmStateChanged

    private void chkMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosMovRegActionPerformed
        if (chkMosMovReg.isSelected())
            cargarMovReg();
        else
            objTblModDet.removeAllRows();
    }//GEN-LAST:event_chkMosMovRegActionPerformed

    private void optAboDifActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optAboDifActionPerformed
        chkMosMovReg.setSelected(true);
        cargarMovReg();
    }//GEN-LAST:event_optAboDifActionPerformed

    private void optTodAboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodAboActionPerformed
        chkMosMovReg.setSelected(true);
        cargarMovReg();
    }//GEN-LAST:event_optTodAboActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

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
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

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

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
    }//GEN-LAST:event_txtNomCliHasFocusLost

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0)
        {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
    }//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
    }//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
    }//GEN-LAST:event_txtNomCliDesFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
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

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrFilAbo;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkMosCliSalCer;
    private javax.swing.JCheckBox chkMosMovReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optAboDif;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JRadioButton optTodAbo;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panMovReg;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptMovReg;
    private javax.swing.JPanel panRptMovRegFil;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnCon;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnMovReg;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JSplitPane sppRpt;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCon;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblMovReg;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        int i;
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.8");
            lblTit.setText(strAux);
            //Configurar objetos.
            intNumTotCon=14;
            strTitCon=new String[intNumTotCon];
            strTitCon[0]="Query para obtener los registros que tienen el signo incorrecto en \"tbm_pagMovInv\".";
            strTitCon[1]="Query para obtener los registros que tienen el signo incorrecto en \"tbm_detPag\".";
            strTitCon[2]="Query para obtener los documentos en \"tbm_detPag\" que están descuadrados.";
            strTitCon[3]="Query para obtener los documentos donde no coincide lo que dice \"tbm_cabMovInv\" con lo que dice \"tbm_pagMovInv\" para el módulo de Ventas.";
            strTitCon[4]="Query para obtener los documentos donde no coincide lo que dice \"tbm_cabMovInv\" con lo que dice \"tbm_pagMovInv\" para el módulo de CxC.";
            strTitCon[5]="Query para obtener los registros en \"tbm_pagMovInv\" marcados con \"F ó I\" que tienen abono.";
            strTitCon[6]="Query para obtener los documentos donde no coincide lo que dice \"tbm_cabPag\" con lo que dice \"tbm_detPag\".";
            strTitCon[7]="Query para obtener los registros en \"tbm_detPag\" que apuntan a registros marcados con \"I ó F\" en \"tbm_pagMovInv\".";
            strTitCon[8]="Query para obtener los documentos en \"tbm_cabMovInv\" donde la fecha no coincide con lo que dice \"tbm_cabDia\".";
            strTitCon[9]="Query para obtener los documentos en \"tbm_cabPag\" donde la fecha no coincide con lo que dice \"tbm_cabDia\".";
            strTitCon[10]="Query para obtener los asientos de diario descuadrados.";
            strTitCon[11]="Query para obtener los registros en \"tbm_detPag\" que apuntan a registros que no existen en \"tbm_pagMovInv\".";
            strTitCon[12]="Query para obtener los documentos en \"tbm_detPag\" que no cuadran con lo que dice \"tbm_detDia\".";
            strTitCon[13]="Query para obtener los registros en \"tbm_pagMovInv\" que no cuadran con lo que dice \"tbm_detPag\".";
            strSQLCon=new String[intNumTotCon];
            strSQLCon[0]="";
            strSQLCon[0]+=" SELECT b1.*";
            strSQLCon[0]+=" FROM";
            strSQLCon[0]+=" (";
            strSQLCon[0]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.mo_pag, a2.tx_natDoc";
            strSQLCon[0]+=" , (CASE WHEN a2.tx_natDoc='I' THEN ABS(a1.mo_pag) ELSE -ABS(a1.mo_pag) END) AS mo_pagSigCor";
            strSQLCon[0]+=" FROM tbm_pagMovInv AS a1";
            strSQLCon[0]+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
            strSQLCon[0]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[0]+=" AND a2.tx_natDoc IN ('I', 'E')";
            strSQLCon[0]+=" ) AS b1";
            strSQLCon[0]+=" WHERE b1.mo_pag<>b1.mo_pagSigCor";
            strSQLCon[0]+=" ORDER BY b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
            
            strSQLCon[1]="";
            strSQLCon[1]+=" SELECT b1.*";
            strSQLCon[1]+=" FROM";
            strSQLCon[1]+=" (";
            strSQLCon[1]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a2.nd_abo, a3.tx_natDoc";
            strSQLCon[1]+=" , (CASE WHEN a3.tx_natDoc='I' THEN ABS(a2.nd_abo) ELSE -ABS(a2.nd_abo) END) AS nd_aboSigCor";
            strSQLCon[1]+=" FROM tbm_cabPag AS a1";
            strSQLCon[1]+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[1]+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
            strSQLCon[1]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[1]+=" AND a3.tx_natDoc IN ('I', 'E')";
            strSQLCon[1]+=" ) AS b1";
            strSQLCon[1]+=" WHERE b1.nd_abo<>b1.nd_aboSigCor";
            strSQLCon[1]+=" ORDER BY b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg";
            
            strSQLCon[2]="";
            strSQLCon[2]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_abo) AS nd_valDoc";
            strSQLCon[2]+=" FROM tbm_detPag AS a1";
            strSQLCon[2]+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
            strSQLCon[2]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[2]+=" AND a1.st_reg='A'";
            strSQLCon[2]+=" AND a2.tx_natDoc IN ('A')";
            strSQLCon[2]+=" AND a2.st_docCua='S'";
            strSQLCon[2]+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[2]+=" HAVING SUM(a1.nd_abo)<>0";
            strSQLCon[2]+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
            
            strSQLCon[3]="";
            strSQLCon[3]+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif";
            strSQLCon[3]+=" FROM ";
            strSQLCon[3]+=" tbm_cabMovInv AS b1";
            strSQLCon[3]+=" INNER JOIN";
            strSQLCon[3]+=" (";
            strSQLCon[3]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag";
            strSQLCon[3]+=" FROM";
            strSQLCon[3]+=" (";
            strSQLCon[3]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag";
            strSQLCon[3]+=" FROM tbm_cabMovInv";
            strSQLCon[3]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[3]+=" UNION ALL";
            strSQLCon[3]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag";
            strSQLCon[3]+=" FROM tbm_pagMovInv";
            strSQLCon[3]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[3]+=" AND st_reg IN ('A','F')";
            strSQLCon[3]+=" GROUP BY co_emp, co_loc, co_tipDoc, co_doc";
            strSQLCon[3]+=" ) AS a1";
            strSQLCon[3]+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[3]+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
            strSQLCon[3]+=" INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)";
            strSQLCon[3]+=" WHERE b1.nd_tot<>b2.nd_pag";
            strSQLCon[3]+=" AND b3.st_genPag='S'";
            strSQLCon[3]+=" AND b1.co_tipDoc NOT IN (124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 165, 166, 167, 168)";
            strSQLCon[3]+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";

            strSQLCon[4]="";
            strSQLCon[4]+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif";
            strSQLCon[4]+=" FROM ";
            strSQLCon[4]+=" tbm_cabMovInv AS b1";
            strSQLCon[4]+=" INNER JOIN";
            strSQLCon[4]+=" (";
            strSQLCon[4]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag";
            strSQLCon[4]+=" FROM";
            strSQLCon[4]+=" (";
            strSQLCon[4]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag";
            strSQLCon[4]+=" FROM tbm_cabMovInv";
            strSQLCon[4]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[4]+=" UNION ALL";
            strSQLCon[4]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag";
            strSQLCon[4]+=" FROM tbm_pagMovInv";
            strSQLCon[4]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[4]+=" AND st_reg IN ('A','C')";
            strSQLCon[4]+=" GROUP BY co_emp, co_loc, co_tipDoc, co_doc";
            strSQLCon[4]+=" ) AS a1";
            strSQLCon[4]+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[4]+=" ) AS b2  ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
            strSQLCon[4]+=" INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)";
            strSQLCon[4]+=" WHERE b1.nd_tot<>b2.nd_pag";
            strSQLCon[4]+=" AND b3.st_genPag='S'";
            strSQLCon[4]+=" AND b1.co_tipDoc NOT IN (124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 165, 166, 167, 168)";
            strSQLCon[4]+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
            
            strSQLCon[5]="";
            strSQLCon[5]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, co_reg, mo_pag, nd_abo, st_reg";
            strSQLCon[5]+=" FROM tbm_pagMovInv";
            strSQLCon[5]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[5]+=" AND st_reg IN ('F', 'I') AND nd_abo<>0";
            strSQLCon[5]+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc, co_reg";
            
            strSQLCon[6]="";
            strSQLCon[6]+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1, b1.fe_doc, b1.nd_monDoc, b2.nd_pag, (b1.nd_monDoc-b2.nd_pag) AS nd_dif";
            strSQLCon[6]+=" FROM ";
            strSQLCon[6]+=" tbm_cabPag AS b1";
            strSQLCon[6]+=" INNER JOIN";
            strSQLCon[6]+=" (";
            strSQLCon[6]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag";
            strSQLCon[6]+=" FROM";
            strSQLCon[6]+=" (";
            strSQLCon[6]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag";
            strSQLCon[6]+=" FROM tbm_cabPag";
            strSQLCon[6]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[6]+=" UNION ALL";
            strSQLCon[6]+=" SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(nd_abo) AS nd_pag";
            strSQLCon[6]+=" FROM tbm_detPag";
            strSQLCon[6]+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[6]+=" AND st_reg='A'";
            strSQLCon[6]+=" GROUP BY co_emp, co_loc, co_tipDoc, co_doc";
            strSQLCon[6]+=" ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
            strSQLCon[6]+=" ) AS a1";
            strSQLCon[6]+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[6]+=" ) AS b2  ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc)";
            strSQLCon[6]+=" INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc)";
            strSQLCon[6]+=" WHERE b1.nd_monDoc<>b2.nd_pag";
            strSQLCon[6]+=" AND b3.tx_natDoc IN ('I', 'E')";
            strSQLCon[6]+=" AND b1.co_tipDoc NOT IN (173)";
            strSQLCon[6]+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";

            strSQLCon[7]="";
            strSQLCon[7]+=" SELECT b1.*";
            strSQLCon[7]+=" FROM";
            strSQLCon[7]+=" (";
            strSQLCon[7]+=" SELECT a2.*";
            strSQLCon[7]+=" FROM tbm_cabPag AS a1";
            strSQLCon[7]+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[7]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[7]+=" AND a1.st_reg IN ('A') AND a2.st_reg='A'";
            strSQLCon[7]+=" ) AS b1 INNER JOIN ";
            strSQLCon[7]+=" (";
            strSQLCon[7]+=" SELECT a2.*";
            strSQLCon[7]+=" FROM tbm_cabMovInv AS a1 ";
            strSQLCon[7]+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[7]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[7]+=" AND a2.st_reg NOT IN ('A','C')";
            strSQLCon[7]+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_locPag=b2.co_loc AND b1.co_tipDocPag=b2.co_tipDoc AND b1.co_docPag=b2.co_doc AND b1.co_regPag=b2.co_reg)";
            strSQLCon[7]+=" ORDER BY b1.co_tipDoc, b1.co_doc, b1.co_reg";
            
            strSQLCon[8]="";
            strSQLCon[8]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a2.fe_dia";
            strSQLCon[8]+=" FROM tbm_cabMovinv AS a1";
            strSQLCon[8]+=" INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
            strSQLCon[8]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[8]+=" AND a1.fe_doc<>a2.fe_dia";
            strSQLCon[8]+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";

            strSQLCon[9]="";
            strSQLCon[9]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a2.fe_dia";
            strSQLCon[9]+=" FROM tbm_cabPag AS a1";
            strSQLCon[9]+=" INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
            strSQLCon[9]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[9]+=" AND a1.fe_doc<>a2.fe_dia";
            strSQLCon[9]+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";

            strSQLCon[10]="";
            strSQLCon[10]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia, SUM(a2.nd_monDeb) AS nd_monDeb, SUM(a2.nd_monHab) AS nd_monHab, SUM(a2.nd_monDeb)-SUM(a2.nd_monHab) AS nd_dif, a1.st_reg";
            strSQLCon[10]+=" FROM tbm_cabDia AS a1";
            strSQLCon[10]+=" INNER JOIN tbm_detDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia)";
            strSQLCon[10]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[10]+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.fe_dia, a1.st_reg";
            strSQLCon[10]+=" HAVING SUM(a2.nd_monDeb)<>SUM(a2.nd_monHab)";
            strSQLCon[10]+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
            
            strSQLCon[11]="";
            strSQLCon[11]+=" SELECT c1.*";
            strSQLCon[11]+=" FROM";
            strSQLCon[11]+=" (";
            strSQLCon[11]+=" SELECT b1.*";
            strSQLCon[11]+=" FROM";
            strSQLCon[11]+=" (";
            strSQLCon[11]+=" SELECT a2.*, a1.st_reg";
            strSQLCon[11]+=" FROM tbm_cabPag AS a1";
            strSQLCon[11]+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[11]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[11]+=" AND a2.st_reg='A' AND a1.co_tipDoc NOT IN (107, 163, 164)";
            strSQLCon[11]+=" ) AS b1 LEFT OUTER JOIN ";
            strSQLCon[11]+=" (";
            strSQLCon[11]+=" SELECT a2.*";
            strSQLCon[11]+=" FROM tbm_cabMovInv AS a1 ";
            strSQLCon[11]+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[11]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[11]+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_locPag=b2.co_loc AND b1.co_tipDocPag=b2.co_tipDoc AND b1.co_docPag=b2.co_doc AND b1.co_regPag=b2.co_reg)";
            strSQLCon[11]+=" WHERE b2.co_reg IS NULL";
            strSQLCon[11]+=" ) AS c1";
            strSQLCon[11]+=" WHERE c1.co_locPag IS NOT NULL";
            strSQLCon[11]+=" ORDER BY c1.co_loc, c1.co_tipDoc, c1.co_doc, c1.co_reg";
            
            strSQLCon[12]="";
            strSQLCon[12]+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.nd_abo, a2.nd_monDeb, (ABS(b1.nd_abo)-ABS(a2.nd_monDeb)) AS nd_dif";
            strSQLCon[12]+=" FROM ";
            strSQLCon[12]+=" (";
            strSQLCon[12]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_abo) AS nd_abo FROM tbm_detPag AS a1 INNER JOIN tbm_cabTipDoc AS b2 ON (a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc) WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg='A' AND b2.st_docCua='N' GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[12]+=" UNION ALL";
            strSQLCon[12]+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_abo) AS nd_abo FROM tbm_detPag AS a1 INNER JOIN tbm_cabTipDoc AS b2 ON (a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc) WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a1.st_reg='A' AND b2.st_docCua='S' AND a1.nd_abo>0 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
            strSQLCon[12]+=" ) AS b1";
            strSQLCon[12]+=" FULL OUTER JOIN ";
            strSQLCon[12]+=" (";
            strSQLCon[12]+=" SELECT co_emp, co_loc, co_tipDoc, co_dia, SUM(nd_monDeb) AS nd_monDeb FROM tbm_detDia WHERE co_emp=" + objParSis.getCodigoEmpresa() + " GROUP BY co_emp, co_loc, co_tipDoc, co_dia";
            strSQLCon[12]+=" ) AS a2 ON (b1.co_emp=a2.co_emp AND b1.co_loc=a2.co_loc AND b1.co_tipDoc=a2.co_tipDoc AND b1.co_doc=a2.co_dia)";
            strSQLCon[12]+=" WHERE ABS(b1.nd_abo)<>ABS(a2.nd_monDeb)";
            strSQLCon[12]+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";

            strSQLCon[13]="";
            strSQLCon[13]+=" SELECT c1.* ";
            strSQLCon[13]+=" FROM";
            strSQLCon[13]+=" (";
            strSQLCon[13]+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.ne_numDoc, b1.nd_aboModVen";
            strSQLCon[13]+=" , (CASE WHEN b2.nd_aboModCxC IS NULL THEN 0 ELSE b2.nd_aboModCxC END) AS nd_aboModCxC";
            strSQLCon[13]+=" FROM";
            strSQLCon[13]+=" (";
            strSQLCon[13]+=" SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.nd_abo AS nd_aboModVen, a1.co_cli, a1.ne_numDoc";
            strSQLCon[13]+=" FROM tbm_cabMovInv AS a1";
            strSQLCon[13]+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDOC AND a1.co_doc=a2.co_doc)";
            strSQLCon[13]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[13]+=" AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')";
            strSQLCon[13]+=" ) AS b1 LEFT OUTER JOIN (";
            strSQLCon[13]+=" SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC";
            strSQLCon[13]+=" FROM tbm_cabPag AS a1";
            strSQLCon[13]+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSQLCon[13]+=" INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)";
            strSQLCon[13]+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQLCon[13]+=" AND a1.st_reg='A' AND a2.st_reg='A' AND a3.st_reg IN ('A','R','C','F')";
            strSQLCon[13]+=" GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag";
            strSQLCon[13]+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locPag AND b1.co_tipDoc=b2.co_tipDocPag AND b1.co_doc=b2.co_docPag AND b1.co_reg=b2.co_regPag)";
            strSQLCon[13]+=" INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)";
            strSQLCon[13]+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
            strSQLCon[13]+=" ) AS c1";
            strSQLCon[13]+=" WHERE c1.nd_aboModVen IS NULL OR c1.nd_aboModCxC IS NULL OR c1.nd_aboModVen<>c1.nd_aboModCxC";
            
            //Configurar las ZafVenCon.
            configurarVenConCli();
            //Configurar los JTables.
            configurarTblCon();
            configurarTblDat();
            configurarTblDet();
            cargarLisCon();
            //Configurar los Tabs correspondientes a los otros controles.
            panRptCon=new javax.swing.JPanel[intNumTotCon];
            lblTitCon=new javax.swing.JLabel[intNumTotCon];
            spnDatCon=new javax.swing.JScrollPane[intNumTotCon];
            tblDatCon=new javax.swing.JTable[intNumTotCon];
            objTblModCon=new ZafTblMod[intNumTotCon];
            spnSenSQLCon=new javax.swing.JScrollPane[intNumTotCon];
            txaSenSQLCon=new javax.swing.JTextArea[intNumTotCon];
            for (i=0; i<intNumTotCon; i++)
            {
                panRptCon[i]=new javax.swing.JPanel();
                panRptCon[i].setLayout(new java.awt.BorderLayout());
                lblTitCon[i]=new javax.swing.JLabel();
                lblTitCon[i].setText(strTitCon[i]);
                spnDatCon[i]=new javax.swing.JScrollPane();
                tblDatCon[i]=new javax.swing.JTable();
                objTblModCon[i]=new ZafTblMod();
                //Configurar JTable: Establecer el modelo de la tabla.
                tblDatCon[i].setModel(objTblModCon[i]);
                //Configurar JTable: Establecer tipo de selección.
                tblDatCon[i].setRowSelectionAllowed(true);
                tblDatCon[i].setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                //Configurar JTable: Establecer el menú de contexto.
                objTblPopMnu=new ZafTblPopMnu(tblDatCon[i]);
                //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
                tblDatCon[i].setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                spnDatCon[i].setViewportView(tblDatCon[i]);
                spnSenSQLCon[i]=new javax.swing.JScrollPane();
                spnSenSQLCon[i].setPreferredSize(new java.awt.Dimension(0, 80));
                txaSenSQLCon[i]=new javax.swing.JTextArea();
                txaSenSQLCon[i].setText(strSQLCon[i]);
                txaSenSQLCon[i].setLineWrap(true);
                txaSenSQLCon[i].setWrapStyleWord(true);
                txaSenSQLCon[i].setEditable(false);
                spnSenSQLCon[i].setViewportView(txaSenSQLCon[i]);
                panRptCon[i].add(lblTitCon[i], java.awt.BorderLayout.NORTH);
                panRptCon[i].add(spnDatCon[i], java.awt.BorderLayout.CENTER);
                panRptCon[i].add(spnSenSQLCon[i], java.awt.BorderLayout.SOUTH);
                tabFrm.addTab("Control" + (i+1), panRptCon[i]);
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
     * Esta función configura el JTable "tblCon".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCon()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(3);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CON_LIN,"");
            vecCab.add(INT_TBL_CON_CHK,"");
            vecCab.add(INT_TBL_CON_NOM,"Control");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLisCon=new ZafTblMod();
            objTblModLisCon.setHeader(vecCab);
            tblCon.setModel(objTblModLisCon);
            //Configurar JTable: Establecer tipo de selección.
            tblCon.setRowSelectionAllowed(true);
            tblCon.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCon);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCon.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCon.getColumnModel();
            tcmAux.getColumn(INT_TBL_CON_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CON_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CON_NOM).setPreferredWidth(610);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_CON_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCon.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_EMP_COD_TIP_DOC).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblCon.getTableHeader().addMouseMotionListener(new ZafMouMotAdaCon());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblCon.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblConMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_CON_CHK);
            objTblModLisCon.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblEmp);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblCon);
            tcmAux.getColumn(INT_TBL_CON_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CON_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblCon);
            tcmAux.getColumn(INT_TBL_CON_CHK).setCellEditor(objTblCelEdiChk);
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

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLisCon.setModoOperacion(objTblModLisCon.INT_TBL_EDI);
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
            vecCab=new Vector(8);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_ABO_VEN,"Abo.Ven.");
            vecCab.add(INT_TBL_DAT_ABO_CXC,"Abo.CxC");
            vecCab.add(INT_TBL_DAT_DIF,"Diferencia");
            //Configurar JTable: Establecer el modelo de la tabla.
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(102);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(232);
            tcmAux.getColumn(INT_TBL_DAT_ABO_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_ABO_CXC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIF).setPreferredWidth(80);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_ABO_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ABO_CXC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DIF).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_ABO_VEN, INT_TBL_DAT_ABO_CXC, INT_TBL_DAT_DIF};
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
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(15);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DET_LIN,"");
            vecCab.add(INT_TBL_DET_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DET_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DET_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DET_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DET_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DET_COD_REG,"Cod.Reg.");
            vecCab.add(INT_TBL_DET_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DET_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DET_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_DET_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DET_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_DET_ABO_VEN,"Abo.Ven.");
            vecCab.add(INT_TBL_DET_ABO_CXC,"Abo.CxC");
            vecCab.add(INT_TBL_DET_DIF,"Diferencia");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCab);
            tblMovReg.setModel(objTblModDet);
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
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DET_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DET_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DET_POR_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_ABO_CXC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DET_DIF).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_LOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_TIP_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_DOC).setResizable(false);
            
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DET_COD_REG).setResizable(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblMovReg.getTableHeader().addMouseMotionListener(new ZafMouMotAdaCre());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblMovReg);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblMovReg);
            tcmAux.getColumn(INT_TBL_DET_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DET_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_ABO_VEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_ABO_CXC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DET_DIF).setCellRenderer(objTblCelRenLbl);
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
     * Esta función permite consultar el listado de controles:
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLisCon()
    {
        int i;
        boolean blnRes=true;
        try
        {
            //Limpiar vector de datos.
            vecDat.clear();
            //Obtener los registros.
            for (i=0; i<strTitCon.length; i++)
            {
                vecReg=new Vector();
                vecReg.add(INT_TBL_CON_LIN,"");
                vecReg.add(INT_TBL_CON_CHK,new Boolean(true));
                vecReg.add(INT_TBL_CON_NOM,"Control" + (i+1) + ":" + strTitCon[i]);
                vecDat.add(vecReg);
            }
            //Asignar vectores al modelo.
            objTblModLisCon.setData(vecDat);
            tblCon.setModel(objTblModLisCon);
            vecDat.clear();
            blnMarTodChkTblCon=false;
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
        double dblAboVen, dblAboCxC, dblDif;
        int i, j, intNumCam;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText(tabFrm.getTitleAt(1) + ": Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a3.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a3.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_cli, b3.tx_ide, b3.tx_nom, b1.nd_aboModVen, b2.nd_aboModCxC";
                strSQL+=" FROM (";
                strSQL+=" SELECT a1.co_emp, a1.co_cli, SUM(a2.nd_abo) AS nd_aboModVen";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')";
                strSQL+=" GROUP BY a1.co_emp, a1.co_cli";
                strSQL+=" ) AS b1 INNER JOIN (";
                strSQL+=" SELECT a1.co_emp, a3.co_cli, SUM(a2.nd_abo) AS nd_aboModCxC";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' AND a2.st_reg='A' AND a3.st_reg IN ('A','R','C','F')";
                strSQL+=" GROUP BY a1.co_emp, a3.co_cli";
                strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                strSQL+=" INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)";
                strSQL+=" WHERE b1.nd_aboModVen<>b2.nd_aboModCxC AND b3.st_cli='S'";
                strSQL+=" ORDER BY b1.co_emp, b1.co_cli";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText(tabFrm.getTitleAt(1) + ": Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
                        dblAboVen=rst.getDouble("nd_aboModVen");
                        dblAboCxC=rst.getDouble("nd_aboModCxC");
                        dblDif=objUti.redondear(dblAboVen-dblAboCxC, objParSis.getDecimalesBaseDatos());
                        vecReg.add(INT_TBL_DAT_ABO_VEN,"" + dblAboVen);
                        vecReg.add(INT_TBL_DAT_ABO_CXC,"" + dblAboCxC);
                        vecReg.add(INT_TBL_DAT_DIF,"" + dblDif);
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                rst=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Cargar los datos para los demás controles.
                for (i=0; i<intNumTotCon; i++)
                {
                    if (objTblModLisCon.isChecked(i, INT_TBL_CON_CHK))
                    {
                        lblMsgSis.setText(tabFrm.getTitleAt(i+2) + ": Obteniendo datos...");
                        rst=stm.executeQuery(strSQLCon[i]);
                        rmd=rst.getMetaData();
                        intNumCam=rmd.getColumnCount();
                        //Obtener la cabecera.
                        vecCab.clear();
                        for (j=1; j<=intNumCam; j++)
                            vecCab.add(rmd.getColumnName(j));
                        rmd=null;
                        //Limpiar vector de datos.
                        vecDat.clear();
                        //Obtener los registros.
                        lblMsgSis.setText(tabFrm.getTitleAt(i+2) + ": Cargando datos...");
                        while (rst.next())
                        {
                            if (blnCon)
                            {
                                vecReg=new Vector();
                                for(j=1; j<=intNumCam; j++)
                                    vecReg.add(rst.getString(j));
                                vecDat.add(vecReg);
                            }
                            else
                            {
                                break;
                            }
                        }
                        rst.close();
                        rst=null;
                        //Asignar vectores al modelo.
                        objTblModCon[i].setHeader(vecCab);
                        objTblModCon[i].setData(vecDat);
                        tblDatCon[i].setModel(objTblModCon[i]);
                        vecDat.clear();
                    }
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
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
        double dblAboVen, dblAboCxC, dblDif;
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                        //Armar la sentencia SQL.
                        strSQL="";
                        strSQL+="SELECT b1.co_loc, b1.co_tipDoc, b4.tx_desCor, b4.tx_desLar, b1.co_doc, b1.co_reg, b1.ne_numDoc, b1.fe_doc, b1.ne_diaCre, b1.fe_ven, b1.nd_porRet, b1.nd_aboModVen, b2.nd_aboModCxC";
                        strSQL+=" FROM (";
                        strSQL+=" SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.nd_abo AS nd_aboModVen, a1.co_cli, a1.ne_numDoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDOC AND a1.co_doc=a2.co_doc)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a1.co_cli=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI);
                        strSQL+=" AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C')";
                        strSQL+=" ) AS b1 LEFT OUTER JOIN (";
                        strSQL+=" SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC";
                        strSQL+=" FROM tbm_cabPag AS a1";
                        strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND a3.co_cli=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI);
                        strSQL+=" AND a1.st_reg='A' AND a3.st_reg IN ('A','R','C','F')";
                        strSQL+=" GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag";
                        strSQL+=" ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locPag AND b1.co_tipDoc=b2.co_tipDocPag AND b1.co_doc=b2.co_docPag AND b1.co_reg=b2.co_regPag)";
                        strSQL+=" INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS b4 ON (b1.co_emp=b4.co_emp AND b1.co_loc=b4.co_loc AND b1.co_tipDoc=b4.co_tipDoc)";
                        strSQL+=" ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                        System.out.println("strSQL: " + strSQL);
                        rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DET_LIN,"");
                        vecReg.add(INT_TBL_DET_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DET_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DET_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DET_DEL_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DET_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DET_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DET_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DET_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DET_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_DET_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DET_POR_RET,rst.getString("nd_porRet"));
                        dblAboVen=rst.getDouble("nd_aboModVen");
                        dblAboCxC=rst.getDouble("nd_aboModCxC");
                        dblDif=objUti.redondear(dblAboVen-dblAboCxC, objParSis.getDecimalesBaseDatos());
                        vecReg.add(INT_TBL_DET_ABO_VEN,"" + dblAboVen);
                        vecReg.add(INT_TBL_DET_ABO_CXC,"" + dblAboCxC);
                        vecReg.add(INT_TBL_DET_DIF,"" + dblDif);
                        if (optTodAbo.isSelected())
                            vecDat.add(vecReg);
                        else
                            if (dblDif!=0)
                                vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModDet.setData(vecDat);
                    tblMovReg.setModel(objTblModDet);
                    vecDat.clear();
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
     * mostrar los "Clientes/Proveedores".
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
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            if (objParSis.getCodigoMenu()==319)
                strSQL+=" AND a1.st_cli='S'";
            else
                strSQL+=" AND a1.st_prv='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
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
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblConMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLisCon.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblCon.columnAtPoint(evt.getPoint())==INT_TBL_CON_CHK)
            {
                if (blnMarTodChkTblCon)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLisCon.setChecked(true, i, INT_TBL_CON_CHK);
                    }
                    blnMarTodChkTblCon=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLisCon.setChecked(false, i, INT_TBL_CON_CHK);
                    }
                    blnMarTodChkTblCon=true;
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
            //Limpiar objetos.
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
    private class ZafMouMotAdaCon extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCon.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CON_LIN:
                    strMsg="";
                    break;
                case INT_TBL_CON_NOM:
                    strMsg="Nombre del control";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblCon.getTableHeader().setToolTipText(strMsg);
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_IDE_CLI:
                    strMsg="Identificación del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_ABO_VEN:
                    strMsg="Abono según el módulo de \"Ventas\"";
                    break;
                case INT_TBL_DAT_ABO_CXC:
                    strMsg="Abono según el módulo de \"Cuentas por cobrar\"";
                    break;
                case INT_TBL_DAT_DIF:
                    strMsg="Diferencia";
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
    private class ZafMouMotAdaCre extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblMovReg.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DET_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DET_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DET_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DET_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DET_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DET_COD_REG:
                    strMsg="Código del registro";
                    break;
                case INT_TBL_DET_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DET_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DET_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DET_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DET_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DET_ABO_VEN:
                    strMsg="Abono según el módulo de \"Ventas\"";
                    break;
                case INT_TBL_DET_ABO_CXC:
                    strMsg="Abono según el módulo de \"Cuentas por cobrar\"";
                    break;
                case INT_TBL_DET_DIF:
                    strMsg="Diferencia";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblMovReg.getTableHeader().setToolTipText(strMsg);
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
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
                if (chkMosMovReg.isSelected())
                    cargarMovReg();
                else
                    objTblModDet.removeAllRows();
            }
        }
    }
    
}