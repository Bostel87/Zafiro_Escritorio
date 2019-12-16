/*encuentra los registros pero no los adiciona al detalle del JTable
 * ZafCxC24.java
 * 
 * Created on 28 de abril de 2006
 * PROGRAMA CRUCE DE DOCUMENTOS SE MODIFICO 02/diciembre/2009
 */      
package CxC.ZafCxC24;
import GenOD.ZafGenOdPryTra;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import java.util.Vector;
import Librerias.ZafColNumerada.ZafColNumerada;
//import Librerias.ZafReglas.GenOD.ZafGenOdDaoPryTra;
import GenOD.ZafGenOdDaoPryTra;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafCnfDoc.ZafCnfDoc;
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
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;  //*******************

//Dario_para llamar a clase de Ajuste Automatico//
import Librerias.ZafUtil.ZafAjuAutSis;
import ZafReglas.ZafGenGuiRem;
import ZafReglas.ZafGuiRemDAO;
import ZafReglas.ZafImp;
import ZafReglas.ZafMetImp;
import java.util.Iterator;

//import Librerias.ZafAsiDia.ZafAsiDia;
/**  
 *
 * @author  Dario Cardenas
 */
public class ZafCxC24 extends javax.swing.JInternalFrame {
    final int INT_TBL_DAT_LIN=0;///0
    final int INT_TBL_DAT_SEL=1;///1
    final int INT_TBL_DAT_COD_LOC=2;///2
    final int INT_TBL_DAT_TIP_DOC=3;///3
    final int INT_TBL_DAT_DES_COR=4;///4
    final int INT_TBL_DAT_NOM_DOC=5;///5
    final int INT_TBL_DAT_NUM_DOC=6;///6
    final int INT_TBL_DAT_NUM_COT=7;///7
    final int INT_TBL_DAT_FEC_DOC=8;///8
    final int INT_TBL_DAT_DIA_CRE=9;///9
    final int INT_TBL_DAT_FEC_VEN=10;///10
    final int INT_TBL_DAT_POR_RET=11;///11
    final int INT_TBL_DAT_MON_DOC=12;///12
    final int INT_TBL_DAT_VAL_PND=13;///13
    final int INT_TBL_DAT_VAL_PND_INI=14;///14
    final int INT_TBL_DAT_COD_REG=15;///15
    final int INT_TBL_DAT_COD_DOC=16;///16
    final int INT_TBL_DAT_VAL_ABO=17;///17
    final int INT_TBL_DAT_AUX_ABO=18;///18
    final int INT_TBL_DAT_NAT_DOC=19;///19
    final int INT_TBL_DAT_VAL_ABO_PAG=20;///20
    
    final String STR_COD_CTA_POR_RET="983";//el codigo de la cuenta de retencion que se encuentra en la tabla de plan de cuentas
    final int INT_POR_RET=1;
    
    
    final int INT_NUM_DEC=2;
    boolean blnCambios = false; //Detecta ke se hizo cambios en el documento
    //    final int varTipDoc=12;
    private Librerias.ZafDate.ZafDatePicker txtFecDoc;
    javax.swing.JInternalFrame jfrThis; //Hace referencia a this
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDatExc, vecCabExc;
    private Vector vecDatPnd, vecCabPnd;
    private boolean blnHayCam, blnMarTodChkTblDatCre=true;          //Determina si hay cambios en el formulario.
    private boolean blnMarTodChkTblDatDeb=true;          //Determina si hay cambios en el formulario.
    private ZafMouMotAda objMouMotAda;          //ToolTipText en TableHeader.
    private Vector    vecAux;
    private ZafToolBar objToolBar;//true: Continua la ejecuciï¿½n del hilo.
    private String strCodCli, strDesLarCli,strIdePro, strDirPro;    
    private java.util.Vector vecRegExc, vecRegPnd;
    private mitoolbar objTooBar;
    private String  strMod, STRNUMDOC="";
    private java.sql.Connection conCab, con, conAux;
    private java.sql.Statement stmCab, stm;
    private java.sql.ResultSet rstCab, rst;
    private tblHilo objHilo;
    //private ZafAsiDia objAsiDia;

    private ZafTblMod objTblModExc;
    private ZafTblMod objTblModPnd;
    private ZafThreadGUI objThrGUI;
    private ZafCxC24.ZafTblModLis objTblModLisExc;
    private ZafCxC24.ZafTblModLis objTblModLisPnd;
    
    //creacion de check
    private ZafTblCelRenChk objTblCelRenChkExc;
    private ZafTblCelRenChk objTblCelRenChkPnd;
    private ZafTblCelEdiChk objTblCelEdiChkExc;
    private ZafTblCelEdiChk objTblCelEdiChkPnd;
    
    //creacion de label
    private ZafTblCelRenLbl objTblCelRenLblExc;
    private ZafTblCelRenLbl objTblCelRenLblPnd;
    
    //creacion de txt
    private ZafTblCelEdiTxt objTblCelEdiTxtExc;
    private ZafTblCelEdiTxt objTblCelEdiTxtPnd;
    private int j=1, bancon=0, banins=0, I=0, J=0, intTotRegSelI=0, intTotRegSelJ=0, intUltNumDocAlt=0, intbanmodcel=0, intBanDocExi=0;
    private int intbanclimod=0, intCodMnu=0;
    private ZafTblEdi objTblEdi;
    private ZafRptSis objRptSis;                        //Reportes del Sistema.    
    private double dblMonDoc=0.00, VAL_DOC_EXC=0.00, VAL_DOC_PEN=0.00, VAL_DOC_PEN_AUX=0.00, VAL_DOC_EXC_AUX=0.00;
    private int intSig=1, intSigAbo=1;                              //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private String strTipDoc, strDesLarTipDoc;        //Contenido del campo al obtener el foco.
    private String strUbiCta;                           //Campos: Ubicaciï¿½n de la cuenta.        
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.        
    ZafVenCon objVenConTipdoc;
    private ZafVenCon vcoTipDoc;                //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCli;                   //Ventana de consulta "Cliente".
    private Librerias.ZafUtil.ZafTipDoc objTipDoc;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMenï¿½ en JTable.
    
    /*Dario_Objeto del Tipo ZafAjuAutSis*/
    private ZafAjuAutSis objAjuAutSis;    
    private int k=0, cant=0, p=0, INTCODLOC=0, INTCODDOC=0, INTTIPDOC=0, CAN_REG_ELI_PND=0, CAN_REG_ELI_EXC=0, CAN_REG_ANU_PND=0, CAN_REG_ANU_EXC=0;
    private int bancelmod=0, CodDocEli=0, CodLocEli=0, TipDocEli=0, banclickCre=0, banclickDeb=0;
    private String STRESTLIN, strEstLin, STRNUMDOCCAB;
    private String arrNumFacCre[], arrCodLocCre[], arrTipDocCre[], arrCodDocCre[];
    private double arrValDocCre[];
    private double arrValAboCre[];
    private double arrSalRegCre[];
    private String arrNumFacPnd[], arrCodLocPnd[], arrTipDocPnd[], arrCodDocPnd[];
    private double arrValDocPnd[];
    private double arrValAboPnd[];
    private double arrSalRegPnd[];
    private ZafAsiDia objAsiDia; // Asiento de Diario
    
    Vector vecDocSinPnd=new Vector();

    
    /** Crea una nueva instancia de la clase ZafCon01. */
    public ZafCxC24(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();
            
            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);
            
            objUti=new ZafUtil();
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y"); 
//            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setText("");
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(516, 4, 148, 20);

            objTooBar=new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones
            objAsiDia=new Librerias.ZafAsiDia.ZafAsiDia(this.objParSis);
             objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
             @Override
             public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                 if (txtCodTipDoc.getText().equals(""))
                     objAsiDia.setCodigoTipoDocumento(-1);
                 else
                    objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
              }
            });
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            if (!configurarFrm())
                exitForm();
            
            objAsiDia=new ZafAsiDia(objParSis);
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);            
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
        
    }



    /** Crea una nueva instancia para la clase ZafVen16_01. */
    public ZafCxC24(ZafParSis obj, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento, int codigoMenu)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis = this;
            objParSis=(ZafParSis)obj.clone();

            objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objParSis);

            objUti=new ZafUtil();
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame)jfrThis.getParent()),"d/m/y");
            panGenCab.add(txtFecDoc);
            txtFecDoc.setBounds(516, 4, 148, 20);
            txtFecDoc.setText("");

            objTooBar=new mitoolbar(this);
            panBar.add(objTooBar);//llama a la barra de botones.

            txtCodTipDoc.setText(""+codigoTipoDocumento);
            txtCodDoc.setText(""+codigoDocumento);
            objParSis.setCodigoLocal(codigoLocal);
            objParSis.setCodigoMenu(codigoMenu);

            if (!configurarFrm())
                exitForm();



            consultarReg();
            objTooBar.setVisible(false);
            objAsiDia=new ZafAsiDia(objParSis);
            panAsiDia.add(objAsiDia, java.awt.BorderLayout.CENTER);         

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

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtTipDoc = new javax.swing.JTextField();
        txtNomDoc = new javax.swing.JTextField();
        butDoc = new javax.swing.JButton();
        lblCom = new javax.swing.JLabel();
        txtNumAltUno = new javax.swing.JTextField();
        lblAte = new javax.swing.JLabel();
        txtNumAltDos = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblPro = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtDesLarCli = new javax.swing.JTextField();
        txtCodCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        txtCodDoc = new javax.swing.JTextField();
        txtNumCta = new javax.swing.JTextField();
        txtNumPro = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        sppGenDet = new javax.swing.JSplitPane();
        panGenDetCre = new javax.swing.JPanel();
        spnValExc = new javax.swing.JScrollPane();
        tblValExc = new javax.swing.JTable();
        panGenDetDeb = new javax.swing.JPanel();
        spnValPnd = new javax.swing.JScrollPane();
        tblValPnd = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panGenTotTot = new javax.swing.JPanel();
        lblMonDocExc = new javax.swing.JLabel();
        lblMonDocPnd = new javax.swing.JLabel();
        lblDif = new javax.swing.JLabel();
        txtMonDocExc = new javax.swing.JTextField();
        txtMonDocPnd = new javax.swing.JTextField();
        lblValDif = new javax.swing.JLabel();
        panAsiDia = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setPreferredSize(new java.awt.Dimension(600, 80));
        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(610, 64));
        panGenCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

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
        panGenCab.add(txtTipDoc);
        txtTipDoc.setBounds(100, 4, 56, 20);

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
        panGenCab.add(txtNomDoc);
        txtNomDoc.setBounds(156, 4, 236, 20);

        butDoc.setText("...");
        butDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDocActionPerformed(evt);
            }
        });
        panGenCab.add(butDoc);
        butDoc.setBounds(392, 4, 20, 20);

        lblCom.setText("Número alterno 1:");
        lblCom.setToolTipText("Número de documento alterno 1");
        lblCom.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblCom);
        lblCom.setBounds(416, 24, 100, 20);

        txtNumAltUno.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumAltUno.setPreferredSize(new java.awt.Dimension(100, 20));
        txtNumAltUno.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumAltUnoFocusLost(evt);
            }
        });
        panGenCab.add(txtNumAltUno);
        txtNumAltUno.setBounds(516, 24, 148, 20);

        lblAte.setText("Número alterno 2:");
        lblAte.setToolTipText("Número de documento alterno 2");
        lblAte.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblAte);
        lblAte.setBounds(416, 44, 100, 20);

        txtNumAltDos.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panGenCab.add(txtNumAltDos);
        txtNumAltDos.setBounds(516, 44, 148, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        lblFecDoc.setPreferredSize(new java.awt.Dimension(100, 15));
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(416, 4, 100, 20);

        lblPro.setText("Cliente:");
        lblPro.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblPro);
        lblPro.setBounds(0, 24, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        lblCodDoc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 44, 100, 20);

        txtDesLarCli.setMaximumSize(null);
        txtDesLarCli.setPreferredSize(new java.awt.Dimension(70, 20));
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
        txtDesLarCli.setBounds(156, 24, 236, 20);

        txtCodCli.setMaximumSize(null);
        txtCodCli.setPreferredSize(new java.awt.Dimension(70, 20));
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
        txtCodCli.setBounds(100, 24, 56, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panGenCab.add(butCli);
        butCli.setBounds(392, 24, 20, 20);

        txtCodDoc.setMaximumSize(null);
        txtCodDoc.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(100, 44, 124, 20);

        txtNumCta.setMaximumSize(null);
        txtNumCta.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtNumCta);
        txtNumCta.setBounds(180, 25, 0, 0);

        txtNumPro.setMaximumSize(null);
        txtNumPro.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenCab.add(txtNumPro);
        txtNumPro.setBounds(180, 46, 0, 0);

        txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(106, 4, 0, 0);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        sppGenDet.setBorder(null);
        sppGenDet.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        sppGenDet.setResizeWeight(0.5);
        sppGenDet.setOneTouchExpandable(true);

        panGenDetCre.setBorder(javax.swing.BorderFactory.createTitledBorder("Créditos"));
        panGenDetCre.setAutoscrolls(true);
        panGenDetCre.setPreferredSize(new java.awt.Dimension(935, 125));
        panGenDetCre.setLayout(new java.awt.BorderLayout());

        tblValExc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnValExc.setViewportView(tblValExc);

        panGenDetCre.add(spnValExc, java.awt.BorderLayout.CENTER);

        sppGenDet.setTopComponent(panGenDetCre);

        panGenDetDeb.setBorder(javax.swing.BorderFactory.createTitledBorder("Débitos"));
        panGenDetDeb.setAutoscrolls(true);
        panGenDetDeb.setPreferredSize(new java.awt.Dimension(20, 125));
        panGenDetDeb.setLayout(new java.awt.BorderLayout());

        tblValPnd.setModel(new javax.swing.table.DefaultTableModel(
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
        spnValPnd.setViewportView(tblValPnd);

        panGenDetDeb.add(spnValPnd, java.awt.BorderLayout.CENTER);

        sppGenDet.setBottomComponent(panGenDetDeb);

        panGen.add(sppGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(518, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(92, 16));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs1.setText("Observación 1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblObs1.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs1.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs1.setPreferredSize(new java.awt.Dimension(92, 8));
        panGenTotLbl.add(lblObs1);

        lblObs2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblObs2.setText("Observación 2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        lblObs2.setMaximumSize(new java.awt.Dimension(92, 15));
        lblObs2.setMinimumSize(new java.awt.Dimension(92, 15));
        lblObs2.setPreferredSize(new java.awt.Dimension(92, 8));
        lblObs2.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setPreferredSize(new java.awt.Dimension(2, 10));
        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1, 0, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGenTotTot.setPreferredSize(new java.awt.Dimension(176, 0));
        panGenTotTot.setLayout(null);

        lblMonDocExc.setText("Créditos:");
        lblMonDocExc.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenTotTot.add(lblMonDocExc);
        lblMonDocExc.setBounds(4, 0, 72, 20);

        lblMonDocPnd.setText("Débitos:");
        lblMonDocPnd.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenTotTot.add(lblMonDocPnd);
        lblMonDocPnd.setBounds(4, 20, 72, 20);

        lblDif.setText("Diferencia:");
        lblDif.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenTotTot.add(lblDif);
        lblDif.setBounds(4, 40, 72, 20);

        txtMonDocExc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonDocExc.setMaximumSize(null);
        txtMonDocExc.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenTotTot.add(txtMonDocExc);
        txtMonDocExc.setBounds(76, 0, 100, 20);

        txtMonDocPnd.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtMonDocPnd.setMaximumSize(null);
        txtMonDocPnd.setPreferredSize(new java.awt.Dimension(70, 20));
        panGenTotTot.add(txtMonDocPnd);
        txtMonDocPnd.setBounds(76, 20, 100, 20);

        lblValDif.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblValDif.setPreferredSize(new java.awt.Dimension(110, 15));
        panGenTotTot.add(lblValDif);
        lblValDif.setBounds(76, 40, 100, 20);

        panGenTot.add(panGenTotTot, java.awt.BorderLayout.EAST);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de Diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtNumAltUnoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumAltUnoFocusLost
        if(intbanclimod > 0)
            intbanmodcel++;
    }//GEN-LAST:event_txtNumAltUnoFocusLost

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void txtNomDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomDocFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtNomDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtNomDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtTipDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
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

    private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
        strDesLarCli=txtDesLarCli.getText();
        txtDesLarCli.selectAll();
    }//GEN-LAST:event_txtDesLarCliFocusGained

    private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
        txtDesLarCli.transferFocus();
    }//GEN-LAST:event_txtDesLarCliActionPerformed

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
        if (!txtDesLarCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtDesLarCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
                objTblModExc.removeAllRows();
                objTblModPnd.removeAllRows();
                txtMonDocExc.setText("");
                txtMonDocPnd.setText("");
            }
            else
            {
                mostrarVenConCli(2);
                if (txtCodCli.getText().equals(""))
                {
                    objTblModExc.removeAllRows();
                    objTblModPnd.removeAllRows();
                    txtMonDocExc.setText("");
                    txtMonDocPnd.setText("");                    
                }
                else
                {
                    if(banins!=0)
                    {
                        cargarDocExc();
                        cargarDocPend();
                        txtMonDocExc.setText("");
                        txtMonDocPnd.setText("");
                    }
                }
            }
        }
        else
            txtDesLarCli.setText(strDesLarCli);                
    }//GEN-LAST:event_txtDesLarCliFocusLost

