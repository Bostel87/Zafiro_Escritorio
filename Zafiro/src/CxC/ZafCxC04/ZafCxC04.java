/*
 * ZafCxC04.java
 *
 * Created on 6 de septiembre de 2004, 15:54
 * DARIO CARDENAS MODIFICO EL PROGRAMA EL 25/MARZOJULIO/2008
 * DOCUMENTOS POSTFECHADOS APLICADO A VARIOS DOCUMENTOS 
 * VERSION V 2.0
 */


package CxC.ZafCxC04;//hola
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafVenCon.ZafVenCon;
//Eddye_ventana para documentos pendientes//
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafVenCon.ZafVenConCxC01;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.util.ArrayList;
/**
 *
 * @author  dCardenas
 */
public class ZafCxC04 extends javax.swing.JInternalFrame 
{
    final int INT_TBL_LINEA        =0;            //LINEA DE NUMERACION DE CANTIDAD DE REGISTROS///0
    final int INT_TBL_SEL          =1;            //LINEA DE SELECCION DE FACTURAS///1
    final int INT_TBL_COD_LOC      =2;            //Codigo de LOCAL///2
    final int INT_TBL_COD_TIP_DOC  =3;            //CODIGO DEL TIPO DE DOCUMENTO///3
    final int INT_TBL_NOM_TIP_DOC  =4;            //NOMBRE DEL TIPO DE DOCUMENTO///4
    final int INT_TBL_COD_DOC      =5;            //CODIGO DEL DOCUMENTO///5
    final int INT_TBL_COD_REG      =6;            //CODIGO DE REGISTRO///6
    final int INT_TBL_NUM_DOC      =7;           //NUMERO DEL DOCUMENTO///7
    final int INT_TBL_FEC_DOC      =8;           //FECHA DE DOCUMENTO///8
    final int INT_TBL_DIA_CRE      =9;           //DIAS DE CREDITO///9
    final int INT_TBL_FEC_VEN      =10;           //FECHA DE VENCIMIENTO///10
    final int INT_TBL_VAL_DOC      =11;           //VALOR DE DOCUMENTO///11
    final int INT_TBL_VAL_PEN      =12;           //VALOR PENDIENTE DE DOCUMENTO///12
    final int INT_TBL_VAL_CHQ      =13;           //FECHA DE RECEPCION DEL CHEQUE///13
    final int INT_TBL_EST_REG      =14;           //ESTADO DE REGISTRO///14
    final int INT_TBL_COD_BAN      =15;           //CODIGO DEL BANCO///15
    final int INT_TBL_DES_BAN      =16;           //DESCRIPCION DEL BANCO///16
    final int INT_TBL_NOM_BAN      =17;           //NOMBRE DEL BANCO///17
    final int INT_TBL_NUM_CTA      =18;           //NUMERO DE CUENTA///18
    final int INT_TBL_NUM_CHQ      =19;           //NUMERO DE CHEQUE///19
    final int INT_TBL_FEC_REC_CHQ  =20;           //FECHA DE RECEPCION DEL CHEQUE///20
    final int INT_TBL_FEC_VEN_CHQ  =21;           //FECHA DE VENCIMIENTO DEL  CHEQUE///21
    

    
    final int INT_GRP_BCO = 8;
    
    
    boolean blnCambios = false; //Detecta ke se hizo cambios en el documento
    //    final int varTipDoc=12;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    private Librerias.ZafDate.ZafDatePicker txtFecVen;
        javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon, strSQLA, strSQLB, strSQLC;
    private Vector vecDat, vecValPen, vecCab;
    private boolean blnHayCam;          //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNum;
    private ZafPopupMenu objPopMnu;
    private ZafThreadGUI objThrGUI;
    //private ZafTableModel objTblMod;
    //private ZafDefTblCelRen objDefTblCelRen;    //Formatear columnas en JTable.
    ///private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private Vector vecTipCta, vecNatCta, vecEstReg, vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodPro, strDesLarPro,strIdePro, strDirPro,strDesCorCta,strDesLarCta;    

    private ZafVenCon vcoTipDoc, vcoBan, vcoLoc, vcoCli;                //Ventana de consulta "Tipo de documento".
    
    private java.util.Vector vecReg;
    //private mitoolbar objTooBar;
    private String sSQLCon;
    private java.sql.Connection conCab, con, conCnsDet,conAnu, conRee, conIns, conReeA, conReeB, conReeC;

    private java.sql.Statement stmCab, stm, stmCnsDet, stmAnu, stmRee, stmReeA, stmReeB, stmReeC;
    private java.sql.ResultSet rstCab, rst, rstCnsDet, rstRee, rstReeFact, rstReeA, rstReeB, rstReeC;
    //private tblHilo objHilo;

    private ZafTblMod objTblMod;
    
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;    
    
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblEdi objTblEdi;
    private ZafTblCelRenLbl objTblCelRenLblObl;
    private ZafCxC04.ZafTblModLis objTblModLis;
    private double dblMonDoc=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;
    
    ////////////////////////////////////
    double VAL_ABO = 0.00, VAL_ABO_REG_SEL=0.00;
    double ABO_ASO_REG = 0.00;
    String fecdocSel="", fecVenDocSel="", strFecDia="";
    String EST_REG_REP="";
    int ULT_REG_DOC=0, CAN_REG_ABO_ASO=0, REG_CAN_FAC=0, REG_FAC=0, K=0;
    double porret = 0.000, VALCHQ=0.00, VAL_TOT_DOC=0;
    String DIA_GRA="", EST_REG_FAC="", FEC_VEN_DOC="";
    int CAN_FAC_SAL_PND=0, CAN_FAC_EST_POS=0, intFilSel=0, k=0, CAN_REG_CON=0;
    
    private String strCodCli, strDesLarCli, strCodBan, strDesCorBan, strDesLarBan; //Contenido del campo al obtener el foco.
    
    private Librerias.ZafDate.ZafDatePicker dtpFecRec, dtpFecVen;
    ///////////////////////////////////
    
    private ZafTblCelRenChk objTblCelRenChkSopNec;
    private ZafTblCelEdiChk objTblCelEdiChkSopNec;
    private ZafTblCelRenChk objTblCelRenChkSopEnt;
    private ZafTblCelEdiChk objTblCelEdiChkSopEnt;
    private ZafTblCelRenChk objTblCelRenChkEstChq;
    private ZafTblCelEdiChk objTblCelEdiChkEstChq;

    private String strSopEnt="";
    
    private int intSig=1;                                   //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    private ZafTblCelEdiBut objTblCelEdiBut;
    private ZafTblCelRenBut objTblCelRenBut;

    
    Librerias.ZafDate.ZafDatePicker objDtePck ; //Para realizar operaciones de fechas
    Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi objDteEdiCel; //Para realizar operaciones en la celda de fechas///
    java.util.Calendar calObj ; //Para realizar operaciones de fecha
    private String strFormatoFecha="d/m/y"; // Formato de fecha a usar 
    private String strFormatoFechaBase="y-m-d"; // Formato de fecha que usa la base
    
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConCodBan;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConNomBan;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConCodCli;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtConNomCli;
    
    private ZafTblCelEdiBut objTblCelEdiButCli;
    private ZafTblCelRenBut objTblCelRenButCli;
    private ZafTblCelEdiBut objTblCelEdiButTipDoc;
    private ZafTblCelRenBut objTblCelRenButTipDoc;
    private ZafTblCelEdiBut objTblCelEdiButBco;
    private ZafTblCelRenBut objTblCelRenButBco;
    private ZafTblCelEdiTxt objTblCelEdiTxtChq;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtNumFac;
    
