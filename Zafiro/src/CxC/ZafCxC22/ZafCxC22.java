/*
 * ZafCxC22.java
 *
 * Created on 31 de marzo de 2008, 10:57
 * RECEPCION DE EFECTIVO
 * CREADO POR DARIO CARDENAS
 */

package CxC.ZafCxC22;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafRptSis.ZafRptSis;
import java.math.BigDecimal;

/**
 *
 * @author  TUVAL dcardenas
 */
public class ZafCxC22 extends javax.swing.JInternalFrame 
{
    /* Declaración de Constantes.*/ 
    final int INT_TBL_LINEA          =0;            //Linea de Numeros de Registros en la tabla.
    final int INT_TBL_SEL            =1;            //Linea para casilla de selección de registros.
    final int INT_TBL_COD_EMP        =2;            //Linea de botones para mostrar datos de la factura.
    final int INT_TBL_COD_LOC        =3;            //Código de cliente.
    final int INT_TBL_COD_TIP_DOC    =4;            //Código del tipo de documento.
    final int INT_TBL_NOM_TIP_DOC    =5;            //Nombre del tipo de documento.
    final int INT_TBL_COD_DOC        =6;            //Código del documento.
    final int INT_TBL_NUM_DOC        =7;            //Número de la factura o documento.
    final int INT_TBL_FEC_DOC        =8;            //Fecha del documento.
    final int INT_TBL_VAL_DOC        =9;            //Valor del documento.
    final int INT_TBL_CTA_DEB        =10;           //Cuenta Debe.
    final int INT_TBL_CTA_HAB        =11;           //Cuenta Haber.
    
    /* Filas Eiminadas */
    final int INT_ARR_ELI_COD_EMP=0;
    final int INT_ARR_ELI_COD_LOC=1;
    final int INT_ARR_ELI_COD_TIP_DOC=2;
    final int INT_ARR_ELI_COD_DOC=3;
    final int INT_ARR_ELI_NUM_DOC=4;
    final int INT_ARR_ELI_FEC_DOC=5;
    final int INT_ARR_ELI_VAL_DOC=6;
    
    /* Declaracion de variables */
    private ZafParSis objParSis;
    javax.swing.JInternalFrame jfrThis;                     //Variable que hace referencia a this    
    private Librerias.ZafDate.ZafDatePicker dtpFecDoc;
    private ZafColNumerada objColNum;
    private ZafUtil objUti;
    private mitoolbar objTooBar;
    private ZafAsiDia objAsiDia;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblMod objTblMod;
    private ZafTblCelRenChk objTblCelRenChkMain;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private ZafRptSis objRptSis;                            //Reportes del Sistema.            
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafVenCon vcoTipDoc;                            //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoEmp;                               //Ventana de consulta.
    private ZafVenCon vcoCtaTra, vcoCtaDeb;
    private ZafThreadGUI objThrGUI;
    private java.util.Date datFecAux;                       //Auxiliar: Para almacenar fechas.    
    Librerias.ZafDate.ZafDatePicker objDtePck ;             //Para realizar operaciones de fechas
    java.util.Calendar calObj ;                             //Para realizar operaciones de fecha
    
    
    private java.sql.Connection conCab, con;    
    private java.sql.Statement stmCab, stm;
    private java.sql.ResultSet rstCab, rst;
    
    private String strSQL, strAux, strSQLCon;
    private String  STRNUMDOC="";
    private java.util.Vector vecReg;
    private Vector vecDat, vecCab;
    private Vector vecAux, vecAuxDat;
    private boolean blnHayCam;                              //Determina si hay cambios en el formulario.
    private boolean blnCon;
    
    private int k=0, banclick=0, banclickMod=0, banclickDes=0, banbot=0, banmod=0, intVarEliReg=0, intbanmodcel=0, intBanDocExi=0;
    private int intSig, intSigIn, intSigEg;                 //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private int CAN_REG_DET=0;
    
    //Variables de la clase.
    private String strUbiCtaTra, strUbiCtaDeb;              //Campos: Ubicación y Estado de autorización de la cuenta.
    private String strDesCorCtaTra, strDesLarCtaTra, strDesCorCtaDeb, strDesLarCtaDeb;
    private String strCodEmp, strNomEmp;                    //Contenido del campo al obtener el foco.
    private String strTipDoc, strDesLarTipDoc;              //Contenido del campo al obtener el foco.
    private String strTipDocCtaCon, strDesLarTipDocCtaCon;  //Contenido del campo al obtener el foco.
    private String strNatDocIng="";                         //Campos: Ubicación de la cuenta.  

    private double VAL_TOT_DOC=0.00;
    
