/*
 * ZafCom91.java
 *
 * Created on Marzo 9, 2015, 17:30 PM
 */
package Compras.ZafCom91;
import Compras.ZafCom92.ZafCom92;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafReaVenComAut.ZafReaVenComAut;
import Librerias.ZafRptSis.ZafRptSis;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author  José Marín
 */
public class ZafCom91 extends javax.swing.JInternalFrame
{
    //Constantes: Jtable
    private static final int INT_TBL_DAT_LIN=0;
    private static final int INT_TBL_DAT_COD_ITM=1;
    private static final int INT_TBL_DAT_COD_ALT_ITM=2;
    private static final int INT_TBL_DAT_COD_ALT_ITM_DOS=3;
    private static final int INT_TBL_DAT_BTN_ITM=4;
    private static final int INT_TBL_DAT_NOM_ITM=5;
    private static final int INT_TBL_DAT_UNI_MED=6;
    private static final int INT_TBL_DAT_CAN=7;
    private static final int INT_TBL_DAT_PES_UNI_ITM=8;
    private static final int INT_TBL_DAT_PES_TOT_ITM=9;
    private static final int INT_TBL_DAT_CAN_ACT_STK=10;
    
    // Constantes: Para el contenedor a enviar de solicitud de transferencia (Prefactura, Reservas) JoséMario 26/Abril/2015.
    final int INT_ARL_SOL_TRA_COD_BOD_ING=0;
    final int INT_ARL_SOL_TRA_COD_BOD_EGR=1;
    final int INT_ARL_SOL_TRA_ITM_GRP=2;
    final int INT_ARL_SOL_TRA_CAN=3;
    final int INT_ARL_SOL_PES_UNI=4;
    final int INT_ARL_SOL_PES_TOT=5;
    
    //Constantes: Para el contenedor a enviar de solicitud de transferencia (Cotizaciones de Venta) JoséMario 26/Abril/2015.
    final int INT_ARL_COT_VEN_COD_EMP=0;
    final int INT_ARL_COT_VEN_COD_LOC=1;
    final int INT_ARL_COT_VEN_COD_TIP_DOC=2;
    final int INT_ARL_COT_VEN_COD_DOC=3;
    final int INT_ARL_COT_VEN_COD_BOD_EGR=4;
    
    // Constantes: Para el contenedor solicitud de transferencia que llegara desde otro programa (Solicitudes Transferencias Importaciones)(ZafImp21).
    private ArrayList arlDatSolTraInv, arlRegSolTraInv;
    private static final int INT_ARL_GEN_DAT_SOL_PK_INGIMP=0;
    private static final int INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP=1;
    private static final int INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP=2;
    private static final int INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP=3;
    private static final int INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP=4;
    private static final int INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP=5;
    private static final int INT_ARL_GEN_DAT_SOL_ITM_GRP=6;
    private static final int INT_ARL_GEN_DAT_SOL_CAN=7;
    private static final int INT_ARL_GEN_DAT_SOL_PES_UNI=8;
    private static final int INT_ARL_GEN_DAT_SOL_PES_UNITOT=9;
    
    // Constantes: Para el contenedor solicitud de transferencia que llegara desde otro programa (Reposicion).
    final int INT_ARL_GEN_SOL_TRA_ITM_GRP=0;
    final int INT_ARL_GEN_SOL_TRA_CAN=1;
    final int INT_ARL_GEN_SOL_PES_UNI=2;
    final int INT_ARL_GEN_SOL_PES_TOT=3;
    final int INT_ARL_GEN_SOL_PES_TOT_SOLTRA=4;
    
    // Constantes: Contenedor pk Reposicion de Inventario de Bodega
    private static final int INT_ARL_GEN_REINBO_CODEMP=0;
    private static final int INT_ARL_GEN_REINBO_CODLOC=1;
    private static final int INT_ARL_GEN_REINBO_CODTIPDOC=2;
    private static final int INT_ARL_GEN_REINBO_CODDOC=3;
        
    //ArrayList para consultar
    private ArrayList arlDatConSol, arlRegConSol;
    private static final int INT_ARL_CON_SOL_TRA_COD_EMP=0;  
    private static final int INT_ARL_CON_SOL_TRA_COD_LOC=1;   
    private static final int INT_ARL_CON_SOL_TRA_COD_TIPDOC=2;  
    private static final int INT_ARL_CON_SOL_TRA_COD_DOC=3;  
    private int intIndiceSolTra=0;
    

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private MiToolBar objTooBar;
    private ZafDocLis objDocLis;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;                    //Render: Presentar JButton en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm;     //Editor: TextField en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCodAltItm2;    //Editor: TextField en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafDatePicker dtpFecDoc;                            //Fecha
    private ZafRptSis objRptSis;                                //Reportes del Sistema.
    private ZafThreadGUI objThrGUI;
    private ZafPerUsr objPerUsr;                                //Permisos Usuarios.
    
    //Para las Ventanas de Consulta
    private ZafVenCon vcoTipDoc;                                // tipo de documento
    private ZafVenCon vcoBodOrg;                                // bodega de origen 
    private ZafVenCon vcoBodDes;                                // bodega de destino 
    private ZafVenCon vcoItm;                                   // Items 
    
    private Date datFecAux;
    private Date datFecDoc;  
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private boolean blnHayCam;
    private Vector vecDat, vecReg, vecCab, vecAux;
    
    private int intCodBodOrg=0;
    private int intCodEmpSolTra;
    private int intCodLocSolTra;
    
    private String strSQL;
    private String strAux;
    private String strDocCod;
    private String strCodBodOrg;
    private String strCodBodDes;
    private String strNomBodOrg;
    private String strNomBodDes;
    private String strDocNom;
    private String strDesCorTipDoc;
    
    private int intCodEmpIngImpLoc;
    private int intCodLocIngImpLoc;
    private int intCodTipDocIngImpLoc;
    private int intCodDocIngImpLoc;
    private String strNumPedEmbImp="";
    
    private int intCodEmpRepInv;
    private int intCodLocRepInv;
    private int intCodTipDocRepInv;
    private int intCodDocRepInv;
    
    private String strVersion=" v0.2.2";   
    
    /** Creates new form ZafCom91 */
    public ZafCom91(ZafParSis obj) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
                        
            //AQUI SE VALIDA QUE SOLO SE PUEDA ABRIR POR GRUPO 
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){  
                initComponents();
                configurarFrm();
                agregarDocLis();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            } 
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }
    private int intIsCotVen=0;
    
    /* Ventana de Cotizaciones de Venta */
    public ZafCom91(ZafParSis obj, int j) 
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
             objUti=new ZafUtil();
            intCodEmpSolTra = objParSis.getCodigoEmpresa();
            intCodLocSolTra = objParSis.getCodigoLocal();
            intIsCotVen=j;
            
            initComponents();
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 
//            configurarFrm();
//            agregarDocLis();
            
        }
        catch (CloneNotSupportedException e){      this.setTitle(this.getTitle() + " [ERROR]");      }
    }
    
    /* Constructor para Botón Consultar Solicitud de Transferencia de Inventario */
    public ZafCom91(ZafParSis obj, String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) 
    {
        try
        {
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText("Solicitudes de Transferencia de Inventario");

            intCodEmpSolTra = Integer.parseInt(strCodEmp);
            intCodLocSolTra = Integer.parseInt(strCodLoc);
            txtCodTipDoc.setText(strCodTipDoc);
            txtCodDoc.setText(strCodDoc);
            
            panBar.setVisible(false);
            consultarReg();
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    }
     
    /**
     * Constructor utilizado como HashMap para usarlo cuando se llama a la clase en forma dinamica.
     * @author Rosa Zambrano
     * @fecha 12/Jun/2017
     * @param map 
     */
    public ZafCom91(HashMap map) 
    {
        ZafParSis obj;
        Integer codigoEmp, codigoLoc;
        Integer codigoTipDoc, codigoDoc;
        
        obj = (ZafParSis) map.get("objParSis");
        codigoEmp = Integer.valueOf(new String(map.get("strCodEmp").toString()));
        codigoLoc = Integer.valueOf(new String(map.get("strCodLoc").toString()));
        codigoTipDoc = Integer.valueOf(new String(map.get("strCodTipDoc").toString()));
        codigoDoc = Integer.valueOf(new String(map.get("strCodDoc").toString()));
        
        try
        {
            //this.objParSis = (ZafParSis) obj.clone();
            this.objParSis = (ZafParSis) obj.clone();
            
            initComponents();
            configurarFrm();
            
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText("Solicitudes de Transferencia de Inventario");

            intCodEmpSolTra = codigoEmp;
            intCodLocSolTra = codigoLoc;
            txtCodTipDoc.setText(""+codigoTipDoc);
            txtCodDoc.setText(""+codigoDoc);
            
            panBar.setVisible(false);
            consultarReg();
        }
        catch (CloneNotSupportedException e) {        objUti.mostrarMsgErr_F1(this, e);      }
    }    
     

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtNomTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBodOrg = new javax.swing.JLabel();
        txtCodBodOrg = new javax.swing.JTextField();
        txtNomBodOrg = new javax.swing.JTextField();
        butBodOrg = new javax.swing.JButton();
        lblBodDes = new javax.swing.JLabel();
        txtCodBodDes = new javax.swing.JTextField();
        txtNomBodDes = new javax.swing.JTextField();
        butBodDes = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblPeskgr = new javax.swing.JLabel();
        txtPesKgr = new javax.swing.JTextField();
        panDet = new javax.swing.JPanel();
        panTabDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panLblObs = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panTxtObs = new javax.swing.JPanel();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setMinimumSize(new java.awt.Dimension(148, 113));

        panFil.setPreferredSize(new java.awt.Dimension(0, 330));
        panFil.setLayout(new java.awt.BorderLayout());

        panCab.setMinimumSize(new java.awt.Dimension(0, 0));
        panCab.setPreferredSize(new java.awt.Dimension(0, 95));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(5, 10, 130, 20);

        txtCodTipDoc.setEnabled(false);
        txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTipDocActionPerformed(evt);
            }
        });
        txtCodTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTipDocFocusLost(evt);
            }
        });
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(120, 10, 20, 20);

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
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(140, 10, 53, 20);

        txtNomTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTipDocActionPerformed(evt);
            }
        });
        txtNomTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTipDocFocusLost(evt);
            }
        });
        panCab.add(txtNomTipDoc);
        txtNomTipDoc.setBounds(193, 10, 210, 20);

        butTipDoc.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(403, 10, 20, 20);

        lblBodOrg.setText("Bodega Origen:");
        panCab.add(lblBodOrg);
        lblBodOrg.setBounds(5, 30, 130, 20);

        txtCodBodOrg.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodOrgActionPerformed(evt);
            }
        });
        txtCodBodOrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodOrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodOrgFocusLost(evt);
            }
        });
        panCab.add(txtCodBodOrg);
        txtCodBodOrg.setBounds(140, 30, 53, 20);

        txtNomBodOrg.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtNomBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodOrgActionPerformed(evt);
            }
        });
        txtNomBodOrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodOrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodOrgFocusLost(evt);
            }
        });
        panCab.add(txtNomBodOrg);
        txtNomBodOrg.setBounds(193, 30, 210, 20);

        butBodOrg.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butBodOrg.setText("...");
        butBodOrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodOrgActionPerformed(evt);
            }
        });
        panCab.add(butBodOrg);
        butBodOrg.setBounds(403, 30, 20, 20);

        lblBodDes.setText("Bodega Destino:");
        panCab.add(lblBodDes);
        lblBodDes.setBounds(5, 50, 130, 20);

        txtCodBodDes.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodDesActionPerformed(evt);
            }
        });
        txtCodBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodDesFocusLost(evt);
            }
        });
        panCab.add(txtCodBodDes);
        txtCodBodDes.setBounds(140, 50, 53, 20);

        txtNomBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodDesActionPerformed(evt);
            }
        });
        txtNomBodDes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodDesFocusLost(evt);
            }
        });
        panCab.add(txtNomBodDes);
        txtNomBodDes.setBounds(193, 50, 210, 20);

        butBodDes.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        butBodDes.setText("...");
        butBodDes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodDesActionPerformed(evt);
            }
        });
        panCab.add(butBodDes);
        butBodDes.setBounds(403, 50, 20, 20);

        lblCodDoc.setText("Código del documento:");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(5, 70, 130, 20);

        txtCodDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCodDoc.setEnabled(false);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(140, 70, 90, 20);

        lblFecDoc.setText("Fecha del documento:");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(425, 10, 140, 20);

        lblNumDoc.setText("Número del documento:");
        panCab.add(lblNumDoc);
        lblNumDoc.setBounds(425, 30, 150, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(575, 30, 90, 20);

        lblPeskgr.setText("Peso (Kg):");
        panCab.add(lblPeskgr);
        lblPeskgr.setBounds(425, 50, 150, 20);

        txtPesKgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtPesKgr);
        txtPesKgr.setBounds(575, 51, 90, 20);

        panFil.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

        panTabDet.setLayout(new java.awt.BorderLayout());

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
        spnDet.setViewportView(tblDat);

        panTabDet.add(spnDet, java.awt.BorderLayout.CENTER);

        panDet.add(panTabDet, java.awt.BorderLayout.CENTER);

        panFil.add(panDet, java.awt.BorderLayout.CENTER);

        panGenTot.setMinimumSize(new java.awt.Dimension(70, 30));
        panGenTot.setPreferredSize(new java.awt.Dimension(34, 58));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panLblObs.setPreferredSize(new java.awt.Dimension(100, 30));
        panLblObs.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panLblObs.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panLblObs.add(lblObs2);

        panGenTot.add(panLblObs, java.awt.BorderLayout.WEST);

        panTxtObs.setPreferredSize(new java.awt.Dimension(579, 70));
        panTxtObs.setLayout(new java.awt.GridLayout(0, 1));

        txaObs1.setLineWrap(true);
        txaObs1.setPreferredSize(new java.awt.Dimension(104, 25));
        txaObs1.setRequestFocusEnabled(false);
        spnObs1.setViewportView(txaObs1);

        panTxtObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        txaObs2.setPreferredSize(new java.awt.Dimension(104, 25));
        spnObs2.setViewportView(txaObs2);

        panTxtObs.add(spnObs2);

        panGenTot.add(panTxtObs, java.awt.BorderLayout.CENTER);

        panFil.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panFil);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("General");

        panBar.setPreferredSize(new java.awt.Dimension(0, 58));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
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
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        if(objTooBar.getEstado()=='c'){

        }
        else{
            clickCambiaTipoDocumento();
        }
        
        strDocCod=txtCodTipDoc.getText();
        mostrarDocumentos(0);
        
        /* JoséMario 18/Agosto/2016 */
        configurarVenBodOrg();
        configurarVenBodDes();
    }//GEN-LAST:event_butTipDocActionPerformed

    private void txtNomTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusLost
        if( ! (objTooBar.getEstado()=='c' || objTooBar.getEstado()=='l'))
        {
            limpiarFrm();
            clickCambiaTipoDocumento();
        }
        if (!txtNomTipDoc.getText().equalsIgnoreCase(strDocNom))
        {
            if (txtNomTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtNomTipDoc.setText("");
            }
            else
            {
                mostrarDocumentos(2);
            }
        }
        else{
            txtNomTipDoc.setText(strDocNom);
        }
        /* JoséMario 18/Agosto/2016 */
        txtCodBodOrg.setText("");
        txtCodBodDes.setText("");
        configurarVenBodOrg();
        configurarVenBodDes();
        
    }//GEN-LAST:event_txtNomTipDocFocusLost

    private void txtNomTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTipDocFocusGained
        strDocNom=txtNomTipDoc.getText();
        txtNomTipDoc.selectAll();
    }//GEN-LAST:event_txtNomTipDocFocusGained

    private void txtNomTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTipDocActionPerformed
        txtNomTipDoc.transferFocus();
    }//GEN-LAST:event_txtNomTipDocActionPerformed

    private void txtCodTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipDocFocusLost
        if (!txtCodTipDoc.getText().equalsIgnoreCase(strDocCod)){
            if (txtCodTipDoc.getText().equals("")){
                txtCodTipDoc.setText("");
                txtNomTipDoc.setText("");
            }
            else
            mostrarDocumentos(1);
        }
        else
        txtCodTipDoc.setText(strDocCod);
    }//GEN-LAST:event_txtCodTipDocFocusLost

    private void txtCodTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTipDocFocusGained
        strDocCod=txtCodTipDoc.getText();
        txtCodTipDoc.selectAll();
    }//GEN-LAST:event_txtCodTipDocFocusGained

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        txtCodTipDoc.transferFocus();
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void txtCodBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodOrgActionPerformed
        txtCodBodOrg.transferFocus();
    }//GEN-LAST:event_txtCodBodOrgActionPerformed

    private void txtCodBodOrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOrgFocusGained
        strCodBodOrg=txtCodBodOrg.getText();
        txtCodBodOrg.selectAll();
    }//GEN-LAST:event_txtCodBodOrgFocusGained

    private void txtCodBodOrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodOrgFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodOrg.setText("");
            txtNomBodOrg.setText("");
        }
        else
        {
            if (!txtCodBodOrg.getText().equalsIgnoreCase(strCodBodOrg)) 
            {
                if (txtCodBodOrg.getText().equals(""))
                {
                    txtCodBodOrg.setText("");
                    txtNomBodOrg.setText("");
                } 
                else 
                {
                    configurarVenBodOrg();
                    mostrarBodegaOrigen(1);
                    limpiarTabla();
                }
            } 
            else
                txtCodBodOrg.setText(strCodBodOrg);
         } 
        
        if(txtCodBodOrg.getText().length()>0){
            isBodegaOrigen();
        }
