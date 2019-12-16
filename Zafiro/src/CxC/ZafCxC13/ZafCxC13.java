/*
 * ZafCom10.java
 *
 * Created on 31 de agosto de 2005, 10:10 PM
 */
package CxC.ZafCxC13;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafSelFec.ZafSelFec;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCxC13 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_DAT_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_DAT_NUM_DOC1=6;                    //Número del documento 1.
    static final int INT_TBL_DAT_NUM_DOC2=7;                    //Número del documento 2.
    static final int INT_TBL_DAT_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_DAT_FEC_VEN=9;                     //Fecha de vencimiento.
    static final int INT_TBL_DAT_COD_CTA=10;                    //Código de la cuenta.
    static final int INT_TBL_DAT_NOM_CTA=11;                    //Nombre de la cuenta.
    static final int INT_TBL_DAT_COD_CLI=12;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=13;                    //Nombre del cliente.
    static final int INT_TBL_DAT_VAL_DOC=14;                    //Valor del documento.
    static final int INT_TBL_DAT_EST_DOC=15;                    //Estado del documento.
    static final int INT_TBL_DAT_FEC_ING=16;                    //Fecha de ingreso.
    static final int INT_TBL_DAT_COD_USR_ING=17;                //Código del usuario de ingreso.
    static final int INT_TBL_DAT_USR_ING=18;                    //Usuario de ingreso.
    static final int INT_TBL_DAT_COM_ING=19;                    //Computadora de ingreso.
    static final int INT_TBL_DAT_FEC_MOD=20;                    //Fecha de última modificación.
    static final int INT_TBL_DAT_COD_USR_MOD=21;                //Código del usuario de última modificación.
    static final int INT_TBL_DAT_USR_MOD=22;                    //Usuario de última modificación.
    static final int INT_TBL_DAT_COM_MOD=23;                    //Computadora de última modificación.
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
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta.
    private ZafVenCon vcoTipDoc;                                //Ventana de consulta.
    private ZafVenCon vcoCta;                                   //Ventana de consulta.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstReg;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strDesCorTipDoc, strDesLarTipDoc;            //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;                  //Contenido del campo al obtener el foco.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
   
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafCxC13(ZafParSis obj) 
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
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblEstReg = new javax.swing.JLabel();
        cboEstReg = new javax.swing.JComboBox();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lbCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
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
        optTod.setBounds(4, 76, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(4, 96, 400, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(24, 136, 120, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(112, 136, 32, 20);

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
        txtDesCorTipDoc.setBounds(144, 136, 56, 20);

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
        txtDesLarTipDoc.setBounds(200, 136, 460, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(660, 136, 20, 20);

        lblEstReg.setText("Estado del documento:");
        lblEstReg.setToolTipText("Estado del documento");
        panFil.add(lblEstReg);
        lblEstReg.setBounds(24, 196, 120, 20);
        panFil.add(cboEstReg);
        cboEstReg.setBounds(144, 196, 264, 20);

        lblCli.setText("Cliente/Proveedor:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(24, 176, 120, 20);

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
        txtCodCli.setBounds(144, 176, 56, 20);

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
        txtDesLarCli.setBounds(200, 176, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 176, 20, 20);

        lbCta.setText("Cuenta:");
        lbCta.setToolTipText("Cuenta");
        panFil.add(lbCta);
        lbCta.setBounds(24, 156, 120, 20);
        panFil.add(txtCodCta);
        txtCodCta.setBounds(112, 156, 32, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        panFil.add(txtDesCorCta);
        txtDesCorCta.setBounds(144, 156, 80, 20);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        panFil.add(txtDesLarCta);
        txtDesLarCta.setBounds(224, 156, 436, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panFil.add(butCta);
        butCta.setBounds(660, 156, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Local");
        panFil.add(lblLoc);
        lblLoc.setBounds(24, 116, 120, 20);

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
        txtCodLoc.setBounds(144, 116, 56, 20);

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
        txtNomLoc.setBounds(200, 116, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil.add(butLoc);
        butLoc.setBounds(660, 116, 20, 20);

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

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

    private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta))
        {
            if (txtDesLarCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
            }
            else
            {
                mostrarVenConCta(2);
            }
        }
        else
            txtDesLarCta.setText(strDesLarCta);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCta.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesLarCtaFocusLost

    private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
        strDesLarCta=txtDesLarCta.getText();
        txtDesLarCta.selectAll();
    }//GEN-LAST:event_txtDesLarCtaFocusGained

    private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
        txtDesLarCta.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaActionPerformed

    private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta))
        {
            if (txtDesCorCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
            }
            else
            {
                mostrarVenConCta(1);
            }
        }
        else
            txtDesCorCta.setText(strDesCorCta);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCta.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtDesCorCtaFocusLost

    private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
        strDesCorCta=txtDesCorCta.getText();
        txtDesCorCta.selectAll();
    }//GEN-LAST:event_txtDesCorCtaFocusGained

    private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
        txtDesCorCta.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaActionPerformed

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
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtDesLarCta.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            cboEstReg.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
//        //Abrir la opción seleccionada al presionar ENTER.
//        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
//        {
//            evt.consume();
//            abrirFrm();
//        }
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
//        //Abrir la opción seleccionada al dar doble click.
//        if (evt.getClickCount()==2)
//        {
//            abrirFrm();
//        }
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

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)) {
            if (txtCodLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } else {
                mostrarVenConLoc(1);
            }
        } else
            txtCodLoc.setText(strCodLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
}//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
}//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc)) {
            if (txtNomLoc.getText().equals("")) {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
            } else {
                mostrarVenConLoc(2);
            }
        } else
            txtNomLoc.setText(strNomLoc);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0) {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_butLocActionPerformed

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
    private javax.swing.JButton butCta;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboEstReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lbCta;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEstReg;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarTipDoc;
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
            this.setTitle(strAux + "v0.6");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodTipDoc.setVisible(false);
            txtCodCta.setVisible(false);
            //Configurar el combo "Estado de registro".
            vecEstReg=new Vector();
            vecEstReg.add("");
            vecEstReg.add("A");
            vecEstReg.add("I");
            vecEstReg.add("P");
            vecEstReg.add("D");
            vecEstReg.add("R");
            cboEstReg.addItem("(Todos)");
            cboEstReg.addItem("A: Activo");
            cboEstReg.addItem("I: Anulado");
            cboEstReg.addItem("P: Pendiente por autorizar");
            cboEstReg.addItem("D: Autorización denegada");
            cboEstReg.addItem("R: Pendiente de impresión");
            cboEstReg.setSelectedIndex(0);
            //Configurar ZafVenCon.
            configurarVenConLoc();
            configurarVenConTipDoc();
            configurarVenConCta();
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
            vecCab=new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Código");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC1,"Núm.Doc.1");
            vecCab.add(INT_TBL_DAT_NUM_DOC2,"Núm.Doc.2");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_DAT_NOM_CTA,"Cuenta");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Monto");
            vecCab.add(INT_TBL_DAT_EST_DOC,"Est.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_ING,"Fec.Ing.");
            vecCab.add(INT_TBL_DAT_COD_USR_ING,"Cód.Usr.Ing.");
            vecCab.add(INT_TBL_DAT_USR_ING,"Usr.Ing.");
            vecCab.add(INT_TBL_DAT_COM_ING,"Com.Ing.");
            vecCab.add(INT_TBL_DAT_FEC_MOD,"Fec.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COD_USR_MOD,"Cód.Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_USR_MOD,"Usr.Ult.Mod.");
            vecCab.add(INT_TBL_DAT_COM_MOD,"Com.Ult.Mod.");
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
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC1).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC2).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setPreferredWidth(50);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_ING, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_USR_MOD, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 318: //Listado de documentos cobrados...
                case 514: //Listado de documentos pagados...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!(objPerUsr.isOpcionEnabled(2506) || objPerUsr.isOpcionEnabled(2507)))
                    {
                        //2506: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                        //2507: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_ING, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_MOD, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_USR_MOD, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COM_MOD, tblDat);
                    }
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
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_EST_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
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
                if (txtCodLoc.getText().length()>0)
                    strConSQL+=" AND a1.co_loc=" + txtCodLoc.getText();
                if (txtCodTipDoc.getText().length()>0)
                    strConSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                if (txtCodCta.getText().length()>0)
                    strConSQL+=" AND a1.co_cta=" + txtCodCta.getText();
                if (txtCodCli.getText().length()>0)
                    strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                if (cboEstReg.getSelectedIndex()>0)
                    strConSQL+=" AND a1.st_reg='" + vecEstReg.get(cboEstReg.getSelectedIndex()) + "'";
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc1, a1.ne_numDoc2";
                    strSQL+=", a1.fe_doc, a1.fe_ven, a1.co_cta, a3.tx_desLar AS a3_tx_desLar, a1.co_cli, a1.tx_nomCli, a1.nd_monDoc, a1.st_reg";
                    strSQL+=", a1.fe_ing, a1.co_usrIng, a5.tx_usr AS a5_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a6.tx_usr AS a6_tx_usr, a1.tx_comMod";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrIng=a5.co_usr)";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a1.co_usrMod=a6.co_usr)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc1";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a1.co_doc, a1.ne_numDoc1, a1.ne_numDoc2";
                    strSQL+=", a1.fe_doc, a1.fe_ven, a1.co_cta, a3.tx_desLar AS a3_tx_desLar, a1.co_cli, a1.tx_nomCli, a1.nd_monDoc, a1.st_reg";
                    strSQL+=", a1.fe_ing, a1.co_usrIng, a5.tx_usr AS a5_tx_usr, a1.tx_comIng, a1.fe_ultMod, a1.co_usrMod, a6.tx_usr AS a6_tx_usr, a1.tx_comMod";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cta=a3.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ON (a1.co_usrIng=a5.co_usr)";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a6 ON (a1.co_usrMod=a6.co_usr)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                    if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    }
                     strSQL+=" AND a1.co_loc not in(5)"; 
                     strSQL+=" AND a1.st_reg= 'A'"; //cambio tony
                    strSQL+="AND a1.co_tipDoc in (  ";
                    strSQL+=" select co_tipDoc from tbr_tipDocUsr where co_emp="+ objParSis.getCodigoEmpresa()+" and co_loc="+ objParSis.getCodigoLocal();
                    strSQL+=" and co_mnu="+ objParSis.getCodigoMenu()+" AND co_usr="+ objParSis.getCodigoUsuario()+")"; 
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.fe_doc, a1.ne_numDoc1";
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
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC1,rst.getString("ne_numDoc1"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC2,rst.getString("ne_numDoc2"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_NOM_CTA,rst.getString("a3_tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("nd_monDoc"));
                        vecReg.add(INT_TBL_DAT_EST_DOC,rst.getString("st_reg"));
                        switch (objParSis.getCodigoMenu())
                        {
                                case 318: //Listado de documentos cobrados...
                                case 514: //Listado de documentos pagados...
                                //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                                if (objPerUsr.isOpcionEnabled(2506) || objPerUsr.isOpcionEnabled(2507))
                                {
                                    //2506: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                                    //2507: Ficha "Reporte": Tabla->Mostrar "Datos de ingreso y última modificación de documentos".
                                    vecReg.add(INT_TBL_DAT_FEC_ING,rst.getString("fe_ing"));
                                    vecReg.add(INT_TBL_DAT_COD_USR_ING,rst.getString("co_usrIng"));
                                    vecReg.add(INT_TBL_DAT_USR_ING,rst.getString("a5_tx_usr"));
                                    vecReg.add(INT_TBL_DAT_COM_ING,rst.getString("tx_comIng"));
                                    vecReg.add(INT_TBL_DAT_FEC_MOD,rst.getString("fe_ultMod"));
                                    vecReg.add(INT_TBL_DAT_COD_USR_MOD,rst.getString("co_usrMod"));
                                    vecReg.add(INT_TBL_DAT_USR_MOD,rst.getString("a6_tx_usr"));
                                    vecReg.add(INT_TBL_DAT_COM_MOD,rst.getString("tx_comMod"));
                                }
                                else
                                {
                                    vecReg.add(INT_TBL_DAT_FEC_ING,null);
                                    vecReg.add(INT_TBL_DAT_COD_USR_ING,null);
                                    vecReg.add(INT_TBL_DAT_USR_ING,null);
                                    vecReg.add(INT_TBL_DAT_COM_ING,null);
                                    vecReg.add(INT_TBL_DAT_FEC_MOD,null);
                                    vecReg.add(INT_TBL_DAT_COD_USR_MOD,null);
                                    vecReg.add(INT_TBL_DAT_USR_MOD,null);
                                    vecReg.add(INT_TBL_DAT_COM_MOD,null);
                                }
                                break;
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
    
    private boolean abrirFrm()
    {
        boolean blnRes=true;
        try
        {
            if (!((tblDat.getSelectedColumn()==-1) || (tblDat.getSelectedRow()==-1)))
            {
                strAux="Contabilidad.ZafCon01.ZafCon01";
                objParSis.setNombreMenu("Plan de cuentas...");
                if (!strAux.equals(""))
                    invocarClase(strAux);
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
        boolean blnRes=true;
        try
        {
            //Obtener el constructor de la clase que se va a invocar.
            Class objVen=Class.forName(clase);
            Class objCla[]=new Class[2];
            objCla[0]=objParSis.getClass();
            objCla[1]=new Integer(0).getClass();
            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
            //Inicializar el constructor que se obtuvo.
            Object objObj[]=new Object[2];
            objObj[0]=objParSis;
//            objObj[1]=new Integer(tblDat.getValueAt(tblDat.getSelectedRow(),INT_TBL_DAT_COD_SIS).toString());
            javax.swing.JInternalFrame ifrVen;
            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
            this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
            ifrVen.setVisible(true);
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
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
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
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("464");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            else
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_placta as a1";
                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
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
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                    if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.setVisible(true);
                    if (vcoCta.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                    {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                    }
                    else
                    {
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.setVisible(true);
                        if (vcoCta.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            txtDesCorCta.setText(strDesCorCta);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText()))
                    {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                    }
                    else
                    {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.setVisible(true);
                        if (vcoCta.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarCta.setText(strDesLarCta);
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
                case INT_TBL_DAT_NUM_DOC1:
                    strMsg="Número del documento 1";
                    break;
                case INT_TBL_DAT_NUM_DOC2:
                    strMsg="Número del documento 2";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_NOM_CTA:
                    strMsg="Nombre de la cuenta";
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
                case INT_TBL_DAT_EST_DOC:
                    strMsg="Estado del documento";
                    break;
                case INT_TBL_DAT_FEC_ING:
                    strMsg="Fecha de ingreso";
                    break;
                case INT_TBL_DAT_COD_USR_ING:
                    strMsg="Código del usuario de ingreso";
                    break;
                case INT_TBL_DAT_USR_ING:
                    strMsg="Usuario de ingreso";
                    break;
                case INT_TBL_DAT_COM_ING:
                    strMsg="Computadora de ingreso";
                    break;
                case INT_TBL_DAT_FEC_MOD:
                    strMsg="Fecha de última modificación";
                    break;
                case INT_TBL_DAT_COD_USR_MOD:
                    strMsg="Código del usuario de última modificación";
                    break;
                case INT_TBL_DAT_USR_MOD:
                    strMsg="Usuario de última modificación";
                    break;
                case INT_TBL_DAT_COM_MOD:
                    strMsg="Computadora de última modificación";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}


