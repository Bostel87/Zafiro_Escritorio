/*
 * ZafVen33.java
 *
 * Created on 12 de junio de 2013, 12:35 PM
 */
package Ventas.ZafVen33;
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
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Ventas.ZafVen04.ZafVen04;
import java.awt.Color;
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

public class ZafVen33 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:

    static final int INT_TBL_DAT_LIN=0;    //Linea
    static final int INT_TBL_DAT_COD_GRU=1;             //Código del grupo.
    static final int INT_TBL_DAT_NOM_GRU=2;             //Nombre del grupor.
    static final int INT_TBL_DAT_COD_ITM=3;            //codigo del item 
    static final int INT_TBL_DAT_COD_ALT=4;             //codigo alterno
    static final int INT_TBL_DAT_NOM_ITM=5;             //nombre del item
    
    
    static final int INT_TBL_DAT_NOM_DOC=/*10*/0;             //Código del vendedor.
    static final int INT_TBL_DAT_COD_VEN=/*10*/1;             //Código del vendedor.
    static final int INT_TBL_DAT_NOM_VEN=/*11*/2;            //Nombre d4el vendedor.
    static final int INT_TBL_EMP_LIN=0;                 //Línea.
    static final int INT_TBL_EMP_CHK=1;                 //Casilla de verificación.
    static final int INT_TBL_EMP_COD_EMP=2;             //Código de la empresa.
    static final int INT_TBL_EMP_NOM_EMP=3;             //Nombre de la empresa.
    //Variables
    private ZafSelFec objSelFec;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModEmp;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod; // add de ZafVen15
    private int columnaPorcentual=0;
    private int intNumDec=2;
    private int intColTot[]; 
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafVenCon vcoCli;                           //Ventana de consulta.
    private ZafVenCon vcoVen;                           //Ventana de consulta.
    private ZafVenCon vcoEmp;                           //Ventana de consulta <jota>
    private ZafVenCon vcoLoc;                           //Ventana de consulta <jota>
    private ZafVenCon vcoGru;                           //Ventana de consulta <jota>
    private ZafVenCon vcoForPag;                        //Ventana de consulta.
    private ZafVenCon vcoItm;                           //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    //ADD DE ZafVen15
    static final int INT_TBL_DAT_NUM_TOT_CDI=3;  //Número total de columnas dinámicas.
    static final int INT_TBL_DAT_CDI_STK=7;      //Columna dinámica: Stock.
    static final int INT_TBL_DAT_CDI_CAN_ING=8;  //Columna dinámica: Cantidad por ingresar a Bodega.
    static final int INT_TBL_DAT_CDI_CAN_EGR=9;  //Columna dinámica: Cantidad por egresar de Bodega.
    
    
    private Vector vecDevCountFac,vecDevSumaFac; // new
    private String strSQL, strConSQL, strAux;
    private Vector vecDatEmp, vecCabEmp;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecEstReg;
    
    ////
    private Vector vecFecha, vecCodGru,vecNomGru,vecCodItm,vecCodAltItm;
    private Vector vecCodGru1;
    private Vector vecNomItm, vecVal;
    private Vector vecAnio;
    private Vector vecTotales;
    private Vector vecTotGru;
    
    //ADD Octubre 
    private Vector vecUniVen, vecValUniVen, vecStock;
    private Vector vecTotUniVen, vecTotValUniVen, vecTotStock;
    
    private int tm=0,tm2=0, flag=0, colr=0;
    private int intColor[]; 
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodCli, strDesLarCli;             //Contenido del campo al obtener el foco.
    private String strCodVen, strNomVen;                //Contenido del campo al obtener el foco.
    private String strCodEmp, strNomEmp;                //Contenido del campo al obtener el foco. <jota>
    private String strCodLoc, strNomLoc;
    private String strCodGru, strNomGru;
    private String strCodItm;
    private String strCodForPag, strForPag;             //Contenido del campo al obtener el foco.
    private String strCodAlt, strNomItm;                //Contenido del campo al obtener el foco.
    private ZafTblCelRenLbl objTblCelRenLblText;
    
    private ZafPerUsr objPerUsr;  
    
    private int intCodEmp,intCodLoc,intCodUsu,intCodMnu;
   
    

///    **
//     * Crea una nueva instancia de la clase ZafVen08.
//     * @param obj El objeto ZafParSis.
//     */
    public ZafVen33(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone(); 
            txtCodItm.setVisible(false);    
             //23/dic/2013 
             intCodEmp=objParSis.getCodigoEmpresa();
             intCodLoc=objParSis.getCodigoLocal();
             intCodUsu=objParSis.getCodigoUsuario();
             intCodMnu=objParSis.getCodigoMenu();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        panFil = new javax.swing.JPanel();
        panFil2 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodVen = new javax.swing.JTextField();
        txtNomVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        lblVen = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblVen1 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblGru = new javax.swing.JLabel();
        txtCodGru = new javax.swing.JTextField();
        txtNomGru = new javax.swing.JTextField();
        butGru = new javax.swing.JButton();
        lblVen3 = new javax.swing.JLabel();
        txtCodAlt = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panNomCli = new javax.swing.JPanel();
        lblCodAltDes = new javax.swing.JLabel();
        txtCodAltDes = new javax.swing.JTextField();
        lblCodAltHas = new javax.swing.JLabel();
        txtCodAltHas = new javax.swing.JTextField();
        panCodAltItmTer = new javax.swing.JPanel();
        lblCodAltItmTer = new javax.swing.JLabel();
        txtCodAltItmTer = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        txtCodItm = new javax.swing.JTextField();
        chkVenRel = new javax.swing.JCheckBox();
        chkExcVenEmp = new javax.swing.JCheckBox();
        chkVenCli = new javax.swing.JCheckBox();
        chkPre = new javax.swing.JCheckBox();
        chkAgrItmCar = new javax.swing.JCheckBox();
        chkMosUniVen = new javax.swing.JCheckBox();
        chkMosValUniVen = new javax.swing.JCheckBox();
        chkMosPesUniVen = new javax.swing.JCheckBox();
        txtCodEmpLoc = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
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

        jScrollPane1.setBorder(null);

        panFil.setPreferredSize(new java.awt.Dimension(0, 480));
        panFil.setLayout(new java.awt.BorderLayout());

        panFil2.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todas las ventas");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil2.add(optTod);
        optTod.setBounds(10, 140, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo las ventas que cumplan el criterio seleccionado");
        panFil2.add(optFil);
        optFil.setBounds(10, 160, 400, 20);

        lblEmp.setText("Empresa");
        lblEmp.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblEmp);
        lblEmp.setBounds(37, 190, 60, 20);

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
        panFil2.add(txtCodVen);
        txtCodVen.setBounds(112, 230, 56, 20);

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
        panFil2.add(txtNomVen);
        txtNomVen.setBounds(168, 230, 460, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panFil2.add(butVen);
        butVen.setBounds(628, 230, 20, 20);

        lblVen.setText("Vendedor");
        lblVen.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblVen);
        lblVen.setBounds(37, 230, 40, 20);

        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panFil2.add(txtCodEmp);
        txtCodEmp.setBounds(112, 190, 56, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panFil2.add(txtNomEmp);
        txtNomEmp.setBounds(168, 190, 460, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil2.add(butEmp);
        butEmp.setBounds(628, 190, 20, 20);

        lblVen1.setText("Local");
        lblVen1.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblVen1);
        lblVen1.setBounds(37, 210, 40, 20);

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
        panFil2.add(txtCodLoc);
        txtCodLoc.setBounds(112, 210, 56, 20);

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
        panFil2.add(txtNomLoc);
        txtNomLoc.setBounds(168, 210, 460, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panFil2.add(butLoc);
        butLoc.setBounds(628, 210, 20, 20);

        lblGru.setText("Grupo");
        lblGru.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblGru);
        lblGru.setBounds(37, 250, 50, 20);

        txtCodGru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGruActionPerformed(evt);
            }
        });
        txtCodGru.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGruFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGruFocusLost(evt);
            }
        });
        panFil2.add(txtCodGru);
        txtCodGru.setBounds(112, 250, 56, 20);

        txtNomGru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomGruActionPerformed(evt);
            }
        });
        txtNomGru.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomGruFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomGruFocusLost(evt);
            }
        });
        panFil2.add(txtNomGru);
        txtNomGru.setBounds(168, 250, 460, 20);

        butGru.setText("...");
        butGru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGruActionPerformed(evt);
            }
        });
        panFil2.add(butGru);
        butGru.setBounds(628, 250, 20, 20);

        lblVen3.setText("Item");
        lblVen3.setToolTipText("Vendedor/Comprador");
        panFil2.add(lblVen3);
        lblVen3.setBounds(37, 270, 30, 20);

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
        panFil2.add(txtCodAlt);
        txtCodAlt.setBounds(112, 270, 92, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil2.add(butItm);
        butItm.setBounds(628, 270, 20, 20);

        panNomCli.setBorder(javax.swing.BorderFactory.createTitledBorder("Código alterno del item"));
        panNomCli.setLayout(null);

        lblCodAltDes.setText("Desde:");
        panNomCli.add(lblCodAltDes);
        lblCodAltDes.setBounds(12, 20, 48, 20);

        txtCodAltDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltDesFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltDes);
        txtCodAltDes.setBounds(60, 20, 100, 20);

        lblCodAltHas.setText("Hasta:");
        panNomCli.add(lblCodAltHas);
        lblCodAltHas.setBounds(168, 20, 48, 20);

        txtCodAltHas.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltHasFocusLost(evt);
            }
        });
        panNomCli.add(txtCodAltHas);
        txtCodAltHas.setBounds(216, 20, 100, 20);

        panFil2.add(panNomCli);
        panNomCli.setBounds(30, 300, 328, 52);

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
        txtCodAltItmTer.setBounds(112, 20, 204, 20);

        panFil2.add(panCodAltItmTer);
        panCodAltItmTer.setBounds(360, 300, 328, 52);

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
        panFil2.add(txtNomItm);
        txtNomItm.setBounds(204, 270, 424, 20);

        txtCodItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodItmActionPerformed(evt);
            }
        });
        txtCodItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodItmFocusLost(evt);
            }
        });
        panFil2.add(txtCodItm);
        txtCodItm.setBounds(55, 270, 56, 20);

        chkVenRel.setSelected(true);
        chkVenRel.setText("Ventas a empresas relacionadas");
        panFil2.add(chkVenRel);
        chkVenRel.setBounds(10, 100, 224, 23);

        chkExcVenEmp.setSelected(true);
        chkExcVenEmp.setText("Mostrar los items");
        panFil2.add(chkExcVenEmp);
        chkExcVenEmp.setBounds(10, 420, 400, 20);

        chkVenCli.setSelected(true);
        chkVenCli.setText("Ventas a clientes");
        panFil2.add(chkVenCli);
        chkVenCli.setBounds(10, 80, 130, 23);

        chkPre.setSelected(true);
        chkPre.setText("Préstamos");
        panFil2.add(chkPre);
        chkPre.setBounds(10, 120, 400, 20);

        chkAgrItmCar.setText("Agrupar los Items por los primeros 3 caracteres del código alterno");
        chkAgrItmCar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAgrItmCarActionPerformed(evt);
            }
        });
        panFil2.add(chkAgrItmCar);
        chkAgrItmCar.setBounds(10, 440, 470, 23);

        chkMosUniVen.setSelected(true);
        chkMosUniVen.setText("Mostrar \"Unidades Vendidas\"");
        panFil2.add(chkMosUniVen);
        chkMosUniVen.setBounds(10, 360, 310, 23);

        chkMosValUniVen.setSelected(true);
        chkMosValUniVen.setText("Mostrar \"Valor de las Unidades Vendidas\"");
        panFil2.add(chkMosValUniVen);
        chkMosValUniVen.setBounds(10, 380, 310, 23);

        chkMosPesUniVen.setSelected(true);
        chkMosPesUniVen.setText("Mostrar \"Peso de las Unidades Vendidas (Kg)\"");
        panFil2.add(chkMosPesUniVen);
        chkMosPesUniVen.setBounds(10, 400, 310, 23);

        txtCodEmpLoc.setEditable(false);
        txtCodEmpLoc.setEnabled(false);
        panFil2.add(txtCodEmpLoc);
        txtCodEmpLoc.setBounds(90, 210, 20, 19);

        panFil.add(panFil2, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(panFil);

        tabFrm.addTab("Filtro", jScrollPane1);

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

        spnTot.setPreferredSize(new java.awt.Dimension(454, 16));

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

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
    }//GEN-LAST:event_formInternalFrameOpened

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodVen.setText("");
            txtNomVen.setText("");
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodLoc.setText("");
            txtNomLoc.setText("");
            txtCodGru.setText("");
            txtNomGru.setText("");
            txtCodItm.setText("");
            txtCodAlt.setText("");
            txtNomItm.setText("");
            txtCodAltDes.setText("");
            txtCodAltHas.setText("");
            txtCodAltItmTer.setText("");
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
    }//GEN-LAST:event_tblDatKeyPressed

    
    
    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        columnaPorcentual=0;
         colr=0;
         tm=0;
         flag=0;
         if (isCamVal())
        {
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
// /**
//     * Esta función determina si los campos son válidos.
//     * @return true: Si los campos son válidos.
//     * <BR>false: En el caso contrario.
//     */
    private boolean isCamVal()
    {
        if( (!chkVenCli.isSelected()) && (!chkVenRel.isSelected()) && (!chkPre.isSelected())){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar el filtro de ventas a mostrar.<BR>Seleccione <FONT COLOR=\"blue\">Ventas a clientes</FONT> o <FONT COLOR=\"blue\">Ventas a empresas relasionadas</FONT> <BR> o <FONT COLOR=\"blue\">Prestamos</FONT> y vuelva a intentarlo.</HTML>");
                chkVenCli.requestFocus();
                return false;
            }
        return true;
    }
    
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
    if (!txtCodVen.getText().equalsIgnoreCase(strCodVen)){
        if (txtCodVen.getText().equals("")){
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
        else{
            mostrarVenConVen(1);
        }
    }
    else
        txtCodVen.setText(strCodVen);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodVen.getText().length()>0){
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
    if (!txtNomVen.getText().equalsIgnoreCase(strNomVen)){
        if (txtNomVen.getText().equals("")){
            txtCodVen.setText("");
            txtNomVen.setText("");
        }
        else{
            mostrarVenConVen(2);
        }
    }
    else
        txtNomVen.setText(strNomVen);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodVen.getText().length()>0){
        optFil.setSelected(true);
    }
}//GEN-LAST:event_txtNomVenFocusLost


