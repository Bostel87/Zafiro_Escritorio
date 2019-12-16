/*  
 *  ZafCom79.java
 *    
 *  Created on 02 de Abril de 2013, 10:10 PM  
 */
package Compras.ZafCom79;
import GenOD.ZafGenGuiRemPryTra;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafCfgSer.ZafCfgSer;
import Librerias.ZafCnfDoc.ZafCnfDoc;
import Librerias.ZafGenFacAut.ZafGenFacAut;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.Vector;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.awt.Color;
import static java.lang.System.out;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author  Alex Morán
 */
public class ZafCom79 extends javax.swing.JInternalFrame 
{
    //Constantes.
    //Columnas del JTable: Bodegas.
    private static final int INT_TBL_LIN   =0;
    private static final int INT_TBL_CHKBOD=1;
    private static final int INT_TBL_CODBOD=2;
    private static final int INT_TBL_NOMBOD=3;
    
    //Jtable: TblDat
    private static final int INT_TBL_DAT_LIN=0;                          //Línea
    private static final int INT_TBL_DAT_CODSEG = 1;                     //Código de Seguimiento.
    
    private static final int INT_TBL_DAT_NUMCOTVEN = 2;                  //Número de Cotización.
    
    private static final int INT_TBL_DAT_CODEMPSOLTRA=3;                 //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOCSOLTRA=4;                 //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOCSOLTRA=5;              //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_CODDOCSOLTRA=6;                 //Código de Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOCSOLTRA=7;           //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOCSOLTRA=8;           //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDOCSOLTRA=9;                 //Número de Documento.
    private static final int INT_TBL_DAT_BUTCONSOLTRA=10;                 //Botón de Solicitud de Transferencia.
    
    private static final int INT_TBL_DAT_NOMLOCMOVINV=11;                //Local.
    private static final int INT_TBL_DAT_CODEMPMOVINV=12;                //Código de Empresa.
    private static final int INT_TBL_DAT_CODLOCMOVINV=13;                //Código de Local.
    private static final int INT_TBL_DAT_CODTIPDOCMOVINV=14;             //Código de Tipo de Documento.
    private static final int INT_TBL_DAT_CODDOCMOVINV=15;                //Código de Documento.
    private static final int INT_TBL_DAT_DESCORTIPDOCMOVINV=16;          //Descripción corta de Tipo de Documento.
    private static final int INT_TBL_DAT_DESLARTIPDOCMOVINV=17;          //Descripción larga de Tipo de Documento.
    private static final int INT_TBL_DAT_NUMDOCMOVINV=18;                //Número de Documento.
    private static final int INT_TBL_DAT_MOTTRAMOVINV=19;                //Motivo de Traslado.
    private static final int INT_TBL_DAT_FECDOCMOVINV=20;                //Fecha de Documento.
    private static final int INT_TBL_DAT_CODBODORGMOVINV=21;             //Código de Bodega Origen.
    private static final int INT_TBL_DAT_NOMBODORGMOVINV=22;             //Nombre de Bodega Origen.
    private static final int INT_TBL_DAT_CODBODDESMOVINV=23;             //Código de Bodega Destino.
    private static final int INT_TBL_DAT_NOMBODDESMOVINV=24;             //Nombre de Bodega Destino.
    private static final int INT_TBL_DAT_CODREGMOVINV=25;                //Código de Registro.
    private static final int INT_TBL_DAT_CODITM=26;                      //Código de Item.
    private static final int INT_TBL_DAT_CODALTITM=27;                   //Código alterno de Item.
    private static final int INT_TBL_DAT_CODALTDOS=28;                   //Código alterno 2 de Item.
    private static final int INT_TBL_DAT_NOMITM=29;                      //Nombre de Item.
    private static final int INT_TBL_DAT_CANMOVINV=30;                   //Cantidad de Guía de Remisión.
    private static final int INT_TBL_DAT_UNIMED=31;                      //Unidad de Medida.
    private static final int INT_TBL_DAT_CANINGBOD=32;                   //Cantidad por Ingresar a Bodega.
    private static final int INT_TBL_DAT_CANINGDES=33;                   //Cantidad por Ingresar a Despacho.
    private static final int INT_TBL_DAT_DIASINCONF=34;                  //Días sin Confirmar. 
    private static final int INT_TBL_DAT_ESTDIA=35;                      //Estado.
    private static final int INT_TBL_DAT_OBS=36;                         //Observación: Utilizada para Mostrar la SOTRIN.
    private static final int INT_TBL_DAT_NOMCLIPRV=37;                   //Nombre de Proveedor.
    private static final int INT_TBL_DAT_ESTCONINV=38;                   //Estado de Confirmacion de Documento de Ingreso.
    private static final int INT_TBL_DAT_TIPMOV=39;                      //Tipo Movimiento Inventario.
    private static final int INT_TBL_DAT_ITMCODL=40;                     //Indica si es código L.
    private static final int INT_TBL_DAT_TIECNF=41;                      //Indica si tiene confirmación.
    
    private static final int INT_TBL_DAT_CHKCONINVAUT=42;                //Casilla de verificación que indica que no necesita confirmación.
    private static final int INT_TBL_DAT_BUTCONDOCINV=43;                //Botón de Documento de Inventario.

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblCelRenLbl objTblCelRenLbl;                                    //Render: Presentar JLabel en JTable.
    private ZafTblMod objTblMod, objTblModBod;
    private ZafThreadGUI objThrGUI;                                             //Hilo de Consulta.
    private ZafThreadGUIGua objThrGUIGua;                                             //Hilo de Guardar.    
    
    private ZafTblCelRenChk objTblCelRenChkBod;
    private ZafTblCelEdiChk objTblCelEdiChkBod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenBut objTblCelRenButConSolTraInv;  
    private ZafTblCelRenLbl objTblCelRenLblColSolTraInv, objTblCelRenLblColCenSol, objTblCelRenLblColSolTraInvNum;
    private ZafTblCelRenChk objTblCelRenChk;                                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblOrd objTblOrd;                                                //JTable de ordenamiento.
    private ZafTblBus objTblBus;                                                //Editor de búsqueda.
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafSelFec objSelFec;                                                //Selector de Fecha.
    private ZafCnfDoc objCnfDoc;
    private ZafGenFacAut objGenFacAut;
    private ZafGenOdPryTra objGenOdPryTra;
    private ZafGenGuiRemPryTra objGenGuiRemPryTra;
    private ZafCfgSer objCfgSer;                                                //Obtiene Configuracion de Servicios.
    private ZafVenCon vcoItm;                                                   //Ventana de consulta.
    private ZafPerUsr objPerUsr;                                                //Permisos Usuarios.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;
    private Vector vecDat, vecCab, vecReg;
    private final Color colFonCol =new Color(255,0,0);
    private boolean blnCon;                                                     //true: Continua la ejecución del hilo.
    private boolean blnGua;                                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodTblBod=true;
    private boolean blnBloqueo = false, blnVenConEme=false, blnCotVen=false;     
    private int intSqlBod=1;
    private HashSet hsCodSeg;
    private String strCodItm="", strCodAlt="", strCodAlt2="",  strNomItm="";
    private String strIpImp;                                                    //Ip donde se ejecuta el servidor de Impresion de OD y Guias.
    private String sqlFilCot="";
       private ZafTblPopMnu objTblPopMnu;  
    //javax.swing.JDesktopPane dskGen;
    
    public final String strVersion = " v0.11.6 ";
       