/**
 *Esta funcion actualiza en tbm_cabtipdoc el ultimo numero del documento que se esta usando
 */
public void actUltCodDoc()
{
        int intUltDoc=0;
        boolean blnRes=true;
        java.sql.Connection conNumDoc;
        java.sql.Statement stmNumDoc;
        java.sql.ResultSet rstNumDoc;
        try{            
            conNumDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if(conNumDoc!=null){            
                stmNumDoc=conNumDoc.createStatement();//(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                
                String sqlDoc="";
                
                sqlDoc+="UPDATE tbm_cabtipdoc SET ne_ultdoc="+Integer.parseInt(txtNumAltUno.getText())+" ";
                sqlDoc+="WHERE co_emp="+ objParSis.getCodigoEmpresa() +" ";
                sqlDoc+=" AND co_loc=" + objParSis.getCodigoLocal() + " ";
                sqlDoc+=" AND co_tipDoc=" + Integer.parseInt(txtCodTipDoc.getText()) + " ";
                stmNumDoc.executeUpdate(sqlDoc);                
            }
            conNumDoc.close();
            conNumDoc=null;
            stmNumDoc=null;
            rstNumDoc=null;
            
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
        //return intUltDoc;
    }

/////////////////////////////////////////////////////////////////////////////////////////////////
    
    
private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
        {
            if (txtCodCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtDesLarCli.setText("");
                objTblModExc.removeAllRows();
                objTblModPnd.removeAllRows();
                txtMonDocExc.setText("");
                txtMonDocPnd.setText("");                                
            }
            else
            {
                mostrarVenConCli(1);
                if (txtCodCli.getText().equals(""))
                {
                    objTblModExc.removeAllRows();
                    objTblModPnd.removeAllRows();
                    txtMonDocExc.setText("");
                    txtMonDocPnd.setText("");                    
                }
                else
                {
                    if(banins!=0)
                    {
                        cargarDocExc();
                        cargarDocPend();
                        txtMonDocExc.setText("");
                        txtMonDocPnd.setText("");
                    }
                    
                }
            }
        }
        else
            txtCodCli.setText(strCodCli);         
}//GEN-LAST:event_txtCodCliFocusLost
    
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
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                    }
                    break;
                case 1: //Bï¿½squeda directa por "Cï¿½digo".
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
                        vcoCli.show();
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
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
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
                        vcoCli.show();
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
    


    
    
    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

 
    protected String strNatCta()
    {
            Connection conNatCta;
            Statement stmNatCta;
            ResultSet rstNatCta;
            String strNatCta="";
            try{
                conNatCta=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conNatCta!=null){
                    stmNatCta=conNatCta.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubicta";
                    strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
                    strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();        
                    rstNatCta=stmNatCta.executeQuery(strSQL);
                    
                    if (rstNatCta.next()){
                        strNatCta=rstNatCta.getString("tx_ubicta");
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
            return strNatCta;        
    }
    
    
    
    private void txtTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipDocActionPerformed
        txtTipDoc.transferFocus();
    }//GEN-LAST:event_txtTipDocActionPerformed

    private void txtCodDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDocActionPerformed
        // TODO add your handling code here:        
    }//GEN-LAST:event_txtCodDocActionPerformed

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        mostrarVenConCli(0);
        if (txtCodCli.getText().equals(""))
        {
            objTblModExc.removeAllRows();
            objTblModPnd.removeAllRows();
            txtMonDocExc.setText("");
            txtMonDocPnd.setText("");
            
        }
        else
        {
            if(bancon!=0 || banins!=0)
            {
                cargarDocExc();
                cargarDocPend();
                txtMonDocExc.setText("");
                txtMonDocPnd.setText("");
            }            
        }
    }//GEN-LAST:event_butCliActionPerformed

    
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        ///intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);

                    }
                    break;
                case 1: //Bï¿½squeda directa por "Descripciï¿½n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        ///intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                            ///intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtTipDoc.setText(strTipDoc);
                        }
                    }
                    break;
                case 2: //Bï¿½squeda directa por "Descripciï¿½n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtNomDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                        txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                        ///intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                    }
                    else
                    {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE)
                        {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));   ///txtCodTipDoc
                            txtTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtNomDoc.setText(vcoTipDoc.getValueAt(3));
                            ///intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        }
                        else
                        {
                            txtNomDoc.setText(strDesLarTipDoc);
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
    
    private void txtTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTipDocFocusLost
        //Validar el contenido de la celda sï¿½lo si ha cambiado.
        if (!txtTipDoc.getText().equalsIgnoreCase(strTipDoc))
        {
            if (txtTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtNomDoc.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtTipDoc.setText(strTipDoc);
    }//GEN-LAST:event_txtTipDocFocusLost
        
    private void butDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDocActionPerformed
        configurarVenConTipDoc();
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butDocActionPerformed
    
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
    private javax.swing.JButton butCli;
    private javax.swing.JButton butDoc;
    private javax.swing.JLabel lblAte;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblCom;
    private javax.swing.JLabel lblDif;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMonDocExc;
    private javax.swing.JLabel lblMonDocPnd;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPro;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDif;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDetCre;
    private javax.swing.JPanel panGenDetDeb;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panGenTotTot;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JScrollPane spnValExc;
    private javax.swing.JScrollPane spnValPnd;
    private javax.swing.JSplitPane sppGenDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblValExc;
    private javax.swing.JTable tblValPnd;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesLarCli;
    private javax.swing.JTextField txtMonDocExc;
    private javax.swing.JTextField txtMonDocPnd;
    private javax.swing.JTextField txtNomDoc;
    private javax.swing.JTextField txtNumAltDos;
    private javax.swing.JTextField txtNumAltUno;
    private javax.swing.JTextField txtNumCta;
    private javax.swing.JTextField txtNumPro;
    private javax.swing.JTextField txtTipDoc;
    // End of variables declaration//GEN-END:variables

    public String retNomTipDoc(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{
            if(txtCodTipDoc.getText().length()>0)
            {
                conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTipDoc!=null){
                    stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    que="";
                    que+=" select *from tbr_tipdocprg as a1, tbm_cabtipdoc as a2";
                    que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and";
                    que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    que+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    rstTipDoc=stmTipDoc.executeQuery(que);
                    if (rstTipDoc.next()){
                        auxTipDoc=rstTipDoc.getString("tx_deslar");
                    }
                }
            }
            else
                auxTipDoc="";
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
    

    protected boolean rtnDocExi()
    {
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="", strNumDoc="";
        boolean blnRes=true;
        try{
            if(txtCodTipDoc.getText().length()>0)
            {
                conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTipDoc!=null){
                    stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    que="";
                    que+=" select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc1, a1.nd_mondoc, a1.fe_doc ";
                    que+=" from tbm_cabpag as a1";
                    que+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    que+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    que+=" and a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                    que+=" and a1.ne_numdoc1=" + txtNumAltUno.getText() + "";
                    que+=" and a1.st_reg NOT IN ('E', 'I')";
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
            }
            else
                blnRes=false;
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
    
    public String retDesCorTipDoc(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxTipDoc="";        
        try{
            if(txtCodTipDoc.getText().length()>0)
            {
                conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTipDoc!=null){
                    stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    que="";
                    que+=" select *from tbr_tipdocprg as a1, tbm_cabtipdoc as a2";
                    que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and";
                    que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    que+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    rstTipDoc=stmTipDoc.executeQuery(que);
                    if (rstTipDoc.next()){
                        auxTipDoc=rstTipDoc.getString("tx_descor");
                    }
                }
            }
            else
                auxTipDoc="";
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
     *Esta funcion retorna el codigo del tipo de documento segun la empresa, local y el menu en el que se encuentra
     *el usuario
     *@return Retorna el codigo del tipo de documento como un String
     */
    public String retCodTipDoc(){
        java.sql.Connection conTipDoc;
        java.sql.Statement stmTipDoc;
        java.sql.ResultSet rstTipDoc;
        String que, auxCodTipDoc="";        
        try{
            if(txtCodTipDoc.getText().length()>0)
            {
                conTipDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if(conTipDoc!=null){
                    stmTipDoc=conTipDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    que="";
                    que+=" select *from tbr_tipdocprg as a1, tbm_cabtipdoc as a2";
                    que+=" where a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc and";
                    que+=" a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    que+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    rstTipDoc=stmTipDoc.executeQuery(que);
                    if (rstTipDoc.next()){
                        auxCodTipDoc=rstTipDoc.getString("co_tipdoc");
                    }
                }
            }
            else
                auxCodTipDoc="";
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }        
        return auxCodTipDoc;
        
    }    
    
    
    
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
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc ";
                strSQL+=", a1.ne_numDoc1, a1.ne_numDoc2, a1.co_cli, a1.tx_ruc, a1.tx_nomCli ";
                strSQL+=", a1.tx_dirCli, a1.nd_monDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg ";
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
                    
                    ///intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    txtFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc1");
                    STRNUMDOCCAB = rst.getString("ne_numDoc1");
                    txtNumAltUno.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("ne_numDoc2");
                    txtNumAltDos.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_cli");
                    txtCodCli.setText((strAux==null)?"":strAux);
                    strIdePro=rst.getString("tx_ruc");
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarCli.setText((strAux==null)?"":strAux);
                    strDirPro=rst.getString("tx_dirCli");
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
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posiciï¿½n relativa del registro.
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

    
    protected boolean consultarCabIns(){
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
                strSQL+="SELECT b4.co_emp, b4.co_tipdoc, b4.co_doc, b4.co_cta, b4.co_cli, b4.co_emp, b4.co_loc,  z1.ne_ultdoc,b4.st_reg";
                strSQL+=" from (tbm_placta AS b3 INNER JOIN (tbm_cabpag AS b4 inner join tbm_cabtipdoc as z1";
                strSQL+=" on b4.co_emp=z1.co_emp and b4.co_emp=z1.co_emp and b4.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON b3.co_emp=b4.co_emp AND b3.co_cta=b4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_detpag as b5";
                strSQL+=" ON b4.co_emp=b5.co_emp AND b4.co_loc=b5.co_loc AND b4.co_tipdoc=b5.co_tipdoc ";
                strSQL+=" AND b4.co_doc=b5.co_doc";
                strSQL+=" WHERE b4.co_emp=" + objParSis.getCodigoEmpresa() + " AND b4.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND b4.co_tipdoc=" + retCodTipDoc() + "";
                
                strAux=txtCodCli.getText();
                if(!strAux.equals(""))
                    strSQL+=" AND b4.co_cli LIKE '" + strAux.replaceAll("'", "'")  + "'";                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND b4.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";                                                                
                if (txtFecDoc.isFecha())
                    strSQL+=" AND b4.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                

                strSQL+=" ORDER BY b4.co_doc";                                                                                
                rstCab=stmCab.executeQuery(strSQL);          
      
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
                    //limpiarFrm();
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
     * Esta funciï¿½n permite consultar el registro insertado.
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
                    objTooBar.setMenSis("Se encontrï¿½ " + rstCab.getRow() + " registro");
                    objTooBar.setPosicionRelativa("1 / 1");
                    objTooBar.setEstadoRegistro("Activo");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
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
     * Esta funciï¿½n permite cargar el registro seleccionado.
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
//
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
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_cli,  a1.ne_numdoc1, a1.nd_mondoc, a1.fe_doc, a1.st_reg";
                strSQL+=" FROM tbm_cabPag AS a1";                                
                strSQL+=" LEFT OUTER JOIN (tbm_cabTipDoc AS a2";
                strSQL+=" LEFT OUTER JOIN tbr_tipdocprg AS z1";
                strSQL+=" ON a2.co_emp=z1.co_emp AND a2.co_loc=z1.co_loc AND a2.co_tipdoc=z1.co_tipdoc)";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                strSQL+=" AND a1.co_loc=" + intCodLoc + "";                                
                strSQL+=" AND z1.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a1.st_reg <> 'E' ";
                
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                strAux=txtCodCli.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_cli = " + strAux + "";
		if (txtFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux + "";
                strAux=txtNumAltUno.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc1 = " + strAux + "";
                strAux=txtNumAltDos.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc2 = " + strAux + "";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc ";
                System.out.println("cxc24: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningï¿½n registro que cumpla el criterio de bï¿½squeda especificado.");
                    //limpiarFrm();
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
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strSQL+=" SET st_reg='E'";    ////Eliminacion Logica////
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=", tx_comMod=" + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
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
            if(txtCodTipDoc.getText().length()>0)
            {
                intFilProExc=objTblModExc.getRowCount();
                for (i=0; i<intFilProExc; i++)
                {
                    strConCelExc=(objTblModExc.getValueAt(i, INT_TBL_DAT_VAL_ABO)==null)?"":objTblModExc.getValueAt(i, INT_TBL_DAT_VAL_ABO).toString();
                    dblValExc=(objUti.isNumero(strConCelExc))?Double.parseDouble(strConCelExc):0;
                    dblTotExc+=dblValExc;
                }
                //Mostrar valor Ingresado///
                ///txtMonDocExc.setText("" + objUti.redondeo(dblTotExc,objParSis.getDecimalesMostrar()));//objParSis.getDecimalesMostrar())); //ori
                txtMonDocExc.setText("" + objUti.redondeo(dblTotExc,6));   ///antes_original///
                ///txtMonDocExc.setText("" + dblTotExc);


                intFilProPnd=objTblModPnd.getRowCount();
                for (i=0; i<intFilProPnd; i++)
                {
                    strConCelPnd=(objTblModPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO)==null)?"":objTblModPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO).toString();
                    dblValPnd=(objUti.isNumero(strConCelPnd))?Double.parseDouble(strConCelPnd):0;
                    dblTotPnd+=dblValPnd;
                }
                //Mostrar valor Ingresado///
                ///txtMonDocPnd.setText("" + objUti.redondeo(dblTotPnd,objParSis.getDecimalesMostrar()));//objParSis.getDecimalesMostrar()));///ori
                txtMonDocPnd.setText("" + objUti.redondeo(dblTotPnd,6));  ///antes_original///
                ///txtMonDocPnd.setText("" + dblTotPnd);

                /*Calcular la Diferencia entre el CREDITO y DEBITO*/
                dblValDif = dblTotExc - dblTotPnd;
                lblValDif.setText(""+objUti.redondeo(dblValDif,8));
            }
            
        }
        catch (NumberFormatException e)
        {
            txtMonDocExc.setText("[ERROR]");
            txtMonDocPnd.setText("[ERROR]");
        }
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm() 
    {
        boolean blnRes=true;
        try {
            
            ////titulo de la ventana////
            this.setTitle(objParSis.getNombreMenu()+ " v1.1");
            lblTit.setText(""+objParSis.getNombreMenu());

            intCodMnu = objParSis.getCodigoMenu();

            
            /*Dario_Objeto del Tipo ZafAjuAutSis*/
            objAjuAutSis = new Librerias.ZafUtil.ZafAjuAutSis(objParSis);
            
            ///*Configurar Objeto para llamar al Reporte*////
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            configurarVenConCli();
            configurarVenConTipDoc();
            
            ////asigno el color de fondo de los campos ////
            txtTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtNomDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNumAltUno.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtMonDocExc.setBackground(objParSis.getColorCamposSistema());
            txtMonDocPnd.setBackground(objParSis.getColorCamposSistema());
            
            txtMonDocExc.setEditable(false);
            txtMonDocPnd.setEditable(false);
            
            /////////////////////////////////////////////////////////////////////////////////
            //Configurar JTable: Establecer el modelo para Tabla de Credito            
            vecDatExc=new Vector();      //Almacena los datos
            vecCabExc=new Vector(25);    //Almacena la cabeceras/////
            vecCabExc.clear();
            vecCabExc.add(INT_TBL_DAT_LIN,"");///0
            vecCabExc.add(INT_TBL_DAT_SEL,"");///1
            vecCabExc.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");///2
            vecCabExc.add(INT_TBL_DAT_TIP_DOC,"Cód.Tip.Doc.");///3
            vecCabExc.add(INT_TBL_DAT_DES_COR,"Tip.Doc.");///4
            vecCabExc.add(INT_TBL_DAT_NOM_DOC,"Tipo de documento");///5
            vecCabExc.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc");///6
            vecCabExc.add(INT_TBL_DAT_NUM_COT,"Ref.Fac.");///7
            vecCabExc.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");///8
            vecCabExc.add(INT_TBL_DAT_DIA_CRE,"Día.Cré.");///9
            vecCabExc.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");///10
            vecCabExc.add(INT_TBL_DAT_POR_RET,"Por.Ret.");///11
            vecCabExc.add(INT_TBL_DAT_MON_DOC,"Val.Doc.");///12
            vecCabExc.add(INT_TBL_DAT_VAL_PND,"Val.Pen.");///13
            vecCabExc.add(INT_TBL_DAT_VAL_PND_INI,"Valor Pend.INI.");///14
            vecCabExc.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");///15
            vecCabExc.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");///16
            vecCabExc.add(INT_TBL_DAT_VAL_ABO,"Valor Aplicar");///17
            vecCabExc.add(INT_TBL_DAT_AUX_ABO,"Abono Aux.");///18
            vecCabExc.add(INT_TBL_DAT_NAT_DOC,"Nat.Doc.");///19
            vecCabExc.add(INT_TBL_DAT_VAL_ABO_PAG,"Val.Abo.Pag.");///20
            objTblModExc=new ZafTblMod();
            objTblModExc.setHeader(vecCabExc);
            
            objTblModExc.setColumnDataType(INT_TBL_DAT_VAL_ABO, objTblModExc.INT_COL_DBL, new Integer(0),null);//new Integer(0), null); ///INT_TBL_DAT_NUM_DOC                        

            tblValExc.setModel(objTblModExc);            
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.
            tblValExc.setRowSelectionAllowed(true);
            tblValExc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
                        
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblValExc,INT_TBL_DAT_LIN);
            
            objTblPopMnu=new ZafTblPopMnu(tblValExc);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblValExc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    
            //Configurar JTable: Establecer el ancho de las columnas.
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);///0
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(20);///1
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);///2
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(20);///3
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(60);///4
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(10);///5
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(50);///6
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NUM_COT).setPreferredWidth(50);///7
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(90);///8
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(28);///9
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(90);///10
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(40);///11
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setPreferredWidth(80);///12
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);///13
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(80);///14
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(10);///15
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(10);///16
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(80);///17
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(80);///18
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(20);///19
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(80);///19

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblValExc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatHeaMouseClickedCre(evt);
                }
            });
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblValExc.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);            
            
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setResizable(false);

            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);            

