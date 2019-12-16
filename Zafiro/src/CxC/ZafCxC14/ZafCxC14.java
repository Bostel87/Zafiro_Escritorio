/*
 * ZafCom14.java
 *
 * Created on 26 de noviembre de 2005, 10:10 PM
 */
package CxC.ZafCxC14;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
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
public class ZafCxC14 extends javax.swing.JInternalFrame 
{
    /*Simbología:
     *  DAT: Datos
     *  CRE: Créditos/débitos
     *  CMR: Créditos/débitos - Movimiento del registro seleccionado
     *  PAG: Pagos realizados
     *  PMR: Pagos realizados - Movimiento del registro seleccionado
     **/
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_CLI=1;                     //Código del cliente.
    static final int INT_TBL_DAT_IDE_CLI=2;                     //Identificación del cliente.
    static final int INT_TBL_DAT_NOM_CLI=3;                     //Nombre del cliente.
    static final int INT_TBL_DAT_SAL_CLI=4;                     //Saldo del cliente.
    
    static final int INT_TBL_CRE_LIN=0;                         //Línea.
    static final int INT_TBL_CRE_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_CRE_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_CRE_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_CRE_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_CRE_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_CRE_COD_REG=6;                     //Código del registro (Sistema).
    static final int INT_TBL_CRE_NUM_DOC=7;                     //Número del documento.
    static final int INT_TBL_CRE_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_CRE_DIA_CRE=9;                     //Días de crédito.
    static final int INT_TBL_CRE_FEC_VEN=10;                    //Fecha de vencimiento.
    static final int INT_TBL_CRE_POR_RET=11;                    //Porcentaje de retención.
    static final int INT_TBL_CRE_VAL_DOC=12;                    //Valor del documento.
    static final int INT_TBL_CRE_ABO_DOC=13;                    //Abono del documento.
    static final int INT_TBL_CRE_VAL_PEN=14;                    //Valor pendiente.
    static final int INT_TBL_CRE_NUM_CTA=15;                    //Número de cuenta.
    static final int INT_TBL_CRE_NUM_CHQ=16;                    //Número de cheque.
    static final int INT_TBL_CRE_FEC_REC_CHQ=17;                //Fecha de recepción del cheque.
    static final int INT_TBL_CRE_FEC_VEN_CHQ=18;                //Fecha de vemcimiento del cheque.
    static final int INT_TBL_CRE_VAL_CHQ=19;                    //Valor del cheque.
    static final int INT_TBL_CRE_COD_PRO=20;                    //Codigo del tipo de protesto.
    static final int INT_TBL_CRE_DEL_PRO=21;                    //Descripcion larga del tipo de protesto.
    
    static final int INT_TBL_CMR_LIN=0;                         //Línea.
    static final int INT_TBL_CMR_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_CMR_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_CMR_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_CMR_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_CMR_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_CMR_COD_REG=6;                     //Código del registro (Sistema).
    static final int INT_TBL_CMR_NUM_DO1=7;                     //Número del documento.
    static final int INT_TBL_CMR_NUM_DO2=8;                     //Número del documento.
    static final int INT_TBL_CMR_FEC_DOC=9;                     //Fecha del documento.
    static final int INT_TBL_CMR_FEC_VEN=10;                    //Fecha de vencimiento.
    static final int INT_TBL_CMR_VAL_DOC=11;                    //Valor del documento.
    static final int INT_TBL_CMR_COD_CTA=12;                    //Código de la cuenta.
    static final int INT_TBL_CMR_NUM_CTA=13;                    //Número de la cuenta.
    static final int INT_TBL_CMR_NOM_CTA=14;                    //Nombre de la cuenta.
    static final int INT_TBL_CMR_COD_TIP_DOC_CON=15;            //Código del tipo de documento de control.
    static final int INT_TBL_CMR_DEC_TIP_DOC_CON=16;            //Descripción corta del tipo de documento de control.
    static final int INT_TBL_CMR_DEL_TIP_DOC_CON=17;            //Descripción larga del tipo de documento de control.
    static final int INT_TBL_CMR_COD_BAN=18;                    //Código del Banco.
    static final int INT_TBL_CMR_DEC_BAN=19;                    //Descripción corta del Banco.
    static final int INT_TBL_CMR_DEL_BAN=20;                    //Descripción larga del Banco.
    static final int INT_TBL_CMR_NUM_CTA_CHQ=21;                //Número de cuenta del cheque.
    static final int INT_TBL_CMR_NUM_CHQ=22;                    //Número de cheque.
    static final int INT_TBL_CMR_FEC_REC_CHQ=23;                //Fecha de recepción del cheque.
    static final int INT_TBL_CMR_FEC_VEN_CHQ=24;                //Fecha de vencimiento del cheque.
    
    static final int INT_TBL_PAG_LIN=0;                         //Línea.
    static final int INT_TBL_PAG_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_PAG_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_PAG_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_PAG_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_PAG_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_PAG_COD_REG=6;                     //Código del registro (Sistema).
    static final int INT_TBL_PAG_NUM_DO1=7;                     //Número del documento.
    static final int INT_TBL_PAG_NUM_DO2=8;                     //Número del documento.
    static final int INT_TBL_PAG_FEC_DOC=9;                     //Fecha del documento.
    static final int INT_TBL_PAG_FEC_VEN=10;                    //Fecha de vencimiento.
    static final int INT_TBL_PAG_VAL_DOC=11;                    //Valor del documento.
    static final int INT_TBL_PAG_COD_CTA=12;                    //Código de la cuenta.
    static final int INT_TBL_PAG_NUM_CTA=13;                    //Número de la cuenta.
    static final int INT_TBL_PAG_NOM_CTA=14;                    //Nombre de la cuenta.
    static final int INT_TBL_PAG_COD_TIP_DOC_CON=15;            //Código del tipo de documento de control.
    static final int INT_TBL_PAG_DEC_TIP_DOC_CON=16;            //Descripción corta del tipo de documento de control.
    static final int INT_TBL_PAG_DEL_TIP_DOC_CON=17;            //Descripción larga del tipo de documento de control.
    static final int INT_TBL_PAG_COD_BAN=18;                    //Código del Banco.
    static final int INT_TBL_PAG_DEC_BAN=19;                    //Descripción corta del Banco.
    static final int INT_TBL_PAG_DEL_BAN=20;                    //Descripción larga del Banco.
    static final int INT_TBL_PAG_NUM_CTA_CHQ=21;                //Número de cuenta del cheque.
    static final int INT_TBL_PAG_NUM_CHQ=22;                    //Número de cheque.
    static final int INT_TBL_PAG_FEC_REC_CHQ=23;                //Fecha de recpción del cheque.
    static final int INT_TBL_PAG_FEC_VEN_CHQ=24;                //Fecha de vencimiento del cheque.
    
