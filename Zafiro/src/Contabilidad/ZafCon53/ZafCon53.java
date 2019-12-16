/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */

package Contabilidad.ZafCon53;
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
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafRptSis.ZafRptSis;
import java.util.HashMap; 
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
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
public class ZafCon53 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable de filtro para la consulta
    final int INT_TBL_DAT_LIN=0;                        //Lánea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificacián.
    final int INT_TBL_DAT_COD_LOC=2;                    //Cádigo del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //Cádigo del tipo de documento.
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;                //Descripcián corta del tipo de documento.
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;                //Descripcián larga del tipo de documento.
       
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod, objTblModAsiDia, objTblModImp;
    private ZafTblEdi objTblEdi, objTblEdiImp;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIPagTot objThrGUIPagTot;
    private ZafThreadGUIImp objThrGUIImp;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg, vecDatAsiDia, vecRegAsiDia, vecCabAsiDia;
    private Vector vecDatImp, vecCabImp, vecRegImp;
    private Vector vecAux;
    private boolean blnCon, blnConImp;                             //true: Continua la ejecucián del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private ZafAsiDia objAsiDia;
    
    
    
    private ZafRptSis objRptSis;
    
    
    
    
    
    private ZafDatePicker dtpFecDoc;
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    
    //Constantes: Columnas del JTable para datos
    final int INT_TBL_DAT_REP_LIN=0;                        //Lánea.
    final int INT_TBL_DAT_REP_CHK=1;
    final int INT_TBL_DAT_REP_NUM_DOC=2;
    final int INT_TBL_DAT_REP_COD_PRV=3;
    final int INT_TBL_DAT_REP_NOM_PRV=4;
    final int INT_TBL_DAT_REP_VAL_DOC_RET=5;
    final int INT_TBL_DAT_REP_BUT_ANE=6;
    
    

    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                           //Ventana de consulta "Cuenta".
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private ZafVenCon vcoBen;                           //Ventana de consulta "Beneficiario".
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;          //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    //Variables de la clase.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direccián del Beneficiario.
    private ZafSelectDate objSelDat;
    private Vector vecDatDet, vecCabDet, vecRegDet;
    
    private ZafTblMod objTblModDet;
    private ZafTblEdi objTblEdiDet;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChkDet;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkDet;            //Editor: JCheckBox en celda.
    private ZafMouMotAdaDet objMouMotAdaDet;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnuDet;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafTblFilCab objTblFilCabDet;
    
    private ZafTblCelRenBut objTblCelRenButAne;
    private ZafTblCelEdiButDlg objTblCelEdiButAne;
    private ZafCon53_01 objCon53_01;
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
    final int INT_ARL_CAB_NUM_DOC_UNO=5;
    final int INT_ARL_CAB_NUM_DOC_DOS=6;
    final int INT_ARL_CAB_COD_CLI=7;
    final int INT_ARL_CAB_RUC_CLI=8;
    final int INT_ARL_CAB_NOM_CLI=9;
    final int INT_ARL_CAB_DIR_CLI=10;
    final int INT_ARL_CAB_MON_DOC=11;
    final int INT_ARL_CAB_SEC_DOC=12;
    final int INT_ARL_CAB_FEC_CAD=13;
    final int INT_ARL_CAB_NUM_AUT_SRI=14;
    final int INT_ARL_CAB_NUM_PED=15;
    final int INT_ARL_CAB_NUM_DOC_CAB_MOV_INV=16;
    
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
    final int INT_ARL_DET_COD_SRI=10;
    final int INT_ARL_DET_SEC_DOC=11;
    final int INT_ARL_DET_FEC_CAD=12;
    final int INT_ARL_DET_NUM_AUT_SRI=13;
    final int INT_ARL_DET_NUM_PED=14;
    
    
    
    private ArrayList arlRegDiaEmiMasRet, arlDatDiaEmiMasRet;
    final int INT_ARL_DIA_EMI_MAS_RET_COD_RTE=0;
    final int INT_ARL_DIA_EMI_MAS_RET_VAL=1;
    final int INT_ARL_DIA_EMI_MAS_RET_PRC=2;
    
    
    
    
    
    
    
    
    
    final int INT_TBL_DAT_ASI_DIA_LIN=0;
    final int INT_TBL_DAT_ASI_DIA_COD_EMP=1;
    final int INT_TBL_DAT_ASI_DIA_COD_LOC=2;
    final int INT_TBL_DAT_ASI_DIA_COD_TIP_DOC=3;
    final int INT_TBL_DAT_ASI_DIA_COD_DOC=4;
    final int INT_TBL_DAT_ASI_DIA_COD_REG=5;
    final int INT_TBL_DAT_ASI_DIA_COD_PRV=6;
    final int INT_TBL_DAT_ASI_DIA_COD_TIP_RET=7;
    final int INT_TBL_DAT_ASI_DIA_VAL_RET=8;
    final int INT_TBL_DAT_ASI_DIA_CHK=9;



    final int INT_TBL_DAT_IMP_LIN=0;
    final int INT_TBL_DAT_IMP_CHK=1;
    final int INT_TBL_DAT_IMP_COD_EMP=2;
    final int INT_TBL_DAT_IMP_COD_LOC=3;
    final int INT_TBL_DAT_IMP_COD_TIP_DOC=4;
    final int INT_TBL_DAT_IMP_COD_DOC=5;
    final int INT_TBL_DAT_IMP_DES_COR_TIP_DOC=6;
    final int INT_TBL_DAT_IMP_COD_CLI=7;
    final int INT_TBL_DAT_IMP_NOM_BEN=8;
    final int INT_TBL_DAT_IMP_VAL_RET=9;
    final int INT_TBL_DAT_IMP_DOC_CAN=10;




    private ZafTblCelRenChk objTblCelRenChkImp;
    private ZafTblCelEdiChk objTblCelEdiChkImp;
    private String strImpresionDirectaRetencion;

    private boolean blnMarTodChkTblImp;

    private String strDirRep;
    
    /** Crea una nueva instancia de la clase ZafCon53. */
    public ZafCon53(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
            arlDatCabPag=new ArrayList();
            arlDatDetPag=new ArrayList();
            arlDatDiaEmiMasRet=new ArrayList();
            strImpresionDirectaRetencion="printretemas";
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
        lbCta = new javax.swing.JLabel();
        txtCodCta = new javax.swing.JTextField();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        lblFecDoc = new javax.swing.JLabel();
        panDet = new javax.swing.JPanel();
        spnTblDet = new javax.swing.JScrollPane();
        tblDatDet = new javax.swing.JTable();
        spnTmAsiDia = new javax.swing.JScrollPane();
        tblDatAsiDia = new javax.swing.JTable();
        panAsiDia = new javax.swing.JPanel();
        panRetFalImp = new javax.swing.JPanel();
        spnRetFalImp = new javax.swing.JScrollPane();
        tblDatRetFalImp = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        chkNotIncNumDoc = new javax.swing.JCheckBox();
        butSetEst = new javax.swing.JButton();
        pasActFil = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        txtCodDocDes = new javax.swing.JTextField();
        txtCodDocHas = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        butConRetImp = new javax.swing.JButton();
        butProRetImp = new javax.swing.JButton();

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

        panCab.setPreferredSize(new java.awt.Dimension(100, 60));
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

        lbCta.setText("Cuenta deudora:");
        lbCta.setToolTipText("Cuenta");
        panCab.add(lbCta);
        lbCta.setBounds(0, 24, 100, 20);
        panCab.add(txtCodCta);
        txtCodCta.setBounds(68, 24, 32, 20);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        panCab.add(txtDesCorCta);
        txtDesCorCta.setBounds(100, 24, 80, 20);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        panCab.add(txtDesLarCta);
        txtDesLarCta.setBounds(180, 24, 240, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panCab.add(butCta);
        butCta.setBounds(420, 24, 20, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(444, 4, 110, 20);

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

        spnTmAsiDia.setPreferredSize(new java.awt.Dimension(0, 0));

        tblDatAsiDia.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTmAsiDia.setViewportView(tblDatAsiDia);

        panDet.add(spnTmAsiDia, java.awt.BorderLayout.PAGE_END);

        panRep.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panRetFalImp.setLayout(new java.awt.BorderLayout());

        tblDatRetFalImp.setModel(new javax.swing.table.DefaultTableModel(
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
        spnRetFalImp.setViewportView(tblDatRetFalImp);

        panRetFalImp.add(spnRetFalImp, java.awt.BorderLayout.CENTER);

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 70));
        jPanel2.setLayout(null);

        chkNotIncNumDoc.setText("No incrementa numeración");
        jPanel2.add(chkNotIncNumDoc);
        chkNotIncNumDoc.setBounds(440, 4, 210, 14);

        butSetEst.setText("Setear estado impresión");
        butSetEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSetEstActionPerformed(evt);
            }
        });
        jPanel2.add(butSetEst);
        butSetEst.setBounds(460, 46, 190, 23);
        jPanel2.add(pasActFil);
        pasActFil.setBounds(190, 6, 130, 20);

        jLabel3.setText("Clave de activación de filtro:");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(10, 10, 200, 14);

        jButton1.setText("Verificar clave");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(20, 30, 140, 23);

        jButton2.setText("Inactivar campos");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(180, 30, 160, 23);
        jPanel2.add(txtCodDocDes);
        txtCodDocDes.setBounds(460, 22, 90, 20);
        jPanel2.add(txtCodDocHas);
        txtCodDocHas.setBounds(557, 22, 90, 20);

        panRetFalImp.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel1.setPreferredSize(new java.awt.Dimension(100, 50));

        butConRetImp.setText("Consultar Retenciones");
        butConRetImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConRetImpActionPerformed(evt);
            }
        });
        jPanel1.add(butConRetImp);

        butProRetImp.setText("Imprimir Retenciones");
        butProRetImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProRetImpActionPerformed(evt);
            }
        });
        jPanel1.add(butProRetImp);

        panRetFalImp.add(jPanel1, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Retenciones faltantes de impresión", panRetFalImp);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexián si está abierta.
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
//Validar el contenido de la celda sálo si ha cambiado.
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
//Validar el contenido de la celda sálo si ha cambiado.
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
//Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc))
        {
            if (txtDesCorTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
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
//Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc))
        {
            if (txtDesLarTipDoc.getText().equals(""))
            {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
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

private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
txtDesCorCta.transferFocus();
}//GEN-LAST:event_txtDesCorCtaActionPerformed

private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
strDesCorCta=txtDesCorCta.getText();
        txtDesCorCta.selectAll();
}//GEN-LAST:event_txtDesCorCtaFocusGained

private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)){
            if (txtDesCorCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesLarCta.setText("");
            }
            else{
                mostrarVenConCta(1);
            }
        }
        else
            txtDesCorCta.setText(strDesCorCta);
}//GEN-LAST:event_txtDesCorCtaFocusLost

private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
txtDesLarCta.transferFocus();
}//GEN-LAST:event_txtDesLarCtaActionPerformed