//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMaxWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setMinWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setResizable(false);
            
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMaxWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setMinWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(0);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setResizable(false);
            
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMaxWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMinWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setResizable(false);
//////
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMaxWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMinWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(0);
//////            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setResizable(false);

////////////////////////////////////////////////////////////           
            vecDatPnd=new Vector();      //Almacena los datos
            vecCabPnd=new Vector(25);    //Almacena la cabeceras
            vecCabPnd.clear();
            vecCabPnd.add(INT_TBL_DAT_LIN,"");
            vecCabPnd.add(INT_TBL_DAT_SEL,"");
            vecCabPnd.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");
            vecCabPnd.add(INT_TBL_DAT_TIP_DOC,"Cód.Tip.Doc.");
            vecCabPnd.add(INT_TBL_DAT_DES_COR,"Tip.Doc.");
            vecCabPnd.add(INT_TBL_DAT_NOM_DOC,"Tipo de documento");
            vecCabPnd.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCabPnd.add(INT_TBL_DAT_NUM_COT,"Ref.Fac.");
            vecCabPnd.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCabPnd.add(INT_TBL_DAT_DIA_CRE,"Día.Cré.");
            vecCabPnd.add(INT_TBL_DAT_FEC_VEN,"Fec.Ven.");
            vecCabPnd.add(INT_TBL_DAT_POR_RET,"Por.Ret.");
            vecCabPnd.add(INT_TBL_DAT_MON_DOC,"Val.Doc.");
            vecCabPnd.add(INT_TBL_DAT_VAL_PND,"Val.Pen.");
            vecCabPnd.add(INT_TBL_DAT_VAL_PND_INI,"Valor Pend.INI");
            vecCabPnd.add(INT_TBL_DAT_COD_REG,"Cód.Reg.");
            ///vecCabPnd.add(INT_TBL_DAT_COD_LOC, "Cod. Loc.");///
            vecCabPnd.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCabPnd.add(INT_TBL_DAT_VAL_ABO,"Valor Aplicar");
            vecCabPnd.add(INT_TBL_DAT_AUX_ABO,"Abono Aux.");
            vecCabPnd.add(INT_TBL_DAT_NAT_DOC,"Nat.Doc.");
            vecCabPnd.add(INT_TBL_DAT_VAL_ABO_PAG,"Val.Abo.Pag.");

            objTblModPnd=new ZafTblMod();
            objTblModPnd.setHeader(vecCabPnd);
            
            objTblModPnd.setColumnDataType(INT_TBL_DAT_VAL_ABO, objTblModPnd.INT_COL_DBL,new Integer(0), null); //new Integer(0), null);                        

            tblValPnd.setModel(objTblModPnd);            
            
            //Configurar JTable: Establecer tipo de selecciï¿½n.//
            tblValPnd.setRowSelectionAllowed(true);
            tblValPnd.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);            
            
            //Configurar JTable: Establecer la fila de cabecera.//
            new ZafColNumerada(tblValPnd,INT_TBL_DAT_LIN);
            
            objTblPopMnu=new ZafTblPopMnu(tblValPnd);
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.//
            tblValPnd.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);                                     
            
            //Configurar JTable: Establecer el ancho de las columnas.
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(60);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(10);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NUM_COT).setPreferredWidth(50);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(90);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_DIA_CRE).setPreferredWidth(28);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_FEC_VEN).setPreferredWidth(90);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(40);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(10);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(10);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_AUX_ABO).setPreferredWidth(80);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_NAT_DOC).setPreferredWidth(20);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(80);
            
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblValPnd.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblDatHeaMouseClickedDeb(evt);
                }
            });
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblValPnd.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_REG).setResizable(false);            
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_TIP_DOC).setResizable(false);            

//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
//            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);

            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);            

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
            
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND_INI).setResizable(false);

            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMaxWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setMinWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setPreferredWidth(0);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO_PAG).setResizable(false);

            
///////////////////////////////////////////////////////////////////////////////////////////////

            
            //para hacer editable las celdas
            vecAux=new Vector();                       
            vecAux.add("" + INT_TBL_DAT_SEL);
            vecAux.add("" + INT_TBL_DAT_VAL_ABO);
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
//inicio de check
            
            //para la tabla de credito
            objTblCelEdiChkExc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChkExc.isChecked())
                    {
                        objTblModExc.setValueAt(objTblModExc.getValueAt(tblValExc.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValExc.getSelectedRow(),INT_TBL_DAT_VAL_ABO);
                        I++;
                        banclickCre++;
                    }
                    else
                    {
                        objTblModExc.setValueAt(null, tblValExc.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
                        if(intTotRegSelI > 0)
                        {
                            I = intTotRegSelI;
                        }
                        I--;
                        banclickCre--;
                        intTotRegSelI = I;
                    }
                    calcularAboTot();
//                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocExc.getText());
                }
                });

            //para la tabla de debito    
            objTblCelRenChkPnd=new ZafTblCelRenChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellRenderer(objTblCelRenChkPnd);
            objTblCelEdiChkPnd=new ZafTblCelEdiChk();
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_SEL).setCellEditor(objTblCelEdiChkPnd);            
//inicio de check 
            
            //para la tabla de credito
            objTblCelEdiChkPnd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblCelEdiChkPnd.isChecked())
                    {
                        objTblModPnd.setValueAt(objTblModPnd.getValueAt(tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_PND),tblValPnd.getSelectedRow(),INT_TBL_DAT_VAL_ABO);                        
                        J++;
                        banclickDeb++;
                    }
                    else
                    {
                        objTblModPnd.setValueAt(null, tblValPnd.getSelectedRow(), INT_TBL_DAT_VAL_ABO);
                        if(intTotRegSelJ > 0)
                        {
                            J = intTotRegSelJ;
                        }
                        J--;
                        banclickDeb--;
                        intTotRegSelJ = J;
                    }
                    calcularAboTot();
//                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocExc.getText());
                }
                });
                
//inicio de label
            objTblCelRenLblExc=new ZafTblCelRenLbl();
            objTblCelRenLblExc.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblExc.setTipoFormato(objTblCelRenLblExc.INT_FOR_NUM);
            objTblCelRenLblExc.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setCellRenderer(objTblCelRenLblExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLblExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLblExc);                
            
            objTblCelRenLblPnd=new ZafTblCelRenLbl();
            objTblCelRenLblPnd.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblPnd.setTipoFormato(objTblCelRenLblExc.INT_FOR_NUM);
            objTblCelRenLblPnd.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);                       
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_MON_DOC).setCellRenderer(objTblCelRenLblPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_PND).setCellRenderer(objTblCelRenLblPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellRenderer(objTblCelRenLblPnd);                
//fin de label
                
//inicio del txt                
            objTblCelEdiTxtExc=new ZafTblCelEdiTxt(tblValExc);
            tblValExc.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellEditor(objTblCelEdiTxtExc);                    
            objTblCelEdiTxtExc.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtExc.getText().equals(""))
                    {
                        objTblModExc.setChecked( false, tblValExc.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                        objTblModExc.setChecked(true, tblValExc.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    
       //////////////////////////////////////////////////////////////////////////////////////////////////////////
                  
                    //valido si el abono al documento es mayor que el pendiente en  credito
                    ///if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
//               if (objTooBar.getEstado()=='n')
//               {
                    int k = 0;
                    k = objTblModExc.getRowCountTrue();
                    I++;
                    
                    ///for (int i=0;i<objTblModExc.getRowCountTrue();i++) ///original///
                    for (int i=0; i<k; i++)   ///prueba///
                    {
                            if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                            {
                              ///if(i != 0)
                              ///{
                                double dblPndAux = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND), 3));
                                double dblAboApl = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO), 3));
                                double dblAboAux = Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO), 3));
                                double SumValPen = (dblPndAux+dblAboAux);
                                bancelmod++;
                                if (objTooBar.getEstado()=='n')
                                {
                                    if(dblAboApl > dblPndAux)
                                    {
                                        if(!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>dblPndAux)
                                        {
                                            mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el campo <BR><FONT COLOR=\"blue\">Valor Pend.</FONT> en la Tabla de CREDITOS</HTML>");
                                            objTblModExc.setValueAt("",i,INT_TBL_DAT_VAL_ABO);
                                        }
                                    }
                                }
                                else
                                {
                                    if (objTooBar.getEstado()=='m')
                                    {
                                        if(dblAboApl > SumValPen)
                                        {
                                            if(!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>SumValPen)
                                            {
                                                mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el <BR><FONT COLOR=\"blue\">Valor Pendiente Anterior.</FONT> en la Tabla de CREDITOS</HTML>");
                                                objTblModExc.setValueAt("",i,INT_TBL_DAT_VAL_ABO);
                                            }
                                        }
                                    }
                                }
                            }                            
                    }
//               }
//               else
       ////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
                    calcularAboTot();
//                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocExc.getText());
                            }
                });                
                
            objTblCelEdiTxtPnd=new ZafTblCelEdiTxt(tblValPnd);
            tblValPnd.getColumnModel().getColumn(INT_TBL_DAT_VAL_ABO).setCellEditor(objTblCelEdiTxtPnd);                    
            objTblCelEdiTxtPnd.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtPnd.getText().equals(""))
                    {
                        objTblModPnd.setChecked( false, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    else
                    {
                        objTblModPnd.setChecked(true, tblValPnd.getSelectedRow(), INT_TBL_DAT_SEL);
                    }
                    
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
                    //valido si el abono al documento es mayor que el pendiente en  debito
//               if (objTooBar.getEstado()=='n')
//               {
                    int l = 0;
                    l = objTblModPnd.getRowCountTrue();
                    J++;
                    for (int i=0; i<l; i++)
                    {
                            if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                            {
                                double dblPndAux = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND), 3));
                                double dblAboApl = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO), 3));
                                double dblAboAux = Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO), 3));
                                double SumValPen = (dblPndAux+dblAboAux);
                                if (objTooBar.getEstado()=='n')
                                {
                                    if(dblAboApl>dblPndAux)
                                    {
                                        if(!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>dblPndAux)
                                        {
                                            mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el campo <BR><FONT COLOR=\"blue\">Valor Pend.</FONT> en la Tabla de DEBITOS</HTML>");
                                            objTblModPnd.setValueAt("",i,INT_TBL_DAT_VAL_ABO);
                                        }
                                    }
                                }
                                else
                                {
                                    if (objTooBar.getEstado()=='m')
                                    {
                                        if(dblAboApl>SumValPen)
                                        {
                                            if(!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals("") && dblAboApl>SumValPen)
                                            {
                                                mostrarMsgInf("<HTML>El campo de <FONT COLOR=\"blue\">Valor Aplicar </FONT> no debe ser mayor que el <BR><FONT COLOR=\"blue\">Valor Pendiente Anterior </FONT> en la Tabla de DEBITOS</HTML>");
                                                objTblModPnd.setValueAt("",i,INT_TBL_DAT_VAL_ABO);
                                            }
                                        }
                                    }
                                }

                            }
                    }
//               }
//               else
        //////////////////////////////////////////////////////////////////////////////////////////////////////////
                    
                    
                    calcularAboTot();