    static final int INT_TBL_PMR_LIN=0;                         //Línea.
    static final int INT_TBL_PMR_COD_LOC=1;                     //Código del local.
    static final int INT_TBL_PMR_COD_TIP_DOC=2;                 //Código del tipo de documento.
    static final int INT_TBL_PMR_DEC_TIP_DOC=3;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_PMR_DEL_TIP_DOC=4;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_PMR_COD_DOC=5;                     //Código del documento (Sistema).
    static final int INT_TBL_PMR_COD_REG=6;                     //Código del registro (Sistema).
    static final int INT_TBL_PMR_NUM_DOC=7;                     //Número del documento.
    static final int INT_TBL_PMR_FEC_DOC=8;                     //Fecha del documento.
    static final int INT_TBL_PMR_DIA_CRE=9;                     //Días de crédito.
    static final int INT_TBL_PMR_FEC_VEN=10;                    //Fecha de vencimiento.
    static final int INT_TBL_PMR_POR_RET=11;                    //Porcentaje de retenciï¿½n.
    static final int INT_TBL_PMR_VAL_DOC=12;                    //Valor del documento.
    static final int INT_TBL_PMR_ABO_DOC=13;                    //Abono del documento.
    static final int INT_TBL_PMR_VAL_PEN=14;                    //Valor pendiente.
    static final int INT_TBL_PMR_NUM_CTA=15;                    //Número de cuenta.
    static final int INT_TBL_PMR_NUM_CHQ=16;                    //Número de cheque.
    static final int INT_TBL_PMR_FEC_REC_CHQ=17;                //Fecha de recepción del cheque.
    static final int INT_TBL_PMR_FEC_VEN_CHQ=18;                //Fecha de vemcimiento del cheque.
    static final int INT_TBL_PMR_VAL_CHQ=19;                    //Valor del cheque.
    static final int INT_TBL_PMR_COD_PRO=20;                    //Código del tipo de protesto.
    static final int INT_TBL_PMR_DEL_PRO=21;                    //Descripción larga del tipo de protesto.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModCre;
    private ZafTblMod objTblModCmr;
    private ZafTblMod objTblModPag;
    private ZafTblMod objTblModPmr;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                                     //true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli;                     //Contenido del campo al obtener el foco.
    private java.util.Date datFecSer;                           //Fecha del servidor.
   
    /** Crea una nueva instancia de la clase ZafCxC14. */
    public ZafCxC14(ZafParSis obj) 
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
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panRptChk = new javax.swing.JPanel();
        chkMosCreDeb = new javax.swing.JCheckBox();
        chkMosPagRea = new javax.swing.JCheckBox();
        panCreDeb = new javax.swing.JPanel();
        sppCreDeb = new javax.swing.JSplitPane();
        panCreDebReg = new javax.swing.JPanel();
        panCreDebFil = new javax.swing.JPanel();
        chkMosCre = new javax.swing.JCheckBox();
        chkMosDocAbi = new javax.swing.JCheckBox();
        chkMosDocVen = new javax.swing.JCheckBox();
        chkMosDeb = new javax.swing.JCheckBox();
        chkMosDocCer = new javax.swing.JCheckBox();
        chkMosRet = new javax.swing.JCheckBox();
        panCreDebFecDoc = new javax.swing.JPanel();
        lblCreDebFecDes = new javax.swing.JLabel();
        txtCreDebFecDes = new javax.swing.JTextField();
        lblCreDebFecHas = new javax.swing.JLabel();
        txtCreDebFecHas = new javax.swing.JTextField();
        spnCreDeb = new javax.swing.JScrollPane();
        tblCreDeb = new javax.swing.JTable();
        panCreDebMovReg = new javax.swing.JPanel();
        chkCreDebMosMovReg = new javax.swing.JCheckBox();
        spnCreDebMovReg = new javax.swing.JScrollPane();
        tblCreDebMovReg = new javax.swing.JTable();
        panPagRea = new javax.swing.JPanel();
        sppPagRea = new javax.swing.JSplitPane();
        panPagReaReg = new javax.swing.JPanel();
        panPagReaFil = new javax.swing.JPanel();
        panPagReaFecDoc = new javax.swing.JPanel();
        lblPagReaFecDes = new javax.swing.JLabel();
        txtPagReaFecDes = new javax.swing.JTextField();
        lblPagReaFecHas = new javax.swing.JLabel();
        txtPagReaFecHas = new javax.swing.JTextField();
        spnPagRea = new javax.swing.JScrollPane();
        tblPagRea = new javax.swing.JTable();
        panPagReaMovReg = new javax.swing.JPanel();
        chkPagReaMosMovReg = new javax.swing.JCheckBox();
        spnPagReaMovReg = new javax.swing.JScrollPane();
        tblPagReaMovReg = new javax.swing.JTable();
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
        setTitle("Título de la ventana"); // NOI18N
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
        lblTit.setText("Título de la ventana"); // NOI18N
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes"); // NOI18N
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado"); // NOI18N
        panFil.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Nombre de cliente"));
        panNomCli.setLayout(null);

        lblNomCliDes.setText("Desde:"); // NOI18N
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

        lblNomCliHas.setText("Hasta:"); // NOI18N
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
        panNomCli.setBounds(24, 64, 660, 52);

        lblCli.setText("Cliente:"); // NOI18N
        lblCli.setToolTipText("Beneficiario"); // NOI18N
        panFil.add(lblCli);
        lblCli.setBounds(24, 44, 120, 20);

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
        txtCodCli.setBounds(144, 44, 56, 20);

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
        txtDesLarCli.setBounds(200, 44, 460, 20);

        butCli.setText("..."); // NOI18N
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(660, 44, 20, 20);

        chkMosCliSalCer.setText("Mostrar los clientes que tienen saldo cero"); // NOI18N
        panFil.add(chkMosCliSalCer);
        chkMosCliSalCer.setBounds(4, 116, 400, 20);

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

        panRptChk.setPreferredSize(new java.awt.Dimension(614, 20));
        panRptChk.setLayout(new java.awt.GridLayout(1, 2));