private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
strDesLarCta=txtDesLarCta.getText();
        txtDesLarCta.selectAll();
}//GEN-LAST:event_txtDesLarCtaFocusGained

private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta))
        {
            if (txtDesLarCta.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
            }
            else
            {
                mostrarVenConCta(2);
            }
        }
        else
            txtDesLarCta.setText(strDesLarCta);
}//GEN-LAST:event_txtDesLarCtaFocusLost

private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
    mostrarVenConCta(0);
        if (!txtCodPrv.getText().equals("")){
        }
}//GEN-LAST:event_butCtaActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
// TODO add your handling code here:
    if(isChangeTable(0)){
        if (butCon.getText().equals("Consultar"))
            {
                //objTblTotales.isActivo(false);
                blnCon=true;
                if (objThrGUIPagTot==null)
                {
                    objThrGUIPagTot=new ZafThreadGUIPagTot();
                    objThrGUIPagTot.start();                

                }            
            }
            else
            {
                blnCon=false;
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
    String strTit, strMsg;
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    if(isChangeTable(1)){
        strTit="Mensaje del sistema Zafiro";
        strMsg="<HTML>¿Está seguro que desea realizar esta operación?<BR>";
        strMsg+="</HTML>";
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
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

private void butConRetImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConRetImpActionPerformed
    // TODO add your handling code here:
        objTblModImp.removeAllRows();
        System.out.println("VALOR DEL BOTON: " + butConRetImp.getText());
        if (butConRetImp.getText().equals("Consultar Retenciones"))
            {
                blnConImp=true;
                if (objThrGUIImp==null)
                {
                    objThrGUIImp=new ZafThreadGUIImp();
                    objThrGUIImp.start();

                }
            }
            else
            {
                blnConImp=false;
            }
}//GEN-LAST:event_butConRetImpActionPerformed

private void butProRetImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProRetImpActionPerformed
    // TODO add your handling code here:
    if(isCamVal()){
        lblMsgSis.setText("Imprimiendo...");
        if(imprimirRetencionesFaltantes()){
            mostrarMsgInf("<HTML>El proceso de impresión se realizó con éxito.</HTML>");
            lblMsgSis.setText("Listo");
        }
        else{
            mostrarMsgInf("<HTML>El proceso de impresión no se pudo realizar</HTML>");
            lblMsgSis.setText("Proceso de impresión fallido");
        }
        eliminaTablaRegistrosSeleccionados();
    }


}//GEN-LAST:event_butProRetImpActionPerformed

private void butSetEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSetEstActionPerformed
    // TODO add your handling code here:
    String strSQLUpdEst="", strLin="";
    try{
        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        con.setAutoCommit(false);
        if(con!=null){
            if(cambiarEstadoImpresion())
                con.commit();
            else
                con.rollback();
            con.close();
            con=null;
        }
    }
    catch(Exception e){
        objUti.mostrarMsgErr_F1(this, e);
    }
}//GEN-LAST:event_butSetEstActionPerformed

private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
    // TODO add your handling code here:
    if(pasActFil.getText().trim().equals("fwraiglm")){
        activarFiltro();
    }
    else{
        desactivarFiltro();
        mostrarMsgInf("Clave incorrecta");
    }
}//GEN-LAST:event_jButton1ActionPerformed

