/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 * 
 */

package Contabilidad.ZafCon54;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafDate.ZafSelectDate;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafAsiDia.ZafAsiDia;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCon54 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable de filtro para la consulta
    final int INT_TBL_DAT_LIN=0;                        //L�nea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificaci�n.
    final int INT_TBL_DAT_COD_LOC=2;                    //C�digo del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //C�digo del tipo de documento.
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;                //Descripci�n larga del tipo de documento.
       
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIPagTot objThrGUIPagTot;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private ZafAsiDia objAsiDia;
   
    
    
    private ZafDatePicker dtpFecDoc, dtpFecImp;
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    
    //Constantes: Columnas del JTable para datos
    final int INT_TBL_DAT_REP_LIN=0;                        //L�nea.
    final int INT_TBL_DAT_REP_CHK=1;
    final int INT_TBL_DAT_REP_NUM_DOC=2;
    final int INT_TBL_DAT_REP_NUM_CHQ=3;
    final int INT_TBL_DAT_REP_COD_PRV=4;
    final int INT_TBL_DAT_REP_NOM_PRV=5;
    final int INT_TBL_DAT_REP_VAL_DOC=6;
    final int INT_TBL_DAT_REP_COD_CTA_DEU=7;
    final int INT_TBL_DAT_REP_NUM_CTA_DEU=8;
    final int INT_TBL_DAT_REP_BUT_CTA_DEU=9;
    final int INT_TBL_DAT_REP_NOM_CTA_DEU=10;
    final int INT_TBL_DAT_REP_GLO_ASI_DIA=11;
    final int INT_TBL_DAT_REP_BUT_ANE=12;
    
    

    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCtaAcr, vcoCtaDeu, vcoCtaDeuTbl;                           //Ventana de consulta "Cuenta".
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private ZafVenCon vcoBen;                           //Ventana de consulta "Beneficiario".
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCtaAcr, strDesLarCtaAcr;          //Contenido del campo al obtener el foco.
    private String strDesCorCtaDeu, strDesLarCtaDeu;          //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    //Variables de la clase.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direcci�n del Beneficiario.
    private ZafSelectDate objSelDat;
    private Vector vecDatDet, vecCabDet, vecRegDet;
    
    private ZafTblMod objTblModDet;
    private ZafTblEdi objTblEdiDet;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChkDet;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkDet;            //Editor: JCheckBox en celda.
    private ZafMouMotAdaDet objMouMotAdaDet;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnuDet;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafTblFilCab objTblFilCabDet;
    
    private ZafTblCelRenBut objTblCelRenButAne;
    private ZafTblCelRenBut objTblCelRenButVcoCta;                //Render: Presentar JButton en JTable.
    private ZafTblCelEdiButDlg objTblCelEdiButAne;
    private ZafCon54_01 objCon54_01;
    private ZafTblBus objTblBusDet;
    private java.util.Date datFecAux;
    private ZafThreadGUIPro objThrGUIPro;
    
    
    //para guardar los datos de tbm_cabPag
    ArrayList arlRegCabPag, arlDatCabPag;
    final int INT_ARL_CAB_COD_EMP=0;
    final int INT_ARL_CAB_COD_LOC=1;
    final int INT_ARL_CAB_COD_TIP_DOC=2;
    final int INT_ARL_CAB_COD_DOC=3;
    final int INT_ARL_CAB_FEC_DOC=4;
    final int INT_ARL_CAB_FEC_VEN=5;
    final int INT_ARL_CAB_NUM_DOC_UNO=6;
    final int INT_ARL_CAB_NUM_DOC_DOS=7;
    final int INT_ARL_CAB_COD_CTA_ACR=8;
    final int INT_ARL_CAB_COD_CTA_DEU=9;
    final int INT_ARL_CAB_COD_CLI=10;
    final int INT_ARL_CAB_RUC_CLI=11;
    final int INT_ARL_CAB_NOM_CLI=12;
    final int INT_ARL_CAB_DIR_CLI=13;
    final int INT_ARL_CAB_MON_DOC=14;
    final int INT_ARL_CAB_COD_BEN=15;
    final int INT_ARL_CAB_NOM_BEN=16;
    final int INT_ARL_CAB_NUM_DOC_CAB_MOV_INV=17;
    final int INT_ARL_CAB_NUM_DOC_PRV=18;
    
    
    
    //para guardar los datos de tbm_detPag
    ArrayList arlRegDetPag, arlDatDetPag;
    final int INT_ARL_DET_COD_EMP=0;
    final int INT_ARL_DET_COD_LOC=1;
    final int INT_ARL_DET_COD_TIP_DOC=2;
    final int INT_ARL_DET_COD_DOC=3;
    final int INT_ARL_DET_COD_REG=4;
    final int INT_ARL_DET_COD_LOC_PAG=5;
    final int INT_ARL_DET_COD_TIP_DOC_PAG=6;
    final int INT_ARL_DET_COD_DOC_PAG=7;
    final int INT_ARL_DET_COD_REG_PAG=8;
    final int INT_ARL_DET_VAL_ABO=9;
    final int INT_ARL_DET_COD_CLI=10;
    final int INT_ARL_DET_FEC_VEN_CHQ=11;
    final int INT_ARL_DET_COD_BEN=12;


    ArrayList arlRegNumRet, arlDatNumRet;
    final int INT_ARL_NUM_RTE_COD_EMP=0;
    final int INT_ARL_NUM_RTE_COD_LOC=1;
    final int INT_ARL_NUM_RTE_COD_TIP_DOC=2;
    final int INT_ARL_NUM_RTE_COD_DOC=3;

    private String strNomSubRep="";
    private String strNomRepTuv="";
    private String strNomRepCas="";
    private String strNomRepNos="";
    private String strNomRepDim="";
    private String strNomRepDef="";
    
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblVal;                //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoCta;       //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoCta;       //Editor: JButton en celda.
    private String strTblActNumDoc="";
    private String strCodGrpTipDoc="";
    /** Crea una nueva instancia de la clase ZafCon54. */
    public ZafCon54(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
            arlDatCabPag=new ArrayList();
            arlDatDetPag=new ArrayList();
            arlDatNumRet=new ArrayList();
        }
        catch (CloneNotSupportedException e){
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
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butPro = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        panConFil = new javax.swing.JPanel();
        panFilEsp = new javax.swing.JPanel();
        panConFilDocPrv = new javax.swing.JPanel();
        panFilTipDoc = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panFilDocPrv = new javax.swing.JPanel();
        panFilPrv = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        panFilFec = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        txtCodTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lbCtaAcr = new javax.swing.JLabel();
        txtCodCtaAcr = new javax.swing.JTextField();
        txtDesCorCtaAcr = new javax.swing.JTextField();
        txtDesLarCtaAcr = new javax.swing.JTextField();
        butCtaAcr = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        lbCtaDeu = new javax.swing.JLabel();
        txtCodCtaDeu = new javax.swing.JTextField();
        txtDesCorCtaDeu = new javax.swing.JTextField();
        txtDesLarCtaDeu = new javax.swing.JTextField();
        butCtaDeu = new javax.swing.JButton();
        panDet = new javax.swing.JPanel();
        spnTblDet = new javax.swing.JScrollPane();
        tblDatDet = new javax.swing.JTable();
        panAsiDia = new javax.swing.JPanel();

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panPie.setPreferredSize(new java.awt.Dimension(100, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butPro.setMnemonic('P');
        butPro.setText("Procesar");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });
        panBot.add(butPro);

        jButton3.setMnemonic('r');
        jButton3.setText("Cerrar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        panBot.add(jButton3);

        panPie.add(panBot, java.awt.BorderLayout.CENTER);

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

        panPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panPie, java.awt.BorderLayout.SOUTH);

        panGen.setLayout(new java.awt.BorderLayout());

        optTod.setSelected(true);
        optTod.setActionCommand("Todos los registros");
        optTod.setIconTextGap(2);
        optTod.setLabel("Todos los registros");
        optTod.setPreferredSize(new java.awt.Dimension(113, 18));
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGen.add(optTod, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        optFil.setLabel("Sólo los registros que cumplan el criterio seleccionado");
        optFil.setPreferredSize(new java.awt.Dimension(281, 18));
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil, java.awt.BorderLayout.NORTH);

        panConFil.setLayout(new java.awt.BorderLayout());

        panFilEsp.setPreferredSize(new java.awt.Dimension(30, 100));
        panConFil.add(panFilEsp, java.awt.BorderLayout.WEST);

        panConFilDocPrv.setLayout(new java.awt.BorderLayout());

        panFilTipDoc.setLayout(new java.awt.BorderLayout());

        spnDat.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de documento"));

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
        spnDat.setViewportView(tblDat);

        panFilTipDoc.add(spnDat, java.awt.BorderLayout.CENTER);

        panConFilDocPrv.add(panFilTipDoc, java.awt.BorderLayout.CENTER);

        panFilDocPrv.setPreferredSize(new java.awt.Dimension(100, 130));
        panFilDocPrv.setLayout(new java.awt.BorderLayout());

        panFilPrv.setPreferredSize(new java.awt.Dimension(100, 24));
        panFilPrv.setLayout(null);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panFilPrv.add(lblPrv);
        lblPrv.setBounds(0, 2, 100, 20);

        txtCodPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrvActionPerformed(evt);
            }
        });
        txtCodPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrvFocusLost(evt);
            }
        });
        panFilPrv.add(txtCodPrv);
        txtCodPrv.setBounds(100, 2, 56, 20);

        txtDesLarPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarPrvActionPerformed(evt);
            }
        });
        txtDesLarPrv.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarPrvFocusLost(evt);
            }
        });
        panFilPrv.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(156, 2, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panFilPrv.add(butPrv);
        butPrv.setBounds(420, 2, 20, 20);

        panFilDocPrv.add(panFilPrv, java.awt.BorderLayout.NORTH);

        panFilFec.setLayout(new java.awt.BorderLayout());
        panFilDocPrv.add(panFilFec, java.awt.BorderLayout.CENTER);

        panConFilDocPrv.add(panFilDocPrv, java.awt.BorderLayout.SOUTH);

        panConFil.add(panConFilDocPrv, java.awt.BorderLayout.CENTER);

        panFil.add(panConFil, java.awt.BorderLayout.CENTER);

        panGen.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panGen);

        panRep.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(100, 76));
        panCab.setLayout(null);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(68, 4, 32, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 100, 20);

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
        txtDesCorTipDoc.setBounds(100, 4, 56, 20);

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
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(156, 4, 264, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(420, 4, 20, 20);

        lbCtaAcr.setText("Cuenta bancaria:");
        lbCtaAcr.setToolTipText("Cuenta");
        panCab.add(lbCtaAcr);
        lbCtaAcr.setBounds(0, 24, 90, 20);
        panCab.add(txtCodCtaAcr);
        txtCodCtaAcr.setBounds(68, 24, 32, 20);

        txtDesCorCtaAcr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaAcrActionPerformed(evt);
            }
        });
        txtDesCorCtaAcr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaAcrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaAcrFocusLost(evt);
            }
        });
        panCab.add(txtDesCorCtaAcr);
        txtDesCorCtaAcr.setBounds(100, 24, 80, 20);

        txtDesLarCtaAcr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaAcrActionPerformed(evt);
            }
        });
        txtDesLarCtaAcr.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaAcrFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaAcrFocusLost(evt);
            }
        });
        panCab.add(txtDesLarCtaAcr);
        txtDesLarCtaAcr.setBounds(180, 24, 240, 20);

        butCtaAcr.setText("...");
        butCtaAcr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaAcrActionPerformed(evt);
            }
        });
        panCab.add(butCtaAcr);
        butCtaAcr.setBounds(420, 24, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(444, 4, 110, 20);

        lbCtaDeu.setText("Cuenta deudora:");
        lbCtaDeu.setToolTipText("Cuenta");
        panCab.add(lbCtaDeu);
        lbCtaDeu.setBounds(0, 44, 100, 20);
        panCab.add(txtCodCtaDeu);
        txtCodCtaDeu.setBounds(68, 44, 32, 20);

        txtDesCorCtaDeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaDeuActionPerformed(evt);
            }
        });
        txtDesCorCtaDeu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaDeuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaDeuFocusLost(evt);
            }
        });
        panCab.add(txtDesCorCtaDeu);
        txtDesCorCtaDeu.setBounds(100, 44, 80, 20);

        txtDesLarCtaDeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaDeuActionPerformed(evt);
            }
        });
        txtDesLarCtaDeu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaDeuFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaDeuFocusLost(evt);
            }
        });
        panCab.add(txtDesLarCtaDeu);
        txtDesLarCtaDeu.setBounds(180, 44, 240, 20);

        butCtaDeu.setText("...");
        butCtaDeu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaDeuActionPerformed(evt);
            }
        });
        panCab.add(butCtaDeu);
        butCtaDeu.setBounds(420, 44, 20, 20);

        panRep.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

        tblDatDet.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblDet.setViewportView(tblDatDet);

        panDet.add(spnTblDet, java.awt.BorderLayout.CENTER);

        panRep.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicaci�n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexi�n si est� abierta.
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

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)){
            if (txtCodPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
            }
            else{
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)){
            if (txtDesLarPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
            }
            else{
                mostrarVenConPrv(2);
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
}//GEN-LAST:event_txtDesLarPrvFocusLost

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
// TODO add your handling code here:
    if(optFil.isSelected()){
        optTod.setSelected(false);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    }
    else{
        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        optTod.setSelected(true);
    }
}//GEN-LAST:event_optFilActionPerformed