private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
  // configurarVenConVen();
    mostrarVenConVen(0);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodVen.getText().length()>0)
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_butVenActionPerformed

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
       strCodEmp=txtCodEmp.getText();
         txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
            if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
    {
        if (txtCodEmp.getText().equals("")){
            txtCodEmp.setText("");
            txtNomEmp.setText("");
        }
        else{
            mostrarEmp(1);
        }
    }
    else
        txtCodEmp.setText(strCodEmp);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodEmp.getText().length()>0){
        optFil.setSelected(true);
    }
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
    txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
    if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
    {
        if (txtNomEmp.getText().equals("")){
            txtCodEmp.setText("");
            txtNomEmp.setText("");
        }
        else{
            mostrarEmp(2);
        }
    }
    else
        txtNomEmp.setText(strNomEmp);
    //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0){
        optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        //configurarVenConVen();
        mostrarEmp(0); 
        //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodEmp.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
        txtCodLoc.transferFocus();
    }//GEN-LAST:event_txtCodLocActionPerformed

    private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
        strCodLoc=txtCodLoc.getText();
        txtCodLoc.selectAll();   
    }//GEN-LAST:event_txtCodLocFocusGained

    private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
    if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc))
    {
        if (txtCodLoc.getText().equals("")){
            txtCodLoc.setText("");
            txtNomLoc.setText("");
        }
        else{
            mostrarLocal(1);
        }
    }
    else
        txtCodLoc.setText(strCodLoc);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodLoc.getText().length()>0){
        optFil.setSelected(true);
    
    }    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
             strNomEmp=txtNomLoc.getText();
             txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
         if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
    {
        if (txtNomLoc.getText().equals("")){
            txtCodLoc.setText("");
            txtNomLoc.setText("");
        }
        else{
            mostrarLocal(2);
        }
    }
    else
        txtNomLoc.setText(strNomLoc);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodLoc.getText().length()>0){
        optFil.setSelected(true);
    }
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
//        if(txtCodEmp.getText().length()>0)
//            configurarLocal();
        mostrarLocal(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void txtCodGruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGruActionPerformed
        txtCodGru.transferFocus();
    }//GEN-LAST:event_txtCodGruActionPerformed

    private void txtCodGruFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGruFocusGained
        strCodGru=txtCodGru.getText();
        txtCodGru.selectAll();   
    }//GEN-LAST:event_txtCodGruFocusGained

    private void txtCodGruFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGruFocusLost
  if (!txtCodGru.getText().equalsIgnoreCase(strCodGru))
    {
        if (txtCodGru.getText().equals("")){
            txtCodGru.setText("");
            txtNomGru.setText("");
        }
        else{
            mostrarGrupo(1);
        }
    }
    else
        txtCodGru.setText(strCodGru);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodGru.getText().length()>0){
        optFil.setSelected(true);
    }
    }//GEN-LAST:event_txtCodGruFocusLost

    private void txtNomGruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomGruActionPerformed
        txtNomGru.transferFocus();
    }//GEN-LAST:event_txtNomGruActionPerformed

    private void txtNomGruFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomGruFocusGained
         strNomGru=txtNomGru.getText();
        txtNomGru.selectAll();  
    }//GEN-LAST:event_txtNomGruFocusGained

    private void txtNomGruFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomGruFocusLost
         if (!txtNomGru.getText().equalsIgnoreCase(strNomGru))
    {
        if (txtNomGru.getText().equals("")){
            txtCodGru.setText("");
            txtNomGru.setText("");
        }
        else{
            mostrarGrupo(2);
        }
    }
    else
        txtNomGru.setText(strNomGru);
    //Seleccionar el JRadioButton de filtro si es necesario.
    if (txtCodGru.getText().length()>0){
        optFil.setSelected(true);
    }
    }//GEN-LAST:event_txtNomGruFocusLost

    private void butGruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGruActionPerformed
        mostrarGrupo(0);
    }//GEN-LAST:event_butGruActionPerformed

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
       strCodItm=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodItm))
        {
            if (txtCodAlt.getText().equals("")){
                txtCodAlt.setText("");
                txtNomItm.setText("");
                txtCodItm.setText("");
            }
            else{
                mostrarItem(1);
            }
        }
        else
            txtCodAlt.setText(strCodItm);
    //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodAlt.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomGru=txtNomGru.getText();
        txtNomGru.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals("")){
                txtCodAlt.setText("");
                txtNomItm.setText("");
                txtCodItm.setText("");
            }
            else{
                mostrarItem(2);
            }
        }
    else
        txtNomItm.setText(strNomItm);
    //Seleccionar el JRadioButton de filtro si es necesario.
        if (txtCodAlt.getText().length()>0){
            optFil.setSelected(true);
        }
    }//GEN-LAST:event_txtNomItmFocusLost

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        //configurarItem();
        mostrarItem(0);
    }//GEN-LAST:event_butItmActionPerformed

    private void txtCodAltDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusGained
        txtCodAltDes.selectAll();
    }//GEN-LAST:event_txtCodAltDesFocusGained

    private void txtCodAltDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltDesFocusLost
        if(txtCodAltDes.getText().length()>0) {
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

    private void txtCodItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodItmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmActionPerformed

    private void txtCodItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmFocusGained

    private void txtCodItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodItmFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodItmFocusLost

    private void chkAgrItmCarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAgrItmCarActionPerformed
        // TODO add your handling code here:
        if(chkAgrItmCar.isSelected()){
            txtCodGru.setEnabled(false);
            txtNomGru.setEnabled(false);
            butGru.setEnabled(false);
            chkExcVenEmp.setSelected(false);
            chkExcVenEmp.setEnabled(false);
            txtCodGru.setText("");
            txtNomGru.setText("");
        }
        else{
            txtCodGru.setEnabled(true);
            txtNomGru.setEnabled(true);
            butGru.setEnabled(true);
            chkExcVenEmp.setEnabled(true);
            chkExcVenEmp.setSelected(true);
        }
    }//GEN-LAST:event_chkAgrItmCarActionPerformed

//    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butGru;
    private javax.swing.JButton butItm;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butVen;
    private javax.swing.JCheckBox chkAgrItmCar;
    private javax.swing.JCheckBox chkExcVenEmp;
    private javax.swing.JCheckBox chkMosPesUniVen;
    private javax.swing.JCheckBox chkMosUniVen;
    private javax.swing.JCheckBox chkMosValUniVen;
    private javax.swing.JCheckBox chkPre;
    private javax.swing.JCheckBox chkVenCli;
    private javax.swing.JCheckBox chkVenRel;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodAltDes;
    private javax.swing.JLabel lblCodAltHas;
    private javax.swing.JLabel lblCodAltItmTer;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblGru;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JLabel lblVen1;
    private javax.swing.JLabel lblVen3;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCodAltItmTer;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFil2;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panNomCli;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodAltDes;
    private javax.swing.JTextField txtCodAltHas;
    private javax.swing.JTextField txtCodAltItmTer;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodEmpLoc;
    private javax.swing.JTextField txtCodGru;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomGru;
    private javax.swing.JTextField txtNomItm;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtNomVen;
    // End of variables declaration//GEN-END:variables

    