//            if(txtCodBodOrg.getText().length()>0){
//                configurarVenConItm();
//            }
         
         
    }//GEN-LAST:event_txtCodBodOrgFocusLost

    private void txtNomBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodOrgActionPerformed
        txtNomBodOrg.transferFocus();
    }//GEN-LAST:event_txtNomBodOrgActionPerformed

    private void txtNomBodOrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOrgFocusGained
        strNomBodOrg=txtNomBodOrg.getText();
        txtNomBodOrg.selectAll();
    }//GEN-LAST:event_txtNomBodOrgFocusGained

    private void txtNomBodOrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodOrgFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodOrg.setText("");
            txtNomBodOrg.setText("");
        }
        else
        {
            if (!txtNomBodOrg.getText().equalsIgnoreCase(strNomBodOrg)) 
            {
                if (txtNomBodOrg.getText().equals("")) 
                {
                    txtCodBodOrg.setText("");
                    txtNomBodOrg.setText("");
                } 
                else 
                {
                    configurarVenBodOrg();
                    mostrarBodegaOrigen(2);
                    limpiarTabla();
                }
            }
            else
                txtNomBodOrg.setText(strNomBodOrg);
        } 
        
        if(txtCodBodOrg.getText().length()>0){
            isBodegaOrigen();
        }
   
    }//GEN-LAST:event_txtNomBodOrgFocusLost

    private void butBodOrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodOrgActionPerformed
        if (txtCodTipDoc.getText().equals("")) 
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodOrg.setText("");
            txtNomBodOrg.setText("");
        }
        else
        {
            strCodBodOrg=txtCodBodOrg.getText();
            configurarVenBodOrg();
            mostrarBodegaOrigen(0);
        
            if (! txtCodBodOrg.getText().equals(strCodBodOrg)) {
                limpiarTabla();
            }

            if(txtCodBodOrg.getText().length()>0){
                isBodegaOrigen();
            }
        }
        
    }//GEN-LAST:event_butBodOrgActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        if( ! (objTooBar.getEstado()=='c' || objTooBar.getEstado()=='l'))
        {
            limpiarFrm();
            clickCambiaTipoDocumento();
        }
        
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtNomTipDoc.setText("");
            }
            else
            {
                mostrarDocumentos(1);
            }
        }
        else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }     
        /* JoséMario 18/Agosto/2016 */
        configurarVenBodOrg();
        configurarVenBodDes();
        
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtCodBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodDesActionPerformed
        txtCodBodDes.transferFocus(); 
    }//GEN-LAST:event_txtCodBodDesActionPerformed

    private void txtCodBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusGained
        strCodBodDes=txtCodBodDes.getText();
        txtCodBodDes.selectAll();
    }//GEN-LAST:event_txtCodBodDesFocusGained

    private void txtCodBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else{
            if (!txtCodBodDes.getText().equalsIgnoreCase(strCodBodDes)){
                if (txtCodBodDes.getText().equals("")){
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                } 
                else{
                    configurarVenBodDes();
                    mostrarBodegaDestino(1);
                }
            } 
            else{
                txtCodBodDes.setText(strCodBodDes);
            }
         } 
