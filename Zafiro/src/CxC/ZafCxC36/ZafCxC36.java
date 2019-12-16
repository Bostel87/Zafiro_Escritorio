/*
 * ZafCxC36.java
 * Created on 20 de diciembre de 2008 
 */ 
package CxC.ZafCxC36; 

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafRptSis.ZafRptSis;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;  
import Librerias.ZafPopupMenu.ZafPopupMenu;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiBut.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtCon.ZafTblCelEdiTxtCon;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafVenCon.ZafVenConCxC01;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafUtil.ZafAjuAutSis;

/**
 *  
 * @author Lizbeth
 */

public class ZafCxC36 extends javax.swing.JInternalFrame {
     //Declaración de variables globales para el JTable
    
    final int INT_TBL_DAT_LIN=0;//0             //LINEA DE NUMEROS DE REGISTROS EN LA TABLA///
    final int INT_TBL_DAT_SEL=1;///1            //LINEA PARA CASILLA DE SELECCION DE REGISTROS
    final int INT_TBL_BUT_CLI=2;//2             //LINEA DE BOTONES PARA MOSTRAR LOS CLIENTES  
    final int INT_TBL_DAT_COD_CLI=3;///3        //CODIGO DEL CLIENTE  
    final int INT_TBL_DAT_NOM_CLI=4;///4        //NOMBRE DEL CLIENTE
    final int INT_TBL_DAT_COD_LOC=5;///5        //CODIGO DEL LOCAL
    final int INT_TBL_DAT_COD_TIP_DOC=6;///6    //CODIGO DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_DES_COR=7;///7        //DESCRIPCION CORTA DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_TIP_DOC=8;///8        //DESCRIPCIÓN DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_COD_DOC=9;///9        //CÓDIGO DEL DOCUMENTO
    final int INT_TBL_DAT_COD_REG=10;///10      //CODIGO DEL REGISTRO
    final int INT_TBL_NUM_DOC=11;//11           //NUMERO DE LA FACTURA O DOCUMENTO  
    final int INT_TBL_DAT_FEC_DOC=12;///12      //FECHA DEL DOCUMENTO
    final int INT_TBL_DAT_DIA_CRE=13;///13      //DIAS DE CREDITO
    final int INT_TBL_DAT_FEC_VEN=14;///14      //FECHA DE VENCIMIENTO
    final int INT_TBL_DAT_POR_RET=15;///15      //PORCENTAJE DE RETENCIï¿½NDEL DOCUMENTO
    final int INT_TBL_DAT_VAL_DOC=16;//16       //VALOR TOTAL DEL DOCUMENTO
    final int INT_TBL_DAT_VAL_PND=17;///17      //VALOR PENDIENTE DEL DOCUMENTO
    final int INT_TBL_DAT_VAL_PND_INI=18;///18  //Valor Pendiente Inicial
    final int INT_TBL_ABO_DOC=19;///19          //ABONO DEL DOCUMENTO
    final int INT_TBL_DAT_AUX_ABO=20;///20      //Abono Auxiliar
    final int INT_TBL_DAT_NAT_DOC=21;///21      //Naturaleza del Documento
    final int INT_TBL_DAT_VAL_ABO_PAG=22;///22  //Valor Abono Pagado
    boolean blnCambios = false; //Detecta que se hizo cambios en el documento
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    javax.swing.JInternalFrame jfrThis;                   
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDatExc, vecCabExc;
    private Vector vecDatPnd, vecCabPnd;
    private boolean blnHayCam;                               //Determina si hay cambios en el formulario.
    private String strEstImpDoc;
    private ZafColNumerada objColNumExc, objColNumPnd;
    private ZafPopupMenu objPopMnu;
    private int FilSel;
    private ZafTblCelEdiButVco objTblCelEdiButVcoBco;        //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBco;        //Editor: JTextField de consulta en celda.
    private ZafDefTblCelRen objDefTblCelRen;                 //Formatear columnas en JTable.
    private ZafMouMotAda objMouMotAda;                       //ToolTipText en TableHeader.
    private Vector  vecAux;
    private boolean blnCon;
    private ZafToolBar objToolBar;//true: Continua la ejecución del hilo.
    private String strTit, sSQL, strFiltro;
    private String strCodCli, strDesLarCli,strIdePro, strDirPro,strDesCorCta,strDesLarCta; 
    private ZafTblCelEdiTxt objTblCelEdiTxtNumDocExc,objTblCelEdiTxtNumDocPnd, objTblCelEdiTxtVar;
    private ZafTblCelEdiTxtCon objTblCelEdiTxtCon, objTblCelEdiTxtConCli, objTblCelEdiTxtConTipDoc, objTblCelEdiTxtConBco;
    private java.util.Vector vecRegExc, vecRegPnd;
    private mitoolbar objTooBar;
    private String sSQLCon, strMod;
    private java.sql.Connection conCab, con, conCnsDet,conAnu;
   private int k=0, banclick=0, bancelmod=0, banbot=0, banmod=0, intVarEliReg=0;
    private java.sql.Statement stmCab, stm, stmCnsDet, stmAnu;
    private java.sql.ResultSet rstCab, rst, rstCnsDet;
    private tblHilo objHilo;
    private ZafAsiDia objAsiDia;
    private int CAN_REG_DET=0;
    private String STRNUMDOC="";
    private ZafTblMod objTblModExc;
    private ZafTblMod objTblModPnd;
    private int intFilSel;
    private int REGINSDETFAC=0;
    private String STRNATDOC="";
    private ZafTblPopMnu objTblPopMnuExc, objTblPopMnuPnd;     
    private ZafCxC36.ZafTblModLis objTblModLisExc;
    private ZafCxC36.ZafTblModLis objTblModLisPnd;
    private double VAL_TOT_DOC=0;
    private ZafRptSis objRptSis;
    
   //creacion de check
    private ZafTblCelRenChk objTblCelRenChkExc;
    private ZafTblCelRenChk objTblCelRenChkPnd;
    private ZafTblCelEdiChk objTblCelEdiChkExc;
    private ZafTblCelEdiChk objTblCelEdiChkPnd;
    private int intbanclimod=0;
    private int IntTipResModDoc=0, IntTipResModFecDoc=0 ;
    //creacion de label
    private ZafTblCelRenLbl objTblCelRenLblExc;
    private ZafTblCelRenLbl objTblCelRenLblPnd;
    private ZafTblCelEdiTxt objTblCelEdiTxtExc;
    private ZafTblCelEdiTxt objTblCelEdiTxtPnd;
    private ZafTblCelEdiChk objTblCelEdiChkMain;
    private int j=1, bancon=0, banins=0, I=0, J=0, intTotRegSelI=0, intTotRegSelJ=0, intUltNumDocAlt=0;
    private ZafTblEdi objTblEdi;
    private double dblMonDoc=0.00, VAL_DOC_EXC=0.00, VAL_DOC_PEN=0.00;
    private double dblValAboBef=0.00;
    private double varTmp=0.00;
    private int intSig=1, intSigAbo=1;                
    private String strTipDoc, strDesLarTipDoc;       
    private String strUbiCta;                             
    private java.util.Date datFecAux;                         
    ZafVenCon objVenConTipdoc;
    private ZafVenCon vcoTipDoc;                
    private ZafVenCon vcoCli;                         
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private ZafTblCelEdiBut objTblCelEdiButCliCxC01, objTblCelEdiButCliCxC02, objTblCelEdiButCliCxC03, objTblCelEdiButCliCxC04;
    private ZafTblCelRenBut objTblCelRenButCliCxC01, objTblCelRenButCliCxC02, objTblCelRenButCliCxC03, objTblCelRenButCliCxC04;
    private ZafTblCelEdiButGen objTblCelEdiButGenCxC01, objTblCelEdiButGenCxC02, objTblCelEdiButGenCxC03, objTblCelEdiButGenCxC04; 
    private ZafTblCelEdiBut objTblCelEdiButPrvCxP01, objTblCelEdiButPrvCxP02, objTblCelEdiButPrvCxP03, objTblCelEdiButPrvCxP04;
    private ZafTblCelRenBut objTblCelRenButPrvCxP01, objTblCelRenButPrvCxP02, objTblCelRenButPrvCxP03, objTblCelRenButPrvCxP04;
    private ZafTblCelEdiButGen objTblCelEdiButGenCxP01, objTblCelEdiButGenCxP02, objTblCelEdiButGenCxP03, objTblCelEdiButGenCxP04;
    private ZafTblCelEdiBut objTblCelEdiBut;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;  
    ///para ventana de CREDITOS///
    private ZafVenConCxC01 objVenConCxC01;
    private ZafVenConCxC01 objVenConCxC02;
    private ZafVenConCxC01 objVenConCxC03;
    private ZafVenConCxC01 objVenConCxC04;
    private int  intbanmodcel=0;
    ///para ventana de DEBITOS///
    private ZafVenConCxC01 objVenConCxP01;
    private ZafVenConCxC01 objVenConCxP02;
    private ZafVenConCxC01 objVenConCxP03;
    private ZafVenConCxC01 objVenConCxP04;
    private String strNomSubRep="";
    private String strNomSubRepPnd="";
    private String strNomRepTuv="";
    private String strNomRepCas="";
    private String strNomRepNos="";
    private String strNomRepDim="";
    private String strNomRepDef="";
   //*Dario_Objeto del Tipo ZafAjuAutSis*/
   private ZafAjuAutSis objAjuAutSis;
   private String strFecDocIni;

   private ZafThreadGUI objThrGUI;

   
   protected void clearNumFactExc(int intFilaSel){
   tblValExc.setValueAt("", intFilaSel,  INT_TBL_NUM_DOC);
    }
     protected void clearNumFactPnd(int intFilaSel){
        tblValPnd.setValueAt("", intFilaSel,  INT_TBL_NUM_DOC);
    }

    private String arrNumFacCre[], arrCodLocCre[], arrTipDocCre[], arrCodDocCre[];
    private double arrValDocCre[];
    private double arrValAboCre[];
    private double arrSalRegCre[];
    private String arrNumFacPnd[], arrCodLocPnd[], arrTipDocPnd[], arrCodDocPnd[];
    private double arrValDocPnd[];
    private double arrValAboPnd[];
    private double arrSalRegPnd[];
    private int cant=0, p=0, INTCODLOC=0, INTCODDOC=0, INTTIPDOC=0, CAN_REG_ELI_PND=0, CAN_REG_ELI_EXC=0, CAN_REG_ANU_PND=0, CAN_REG_ANU_EXC=0;
    private String STRESTLIN, strEstLin;


    public ZafCxC36(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objUti=new ZafUtil();
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            txtFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtFecDoc.setPreferredSize(new java.awt.Dimension(250, 20));
            txtFecDoc.setText("");
            panCabDoc.add(txtFecDoc);
            txtFecDoc.setBounds(580, 6, 100, 20);
            objTooBar=new mitoolbar(this);

              ///*Configurar Objeto para llamar al Reporte*////
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            
            panBar.add(objTooBar);
            if (!configurarFrm())
                exitForm();
            objAsiDia=new ZafAsiDia(objParSis);
            panDia.add(objAsiDia, java.awt.BorderLayout.CENTER);           
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }



    public ZafCxC36(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            objUti=new ZafUtil();
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            txtFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtFecDoc.setPreferredSize(new java.awt.Dimension(250, 20));
            txtFecDoc.setText("");
            panCabDoc.add(txtFecDoc);
            txtFecDoc.setBounds(580, 6, 100, 20);
            objTooBar=new mitoolbar(this);

            panBar.add(objTooBar);

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);