//    /** Configurar el formulario. */
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
           // System.out.println("configurarFrm");
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFil2.add(objSelFec);
            objPerUsr=new ZafPerUsr(objParSis);
            objSelFec.setBounds(4, 4, 472, 72);
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + "v1.2"); // Jose Marin 3 Enero 2013
            lblTit.setText(strAux);           
            //Configurar las ZafVenCon.
            if(!objPerUsr.isOpcionEnabled(3775)){     //Ventas a clientes
                chkVenCli.setSelected(false);
                chkVenCli.setEnabled(false);
            }
            if(!objPerUsr.isOpcionEnabled(3776)){    //Ventas a empresas relacionadas
                chkVenRel.setSelected(false);
                chkVenRel.setEnabled(false);
            }
            if(!objPerUsr.isOpcionEnabled(3777)){    //Prestamos
                chkPre.setSelected(false);
                chkPre.setEnabled(false);
            }
            if(!objPerUsr.isOpcionEnabled(3778)){    //Unidades Vendidas
                chkMosUniVen.setSelected(false);
                chkMosUniVen.setEnabled(false);
            }
            if(!objPerUsr.isOpcionEnabled(3779)){    //Valos de las unidades vendidas
                chkMosValUniVen.setSelected(false);
                chkMosValUniVen.setEnabled(false);
            }
            if(!objPerUsr.isOpcionEnabled(3780)){    //Peso de las unidades vendidas
                chkMosPesUniVen.setSelected(false);
                chkMosPesUniVen.setEnabled(false);
            }
            txtCodEmpLoc.setVisible(false); // Enero/3/2013 José Marín
            configurarVenConVen();
            configurarEmp();
            configurarLocal();
            configurarGrupo();
            configurarItem();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch(Exception e){
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
    
        private boolean configurarTblDat(){
        boolean blnRes=true;
        try{
          //  System.out.println("configurarTblDat");
        //Configurar JTable: Establecer el modelo.
        //vecDat=new Vector();    //Almacena los datos
        vecCab=new Vector();//Almacena las cabeceras
        vecCab.clear();
        vecCab.add(INT_TBL_DAT_NOM_DOC,"Nom.Doc.");
        vecCab.add(INT_TBL_DAT_COD_VEN,"Cód.Ven.");
        vecCab.add(INT_TBL_DAT_NOM_VEN,"Vendedor");
        objTblMod=new ZafTblMod();
        objTblMod.setHeader(vecCab);
        //objTblFil.setFilasEditables(vecFec);
       // objTblMod.setFilasEditables(vecDat);
        tblDat.setModel(objTblMod);
        
        
        
        //Configurar JTable: Establecer tipo de selección.
//        tblDat.setRowSelectionAllowed(true);
//        tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        //Configurar JTable: Establecer el menú de contexto.
//        objTblPopMnu=new ZafTblPopMnu(tblDat);
        //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
//        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        //Configurar JTable: Establecer el ancho de las columnas.
        javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
        tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(130); //jota
        tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(100); //jota
        tcmAux.getColumn(INT_TBL_DAT_NOM_VEN).setPreferredWidth(100);
        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
        //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
//        tblDat.getTableHeader().setReorderingAllowed(false);
        //Configurar JTable: Ocultar columnas del sistema.
        //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
//        objMouMotAda=new ZafMouMotAda();
//        tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        //Configurar JTable: Editor de búsqueda.

        //Configurar JTable: Establecer la fila de cabecera.
//        objTblFilCab=new ZafTblFilCab(tblDat);
        //Configurar JTable: Renderizar celdas.
        objTblCelRenLbl=new ZafTblCelRenLbl();
        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
        objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
        objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
        //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
        //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
        //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
        objTblCelRenLbl=null;
//        objTblCelRenLbl=new ZafTblCelRenLbl();
//        objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
//        objTblCelRenLbl=null;
        //Configurar JTable: Establecer la clase que controla el ordenamiento.
         //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
        //MOD 15/AGOS
            objTblOrd=new ZafTblOrd(tblDat);
        
        //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
        //Libero los objetos auxiliares.

        vecCab=null;
        tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

        
    private boolean configurarTblDatMos(int cont){
        int fecha, fecha2=0,k=0,i=0,j=0;
        boolean blnRes=true;
        try{
            cont=cont*4;
           // System.out.println("configurarTblDatMos");
            tm=0;
             flag=0;
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(cont+6);//Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_GRU,"Cód.Grp.");//0
            vecCab.add(INT_TBL_DAT_NOM_GRU,"Grupo");//1
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm.");//2
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");//3
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del item");//4
            for(i=6;i<=cont+5;i++){
                vecCab.add(i,"Uni.Ven.");i++;
                vecCab.add(i,"Val.Uni.Ven.");i++;
                vecCab.add(i,"Pes.Uni.Ven.");i++;
                vecCab.add(i,"Stock");
            }
            //add
            //vecCab.add(i,"TOTAL");
                vecCab.add(i,"Uni.Ven.");i++;
                vecCab.add(i,"Val.Uni.Ven.");i++;
                vecCab.add(i,"Pes.Uni.Ven.");i++;
                vecCab.add(i,"Stock");
            //TOTALES
             objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //objTblMod.setFilasEditables(vecDat);
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
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(40); //jota
            //tcmAux.getColumn(INT_TBL_DAT_COD_VEN).setPreferredWidth(100); //jota
            tcmAux.getColumn(INT_TBL_DAT_COD_GRU).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_GRU).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(100);
            
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(250);
            tblDat.getTableHeader().setReorderingAllowed(false);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            if(chkAgrItmCar.isSelected()){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GRU, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_GRU, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NOM_ITM, tblDat); //2/Ene/2014 Jose Marin 
            }
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM, tblDat);
            
            
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*2);
            fecha=Integer.parseInt(vecFecha.get(0).toString());
            for(i=6;i<=cont+5;i++){
                if(fecha!=fecha2)
                {
                    fecha=Integer.parseInt(vecFecha.get(k).toString());
                 switch(fecha){
                        case 1: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses1=new ZafTblHeaColGrp("ENE/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses1.setHeight(16);
                            objTblHeaColGrpMeses1.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses1.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses1.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses1.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses1);
                            objTblHeaColGrpMeses1=null;
                            break;
                        case 2: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses2=new ZafTblHeaColGrp("FEB/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses2.setHeight(16);
                            objTblHeaColGrpMeses2.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses2.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses2.add(tcmAux.getColumn(i));i++;
                            objTblHeaColGrpMeses2.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses2);
                            objTblHeaColGrpMeses2=null;
                            break;
                        case 3: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses3=new ZafTblHeaColGrp("MAR/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses3.setHeight(16);
                                objTblHeaColGrpMeses3.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses3.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses3.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses3.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses3);
                            objTblHeaColGrpMeses3=null;
                            break; 
                        case 4: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses4=new ZafTblHeaColGrp("ABR/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses4.setHeight(16);
                                objTblHeaColGrpMeses4.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses4.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses4.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses4.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses4);
                            objTblHeaColGrpMeses4=null;
                            break;
                        case 5: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses5=new ZafTblHeaColGrp("MAY/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses5.setHeight(16);
                                objTblHeaColGrpMeses5.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses5.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses5.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses5.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses5);
                            objTblHeaColGrpMeses5=null;
                            break;
                        case 6: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses6=new ZafTblHeaColGrp("JUN/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses6.setHeight(16);
                                objTblHeaColGrpMeses6.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses6.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses6.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses6.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses6);
                            objTblHeaColGrpMeses6=null;
                            break;
                        case 7: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses7=new ZafTblHeaColGrp("JUL/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses7.setHeight(16);
                                objTblHeaColGrpMeses7.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses7.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses7.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses7.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses7);
                            objTblHeaColGrpMeses7=null;;
                            break;
                        case 8: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses8=new ZafTblHeaColGrp("AGO/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses8.setHeight(16);
                                objTblHeaColGrpMeses8.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses8.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses8.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses8.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses8);
                            objTblHeaColGrpMeses8=null;
                            break;
                        case 9: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses9=new ZafTblHeaColGrp("SEP/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses9.setHeight(16);
                                objTblHeaColGrpMeses9.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses9.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses9.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses9.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses9);
                            objTblHeaColGrpMeses9=null;
                            break;
                        case 10: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses10=new ZafTblHeaColGrp("OCT/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses10.setHeight(16);
                                objTblHeaColGrpMeses10.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses10.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses10.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses10.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses10);
                            objTblHeaColGrpMeses10=null;
                            break;
                        case 11: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses11=new ZafTblHeaColGrp("NOV/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses11.setHeight(16);
                                objTblHeaColGrpMeses11.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses11.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses11.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses11.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses11);
                            objTblHeaColGrpMeses11=null;
                            break;
                        case 12: 
                            ZafTblHeaColGrp objTblHeaColGrpMeses12=new ZafTblHeaColGrp("DIC/" + vecAnio.get(k).toString());
                            objTblHeaColGrpMeses12.setHeight(16);
                                objTblHeaColGrpMeses12.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses12.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses12.add(tcmAux.getColumn(i));i++;
                                objTblHeaColGrpMeses12.add(tcmAux.getColumn(i));
                            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMeses12);
                            objTblHeaColGrpMeses12=null;
                            break;
                      }
                }
                k++;
            }
            ZafTblHeaColGrp objTblHeaColGrpTotales=new ZafTblHeaColGrp("TOTALES");
            objTblHeaColGrpTotales.setHeight(16);
                objTblHeaColGrpTotales.add(tcmAux.getColumn(i));i++;
                objTblHeaColGrpTotales.add(tcmAux.getColumn(i));i++;
                objTblHeaColGrpTotales.add(tcmAux.getColumn(i));i++;
                objTblHeaColGrpTotales.add(tcmAux.getColumn(i));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpTotales);
            objTblHeaColGrpTotales=null;
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //Configurar JTable: Ocultar columnas del sistema.
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
          
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
           
            for(i=6;i<=cont+5;i++)
            {
                //columnas dinamicas
                tcmAux.getColumn(i).setPreferredWidth(80);//Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Val.Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosValUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;    
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Pes.Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosPesUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;    
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Stock
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                objTblMod.addSystemHiddenColumn(i, tblDat); //se oculta el Stock
                columnaPorcentual++;
            }
                //TOTALES
                tcmAux.getColumn(i).setPreferredWidth(80);//Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Val.Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosValUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;    
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Pes.Uni.Ven.
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                if(!chkMosPesUniVen.isSelected())
                    objTblMod.addSystemHiddenColumn(i, tblDat);
                columnaPorcentual++;    
                i++;
                tcmAux.getColumn(i).setPreferredWidth(80);//Stock
                tcmAux.getColumn(i).setCellRenderer(objTblCelRenLbl);
                objTblMod.addSystemHiddenColumn(i, tblDat); //se oculta el Stock 
                columnaPorcentual++;
                
                /* -------------------------------------------------------------
                 * JOSE MARIN: 
                 * 
                 * EL STOCK QUEDA OCULTO POR ESTAR MAL SACADO DENTRO DEL QUERY
                 * QUEDA PENDIENTE ENCONTRAR EL STOCK A FIN DE MES
                 * -------------------------------------------------------------
                 */
                
                //TOTALES       
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            objTblCelRenLblText = new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_GRU).setCellRenderer(objTblCelRenLblText);
            tcmAux.getColumn(INT_TBL_DAT_NOM_GRU).setCellRenderer(objTblCelRenLblText);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLblText);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblText);
            objTblCelRenLblText=null;
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.            
            //Configurar JTable: Renderizar celdas.
            //Configurar JTable: Establecer la clase que controla el ordenamiento.            
            objTblOrd=new ZafTblOrd(tblDat);            
            int intCol[] = new int[300];
            int b=0;
            for(i=6;i<=cont+5;i++)
            {
                intCol[b]=i;
                b++;
                i++;
                intCol[b]=i;
                b++;
                i++;
                intCol[b]=i;
                b++;
                i++;
                intCol[b]=i;
                b++;
            }
            intColTot=intCol;
            //add    
            intCol[b]=i;
            intCol[b+1]=i+1;
            intCol[b+2]=i+2;
            intCol[b+3]=i+3;
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol); 
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }  
 
   
//    /**
//     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
//     * @return true: Si se pudo consultar los registros.
//     * <BR>false: En el caso contrario.
//     */
    
    private boolean cargarDetReg(){
        int i,temp=0,j=0;
        String strConSQLFec="",strConSQL2="",strConSQLFec2="",strAux2="",strAux3="", strAux4="", strEmp="";
        String strMosItmCar="";
       
        //objUti=new ZafUtil();
        vecDat=new Vector();    //Almacena los datos
        boolean blnRes=true;
        strAux="";
        try{
           // System.out.println("cargarDetReg");
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                strConSQLFec="";
               switch (objSelFec.getTipoSeleccion()){
                   case 0: //Búsqueda por rangos
                        strConSQLFec+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        strConSQLFec2=" AND c.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                   case 1: //Fechas menores o iguales que "Hasta".
                       strConSQLFec+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                       strConSQLFec2+=" AND c.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                       
                       break;
                   case 2: //Fechas mayores o iguales que "Desde".
                       strConSQLFec+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                       strConSQLFec2+=" AND c.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                       break;
                   case 3: //Todo....
                   break;
               }
                if (txtCodAltDes.getText().length()>0 || txtCodAltHas.getText().length()>0)
                    strAux+=" AND ((LOWER(a3.tx_codAlt) BETWEEN '" + txtCodAltDes.getText().replaceAll("'", "''").toLowerCase() + "' AND '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "') OR LOWER(a3.tx_codAlt) LIKE '" + txtCodAltHas.getText().replaceAll("'", "''").toLowerCase() + "%')";
               
                if (txtCodAltItmTer.getText().length()>0){
                    strConSQL+=getConSQLAdiCamTer("a3.tx_codAlt", txtCodAltItmTer.getText());}
                

               if(intCodEmp==objParSis.getCodigoEmpresaGrupo())
               {
                    if(intCodUsu==1){
                            if (txtCodEmp.getText().length()>0) {
                                strConSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                                strConSQL2+=" and c.co_emp=" + txtCodEmp.getText();
                                strAux2+=" and a3.co_emp=" + txtCodEmp.getText();
                            }
                            if (txtCodLoc.getText().length()>0) {
                                strConSQL+=" and a1.co_loc=" + txtCodLoc.getText();
                                strConSQL2+=" and c.co_loc=" + txtCodLoc.getText();
                            }
                             if(txtCodVen.getText().length()>0) {
                             strConSQL+=" and a1.co_com=" + txtCodVen.getText();
                             strConSQL2+=" and c.co_com=" + txtCodVen.getText();
                         }
                    ////FILTRO PARA EMPRESAS 
                             //SOLO EMPRESA SIN LOCAL 
                            if (txtCodEmp.getText().length()>0 && txtCodLoc.getText().length()<=0) {
                                if (txtCodEmp.getText().equals("1")){
                                    strEmp="AND "
                                    +"( (a1.co_emp=1 AND a1.co_loc=1) OR (a1.co_emp=1 AND a1.co_loc=4) OR "
                                    +"(a1.co_emp=1 AND a1.co_loc=5) OR (a1.co_emp=1 AND a1.co_loc=6) OR (a1.co_emp=1 AND a1.co_loc=10))";
                                }
                                if(txtCodEmp.getText().equals("2"))
                                {
                                    strEmp="AND "
                                    +"((a1.co_emp=2 AND a1.co_loc=1) OR (a1.co_emp=2 AND a1.co_loc=2) OR (a1.co_emp=2 AND a1.co_loc=4) OR "
                                    +"(a1.co_emp=2 AND a1.co_loc=5) OR (a1.co_emp=2 AND a1.co_loc=6) OR (a1.co_emp=2 AND a1.co_loc=7) OR "
                                    +"(a1.co_emp=2 AND a1.co_loc=8) OR (a1.co_emp=2 AND a1.co_loc=9))";
                                }
                                if(txtCodEmp.getText().equals("3")){
                                    strEmp="AND "
                                    +"( (a1.co_emp=3 AND a1.co_loc=1) OR "
                                    +"(a1.co_emp=3 AND a1.co_loc=2) )";
                                }
                                if(txtCodEmp.getText().equals("4")){
                                    strEmp="AND "
                                    +"( (a1.co_emp=4 AND a1.co_loc=1) OR (a1.co_emp=4 AND a1.co_loc=2) OR" 
                                    +"(a1.co_emp=4 AND a1.co_loc=3) OR (a1.co_emp=4 AND a1.co_loc=4) OR" 
                                    +"(a1.co_emp=4 AND a1.co_loc=10) ) ";
                                }
                          }
                        //EMPRESAS Y LOCAL 
                        if (txtCodEmp.getText().length()>0 && txtCodLoc.getText().length()>0) {
                            if(txtCodEmp.getText().equals("1") && txtCodLoc.getText().equals("1")){
                                strEmp="AND ((a1.co_emp=1 AND a1.co_loc=1))";}
                            if(txtCodEmp.getText().equals("1") && txtCodLoc.getText().equals("4")){
                                strEmp="AND ( (a1.co_emp=1 AND a1.co_loc=4))";}
                            if(txtCodEmp.getText().equals("1") && txtCodLoc.getText().equals("5"))
                                {strEmp="AND ( (a1.co_emp=1 AND a1.co_loc=5)) ";}
                            if(txtCodEmp.getText().equals("1") && txtCodLoc.getText().equals("6"))
                                {strEmp="AND ( (a1.co_emp=1 AND a1.co_loc=6)) ";}
                            if(txtCodEmp.getText().equals("1") && txtCodLoc.getText().equals("10"))
                                {strEmp="AND ( (a1.co_emp=1 AND a1.co_loc=10)) ";}
                            //2
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("1"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=1)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("2"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=2)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("3"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=3)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("4"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=4)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("5"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=5)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("6"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=6)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("7"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=7)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("8"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=8)) ";}
                            if(txtCodEmp.getText().equals("2") && txtCodLoc.getText().equals("9"))
                                {strEmp="AND ( (a1.co_emp=2 AND a1.co_loc=9)) ";}
                            //3
                            if(txtCodEmp.getText().equals("3") && txtCodLoc.getText().equals("1"))
                                {strEmp="AND ( (a1.co_emp=3 AND a1.co_loc=1)) ";}
                            if(txtCodEmp.getText().equals("3") && txtCodLoc.getText().equals("2"))
                                {strEmp="AND ( (a1.co_emp=3 AND a1.co_loc=2)) ";}
                            //4
                            if(txtCodEmp.getText().equals("4") && txtCodLoc.getText().equals("1"))
                                {strEmp="AND ( (a1.co_emp=4 AND a1.co_loc=1)) ";}
                            if(txtCodEmp.getText().equals("4") && txtCodLoc.getText().equals("2"))
                                {strEmp="AND ( (a1.co_emp=4 AND a1.co_loc=2)) ";}
                            if(txtCodEmp.getText().equals("4") && txtCodLoc.getText().equals("3"))
                                {strEmp="AND ( (a1.co_emp=4 AND a1.co_loc=3)) ";}
                            if(txtCodEmp.getText().equals("4") && txtCodLoc.getText().equals("4"))
                                {strEmp="AND ( (a1.co_emp=4 AND a1.co_loc=4)) ";}
                            if(txtCodEmp.getText().equals("4") && txtCodLoc.getText().equals("5"))
                                {strEmp="AND ( (a1.co_emp=4 AND a1.co_loc=5)) ";}
                        }
                        
//                        if (txtCodEmp.getText().length()<=0 && txtCodLoc.getText().length()<=0) 
//                            {
//                                strEmp=" AND ((a1.co_emp=1 AND a1.co_loc=1) OR (a1.co_emp=1 AND a1.co_loc=4) OR " +
//                                "(a1.co_emp=1 AND a1.co_loc=5) OR (a1.co_emp=1 AND a1.co_loc=6) OR (a1.co_emp=1 AND a1.co_loc=10) OR" +
//                                "(a1.co_emp=2 AND a1.co_loc=1) OR (a1.co_emp=2 AND a1.co_loc=2) OR (a1.co_emp=2 AND a1.co_loc=4) OR " +
//                                "(a1.co_emp=2 AND a1.co_loc=5) OR (a1.co_emp=2 AND a1.co_loc=6) OR (a1.co_emp=2 AND a1.co_loc=7) OR " +
//                                " (a1.co_emp=2 AND a1.co_loc=8) OR (a1.co_emp=2 AND a1.co_loc=9) OR (a1.co_emp=3 AND a1.co_loc=1) OR " +
//                                "(a1.co_emp=3 AND a1.co_loc=2) OR (a1.co_emp=4 AND a1.co_loc=1) OR (a1.co_emp=4 AND a1.co_loc=2) OR " +
//                                "(a1.co_emp=4 AND a1.co_loc=3) OR (a1.co_emp=4 AND a1.co_loc=4) OR (a1.co_emp=4 AND a1.co_loc=10))";
//                            }
                    } //NO ES ADMINISTRADOR PERO INGRESA POR EL GRUPO 
                    else{
                            if (txtCodEmp.getText().length()>0) {
                                strConSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                                strConSQL2+=" and c.co_emp=" + txtCodEmp.getText();
                                strAux2+=" and a3.co_emp=" + txtCodEmp.getText();
                                if (txtCodLoc.getText().length()>0) {
                                    strConSQL+=" and a1.co_loc=" + txtCodLoc.getText();
                                    strConSQL2+=" and c.co_loc=" + txtCodLoc.getText();
                                }
                                else{
                                    strConSQL+=" and a1.co_loc in (SELECT DISTINCT a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                                    strConSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                    strConSQL+=" a2.co_mnu=" + intCodMnu;
                                    strConSQL+=" and a2.co_emp=" + intCodEmp;
                                    strConSQL+=" and a2.co_loc=" + intCodLoc;
                                    strConSQL+=" and a3.co_emp=" + txtCodEmp.getText(); // LAS EMPRESAS QUE YA HA ELEGIDO
                                    strConSQL+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                                    strConSQL2+=" and c.co_loc in (SELECT DISTINCT a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                                    strConSQL2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                    strConSQL2+=" a2.co_mnu=" + intCodMnu;
                                    strConSQL2+=" and a2.co_emp=" + intCodEmp;
                                    strConSQL2+=" and a2.co_loc=" + intCodLoc;
                                    strConSQL2+=" and a3.co_emp=" + txtCodEmp.getText(); // LAS EMPRESAS QUE YA HA ELEGIDO
                                    strConSQL2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                                }
                            }
                            else if (txtCodEmp.getText().length()<=0 && txtCodLoc.getText().length()>0) {
                                strConSQL+=" and a1.co_emp=" + txtCodEmpLoc.getText() + " and a1.co_loc=" + txtCodLoc.getText();
                                strConSQL2+=" and c.co_emp=" + txtCodEmpLoc.getText() + " and c.co_loc=" + txtCodLoc.getText();
                                strAux2+=" and a3.co_emp in (SELECT DISTINCT  a3.co_emp FROM tbr_locPrgUsr as a2 ";
                                strAux2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                strAux2+=" a2.co_mnu=" + intCodMnu;
                                strAux2+=" and a2.co_emp=" + intCodEmp;
                                strAux2+=" and a2.co_loc=" + intCodLoc;
                                strAux2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";   
                            }
                            else{
                                strConSQL+=" and a1.co_emp || a1.co_loc in (SELECT DISTINCT  a3.co_emp || a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                                strConSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                strConSQL+=" a2.co_mnu=" + intCodMnu;
                                strConSQL+=" and a2.co_emp=" + intCodEmp;
                                strConSQL+=" and a2.co_loc=" + intCodLoc;
                                strConSQL+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                                strConSQL2+=" and c.co_emp || c.co_loc in (SELECT DISTINCT  a3.co_emp || a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                                strConSQL2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                strConSQL2+=" a2.co_mnu=" + intCodMnu;
                                strConSQL2+=" and a2.co_emp=" + intCodEmp;
                                strConSQL2+=" and a2.co_loc=" + intCodLoc;
                                strConSQL2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                                strAux2+=" and a3.co_emp in (SELECT DISTINCT  a3.co_emp FROM tbr_locPrgUsr as a2 ";
                                strAux2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                                strAux2+=" a2.co_mnu=" + intCodMnu;
                                strAux2+=" and a2.co_emp=" + intCodEmp;
                                strAux2+=" and a2.co_loc=" + intCodLoc;
                                strAux2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";      
                            }
                            if(txtCodVen.getText().length()>0) {
                                strConSQL+=" and a1.co_com=" + txtCodVen.getText();
                                strConSQL2+=" and c.co_com=" + txtCodVen.getText();
                            }
                    }//
               }
               else{
                  //SI NO ENTRA COMO GRUPO <<< VALIDA TODO OTRA VEZ 
                        if(intCodUsu==1)
                        {
                            if(txtCodEmp.getText().length()>0){
                                strConSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                                strConSQL2+=" and c.co_emp=" + txtCodEmp.getText();
                                strAux2+=" and a3.co_emp=" + txtCodEmp.getText();
                            }
                            if(txtCodLoc.getText().length()>0) {
                                    strConSQL+=" and a1.co_loc="+txtCodLoc.getText();
                                    strConSQL2+=" and c.co_loc="+txtCodLoc.getText();
                             }
                             if(txtCodVen.getText().length()>0) {
                                 strConSQL+=" and a1.co_com=" + txtCodVen.getText();
                                 strConSQL2+=" and c.co_com=" + txtCodVen.getText();
                             }
                             strConSQL+=" and a1.co_emp="+ intCodEmp;
                            strConSQL2+=" and c.co_emp="+ intCodEmp;
                            strAux2+=" and a3.co_emp =" + intCodEmp;
                        }
                        else
                        {
                            if(txtCodEmp.getText().length()>0){
                                strConSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                                strConSQL2+=" and c.co_emp=" + txtCodEmp.getText();
                                strAux2+=" and a3.co_emp=" + txtCodEmp.getText();
                            }
                            if(txtCodLoc.getText().length()>0) {
                                    strConSQL+=" and a1.co_loc="+txtCodLoc.getText();
                                    strConSQL2+=" and c.co_loc="+txtCodLoc.getText();
                             }
                             if(txtCodVen.getText().length()>0) {
                                 strConSQL+=" and a1.co_com=" + txtCodVen.getText();
                                 strConSQL2+=" and c.co_com=" + txtCodVen.getText();
                             }
                            strConSQL+=" and a1.co_emp || a1.co_loc in (SELECT DISTINCT  a3.co_emp || a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                            strConSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                            strConSQL+=" a2.co_mnu=" + intCodMnu;
                            strConSQL+=" and a2.co_emp=" + intCodEmp;
                            strConSQL+=" and a2.co_loc=" + intCodLoc;
                            strConSQL+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                            strConSQL2+=" and c.co_emp || c.co_loc in (SELECT DISTINCT  a3.co_emp || a3.co_loc FROM tbr_locPrgUsr as a2 " ;
                            strConSQL2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE";
                            strConSQL2+=" a2.co_mnu=" + intCodMnu;
                            strConSQL2+=" and a2.co_emp=" + intCodEmp;
                            strConSQL2+=" and a2.co_loc=" + intCodLoc;
                            strConSQL2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";
                            strAux2+=" and a3.co_emp in (SELECT DISTINCT  a3.co_emp FROM tbr_locPrgUsr as a2 ";
                            strAux2+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc) WHERE ";
                            strAux2+=" a2.co_mnu=" + intCodMnu;
                            strAux2+=" and a2.co_emp=" + intCodEmp;
                            strAux2+=" and a2.co_loc=" + intCodLoc;
                            strAux2+=" and a2.st_reg in ('A','P') and a2.co_usr="+ intCodUsu+")";                        
                        }                     
            }                  
            strConSQL+=strConSQLFec;
            strConSQL2+=strConSQLFec2;
            if(intCodEmp==objParSis.getCodigoEmpresaGrupo()) 
            { //SI INGRESA COMO GRUPO
                strSQL=" ";
                if(chkAgrItmCar.isSelected())
                {
                    strSQL+=" SELECT SUBSTRING(a.tx_codAlt FROM 1 FOR 3) as tx_codAlt, a.fe_doc ,";
                    strSQL+=" SUM(a.nd_stkAct) as nd_stkAct,SUM(a.val) as val,a.anio,";
                    strSQL+=" SUM(a.nd_uniVen) as nd_uniVen,SUM(a.nd_venTot) as nd_venTot,SUM(a.valCate) as valCate,";
                    strSQL+=" SUM(a.valUniVen) as valUniVen,SUM(a.valvenTot) as valVenTot,SuM(a.valStkAct) as valStkAct,";
                    strSQL+=" '' as tx_desLar, '' as co_itm, '' as co_grp, '' as tx_nomItm";
                    strSQL+=" FROM( ";
                }
                strSQL+=" SELECT a.fe_doc,a.co_grp,a.tx_deslar,a.tx_codalt,a.co_itmMae as co_itm,a.tx_nomitm,a.nd_stkAct,a.val,a.nd_uniVen,";
                strSQL+=" a.nd_venTot,a.anio,b.valcate,b.valUniVen, b.valVenTot,b.valStkAct ";
                strSQL+=" FROM( ";
                strSQL+=" SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar, a.tx_codalt, a.co_itmMae, a.tx_nomitm,d.nd_stkAct,";
                strSQL+=" d.val, a.anio,''as valcate,'' as valUniVen,'' as valVenTot,'' as valStkAct,d.nd_uniVen,d.nd_venTot";
                strSQL+=" FROM(";
                strSQL+=" SELECT DISTINCT y.co_grp,y.tx_deslar,y.co_itmMae,x.fe_cot,y.tx_codalt,y.tx_nomitm,x.anio";
                strSQL+=" FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot,extract(Year from c.fe_doc) as anio";
                strSQL+=" FROM tbm_cabmovinv c "; 
                strSQL+=" WHERE c.co_emp <>0 ";
                strSQL+=" " + strConSQL2; //fechas
                strSQL+=" ) as x INNER JOIN( ";
                strSQL+=" SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END, a.tx_desLar, b.co_itmMae,";
                strSQL+=" b.tx_codalt, b.tx_nomitm";
                strSQL+=" FROM(SELECT PADRE.co_grp,PADRE.tx_deslar,a6.co_itmMae, a3.tx_codalt ";
                strSQL+=" FROM tbm_grpInvImp as PADRE ";
                strSQL+=" INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp) ";
                strSQL+=" INNER JOIN tbm_equInv as a6 ON (a3.co_emp=a6.co_emp and a3.co_itm=a6.co_itm)";
                strSQL+=" WHERE PADRE.co_grppad IS NULL and a3.co_emp <> 0";
                strSQL+="" + strAux2; 
                strSQL+="  GROUP BY PADRE.co_grp,PADRE.tx_deslar,HIJO.co_grp,a3.co_grpimp,a6.co_itmMae,a3.tx_codalt";
                strSQL+=" ) as a RIGHT OUTER JOIN( ";
                strSQL+=" SELECT distinct a6.co_itmMae,a3.tx_codalt,a3.tx_nomitm ";
                strSQL+=" FROM tbm_cabmovinv as a1 ";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND ";
                strSQL+=" a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)"; 
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)"; 
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_itm=a6.co_itm)";
                strSQL+=" WHERE 1=1";
                ////////////////////////////
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;
                //MODIFICADO
                //////////////////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL; 
                ////////////
                strSQL+=" AND a3.st_ser IN ('N', 'S', 'T', 'O')";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')  AND a4.ne_mod=1 )AS b ON (a.co_itmMae=b.co_itmMae)";
                strSQL+=" ";
                strSQL+=" WHERE 1=1";
                if(txtCodGru.getText().length()>0) {
                       strSQL+=" and a.co_grp=" +txtCodGru.getText();
                }
                if(txtCodItm.getText().length()>0) {
//                    if(intCodEmp==objParSis.getCodigoEmpresaGrupo())
//                        if(intCodUsu==1)
//                        {
                           strSQL+=" AND b.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm="+ txtCodItm.getText() +" )";
//                        }
//                    if(intCodEmp!=objParSis.getCodigoEmpresaGrupo())
//                        strSQL+=" and b.co_itm=" +txtCodItm.getText();
                }
                strSQL+=" ";
                strSQL+=" )as y on(x.fe_cot != y.tx_codalt) ";
                strSQL+=" )AS a LEFT OUTER JOIN( SELECT extract (month from a1.fe_doc) as fe_doc,";
                strSQL+=" a6.co_itmMae,0 as nd_stkAct,a3.tx_codAlt, sum(-(CASE WHEN(a1.st_tipDev IS NULL OR a1.st_tipDev='C') ";
                strSQL+=" THEN a2.nd_can ELSE 0 END)*a3.nd_pesItmKgr) as val, ";
                strSQL+=" -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen,";
                strSQL+=" -SUM(a2.nd_tot) AS nd_venTot ";
                strSQL+=" FROM tbm_cabmovinv as a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON(a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND";
                strSQL+=" a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) ";
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_itm=a6.co_itm) ";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a4.ne_mod=1 and a3.co_emp<>0";
                strSQL+=" ";          
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;
                //////////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL;                
                //EMPRESAS 
                strSQL+=" " + strEmp;
                //
                strSQL+=" GROUP BY a6.co_itmMae,extract(month from a1.fe_doc),a3.tx_codalt, a3.tx_nomitm";
                strSQL+=")as d ON (a.co_itmMae=d.co_itmMae and a.fe_cot=d.fe_doc)";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar ,a.co_itmMae, a.tx_codalt, a.tx_nomitm,d.nd_stkAct, a.anio, a.fe_cot,";
                strSQL+=" d.val,d.nd_uniVen,d.nd_venTot";
                strSQL+=" ";
                strSQL+=" )as a LEFT OUTER JOIN  (";
                strSQL+=" SELECT fe_doc, co_grp,tx_deslar,'' as tx_codalt,'' as co_itmMae,'' as tx_nomitm,'' as nd_stkAct,"; 
                strSQL+=" anio,'' as val , sum(val) as valCate, sum(nd_uniVen) as valUniVen, sum(nd_venTot) as valVenTot,";
                strSQL+=" sum(nd_stkAct) as valStkAct,'' as nd_uniVen, '' as nd_venTot";
                strSQL+=" FROM(SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar,d.val, a.anio,a.co_itmMae,a.tx_nomItm, ";
                strSQL+=" d.nd_stkAct,d.nd_uniVen,d.nd_venTot ";
                strSQL+=" FROM(SELECT y.co_grp,y.tx_deslar,y.co_itmMae,x.fe_cot,y.tx_codalt,y.tx_nomitm,x.anio ";
                strSQL+=" FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot, extract(Year from c.fe_doc) as anio";
                strSQL+=" FROM tbm_cabmovinv c ";
                strSQL+=" WHERE 1=1";
                strSQL+=" " + strConSQL2;
                strSQL+=" ) as x INNER JOIN (SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END,";
                strSQL+=" a.tx_deslar, b.co_itmMae,b.tx_codalt, b.tx_nomitm";
                strSQL+=" FROM(SELECT PADRE.co_grp,PADRE.tx_deslar, a6.co_itmMae";
                strSQL+=" FROM tbm_grpInvImp as PADRE ";
                strSQL+=" INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp)";
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_itm=a6.co_itm)";
                strSQL+=" WHERE PADRE.co_grppad IS NULL and a3.co_emp<>0";
                strSQL+="" + strAux2; 
                strSQL+=" GROUP BY PADRE.co_grp,PADRE.tx_deslar, HIJO.co_grp, a3.co_grpimp, a6.co_itmMae";
                strSQL+=" ) as a ";
                strSQL+=" ";
                strSQL+=" RIGHT OUTER JOIN(SELECT distinct a6.co_itmMae,a3.tx_codalt,a3.tx_nomitm ";
                strSQL+=" FROM tbm_cabmovinv as a1 ";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND";
                strSQL+=" a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND  a1.co_doc=a2.co_doc) ";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) "; 
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND"; 
                strSQL+=" a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_itm=a6.co_itm)";
                strSQL+=" WHERE 1=1 ";
                //////
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;
                ////////////////////////            
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL; 
                strSQL+=" AND a1.st_reg IN ('A','R','C','F') and a4.ne_mod=1 ";
                strSQL+=" GROUP BY a6.co_itmMae,a3.tx_codalt,a3.tx_nomitm)AS b ";
                strSQL+=" ON (a.co_itmMae=b.co_itmMae )";
                strSQL+=" WHERE 1=1";
                if(txtCodGru.getText().length()>0) {
                       strSQL+=" and a.co_grp=" +txtCodGru.getText();
                }
                if(txtCodItm.getText().length()>0) {
//                    if(intCodEmp==objParSis.getCodigoEmpresaGrupo())
//                        if(intCodUsu==1)
//                        {
                           strSQL+=" AND b.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0 AND co_itm="+ txtCodItm.getText() +" )";
//                        }
//                    if(intCodEmp!=objParSis.getCodigoEmpresaGrupo())
//                        strSQL+=" and b.co_itm=" +txtCodItm.getText();
                }
                strSQL+=" ";
                strSQL+=" )as y on(x.fe_cot != y.co_itmMae) ";
                strSQL+=" ";
                //
                strSQL+=" )AS a LEFT OUTER JOIN(SELECT extract (month from a1.fe_doc) as fe_doc,a6.co_itmMae,0 as nd_stkAct,";
                strSQL+=" sum(-(CASE WHEN(a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" THEN a2.nd_can ELSE 0 END)*a3.nd_pesItmKgr) as val,";
                strSQL+=" -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen,";
                strSQL+=" ";
                strSQL+=" -SUM(a2.nd_tot) AS nd_venTot ";
                strSQL+=" FROM tbm_cabmovinv as a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON(a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND";
                strSQL+=" a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) ";
                strSQL+=" INNER JOIN tbm_equInv AS a6 ON (a3.co_emp=a6.co_emp AND a3.co_itm=a6.co_itm)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a4.ne_mod=1 and a3.co_emp<>0";
                strSQL+=" ";
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;

                ///////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL;
                strSQL+=" " + strEmp;                
                strSQL+=" GROUP BY a6.co_itmMae,extract(month from a1.fe_doc),a3.tx_codalt,a3.tx_nomitm";
                strSQL+=" )as d ON (a.co_itmMae=d.co_itmMae and a.fe_cot=d.fe_doc)";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar ,a.co_itmMae, a.tx_codalt, a.tx_nomitm, a.anio, a.fe_cot, d.val,";
                strSQL+=" d.nd_stkAct,d.nd_uniVen,d.nd_venTot";
                strSQL+=" )as x";
                strSQL+=" GROUP BY  x.fe_doc,x.co_grp,x.tx_deslar, x.anio";
                strSQL+=" ";
                strSQL+=" ) as B ON (a.fe_doc=b.fe_doc and a.co_grp=b.co_grp and a.anio=b.anio)";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar,a.co_itmMae,a.tx_codalt,a.tx_nomitm,a.nd_stkAct,a.anio,a.fe_doc,";
                strSQL+=" a.val,a.nd_uniVen,a.nd_venTot,";
                strSQL+=" b.valcate,b.valUniVen,b.valVenTot,b.valStkAct";
                strSQL+=" ORDER BY a.tx_deslar,a.co_grp,a.co_itmMae,a.anio,a.fe_doc"; 
                if(chkAgrItmCar.isSelected())
                {
                    strSQL+=" )  as A ";
                    strSQL+=" GROUP BY SUBSTRING(a.tx_codAlt FROM 1 FOR 3),a.anio, a.fe_doc";
                    strSQL+=" ORDER BY SUBSTRING(a.tx_codAlt FROM 1 FOR 3),a.anio,a.fe_doc";
                }
                strSQL+=" "; 
            }
            else //NO ES EMPRESA DE GRUPO/////////////////////////////////////////////////////////////
            { 
                strSQL=" ";
                if(chkAgrItmCar.isSelected())
                {
                    strSQL+=" SELECT SUBSTRING(a.tx_codAlt FROM 1 FOR 3) as tx_codAlt, a.fe_doc ,";
                    strSQL+=" SUM(a.nd_stkAct) as nd_stkAct,SUM(a.val) as val,a.anio,";
                    strSQL+=" SUM(a.nd_uniVen) as nd_uniVen,SUM(a.nd_venTot) as nd_venTot,SUM(a.valCate) as valCate,";
                    strSQL+=" SUM(a.valUniVen) as valUniVen,SUM(a.valvenTot) as valVenTot,SuM(a.valStkAct) as valStkAct,";
                    strSQL+=" '' as tx_desLar, '' as co_itm, '' as co_grp, '' as tx_nomItm";
                    strSQL+=" FROM( ";
                }
                strSQL+=" SELECT a.fe_doc,a.co_grp,a.tx_deslar,a.tx_codalt,a.co_itm,a.tx_nomitm,a.nd_stkAct,a.val,a.anio,a.nd_uniVen,";
                strSQL+=" a.nd_venTot,b.valcate,b.valUniVen, b.valVenTot,b.valStkAct";
                strSQL+=" FROM(";
                strSQL+=" SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar, a.tx_codalt, a.co_itm, a.tx_nomitm,d.nd_stkAct, d.val,a.anio,''as valcate,";
                strSQL+=" '' as valUniVen,'' as valVenTot,'' as valStkAct, d.nd_uniVen, d.nd_venTot ";
                strSQL+=" FROM(SELECT y.co_grp,y.tx_deslar,y.co_itm, x.fe_cot, y.tx_codalt, y.tx_nomitm , x.anio";
                strSQL+=" FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot, extract(Year from c.fe_doc) as anio ";
                strSQL+=" FROM tbm_cabmovinv c "; 
                strSQL+=" WHERE 1=1 ";
                strSQL+=" " + strConSQL2;
                strSQL+=" ) as x ";
                strSQL+=" INNER JOIN (SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END, a.tx_deslar, b.co_itm, b.tx_codalt, b.tx_nomitm";
                strSQL+=" FROM (SELECT PADRE.co_grp,PADRE.tx_deslar, a3.co_itm ";
                strSQL+=" FROM tbm_grpInvImp as PADRE";
                strSQL+=" INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp)";
                strSQL+=" WHERE PADRE.co_grppad IS NULL ";
                strSQL+="" + strAux2; 
                strSQL+=" GROUP BY PADRE.co_grp,PADRE.tx_deslar, HIJO.co_grp, a3.co_grpimp, a3.co_itm";
                strSQL+=" ) as a ";
                strSQL+=" ";
                strSQL+=" RIGHT OUTER JOIN (SELECT distinct a2.co_itm, a3.tx_codalt , a3.tx_nomitm";
                strSQL+=" FROM tbm_cabmovinv as a1 INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND  ";
                strSQL+=" a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND"; 
                strSQL+=" a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)"; 
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm )";
                strSQL+=" WHERE 1=1";
                ////////////////////////////
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;
                //MODIFICADO
                //////////////////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL; 
                ////////////
                strSQL+=" AND a3.st_ser IN ('N', 'S', 'T', 'O')";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')  AND a4.ne_mod=1 )AS b ";
                strSQL+=" ON (a.co_itm=b.co_itm )";
                strSQL+=" WHERE 1=1";
                if(txtCodGru.getText().length()>0) {
                       strSQL+=" and a.co_grp=" +txtCodGru.getText();
                }
                if(txtCodItm.getText().length()>0) {
                       strSQL+=" and b.co_itm=" +txtCodItm.getText();
                }
                strSQL+=" ORDER BY 1";
                strSQL+=" )as y on(x.fe_cot != y.co_itm)";
                strSQL+=" )AS a LEFT OUTER JOIN(SELECT extract (month from a1.fe_doc) as fe_doc,a3.co_itm,a3.nd_stkAct,";
                strSQL+=" sum(-(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C')THEN a2.nd_can ELSE 0 END)*a3.nd_pesItmKgr) as val,";
                strSQL+=" -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C')THEN a2.nd_can ELSE 0 END) AS nd_uniVen,/* UNidades vendidas*/";
                strSQL+=" -SUM(a2.nd_tot) AS nd_venTot	/*Total de dinero por unidades vendidas*/";
                strSQL+=" FROM tbm_cabmovinv as a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc  as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND";
                strSQL+=" a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND";
                strSQL+=" a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a4.ne_mod=1 ";
                strSQL+=" ";
