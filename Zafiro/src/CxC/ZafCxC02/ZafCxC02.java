/*
 * ZafCxC02.java
 *
 * Created on 4 Agosto , 2015, 3:26 PM
 */
package CxC.ZafCxC02;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Tony Sanginez
 */
public class ZafCxC02 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private ZafTblCelEdiTxt objTblCelEdiTxt;//Editor: JTextField en celda.
    private ZafUtil objUti;//Objeto del tipo de la clase ZafUtil, el cual me va a permitir 
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenBut objTblCelRenBut;//Render: Presentar JButton en JTable.
    private ZafUtil zafUti = new ZafUtil();
    private ZafTblOrd objTblOrd;
    private ZafTblBus objTblBus;

    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.

    private ZafTblFilCab objTblFilCab;

    private boolean blnHayCam;
    private ZafTblEdi objTblEdi;                                //Editor: Editor del JTable.
    private boolean blnCon;//true: Continua la ejecución del hilo. // Continuidad del hilo
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenLbl objTblCelRenLblNum;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelRenLbl objTblCelRenLblCod;                 //Render: Presentar JLabel en JTable (Números).
    private ZafTblCelEdiChk objTblCelEdiChk;
    private java.util.Date datFecAux;                          //Auxiliar: Para almacenar fechas.
    private String strCodCli;                                  //Contenido del campo al obtener el foco.
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiChk objTblCelEdiChkPre;
    private String strVersion = " v0.4";

    private ZafVenCon vcoCli;//
    private ZafTblModLis objTblModLis;                          //Detectar cambios de valores en las celdas.
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;           //Editor: JButton en celda.
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;           //Editor: JTextField de consulta en celda.

    private String strSQL, strAux;
    private Vector vecDat, vecReg, vecCab, vecAux;
    private String strNumDoc, strFecVen_Glo, strValAbo_Glo;
    private Connection con;
    private Statement stm;
    private ResultSet rst;

    private String strDesLarTipDoc, strDesCorTipDoc, strCodTipDoc, strDesLarCli;
    private ZafVenCon vcoDoc;
    private ZafVenCon vcoFac;
    private ZafVenCon vcoTipRet;                                //Ventana de consulta "para las retencions".

    //Tabla 
    private final int INT_TBL_DAT_LIN = 0;
    private final int INT_TBL_DAT_COD_EMP = 1;
    private final int INT_TBL_DAT_COD_LOC = 2;
    private final int INT_TBL_DAT_COD_TIP_DOC = 3;
    private final int INT_TBL_DAT_DES_COR_TIP_DOC = 4;
    private final int INT_TBL_DAT_DES_LAR_TIP_DOC = 5;
    private final int INT_TBL_DAT_COD_DOC = 6;
    private final int INT_TBL_DAT_NUM_DOC = 7;
    private final int INT_TBL_DAT_FEC_DOC = 8;
    private final int INT_TBL_DAT_MON = 9;
    private final int INT_TBL_DAT_ABO = 10;
    private final int INT_TBL_DAT_CHK_VAL_RET_FAV_CLI = 11;
    private final int INT_TBL_DAT_COD_RET = 12;
    private final int INT_TBL_DAT_POR_RET = 13;
    private final int INT_TBL_DAT_BTN_RET = 14;
    private final int INT_TBL_DAT_APL_RET = 15;

    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoTipRet;        //Editor: JTextField de consulta en celda.
    private ZafTblCelEdiButVco objTblCelEdiButVcoTipRet;        //Editor: JButton en celda
    private ZafPerUsr objPerUsr;                               //Objeto que almacena el perfil del usuario.
    private ZafDocLis objDocLis;
    private ZafDatePicker dtpFecDoc;
    private Boolean blnTieneCheque = false, blnTieneAbonos = false, blnTieneRetenciones = false;
    private String strCodEmpRee, strCodLocRee, strCodTipDocRee, strCodDocRee;

    /**
     * Creates new form ZafCxC02
     */
    public ZafCxC02(ZafParSis obj) {
        try {
            objParSis = (ZafParSis) obj.clone();
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                objUti = new ZafUtil();
                //Inicializar objetos.
                objUti = new ZafUtil();
                txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
                txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
                txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
                txtDesLarCli.setBackground(objParSis.getColorCamposObligatorios());
                txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
                txtSubTotBie.setBackground(objParSis.getColorCamposSistema());
                txtIvaBie.setBackground(objParSis.getColorCamposSistema());
                txtSubTotSer.setBackground(objParSis.getColorCamposSistema());
                txtIvaSer.setBackground(objParSis.getColorCamposSistema());
                txtValDoc.setBackground(objParSis.getColorCamposSistema());
                txtCodTipDoc.setVisible(false); // Codigo del tipo de documento
                txtCodTipPer.setVisible(false); // Codigo del Tipo de persona
                lblTieAboAsi.setVisible(false);
                lblTieCheAsi.setVisible(false);
                blnCon = true;
            } else {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde EMPRESAS.");
                dispose();
            }

            if (!configurarFrm()) {
                exitForm();
            }
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      bgrFil = new javax.swing.ButtonGroup();
      jLabel1 = new javax.swing.JLabel();
      panFrm = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      tabFrm = new javax.swing.JTabbedPane();
      panRpt = new javax.swing.JPanel();
      panCab = new javax.swing.JPanel();
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jLabel4 = new javax.swing.JLabel();
      txtCodTipDoc = new javax.swing.JTextField();
      txtDesCorTipDoc = new javax.swing.JTextField();
      txtDesLarTipDoc = new javax.swing.JTextField();
      txtNumDoc = new javax.swing.JTextField();
      txtValDoc = new javax.swing.JTextField();
      jLabel5 = new javax.swing.JLabel();
      jLabel6 = new javax.swing.JLabel();
      txtIvaBie = new javax.swing.JTextField();
      txtSubTotBie = new javax.swing.JTextField();
      btnNumDoc = new javax.swing.JButton();
      btnTipDoc = new javax.swing.JButton();
      txtCodTipPer = new javax.swing.JTextField();
      jLabel7 = new javax.swing.JLabel();
      lblCli = new javax.swing.JLabel();
      txtCodCli = new javax.swing.JTextField();
      txtDesLarCli = new javax.swing.JTextField();
      butCli = new javax.swing.JButton();
      rdaReeNor = new javax.swing.JRadioButton();
      lblTieAboAsi = new javax.swing.JLabel();
      lblTieCheAsi = new javax.swing.JLabel();
      rdaPonValRetFavCli = new javax.swing.JRadioButton();
      jLabel8 = new javax.swing.JLabel();
      txtSubTotSer = new javax.swing.JTextField();
      jLabel9 = new javax.swing.JLabel();
      txtIvaSer = new javax.swing.JTextField();
      chkMosItmCha = new javax.swing.JCheckBox();
      panDet = new javax.swing.JPanel();
      spnDet = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      panBar = new javax.swing.JPanel();
      panBot = new javax.swing.JPanel();
      butLim = new javax.swing.JButton();
      butCon = new javax.swing.JButton();
      butGua = new javax.swing.JButton();
      butCer = new javax.swing.JButton();
      panBarEst = new javax.swing.JPanel();
      lblMsgSis = new javax.swing.JLabel();
      jPanel6 = new javax.swing.JPanel();
      pgrSis = new javax.swing.JProgressBar();

      jLabel1.setText("jLabel1");

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
      lblTit.setText("...");
      panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

      tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

      panRpt.setLayout(new java.awt.BorderLayout());

      panCab.setPreferredSize(new java.awt.Dimension(610, 140));
      panCab.setLayout(null);

      jLabel2.setText("Tipo de documento:");
      panCab.add(jLabel2);
      jLabel2.setBounds(10, 10, 120, 14);

      jLabel3.setText("IVA Bien:");
      panCab.add(jLabel3);
      jLabel3.setBounds(430, 51, 120, 20);

      jLabel4.setText("Total:");
      panCab.add(jLabel4);
      jLabel4.setBounds(430, 114, 120, 20);

      txtCodTipDoc.setEditable(false);
      txtCodTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtCodTipDocActionPerformed(evt);
         }
      });
      panCab.add(txtCodTipDoc);
      txtCodTipDoc.setBounds(260, 47, 10, 20);

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
      txtDesCorTipDoc.setBounds(135, 3, 60, 20);
      panCab.add(txtDesLarTipDoc);
      txtDesLarTipDoc.setBounds(196, 3, 207, 20);

      txtNumDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtNumDocActionPerformed(evt);
         }
      });
      txtNumDoc.addFocusListener(new java.awt.event.FocusAdapter() {
         public void focusGained(java.awt.event.FocusEvent evt) {
            txtNumDocFocusGained(evt);
         }
         public void focusLost(java.awt.event.FocusEvent evt) {
            txtNumDocFocusLost(evt);
         }
      });
      panCab.add(txtNumDoc);
      txtNumDoc.setBounds(135, 47, 100, 20);

      txtValDoc.setEditable(false);
      txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCab.add(txtValDoc);
      txtValDoc.setBounds(550, 114, 120, 20);

      jLabel5.setText("Num. de documento:");
      panCab.add(jLabel5);
      jLabel5.setBounds(10, 50, 120, 14);
      jLabel5.getAccessibleContext().setAccessibleName("Num. documento:");

      jLabel6.setText("SubTotal Bien:");
      panCab.add(jLabel6);
      jLabel6.setBounds(430, 30, 120, 20);

      txtIvaBie.setEditable(false);
      txtIvaBie.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCab.add(txtIvaBie);
      txtIvaBie.setBounds(550, 51, 120, 20);

      txtSubTotBie.setEditable(false);
      txtSubTotBie.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCab.add(txtSubTotBie);
      txtSubTotBie.setBounds(550, 30, 120, 20);

      btnNumDoc.setText("...");
      btnNumDoc.setPreferredSize(new java.awt.Dimension(45, 20));
      btnNumDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNumDocActionPerformed(evt);
         }
      });
      panCab.add(btnNumDoc);
      btnNumDoc.setBounds(235, 47, 20, 20);

      btnTipDoc.setText("...");
      btnTipDoc.setPreferredSize(new java.awt.Dimension(45, 20));
      btnTipDoc.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnTipDocActionPerformed(evt);
         }
      });
      panCab.add(btnTipDoc);
      btnTipDoc.setBounds(403, 3, 20, 20);

      txtCodTipPer.setEditable(false);
      panCab.add(txtCodTipPer);
      txtCodTipPer.setBounds(270, 47, 10, 20);

      jLabel7.setText("Fecha del documento:");
      panCab.add(jLabel7);
      jLabel7.setBounds(430, 10, 130, 14);

      lblCli.setText("Cliente:");
      panCab.add(lblCli);
      lblCli.setBounds(10, 30, 80, 14);

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
      panCab.add(txtCodCli);
      txtCodCli.setBounds(135, 25, 60, 20);

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
      panCab.add(txtDesLarCli);
      txtDesLarCli.setBounds(196, 25, 207, 20);

      butCli.setText("...");
      butCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butCliActionPerformed(evt);
         }
      });
      panCab.add(butCli);
      butCli.setBounds(403, 25, 20, 20);

      bgrFil.add(rdaReeNor);
      rdaReeNor.setSelected(true);
      rdaReeNor.setText("Reestructuración normal");
      rdaReeNor.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            rdaReeNorActionPerformed(evt);
         }
      });
      panCab.add(rdaReeNor);
      rdaReeNor.setBounds(10, 72, 180, 18);

      lblTieAboAsi.setForeground(new java.awt.Color(255, 0, 0));
      lblTieAboAsi.setText("*Tiene Abonos asignados.");
      panCab.add(lblTieAboAsi);
      lblTieAboAsi.setBounds(10, 115, 159, 14);

      lblTieCheAsi.setForeground(new java.awt.Color(255, 0, 0));
      lblTieCheAsi.setText("*Tiene Cheque asignado.");
      panCab.add(lblTieCheAsi);
      lblTieCheAsi.setBounds(220, 115, 160, 14);

      bgrFil.add(rdaPonValRetFavCli);
      rdaPonValRetFavCli.setText("Poner valor de retención como saldo a favor del cliente");
      rdaPonValRetFavCli.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            rdaPonValRetFavCliActionPerformed(evt);
         }
      });
      panCab.add(rdaPonValRetFavCli);
      rdaPonValRetFavCli.setBounds(10, 90, 340, 18);

      jLabel8.setText("SubTotal Servicio:");
      panCab.add(jLabel8);
      jLabel8.setBounds(430, 72, 120, 20);

      txtSubTotSer.setEditable(false);
      txtSubTotSer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCab.add(txtSubTotSer);
      txtSubTotSer.setBounds(550, 72, 120, 20);

      jLabel9.setText("IVA Servicio:");
      panCab.add(jLabel9);
      jLabel9.setBounds(430, 93, 120, 20);

      txtIvaSer.setEditable(false);
      txtIvaSer.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      panCab.add(txtIvaSer);
      txtIvaSer.setBounds(550, 93, 120, 20);

      chkMosItmCha.setText("El item es chatarra");
      chkMosItmCha.setEnabled(false);
      panCab.add(chkMosItmCha);
      chkMosItmCha.setBounds(220, 72, 140, 18);

      panRpt.add(panCab, java.awt.BorderLayout.NORTH);

      panDet.setLayout(new java.awt.BorderLayout());

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

      panDet.add(spnDet, java.awt.BorderLayout.CENTER);

      panRpt.add(panDet, java.awt.BorderLayout.CENTER);

      tabFrm.addTab("General", panRpt);

      panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

      panBar.setLayout(new java.awt.BorderLayout());

      panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

      butLim.setText("Limpiar");
      butLim.setToolTipText("Limpia la pantalla");
      butLim.setPreferredSize(new java.awt.Dimension(92, 25));
      butLim.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butLimActionPerformed(evt);
         }
      });
      panBot.add(butLim);

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
      butGua.setToolTipText("Guarda");
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

      getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-700)/2, (screenSize.height-520)/2, 700, 520);
   }// </editor-fold>//GEN-END:initComponents

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
// TODO add your handling code here:
    String strTit, strMsg;
    try {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            if (con != null) {
                con.close();
                con = null;
            }
            dispose();
        }
    } catch (java.sql.SQLException e) {
        dispose();
    }
}//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        if (validaDocumentos()) 
        {   if (txtNumDoc.getText().length() > 0)
            {   objTblMod.removeAllRows();
                if (butCon.getText().equals("Consultar"))
                {   blnCon = true;
                    consultar();
                }
                else 
                {
                    blnCon = false;
                }
            }
            else
            {   String strTit = "Mensaje del sistema Zafiro", strMsg;
                strMsg = "<HTML>El número de documento es obligatorio antes de consultar</HTML>";
                JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
                txtNumDoc.requestFocus();
            }
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed

       int intConFil, intConFilSelChk;
       String strAux;
       
       try 
       {  intConFil = 0;
          intConFilSelChk = 0;
      
          for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
          {  strAux = objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC).toString();

             if (!strAux.equals(""))
             {  
                intConFil++;
             }
             
             if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_VAL_RET_FAV_CLI))
             {
                intConFilSelChk++;
             }
          }

          if (intConFil == 0)
          {  mostrarMsgInf("No es posible grabar sin detalle.\nIngrese el detalle para poder grabar.");
             return;
          }
          
          if (rdaReeNor.isSelected() && intConFilSelChk != 0)
          {  strAux =  "<HTML>Está marcada la opción <FONT COLOR=\"blue\">Reestructuración normal</FONT>.<BR>";
             strAux += "Por tanto, en el detalle no debe estar seleccionada la columna <FONT COLOR=\"blue\">Val.Fav.Cli.</FONT>.<BR>";
             strAux += "Verifique y vuelva a intentarlo.</HTML>";
             mostrarMsgInf(strAux);
             return;
          }
          
          if (rdaPonValRetFavCli.isSelected() && intConFilSelChk == 0)
          {  strAux =  "<HTML>Está marcada la opción <FONT COLOR=\"blue\">Poner valor de retención como saldo a favor del cliente</FONT>.<BR>";
             strAux += "Por tanto, en el detalle debe estar seleccionada la columna <FONT COLOR=\"blue\">Val.Fav.Cli.</FONT>.<BR>";
             strAux += "Verifique y vuelva a intentarlo.</HTML>";
             mostrarMsgInf(strAux);
             return;
          }
          
          if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0)
          {  if (guardar())
             {  
                mostrarMsgInf("<HTML>La operación GUARDAR se realizó con éxito</HTML>");
             }
             else
             {  strAux = "<HTML>Ocurrió un error al realizar la operación GUARDAR.<BR>Intente realizar la operación nuevamente.<BR>Si el problema persiste notifiquelo a su administrador del sistema</HTML>";
                mostrarMsgInf(strAux);
             }
          }
       } //try 
       
       catch (Exception e)
       {
          objUti.mostrarMsgErr_F1(this, e);
       }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void txtCodTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTipDocActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCodTipDocActionPerformed

    private void btnTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTipDocActionPerformed
        strCodTipDoc = txtCodTipDoc.getText();
        mostrarDocumentos(0);
    }//GEN-LAST:event_btnTipDocActionPerformed

    private void txtNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocActionPerformed
        // TODO add your handling code here:
        if (validaDocumentos())
        {
            strCodTipDoc = txtCodTipDoc.getText();
            strNumDoc = txtNumDoc.getText();
            configurarFacturas();
            mostrarFacturas(1);
        }
        else
        {
           txtNumDoc.setText("");
        }
    }//GEN-LAST:event_txtNumDocActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        // TODO add your handling code here:
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        // TODO add your handling code here:
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        // TODO add your handling code here:
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarDocumentos(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void btnNumDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNumDocActionPerformed
        // TODO add your handling code here:
        if (validaDocumentos()) {
            configurarFacturas();
            consultarFacturas();
            limpiaTabla();
        }
    }//GEN-LAST:event_btnNumDocActionPerformed

    private void txtNumDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusGained
        // TODO add your handling code here:
        strNumDoc = txtNumDoc.getText();
        txtNumDoc.selectAll();
    }//GEN-LAST:event_txtNumDocFocusGained

    private void txtNumDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocFocusLost
        
         if (!txtNumDoc.getText().equalsIgnoreCase(strNumDoc)) 
         {   if (txtNumDoc.getText().equals(""))
             {   txtSubTotBie.setText("");
                 txtIvaBie.setText("");
                 txtSubTotSer.setText("");
                 txtIvaSer.setText("");
                 txtValDoc.setText("");
                 lblTieAboAsi.setVisible(false);
                 lblTieCheAsi.setVisible(false);
                 blnTieneAbonos = false;
                 blnTieneCheque = false;
                 blnTieneRetenciones = false;
                 objTblMod.removeAllRows();
             }
             else 
             {   if (validaDocumentos())
                 {  configurarFacturas();
                    mostrarFacturas(1);
                    consultar();
                 }
                 else
                 {  txtNumDoc.setText("");
                    txtSubTotBie.setText("");
                    txtIvaBie.setText("");
                    txtSubTotSer.setText("");
                    txtIvaSer.setText("");
                    txtValDoc.setText("");
                    lblTieAboAsi.setVisible(false);
                    lblTieCheAsi.setVisible(false);
                    blnTieneAbonos = false;
                    blnTieneCheque = false;
                    blnTieneRetenciones = false;
                    objTblMod.removeAllRows();
                 }
             }
         }
         else 
         {
             txtNumDoc.setText(strNumDoc);
             consultar();
         }
    }//GEN-LAST:event_txtNumDocFocusLost
    
   private void butLimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLimActionPerformed
      //Realizar accion de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
      limpiar();
   }//GEN-LAST:event_butLimActionPerformed

   private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
      // TODO add your handling code here:
      txtCodCli.transferFocus();
   }//GEN-LAST:event_txtCodCliActionPerformed

   private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
      // TODO add your handling code here:
      strCodCli = txtCodCli.getText();
      txtCodCli.selectAll();
   }//GEN-LAST:event_txtCodCliFocusGained

   private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
      // TODO add your handling code here:
      //Validar el contenido de la celda sólo si ha cambiado.
      if (!txtCodCli.getText().equalsIgnoreCase(strCodCli))
      {  if (txtCodCli.getText().equals(""))
         {  txtCodCli.setText("");
            txtDesLarCli.setText("");
         }
         else
         {
            mostrarVenConCli(1);
         }
      }
      else
      {
         txtCodCli.setText(strCodCli);
      }
   }//GEN-LAST:event_txtCodCliFocusLost

   private void txtDesLarCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCliActionPerformed
      // TODO add your handling code here:
      txtDesLarCli.transferFocus();
   }//GEN-LAST:event_txtDesLarCliActionPerformed

   private void txtDesLarCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusGained
      // TODO add your handling code here:
      strDesLarCli = txtDesLarCli.getText();
      txtDesLarCli.selectAll();
   }//GEN-LAST:event_txtDesLarCliFocusGained

   private void txtDesLarCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCliFocusLost
      // TODO add your handling code here:
      //Validar el contenido de la celda sólo si ha cambiado.
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
      {
         txtDesLarCli.setText(strDesLarCli);
      }
   }//GEN-LAST:event_txtDesLarCliFocusLost

   private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
      // TODO add your handling code here:
      strCodCli = txtCodCli.getText();
      mostrarVenConCli(0);
   }//GEN-LAST:event_butCliActionPerformed

   private void rdaReeNorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdaReeNorActionPerformed
      // TODO add your handling code here:

      if (rdaReeNor.isSelected())
      {  for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
         {  strAux = objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC).toString();

            if (!strAux.equals(""))
            {  
               objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_VAL_RET_FAV_CLI);
            }
         }
      }
   }//GEN-LAST:event_rdaReeNorActionPerformed

   private void rdaPonValRetFavCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdaPonValRetFavCliActionPerformed
      // TODO add your handling code here:

      if (rdaPonValRetFavCli.isSelected())
      {  for (int i = 0; i < objTblMod.getRowCountTrue(); i++)
         {  strAux = objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_NUM_DOC).toString();

            if (!strAux.equals(""))
            {  
               objTblMod.setChecked(true, i, INT_TBL_DAT_CHK_VAL_RET_FAV_CLI);
            }
         }
      }
   }//GEN-LAST:event_rdaPonValRetFavCliActionPerformed


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.ButtonGroup bgrFil;
   private javax.swing.JButton btnNumDoc;
   private javax.swing.JButton btnTipDoc;
   private javax.swing.JButton butCer;
   private javax.swing.JButton butCli;
   private javax.swing.JButton butCon;
   private javax.swing.JButton butGua;
   private javax.swing.JButton butLim;
   private javax.swing.JCheckBox chkMosItmCha;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JLabel jLabel4;
   private javax.swing.JLabel jLabel5;
   private javax.swing.JLabel jLabel6;
   private javax.swing.JLabel jLabel7;
   private javax.swing.JLabel jLabel8;
   private javax.swing.JLabel jLabel9;
   private javax.swing.JPanel jPanel6;
   private javax.swing.JLabel lblCli;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblTieAboAsi;
   private javax.swing.JLabel lblTieCheAsi;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBar;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panBot;
   private javax.swing.JPanel panCab;
   private javax.swing.JPanel panDet;
   private javax.swing.JPanel panFrm;
   private javax.swing.JPanel panRpt;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JRadioButton rdaPonValRetFavCli;
   private javax.swing.JRadioButton rdaReeNor;
   private javax.swing.JScrollPane spnDet;
   private javax.swing.JTabbedPane tabFrm;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextField txtCodCli;
   private javax.swing.JTextField txtCodTipDoc;
   private javax.swing.JTextField txtCodTipPer;
   private javax.swing.JTextField txtDesCorTipDoc;
   private javax.swing.JTextField txtDesLarCli;
   private javax.swing.JTextField txtDesLarTipDoc;
   private javax.swing.JTextField txtIvaBie;
   private javax.swing.JTextField txtIvaSer;
   private javax.swing.JTextField txtNumDoc;
   private javax.swing.JTextField txtSubTotBie;
   private javax.swing.JTextField txtSubTotSer;
   private javax.swing.JTextField txtValDoc;
   // End of variables declaration//GEN-END:variables