private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
// TODO add your handling code here:
    if(optTod.isSelected()){
        optFil.setSelected(false);
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
        }
        objTblModDet.removeAllRows();
    }
    else
        optFil.setSelected(true);
}//GEN-LAST:event_optTodActionPerformed

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodCtaAcr.setText("");
                txtDesCorCtaAcr.setText("");
                txtDesLarCtaAcr.setText("");
            }
            else
            {
                mostrarVenConTipDoc(1);
            }
        }
        else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtCodCtaAcr.setText("");
                txtDesCorCtaAcr.setText("");
                txtDesLarCtaAcr.setText("");
            }
            else
            {
                mostrarVenConTipDoc(2);
            }
        }
        else
            txtDesLarTipDoc.setText(strDesLarTipDoc);
}//GEN-LAST:event_txtDesLarTipDocFocusLost

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

private void txtDesCorCtaAcrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaAcrActionPerformed
txtDesCorCtaAcr.transferFocus();
}//GEN-LAST:event_txtDesCorCtaAcrActionPerformed

private void txtDesCorCtaAcrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaAcrFocusGained
    strDesCorCtaAcr=txtDesCorCtaAcr.getText();
    txtDesCorCtaAcr.selectAll();
}//GEN-LAST:event_txtDesCorCtaAcrFocusGained

private void txtDesCorCtaAcrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaAcrFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesCorCtaAcr.getText().equalsIgnoreCase(strDesCorCtaAcr)){
            if (txtDesCorCtaAcr.getText().equals(""))
            {
                txtCodCtaAcr.setText("");
                txtDesLarCtaAcr.setText("");
                objTblModDet.removeAllRows();
            }
            else{
                mostrarVenConCtaAcr(1);
            }
        }
        else
            txtDesCorCtaAcr.setText(strDesCorCtaAcr);
}//GEN-LAST:event_txtDesCorCtaAcrFocusLost

private void txtDesLarCtaAcrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaAcrActionPerformed
    txtDesLarCtaAcr.transferFocus();
}//GEN-LAST:event_txtDesLarCtaAcrActionPerformed

private void txtDesLarCtaAcrFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaAcrFocusGained
    strDesLarCtaAcr=txtDesLarCtaAcr.getText();
    txtDesLarCtaAcr.selectAll();
}//GEN-LAST:event_txtDesLarCtaAcrFocusGained

private void txtDesLarCtaAcrFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaAcrFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarCtaAcr.getText().equalsIgnoreCase(strDesLarCtaAcr))
        {
            if (txtDesLarCtaAcr.getText().equals(""))
            {
                txtCodCtaAcr.setText("");
                txtDesCorCtaAcr.setText("");
                objTblModDet.removeAllRows();
            }
            else
            {
                mostrarVenConCtaAcr(2);
            }
        }
        else
            txtDesLarCtaAcr.setText(strDesLarCtaAcr);
}//GEN-LAST:event_txtDesLarCtaAcrFocusLost

private void butCtaAcrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaAcrActionPerformed
    mostrarVenConCtaAcr(0);
        if (!txtCodPrv.getText().equals("")){
        }
}//GEN-LAST:event_butCtaAcrActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
// TODO add your handling code here:
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
    if(isChangeTable(0)){
        if(isCamFilCon()){
            if (butCon.getText().equals("Consultar")){
                //objTblTotales.isActivo(false);
                blnCon=true;
                if (objThrGUIPagTot==null){
                    objThrGUIPagTot=new ZafThreadGUIPagTot();
                    objThrGUIPagTot.start();                

                }            
            }
            else{
                blnCon=false;
            }
        }
    }
    else{
        mostrarMsgInf("<HTML>El tipo de documento no se ha seleccionado.<BR>Seleccione por lo menos un Tipo de documento y vuelva a intentarlo.</HTML>");
    }
    
    

}//GEN-LAST:event_butConActionPerformed

private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
    exitForm(null);
}//GEN-LAST:event_jButton3ActionPerformed

private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
// TODO add your handling code here:
    if(isChangeTable(1)){
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>¿Está seguro que desea realizar esta operación?<BR>";
        strMsg+="</HTML>";
        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        if (butPro.getText().equals("Procesar")){
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                blnCon=true;
                lblMsgSis.setText("Procesando...");
                if (objThrGUIPro==null){
                    objThrGUIPro=new ZafThreadGUIPro();
                    objThrGUIPro.start();
                }
            }   
        }
        else{
            blnCon=false;
        }
    }
    else{
        mostrarMsgInf("<HTML>No se han realizado cambios.<BR> Verifique y vuelva a intentarlo.</HTML>");
    }

}//GEN-LAST:event_butProActionPerformed

private void txtDesCorCtaDeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaDeuActionPerformed
// TODO add your handling code here:
    txtDesCorCtaDeu.transferFocus();
}//GEN-LAST:event_txtDesCorCtaDeuActionPerformed

private void txtDesCorCtaDeuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaDeuFocusGained
// TODO add your handling code here:
    strDesCorCtaDeu=txtDesCorCtaDeu.getText();
    txtDesCorCtaDeu.selectAll();
}//GEN-LAST:event_txtDesCorCtaDeuFocusGained

private void txtDesCorCtaDeuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaDeuFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesCorCtaDeu.getText().equalsIgnoreCase(strDesCorCtaDeu)){
            if (txtDesCorCtaDeu.getText().equals(""))
            {
                txtCodCtaDeu.setText("");
                txtDesLarCtaDeu.setText("");
            }
            else{
                mostrarVenConCtaDeu(1);
            }
        }
        else
            txtDesCorCtaDeu.setText(strDesCorCtaDeu);
}//GEN-LAST:event_txtDesCorCtaDeuFocusLost

private void txtDesLarCtaDeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaDeuActionPerformed
// TODO add your handling code here:
    txtDesLarCtaDeu.transferFocus();
}//GEN-LAST:event_txtDesLarCtaDeuActionPerformed

private void txtDesLarCtaDeuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaDeuFocusGained
// TODO add your handling code here:
    strDesLarCtaDeu=txtDesLarCtaDeu.getText();
    txtDesLarCtaDeu.selectAll();
}//GEN-LAST:event_txtDesLarCtaDeuFocusGained

private void txtDesLarCtaDeuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaDeuFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarCtaDeu.getText().equalsIgnoreCase(strDesLarCtaDeu))
        {
            if (txtDesLarCtaDeu.getText().equals(""))
            {
                txtCodCtaDeu.setText("");
                txtDesCorCtaDeu.setText("");
            }
            else
            {
                mostrarVenConCtaDeu(2);
            }
        }
        else
            txtDesLarCtaDeu.setText(strDesLarCtaDeu);
}//GEN-LAST:event_txtDesLarCtaDeuFocusLost

private void butCtaDeuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaDeuActionPerformed
// TODO add your handling code here:
    mostrarVenConCtaDeu(0);
}//GEN-LAST:event_butCtaDeuActionPerformed

    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCon;
    private javax.swing.JButton butCtaAcr;
    private javax.swing.JButton butCtaDeu;
    private javax.swing.JButton butPro;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lbCtaAcr;
    private javax.swing.JLabel lbCtaDeu;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panConFil;
    private javax.swing.JPanel panConFilDocPrv;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilDocPrv;
    private javax.swing.JPanel panFilEsp;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFilPrv;
    private javax.swing.JPanel panFilTipDoc;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTblDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatDet;
    private javax.swing.JTextField txtCodCtaAcr;
    private javax.swing.JTextField txtCodCtaDeu;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCtaAcr;
    private javax.swing.JTextField txtDesCorCtaDeu;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCtaAcr;
    private javax.swing.JTextField txtDesLarCtaDeu;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();

            objDocLis=new ZafDocLis();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.6");//para codigo con autorizaciones es 0.16 y para produccion 0.13.1
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCtaDeu();
            configurarVenConCtaAcr();
            configurarVenConCtaDeuTbl();
            configurarVenConPrv();
            //Configurar los JTables.
            if(configurarTblDat())
                if(cargarTipoDocumentoUsuario())
                    configurarTblDet();
            