//        configurarVenConItm();
    }//GEN-LAST:event_txtCodBodDesFocusLost

    private void txtNomBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodDesActionPerformed
         txtNomBodDes.transferFocus();
    }//GEN-LAST:event_txtNomBodDesActionPerformed

    private void txtNomBodDesFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusGained
        strNomBodDes=txtNomBodDes.getText();
        txtNomBodDes.selectAll();
    }//GEN-LAST:event_txtNomBodDesFocusGained

    private void txtNomBodDesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodDesFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else{
            if (!txtNomBodDes.getText().equalsIgnoreCase(strNomBodDes)){
                if (txtNomBodDes.getText().equals("")){
                    txtCodBodDes.setText("");
                    txtNomBodDes.setText("");
                }
                else {
                    configurarVenBodDes();
                    mostrarBodegaDestino(2);
                }
            }
            else
                txtNomBodDes.setText(strNomBodDes);
        } 
    }//GEN-LAST:event_txtNomBodDesFocusLost

    private void butBodDesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodDesActionPerformed
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
        }
        else
        {
            strCodBodDes=txtCodBodDes.getText();
            configurarVenBodDes();
            mostrarBodegaDestino(0);
        
            //if (! txtCodBodDes.getText().equals(strCodBodDes)) 
            //    limpiarTabla();
        }
    }//GEN-LAST:event_butBodDesActionPerformed

    //<editor-fold defaultstate="collapsed" desc="/* Variables declaration - do not modify */">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBodDes;
    private javax.swing.JButton butBodOrg;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBodDes;
    private javax.swing.JLabel lblBodOrg;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPeskgr;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panLblObs;
    private javax.swing.JPanel panTabDet;
    private javax.swing.JPanel panTxtObs;
    private javax.swing.JScrollPane spnDet;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBodDes;
    private javax.swing.JTextField txtCodBodOrg;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtNomBodDes;
    private javax.swing.JTextField txtNomBodOrg;
    private javax.swing.JTextField txtNomTipDoc;
    private javax.swing.JTextField txtNumDoc;
    private javax.swing.JTextField txtPesKgr;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
    
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            
            //Obtener los permisos por Usuario y Programa.
            objPerUsr=new ZafPerUsr(objParSis);
            
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);  //Titulo y version
            lblTit.setText(strAux);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleModificar(false);  // EN LA BARRA DE OPCIONES NO SE MOSTRARA EL BOTON DE MODIFICAR 
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            
            //Configurar ZafDatePicker: Para la fecha 
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(575, 10, 90, 20);
            dtpFecDoc.setEnabled(false);  /// TEMPORAL!!! 
            //fin config zafDatePicker
            
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());  
            txtNomTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodOrg.setBackground(objParSis.getColorCamposObligatorios());  // AZUL
            txtNomBodOrg.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBodDes.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtPesKgr.setBackground(objParSis.getColorCamposSistema());          // AMARILLO
            txtCodTipDoc.setVisible(false);                                      //no estaba en el diseño de la pantalla 
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3984)) 
            {
                objTooBar.setVisibleInsertar(false);
            }
            if (!objPerUsr.isOpcionEnabled(3985)) 
            {
                objTooBar.setVisibleConsultar(false);
            }
            if (!objPerUsr.isOpcionEnabled(3989)) 
            {
                objTooBar.setVisibleImprimir(false);
            }
            if (!objPerUsr.isOpcionEnabled(3990)) 
            {
                objTooBar.setVisibleVistaPreliminar(false);
            }
            
            configurarVenBodDes();
            configurarVenBodOrg();
            configurarTipoDocumento();
            configurarVenConItm();
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
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"Cód.Itm");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM_DOS,"Cód.Alt.2");
            vecCab.add(INT_TBL_DAT_BTN_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre del Item");
            vecCab.add(INT_TBL_DAT_UNI_MED,"Unidad");
            vecCab.add(INT_TBL_DAT_CAN,"Cantidad");
            vecCab.add(INT_TBL_DAT_PES_UNI_ITM,"Pes.Uni.(Kg)");
            vecCab.add(INT_TBL_DAT_PES_TOT_ITM,"Pes.Tot.(Kg)");
            vecCab.add(INT_TBL_DAT_CAN_ACT_STK,"stock");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(245);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafCom91.ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            //Configurar JTable: Alineamiento de columnas, formato a las columnas para los decimales por ejemplo
            objTblCelRenLblNum=new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_UNI_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_PES_TOT_ITM).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ACT_STK).setCellRenderer(objTblCelRenLblNum);  // JoséMarín: NO se lo esta mostrando, se deja configurado por si deciden mostrar
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.CENTER);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM_DOS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN);
            vecAux.add("" + INT_TBL_DAT_BTN_ITM);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
             //columnas ocultas
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_DAT_COD_ITM);
            arlColHid.add(""+INT_TBL_DAT_CAN_ACT_STK);  // 
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            //botones agregados, hacer una columna de botones 
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            //setEditable(false);
            
            //new
            int intColVen[]=new int[7];
            intColVen[0]=1; //co_itm
            intColVen[1]=2; //tx_codAlt
            intColVen[2]=3; //tx_codAlt2
            intColVen[3]=4; //tx_nomItm
            intColVen[4]=5; //tx_desCor
            intColVen[5]=6; //nd_pesItmKgr
            intColVen[6]=7; //nd_canDis
            int intColTbl[]=new int[7];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[2]=INT_TBL_DAT_COD_ALT_ITM_DOS;
            intColTbl[3]=INT_TBL_DAT_NOM_ITM;
            intColTbl[4]=INT_TBL_DAT_UNI_MED;
            intColTbl[5]=INT_TBL_DAT_PES_UNI_ITM;
            intColTbl[6]=INT_TBL_DAT_CAN_ACT_STK;
            
            //Cod.Alt.
            objTblCelEdiTxtVcoCodAltItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoCodAltItm);
            objTblCelEdiTxtVcoCodAltItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {  
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
                        txtCodBodOrg.requestFocus();      
                    }
                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
                    {
                        objTblCelEdiTxtVcoCodAltItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
                        txtCodBodDes.requestFocus();
                    }
                }
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                     int intCelSel = tblDat.getSelectedRow();
                     if(!objTblMod.isRowEmpty(intCelSel)){
                        if (objTblCelEdiTxtVcoCodAltItm.isConsultaAceptada()) {
                            if (!existeMismoItem(intCelSel)) { /*JM*/
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
                            }
                        }
                    }
                }
            });
            
            //Boton ítem.
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                @Override
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                }   
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
                    {
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
                        limpiarTabla();
                        txtCodBodOrg.requestFocus();
                    }
                    else if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
                    {
                        objTblCelEdiButVcoItm.setCancelarEdicion(true);
                        mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
                        limpiarTabla();
                        txtCodBodDes.requestFocus();
                    }
                }
                
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                     int intCelSel = tblDat.getSelectedRow();
                     if(!objTblMod.isRowEmpty(intCelSel)){
                        if (objTblCelEdiButVcoItm.isConsultaAceptada()) {
                            if (!existeMismoItem(intCelSel)) { /*JM*/
                                objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
                            }
                        }
                    }
                }
            });
                
            intColVen=null;
            intColTbl=null;
            
            //Cantidad
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() 
            {
                Double dblCantidad,dblCostoUnitario,dblPesoUnitario,dblCostoTotal,dblPesoTotal;
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) 
                {
                    int intCelSel = tblDat.getSelectedRow();
                    if(objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))>0){
                        if (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN))<=
                            (objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN_ACT_STK))))
                        {
                            dblCantidad=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_CAN));
                            dblPesoUnitario=objUti.parseDouble(objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM)==null?0.00:objTblMod.getValueAt(intCelSel, INT_TBL_DAT_PES_UNI_ITM));
                            dblPesoTotal=objUti.redondear(dblCantidad*dblPesoUnitario,objParSis.getDecimalesMostrar());
                            objTblMod.setValueAt(dblPesoTotal, intCelSel, INT_TBL_DAT_PES_TOT_ITM);
                            recalcularPeso();
                            //objTblMod.insertRow();
                            objTblEdi.seleccionarCelda(tblDat.getSelectedRow()+1, INT_TBL_DAT_COD_ALT_ITM);
                        }
                        else
                        {
                            mostrarMsgInf("La cantidad solicitada es menor a lo disponible");
                            objTblMod.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_CAN);
                            objTblEdi.seleccionarCelda(INT_TBL_DAT_CAN);
                        }
                    }
                                         
                }
            });
            //objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objDocLis=new ZafDocLis();
            
            // Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis=new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);  
            
        }
        catch(Exception e)   {  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);     }
        return blnRes;
    }
      
    /**
     * Esta función valida que no se repita el item ingresado.
     * Si el item existe muestra un mensaje y lo borra para evitar problemas de stock.
     * Solicitado por Ingrid Lino 14/Junio/2017 
     * @param intFilSel
     * @return true: Si el item existe.
     * <BR>false: En el caso contrario.
     */
    private boolean existeMismoItem(int intFilSel){
        boolean blnRes = false;
        for (int i=0; i<objTblMod.getRowCountTrue(); i++){
            if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM) != null){
                if (i != intFilSel){
                    if (tblDat.getValueAt(i, INT_TBL_DAT_COD_ITM).toString().equals(tblDat.getValueAt(intFilSel, INT_TBL_DAT_COD_ITM).toString())){
                        blnRes = true;
                        mostrarMsgInf("<HTML>El item ya fue ingresado en la fila " + (i+1) + ".</HTML>");
                        objTblMod.removeRow(intFilSel);
                        tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                        objTblEdi.seleccionarCelda(INT_TBL_DAT_COD_ALT_ITM);
                        break;
                    }
                }
            }
        }
        return blnRes;
    }
    
//    private boolean removerFila(int Row){
//        boolean blnRes=false;
//        try{
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_COD_ITM);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_COD_ALT_ITM);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_COD_ALT_ITM_DOS);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_NOM_ITM);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_UNI_MED);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_PES_UNI_ITM);
//            objTblMod.setValueAt(null, Row, INT_TBL_DAT_CAN_ACT_STK);
//            blnRes=true;
//        }
//        catch (Exception e) {    
//            objUti.mostrarMsgErr_F1(this, e);   
//            blnRes=false;
//        }
//        return blnRes;
//    }
    
    public void recalcularPeso(){
        try
        {
            Double dblPeso = 0.00 ,dblPesoTotal = 0.00;
            int intNumFil = objTblMod.getRowCountTrue();
            for(int i=0;i<intNumFil;i++){
                dblPeso = objUti.redondear(Double.parseDouble(objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT_ITM)==null?"0.00":objTblMod.getValueAt(i, INT_TBL_DAT_PES_TOT_ITM).toString()), objParSis.getDecimalesMostrar());
                dblPesoTotal = dblPesoTotal + dblPeso;
            }
            txtPesKgr.setText(String.valueOf(dblPesoTotal));
        } 
        catch (Exception e) {    
            objUti.mostrarMsgErr_F1(this, e);   
        }
    }
    
    
    private String getSQLBodOrg(){
        String strCadena="";
        strCadena+=" SELECT a3.co_itm, a3.tx_codAlt, a3.tx_codAlt2, a3.tx_nomItm, a4.tx_desCor, \n";
        strCadena+="        CASE WHEN a3.nd_pesItmKgr IS NULL THEN 0 ELSE ROUND(a3.nd_pesItmKgr,2) END AS nd_pesItmKgr, \n";
        strCadena+="        CASE WHEN a3.nd_cosUni IS NULL THEN 0 ELSE ROUND(a3.nd_cosUni,2) END as nd_cosUni, \n";
        strCadena+="        ROUND(a1.nd_canDis,2) as nd_canDis,a3.st_ser  \n";
        strCadena+=" FROM \n";
        strCadena+=" ( \n";
        strCadena+="       SELECT z.co_empGrp AS co_emp, z.co_itmMae, SUM(z.nd_canDis) AS nd_canDis \n";
        strCadena+="       FROM( \n";
        strCadena+="           SELECT a1.co_emp, a1.co_itm, a1.co_bod, a1.nd_canDis, a2.co_itmMae , a3.co_empGrp   \n";
        strCadena+="           FROM tbm_invBod AS a1  \n";
        strCadena+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
        strCadena+="           INNER JOIN tbr_bodEmpBodGrp as a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)  \n";
        strCadena+="           WHERE a3.co_empGrp="+objParSis.getCodigoEmpresaGrupo()+"  ";
        if(txtCodBodOrg.getText().length()>0){  // Jota: Deberia ser por 
            strCadena+=" AND a3.co_bodGrp="+txtCodBodOrg.getText() + " \n";
        }            
        strCadena+="       ) AS z  \n";
        strCadena+="       GROUP BY z.co_empGrp, z.co_itmMae  \n";
        strCadena+=" ) as a1  \n";
        strCadena+=" INNER JOIN tbm_equInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itmMae=a2.co_itmMae) \n";
        strCadena+=" INNER JOIN tbm_inv as a3 ON (a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm) \n";
        strCadena+=" LEFT OUTER JOIN tbm_var as a4 ON (a3.co_uni=a4.co_reg) \n";
        strCadena+=" WHERE a3.tx_codAlt NOT LIKE ('%L') AND a3.st_ser='N' \n";
        strCadena+="  \n";
        //System.out.println("getSQLBodOrg:" + strCadena);
        return strCadena;        
    }
    
    public void isBodegaOrigen() 
    {
        if(txtCodBodOrg.getText().length()>0){
            if(intCodBodOrg != Integer.parseInt(txtCodBodOrg.getText()) ){  // SI ES OTRA BODEGA 
                vcoItm.limpiar();
                vcoItm.setSentenciaSQL(getSQLBodOrg());
                intCodBodOrg=Integer.parseInt(txtCodBodOrg.getText());
            }
        }
    }

    public void setEditable(boolean editable) 
    {
        if (editable == true) 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_INS);
        else 
            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_NO_EDI);
    }

    /**
     * Funcion para validar que tengan bodegas ingresadas antes de 
     * poder elegir los Items, pues los items se cargaran segun la bodega de Origen
     * Jota
     */