//                    objAsiDia.generarDiario(txtCodTipDoc.getText(), txtMonDocExc.getText(),txtMonDocExc.getText());
                            }
                });
                
          /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter()
	    {
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) 
                {   
                    calcularAboTot();
                }
            });
       /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
                objTblModLisExc=new ZafCxC24.ZafTblModLis();                
                objTblModExc.addTableModelListener(objTblModLisExc);

                objTblModLisPnd=new ZafCxC24.ZafTblModLis();
                objTblModPnd.addTableModelListener(objTblModLisPnd);
                //setEditable(true);    
            
                
            /*Dario_---Para Generar Ajutes Automaticos---*/
            objAjuAutSis.mostrarTipDocPre();
            //////////////////////////////////////
            
                
        }
        catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        //////////////////////////
    }
    
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica los cheques en el JTable de Depositos.
     */
    private void tblDatHeaMouseClickedCre(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        double dblValPnd = 0.00;
        
        try
        {
            intNumFil=objTblModExc.getRowCountTrue();
            banclickCre=0;
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.  //INT_TBL_DAT_PRV_SEL_REVISA, intBanSelRev
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblValExc.columnAtPoint(evt.getPoint())==INT_TBL_DAT_SEL)
            {
                banclickCre=0;
                I=0;
                
                if (blnMarTodChkTblDatCre)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        banclickCre++;
                        I++;
                        
                        objTblModExc.setChecked(true, i, INT_TBL_DAT_SEL);
                        
                        dblValPnd = Math.abs(objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND)));
                        if(dblValPnd > 0)                        
                            objTblModExc.setValueAt(new Double(dblValPnd),i,INT_TBL_DAT_VAL_ABO);
                        else
                            objTblModExc.setValueAt(null,i,INT_TBL_DAT_VAL_ABO);
                    }
                    blnMarTodChkTblDatCre=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        banclickCre--;
                        I--;
                        
                        objTblModExc.setChecked(false, i, INT_TBL_DAT_SEL);
                        objTblModExc.setValueAt(null,i,INT_TBL_DAT_VAL_ABO);
                    }
                    blnMarTodChkTblDatCre=true;
                }
                calcularAboTot();
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica los cheques en el JTable de Depositos.
     */
    private void tblDatHeaMouseClickedDeb(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        double dblValPnd = 0.00;
        
        try
        {
            intNumFil=objTblModPnd.getRowCountTrue();
            banclickDeb=0;
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.  //INT_TBL_DAT_PRV_SEL_REVISA, intBanSelRev
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblValPnd.columnAtPoint(evt.getPoint())==INT_TBL_DAT_SEL)
            {
                banclickDeb=0;
                J=0;
                
                ///INT_TBL_DAT_SEL, INT_TBL_DAT_VAL_PND, INT_TBL_DAT_VAL_ABO --- banclickCre, banclickDeb, tblValExc, tblValPnd///
                
                if (blnMarTodChkTblDatDeb)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        banclickDeb++;
                        J++;
                        
                        objTblModPnd.setChecked(true, i, INT_TBL_DAT_SEL);
                        
                        dblValPnd = Math.abs(objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND)));
                        if(dblValPnd > 0)
                            objTblModPnd.setValueAt(new Double(dblValPnd),i,INT_TBL_DAT_VAL_ABO);
                        else
                            objTblModPnd.setValueAt(null,i,INT_TBL_DAT_VAL_ABO);
                    }
                    blnMarTodChkTblDatDeb=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        banclickDeb--;
                        J--;
                        objTblModPnd.setChecked(false, i, INT_TBL_DAT_SEL);
                        objTblModPnd.setValueAt(null,i,INT_TBL_DAT_VAL_ABO);
                    }
                    blnMarTodChkTblDatDeb=true;
                }
                calcularAboTot();
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }
    
    
    /**
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
     * mostrar los "Clientes/Proveedores".
     */
    private boolean configurarVenConCli()
    {
        boolean blnRes=true;
        int intCodEmp, intCodEmpGrp, intCodMnu;
        
        try
        {
            
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodEmpGrp = objParSis.getCodigoEmpresaGrupo();
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
            
            //Armar la sentencia SQL.            
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario()))
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else
            {
                strSQL="";
                strSQL+="SELECT a1.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM  tbr_cliloc AS a1";
                strSQL+=" INNER JOIN tbm_cli AS a2 ON (a1.co_emp=a2.co_emp and a1.co_cli=a2.co_cli) ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.st_reg='A'";
                strSQL+=" ORDER BY a2.tx_nom";
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
     * Esta funciï¿½n configura la "Ventana de consulta" que serï¿½ utilizada para
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
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
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
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a2.co_usr";
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
        txtNumAltUno.setText(""+(n+1));
    }
    
    private void cargarCabTipoDoc(int TipoDoc)
    {
        objTipDoc.cargarTipoDoc(TipoDoc);
        txtCodTipDoc.setText(""+objTipDoc.getco_tipdoc());
        txtTipDoc.setText(objTipDoc.gettx_descor());
        txtNomDoc.setText(objTipDoc.gettx_deslar());
    }
   
/////////////////////////////////////////////////////////////////////////////////////////////////////////        
    //AQUI VAN LOS RENDERS Y EDITORES
    
    //////////////////////////////////////////////////////////////////////////////////////////
    
    /*para alterar el modelo de datos
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
                case INT_TBL_DAT_MON_DOC:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_DAT_VAL_PND:
                    intAliHor=javax.swing.JLabel.RIGHT;
                    strVal=objUti.formatearNumero(strVal,strFor,true);
                    break;
                case INT_TBL_DAT_VAL_ABO:
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
    /*para adicionar el tooltip
     **/
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {
        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol=tblValPnd.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_SEL:
                    strMsg="Seleccione para escoger el valor a aplicar";
                    break;
                case INT_TBL_DAT_DES_COR:
                    strMsg="Tipo de documento";
                    break;
                case INT_TBL_DAT_NOM_DOC:
                    strMsg="Nombre del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Nï¿½mero del documento";
                    break;
                case INT_TBL_DAT_NUM_COT:
                    strMsg="Nï¿½mero del documento de referencia";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DIA_CRE:
                    strMsg="Nï¿½mero de dï¿½as de crï¿½dito del documento";
                    break;
                case INT_TBL_DAT_FEC_VEN:
                    strMsg="Fecha de vencimiento del documento";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg="Porcentaje de retenciï¿½n";
                    break;
                case INT_TBL_DAT_MON_DOC:
                    strMsg="Valor total a pagar especificado en el documento";
                    break;
                case INT_TBL_DAT_VAL_PND:
                    strMsg="Valor de exceso cancelado por el cliente";
                    break;
            }
            tblValPnd.getTableHeader().setToolTipText(strMsg);
        }
    }

 /////////////////////////////////////////////////////////////////////////
 //PARA LA BARRA DE HERRAMIENTAS
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtTipDoc.setText("");
            txtNomDoc.setText("");
            txtCodCli.setText("");
            txtDesLarCli.setText("");
            txtCodDoc.setText("");
            txtFecDoc.setText("");           
            txtNumAltUno.setText("");
            txtNumAltDos.setText("");
            txaObs1.setText("");
            txaObs2.setText("");            
            txtNumCta.setText("");
            txtNumPro.setText("");
            txtCodTipDoc.setText("");
            txtMonDocExc.setText("");
            txtMonDocPnd.setText("");
            lblValDif.setText("");