//    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }

//    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
            this.setTitle(objParSis.getNombreMenu() + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar ZafDatePicker:
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(570, 10, 100, 20);
            dtpFecDoc.setEnabled(false);
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Ven.");
            vecCab.add(INT_TBL_DAT_MON, "Mon.");
            vecCab.add(INT_TBL_DAT_ABO, "Abo.");
            vecCab.add(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI, "Val.Fav.Cli.");
            vecCab.add(INT_TBL_DAT_COD_RET, "Cod.Ret.");
            vecCab.add(INT_TBL_DAT_POR_RET, "Por.Ret.");
            vecCab.add(INT_TBL_DAT_BTN_RET, "");
            vecCab.add(INT_TBL_DAT_APL_RET, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            //tblDat.setCellSelectionEnabled(true);
            //tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);

            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objTblPopMnu.setPegarEnabled(false);
            objTblPopMnu.setBorrarContenidoEnabled(true);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();

            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_MON).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_ABO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BTN_RET).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_APL_RET).setPreferredWidth(20);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_APL_RET, tblDat);

            objTblCelRenLblNum = new ZafTblCelRenLbl();
            objTblCelRenLblNum.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblNum.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLblNum.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_MON).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_ABO).setCellRenderer(objTblCelRenLblNum);
            tcmAux.getColumn(INT_TBL_DAT_POR_RET).setCellRenderer(objTblCelRenLblNum);

            objTblCelRenLblCod = new ZafTblCelRenLbl();
            objTblCelRenLblCod.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLblCod);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setCellRenderer(objTblCelRenLblCod);
            
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI).setCellRenderer(objTblCelRenChk);
            
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI).setCellEditor(objTblCelEdiChk);
            
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter()
            {   public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    objTblCelEdiChk.setCancelarEdicion(false);
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit");
                }
            });

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK_VAL_RET_FAV_CLI);
            
            if (objParSis.getCodigoUsuario() == 1)
            {
               vecAux.add("" + INT_TBL_DAT_BTN_RET);
            }
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            //Configurar JTable: Editor de la tabla.
            objTblEdi = new ZafTblEdi(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            objTblFilCab = null;

            //botones agregados
            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BTN_RET).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            configurarVenConCli();
            configurarDocumentos();

            ///////Configurar JTable: Detectar cambios de valores en las celdas.
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);

            //Configurar JTable: Editor de celdas.
            int intColVen[] = new int[2];
            intColVen[0] = 1;
            intColVen[1] = 4;
            int intColTbl[] = new int[2];
            intColTbl[0] = INT_TBL_DAT_COD_RET;
            intColTbl[1] = INT_TBL_DAT_POR_RET;

            ZafTblCelRenChk objTblCelRenChk;
            ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new ZafTblCelRenChk();

            objTblCelEdiButVcoTipRet = new ZafTblCelEdiButVco(tblDat, vcoTipRet, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BTN_RET).setCellEditor(objTblCelEdiButVcoTipRet);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BTN_RET, "Tipo Retención") {
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    String strMsg;
                    
                    if (txtNumDoc.getText().equals(""))
                    {  strMsg =  "<HTML>Primero debe poner un Número de documento antes de pulsar<BR>";
                       strMsg += "el botón de búsqueda de Listado de porcentajes de retención</HTML>";
                       mostrarMsgInf(strMsg);
                       txtNumDoc.requestFocus();
                       return;
                    }
                    
                    if (vcoTipRet == null) 
                    {
                       configurarVenConTipRet();
                    }

                    mostrarTipRet(0, intSelFil);
                }
            };
            intColVen = null;
            intColTbl = null;

            objTblOrd = new ZafTblOrd(tblDat);
            objTblBus = new ZafTblBus(tblDat);
            objDocLis = new ZafDocLis();

            //Configurar JTable: Detectar cambios de valores en las celdas.
            objTblModLis = new ZafTblModLis();
            objTblMod.addTableModelListener(objTblModLis);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean consultar() {
        String strAux = "";

        boolean blnRes = true;
        strAux = "";
        try {
            configurarVenConTipRet();
            if (txtNumDoc.getText().length() > 0) {
                cargarCodigosPrincipalesFactura(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(objParSis.getCodigoLocal()), txtCodTipDoc.getText(), txtNumDoc.getText());
                verificaTieneCheque(strCodEmpRee, strCodLocRee, strCodTipDocRee, strCodDocRee);
                verificaTieneAbonos(strCodEmpRee, strCodLocRee, strCodTipDocRee, strCodDocRee);
                verificaTieneRetenciones(strCodEmpRee, strCodLocRee, strCodTipDocRee, strCodDocRee);
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else
            {  txtSubTotBie.setText("");
               txtIvaBie.setText("");
               txtSubTotSer.setText("");
               txtIvaSer.setText("");
               txtValDoc.setText("");
               lblTieAboAsi.setVisible(false);
               lblTieCheAsi.setVisible(false);
               blnTieneAbonos = false;
               blnTieneCheque = false;
               blnTieneRetenciones = false;
               objTblMod.removeAllRows();
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void limpiaTabla() {
        try {
            vecDat.clear();
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            lblTieAboAsi.setVisible(false);
            lblTieCheAsi.setVisible(false);
            blnTieneAbonos = false;
            blnTieneCheque = false;
            blnTieneRetenciones = false;
        } catch (Exception e) {

            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean cargarDetReg_Reestructuracion_normal()
    {
        boolean blnRes = true;
        int intCodDoc;
        String strAux = "", strDesCorTipDoc_Loc, strDesLarTipDoc_Loc, strAplRet, strDesCorPolRet;
        BigDecimal bdePorRet, bdeBasImp, bdeValPag_PagMovInv, bdeValRetCal, bdeDif, bdeMonPag;
        objUti = new ZafUtil();
        strAux = "";
        
        try 
        {
            if (blnTieneAbonos) 
            {
                lblTieAboAsi.setVisible(true);
            } 
            else 
            {
                lblTieAboAsi.setVisible(false);
            }
            
            if (blnTieneCheque) 
            {
                lblTieCheAsi.setVisible(true);
            } 
            else 
            {
                lblTieCheAsi.setVisible(false);
            }

            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con != null)
            {  stm = con.createStatement();
               strDesCorTipDoc_Loc = "";
               strDesLarTipDoc_Loc = "";
               intCodDoc = 0;
               lblMsgSis.setText("Cargando datos...");
               
               //De la tabla tbm_pagmovinv se va a obtener el co_doc, ademas de la descripcion corta y larga del TipDoc.
               strSQL =  " SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc,a4.tx_desCor,a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, a2.co_reg, \n";
               strSQL += "        a2.fe_ven, a2.co_tipRet,a5.tx_desLar,a2.nd_porRet, ROUND(-a2.mo_pag,2) as mo_pag, ROUND(-a2.nd_abo,2) as nd_abo, a2.st_reg,a5.tx_aplRet   \n";
               strSQL += " FROM tbm_cabMovInv as a1  \n";
               strSQL += " INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc \n";
               strSQL += "                                    AND a1.co_doc=a2.co_doc)\n";
               strSQL += " INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
               strSQL += " INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
               strSQL += " INNER JOIN tbm_cabTipRet as a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet) \n";
               strSQL += " WHERE a1.co_emp=" + strCodEmpRee + " and a1.co_loc=" + strCodLocRee + " and \n";
               strSQL += "       a1.co_tipDoc=" + strCodTipDocRee + " and a1.ne_numDoc=" + txtNumDoc.getText() + " and a2.st_reg in('A','C')\n";
               strSQL += " Order by a2.nd_porRet";
               rst = stm.executeQuery(strSQL);
               
               if (rst.next())
               {  strDesCorTipDoc_Loc = rst.getString("tx_desCor");
                  strDesLarTipDoc_Loc = rst.getString("tx_desLar");
                  intCodDoc = rst.getInt("co_doc");
               }
               
               //Se va a traer los Por.Ret. de acuerdo al cliente.
               strSQL =  " SELECT a1.co_tipPer,a1.co_emp, a1.co_cli, a1.tx_nom,a3.nd_porRet,\n";
               strSQL += " a3.co_tipRet, a3.tx_desCor as tx_desCorTipRet, a3.tx_desLar as tx_desLarTipRet, \n";
               strSQL += " a2.fe_vigDes, a2.fe_vigHas, a2.co_pol, a4.tx_desCor as tx_desCorPol, a4.tx_desLar as tx_desLarPol, a3.tx_aplRet \n";
               strSQL += " FROM tbm_cli as a1 \n";
               strSQL += " INNER JOIN tbm_emp as EMP ON (a1.co_emp=EMP.co_emp) \n";
               strSQL += " INNER JOIN tbm_polRet as a2 ON (a1.co_emp=a2.co_emp AND a1.co_tipPer=a2.co_ageRet AND EMP.co_tipPer=a2.co_sujRet) \n";
               strSQL += " INNER JOIN tbm_cabTipRet as a3 ON (a2.co_emp=a3.co_emp AND a2.co_tipRet=a3.co_tipRet) \n";
               strSQL += " INNER JOIN tbm_motDoc as a4 ON (a3.co_emp=a4.co_emp AND a2.co_motTra=a4.co_mot) \n";
               strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.st_reg = 'A' and (a2.fe_vigHas >= current_date OR a2.fe_vigHas is null) AND \n";
               strSQL += "       a4.co_mot in (1, 2) AND a1.co_cli=" + txtCodCli.getText() + "\n";
               strSQL += " ORDER BY a1.co_cli, co_tipRet \n";
               rst = stm.executeQuery(strSQL);
               
               while (rst.next())
               {  strAplRet = rst.getString("tx_aplRet");
                  strDesCorPolRet = rst.getString("tx_desCorPol");
                  bdePorRet = rst.getBigDecimal("nd_porRet");
                  bdePorRet = objUti.redondearBigDecimal(bdePorRet, objParSis.getDecimalesMostrar());
                  
                  if ( ( strDesCorPolRet.equals("COMBIE") && strAplRet.equals("S") && !bdePorRet.equals(new BigDecimal("2.00"))  && !txtSubTotBie.getText().equals("") ) || //El item es un bien.
                       ( strDesCorPolRet.equals("COMSER") && strAplRet.equals("S") &&  bdePorRet.equals(new BigDecimal("2.00"))  && !txtSubTotSer.getText().equals("") ) || //El item es un servicio.
                       ( strDesCorPolRet.equals("COMBIE") && strAplRet.equals("I") && !bdePorRet.equals(new BigDecimal("20.00")) && !txtIvaBie.getText().equals("") ) || //El item es un bien.
                       ( strDesCorPolRet.equals("COMSER") && strAplRet.equals("I") &&  bdePorRet.equals(new BigDecimal("20.00")) && !txtIvaSer.getText().equals("") ) //El item es un servicio.
                     )
                  {  //La funcion getDatos_PagMovInv() se encarga de llenar las variables globales que guardaran valores de ciertos campos de tbm_pagmovinv.
                     getDatos_PagMovInv(bdePorRet);
                     
                     vecReg = new Vector();
                     vecReg.add(INT_TBL_DAT_LIN, "");
                     vecReg.add(INT_TBL_DAT_COD_EMP, strCodEmpRee);
                     vecReg.add(INT_TBL_DAT_COD_LOC, strCodLocRee);
                     vecReg.add(INT_TBL_DAT_COD_TIP_DOC, strCodTipDocRee);
                     vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, strDesCorTipDoc_Loc);
                     vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, strDesLarTipDoc_Loc);
                     vecReg.add(INT_TBL_DAT_COD_DOC, intCodDoc);
                     vecReg.add(INT_TBL_DAT_NUM_DOC, txtNumDoc.getText());
                     vecReg.add(INT_TBL_DAT_FEC_DOC, strFecVen_Glo);

                     bdeBasImp = new BigDecimal(0);

                     if (strAplRet.equals("S"))
                     {  if (!bdePorRet.equals(new BigDecimal("2.00")))
                        {  //El item corresponde a un bien.
                           bdeBasImp = txtSubTotBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotBie.getText());
                        }
                        else
                        {  //El item corresponde a un servicio.
                           bdeBasImp = txtSubTotSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotSer.getText());
                        }
                     }
                     else if (strAplRet.equals("I"))
                     {  if (!bdePorRet.equals(new BigDecimal("20.00")))
                        {  //El item corresponde a un bien.
                           bdeBasImp = txtIvaBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaBie.getText());
                        }
                        else
                        {  //El item corresponde a un servicio.
                           bdeBasImp = txtIvaSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaSer.getText());
                        }
                     }
                     
                     bdeBasImp = objUti.redondearBigDecimal(bdeBasImp, objParSis.getDecimalesMostrar());
                     //Se va a calcular el Valor_retencion tomando como base imponible los JTextField txtSubTot o txtIva, segun sea el caso.
                     bdeValRetCal = bdeBasImp.multiply(bdePorRet.divide(new BigDecimal(100)));
                     bdeValRetCal = objUti.redondearBigDecimal(bdeValRetCal, objParSis.getDecimalesMostrar());
                     vecReg.add(INT_TBL_DAT_MON, bdeValRetCal);
                     
                     vecReg.add(INT_TBL_DAT_ABO, strValAbo_Glo);
                     vecReg.add(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI, null);
                     vecReg.add(INT_TBL_DAT_COD_RET, rst.getString("co_tipRet"));
                     vecReg.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porRet"));
                     vecReg.add(INT_TBL_DAT_BTN_RET, "");
                     vecReg.add(INT_TBL_DAT_APL_RET, rst.getString("tx_aplRet"));
                     vecDat.add(vecReg);
                  } //if ( ( strDesCorPolRet.equals("COMBIE") && strAplRet.equals("S") && !bdePorRet.equals(new BigDecimal("2.00"))  && !txtSubTotBie.getText().equals("") )...
               } //while (rst.next())
            } //if (con != null)
            
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
            if (blnCon) {
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            } else {
                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion cargarDetReg_Reestructuracion_normal()
    
    private boolean cargarDetReg_PonerValRetSaldoFavorCliente()
    {
        String strAux = "";
        objUti = new ZafUtil();
        boolean blnRes = true;
        strAux = "";
        BigDecimal bdePorRet, bdeBasImp, bdeValPag_PagMovInv, bdeValRetCal, bdeDif, bdeMonPag;
        
        try 
        {
            if (blnTieneAbonos)
            {
                lblTieAboAsi.setVisible(true);
            }
            else 
            {
                lblTieAboAsi.setVisible(false);
            }
            
            if (blnTieneCheque) 
            {
                lblTieCheAsi.setVisible(true);
            }
            else 
            {
                lblTieCheAsi.setVisible(false);
            }

            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = " ";
                strSQL += " SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc,a4.tx_desCor,a4.tx_desLar, a1.co_doc, a1.ne_numDoc, a1.fe_doc, a1.co_cli, a1.tx_nomCli, a2.co_reg, \n";
                strSQL += "        a2.fe_ven, a2.co_tipRet,a5.tx_desLar,a2.nd_porRet, ROUND(-a2.mo_pag,2) as mo_pag, ROUND(-a2.nd_abo,2) as nd_abo, a2.st_reg,a5.tx_aplRet   \n";
                strSQL += " FROM tbm_cabMovInv as a1  \n";
                strSQL += " INNER JOIN tbm_pagMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc \n";
                strSQL += "                                    AND a1.co_doc=a2.co_doc)\n";
                strSQL += " INNER JOIN tbm_cli as a3 ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli) \n";
                strSQL += " INNER JOIN tbm_cabTipDoc as a4 ON (a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipDoc=a4.co_tipDoc) \n";
                strSQL += " INNER JOIN tbm_cabTipRet as a5 ON (a2.co_emp=a5.co_emp AND a2.co_tipRet=a5.co_tipRet) \n";
                strSQL += " WHERE a1.co_emp=" + strCodEmpRee + " and a1.co_loc=" + strCodLocRee + " and \n";
                strSQL += "       a1.co_tipDoc=" + strCodTipDocRee + " and a1.ne_numDoc=" + txtNumDoc.getText() + " AND a2.nd_porRet > 0 and a2.st_reg in('A','C')\n";
                strSQL += " Order by a2.nd_porRet";
                //System.out.println(strSQL);
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_ven"));
                    
                    bdeBasImp = new BigDecimal(0);
                    bdePorRet = rst.getBigDecimal("nd_porRet");
                    bdePorRet = objUti.redondearBigDecimal(bdePorRet, objParSis.getDecimalesMostrar());
                    
                    if (rst.getString("tx_aplRet").equals("S"))
                    {  if (!bdePorRet.equals(new BigDecimal("2.00")))
                       {  //El item corresponde a un bien.
                          bdeBasImp = txtSubTotBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotBie.getText());
                       }
                       else
                       {  //El item corresponde a un servicio.
                          bdeBasImp = txtSubTotSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotSer.getText());
                       }
                    }
                    else if (rst.getString("tx_aplRet").equals("I"))
                    {  if (!bdePorRet.equals(new BigDecimal("20.00")))
                       {  //El item corresponde a un bien.
                          bdeBasImp = txtIvaBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaBie.getText());
                       }
                       else
                       {  //El item corresponde a un servicio.
                          bdeBasImp = txtIvaSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaSer.getText());
                       }
                    }
                    
                    bdeBasImp = objUti.redondearBigDecimal(bdeBasImp, objParSis.getDecimalesMostrar());
                    //Se va a calcular el Valor_retencion tomando como base imponible los JTextField txtSubTot o txtIva, segun sea el caso.
                    bdeValRetCal = bdeBasImp.multiply(bdePorRet.divide(new BigDecimal(100)));
                    bdeValRetCal = objUti.redondearBigDecimal(bdeValRetCal, objParSis.getDecimalesMostrar());
                    bdeValPag_PagMovInv = rst.getBigDecimal("mo_pag");
                    bdeValPag_PagMovInv = objUti.redondearBigDecimal(bdeValPag_PagMovInv, objParSis.getDecimalesMostrar());
                    bdeDif = bdeValPag_PagMovInv.subtract(bdeValRetCal).abs();
                    bdeDif = objUti.redondearBigDecimal(bdeDif, objParSis.getDecimalesMostrar());
                    
                    if ( !bdeValPag_PagMovInv.equals(bdeValRetCal) && bdeDif.equals(new BigDecimal("0.01")) )
                    {  //Puede ser que el valor de tbm_pagmovinv.mo_pag y el valor calculado de la retencion no sea el mismo. En este caso, si la diferencia
                       //entre ambos valores es 0.01, entonces se concluye que el valor correcto que debera aparecer en el JTable es el valor calculado.
                       bdeMonPag = bdeValRetCal;
                    }
                    else
                    {  
                       bdeMonPag = rst.getBigDecimal("mo_pag");
                    }
                    
                    vecReg.add(INT_TBL_DAT_MON, bdeMonPag);
                    
                    vecReg.add(INT_TBL_DAT_ABO, rst.getString("nd_abo"));
                    vecReg.add(INT_TBL_DAT_CHK_VAL_RET_FAV_CLI, null);
                    vecReg.add(INT_TBL_DAT_COD_RET, rst.getString("co_tipRet"));
                    vecReg.add(INT_TBL_DAT_POR_RET, rst.getString("nd_porRet"));
                    vecReg.add(INT_TBL_DAT_BTN_RET, "");
                    vecReg.add(INT_TBL_DAT_APL_RET, rst.getString("tx_aplRet"));
                    vecDat.add(vecReg);
                }
            }
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();
            if (blnCon) {
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
            } else {
                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            butCon.setText("Consultar");
            pgrSis.setIndeterminate(false);
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion cargarDetReg_PonerValRetSaldoFavorCliente()

//    /**
//     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
//     * del mouse (mover el mouse; arrastrar y soltar).
//     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
//     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
//     * resulta muy corto para mostrar leyendas que requieren más espacio.
//     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg = "Código del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de Documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg = "Descripción corta del tipo de Documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg = "Descripción larga del tipo de Documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número del documento";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_MON:
                    strMsg = "Monto";
                    break;
                case INT_TBL_DAT_ABO:
                    strMsg = "Valor Abonado";
                    break;
                case INT_TBL_DAT_CHK_VAL_RET_FAV_CLI:
                    strMsg = "Poner valor de retención como saldo a favor del cliente";
                    break;
                case INT_TBL_DAT_COD_RET:
                    strMsg = "Código de la retención";
                    break;
                case INT_TBL_DAT_POR_RET:
                    strMsg = "Porcentaje de retención";
                    break;
                case INT_TBL_DAT_BTN_RET:
                    strMsg = "Mostrar los tipos de retención";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

//    /**
//     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
//     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
//     * debe llenar o corregir.
//     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

//    /**
//     * Esta clase implementa la interface DocumentListener que observa los cambios que
//     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
//     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
//     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
//     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
//     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
//     */
    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }

    private class ZafThreadGUI extends Thread {

        public void run() {
            if (rdaReeNor.isSelected() && cargarDetReg_Reestructuracion_normal() == true)
            {
               lblMsgSis.setText("Listo");
               pgrSis.setValue(0);
               butCon.setText("Consultar");
            }
            else if (rdaPonValRetFavCli.isSelected() && cargarDetReg_PonerValRetSaldoFavorCliente() == true)
            {
               lblMsgSis.setText("Listo");
               pgrSis.setValue(0);
               butCon.setText("Consultar");
            }
            objThrGUI = null;
        }
    }