///          ////////
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;
                //////////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL;
                strSQL+=" GROUP BY a3.co_itm,a3.nd_stkAct, extract(month from a1.fe_doc),a3.tx_codalt, a3.tx_nomitm";
                strSQL+=" )as d ON (a.co_itm=d.co_itm and a.fe_cot=d.fe_doc)";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar,a.co_itm,a.tx_codalt,a.tx_nomitm,d.nd_stkAct,a.anio,a.fe_cot,d.val,";
                strSQL+=" d.nd_uniVen,d.nd_venTot";
                strSQL+=" ";
                strSQL+=" )as a LEFT OUTER JOIN (";
                strSQL+=" /* UNION UNION UNION UNION UNION */ "; 
                strSQL+=" SELECT fe_doc, co_grp,tx_deslar,'' as tx_codalt,'' as co_itm,'' as tx_nomitm,'' as nd_stkAct,";
                strSQL+=" anio,'' as val , sum(val) as valCate, sum(nd_uniVen) as valUniVen, sum(nd_venTot) as valVenTot,";
                strSQL+=" sum(nd_stkAct) as valStkAct,'' as nd_uniVen, '' as nd_venTot ";
                strSQL+=" FROM(";
                strSQL+=" SELECT a.fe_cot as fe_doc,a.co_grp,a.tx_deslar,a.tx_codalt,a.co_itm,a.tx_nomitm,d.nd_stkAct,";
                strSQL+=" d.val,a.anio,d.nd_uniVen,d.nd_venTot ";
                strSQL+=" FROM(SELECT y.co_grp,y.tx_deslar,y.co_itm, x.fe_cot, y.tx_codalt, y.tx_nomitm , x.anio";
                strSQL+=" FROM(SELECT distinct extract(month from c.fe_doc) as fe_cot, extract(Year from c.fe_doc) as anio ";
                strSQL+=" FROM tbm_cabmovinv c "; 
                strSQL+=" WHERE 1=1 ";
                strSQL+=" " + strConSQL2;
                strSQL+=" ) as x ";
                strSQL+=" INNER JOIN (SELECT CASE WHEN a.co_grp IS NULL THEN 0 ELSE a.co_grp END, a.tx_deslar, b.co_itm, b.tx_codalt, b.tx_nomitm";
                strSQL+=" FROM (SELECT PADRE.co_grp,PADRE.tx_deslar, a3.co_itm ";
                strSQL+=" FROM tbm_grpInvImp as PADRE";
                strSQL+=" INNER JOIN tbm_grpInvImp as HIJO ON (PADRE.co_grp=HIJO.co_grppad)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a3.co_grpimp=HIJO.co_grp)";
                strSQL+=" WHERE PADRE.co_grppad IS NULL ";
                strSQL+="" + strAux2; 
                strSQL+=" GROUP BY PADRE.co_grp,PADRE.tx_deslar, HIJO.co_grp, a3.co_grpimp, a3.co_itm";
                strSQL+=" ) as a ";
                strSQL+=" ";
                strSQL+=" RIGHT OUTER JOIN (SELECT distinct a2.co_itm, a3.tx_codalt , a3.tx_nomitm";
                strSQL+=" FROM tbm_cabmovinv as a1 INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND  ";
                strSQL+=" a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm )";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a4 ON(a1.co_emp=a4.co_emp AND"; 
                strSQL+=" a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc)"; 
                strSQL+=" WHERE 1=1 ";
/////////

                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;

                ////////////////////////            
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL; 
                strSQL+=" AND a1.st_reg IN ('A','R','C','F') and a4.ne_mod=1  )AS b ";
                strSQL+=" ON (a.co_itm=b.co_itm )";
                strSQL+=" WHERE 1=1";
                if(txtCodGru.getText().length()>0) {
                       strSQL+=" and a.co_grp=" +txtCodGru.getText();
                }
                if(txtCodItm.getText().length()>0) {
                       strSQL+=" and b.co_itm=" +txtCodItm.getText();
                }
                strSQL+=" ";
                strSQL+=" )as y on(x.fe_cot != y.co_itm)";
                strSQL+=" )AS a LEFT OUTER JOIN(SELECT extract (month from a1.fe_doc) as fe_doc , a3.co_itm,a3.nd_stkAct,";
                strSQL+=" sum(-(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                strSQL+=" THEN a2.nd_can ELSE 0 END)*a3.nd_pesItmKgr) as val,";
                strSQL+=" -SUM(CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END) AS nd_uniVen,";
                strSQL+=" -SUM(a2.nd_tot) AS nd_venTot";
                strSQL+=" FROM tbm_cabmovinv as a1 ";
                strSQL+=" INNER JOIN tbm_cabTipDoc  as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND";
                strSQL+=" a1.co_tipDoc=a4.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_cli as a7 ON (a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli)";
                strSQL+=" INNER JOIN tbm_detmovinv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND";
                strSQL+=" a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                strSQL+=" WHERE a1.st_reg IN ('A','R','C','F') AND a4.ne_mod=1";
                strSQL+=" ";