    /**
     * Crea una nueva instancia de la clase ZafIndRpt.
     */
    public ZafCom79(ZafParSis obj) 
    {
        try
        {
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            initComponents();
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
        bgpFilOPt1 = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        spnFil = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkItmCanPenBod = new javax.swing.JCheckBox();
        chkItmCanPenDes = new javax.swing.JCheckBox();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAltItm = new javax.swing.JTextField();
        txtCodAlt2 = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panItmDesHas = new javax.swing.JPanel();
        lblDesItm = new javax.swing.JLabel();
        txtCodAltItmDes = new javax.swing.JTextField();
        lblHasItm = new javax.swing.JLabel();
        txtCodAltItmHas = new javax.swing.JTextField();
        panItmTer = new javax.swing.JPanel();
        lblItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        panCot = new javax.swing.JPanel();
        txtNumCot = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panReg = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        lblTit.setText("Listado de ingresos físicos de mercadería pendientes de confirmar...");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        spnFil.setPreferredSize(new java.awt.Dimension(0, 360));

        panFil.setPreferredSize(new java.awt.Dimension(10, 430));
        panFil.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de Bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        spnBod.setPreferredSize(new java.awt.Dimension(445, 380));

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
        panBod.setBounds(10, 20, 620, 100);

        chkItmCanPenBod.setSelected(true);
        chkItmCanPenBod.setText("Mostrar los ítems con cantidad pendiente de ingresar a bodega.");
        chkItmCanPenBod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkItmCanPenBodStateChanged(evt);
            }
        });
        panFil.add(chkItmCanPenBod);
        chkItmCanPenBod.setBounds(30, 270, 440, 20);

        chkItmCanPenDes.setSelected(true);
        chkItmCanPenDes.setText("Mostrar los ítems con cantidad pendiente de ingresar a despacho.");
        chkItmCanPenDes.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkItmCanPenDesStateChanged(evt);
            }
        });
        panFil.add(chkItmCanPenDes);
        chkItmCanPenDes.setBounds(30, 290, 440, 20);

        optTod.setSelected(true);
        optTod.setText("Todos los ítems");
        optTod.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                optTodStateChanged(evt);
            }
        });
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 130, 400, 20);

        optFil.setText("Sólo los  ítems que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(10, 150, 400, 20);

        lblItm.setText("Item:");
        panFil.add(lblItm);
        lblItm.setBounds(30, 180, 80, 15);

        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodItm);
        txtCodItm.setBounds(70, 180, 30, 20);

        txtCodAltItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmFocusLost(evt);
            }
        });
        txtCodAltItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltItmActionPerformed(evt);
            }
        });
        panFil.add(txtCodAltItm);
        txtCodAltItm.setBounds(100, 180, 84, 20);

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
        txtCodAlt2.setBounds(184, 180, 66, 20);

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
        txtNomItm.setBounds(250, 180, 370, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(620, 180, 20, 20);

        panItmDesHas.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmDesHas.setLayout(null);

        lblDesItm.setText("Desde:");
        panItmDesHas.add(lblDesItm);
        lblDesItm.setBounds(12, 20, 60, 16);

        txtCodAltItmDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmDesFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmDes);
        txtCodAltItmDes.setBounds(60, 20, 100, 20);

        lblHasItm.setText("Hasta:");
        panItmDesHas.add(lblHasItm);
        lblHasItm.setBounds(170, 20, 50, 16);

        txtCodAltItmHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmHasFocusLost(evt);
            }
        });
        panItmDesHas.add(txtCodAltItmHas);
        txtCodAltItmHas.setBounds(220, 20, 100, 20);

        panFil.add(panItmDesHas);
        panItmDesHas.setBounds(30, 210, 330, 48);

        panItmTer.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del Item"));
        panItmTer.setLayout(null);

        lblItmTer.setText("Terminan con:");
        panItmTer.add(lblItmTer);
        lblItmTer.setBounds(20, 20, 120, 16);

        txtCodAltItmTer.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltItmTerFocusLost(evt);
            }
        });
        panItmTer.add(txtCodAltItmTer);
        txtCodAltItmTer.setBounds(140, 20, 92, 20);

        panFil.add(panItmTer);
        panItmTer.setBounds(380, 210, 260, 48);

        panCot.setBorder(javax.swing.BorderFactory.createTitledBorder("Número Cotización"));
        panCot.setLayout(null);

        txtNumCot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumCotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumCotFocusLost(evt);
            }
        });
        panCot.add(txtNumCot);
        txtNumCot.setBounds(20, 20, 90, 20);

        panFil.add(panCot);
        panCot.setBounds(510, 260, 130, 50);

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
        ));
        tblDat.setAutoscrolls(false);
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);
        panRpt.getAccessibleContext().setAccessibleParent(tabFrm);

        panReg.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Registro", panReg);

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
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objTblCelRenLblColSolTraInv = new ZafTblCelRenLbl();
            objTblCelRenLblColSolTraInvNum = new ZafTblCelRenLbl();
            objTblCelRenChk = new ZafTblCelRenChk();
           
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            
            //Configurar Selector de Fecha. 
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxChecked(true);
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setFlechaDerechaSeleccionada(true);
            objSelFec.setFlechaIzquierdaSeleccionada(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setBounds(6, 4, 600, 75);
            //panFil.add(objSelFec);
            
            //Ocultar Código ítem.
            txtCodItm.setVisible(false);
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3327)) 
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(4136)) 
            {
                butGua.setVisible(false);
                panCot.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3328)) 
            {
                butCer.setVisible(false);
            }
            
            //Configurar ZafVenCon
            configurarVenConItm();            
            
            //Configuraciones Varias.
            configurarTblDat();
            configurarTblBod();
            cargarBod();
            
            //Se oculta panel de Confirmación de Registros.
            tabFrm.remove(panReg);
        }
        catch(Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (isCamVal()) 
        {
            consultar();
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
            dispose();
    }//GEN-LAST:event_exitForm

    private void chkItmCanPenBodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkItmCanPenBodStateChanged
        if((!chkItmCanPenBod.isSelected()) && (!chkItmCanPenDes.isSelected()))
            chkItmCanPenBod.setSelected(true);
    }//GEN-LAST:event_chkItmCanPenBodStateChanged

    private void chkItmCanPenDesStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkItmCanPenDesStateChanged
        if((!chkItmCanPenBod.isSelected()) && (!chkItmCanPenDes.isSelected()))
            chkItmCanPenDes.setSelected(true);
    }//GEN-LAST:event_chkItmCanPenDesStateChanged

    private void optTodStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_optTodStateChanged
        if (optTod.isSelected())
        {
            txtCodItm.setText("");
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
            chkItmCanPenBod.setSelected(true);
            chkItmCanPenDes.setSelected(true);

            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
        {
            optFil.setSelected(false);
        }
    }//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        if (optFil.isSelected())
        {
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_optFilActionPerformed

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmActionPerformed

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
                txtCodAltItm.setText("");
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
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtCodAlt2FocusLost

    private void txtCodAlt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAlt2ActionPerformed
        txtCodAlt2.transferFocus();
    }//GEN-LAST:event_txtCodAlt2ActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm = txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
        {
            txtNomItm.setText(strNomItm);
        }

        if (txtNomItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
        if (txtCodAlt2.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltItmDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusGained
        txtCodAltItmDes.selectAll();
    }//GEN-LAST:event_txtCodAltItmDesFocusGained

    private void txtCodAltItmDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmDesFocusLost
        if (txtCodAltItmDes.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAlt2.setText("");
            txtNomItm.setText("");
            if (txtCodAltItmHas.getText().length() == 0)
            {
                txtCodAltItmHas.setText(txtCodAltItmDes.getText());
            }
        }
    }//GEN-LAST:event_txtCodAltItmDesFocusLost

    private void txtCodAltItmHasFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusGained
        txtCodAltItmHas.selectAll();
    }//GEN-LAST:event_txtCodAltItmHasFocusGained

    private void txtCodAltItmHasFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmHasFocusLost
        if (txtCodAltItmHas.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmHasFocusLost

    private void txtCodAltItmTerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusGained
        txtCodAltItmTer.selectAll();
    }//GEN-LAST:event_txtCodAltItmTerFocusGained

    private void txtCodAltItmTerFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmTerFocusLost
        if (txtCodAltItmTer.getText().length() > 0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
            txtCodAltItm.setText("");
            txtCodAlt2.setText("");
            txtNomItm.setText("");
        }
    }//GEN-LAST:event_txtCodAltItmTerFocusLost

    private void txtCodAltItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusGained
        strCodAlt=txtCodAltItm.getText();
        txtCodAltItm.selectAll();
    }//GEN-LAST:event_txtCodAltItmFocusGained

    private void txtCodAltItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAltItm.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAltItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAltItm.setText("");
                txtCodAlt2.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAltItm.setText(strCodAlt);

        if (txtCodAltItm.getText().length() > 0)
        {
            optTod.setSelected(false);
            optFil.setSelected(true);
            txtCodAltItmDes.setText("");
            txtCodAltItmHas.setText("");
            txtCodAltItmTer.setText("");
        }

    }//GEN-LAST:event_txtCodAltItmFocusLost

    private void txtCodAltItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltItmActionPerformed
        txtCodAltItm.transferFocus();
    }//GEN-LAST:event_txtCodAltItmActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (objTblMod.isDataModelChanged())
        {
            if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) 
            {
                guardar();
            }
        }
        else 
            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
    }//GEN-LAST:event_butGuaActionPerformed

    private void txtNumCotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCotFocusGained
        txtNumCot.selectAll();
    }//GEN-LAST:event_txtNumCotFocusGained

    private void txtNumCotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCotFocusLost
        if (txtNumCot.getText().length() > 0)
        {
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNumCotFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
    
    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) 
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return JOptionPane.showConfirmDialog(this, strMsg, strTit, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función determina si los campos son válidos.
     *
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() 
    {
        int intNumFilTblBodOri, i, j;
        
        intNumFilTblBodOri=objTblModBod.getRowCountTrue();
        i=0;
        for (j=0; j<intNumFilTblBodOri; j++)
        {
            if (objTblModBod.isChecked(j, INT_TBL_CHKBOD))
            {
                i++;
                break;
            }
        }
        if (i==0)
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar obligatoriamente una bodega.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            tblBod.requestFocus();
            //tabFrm.setSelectedIndex(0);
            return false;
        }
        
        return true;
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

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgpFilOPt1;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkItmCanPenBod;
    private javax.swing.JCheckBox chkItmCanPenDes;
    private javax.swing.JLabel lblDesItm;
    private javax.swing.JLabel lblHasItm;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblItmTer;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panItmDesHas;
    private javax.swing.JPanel panItmTer;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panReg;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnFil;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodAlt2;
    private javax.swing.JTextField txtCodAltItm;
    private javax.swing.JTextField txtCodAltItmDes;
    private javax.swing.JTextField txtCodAltItmHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNumCot;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="/* Ingresos: Solicitudes de Transferencia de Inventario */">
    private boolean configurarTblDat()
    {
        boolean blnRes = false;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();    //Almacena las cabeceras        
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_NUMCOTVEN,"Núm.Cot.Ven.");
            vecCab.add(INT_TBL_DAT_CODEMPSOLTRA,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCSOLTRA,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCSOLTRA,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCSOLTRA,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCSOLTRA,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCSOLTRA,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCSOLTRA,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_BUTCONSOLTRA,"...");
            vecCab.add(INT_TBL_DAT_NOMLOCMOVINV,"Local");
            vecCab.add(INT_TBL_DAT_CODEMPMOVINV,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOCMOVINV,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOCMOVINV,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOCMOVINV,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOCMOVINV,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOCMOVINV,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_MOTTRAMOVINV,"Mot.Tra.");
            vecCab.add(INT_TBL_DAT_FECDOCMOVINV,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODBODORGMOVINV,"Cód.Bod.Ori.");
            vecCab.add(INT_TBL_DAT_NOMBODORGMOVINV,"Bod.Ori.");
            vecCab.add(INT_TBL_DAT_CODBODDESMOVINV,"Cód.Bod.Des.");
            vecCab.add(INT_TBL_DAT_NOMBODDESMOVINV,"Bod.Des.");
            vecCab.add(INT_TBL_DAT_CODREGMOVINV,"Cód.Reg.");
            vecCab.add(INT_TBL_DAT_CODITM,"Cód.Itm.");        
            vecCab.add(INT_TBL_DAT_CODALTITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_CODALTDOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_NOMITM,"Nom.Itm.");
            vecCab.add(INT_TBL_DAT_CANMOVINV,"Can.Tra.");
            vecCab.add(INT_TBL_DAT_UNIMED,"Uni.Med.");
            vecCab.add(INT_TBL_DAT_CANINGBOD,"Can.Ing.Bod.");
            vecCab.add(INT_TBL_DAT_CANINGDES,"Can.Ing.Des.");
            vecCab.add(INT_TBL_DAT_DIASINCONF,"Días");
            vecCab.add(INT_TBL_DAT_ESTDIA,"");
            vecCab.add(INT_TBL_DAT_OBS,"Observación");
            vecCab.add(INT_TBL_DAT_NOMCLIPRV,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_ESTCONINV,"Est.Con.Inv.");
            vecCab.add(INT_TBL_DAT_TIPMOV,"Tip.Mov.Inv.");
            vecCab.add(INT_TBL_DAT_ITMCODL,"Itm.Cod.L");
            vecCab.add(INT_TBL_DAT_TIECNF,"Tie.Cnf.?");
            vecCab.add(INT_TBL_DAT_CHKCONINVAUT,"Conf.Aut.");
            
            vecCab.add(INT_TBL_DAT_BUTCONDOCINV,"...");
            
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod); 
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
             //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION); 

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAdaTblSolTraInv objMouMotAda2=new ZafMouMotAdaTblSolTraInv();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda2);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel(); 
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Establecer el check en columnas
            tcmAux.getColumn(INT_TBL_DAT_CHKCONINVAUT).setCellRenderer(objTblCelRenChk);
            
            //Establece tamaño.
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUMCOTVEN).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_UNIMED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CANINGBOD).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CANINGDES).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DIASINCONF).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_ESTDIA).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_OBS).setPreferredWidth(120);    
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV).setPreferredWidth(120);    
            tcmAux.getColumn(INT_TBL_DAT_ESTCONINV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TIPMOV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ITMCODL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TIECNF).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHKCONINVAUT).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setPreferredWidth(20);
            
            //Columnas Ocultas
            if (!objPerUsr.isOpcionEnabled(4136)) 
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUMCOTVEN, tblDat);
            }
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMPSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUMDOCSOLTRA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUTCONSOLTRA, tblDat);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMPMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOCMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODBODORGMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODBODDESMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODREGMOVINV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODITM, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CANINGDES, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ESTDIA, tblDat);
            
            if(objParSis.getCodigoUsuario()!= 1)
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ESTCONINV, tblDat);
            }
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIPMOV, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_ITMCODL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIECNF, tblDat);
            
            if (!objPerUsr.isOpcionEnabled(4136)) 
            {
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHKCONINVAUT, tblDat);
            }

            //Agrupamiento de Columnas.
            ZafTblHeaGrp objTblHeaGrpSolTra=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpSolTra.setHeight(16*2);

            ZafTblHeaColGrp objTblHeaColGrpSolTra=new ZafTblHeaColGrp(" SOLICITUDES DE TRANSFERENCIAS ");
            objTblHeaColGrpSolTra.setHeight(16);
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA));
            objTblHeaColGrpSolTra.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA));

            ZafTblHeaColGrp objTblHeaColGrpTraInv=new ZafTblHeaColGrp(" MOVIMIENTOS DE INVENTARIO ");
            objTblHeaColGrpTraInv.setHeight(16);
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODEMPMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODLOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODORGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODBODDESMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODREGMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODALTITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CODALTDOS));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMITM));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANMOVINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_UNIMED));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANINGBOD));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CANINGDES));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_DIASINCONF));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_ESTDIA));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_OBS));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_ESTCONINV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_TIPMOV));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_ITMCODL));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_TIECNF));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_CHKCONINVAUT));
            objTblHeaColGrpTraInv.add(tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV));

            objTblHeaGrpSolTra.addColumnGroup(objTblHeaColGrpSolTra);
            objTblHeaGrpSolTra.addColumnGroup(objTblHeaColGrpTraInv);
            objTblHeaColGrpSolTra=null;
            objTblHeaColGrpTraInv=null;
            
            
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUTCONSOLTRA);
            vecAux.add("" + INT_TBL_DAT_CHKCONINVAUT);
            vecAux.add("" + INT_TBL_DAT_BUTCONDOCINV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Establecer Botón.
            objTblCelRenButConSolTraInv=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setCellRenderer(objTblCelRenButConSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellRenderer(objTblCelRenButConSolTraInv);
            
            objTblCelRenButConSolTraInv.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButConSolTraInv.getColumnRender())
                    {
                        case INT_TBL_DAT_BUTCONSOLTRA:
                            if(objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODEMPSOLTRA).toString().equals(""))
                               objTblCelRenButConSolTraInv.setText("");
                            else
                               objTblCelRenButConSolTraInv.setText("...");
                        break;
                                                    
                        case INT_TBL_DAT_BUTCONDOCINV:
                            if (objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODEMPMOVINV).toString().equals("") 
                                || objTblMod.getValueAt(objTblCelRenButConSolTraInv.getRowRender(), INT_TBL_DAT_CODBODORGMOVINV).toString().equals("0")
                                ) 
                               objTblCelRenButConSolTraInv.setText("");
                            else
                               objTblCelRenButConSolTraInv.setText("...");
                        break;
                    }
                }
            });
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTCONSOLTRA).setCellEditor(objTblCelEdiButGen);
            tcmAux.getColumn(INT_TBL_DAT_BUTCONDOCINV).setCellEditor(objTblCelEdiButGen);
           
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
                            case INT_TBL_DAT_BUTCONSOLTRA:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMPSOLTRA).toString().equals("")))
                                   objTblCelEdiButGen.setCancelarEdicion(true);
                                break;
                            case INT_TBL_DAT_BUTCONDOCINV:
                                if( (objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMPMOVINV).toString().equals(""))
                                    || objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODBODORGMOVINV).toString().equals("0")
                                   )
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
                            case INT_TBL_DAT_BUTCONSOLTRA:
                                cargarSolTraInv   (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPSOLTRA).toString(), 
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCSOLTRA).toString(),
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCSOLTRA).toString(),
                                                   tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCSOLTRA).toString()
                                                   );
                                break;
                            case INT_TBL_DAT_BUTCONDOCINV:
                                cargarVentanaMovInv(tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODEMPMOVINV).toString(), 
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODLOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOCMOVINV).toString(),
                                                    tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODDOCMOVINV).toString()
                                                    );
                                break;

                        }
                    }
                }
            });     
            
            //Configurar Jtable: Colores            
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NUMCOTVEN).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCSOLTRA).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMLOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_MOTTRAMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_FECDOCMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODBODORGMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODORGMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODBODDESMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMBODDESMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CODALTITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_NOMITM).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_UNIMED).setCellRenderer(objTblCelRenLblColSolTraInv);
            tcmAux.getColumn(INT_TBL_DAT_DIASINCONF).setCellRenderer(objTblCelRenLblColSolTraInv);            
            tcmAux.getColumn(INT_TBL_DAT_ESTDIA).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_OBS).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_NOMCLIPRV).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_ESTCONINV).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_TIPMOV).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_ITMCODL).setCellRenderer(objTblCelRenLblColSolTraInv);   
            tcmAux.getColumn(INT_TBL_DAT_TIECNF).setCellRenderer(objTblCelRenLblColSolTraInv);   
                        
            objTblCelRenLblColSolTraInv.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColSolTraInv.getRowRender();
                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColSolTraInv.setBackground(colFonCol);
                        objTblCelRenLblColSolTraInv.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColSolTraInv.setBackground(Color.WHITE);
                        objTblCelRenLblColSolTraInv.setForeground(Color.BLACK);
                    }
                }
            });
            
            
            //Formato Cantidades
            tcmAux.getColumn(INT_TBL_DAT_CANMOVINV).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            tcmAux.getColumn(INT_TBL_DAT_CANINGBOD).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            tcmAux.getColumn(INT_TBL_DAT_CANINGDES).setCellRenderer(objTblCelRenLblColSolTraInvNum);
            
            objTblCelRenLblColSolTraInvNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblColSolTraInvNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblColSolTraInvNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblColSolTraInvNum.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColSolTraInvNum.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColSolTraInvNum.setBackground(colFonCol);
                        objTblCelRenLblColSolTraInvNum.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColSolTraInvNum.setBackground(Color.WHITE);
                        objTblCelRenLblColSolTraInvNum.setForeground(Color.BLACK);
                    }
                }
            });

            
            //Formato Código Alterno 2.
            objTblCelRenLblColCenSol = new ZafTblCelRenLbl();
            objTblCelRenLblColCenSol.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_CODALTDOS).setCellRenderer(objTblCelRenLblColCenSol);
            
            objTblCelRenLblColCenSol.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColCenSol.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColCenSol.setBackground(colFonCol);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColCenSol.setBackground(Color.WHITE);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                }
            });
            
            //Formato: Check
            
            objTblCelRenChk.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    //Mostrar de color gris las columnas impares.
                    int intCell=objTblCelRenLblColCenSol.getRowRender();

                    String str=tblDat.getValueAt(intCell, INT_TBL_DAT_ESTDIA).toString();

                    if(str.equals("S"))
                    {
                        objTblCelRenLblColCenSol.setBackground(colFonCol);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                    else 
                    {
                        objTblCelRenLblColCenSol.setBackground(Color.WHITE);
                        objTblCelRenLblColCenSol.setForeground(Color.BLACK);
                    }
                }
            });
            
            //Edición de Celdas Check
            objTblCelEdiChk = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKCONINVAUT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                int intFilSel/*, intColSel*/;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    //intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_ITMCODL).toString().equals("N"))
                        {      
                            tblDat.setValueAt(true, intFilSel, INT_TBL_DAT_CHKCONINVAUT);
                            mostrarMsgInf("<HTML>Solo se pueden seleccionar ítems con Terminal L.</HTML>");
                        }
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_TIECNF).toString().equals("S"))
                        {      
                            tblDat.setValueAt(true, intFilSel, INT_TBL_DAT_CHKCONINVAUT);
                            mostrarMsgInf("<HTML>No se puede seleccionar este ítem por que ya tiene confirmacion(es) realizada(s).</HTML>");
                        }
                    }
                    
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel = tblDat.getSelectedRow();
                    //intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) 
                    {
                        if(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHKCONINVAUT).equals(false))
                        {                    
                            tblDat.setValueAt(false, intFilSel, INT_TBL_DAT_CHKCONINVAUT);
                        }
                        else 
                        {
                            tblDat.setValueAt(true, intFilSel, INT_TBL_DAT_CHKCONINVAUT);
                        }

                    }
                }
            });
            
            //Configurar JTable: Establecer el modo de operación.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
           
            
            tcmAux=null;
            blnRes = true;

        } 
        catch (Exception e) {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaTblSolTraInv extends java.awt.event.MouseMotionAdapter 
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
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código Seguimiento";
                    break;
                case INT_TBL_DAT_NUMCOTVEN:
                    strMsg="Número de Cotización de Venta.";
                    break;
                case INT_TBL_DAT_CODEMPSOLTRA:
                    strMsg="Código de la empresa de la solicitud";
                    break;
                case INT_TBL_DAT_CODLOCSOLTRA:
                    strMsg="Código del local de la solicitud";
                    break;
                case INT_TBL_DAT_CODTIPDOCSOLTRA:
                    strMsg="Código del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_CODDOCSOLTRA:
                    strMsg="Código del documento de la solicitud";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCSOLTRA:
                    strMsg="Descripción corta del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCSOLTRA:
                    strMsg="Descripción larga del tipo de documento de la solicitud";
                    break;
                case INT_TBL_DAT_NUMDOCSOLTRA:
                    strMsg="Número de Documento de la solicitud";
                    break;
                case INT_TBL_DAT_BUTCONSOLTRA:
                    strMsg="Botón Consulta Solicitud de Transferencia";
                    break;
                case INT_TBL_DAT_NOMLOCMOVINV:
                    strMsg="Local en que se almacena el documento";
                    break;
                case INT_TBL_DAT_CODEMPMOVINV:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOCMOVINV:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOCMOVINV:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_CODDOCMOVINV:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOCMOVINV:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DESLARTIPDOCMOVINV:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUMDOCMOVINV:
                    strMsg="Número de Documento";
                    break;
                case INT_TBL_DAT_MOTTRAMOVINV:
                    strMsg="Motivo de Movimiento de Inventario";
                    break;
                case INT_TBL_DAT_FECDOCMOVINV:
                    strMsg="Fecha de Documento";
                    break;    
                case INT_TBL_DAT_CODBODORGMOVINV:
                    strMsg="Código de Bodega Origen";
                    break;
                case INT_TBL_DAT_NOMBODORGMOVINV:
                    strMsg="Nombre de Bodega Origen";
                    break;
                case INT_TBL_DAT_CODBODDESMOVINV:
                    strMsg="Código de Bodega Destino";
                    break;
                case INT_TBL_DAT_NOMBODDESMOVINV:
                    strMsg="Nombre de Bodega Destino";
                    break;
                case INT_TBL_DAT_CODREGMOVINV:
                    strMsg="Código de Registro";
                    break;
                case INT_TBL_DAT_CODITM:
                    strMsg="Código del item";
                    break;
                case INT_TBL_DAT_CODALTITM:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_CODALTDOS:
                    strMsg="Código alterno 2 del item";
                    break;
                case INT_TBL_DAT_NOMITM:
                    strMsg="Nombre del item";
                    break;   
                case INT_TBL_DAT_CANMOVINV:
                    strMsg="Cantidad de Transferencia";
                    break;
                case INT_TBL_DAT_UNIMED:
                    strMsg="Unidad de medida";
                    break;
                case INT_TBL_DAT_CANINGBOD:
                    strMsg="Cantidad por Ingresar a Bodega";
                    break;
                case INT_TBL_DAT_CANINGDES:
                    strMsg="Cantidad por Ingresar a Despacho";
                    break;
                case INT_TBL_DAT_DIASINCONF:
                    strMsg="Días sin confirmar (Laboralmente)";
                    break;
                case INT_TBL_DAT_ESTDIA:
                    strMsg="Estado";
                    break;
                case INT_TBL_DAT_OBS:
                    strMsg="Observación";
                    break;
                case INT_TBL_DAT_NOMCLIPRV:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_ESTCONINV:
                    strMsg="Estado de Confirmacion de Inventario";
                    break;
                case INT_TBL_DAT_TIPMOV:
                    strMsg="Tipo de Movimiento de Inventario";
                    break;
                case INT_TBL_DAT_ITMCODL:
                    strMsg="Indica si es Item L.";
                    break;
                case INT_TBL_DAT_TIECNF:
                    strMsg="Tie.Cnf.?";
                    break;               
                case INT_TBL_DAT_CHKCONINVAUT:
                    strMsg="Confirmación Automática?";
                    break;
                case INT_TBL_DAT_BUTCONDOCINV:
                    strMsg="Botón Consulta Documento de Inventario";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="/* Botón Movimiento de Inventario*/">
    private void cargarVentanaMovInv(String strCodEmp, String strCodLoc,  String strCodTipDoc, String strCodDoc)
    {
        int intCodEmp=Integer.valueOf(strCodEmp);
        int intCodLoc=Integer.valueOf(strCodLoc);
        int intCodTipDoc=Integer.valueOf(strCodTipDoc);
        int intCodDoc=Integer.valueOf(strCodDoc);
        
        Compras.ZafCom20.ZafCom20 obj = new Compras.ZafCom20.ZafCom20(objParSis, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc) ;
        this.getParent().add(obj, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.show();
    }
    //</editor-fold>
    
    
    private void cargarSolTraInv(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try 
        {
            Compras.ZafCom91.ZafCom91 obj1 = new Compras.ZafCom91.ZafCom91(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
            this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
            obj1.show();
        } 
        catch (Exception Evt) {        objUti.mostrarMsgErr_F1(this, Evt);     }
    }
    
    
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="/* Bodegas */">
    private boolean configurarTblBod()
    {
        boolean blnRes = false;

        Vector vecCab = new Vector();    //Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_LIN, "");
        vecCab.add(INT_TBL_CHKBOD, "");
        vecCab.add(INT_TBL_CODBOD, "Cód.Bod.");
        vecCab.add(INT_TBL_NOMBOD, "Bodega");

        objTblModBod = new ZafTblMod();
        objTblModBod.setHeader(vecCab);
        tblBod.setModel(objTblModBod);

        tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel tcmAux = tblBod.getColumnModel();
        tblBod.getTableHeader().setReorderingAllowed(false);
        
        //Tamaño de las celdas
        tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
        tcmAux.getColumn(INT_TBL_CHKBOD).setPreferredWidth(30);
        tcmAux.getColumn(INT_TBL_CODBOD).setPreferredWidth(60);
        tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(380);

        //Configurar JTable: Establecer columnas editables.
        Vector vecAux = new Vector();
        vecAux.add("" + INT_TBL_CHKBOD);
        objTblModBod.setColumnasEditables(vecAux);
        vecAux = null;
        
        //Configurar JTable: Establecer la fila de cabecera.
        objTblFilCab=new ZafTblFilCab(tblBod);
        tcmAux.getColumn(INT_TBL_LIN).setCellRenderer(objTblFilCab);
        
        //Configurar JTable: Editor de la tabla.
        ZafTblEdi objZafTblEdi = new ZafTblEdi(tblBod);

        objTblCelRenChkBod = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellRenderer(objTblCelRenChkBod);
        objTblCelRenChkBod = null;

        objTblCelEdiChkBod = new ZafTblCelEdiChk();
        tcmAux.getColumn(INT_TBL_CHKBOD).setCellEditor(objTblCelEdiChkBod);
        objTblCelEdiChkBod.addTableEditorListener(new ZafTableAdapter() 
        {
            public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
            {
            }
            public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
            {
            }
        });

        //Configurar JTable: Establecer los listener para el TableHeader.
        tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt) 
            {
                tblDatMouseClickedBod(evt);
            }
        });

        objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);

        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes = true;
        String strSQL="";
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
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Item");
            arlAli.add("Uni.Med.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("60");
            arlAncCol.add("300");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL+=" SELECT a.co_itm, a.tx_codAlt, a.tx_codAlt2, a.tx_nomItm, a1.tx_desCor ";
            strSQL+=" FROM tbm_inv as a LEFT JOIN tbm_var as a1 ON (a1.co_reg=a.co_uni) ";
            strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a.st_reg NOT IN ('U','T') ";  
            strSQL+=" ORDER BY a.tx_codalt ";
            //System.out.println("configurarVenConItm: "+strSQL);
            
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e) {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus) 
    {
        boolean blnRes = true;
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
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
                        txtCodAlt2.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAltItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
                            txtCodAlt2.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAltItm.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Codigo alterno 2".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodAlt2.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                        txtCodAltItm.setText(vcoItm.getValueAt(2));
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
                            txtCodAltItm.setText(vcoItm.getValueAt(2));
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
        catch (Exception e)  {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    public boolean cargarBod()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL=getQueryBodegas(intSqlBod, blnVenConEme);
                //strBod=strSQL;
                rst=stm.executeQuery(strSQL);
                
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_CHKBOD,true);
                    vecReg.add(INT_TBL_CODBOD, rst.getString("co_bod") );
                    vecReg.add(INT_TBL_NOMBOD, rst.getString("tx_nom") );
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
                //blnMarTodTblBod=false;
            }
        }
        catch (java.sql.SQLException e)  {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e) {   blnRes=false;    objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    
    private void tblDatMouseClickedBod(java.awt.event.MouseEvent evt) 
    {
        int i, intNumFil;
        try 
        {
            intNumFil = tblBod.getRowCount();
            
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 1 && tblBod.columnAtPoint(evt.getPoint()) == INT_TBL_CHKBOD) 
            {
                if (blnMarTodTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        tblBod.setValueAt(new Boolean(false), i, INT_TBL_CHKBOD);
                    }
                    blnMarTodTblBod = false;
                }
                else 
                {
                    //Ocultar todas las columnas.
                    for (i = 0; i < intNumFil; i++) 
                    {
                        tblBod.setValueAt(new Boolean(true), i, INT_TBL_CHKBOD);
                    }
                    blnMarTodTblBod = true;
                }
            }
        }
        catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
    }
    //</editor-fold>
    
    
    private void consultar()
    {
        if (butCon.getText().equals("Consultar")) 
        {
            blnCon = true;
            if (objThrGUI == null) 
            {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
            else{
                objThrGUI.stop();
                objThrGUI=null;
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else 
        {
            blnCon = false;
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
        @Override
        public void run()
        {
            if (!cargarDetReg(sqlConFil()))
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
    
    
    /* Filtro Consulta*/
    public String sqlConFil() 
    {
        String sqlFil="";
        intSqlBod=0;
        blnCotVen=false;
        
        String strAuxBod="";
        for (int j = 0; j < tblBod.getRowCount(); j++) 
        {
            if (tblBod.getValueAt(j, INT_TBL_CHKBOD) != null) 
            {
              if (tblBod.getValueAt(j, INT_TBL_CHKBOD).toString().equals("true")) 
              {
                if(strAuxBod.equals("")) strAuxBod +=  tblBod.getValueAt(j, INT_TBL_CODBOD).toString();
                else strAuxBod += ","+tblBod.getValueAt(j, INT_TBL_CODBOD).toString();
              } 
            }
        }
        
        if(!blnVenConEme)
        {
            sqlFil+=" AND ( b.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND b.co_bodGrp IN ( "+strAuxBod+" ) )";
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Fecha del documento. */">
            if(objSelFec.isCheckBoxChecked())
            {
                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Búsqueda por rangos
                        sqlFil += " AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        sqlFil += " AND a1.fe_doc <='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        sqlFil += " AND a1.fe_doc >='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        //sqlFil+= " AND a1.fe_Doc >='2016-10-11'";
                        break;
                    case 3: //Todo.
                        break;
                }
            }
            //</editor-fold>
            
            if (optFil.isSelected()) 
            {
                //Búsqueda por ítem.
                if (txtCodItm.getText().length()>0)
                    sqlFil+=" AND a4.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_itm=" + txtCodItm.getText() +")";

                if (txtCodAltItmDes.getText().length()>0 || txtCodAltItmHas.getText().length()>0)
                    sqlFil+=" AND ((LOWER(a2.tx_codAlt) BETWEEN '" + txtCodAltItmDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a2.tx_codAlt) LIKE '" + txtCodAltItmHas.getText().replaceAll("'", "''").toLowerCase() + "%')";

                //Items Terminan Con.
                if (txtCodAltItmTer.getText().length()>0) {
                    sqlFil+=getConSQLAdiCamTer("a2.tx_codAlt",txtCodAltItmTer.getText().trim());
                }
            }
                    
            //Cantidades Pendientes de Bodega/Despacho
            if(!(chkItmCanPenBod.isSelected() && chkItmCanPenDes.isSelected()))
            {
                if(chkItmCanPenBod.isSelected())
                    sqlFil+=" AND a1.tx_tipMov NOT IN ('I','V') ";
                else if(chkItmCanPenDes.isSelected())
                    sqlFil+=" AND a1.tx_tipMov IN ('I','V') ";
            }
            
            //<editor-fold defaultstate=""collapsed" desc="/* Filtro: Número Cotización. */">
            sqlFilCot="";
            if (txtNumCot.getText().trim().length()>0 )
            {
                blnCotVen=true;
                sqlFilCot=" and a.co_Cot = "+txtNumCot.getText().trim().replaceAll("'", "");
            }
            //</editor-fold>
            
        }
        else
        {
            sqlFil+=" AND ( b.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+" AND b.co_bodGrp IN ( "+getQueryBodegas(intSqlBod, blnVenConEme)+" ) )";
        }
         
        System.out.println("Filtro: "+sqlFil);
        return sqlFil;
    }
    
    public String getQueryItemsPendientes(String strFil) 
    {
        //Armar la sentencia SQL.
        strSQL =""; 
        strSQL+=" SELECT Seg.co_Seg, z.* , Cot.co_cot, CASE WHEN Conf.co_emp IS NULL THEN 'N' ELSE 'S' END as isConf \n";
        strSQL+=" FROM  \n";
        strSQL+=" ( \n";
        strSQL+="   SELECT y.*, CASE WHEN diasPen <=1 then 'S' else 'N' end as estDia, case when diasPen <0 then 'S' else 'N' end as bloqueo   \n";
        strSQL+="             , CASE WHEN (y.tx_CodAlt NOT LIKE '%L' AND y.st_merIngEgrFisBod='S') THEN 'N' ELSE 'S' END as isItemL \n";
        strSQL+="   FROM    \n";
        strSQL+="   (       \n";
        strSQL+="       SELECT x.*, ( x.ne_nummaxdiaconing - x.diasLab) as diasPen  \n";
        strSQL+="       FROM     \n";
        strSQL+="       (        \n";
        strSQL+="            SELECT loc.tx_nom, a1.tx_tipMov,  a2.st_merIngEgrFisBod, CASE WHEN a1.tx_tipMov IN ('I','V') THEN 'VENTA' ELSE 'REPOSICIÓN' END as MotTra,  \n";
        strSQL+="                   a1.co_emp as CodEmpMovInv, a1.co_Loc as CodLocMovInv, a1.co_tipDoc as CodTipDocMovInv, a1.co_doc as CodDocMovInv,\n";
        strSQL+="                   t.tx_desCor as DesCorMovInv, t.tx_desLar as DesLarMovInv, a1.ne_numDoc as NumDocMovInv, a1.fe_Doc as FecDocMovInv,\n";
        strSQL+="                   CASE WHEN Sol.CodBodOrg IS NULL THEN 0 ELSE Sol.CodBodOrg END as CodBodOrg, Sol.NomBodOrg, \n";
        strSQL+="  		    b.co_bodGrp as CodBodGrpDes, b1.tx_nom as NomBodDes, \n";
        strSQL+="  		    a2.co_reg as CodRegMovInv,  a2.co_itm, a2.tx_CodAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.nd_Can,  a2.tx_uniMed, \n";
        strSQL+="  		    CASE WHEN a2.nd_canIngBod IS NULL THEN 0 ELSE a2.nd_canIngBod END as CanPenIngBod,  \n";
        strSQL+="  		    CASE WHEN a1.st_conInv IS NULL THEN 'P' ELSE a1.st_conInv END as EstConInv,	\n";
        strSQL+="  		    Sol.CodEmpSol, Sol.CodLocSol, Sol.CodTipDocSol,  Sol.CodDocSol, Sol.DesCorSol, Sol.DesLarSol, Sol.NumDocSol, \n";
        strSQL+="  		    CAST (Sol.DesCorSol ||' - '|| Sol.NumDocSol as character varying ) as tx_obs, \n";
        strSQL+="  		    CASE WHEN Sol.CodBodOrg IS NULL THEN a1.tx_nomCli ELSE '' END as NomCliPrv, \n";
        strSQL+="  		    (current_date - a1.fe_Doc) as diasSinConf,  \n";
        strSQL+="                  (select ne_nummaxdiaconing from tbm_loc where tbm_loc.co_emp="+objParSis.getCodigoEmpresa()+" and tbm_loc.co_loc="+objParSis.getCodigoLocal()+") AS ne_nummaxdiaconing,     \n";
        strSQL+="                  (select cast(count(fe_dia)as smallint) from tbm_calciu AS cal inner join tbm_loc as loc on(loc.co_ciu = cal.co_ciu) \n";
        strSQL+="                  where loc.co_emp="+objParSis.getCodigoEmpresa()+" and loc.co_loc="+objParSis.getCodigoLocal()+" and fe_dia > a1.fe_doc and fe_dia <= current_date and tx_tipdia = 'L' ) AS diasLab \n";
        strSQL+="            FROM tbm_cabMovInv as a1  \n";	    
        strSQL+="            INNER JOIN tbm_detMovInv as a2 ON (a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_Doc=a1.co_doc ) \n";
        strSQL+="            INNER JOIN tbm_loc as loc ON (loc.co_emp=a1.co_emp AND loc.co_loc=a1.co_loc) \n";
        strSQL+="  	     INNER JOIN tbm_inv as inv ON (inv.co_Emp=a1.co_Emp AND inv.co_itm=a2.co_itm) \n";   
        strSQL+="  	     INNER JOIN tbr_bodEmpBodGrp AS b ON (b.co_emp=a1.co_emp AND b.co_bod=a2.co_bod) \n";
        strSQL+="  	     INNER JOIN tbm_bod as b1 ON (b1.co_emp=b.co_empGrp AND b1.co_bod=b.co_bodGrp) \n";  
        strSQL+="  	     INNER JOIN tbm_cabTipDoc as t ON (t.co_emp=a1.co_emp AND t.co_loc=a1.co_loc AND t.co_tipDoc=a1.co_tipDoc) \n";
        strSQL+="            LEFT OUTER JOIN \n";
        strSQL+="            ( \n";
        strSQL+=" 		select a.co_emp as CodEmpSol, a.co_loc as CodLocSol, a.co_tipDoc as CodTipDocSol, a.co_doc as CodDocSol,\n"; 
        strSQL+=" 		       t1.tx_DesCor as DesCorSol, t1.tx_DesLar as DesLarSol, a.ne_numDoc as NumDocSol, \n";
        strSQL+=" 		       a.co_bodOrg as CodBodOrg, b1.tx_nom as NomBodOrg, a.co_bodDes \n";
        strSQL+=" 	        from tbm_cabSolTraInv as a \n";
        strSQL+=" 		inner join tbm_cabTipDoc as t1 on (t1.co_emp=a.co_emp AND t1.co_loc=a.co_loc AND t1.co_tipDoc=a.co_tipDoc) \n";
        strSQL+=" 		inner join tbm_bod as b1 on (b1.co_Emp=a.co_emp AND b1.co_bod=a.co_bodOrg)\n";
        strSQL+=" 		where a.st_reg='A' \n";
        strSQL+="            ) as Sol ON (Sol.CodEmpSol=a1.co_empRelCabSolTraInv AND Sol.CodLocSol=a1.co_locRelCabSolTraInv AND Sol.CodTipDocSol=a1.co_tipDocRelCabSolTraInv AND Sol.CodDocSol=a1.co_docRelCabSolTraInv) \n";
        strSQL+="  	    INNER JOIN tbm_equinv as a4 ON (a4.co_emp=a2.co_emp and a4.co_itm=a2.co_itm ) \n";
        strSQL+="  	    WHERE a1.st_Reg='A' AND a1.tx_tipMov IS NOT NULL  AND a2.nd_Can>0 \n";
        strSQL+="  	    AND a2.st_merIngEgrFisBod in ('S') \n";//Rose Códigos L.
        strSQL+="           "+strFil+"\n"; 
        strSQL+="  	    GROUP BY loc.tx_nom, a1.tx_tipMov,  a2.st_merIngEgrFisBod, t.tx_desCor, t.tx_desLar, a1.fe_Doc, a1.co_emp, a1.co_Loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, \n";	 
        strSQL+="  	             Sol.CodBodOrg, Sol.NomBodOrg, b.co_bodGrp, b1.tx_nom, a2.co_reg,  a2.co_itm, a2.tx_CodAlt, a2.tx_codAlt2, a2.tx_nomItm, a2.nd_Can, a2.tx_uniMed, a2.nd_canIngBod, a1.st_conInv, \n";
        strSQL+="  	             Sol.CodEmpSol, Sol.CodLocSol, Sol.CodTipDocSol, Sol.CodDocSol, Sol.DesCorSol, Sol.DesLarSol, Sol.NumDocSol, a1.tx_nomCli \n";	             
        strSQL+="      ) as x  WHERE  x.CodBodOrg != x.CodBodGrpDes AND (x.CanPenIngBod>0 ) AND x.EstConInv  not in ('F')  \n";
        strSQL+="    ) as y \n";
        strSQL+=" ) as z \n";
        strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as Seg  \n";
        strSQL+=" ON (z.CodEmpMovInv=Seg.co_empRelCabMovInv AND z.CodLocMovInv=Seg.co_locRelCabMovInv AND z.CodTipDocMovInv=Seg.co_tipDocRelCabMovInv AND z.CodDocMovInv=Seg.co_docRelCabMovInv)  \n";
        if(blnCotVen)
            strSQL+=" INNER JOIN \n";
        else
            strSQL+=" LEFT OUTER JOIN \n";
        strSQL+=" ( \n";
        strSQL+="   select a2.co_seg, a.co_emp, a.co_loc, a.co_cot \n";
        strSQL+="   from tbm_cabCotVen as a \n";
        strSQL+="   inner join tbm_cabSegMovInv as a2 ON (a.co_emp=a2.co_empRelCabCotVen AND a.co_loc=a2.co_locRelCabCotVen AND a.co_cot=a2.co_cotRelCabCotVen) \n";
        strSQL+="   where a2.co_empRelCabCotVen IS NOT NULL \n";
        strSQL+="   "+sqlFilCot+"\n";
        strSQL+=" ) as Cot ON (Cot.co_seg=Seg.co_seg) \n";
        strSQL+=" LEFT OUTER JOIN \n";
        strSQL+=" ( \n";
        strSQL+="    SELECT a.co_emp, a.co_locRel, a.co_TipDocRel, a.co_DocRel, a.co_regRel  \n";
        strSQL+="    FROM tbm_detIngEgrMerBod as a \n";
        strSQL+="    INNER JOIN tbm_cabIngEgrMerBod as a1  \n";
        strSQL+="    ON (a1.co_Emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_Doc=a.co_doc) \n";
        strSQL+="    WHERE a1.st_Reg='A' \n";
        strSQL+="    GROUP BY a.co_emp, a.co_locRel, a.co_TipDocRel, a.co_DocRel, a.co_regRel  \n";
        strSQL+=" )as Conf ON (Conf.co_emp=z.CodEmpMovInv  AND Conf.co_locRel=z.CodLocMovInv AND Conf.co_tipDocRel=z.CodTipDocMovInv AND Conf.co_DocRel=z.CodDocMovInv AND Conf.co_regRel=z.CodRegMovInv ) \n";
 
        return strSQL;
    }
    
    public String getQueryBodegas(int intSqlBod, boolean blnVenConEme) 
    {
        strSQL="";
        //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
        if (objParSis.getCodigoUsuario()==1)
        {
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a2.co_bod ";
            if(intSqlBod==1)
               strSQL+=" , a2.tx_nom, a2.st_reg ";
            strSQL+=" FROM tbm_emp AS a1 "; 
            strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp) ";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo()+" AND a2.st_reg='A' ";
            strSQL+=" ORDER BY a1.co_emp, a2.co_bod ";
        }
        else
        {
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_bodGrp as co_bod ";
                if(intSqlBod==1)
                   strSQL+=" , a2.tx_nom ";
                strSQL+=" FROM tbr_bodlocprgusr as a ";
                strSQL+=" INNER JOIN tbr_bodEmpBodGrp as a1 ON (a.co_emp=a1.co_emp AND a.co_bod=a1.co_bod) ";
                strSQL+=" INNER JOIN tbm_bod as a2 ON (a1.co_empGrp=a2.co_emp AND a1.co_bodGrp=a2.co_bod) ";
                strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                if(!blnVenConEme)
                    strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu(); 
                else
                    strSQL+=" AND a.co_mnu=3326 "; 
                strSQL+=" AND a.co_usr="+ objParSis.getCodigoUsuario();
                strSQL+=" AND a.tx_natBod in ('I', 'A')  ";
                strSQL+=" ORDER BY a1.co_bodGrp ";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a.co_bod ";
                if(intSqlBod==1)
                    strSQL+=" , a2.tx_nom ";
                strSQL+=" FROM tbr_bodlocprgusr as a  ";
                strSQL+=" INNER JOIN tbm_bod as a2 ON (a.co_Emp=a2.co_emp and a.co_bod=a2.co_bod) ";
                strSQL+=" WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a.co_loc=" + objParSis.getCodigoLocal();
                if(!blnVenConEme)
                    strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu(); 
                else
                    strSQL+=" AND a.co_mnu=3326 "; 
                strSQL+=" AND a.co_usr="+ objParSis.getCodigoUsuario();
                strSQL+=" AND a.tx_natBod in ('I', 'A') ";
                strSQL+=" ORDER BY a.co_bod ";
            }
        }
        
        return strSQL;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(String strFil)
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                stm = con.createStatement();      
                //Armar la sentencia SQL.
                strSQL = getQueryItemsPendientes(strFil) ;
                strSQL+=" ORDER BY z.FecDocMovInv, z.CodEmpMovInv, z.CodLocMovInv, z.CodTipDocMovInv, z.CodDocMovInv, z.NumDocMovInv, z.CodRegMovInv \n";
        
                System.out.println("ZafCom79.cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat = new Vector();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN," ");
                        vecReg.add(INT_TBL_DAT_CODSEG, rst.getString("co_seg"));
                        vecReg.add(INT_TBL_DAT_NUMCOTVEN, rst.getString("co_cot")==null?"":rst.getString("co_cot"));
                        vecReg.add(INT_TBL_DAT_CODEMPSOLTRA, rst.getString("CodEmpSol")==null?"":rst.getString("CodEmpSol")  );
                        vecReg.add(INT_TBL_DAT_CODLOCSOLTRA, rst.getString("CodLocSol")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCSOLTRA, rst.getString("CodTipDocSol")  );
                        vecReg.add(INT_TBL_DAT_CODDOCSOLTRA, rst.getString("CodDocSol")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCSOLTRA, rst.getString("DesCorSol")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCSOLTRA, rst.getString("DesLarSol")  );
                        vecReg.add(INT_TBL_DAT_NUMDOCSOLTRA, rst.getString("NumDocSol")  );
                        vecReg.add(INT_TBL_DAT_BUTCONSOLTRA, "" );  
                        vecReg.add(INT_TBL_DAT_NOMLOCMOVINV, rst.getString("tx_nom") );
                        vecReg.add(INT_TBL_DAT_CODEMPMOVINV, rst.getString("CodEmpMovInv")==null?"":rst.getString("CodEmpMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODLOCMOVINV, rst.getString("CodLocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODTIPDOCMOVINV, rst.getString("CodTipDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODDOCMOVINV, rst.getString("CodDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOCMOVINV, rst.getString("DesCorMovInv")  );
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOCMOVINV, rst.getString("DesLarMovInv")  );
                        vecReg.add(INT_TBL_DAT_NUMDOCMOVINV, rst.getString("NumDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_MOTTRAMOVINV, rst.getString("MotTra")  );
                        vecReg.add(INT_TBL_DAT_FECDOCMOVINV, rst.getString("FecDocMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODBODORGMOVINV, rst.getString("CodBodOrg")  );
                        vecReg.add(INT_TBL_DAT_NOMBODORGMOVINV,rst.getString("NomBodOrg")  );
                        vecReg.add(INT_TBL_DAT_CODBODDESMOVINV, rst.getString("CodBodGrpDes")  );
                        vecReg.add(INT_TBL_DAT_NOMBODDESMOVINV,rst.getString("NomBodDes")  );
                        vecReg.add(INT_TBL_DAT_CODREGMOVINV,  rst.getString("CodRegMovInv")  );
                        vecReg.add(INT_TBL_DAT_CODITM, rst.getString("co_itm")  );
                        vecReg.add(INT_TBL_DAT_CODALTITM, rst.getString("tx_CodAlt")  );
                        vecReg.add(INT_TBL_DAT_CODALTDOS, rst.getString("tx_codAlt2")==null?"":rst.getString("tx_codAlt2").equals("null")?"":rst.getString("tx_codAlt2") );
                        vecReg.add(INT_TBL_DAT_NOMITM, rst.getString("tx_nomItm")  );
                        vecReg.add(INT_TBL_DAT_CANMOVINV, rst.getString("nd_Can")  );
                        vecReg.add(INT_TBL_DAT_UNIMED, rst.getString("tx_uniMed")  );
                        vecReg.add(INT_TBL_DAT_CANINGBOD, rst.getString("CanPenIngBod")  );
                        vecReg.add(INT_TBL_DAT_CANINGDES, rst.getString("CanPenIngBod")  );
                        //vecReg.add(INT_TBL_DAT_DIASINCONF, rst.getString("diasSinConf")  );
                        vecReg.add(INT_TBL_DAT_DIASINCONF, rst.getString("diasLab")  ); // Dias Laborales sin Confirmar
                        vecReg.add(INT_TBL_DAT_ESTDIA, rst.getString("estDia")  );
                        vecReg.add(INT_TBL_DAT_OBS, rst.getString("tx_obs")  );
                        vecReg.add(INT_TBL_DAT_NOMCLIPRV, rst.getString("NomCliPrv")  );
                        vecReg.add(INT_TBL_DAT_ESTCONINV, rst.getString("EstConInv")  );
                        vecReg.add(INT_TBL_DAT_TIPMOV, rst.getString("tx_tipMov")  );
                        vecReg.add(INT_TBL_DAT_ITMCODL, rst.getString("isItemL")  );
                        vecReg.add(INT_TBL_DAT_TIECNF, rst.getString("isConf")  );
                        vecReg.add(INT_TBL_DAT_CHKCONINVAUT, "" );//Boolean.FALSE
                        vecReg.add(INT_TBL_DAT_BUTCONDOCINV, "" );
                        // TERMINAL 
                        vecDat.add(vecReg);
                        
                        if (rst.getString("bloqueo").equalsIgnoreCase("S")) 
                        {                            
                            blnBloqueo=true;
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
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean obtieneHostImpresionOD()
    {
        boolean blnRes=true;
        
        try
        {
            objCfgSer = new ZafCfgSer(objParSis);
            objCfgSer.cargaDatosIpHostServicios(objParSis.getCodigoEmpresaGrupo(),16);  
            strIpImp=objCfgSer.getIpHost();
        }
        catch (Exception e) 
        {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que retorna Codigos de Seguimientos que se están procesando al momento de guardar.
     * @return 
     */
    private boolean hsObtieneCodSeg()
    {
        int intCodSeg=0;
        boolean blnRes=false;
        hsCodSeg=new HashSet();
        for (int i = 0; i < tblDat.getRowCount(); i++) 
        {
            if (tblDat.getValueAt(i, INT_TBL_DAT_CHKCONINVAUT).toString().compareTo("true") == 0) 
            {
                intCodSeg = Integer.parseInt(tblDat.getValueAt(i, INT_TBL_DAT_CODSEG).toString());
                hsCodSeg.add(intCodSeg);
                blnRes=true;
            }
        }
        return blnRes;
    }
  
    
    /**
     * Función que se encarga de realizar el proceso de guardar cambios.
     * @return true: Si se pudo realizar el proceso de guardado.
     * <BR> false: Caso contrario.
     */
    private boolean guardarDat()
    {
        boolean blnRes=false;
        Connection con=null;
        System.out.println("***guardarDat***");
        try
        {
            pgrSis.setIndeterminate(true);
            butGua.setText("Detener");
            lblMsgSis.setText("Guardando datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                if (blnGua)
                {
                    con.setAutoCommit(false);
                    if(actualizarTbmDetMovInv(con)){
                        if(actualizaTbmCabMovInv(con)){
                            if(actualizarTbmInvBod(con)){
                                if(hsObtieneCodSeg()){
                                    if(generarFacturaAut(con)){
                                        if(existeConfirmacionIngreso(con)){
                                            con.commit();
                                            blnRes = true;
                                        }else{con.rollback();}
                                    }else{
                                        con.rollback();
                                        mostrarMsgErr("Ocurrió un error al intentar generar la factura");
                                    }
                                }else {con.rollback();}
                            }else{
                                con.rollback();
                                mostrarMsgErr("Ocurrió un error al actualizar el inventario");
                            }
                        }else{con.rollback();}
                    }else{con.rollback();}
               }
            }
            con.close();
            con = null;

            if(blnRes)
            {
                blnCon = true;
                if (!cargarDetReg(sqlConFil()))
                {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    butCon.setText("Consultar");
                }
                procesaOrdDesGuiRem();
                butGua.setText("Guardar");
                pgrSis.setIndeterminate(false);
            }
            
        } 
        catch (java.sql.SQLException e)  {    blnRes = false;    objUti.mostrarMsgErr_F1(this, e); }
        catch (Exception e) {    blnRes = false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
    

     /**
     * Esta función permite actualizar el detalle de un registro.
     * @return true: Si se pudo actualizar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmDetMovInv(java.sql.Connection conn) //FACCOM
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        System.out.println("***actualizarTbmDetMovInv***");
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSQL="";
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    if (tblDat.getValueAt(i, INT_TBL_DAT_CHKCONINVAUT).toString().compareTo("true") == 0) 
                    {
                        //Armar la sentencia SQL.
                        strSQL+=" UPDATE tbm_detMovInv SET ";
                        strSQL+=" st_merIngEgrFisBod='A', tx_obs1='Conf.Aut. - "+objParSis.getNombreUsuario()+"', nd_CanPen=NULL, nd_canIngBod=NULL";
                        strSQL+=" , nd_canEgrBod=NULL, nd_canTra=NULL";
                        strSQL+=" WHERE co_Emp=" + tblDat.getValueAt(i, INT_TBL_DAT_CODEMPMOVINV).toString(); //co_emp
                        strSQL+=" AND co_loc=" + tblDat.getValueAt(i, INT_TBL_DAT_CODLOCMOVINV).toString(); //co_loc
                        strSQL+=" AND co_TipDoc=" + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOCMOVINV).toString(); //co_tipdoc
                        strSQL+=" AND co_Doc=" +tblDat.getValueAt(i, INT_TBL_DAT_CODDOCMOVINV).toString(); //co_doc
                        strSQL+=" AND co_reg=" + tblDat.getValueAt(i, INT_TBL_DAT_CODREGMOVINV).toString(); //co_reg
                        strSQL+=";";
                        
                        blnRes=true;
                    }
                }
                System.out.println("actualizarTbmDetMovInv: "+strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){      blnRes=false;      objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite actualizar el st_conInv='F' a la OC cuando no existan ítems pendientes de ingresar.
     * @return true: Si se pudo actualizar el estado.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizaTbmCabMovInv(java.sql.Connection conn) //FACCOM
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        System.out.println("***actualizaTbmCabMovInv***");
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSQL="";
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    if (tblDat.getValueAt(i, INT_TBL_DAT_CHKCONINVAUT).toString().compareTo("true") == 0) 
                    {
                        //Armar la sentencia SQL.
                        strSQL+=" UPDATE tbm_cabMovInv SET ";
                        strSQL+=" st_conInv='F'";               
                        strSQL+=" FROM \n";
                        strSQL+=" (    \n";
                        strSQL+="   SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc \n";
                        strSQL+="   FROM tbm_cabMovInv AS a \n";
                        strSQL+="   INNER JOIN  tbm_detMovInv as a1 ON (a.co_emp=a1.co_emp AND a.co_loc=a1.co_loc AND a.co_tipDoc=a1.co_tipDoc AND a.co_doc=a1.co_doc) \n";
                        strSQL+="   WHERE a1.nd_can>0 AND a1.st_meringegrfisbod='A'  \n";
                        strSQL+="   AND NOT EXISTS \n";
                        strSQL+="   ( \n";
                        strSQL+="     SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc, x1.co_reg, x1.st_meringegrfisbod, \n"; 
                        strSQL+="            x2.co_emp AS co_empRel, x1.nd_can, x1.nd_canCon, x1.nd_canIngBod, \n";
                        strSQL+="            CASE WHEN x1.nd_canDesEntCli IS NULL THEN 0 ELSE x1.nd_canDesEntCli END AS nd_canDesEntCli \n";
                        strSQL+="     FROM tbm_cabMovInv AS x \n";
                        strSQL+="     INNER JOIN  \n";
                        strSQL+="     ( tbm_detMovInv AS x1 LEFT OUTER JOIN tbr_detMovInv AS x2 ON x1.co_emp=x2.co_empRel AND x1.co_loc=x2.co_locRel AND x1.co_tipDoc=x2.co_tipDocRel AND x1.co_doc=x2.co_docRel AND x1.co_reg=x2.co_regRel ) \n";
                        strSQL+="     ON x.co_emp=x1.co_emp AND x.co_loc=x1.co_loc AND x.co_tipDoc=x1.co_tipDoc AND x.co_doc=x1.co_doc \n";
                        strSQL+="     WHERE  x1.nd_can>0 AND x1.st_meringegrfisbod='S' AND (x1.nd_can - x1.nd_canCon)<>0 AND (x1.nd_canPen<>0 OR x1.nd_canPen IS NOT NULL)  \n";
                        strSQL+="     AND x.co_emp=a1.co_emp and x.co_loc=a1.co_loc and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_Doc  \n";
                        strSQL+="   ) \n";
                        strSQL+="   AND a.co_emp=" + tblDat.getValueAt(i, INT_TBL_DAT_CODEMPMOVINV).toString(); //co_emp
                        strSQL+="   AND a.co_loc=" + tblDat.getValueAt(i, INT_TBL_DAT_CODLOCMOVINV).toString(); //co_loc  
                        strSQL+="   AND a.co_tipDoc=" + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOCMOVINV).toString(); //co_tipdoc 
                        strSQL+="   AND a.co_doc=" +tblDat.getValueAt(i, INT_TBL_DAT_CODDOCMOVINV).toString(); //co_doc
                        strSQL+="   GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_doc \n";
                        strSQL+=" ) AS x \n";
                        strSQL+=" WHERE tbm_cabMovInv.co_emp=x.co_emp AND tbm_cabMovInv.co_loc=x.co_loc AND tbm_cabMovInv.co_tipDoc=x.co_tipDoc AND tbm_cabMovInv.co_doc=x.co_doc \n";
                        strSQL+=";";
                        blnRes=true;
                    }
                }
                System.out.println("actualizaTbmCabMovInv: "+strSQL);
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){      blnRes=false;      objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }    
      
    
    /**
     * Función que genera factura de forma automática en caso de que no existan documentos pendientes de ingresar.
     * @param conn
     * NO TOCAR!!!  Jm
     * @return 
     */
    private boolean generarFacturaAut(java.sql.Connection conn)
    {
        boolean blnRes=false;
        System.out.println("***generarFacturaAut***");
        try{
            if (conn!=null){
                objCnfDoc=new ZafCnfDoc(objParSis, this);
                objGenFacAut=new ZafGenFacAut(objParSis, this);
                if(hsCodSeg!=null && !hsCodSeg.isEmpty()){
                    Iterator it=hsCodSeg.iterator();
                    while(it.hasNext()){
                        int intSeg=(Integer)it.next();
                        System.out.println("*******intSeg*******: "+intSeg);
                        if(objCnfDoc.isDocIngPenCnf(conn, intSeg, "I")){  // SOLO LOS INGRESOS Jota NO TE COMPLIQUES LA VIDA ROSE!!!! 
                            if(objCnfDoc.isProCnfTotActEstCot(conn, intSeg, 4000)){
                                blnRes=true;
                                System.out.println("Paso GenerarFacturaAut!!");
                            }
                        }
                    }
                }
                objCnfDoc=null;
                objGenFacAut=null;
            }
        }
        catch (Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);           }
        return blnRes;
    }
    
    /**
     * Esta función permite actualizar tbm_invBod.
     * @return true: Si se pudo actualizar el inventario.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmInvBod(java.sql.Connection conn) 
    {
        boolean blnRes=false;
        java.sql.Statement stmLoc = null;
        System.out.println("***actualizarTbmInvBod***");
        try
        {
            if (conn!=null)
            {
                stmLoc=conn.createStatement();
                strSQL="";
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    if (tblDat.getValueAt(i, INT_TBL_DAT_CHKCONINVAUT).toString().compareTo("true") == 0) 
                    {
                        //Armar la sentencia SQL.
                        strSQL+=" UPDATE tbm_invBod SET ";
                        strSQL+=" nd_canIngBod= (CASE WHEN nd_canIngBod IS NULL THEN 0 ELSE nd_canIngBod END ) -"+ tblDat.getValueAt(i, INT_TBL_DAT_CANMOVINV).toString();
                        strSQL+=" WHERE co_Emp=" + tblDat.getValueAt(i, INT_TBL_DAT_CODEMPMOVINV).toString(); //co_emp
                        strSQL+=" AND co_bod=" + tblDat.getValueAt(i, INT_TBL_DAT_CODBODDESMOVINV).toString(); //co_loc
                        strSQL+=" AND co_itm=" + tblDat.getValueAt(i, INT_TBL_DAT_CODITM).toString(); //co_tipdoc
                        strSQL+=" ;";
                        System.out.println("actualizarTbmInvBod: "+strSQL);
                        blnRes=true;
                    }
                }
                stmLoc.executeUpdate(strSQL);
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch (java.sql.SQLException e){      blnRes=false;      objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    
    /**
     * Valida si existen confirmaciones de ingreso para el ítem que se quiere actualizar.
     * @return true: Cuando NO existen confirmaciones.
     * <BR> false: Cuando SI existen confirmaciones.
     */
    private boolean existeConfirmacionIngreso(java.sql.Connection conn) 
    {     
        boolean blnRes=true;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strMsg="";
        
        try
        {
            if (conn != null) 
            {
                for (int i = 0; i < tblDat.getRowCount(); i++) 
                {
                    if (tblDat.getValueAt(i, INT_TBL_DAT_CHKCONINVAUT).toString().compareTo("true") == 0) 
                    {
                        stmLoc = conn.createStatement();
                        strSQL ="";
                        strSQL+=" SELECT a.co_emp, a.co_locRel, a.co_TipDocRel, a.co_DocRel, a.co_regRel ";
                        strSQL+=" FROM tbm_detIngEgrMerBod as a";
                        strSQL+=" INNER JOIN tbm_cabIngEgrMerBod as a1 ";
                        strSQL+=" ON (a1.co_Emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_Doc=a.co_doc)";
                        strSQL+=" WHERE a1.st_Reg='A'  ";
                        strSQL+=" AND a.co_Emp=" + tblDat.getValueAt(i, INT_TBL_DAT_CODEMPMOVINV).toString(); //co_emp
                        strSQL+=" AND a.co_locRel=" + tblDat.getValueAt(i, INT_TBL_DAT_CODLOCMOVINV).toString(); //co_loc
                        strSQL+=" AND a.co_TipDocRel=" + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOCMOVINV).toString(); //co_tipdoc
                        strSQL+=" AND a.co_DocRel=" +tblDat.getValueAt(i, INT_TBL_DAT_CODDOCMOVINV).toString(); //co_doc
                        strSQL+=" AND a.co_regRel=" + tblDat.getValueAt(i, INT_TBL_DAT_CODREGMOVINV).toString(); //co_reg
                        strSQL+=" GROUP BY a.co_emp, a.co_locRel, a.co_TipDocRel, a.co_DocRel, a.co_regRel ";
                        System.out.println("existeConfirmacionIngreso:"+strSQL);

                        rstLoc = stmLoc.executeQuery(strSQL);

                        while (rstLoc.next()) 
                        {
                            blnRes=false;
                            strMsg+=" <tr><td>" +  tblDat.getValueAt(i, INT_TBL_DAT_DESCORTIPDOCMOVINV).toString()+"# "+tblDat.getValueAt(i, INT_TBL_DAT_NUMDOCMOVINV).toString()+"</td>" ;
                            strMsg+="  <td>"+ tblDat.getValueAt(i, INT_TBL_DAT_CODALTITM).toString()+" </td></tr>";
                        }

                    }
                }
                if(!blnRes)
                {
                    mostrarMsgInf("<HTML> Los siguientes documentos no se pueden procesar,<BR> porque tienen confirmaciones de ingreso realizadas:"+
                                  "<BR><BR> <table BORDER=1 > <tr> <td> NÚM.DOC. </td> <td> ÍTEM </td></tr> "+strMsg+" </table> </HTML>");
                }
              
            }
            rstLoc.close();
            stmLoc.close();
            rstLoc=null;
            stmLoc=null;
        }
        catch (java.sql.SQLException e) {  blnRes=false;    objUti.mostrarMsgErr_F1(this, e);       }
        catch(Exception e){    blnRes=false;     objUti.mostrarMsgErr_F1(this, e);      }
        return blnRes;
    }
    
    
    /**
     * Función que permite imprimir la Orden de Despacho, y generar la Guia de remisión.
     * @param conn Conexión a la base.
     * @return 
     */
    private boolean procesaOrdDesGuiRem()
    {
        boolean blnRes=false;
        java.sql.Connection conLoc;
        String strPryKeyFac="";
        String [] strArrPryKeyFac=new String[4];
        System.out.println("***procesaOrdDesGuiRem***");
        try
        {
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc!=null)
            {
                objGenFacAut=new ZafGenFacAut(objParSis, this);
                objGenOdPryTra=new ZafGenOdPryTra();
                objGenGuiRemPryTra=new ZafGenGuiRemPryTra();
                strSQL="";
                obtieneHostImpresionOD();
                if(hsCodSeg!=null && !hsCodSeg.isEmpty())
                {
                    Iterator it=hsCodSeg.iterator();
                    while(it.hasNext())
                    {
                        int intSeg=(Integer)it.next();
                        System.out.println("procesaOrdDesGuiRem.intSeg: "+intSeg);
                        strPryKeyFac=objGenFacAut.obtenerFacturaSeguimientoInventario(conLoc, intSeg);
                        if(strPryKeyFac.trim().length()>0)
                        {
                            strArrPryKeyFac=strPryKeyFac.split("-");
                            objGenOdPryTra.imprimirOdLocal(conLoc, Integer.parseInt(strArrPryKeyFac[0]), Integer.parseInt(strArrPryKeyFac[1]), Integer.parseInt(strArrPryKeyFac[2]), Integer.parseInt(strArrPryKeyFac[3]), strIpImp);
                            System.out.println("procesaOrdDesGuiRem.imprimirOdLocal--->: "+hsCodSeg); 
                            if(objGenOdPryTra.generarTermL(conLoc, Integer.parseInt(strArrPryKeyFac[0]), Integer.parseInt(strArrPryKeyFac[1]), Integer.parseInt(strArrPryKeyFac[2]), Integer.parseInt(strArrPryKeyFac[3])))
                            {
                                System.out.println("procesaOrdDesGuiRem.generarTermL--->: "+hsCodSeg);
                            }
                        }
                    }
                }
                blnRes=true;
                objGenGuiRemPryTra=null;
                objGenFacAut=null;
                objGenOdPryTra=null;
            }
            conLoc.close();
            conLoc = null;
        }
        catch (Exception e){       blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
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
    private class ZafThreadGUIGua extends Thread
    {
        @Override
        public void run()
        {
            if (!guardarDat())
            {
                //Inicializar objetos si no se pudo guardar los datos.
                mostrarMsgErr("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema.");
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
                butGua.setText("Guardar");
            }
            else
            {
                mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                objThrGUIGua=null;
            }
        }
    }
    
    
    private void guardar()
    {
        if (butGua.getText().equals("Guardar")) 
        {
            blnGua = true;
            if (objThrGUIGua == null) 
            {
                objThrGUIGua = new ZafThreadGUIGua();
                objThrGUIGua.start();
            }
            else
            {
                objThrGUIGua.stop();
                objThrGUIGua=null;
                objThrGUIGua = new ZafThreadGUIGua();
                objThrGUIGua.start();
            }
        }
        else 
        {
            blnGua = false;
        }
    }
  
    
    
    
}