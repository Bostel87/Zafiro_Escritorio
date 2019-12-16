/*
 * ZafVen31.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package Ventas.ZafVen31;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author  Eddye Lino
 */
public class ZafVen31 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_NUM_DOC=6;                     //Número del documento (Preimpreso).
    static final int INT_TBL_DAT_FEC_DOC=7;                     //Fecha del documento.
    static final int INT_TBL_DAT_COD_CLI=8;                     //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=9;                     //Nombre del cliente.
    static final int INT_TBL_DAT_CHK=10;
    static final int INT_TBL_DAT_OBS=11;
    static final int INT_TBL_DAT_BUT_OBS=12;
    static final int INT_TBL_DAT_FEC_REC=13;
    static final int INT_TBL_DAT_COD_USR_REC=14;
    static final int INT_TBL_DAT_USR_REC=15;
    static final int INT_TBL_DAT_CHK_AUX=16;


    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.

    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.


    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAux;
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkAux;
    
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiBut;
    private ZafVen31_01 objVen31_01;



    private ArrayList arlRegDiaAnt, arlDatDiaAnt;
    private int INT_ARL_DIA_ANT_COD_EMP=0;
    private int INT_ARL_DIA_ANT_COD_CTA=1;
    private int INT_ARL_DIA_ANT_MON_DEB=2;
    private int INT_ARL_DIA_ANT_MON_HAB=3;
    private int INT_ARL_DIA_ANT_EST_REG=4;
    private int INT_ARL_DIA_ANT_EST_CON_BAN=5;

    private ArrayList arlRegDiaAct, arlDatDiaAct;
    private int INT_ARL_DIA_ACT_COD_EMP=0;
    private int INT_ARL_DIA_ACT_COD_CTA=1;
    private int INT_ARL_DIA_ACT_MON_DEB=2;
    private int INT_ARL_DIA_ACT_MON_HAB=3;
    private int INT_ARL_DIA_ACT_EST_REG=4;
    private int INT_ARL_DIA_ACT_EST_CON_BAN=5;
                


    private int intNumAniDoc;
    private int intNumMesDoc;


    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafVen31(ZafParSis obj)
    {
        try {
            objParSis=(ZafParSis) obj.clone();
            initComponents();
            //Inicializar objetos.
            objPerUsr=new ZafPerUsr(objParSis);
            arlDatDiaAnt=new ArrayList();
            arlDatDiaAct=new ArrayList();
        }
        catch (CloneNotSupportedException e) {
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
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        chkMosNotRec = new javax.swing.JCheckBox();
        chkMosRec = new javax.swing.JCheckBox();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todas los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 114, 400, 16);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 130, 400, 16);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(26, 170, 110, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(118, 170, 32, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 170, 56, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 170, 460, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(660, 170, 20, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(26, 190, 120, 20);

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
        txtCodCli.setBounds(150, 190, 56, 20);

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
        txtDesLarCli.setBounds(200, 190, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 190, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFil.add(lblLoc);
        lblLoc.setBounds(26, 150, 120, 20);

        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        panFil.add(txtCodLoc);
        txtCodLoc.setBounds(150, 150, 56, 20);

        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panFil.add(txtNomLoc);
        txtNomLoc.setBounds(200, 150, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(660, 150, 20, 20);

        chkMosNotRec.setSelected(true);
        chkMosNotRec.setText("Mostrar los documentos que aún no han sido recibidos");
        panFil.add(chkMosNotRec);
        chkMosNotRec.setBounds(4, 80, 530, 14);

        chkMosRec.setText("Mostrar los documentos que ya fueron recibidos");
        panFil.add(chkMosRec);
        chkMosRec.setBounds(4, 96, 530, 14);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

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
        txtNomCliDes.setBounds(56, 20, 260, 20);

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
        txtNomCliHas.setBounds(380, 20, 260, 20);

        panFil.add(panNomCli);
        panNomCli.setBounds(24, 218, 650, 52);

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

        butGua.setText("Guardar");
        butGua.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

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

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodTipDoc.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
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

    private void txtNomCliDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusGained
        txtNomCliDes.selectAll();
}//GEN-LAST:event_txtNomCliDesFocusGained

    private void txtNomCliDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliDesFocusLost
        if (txtNomCliDes.getText().length()>0) {
            optFil.setSelected(true);
            if (txtNomCliHas.getText().length()==0)
                txtNomCliHas.setText(txtNomCliDes.getText());
        }
}//GEN-LAST:event_txtNomCliDesFocusLost

    private void txtNomCliHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusGained
        txtNomCliHas.selectAll();
}//GEN-LAST:event_txtNomCliHasFocusGained

    private void txtNomCliHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliHasFocusLost
        if (txtNomCliHas.getText().length()>0)
            optFil.setSelected(true);
}//GEN-LAST:event_txtNomCliHasFocusLost

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        if(guardar()){
            mostrarMsgInf("<HTML>La información se guardó correctamente.</HTML>");
            cargarDetReg();
        }
        else{
            mostrarMsgInf("<HTML>La información no se pudo guardar.</HTML>");
        }

    }//GEN-LAST:event_butGuaActionPerformed

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
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkMosNotRec;
    private javax.swing.JCheckBox chkMosRec;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTipDoc;
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
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtNomLoc;
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
            this.setTitle(strAux + "v0.1.2");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);            
            //Configurar las ZafVenCon.
            configurarVenConLoc();
            configurarVenConTipDoc();
            configurarVenConCli();
            //Configurar los JTables.
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
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Código");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_BUT_OBS,"");
            vecCab.add(INT_TBL_DAT_FEC_REC,"Fec.Rec.");
            vecCab.add(INT_TBL_DAT_COD_USR_REC,"Cod.Usr.Rec.");
            vecCab.add(INT_TBL_DAT_USR_REC,"Usr.Rec.");
            vecCab.add(INT_TBL_DAT_CHK_AUX,"");

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
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(157);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_USR_REC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_USR_REC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUX).setPreferredWidth(20);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_REC, tblDat);

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
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAux=new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUX).setCellRenderer(objTblCelRenChkAux);
            objTblCelRenChkAux=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);

            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strNomCliFilSel="";
                String strNomBenFilSel="";
                boolean blnIsChkBef;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUX)){
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }
                    else{
                        objTblCelEdiChk.setCancelarEdicion(false);
                    }
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAux=new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_AUX).setCellEditor(objTblCelEdiChkAux);
            objTblCelEdiChkAux=null;



            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;

            objVen31_01=new ZafVen31_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiBut= new ZafTblCelEdiButDlg(objVen31_01);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS).setCellEditor(objTblCelEdiBut);
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                String strPanObsEdi="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    intCol=tblDat.getSelectedColumn();

                    objTblCelEdiBut.setCancelarEdicion(false);
                    if(objTblMod.isChecked(intFil, INT_TBL_DAT_CHK_AUX)){
                        strPanObsEdi="S";
                    }
                    else{
                        strPanObsEdi="N";
                    }
                    objVen31_01.setContenido(""+objTblMod.getValueAt(intFil, INT_TBL_DAT_OBS).toString(), strPanObsEdi);

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblMod.setValueAt(objVen31_01.getContenido(), intFil, INT_TBL_DAT_OBS);
                }
            });

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_BUT_OBS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);


            if(objParSis.getCodigoUsuario()!=1){
                butCon.setEnabled(false);
                butGua.setEnabled(false);
                butCer.setEnabled(false);
            }
            
          //Recepcion de Facturas de Ventas
            if(objParSis.getCodigoMenu()==2227){
                if(objPerUsr.isOpcionEnabled(2228)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2229)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2230)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
            }

          //Recepcion de Devoluciones de Compras
            if(objParSis.getCodigoMenu()==2682){
                if(objPerUsr.isOpcionEnabled(2683)){
                    butCon.setEnabled(true);
                    butCon.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2684)){
                    butGua.setEnabled(true);
                    butGua.setVisible(true);
                }
                if(objPerUsr.isOpcionEnabled(2685)){
                    butCer.setEnabled(true);
                    butCer.setVisible(true);
                }
            }

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
        int intCodUsrRecDoc;
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
                if (txtCodLoc.getText().length()>0)
                    strConSQL+=" AND a1.co_loc=" + txtCodLoc.getText();
                if (txtCodTipDoc.getText().length()>0)
                    strConSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                if (txtCodCli.getText().length()>0)
                    strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
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
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" , a3.tx_desCor AS tx_desCorTipDoc, a3.tx_desLar AS tx_desLarTipDoc";
                    strSQL+=" , a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli";
                    strSQL+=" , a2.tx_obsrec, a2.fe_rec, a2.co_usrrec, a4.tx_usr";
                    strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3";
                    strSQL+=" 	ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                    strSQL+=")";
                    strSQL+=" LEFT OUTER JOIN";

                    strSQL+=" tbm_recMovInv AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a4";
                    strSQL+=" ON a2.co_usrrec=a4.co_usr";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="   AND a1.co_tipDoc IN(";
                        strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocPrg ";
                        strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                        strSQL+=" )";
                    }
                    else{
                        if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="   AND a1.co_tipDoc IN(";
                            strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocUsr ";
                            strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                            strSQL+=" )";
                        }
                        else{
                            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+="   AND a1.co_tipDoc IN(";
                            strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocUsr ";
                            strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                            strSQL+="                           AND co_usr=" + objParSis.getCodigoUsuario() + "";
                            strSQL+=" )";
                        }
                    }
                    if(  (chkMosNotRec.isSelected())  &&  (chkMosRec.isSelected()) )
                        strSQL+="";
                    else if(  (chkMosNotRec.isSelected())  &&  (!chkMosRec.isSelected()) )
                        strSQL+=" AND a1.ne_numVecRecDoc=0";
                    else if(  (!chkMosNotRec.isSelected())  &&  (chkMosRec.isSelected()) )
                        strSQL+=" AND a1.ne_numVecRecDoc>0";

                    strSQL+=strConSQL;
                    strSQL+=" AND a1.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a1.fe_doc, a1.tx_nomCli, a1.ne_numDoc";
                    System.out.println("strSQL: " + strSQL);
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
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCorTipDoc"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLarTipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_CHK, null);
                        vecReg.add(INT_TBL_DAT_OBS, rst.getObject("tx_obsrec")==null?"":rst.getString("tx_obsrec"));
                        vecReg.add(INT_TBL_DAT_BUT_OBS,"");
                        vecReg.add(INT_TBL_DAT_FEC_REC,rst.getString("fe_rec"));
                        intCodUsrRecDoc=Integer.parseInt(rst.getObject("co_usrrec")==null?"0":rst.getString("co_usrrec"));
                        vecReg.add(INT_TBL_DAT_COD_USR_REC,"" + intCodUsrRecDoc);
                        vecReg.add(INT_TBL_DAT_USR_REC,rst.getString("tx_usr"));
                        vecReg.add(INT_TBL_DAT_CHK_AUX,null);

                        if(intCodUsrRecDoc>0){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AUX);
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

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
    private boolean configurarVenConLoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("400");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" ORDER BY a1.co_loc";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.tx_nom";
                strSQL+=" FROM tbm_loc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" ORDER BY a1.co_loc";
            }
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 inner join tbr_tipDocUsr AS a3";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
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
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                if(objParSis.getCodigoMenu()==2227)
                    strSQL+=" AND a1.st_cli='S'";
                else if(objParSis.getCodigoMenu()==2682)
                    strSQL+=" AND a1.st_prv='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A'";
                if(objParSis.getCodigoMenu()==2227)
                    strSQL+=" AND a1.st_cli='S'";
                else if(objParSis.getCodigoMenu()==2682)
                    strSQL+=" AND a1.st_prv='S'";
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtNomLoc.setText(strNomLoc);
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
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.setVisible(true);
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.setVisible(true);
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
                        vcoCli.setVisible(true);
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
                        vcoCli.setVisible(true);
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
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
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
                    strMsg="Número del documento";
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
                case INT_TBL_DAT_CHK:
                    strMsg="";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación de la recepción";
                    break;
                case INT_TBL_DAT_BUT_OBS:
                    strMsg="Observación de la recepción";
                    break;

                case INT_TBL_DAT_FEC_REC:
                    strMsg="Fecha de la recepción";
                    break;
                case INT_TBL_DAT_COD_USR_REC:
                    strMsg="Código de usuario de la recepción";
                    break;
                case INT_TBL_DAT_USR_REC:
                    strMsg="Usuario que realizó la recepción";
                    break;
                case INT_TBL_DAT_CHK_AUX:
                    strMsg="Usado para saber si está o no ya realizada la recepción y de acuerdo a esto poder modificar";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }



    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardar(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(guardarDatos()){
                    con.commit();
                }
                else{
                    con.rollback();
                    blnRes=false;
                }
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean guardarDatos(){
        boolean blnRes=true;
        String strLin="";
        String strUpd="";

        int intCodEmp=-1;
        int intCodLoc=-1;
        int intCodTipDoc=-1;
        int intCodDoc=-1;

         try{
            if(con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strUpd="";
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            strSQL="";
                            strSQL+="UPDATE tbm_cabMovInv";
                            strSQL+=" SET ne_numVecRecDoc=1";
                            strSQL+=" , fe_ultRecDoc='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                            strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                            strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                            strSQL+=";";
                            strUpd+=strSQL;
                            //tbm_recMovInv
                            strSQL="";
                            strSQL+="INSERT INTO tbm_recMovInv(";
                            strSQL+="       co_emp, co_loc, co_tipdoc, co_doc, co_reg, fe_rec, tx_obsrec, co_usrrec, st_regrep)";
                            strSQL+="       (SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp";
                            strSQL+="         , " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + " AS co_loc";
                            strSQL+="         , " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + " AS co_tipdoc";
                            strSQL+="         , " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + " AS co_doc";
                            strSQL+="         , 1 AS co_reg";
                            strSQL+="         , " +  " '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "' AS fe_rec";
                            strSQL+="         , " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_OBS)) + " AS tx_obsrec";
                            strSQL+="         , "  + objParSis.getCodigoUsuario() + " AS co_usrrec";
                            strSQL+="         , 'I' AS st_regrep";
                            strSQL+="   WHERE NOT EXISTS(";
                            strSQL+="           SELECT *FROM tbm_recMovInv";
                            strSQL+="             WHERE co_emp=" + objParSis.getCodigoEmpresa();
                            strSQL+="             AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC);
                            strSQL+="             AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC);
                            strSQL+="             AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC);
                            strSQL+="));";
                            strUpd+=strSQL;

                            //cambiar asiento de diario si es una DEVCOM
                            if(objParSis.getCodigoMenu()==2682){

                                intCodEmp=objParSis.getCodigoEmpresa();
                                intCodLoc=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                                intCodTipDoc=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                                intCodDoc=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());

                                if(!isMesCerrado(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                    if(getDetalleDiarioAnterior(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){

                                        strSQL="";
                                        strSQL+="UPDATE tbm_detDia";
                                        strSQL+=" SET co_cta=x.co_ctaFin FROM(";
                                        strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a2.co_reg, a2.co_cta, a3.co_ctaTra, a3.co_ctaFin";
                                        strSQL+="	FROM tbm_cabDia AS a1";
                                        strSQL+="	INNER JOIN tbm_detDia AS a2";
                                        strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_dia=a2.co_dia";
                                        strSQL+=" 	INNER JOIN tbr_ctaTraFinTipDoc AS a3";
                                        strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_ctaTra";
                                        strSQL+="         WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                        strSQL+="         AND a1.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                                        strSQL+="         AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                        strSQL+="         AND a1.co_dia=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                                        strSQL+=" ) AS x";
                                        strSQL+=" WHERE tbm_detDia.co_emp=x.co_emp AND tbm_detDia.co_loc=x.co_loc AND tbm_detDia.co_tipDoc=x.co_tipDoc";
                                        strSQL+=" AND tbm_detDia.co_dia=x.co_dia AND tbm_detDia.co_reg=x.co_reg";
                                        strSQL+=" ;";
                                        strUpd+=strSQL;


                                    }
                                }
                            }



                            System.out.println("strUpd: " + strUpd);
                            stm.executeUpdate(strUpd);

                            if(objParSis.getCodigoMenu()==2682){
                                if(getDetalleDiarioActual(con, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc)){
                                    if(mayorizaCta(con, intCodEmp)){
                                    }
                                    else{
                                        blnRes=false;
                                    }
                                }
                                else{
                                    blnRes=false;
                                }
                            }




                        }

                    }


                }
                stm.close();
                stm=null;



            }

        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    private boolean getDetalleDiarioAnterior(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        arlDatDiaAnt.clear();
        String strSQLTmp;
        Statement stmMesCer;
        ResultSet rstMesCer;
        try{
            if (con!=null){
                stmMesCer=con.createStatement();
                //Armar la sentencia SQL.
                strSQLTmp="";
                strSQLTmp+="SELECT a1.co_cta, a2.tx_codCta, a2.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a3.st_reg";
                strSQLTmp+=" , a1.st_conban FROM tbm_plaCta AS a2 inner join (tbm_detDia AS a1";
                strSQLTmp+=" inner join tbm_cabdia as a3";
                strSQLTmp+=" on a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_dia=a3.co_dia";
                strSQLTmp+=" )";
                strSQLTmp+=" on a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQLTmp+=" AND a1.co_emp=" + intCodEmp;
                strSQLTmp+=" AND a1.co_loc=" + intCodLoc;
                strSQLTmp+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQLTmp+=" AND a1.co_dia=" + intCodDia;
                strSQLTmp+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                rstMesCer=stmMesCer.executeQuery(strSQLTmp);
                while (rstMesCer.next()){
                        //CAPTURA LOS DATOS INICIALES DE LOS SALDOS DEL DIARIO A MODIFICAR(ACTUALIZACION, ELIMINACION, ANULACION)
                        arlRegDiaAnt=new ArrayList();
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_COD_EMP, "" + intCodEmp);
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_COD_CTA, "" + rstMesCer.getString("co_cta"));
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_MON_DEB, "" + rstMesCer.getDouble("nd_monDeb"));
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_MON_HAB, "" + rstMesCer.getDouble("nd_monHab"));
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_EST_REG, "" + rstMesCer.getString("st_reg"));
                        arlRegDiaAnt.add(INT_ARL_DIA_ANT_EST_CON_BAN, "" + rstMesCer.getString("st_conban"));
                        arlDatDiaAnt.add(arlRegDiaAnt);
                }
                System.out.println("arlDatDiaAnt: " + arlDatDiaAnt.toString());
                rstMesCer.close();
                stmMesCer.close();
                rstMesCer=null;
                stmMesCer=null;
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

    private boolean getDetalleDiarioActual(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        boolean blnRes=true;
        arlDatDiaAct.clear();
        String strSQLTmp;
        Statement stmMesCer;
        ResultSet rstMesCer;
        try{
            if (con!=null){
                stmMesCer=con.createStatement();
                //Armar la sentencia SQL.
                strSQLTmp="";
                strSQLTmp+="SELECT a1.co_cta, a2.tx_codCta, a2.tx_desLar, a1.nd_monDeb, a1.nd_monHab, a1.tx_ref, a3.st_reg";
                strSQLTmp+=" , a1.st_conban FROM tbm_plaCta AS a2 inner join (tbm_detDia AS a1";
                strSQLTmp+=" inner join tbm_cabdia as a3";
                strSQLTmp+=" on a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_dia=a3.co_dia";
                strSQLTmp+=" )";
                strSQLTmp+=" on a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQLTmp+=" AND a1.co_emp=" + intCodEmp;
                strSQLTmp+=" AND a1.co_loc=" + intCodLoc;
                strSQLTmp+=" AND a1.co_tipDoc=" + intTipDoc;
                strSQLTmp+=" AND a1.co_dia=" + intCodDia;
                strSQLTmp+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia, a1.co_reg";
                rstMesCer=stmMesCer.executeQuery(strSQLTmp);
                while (rstMesCer.next()){
                        //CAPTURA LOS DATOS INICIALES DE LOS SALDOS DEL DIARIO A MODIFICAR(ACTUALIZACION, ELIMINACION, ANULACION)
                        arlRegDiaAct=new ArrayList();
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_COD_EMP, "" + intCodEmp);
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_COD_CTA, "" + rstMesCer.getString("co_cta"));
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_MON_DEB, "" + rstMesCer.getDouble("nd_monDeb"));
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_MON_HAB, "" + rstMesCer.getDouble("nd_monHab"));
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_EST_REG, "" + rstMesCer.getString("st_reg"));
                        arlRegDiaAct.add(INT_ARL_DIA_ACT_EST_CON_BAN, "" + rstMesCer.getString("st_conban"));
                        arlDatDiaAct.add(arlRegDiaAct);
                }
                System.out.println("arlDatDiaAct: " + arlDatDiaAct.toString());
                rstMesCer.close();
                stmMesCer.close();
                rstMesCer=null;
                stmMesCer=null;
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




    private boolean mayorizaCta(java.sql.Connection con, int intCodEmp)
    {
        BigDecimal bdeValDeb=new BigDecimal(BigInteger.ZERO);
        BigDecimal bdeValHab=new BigDecimal(BigInteger.ZERO);
        boolean blnRes=true;
        String strSQLTmp="";
        Statement stmMesCer;
        try{
            if(con!=null){
                stmMesCer=con.createStatement();
                for(int i=0; i<arlDatDiaAnt.size();i++){
                    bdeValDeb=new BigDecimal(objUti.getStringValueAt(arlDatDiaAnt, i, INT_ARL_DIA_ANT_MON_DEB).toString());
                    bdeValHab=new BigDecimal(objUti.getStringValueAt(arlDatDiaAnt, i, INT_ARL_DIA_ANT_MON_HAB).toString());
                    strSQLTmp="";
                    strSQLTmp+="update tbm_salcta";
                    strSQLTmp+=" set nd_salcta=x.saldo from (";
                    strSQLTmp+=" 	select co_emp, co_cta, co_per,";
                    strSQLTmp+=" 	case when nd_salcta is null then " + (bdeValDeb.subtract(bdeValHab)) + "";
                    strSQLTmp+=" 	else";
                    strSQLTmp+=" 	nd_salcta-(" + (bdeValDeb.subtract(bdeValHab)) + ") end as saldo";
                    strSQLTmp+=" 	from tbm_salcta";
                    strSQLTmp+=" 	where co_emp=" + intCodEmp + "";
                    strSQLTmp+="       and co_cta=" + objUti.getIntValueAt(arlDatDiaAnt, i, INT_ARL_DIA_ANT_COD_CTA) + "";
                    strSQLTmp+="       and co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    strSQLTmp+=" ) as x";
                    strSQLTmp+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                    strSQLTmp+=" and tbm_salcta.co_per=x.co_per";
                    strSQLTmp+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                    strSQLTmp+=" and tbm_salcta.co_cta=" + objUti.getIntValueAt(arlDatDiaAnt, i, INT_ARL_DIA_ANT_COD_CTA) + "";
                    strSQLTmp+=" and tbm_salcta.co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    System.out.println("strSQLTmp-arlDatDiaAnt: " + strSQLTmp);
                    stmMesCer.executeUpdate(strSQLTmp);
                }

                for(int i=0; i<arlDatDiaAct.size();i++){
                    bdeValDeb=new BigDecimal(objUti.getStringValueAt(arlDatDiaAct, i, INT_ARL_DIA_ACT_MON_DEB).toString());
                    bdeValHab=new BigDecimal(objUti.getStringValueAt(arlDatDiaAct, i, INT_ARL_DIA_ACT_MON_HAB).toString());
                    strSQLTmp="";
                    strSQLTmp+="update tbm_salcta";
                    strSQLTmp+=" set nd_salcta=x.saldo from (";
                    strSQLTmp+=" 	select co_emp, co_cta, co_per,";
                    strSQLTmp+=" 	case when nd_salcta is null then " + (bdeValDeb.subtract(bdeValHab)) + "";
                    strSQLTmp+=" 	else";
                    strSQLTmp+=" 	nd_salcta+(" + (bdeValDeb.subtract(bdeValHab)) + ") end as saldo";
                    strSQLTmp+=" 	from tbm_salcta";
                    strSQLTmp+=" 	where co_emp=" + intCodEmp + "";
                    strSQLTmp+="       and co_cta=" + objUti.getIntValueAt(arlDatDiaAct, i, INT_ARL_DIA_ACT_COD_CTA) + "";
                    strSQLTmp+="       and co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    strSQLTmp+=" ) as x";
                    strSQLTmp+=" where tbm_salcta.co_emp=x.co_emp and tbm_salcta.co_cta=x.co_cta";
                    strSQLTmp+=" and tbm_salcta.co_per=x.co_per";
                    strSQLTmp+=" and tbm_salcta.co_emp=" + intCodEmp + "";
                    strSQLTmp+=" and tbm_salcta.co_cta=" + objUti.getIntValueAt(arlDatDiaAct, i, INT_ARL_DIA_ACT_COD_CTA) + "";
                    strSQLTmp+=" and tbm_salcta.co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    System.out.println("strSQLTmp-arlDatDiaAct: " + strSQLTmp);
                    stmMesCer.executeUpdate(strSQLTmp);
                }




                for (int j=6; j>1; j--){
                    strSQLTmp="";
                    strSQLTmp+="UPDATE tbm_salCta";
                    strSQLTmp+=" SET nd_salCta=b1.nd_salCta";
                    strSQLTmp+=" FROM (";
                    strSQLTmp+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intNumAniDoc + "" + intNumMesDoc + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQLTmp+=" FROM tbm_plaCta AS a1";
                    strSQLTmp+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQLTmp+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQLTmp+=" AND a1.ne_niv=" + j;
                    strSQLTmp+=" AND a2.co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    strSQLTmp+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQLTmp+=" ) AS b1";
                    strSQLTmp+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stmMesCer.executeUpdate(strSQLTmp);

                    if(j==2){
                        strSQLTmp="";
                        strSQLTmp+="UPDATE tbm_salCta";
                        strSQLTmp+=" SET nd_salCta=b1.nd_salCta";
                        strSQLTmp+=" FROM (";
                        strSQLTmp+=" SELECT a1.co_emp, a3.co_ctaRes AS co_cta, " + intNumAniDoc + "" + intNumMesDoc + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                        strSQLTmp+=" FROM tbm_plaCta AS a1";
                        strSQLTmp+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                        strSQLTmp+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                        strSQLTmp+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQLTmp+=" and a1.ne_niv='1' and a1.tx_niv1 in ('4','5','6','7','8')";
                        strSQLTmp+=" AND a2.co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                        strSQLTmp+=" GROUP BY a1.co_emp, a3.co_ctaRes";
                        strSQLTmp+=" ) AS b1";
                        strSQLTmp+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                        stmMesCer.executeUpdate(strSQLTmp);
                    }

                }

                for (int j=6; j>1; j--){
                    strSQLTmp="";
                    strSQLTmp+="UPDATE tbm_salCta";
                    strSQLTmp+=" SET nd_salCta=b1.nd_salCta";
                    strSQLTmp+=" FROM (";
                    strSQLTmp+=" SELECT a1.co_emp, a1.ne_pad AS co_cta, " + intNumAniDoc + "" + intNumMesDoc + " AS co_per, SUM(a2.nd_salCta) AS nd_salCta";
                    strSQLTmp+=" FROM tbm_plaCta AS a1";
                    strSQLTmp+=" INNER JOIN tbm_salCta AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta)";
                    strSQLTmp+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQLTmp+=" AND a1.ne_niv=" + j;
                    strSQLTmp+=" AND a2.co_per=" + intNumAniDoc + "" + intNumMesDoc + "";
                    strSQLTmp+=" GROUP BY a1.co_emp, a1.ne_pad";
                    strSQLTmp+=" ) AS b1";
                    strSQLTmp+=" WHERE tbm_salCta.co_emp=b1.co_emp AND tbm_salCta.co_cta=b1.co_cta AND tbm_salCta.co_per=b1.co_per";
                    stmMesCer.executeUpdate(strSQLTmp);
                }




                stmMesCer.close();
                stmMesCer=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián establece si el mes que se desea procesar
     * ha sido cerrado.
     * @param periodo El periodo que se desea saber si ha sido cerrado.
     */
    private boolean isMesCerrado(java.sql.Connection con, int intCodEmp, int local, int tipoDocumento, int intCodDia){
        boolean blnRes=false;
        Statement stmMesCer;
        ResultSet rstMesCer;
        intNumAniDoc=-1;
        intNumMesDoc=-1;
        try{
            if(con!=null){
                stmMesCer=con.createStatement();
                strSQL="";
                strSQL+="select extract('year' FROM fe_dia) AS ne_ani ";
                strSQL+=" , case when extract('month' FROM fe_dia)<10 then '0'|| extract('month' FROM fe_dia)";
                strSQL+=" else ''||extract('month' FROM fe_dia) end AS ne_mes";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + local + "";
                strSQL+=" AND a1.co_tipDoc=" + tipoDocumento + "";
                strSQL+=" AND a1.co_dia=" + intCodDia + "";
                rstMesCer=stmMesCer.executeQuery(strSQL);
                if(rstMesCer.next()){
                    intNumAniDoc=rstMesCer.getInt("ne_ani");
                    intNumMesDoc=rstMesCer.getInt("ne_mes");
                }
                strSQL="";
                strSQL+="SELECT ne_mes FROM tbm_cabCieSis AS a1";
                strSQL+=" INNER JOIN tbm_detCieSis AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.ne_ani=a2.ne_ani";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.ne_ani=" + intNumAniDoc + "";
                strSQL+=" AND a2.ne_mes=" + intNumMesDoc + "";
                System.out.println("SQL ISMESCERRADO: " + strSQL);
                rstMesCer=stmMesCer.executeQuery(strSQL);
                if(rstMesCer.next()){
                    blnRes=true;
                }
                stmMesCer.close();
                stmMesCer=null;
                rstMesCer.close();
                rstMesCer=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


}