/*
 * ZafVenConCxC01.java
 *
 * Created on 18 de septiembre de 2007, 10:26 AM
 */

package Librerias.ZafVenCon;
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
import Librerias.ZafSelFec.ZafSelFec;
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
public class ZafVenConCxC01 extends javax.swing.JDialog
{
    //Constantes:
    public static final int INT_BUT_CAN=0;                      /**Un valor para getSelectedButton: Indica "Botón Cancelar".*/
    public static final int INT_BUT_ACE=1;                      /**Un valor para getSelectedButton: Indica "Botón Aceptar".*/
    
    //Constantes: Columnas del JTable:
    public static final int INT_TBL_DAT_LIN=0;                  //Línea.
    public static final int INT_TBL_DAT_CHK=1;                  //Casilla de verificación.
    public static final int INT_TBL_DAT_COD_LOC=2;              //Código del local.
    public static final int INT_TBL_DAT_COD_TIP_DOC=3;          //Código del tipo de documento.
    public static final int INT_TBL_DAT_DEC_TIP_DOC=4;          //Descripción corta del tipo de documento.
    public static final int INT_TBL_DAT_DEL_TIP_DOC=5;          //Descripción larga del tipo de documento.
    public static final int INT_TBL_DAT_COD_DOC=6;              //Código del documento (Sistema).
    public static final int INT_TBL_DAT_COD_REG=7;              //Código del registro (Sistema).
    public static final int INT_TBL_DAT_NUM_DOC=8;              //Número del documento.
    public static final int INT_TBL_DAT_FEC_DOC=9;              //Fecha del documento.
    public static final int INT_TBL_DAT_DIA_CRE=10;             //Días de crédito.
    public static final int INT_TBL_DAT_FEC_VEN=11;             //Fecha de vencimiento.
    public static final int INT_TBL_DAT_POR_RET=12;             //Porcentaje de retención.
    public static final int INT_TBL_DAT_VAL_DOC=13;             //Valor del documento.
    public static final int INT_TBL_DAT_ABO_DOC=14;             //Abono del documento.
    public static final int INT_TBL_DAT_VAL_PEN=15;             //Valor pendiente.
    public static final int INT_TBL_DAT_EST_SOP=16;             //Estado de soporte.
    public static final int INT_TBL_DAT_SOP_ENT=17;             //Soporte entregado.
    public static final int INT_TBL_DAT_EST_POS=18;             //Estado de postfechado.
    public static final int INT_TBL_DAT_COD_BAN=19;             //Código del Banco.
    public static final int INT_TBL_DAT_NOM_BAN=20;             //Nombre del Banco.
    public static final int INT_TBL_DAT_NUM_CTA=21;             //Número de cuenta.
    public static final int INT_TBL_DAT_NUM_CHQ=22;             //Número de cheque.
    public static final int INT_TBL_DAT_FEC_REC_CHQ=23;         //Fecha de recepción del cheque.
    public static final int INT_TBL_DAT_FEC_VEN_CHQ=24;         //Fecha de vemcimiento del cheque.
    public static final int INT_TBL_DAT_VAL_CHQ=25;             //Valor del cheque.
    
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta "Cliente".
    private ZafVenCon vcoForPag;                                //Ventana de consulta "Forma de pago".
    private ZafVenCon vcoLoc;                                   //Ventana de consulta "Local".
    private ZafVenCon vcoVen;                                   //Ventana de consulta "Vendedor/Comprador".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private String strCodForPag, strNomForPag;                  //Contenido del campo al obtener el foco.
    private String strCodLoc, strNomLoc;                        //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                        //Contenido del campo al obtener el foco.
    //Variables de la clase.
    private int intButSelDlg;                                   //Botón seleccionado en el JDialog.
    private String strTit;                                      //Título del JDialog.
    private boolean blnMarTodChkTblDat=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private int intTipCon;                                      //Tipo de consulta.
    private String strNumSerRet;                                //Retenciones recibidas: Numero de serie.
    private String strNumAutRet;                                //Retenciones recibidas: Número de autorización.
    private String strFecCadRet;                                //Retenciones recibidas: Fecha de caducidad.
    private String strCodSRIRet;                                //Retenciones recibidas: Código del SRI.
    private boolean blnTarCre;
    
    /**
     * Este constructor crea una instancia de la clase ZafVenConCxC01.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     */
    public ZafVenConCxC01(java.awt.Frame padre, ZafParSis parametros, String titulo)
    {
        this(padre, parametros, titulo, false);
    }
    
    /**
     * Este constructor crea una instancia de la clase ZafVenConCxC01.
     * @param padre El formulario padre.
     * @param parametros El objeto ZafParSis.
     * @param titulo El título del JDialog.
     * @param tarjetasCredito Para tarjetas de crédito
     * <BR>true: Sólo se mostrará los documentos donde la forma de pago sea "Tarjeta de crédito".
     * <BR>false: Sólo se mostrará los documentos donde la forma de pago no sea "Tarjeta de crédito".
     */
    public ZafVenConCxC01(java.awt.Frame padre, ZafParSis parametros, String titulo, boolean tarjetasCredito)
    {
        super(padre, true);
        initComponents();
        //Inicializar objetos.
        objParSis=parametros;
        strTit=titulo;
        intButSelDlg=INT_BUT_CAN;
        blnTarCre=tarjetasCredito;
        intTipCon=1;
        if (!configurarFrm())
            exitForm();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrCri = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        chkMosSolDocVen = new javax.swing.JCheckBox();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtNomForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        chkMosSolDocCon = new javax.swing.JCheckBox();
        chkMosRet = new javax.swing.JCheckBox();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(10, 140));
        panGenCab.setLayout(null);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Bodega");
        panGenCab.add(lblCli);
        lblCli.setBounds(0, 4, 100, 20);