            if (!configurarFrm())
                exitForm();
            objAsiDia=new ZafAsiDia(objParSis);
            panDia.add(objAsiDia, java.awt.BorderLayout.CENTER);
            strMod="1";
            consultarReg();
            objTooBar.setVisible(false);



        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }






    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCabDoc = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtTipDoc = new javax.swing.JTextField();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        lblCom = new javax.swing.JLabel();
        txtNumAltUno = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        panCenDat = new javax.swing.JPanel();
        panValExc = new javax.swing.JPanel();
        spnValExc = new javax.swing.JScrollPane();
        tblValExc = new javax.swing.JTable();
        panValPnd = new javax.swing.JPanel();
        spnValPnd = new javax.swing.JScrollPane();
        tblValPnd = new javax.swing.JTable();
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
        jPanel1 = new javax.swing.JPanel();
        lblMonDocExc = new javax.swing.JLabel();
        txtMonDocExc = new javax.swing.JTextField();
        lblMonDocPnd = new javax.swing.JLabel();
        txtMonDocPnd = new javax.swing.JTextField();
        lblDif = new javax.swing.JLabel();
        lblValDif = new javax.swing.JLabel();
        panDia = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 70));
        panCabDoc.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblTipDoc.setText("Tipo de documento:");
        panCabDoc.add(lblTipDoc);
        lblTipDoc.setBounds(4, 0, 94, 20);

        txtTipDoc.setMaximumSize(null);
        txtTipDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipDocActionPerformed(evt);
            }
        });
        txtTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTipDocFocusLost(evt);
            }
        });
        panCabDoc.add(txtTipDoc);
        txtTipDoc.setBounds(108, 4, 60, 20);

        txtNomDoc.setMaximumSize(null);
        txtNomDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomDocActionPerformed(evt);
            }
        });
        txtNomDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomDocFocusLost(evt);
            }
        });
        panCabDoc.add(txtNomDoc);
        txtNomDoc.setBounds(164, 4, 280, 20);

        butDoc.setFont(new java.awt.Font("SansSerif", 1, 12));
        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });
        panCabDoc.add(butDoc);
        butDoc.setBounds(446, 4, 20, 20);

        lblCom.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCom.setText("Número Alterno1:");
        lblCom.setMaximumSize(new java.awt.Dimension(66, 15));
        lblCom.setMinimumSize(new java.awt.Dimension(66, 15));
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panCabDoc.add(lblCom);
        lblCom.setBounds(470, 30, 90, 15);

        txtNumAltUno.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNumAltUno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumAltUnoActionPerformed(evt);
            }
        });
        txtNumAltUno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumAltUnoFocusLost(evt);
            }
        });
        panCabDoc.add(txtNumAltUno);
        txtNumAltUno.setBounds(580, 30, 90, 20);

        lblFecDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setMaximumSize(new java.awt.Dimension(89, 15));
        lblFecDoc.setMinimumSize(new java.awt.Dimension(89, 15));
        lblFecDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        lblFecDoc.setRequestFocusEnabled(false);
        panCabDoc.add(lblFecDoc);
        lblFecDoc.setBounds(470, 0, 110, 20);

        lblCodDoc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblCodDoc.setText("Código del Doc. :");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panCabDoc.add(lblCodDoc);
        lblCodDoc.setBounds(10, 35, 110, 10);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        panCabDoc.add(txtCodDoc);
        txtCodDoc.setBounds(110, 30, 60, 20);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panCabDoc.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipDocActionPerformed(evt);
            }
        });
        panCabDoc.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        panDat.add(panCabDoc, java.awt.BorderLayout.NORTH);

        panCenDat.setEnabled(false);
        panCenDat.setLayout(new java.awt.GridLayout(2, 0));

        panValExc.setAutoscrolls(true);
        panValExc.setPreferredSize(new java.awt.Dimension(935, 120));
        panValExc.setLayout(new java.awt.BorderLayout());

        spnValExc.setBorder(javax.swing.BorderFactory.createTitledBorder("Creditos"));
        spnValExc.setAutoscrolls(true);
        spnValExc.setPreferredSize(new java.awt.Dimension(20, 100));

        javax.swing.JCheckBox selReg = new javax.swing.JCheckBox();
        tblValExc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Sel", "Tipo de documento", "Nombre del documento", "Numero de documento", "Fecha del documento", "Días de crédito", "Fecha de vencimiento", "% de Retención", "Valor del documento", "Valor pendiente", "Codigo del Registro", "Codigo Local", "Codigo Documento", "Abono", "Abono Auxiliar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblValExc.setMinimumSize(new java.awt.Dimension(600, 64));
        spnValExc.setViewportView(tblValExc);

        panValExc.add(spnValExc, java.awt.BorderLayout.CENTER);

        panCenDat.add(panValExc);

        panValPnd.setAutoscrolls(true);
        panValPnd.setPreferredSize(new java.awt.Dimension(20, 120));
        panValPnd.setLayout(new java.awt.BorderLayout());

        spnValPnd.setBorder(javax.swing.BorderFactory.createTitledBorder("Debitos"));
        spnValPnd.setAutoscrolls(true);
        spnValPnd.setPreferredSize(new java.awt.Dimension(20, 100));

        tblValPnd.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Linea", "Sel", "Tipo de documento", "Nombre del documento", "Numero de documento", "Fecha del documento", "Días de crédito", "Fecha de vencimiento", "% de Retención", "Valor del documento", "Valor pendiente", "Codigo del Registro", "Codigo Local", "Codigo Documento", "Abono", "Abono Auxiliar"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblValPnd.setMinimumSize(new java.awt.Dimension(600, 64));
        spnValPnd.setViewportView(tblValPnd);

        panValPnd.add(spnValPnd, java.awt.BorderLayout.CENTER);

        panCenDat.add(panValPnd);

        panDat.add(panCenDat, java.awt.BorderLayout.CENTER);

        spnObs.setPreferredSize(new java.awt.Dimension(519, 80));

        panObs.setPreferredSize(new java.awt.Dimension(518, 40));
        panObs.setLayout(new java.awt.BorderLayout());

        panLbl.setPreferredSize(new java.awt.Dimension(92, 16));
        panLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:");
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panLbl.add(lblObs1);

        lblObs2.setFont(new java.awt.Font("Dialog", 0, 12));
        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:");
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 8));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panLbl.add(lblObs2);

        panObs.add(panLbl, java.awt.BorderLayout.WEST);

        panTxa.setPreferredSize(new java.awt.Dimension(2, 10));
        panTxa.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        spnObs1.setViewportView(txaObs1);

        panTxa.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panTxa.add(spnObs2);

        panObs.add(panTxa, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(200, 16));

        lblMonDocExc.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonDocExc.setText("Monto Credito:");
        lblMonDocExc.setMaximumSize(new java.awt.Dimension(69, 8));
        lblMonDocExc.setMinimumSize(new java.awt.Dimension(69, 8));
        lblMonDocExc.setPreferredSize(new java.awt.Dimension(110, 15));
        lblMonDocExc.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(lblMonDocExc);

        txtMonDocExc.setMaximumSize(null);
        txtMonDocExc.setMinimumSize(new java.awt.Dimension(6, 30));
        txtMonDocExc.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel1.add(txtMonDocExc);

        lblMonDocPnd.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblMonDocPnd.setText("Monto Debito:");
        lblMonDocPnd.setMaximumSize(new java.awt.Dimension(69, 8));
        lblMonDocPnd.setPreferredSize(new java.awt.Dimension(110, 16));
        jPanel1.add(lblMonDocPnd);

        txtMonDocPnd.setMaximumSize(null);
        txtMonDocPnd.setMinimumSize(new java.awt.Dimension(6, 40));
        txtMonDocPnd.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel1.add(txtMonDocPnd);

        lblDif.setFont(new java.awt.Font("SansSerif", 0, 11));
        lblDif.setText("Diferencia:");
        lblDif.setMaximumSize(new java.awt.Dimension(69, 15));
        lblDif.setMinimumSize(new java.awt.Dimension(69, 15));
        lblDif.setPreferredSize(new java.awt.Dimension(110, 15));
        lblDif.setRequestFocusEnabled(false);
        jPanel1.add(lblDif);

        lblValDif.setFont(new java.awt.Font("SansSerif", 0, 14));
        lblValDif.setMaximumSize(null);
        lblValDif.setMinimumSize(new java.awt.Dimension(6, 21));
        lblValDif.setPreferredSize(new java.awt.Dimension(70, 20));
        jPanel1.add(lblValDif);

        panObs.add(jPanel1, java.awt.BorderLayout.EAST);

        spnObs.setViewportView(panObs);

        panDat.add(spnObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panDat);

        panDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de Diario", panDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-710)/2, (screenSize.height-452)/2, 710, 452);
    }// </editor-fold>//GEN-END:initComponents
    
    private void txtNumAltUnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumAltUnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumAltUnoActionPerformed

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtNomDoc.getText().equals(""))
            {
                
                txtCodTipDoc.setText("");
                txtTipDoc.setText("");
                objTblModExc.removeAllRows();
                objTblModPnd.removeAllRows();
                txtMonDocExc.setText("");
                txtMonDocPnd.setText("");
               }else{
                mostrarVenConTipDoc(2);
                 if (txtCodDoc.getText().equals(""))
                {
                  objTblModExc.removeAllRows();
                  objTblModPnd.removeAllRows();
                  txtMonDocExc.setText("");
                  txtMonDocPnd.setText("");                    
                }else{
                    if(banins!=0)
                    {
                        txtMonDocExc.setText("");
                        txtMonDocPnd.setText("");
                    }}}}
           else
            txtNomDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtNomDocFocusLost

    private void txtNomDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusGained
       strDesLarTipDoc=txtNomDoc.getText();
       txtNomDoc.selectAll();
    }//GEN-LAST:event_txtNomDocFocusGained

    private void txtNomDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomDocActionPerformed
        txtNomDoc.transferFocus();
    }//GEN-LAST:event_txtNomDocActionPerformed

    private void txtTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusGained
        strTipDoc=txtTipDoc.getText();
        txtTipDoc.selectAll();        
    }//GEN-LAST:event_txtTipDocFocusGained

    private void txtTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocActionPerformed
        txtTipDoc.transferFocus();
    }//GEN-LAST:event_txtTipDocActionPerformed

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
    }//GEN-LAST:event_txtCodDocActionPerformed

    /**
     * Esta funciónn permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una bï¿½squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de bï¿½squeda a realizar.
     * @return true: Si no se presentï¿½ ningï¿½n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus)
    {
        boolean blnRes=true;
        int n=0;
        try
        {
            switch (intTipBus)
            {
                
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){                        
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));  
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        txtNumAltUno.setText(vcoTipDoc.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtTipDoc.getText())){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1)); 
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                    }else{
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                       }else{
                            txtTipDoc.setText(strTipDoc);
                        }}
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtNomDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                    }else{ 
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1)); 
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        }else{
                            txtNomDoc.setText(strDesLarTipDoc);
                        }}
                    break;
            } }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private void txtTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusLost
 
      if (!txtTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (txtTipDoc.getText().equals("")){
                txtCodTipDoc.setText("");
                txtNomDoc.setText("");
            }else{
                mostrarVenConTipDoc(1);
            }
        }else
            txtTipDoc.setText(strTipDoc);
    }//GEN-LAST:event_txtTipDocFocusLost
    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        mostrarVenConTipDoc(0);
        if (txtTipDoc.getText().equals(""))
        {
            objTblModExc.removeAllRows();
            objTblModPnd.removeAllRows();
            txtMonDocExc.setText("");
            txtMonDocPnd.setText("");
        }else {
            if(bancon!=0 || banins!=0)
            {
                txtMonDocExc.setText("");
                txtMonDocPnd.setText("");
            }}
    }//GEN-LAST:event_butDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
       String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Esta seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
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

private void txtNumAltUnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumAltUnoFocusLost
  System.out.println("---presiono primero modificar --intbanclimod--: " +intbanclimod);
        if(intbanclimod > 0)
            intbanmodcel++;
}//GEN-LAST:event_txtNumAltUnoFocusLost

    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butDoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDocExc;
    private javax.swing.JLabel lblMonDocPnd;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDif;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panCenDat;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panDia;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panLbl;
    private javax.swing.JPanel panObs;
    private javax.swing.JPanel panTxa;
    private javax.swing.JPanel panValExc;
    private javax.swing.JPanel panValPnd;
    private javax.swing.JScrollPane spnObs;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnValExc;
    private javax.swing.JScrollPane spnValPnd;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblValExc;
    private javax.swing.JTable tblValPnd;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtMonDocExc;
    private javax.swing.JTextField txtMonDocPnd;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtNumAltUno;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtTipDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta funciï¿½n permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg()
    {
        int intPosRel;
        boolean blnRes=true;
        try
        { 
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc ";
                strSQL+=", a1.ne_numDoc1 ";
                strSQL+=", a1.nd_monDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp ";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" AND a1.st_reg <> 'E'";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    cargarCabTipoDoc(rst.getInt("co_tipDoc"));
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    txtFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strFecDocIni=txtFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp"); //Double
                    strAux=rst.getString("ne_numDoc1");
                    txtNumAltUno.setText((strAux==null)?"":strAux);
                    txtMonDocExc.setText("" + Math.abs(rst.getDouble("nd_monDoc")));
                    txtMonDocPnd.setText("" + Math.abs(rst.getDouble("nd_monDoc")));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("A"))
                        strAux="Activo";
                    else if (strAux.equals("I"))
                        strAux="Anulado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            //strAux.close();
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
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifï¿½quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
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
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numdoc1";
                strSQL+=" FROM tbm_cabpag AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                strSQL+=" AND a1.co_loc=" + intCodLoc;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_doc=" + txtCodDoc.getText();
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontró " + rstCab.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    objTooBar.setEstadoRegistro("Activo");
                    rstCab.first();
                     cargarReg(); 
                    strSQLCon=strSQL;
                }else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
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
     * Esta función permite cargar el registro seleccionado.
         * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=true;
        try
        {
            if (cargarCabReg())
            {
                if (cargarDetRegExc())
                {
                    if (cargarDetRegPnd())
                    {                    
               objAsiDia.consultarDiario(objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                    }                
                }
            }
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }    

     /**
     * Esta funciï¿½n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
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
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numdoc1, a1.nd_mondoc, a1.fe_doc, a1.st_reg";
                strSQL+=" FROM tbm_cabPag AS a1";                                
                strSQL+=" LEFT OUTER JOIN (tbm_cabTipDoc AS a2";
                strSQL+=" LEFT OUTER JOIN tbr_tipdocprg AS z1";
                strSQL+=" ON a2.co_emp=z1.co_emp AND a2.co_loc=z1.co_loc AND a2.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";                                
                strSQL+=" AND z1.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a1.st_reg <> 'E' ";
                  strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = "+strAux+" ";
 		if (txtFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc ="+strAux+" ";
                strAux=txtNumAltUno.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 = "+strAux+"";

                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("---consultarReg: " +strSQL);
                rstCab=stmCab.executeQuery(strSQL);
           if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }else{
                    mostrarMsgInf("No se ha encontrado ningun registro que cumpla el criterio de bï¿½squeda especificado.");
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
     *Esta funcion elimina la cabecera del documento en la base de datos en la tabla tbm_cabpag
     *@return true: si elimina
     *<BR>false: caso contarrio
     */
    private boolean eliminarCab()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();

                ///Obtener la fecha del servidor.///
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

                ///Armar la sentencia SQL.///
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='E'"; 
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
		System.out.println("---eliminarCab()--: " +strSQL+";");
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
    
    private void calcularAboTot()
        {
            
        double dblValExc=0, dblValPnd=0, dblTotExc=0, dblTotPnd=0, dblValDif=0.0;
        int intFilProExc, intFilProPnd, i;
        String strConCelExc, strConCelPnd;
        try
        {
            intFilProExc=objTblModExc.getRowCount();
            for (i=0; i<intFilProExc; i++)
            {
                strConCelExc=(objTblModExc.getValueAt(i, INT_TBL_ABO_DOC)==null)?"":objTblModExc.getValueAt(i, INT_TBL_ABO_DOC).toString();
                dblValExc=(objUti.isNumero(strConCelExc))?Double.parseDouble(strConCelExc):0;
                dblTotExc+=dblValExc;
            }
                txtMonDocExc.setText("" + objUti.redondeo(dblTotExc,6)); 
                intFilProPnd=objTblModPnd.getRowCount();
            for (i=0; i<intFilProPnd; i++)
            {
                strConCelPnd=(objTblModPnd.getValueAt(i, INT_TBL_ABO_DOC)==null)?"":objTblModPnd.getValueAt(i, INT_TBL_ABO_DOC).toString();
                dblValPnd=(objUti.isNumero(strConCelPnd))?Double.parseDouble(strConCelPnd):0;
                dblTotPnd+=dblValPnd;
            }            
            txtMonDocPnd.setText("" + objUti.redondeo(dblTotPnd,6)); 
            ////*Calcular la Diferencia entre el CREDITO y DEBITO*////
            dblValDif = dblTotExc - dblTotPnd;
            System.out.println("---dblValDif: " + dblValDif);
            lblValDif.setText(""+objUti.redondeo(dblValDif,8));
        }
        catch (NumberFormatException e)
        {
            txtMonDocExc.setText("[ERROR]");
            txtMonDocPnd.setText("[ERROR]");
            System.out.println("Error de suma de total...");
        }
    }

    /** Configurar el formulario */
    private boolean configurarFrm() 
    {
        boolean blnRes=true;
        try 
        { 
            //titulo de la ventana
            this.setTitle(objParSis.getNombreMenu()+ " v0.3");
            lblTit.setText(""+objParSis.getNombreMenu());
            int intCodMnu=0;
            intCodMnu = objParSis.getCodigoMenu();
            System.out.println("---intCodMnu--- " + intCodMnu);
            configurarVenConTipDoc();
            //Eddye_ventana de documentos EXCESOS///            
            configurarVenConCreCxC01();
            configurarVenConCreCxC02();
            configurarVenConCreCxC03();
            configurarVenConCreCxC04();

            ///Eddye_ventana de documentos PENDIENTES///
            configurarVenConDebCxP01();
            configurarVenConDebCxP02();
            configurarVenConDebCxP03();
            configurarVenConDebCxP04();
            
            //asigno el color de fondo de los campos 
            txtTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNumAltUno.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtMonDocExc.setBackground(objParSis.getColorCamposSistema());
            txtMonDocPnd.setBackground(objParSis.getColorCamposSistema());     
            txtMonDocExc.setEditable(false);
            txtMonDocPnd.setEditable(false);
             objAjuAutSis = new Librerias.ZafUtil.ZafAjuAutSis(objParSis);
            //Configurar JTable: Establecer el modelo para Tabla de Credito//
            vecDatExc=new Vector();                                                      
            vecCabExc=new Vector(25);                                                    
            vecCabExc.clear();
            vecCabExc.add(INT_TBL_DAT_LIN,"");
            vecCabExc.add(INT_TBL_DAT_SEL,"");
            vecCabExc.add(INT_TBL_BUT_CLI,"...");
            vecCabExc.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");
            vecCabExc.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCabExc.add(INT_TBL_DAT_COD_LOC,"Cod. Loc.");
            vecCabExc.add(INT_TBL_DAT_COD_TIP_DOC,"Cï¿½d. Tip. Doc.");
            vecCabExc.add(INT_TBL_DAT_DES_COR,"Tip. Doc.");
            vecCabExc.add(INT_TBL_DAT_TIP_DOC,"Tipo de Documento");
            vecCabExc.add(INT_TBL_DAT_COD_DOC,"Cod. Doc.");
            vecCabExc.add(INT_TBL_DAT_COD_REG,"Cod. Reg.");
            vecCabExc.add(INT_TBL_NUM_DOC,"Num.Doc.");
            vecCabExc.add(INT_TBL_DAT_FEC_DOC,"Fec. Doc.");
            vecCabExc.add(INT_TBL_DAT_DIA_CRE,"Dia. Cre.");
            vecCabExc.add(INT_TBL_DAT_FEC_VEN,"Fec. Ven.");
            vecCabExc.add(INT_TBL_DAT_POR_RET,"% Ret.");
            vecCabExc.add(INT_TBL_DAT_VAL_DOC,"Val. Doc.");
            vecCabExc.add(INT_TBL_DAT_VAL_PND,"Val. Pen.");
            vecCabExc.add(INT_TBL_DAT_VAL_PND_INI,"Valor Pend.INI.");
            vecCabExc.add(INT_TBL_ABO_DOC,"Abono");
            vecCabExc.add(INT_TBL_DAT_AUX_ABO,"Abono Aux.");
            vecCabExc.add(INT_TBL_DAT_NAT_DOC,"Nat. Doc.");
            vecCabExc.add(INT_TBL_DAT_VAL_ABO_PAG,"Val.Abo.Pag.");
            objTblModExc=new ZafTblMod();
            objTblModExc.setHeader(vecCabExc);
            objTblModExc.setColumnDataType(INT_TBL_ABO_DOC, objTblModExc.INT_COL_DBL, new Integer(0),null);//new Integer(0), null); ///INT_TBL_DAT_NUM_DOC                        
            tblValExc.setModel(objTblModExc);            
            //Configurar JTable: Establecer tipo de selección
            tblValExc.setRowSelectionAllowed(true);
            tblValExc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
                        
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumExc=new ZafColNumerada(tblValExc,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblValExc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            objTblPopMnuExc=new ZafTblPopMnu(tblValExc);

            //Configurar JTable: Establecer el ancho de las columnas.
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);///0
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(20);///1
            tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setPreferredWidth(25);///2
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(40);///3
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(60);///4
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(25);///5
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);///6
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(50);///7
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(10);///8
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(20);///9
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(20);///10
            tblValExc.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);///11
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(75);///12
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(28);///13
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(75);///14
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);///15
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);///16
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);///17
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(50);///18
            tblValExc.getColumnModel().getColumn(INT_TBL_ABO_DOC).setPreferredWidth(80);///19
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(80);///20
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(50);///21
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(50);//22
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblValExc.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblValPnd.getTableHeader().addMouseMotionListener(objMouMotAda);
            tblValExc.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Ocultar columnas del sistema de la tabla de crï¿½ditos.    
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);            
       
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);            
            
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);   

            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setResizable(false);  
            
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setResizable(false);  
            
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setResizable(false);
            

            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setResizable(false);
 
