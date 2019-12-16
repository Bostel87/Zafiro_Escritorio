/*
 * ZafCxC23.java
 *
 * Created on 13 de enero de 2006, 17:17 PM
 */
package CxC.ZafCxC23;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafUtil.ZafLocPrgUsr;
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
public class ZafCxC23 extends javax.swing.JInternalFrame 
{
    //Constantes.
    //JTable: Columnas de TblDat
    static final int INT_TBL_EMP_LIN=0;                         //Línea.
    static final int INT_TBL_EMP_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_EMP_NOM_EMP=3;                     //Nombre de la empresa.

    static final int INT_TBL_DAT_NUM_TOT_CES=9;                 //Número total de columnas estáticas.
    static final int INT_TBL_DAT_NUM_TOT_CDI=12;                //Número total de columnas dinámicas.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_CLI=1;                     //Código del cliente.
    static final int INT_TBL_DAT_IDE_CLI=2;                     //Identificación del cliente.
    static final int INT_TBL_DAT_NOM_CLI=3;                     //Nombre del cliente.
    static final int INT_TBL_DAT_BUT_SOL_CRE_RES=4;             //Botón de consulta "Solicitud de crédito resumida".
    static final int INT_TBL_DAT_BUT_OBS_CLI=5;                 //Boton general: Muestra el campo tbm_cli.tx_obsCxC.
    static final int INT_TBL_DAT_BUT_HIS_PAG=6;                 //Boton general: Muestra el "Historial de pago de clientes...".
    static final int INT_TBL_DAT_BUT_HIS_TRA=7;                 //Boton general: Muestra el "Historial de transacciones de clientes...".
    static final int INT_TBL_DAT_BUT_HIS_POS_CHQ=8;             //Boton general: Muestra el "Historial de postergaciones de cheques de clientes...".
    static final int INT_TBL_DAT_CDI_COD_EMP=9;                 //Columna dinámica: Código de la empresa.
    static final int INT_TBL_DAT_CDI_COD_CLI=10;                //Columna dinámica: Código del cliente.
    static final int INT_TBL_DAT_CDI_COD_FOR_PAG=11;            //Columna dinámica: Código de la forma de pago (Sistema).
    static final int INT_TBL_DAT_CDI_DEL_FOR_PAG=12;            //Columna dinámica: Descripción larga de la forma de pago.
    static final int INT_TBL_DAT_CDI_BUT_FOR_PAG=13;            //Columna dinámica: Botón de consulta.
    static final int INT_TBL_DAT_CDI_MON_CRE=14;                //Columna dinámica: Monto de crédito.
    static final int INT_TBL_DAT_CDI_MAX_DES=15;                //Columna dinámica: Máximo porcentaje de descuento.
    static final int INT_TBL_DAT_CDI_DES_ESP=16;                //Columna dinámica: Descuento especial.
    static final int INT_TBL_DAT_CDI_DIA_GRA_VEN=17;            //Columna dinámica: Días de gracia (Documentos vencidos).
    static final int INT_TBL_DAT_CDI_DIA_GRA_SOP=18;            //Columna dinámica: Días de gracia (Documentos que necesitan soporte. Ej: Cheques a fecha).
    static final int INT_TBL_DAT_CDI_CHK_CIE_RET=19;            //Columna dinámica: Casilla de verificación. Cierre por retenciones.
    static final int INT_TBL_DAT_CDI_BUT_MAS_INF=20;            //Columna dinámica: Botón de consulta. Más información.
   
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModEmp;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelRenBut objTblCelRenButCfg;                 //Render: Presentar JButton configurable en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiButGen objTblCelEdiButGen;              //Editor: JButton en celda.
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafTblCelEdiButDlg objTblCelEdiButDlgForPag;        //Editor: JButton en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelEdiChk objTblCelEdiChkEmp;                 //Editor: JCheckBox en celda.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafVenCon vcoCli;                                   //Ventana de consulta.
    private ZafVenCon vcoForPag;                                //Ventana de consulta.
    private ZafVenCon vcoLoc;                                   //Ventana de consulta "Local".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private String strCodCli, strIdeCli, strDesLarCli;          //Contenido del campo al obtener el foco.
    private String strCodForPag, strForPag;                     //Contenido del campo al obtener el foco.
    private String strCodLoc, strLoc;                           //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private ZafCxC23_01 objCxC23_01;
    private ZafCxC23_02 objCxC23_02;
    private ZafCxC23_04 objCxC23_04;
    private ZafCxC23_05 objCxC23_05;
    private ZafPerUsr objPerUsr;                                //Objeto que almacena el perfil del usuario.
    private ZafLocPrgUsr objLocPrgUsr;                          //Objeto que almacena los locales por usuario y programa.
   
    /** Crea una nueva instancia de la clase ZafCxC23. */
    public ZafCxC23(ZafParSis obj) 
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
        panEmp = new javax.swing.JPanel();
        spnEmp = new javax.swing.JScrollPane();
        tblEmp = new javax.swing.JTable();
        panFilSel = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtIdeCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblForPag = new javax.swing.JLabel();
        txtCodForPag = new javax.swing.JTextField();
        txtForPag = new javax.swing.JTextField();
        butForPag = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblNomCliDes = new javax.swing.JLabel();
        txtNomCliDes = new javax.swing.JTextField();
        lblNomCliHas = new javax.swing.JLabel();
        txtNomCliHas = new javax.swing.JTextField();
        panMonCre = new javax.swing.JPanel();
        lblMonCreDes = new javax.swing.JLabel();
        txtMonCreDes = new javax.swing.JTextField();
        lblMonCreHas = new javax.swing.JLabel();
        txtMonCreHas = new javax.swing.JTextField();
        panMaxDes = new javax.swing.JPanel();
        lblMaxDesDes = new javax.swing.JLabel();
        txtMaxDesDes = new javax.swing.JTextField();
        lblMaxDesHas = new javax.swing.JLabel();
        txtMaxDesHas = new javax.swing.JTextField();
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
        butGua = new javax.swing.JButton();
        butSolCrePorAna = new javax.swing.JButton();
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

        panEmp.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de empresas"));
        panEmp.setPreferredSize(new java.awt.Dimension(10, 92));
        panEmp.setLayout(new java.awt.BorderLayout());

        tblEmp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnEmp.setViewportView(tblEmp);

        panEmp.add(spnEmp, java.awt.BorderLayout.CENTER);

        panFil.add(panEmp, java.awt.BorderLayout.NORTH);