    /** Creates new form ZafCxC22 */
    public ZafCxC22(ZafParSis obj) 
    {        
        try
        {
            objParSis=(ZafParSis)obj.clone();
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()) 
            {
                initComponents();
                //Inicializar objetos.
                jfrThis = this;
                objUti=new ZafUtil();
                dtpFecDoc = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y"); 
                //dtpFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
                dtpFecDoc.setText("");
                panCabGen.add(dtpFecDoc);
                dtpFecDoc.setBounds(580, 24, 100, 20);
                objTooBar=new mitoolbar(this);
                panBar.add(objTooBar);//llama a la barra de botones
                if (!configurarFrm())
                    exitForm();
                objAsiDia=new ZafAsiDia(objParSis);
                ///////asiento mejorado////
                objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() 
                {
                    public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) 
                    {
                            if (txtCodTipDoc.getText().equals(""))
                            objAsiDia.setCodigoTipoDocumento(-1);
                            else
                            objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                    }
                });
            }
            else
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCabGen = new javax.swing.JPanel();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumAlt = new javax.swing.JLabel();
        txtNumAlt = new javax.swing.JTextField();
        lblMonDoc = new javax.swing.JLabel();
        txtMonDoc = new javax.swing.JTextField();
        lblSal = new javax.swing.JLabel();
        txtSal = new javax.swing.JTextField();
        lblCtaTra = new javax.swing.JLabel();
        txtCodCtaTra = new javax.swing.JTextField();
        txtDesCorCtaTra = new javax.swing.JTextField();
        txtDesLarCtaTra = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        butCtaTra = new javax.swing.JButton();
        txtDesCorCtaDeb = new javax.swing.JTextField();
        lblCtaTra1 = new javax.swing.JLabel();
        txtCodCtaDeb = new javax.swing.JTextField();
        txtDesLarCtaDeb = new javax.swing.JTextField();
        butCtaDeb = new javax.swing.JButton();
        panDetDat = new javax.swing.JPanel();
        spnDetDat = new javax.swing.JScrollPane();
        tblCtaTra = new javax.swing.JTable();
        spnObs = new javax.swing.JScrollPane();
        panObs = new javax.swing.JPanel();
        panLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxa = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        lblTit.setPreferredSize(new java.awt.Dimension(215, 15));
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setMinimumSize(new java.awt.Dimension(136, 98));
        tabFrm.setPreferredSize(new java.awt.Dimension(605, 107));

        panDat.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panDat.setMinimumSize(new java.awt.Dimension(31, 53));
        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCabGen.setMinimumSize(new java.awt.Dimension(0, 0));
        panCabGen.setPreferredSize(new java.awt.Dimension(800, 104));
        panCabGen.setLayout(null);

        lblEmp.setText("Empresa:");
        panCabGen.add(lblEmp);
        lblEmp.setBounds(0, 4, 100, 20);

        txtCodEmp.setMaximumSize(null);
        txtCodEmp.setPreferredSize(new java.awt.Dimension(70, 20));
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
        panCabGen.add(txtCodEmp);
        txtCodEmp.setBounds(100, 4, 100, 20);

        txtNomEmp.setPreferredSize(new java.awt.Dimension(100, 20));
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
        panCabGen.add(txtNomEmp);
        txtNomEmp.setBounds(200, 4, 256, 20);

        butEmp.setText("...");
        butEmp.setPreferredSize(new java.awt.Dimension(20, 20));
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panCabGen.add(butEmp);
        butEmp.setBounds(456, 4, 20, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabGen.add(lblCodDoc);
        lblCodDoc.setBounds(0, 84, 100, 20);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabGen.add(txtCodDoc);
        txtCodDoc.setBounds(100, 84, 100, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabGen.add(lblFecDoc);
        lblFecDoc.setBounds(480, 24, 100, 20);

        lblNumAlt.setText("Número de documento:");
        lblNumAlt.setToolTipText("Número de documento");
        lblNumAlt.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabGen.add(lblNumAlt);
        lblNumAlt.setBounds(480, 64, 100, 20);

        txtNumAlt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabGen.add(txtNumAlt);
        txtNumAlt.setBounds(580, 64, 100, 20);

        lblMonDoc.setText("Valor del documento:");
        lblMonDoc.setToolTipText("Valor del documento");
        panCabGen.add(lblMonDoc);
        lblMonDoc.setBounds(480, 84, 100, 20);

        txtMonDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabGen.add(txtMonDoc);
        txtMonDoc.setBounds(580, 84, 100, 20);

        lblSal.setText("Saldo de la cuenta:");
        lblSal.setToolTipText("Saldo de la cuenta");
        lblSal.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabGen.add(lblSal);
        lblSal.setBounds(480, 44, 100, 20);

        txtSal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCabGen.add(txtSal);
        txtSal.setBounds(580, 44, 100, 20);

        lblCtaTra.setText("Cuenta acreedora:");
        lblCtaTra.setToolTipText("Cuenta acreedora");
        panCabGen.add(lblCtaTra);
        lblCtaTra.setBounds(0, 44, 100, 20);
        lblCtaTra.getAccessibleContext().setAccessibleName("acreedora:");

        txtCodCtaTra.setMaximumSize(null);
        txtCodCtaTra.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabGen.add(txtCodCtaTra);
        txtCodCtaTra.setBounds(68, 44, 32, 20);

        txtDesCorCtaTra.setMaximumSize(null);
        txtDesCorCtaTra.setPreferredSize(new java.awt.Dimension(70, 20));
        txtDesCorCtaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaTraActionPerformed(evt);
            }
        });
        txtDesCorCtaTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaTraFocusLost(evt);
            }
        });
        panCabGen.add(txtDesCorCtaTra);
        txtDesCorCtaTra.setBounds(100, 44, 100, 20);

        txtDesLarCtaTra.setPreferredSize(new java.awt.Dimension(100, 20));
        txtDesLarCtaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaTraActionPerformed(evt);
            }
        });
        txtDesLarCtaTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaTraFocusLost(evt);
            }
        });
        panCabGen.add(txtDesLarCtaTra);
        txtDesLarCtaTra.setBounds(200, 44, 256, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabGen.add(lblTipDoc);
        lblTipDoc.setBounds(0, 24, 100, 20);

        txtCodTipDoc.setMaximumSize(null);
        txtCodTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabGen.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 24, 32, 20);

        txtDesCorTipDoc.setMaximumSize(null);
        txtDesCorTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
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
        panCabGen.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(100, 24, 100, 20);

        txtDesLarTipDoc.setPreferredSize(new java.awt.Dimension(100, 20));
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
        panCabGen.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(200, 24, 256, 20);

        butTipDoc.setText("...");
        butTipDoc.setPreferredSize(new java.awt.Dimension(20, 20));
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabGen.add(butTipDoc);
        butTipDoc.setBounds(456, 24, 20, 20);

        butCtaTra.setText("...");
        butCtaTra.setPreferredSize(new java.awt.Dimension(20, 20));
        butCtaTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaTraActionPerformed(evt);
            }
        });
        panCabGen.add(butCtaTra);
        butCtaTra.setBounds(456, 44, 20, 20);

        txtDesCorCtaDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaDebActionPerformed(evt);
            }
        });
        txtDesCorCtaDeb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaDebFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaDebFocusLost(evt);
            }
        });
        panCabGen.add(txtDesCorCtaDeb);
        txtDesCorCtaDeb.setBounds(100, 64, 100, 20);

        lblCtaTra1.setText("Cuenta deudora:");
        lblCtaTra1.setToolTipText("Cuenta deudora");
        panCabGen.add(lblCtaTra1);
        lblCtaTra1.setBounds(0, 64, 100, 20);
        panCabGen.add(txtCodCtaDeb);
        txtCodCtaDeb.setBounds(68, 64, 32, 20);

        txtDesLarCtaDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaDebActionPerformed(evt);
            }
        });
        txtDesLarCtaDeb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaDebFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaDebFocusLost(evt);
            }
        });
        panCabGen.add(txtDesLarCtaDeb);
        txtDesLarCtaDeb.setBounds(200, 64, 256, 20);

        butCtaDeb.setText("...");
        butCtaDeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaDebActionPerformed(evt);
            }
        });
        panCabGen.add(butCtaDeb);
        butCtaDeb.setBounds(456, 64, 20, 20);

        panDat.add(panCabGen, java.awt.BorderLayout.NORTH);

        panDetDat.setMaximumSize(new java.awt.Dimension(2147483647, 2147483647));
        panDetDat.setMinimumSize(new java.awt.Dimension(31, 31));
        panDetDat.setPreferredSize(new java.awt.Dimension(935, 400));
        panDetDat.setLayout(new java.awt.BorderLayout());

        tblCtaTra.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDetDat.setViewportView(tblCtaTra);

        panDetDat.add(spnDetDat, java.awt.BorderLayout.CENTER);

        panDat.add(panDetDat, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(519, 100));

        panObs.setPreferredSize(new java.awt.Dimension(518, 90));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setMinimumSize(new java.awt.Dimension(92, 30));
        panLbl.setPreferredSize(new java.awt.Dimension(92, 30));
        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 15));
        panLbl.add(lblObs1);

        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:");
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 15));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panTxa.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panTxa.add(spnObs2);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        spnObs.setViewportView(panObs);

        panDat.add(spnObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

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
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strTipDoc))
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
            txtDesCorTipDoc.setText(strTipDoc);
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();   
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        mostrarVenConEmp(0);
        
        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            mostrarTipDocPreGrp();
            
            if (!txtCodTipDoc.getText().equals(""))
            {
                mostrarCtaPreTra();
                mostrarSaldoCta();
            }            
        }
    }//GEN-LAST:event_butEmpActionPerformed

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
            }
            else
            {
                mostrarVenConEmp(1);
            }
        }
        else
            txtCodEmp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "óEstó seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) 
        {

            dispose();
        }

    }//GEN-LAST:event_exitForm

    private void butCtaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaTraActionPerformed
        mostrarVenConCtaTra(0);
    }//GEN-LAST:event_butCtaTraActionPerformed

    private void txtDesCorCtaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaTraActionPerformed
        txtDesCorCtaTra.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaTraActionPerformed

    private void txtDesCorCtaTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaTraFocusGained
        strDesCorCtaTra=txtDesCorCtaTra.getText();
        txtDesCorCtaTra.selectAll();
    }//GEN-LAST:event_txtDesCorCtaTraFocusGained

    private void txtDesCorCtaTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaTraFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCtaTra.getText().equalsIgnoreCase(strDesCorCtaTra)){
            if (txtDesCorCtaTra.getText().equals("")){
                txtCodCtaTra.setText("");
                txtDesLarCtaTra.setText("");
            }
            else
            {
                mostrarVenConCtaTra(1);
            }
        }
        else
            txtDesCorCtaTra.setText(strDesCorCtaTra);
    }//GEN-LAST:event_txtDesCorCtaTraFocusLost

    private void txtDesLarCtaTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaTraActionPerformed
        txtDesLarCtaTra.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaTraActionPerformed

    private void txtDesLarCtaTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaTraFocusGained
        strDesLarCtaTra=txtDesLarCtaTra.getText();
        txtDesLarCtaTra.selectAll();
    }//GEN-LAST:event_txtDesLarCtaTraFocusGained

    private void txtDesLarCtaTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaTraFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCtaTra.getText().equalsIgnoreCase(strDesLarCtaTra)){
            if (txtDesLarCtaTra.getText().equals("")){
                txtCodCtaTra.setText("");
                txtDesCorCtaTra.setText("");
            }
            else{
                mostrarVenConCtaTra(2);
            }
        }
        else
            txtDesLarCtaTra.setText(strDesLarCtaTra);
    }//GEN-LAST:event_txtDesLarCtaTraFocusLost

    private void txtDesCorCtaDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaDebActionPerformed
        txtDesCorCtaDeb.transferFocus();
    }//GEN-LAST:event_txtDesCorCtaDebActionPerformed

    private void txtDesCorCtaDebFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaDebFocusGained
        strDesCorCtaDeb = txtDesCorCtaDeb.getText();
        txtDesCorCtaDeb.selectAll();
    }//GEN-LAST:event_txtDesCorCtaDebFocusGained

    private void txtDesCorCtaDebFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaDebFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCtaDeb.getText().equalsIgnoreCase(strDesCorCtaDeb)) 
        {
            if (txtDesCorCtaDeb.getText().equals("")) 
            {
                txtCodCtaDeb.setText("");
                txtDesLarCtaDeb.setText("");
            } 
            else 
            {
                mostrarVenConCtaDeb(1);
            }
        }
        else 
        {
            txtDesCorCtaDeb.setText(strDesCorCtaDeb);
        }
    }//GEN-LAST:event_txtDesCorCtaDebFocusLost

    private void txtDesLarCtaDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaDebActionPerformed
        txtDesLarCtaDeb.transferFocus();
    }//GEN-LAST:event_txtDesLarCtaDebActionPerformed

    private void txtDesLarCtaDebFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaDebFocusGained
        strDesLarCtaDeb = txtDesLarCtaDeb.getText();
        txtDesLarCtaDeb.selectAll();
    }//GEN-LAST:event_txtDesLarCtaDebFocusGained

    private void txtDesLarCtaDebFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaDebFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCtaDeb.getText().equalsIgnoreCase(strDesLarCtaDeb)) 
        {
            if (txtDesLarCtaDeb.getText().equals("")) 
            {
                txtCodCtaDeb.setText("");
                txtDesCorCtaDeb.setText("");
            } 
            else
            {
                mostrarVenConCtaDeb(2);
            }
        } 
        else 
        {
            txtDesLarCtaDeb.setText(strDesLarCtaDeb);
        }
    }//GEN-LAST:event_txtDesLarCtaDebFocusLost

    private void butCtaDebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaDebActionPerformed
        mostrarVenConCtaDeb(0);
    }//GEN-LAST:event_butCtaDebActionPerformed
    
    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCtaDeb;
    private javax.swing.JButton butCtaTra;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCtaTra;
    private javax.swing.JLabel lblCtaTra1;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDoc;
    private javax.swing.JLabel lblNumAlt;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblSal;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabGen;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDetDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTxa;
    private javax.swing.JScrollPane spnDetDat;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblCtaTra;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCtaDeb;
    private javax.swing.JTextField txtCodCtaTra;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCtaDeb;
    private javax.swing.JTextField txtDesCorCtaTra;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCtaDeb;
    private javax.swing.JTextField txtDesLarCtaTra;
    private javax.swing.JTextField txtDesLarTipDoc;
    public javax.swing.JTextField txtMonDoc;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNumAlt;
    private javax.swing.JTextField txtSal;
    // End of variables declaration//GEN-END:variables
    
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;

        try
        {
            //Configurar JTable: Establecer el modelo.//
            this.setTitle(objParSis.getNombreMenu()+"v0.4");
            lblTit.setText(objParSis.getNombreMenu());
            txtCodTipDoc.setVisible(false);
            txtCodCtaTra.setVisible(false);
            txtCodCtaDeb.setVisible(false);
            txtCodEmp.setBackground(objParSis.getColorCamposObligatorios());
            txtNomEmp.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            ///txtCodDoc.setEnabled(false);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorCtaTra.setBackground(objParSis.getColorCamposSistema());
            txtDesLarCtaTra.setBackground(objParSis.getColorCamposSistema());
            txtDesCorCtaDeb.setBackground(objParSis.getColorCamposSistema());
            txtDesLarCtaDeb.setBackground(objParSis.getColorCamposSistema());
            txtSal.setEnabled(false);
            txtSal.setBackground(objParSis.getColorCamposSistema());
            txtNumAlt.setBackground(objParSis.getColorCamposObligatorios());
            txtMonDoc.setEnabled(false);
            txtMonDoc.setBackground(objParSis.getColorCamposSistema());
            vecDat=new Vector();        //Almacena los datos
            vecCab=new Vector(20);      //Almacena las cabeceras
            vecCab.clear();
            
            objTooBar.setVisibleAnular(false);
            objTooBar.setEnabledAnular(false);

            //Ocultar objetos de empresa para cuando se carga por empresa//
            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
            {
                lblEmp.setVisible(true);
                txtCodEmp.setVisible(true);
                txtNomEmp.setVisible(true);
                butEmp.setVisible(true);
            }
            else
            {
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
                               
                // lblTipDoc.setBounds(4,22,100,20);
                // txtCodTipDoc.setBounds(80,20,25,20);
                // txtDesCorTipDoc.setBounds(106,20,70,20);
                // txtDesLarTipDoc.setBounds(177,20,212,20);
                // butTipDoc.setBounds(390,20,20,20);
                             
                // lblCtaTra.setBounds(4,47,100,20);
                // txtCodCtaTra.setBounds(80,45,25,20);
                // txtDesCorCtaTra.setBounds(106,45,82,20);
                // txtDesLarCtaTra.setBounds(190,45,198,20);
                // butCtaTra.setBounds(390,45,20,20);
                                               
                // lblCodDoc.setBounds(4,70,100,20);
                // txtCodDoc.setBounds(106,70,70,20);
            }
            //Configurar Ventana de Consulta de Empresa//
            configurarVenConEmp();
            
            //Configurar Ventana de Consulta de Tipo de Documento//
            configurarVenConTipDoc();
            configurarVenConCtaTra();
            configurarVenConCtaDeb();
            
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           
            // configurarVenConTipDoc();
            // configurarVenConTipDocCtaCon();
            // configurarVenConBco();

            //Eddye_ventana de documentos pendientes///
            // configurarVenConDocAbi();
    
            /*Configurar Vector de Cabecera para los datos de las Columnas*/
            vecCab.add(INT_TBL_LINEA,"");//0
            vecCab.add(INT_TBL_SEL,"");//1
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");//2
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");//3
            vecCab.add(INT_TBL_COD_TIP_DOC,"Cod.Tip.Doc.");//4
            vecCab.add(INT_TBL_NOM_TIP_DOC,"Nom.Tip.Doc.");//5
            vecCab.add(INT_TBL_COD_DOC,"Cod.Doc.");//6
            vecCab.add(INT_TBL_NUM_DOC,"Num.Doc.");//7
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");//8
            vecCab.add(INT_TBL_VAL_DOC,"Val.Doc.");//9
            vecCab.add(INT_TBL_CTA_DEB,"Cta.Deb.");//10
            vecCab.add(INT_TBL_CTA_HAB,"Cta.Hab.");//11
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            ///Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_NUM_DOC, objTblMod.INT_COL_INT, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_VAL_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            //necesito para eliminar en pagmovinv
            //para eliminar registros en detpag  --- son los mismos datos que necestito para pagmovinv
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux=new java.util.ArrayList();    
            arlAux.add(INT_ARR_ELI_COD_EMP,"" + INT_TBL_COD_EMP);
            arlAux.add(INT_ARR_ELI_COD_LOC,"" + INT_TBL_COD_LOC);
            arlAux.add(INT_ARR_ELI_COD_TIP_DOC,"" + INT_TBL_COD_TIP_DOC);
            arlAux.add(INT_ARR_ELI_COD_DOC,"" + INT_TBL_COD_DOC);
            arlAux.add(INT_ARR_ELI_NUM_DOC,"" + INT_TBL_NUM_DOC);
            arlAux.add(INT_ARR_ELI_FEC_DOC,"" + INT_TBL_FEC_DOC);
            arlAux.add(INT_ARR_ELI_VAL_DOC,"" + INT_TBL_VAL_DOC);
            objTblMod.setColsSaveBeforeRemoveRow(arlAux);
            arlAux=null;
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblCtaTra.setModel(objTblMod); 

            //Configurar JTable: Establecer tipo de selección.
            tblCtaTra.setRowSelectionAllowed(true);

            tblCtaTra.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNum=new ZafColNumerada(tblCtaTra,INT_TBL_LINEA);
            objTblPopMnu=new ZafTblPopMnu(tblCtaTra);
            tblCtaTra.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar ZafTblMod: Establecer las columnas que el modelo debe almacenar antes de eliminar una fila.
            javax.swing.table.TableColumnModel tcmAux=tblCtaTra.getColumnModel();
            
            tblCtaTra.getColumnModel().getColumn(INT_TBL_LINEA).setPreferredWidth(30);//0
            tblCtaTra.getColumnModel().getColumn(INT_TBL_SEL).setPreferredWidth(25);//1
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_EMP).setPreferredWidth(60);//2
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_LOC).setPreferredWidth(60);//3
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_TIP_DOC).setPreferredWidth(30);//4
            tblCtaTra.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setPreferredWidth(100);//5
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_DOC).setPreferredWidth(40);//6
            tblCtaTra.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);//7
            tblCtaTra.getColumnModel().getColumn(INT_TBL_FEC_DOC).setPreferredWidth(110);//8
            tblCtaTra.getColumnModel().getColumn(INT_TBL_VAL_DOC).setPreferredWidth(100);//9
            tblCtaTra.getColumnModel().getColumn(INT_TBL_CTA_DEB).setPreferredWidth(60);//10
            tblCtaTra.getColumnModel().getColumn(INT_TBL_CTA_HAB).setPreferredWidth(60);//11
            
            //Configurar JTable: Establecer Ocultas las columnas.
            ocultaCol(INT_TBL_COD_TIP_DOC);//4
            ocultaCol(INT_TBL_COD_DOC);//6
            ocultaCol(INT_TBL_CTA_DEB);//10
            ocultaCol(INT_TBL_CTA_HAB);//11

            // objMouMotAda=new ZafMouMotAda();
            // tblCtaTra.getTableHeader().addMouseMotionListener(objMouMotAda);

            //para hacer editable las celdas
            vecAux=new Vector();
            vecAuxDat=new Vector();
            vecAux.add("" + INT_TBL_SEL);///1
            objTblMod.setColumnasEditables(vecAux);
            vecAuxDat = vecAux;
            vecAux=null;

            ///renderizador para centrar los datos de las columnas///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_NOM_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            ///renderizador para alinear a la derecha los datos de las columnas y color obligatorio///
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            // tblCtaTra.getTableHeader().setReorderingAllowed(false);

            //metodo de renderizador de la columna de seleccion//
            objTblCelRenChkMain=new ZafTblCelRenChk();
            tblCtaTra.getColumnModel().getColumn(INT_TBL_SEL).setCellRenderer(objTblCelRenChkMain);
            objTblCelRenChkMain=null;

            objTblCelEdiChkMain=new ZafTblCelEdiChk(tblCtaTra);
            tblCtaTra.getColumnModel().getColumn(INT_TBL_SEL).setCellEditor(objTblCelEdiChkMain);            
            objTblCelEdiChkMain.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if(objTblCelEdiChkMain.isChecked())
                    {
                        banclick++;
                        //double valpnd = Math.abs(objUti.parseDouble(objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_VAL_PEN)));
                    }
                    else
                    {                            
                        if (CAN_REG_DET>0)
                        {
                            banclick = CAN_REG_DET;
                            banclick--;
                            CAN_REG_DET = banclick;
                        }
                        else
                            banclick--;

                       if(banclickMod > 0)
                       {
                           banclickDes++;
                           java.util.ArrayList arlAux=new java.util.ArrayList();
                           arlAux.add(INT_ARR_ELI_COD_EMP,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_COD_EMP));
                           arlAux.add(INT_ARR_ELI_COD_LOC,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_COD_LOC));
                           arlAux.add(INT_ARR_ELI_COD_TIP_DOC,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_COD_TIP_DOC));
                           arlAux.add(INT_ARR_ELI_COD_DOC,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_COD_DOC));
                           arlAux.add(INT_ARR_ELI_NUM_DOC,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_NUM_DOC));
                           arlAux.add(INT_ARR_ELI_FEC_DOC,objTblMod.getValueAt(tblCtaTra.getSelectedRow(),INT_TBL_FEC_DOC));
                           objTblMod.addRowDataSavedBeforeRemoveRow(arlAux);

                       }
                    }
                    
                    calcularAboTot();
                }
            });
            
            //se hace invisible la opcion de modificar porque no esta bien la programacion
            objTooBar.setVisibleModificar(false);
            objTooBar.setEnabledModificar(false);
          
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
            
        return blnRes;
    }
    
    
    
    /**
     * Esta función configura la "Ventana de consulta" que sería utilizada para
     * mostrar los "Empresa".
     */
    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_ruc");
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
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_emp, a1.tx_ruc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_emp AS a1";
            ///strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.co_emp";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoEmp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta función configura la "Ventana de consulta" que sería utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
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
            arlAli.add("Código");
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
            
            if(intCodUsr==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
            }
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
    
    
    private void ocultaCol(int intCol)
    {
        tblCtaTra.getColumnModel().getColumn(intCol).setWidth(0);
        tblCtaTra.getColumnModel().getColumn(intCol).setMaxWidth(0);
        tblCtaTra.getColumnModel().getColumn(intCol).setMinWidth(0);
        tblCtaTra.getColumnModel().getColumn(intCol).setPreferredWidth(0);
        tblCtaTra.getColumnModel().getColumn(intCol).setResizable(false);         
    
    }
    
    private void mostrarCol(int intCol, int tamCol)
    {
        tblCtaTra.getColumnModel().getColumn(intCol).setWidth(tamCol);
        tblCtaTra.getColumnModel().getColumn(intCol).setMaxWidth(tamCol);
        tblCtaTra.getColumnModel().getColumn(intCol).setMinWidth(tamCol);
        tblCtaTra.getColumnModel().getColumn(intCol).setPreferredWidth(tamCol);
        tblCtaTra.getColumnModel().getColumn(intCol).setResizable(false);         
    }
    
    private void calcularAboTot()
    {
        double dblVal=0, dblTot=0;
        int intFilPro, i;
        String strConCel;
        try
        {            
            intFilPro=objTblMod.getRowCountTrue();
            
            for (i=0; i<intFilPro; i++)
            {
                if (objTblMod.isChecked(i, INT_TBL_SEL))
                {
                    strConCel=(objTblMod.getValueAt(i, INT_TBL_VAL_DOC)==null)?"":objTblMod.getValueAt(i, INT_TBL_VAL_DOC).toString();
                    dblVal=(objUti.isNumero(strConCel))?Double.parseDouble(strConCel):0;
                    dblTot+=dblVal;
                }
            }
            ///VAL_TOT_DOC = dblTot;
            VAL_TOT_DOC = objUti.redondear(dblTot,objParSis.getDecimalesBaseDatos());
            //Mostrar el Valor Total en el txtMonDoc//
            txtMonDoc.setText("" + objUti.redondeo(dblTot,objParSis.getDecimalesMostrar()));
        }
        catch (NumberFormatException e)
        {
            txtMonDoc.setText("[ERROR]");
        }
    }
    
    public class mitoolbar extends ZafToolBar
    {      
        public mitoolbar(javax.swing.JInternalFrame jfrThis)
        {
            super(jfrThis, objParSis);
        }

        public void clickInsertar() 
        {
         objTooBar.setEnabledImprimir(true);
         objTooBar.setEnabledVistaPreliminar(true);

            try
            {
                vecDat.clear();
                
                if (blnHayCam)
                {
                    isRegPro();
                }
                
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                

                clnTextos();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                mostrarTipDocPre();
                mostrarCtaPreTra();
                mostrarSaldoCta();
                cargarDatReg();
                
//                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
//                {
//                    mostrarTipDocPre();
                    
//                    if (!txtCodTipDoc.getText().equals(""))
//                    {
//                        mostrarCtaPreTra();
//                        mostrarSaldoCta();
//                        cargarDatReg();
//                    }
//                }

                
                
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setColumnasEditables(vecAuxDat);
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }

        }


        public boolean insertar()
        {
            boolean blnRes=false;
            
            if (!insertarReg())
                return false;
            else            
                blnRes = true;
            

            return blnRes;                                 
        }

        public void clickConsultar()
        {
            // vecDat.clear();
            txtDesCorTipDoc.requestFocus();
            objTblMod.setColumnasEditables(null);
        }


        public boolean consultar() 
        {
               consultarReg();
               return true;                                
        }

        public void clickModificar()
        {
            banclickMod++;
            
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            txtCodTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtCodDoc.setEditable(false);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            objTblMod.setColumnasEditables(vecAuxDat);
            objTblMod.insertRow();
            //  objAsiDia.setEditable(true);
            cargarDetReg();
            txtNumAlt.selectAll();
            
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);

            blnHayCam=false;
        }


        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }

        public void clickEliminar()
        {
            cargarDetReg();
        }

        public boolean eliminar()
        {
            try
            {
                if (!eliminarReg())
                {
                    return false;
                }
                
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }       
            
            return true;
        }

        public void clickAnular()
        {
            //  if(clickbutcon==0)  consultarReg();  else
            cargarDetReg();
        }

        public boolean anular()
        {
            try
            {
                if (!anularReg())
                    return false;

                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
                {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Anulado");
                    ///clnTextos();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e)
            {
                return true;
            }
            
            return true;                       
        }

        public void clickInicio() 
        {
            try
            {
                vecDat.clear();
                if (!rstCab.isFirst())
                {
                    ///if (blnHayCam || objAsiDia.isDiarioModificado())
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
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
        }

        public void clickAnterior() 
        {
            try
            {
                vecDat.clear();
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {

                            rstCab.previous();

                            cargarReg();
                        }
                        //consultarCamUsr();
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();                   
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

        }

        public void clickSiguiente() 
        {
            try
            {
                vecDat.clear();
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
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

        }

        public void clickFin() 
        {
            try
            {
                vecDat.clear();                    
                if (!rstCab.isLast())
                {
                    ///if (blnHayCam || objAsiDia.isDiarioModificado())
                    if (blnHayCam)
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
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
        }

        public void clickImprimir()
        {

        }

        public void clickVisPreliminar()
        {                

        }

        public void clickAceptar()
        {
            vecDat.clear();
        }


        public void clickCancelar()
        {
            clnTextos();
            vecDat.clear();
            objTblMod.removeAllRows();
        }

        private int Mensaje()
        {
            String strTit, strMsg;
            strTit="Mensaje del sistema Zafiro";
            strMsg="óDesea guardar los cambios efectuados a óste registro?\n";
            strMsg+="Si no guarda los cambios perderó toda la información que no haya guardado.";

            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();

            return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                

        }

        private void MensajeValidaCampo(String strNomCampo)
        {
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }

                
        public boolean beforeVistaPreliminar() {
            return true;
        }            

        public boolean afterVistaPreliminar() {
            return true;
        }            

        public boolean beforeModificar() 
        {
            calcularAboTot();

            strAux=objTooBar.getEstadoRegistro();
            
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estó ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento estó ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
//            if (!isCamValMod())
//                return false;
//            if (objAsiDia.getGeneracionDiario()==2)
//                return regenerarDiario();
            
            return true;
        }

        public boolean beforeEliminar() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya estó ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }

            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterInsertar() 
        {
//            this.setEstado('w');
//            consultarRegIns();
//            mostrarCtaPreTra();
//            mostrarSaldoCta();
//            return true;

            this.setEstado('w');
            objAsiDia.setDiarioModificado(false);
            objTooBar.setEstado('w');
            consultarReg();
            objAsiDia.setDiarioModificado(false);
//            mostrarCtaPreTra();
//            mostrarSaldoCta();
            blnHayCam=false;
            return true;




        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterModificar() 
        {
            objTblMod.clearDataSavedBeforeRemoveRow();
//            objAsiDia.setDiarioModificado(false);
            cargarDetReg();
            mostrarCtaPreTra();
            mostrarSaldoCta();
            blnHayCam=false;
            
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterEliminar() 
        {                
            return true;
        }

        public boolean afterAnular() 
        {
            mostrarCtaPreTra();
            mostrarSaldoCta();
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeInsertar() 
        {
//            calcularAboTot();
//
            if (!isCamVal())
                return false;
            
//            if (objAsiDia.getGeneracionDiario()==2)
//                return regenerarDiario();
            
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean beforeAnular() 
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estó ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya estó ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }


        public boolean cancelar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }


        public boolean imprimir() 
        {        
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;
        }

        public boolean vistaPreliminar() 
        {
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
        }

    }
    
    
    private boolean validaCampos()
    {

//            if(!dtpFecDoc.isFecha())
//            {
//               tabFrm.setSelectedIndex(0);                  
//               ///MensajeValidaCampo("Fecha de CotizaciÃ³n");
//               dtpFecDoc.requestFocus();
//               return false;
//            }
//
//
//
//           int intColObligadas[] = {INT_TBL_COD_CLI,INT_TBL_ABO_DOC,INT_TBL_DES_CTA_DOC};
//           
//           if( !objUti.verifyTbl(tblCtaTra,intColObligadas))
//           {
//               tabFrm.setSelectedIndex(0);                  
//                javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
//                    String strTit, strMsg;
//                    strTit="Mensaje del sistema Zafiro";
//                    strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
//                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
//
//                objUti.verifyTbl(tblCtaTra,intColObligadas);
//                return false;
//           }
//
//           //Validar que el "Diario estó cuadrado".
//           if(!objAsiDia.isDiarioCuadrado())
//           {
//                javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
//                    String strTit, strMsg;
//                    strTit="Mensaje del sistema Zafiro";
//                    strMsg="EL diario no esta cuadrado, no se puede grabar";
//                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                return false;
//           }
//
//           //Validar que el "Monto del diario" sea igual al monto del documento.
//           ///if (!objAsiDia.isDocumentoCuadrado(txtMonDoc.getText()))
//           if (!objAsiDia.isDocumentoCuadrado(VAL_TOT_DOC))
//           {
//               javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
//                    String strTit, strMsg;
//                    strTit="Mensaje del sistema Zafiro";
//                    strMsg="El valor del documento no coincide con el valor del asiento de diario. Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.";
//                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
//                return false;
//           }   
       return true; 
    }
    
    
    private boolean isCamVal()
    {
        if(!txtNumAlt.getText().equals(""))
        {
            if (!txtDesCorTipDoc.getText().equals(""))
            {
                if(rtnDocExi())
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El documento a insertar ya existe.<BR>Verifique Tipo de Documento y Nómero de Documento y vuelva a intentarlo.</HTML>");
                    txtNumAlt.requestFocus();
                    return false;
                }
            }
            else
            {
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
        }
        else
        {
            mostrarMsgInf("<HTML><CENTER>Â¡Â¡Â¡ATENCION!!! </CENTER><BR>El Documento que desea modificar no posee Numero Alterno, Favor verificar</HTML>");
        }
        
        if(banclick == 0)
        {
            if(CAN_REG_DET==0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El documento no posee Regitros Seleccionado <BR>Favor seleccionar un Registro y vuelva intentarlo</HTML>");
                return false;
            }
        }
        
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba un Tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodTipDoc.requestFocus();
            return false;
        }

        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if (txtDesLarTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
            txtDesLarTipDoc.requestFocus();
            return false;
        }

        if(dtpFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if(!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del Documento</FONT> es obligatorio.<BR>Escriba la fecha del documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }

        if(txtNumAlt.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nómero Alterno 1</FONT> es obligatorio.<BR>Escriba un nómero alterno 1 y vuelva a intentarlo.</HTML>");
            txtNumAlt.requestFocus();
            return false;
        }
       
       return true;
    }
    
    
    /* Funcion para encerar variables que se estan utilizando */
    public void clnTextos() 
    {
        //Valores para actualizar---encerar--- variable Cabecera        
        txtCodDoc.setText("");
        txtCodDoc.setEditable(false);
        dtpFecDoc.setText("");
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodDoc.setText("");
        txtCodCtaTra.setText("");
        txtDesCorCtaTra.setText("");
        txtDesLarCtaTra.setText("");
        txtCodCtaDeb.setText("");
        txtDesCorCtaDeb.setText("");
        txtDesLarCtaDeb.setText("");
        txtNumAlt.setText("");
        txtSal.setText("");
        txtMonDoc.setText("");
        
        objTblMod.removeAllRows();
        
        CAN_REG_DET=0;
        banclick=0;
        banclickMod=0;
        banclickDes=0;
    }
    
    
    protected boolean rtnDocExi()
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="", strNumDoc="";
        boolean blnRes=true;
        try{
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                que="";
                que+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.nd_mondoc, a1.fe_doc ";
                que+=" from tbm_cabRecEfe as a1";
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    que+=" where a1.co_emp=" + txtCodEmp.getText() + "";
                else
                    que+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                
                que+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                que+=" and a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                que+=" and a1.ne_numdoc1=" + txtNumAlt.getText() + "";
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next())
                {
                    strNumDoc = rstTipDoc.getString("ne_numdoc1");
                    blnRes=true;
                    intBanDocExi++;
                }
                else
                {
                    blnRes=false;
                    intBanDocExi--;
                }
                
                STRNUMDOC = strNumDoc;
                    
            }
            conTipDoc.close();
            conTipDoc=null;
            stmTipDoc=null;
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
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDatReg()
    {
        int intCodEmp, intCodLoc, intCodTipDoc, intCanReg=0, intCodMnu, intCodUsr;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                intCodMnu=objParSis.getCodigoMenu();
                intCodUsr=objParSis.getCodigoUsuario();                
                if (con!=null)
                {
                    stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);                   
                        strSQL="";
                        strSQL+=" SELECT COUNT(*) ";
                        strSQL+=" FROM (";
                        strSQL+="  SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.tx_descor, x.tx_deslar, x.co_dia, x.co_ctadeb as ctaDeb, y.co_ctahab as ctaHab, x.nd_mondeb as ValDoc, x.fe_dia, x.tx_numdia ";
                        strSQL+="  FROM (";
                        strSQL+="  select a1.co_emp,  a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.co_dia, a1.co_reg, a1.co_cta, a1.nd_mondeb, a2.tx_numdia, a2.fe_dia, a3.co_ctadeb ";
                        strSQL+="  from tbm_detdia AS a1 ";
                        strSQL+="  inner join tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia)";
                        strSQL+="  inner join tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc)";
                        strSQL+="  inner join tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE a2.st_reg IN ('O') AND a1.nd_mondeb>0 ";
//                        strSQL+="  AND a1.co_emp=" + intCodEmp;

                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else{
                            strSQL+=" AND a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                        }

                        strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        strSQL+="  ) AS x, (";
                        strSQL+="  select a1.co_emp,  a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.co_dia, a1.co_reg, a1.co_cta, a1.nd_mondeb, a2.tx_numdia, a2.fe_dia, a3.co_ctahab ";
                        strSQL+="  from tbm_detdia AS a1 ";
                        strSQL+="  inner join tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia)";
                        strSQL+="  inner join tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc)";
                        strSQL+="  inner join tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE a2.st_reg IN ('O') AND a1.nd_mondeb>0 ";
                        //strSQL+="  AND a1.co_emp=" + intCodEmp;

                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else{
                            strSQL+=" AND a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                        }

                        strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        strSQL+="  ) AS y";
                        strSQL+="  WHERE x.co_emp=y.co_emp and x.co_loc=y.co_loc and x.co_tipdoc=y.co_tipdoc and x.co_dia=y.co_dia";
                        strSQL+="  ) AS Z";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////                        
                        
                        strSQL="";
                        strSQL+=" SELECT * ";
                        strSQL+=" FROM (";
                        strSQL+="  SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.tx_descor, x.tx_deslar, x.co_dia, x.co_ctadeb as ctaDeb, y.co_ctahab as ctaHab, x.nd_mondeb as ValDoc, x.fe_dia, x.tx_numdia ";
                        strSQL+="  FROM (";
                        strSQL+="  select a1.co_emp,  a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.co_dia, a1.co_reg, a1.co_cta, a1.nd_mondeb, a2.tx_numdia, a2.fe_dia, a3.co_ctadeb ";
                        strSQL+="  from tbm_detdia AS a1 ";
                        strSQL+="  inner join tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia)";
                        strSQL+="  inner join tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc)";
                        strSQL+="  inner join tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE a2.st_reg IN ('O') AND a1.nd_mondeb>0 ";
//                        strSQL+="  AND a1.co_emp=" + intCodEmp;

                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else{
                            strSQL+=" AND a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                        }


                        strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        strSQL+="  ) AS x, (";
                        strSQL+="  select a1.co_emp,  a1.co_loc, a1.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.co_dia, a1.co_reg, a1.co_cta, a1.nd_mondeb, a2.tx_numdia, a2.fe_dia, a3.co_ctahab ";
                        strSQL+="  from tbm_detdia AS a1 ";
                        strSQL+="  inner join tbm_cabdia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia)";
                        strSQL+="  inner join tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipdoc=a3.co_tipdoc)";
                        strSQL+="  inner join tbr_tipdocdetprg AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipdoc) ";
                        strSQL+="  WHERE a2.st_reg IN ('O') AND a1.nd_mondeb>0 ";
//                        strSQL+="  AND a1.co_emp=" + intCodEmp;

                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else{
                            strSQL+=" AND a1.co_emp=" + intCodEmp + "";
                            strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                        }

                        strSQL+="  AND a4.co_mnu=" + intCodMnu;
                        strSQL+="  ) AS y";
                        strSQL+="  WHERE x.co_emp=y.co_emp and x.co_loc=y.co_loc and x.co_tipdoc=y.co_tipdoc and x.co_dia=y.co_dia";
                        strSQL+="  ) AS Z";                                               
                        rst=stm.executeQuery(strSQL);            
            
                        for(int i=0;rst.next();i++)
                        {                 
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_SEL, "");////1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("co_emp"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("co_loc"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("co_tipdoc"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("tx_deslar"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("co_dia"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("tx_numdia"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_dia"), "dd/MM/yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("ValDoc")));////9
                            vecReg.add(INT_TBL_CTA_DEB, ""+ (rst.getString("ctaDeb")));////10
                            vecReg.add(INT_TBL_CTA_HAB, ""+ (rst.getString("ctaHab")));////11
                            
                            vecDat.add(vecReg);
                        }                    
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblCtaTra.setModel(objTblMod);
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
    
    
    /**
     * Esta función permite consultar el registro insertado.
     * @return true: Si se pudo consultar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarRegIns()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL+=" FROM tbm_cabRecEfe AS a1";
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
                else
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText();
                rstCab=stmCab.executeQuery(strSQL);
                
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontro " + rstCab.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    objTooBar.setEstadoRegistro("Activo");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningón registro que cumpla el criterio de bósqueda especificado.");
                    clnTextos();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
     * Esta función permite consultar el registro insertado.
     * @return true: Si se pudo consultar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1";
                strSQL+=" FROM tbm_cabRecEfe AS a1";
                strSQL+=" LEFT OUTER JOIN (tbm_cabTipDoc AS a2 LEFT OUTER JOIN tbr_tipdocprg AS z1";
                strSQL+=" ON a2.co_emp=z1.co_emp AND a2.co_loc=z1.co_loc AND a2.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText() + "";
                else{
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                }
                
                strSQL+=" AND a1.st_reg <> 'E' ";
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
		if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc=" + strAux + "";
                strAux=txtNumAlt.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1=" + strAux + "";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros en esta Funcion");
                    rstCab.first();
                    cargarReg();
                    
//                    mostrarCtaPreTra();
//                    mostrarSaldoCta();
                    
                    //strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningón registro que cumpla el criterio de bósqueda especificado.");
                    clnTextos();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetReg())
                {
                }
            }
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
        
    }
    
    /**
     * Esta función permite consultar la Cabecera de los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            int intCodEmp=objParSis.getCodigoEmpresa();
            int intCodLoc=objParSis.getCodigoLocal();
            
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, ";
                strSQL+="a1.ne_numDoc1, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.nd_mondoc ";
                strSQL+=" FROM tbm_cabRecEfe AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" AND a1.st_reg <> 'E' ";

                
                rst=stm.executeQuery(strSQL);
                
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    txtMonDoc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));                    
                    VAL_TOT_DOC = Math.abs(rst.getDouble("nd_monDoc"));
                    
                    strAux=rst.getString("ne_numDoc1");
                    txtNumAlt.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                                       
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I") )  /////&& strAux.equals("E"))   //////Eliminacion Logica////////
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    clnTextos();
                    blnRes=false;
                }
                
                
                
                
                
                strSQL="";
                strSQL+=" 	SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_dia, a3.tx_numdia AS NUMDOC, a3.fe_dia AS FECDOC, (a4.nd_mondeb) as VALDOC";
                strSQL+=" 	, a4.co_cta AS CTADEB, a5.tx_codCta AS tx_codCtaDeb, a5.tx_desLar AS tx_nomCtaDeb";
                strSQL+="	FROM tbm_detRecEfe AS a2 INNER JOIN tbm_cabdia AS a3 ";
                strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_locrec=a3.co_loc AND a2.co_tipdocrec=a3.co_tipdoc AND a2.co_docrec=a3.co_dia";
                strSQL+="	INNER JOIN tbm_detdia AS a4";
                strSQL+=" 	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipdoc=a4.co_tipdoc AND a3.co_dia=a4.co_dia";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta";
                strSQL+=" 	WHERE a4.nd_mondeb > 0";
                strSQL+=" 	AND a2.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" 	AND a2.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" 	AND a2.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" 	AND a2.co_doc=" + rstCab.getString("co_doc");
                System.out.println("strSQL DEBE: " + strSQL);
                rst=stm.executeQuery(strSQL);               
                if (rst.next()){
                    strAux=rst.getString("CTADEB");
                    txtCodCtaDeb.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_codCtaDeb");
                    txtDesCorCtaDeb.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomCtaDeb");
                    txtDesLarCtaDeb.setText((strAux==null)?"":strAux);
                }
                
                strSQL="";
                strSQL+=" 	SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_dia, a3.tx_numdia AS NUMDOC, a3.fe_dia AS FECDOC, (a4.nd_mondeb) as VALDOC";
                strSQL+=" 	, a4.co_cta AS CTAHAB, a5.tx_codCta AS tx_codCtaHab, a5.tx_desLar AS tx_nomCtaHab";
                strSQL+="	FROM tbm_detRecEfe AS a2 INNER JOIN tbm_cabdia AS a3 ";
                strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_locrec=a3.co_loc AND a2.co_tipdocrec=a3.co_tipdoc AND a2.co_docrec=a3.co_dia";
                strSQL+="	INNER JOIN tbm_detdia AS a4";
                strSQL+=" 	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipdoc=a4.co_tipdoc AND a3.co_dia=a4.co_dia";
                strSQL+=" 	INNER JOIN tbm_plaCta AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta";
                strSQL+=" 	WHERE a4.nd_monHab > 0";
                strSQL+=" 	AND a2.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" 	AND a2.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" 	AND a2.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" 	AND a2.co_doc=" + rstCab.getString("co_doc");
                System.out.println("strSQL HABER: " + strSQL);
                rst=stm.executeQuery(strSQL);               
                if (rst.next()){
                    strAux=rst.getString("CTAHAB");
                    txtCodCtaTra.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_codCtaHab");
                    txtDesCorCtaTra.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomCtaHab");
                    txtDesLarCtaTra.setText((strAux==null)?"":strAux);
                }               
                
                
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posición relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta función permite consultar el detalle de los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intCodLoc, intCodTipDoc, intCanReg=0;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                
                if (con!=null)
                {
                    stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    
                        //Para los demós modos se muestra: sólo los documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT COUNT(*) ";
                        strSQL+=" FROM (";
                        strSQL+="  SELECT a1.co_emp AS CODEMP, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_locrec AS CODLOC, a1.co_tipdocrec AS TIPDOC, ";
                        strSQL+="  a1.co_docrec AS CODDOC, a5.tx_deslar AS NOMTIPDOC, a3.tx_numdia AS NUMDOC, a3.fe_dia AS FECDOC, sum(a4.nd_mondeb) as VALDOC, ";
                        strSQL+="  a5.co_ctadeb AS CTADEB, a5.co_ctahab AS CTAHAB ";
                        strSQL+="  FROM tbm_detRecEfe AS a1";
                        strSQL+="  INNER JOIN tbm_cabRecEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                        strSQL+="  INNER JOIN tbm_cabdia AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_locrec=a3.co_loc AND a1.co_tipdocrec=a3.co_tipdoc AND a1.co_docrec=a3.co_dia)";
                        strSQL+="  INNER JOIN tbm_detdia AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipdoc=a4.co_tipdoc AND a3.co_dia=a4.co_dia)";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc)";                        
                        strSQL+=" WHERE ";
                        
                        strSQL+=" a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a2.co_loc=" + rstCab.getString("co_loc");
                        strSQL+=" AND a1.co_tipDoc = " + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                        
                        strSQL+=" AND a2.st_reg <> 'E' ";
                        strSQL+=" AND a4.nd_mondeb > 0 ";
                        
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_locrec,  ";
                        strSQL+=" a1.co_tipdocrec, a1.co_docrec, a5.tx_deslar, a3.tx_numdia, a3.fe_dia, a5.co_ctadeb, a5.co_ctahab ";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg ";
                        
                        strSQL+=" ) AS A";
                        intCanReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                        
                        //Para los demós modos se muestra: sólo los documentos pagados.
                        strSQL="";
                        strSQL+="  SELECT a1.co_emp AS CODEMP, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_locrec AS CODLOC, a1.co_tipdocrec AS TIPDOC, ";
                        strSQL+="  a1.co_docrec AS CODDOC, a5.tx_deslar AS NOMTIPDOC, a3.tx_numdia AS NUMDOC, a3.fe_dia AS FECDOC, sum(a4.nd_mondeb) as VALDOC, ";
                        strSQL+="  a5.co_ctadeb AS CTADEB, a5.co_ctahab AS CTAHAB ";
                        strSQL+="  FROM tbm_detRecEfe AS a1";
                        strSQL+="  INNER JOIN tbm_cabRecEfe AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc)";
                        strSQL+="  INNER JOIN tbm_cabdia AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_locrec=a3.co_loc AND a1.co_tipdocrec=a3.co_tipdoc AND a1.co_docrec=a3.co_dia)";
                        strSQL+="  INNER JOIN tbm_detdia AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipdoc=a4.co_tipdoc AND a3.co_dia=a4.co_dia)";
                        strSQL+="  INNER JOIN tbm_cabtipdoc AS a5 ON (a3.co_emp=a5.co_emp AND a3.co_loc=a5.co_loc AND a3.co_tipdoc=a5.co_tipdoc)";                        
                        strSQL+=" WHERE ";
                        strSQL+=" a1.co_emp=" + rstCab.getString("co_emp");
                        strSQL+=" AND a2.co_loc=" + rstCab.getString("co_loc");
                        strSQL+=" AND a1.co_tipDoc = " + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc") + "";
                        strSQL+=" AND a2.st_reg <> 'E' ";
                        strSQL+=" AND a4.nd_mondeb > 0 ";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_locrec,  ";
                        strSQL+=" a1.co_tipdocrec, a1.co_docrec, a5.tx_deslar, a3.tx_numdia, a3.fe_dia, a5.co_ctadeb, a5.co_ctahab ";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg ";
                                               
                        rst=stm.executeQuery(strSQL);
            
                        for(int i=0;rst.next();i++)
                        {
                            
                            vecReg=new Vector();
                            vecReg.add(INT_TBL_LINEA, "");////0
                            vecReg.add(INT_TBL_SEL, "");////1
                            vecReg.add(INT_TBL_COD_EMP, rst.getString("CODEMP"));////2
			    vecReg.add(INT_TBL_COD_LOC, rst.getString("CODLOC"));////3
                            vecReg.add(INT_TBL_COD_TIP_DOC, rst.getString("TIPDOC"));////4
                            vecReg.add(INT_TBL_NOM_TIP_DOC, rst.getString("NOMTIPDOC"));////5
                            vecReg.add(INT_TBL_COD_DOC, rst.getString("CODDOC"));////6
                            vecReg.add(INT_TBL_NUM_DOC, rst.getString("NUMDOC"));////7
                            vecReg.add(INT_TBL_FEC_DOC, objUti.formatearFecha(rst.getDate("FECDOC"), "dd/MM/yyyy"));////8
                            vecReg.add(INT_TBL_VAL_DOC, ""+ (rst.getDouble("VALDOC")));////9
                            vecReg.add(INT_TBL_CTA_DEB, ""+ rst.getString("CTADEB"));////10
                            vecReg.add(INT_TBL_CTA_HAB, ""+ rst.getString("CTAHAB"));////11
                            vecReg.setElementAt(new Boolean(true),INT_TBL_SEL);
                            vecDat.add(vecReg);
                        }
                        rst.close();
                        stm.close();
                        con.close();
                        rst=null;
                        stm=null;
                        con=null;
                        //Asignar vectores al modelo.
                        objTblMod.setData(vecDat);
                        tblCtaTra.setModel(objTblMod);
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
    
    
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumAlt.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    strNatDocIng = rst.getString("tx_natDoc");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
    
    
    private boolean mostrarTipDocPreGrp()///prueba///
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                
                if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc AS TIPDOC, a1.tx_desCor AS DESCOR, a1.tx_desLar AS DESLAR, a1.ne_ultDoc AS ULTDOC, a1.tx_natDoc AS NATDOC";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc ";
                    strSQL+=" FROM tbr_tipDocPrg ";
                    strSQL+=" WHERE co_emp=" + txtCodEmp.getText();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                    rst=stm.executeQuery(strSQL);
                }
                
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("TIPDOC"));   ///txtCodTipDoc
                    txtDesCorTipDoc.setText(rst.getString("DESCOR"));
                    txtDesLarTipDoc.setText(rst.getString("DESLAR"));
                    txtNumAlt.setText("" + (rst.getInt("ULTDOC")+1));
                    intSig=(rst.getString("NATDOC").equals("I")?1:-1);
                    strNatDocIng = rst.getString("NATDOC");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función muestra la cuenta contable predeterminado del programa 
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarCtaPreTra()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" AND a2.co_emp=" + txtCodEmp.getText();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a2.st_reg='S'";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a2.st_reg='S'";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodCtaTra.setText(rst.getString("co_cta"));
                    txtDesCorCtaTra.setText(rst.getString("tx_codCta"));
                    txtDesLarCtaTra.setText(rst.getString("tx_desLar"));
                    strUbiCtaTra=rst.getString("tx_ubiCta");
                }
                
                //para la cuenta de debe
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, 'D' AS tx_ubiCta, a1.st_aut";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctaDeb";
                    strSQL+=" AND a2.co_emp=" + txtCodEmp.getText();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a1.co_cta=a2.co_ctaDeb";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, 'D' AS tx_ubiCta, a1.st_aut";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_ctaDeb";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a1.co_cta=a2.co_ctaDeb";
                    rst=stm.executeQuery(strSQL);
                }
                if (rst.next())
                {
                    txtCodCtaDeb.setText(rst.getString("co_cta"));
                    txtDesCorCtaDeb.setText(rst.getString("tx_codCta"));
                    txtDesLarCtaDeb.setText(rst.getString("tx_desLar"));
                    strUbiCtaDeb=rst.getString("tx_ubiCta");
                }
                
                if(objParSis.getCodigoUsuario()==90 || objParSis.getCodigoUsuario()==156)
                {
                    txtCodCtaDeb.setText("");
                    txtDesCorCtaDeb.setText("");
                    txtDesLarCtaDeb.setText("");
                    strUbiCtaDeb="";                    
                }
                
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función muestra la cuenta contable predeterminado del programa 
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarSaldoCta()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);                
                strSQL="";
                strSQL+=" SELECT sum(round((a2.nd_mondeb-a2.nd_monhab),2)) as SALDO";
                strSQL+=" FROM tbm_cabdia AS a1 ";
                strSQL+=" INNER JOIN tbm_detdia AS a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_dia=a2.co_dia) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_cta=" + txtCodCtaTra.getText();
                strSQL+=" AND a1.st_reg='A'";
                rst=stm.executeQuery(strSQL);
                
                rst=stm.executeQuery(strSQL);
                    
                if (rst.next())
                {
                    txtSal.setText(rst.getString("SALDO"));
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
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
     * Esta función muestra la cuenta contable predeterminado del programa 
     * de acuerdo al tipo de documento predeterminado.
     * @return true: Si se pudo mostrar la cuenta contable predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarSaldoCta_v01()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
                
                /*
                 select (a2.nd_mondeb-a2.nd_monhab) as salAnt 
                 from tbm_cabdia as a1
                 inner join tbm_detdia as a2 on a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_dia=a2.co_dia 
                 where a1.co_emp=1 and a2.co_cta=2046 and a1.fe_dia<'2007-01-01' and a1.st_reg='A'
                EL SALDO ANTERIOR E
                 */                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    strSQL="";
                    strSQL+="SELECT sum(round((a1.nd_salcta),2)) as SALDO";
                    strSQL+=" FROM tbm_salcta AS a1 ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_cta=" + txtCodCtaTra.getText();
                    
                    rst=stm.executeQuery(strSQL);

                    if (rst.next())
                    {
                        txtSal.setText(rst.getString("SALDO"));
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT sum(round((a1.nd_salcta),2)) as SALDO";
                    strSQL+=" FROM tbm_salcta AS a1 ";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_cta=" + txtCodCtaTra.getText();
                    
                    rst=stm.executeQuery(strSQL);

                    if (rst.next())
                    {
                        txtSal.setText(rst.getString("SALDO"));
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bósqueda determina si se debe hacer
     * una bósqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estó buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConEmp(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    break;
                case 1: //Bósqueda directa por "Código".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
                        }
                        else
                        {
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Bósqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText()))
                    {
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(3));
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(3));
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
    
    
    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bósqueda determina si se debe hacer
     * una bósqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se estó buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de bósqueda a realizar.
     * @return true: Si no se presentó ningón problema.
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
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));///txtCodTipDoc   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        
                        mostrarCtaPreTra();
                        mostrarSaldoCta();

                    }
                    break;
                case 1: //Bósqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_descor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        
                        mostrarCtaPreTra();
                        mostrarSaldoCta();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            
                            mostrarCtaPreTra();
                            mostrarSaldoCta();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Bósqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_deslar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        
                        mostrarCtaPreTra();
                        mostrarSaldoCta();
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            
                            mostrarCtaPreTra();
                            mostrarSaldoCta();
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                default:
                    txtDesCorTipDoc.requestFocus();
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
    
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
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
    
    
    private boolean insertarReg()
    {
        boolean blnRes=false;
        int codtipdoc=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {               
                if (insertarCab())
                {
                    if (insertarDet())
                    {

                        if(actualizarEstDia(0))
                        {
                            if (objUti.set_ne_ultDoc_tbm_cabTipDoc(this, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtNumAlt.getText())))
                            {         
                                con.commit();
                                blnRes=true;
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
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
    
    
    /**
     * Esta función permite insertar la cabecera de un documento.
     * @return true: Si se pudo insertar el documento.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab()
    {
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        String strTblAbo="";
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                double dblTblAbo=0.00;
                

                objTblMod.removeEmptyRows();
                
                //Obtener el código del óltimo registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabRecEfe AS a1";
                
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
                else
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                //Armar la sentencia SQL.
                strSQL="";                                                
                strSQL+="INSERT INTO tbm_cabRecEfe (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1, ";
                strSQL+="nd_monDoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_coming, tx_comultmod)";
                strSQL+=" VALUES (";

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=txtCodEmp.getText();
                else
                    strSQL+=objParSis.getCodigoEmpresa();

                strSQL+=", " + objParSis.getCodigoLocal();
                strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                
                strSQL+=", " + objUti.codificar(txtNumAlt.getText(),2);
                ///strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=", " + VAL_TOT_DOC;
                strSQL+=", " + objUti.codificar(txaObs1.getText());
                strSQL+=", " + objUti.codificar(txaObs2.getText());
                strSQL+=", 'A'";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'";
                strSQL+=", '" + objParSis.getNombreComputadoraConDirIP() + "'";
                strSQL+=")";
                stm.executeUpdate(strSQL);
                
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
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet()
    {
        boolean blnRes=true;
        int intCodEmp, intCodLoc, i, j;
        String strCodTipDoc, strCodDoc;
        
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                j=1;
                
                for (i=0;i<objTblMod.getRowCountTrue();i++)
                {
                    if (objTblMod.isChecked(i, INT_TBL_SEL))
                    {
                        strSQL=" ";
                        strSQL+=" INSERT INTO tbm_detRecEfe (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locRec, co_tipDocRec, co_docRec )";
                        strSQL+=" VALUES (";

                        if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                            strSQL+=" " + txtCodEmp.getText();
                        else
                            strSQL+=" " + objParSis.getCodigoEmpresa();

                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_LOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC);
                        strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_COD_DOC);
                        strSQL+=" )";

                        stm.executeUpdate(strSQL);
                        j++;
                        
                    }/////FIN DEL IF DE SELECCION DE REGISTRO////
                        
                }//////FIN DEL FOR/////////
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
     * Esta función permite Actualizar Abono del registro en la Tabla tbmCabDia
     * @param intTipOpe El tipo de operación.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operación</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarEstDia(int intTipOpe)
    {
        int intCodEmp, intCodLoc, i;
        double dblAbo1, dblAbo2;
        boolean blnRes=true;
        String estado="", estReg2="", estReg="";
        Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
        
        double abono=0.00;
        int regpag = 0;
        String strFecDoc="";
        int intCodMnuCieCaj = 1708;
        
        int codemp=0, codloc=0, codtipdoc=0, codoc=0;
        String numdoc, fedoc ;
        
        BigDecimal bdeValCieCaj=new BigDecimal("0");
                    
        try
        {           
                if (con!=null){
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                                                            
                    switch (intTipOpe)
                    {
                        case 0:///INSERTAR.
                            for (i=0; i<objTblMod.getRowCountTrue(); i++)
                            {
                                if (objTblMod.isChecked(i, INT_TBL_SEL))
                                {
                                    codemp = objParSis.getCodigoEmpresa();
                                    codloc = Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_LOC));
                                    codtipdoc = Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC));
                                    codoc = Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_DOC));
                                    numdoc = objUti.parseString(objTblMod.getValueAt(i,INT_TBL_NUM_DOC));
                                    fedoc = objTblMod.getValueAt(i,INT_TBL_FEC_DOC).toString();
                                    
                                    bdeValCieCaj=new BigDecimal(objTblMod.getValueAt(i,INT_TBL_VAL_DOC)==null?"0":(objTblMod.getValueAt(i,INT_TBL_VAL_DOC).toString().equals("")?"0":objTblMod.getValueAt(i,INT_TBL_VAL_DOC).toString()));
                                    
                                    objAsiDia.inicializar();
                                    
                                    objAsiDia.generarDiario(objParSis.getCodigoEmpresa(), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_LOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC)), Integer.parseInt(txtCodCtaDeb.getText()),  bdeValCieCaj.toString(), Integer.parseInt(txtCodCtaTra.getText()), bdeValCieCaj.toString());
                                    
                                    if(objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_LOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_DOC)), objUti.parseString(objTblMod.getValueAt(i,INT_TBL_NUM_DOC)),  objUti.parseDate((objUti.formatearFecha(objTblMod.getValueAt(i,INT_TBL_FEC_DOC).toString(), "dd/MM/yyyy", "dd/MM/yyyy")), "dd/MM/yyyy") , "A", intCodMnuCieCaj))
                                    {
                                        actualizarFechaCieCaj(con, objParSis.getCodigoEmpresa(), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_LOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_DOC)));
                                    }
                                }
                            }
                            break;
                        case 1:///MODIFICAR--ANULAR--ELIMINAR.
                            
                            //<editor-fold defaultstate="collapsed" desc="/* Comentado */">   
//                            
//                            java.util.ArrayList arlAux=objTblMod.getDataSavedBeforeRemoveRow();
//
//                            for (i=0;i<arlAux.size();i++)
//                            {
//                                    codemp = Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_EMP));
//                                    codloc = Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_LOC));
//                                    codtipdoc = Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_TIP_DOC));
//                                    codoc = Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_DOC));
//                                    numdoc = objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_NUM_DOC).toString();
//                                    fedoc = objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_FEC_DOC).toString();
//                                    ctaDeb = objTblMod.getValueAt(i,INT_TBL_CTA_DEB).toString();
//                                    ctaHab = objTblMod.getValueAt(i,INT_TBL_CTA_HAB).toString();
//                                                                        
//                                    if(objAsiDia.actualizarDiarioCieCaj(con, Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_EMP)), Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_LOC)), Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_TIP_DOC)), Integer.parseInt(""+objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_COD_DOC)), objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_NUM_DOC).toString(), objUti.getObjectValueAt(arlAux, i, INT_ARR_ELI_FEC_DOC).toString(), "O", Double.parseDouble(""+objTblMod.getValueAt(i,INT_TBL_VAL_DOC)), ctaDeb, ctaHab, intCodMnuCieCaj))
//                                        System.out.println("---es verdad posi---///MODIFICAR--ANULAR--ELIMINAR///");
//
//                            }
                            //</editor-fold>
                            
                            /* JoséMario 22/Ene/2016 */
                            for (i=0; i<objTblMod.getRowCountTrue(); i++)
                            {
                                regresarDiarioPredocumento(con, objParSis.getCodigoEmpresa(), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_LOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_TIP_DOC)), Integer.parseInt(""+objTblMod.getValueAt(i,INT_TBL_COD_DOC)));
                            } 
                            /* JoséMario 22/Ene/2016 */
                            break;
                    }
                    
                }

        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {               
                if (actualizarCab())
                {
                    if(actualizarEstDia(1))
                    {
                        if (eliminarDet())
                        {                    
                            if (insertarDet())
                            {
                                  //if(actualizarEstDia(1))
                                  //{                               
                                    con.commit();
                                    blnRes=true;
                                  //}
                                  //else
                                      //con.rollback();
                            }
                            else
                                con.rollback();
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
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
    
    
    /**
     * Esta función permite Actualizar Datos en la Tabla tbmCabRecEfe.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                int intCodEmp=objParSis.getCodigoEmpresa();
                int intCodLoc=objParSis.getCodigoLocal();
                if (datFecAux==null)
                    return false;
                //para calcular el monto del documento en base al detalle
                objTblMod.removeEmptyRows();
                
                strSQL="";
                strSQL+=" UPDATE tbm_cabRecEfe";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "',";
                strSQL+=" ne_numDoc1=" + objUti.codificar(txtNumAlt.getText(),2)+",";
                ///strSQL+=" nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDoc.getText())?"" + (intSig*Double.parseDouble(txtMonDoc.getText())):"0"),3);
                strSQL+=" nd_monDoc=" + VAL_TOT_DOC+",";
                strSQL+=" tx_obs1=" + objUti.codificar(txaObs1.getText())+",";
                strSQL+=" tx_obs2=" + objUti.codificar(txaObs2.getText())+",";
                strSQL+=" fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+=" co_usrMod=" + objParSis.getCodigoUsuario()+",";
                strSQL+=" tx_comUltmod='" + objParSis.getNombreComputadoraConDirIP() + "'";                    
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);

                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta función permite Eliminar los Registros en la Tabla tbmDetRecEfe.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" DELETE FROM tbm_detRecEfe";
                strSQL+=" WHERE co_emp=" + intCodEmp;
                strSQL+=" AND co_loc=" + intCodLoc;
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND co_doc=" + txtCodDoc.getText();
                stm.executeUpdate(strSQL);
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
    
    
    
    private boolean anularReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (anularCab())
                {
                    if(actualizarEstDia(1))
                    {
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
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
    
    
    private boolean anularCab()
    {
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                if(!txtCodDoc.getText().equals(""))
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabRecEfe";
                    strSQL+=" SET st_reg = 'I',";                
                    strSQL+=" fe_ultMod = '" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                    strSQL+=" co_usrMod =" + objParSis.getCodigoUsuario()+",";
                    strSQL+=" tx_comUltMod = '" + objParSis.getNombreComputadoraConDirIP() + "'";
                    
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE co_emp=" + txtCodEmp.getText();
                    else
                        strSQL+=" WHERE co_emp=" + intCodEmp;
                    
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_doc=" + txtCodDoc.getText();
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    datFecAux=null;
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
    
    private boolean eliminarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {
                if (eliminarCab())
                {
                    if(actualizarEstDia(1))
                    {
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();
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
    
     
    private boolean eliminarCab()
    {    
        boolean blnRes=true;
        int intCodEmp=objParSis.getCodigoEmpresa();
        int intCodLoc=objParSis.getCodigoLocal();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                if(!txtCodDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+=" UPDATE tbm_cabRecEfe";
                    strSQL+=" SET st_reg='E',";     ///////Eliminacion Logica//////
                    strSQL+=" fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                    strSQL+=" co_usrMod=" + objParSis.getCodigoUsuario()+",";
                    strSQL+=" tx_comUltMod = '" + objParSis.getNombreComputadoraConDirIP() + "'";
                    
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                        strSQL+=" WHERE co_emp=" + txtCodEmp.getText();
                    else
                        strSQL+=" WHERE co_emp=" + intCodEmp;
                    
                    strSQL+=" AND co_loc=" + intCodLoc;
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND co_doc=" + txtCodDoc.getText();
                    stm.executeUpdate(strSQL);
                    stm.close();
                    stm=null;
                    datFecAux=null;
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
     * Esta clase crea un hilo que permite manipular la interface grófica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que estó ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podróa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaróa informado en todo
     * momento de lo que ocurre. Si se desea hacer ósto es necesario utilizar ósta clase
     * ya que si no sólo se apreciaróa los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread
    {
        private int intIndFun;
        
        public ZafThreadGUI()
        {
            intIndFun=0;
        }
        
        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }
        
        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
    
    /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer;
        int i, intNumTotRpt;
        boolean blnRes=true;
       
        //Inicializar los parametros que se van a pasar al reporte.
        java.util.Map mapPar=new java.util.HashMap();
        
        Connection conIns;
        try
        {
            conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try
            {
                if(conIns!=null)
                {
                    objRptSis.cargarListadoReportes();
                    objRptSis.show();
                    if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
                    {
                        //Obtener la fecha y hora del servidor.
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        if (datFecAux==null)
                            return false;
                        strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MMM/yyyy HH:mm:ss");
                        datFecAux=null;

                        intNumTotRpt=objRptSis.getNumeroTotalReportes();
                        for (i=0;i<intNumTotRpt;i++)
                        {
                            if (objRptSis.isReporteSeleccionado(i))
                            {
                                switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                                {
                                    case 64:
                                    default:

                                    break;
                                }
                            }
                        }
                    }                  
                }
            }
            catch (Exception e)
            {
                blnRes=false;
                objUti.mostrarMsgErr_F1(this, e);
            }           
        }
        catch(SQLException ex)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, ex);
        }        
        return blnRes;
    }
    
    
    
    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaTra(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCtaTra.setText("");
                txtDesCorCtaTra.setText("");
                txtDesLarCtaTra.setText("");
                strUbiCtaTra="";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND tx_ubiCta='H'";
                strSQL+=" ORDER BY a1.tx_codCta";
                System.out.println("mostrarVenConCtaTra:" + strSQL);

                vcoCtaTra.setSentenciaSQL(strSQL);
                switch (intTipBus){
                    case 0: //Mostrar la ventana de consulta.
                        vcoCtaTra.setCampoBusqueda(1);
                        vcoCtaTra.show();
                        if (vcoCtaTra.getSelectedButton()==vcoCtaTra.INT_BUT_ACE)
                        {
                            txtCodCtaTra.setText(vcoCtaTra.getValueAt(1));
                            txtDesCorCtaTra.setText(vcoCtaTra.getValueAt(2));
                            txtDesLarCtaTra.setText(vcoCtaTra.getValueAt(3));
                            strUbiCtaTra=vcoCtaTra.getValueAt(4);
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCtaTra.buscar("a1.tx_codCta", txtDesCorCtaTra.getText()))
                        {
                            txtCodCtaTra.setText(vcoCtaTra.getValueAt(1));
                            txtDesCorCtaTra.setText(vcoCtaTra.getValueAt(2));
                            txtDesLarCtaTra.setText(vcoCtaTra.getValueAt(3));
                            strUbiCtaTra=vcoCtaTra.getValueAt(4);
                        }
                        else
                        {
                            vcoCtaTra.setCampoBusqueda(1);
                            vcoCtaTra.setCriterio1(11);
                            vcoCtaTra.cargarDatos();
                            vcoCtaTra.show();
                            if (vcoCtaTra.getSelectedButton()==vcoCtaTra.INT_BUT_ACE)
                            {
                                txtCodCtaTra.setText(vcoCtaTra.getValueAt(1));
                                txtDesCorCtaTra.setText(vcoCtaTra.getValueAt(2));
                                txtDesLarCtaTra.setText(vcoCtaTra.getValueAt(3));
                                strUbiCtaTra=vcoCtaTra.getValueAt(4);
                            }
                            else
                            {
                                txtDesCorCtaTra.setText(strDesCorCtaTra);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCtaTra.buscar("a1.tx_desLar", txtDesLarCtaTra.getText()))
                        {
                            txtCodCtaTra.setText(vcoCtaTra.getValueAt(1));
                            txtDesCorCtaTra.setText(vcoCtaTra.getValueAt(2));
                            txtDesLarCtaTra.setText(vcoCtaTra.getValueAt(3));
                            strUbiCtaTra=vcoCtaTra.getValueAt(4);
                        }
                        else
                        {
                            vcoCtaTra.setCampoBusqueda(2);
                            vcoCtaTra.setCriterio1(11);
                            vcoCtaTra.cargarDatos();
                            vcoCtaTra.show();
                            if (vcoCtaTra.getSelectedButton()==vcoCtaTra.INT_BUT_ACE)
                            {
                                txtCodCtaTra.setText(vcoCtaTra.getValueAt(1));
                                txtDesCorCtaTra.setText(vcoCtaTra.getValueAt(2));
                                txtDesLarCtaTra.setText(vcoCtaTra.getValueAt(3));
                                strUbiCtaTra=vcoCtaTra.getValueAt(4);
                            }
                            else
                            {
                                txtDesLarCtaTra.setText(strDesLarCtaTra);
                            }
                        }
                        break;
                }              

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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaDeb(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCtaDeb.setText("");
                txtDesCorCtaDeb.setText("");
                txtDesLarCtaDeb.setText("");
                strUbiCtaDeb="";
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta";
                strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND tx_ubiCta='D'";
                strSQL+=" ORDER BY tx_codCta";
                System.out.println("mostrarVenConCtaDeb:" + strSQL);

                vcoCtaDeb.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCtaDeb.setCampoBusqueda(1);
                        vcoCtaDeb.show();
                        if (vcoCtaDeb.getSelectedButton()==vcoCtaDeb.INT_BUT_ACE)
                        {
                            txtCodCtaDeb.setText(vcoCtaDeb.getValueAt(1));
                            txtDesCorCtaDeb.setText(vcoCtaDeb.getValueAt(2));
                            txtDesLarCtaDeb.setText(vcoCtaDeb.getValueAt(3));
                            strUbiCtaDeb=vcoCtaDeb.getValueAt(4);
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCtaDeb.buscar("a1.tx_codCta", txtDesCorCtaDeb.getText()))
                        {
                            txtCodCtaDeb.setText(vcoCtaDeb.getValueAt(1));
                            txtDesCorCtaDeb.setText(vcoCtaDeb.getValueAt(2));
                            txtDesLarCtaDeb.setText(vcoCtaDeb.getValueAt(3));
                            strUbiCtaDeb=vcoCtaDeb.getValueAt(4);
                        }
                        else
                        {
                            vcoCtaDeb.setCampoBusqueda(1);
                            vcoCtaDeb.setCriterio1(11);
                            vcoCtaDeb.cargarDatos();
                            vcoCtaDeb.show();
                            if (vcoCtaDeb.getSelectedButton()==vcoCtaDeb.INT_BUT_ACE)
                            {
                                txtCodCtaDeb.setText(vcoCtaDeb.getValueAt(1));
                                txtDesCorCtaDeb.setText(vcoCtaDeb.getValueAt(2));
                                txtDesLarCtaDeb.setText(vcoCtaDeb.getValueAt(3));
                                strUbiCtaDeb=vcoCtaDeb.getValueAt(4);
                            }
                            else
                            {
                                txtDesCorCtaDeb.setText(strDesCorCtaDeb);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCtaDeb.buscar("a1.tx_desLar", txtDesLarCtaDeb.getText()))
                        {
                            txtCodCtaDeb.setText(vcoCtaDeb.getValueAt(1));
                            txtDesCorCtaDeb.setText(vcoCtaDeb.getValueAt(2));
                            txtDesLarCtaDeb.setText(vcoCtaDeb.getValueAt(3));
                            strUbiCtaDeb=vcoCtaDeb.getValueAt(4);
                        }
                        else
                        {
                            vcoCtaDeb.setCampoBusqueda(2);
                            vcoCtaDeb.setCriterio1(11);
                            vcoCtaDeb.cargarDatos();
                            vcoCtaDeb.show();
                            if (vcoCtaDeb.getSelectedButton()==vcoCtaDeb.INT_BUT_ACE)
                            {
                                txtCodCtaDeb.setText(vcoCtaDeb.getValueAt(1));
                                txtDesCorCtaDeb.setText(vcoCtaDeb.getValueAt(2));
                                txtDesLarCtaDeb.setText(vcoCtaDeb.getValueAt(3));
                                strUbiCtaDeb=vcoCtaDeb.getValueAt(4);
                            }
                            else
                            {
                                txtDesLarCtaDeb.setText(strDesLarCtaDeb);
                            }
                        }
                        break;
                }              

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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCtaTra()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            vcoCtaTra=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas acreedoras", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCtaTra.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCtaTra.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCtaDeb()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a2.tx_ubiCta");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Ubicación");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            vcoCtaDeb=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas deudoras", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCtaDeb.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCtaDeb.setConfiguracionColumna(4, javax.swing.JLabel.CENTER);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /*JoséMario 22/Ene/2016*/
    private boolean regresarDiarioPredocumento(java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc )
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        String strCadena;
        try
        {
            stmLoc = con.createStatement();
            strCadena = "";
            strCadena+= " UPDATE tbm_cabDia \n";
            strCadena+= " SET st_reg = 'O'  \n";
            strCadena+= " WHERE co_emp="+ intCodEmp +" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc+" AND co_dia="+intCodDoc+"; \n";
            System.out.println("regresarDiarioPredocumento: \n" + strCadena);
            stmLoc.executeUpdate(strCadena);
            stmLoc.close();
            stmLoc=null;
        }
        catch(java.sql.SQLException Evt){    blnRes=false;       objUti.mostrarMsgErr_F1(this, Evt);         }
        catch(Exception Evt){     objUti.mostrarMsgErr_F1(this, Evt);          blnRes=false;        }
        return blnRes;
    }
    /*JoséMario 22/Ene/2016*/
    /*TonySanginez 05/Jun/2017*/
    private boolean actualizarFechaCieCaj(java.sql.Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc )
    {
        boolean blnRes=true;
        java.sql.Statement stmLoc;
        String strCadena;
        try
        {
            stmLoc = con.createStatement();
            strCadena = "";
            strCadena+= " UPDATE tbm_cabDia \n";
            strCadena+= " SET fe_dia =  '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
            strCadena+= " WHERE co_emp="+ intCodEmp +" AND co_loc="+intCodLoc+" AND co_tipDoc="+intCodTipDoc+" AND co_dia="+intCodDoc+"; \n";
            System.out.println("regresarDiarioPredocumento: \n" + strCadena);
            stmLoc.executeUpdate(strCadena);
            stmLoc.close();
            stmLoc=null;
        }
        catch(java.sql.SQLException Evt){    blnRes=false;       objUti.mostrarMsgErr_F1(this, Evt);         }
        catch(Exception Evt){     objUti.mostrarMsgErr_F1(this, Evt);          blnRes=false;        }
        return blnRes;
    }
    /*TonySanginez 05/Jun/2017*/
    
}
