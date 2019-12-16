/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom42;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCom42 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    //PASO 1
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_COD_TIP_DOC=3;
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    final int INT_TBL_DAT_COD_DOC=6;
    final int INT_TBL_DAT_COD_MNU_DOC=7;
    final int INT_TBL_DAT_NUM_DOC=8;
    final int INT_TBL_DAT_FEC_DOC=9;
    final int INT_TBL_DAT_COD_CLI=10;
    final int INT_TBL_DAT_NOM_CLI=11;
    final int INT_TBL_DAT_COD_USU_SOL=12;
    final int INT_TBL_DAT_NOM_USU_SOL=13;
    
    //PASO 2
    final int INT_TBL_DAT_CHK_AUT=14;
    final int INT_TBL_DAT_CHK_DEN=15;
    final int INT_TBL_DAT_CHK_CAN=16;
    final int INT_TBL_DAT_FEC_REV=17;
    final int INT_TBL_DAT_OBS_REV=18;
    final int INT_TBL_DAT_BUT_OBS_REV=19;
    
    //PASO 3
    final int INT_TBL_DAT_TSF_EGR=20;
    final int INT_TBL_DAT_CNF_EGR=21;
    final int INT_TBL_DAT_MER_NOT_EGR=22;
    
    //PASO 4
    final int INT_TBL_DAT_TSF_ING=23;
    final int INT_TBL_DAT_CNF_ING=24;
    final int INT_TBL_DAT_MER_NOT_ING=25;

    //PASO 5
    final int INT_TBL_DAT_FAC_MER_SAL_DEM=26;
    final int INT_TBL_DAT_MER_FAC=27;
    
    //PASO 6
    final int INT_TBL_DAT_PRO_COM=28;
    
    
    
    
    //Variables generales.
    private String strFecDocIni;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafVenCon vcoTipDoc, vcoLoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCli, vcoVen;                           //Ventana de consulta "Proveedor".
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strNumDoc1;              //Contenido del campo al obtener el foco.
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direcci�n del Beneficiario.
    private ZafTblBus objTblBus;
    
    
    private String strCodVen,strDesLarVen;
    
    //para el paso 2
    private ZafTblCelRenChk objTblCelRenChkAut, objTblCelRenChkDen, objTblCelRenChkCan;
    private ZafTblCelEdiChk objTblCelEdiChkAut, objTblCelEdiChkDen, objTblCelEdiChkCan;
    
    //para el paso 3
    private ZafTblCelRenChk objTblCelRenChkTraEgr, objTblCelRenChkCnfEgr, objTblCelRenChkMerNotEgr;
    private ZafTblCelEdiChk objTblCelEdiChkTraEgr, objTblCelEdiChkCnfEgr, objTblCelEdiChkMerNotEgr;
    
    //para el paso 4
    private ZafTblCelRenChk objTblCelRenChkTraIng, objTblCelRenChkCnfIng, objTblCelRenChkMerNotIng;
    private ZafTblCelEdiChk objTblCelEdiChkTraIng, objTblCelEdiChkCnfIng, objTblCelEdiChkMerNotIng;
    
    //para el paso 5
    private ZafTblCelRenChk objTblCelRenChkFacMerDem,objTblCelRenChkMerFac;
    private ZafTblCelEdiChk objTblCelEdiChkFacMerDem,objTblCelEdiChkMerFac;
            
    //para el paso 6        
    private ZafTblCelRenChk objTblCelRenChkProCom;
    private ZafTblCelEdiChk objTblCelEdiChkProCom;
    
    
    private ZafThreadGUI objThrGUI;
    private ArrayList arlReg, arlDat;
    
    private final int INT_ARL_COD_EMP=0;
    private final int INT_ARL_COD_LOC=1;
    private final int INT_ARL_COD_TIP_DOC=2;
    private final int INT_ARL_COD_DOC=3;
    private final int INT_ARL_COD_REG=4;
    private ZafSelFec objSelFec;
    
    private String strCodLoc, strDesLarLoc;
    
    private ZafTblCelRenBut objTblCelRenButObs;
    private ZafTblCelEdiButDlg objTblCelEdiButObs;
    
    private ZafCom42_01 objCom42_01;
    private ZafTblCelRenLbl objTblCelRenLbl;
            
            
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom42(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
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
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        txtDesCorVen = new javax.swing.JTextField();
        txtDesLarVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtCodVen = new javax.swing.JTextField();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtDesLarLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblEstAut = new javax.swing.JLabel();
        lblVen2 = new javax.swing.JLabel();
        cboEstAut = new javax.swing.JComboBox();
        lblPas = new javax.swing.JLabel();
        optRea = new javax.swing.JRadioButton();
        optPen = new javax.swing.JRadioButton();
        cboPas = new javax.swing.JComboBox();
        panFilFec = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 82));
        panGenCab.setLayout(null);

        lblPrv.setText("Cliente:");
        lblPrv.setToolTipText("Proveedor");
        panGenCab.add(lblPrv);
        lblPrv.setBounds(22, 80, 70, 20);

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
        panGenCab.add(txtCodPrv);
        txtCodPrv.setBounds(150, 80, 70, 20);

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
        panGenCab.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(220, 80, 410, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenCab.add(butPrv);
        butPrv.setBounds(630, 80, 20, 20);

        txtDesCorVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorVenActionPerformed(evt);
            }
        });
        txtDesCorVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorVenFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCorVen);
        txtDesCorVen.setBounds(150, 100, 70, 20);

        txtDesLarVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarVenActionPerformed(evt);
            }
        });
        txtDesLarVen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarVenFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarVenFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarVen);
        txtDesLarVen.setBounds(220, 100, 410, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenCab.add(butVen);
        butVen.setBounds(630, 100, 20, 20);
        panGenCab.add(txtCodVen);
        txtCodVen.setBounds(117, 100, 32, 20);

        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGenCab.add(optTod);
        optTod.setBounds(0, 0, 440, 18);

        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panGenCab.add(optFil);
        optFil.setBounds(0, 18, 440, 18);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Proveedor");
        panGenCab.add(lblLoc);
        lblLoc.setBounds(22, 40, 70, 20);

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
        panGenCab.add(txtCodLoc);
        txtCodLoc.setBounds(150, 40, 70, 20);

        txtDesLarLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarLocActionPerformed(evt);
            }
        });
        txtDesLarLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarLocFocusLost(evt);
            }
        });
        panGenCab.add(txtDesLarLoc);
        txtDesLarLoc.setBounds(220, 40, 410, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panGenCab.add(butLoc);
        butLoc.setBounds(630, 40, 20, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(22, 60, 116, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(117, 60, 32, 20);

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
        panGenCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 60, 70, 20);

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
        panGenCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(220, 60, 410, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(630, 60, 20, 20);

        lblEstAut.setText("Estado de autorización:");
        lblEstAut.setToolTipText("Vendedor");
        panGenCab.add(lblEstAut);
        lblEstAut.setBounds(22, 120, 127, 20);

        lblVen2.setText("Solicitante:");
        lblVen2.setToolTipText("Vendedor");
        panGenCab.add(lblVen2);
        lblVen2.setBounds(22, 100, 70, 20);

        panGenCab.add(cboEstAut);
        cboEstAut.setBounds(150, 120, 500, 20);

        lblPas.setText("Paso:");
        lblPas.setToolTipText("Vendedor");
        panGenCab.add(lblPas);
        lblPas.setBounds(22, 140, 70, 20);

        optRea.setText("Realizado");
        optRea.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optReaActionPerformed(evt);
            }
        });
        panGenCab.add(optRea);
        optRea.setBounds(236, 140, 90, 23);

        optPen.setSelected(true);
        optPen.setText("Pendiente");
        optPen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optPenActionPerformed(evt);
            }
        });
        panGenCab.add(optPen);
        optPen.setBounds(140, 140, 90, 23);

        cboPas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Paso 1:   Esperando Autorización", "Paso 2:   Solicitud revisada", "Paso 3:   Egreso de Bodega", "Paso 4:   Ingreso a Bodega", "Paso 5:   Facturar", "Paso 6:   Proceso Completo" }));
        panGenCab.add(cboPas);
        cboPas.setBounds(330, 140, 320, 20);

        panFil.add(panGenCab, java.awt.BorderLayout.CENTER);

        panFilFec.setPreferredSize(new java.awt.Dimension(100, 80));
        panFilFec.setLayout(null);
        panFil.add(panFilFec, java.awt.BorderLayout.NORTH);

        tabFrm.addTab("General", panFil);

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panGenDet);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 50));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 2));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setMnemonic('r');
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
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicaci�n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="Está seguro que desea cerrar este programa?";
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