        chkMosCreDeb.setSelected(true);
        chkMosCreDeb.setText("Mostrar los créditos/débitos del cliente seleccionado"); // NOI18N
        chkMosCreDeb.setToolTipText("Mostrar los créditos/débitos del cliente seleccionado"); // NOI18N
        chkMosCreDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreDebActionPerformed(evt);
            }
        });
        panRptChk.add(chkMosCreDeb);

        chkMosPagRea.setText("Mostrar los pagos realizados por el cliente seleccionado"); // NOI18N
        chkMosPagRea.setToolTipText("Mostrar los pagos realizados por el cliente seleccionado"); // NOI18N
        chkMosPagRea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPagReaActionPerformed(evt);
            }
        });
        panRptChk.add(chkMosPagRea);

        panRpt.add(panRptChk, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Saldos", panRpt);

        panCreDeb.setLayout(new java.awt.BorderLayout());

        sppCreDeb.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppCreDeb.setResizeWeight(0.8);
        sppCreDeb.setOneTouchExpandable(true);

        panCreDebReg.setLayout(new java.awt.BorderLayout());

        panCreDebFil.setPreferredSize(new java.awt.Dimension(0, 92));
        panCreDebFil.setLayout(null);

        chkMosCre.setSelected(true);
        chkMosCre.setText("Mostrar los créditos"); // NOI18N
        chkMosCre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosCreActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosCre);
        chkMosCre.setBounds(0, 0, 228, 20);

        chkMosDocAbi.setSelected(true);
        chkMosDocAbi.setText("Mostrar los documentos abiertos"); // NOI18N
        chkMosDocAbi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocAbiActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosDocAbi);
        chkMosDocAbi.setBounds(228, 0, 228, 20);

        chkMosDocVen.setText("Mostrar sólo los documentos vencidos"); // NOI18N
        chkMosDocVen.setToolTipText("Mostrar sólo los documentos vencidos"); // NOI18N
        chkMosDocVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocVenActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosDocVen);
        chkMosDocVen.setBounds(456, 0, 228, 20);

        chkMosDeb.setSelected(true);
        chkMosDeb.setText("Mostrar los débitos"); // NOI18N
        chkMosDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDebActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosDeb);
        chkMosDeb.setBounds(0, 20, 228, 20);

        chkMosDocCer.setText("Mostrar los documentos cerrados"); // NOI18N
        chkMosDocCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocCerActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosDocCer);
        chkMosDocCer.setBounds(228, 20, 228, 20);

        chkMosRet.setSelected(true);
        chkMosRet.setText("Mostrar las retenciones"); // NOI18N
        chkMosRet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosRetActionPerformed(evt);
            }
        });
        panCreDebFil.add(chkMosRet);
        chkMosRet.setBounds(456, 20, 228, 20);

        panCreDebFecDoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha del documento"));
        panCreDebFecDoc.setLayout(null);

        lblCreDebFecDes.setText("Desde:"); // NOI18N
        panCreDebFecDoc.add(lblCreDebFecDes);
        lblCreDebFecDes.setBounds(12, 20, 44, 20);
        panCreDebFecDoc.add(txtCreDebFecDes);
        txtCreDebFecDes.setBounds(56, 20, 268, 20);

        lblCreDebFecHas.setText("Hasta:"); // NOI18N
        panCreDebFecDoc.add(lblCreDebFecHas);
        lblCreDebFecHas.setBounds(336, 20, 44, 20);
        panCreDebFecDoc.add(txtCreDebFecHas);
        txtCreDebFecHas.setBounds(380, 20, 268, 20);

        panCreDebFil.add(panCreDebFecDoc);
        panCreDebFecDoc.setBounds(0, 40, 660, 52);

        panCreDebReg.add(panCreDebFil, java.awt.BorderLayout.NORTH);

        tblCreDeb.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCreDeb.setViewportView(tblCreDeb);

        panCreDebReg.add(spnCreDeb, java.awt.BorderLayout.CENTER);

        sppCreDeb.setTopComponent(panCreDebReg);

        panCreDebMovReg.setLayout(new java.awt.BorderLayout());

        chkCreDebMosMovReg.setText("Mostrar el movimiento del documento seleccionado"); // NOI18N
        chkCreDebMosMovReg.setPreferredSize(new java.awt.Dimension(269, 20));
        chkCreDebMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkCreDebMosMovRegActionPerformed(evt);
            }
        });
        panCreDebMovReg.add(chkCreDebMosMovReg, java.awt.BorderLayout.NORTH);

        tblCreDebMovReg.setModel(new javax.swing.table.DefaultTableModel(
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
        spnCreDebMovReg.setViewportView(tblCreDebMovReg);

        panCreDebMovReg.add(spnCreDebMovReg, java.awt.BorderLayout.CENTER);

        sppCreDeb.setBottomComponent(panCreDebMovReg);

        panCreDeb.add(sppCreDeb, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Cr\u00e9ditos/D\u00e9bitos", panCreDeb);

        panPagRea.setLayout(new java.awt.BorderLayout());

        sppPagRea.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppPagRea.setResizeWeight(0.8);
        sppPagRea.setOneTouchExpandable(true);

        panPagReaReg.setLayout(new java.awt.BorderLayout());

        panPagReaFil.setPreferredSize(new java.awt.Dimension(0, 52));
        panPagReaFil.setLayout(null);

        panPagReaFecDoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Fecha del documento"));
        panPagReaFecDoc.setLayout(null);

        lblPagReaFecDes.setText("Desde:"); // NOI18N
        panPagReaFecDoc.add(lblPagReaFecDes);
        lblPagReaFecDes.setBounds(12, 20, 44, 20);
        panPagReaFecDoc.add(txtPagReaFecDes);
        txtPagReaFecDes.setBounds(56, 20, 268, 20);

        lblPagReaFecHas.setText("Hasta:"); // NOI18N
        panPagReaFecDoc.add(lblPagReaFecHas);
        lblPagReaFecHas.setBounds(336, 20, 44, 20);
        panPagReaFecDoc.add(txtPagReaFecHas);
        txtPagReaFecHas.setBounds(380, 20, 268, 20);

        panPagReaFil.add(panPagReaFecDoc);
        panPagReaFecDoc.setBounds(0, 0, 660, 52);

        panPagReaReg.add(panPagReaFil, java.awt.BorderLayout.NORTH);

        tblPagRea.setModel(new javax.swing.table.DefaultTableModel(
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
        spnPagRea.setViewportView(tblPagRea);

        panPagReaReg.add(spnPagRea, java.awt.BorderLayout.CENTER);

        sppPagRea.setTopComponent(panPagReaReg);

        panPagReaMovReg.setLayout(new java.awt.BorderLayout());

        chkPagReaMosMovReg.setText("Mostrar el movimiento del documento seleccionado"); // NOI18N
        chkPagReaMosMovReg.setPreferredSize(new java.awt.Dimension(269, 20));
        chkPagReaMosMovReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkPagReaMosMovRegActionPerformed(evt);
            }
        });
        panPagReaMovReg.add(chkPagReaMosMovReg, java.awt.BorderLayout.NORTH);

        tblPagReaMovReg.setModel(new javax.swing.table.DefaultTableModel(
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
        spnPagReaMovReg.setViewportView(tblPagReaMovReg);

        panPagReaMovReg.add(spnPagReaMovReg, java.awt.BorderLayout.CENTER);

        sppPagRea.setBottomComponent(panPagReaMovReg);

        panPagRea.add(sppPagRea, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Pagos realizados", panPagRea);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar"); // NOI18N
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado."); // NOI18N
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar"); // NOI18N
        butCer.setToolTipText("Cierra la ventana."); // NOI18N
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

        lblMsgSis.setText("Listo"); // NOI18N
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
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkPagReaMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkPagReaMosMovRegActionPerformed
        if (chkPagReaMosMovReg.isSelected())
            cargarPagReaMovReg();
        else
            objTblModPmr.removeAllRows();
    }//GEN-LAST:event_chkPagReaMosMovRegActionPerformed

    private void chkCreDebMosMovRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkCreDebMosMovRegActionPerformed
        if (chkCreDebMosMovReg.isSelected())
            cargarCreDebMovReg();
        else
            objTblModCmr.removeAllRows();
    }//GEN-LAST:event_chkCreDebMosMovRegActionPerformed

    private void chkMosPagReaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPagReaActionPerformed
        if (chkMosPagRea.isSelected())
            cargarPagRea();
        else
            objTblModPag.removeAllRows();
    }//GEN-LAST:event_chkMosPagReaActionPerformed

    private void chkMosCreDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreDebActionPerformed
        if (chkMosCreDeb.isSelected())
            cargarCreDeb();
        else
            objTblModCre.removeAllRows();
    }//GEN-LAST:event_chkMosCreDebActionPerformed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked
        //Abrir la opción seleccionada al dar doble click.
        if (evt.getClickCount()==2)
        {
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblCreDeb.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(2);
                tblCreDeb.setRowSelectionInterval(0, 0);
                tblCreDeb.requestFocus();
            }
        }
    }//GEN-LAST:event_tblDatMouseClicked

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode()==java.awt.event.KeyEvent.VK_ENTER)
        {
            evt.consume();
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblCreDeb.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(2);
                tblCreDeb.setRowSelectionInterval(0, 0);
                tblCreDeb.requestFocus();
            }
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void chkMosRetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosRetActionPerformed
        cargarCreDeb();
    }//GEN-LAST:event_chkMosRetActionPerformed

    private void chkMosDocVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocVenActionPerformed
        if (chkMosDocCer.isSelected())
            chkMosDocCer.setSelected(false);
        cargarCreDeb();
    }//GEN-LAST:event_chkMosDocVenActionPerformed

    private void chkMosDocCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocCerActionPerformed
        if (!chkMosDocAbi.isSelected())
        {
            chkMosDocCer.setSelected(true);
        }
        else
        {
            cargarCreDeb();
        }
    }//GEN-LAST:event_chkMosDocCerActionPerformed

    private void chkMosDocAbiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocAbiActionPerformed
        if (!chkMosDocCer.isSelected())
        {
            chkMosDocAbi.setSelected(true);
        }
        else
        {
            cargarCreDeb();
        }
    }//GEN-LAST:event_chkMosDocAbiActionPerformed

    private void chkMosDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDebActionPerformed
        if (!chkMosCre.isSelected())
        {
            chkMosDeb.setSelected(true);
        }
        else
        {
            cargarCreDeb();
        }
    }//GEN-LAST:event_chkMosDebActionPerformed

    private void chkMosCreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosCreActionPerformed
        if (!chkMosDeb.isSelected())
        {
            chkMosCre.setSelected(true);
        }
        else
        {
            cargarCreDeb();
        }
    }//GEN-LAST:event_chkMosCreActionPerformed

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
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkCreDebMosMovReg;
    private javax.swing.JCheckBox chkMosCliSalCer;
    private javax.swing.JCheckBox chkMosCre;
    private javax.swing.JCheckBox chkMosCreDeb;
    private javax.swing.JCheckBox chkMosDeb;
    private javax.swing.JCheckBox chkMosDocAbi;
    private javax.swing.JCheckBox chkMosDocCer;
    private javax.swing.JCheckBox chkMosDocVen;
    private javax.swing.JCheckBox chkMosPagRea;
    private javax.swing.JCheckBox chkMosRet;
    private javax.swing.JCheckBox chkPagReaMosMovReg;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblCreDebFecDes;
    private javax.swing.JLabel lblCreDebFecHas;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblPagReaFecDes;
    private javax.swing.JLabel lblPagReaFecHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCreDeb;
    private javax.swing.JPanel panCreDebFecDoc;
    private javax.swing.JPanel panCreDebFil;
    private javax.swing.JPanel panCreDebMovReg;
    private javax.swing.JPanel panCreDebReg;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panPagRea;
    private javax.swing.JPanel panPagReaFecDoc;
    private javax.swing.JPanel panPagReaFil;
    private javax.swing.JPanel panPagReaMovReg;
    private javax.swing.JPanel panPagReaReg;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptChk;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnCreDeb;
    private javax.swing.JScrollPane spnCreDebMovReg;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnPagRea;
    private javax.swing.JScrollPane spnPagReaMovReg;
    private javax.swing.JSplitPane sppCreDeb;
    private javax.swing.JSplitPane sppPagRea;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCreDeb;
    private javax.swing.JTable tblCreDebMovReg;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblPagRea;
    private javax.swing.JTable tblPagReaMovReg;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCreDebFecDes;
    private javax.swing.JTextField txtCreDebFecHas;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    private javax.swing.JTextField txtPagReaFecDes;
    private javax.swing.JTextField txtPagReaFecHas;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.17");
            lblTit.setText(strAux);
            //Configurar objetos.
            if (objParSis.getCodigoMenu()==515)
            {
                optTod.setText("Todos los proveedores");
                optFil.setText("Sólo los proveedores que cumplan el criterio seleccionado");
                lblCli.setText("Proveedor:");
                panNomCli.setBorder(new javax.swing.border.TitledBorder("Nombre de proveedor"));
                chkMosCreDeb.setText("Mostrar los créditos/débitos del proveedor seleccionado");
                chkMosCreDeb.setToolTipText("Mostrar los créditos/débitos del proveedor seleccionado");
                chkMosPagRea.setText("Mostrar los pagos realizados al proveedor seleccionado");
                chkMosPagRea.setToolTipText("Mostrar los pagos realizados al proveedor seleccionado");
                chkMosCliSalCer.setText("Mostrar los proveedores que tienen saldo cero");
            }
            //Configurar las ZafVenCon.
            configurarVenConCli();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblCreDeb();
            configurarTblCreDebMovReg();
            configurarTblPagRea();
            configurarTblPagReaMovReg();
            //Obtener la fecha del servidor. Luego de un análisis se tomó la decisión de obtener la fecha del servidor al cargar el formulario y no cada vez que se efectuaba una consulta.
            datFecSer=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            if (datFecSer==null)
                datFecSer=new java.util.Date();
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
            vecCab=new Vector(16);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_SAL_CLI,"Saldo");
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
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(102);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(395);
            tcmAux.getColumn(INT_TBL_DAT_SAL_CLI).setPreferredWidth(80);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_SAL_CLI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
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
     * Esta función configura el JTable "tblCreDeb".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCreDeb()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CRE_LIN,"");
            vecCab.add(INT_TBL_CRE_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CRE_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_CRE_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_CRE_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_CRE_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_CRE_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_CRE_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_CRE_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_CRE_DIA_CRE,"Día.Cré.");
            vecCab.add(INT_TBL_CRE_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_CRE_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_CRE_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_CRE_ABO_DOC,"Abono");
            vecCab.add(INT_TBL_CRE_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_CRE_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_CRE_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_CRE_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_CRE_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_CRE_VAL_CHQ,"Val.Chq.");
            vecCab.add(INT_TBL_CRE_COD_PRO,"Cód.Pro.");
            vecCab.add(INT_TBL_CRE_DEL_PRO,"Tipo de protesto");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModCre=new ZafTblMod();
            objTblModCre.setHeader(vecCab);
            tblCreDeb.setModel(objTblModCre);
            //Configurar JTable: Establecer tipo de selección.
            tblCreDeb.setRowSelectionAllowed(true);
            tblCreDeb.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCreDeb);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCreDeb.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCreDeb.getColumnModel();
            tcmAux.getColumn(INT_TBL_CRE_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CRE_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CRE_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CRE_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_CRE_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CRE_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CRE_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CRE_FEC_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CRE_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_CRE_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CRE_ABO_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CRE_VAL_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CRE_NUM_CTA).setPreferredWidth(78);
            tcmAux.getColumn(INT_TBL_CRE_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CRE_FEC_REC_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CRE_FEC_VEN_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CRE_VAL_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CRE_COD_PRO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CRE_DEL_PRO).setPreferredWidth(100);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCreDeb.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCre.addSystemHiddenColumn(INT_TBL_CRE_COD_TIP_DOC, tblCreDeb);
            objTblModCre.addSystemHiddenColumn(INT_TBL_CRE_COD_DOC, tblCreDeb);
            objTblModCre.addSystemHiddenColumn(INT_TBL_CRE_COD_REG, tblCreDeb);
            objTblModCre.addSystemHiddenColumn(INT_TBL_CRE_COD_PRO, tblCreDeb);
            objTblModCre.addSystemHiddenColumn(INT_TBL_CRE_DEL_PRO, tblCreDeb);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblCreDeb.getTableHeader().addMouseMotionListener(new ZafMouMotAdaCre());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblCreDeb);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_CRE_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CRE_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CRE_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CRE_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CRE_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_CRE_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblCreDeb);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblCreDeb.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisCre());
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
     * Esta función configura el JTable "tblCreDebMovReg".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblCreDebMovReg()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_CMR_LIN,"");
            vecCab.add(INT_TBL_CMR_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_CMR_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_CMR_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_CMR_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_CMR_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_CMR_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_CMR_NUM_DO1,"Núm.Doc1.");
            vecCab.add(INT_TBL_CMR_NUM_DO2,"Núm.Doc2.");
            vecCab.add(INT_TBL_CMR_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_CMR_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_CMR_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_CMR_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_CMR_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_CMR_NOM_CTA,"Cuenta");
            vecCab.add(INT_TBL_CMR_COD_TIP_DOC_CON,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_CMR_DEC_TIP_DOC_CON,"Tip.Doc.");
            vecCab.add(INT_TBL_CMR_DEL_TIP_DOC_CON,"Tipo de documento");
            vecCab.add(INT_TBL_CMR_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_CMR_DEC_BAN,"Banco");
            vecCab.add(INT_TBL_CMR_DEL_BAN,"Nombre del Banco");
            vecCab.add(INT_TBL_CMR_NUM_CTA_CHQ,"Núm.Cta.Chq.");
            vecCab.add(INT_TBL_CMR_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_CMR_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_CMR_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModCmr=new ZafTblMod();
            objTblModCmr.setHeader(vecCab);
            tblCreDebMovReg.setModel(objTblModCmr);
            //Configurar JTable: Establecer tipo de selección.
            tblCreDebMovReg.setRowSelectionAllowed(true);
            tblCreDebMovReg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblCreDebMovReg);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblCreDebMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblCreDebMovReg.getColumnModel();
            tcmAux.getColumn(INT_TBL_CMR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CMR_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CMR_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_CMR_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_NUM_DO1).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_CMR_NUM_DO2).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_CMR_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CMR_FEC_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CMR_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CMR_COD_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_CMR_NOM_CTA).setPreferredWidth(81);
            tcmAux.getColumn(INT_TBL_CMR_COD_TIP_DOC_CON).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_DEC_TIP_DOC_CON).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CMR_DEL_TIP_DOC_CON).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_CMR_COD_BAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CMR_DEC_BAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_CMR_DEL_BAN).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_CMR_NUM_CTA_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CMR_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CMR_FEC_REC_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CMR_FEC_VEN_CHQ).setPreferredWidth(80);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblCreDebMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_TIP_DOC, tblCreDebMovReg);
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_DOC, tblCreDebMovReg);
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_REG, tblCreDebMovReg);
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_CTA, tblCreDebMovReg);
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_TIP_DOC_CON, tblCreDebMovReg);
            objTblModCmr.addSystemHiddenColumn(INT_TBL_CMR_COD_BAN, tblCreDebMovReg);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblCreDebMovReg.getTableHeader().addMouseMotionListener(new ZafMouMotAdaCmr());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblCreDebMovReg);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_CMR_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CMR_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblCreDebMovReg);
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
     * Esta función configura el JTable "tblPagRea".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblPagRea()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_PAG_LIN,"");
            vecCab.add(INT_TBL_PAG_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_PAG_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_PAG_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_PAG_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_PAG_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_PAG_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_PAG_NUM_DO1,"Núm.Doc1.");
            vecCab.add(INT_TBL_PAG_NUM_DO2,"Núm.Doc2.");
            vecCab.add(INT_TBL_PAG_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_PAG_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_PAG_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_PAG_COD_CTA,"Cód.Cta.");
            vecCab.add(INT_TBL_PAG_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_PAG_NOM_CTA,"Cuenta");
            vecCab.add(INT_TBL_PAG_COD_TIP_DOC_CON,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_PAG_DEC_TIP_DOC_CON,"Tip.Doc.");
            vecCab.add(INT_TBL_PAG_DEL_TIP_DOC_CON,"Tipo de documento");
            vecCab.add(INT_TBL_PAG_COD_BAN,"Cód.Ban.");
            vecCab.add(INT_TBL_PAG_DEC_BAN,"Banco");
            vecCab.add(INT_TBL_PAG_DEL_BAN,"Nombre del Banco");
            vecCab.add(INT_TBL_PAG_NUM_CTA_CHQ,"Núm.Cta.Chq.");
            vecCab.add(INT_TBL_PAG_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_PAG_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_PAG_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModPag=new ZafTblMod();
            objTblModPag.setHeader(vecCab);
            tblPagRea.setModel(objTblModPag);
            //Configurar JTable: Establecer tipo de selección.
            tblPagRea.setRowSelectionAllowed(true);
            tblPagRea.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblPagRea);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblPagRea.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblPagRea.getColumnModel();
            tcmAux.getColumn(INT_TBL_PAG_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_PAG_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PAG_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_PAG_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_COD_REG).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_NUM_DO1).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_PAG_NUM_DO2).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_PAG_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PAG_FEC_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PAG_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PAG_COD_CTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_NUM_CTA).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_PAG_NOM_CTA).setPreferredWidth(81);
            tcmAux.getColumn(INT_TBL_PAG_COD_TIP_DOC_CON).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_DEC_TIP_DOC_CON).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PAG_DEL_TIP_DOC_CON).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_PAG_COD_BAN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PAG_DEC_BAN).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PAG_DEL_BAN).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_PAG_NUM_CTA_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PAG_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PAG_FEC_REC_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PAG_FEC_VEN_CHQ).setPreferredWidth(80);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblPagRea.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_TIP_DOC, tblPagRea);
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_DOC, tblPagRea);
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_REG, tblPagRea);
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_CTA, tblPagRea);
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_TIP_DOC_CON, tblPagRea);
            objTblModPag.addSystemHiddenColumn(INT_TBL_PAG_COD_BAN, tblPagRea);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblPagRea.getTableHeader().addMouseMotionListener(new ZafMouMotAdaPag());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblPagRea);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_PAG_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_PAG_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblPagRea);
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblPagRea.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSelLisPag());
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
     * Esta función configura el JTable "tblPagReaMovReg".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblPagReaMovReg()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_PMR_LIN,"");
            vecCab.add(INT_TBL_PMR_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_PMR_COD_TIP_DOC,"Cód.Tip.Doc");
            vecCab.add(INT_TBL_PMR_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_PMR_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_PMR_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_PMR_COD_REG,"Cód.Reg.");
            vecCab.add(INT_TBL_PMR_NUM_DOC,"Num.Doc.");
            vecCab.add(INT_TBL_PMR_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_PMR_DIA_CRE,"Día.Cre.");
            vecCab.add(INT_TBL_PMR_FEC_VEN,"Fec.Ven.");
            vecCab.add(INT_TBL_PMR_POR_RET,"% Ret.");
            vecCab.add(INT_TBL_PMR_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_PMR_ABO_DOC,"Abono");
            vecCab.add(INT_TBL_PMR_VAL_PEN,"Val.Pen.");
            vecCab.add(INT_TBL_PMR_NUM_CTA,"Núm.Cta.");
            vecCab.add(INT_TBL_PMR_NUM_CHQ,"Núm.Chq.");
            vecCab.add(INT_TBL_PMR_FEC_REC_CHQ,"Fec.Rec.Chq.");
            vecCab.add(INT_TBL_PMR_FEC_VEN_CHQ,"Fec.Ven.Chq.");
            vecCab.add(INT_TBL_PMR_VAL_CHQ,"Val.Chq.");
            vecCab.add(INT_TBL_PMR_COD_PRO,"Cód.Pro.");
            vecCab.add(INT_TBL_PMR_DEL_PRO,"Tipo de protesto");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModPmr=new ZafTblMod();
            objTblModPmr.setHeader(vecCab);
            tblPagReaMovReg.setModel(objTblModPmr);
            //Configurar JTable: Establecer tipo de selección.
            tblPagReaMovReg.setRowSelectionAllowed(true);
            tblPagReaMovReg.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblPagReaMovReg);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblPagReaMovReg.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblPagReaMovReg.getColumnModel();
            tcmAux.getColumn(INT_TBL_PMR_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_PMR_COD_LOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PMR_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_PMR_DEL_TIP_DOC).setPreferredWidth(22);
            tcmAux.getColumn(INT_TBL_PMR_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PMR_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PMR_DIA_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PMR_FEC_VEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PMR_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_PMR_VAL_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PMR_ABO_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PMR_VAL_PEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PMR_NUM_CTA).setPreferredWidth(78);
            tcmAux.getColumn(INT_TBL_PMR_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_PMR_FEC_REC_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PMR_FEC_VEN_CHQ).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_PMR_VAL_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_PMR_COD_PRO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_PMR_DEL_PRO).setPreferredWidth(100);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