private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
    // TODO add your handling code here:
    desactivarFiltro();
}//GEN-LAST:event_jButton2ActionPerformed

    private boolean activarFiltro(){
        boolean blnRes=true;
        try{
            chkNotIncNumDoc.setEnabled(true);
            butSetEst.setEnabled(true);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean desactivarFiltro(){
        boolean blnRes=true;
        try{
            pasActFil.setText("");
            chkNotIncNumDoc.setSelected(false);
            txtCodDocDes.setText("");
            txtCodDocHas.setText("");
            chkNotIncNumDoc.setEnabled(false);
            butSetEst.setEnabled(false);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


private boolean cambiarEstadoImpresion(){
    boolean blnRes=true;
    String strLin="", strSQLUpdEst="";
    try{
        if(con!=null){
            stm=con.createStatement();
            strSQL="";
            strSQL+="UPDATE tbm_cabPag";
            strSQL+=" SET st_imp='N'";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
            strSQL+=" AND co_doc>=" + txtCodDocDes.getText() + "AND co_doc<=" + txtCodDocHas.getText() + "";
            strSQL+=";";
            strSQLUpdEst+=strSQL;

            //en tbm_cabDia.tx_numDia
            strSQL="";
            strSQL+="UPDATE tbm_cabDia";
            strSQL+=" SET st_imp='N'";
            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
            strSQL+=" AND co_dia>=" + txtCodDocDes.getText() + "AND co_dia<=" + txtCodDocHas.getText() + "";
            strSQL+=";";
            strSQLUpdEst+=strSQL;
            stm.executeUpdate(strSQLUpdEst);
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






    /** Cerrar la aplicacián. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCon;
    private javax.swing.JButton butConRetImp;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butPro;
    private javax.swing.JButton butProRetImp;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butSetEst;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkNotIncNumDoc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lbCta;
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
    private javax.swing.JPanel panRetFalImp;
    private javax.swing.JPasswordField pasActFil;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnRetFalImp;
    private javax.swing.JScrollPane spnTblDet;
    private javax.swing.JScrollPane spnTmAsiDia;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatAsiDia;
    private javax.swing.JTable tblDatDet;
    private javax.swing.JTable tblDatRetFalImp;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodDocDes;
    private javax.swing.JTextField txtCodDocHas;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarCta;
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
            this.setTitle(strAux + " v0.14.2");
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConCta();
            configurarVenConPrv();
            //Configurar los JTables.
            if(configurarTblDat())
                if(cargarTipoDocumentoUsuario())
                    configurarTblDet();
            




            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
            configurarTblDatAsiDia();

            configurarTblDatRetFalImp();

            desactivarFiltro();


            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);


//            if(System.getProperty("os.name").equals("Linux")){
//                strDirRep="//zafiro//Reportes/Contabilidad/ZafCon53/ZafRptCon05.jrxml";
//            }
//            else{
//                strDirRep="C://Zafiro//Reportes/Contabilidad/ZafCon53/ZafRptCon05.jrxml";
//            }






            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
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
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
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
//                    if (objTblCelEdiChk.isChecked()){
                    if(optTod.isSelected()){
                        optTod.setSelected(false);
                        optFil.setSelected(true);
                    }
//                    }
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
       


    private boolean configurarTblDatRetFalImp(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatImp=new Vector();    //Almacena los datos
            vecCabImp=new Vector(11);  //Almacena las cabeceras
            vecCabImp.clear();
            vecCabImp.add(INT_TBL_DAT_IMP_LIN,"");
            vecCabImp.add(INT_TBL_DAT_IMP_CHK,"");
            vecCabImp.add(INT_TBL_DAT_IMP_COD_EMP,"Cód.Emp.");
            vecCabImp.add(INT_TBL_DAT_IMP_COD_LOC,"Cód.Loc.");
            vecCabImp.add(INT_TBL_DAT_IMP_COD_TIP_DOC,"Cod.Tip.Doc.");
            vecCabImp.add(INT_TBL_DAT_IMP_COD_DOC,"Cod.Doc.");
            vecCabImp.add(INT_TBL_DAT_IMP_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCabImp.add(INT_TBL_DAT_IMP_COD_CLI,"Cod.Cli.");
            vecCabImp.add(INT_TBL_DAT_IMP_NOM_BEN,"Nom.Benc.");
            vecCabImp.add(INT_TBL_DAT_IMP_VAL_RET,"Val.Rte.");
            vecCabImp.add(INT_TBL_DAT_IMP_DOC_CAN,"Doc.Can.");



            objTblModImp=new ZafTblMod();
            objTblModImp.setHeader(vecCabImp);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatRetFalImp.setModel(objTblModImp);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDatRetFalImp.setRowSelectionAllowed(true);
            tblDatRetFalImp.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDatRetFalImp);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatRetFalImp.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatRetFalImp.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_IMP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_IMP_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IMP_COD_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_IMP_NOM_BEN).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_IMP_VAL_RET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_IMP_DOC_CAN).setPreferredWidth(120);



            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_IMP_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatRetFalImp.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDatRetFalImp);
            tcmAux.getColumn(INT_TBL_DAT_IMP_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_IMP_CHK);
            objTblModImp.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiImp=new ZafTblEdi(tblDatRetFalImp);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkImp=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_IMP_CHK).setCellRenderer(objTblCelRenChkImp);
            objTblCelRenChkImp=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkImp=new ZafTblCelEdiChk(tblDatRetFalImp);
            tcmAux.getColumn(INT_TBL_DAT_IMP_CHK).setCellEditor(objTblCelEdiChkImp);

            objTblModImp.setModoOperacion(objTblModImp.INT_TBL_EDI);

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDatRetFalImp.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblEmpMouseClicked(evt);
                }
            });

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
            
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodTipDoc.setEnabled(false);
            
            txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCta.setBackground(objParSis.getColorCamposObligatorios());
            txtCodCta.setVisible(false);
            txtCodCta.setEditable(false);
            txtCodCta.setEnabled(false);
            objAsiDia=new ZafAsiDia(objParSis);
            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            //Configurar JTable: Establecer el modelo.
            vecDatDet=new Vector();    //Almacena los datos
            vecCabDet=new Vector(7);  //Almacena las cabeceras
            vecCabDet.clear();
            vecCabDet.add(INT_TBL_DAT_REP_LIN,"");
            vecCabDet.add(INT_TBL_DAT_REP_CHK,"");
            vecCabDet.add(INT_TBL_DAT_REP_NUM_DOC,"Núm.Doc.");
            vecCabDet.add(INT_TBL_DAT_REP_COD_PRV,"Cód.Prv.");
            vecCabDet.add(INT_TBL_DAT_REP_NOM_PRV,"Nom.Prv.");
            vecCabDet.add(INT_TBL_DAT_REP_VAL_DOC_RET,"Val.Ret.");
            vecCabDet.add(INT_TBL_DAT_REP_BUT_ANE,"");
            
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCabDet);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatDet.setModel(objTblModDet);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDatDet.setRowSelectionAllowed(true);
            tblDatDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDatDet);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_REP_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_REP_NUM_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_REP_COD_PRV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_REP_NOM_PRV).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_REP_VAL_DOC_RET).setPreferredWidth(80);
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
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDet=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setCellRenderer(objTblCelRenChkDet);
            objTblCelRenChkDet=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDet=new ZafTblCelEdiChk(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_REP_CHK).setCellEditor(objTblCelEdiChkDet);
            //Libero los objetos auxiliares.
            tcmAux=null;            
            
            configurarVenConTipDoc();
            
            
            objTblCelRenButAne=new ZafTblCelRenBut();
            tblDatDet.getColumnModel().getColumn(INT_TBL_DAT_REP_BUT_ANE).setCellRenderer(objTblCelRenButAne);
            objCon53_01=new ZafCon53_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButAne= new ZafTblCelEdiButDlg(objCon53_01);
            tblDatDet.getColumnModel().getColumn(INT_TBL_DAT_REP_BUT_ANE).setCellEditor(objTblCelEdiButAne);
            objTblCelEdiButAne.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                        //objCon09_01.colokObs(""+objTblMod.getValueAt(tblDat.getSelectedRow(), tblDat.getSelectedColumn()));
                if(objSelDat.chkIsSelected())
                    objCon53_01.setFechaCorte("AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'");
                else
                    objCon53_01.setFechaCorte("");

                    objCon53_01.setCodigoProveedor(objTblModDet.getValueAt(tblDatDet.getSelectedRow(), INT_TBL_DAT_REP_COD_PRV).toString());
                    objCon53_01.setCodigoTipoDocumento(getTipoDocConPagTot());
                    objCon53_01.cargarDatosRetener();
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
            vecAux.add("" + INT_TBL_DAT_REP_BUT_ANE);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);

            objTblBusDet=new ZafTblBus(tblDatDet);
            
            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    


    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
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
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta funcián muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }


    
    /**
     * Esta funcián permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningán problema.
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cádigo");
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
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=533";
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=533";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
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
    
    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Cuentas".
     */
    private boolean configurarVenConCta()
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
            vcoCta=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
            arlAli.add("Cádigo");
            arlAli.add("Identificacián");
            arlAli.add("Nombre");
            arlAli.add("Direccián");
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
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
                case 1: //Básqueda directa por "Námero de cuenta".
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
                case 2: //Básqueda directa por "Descripcián larga".
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
     * Se la usa en el sistema para determinar si existe algán cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
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
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
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
                    strMsg="Descripcián larga del tipo de documento";
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
                case INT_TBL_DAT_REP_COD_PRV:
                    strMsg="Código del proveedor";
                    break;
                case INT_TBL_DAT_REP_NOM_PRV:
                    strMsg="Nombre del proveedor";
                    break;
                case INT_TBL_DAT_REP_VAL_DOC_RET:
                    strMsg="Valor del documento";
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
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
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
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
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConCta(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            //Validar que sálo se pueda seleccionar la "Cuenta" si se seleccioná el "Tipo de documento".
            if (txtCodTipDoc.getText().equals(""))
            {
                txtCodCta.setText("");
                txtDesCorCta.setText("");
                txtDesLarCta.setText("");
                mostrarMsgInf("<HTML>Primero debe seleccionar un <FONT COLOR=\"blue\">Tipo de documento</FONT>.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                txtDesCorTipDoc.requestFocus();
            }
            else
            {
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
                strSQL+=" ORDER BY a1.tx_codCta";
                vcoCta.setSentenciaSQL(strSQL);
                switch (intTipBus)
                {
                    case 0: //Mostrar la ventana de consulta.
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.show();
                        if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".
                        if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(0);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                            }
                            else
                            {
                                txtDesCorCta.setText(strDesCorCta);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".
                        if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText()))
                        {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                        }
                        else
                        {
                            vcoCta.setCampoBusqueda(1);
                            vcoCta.setCriterio1(11);
                            vcoCta.cargarDatos();
                            vcoCta.show();
                            if (vcoCta.getSelectedButton()==vcoCta.INT_BUT_ACE)
                            {
                                txtCodCta.setText(vcoCta.getValueAt(1));
                                txtDesCorCta.setText(vcoCta.getValueAt(2));
                                txtDesLarCta.setText(vcoCta.getValueAt(3));
                            }
                            else
                            {
                                txtDesLarCta.setText(strDesLarCta);
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
     * Esta clase crea un hilo que permite manipular la interface gráfica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que está ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podráa presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estaráa informado en todo
     * momento de lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase
     * ya que si no sálo se apreciaráa los cambios cuando ha terminado todo el proceso.
     */
    
 

    
    private class ZafThreadGUIPagTot extends Thread
    {
        public void run()
        {
            if (!cargarRegPagTot())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUIPagTot=null;
        }
    }



    private class ZafThreadGUIImp extends Thread
    {
        public void run()
        {
            if (!cargarRetencionesSinImprimir())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butConRetImp.setText("Consultar Retenciones");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDatRetFalImp.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(3);
                tblDatRetFalImp.setRowSelectionInterval(0, 0);
                tblDatRetFalImp.requestFocus();
            }
            objThrGUIImp=null;
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
                strSQL+="SELECT a1.co_cli, a3.tx_nom, SUM(a2.mo_pag + a2.nd_abo) as nd_val";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS a3 ON a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN (";
                strSQL+=" 		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+="                                      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+="                                      FROM tbm_cabTipDoc AS b1";
                strSQL+="                                      WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                      AND b1.st_reg='A') AS a";
                strSQL+="                              INNER JOIN(";
                strSQL+="                                      SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+="                                              SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+="                                              FROM tbm_visBueMovInv AS b1";
                strSQL+="                                              WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                              AND b1.st_reg='A'";
                strSQL+="                                              ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+="                                      ) AS x";
                strSQL+="                                      GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+="                              ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+=" ) AS p";
                strSQL+=" ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F') ";
                strSQL+=" AND a2.st_reg IN('A','C') AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                if(objSelDat.chkIsSelected())
                    strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                if( ! txtCodPrv.getText().toString().equals(""))
                    strSQL+=" AND a1.co_cli IN(" + txtCodPrv.getText() + ")";

                strSQL+=" AND (a2.tx_numSer IS NOT NULL AND a2.tx_fecCad IS NOT NULL AND a2.tx_numAutSri IS NOT NULL AND a2.tx_numChq IS NOT NULL)";
                strSQL+=" GROUP BY a1.co_cli, a3.tx_nom";
                strSQL+=" HAVING SUM(a2.mo_pag + a2.nd_abo) > 0";
                strSQL+=" ORDER BY a3.tx_nom";
                System.out.println("cargarRegPagTot: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while(rst.next()){
                    if (blnCon){
                        vecRegDet=new Vector();
                        vecRegDet.add(INT_TBL_DAT_REP_LIN, "");
                        vecRegDet.add(INT_TBL_DAT_REP_CHK, "");
                        vecRegDet.add(INT_TBL_DAT_REP_NUM_DOC, "0");
                        vecRegDet.add(INT_TBL_DAT_REP_COD_PRV, "" + rst.getString("co_cli"));
                        vecRegDet.add(INT_TBL_DAT_REP_NOM_PRV, "" + rst.getString("tx_nom"));
                        vecRegDet.add(INT_TBL_DAT_REP_VAL_DOC_RET, "" + rst.getString("nd_val"));
                        vecRegDet.add(INT_TBL_DAT_REP_BUT_ANE, null);
                        vecDatDet.add(vecRegDet);
                        i++;
                        pgrSis.setValue(i);
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
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                //objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
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
    
    /**La funcián ZafThreadGUIPro almacena en una tabla temporal los datos necesarios para
     *luego poder realizar el proceso de unificacián de clientes en todas las compaááas
     */
    private class ZafThreadGUIPro extends Thread{
        public void run(){
            if(isCamFil()){
                if(procesar()){
                    mostrarMsgInf("<HTML>El proceso guardar se realizó con éxito.</HTML>");
                    lblMsgSis.setText("Listo");

                    //para el proceso de impresión
                    mostrarMsgInf("<HTML>Las retenciones generadas serán impresas a continuación.</HTML>");
                    lblMsgSis.setText("Imprimiendo...");
                    if(imprimir()){
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

            cargarRetencionesSinImprimir();
            if (tblDatRetFalImp.getRowCount()>0){
                tabFrm.setSelectedIndex(3);
                tblDatRetFalImp.setRowSelectionInterval(0, 0);
                tblDatRetFalImp.requestFocus();
            }
            
        }
    }
    
    
    private boolean procesar(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(guardaArregloCabeceraRetencion()){
                    if(guardaArregloDetalleRetencion()){
                        if(insertar_tbmCabPag()){
                            if(insertar_tbmDetPag()){
                                if(actualizar_tbmPagMovInv()){//dentro de esta funcion llamar a insertarDiario.
                                    if(insertarDiario()){
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
    

    private boolean guardaArregloCabeceraRetencion(){
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
                
                strSQL="";
                strSQL+="SELECT SUM(a2.mo_pag+a2.nd_abo) AS nd_valRet, b2.co_cli, b2.tx_nom, b2.tx_ide, b2.tx_dir";
                strSQL+=" ,x.tx_numSer, x.tx_fecCad, x.tx_numAutSRI, x.tx_numChq";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN(";
                strSQL+="                 SELECT a1.co_emp, a1.co_cli, a2.tx_numSer, a2.tx_fecCad, a2.tx_numAutSRI, a2.tx_numChq";
                strSQL+="                  FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="                 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+="                  AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                  INNER JOIN (";
                strSQL+="                  		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+="                                                      SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+="                                                       FROM tbm_cabTipDoc AS b1";
                strSQL+="                                                       WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                       AND b1.st_reg='A') AS a";
                strSQL+="                                               INNER JOIN(";
                strSQL+="                                                       SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+="                                                               SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+="                                                               FROM tbm_visBueMovInv AS b1";
                strSQL+="                                                               WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                               AND b1.st_reg='A'";
                strSQL+="                                                               ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+="                                                       ) AS x";
                strSQL+="                                                       GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+="                                               ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+="                  ) AS p";
                strSQL+="                  ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                  AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                  AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+="                  AND a2.st_reg IN('A','C')";
                strSQL+="                  AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";   
                strSQL+="                  AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_cli, a2.tx_numSer, a2.tx_fecCad, a2.tx_numAutSRI, a2.tx_numChq) AS x";
                strSQL+=" ON a1.co_emp=x.co_emp AND a1.co_cli=x.co_cli AND a2.tx_numSer=x.tx_numSer ";
                strSQL+=" AND a2.tx_fecCad=x.tx_fecCad AND a2.tx_numAutSRI=x.tx_numAutSRI AND a2.tx_numChq=x.tx_numChq";
                strSQL+=" AND x.tx_numSer IS NOT NULL AND x.tx_fecCad IS NOT NULL AND x.tx_numAutSRI IS NOT NULL AND x.tx_numChq IS NOT NULL";

                strSQL+=" INNER JOIN tbm_cli AS b2 ON a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli";
                strSQL+=" WHERE (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                  AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+="                  AND a2.st_reg IN('A','C')";
                strSQL+=" GROUP BY b2.co_cli, b2.tx_nom, b2.tx_ide, b2.tx_dir,x.tx_numSer, x.tx_fecCad, x.tx_numAutSRI, x.tx_numChq";
                strSQL+=" ORDER BY b2.co_cli";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    intUltReg++;
                    arlRegCabPag=new ArrayList();
                    arlRegCabPag.add(INT_ARL_CAB_COD_EMP,       "" + objParSis.getCodigoEmpresa());
                    arlRegCabPag.add(INT_ARL_CAB_COD_LOC,       "" + objParSis.getCodigoLocal());
                    arlRegCabPag.add(INT_ARL_CAB_COD_TIP_DOC,   "" + txtCodTipDoc.getText());
                    arlRegCabPag.add(INT_ARL_CAB_COD_DOC,       "" + intUltReg);
                    arlRegCabPag.add(INT_ARL_CAB_FEC_DOC,       "" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()));
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_UNO,   "0");
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_DOS,   "0");
                    arlRegCabPag.add(INT_ARL_CAB_COD_CLI,       "" + rst.getString("co_cli"));
                    arlRegCabPag.add(INT_ARL_CAB_RUC_CLI,       "" + rst.getString("tx_ide"));
                    arlRegCabPag.add(INT_ARL_CAB_NOM_CLI,       "" + rst.getString("tx_nom"));
                    arlRegCabPag.add(INT_ARL_CAB_DIR_CLI,       "" + rst.getString("tx_dir"));
                    arlRegCabPag.add(INT_ARL_CAB_MON_DOC,       "" + rst.getString("nd_valRet"));
                    arlRegCabPag.add(INT_ARL_CAB_SEC_DOC,       "" + rst.getString("tx_numSer"));
                    arlRegCabPag.add(INT_ARL_CAB_FEC_CAD,       "" + rst.getString("tx_fecCad"));
                    arlRegCabPag.add(INT_ARL_CAB_NUM_AUT_SRI,   "" + rst.getString("tx_numAutSRI"));
                    arlRegCabPag.add(INT_ARL_CAB_NUM_PED,       "" + rst.getString("tx_numChq"));
                    arlRegCabPag.add(INT_ARL_CAB_NUM_DOC_CAB_MOV_INV,       "");
                    
                    arlDatCabPag.add(arlRegCabPag);
                }
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
    
    
    private boolean guardaArregloDetalleRetencion(){
        boolean blnRes=true;
        arlDatDetPag.clear();
        String strRstSecDoc="", strRstFecCad="", strRstNumAutSRI="", strRstNumPed="";
        String strArlCabSecDoc="", strArlCabFecCad="", strArlCabNumAutSRI="", strArlCabNumPed="";
        String strArlDetSecDocUlt="", strArlDetFecCadUlt="", strArlDetNumAutSRIUlt="", strArlDetNumPedUlt="";
        int j=0;
        String strGloDoc="", strGloDocAnt="";
        strAux="";
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, x.co_cli, ";
                strSQL+=" (a2.mo_pag + a2.nd_abo) AS nd_valRet, a2.tx_codSRI";
                strSQL+=" ,x.tx_numSer, x.tx_fecCad, x.tx_numAutSRI, x.tx_numChq, a1.ne_numDoc";
                strSQL+=" FROM tbm_cabMovInv AS a1 ";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN(";
                strSQL+="                 SELECT a1.co_emp, a1.co_cli, a2.tx_numSer, a2.tx_fecCad, a2.tx_numAutSri, a2.tx_numChq";
                strSQL+="                  FROM tbm_cabMovInv AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQL+="                  ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+="                  AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                 INNER JOIN (";
                strSQL+="                  		SELECT b.co_emp, b.co_loc, b.co_tipDoc, b.co_doc, b.ne_numVisBue FROM(";
                strSQL+="                                                       SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.ne_numVisBue";
                strSQL+="                                                       FROM tbm_cabTipDoc AS b1";
                strSQL+="                                                       WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                       AND b1.st_reg='A') AS a";
                strSQL+="                                               INNER JOIN(";
                strSQL+="                                                       SELECT COUNT(co_visBue) AS ne_numVisBue, x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc FROM(";
                strSQL+="                                                               SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue";
                strSQL+="                                                               FROM tbm_visBueMovInv AS b1";
                strSQL+="                                                               WHERE b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                                                               AND b1.st_reg='A'";
                strSQL+="                                                               ORDER BY b1.co_doc, b1.co_visBue";
                strSQL+="                                                       ) AS x";
                strSQL+="                                                       GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_doc) AS b";
                strSQL+="                                               ON a.co_emp=b.co_emp AND a.co_loc=b.co_loc AND a.co_tipDoc=b.co_tipDoc AND a.ne_numVisBue=b.ne_numVisBue";
                strSQL+="                  ) AS p";
                strSQL+="                  ON a1.co_emp=p.co_emp AND a1.co_loc=p.co_loc AND a1.co_tipDoc=p.co_tipDoc AND a1.co_doc=p.co_doc";
                strSQL+="                  WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="                  AND (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                  AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+="                  AND a2.st_reg IN('A','C')";
                strSQL+="                  AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";   
                strSQL+="                  AND a1.co_cli IN(" + getCodigoProveedorSeleccionado() + ")";
                strSQL+=" 		 GROUP BY a1.co_emp, a1.co_cli, a2.tx_numSer, a2.tx_fecCad, a2.tx_numAutSri, a2.tx_numChq) AS x";
                strSQL+=" ON a1.co_emp=x.co_emp AND a1.co_cli=x.co_cli AND a2.tx_numSer=x.tx_numSer ";
                strSQL+=" AND a2.tx_fecCad=x.tx_fecCad AND a2.tx_numAutSri=x.tx_numAutSri AND a2.tx_numChq=x.tx_numChq";
                strSQL+=" AND x.tx_numSer IS NOT NULL AND x.tx_fecCad IS NOT NULL AND x.tx_numAutSri IS NOT NULL AND x.tx_numChq IS NOT NULL";
                strSQL+=" INNER JOIN tbm_cli AS b2 ON a1.co_emp=b2.co_emp AND a1.co_cli=b2.co_cli";
                strSQL+=" WHERE (a2.mo_pag + a2.nd_abo) > 0";
                strSQL+="                  AND (a2.nd_porRet > 0) AND a1.st_reg IN ('A','R','C','F')";
                strSQL+="                  AND a2.st_reg IN('A','C')";
                strSQL+=" ORDER BY x.co_cli";


                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    strRstSecDoc=rst.getString("tx_numSer");
                    strRstFecCad=rst.getString("tx_fecCad");
                    strRstNumAutSRI=rst.getString("tx_numAutSRI");
                    strRstNumPed=rst.getString("tx_numChq");
                    
                    if(arlDatDetPag.size()>0){
                        strArlDetSecDocUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_SEC_DOC);
                        strArlDetFecCadUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_FEC_CAD);
                        strArlDetNumAutSRIUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_NUM_AUT_SRI);
                        strArlDetNumPedUlt=objUti.getStringValueAt(arlDatDetPag, (arlDatDetPag.size()-1), INT_ARL_DET_NUM_PED);
                        if(   (strRstSecDoc.equals(strArlDetSecDocUlt)) && (strRstFecCad.equals(strArlDetFecCadUlt)) && (strRstNumAutSRI.equals(strArlDetNumAutSRIUlt)) && (strRstNumPed.equals(strArlDetNumPedUlt))  ){
                            j++;
                            if(  !  (strGloDocAnt.equals(rst.getString("ne_numDoc")))   ){
                                strGloDoc+="/" + rst.getString("ne_numDoc");
                            }
                            strGloDocAnt=rst.getString("ne_numDoc");
                        }
                        else{
                            j=1;
                            strGloDoc=rst.getString("ne_numDoc");
                            strGloDocAnt=rst.getString("ne_numDoc");
                        }
                    }
                    else{
                        j=1;
                        strGloDoc=rst.getString("ne_numDoc");
                        strGloDocAnt=rst.getString("ne_numDoc");
                    }
                    arlRegDetPag=new ArrayList();
                    arlRegDetPag.add(INT_ARL_DET_COD_EMP,           "" + objParSis.getCodigoEmpresa());
                    for(int k=0; k<arlDatCabPag.size(); k++){
                        strArlCabSecDoc=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_SEC_DOC);
                        strArlCabFecCad=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_FEC_CAD);
                        strArlCabNumAutSRI=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_NUM_AUT_SRI);
                        strArlCabNumPed=objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_NUM_PED);
                        
                        
                        
                        if(strRstSecDoc.equals(strArlCabSecDoc)){
                            if(strRstFecCad.equals(strArlCabFecCad)){
                                if(strRstNumAutSRI.equals(strArlCabNumAutSRI)){
                                    if(strRstNumPed.equals(strArlCabNumPed)){
                                        arlRegDetPag.add(INT_ARL_DET_COD_LOC,     "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_LOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_TIP_DOC, "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_TIP_DOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_DOC,     "" + objUti.getStringValueAt(arlDatCabPag, k, INT_ARL_CAB_COD_DOC));
                                        arlRegDetPag.add(INT_ARL_DET_COD_REG,     "" + j);//ver bien como hacer para colocar el registro
                                        objUti.setStringValueAt(arlDatCabPag, k, INT_ARL_CAB_NUM_DOC_CAB_MOV_INV, strGloDoc);
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
                    arlRegDetPag.add(INT_ARL_DET_VAL_ABO,           "" + rst.getString("nd_valRet"));
                    arlRegDetPag.add(INT_ARL_DET_COD_SRI,           "" + rst.getString("tx_codSRI"));
                    arlRegDetPag.add(INT_ARL_DET_SEC_DOC,           "" + rst.getString("tx_numSer"));
                    arlRegDetPag.add(INT_ARL_DET_FEC_CAD,           "" + rst.getString("tx_fecCad"));
                    arlRegDetPag.add(INT_ARL_DET_NUM_AUT_SRI,       "" + rst.getString("tx_numAutSRI"));
                    arlRegDetPag.add(INT_ARL_DET_NUM_PED,           "" + rst.getString("tx_numChq"));
                    arlDatDetPag.add(arlRegDetPag);
                }
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
                    strSQL+="'" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_DOC) + "',";//fe_ven
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_UNO) + ",";//ne_numdoc1
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_DOS) + ",";//ne_numdoc2
                    strSQL+=" Null,";//co_cta
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CLI) + ",";//co_cli
                    strSQL+="" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_RUC_CLI) + ",";//tx_ruc
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
                    strSQL+="Null,";//co_ben
                    strSQL+="Null,";//tx_benchq
                    strSQL+="Null,";//tx_mondocpal
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
                    strSQL+="             tx_numchq, fe_recchq, fe_venchq, st_regrep,";
                    strSQL+="             st_reg, co_locRelDepCliRegDirBan, co_tipDocRelDepCliRegDirBan, co_docRelDepCliRegDirBan, co_regRelDepCliRegDirBan)";
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
//                    if(objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_SRI).equals("null"))
//                        strSQL+="Null" ;//tx_codsri
//                    else
//                        strSQL+="'" + objUti.getStringValueAt(arlDatDetPag, i, INT_ARL_DET_COD_SRI) + "'" ;//tx_codsri

                    strSQL+="'A',";//st_reg
                    strSQL+="Null,";//co_locRelDepCliRegDirBan
                    strSQL+="Null,";//co_tipDocRelDepCliRegDirBan
                    strSQL+="Null,";//co_docRelDepCliRegDirBan
                    strSQL+="Null";//co_regRelDepCliRegDirBan


                    strSQL+=");";
                    strSQLGrl+=strSQL;
                }
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
        String strSecDoc="";String strFecCad="";String strNumAutSri="";String strNumPed="";
        String strCodPrv="";
        
        
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
                    objAsiDia.inicializar();
                    
                    strSecDoc=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_SEC_DOC);
                    strFecCad=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_FEC_CAD);
                    strNumAutSri=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_AUT_SRI);
                    strNumPed=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_PED);
                    strCodPrv=objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_CLI);
                            
                            
                    
                    cargarDatosRetenerAsiDia(intCodEmp, intCodLoc, intCodTipDoc, strCodDia, strCodPrv);
                    
                    objAsiDia.generarDiario(Integer.parseInt(txtCodTipDoc.getText()), tblDatAsiDia, INT_TBL_DAT_ASI_DIA_CHK, INT_TBL_DAT_ASI_DIA_COD_TIP_RET, INT_TBL_DAT_ASI_DIA_VAL_RET);
                    
                    objAsiDia.setCamTblUltDoc("L");
//                    
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
        //Validar el "Fecha del documento".
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        //Validar el "Cuenta deudora".
        if (txtCodCta.getText().equals("")){
            tabFrm.setSelectedIndex(1);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cuenta deudora</FONT> es obligatorio.<BR>Escriba o seleccione una cuenta deudora y vuelva a intentarlo.</HTML>");
            txtDesCorCta.requestFocus();
            return false;
        }
        
        
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
                        if (!dtpFecDoc.isFecha()){
                            mostrarMsgInf("<HTML>La fecha ingresada no es correcta.<BR>Verifique y vuelva a intentarlo</HTML>");
                            return false;
                        }
//                        else
//                            System.out.println("SI ES UNA FECHA");
                        
                        
                        
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
        

        
        return true;
        
    }
    
    
//    private boolean procesoImprimir(){
//        boolean blnRes=false;
//        try{
//            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//            if(con!=null){
//                if(imprimir()){
//                    blnRes=true;
//                    con.commit();
//                }
//                else{
//                    con.rollback();
//                }
//                con.close();
//                con=null;
//            }
//        }
//        catch(java.sql.SQLException e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
//        catch(Exception e){
//            objUti.mostrarMsgErr_F1(this, e);
//            blnRes=false;
//        }
//        return blnRes;
//    }



    
    
    private boolean imprimir(){
        boolean blnRes=true;
        int intUltDoc=0;
        String strGloCabDia="";
        String strSQLUpdGrl="";
        String strSQLRep="", strSQLSubRep="";
        try{

            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(false);

            strDirRep=objRptSis.getRutaReporte(0) + "ZafRptCon05.jrxml";

            for(int i=0; i<arlDatCabPag.size(); i++){
                System.out.println("arlDatCabPag: " + arlDatCabPag.toString());
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                con.setAutoCommit(false);
                if(con!=null){
                    stm=con.createStatement();
                    strGloCabDia="";
                    //para obtener tbm_cabTipDoc.ne_ultDoc
                    strSQL="";
                    strSQL+="SELECT MAX(a1.ne_ultDoc) AS ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        intUltDoc=(rst.getInt("ne_ultDoc")+1);
                    }

                    //para obtener la glosa
                    strGloCabDia+=txtDesCorTipDoc.getText() + ": " + intUltDoc + "";
                    strGloCabDia+=";  Beneficiario: " + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NOM_CLI) + "";
                    strGloCabDia+=";  Documentos: " + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_NUM_DOC_CAB_MOV_INV) + "";

                    //se actualizan los campos en cada tabla
                    //en tbm_cabTipDoc.ne_ultDoc
                    strSQL="";
                    strSQL+="UPDATE tbm_cabTipDoc";
                    strSQL+=" SET ne_ultDoc=" + intUltDoc + "";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=";";
                    strSQLUpdGrl+=strSQL;

                    //en tbm_cabPag.ne_numDoc1 y tbm_cabPag.ne_numDoc2
                    strSQL="";
                    strSQL+="UPDATE tbm_cabPag";
                    strSQL+=" SET ne_numDoc1=" + intUltDoc + "";
                    strSQL+=" , ne_numDoc2=" + intUltDoc + "";
                    strSQL+=" , st_imp='S'";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=";";
                    strSQLUpdGrl+=strSQL;

                    //en tbm_cabDia.tx_numDia
                    strSQL="";
                    strSQL+="UPDATE tbm_cabDia";
                    strSQL+=" SET tx_numDia=" + intUltDoc + "";
                    strSQL+=" , tx_glo='" + strGloCabDia + "'";
                    strSQL+=" , st_imp='S'";
                    strSQL+=" WHERE co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND co_dia=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=";";
                    strSQLUpdGrl+=strSQL;


                    stm.executeUpdate(strSQLUpdGrl);
                    strSQLUpdGrl="";



                    String strNumDocPgd="";
                    strSQL="";
                    strSQL+="SELECT a3.ne_numdoc FROM (";
                    strSQL+=" tbm_cabPag AS b1";
                    strSQL+=" INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
                    strSQL+=" INNER JOIN tbm_detPag AS a1";
                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ";
                    strSQL+=" ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP);
                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC);
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=" GROUP BY a3.ne_numdoc";
                    strSQL+=" ORDER BY a3.ne_numdoc";
                    System.out.println("docPgdos: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while (rst.next()){
                        strNumDocPgd=rst.getString("ne_numdoc") + ",";
                    }



//                    strSQL="";
//                    strSQL+=" SELECT b1.tx_nomCli, b1.tx_ruc AS tx_rucCli";
//                    strSQL+=" , extract(day from b1.fe_doc)  ||'-'||";
//                    strSQL+=" 	case extract(month from b1.fe_doc)";
//                    strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
//                    strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
//                    strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
//                    strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
//                    strSQL+=" , b1.tx_dirCli, cast( '" + strNumDocPgd + "' as character varying) AS ne_numordcomcan, a2.tx_numAutSRI AS tx_numAutSRICli, a2.tx_fecCad AS tx_fecCadCli";
//                    strSQL+=" , (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, b1.tx_obs2, CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis";
//                    strSQL+=" FROM tbm_cabPag AS b1 INNER JOIN tbm_detPag AS a1";
//                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
//                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
//                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
//                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
//                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
//                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
//                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
//                    strSQL+=" GROUP BY b1.tx_nomCli, b1.tx_ruc, b1.fe_doc, b1.tx_dirCli";
//                    strSQL+=" , a2.tx_numAutSRI, a2.tx_fecCad,a2.tx_numSer, a2.tx_numChq, b1.tx_obs2, b1.fe_doc";
//                    strSQLRep=strSQL;
//                    System.out.println("strSQLRep: " + strSQLRep);
//
//
//                    strSQL="";
//                    strSQL+="SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
//                    strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
//                    strSQL+=" 	when round(a2.nd_porret) in(30,70,100) then '    I.V.A.' end AS Impuesto";
//                    strSQL+=" , round(a2.nd_porret) AS nd_porret";
//                    strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, a2.tx_codSRI";
//                    strSQL+=" FROM (";
//                    strSQL+=" 	tbm_cabPag AS b1";
//                    strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
//                    strSQL+=" INNER JOIN tbm_detPag AS a1";
//                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
//                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
//                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
//                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
//                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
//                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
//                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
//                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
//                    strSQL+=" GROUP BY b1.fe_doc";
//                    strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
//                    strSQL+=" ORDER BY a2.nd_porret";
//                    strSQLSubRep=strSQL;
//                    System.out.println("strSQLSubRep: " + strSQLSubRep);


                    strSQL="";
                    strSQL+="SELECT a1.tx_ruc AS tx_rucEmp, a1.tx_nom AS tx_nomEmp, a1.tx_dir AS tx_dirEmp, a1.tx_tel AS tx_telEmp,";
                    strSQL+="a1.tx_fax AS tx_faxEmp, a1.tx_dirWeb AS tx_dirWebEmp, a1.tx_corEle AS tx_corEleEmp, a1.tx_desConEsp AS tx_desConEspEmp,";
                    strSQL+="a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                    strSQL+="b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                    strSQL+=", b1.ne_numDoc1, lpad(''|| b1.ne_numDoc1,7,'0') AS ne_numDocFor";
                    strSQL+=", b1.fe_doc,";
                    strSQL+=" extract(day from b1.fe_doc)  ||'-'||";
                    strSQL+=" 	case extract(month from b1.fe_doc)";
                    strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
                    strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
                    strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
                    strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
                    //--cliente
                    strSQL+=", b1.co_cli, b1.tx_ruc AS tx_rucCli, b1.tx_nomCli, b1.tx_dirCli";
                    strSQL+=", b1.tx_obs1, b1.tx_obs2";
                    strSQL+=", cast( '" + strNumDocPgd + "' as character varying) AS ne_numOrdComCan";
                    strSQL+=", (a6.tx_numSer  || '-' || a6.tx_numChq ) as ne_numCmpVta";
                    strSQL+=", CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis";
                    strSQL+=" , a6.tx_fecCad AS tx_fecCadCli, a6.tx_numAutSRI AS tx_numAutSRICli, ABS(b1.nd_monDoc) AS ne_valRet";
                    strSQL+=" FROM (tbm_cabPag AS b1 INNER JOIN tbm_emp AS a1 ON(b1.co_emp=a1.co_emp)";
                    strSQL+=" 	INNER JOIN tbm_loc AS a5 ON (b1.co_emp=a5.co_emp AND b1.co_loc=a5.co_loc)";
                    strSQL+="	INNER JOIN tbm_datAutSri AS a4 ON (b1.co_emp=a4.co_emp AND b1.co_loc=a4.co_loc AND b1.co_tipDoc=a4.co_tipDoc)";
                    strSQL+="	INNER JOIN tbm_cabTipDoc AS a2 ON (b1.co_emp=a2.co_emp AND b1.co_loc=a2.co_loc AND b1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" )";
                    strSQL+=" INNER JOIN tbm_detPag AS b2";
                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                    strSQL+=" INNER JOIN tbm_pagMovInv AS a6";
                    strSQL+=" ON b2.co_emp=a6.co_emp AND b2.co_locPag=a6.co_loc AND b2.co_tipDocPag=a6.co_tipDoc AND b2.co_docPag=a6.co_doc AND b2.co_regPag=a6.co_reg";
                    strSQL+=" INNER JOIN tbm_cabMovInv AS a3";
                    strSQL+=" ON a6.co_emp=a3.co_emp AND a6.co_loc=a3.co_loc AND a6.co_tipDoc=a3.co_tipDoc AND a6.co_doc=a3.co_doc";
                    strSQL+=" WHERE b1.co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND b1.co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND b1.co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND b1.co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=" AND b1.ne_numDoc1>=a4.ne_numDocDes AND b1.ne_numDoc1<=a4.ne_numDocHas";
                    strSQL+=" GROUP BY a1.tx_ruc, a1.tx_nom, a1.tx_dir, a1.tx_tel,";
                    strSQL+=" a1.tx_fax, a1.tx_dirWeb, a1.tx_corEle, a1.tx_desConEsp,";
                    strSQL+=" a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                    strSQL+=" b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1";
                    strSQL+=" , b1.fe_doc, b1.co_cli, b1.tx_ruc,b1.tx_nomCli, b1.tx_dirCli";
                    strSQL+=" , b1.tx_obs1, b1.tx_obs2, a6.tx_numSer, a6.tx_numChq";
                    strSQL+=" , a6.tx_fecCad, a6.tx_numAutSRI, b1.nd_monDoc";
                    strSQLRep=strSQL;
                    System.out.println("strSQLRep: " + strSQLRep);                   

                    strSQL="";
                    strSQL+="SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
                    strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
                    strSQL+=" 	when round(a2.nd_porret) in(30,70,100) then '    I.V.A.' end AS Impuesto";
                    strSQL+=" , round(a2.nd_porret) AS nd_porret";
                    strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, a2.tx_codSRI";
                    strSQL+=" FROM (";
                    strSQL+=" 	tbm_cabPag AS b1";
                    strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
                    strSQL+=" INNER JOIN tbm_detPag AS a1";
                    strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
                    strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                    strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                    strSQL+=" WHERE a1.co_emp=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP) + "";
                    strSQL+=" AND a1.co_loc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC) + "";
                    strSQL+=" AND a1.co_tipDoc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC) + "";
                    strSQL+=" AND a1.co_doc=" + objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC) + "";
                    strSQL+=" GROUP BY b1.fe_doc";
                    strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
                    strSQL+=" ORDER BY a2.nd_porret";
                    strSQLSubRep=strSQL;
                    System.out.println("strSQLSubRep: " + strSQLSubRep);
                    
                    //para la impresión
                    Map parameters = new HashMap();
                    parameters.put("codEmp",    new Integer(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_EMP)));
                    parameters.put("codLoc",    new Integer(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_LOC)));
                    parameters.put("codTipDoc", new Integer(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_TIP_DOC)));
                    parameters.put("codDoc",    new Integer(objUti.getStringValueAt(arlDatCabPag, i, INT_ARL_CAB_COD_DOC)));
                    //parameters.put("Numdoc",    "" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_DOC_CAN));
                    parameters.put("strSQLRep", strSQLRep);
                    parameters.put("strSQLSubRep", strSQLSubRep);
                    parameters.put("SUBREPORT_DIR", objRptSis.getRutaReporte(0));



                    String GLO_strnomEmp = objParSis.getNombreEmpresa();
                    String strNomEmp = GLO_strnomEmp.substring(0,3);
                    JasperDesign jasperDesign  = JRXmlLoader.load(strDirRep);
                    JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);


                   if(System.getProperty("os.name").equals("Linux")){
                       javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                       objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                       JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReport, parameters,  con);
                       javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpresionDirectaRetencion , null);//me la da marcelo
                       javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                       printServiceAttributeSet.add(printerName);
                       net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                       objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                       objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                       objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                       objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                       objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                       objJRPSerExp.exportReport();

                    }else{
                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                        //JasperPrintManager.printReport(report,false);
                        JasperViewer.viewReport(report, false);
                    }

                    
                    con.commit();
                    stm.close();
                    stm=null;
                    con.close();
                    con=null;



                }
            }


        }
        catch (JRException e){
            objUti.mostrarMsgErr_F1(this, e);
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









    private boolean imprimirRetencionesFaltantes(){
        boolean blnRes=true;
        int intUltDoc=0;
        String strGloCabDia="";
        String strSQLUpdGrl="";
        String strLin="";
        String strSQLRep="", strSQLSubRep="";
        try{

            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(false);
            strDirRep=objRptSis.getRutaReporte(0) + "ZafRptCon05.jrxml";


            for(int i=0; i<objTblModImp.getRowCountTrue(); i++){
                strLin=objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_LIN)==null?"":objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_LIN).toString();
                if(strLin.equals("M")){
                    if(objTblModImp.isChecked(i, INT_TBL_DAT_IMP_CHK)){
                        con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        con.setAutoCommit(false);
                        if(con!=null){
                            stm=con.createStatement();
                            if(chkNotIncNumDoc.isSelected()){
                                //en tbm_cabPag.ne_numDoc1 y tbm_cabPag.ne_numDoc2
                                strSQL="";
                                strSQL+="UPDATE tbm_cabPag";
                                strSQL+=" SET st_imp='S'";
                                strSQL+=" WHERE co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                strSQL+=" AND co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                                strSQL+=";";
                                strSQLUpdGrl+=strSQL;

                                //en tbm_cabDia.tx_numDia
                                strSQL="";
                                strSQL+="UPDATE tbm_cabDia";
                                strSQL+=" SET st_imp='S'";
                                strSQL+=" WHERE co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                strSQL+=" AND co_dia=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                                strSQL+=";";
                                strSQLUpdGrl+=strSQL;
                                stm.executeUpdate(strSQLUpdGrl);
                                strSQLUpdGrl="";
                            }
                            else{
                                strGloCabDia="";
                                //para obtener tbm_cabTipDoc.ne_ultDoc
                                strSQL="";
                                strSQL+="SELECT MAX(a1.ne_ultDoc) AS ne_ultDoc";
                                strSQL+=" FROM tbm_cabTipDoc AS a1";
                                strSQL+=" WHERE a1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP);
                                strSQL+=" AND a1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC);
                                strSQL+=" AND a1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                rst=stm.executeQuery(strSQL);
                                if (rst.next()){
                                    intUltDoc=(rst.getInt("ne_ultDoc")+1);
                                }

                                //para obtener la glosa
                                strGloCabDia+=txtDesCorTipDoc.getText() + ": " + intUltDoc + "";
                                strGloCabDia+=";  Beneficiario: " + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_NOM_BEN) + "";
                                strGloCabDia+=";  Documentos: " + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_DOC_CAN) + "";
                                //se actualizan los campos en cada tabla
                                //en tbm_cabTipDoc.ne_ultDoc
                                strSQL="";
                                strSQL+="UPDATE tbm_cabTipDoc";
                                strSQL+=" SET ne_ultDoc=" + intUltDoc + "";
                                strSQL+=" WHERE co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                strSQL+=";";
                                strSQLUpdGrl+=strSQL;

                                //en tbm_cabPag.ne_numDoc1 y tbm_cabPag.ne_numDoc2
                                strSQL="";
                                strSQL+="UPDATE tbm_cabPag";
                                strSQL+=" SET ne_numDoc1=" + intUltDoc + "";
                                strSQL+=" , ne_numDoc2=" + intUltDoc + "";
                                strSQL+=" , st_imp='S'";
                                strSQL+=" WHERE co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                strSQL+=" AND co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                                strSQL+=";";
                                strSQLUpdGrl+=strSQL;

                                //en tbm_cabDia.tx_numDia
                                strSQL="";
                                strSQL+="UPDATE tbm_cabDia";
                                strSQL+=" SET tx_numDia=" + intUltDoc + "";
                                strSQL+=" , tx_glo='" + strGloCabDia + "'";
                                strSQL+=" , st_imp='S'";
                                strSQL+=" WHERE co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                                strSQL+=" AND co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                                strSQL+=" AND co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                                strSQL+=" AND co_dia=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                                strSQL+=";";
                                strSQLUpdGrl+=strSQL;


                                stm.executeUpdate(strSQLUpdGrl);
                                strSQLUpdGrl="";


                            }

                            String strNumDocPgd="";
                            strSQL="";
                            strSQL+="SELECT a3.ne_numdoc FROM (";
                            strSQL+=" tbm_cabPag AS b1";
                            strSQL+=" INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
                            strSQL+=" INNER JOIN tbm_detPag AS a1";
                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
                            strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
                            strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                            strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ";
                            strSQL+=" ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                            strSQL+=" WHERE a1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP);
                            strSQL+=" AND a1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC);
                            strSQL+=" AND a1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                            strSQL+=" AND a1.co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                            strSQL+=" GROUP BY a3.ne_numdoc";
                            strSQL+=" ORDER BY a3.ne_numdoc";
                            System.out.println("docPgdos: " + strSQL);
                            rst=stm.executeQuery(strSQL);
                            while (rst.next()){
                                strNumDocPgd=rst.getString("ne_numdoc") + ",";
                            }



//                            strSQL="";
//                            strSQL+=" SELECT b1.tx_nomCli, b1.tx_ruc";
//                            strSQL+=" , extract(day from b1.fe_doc)  ||'-'||";
//                            strSQL+=" 	case extract(month from b1.fe_doc)";
//                            strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
//                            strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
//                            strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
//                            strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
//                            strSQL+=" , b1.tx_dirCli, cast( '" + strNumDocPgd + "' as character varying) AS ne_numDoc1, a2.tx_numAutSRI, a2.tx_fecCad";
//                            strSQL+=" , (a2.tx_numSer  || '-' || a2.tx_numChq ) as comprobante, b1.tx_obs2, CAST(extract(year from b1.fe_doc) AS Integer) as aniodoc";
//                            strSQL+=" FROM tbm_cabPag AS b1 INNER JOIN tbm_detPag AS a1";
//                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
//                            strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
//                            strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
//                            strSQL+=" WHERE a1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
//                            strSQL+=" AND a1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
//                            strSQL+=" AND a1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
//                            strSQL+=" AND a1.co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
//                            strSQL+=" GROUP BY b1.tx_nomCli, b1.tx_ruc, b1.fe_doc, b1.tx_dirCli";
//                            strSQL+=" , a2.tx_numAutSRI, a2.tx_fecCad,a2.tx_numSer, a2.tx_numChq, b1.tx_obs2, b1.fe_doc";
//                            strSQLRep=strSQL;
//                            System.out.println("strSQLRep: " + strSQLRep);
//
//
//                            strSQL="";
//                            strSQL+="SELECT CAST(extract(year from b1.fe_doc) AS Integer) as aniodoc, SUM(a2.nd_basImp) AS nd_basImp";
//                            strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
//                            strSQL+=" 	when round(a2.nd_porret) in(30,70,100) then '    I.V.A.' end AS Impuesto";
//                            strSQL+=" , round(a2.nd_porret) AS nd_porret";
//                            strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as comprobante, a2.tx_codSRI";
//                            strSQL+=" FROM (";
//                            strSQL+=" 	tbm_cabPag AS b1";
//                            strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
//                            strSQL+=" INNER JOIN tbm_detPag AS a1";
//                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
//                            strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
//                            strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
//                            strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
//                            strSQL+=" WHERE a1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
//                            strSQL+=" AND a1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
//                            strSQL+=" AND a1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
//                            strSQL+=" AND a1.co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
//                            strSQL+=" GROUP BY b1.fe_doc";
//                            strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
//                            strSQL+=" ORDER BY a2.nd_porret";
//                            strSQLSubRep=strSQL;
//                            System.out.println("strSQLSubRep: " + strSQLSubRep);


                            strSQL="";
                            strSQL+="SELECT a1.tx_ruc AS tx_rucEmp, a1.tx_nom AS tx_nomEmp, a1.tx_dir AS tx_dirEmp, a1.tx_tel AS tx_telEmp,";
                            strSQL+="a1.tx_fax AS tx_faxEmp, a1.tx_dirWeb AS tx_dirWebEmp, a1.tx_corEle AS tx_corEleEmp, a1.tx_desConEsp AS tx_desConEspEmp,";
                            strSQL+="a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                            strSQL+="b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                            strSQL+=", b1.ne_numDoc1, lpad(''|| b1.ne_numDoc1,7,'0') AS ne_numDocFor";
                            strSQL+=", b1.fe_doc,";
                            strSQL+=" extract(day from b1.fe_doc)  ||'-'||";
                            strSQL+=" 	case extract(month from b1.fe_doc)";
                            strSQL+=" 	when 1 then 'Ene' when 2 then 'Feb' when 3 then 'Mar' when 4 then 'Abr'";
                            strSQL+=" 	when 5 then 'May' when 6 then 'Jun' when 7 then 'Jul' when 8 then 'Ago'";
                            strSQL+=" 	when 9 then 'Sep' when 10 then 'Oct' when 11 then 'Nov' when 12 then 'Dic'";
                            strSQL+=" 	end ||'-'||extract(year from b1.fe_doc) as FecReg";
                            //--cliente
                            strSQL+=", b1.co_cli, b1.tx_ruc AS tx_rucCli, b1.tx_nomCli, b1.tx_dirCli";
                            strSQL+=", b1.tx_obs1, b1.tx_obs2";
                            strSQL+=", cast( '" + strNumDocPgd + "' as character varying) AS ne_numOrdComCan";
                            strSQL+=", (a6.tx_numSer  || '-' || a6.tx_numChq ) as ne_numCmpVta";
                            strSQL+=", CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis";
                            strSQL+=" , a6.tx_fecCad AS tx_fecCadCli, a6.tx_numAutSRI AS tx_numAutSRICli, ABS(b1.nd_monDoc) AS ne_valRet";
                            strSQL+=" FROM (tbm_cabPag AS b1 INNER JOIN tbm_emp AS a1 ON(b1.co_emp=a1.co_emp)";
                            strSQL+=" 	INNER JOIN tbm_loc AS a5 ON (b1.co_emp=a5.co_emp AND b1.co_loc=a5.co_loc)";
                            strSQL+="	INNER JOIN tbm_datAutSri AS a4 ON (b1.co_emp=a4.co_emp AND b1.co_loc=a4.co_loc AND b1.co_tipDoc=a4.co_tipDoc)";
                            strSQL+="	INNER JOIN tbm_cabTipDoc AS a2 ON (b1.co_emp=a2.co_emp AND b1.co_loc=a2.co_loc AND b1.co_tipDoc=a2.co_tipDoc)";
                            strSQL+=" )";
                            strSQL+=" INNER JOIN tbm_detPag AS b2";
                            strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                            strSQL+=" INNER JOIN tbm_pagMovInv AS a6";
                            strSQL+=" ON b2.co_emp=a6.co_emp AND b2.co_locPag=a6.co_loc AND b2.co_tipDocPag=a6.co_tipDoc AND b2.co_docPag=a6.co_doc AND b2.co_regPag=a6.co_reg";
                            strSQL+=" INNER JOIN tbm_cabMovInv AS a3";
                            strSQL+=" ON a6.co_emp=a3.co_emp AND a6.co_loc=a3.co_loc AND a6.co_tipDoc=a3.co_tipDoc AND a6.co_doc=a3.co_doc";
                            strSQL+=" WHERE b1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                            strSQL+=" AND b1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                            strSQL+=" AND b1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                            strSQL+=" AND b1.co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                            strSQL+=" AND b1.ne_numDoc1>=a4.ne_numDocDes AND b1.ne_numDoc1<=a4.ne_numDocHas";
                            strSQL+=" GROUP BY a1.tx_ruc, a1.tx_nom, a1.tx_dir, a1.tx_tel,";
                            strSQL+=" a1.tx_fax, a1.tx_dirWeb, a1.tx_corEle, a1.tx_desConEsp,";
                            strSQL+=" a4.tx_numSerFac, a4.tx_numAutSri, a4.tx_fecCadFac, ";
                            strSQL+=" b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc1";
                            strSQL+=" , b1.fe_doc, b1.co_cli, b1.tx_ruc,b1.tx_nomCli, b1.tx_dirCli";
                            strSQL+=" , b1.tx_obs1, b1.tx_obs2, a6.tx_numSer, a6.tx_numChq";
                            strSQL+=" , a6.tx_fecCad, a6.tx_numAutSRI, b1.nd_monDoc";
                            strSQLRep=strSQL;
                            System.out.println("strSQLRep: " + strSQLRep);


                            strSQL="";
                            strSQL+="SELECT CAST(extract(year from b1.fe_doc) AS Integer) as ne_aniEjeFis, SUM(a2.nd_basImp) AS nd_basImp";
                            strSQL+=" , case when round(a2.nd_porret) in(0,1,2,5,8,10) then 'IMPUESTO A LA RENTA'";
                            strSQL+=" 	when round(a2.nd_porret) in(30,70,100) then '    I.V.A.' end AS Impuesto";
                            strSQL+=" , round(a2.nd_porret) AS nd_porret";
                            strSQL+=" , SUM(ABS(a2.nd_abo)) AS nd_abo, (a2.tx_numSer  || '-' || a2.tx_numChq ) as ne_numCmpVta, a2.tx_codSRI";
                            strSQL+=" FROM (";
                            strSQL+=" 	tbm_cabPag AS b1";
                            strSQL+="	INNER JOIN tbm_emp AS b2 ON b1.co_emp=b2.co_emp)";
                            strSQL+=" INNER JOIN tbm_detPag AS a1";
                            strSQL+=" ON b1.co_emp=a1.co_emp AND b1.co_loc=a1.co_loc AND b1.co_tipDoc=a1.co_tipDoc AND b1.co_doc=a1.co_doc";
                            strSQL+=" LEFT OUTER JOIN tbm_pagMovInv AS a2";
                            strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg)";
                            strSQL+=" LEFT OUTER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc)";
                            strSQL+=" WHERE a1.co_emp=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP) + "";
                            strSQL+=" AND a1.co_loc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC) + "";
                            strSQL+=" AND a1.co_tipDoc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC) + "";
                            strSQL+=" AND a1.co_doc=" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC) + "";
                            strSQL+=" GROUP BY b1.fe_doc";
                            strSQL+=" , a2.nd_porret, a2.tx_numSer, a2.tx_numChq, a2.tx_codSRI";
                            strSQL+=" ORDER BY a2.nd_porret";
                            strSQLSubRep=strSQL;
                            System.out.println("strSQLSubRep: " + strSQLSubRep);
                            
                            
                            //para la impresión
                            Map parameters = new HashMap();
                            parameters.put("codEmp",    new Integer(objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_EMP).toString()));
                            parameters.put("codLoc",    new Integer(objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_LOC).toString()));
                            parameters.put("codTipDoc", new Integer(objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_TIP_DOC).toString()));
                            parameters.put("codDoc",    new Integer(objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_COD_DOC).toString()));
                            //parameters.put("Numdoc",    "" + objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_DOC_CAN));
                            parameters.put("strSQLRep", strSQLRep);
                            parameters.put("strSQLSubRep", strSQLSubRep);
                            parameters.put("SUBREPORT_DIR", objRptSis.getRutaReporte(0));

                            JasperDesign jasperDesign  = JRXmlLoader.load(strDirRep);
                            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                            //mostrarMsgInf("Coloque la hoja para impresión y de click en OK");

                           if(System.getProperty("os.name").equals("Linux")){
                               javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                               objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                               JasperPrint reportGuiaRem =JasperFillManager.fillReport(jasperReport, parameters,  con);
                               javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strImpresionDirectaRetencion , null);//me la da marcelo
                               javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                               printServiceAttributeSet.add(printerName);
                               net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                               objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                               objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                               objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                               objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                               objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                               objJRPSerExp.exportReport();

                            }
                           else{
                                JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, con);
                                JasperPrintManager.printReport(report,false);
                                //JasperViewer.viewReport(report, false);
                            }
                          con.commit();
                          stm.close();
                          stm=null;
                          con.close();
                          con=null;
                        }
                    }
                }




            }


        }
        catch (JRException e){
            objUti.mostrarMsgErr_F1(this, e);
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
    
    
    
    
    private boolean configurarTblDatAsiDia(){
        boolean blnRes=true;
        try{
            //Configurar JTable: Establecer el modelo.
            vecDatAsiDia=new Vector();    //Almacena los datos
            vecCabAsiDia=new Vector(9);  //Almacena las cabeceras
            vecCabAsiDia.clear();
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_LIN,"");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_EMP,"Cód. Emp.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_LOC,"Cód.Loc.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_TIP_DOC,"Cód. Tip.Doc.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_DOC,"Cód.Doc.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_REG,"Cód.Reg.");
            
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_PRV,"Cód.Prv.");

            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_TIP_RET,"Cód.Tip.Ret.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_VAL_RET,"Val.Ret.");
            vecCabAsiDia.add(INT_TBL_DAT_ASI_DIA_CHK,"");
            
            
            objTblModAsiDia=new ZafTblMod();
            objTblModAsiDia.setHeader(vecCabAsiDia);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatAsiDia.setModel(objTblModAsiDia);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDatAsiDia.setRowSelectionAllowed(true);
            tblDatAsiDia.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatAsiDia.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatAsiDia.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_EMP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_LOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_PRV).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_COD_TIP_RET).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_VAL_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_ASI_DIA_CHK).setPreferredWidth(30);
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    protected boolean cargarDatosRetenerAsiDia(int codigoEmpresa, int codigoLocal, int tipoDocumento, String codigoDiario, String codigoProveedor){
        boolean blnRes=true;
        int intCodEmpCab =codigoEmpresa;
        int intCodLocCab=codigoLocal;
        int intTipDocCab=tipoDocumento;
        String strCodDiaCab=codigoDiario;
        String strCodProCab=codigoProveedor;
        
        int intCodEmpDet;
        int intCodLocDet;
        int intTipDocDet;
        String strCodDiaDet="";
        
        int intCodEmpDetPag;
        int intCodLocDetPag;
        int intTipDocDetPag;
        String strCodDiaDetPag="";
        String strCodRegDetPag="";
        
        
        try{
            for(int d=0; d<arlDatDetPag.size();d++ ){
                intCodEmpDet=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_EMP);
                intCodLocDet=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_LOC);
                intTipDocDet=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_TIP_DOC);
                strCodDiaDet=objUti.getStringValueAt(arlDatDetPag, d, INT_ARL_DET_COD_DOC);
                
                intCodEmpDetPag=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_EMP);
                intCodLocDetPag=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_LOC_PAG);
                intTipDocDetPag=objUti.getIntValueAt(arlDatDetPag, d, INT_ARL_DET_COD_TIP_DOC_PAG);
                strCodDiaDetPag=objUti.getStringValueAt(arlDatDetPag, d, INT_ARL_DET_COD_DOC_PAG);
                strCodRegDetPag=objUti.getStringValueAt(arlDatDetPag, d, INT_ARL_DET_COD_REG_PAG);

                
                if(intCodEmpCab==intCodEmpDet){
                    if(intCodLocCab==intCodLocDet){
                        if(intTipDocCab==intTipDocDet){
                            if(strCodDiaCab.equals(strCodDiaDet)){
                                vecRegAsiDia=new Vector();
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_LIN, "");
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_EMP,         "" + intCodEmpDet);
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_LOC,         "" + intCodLocDet);
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_TIP_DOC,     "" + intTipDocDet);
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_DOC,         "" + strCodDiaDet);
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_REG,         "" + objUti.getStringValueAt(arlDatDetPag, d, INT_ARL_DET_COD_REG));
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_PRV,         "" + strCodProCab);
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_COD_TIP_RET, "" + getCodigoTipoRetencion(con, intCodEmpDetPag, intCodLocDetPag, intTipDocDetPag, strCodDiaDetPag, strCodRegDetPag ));
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_VAL_RET,         "" + objUti.getStringValueAt(arlDatDetPag, d, INT_ARL_DET_VAL_ABO));
                                vecRegAsiDia.add(INT_TBL_DAT_ASI_DIA_CHK,         "");
                                vecRegAsiDia.setElementAt(new Boolean(true), INT_TBL_DAT_ASI_DIA_CHK);
                                vecDatAsiDia.add(vecRegAsiDia);
                            }
                        }
                    }
                }
            }
            //Asignar vectores al modelo.
            objTblModAsiDia.setData(vecDatAsiDia);
            tblDatAsiDia.setModel(objTblModAsiDia);
            vecDatAsiDia.clear();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    private int getCodigoTipoRetencion(Connection conTipRet, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, String codigoDiario, String codigoRegistro){
        int intCodTipRet=0;
        Statement stmTipRet;
        ResultSet rstTipRet;
        String strSQLTipRet="";
        try{
            if(conTipRet!=null){
                stmTipRet=conTipRet.createStatement();
                strSQLTipRet="";
                strSQLTipRet+="SELECT a2.co_tipRet";
                strSQLTipRet+=" FROM tbm_pagMovInv AS a1 INNER JOIN tbm_cabTipRet AS a2";
                strSQLTipRet+=" ON a1.co_emp=a2.co_emp AND a1.co_tipRet=a2.co_tipRet";
                strSQLTipRet+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQLTipRet+=" AND a1.co_loc=" + codigoLocal + "";
                strSQLTipRet+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strSQLTipRet+=" AND a1.co_doc=" + codigoDiario + "";
                strSQLTipRet+=" AND a1.co_reg=" + codigoRegistro + "";
                rstTipRet=stmTipRet.executeQuery(strSQLTipRet);
                while(rstTipRet.next()){
                    intCodTipRet=rstTipRet.getInt("co_tipRet");
                }
                stmTipRet.close();
                stmTipRet=null;
                rstTipRet.close();
                rstTipRet=null;
                
                
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodTipRet;
    }
    
    
    private boolean cargarRetencionesSinImprimir(){
        boolean blnRes=true;
        int intNumTotReg;
        int i;
        try{
            butConRetImp.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="select a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1, a1.st_imp ";
                strSQL+=", a2.tx_desCor, a1.co_cli, a1.tx_nomcli, a1.nd_mondoc";
                strSQL+=" from tbm_cabpag as a1 inner join tbm_cabTipDoc as a2";
                strSQL+=" on a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc";
                strSQL+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" and a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" and a1.st_imp='N'";
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;

                strSQL="";
                strSQL+="select a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc1, a1.st_imp ";
                strSQL+=", a2.tx_desCor, a1.co_cli, a1.tx_nomcli, a1.nd_mondoc";
                strSQL+=" from tbm_cabpag as a1 inner join tbm_cabTipDoc as a2";
                strSQL+=" on a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc";
                strSQL+=" where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" and a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" and a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" and a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" and a1.st_imp='N' ORDER BY a1.ne_numDoc1, a1.co_doc";
                System.out.println("cargarRetencionesSinImprimir: " + strSQL);
                rst=stm.executeQuery(strSQL);
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                int intCodEmpImp, intCodLocImp, intCodTipDocImp, intCodDocImp;
                while(rst.next()){
                    if (blnConImp){
                        intCodEmpImp=rst.getInt("co_emp");
                        intCodLocImp=rst.getInt("co_loc");
                        intCodTipDocImp=rst.getInt("co_tipDoc");
                        intCodDocImp=rst.getInt("co_doc");

                        vecRegImp=new Vector();
                        vecRegImp.add(INT_TBL_DAT_IMP_LIN, "");
                        vecRegImp.add(INT_TBL_DAT_IMP_CHK, "");
                        vecRegImp.add(INT_TBL_DAT_IMP_COD_EMP,          "" + intCodEmpImp);
                        vecRegImp.add(INT_TBL_DAT_IMP_COD_LOC,          "" + intCodLocImp);
                        vecRegImp.add(INT_TBL_DAT_IMP_COD_TIP_DOC,      "" + intCodTipDocImp);
                        vecRegImp.add(INT_TBL_DAT_IMP_COD_DOC,          "" + intCodDocImp);
                        vecRegImp.add(INT_TBL_DAT_IMP_DES_COR_TIP_DOC,  "" + rst.getString("tx_desCor"));
                        vecRegImp.add(INT_TBL_DAT_IMP_COD_CLI,          "" + rst.getString("co_cli"));
                        vecRegImp.add(INT_TBL_DAT_IMP_NOM_BEN,          "" + rst.getString("tx_nomcli"));
                        vecRegImp.add(INT_TBL_DAT_IMP_VAL_RET,          "" + rst.getString("nd_mondoc"));
                        vecRegImp.add(INT_TBL_DAT_IMP_DOC_CAN,          "" + obtieneDocCancelados(con, intCodEmpImp, intCodLocImp, intCodTipDocImp, intCodDocImp));

                        vecDatImp.add(vecRegImp);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else
                    {
                        break;
                    }

                }
                blnMarTodChkTblImp=false;

                //Asignar vectores al modelo.
                objTblModImp.setData(vecDatImp);
                tblDatRetFalImp.setModel(objTblModImp);
                vecDatImp.clear();
                pgrSis.setValue(0);
                butConRetImp.setText("Consultar Retenciones");

                if (blnConImp)
                    lblMsgSis.setText("Se encontraron " + tblDatRetFalImp.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatRetFalImp.getRowCount() + " registros.");
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
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private String obtieneDocCancelados(Connection conexion, int codigoEmpresa, int codigoLocal, int codigoTipoDocumento, int codigoDocumento){
        String strDocCan="";
        Statement stmDocCan;
        ResultSet rstDocCan;
        String strSQLTmp="";
        int intConDocCan=0;
        try{
            if(conexion!=null){
                stmDocCan=conexion.createStatement();
                strSQLTmp="";
                strSQLTmp+=" SELECT a3.ne_numDoc";
                strSQLTmp+=" FROM tbm_detPag AS a1 INNER JOIN tbm_pagMovInv AS a2";
                strSQLTmp+=" ON a1.co_emp=a2.co_emp AND a1.co_locPag=a2.co_loc AND a1.co_tipDocPag=a2.co_tipDoc ";
                strSQLTmp+=" AND a1.co_docPag=a2.co_doc AND a1.co_regPag=a2.co_reg";
                strSQLTmp+=" INNER JOIN tbm_cabMovInv AS a3";
                strSQLTmp+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQLTmp+=" WHERE a1.co_emp=" + codigoEmpresa + "";
                strSQLTmp+=" AND a1.co_loc=" + codigoLocal + "";
                strSQLTmp+=" AND a1.co_tipDoc=" + codigoTipoDocumento + "";
                strSQLTmp+=" AND a1.co_doc=" + codigoDocumento + "";
                strSQLTmp+=" GROUP BY a3.ne_numDoc";
                strSQLTmp+=" ORDER BY a3.ne_numDoc";
                rstDocCan=stmDocCan.executeQuery(strSQLTmp);
                while(rstDocCan.next()){
                    if(intConDocCan==0){
                        strDocCan="" + rstDocCan.getString("ne_numDoc");
                        intConDocCan++;
                    }
                    else{
                        strDocCan+=", " + rstDocCan.getString("ne_numDoc");
                    }
                }
                stmDocCan.close();
                stmDocCan=null;
                rstDocCan.close();
                rstDocCan=null;
            }

        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strDocCan;
    }

    private boolean isCamVal(){
        objTblModImp.removeEmptyRows();
//        String strLin="";
        int intConCamRea=0;
        for(int i=0; i<objTblModImp.getRowCountTrue(); i++){
            if(objTblModImp.isChecked(i, INT_TBL_DAT_IMP_CHK)){
                intConCamRea++;
            }
        }
        if(intConCamRea>0){
        }
        else{
            mostrarMsgInf("<HTML>No se ha seleccionado documento<BR>Seleccione algún documento y vuelva a intentarlo.</HTML>");
            tblDatRetFalImp.setRowSelectionInterval(0, 0);
            tblDatRetFalImp.changeSelection(0, INT_TBL_DAT_IMP_NOM_BEN, true, true);
            tblDatRetFalImp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean eliminaTablaRegistrosSeleccionados(){
        boolean blnRes=true;
        String strLin="";
        try{
            objTblModImp.setModoOperacion(objTblModImp.INT_TBL_INS);
            for(int i=(objTblModImp.getRowCountTrue()-1); i>=0; i--){
                strLin=objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_LIN)==null?"":objTblModImp.getValueAt(i, INT_TBL_DAT_IMP_LIN).toString();
                if(strLin.equals("M")){
                    if(objTblModImp.isChecked(i, INT_TBL_DAT_IMP_CHK)){
                        objTblModImp.removeRows(i, i);
                    }
                }
            }
            objTblModImp.setModoOperacion(objTblModImp.INT_TBL_EDI);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        int intColSel=tblDatRetFalImp.getSelectedColumn();
        try
        {
            intNumFil=objTblModImp.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDatRetFalImp.columnAtPoint(evt.getPoint())==intColSel)
            {
                if (blnMarTodChkTblImp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModImp.setChecked(true, i, intColSel);
                    }
                    blnMarTodChkTblImp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModImp.setChecked(false, i, intColSel);
                    }
                    blnMarTodChkTblImp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }




}