private void butVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butVenActionPerformed
// TODO add your handling code here:
        strCodVen=txtDesCorVen.getText();
        mostrarVenConVen(0);
}//GEN-LAST:event_butVenActionPerformed

private void txtDesLarVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVenFocusLost
// TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarVen.getText().equalsIgnoreCase(strDesLarVen)){
            if (txtDesLarVen.getText().equals("")){
                txtDesCorVen.setText("");
                txtDesLarVen.setText("");
            }
            else
            {
                mostrarVenConVen(2);
            }
        }
        else
            txtDesLarVen.setText(strDesLarVen);
}//GEN-LAST:event_txtDesLarVenFocusLost

private void txtDesLarVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarVenFocusGained
// TODO add your handling code here:
        strDesLarVen=txtDesLarVen.getText();
        txtDesLarVen.selectAll();
}//GEN-LAST:event_txtDesLarVenFocusGained

private void txtDesLarVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarVenActionPerformed
// TODO add your handling code here:
        txtDesLarVen.transferFocus();
}//GEN-LAST:event_txtDesLarVenActionPerformed

private void txtDesCorVenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorVenFocusLost
// TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesCorVen.getText().equalsIgnoreCase(strCodVen)){
            if (txtDesCorVen.getText().equals("")){
                txtDesCorVen.setText("");
                txtDesLarVen.setText("");
            }
            else
                mostrarVenConVen(1);
        }
        else
            txtDesCorVen.setText(strCodVen);
}//GEN-LAST:event_txtDesCorVenFocusLost

private void txtDesCorVenFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorVenFocusGained
// TODO add your handling code here:
        strCodVen=txtDesCorVen.getText();
        txtDesCorVen.selectAll();
}//GEN-LAST:event_txtDesCorVenFocusGained

private void txtDesCorVenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorVenActionPerformed
// TODO add your handling code here:
        txtDesCorVen.transferFocus();
}//GEN-LAST:event_txtDesCorVenActionPerformed

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
    strCodPrv=txtCodPrv.getText();
    mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtDesLarPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConPrv(2);
                //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
                if( (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))   ){
                    //cargarDocPen();
                }
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
}//GEN-LAST:event_txtDesLarPrvFocusLost

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
    strDesLarPrv=txtDesLarPrv.getText();
    txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
    txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
        {
            if (txtCodPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
    strCodPrv=txtCodPrv.getText();
    txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
    txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
    //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
    if (butCon.getText().equals("Consultar")){
        blnCon=true;
        if (objThrGUI==null){
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

private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
// TODO add your handling code here:
    if(optTod.isSelected())
        optFil.setSelected(false);
    else
        optFil.setSelected(true);
}//GEN-LAST:event_optTodActionPerformed

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
// TODO add your handling code here:
    if(optFil.isSelected())
        optTod.setSelected(false);
    else
        optTod.setSelected(true);
}//GEN-LAST:event_optFilActionPerformed

private void txtCodLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLocActionPerformed
// TODO add your handling code here:
    txtCodLoc.transferFocus();
}//GEN-LAST:event_txtCodLocActionPerformed

private void txtCodLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusGained
// TODO add your handling code here:
    strCodLoc=txtCodLoc.getText();
    txtCodLoc.selectAll();
}//GEN-LAST:event_txtCodLocFocusGained