//          Configurar JTable: Establecer el modelo para Tabla de Débito
            vecDatPnd=new Vector();
            vecCabPnd=new Vector(25);
            vecCabPnd.clear();
            vecCabPnd.add(INT_TBL_DAT_LIN,"");
            vecCabPnd.add(INT_TBL_DAT_SEL,"");
            vecCabPnd.add(INT_TBL_BUT_CLI,"...");
            vecCabPnd.add(INT_TBL_DAT_COD_CLI,"Cod.Cli.");
            vecCabPnd.add(INT_TBL_DAT_NOM_CLI,"Cliente");
            vecCabPnd.add(INT_TBL_DAT_COD_LOC,"Cod. Loc.");
            vecCabPnd.add(INT_TBL_DAT_COD_TIP_DOC,"Cï¿½d. Tip. Doc.");
            vecCabPnd.add(INT_TBL_DAT_DES_COR,"Tip. Doc.");
            vecCabPnd.add(INT_TBL_DAT_TIP_DOC,"Tipo de Documento");
            vecCabPnd.add(INT_TBL_DAT_COD_DOC,"Cod. Doc.");
            vecCabPnd.add(INT_TBL_DAT_COD_REG,"Cod. Reg.");
            vecCabPnd.add(INT_TBL_NUM_DOC,"Num.Doc.");
            vecCabPnd.add(INT_TBL_DAT_FEC_DOC,"Fec. Doc.");
            vecCabPnd.add(INT_TBL_DAT_DIA_CRE,"Dia. Cre.");
            vecCabPnd.add(INT_TBL_DAT_FEC_VEN,"Fec. Ven.");
            vecCabPnd.add(INT_TBL_DAT_POR_RET,"% Ret.");
            vecCabPnd.add(INT_TBL_DAT_VAL_DOC,"Val. Doc.");
            vecCabPnd.add(INT_TBL_DAT_VAL_PND,"Val. Pen.");
            vecCabPnd.add(INT_TBL_DAT_VAL_PND_INI,"Valor Pend.INI.");
            vecCabPnd.add(INT_TBL_ABO_DOC,"Abono");
            vecCabPnd.add(INT_TBL_DAT_AUX_ABO,"Abono Aux.");
            vecCabPnd.add(INT_TBL_DAT_NAT_DOC,"Nat. Doc.");
            vecCabPnd.add(INT_TBL_DAT_VAL_ABO_PAG,"Val.Abo.Pag.");
            objTblModPnd=new ZafTblMod();
            objTblModPnd.setHeader(vecCabPnd);
            objTblModPnd.setColumnDataType(INT_TBL_ABO_DOC, objTblModPnd.INT_COL_DBL,new Integer(0), null);                
            tblValPnd.setModel(objTblModPnd);            
            
            //Configurar JTable: Establecer tipo de selección
            tblValPnd.setRowSelectionAllowed(true);
            tblValPnd.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumPnd=new ZafColNumerada(tblValPnd,INT_TBL_DAT_LIN);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblValPnd.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); 
            objTblPopMnuPnd=new ZafTblPopMnu(tblValPnd);
       
            //Configurar JTable: Establecer el ancho de las columnas.
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setPreferredWidth(25);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(40);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(60);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(25);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(10);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(75);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(28);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(75);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_ABO_DOC).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(50);
       
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblValPnd.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema de la tabla de dï¿½bitos.
                  
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);            
       
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);            
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);   
      
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setResizable(false);  
            
           
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setResizable(false);              
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setResizable(false);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setResizable(false);
            
            
            //para hacer editable las celdas
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_SEL);
            vecAux.add("" + INT_TBL_BUT_CLI);
            vecAux.add("" + INT_TBL_ABO_DOC);
            vecAux.add("" + INT_TBL_NUM_DOC);
            
            objTblModExc.setColumnasEditables(vecAux);
            objTblModPnd.setColumnasEditables(vecAux);
            vecAux=null;
            //setEditable(true);
            objTblEdi=new ZafTblEdi(tblValExc);
            objTblEdi=new ZafTblEdi(tblValPnd); 
            
            //setEditable(true);   
            objTblCelRenChkExc=new ZafTblCelRenChk();
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChkExc);
            objTblCelEdiChkExc=new ZafTblCelEdiChk();
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChkExc);   

            //para la tabla de créditos
               objTblCelEdiChkExc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
               System.out.println("afterEdit - DESPUES -- CLICK EN TABLA DE CREDITO - VALOR EXCESO");
                    if(objTblCelEdiChkExc.isChecked())
                    {
                        //objTblModExc.setValueAt(""+Math.abs(Double.parseDouble(objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_DAT_VAL_PND).toString())),tblValExc.getSelectedRow(),INT_TBL_ABO_DOC);                        
                        objTblModExc.setValueAt(objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValExc.getSelectedRow(),INT_TBL_ABO_DOC);
                        I++;
                        System.out.println("---I = " + I);
                    }else{
                        objTblModExc.setValueAt(null, tblValExc.getSelectedRow(), INT_TBL_ABO_DOC);
                        if(intTotRegSelI > 0)
                        {
                            I = intTotRegSelI;
                            System.out.println("---en la opcion de consultar reg I = " + I );
                        }
                        I--;
                        System.out.println("---I = " + I);
                        intTotRegSelI = I;
                    }
                calcularAboTot();
                objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
                }
                });
                
                
            //para la tabla de Débito    
            objTblCelRenChkPnd=new ZafTblCelRenChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChkPnd);
            objTblCelEdiChkPnd=new ZafTblCelEdiChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChkPnd); 

            objTblCelEdiChkPnd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit - DESPUES -- CLICK EN TABLA DE DEBITO - VALOR PENDIENTE");
                    if(objTblCelEdiChkPnd.isChecked())
                    {         
                     objTblModPnd.setValueAt(objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValPnd.getSelectedRow(),INT_TBL_ABO_DOC);                        
                        J++;
                        System.out.println("---J = " + J);
                    } else {
                        objTblModPnd.setValueAt(null, tblValPnd.getSelectedRow(), INT_TBL_ABO_DOC);
                        if(intTotRegSelJ > 0)
                        {
                            J = intTotRegSelJ;
                            System.out.println("---en la opcion de consultar reg J = " + J );
                        }
                        J--;
                        System.out.println("---J = " + J);
                        intTotRegSelJ = J;
                    }
                     calcularAboTot();
                   objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
                }
                });
                

            objTblCelEdiTxtNumDocExc=new ZafTblCelEdiTxt(tblValExc);            
            tblValExc.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellEditor(objTblCelEdiTxtNumDocExc);                    
            objTblCelEdiTxtNumDocExc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
             {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel=Integer.parseInt(""+tblValExc.getSelectedRow());
                    k=0;
                }   
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {                                                                                                                  
                    if (objTblCelEdiTxtNumDocExc.getText().equals(""))
                    {
                        objTblModExc.setChecked( false, tblValExc.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                         System.out.println("---intFilSel---NUM_DOC_EXC--- " + intFilSel);
                         intFilSel=tblValExc.getSelectedRow();
                         listaDocExc(intFilSel);
                }
                }
            }); 

            
            
            objTblCelEdiTxtNumDocPnd=new ZafTblCelEdiTxt(tblValPnd);            
            tblValPnd.getColumnModel().getColumn(INT_TBL_NUM_DOC).setCellEditor(objTblCelEdiTxtNumDocPnd);                    
            objTblCelEdiTxtNumDocPnd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
             {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    intFilSel=Integer.parseInt(""+tblValPnd.getSelectedRow());
                    k=0;
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {                                                                                                                  
                    if (objTblCelEdiTxtNumDocPnd.getText().equals(""))
                    {
                      objTblModPnd.setChecked( false, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                    System.out.println("---intFilSel---NUM_DOC_PND--- " + intFilSel);
                   intFilSel=tblValPnd.getSelectedRow();
                   listaDocPnd(intFilSel);
                   }
                }
            });
            objTblCelRenLblExc=new ZafTblCelRenLbl();
            objTblCelRenLblExc.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblExc.setTipoFormato(objTblCelRenLblExc.INT_FOR_NUM);
            objTblCelRenLblExc.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLblExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLblExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_ABO_DOC).setCellRenderer(objTblCelRenLblExc);                
            objTblCelRenLblPnd=new ZafTblCelRenLbl();
            objTblCelRenLblPnd.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPnd.setTipoFormato(objTblCelRenLblPnd.INT_FOR_NUM);
            objTblCelRenLblPnd.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                      
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLblPnd);              
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLblPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_ABO_DOC).setCellRenderer(objTblCelRenLblPnd);             
            objTblCelEdiTxtExc=new ZafTblCelEdiTxt(tblValExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_ABO_DOC).setCellEditor(objTblCelEdiTxtExc);                    
            objTblCelEdiTxtExc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                
                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit - DESPUES -- SE EDITO EL VALOR EN EXCESO");
                    if (objTblCelEdiTxtExc.getText().equals(""))
                    {objTblModExc.setChecked( false, tblValExc.getSelectedRow(), INT_TBL_DAT_SEL);}
                    else
                    {objTblModExc.setChecked(true, tblValExc.getSelectedRow(), INT_TBL_DAT_SEL);}
                    System.out.println("---ESTA EN MODO DE INSERTAR---EN EL VALOR EN EXCESO---");
                    int k = 0;
                    k = objTblModExc.getRowCountTrue();
                    System.out.println("El valor de K es: " + k);
                    I++;
                    for (int i=0; i<k; i++){
                            if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL)){  
                                double dblPndAux = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND), 3));
                                double dblAboApl = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC), 3));
                                double dblAboAux = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO), 3));
                                double SumValPen = (dblPndAux+dblAboAux);
                                System.out.println("El valor del campo ValExc.(dblExcAux) es: " + dblPndAux);
                                System.out.println("El valor del campo ValApl.(dblAboApl) es: " + dblAboApl);
                                System.out.println("El valor del campo ValAboAux.(dblAboAux) es: " + dblAboAux);
                                System.out.println("--SumValPen es: " + SumValPen);
                                if (objTooBar.getEstado()=='n'){
                                    if(dblAboApl > dblPndAux){
                                        if(!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>dblPndAux){
                                            mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el campo <BR><FONT COLOR=\"blue\">Valor Pend.</FONT> en la Tabla de CREDITOS</HTML>");
                                            objTblModExc.setValueAt("",i,INT_TBL_ABO_DOC);
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("---ESTA EN MODO DE consultar---EN EL VALOR EN EXCESO---");
                                    if (objTooBar.getEstado()=='m'){
                                        if(dblAboApl > SumValPen){
                                            if(!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>SumValPen){
                                                mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el <BR><FONT COLOR=\"blue\">Valor Pendiente Anterior.</FONT> en la Tabla de CREDITOS</HTML>");
                                                objTblModExc.setValueAt("",i,INT_TBL_ABO_DOC);
                                            }
                                        }
                                    }
                                }
                            }                            
                    }
                  calcularAboTot();
                  objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
                            }
                });  
            objTblCelEdiTxtPnd=new ZafTblCelEdiTxt(tblValPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_ABO_DOC).setCellEditor(objTblCelEdiTxtPnd);                    
            objTblCelEdiTxtPnd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit - DESPUES -- SE EDITO EL VALOR PENDIENTE");
                    if (objTblCelEdiTxtPnd.getText().equals(""))
                    {
                        objTblModPnd.setChecked( false, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                        objTblModPnd.setChecked(true, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    int l = 0;
                    l = objTblModPnd.getRowCountTrue();
                    System.out.println("El valor de l es: " + l);
                    J++;
                    for (int i=0; i<l; i++)
                    {
                            if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                            {
                                double dblPndAux = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND), 3));
                                double dblAboApl = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC), 3));
                                double dblAboAux = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO), 3));
                                double SumValPen = (dblPndAux+dblAboAux); 
                               
                                System.out.println("El valor del campo ValPnd.(dblPndAux) es: " + dblPndAux);
                                System.out.println("El valor del campo ValApl.(dblAboApl) es: " + dblAboApl);
                                System.out.println("El valor del campo ValAboAux.(dblAboAux) es: " + dblAboAux);
                                System.out.println("--SumValPen es: " + SumValPen);
                                if (objTooBar.getEstado()=='n')
                                {
                                    System.out.println("---ESTA EN MODO DE INSERTAR---EN EL VALOR PENDIENTE---");
                                    if(dblAboApl>dblPndAux)
                                    {
                                        if(!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>dblPndAux)
                                        {
                                            mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el campo <BR><FONT COLOR=\"blue\">Valor Pend.</FONT> en la Tabla de DEBITOS</HTML>");
                                            objTblModPnd.setValueAt("",i,INT_TBL_ABO_DOC);
                                        }
                                    }
                                }
                                else
                                {
                                    System.out.println("---ESTA EN MODO DE consultar---EN EL VALOR PENDIENTE---");
                                    if (objTooBar.getEstado()=='m')
                                    {
                                        if(dblAboApl>SumValPen)
                                        {
                                            if(!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>SumValPen)
                                            {
                                                mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el <BR><FONT COLOR=\"blue\">Valor Pendiente Anterior </FONT> en la Tabla de DEBITOS</HTML>");
                                                objTblModPnd.setValueAt("",i,INT_TBL_ABO_DOC);
                                            }
                                        }
                                    }
                                }

                            }
                    }  
                    calcularAboTot();
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
                            }
                });

                
            if(intCodMnu == 1673)
            {
                objTblCelRenButPrvCxP01 = new ZafTblCelRenBut();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButPrvCxP01);
                objTblCelRenButPrvCxP01=null;
                objTblCelEdiButGenCxP01=new ZafTblCelEdiButGen();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxP01);
                objTblCelEdiButGenCxP01.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE DEBITO - VALORES PENDIENTES --- CXP01 MNU 1673 --- opc:13");
                        
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValPnd.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxP01.setVisible(true);
                        }
                         if (objVenConCxP01.getSelectedButton()==objVenConCxP01.INT_BUT_ACE)
                        {
                            intFilSel=tblValPnd.getSelectedRow();
                            intFilSelVenCon=objVenConCxP01.getFilasSeleccionadas();
                            strCodCli=objVenConCxP01.getCodigoCliente();
                            strNomCli=objVenConCxP01.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                System.out.println("intFilSel 1  = "+intFilSel);
                                if (objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_LIN)!="P")
                                {
                                    
                                    
                                    objTblModPnd.insertRow(j);
                                    objTblModPnd.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModPnd.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    ////objVenConCxP01.setFilaProcesada(intFilSel);////
                                    objTblModPnd.setValueAt(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModPnd.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxP01.getValueAt(intFilSelVenCon[i], objVenConCxP01.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxP01.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                    System.out.println("JJJ = "+j);
                                    System.out.println("intFilSel = "+intFilSel);
                                }
                            }
                            tblValPnd.requestFocus();
                            objTblModPnd.removeEmptyRows();
                        }
                    }
                });  
            }
            
//  PARA MOSTRAR VENTANA DE DEBITO CXP DESDE EL MENU 276(Opc 19)
            if(intCodMnu == 276)
            {
                objTblCelRenButPrvCxP02 = new ZafTblCelRenBut();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButPrvCxP02);
                objTblCelRenButPrvCxP02=null;
               //ventana de documentos pendientes
                objTblCelEdiButGenCxP02=new ZafTblCelEdiButGen();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxP02);
                objTblCelEdiButGenCxP02.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE DEBITO - VALORES PENDIENTES --- CXP02 MNU 276 --- opc:19");
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValPnd.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxP02.setVisible(true);
                        }
                         if (objVenConCxP02.getSelectedButton()==objVenConCxP02.INT_BUT_ACE)
                        {
                            intFilSel=tblValPnd.getSelectedRow();
                            intFilSelVenCon=objVenConCxP02.getFilasSeleccionadas();
                            strCodCli=objVenConCxP02.getCodigoCliente();
                            strNomCli=objVenConCxP02.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                System.out.println("intFilSel 1  = "+intFilSel);
                                if (objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModPnd.insertRow(j);
                                    objTblModPnd.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModPnd.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    ////objVenConCxP02.setFilaProcesada(intFilSel);////
                                    objTblModPnd.setValueAt(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModPnd.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxP02.getValueAt(intFilSelVenCon[i], objVenConCxP02.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxP02.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                    System.out.println("JJJ = "+j);
                                    System.out.println("intFilSel = "+intFilSel);
                                }
                            }
                            tblValPnd.requestFocus();
                            objTblModPnd.removeEmptyRows();
                        }
                    }
                }); 
            }
            
//PARA MOSTRAR VENTANA DE DEBITO CXP DESDE EL MENU 1683 (Opc16)
            if(intCodMnu == 1683)
            {
                objTblCelRenButPrvCxP03 = new ZafTblCelRenBut();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButPrvCxP03);
                objTblCelRenButPrvCxP03=null;
               //ventana de documentos pendientes
                objTblCelEdiButGenCxP03=new ZafTblCelEdiButGen();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxP03);
                objTblCelEdiButGenCxP03.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE DEBITO - VALORES PENDIENTES --- CXP03 MNU 1683 --- opc:16");
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValPnd.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxP03.setVisible(true);
                        }
                         if (objVenConCxP03.getSelectedButton()==objVenConCxP03.INT_BUT_ACE)
                        {
                            intFilSel=tblValPnd.getSelectedRow();
                            intFilSelVenCon=objVenConCxP03.getFilasSeleccionadas();
                            strCodCli=objVenConCxP03.getCodigoCliente();
                            strNomCli=objVenConCxP03.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                System.out.println("intFilSel 1  = "+intFilSel);
                                if (objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModPnd.insertRow(j);
                                    objTblModPnd.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModPnd.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    /////objVenConCxP03.setFilaProcesada(intFilSel);
                                    objTblModPnd.setValueAt(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModPnd.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxP03.getValueAt(intFilSelVenCon[i], objVenConCxP03.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxP03.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                    System.out.println("JJJ = "+j);
                                    System.out.println("intFilSel = "+intFilSel);
                                }
                            }
                            tblValPnd.requestFocus();
                            objTblModPnd.removeEmptyRows();
                        }
                    }
                }); 
            }
                