//            objAsiDia.inicializar();
            objTblModExc.removeAllRows();  
            objTblModPnd.removeAllRows();  
            dblMonDoc=0.00;
            intbanmodcel=0;
            intBanDocExi=0;
            intbanclimod=0;
            p=0;
            ///objTooBar.setEstadoRegistro("");
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
            if(strMod.equals("1") || strMod.equals("3"))
            {
                arlParEmp=objParSis.getValoresParametroTbrParEmp(codigoEmpresa, 4);
            }
            else
            {
                arlParEmp=objParSis.getValoresParametroTbrParEmp(codigoEmpresa, 5);
            }
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
                        break;
                    case 2:                        
                        blnRes=true;
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

    //PARA CXC (INGRESO), aqui intSig=1, los signos se manejan asi:
    /**Esta funcion carga los datos a favor del cliente en la tabla de credito, estos son N/C y DEVVEN con su repectivo signo
     *sirve para cruce de documentos con los clientes, aqui una tabla de los doc. que intervienen y 
     *sus signos<br> 
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR>
     *          <TD><I>Cod.Tip.Doc.</I></TD>
     *          <TD><I>Des.Tip.Doc.</I></TD>
     *          <TD><I>Nat.Doc.</I></TD>
     *          <TD><I>Sig.Mon.</I></TD>
     *          <TD><I>Sig.Abo.</I></TD>
     *     </TR>
     *     <TR>
     *          <TD><I>1</I></TD>
     *          <TD><I>FACVEN</I></TD>
     *          <TD><I>E</I></TD>
     *          <TD><I>-</I></TD>
     *          <TD><I>+</I></TD>
     *     </TR>
     *     <TR>
     *          <TD><I>28</I></TD>
     *          <TD><I>N/C</I></TD>
     *          <TD><I>I</I></TD>
     *          <TD><I>+</I></TD>
     *          <TD><I>-</I></TD>
     *     </TR>
     *     <TR>
     *          <TD><I>3</I></TD>
     *          <TD><I>DEVVEN</I></TD>
     *          <TD><I>I</I></TD>
     *          <TD><I>+</I></TD>
     *          <TD><I>-</I></TD>
     *     </TR>
     * </TABLE>
     * </CENTER>
     * </br>
     */
    private boolean cargarDocExc()
    {
        /////esto para CXC-credito---Ing---///
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        double dblValPenDoc=0.00;
        double dblValPenDocAux=0.00;
        try
        {
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                ///intCodMnu=799----para cruce de documentos del mismo cliente---
                ///intCodMnu=818----para cruce de documentos del mismo proveedor---
                /////Armar la sentencia SQL.//////
                strSQL="";
                strSQL+=" SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot, a1.fe_doc, a2.ne_diaCre, a2.fe_ven,";
                strSQL+=" (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, (a2.mo_pag+a2.nd_abo) AS valPndAux, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
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
                strSQL+=" AND a1.co_cli = " + txtCodCli.getText();
                ///intCodMnu=799----para cruce de documentos del mismo cliente---
                ///CREDITOS_CXC_CLIENTES///
                if(intCodMnu == 799)
                {
                    strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                    strSQL+=" AND (a2.mo_pag + a2.nd_abo)>0";
                }
                else///CREDITOS_CXP_PROVEEDORES///
                {
                    strSQL+=" AND a3.ne_mod in (0,2,4)";
                    strSQL+=" AND (a2.mo_pag + a2.nd_abo)<0";
                }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')";
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatExc.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {
                    vecRegExc=new Vector();
                    vecRegExc.add(INT_TBL_DAT_LIN, "");///0
                    vecRegExc.add(INT_TBL_DAT_SEL, "");///1
                    vecRegExc.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));///2
                    vecRegExc.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));///3
                    vecRegExc.add(INT_TBL_DAT_DES_COR, rst.getString("tx_descor"));///4
                    vecRegExc.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));///5
                    vecRegExc.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));///6
                    vecRegExc.add(INT_TBL_DAT_NUM_COT, rst.getString("ne_numcot"));///7
                    vecRegExc.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///8
                    vecRegExc.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));///9
                    vecRegExc.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///10
                    vecRegExc.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));///11
                    vecRegExc.add(INT_TBL_DAT_MON_DOC, ""+rst.getDouble("monPag"));///12
                    vecRegExc.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));///13
                    dblValPenDoc=rst.getDouble("valPnd");
                    VAL_DOC_EXC=dblValPenDoc;
                    dblValPenDocAux=rst.getDouble("valPndAux");
                    VAL_DOC_EXC_AUX=dblValPenDocAux;
                    vecRegExc.add(INT_TBL_DAT_VAL_PND_INI, ""+rst.getDouble("valPndAux"));///14
                    vecRegExc.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));///15
                    vecRegExc.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));///16
                    vecRegExc.add(INT_TBL_DAT_VAL_ABO, "");///17
                    vecRegExc.add(INT_TBL_DAT_AUX_ABO, "");///18
                    vecRegExc.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natDoc"));///19
                    vecRegExc.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("Abono"));///20
                    vecDatExc.add(vecRegExc);                                                                                                                                                               
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModExc.setData(vecDatExc);
                tblValExc.setModel(objTblModExc);
                vecDatExc.clear();
                objTooBar.setMenSis("Listo");
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


    //PARA PENDIENTES
    /**
     *
     */
    private boolean cargarDocPend()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        double dblValPenDoc=0.00;
        double dblValPenDocAux=0.00;
        try
        {
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                ///intCodMnu=799----para cruce de documentos del mismo cliente---
                ///intCodMnu=818----para cruce de documentos del mismo proveedor---
                /////Armar la sentencia SQL.////
                strSQL="";
                strSQL+=" (";
                strSQL+=" SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc,a1.ne_numcot, a1.fe_doc, a2.ne_diaCre, a2.fe_ven, ";
                strSQL+=" (a2.mo_pag) as monPag, a2.nd_abo as Abono, abs(a2.mo_pag+a2.nd_abo) AS valPnd, (a2.mo_pag+a2.nd_abo) AS valPndAux, a2.nd_porret, a2.nd_abo, a3.tx_natDoc";
                strSQL+=" FROM tbm_cabmovinv AS a1";
                strSQL+=" INNER JOIN tbm_pagmovinv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" INNER JOIN tbm_cabtipdoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
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
                strSQL+=" AND a1.co_cli=" + txtCodCli.getText(); 
                ///intCodMnu=799----para cruce de documentos del mismo cliente---
                ///CREDITOS_CXC_CLIENTES///
                if(intCodMnu == 799)
                {
                    strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                    strSQL+=" AND (a2.mo_pag + a2.nd_abo)<0";
                }
                else///CREDITOS_CXP_PROVEEDORES///
                {
                    strSQL+=" AND a3.ne_mod in (0,2,4)";
                    strSQL+=" AND (a2.mo_pag + a2.nd_abo)>0";
                }
                strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')"; 
                strSQL+=" AND a2.st_reg IN ('A','C')";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc";
                strSQL+=" )";
                strSQL+=" UNION ALL ";
                strSQL+=" (";
                strSQL+=" SELECT a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg,";
                strSQL+=" a1.ne_numDoc1 as ne_numDoc,a1.ne_numdoc2 as ne_numcot,";
                strSQL+=" a1.fe_doc, 0 as ne_diaCre, a1.fe_doc as fe_ven, (a1.nd_mondoc) as monPag, 0 as Abono, ";
                strSQL+=" abs(a1.nd_mondoc+sum(a2.nd_abo)) AS valPnd, (a1.nd_mondoc+sum(a2.nd_abo)) AS valPndAux, 0 as nd_porret,";
                strSQL+=" sum(a2.nd_abo) as nd_abo, a3.tx_natDoc";
                strSQL+=" FROM";
                strSQL+=" tbm_cabTipDoc AS a3 inner join";
                strSQL+=" (";
                strSQL+=" tbm_cabpag AS a1 left outer join tbm_detpag AS a2";
                strSQL+=" on a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" )";
                strSQL+=" on a3.co_emp=a1.co_emp and a3.co_loc=a1.co_loc AND a3.co_tipDoc=a1.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                ///strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";   ///se inactivo para mostrar documentos de todos los locales///
                strSQL+=" AND a1.co_cli=" + txtCodCli.getText() + "";
                ///intCodMnu=799----para cruce de documentos del mismo cliente---
                ///CREDITOS_CXC_CLIENTES///
                if(intCodMnu == 799)
                {
                    strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                }
                else///CREDITOS_CXP_PROVEEDORES///
                {
                    strSQL+=" AND a3.ne_mod in (0,2,4)";
                }
                strSQL+=" AND a1.st_reg IN ('A') AND a3.tx_natdoc NOT IN ('A') ";
                strSQL+=" and ((nd_abo is null) or (nd_abo=0))";
                strSQL+=" group by a1.co_loc, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.co_doc, a2.co_reg, a1.ne_numDoc1,a1.ne_numdoc2,";
                strSQL+=" a1.fe_doc, ne_diaCre, fe_ven, a1.nd_mondoc, a2.nd_abo,nd_porret, a3.tx_natDoc";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc, a2.co_reg, a1.ne_numDoc1";
                strSQL+=" )";
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDatPnd.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next())
                {                    
                    vecRegPnd=new Vector();
                    vecRegPnd.add(INT_TBL_DAT_LIN, "");///0
                    vecRegPnd.add(INT_TBL_DAT_SEL, "");///1
                    vecRegPnd.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));///2
                    vecRegPnd.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));///3
                    vecRegPnd.add(INT_TBL_DAT_DES_COR, rst.getString("tx_desCor"));///4
                    vecRegPnd.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));///5
                    vecRegPnd.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));///6
                    vecRegPnd.add(INT_TBL_DAT_NUM_COT, rst.getString("ne_numcot"));///7
                    vecRegPnd.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///8
                    vecRegPnd.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));///9
                    vecRegPnd.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///10
                    vecRegPnd.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));///11
                    vecRegPnd.add(INT_TBL_DAT_MON_DOC, ""+rst.getDouble("monPag"));///12
                    vecRegPnd.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));///13
                    
                    dblValPenDoc=rst.getDouble("valPnd");
                    VAL_DOC_PEN=dblValPenDoc;

                    dblValPenDocAux=rst.getDouble("valPndAux");
                    VAL_DOC_PEN_AUX=dblValPenDocAux;
                    
                    vecRegPnd.add(INT_TBL_DAT_VAL_PND_INI, ""+rst.getDouble("valPndAux"));///14
                    vecRegPnd.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));///15
                    vecRegPnd.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));///16
                    vecRegPnd.add(INT_TBL_DAT_VAL_ABO, "");///17
                    vecRegPnd.add(INT_TBL_DAT_AUX_ABO, "");///18
                    vecRegPnd.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natdoc"));///19
                    vecRegPnd.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("Abono"));///20
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
                vecDatPnd.clear();
                objTooBar.setMenSis("Listo");
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
    
    
    private boolean cargarDetRegExc()
    {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        //double dblValPnd=0.00;
        try
        {
            if (!txtCodCli.getText().equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        //Para el modo "Modificar" se muestra: documentos pendientes + documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT a1.co_loc, a4.co_loc as locpag, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc,a1.ne_numcot, a1.fe_doc, a2.ne_diaCre,a2.fe_ven, a2.nd_porret";
                        strSQL+=" , (a2.mo_pag) as monDoc, a2.co_reg, a2.nd_abo, a1.co_doc as codDoc, abs(a2.mo_pag+a2.nd_abo) AS valPnd, (a2.mo_pag+a2.nd_abo) AS valPndAux, ";
                        strSQL+=" (a4.nd_abo) as aboDoc, a3.tx_natdoc";    
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  ";

                        strSQL+=" LEFT OUTER JOIN ( ";
                        strSQL+=" SELECT x1.co_emp,x1.co_loc, x1.co_tipdoc, x2.co_reg, x4.nd_abo, x2.co_doc ";
                        strSQL+=" FROM tbm_cabMovInv AS x1    ";
                        strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS x2 ON (x1.co_emp=x2.co_emp AND x1.co_loc=x2.co_loc AND x1.co_tipDoc=x2.co_tipDoc AND x1.co_doc=x2.co_doc) ";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS x3 ON (x1.co_emp=x3.co_emp AND x1.co_loc=x3.co_loc AND x1.co_tipDoc=x3.co_tipDoc)"; 
                        strSQL+=" LEFT OUTER JOIN tbm_detPag AS x4 ON (x2.co_emp=x4.co_emp AND x2.co_loc=x4.co_locPag AND x2.co_tipDoc=x4.co_tipDocPag AND x2.co_doc=x4.co_docPag AND x2.co_reg=x4.co_regPag";
                        strSQL+=" AND x4.co_emp="+ rstCab.getString("co_emp") + ""; ///original///
                        ///strSQL+=" AND x4.co_emp="+ intCodEmp + "";
                        ///strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND x4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND x4.co_doc=" + rstCab.getString("co_doc") + ")";
                        strSQL+=" WHERE x1.co_emp=" + rstCab.getString("co_emp") + "";
                        ///strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND x1.co_cli=" + txtCodCli.getText() + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND x3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (x4.nd_abo)<0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND x3.ne_mod in (0,2,4)";
                            strSQL+=" AND (x4.nd_abo)>0";
                        }
                        strSQL+=" ORDER BY x1.co_emp, x1.co_loc, x1.co_tipDoc, x1.co_doc, x2.co_reg ";
                        strSQL+=" ) as a4 ON( ";
                        strSQL+=" a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc  AND a2.co_reg=a4.co_reg AND a1.co_doc=a4.co_doc AND a1.co_tipdoc=a4.co_tipdoc ";
                        strSQL+=" ) ";
                        strSQL+=" where a1.co_emp="+ rstCab.getString("co_emp") + "";
                        ///strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";  ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a1.co_cli=" + txtCodCli.getText() + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (a4.nd_abo)<0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND a3.ne_mod in (0,2,4)";
                            strSQL+=" AND (a4.nd_abo)>0";
                        }
                        strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')  ";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc ";
                    }
                    else
                    {
                        //Para los demás modos se muestra: sólo los documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT a1.co_loc, a4.co_locpag as locpag, a4.co_doc,a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc,a1.ne_numcot, a1.fe_doc, a2.ne_diacre, a2.fe_ven, a2.nd_porret, (a2.mo_pag) as monDoc, ";
                        strSQL+=" a2.co_reg, (a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, (a2.mo_pag+a2.nd_abo) as valPndAux, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc) ";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag) ";                        
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (a4.nd_abo)<0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND a3.ne_mod in (0,2,4)";
                            strSQL+=" AND (a4.nd_abo)>0";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg";
                    }
                    System.out.println("detalle 1: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    vecDatExc.clear();

                    
                    for(int i=0;rst.next();i++)
                    {
                        vecRegExc=new Vector();
                        vecRegExc.add(INT_TBL_DAT_LIN, "");///0
                        vecRegExc.add(INT_TBL_DAT_SEL, "");///1
                        vecRegExc.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));///2
                        vecRegExc.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));///3
                        vecRegExc.add(INT_TBL_DAT_DES_COR, rst.getString("tx_descor"));///4
                        vecRegExc.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));///5
                        vecRegExc.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));///6
                        vecRegExc.add(INT_TBL_DAT_NUM_COT, rst.getString("ne_numcot"));///7
                        vecRegExc.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///8
                        vecRegExc.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));///9
                        vecRegExc.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///10
                        vecRegExc.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));///11
                        
                        double dblValDoc=Double.parseDouble(""+rst.getString("monDoc"));
                        
                        vecRegExc.add(INT_TBL_DAT_MON_DOC, ""+dblValDoc);///12
                        vecRegExc.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));///13
                        vecRegExc.add(INT_TBL_DAT_VAL_PND_INI, ""+VAL_DOC_EXC_AUX);///14

                        double dblAuxAbo=rst.getString("aboDoc")==null?0:rst.getDouble("aboDoc");

                        //double dblAux=dblAuxAbo*(-1);
                        vecRegExc.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));///15
                        vecRegExc.add(INT_TBL_DAT_COD_DOC, rst.getString("codDoc"));///16
                        vecRegExc.add(INT_TBL_DAT_VAL_ABO, ""+ dblAuxAbo);///17
                        vecRegExc.add(INT_TBL_DAT_AUX_ABO, ""+ dblAuxAbo);///18
                        vecRegExc.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natdoc"));///19
                        vecRegExc.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("nd_abo"));///19
                        
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
                    //Asignar vectores al modelo.
                    
                    objTblModExc.setData(vecDatExc);
                    tblValExc.setModel(objTblModExc);
                    intTotRegSelI = 0;
                    intTotRegSelI = tblValExc.getRowCount();
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
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        try
        {
            vecDocSinPnd.clear();            
            if (!txtCodCli.getText().equals(""))
            {
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null)
                {
                    stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    intCodEmp=objParSis.getCodigoEmpresa();
                    intCodLoc=objParSis.getCodigoLocal();
                    //Armar la sentencia SQL.
                    if (objTooBar.getEstado()=='x' || objTooBar.getEstado()=='m')
                    {
                        //Para el modo "Modificar" se muestra: documentos pendientes + documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT a1.co_loc, a4.co_loc as locpag, a1.co_tipDoc, a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc,a1.ne_numcot, a1.fe_doc, a2.ne_diaCre,a2.fe_ven, a2.nd_porret";
                        strSQL+=" , (a2.mo_pag) as monDoc, a2.co_reg, a2.nd_abo, a1.co_doc as codDoc, abs(a2.mo_pag+a2.nd_abo) AS valPnd, (a2.mo_pag+a2.nd_abo) AS valPndAux, ";
                        strSQL+=" (a4.nd_abo) as aboDoc, a3.tx_natdoc ";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) ";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)  ";
                        strSQL+=" LEFT OUTER JOIN ( ";
                        strSQL+=" SELECT x1.co_emp, x1.co_loc, x1.co_tipdoc, x2.co_reg, x4.nd_abo, x2.co_doc, x4.co_tipdoccon, x3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS x1    ";
                        strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS x2 ON (x1.co_emp=x2.co_emp AND x1.co_loc=x2.co_loc AND x1.co_tipDoc=x2.co_tipDoc AND x1.co_doc=x2.co_doc)";
                        strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS x3 ON (x1.co_emp=x3.co_emp AND x1.co_loc=x3.co_loc AND x1.co_tipDoc=x3.co_tipDoc)"; 
                        strSQL+=" LEFT OUTER JOIN tbm_detPag AS x4 ON (x2.co_emp=x4.co_emp AND x2.co_loc=x4.co_locPag AND x2.co_tipDoc=x4.co_tipDocPag AND x2.co_doc=x4.co_docPag AND x2.co_reg=x4.co_regPag";
                        strSQL+=" AND x4.co_emp="+ rstCab.getString("co_emp") + "";
                        ///strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND x4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND x4.co_doc=" + rstCab.getString("co_doc") + ")";
                        strSQL+=" WHERE x1.co_emp=" + rstCab.getString("co_emp") + "";
                        ///strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND x1.co_cli=" + txtCodCli.getText() + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND x3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (x4.nd_abo)>0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND x3.ne_mod in (0,2,4)";
                            strSQL+=" AND (x4.nd_abo)<0";

                        }
                        strSQL+=" ORDER BY x1.co_emp, x1.co_loc, x1.co_tipDoc, x1.co_doc, x2.co_reg ";
                        strSQL+=" ) as a4 ON( ";
                        strSQL+=" a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc  AND a2.co_reg=a4.co_reg AND a1.co_doc=a4.co_doc AND a1.co_tipdoc=a4.co_tipdoc ";
                        strSQL+=" ) ";
                        strSQL+=" where a1.co_emp="+ rstCab.getString("co_emp") + "";
                        ///strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a1.co_cli=" + txtCodCli.getText() + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (a4.nd_abo)>0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND a3.ne_mod in (0,2,4)";
                            strSQL+=" AND (a4.nd_abo)<0";
                        }
                        strSQL+=" AND a1.st_reg IN ('A','C', 'R', 'F')  ";
                        strSQL+=" AND a2.st_reg IN ('A','C')";
                        strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.ne_numDoc ";
                    }
                    else
                    {
                        //Para los demï¿½s modos se muestra: sï¿½lo los documentos pagados.
                        strSQL="";
                        strSQL+=" SELECT a1.co_loc, a4.co_locpag as locpag, a4.co_doc, a2.co_tipdoc, a3.tx_descor, a3.tx_deslar, a1.ne_numdoc,a1.ne_numcot, a1.fe_doc, a2.ne_diacre, a2.fe_ven, a2.nd_porret, (a2.mo_pag) as monDoc, ";
                        strSQL+=" a2.co_reg, (a4.nd_abo) as aboDoc, a2.co_loc, a2.co_doc as codDoc, a2.nd_abo, abs(a2.mo_pag+a2.nd_abo) as valPnd, (a2.mo_pag+a2.nd_abo) as valPndAux, a3.tx_natdoc";
                        strSQL+=" FROM tbm_cabMovInv AS a1 ";
                        strSQL+=" INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                        strSQL+=" INNER JOIN tbm_cabTipDoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                        strSQL+=" INNER JOIN tbm_detPag AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_locPag AND a2.co_tipDoc=a4.co_tipDocPag AND a2.co_doc=a4.co_docPag AND a2.co_reg=a4.co_regPag)";
                        strSQL+=" WHERE a4.co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND a4.co_loc=" + rstCab.getString("co_loc") + "";   ///se inactivo para mostrar documentos de todos los locales///
                        strSQL+=" AND a4.co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND a4.co_doc=" + rstCab.getString("co_doc") + "";
                        ///intCodMnu=799----para cruce de documentos del mismo cliente---
                        ///CREDITOS_CXC_CLIENTES///
                        if(intCodMnu == 799)
                        {
                            strSQL+=" AND a3.ne_mod in (0,1,3,5)";
                            strSQL+=" AND (a4.nd_abo)>0";
                        }
                        else///CREDITOS_CXP_PROVEEDORES///
                        {
                            strSQL+=" AND a3.ne_mod in (0,2,4)";
                            strSQL+=" AND (a4.nd_abo)<0";
                        }
                        strSQL+=" ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_reg"; 
                    }
                    System.out.println("detalle 2: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    vecDatPnd.clear();
                    for(int i=0;rst.next();i++)
                    {
                        vecRegPnd=new Vector();
                        vecRegPnd.add(INT_TBL_DAT_LIN, "");///0
                        vecRegPnd.add(INT_TBL_DAT_SEL, "");///1
                        vecRegPnd.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));///2
                        vecRegPnd.add(INT_TBL_DAT_TIP_DOC, rst.getString("co_tipdoc"));///3
                        vecRegPnd.add(INT_TBL_DAT_DES_COR, rst.getString("tx_descor"));///4
                        vecRegPnd.add(INT_TBL_DAT_NOM_DOC, rst.getString("tx_deslar"));///5
                        vecRegPnd.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numdoc"));///6
                        vecRegPnd.add(INT_TBL_DAT_NUM_COT, rst.getString("ne_numcot"));///7
                        vecRegPnd.add(INT_TBL_DAT_FEC_DOC, objUti.formatearFecha(rst.getDate("fe_doc"), "dd-MM-yyyy"));///8
                        vecRegPnd.add(INT_TBL_DAT_DIA_CRE, rst.getString("ne_diacre"));///9
                        vecRegPnd.add(INT_TBL_DAT_FEC_VEN, objUti.formatearFecha(rst.getDate("fe_ven"), "dd-MM-yyyy"));///10
                        vecRegPnd.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porret"));///11
                        
                        double dblValDoc=Double.parseDouble(""+rst.getString("monDoc"));
                        
                        vecRegPnd.add(INT_TBL_DAT_MON_DOC, ""+dblValDoc);///12
                        vecRegPnd.add(INT_TBL_DAT_VAL_PND, ""+rst.getDouble("valPnd"));///13
                        vecRegPnd.add(INT_TBL_DAT_VAL_PND_INI, ""+VAL_DOC_PEN_AUX);///14

                        double dblAuxAbo=rst.getString("aboDoc")==null?0:rst.getDouble("aboDoc");
                        
                        vecRegPnd.add(INT_TBL_DAT_COD_REG, rst.getString("co_reg"));///15
                        vecRegPnd.add(INT_TBL_DAT_COD_DOC, rst.getString("codDoc"));///16
                        
                        System.out.println(rst.getString("co_loc")+"-"+rst.getString("co_tipdoc")+"-"+rst.getString("codDoc"));
                        if(Double.parseDouble((String)vecRegPnd.get(INT_TBL_DAT_VAL_PND))==0D){
                             vecDocSinPnd.add(objParSis.getCodigoEmpresa()+"-"+ rst.getString("co_loc")+"-"+rst.getString("co_tipdoc")+"-"+rst.getString("codDoc")+"-"+rst.getString("ne_numdoc"));
                        }
                        vecRegPnd.add(INT_TBL_DAT_VAL_ABO, ""+ dblAuxAbo);///17
                        vecRegPnd.add(INT_TBL_DAT_AUX_ABO, ""+ dblAuxAbo);///18
                        vecRegPnd.add(INT_TBL_DAT_NAT_DOC, rst.getString("tx_natdoc"));///19
                        vecRegPnd.add(INT_TBL_DAT_VAL_ABO_PAG, rst.getString("nd_abo"));///20
                        
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
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'm': //Modificar
                        break;
                    case 'e': //Eliminar
                        break;
                    case 'n': //insertar