private void txtCodLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLocFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)){
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtDesLarLoc.setText("");
            }
            else{
                mostrarVenConLoc(1);
            }
        }
        else
            txtCodLoc.setText(strCodLoc);
}//GEN-LAST:event_txtCodLocFocusLost

private void txtDesLarLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarLocActionPerformed
// TODO add your handling code here:
    txtDesLarLoc.transferFocus();
}//GEN-LAST:event_txtDesLarLocActionPerformed

private void txtDesLarLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusGained
// TODO add your handling code here:
    strDesLarLoc=txtDesLarLoc.getText();
    txtDesLarLoc.selectAll();
}//GEN-LAST:event_txtDesLarLocFocusGained

private void txtDesLarLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarLocFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarLoc.getText().equalsIgnoreCase(strDesLarLoc)){
            if (txtDesLarLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtDesLarLoc.setText("");
                objTblMod.removeAllRows();
            }
            else{
                mostrarVenConLoc(2);
            }
        }
        else
            txtDesLarLoc.setText(strDesLarLoc);
}//GEN-LAST:event_txtDesLarLocFocusLost

private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
// TODO add your handling code here:
    strCodLoc=txtCodLoc.getText();
    mostrarVenConLoc(0);
}//GEN-LAST:event_butLocActionPerformed

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

private void optPenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optPenActionPerformed
// TODO add your handling code here:
    if(optPen.isSelected())
        optRea.setSelected(false);
    else
        optRea.setSelected(true);
}//GEN-LAST:event_optPenActionPerformed

