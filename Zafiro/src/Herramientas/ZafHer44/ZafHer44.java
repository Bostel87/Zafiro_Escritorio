/*
 * ZafHer42.java
 *
 * Created on 14 de abril de 2008, 12:09 PM
 */
package Herramientas.ZafHer44;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
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
public class ZafHer44 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.

    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_EMP=1;                     //Código de la empresa.
    static final int INT_TBL_DAT_COD_LOC=2;                     //Código del local.
    static final int INT_TBL_DAT_COD_COT=3;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_FEC_DOC=4;                     //Fecha del documento.
    static final int INT_TBL_DAT_COD_CLI=5;                     //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=6;                    //Nombre del cliente.
    static final int INT_TBL_DAT_VAL_DOC=7;                    //Valor del documento.
    static final int INT_TBL_DAT_BUT_DOC=8;                    //Botón del documento.
    static final int INT_TBL_DAT_COD_REG=9;                  //Código de registro.
    static final int INT_TBL_DAT_COD_CON=10;                 //Código del control.
    static final int INT_TBL_DAT_NOM_CON=11;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_AUT_DOC=12;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEN_DOC=13;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_COD_USR=14;                 //Código del usuario.
    static final int INT_TBL_DAT_NOM_USR=15;                 //Nombre de usuario.
    static final int INT_TBL_DAT_FEC_AUT=16;                 //Fecha de autorización de documento.
    static final int INT_TBL_DAT_COM_AUT=17;                 //Computadora por la cual fue autorizado.
    
     
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
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoForPag;                                //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private Vector vecEstReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strIdeCli, strDesLarCli;          //Contenido del campo al obtener el foco.
    private String strCodForPag, strForPag;                     //Contenido del campo al obtener el foco.
    private String strVersion=" v0.02";
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    //Variables de la clase.
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;                                      //Código del docuemnto.
    private int intCodAut;                                      //Código de la autorización.
    
    private ZafTblOrd  objTblOrdLoc;                  //JTable de ordenamiento.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafHer44(ZafParSis obj) 
    {
        initComponents();
        //Inicializar objetos.
        objParSis=obj;
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
        panFil1 = new javax.swing.JPanel();
        chkMosLocIna = new javax.swing.JCheckBox();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        panFil2 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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

        panFil.setLayout(new java.awt.BorderLayout());

        panFil1.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Locales"));
        panFil1.setPreferredSize(new java.awt.Dimension(10, 92));
        panFil1.setLayout(new java.awt.BorderLayout());

        chkMosLocIna.setText("Mostrar locales inactivos");
        chkMosLocIna.setPreferredSize(new java.awt.Dimension(145, 14));
        chkMosLocIna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosLocInaActionPerformed(evt);
            }
        });
        panFil1.add(chkMosLocIna, java.awt.BorderLayout.SOUTH);

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

        panFil1.add(spnLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panFil1, java.awt.BorderLayout.NORTH);
        panFil1.getAccessibleContext().setAccessibleName("Listado de Locales");

        panFil2.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todas las cotizaciones de ventas");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil2.add(optTod);
        optTod.setBounds(4, 76, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo las cotizaciones que cumplan el criterio seleccionado");
        panFil2.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panFil2.add(lblCli);
        lblCli.setBounds(24, 116, 120, 20);

        lblForPag.setText("Forma de pago:");
        lblForPag.setToolTipText("Forma de pago");
        panFil2.add(lblForPag);
        lblForPag.setBounds(24, 136, 120, 20);

        txtCodForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodForPagActionPerformed(evt);
            }
        });
        txtCodForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodForPagFocusLost(evt);
            }
        });
        panFil2.add(txtCodForPag);
        txtCodForPag.setBounds(144, 136, 56, 20);

        txtForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtForPagActionPerformed(evt);
            }
        });
        txtForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtForPagFocusLost(evt);
            }
        });
        panFil2.add(txtForPag);
        txtForPag.setBounds(200, 136, 460, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panFil2.add(butForPag);
        butForPag.setBounds(660, 136, 20, 20);

        txtCodCli.setToolTipText("Código del cliente/proveedor");
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
        panFil2.add(txtCodCli);
        txtCodCli.setBounds(144, 116, 56, 20);

        txtIdeCli.setToolTipText("Identificación del cliente/proveedor");
        txtIdeCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdeCliActionPerformed(evt);
            }
        });
        txtIdeCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtIdeCliFocusLost(evt);
            }
        });
        panFil2.add(txtIdeCli);
        txtIdeCli.setBounds(200, 116, 100, 20);

        txtDesLarCli.setToolTipText("Nombre del cliente/proveedor");
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
        panFil2.add(txtDesLarCli);
        txtDesLarCli.setBounds(300, 116, 360, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil2.add(butCli);
        butCli.setBounds(660, 116, 20, 20);

        panFil.add(panFil2, java.awt.BorderLayout.CENTER);

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

        setBounds(0, 0, 700, 450);
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

    private void txtIdeCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusGained
        strIdeCli=txtIdeCli.getText();
        txtIdeCli.selectAll();
    }//GEN-LAST:event_txtIdeCliFocusGained

    private void txtIdeCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdeCliActionPerformed
        txtIdeCli.transferFocus();
    }//GEN-LAST:event_txtIdeCliActionPerformed

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

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag))
        {
            if (txtCodForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtForPag.setText("");
            }
            else
            {
                mostrarVenConForPag(1);
            }
        }
        else
            txtCodForPag.setText(strCodForPag);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodForPag.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void txtForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtForPag.getText().equalsIgnoreCase(strForPag))
        {
            if (txtForPag.getText().equals(""))
            {
                txtCodForPag.setText("");
                txtForPag.setText("");
            }
            else
            {
                mostrarVenConForPag(2);
            }
        }
        else
            txtForPag.setText(strForPag);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodForPag.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtForPagFocusLost

    private void txtForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtForPagFocusGained
        strForPag=txtForPag.getText();
        txtForPag.selectAll();
    }//GEN-LAST:event_txtForPagFocusGained

    private void txtForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtForPagActionPerformed
        txtForPag.transferFocus();
    }//GEN-LAST:event_txtForPagActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodCli.setText("");
            txtIdeCli.setText("");
            txtDesLarCli.setText("");
            txtCodForPag.setText("");
            txtForPag.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            abrirFrm();
            evt.setKeyCode(0);
        }
    }//GEN-LAST:event_tblDatKeyPressed

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

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            abrirFrm();
        }
    }//GEN-LAST:event_tblDatMouseClicked

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

    private void chkMosLocInaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosLocInaActionPerformed
        cargarLoc();
    }//GEN-LAST:event_chkMosLocInaActionPerformed

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
    private javax.swing.JButton butForPag;
    private javax.swing.JCheckBox chkMosLocIna;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil1;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtIdeCli;
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
            panFil2.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