//                        objTooBar.insertar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                break;
            case 2: //CANCEL_OPTION
                break;
        }
    //nuevos metodos del toolbar
                    
    }   

    private boolean isRegPro_v01()
    {
        boolean blnRes=true;
        strAux="ï¿½Desea guardar los cambios efectuados a ï¿½ste registro?\n";
        strAux+="Si no guarda los cambios perderï¿½ toda la informaciï¿½n que no haya guardado.";
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
    
    

    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
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
                        blnRes=objTooBar.beforeInsertar();
                        if (blnRes)
                            blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.beforeModificar();
                        if (blnRes)
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


    /**
     *Esta funcion valida si los campos obligatorios han sido completados correctamente 
     *@return True si los campos obligatorios se llenaron correctamente.
     */
    private boolean isCamVal()
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
                    intUltNumDocAlt = intNumAltDoc;
                    
                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumAltUno.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumAltUno.setText("" +((intUltNumDocAlt)+1));
                        }
                        else
                        {
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
        
        
//        if(!txtNumAltUno.getText().equals(""))
//        {
//            if (!txtTipDoc.getText().equals(""))
//            {
//                if(rtnDocExi())
//                {
//                    tabFrm.setSelectedIndex(0);
//                    mostrarMsgInf("<HTML>El documento a insertar ya existe.<BR>Verifique Tipo de Documento y Nï¿½mero de Documento y vuelva a intentarlo.</HTML>");
//                    txtNumAltUno.requestFocus();
//                    return false;
//                }
//            }
//            else
//            {
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
//                txtTipDoc.requestFocus();
//            }
//        }
//        else
//        {
//            mostrarMsgInf("<HTML><CENTER>Â¡Â¡Â¡ATENCION!!! </CENTER><BR>El Documento que desea modificar no posee Numero Alterno, Favor verificar</HTML>");
//        }
        
        if (txtTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero de documento</FONT> es obligatorio.<BR>Escriba un nï¿½mero de tipo de documento y vuelva a intentarlo.</HTML>");
            txtTipDoc.requestFocus();
            return false;
        }

        if (txtNomDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomDoc.requestFocus();
            return false;
        }

        if(txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Proveedor</FONT> es obligatorio.<BR>Escriba un cï¿½digo de proveedor y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }

        if(txtDesLarCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Proveedor</FONT> es obligatorio.<BR>Escriba un nombre de proveedor y vuelva a intentarlo.</HTML>");            
            txtDesLarCli.requestFocus();
            return false;
        }

        if(txtFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            txtFecDoc.requestFocus();
            return false;
        }
        
        if(!txtFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            txtFecDoc.requestFocus();
            return false;
        } 
        if(txtNumAltUno.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero de Alterno 1</FONT> es obligatorio.<BR>Escriba un nï¿½mero de retenciï¿½n y vuelva a intentarlo.</HTML>");            
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
        

        double intMonExc=0.00;
        double intMonPnd=0.00;
        
        intMonExc=Double.parseDouble(txtMonDocExc.getText());        
        intMonPnd=Double.parseDouble(txtMonDocPnd.getText());
        if(intMonExc!=intMonPnd){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Los valores de CREDITO con los valores de DEBITO <FONT COLOR=\"blue\">NO SON IGUALES..</FONT>.<BR><CENTER>Favor Verifique e Iguale los valores...</CENTER></BR></HTML>");            
            return false;            
        }


        if(objTblModExc.getRowCountTrue()==0)
        {
           mostrarMsgInf("<HTML>No existen Registros en el Detalle de CREDITO del Documento.<BR>Favor ingrese valores en el Detalle  y vuelva a intentarlo.</HTML>");
           return false;
        }

        if(objTblModPnd.getRowCountTrue()==0)
        {
           mostrarMsgInf("<HTML>No existen Registros en el Detalle de DEBITO del Documento.<BR>Favor ingrese valores en el Detalle  y vuelva a intentarlo.</HTML>");
           return false;
        }


        return true;
    }
    
    /**
     *Esta funcion valida si los campos obligatorios han sido completados correctamente 
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
                    intUltNumDocAlt = intNumAltDoc;

                    try
                    { 
                        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
                        strTit="Mensaje del sistema Zafiro";
                        strMsg="<HTML>El N�mero de documento <FONT COLOR=\"blue\">" + txtNumAltUno.getText() + " </FONT> ya existe,  el siguiente n�mero de documento es: <FONT COLOR=\"blue\">" +  ((intUltNumDocAlt)+1) + " </FONT> <BR> �Desea que el sistema le asigne dicho n�mero al documento..? </HTML>";

                        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
                        {
                            txtNumAltUno.setText("" +((intUltNumDocAlt)+1));
                        }
                        else
                        {
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
        
//        if(!txtNumAltUno.getText().equals(""))
//        {
//            if (!txtTipDoc.getText().equals(""))
//            {
//                if(rtnDocExi())
//                {
//                    if(intbanmodcel > 0)
//                    {
//                        if(intBanDocExi > 0)
//                        {
//                            //if(!(txtNumAltUno.getText().equals(STRNUMDOCCAB))) //STRNUMDOCCAB  ///STRNUMDOC
//                            if(!(txtNumAltUno.getText().equals(STRNUMDOC))) //STRNUMDOCCAB  ///STRNUMDOC
//                            {
//                                tabFrm.setSelectedIndex(0);
//                                mostrarMsgInf("<HTML>El documento a insertar ya existe.<BR>Verifique Tipo de Documento y Nï¿½mero de Documento y vuelva a intentarlo.</HTML>");
//                                txtNumAltUno.requestFocus();
//                                intbanmodcel--;
//                                return false;
//                            }
//                        }
//                    }
//                }
//            }
//            else
//            {
//                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento y vuelva a intentarlo.</HTML>");
//                txtTipDoc.requestFocus();
//            }
//        }
//        else
//        {
//            mostrarMsgInf("<HTML><CENTER>Â¡Â¡Â¡ATENCION!!! </CENTER><BR>El Documento que desea modificar no posee Numero Alterno, Favor verificar</HTML>");
//        }
                
        if (txtTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero de documento</FONT> es obligatorio.<BR>Escriba un nï¿½mero de tipo de documento y vuelva a intentarlo.</HTML>");
            txtTipDoc.requestFocus();
            return false;
        }

        if (txtNomDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomDoc.requestFocus();
            return false;
        }

        if(txtCodCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cï¿½digo de Proveedor</FONT> es obligatorio.<BR>Escriba un cï¿½digo de proveedor y vuelva a intentarlo.</HTML>");
            txtCodCli.requestFocus();
            return false;
        }

        if(txtDesLarCli.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nombre de Proveedor</FONT> es obligatorio.<BR>Escriba un nombre de proveedor y vuelva a intentarlo.</HTML>");            
            txtDesLarCli.requestFocus();
            return false;
        }

        if(txtFecDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            txtFecDoc.requestFocus();
            return false;
        }
        
        if(!txtFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de Documento</FONT> es obligatorio.<BR>Escriba una fecha de documento y vuelva a intentarlo.</HTML>");            
            txtFecDoc.requestFocus();
            return false;
        }
        if(txtNumAltUno.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Nï¿½mero de Alterno 1</FONT> es obligatorio.<BR>Escriba un nï¿½mero de retenciï¿½n y vuelva a intentarlo.</HTML>");            
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
        

        double intMonExc=0.00;
        double intMonPnd=0.00;
        
        intMonExc=Double.parseDouble(txtMonDocExc.getText());        
        intMonPnd=Double.parseDouble(txtMonDocPnd.getText());
        if(intMonExc!=intMonPnd){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Los valores de CREDITO con los valores de DEBITO <FONT COLOR=\"blue\">NO SON IGUALES..</FONT>.<BR><CENTER>Favor Verifique e Iguale los valores...</CENTER></BR></HTML>");            
            return false;            
        }


        if(objTblModExc.getRowCountTrue()==0)
        {
           mostrarMsgInf("<HTML>No existen Registros en el Detalle de CREDITO del Documento.<BR>Favor ingrese valores en el Detalle  y vuelva a intentarlo.</HTML>");
           return false;
        }

        if(objTblModPnd.getRowCountTrue()==0)
        {
           mostrarMsgInf("<HTML>No existen Registros en el Detalle de DEBITO del Documento.<BR>Favor ingrese valores en el Detalle  y vuelva a intentarlo.</HTML>");
           return false;
        }


        return true;
    }

    

      /**
     * Esta funciï¿½n permite insertar la cabecera de un registro.
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
                
                //Obtener el cï¿½digo del ï¿½ltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();                
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);//asigno el codigo del documento
                
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabPag (co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc1, ne_numDoc2, co_cli";
                strSQL+=", tx_ruc, tx_nomCli, tx_dirCli, nd_mondoc, tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod, tx_comIng, tx_comMod)";
                strSQL+=" VALUES (";
                strSQL+=objParSis.getCodigoEmpresa();
                strSQL+=", " + objParSis.getCodigoLocal();
                strSQL+=", " + txtCodTipDoc.getText();
                strSQL+=", " + intUltReg;
                strSQL+=", '" + objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", " + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", " + objUti.codificar(txtNumAltDos.getText(),2);
                strSQL+=", " + objUti.codificar(txtCodCli.getText(),2);
                strSQL+=", " + objUti.codificar(strIdePro);
                strSQL+=", " + objUti.codificar(txtDesLarCli.getText());
                strSQL+=", " + objUti.codificar(strDirPro);
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
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabPag";
                strAux=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" SET fe_doc='" + strAux + "'";
                strAux=objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=", fe_ven='" + strAux + "'";
                strSQL+=", ne_numDoc1=" + objUti.codificar(txtNumAltUno.getText(),2);
                strSQL+=", ne_numDoc2=" + objUti.codificar(txtNumAltDos.getText(),2);
                strSQL+=", co_cli=" + objUti.codificar(txtCodCli.getText(),2);
                strSQL+=", tx_ruc=" + objUti.codificar(strIdePro);
                strSQL+=", tx_nomCli=" + objUti.codificar(txtDesLarCli.getText());
                strSQL+=", tx_dirCli=" + objUti.codificar(strDirPro);
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
    
    protected String rtnNomUsrSis()
    {
	java.sql.Connection conUltRegDoc;
        java.sql.Statement stmUltRegDoc;
        java.sql.ResultSet rstUltRegDoc;
        String strSQL, stRegRep="";
        int intAux=0;
        int codusr= objParSis.getCodigoUsuario();

        try
        {
            if(txtCodTipDoc.getText().length()>0)
            {
               conUltRegDoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
               conUltRegDoc.setAutoCommit(false);
                    if (conUltRegDoc!=null)
                    {                        
                        stmUltRegDoc=conUltRegDoc.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                        strSQL="";
                        strSQL+="SELECT co_usr, tx_usr, tx_pwd, tx_nom";
                        strSQL+=" FROM tbm_usr AS a1";
                        strSQL+=" WHERE a1.co_usr= " + codusr;
                        strSQL+=" AND a1.st_usrSis='S'";
                        strSQL+=" AND a1.st_reg='A'";
                        rstUltRegDoc=stmUltRegDoc.executeQuery(strSQL);
                        if (rstUltRegDoc.next())
                        {
                            stRegRep = rstUltRegDoc.getString("tx_nom");
                        }
                    }
            }
            else
                stRegRep="";
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return stRegRep;
    }
    
    
    
    private boolean regenerarDiario()
    {
        boolean blnRes=true;
        
        return blnRes;
    }    
    
    
    /**
     * Esta funciï¿½n muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        String strCodTipDoc, strNatDoc;
        int numdoc=0, intUltNumDoc=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_mod";
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
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc, a1.ne_mod";
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
                if (rst.next())
                {
                    strCodTipDoc=(rst.getString("co_tipDoc"));
                    strNatDoc=(rst.getString("tx_natDoc"));
                                        
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtTipDoc.setText(rst.getString("tx_desCor"));
                    txtNomDoc.setText(rst.getString("tx_desLar"));
                    txtNumAltUno.setText("" + (rst.getInt("ne_ultDoc")+1));
                    
                    ///este valor quedo para la historia ya que el nuevo estado para los cruce paso de estado I a estado A///
                    ///por lo que la variable intSig tendra otra forma de calcularlo///
//                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);  
                    strMod=(rst.getString("ne_mod"));

                    if(strCodTipDoc.equals("55"))
                        intSig=1;
                    else if(strCodTipDoc.equals("59"))
                        intSig=-1;
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
                stm=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Armar la sentencia SQL.
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
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {                    
                    numdoc=(rst.getInt("ne_numdoc1"));
                    intUltNumDoc = numdoc+1;
                    ///txtNumAltUno.setText("" + intUltNumDoc);
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
    private boolean insertarDetExc()//////CREDITOS///////
    {
        int intCodEmp, intCodLoc, i;
        String strCodTipDoc, strCodDoc;
        String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        double dblValPndAux=0.00;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                for (i=0;i<objTblModExc.getRowCountTrue();i++)
                {
                    if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                    {

                        dblValPndAux=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND_INI)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND_INI));
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
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                        ////////////////////////////////////////////////////////////////////
                        
                        ////////CTS x COBRAR///////
                        if(intCodMnu==799)
                        {
                            if(dblValPndAux > 0)
                                intSigAbo=-1;  ///signo para el abono seria negativo////
                            if(dblValPndAux < 0)
                                intSigAbo=1;   ///signo para el abono seria positivo////
                        }

                        ////////CTS x PAGAR///////
                        if(intCodMnu==818)
                        {
                            if(dblValPndAux > 0)
                                intSigAbo=-1;  ///signo para el abono seria negativo////
                            if(dblValPndAux < 0)
                                intSigAbo=1;   ///signo para el abono seria positivo////
                        }
                        strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO), 3).toString()));
                        strSQL+=", " + objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        stm.executeUpdate(strSQL);
                        j++;
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

    
    /**
     * Esta funciï¿½n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */

    //este metodo es para insertar los registros de la tabla de debito
    private boolean insertarDetPnd()
    {
        int intCodEmp, intCodLoc, i;
        String strCodTipDoc, strCodDoc;
        String strFecDoc=objUti.formatearFecha(txtFecDoc.getText().toString(), "dd/MM/yyy", "yyyy/MM/dd");
        boolean blnRes=true;
        double dblValPndAux=0.00;

        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodEmp=objParSis.getCodigoEmpresa();
                intCodLoc=objParSis.getCodigoLocal();
                strCodTipDoc=txtCodTipDoc.getText();
                strCodDoc=txtCodDoc.getText();
                for (i=0;i<objTblModPnd.getRowCountTrue();i++)
                {
                    if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                    {
                        dblValPndAux=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI));
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
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                        ////////////////////////////////////////////////////////////////////
                        ////////CTS x COBRAR///////
                        if(intCodMnu==799)
                        {
                            if(dblValPndAux > 0)
                                intSigAbo=-1;  ///signo para el abono seria negativo////
                            if(dblValPndAux < 0)
                                intSigAbo=1;   ///signo para el abono seria positivo////
                        }
                        ////////CTS x PAGAR///////
                        if(intCodMnu==818)
                        {
                            if(dblValPndAux > 0)
                                intSigAbo=-1;  ///signo para el abono seria negativo////
                            if(dblValPndAux < 0)
                                intSigAbo=1;   ///signo para el abono seria positivo////
                        }
                        strSQL+=", " + (intSigAbo * Double.parseDouble(objUti.codificar(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO), 3).toString()));
                        strSQL+=", " + objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                        strSQL+=", Null";
                        strSQL+=", Null";
                        strSQL+=", " + txtNumAltUno.getText();
                        strSQL+=", '#" + strFecDoc;
                        strSQL+="#', '#" + strFecDoc;
                        strSQL+="#')";
                        stm.executeUpdate(strSQL);
                        j++;
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
        


    /**
     * Esta funciï¿½n permite actualizar la tabla "tbm_pagMovInv".
     * @param intTipOpe El tipo de operaciï¿½n.
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo</I></TD><TD><I>Operaciï¿½n</I></TD></TR>
     *     <TR><TD>0</TD><TD>Insertar</TD></TR>
     *     <TR><TD>1</TD><TD>Modificar</TD></TR>
     *     <TR><TD>2</TD><TD>Eliminar</TD></TR>
     *     <TR><TD>3</TD><TD>Anular</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return true: Si se pudo actualizar la tabla.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarTbmPagMovInvPnd(int intTipOpe)
    {
        int intCodEmp, i, intCantRegAnu=0, intCantRegEli=0;
        double dblAbo1, dblAbo2, dblAbo3, dblSaldo;
        boolean blnRes=true;
        double dblValPndAux=0.00;

        arrValDocPnd = new double [200];
        arrValAboPnd = new double [200];
        arrNumFacPnd = new String [200];
        arrCodLocPnd = new String [200];
        arrSalRegPnd = new double [200];
        arrTipDocPnd = new String [200];
        arrCodDocPnd = new String [200];

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
                        ////////////////////////PRUEBA//////////////////////
                            intCantRegAnu = objTblModPnd.getRowCountTrue();
                            CAN_REG_ANU_PND = intCantRegAnu;
                            for(i=0; i<objTblModPnd.getRowCountTrue(); i++)
                            {
                                arrNumFacPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                                arrCodLocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_LOC));
                                arrTipDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_TIP_DOC));
                                arrCodDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC));
                                arrValDocPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_MON_DOC));
                                arrValAboPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO));
                                arrSalRegPnd[i] = objUti.redondear((arrValDocPnd[i] + arrValAboPnd[i]),2);
                            }
                        /////////////////////FIN DE LA PARTE DE PRUEBA/////////
                    }
                    if(intTipOpe==2)
                    {

                        /////////////PRUEBA///////////////////////
                            intCantRegEli = objTblModPnd.getRowCountTrue();
                            CAN_REG_ELI_PND = intCantRegEli;
                            for(i=0; i<objTblModPnd.getRowCountTrue(); i++)
                            {
                                arrNumFacPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                                arrCodLocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_LOC));
                                arrTipDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_TIP_DOC));
                                arrCodDocPnd[i] = ("" + tblValPnd.getValueAt(i, INT_TBL_DAT_COD_DOC));
                                arrValDocPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_MON_DOC));
                                arrValAboPnd[i] = objUti.parseDouble(tblValPnd.getValueAt(i, INT_TBL_DAT_VAL_ABO));
                                arrSalRegPnd[i] = objUti.redondear((arrValDocPnd[i] + arrValAboPnd[i]),2);
                            }
                        //////FIN DE LA PARTE DE PRUEBA/////////
                    }

                    switch (intTipOpe)
                    {
                        case 0:
                        case 1://para modificar e insertar

                            System.out.println("Ingresa Aqui...");

                            for (i=0;i<objTblModPnd.getRowCountTrue();i++)
                            {

                                strEstLin=objUti.parseString(objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN));
                                STRESTLIN = strEstLin;
                                if (!objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN).equals(""))
                                {
                                    dblValPndAux=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI));
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));

                                    ///dblSaldo = (dblAbo1-dblAbo2);
                                    dblSaldo = (dblAbo1);
                                    ////////CTS x COBRAR///////
                                    if(intCodMnu==799)
                                    {
                                        if(dblValPndAux > 0)
                                            intSig=-1;  ///signo para el abono seria negativo////

                                        if(dblValPndAux < 0)
                                            intSig=1;   ///signo para el abono seria positivo////
                                    }

                                    ////////CTS x PAGAR///////
                                    if(intCodMnu==818)
                                    {
                                        if(dblValPndAux > 0)
                                            intSig=-1;  ///signo para el abono seria negativo////

                                        if(dblValPndAux < 0)
                                            intSig=1;   ///signo para el abono seria positivo////

//                                         agregarCambioCuentasContables(con, intCodEmp, Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()),
//                                                  Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC).toString()),
//                                                   Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()),
//                                                   java.lang.Math.abs(dblAbo1) );
                                    }

                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";////original////
                                    /////////strSQL+=" SET nd_abo=nd_abo + (" + (1) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);
                                    objTblModPnd.setValueAt(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO),i,INT_TBL_DAT_AUX_ABO);
                                }
                            }
                            break;
                        case 2:
                        case 3://para anular
                            for (i=0;i<objTblModPnd.getRowCountTrue();i++)
                            {

                                strEstLin=objUti.parseString(objTblModPnd.getValueAt(i,INT_TBL_DAT_LIN));
                                STRESTLIN = strEstLin;
                                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                                {
                                    dblValPndAux=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_PND_INI));
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));

                                    dblSaldo = (dblAbo3-dblAbo2);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";

                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos()) + ")";////antes original///
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((-dblAbo2),objParSis.getDecimalesBaseDatos()) + ")"; ///original///
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos()) + ")";///PRUEBA///
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);
                                }
                                else
                                {
                                    dblAbo1=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModPnd.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    ////dblSaldo = (dblAbo3+dblAbo2);
                                    dblSaldo = (dblAbo3-dblAbo2);
                                    //Armar la sentencia SQL.//
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos()) + ")";////antes original///
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((-dblAbo2),objParSis.getDecimalesBaseDatos()) + ")"; ///original///
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos()) + ")";///PRUEBA///
                                    strSQL+=" SET nd_abo=" + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);

//                                    quitarCambioCuentasContables(con, intCodEmp, Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()),
//                                       Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC).toString()),
//                                       Integer.parseInt(objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()),  java.lang.Math.abs(dblAbo3)  );
                                }
                            }
                            break;
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

//
//    private boolean agregarCambioCuentasContables(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01;
//  java.sql.ResultSet rstLoc;
//  int intCodReg=0;
//  String strSql="";
//  try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//
// /*********** CASO IVA ********************/
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//          double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", "+dblValIva+", nd_monhab, tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctahab=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", nd_mondeb, "+dblValAsi+", tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}