        panFilSel.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los clientes");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFilSel.add(optTod);
        optTod.setBounds(4, 4, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los clientes que cumplan el criterio seleccionado");
        panFilSel.add(optFil);
        optFil.setBounds(4, 24, 400, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        panFilSel.add(lblCli);
        lblCli.setBounds(24, 44, 100, 20);

        txtCodCli.setToolTipText("Código del cliente/proveedor");
        txtCodCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodCliFocusLost(evt);
            }
        });
        panFilSel.add(txtCodCli);
        txtCodCli.setBounds(144, 44, 56, 20);

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
        panFilSel.add(txtIdeCli);
        txtIdeCli.setBounds(200, 44, 100, 20);

        txtNomCli.setToolTipText("Nombre del cliente/proveedor");
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        panFilSel.add(txtNomCli);
        txtNomCli.setBounds(300, 44, 360, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFilSel.add(butCli);
        butCli.setBounds(660, 44, 20, 20);

        lblForPag.setText("Forma de pago:");
        lblForPag.setToolTipText("Forma de pago");
        panFilSel.add(lblForPag);
        lblForPag.setBounds(24, 64, 100, 20);

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
        panFilSel.add(txtCodForPag);
        txtCodForPag.setBounds(144, 64, 56, 20);

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
        panFilSel.add(txtForPag);
        txtForPag.setBounds(200, 64, 460, 20);

        butForPag.setText("...");
        butForPag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butForPagActionPerformed(evt);
            }
        });
        panFilSel.add(butForPag);
        butForPag.setBounds(660, 64, 20, 20);

        lblLoc.setText("Local:");
        panFilSel.add(lblLoc);
        lblLoc.setBounds(24, 84, 100, 20);

        txtCodLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLocFocusLost(evt);
            }
        });
        txtCodLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLocActionPerformed(evt);
            }
        });
        panFilSel.add(txtCodLoc);
        txtCodLoc.setBounds(144, 84, 56, 20);

        txtLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLocFocusLost(evt);
            }
        });
        txtLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLocActionPerformed(evt);
            }
        });
        panFilSel.add(txtLoc);
        txtLoc.setBounds(200, 84, 460, 20);

        butLoc.setText("jButton1");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFilSel.add(butLoc);
        butLoc.setBounds(660, 84, 20, 20);
        butLoc.getAccessibleContext().setAccessibleName("...");
        butLoc.getAccessibleContext().setAccessibleDescription("");

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
        txtNomCliDes.setBounds(60, 20, 250, 20);

        lblNomCliHas.setText("Hasta:");
        panNomCli.add(lblNomCliHas);
        lblNomCliHas.setBounds(330, 20, 44, 20);

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

        panFilSel.add(panNomCli);
        panNomCli.setBounds(20, 110, 660, 50);

        panMonCre.setBorder(javax.swing.BorderFactory.createTitledBorder("Monto de crédito"));
        panMonCre.setLayout(null);

        lblMonCreDes.setText("Desde:");
        panMonCre.add(lblMonCreDes);
        lblMonCreDes.setBounds(12, 20, 44, 20);

        txtMonCreDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonCreDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonCreDesFocusLost(evt);
            }
        });
        panMonCre.add(txtMonCreDes);
        txtMonCreDes.setBounds(60, 20, 100, 20);

        lblMonCreHas.setText("Hasta:");
        panMonCre.add(lblMonCreHas);
        lblMonCreHas.setBounds(170, 20, 44, 20);

        txtMonCreHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMonCreHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMonCreHasFocusLost(evt);
            }
        });
        panMonCre.add(txtMonCreHas);
        txtMonCreHas.setBounds(216, 20, 100, 20);

        panFilSel.add(panMonCre);
        panMonCre.setBounds(20, 160, 328, 52);

        panMaxDes.setBorder(javax.swing.BorderFactory.createTitledBorder("Máximo porcentaje de descuento"));
        panMaxDes.setLayout(null);

        lblMaxDesDes.setText("Desde:");
        panMaxDes.add(lblMaxDesDes);
        lblMaxDesDes.setBounds(12, 20, 44, 20);

        txtMaxDesDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaxDesDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaxDesDesFocusLost(evt);
            }
        });
        panMaxDes.add(txtMaxDesDes);
        txtMaxDesDes.setBounds(60, 20, 100, 20);

        lblMaxDesHas.setText("Hasta:");
        panMaxDes.add(lblMaxDesHas);
        lblMaxDesHas.setBounds(170, 20, 44, 20);

        txtMaxDesHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtMaxDesHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaxDesHasFocusLost(evt);
            }
        });
        panMaxDes.add(txtMaxDesHas);
        txtMaxDesHas.setBounds(216, 20, 100, 20);

        panFilSel.add(panMaxDes);
        panMaxDes.setBounds(350, 160, 328, 52);

        panFil.add(panFilSel, java.awt.BorderLayout.CENTER);

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
        butGua.setToolTipText("Guarda los cambios realizados.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butSolCrePorAna.setText("Solicitudes");
        butSolCrePorAna.setToolTipText("Solicitudes de crédito por analizar");
        butSolCrePorAna.setPreferredSize(new java.awt.Dimension(92, 25));
        butSolCrePorAna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolCrePorAnaActionPerformed(evt);
            }
        });
        panBot.add(butSolCrePorAna);

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

    private void butSolCrePorAnaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolCrePorAnaActionPerformed
        abrirFrm(1);
    }//GEN-LAST:event_butSolCrePorAnaActionPerformed

    private void txtIdeCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtIdeCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtIdeCli.getText().equalsIgnoreCase(strIdeCli))
        {
            if (txtIdeCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butForPagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butForPagActionPerformed
        mostrarVenConForPag(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodForPag.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butForPagActionPerformed

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

    private void txtMaxDesHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxDesHasFocusLost
        if (txtMaxDesHas.getText().length()>0)
            //Validar que sea un número.
            if (objUti.isNumero(txtMaxDesHas.getText()))
            {
                optFil.setSelected(true);
            }
            else
                txtMaxDesHas.setText("");
    }//GEN-LAST:event_txtMaxDesHasFocusLost

    private void txtMonCreHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonCreHasFocusLost
        if (txtMonCreHas.getText().length()>0)
            //Validar que sea un número.
            if (objUti.isNumero(txtMonCreHas.getText()))
            {
                optFil.setSelected(true);
            }
            else
                txtMonCreHas.setText("");
    }//GEN-LAST:event_txtMonCreHasFocusLost

    private void txtMaxDesHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxDesHasFocusGained
        txtMaxDesHas.selectAll();
    }//GEN-LAST:event_txtMaxDesHasFocusGained

    private void txtMonCreHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonCreHasFocusGained
        txtMonCreHas.selectAll();
    }//GEN-LAST:event_txtMonCreHasFocusGained

    private void txtMaxDesDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxDesDesFocusLost
        if (txtMaxDesDes.getText().length()>0)
        {
            //Validar que sea un número.
            if (objUti.isNumero(txtMaxDesDes.getText()))
            {
                optFil.setSelected(true);
                if (txtMaxDesHas.getText().length()==0)
                    txtMaxDesHas.setText(txtMaxDesDes.getText());
            }
            else
                txtMaxDesDes.setText("");
        }
    }//GEN-LAST:event_txtMaxDesDesFocusLost

    private void txtMonCreDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonCreDesFocusLost
        if (txtMonCreDes.getText().length()>0)
        {
            //Validar que sea un número.
            if (objUti.isNumero(txtMonCreDes.getText()))
            {
                optFil.setSelected(true);
                if (txtMonCreHas.getText().length()==0)
                    txtMonCreHas.setText(txtMonCreDes.getText());
            }
            else
                txtMonCreDes.setText("");
        }
    }//GEN-LAST:event_txtMonCreDesFocusLost

    private void txtMaxDesDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaxDesDesFocusGained
        txtMaxDesDes.selectAll();
    }//GEN-LAST:event_txtMaxDesDesFocusGained

    private void txtMonCreDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMonCreDesFocusGained
        txtMonCreDes.selectAll();
    }//GEN-LAST:event_txtMonCreDesFocusGained

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?")==0)
            {
                if (actualizarDet())
                    mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                else
                    mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
            }
        }
        else
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
            }
            else
            {
                mostrarVenConCli(3);
            }
        }
        else
            txtNomCli.setText(strDesLarCli);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodCli.getText().length()>0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtIdeCli.setText("");
                txtNomCli.setText("");
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
            txtIdeCli.setText("");
            txtNomCli.setText("");
            txtCodForPag.setText("");
            txtForPag.setText("");
            txtCodLoc.setText("");
            txtLoc.setText("");
            txtNomCliDes.setText("");
            txtNomCliHas.setText("");
            txtMonCreDes.setText("");
            txtMonCreHas.setText("");
            txtMaxDesDes.setText("");
            txtMaxDesHas.setText("");
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
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        mostrarVenConLoc(0);
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_butLocActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
        {
            if (txtCodLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
        
        if (txtCodLoc.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLocActionPerformed
        txtLoc.transferFocus();
    }//GEN-LAST:event_txtLocActionPerformed

    private void txtLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLocFocusGained
        strLoc = txtLoc.getText();
        txtLoc.selectAll();
    }//GEN-LAST:event_txtLocFocusGained

    private void txtLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtLoc.getText().equalsIgnoreCase(strLoc))
        {
            if (txtLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtLoc.setText("");
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtLoc.setText(strLoc);
        
        if (txtLoc.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtLocFocusLost

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
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butSolCrePorAna;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblForPag;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMaxDesDes;
    private javax.swing.JLabel lblMaxDesHas;
    private javax.swing.JLabel lblMonCreDes;
    private javax.swing.JLabel lblMonCreHas;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblNomCliDes;
    private javax.swing.JLabel lblNomCliHas;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panEmp;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilSel;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panMaxDes;
    private javax.swing.JPanel panMonCre;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnEmp;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblEmp;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodForPag;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtForPag;
    private javax.swing.JTextField txtIdeCli;
    private javax.swing.JTextField txtLoc;
    private javax.swing.JTextField txtMaxDesDes;
    private javax.swing.JTextField txtMaxDesHas;
    private javax.swing.JTextField txtMonCreDes;
    private javax.swing.JTextField txtMonCreHas;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomCliDes;
    private javax.swing.JTextField txtNomCliHas;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            //Obbtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            //Obtener los locales por Usuario y Programa.
            objLocPrgUsr=new ZafLocPrgUsr(objParSis);
            //Título de la Ventana.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.19");
            lblTit.setText(strAux);
            objCxC23_02=new ZafCxC23_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objCxC23_04=new ZafCxC23_04(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, objPerUsr.isOpcionEnabled(2447)); //2447: Ficha "Reporte": Tabla->Modificar "Observación CxC".
            objCxC23_05=new ZafCxC23_05(objParSis);
            //Configurar objetos.
            switch (objParSis.getCodigoMenu())
            {
                case 775: //CxC: Actualización de datos de crédito...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(776))
                        butCon.setVisible(false);
                    if (!objPerUsr.isOpcionEnabled(777))
                        butGua.setVisible(false);
                    if (!objPerUsr.isOpcionEnabled(2396))
                        butSolCrePorAna.setVisible(false);
                    if (!objPerUsr.isOpcionEnabled(778))
                        butCer.setVisible(false);
                    break;
                case 2327: //CxP: Actualización de datos de crédito...
                    //Habilitar/Inhabilitar las opciones según el perfil del usuario.
                    if (!objPerUsr.isOpcionEnabled(2328))
                        butCon.setVisible(false);
                    if (!objPerUsr.isOpcionEnabled(2330))
                        butCer.setVisible(false);
                    optTod.setText("Todos los proveedores");
                    optFil.setText("Sólo los proveedores que cumplan el criterio seleccionado");
                    lblCli.setText("Proveedor:");
                    panNomCli.setBorder(new javax.swing.border.TitledBorder("Nombre de proveedor"));
                    panMonCre.setVisible(false);
                    panMaxDes.setVisible(false);
                    butGua.setVisible(false);
                    butSolCrePorAna.setVisible(false);
                    break;
            }
            //Configurar las ZafVenCon.
            configurarVenConCli();
            configurarVenConForPag();
            configurarVenConLoc();
            //Configurar los JTables.
            configurarTblDat();
            configurarTblEmp();
            cargarEmp();
            agregarColTblDat();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                txtCodCli.setEditable(false);
                txtCodCli.setBackground(new java.awt.Color(255,255,255));
                lblForPag.setVisible(false);
                txtCodForPag.setVisible(false);
                txtForPag.setVisible(false);
                butForPag.setVisible(false);
                lblLoc.setVisible(false);
                txtCodLoc.setVisible(false);
                txtLoc.setVisible(false);
                butForPag.setVisible(false);
                panNomCli.setBounds(24, 64, 660, 52);
                panMonCre.setBounds(24, 116, 328, 52);
                panMaxDes.setBounds(356, 116, 328, 52);
            }
            else
            {
                panEmp.setVisible(false);
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
     * Esta función configura el JTable "tblEmp".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblEmp()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(4);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_EMP_LIN,"");
            vecCab.add(INT_TBL_EMP_CHK,"");
            vecCab.add(INT_TBL_EMP_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_EMP_NOM_EMP,"Empresa");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModEmp=new ZafTblMod();
            objTblModEmp.setHeader(vecCab);
            tblEmp.setModel(objTblModEmp);
            //Configurar JTable: Establecer tipo de selección.
            tblEmp.setRowSelectionAllowed(true);
            tblEmp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblEmp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblEmp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblEmp.getColumnModel();
            tcmAux.getColumn(INT_TBL_EMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_EMP_NOM_EMP).setPreferredWidth(545);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_EMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblEmp.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblMod.addSystemHiddenColumn(INT_TBL_EMP_COD_TIP_DOC, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblEmp.getTableHeader().addMouseMotionListener(new ZafMouMotAdaEmp());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblEmp.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblEmpMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_EMP_CHK);
            objTblModEmp.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblEmp);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_EMP_COD_EMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkEmp=new ZafTblCelEdiChk(tblEmp);
            tcmAux.getColumn(INT_TBL_EMP_CHK).setCellEditor(objTblCelEdiChkEmp);
            objTblCelEdiChkEmp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblEmp.getSelectedRow();
                    if (objTblCelEdiChkEmp.isChecked())
                    {
                        //Mostrar columnas.
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    }
                    else
                    {
                        //Ocultar columnas.
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    }
                }
            });
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblEmp.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModEmp.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
            vecCab=new Vector(INT_TBL_DAT_NUM_TOT_CES);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            switch (objParSis.getCodigoMenu())
            {
                case 775: //CxC: Actualización de datos de crédito...
                    vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
                    vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
                    vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente");
                    break;
                case 2327: //CxP: Actualización de datos de crédito...
                    vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Prv.");
                    vecCab.add(INT_TBL_DAT_IDE_CLI,"Identificación");
                    vecCab.add(INT_TBL_DAT_NOM_CLI,"Proveedor");
                    break;
            }
            vecCab.add(INT_TBL_DAT_BUT_SOL_CRE_RES,"");
            vecCab.add(INT_TBL_DAT_BUT_OBS_CLI,"");
            vecCab.add(INT_TBL_DAT_BUT_HIS_PAG,"");
            vecCab.add(INT_TBL_DAT_BUT_HIS_TRA,"");
            vecCab.add(INT_TBL_DAT_BUT_HIS_POS_CHQ,"");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