// PARA MOSTRAR VENTANA DE DEBITO CXP DESDE EL MENU 499 (Opc13)
            if(intCodMnu == 499)
            {
                objTblCelRenButPrvCxP04 = new ZafTblCelRenBut();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButPrvCxP04);
                objTblCelRenButPrvCxP04=null;
               //ventana de documentos pendientes
                objTblCelEdiButGenCxP04=new ZafTblCelEdiButGen();
                tblValPnd.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxP04);
                objTblCelEdiButGenCxP04.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE DEBITO - VALORES PENDIENTES --- CXP04 MNU 499 --- opc:13");
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValPnd.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxP04.setVisible(true);
                        }
                         if (objVenConCxP04.getSelectedButton()==objVenConCxP04.INT_BUT_ACE)
                        {
                            intFilSel=tblValPnd.getSelectedRow();
                            intFilSelVenCon=objVenConCxP04.getFilasSeleccionadas();
                            strCodCli=objVenConCxP04.getCodigoCliente();
                            strNomCli=objVenConCxP04.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                System.out.println("intFilSel 1  = "+intFilSel);
                                if (objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModPnd.insertRow(j);
                                    objTblModPnd.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModPnd.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    ////objVenConCxP04.setFilaProcesada(intFilSel);
                                    objTblModPnd.setValueAt(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModPnd.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxP04.getValueAt(intFilSelVenCon[i], objVenConCxP04.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxP04.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                    System.out.println("JJJ = "+j);
                                    System.out.println("intFilSel = "+intFilSel);
                                }
                            }
                            tblValPnd.requestFocus();
                            objTblModPnd.removeEmptyRows();
                        }
                    }
                }); 
            }
 
            objTblPopMnuExc.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
	    {
                 public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {   
                    System.out.println("---despues afterClick---CLICK PARA ELIMINAR UN REGISTRO INSERTADO---");
                    calcularAboTot();
                    if (objTblPopMnuExc.isClickEliminarFila())
                    {
                        System.out.println("---if en funcion isClickEliminraFila()---");
                        if (VAL_TOT_DOC == 0.00)
                        {
                            System.out.println("--- inicializa diario eliminar reg---VAL_TOT_DOC == 0.00--");
                        }
                    }                    
                }
            });
            
            
           objTblPopMnuPnd.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
	    {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {   
                    System.out.println("---despues afterClick---CLICK PARA ELIMINAR UN REGISTRO INSERTADO---");
                    calcularAboTot();
                    if (objTblPopMnuPnd.isClickEliminarFila())
                    {
                        System.out.println("---if en funcion isClickEliminraFila()---");
                        
                        if (VAL_TOT_DOC == 0.00)
                        {
                            System.out.println("--- inicializa diario eliminar reg---VAL_TOT_DOC == 0.00--");
                        }
                    }                    
                }
            });

            
            if(intCodMnu == 1673) 
            {
                objTblCelRenButCliCxC01 = new ZafTblCelRenBut();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButCliCxC01);
                objTblCelRenButCliCxC01=null;
                objTblCelEdiButGenCxC01=new ZafTblCelEdiButGen();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxC01);
                objTblCelEdiButGenCxC01.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
                {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE CREDITO - VALORES EN EXCESO --- CXC01 MNU 1673 --- opc:10");
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValExc.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxC01.setVisible(true);
                        }
                        if (objVenConCxC01.getSelectedButton()==objVenConCxC01.INT_BUT_ACE)
                        {
                            intFilSel=tblValExc.getSelectedRow();
                            intFilSelVenCon=objVenConCxC01.getFilasSeleccionadas();
                            strCodCli=objVenConCxC01.getCodigoCliente();
                            strNomCli=objVenConCxC01.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                if (objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModExc.insertRow(j);
                                    objTblModExc.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModExc.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    objTblModExc.setValueAt(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModExc.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxC01.getValueAt(intFilSelVenCon[i], objVenConCxC01.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxC01.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                }
                            }
                            tblValExc.requestFocus();
                            objTblModExc.removeEmptyRows();
                        }
                    }
                });
            }

            
            if(intCodMnu == 276)
            {
                objTblCelRenButCliCxC02 = new ZafTblCelRenBut();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButCliCxC02);
                objTblCelRenButCliCxC02=null;

                //ventana de documentos pendientes
                objTblCelEdiButGenCxC02=new ZafTblCelEdiButGen();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxC02);
                objTblCelEdiButGenCxC02.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
                {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE CREDITO - VALORES EN EXCESO --- CXC02 MNU 276 --- opc:10");

                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValExc.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxC02.setVisible(true);
                        }
                        if (objVenConCxC02.getSelectedButton()==objVenConCxC02.INT_BUT_ACE)
                        {
                            intFilSel=tblValExc.getSelectedRow();
                            intFilSelVenCon=objVenConCxC02.getFilasSeleccionadas();
                            strCodCli=objVenConCxC02.getCodigoCliente();
                            strNomCli=objVenConCxC02.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                if (objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModExc.insertRow(j);
                                    objTblModExc.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModExc.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    objTblModExc.setValueAt(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModExc.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxC02.getValueAt(intFilSelVenCon[i], objVenConCxC02.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxC02.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                }
                            }
                            tblValExc.requestFocus();
                            objTblModExc.removeEmptyRows();
                        }
                    }
                }); 
            }

            
            if(intCodMnu == 1683)
            {
                objTblCelRenButCliCxC03 = new ZafTblCelRenBut();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButCliCxC03);
                objTblCelRenButCliCxC03=null;
                objTblCelEdiButGenCxC03=new ZafTblCelEdiButGen();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxC03);
                objTblCelEdiButGenCxC03.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
                {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE CREDITO - VALORES EN EXCESO --- CXC03 MNU 1683 --- opc:19");

                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValExc.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxC03.setVisible(true);
                        }
                        if (objVenConCxC03.getSelectedButton()==objVenConCxC03.INT_BUT_ACE)
                        {
                            intFilSel=tblValExc.getSelectedRow();
                            intFilSelVenCon=objVenConCxC03.getFilasSeleccionadas();
                            strCodCli=objVenConCxC03.getCodigoCliente();
                            strNomCli=objVenConCxC03.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                if (objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModExc.insertRow(j);
                                    objTblModExc.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModExc.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    objTblModExc.setValueAt(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModExc.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxC03.getValueAt(intFilSelVenCon[i], objVenConCxC03.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objVenConCxC03.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                }
                            }
                            tblValExc.requestFocus();
                            objTblModExc.removeEmptyRows();
                        }
                    }
                });
            }

            
            
            
            if(intCodMnu == 499)
            {
                objTblCelRenButCliCxC04 = new ZafTblCelRenBut();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellRenderer(objTblCelRenButCliCxC04);
                objTblCelRenButCliCxC04=null;
                objTblCelEdiButGenCxC04=new ZafTblCelEdiButGen();
                tblValExc.getColumnModel().getColumn(INT_TBL_BUT_CLI).setCellEditor(objTblCelEdiButGenCxC04);
                objTblCelEdiButGenCxC04.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
                {
                    public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                    {
                        System.out.println("actionPerformed --- CLICK EN EL BOTON DE CREDITO - VALORES EN EXCESO --- CXC04 MNU 499 --- opc:16");
                        int intFilSel, intFilSelVenCon[], i, j;
                        String strCodCli, strNomCli;
                        if (tblValExc.getSelectedColumn()==INT_TBL_BUT_CLI)
                        {
                            objVenConCxC04.setVisible(true);
                        }
                        if (objVenConCxC04.getSelectedButton()==objVenConCxC04.INT_BUT_ACE)
                        {
                            intFilSel=tblValExc.getSelectedRow();
                            intFilSelVenCon=objVenConCxC04.getFilasSeleccionadas();
                            strCodCli=objVenConCxC04.getCodigoCliente();
                            strNomCli=objVenConCxC04.getNombreCliente();
                            j=intFilSel;
                            for (i=0; i<intFilSelVenCon.length; i++)
                            {
                                if (objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_LIN)!="P")
                                {
                                    objTblModExc.insertRow(j);
                                    objTblModExc.setValueAt(strCodCli, j, INT_TBL_DAT_COD_CLI);
                                    objTblModExc.setValueAt(strNomCli, j, INT_TBL_DAT_NOM_CLI);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_COD_LOC), j, INT_TBL_DAT_COD_LOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_COD_TIP_DOC), j, INT_TBL_DAT_COD_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_DEC_TIP_DOC), j, INT_TBL_DAT_DES_COR);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_DEL_TIP_DOC), j, INT_TBL_DAT_TIP_DOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_COD_DOC), j, INT_TBL_DAT_COD_DOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_COD_REG), j, INT_TBL_DAT_COD_REG);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_NUM_DOC), j, INT_TBL_NUM_DOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_FEC_DOC), j, INT_TBL_DAT_FEC_DOC);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_DIA_CRE), j, INT_TBL_DAT_DIA_CRE);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_FEC_VEN), j, INT_TBL_DAT_FEC_VEN);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_POR_RET), j, INT_TBL_DAT_POR_RET);
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_VAL_DOC), j, INT_TBL_DAT_VAL_DOC);
                                    objTblModExc.setValueAt(""+Math.abs(Double.parseDouble(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_VAL_PEN).toString())),j,INT_TBL_DAT_VAL_PND); 
                                    objTblModExc.setValueAt(objVenConCxC04.getValueAt(intFilSelVenCon[i], objVenConCxC04.INT_TBL_DAT_VAL_PEN), j, INT_TBL_DAT_VAL_PND);
                                    objVenConCxC04.setFilaProcesada(intFilSelVenCon[i]);
                                    j++;
                                }
                            }
                            tblValExc.requestFocus();
                            objTblModExc.removeEmptyRows();

                        }
                    }
                });            
            }
            objTblModLisExc=new ZafCxC36.ZafTblModLis();                
            objTblModExc.addTableModelListener(objTblModLisExc);
            objTblModLisPnd=new ZafCxC36.ZafTblModLis();
            objTblModPnd.addTableModelListener(objTblModLisPnd);
            objAjuAutSis.mostrarTipDocPre();
        }
    catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
        blnRes=false;
    }    
    return blnRes;
    } 
    
    
    
      private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        int intCodUsr = objParSis.getCodigoUsuario();
        try
        {
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
            if(intCodUsr==1)
            {
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                System.out.println("---Query consulta usuario ADMIN: "+strSQL);
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_usr";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocUsr AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                System.out.println("---Query consulta usuario VARIOS: "+strSQL);
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
            vcoTipDoc.setConfiguracionColumna(3, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

      
    private boolean configurarVenConCreCxC01()
//  LLama al cuadro de clientes de cxc creditos mnu 1673
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxC01()--- ");
            objVenConCxC01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Créditos");
            if(objParSis.getCodigoMenu()==1673)
                objVenConCxC01.setTipoConsulta(10);            
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarVenConCreCxC02()
//  LLama al cuadro de clientes de cxc creditos co_mnu 276
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxC02()--- ");
            objVenConCxC02=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Créditos");
            if(objParSis.getCodigoMenu()==276)
                objVenConCxC02.setTipoConsulta(10);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConCreCxC03()
//   LLama al cuadro de proveedores de cxc Creditos co_mnu 1683
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxC03()--- ");
            objVenConCxC03=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Créditos");
            //cruce de documentos de diferentes clientes
            if(objParSis.getCodigoMenu()==1683)
                objVenConCxC03.setTipoConsulta(19);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConCreCxC04()
// LLama al cuadro de proveedores de cxc Creditos co_mnu 499
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxC04()--- ");
            objVenConCxC04=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Créditos");
            if(objParSis.getCodigoMenu()==499)
                objVenConCxC04.setTipoConsulta(16);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        
        return blnRes;
    }
    
    
    private boolean configurarVenConDebCxP01()
//  LLama al cuadro de clientes de cxp Débitos mnu 1673
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxP01()--- ");
            objVenConCxP01=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Débitos");
            //cruce de documentos de diferentes clientes
            if(objParSis.getCodigoMenu()==1673)
                objVenConCxP01.setTipoConsulta(13);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConDebCxP02()
//   LLama al cuadro de clientes de cxp Débitos co_mnu 276
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxP02()--- ");
            objVenConCxP02=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Débitos");
            if(objParSis.getCodigoMenu()==276)
                objVenConCxP02.setTipoConsulta(19);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean configurarVenConDebCxP03()
//   LLama al cuadro de proveedores de cxp Débitos co_mnu 1683
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxP03()--- ");
            objVenConCxP03=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Débitos");
            if(objParSis.getCodigoMenu()==1683)
                objVenConCxP03.setTipoConsulta(16);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarVenConDebCxP04()
//   LLama al cuadro de proveedores de cxp Débitos co_mnu 499
    {
        boolean blnRes=true;
        try
        {
            System.out.println("---configurarVenConCreCxP04()--- ");
            objVenConCxP04=new ZafVenConCxC01(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Débitos");
            if(objParSis.getCodigoMenu()==499)
                objVenConCxP04.setTipoConsulta(13);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
 
      
   public void setEditable(boolean editable)
    {
        if (editable==true)
        {
            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
        }
        else
        {
            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_NO_EDI);
            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_NO_EDI);            
        }
    }

    private void cargaTipoDocPredeterminado()
    {
        int n=0;
        objTipDoc.DocumentoPredeterminado();
        n=objTipDoc.getne_ultdoc();
        System.out.println("---n es : " + n);        
        txtNumAltUno.setText(""+(n+1));
       }
    
    private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtTipDoc.setText(objTipDoc.gettx_descor());
        txtNomDoc.setText(objTipDoc.gettx_deslar());
    }
    
    /*ara alterar el modelo de datos
     *asigno los formatos de las celdas de la tabla
     **/
    private class ZafDefTblCelRen extends javax.swing.table.DefaultTableCellRenderer {
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component objCom;
            javax.swing.JLabel lblAux;
            int intAliHor;
            String strFor, strVal;       //Formato, Valor
            objCom=super.getTableCellRendererComponent(table,value,isSelected,hasFocus,row,column);
            lblAux=(javax.swing.JLabel)objCom;
            strFor=objParSis.getFormatoNumero();
            strVal=(value==null)?"":value.toString();
            switch (column) {
                case INT_TBL_DAT_VAL_DOC:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_DAT_VAL_PND:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_ABO_DOC:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                default:
                    intAliHor=javax.swing.JLabel.LEFT;
                    break;
            }
            lblAux.setHorizontalAlignment(intAliHor);
            lblAux.setText(strVal);
            lblAux=null;
            return this;
        }
    }
    /* para adicionar el tooltip
     **/
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblValPnd.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_DAT_SEL:
                    strMsg="Seleccione para escoger el valor a aplicar";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Codigo del Cliente";
                    break;  
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del Cliente";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;           
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg="Código del registro";
                    break;  
                case INT_TBL_NUM_DOC:
                    strMsg="Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break; 
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Numero de dias de crédito del documento";
                    break;   
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento del documento";
                    break;  
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retenciï¿½n";
                    break;                    
                case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                 case INT_TBL_DAT_VAL_PND:
                    strMsg="Valor pendiente del documento";
                    break;  
                    
                 case INT_TBL_ABO_DOC:
                    strMsg="Abono del documento";
                    break; 
            }
            tblValPnd.getTableHeader().setToolTipText(strMsg);
            tblValExc.getTableHeader().setToolTipText(strMsg);
           }
    }
  
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtTipDoc.setText("");
            txtNomDoc.setText("");
            txtCodDoc.setText("");
            txtFecDoc.setText("");           
            txtNumAltUno.setText("");
            txaObs1.setText("");
            txaObs2.setText("");            
            txtCodTipDoc.setText("");
            txtMonDocExc.setText("");
            txtMonDocPnd.setText("");
            lblValDif.setText("");
            objAsiDia.inicializar();
            objTblModExc.removeAllRows();  
            objTblModPnd.removeAllRows();  
            dblMonDoc=0.00;
            intbanclimod=0;
            intbanmodcel=0;
            objTooBar.setEstadoRegistro("");
            
        }
        catch (Exception e)
        { 
            blnRes=false;
        }
        return blnRes;
    }

        private boolean verificarDatoAjuste()
    {
        java.util.ArrayList arlParEmp, arlParUsr;
        int codigoEmpresa;
        boolean blnRes=true;
        try
        {
            
            codigoEmpresa = objParSis.getCodigoEmpresa();
            
            System.out.println("---strMod: " + strMod);
            
            if(strMod.equals("1") || strMod.equals("3"))
            {
                System.out.println("---Modulo de CxC");
                arlParEmp=objParSis.getValoresParametroTbrParEmp(codigoEmpresa, 4);
            }
            else
            {
                System.out.println("---Modulo de CxP");
                arlParEmp=objParSis.getValoresParametroTbrParEmp(codigoEmpresa, 5);
            }
            
            System.out.println("---arlParEmp: " + arlParEmp);
            
            if (arlParEmp==null)
            {
                //No se encontrï¿½ ninguna configuraciï¿½n para la empresa. Se asume la configuraciï¿½n mï¿½s bï¿½sica.
                //Es decir, se utilizarï¿½ "Clientes por local".
                blnRes=false;
            }
            else
            {
                switch (((int)Double.parseDouble(arlParEmp.get(0).toString())))
                {
                    case 1:                        
                        blnRes=false;
                        System.out.println("---FALSE---blnRes: " + blnRes);
                        break;
                    case 2:                        
                        blnRes=true;
                        System.out.println("---VERDAD---SE AJUSTA---blnRes: " + blnRes);
                        break;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

        private boolean listaDocExc(int filaSel)
    {
       java.sql.Connection conExiFac;
       java.sql.Statement stmExiFac;
       java.sql.ResultSet rstExiFac;                
       java.sql.Connection conTmpPnd;
       java.sql.Statement stmTmpPnd;
       java.sql.ResultSet rstTmpPnd;
       java.sql.Connection conPgdo;
       java.sql.Statement stmPgdo;
       java.sql.ResultSet rstPgdo;
       int intRowTmp=filaSel;
       int intFilaSel=filaSel;//si ya fue procesada
       int intFilaSel2=filaSel;//para cargar cuando no ha sido procesada
       int intFilaSel3=filaSel;//si no existe todavia la factura y me sirve para obtener la fila y hacerle un clear--pedido de carlos
       int intFilaSelMain=filaSel;
       String strNumDoc="";
       String strNomDoc="";
       String strSQLPgdo="", strAuxPgdo, strNumFac="", strAboFac="";
       String strSQLExiFac="", strAuxExiFac, strSaldo="";
       int k=0, intNumReg=0, intTotRegPag=0, intTotRegAct=0, intTotRegAbo=0;
       double saldo=0.00;
       boolean blnRes=true;
       int intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());
       int intCodEmp, intCodLoc;
       String strCodTipDoc="";
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();   
       try
       {
            conExiFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());           
            if(conExiFac!=null)
            {
                   stmExiFac=conExiFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );             
                   strAuxExiFac="";
                   if((tblValExc.getValueAt(intFilaSel,INT_TBL_NUM_DOC))!=null){
                       strAuxExiFac+=" and cab.ne_numdoc=" + tblValExc.getValueAt(intFilaSel, INT_TBL_NUM_DOC) + "";
                   }                              
                   strSQLExiFac="";
                   strSQLExiFac+="select ne_numdoc, co_cli, st_reg, co_tipdoc, co_doc from tbm_cabmovinv as cab";
                   strSQLExiFac+=" where co_emp=" + objParSis.getCodigoEmpresa() + "" ;
                   strSQLExiFac+=strAuxExiFac;
                   strSQLExiFac+=" and cab.st_reg not in ('I','E')";       ////Eliminacion Logica////
                   System.out.println("---PASO1.- listaDocPnd para saber si hay facturas en la cabmovinv que no esten ANULADAS ni ELIMINADAS:"+strSQLExiFac);            
                   rstExiFac=stmExiFac.executeQuery(strSQLExiFac);
                   if(rstExiFac.next())
                   {
                       String strSQL="", strAux;
                       strCodTipDoc = rstExiFac.getString("co_tipdoc");
                       
                       try
                       {                      
                           conTmpPnd=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());                                   
                           if(conTmpPnd!=null)
                           {
                               stmTmpPnd=conExiFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                               strAux="";
                               intCodEmp=objParSis.getCodigoEmpresa();
                               intCodLoc=objParSis.getCodigoLocal();
                               if((tblValExc.getValueAt(intFilaSel2,INT_TBL_NUM_DOC))!=null)
                               {
                                   strAux+=" and a1.ne_numdoc=" + tblValExc.getValueAt(intFilaSel2, INT_TBL_NUM_DOC) + "";
                               }                              
              if(intCodMnu == 499)
             {              
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1")) 
                {
                      strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                       strSQL+=" AND (mo_pag + nd_abo)>0";
                 }else{            

                     strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)>0";
                 }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";                         
               System.out.println("---PASO2.-499  LISTADOCEXC() SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);
                }   
                 if(intCodMnu ==1673)
             {               
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1"))
                {
                      strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                       strSQL+=" AND (mo_pag + nd_abo)>0";
                 } else {     
                      strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)>0";
                 }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";                         
                     
                System.out.println("---PASO2.-1673(10)  LISTADOCEXC()  SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);              
                 }
                               
            if(intCodMnu ==276)
             {               
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1"))
                {
                      strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                       strSQL+=" AND (mo_pag + nd_abo)>0";
                 } else {     
                      strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)>0";
                 }
                
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";                         
                     
                System.out.println("---PASO2.-276(10)  LISTADOCEXC()  SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);              
                 }              
                               
                               
                 else
                 {
                                   
                if((intCodMnu!=1673)&&(intCodMnu!=499)&&(intCodMnu!=276))
             {    
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1"))
                {
                      strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                       strSQL+=" AND (mo_pag + nd_abo)>0";
                 } else {                    
                   strSQL+=" AND a3.ne_mod in (0,2,4)";
                    strSQL+=" AND (mo_pag + nd_abo)<0";
                 }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";                         
                     
                System.out.println("--:PASO2.-ELSE  LISTADOCEXC()  SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);              
                }
                 }
                               rstTmpPnd=stmTmpPnd.executeQuery(strSQL);
                               intFilaSel2--;
                               if(rstTmpPnd.next())
                               {
                                    rstTmpPnd.beforeFirst();
                                    while (rstTmpPnd.next())
                                    {
                                       intFilaSel2++;
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_DAT_LIN);////0
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_DAT_SEL);////1
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_BUT_CLI);////2                                           
                                       tblValExc.setValueAt(rstTmpPnd.getString("co_cli"), intFilaSel2, INT_TBL_DAT_COD_CLI);////3
                                       tblValExc.setValueAt(rstTmpPnd.getString("tx_nomcli"), intFilaSel2, INT_TBL_DAT_NOM_CLI);////4
                                       tblValExc.setValueAt(rstTmpPnd.getString("co_loc"), intFilaSel2, INT_TBL_DAT_COD_LOC);////5
                                       tblValExc.setValueAt(rstTmpPnd.getString("co_tipdoc"), intFilaSel2, INT_TBL_DAT_COD_TIP_DOC);////6
                                       tblValExc.setValueAt(rstTmpPnd.getString("tx_descor"), intFilaSel2, INT_TBL_DAT_DES_COR);////7
                                       tblValExc.setValueAt(rstTmpPnd.getString("tx_deslar"), intFilaSel2, INT_TBL_DAT_TIP_DOC);////8
                                       tblValExc.setValueAt(rstTmpPnd.getString("co_doc"), intFilaSel2, INT_TBL_DAT_COD_DOC);////9
                                       tblValExc.setValueAt(rstTmpPnd.getString("co_reg"), intFilaSel2, INT_TBL_DAT_COD_REG);////10
                                       tblValExc.setValueAt(rstTmpPnd.getString("ne_numdoc"), intFilaSel2, INT_TBL_NUM_DOC);///11
                                       tblValExc.setValueAt(objUti.formatearFecha(rstTmpPnd.getDate("fe_doc"), "dd-MM-yyyy"), intFilaSel2, INT_TBL_DAT_FEC_DOC);////12
                                       tblValExc.setValueAt(rstTmpPnd.getString("ne_diacre"), intFilaSel2, INT_TBL_DAT_DIA_CRE);////13
                                       tblValExc.setValueAt(objUti.formatearFecha(rstTmpPnd.getDate("fe_ven"), "dd-MM-yyyy"), intFilaSel2, INT_TBL_DAT_FEC_VEN);////14
                                       tblValExc.setValueAt(rstTmpPnd.getString("nd_porret"), intFilaSel2, INT_TBL_DAT_POR_RET);////15
                                       tblValExc.setValueAt("" + (rstTmpPnd.getDouble("monPag")), intFilaSel2, INT_TBL_DAT_VAL_DOC);////16
                                       tblValExc.setValueAt(""+(rstTmpPnd.getDouble("valPnd")), intFilaSel2, INT_TBL_DAT_VAL_PND);////17
                                       tblValExc.setValueAt(""+(rstTmpPnd.getDouble("valPnd")), intFilaSel2, INT_TBL_DAT_VAL_PND_INI);////18
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_ABO_DOC);////19
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_DAT_AUX_ABO);////21
                                       String strNatDoc="";
                                       STRNATDOC=rstTmpPnd.getString("tx_natDoc");
                                       tblValExc.setValueAt(""+STRNATDOC, intFilaSel2, INT_TBL_DAT_NAT_DOC);////21
                                       tblValExc.setValueAt("", intFilaSel2, INT_TBL_DAT_VAL_ABO_PAG);////22
                                       objTblModExc.insertRow();
                                       REGINSDETFAC ++;
                                       System.out.println("---REGINSDETFAC --while-- en listaDocExc() REGINSDETFAC es: " + REGINSDETFAC);
                                    }
                               }
                               else
                               {  
                                   try
                                   {
                                       conPgdo=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                       if(conPgdo!=null)
                                       {
                                           stmPgdo=conExiFac.createStatement();                                                   
                                           strAuxPgdo="";
                                           if((tblValExc.getValueAt(intFilaSel,INT_TBL_NUM_DOC))!=null)
                                           {
                                               strAuxPgdo+=" and a1.ne_numdoc=" + tblValExc.getValueAt(intFilaSel, INT_TBL_NUM_DOC) + "";
                                           }                              
                                           strSQLPgdo="";
                                           strSQLPgdo+=" SELECT a4.co_emp, a2.co_reg as CodReg, a4.ne_numdoc1, a5.tx_descor as nomTipDoc, a1.ne_numdoc as NumDoc, a1.co_doc, a2.mo_pag, round(a2.nd_abo,2) as AboFac, (a2.mo_pag+a2.nd_abo) as Saldo, a3.nd_abo as ValAboDet";
                                           strSQLPgdo+=" FROM tbm_cabmovinv AS a1";
                                           strSQLPgdo+=" INNER JOIN tbm_pagmovinv AS a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)";
                                           strSQLPgdo+=" INNER JOIN tbm_detpag AS a3 on (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_locpag and a2.co_tipdoc=a3.co_tipdocpag and a2.co_doc=a3.co_docpag and a2.co_reg=a3.co_regpag)";
                                           strSQLPgdo+=" INNER JOIN tbm_cabpag AS a4 on (a3.co_emp=a4.co_emp and a3.co_loc=a4.co_loc and a3.co_tipdoc=a4.co_tipdoc and a3.co_doc=a4.co_doc) ";
                                           strSQLPgdo+=" INNER JOIN tbm_cabtipdoc AS a5 on (a4.co_emp=a5.co_emp and a4.co_loc=a5.co_loc and a4.co_tipdoc=a5.co_tipdoc)";
                                           strSQLPgdo+=" WHERE ";
                                           strSQLPgdo+=" a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                           strSQLPgdo+=" and a1.co_tipdoc=" + strCodTipDoc + "";
                                           strSQLPgdo+=" and (abs(a2.nd_abo)>=abs(a2.mo_pag)  or a2.nd_abo>0)";
                                           strSQLPgdo+=" and a2.st_reg IN ('A','C')";
                                            strSQLPgdo+=strAuxPgdo;
                                            strSQLPgdo+=" ORDER BY a2.co_reg";
                                           System.out.println("---PASO3.- LISTADOCEXC() SI TODOS LOS DOC. TIENEN ABONOS ASOCIADOS :"+strSQLPgdo);            
                                           rstPgdo=stmPgdo.executeQuery(strSQLPgdo);
                                           if(rstPgdo.next())
                                           {
                                               //YA FUE PROCESADA
                                               strNumDoc=rstPgdo.getString("ne_numdoc1");
                                               strNomDoc=rstPgdo.getString("nomTipDoc");
                                               strNumFac=rstPgdo.getString("NumDoc");
                                               strAboFac=rstPgdo.getString("AboFac");
                                               intNumReg=rstPgdo.getInt("CodReg");
                                               intTotRegPag=retTotRegPag(strNumFac);
                                               intTotRegAct=retCanRegAct(strNumFac);
                                               intTotRegAbo=retCanRegAbo(strNumFac);
                                               saldo =Double.parseDouble(rstPgdo.getString("Saldo"));
                                               STRNUMDOC = strNumFac;
                                               if(intTotRegPag > intTotRegAct)
                                               {
                                                   if(intTotRegAct==0)
                                                   {
                                                       mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> ya ESTA CANCELADA TOTALMENTE <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                       clearNumFactExc(intFilaSel3);
                                                   }
                                                   else
                                                   {
                                                       if(intTotRegAbo > 0)
                                                       {
                                                           mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO  <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                           clearNumFactExc(intFilaSel3);
                                                       }
                                                   }
                                               }
                                               if(intTotRegAct > intTotRegPag)
                                               {
                                                   if(intTotRegPag == 0)
                                                   {
                                                       if(intTotRegAbo > 0)
                                                       {
                                                           mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO  <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                           clearNumFactExc(intFilaSel3);
                                                       }
                                                   }
                                               }
                                               if(intTotRegAct == intTotRegPag)
                                               {
                                                   if(intTotRegAbo > 0)
                                                   {
                                                       mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                       clearNumFactExc(intFilaSel3);
                                                   }
                                               }
                                           }else{
                                               
                                               mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> NO PERTENECE A ESTE MODULO <BR> Por Favor Verifique el Numero de Factura ingresada.</HTML>");
                                               clearNumFactExc(intFilaSel3);
                                           }
                                           rstPgdo.close();
                                           stmPgdo.close();
                                           conPgdo.close();
                                           rstPgdo=null;
                                           stmPgdo=null;
                                           conPgdo=null;
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
                               }
                               rstTmpPnd.close();
                               stmTmpPnd.close();
                               conTmpPnd.close();
                               rstTmpPnd=null;
                               stmTmpPnd=null;
                               conTmpPnd=null;
                               objTblModExc.removeEmptyRows();
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
                   }
                   
                   else
                   {                   
                       mostrarMsgInf("<HTML>El numero de Factura no ha sido ingresada<BR> o estï¿½ anulada <BR> Por Favor Verifique el dato ingresado.</HTML>");  
                       clearNumFactExc(intFilaSel3);
                   }                                         
                        rstExiFac.close();
                        stmExiFac.close();
                        conExiFac.close();
                        rstExiFac=null;
                        stmExiFac=null;
                        conExiFac=null;
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

        private boolean listaDocPnd(int filaSel)
    { 
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();     
       java.sql.Connection conExiFac;
       java.sql.Statement stmExiFac;
       java.sql.ResultSet rstExiFac;                
       java.sql.Connection conTmpPnd;
       java.sql.Statement stmTmpPnd;
       java.sql.ResultSet rstTmpPnd;
       java.sql.Connection conPgdo;
       java.sql.Statement stmPgdo;
       java.sql.ResultSet rstPgdo;
       int intRowTmp=filaSel;
       int intFilaSel=filaSel;//si ya fue procesada
       int intFilaSel2=filaSel;//para cargar cuando no ha sido procesada
       int intFilaSel3=filaSel;//si no existe todavia la factura y me sirve para obtener la fila y hacerle un clear--pedido de carlos
       int intFilaSelMain=filaSel;
       String strNumDoc="";
       String strNomDoc="";  
       String strSQLPgdo="", strAuxPgdo, strNumFac="", strAboFac="";
       String strSQLExiFac="", strAuxExiFac, strSaldo="";
       int k=0, intNumReg=0, intTotRegPag=0, intTotRegAct=0, intTotRegAbo=0;
       double saldo=0.00;
       boolean blnRes=true;
       int intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());
       int intCodEmp, intCodLoc;
       String strCodTipDoc="";
       try
       {
            conExiFac=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());           
            if(conExiFac!=null)
            {
                   stmExiFac=conExiFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );             
                   strAuxExiFac="";
                   if((tblValPnd.getValueAt(intFilaSel,INT_TBL_NUM_DOC))!=null){
                       strAuxExiFac+=" and cab.ne_numdoc=" + tblValPnd.getValueAt(intFilaSel, INT_TBL_NUM_DOC) + "";
                  
                   
                   }                              
                   strSQLExiFac="";
                   strSQLExiFac+="select ne_numdoc, co_cli, st_reg, co_tipdoc, co_doc from tbm_cabmovinv as cab";
                   strSQLExiFac+=" where co_emp=" + objParSis.getCodigoEmpresa() + "" ;
                   strSQLExiFac+=strAuxExiFac;
                   strSQLExiFac+=" and cab.st_reg not in ('I','E')";       ////Eliminacion Logica////
                   System.out.println("---PASO1.- listaDocPnd para saber si hay facturas en la cabmovinv que no esten ANULADAS ni ELIMINADAS:"+strSQLExiFac);            
                   rstExiFac=stmExiFac.executeQuery(strSQLExiFac);
                   if(rstExiFac.next())
                   {
                       String strSQL="", strAux;
                       strCodTipDoc = rstExiFac.getString("co_tipdoc");
                       try
                       {                      
                           conTmpPnd=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());                                   
                           if(conTmpPnd!=null)
                           {
                               stmTmpPnd=conExiFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                               strAux="";
                               intCodEmp=objParSis.getCodigoEmpresa();
                               intCodLoc=objParSis.getCodigoLocal();
                               if((tblValPnd.getValueAt(intFilaSel2,INT_TBL_NUM_DOC))!=null)
                               {
                                   strAux+=" and a1.ne_numdoc=" + tblValPnd.getValueAt(intFilaSel2, INT_TBL_NUM_DOC) + "";
                               }   
                               
            if(intCodMnu == 499)
            {    
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                {
                     strSQL+=" AND a3.ne_mod in (0,1,3,5) ";
                     strSQL+=" AND (mo_pag + nd_abo)<0";
                }else
                {         
                    
                      strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)<0";
               }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";        
                System.out.println("---PASO2.-499 LISTADOCPND SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);        
              }
    
                               
            if(intCodMnu == 276)
            {    
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_NUM_DOC);
                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                {
                     strSQL+=" AND a3.ne_mod in (2,4)";
                     strSQL+=" AND (a2.mo_pag+a2.nd_abo)<0";
                }else 
                {                    
                     strSQL+=" AND a3.ne_mod IN (2, 4) AND (a2.mo_pag+a2.nd_abo)<0";
               }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";        
                System.out.println("---PASO2.-276 LISTADOCPND SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);         
                     }   

              if((intCodMnu==1673))
            {
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_NUM_DOC);
                
                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                {
                     strSQL+=" AND a3.ne_mod in (0,1,3,5) ";
                     strSQL+=" AND (mo_pag + nd_abo)<0";
                }else{ 
                    
                    strSQL+=" AND a3.ne_mod IN (1, 3) AND (a2.mo_pag+a2.nd_abo)<0";
               }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";        
             System.out.println("---PASO2.-LISTADOCPND 1673(13)  SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);
            }           
                               
               if((intCodMnu != 276) && (intCodMnu != 499) &&(intCodMnu!=1673))
            {
                strSQL="";
                strSQL+="(";
                strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot";
                strSQL+=", a1.fe_doc, a2.ne_diaCre, a2.fe_ven, (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" WHERE a1.co_emp = " + intCodEmp;
                strSQL+=" AND a1.ne_numdoc = " + objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_NUM_DOC);
                
                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                {
                     strSQL+=" AND a3.ne_mod in (0,1,3,5) ";
                     strSQL+=" AND (mo_pag + nd_abo)<0";
                }else{                    
                    strSQL+=" AND a3.ne_mod in (0,2,4)";
                    strSQL+=" AND (mo_pag + nd_abo)>0";
               }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R','F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=")";        
             System.out.println("---PASO2.-LISTADOCPND (ELSE)  SI HAY POR LO MENOS UN REGISTRO NO SE HA PAGADO (ESTA PENDIENTE): " + strSQL);
            }
                                rstTmpPnd=stmTmpPnd.executeQuery(strSQL);
                                intFilaSel2--;
                               if(rstTmpPnd.next())
                               {
                                    rstTmpPnd.beforeFirst();

                                    while (rstTmpPnd.next())
                                    {  
                                       intFilaSel2++;
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_DAT_LIN);////0
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_DAT_SEL);////1
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_BUT_CLI);////2                                           
                                       tblValPnd.setValueAt(rstTmpPnd.getString("co_cli"), intFilaSel2, INT_TBL_DAT_COD_CLI);////3
                                       tblValPnd.setValueAt(rstTmpPnd.getString("tx_nomcli"), intFilaSel2, INT_TBL_DAT_NOM_CLI);////4
                                       tblValPnd.setValueAt(rstTmpPnd.getString("co_loc"), intFilaSel2, INT_TBL_DAT_COD_LOC);////5
                                       tblValPnd.setValueAt(rstTmpPnd.getString("co_tipdoc"), intFilaSel2, INT_TBL_DAT_COD_TIP_DOC);////6
                                       tblValPnd.setValueAt(rstTmpPnd.getString("tx_descor"), intFilaSel2, INT_TBL_DAT_DES_COR);////7
                                       tblValPnd.setValueAt(rstTmpPnd.getString("tx_deslar"), intFilaSel2, INT_TBL_DAT_TIP_DOC);////8
                                       tblValPnd.setValueAt(rstTmpPnd.getString("co_doc"), intFilaSel2, INT_TBL_DAT_COD_DOC);////9
                                       tblValPnd.setValueAt(rstTmpPnd.getString("co_reg"), intFilaSel2, INT_TBL_DAT_COD_REG);////10
                                       tblValPnd.setValueAt(rstTmpPnd.getString("ne_numdoc"), intFilaSel2, INT_TBL_NUM_DOC);///11
                                       tblValPnd.setValueAt(objUti.formatearFecha(rstTmpPnd.getDate("fe_doc"), "dd-MM-yyyy"), intFilaSel2, INT_TBL_DAT_FEC_DOC);////12
                                       tblValPnd.setValueAt(rstTmpPnd.getString("ne_diacre"), intFilaSel2, INT_TBL_DAT_DIA_CRE);////13
                                       tblValPnd.setValueAt(objUti.formatearFecha(rstTmpPnd.getDate("fe_ven"), "dd-MM-yyyy"), intFilaSel2, INT_TBL_DAT_FEC_VEN);////14
                                       tblValPnd.setValueAt(rstTmpPnd.getString("nd_porret"), intFilaSel2, INT_TBL_DAT_POR_RET);////15
                                       tblValPnd.setValueAt("" + (rstTmpPnd.getDouble("monPag")), intFilaSel2, INT_TBL_DAT_VAL_DOC);////16
                                       tblValPnd.setValueAt(""+(rstTmpPnd.getDouble("valPnd")), intFilaSel2, INT_TBL_DAT_VAL_PND);////17
                                       tblValPnd.setValueAt(""+(rstTmpPnd.getDouble("valPnd")), intFilaSel2, INT_TBL_DAT_VAL_PND_INI);////18
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_ABO_DOC);////19
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_DAT_AUX_ABO);////20
                                       String strNatDoc="";
                                       STRNATDOC=rstTmpPnd.getString("tx_natDoc");
                                       tblValPnd.setValueAt(""+STRNATDOC, intFilaSel2, INT_TBL_DAT_NAT_DOC);////21
                                       tblValPnd.setValueAt("", intFilaSel2, INT_TBL_DAT_VAL_ABO_PAG);////22
                                       objTblModPnd.insertRow();
                                       REGINSDETFAC ++;
                                       System.out.println("---REGINSDETFAC --while-- en listaDocPnd() REGINSDETFAC es: " + REGINSDETFAC);
                                    }
                               }
                               else
                               {    
                                   try
                                   {
                                       conPgdo=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                                       if(conPgdo!=null)
                                       {
                                           stmPgdo=conExiFac.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );                                                   
                                           strAuxPgdo="";
                                           if((tblValPnd.getValueAt(intFilaSel,INT_TBL_NUM_DOC))!=null)
                                           {
                                               strAuxPgdo+=" and a1.ne_numdoc=" + tblValPnd.getValueAt(intFilaSel, INT_TBL_NUM_DOC) + "";
                                           }                              
                                           strSQLPgdo="";
                                           strSQLPgdo+=" SELECT a4.co_emp, a2.co_reg as CodReg, a4.ne_numdoc1, a5.tx_descor as nomTipDoc, a1.ne_numdoc as NumDoc, a1.co_doc, a2.mo_pag, round(a2.nd_abo,2) as AboFac, (a2.mo_pag+a2.nd_abo) as Saldo, a3.nd_abo as ValAboDet";
                                           strSQLPgdo+=" FROM tbm_cabmovinv AS a1";
                                           strSQLPgdo+=" INNER JOIN tbm_pagmovinv AS a2 on (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and a1.co_doc=a2.co_doc)";
                                           strSQLPgdo+=" INNER JOIN tbm_detpag AS a3 on (a2.co_emp=a3.co_emp and a2.co_loc=a3.co_locpag and a2.co_tipdoc=a3.co_tipdocpag and a2.co_doc=a3.co_docpag and a2.co_reg=a3.co_regpag)";
                                           strSQLPgdo+=" INNER JOIN tbm_cabpag AS a4 on (a3.co_emp=a4.co_emp and a3.co_loc=a4.co_loc and a3.co_tipdoc=a4.co_tipdoc and a3.co_doc=a4.co_doc) ";
                                           strSQLPgdo+=" INNER JOIN tbm_cabtipdoc AS a5 on (a4.co_emp=a5.co_emp and a4.co_loc=a5.co_loc and a4.co_tipdoc=a5.co_tipdoc)";
                                           strSQLPgdo+=" WHERE ";
                                           strSQLPgdo+=" a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                           strSQLPgdo+=" and a1.co_tipdoc=" + strCodTipDoc + "";
                                           strSQLPgdo+=" and (abs(a2.nd_abo)>=abs(a2.mo_pag)  or a2.nd_abo>0)";
                                         
                                             if(strMod.equals("3") || strMod.equals("1"))
                                                strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                                            else 
                                                strSQL+=" AND a3.ne_mod in (0,2,4)";
                
                                           strSQLPgdo+=" and a2.st_reg IN ('A','C')";
                                           strSQLPgdo+=strAuxPgdo;
                                           strSQLPgdo+=" ORDER BY a2.co_reg";
                                           System.out.println("---PASO3.- LISTADOCPND() SI TODOS LOS DOC. TIENEN ABONOS ASOCIADOS :"+strSQLPgdo);            
                                           rstPgdo=stmPgdo.executeQuery(strSQLPgdo);
                                           if(rstPgdo.next())
                                           {
                                               strNumDoc=rstPgdo.getString("ne_numdoc1");
                                               strNomDoc=rstPgdo.getString("nomTipDoc");
                                               strNumFac=rstPgdo.getString("NumDoc");
                                               strAboFac=rstPgdo.getString("AboFac");
                                               intNumReg=rstPgdo.getInt("CodReg");
                                               intTotRegPag=retTotRegPag(strNumFac);
                                               intTotRegAct=retCanRegAct(strNumFac);
                                               intTotRegAbo=retCanRegAbo(strNumFac);
                                               saldo =Double.parseDouble(rstPgdo.getString("Saldo"));
                                               STRNUMDOC = strNumFac;

                                               if(intTotRegPag > intTotRegAct)
                                               {
                                                   if(intTotRegAct==0)
                                                   {
                                                       mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> ya ESTA CANCELADA TOTALMENTE <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");          
                                                       clearNumFactPnd(intFilaSel3);
                                                   }
                                                   else
                                                   {
                                                             if(intTotRegAbo > 0)
                                                       {
                                                           mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO  <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                           clearNumFactPnd(intFilaSel3);
                                                       }
                                                   }
                                               }

                                               if(intTotRegAct > intTotRegPag)
                                               {
                                                   if(intTotRegPag == 0)
                                                   {
                                                       if(intTotRegAbo > 0)
                                                       {
                                                           mostrarMsgInf("<HTML>El nï¿½mero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO  <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                           clearNumFactPnd(intFilaSel3);
                                                       }
                                                   }
                                               }
                                               if(intTotRegAct == intTotRegPag)
                                               {
                                                   if(intTotRegAbo > 0)
                                                   {
                                                       mostrarMsgInf("<HTML>El nï¿½mero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> POSEE ABONO ASOCIADO <BR> en el nï¿½mero de documento <FONT COLOR=\"blue\">" + strNumDoc + "</FONT> del Tipo de Documento <FONT COLOR=\"blue\">" + strNomDoc + "</FONT> . <BR> Por Favor Verifique el Nï¿½mero de Factura ingresada.</HTML>");
                                                       clearNumFactPnd(intFilaSel3);
                                                   }
                                               }
                                           }
                                           else
                                           {
                                               mostrarMsgInf("<HTML>El numero de Factura ingresada <FONT COLOR=\"blue\">" + strNumFac + " </FONT> NO PERTENECE A ESTE MODULO <BR> Por Favor Verifique el Numero de Factura ingresada.</HTML>");
                                               clearNumFactPnd(intFilaSel3);
                                           }
                                           rstPgdo.close();
                                           stmPgdo.close();
                                           conPgdo.close();
                                           rstPgdo=null;
                                           stmPgdo=null;
                                           conPgdo=null;
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
                               }
                               rstTmpPnd.close();
                               stmTmpPnd.close();
                               conTmpPnd.close();
                               rstTmpPnd=null;
                               stmTmpPnd=null;
                               conTmpPnd=null;
                               objTblModPnd.removeEmptyRows();
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
                   }
                   else
                   {                   
                       mostrarMsgInf("<HTML>El numero de Factura no ha sido ingresada<BR> o estï¿½ anulada <BR> Por Favor Verifique el dato ingresado.</HTML>");  
                       clearNumFactPnd(intFilaSel3);
                   }                                         
                        rstExiFac.close();
                        stmExiFac.close();
                        conExiFac.close();
                        rstExiFac=null;
                        stmExiFac=null;
                        conExiFac=null;
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
     
    public int  retTotRegPag(String NumDoc)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        int valCanRegCan=0, valCanRegAct=0;
        int intCodEmp=0, intCodLoc=0;
        
        try{            

            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodLoc = objParSis.getCodigoLocal();
            
            if(conTipDoc!=null){
                
               stmTipDoc=conTipDoc.createStatement();
               que="";
               que+=" SELECT count(*)";
               que+=" FROM tbm_cabmovinv AS a1 ";
               que+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc) ";
               que+=" WHERE a1.co_emp = " + intCodEmp;
               que+=" AND a1.co_loc = " + intCodLoc;
               que+=" AND a1.ne_numdoc = " + NumDoc;
               que+=" AND a1.st_reg <> 'E' AND a2.st_reg IN ('A','C')  AND (a2.mo_pag+a2.nd_abo) = 0.00";
               valCanRegCan = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), que);
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
        return valCanRegCan;
        
    }
    
    public int  retCanRegAct(String NumDoc)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        int valCanRegCan=0, valCanRegAct=0;
        int intCodEmp=0, intCodLoc=0;
        try{            
            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodLoc = objParSis.getCodigoLocal();
            if(conTipDoc!=null){
             stmTipDoc=conTipDoc.createStatement();
               que="";
               que+=" SELECT count(*)";
               que+=" FROM tbm_cabmovinv AS a1 ";
               que+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc) ";
               que+=" WHERE a1.co_emp = " + intCodEmp;
               que+=" AND a1.co_loc = " + intCodLoc;
               que+=" AND a1.ne_numdoc = " + NumDoc;
               que+=" AND a1.st_reg <> 'E' AND a2.st_reg IN ('A','C')  AND (a2.mo_pag+a2.nd_abo) <> 0.00";
               System.out.println("---Query para cantidad de reg Activos y con Saldos retCanRegAct() --(CAN_REG_ACT)--: " + que );
               valCanRegAct = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), que);
               System.out.println("---NumTotRegAct es (CAN_REG_ACT): " + valCanRegAct);
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
        return valCanRegAct;
   }
    
    
    public int  retCanRegAbo(String NumDoc)
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";
        int valCanRegCan=0, valCanRegAbo=0;
        int intCodEmp=0, intCodLoc=0;
        try{            
           conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            intCodEmp = objParSis.getCodigoEmpresa();
            intCodLoc = objParSis.getCodigoLocal();
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
               que="";
               que+=" SELECT count(*)";
               que+=" FROM tbm_cabmovinv AS a1 ";
               que+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_doc=a2.co_doc) ";
               que+=" WHERE a1.co_emp = " + intCodEmp;
               que+=" AND a1.co_loc = " + intCodLoc;
               que+=" AND a1.ne_numdoc = " + NumDoc;
               que+=" AND a1.st_reg <> 'E' AND a2.st_reg IN ('A','C')  AND (a2.nd_abo) <> 0.00";
                valCanRegAbo = objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), que);
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
        return valCanRegAbo;        
    }   
    
    
       private boolean cargarDetRegExc()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
         int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
        
        try
        {
            if (!txtTipDoc.getText().equals(""))
           {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                     if(intCodMnu == 499)
                   {
                      if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                        { 
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                         }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegExc()--499--CREDITOS--(MODIFICAR): " + strSQL);  
                    } else{  
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                         if(strMod.equals("3") || strMod.equals("1")) 
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        } else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                        }  
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegExceso()--499--CREDITOS--(CONSULTAR): " + strSQL);
                    }
                  }
                    
                                 
                      if(intCodMnu == 1673)
                      {
                         if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                          {
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        } else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) > 0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExc()--1673--CREDITOS--(MODIFICAR): " + strSQL);
                    }else{
                             
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        } else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) >0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExc--1673--CREDITOS--(CONSULTAR): " + strSQL);
                    }  
                     }
                    
                   if(intCodMnu ==276)
                   {
                  if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) > 0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExc()--276--CREDITOS--(MODIFICAR): " + strSQL);
                    }else{
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) >0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExc--276--CREDITOS--(CONSULTAR): " + strSQL);
                    }  
                     }
                     else
                     {
                        
                         if((intCodMnu !=499)&&(intCodMnu !=1673)&& (intCodMnu !=276))
                   {
                        if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) > 0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExceso-ELSE-CREDITOS--(MODIFICAR): " + strSQL);
                    }else{
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) >0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegExceso-ELSE-CREDITOS--(CONSULTAR): " + strSQL);
                    }  
                     }
                     }
                    rst=stm.executeQuery(strSQL);
                    vecDatExc.clear();
                    for(int i=0;rst.next();i++)
                    {
                        vecRegExc=new Vector();
                        vecRegExc.add(INT_TBL_DAT_LIN, "");///0
                        vecRegExc.add(INT_TBL_DAT_SEL, "");///1 
                        vecRegExc.add(INT_TBL_BUT_CLI, "");///2
                        vecRegExc.add(INT_TBL_DAT_COD_CLI,rst.getString ("co_cli"));///3
                        vecRegExc.add(INT_TBL_DAT_NOM_CLI,rst.getString ("tx_nomcli"));///4
                        vecRegExc.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));///5
                        vecRegExc.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipdoc"));///6            
                        vecRegExc.add(INT_TBL_DAT_DES_COR, rst.getString("tx_descor"));///7
                        vecRegExc.add(INT_TBL_DAT_TIP_DOC, rst.getString("tx_deslar"));///8
                        vecRegExc.add(INT_TBL_DAT_COD_DOC, rst.getString("codDoc"));///9
                        vecRegExc.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));///10
                        vecRegExc.add(INT_TBL_NUM_DOC, rst.getString("ne_numdoc"));///11      
                        vecRegExc.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///12
                        vecRegExc.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));///13
                        vecRegExc.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///14
                        vecRegExc.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));///15
                        double dblValDoc=Double.parseDouble(""+rst.getString("monDoc"));
                        vecRegExc.add(INT_TBL_DAT_VAL_DOC, ""+dblValDoc);///16
                        vecRegExc.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));///17
                        vecRegExc.add(INT_TBL_DAT_VAL_PND_INI, ""+VAL_DOC_EXC);///18
                        double dblAuxAbo=rst.getString("aboDoc")==null?0:rst.getDouble("aboDoc");
                        vecRegExc.add(INT_TBL_ABO_DOC, ""+ dblAuxAbo);///19
                        vecRegExc.add(INT_TBL_DAT_AUX_ABO, ""+ dblAuxAbo);///20
                        vecRegExc.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natdoc"));///21
                        vecRegExc.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("nd_abo"));///22
                        if (dblAuxAbo!=0)
                          vecRegExc.setElementAt(new Boolean(true),INT_TBL_DAT_SEL);
                          vecDatExc.add(vecRegExc);                                                            
                    }     
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    objTblModExc.setData(vecDatExc);
                    tblValExc.setModel(objTblModExc);
                    intTotRegSelI = 0;
                    intTotRegSelI = tblValExc.getRowCount();
                    System.out.println("---el numero de reg sel intTotRegSelI: " + intTotRegSelI);
                     if(intbanclimod > 0)
                        I = intTotRegSelI;
                        vecDatExc.clear();
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
 
  private boolean cargarDetRegPnd()
    {
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
       int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            if (!txtTipDoc.getText().equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                      if(intCodMnu == 499)
            {
                  if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) > 0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegPnd--499--DEBITOS--(MODIFICAR): " + strSQL);
                    }
                    else
                    {
                        strSQL="";
                        strSQL+=" SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, ABS(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))    
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 "; 
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND (a4.nd_abo) >0 ";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                        System.out.println("---> en cargarDetRegPnd--499--DEBITOS--(CONSULTAR): " + strSQL);
                    }  
                     }
                     if(intCodMnu == 1673)
                   {
                      if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + ""; 
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))
                        { 
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        } else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                         }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--1673--Debitos--(MODIFICAR): " + strSQL);  
                    }
                    else
                    {  
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                         if(strMod.equals("3") || strMod.equals("1")) 
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        }
                        else
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                        }  
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--1673--DEBITOS--(CONSULTAR): " + strSQL);
                    }
                     }
 
              
                               if(intCodMnu ==276)
                   {
                      if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + ""; 
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))
                        { 
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        } else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                         }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--276--DEBITOS--(MODIFICAR): " + strSQL);  
                    }
                    else
                    {  
                          
                        //Muestra reg 
                        //Para los demas modos se muestra: solo los documentos pagados.
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                         if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        }else{
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                        }  
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--276--DEBITOS--(CONSULTAR): " + strSQL);
                    }
                     }            
                      else
                      {             
                      if((intCodMnu != 1673)&&(intCodMnu != 499)&&(intCodMnu !=276))
                     {
                         if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                      {
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        if(strMod.equals("3") || strMod.equals("1"))
                        { 
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        }
                        else 
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                         }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--Else--DEBITOS--(MODIFICAR): " + strSQL);  
                    }
                         
                    else
                    {  
                        strSQL="";
                        strSQL+="SELECT a1.co_cli, a1.tx_nomcli, a1.co_loc, a4.co_locpag as locpag, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc, a1.fe_doc, a2.ne_diacre, a2.fe_ven,";
                        strSQL+=" a2.nd_porret, abs(a2.mo_pag) as monDoc, a2.co_reg, abs(a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                         if(strMod.equals("3") || strMod.equals("1"))
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo >=0 ";
                        }else {
                            strSQL+=" AND a3.ne_mod in (0,1,2,3,4)";
                            strSQL+=" AND a4.nd_abo < 0 ";
                        }  
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                        System.out.println("---> en cargarDetRegPnd()--Else--DEBITOS--(CONSULTAR): " + strSQL);
                    }} }
                    rst=stm.executeQuery(strSQL);
                    vecDatPnd.clear();

                    for(int i=0;rst.next();i++)
                    {
                        vecRegPnd=new Vector();
                        vecRegPnd.add(INT_TBL_DAT_LIN, "");
                        vecRegPnd.add(INT_TBL_DAT_SEL, "");
                        vecRegPnd.add(INT_TBL_BUT_CLI, "...");
                        vecRegPnd.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                        vecRegPnd.add(INT_TBL_DAT_NOM_CLI, rst.getString("tx_nomcli"));
                        vecRegPnd.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                        vecRegPnd.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipdoc"));
                        vecRegPnd.add(INT_TBL_DAT_DES_COR, rst.getString("tx_descor"));
                        vecRegPnd.add(INT_TBL_DAT_TIP_DOC, rst.getString("tx_deslar"));
                        vecRegPnd.add(INT_TBL_DAT_COD_DOC, rst.getString("codDoc"));
                        vecRegPnd.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));
                        vecRegPnd.add( INT_TBL_NUM_DOC, rst.getString("ne_numdoc"));
                        vecRegPnd.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///12
                        vecRegPnd.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));
                        vecRegPnd.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///14
                        vecRegPnd.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));
                        double dblValDoc=Double.parseDouble(""+rst.getString("monDoc"));
                        vecRegPnd.add(INT_TBL_DAT_VAL_DOC, ""+dblValDoc);
                        vecRegPnd.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));
                        vecRegPnd.add(INT_TBL_DAT_VAL_PND_INI, ""+VAL_DOC_PEN);
                        double dblAuxAbo=rst.getString("aboDoc")==null?0:rst.getDouble("aboDoc");
                        vecRegPnd.add(INT_TBL_ABO_DOC, ""+ dblAuxAbo);
                        vecRegPnd.add(INT_TBL_DAT_AUX_ABO, ""+ dblAuxAbo);
                        vecRegPnd.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natdoc"));
                        vecRegPnd.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("nd_abo"));
                        if (dblAuxAbo!=0)
                        vecRegPnd.setElementAt(new Boolean(true),INT_TBL_DAT_SEL);
                        vecDatPnd.add(vecRegPnd);                                                            
                    }
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                    //Asignar vectores al modelo.
                    objTblModPnd.setData(vecDatPnd);
                    tblValPnd.setModel(objTblModPnd);
                    intTotRegSelJ = 0;
                    intTotRegSelJ = tblValPnd.getRowCount();
                    System.out.println("---el numero de reg sel intTotRegSelJ: " + intTotRegSelJ);
                    if(intbanclimod > 0)
                        J = intTotRegSelJ;
                    vecDatPnd.clear();
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
 
     private void mostrarMsgInf(String strMsg) {
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
    
    private void consultarCamUsr()
    {
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
        
        switch (mostrarMsgCon(strAux))
        {
            case 0: 
                switch (objTooBar.getEstado())
                {
                    case 'm':
                        System.out.println("Modo Modificar");
                        break;
                    case 'e':
                        System.out.println("Modo Eliminar");
                        break;
                    case 'n':
                        System.out.println("Modo Ninguno");
                        break;
                }
                break;
            case 1:
                break;
            case 2:
                break;
        }
                    
    }   
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0:
                switch (objTooBar.getEstado())
                {
                    case 'n':
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm':
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1:
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: 
                blnRes=false;
                break;
        }
        return blnRes;
    }  
    
    
    private int MsgConf(String strMsg){
        String strTit="Zafiro";
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
    }  
    
    /**
     *Esta función valida si los campos obligatorios han sido completados correctamente 
     *@return True si los campos obligatorios se llenaron correctamente.
     */
    private boolean isCamValMod()
    {
         if (!txtNumAltUno.getText().equals(""))
         { 
                String strTit, strMsg;
                int intNumAltDoc=0;
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc1";
                strSQL+=" FROM tbm_cabpag AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc1='" + txtNumAltUno.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
               if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    intNumAltDoc = Integer.parseInt(txtNumAltUno.getText());
                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumAltUno.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumAltUno.setText("" +((intUltNumDocAlt)+1));
                        }else{
                            txtNumAltUno.setText("");
                            txtNumAltUno.requestFocus();
                        }
                    } 
                    catch (Exception e)
                    {
                        dispose();
                    }
                    return false;
                }
         }
         return true;
    }

    private boolean isCamVal()
    {
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        if (txtTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de tipo de documento y vuelva a intentarlo.</HTML>");
            txtTipDoc.requestFocus();
            return false;
        }
        if (txtNomDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomDoc.requestFocus();
            return false;
        }
         if (!txtFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            txtFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0:
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                            
                            txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";

                            switch (mostrarMsgCon(strMsj)){
                                case 0: 
                                    System.out.println("POR YES");
                                    return true;
                                case 1:
                                    System.out.println("POR NO");
                                    return false;
                                case 2: 
                                    System.out.println("POR CANCEL");
                                    return false;
                            }
                            datFecAux=null;
                        }
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
                            strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                            switch (mostrarMsgCon(strMsj)){
                                case 0:
                                    return true;
                                case 1:
                                    return false;
                                case 2:
                                    return false;
                            }
                            
                        }
                    }
                    break;
                case 1:
                    if(objTooBar.getEstado()=='n'){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    
                    break;
                case 2:
                    if(objTooBar.getEstado()=='n'){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3:
                    if(objTooBar.getEstado()=='n'){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        strFecDocTmp="";strFecAuxTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                        strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            txtFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }

        if(txtNumAltUno.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de Alterno 1</FONT> es obligatorio.<BR>Escriba un número de retención y vuelva a intentarlo.</HTML>");            
            txtNumAltUno.requestFocus();
            return false;
        }
        if(txtMonDocExc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Monto de Credito</FONT> es obligatorio.<BR> Favor verifique y vuelva a intentarlo.</HTML>");
            return false;
        } 
        if(txtMonDocPnd.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Monto de Debito</FONT> es obligatorio.<BR> Favor verifique y vuelva a intentarlo.</HTML>");
            return false;
        }
           if (!objAsiDia.isDiarioCuadrado())
        {
            mostrarMsgInf("<HTML>El asiento de diario está <FONT COLOR=\"blue\">descuadrado</FONT>.<BR>Cuadre el asiento de diario y vuelva a intentarlo.</HTML>");
            return false;
        }
         if (!objAsiDia.isDocumentoCuadrado(txtMonDocExc.getText()))
        {
            mostrarMsgInf("<HTML>El valor del documento no coincide con el valor del asiento de diario.<BR>Cuadre el valor del documento con el valor del asiento de diario y vuelva a intentarlo.</HTML>");
            txtMonDocExc.selectAll();
            txtMonDocExc.requestFocus();
            return false;
        }   
//        if (objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy").compareTo(objUti.parseDate("2006/12/31", "yyyy/MM/dd"))<=0)
//        {
//            System.out.println("No se puede MODIFICAR un documento con fecha del año anterior de la base...");
//            mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo cerrado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
//            return false;
//        }
//        
//        if (objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy").compareTo(objUti.parseDate("2009/12/31", "yyyy/MM/dd"))>0)
//        {
//            System.out.println("..No se puede MODIFICAR un documento con fecha mayor que el año en la base...");
//            mostrarMsgInf("<HTML>La fecha del documento es incorrecta. <BR>Está tratando de MODIFICAR un documento en un periodo NO creado. <BR>Corrija la fecha del documento y vuelva a intentarlo.</HTML>");
//            return false;
//            
//        }

       if (!txtNumAltUno.getText().equals(""))
         {  
                String strTit, strMsg;
                int intNumAltDoc=0;
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc1";
                strSQL+=" FROM tbm_cabpag AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc1='" + txtNumAltUno.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                
                if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    intNumAltDoc = Integer.parseInt(txtNumAltUno.getText());     
                    intUltNumDocAlt = intNumAltDoc;
                    
                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumAltUno.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumAltUno.setText("" +((intUltNumDocAlt)+1));
                        } else{
                            txtNumAltUno.setText("");
                            txtNumAltUno.requestFocus();
                        }
                    }
                    catch (Exception e)
                    {
                        dispose();
                    }
                    return false;
                }
        }
        double intMonExc=0.00;
        double intMonPnd=0.00;
        intMonExc=Double.parseDouble(txtMonDocExc.getText());        
        intMonPnd=Double.parseDouble(txtMonDocPnd.getText());
        System.out.println("---intMonExc--- " + intMonExc);
        System.out.println("---intMonPnd--- " + intMonPnd);
        if(intMonExc!=intMonPnd){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Los valores de CREDITO con los valores de DEBITO <FONT COLOR=\"blue\">NO SON IGUALES..</FONT>.<BR><CENTER>Favor Verifique e Iguale los valores...</CENTER></BR></HTML>");            
            return false;            
        }
        return true;
    }

    private int canTipoModificacion(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModTipDocUsr=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    System.out.println("Q TIPO DE MODIFICACION: " + strSQL);
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModTipDocUsr=rstChaDat.getInt("ne_tipresmoddoc");
                    }
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModTipDocUsr;
        
    }
    
    
        private boolean camposInactivosPermisoModifi(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            String strFecDocTmp;
            intTipModDoc=canTipoModificacion();
            switch(intTipModDoc){
                case 0:
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            
                            txtFecDoc.setEnabled(false);
                            txtNumAltUno.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_NO_EDI);
                            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }

                        }
                    }
                    break;
                case 1:
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){
                         txtFecDoc.setEnabled(false);
                            txtNumAltUno.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_NO_EDI);
                            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_NO_EDI);
                        
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2:
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){
                        if( ! strEstImpDoc.equals("S")){
                            txtFecDoc.setEnabled(true);
                            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
                            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
                        }
                        else{
                             txtFecDoc.setEnabled(false);
                            txtNumAltUno.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objAsiDia.setEditable(false);
                            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_NO_EDI);
                            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                            }
                    }
                    break;
                case 3:
                    txtFecDoc.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

        private int canChangeDate(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModFec=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModFec=4;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmodfecdoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    System.out.println("ACCESO A MODIFICAR FECHA: " + strSQL);
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModFec=rstChaDat.getInt("ne_tipresmodfecdoc");
                    }
                    stmChaDat.close();
                    stmChaDat=null;
                    rstChaDat.close();
                    rstChaDat=null;
                }
                conChaDat.close();
                conChaDat=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intTipModFec;
        
    }
    
    
      /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
   private boolean insertarCab()
    {
       
        int intCodUsr, intUltReg, intUltNumAlt;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el código del ltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();                
                System.out.println("---query para max cod_doc en funcion insertarCab:" + strSQL);                
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strSQL="";
                strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1 ";
                strSQL+=", nd_mondoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod)";
                strSQL+=" VALUES (";
                strSQL+=objParSis.getCodigoEmpresa();
                strSQL+=", " + objParSis.getCodigoLocal();
               strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", '" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", " + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", " + objUti.codificar((objUti.isNumero(txtMonDocPnd.getText())?"" + (intSig*Double.parseDouble(txtMonDocPnd.getText())):"0"),3);
                strSQL+=", " + objUti.codificar(txaObs1.getText());
                strSQL+=", " + objUti.codificar(txaObs2.getText());
                strSQL+=", 'A'";
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";
                strSQL+=", '" + strAux + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";



                strSQL+=")";
                System.out.println("--insertarCab(): " + strSQL);
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
        private boolean actualizarCab()
    {  
 
        boolean blnRes=true;
        try
        { 
            if (con!=null)
            {
                stm=con.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strAux=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strAux=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", nd_monDoc=" + objUti.codificar((objUti.isNumero(txtMonDocExc.getText())?"" + (intSig*Double.parseDouble(txtMonDocExc.getText())):"0"),3);
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";


                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                System.out.println("---SQL del update: " + strSQL+ ";");
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
  
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        if (objAsiDia.getGeneracionDiario()==2)
       {
            
            strAux="ï¿½Desea regenerar el asiento de diario?\n";
            strAux+="El asiento de diario ha sido modificado manualmente.";
            strAux+="\nSi desea que el sistema regenere el asiento de diario de click en SI.";
            strAux+="\nSi desea grabar el asiento de diario tal como estï¿½ de click en NO.";
            strAux+="\nSi desea cancelar ï¿½sta operaciï¿½n de click en CANCELAR.";
            switch (mostrarMsgCon(strAux))
            {
                case 0:             
                    objAsiDia.setGeneracionDiario((byte)0);                      
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
                    break;
                case 1:
                    break;
                case 2:
                    blnRes=false;
            }
        }
        
        else
        {
            
            objAsiDia.setGeneracionDiario((byte)0);
            if(txtMonDocExc.getText().toString().length()==0){
              txtMonDocExc.setText("0");
            }
            objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocPnd.getText());
        }
        return blnRes;
    }  

    
     private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        String strCodTipDoc, strNatDoc;
        int numdoc=0, intUltNumDoc=0;
        int intnumdoc=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_mod";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
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
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_mod";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
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
                }
                
                System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    
                    strCodTipDoc=(rst.getString("co_tipDoc"));
                    strNatDoc=(rst.getString("tx_natDoc"));
                                        
                    intnumdoc = (rst.getInt("ne_ultDoc"));
                    intUltNumDocAlt = intnumdoc;
                    System.out.println("---intnumdoc--en mostrarTipDocPre()--: " + intnumdoc);
                    
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtTipDoc.setText(rst.getString("tx_desCor"));
                    txtNomDoc.setText(rst.getString("tx_desLar"));
                    txtNumAltUno.setText("" + (rst.getInt("ne_ultDoc")+1));
                    strMod=(rst.getString("ne_mod"));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
                    
                    System.out.println("---strCodTipDoc--en mostrarTipDocPre()--: " + strCodTipDoc);
                    System.out.println("---strNatDoc--en mostrarTipDocPre()--: " + strNatDoc);
                    System.out.println("---intSig--en mostrarTipDocPre()--: " + intSig);
                    System.out.println("---strMod--en mostrarTipDocPre()--: " + strMod);
                    System.out.println("---intUltNumDocAlt--en mostrarTipDocPre()--: " + intUltNumDocAlt);  
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
     * Esta funciï¿½n muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarUltNumDoc()
    {
        boolean blnRes=true;
        String strCodTipDoc, strNatDoc;
        int numdoc=0, intUltNumDoc=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc1 ";
                strSQL+=" FROM tbm_cabpag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.co_Doc= (";
                strSQL+="  SELECT MAX(x1.co_doc) as co_doc";
                strSQL+="  FROM tbm_cabpag as x1";
                strSQL+="  WHERE x1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="  AND x1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+="  AND x1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" )";
                strSQL+=" order by a1.ne_numdoc1 ";
                System.out.println("---strSQL--cargarUltNumDoc()--: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                    
                    numdoc=(rst.getInt("ne_numdoc1"));
                    System.out.println("---numdoc--en cargarUltNumDoc()--: " + numdoc);
                    intUltNumDoc = numdoc+1;
                    System.out.println("---intUltNumDoc--en cargarUltNumDoc()--: " + intUltNumDoc);
                    intUltNumDocAlt = intUltNumDoc;
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
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    //este metodo es para insertar los registros de la tabla de credito
    private boolean insertarDetExc()
    {
        int intCodEmp, intCodLoc, i;
        String strCodTipDoc, strCodDoc;
        String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
        try
        {
            
            
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                System.out.println("---intSig--en antes de insertarDetExc()--: " + intSig);
                
                  for (i=0;i<objTblModExc.getRowCountTrue();i++)
                {         
                    if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                    {
                        if(((intCodMnu ==499))||(intCodMnu ==276)||(intCodMnu ==1673))
                         {
                           System.out.println("insertarDet()--intCodMnu--499 "+intCodMnu);
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                        strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                        strSQL+=" VALUES (";
                        strSQL+="" + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                         //Ctas por Cobrar
                        if(intSig==-1)
                            intSigAbo=-1;
                        else
                            intSigAbo=1; 
                        strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC), 3).toString()));
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        System.out.println("---insertarDetExc() --Credito--: " + strSQL + ";");
                        stm.executeUpdate(strSQL);
                        j++;
                    }
               else
               {  
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                        strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                        strSQL+=" VALUES (";
                        strSQL+="" + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                         //Ctas por Cobrar
                        if(intSig==1)
                            intSigAbo=-1;
                        else
                            intSigAbo=1;
                        
                         strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC), 3).toString()));
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        System.out.println("---insertarDetExc() --Credito--: " + strSQL + ";");
                        stm.executeUpdate(strSQL);
                        j++;   
               }
                    }  
                    
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

    //este metodo es para insertar los registros de la tabla de debito
     private boolean insertarDetPnd()
    {
        int intCodEmp, intCodLoc, i;
        String strCodTipDoc, strCodDoc;
        String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                System.out.println("---intSig--en antes de insertarDetPnd()--: " + intSig);
                for (i=0;i<objTblModPnd.getRowCountTrue();i++)
                {
                    if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                    {    
                         if((intCodMnu == 499)||(intCodMnu ==276)||(intCodMnu ==1673))
                         {
                             
                        System.out.println("insertarDetPnd()--intCodMnu--499 "+intCodMnu);
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                        strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                        strSQL+=" VALUES (";
                        strSQL+="" + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                        if(intSig==-1)
                            intSigAbo=1;
                        else
                            intSigAbo=-1; 
                        
                        strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC), 3).toString()));
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        System.out.println("---insertarDetPnd() --Debito: " + strSQL + ";");
                        stm.executeUpdate(strSQL);
                        j++;
                    }
                         else
                         {
                          strSQL="";
                        strSQL+="INSERT INTO tbm_detPag (co_emp, co_loc, co_tipDoc, co_doc, co_reg, co_locPag, co_tipDocPag, co_docPag";
                        strSQL+=", co_regPag, nd_abo, co_tipDocCon, co_banChq, tx_numCtaChq, tx_numChq, fe_recChq, fe_venChq)";
                        strSQL+=" VALUES (";
                        strSQL+="" + intCodEmp;
                        strSQL+=", " + intCodLoc;
                        strSQL+=", " + strCodTipDoc;
                        strSQL+=", " + strCodDoc;
                        strSQL+=", " + j;
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                        //Ctas por Cobrar
                        if(intSig==1)
                            intSigAbo=1;
                        else
                            intSigAbo=-1;
                        strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC), 3).toString()));
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        System.out.println("---insertarDetPnd() --Debito: " + strSQL + ";");
                        stm.executeUpdate(strSQL);
                        j++;    
                         }
                    }
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