//            if(System.getProperty("os.name").equals("Linux")){
//                strNomSubRep="//Zafiro//Reportes_impresos//SubRptChq.jrxml";
//                strNomRepTuv="//Zafiro//Reportes_impresos//RptChq2.jrxml";
//                strNomRepCas="//Zafiro//Reportes_impresos//RptChqC.jrxml";
//                strNomRepNos="//Zafiro//Reportes_impresos//RptChqN.jrxml";
//                strNomRepDim="//Zafiro//Reportes_impresos//RptChqD.jrxml";
//                strNomRepDef="//Zafiro//Reportes_impresos//RptChq.jrxml";
//            }
//            else{
//                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
//                strNomRepTuv="C://Zafiro//Reportes_impresos//RptChq2.jrxml";
//                strNomRepCas="C://Zafiro//Reportes_impresos//RptChqC.jrxml";
//                strNomRepNos="C://Zafiro//Reportes_impresos//RptChqN.jrxml";
//                strNomRepDim="C://Zafiro//Reportes_impresos//RptChqD.jrxml";
//                strNomRepDef="C://Zafiro//Reportes_impresos//RptChq.jrxml";
//            }
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funci�n configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            
            objSelDat=new ZafSelectDate(new javax.swing.JFrame(),"d/m/y");
            objSelDat.chkSetSelected(true);
            panFilFec.add(objSelDat);
            objSelDat.setEnabled(false);
            objSelDat.setBounds(2, 2, 560, 80);
            objSelDat.chkSetSelected(false);
            
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Ali.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Nom.Tip.Doc.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(180);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil;
                boolean blnChkBef, blnChkAft;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    blnChkBef=objTblMod.getValueAt(intFil, INT_TBL_DAT_CHK)==null?false:(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHK).toString().equals("true")?true:false);
                }                
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    intFil=tblDat.getSelectedRow();
                    blnChkAft=objTblMod.getValueAt(intFil, INT_TBL_DAT_CHK)==null?false:(objTblMod.getValueAt(intFil, INT_TBL_DAT_CHK).toString().equals("true")?true:false);
                    if(blnChkBef==blnChkAft){
                    }
                    else{
                        objTblModDet.removeAllRows();
                    }
                    
                    if(optTod.isSelected()){
                        optTod.setSelected(false);
                        optFil.setSelected(true);
                    }
                }
            });
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    private boolean configurarTblDet(){
        boolean blnRes=true;
        try{
            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
            //Configurar ZafDatePicker:            
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(544, 4, 120, 20);
            datFecAux=null;
            //dtpFecDoc.setEnabled(false);
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodTipDoc.setEnabled(false);
            
            txtCodCtaAcr.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCtaAcr.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCtaAcr.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCtaAcr.setVisible(false);
            txtCodCtaAcr.setEditable(false);
            txtCodCtaAcr.setEnabled(false);
            
            txtCodCtaDeu.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCtaDeu.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCtaDeu.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCtaDeu.setVisible(false);
            txtCodCtaDeu.setEditable(false);
            txtCodCtaDeu.setEnabled(false);
            
            
            objAsiDia=new ZafAsiDia(objParSis);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //Configurar JTable: Establecer el modelo.
            vecDatDet=new Vector();    //Almacena los datos
            vecCabDet=new Vector(13);  //Almacena las cabeceras
            vecCabDet.clear();
            vecCabDet.add(INT_TBL_DAT_REP_LIN,"");
            vecCabDet.add(INT_TBL_DAT_REP_CHK,"");
            vecCabDet.add(INT_TBL_DAT_REP_NUM_DOC,"Núm.Doc.");
            vecCabDet.add(INT_TBL_DAT_REP_NUM_CHQ,"Núm.Chq.");
            vecCabDet.add(INT_TBL_DAT_REP_COD_PRV,"Cód.Prv.");
            vecCabDet.add(INT_TBL_DAT_REP_NOM_PRV,"Nom.Prv.");
            vecCabDet.add(INT_TBL_DAT_REP_VAL_DOC,"Val.Doc.");
            vecCabDet.add(INT_TBL_DAT_REP_COD_CTA_DEU,"Cód.Cta.Deu.");
            vecCabDet.add(INT_TBL_DAT_REP_NUM_CTA_DEU,"Núm.Cta.Deu.");
            vecCabDet.add(INT_TBL_DAT_REP_BUT_CTA_DEU,"");
            vecCabDet.add(INT_TBL_DAT_REP_NOM_CTA_DEU,"Nom.Cta.Deu.");
            vecCabDet.add(INT_TBL_DAT_REP_GLO_ASI_DIA,"Glo.Asi.Dia");
            vecCabDet.add(INT_TBL_DAT_REP_BUT_ANE,"");
            
            
            
            
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCabDet);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatDet.setModel(objTblModDet);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDatDet.setRowSelectionAllowed(true);
            tblDatDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDatDet);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_REP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_CHQ).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_REP_COD_PRV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_REP_NOM_PRV).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_REP_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_REP_COD_CTA_DEU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_CTA_DEU).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_REP_BUT_CTA_DEU).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_REP_NOM_CTA_DEU).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_REP_GLO_ASI_DIA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_REP_BUT_ANE).setPreferredWidth(30);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatDet.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDet=new ZafMouMotAdaDet();
            tblDatDet.getTableHeader().addMouseMotionListener(objMouMotAdaDet);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabDet=new ZafTblFilCab(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_REP_LIN).setCellRenderer(objTblFilCabDet);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_REP_CHK);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiDet=new ZafTblEdi(tblDatDet);


            objTblCelRenLblVal=new ZafTblCelRenLbl();
            objTblCelRenLblVal.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblVal.setTipoFormato(objTblCelRenLblVal.INT_FOR_NUM);
            objTblCelRenLblVal.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_REP_VAL_DOC).setCellRenderer(objTblCelRenLblVal);
            objTblCelRenLblVal=null;


            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDet=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setCellRenderer(objTblCelRenChkDet);
            objTblCelRenChkDet=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDet=new ZafTblCelEdiChk(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setCellEditor(objTblCelEdiChkDet);
            objTblCelEdiChkDet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChkDet.isChecked()){
                        if(isCamFilCon()){
                            objTblModDet.setValueAt(txtCodCtaDeu.getText(), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_CTA_DEU);
                            objTblModDet.setValueAt(txtDesCorCtaDeu.getText(), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NUM_CTA_DEU);
                            objTblModDet.setValueAt(txtDesLarCtaDeu.getText(), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NOM_CTA_DEU);
                        }
                    }
                    else{
                        objTblModDet.setValueAt("", tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_CTA_DEU);
                        objTblModDet.setValueAt("", tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NUM_CTA_DEU);
                        objTblModDet.setValueAt("", tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NOM_CTA_DEU);
                    }
                }
            });
            
          
            
            configurarVenConTipDoc();
            
            
            objTblCelRenButAne=new ZafTblCelRenBut();
            tblDatDet.getColumnModel().getColumn(INT_TBL_DAT_REP_BUT_ANE).setCellRenderer(objTblCelRenButAne);
            objCon54_01=new ZafCon54_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButAne= new ZafTblCelEdiButDlg(objCon54_01);
            tblDatDet.getColumnModel().getColumn(INT_TBL_DAT_REP_BUT_ANE).setCellEditor(objTblCelEdiButAne);
            objTblCelEdiButAne.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        //objCon09_01.colokObs(""+objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()));
                if(objSelDat.chkIsSelected())
                    objCon54_01.setFechaCorte("AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'");
                else
                    objCon54_01.setFechaCorte("");

                    objCon54_01.setCodigoProveedor(objTblModDet.getValueAt(tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_PRV).toString());
                    objCon54_01.setCodigoTipoDocumento(getTipoDocConPagTot());
                    objCon54_01.setCodigoCuentaDeudora(txtCodCtaAcr.getText());
                    
                    objCon54_01.cargarDatosRetener();
                }


                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intFil=tblDatDet.getSelectedRow();
                    int intCol=tblDatDet.getSelectedColumn();
                    //objTblMod.setValueAt(objCon09_01.retornaTexto(), intFil, intCol) ;
                }
            });
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_REP_CHK);
            vecAux.add("" + INT_TBL_DAT_REP_NUM_CTA_DEU);
            vecAux.add("" + INT_TBL_DAT_REP_BUT_CTA_DEU);
            vecAux.add("" + INT_TBL_DAT_REP_BUT_ANE);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);

            objTblBusDet=new ZafTblBus(tblDatDet);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_CTA_DEU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_REP_NOM_CTA_DEU).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            objTblCelRenButVcoCta=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_REP_BUT_CTA_DEU).setCellRenderer(objTblCelRenButVcoCta);
            objTblCelRenButVcoCta=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[3];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            int intColTbl[]=new int[3];
            intColTbl[0]=INT_TBL_DAT_REP_COD_CTA_DEU;
            intColTbl[1]=INT_TBL_DAT_REP_NUM_CTA_DEU;
            intColTbl[2]=INT_TBL_DAT_REP_NOM_CTA_DEU;
            
            objTblCelEdiButVcoCta=new ZafTblCelEdiButVco(tblDatDet, vcoCtaDeuTbl, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_REP_BUT_CTA_DEU).setCellEditor(objTblCelEdiButVcoCta);
            objTblCelEdiButVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                String strMe="";
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCtaDeuTbl.setCampoBusqueda(1);
                    vcoCtaDeuTbl.setCriterio1(7);
                    if(objParSis.getCodigoUsuario()!=1){
                        strMe="";
                        strMe+=" AND a2.co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strMe+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                        vcoCtaDeuTbl.setCondicionesSQL(strMe);
                    }
                    setPuntosCta();
                }
                public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoCta.isConsultaAceptada()){
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(1), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_CTA_DEU);
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(2), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NUM_CTA_DEU);
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(3), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NOM_CTA_DEU);
                        objTblModDet.setChecked(true, tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_CHK);
                    }
                }
            });
            
            
            
            objTblCelEdiTxtVcoCta=new ZafTblCelEdiTxtVco(tblDatDet, vcoCtaDeuTbl, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_CTA_DEU).setCellEditor(objTblCelEdiTxtVcoCta);
            objTblCelEdiTxtVcoCta.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strMe="";    
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoCtaDeuTbl.setCampoBusqueda(1);
                    vcoCtaDeuTbl.setCriterio1(7);
                    if(objParSis.getCodigoUsuario()!=1){
                        strMe="";
                        strMe+=" AND a2.co_tipdoc=" + txtCodTipDoc.getText() + "";
                        strMe+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                        vcoCtaDeuTbl.setCondicionesSQL(strMe);                        
                    }
                    setPuntosCta();
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoCta.isConsultaAceptada()){
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(1), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_CTA_DEU);
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(2), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NUM_CTA_DEU);
                        objTblModDet.setValueAt(vcoCtaDeuTbl.getValueAt(3), tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_NOM_CTA_DEU);
                        objTblModDet.setChecked(true, tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_CHK);
                    }
                }
            });
                
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_REP_COD_CTA_DEU, tblDatDet);
            objTblModDet.addSystemHiddenColumn(INT_TBL_DAT_REP_GLO_ASI_DIA, tblDatDet);
          

            //Libero los objetos auxiliares.
            tcmAux=null;  
            intColVen=null;
            intColTbl=null;
            
            
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private void setPuntosCta(){
        strAux=objTblCelEdiTxtVcoCta.getText();
        String strCodCtaOri=strAux;
        String strCodCtaDes="";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta=strCodCtaOri.length();
        int intLonCodCtaMen=intLonCodCta-1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig=intLonCodCta-2;
        if (strCodCtaOri.length()<=1)
            return;
        else{
//            if(  (strCodCtaOri.length() % 2) != 0 ){
                chrCtaOri=strCodCtaOri.charAt(1);
                if(chrCtaOri!='.'){
                    for (int i=0; i < strCodCtaOri.length(); i++){
                        if(i==0){
                            strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                            strCodCtaDes=strCodCtaDes+".";
                        }
                        else{
                            if(  (strCodCtaOri.length() % 2) == 0 ){
                                if(((i % 2)==0)&&(i<intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMenTreDig)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }
                            else{
                                if(((i % 2)==0)&&(i!=intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                    strCodCtaDes=strCodCtaDes+".";
                                }
                                if(((i % 2)==0)&&(i==intLonCodCtaMen)){
                                    strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                                else{
                                    if((i % 2)!= 0)
                                        strCodCtaDes=strCodCtaDes+strCodCtaOri.charAt(i);
                                }
                            }       
                        }
                    }
                    objTblCelEdiTxtVcoCta.setText(strCodCtaDes);
                }
        }
    }
    
    


    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funci�n muestra un mensaje de advertencia al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notif�quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }


    
    /**
     * Esta funci�n permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodPrv.setText("");
            strIdePrv="";
            txtDesLarPrv.setText("");
            strDirPrv="";
            objTblMod.removeAllRows();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            arlCam.add("tblActNumDoc");
            arlCam.add("a2.co_grpTipDoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Ref.Tbl.Act.");
            arlAli.add("Cod.Grp.Tbl.Tip.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar,";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=574";
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, ";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=574";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            

            //Ocultar columnas.
            int intColOcu[]=new int[3];
            intColOcu[0]=7;
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
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCtaAcr()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a2.tx_ubiCta, a1.st_aut";
            strSQL+=" FROM tbm_plaCta AS a1, tbm_detTipDoc AS a2";
            strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
            strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" ORDER BY a1.tx_codCta";
            //Ocultar columnas.
            vcoCtaAcr=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCtaAcr.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCtaDeu()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
            strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN ";
            strSQL+=" tbr_ctatipdocusr AS a2";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
            strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            strSQL+=" AND a1.tx_tipCta='D'";
            strSQL+=" ORDER BY a1.tx_codCta";
            //Ocultar columnas.
            vcoCtaDeu=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCtaDeu.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCtaDeuTbl()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cï¿½digo");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.tx_tipCta='D'";
                strSQL+=" ORDER BY a1.tx_codCta";
            }
            else
            {
//                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
//                strSQL+=" FROM tbm_placta as a1";
//                strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
//                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
//                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
//                strSQL+=" AND a1.tx_tipCta='D'";
//                strSQL+=" ORDER BY a1.tx_codCta";
                
                
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN ";
                strSQL+=" tbr_ctatipdocusr AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.tx_tipCta='D'";
//                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
//                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                
                strSQL+=" ORDER BY a1.tx_codCta";
                
                
                
                
                
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=1;
            vcoCtaDeuTbl=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCtaDeuTbl.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCtaDeuTbl.setCampoBusqueda(1);
            vcoCtaDeuTbl.setCriterio1(7);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        String strTitVenCon="";
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
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            strTitVenCon="Listado de Proveedores";
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_prv='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else{
                strSQL="";
                strSQL+="SELECT a2.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.st_prv='S'";
                strSQL+=" ORDER BY a2.tx_nom";
            }
            
            
            
            
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    




    

    
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else
                        {
                            txtDesLarPrv.setText(strDesLarPrv);
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
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
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis(){
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
    }   

    


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripci�n larga del tipo de documento";
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
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaDet extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_REP_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_REP_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_DAT_REP_COD_PRV:
                    strMsg="Código del proveedor";
                    break;
                case INT_TBL_DAT_REP_NOM_PRV:
                    strMsg="Nombre del proveedor";
                    break;
                case INT_TBL_DAT_REP_VAL_DOC:
                    strMsg="Valor del documento";
                    break;
                case INT_TBL_DAT_REP_COD_CTA_DEU:
                    strMsg="Código de cuenta deudora";
                    break;
                case INT_TBL_DAT_REP_NUM_CTA_DEU:
                    strMsg="Número de cuenta deudora";
                    break;
                case INT_TBL_DAT_REP_NOM_CTA_DEU:
                    strMsg="Nombre de cuenta deudora";
                    break;
                case INT_TBL_DAT_REP_GLO_ASI_DIA:
                    strMsg="Glosa de asiento de diario";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    


    private boolean cargarTipoDocumentoUsuario(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a2.co_loc, a2.co_tipdoc, a2.tx_desCor, a2.tx_desLar, a2.co_ctaDeb, a2.co_ctaHab";
                    strSQL+=" FROM ";
                    strSQL+=" tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a2.co_tipDoc";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a2.co_loc, a2.co_tipdoc, a2.tx_desCor, a2.tx_desLar, a2.co_ctaDeb, a2.co_ctaHab";
                    strSQL+=" FROM tbr_tipDocUsr AS a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a2.st_reg NOT IN('I','E')";
                    strSQL+=" ORDER BY a2.co_tipDoc";
                }

                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_CHK, "");
                    vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                    vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                    vecDat.add(vecReg);
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
   
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
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
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                    }
                    break;
                case 1: //B�squeda directa por "Descripci�n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
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
                            strTblActNumDoc=vcoTipDoc.getValueAt(6);
                            strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTblActNumDoc=vcoTipDoc.getValueAt(6);
                        strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
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
                            strTblActNumDoc=vcoTipDoc.getValueAt(6);
                            strCodGrpTipDoc=vcoTipDoc.getValueAt(7);
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaAcr(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que s�lo se pueda seleccionar la "Cuenta" si se seleccion� el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCtaAcr.setText("");
                txtDesCorCtaAcr.setText("");
                txtDesLarCtaAcr.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN ";
                strSQL+=" tbm_dettipdoc AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" ORDER BY a1.tx_codCta";
                vcoCtaAcr.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCtaAcr.setCampoBusqueda(1);
                        vcoCtaAcr.show();
                        if (vcoCtaAcr.getSelectedButton()==vcoCtaAcr.INT_BUT_ACE)
                        {
                            txtCodCtaAcr.setText(vcoCtaAcr.getValueAt(1));
                            txtDesCorCtaAcr.setText(vcoCtaAcr.getValueAt(2));
                            txtDesLarCtaAcr.setText(vcoCtaAcr.getValueAt(3));
                            objTblModDet.removeAllRows();
                        }
                        break;
                    case 1: //B�squeda directa por "N�mero de cuenta".
                        if (vcoCtaAcr.buscar("a1.tx_codCta", txtDesCorCtaAcr.getText()))
                        {
                            txtCodCtaAcr.setText(vcoCtaAcr.getValueAt(1));
                            txtDesCorCtaAcr.setText(vcoCtaAcr.getValueAt(2));
                            txtDesLarCtaAcr.setText(vcoCtaAcr.getValueAt(3));
                            objTblModDet.removeAllRows();
                        }
                        else
                        {
                            vcoCtaAcr.setCampoBusqueda(0);
                            vcoCtaAcr.setCriterio1(11);
                            vcoCtaAcr.cargarDatos();
                            vcoCtaAcr.show();
                            if (vcoCtaAcr.getSelectedButton()==vcoCtaAcr.INT_BUT_ACE)
                            {
                                txtCodCtaAcr.setText(vcoCtaAcr.getValueAt(1));
                                txtDesCorCtaAcr.setText(vcoCtaAcr.getValueAt(2));
                                txtDesLarCtaAcr.setText(vcoCtaAcr.getValueAt(3));
                                objTblModDet.removeAllRows();
                            }
                            else
                            {
                                txtDesCorCtaAcr.setText(strDesCorCtaAcr);
                            }
                        }
                        break;
                    case 2: //B�squeda directa por "Descripci�n larga".
                        if (vcoCtaAcr.buscar("a1.tx_desLar", txtDesLarCtaAcr.getText()))
                        {
                            txtCodCtaAcr.setText(vcoCtaAcr.getValueAt(1));
                            txtDesCorCtaAcr.setText(vcoCtaAcr.getValueAt(2));
                            txtDesLarCtaAcr.setText(vcoCtaAcr.getValueAt(3));
                            objTblModDet.removeAllRows();
                        }
                        else
                        {
                            vcoCtaAcr.setCampoBusqueda(1);
                            vcoCtaAcr.setCriterio1(11);
                            vcoCtaAcr.cargarDatos();
                            vcoCtaAcr.show();
                            if (vcoCtaAcr.getSelectedButton()==vcoCtaAcr.INT_BUT_ACE)
                            {
                                txtCodCtaAcr.setText(vcoCtaAcr.getValueAt(1));
                                txtDesCorCtaAcr.setText(vcoCtaAcr.getValueAt(2));
                                txtDesLarCtaAcr.setText(vcoCtaAcr.getValueAt(3));
                                objTblModDet.removeAllRows();
                            }
                            else
                            {
                                txtDesLarCtaAcr.setText(strDesLarCtaAcr);
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
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCtaDeu(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que s�lo se pueda seleccionar la "Cuenta" si se seleccion� el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCtaDeu.setText("");
                txtDesCorCtaDeu.setText("");
                txtDesLarCtaDeu.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
                if(objParSis.getCodigoUsuario()==1){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }
                else{
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar";
                    strSQL+=" FROM tbm_plaCta AS a1 INNER JOIN ";
                    strSQL+=" tbr_ctatipdocusr AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText();
                    strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a1.tx_tipCta='D'";
                    strSQL+=" ORDER BY a1.tx_codCta";
                }



                vcoCtaDeu.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCtaDeu.setCampoBusqueda(1);
                        vcoCtaDeu.show();
                        if (vcoCtaDeu.getSelectedButton()==vcoCtaDeu.INT_BUT_ACE)
                        {
                            txtCodCtaDeu.setText(vcoCtaDeu.getValueAt(1));
                            txtDesCorCtaDeu.setText(vcoCtaDeu.getValueAt(2));
                            txtDesLarCtaDeu.setText(vcoCtaDeu.getValueAt(3));
                        }
                        break;
                    case 1: //B�squeda directa por "N�mero de cuenta".
                        if (vcoCtaDeu.buscar("a1.tx_codCta", txtDesCorCtaDeu.getText()))
                        {
                            txtCodCtaDeu.setText(vcoCtaDeu.getValueAt(1));
                            txtDesCorCtaDeu.setText(vcoCtaDeu.getValueAt(2));
                            txtDesLarCtaDeu.setText(vcoCtaDeu.getValueAt(3));
                        }
                        else
                        {
                            vcoCtaDeu.setCampoBusqueda(0);
                            vcoCtaDeu.setCriterio1(11);
                            vcoCtaDeu.cargarDatos();
                            vcoCtaDeu.show();
                            if (vcoCtaDeu.getSelectedButton()==vcoCtaDeu.INT_BUT_ACE)
                            {
                                txtCodCtaDeu.setText(vcoCtaDeu.getValueAt(1));
                                txtDesCorCtaDeu.setText(vcoCtaDeu.getValueAt(2));
                                txtDesLarCtaDeu.setText(vcoCtaDeu.getValueAt(3));
                            }
                            else
                            {
                                txtDesCorCtaDeu.setText(strDesCorCtaDeu);
                            }
                        }
                        break;
                    case 2: //B�squeda directa por "Descripci�n larga".
                        if (vcoCtaDeu.buscar("a1.tx_desLar", txtDesLarCtaDeu.getText()))
                        {
                            txtCodCtaDeu.setText(vcoCtaDeu.getValueAt(1));
                            txtDesCorCtaDeu.setText(vcoCtaDeu.getValueAt(2));
                            txtDesLarCtaDeu.setText(vcoCtaDeu.getValueAt(3));
                        }
                        else
                        {
                            vcoCtaDeu.setCampoBusqueda(1);
                            vcoCtaDeu.setCriterio1(11);
                            vcoCtaDeu.cargarDatos();
                            vcoCtaDeu.show();
                            if (vcoCtaDeu.getSelectedButton()==vcoCtaDeu.INT_BUT_ACE)
                            {
                                txtCodCtaDeu.setText(vcoCtaDeu.getValueAt(1));
                                txtDesCorCtaDeu.setText(vcoCtaDeu.getValueAt(2));
                                txtDesLarCtaDeu.setText(vcoCtaDeu.getValueAt(3));
                            }
                            else
                            {
                                txtDesLarCtaDeu.setText(strDesLarCtaDeu);
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
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    
 

    
    private class ZafThreadGUIPagTot extends Thread{
        public void run(){
            if (!cargarRegPagTot()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUIPagTot=null;
        }
    }
    
    private String getTipoDocConPagTot(){
        String strTipDocCon="";
        int p=0;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    if(p==0){
                        strTipDocCon+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString() + "";
                        p++;
                    }
                    else{
                        strTipDocCon+=",";
                        strTipDocCon+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString() + "";
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipDocCon;
        
    }
    
    
    
    private boolean cargarRegPagTot(){
        boolean blnRes=true;
        int intNumTotReg, i;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT SUM(nd_dif) AS nd_val FROM(";
                strSQL+=" 	SELECT z.co_cli, z.tx_nom, (z.nd_val + z.nd_valApl) AS nd_dif FROM(";
                strSQL+=" 		SELECT x.co_cli, x.tx_nom, x.nd_val,";
                strSQL+=" 			CASE WHEN y.nd_valApl IS NULL THEN cast(0.00 as numeric) ELSE y.nd_valApl END AS nd_valApl FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,";
                strSQL+=" 			a1.co_cli, a3.tx_nom, SUM(a2.mo_pag + a2.nd_abo) as nd_val";
                strSQL+=" 			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" 			INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                strSQL+=" 			AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN (";
                strSQL+=" 				SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+=" 						      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+=" 						      FROM tbm_cabTipDoc AS b1";
                strSQL+=" 						      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 						      AND b1.st_reg='A') AS a";
                strSQL+=" 					      INNER JOIN(";
                strSQL+=" 						      SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+=" 							      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+=" 							      FROM tbm_visBueMovInv AS b1";
                strSQL+=" 							      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + " AND b1.st_reg='A'";
                strSQL+=" 							      ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+=" 						      ) AS x";
                strSQL+=" 						      GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+=" 					      ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+=" 			) AS p";
                strSQL+=" 			ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 			AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg NOT IN ('I','E')";
                strSQL+=" 			AND a2.st_reg IN('A','C') AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                       AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                if(objSelDat.chkIsSelected())
                    strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                if( ! txtCodPrv.getText().toString().equals(""))
                    strSQL+=" AND a1.co_cli IN(" + txtCodPrv.getText() + ")";
                strSQL+=" AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.ne_numDoc, a1.co_cli, a3.tx_nom";
                strSQL+=" 			HAVING SUM(a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" 			) AS x";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT b1.co_emp, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel,";
                strSQL+=" 				(b2.mo_pag + b2.nd_abo) AS nd_valApl";
                strSQL+=" 				FROM tbr_cabMovInv AS b1 INNER JOIN tbm_pagMovInv AS b2";
                strSQL+=" 				ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 				WHERE b1.co_tipDoc=4 AND (b2.mo_pag+b2.nd_abo)<0";
                strSQL+=" 				AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL) AND b2.st_reg IN ('A','C')";
                strSQL+=" 			) AS y";
                strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_loc=y.co_locRel AND x.co_tipDoc=y.co_tipDocRel AND x.co_doc=y.co_docRel";
                strSQL+=" 		GROUP BY x.co_cli, x.tx_nom, x.nd_val, y.nd_valApl";
                strSQL+=" 		) AS z";
                strSQL+=" 	WHERE (z.nd_val + z.nd_valApl)>0";
                strSQL+=" ) AS a";
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                strSQL="";
                strSQL+=" SELECT co_cli, tx_nom, SUM(nd_dif) AS nd_val FROM(";
                strSQL+=" 	SELECT z.co_cli, z.tx_nom, (z.nd_val + z.nd_valApl) AS nd_dif FROM(";
                strSQL+=" 		SELECT x.co_cli, x.tx_nom, x.nd_val,";
                strSQL+=" 			CASE WHEN y.nd_valApl IS NULL THEN cast(0.00 as numeric) ELSE y.nd_valApl END AS nd_valApl FROM(";
                strSQL+=" 			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,";
                strSQL+=" 			a1.co_cli, a3.tx_nom, SUM(a2.mo_pag + a2.nd_abo) as nd_val";
                strSQL+=" 			FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" 			INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                strSQL+=" 			AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN (";
                strSQL+=" 				SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+=" 						      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+=" 						      FROM tbm_cabTipDoc AS b1";
                strSQL+=" 						      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 						      AND b1.st_reg='A') AS a";
                strSQL+=" 					      INNER JOIN(";
                strSQL+=" 						      SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+=" 							      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+=" 							      FROM tbm_visBueMovInv AS b1";
                strSQL+=" 							      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + " AND b1.st_reg='A'";
                strSQL+=" 							      ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+=" 						      ) AS x";
                strSQL+=" 						      GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+=" 					      ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+=" 			) AS p";
                strSQL+=" 			ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 			AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg NOT IN ('I','E')";
                strSQL+=" 			AND a2.st_reg IN('A','C') AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                       AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                if(objSelDat.chkIsSelected())
                    strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                if( ! txtCodPrv.getText().toString().equals(""))
                    strSQL+=" AND a1.co_cli IN(" + txtCodPrv.getText() + ")";
                strSQL+=" AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
                strSQL+=" 			GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.ne_numDoc, a1.co_cli, a3.tx_nom";
                strSQL+=" 			HAVING SUM(a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" 			) AS x";
                strSQL+=" 			LEFT OUTER JOIN(";
                strSQL+=" 				SELECT b1.co_emp, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel,";
                strSQL+=" 				(b2.mo_pag + b2.nd_abo) AS nd_valApl";
                strSQL+=" 				FROM tbr_cabMovInv AS b1 INNER JOIN tbm_pagMovInv AS b2";
                strSQL+=" 				ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 				WHERE b1.co_tipDoc=4 AND (b2.mo_pag+b2.nd_abo)<0";
                strSQL+=" 				AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL) AND b2.st_reg IN ('A','C')";
                strSQL+=" 			) AS y";
                strSQL+=" 		ON x.co_emp=y.co_emp AND x.co_loc=y.co_locRel AND x.co_tipDoc=y.co_tipDocRel AND x.co_doc=y.co_docRel";
                strSQL+=" 		GROUP BY x.co_cli, x.tx_nom, x.nd_val, y.nd_valApl";
                strSQL+=" 		) AS z";
                strSQL+=" 	WHERE (z.nd_val + z.nd_valApl)>0";
                strSQL+=" ) AS a";
                strSQL+=" GROUP BY co_cli, tx_nom";
                System.out.println("SQL:  " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while(rst.next()){
                    if (blnCon){
                        vecRegDet=new Vector();
                        vecRegDet.add(INT_TBL_DAT_REP_LIN, "");
                        vecRegDet.add(INT_TBL_DAT_REP_CHK, "");
                        vecRegDet.add(INT_TBL_DAT_REP_NUM_DOC, "0");
                        vecRegDet.add(INT_TBL_DAT_REP_NUM_CHQ, "0");
                        vecRegDet.add(INT_TBL_DAT_REP_COD_PRV, "" + rst.getString("co_cli"));
                        vecRegDet.add(INT_TBL_DAT_REP_NOM_PRV, "" + rst.getString("tx_nom"));
                        vecRegDet.add(INT_TBL_DAT_REP_VAL_DOC, "" + rst.getString("nd_val"));
                        vecRegDet.add(INT_TBL_DAT_REP_COD_CTA_DEU, "");
                        vecRegDet.add(INT_TBL_DAT_REP_NUM_CTA_DEU, "");
                        vecRegDet.add(INT_TBL_DAT_REP_BUT_CTA_DEU, null);
                        vecRegDet.add(INT_TBL_DAT_REP_NOM_CTA_DEU, "");
                        vecRegDet.add(INT_TBL_DAT_REP_GLO_ASI_DIA, "");
                        vecRegDet.add(INT_TBL_DAT_REP_BUT_ANE, null);
                        vecDatDet.add(vecRegDet);
                        
                        i++;
                        pgrSis.setValue(i);
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros");
                    }
                    else
                    {                        
                        break;
                    }

                }
                //Asignar vectores al modelo.
                objTblModDet.setData(vecDatDet);
                tblDatDet.setModel(objTblModDet);
                vecDatDet.clear();
                pgrSis.setValue(0);
                butCon.setText("Consultar");

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatDet.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatDet.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**La funci�n ZafThreadGUIPro almacena en una tabla temporal los datos necesarios para
     *luego poder realizar el proceso de unificaci�n de clientes en todas las compa��as
     */
    private class ZafThreadGUIPro extends Thread{
        public void run(){
            if(isCamFil()){
                if(procesar()){
                    mostrarMsgInf("<HTML>El proceso guardar se realizó con éxito.</HTML>");
                    lblMsgSis.setText("Listo");

//                    para el proceso de impresión
                    mostrarMsgInf("<HTML>Las cheques generados serán impresas a continuacion.</HTML>");
                    lblMsgSis.setText("Imprimiendo...");
                    if(procesoImprimir()){
                        mostrarMsgInf("<HTML>El proceso de impresión se realizó con éxito.</HTML>");
                        lblMsgSis.setText("Listo");
                    }
                    else{
                        mostrarMsgInf("<HTML>El proceso de impresión no se pudo realizar</HTML>");
                        lblMsgSis.setText("Proceso de impresión fallido");
                    }
                }
                else{
                    mostrarMsgInf("<HTML>El proceso guardar no se pudo realizar</HTML>");
                    lblMsgSis.setText("Proceso guardar fallido");
                }
            }
            
            objThrGUIPro=null;
            //Inicializar objetos si no se pudo cargar los datos.
            butPro.setText("Procesar");
            
            
        }
    }
    
    
    private boolean procesar(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(guardaArregloCabeceraDocumento()){
                    if(guardaArregloDetalleDocumento()){
                        if(insertar_tbmCabPag()){
                            if(insertar_tbmDetPag()){
                                if(actualizar_tbmPagMovInv()){//dentro de esta funcion llamar a insertarDiario.
                                    if(insertarDiario()){
                                        con.commit();
                                        //con.rollback();
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
                        blnRes=false;
                }
                else
                    blnRes=false;
                con.close();
                con=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }

        return blnRes;
    }
    
    
    private String getCodigoProveedorSeleccionado(){
        boolean blnRes=true;
        String strLin;
        String strCodPrv="";
        int p=0;
        try{
            for(int i=0;i<objTblModDet.getRowCountTrue(); i++){
                strLin=objTblModDet.getValueAt(i, INT_TBL_DAT_REP_LIN)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_REP_LIN).toString();
                if(strLin.equals("M")){
                    if(objTblModDet.isChecked(i, INT_TBL_DAT_REP_CHK)){
                        if(p==0){
                            strCodPrv+=objTblModDet.getValueAt(i, INT_TBL_DAT_REP_COD_PRV)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_REP_COD_PRV).toString();
                            p++;
                        }
                        else{
                            strCodPrv+=",";
                            strCodPrv+=objTblModDet.getValueAt(i, INT_TBL_DAT_REP_COD_PRV)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_REP_COD_PRV).toString();
                        }
                        
                        
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return strCodPrv;
    }
    
    
    
 
    private boolean guardaArregloCabeceraDocumento(){
        boolean blnRes=true;
        int intUltReg;
        arlDatCabPag.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabPag AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                //intUltReg++;
                
//                strSQL="";
//                strSQL+=" SELECT SUM(nd_valDoc) AS nd_valDoc, x.co_cli, x.tx_nom, x.tx_ide, x.tx_dir";
//                strSQL+=" , x.co_ben, x.tx_benChq, x.fe_recchq FROM(";
//                strSQL+="SELECT SUM(a2.mo_pag+a2.nd_abo) AS nd_valDoc, b2.co_cli, b2.tx_nom, b2.tx_ide, b2.tx_dir";
//                strSQL+=" , a1.co_ben, a1.tx_benChq, ";
//                strSQL+=" CASE WHEN a2.fe_recchq IS NULL THEN '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd") + "'";
//                strSQL+=" ELSE a2.fe_recchq END AS fe_recchq";
//                strSQL+=" FROM tbm_cabMovInv AS a1 ";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" INNER JOIN(";
//                strSQL+=" 	SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
//                strSQL+=" 			      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
//                strSQL+=" 			       FROM tbm_cabTipDoc AS b1";
//                strSQL+=" 			       WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" 			       AND b1.st_reg='A') AS a";
//                strSQL+=" 		       INNER JOIN(";
//                strSQL+=" 			       SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
//                strSQL+=" 				       SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
//                strSQL+=" 				       FROM tbm_visBueMovInv AS b1";
//                strSQL+=" 				       WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" 				       AND b1.st_reg='A'";
//                strSQL+=" 				       ORDER BY b1.co_doc, b1.co_visBue";
//                strSQL+=" 			       ) AS x";
//                strSQL+=" 			       GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
//                strSQL+=" 		       ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
//                strSQL+=" 		  ) AS p";
//                strSQL+=" 		  ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
//                strSQL+=" INNER JOIN tbm_cli AS b2 ON a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli";
//                strSQL+=" 		  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+=" 		  AND (a2.mo_pag + a2.nd_abo) > 0";
//                strSQL+=" 		  AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg IN ('A','R','C','F')";
//                strSQL+=" 		  AND a2.st_reg IN('A','C')";
//                strSQL+="                 AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
//                strSQL+="                 AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
//                strSQL+="                 AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
//                strSQL+=" GROUP BY b2.co_cli, b2.tx_nom, b2.tx_ide, b2.tx_dir, a1.co_ben, a1.tx_benChq, a2.fe_recchq";
//                strSQL+=" ORDER BY b2.co_cli";
//                strSQL+=" ) AS x";
//                strSQL+=" GROUP BY x.co_cli, x.tx_nom, x.tx_ide, x.tx_dir";
//                strSQL+=" , x.co_ben, x.tx_benChq, x.fe_recchq";
//                strSQL+=" ORDER BY x.co_cli,x.co_ben";



                strSQL="";
                strSQL+="SELECT SUM(z.nd_val + z.nd_valApl) AS nd_valDoc, z.co_cli, z.tx_nom, z.tx_ide, z.tx_dir";
                strSQL+=" , z.co_ben, z.tx_benChq, z.fe_recchq FROM(";
                strSQL+=" 	SELECT x.co_emp, x.co_loc, x.co_tipdoc, x.co_doc, x.ne_numDoc,";
                strSQL+=" 		x.co_cli, x.tx_nom, x.nd_val";
                strSQL+=" 		,CASE WHEN y.nd_valApl IS NULL THEN cast(0.00 as numeric) ELSE y.nd_valApl END AS nd_valApl";
                strSQL+=" 		,x.tx_ide, x.tx_dir, x.co_ben, x.tx_benChq, x.fe_recchq FROM(";
                strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,";
                strSQL+=" 		a1.co_cli, a3.tx_nom, SUM(a2.mo_pag + a2.nd_abo) as nd_val";
                strSQL+=" 		,a1.tx_ruc AS tx_ide, a1.tx_dircli AS tx_dir, a1.co_ben, a1.tx_benChq,";
                strSQL+=" CASE WHEN a2.fe_recchq IS NULL THEN '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd") + "'";
                strSQL+=" ELSE a2.fe_recchq END AS fe_recchq";
                strSQL+=" 		FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" 		INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                strSQL+=" 		AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 		INNER JOIN (";
                strSQL+=" 			SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+=" 					      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+=" 					      FROM tbm_cabTipDoc AS b1";
                strSQL+=" 					      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 					      AND b1.st_reg='A') AS a";
                strSQL+=" 				      INNER JOIN(";
                strSQL+=" 					      SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+=" 						      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+=" 						      FROM tbm_visBueMovInv AS b1";
                strSQL+=" 						      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + " AND b1.st_reg='A'";
                strSQL+=" 						      ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+=" 					      ) AS x";
                strSQL+=" 					      GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+=" 				      ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+=" 		) AS p";
                strSQL+=" 		ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" 		AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg NOT IN ('I','E')";
                strSQL+=" 		AND a2.st_reg IN('A','C') AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="               AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                strSQL+="               AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
                strSQL+="               AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
                strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.ne_numDoc, a1.co_cli, a3.tx_nom, a1.tx_ruc, a1.tx_dircli, a1.co_ben, a1.tx_benChq, a2.fe_recchq";
                strSQL+=" 		HAVING SUM(a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" 		) AS x";
                strSQL+=" 		LEFT OUTER JOIN(";
                strSQL+=" 			SELECT b1.co_emp, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel,";
                strSQL+=" 			(b2.mo_pag + b2.nd_abo) AS nd_valApl";
                strSQL+=" 			FROM tbr_cabMovInv AS b1 INNER JOIN tbm_pagMovInv AS b2";
                strSQL+=" 			ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 			WHERE b1.co_tipDoc=4 AND (b2.mo_pag+b2.nd_abo)<0";
                strSQL+=" 			AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL) AND b2.st_reg IN ('A','C')";
                strSQL+=" 		) AS y";
                strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_loc=y.co_locRel AND x.co_tipDoc=y.co_tipDocRel AND x.co_doc=y.co_docRel";
                strSQL+=" ) AS z";
                strSQL+=" WHERE (z.nd_val + z.nd_valApl)>0";
                strSQL+=" GROUP BY z.co_cli, z.tx_nom, z.tx_ide, z.tx_dir";
                strSQL+=" , z.co_ben, z.tx_benChq, z.fe_recchq";
                System.out.println("SQL CABECERA: " + strSQL);
                
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    intUltReg++;
                    arlRegCabPag=new ArrayList();
                    arlRegCabPag.add(INT_ARL_CAB_COD_EMP,       "" + objParSis.getCodigoEmpresa());
                    arlRegCabPag.add(INT_ARL_CAB_COD_LOC,       "" + objParSis.getCodigoLocal());
                    arlRegCabPag.add(INT_ARL_CAB_COD_TIP_DOC,   "" + txtCodTipDoc.getText());
                    arlRegCabPag.add(INT_ARL_CAB_COD_DOC,       "" + intUltReg);
                    String strTxtFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                    arlRegCabPag.add(INT_ARL_CAB_FEC_DOC,       "" + strTxtFecDoc);
                    String strRstFecVenChq=rst.getObject("fe_recchq")==null?"":(rst.getString("fe_recchq").toString().equals("Null")?"":(objUti.formatearFecha(rst.getString("fe_recchq"),"yyyy-MM-dd", "yyyy-MM-dd")));
                    if(strRstFecVenChq.equals(""))
                        arlRegCabPag.add(INT_ARL_CAB_FEC_VEN,       "" + strTxtFecDoc);
                    else
                        arlRegCabPag.add(INT_ARL_CAB_FEC_VEN,       "" + strRstFecVenChq);
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_UNO,   "0");
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_DOS,   "0");
                    arlRegCabPag.add(INT_ARL_CAB_COD_CTA_ACR,   "" + txtCodCtaAcr.getText() + "");
                    arlRegCabPag.add(INT_ARL_CAB_COD_CTA_DEU,   "");
                    arlRegCabPag.add(INT_ARL_CAB_COD_CLI,       "" + rst.getString("co_cli"));
                    arlRegCabPag.add(INT_ARL_CAB_RUC_CLI,       "" + rst.getString("tx_ide"));
                    arlRegCabPag.add(INT_ARL_CAB_NOM_CLI,       "" + rst.getString("tx_nom"));
                    arlRegCabPag.add(INT_ARL_CAB_DIR_CLI,       "" + rst.getString("tx_dir"));
                    arlRegCabPag.add(INT_ARL_CAB_MON_DOC,       "" + rst.getString("nd_valDoc"));
                    arlRegCabPag.add(INT_ARL_CAB_COD_BEN,       "" + rst.getObject("co_ben")==null?"":rst.getString("co_ben"));
                    arlRegCabPag.add(INT_ARL_CAB_NOM_BEN,       "" + rst.getString("tx_benChq"));
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_CAB_MOV_INV,       "");
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_PRV,       "");

                    arlDatCabPag.add(arlRegCabPag);
                }
                System.out.println("CABECERA: " + arlDatCabPag.toString());
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    private boolean guardaArregloDetalleDocumento(){
        boolean blnRes=true;
        arlDatDetPag.clear();
        String strRstCodEmp="", strRstCodPrv="", strRstFecVenChq="", strRstCodBen="";
        String strArlCabCodEmp="", strArlCabCodPrv="", strArlCabFecVenChq="", strArlCabCodBen="";
        String strArlDetCodEmpUlt="", strArlDetCodPrvUlt="", strArlDetFecVenChqUlt="", strArlDetCodBenUlt="";
        String strRstNumPed="";
        int j=0, m=0;
        String strGloDoc="", strGloDocAnt="";
        String strNumPed="", strNumPedAnt="";
        strAux="";
        try{
            if(con!=null){
                stm=con.createStatement();
//                strSQL="";
//                strSQL+="SELECT a1.co_emp,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.co_cli, a1.tx_numPed, ";
//                strSQL+=" 		  a2.fe_recchq, (a2.mo_pag + a2.nd_abo) AS nd_valDoc, a1.ne_numDoc";
//                strSQL+=" 		  , a1.co_ben, a1.tx_benChq";
//                strSQL+="                   FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
//                strSQL+="                   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
//                strSQL+="                    AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+="                  INNER JOIN (";
//                strSQL+="                   		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
//                strSQL+="                                                        SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
//                strSQL+="                                                        FROM tbm_cabTipDoc AS b1";
//                strSQL+="                                                        WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="                                                        AND b1.st_reg='A') AS a";
//                strSQL+="                                                INNER JOIN(";
//                strSQL+="                                                        SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
//                strSQL+="                                                                SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
//                strSQL+="                                                                FROM tbm_visBueMovInv AS b1";
//                strSQL+="                                                                WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="                                                                AND b1.st_reg='A'";
//                strSQL+="                                                                ORDER BY b1.co_doc, b1.co_visBue";
//                strSQL+="                                                        ) AS x";
//                strSQL+="                                                        GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
//                strSQL+="                                                ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
//                strSQL+="                   ) AS p  ";
//                strSQL+="                 ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
//                strSQL+=" 		INNER JOIN tbm_cli AS b2 ON a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli ";
//                strSQL+="                   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="                   AND (a2.mo_pag + a2.nd_abo) > 0";
//                strSQL+="                   AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg IN ('A','R','C','F')";
//                strSQL+="                   AND a2.st_reg IN('A','C')";
//                strSQL+="                   AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
//                strSQL+="                   AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
//                strSQL+=" AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
//                strSQL+="  		 GROUP BY a1.co_emp,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.co_cli, a1.tx_numPed, ";
//                strSQL+=" 		  a2.fe_recchq, a1.ne_numDoc,a2.mo_pag,a2.nd_abo, a1.co_ben, a1.tx_benChq";
//                strSQL+=" 		  ORDER BY a1.co_cli, a1.co_ben, a1.co_emp,a2.fe_recChq,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";




                strSQL="";
                strSQL+="SELECT z.co_emp, z.co_loc, z.co_tipDoc, z.co_doc, z.co_reg, z.co_cli, z.tx_numPed,";
                strSQL+=" z.fe_recchq, (z.nd_valDoc + z.nd_valApl) AS nd_valDoc, z.ne_numDoc";
                strSQL+=" , z.co_ben, z.tx_benChq FROM(";
                strSQL+=" 	SELECT x.*, CASE WHEN y.nd_valApl IS NULL THEN 0 ELSE y.nd_valApl END AS nd_valApl FROM(";
                strSQL+="                 SELECT a1.co_emp,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.co_cli, a1.tx_numPed,";
                strSQL+="                  		  a2.fe_recchq, (a2.mo_pag + a2.nd_abo) AS nd_valDoc, a1.ne_numDoc";
                strSQL+="                  		  , a1.co_ben, a1.tx_benChq";
                strSQL+="                                    FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="                                    ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                strSQL+="                                     AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                                   INNER JOIN (";
                strSQL+="                                    		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+="                                                                         SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+="                                                                         FROM tbm_cabTipDoc AS b1";
                strSQL+="                                                                         WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                                         AND b1.st_reg='A') AS a";
                strSQL+="                                                                 INNER JOIN(";
                strSQL+="                                                                         SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+="                                                                                 SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+="                                                                                 FROM tbm_visBueMovInv AS b1";
                strSQL+="                                                                                 WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                                                 AND b1.st_reg='A'";
                strSQL+="                                                                                 ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+="                                                                         ) AS x";
                strSQL+="                                                                         GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+="                                                                 ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+="                                    ) AS p";
                strSQL+="                                  ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+="                  		INNER JOIN tbm_cli AS b2 ON a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli";
                strSQL+="                                    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                    AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                                    AND (a2.nd_porRet = 0  OR a2.nd_porRet IS NULL) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+="                                    AND a2.st_reg IN('A','C')";
                strSQL+="                                    AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                strSQL+="                                    AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
                strSQL+="                                    AND a2.st_autpag='S' AND a2.co_ctaautpag=" + txtCodCtaAcr.getText() + "";
                strSQL+="                   		 GROUP BY a1.co_emp,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a1.co_cli, a1.tx_numPed,";
                strSQL+="                  		  a2.fe_recchq, a1.ne_numDoc,a2.mo_pag,a2.nd_abo, a1.co_ben, a1.tx_benChq";
                strSQL+="                  		  ORDER BY a1.co_cli, a1.co_ben, a1.co_emp,a2.fe_recChq,a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                strSQL+=" 		) AS x";
                strSQL+=" 		LEFT OUTER JOIN(";
                strSQL+=" 			SELECT b1.co_emp, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel,";
                strSQL+=" 			(b2.mo_pag + b2.nd_abo) AS nd_valApl";
                strSQL+=" 			FROM tbr_cabMovInv AS b1 INNER JOIN tbm_pagMovInv AS b2";
                strSQL+=" 			ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL+=" 			WHERE b1.co_tipDoc=4 AND (b2.mo_pag+b2.nd_abo)<0";
                strSQL+=" 			AND (b2.nd_porRet=0 OR b2.nd_porRet IS NULL) AND b2.st_reg IN ('A','C')";
                strSQL+=" 		) AS y";
                strSQL+=" 	ON x.co_emp=y.co_emp AND x.co_loc=y.co_locRel AND x.co_tipDoc=y.co_tipDocRel AND x.co_doc=y.co_docRel";
                strSQL+=" 	GROUP BY x.co_emp,x.co_loc, x.co_tipDoc, x.co_doc, x.co_reg, x.co_cli, x.tx_numPed,";
                strSQL+="                  		  x.fe_recchq, x.nd_valDoc, x.ne_numDoc";
                strSQL+="                  		  , x.co_ben, x.tx_benChq, y.nd_valApl";
                strSQL+=" ) AS z";
                strSQL+=" WHERE (z.nd_valDoc + z.nd_valApl)>0";
                System.out.println("SQL DETALLE:" + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    strRstCodEmp=rst.getString("co_emp");
                    strRstCodPrv=rst.getString("co_cli");
                    strRstNumPed=rst.getObject("tx_numPed")==null?"":rst.getString("tx_numPed");
                    strRstFecVenChq=rst.getObject("fe_recchq")==null?"":(rst.getString("fe_recchq").toString().equals("Null")?"":(objUti.formatearFecha(rst.getString("fe_recchq"),"yyyy-MM-dd", "yyyy-MM-dd")));
                    strRstCodBen=rst.getObject("co_ben")==null?"":rst.getString("co_ben");
                    
                    String strTxtFecDoc=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy","yyyy-MM-dd");
                    

                    if(strRstFecVenChq.equals(""))
                        strRstFecVenChq=strTxtFecDoc;                    
                    
                    if(arlDatDetPag.size()>0){
                        strArlDetCodEmpUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_COD_EMP);
                        strArlDetCodPrvUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_COD_CLI);
                        strArlDetFecVenChqUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_FEC_VEN_CHQ);
                        strArlDetCodBenUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_COD_BEN);
                        
                        if(   (strRstCodEmp.equals(strArlDetCodEmpUlt)) && (strRstCodPrv.equals(strArlDetCodPrvUlt))  && (strRstFecVenChq.equals(strArlDetFecVenChqUlt))  &&  (strRstCodBen.equals(strArlDetCodBenUlt))   ){
                            j++; 
                            if(  !  (strGloDocAnt.equals(rst.getString("ne_numDoc")))   ){
                                strGloDoc+="/" + rst.getString("ne_numDoc");
                            }
                            strGloDocAnt=rst.getString("ne_numDoc");
                            m++;
                            if(  !  strNumPedAnt.equals(strRstNumPed)  ){
                                strNumPed+="/" + rst.getObject("tx_numPed")==null?"":rst.getString("tx_numPed");
                            }
                            strNumPedAnt=strRstNumPed;
                        }
                        else{
                            j=1;
                            strGloDoc=rst.getString("ne_numDoc");
                            strGloDocAnt=rst.getString("ne_numDoc");
                            m=1;
                            strNumPed=strRstNumPed;
                            strNumPedAnt=strRstNumPed;
                        }
                    }
                    else{
                        j=1;
                        strGloDoc=rst.getString("ne_numDoc");
                        strGloDocAnt=rst.getString("ne_numDoc");
                        m=1;
                        strNumPed=strRstNumPed;
                        strNumPedAnt=strRstNumPed;
                    }
                    arlRegDetPag=new ArrayList();
                    arlRegDetPag.add(INT_ARL_DET_COD_EMP,           "" + objParSis.getCodigoEmpresa());
                    for(int k=0; k<arlDatCabPag.size(); k++){
                        strArlCabCodEmp=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_EMP);
                        strArlCabCodPrv=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_CLI);
                        strArlCabFecVenChq=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_FEC_VEN);
                        strArlCabCodBen=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_BEN);
                        if(strRstCodEmp.equals(strArlCabCodEmp)){
                            if(strRstCodPrv.equals(strArlCabCodPrv)){
                                if(strRstFecVenChq.equals(strArlCabFecVenChq)){
                                    if(strRstCodBen.equals(strArlCabCodBen)){
                                        //AQUI VA EL CODIGO DE BENEFICIARIO TAMBIEN
                                        arlRegDetPag.add(INT_ARL_DET_COD_LOC,     "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_LOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_TIP_DOC, "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_TIP_DOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_DOC,     "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_DOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_REG,     "" + j);//ver bien como hacer para colocar el registro
                                        objUti.setStringValueAt(arlDatCabPag, k, INT_ARL_CAB_NUM_DOC_CAB_MOV_INV, strGloDoc);
                                        objUti.setStringValueAt(arlDatCabPag, k, INT_ARL_CAB_NUM_DOC_PRV, strNumPed);

                                        String strCodPrv="";
                                        for(int h=0;h<objTblModDet.getRowCountTrue(); h++){
                                            strCodPrv=objTblModDet.getValueAt(h, INT_TBL_DAT_REP_COD_PRV)==null?"":objTblModDet.getValueAt(h, INT_TBL_DAT_REP_COD_PRV).toString();
                                            if(strCodPrv.equals(rst.getString("co_cli"))){
                                                objUti.setStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_CTA_DEU, (objTblModDet.getValueAt(h, INT_TBL_DAT_REP_COD_CTA_DEU)==null?"":objTblModDet.getValueAt(h, INT_TBL_DAT_REP_COD_CTA_DEU).toString()));
    //                                            System.out.println("PRIMER BREAK");
                                                break;
                                            }
                                        }
    //                                    System.out.println("SEGUNDO BREAK");
                                        break;
                                    }

                                }

                            }
                        }
                    }
                    arlRegDetPag.add(INT_ARL_DET_COD_LOC_PAG,       "" + rst.getString("co_loc"));
                    arlRegDetPag.add(INT_ARL_DET_COD_TIP_DOC_PAG,   "" + rst.getString("co_tipDoc"));
                    arlRegDetPag.add(INT_ARL_DET_COD_DOC_PAG,       "" + rst.getString("co_doc"));
                    arlRegDetPag.add(INT_ARL_DET_COD_REG_PAG,       "" + rst.getString("co_reg"));
                    arlRegDetPag.add(INT_ARL_DET_VAL_ABO,           "" + rst.getString("nd_valDoc"));
                    arlRegDetPag.add(INT_ARL_DET_COD_CLI,           "" + rst.getString("co_cli"));
                    arlRegDetPag.add(INT_ARL_DET_FEC_VEN_CHQ,       "" + strRstFecVenChq);
                    arlRegDetPag.add(INT_ARL_DET_COD_BEN,           "" + rst.getString("co_ben"));
                    
                    
                    arlDatDetPag.add(arlRegDetPag);
                    
                    
                }
                System.out.println("DETALLE: " + arlDatDetPag.toString());
                System.out.println("CABECERA: " + arlDatCabPag.toString());
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    private boolean insertar_tbmCabPag(){
        boolean blnRes=true;
        String strSQLGrl="";
        try{
            if(con!=null){
                stm=con.createStatement();
                
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                
                for(int i=0; i<arlDatCabPag.size(); i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbm_cabpag(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, ";
                    strSQL+="             co_doc, fe_doc, fe_ven, ne_numdoc1, ";
                    strSQL+="             ne_numdoc2, co_cta, co_cli, tx_ruc, ";
                    strSQL+="             tx_nomcli, tx_dircli, nd_mondoc, ";
                    strSQL+="             tx_obs1, tx_obs2, st_reg, fe_ing, ";
                    strSQL+="             fe_ultmod, co_usring, co_usrmod, ";
                    strSQL+="             co_dia, co_ben, tx_benchq, tx_mondocpal, ";
                    strSQL+="             co_mnu, st_regrep, st_imp, ";
                    strSQL+="             tx_obssolaut, tx_obsautsol, ";
                    strSQL+="             st_aut, ne_valaut, st_condepban, tx_comIng, tx_comMod)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + ",";//co_emp
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + ",";//co_loc
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + ",";//co_tipdoc
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + ",";//co_doc
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_DOC) + "',";//fe_doc
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_VEN) + "',";//fe_ven
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_UNO) + ",";//ne_numdoc1
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_DOS) + ",";//ne_numdoc2
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CTA_ACR) + ",";//co_cta
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CLI) + ",";//co_cli
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_RUC_CLI) + "',";//tx_ruc
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NOM_CLI) + "',";//tx_nomcli
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_DIR_CLI) + "',";//tx_dircli
                    //strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC) + ",";//nd_mondoc                    
                    strSQL+="" + objUti.codificar((objUti.isNumero(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC))?"" + (intSig*Double.parseDouble(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC))):"0"),3) + ",";//nd_mondoc
                    strSQL+="Null,";//tx_obs1
                    strSQL+="Null,";//tx_obs2
                    strSQL+="'A',";//st_reg
                    strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";//fe_ing
                    strSQL+="'" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";//fe_ultmod
                    strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                    strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                    strSQL+="Null,";//co_dia
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_BEN) + ",";//co_ben
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NOM_BEN) + "',";//tx_benchq
                    strSQL+="'" + generaMontoDocumentoPalabra(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC)) + "',";//tx_mondocpal
                    strSQL+="" + objParSis.getCodigoMenu() + ",";//co_mnu
                    strSQL+="'I',";//st_regrep
                    strSQL+="'N',";//st_imp
                    strSQL+="Null,";//tx_obssolaut
                    strSQL+="Null,";//tx_obsautsol
                    strSQL+="Null,";//st_aut
                    strSQL+="Null,";//ne_valaut
                    strSQL+="'N'";//st_condepban
                    strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                    strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP()) + "";
                    strSQL+=");";
                    strSQLGrl+=strSQL;
                }
                System.out.println("CABECERA: " + strSQLGrl);
                stm.executeUpdate(strSQLGrl);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean insertar_tbmDetPag(){
        boolean blnRes=true;
        String strSQLGrl="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0;i<arlDatDetPag.size();i++){
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detpag(";
                    strSQL+="             co_emp, co_loc, co_tipdoc, co_doc, co_reg, ";
                    strSQL+="             co_locpag, co_tipdocpag, co_docpag, co_regpag, ";
                    strSQL+="             nd_abo, co_tipdoccon, co_banchq, tx_numctachq, ";
                    strSQL+="             tx_numchq, fe_recchq, fe_venchq, st_regrep, tx_codsri)";
                    strSQL+="    VALUES (";
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_EMP) + ",";//co_emp
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_LOC) + ",";//co_loc
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_TIP_DOC) + ",";//co_tipdoc
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_DOC) + ",";//co_doc
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_REG) + ",";//co_reg
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_LOC_PAG) + ",";//co_locpag
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_TIP_DOC_PAG) + ",";//co_tipdocpag
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_DOC_PAG) + ",";//co_docpag
                    strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_REG_PAG) + ",";//co_regpag
                    //strSQL+="" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_VAL_ABO) + ",";//nd_abo
                    strSQL+="" + intSig*Double.parseDouble(objUti.codificar(objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_VAL_ABO), 3)) + ",";//nd_abo
                    strSQL+="Null,";//co_tipdoccon
                    strSQL+="Null,";//co_banchq
                    strSQL+="Null,";//tx_numctachq
                    strSQL+="Null,";//tx_numchq
                    strSQL+="Null,";//fe_recchq
                    strSQL+="Null,";//fe_venchq
                    strSQL+="'I',";//st_regrep
                    strSQL+="Null";
                    strSQL+=");";
                    strSQLGrl+=strSQL;
                }
                System.out.println("DETALLE: " + strSQLGrl);
                stm.executeUpdate(strSQLGrl);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean actualizar_tbmPagMovInv(){
        boolean blnRes=true;
        String strSQLGrl="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<arlDatDetPag.size(); i++){
                    strSQL="";
                    strSQL+="UPDATE tbm_pagMovInv";
                    strSQL+=" SET nd_abo=nd_abo+(" + intSig*objUti.redondear((objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_VAL_ABO)),objParSis.getDecimalesBaseDatos()) + ")";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_LOC_PAG) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_TIP_DOC_PAG) + "";
                    strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_DOC_PAG) + "";
                    strSQL+=" AND co_reg=" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_REG_PAG) + "";
                    strSQL+=";";
                    strSQLGrl+=strSQL;
                }
                System.out.println("PAGOS: " + strSQLGrl);
                stm.executeUpdate(strSQLGrl);
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean insertarDiario(){
        boolean blnRes=false;
        int intCodEmp=0, intCodLoc=0, intCodTipDoc=0;
        String strCodDia="", strNumDia="", strFecDia="";
        String strValDia="";
        String strGloDia="";
        int intCodCtaDeu=0;
        try{
            if(con!=null){
                for(int i=0; i<arlDatCabPag.size(); i++){
                    intCodEmp=objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP);
                    intCodLoc=objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC);
                    intCodTipDoc=objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC);
                    strCodDia=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC);
                    strNumDia=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_UNO);
                    strFecDia=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_DOC);
                    strValDia=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC);
                    strGloDia=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_CAB_MOV_INV);
                    intCodCtaDeu=objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CTA_DEU);
                    objAsiDia.inicializar();
                    objAsiDia.generarDiario(txtCodTipDoc.getText(), intCodCtaDeu, strValDia, Integer.parseInt(txtCodCtaAcr.getText()), strValDia);
                    objAsiDia.setCamTblUltDoc(strTblActNumDoc);
                    if(strTblActNumDoc.equals("G"))
                        objAsiDia.setCodTipDocGrp(strCodGrpTipDoc);
                    if (objAsiDia.insertarDiario(con, intCodEmp, intCodLoc, intCodTipDoc, strCodDia, strNumDia, objUti.parseDate(strFecDia,"yyyy-MM-dd"))){
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                        break;
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    

    
    
    private boolean isCamFil(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                        dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                        String strMsj="";
                        strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                        strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                        strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                        strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                        strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                        strMsj+="<BR>      El documento se guardará con fecha del día así ud. coloque otra fecha.";
                        strMsj+="<BR>  Está seguro que desea continuar?</HTML>";
                        //mostrarMsgInf("<HTML> " + strMsj + "</HTML>");

                        switch (mostrarMsgCon(strMsj)){
                            case 0: //YES_OPTION
                                return true;
                            case 1: //NO_OPTION
                                return false;
                            case 2: //CANCEL_OPTION
                                return false;
                        }
                        datFecAux=null;
                    }
                    break;
                case 1://no puede cambiarla para nada
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    strFecDocTmp="";strFecAuxTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                    strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) != 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permisos para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }
                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    strFecDocTmp="";strFecAuxTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                    strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) > 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha ingresada en el documento es mayor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha posterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    strFecDocTmp="";strFecAuxTmp="";
                    strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");
                    strFecAuxTmp=objUti.formatearFecha(datFecAux,"dd/MM/yyyy");
                    if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo(objUti.parseDate(strFecAuxTmp, "dd/MM/yyyy")) < 0 ){
                        dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                        mostrarMsgInf("<HTML>La fecha ingresada en el documento es menor a la fecha del día.<BR>Ud. no tiene permiso para colocar fecha anterior a la del día.<BR>Verifique y vuelva a intentarlo.</HTML>");
                        datFecAux=null;
                        return false;
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        //Validar el "Cuenta deudora".
        if (txtCodCtaAcr.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta acreedora</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta acreedora y vuelva a intentarlo.</HTML>");
            txtDesCorCtaAcr.requestFocus();
            return false;
        }
        
        //Validar el "Cuenta deudora".
        if (txtCodCtaDeu.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta deudora</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta deudora y vuelva a intentarlo.</HTML>");
            txtDesCorCtaDeu.requestFocus();
            return false;
        }
        
        
        
        return true;
        
    }
    
    
    private boolean isCamFilCon(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        //Validar el "Cuenta deudora".
        if (txtCodCtaAcr.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta acreedora</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta acreedora y vuelva a intentarlo.</HTML>");
            txtDesCorCtaAcr.requestFocus();
            return false;
        }
        
        //Validar el "Cuenta deudora".
        if (txtCodCtaDeu.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta deudora</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta deudora y vuelva a intentarlo.</HTML>");
            txtDesCorCtaDeu.requestFocus();
            return false;
        }
        
        
        
        return true;
        
    }
    
    
    
    private boolean procesoImprimir(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(imprimir()){
                    blnRes=true;
                    con.commit();
                    //con.rollback();
                }
                else{
                    con.rollback();
                }
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private boolean imprimir(){
        boolean blnRes=true;
        int intNumChq=0, intNumEgr=0, intNumEgrGrp=0;
        String strGloCabDia="";
        strAux="";
        String strnomEmp=retNomEmp(objParSis.getCodigoEmpresa());
        String strNumEgr="";
        int intCodGrpTipDoc=0;
        String strCodGrpTipDocFunImp="";
        try{
            if(con!=null){
                stm=con.createStatement();
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                datFecAux=null;
//                System.out.println("EL TAMAÑO DE LA CABECERA ES: " + arlDatCabPag.size());
                for(int i=0; i<arlDatCabPag.size(); i++){
                    strGloCabDia="";
                    //para obtener numero de egreso
                    strSQL="";
                    strSQL+="SELECT a2.co_grpTipDoc, MAX(a1.ne_ultDoc) AS ne_ultDoc,";
                    strSQL+=" MAX(a2.ne_ultDoc) AS ne_ultDocGrp";
                    strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" GROUP BY a2.co_grpTipDoc";
                    System.out.println("egreso: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        strNumEgr=rst.getObject("ne_ultDocGrp")==null?"":rst.getString("ne_ultDocGrp");
                        strCodGrpTipDocFunImp=rst.getObject("co_grpTipDoc")==null?"":rst.getString("co_grpTipDoc");                        
                        
                        if(strNumEgr.equals(""))
                            intNumEgr=(rst.getInt("ne_ultDoc"));
                        else
                            intNumEgrGrp=(rst.getInt("ne_ultDocGrp"));
                        
                        
                        if( ! strCodGrpTipDocFunImp.equals(""))
                            intCodGrpTipDoc=rst.getInt("co_grpTipDoc");
                        else
                            intCodGrpTipDoc=0;
                    }
                    
                    
                    
                    //para obtener numero de cheque
                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_ultnumchq) AS ne_ultnumchq";
                    strSQL+=" FROM tbm_plaCta AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_cta=" + txtCodCtaAcr.getText() + "";
                    System.out.println("cheque: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        intNumChq=(rst.getInt("ne_ultnumchq")+1);
                    }
                    //para obtener la glosa
//                    strGloCabDia+=txtDesCorTipDoc.getText() + ": " + (intNumEgr==0?(intNumEgrGrp+1):(intNumEgr+1)) + "";
                    strGloCabDia+=";  Egreso: " + (intNumEgrGrp==0?(intNumEgr+1):(intNumEgrGrp+1)) + "";
                    strGloCabDia+=";  Cheque: " + intNumChq + "";
                    strGloCabDia+=";  Beneficiario: " + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NOM_BEN) + "";
                    strGloCabDia+=";  Documentos: " + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_CAB_MOV_INV) + "";
                    strGloCabDia+=";  Cuenta: " + txtDesLarCtaAcr.getText() + "";
                    strGloCabDia+=";  Factura: " + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_PRV) + "";
                    strGloCabDia+=";  Retención: " + obtieneNumeroRetencion(objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC));
                    strGloCabDia+=";  Motivo: " + obtieneObservacion2DocPagar(objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC), objUti.getIntValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC));

                    
                    
                    //en tbm_cabPag.ne_numDoc1 y tbm_cabPag.ne_numDoc2
                    strSQL="";
                    strSQL+="UPDATE tbm_cabPag";
                    strSQL+=" SET ne_numDoc1=" + (intNumEgrGrp==0?(intNumEgr+1):(intNumEgrGrp+1)) + "";
                    strSQL+=" , ne_numDoc2=" + intNumChq + "";
                    strSQL+=" , st_imp='S'";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=";";
                    System.out.println("CABpAG: : " + strSQL);
                    stm.executeUpdate(strSQL);
                    
                    //en tbm_cabDia.tx_numDia
                    strSQL="";
                    strSQL+="UPDATE tbm_cabDia";
                    strSQL+=" SET tx_numDia=" + (intNumEgr==0?(intNumEgrGrp+1):(intNumEgr+1)) + "";
                    strSQL+=" , tx_glo='" + strGloCabDia + "'";
                    strSQL+=" , st_imp='S'";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND co_dia=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=";";
                    System.out.println("CABDIA: : " + strSQL);
                    stm.executeUpdate(strSQL);
                    
                    //para actualizar el campo numero de cheque q se almacena en tbm_plaCta
                    strSQL="";
                    strSQL+="UPDATE tbm_plaCta";
                    strSQL+=" SET ne_ultnumchq=" + intNumChq + "";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_cta=" + txtCodCtaAcr.getText() + "";
                    strSQL+=";";
                    stm.executeUpdate(strSQL);
                    
                    //para actualizar el campr numero de egreso que se almacena en tbm_cabTipDoc o en tbm_cabGrpTipDoc
                    if(strTblActNumDoc.equals("G")){
                        strSQL="";
                        strSQL+="UPDATE tbm_cabGrpTipDoc";
                        strSQL+=" SET ne_ultDoc=" + (intNumEgrGrp+1) + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_grptipDoc=" + strCodGrpTipDoc;
                        stm.executeUpdate(strSQL);
                    }
                    else{
                        strSQL="";
                        strSQL+="UPDATE tbm_cabTipDoc";
                        strSQL+=" SET ne_ultDoc=" + (intNumEgr+1) + "";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                        strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
                        stm.executeUpdate(strSQL);
                    }
                    
                    //para la impresión
                      //JasperViewer.viewReport(report, false);
                      //JasperPrintManager.printReport(report, false);    
                    
                    
                    if(System.getProperty("os.name").equals("Linux")){
                        strNomSubRep="//Zafiro//Reportes_impresos//SubRptChq.jrxml";
                        strNomRepTuv="//Zafiro//Reportes_impresos//RptChq2.jrxml";
                        strNomRepCas="//Zafiro//Reportes_impresos//RptChqC.jrxml";
                        strNomRepNos="//Zafiro//Reportes_impresos//RptChqN.jrxml";
                        strNomRepDim="//Zafiro//Reportes_impresos//RptChqD.jrxml";
                        strNomRepDef="//Zafiro//Reportes_impresos//RptChq.jrxml";
                    }
                    else{

                        if(objParSis.getCodigoEmpresa()==1){
                            if(txtCodCtaAcr.getText().equals("18")){
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChqMM.jrxml";
                                strNomRepTuv="C://Zafiro//Reportes_impresos//RptChq2MM.jrxml";
                            }
                            else{
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
                                strNomRepTuv="C://Zafiro//Reportes_impresos//RptChq2.jrxml";//este era el q estaba
                            }
                        }

                        else if(objParSis.getCodigoEmpresa()==2){
                            if(txtCodCtaAcr.getText().equals("942")){
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChqMM.jrxml";
                                strNomRepTuv="C://Zafiro//Reportes_impresos//RptChq2MM.jrxml";
                            }
                            else{
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
                                strNomRepCas="C://Zafiro//Reportes_impresos//RptChqC.jrxml";//este era el q estaba
                            }
                        }
                        else if(objParSis.getCodigoEmpresa()==3){
                            strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
                            strNomRepNos="C://Zafiro//Reportes_impresos//RptChqN.jrxml";//este era el q estaba
                        }
                        else if(objParSis.getCodigoEmpresa()==4){
                            if(txtCodCtaAcr.getText().equals("1802")){
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChqMM.jrxml";
                                strNomRepTuv="C://Zafiro//Reportes_impresos//RptChq2MM.jrxml";
                            }
                            else{
                                strNomRepDim="C://Zafiro//Reportes_impresos//RptChqD.jrxml";
                                strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
                            }
                        }
                        else{
                            strNomSubRep="C://Zafiro//Reportes_impresos//SubRptChq.jrxml";
                            strNomRepDef="C://Zafiro//Reportes_impresos//RptChq.jrxml";
                        }
                    }
                    
                    JasperDesign subjasperDesign = JRXmlLoader.load(strNomSubRep);                
                    JasperReport subjasperReport = JasperCompileManager.compileReport(subjasperDesign); 
                    
                    // Second, create a map of parameters to pass to the report.
                    Map parameters = new HashMap();
                    parameters.put("tit", objParSis.getNombreMenu());
                    parameters.put("codEmp", "" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP));
                    parameters.put("codLoc", "" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC));
                    parameters.put("codTipDoc", objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC));
                    parameters.put("codDoc", objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC));
                    parameters.put("nomTipDoc", txtDesLarTipDoc.getText());
                    parameters.put("codCli", objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CLI));
                    parameters.put("nomCli", objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NOM_BEN));                
                    parameters.put("monDoc", objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_MON_DOC));                
                    parameters.put("fecDoc", objUti.formatearFecha(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_VEN), "yyyy-MM-dd","dd 'de' MMMMM 'de' yyyy"));
                    parameters.put("numAltUno", "" + (intNumEgr==0?(intNumEgrGrp+1):(intNumEgr+1)));     
                    parameters.put("numAltDos", "" + intNumChq);     
                    parameters.put("nomEmp", strnomEmp);     
                    parameters.put("obs1", ""); 
                    parameters.put("numBco", txtDesCorCtaAcr.getText()); 
                    parameters.put("nomBco", txtDesLarCtaAcr.getText()); 
                    parameters.put("fecDocCor", dtpFecDoc.getText());                 
                    parameters.put("fecha", "" + strAux);
                    parameters.put("usuario", objParSis.getNombreUsuario());
                    parameters.put("glosa", strGloCabDia);
                    parameters.put("SUBREPORT", subjasperReport);
                    
                    //para imprimir