//            objTblMod.setColumnDataType(INT_TBL_DAT_CDI_MON_CRE, objTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IDE_CLI).setPreferredWidth(102);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(135);
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL_CRE_RES).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_CLI).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_PAG).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_TRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_POS_CHQ).setPreferredWidth(20);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
            }
            switch (objParSis.getCodigoMenu())
            {
                case 775: //CxC: Actualización de datos de crédito...
                    break;
                case 2327: //CxP: Actualización de datos de crédito...
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_SOL_CRE_RES, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_OBS_CLI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_HIS_PAG, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_HIS_TRA, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_HIS_POS_CHQ, tblDat);
                    break;
            }
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            //2445: Ficha "Reporte": Tabla->Consultar "Solicitud de crédito (Resumida)".
            if (objPerUsr.isOpcionEnabled(2445))
                vecAux.add("" + INT_TBL_DAT_BUT_SOL_CRE_RES);
            //2446: Ficha "Reporte": Tabla->Consultar "Observación CxC".
            //2447: Ficha "Reporte": Tabla->Modificar "Observación CxC".
            if (objPerUsr.isOpcionEnabled(2446) || objPerUsr.isOpcionEnabled(2447))
                vecAux.add("" + INT_TBL_DAT_BUT_OBS_CLI);
            //2448: Ficha "Reporte": Tabla->Consultar "Historial de pago de clientes".
            if (objPerUsr.isOpcionEnabled(2448))
                vecAux.add("" + INT_TBL_DAT_BUT_HIS_PAG);
            //2449: Ficha "Reporte": Tabla->Consultar "Historial de transacciones de clientes".
            if (objPerUsr.isOpcionEnabled(2449))
                vecAux.add("" + INT_TBL_DAT_BUT_HIS_TRA);
            //9999: Ficha "Reporte": Tabla->Consultar "Historial de postergaciones de cheques de clientes".
            //if (objPerUsr.isOpcionEnabled(9999))
                vecAux.add("" + INT_TBL_DAT_BUT_HIS_POS_CHQ);

            objTblMod.setColumnasEditables(vecAux);

            vecAux=null;
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
            
            objTblCelRenButCfg=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL_CRE_RES).setCellRenderer(objTblCelRenButCfg);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_CLI).setCellRenderer(objTblCelRenButCfg);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_PAG).setCellRenderer(objTblCelRenButCfg);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_TRA).setCellRenderer(objTblCelRenButCfg);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_POS_CHQ).setCellRenderer(objTblCelRenButCfg);
            objTblCelRenButCfg.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    switch (objTblCelRenButCfg.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_SOL_CRE_RES:
                            if (objTblMod.getValueAt(objTblCelRenButCfg.getRowRender(), INT_TBL_DAT_BUT_SOL_CRE_RES)==null)
                            {
                                objTblCelRenButCfg.setText("");
                            }
                            else
                            {
                                objTblCelRenButCfg.setText("...");
                            }
                            break;
                        case INT_TBL_DAT_BUT_OBS_CLI:
                            if (objTblMod.getValueAt(objTblCelRenButCfg.getRowRender(), INT_TBL_DAT_BUT_OBS_CLI)==null)
                            {
                                objTblCelRenButCfg.setText("");
                            }
                            else
                            {
                                objTblCelRenButCfg.setText("...");
                            }
                            break;
                        case INT_TBL_DAT_BUT_HIS_PAG:
                        case INT_TBL_DAT_BUT_HIS_TRA:
                        case INT_TBL_DAT_BUT_HIS_POS_CHQ:
                            objTblCelRenButCfg.setText("...");
                            break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT_SOL_CRE_RES).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_PAG).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_TRA).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUT_HIS_POS_CHQ).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    if (intFilSel!=-1)
                    {
                        switch (intColSel)
                        {
                            case INT_TBL_DAT_BUT_SOL_CRE_RES:
                                //Validar que exista una solicitud de crédito.
                                if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_BUT_SOL_CRE_RES)==null)
                                {
                                    objTblCelEdiButGen.setCancelarEdicion(true);
                                }
                                break;
                        }
                    }
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    System.out.println("intColSel: " + intColSel);
                    if (intFilSel!=-1)
                    {
                        if (intColSel>INT_TBL_DAT_NUM_TOT_CES)
                            intColSel=(intColSel-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CDI+INT_TBL_DAT_NUM_TOT_CES;
                        
                        switch (intColSel)
                        {
                            case INT_TBL_DAT_BUT_SOL_CRE_RES:
                                abrirFrm(2);
                                break;
                            case INT_TBL_DAT_CDI_BUT_MAS_INF:
                                abrirFrm(0);
                                break;
                            case INT_TBL_DAT_BUT_HIS_PAG:
                                abrirFrm(4);
                                break;
                            case INT_TBL_DAT_BUT_HIS_TRA:
                                abrirFrm(5);
                                break;
                            case INT_TBL_DAT_BUT_HIS_POS_CHQ:
                                abrirFrm(6);
                                break;
                        }
                    }
                }
            });

            objTblCelEdiButDlg=new ZafTblCelEdiButDlg(objCxC23_04);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_CLI).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if (intFilSel!=-1)
                    {
                        //Validar que exista una observación para el cliente.
                        if (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_BUT_OBS_CLI)==null)
                        {
                            objTblCelEdiButDlg.setCancelarEdicion(true);
                        }
                        else
                        {
                            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            {
                                objCxC23_04.setParDlg(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_IDE_CLI).toString());
                                objCxC23_04.cargarReg();
                            }
                            else
                            {
                                objCxC23_04.setParDlg(Integer.parseInt(objTblMod.getValueAt(intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP)).toString()), Integer.parseInt(objTblMod.getValueAt(intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_CLI)).toString()));
                                objCxC23_04.cargarReg();
                            }
                        }
                    }
                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    if (objCxC23_04.getOpcSelDlg()==1)