//            tcmAux.getColumn(INT_TBL_DAT_BUT_TIP_RET).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblPagReaMovReg.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModPmr.addSystemHiddenColumn(INT_TBL_PMR_COD_TIP_DOC, tblPagReaMovReg);
            objTblModPmr.addSystemHiddenColumn(INT_TBL_PMR_COD_DOC, tblPagReaMovReg);
            objTblModPmr.addSystemHiddenColumn(INT_TBL_PMR_COD_REG, tblPagReaMovReg);
            objTblModPmr.addSystemHiddenColumn(INT_TBL_PMR_COD_PRO, tblPagReaMovReg);
            objTblModPmr.addSystemHiddenColumn(INT_TBL_PMR_DEL_PRO, tblPagReaMovReg);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblPagReaMovReg.getTableHeader().addMouseMotionListener(new ZafMouMotAdaPmr());
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblPagReaMovReg);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_PMR_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_PMR_POR_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PMR_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PMR_ABO_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PMR_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PMR_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
//            objTblOrd=new ZafTblOrd(tblDat);
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
                strAux="";
                if (txtCodCli.getText().length()>0)
                    strAux+=" AND a1.co_cli=" + txtCodCli.getText();
                if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a3.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a3.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a3.co_cli, a3.tx_ide, a3.tx_nom, SUM(a2.mo_pag+a2.nd_abo) AS nd_sal";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                {
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                }
                else
                {
                    strSQL+=" INNER JOIN tbr_cliLoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_cli=a4.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                }
                if (objParSis.getCodigoMenu()==319)
                    strSQL+=" AND a3.st_cli='S'";
                else
                    strSQL+=" AND a3.st_prv='S'";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=strAux;
                strSQL+=" GROUP BY a3.co_cli, a3.tx_ide, a3.tx_nom";
                if (!chkMosCliSalCer.isSelected())
                    strSQL+=" HAVING SUM(a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" ORDER BY a3.tx_nom";
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
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_SAL_CLI,rst.getString("nd_sal"));
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
     * Esta función permite cargar los créditos/débitos del cliente/proveedor seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCreDeb()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                    strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen";
                    strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.co_proChq, a4.tx_desLar AS a4_tx_desLar";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_proChq=a4.co_reg)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                    if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        strSQL+=" AND a1.co_loc=" + intCodLoc;
                    }
                    strSQL+=" AND a1.co_cli=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI);
                    strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                    strSQL+=" AND a2.st_reg IN ('A','C')";
                    if ( !(chkMosCre.isSelected() && chkMosDeb.isSelected()) )
                    {
                        if (chkMosCre.isSelected())
                            strSQL+=" AND a3.tx_natDoc='I'";
                        else
                            strSQL+=" AND a3.tx_natDoc='E'";
                    }
                    if ( !(chkMosDocAbi.isSelected() && chkMosDocCer.isSelected()) )
                    {
                        if (chkMosDocAbi.isSelected())
                            strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                        else
                            strSQL+=" AND (a2.mo_pag+a2.nd_abo)=0";
                    }
                    if (chkMosDocVen.isSelected())
                    {
                        strAux=objUti.formatearFecha(datFecSer, objParSis.getFormatoFechaHoraBaseDatos());
                        strSQL+=" AND a2.fe_ven<='" + strAux + "'";
                    }
                    if (!chkMosRet.isSelected())
                    {
                        strSQL+=" AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                    }
                    strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_CRE_LIN,"");
                        vecReg.add(INT_TBL_CRE_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_CRE_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_CRE_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_CRE_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_CRE_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_CRE_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_CRE_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_CRE_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_CRE_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_CRE_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_CRE_POR_RET,rst.getString("nd_porRet"));
                        vecReg.add(INT_TBL_CRE_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_CRE_ABO_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_CRE_VAL_PEN,rst.getString("nd_pen"));
                        vecReg.add(INT_TBL_CRE_NUM_CTA,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_CRE_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_CRE_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_CRE_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_CRE_VAL_CHQ,rst.getString("nd_monChq"));
                        vecReg.add(INT_TBL_CRE_COD_PRO,rst.getString("co_proChq"));
                        vecReg.add(INT_TBL_CRE_DEL_PRO,rst.getString("a4_tx_desLar"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModCre.setData(vecDat);
                    tblCreDeb.setModel(objTblModCre);
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
     * Esta función permite cargar el movimiento del crédito/débito seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCreDebMovReg()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            if (tblCreDeb.getSelectedRow()!=-1)
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a2.co_locpag, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc1, a1.ne_numDoc2";
                    strSQL+=", a1.fe_doc, a1.fe_ven, a2.nd_abo, a1.co_cta, a4.tx_codCta, a4.tx_desLar AS a4_tx_desLar, a2.co_tipDocCon";
                    strSQL+=", a5.tx_desCor AS a5_tx_desCor, a5.tx_desLar AS a5_tx_desLar, a2.co_banChq, a6.tx_desCor AS a6_tx_desCor";
                    strSQL+=", a6.tx_desLar AS a6_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cta=a4.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDocCon=a5.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a6 ON (a2.co_banChq=a6.co_reg)";
                    strSQL+=" WHERE a2.co_emp=" + intCodEmp;
                    strSQL+=" AND a2.co_locPag=" + objTblModCre.getValueAt(tblCreDeb.getSelectedRow(), INT_TBL_CRE_COD_LOC);
                    strSQL+=" AND a2.co_tipDocPag=" + objTblModCre.getValueAt(tblCreDeb.getSelectedRow(), INT_TBL_CRE_COD_TIP_DOC);
                    strSQL+=" AND a2.co_docPag=" + objTblModCre.getValueAt(tblCreDeb.getSelectedRow(), INT_TBL_CRE_COD_DOC);
                    strSQL+=" AND a2.co_regPag=" + objTblModCre.getValueAt(tblCreDeb.getSelectedRow(), INT_TBL_CRE_COD_REG);
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_CMR_LIN,"");
                        vecReg.add(INT_TBL_CMR_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_CMR_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_CMR_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_CMR_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_CMR_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_CMR_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_CMR_NUM_DO1,rst.getString("ne_numDoc1"));
                        vecReg.add(INT_TBL_CMR_NUM_DO2,rst.getString("ne_numDoc2"));
                        vecReg.add(INT_TBL_CMR_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_CMR_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_CMR_VAL_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_CMR_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_TBL_CMR_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_CMR_NOM_CTA,rst.getString("a4_tx_desLar"));
                        vecReg.add(INT_TBL_CMR_COD_TIP_DOC_CON,rst.getString("co_tipDocCon"));
                        vecReg.add(INT_TBL_CMR_DEC_TIP_DOC_CON,rst.getString("a5_tx_desCor"));
                        vecReg.add(INT_TBL_CMR_DEL_TIP_DOC_CON,rst.getString("a5_tx_desLar"));
                        vecReg.add(INT_TBL_CMR_COD_BAN,rst.getString("co_banChq"));
                        vecReg.add(INT_TBL_CMR_DEC_BAN,rst.getString("a6_tx_desCor"));
                        vecReg.add(INT_TBL_CMR_DEL_BAN,rst.getString("a6_tx_desLar"));
                        vecReg.add(INT_TBL_CMR_NUM_CTA_CHQ,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_CMR_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_CMR_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_CMR_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModCmr.setData(vecDat);
                    tblCreDebMovReg.setModel(objTblModCmr);
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
     * Esta funciónn permite cargar los pagos realizados por el cliente/proveedor seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPagRea()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblDat.getSelectedRow()!=-1)
            {
//              objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a2.co_locpag, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc1, a1.ne_numDoc2";
                    strSQL+=", a1.fe_doc, a1.fe_ven, a2.nd_abo, a1.co_cta, a5.tx_codCta, a5.tx_desLar AS a5_tx_desLar, a2.co_tipDocCon";
                    strSQL+=", a6.tx_desCor AS a6_tx_desCor, a6.tx_desLar AS a6_tx_desLar, a2.co_banChq, a7.tx_desCor AS a7_tx_desCor";
                    strSQL+=", a7.tx_desLar AS a7_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq";
                    strSQL+=" FROM tbm_cabPag AS a1";
                    strSQL+=" INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_plaCta AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_cta=a5.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDocCon=a6.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a7 ON (a2.co_banChq=a7.co_reg)";
                    strSQL+=" WHERE a3.co_emp=" + intCodEmp;
                    //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                    if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        strSQL+=" AND a3.co_loc=" + intCodLoc;
                    }
                    strSQL+=" AND a3.co_cli=" + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_CLI);
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_PAG_LIN,"");
                        vecReg.add(INT_TBL_PAG_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_PAG_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_PAG_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_PAG_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_PAG_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_PAG_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_PAG_NUM_DO1,rst.getString("ne_numDoc1"));
                        vecReg.add(INT_TBL_PAG_NUM_DO2,rst.getString("ne_numDoc2"));
                        vecReg.add(INT_TBL_PAG_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_PAG_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_PAG_VAL_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_PAG_COD_CTA,rst.getString("co_cta"));
                        vecReg.add(INT_TBL_PAG_NUM_CTA,rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_PAG_NOM_CTA,rst.getString("a5_tx_desLar"));
                        vecReg.add(INT_TBL_PAG_COD_TIP_DOC_CON,rst.getString("co_tipDocCon"));
                        vecReg.add(INT_TBL_PAG_DEC_TIP_DOC_CON,rst.getString("a6_tx_desCor"));
                        vecReg.add(INT_TBL_PAG_DEL_TIP_DOC_CON,rst.getString("a6_tx_desLar"));
                        vecReg.add(INT_TBL_PAG_COD_BAN,rst.getString("co_banChq"));
                        vecReg.add(INT_TBL_PAG_DEC_BAN,rst.getString("a7_tx_desCor"));
                        vecReg.add(INT_TBL_PAG_DEL_BAN,rst.getString("a7_tx_desLar"));
                        vecReg.add(INT_TBL_PAG_NUM_CTA_CHQ,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_PAG_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_PAG_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_PAG_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModPag.setData(vecDat);
                    tblPagRea.setModel(objTblModPag);
                    vecDat.clear();
//                    objTooBar.setMenSis("Listo");
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
     * Esta función permite cargar el movimiento del pago seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarPagReaMovReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (tblPagRea.getSelectedRow()!=-1)
            {
//                objTooBar.setMenSis("Obteniendo datos...");
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_loc, a3.co_locpag, a1.co_tipDoc, a4.tx_desCor, a4.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                    strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen";
                    strSQL+=", a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.co_proChq, a5.tx_desLar AS a5_tx_desLar";
                    strSQL+=" FROM tbm_cabMovInv AS a1";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" INNER JOIN tbm_detPag AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locPag AND a2.co_tipDoc=a3.co_tipDocPag AND a2.co_doc=a3.co_docPag AND a2.co_reg=a3.co_regPag)";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_var AS a5 ON (a2.co_proChq=a5.co_reg)";
                    strSQL+=" WHERE a3.co_emp=" + intCodEmp;
                    //Validar si se presentan "Clientes por Empresa" ó "Clientes por Local".
                    if (!objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
                    {
                        strSQL+=" AND a1.co_loc=" + intCodLoc;
                    }
                    strSQL+=" AND a3.co_tipDoc=" + objTblModPag.getValueAt(tblPagRea.getSelectedRow(), INT_TBL_PAG_COD_TIP_DOC);
                    strSQL+=" AND a3.co_doc=" + objTblModPag.getValueAt(tblPagRea.getSelectedRow(), INT_TBL_PAG_COD_DOC);
                    strSQL+=" AND a3.co_reg=" + objTblModPag.getValueAt(tblPagRea.getSelectedRow(), INT_TBL_PAG_COD_REG);
                    strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
//                    objTooBar.setMenSis("Cargando datos...");
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_PMR_LIN,"");
                        vecReg.add(INT_TBL_PMR_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_PMR_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_PMR_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_PMR_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_PMR_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_PMR_COD_REG,rst.getString("co_reg"));
                        vecReg.add(INT_TBL_PMR_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_PMR_FEC_DOC,rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_PMR_DIA_CRE,rst.getString("ne_diaCre"));
                        vecReg.add(INT_TBL_PMR_FEC_VEN,rst.getString("fe_ven"));
                        vecReg.add(INT_TBL_PMR_POR_RET,rst.getString("nd_porRet"));
                        vecReg.add(INT_TBL_PMR_VAL_DOC,rst.getString("mo_pag"));
                        vecReg.add(INT_TBL_PMR_ABO_DOC,rst.getString("nd_abo"));
                        vecReg.add(INT_TBL_PMR_VAL_PEN,rst.getString("nd_pen"));
                        vecReg.add(INT_TBL_PMR_NUM_CTA,rst.getString("tx_numCtaChq"));
                        vecReg.add(INT_TBL_PMR_NUM_CHQ,rst.getString("tx_numChq"));
                        vecReg.add(INT_TBL_PMR_FEC_REC_CHQ,rst.getString("fe_recChq"));
                        vecReg.add(INT_TBL_PMR_FEC_VEN_CHQ,rst.getString("fe_venChq"));
                        vecReg.add(INT_TBL_PMR_VAL_CHQ,rst.getString("nd_monChq"));
                        vecReg.add(INT_TBL_PMR_COD_PRO,rst.getString("co_proChq"));
                        vecReg.add(INT_TBL_PMR_DEL_PRO,rst.getString("a5_tx_desLar"));
                        vecDat.add(vecReg);
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModPmr.setData(vecDat);
                    tblPagReaMovReg.setModel(objTblModPmr);
                    vecDat.clear();
//                    objTooBar.setMenSis("Listo");
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
            //Limpiar objetos.
            objTblModCre.removeAllRows();
            objTblModCmr.removeAllRows();
            objTblModPag.removeAllRows();
            objTblModPmr.removeAllRows();
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
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_IDE_CLI:
                    strMsg="Identificación del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_SAL_CLI:
                    strMsg="Saldo del cliente/proveedor";
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
            int intCol=tblCreDeb.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CRE_LIN:
                    strMsg="";
                    break;
                case INT_TBL_CRE_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_CRE_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_CRE_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CRE_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_CRE_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_CRE_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_CRE_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_CRE_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_CRE_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_CRE_ABO_DOC:
                    strMsg="Abono";
                    break;
                case INT_TBL_CRE_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_CRE_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_CRE_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_CRE_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_CRE_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_CRE_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_CRE_DEL_PRO:
                    strMsg="Tipo de protesto";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblCreDeb.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaCmr extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblCreDebMovReg.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_CMR_LIN:
                    strMsg="";
                    break;
                case INT_TBL_CMR_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_CMR_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_CMR_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CMR_NUM_DO1:
                    strMsg="Número de documento 1";
                    break;
                case INT_TBL_CMR_NUM_DO2:
                    strMsg="Número de documento 2";
                    break;
                case INT_TBL_CMR_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_CMR_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_CMR_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_CMR_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_CMR_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_CMR_DEC_TIP_DOC_CON:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_CMR_DEL_TIP_DOC_CON:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_CMR_DEC_BAN:
                    strMsg="Descripción corta del banco";
                    break;
                case INT_TBL_CMR_DEL_BAN:
                    strMsg="Descripción larga del banco";
                    break;
                case INT_TBL_CMR_NUM_CTA_CHQ:
                    strMsg="Número de cuenta del cheque";
                    break;
                case INT_TBL_CMR_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_CMR_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_CMR_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblCreDebMovReg.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaPag extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblPagRea.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_PAG_LIN:
                    strMsg="";
                    break;
                case INT_TBL_PAG_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_PAG_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_PAG_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_PAG_NUM_DO1:
                    strMsg="Número de documento 1";
                    break;
                case INT_TBL_PAG_NUM_DO2:
                    strMsg="Número de documento 2";
                    break;
                case INT_TBL_PAG_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_PAG_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_PAG_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_PAG_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_PAG_NOM_CTA:
                    strMsg="Nombre de la cuenta";
                    break;
                case INT_TBL_PAG_DEC_TIP_DOC_CON:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_PAG_DEL_TIP_DOC_CON:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_PAG_DEC_BAN:
                    strMsg="Descripción corta del banco";
                    break;
                case INT_TBL_PAG_DEL_BAN:
                    strMsg="Descripción larga del banco";
                    break;
                case INT_TBL_PAG_NUM_CTA_CHQ:
                    strMsg="Número de cuenta del cheque";
                    break;
                case INT_TBL_PAG_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_PAG_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_PAG_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblPagRea.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaPmr extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblPagReaMovReg.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_PMR_LIN:
                    strMsg="";
                    break;
                case INT_TBL_PMR_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_PMR_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_PMR_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_PMR_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_PMR_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_PMR_DIA_CRE:
                    strMsg="Días de crédito";
                    break;
                case INT_TBL_PMR_FEC_VEN:
                    strMsg="Fecha de vencimiento";
                    break;
                case INT_TBL_PMR_POR_RET:
                    strMsg="Porcentaje de retención";
                    break;
                case INT_TBL_PMR_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_PMR_ABO_DOC:
                    strMsg="Abono";
                    break;
                case INT_TBL_PMR_VAL_PEN:
                    strMsg="Valor pendiente";
                    break;
                case INT_TBL_PMR_NUM_CTA:
                    strMsg="Número de cuenta";
                    break;
                case INT_TBL_PMR_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_PMR_FEC_REC_CHQ:
                    strMsg="Fecha de recepción del cheque";
                    break;
                case INT_TBL_PMR_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_PMR_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_PMR_DEL_PRO:
                    strMsg="Tipo de protesto";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblPagReaMovReg.getTableHeader().setToolTipText(strMsg);
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
                if (chkMosCreDeb.isSelected())
                    cargarCreDeb();
                else
                    objTblModCre.removeAllRows();
                if (chkMosPagRea.isSelected())
                    cargarPagRea();
                else
                    objTblModPag.removeAllRows();
            }
        }
    }
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisCre implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
                if (chkCreDebMosMovReg.isSelected())
                    cargarCreDebMovReg();
                else
                    objTblModCmr.removeAllRows();
            }
        }
    }
    
    /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSelLisPag implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {
                if (chkPagReaMosMovReg.isSelected())
                    cargarPagReaMovReg();
                else
                    objTblModPmr.removeAllRows();
            }
        }
    }
    
}