//
//private boolean agregarCambioCuentasContablesDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01;
//  java.sql.ResultSet rstLoc;
//  int intCodReg=0;
//  String strSql="";
//  try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//
//
//
// /*********** CASO IVA ********************/
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//          double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", nd_mondeb, "+dblValIva+",  tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctadeb=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//         if(rstLoc.getString("exitCta").equals("N")){
//
//             intCodReg= _getObtenerMaxCodRegDetDia(conn, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc );
//
//             strSql = "INSERT INTO TBM_DETDIA( co_emp, co_loc, co_tipdoc, co_dia, co_reg, co_cta, nd_mondeb, " +
//             " nd_monhab, tx_ref, st_regrep, st_conban, fe_conban, co_usrconban ) " +
//             " SELECT co_emp, co_loc, co_tipdoc, co_dia, "+intCodReg+", "+rstLoc.getInt("co_ctafin")+", "+dblValAsi+", nd_monhab, tx_ref, st_regrep,  " +
//             " st_conban, fe_conban, co_usrconban FROM tbm_detdia  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+" "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }else{
//             strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}
//


/**
 * Obtiene el maximo registro de la tabla tbm_detdia  + 1
 * @param conn    coneccion de la base
 * @param intCodEmp   codigo de la empresa
 * @param intCodLoc   codigo del local
 * @param intCodTipDoc codigo del tipo documento
 * @param intCodDoc    codigo del documento
 * @return  -1  si no se hay algun error   caso contrario retorna el valor correcto
 */
public int _getObtenerMaxCodRegDetDia(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
  int intCodReg=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();

       strSql="SELECT CASE WHEN max(co_reg) IS NULL THEN 1 ELSE max(co_reg)+1 END AS coreg FROM tbm_detdia " +
       " WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND co_tipdoc="+intCodTipDoc+" AND co_dia= "+intCodDoc+" ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
             intCodReg=rstLoc.getInt("coreg");
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }}catch(java.sql.SQLException e) { objUti.mostrarMsgErr_F1(this, e); }
    catch(Exception  Evt){ objUti.mostrarMsgErr_F1(this, Evt); }
 return intCodReg;
}


//
//private boolean quitarCambioCuentasContables(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01, stmLoc02;
//  java.sql.ResultSet rstLoc;
//  String strSql="";
// try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//     stmLoc02=conn.createStatement();
//
//
//
// /*********** CASO IVA ********************/
//
//     double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a3.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"  and    a3.nd_mondeb >=  "+dblValIva+" ";
//     System.out.println("1 --------->  "+strSql );
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//         if(rstLoc.getString("exitCta").equals("S")){
//
//
//              strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a3.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctahab=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where  a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"  and    a3.nd_monhab >=  "+dblValAsi+"  ";
//      System.out.println("2 ---------> "+strSql );
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//         if(rstLoc.getString("exitCta").equals("S")){
//
//             strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//
//
//
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//      stmLoc02.close();
//      stmLoc02=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}
//
//
//private boolean quitarCambioCuentasContablesDevCom(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, double dblValAsi ){
//  boolean blnRes=false;
//  java.sql.Statement stmLoc, stmLoc01, stmLoc02;
//  java.sql.ResultSet rstLoc;
//  String strSql="";
// try{
//    if(conn!=null){
//     stmLoc=conn.createStatement();
//     stmLoc01=conn.createStatement();
//     stmLoc02=conn.createStatement();
//
//
//
// /*********** CASO IVA ********************/
//
//     strSql="select a2.co_ctaivacom,  a1.*, a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta " +
//     " from tbm_detdia as a " +
//     " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//     " inner join tbm_emp as a2 on (a2.co_emp=a.co_emp     and  a2.co_ctaivacom=a.co_cta )  " +
//     " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin )  " +
//     " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//     System.out.println("1 --------->  "+strSql );
//     rstLoc=stmLoc.executeQuery(strSql);
//     if(rstLoc.next()){
//
//         if(rstLoc.getString("exitCta").equals("S")){
//             double dblValIva =  objUti.redondear( (dblValAsi -(dblValAsi/1.12)), 2);
//
//              strSql = " UPDATE TBM_DETDIA SET nd_monhab= nd_monhab + "+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_monhab= nd_monhab-"+dblValIva+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
// /******************************************/
// /*********** CASO PROVEEDOR VARIOS ********************/
//
//      strSql="select a2.co_ctahab, a1.co_ctafin,  a.co_reg, a.nd_mondeb, a.co_reg as coregtran,   a3.co_reg as coregfin, case when a3.co_reg is null then 'N' else 'S' end as exitCta  " +
//      " from tbm_detdia as a " +
//      " inner join tbr_ctaTraFinTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_ctatra=a.co_cta ) " +
//      " inner join tbm_cabtipdoc as a2 on (a2.co_emp=a.co_emp  and a2.co_loc=a.co_loc and a2.co_tipdoc=a.co_tipdoc    and  a2.co_ctadeb=a.co_cta ) " +
//      " left join tbm_detdia as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_dia=a.co_dia and a3.co_cta=a1.co_ctafin ) " +
//      " where  a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_dia= "+intCodDoc+"   ";
//      System.out.println("2 ---------> "+strSql );
//      rstLoc=stmLoc.executeQuery(strSql);
//      if(rstLoc.next()){
//         if(rstLoc.getString("exitCta").equals("S")){
//
//             strSql = " UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb + "+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregtran")+"  "+
//             " ; UPDATE TBM_DETDIA SET nd_mondeb= nd_mondeb-"+dblValAsi+"  " +
//             " WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_dia="+intCodDoc+" and co_reg="+rstLoc.getInt("coregfin")+"  ";
//             stmLoc01.executeUpdate(strSql);
//         }
//     }
//     rstLoc.close();
//     rstLoc=null;
//
//  /******************************************/
//
//
//
//      stmLoc.close();
//      stmLoc=null;
//      stmLoc01.close();
//      stmLoc01=null;
//      stmLoc02.close();
//      stmLoc02=null;
//    blnRes=true;
// }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
//  catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
// return blnRes;
//}



    private boolean actualizarTbmPagMovInvExc(int intTipOpe)
    {
        int intCodEmp, i, intCantRegAnu=0, intCantRegEli=0;
        double dblAbo1=0.0, dblAbo2=0.0, dblAbo3=0.0, dblSaldo=0.0, dblValPndAux=0.00;
        boolean blnRes=true;

        arrValDocCre = new double [200];
        arrValAboCre = new double [200];
        arrNumFacCre = new String [200];
        arrCodLocCre = new String [200];
        arrSalRegCre = new double [200];
        arrTipDocCre = new String [200];
        arrCodDocCre = new String [200];

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
                        ////////////////////////PRUEBA//////////////////////
                        intCantRegAnu = objTblModExc.getRowCountTrue();
                        CAN_REG_ANU_EXC = intCantRegAnu;
                        for(i=0; i<objTblModExc.getRowCountTrue(); i++)
                        {
                            arrNumFacCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                            arrCodLocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_LOC));
                            arrTipDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_TIP_DOC));
                            arrCodDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_DOC));
                            arrValDocCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_MON_DOC));
                            arrValAboCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_VAL_ABO));
                            arrSalRegCre[i] = objUti.redondear((arrValDocCre[i] + arrValAboCre[i]),2);
                        }
                        /////////////////////FIN DE LA PARTE DE PRUEBA/////////
                    }
                    if(intTipOpe==2)
                    {
                        /////////////PRUEBA///////////////////////
                        intCantRegEli = objTblModExc.getRowCountTrue();
                        CAN_REG_ELI_EXC = intCantRegEli;
                        for(i=0; i<objTblModExc.getRowCountTrue(); i++)
                        {
                            arrNumFacCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_NUM_DOC));
                            arrCodLocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_LOC));
                            arrTipDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_TIP_DOC));
                            arrCodDocCre[i] = ("" + tblValExc.getValueAt(i, INT_TBL_DAT_COD_DOC));
                            arrValDocCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_MON_DOC));
                            arrValAboCre[i] = objUti.parseDouble(tblValExc.getValueAt(i, INT_TBL_DAT_VAL_ABO));
                            arrSalRegCre[i] = objUti.redondear((arrValDocCre[i] + arrValAboCre[i]),2);
                        }
                        //////FIN DE LA PARTE DE PRUEBA/////////
                    }

                    switch (intTipOpe)
                    {
                        case 0://insertar
                        case 1://modificar
                            for (i=0;i<objTblModExc.getRowCountTrue();i++)
                            {
                                strEstLin=objUti.parseString(objTblModExc.getValueAt(i,INT_TBL_DAT_LIN));
                                STRESTLIN = strEstLin;
                                if (!objTblModExc.getValueAt(i,INT_TBL_DAT_LIN).equals(""))
                                {
                                    dblValPndAux=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND_INI)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_PND_INI));
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    dblSaldo=(dblAbo1-dblAbo2);
                                    ////////CTS x COBRAR///////
                                    if(intCodMnu==799)
                                    {
                                        if(dblValPndAux > 0)
                                            intSig=-1;  ///signo para el abono seria negativo////
                                        if(dblValPndAux < 0)
                                            intSig=1;   ///signo para el abono seria positivo////
                                    }
                                    ////////CTS x PAGAR///////
                                    if(intCodMnu==818)
                                    {
                                        if(dblValPndAux > 0)
                                            intSig=-1;  ///signo para el abono seria negativo////
                                        if(dblValPndAux < 0)
                                            intSig=1;   ///signo para el abono seria positivo////

//                                         agregarCambioCuentasContablesDevCom(con, intCodEmp, Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()),
//                                                  Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC).toString()),
//                                                   Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()),
//                                                   java.lang.Math.abs(dblAbo1) );
                                    }
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((dblAbo1-dblAbo2),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";///original///
                                    /////////strSQL+=" SET nd_abo=nd_abo - (" + (1) * objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos())+")";
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);
                                    objTblModExc.setValueAt(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO),i,INT_TBL_DAT_AUX_ABO);
                                }
                            }
                            break;
                        case 2://eliminar
                        case 3://anular

                            for (i=0;i<objTblModExc.getRowCountTrue();i++)
                            {
                                strEstLin=objUti.parseString(objTblModExc.getValueAt(i,INT_TBL_DAT_LIN));
                                STRESTLIN = strEstLin;
                                if(strMod.equals("3") || strMod.equals("1"))  //para ingreso o CXC
                                {
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    //dblSaldo=(dblAbo3+dblAbo2);
                                    dblSaldo=(dblAbo3-dblAbo2);
                                    //Armar la sentencia SQL.//
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///antes original///
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((-dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///original//
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///PRUEBA///
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);
                                }
                                else
                                {
                                    dblAbo1=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO));
                                    dblAbo2=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_AUX_ABO));
                                    dblAbo3=objUti.parseDouble(objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG)==null?"0.0":objTblModExc.getValueAt(i,INT_TBL_DAT_VAL_ABO_PAG));
                                    dblSaldo=(dblAbo3-dblAbo2);
                                    //Armar la sentencia SQL.
                                    strSQL="";
                                    strSQL+="UPDATE tbm_pagMovInv";
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///antes original///
                                    ///strSQL+=" SET nd_abo=nd_abo - (" + (intSig) * objUti.redondear((-dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///original//
                                    ///strSQL+=" SET nd_abo=nd_abo + (" + (intSig) * objUti.redondear((dblAbo2),objParSis.getDecimalesBaseDatos())+ ")";///PRUEBA///
                                    strSQL+=" SET nd_abo = " + objUti.redondear((dblSaldo),objParSis.getDecimalesBaseDatos());
                                    strSQL+=" WHERE co_emp=" + intCodEmp;
                                    strSQL+=" AND co_loc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC);
                                    strSQL+=" AND co_tipDoc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC);
                                    strSQL+=" AND co_doc=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC);
                                    strSQL+=" AND co_reg=" + objTblModExc.getValueAt(i,INT_TBL_DAT_COD_REG);
                                    stm.executeUpdate(strSQL);