//    /**
//     * Esta función inserta el registro en la base de datos.
//     * @return true: Si se pudo insertar el registro.
//     * <BR>false: En el caso contrario.
//     */
      private boolean actualizarTbmPagMovInvExc(int intTipOpe)
    {
        
        int intCodEmp, i, intCantRegAnu=0, intCantRegEli=0;
        double dblAbo1=0.0, dblAbo2=0.0, dblAbo3=0.0, dblSaldo=0.0,dblAbo4=0.0;
        boolean blnRes=true;                          
        arrValDocCre = new double [50];
        arrValAboCre = new double [50];
        arrNumFacCre = new String [50];
        arrCodLocCre = new String [50];
        arrSalRegCre = new double [50];
        arrTipDocCre = new String [50];
        arrCodDocCre = new String [50];
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {                
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    
                    if(intTipOpe==3)
                    {              
                        intCantRegAnu = objTblModExc.getRowCountTrue();
                        CAN_REG_ANU_EXC = intCantRegAnu;
                        System.out.println("--CAN_REG_ANU_EXC: " + CAN_REG_ANU_EXC);

                        for(i=0; i<objTblModExc.getRowCountTrue(); i++)
                        {
                            arrNumFacCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_NUM_DOC));
                            arrCodLocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_LOC));
                            arrTipDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                            arrCodDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_DOC));
                            arrValDocCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                            arrValAboCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_ABO_DOC));
                            arrSalRegCre[i] = objUti.redondear((arrValDocCre[i] + arrValAboCre[i]),2);
                        }
                    }
                    if(intTipOpe==2)
                    {                                    
                        /////////////PRUEBA///////////////////////
                        intCantRegEli = objTblModExc.getRowCountTrue();
                        CAN_REG_ELI_EXC = intCantRegEli;
                        System.out.println("--CAN_REG_ELI_EXC: " + CAN_REG_ELI_EXC);

                        for(i=0; i<objTblModExc.getRowCountTrue(); i++)
                        {
                            arrNumFacCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_NUM_DOC));
                            arrCodLocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_LOC));
                            arrTipDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                            arrCodDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_DOC));
                            arrValDocCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                            arrValAboCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_ABO_DOC));
                            arrSalRegCre[i] = objUti.redondear((arrValDocCre[i] + arrValAboCre[i]),2);
                        }
                    }
                    for (i=0;i<objTblModExc.getRowCountTrue();i++)
                    {                        
                        strEstLin=objUti.parseString(objTblModExc.getValueAt(i,INT_TBL_DAT_LIN));
                        STRESTLIN = strEstLin;
                        switch (intTipOpe)
                        {
                            case 0://insertar
                            case 1://modificar
                                if (!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals(""))
                                {
                                     if((intCodMnu ==499)||(intCodMnu ==276)||(intCodMnu ==1673))
                                    {
                                     dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                     dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                     dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                   
                                     dblSaldo=(dblAbo1-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--dblSaldo--: " + dblSaldo);
                                    strSQL=""; 
                                    strSQL+="UPDATE tbm_pagMovInv"; 
                                    strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---en actualizarTbmPagMovInvExc (499-276) 0 y 1 -- modifica&inserta--: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                    objTblModExc.setValueAt(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                           }else {     
                                     if((intCodMnu !=499)||(intCodMnu !=276))
                                    {    
                                     dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                     dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                     dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                     dblSaldo=(dblAbo1-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 0-1--dblSaldo--: " + dblSaldo);
                                    strSQL=""; 
                                    strSQL+="UPDATE tbm_pagMovInv"; 
                                    strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---en actualizarTbmPagMovInvExc -- 0 y 1 -- modifica&inserta--: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                    objTblModExc.setValueAt(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                                     }   
                           }
                                }
                                
                                break;
                            case 2://eliminar
                            case 3://anular
                                if(strMod.equals("3") || strMod.equals("1"))  
                                {                                    
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--AREA DE CXC--");
                                    
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    dblSaldo=(dblAbo3+dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";                                
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXC---en actualizarTbmPagMovInvExc (Else1)-- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                } else {
                                    if ((intCodMnu == 499)||(intCodMnu ==276))
                                    {
                                  System.out.println("---actualizarTbmPagMovInvExc() 499 --caso 2-3--AREA CXP--");
                                    
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                     
                                    dblSaldo = (dblAbo3+dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP---en actualizarTbmpagMovInvExc -- 2 y 3(499-276)---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                }
                                   if ((intCodMnu ==1673))
                                    {
                                  System.out.println("---actualizarTbmPagMovInvExc() 499 --caso 2-3--AREA CXP--");
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    dblSaldo = (dblAbo3+dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP---en actualizarTbmpagMovInvExc (1673)- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                     } 
                                     else
                                     {
                                    if ((intCodMnu !=499)&& (intCodMnu !=1673)&&(intCodMnu !=276))
                                    { 
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--AREA CXP--");
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    dblSaldo=(dblAbo3-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvExc()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";                                
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP---en actualizarTbmPagMovInvExc (Else2)-- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);   
                                     }
                                }
                                }
                                break;
                        }
                    }
                    
                    
                    stm.close();
                    stm=null;
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
      
      
      
      private boolean actualizarTbmPagMovInvPnd(int intTipOpe)
    {
        int intCodEmp, i, intCantRegAnu=0, intCantRegEli=0;
        double dblAbo1, dblAbo2, dblAbo3, dblSaldo;
        boolean blnRes=true;
         
        
        arrValDocPnd = new double [50];
        arrValAboPnd = new double [50];
        arrNumFacPnd = new String [50];
        arrCodLocPnd = new String [50];
        arrSalRegPnd = new double [50];
        arrTipDocPnd = new String [50];
        arrCodDocPnd = new String [50];
        
        int intCodMnu=0;
        intCodMnu = objParSis.getCodigoMenu();
        try
        {
            if (!objTooBar.getEstadoRegistro().equals("Anulado"))
            {
                
                if (con!=null)
                {
                    stm=con.createStatement();
                    intCodEmp=objParSis.getCodigoEmpresa();
                    
                    if(intTipOpe==3)
                    {                
                            intCantRegAnu = objTblModPnd.getRowCountTrue();
                            CAN_REG_ANU_PND = intCantRegAnu;
                            System.out.println("--CAN_REG_ANU_PND: " + CAN_REG_ANU_PND);

                            for(i=0; i<objTblModPnd.getRowCountTrue(); i++)
                            {
                                arrNumFacPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_NUM_DOC));
                                arrCodLocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_LOC));
                                arrTipDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                                arrCodDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC));
                                arrValDocPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                                arrValAboPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_ABO_DOC));
                                arrSalRegPnd[i] = objUti.redondear((arrValDocPnd[i] + arrValAboPnd[i]),2);
                            }
              
                    }
                    if(intTipOpe==2)
                    {   
                            intCantRegEli = objTblModPnd.getRowCountTrue();
                            CAN_REG_ELI_PND = intCantRegEli;
                            System.out.println("--CAN_REG_ELI_PND: " + CAN_REG_ELI_PND);

                            for(i=0; i<objTblModPnd.getRowCountTrue(); i++)
                             {
                                arrNumFacPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_NUM_DOC));
                                arrCodLocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_LOC));
                                arrTipDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC));
                                arrCodDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC));
                                arrValDocPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_DOC));
                                arrValAboPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_ABO_DOC));
                                arrSalRegPnd[i] = objUti.redondear((arrValDocPnd[i] + arrValAboPnd[i]),2);
                            }
                    }
                    for (i=0;i<objTblModPnd.getRowCountTrue();i++)
                    {
                        
                        strEstLin=objUti.parseString(objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN));
                        STRESTLIN = strEstLin;
                        
                        switch (intTipOpe)
                        {
                            case 0:
                            case 1://para modificar e insertar 
                                if (!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals(""))
                                { 
                                  if((intCodMnu ==499)||(intCodMnu ==276)||(intCodMnu ==1673))
                                     {
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                     dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                     dblSaldo =(dblAbo1-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---en actualizarTbmpagmovinvPnd -- 0 y 1 -- modifica&inserta--:" + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                    objTblModPnd.setValueAt(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                                }
                                  
                                      else
                                      {
                                       dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                     dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                   
                                     dblSaldo =(dblAbo1-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 0-1--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---en actualizarTbmpagmovinvPnd -- 0 y 1 -- modifica&inserta--:" + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                    objTblModPnd.setValueAt(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC),i,INT_TBL_DAT_AUX_ABO);
                                      }
                                }
                                break;
                            case 2:
                            case 3://para anular
                                    
                                if(strMod.equals("3") || strMod.equals("1"))
                                {
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--AREA CXC--");
                                    
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    dblSaldo = (dblAbo3-dblAbo2);
                                        
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXC---en actualizarTbmpagMovInvPnd (Else1)-- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                }
                                else
                                {
                                   if((intCodMnu ==499)||(intCodMnu ==276))
                                    {
                                     System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--AREA CXP--");
         
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    dblSaldo=(dblAbo3-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";                                
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP 499 -en actualizarTbmPagMovInvPnd -- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL); 
                                   }
                                   
                                    if((intCodMnu ==1673))
                                    {
                                     System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--AREA CXP--");
         
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    dblSaldo=(dblAbo3-dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";                                
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP 1673 -en actualizarTbmPagMovInvPnd (1673)- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL); 
                                   }
                                   else 
                                   {
                                       if((intCodMnu !=499)&&(intCodMnu !=276)&&(intCodMnu !=1673))
                                       
                                       {
                                       System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--AREA CXP--");
                                    
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_ABO_DOC));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    dblSaldo = (dblAbo3+dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO --dblAbo1--: " + dblAbo1);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_AUX_ABO --dblAbo2--: " + dblAbo2);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--INT_TBL_DAT_VAL_ABO_PAG --dblAbo3--: " + dblAbo3);
                                    System.out.println("---actualizarTbmPagMovInvPnd()--caso 2-3--dblSaldo--: " + dblSaldo);
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    System.out.println("---CXP---en actualizarTbmpagMovInvPnd (Else2) -- 2 y 3---Anula&Elimina: " + strSQL + ";");
                                    stm.executeUpdate(strSQL);
                                   }
                                   }
                                }
                                break;
                        }
                    }
                    stm.close();
                    stm=null;
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
   
      
    private boolean insertarReg()
    {
        boolean blnRes=false;
        int intFilSelValExc=0;
        int intFilSelValPnd=0;
        try
        {
           con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                for(int i=0; i<objTblModExc.getRowCountTrue();i++){
                    if(objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                        intFilSelValExc++;
                }
                for(int i=0; i<objTblModPnd.getRowCountTrue();i++){
                    if(objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                        intFilSelValPnd++;
                }
                if(intFilSelValExc>0 && intFilSelValPnd>0){
                    if (insertarCab()){
                        if (insertarDetExc()){
                            if (insertarDetPnd()){
                                if (actualizarTbmPagMovInvExc(0)){
                                    if (actualizarTbmPagMovInvPnd(0)){
                                        if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(), "dd/MM/yyyy")    )){
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
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                }
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
     * Esta funciï¿½n permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detPag";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                System.out.println(" " + strSQL + ";");
                
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
     * Esta funciï¿½n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg()
    {
        boolean blnRes=false;
        try
                
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null)
            {  System.out.println("I: " + I);
                if(I > 0)
                { 
                  System.out.println("J:" + J);
                    if(J > 0)
                    {
                        if (actualizarCab())
                        {
                            
                            if (eliminarDet())
                            {
                                if (insertarDetExc())
                                {
                                    if (insertarDetPnd())
                                    {
                                        if (actualizarTbmPagMovInvExc(1))
                                        {
                                            if (actualizarTbmPagMovInvPnd(1))
                                            {
                                               if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A"))
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
                    {
                        mostrarMsgInf("NO HA SELECCIONADO REGISTROS EN LA TABLA DE CREDITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
                    }
                }
                else
                {
                    mostrarMsgInf("NO HA SELECCIONADO REGISTROS EN LA TABLA DE DEBITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
                }
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
     * Esta funciï¿½n permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */    
    private boolean anularCab()
    {
        boolean blnRes=true;
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
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";

                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                System.out.println("---->Query para Anular cabpag: "+ strSQL);
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
     * Esta funciï¿½n anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
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
                    if (actualizarTbmPagMovInvExc(3))
                    {
                        if (actualizarTbmPagMovInvPnd(3))
                        {       
                         if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
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
     * Esta funciï¿½n elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
     
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
                        if (actualizarTbmPagMovInvExc(2))
                        {                        
                            if (actualizarTbmPagMovInvPnd(2))
                            {
                                    if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
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

     public class mitoolbar extends ZafToolBar{
            public mitoolbar(javax.swing.JInternalFrame jfrThis){
                super(jfrThis, objParSis);
            }

        public void clickInsertar()
        {
            try
            {
              if (blnHayCam)
                {
                    isRegPro();
                }
                if (rstCab!=null)//SI HAY REGISTROS
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
              
                limpiarFrm();
                txtCodDoc.setEditable(false);
                txtMonDocExc.setEditable(false);
                txtMonDocPnd.setEditable(false);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                txtFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                
                if (!txtCodTipDoc.getText().equals(""))
                   
                objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
                objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
                objAsiDia.setEditable(true);
                txtNumAltUno.selectAll();
               
                //Inicializar las variables que indican cambios.
                objAsiDia.setDiarioModificado(false);
                blnHayCam=false;
                txtMonDocExc.setEditable(false);
                txtMonDocPnd.setEditable(false);
                txtFecDoc.setEnabled(true);
                txtNumAltUno.setEnabled(true);
//            }

               // cargarDocExc();
                //cargarDocPend();
               objTblModExc.setModoOperacion(objTblModExc.INT_TBL_INS);
               objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_INS);
               objAsiDia.setEditable(true);
               txtNumAltUno.selectAll();
               txtNumAltUno.requestFocus();
               banins++;
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
       
        
        public boolean insertar(){
            if (!isCamVal())
                return false;
            if (!insertarReg())
                return false;
            return true;
        }
        public void clickConsultar(){     
       
           //deshabilita los campos de la suma de los abonos 
            txtMonDocExc.setEditable(false);
            txtMonDocPnd.setEditable(false);
            mostrarTipDocPre();
            txtNumAltUno.setText("");
            txtTipDoc.requestFocus();
            bancon++;  
        }
       public boolean consultar() {
           consultarReg(); 
           return true;                
       }
       
       public void clickModificar(){
           
           intbanclimod++;
           txtTipDoc.setEditable(false);
           txtNomDoc.setEditable(false);
           butDoc.setEnabled(false);
           txtCodDoc.setEditable(false);
           txtMonDocExc.setEditable(false);
           txtMonDocPnd.setEditable(false);
           objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
           objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
           objAsiDia.setEditable(true);
           consultarReg();
           txtNumAltUno.selectAll();
           txtNumAltUno.requestFocus(); 
            if(camposInactivosPermisoModifi()){ 
            }
         }
       
       
       public boolean modificar(){                           
            if (!actualizarReg())
                return false;
            return true;
       }
       public void clickEliminar()
        {
            txtMonDocExc.setEditable(false);
            txtMonDocPnd.setEditable(false);
            cargarDetRegExc();
            cargarDetRegPnd();
        }
        public boolean eliminar(){
            try
            {
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast())
               {
                    rstCab.next();
                    cargarReg();
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
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
                txtMonDocExc.setEditable(false);
                txtMonDocPnd.setEditable(false);
                cargarDetRegExc();
                cargarDetRegPnd();
        }

        public boolean anular(){   
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;                        
            }    
             
     public void clickInicio() {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.first();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.first();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                            }
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
     
     public void clickAnterior() {
            try
            {
                if (!rstCab.isFirst())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            rstCab.previous();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else
                    {
                        rstCab.previous();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                       }
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
     
     public void clickSiguiente() {
        try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam)
                    {
                        if(isRegPro())
                        {
                            rstCab.next();
                            cargarReg();
                            
            if(camposInactivosPermisoModifi()){
            }
                        }
                    }
                    else
                    {
                        rstCab.next();
                        cargarReg();
                     if(camposInactivosPermisoModifi()){
            }
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
     
     public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam || objAsiDia.isDiarioModificado())
                    {
                        if (isRegPro())
                        {
                            rstCab.last();
                            cargarReg();
                            
            if(camposInactivosPermisoModifi()){
                
            }
            
                        }
                    }
                    else
                    {
                        rstCab.last();
                        cargarReg();
                        
                  if(camposInactivosPermisoModifi()){
                
                         }
            
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
            
     public void clickImprimir(){
     }
     
     public void clickVisPreliminar(){
     }
     
     public void clickAceptar(){
         bancon=0;
         banins=0;
     }

     public void clickCancelar(){
          limpiarFrm();
          //deshabilita los campos de la suma de los abonos 
          txtMonDocExc.setBackground(objParSis.getColorCamposSistema());
          txtMonDocPnd.setBackground(objParSis.getColorCamposSistema());

          objTblModExc.removeAllRows();
          objTblModPnd.removeAllRows();
          bancon=0;
          banins=0;
     }

     private int Mensaje(){
                String strTit, strMsg;
                strTit="Mensaje del sistema Zafiro";
                strMsg="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
                strMsg+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
                javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                return obj.showConfirmDialog(jfrThis ,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);                
     }
     private void MensajeValidaCampo(String strNomCampo){
                    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
                    String strTit, strMsg;
                    strTit="Mensaje del sistema Zafiro";
                    strMsg="El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
                    obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
     }
     private boolean validaCampos(){               
            
               if(!txtFecDoc.isFecha()){
                   MensajeValidaCampo("Fecha de Documento");
                   txtFecDoc.requestFocus();
                   return false;
               }
               int intColObligadas[] = {INT_TBL_NUM_DOC,INT_TBL_DAT_VAL_DOC,INT_TBL_DAT_VAL_PND,INT_TBL_ABO_DOC};
               if( !objUti.verifyTbl(tblValPnd,intColObligadas)){
                   tabFrm.setSelectedIndex(0);                  
                    javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
                        String strTit, strMsg;
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="Existe un campo en el Detalle que es obligatorio y no esta ingresado.\nEscriba en el campo y vuelva a intentarlo.";
                        obj.showMessageDialog(jfrThis,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
                    
                    objUti.verifyTbl(tblValPnd,intColObligadas);
                    return false;
               }
               
               /*
                * Validando que la suma de los abonos sean igual al monto del documento
                */               
               double dblTotAbo=0.00;
               for(int rowDet=0;rowDet<tblValPnd.getRowCount();rowDet++)
               {
                    dblTotAbo += Double.parseDouble((tblValExc.getValueAt(rowDet,11)==null)?"0":tblValExc.getValueAt(rowDet,11).toString());
               }
                        
         return true; 
          }
    
      public String retNomEmp(int codEmp){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{            
            conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conTipDoc!=null){
                stmTipDoc=conTipDoc.createStatement();
                que="";
                que+=" select tx_nom from tbm_emp";
                que+=" where co_emp=" + codEmp + "";                                                
//                System.out.println("el query del nombre de la empresa es:"+que);
                rstTipDoc=stmTipDoc.executeQuery(que);
                if (rstTipDoc.next()){
                    auxTipDoc=rstTipDoc.getString("tx_nom");
                }
            }
            conTipDoc.close();
            conTipDoc=null;
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
      
       public boolean vistaPreliminar(){

            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(1);
                objThrGUI.start();
            }
            return true;
       }
       
        public boolean afterVistaPreliminar() {
            return true;
        }        


        
       public boolean imprimir(){
            if (objThrGUI==null)
            {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.setIndFunEje(0);
                objThrGUI.start();
            }
            return true;          
        }



       public boolean afterAceptar() {
            return true;
        }        
   
          public boolean afterInsertar() 
        {
            blnHayCam=false;
            objTooBar.setEstado('w');
            blnHayCam=false;
            consultarRegIns();
            
            System.out.println("---AFTER INSERTAR()---TERMINO DE INSERTAR---");
            if(verificarDatoAjuste())
            {
                System.out.println("---AFTER INSERTAR()---ENTRO A LA FUNCION verificarDatoAjuste()---");
                double valor=0.00, sumval=0.00;
                int k=0, l=0;
                System.out.println(" ");
                System.out.println(" ");
                objAjuAutSis.consultarReg();

                l=objTblModExc.getRowCountTrue();
                System.out.println("---l " + l);

                for (int i=0;i<objTblModExc.getRowCountTrue();i++)
                {
                    if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                    {       
                        objAjuAutSis.mostrarDatIns();
                       int codloc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC));
                       int codtipdoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC));
                       int coddoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC));
                       INTCODLOC = codloc;
                       INTTIPDOC = codtipdoc;
                       INTCODDOC = coddoc;
                       cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                       valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                       sumval = sumval + valor;
                       
                       String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                       System.out.println("---strFecDocPag " + strFecDocPag);
                       if(cant>0)
                       {
                           if(p>=i)
                               i--;
                           if(i==0)
                           {
                               k=i;
                               k++;
                           }
                           else
                               k=cant;
                           objAjuAutSis.insertarRegAut(k,strFecDocPag);
                       }
                       else
                       {
                           p++;
                       }
                    }
                }
                System.out.println(" ");
                System.out.println(" ");
                objAjuAutSis.consultarReg();
                for (int i=0;i<objTblModPnd.getRowCountTrue();i++)
                {
                    if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                    {                   
                        objAjuAutSis.mostrarDatIns();
                       int codloc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC));
                       int codtipdoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC));
                       int coddoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC));

                       INTCODLOC = codloc;
                       INTTIPDOC = codtipdoc;
                       INTCODDOC = coddoc;
                       cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                       valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                       sumval = sumval + valor;
                       String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                       System.out.println("---strFecDocPag " + strFecDocPag);
                       if(cant>0)
                       {
                           if(p>=i)
                               i--;
                           if(i==0)
                           {
                               k=i;
                               k++;
                           }
                           else
                               k=cant;
                           objAjuAutSis.insertarRegAut(k,strFecDocPag);
                       }
                       else
                       {
                           p++;
                       }
                    }
                }
            }
            objAsiDia.setDiarioModificado(false);
            return true;
        }

          
           public boolean afterModificar() 
        {
            objAsiDia.setDiarioModificado(false);
            blnHayCam=false;
             strFecDocIni=txtFecDoc.getText();
            if(verificarDatoAjuste())
            {
                double valor=0.00, sumval=0.00;
                objAjuAutSis.mostrarDatIns();
                    for (int i=0;i<objTblModExc.getRowCountTrue();i++)
                    {
                        if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                        {
                                objAjuAutSis.mostrarDatIns();
                               int codloc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC));
                               int codtipdoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC));
                               int coddoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC));
                               INTCODLOC = codloc;
                               INTTIPDOC = codtipdoc;
                               INTCODDOC = coddoc;

                               cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               sumval = sumval + valor;
                               
                               String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                               System.out.println("---strFecDocPag " + strFecDocPag);
                                if(cant>0)
                               {
                                   if(i==0)
                                   {
                                       k=i;
                                       k++;
                                   }
                                   else
                                   k=cant;
                                   objAjuAutSis.insertarRegAut(k,strFecDocPag);
                               }
                               else
                               {
                               }

                        }
                    }  
                    for (int i=0;i<objTblModPnd.getRowCountTrue();i++)
                    {
                        if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                        {
                        
                                objAjuAutSis.mostrarDatIns();
                               int codloc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC));
                               int codtipdoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_TIP_DOC));
                               int coddoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC));
                               INTCODLOC = codloc;
                               INTTIPDOC = codtipdoc;
                               INTCODDOC = coddoc;

                               cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               System.out.println("---cant " + cant);

                               valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               System.out.println("---valor " + valor);
                       
                               sumval = sumval + valor;
                               
                               String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                               System.out.println("---strFecDocPag " + strFecDocPag);
                               if(cant>0)
                               {
                                   if(i==0)
                                   {
                                       k=i;
                                       k++;
                                   }
                                   else
                                       k=cant;
                                   objAjuAutSis.insertarRegAut(k,strFecDocPag);
                               }
                               else
                               {
                               }

                        }
                    }
        }
            
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }
          
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean aceptar() {                        
            return true;
        }
        
        public boolean cancelar() {
            return true;
        }
        
        public boolean afterConsultar() {
            return true;
        }
      public boolean beforeInsertar()
        {
           calcularAboTot();
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            
            return true;
        }

        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            calcularAboTot();
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            if (objAsiDia.getGeneracionDiario()==2)
                return regenerarDiario();
            return true;
  
        }
        public boolean beforeEliminar()
        {
            objAjuAutSis.mostrarDatIns();
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals(""))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            objAjuAutSis.mostrarDatIns();
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya está ANULADO.\nNo es posible anular un documento anulado.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir()
        {
            return true;
        }

        public boolean beforeVistaPreliminar()
          {
            boolean blnRes=true;
            return blnRes;
            
        }

        public boolean beforeAceptar()
        {
            return true;
        }

        public boolean beforeCancelar()
        {
            return true;
        }     
   }        
     
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            System.out.println("ZafAsiDia.tableChanged");
            System.out.println("Celda modificada: (" + tblValPnd.getEditingRow() + "," + tblValPnd.getEditingColumn() + ")");
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_SEL){
                    }
                    else
                        if(tblValPnd.getEditingColumn()==INT_TBL_ABO_DOC){
                            if (objHilo==null) {
                                objHilo = new tblHilo();
                                objHilo.start();
                                objHilo = null;
                            }
                    }
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_VAL_DOC){
                        
                    }
                    break;
            }
        }
    } 
      class tblHilo extends Thread{
            public void run(){
                System.out.println("despues de llamar a monDoc");
            }
     }
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
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
        private int intIndFun;

        public ZafThreadGUI()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Boton "Imprimir".
                    objTooBar.setEnabledImprimir(false);
                    generarRpt(1);
                    objTooBar.setEnabledImprimir(true);
                    break;
                case 1: //Boton "Vista Preliminar".
                    objTooBar.setEnabledVistaPreliminar(false);
                    generarRpt(2);
                    objTooBar.setEnabledVistaPreliminar(true);
                    break;
            }
            objThrGUI=null;
        }

        /**
         * Esta funciï¿½n establece el indice de la funciï¿½n a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta funciï¿½n sirve para determinar
         * la funciï¿½n que debe ejecutar el Thread.
         * @param indice El indice de la funciï¿½n a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }


  /**
     * Esta funciï¿½n permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresiï¿½n directa.
     * <LI>1: Impresiï¿½n directa (Cuadro de dialogo de impresiï¿½n).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
    private boolean generarRpt(int intTipRpt)
    {
        String strRutRpt, strNomRpt, strFecHorSer, strBan="";
        int i, intNumTotRpt;
        boolean blnRes=true;

        String CodTipDoc = txtCodTipDoc.getText();
        String CodDoc = txtCodDoc.getText();


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
                        strFecHorSer=objUti.formatearFecha(datFecAux, "yyyy/MMM/dd HH:mm:ss");
                        datFecAux=null;

                        intNumTotRpt=objRptSis.getNumeroTotalReportes();
                        for (i=0;i<intNumTotRpt;i++)
                        {
                            if (objRptSis.isReporteSeleccionado(i))
                            {

                                            strRutRpt=objRptSis.getRutaReporte(i);
                                            strNomRpt=objRptSis.getNombreReporte(i);

                                            mapPar.put("co_emp", new Integer(objParSis.getCodigoEmpresa()) );
                                            mapPar.put("co_loc",  new Integer(objParSis.getCodigoLocal()) );
                                            mapPar.put("co_tipdoc", new Integer( Integer.parseInt(CodTipDoc)));
                                            mapPar.put("co_doc", new Integer( Integer.parseInt(CodDoc)));
                                            mapPar.put("titulo", ""+objParSis.getNombreMenu());
                                            mapPar.put("nomemp", ""+objParSis.getNombreEmpresa() );
                                            mapPar.put("strCamAudRpt", ""+ this.getClass().getName() + "      " +  strNomRpt +"   " + objParSis.getNombreUsuario() + "      " + strFecHorSer +"      v0.1    " );
                                            mapPar.put("SUBREPORT_DIR", ""+strRutRpt);
                                            objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);


                            }
                        }
                    }
                }
                conIns.close();
                conIns=null;
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


    
    
    }