    private ZafTblCelEdiButVco objTblCelEdiButVcoBco;   //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBco;   //Editor: JTextField de consulta en celda.
    
    //Eddye_ventana de documentos pendientes//
    private ZafTblCelEdiButGen objTblCelEdiButGen;      //Editor: JButton en celda.
    private ZafVenConCxC01 objVenConCxC01;
    
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDoc;
    
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC04(ZafParSis obj) {
        try
        {            
            initComponents();
            //Inicializar objetos.
            this.objZafParSis=obj;
            jfrThis = this;
            objZafParSis=(ZafParSis)obj.clone();
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
        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        txtCodCli = new javax.swing.JTextField();
        txtDesLarCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblCli = new javax.swing.JLabel();
        lblBan = new javax.swing.JLabel();
        txtDesCorBan = new javax.swing.JTextField();
        txtDesLarBan = new javax.swing.JTextField();
        butBan = new javax.swing.JButton();
        lblNumCta = new javax.swing.JLabel();
        txtNumCta = new javax.swing.JTextField();
        lblNumChq = new javax.swing.JLabel();
        txtNumChq = new javax.swing.JTextField();
        lblRecChe = new javax.swing.JLabel();
        lblVenChe = new javax.swing.JLabel();
        lblValChq = new javax.swing.JLabel();
        txtValChq = new javax.swing.JTextField();
        txtIde = new javax.swing.JTextField();
        txtCodBan = new javax.swing.JTextField();
        panVal = new javax.swing.JPanel();
        spnValExc = new javax.swing.JScrollPane();
        tblFacDet = new javax.swing.JTable();
        spnObs = new javax.swing.JScrollPane();
        panTxa = new javax.swing.JPanel();
        butConsultar = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
        butLim = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("T\u00edtulo de la ventana");
        setPreferredSize(new java.awt.Dimension(116, 210));
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
        lblTit.setText("Informaci\u00f3n del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setLayout(new java.awt.BorderLayout());

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panFil.setLayout(null);

        panFil.setPreferredSize(new java.awt.Dimension(10, 140));
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
        txtCodCli.setBounds(125, 10, 56, 20);

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
        txtDesLarCli.setBounds(182, 10, 460, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });

        panFil.add(butCli);
        butCli.setBounds(644, 10, 20, 20);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Beneficiario");
        panFil.add(lblCli);
        lblCli.setBounds(5, 9, 70, 20);

        lblBan.setFont(new java.awt.Font("SansSerif", 1, 12));
        lblBan.setText("Banco:");
        panFil.add(lblBan);
        lblBan.setBounds(5, 34, 60, 20);

        txtDesCorBan.setMaximumSize(null);
        txtDesCorBan.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDesCorBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorBanActionPerformed(evt);
            }
        });
        txtDesCorBan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorBanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorBanFocusLost(evt);
            }
        });

        panFil.add(txtDesCorBan);
        txtDesCorBan.setBounds(125, 35, 56, 20);

        txtDesLarBan.setMaximumSize(null);
        txtDesLarBan.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDesLarBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarBanActionPerformed(evt);
            }
        });
        txtDesLarBan.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarBanFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarBanFocusLost(evt);
            }
        });

        panFil.add(txtDesLarBan);
        txtDesLarBan.setBounds(182, 35, 460, 20);

        butBan.setFont(new java.awt.Font("SansSerif", 1, 12));
        butBan.setText("...");
        butBan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBanActionPerformed(evt);
            }
        });

        panFil.add(butBan);
        butBan.setBounds(644, 35, 20, 20);

        lblNumCta.setText("N\u00famero Cuenta:");
        panFil.add(lblNumCta);
        lblNumCta.setBounds(5, 61, 100, 15);

        txtNumCta.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtNumCta.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNumCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumCtaFocusGained(evt);
            }
        });

        panFil.add(txtNumCta);
        txtNumCta.setBounds(125, 60, 110, 20);

        lblNumChq.setText("N\u00famero Cheque:");
        panFil.add(lblNumChq);
        lblNumChq.setBounds(280, 61, 110, 15);

        txtNumChq.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtNumChq.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNumChq.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumChqFocusGained(evt);
            }
        });

        panFil.add(txtNumChq);
        txtNumChq.setBounds(390, 60, 110, 20);

        lblRecChe.setText("Fec.Rec.Chq. :");
        panFil.add(lblRecChe);
        lblRecChe.setBounds(5, 86, 100, 15);

        lblVenChe.setText("Fec.Ven.Chq. :");
        panFil.add(lblVenChe);
        lblVenChe.setBounds(280, 86, 100, 15);

        lblValChq.setText("Valor del  Cheque:");
        panFil.add(lblValChq);
        lblValChq.setBounds(5, 112, 120, 20);

        txtValChq.setFont(new java.awt.Font("SansSerif", 0, 12));
        txtValChq.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtValChq.setPreferredSize(new java.awt.Dimension(100, 20));
        panFil.add(txtValChq);
        txtValChq.setBounds(125, 112, 110, 20);

        panFil.add(txtIde);
        txtIde.setBounds(100, 10, 0, 0);

        panFil.add(txtCodBan);
        txtCodBan.setBounds(100, 35, 0, 0);

        panDat.add(panFil, java.awt.BorderLayout.NORTH);

        panVal.setLayout(new java.awt.BorderLayout());

        panVal.setPreferredSize(new java.awt.Dimension(935, 400));
        spnValExc.setBorder(new javax.swing.border.TitledBorder(""));
        spnValExc.setPreferredSize(new java.awt.Dimension(20, 0));
        tblFacDet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Sel", "Codigo Cliente", "co_regpag", "...", "Nombre Cliente", "...", "Tipo Dcto", "Nombre Dcto", "co_doc", "Numero Dcto", "Fecha Dcto", "Dias Credito", "Fecha Vcto", "% retencion", "Valor Dcto", "Valor Pndte", "Codigo Banco", "...", "Nombre Bco", "Numero cta", "Numero Cheque", "Fecha Recepcion", "Fecha Vcto", "Valor del Chq", "Dias de gracia", "Codigo Local"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, false, true, true, true, false, false, false, true, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblFacDet.setMinimumSize(new java.awt.Dimension(600, 64));
        spnValExc.setViewportView(tblFacDet);

        panVal.add(spnValExc, java.awt.BorderLayout.CENTER);

        panDat.add(panVal, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(519, 40));
        panTxa.setPreferredSize(new java.awt.Dimension(98, 20));
        butConsultar.setText("Consultar");
        butConsultar.setToolTipText("Consultar Informacion General");
        butConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConsultarActionPerformed(evt);
            }
        });
        butConsultar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                butConsultarFocusGained(evt);
            }
        });

        panTxa.add(butConsultar);

        butGua.setToolTipText("Guarda Informacion");
        butGua.setLabel("Guardar");
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });

        panTxa.add(butGua);

        butLim.setToolTipText("Limpia informacion de los Campos");
        butLim.setLabel("Limpiar");
        butLim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLimActionPerformed(evt);
            }
        });

        panTxa.add(butLim);

        butCer.setToolTipText("Salir del Programa");
        butCer.setLabel("Cerrar");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });

        panTxa.add(butCer);

        spnObs.setViewportView(panTxa);

        panDat.add(spnObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butConsultarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butConsultarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_butConsultarFocusGained

    private void txtNumChqFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumChqFocusGained
        // TODO add your handling code here:
        txtNumChq.selectAll();
    }//GEN-LAST:event_txtNumChqFocusGained

    private void txtNumCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumCtaFocusGained
        // TODO add your handling code here:
        txtNumCta.selectAll();
    }//GEN-LAST:event_txtNumCtaFocusGained

    private void butConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConsultarActionPerformed
        // TODO add your handling code here:
        
        txtNumCta.setText("");
        txtNumChq.setText("");
        txtValChq.setText("");
        dtpFecVen.setText("");
        
        if (isCamVal())
        {
            //Realizar acciï¿½n de acuerdo a la etiqueta del botï¿½n ("Consultar" o "Detener").
            if (butConsultar.getText().equals("Consultar"))
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
    }//GEN-LAST:event_butConsultarActionPerformed

    private void butBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBanActionPerformed
        // TODO add your handling code here:
        mostrarVenConBan(0);
    }//GEN-LAST:event_butBanActionPerformed

    private void txtDesLarBanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarBanFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesLarBan.getText().equalsIgnoreCase(strDesLarBan))
        {
            if (txtDesLarBan.getText().equals(""))
            {
                txtDesCorBan.setText("");
                txtDesLarBan.setText("");
            }
            else
            {
                mostrarVenConBan(2);
            }
        }
        else
            txtDesLarBan.setText(strDesLarBan);
    }//GEN-LAST:event_txtDesLarBanFocusLost

    private void txtDesLarBanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarBanFocusGained
        // TODO add your handling code here:
        strDesLarBan=txtDesLarBan.getText();
        txtDesLarBan.selectAll();
    }//GEN-LAST:event_txtDesLarBanFocusGained

    private void txtDesLarBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarBanActionPerformed
        // TODO add your handling code here:
        txtDesLarBan.transferFocus();
    }//GEN-LAST:event_txtDesLarBanActionPerformed

    private void txtDesCorBanFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorBanFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtDesCorBan.getText().equalsIgnoreCase(strDesCorBan))
        {
            if (txtDesCorBan.getText().equals(""))
            {
                txtDesCorBan.setText("");
                txtDesLarBan.setText("");
            }
            else
            {
                mostrarVenConBan(1);
            }
        }
        else
            txtDesCorBan.setText(strDesCorBan);
    }//GEN-LAST:event_txtDesCorBanFocusLost

    private void txtDesCorBanFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorBanFocusGained
        // TODO add your handling code here:
        strDesCorBan=txtDesCorBan.getText();
        txtDesCorBan.selectAll();
    }//GEN-LAST:event_txtDesCorBanFocusGained

    private void txtDesCorBanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorBanActionPerformed
        // TODO add your handling code here:
        txtDesCorBan.transferFocus();
    }//GEN-LAST:event_txtDesCorBanActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
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
    }//GEN-LAST:event_txtDesLarCliFocusLost

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        // TODO add your handling code here:
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
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
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        objUti=new ZafUtil();

            if (!configurarFrm())
                exitForm();
           
            vecDat.clear();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