//    public boolean validaBodegas() 
//    {
//        boolean blnRes = true;
//        setEditable(true);
//        if (!(txtCodBodOrg.getText().length() > 0))  //Si no tiene Bodega de origen 
//        {
//            mostrarMsgInf("La Bodega de Origen es Obligatoria para poder elegir los Items");
//            blnRes = false;
//            tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM);
//            txtCodBodOrg.requestFocus();
//            setEditable(false);
//        }
//        else
//        {
//            if (!(txtCodBodDes.getText().length() > 0)) //Si no tiene Bodega de destino 
//            { 
//                mostrarMsgInf("La Bodega de Destino es Obligatoria para poder elegir los Items");
//                blnRes = false;
//                tblDat.setValueAt("", tblDat.getSelectedRow(), INT_TBL_DAT_COD_ALT_ITM);
//                txtCodBodDes.requestFocus();
//                setEditable(false);
//            }
//        }
//        return blnRes;
//    }
    
    
 
    private void limpiarTabla() 
    {
        objTblMod.setDataModelChanged(false);
        objTblMod.removeAllRows();
    }

    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt)        {
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtNomTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBodOrg.getDocument().addDocumentListener(objDocLis);
        txtNomBodOrg.getDocument().addDocumentListener(objDocLis);
        txtCodBodDes.getDocument().addDocumentListener(objDocLis);
        txtNomBodDes.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtPesKgr.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
    } 
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public void clickInicio() 
        {
            try{
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=0;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() 
        {
            try{
                if(arlDatConSol.size()>0){
                    if(intIndiceSolTra>0){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() 
        {
            try{
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra < arlDatConSol.size()-1){
                        if (blnHayCam || objTblMod.isDataModelChanged()) {
                            if (isRegPro()) {
                                intIndiceSolTra++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() 
        {
            try{
                 if(arlDatConSol.size()>0){
                    if(intIndiceSolTra<arlDatConSol.size()-1){
                        if (blnHayCam) {
                            if (isRegPro()) {
                                intIndiceSolTra=arlDatConSol.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndiceSolTra=arlDatConSol.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() 
        {
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecDoc.setEnabled(false);
                datFecAux=null;
                mostrarTipDocPre();
                configurarVenBodOrg();
                configurarVenBodDes();
                //configurarVenConItm();
                txtCodBodOrg.requestFocus();     
                txtNumDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtPesKgr.setEditable(false);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                objTblMod.setDataModelChanged(false);
                blnHayCam=false;
            }
            catch (Exception e){          
                objUti.mostrarMsgErr_F1(this, e);          
            }
        }

        public void clickConsultar() 
        {   
            System.out.println("clickConsultar");
            switch (objTooBar.getEstado())
            {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtDesCorTipDoc.requestFocus();
                    break;
                case 'j':
                    break;
            }
            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            txtNumDoc.setEditable(true);
            dtpFecDoc.setEnabled(true);  /// TEMPORAL!!! 
            blnHayCam=false;
        }

        public void clickModificar() {

        }

        public void clickEliminar() {
            
        }

        public void clickAnular() {
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {
        }

        public boolean insertar() {
            if(validaTipoDocumento()){
                if (!insertarReg())
                    return false;
            }else{
                mostrarMsgInf("Solo se pueden Insertar Documentos, con el tipo de documento SOTRIN: Solicitudes de transferencias de inventario. ");
                return false;
            }
            return true;
        }

        public boolean consultar() {
            if (!consultarReg())
                return false;
            return true;
        }
        
        public boolean modificar() {
            return true;
        }

        public boolean eliminar() {
            try
            {
                if (!eliminarReg())
                    return false;
                 
                //Desplazarse al siguiente registro si es posible.
                if(arlDatConSol.size()>0){
                   if(intIndiceSolTra < arlDatConSol.size()-1){
                        intIndiceSolTra++;
                        cargarReg();
                   }
                   else
                   {
                       objTooBar.setEstadoRegistro("Eliminado");
                       limpiarFrm();
                   }
                }
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            blnHayCam=false;
            return true;
        }

        public boolean anular() {
            System.out.println("anular");
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
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

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try
            {
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                objTblMod.clearRowHeaderRaise();
                limpiarFrm();
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
            return blnRes;
        }

        public boolean beforeInsertar() {
             if (!isCamVal())
                return false;
            if (objTblMod.getRowCountTrue()==0)
            {
                if (mostrarMsgCon("El documento no tiene ninguna fila en el detalle.\n¿Esta seguro que desea INSERTAR el documento?", false)==javax.swing.JOptionPane.NO_OPTION)
                    return false;
            }
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            return blnRes;
        }

        public boolean beforeEliminar() {
          strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya está ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
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

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() 
        {
            System.out.println("afterInsertar");
            blnHayCam=false;
            objTooBar.setEstado('w');
            datFecDoc=objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy");
            objTblMod.initRowsState();
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterEliminar() {
            blnHayCam=false;
            return true;
        }

        public boolean afterAnular() {
            cargarReg();
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
            return true;
        }
        
        public boolean beforeClickInicio()
        {
            return isRegPro();
        }
        
        public boolean beforeClickAnterior()
        {
            return isRegPro();
        }

        public boolean beforeClickSiguiente()
        {
            return isRegPro();
        }

        public boolean beforeClickFin()
        {
            return isRegPro();            
        }

        public boolean beforeClickInsertar()
        {
            return isRegPro();
        }

        public boolean beforeClickConsultar()
        {
            return isRegPro();
        }

        public boolean beforeClickEliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickAnular()
        {
            return isRegPro();
        }

        public boolean beforeClickImprimir()
        {
            return isRegPro();
        }

        public boolean beforeClickVistaPreliminar()
        {
            return isRegPro();
        }

        public boolean beforeClickCancelar()
        {
            return isRegPro();
        }
        
    }

     /**
     * Esta clase hereda de la interface TableModelListener que permite determinar
     * cambios en las celdas del JTable.
     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener
    {
        public void tableChanged(javax.swing.event.TableModelEvent e)
        {
            switch (e.getType())
            {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        if (blnHayCam || objTblMod.isDataModelChanged())
        {
            strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
            strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
            switch (mostrarMsgCon(strAux, true))
            {
                case 0: //YES_OPTION
                    switch (objTooBar.getEstado())
                    {
                        case 'n': //Insertar
                            blnRes=objTooBar.beforeInsertar();
                            if (blnRes)
                                blnRes=objTooBar.insertar();
                            break;
                    }
                    break;
                case 1: //NO_OPTION
                    if (objTooBar.getBotonClick()==MiToolBar.INT_BUT_CON || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ELI || objTooBar.getBotonClick()==MiToolBar.INT_BUT_ANU || objTooBar.getBotonClick()==MiToolBar.INT_BUT_IMP || objTooBar.getBotonClick()==MiToolBar.INT_BUT_VIS)
                    {
                        blnRes=cargarReg();
                    }
                    break;
                case 2: //CANCEL_OPTION
                    blnRes=false;
                    break;
            }
        }
        return blnRes;
    }
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtNomTipDoc.setText("");
            
            txtCodBodOrg.setText("");
            txtNomBodOrg.setText("");
            
            txtCodBodDes.setText("");
            txtNomBodDes.setText("");
            
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtPesKgr.setText("");
            
            dtpFecDoc.setText("");
            
            txaObs1.setText("");
            txaObs2.setText("");
            
            limpiarTabla();
            
        }
        catch (Exception e){         blnRes=false;      }
        return blnRes;
    }
    

    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        int intCodTipDoc,intUltNumDoc, intUltCodDoc;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                intCodTipDoc=Integer.parseInt(txtCodTipDoc.getText());
                intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                if (insertarCab(con,intCodTipDoc, intUltCodDoc,intUltNumDoc)){
                    if(insertarDet(con,intCodTipDoc,intUltCodDoc)){
                        con.commit();
                        blnRes=true;
                    }else{con.rollback();}
                }else{con.rollback();}
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){         objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }

    
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc,int intUltNumDoc)
    {
        boolean blnRes=true;
        try
        {
            if (conn!=null)
            {
                stm=conn.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null) {
                    return false;  // Si no se puede obtener, la fecha 
                }
                strSQL="";
                strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+","+objParSis.getCodigoLocal()+","+intCodTipDoc+","+intUltCodDoc+", \n";
                strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                strSQL+="        "+intUltNumDoc+","+txtCodBodOrg.getText()+","+txtCodBodDes.getText()+","+txtPesKgr.getText()+",'N',";
                strSQL+="        "+objUti.codificar(txaObs1.getText())+","+objUti.codificar(txaObs2.getText())+", 'A', \n";
                strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null,'P');";
                strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc="+objParSis.getCodigoLocal()+" AND co_tipDoc="+intCodTipDoc+";";
                strSQL+=" \n ";
                stm.executeUpdate(strSQL);
            }
            stm.close();
            stm=null;
            datFecAux=null;
        }
        catch (java.sql.SQLException e){        blnRes=false;          objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;           objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
    
    /**
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(java.sql.Connection conn, int intCodTipDoc, int intUltCodDoc)
    {
        boolean blnRes=true;
        try
        {
            if (conn!=null)
            {
                stm=conn.createStatement();
                //Obtener la fecha del servidor.
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa() + ",";
                    strSQL+=" " + objParSis.getCodigoLocal() + ",";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (i+1) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_UNI_ITM) + ",";
                    strSQL+=" " + objTblMod.getValueAt(i,INT_TBL_DAT_PES_TOT_ITM);
                    strSQL+=" );\n";
                    stm.executeUpdate(strSQL);
                }        
            }
            stm.close();
            stm=null;
            datFecAux=null;
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

    private int getUltNumDoc(java.sql.Connection conn,int codTipDoc)
    {
        int intRes=0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
           if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN (a1.ne_ultdoc) IS NULL THEN 1 ELSE (a1.ne_ultdoc+1) END AS ne_ultDoc \n";
                strSQL+=" FROM tbm_cabTipDoc as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_loc=1";
                strSQL+=" AND a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intRes = rstLoc.getInt("ne_ultDoc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        objUti.mostrarMsgErr_F1(this, e);          intRes = 0;       }
        catch (Exception e){           objUti.mostrarMsgErr_F1(this, e);           intRes = 0;       }
        return intRes;
    }
    
    private int getUltCodDoc(java.sql.Connection conn, int codTipDoc) 
    {
        int intRes = 0;
        try
        {
            java.sql.Statement stmLoc; 
            java.sql.ResultSet rstLoc;
            if (conn!=null){
                stmLoc=conn.createStatement();
                strSQL="";
                strSQL+=" SELECT CASE WHEN MAX (co_doc) IS NULL THEN 1 ELSE MAX(co_doc) + 1 END AS co_doc  \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" WHERE a1.co_emp="+objParSis.getCodigoEmpresaGrupo()+" AND a1.co_loc=1 AND\n";
                strSQL+="       a1.co_tipDoc="+codTipDoc+" \n";
                rstLoc = stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                       intRes = rstLoc.getInt("co_doc");
                } 
                rstLoc.close();
                rstLoc=null; 
                stmLoc.close();
                stmLoc=null;
           }
        }
        catch (java.sql.SQLException e){        
            objUti.mostrarMsgErr_F1(this, e);      
            intRes = 0;       
        }
        catch (Exception e){        
            objUti.mostrarMsgErr_F1(this, e);           
            intRes = 0;        
        }
        return intRes;
    }
    
    
     /**
     * Esta función obtiene el query de los Tipos de Documentos 
     * configurados de acuerdo al usuario.
     */
    private String getSQLTipDoc(int intTipo)
    {
        String strTipDoc="";
        try
        {
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario()==1)
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc ";
                if(intTipo!=1)
                  strSQL+="  ,a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                strSQL+=" FROM tbr_tipDocPrg as a ";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a1 ON (a.co_emp=a1.co_emp and a.co_loc=a1.co_loc and a.co_tipDoc=a1.co_tipDoc)";
                if(intIsCotVen==1){
                   strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut as a2 ON (a1.co_emp=a2.co_emp AND a2.co_cfg=1)"; 
                }
                strSQL+=" WHERE  a1.st_reg='A' ";
                strSQL+=" AND a.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a.co_loc=1" ;
                strSQL+=" AND a.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_tipDoc ";
                if(intTipo!=1)
                  strSQL+="  ,a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc ";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                if(intIsCotVen==1){
                   strSQL+=" INNER JOIN tbm_cfgTipDocUtiProAut as a2 ON (a1.co_emp=a2.co_emp AND a2.co_cfg=1)"; 
                }
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                strSQL+=" AND a1.co_loc=1";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            strTipDoc=strSQL;
        }
        catch (Exception e) {   objUti.mostrarMsgErr_F1(this, e);   }
        return strTipDoc;
    }   
        

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg() 
    {
        boolean blnRes = true;
        try
        {
            String strFil="";
            System.out.println("consultarReg:");
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null)
            {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
               
                //<editor-fold defaultstate="collapsed" desc="/* Filtro */">
                if(dtpFecDoc.isFecha()) {
                    strFil+=" AND a1.fe_doc='" +  objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                }
                if(txtCodTipDoc.getText().length()>0) {
                    strFil+=" AND a1.co_TipDoc=" + txtCodTipDoc.getText();
                }
                else
                {
                    strFil+=" AND a1.co_TipDoc in (" + getSQLTipDoc(1)+ " )";
                }
                if(txtCodDoc.getText().length()>0) {
                    strFil+=" AND a1.co_doc=" + txtCodDoc.getText();
                }
                if(txtNumDoc.getText().length()>0) {
                    strFil+=" AND a1.ne_NumDoc=" + txtNumDoc.getText() + "";
                }
                if(txtCodBodOrg.getText().length()>0) {
                    strFil+=" AND a1.co_bodOrg=" + txtCodBodOrg.getText();
                }
                if(txtCodBodDes.getText().length()>0) {
                    strFil+=" AND a1.co_bodDes=" + txtCodBodDes.getText();
                }
                //</editor-fold>
               
                if(objParSis.getCodigoUsuario()==1)
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
                    strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                    strSQL+=" INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)  \n";
                    strSQL+=" WHERE /*a1.st_reg='A' \n";
                    strSQL+=" AND */ a1.co_emp=" + intCodEmpSolTra;
                    strSQL+=" AND a1.co_loc=" + intCodLocSolTra;
                    strSQL+=" "+ strFil + " \n";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.ne_numDoc";
                }
                else
                {
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
                    strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                    strSQL+=" INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) \n";
                    strSQL+=" WHERE /*a1.st_reg='A' \n";
                    strSQL+=" AND */ a1.co_emp=" + intCodEmpSolTra;
                    strSQL+=" AND a1.co_loc=" + intCodLocSolTra;
                    strSQL+=" "+ strFil + " \n";
                    strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.fe_doc, a1.co_tipDoc, a1.ne_numDoc";
                }
                System.out.println("consultarReg: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                arlDatConSol = new ArrayList();
                while(rstCab.next())
                {
                    arlRegConSol = new ArrayList();
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_EMP,rstCab.getInt("co_emp"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_LOC,rstCab.getInt("co_loc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_TIPDOC,rstCab.getInt("co_tipDoc"));
                    arlRegConSol.add(INT_ARL_CON_SOL_TRA_COD_DOC,rstCab.getInt("co_doc"));
                    arlDatConSol.add(arlRegConSol);
                }
                stmCab.close();
                stmCab=null;
                rstCab.close();
                rstCab=null;
                conCab.close();
                conCab=null;
                
                if(arlDatConSol.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConSol.size()) + " registros");
                    intIndiceSolTra=arlDatConSol.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
            }
        }
        catch (java.sql.SQLException e){ blnRes=false;    objUti.mostrarMsgErr_F1(this, e);      }
        catch (Exception e){      blnRes=false;     objUti.mostrarMsgErr_F1(this, e);       }
        return blnRes;
    }
    

    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg()
    {
        boolean blnRes=false;
        try
        {
            if (cargarCabReg()) {
                if (cargarDetReg()) {
                    blnRes = true;
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
     * Esta función permite cargar la cabecera del registro seleccionado.
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
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.co_bodOrg, a1.co_bodDes,ROUND(a1.nd_pesTotKgr,2) as nd_pesTotKgr, a1.st_imp, \n";
                strSQL+="        a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.fe_ing, a1.fe_ultMod, a1.co_usrIng, a1.co_usrMod, a1.tx_comIng, a1.tx_comUltMod, a1.st_aut, \n";
                strSQL+="        a2.tx_desCor, a2.tx_desLar, a3.tx_nom as tx_nomBodOrg, a4.tx_nom as tx_nomBodDes \n";
                strSQL+=" FROM tbm_cabSolTraInv as a1 \n";
                strSQL+=" INNER JOIN tbm_cabTipDoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc) \n";
                strSQL+=" INNER JOIN tbm_bod as a3 ON (a1.co_emp=a3.co_emp AND a1.co_bodOrg=a3.co_bod) \n";
                strSQL+=" INNER JOIN tbm_bod as a4 ON (a1.co_emp=a4.co_emp AND a1.co_bodDes=a4.co_bod) \n";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP)+"\n";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC)+"\n";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC)+"\n";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC)+"\n";
                //  System.out.println("cargarCabReg: \n" + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux); 
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_desLar");
                    txtNomTipDoc.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("co_bodOrg");
                    txtCodBodOrg.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_nomBodOrg");
                    txtNomBodOrg.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("co_bodDes");
                    txtCodBodDes.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_nomBodDes");
                    txtNomBodDes.setText((strAux==null)?"":strAux);                    
                    
                    strAux=rst.getString("nd_pesTotKgr");
                    txtPesKgr.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);                    
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);                    
                    datFecDoc=rst.getDate("fe_doc");
                    dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);     
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    if (strAux.equals("I"))
                        strAux="Anulado";
                    else if (strAux.equals("E"))
                        strAux="Eliminado";
                    else
                        strAux="Otro";
                    objTooBar.setEstadoRegistro(strAux);
                }
                con.close();
                con=null;
            }
            rst.close();
            rst=null;
            stm.close();
            stm=null;
            //Mostrar la posición relativa del registro.
            intPosRel = intIndiceSolTra+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConSol.size()) );
        }
        catch (java.sql.SQLException e){      blnRes=false;        objUti.mostrarMsgErr_F1(this, e);       }
        catch (Exception e){           blnRes=false;         objUti.mostrarMsgErr_F1(this, e);        }
        return blnRes;
    }
   
    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            objTblMod.removeAllRows();
            objTooBar.setMenSis("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.co_reg, a1.co_itm, a2.tx_codAlt, a2.tx_nomItm,a3.tx_desCor, a1.nd_can,  \n";
                strSQL+="        a2.nd_cosUni, (a1.nd_can*a2.nd_cosUni) as nd_cosTot, a1.nd_pesUniKgr,a1.nd_pesTotKgr,a2.tx_codAlt2 \n";
                strSQL+=" FROM tbm_detSolTraInv as a1\n";
                strSQL+=" INNER JOIN tbm_inv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm) \n";
                strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (a2.co_uni=a3.co_reg)\n";
                strSQL+=" WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_EMP)+"\n";
                strSQL+=" AND a1.co_loc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_LOC)+"\n";
                strSQL+=" AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC)+"\n";
                strSQL+=" AND a1.co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC)+"\n";
                strSQL+=" ORDER BY a1.co_reg";
                // System.out.println("cargarDetReg::.." + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_ITM,rst.getString("co_itm"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM_DOS,rst.getString("tx_codAlt2"));
                    vecReg.add(INT_TBL_DAT_BTN_ITM,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_UNI_MED,rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_CAN,rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_PES_UNI_ITM,rst.getString("nd_pesUniKgr"));
                    vecReg.add(INT_TBL_DAT_PES_TOT_ITM,rst.getString("nd_pesTotKgr"));
                    vecReg.add(INT_TBL_DAT_CAN_ACT_STK,"");
                    vecDat.add(vecReg);
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
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

    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
     */
    private String getEstReg(String estado)
    {
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0))
            {
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                case 'C':
                    estado="Pendiente confirmación de inventario";
                    break;
                case 'F':
                    estado="Existen diferencias de inventario";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
        return estado;
    }
    

     /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtNomBodOrg.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Bodega de Origen </FONT> es obligatorio.<BR>Ingrese una bodega y vuelva a intentarlo.</HTML>");
            txtNomBodOrg.requestFocus();
            return false;
        }   
        if (txtNomTipDoc.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Tipo de documento </FONT> es obligatorio.<BR>Escriba una descripción larga y vuelva a intentarlo.</HTML>");
            txtNomTipDoc.requestFocus();
            return false;
        }
        if (txtNomBodDes.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\"> Bodega de Destino </FONT> es obligatorio.<BR>Ingrese una bodega y vuelva a intentarlo.</HTML>");
            txtNomBodDes.requestFocus();
            return false;
        }
        if (!dtpFecDoc.isFecha())
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        return true;
    }
    

    /**
     * Esta función anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg()
    {
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anularGrp()){
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
    

    /**
     * Esta función permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularGrp()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabSolTraInv ";
                strSQL+=" SET st_reg='I',co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" , fe_usrMod="+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" , tx_comUltMod="+objParSis.getNombreComputadoraConDirIP();
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" and co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
                strSQL+=" ;";
                //  System.out.println("anularGrp" + strSQL);
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
     * Esta función elimina el registro de la base de datos.
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
            if (con!=null){
                if (eliminarGrp()){
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
    
    
    /**
     * Esta función permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarGrp()
    {
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" UPDATE tbm_cabSolTraInv";
                strSQL+=" SET st_reg='I',co_usrMod="+objParSis.getCodigoUsuario();
                strSQL+=" , fe_usrMod="+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+=" , tx_comUltMod="+objParSis.getNombreComputadoraConDirIP();
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" and co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" and co_tipDoc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_TIPDOC);
                strSQL+=" and co_doc=" + objUti.getIntValueAt(arlDatConSol, intIndiceSolTra, INT_ARL_CON_SOL_TRA_COD_DOC);
                
                //  System.out.println("eliminarGrp" + strSQL);
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
        
    
    private boolean mostrarDocumentos(int intTipBus)
    {
       boolean blnRes=true;
        try
        {
            //  System.out.println("mostrarDocumentos::::....");
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(0);
                    vcoTipDoc.setVisible(true);
                   if (vcoTipDoc.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por descripcion corta".
                     if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                     {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
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
                            txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //if(objTooBar.getEstado()=='n')
                            //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                        }
                        else
                        {
                            txtCodTipDoc.setText(strDocCod);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtNomTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                        //if(objTooBar.getEstado()=='n')
                        //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
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
                            txtNomTipDoc.setText(vcoTipDoc.getValueAt(3));
                            //if(objTooBar.getEstado()=='n')
                            //   txtNumDoc.setText("" + (objUti.isNumero(vcoTipDoc.getValueAt(4))?Integer.parseInt(vcoTipDoc.getValueAt(4))+1:1));
                        }
                        else
                        {
                            txtNomTipDoc.setText(strDocNom);
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


    private boolean mostrarBodegaOrigen(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodOrg.setCampoBusqueda(0);
                    vcoBodOrg.setVisible(true);
                   if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                        txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por Código".
                    if (vcoBodOrg.buscar("a1.co_bod", txtCodBodOrg.getText()))
                    {
                        txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                        txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                        vcoBodOrg.setCampoBusqueda(1);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                            txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtCodBodOrg.setText(strCodBodOrg);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodOrg.buscar("a1.tx_nom", txtNomBodOrg.getText()))
                    {
                         txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                         txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                    }
                    else
                    {
                         vcoBodOrg.setCampoBusqueda(2);
                        vcoBodOrg.setCriterio1(11);
                        vcoBodOrg.cargarDatos();
                        vcoBodOrg.setVisible(true);
                        if (vcoBodOrg.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodOrg.setText(vcoBodOrg.getValueAt(1));
                            txtNomBodOrg.setText(vcoBodOrg.getValueAt(2));
                        }
                        else
                        {
                            txtNomBodOrg.setText(strCodBodOrg);
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
    
    private boolean mostrarBodegaDestino(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoBodDes.setCampoBusqueda(0);
                    vcoBodDes.setVisible(true);
                    if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por Código".
                    if (vcoBodDes.buscar("a1.co_bod", txtCodBodDes.getText()))
                    {
                        txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                        txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    else
                    {
                        vcoBodDes.setCampoBusqueda(1);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.setVisible(true);
                        if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        else
                        {
                            txtCodBodDes.setText(strCodBodDes);
                        }
                    }
                    break;
               case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoBodDes.buscar("a1.tx_nom", txtNomBodDes.getText()))
                    {
                         txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                         txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                    }
                    else
                    {
                         vcoBodDes.setCampoBusqueda(2);
                        vcoBodDes.setCriterio1(11);
                        vcoBodDes.cargarDatos();
                        vcoBodDes.setVisible(true);
                        if (vcoBodDes.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodBodDes.setText(vcoBodDes.getValueAt(1));
                            txtNomBodDes.setText(vcoBodDes.getValueAt(2));
                        }
                        else
                        {
                            txtNomBodDes.setText(strCodBodDes);
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

    private boolean configurarTipoDocumento()
    {
        boolean blnRes=true;
        int intTipo=2;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_deslar");
            arlCam.add("a1.ne_ultDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción Corta");
            arlAli.add("Descripción Larga");
            arlAli.add("Último Documento");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
            strSQL=getSQLTipDoc(intTipo);
            //System.out.println("configurarTipoDocumento: "+strSQL);
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Documentos", strSQL, arlCam, arlAli, arlAncCol);
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
    
    
    private boolean configurarVenBodOrg()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1)//Admin
            {
                strSQL="";
                strSQL+=" SELECT co_bod, tx_nom ";
                strSQL+=" FROM tbm_bod";
                strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa()+" AND st_reg IN ('A','P')";
                if(txtCodBodDes.getText().length()>0)
                    strSQL+=" AND co_bod !="+txtCodBodDes.getText();
                strSQL+=" ORDER BY co_bod";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom \n";
                strSQL+=" FROM tbm_bod as a1 \n";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) \n";
                strSQL+=" WHERE a1.st_reg IN ('A','P') AND a2.tx_natBod IN ('A','E') " ;
                strSQL+="       AND a2.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                strSQL+="       AND a2.co_loc=" + objParSis.getCodigoLocal() + " \n";
                strSQL+="       AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                strSQL+="       AND a2.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+="       AND a2.co_usr=" + objParSis.getCodigoUsuario() + " \n";
                if(txtCodBodDes.getText().length()>0)
                    strSQL+=" AND a1.co_bod !="+txtCodBodDes.getText();
                strSQL+=" ORDER BY a1.co_bod";
            }
            //  System.out.println("configurarVenBodOrg: \n" + strSQL);
            vcoBodOrg=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Origen", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBodOrg.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    
    private boolean configurarVenBodDes()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("350");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1) //Admin
            {
                strSQL="";
                strSQL+=" SELECT co_bod, tx_nom ";
                strSQL+=" FROM tbm_bod";
                strSQL+=" WHERE co_emp="+ objParSis.getCodigoEmpresa()+" AND st_reg IN ('A','P')";
                if(txtCodBodOrg.getText().length()>0)
                    strSQL+=" AND co_bod !="+txtCodBodOrg.getText();
                strSQL+=" ORDER BY co_bod";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom \n";
                strSQL+=" FROM tbm_bod as a1 \n";
                strSQL+=" INNER JOIN tbr_bodTipDocPrgUsr as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) \n";
                strSQL+=" WHERE a1.st_reg IN ('A','P') AND a2.tx_natBod IN ('A','I') "+ " \n";
                strSQL+="       AND a2.co_emp=" + objParSis.getCodigoEmpresa()+ " \n";
                strSQL+="       AND a2.co_loc=" + objParSis.getCodigoLocal() + " \n";
                strSQL+="       AND a2.co_tipDoc=" + txtCodTipDoc.getText() + " \n";
                strSQL+="       AND a2.co_mnu=" + objParSis.getCodigoMenu() + " \n";
                strSQL+="       AND a2.co_usr=" + objParSis.getCodigoUsuario()  + " \n";
                if(txtCodBodOrg.getText().length()>0)
                    strSQL+=" AND a1.co_bod !="+txtCodBodOrg.getText();
                strSQL+=" ORDER BY a1.co_bod";
            }
            //  System.out.println("configurarVenBodDes: \n" + strSQL);
            vcoBodDes=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodega Destino", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBodDes.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                if(objParSis.getCodigoUsuario()==1) //Admin
                {
                     strSQL="";
                     strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar ";
                     strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                     strSQL+=" INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                     strSQL+=" WHERE a2.st_reg IN ('P','S') AND a2.co_mnu=3983 AND a1.co_emp="+ objParSis.getCodigoEmpresaGrupo()+" AND a1.co_loc=1 ";
                     strSQL+=" ORDER BY a1.co_tipDoc, a1.st_reg desc \n";
                }//se quema por que se llama desde cotizaciones de venta 
                else
                {
                     strSQL="";
                     strSQL+=" SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar ";
                     strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                     strSQL+=" INNER JOIN tbr_tipDocUsr AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)\n";
                     strSQL+=" WHERE a3.st_reg IN ('P','S') AND a3.co_mnu=3983 AND a3.co_emp="+ objParSis.getCodigoEmpresaGrupo()+" AND a3.co_loc=1 ";
                     strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                     strSQL+=" ORDER BY a1.co_tipDoc, a1.st_reg desc \n";
                 }
                //System.out.println("mostrarTipDocPre: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtNomTipDoc.setText(rst.getString("tx_desLar"));
                    //txtNumDoc.setText(String.valueOf(getUltNumDoc(con,rst.getInt("co_tipDoc"))));
                }
                else
                {
                    mostrarMsgAdv("No tiene ningun documento autorizado...");
                    objTooBar.setEnabledAceptar(false);
                    return false;
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
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del Item";
                    break;
                 case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código Alterno del Item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM_DOS:
                    strMsg="Código alterno 2 del ítem";
                break;
                case INT_TBL_DAT_BTN_ITM:
                    strMsg="Listado de items";
                    break;
               case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
               case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de medida";
                    break;
               case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
               case INT_TBL_DAT_PES_UNI_ITM:
                    strMsg="Peso unitario (Kg)";
                    break;
               case INT_TBL_DAT_PES_TOT_ITM:
                    strMsg="Peso total (Kg)";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_codAlt2");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("a1.tx_desCor"); // Unidad de medida
            arlCam.add("a1.nd_pesItmKgr");
            arlCam.add("a1.nd_canDis");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre del Item");
            arlAli.add("Uni.Med.");
            arlAli.add("Peso (Kg)");
            arlAli.add("Disponible");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("70");
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("30");
            arlAncCol.add("50");
            arlAncCol.add("70");
            
             int intColOcu[] = new int[1];
             intColOcu[0] = 1;
             
            //Armar la sentencia SQL.
            String strCadena="";

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items", strCadena, arlCam, arlAli, arlAncCol, intColOcu );
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(3, javax.swing.JLabel.CENTER);
            vcoItm.setConfiguracionColumna(7, javax.swing.JLabel.RIGHT, ZafVenCon.INT_FOR_NUM, objParSis.getFormatoNumero(), false, true);
            vcoItm.setCampoBusqueda(1);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si, No y Cancelar. El usuario es quien determina lo que debe
     * hacer el sistema seleccionando una de las opciones que se presentan.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     * @param blnMosBotCan Un valor booleano que deteremina si se debe mostrar
     * el botón "Cancelar".
     * @return La opción que seleccionó el usuario.
     */
    private int mostrarMsgCon(String strMsg, boolean blnMosBotCan)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, (blnMosBotCan==true?javax.swing.JOptionPane.YES_NO_CANCEL_OPTION:javax.swing.JOptionPane.YES_NO_OPTION), javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


    /**
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Número de documento <FONT COLOR=\"red\">ya en uso </FONT>. <BR>.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    
    private ArrayList arlRegSolTra;
    private ArrayList arlDatSolTra;
    private static final int INT_ARL_COD_CFG_SOL_TRA_VEN=2000;  // CODIGO DE LA CONFIGURACION PARA SOLICITUDES POR VENTA 
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaPreFactura(java.sql.Connection con, ArrayList arlDat,int intCodEmpCot, int intCodLocCot, int intCodDocCot)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                //objTooBar.clickInsertar();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT a1.co_seg, MAX(a1.co_reg) as co_reg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                strSQL+= " WHERE a1.co_empRelCabCotVen="+intCodEmpCot+" AND a1.co_locRelCabCotVen="+intCodLocCot+" \n";
                strSQL+= "       AND a1.co_cotRelCabCotVen="+intCodDocCot+" \n";
                strSQL+= " GROUP BY a1.co_seg  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=rstLoc.getInt("co_reg");
                }
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaReservas(java.sql.Connection con, ArrayList arlDat,int intCodEmpCot, int intCodLocCot, int intCodDocCot)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT a1.co_seg, MAX(a1.co_reg) as co_reg\n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                strSQL+= " WHERE a1.co_empRelCabCotVen="+intCodEmpCot+" AND a1.co_locRelCabCotVen="+intCodLocCot+" \n";
                strSQL+= "       AND a1.co_cotRelCabCotVen="+intCodDocCot+" \n";
                strSQL+= " GROUP BY a1.co_seg  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=rstLoc.getInt("co_reg");
                }
                
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    
    /**
     * Función que inserta solicitud cuando viene desde la venta, y necesita mercaderia de otras bodega.
     * se hace primero la solicitud y ésta queda registrada en la tabla de seguimiento.
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodDocCot
     * @return 
     */
    public ArrayList<String> insertarSolicitudTransferenciaSistemas(java.sql.Connection con, ArrayList arlDat)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        String strCodBodEgr="";
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                //objTooBar.clickInsertar();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,INT_ARL_COD_CFG_SOL_TRA_VEN);
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                
                strSQL = "";
                strSQL+= " SELECT MAX(a1.co_seg)  as co_seg \n";
                strSQL+= " FROM tbm_cabSegMovInv as a1  \n";
                rstLoc = stm.executeQuery(strSQL);
                if (rstLoc.next()) {
                    intCodSeg=rstLoc.getInt("co_seg");
                    intCodRegSolTra=1;
                }
                intCodSeg++;
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(i==0 || (!objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR).equals(strCodBodEgr))){
                        strCodBodEgr=objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR);
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+objParSis.getCodigoEmpresaGrupo()+", 1, "+intCodTipDoc+","+intUltCodDoc+", \n";
                        strSQL+="        '"+objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)+","+objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)+",null,'N',";
                        strSQL+="        null,"+objUti.codificar("[Generado por Zafiro.] ")+ " , 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(objParSis.getFechaHoraServidorIngresarSistema(), objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null ";
                        if(objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_EGR)==objUti.getIntValueAt(arlDat, i, INT_ARL_SOL_TRA_COD_BOD_ING)){
                            strSQL+=" ,'N' ";
                        }
                        else{
                            strSQL+=" ,'P' ";
                        }
                        strSQL+=" ); \n";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDoc+";";
                        strSQL+=" \n ";
                        intCodReg=1;
                        stm.executeUpdate(strSQL);
                        
                        /* CONTENEDOR PARA ENVIAR A COTIZACIONES DE VENTA */
                        arlRegSolTra = new ArrayList();
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_EMP, objParSis.getCodigoEmpresaGrupo());
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_LOC, 1);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_TIP_DOC, intCodTipDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_DOC,intUltCodDoc);
                        arlRegSolTra.add(INT_ARL_COT_VEN_COD_BOD_EGR,Integer.parseInt(strCodBodEgr));
                        arlDatSolTra.add(arlRegSolTra);
                        
                        intCodRegSolTra++;
                        strSQL = "";
                        strSQL+= " INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+= " VALUES ("+intCodSeg+","+intCodRegSolTra+","+ objParSis.getCodigoEmpresaGrupo();
                        strSQL+= ",1,"+ intCodTipDoc +","+intUltCodDoc+"); \n";
                        stm.executeUpdate(strSQL);
                        
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresaGrupo() + ",";
                    strSQL+=" 1,";
                    strSQL+=" " + intCodTipDoc + ",";
                    strSQL+=" " + intUltCodDoc + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                rstLoc.close();
                rstLoc=null;
            }
            stm.close();
            stm=null;
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return arlDatSolTra;
    }
    
    private int getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(java.sql.Connection con,int intCodCfg){
        java.sql.Statement stmLoc; 
        java.sql.ResultSet rstLoc;
        int intCodTipDoc=0;
        try{
            if (con!=null){
                stmLoc=con.createStatement();
                strSQL=" select co_tipDoc from tbm_cfgTipDocUtiProAut where co_emp="+objParSis.getCodigoEmpresaGrupo()+" and co_cfg="+intCodCfg;
                System.out.println("getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica " +strSQL );
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    intCodTipDoc=rstLoc.getInt("co_tipDoc");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
        }
        return intCodTipDoc;
    }
    
    private ZafCom92 objZafCom92;
    public int intCodEmpGenSolTraInv, intCodLocGenSolTraInv, intCodTipDocGenSolTraInv, intCodDocGenSolTraInv;
    
    public boolean generaMovimientoTransferenciaReposicion(java.sql.Connection con, ArrayList arlDat, ArrayList arlDatRepInvBod, String strPesTotKgrSolTra, int intCodBodOrg, int intCodBodDes,int intCodCfg){
        boolean blnRes=false;
        try{
            objZafCom92 = new ZafCom92(objParSis);
            if(con!=null){
                if(insertarSolicitudTransferenciaReposicion(con,arlDat, arlDatRepInvBod, strPesTotKgrSolTra, intCodBodOrg,intCodBodDes,intCodCfg)){//INSERTA LA SOLICITUD Y LA PONE EN EL SEGUIMIENTO
                    if(objZafCom92.generaTransferenciaPreAutorizada(con, intCodEmpGenSolTraInv, intCodLocGenSolTraInv, intCodTipDocGenSolTraInv, intCodDocGenSolTraInv)){
                        blnRes=true;
                        System.out.println("Listo Jota!");
                    }
                }
            }
            objZafCom92=null;
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);       
            blnRes=false;
        }
        return blnRes;
    }
    
    public ArrayList getSolTraInv(){
        ArrayList arlReg, arlDat;
        arlReg = new ArrayList();
        arlDat = new ArrayList();
        arlReg.add(intCodEmpGenSolTraInv);
        arlReg.add(intCodLocGenSolTraInv);
        arlReg.add(intCodTipDocGenSolTraInv);
        arlReg.add(intCodDocGenSolTraInv);
        arlDat.add(arlReg);
        return arlDat;
    }
    
    
    
    
    /**
     * Función que inserta solicitud cuando viene desde otro programa
     * 
     * @param con
     * @param arlDat
     * @param intCodEmpCot
     * @param intCodLocCot
     * @param intCodCfg: CODIGO DE LA CONFIGURACION A USAR DOCUMENTOS AUTOMATICOS 
     * @return 
     */
    private boolean insertarSolicitudTransferenciaReposicion(java.sql.Connection con, ArrayList arlDat, ArrayList arlDatRepInvBod,  String strPesTotKgrSolTra, int intCodBodOrg, int intCodBodDes,int intCodCfg)
    {
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        java.sql.ResultSet rstLoc;
        int intCodSeg=0, intCodRegSolTra=0;
        boolean blnRes=true, blnIsCab=true, blnSeg=false;
        try
        {
            if (con!=null)
            {
                arlDatSolTra= new ArrayList();
                objTooBar.clickInsertar();
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,intCodCfg);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(blnIsCab)
                    {
                        //<editor-fold defaultstate="collapsed" desc=" Datos Solicitud de Transferencia">
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                        for(int j=0; j<arlDatRepInvBod.size(); j++)
                        {
                            intCodEmpRepInv=  objUti.getIntValueAt(arlDatRepInvBod, j, INT_ARL_GEN_REINBO_CODEMP);
                            intCodLocRepInv=  objUti.getIntValueAt(arlDatRepInvBod, j, INT_ARL_GEN_REINBO_CODLOC);
                            intCodTipDocRepInv= objUti.getIntValueAt(arlDatRepInvBod, j, INT_ARL_GEN_REINBO_CODTIPDOC);
                            intCodDocRepInv=   objUti.getIntValueAt(arlDatRepInvBod, j, INT_ARL_GEN_REINBO_CODDOC);
                        }
                        
                        //Obtiene Datos seguimiento
                        strSQL = " SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                        strSQL += " FROM tbm_cabSegMovInv as a1 \n";
                        strSQL += " WHERE a1.co_seg = ( \n";
                        strSQL += " SELECT co_seg FROM tbm_cabSegMovInv ";
                        strSQL += " WHERE co_empRelCabRepInvBod=" + intCodEmpRepInv;
                        strSQL += " AND co_locRelCabRepInvBod=" + intCodLocRepInv;
                        strSQL += " AND co_tipDocRelCabRepInvBod=" + intCodTipDocRepInv;
                        strSQL += " AND co_docRelCabRepInvBod=" + intCodDocRepInv;
                        strSQL += " )  GROUP BY a1.co_seg ";
                        System.out.println("CodigoSeguimiento-Reposiciones(1): "+strSQL);
                        rstLoc = stm.executeQuery(strSQL);
                        if (rstLoc.next())
                        {
                            intCodSeg = rstLoc.getInt("co_seg");
                            intCodRegSolTra = rstLoc.getInt("co_reg");
                            blnSeg=true;
                        }
                        if(!blnSeg)
                        {
                            strSQL ="";
                            strSQL+=" SELECT MAX(a1.co_seg)+1 as co_seg FROM tbm_cabSegMovInv as a1 ";
                            System.out.println("CodigoSeguimiento-Reposiciones(2): "+strSQL);
                            rstLoc = stm.executeQuery(strSQL);
                            if (rstLoc.next()) 
                            {
                                intCodSeg = rstLoc.getInt("co_seg");
                                intCodRegSolTra=1;
                            }
                        }
                            
                        rstLoc.close();
                        rstLoc = null;
                        
                        intCodEmpGenSolTraInv=objParSis.getCodigoEmpresaGrupo(); intCodLocGenSolTraInv=1; intCodTipDocGenSolTraInv=intCodTipDoc; intCodDocGenSolTraInv=intUltCodDoc;
                        //</editor-fold>
                        
                        strSQL="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,fe_doc,ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, \n";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2,st_reg,fe_ing,co_usrIng,tx_comIng,st_aut,st_conInv)  \n";
                        strSQL+=" VALUES ("+intCodEmpGenSolTraInv+", "+intCodLocGenSolTraInv+", "+intCodTipDocGenSolTraInv+","+intCodDocGenSolTraInv+", \n";
                        strSQL+="        '"+objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+", "+intCodBodOrg+", "+intCodBodDes+", "+strPesTotKgrSolTra+", 'N',";
                        strSQL+="        null,'[Generado por Zafiro.]', 'A', \n";
                        strSQL+="        '"+objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="       "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"', null,'P');";
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 where co_emp="+intCodEmpGenSolTraInv;
                        strSQL+=" AND co_loc=1 AND co_tipDoc="+intCodTipDocGenSolTraInv+";";
                        strSQL+=" INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) \n";
                        strSQL+=" VALUES ("+intCodSeg+","+intCodRegSolTra+","+ intCodEmpGenSolTraInv+" , "+intCodLocGenSolTraInv;
                        strSQL+=" ,"+ intCodTipDocGenSolTraInv +","+intCodDocGenSolTraInv+"); \n";
                        strSQL+=" \n ";
                        stm.executeUpdate(strSQL);
                        intCodReg=1;
                        blnIsCab=false;
                    }
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + intCodEmpGenSolTraInv + ",";
                    strSQL+=" " + intCodLocGenSolTraInv + ",";
                    strSQL+=" " + intCodTipDocGenSolTraInv + ",";
                    strSQL+=" " + intCodDocGenSolTraInv + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_SOL_TRA_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_SOL_TRA_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_SOL_PES_TOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    stm.executeUpdate(strSQL);
                }  
                stm.close();
                stm=null;
            }
            
        }
        catch (java.sql.SQLException e){         
            objUti.mostrarMsgErr_F1(this, e);      
            blnRes=false;
        }
        catch (Exception e){         
            objUti.mostrarMsgErr_F1(this, e);      
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que inserta solicitud cuando viene desde otro programa.
     * Ejemplo: Importaciones/Compras Locales
     * @param con
     * @param arlDat
     * @param strPesTotKgrSolTra
     * @param intCodBodOrg
     * @param intCodBodDes
     * @param intCodCfg Código de la configuración para uso de documentos automáticos.
     * @param arlDatIngImpLoc Datos de Ingreso por Importacion o Compra Local.
     * @return 
     */
    public boolean insertarSolicitudTransferenciaImportaciones(java.sql.Connection con, ArrayList arlDat,  BigDecimal bgdPesoTotalSol, int intCodBodOrg, int intCodBodDes,int intCodCfg)
    {
        java.sql.ResultSet rstLoc;
        int intCodTipDoc, intUltCodDoc=0, intUltNumDoc=0,intCodReg=0;
        int intCodSeg=0, intCodRegSolTra=0;
        
        boolean blnRes=false,blnIsCab=true, blnSeg=false;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                intCodTipDoc=getCodigoTipoDocumentoSolicitudesTransferenciaAutomatica(con,intCodCfg);
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                for(int i=0; i<arlDat.size(); i++)
                {
                    if(blnIsCab)
                    {
                        //<editor-fold defaultstate="collapsed" desc=" Datos Solicitud de Transferencia">
                        intUltCodDoc=getUltCodDoc(con,intCodTipDoc);
                        intUltNumDoc=getUltNumDoc(con,intCodTipDoc);  
                      
                        intCodEmpIngImpLoc=    objUti.getIntValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_CODEMP_INGIMP);
                        intCodLocIngImpLoc=    objUti.getIntValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_CODLOC_INGIMP);
                        intCodTipDocIngImpLoc= objUti.getIntValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_CODTIPDOC_INGIMP);
                        intCodDocIngImpLoc=    objUti.getIntValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_CODDOC_INGIMP);
                        strNumPedEmbImp=       objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_NUMPED_INGIMP);
                                   
                        //Obtiene Datos seguimiento
                        strSQL =" SELECT MAX(co_reg)+1 as co_reg, a1.co_seg  \n";
                        strSQL+=" FROM tbm_cabSegMovInv as a1 \n";
                        strSQL+=" WHERE a1.co_seg = ( \n";
                        strSQL+=" SELECT co_seg FROM tbm_cabSegMovInv ";
                        strSQL+=" WHERE co_empRelCabMovInv=" + intCodEmpIngImpLoc;
                        strSQL+=" AND co_locRelCabMovInv=" + intCodLocIngImpLoc;
                        strSQL+=" AND co_tipDocRelCabMovInv=" + intCodTipDocIngImpLoc;
                        strSQL+=" AND co_docRelCabMovInv=" + intCodDocIngImpLoc;
                        strSQL+=" )  GROUP BY a1.co_seg ";
                        //System.out.println("CodigoSeguimiento-Importaciones(1): "+strSQL);
                        rstLoc = stm.executeQuery(strSQL);
                        if (rstLoc.next())
                        {
                            intCodSeg = rstLoc.getInt("co_seg");
                            intCodRegSolTra = rstLoc.getInt("co_reg");
                            blnSeg=true;
                        }
                        if(!blnSeg)
                        {
                            strSQL ="";
                            strSQL+=" SELECT MAX(a1.co_seg)+1 as co_seg FROM tbm_cabSegMovInv as a1 ";
                            //System.out.println("CodigoSeguimiento-Importaciones(2): "+strSQL);
                            rstLoc = stm.executeQuery(strSQL);
                            if (rstLoc.next()) 
                            {
                                intCodSeg = rstLoc.getInt("co_seg");
                                intCodRegSolTra=1;
                            }
                        }
                            
                        rstLoc.close();
                        rstLoc = null;
                        
                        intCodEmpGenSolTraInv=objParSis.getCodigoEmpresaGrupo(); intCodLocGenSolTraInv=1; intCodTipDocGenSolTraInv=intCodTipDoc; intCodDocGenSolTraInv=intUltCodDoc;
                        //</editor-fold>
                            
                        strSQL ="";
                        strSQL+=" INSERT INTO tbm_cabSolTraInv(co_emp, co_loc, co_tipDoc, co_doc, fe_doc, ne_numDoc, co_bodOrg, co_bodDes, nd_pesTotKgr, ";
                        strSQL+="                              st_imp, tx_obs1, tx_obs2, st_reg, fe_ing, co_usrIng, tx_comIng, st_aut, st_conInv)  \n";
                        strSQL+=" VALUES ("+intCodEmpGenSolTraInv+","+intCodLocGenSolTraInv+", "+intCodTipDocGenSolTraInv+","+intCodDocGenSolTraInv+", ";
                        strSQL+="        '"+objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="        "+intUltNumDoc+","+intCodBodOrg+","+intCodBodDes+","+bgdPesoTotalSol+",'N',";
                        strSQL+="        '"+strNumPedEmbImp+"','[Generado por Zafiro]', 'A', ";
                        strSQL+="        '"+objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos())+"',";
                        strSQL+="        "+objParSis.getCodigoUsuario()+",'"+objParSis.getNombreComputadoraConDirIP()+"',null,'P'); \n";
                        
                        //Actualización Último Tipo Documento.
                        strSQL+=" UPDATE tbm_cabTipDoc set ne_ultDoc=ne_ultDoc+1 ";
                        strSQL+=" WHERE co_emp="+intCodEmpGenSolTraInv+" AND co_loc="+intCodLocGenSolTraInv+" AND co_tipDoc="+intCodTipDocGenSolTraInv+"; \n";
                        
                        //Seguimiento 
                        strSQL+=" INSERT INTO tbm_cabSegMovInv (co_seg, co_reg, co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipDocRelCabSolTraInv, co_docRelCabSolTraInv) ";
                        strSQL+=" VALUES ("+intCodSeg+","+intCodRegSolTra+","+ intCodEmpGenSolTraInv;
                        strSQL+=" , "+intCodLocGenSolTraInv+", "+ intCodTipDocGenSolTraInv +", "+intCodDocGenSolTraInv+"); \n";                        
                        
                        //Relacion Ingreso Importacion con Solicitud de Transferencia.
                        strSQL+=" INSERT INTO tbr_cabSolTraInvCabMovInv (co_empRelCabSolTraInv, co_locRelCabSolTraInv, co_tipdocRelCabSolTraInv, co_docRelCabSolTraInv,  co_empRelCabMovInv, co_locRelCabMovInv, co_tipdocRelCabMovInv, co_docRelCabMovInv) ";
                        strSQL+=" VALUES ("+intCodEmpGenSolTraInv+","+intCodLocGenSolTraInv+","+ intCodTipDocGenSolTraInv+","+ intCodDocGenSolTraInv+"  ";
                        strSQL+=" , "+intCodEmpIngImpLoc+", "+ intCodLocIngImpLoc +", "+intCodTipDocIngImpLoc+", "+intCodDocIngImpLoc+"); \n";
                        
                        //System.out.println("insertarSolicitudTransferenciaParaImportacion-Cabecera: "+strSQL);
                        stm.executeUpdate(strSQL);
                        intCodReg=1;
                        txtCodTipDoc.setText(String.valueOf(intCodTipDocGenSolTraInv));
                        txtCodDoc.setText(String.valueOf(intCodDocGenSolTraInv));
                        blnIsCab=false;
                       
                    }
                    
                    //Detalle
                    strSQL="";
                    strSQL+=" INSERT INTO tbm_detSolTraInv(co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,nd_can,nd_pesUniKgr,nd_pesTotKgr) \n";
                    strSQL+=" VALUES (";
                    strSQL+=" " + intCodEmpGenSolTraInv + ",";
                    strSQL+=" " + intCodLocGenSolTraInv + ",";
                    strSQL+=" " + intCodTipDocGenSolTraInv + ",";
                    strSQL+=" " + intCodDocGenSolTraInv + ",";
                    strSQL+=" " + (intCodReg) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_ITM_GRP) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_CAN) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_PES_UNI) + ",";
                    strSQL+=" " + objUti.getStringValueAt(arlDat, i, INT_ARL_GEN_DAT_SOL_PES_UNITOT);
                    strSQL+=" );\n";
                    intCodReg++;
                    //System.out.println("insertarSolicitudTransferenciaParaAutorizacion-Detalle: "+strSQL);
                    
                     stm.executeUpdate(strSQL);
                     blnRes=true;
                }  
                stm.close();
                stm=null;
            }
            
        }
        catch (java.sql.SQLException e)  {    objUti.mostrarMsgErr_F1(this, e);       blnRes=false;     }
        catch (Exception e){       objUti.mostrarMsgErr_F1(this, e);      blnRes=false;      }
        return blnRes;
    }
    
    
    public String getSQLSolTrsCab(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        String strSolTra="";

        try
        {
            strSolTra+=" SELECT a1.tx_DesCor, a1.tx_DesLar, a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.ne_numDoc, a.fe_doc, a.nd_pesTotKgr, ((a.nd_pesTotKgr* 2.2)/100)  as nd_kilQQ , ";      
            strSolTra+="        a.co_bodOrg, b.tx_nom as NomBodOrg, b.tx_dir as DirBodOrg, a.co_bodDes, b1.tx_nom as NomBodDes, b1.tx_dir as DirBodDes, ";
            strSolTra+="        a.st_imp, a.co_usrIng, a2.tx_nom as NomUsr, a.tx_obs1 ";
            strSolTra+=" FROM tbm_cabSolTraInv as a ";
            strSolTra+=" INNER JOIN tbm_cabTipDoc as a1 ON (a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc) ";
            strSolTra+=" INNER JOIN tbm_usr as a2 ON (a2.co_usr=a.co_usrIng) ";
            strSolTra+=" INNER JOIN tbm_bod as b ON (b.co_emp=a.co_emp AND b.co_bod=a.co_bodOrg ) ";
            strSolTra+=" INNER JOIN tbm_bod as b1 ON (b1.co_emp=a.co_emp AND b1.co_bod=a.co_bodDes ) ";
            strSolTra+=" WHERE a.co_emp="+CodEmp;
            strSolTra+=" AND a.co_loc="+CodLoc;
            strSolTra+=" AND a.co_tipDoc="+CodTipDoc;
            strSolTra+=" AND a.co_doc="+CodDoc;
            //System.out.println("PK-SOLICITUD-IMPRIMIR: "+CodEmp+" - "+CodLoc+" - "+CodTipDoc+" - "+CodDoc);
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);     }
        return strSolTra;
    }
    
    public String getSQLSolTrsDet(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc)
    {
        String strSolTra="";

        try
        {
            strSolTra+=" SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.co_itm, a1.tx_codAlt";
            strSolTra+="       ,(substring(a1.tx_codAlt2 from 1 for 1 ) || ' ' || substring(a1.tx_codAlt2 from 2 for 1 )|| ' ' || substring(a1.tx_codAlt2 from 3 for 1 )) as tx_codAlt2 ";
            strSolTra+="       , a1.tx_nomItm, a.nd_can, a2.tx_DesCor as tx_uniMed, a.nd_pesUniKgr as nd_pesUniKgrItm, a.nd_pesTotKgr as nd_pesTotKgrItm, a5.tx_ubi";        
            strSolTra+=" FROM tbm_detSolTraInv as a";
            strSolTra+=" INNER JOIN tbm_inv as a1 ON (a1.co_emp=a.co_emp AND a1.co_itm=a.co_itm)";
            strSolTra+=" INNER JOIN tbm_equInv as a3 ON (a3.co_Emp=a1.co_emp AND a3.co_itm=a1.co_itm)";
            strSolTra+=" INNER JOIN tbm_cabSolTraInv as a4 ON (a4.co_Emp=a.co_emp AND a4.co_loc=a.co_loc AND a4.co_tipDoc=a.co_TipDoc AND a4.co_doc=a.co_Doc)";  
            strSolTra+=" LEFT OUTER JOIN (";
            strSolTra+="     SELECT DISTINCT b3.co_empGrp, b3.co_bodGrp, b1.co_itmMae, b2.tx_ubi";
            strSolTra+="     FROM tbm_equInv AS b1";
            strSolTra+="     INNER JOIN tbm_invBod AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_itm=b2.co_itm)";
            strSolTra+="     INNER JOIN tbr_bodempbodgrp b3 on (b3.co_emp = b2.co_emp and b3.co_bod = b2.co_bod)";
            strSolTra+="     INNER JOIN ";
            strSolTra+="     (";
            strSolTra+="         SELECT co_emp, co_bodDes FROM tbm_CabSolTraInv"; 
            strSolTra+="         WHERE co_emp="+CodEmp;
            strSolTra+="         AND co_loc="+CodLoc;
            strSolTra+="         AND co_tipDoc="+CodTipDoc;
            strSolTra+="         AND co_doc="+CodDoc;
            strSolTra+="     ) as b4 ON (b4.co_emp=b3.co_empGrp AND b4.co_bodDes=b3.co_bodGrp) ";       
            strSolTra+=" ) AS a5 ON (a5.co_itmMae=a3.co_itmMae AND a5.co_empGrp=a4.co_emp AND a5.co_bodGrp=a4.co_bodDes)";
            strSolTra+=" LEFT OUTER JOIN tbm_var as a2 ON (a2.co_reg=a1.co_uni)";
            strSolTra+=" WHERE a4.co_emp="+CodEmp;
            strSolTra+=" AND a4.co_loc="+CodLoc;
            strSolTra+=" AND a4.co_tipDoc="+CodTipDoc;
            strSolTra+=" AND a4.co_doc="+CodDoc;
            
        }
        catch (Exception e) {     objUti.mostrarMsgErr_F1(this, e);     }
        return strSolTra;
    }
    
    /**
     * Actualiza estado de Impresión en las solicitudes de transferencias de inventario.
     * @return 
     */
    public boolean setEstImp(int CodEmp, int CodLoc, int CodTipDoc, int CodDoc) 
    {
        boolean blnRes = false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        try 
        {
            conLoc = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) 
            {
                stmLoc = conLoc.createStatement();
                strSQL = "";
                strSQL += " UPDATE tbm_cabSolTraInv";
                strSQL += " SET st_imp='S'";
                strSQL += " WHERE co_emp=" + CodEmp;
                strSQL += " AND co_loc=" + CodLoc;
                strSQL += " AND co_tipDoc=" + CodTipDoc;
                strSQL += " AND co_doc="  + CodDoc;
                strSQL += "; ";
        
                System.out.println("setEstImp" + strSQL);
                stmLoc.executeUpdate(strSQL);
                blnRes = true;
                stmLoc.close();
                conLoc.close();
                stmLoc = null;
                conLoc = null;
            }
            return blnRes;
        } 
        catch (java.sql.SQLException e) {   blnRes = false;  objUti.mostrarMsgErr_F1(this, e);   } 
        catch (Exception e) {  blnRes = false;   objUti.mostrarMsgErr_F1(this, e);  }
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
        boolean blnRes=false;
        String strRutRpt, strNomRpt, strFecHorSer;
        String strSQLRep="", strSQLSubRep="";
        int i, intNumTotRpt;

        try
        {
            objRptSis.cargarListadoReportes(conCab);
            objRptSis.setVisible(true);
            if (objRptSis.getOpcionSeleccionada()==ZafRptSis.INT_OPC_ACE)
            {
                strSQLRep+=getSQLSolTrsCab(intCodEmpSolTra, intCodLocSolTra, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                strSQLSubRep+=getSQLSolTrsDet(intCodEmpSolTra, intCodLocSolTra, Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
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
                            default:
                                strRutRpt=objRptSis.getRutaReporte(i);
                                strNomRpt=objRptSis.getNombreReporte(i);
                                //Inicializar los parametros que se van a pasar al reporte.
                                java.util.Map mapPar=new java.util.HashMap();
                                mapPar.put("strSQLRep", strSQLRep);
                                mapPar.put("strSQLSubRep", strSQLSubRep);
                                mapPar.put("SUBREPORT_DIR", strRutRpt);
                                mapPar.put("strFecImp",  strFecHorSer );
                                mapPar.put("strCamAudRpt", objParSis.getNombreUsuario() + " - " + objRptSis.getNombreReporte(0) + "   ");
            
                                if (objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt)) 
                                {
                                    blnRes = true;
                                    System.out.println("Se genero reporte de Solicitud de Transferencia de Inventario");
                                }
                                break;
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
        return blnRes;
    }
    
    //269;SOTRIN;Solicitudes de transferencias de inventario
    private boolean validaTipoDocumento(){
        boolean blnRes=false;
        if(txtCodTipDoc.getText().equals("269")){
            blnRes=true;
        }
        return blnRes;
    }
    
    
    private void clickCambiaTipoDocumento(){
        try
        {
            if (rstCab!=null)
            {
                rstCab.close();
                stmCab.close();
                conCab.close();
                rstCab=null;
                stmCab=null;
                conCab=null;
            }
            limpiarFrm();
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
            datFecAux=null;
            mostrarTipDocPre();
            configurarVenBodOrg();
            configurarVenBodDes();
            //configurarVenConItm();
            txtCodBodOrg.requestFocus();     

            if(objTooBar.getEstado()=='c'){
                dtpFecDoc.setEnabled(true);
                txtNumDoc.setEditable(true);
                txtCodDoc.setEditable(false);
                txtPesKgr.setEditable(false);
            }
            else{
                dtpFecDoc.setEnabled(false);
                txtNumDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtPesKgr.setEditable(false);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            }
            objTblMod.setDataModelChanged(false);
            blnHayCam=false;
        }
        catch (Exception e){          
            objUti.mostrarMsgErr_F1(this, e);          
        }
    }
    
    
}