///////////////
                strAux4="";
                    if (chkVenCli.isSelected())
                    {
                        strAux4+="a4.co_cat IN (3, 4) AND a7.co_empGrp IS NULL";
                    }
                    if (chkVenRel.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (3, 4) AND a7.co_empGrp IS NOT NULL";
                    }
                    if (chkPre.isSelected())
                    {
                        strAux4+=(strAux4.equals("")?"": " OR ") + "a4.co_cat IN (23)";
                    }
                strAux3=" AND (" + strAux4 + ")";
                strSQL+=" " + strAux3;

                ///////////////////
                strSQL+=" " + strAux;
                strSQL+=" " + strConSQL;
                strSQL+=" GROUP BY a3.co_itm,a3.nd_stkAct, extract(month from a1.fe_doc),a3.tx_codalt, a3.tx_nomitm";
                strSQL+=" )as d ON (a.co_itm=d.co_itm and a.fe_cot=d.fe_doc)";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar ,a.co_itm, a.tx_codalt, a.tx_nomitm, a.anio, a.fe_cot, d.val,d.nd_stkAct,d.nd_uniVen,d.nd_venTot";
                strSQL+=" ) as x";
                strSQL+=" GROUP BY  fe_doc,co_grp,tx_deslar, anio";
                strSQL+=" ";
                strSQL+=" ) as B ON (a.fe_doc=b.fe_doc and a.co_grp=b.co_grp and a.anio=b.anio) ";
                strSQL+=" GROUP BY a.co_grp,a.tx_deslar,a.co_itm,a.tx_codalt,a.tx_nomitm,a.nd_stkAct,a.anio,a.fe_doc,";
                strSQL+=" a.val,a.nd_uniVen,a.nd_venTot,b.valcate,b.valUniVen,b.valVenTot,b.valStkAct";
                strSQL+=" ORDER BY a.tx_deslar,a.co_grp,a.co_itm,a.anio,a.fe_doc";         
                if(chkAgrItmCar.isSelected())
                {
                    strSQL+=" )  as A ";
                    strSQL+=" GROUP BY SUBSTRING(a.tx_codAlt FROM 1 FOR 3),a.anio, a.fe_doc";
                    strSQL+=" ORDER BY SUBSTRING(a.tx_codAlt FROM 1 FOR 3),a.anio,a.fe_doc";
                }
                strSQL+=" "; 
            }
            System.out.println("strConSQL2::::::::...." + strConSQL2);
            System.out.println();
            System.out.println("ZafVen33 "+strSQL);
            rst=stm.executeQuery(strSQL);
            vecDat.clear();
            lblMsgSis.setText("Cargando datos...");
            i=0;	   	
            vecFecha=new Vector(); 
            vecCodGru=new Vector();
            vecCodGru1=new Vector();
            vecNomGru=new Vector();
            vecCodItm=new Vector();
            vecCodAltItm=new Vector();
            vecNomItm=new Vector();
            vecVal=new Vector();
            vecAnio=new Vector();
            vecTotales=new Vector();
            vecTotGru=new Vector();
            vecUniVen=new Vector();
            vecValUniVen = new Vector();
            vecStock = new Vector();
            vecTotUniVen=new Vector();
            vecTotValUniVen= new Vector();
            vecTotStock=new Vector();
            while(rst.next()){
                if (blnCon){
                   vecFecha.add(j,rst.getString("fe_doc"));
                   if(rst.getString("co_grp")!=null)vecCodGru.add(j,rst.getString("co_grp"));
                   else vecCodGru.add(j,"0");
                   vecNomGru.add(j,rst.getString("tx_deslar"));
                   if(rst.getString("co_itm")!=null)vecCodItm.add(j,rst.getString("co_itm"));
                   else vecCodItm.add(j,"0");
                   vecCodAltItm.add(j,rst.getString("tx_codalt"));
                   vecNomItm.add(j,rst.getString("tx_nomitm"));
                   if(rst.getString("val")!=null)vecVal.add(j,rst.getString("val"));
                   else vecVal.add(j,"0");
                   vecTotales.add(j,rst.getString("valcate"));
                   vecAnio.add(j,rst.getString("anio"));
                   if(rst.getString("nd_uniVen")!=null) vecUniVen.add(j,rst.getString("nd_uniVen"));
                   else vecUniVen.add(j,"0");
                   if(rst.getString("nd_venTot")!=null) vecValUniVen.add(j,rst.getString("nd_venTot"));
                   else vecValUniVen.add(j,"0");
                   if(rst.getString("nd_stkAct")!=null)vecStock.add(j,rst.getString("nd_stkAct"));
                   else vecStock.add(j,"0");
                   if(rst.getString("valStkAct")!=null) vecTotStock.add(j,rst.getString("valStkAct"));
                   else vecTotStock.add(j,"0");
                   ////
                   if(rst.getString("valUniVen")!=null) vecTotUniVen.add(j,rst.getString("valUniVen"));
                   else vecTotUniVen.add(j,"0");
                   if(rst.getString("valVenTot")!=null) vecTotValUniVen.add(j,rst.getString("valVenTot"));
                   else vecTotValUniVen.add(j,"0");
                   //// 
                   temp++;
                   j++;
                    
                }
                else{
                    break;
                }
           }
     }
            int jota=0;
            if(!vecFecha.isEmpty()){
                int j1=0;
                String zxc="";
                zxc=vecFecha.get(0).toString();//solo una fecha
                while(j>j1){
                    if(zxc.equals(vecFecha.get(j1).toString()) ){
                        vecCodGru1.add(vecCodGru.get(j1).toString());
                        jota++; 
                    }
                    j1++;
                }
            }          
            int cont=0;
    if(!vecFecha.isEmpty())
    {
          if(temp>0)
          {
                String str="";
                str=vecCodAltItm.get(0).toString();
                for(i=0;i<temp;i++){
                    if(vecCodAltItm.get(i).equals(str)){
                        str=vecCodAltItm.get(i).toString();
                        vecTotGru.add(cont,"0");
                        cont++;
                    }
                }
                int registros=temp/cont;
                configurarTblDatMos(cont);
                int m=0,contVal=0,posVal=6, z=0,tot=0,contTot=0, reg=0;
                double acumD=0,acumG=0;
                double acumUniVen=0, acumTotUniVen=0, acumValUniVen=0, acumTotValUniVen=0, acumStock=0, acumTotStock=0;
                    // LO NORMAL CON CHK_ON 
                if(chkExcVenEmp.isSelected())
                {
                     while(j>m){ 
                        vecReg= new Vector(); 
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                        if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                        vecReg.add(INT_TBL_DAT_COD_ITM,vecCodItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_COD_ALT,vecCodAltItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_NOM_ITM,vecNomItm.get(m).toString());
                        while(cont>z)
                        {
                            if(vecUniVen.get(contVal)!=null){
                                vecReg.add(posVal,vecUniVen.get(contVal).toString());
                                acumUniVen=acumUniVen+Double.parseDouble(vecUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecValUniVen.get(contVal)!=null){
                                   vecReg.add(posVal,vecValUniVen.get(contVal).toString());
                                   acumValUniVen=acumValUniVen+Double.parseDouble(vecValUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecVal.get(contVal)!=null){
                                   vecReg.add(posVal,vecVal.get(contVal).toString());
                                   acumD=acumD+Double.parseDouble(vecVal.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecStock.get(contVal)!=null){
                                   vecReg.add(posVal,vecStock.get(contVal).toString());
                                   acumStock=acumStock+Double.parseDouble(vecStock.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            /////////
                            contVal++;
                            z++;
                        }
                        vecReg.add(posVal,acumUniVen);posVal++;
                        vecReg.add(posVal,acumValUniVen);posVal++;
                        vecReg.add(posVal,acumD);posVal++;
                        vecReg.add(posVal,acumStock);
                        vecDat.add(vecReg);
                        posVal=6;
                        z=0;
                        acumG=0;
                        acumTotUniVen=0;acumTotValUniVen=0;acumTotStock=0;
                        contTot=contVal-cont;
                        if(registros>reg+1) 
                        {
                            if(!vecCodGru.get(m).toString().equals(vecCodGru.get(m+cont).toString()))
                            {
                                vecReg= new Vector(); 
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                                if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                                vecReg.add(INT_TBL_DAT_COD_ITM,"");
                                vecReg.add(INT_TBL_DAT_COD_ALT,"");
                                vecReg.add(INT_TBL_DAT_NOM_ITM,"TOTAL GRUPO: ");
                                while(cont>z)
                                {
                                    if(vecTotUniVen.get(contTot)!=null){
                                        vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                                        acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotValUniVen.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                                           acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotales.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotales.get(contTot).toString());
                                           acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotStock.get(contTot)!=null){
                                            vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                            acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                                     }
                                     else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    contTot++;
                                    z++;
                                }
                                vecReg.add(posVal,acumTotUniVen);posVal++;
                                vecReg.add(posVal,acumTotValUniVen);posVal++;
                                vecReg.add(posVal,acumG);posVal++;
                                vecReg.add(posVal,acumTotStock);
                                if(!chkAgrItmCar.isSelected())
                                vecDat.add(vecReg);
                            }
                        }
                        z=0;
                        m=m+cont;
                        acumD=0;
                        acumUniVen=0;acumValUniVen=0;acumStock=0;
                        posVal=6;
                        reg++;
                    }
                    m=m-cont;
                    vecReg= new Vector(); 
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());    }
                    else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                    if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                    else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                    vecReg.add(INT_TBL_DAT_COD_ITM,"");
                    vecReg.add(INT_TBL_DAT_COD_ALT,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,"TOTAL GRUPO: ");
                    while(cont>z)
                    {
                        if(vecTotUniVen.get(contTot)!=null){
                            vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                            acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                        }
                        else{vecReg.add(posVal,"0");}
                        posVal++;
                        if(vecTotValUniVen.get(contTot)!=null){
                               vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                               acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                        }
                        else{vecReg.add(posVal,"0");}
                        posVal++;
                        if(vecTotales.get(contTot)!=null){
                               vecReg.add(posVal,vecTotales.get(contTot).toString());
                               acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                        }
                        else{vecReg.add(posVal,"0");}
                        posVal++;
                        if(vecTotStock.get(contTot)!=null){
                                vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                         }
                         else{vecReg.add(posVal,"0");}
                        posVal++;
                        contTot++;
                        z++;
                    }
                    vecReg.add(posVal,acumTotUniVen);posVal++;
                    vecReg.add(posVal,acumTotValUniVen);posVal++;
                    vecReg.add(posVal,acumG);posVal++;
                    vecReg.add(posVal,acumTotStock);
                    if(!chkAgrItmCar.isSelected())
                        vecDat.add(vecReg);
                    z=0;
               }
               else if (!chkExcVenEmp.isSelected() && !chkAgrItmCar.isSelected())// por grupo de items!!
               {
                   while(j>m){ 
                        vecReg= new Vector(); 
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                        if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                        vecReg.add(INT_TBL_DAT_COD_ITM,vecCodItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_COD_ALT,vecCodAltItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_NOM_ITM,vecNomItm.get(m).toString());
                        while(cont>z)
                        {
                            if(vecUniVen.get(contVal)!=null){
                                vecReg.add(posVal,vecUniVen.get(contVal).toString());
                                acumUniVen=acumUniVen+Double.parseDouble(vecUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecValUniVen.get(contVal)!=null){
                                   vecReg.add(posVal,vecValUniVen.get(contVal).toString());
                                   acumValUniVen=acumValUniVen+Double.parseDouble(vecValUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecVal.get(contVal)!=null){
                                   vecReg.add(posVal,vecVal.get(contVal).toString());
                                   acumD=acumD+Double.parseDouble(vecVal.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecStock.get(contVal)!=null){
                                   vecReg.add(posVal,vecStock.get(contVal).toString());
                                   acumStock=acumStock+Double.parseDouble(vecStock.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            /////////
                            contVal++;
                            z++;
                        }
                        vecReg.add(posVal,acumUniVen);posVal++;
                        vecReg.add(posVal,acumValUniVen);posVal++;
                        vecReg.add(posVal,acumD);posVal++;
                        vecReg.add(posVal,acumStock);
                        //vecDat.add(vecReg);
                        posVal=6;
                        z=0;
                        acumG=0;
                        acumTotUniVen=0;acumTotValUniVen=0;acumTotStock=0;
                        contTot=contVal-cont;
                        if(registros>reg+1) 
                        {
                            if(!vecCodGru.get(m).toString().equals(vecCodGru.get(m+cont).toString()))
                            {
                                vecReg= new Vector(); 
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                                if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                                vecReg.add(INT_TBL_DAT_COD_ITM,"");
                                vecReg.add(INT_TBL_DAT_COD_ALT,"");
                                vecReg.add(INT_TBL_DAT_NOM_ITM,"");
                                while(cont>z)
                                {
                                    if(vecTotUniVen.get(contTot)!=null){
                                        vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                                        acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotValUniVen.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                                           acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotales.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotales.get(contTot).toString());
                                           acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotStock.get(contTot)!=null){
                                            vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                            acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                                     }
                                     else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    contTot++;
                                    z++;
                                }
                                vecReg.add(posVal,acumTotUniVen);posVal++;
                                vecReg.add(posVal,acumTotValUniVen);posVal++;
                                vecReg.add(posVal,acumG);posVal++;
                                vecReg.add(posVal,acumTotStock);
                                vecDat.add(vecReg);
                            }
                        }
                        z=0;
                        m=m+cont;
                        acumD=0;
                        acumUniVen=0;acumValUniVen=0;acumStock=0;
                        posVal=6;
                        reg++;
                   }
                   m=m-cont;
                   vecReg= new Vector(); 
                   vecReg.add(INT_TBL_DAT_LIN,"");
                   if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                   else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                   if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                   else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                   vecReg.add(INT_TBL_DAT_COD_ITM,"");
                   vecReg.add(INT_TBL_DAT_COD_ALT,"");
                   vecReg.add(INT_TBL_DAT_NOM_ITM,"");
                   while(cont>z)
                   {
                        if(vecTotUniVen.get(contTot)!=null){
                                        vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                                        acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotValUniVen.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                                           acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotales.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotales.get(contTot).toString());
                                           acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotStock.get(contTot)!=null){
                                            vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                            acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                                     }
                                     else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    contTot++;
                                    z++;
                    }
                    vecReg.add(posVal,acumTotUniVen);posVal++;
                    vecReg.add(posVal,acumTotValUniVen);posVal++;
                    vecReg.add(posVal,acumG);posVal++;
                    vecReg.add(posVal,acumTotStock);
                    vecDat.add(vecReg);
                    z=0;
                }
               else if(!chkExcVenEmp.isSelected() && chkAgrItmCar.isSelected())
               {
                   while(j>m){ 
                        vecReg= new Vector(); 
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                        if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                        else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                        vecReg.add(INT_TBL_DAT_COD_ITM,vecCodItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_COD_ALT,vecCodAltItm.get(m).toString());
                        vecReg.add(INT_TBL_DAT_NOM_ITM,vecNomItm.get(m).toString());
                        while(cont>z)
                        {
                            if(vecUniVen.get(contVal)!=null){
                                vecReg.add(posVal,vecUniVen.get(contVal).toString());
                                acumUniVen=acumUniVen+Double.parseDouble(vecUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecValUniVen.get(contVal)!=null){
                                   vecReg.add(posVal,vecValUniVen.get(contVal).toString());
                                   acumValUniVen=acumValUniVen+Double.parseDouble(vecValUniVen.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecVal.get(contVal)!=null){
                                   vecReg.add(posVal,vecVal.get(contVal).toString());
                                   acumD=acumD+Double.parseDouble(vecVal.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecStock.get(contVal)!=null){
                                   vecReg.add(posVal,vecStock.get(contVal).toString());
                                   acumStock=acumStock+Double.parseDouble(vecStock.get(contVal).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            /////////
                            contVal++;
                            z++;
                        }
                        vecReg.add(posVal,acumUniVen);posVal++;
                        vecReg.add(posVal,acumValUniVen);posVal++;
                        vecReg.add(posVal,acumD);posVal++;
                        vecReg.add(posVal,acumStock);
                        vecDat.add(vecReg);
                        posVal=6;
                        z=0;
                        acumG=0;
                        acumTotUniVen=0;acumTotValUniVen=0;acumTotStock=0;
                        contTot=contVal-cont;
                        if(registros>reg+1) 
                        {
                            if(!vecCodGru.get(m).toString().equals(vecCodGru.get(m+cont).toString()))
                            {
                                vecReg= new Vector(); 
                                vecReg.add(INT_TBL_DAT_LIN,"");
                                if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                                if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                                else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                                vecReg.add(INT_TBL_DAT_COD_ITM,"");
                                vecReg.add(INT_TBL_DAT_COD_ALT,"");
                                vecReg.add(INT_TBL_DAT_NOM_ITM,"");
                                while(cont>z)
                                {
                                    if(vecTotUniVen.get(contTot)!=null){
                                        vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                                        acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotValUniVen.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                                           acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotales.get(contTot)!=null){
                                           vecReg.add(posVal,vecTotales.get(contTot).toString());
                                           acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                                    }
                                    else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    if(vecTotStock.get(contTot)!=null){
                                            vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                            acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                                     }
                                     else{vecReg.add(posVal,"0");}
                                    posVal++;
                                    contTot++;
                                    z++;
                                }
                                vecReg.add(posVal,acumTotUniVen);posVal++;
                                vecReg.add(posVal,acumTotValUniVen);posVal++;
                                vecReg.add(posVal,acumG);posVal++;
                                vecReg.add(posVal,acumTotStock);
                               // vecDat.add(vecReg);
                            }
                        }
                        z=0;
                        m=m+cont;
                        acumD=0;
                        acumUniVen=0;acumValUniVen=0;acumStock=0;
                        posVal=6;
                        reg++;
                   }
                   m=m-cont;
                   vecReg= new Vector(); 
                   vecReg.add(INT_TBL_DAT_LIN,"");
                   if(vecCodGru.get(m)!=null){vecReg.add(INT_TBL_DAT_COD_GRU,vecCodGru.get(m).toString());}
                   else{vecReg.add(INT_TBL_DAT_COD_GRU,"0");}
                   if(vecNomGru.get(m)!=null){vecReg.add(INT_TBL_DAT_NOM_GRU,vecNomGru.get(m).toString());}
                   else{vecReg.add(INT_TBL_DAT_NOM_GRU,"(Sin Grupo)");}
                   vecReg.add(INT_TBL_DAT_COD_ITM,"");
                   vecReg.add(INT_TBL_DAT_COD_ALT,"");
                   vecReg.add(INT_TBL_DAT_NOM_ITM,"");
                   while(cont>z)
                   {
                        if(vecTotUniVen.get(contTot)!=null){
                                        vecReg.add(posVal,vecTotUniVen.get(contTot).toString());
                                        acumTotUniVen=acumTotUniVen+Double.parseDouble(vecTotUniVen.get(contTot).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecTotValUniVen.get(contTot)!=null){
                                   vecReg.add(posVal,vecTotValUniVen.get(contTot).toString());
                                   acumTotValUniVen=acumTotValUniVen+Double.parseDouble(vecTotValUniVen.get(contTot).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecTotales.get(contTot)!=null){
                                   vecReg.add(posVal,vecTotales.get(contTot).toString());
                                   acumG+=Double.parseDouble(vecTotales.get(contTot).toString());
                            }
                            else{vecReg.add(posVal,"0");}
                            posVal++;
                            if(vecTotStock.get(contTot)!=null){
                                    vecReg.add(posVal,vecTotStock.get(contTot).toString());
                                    acumTotStock=acumTotStock+Double.parseDouble(vecTotStock.get(contTot).toString());
                             }
                             else{vecReg.add(posVal,"0");}
                            posVal++;
                            contTot++;
                            z++;
                    }
                    vecReg.add(posVal,acumTotUniVen);posVal++;
                    vecReg.add(posVal,acumTotValUniVen);posVal++;
                    vecReg.add(posVal,acumG);posVal++;
                    vecReg.add(posVal,acumTotStock);
                 //   vecDat.add(vecReg);
                    z=0;
               }
             objTblMod.setData(vecDat);
             tblDat.setModel(objTblMod);
                    
                    //////////
                    //mod 15/agos/2013
                vecDat=null;
                vecReg=null;
                vecEstReg=null;
                vecFecha=null;
                vecCodGru=null;
                vecCodGru1=null;
                vecNomGru=null;
                vecCodItm=null;
                vecCodAltItm=null;
                vecNomItm=null;
                vecVal=null;
                vecAnio=null;
                vecTotales=null;
                vecTotGru=null;
                vecUniVen=null;
                vecValUniVen=null;
                vecStock=null;
                vecTotUniVen=null;
                vecTotStock=null;
                    /////////////
                    //Calcular totales.
                calcularTotales2();
                    
                if(blnCon){lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");}
                else{lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");}
             }   
        }
        else
        {
            lblMsgSis.setText("No Se encontraron registros.");
            configurarTblDat();
        }
        rst.close();
        stm.close();
        con.close();
        rst=null;
        stm=null;
        con=null;
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
    }       
   catch (java.sql.SQLException e){
       blnRes=false;
       objUti.mostrarMsgErr_F1(this, e);
   }
   catch (Exception e){
       blnRes=false;
       objUti.mostrarMsgErr_F1(this, e);
   }
   return blnRes;
}


    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
        //    System.out.println("getConSQLAdiCamTer");
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
    
    
//    /**
//     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    
    private void mostrarMsgInf(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
//    /**
//     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si y No. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    
//    /**
//     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
//     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
//     * y que debe comunicar de este particular al administrador del sistema.
//     */
   
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }



//    /**
//     * Esta función configura la "Ventana de consulta" que será utilizada para
//     * mostrar los "Vendedores".
//     */
    
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
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario()==1){
                 if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                 {
                      strSQL=" ";
                      strSQL+=" SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                      strSQL+=" FROM tbr_locPrgUsr as a1 ";
                      strSQL+=" INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                      strSQL+=" INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                      strSQL+=" INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                      strSQL+=" WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                      if(txtCodEmp.getText().length()>0)
                          strSQL+=" and a4.co_emp=" + txtCodEmp.getText();
                      if(txtCodLoc.getText().length()>0)
                          strSQL+=" and a4.co_loc=" + txtCodLoc.getText();
                      strSQL+=" GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                      strSQL+=" ORDER BY a2.tx_nom";            
                   }
                 else
                 {
                    strSQL=" ";
                      strSQL+=" SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                      strSQL+=" FROM tbr_locPrgUsr as a1 ";
                      strSQL+=" INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                      strSQL+=" INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                      strSQL+=" INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                      strSQL+=" WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                      strSQL+=" and a4.co_emp="+ objParSis.getCodigoEmpresa();
                    if(txtCodEmp.getText().length()>0)
                        strSQL+=" and a4.co_emp=" + txtCodEmp.getText();
                    if(txtCodLoc.getText().length()>0)
                        strSQL+=" and a4.co_loc=" + txtCodLoc.getText();
                    strSQL+=" GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                    strSQL+=" ORDER BY a2.tx_nom";          
                 }   
                }
                else{
               strSQL=" ";
                strSQL+=" SELECT DISTINCT a2.co_usr, a2.tx_usr, a2.tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a1 ";
                strSQL+=" INNER JOIN tbr_locusr as a4 ON(a1.co_emprel=a4.co_emp AND a1.co_locrel=a4.co_loc)";
                strSQL+=" INNER JOIN tbm_usr as a2 ON (a4.co_usr=a2.co_usr)";
                strSQL+=" INNER JOIN tbr_usremp as a3 ON (a2.co_usr=a3.co_usr AND a3.co_emp=a4.co_emp)";
                strSQL+=" WHERE a3.st_ven='S' and a2.st_reg='A' and a3.co_emp>0";
                strSQL+=" ";
                strSQL+=" and a1.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" and a1.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" and a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal();
                if(txtCodEmp.getText().length()>0)
                    strSQL+=" and a4.co_emp=" + txtCodEmp.getText();
                if(txtCodLoc.getText().length()>0)
                    strSQL+=" and a4.co_loc=" + txtCodLoc.getText();
                strSQL+=" GROUP BY a2.co_usr,a2.tx_usr,a2.tx_nom ";
                strSQL+=" ORDER BY a2.tx_nom";            
             }   
        //    System.out.println("vendedores...." + strSQL);
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
    
    //copia
    private boolean configurarEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
             if (objParSis.getCodigoUsuario()==1)
            {
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom";
                    strSQL+=" FROM tbm_Emp AS a1";
                    strSQL+=" WHERE a1.st_reg like 'A' and a1.co_emp>0";
                    strSQL+=" ORDER BY a1.co_emp";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp as co_emp,a1.tx_nom as tx_nom";
                    strSQL+=" FROM tbm_Emp AS a1";
                    strSQL+=" WHERE a1.st_reg like 'A' and co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.co_emp";
                }
            }
            else
            {
                 strSQL="";
                strSQL+=" SELECT DISTINCT a4.co_emp as co_emp, a4.tx_nom as tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a2";
                strSQL+=" INNER JOIN tbm_loc as a3 ON(a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc)";
                strSQL+=" INNER JOIN tbm_emp as a4 ON(a2.co_emprel=a4.co_emp)";
                strSQL+=" WHERE 1=1 and a2.st_reg in ('A','P')";
                strSQL+=" and a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" and a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" and a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a4.co_emp";
          }
            // System.out.println("EMPRESAS::..." + strSQL);
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //add
   private boolean configurarLocal(){
        boolean blnRes=true;
        String aux="";
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.");
            arlAli.add("Cód.Loc.");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("5");
            arlAncCol.add("50");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){ 
               if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                   strSQL="";
                   strSQL+=" SELECT a1.co_emp,a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0";
                    if (txtCodEmp.getText().length()>0) {
                            strSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                    }
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc";
               }
               else{
                   strSQL="";
                   strSQL+=" SELECT a1.co_emp, a1.co_loc ,a1.tx_nom";
                    strSQL+=" FROM tbm_Loc AS a1";
                    strSQL+=" WHERE a1.co_emp>0 and a1.co_emp=" + objParSis.getCodigoEmpresa();
                    if (txtCodEmp.getText().length()>0) {
                            strSQL+=" and a1.co_emp=" + txtCodEmp.getText();
                    }
                    strSQL+=" ORDER BY a1.co_emp,a1.co_loc";
               }
           }
           else{
                strSQL="";
                strSQL+=" SELECT DISTINCT a3.co_emp, a3.co_loc as co_loc, a3.tx_nom as tx_nom ";
                strSQL+=" FROM tbr_locPrgUsr as a2";
                strSQL+=" INNER JOIN tbm_loc as a3 ON (a2.co_emprel=a3.co_emp and a2.co_locrel=a3.co_loc)";
                strSQL+=" INNER JOIN tbm_emp as a4 ON (a2.co_emprel=a4.co_emp)";
                strSQL+=" WHERE a2.st_reg in ('A','P')";
                strSQL+=" and a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" and a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" and a2.co_mnu=" + objParSis.getCodigoMenu();
           if (txtCodEmp.getText().length()>0){
               strSQL+=" and a2.co_emprel=" + txtCodEmp.getText();
           }
           strSQL+=" ORDER BY a3.co_emp,a3.co_loc";
         }
          //   System.out.println("LOCALES...."+ strSQL);
            vcoLoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Locales", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoLoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //add
   private boolean configurarGrupo()
    {
        boolean blnRes=true;
        try
        {
            //select co_grp, tx_deslar from tbm_grpInvImp
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("374");
            //Armar la sentencia SQL.
                 //select co_grp, tx_deslar from tbm_grpInvImp
            strSQL="";
            strSQL+="SELECT distinct a1.co_grp,a1.tx_deslar";
            strSQL+=" FROM tbm_grpInvImp AS a1";
            strSQL+=" WHERE a1.st_reg like 'A' and a1.co_grppad is null";
            strSQL+=" ORDER BY a1.tx_deslar";
            vcoGru=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Grupos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoGru.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    //add
   private boolean configurarItem()
{
        boolean blnRes=true;
        String aux="";
        try
        {
           //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Alterno");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_codAlt";
        //    System.out.println("Inventario:.. "+strSQL);
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
       }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
}
    
    
 
//    /**
//     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
//     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
//     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
//     * exista lo que se está buscando) o presentar la ventana de consulta para que
//     * el usuario seleccione la opción que desea utilizar.
//     * @param intTipBus El tipo de búsqueda a realizar.
//     * @return true: Si no se presentó ningún problema.
//     * <BR>false: En el caso contrario.
//     */
   
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
    
    //copia
    private boolean mostrarEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoEmp.setVisible(true);
                    if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));//selecciona de popup
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.setVisible(true);
                        if (vcoEmp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else
                        {
                            txtNomEmp.setText(strNomEmp);
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
    
    //add
    private boolean mostrarLocal(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoLoc.setVisible(true);
                    if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                           txtCodEmpLoc.setText(vcoLoc.getValueAt(1));
                            txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                            txtNomLoc.setText(vcoLoc.getValueAt(3));
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
                        txtCodEmpLoc.setText(vcoLoc.getValueAt(1));
                        txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                        txtNomLoc.setText(vcoLoc.getValueAt(3));
                    } 
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.setVisible(true);
                        if (vcoLoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodEmpLoc.setText(vcoLoc.getValueAt(1));
                            txtCodLoc.setText(vcoLoc.getValueAt(2));//selecciona de popup
                            txtNomLoc.setText(vcoLoc.getValueAt(3));
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
    
    //add
    private boolean mostrarGrupo(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: 
                    vcoGru.setVisible(true);
                    if (vcoGru.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodGru.setText(vcoGru.getValueAt(1));//selecciona de popup
                        txtNomGru.setText(vcoGru.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoGru.buscar("a1.co_grp", txtCodGru.getText()))
                    {
                        txtCodGru.setText(vcoGru.getValueAt(1));
                        txtNomGru.setText(vcoGru.getValueAt(2));
                    }
                    else
                    {
                        vcoGru.setCampoBusqueda(0);
                        vcoGru.setCriterio1(11);
                        vcoGru.cargarDatos();
                        vcoGru.setVisible(true);
                        if (vcoGru.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodGru.setText(vcoGru.getValueAt(1));
                            txtNomGru.setText(vcoGru.getValueAt(2));
                        }
                        else
                        {
                            txtCodGru.setText(strCodGru);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoGru.buscar("a1.tx_deslar", txtNomGru.getText()))
                    {
                        txtCodGru.setText(vcoGru.getValueAt(1));
                        txtNomGru.setText(vcoGru.getValueAt(2));
                    }
                    else
                    {
                        vcoGru.setCampoBusqueda(2);
                        vcoGru.setCriterio1(11);
                        vcoGru.cargarDatos();
                        vcoGru.setVisible(true);
                        if (vcoGru.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodGru.setText(vcoGru.getValueAt(1));
                            txtNomGru.setText(vcoGru.getValueAt(2));
                        }
                        else
                        {
                            txtNomGru.setText(strNomGru);
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
    
    //add
    private boolean mostrarItem(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
              switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtNomItm.setText(vcoItm.getValueAt(3));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==vcoItm.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtNomItm.setText(vcoItm.getValueAt(3));
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
    
  

   
 
//    /**
//     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
//     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
//     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
//     * llevan a cabo los procesos se podría presentar mensajes informativos en un JLabel e
//     * ir incrementando un JProgressBar con lo cual el usuario estaría informado en todo
//     * momento de lo que ocurre. Si se desea hacer ésto es necesario utilizar ésta clase
//     * ya que si no sólo se apreciaría los cambios cuando ha terminado todo el proceso.
//     */
    
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
        //    System.out.println("Run");
         //   configurarTblDatMos();   // APARECE NUEVA TABLA LISTA PARA SER LLENADA XD 
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
    

    
//    /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            int b=intCol;
            String strMsg="";
            if (intCol>INT_TBL_DAT_NOM_ITM) {
                intCol=(intCol - 6) % columnaPorcentual + 6;
            }
            switch (intCol)
            {
                case INT_TBL_DAT_COD_GRU://1
                    strMsg="Código del Grupo";
                    break;
                case INT_TBL_DAT_NOM_GRU://2
                    strMsg="Nombre del grupo";
                    break;
                case INT_TBL_DAT_COD_ITM:   //3
                    strMsg="Código del Item";
                    break;
                case INT_TBL_DAT_COD_ALT:  //4
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM: //5
                    strMsg="Nombre del Item";
                    break;
                case 6:
                    if(b!=6)
                    strMsg="Total";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    public boolean calcularTotales2()
    {
  //      System.out.println("calcularTotales2");
          int intNumFil, intNumCol, i, j;
          boolean blnRes=true;
          try{
                intNumFil=tblDat.getRowCount();
                intNumCol=intColTot.length;
                double dblTot[]=new double[intNumCol];
                for(i=0; i<intNumFil; i++){
                    for(j=0; j<intNumCol; j++)
                    {    dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, intColTot[j]));}
                }
                for(j=0; j<intNumCol; j++){ 
                    
                    if (chkExcVenEmp.isSelected())
                    {
                        tblTot.setValueAt("" + objUti.redondear(dblTot[j]/2, intNumDec), 0, intColTot[j]);
                    }
                    else
                    {
                        tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, intColTot[j]);
                    }
               }
                //cabeceras de columnas 
                tblTot.setValueAt(" ", 0, 0);
            }
            catch (Exception e)
            {
                blnRes=false;
            }
            return blnRes;
    }
}