//                                     quitarCambioCuentasContablesDevCom(con, intCodEmp, Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC).toString()),
//                                     Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC).toString()),
//                                     Integer.parseInt(objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC).toString()),  java.lang.Math.abs(dblAbo3)  );
                                

                                }
                            }
                            break;
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
    
    
    /**
     * Esta funciï¿½n inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            
            if (con!=null)
            {
                if(I != 0)
                {
                    if(J != 0)
                    {
                        if (insertarCab())
                        {
                            if (insertarDetExc())////CREDITOS////
                            {
                                if (insertarDetPnd())////DEBITOS////
                                {                                                
                                    ///if (actualizarTbmPagMovInvPnd(0))
                                    if (actualizarTbmPagMovInvExc(0))////CREDITOS////
                                    {                            
                                        ///if (actualizarTbmPagMovInvExc(0))
                                        if (actualizarTbmPagMovInvPnd(0))////DEBITOS////
                                        {
                                            if (objUti.set_ne_ultDoc_tbm_cabTipDoc(this, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtNumAltUno.getText())))
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
                    {
                        mostrarMsgInf("!!! NO HA SELECCIONADO REGISTROS EN LA TABLA DE CREDITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
                    }
                }
                else
                {
                    mostrarMsgInf("!!! NO HA SELECCIONADO REGISTROS EN LA TABLA DE DEBITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
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
            {
                if(I > 0)
                {
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
        //                                        if (objAsiDia.actualizarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()), txtNumAltUno.getText(), objUti.parseDate(txtFecDoc.getText(),"dd/MM/yyyy"), "A"))
        //                                        {
                                                    con.commit();
                                                    blnRes=true;
        //                                        }
        //                                        else
        //                                            con.rollback();
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
                        mostrarMsgInf("Â¡Â¡Â¡ NO HA MODIFICADO REGISTROS EN LA TABLA DE CREDITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
                    }
                }
                else
                {
                    mostrarMsgInf("Â¡Â¡Â¡ NO HA MODIFICADO REGISTROS EN LA TABLA DE DEBITOS... FAVOR SELECCIONE ALGUN REGISTRO !!!");
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
//                            if (objAsiDia.anularDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
//                            {
                                con.commit();
                                blnRes=true;
//                            }
//                            else
//                                con.rollback();                                                
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
                //if (eliminarDet())
                //{
                    if (eliminarCab())
                    {
                        if (actualizarTbmPagMovInvExc(2))
                        {                        
                            if (actualizarTbmPagMovInvPnd(2))
                            {
//                                    if (objAsiDia.eliminarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText())))
//                                    {
                                    con.commit();
                                    blnRes=true;
//                                    }
//                                    else
//                                        con.rollback();
                            }
                            else
                                con.rollback();                        
                        }
                        else
                            con.rollback();
                    }
                    else
                        con.rollback();
                //}
                //else
                    //con.rollback();
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
                ///if (blnHayCam)
                if (blnHayCam || objTblModExc.isDataModelChanged() || objTblModPnd.isDataModelChanged())
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
                ///cargarUltNumDoc();
                
                ////Prueba para mostrar documento///
                ///cargaTipoDocPredeterminado();
                
                objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
                objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
//                objAsiDia.setEditable(true);
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
            //limpiarFrm();
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
            txtCodCli.setEditable(false);
            txtDesLarCli.setEditable(false);
            butCli.setEnabled(false);
            txtCodDoc.setEditable(false);
            txtMonDocExc.setEditable(false);
            txtMonDocPnd.setEditable(false);

            ///mostrarTipDocPre();

            objTblModExc.setModoOperacion(objTblModExc.INT_TBL_EDI);
            objTblModPnd.setModoOperacion(objTblModPnd.INT_TBL_EDI);
//            objAsiDia.setEditable(true);
            consultarReg();
            ///cargarDetRegExc();
            ///cargarDetRegPnd();
            txtNumAltUno.selectAll();
            txtNumAltUno.requestFocus();  
            
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
            //consultarReg();
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
                ///consultarReg();
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
                    if (blnHayCam /*|| objAsiDia.isDiarioModificado()*/)
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
                        }
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
     
     public void clickFin() {
            try
            {
                if (!rstCab.isLast())
                {
                    if (blnHayCam /*|| objAsiDia.isDiarioModificado()*/)
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
            
     public void clickImprimir(){
     }
     
     public void clickVisPreliminar(){
     }
     
     public void clickAceptar(){
          //limpiarFrm();
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
            

     private boolean validaCampos()
     {
                if(txtCodCli.getText().equals("") ){
                   MensajeValidaCampo("Proveedor");
                   txtCodCli.requestFocus();
                   return false;
               }
               if(!txtFecDoc.isFecha()){
                   MensajeValidaCampo("Fecha de Documento");
                   txtFecDoc.requestFocus();
                   return false;
               }
                
                
               int intColObligadas[] = {INT_TBL_DAT_NUM_DOC,/*INT_TBL_DAT_NUM_COT,*/INT_TBL_DAT_MON_DOC,INT_TBL_DAT_VAL_PND,INT_TBL_DAT_VAL_ABO};
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
        
        public boolean afterVistaPreliminar() {
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
        
        public boolean afterAceptar() {
            return true;
        }        
        
        /**
         * Metodo que genera las OD REMOTAS Y LAS LOCALES DE LOS DOCUMENTOS FACTURAS CRUZADOS CON ND.
         */
        public void generarODLocRem(){
        
            ZafImp oi=new ZafImp();
            Iterator itVec=vecDocSinPnd.iterator();
            String strQry=new String("");
            ZafGenOdPryTra objGenOD=new ZafGenOdPryTra();
            ZafGuiRemDAO objGuiRemDAO=new ZafGuiRemDAO();
            ZafCnfDoc objValCnf=new ZafCnfDoc(objParSis,this);
            try{
                Connection con2=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con2.setAutoCommit(false);
                while(itVec.hasNext()){
                    strQry=" select sum(mo_pag) as monto_pagar, sum(nd_abo) as monto_abonado , co_emp, co_loc, co_tipdoc, co_doc";
                    strQry+=" from tbm_pagmovinv";
                    strQry+=" where co_emp=:co_emp";
                    strQry+=" and co_loc=:co_loc";
                    strQry+=" and co_tipdoc=:co_tipdoc";
                    strQry+=" and co_doc=:co_doc";
		    strQry+=" and co_tipret is null";
                    strQry+=" group by  co_emp, co_loc, co_tipdoc, co_doc";
                    strQry+=" having abs(sum(mo_pag))=  sum(nd_abo)";                    
                    String strPk=(String)itVec.next();
                    String[] arrPk=strPk.split("-");
                    strQry=strQry.replace(":co_emp",arrPk[0]).replace(":co_loc",arrPk[1]).replace(":co_tipdoc",arrPk[2]).replace(":co_doc",arrPk[3]);
                    Statement stm=con2.createStatement();
                    ResultSet rs=stm.executeQuery(strQry);
                    if(rs.next()){                      
                        
                        //objDaoOD.verificacionPrevCantODxDev(con, intCodMnu, intCodMnu, intCodMnu)
                        //objDaoOD.verificarIngFic(con2, intCodMnu, intCodMnu, CodDocEli, intCodMnu, intCodMnu)
                        //objDaoOD.verificarOrd(con, intCodMnu, intCodMnu, intCodMnu, intCodMnu)
                        //int intTipDoc=objDaoOD.verificarTipDocOD(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc"));
                        
                            //if(intTipDoc ==102 || intTipDoc==101){
                                
                                /*FORMA ANTERIOR AL PROYECTO DE TRANSFERENCIA*/
                                /*oi.setEmp(rs.getInt("co_emp"));
                                oi.setLoc(rs.getInt("co_loc"));
                                oi.setTipdoc(rs.getInt("co_tipdoc"));
                                oi.setCoDoc(rs.getInt("co_doc"));
                                oi.setNumdoc(Integer.parseInt(arrPk[4]));
                                ZafMetImp om=new ZafMetImp(oi);
                                boolean r=om.validarOD(con2);

                                if (r==false) {                            
                                    om.impresionNormal2(con2);
                                }
                                int intEmp=Integer.parseInt(arrPk[0]);
                                int intLoc=Integer.parseInt(arrPk[1]);
                                int intCodTipDoc2=Integer.parseInt(arrPk[2]);
                                int intCodDocu=Integer.parseInt(arrPk[3]);
                                int intNumDoc=Integer.parseInt(arrPk[4]);


                                
                                ZafGenGuiRem objZafGuiRem=new ZafGenGuiRem();
                                boolean booRetTer = objZafGuiRem.generarProTermL(con2, intEmp, intLoc, intCodTipDoc2, intCodDocu, intNumDoc);*/
                                /*FORMA ANTERIOR AL PROYECTO DE TRANSFERENCIA*/
                            //}else {
                        
                                /*FORMA DE GENERAR EL TERMINAL L PROYECTO DE TRANSFERENCIA*/
                                if(!objValCnf.isDocIngPenCnfxFac(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc"), "I")){
                                    if(!(objGenOD.validarODExs(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc")))){
                                        if(objGenOD.generarNumOD(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc"), false)){
                                            con2.commit();
                                            String strIp=objGenOD.obtenerIpSerImp(con2);
                                            objGenOD.imprimirOdLocal(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc"), strIp);
                                            objGenOD.generarTermL(con2, rs.getInt("co_emp"), rs.getInt("co_loc"), rs.getInt("co_tipdoc"), rs.getInt("co_doc"));
                                        }else{
                                            con2.rollback();
                                        }
                                    }
                                }
                                /*FORMA DE GENERAR EL TERMINAL L PROYECTO DE TRANSFERENCIA*/
                            //}

                    }
                }
                vecDocSinPnd.clear();

            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
        public boolean afterInsertar() 
        {
            try
            {
                conAux=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                conAux.setAutoCommit(false);

                if(conAux!=null)
                {
                    blnHayCam=false;
                    objTooBar.setEstado('w');
                    blnHayCam=false;


                    /*Dario_---Para Generar Ajutes Automaticos---*/

                    if(verificarDatoAjuste())
                    {

                        double valor=0.00, sumval=0.00;
                        int k=0, l=0;
                        objAjuAutSis.consultarReg();

                        l=objTblModExc.getRowCountTrue();

                        for (int i=0;i<objTblModExc.getRowCountTrue();i++)
                        {
                            if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                            {
                                objAjuAutSis.mostrarDatIns();

                               int codloc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC));
                               int codtipdoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC));
                               int coddoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC));

                               INTCODLOC = codloc;
                               INTTIPDOC = codtipdoc;
                               INTCODDOC = coddoc;

                               cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               sumval = sumval + valor;

                               String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                               ///////////////////////////////////////////////////////////////////////
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
                                   //objAjuAutSis.insertarRegAut(k);
                                   objAjuAutSis.insertarRegAut(k,strFecDocPag);
                                   /////objAjuAutSis.insertarRegAut(conAux, k, strFecDocPag);/////version de pueba/////
                               }
                               else
                               {
                                   p++;
                               }
                            }
                        }

                       ///////////////////////////////////////////////////////////////////////
                        objAjuAutSis.consultarReg();
                        for (int i=0;i<objTblModPnd.getRowCountTrue();i++)
                        {
                            if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                            {
                                objAjuAutSis.mostrarDatIns();

                               int codloc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC));
                               int codtipdoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC));
                               int coddoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC));

                               INTCODLOC = codloc;
                               INTTIPDOC = codtipdoc;
                               INTCODDOC = coddoc;

                               cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                               sumval = sumval + valor;
                               String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                               ///////////////////////////////////////////////////////////////////////
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
                                   //objAjuAutSis.insertarRegAut(k);
                                   objAjuAutSis.insertarRegAut(k,strFecDocPag);
                                   /////objAjuAutSis.insertarRegAut(conAux, k, strFecDocPag);////version de prueba////
                               }
                               else
                               {
                                   p++;
                               }
                            }
                        }
                       ///////////////////////////////////////////////////////////////////////
                    }
                }

                consultarRegIns();
                
                generarODLocRem();
                
                

                conAux.close();
                conAux=null;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
                
            return true;
        }
        
        
        public boolean afterModificar() 
        {
            try
            {
                conAux=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                conAux.setAutoCommit(false);

                if(conAux!=null)
                {
                    blnHayCam=false;
                    /*Dario_---Para Generar Ajutes Automaticos---*/
                    if(verificarDatoAjuste())
                    {
                        double valor=0.00, sumval=0.00;
                        objAjuAutSis.mostrarDatIns();



                            for (int i=0;i<objTblModExc.getRowCountTrue();i++)
                            {
                                if (objTblModExc.isChecked(i, INT_TBL_DAT_SEL))
                                {
        //                            if(STRESTLIN.equals("M"))
        //                            {
                                        objAjuAutSis.mostrarDatIns();
        //                                objAjuAutSis.consultarReg();

                                       int codloc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_LOC));
                                       int codtipdoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_TIP_DOC));
                                       int coddoc = Integer.parseInt(""+objTblModExc.getValueAt(i,INT_TBL_DAT_COD_DOC));

                                       INTCODLOC = codloc;
                                       INTTIPDOC = codtipdoc;
                                       INTCODDOC = coddoc;
                                       cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                                       valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                                       sumval = sumval + valor;
                                       String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                                       ///////////////////////////////////////////////////////////////////////
                                       if(cant>0)
                                       {
                                           if(i==0)
                                           {
                                               k=i;
                                               k++;
                                           }
                                           else
                                               k=cant;
                                           //objAjuAutSis.insertarRegAut(k);
                                           objAjuAutSis.insertarRegAut(k,strFecDocPag);
                                           /////objAjuAutSis.insertarRegAut(conAux, k, strFecDocPag);////version de prueba////
                                       }
                                       else
                                       {

                                       }
        //                            }
                                }
                            }

                            for (int i=0;i<objTblModPnd.getRowCountTrue();i++)
                            {
                                if (objTblModPnd.isChecked(i, INT_TBL_DAT_SEL))
                                {
        //                            if(STRESTLIN.equals("M"))
        //                            {
                                        objAjuAutSis.mostrarDatIns();
        //                                objAjuAutSis.consultarReg();

                                       int codloc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_LOC));
                                       int codtipdoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_TIP_DOC));
                                       int coddoc = Integer.parseInt(""+objTblModPnd.getValueAt(i,INT_TBL_DAT_COD_DOC));

                                       INTCODLOC = codloc;
                                       INTTIPDOC = codtipdoc;
                                       INTCODDOC = coddoc;
                                       cant = objAjuAutSis.mostrarReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                                       valor = objAjuAutSis.rtnValReg(INTCODLOC, INTTIPDOC, INTCODDOC);
                                       sumval = sumval + valor;
                                       String strFecDocPag = objUti.formatearFecha(txtFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                                       ///////////////////////////////////////////////////////////////////////
                                       if(cant>0)
                                       {
                                           if(i==0)
                                           {
                                               k=i;
                                               k++;
                                           }
                                           else
                                               k=cant;
                                           //objAjuAutSis.insertarRegAut(k);
                                           objAjuAutSis.insertarRegAut(k,strFecDocPag);
                                           ////objAjuAutSis.insertarRegAut(conAux, k, strFecDocPag);////version de prueba////
                                       }
                                       else
                                       {

                                       }
        //                            }
                                }
                            }

        //                }
                    /////////////////////////////////////////////////////////////////////////////////////////
                    }
                }
                conAux.close();
                conAux=null;
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterEliminar() 
        {
            return true;
        }
        
        public boolean afterCancelar() {
            return true;
        }
        
        public boolean afterAnular() 
        {
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
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento estï¿½ ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            
            if (!isCamValMod())
                return false;
            
//            if (objAsiDia.getGeneracionDiario()==2)
//                return regenerarDiario();
            return true;
        }

        public boolean beforeEliminar()
        {
            /*Dario_---Para Generar Ajutes Automaticos---*/
            objAjuAutSis.mostrarDatIns();
            
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
            /*Dario_---Para Generar Ajutes Automaticos---*/
            objAjuAutSis.mostrarDatIns();
            
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento estï¿½ ELIMINADO.\nNo es posible anular un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento ya estï¿½ ANULADO.\nNo es posible anular un documento anulado.");
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
            return true;
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

/////////////////////////////////////////////////////////////////////////////
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    //camSelChk();
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_SEL){
                    }
                    else
                        if(tblValPnd.getEditingColumn()==INT_TBL_DAT_VAL_ABO){
                            //camValApl();//para q se active la celda del checkbox                        
                            if (objHilo==null) {
                                objHilo = new tblHilo();
                                objHilo.start();
                                objHilo = null;
                            }
                    }
                    if (tblValPnd.getEditingColumn()==INT_TBL_DAT_MON_DOC){
                        
                    }
                    break;
            }
        }
    }        
     
    class tblHilo extends Thread
    {
            public void run()
            {

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



}