//        System.out.println("---CLICK BUT GUARDAR---");
        
        if(K>0)
        {
//            System.out.println("EXISTEN REGISTROS SELECCIONADOS K... " + K);
            if(isCamValIns())
            {
                if (insertarReg())
                {
//                            System.out.println("funko insertar");
                            mostrarMsgInfReg("La operaciï¿½n GUARDAR registros se realizï¿½ con ï¿½xito");
                        }
                        else{
//                            System.out.println("no funko dividir");
                            mostrarMsgInfReg("La operaciï¿½n GUARDAR registros no se pudo realizar");
                }                
                vecDat.clear();
            }
        }
        else
            if(K==0)
            {
//                System.out.println(" NO EXISTEN REGISTROS SELECCIONADOS K... " + K);
                mostrarMsgInfReg("No se puede GUARDAR el documento, no posee registros seleccionados");
            }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        String strTit, strMsg;
//        try
//        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
    }//GEN-LAST:event_butCerActionPerformed

    private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
        limpiarFrm();
    }//GEN-LAST:event_butLimActionPerformed

    /*
     * FUNCION PARA SABER SI EL DATO INGRESADO ES NUMERICO
     */
    public static boolean IsNumeric(String s)
    {
        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) < '0' || s.charAt(i) > '9')
            {
                return false;
            }
        }
        return true;
    }
    
    /*
     *FUNCION PARA LIMPIAR FORMULARIO
     */
    protected void limpiarFrm(){
        vecDat.clear();
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        txtCodBan.setText("");
        txtDesCorBan.setText("");
        txtDesLarBan.setText("");
        txtNumCta.setText("");
        txtNumChq.setText("");
        txtValChq.setText("");
        dtpFecVen.setText("");
        objTblMod.removeAllRows();
        objTblMod.insertRow();
        K=0;
    }
                
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexiï¿½n si estï¿½ abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }                        
    }//GEN-LAST:event_exitForm
    
    /** Cerrar la aplicaciï¿½n. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butBan;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butConsultar;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butLim;
    private javax.swing.JLabel lblBan;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblNumChq;
    private javax.swing.JLabel lblNumCta;
    private javax.swing.JLabel lblRecChe;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValChq;
    private javax.swing.JLabel lblVenChe;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panVal;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnValExc;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblFacDet;
    private javax.swing.JTextField txtCodBan;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtDesCorBan;
    private javax.swing.JTextField txtDesLarBan;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtIde;
    private javax.swing.JTextField txtNumChq;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtValChq;
    // End of variables declaration//GEN-END:variables
 
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }   

   
    private boolean isCamVal()
    {
        //Validar que el JTable de detalle estï¿½ completo.
        int intcountTrue = objTblMod.getRowCountTrue();
        int intcount = objTblMod.getRowCount();
        
        System.out.println("---isCamVal()--intcountTrue---: " + intcountTrue);
        System.out.println("---isCamVal()--intcount---: " + intcount);
        
        if(K==0)
        {
            objTblMod.removeEmptyRows();
            if (!objTblMod.isAllRowsComplete())
            {
                mostrarMsgInf("<HTML>El detalle del documento contiene filas que estï¿½n incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                tblFacDet.setRowSelectionInterval(0, 0);
                tblFacDet.changeSelection(0, INT_TBL_NUM_DOC, true, true);
                tblFacDet.requestFocus();
                return false;
            }
        }
        
        if(txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba un Cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }
        return true;
    }
    
    
    private boolean isCamValIns()
    {
        //Validar que el JTable de detalle estï¿½ completo.
        int intcountTrue = objTblMod.getRowCountTrue();
        int intcount = objTblMod.getRowCount();
        
//        System.out.println("---isCamValIns()--intcountTrue---: " + intcountTrue);
//        System.out.println("---isCamValIns()--intcount---: " + intcount);
        
        if(K==0)
        {
            objTblMod.removeEmptyRows();
            if (!objTblMod.isAllRowsComplete())
            {
                mostrarMsgInf("<HTML>El detalle del documento contiene filas que estï¿½n incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                tblFacDet.setRowSelectionInterval(0, 0);
                tblFacDet.changeSelection(0, INT_TBL_NUM_DOC, true, true);
                tblFacDet.requestFocus();
                return false;
            }
        }
        
        if(!dtpFecRec.isFecha())
        ///if(dtpFecRec.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Recepcion del Cheque</FONT> es obligatorio.<BR>Escriba la fecha y vuelva a intentarlo.</HTML>");
            dtpFecRec.requestFocus();
            return false;
        }
        
        if(!dtpFecVen.isFecha())
        ///if(dtpFecVen.getText().equals(""))
        {
            System.out.println("---fec_ven en blanco o nulo--");
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Vencimiento del Cheque</FONT> es obligatorio.<BR>Escriba la fecha y vuelva a intentarlo.</HTML>");
            dtpFecVen.requestFocus();
            return false;
        }

        if(txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba un Cliente y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }
        
        if(txtNumChq.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Numero de Cheque</FONT> es obligatorio.<BR>Escriba un Numero y vuelva a intentarlo.</HTML>");
            txtNumChq.requestFocus();
            return false;
        }
        
        if(txtNumChq.getText().length()>6)
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Numero de Cheque</FONT> se puede agregar hasta 6 caracteres.<BR>Escriba un Numero de Cheque y vuelva a intentarlo.</HTML>");
            txtNumChq.requestFocus();
            return false;
        }
        
        if(txtNumCta.getText().length()>12)
        {
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Numero de Cuenta del Cheque</FONT> se puede agregar hasta 12 caracteres.<BR>Escriba un Numero de Cuenta del Cheque y vuelva a intentarlo.</HTML>");
            txtNumCta.requestFocus();
            return false;
        }
        
        return true;
    }
       
   
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {                        
            //Configurar JTable: Establecer el modelo.            
            this.setTitle(objZafParSis.getNombreMenu()+" V 2.0");
            lblTit.setText(objZafParSis.getNombreMenu());
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            
            //configurar en txtValChq//
            txtValChq.setEditable(false);
            txtValChq.setEnabled(false);
            txtValChq.setBackground(objZafParSis.getColorCamposSistema());
            txtCodCli.setBackground(objZafParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objZafParSis.getColorCamposObligatorios());
            txtNumChq.setBackground(objZafParSis.getColorCamposObligatorios());
            ///txtNumCta.setBackground(objZafParSis.getColorCamposObligatorios());
            
            dtpFecRec = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
            dtpFecRec.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecRec.setText("");
            panFil.add(dtpFecRec);
            dtpFecRec.setBounds(125, 85, 110, 20);
            
            dtpFecVen = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
            dtpFecVen.setPreferredSize(new java.awt.Dimension(70, 20));
            dtpFecVen.setText("");
            panFil.add(dtpFecVen);
            dtpFecVen.setBounds(390, 85, 110, 20);
            
            configurarVenConBco();
            //Eddye_ventana de documentos pendientes///
            configurarVenConDocAbi();
            ///Configurar la Ventana de Consulta de Cliente///
            configurarVenConCli();
            
            
            //Obtener la fecha del servidor.            
            ///dtpFecRec.setText(strFecDia);
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            dtpFecRec.setText(objUti.formatearFecha(datFecAux, "dd/MM/yyyy"));
            ///datFecAux=null;
            
            ///color obligatorio///
            dtpFecVen.setBackground(objZafParSis.getColorCamposObligatorios());
            
            
            vecCab.add(INT_TBL_LINEA,"");///0
            vecCab.add(INT_TBL_SEL,"");///1
            vecCab.add(INT_TBL_COD_LOC,"Cï¿½d.Loc.");///2
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");///3
            vecCab.add(INT_TBL_NOM_TIP_DOC,"Nom.Tip.Doc.");///4
            vecCab.add(INT_TBL_COD_DOC,"Cï¿½d.Doc.");///5
            vecCab.add(INT_TBL_COD_REG,"Cï¿½d.Reg.");///6
            vecCab.add(INT_TBL_NUM_DOC,"Nï¿½m.Doc.");///7
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");///8
            vecCab.add(INT_TBL_DIA_CRE,"Dï¿½a.Crï¿½.");///9
            vecCab.add(INT_TBL_FEC_VEN,"Fec.Ven.");///10
            vecCab.add(INT_TBL_VAL_DOC,"Val.Doc.");///11
            vecCab.add(INT_TBL_VAL_PEN,"Val.Pnd.");///12
            vecCab.add(INT_TBL_VAL_CHQ,"Val.Chq.");///13
            vecCab.add(INT_TBL_EST_REG,"Est.Reg.");///14
            vecCab.add(INT_TBL_COD_BAN,"Cod.Ban.");///15
            vecCab.add(INT_TBL_DES_BAN,"Des.Ban.");///16
            vecCab.add(INT_TBL_NOM_BAN,"Nom.Ban.");///17
            vecCab.add(INT_TBL_NUM_CTA,"Num.Cta.");///18
            vecCab.add(INT_TBL_NUM_CHQ,"Num.Chq.");///19
            vecCab.add(INT_TBL_FEC_REC_CHQ,"Fec.Rec.Chq.");///20
            vecCab.add(INT_TBL_FEC_VEN_CHQ,"Fec.Ven.Chq.");///21

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblFacDet.setModel(objTblMod);
            
            ///Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblFacDet.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            
            //Configurar JTable: Establecer el menï¿½ de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblFacDet);
            

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true); 
            objTblCelRenLbl.setFormatoNumerico("###,###,##0.00");            
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_PEN).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblCelRenLbl=null;
            
            ///renderizador para centrar los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_DIA_CRE).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ////////////////////////////////////////////////////////////////////////////////////////////////////            
            
            tblFacDet.setRowSelectionAllowed(true);
            tblFacDet.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblFacDet,INT_TBL_LINEA);
            tblFacDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(20);///0
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setPreferredWidth(20);///1
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(35);///3
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(50);///4
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setPreferredWidth(60);///5
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(10);///6
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setPreferredWidth(10);///7
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);///8
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(90);///9
            tblFacDet.getColumnModel().getColumn(INT_TBL_DIA_CRE).setPreferredWidth(40);///10
            tblFacDet.getColumnModel().getColumn(INT_TBL_FEC_VEN).setPreferredWidth(90);///11
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_DOC).setPreferredWidth(80);///12
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_PEN).setPreferredWidth(80);///13
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setPreferredWidth(80);///14
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(30);///15
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setPreferredWidth(30);///16
            tblFacDet.getColumnModel().getColumn(INT_TBL_DES_BAN).setPreferredWidth(60);///17
            tblFacDet.getColumnModel().getColumn(INT_TBL_NOM_BAN).setPreferredWidth(80);///18
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_CTA).setPreferredWidth(60);///19
            tblFacDet.getColumnModel().getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(60);///20
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblFacDet.getTableHeader().setReorderingAllowed(false);
           
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_DOC).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_REG).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_EST_REG).setResizable(false);
            
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setMaxWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setMinWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setPreferredWidth(0);
            tblFacDet.getColumnModel().getColumn(INT_TBL_COD_BAN).setResizable(false);
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_SEL);///1
            ///vecAux.add("" + INT_TBL_NUM_DOC);///10
            vecAux.add("" + INT_TBL_VAL_CHQ);///14
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblEdi=new ZafTblEdi(tblFacDet);
            ///setEditable(true);
                        
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelEdiChkMain=new ZafTblCelEdiChk();
            tblFacDet.getColumnModel().getColumn(INT_TBL_SEL).setCellEditor(objTblCelEdiChkMain);
            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {                           
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
//                                System.out.println("---afterEdit--despues de dar click EN CELDA DE SELECCION---");
                                
                                double valpnd = Math.abs(objUti.parseDouble(objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_VAL_PEN)));
//                                System.out.println("---valpnd--- " + valpnd);
                                
                                ///if(objTblCelEdiChkMain.isChecked())
                                if(objTblCelEdiChkMain.isSelected() == true)
                                {
                                    rtnDatosRegSel();
//                                    System.out.println("---VALCHQ--- " + VALCHQ);
                                    if(VALCHQ>0)
                                    {
                                        mostrarMsgInf("<HTML>Este Registro <FONT COLOR=\"blue\">ESTA POSTFECHADO</FONT>.<BR>Favor verifique los datos y proceda con los cambios pertinentes.</HTML>");
                                        objTblMod.setValueAt( new Boolean(false), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                                        calcularAboTot();
                                    }
                                    else
                                    {
//                                        System.out.println("---hizo click---");
                                        ///objTblMod.setValueAt(objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_VAL_PEN),tblFacDet.getSelectedRow(),INT_TBL_VAL_CHQ);
                                        objTblMod.setValueAt(new Double(valpnd),tblFacDet.getSelectedRow(),INT_TBL_VAL_CHQ);                                        
                                        K++;
//                                        System.out.println("---selecciono K = " + K + " registros desde casilla de seleccion---" );
                                    }
                                }
                                else
                                {
                                    objTblMod.setValueAt(null, tblFacDet.getSelectedRow(), INT_TBL_VAL_CHQ);
                                    K--;
//                                    System.out.println("---desactivo K = " + K + " registros desde casilla de seleccion---" );
                                }
                                calcularAboTot();
                }
            });
              
///////////////////////////////////////////////////////////////////////////////////////////////////////
            objTblCelEdiTxtChq=new ZafTblCelEdiTxt(tblFacDet);
            tblFacDet.getColumnModel().getColumn(INT_TBL_VAL_CHQ).setCellEditor(objTblCelEdiTxtChq);
///////////////////////////////////////////////////////////////////////////////////////////////////////                       
            objTblCelEdiTxtChq.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {                  
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
//                    System.out.println("afterEdit - DESPUES -- SE MODIFICO CELDA DE CHEQUE---");
                    if (objTblCelEdiTxtChq.getText().equals(""))
                    {
                        objTblMod.setValueAt( new Boolean(false), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                        K--;
//                        System.out.println("---se borraron K = " + K + " registros desde el valor del cheque---" );
                    }
                    else
                    {
                        objTblMod.setValueAt(new Boolean(true), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                        ///objTblCelEdiChkMain.setChecked(true, tblFacDet.getSelectedRow(), INT_TBL_SEL);
                        K++;
//                        System.out.println("---se activaron K = " + K + " registros desde el valor del cheque---" );
                        
                                    rtnDatosRegSel();
//                                    System.out.println("---CELDA VALCHQ--- " + VALCHQ);
                                    if(VALCHQ>0)
                                    {
//                                        System.out.println("---ENTRO EN VALCHQ>0--- " + VALCHQ);
                                        mostrarMsgInf("<HTML>Este Registro <FONT COLOR=\"blue\">ESTA POSTFECHADO</FONT>.<BR>Favor verifique los datos y proceda con los cambios pertinentes.</HTML>");
                                        objTblMod.setValueAt( new Boolean(false), tblFacDet.getSelectedRow(), INT_TBL_SEL);
                                        objTblMod.setValueAt(new Double(VALCHQ),tblFacDet.getSelectedRow(),INT_TBL_VAL_CHQ);
                                        calcularAboTot();
                                    }
                                    
                    }                    
                    calcularAboTot();
                }
            });
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
   

    ///Funcion para calcular valor del cheque///
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {            
            intFilPro=objTblMod.getRowCountTrue();
//            System.out.println("intFilPro--- " + intFilPro);
            
            for (i=0; i<intFilPro; i++)
            {
                if (objTblMod.isChecked(i, INT_TBL_SEL))
                {
                    ///Math.abs((objTblMod.getValueAt(tblFacDet.getSelectedRow(),INT_TBL_VAL_PEN)))
                    strConCel=(objTblMod.getValueAt(i, INT_TBL_VAL_CHQ)==null)?"":objTblMod.getValueAt(i, INT_TBL_VAL_CHQ).toString();
                    ///strConCel=(objTblMod.getValueAt(i, INT_TBL_VAL_PEN)==null)?"":objTblMod.getValueAt(i, INT_TBL_VAL_PEN).toString();
                    dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                    dblTot+=dblVal;
//                    System.out.println("---dblTot reg sel--- " + dblTot);
                }
            }
            VAL_TOT_DOC = dblTot;
//            System.out.println("VAL_TOT_DOC--- " + VAL_TOT_DOC);
            //Mostrar el Valor Total en el txtValChq//
            txtValChq.setText("" + objUti.redondeo(dblTot,objZafParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            txtValChq.setText("[ERROR]");
        }
    } 

   /**
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
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
                        txtIde.setText(vcoCli.getValueAt(2));
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIde.setText(vcoCli.getValueAt(2));
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
                            txtIde.setText(vcoCli.getValueAt(2));
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        txtIde.setText(vcoCli.getValueAt(2));
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
                            txtIde.setText(vcoCli.getValueAt(2));
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
     * Esta funciï¿½n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBan(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBan.setCampoBusqueda(2);
                    vcoBan.show();
                    if (vcoBan.getSelectedButton()==vcoBan.INT_BUT_ACE)
                    {
                        txtCodBan.setText(vcoBan.getValueAt(1));
                        txtDesCorBan.setText(vcoBan.getValueAt(2));
                        txtDesLarBan.setText(vcoBan.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
                    if (vcoBan.buscar("a2.tx_descor", txtDesCorBan.getText()))
                    {
                        txtCodBan.setText(vcoBan.getValueAt(1));
                        txtDesCorBan.setText(vcoBan.getValueAt(2));
                        txtDesLarBan.setText(vcoBan.getValueAt(3));
                    }
                    else
                    {
                        vcoBan.setCampoBusqueda(0);
                        vcoBan.setCriterio1(11);
                        vcoBan.cargarDatos();
                        vcoBan.show();
                        if (vcoBan.getSelectedButton()==vcoBan.INT_BUT_ACE)
                        {
                            txtCodBan.setText(vcoBan.getValueAt(1));
                            txtDesCorBan.setText(vcoBan.getValueAt(2));
                            txtDesLarBan.setText(vcoBan.getValueAt(3));
                        }
                        else
                        {
                            txtCodBan.setText(strDesCorBan);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoBan.buscar("a2.tx_deslar", txtDesLarBan.getText()))
                    {
                        txtCodBan.setText(vcoBan.getValueAt(1));
                        txtDesCorBan.setText(vcoBan.getValueAt(2));
                        txtDesLarBan.setText(vcoBan.getValueAt(3));
                    }
                    else
                    {
                        vcoBan.setCampoBusqueda(2);
                        vcoBan.setCriterio1(11);
                        vcoBan.cargarDatos();
                        vcoBan.show();
                        if (vcoBan.getSelectedButton()==vcoBan.INT_BUT_ACE)
                        {
                            txtCodBan.setText(vcoBan.getValueAt(1));
                            txtDesCorBan.setText(vcoBan.getValueAt(2));
                            txtDesLarBan.setText(vcoBan.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarBan.setText(strDesLarBan);
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConBco()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a2.co_reg");
            arlCam.add("a2.tx_descor");
            arlCam.add("a2.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½d.Reg.");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("30");
            arlAncCol.add("100");
            arlAncCol.add("300");
            
            //Armar la sentencia SQL.            
            strSQL="";
            strSQL+=" SELECT a2.co_reg, a2.tx_descor, a2.tx_deslar ";
            strSQL+=" FROM tbm_grpvar AS a1, tbm_var AS a2";
            strSQL+=" WHERE a1.co_grp=a2.co_grp and a1.co_grp=" + INT_GRP_BCO + "";
            strSQL+=" and a2.st_reg='A'";
            strSQL+=" ORDER BY a2.co_reg ";
            ///System.out.println("--- configurarVenConBco() -- "+strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoBan=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de Bancos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoBan.setConfiguracionColumna(1, javax.swing.JLabel.CENTER);
            vcoBan.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
            vcoBan.setConfiguracionColumna(3, javax.swing.JLabel.LEFT);
            vcoBan.setCampoBusqueda(2);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        int intCodEmp, intCodEmpGrp;
        try
        {
            intCodEmp=objZafParSis.getCodigoEmpresa();
            intCodEmpGrp = objZafParSis.getCodigoEmpresaGrupo();
            System.out.println("---intCodEmp: " + intCodEmp);
            System.out.println("---intCodEmpGrp: " + intCodEmpGrp);
//            INTCODEMP = intCodEmp;
//            INTCODEMPGRP = intCodEmpGrp;
            
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Identificaciï¿½n");
            arlAli.add("Nombre");
            arlAli.add("Direcciï¿½n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("284");
            arlAncCol.add("110");
            
            if(intCodEmp == intCodEmpGrp)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT c2.co_cli, c2.tx_ide, c2.tx_nom, c2.tx_dir";
                strSQL+=" FROM (";
                strSQL+="        SELECT b2.co_emp, MAX(b2.co_cli) AS co_cli, b2.tx_ide";
                strSQL+="        FROM (";
                strSQL+="               SELECT MAX(co_emp) AS co_emp, tx_ide";
                strSQL+="               FROM tbm_cli";
                strSQL+="               GROUP BY tx_ide";
                strSQL+="               ORDER BY tx_ide";
                strSQL+="             ) AS b1";
                strSQL+="        INNER JOIN tbm_cli AS b2 ON (b1.co_emp=b2.co_emp AND b1.tx_ide=b2.tx_ide)";
                strSQL+="        GROUP BY b2.co_emp, b2.tx_ide";
                strSQL+="      ) AS c1";
                strSQL+=" INNER JOIN tbm_cli AS c2 ON (c1.co_emp=c2.co_emp AND c1.co_cli=c2.co_cli)";
                strSQL+=" ORDER BY c2.tx_nom";
            }
            else
            {
                //Armar la sentencia SQL.                
                if(objUti.utilizarClientesEmpresa(objZafParSis, objZafParSis.getCodigoEmpresa(), objZafParSis.getCodigoLocal(), objZafParSis.getCodigoUsuario()))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_reg='A'";
                    strSQL+=" ORDER BY a1.tx_nom";
                    System.out.println("---QUERY PARA CONCLI x EMPRESA---: " + strSQL);
                }
                else
                {
                    System.out.println("---ELSE el resultado es---");
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM  tbr_cliloc AS a1";
                    strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                    strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                    strSQL+=" AND a2.st_reg='A'";
                    strSQL+=" ORDER BY a2.tx_nom";
                    System.out.println("---QUERY PARA CONCLI x LOCAL---: " + strSQL);
                }
            }
            
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de clientes/proveedores", strSQL, arlCam, arlAli, arlAncCol);
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
    
    //Eddye_ventana de documentospendientes///
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Documentos abiertos".
     */
    private boolean configurarVenConDocAbi()
    {
        boolean blnRes=true;
        try
        {
            objVenConCxC01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de documentos abiertos");
            
            ///cobros masivos//
            if(objZafParSis.getCodigoMenu()== 256)
                objVenConCxC01.setTipoConsulta(2);
            else
            {
                objVenConCxC01.setTipoConsulta(2);
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objZafParSis.getCodigoUsuario();
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
//            
            if(intCodUsr==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                System.out.println("---Query consulta usuario ADMIN: "+strSQL);
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_usr";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objZafParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objZafParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objZafParSis.getCodigoUsuario();
                System.out.println("---Query consulta usuario VARIOS: "+strSQL);
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
    

    protected String strNomDctoDos(String codDocDos){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        int intCodDos=Integer.parseInt(codDocDos);
        System.out.println("el codigo es:"+intCodDos);
        try{            
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select a1.tx_descor from tbm_cabtipdoc as a1";
                que+=" where";
                que+=" a1.co_emp=" + objZafParSis.getCodigoEmpresa() + " and a1.co_loc=" + objZafParSis.getCodigoLocal() + "";
                que+=" and a1.co_tipdoc=" + intCodDos + "";                                                
                System.out.println("estoy dentro de la funcion de tipdoc2:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre del document:" +auxTipDoc);
                }
            }            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;
        
    }        
    
    
    protected String strNomBco(String codBco){
        java.sql.Connection conBco;
        java.sql.Statement stmBco;
        java.sql.ResultSet rstBco;
        String que, auxBco="";        
        int intCodBco=Integer.parseInt(codBco);
        System.out.println("el codigo es:"+intCodBco);
        try{            
            conBco=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conBco!=null){
                stmBco=conBco.createStatement();
                que="";                                
                que+=" select a1.tx_descor from tbm_var as a1, tbm_grpvar as a2";
                que+=" where a1.co_grp=a2.co_grp and";                
                que+=" a2.co_grp=" + INT_GRP_BCO + "";
                System.out.println("estoy dentro de la funcion de bancos:"+que);
                rstBco=stmBco.executeQuery(que);
                if (rstBco.next()){
                    auxBco=rstBco.getString("tx_descor");
                    System.out.println("este es el nombre del document:" +auxBco);
                }
            }            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxBco;
        
    }        
    
 
    
    public void clnTextos() {
        //Cabecera
        
//        txtDoc.setText("");
//        txtDoc.setEditable(false);
//        txtFecDoc.setText("");
//        txtTipDocCod.setText("");
//        txtTipDocNom.setText("");
//        txtCodDoc.setText("");
      
    }
    


    protected String strNomDocDet(int tmp)
    {
        int tmpFun=tmp;
        Connection conTmp;
        Statement stmTmp;
        ResultSet rstTmp;
        String strSQL,strAux="";
        
        try
        {
            conTmp=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conTmp!=null)
            {
                stmTmp=conTmp.createStatement();
                strSQL="";
                strSQL+="select tx_descor from tbm_cabtipdoc";
                strSQL+=" where co_emp=" + objZafParSis.getCodigoEmpresa() + "";
                strSQL+=" and co_loc=" + objZafParSis.getCodigoLocal() + "";
                strSQL+=" and co_tipdoc=" + tmpFun + "";
                System.out.println("en cargarCabReg:"+strSQL);
                rstTmp=stmTmp.executeQuery(strSQL);
                if (rstTmp.next())
                {
                    strAux=rstTmp.getString("tx_descor");
                }
            }            
            //rstTmp.close();
            //stmTmp.close();
            conTmp.close();
            rstTmp=null;
            stmTmp=null;
            conTmp=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strAux;            
    }
    
    
    /**
     * Esta funciï¿½n permite cargar datos del registro seleccionado.
     * @return true: Si se pudo cargar los datos del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean rtnDatosRegSel()
    {
        int intPosRel, intCodEmp, intCanRegCan=0, intCanRegCon=0, intUltReg=0;
        boolean blnRes=true;
        double dblSumRegPag=0.00, dblValTotFac=0.00, dblSalFacPag=0.00, valabo=0.00;
        double valpnd=0.00, valret=0.00;
        String stRegRep="", strDiaGra="";
        try
        {
            intCodEmp=objZafParSis.getCodigoEmpresa();
            conRee=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (conRee!=null)
            {
                stmRee = conRee.createStatement();
                
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                if (objTblMod.isChecked(tblFacDet.getSelectedRow(), INT_TBL_SEL))
                ///ori/// if(objTblCelEdiChkMain.isChecked()))
                {
                    strSQL="";
                    strSQL+=" SELECT ";
                    strSQL+=" a1.co_emp as CoEmp, a1.co_loc as CoLoc, a1.co_tipdoc as CoTipDoc, a1.co_doc as coDoc, a1.ne_numdoc as NeNumDoc, a2.co_reg as coReg,  ";
                    strSQL+=" a1.co_cli, a1.tx_nomcli, a1.co_forpag, a1.tx_desforpag, a1.fe_doc as fecDoc, round(abs(a1.nd_tot),2) as TotFac, ";
                    strSQL+=" round(abs(a2.mo_pag),2) as valDoc, round(abs(a2.nd_abo),2) as Abono, round(abs(a2.mo_pag+a2.nd_abo),5) as valPnd, ";
                    strSQL+=" a2.ne_diaCre as diaCre, a2.ne_diagra as DiaGra, a2.fe_ven as fecVen, a2.co_tipret as CodTipRet, a2.nd_porRet as porRet, a2.st_reg as stReg, a2.st_regrep as stRegRep, ";                    
                    strSQL+=" a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq ";
                    strSQL+="FROM tbm_cabmovinv AS a1 ";
                    strSQL+="INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                    strSQL+=" WHERE a1.co_emp = " + intCodEmp + "";
                    strSQL+=" AND a1.co_loc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipdoc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_TIP_DOC) + "";
                    strSQL+=" AND a1.co_doc = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_DOC) + "";
                    ///strSQL+=" AND a2.co_reg = " + rstCabRee.getString("CoReg") + "";
                    strSQL+=" AND a2.co_reg = " + objTblMod.getValueAt(tblFacDet.getSelectedRow(), INT_TBL_COD_REG) + "";
                    strSQL+=" AND a1.st_reg <> 'E' AND a2.st_reg IN ('A','C') ";
                    ///strSQL+=" AND round(abs(a2.mo_pag+a2.nd_abo),2) = 0 ";
                    strSQL+=" ORDER BY a2.co_reg ";
                    System.out.println("---QUERY--rtnDatosRegSel(): " +strSQL);
                    rstRee=stmRee.executeQuery(strSQL);
//
//                    ///if (rstRee.next())
                    for(int i=0 ; rstRee.next() ; i++)
                    {                    
                        valabo = Double.parseDouble(rstRee.getString("Abono"));
                        valpnd = Double.parseDouble(rstRee.getString("valPnd"));
                        valret = Double.parseDouble(rstRee.getString("porRet"));
                        fecdocSel = objUti.formatearFecha(rstRee.getDate("fecDoc"),"dd/MM/yyyy");
                        fecVenDocSel = objUti.formatearFecha(rstRee.getDate("fecVen"),"dd/MM/yyyy");
                        stRegRep = rstRee.getString("stRegRep");
                        strDiaGra = rstRee.getString("DiaGra");
                        
                        
                        ///////////////////////////////////////////////////
                        String strEstPoschq="";
                        strEstPoschq=rstRee.getString("st_pos");
                        
                        if(strEstPoschq.equals("S"))
                        {
                            double dblValChq=rstRee.getDouble("nd_monChq");
                            System.out.println("---El valor del cheque es: " + dblValChq);
                            VALCHQ = dblValChq;
                        }
                        else
                        {
                            VALCHQ = 0.00;
                        }
                            
                        ///////////////////////////////////////////////////
                        
                        
                    }
                    VAL_ABO_REG_SEL = valabo;
                    EST_REG_REP = stRegRep;
                    DIA_GRA = strDiaGra;
//                    System.out.println("---VALABO_REG_SEL: " + valabo);
//                    System.out.println("---VALPND_REG_SEL: " + valpnd);
//                    System.out.println("---VALRET_REG_SEL: " + valret);
//                    System.out.println("---FECDOC_REG_SEL: " + fecdocSel);
//                    System.out.println("---FEC_VEN_DOC_REG_SEL: " + fecVenDocSel);
//                    System.out.println("---EST_REG_REP: " + EST_REG_REP);
//                    System.out.println("---DIA_GRA: " + DIA_GRA);
//                    System.out.println("---VALCHQ: " + VALCHQ);

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
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
    
    
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            ///System.out.println("ZafAsiDia.tableChanged");
            ///System.out.println("Celda modificada: (" + tblFacDet.getEditingRow() + "," + tblFacDet.getEditingColumn() + ")");
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    System.out.println("Se elimina fila...");
                    calcularAboTot();
                    //camSelChk();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }        
     
    public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
        }
        else
        {
            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        }
    }   
    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

 
    //para el boton de guardar
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (actualizarTbmPagMovInv())
                {
                        con.commit();
                        blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    private boolean actualizarTbmPagMovInv()
    {
        int intCodEmp, i;
        double dblAbo1, dblAbo2;
        boolean blnRes=true;
        String strFecRecChq, strFecVncChq, strCodBan;
        try
        {                
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objZafParSis.getCodigoEmpresa();
                    
                    for (i=0;i<objTblMod.getRowCountTrue();i++)
                    {
                        //if(objTblMod.getValueAt(i,INT_TBL_LINEA)=="M"){
//                        System.out.println(" ---ENTRO AL FOR---DENTRO DE actualizarTbmPagMovInv()--- ");
                        if (objTblMod.isChecked(i, INT_TBL_SEL))
                        {
                            
                            String strAux1=objUti.formatearFecha(dtpFecRec.getText(),"dd/MM/yyyy",objZafParSis.getFormatoFechaBaseDatos());
                            String strAux2=objUti.formatearFecha(dtpFecVen.getText(),"dd/MM/yyyy",objZafParSis.getFormatoFechaBaseDatos());
                            
                            strFecRecChq=strAux1;
                            strFecVncChq=strAux2;
                            
//                            System.out.println(" ---FECHA_RECEPCION_CHEQUE---strFecRecChq--- " + strFecRecChq);
//                            System.out.println(" ---FECHA_VENCIMIENTO_CHEQUE---strFecVncChq--- " + strFecVncChq);
                                   
                                   strSQL="";
                                   strSQL+="Update tbm_pagMovInv set";
                                   strSQL+=" st_entSop='S',";
                                   strSQL+=" st_pos='S',";
                                   strSQL+=" fe_recchq='#" + strFecRecChq + "#',";
                                   strSQL+=" fe_venchq='#" + strFecVncChq + "#',";
                                   
                                   if(!txtCodBan.getText().equals(""))
                                       strSQL+=" co_banChq= " + txtCodBan.getText() + ",";   ///original///
                                   
                                   ///strSQL+=" co_banChq=" + (txtCodBan.getText())==""?null:txtCodBan.getText() + ",";///prueba1
                                   ///strSQL+=" co_banChq= " + strCodBan + ",";///prueba2
                                   strSQL+=" tx_numCtaChq='" + txtNumCta.getText() + "',";
                                   strSQL+=" tx_numChq='" + txtNumChq.getText() + "',";
                                   ///strSQL+=" nd_monChq=" + txtValChq.getText() + "";
                                   strSQL+=" nd_monChq=" + objUti.parseDouble(objTblMod.getValueAt(i,INT_TBL_VAL_CHQ)) + "";
                                   strSQL+=" where";
                                   strSQL+=" co_emp=" + objZafParSis.getCodigoEmpresa() + " and" ;
                                   ///strSQL+=" co_loc=" + objZafParSis.getCodigoLocal() + " and";
                                   strSQL+=" co_loc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_LOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_LOC)) + " and";
                                   strSQL+=" co_tipdoc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_TIP_DOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_TIP_DOC)) + " and";
                                   strSQL+=" co_doc=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_DOC)==null)?"0":tblFacDet.getValueAt(i, INT_TBL_COD_DOC)) + " and";
                                   strSQL+=" co_reg=" + ((tblFacDet.getValueAt(i, INT_TBL_COD_REG)==null)?"0": tblFacDet.getValueAt(i, INT_TBL_COD_REG)) + "";
                                   System.out.println("---Query en actualizarTbmpagmovinv:"+strSQL);
                                   stm.executeUpdate(strSQL);
                                   
                        }
//                        else
//                            System.out.println(" ---ELSE---DENTRO DE actualizarTbmPagMovInv()--- ");
                    }
                    
                    stm.close();
                    stm=null;
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
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
//            pgrSis.setIndeterminate(true);
//            butCon.setText("Detener");
//            lblMsgSis.setText("Obteniendo datos...");
            
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            
            if (con!=null)
            {
                stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_loc, a1.co_cli, a1.tx_nomcli, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc, a1.fe_doc, a2.ne_diaCre";
                strSQL+=", a2.fe_ven, a2.nd_porRet, a2.mo_pag, a2.nd_abo, (a2.mo_pag+a2.nd_abo) AS nd_pen, a2.st_sop, a2.st_entSop, a2.st_pos, a2.co_banChq";
                strSQL+=", a4.tx_descor AS a4_tx_descor, a4.tx_desLar AS a4_tx_desLar, a2.tx_numCtaChq, a2.tx_numChq, a2.fe_recChq, a2.fe_venChq, a2.nd_monChq, a2.st_reg";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a4 ON (a2.co_banChq=a4.co_reg)";
                strSQL+=" WHERE a1.co_emp=" + objZafParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_cli=" + txtCodCli.getText();
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                //CxC (Todos los cobros que no correspondan a retenciones).
                strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.nd_porRet IS NULL OR a2.nd_porRet=0)";
                ///strSQL+=" AND (a2.nd_monChq IS NULL OR a2.nd_monChq=0)";
                strSQL+=" AND (a2.mo_pag+a2.nd_abo)<>0";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                System.out.println("---Query para cargarDetReg()--: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
//                vecDat.clear();
//                //Obtener los registros.
//                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LINEA,"");//0
                        vecReg.add(INT_TBL_SEL,"");//1
                        vecReg.add(INT_TBL_COD_LOC,rst.getString("co_loc"));//2
                        vecReg.add(INT_TBL_COD_TIP_DOC,rst.getString("co_tipDoc"));//3
                        vecReg.add(INT_TBL_NOM_TIP_DOC,rst.getString("tx_desCor"));//4
                        vecReg.add(INT_TBL_COD_DOC,rst.getString("co_doc"));//5
                        vecReg.add(INT_TBL_COD_REG,rst.getString("co_reg"));//6
                        vecReg.add(INT_TBL_NUM_DOC,rst.getString("ne_numDoc"));//7
                        vecReg.add(INT_TBL_FEC_DOC,rst.getString("fe_doc"));//8
                        vecReg.add(INT_TBL_DIA_CRE,rst.getString("ne_diaCre"));//9
                        vecReg.add(INT_TBL_FEC_VEN,rst.getString("fe_ven"));//10
                        vecReg.add(INT_TBL_VAL_DOC,rst.getString("mo_pag"));//11
                        vecReg.add(INT_TBL_VAL_PEN,rst.getString("nd_pen"));//12
                        vecReg.add(INT_TBL_VAL_CHQ,rst.getString("nd_monChq"));//13
                        vecReg.add(INT_TBL_EST_REG,rst.getString("st_reg"));//14                        
                        vecReg.add(INT_TBL_COD_BAN,rst.getString("co_banChq"));//15
                        vecReg.add(INT_TBL_DES_BAN,rst.getString("a4_tx_descor"));//16
                        vecReg.add(INT_TBL_NOM_BAN,rst.getString("a4_tx_desLar"));//17
                        vecReg.add(INT_TBL_NUM_CTA,rst.getString("tx_numCtaChq"));//18
                        vecReg.add(INT_TBL_NUM_CHQ,rst.getString("tx_numChq"));//19
                        vecReg.add(INT_TBL_FEC_REC_CHQ,rst.getString("fe_recChq"));//20
                        vecReg.add(INT_TBL_FEC_VEN_CHQ,rst.getString("fe_venChq"));//21
                        
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
                tblFacDet.setModel(objTblMod);
                vecDat.clear();
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
    
 
     private void mostrarMsgInfReg(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }              
    

     
    public String retNomBco(int intCodBco)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_descor from tbm_var";
                que+=" where co_reg=" + intCodBco + "";                                                
                System.out.println("el query del nombre del bco es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_descor");
                    System.out.println("este es el nombre del bco:" +auxTipDoc);
                }
            }            
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxTipDoc;        
    }
    
    /**
     * Esta clase crea un hilo que permite manipular la interface grï¿½fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estï¿½ ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podrï¿½a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estarï¿½a informado en todo
     * momento de lo que ocurre. Si se desea hacer ï¿½sto es necesario utilizar ï¿½sta clase
     * ya que si no sï¿½lo se apreciarï¿½a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
//                lblMsgSis.setText("Listo");
//                pgrSis.setValue(0);
                butConsultar.setText("Consultar");
            }
            //Establecer el foco en el JTable sï¿½lo cuando haya datos.
            if (tblFacDet.getRowCount()>0)
            {
                tblFacDet.setRowSelectionInterval(0, 0);
                tblFacDet.requestFocus();
            }
            objThrGUI=null;
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren mï¿½s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblFacDet.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
//                case INT_TBL_DAT_LIN:
//                    strMsg="";
//                    break;
//                case INT_TBL_DAT_COD_LOC:
//                    strMsg="Cï¿½digo del local";
//                    break;
//                case INT_TBL_DAT_DEC_TIP_DOC:
//                    strMsg="Descripciï¿½n corta del tipo de documento";
//                    break;
//                case INT_TBL_DAT_DEL_TIP_DOC:
//                    strMsg="Descripciï¿½n larga del tipo de documento";
//                    break;
//                case INT_TBL_DAT_NUM_DOC:
//                    strMsg="Nï¿½mero de documento";
//                    break;
//                case INT_TBL_DAT_FEC_DOC:
//                    strMsg="Fecha del documento";
//                    break;
//                case INT_TBL_DAT_DIA_CRE:
//                    strMsg="Dï¿½as de crï¿½dito";
//                    break;
//                case INT_TBL_DAT_FEC_VEN:
//                    strMsg="Fecha de vencimiento";
//                    break;
//                case INT_TBL_DAT_POR_RET:
//                    strMsg="Porcentaje de retenciï¿½n";
//                    break;
//                case INT_TBL_DAT_VAL_DOC:
//                    strMsg="Valor del documento";
//                    break;
//                case INT_TBL_DAT_ABO_DOC:
//                    strMsg="Abono";
//                    break;
//                case INT_TBL_DAT_VAL_PEN:
//                    strMsg="Valor pendiente";
//                    break;
//                case INT_TBL_DAT_EST_SOP:
//                    strMsg="Estado de soporte";
//                    break;
//                case INT_TBL_DAT_SOP_ENT:
//                    strMsg="Soporte entregado";
//                    break;
//                case INT_TBL_DAT_EST_POS:
//                    strMsg="Estado de postfechado";
//                    break;
//                case INT_TBL_DAT_COD_BAN:
//                    strMsg="Cï¿½digo del Banco";
//                    break;
//                case INT_TBL_DAT_NOM_BAN:
//                    strMsg="Nombre del Banco";
//                    break;
//                case INT_TBL_DAT_NUM_CTA:
//                    strMsg="Nï¿½mero de cuenta";
//                    break;
//                case INT_TBL_DAT_NUM_CHQ:
//                    strMsg="Nï¿½mero de cheque/retenciï¿½n";
//                    break;
//                case INT_TBL_DAT_FEC_REC_CHQ:
//                    strMsg="Fecha de recepciï¿½n del cheque";
//                    break;
//                case INT_TBL_DAT_FEC_VEN_CHQ:
//                    strMsg="Fecha de vencimiento del cheque";
//                    break;
//                case INT_TBL_DAT_VAL_CHQ:
//                    strMsg="Valor del cheque";
//                    break;
//                default:
//                    strMsg="";
//                    break;
            }
            tblFacDet.getTableHeader().setToolTipText(strMsg);
        }
    }
    
}