//                    JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                    JasperPrintManager.printReport(report, false);                 
                       
                    if(objParSis.getCodigoEmpresa()==1){
                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRepTuv);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                         //JasperPrintManager.printReport(report, false);
                         mostrarMsgInf("Coloque la hoja para impresión y de click en OK");
                         JasperViewer.viewReport(report, false);
                         //JasperPrintManager.printReport(report, false);
                   
                     } 
                    else if(objParSis.getCodigoEmpresa()==2){
                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRepCas);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                        //JasperPrintManager.printReport(report, false);
                         mostrarMsgInf("Coloque la hoja para impresión y de click en OK");
//                         JasperViewer.viewReport(report, false);
                         JasperPrintManager.printReport(report, false);
                    }
                    else    if(objParSis.getCodigoEmpresa()==3){
                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRepNos);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                         //JasperPrintManager.printReport(report, false);
                         mostrarMsgInf("Coloque la hoja para impresión y de click en OK");
//                         JasperViewer.viewReport(report, false);
                         JasperPrintManager.printReport(report, false);
                    }
                    else  if(objParSis.getCodigoEmpresa()==4){
                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRepDim);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                         //JasperPrintManager.printReport(report, false);
                         mostrarMsgInf("Coloque la hoja para impresión y de click en OK");
