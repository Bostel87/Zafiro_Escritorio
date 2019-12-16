/*
 * ZafVen39.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Ventas.ZafVen39;
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
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Jose Mario Marin
 */
public class ZafVen39 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.
    
    
    static final int INT_TBL_DAT_LIN=0;                          
    static final int INT_TBL_DAT_COD_VEN=1;                    
    static final int INT_TBL_DAT_NOM_VEN=2;                      
    static final int INT_TBL_DAT_COD_CLI=3;                    
    static final int INT_TBL_DAT_NOM_CLI=4;                   
    
    static final int INT_TBL_DAT_COT_VEN_NUM_TOT_DOC=5; 
    static final int INT_TBL_DAT_COT_VEN_NUM_TOT_FIL=6; 
    static final int INT_TBL_DAT_COT_VEN_VAL_TOT_DOC=7; 
    static final int INT_TBL_DAT_COT_VEN_BTN_LIS_DOC=8; 
    
    static final int INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC=9; 
    static final int INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL=10; 
    static final int INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC=11; 
    static final int INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC=12; 
    
    static final int INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC=13; 
    static final int INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL=14; 
    static final int INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC=15; 
    static final int INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC=16; 
     
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModLoc;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd, objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
     
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoVen;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
     
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strCodCli, strIdeCli, strDesLarCli;          //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
   
    private String strVersion="v 0.05";
    
    /**
     * Crea una nueva instancia de la clase ZafCom10.
     * @param obj El objeto ZafParSis.
     */
    public ZafVen39(ZafParSis obj) 
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
        bgrFuncionamiento = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        sppFil = new javax.swing.JSplitPane();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtIdeCli = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        chkMosCli = new javax.swing.JCheckBox();
        panFun = new javax.swing.JPanel();
        rdoPorVendedor = new javax.swing.JRadioButton();
        rdoPorUsuarioIngreso = new javax.swing.JRadioButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable()  {    protected javax.swing.table.JTableHeader createDefaultTableHeader()    {       return new ZafTblHeaGrp(columnModel);    } };
        spnTot = new javax.swing.JScrollPane();
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

        sppFil.setBorder(null);
        sppFil.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppFil.setResizeWeight(0.14);
        sppFil.setToolTipText("");
        sppFil.setOneTouchExpandable(true);
        sppFil.setPreferredSize(new java.awt.Dimension(464, 360));

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setPreferredSize(new java.awt.Dimension(464, 60));
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

        sppFil.setTopComponent(panLoc);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 76, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(20, 120, 120, 20);

        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        txtCodCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodCliActionPerformed(evt);
            }
        });
        panFil.add(txtCodCli);
        txtCodCli.setBounds(140, 120, 60, 20);

        txtDesLarCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCliFocusLost(evt);
            }
        });
        txtDesLarCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCliActionPerformed(evt);
            }
        });
        panFil.add(txtDesLarCli);
        txtDesLarCli.setBounds(300, 120, 340, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(640, 120, 20, 20);

        lblVen.setText("Vendedor:");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil.add(lblVen);
        lblVen.setBounds(20, 140, 120, 20);

        txtCodVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodVenFocusLost(evt);
            }
        });
        txtCodVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodVenActionPerformed(evt);
            }
        });
        panFil.add(txtCodVen);
        txtCodVen.setBounds(140, 140, 60, 20);

        txtNomVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomVenFocusLost(evt);
            }
        });
        txtNomVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomVenActionPerformed(evt);
            }
        });
        panFil.add(txtNomVen);
        txtNomVen.setBounds(200, 140, 440, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil.add(butVen);
        butVen.setBounds(640, 140, 20, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        panFil.add(txtIdeCli);
        txtIdeCli.setBounds(200, 120, 100, 20);

        jLabel1.setText("Columnas:");
        panFil.add(jLabel1);
        jLabel1.setBounds(20, 165, 70, 14);

        chkMosCli.setText("Mostrar \"Cliente\"");
        panFil.add(chkMosCli);
        chkMosCli.setBounds(140, 165, 200, 23);

        panFun.setBorder(javax.swing.BorderFactory.createTitledBorder("Funcionamiento:"));
        panFun.setLayout(null);

        bgrFuncionamiento.add(rdoPorVendedor);
        rdoPorVendedor.setSelected(true);
        rdoPorVendedor.setText("Por Vendedor");
        panFun.add(rdoPorVendedor);
        rdoPorVendedor.setBounds(10, 20, 130, 23);

        bgrFuncionamiento.add(rdoPorUsuarioIngreso);
        rdoPorUsuarioIngreso.setText("Por usuario de ingreso");
        panFun.add(rdoPorUsuarioIngreso);
        rdoPorUsuarioIngreso.setBounds(10, 40, 170, 23);

        panFil.add(panFun);
        panFun.setBounds(500, 10, 200, 70);

        sppFil.setBottomComponent(panFil);

        spnFil.setViewportView(sppFil);

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
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

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
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 706, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(3);
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
                txtIdeCli.setText("");
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
           
            txtCodVen.setText("");
            txtNomVen.setText("");
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
         
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
         
    }//GEN-LAST:event_tblDatKeyPressed

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

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
       
    }//GEN-LAST:event_tblDatMouseClicked

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

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli=txtIdeCli.getText();
        txtIdeCli.selectAll();
    }//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtDesLarCli.setText("");
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtIdeCli.setText(strIdeCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtIdeCliFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.ButtonGroup bgrFuncionamiento;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosCli;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
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
    private javax.swing.JPanel panFun;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton rdoPorUsuarioIngreso;
    private javax.swing.JRadioButton rdoPorVendedor;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JSplitPane sppFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIdeCli;
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
            objSelFec.setTitulo("Fecha del documento");
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
            
            //Configurar las ZafVenCon.
            configurarVenConCli();
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblLoc();
            configurarTblDat();
            cargarLoc();
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
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(320);
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
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_VEN,"Cód.Ven.");
            vecCab.add(INT_TBL_DAT_NOM_VEN,"Nom.Ven.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            
            vecCab.add(INT_TBL_DAT_COT_VEN_NUM_TOT_DOC,"Núm.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_COT_VEN_NUM_TOT_FIL,"Núm.Tot.Fil.");
            vecCab.add(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC,"Val.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC,"...");
            
            vecCab.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC,"Núm.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,"Núm.Tot.Fil.");
            vecCab.add(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC,"Val.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC,"...");
            
            vecCab.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC,"Núm.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,"Núm.Tot.Fil.");
            vecCab.add(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC,"Val.Tot.Doc.");
            vecCab.add(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC,"...");
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(157);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(157);
             
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_NUM_TOT_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_NUM_TOT_FIL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC).setPreferredWidth(30);
            
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC).setPreferredWidth(30);
    
 
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
             
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
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ArrayList arlColHid=new ArrayList();
            if(!chkMosCli.isSelected()){
                arlColHid.add(""+INT_TBL_DAT_COD_CLI);
                arlColHid.add(""+INT_TBL_DAT_NOM_CLI);
            }
            if(rdoPorVendedor.isSelected()){
                arlColHid.add(""+INT_TBL_DAT_COT_VEN_NUM_TOT_FIL);
                arlColHid.add(""+INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL);
                arlColHid.add(""+INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL);
            }
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);   
            arlColHid=null;
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COT_VEN_BTN_LIS_DOC);
            vecAux.add("" + INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC);
            vecAux.add("" + INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            
            //botones agregados
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC).setCellRenderer(objTblCelRenBut);
            tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            ButLisCotVen butLisCotVen = new ButLisCotVen(tblDat, INT_TBL_DAT_COT_VEN_BTN_LIS_DOC); //***** 
            
            ButLisFacVen butLisFacVen = new ButLisFacVen(tblDat, INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC); //*****
            ButLisDevVen butLisDevVen = new ButLisDevVen(tblDat, INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC); //*****
            
            
            
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpCot=new ZafTblHeaColGrp("Cotizaciones de Venta");
            objTblHeaColGrpCot.setHeight(16);
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COT_VEN_NUM_TOT_DOC)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COT_VEN_NUM_TOT_FIL)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC)); 
            objTblHeaColGrpCot.add(tcmAux.getColumn(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC)); 
            
            ZafTblHeaColGrp objTblHeaColGrpFacVen=new ZafTblHeaColGrp("Facturas de Venta");
            objTblHeaColGrpFacVen.setHeight(16);
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC));
            objTblHeaColGrpFacVen.add(tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC));
            
            ZafTblHeaColGrp objTblHeaColGrpDevVen=new ZafTblHeaColGrp("Devoluciones de Venta");
            objTblHeaColGrpDevVen.setHeight(16);
            objTblHeaColGrpDevVen.add(tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC)); 
            objTblHeaColGrpDevVen.add(tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL)); 
            objTblHeaColGrpDevVen.add(tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC)); 
            objTblHeaColGrpDevVen.add(tcmAux.getColumn(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC)); 
            
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCot);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpFacVen);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpDevVen);
            
            objTblHeaColGrpCot=null;
            objTblHeaColGrpFacVen=null;
            objTblHeaColGrpDevVen=null;
            
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_COT_VEN_NUM_TOT_DOC, INT_TBL_DAT_COT_VEN_NUM_TOT_FIL, INT_TBL_DAT_COT_VEN_VAL_TOT_DOC, 
                          INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC,INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC, 
                          INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC,INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC, 
            };
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
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
    
    private class ButLisCotVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisCotVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Cotizaciones de Venta.");
           
        }
        @Override
        public void butCLick() {
         // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarCotVen();
        }
    }
    
    private class ButLisDevVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisDevVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Devoluciones de Venta.");
           
        }
        @Override
        public void butCLick() {
         // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarFacVen(229);
        }
    }
    
      private class ButLisFacVen extends Librerias.ZafTableColBut.ZafTableColBut_uni{
        public ButLisFacVen(javax.swing.JTable tbl, int intIdx){
            super(tbl,intIdx, "Factura de Venta.");
           
        }
        @Override
        public void butCLick() {
         // OBTENER FACTURAS SEGUN LA COTIZACION PUEDE TENER MUCHAS FACTURAS
            llamarFacVen(228);
        }
    }
      
    private void llamarCotVen(){
        try{
            String strAuxFecCot="" , strCodEmpLoc;
            boolean blnIsPorVendedor=false, blnMosCli=false;
            int intNumFilTblLoc=objTblModLoc.getRowCountTrue();
            int i=0, intCol = tblDat.getSelectedRow();
            String strCodVen = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  ).toString());
            String strCodCli = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_CLI  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_CLI  ).toString());
            
            if(chkMosCli.isSelected()){
                if(!(strCodCli.length()>0)){
                    strCodCli="0";
                }
            }
            else{
                if(txtCodCli.getText().length()>0){
                    strCodCli=txtCodCli.getText();
                }
                else{
                    strCodCli="0";
                }
            }
            System.out.println("cliente " + strCodCli);
            
            switch (objSelFec.getTipoSeleccion()){
                case 0: //Búsqueda por rangos
                    strAuxFecCot+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAuxFecCot+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAuxFecCot+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }

            strCodEmpLoc="";
            for (int j=0; j<intNumFilTblLoc; j++)
            {
                if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                {
                    if (i==0)
                        strCodEmpLoc+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    else
                        strCodEmpLoc+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    i++;
                }
            }
            if (!strCodEmpLoc.equals(""))
            {
                strCodEmpLoc=" AND (" + strCodEmpLoc + ")";
            }

            if(rdoPorVendedor.isSelected()){
                blnIsPorVendedor=true;
            }
            else{
                blnIsPorVendedor=false;
            }

            if(chkMosCli.isSelected()){
                blnMosCli=true;
            }
            ZafVen39_02 obj1 = new  ZafVen39_02(objParSis,this,  strCodEmpLoc, blnIsPorVendedor, blnMosCli,Integer.parseInt(strCodVen) ,Integer.parseInt(strCodCli), strAuxFecCot );
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        
    }
      
      
      
    private void llamarFacVen(int CodTipDoc){
        try{
            String  strAuxFecDoc="", strCodEmpLoc;
            boolean blnIsPorVendedor=false, blnMosCli=false;
            int intNumFilTblLoc=objTblModLoc.getRowCountTrue();
            int i=0, intCol = tblDat.getSelectedRow();
            String strCodVen = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_VEN  ).toString());
            String strCodCli = ( tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_CLI  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_DAT_COD_CLI  ).toString());
            
            if(chkMosCli.isSelected()){
                if(!(strCodCli.length()>0)){
                    strCodCli="0";
                }
            }
            else{
                if(txtCodCli.getText().length()>0){
                    strCodCli=txtCodCli.getText();
                }
                else{
                    strCodCli="0";
                }
            }
            
            
            switch (objSelFec.getTipoSeleccion()){
                case 0: //Búsqueda por rangos
                    strAuxFecDoc+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAuxFecDoc+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAuxFecDoc+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                    break;
                case 3: //Todo.
                    break;
            }

            strCodEmpLoc="";
            for (int j=0; j<intNumFilTblLoc; j++)
            {
                if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                {
                    if (i==0)
                        strCodEmpLoc+=" (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    else
                        strCodEmpLoc+=" OR (a1.co_emp=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_EMP) + " AND a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    i++;
                }
            }
            if (!strCodEmpLoc.equals(""))
            {
                strCodEmpLoc=" AND (" + strCodEmpLoc + ")";
            }

            if(rdoPorVendedor.isSelected()){
                blnIsPorVendedor=true;
            }
            else{
                blnIsPorVendedor=false;
            }

            if(chkMosCli.isSelected()){
                blnMosCli=true;
            }
            ZafVen39_01 obj1 = new  ZafVen39_01(objParSis,this, CodTipDoc, strCodEmpLoc, blnIsPorVendedor, blnMosCli,Integer.parseInt(strCodVen) ,Integer.parseInt(strCodCli), strAuxFecDoc );
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        
    }
    
    
    /**
     * Esta función permite agregar columnas al "tblDat" de acuerdo a la cantidad de usuarios".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat(){
        boolean blnRes=true;
        try{
            ArrayList arlColHid=new ArrayList();
            if(!chkMosCli.isSelected()){
                arlColHid.add(""+INT_TBL_DAT_COD_CLI);
                arlColHid.add(""+INT_TBL_DAT_NOM_CLI);
            }
            if(rdoPorVendedor.isSelected()){
                arlColHid.add(""+INT_TBL_DAT_COT_VEN_NUM_TOT_FIL);
                arlColHid.add(""+INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL);
                arlColHid.add(""+INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL);
            }
             
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);   
            arlColHid=null;
            
            objTblTot.igualarAnchoColumnas();
             
//            if(rdoPorVendedor.isSelected()){
//                int intCol[]={INT_TBL_DAT_COT_VEN_NUM_TOT_DOC, /*INT_TBL_DAT_COT_VEN_NUM_TOT_FIL,*/ INT_TBL_DAT_COT_VEN_VAL_TOT_DOC,INT_TBL_DAT_COT_VEN_BTN_LIS_DOC,
//                              INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC, /*INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,*/ INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC,INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC,
//                              INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC, /*INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,*/ INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC,INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC
//                };
//                objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
//            }
//            else{
//                //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
//                int intCol[]={INT_TBL_DAT_COT_VEN_NUM_TOT_DOC, INT_TBL_DAT_COT_VEN_NUM_TOT_FIL, INT_TBL_DAT_COT_VEN_VAL_TOT_DOC,INT_TBL_DAT_COT_VEN_BTN_LIS_DOC,
//                              INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC,INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC,INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC,
//                              INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC,INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC,INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC
//                };
//                objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
//            }
             
        }
        catch(Exception e){
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
                    }
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
                    strSQL+=" INNER JOIN tbr_locprgusr AS a3 ON (a2.co_emp=a3.co_empRel AND a2.co_loc=a3.co_locRel)";
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL+=" WHERE a3.co_mnu=" + objParSis.getCodigoMenu() + " AND a3.co_usr=" + objParSis.getCodigoUsuario();
                    }
                    else{
                        strSQL+=" WHERE a3.co_emp=" + intCodEmp + " AND a3.co_loc="+objParSis.getCodigoLocal()+"  AND a3.co_mnu=" + objParSis.getCodigoMenu() + " AND a3.co_usr=" + objParSis.getCodigoUsuario();
                    }
                    strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom";
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
        int intNumFilTblLoc, i, j;
        boolean blnRes=true;
        String strAuxFecCot="", strAuxFecDoc="", strAuxCli="", strAuxVen="", strAuxVenCot="";
        try{
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                //Obtener los locales a consultar.
                intNumFilTblLoc=objTblModLoc.getRowCountTrue();
                i=0;
                strAux="";
                for (j=0; j<intNumFilTblLoc; j++)
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
   
                if (txtCodCli.getText().trim().length()>0)
                    strConSQL+=" AND a3.co_cli=" + txtCodCli.getText();
                else
                {
                    if (txtIdeCli.getText().length()>0)
                    {
                        strConSQL+=" AND a3.tx_ruc='" + txtIdeCli.getText() + "'";
                    }
                }
                if (txtCodVen.getText().length()>0){
                    if(rdoPorVendedor.isSelected()){
                        strAuxVen+=" AND a1.co_com=" + txtCodVen.getText();
                        strAuxVenCot+=" AND a1.co_ven=" + txtCodVen.getText();
                    }
                    else{
                        strAuxVen+=" AND a1.co_usrIng=" + txtCodVen.getText();
                        strAuxVenCot+=" AND a1.co_usrIng=" + txtCodVen.getText();
                    }
                }
                    
                
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
                        strAuxFecCot+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDoc+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAuxFecCot+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDoc+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAuxFecCot+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strAuxFecDoc+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                //Armar la sentencia SQL.
                if(rdoPorVendedor.isSelected()){
                    if(chkMosCli.isSelected()){
                        strSQL="";
                        strSQL+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom,  \n";
                        strSQL+="        a3.co_ven,a3.co_cli, a3.tx_nomCli, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos,  \n";
                        strSQL+="        a4.co_com, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="        a5.co_com, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos  \n";
                        strSQL+=" FROM tbm_usr as a1  \n";
                        strSQL+=" INNER JOIN tbr_locUsr as a2 ON (a1.co_usr=a2.co_usr) \n";
                        strSQL+=" INNER JOIN tbr_usrEmp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_usr=a6.co_usr) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_ven , a2.co_cli,a2.tx_nomCli, \n";
                        strSQL+="              COUNT(a1.co_cot) as CotVen_cantDoc, sum(a2.registros) as CotVen_registros, SUM(a1.nd_tot) as CotVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabCotVen as a1  \n";
                        strSQL+="       INNER JOIN (  \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_cot,a3.co_cli, a3.tx_nom as tx_nomCli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabCotVen as a1  \n";
                        strSQL+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('I') \n";
                        strSQL+=strConSQL; strSQL+=strAuxFecCot; strSQL+=strAuxCli; strSQL+=strAuxVenCot;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_ven IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_cot,a3.co_cli, a3.tx_nom \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="       GROUP BY  a1.co_ven, a2.co_cli,a2.tx_nomCli \n";
                        strSQL+=" )as a3 ON (a2.co_usr=a3.co_ven ) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_com ,a2.co_cli, \n";
                        strSQL+="              COUNT(a1.co_doc) as FacVen_cantDoc, sum(a2.registros) as FacVen_registros, SUM(a1.nd_tot)*-1 as FacVen_valorDocumentos  \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a3.co_cli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE  a1.st_reg NOT IN ('E','I') AND a1.co_tipDoc=228    \n";
                        strSQL+=strAuxFecDoc;  strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_com IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ,a3.co_cli \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSQL+="       GROUP BY  a1.co_com ,a2.co_cli  \n";
                        strSQL+=" ) as a4 ON (a2.co_usr=a4.co_com  AND a3.co_cli=a4.co_cli) \n";
                        strSQL+=" LEFT OUTER JOIN( \n";
                        strSQL+="       SELECT a1.co_com, a2.co_cli, \n";
                        strSQL+="              COUNT(a1.co_doc) as DevVen_cantDoc, sum(a2.registros) as DevVen_registros, SUM(a1.nd_tot)*-1 as DevVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a3.co_cli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('E','I') and a1.co_tipDoc=229   \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_com IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ,a3.co_cli  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc )  \n";
                        strSQL+="       GROUP BY  a1.co_com , a2.co_cli \n";
                        strSQL+=" ) as a5 ON (a2.co_usr=a5.co_com AND a4.co_cli=a5.co_cli) \n";
                        strSQL+=" WHERE a6.st_ven='S'  AND (a3.co_ven IS NOT NULL) \n";
                        strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom, a3.co_ven,a3.co_cli, a3.tx_nomCli,  \n";
                        strSQL+="          a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos, \n";
                        strSQL+="          a4.co_com, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="          a5.co_com, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos   \n";
                        strSQL+=" ORDER BY a1.tx_nom \n";
                    }
                    else{
                        strSQL="";
                        strSQL+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom,  \n";
                        strSQL+="        a3.co_ven, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos,  \n";
                        strSQL+="        a4.co_com, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="        a5.co_com, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos  \n";
                        strSQL+=" FROM tbm_usr as a1  \n";
                        strSQL+=" INNER JOIN tbr_locUsr as a2 ON (a1.co_usr=a2.co_usr) \n";
                        strSQL+=" INNER JOIN tbr_usrEmp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_usr=a6.co_usr) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_ven , \n";
                        strSQL+="              COUNT(a1.co_cot) as CotVen_cantDoc, sum(a2.registros) as CotVen_registros, SUM(a1.nd_tot) as CotVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabCotVen as a1  \n";
                        strSQL+="       INNER JOIN (  \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_cot , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabCotVen as a1  \n";
                        strSQL+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('I') \n";
                        strSQL+=strConSQL; strSQL+=strAuxFecCot;  strSQL+=strAuxCli; strSQL+=strAuxVenCot;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_ven IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_cot  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="       GROUP BY  a1.co_ven \n";
                        strSQL+=" )as a3 ON (a2.co_usr=a3.co_ven ) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_com , \n";
                        strSQL+="              COUNT(a1.co_doc) as FacVen_cantDoc, sum(a2.registros) as FacVen_registros, SUM(a1.nd_tot)*-1 as FacVen_valorDocumentos  \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE  a1.st_reg NOT IN ('E','I') AND a1.co_tipDoc=228    \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_com IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSQL+="       GROUP BY  a1.co_com  \n";
                        strSQL+=" ) as a4 ON (a2.co_usr=a4.co_com) \n";
                        strSQL+=" LEFT OUTER JOIN( \n";
                        strSQL+="       SELECT a1.co_com, \n";
                        strSQL+="              COUNT(a1.co_doc) as DevVen_cantDoc, sum(a2.registros) as DevVen_registros, SUM(a1.nd_tot)*-1 as DevVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('E','I') and a1.co_tipDoc=229   \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_com IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc   \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc )  \n";
                        strSQL+="       GROUP BY  a1.co_com  \n";
                        strSQL+=" ) as a5 ON (a2.co_usr=a5.co_com) \n";
                        strSQL+=" WHERE a6.st_ven='S'  AND (a3.co_ven IS NOT NULL) \n";
                        strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom, a3.co_ven, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos, \n";
                        strSQL+="          a4.co_com, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="          a5.co_com, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos   \n";
                        strSQL+=" ORDER BY a1.tx_nom \n";
                   }
                    
                }
                else{
                    if(chkMosCli.isSelected()){
                        strSQL="";
                        strSQL+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom,  \n";
                        strSQL+="        a3.co_usrIng,a3.co_cli, a3.tx_nomCli, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos,  \n";
                        strSQL+="        a4.co_usrIng, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="        a5.co_usrIng, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos  \n";
                        strSQL+=" FROM tbm_usr as a1  \n";
                        strSQL+=" INNER JOIN tbr_locUsr as a2 ON (a1.co_usr=a2.co_usr) \n";
                        strSQL+=" INNER JOIN tbr_usrEmp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_usr=a6.co_usr) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_usrIng , a2.co_cli,a2.tx_nomCli, \n";
                        strSQL+="              COUNT(a1.co_cot) as CotVen_cantDoc, sum(a2.registros) as CotVen_registros, SUM(a1.nd_tot) as CotVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabCotVen as a1  \n";
                        strSQL+="       INNER JOIN (  \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_cot,a3.co_cli, a3.tx_nom as tx_nomCli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabCotVen as a1  \n";
                        strSQL+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('I') \n";
                        strSQL+=strConSQL; strSQL+=strAuxFecCot; strSQL+=strAuxCli; strSQL+=strAuxVenCot;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_cot,a3.co_cli, a3.tx_nom \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="       GROUP BY  a1.co_usrIng, a2.co_cli,a2.tx_nomCli \n";
                        strSQL+=" )as a3 ON (a2.co_usr=a3.co_usrIng ) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_usrIng ,a2.co_cli, \n";
                        strSQL+="              COUNT(a1.co_doc) as FacVen_cantDoc, sum(a2.registros) as FacVen_registros, SUM(a1.nd_tot)*-1 as FacVen_valorDocumentos  \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a3.co_cli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE  a1.st_reg NOT IN ('E','I') AND a1.co_tipDoc=228    \n";
                        strSQL+=strAuxFecDoc;  strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ,a3.co_cli \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSQL+="       GROUP BY  a1.co_usrIng ,a2.co_cli  \n";
                        strSQL+=" ) as a4 ON (a2.co_usr=a4.co_usrIng  AND a3.co_cli=a4.co_cli) \n";
                        strSQL+=" LEFT OUTER JOIN( \n";
                        strSQL+="       SELECT a1.co_usrIng, a2.co_cli, \n";
                        strSQL+="              COUNT(a1.co_doc) as DevVen_cantDoc, sum(a2.registros) as DevVen_registros, SUM(a1.nd_tot)*-1 as DevVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a3.co_cli , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('E','I') and a1.co_tipDoc=229   \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc ,a3.co_cli  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc )  \n";
                        strSQL+="       GROUP BY  a1.co_usrIng , a2.co_cli \n";
                        strSQL+=" ) as a5 ON (a2.co_usr=a5.co_usrIng AND a4.co_cli=a5.co_cli) \n";
                        strSQL+=" WHERE (a3.co_usrIng IS NOT NULL) \n";
                        strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom, a3.co_usrIng,a3.co_cli, a3.tx_nomCli,  \n";
                        strSQL+="          a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos, \n";
                        strSQL+="          a4.co_usrIng, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="          a5.co_usrIng, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos   \n";
                        strSQL+=" ORDER BY a1.tx_nom \n";
                    }
                    else{
                        strSQL="";
                        strSQL+=" SELECT a1.co_usr, a1.tx_usr, a1.tx_nom,  \n";
                        strSQL+="        a3.co_usrIng, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos,  \n";
                        strSQL+="        a4.co_usrIng, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="        a5.co_usrIng, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos  \n";
                        strSQL+=" FROM tbm_usr as a1  \n";
                        strSQL+=" INNER JOIN tbr_locUsr as a2 ON (a1.co_usr=a2.co_usr) \n";
                        strSQL+=" INNER JOIN tbr_usrEmp as a6 ON (a2.co_emp=a6.co_emp AND a2.co_usr=a6.co_usr) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_usrIng , \n";
                        strSQL+="              COUNT(a1.co_cot) as CotVen_cantDoc, sum(a2.registros) as CotVen_registros, SUM(a1.nd_tot) as CotVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabCotVen as a1  \n";
                        strSQL+="       INNER JOIN (  \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_cot , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabCotVen as a1  \n";
                        strSQL+="           INNER JOIN tbm_detCotVen as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('I') \n";
                        strSQL+=strConSQL; strSQL+=strAuxFecCot;  strSQL+=strAuxCli; strSQL+=strAuxVenCot;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_cot  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_cot=a2.co_cot) \n";
                        strSQL+="       GROUP BY  a1.co_usrIng \n";
                        strSQL+=" )as a3 ON (a2.co_usr=a3.co_usrIng ) \n";
                        strSQL+=" LEFT OUTER JOIN ( \n";
                        strSQL+="       SELECT a1.co_usrIng , \n";
                        strSQL+="              COUNT(a1.co_doc) as FacVen_cantDoc, sum(a2.registros) as FacVen_registros, SUM(a1.nd_tot)*-1 as FacVen_valorDocumentos  \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE  a1.st_reg NOT IN ('E','I') AND a1.co_tipDoc=228    \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc  \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) \n";
                        strSQL+="       GROUP BY  a1.co_usrIng  \n";
                        strSQL+=" ) as a4 ON (a2.co_usr=a4.co_usrIng) \n";
                        strSQL+=" LEFT OUTER JOIN( \n";
                        strSQL+="       SELECT a1.co_usrIng, \n";
                        strSQL+="              COUNT(a1.co_doc) as DevVen_cantDoc, sum(a2.registros) as DevVen_registros, SUM(a1.nd_tot)*-1 as DevVen_valorDocumentos   \n";
                        strSQL+="       FROM tbm_cabMovInv as a1  \n";
                        strSQL+="       INNER JOIN ( \n";
                        strSQL+="           SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc , count(a2.co_reg) as registros \n";
                        strSQL+="           FROM tbm_cabMovInv as a1  \n";
                        strSQL+="           INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)  \n";
                        strSQL+="           INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                        strSQL+="           WHERE a1.st_reg NOT IN ('E','I') and a1.co_tipDoc=229   \n";
                        strSQL+=strAuxFecDoc; strSQL+=strConSQL; strSQL+=strAuxCli; strSQL+=strAuxVen;
                        if(objParSis.getCodigoUsuario()!=1){
                                strSQL+=" AND a1.co_usrIng IN ( \n";
                                strSQL+="                   SELECT a1.co_usrRel \n";
                                strSQL+="                   FROM tbr_usrLocPrgUsr as a1 \n";
                                strSQL+="                   WHERE a1.co_emp="+objParSis.getCodigoEmpresa()+" AND a1.co_loc="+objParSis.getCodigoLocal()+" AND \n";
                                strSQL+="                         a1.co_mnu="+objParSis.getCodigoMenu()+" AND a1.co_usr="+objParSis.getCodigoUsuario()+"  \n";
                                strSQL+="                   )  \n";
                        }
                        strSQL+="           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc   \n";
                        strSQL+="       ) as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc )  \n";
                        strSQL+="       GROUP BY  a1.co_usrIng  \n";
                        strSQL+=" ) as a5 ON (a2.co_usr=a5.co_usrIng) \n";
                        strSQL+=" WHERE (a3.co_usrIng IS NOT NULL) \n";
                        strSQL+=" GROUP BY a1.co_usr, a1.tx_usr, a1.tx_nom, a3.co_usrIng, a3.CotVen_cantDoc, a3.CotVen_registros, a3.CotVen_valorDocumentos, \n";
                        strSQL+="          a4.co_usrIng, a4.FacVen_cantDoc, a4.FacVen_registros, a4.FacVen_valorDocumentos,  \n";
                        strSQL+="          a5.co_usrIng, a5.DevVen_cantDoc, a5.DevVen_registros, a5.DevVen_valorDocumentos   \n";
                        strSQL+=" ORDER BY a1.tx_nom \n";
                   }
                }
                strSQL+=" \n";
                System.out.println("---> "+strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        if(rdoPorVendedor.isSelected()){
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_VEN,rst.getString("co_usr"));
                            vecReg.add(INT_TBL_DAT_NOM_VEN,rst.getString("tx_nom"));
                            if(chkMosCli.isSelected()){
                                vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                                vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_COD_CLI,"");
                                vecReg.add(INT_TBL_DAT_NOM_CLI,"");
                            }
                           

                            vecReg.add(INT_TBL_DAT_COT_VEN_NUM_TOT_DOC,rst.getString("CotVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_NUM_TOT_FIL,rst.getString("CotVen_registros"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC,rst.getString("CotVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC,"");

                            vecReg.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC,rst.getString("FacVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,rst.getString("FacVen_registros"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC,rst.getString("FacVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC,"");

                            vecReg.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC,rst.getString("DevVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,rst.getString("DevVen_registros"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC,rst.getString("DevVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC,"");
                            vecDat.add(vecReg);
                        }
                        else{
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_DAT_LIN,"");
                            vecReg.add(INT_TBL_DAT_COD_VEN,rst.getString("co_usr"));
                            vecReg.add(INT_TBL_DAT_NOM_VEN,rst.getString("tx_nom"));
                            if(chkMosCli.isSelected()){
                                vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                                vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                            }
                            else{
                                vecReg.add(INT_TBL_DAT_COD_CLI,"");
                                vecReg.add(INT_TBL_DAT_NOM_CLI,"");
                            }
                           

                            vecReg.add(INT_TBL_DAT_COT_VEN_NUM_TOT_DOC,rst.getString("CotVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_NUM_TOT_FIL,rst.getString("CotVen_registros"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_VAL_TOT_DOC,rst.getString("CotVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_COT_VEN_BTN_LIS_DOC,"");

                            vecReg.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC,rst.getString("FacVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL,rst.getString("FacVen_registros"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC,rst.getString("FacVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC,"");

                            vecReg.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC,rst.getString("DevVen_cantDoc"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL,rst.getString("DevVen_registros"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC,rst.getString("DevVen_valorDocumentos"));
                            vecReg.add(INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC,"");
                            vecDat.add(vecReg);
                        }
                        
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("380");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
             
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT '' AS co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cli='S' ";
                strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.st_reg='A' AND a1.st_cli='S' AND a2.co_emp="+objParSis.getCodigoEmpresa()+" ";
                strSQL+=" GROUP BY a1.co_cli, a1.tx_ide, a1.tx_nom";
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
            arlAncCol.add("360");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1){
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                strSQL+=" FROM tbm_usr AS a1 \n";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A' /* AND a2.st_ven='S'*/ \n";
                strSQL+=" ORDER BY a1.tx_nom \n";
            }
            else{
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom \n";
                strSQL+=" FROM tbm_usr AS a1 \n";
                strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr) \n";
                strSQL+=" INNER JOIN tbr_usrLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_usr=a3.co_usrRel)  \n";
                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa()+" AND a3.co_loc="+objParSis.getCodigoLocal()+" AND a3.co_mnu="+objParSis.getCodigoMenu()+" AND \n";                
                strSQL+="       a3.co_usr="+objParSis.getCodigoUsuario()+" /* AND a1.st_reg='A' AND a2.st_ven='S'*/ \n";
                strSQL+=" ORDER BY a1.tx_nom \n";
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
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Identificación".
                    if (vcoCli.buscar("a1.tx_ide", txtIdeCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(7);
                        vcoCli.cargarDatos();
                        vcoCli.setVisible(true);
                        if (vcoCli.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            txtIdeCli.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
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
                            txtIdeCli.setText(vcoCli.getValueAt(2));
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
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        //Validar que esté seleccionado al menos un local.
        if (!objTblModLoc.isCheckedAnyRow(INT_TBL_LOC_CHK))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Es obligatorio seleccionar al menos un local.<BR>Por favor corrija y vuelva a intentarlo.</HTML>");
            if (objTblModLoc.getRowCount()>0)
            {
                tblLoc.setRowSelectionInterval(0, 0);
                tblLoc.changeSelection(0, INT_TBL_LOC_CHK, false, false);
                tblLoc.requestFocus();
            }
            return false;
        }
        return true;
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
            if(agregarColTblDat()){
                if (!cargarDetReg())
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
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
                case INT_TBL_DAT_COD_VEN:
                    strMsg="Código del Vendedor";
                    break;
                case INT_TBL_DAT_NOM_VEN:
                    strMsg="Nombre del vendedor";
                    break;
                 
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                    
                case INT_TBL_DAT_COT_VEN_NUM_TOT_DOC:
                    strMsg="Cotizaciones de Venta: Número total de documentos";
                    break;
                case INT_TBL_DAT_COT_VEN_NUM_TOT_FIL:
                    strMsg="Cotizaciones de Venta: Número total de filas";
                    break;
                case INT_TBL_DAT_COT_VEN_VAL_TOT_DOC:
                    strMsg="Cotizaciones de Venta: Valor total de los documentos";
                    break;
                case INT_TBL_DAT_COT_VEN_BTN_LIS_DOC:
                    strMsg="Muestra el Listado de cotizaciones de ventas";
                    break;    
                    
                case INT_TBL_DAT_FAC_VEN_NUM_TOT_DOC:
                    strMsg="Facturas de Venta: Número total de documentos";
                    break;
                case INT_TBL_DAT_FAC_VEN_NUM_TOT_FIL:
                    strMsg="Facturas de Venta: Número total de filas";
                    break;
                case INT_TBL_DAT_FAC_VEN_VAL_TOT_DOC:
                    strMsg="Facturas de Venta: Valor total de los documentos";
                    break;
                case INT_TBL_DAT_FAC_VEN_BTN_LIS_DOC:
                    strMsg="Muestra el Listado de Facturas de ventas";
                    break;     
          
                case INT_TBL_DAT_DEV_VEN_NUM_TOT_DOC:
                    strMsg="Devoluciones de Venta: Número total de documentos";
                    break;
                case INT_TBL_DAT_DEV_VEN_NUM_TOT_FIL:
                    strMsg="Devoluciones de Venta: Número total de filas";
                    break;
                case INT_TBL_DAT_DEV_VEN_VAL_TOT_DOC:
                    strMsg="Devoluciones de Venta: Valor total de los documentos";
                    break;
                case INT_TBL_DAT_DEV_VEN_BTN_LIS_DOC:
                    strMsg="Muestra el Listado de Devoluciones de ventas";
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
    
}