private void optReaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optReaActionPerformed
// TODO add your handling code here:
    if(optRea.isSelected())
        optPen.setSelected(false);
    else
        optPen.setSelected(true);
}//GEN-LAST:event_optReaActionPerformed


    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butLoc;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JComboBox cboEstAut;
    private javax.swing.JComboBox cboPas;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEstAut;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPas;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen2;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optPen;
    private javax.swing.JRadioButton optRea;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesCorVen;
    private javax.swing.JTextField txtDesLarLoc;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarVen;
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
            this.setTitle(strAux + " v0.1");//para codigo con autorizaciones es 0.16 y para produccion 0.13.1
            lblTit.setText(strAux);
            
             //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(true);
            panFilFec.add(objSelFec);
            objSelFec.setBounds(6, 6, 472, 72);           
            objSelFec.setTitulo("Fecha del documento");
            System.out.println("objSelFec.getFechaDesde(): " + objSelFec.getFechaHasta());
            objSelFec.setFechaDesde(getMesAnterior(objSelFec.getFechaHasta()));
            
            //Configurar las ZafVenCon.
            configurarVenConPrv();
            configurarVenConVen();
            configurarVenConTipDoc();
            configurarVenConLoc();
            //Configurar los JTables.
            configurarTblDat();
            cargarEstadoAutorizacion();
            
            
            txtCodVen.setVisible(false);
            txtCodTipDoc.setVisible(false);
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
            arlDat=new ArrayList();
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(26);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"COD.EMP.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"COD.LOC.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"COD_TIP.DOC");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"DES.COR.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"DES.LAR.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"COD.DOC.");
            vecCab.add(INT_TBL_DAT_COD_MNU_DOC,"COD.MNU.DOC.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"NUM.DOC.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"FEC.DOC.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"COD.CLI.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"NOM.CLI.");
            vecCab.add(INT_TBL_DAT_COD_USU_SOL,"COD.USU.SOL.");
            vecCab.add(INT_TBL_DAT_NOM_USU_SOL,"NOM.USU.SOL.");
            vecCab.add(INT_TBL_DAT_CHK_AUT,"AUT.");
            vecCab.add(INT_TBL_DAT_CHK_DEN,"DEN.");
            vecCab.add(INT_TBL_DAT_CHK_CAN,"CAN.");
            vecCab.add(INT_TBL_DAT_FEC_REV,"FEC.REV.");
            vecCab.add(INT_TBL_DAT_OBS_REV,"OBS.REV.");
            vecCab.add(INT_TBL_DAT_BUT_OBS_REV,"");
            vecCab.add(INT_TBL_DAT_TSF_EGR,"TRANSF.EGR");
            vecCab.add(INT_TBL_DAT_CNF_EGR,"CONFIR.EGR.");
            vecCab.add(INT_TBL_DAT_MER_NOT_EGR,"MERCAD.SIN.EGR.");
            vecCab.add(INT_TBL_DAT_TSF_ING,"TRANSF.ING.");
            vecCab.add(INT_TBL_DAT_CNF_ING,"CONFIR.ING");
            vecCab.add(INT_TBL_DAT_MER_NOT_ING,"MERCAD.SIN.ING.");
            vecCab.add(INT_TBL_DAT_FAC_MER_SAL_DEM,"FACTURAR.MER.DEM.");
            vecCab.add(INT_TBL_DAT_MER_FAC,"MER.FACTURADA.");
            vecCab.add(INT_TBL_DAT_PRO_COM,"PRO.COM.");
            
            
            java.awt.Color colFonColUno=new java.awt.Color(228,228,203);
            java.awt.Color colFonColDos=new java.awt.Color(255,221,187);

            
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
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MNU_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(180);
            tcmAux.getColumn(INT_TBL_DAT_COD_USU_SOL).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USU_SOL).setPreferredWidth(150);
            
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CAN).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_OBS_REV).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_REV).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_TSF_EGR).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CNF_EGR).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_EGR).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_TSF_ING).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_CNF_ING).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_ING).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_FAC_MER_SAL_DEM).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_MER_FAC).setPreferredWidth(85);
            tcmAux.getColumn(INT_TBL_DAT_PRO_COM).setPreferredWidth(100);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_MNU_DOC).setResizable(false);
            
            
            tcmAux.getColumn(INT_TBL_DAT_COD_USU_SOL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CAN).setResizable(false);
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
            vecAux.add("" + INT_TBL_DAT_BUT_OBS_REV);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            //objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl.setBackground(colFonColUno);
            

            
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_MNU_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_USU_SOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_USU_SOL).setCellRenderer(objTblCelRenLbl);
            
            
            
            
            
            
            
            objTblCelRenLbl=null;
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
//            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl.setBackground(colFonColDos);
            
            
            tcmAux.getColumn(INT_TBL_DAT_FEC_REV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_OBS_REV).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_REV).setCellRenderer(objTblCelRenLbl);
            
            
    
            
            
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkAut=new ZafTblCelRenChk();
            objTblCelRenChkAut.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellRenderer(objTblCelRenChkAut);
            objTblCelRenChkAut=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAut=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUT).setCellEditor(objTblCelEdiChkAut);
            objTblCelEdiChkAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkDen=new ZafTblCelRenChk();
            objTblCelRenChkDen.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellRenderer(objTblCelRenChkDen);
            objTblCelRenChkDen=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDen=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DEN).setCellEditor(objTblCelEdiChkDen);
            objTblCelEdiChkDen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });            
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkCan=new ZafTblCelRenChk();
            objTblCelRenChkCan.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CAN).setCellRenderer(objTblCelRenChkCan);
            objTblCelRenChkCan=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCan=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CAN).setCellEditor(objTblCelEdiChkCan);
            objTblCelEdiChkCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkTraEgr=new ZafTblCelRenChk();
            objTblCelRenChkTraEgr.setBackground(colFonColUno);
            tcmAux.getColumn(INT_TBL_DAT_TSF_EGR).setCellRenderer(objTblCelRenChkTraEgr);
            objTblCelRenChkTraEgr=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkTraEgr=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TSF_EGR).setCellEditor(objTblCelEdiChkTraEgr);
            objTblCelEdiChkTraEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });           
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkCnfEgr=new ZafTblCelRenChk();
            objTblCelRenChkCnfEgr.setBackground(colFonColUno);
            tcmAux.getColumn(INT_TBL_DAT_CNF_EGR).setCellRenderer(objTblCelRenChkCnfEgr);
            objTblCelRenChkCnfEgr=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCnfEgr=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CNF_EGR).setCellEditor(objTblCelEdiChkCnfEgr);
            objTblCelEdiChkCnfEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });                   
                   
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkMerNotEgr=new ZafTblCelRenChk();
            objTblCelRenChkMerNotEgr.setBackground(colFonColUno);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_EGR).setCellRenderer(objTblCelRenChkMerNotEgr);
            objTblCelRenChkMerNotEgr=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkMerNotEgr=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_EGR).setCellEditor(objTblCelEdiChkMerNotEgr);
            objTblCelEdiChkMerNotEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkTraIng=new ZafTblCelRenChk();
            objTblCelRenChkTraIng.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_TSF_ING).setCellRenderer(objTblCelRenChkTraIng);
            objTblCelRenChkTraIng=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkTraIng=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_TSF_ING).setCellEditor(objTblCelEdiChkTraIng);
            objTblCelEdiChkTraIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });           
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkCnfIng=new ZafTblCelRenChk();
            objTblCelRenChkCnfIng.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_CNF_ING).setCellRenderer(objTblCelRenChkCnfIng);
            objTblCelRenChkCnfIng=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCnfIng=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CNF_ING).setCellEditor(objTblCelEdiChkCnfIng);
            objTblCelEdiChkCnfIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });                   
                   
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkMerNotIng=new ZafTblCelRenChk();
            objTblCelRenChkMerNotIng.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_ING).setCellRenderer(objTblCelRenChkMerNotIng);
            objTblCelRenChkMerNotIng=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkMerNotIng=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MER_NOT_ING).setCellEditor(objTblCelEdiChkMerNotIng);
            objTblCelEdiChkMerNotIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
                  
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkFacMerDem=new ZafTblCelRenChk();
            objTblCelRenChkFacMerDem.setBackground(colFonColUno);
            tcmAux.getColumn(INT_TBL_DAT_FAC_MER_SAL_DEM).setCellRenderer(objTblCelRenChkFacMerDem);
            objTblCelRenChkFacMerDem=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkFacMerDem=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_FAC_MER_SAL_DEM).setCellEditor(objTblCelEdiChkFacMerDem);
            objTblCelEdiChkFacMerDem.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkMerFac=new ZafTblCelRenChk();
            objTblCelRenChkMerFac.setBackground(colFonColUno);
            tcmAux.getColumn(INT_TBL_DAT_MER_FAC).setCellRenderer(objTblCelRenChkMerFac);
            objTblCelRenChkMerFac=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkMerFac=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_MER_FAC).setCellEditor(objTblCelEdiChkMerFac);
            objTblCelEdiChkMerFac.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            //Configurar JTable: Renderizar celdas DE FACTURAR
            objTblCelRenChkProCom=new ZafTblCelRenChk();
            objTblCelRenChkProCom.setBackground(colFonColDos);
            tcmAux.getColumn(INT_TBL_DAT_PRO_COM).setCellRenderer(objTblCelRenChkProCom);
            objTblCelRenChkProCom=null;
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkProCom=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_PRO_COM).setCellEditor(objTblCelEdiChkProCom);
            objTblCelEdiChkProCom.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            objTblCelRenButObs=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS_REV).setCellRenderer(objTblCelRenButObs);
            objCom42_01=new ZafCom42_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButObs= new ZafTblCelEdiButDlg(objCom42_01);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_OBS_REV).setCellEditor(objTblCelEdiButObs);
            objTblCelEdiButObs.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    String strObsLar=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_REV)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_OBS_REV).toString();
                    objCom42_01.setTexto(strObsLar);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            
            
            ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpPasUno=null;
            ZafTblHeaColGrp objTblHeaColGrpPasDos=null;
            ZafTblHeaColGrp objTblHeaColGrpPasTre=null;
            ZafTblHeaColGrp objTblHeaColGrpPasCua=null;
            ZafTblHeaColGrp objTblHeaColGrpPasCin=null;
            ZafTblHeaColGrp objTblHeaColGrpPasSei=null;
            
            
            objTblHeaColGrpPasUno=new ZafTblHeaColGrp("PASO 1: ESPERANDO AUTORIZACIÓN");
            objTblHeaColGrpPasUno.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasUno);
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_MNU_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_FEC_DOC));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_CLI));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_NOM_CLI));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_COD_USU_SOL));
            objTblHeaColGrpPasUno.add(tcmAux.getColumn(INT_TBL_DAT_NOM_USU_SOL));
            
            objTblHeaColGrpPasDos=new ZafTblHeaColGrp("PASO 2: SOLICITUD REVISADA");
            objTblHeaColGrpPasDos.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasDos);
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_CHK_AUT));
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_CHK_DEN));
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_CHK_CAN));
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_FEC_REV));
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_OBS_REV));
            objTblHeaColGrpPasDos.add(tcmAux.getColumn(INT_TBL_DAT_BUT_OBS_REV));
            
            objTblHeaColGrpPasTre=new ZafTblHeaColGrp("PASO 3: EGRESO DE BODEGA");
            objTblHeaColGrpPasTre.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasTre);
            objTblHeaColGrpPasTre.add(tcmAux.getColumn(INT_TBL_DAT_TSF_EGR));
            objTblHeaColGrpPasTre.add(tcmAux.getColumn(INT_TBL_DAT_CNF_EGR));
            objTblHeaColGrpPasTre.add(tcmAux.getColumn(INT_TBL_DAT_MER_NOT_EGR));
            
            objTblHeaColGrpPasCua=new ZafTblHeaColGrp("PASO 4: INGRESO A BODEGA");
            objTblHeaColGrpPasCua.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasCua);
            objTblHeaColGrpPasCua.add(tcmAux.getColumn(INT_TBL_DAT_TSF_ING));
            objTblHeaColGrpPasCua.add(tcmAux.getColumn(INT_TBL_DAT_CNF_ING));
            objTblHeaColGrpPasCua.add(tcmAux.getColumn(INT_TBL_DAT_MER_NOT_ING));
            
            objTblHeaColGrpPasCin=new ZafTblHeaColGrp("PASO 5: FACTURAR");
            objTblHeaColGrpPasCin.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasCin);
            objTblHeaColGrpPasCin.add(tcmAux.getColumn(INT_TBL_DAT_FAC_MER_SAL_DEM));
            objTblHeaColGrpPasCin.add(tcmAux.getColumn(INT_TBL_DAT_MER_FAC));
            
            objTblHeaColGrpPasSei=new ZafTblHeaColGrp("PASO 6: PROCESO COMPLETO");
            objTblHeaColGrpPasSei.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpPasSei);
            objTblHeaColGrpPasSei.add(tcmAux.getColumn(INT_TBL_DAT_PRO_COM));
            
            
            objTblBus=new ZafTblBus(tblDat);
                    
            //objTblMod.insertRow();
            
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
     * Esta funci�n determina si los campos son v�lidos.
     * @return true: Si los campos son v�lidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        int intTipCamFec;
        String strFecDocTmp="";
        String strFecAuxTmp="";
        
        //Validar el "Proveedor".
        if (txtCodPrv.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Proveedor</FONT> es obligatorio.<BR>Escriba o seleccione un proveedor y vuelva a intentarlo.</HTML>");
            txtCodPrv.requestFocus();
            return false;
        }
        



        return true;
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
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
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
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_cli AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_cli='S'";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoCli.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        strIdePrv=vcoCli.getValueAt(2);
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
                        strDirPrv=vcoCli.getValueAt(4);
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        strIdePrv=vcoCli.getValueAt(2);
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
                        strDirPrv=vcoCli.getValueAt(4);
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoCli.getValueAt(1));
                            strIdePrv=vcoCli.getValueAt(2);
                            txtDesLarPrv.setText(vcoCli.getValueAt(3));
                            strDirPrv=vcoCli.getValueAt(4);
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        strIdePrv=vcoCli.getValueAt(2);
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
                        strDirPrv=vcoCli.getValueAt(4);
                        objTblMod.removeAllRows();
                    }
                    else
                    {
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoCli.getValueAt(1));
                            strIdePrv=vcoCli.getValueAt(2);
                            txtDesLarPrv.setText(vcoCli.getValueAt(3));
                            strDirPrv=vcoCli.getValueAt(4);
                            objTblMod.removeAllRows();
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
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
    }



    /**
     * Esta funci�n obtiene la descripci�n larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripci�n larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funci�n devuelve una cadena vac�a.
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
                    estado="Autorizaci�n denegada";
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
    

    /**
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    private class ZafThreadGUI extends Thread{
        public void run(){
            objTblMod.removeAllRows();
            if( ! cargarDetReg()){//carga paso 1
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");                    
            }

            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código de local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_COD_MNU_DOC:
                    strMsg="Código de menú de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="código de cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre de cliente";
                    break;
                case INT_TBL_DAT_COD_USU_SOL:
                    strMsg="Código del usuario solicitante";
                    break;
                case INT_TBL_DAT_NOM_USU_SOL:
                    strMsg="Nombre del usuario solicitante";
                    break;
                case INT_TBL_DAT_CHK_AUT:
                    strMsg="Solicitud Autorizada";
                    break;
                case INT_TBL_DAT_CHK_DEN:
                    strMsg="Solicitud Denegada";
                    break;
                case INT_TBL_DAT_CHK_CAN:
                    strMsg="Solicitud Cancelada";
                    break;
                case INT_TBL_DAT_FEC_REV:
                    strMsg="Fecha de Revisión";
                    break;
                case INT_TBL_DAT_OBS_REV:
                    strMsg="Observación de la revisión";
                    break;
                case INT_TBL_DAT_TSF_EGR:
                    strMsg="Se transfirieron todos los items a egresar";
                    break;
                case INT_TBL_DAT_CNF_EGR:
                    strMsg="Se confirmó el egreso de todos los items";
                    break;
                case INT_TBL_DAT_MER_NOT_EGR:
                    strMsg="Hay mercadería que no egresó";
                    break;
                case INT_TBL_DAT_TSF_ING:
                    strMsg="Se transfirieron todos los items a ingresar";
                    break;
                case INT_TBL_DAT_CNF_ING:
                    strMsg="Se confirmó el ingreso de todos los items";
                    break;
                case INT_TBL_DAT_MER_NOT_ING:
                    strMsg="Hay mercadería que no ingresó";
                    break;
                case INT_TBL_DAT_FAC_MER_SAL_DEM:
                    strMsg="Hay que facturar mercadería que salió para demostración";
                    break;
                case INT_TBL_DAT_MER_FAC:
                    strMsg="La mercadería fue facturada";
                    break;
                case INT_TBL_DAT_PRO_COM:
                    strMsg="Proceso completo";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    private boolean configurarVenConVen(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_usr");
            arlCam.add("a1.tx_usr");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Usuario");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_usr AS a1 INNER JOIN tbr_usrEmp AS a2";
            strSQL+=" ON a1.co_usr=a2.co_usr";
            strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" AND a2.st_ven='S'";
            strSQL+=" ORDER BY a1.tx_nom";
//            System.out.println("CONFIGURARVENCONVEN: " +strSQL);
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoVen=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Vendedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
    
    
     private boolean mostrarVenConVen(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoVen.setCampoBusqueda(1);
                    vcoVen.show();
                    if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtDesCorVen.setText(vcoVen.getValueAt(2));
                        txtDesLarVen.setText(vcoVen.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoVen.buscar("a1.tx_usr", txtDesCorVen.getText())){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtDesCorVen.setText(vcoVen.getValueAt(2));
                        txtDesLarVen.setText(vcoVen.getValueAt(3));
                    }
                    else{
                        vcoVen.setCampoBusqueda(1);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtDesCorVen.setText(vcoVen.getValueAt(2));
                            txtDesLarVen.setText(vcoVen.getValueAt(3));
                        }
                        else{
                            txtDesLarVen.setText(strDesLarVen);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoVen.buscar("a1.tx_nom", txtDesLarVen.getText())){
                        txtCodVen.setText(vcoVen.getValueAt(1));
                        txtDesCorVen.setText(vcoVen.getValueAt(2));
                        txtDesLarVen.setText(vcoVen.getValueAt(3));
                    }
                    else{
                        vcoVen.setCampoBusqueda(2);
                        vcoVen.setCriterio1(11);
                        vcoVen.cargarDatos();
                        vcoVen.show();
                        if (vcoVen.getSelectedButton()==vcoVen.INT_BUT_ACE)
                        {
                            txtCodVen.setText(vcoVen.getValueAt(1));
                            txtDesCorVen.setText(vcoVen.getValueAt(2));
                            txtDesLarVen.setText(vcoVen.getValueAt(3));
                        }
                        else{
                            txtDesLarVen.setText(strDesLarVen);
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
 
     
     
    private String nombreCompletoUsuario(String codigoVendedor){
        Connection conNom;
        Statement stmNom;
        ResultSet rstNom;
        String strNomComVen="";
        try{
            conNom=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conNom!=null){
                stmNom=conNom.createStatement();
                strSQL="";
                strSQL+="SELECT tx_nom FROM tbm_usr";
                strSQL+=" WHERE co_usr=" + codigoVendedor + "";
                rstNom=stmNom.executeQuery(strSQL);
                if(rstNom.next()){
                    strNomComVen=rstNom.getString("tx_nom");
                }
                System.out.println("strNomComVen: " + strNomComVen);
                conNom.close();
                conNom=null;
                rstNom.close();
                rstNom=null;
                stmNom.close();
                stmNom=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);            
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);            
        }
        return strNomComVen;
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
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL+=" WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
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
    
    
    
    private boolean cargarDetReg(){
        int intNumTotReg, i;
        boolean blnRes=true;
        strAux="";
        String strTmp="";
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                
                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //B�squeda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                switch(cboEstAut.getSelectedIndex()){
                    case 0://todos
                        strAux+=" AND a1.st_aut IN('A', 'C', 'D', 'P')";
                        break;
                    case 1://autorizado
                        strAux+=" AND a1.st_aut IN('A')";
                        break;
                    case 2://cancelado
                        strAux+=" AND a1.st_aut IN('C')";
                        break;
                    case 3://denegado
                        strAux+=" AND a1.st_aut IN('D')";
                        break;
                    case 4://pendiente
                        strAux+=" AND a1.st_aut IN('P')";
                        break;
                }
                
                if( ! txtCodLoc.getText().equals(""))
                    strAux+=" AND a1.co_loc=" + txtCodLoc.getText() + "";
                
                if( ! txtCodTipDoc.getText().equals(""))
                    strAux+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                
                if( ! txtCodPrv.getText().equals(""))
                    strAux+=" AND a1.co_cli=" + txtCodPrv.getText() + "";
                
                if( ! txtCodVen.getText().equals(""))
                    strAux+=" AND a1.co_usrSol=" + txtCodVen.getText() + "";
                

                
                switch(cboPas.getSelectedIndex()){
                    case 0://Todos
                        if(optPen.isSelected()){//pendientes
                            //strAux+=" AND a1.st_aut IN('P')";
                        }
                        else if(optRea.isSelected()){//realizados
                            strAux+=" AND a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('T') AND a1.st_conTotMerIng='S' AND a1.st_facMerSalDem IN('S') AND a1.st_merSalDemFac='S'";
                        }
                        break;
                    case 1://esperando autorizacion
                        if(optPen.isSelected()){//pendientes
                            strAux+=" AND a1.st_aut IN('P')";
                        }
                        else if(optRea.isSelected()){//realizados
                            strAux+=" AND a1.st_aut IN('P')";
                        }
                        break;
                    case 2://solicitud revisada
                        if(optPen.isSelected()){//pendientes
                            strAux+=" AND a1.st_aut IN('P')";
                        }
                        else if(optRea.isSelected()){//realizados
                            strAux+=" AND a1.st_aut IN('A', 'C', 'D')";
                        }
                        break;
                    case 3://egreso de bodega
                        if(optRea.isSelected()){//realizados
                            strAux+=" AND CASE WHEN a1.st_aut IN('A') THEN";
                            strAux+=" a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' END";
                        }
                        else{//pendientes
                            strAux+=" AND CASE WHEN a1.st_aut IN('A','C','D') THEN";
                            strAux+=" a1.st_creDocEgr IN('N','P') AND a1.st_conTotMerEgr='N' END";
                        }
                        break;
                    case 4://ingreso de bodega
                        if(optRea.isSelected()){//realizados
                            strAux+=" AND CASE WHEN a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' THEN";
                            strAux+=" a1.st_creDocIng='T' AND a1.st_conTotMerIng='S' END";
                        }
                        else{//pendientes
                            strAux+=" AND CASE WHEN a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' THEN";
                            strAux+=" a1.st_creDocIng IN('N','P') AND a1.st_conTotMerIng='N' END";
                        }
                        break;
                    case 5://facturar
                        if(optPen.isSelected()){//pendientes
                            strAux+=" AND CASE WHEN a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('P','N') AND a1.st_conTotMerIng='N' AND a1.st_exiMerSinIng IN('S','N') THEN";
                            strAux+=" a1.st_facMerSalDem IN('N','S') AND a1.st_merSalDemFac='N' ";
                            strAux+=" ";
                            strAux+=" WHEN a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('N') AND a1.st_conTotMerIng='N' AND a1.st_exiMerSinIng='N' THEN";
                            strAux+=" a1.st_facMerSalDem IN('S','N') AND a1.st_merSalDemFac='N' ";
                            strAux+=" END";
                            
                            
                        }
                        else if(optRea.isSelected()){//realizados
                            strAux+=" AND CASE WHEN a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('P','N') AND a1.st_conTotMerIng='N' AND a1.st_exiMerSinIng='S' THEN";
                            strAux+=" a1.st_facMerSalDem IN('S') AND a1.st_merSalDemFac='S' END";
                        }
                        break;
                    case 6://proceso completo
                        if(optPen.isSelected()){//pendientes
//                            strAux+=" AND CASE WHEN a1.st_facMerSalDem IN('N') THEN";
//                            strAux+=" a1.st_creDocEgr IN('T') AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('P','N') ";
//                            strAux+=" AND a1.st_conTotMerIng='N' AND a1.st_facMerSalDem IN('N') AND a1.st_merSalDemFac='N'";
//                            strAux+=" ELSE ";
//                            strAux+=" a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('T') ";
//                            strAux+=" AND a1.st_conTotMerIng='S' AND a1.st_facMerSalDem IN('N','S') AND a1.st_merSalDemFac='N' END";
                            
                            strAux+=" AND CASE WHEN a1.st_merSalDemFac IN('N') THEN";
                            strAux+=" (a1.st_facMerSalDem IN('S') OR a1.st_conTotMerIng IN('N') OR a1.st_conTotMerEgr IN('N') )";
                            strAux+="     WHEN a1.st_facMerSalDem IN('N') THEN";
                            strAux+=" (a1.st_facMerSalDem IN('N') OR a1.st_conTotMerIng IN('N') OR a1.st_conTotMerEgr IN('N') ) END";
                        }
                        else if(optRea.isSelected()){//realizados
                            strAux+=" AND CASE WHEN a1.st_facMerSalDem IN('N') THEN";
                            strAux+=" a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('T') ";
                            strAux+=" AND a1.st_conTotMerIng='S' AND a1.st_facMerSalDem IN('N') AND a1.st_merSalDemFac='N'";
                            strAux+=" ELSE ";
                            strAux+=" a1.st_creDocEgr='T' AND a1.st_conTotMerEgr='S' AND a1.st_creDocIng IN('T') ";
                            strAux+=" AND a1.st_conTotMerIng='S' AND a1.st_facMerSalDem IN('S') AND a1.st_merSalDemFac='S' END";
                        }
                        break;
                }
                
                
                
                
                
                //Obtener la condición.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM tbm_cabSolSalTemMer AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli AND a3.st_reg='A')";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a4 ";
                strSQL+=" ON (a1.co_usrSol=a4.co_usr AND a4.st_reg='A')";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_mnu IN(2039,2049)";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=strAux;
                System.out.println("COUNT - cargarDetReg: " + strSQL);
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, ";
                strSQL+=" a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, ";
                strSQL+=" a1.co_doc, a1.co_mnu, a1.ne_numDoc, a1.fe_doc";
                strSQL+=" , a3.co_cli, a3.tx_nom AS tx_nomCli, a4.co_usr, a4.tx_usr, a4.tx_nom";
                strSQL+=" , a1.st_aut, a1.fe_aut, a1.tx_obsAut";
                strSQL+=" , a1.st_creDocEgr, a1.st_conTotMerEgr, a1.st_exiMerSinEgr";
                strSQL+=" , a1.st_creDocIng, a1.st_conTotMerIng, a1.st_exiMerSinIng, a1.st_facMerSalDem, a1.st_merSalDemFac";
                strSQL+=" FROM tbm_cabSolSalTemMer AS a1 ";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli AND a3.st_reg='A')";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a4 ";
                strSQL+=" ON (a1.co_usrSol=a4.co_usr AND a4.st_reg='A')";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_mnu IN(2039,2049)";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=strAux;

                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,             "");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         "" + rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,         "" + rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_MNU_DOC,     "" + rst.getString("co_mnu"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         "" + rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         "" + objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,         "" + rst.getObject("co_cli")==null?"":rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,         "" + rst.getObject("tx_nomCli")==null?"":rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_COD_USU_SOL,     "" + rst.getString("co_usr"));
                        vecReg.add(INT_TBL_DAT_NOM_USU_SOL,     "" + rst.getString("tx_nom"));
                        //Paso 2
                        vecReg.add(INT_TBL_DAT_CHK_AUT,         "");
                        vecReg.add(INT_TBL_DAT_CHK_DEN,         "");
                        vecReg.add(INT_TBL_DAT_CHK_CAN,         "");
                        vecReg.add(INT_TBL_DAT_FEC_REV,         "" + rst.getObject("fe_aut")==null?"":rst.getString("fe_aut"));
                        vecReg.add(INT_TBL_DAT_OBS_REV,         "" + rst.getObject("tx_obsAut")==null?"":rst.getString("tx_obsAut"));
                        
                        vecReg.add(INT_TBL_DAT_BUT_OBS_REV,     "");
                        //Paso 3
                        vecReg.add(INT_TBL_DAT_TSF_EGR,         "");
                        vecReg.add(INT_TBL_DAT_CNF_EGR,         "");
                        vecReg.add(INT_TBL_DAT_MER_NOT_EGR,     "");
                        vecReg.add(INT_TBL_DAT_TSF_ING,         "");
                        vecReg.add(INT_TBL_DAT_CNF_ING,         "");
                        vecReg.add(INT_TBL_DAT_MER_NOT_ING,     "");
                        vecReg.add(INT_TBL_DAT_FAC_MER_SAL_DEM, "");
                        vecReg.add(INT_TBL_DAT_MER_FAC,         "");
                        vecReg.add(INT_TBL_DAT_PRO_COM,         "");
                        
                        //Paso 2
                        strTmp=rst.getString("st_aut");
                        if(strTmp.equals("A"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AUT);
                        else if(strTmp.equals("D"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_DEN);
                        else if(strTmp.equals("C"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_CAN);
                        else if(strTmp.equals("P")){
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CHK_AUT);
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CHK_DEN);
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CHK_CAN);
                        }
                        
                        strTmp=rst.getString("st_creDocEgr");
                        if(strTmp.equals("T"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_TSF_EGR);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_TSF_EGR);
                        
                        strTmp=rst.getString("st_conTotMerEgr");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CNF_EGR);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CNF_EGR);
                        
                        strTmp=rst.getString("st_exiMerSinEgr");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_MER_NOT_EGR);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_MER_NOT_EGR);

                        
                        
                        
                        
                        strTmp=rst.getString("st_creDocIng");
                        if(strTmp.equals("T"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_TSF_ING);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_TSF_ING);

                        strTmp=rst.getString("st_conTotMerIng");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CNF_ING);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_CNF_ING);

                        strTmp=rst.getString("st_exiMerSinIng");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_MER_NOT_ING);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_MER_NOT_ING);

                        
                        strTmp=rst.getString("st_facMerSalDem");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_FAC_MER_SAL_DEM);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_FAC_MER_SAL_DEM);
                        
                        strTmp=rst.getString("st_merSalDemFac");
                        if(strTmp.equals("S"))
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_MER_FAC);
                        else
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_MER_FAC);
                        
                        //si no hay q facturar
                        if(  (rst.getString("st_facMerSalDem").equals("N")) &&  rst.getString("st_merSalDemFac").equals("N") && rst.getString("st_creDocIng").equals("T")   &&  rst.getString("st_conTotMerIng").equals("S")   ){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_PRO_COM);
                        }
                        //si hay q facturar
                        else if(  (rst.getString("st_facMerSalDem").equals("S")) &&  rst.getString("st_merSalDemFac").equals("S") && rst.getString("st_creDocIng").equals("T")   &&  rst.getString("st_conTotMerIng").equals("S")   ){
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_PRO_COM);
                        }
                        else{
                            vecReg.setElementAt(new Boolean(false), INT_TBL_DAT_PRO_COM);
                        }
                        
                        

                        strTmp="";
                        
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
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
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero s�lo se procesaron " + tblDat.getRowCount() + ".");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
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
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
                case 1: //B�squeda directa por "Descripci�n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else{
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else{
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())){
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    else{
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        }
                        else{
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    private boolean cargarEstadoAutorizacion(){
        boolean blnRes=true;
        int intTod=0;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT ";
                strSQL+=" CASE WHEN st_aut='A' THEN 'Autorizado'";
                strSQL+="      WHEN st_aut='C' THEN 'Cancelado'";
                strSQL+="      WHEN st_aut='D' THEN 'Denegado'";
                strSQL+="      WHEN st_aut='P' THEN 'Pendiente'";
                strSQL+=" END AS st_aut";
                strSQL+=" FROM tbm_cabSolSalTemMer";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" GROUP BY st_aut";
                strSQL+=" ORDER BY st_aut";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    if(intTod==0){
                        cboEstAut.addItem("Todos");
                        intTod++;
                    }
                    cboEstAut.addItem("" + rst.getString("st_aut"));
                }
                cboEstAut.setSelectedIndex(0);
                
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
        
    }
    
    private String getMesAnterior(String fechaActual){
        Connection conMesAnt;
        Statement stmMesAnt;
        ResultSet rstMesAnt;
        String strMesAnt="";
        try{
            conMesAnt=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conMesAnt!=null){
                stmMesAnt=conMesAnt.createStatement();
                strSQL="";
                strSQL+=" select cast('" + objUti.formatearFecha(fechaActual, "dd/MM/yyyy", "yyyy-MM-dd") + "' as date)-31  as mesAnt";
                rstMesAnt=stmMesAnt.executeQuery(strSQL);
                if(rstMesAnt.next()){
                    strMesAnt=rstMesAnt.getString("mesAnt");
                }
                conMesAnt.close();
                conMesAnt=null;
                stmMesAnt.close();
                stmMesAnt=null;
                rstMesAnt.close();
                rstMesAnt=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return objUti.formatearFecha(strMesAnt,"yyyy-MM-dd","dd/MM/yyyy");
    }
    
    
    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConLoc(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("250");
            arlAncCol.add("200");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_loc, a1.tx_nom, a1.tx_dir";
            strSQL+=" FROM tbm_loc AS a1";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND a1.st_reg IN ('A','P')";
            strSQL+=" ORDER BY a1.tx_nom";
            //Ocultar columnas.
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
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConLoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.show();
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoLoc.buscar("a1.tx_nom", txtDesLarLoc.getText()))
                    {
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else
                    {
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtDesLarLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarLoc.setText(strDesLarLoc);
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
    
    
}