//                         JasperViewer.viewReport(report, false);
                         JasperPrintManager.printReport(report, false);
                    
                    }else{
                        JasperDesign jasperDesign = JRXmlLoader.load(strNomRepDef);
                        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                        //JasperPrintManager.printReport(report, false);
                         mostrarMsgInf("Coloque la hoja para impresión y de click en OK");
//                         JasperViewer.viewReport(report, false);
                         JasperPrintManager.printReport(report, false);
                        
                    }
                    
                    
                }

                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private String generaMontoDocumentoPalabra(String valorDocumento){
        boolean blnRes=true;
        String res="";
        try{
            Librerias.ZafUtil.ZafNumLetra numero;
            double cantidad= objUti.redondear(valorDocumento, objParSis.getDecimalesMostrar());
            String decimales=String.valueOf(cantidad).toString();
                   decimales=decimales.substring(decimales.indexOf('.') + 1); 
                   decimales=(decimales.length()<2?decimales + "0":decimales.substring(0,2));
            int deci= Integer.parseInt(decimales);
            int m_pesos=(int)cantidad;
            numero = new Librerias.ZafUtil.ZafNumLetra(m_pesos);
            res = numero.convertirLetras(m_pesos);
            res = res+" "+decimales+"/100  DOLARES";
            res=res.toUpperCase(); 	
            numero=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return res;
    }
    
    private boolean isChangeTable(int caso){
        boolean blnRes=true;
        String strLin="";
        int intCamModTbl=0;
        int intTbl=caso;
        try{
            switch(intTbl){
                case 0://boton de consultar-se observa la tabla donde estan los tipos de documentos del filtro
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            intCamModTbl++;
                        }
                    }
                    break;
                case 1://para el boton procesar-se observa la tabla de los datos cargados del reporte
                    for(int i=0; i<objTblModDet.getRowCountTrue(); i++){
                        strLin=objTblModDet.getValueAt(i, INT_TBL_DAT_REP_LIN)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_REP_LIN).toString();
                        if(strLin.equals("M")){
                            if(objTblModDet.isChecked(i, INT_TBL_DAT_REP_CHK)){
                                intCamModTbl++;
                            }
                        }
                    }
                    break;
            }
            if(intCamModTbl==0){
                blnRes=false;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
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
//                    System.out.println("ACCESO A MODIFICAR FECHA: " + strSQL);
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
    

    private String obtieneNumeroRetencion(int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        String strNumRetAso="";
        Connection conNumRet;
        Statement stmNumRet;
        ResultSet rstNumRet;
        String strNumRet;
        int j=0;
        try{
            conNumRet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumRet!=null){
                stmNumRet=conNumRet.createStatement();
                strNumRet="";
                strNumRet+="SELECT *FROM(";
                strNumRet+="	SELECT a1.co_emp, a1.co_locPag, a1.co_tipDocPag, a1.co_docPag";
                strNumRet+=" 	FROM tbm_detPag AS a1 INNER JOIN tbm_cabPag AS a2";
                strNumRet+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strNumRet+=" 	WHERE a1.co_emp=" + codigoEmpresa + "";
                strNumRet+="     AND a1.co_loc=" + codigoLocal + "";
                strNumRet+="     AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strNumRet+="     AND a1.co_doc IN(" + codigoDocumento + ")";
                strNumRet+=" 	)AS x";
                strNumRet+=" INNER JOIN(";
                strNumRet+=" 	SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.ne_numDoc1";
                strNumRet+=" 	FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a2";
                strNumRet+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strNumRet+=" 	INNER JOIN tbm_pagMovInv AS a3";
                strNumRet+=" 	ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                strNumRet+="    AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strNumRet+=" 	WHERE a3.nd_porRet>0 AND a1.st_reg IN('A','C')";
                strNumRet+=" )AS y";
                strNumRet+=" ON x.co_emp=y.co_emp AND x.co_locPag=y.co_loc AND x.co_tipDocPag=y.co_tipDoc AND x.co_docPag=y.co_doc";
                strNumRet+=" GROUP BY x.co_emp, x.co_locPag, x.co_tipDocPag, x.co_docPag, y.co_emp, y.co_loc, y.co_tipDoc, y.co_doc, y.ne_numDoc1";
                rstNumRet=stmNumRet.executeQuery(strNumRet);
                strNumRetAso="";
                while(rstNumRet.next()){
                    if(j==0){
                        strNumRetAso=rstNumRet.getObject("ne_numDoc1")==null?"":rstNumRet.getString("ne_numDoc1");
                        j++;
                    }
                    else{
                        strNumRetAso+=",";
                        strNumRetAso+=rstNumRet.getObject("ne_numDoc1")==null?"":rstNumRet.getString("ne_numDoc1");
                    }
                }
                conNumRet.close();
                conNumRet=null;
                stmNumRet.close();
                stmNumRet=null;
                rstNumRet.close();
                rstNumRet=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strNumRetAso;
    }


    private String obtieneObservacion2DocPagar(int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        String strNumRetAso="";
        Connection conNumRet;
        Statement stmNumRet;
        ResultSet rstNumRet;
        String strNumRet;
        int j=0;
        try{
            conNumRet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNumRet!=null){
                stmNumRet=conNumRet.createStatement();
                strNumRet="";
                strNumRet+="SELECT a3.co_emp, a3.co_loc, a3.co_tipDoc, a3.co_doc, a1.ne_numDoc1, a4.tx_obs2";
                strNumRet+=" FROM tbm_cabPag AS a1 INNER JOIN tbm_detPag AS a2";
                strNumRet+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strNumRet+=" INNER JOIN tbm_pagMovInv AS a3";
                strNumRet+=" ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc";
                strNumRet+=" AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strNumRet+=" INNER JOIN tbm_cabMovInv AS a4";
                strNumRet+=" ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strNumRet+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                strNumRet+=" AND a1.co_loc=" + codigoLocal + "";
                strNumRet+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strNumRet+=" AND a1.co_doc IN(" + codigoDocumento + ")";
                strNumRet+=" AND a1.st_reg IN('A','C')";

                rstNumRet=stmNumRet.executeQuery(strNumRet);
                strNumRetAso="";
                while(rstNumRet.next()){
                    if(j==0){
                        strNumRetAso=rstNumRet.getObject("tx_obs2")==null?"":rstNumRet.getString("tx_obs2");
                        j++;
                    }
                    else{
                        strNumRetAso+=",";
                        strNumRetAso+=rstNumRet.getObject("tx_obs2")==null?"":rstNumRet.getString("tx_obs2");
                    }
                }
                conNumRet.close();
                conNumRet=null;
                stmNumRet.close();
                stmNumRet=null;
                rstNumRet.close();
                rstNumRet=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strNumRetAso;
    }

    
}