//                    {
//                        objTblMod.setValueAt("" + objCxC23_01.getCodForPagPre(), intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_FOR_PAG));
//                        objTblMod.setValueAt(objCxC23_01.getNomForPagPre(), intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_DEL_FOR_PAG));
//                    }
//                }
            });
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer que el JTable sea editable.
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblBod".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i, intNumFil, intNumColTblDat;
        javax.swing.table.TableColumn tbc;
        String strCodEmp="";
        objCxC23_01=new ZafCxC23_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, (objPerUsr.isOpcionEnabled(2451) || objPerUsr.isOpcionEnabled(2453))); //2451 y 2453: Ficha "Reporte": Tabla->Modificar "Forma de pago".
        objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
        objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel, intPosRelColCodEmp;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFilSel=tblDat.getSelectedRow();
                if (intFilSel!=-1)
                {
                    intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                    //Validar que exista el cliente para la empresa especificada.
                    if (objTblMod.getValueAt(intFilSel, intPosRelColCodEmp)==null)
                    {
                        objTblCelEdiTxt.setCancelarEdicion(true);
                    }
                }
            }
        });
        objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
        objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            int intFilSel, intPosRelColCodEmp;
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                intFilSel=tblDat.getSelectedRow();
                if (intFilSel!=-1)
                {
                    intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                    //Validar que exista el cliente para la empresa especificada.
                    if (objTblMod.getValueAt(intFilSel, intPosRelColCodEmp)==null)
                    {
                        objTblCelEdiChk.setCancelarEdicion(true);
                    }
                }
            }
        });
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpEmp=null;
        java.awt.Color colFonCol;
        boolean blnRes=true;
        try
        {
            intNumFil=objTblModEmp.getRowCountTrue();
            intNumColTblDat=objTblMod.getColumnCount();
            vecAux=new Vector();
            for (i=0; i<intNumFil; i++)
            {
                //Establecer el color de fondo de las columnas.
                if ((i%2)==0)
                    colFonCol=new java.awt.Color(228,228,203);
                else
                    colFonCol=new java.awt.Color(255,221,187);

                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Cód.Emp.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                //Configurar JTable: Ocultar columnas del sistema.
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_COD_CLI+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Cód.Cli.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                //Configurar JTable: Ocultar columnas del sistema.
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_COD_CLI+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_COD_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Cód.For.Pag.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                //Configurar JTable: Ocultar columnas del sistema.
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_COD_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Forma de pago");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(90);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(20);
                //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
                tbc.setResizable(false);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenBut=new ZafTblCelRenBut();
                tbc.setCellRenderer(objTblCelRenBut);
                objTblCelRenBut=null;
                //Configurar JTable: Editor de celdas.
                objTblCelEdiButDlgForPag=new ZafTblCelEdiButDlg(objCxC23_01);
                tbc.setCellEditor(objTblCelEdiButDlgForPag);
                objTblCelEdiButDlgForPag.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    int intFilSel, intColSel, intPosRelColCodEmp;
                    public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        intFilSel=tblDat.getSelectedRow();
                        intColSel=tblDat.getSelectedColumn();
                        if (intFilSel!=-1)
                        {
                            intPosRelColCodEmp=getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP);
                            //Validar que exista el cliente para la empresa especificada.
                            if (objTblMod.getValueAt(intFilSel, intPosRelColCodEmp)==null)
                            {
                                mostrarMsgInf("El cliente que seleccionó no existe en ésta empresa.");
                            }
                            else
                            {
                                objCxC23_01.setParDlg(Integer.parseInt(objTblMod.getValueAt(intFilSel, intPosRelColCodEmp).toString()), Integer.parseInt(objTblMod.getValueAt(intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_CLI)).toString()));
                                objCxC23_01.cargarReg();
                            }
                        }
                    }
                    public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        if (objCxC23_01.getOpcSelDlg()==1)
                        {
                            objTblMod.setValueAt("" + objCxC23_01.getCodForPagPre(), intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_FOR_PAG));
                            objTblMod.setValueAt(objCxC23_01.getNomForPagPre(), intFilSel, getPosRelColEspColSel(INT_TBL_DAT_CDI_DEL_FOR_PAG));
                        }
                    }
                });
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Mon.Cré.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(55);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Máx.Des.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(55);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Des.Esp.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(55);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Dia.Gra.Doc.Ven.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico("#",false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Dia.Gra.Doc.Sop.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenLbl=new ZafTblCelRenLbl();
                objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
                objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
                objTblCelRenLbl.setFormatoNumerico("#",false,true);
                objTblCelRenLbl.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenLbl);
                objTblCelRenLbl=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiTxt);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("Cie.Ret.");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(30);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenChk=new ZafTblCelRenChk();
                objTblCelRenChk.setBackground(colFonCol);
                tbc.setCellRenderer(objTblCelRenChk);
                objTblCelRenChk=null;
                //Configurar JTable: Editor de celdas.
                tbc.setCellEditor(objTblCelEdiChk);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                tbc=new javax.swing.table.TableColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI);
                tbc.setHeaderValue("");
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(20);
                //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
                tbc.setResizable(false);
                //Configurar JTable: Renderizar celdas.
                objTblCelRenBut=new ZafTblCelRenBut();
                tbc.setCellRenderer(objTblCelRenBut);
                objTblCelRenBut=null;
                //Configurar JTable: Editor de celdas.
                //El objeto "objTblCelEdiButGen" fue creado y configurado en "configurarTblDat()".
                tbc.setCellEditor(objTblCelEdiButGen);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat, tbc);
                
                //Agrupar las columnas.
                if (!strCodEmp.equals(objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString()))
                {
                    objTblHeaColGrpEmp=new ZafTblHeaColGrp(objTblModEmp.getValueAt(i, INT_TBL_EMP_NOM_EMP).toString());
                    objTblHeaColGrpEmp.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
                    strCodEmp=objTblModEmp.getValueAt(i, INT_TBL_EMP_COD_EMP).toString();
                }
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_COD_EMP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_COD_CLI+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_COD_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI));
                objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI));
                //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
                objTblMod.setColumnDataType(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, ZafTblMod.INT_COL_DBL, new Integer(0), null);
                objTblMod.setColumnDataType(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
                objTblMod.setColumnDataType(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, ZafTblMod.INT_COL_DBL, new Integer(0), new Integer(100));
                objTblMod.setColumnDataType(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, ZafTblMod.INT_COL_INT, new Integer(0), null);
                objTblMod.setColumnDataType(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, ZafTblMod.INT_COL_INT, new Integer(0), null);
                //Configurar JTable: Establecer columnas editables.
                switch (objParSis.getCodigoMenu())
                {
                    case 775: //CxC: Actualización de datos de crédito...
                        //2450: Ficha "Reporte": Tabla->Consultar "Forma de pago".
                        //2451: Ficha "Reporte": Tabla->Modificar "Forma de pago".
                        if (objPerUsr.isOpcionEnabled(2450) || objPerUsr.isOpcionEnabled(2451))
                        {
                            vecAux.add("" + (INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI));
                        }
                        //3069: Ficha "Reporte": Tabla->Modificar "Descuento especial".
                        if (objPerUsr.isOpcionEnabled(3069))
                        {
                            vecAux.add("" + (INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI));
                        }
                        //777: Guardar.
                        if (objPerUsr.isOpcionEnabled(777))
                        {
                            vecAux.add("" + (INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI));
                            vecAux.add("" + (INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI));
                            vecAux.add("" + (INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI));
                            vecAux.add("" + (INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI));
                            vecAux.add("" + (INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI));
                            vecAux.add("" + (INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI));
                        }
                        break;
                    case 2327: //CxP: Actualización de datos de crédito...
                        //2452: Ficha "Reporte": Tabla->Consultar "Forma de pago".
                        //2453: Ficha "Reporte": Tabla->Modificar "Forma de pago".
                        if (objPerUsr.isOpcionEnabled(2452) || objPerUsr.isOpcionEnabled(2453))
                        {
                            vecAux.add("" + (INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI));
                        }
                        break;
                }
                if (objTblModEmp.isChecked(i, INT_TBL_EMP_CHK))
                {
                    //Ocultar las columnas si el programa es llamado desde CxP.
                    switch (objParSis.getCodigoMenu())
                    {
                        case 775: //CxC: Actualización de datos de crédito...
                            //3068: Ficha "Reporte": Tabla->Consultar "Descuento especial".
                            if (!objPerUsr.isOpcionEnabled(3068))
                            {
                                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            }
                            break;
                        case 2327: //CxP: Actualización de datos de crédito...
                            //Ocultar columnas.
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                            break;
                    }
                }
                else
                {
                    //Ocultar las columnas si la empresa no está seleccionada.
                    //Ocultar columnas.
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                }
            }
            objTblMod.addColumnasEditables(vecAux);
            objTblCelRenLbl=null;
            vecAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar las empresas de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todas las empresae.
     * <LI>Si se ingresa a cualquier otra empresa se muestra sólo la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarEmp()
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
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.st_reg, a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                else
                    strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_emp";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_EMP_LIN,"");
                    if (rst.getString("st_reg").equals("A"))
                        vecReg.add(INT_TBL_EMP_CHK,Boolean.TRUE);
                    else
                        vecReg.add(INT_TBL_EMP_CHK,null);
                    vecReg.add(INT_TBL_EMP_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_EMP_NOM_EMP,rst.getString("tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModEmp.setData(vecDat);
                tblEmp.setModel(objTblModEmp);
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
        int intCodEmp, intNumFilTblEmp, i, j;
        int intTipLoc=1; 
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            intNumFilTblEmp=objTblModEmp.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT b1.tx_ide";
                    for (j=0; j<intNumFilTblEmp; j++)
                    {
                        if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                        {
                            strSQL+=", b" + (j+2) + ".co_emp AS b" + (j+2) + "_co_emp";
                            strSQL+=", b" + (j+2) + ".co_cli AS b" + (j+2) + "_co_cli";
                            strSQL+=", b" + (j+2) + ".tx_nom AS b" + (j+2) + "_tx_nom";
                            strSQL+=", b" + (j+2) + ".co_forPag AS b" + (j+2) + "_co_forPag";
                            strSQL+=", b" + (j+2) + ".tx_des AS b" + (j+2) + "_tx_des";
                            switch (objParSis.getCodigoMenu())
                            {
                                case 775: //CxC: Actualización de datos de crédito...
                                    strSQL+=", b" + (j+2) + ".nd_monCre AS b" + (j+2) + "_nd_monCre";
                                    strSQL+=", b" + (j+2) + ".nd_maxDes AS b" + (j+2) + "_nd_maxDes";
                                    //3068: Ficha "Reporte": Tabla->Consultar "Descuento especial".
                                    if (objPerUsr.isOpcionEnabled(3068))
                                    {
                                        strSQL+=", b" + (j+2) + ".nd_desEsp AS b" + (j+2) + "_nd_desEsp";
                                    }
                                    else
                                    {
                                        strSQL+=", Null AS b" + (j+2) + "_nd_desEsp";
                                    }
                                    strSQL+=", b" + (j+2) + ".ne_diaGra AS b" + (j+2) + "_ne_diaGra";
                                    strSQL+=", b" + (j+2) + ".ne_diaGraChqFec AS b" + (j+2) + "_ne_diaGraChqFec";
                                    strSQL+=", b" + (j+2) + ".st_cieRetPen AS b" + (j+2) + "_st_cieRetPen";
                                    strSQL+=", b" + (j+2) + ".ne_numTotSolCre AS b" + (j+2) + "_ne_numTotSolCre";
                                    strSQL+=", b" + (j+2) + ".tx_obsCxC AS b" + (j+2) + "_tx_obsCxC";
                                    break;
                                case 2327: //CxP: Actualización de datos de crédito...
                                    strSQL+=", Null AS b" + (j+2) + "_nd_monCre";
                                    strSQL+=", Null AS b" + (j+2) + "_nd_maxDes";
                                    strSQL+=", Null AS b" + (j+2) + "_nd_desEsp";
                                    strSQL+=", Null AS b" + (j+2) + "_ne_diaGra";
                                    strSQL+=", Null AS b" + (j+2) + "_ne_diaGraChqFec";
                                    strSQL+=", Null AS b" + (j+2) + "_st_cieRetPen";
                                    strSQL+=", Null AS b" + (j+2) + "_ne_numTotSolCre";
                                    strSQL+=", Null AS b" + (j+2) + "_tx_obsCxC";
                                    break;
                            }
                        }
                        else
                        {
                            strSQL+=", Null AS b" + (j+2) + "_co_emp";
                            strSQL+=", Null AS b" + (j+2) + "_co_cli";
                            strSQL+=", Null AS b" + (j+2) + "_tx_nom";
                            strSQL+=", Null AS b" + (j+2) + "_co_forPag";
                            strSQL+=", Null AS b" + (j+2) + "_tx_des";
                            strSQL+=", Null AS b" + (j+2) + "_nd_monCre";
                            strSQL+=", Null AS b" + (j+2) + "_nd_maxDes";
                            strSQL+=", Null AS b" + (j+2) + "_nd_desEsp";
                            strSQL+=", Null AS b" + (j+2) + "_ne_diaGra";
                            strSQL+=", Null AS b" + (j+2) + "_ne_diaGraChqFec";
                            strSQL+=", Null AS b" + (j+2) + "_st_cieRetPen";
                            strSQL+=", Null AS b" + (j+2) + "_ne_numTotSolCre";
                            strSQL+=", Null AS b" + (j+2) + "_tx_obsCxC";
                        }
                    }
                    strSQL+=" FROM ( SELECT a2.co_emp, MAX(a2.co_cli) AS co_cli, a2.tx_ide";
                    strSQL+=" FROM ( SELECT MIN(co_emp) AS co_emp, tx_ide FROM tbm_cli GROUP BY tx_ide ) AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.tx_ide=a2.tx_ide)";
                    strSQL+=" GROUP BY a2.co_emp, a2.tx_ide";
                    strSQL+=" ) AS b1";
                    for (j=0; j<intNumFilTblEmp; j++)
                    {
                        if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                        {
                            strSQL+=" LEFT OUTER JOIN (";
                            strSQL+=" SELECT a1.co_emp, a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.nd_desEsp, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen, a3.ne_numTotSolCre, a1.tx_obsCxC";
                            strSQL+=" FROM tbm_cli AS a1";
                            strSQL+=" INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)";
                            strSQL+=" LEFT OUTER JOIN (SELECT co_emp, co_cli, COUNT(*) AS ne_numTotSolCre FROM tbm_solCre WHERE co_emp=" + objTblModEmp.getValueAt(j, INT_TBL_EMP_COD_EMP) + " GROUP BY co_emp, co_cli) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                            strSQL+=" WHERE a1.co_emp=" + objTblModEmp.getValueAt(j, INT_TBL_EMP_COD_EMP);
                            switch (objParSis.getCodigoMenu())
                            {
                                case 775: //CxC: Actualización de datos de crédito...
                                    strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                                    break;
                                case 2327: //CxP: Actualización de datos de crédito...
                                    strSQL+=" AND a1.st_prv='S' AND a1.st_reg='A'";
                                    break;
                            }
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.tx_ide=b" + (j+2) + ".tx_ide)";
                        }
                    }
                    strSQL+=" WHERE 1=1";
                    //Evitar que aparezcan filas en blanco.
                    i=0;
                    strAux="";
                    for (j=0; j<intNumFilTblEmp; j++)
                    {
                        if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                        {
                            if (i==0)
                                strAux+=" b" + (j+2) + ".co_emp IS NOT NULL";
                            else
                                strAux+=" OR b" + (j+2) + ".co_emp IS NOT NULL";
                            i++;
                        }
                    }
                    if (!strAux.equals(""))
                        strSQL+=" AND (" + strAux + ")";
                    //Obtener la condición.
                    strConSQL="";
                    if (txtIdeCli.getText().length()>0)
                    {
                        strConSQL+=" AND b1.tx_ide='" + txtIdeCli.getText() + "'";
                    }
                    if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                    {
                        i=0;
                        strAux="";
                        for (j=0; j<intNumFilTblEmp; j++)
                        {
                            if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                            {
                                if (i==0)
                                    strAux+=" ((LOWER(b" + (j+2) + ".tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b" + (j+2) + ".tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                                else
                                    strAux+=" OR ((LOWER(b" + (j+2) + ".tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(b" + (j+2) + ".tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                                i++;
                            }
                        }
                        if (!strAux.equals(""))
                            strConSQL+=" AND (" + strAux + ")";
                    }
                    if (txtMonCreDes.getText().length()>0 || txtMonCreHas.getText().length()>0)
                    {
                        i=0;
                        strAux="";
                        for (j=0; j<intNumFilTblEmp; j++)
                        {
                            if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                            {
                                if (i==0)
                                    strAux+=" (b" + (j+2) + ".nd_monCre>=" + txtMonCreDes.getText() + " AND b" + (j+2) + ".nd_monCre<=" + txtMonCreHas.getText() + ")";
                                else
                                    strAux+=" OR (b" + (j+2) + ".nd_monCre>=" + txtMonCreDes.getText() + " AND b" + (j+2) + ".nd_monCre<=" + txtMonCreHas.getText() + ")";
                                i++;
                            }
                        }
                        if (!strAux.equals(""))
                            strConSQL+=" AND (" + strAux + ")";
                    }
                    if (txtMaxDesDes.getText().length()>0 || txtMaxDesHas.getText().length()>0)
                    {
                        i=0;
                        strAux="";
                        for (j=0; j<intNumFilTblEmp; j++)
                        {
                            if (objTblModEmp.isChecked(j, INT_TBL_EMP_CHK))
                            {
                                if (i==0)
                                    strAux+=" (b" + (j+2) + ".nd_maxDes>=" + txtMaxDesDes.getText() + " AND b" + (j+2) + ".nd_maxDes<=" + txtMaxDesDes.getText() + ")";
                                else
                                    strAux+=" OR (b" + (j+2) + ".nd_maxDes>=" + txtMaxDesDes.getText() + " AND b" + (j+2) + ".nd_maxDes<=" + txtMaxDesDes.getText() + ")";
                                i++;
                            }
                        }
                        if (!strAux.equals(""))
                            strConSQL+=" AND (" + strAux + ")";
                    }
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY b1.tx_ide";
                    rst=stm.executeQuery(strSQL);
                }
                else //Empresas
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    //Armar la sentencia SQL.
                    strSQL="";
                    switch (objParSis.getCodigoMenu())
                    {
                        case 775: //CxC: Actualización de datos de crédito...
                            strSQL+="SELECT DISTINCT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, a1.nd_monCre, a1.nd_maxDes, a1.nd_desEsp, a1.ne_diaGra, a1.ne_diaGraChqFec, a1.st_cieRetPen, a3.ne_numTotSolCre, a1.tx_obsCxC";
                            break;
                        case 2327: //CxP: Actualización de datos de crédito...
                            strSQL+="SELECT DISTINCT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.co_forPag, a2.tx_des, Null AS nd_monCre, Null AS nd_maxDes, Null AS nd_desEsp, Null AS ne_diaGra, Null AS ne_diaGraChqFec, Null AS st_cieRetPen, Null AS ne_numTotSolCre, Null AS tx_obsCxC";
                            break;
                    }
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" INNER JOIN tbm_cabForPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_forPag=a2.co_forPag)";
                    strSQL+=" LEFT OUTER JOIN tbr_CliLoc AS c ON (a1.co_emp=c.co_Emp AND a1.co_cli=c.co_cli) ";
                    strSQL+=" LEFT OUTER JOIN tbm_Loc AS l ON (c.co_emp=l.co_Emp AND c.co_loc=l.co_loc) ";
                    strSQL+=" LEFT OUTER JOIN (SELECT co_emp, co_cli, COUNT(*) AS ne_numTotSolCre FROM tbm_solCre WHERE co_emp=" + intCodEmp + " GROUP BY co_emp, co_cli) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    switch (objParSis.getCodigoMenu())
                    {
                        case 775: //CxC: Actualización de datos de crédito...
                            strSQL+=" AND a1.st_cli='S' AND a1.st_reg='A'";
                            break;
                        case 2327: //CxP: Actualización de datos de crédito...
                            strSQL+=" AND a1.st_prv='S' AND a1.st_reg='A'";
                            break;
                    }
                    strConSQL="";
                    if (txtCodCli.getText().length()>0)
                        strConSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                    if (txtCodForPag.getText().length()>0)
                        strConSQL+=" AND a1.co_forPag=" + txtCodForPag.getText();
                    if (txtNomCliDes.getText().length()>0 || txtNomCliHas.getText().length()>0)
                        strConSQL+=" AND ((LOWER(a1.tx_nom) BETWEEN '" + txtNomCliDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a1.tx_nom) LIKE '" + txtNomCliHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
                    if (txtMonCreDes.getText().length()>0 || txtMonCreHas.getText().length()>0)
                        strConSQL+=" AND (a1.nd_monCre>=" + txtMonCreDes.getText() + " AND a1.nd_monCre<=" + txtMonCreHas.getText() + ")";
                    if (txtMaxDesDes.getText().length()>0 || txtMaxDesHas.getText().length()>0)
                        strConSQL+=" AND (a1.nd_maxDes>=" + txtMaxDesDes.getText() + " AND a1.nd_maxDes<=" + txtMaxDesHas.getText() + ")";
                    //Segmentación por Local.
                    if (txtCodLoc.getText().length()>0)
                    {
                        strConSQL+=" AND c.co_loc=" + txtCodLoc.getText();
                    }
                    else 
                    {
                        if (objParSis.getCodigoUsuario() != 1) 
                        {
                            //Valida si el usuario tiene acceso a locales.
                            if ((objLocPrgUsr.validaLocUsr())) 
                            {
                                strConSQL += " AND c.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                            }
                            else
                            {
                                strConSQL += " AND c.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                            }
                        }
                    }
                    strSQL+=strConSQL;
                    strSQL+=" ORDER BY a1.tx_nom";
                    System.out.println("cargarDetReg: "+strSQL);
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
                        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        {
                            vecReg.add(INT_TBL_DAT_COD_CLI,null);
                            vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                            vecReg.add(INT_TBL_DAT_NOM_CLI,null);
                            vecReg.add(INT_TBL_DAT_BUT_SOL_CRE_RES,null);
                            vecReg.add(INT_TBL_DAT_BUT_OBS_CLI,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_PAG,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_TRA,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_POS_CHQ,null);
                            for (j=0; j<intNumFilTblEmp; j++)
                            {
                                vecReg.add(INT_TBL_DAT_CDI_COD_EMP+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_co_emp"));
                                vecReg.add(INT_TBL_DAT_CDI_COD_CLI+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_co_cli"));
                                strAux=rst.getString("b" + (j+2) + "_tx_nom");
                                if (vecReg.get(INT_TBL_DAT_NOM_CLI)==null)
                                    vecReg.setElementAt(strAux, INT_TBL_DAT_NOM_CLI);
                                vecReg.add(INT_TBL_DAT_CDI_COD_FOR_PAG+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_co_forPag"));
                                vecReg.add(INT_TBL_DAT_CDI_DEL_FOR_PAG+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_tx_des"));
                                vecReg.add(INT_TBL_DAT_CDI_BUT_FOR_PAG+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                vecReg.add(INT_TBL_DAT_CDI_MON_CRE+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_monCre"));
                                vecReg.add(INT_TBL_DAT_CDI_MAX_DES+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_maxDes"));
                                vecReg.add(INT_TBL_DAT_CDI_DES_ESP+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_nd_desEsp"));
                                vecReg.add(INT_TBL_DAT_CDI_DIA_GRA_VEN+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_ne_diaGra"));
                                vecReg.add(INT_TBL_DAT_CDI_DIA_GRA_SOP+j*INT_TBL_DAT_NUM_TOT_CDI,rst.getString("b" + (j+2) + "_ne_diaGraChqFec"));
                                strAux=rst.getString("b" + (j+2) + "_st_cieRetPen");
                                if (((strAux==null)?"":strAux).equals("S"))
                                    vecReg.add(INT_TBL_DAT_CDI_CHK_CIE_RET+j*INT_TBL_DAT_NUM_TOT_CDI,Boolean.TRUE);
                                else
                                    vecReg.add(INT_TBL_DAT_CDI_CHK_CIE_RET+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                                strAux=rst.getString("b" + (j+2) + "_ne_numTotSolCre");
                                if (!((strAux==null)?"":strAux).equals(""))
                                    vecReg.setElementAt("", INT_TBL_DAT_BUT_SOL_CRE_RES);
                                strAux=rst.getString("b" + (j+2) + "_tx_obsCxC");
                                if (!((strAux==null)?"":strAux).equals(""))
                                    vecReg.setElementAt("", INT_TBL_DAT_BUT_OBS_CLI);
                                vecReg.add(INT_TBL_DAT_CDI_BUT_MAS_INF+j*INT_TBL_DAT_NUM_TOT_CDI,null);
                            }
                        }
                        else
                        {
                            strAux=rst.getString("co_cli");
                            vecReg.add(INT_TBL_DAT_COD_CLI,strAux);
                            vecReg.add(INT_TBL_DAT_IDE_CLI,rst.getString("tx_ide"));
                            vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nom"));
                            vecReg.add(INT_TBL_DAT_BUT_SOL_CRE_RES,null);
                            vecReg.add(INT_TBL_DAT_BUT_OBS_CLI,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_PAG,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_TRA,null);
                            vecReg.add(INT_TBL_DAT_BUT_HIS_POS_CHQ,null);
                            vecReg.add(INT_TBL_DAT_CDI_COD_EMP,"" + intCodEmp);
                            vecReg.add(INT_TBL_DAT_CDI_COD_CLI,strAux);
                            vecReg.add(INT_TBL_DAT_CDI_COD_FOR_PAG,rst.getString("co_forPag"));
                            vecReg.add(INT_TBL_DAT_CDI_DEL_FOR_PAG,rst.getString("tx_des"));
                            vecReg.add(INT_TBL_DAT_CDI_BUT_FOR_PAG,null);
                            vecReg.add(INT_TBL_DAT_CDI_MON_CRE,rst.getString("nd_monCre"));
                            vecReg.add(INT_TBL_DAT_CDI_MAX_DES,rst.getString("nd_maxDes"));
                            vecReg.add(INT_TBL_DAT_CDI_DES_ESP,rst.getString("nd_desEsp"));
                            vecReg.add(INT_TBL_DAT_CDI_DIA_GRA_VEN,rst.getString("ne_diaGra"));
                            vecReg.add(INT_TBL_DAT_CDI_DIA_GRA_SOP,rst.getString("ne_diaGraChqFec"));
                            strAux=rst.getString("st_cieRetPen");
                            if (((strAux==null)?"":strAux).equals("S"))
                                vecReg.add(INT_TBL_DAT_CDI_CHK_CIE_RET,Boolean.TRUE);
                            else
                                vecReg.add(INT_TBL_DAT_CDI_CHK_CIE_RET,null);
                            strAux=rst.getString("ne_numTotSolCre");
                            if (!((strAux==null)?"":strAux).equals(""))
                                vecReg.setElementAt("", INT_TBL_DAT_BUT_SOL_CRE_RES);
                            strAux=rst.getString("tx_obsCxC");
                            if (!((strAux==null)?"":strAux).equals(""))
                                vecReg.setElementAt("", INT_TBL_DAT_BUT_OBS_CLI);
                            vecReg.add(INT_TBL_DAT_CDI_BUT_MAS_INF,null);
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
     * Esta función permite actualizar los registros del detalle.
     * @return true: Si se pudo actualizar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarDet()
    {
        int intNumFil, intNumFilTblEmp, i, j, k;
        StringBuffer stb=new StringBuffer();
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                intNumFil=objTblMod.getRowCountTrue();
                intNumFilTblEmp=objTblModEmp.getRowCountTrue();
                //Crear la tabla virtual que servirá para actualizar la tabla "tbm_cli" con un solo update.
                k=0;
                for (i=0; i<intNumFil; i++)
                {
                    if (objUti.parseString(objTblMod.getValueAt(i,0)).equals("M"))
                    {
                        for (j=0; j<intNumFilTblEmp; j++)
                        {
                            //Validar que exista el cliente para la empresa especificada.
                            if (objTblMod.getValueAt(i, INT_TBL_DAT_CDI_COD_EMP+j*INT_TBL_DAT_NUM_TOT_CDI)!=null)
                            {
                                if (k>0)
                                    stb.append(" UNION ALL ");
                                stb.append("SELECT " + objTblMod.getValueAt(i, INT_TBL_DAT_CDI_COD_EMP+j*INT_TBL_DAT_NUM_TOT_CDI) + " AS co_emp");
                                stb.append(", " + objTblMod.getValueAt(i, INT_TBL_DAT_CDI_COD_CLI+j*INT_TBL_DAT_NUM_TOT_CDI) + " AS co_cli");
                                stb.append(", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_COD_FOR_PAG+j*INT_TBL_DAT_NUM_TOT_CDI), 2) + " AS co_forPag");
                                strAux=objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_MON_CRE+j*INT_TBL_DAT_NUM_TOT_CDI), 2);
                                if (strAux.equalsIgnoreCase("Null"))
                                    stb.append(", CAST(NULL AS NUMERIC) AS nd_monCre");
                                else
                                    stb.append(", " + strAux + " AS nd_monCre");
                                stb.append(", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_MAX_DES+j*INT_TBL_DAT_NUM_TOT_CDI), 3) + " AS nd_maxDes");
                                stb.append(", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_DES_ESP+j*INT_TBL_DAT_NUM_TOT_CDI), 3) + " AS nd_desEsp");
                                stb.append(", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_DIA_GRA_VEN+j*INT_TBL_DAT_NUM_TOT_CDI), 3) + " AS ne_diaGra");
                                stb.append(", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_CDI_DIA_GRA_SOP+j*INT_TBL_DAT_NUM_TOT_CDI), 3) + " AS ne_diaGraChqFec");
                                if (objTblMod.isChecked(i, INT_TBL_DAT_CDI_CHK_CIE_RET+j*INT_TBL_DAT_NUM_TOT_CDI))
                                    stb.append(", CAST('S' AS CHAR) AS st_cieRetPen");
                                else
                                    stb.append(", CAST('N' AS CHAR) AS st_cieRetPen");
                                stb.append(", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_ultMod");
                                stb.append(", " + objParSis.getCodigoUsuario() + " AS co_usrMod");
                                k++;
                            }
                        }
                    }
                }
                //Insertar y actualizar sólo en caso de existir cambios.
                if (k>0)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbh_cli(co_emp, co_cli, co_his, st_cli, st_prv, tx_tipIde, tx_ide, tx_nom, tx_dir";
                    strSQL+=", tx_refUbi, tx_tel1, tx_tel2, tx_tel3, tx_tel, tx_fax, tx_cas, tx_dirWeb, tx_corEle, tx_tipPer, co_tipPer, co_ciu, co_zon";
                    strSQL+=", tx_perCon, tx_telCon, tx_corEleCon, co_ven, co_grp, co_forPag, nd_monCre, ne_diaGra, ne_diaGraChqFec, nd_maxDes, nd_marUti";
                    strSQL+=", tx_repLeg, st_cieRetPen, tx_idePro, tx_nomPro, tx_dirPro, tx_telPro, tx_nacPro, fe_conEmp, tx_tipActEmp, tx_obsPro, ne_numMaxVenCon";
                    strSQL+=", tx_obsVen, tx_obsInv, tx_obsCxC, tx_obsCxP, fe_ultActDat, fe_proActDat, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng";
                    strSQL+=", co_usrMod, nd_desEsp, fe_his, co_usrHis)";
                    strSQL+=" SELECT a2.co_emp, a2.co_cli, (CASE WHEN a3.co_his IS NULL THEN 1 ELSE a3.co_his+1 END) AS co_his, a2.st_cli, a2.st_prv, a2.tx_tipIde, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=", a2.tx_refUbi, a2.tx_tel1, a2.tx_tel2, a2.tx_tel3, a2.tx_tel, a2.tx_fax, a2.tx_cas, a2.tx_dirWeb, a2.tx_corEle, a2.tx_tipPer, a2.co_tipPer, a2.co_ciu, a2.co_zon";
                    strSQL+=", a2.tx_perCon, a2.tx_telCon, a2.tx_corEleCon, a2.co_ven, a2.co_grp, a2.co_forPag, a2.nd_monCre, a2.ne_diaGra, a2.ne_diaGraChqFec, a2.nd_maxDes, a2.nd_marUti";
                    strSQL+=", a2.tx_repLeg, a2.st_cieRetPen, a2.tx_idePro, a2.tx_nomPro, a2.tx_dirPro, a2.tx_telPro, a2.tx_nacPro, a2.fe_conEmp, a2.tx_tipActEmp, a2.tx_obsPro, a2.ne_numMaxVenCon";
                    strSQL+=", a2.tx_obsVen, a2.tx_obsInv, a2.tx_obsCxC, a2.tx_obsCxP, a2.fe_ultActDat, a2.fe_proActDat, a2.tx_obs1, a2.tx_obs2, a2.st_reg, a2.fe_ing, a2.fe_ultMod, a2.co_usrIng";
                    strSQL+=", a2.co_usrMod, a2.nd_desEsp";
                    strSQL+=", " + objParSis.getFuncionFechaHoraBaseDatos() + " AS fe_his";
                    strSQL+=", " + objParSis.getCodigoUsuario() + " AS co_usrHis";
                    strSQL+=" FROM (";
                    strSQL+=stb.toString();
                    strSQL+=" ) AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL+=" LEFT OUTER JOIN ( SELECT co_emp, co_cli, MAX(co_his) AS co_his FROM tbh_cli GROUP BY co_emp, co_cli ) AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                    stm.executeUpdate(strSQL);
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="UPDATE tbm_cli";
                    strSQL+=" SET co_forPag=a1.co_forPag";
                    strSQL+=", nd_moncre=a1.nd_moncre";
                    strSQL+=", nd_maxdes=a1.nd_maxdes";
                    strSQL+=", nd_desEsp=a1.nd_desEsp";
                    strSQL+=", ne_diaGra=a1.ne_diaGra";
                    strSQL+=", ne_diaGraChqFec=a1.ne_diaGraChqFec";
                    strSQL+=", st_cieRetPen=a1.st_cieRetPen";
                    strSQL+=", fe_ultMod=a1.fe_ultMod";
                    strSQL+=", co_usrMod=a1.co_usrMod";
                    strSQL+=" FROM (";
                    strSQL+=stb.toString();
                    strSQL+=" ) AS a1";
                    strSQL+=" WHERE tbm_cli.co_emp=a1.co_emp AND tbm_cli.co_cli=a1.co_cli";
                    stm.executeUpdate(strSQL);
                }
                stm.close();
                con.close();
                stm=null;
                con=null;
                //Inicializo el estado de las filas.
                objTblMod.initRowsState();
                objTblMod.setDataModelChanged(false);
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
        int intTipLoc=1; //Tipo de Consulta para generar query de locales. 1=Código Local; 2=Todos los datos del local.
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
                switch (objParSis.getCodigoMenu())
                {
                    case 775: //CxC: Actualización de datos de crédito...
                        strSQL+=" AND b2.st_cli='S' AND b2.st_reg='A'";
                        break;
                    case 2327: //CxP: Actualización de datos de crédito...
                        strSQL+=" AND b2.st_prv='S' AND b2.st_reg='A'";
                        break;
                }
                strSQL+=" ORDER BY b2.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.
                if (objParSis.getCodigoUsuario() == 1)
                {
                    strSQL = "";
                    strSQL += " SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL += " FROM tbm_cli as a1";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                } 
                else 
                {
                    strSQL = "";
                    strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL += " FROM tbm_cli AS a1";
                    strSQL += " INNER JOIN tbr_cliLoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli)";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    //Valida si el usuario tiene acceso a locales.
                    if ((objLocPrgUsr.validaLocUsr())) 
                    {
                        strSQL += " AND a2.co_loc in (" + objLocPrgUsr.cargarLocUsr(intTipLoc) + ")";
                    }
                    else 
                    {
                        strSQL += " AND a2.co_loc not in (" + objLocPrgUsr.cargarLoc(intTipLoc) + ")";
                    }
                }
                switch (objParSis.getCodigoMenu()) 
                {
                    case 775: //CxC: Actualización de datos de crédito...
                        strSQL += " AND a1.st_cli='S' AND a1.st_reg='A'";
                        break;
                    case 2327: //CxP: Actualización de datos de crédito...
                        strSQL += " AND a1.st_prv='S' AND a1.st_reg='A'";
                        break;
                }
                strSQL += " ORDER BY a1.tx_nom";
            }
            //System.out.println("configurarVenConCli: "+strSQL);
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
            strSQL+=" SELECT a1.co_forPag, a1.tx_des";
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
           
            strSQL="";
            
            if(objParSis.getCodigoUsuario()==1)  
            {    strSQL=objLocPrgUsr.cargarLoc(2);       }
            else 
            {    strSQL=objLocPrgUsr.cargarLocUsr(2);    }

            //System.out.println("ConfigurarVenConLoc: "+strSQL);
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
                        txtNomCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
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
                            txtNomCli.setText(vcoCli.getValueAt(3));
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
                        txtNomCli.setText(vcoCli.getValueAt(3));
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
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtIdeCli.setText(strIdeCli);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIdeCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
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
                            txtNomCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtNomCli.setText(strDesLarCli);
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
                    vcoForPag.setVisible(true);
                    if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        vcoForPag.setVisible(true);
                        if (vcoForPag.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
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
                        txtLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtLoc.setText(vcoLoc.getValueAt(2));
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
                            txtLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoLoc.buscar("a1.tx_nom", txtLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtLoc.setText(vcoLoc.getValueAt(2));
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
                            txtLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtLoc.setText(strLoc);
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
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModEmp.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1 && tblEmp.columnAtPoint(evt.getPoint())==INT_TBL_EMP_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    //Mostrar columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModEmp.setChecked(true, i, INT_TBL_EMP_CHK);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    //Ocultar columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModEmp.setChecked(false, i, INT_TBL_EMP_CHK);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DEL_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_FOR_PAG+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MON_CRE+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_MAX_DES+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DES_ESP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_VEN+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_DIA_GRA_SOP+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_CHK_CIE_RET+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
                        objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CDI_BUT_MAS_INF+i*INT_TBL_DAT_NUM_TOT_CDI, tblDat);
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
     * Esta función abre el formulario especificado.
     * @param intFrmAbr El indice del formulario que se desea abrir.
     * @return true: Si se pudo abrir el formulario.
     * <BR>false: En el caso contrario.
     */
    private boolean abrirFrm(int intFrmAbr)
    {
        Class objVen;
        Class objCla[];
        java.lang.reflect.Constructor objCon;
        Object objObj[];
        javax.swing.JInternalFrame ifrVen;
        int intFilSel;
        boolean blnRes=true;
        try
        {
            switch (intFrmAbr)
            {
                case 0: //Panel de control de solicitudes de crédito.
                    //Obtener el constructor de la clase que se va a invocar.
                    objVen=Class.forName("CxC.ZafCxC32.ZafCxC32");
                    objCla=new Class[3];
                    objCla[0]=objParSis.getClass();
                    objCla[1]=new Integer(0).getClass();
                    objCla[2]=new Integer(0).getClass();
                    objCon=objVen.getConstructor(objCla);
                    intFilSel=tblDat.getSelectedRow();
                    //Inicializar el constructor que se obtuvo.
                    objObj=new Object[3];
                    objObj[0]=objParSis;
                    objObj[1]=new Integer(objTblMod.getValueAt(intFilSel,getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP)).toString());
                    objObj[2]=new Integer(objTblMod.getValueAt(intFilSel,getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_CLI)).toString());
                    ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                    this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    ifrVen.show();
                    break;
                case 1: //Solicitudes de crédito por analizar.
                    //Obtener el constructor de la clase que se va a invocar.
                    objVen=Class.forName("CxC.ZafCxC33.ZafCxC33");
                    objCla=new Class[1];
                    objCla[0]=objParSis.getClass();
                    objCon=objVen.getConstructor(objCla);
                    intFilSel=tblDat.getSelectedRow();
                    //Inicializar el constructor que se obtuvo.
                    objObj=new Object[1];
                    objObj[0]=objParSis;
                    ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                    this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    ifrVen.show();
                    break;
                case 2: //Solicitud de crédito resumida.
                case 4: //Historial de pago de clientes.
                case 5: //Historial de transacciones de clientes
                case 6: //Historial de postergaciones de cheques de clientes
                   
                    objVen=Class.forName(intFrmAbr==2?"CxC.ZafCxC23.ZafCxC23_03":(intFrmAbr==4?"CxC.ZafCxC45.ZafCxC45":(intFrmAbr==5?"CxC.ZafCxC19.ZafCxC19":"CxC.ZafCxC77.ZafCxC77")));
                    if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    {
                        //Obtener el constructor de la clase que se va a invocar.
                        objCla=new Class[2];
                        objCla[0]=objParSis.getClass();
                        objCla[1]=new String("").getClass();
                        objCon=objVen.getConstructor(objCla);
                        intFilSel=tblDat.getSelectedRow();
                        //Inicializar el constructor que se obtuvo.
                        objObj=new Object[2];
                        objObj[0]=objParSis;
                        objObj[1]=objTblMod.getValueAt(intFilSel,getPosRelColEspColSel(INT_TBL_DAT_IDE_CLI)).toString();
                        ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                        this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                        ifrVen.show();
                    }
                    else
                    {
                        //Obtener el constructor de la clase que se va a invocar.
                        objCla=new Class[3];
                        objCla[0]=objParSis.getClass();
                        objCla[1]=new Integer(0).getClass();
                        objCla[2]=new Integer(0).getClass();
                        objCon=objVen.getConstructor(objCla);
                        intFilSel=tblDat.getSelectedRow();
                        //Inicializar el constructor que se obtuvo.
                        objObj=new Object[3];
                        objObj[0]=objParSis;
                        objObj[1]=new Integer(objTblMod.getValueAt(intFilSel,getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_EMP)).toString());
                        objObj[2]=new Integer(objTblMod.getValueAt(intFilSel,getPosRelColEspColSel(INT_TBL_DAT_CDI_COD_CLI)).toString());
                        ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                        this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                        ifrVen.show();
                    }
                    break;
                case 3: //Más información.
                    //Obtener el constructor de la clase que se va a invocar.
                    objVen=Class.forName("CxC.ZafCxC23.ZafCxC23_05");
                    objCla=new Class[1];
                    objCla[0]=objParSis.getClass();
                    objCon=objVen.getConstructor(objCla);
                    //Inicializar el constructor que se obtuvo.
                    objObj=new Object[1];
                    objObj[0]=objParSis;
                    ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
                    this.getParent().add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    ifrVen.show();
                    break;
            }
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
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la posición relativa de la columna especificada con relación a la columna seleccionada.
     * La idea es que a partir de la columna seleccionada sea posible determinar la posición de la columna especificada.
     * @return Puede retornar uno de los siguientes valores:
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>-1: Si ocurrió un error al ejecutar el método.
     * <LI>La posición relativa de la columna especificada.
     * </UL>
     */
    private int getPosRelColEspColSel(int intCol)
    {
        int intColSel;
        int intRes=-1;
        try
        {
            intColSel=tblDat.getSelectedColumn();
            if (intColSel!=-1)
            {
                intRes=intCol + (((intColSel-INT_TBL_DAT_NUM_TOT_CES)-((intColSel-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CDI))/INT_TBL_DAT_NUM_TOT_CDI)*INT_TBL_DAT_NUM_TOT_CDI;
            }
        }
        catch(Exception e)
        {
            intRes=-1;
        }
        return intRes;
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
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                objCxC23_02.cargarReg();
                objCxC23_02.show();
                //Validar que sólo se consulte si no existen clientes repetidos.
                if (!objCxC23_02.isCliRep())
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
                }
                objThrGUI=null;
            }
            else
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
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaEmp extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblEmp.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_EMP_LIN:
                    strMsg="";
                    break;
                case INT_TBL_EMP_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_EMP_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblEmp.getTableHeader().setToolTipText(strMsg);
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
            if (intCol>INT_TBL_DAT_NUM_TOT_CES)
                intCol=(intCol-INT_TBL_DAT_NUM_TOT_CES)%INT_TBL_DAT_NUM_TOT_CDI+INT_TBL_DAT_NUM_TOT_CES;
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
                case INT_TBL_DAT_BUT_SOL_CRE_RES:
                    strMsg="Muestra la \"Solicitud de crédito (Resumida)\"";
                    break;
                case INT_TBL_DAT_BUT_OBS_CLI:
                    strMsg="Observación CxC";
                    break;
                case INT_TBL_DAT_BUT_HIS_PAG:
                    strMsg="Historial de pago de clientes";
                    break;
                case INT_TBL_DAT_BUT_HIS_TRA:
                    strMsg="Historial de transacciones de clientes";
                    break;
                case INT_TBL_DAT_BUT_HIS_POS_CHQ:
                    strMsg="Historial de postergaciones de cheques de clientes";
                    break;
                case INT_TBL_DAT_CDI_DEL_FOR_PAG:
                    strMsg="Forma de pago";
                    break;
                case INT_TBL_DAT_CDI_MON_CRE:
                    strMsg="Monto de crédito";
                    break;
                case INT_TBL_DAT_CDI_MAX_DES:
                    strMsg="Máximo porcentaje de descuento";
                    break;
                case INT_TBL_DAT_CDI_DES_ESP:
                    strMsg="Porcentaje de descuento especial";
                    break;
                case INT_TBL_DAT_CDI_DIA_GRA_VEN:
                    strMsg="Días de gracia (Documentos vencidos)";
                    break;
                case INT_TBL_DAT_CDI_DIA_GRA_SOP:
                    strMsg="Días de gracia (Documentos que necesitan soporte. Ej: Cheques a fecha)";
                    break;
                case INT_TBL_DAT_CDI_CHK_CIE_RET:
                    strMsg="Cierre de crédito por retenciones pendientes";
                    break;
                case INT_TBL_DAT_CDI_BUT_MAS_INF:
                    strMsg="Panel de control de solicitudes de crédito";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}