        txtCodCli.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
        panGenCab.add(txtCodCli);
        txtCodCli.setBounds(100, 4, 56, 20);

        txtDesLarCli.setToolTipText("Cliente/Proveedor");
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
        panGenCab.add(txtDesLarCli);
        txtDesLarCli.setBounds(156, 4, 236, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGenCab.add(butCli);
        butCli.setBounds(392, 4, 20, 20);

        chkMosSolDocVen.setText("Mostrar sólo los documentos vencidos");
        chkMosSolDocVen.setToolTipText("Mostrar sólo los documentos vencidos");
        panGenCab.add(chkMosSolDocVen);
        chkMosSolDocVen.setBounds(0, 116, 250, 20);

        lblForPag.setText("Forma de pago:");
        lblForPag.setToolTipText("Tipo de documento");
        panGenCab.add(lblForPag);
        lblForPag.setBounds(0, 24, 100, 20);

        txtCodForPag.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodForPag.setToolTipText("Código de la forma de pago");
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
        panGenCab.add(txtCodForPag);
        txtCodForPag.setBounds(100, 24, 56, 20);

        txtNomForPag.setToolTipText("Forma de pago");
        txtNomForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomForPagActionPerformed(evt);
            }
        });
        txtNomForPag.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomForPagFocusLost(evt);
            }
        });
        panGenCab.add(txtNomForPag);
        txtNomForPag.setBounds(156, 24, 236, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panGenCab.add(butForPag);
        butForPag.setBounds(392, 24, 20, 20);

        lblVen.setText("Vendedor:");
        panGenCab.add(lblVen);
        lblVen.setBounds(416, 24, 100, 20);

        txtCodVen.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodVen.setToolTipText("Código del vendedor/comprador");
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
        panGenCab.add(txtCodVen);
        txtCodVen.setBounds(516, 24, 32, 20);

        txtNomVen.setToolTipText("Nombre del vendedor/comprador");
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
        panGenCab.add(txtNomVen);
        txtNomVen.setBounds(548, 24, 116, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenCab.add(butVen);
        butVen.setBounds(664, 24, 20, 20);

        lblLoc.setText("Local:");
        panGenCab.add(lblLoc);
        lblLoc.setBounds(416, 4, 100, 20);

        txtCodLoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodLoc.setToolTipText("Código del local");
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
        panGenCab.add(txtCodLoc);
        txtCodLoc.setBounds(516, 4, 32, 20);

        txtNomLoc.setToolTipText("Nombre del local");
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
        panGenCab.add(txtNomLoc);
        txtNomLoc.setBounds(548, 4, 116, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panGenCab.add(butLoc);
        butLoc.setBounds(664, 4, 20, 20);

        chkMosSolDocCon.setText("Mostrar sólo los documentos de contado");
        chkMosSolDocCon.setToolTipText("Mostrar sólo los documentos de contado");
        panGenCab.add(chkMosSolDocCon);
        chkMosSolDocCon.setBounds(250, 116, 260, 20);

        chkMosRet.setText("Mostrar las retenciones");
        chkMosRet.setToolTipText("Mostrar las retenciones");
        panGenCab.add(chkMosRet);
        chkMosRet.setBounds(510, 116, 176, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDet.setLayout(new java.awt.BorderLayout(0, 1));

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panFrm.add(panGen, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al criterio seleccionado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butLim.setText("Limpiar");
        butLim.setToolTipText("Limpia el cuadro de dialogo.");
        butLim.setPreferredSize(new java.awt.Dimension(92, 25));
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });
        panBot.add(butLim);

        butAce.setText("Aceptar");
        butAce.setToolTipText("Acepta la opción seleccionada.");
        butAce.setPreferredSize(new java.awt.Dimension(92, 25));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panBot.add(butAce);

        butCan.setText("Cancelar");
        butCan.setToolTipText("Cierra el cuadro de dialogo.");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panBot.add(butCan);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
        mostrarVenConVen(0);
    }//GEN-LAST:event_butVenActionPerformed

    private void txtNomVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusLost
        if (txtNomVen.isEditable())
        {
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
        }
    }//GEN-LAST:event_txtNomVenFocusLost

    private void txtNomVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomVenFocusGained
        strNomVen=txtNomVen.getText();
        txtNomVen.selectAll();
    }//GEN-LAST:event_txtNomVenFocusGained

    private void txtNomVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomVenActionPerformed
        txtNomVen.transferFocus();
    }//GEN-LAST:event_txtNomVenActionPerformed

    private void txtCodVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusLost
        if (txtCodVen.isEditable())
        {
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
        }
    }//GEN-LAST:event_txtCodVenFocusLost

    private void txtCodVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodVenFocusGained
        strCodVen=txtCodVen.getText();
        txtCodVen.selectAll();
    }//GEN-LAST:event_txtCodVenFocusGained

    private void txtCodVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodVenActionPerformed
        txtCodVen.transferFocus();
    }//GEN-LAST:event_txtCodVenActionPerformed

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        if (txtNomLoc.isEditable())
        {
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
        if (txtCodLoc.isEditable())
        {
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
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
    }//GEN-LAST:event_butForPagActionPerformed

    private void txtNomForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusLost
        if (txtNomForPag.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomForPag.getText().equalsIgnoreCase(strNomForPag))
            {
                if (txtNomForPag.getText().equals(""))
                {
                    txtCodForPag.setText("");
                    txtNomForPag.setText("");
                }
                else
                {
                    mostrarVenConForPag(2);
                }
            }
            else
                txtNomForPag.setText(strNomForPag);
        }
    }//GEN-LAST:event_txtNomForPagFocusLost

    private void txtNomForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomForPagFocusGained
        strNomForPag=txtNomForPag.getText();
        txtNomForPag.selectAll();
    }//GEN-LAST:event_txtNomForPagFocusGained

    private void txtNomForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomForPagActionPerformed
        txtNomForPag.transferFocus();
    }//GEN-LAST:event_txtNomForPagActionPerformed

    private void txtCodForPagFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusLost
        if (txtCodForPag.isEditable())
        {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtCodForPag.getText().equalsIgnoreCase(strCodForPag))
            {
                if (txtCodForPag.getText().equals(""))
                {
                    txtCodForPag.setText("");
                    txtNomForPag.setText("");
                }
                else
                {
                    mostrarVenConForPag(1);
                }
            }
            else
                txtCodForPag.setText(strCodForPag);
        }
    }//GEN-LAST:event_txtCodForPagFocusLost

    private void txtCodForPagFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodForPagFocusGained
        strCodForPag=txtCodForPag.getText();
        txtCodForPag.selectAll();
    }//GEN-LAST:event_txtCodForPagFocusGained

    private void txtCodForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodForPagActionPerformed
        txtCodForPag.transferFocus();
    }//GEN-LAST:event_txtCodForPagActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
        //Limpiar sólo si el cliente ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            objTblMod.removeAllRows();
            lblMsgSis.setText("Listo");
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
            //Limpiar sólo si el cliente ha cambiado.
            if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
            {
                objTblMod.removeAllRows();
                lblMsgSis.setText("Listo");
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);
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
            //Limpiar sólo si el cliente ha cambiado.
            if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
            {
                objTblMod.removeAllRows();
                lblMsgSis.setText("Listo");
            }
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiar();
    }//GEN-LAST:event_butLimActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if (tblDat.getRowCount()>0)
        {
            if (tblDat.getSelectedRow()==-1)
            {
                tblDat.setRowSelectionInterval(0,0);
            }
            tblDat.requestFocus();
        }
        else
        {
            txtCodCli.requestFocus();
        }
    }//GEN-LAST:event_formWindowActivated

    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        intButSelDlg=INT_BUT_CAN;
        dispose();
    }//GEN-LAST:event_exitForm

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        intButSelDlg=INT_BUT_ACE;
        dispose();
    }//GEN-LAST:event_butAceActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Cerrar el JDialog al dar doble click.
        if (evt.getClickCount()==2)
        {
            butAceActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatMouseClicked

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Cerrar el JDialog al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            butAceActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCanActionPerformed

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
    
    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrCri;
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butForPag;
    private javax.swing.JButton butLim;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkMosSolDocCon;
    private javax.swing.JCheckBox chkMosSolDocVen;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomForPag;
    private javax.swing.JTextField txtNomLoc;
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
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxChecked(false);
            panGenCab.add(objSelFec);
            objSelFec.setBounds(0, 44, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(strTit + " v0.11");
            lblTit.setText(strTit);
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar las ZafVenCon.
            configurarVenConCli();
            configurarVenConForPag();
            configurarVenConLoc();
            configurarVenConVen();
            //Configurar los JTables.
            configurarTblDat();
            //Evitar que sólo se pueda seleccionar otro local si el sistema maneja "Clientes por Empresa".
            if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                txtCodLoc.setText("" + objParSis.getCodigoLocal());
                txtCodLoc.setEditable(false);
                txtNomLoc.setEditable(false);
                txtCodLoc.setBackground(txtCodVen.getBackground());
                txtNomLoc.setBackground(txtCodVen.getBackground());
                butLoc.setEnabled(false);
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
            vecCab=new Vector(26);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cod.Doc.");
            vecCab.add(INT_TBL_DAT_COD_REG,"Cod.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DIA_CRE,"Dia.Cre.");
            vecCab.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_ABO_DOC,"Abono");
            vecCab.add(INT_TBL_DAT_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_DAT_EST_SOP,"Est.Sop.");
            vecCab.add(INT_TBL_DAT_SOP_ENT,"Sop.Ent.");
            vecCab.add(INT_TBL_DAT_EST_POS,"Est.Pos.");
            vecCab.add(INT_TBL_DAT_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_DAT_NOM_BAN,"Nom.Ban.");
            vecCab.add(INT_TBL_DAT_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_DAT_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_DAT_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_DAT_VAL_CHQ,"Val.Chq.");
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
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(19);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_BAN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REC_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_SOP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOP_ENT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_POS, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatHeaMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_EST_SOP).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_SOP_ENT).setCellRenderer(objTblCelRenChk);
            tcmAux.getColumn(INT_TBL_DAT_EST_POS).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            //Establecer el modo de operación del JTable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
        }
        catch(Exception e)
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
            arlCam.add("a1.tx_numSerRet");
            arlCam.add("a1.tx_numAutSRIRet");
            arlCam.add("a1.tx_fecCadRet");
            arlCam.add("a1.tx_codSRIRet");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            arlAli.add("Núm.Ser.Ret.");
            arlAli.add("Núm.Aut.Ret.");
            arlAli.add("Fec.Cad.Ret.");
            arlAli.add("Cód.SRI.Ret.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
            if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_numSerRet, a1.tx_numAutSRIRet, a1.tx_fecCadRet, a1.tx_codSRIRet";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir, a1.tx_numSerRet, a1.tx_numAutSRIRet, a1.tx_fecCadRet, a1.tx_codSRIRet";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[4];
            intColOcu[0]=4;
            intColOcu[1]=5;
            intColOcu[2]=6;
            intColOcu[3]=7;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("492");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_forPag, a1.tx_des";
            strSQL+=" FROM tbm_cabForPag AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_forPag";
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
            arlAncCol.add("492");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_loc, a1.tx_nom";
            strSQL+=" FROM tbm_loc AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg IN ('A', 'P')";
            strSQL+=" ORDER BY a1.co_loc";
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
            arlAncCol.add("90");
            arlAncCol.add("402");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom";
            strSQL+=" FROM tbm_usr AS a1";
            strSQL+=" INNER JOIN tbr_usrEmp AS a2 ON (a1.co_usr=a2.co_usr)";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.co_usr";
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de vendedores/compradores", strSQL, arlCam, arlAli, arlAncCol);
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        strNumSerRet=vcoCli.getValueAt(5);
                        strNumAutRet=vcoCli.getValueAt(6);
                        strFecCadRet=vcoCli.getValueAt(7);
                        strCodSRIRet=vcoCli.getValueAt(8);
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        strNumSerRet=vcoCli.getValueAt(5);
                        strNumAutRet=vcoCli.getValueAt(6);
                        strFecCadRet=vcoCli.getValueAt(7);
                        strCodSRIRet=vcoCli.getValueAt(8);
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
                            strNumSerRet=vcoCli.getValueAt(5);
                            strNumAutRet=vcoCli.getValueAt(6);
                            strFecCadRet=vcoCli.getValueAt(7);
                            strCodSRIRet=vcoCli.getValueAt(8);
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
                        strNumSerRet=vcoCli.getValueAt(5);
                        strNumAutRet=vcoCli.getValueAt(6);
                        strFecCadRet=vcoCli.getValueAt(7);
                        strCodSRIRet=vcoCli.getValueAt(8);
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
                            strNumSerRet=vcoCli.getValueAt(5);
                            strNumAutRet=vcoCli.getValueAt(6);
                            strFecCadRet=vcoCli.getValueAt(7);
                            strCodSRIRet=vcoCli.getValueAt(8);
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
                    vcoForPag.setCampoBusqueda(1);
                    vcoForPag.setVisible(true);
                    if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoForPag.buscar("a1.co_forPag", txtCodForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(0);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtCodForPag.setText(strCodForPag);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción".
                    if (vcoForPag.buscar("a1.tx_des", txtNomForPag.getText()))
                    {
                        txtCodForPag.setText(vcoForPag.getValueAt(1));
                        txtNomForPag.setText(vcoForPag.getValueAt(2));
                    }
                    else
                    {
                        vcoForPag.setCampoBusqueda(1);
                        vcoForPag.setCriterio1(11);
                        vcoForPag.cargarDatos();
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodForPag.setText(vcoForPag.getValueAt(1));
                            txtNomForPag.setText(vcoForPag.getValueAt(2));
                        }
                        else
                        {
                            txtNomForPag.setText(strNomForPag);
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
    private boolean mostrarVenConLoc(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(1);
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
                        vcoLoc.setCampoBusqueda(1);
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
    private boolean mostrarVenConVen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(1);
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
    private void tblDatHeaMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblMod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblDat.columnAtPoint(evt.getPoint())==INT_TBL_DAT_CHK)
            {
                if (blnMarTodChkTblDat)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK);
                    }
                    blnMarTodChkTblDat=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    /**
     * Esta función obtiene el título de la ventana de consulta.
     * @return El título de la ventana de consulta.
     */
    public String getTitulo()
    {
        return strTit;
    }
    
    /**
     * Esta función establece el título de la ventana de consulta.
     * @param titulo El título de la ventana de consulta.
     */
    public void setTitulo(String titulo)
    {
        strTit=titulo;
        this.setTitle(strTit);
        lblTit.setText(strTit);
    }

    /**
     * Esta función carga los datos en la ventana de consulta.
     */
    public void cargarDatos()
    {
        butConActionPerformed(null);
    }

    /**
     * Esta función obtiene la opción que seleccionó el usuario en el JDialog.
     * Puede devolver uno de los siguientes valores:
     * <UL>
     * <LI>0: Click en el botón Cancelar (INT_BUT_CAN).
     * <LI>1: Click en el botón Aceptar (INT_BUT_ACE).
     * </UL>
     * <BR>Nota.- La opción predeterminada es el botón Cancelar.
     * @return La opción seleccionada por el usuario.
     */
    public int getSelectedButton()
    {
        return intButSelDlg;
    }

    /**
     * Esta función obtiene el valor que se encuentra en la posición especificada.
     * @param row La fila de la que se desea obtener el valor.
     * @param col La columna de la que se desea obtener el valor.
     * @return El valor que se encuentra en la posición especificada.
     */
    public String getValueAt(int row, int col)
    {
        if (row!=-1)
            return objUti.parseString(objTblMod.getValueAt(row, col));
        else
            return "";
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String strAuxAccCli="";
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
                if (!txtCodForPag.getText().equals(""))
                    strConSQL+=" AND a1.co_forPag=" + txtCodForPag.getText();
                if (!txtCodLoc.getText().equals(""))
                    strConSQL+=" AND a1.co_loc=" + txtCodLoc.getText();
                if (!txtCodVen.getText().equals(""))
                    strConSQL+=" AND a1.co_com=" + txtCodVen.getText();
                if (objSelFec.isCheckBoxChecked())
                {
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
                }
                if (chkMosSolDocVen.isSelected())
                {
                    strConSQL+=" AND a2.fe_ven<=" + objParSis.getFuncionFechaHoraBaseDatos();
                }
                if (chkMosSolDocCon.isSelected())
                {
                    if (chkMosRet.isSelected())
                    {
                        strConSQL+=" AND a2.ne_diaCre=0 AND b2.ne_diaGra=0";
                    }
                    else
                    {
                        strConSQL+=" AND a2.ne_diaCre=0 AND a2.nd_porRet=0 AND b2.ne_diaGra=0";
                    }
                }
                else
                {
                    if (!chkMosRet.isSelected())
                    {
                        strConSQL+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                    }
                }
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre";
                strSQL+=", a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq";
                strSQL+=", a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq";
                strSQL+=" FROM ";
                if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS b2 ON (a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli)";
                    strAuxAccCli="";
                }
                else
                {
                    strSQL+=" tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbr_cliLoc AS b1 ON (a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_cli=b1.co_cli)";
                    strSQL+=" INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_cli=b2.co_cli)";
                    strAuxAccCli=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                }
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg)";
                strSQL+=" LEFT OUTER JOIN tbm_cabForPag AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_forPag=a5.co_forPag)";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                strSQL+=strAuxAccCli;
                strSQL+=strConSQL;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                switch (intTipCon)
                {
                    case 1: //CxC (Todos los cobros).
                        strSQL+=" AND a3.ne_mod IN (1, 3)";
                        break;
                    case 2: //CxC (Todos los cobros que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3)";
                        break;
                    case 3: //CxC (Todos los cobros que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND a2.nd_porRet>0";
                        break;
                    case 4: //CxP (Todos los pagos).
                        strSQL+=" AND a3.ne_mod IN (2, 4)";
                        break;
                    case 5: //CxP (Todos los pagos que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4)";
                        break;
                    case 6: //CxP (Todos los pagos que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND a2.nd_porRet>0";
                        break;
                    case 7: //CxC y CxP (Todos los cobros y pagos).
                        break;
                    case 8: //CxC y CxP (Todos los cobros y pagos que no correspondan a retenciones).
                        break;
                    case 9: //CxC y CxP (Todos los cobros y pagos que correspondan a retenciones).
                        strSQL+=" AND a2.nd_porRet>0";
                        break;
                    case 10: //CxC (Todos los créditos).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 11: //CxC (Todos los créditos que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 12: //CxC (Todos los créditos que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)>0 AND a2.nd_porRet>0";
                        break;
                    case 13: //CxC (Todos los débitos).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 14: //CxC (Todos los débitos que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 15: //CxC (Todos los débitos que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)<0 AND a2.nd_porRet>0";
                        break;
                    case 16: //CxP (Todos los créditos).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 17: //CxP (Todos los créditos que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 18: //CxP (Todos los créditos que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)>0 AND a2.nd_porRet>0";
                        break;
                    case 19: //CxP (Todos los débitos).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 20: //CxP (Todos los débitos que no correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 21: //CxP (Todos los débitos que correspondan a retenciones).
                        strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)<0 AND a2.nd_porRet>0";
                        break;
                    case 22: //CxC y CxP (Todos los créditos).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 23: //CxC y CxP (Todos los créditos que no correspondan a retenciones).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0";
                        break;
                    case 24: //CxC y CxP (Todos los créditos que correspondan a retenciones).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)>0 AND a2.nd_porRet>0";
                        break;
                    case 25: //CxC y CxP (Todos los débitos).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 26: //CxC y CxP (Todos los débitos que no correspondan a retenciones).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                        break;
                    case 27: //CxC y CxP (Todos los débitos que correspondan a retenciones).
                        strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0 AND a2.nd_porRet>0";
                        break;
                }
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                //--- Inicio de comentario del bloque: 12/Oct/2016 --
                //Se tuvo que comentar el bloque debido a que, si hay facturas con co_ForPag = 97 (Tarjeta de credito/debito), esta condicion
                //impedia mostrar dichas facturas
                /*
                if (blnTarCre)
                    strSQL+=" AND a5.ne_tipForPag=4";
                else
                    strSQL+=" AND a5.ne_tipForPag<>4";
                */
                //--- Fin del comentario del bloque: 12/Oct/2016 ----
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                strAuxAccCli="";
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
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_DAT_POR_RET,rst.getString("nd_porRet"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_DAT_ABO_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_DAT_VAL_PEN,rst.getString("nd_pen"));
                        if (rst.getString("st_sop").equals("S"))
                            vecReg.add(INT_TBL_DAT_EST_SOP,new Boolean(true));
                        else
                            vecReg.add(INT_TBL_DAT_EST_SOP,null);
                        if (rst.getString("st_entSop").equals("S"))
                            vecReg.add(INT_TBL_DAT_SOP_ENT,new Boolean(true));
                        else
                            vecReg.add(INT_TBL_DAT_SOP_ENT,null);
                        if (rst.getString("st_pos").equals("S"))
                            vecReg.add(INT_TBL_DAT_EST_POS,new Boolean(true));
                        else
                            vecReg.add(INT_TBL_DAT_EST_POS,null);
                        vecReg.add(INT_TBL_DAT_COD_BAN,rst.getString("co_banChq"));
                        vecReg.add(INT_TBL_DAT_NOM_BAN,rst.getString("a4_tx_desLar"));
                        vecReg.add(INT_TBL_DAT_NUM_CTA,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_DAT_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_DAT_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_DAT_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_DAT_VAL_CHQ,rst.getString("nd_monChq"));
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
                blnMarTodChkTblDat=true;
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
        //Validar el "Cliente".
        if (txtCodCli.getText().equals(""))
        {
            switch (intTipCon)
            {
                case 1: //CxC (Todos los cobros).
                case 2: //CxC (Todos los cobros que no correspondan a retenciones).
                case 3: //CxC (Sólo los cobros que correspondan a retenciones).
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
                    break;
                case 4: //CxP (Todos los pagos).
                case 5: //CxP (Todos los pagos que no correspondan a retenciones).
                case 6: //CxP (Sólo los pagos que correspondan a retenciones).
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Proveedor</FONT> es obligatorio.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
                    break;
                case 7: //CxC y CxP (Todos los cobros y pagos).
                case 8: //CxC y CxP (Todos los cobros y pagos que no correspondan a retenciones).
                case 9: //CxC y CxP (Sólo los cobros y pagos que correspondan a retenciones).
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente/Proveedor</FONT> es obligatorio.<BR>Escriba o seleccione un cliente/proveedor y vuelva a intentarlo.</HTML>");
                    break;
            }
            txtCodCli.requestFocus();
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
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,"Mensaje del sistema Zafiro",javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta función obtiene las filas que han sido seleccionadas por el usuario en el JTable.
     * @return Un arreglo de enteros que contiene la(s) fila(s) seleccionadas.
     */
    public int[] getFilasSeleccionadas()
    {
        int i=0, j=0;
        int intRes[];
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                j++;
            }
        }
        intRes=new int[j];
        j=0;
        for (i=0; i<objTblMod.getRowCountTrue(); i++)
        {
            if (objTblMod.isChecked(i, INT_TBL_DAT_CHK))
            {
                intRes[j]=i;
                j++;
            }
        }
        return intRes;
    }
    
    /**
     * Esta función obtiene el código del cliente seleccionado en la ventana de consulta.
     * @return El código del cliente selecoiondo en la ventana de consulta
     */
    public String getCodigoCliente()
    {
        return txtCodCli.getText();
    }
    
    /**
     * Esta función obtiene el nombre del cliente seleccionado en la ventana de consulta
     * @return El título de la ventana de consulta.
     */
    public String getNombreCliente()
    {
        return txtDesLarCli.getText();
    }
    
    /**
     * Esta función obtiene el tipo de consulta que se utilizó para cargar los datos.
     * Es de acuerdo al tipo de consulta que se presentan los datos en el JTable.
     * Por ejemplo: si se recibe como parámetro 3 en el JTable sólo se presentarán los registros
     * correspondientes a CxC que sean retenciones.
     * @return El tipo de consulta que se utilizó para cargar los datos.
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>1: CxC (Todos los cobros).
     * <LI>2: CxC (Todos los cobros que no correspondan a retenciones).
     * <LI>3: CxC (Todos los cobros que correspondan a retenciones).
     * <LI>4: CxP (Todos los pagos).
     * <LI>5: CxP (Todos los pagos que no correspondan a retenciones).
     * <LI>6: CxP (Todos los pagos que correspondan a retenciones).
     * <LI>7: CxC y CxP (Todos los cobros y pagos).
     * <LI>8: CxC y CxP (Todos los cobros y pagos que no correspondan a retenciones).
     * <LI>9: CxC y CxP (Todos los cobros y pagos que correspondan a retenciones).
     * <LI>10: CxC (Todos los créditos).
     * <LI>11: CxC (Todos los créditos que no correspondan a retenciones).
     * <LI>12: CxC (Todos los créditos que correspondan a retenciones).
     * <LI>13: CxC (Todos los débitos).
     * <LI>14: CxC (Todos los débitos que no correspondan a retenciones).
     * <LI>15: CxC (Todos los débitos que correspondan a retenciones).
     * <LI>16: CxP (Todos los créditos).
     * <LI>17: CxP (Todos los créditos que no correspondan a retenciones).
     * <LI>18: CxP (Todos los créditos que correspondan a retenciones).
     * <LI>19: CxP (Todos los débitos).
     * <LI>20: CxP (Todos los débitos que no correspondan a retenciones).
     * <LI>21: CxP (Todos los débitos que correspondan a retenciones).
     * <LI>22: CxC y CxP (Todos los créditos).
     * <LI>23: CxC y CxP (Todos los créditos que no correspondan a retenciones).
     * <LI>24: CxC y CxP (Todos los créditos que correspondan a retenciones).
     * <LI>25: CxC y CxP (Todos los débitos).
     * <LI>26: CxC y CxP (Todos los débitos que no correspondan a retenciones).
     * <LI>27: CxC y CxP (Todos los débitos que correspondan a retenciones).
     * </UL>
     */
    public int getTipoConsulta()
    {
        return intTipCon;
    }
    
    /**
     * Esta función establece el tipo de consulta que se utilizará para cargar los datos.
     * Es de acuerdo al tipo de consulta que se presentan los datos en el JTable.
     * Por ejemplo: si se recibe como parámetro 3 en el JTable sólo se presentarán los registros
     * correspondientes a CxC que sean retenciones.
     * @param tipo El tipo de consulta que se utilizará para cargar los datos.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>1: CxC (Todos los cobros).
     * <LI>2: CxC (Todos los cobros que no correspondan a retenciones).
     * <LI>3: CxC (Sólo los cobros que correspondan a retenciones).
     * <LI>4: CxP (Todos los pagos).
     * <LI>5: CxP (Todos los pagos que no correspondan a retenciones).
     * <LI>6: CxP (Sólo los pagos que correspondan a retenciones).
     * <LI>7: CxC y CxP (Todos los cobros y pagos).
     * <LI>8: CxC y CxP (Todos los cobros y pagos que no correspondan a retenciones).
     * <LI>9: CxC y CxP (Sólo los cobros y pagos que correspondan a retenciones).
     * <LI>10: CxC (Todos los créditos).
     * <LI>11: CxC (Todos los créditos que no correspondan a retenciones).
     * <LI>12: CxC (Todos los créditos que correspondan a retenciones).
     * <LI>13: CxC (Todos los débitos).
     * <LI>14: CxC (Todos los débitos que no correspondan a retenciones).
     * <LI>15: CxC (Todos los débitos que correspondan a retenciones).
     * <LI>16: CxP (Todos los créditos).
     * <LI>17: CxP (Todos los créditos que no correspondan a retenciones).
     * <LI>18: CxP (Todos los créditos que correspondan a retenciones).
     * <LI>19: CxP (Todos los débitos).
     * <LI>20: CxP (Todos los débitos que no correspondan a retenciones).
     * <LI>21: CxP (Todos los débitos que correspondan a retenciones).
     * <LI>22: CxC y CxP (Todos los créditos).
     * <LI>23: CxC y CxP (Todos los créditos que no correspondan a retenciones).
     * <LI>24: CxC y CxP (Todos los créditos que correspondan a retenciones).
     * <LI>25: CxC y CxP (Todos los débitos).
     * <LI>26: CxC y CxP (Todos los débitos que no correspondan a retenciones).
     * <LI>27: CxC y CxP (Todos los débitos que correspondan a retenciones).
     * </UL>
     */
    public void setTipoConsulta(int tipo)
    {
        intTipCon=tipo;
        switch (intTipCon)
        {
            case 1: //CxC (Todos los cobros).
            case 10: //CxC (Todos los créditos).
            case 13: //CxC (Todos los débitos).
                lblCli.setText("Cliente:");
                lblVen.setText("Vendedor:");
                vcoCli.setCondicionesSQL(" AND a1.st_cli='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_ven='S'");
                chkMosRet.setEnabled(true);
                chkMosRet.setSelected(true);
                break;
            case 2: //CxC (Todos los cobros que no correspondan a retenciones).
            case 11: //CxC (Todos los créditos que no correspondan a retenciones).
            case 14: //CxC (Todos los débitos que no correspondan a retenciones).
                lblCli.setText("Cliente:");
                lblVen.setText("Vendedor:");
                vcoCli.setCondicionesSQL(" AND a1.st_cli='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_ven='S'");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(false);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_RET, tblDat);
                break;
            case 3: //CxC (Sólo los cobros que correspondan a retenciones).
            case 12: //CxC (Todos los créditos que correspondan a retenciones).
            case 15: //CxC (Todos los débitos que correspondan a retenciones).
                lblCli.setText("Cliente:");
                lblVen.setText("Vendedor:");
                vcoCli.setCondicionesSQL(" AND a1.st_cli='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_ven='S'");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(true);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BAN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_BAN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_CTA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_REC_CHQ, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_VEN_CHQ, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_CHQ, tblDat);
                break;
            case 4: //CxP (Todos los pagos).
            case 16: //CxP (Todos los créditos).
            case 19: //CxP (Todos los débitos).
                lblCli.setText("Proveedor:");
                lblVen.setText("Comprador:");
                vcoCli.setCondicionesSQL(" AND a1.st_prv='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_com='S'");
                chkMosRet.setEnabled(true);
                chkMosRet.setSelected(true);
                break;
            case 5: //CxP (Todos los pagos que no correspondan a retenciones).
            case 17: //CxP (Todos los créditos que no correspondan a retenciones).
            case 20: //CxP (Todos los débitos que no correspondan a retenciones).
                lblCli.setText("Proveedor:");
                lblVen.setText("Comprador:");
                vcoCli.setCondicionesSQL(" AND a1.st_prv='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_com='S'");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(false);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_RET, tblDat);
                break;
            case 6: //CxP (Sólo los pagos que correspondan a retenciones).
            case 18: //CxP (Todos los créditos que correspondan a retenciones).
            case 21: //CxP (Todos los débitos que correspondan a retenciones).
                lblCli.setText("Proveedor:");
                lblVen.setText("Comprador:");
                vcoCli.setCondicionesSQL(" AND a1.st_prv='S'");
                vcoVen.setCondicionesSQL(" AND a2.st_com='S'");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(true);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BAN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_BAN, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_CTA, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_REC_CHQ, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_VEN_CHQ, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VAL_CHQ, tblDat);
                break;
            case 7: //CxC y CxP (Todos los cobros y pagos).
            case 22: //CxC y CxP (Todos los créditos).
            case 25: //CxC y CxP (Todos los débitos).
                vcoVen.setCondicionesSQL(" AND (a2.st_ven='S' OR a2.st_com='S')");
                chkMosRet.setEnabled(true);
                chkMosRet.setSelected(true);
                break;
            case 8: //CxC y CxP (Todos los cobros y pagos que no correspondan a retenciones).
            case 23: //CxC y CxP (Todos los créditos que no correspondan a retenciones).
            case 26: //CxC y CxP (Todos los débitos que no correspondan a retenciones).
                vcoVen.setCondicionesSQL(" AND (a2.st_ven='S' OR a2.st_com='S')");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(false);
                break;
            case 9: //CxC y CxP (Sólo los cobros y pagos que correspondan a retenciones).
            case 24: //CxC y CxP (Todos los créditos que correspondan a retenciones).
            case 27: //CxC y CxP (Todos los débitos que correspondan a retenciones).
                vcoVen.setCondicionesSQL(" AND (a2.st_ven='S' OR a2.st_com='S')");
                chkMosRet.setEnabled(false);
                chkMosRet.setSelected(true);
                break;
        }
    }

    /**
     * Esta función establece la fila especificada como una fila procesada. Lo que hace internamente la función
     * es almacenar una letra "P" en la primera columna del JTable. A través de éste valor es posible determinar
     * si la fila ya fue procesada anteriormente o si todavía no ha sido procesada.
     * @param fila La fila que se marcará como procesada.
     */
    public void setFilaProcesada(int fila)
    {
        objTblMod.setValueAt("P", fila, INT_TBL_DAT_LIN);
    }

    /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     */
    public void limpiar()
    {
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        txtCodForPag.setText("");
        txtNomForPag.setText("");
        txtCodLoc.setText("");
        txtNomLoc.setText("");
        txtCodVen.setText("");
        txtNomVen.setText("");
        objSelFec.setCheckBoxChecked(false);
        chkMosSolDocVen.setSelected(false);
        objTblMod.removeAllRows();
        lblMsgSis.setText("Listo");
        txtCodCli.requestFocus();
    }

    /**
     * Esta función determina si está marcada o no la casilla de verificación "Mostrar sólo los documentos de contado".
     * @return true: Si la casilla está marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckedMostrarSoloDocumentosContado()
    {
        return chkMosSolDocCon.isSelected();
    }

    /**
     * Esta función establece si se debe marcar/desmarcar la casilla de verificación "Mostrar sólo los documentos de contado".
     * @param checked Valor booleano que determina si se debe marcar/desmarcar la casilla.
     */
    public void setCheckedMostrarSoloDocumentosContado(boolean checked)
    {
        chkMosSolDocCon.setSelected(checked);
    }

    /**
     * Esta función determina si está marcada o no la casilla de verificación "Mostrar las retenciones".
     * @return true: Si la casilla está marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckedMostrarRetenciones()
    {
        return chkMosRet.isSelected();
    }

    /**
     * Esta función establece si se debe marcar/desmarcar la casilla de verificación "Mostrar las retenciones".
     * @param checked Valor booleano que determina si se debe marcar/desmarcar la casilla.
     */
    public void setCheckedMostrarRetenciones(boolean checked)
    {
        chkMosRet.setSelected(checked);
    }

    /**
     * Esta función obtiene el número de serie de la retención recibida correspondiente al cliente seleccionado.
     * @return El número de serie de la retención recibida correspondiente al cliente seleccionado.
     */
    public String getNumeroSerieRetencion()
    {
        return strNumSerRet;
    }

    /**
     * Esta función obtiene el número de autorización de la retención recibida correspondiente al cliente seleccionado.
     * @return El número de autorización de la retención recibida correspondiente al cliente seleccionado.
     */
    public String getNumeroAutorizacionRetencion()
    {
        return strNumAutRet;
    }

    /**
     * Esta función obtiene la fecha de caducidad de la retención recibida correspondiente al cliente seleccionado.
     * @return La fecha de caducidad de la retención recibida correspondiente al cliente seleccionado.
     */
    public String getFechaCaducidadRetencion()
    {
        return strFecCadRet;
    }

    /**
     * Esta función obtiene el codigo del SRI de la retención recibida correspondiente al cliente seleccionado.
     * @return El codigo del SRI de la retención recibida correspondiente al cliente seleccionado.
     */
    public String getCodigoSRIRetencion()
    {
        return strCodSRIRet;
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
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_ABO_DOC:
                    strMsg="Abono";
                    break;
                case INT_TBL_DAT_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_DAT_EST_SOP:
                    strMsg="Estado de soporte";
                    break;
                case INT_TBL_DAT_SOP_ENT:
                    strMsg="Soporte entregado";
                    break;
                case INT_TBL_DAT_EST_POS:
                    strMsg="Estado de postfechado";
                    break;
                case INT_TBL_DAT_COD_BAN:
                    strMsg="Código del Banco";
                    break;
                case INT_TBL_DAT_NOM_BAN:
                    strMsg="Nombre del Banco";
                    break;
                case INT_TBL_DAT_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_DAT_NUM_CHQ:
                    strMsg="Número de cheque/retención";
                    break;
                case INT_TBL_DAT_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_DAT_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_DAT_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}