//            txtCodTipDoc.setVisible(false);
            //Configurar el combo "Estado de registro".
             
            
            //Configurar las ZafVenCon.
            configurarVenConCli();
            configurarVenConForPag();
            //Configurar los JTables.
            configurarTblDat();
        
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodCli.setEditable(false);
                txtCodCli.setBackground(new java.awt.Color(255,255,255));
                lblForPag.setVisible(false);
                txtCodForPag.setVisible(false);
                txtForPag.setVisible(false);
                butForPag.setVisible(false);
                
                configurarTblLoc();
                cargarLoc();
            }
            else
            {
                panFil1.setVisible(false);
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
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_COT,"Cód.Cot.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_BUT_DOC,"");           
            vecCab.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_COD_CON,"Cód.Con.");
            vecCab.add(INT_TBL_DAT_NOM_CON,"Control.");
            vecCab.add(INT_TBL_DAT_AUT_DOC,"Autorizar");
            vecCab.add(INT_TBL_DAT_DEN_DOC,"Denegar");
            vecCab.add(INT_TBL_DAT_COD_USR,"Cód.Usr.");
            vecCab.add(INT_TBL_DAT_NOM_USR,"Usuario");
            vecCab.add(INT_TBL_DAT_FEC_AUT,"Fec.Aut.");
            vecCab.add(INT_TBL_DAT_COM_AUT,"Computadora");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_COT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(60);         
            tcmAux.getColumn(INT_TBL_DAT_COD_CON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CON).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_AUT_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEN_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USR).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FEC_AUT).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_COM_AUT).setPreferredWidth(100);
            
            
           
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CON, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR, tblDat);
             
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_DOC);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_COT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_AUT_DOC).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_DEN_DOC).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    abrirFrm();
                }
            });
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
             
            //Configurar JTable: Establecer el modo de operación.
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int i;
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
                //Obtener los locales a consultar.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    int intNumFilTblBod=objTblModLoc.getRowCountTrue();
                    i=0;
                    strAux="";
                    for (int j=0; j<intNumFilTblBod; j++)
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
                }
               
                switch (objSelFec.getTipoSeleccion())
                {
                    case 0: //Búsqueda por rangos
                        strConSQL+=" AND a1.fe_cot BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strConSQL+=" AND a1.fe_cot<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strConSQL+=" AND a1.fe_cot>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    if (txtIdeCli.getText().length()>0)
                        strConSQL+=" AND a2.tx_ide='" + txtIdeCli.getText() + "'";
                }
                else
                {
                    if (txtCodCli.getText().length()>0)
                        strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                }
                if (txtCodForPag.getText().length()>0)
                    strConSQL+=" AND a1.co_forPag=" + txtCodForPag.getText();
                 
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot , a1.co_cli, a2.tx_nom AS tx_nomCli, a1.nd_tot, \n";
                    strSQL+="        a4.co_reg, a4.co_regNeg,a4.st_reg , a5.tx_desCor, a5.tx_desLar, \n";
                    strSQL+="        a4.fe_aut, a4.co_usrAut, a4.tx_comAut, a6.tx_usr as tx_nomUsr \n ";
                    strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="       SELECT MAX(a3.co_aut) as co_aut, a1.co_emp,a1.co_loc, a1.co_cot  \n";
                    strSQL+="       FROM tbm_cabCotVen as a1  \n";
                    strSQL+="       INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                    strSQL+="       INNER JOIN tbm_cabAutCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot)  \n";
                    strSQL+="       WHERE a3.st_reg='A' \n";
                    strSQL+=strConSQL+" \n";
                    strSQL+="       GROUP BY a1.co_emp,a1.co_loc, a1.co_cot \n";
                    strSQL+=" ) as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot) \n";
                    strSQL+=" INNER JOIN tbm_detAutCotVen AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_cot=a4.co_cot AND a3.co_aut=a4.co_aut AND a4.st_cum='N')  \n";
                    strSQL+=" INNER JOIN tbm_regNeg as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_regNeg=a5.co_reg) \n";
                    strSQL+=" LEFT OUTER JOIN tbm_usr as a6 ON (a4.co_usrAut=a6.co_usr) \n";
                    strSQL+=" WHERE 1=1 \n";
                    strSQL+=strConSQL+" \n";;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot \n";
                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_cot, a1.fe_cot , a1.co_cli, a2.tx_nom AS tx_nomCli, a1.nd_tot, \n";
                    strSQL+="        a4.co_reg, a4.co_regNeg,a4.st_reg , a5.tx_desCor, a5.tx_desLar,     \n";
                    strSQL+="        a4.fe_aut, a4.co_usrAut, a4.tx_comAut, a6.tx_usr as tx_nomUsr \n ";
                    strSQL+=" FROM tbm_cabCotVen AS a1  \n";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                    strSQL+=" INNER JOIN ( \n";
                    strSQL+="       SELECT MAX(a3.co_aut) as co_aut, a1.co_emp,a1.co_loc, a1.co_cot  \n";
                    strSQL+="       FROM tbm_cabCotVen as a1  \n";
                    strSQL+="       INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)  \n";
                    strSQL+="       INNER JOIN tbm_cabAutCotVen as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot)  \n";
                    strSQL+="       WHERE a3.st_reg='A' AND a1.co_emp=" + objParSis.getCodigoEmpresa() +" AND a1.co_loc=" + objParSis.getCodigoLocal()+" \n";
                    strSQL+=strConSQL+" \n";
                    strSQL+="       GROUP BY a1.co_emp,a1.co_loc, a1.co_cot \n";
                    strSQL+=" ) as a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_cot=a3.co_cot) \n";
                    strSQL+=" INNER JOIN tbm_detAutCotVen AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_cot=a4.co_cot AND a3.co_aut=a4.co_aut AND a4.st_cum='N')  \n";
                    strSQL+=" INNER JOIN tbm_regNeg as a5 ON (a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_regNeg=a5.co_reg) \n";
                    strSQL+=" LEFT OUTER JOIN tbm_usr as a6 ON (a4.co_usrAut=a6.co_usr) \n";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() +" AND a1.co_loc=" + objParSis.getCodigoLocal()+" \n";
                    strSQL+=strConSQL+" \n";;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_cot \n";
                }
                System.out.println("ZafHer44:: \n " + strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_EMP,rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_COT,rst.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_cot"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BUT_DOC,null);
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_COD_CON,rst.getString("co_regNeg"));
                        vecReg.add(INT_TBL_DAT_NOM_CON,rst.getString("tx_desCor"));
                        strAux=rst.getString("st_reg");
                        if (strAux.equals("A")){
                            vecReg.add(INT_TBL_DAT_AUT_DOC,new Boolean(true));
                            vecReg.add(INT_TBL_DAT_DEN_DOC,null);
                        }
                        else if (strAux.equals("D")){
                            vecReg.add(INT_TBL_DAT_AUT_DOC,null);
                            vecReg.add(INT_TBL_DAT_DEN_DOC,new Boolean(true));
                        }
                        else{
                            vecReg.add(INT_TBL_DAT_AUT_DOC,null);
                            vecReg.add(INT_TBL_DAT_DEN_DOC,null);
                        }
                        vecReg.add(INT_TBL_DAT_COD_USR,rst.getString("co_usrAut"));
                        vecReg.add(INT_TBL_DAT_NOM_USR,rst.getString("tx_nomUsr"));
                        vecReg.add(INT_TBL_DAT_FEC_AUT,rst.getString("fe_aut"));
                        vecReg.add(INT_TBL_DAT_COM_AUT,rst.getString("tx_comAut"));
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

    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedColumn()==INT_TBL_DAT_BUT_DOC)
            {
                invocarClase("Ventas.ZafVen01.ZafVen01");
            }       
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
            Class objCla[]=new Class[4];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            objCla[2]=new Integer(0).getClass();
            objCla[3]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            intFilSel=tblDat.getSelectedRow();
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[4];
            objObj[0]=objParSis;
            objObj[1]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_EMP).toString());
            objObj[2]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_LOC).toString());
            objObj[3]=new Integer(objTblMod.getValueAt(intFilSel,INT_TBL_DAT_COD_COT).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT ' ' AS co_cli, b2.tx_ide, b2.tx_nom, b2.tx_dir";
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide";
                strSQL+=" FROM (";
                strSQL+=" SELECT MIN(co_emp) AS co_emp, tx_ide";
                strSQL+=" FROM tbm_cli";
                strSQL+=" GROUP BY tx_ide";
                strSQL+=" ) AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)";
                strSQL+=" GROUP BY a2.co_emp, a2.tx_ide";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                strSQL+=" ORDER BY b2.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" ORDER BY a1.tx_nom";
            }
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Formas de pago".
     */
    private boolean configurarVenConForPag()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_forPag");
            arlCam.add("a1.tx_des");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("300");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forPag, a1.tx_des";
            strSQL+=" FROM tbm_cabForPag AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_des";
            vcoForPag=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de formas de pago", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoForPag.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
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
    private boolean mostrarVenConForPag(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoForPag.setCampoBusqueda(2);
                    vcoForPag.show();
                    if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoForPag.buscar("a1.tx_des", txtForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.show();
                        if (vcoForPag.getSelectedButton()==vcoForPag.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtForPag.setText(strForPag);
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
                 
                case INT_TBL_DAT_COD_COT:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_BUT_DOC:
                    strMsg="Abrir documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