//    /**
//     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
//     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
//     * seleccionando una de las opciones que se presentan.
//     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

//     /**
//     * Esta clase hereda de la interface TableModelListener que permite determinar
//     * cambios en las celdas del JTable.
//     */
    private class ZafTblModLis implements javax.swing.event.TableModelListener {

        public void tableChanged(javax.swing.event.TableModelEvent e) {
            switch (e.getType()) {
                case javax.swing.event.TableModelEvent.INSERT:
                    break;
                case javax.swing.event.TableModelEvent.DELETE:
                    break;
                case javax.swing.event.TableModelEvent.UPDATE:
                    break;
            }
        }
    }

    private boolean mostrarDocumentos(int intTipBus) {
        
        boolean blnRes = true;
        
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoDoc.setCampoBusqueda(0);
                    vcoDoc.setVisible(true);
                    if (vcoDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                    {   txtCodTipDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoDoc.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Busqueda por descripcion corta".
                    if (vcoDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {   txtCodTipDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoDoc.getValueAt(3));
                    } 
                    else 
                    {   vcoDoc.setCampoBusqueda(1);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                        {   txtCodTipDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {   txtCodTipDoc.setText(strCodTipDoc);
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {   txtCodTipDoc.setText(vcoDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoDoc.getValueAt(3));
                    }
                    else
                    {   vcoDoc.setCampoBusqueda(2);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                        if (vcoDoc.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                        {   txtCodTipDoc.setText(vcoDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoDoc.getValueAt(3));
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            } //switch (intTipBus)
            
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean mostrarVenConCli(int intTipBus){
        
        boolean blnRes=true;
        
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {   txtCodCli.setText(vcoCli.getValueAt(1));
                        //strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        //strDirCli=vcoCli.getValueAt(4);
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText()))
                    {   txtCodCli.setText(vcoCli.getValueAt(1));
                        //strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        //strDirCli=vcoCli.getValueAt(4);
                    }
                    else
                    {   vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {   txtCodCli.setText(vcoCli.getValueAt(1));
                            //strIdeCli=vcoCli.getValueAt(2);
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                            //strDirCli=vcoCli.getValueAt(4);
                        }
                        else
                        {
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarCli.getText()))
                    {   txtCodCli.setText(vcoCli.getValueAt(1));
                        //strIdeCli=vcoCli.getValueAt(2);
                        txtDesLarCli.setText(vcoCli.getValueAt(3));
                        //strDirCli=vcoCli.getValueAt(4);
                    }
                    else
                    {   vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {   txtCodCli.setText(vcoCli.getValueAt(1));
                            //strIdeCli=vcoCli.getValueAt(2);
                            txtDesLarCli.setText(vcoCli.getValueAt(3));
                            //strDirCli=vcoCli.getValueAt(4);
                        }
                        else
                        {
                            txtDesLarCli.setText(strDesLarCli);
                        }
                    }
                    break;
            } //switch (intTipBus)
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Funcion mostrarVenConCli()

    /*
     * llenar la consulta de Tipos de Documentos
     */
    private boolean configurarDocumentos() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_deslar");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Des.Cor.");
            arlAli.add("Des.Lar.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("100");
            arlAncCol.add("350");
            //Si es el usuario Administrador (Código=1) tiene acceso a todos los tipos de documentos.
            if (objParSis.getCodigoUsuario() == 1)
            {   //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            else
            {   //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1";
                strSQL += " INNER JOIN tbr_tipDocUsr AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " ORDER BY a1.tx_desCor";
            }
            vcoDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Tipos de Documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void consultarFacturas() 
    {
        strCodTipDoc = txtCodTipDoc.getText();
        mostrarFacturas(0);
    }

   //  mostrar Facturas o Ordenes de compra 
    private boolean mostrarFacturas(int intTipBus) {
        
        boolean blnRes = true;
        
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoFac.setCampoBusqueda(0);
                    vcoFac.setVisible(true);
                    if (vcoFac.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                    {   SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                        String strFecha = vcoFac.getValueAt(2);
                        strFecha = strFecha.replace("-", "/");
                        Date datFec = null;
                        datFec = formato.parse(strFecha);
                        txtNumDoc.setText(vcoFac.getValueAt(1));
                        dtpFecDoc.setText(objUti.formatearFecha(datFec, "dd/MM/yyyy"));
                        txtValDoc.setText(vcoFac.getValueAt(7));
                        getSubtotal_Iva_Bienes_Servicios();
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoFac.buscar("a1.ne_numDoc", txtNumDoc.getText()))
                    {   SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                        String strFecha = vcoFac.getValueAt(2);
                        strFecha = strFecha.replace("-", "/");
                        Date datFec = null;
                        datFec = formato.parse(strFecha);
                        txtNumDoc.setText(vcoFac.getValueAt(1));
                        dtpFecDoc.setText(objUti.formatearFecha(datFec, "dd/MM/yyyy"));
                        txtValDoc.setText(vcoFac.getValueAt(7));
                        getSubtotal_Iva_Bienes_Servicios();
                    }
                    else 
                    {   txtSubTotBie.setText("");
                        txtIvaBie.setText("");
                        txtSubTotSer.setText("");
                        txtIvaSer.setText("");
                        txtValDoc.setText("");
                        lblTieAboAsi.setVisible(false);
                        lblTieCheAsi.setVisible(false);
                        blnTieneAbonos = false;
                        blnTieneCheque = false;
                        blnTieneRetenciones = false;
                        objTblMod.removeAllRows();
                        vcoFac.setCampoBusqueda(0);
                        vcoFac.setCriterio1(11);
                        vcoFac.cargarDatos();
                        vcoFac.setVisible(true);
                        if (vcoFac.getSelectedButton() == ZafVenCon.INT_BUT_ACE) 
                        {   SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
                            String strFecha = vcoFac.getValueAt(2);
                            strFecha = strFecha.replace("-", "/");
                            Date datFec = null;
                            datFec = formato.parse(strFecha);

                            txtNumDoc.setText(vcoFac.getValueAt(1));
                            dtpFecDoc.setText(objUti.formatearFecha(datFec, "dd/MM/yyyy"));
                            txtValDoc.setText(vcoFac.getValueAt(7));
                            getSubtotal_Iva_Bienes_Servicios();
                        }
                        else
                        {   txtNumDoc.setText(strNumDoc);
                            
                            if (txtValDoc.getText().equals(""))
                            {  txtNumDoc.setText("");
                               txtSubTotBie.setText("");
                               txtIvaBie.setText("");
                               txtSubTotSer.setText("");
                               txtIvaSer.setText("");
                               txtValDoc.setText("");
                               lblTieAboAsi.setVisible(false);
                               lblTieCheAsi.setVisible(false);
                               blnTieneAbonos = false;
                               blnTieneCheque = false;
                               blnTieneRetenciones = false;
                               objTblMod.removeAllRows();
                            }
                        }
                    } //if (vcoFac.buscar("a1.ne_numDoc", txtNumDoc.getText()))
                    break;
            } //switch (intTipBus)
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /*
     * llenar la consulta de Numero de Documentos
     */
    private boolean configurarFacturas() {
        
        boolean blnRes = true;
        
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            //arlCam.add("a1.co_emp");//1
            //arlCam.add("a1.co_loc");//2
            //arlCam.add("a1.co_tipDoc");//3
            //arlCam.add("a1.co_doc");//4
            arlCam.add("a1.ne_numDoc");//5
            arlCam.add("a1.fe_doc");//6
            arlCam.add("a1.co_cli");//7
            arlCam.add("a1.tx_nomCli");//8
            arlCam.add("a1.nd_valIva");//9
            arlCam.add("a1.nd_sub");//10
            arlCam.add("a1.nd_tot");//11
            //arlCam.add("a1.co_tipPer");//12
            
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            //arlAli.add("Empresa");
            //arlAli.add("Local");
            //arlAli.add("Tip.Doc.");
            //arlAli.add("Cód.Doc.");
            arlAli.add("Num.Doc.");
            arlAli.add("Fec.Doc.");
            arlAli.add("Cód.Cli.");
            arlAli.add("Nom.Cli.");
            arlAli.add("IVA");
            arlAli.add("Subtotal");
            arlAli.add("Total");
            //arlAli.add("Cód.Tip.Ret.");
            
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            // arlAncCol.add("50");//1
            // arlAncCol.add("50");//2
            // arlAncCol.add("50"); //3
            // arlAncCol.add("50");//4
            arlAncCol.add("60"); // SI Numero de Documento  //5
            arlAncCol.add("70"); // SI Fecha de Documento  //6
            arlAncCol.add("50");//7
            arlAncCol.add("200"); // SI Nombre del Cliente  //8
            arlAncCol.add("60"); // SI IVA //9
            arlAncCol.add("60"); //SI SubTotal  //10
            arlAncCol.add("60"); // SI TOtal //11
            //arlAncCol.add("50"); //12
            
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            //intColOcu[0]=1;  // co_emp
            //intColOcu[1]=2;  // co_loc
            //intColOcu[2]=3;  // co_tipDoc
            //intColOcu[3]=4;  // co_doc
            intColOcu[0] = 7;  // co_cli
            //intColOcu[5]=12;  // co_tipPer
            strSQL = "";
            //strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,a1.fe_doc, a1.co_cli, a1.tx_nomCli,\n";
            strSQL += " SELECT a1.ne_numDoc,a1.fe_doc, a1.co_cli, a1.tx_nomCli,\n";
            strSQL += "         ROUND(-nd_sub,2) as nd_sub, ROUND(-nd_tot,2) as nd_tot, ROUND(-nd_valIva,2) as nd_valIva, a2.co_tipPer \n ";
            strSQL += " FROM tbm_cabMovInv as a1 \n";
            strSQL += " INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " and a1.co_loc=" + objParSis.getCodigoLocal() + " \n";
            strSQL += "   AND a1.co_tipDoc=" + txtCodTipDoc.getText();
            strSQL += "   AND a1.co_cli=" + txtCodCli.getText();
            strSQL += "   AND a1.st_reg IN ('A') \n";
            strSQL += " ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc \n";
            vcoFac = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoFac.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean validaDocumentos() {
        boolean blnRes = true;
        String strTit = "Mensaje del sistema Zafiro", strMsg;
        
        if (!(txtCodTipDoc.getText().length() > 0))
        {   txtNumDoc.setText("");
            strMsg = "<HTML>El Tipo de documento es obligatorio antes de buscar los documentos</HTML>";
            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if (!(txtCodCli.getText().length() > 0))
        {   txtNumDoc.setText("");
            strMsg = "<HTML>El cliente es obligatorio antes de buscar los documentos</HTML>";
            JOptionPane.showMessageDialog(this, strMsg, strTit, JOptionPane.INFORMATION_MESSAGE);
            txtCodCli.requestFocus();
            return false;
        }
        
        return blnRes;
    }
    
    private boolean configurarVenConCli(){
        boolean blnRes=true;
        String strTitVenCon="";
        try{
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
            
            strTitVenCon="Listado de Clientes";
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
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
    } //Funcion configurarVenConCli()

//    /**
//     * Esta función configura la "Ventana de consulta" que será utilizada para
//     * mostrar la "Unidad de medida".
//     */
    private boolean configurarVenConTipRet() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipRet");//1
            arlCam.add("a1.tx_desCorTipRet");//2
            arlCam.add("a1.tx_desLarTipRet");//3
            arlCam.add("a1.nd_porRet");//4
            arlCam.add("a1.co_pol");//5
            arlCam.add("a1.tx_desCorPol");//6
            arlCam.add("a1.tx_desLarPol");//7
            arlCam.add("a1.tx_aplRet");//8
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Cód.Tip.Ret.");
            arlAli.add("Des.Cor.Tip.Ret.");
            arlAli.add("Des.Lar.Tip.Ret.");
            arlAli.add("Por.Ret.");
            arlAli.add("Cód.Pol.");
            arlAli.add("Des.Cor.Pol.");
            arlAli.add("Des.Lar.Pol.");
            arlAli.add("Aplica");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("0");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += " SELECT a1.co_tipPer,a1.co_emp, a1.co_cli, a1.tx_nom,a3.nd_porRet,\n";
            strSQL += " a3.co_tipRet, a3.tx_desCor as tx_desCorTipRet, a3.tx_desLar as tx_desLarTipRet, \n";
            strSQL += " a2.fe_vigDes, a2.fe_vigHas, a2.co_pol, a4.tx_desCor as tx_desCorPol, a4.tx_desLar as tx_desLarPol, a3.tx_aplRet \n";
            strSQL += " FROM tbm_cli as a1 \n";
            strSQL += " INNER JOIN tbm_emp as EMP ON (a1.co_emp=EMP.co_emp) \n";
            strSQL += " INNER JOIN tbm_polRet as a2 ON (a1.co_emp=a2.co_emp AND a1.co_tipPer=a2.co_ageRet AND EMP.co_tipPer=a2.co_sujRet) \n";
            strSQL += " INNER JOIN tbm_cabTipRet as a3 ON (a2.co_emp=a3.co_emp AND a2.co_tipRet=a3.co_tipRet) \n";
            strSQL += " INNER JOIN tbm_motDoc as a4 ON (a3.co_emp=a4.co_emp AND a2.co_motTra=a4.co_mot) \n";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a2.st_reg = 'A' and (a2.fe_vigHas >= current_date OR a2.fe_vigHas is null) AND \n";
            strSQL += "       a4.co_mot in (1, 2) AND a1.co_cli=" + txtCodCli.getText() + "\n";
            strSQL += " ORDER BY a1.co_cli, co_tipRet \n";
            vcoTipRet = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Porcentajes", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipRet.setConfiguracionColumna(2, javax.swing.JLabel.CENTER);
            vcoTipRet.setCampoBusqueda(1);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarTipRet(int intTipBus, int intSelFil) {
        
       boolean blnRes = true;
        
        try 
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipRet.setCampoBusqueda(0);
                    vcoTipRet.setVisible(true);
                    if (vcoTipRet.getSelectedButton() == ZafVenCon.INT_BUT_ACE)
                    {   boolean booVerRet = true;
                        String strTipRet = "";
                        //Validación para verificar que no se repita el tipo de retención
                        for (int i = 0; i < tblDat.getRowCount(); i++)
                        {   if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_RET) != null)
                            {   if (objTblMod.getValueAt(i, INT_TBL_DAT_COD_RET).equals(vcoTipRet.getValueAt(1)))
                                {
                                    booVerRet = false;
                                }
                            }
                        }

                        if (vcoTipRet.getValueAt(8).equals("S")) {
                            strTipRet = "S";
                        } else if (vcoTipRet.getValueAt(8).equals("I")) {
                            strTipRet = "I";
                        }
                        
                        //(booAplRetSub && booAplRetIva) || para cuando no tiene aplicada retención 
                        if ( (booVerRet && strTipRet.equals("I")) || (booVerRet && strTipRet.equals("S")) ) 
                        {   if (vcoTipRet.getValueAt(8).equals("S")) 
                            {   BigDecimal bdeSubTot;
                                BigDecimal bdePor = new BigDecimal(vcoTipRet.getValueAt(4));
                                bdePor = objUti.redondearBigDecimal(bdePor, objParSis.getDecimalesMostrar());
                                
                                if (!bdePor.equals(new BigDecimal("2.00")))
                                {  //El item corresponde a un bien
                                   bdeSubTot = txtSubTotBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotBie.getText());
                                }
                                else
                                {  //El item corresponde a un servicio
                                   bdeSubTot = txtSubTotSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtSubTotSer.getText());
                                }  
                                   
                                BigDecimal bdeTot = bdeSubTot.multiply(bdePor).divide(new BigDecimal(100));
                                if (intSelFil==-1) {
                                    intSelFil=0;
                                }
                                
                                objTblMod.setValueAt(bdeTot, intSelFil, INT_TBL_DAT_MON);
                            }
                            else if (vcoTipRet.getValueAt(8).equals("I"))
                            {   BigDecimal bdeIva;
                                BigDecimal bdePor = new BigDecimal(vcoTipRet.getValueAt(4));
                                bdePor = objUti.redondearBigDecimal(bdePor, objParSis.getDecimalesMostrar());
                                
                                if (!bdePor.equals(new BigDecimal("20.00")))
                                {  //El item corresponde a un bien
                                   bdeIva = txtIvaBie.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaBie.getText());
                                }
                                else
                                {  //El item corresponde a un servicio
                                   bdeIva = txtIvaSer.getText().trim().equals("")? new BigDecimal(0) :new BigDecimal(txtIvaSer.getText());
                                }
                                
                                BigDecimal bdeTot = bdeIva.multiply(bdePor).divide(new BigDecimal(100));
                                
                                objTblMod.setValueAt(bdeTot, intSelFil, INT_TBL_DAT_MON);
                            }
                            objTblMod.setValueAt(vcoTipRet.getValueAt(1), intSelFil, INT_TBL_DAT_COD_RET);
                            objTblMod.setValueAt(vcoTipRet.getValueAt(4), intSelFil, INT_TBL_DAT_POR_RET);
                            objTblMod.setValueAt(vcoTipRet.getValueAt(8), intSelFil, INT_TBL_DAT_APL_RET);
                            objTblMod.setValueAt("M", intSelFil, INT_TBL_DAT_LIN);
                            //Sirve para crear una copia del registro anterior para que solo cambie el tipo de retención mas no los datos claves de la factura.
                            objTblMod.setValueAt(strCodLocRee, intSelFil, INT_TBL_DAT_COD_LOC);
                            objTblMod.setValueAt(strCodEmpRee, intSelFil, INT_TBL_DAT_COD_EMP);
                            objTblMod.setValueAt(txtCodTipDoc.getText(), intSelFil, INT_TBL_DAT_COD_TIP_DOC);
                            objTblMod.setValueAt(txtDesCorTipDoc.getText(), intSelFil, INT_TBL_DAT_DES_COR_TIP_DOC);
                            objTblMod.setValueAt(txtDesLarTipDoc.getText(), intSelFil, INT_TBL_DAT_DES_LAR_TIP_DOC);
                            objTblMod.setValueAt(strCodDocRee, intSelFil, INT_TBL_DAT_COD_DOC);
                            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
                            String strFecha = dtpFecDoc.getText();
                            strFecha = strFecha.replace("/", "-");
                            Date datFec = null;
                            datFec = formato.parse(strFecha);
                            objTblMod.setValueAt(objUti.formatearFecha(datFec, "yyyy-MM-dd"), intSelFil, INT_TBL_DAT_FEC_DOC);
                            objTblMod.setValueAt(txtNumDoc.getText(), intSelFil, INT_TBL_DAT_NUM_DOC);
                        } //if ( (booVerRet && strTipRet.equals("I")) || (booVerRet && strTipRet.equals("S")) ) 
                    } //if (vcoTipRet.getSelectedButton() == ZafVenCon.INT_BUT_ACE)

                    break;
                case 1: //Búsqueda directa por "Busqueda por descripcion corta".
                    if (vcoTipRet.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) 
                    {   //txtCodTipDoc.setText(vcoTipRet.getValueAt(1));
                        //txtDesCorTipDoc.setText(vcoTipRet.getValueAt(2));
                        //txtDesLarTipDoc.setText(vcoTipRet.getValueAt(3));
                    }
                    else 
                    {   vcoTipRet.setCampoBusqueda(1);
                        vcoTipRet.setCriterio1(11);
                        vcoTipRet.cargarDatos();
                        vcoTipRet.setVisible(true);
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipRet.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {   //txtCodTipDoc.setText(vcoTipRet.getValueAt(1));
                        //txtDesCorTipDoc.setText(vcoTipRet.getValueAt(2));
                        //txtDesLarTipDoc.setText(vcoTipRet.getValueAt(3));
                    }
                    else
                    {   vcoDoc.setCampoBusqueda(2);
                        vcoDoc.setCriterio1(11);
                        vcoDoc.cargarDatos();
                        vcoDoc.setVisible(true);
                    }
                    break;
            } //switch (intTipBus)
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private Boolean verificaTieneCheque(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        Boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += " select *\n";
            strSql += " from tbr_detRecDocPagMovInv\n";
            strSql += " where co_empRel = " + strCodEmp;
            strSql += " and co_locRel = " + strCodLoc;
            strSql += " and co_tipdocRel = " + strCodTipDoc;
            strSql += " and co_docRel = " + strCodDoc;
            strSql += " and st_reg='A'";
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next())
            {
               blnTieneCheque = true;
            }
            blnRes = blnTieneCheque;
            stmLoc.close();
            rstLoc.close();
            conLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            blnRes = false;
        }
        return blnRes;
    }

    private Boolean cargarCodigosPrincipalesFactura(String strCodEmp, String strCodLoc, String strCodTipDoc, String strNumDoc) {
        Boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += " select co_emp,co_loc,co_tipdoc,co_doc\n";
            strSql += " from tbm_cabmovinv \n";
            strSql += " where co_emp = " + strCodEmp;
            strSql += " and co_loc = " + strCodLoc;
            strSql += " and co_tipdoc = " + strCodTipDoc;
            strSql += " and ne_numdoc = " + strNumDoc;
            strSql += " and st_reg='A'";
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next())
            {  strCodEmpRee = rstLoc.getString("co_emp");
               strCodLocRee = rstLoc.getString("co_loc");
               strCodTipDocRee = rstLoc.getString("co_tipdoc");
               strCodDocRee = rstLoc.getString("co_doc");
               blnRes = true;
            }
            blnRes = blnTieneCheque;
            stmLoc.close();
            rstLoc.close();
            conLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            blnRes = false;
        }
        return blnRes;
    }

    private Boolean verificaTieneAbonos(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        Boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += " select *\n";
            strSql += " from tbm_detpag\n";
            strSql += " where co_emp = " + strCodEmp;
            strSql += " and co_locpag = " + strCodLoc;
            strSql += " and co_tipdocpag = " + strCodTipDoc;
            strSql += " and co_docpag = " + strCodDoc;
            strSql += " and st_reg='A'";
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next())
            {
               blnTieneAbonos = true;
            }
            blnRes = blnTieneAbonos;
            stmLoc.close();
            rstLoc.close();
            conLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            blnRes = false;
        }
        return blnRes;
    }

    private Boolean verificaTieneRetenciones(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        Boolean blnRes = false;
        Connection conLoc;
        Statement stmLoc;
        ResultSet rstLoc;
        String strSql;
        try {
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            stmLoc = conLoc.createStatement();
            strSql = " ";
            strSql += " select round(mo_pag+nd_abo,2) as esPagado,a1.* from tbm_pagmovinv as a1";
            strSql += " inner join tbm_cabMovInv as a2 on(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
            strSql += " where a1.co_emp = " + strCodEmp;
            strSql += " and a1.co_loc = " + strCodLoc;
            strSql += " and a1.co_tipdoc = " + strCodTipDoc;
            strSql += " and a1.co_doc = " + strCodDoc;
            strSql += " AND a1.nd_porRet > 0 and a1.st_reg in('A','C')";
            strSql += " ORDER BY a1.nd_porRet";
            rstLoc = stmLoc.executeQuery(strSql);
            if (rstLoc.next()) 
            {
               blnTieneRetenciones = true;
            }
            blnRes = blnTieneRetenciones;
            stmLoc.close();
            rstLoc.close();
            conLoc.close();
            rstLoc = null;
            conLoc = null;
            stmLoc = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            blnRes = false;
        }
        return blnRes;
    }
    
    /**
     * Esta función limpia la ventana de consulta. Es decir, la ventana de consulta
     * queda como si todavía no se hubiera consultado nada.
     */
    public void limpiar()
    {
        txtCodTipDoc.setText("");
        txtDesCorTipDoc.setText("");
        txtDesLarTipDoc.setText("");
        txtCodCli.setText("");
        txtDesLarCli.setText("");
        txtNumDoc.setText("");
        txtSubTotBie.setText("");
        txtIvaBie.setText("");
        txtSubTotSer.setText("");
        txtIvaSer.setText("");
        txtValDoc.setText("");
        chkMosItmCha.setSelected(false);
        lblTieAboAsi.setVisible(false);
        lblTieCheAsi.setVisible(false);
        blnTieneAbonos = false;
        blnTieneCheque = false;
        blnTieneRetenciones = false;
        objTblMod.removeAllRows();
        lblMsgSis.setText("Listo");
        butCon.setText("Consultar");
        pgrSis.setIndeterminate(false);
    }
    
    private boolean guardar()
    {
       boolean blnRes = true;
       
       try
       {
          con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
          if (con != null)
          {  con.setAutoCommit(false);
             if (rdaReeNor.isSelected() && guardarDatos_Reestructuracion_normal() == true)
             {
                con.commit();
                con.close();
                con = null;
                cargarDetReg_PonerValRetSaldoFavorCliente();
             }
             else if (rdaPonValRetFavCli.isSelected() && guardarDatos_PonerValRetSaldoFavorCliente() == true)
             {
                con.commit();
                con.close();
                con = null;
                cargarDetReg_PonerValRetSaldoFavorCliente();
             }
             else
             {
                con.rollback();
                con.close();
                con = null;
                blnRes = false;
             }
          }
       }
        
       catch (java.sql.SQLException e)
       {
          objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
        
       catch (Exception e)
       {
          objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    } //Funcion guardar()
    
    private boolean guardarDatos_Reestructuracion_normal()
    {
       boolean blnRes = true;
       int i, intNumPag, intCodReg_PagMovInv, intUltCodReg_PagMovInv;
       String strAux, strObs, strFecSis, strPorRet_JTb, strAplRet_JTb, strCodTipRet_JTb;
       BigDecimal bdeAux, bdePorRet_PagMovInv, bdeValPag_PagMovInv, bdeValAbo_PagMovInv, bdePorRet_JTb, bdeValRet_JTb, bdeSumRet, bdeValPagSinRet;
       boolean blnPorRetFound;
       Statement stmLoc1, stmLoc2;
       ResultSet rstLoc1, rstLoc2;
       
       try
       {  
          if (con != null)
          {  stmLoc1 = con.createStatement();
             stmLoc2 = con.createStatement();
             rstLoc2 = null;
             datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
             strFecSis = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
             strObs = "Reestructurado por programa Reestructuracion de retenciones";
             
             //**********************************
             //*** PASO 1 ***********************
             //En este paso, de la tabla tbm_pagmovinv se van a actualizar los registros donde el campo tbm_pagmovinv.st_reg sea 'F'. Como se va a 
             //reestructurar, este campo debera tener el valor 'I'.
             //**********************************
             strSQL =  "UPDATE tbm_pagmovinv ";
             strSQL += "SET st_reg = 'I',";
             strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
             strSQL += " fe_ree = '" + strFecSis + "',";
             strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
             strSQL += " tx_obs1 = '" + strObs + "' ";
             strSQL += "WHERE st_reg = 'F' and co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             stmLoc1.executeUpdate(strSQL);
             
             //**********************************
             //*** PASO 2 ***********************
             //En este paso, de la tabla tbm_pagmovinv se van a traer los registros que tengan valores de retenciones. Por cada reg. de tbm_pagmovinv se lo va
             //a buscar en el Jtable. Si ambos (tbm_pagmovinv y JTable) tienen el mismo Por.Ret., entonces se procede a verificar si el Val.Ret. es diferente. 
             //En caso de ser diferente, se procede a reestructurar.
             //**********************************
             //De la tabla tbm_pagmovinv se van a traer los registros que tengan valores de retenciones.
             strSQL =  "SELECT * ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             strSQL += " and nd_porret > 0 and st_reg in ('A','C')";
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             while (rstLoc1.next())
             {  bdePorRet_PagMovInv = rstLoc1.getBigDecimal("nd_porret");
                bdePorRet_PagMovInv = objUti.redondearBigDecimal(bdePorRet_PagMovInv, objParSis.getDecimalesMostrar());
                intCodReg_PagMovInv = rstLoc1.getInt("co_reg");
                blnPorRetFound = false;
                
                for (i = 0; i < tblDat.getRowCount(); i++)
                {  strPorRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_POR_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_POR_RET).toString();
                   
                   if (!strPorRet_JTb.equals(""))
                   {  bdePorRet_JTb = new BigDecimal(strPorRet_JTb);
                      bdePorRet_JTb = objUti.redondearBigDecimal(bdePorRet_JTb, objParSis.getDecimalesMostrar());
                   
                      if (bdePorRet_PagMovInv.equals(bdePorRet_JTb))
                      {  blnPorRetFound = true;
                         bdeValPag_PagMovInv = rstLoc1.getBigDecimal("mo_pag").abs();
                         bdeValPag_PagMovInv = objUti.redondearBigDecimal(bdeValPag_PagMovInv, objParSis.getDecimalesMostrar());
                         bdeValRet_JTb = new BigDecimal(tblDat.getValueAt(i, INT_TBL_DAT_MON).toString());
                         bdeValRet_JTb = objUti.redondearBigDecimal(bdeValRet_JTb, objParSis.getDecimalesMostrar());
                         bdeValAbo_PagMovInv = rstLoc1.getBigDecimal("nd_abo");

                         if (!bdeValPag_PagMovInv.equals(bdeValRet_JTb))
                         {  //Si es diferente, entonces hay que reestructurar.
                            //Se debe actualizar el campo tbm_pagmovinv.st_reg con el valor 'F'.
                            strSQL =  "UPDATE tbm_pagmovinv ";
                            strSQL += "SET nd_abo = 0, st_reg = 'F',";
                            strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
                            strSQL += " fe_ree = '" + strFecSis + "',";
                            strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
                            strSQL += " tx_obs1 = '" + strObs + "' ";
                            strSQL += "WHERE co_emp = " + strCodEmpRee;
                            strSQL += " and co_loc = " + strCodLocRee;
                            strSQL += " and co_tipdoc = " + strCodTipDocRee;
                            strSQL += " and co_doc = " + strCodDocRee;
                            strSQL += " and co_reg = " + intCodReg_PagMovInv;
                            stmLoc2.executeUpdate(strSQL);
                            
                            intUltCodReg_PagMovInv = 0;
                            //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                            strSQL =  "SELECT max(co_reg) as maxCodReg ";
                            strSQL += "FROM tbm_pagmovinv ";
                            strSQL += "WHERE co_emp = " + strCodEmpRee;
                            strSQL += " and co_loc = " + strCodLocRee;
                            strSQL += " and co_tipdoc = " + strCodTipDocRee;
                            strSQL += " and co_doc = " + strCodDocRee;
                            rstLoc2 = stmLoc2.executeQuery(strSQL);
                            
                            if (rstLoc2.next())
                            {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                               intUltCodReg_PagMovInv++;
                            }
                            
                            //Se debe ingresar un nuevo reg. en tbm_pagmovinv con el Por.Ret. y Val.Ret. del JTable.
                            //-------------------------------------------------------
                            strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                            strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                            strSQL += " fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag, tx_obs1,";
                            strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                            strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven, co_tipret, nd_porret,";
                            strSQL += " tx_aplret,";
                            strSQL += " -" + bdeValRet_JTb + ","; //mo_pag
                            strSQL += " ne_diagra, ";
                            strSQL += bdeValAbo_PagMovInv + ","; //nd_abo
                            strSQL += " st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq,";
                            strSQL += " 'C',"; //st_reg
                            strSQL += " st_regrep, ";
                            strSQL += "'" + strFecSis + "', "; //fe_ree
                            strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                            strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                            strSQL += " st_autpag, co_ctaautpag, ";
                            strSQL += "'" + strObs + "',"; //tx_obs1
                            strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                            strSQL += "FROM tbm_pagMovInv ";
                            strSQL += "WHERE co_emp = " + strCodEmpRee;
                            strSQL += " and co_loc = " + strCodLocRee;
                            strSQL += " and co_tipdoc = " + strCodTipDocRee;
                            strSQL += " and co_doc = " + strCodDocRee;
                            strSQL += " and co_reg = " + intCodReg_PagMovInv;
                            stmLoc2.executeUpdate(strSQL);
                            
                            //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbm_detpag.
                            //-------------------------------------------------------------------------
                            strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locPag, co_tipdocPag, co_docPag, co_regPag, nd_abo, st_reg ";
                            strSQL += "FROM tbm_detpag ";
                            strSQL += "WHERE co_emp = " + strCodEmpRee;
                            strSQL += " and co_locPag = " + strCodLocRee;
                            strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                            strSQL += " and co_docPag = " + strCodDocRee;
                            strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                            rstLoc2 = stmLoc2.executeQuery(strSQL);
                            
                            if (rstLoc2.next())
                            {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                              strSQL =  "UPDATE tbm_detpag ";
                              strSQL += "SET co_regPag = " + intUltCodReg_PagMovInv + " ";
                              strSQL += "WHERE co_emp = " + strCodEmpRee;
                              strSQL += " and co_locPag = " + strCodLocRee;
                              strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                              strSQL += " and co_docPag = " + strCodDocRee;
                              strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                              stmLoc2.executeUpdate(strSQL);
                            }
                            
                            //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbr_detRecDocPagMovInv.
                            //-------------------------------------------------------------------------------------
                            strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_empRel, co_locRel, co_tipdocRel, co_docRel, co_regRel, st_reg ";
                            strSQL += "FROM tbr_detRecDocPagMovInv ";
                            strSQL += "WHERE co_empRel = " + strCodEmpRee;
                            strSQL += " and co_locRel = " + strCodLocRee;
                            strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                            strSQL += " and co_docRel = " + strCodDocRee;
                            strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                            rstLoc2 = stmLoc2.executeQuery(strSQL);
                            
                            if (rstLoc2.next())
                            {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                              strSQL =  "UPDATE tbr_detRecDocPagMovInv ";
                              strSQL += "SET co_regRel = " + intUltCodReg_PagMovInv + " ";
                              strSQL += "WHERE co_empRel = " + strCodEmpRee;
                              strSQL += " and co_locRel = " + strCodLocRee;
                              strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                              strSQL += " and co_docRel = " + strCodDocRee;
                              strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                              stmLoc2.executeUpdate(strSQL);
                            }
                         } //if (!bdeValPag_PagMovInv.equals(bdeValRet_JTb))
                      } //if (bdePorRet_PagMovInv.equals(bdePorRet_JTb))
                   } //if (!strPorRet_JTb.equals(""))
                } //for (i = 0; i < tblDat.getRowCount(); i++)
                
                if (blnPorRetFound == false)
                {  //Como el Por.Ret. de tbm_pagmovinv no fue encontrado en el JTable, significa que se debe poner estado 'F' en dicho registro.
                   //Se debe actualizar el campo tbm_pagmovinv.st_reg con el valor 'F'.
                   strSQL =  "UPDATE tbm_pagmovinv ";
                   strSQL += "SET nd_abo = 0, st_reg = 'F',";
                   strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
                   strSQL += " fe_ree = '" + strFecSis + "',";
                   strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
                   strSQL += " tx_obs1 = '" + strObs + "' ";
                   strSQL += "WHERE co_emp = " + strCodEmpRee;
                   strSQL += " and co_loc = " + strCodLocRee;
                   strSQL += " and co_tipdoc = " + strCodTipDocRee;
                   strSQL += " and co_doc = " + strCodDocRee;
                   strSQL += " and co_reg = " + intCodReg_PagMovInv;
                   stmLoc2.executeUpdate(strSQL);
                }
             } //while (rstLoc1.next())
             
             //**********************************
             //*** PASO 3 ***********************
             //En este paso, por cada fila del JTable se va a verificar si el Por.Ret. existe em tbm_pagmovinv.  Si el Por.Ret. del JTable no fue encontrado
             //en tbm_pagmovinv, se procede a realizar un Insert en tbm_pagmovinv de dicho Por.Ret.
             //**********************************
             //Se va a barrer el JTable con los Por.Ret. y Val.Ret. que iran finalmente en tbm_pagmovinv.
             for (i = 0; i < tblDat.getRowCount(); i++)
             {  strPorRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_POR_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_POR_RET).toString();
                blnPorRetFound = false;
                
                if (!strPorRet_JTb.equals(""))
                {  bdePorRet_JTb = new BigDecimal(strPorRet_JTb);
                   bdePorRet_JTb = objUti.redondearBigDecimal(bdePorRet_JTb, objParSis.getDecimalesMostrar());
                   //De la tabla tbm_pagmovinv se van a traer los registros que tengan valores de retenciones.
                   strSQL =  "SELECT * ";
                   strSQL += "FROM tbm_pagmovinv ";
                   strSQL += "WHERE co_emp = " + strCodEmpRee;
                   strSQL += " and co_loc = " + strCodLocRee;
                   strSQL += " and co_tipdoc = " + strCodTipDocRee;
                   strSQL += " and co_doc = " + strCodDocRee;
                   strSQL += " and nd_porret > 0 and st_reg in ('A','C')";
                   rstLoc1 = stmLoc1.executeQuery(strSQL);
                   
                   while (rstLoc1.next())
                   {  bdePorRet_PagMovInv = rstLoc1.getBigDecimal("nd_porret");
                      bdePorRet_PagMovInv = objUti.redondearBigDecimal(bdePorRet_PagMovInv, objParSis.getDecimalesMostrar());
                      
                      if (bdePorRet_JTb.equals(bdePorRet_PagMovInv))
                      {  blnPorRetFound = true;
                         break; //Como el Por.Ret_JTable fue encontrado en tbm_pagmovinv, ya no es necesario continuar buscando en el while.
                      }
                   }
                   
                   if (blnPorRetFound == false)
                   {  //Como el Por.Ret_JTable. no fue encontrado en tbm_pagmovinv, significa que se debe realizar un Insert.
                      intUltCodReg_PagMovInv = 0;
                      //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                      strSQL =  "SELECT max(co_reg) as maxCodReg ";
                      strSQL += "FROM tbm_pagmovinv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      rstLoc2 = stmLoc2.executeQuery(strSQL);

                      if (rstLoc2.next())
                      {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                         intUltCodReg_PagMovInv++;
                      }

                      strAplRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_APL_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_APL_RET).toString();
                      strCodTipRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_COD_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_COD_RET).toString();
                      bdeValRet_JTb = new BigDecimal(tblDat.getValueAt(i, INT_TBL_DAT_MON).toString());
                      bdeValRet_JTb = objUti.redondearBigDecimal(bdeValRet_JTb, objParSis.getDecimalesMostrar());
                         
                      //Se debe ingresar un nuevo reg. en tbm_pagmovinv con el Por.Ret. y Val.Ret. del JTable.
                      //-------------------------------------------------------
                      strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                      strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq,";
                      strSQL += " nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag, tx_obs1,";
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                      strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven, ";
                      strSQL += strCodTipRet_JTb + ", "; //co_tipret
                      strSQL += bdePorRet_JTb + ", "; //nd_porret
                      strSQL += "'" + strAplRet_JTb + "',"; //tx_aplret
                      strSQL += " -" + bdeValRet_JTb + ","; //mo_pag
                      strSQL += " ne_diagra,";
                      strSQL += " 0,"; //nd_abo
                      strSQL += " st_sop, st_entsop, st_pos, ";
                      strSQL += " null,"; //co_banchq
                      strSQL += " null,"; //tx_numctachq
                      strSQL += " null,"; //tx_numchq
                      strSQL += " null,"; //fe_recchq
                      strSQL += " null,"; //fe_venchq
                      strSQL += " null,"; //nd_monchq
                      strSQL += " null,"; //co_prochq
                      strSQL += " 'C',"; //st_reg
                      strSQL += " st_regrep, ";
                      strSQL += "'" + strFecSis + "', "; //fe_ree
                      strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                      strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                      strSQL += " st_autpag, co_ctaautpag, ";
                      strSQL += "'" + strObs + "',"; //tx_obs1
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                      strSQL += "FROM tbm_pagMovInv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      strSQL += " and co_reg = 1";
                      stmLoc2.executeUpdate(strSQL);
                   } //if (blnPorRetFound = false)
                } //if (!strPorRet_JTb.equals(""))
             } //for (i = 0; i < tblDat.getRowCount(); i++)
             
             //**********************************
             //*** PASO 4 ***********************
             //En este paso, de la tabla tbm_pagmovinv se van a traer los registros que no tengan valores de retenciones y poner tbm_pagmovinv.st_reg = 'F'.
             //Ademas se va a calcular el valor_pagar_sin_retencion tomando en cuenta el valor del campo tbm_cabmovinv.nd_tot y los valores_retencion_JTable.
             //**********************************
             bdeSumRet = new BigDecimal(0);
             //Se va a barrer el JTable para sumar los Val.Ret. y calcular el valor a pagar sin retenciones.
             for (i = 0; i < tblDat.getRowCount(); i++)
             {  strPorRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_POR_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_POR_RET).toString();
                strAplRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_APL_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_APL_RET).toString();
                
                if (!strPorRet_JTb.equals(""))
                {  bdeValRet_JTb = new BigDecimal(tblDat.getValueAt(i, INT_TBL_DAT_MON).toString());
                   bdeValRet_JTb = objUti.redondearBigDecimal(bdeValRet_JTb, objParSis.getDecimalesMostrar());
                   bdeSumRet = bdeSumRet.add(bdeValRet_JTb);
                }
             }
             
             bdeValPagSinRet = new BigDecimal(txtValDoc.getText()).subtract(bdeSumRet);
             intNumPag = 0;
             //Se va a obtener el numero de pagos al que corresponde la forma de pago.
             strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_forpag, a2.tx_des, a2.ne_numpag ";
             strSQL += "FROM tbm_cabmovinv as a1 ";
             strSQL += "INNER JOIN tbm_cabforpag as a2 on a1.co_emp = a2.co_emp and a1.co_forpag = a2.co_forpag ";
             strSQL += "WHERE a1.co_emp = " + strCodEmpRee;
             strSQL += " and a1.co_loc = " + strCodLocRee;
             strSQL += " and a1.co_tipdoc = " + strCodTipDocRee;
             strSQL += " and a1.co_doc = " + strCodDocRee;
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             if (rstLoc1.next())
             {  intNumPag = rstLoc1.getInt("ne_numpag");
                bdeAux = new BigDecimal(intNumPag);
                bdeValPagSinRet = bdeValPagSinRet.divide(bdeAux, 2, RoundingMode.HALF_UP); 
             }
             
             //De la tabla tbm_pagmovinv se van a traer los registros que no tengan valores de retenciones.
             strSQL =  "SELECT * ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             strSQL += " and nd_porret = 0 and st_reg in ('A','C')";
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             while (rstLoc1.next())
             {  intCodReg_PagMovInv = rstLoc1.getInt("co_reg");
                bdeValAbo_PagMovInv = rstLoc1.getBigDecimal("nd_abo");
                //Hay que reestructurar.
                //Se debe actualizar el campo tbm_pagmovinv.st_reg con el valor 'F'.
                strSQL =  "UPDATE tbm_pagmovinv ";
                strSQL += "SET nd_abo = 0, st_reg = 'F',";
                strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
                strSQL += " fe_ree = '" + strFecSis + "',";
                strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
                strSQL += " tx_obs1 = '" + strObs + "' ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                strSQL += " and co_reg = " + intCodReg_PagMovInv;
                stmLoc2.executeUpdate(strSQL);

                intUltCodReg_PagMovInv = 0;
                //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                strSQL =  "SELECT max(co_reg) as maxCodReg ";
                strSQL += "FROM tbm_pagmovinv ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                   intUltCodReg_PagMovInv++;
                }

                //Se debe ingresar un nuevo reg. en tbm_pagmovinv con el Valor_pagar_sin_retencion.
                //-------------------------------------------------------
                strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq,";
                strSQL += " nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp,";
                strSQL += " tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven, co_tipret, nd_porret,";
                strSQL += " tx_aplret,";
                strSQL += " -" + bdeValPagSinRet + ","; //mo_pag
                strSQL += " ne_diagra, ";
                strSQL += bdeValAbo_PagMovInv + ","; //nd_abo
                strSQL += " st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq,";
                strSQL += " 'C',"; //st_reg
                strSQL += " st_regrep, ";
                strSQL += "'" + strFecSis + "', "; //fe_ree
                strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                strSQL += " st_autpag, co_ctaautpag, ";
                strSQL += "'" + strObs + "',"; //tx_obs1
                strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                strSQL += "FROM tbm_pagMovInv ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                strSQL += " and co_reg = " + intCodReg_PagMovInv;
                stmLoc2.executeUpdate(strSQL);

                //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbm_detpag.
                //-------------------------------------------------------------------------
                strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locPag, co_tipdocPag, co_docPag, co_regPag, nd_abo, st_reg ";
                strSQL += "FROM tbm_detpag ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_locPag = " + strCodLocRee;
                strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                strSQL += " and co_docPag = " + strCodDocRee;
                strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                   strSQL =  "UPDATE tbm_detpag ";
                   strSQL += "SET co_regPag = " + intUltCodReg_PagMovInv + " ";
                   strSQL += "WHERE co_emp = " + strCodEmpRee;
                   strSQL += " and co_locPag = " + strCodLocRee;
                   strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                   strSQL += " and co_docPag = " + strCodDocRee;
                   strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                   stmLoc2.executeUpdate(strSQL);
                }

                //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbr_detRecDocPagMovInv.
                //-------------------------------------------------------------------------------------
                strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_empRel, co_locRel, co_tipdocRel, co_docRel, co_regRel, st_reg ";
                strSQL += "FROM tbr_detRecDocPagMovInv ";
                strSQL += "WHERE co_empRel = " + strCodEmpRee;
                strSQL += " and co_locRel = " + strCodLocRee;
                strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                strSQL += " and co_docRel = " + strCodDocRee;
                strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                   strSQL =  "UPDATE tbr_detRecDocPagMovInv ";
                   strSQL += "SET co_regRel = " + intUltCodReg_PagMovInv + " ";
                   strSQL += "WHERE co_empRel = " + strCodEmpRee;
                   strSQL += " and co_locRel = " + strCodLocRee;
                   strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                   strSQL += " and co_docRel = " + strCodDocRee;
                   strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                   stmLoc2.executeUpdate(strSQL);
                }
             } //while (rstLoc1.next())
             
             //Despues de la reestructuracion, se va a verificar si la misma fue bien realizada.
             if (existeDiferencia_CabMovInv_PagMovInv() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             if (blnRes == true && existeDiferencia_PagMovInv_DetPag() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             if (blnRes == true && existeDiferencia_PagMovInv_ValTotDoc() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             rstLoc1.close();
             rstLoc1 = null;
             rstLoc2.close();
             rstLoc2 = null;
             stmLoc1.close();
             stmLoc1 = null;
             stmLoc2.close();
             stmLoc2 = null;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    } //Funcion guardarDatos_Reestructuracion_normal()
    
    private boolean guardarDatos_PonerValRetSaldoFavorCliente()
    {
       boolean blnRes = true;
       int i, intNumPag, intCodReg_PagMovInv, intUltCodReg_PagMovInv, intMaxCodReg_PagMovInv_AntRee;
       String strAux, strObs, strFecSis, strPorRet_JTb;
       BigDecimal bdeAux, bdeValAbo_PagMovInv, bdePorRet_JTb, bdeValRet_JTb, bdeSumValPag, bdeValPagSinRet, bdeValAbo_DetPag;
       boolean blnMosMsj;
       Statement stmLoc1, stmLoc2;
       ResultSet rstLoc1, rstLoc2;
       
       try
       {  
          if (con != null)
          {  stmLoc1 = con.createStatement();
             stmLoc2 = con.createStatement();
             rstLoc2 = null;
             
             datFecAux = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
             strFecSis = objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
             strObs = "Reestructurado por programa Reestructuracion de retenciones";
             intMaxCodReg_PagMovInv_AntRee = 0;
             //Se va a obtener el maximo secuencial en tbm_pagmovinv antes de reestructurar.
             strSQL =  "SELECT max(co_reg) as maxCodReg ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             rstLoc2 = stmLoc2.executeQuery(strSQL);

             if (rstLoc2.next())
             {  
                intMaxCodReg_PagMovInv_AntRee = rstLoc2.getInt("maxCodReg");
             }
             
             //**********************************
             //*** PASO 1 ***********************
             //En este paso, de la tabla tbm_pagmovinv se van a actualizar los registros donde el campo tbm_pagmovinv.st_reg sea 'F'. Como se va a 
             //reestructurar, este campo debera tener el valor 'I'.
             //**********************************
             strSQL =  "UPDATE tbm_pagmovinv ";
             strSQL += "SET st_reg = 'I',";
             strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
             strSQL += " fe_ree = '" + strFecSis + "',";
             strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
             strSQL += " tx_obs1 = '" + strObs + "' ";
             strSQL += "WHERE st_reg = 'F' and co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             stmLoc1.executeUpdate(strSQL);
             
             //**********************************
             //*** PASO 2 ***********************
             //Se va a barrer el JTable para tomar las filas que esten marcadas en la col. "Poner_valor_ret_a_favor_del_cliente".
             //**********************************
             for (i = 0; i < tblDat.getRowCount(); i++)
             {  strPorRet_JTb = tblDat.getValueAt(i, INT_TBL_DAT_POR_RET) == null? "" :tblDat.getValueAt(i, INT_TBL_DAT_POR_RET).toString();
                
                if (!strPorRet_JTb.equals("") && objTblMod.isChecked(i, INT_TBL_DAT_CHK_VAL_RET_FAV_CLI))
                {  bdePorRet_JTb = new BigDecimal(strPorRet_JTb);
                   bdePorRet_JTb = objUti.redondearBigDecimal(bdePorRet_JTb, objParSis.getDecimalesMostrar());
                   bdeValRet_JTb = new BigDecimal(tblDat.getValueAt(i, INT_TBL_DAT_ABO).toString()).abs();
                   bdeValRet_JTb = objUti.redondearBigDecimal(bdeValRet_JTb, objParSis.getDecimalesMostrar());
                   //De la tabla tbm_pagmovinv se va a traer el registro de acuerdo al Por_Ret_JTable.
                   strSQL =  "SELECT * ";
                   strSQL += "FROM tbm_pagmovinv ";
                   strSQL += "WHERE co_emp = " + strCodEmpRee;
                   strSQL += " and co_loc = " + strCodLocRee;
                   strSQL += " and co_tipdoc = " + strCodTipDocRee;
                   strSQL += " and co_doc = " + strCodDocRee;
                   strSQL += " and st_reg in ('A','C') and nd_porret = " + bdePorRet_JTb;
                   rstLoc1 = stmLoc1.executeQuery(strSQL);
                   
                   if (rstLoc1.next())
                   {  intCodReg_PagMovInv = rstLoc1.getInt("co_reg");
                      bdeValAbo_PagMovInv = rstLoc1.getBigDecimal("nd_abo");
                      //Se debe actualizar el campo tbm_pagmovinv.st_reg con el valor 'F'.
                      strSQL =  "UPDATE tbm_pagmovinv ";
                      strSQL += "SET nd_abo = 0, st_reg = 'F',";
                      strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
                      strSQL += " fe_ree = '" + strFecSis + "',";
                      strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
                      strSQL += " tx_obs1 = '" + strObs + "' ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      strSQL += " and co_reg = " + intCodReg_PagMovInv;
                      stmLoc2.executeUpdate(strSQL);

                      intUltCodReg_PagMovInv = 0;
                      //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                      strSQL =  "SELECT max(co_reg) as maxCodReg ";
                      strSQL += "FROM tbm_pagmovinv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      rstLoc2 = stmLoc2.executeQuery(strSQL);

                      if (rstLoc2.next())
                      {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                         intUltCodReg_PagMovInv++;
                      }

                      //1er. Insert en tbm_pagmovinv. Este nuevo reg. tendra el Por.Ret. y Val.Ret. del JTable.
                      //-----------------------------
                      strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                      strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                      strSQL += " fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag, tx_obs1,";
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                      strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven, co_tipret, nd_porret,";
                      strSQL += " tx_aplret,";
                      strSQL += " mo_pag" + ","; //mo_pag
                      strSQL += " ne_diagra, ";
                      strSQL += " 0,"; //nd_abo
                      strSQL += " st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq,";
                      strSQL += " 'C',"; //st_reg
                      strSQL += " st_regrep, ";
                      strSQL += "'" + strFecSis + "', "; //fe_ree
                      strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                      strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                      strSQL += " st_autpag, co_ctaautpag, ";
                      strSQL += "'" + strObs + "',"; //tx_obs1
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                      strSQL += "FROM tbm_pagMovInv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      strSQL += " and co_reg = " + intCodReg_PagMovInv;
                      stmLoc2.executeUpdate(strSQL);
                      
                      intUltCodReg_PagMovInv = 0;
                      //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                      strSQL =  "SELECT max(co_reg) as maxCodReg ";
                      strSQL += "FROM tbm_pagmovinv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      rstLoc2 = stmLoc2.executeQuery(strSQL);

                      if (rstLoc2.next())
                      {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                         intUltCodReg_PagMovInv++;
                      }

                      //2do. Insert en tbm_pagmovinv. Este nuevo reg. va a tener nd_porret = 0, y este reg. es el que debera ser cruzado con tbm_detpag.
                      //-----------------------------
                      strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                      strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,";
                      strSQL += " fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag, tx_obs1,";
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                      strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven,";
                      strSQL += " null,"; //co_tipret
                      strSQL += " 0,"; //nd_porret
                      strSQL += " null,"; //tx_aplret
                      strSQL += " mo_pag,"; //mo_pag
                      strSQL += " ne_diagra, ";
                      strSQL += bdeValAbo_PagMovInv + ","; //nd_abo
                      strSQL += " st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq,";
                      strSQL += " 'C',"; //st_reg
                      strSQL += " st_regrep, ";
                      strSQL += "'" + strFecSis + "', "; //fe_ree
                      strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                      strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                      strSQL += " st_autpag, co_ctaautpag, ";
                      strSQL += "'" + strObs + "',"; //tx_obs1
                      strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                      strSQL += "FROM tbm_pagMovInv ";
                      strSQL += "WHERE co_emp = " + strCodEmpRee;
                      strSQL += " and co_loc = " + strCodLocRee;
                      strSQL += " and co_tipdoc = " + strCodTipDocRee;
                      strSQL += " and co_doc = " + strCodDocRee;
                      strSQL += " and co_reg = " + intCodReg_PagMovInv;
                      stmLoc2.executeUpdate(strSQL);
 
                      blnMosMsj = false;
                      //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbm_detpag.
                      //-------------------------------------------------------------------------
                      strSQL =  "SELECT a.co_emp, a.co_locPag, a.co_tipdocPag, a.co_docPag, sum(a.nd_abo) as nd_abo " + "\n";
                      strSQL += "FROM ( SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locPag, co_tipdocPag, co_docPag, co_regPag, nd_abo, st_reg " + "\n";
                      strSQL += "       FROM tbm_detpag " + "\n";
                      strSQL += "       WHERE co_emp = " + strCodEmpRee + " and co_locPag = " + strCodLocRee + " and co_tipdocPag = " + strCodTipDocRee;
                      strSQL += "          and co_docPag = " + strCodDocRee + " and co_regPag = " + intCodReg_PagMovInv;
                      strSQL += "     ) as a " + "\n";
                      strSQL += "GROUP BY a.co_emp, a.co_locPag, a.co_tipdocPag, a.co_docPag";
                      rstLoc2 = stmLoc2.executeQuery(strSQL);
                      
                      if (rstLoc2.next())
                      {  bdeValAbo_DetPag = rstLoc2.getBigDecimal("nd_abo");
                         bdeValAbo_DetPag = objUti.redondearBigDecimal(bdeValAbo_DetPag, objParSis.getDecimalesMostrar());
                         
                         if (!bdeValRet_JTb.equals(bdeValAbo_DetPag))
                         {  //Si son valores diferentes, no se puede continuar.
                            blnMosMsj = true;
                         }
                         else
                         {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                            strSQL =  "UPDATE tbm_detpag ";
                            strSQL += "SET co_regPag = " + intUltCodReg_PagMovInv + " ";
                            strSQL += "WHERE co_emp = " + strCodEmpRee;
                            strSQL += " and co_locPag = " + strCodLocRee;
                            strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                            strSQL += " and co_docPag = " + strCodDocRee;
                            strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                            stmLoc2.executeUpdate(strSQL);
                         }
                      }
                      else
                      {  //Como no se encontro el reg, no se puede continuar.
                         blnMosMsj = true;
                      }
                      
                      if (blnMosMsj == true)
                      {  //Se debe mostrar el mensaje de error y abortar la operacion de guardar.
                         strAux =  "<HTML>Se encontró una incoherencia de datos y por tanto no se puede guardar.<BR>";
                         strAux += "Esto ocurrió para la siguiente línea de detalle:<BR><BR>";
                         strAux += "Porcentaje de Retención: " + bdePorRet_JTb + "<BR>";
                         strAux += "Valor de Retención: " + bdeValRet_JTb + "<BR><BR>";;
                         strAux += "Notifique esta novedad al Administrador del sistema.</HTML>";
                         mostrarMsgInf(strAux);
                         return false;
                      }

                      //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbr_detRecDocPagMovInv.
                      //-------------------------------------------------------------------------------------
                      strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_empRel, co_locRel, co_tipdocRel, co_docRel, co_regRel, st_reg ";
                      strSQL += "FROM tbr_detRecDocPagMovInv ";
                      strSQL += "WHERE co_empRel = " + strCodEmpRee;
                      strSQL += " and co_locRel = " + strCodLocRee;
                      strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                      strSQL += " and co_docRel = " + strCodDocRee;
                      strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                      rstLoc2 = stmLoc2.executeQuery(strSQL);
 
                      if (rstLoc2.next())
                      {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                         strSQL =  "UPDATE tbr_detRecDocPagMovInv ";
                         strSQL += "SET co_regRel = " + intUltCodReg_PagMovInv + " ";
                         strSQL += "WHERE co_empRel = " + strCodEmpRee;
                         strSQL += " and co_locRel = " + strCodLocRee;
                         strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                         strSQL += " and co_docRel = " + strCodDocRee;
                         strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                         stmLoc2.executeUpdate(strSQL);
                      }
                   } //if (rstLoc1.next())
                } //if (!strPorRet_JTb.equals("") && objTblMod.isChecked(i, INT_TBL_DAT_CHK_VAL_RET_FAV_CLI))
             } //for (i = 0; i < tblDat.getRowCount(); i++)
             
             //**********************************
             //*** PASO 3 ***********************
             //En este paso, de la tabla tbm_pagmovinv se van a traer los registros que no tengan valores de retenciones y poner tbm_pagmovinv.st_reg = 'F'.
             //Ademas se va a calcular el valor_pagar_sin_retencion tomando en cuenta los nuevos valores insertados.
             //**********************************
             bdeSumValPag = new BigDecimal(0);
             strSQL =  "SELECT sum(mo_pag) as sumValPag ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             strSQL += " and st_reg in ('A','C') and co_reg > " + intMaxCodReg_PagMovInv_AntRee;
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             if (rstLoc1.next())
             {  bdeSumValPag = rstLoc1.getBigDecimal("sumValPag").abs();
                bdeSumValPag = objUti.redondearBigDecimal(bdeSumValPag, objParSis.getDecimalesMostrar());
             }
             
             bdeValPagSinRet = new BigDecimal(txtValDoc.getText()).subtract(bdeSumValPag);
             intNumPag = 0;
             //Se va a obtener el numero de pagos al que corresponde la forma de pago.
             strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_forpag, a2.tx_des, a2.ne_numpag ";
             strSQL += "FROM tbm_cabmovinv as a1 ";
             strSQL += "INNER JOIN tbm_cabforpag as a2 on a1.co_emp = a2.co_emp and a1.co_forpag = a2.co_forpag ";
             strSQL += "WHERE a1.co_emp = " + strCodEmpRee;
             strSQL += " and a1.co_loc = " + strCodLocRee;
             strSQL += " and a1.co_tipdoc = " + strCodTipDocRee;
             strSQL += " and a1.co_doc = " + strCodDocRee;
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             if (rstLoc1.next())
             {  intNumPag = rstLoc1.getInt("ne_numpag");
                bdeAux = new BigDecimal(intNumPag);
                bdeValPagSinRet = bdeValPagSinRet.divide(bdeAux, 2, RoundingMode.HALF_UP); 
             }
             
             //De la tabla tbm_pagmovinv se van a traer los registros que no tengan valores de retenciones.
             strSQL =  "SELECT * ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             strSQL += " and nd_porret = 0 and st_reg in ('A','C') and co_reg <= " + intMaxCodReg_PagMovInv_AntRee;
             rstLoc1 = stmLoc1.executeQuery(strSQL);
             
             while (rstLoc1.next())
             {  intCodReg_PagMovInv = rstLoc1.getInt("co_reg");
                bdeValAbo_PagMovInv = rstLoc1.getBigDecimal("nd_abo");
                //Hay que reestructurar.
                //Se debe actualizar el campo tbm_pagmovinv.st_reg con el valor 'F'.
                strSQL =  "UPDATE tbm_pagmovinv ";
                strSQL += "SET nd_abo = 0, st_reg = 'F',";
                strSQL += " co_usrree = " + objParSis.getCodigoUsuario() + ",";
                strSQL += " fe_ree = '" + strFecSis + "',";
                strSQL += " tx_comree = '" + objParSis.getNombreComputadoraConDirIP() + "',";
                strSQL += " tx_obs1 = '" + strObs + "' ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                strSQL += " and co_reg = " + intCodReg_PagMovInv;
                stmLoc2.executeUpdate(strSQL);

                intUltCodReg_PagMovInv = 0;
                //Se va a obtener el maximo secuencial en tbm_pagmovinv.
                strSQL =  "SELECT max(co_reg) as maxCodReg ";
                strSQL += "FROM tbm_pagmovinv ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  intUltCodReg_PagMovInv = rstLoc2.getInt("maxCodReg");
                   intUltCodReg_PagMovInv++;
                }

                //Se debe ingresar un nuevo reg. en tbm_pagmovinv con el Valor_pagar_sin_retencion.
                //-------------------------------------------------------
                strSQL =  "INSERT INTO tbm_pagMovInv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, co_tipret, nd_porret,";
                strSQL += " tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq,";
                strSQL += " nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, tx_comree, st_autpag, co_ctaautpag,";
                strSQL += " tx_obs1, tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg)" + "\n";
                strSQL += "SELECT co_emp, co_loc, co_tipdoc, co_doc, " + intUltCodReg_PagMovInv + ", ne_diacre, fe_ven, co_tipret, nd_porret,";
                strSQL += " tx_aplret,";
                strSQL += " -" + bdeValPagSinRet + ","; //mo_pag
                strSQL += " ne_diagra, ";
                strSQL += bdeValAbo_PagMovInv + ","; //nd_abo
                strSQL += " st_sop, st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq, fe_venchq, nd_monchq, co_prochq,";
                strSQL += " 'C',"; //st_reg
                strSQL += " st_regrep,";
                strSQL += "'" + strFecSis + "', "; //fe_ree
                strSQL += objParSis.getCodigoUsuario() + ", "; //co_usrree
                strSQL += "'" + objParSis.getNombreComputadoraConDirIP() + "',"; //tx_comree
                strSQL += " st_autpag, co_ctaautpag, ";
                strSQL += "'" + strObs + "',"; //tx_obs1        
                strSQL += " tx_codsri, nd_basimp, tx_numser, tx_numautsri, tx_feccad, fe_venchqautpag, nd_valiva, tx_tipreg " + "\n";
                strSQL += "FROM tbm_pagMovInv ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_loc = " + strCodLocRee;
                strSQL += " and co_tipdoc = " + strCodTipDocRee;
                strSQL += " and co_doc = " + strCodDocRee;
                strSQL += " and co_reg = " + intCodReg_PagMovInv;
                stmLoc2.executeUpdate(strSQL);

                //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbm_detpag.
                //-------------------------------------------------------------------------
                strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_locPag, co_tipdocPag, co_docPag, co_regPag, nd_abo, st_reg ";
                strSQL += "FROM tbm_detpag ";
                strSQL += "WHERE co_emp = " + strCodEmpRee;
                strSQL += " and co_locPag = " + strCodLocRee;
                strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                strSQL += " and co_docPag = " + strCodDocRee;
                strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                   strSQL =  "UPDATE tbm_detpag ";
                   strSQL += "SET co_regPag = " + intUltCodReg_PagMovInv + " ";
                   strSQL += "WHERE co_emp = " + strCodEmpRee;
                   strSQL += " and co_locPag = " + strCodLocRee;
                   strSQL += " and co_tipdocPag = " + strCodTipDocRee;
                   strSQL += " and co_docPag = " + strCodDocRee;
                   strSQL += " and co_regPag = " + intCodReg_PagMovInv;
                   stmLoc2.executeUpdate(strSQL);
                }

                //Se va a verificar si el reg. de tbm_pagmovinv se encuentra en tbr_detRecDocPagMovInv.
                //-------------------------------------------------------------------------------------
                strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_empRel, co_locRel, co_tipdocRel, co_docRel, co_regRel, st_reg ";
                strSQL += "FROM tbr_detRecDocPagMovInv ";
                strSQL += "WHERE co_empRel = " + strCodEmpRee;
                strSQL += " and co_locRel = " + strCodLocRee;
                strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                strSQL += " and co_docRel = " + strCodDocRee;
                strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                rstLoc2 = stmLoc2.executeQuery(strSQL);

                if (rstLoc2.next())
                {  //Como el reg. de tbm_pagmovinv si fue encuentrado en tbm_detpag, se va a actualizar el campo tbm_cabpag.co_regPag.
                   strSQL =  "UPDATE tbr_detRecDocPagMovInv ";
                   strSQL += "SET co_regRel = " + intUltCodReg_PagMovInv + " ";
                   strSQL += "WHERE co_empRel = " + strCodEmpRee;
                   strSQL += " and co_locRel = " + strCodLocRee;
                   strSQL += " and co_tipdocRel = " + strCodTipDocRee;
                   strSQL += " and co_docRel = " + strCodDocRee;
                   strSQL += " and co_regRel = " + intCodReg_PagMovInv;
                   stmLoc2.executeUpdate(strSQL);
                }
             } //while (rstLoc1.next())
             
             //Despues de la reestructuracion, se va a verificar si la misma fue bien realizada.
             if (existeDiferencia_CabMovInv_PagMovInv() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             if (blnRes == true && existeDiferencia_PagMovInv_DetPag() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             if (blnRes == true && existeDiferencia_PagMovInv_ValTotDoc() == true)
             {  //Se debe abortar la operacion de guardar.
                blnRes = false;
             }
             
             rstLoc1.close();
             rstLoc1 = null;
             rstLoc2.close();
             rstLoc2 = null;
             stmLoc1.close();
             stmLoc1 = null;
             stmLoc2.close();
             stmLoc2 = null;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    } //Funcion guardarDatos_PonerValRetSaldoFavorCliente()
    
    private boolean existeDiferencia_CabMovInv_PagMovInv()
    {
       boolean blnRes = false;
       Statement stmLoc;
       ResultSet rstLoc;
       
       try
       {  
          if (con != null)
          {  stmLoc = con.createStatement();
             
             strSQL =  "SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.ne_numDoc, b1.fe_doc, b1.nd_tot, b2.nd_pag, (b1.nd_tot-b2.nd_pag) AS nd_dif " + "\n";
             strSQL += "FROM  tbm_cabMovInv AS b1 " + "\n";
             strSQL += "INNER JOIN ( SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, SUM(a1.nd_pag) AS nd_pag " + "\n";
             strSQL += "             FROM ( SELECT co_emp, co_loc, co_tipDoc, co_doc, 0 AS nd_pag " + "\n";
             strSQL += "                    FROM tbm_cabMovInv " + "\n";
             strSQL += "                    WHERE co_emp= " + strCodEmpRee + "\n";
             strSQL += "                    UNION ALL " + "\n";
             strSQL += "                    SELECT co_emp, co_loc, co_tipDoc, co_doc, SUM(mo_pag) AS nd_pag " + "\n";
             strSQL += "                    FROM tbm_pagMovInv " + "\n";
             strSQL += "                    WHERE co_emp= " + strCodEmpRee + " AND st_reg IN ('A','F') " + "\n";
             strSQL += "                    GROUP BY co_emp, co_loc, co_tipDoc, co_doc " + "\n";
             strSQL += "                  ) AS a1 " + "\n";
             strSQL += "             GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc " + "\n";
             strSQL += "           ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc) " + "\n";
             strSQL += "INNER JOIN tbm_cabTipDoc AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_loc AND b1.co_tipDoc=b3.co_tipDoc) " + "\n";
             strSQL += "WHERE b1.nd_tot<>b2.nd_pag AND b3.st_genPag='S' AND b1.co_tipDoc NOT IN (124, 125, 126, 127, 128, 129, 130, 131, 132, 133, 134, 135, 136, 137, 138, 139, 165, 166, 167, 168) " + "\n";
             strSQL += " and b1.co_emp = " + strCodEmpRee + " and b1.co_loc = " + strCodLocRee + " and b1.co_tipdoc = " + strCodTipDocRee;
             strSQL += " and b1.co_doc = " + strCodDocRee;
             //strSQL += "ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc
             rstLoc = stmLoc.executeQuery(strSQL);
             
             if (rstLoc.next())
             {  //Se encontro diferencia en tbm_cabMovInv y tbm_pagMovInv. Por tanto, se debe abortar la operacion de guardar.
                blnRes = true;
                strAux =  "<HTML>Se encontró una incoherencia de datos entre tbm_cabMovInv<BR>";
                strAux += "y tbm_pagMovInv. Debido a esto no se puede guardar.<BR><BR>";
                strAux += "Notifique esta novedad al Administrador del sistema.</HTML>";
                mostrarMsgInf(strAux);
             }
             
             rstLoc.close();
             rstLoc = null;
             stmLoc.close();
             stmLoc = null;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       return blnRes;
    } //Funcion existeDiferencia_CabMovInv_PagMovInv()
    
    private boolean existeDiferencia_PagMovInv_DetPag()
    {
       boolean blnRes = false;
       Statement stmLoc;
       ResultSet rstLoc;
       
       try
       {  
          if (con != null)
          {  stmLoc = con.createStatement();
             
             strSQL =  "SELECT c1.* " + "\n";
             strSQL += "FROM ( SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.ne_numDoc, b1.nd_aboModVen , (CASE WHEN b2.nd_aboModCxC IS NULL THEN 0 ELSE b2.nd_aboModCxC END) AS nd_aboModCxC " + "\n";
             strSQL += "       FROM ( SELECT a1.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.nd_abo AS nd_aboModVen, a1.co_cli, a1.ne_numDoc " + "\n";
             strSQL += "              FROM tbm_cabMovInv AS a1 " + "\n";
             strSQL += "              INNER JOIN tbm_pagMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDOC AND a1.co_doc=a2.co_doc) " + "\n";
             strSQL += "              WHERE a1.co_emp= " + strCodEmpRee + " AND a1.st_reg IN ('A','R','C','F') AND a2.st_reg IN ('A','C') " + "\n";
             strSQL += "            ) AS b1 " + "\n";
             strSQL += "       LEFT OUTER JOIN ( SELECT a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag, SUM(a2.nd_abo) AS nd_aboModCxC " + "\n";
             strSQL += "                         FROM tbm_cabPag AS a1 " + "\n";
             strSQL += "                         INNER JOIN tbm_detPag AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc) " + "\n";
             strSQL += "                         INNER JOIN tbm_cabMovInv AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc) " + "\n";
             strSQL += "                         WHERE a1.co_emp=" + strCodEmpRee + " AND a1.st_reg='A' AND a2.st_reg='A' AND a3.st_reg IN ('A','R','C','F') " + "\n";
             strSQL += "                         GROUP BY a1.co_emp, a2.co_locPag, a2.co_tipDocPag, a2.co_docPag, a2.co_regPag " + "\n";
             strSQL += "                       ) AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locPag AND b1.co_tipDoc=b2.co_tipDocPag AND b1.co_doc=b2.co_docPag AND b1.co_reg=b2.co_regPag) " + "\n";
             strSQL += "       INNER JOIN tbm_cli AS b3 ON (b1.co_emp=b3.co_emp AND b1.co_cli=b3.co_cli) " + "\n";
             strSQL += "       ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc " + "\n";
             strSQL += "     ) AS c1 " + "\n";
             strSQL += "WHERE c1.nd_aboModVen IS NULL OR c1.nd_aboModCxC IS NULL OR c1.nd_aboModVen<>c1.nd_aboModCxC " + "\n";
             strSQL += " and c1.co_emp = " + strCodEmpRee + " and c1.co_loc = " + strCodLocRee + " and c1.co_tipdoc = " + strCodTipDocRee;
             strSQL += " and c1.co_doc = " + strCodDocRee;
             rstLoc = stmLoc.executeQuery(strSQL);
             
             if (rstLoc.next())
             {  //Se encontro diferencia en tbm_pagMovInv y tbm_detPag. Por tanto, se debe abortar la operacion de guardar.
                blnRes = true;
                strAux =  "<HTML>Se encontró una incoherencia de datos entre tbm_pagMovInv<BR>";
                strAux += "y tbm_detPag. Debido a esto no se puede guardar.<BR><BR>";
                strAux += "Notifique esta novedad al Administrador del sistema.</HTML>";
                mostrarMsgInf(strAux);
             }
             
             rstLoc.close();
             rstLoc = null;
             stmLoc.close();
             stmLoc = null;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       return blnRes;
    } //Funcion existeDiferencia_PagMovInv_DetPag()
    
    private boolean existeDiferencia_PagMovInv_ValTotDoc()
    {
       boolean blnRes = false;
       BigDecimal bdeSumValPag_PagMovInv, bdeValTotDoc;
       Statement stmLoc;
       ResultSet rstLoc;
       
       try
       {  
          if (con != null)
          {  stmLoc = con.createStatement();
             bdeValTotDoc = new BigDecimal(txtValDoc.getText());
             bdeValTotDoc = objUti.redondearBigDecimal(bdeValTotDoc, objParSis.getDecimalesMostrar());
             bdeSumValPag_PagMovInv = new BigDecimal(0);
             
             strSQL =  "SELECT sum(mo_pag) as sumValPag ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE co_emp = " + strCodEmpRee;
             strSQL += " and co_loc = " + strCodLocRee;
             strSQL += " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee;
             strSQL += " and st_reg in ('A','C')";
             rstLoc = stmLoc.executeQuery(strSQL);
             
             if (rstLoc.next())
             {  bdeSumValPag_PagMovInv = rstLoc.getBigDecimal("sumValPag").abs();
                bdeSumValPag_PagMovInv = objUti.redondearBigDecimal(bdeSumValPag_PagMovInv, objParSis.getDecimalesMostrar());
             }
             
             if (!bdeValTotDoc.equals(bdeSumValPag_PagMovInv))
             {  //Se encontro diferencia en ValTotDoc (tbm_cabMovInv.nd_tot) y tbm_pagMovInv. Por tanto, se debe abortar la operacion de guardar.
                blnRes = true;
                strAux =  "<HTML>Se encontró una incoherencia de datos entre tbm_pagMovInv<BR>";
                strAux += "y el Valor Total del documento. Debido a esto no se puede guardar.<BR><BR>";
                strAux += "Notifique esta novedad al Administrador del sistema.</HTML>";
                mostrarMsgInf(strAux);
             }
             
             rstLoc.close();
             rstLoc = null;
             stmLoc.close();
             stmLoc = null;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = true;
       }
       
       return blnRes;
    } //Funcion existeDiferencia_PagMovInv_ValTotDoc()
    
    private boolean getSubtotal_Iva_Bienes_Servicios()
    {
       boolean blnRes = false, blnFound;
       BigDecimal bdeValSub, bdeValIva;
       String strAux, strRuc;
       Connection conLoc;
       Statement stmLoc;
       ResultSet rstLoc;
       
       try
       {  
          conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
          
          if (conLoc != null)
          {  stmLoc = conLoc.createStatement();
             cargarCodigosPrincipalesFactura(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(objParSis.getCodigoLocal()), txtCodTipDoc.getText(), txtNumDoc.getText());
             
             strSQL =  "SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.ne_numdoc, b1.st_ser, b1.nd_porIva, sum(b1.nd_valIva) as nd_valIva, sum(b1.nd_tot) as nd_subTot " + "\n";
             strSQL += "FROM ( SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.ne_numdoc, a3.st_ser, a1.nd_porIva, (a1.nd_basImpIvaGra * (a1.nd_porIva / 100)) as nd_valIva, a1.nd_tot " + "\n";
             strSQL += "       FROM tbm_detmovinv as a1 " + "\n";
             strSQL += "       INNER JOIN tbm_cabmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc " + "\n";
             strSQL += "       INNER JOIN tbm_inv as a3 on a1.co_emp = a3.co_emp and a1.co_itm = a3.co_itm " + "\n";
             strSQL += "       WHERE a2.st_reg = 'A' and a1.co_emp = " + strCodEmpRee + " and a1.co_loc = " + strCodLocRee;
             strSQL += "          and a1.co_tipdoc = " + strCodTipDocRee + " and a1.co_doc = " + strCodDocRee;
             strSQL += "     ) as b1 " + "\n";
             strSQL += "GROUP BY b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.ne_numdoc, b1.st_ser, b1.nd_porIva";
             rstLoc = stmLoc.executeQuery(strSQL);
             
             while (rstLoc.next())
             {  bdeValSub = rstLoc.getBigDecimal("nd_subTot") == null? new BigDecimal(0) :rstLoc.getBigDecimal("nd_subTot").abs();
                bdeValSub = objUti.redondearBigDecimal(bdeValSub, objParSis.getDecimalesMostrar());
                bdeValIva = rstLoc.getBigDecimal("nd_valIva") == null? new BigDecimal(0) :rstLoc.getBigDecimal("nd_valIva").abs();
                bdeValIva = objUti.redondearBigDecimal(bdeValIva, objParSis.getDecimalesMostrar());
                   
                if (rstLoc.getString("st_ser").equals("N"))
                {  //El item coresponde a un bien.
                   txtSubTotBie.setText(bdeValSub.toString());
                   txtIvaBie.setText(bdeValIva.toString());
                }
                else if (rstLoc.getString("st_ser").equals("S") || rstLoc.getString("st_ser").equals("T"))
                {  //El item coresponde a un servicio (S) o transporte (T).
                   txtSubTotSer.setText(bdeValSub.toString());
                   txtIvaSer.setText(bdeValIva.toString());
                }
             }
             
             blnFound = false;
             strRuc = "";
             //Se va a determinar si el item es Chatarra.
             strSQL =  "SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a2.co_cli, a2.tx_nomcli, a2.tx_ruc, a1.co_itm, a1.tx_codalt, a1.tx_nomitm " + "\n";
             strSQL += "FROM tbm_detmovinv as a1 " + "\n";
             strSQL += "INNER JOIN tbm_cabmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_loc and a2.co_tipdoc = a1.co_tipdoc and a2.co_doc = a1.co_doc " + "\n";
             strSQL += "WHERE a1.co_emp = " + strCodEmpRee + " and a1.co_loc = " + strCodLocRee + " and a1.co_tipdoc = " + strCodTipDocRee;
             strSQL += " and a1.co_doc = " + strCodDocRee;
             rstLoc = stmLoc.executeQuery(strSQL);
             
             while (rstLoc.next())
             {  strAux = rstLoc.getString("tx_codalt");
                
                if (strAux.equals("CHAT") || strAux.equals("CHAMET") || strAux.equals("CHAMETSERV"))
                {  blnFound = true;
                   strRuc = rstLoc.getString("tx_ruc");
                   break;
                }
             }
             
             if (blnFound == true)
             {  //Si es verdadero, significa que el item es Chatarra.  En Zafiro este item esta definido como un Servicio y los Por.Ret. suelen ser 
                //Ret.Fte. 2% y Ret.IVA 20%. Pero para el cliente, este item es un bien, lo cual es correcto. Y el cliente suele enviar la retencion con los 
                //Por.Ret. Ret.Fte. 1% y Ret.IVA 10%, lo cual es correcto. Por ello, se procede a copiar los valores de los JTextField de Servicios en los 
                //JTextField de Bienes. Y se procede a blanquear los JTextField de Servicios.
                txtSubTotBie.setText(txtSubTotSer.getText());
                txtIvaBie.setText(txtIvaSer.getText());
                txtSubTotSer.setText("");
                txtIvaSer.setText("");
                chkMosItmCha.setSelected(true);
                blnRes = true;
             }
             else
             {
                chkMosItmCha.setSelected(false);
             }
             
             rstLoc.close();
             rstLoc = null;
             stmLoc.close();
             stmLoc = null;
             conLoc.close();
             conLoc = null;
             blnRes = true;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    } //Funcion getSubtotal_Iva_Bienes_Servicios()
    
    private boolean getDatos_PagMovInv(BigDecimal bdePorRet)
    {
       boolean blnRes = false, blnFound;
       BigDecimal bdeValSub, bdeValIva;
       String strAux, strRuc;
       Connection conLoc;
       Statement stmLoc;
       ResultSet rstLoc;
       
       try
       {  
          strFecVen_Glo = "";
          strValAbo_Glo = "";
          conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
          
          if (conLoc != null)
          {  stmLoc = conLoc.createStatement();
             
             strSQL =  "SELECT co_emp, co_loc, co_tipdoc, co_doc, co_reg, nd_porret, tx_aplret, st_reg, fe_ven, nd_abo ";
             strSQL += "FROM tbm_pagmovinv ";
             strSQL += "WHERE st_reg in ('A','C') and co_emp = " + strCodEmpRee + " and co_loc = " + strCodLocRee + " and co_tipdoc = " + strCodTipDocRee;
             strSQL += " and co_doc = " + strCodDocRee + " and nd_porret = " + bdePorRet;
             rstLoc = stmLoc.executeQuery(strSQL);
             
             if (rstLoc.next())
             {  strFecVen_Glo = rstLoc.getString("fe_ven");
                strValAbo_Glo = "-" + rstLoc.getString("nd_abo");
             }
             
             rstLoc.close();
             rstLoc = null;
             stmLoc.close();
             stmLoc = null;
             conLoc.close();
             conLoc = null;
             blnRes = true;
          } //if (con != null)
       } //try
       
       catch (java.sql.SQLException e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       catch (Exception e)
       {  objUti.mostrarMsgErr_F1(this, e);
          blnRes = false;
       }
       
       return blnRes;
    } //Funcion getDatos_PagMovInv()
}