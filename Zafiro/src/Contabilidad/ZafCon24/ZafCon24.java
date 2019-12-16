/*
 * ZafCon24.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */
package Contabilidad.ZafCon24;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.util.ArrayList;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

/**
 *
 * @author Eddye Lino
 */
public class ZafCon24 extends javax.swing.JInternalFrame {

    final int INT_TBL_DAT_RET_LIN = 0;
    final int INT_TBL_DAT_RET_CHK = 1;
    final int INT_TBL_DAT_RET_COD = 2;
    final int INT_TBL_DAT_RET_DES_COR = 3;
    final int INT_TBL_DAT_RET_DES_LAR = 4;
    final int INT_TBL_DAT_RET_POR_RET = 5;
    final int INT_TBL_DAT_RET_APL = 6;

    //Constantes: Columnas del JTable:
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_COD_EMP = 1;
    final int INT_TBL_DAT_COD_LOC = 2;
    final int INT_TBL_DAT_COD_TIP_DOC = 3;
    final int INT_TBL_DAT_COD_DOC = 4;
    final int INT_TBL_DAT_COD_CLI = 5;
    final int INT_TBL_DAT_NOM_CLI = 6;
    final int INT_TBL_DAT_FEC_DOC = 7;
    final int INT_TBL_DAT_NOM_DOC = 8;
    final int INT_TBL_DAT_NUM_DOC = 9;
    final int INT_TBL_DAT_SUB = 10;
    final int INT_TBL_DAT_VAL_IVA = 11;
    final int INT_TBL_DAT_TOT = 12;
    final int INT_TBL_DAT_TIP_IDE = 13;//TIPOID
    final int INT_TBL_DAT_RUC = 14;//RUC
    final int INT_TBL_DAT_TIP_COM = 15;//TIPCOM
    final int INT_TBL_DAT_FEC_EMI = 16;//FEC_EMI
    final int INT_TBL_DAT_FEC_REG = 17;//FEC_REG
    final int INT_TBL_DAT_NUM_SER_ORD = 18;//NUMSER
    final int INT_TBL_DAT_NUM_SEC_ORD = 19;//NUMSEC
    final int INT_TBL_DAT_NUM_AUT_ORD = 20;//NUMAUT
    final int INT_TBL_DAT_COD_CRE = 21;//COD_CRE
    final int INT_TBL_DAT_BAS_IMP_GRB = 22;//BASEIG
    final int INT_TBL_DAT_COD_IVA = 23;//CODIVA
    final int INT_TBL_DAT_BAS_TAR_CER = 24;//BASEIT
    final int INT_TBL_DAT_IVA = 25;//IVA
    final int INT_TBL_DAT_BAS_IMP_ICE = 26;//BASEIC
    final int INT_TBL_DAT_COD_ICE = 27;//CODICE
    final int INT_TBL_DAT_ICE = 28;//ICE
    final int INT_TBL_DAT_IVA1 = 29;//IVA1
    final int INT_TBL_DAT_COD_RET1 = 30;//CODRET1
    final int INT_TBL_DAT_VAL_RET1 = 31;//VALRET1
    final int INT_TBL_DAT_IVA2 = 32;//IVA2
    final int INT_TBL_DAT_COD_RET2 = 33;//CODRET2
    final int INT_TBL_DAT_VAL_RET2 = 34;//VALRET2
    final int INT_TBL_DAT_DEV = 35;//DEVUEL
    final int INT_TBL_DAT_FEC_CAD = 36;//FECCAD
    final int INT_TBL_DAT_COD_RETF = 37;//CODRETF
    final int INT_TBL_DAT_VAL_RETF = 38;//VALRETF
    final int INT_TBL_DAT_BAS_RET = 39;//BASERET
    final int INT_TBL_DAT_FEC_RET = 40;//FECRET
    final int INT_TBL_DAT_SER_RET = 41;//SERRTE
    final int INT_TBL_DAT_NUM_RET = 42;//NUMRET
    final int INT_TBL_DAT_AUT_RET = 43;//AUTRET
    final int INT_TBL_DAT_BAS_RET2 = 44;//BASERET
    final int INT_TBL_DAT_COD_RETF2 = 45;//CODRET2
    final int INT_TBL_DAT_VAL_RETF2 = 46;//VALRETF    
    final int INT_TBL_DAT_BAS_RET3 = 47;//BASERET
    final int INT_TBL_DAT_COD_RETF3 = 48;//CODRET2
    final int INT_TBL_DAT_VAL_RETF3 = 49;//VALRETF
    final int INT_TBL_DAT_TIP_COM2 = 50;//TIPCOM2
    final int INT_TBL_DAT_FEC_EMI2 = 51;//FECEMI2
    final int INT_TBL_DAT_NUM_SER2 = 52;//NUMSER2
    final int INT_TBL_DAT_NUM_SEC2 = 53;//NUMSEC2
    final int INT_TBL_DAT_NUM_AUT2 = 54;//NUMAUT2
    final int INT_TBL_DAT_CON_CEP = 55;//CONCEPTO
    final int INT_TBL_DAT_REF = 56;//REFER
    final int INT_TBL_DAT_COD_PAG1 = 57;//CODFPAG1
    final int INT_TBL_DAT_COD_PAG2 = 58;//CODFPAG2
    final int INT_TBL_DAT_PAG_EXT = 59;//PAGOEXTE
    final int INT_TBL_DAT_COD_PAI = 60;//CODPAIS
    final int INT_TBL_DAT_CNV = 61;//CONVENI
    final int INT_TBL_DAT_RET_IEN = 62;//RETIENE
    final int INT_TBL_DAT_TIP_PRO = 63;//TIPOPROV
    final int INT_TBL_DAT_ES_REL = 64;//ESRELACI
    final int INT_TBL_DAT_VAL_COM_SOL = 65;//Valor de Compensacion Solidaria
    //Constantes del arreglo arlDatOrdCom
    static final int INT_ARL_DAT_COD_EMP1 = 0;
    static final int INT_ARL_DAT_COD_LOC1 = 1;
    static final int INT_ARL_DAT_COD_TIP_DOC1 = 2;
    static final int INT_ARL_DAT_COD_DOC1 = 3;
    static final int INT_ARL_DAT_POR_IVA1 = 4;

    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab, objTblFilCabRet;
    private ZafTblMod objTblMod, objTblModRet;
    private ZafTblPopMnu objTblPopMnu, objTblPopMnuRet;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".

    private ZafMouMotAdaRet objMouMotAdaRet;
    private ZafMouMotAda objMouMotAda;
    private boolean blnMarTodChkRet;
    private ZafTblTot objTblTot;                        //JTable de totales.

    private Vector vecDat, vecCab, vecReg, vecAux;
    private Vector vecDatRet, vecCabRet, vecRegRet;
    private boolean blnCon;                     //true: Continua la ejecucián del hilo.
    private String strMsg = "";
    private javax.swing.JOptionPane oppMsg;
    private String strCodVen, strNomVen;             //Contenido del campo al obtener el foco.
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCol;
    private ZafTblCelRenLbl objTblCelRenLblRet;

    private ZafTblBus objTblBus;

    private ZafSelFec objSelFec;

    private String strDesCorTipDoc, strDesLarTipDoc;
    private ZafVenCon vcoTipDoc, vcoCta;
    private ZafTblCelEdiChk objTblCelEdiChkRet;
    private ZafTblCelRenChk objTblCelRenChkRet;

    private ZafPerUsr objPerUsr;

    private boolean blnPerCon, blnPerExp, blnPerCer;
    private BigDecimal bdeIvaGlo;
    private String strVersion = "v1.50";

    /**
     * Crea una nueva instancia de la clase ZafIndRpt.
     */
    public ZafCon24(ZafParSis obj) {
        try {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);
            bdeIvaGlo = objParSis.getPorcentajeIvaCompras();
            bdeIvaGlo = bdeIvaGlo.divide(new BigDecimal(100));

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        panCorRpt = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRet = new javax.swing.JTable();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTotal = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butExp = new javax.swing.JButton();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(100, 150));
        jPanel4.setLayout(new java.awt.BorderLayout());

        panCorRpt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCorRpt.setPreferredSize(new java.awt.Dimension(560, 90));
        panCorRpt.setLayout(new java.awt.BorderLayout());
        jPanel4.add(panCorRpt, java.awt.BorderLayout.CENTER);
        panCorRpt.getAccessibleContext().setAccessibleName("Codigo");

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 60));
        jPanel2.setLayout(null);

        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        jPanel2.add(optTod);
        optTod.setBounds(0, 0, 440, 18);

        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        jPanel2.add(optFil);
        optFil.setBounds(0, 18, 440, 18);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        jPanel2.add(lblPrv);
        lblPrv.setBounds(21, 37, 100, 16);

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
        jPanel2.add(txtCodPrv);
        txtCodPrv.setBounds(134, 37, 80, 20);

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
        jPanel2.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(214, 37, 296, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        jPanel2.add(butPrv);
        butPrv.setBounds(510, 37, 20, 20);

        jPanel4.add(jPanel2, java.awt.BorderLayout.NORTH);

        panFil.add(jPanel4, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(0, 130));
        jPanel1.setLayout(new java.awt.BorderLayout());

        tblRet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblRet);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel1, java.awt.BorderLayout.CENTER);

        panFil.add(jPanel3, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

        tblDat.setToolTipText("");
        tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblDat.setMaximumSize(new java.awt.Dimension(2147483647, 192));
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

        tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotal.setViewportView(tblTotal);

        panRpt.add(spnTotal, java.awt.BorderLayout.SOUTH);

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

        butExp.setText("Exportar");
        butExp.setPreferredSize(new java.awt.Dimension(92, 25));
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panBot.add(butExp);

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


    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }

    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicacián.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm


    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if (optTod.isSelected()) {
            optFil.setSelected(false);
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
        } else {
            optFil.setSelected(true);
        }
}//GEN-LAST:event_optTodActionPerformed

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
        if (optFil.isSelected()) {
            optTod.setSelected(false);
        } else {
            optTod.setSelected(true);
        }
}//GEN-LAST:event_optFilActionPerformed

    private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
        txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

    private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
        strCodPrv = txtCodPrv.getText();
        txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

    private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)) {
            if (txtCodPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            } else {
                mostrarVenConPrv(1);
            }
        } else {
            txtCodPrv.setText(strCodPrv);
        }

        if (txtCodPrv.getText().length() > 0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
}//GEN-LAST:event_txtCodPrvFocusLost

    private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
        txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

    private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
        strDesLarPrv = txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

    private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)) {
            if (txtDesLarPrv.getText().equals("")) {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
                objTblMod.removeAllRows();
            } else {
                mostrarVenConPrv(2);
            }
        } else {
            txtDesLarPrv.setText(strDesLarPrv);
        }

        if (txtDesLarPrv.getText().length() > 0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }

}//GEN-LAST:event_txtDesLarPrvFocusLost

    private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
        strCodPrv = txtCodPrv.getText();
        mostrarVenConPrv(0);

}//GEN-LAST:event_butPrvActionPerformed

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        // TODO add your handling code here:
        java.io.File filExc;
        Process pro;
        try {
            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            if (System.getProperty("os.name").equals("Linux")) {
                filExc = new java.io.File("/tmp/ZafCon24.xls");
            } else {
                filExc = new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon24\\ZafCon24.xls");
            }
            String strNomHoj = "Hoja1";

            ZafCon24_01 objCon24_01 = new ZafCon24_01(tblDat, filExc, strNomHoj);

            if (objCon24_01.export()) {
                mostrarMsgInf("El archivo se cargó correctamente.");
                //Process pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon24/ZafCon24.xls");

                if (System.getProperty("os.name").equals("Linux")) {
                    pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCon24.xls");
                    System.out.println("LINUX: " + pro.toString());
                } else {
                    pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon24/ZafCon24.xls");
                    System.out.println("WINDOWS: " + pro.toString());
                }

            } else {
                mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpActionPerformed

    /**
     * Cerrar la aplicacián.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butPrv;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCorRpt;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblRet;
    private javax.swing.JTable tblTotal;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtDesLarPrv;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            blnPerCon = false;
            blnPerCer = false;
            blnPerExp = false;

            if (objParSis.getCodigoUsuario() == 1) {
                blnPerCon = true;
                blnPerCer = true;
                blnPerExp = true;
            } else {
                if (objPerUsr.isOpcionEnabled(1396)) {
                    blnPerCon = true;
                }
                if (objPerUsr.isOpcionEnabled(1397)) {
                    blnPerExp = true;
                }
                if (objPerUsr.isOpcionEnabled(1398)) {
                    blnPerCer = true;
                }
            }

            strAux = objParSis.getNombreMenu() + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCorRpt.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(65);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP, "Cod.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC, "Cod.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cod.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC, "Cód.Doc");
            vecCab.add(INT_TBL_DAT_COD_CLI, "Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI, "Nom.Cli");
            vecCab.add(INT_TBL_DAT_FEC_DOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_NOM_DOC, "Nom.Doc");
            vecCab.add(INT_TBL_DAT_NUM_DOC, "Num.Doc.");
            vecCab.add(INT_TBL_DAT_SUB, "Sub.");
            vecCab.add(INT_TBL_DAT_VAL_IVA, "Iva");
            vecCab.add(INT_TBL_DAT_TOT, "Tot");
            vecCab.add(INT_TBL_DAT_TIP_IDE, "Tip.Oid.");
            vecCab.add(INT_TBL_DAT_RUC, "Ruc");
            vecCab.add(INT_TBL_DAT_TIP_COM, "Tip.Com.");
            vecCab.add(INT_TBL_DAT_FEC_EMI, "Fec.Emi.");
            vecCab.add(INT_TBL_DAT_FEC_REG, "Fec.Reg.");
            vecCab.add(INT_TBL_DAT_NUM_SER_ORD, "Núm.Ser.");
            vecCab.add(INT_TBL_DAT_NUM_SEC_ORD, "Núm.Sec.");
            vecCab.add(INT_TBL_DAT_NUM_AUT_ORD, "Núm.Aut.");
            vecCab.add(INT_TBL_DAT_COD_CRE, "Cód.Cre.");
            vecCab.add(INT_TBL_DAT_BAS_IMP_GRB, "Bas.Imp.Gra.");
            vecCab.add(INT_TBL_DAT_COD_IVA, "Cód.Iva");
            vecCab.add(INT_TBL_DAT_BAS_TAR_CER, "Bas.Tar. 0");
            vecCab.add(INT_TBL_DAT_IVA, "Iva");
            vecCab.add(INT_TBL_DAT_BAS_IMP_ICE, "Bas.Imp.Ice");
            vecCab.add(INT_TBL_DAT_COD_ICE, "Cód.Ice.");
            vecCab.add(INT_TBL_DAT_ICE, "Ice");
            vecCab.add(INT_TBL_DAT_IVA1, "Iva1");
            vecCab.add(INT_TBL_DAT_COD_RET1, "Cód.Ret1");
            vecCab.add(INT_TBL_DAT_VAL_RET1, "Val.Ret1");
            vecCab.add(INT_TBL_DAT_IVA2, "Iva2");
            vecCab.add(INT_TBL_DAT_COD_RET2, "Cód.Ret2");
            vecCab.add(INT_TBL_DAT_VAL_RET2, "Val.Ret2");
            vecCab.add(INT_TBL_DAT_DEV, "Dev.");
            vecCab.add(INT_TBL_DAT_FEC_CAD, "Fec.Cad.");
            vecCab.add(INT_TBL_DAT_COD_RETF, "Cód.Retf");
            vecCab.add(INT_TBL_DAT_VAL_RETF, "Val.Retf");
            vecCab.add(INT_TBL_DAT_BAS_RET, "Bas.Ret.");
            vecCab.add(INT_TBL_DAT_FEC_RET, "Fec.Ret.");
            vecCab.add(INT_TBL_DAT_SER_RET, "Ser.Ret.");
            vecCab.add(INT_TBL_DAT_NUM_RET, "Núm.Ret.");
            vecCab.add(INT_TBL_DAT_AUT_RET, "Aut.Ret.");
            vecCab.add(INT_TBL_DAT_BAS_RET2, "Bas.Ret2");
            vecCab.add(INT_TBL_DAT_COD_RETF2, "Cod.Retf2.");
            vecCab.add(INT_TBL_DAT_VAL_RETF2, "Val.Retf2.");
            vecCab.add(INT_TBL_DAT_BAS_RET3, "Bas.Ret3.");
            vecCab.add(INT_TBL_DAT_COD_RETF3, "Cód.Retf3.");
            vecCab.add(INT_TBL_DAT_VAL_RETF3, "Val.Retf3.");
            vecCab.add(INT_TBL_DAT_TIP_COM2, "Tip.Com2.");
            vecCab.add(INT_TBL_DAT_FEC_EMI2, "Fec.Emi2.");
            vecCab.add(INT_TBL_DAT_NUM_SER2, "Núm.Ser2.");
            vecCab.add(INT_TBL_DAT_NUM_SEC2, "Núm.Sec2.");
            vecCab.add(INT_TBL_DAT_NUM_AUT2, "Num.Aut2.");
            vecCab.add(INT_TBL_DAT_CON_CEP, "Concepto");
            vecCab.add(INT_TBL_DAT_REF, "Refer.");
            vecCab.add(INT_TBL_DAT_COD_PAG1, "Cód.Pag1.");
            vecCab.add(INT_TBL_DAT_COD_PAG2, "Cód.Pag2.");
            vecCab.add(INT_TBL_DAT_PAG_EXT, "Pag.Ext.");
            vecCab.add(INT_TBL_DAT_COD_PAI, "Cód.Pai.");
            vecCab.add(INT_TBL_DAT_CNV, "Conveni");
            vecCab.add(INT_TBL_DAT_RET_IEN, "Retiene");
            vecCab.add(INT_TBL_DAT_TIP_PRO, "Tip.Prov.");
            vecCab.add(INT_TBL_DAT_ES_REL, "EsRelac.");
            vecCab.add(INT_TBL_DAT_VAL_COM_SOL, "Val.Com.Sol.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Tamaáo de las celdas
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NOM_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SUB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_IDE).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_RUC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_COM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SER_ORD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SEC_ORD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT_ORD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_CRE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_GRB).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_IVA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_TAR_CER).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_IVA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_ICE).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_ICE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ICE).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IVA1).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_RET1).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET1).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_IVA2).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_RET2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAD).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_RETF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_RET).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_SER_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_AUT_RET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_RETF2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET3).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_RETF3).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF3).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_COM2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMI2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SER2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SEC2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CON_CEP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_REF).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAG1).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAG2).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_PAG_EXT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_PAI).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CNV).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_RET_IEN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_TIP_PRO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_ES_REL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_COM_SOL).setPreferredWidth(50);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            //objTblCelRenLbl.setForeground(Color.BLACK);
            tcmAux.getColumn(INT_TBL_DAT_SUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_GRB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_TAR_CER).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IVA1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET1).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_IVA2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF2).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_RET3).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RETF3).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP_ICE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_ICE).setCellRenderer(objTblCelRenLbl);

            tblDat.getTableHeader().setReorderingAllowed(false);

            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);

            configurarVenConPrv();
            configurarFrmRet();

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[] = {INT_TBL_DAT_VAL_RETF, INT_TBL_DAT_VAL_RET1, INT_TBL_DAT_VAL_RET2, INT_TBL_DAT_BAS_IMP_GRB, INT_TBL_DAT_BAS_TAR_CER, INT_TBL_DAT_IVA, INT_TBL_DAT_IVA1, INT_TBL_DAT_IVA2};
            objTblTot = new ZafTblTot(spnDat, spnTotal, tblDat, tblTotal, intCol);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);

            //para tabla de totales
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);

            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setResizable(false);

            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setResizable(false);

            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setResizable(false);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrmRet() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDatRet = new Vector();    //Almacena los datos
            vecCabRet = new Vector(7);    //Almacena las cabeceras
            vecCabRet.clear();
            vecCabRet.add(INT_TBL_DAT_RET_LIN, "");
            vecCabRet.add(INT_TBL_DAT_RET_CHK, "");
            vecCabRet.add(INT_TBL_DAT_RET_COD, "Cód.Ret.");
            vecCabRet.add(INT_TBL_DAT_RET_DES_COR, "Tip.Doc");
            vecCabRet.add(INT_TBL_DAT_RET_DES_LAR, "Tipo de Documento");
            vecCabRet.add(INT_TBL_DAT_RET_POR_RET, "% Ret.");
            vecCabRet.add(INT_TBL_DAT_RET_APL, "Aplica");

            objTblModRet = new ZafTblMod();
            objTblModRet.setHeader(vecCabRet);
            tblRet.setModel(objTblModRet);

            //Configurar JTable: Establecer tipo de seleccián.
            tblRet.setRowSelectionAllowed(true);
            tblRet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnuRet = new ZafTblPopMnu(tblRet);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaRet = new ZafMouMotAdaRet();
            tblRet.getTableHeader().addMouseMotionListener(objMouMotAdaRet);

            javax.swing.table.TableColumnModel tcmAux = tblRet.getColumnModel();

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabRet = new ZafTblFilCab(tblRet);
            tcmAux.getColumn(INT_TBL_DAT_RET_LIN).setCellRenderer(objTblFilCabRet);
            tblRet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Tamaáo de las celdas
            tcmAux.getColumn(INT_TBL_DAT_RET_LIN).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_RET_CHK).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_RET_COD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_RET_DES_COR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_RET_DES_LAR).setPreferredWidth(210);
            tcmAux.getColumn(INT_TBL_DAT_RET_POR_RET).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_RET_APL).setPreferredWidth(50);

            tblRet.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tblDat.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setResizable(false);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkRet = new ZafTblCelRenChk();
            tblRet.getColumnModel().getColumn(INT_TBL_DAT_RET_CHK).setCellRenderer(objTblCelRenChkRet);
            objTblCelRenChkRet = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkRet = new ZafTblCelEdiChk(tblRet);
            tblRet.getColumnModel().getColumn(INT_TBL_DAT_RET_CHK).setCellEditor(objTblCelEdiChkRet);
            objTblCelEdiChkRet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });

            objTblCelRenLblRet = new ZafTblCelRenLbl();
            objTblCelRenLblRet.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblRet.setTipoFormato(objTblCelRenLblRet.INT_FOR_NUM);
            objTblCelRenLblRet.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_DAT_RET_POR_RET).setCellRenderer(objTblCelRenLblRet);
            objTblCelRenLblRet = null;

            //Configurar JTable: Establecer los listener para el TableHeader.
            tblRet.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblRetMouseClicked(evt);
                }
            });

            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_RET_CHK);
            objTblModRet.setColumnasEditables(vecAux);
            vecAux = null;
            tcmAux = null;

            objTblModRet.setModoOperacion(objTblMod.INT_TBL_NO_EDI);

            cargarDatosRetencion();

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite cargar la información de la retención
     *
     * @return true: Si se pudo efectuar la consulta
     * <BR> false: En el caso contrario
     */
    private boolean cargarDatosRetencion() {
        boolean blnRes = true;
        vecDatRet.clear();
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "";
                strSQL += " SELECT co_emp, co_tipret, tx_descor, tx_deslar, nd_porret, tx_aplret";
                strSQL += " FROM tbm_cabtipret AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " ORDER by nd_porret";
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {
                    vecRegRet = new Vector();
                    vecRegRet.add(INT_TBL_DAT_RET_LIN, "");
                    vecRegRet.add(INT_TBL_DAT_RET_CHK, null);
                    vecRegRet.add(INT_TBL_DAT_RET_COD, "" + rst.getString("co_tipret"));
                    vecRegRet.add(INT_TBL_DAT_RET_DES_COR, "" + rst.getString("tx_descor"));
                    vecRegRet.add(INT_TBL_DAT_RET_DES_LAR, "" + rst.getString("tx_deslar"));
                    vecRegRet.add(INT_TBL_DAT_RET_POR_RET, "" + rst.getString("nd_porret"));
                    vecRegRet.add(INT_TBL_DAT_RET_APL, "" + rst.getString("tx_aplret"));
                    vecDatRet.add(vecRegRet);

                    vecRegRet.setElementAt(new Boolean(true), INT_TBL_DAT_RET_CHK);

                    blnMarTodChkRet = false;
                }
                con.close();
                con = null;
                stm.close();
                stm = null;
                rst.close();
                rst = null;

                //Asignar vectores al modelo.
                objTblModRet.setData(vecDatRet);
                tblRet.setModel(objTblModRet);
                pgrSis.setValue(0);
                butCon.setText("Consultar");

            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL += " FROM tbm_cli AS a1";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            if (objParSis.getCodigoMenu() == 1395) {
                strSQL += " AND a1.st_prv='S'";
            } else {
                strSQL += " AND a1.st_cli='S'";
            }

            strSQL += " AND a1.st_reg='A'";
            strSQL += " ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 4;
            vcoPrv = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        objTblMod.removeAllRows();

                        optFil.setSelected(true);
                        optTod.setSelected(false);

                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        objTblMod.removeAllRows();
                        optFil.setSelected(true);
                        optTod.setSelected(false);
                    } else {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            objTblMod.removeAllRows();
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        } else {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        objTblMod.removeAllRows();
                        optFil.setSelected(true);
                        optTod.setSelected(false);
                    } else {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            objTblMod.removeAllRows();
                            optFil.setSelected(true);
                            optTod.setSelected(false);
                        } else {
                            txtDesLarPrv.setText(strDesLarPrv);
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta clase crea un hilo que permite manipular la interface gráfica de
     * usuario (GUI). Por ejemplo: se la puede utilizar para cargar los datos en
     * un JTable donde la idea es mostrar al usuario lo que está ocurriendo
     * internamente. Es decir a medida que se llevan a cabo los procesos se
     * podráa presentar mensajes informativos en un JLabel e ir incrementando un
     * JProgressBar con lo cual el usuario estaráa informado en todo momento de
     * lo que ocurre. Si se desea hacer ásto es necesario utilizar ásta clase ya
     * que si no sálo se apreciaráa los cambios cuando ha terminado todo el
     * proceso.
     */
    private class ZafThreadGUI extends Thread {

        public void run() {
            if (!cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    /**
     * Esta función permite establecer la conexión para consultas DML
     *
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes = true, blnExiReg, blnEsDocPorPag, blnFacPrvIgu1;
        strAux = "";
        String strAuxAux="";
        String strAux2 = "", strAuxLoc, strCodRetFte, strBasRetFte, strBasRetFte2, strCodRetFte2, strCodRetFte3, strDesCorTipDocLoc, strValRet1;
        int i, j, k, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp_RelRet, intCodLoc_RelRet, intCodTipDoc_RelRet, intCodDoc_RelRet;
        int intCodIvaSri, intCodEmpAux, intCodLocAux, intCodTipDocAux, intCodDocAux;
        BigDecimal bdeAux, bdeBasTarCer, bdeBasImpGra, bdeSub, bdeValIva_Sub, bdeValIva_BasImpGra, bdePorIva, bdePorIvaAux, bdeValIva1_Iva2;
        Date datFecEmi, datFecIniFacIva14, datFecFinFacIva14;
        ArrayList arlRegOrdCom, arlDatOrdCom;
        
        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (con != null) {
                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Básqueda por rangos
                        strAux += " AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux += " AND a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux += " AND a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }

                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Básqueda por rangos
                        strAux2 += " AND a4.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux2 += " AND a4.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux2 += " AND a4.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }

                if (!txtCodPrv.getText().equals("")) {
                    strAux += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                    strAuxAux += " AND a9.co_cli=" + txtCodPrv.getText() + "";
                }

                stm = con.createStatement();
                //lblMsgSis.setText("Cargando datos...");
                strSQL = "";
                //--1- Para obtener LA INFORMACION GENERAL DEL FORMULARIO
                strSQL += "	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli";
                strSQL += "	, b1.fe_doc, b1.NOM_DOC, b1.ne_numDoc1, b1.nd_sub, b1.nd_valIva, b1.nd_tot";
                strSQL += "	, b1.TIPOID, b1.RUC, b1.TIPCOM, b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel, b1.FECCAD, b1.ne_codIva, b1.cod_pag1, b1.nd_valComSol";
                strSQL += "	, b2.COD_CRE AS b2_COD_CRE, b2.BASEIG AS b2_BASEIG, b2.CODIVA AS b2_CODIVA, b2.BASEIT AS b2_BASEIT, b2.FECCAD AS b2_FECCAD/*, b2.CODRETF AS b2_CODRETF*/";
                strSQL += "	, b2.VALRETF AS b2_VALRETF, b2.BASERET AS b2_BASERET, b2.FECRET AS b2_FECRET, b2.SERRTE AS b2_SERRTE, b2.NUMRET AS b2_NUMRET, b2.AUTRET AS b2_AUTRET																";

                //strSQL+="	, CASE WHEN b2.BASEIG IS NOT NULL THEN (b2.BASEIG*0.12) ELSE 0 END AS b2_VALIVA";//lo calculo porque ya no se que hacer
                strSQL += "	, CASE WHEN b2.BASEIG IS NOT NULL THEN (b2.BASEIG * (b2.nd_poriva / 100)) ELSE 0 END AS b2_VALIVA";//lo calculo porque ya no se que hacer

                //strSQL+="	, b3.COD_CRE AS b3_COD_CRE, b3.BASEIG AS b3_BASEIG, b3.CODIVA AS b3_CODIVA, b3.BASEIT AS b3_BASEIT, b3.FECCAD AS b3_FECCAD, b3.CODRETF AS b3_CODRETF";
                //strSQL+="	, b3.VALRETF AS b3_VALRETF, b3.BASERET AS b3_BASERET, b3.FECRET AS b3_FECRET, b3.SERRTE AS b3_SERRTE, b3.NUMRET AS b3_NUMRET, b3.AUTRET AS b3_AUTRET";
                strSQL += "	, b4.COD_CRE AS b4_COD_CRE, CASE WHEN b4.BASEIG IS NULL THEN 0 ELSE b4.BASEIG END AS b4_BASEIG, b4.CODIVA AS b4_CODIVA, CASE WHEN b4.BASEIT IS NULL THEN 0 ELSE b4.BASEIT END AS b4_BASEIT, b4.FECCAD AS b4_FECCAD																";
                strSQL += "	, b4.CODRETF AS b4_CODRETF, b4.VALRETF AS b4_VALRETF, b4.BASERET AS b4_BASERET, b4.FECRET, b4.SERRTE, b4.NUMRET, b4.AUTRET																";
                strSQL += "	, b5.COD_CRE AS b5_COD_CRE, b5.BASEIG AS B5_BASEIG, b5.CODIVA AS b5_CODIVA, b5.FECCAD AS b5_FECCAD/*, b5.CODRETF AS b5_CODRETF*/																";
                strSQL += "	, b5.VALRETF AS b5_VALRETF, b5.BASERET AS b5_BASERET, b5.FECRET AS b5_FECRET, b5.SERRTE AS b5_SERRTE, b5.NUMRET AS b5_NUMRET, b5.AUTRET AS b5_AUTRET																";
                strSQL += "	, b6.COD_CRE AS b6_COD_CRE, b6.BASEIG AS b6_BASEIG, b6.CODIVA AS b6_CODIVA, b6.FECCAD AS b6_FECCAD/*, b6.CODRETF AS b6_CODRETF*/";
                strSQL += "	, b6.VALRETF AS b6_VALRETF, b6.BASERET AS b6_BASERET, b6.FECRET AS b6_FECRET, b6.SERRTE AS b6_SERRTE, b6.NUMRET AS b6_NUMRET, b6.AUTRET AS b6_AUTRET";
                strSQL += "	, b7.COD_CRE AS b7_COD_CRE, b7.BASEIG AS b7_BASEIG, b7.CODIVA AS b7_CODIVA,  b7.FECCAD AS b7_FECCAD/*, b7.CODRETF AS b7_CODRETF*/";
                strSQL += "	, b7.VALRETF AS b7_VALRETF, b7.BASERET AS b7_BASERET, b7.FECRET AS b7_FECRET, b7.SERRTE AS b7_SERRTE, b7.NUMRET AS b7_NUMRET, b7.AUTRET AS b7_AUTRET";
                strSQL += "	, b8.COD_CRE AS b8_COD_CRE, b8.BASEIG AS b8_BASEIG, b8.CODIVA AS b8_CODIVA, b8.BASEIT AS b8_BASEIT, b8.FECCAD AS b8_FECCAD, b8.CODRETF AS b8_CODRETF";
                strSQL += "	, b8.VALRETF AS b8_VALRETF, b8.BASERET AS b8_BASERET, b8.FECRET AS b8_FECRET, b8.SERRTE AS b8_SERRTE, b8.NUMRET AS b8_NUMRET, b8.AUTRET AS b8_AUTRET";
                strSQL += "	, b9.COD_CRE AS b9_COD_CRE, b9.BASEIG AS b9_BASEIG, b9.CODIVA AS b9_CODIVA, b9.BASEIT AS b9_BASEIT, b9.FECCAD AS b9_FECCAD, b9.CODRETF AS b9_CODRETF";
                strSQL += "	, b9.VALRETF AS b9_VALRETF, b9.BASERET AS b9_BASERET, b9.FECRET AS b9_FECRET, b9.SERRTE AS b9_SERRTE, b9.NUMRET AS b9_NUMRET, b9.AUTRET AS b9_AUTRET";
                strSQL += "	, b1.SERRTE AS b1_SERRTE, b1.NUMAUTSRI AS b1_NUMAUTSRI";
                strSQL += "	FROM(																";
                
                strSQL += "select c1.*, ";
                strSQL += "( case when c1.ne_tipforpag = 1 and c1.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "when c1.fe_doc >= '2016-09-01' and c1.ne_tipforpag = 4 and c1.nd_tot >= 1000 then 19 "; //19 = Tarjeta de credito
                strSQL += "when c1.fe_doc >= '2016-09-01' and c1.ne_tipforpag not in (1,4) and c1.nd_tot >= 1000 then 20 "; //20 = Otros con utilizacion del sistema financiero
                strSQL += "when c1.fe_doc < '2016-09-01'  and c1.ne_tipforpag in (4) and c1.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "when c1.fe_doc < '2016-09-01'  and c1.ne_tipforpag not in (1,4) and c1.nd_tot >= 1000 then 2 "; //2 = Cheque propio (credito)
                strSQL += "else null ";
                strSQL += "end ) as cod_pag1 ";
                strSQL += "from ";
                strSQL += "		( SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli															";
                strSQL += "		 , b1.fe_doc, b1.NOM_DOC, b1.ne_numDoc1, SUM(b1.nd_sub) AS nd_sub, SUM(b1.nd_valIva) AS nd_valIva, SUM(b1.nd_tot) AS nd_tot															";
                strSQL += "		 , b1.TIPOID, b1.RUC, b1.TIPCOM															";
                strSQL += "		 , b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel, b1.FECCAD, b1.SERRTE, b1.NUMAUTSRI, b1.ne_codIva, b1.nd_valcomsol, max(b1.ne_tipforpag) as ne_tipforpag FROM(															";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, /*a2.co_reg,*/ a1.co_cli, a1.tx_nomCli														";
                strSQL += "			 , a1.fe_doc, c1.tx_desCor AS NOM_DOC, a1.ne_numDoc1, a6.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot														";
                strSQL += "			 , b1.tx_tipide AS TIPOID, a1.tx_ruc AS RUC, CASE WHEN a4.co_tipDoc=57 THEN '03' ELSE '01' END AS TIPCOM														";
                strSQL += "			 , a1.fe_doc AS FEC_EMI, a1.fe_doc AS FEC_REG, a3.tx_numser AS NUMSER, a3.tx_numChq AS NUMSEC ";
                //strSQL+="                      ,  a3.tx_numAutSRI AS NUMAUT "; // Se comenta esta condicion porque ahora, si el CodTipDoc = 230 (RFP1E) se va a buscar el numero de autorizacion en la tabla tbm_cabpag. Si es diferente a 230, va a seguir buscando en tbm_pagMovInv
                strSQL += "                        , (case when a1.co_tipDoc <> 230 then a3.tx_numAutSRI else a1.tx_numAutFacEle end) AS NUMAUT ";
                strSQL += "			 , CASE WHEN a9.co_empgrp IS NOT NULL THEN 'S' ELSE '' END AS st_empRel, a3.tx_fecCad AS FECCAD, b3.tx_numserfac AS SERRTE, b3.tx_numautsri AS NUMAUTSRI, ";
                strSQL += "( case when a4.co_emp = 2 and a4.co_loc = 4 and a4.fe_doc >= '2016-06-01' and a4.fe_doc<'2017-06-01' then 2 "; //2 = Cod. Iva 12%; Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "       when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01' then 2 "; //2 = Cod. Iva 12%
                strSQL += "       else 3 "; //3 = Cod. Iva 14%
                strSQL += "end ) as ne_codIva, ";

//                strSQL += "( case when a6.ne_tipforpag in (1,19) and a4.nd_tot >= 1000 then '19' "; //1 = Contado; 19 = Tarjeta de credito
//                strSQL += "       when a6.ne_tipforpag not in (1,19) and a4.nd_tot >= 1000 then '20' ";
//                strSQL += "       else '' ";
//                strSQL += "end ) as cod_pag1, ";
                strSQL += "       ( case when a6.ne_tipforpag = 1 and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a6.ne_tipforpag = 4 and a4.nd_tot >= 1000 then 19 "; //19 = Tarjeta de credito
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a6.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 20 "; //20 = Otros con utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a6.ne_tipforpag in (4) and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a6.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 2 "; //2 = Cheque propio (credito)
                strSQL += "              else null ";
                strSQL += "       end ) as cod_pag1, ";

                strSQL += "( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then a4.nd_sub * 0.02 "; //Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "       else 0 ";
                strSQL += "end ) as nd_valComSol ";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN														";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)														";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)														";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 														";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)													";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc													";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   													";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg														";
                strSQL += "			 INNER JOIN (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)														";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc													";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5														";
                strSQL += "				 ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet													";
                strSQL += "                        LEFT OUTER JOIN tbm_cabforpag as a6 on a4.co_emp = a6.co_emp and a4.co_forpag = a6.co_forpag ";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "														";
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)														";
                strSQL += "			 " + strAux + "";// AND a1.fe_doc BETWEEN'2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')														";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, /*a2.co_reg,*/ a1.co_cli, a1.tx_nomCli														";
                strSQL += "			 , a1.fe_doc, c1.tx_desCor, a1.ne_numDoc1, a4.co_emp, a4.co_loc, a4.fe_doc, a6.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot														";
                strSQL += "			 , b1.tx_tipide, a1.tx_ruc, a4.co_tipDoc														";
                strSQL += "			 , a1.fe_doc, a1.fe_doc, a3.tx_numser, a3.tx_numChq, a3.tx_numAutSRI ";
                strSQL += "                        , a1.tx_numAutFacEle "; // Se agrego esta linea debido a que si el CodTipDoc = 230 (RFP1E) se va a buscar el numero de autorizacion en la tabla tbm_cabpag
                strSQL += "                        , a9.co_empgrp, a3.tx_fecCad, b3.tx_numserfac, b3.tx_numautsri		";
                strSQL += "			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc	";
                strSQL += "		) AS b1															";
                strSQL += "		GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli															";
                strSQL += "		 , b1.fe_doc, b1.NOM_DOC, b1.ne_numDoc1															";
                strSQL += "		 , b1.TIPOID, b1.RUC, b1.TIPCOM															";
                strSQL += "		 , b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel, b1.FECCAD, b1.SERRTE, b1.NUMAUTSRI, b1.ne_codIva, b1.nd_valComSol ";
                strSQL += "		ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc															";
                strSQL += ") as c1 ";
                strSQL += "	) AS b1																";
                
                strSQL += "	LEFT OUTER JOIN(																";
                //--2.- Para obtener el 1%(1)  5%(2)  8%(3)  2%(8)  1%(9)  2%(11)  10%(12)  QUE NO ESTEN ASOCIADOS CON TRANSPORTE
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA, SUM(a1.BASEIT) AS BASEIT, a1.FECCAD															";
                strSQL += "		, /*a1.CODRETF,*/ SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET, a1.nd_poriva FROM(															";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG														";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT														";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF														";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE														";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a4.nd_poriva														";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN														";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)														";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)														";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 														";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)													";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc													";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   													";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg														";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4														";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc													";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7														";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 													";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8														";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot													";
                strSQL += "			 INNER JOIN tbm_polRet AS a6														";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra													";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5														";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet													";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "														";
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)														";
                strSQL += "			 " + strAux;// AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(1,2,3,8,9,11,12,13)";
                strSQL += "			 AND a8.co_mot NOT IN(8) AND a3.tx_codSri NOT IN('310')";//  --para que no salga transporte
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')														";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp														";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo														";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac														";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a4.nd_poriva														";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";

                strSQL += "			 UNION";

                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG														";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT														";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF														";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE														";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a4.nd_poriva														";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN														";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)														";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)														";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 														";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)													";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc													";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   													";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg														";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4														";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc													";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5														";
                strSQL += "				 ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet													";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "														";
                strSQL += "			 " + strAux;//AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'  --'2012-10-17' AND '2012-10-17'
                strSQL += "			 AND a5.co_tipRet IN(1,2,3,8,9,11,12,13)";
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')														";
                strSQL += "			 AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)														";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp														";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo														";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac														";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a4.nd_poriva														";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";
                strSQL += "			 ORDER BY co_emp, co_loc, co_tipDoc, co_doc";

                strSQL += "		) AS a1															";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA, a1.FECCAD															";
                strSQL += "		/*, a1.CODRETF*/, a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET, a1.nd_poriva															";
                strSQL += "		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc															";
                strSQL += "	) AS b2																";
                strSQL += "	ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc																";
                strSQL += "	LEFT OUTER JOIN(																";
                //--4.- Para obtener el 1%(1)  5%(2)  8%(3)  2%(8)  1%(9)  2%(11)  10%(12)  SOLO TRANSPORTE															
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA, SUM(a1.BASEIT) AS BASEIT															";
                strSQL += "		, a1.FECCAD, a1.CODRETF, SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET, a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET FROM(															";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG														";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT														";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF														";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE														";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet														";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN														";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)														";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)														";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 														";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)													";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc													";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   													";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg														";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4														";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc													";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7														";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 													";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8														";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot													";
                strSQL += "			 INNER JOIN tbm_polRet AS a6														";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra													";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5														";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet													";
                strSQL += "																	";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "														";
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)														";
                strSQL += "			 " + strAux; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(1,2,3,8,9,11,12,13)";
                strSQL += "			 AND a8.co_mot IN(8) AND a3.tx_codSri IN('310')";//  --para que no salga transporte
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')														";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp														";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo														";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac														";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet														";
                strSQL += "			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet														";
                strSQL += "		) AS a1															";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA															";
                strSQL += "		, a1.FECCAD, a1.CODRETF, a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET															";
                strSQL += "	) AS b4																";
                strSQL += "	ON b1.co_emp=b4.co_emp AND b1.co_loc=b4.co_loc AND b1.co_tipDoc=b4.co_tipDoc AND b1.co_doc=b4.co_doc																";
                strSQL += "	LEFT OUTER JOIN(																";
                //--5.- Para obtener el 30%(4)  70%(5)  100%(6)  10%  20%
                //strSQL+="		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, ROUND((SUM(a1.BASEIG)*0.12),2) AS BASEIG, a1.CODIVA		";
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, ROUND((SUM(a1.BASEIG) * (a1.nd_poriva / 100)),2) AS BASEIG, a1.CODIVA		";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET, a1.nd_poriva FROM(															";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg														";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, a3.nd_basImp AS BASEIG					";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA							";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF														";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE														";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva, a4.nd_poriva ";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN														";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)														";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)														";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 														";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)													";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc													";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   													";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg														";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4														";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc													";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7														";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 													";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8														";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot													";
                strSQL += "			 INNER JOIN tbm_polRet AS a6														";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra													";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5														";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet													";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "														";
                strSQL += "			 " + strAux;//AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(4,5,6,14,15)";//1,8,9,11
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "			 AND a4.nd_valIva<>0";
                strSQL += "			  AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')														";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg	";
                strSQL += "			 , a4.nd_valIva";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo				";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac				";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a4.nd_poriva				";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet	";

                strSQL += "			 UNION ";

                strSQL += "			 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, a3.nd_basImp  AS BASEIG";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva, a4.nd_poriva";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 ";
                strSQL += "			 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4";
                strSQL += "                           ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5";
                strSQL += "                           ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "			 " + strAux;//AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(4,5,6,14,15)";//1,8,9,11
                strSQL += "			 AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "			 AND a4.nd_valIva<>0";
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += "			 , a4.nd_valIva";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a4.nd_poriva";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";
                strSQL += "			 ORDER BY co_emp, co_loc, co_tipDoc, co_doc, nd_porRet";
                strSQL += "		) AS a1										";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET, a1.nd_poriva	";
                strSQL += "	) AS b5									";
                strSQL += "	ON b1.co_emp=b5.co_emp AND b1.co_loc=b5.co_loc AND b1.co_tipDoc=b5.co_tipDoc AND b1.co_doc=b5.co_doc";
                strSQL += "	LEFT OUTER JOIN(";
                //--6.- Para obtener el 30%(4)  10%(14)
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET FROM(";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                //strSQL+="			 , CAST('1' AS CHARACTER VARYING) AS COD_CRE, ";
                strSQL += "                        , CASE WHEN a5.co_tipRet=4 THEN CAST('1' AS CHARACTER VARYING) WHEN a5.co_tipRet=14 THEN CAST('9' AS CHARACTER VARYING) ELSE '' END AS COD_CRE, ";
                strSQL += "                        a3.nd_basImp AS BASEIG";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 ";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc ";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot";
                strSQL += "			 INNER JOIN tbm_polRet AS a6";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "	" + strAux;//AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(4,14)";// --solo el 30% y 10%   1,9
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "			 AND a4.nd_valIva<>0";
                strSQL += "			  AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += "			 , a4.nd_valIva, a3.tx_fecCad, a3.tx_codsri, a2.nd_abo";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac, a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a5.co_tipRet";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";

                strSQL += "                       UNION";

                strSQL += "                       SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                //strSQL+="                       , CAST('1' AS CHARACTER VARYING) AS COD_CRE, ";
                strSQL += "                       , CASE WHEN a5.co_tipRet=4 THEN CAST('1' AS CHARACTER VARYING) WHEN a5.co_tipRet=14 THEN CAST('9' AS CHARACTER VARYING) ELSE '' END AS COD_CRE, ";
                strSQL += "                       a3.nd_basImp AS BASEIG";
                strSQL += "                       , CAST('2' AS CHARACTER VARYING) AS CODIVA";
                strSQL += "                       ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF";
                strSQL += "                       ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE";
                strSQL += "                       , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva";
                strSQL += "                       FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN";
                strSQL += "                       tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL += "                       INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL += "                       LEFT OUTER JOIN tbm_datautsri AS b3";
                strSQL += "                           ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL += "                       INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += "                       INNER JOIN tbm_pagMovInv AS a3";
                strSQL += "                       ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL += "                       INNER JOIN tbm_cabMovInv AS a4";
                strSQL += "                           ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL += "                       INNER JOIN tbm_cabTipRet AS a5";
                strSQL += "                           ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet";
                strSQL += "                       WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "                       " + strAux;//AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "                       AND a5.co_tipRet IN(4,14)";//1,9
                strSQL += "                       AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "                       AND a4.nd_valIva<>0";
                strSQL += "                       AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL += "                       GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += "                       , a4.nd_valIva";
                strSQL += "                       , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo";
                strSQL += "                       , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac";
                strSQL += "                       , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a5.co_tipRet";
                //strSQL+="		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";
                strSQL += "                       ORDER BY co_emp, co_loc, co_tipDoc, co_doc, nd_porRet";
                strSQL += "		) AS a1";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET";
                strSQL += "		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL += "	) AS b6";
                strSQL += "	ON b1.co_emp=b6.co_emp AND b1.co_loc=b6.co_loc AND b1.co_tipDoc=b6.co_tipDoc AND b1.co_doc=b6.co_doc";
                strSQL += "	LEFT OUTER JOIN(															";
                //--.- Para obtener el 70%(5)  100%(6)  20%(15)
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET FROM(														";
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                //strSQL+="			 , CAST('02' AS CHARACTER VARYING) AS COD_CRE"; //el 70%(5) se pone '02'; 100%(6) se pone '03'; 20%(15) se pone '10'

                strSQL += "			 , CASE WHEN a5.co_tipRet=5 THEN CAST('2' AS CHARACTER VARYING) ";
                strSQL += "			        WHEN a5.co_tipRet=6 THEN CAST('3' AS CHARACTER VARYING) ";
                strSQL += "			        WHEN a5.co_tipRet=15 THEN CAST('10' AS CHARACTER VARYING) ";
                strSQL += "                               ELSE '' END AS COD_CRE";

                strSQL += "			 , a3.nd_basImp AS BASEIG";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF													";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE													";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva													";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN													";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)													";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)													";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 													";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)												";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc												";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   												";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg													";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4													";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc												";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7													";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 												";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8													";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot												";
                strSQL += "			 INNER JOIN tbm_polRet AS a6													";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra												";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5													";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet												";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "													";
                strSQL += "			 " + strAux; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(5,6,15) ";//--el 70%(5)  100%(6)  20%   3,8,12
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "			 AND a4.nd_valIva<>0";
                strSQL += "			  AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')													";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , a4.nd_valIva		";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo													";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac													";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a5.co_tipRet													";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";

                strSQL += "			 UNION";

                strSQL += "			 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                //strSQL+="			 , CAST('02' AS CHARACTER VARYING) AS COD_CRE"; //el 70%(5) se pone '02'; 100%(6) se pone '03'; 20%(15) se pone '10'

                strSQL += "			 , CASE WHEN a5.co_tipRet=5 THEN CAST('2' AS CHARACTER VARYING) ";
                strSQL += "			        WHEN a5.co_tipRet=6 THEN CAST('3' AS CHARACTER VARYING) ";
                strSQL += "			        WHEN a5.co_tipRet=15 THEN CAST('10' AS CHARACTER VARYING) ";
                strSQL += "                               ELSE '' END AS COD_CRE";

                strSQL += "			 , a3.nd_basImp AS BASEIG";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet, a3.nd_valIva";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 ";
                strSQL += "                           ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4";
                strSQL += "                           ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5";
                strSQL += "                           ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "			 " + strAux; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(5,6,15)";//3,8,12
                strSQL += "			 AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)";
                strSQL += "			 AND a4.nd_valIva<>0";
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL += "			 , a4.nd_valIva";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet, a3.nd_valIva, a5.co_tipRet";
                //strSQL+="			 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet";
                strSQL += "			 ORDER BY co_emp, co_loc, co_tipDoc, co_doc, nd_porRet";
                strSQL += "		) AS a1														";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA														";
                strSQL += "		, a1.FECCAD, /*a1.CODRETF,*/ a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET														";
                strSQL += "		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                strSQL += "	) AS b7															";
                strSQL += "	ON b1.co_emp=b7.co_emp AND b1.co_loc=b7.co_loc AND b1.co_tipDoc=b7.co_tipDoc AND b1.co_doc=b7.co_doc															";
                strSQL += "	LEFT OUTER JOIN(															";
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA, SUM(a1.BASEIT) AS BASEIT, a1.FECCAD														";
                strSQL += "		, a1.CODRETF, SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET FROM(														";
                //                        --2.- Para obtener el 1%(1)  5%(2)  8%(3)  1%(9)  10%(12)  QUE NO ESTEN ASOCIADOS CON TRANSPORTE 													
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG													";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT													";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF													";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE													";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet													";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN													";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)													";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)													";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 													";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)												";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc												";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   												";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg													";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4													";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc												";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7													";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 												";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8													";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot												";
                strSQL += "			 INNER JOIN tbm_polRet AS a6													";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra												";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5													";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet												";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "													";
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)													";
                strSQL += "			 " + strAux; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'  --'2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(1,2,3,9,12)";//
                strSQL += "			 AND a8.co_mot NOT IN(8) AND a3.tx_codSri NOT IN('310')  ";//--para que no salga transporte
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')													";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp													";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo													";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac													";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet													";
                //--ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet													";
                strSQL += "			 UNION													";
                //--Para obtener el 1%(1)    1%(9)         FACCOM 													
                strSQL += "			 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG													";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT													";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF													";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE													";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet													";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN													";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)													";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)													";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 													";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)												";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc												";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   												";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg													";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4													";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc												";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5													";
                strSQL += "				 ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet												";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "													";
                strSQL += "			 " + strAux; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'  --'2012-10-17' AND '2012-10-17'
                strSQL += "			 AND a5.co_tipRet IN(1,9)";
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')													";
                strSQL += "			 AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)													";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp													";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo													";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac													";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet													";
                //--ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet													
                strSQL += "			ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                strSQL += "		) AS a1														";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA, a1.FECCAD														";
                strSQL += "		, a1.CODRETF, a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET														";
                strSQL += "		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc														";
                strSQL += "	) AS b8															";
                strSQL += "	ON b1.co_emp=b8.co_emp AND b1.co_loc=b8.co_loc AND b1.co_tipDoc=b8.co_tipDoc AND b1.co_doc=b8.co_doc";
                strSQL += "	LEFT OUTER JOIN(															";
                strSQL += "		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, SUM(a1.BASEIG) AS BASEIG, a1.CODIVA, SUM(a1.BASEIT) AS BASEIT, a1.FECCAD														";
                strSQL += "		, a1.CODRETF, SUM(a1.VALRETF) AS VALRETF, SUM(a1.BASERET) AS BASERET,a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET FROM(														";
                //                        --2.- Para obtener el 1%(1)  5%(2)  8%(3)  1%(9)  10%(12)  QUE NO ESTEN ASOCIADOS CON TRANSPORTE 													
                strSQL += "			SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG													";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT													";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF													";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE													";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet													";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN													";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)													";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)													";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 													";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)												";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc												";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   												";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg													";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4													";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc												";
                strSQL += "			 INNER JOIN tbm_retmovinv AS a7													";
                strSQL += "				 ON a4.co_emp=a7.co_emp AND a4.co_loc=a7.co_loc AND a4.co_tipDoc=a7.co_tipDoc AND a4.co_doc=a7.co_doc 												";
                strSQL += "			 INNER JOIN tbm_motDoc AS a8													";
                strSQL += "				 ON a7.co_emp=a8.co_emp AND a7.co_motDoc=a8.co_mot												";
                strSQL += "			 INNER JOIN tbm_polRet AS a6													";
                strSQL += "				 ON a8.co_emp=a6.co_emp AND a8.co_mot=a6.co_motTra												";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5													";
                strSQL += "				 ON a6.co_emp=a5.co_emp AND a6.co_tipRet=a5.co_tipRet AND a3.co_tipRet=a5.co_tipRet												";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "													";
                strSQL += "			 AND a1.co_tipDoc IN(33,160,158,230)													";
                strSQL += "			 " + strAux2; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'  --'2013-06-01' AND '2013-06-30'
                strSQL += "			 AND a5.co_tipRet IN(8,11)";//
                strSQL += "			 AND a8.co_mot NOT IN(8) AND a3.tx_codSri NOT IN('310')  ";//--para que no salga transporte
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')													";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp													";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo													";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac													";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet													";
                //--ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet													";
                strSQL += "			 UNION													";
                //--Para obtener el 1%(1)    1%(9)         FACCOM 													
                strSQL += "			 SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , CAST('01' AS CHARACTER VARYING) AS COD_CRE, CASE WHEN a4.nd_valIva=0 THEN 0 ELSE a3.nd_basImp END AS BASEIG													";
                strSQL += "			 , CAST('2' AS CHARACTER VARYING) AS CODIVA, CASE WHEN a4.nd_valIva=0 THEN a3.nd_basImp  ELSE 0 END AS BASEIT													";
                strSQL += "			 ,a3.tx_fecCad AS FECCAD, a3.tx_codsri AS CODRETF, a2.nd_abo*(-1) AS VALRETF													";
                strSQL += "			 ,a3.nd_basImp AS BASERET,a1.fe_doc AS FECRET, b3.tx_numserfac AS SERRTE													";
                strSQL += "			 , a1.ne_numDoc1 AS NUMRET, b3.tx_numautsri AS AUTRET,a3.nd_porRet													";
                strSQL += "			 FROM (((  (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS c1 ON a1.co_emp=c1.co_emp AND a1.co_loc=c1.co_loc AND a1.co_tipDoc=c1.co_tipDoc) INNER JOIN													";
                strSQL += "			 tbm_cli AS b1 ON a1.co_emp=b1.co_emp AND a1.co_cli=b1.co_cli)													";
                strSQL += "			 INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)													";
                strSQL += "			 LEFT OUTER JOIN tbm_datautsri AS b3 													";
                strSQL += "				 ON a1.co_emp=b3.co_emp AND a1.co_loc=b3.co_loc AND a1.co_tipDoc=b3.co_tipDoc AND a1.ne_numDoc1 BETWEEN b3.ne_numdocdes AND b3.ne_numdochas)												";
                strSQL += "			 INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc												";
                strSQL += "			 INNER JOIN tbm_pagMovInv AS a3 	   												";
                strSQL += "			 ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg													";
                strSQL += "			 INNER JOIN tbm_cabMovInv AS a4													";
                strSQL += "				 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc												";
                strSQL += "			 INNER JOIN tbm_cabTipRet AS a5													";
                strSQL += "				 ON a3.co_emp=a5.co_emp AND a3.co_tipRet=a5.co_tipRet												";
                strSQL += "			 WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "													";
                strSQL += "			 " + strAux2; //AND a1.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'  --'2012-10-17' AND '2012-10-17'
                strSQL += "			 AND a5.co_tipRet IN(8,11)";
                strSQL += "			 AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')													";
                strSQL += "			 AND a4.co_tipDoc IN(2) AND a1.co_tipDoc IN(33,160,158,230)													";
                strSQL += "			 GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg													";
                strSQL += "			 , a4.nd_valIva, a3.nd_basImp													";
                strSQL += "			 , a3.tx_fecCad, a3.tx_codsri, a2.nd_abo													";
                strSQL += "			 , a3.nd_basImp,a1.fe_doc, b3.tx_numserfac													";
                strSQL += "			 , a1.ne_numDoc1, b3.tx_numautsri,a3.nd_porRet													";
                //--ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a3.nd_porRet													
                strSQL += "			ORDER BY co_emp, co_loc, co_tipDoc, co_doc";
                strSQL += "		) AS a1														";
                strSQL += "		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.COD_CRE, a1.CODIVA, a1.FECCAD														";
                strSQL += "		, a1.CODRETF, a1.FECRET, a1.SERRTE, a1.NUMRET, a1.AUTRET														";
                strSQL += "		ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc														";
                strSQL += "	) AS b9															";
                strSQL += "	ON b1.co_emp=b9.co_emp AND b1.co_loc=b9.co_loc AND b1.co_tipDoc=b9.co_tipDoc AND b1.co_doc=b9.co_doc";
                strSQL += "	ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                System.out.println("cargarDetReg: " + strSQL);
                lblMsgSis.setText("Cargando datos...");
                rst = stm.executeQuery(strSQL);
                vecDat.clear();
                while (rst.next()) {
                    if (blnCon) {
                        if (rst.getString("NUMAUT")!=null && !rst.getString("NUMAUT").equals("")) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI, rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_NOM_DOC, rst.getString("NOM_DOC"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numDoc1"));
                        vecReg.add(INT_TBL_DAT_SUB, rst.getString("nd_sub"));
                        vecReg.add(INT_TBL_DAT_VAL_IVA, rst.getString("nd_valIva"));
                        vecReg.add(INT_TBL_DAT_TOT, rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_TIP_IDE, rst.getString("TIPOID"));
                        vecReg.add(INT_TBL_DAT_RUC, rst.getString("RUC"));
                        vecReg.add(INT_TBL_DAT_TIP_COM, rst.getString("TIPCOM"));
                        vecReg.add(INT_TBL_DAT_FEC_EMI, objUti.formatearFecha(rst.getDate("FEC_EMI"), "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_FEC_REG, objUti.formatearFecha(rst.getDate("FEC_REG"), "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_NUM_SER_ORD, rst.getObject("NUMSER") == null ? "" : rst.getString("NUMSER").replaceAll("-", ""));
                        vecReg.add(INT_TBL_DAT_NUM_SEC_ORD, rst.getString("NUMSEC"));
                        vecReg.add(INT_TBL_DAT_NUM_AUT_ORD, rst.getString("NUMAUT"));
                        vecReg.add(INT_TBL_DAT_COD_CRE, (rst.getObject("b2_COD_CRE") == null ? "02" : (rst.getString("b2_COD_CRE").equals("") ? "02" : rst.getString("b2_COD_CRE"))));
                        vecReg.add(INT_TBL_DAT_BAS_IMP_GRB, rst.getString("b2_BASEIG"));
                        vecReg.add(INT_TBL_DAT_COD_IVA, rst.getString("ne_codIva"));

                        BigDecimal bdeValSub = new BigDecimal("0");
                        BigDecimal bdeValBasCer = new BigDecimal("0");
                        BigDecimal bdeValBasImp = new BigDecimal("0");
                        BigDecimal bdeValDif = new BigDecimal("0");

                        bdeValSub = rst.getObject("nd_sub") == null ? new BigDecimal("0") : (rst.getBigDecimal("nd_sub").equals("") ? new BigDecimal("0") : rst.getBigDecimal("nd_sub"));
                        bdeValBasCer = (rst.getObject("b4_BASEIT") == null ? new BigDecimal("0") : (rst.getString("b4_BASEIT").equals("") ? new BigDecimal("0") : (rst.getObject("b4_BASEIT") == null ? new BigDecimal("0") : (rst.getString("b4_BASEIT").equals("") ? new BigDecimal("0") : rst.getBigDecimal("b4_BASEIT"))))).compareTo(new BigDecimal("0")) == 0 ? (rst.getObject("b2_BASEIT") == null ? new BigDecimal("0") : (rst.getString("b2_BASEIT").equals("") ? new BigDecimal("0") : rst.getBigDecimal("b2_BASEIT"))) : (rst.getObject("b4_BASEIT") == null ? new BigDecimal("0") : (rst.getString("b4_BASEIT").equals("") ? new BigDecimal("0") : rst.getBigDecimal("b4_BASEIT")));
                        bdeValBasImp = rst.getObject("b2_BASEIG") == null ? new BigDecimal("0") : (rst.getString("b2_BASEIG").equals("") ? new BigDecimal("0") : rst.getBigDecimal("b2_BASEIG"));

                        bdeValDif = bdeValSub.subtract(bdeValBasCer.add(bdeValBasImp));

                        //para cosenco en el mes de julio esta cargando valores en TarifaCero cuando no debe cargar nada
                        if ((rst.getObject("b8_CODRETF") == null ? "" : rst.getString("b8_CODRETF")).equals("312") || (rst.getObject("b8_CODRETF") == null ? "" : rst.getString("b8_CODRETF")).equals("307")) {
                            vecReg.add(INT_TBL_DAT_BAS_TAR_CER, bdeValBasCer);
                        } else {
                            vecReg.add(INT_TBL_DAT_BAS_TAR_CER, bdeValDif.compareTo(new BigDecimal("0")) > 0 ? bdeValDif : bdeValBasCer);
                        }

                        vecReg.add(INT_TBL_DAT_IVA, rst.getString("b2_VALIVA"));
                        vecReg.add(INT_TBL_DAT_BAS_IMP_ICE, "0");
                        vecReg.add(INT_TBL_DAT_COD_ICE, "");
                        vecReg.add(INT_TBL_DAT_ICE, "0");
                        vecReg.add(INT_TBL_DAT_IVA1, rst.getString("b6_BASEIG"));
                        vecReg.add(INT_TBL_DAT_COD_RET1, rst.getString("b6_COD_CRE"));
                        vecReg.add(INT_TBL_DAT_VAL_RET1, rst.getString("b6_VALRETF"));
                        vecReg.add(INT_TBL_DAT_IVA2, rst.getString("b7_BASEIG"));
                        vecReg.add(INT_TBL_DAT_COD_RET2, rst.getString("b7_COD_CRE"));
                        vecReg.add(INT_TBL_DAT_VAL_RET2, rst.getString("b7_VALRETF"));
                        vecReg.add(INT_TBL_DAT_DEV, "");
                        vecReg.add(INT_TBL_DAT_FEC_CAD, rst.getString("FECCAD"));
                            vecReg.add(INT_TBL_DAT_COD_RETF, rst.getString("b8_CODRETF"));
                            vecReg.add(INT_TBL_DAT_VAL_RETF, rst.getString("b8_VALRETF"));
                            vecReg.add(INT_TBL_DAT_BAS_RET, rst.getString("b8_BASEIG"));
                            if (rst.getString("b8_CODRETF")!=null && rst.getString("b8_CODRETF").equals("332")) {
                                vecReg.add(INT_TBL_DAT_FEC_RET, "");
                                vecReg.add(INT_TBL_DAT_SER_RET, "");
                                vecReg.add(INT_TBL_DAT_NUM_RET, "");
                                vecReg.add(INT_TBL_DAT_AUT_RET, "");
                            } else {
                                vecReg.add(INT_TBL_DAT_FEC_RET, objUti.formatearFecha(rst.getDate("fec_emi"), "dd/MM/yyyy"));
                                //vecReg.add(INT_TBL_DAT_SER_RET, rst.getString("SERRTE")==null?"":rst.getString("SERRTE").replaceAll("-", "")   );

                                //vecReg.add(INT_TBL_DAT_SER_RET, rst.getObject("SERRTE")==null? ( rst.getObject("b1_SERRTE")==null?"":rst.getString("b1_SERRTE").replaceAll("-", ""))       :(rst.getString("SERRTE").equals("")?( rst.getObject("b1_SERRTE")==null?"":(rst.getString("b1_SERRTE").replaceAll("-", ""))     )  : (rst.getString("SERRTE").replaceAll("-", ""))    )   );
                                vecReg.add(INT_TBL_DAT_SER_RET, rst.getObject("b1_SERRTE") == null ? (rst.getObject("SERRTE") == null ? "" : rst.getString("SERRTE").replaceAll("-", "")) : (rst.getString("b1_SERRTE").equals("") ? (rst.getObject("SERRTE") == null ? "" : (rst.getString("SERRTE").replaceAll("-", ""))) : (rst.getString("b1_SERRTE").replaceAll("-", ""))));

                                vecReg.add(INT_TBL_DAT_NUM_RET, rst.getString("ne_numDoc1"));
                                //vecReg.add(INT_TBL_DAT_AUT_RET, rst.getString("numaut"));

                                //vecReg.add(INT_TBL_DAT_AUT_RET, rst.getObject("numaut")==null? ( rst.getObject("b1_NUMAUTSRI")==null?"":rst.getString("b1_NUMAUTSRI").replaceAll("-", ""))       :(rst.getString("numaut").equals("")?( rst.getObject("b1_NUMAUTSRI")==null?"":(rst.getString("b1_NUMAUTSRI").replaceAll("-", ""))     )  : (rst.getString("numaut").replaceAll("-", ""))    )   );
                                vecReg.add(INT_TBL_DAT_AUT_RET, rst.getObject("b1_NUMAUTSRI") == null ? (rst.getObject("numaut") == null ? "" : rst.getString("numaut").replaceAll("-", "")) : (rst.getString("b1_NUMAUTSRI").equals("") ? (rst.getObject("numaut") == null ? "" : (rst.getString("numaut").replaceAll("-", ""))) : (rst.getString("b1_NUMAUTSRI").replaceAll("-", ""))));
                            }
                        

                        vecReg.add(INT_TBL_DAT_BAS_RET2, rst.getString("b9_BASEIG"));
                        vecReg.add(INT_TBL_DAT_COD_RETF2, rst.getString("b9_CODRETF"));
                        vecReg.add(INT_TBL_DAT_VAL_RETF2, rst.getString("b9_VALRETF"));
                        vecReg.add(INT_TBL_DAT_BAS_RET3, (rst.getBigDecimal("b4_BASEIT").compareTo(new BigDecimal("0")) == 0 ? rst.getString("b4_BASEIG") : rst.getString("b4_BASEIT")));
                        vecReg.add(INT_TBL_DAT_COD_RETF3, rst.getString("b4_CODRETF"));
                        vecReg.add(INT_TBL_DAT_VAL_RETF3, rst.getString("b4_VALRETF"));
                        vecReg.add(INT_TBL_DAT_TIP_COM2, "");
                        vecReg.add(INT_TBL_DAT_FEC_EMI2, "");
                        vecReg.add(INT_TBL_DAT_NUM_SER2, "");
                        vecReg.add(INT_TBL_DAT_NUM_SEC2, "");
                        vecReg.add(INT_TBL_DAT_NUM_AUT2, "");
                        vecReg.add(INT_TBL_DAT_CON_CEP, rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_REF, "");
                        //vecReg.add(INT_TBL_DAT_COD_PAG1, "2");
                        vecReg.add(INT_TBL_DAT_COD_PAG1, rst.getString("cod_pag1"));
                        vecReg.add(INT_TBL_DAT_COD_PAG2, "");
                        vecReg.add(INT_TBL_DAT_PAG_EXT, "");
                        vecReg.add(INT_TBL_DAT_COD_PAI, "");
                        vecReg.add(INT_TBL_DAT_CNV, "");
                        vecReg.add(INT_TBL_DAT_RET_IEN, "");
                        vecReg.add(INT_TBL_DAT_TIP_PRO, "");
                        vecReg.add(INT_TBL_DAT_ES_REL, rst.getString("st_empRel"));

                        bdeAux = rst.getBigDecimal("nd_valComSol");
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());

                        if (bdeAux.equals(new BigDecimal("0.00"))) {
                            strAuxLoc = "";
                        } else {
                            bdeAux = bdeAux.multiply(new BigDecimal("-1"));
                            strAuxLoc = bdeAux.toString();
                        }

                        vecReg.add(INT_TBL_DAT_VAL_COM_SOL, strAuxLoc);
                        vecDat.add(vecReg);    
                        }
                        
                    } else {
                        break;
                    }

                }

                //NO RETENCION, S/N IVA
                strSQL = "";
                strSQL += "(";
                strSQL += "SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli";
                strSQL += ", b1.fe_doc, b1.NOM_DOC, b1.ne_numDoc, b1.ne_tipforpag, b1.nd_sub, b1.nd_valIva, b1.nd_tot";
                strSQL += ", CASE WHEN b1.nd_valIva=0 THEN 0 ELSE b1.nd_basImp END AS BASEIG";
                strSQL += ", CASE WHEN b1.nd_valIva=0 THEN b1.nd_basImp  ELSE 0 END AS BASEIT";
                strSQL += " , CASE WHEN b1.nd_valIva<>0 THEN b1.nd_sub ELSE 0 END AS BASERET, b1.TIPOID, b1.RUC, b1.TIPCOM";
                strSQL += " , b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel";
                strSQL += " , CASE WHEN b1.nd_valIva=0  THEN CAST('02' AS CHARACTER VARYING) ELSE CAST('01' AS CHARACTER VARYING) END AS COD_CRE";
                strSQL += " , b1.CODRETF";
                strSQL += " , b1.tx_fecCad, b1.fe_venChq, b1.ne_codIva, b1.cod_pag1, b1.nd_valComSol";
                strSQL += " FROM(";
                strSQL += "		 SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a2.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, a3.nd_basImp";
                strSQL += " 		 , a9.tx_tipide AS TIPOID, a4.tx_ruc AS RUC, CASE WHEN b2.co_tipDoc=57 THEN '03' ELSE '01' END AS TIPCOM";
                strSQL += " 		 , a4.fe_doc AS FEC_EMI, a4.fe_doc AS FEC_REG, a3.tx_numser AS NUMSER, a3.tx_numChq AS NUMSEC, a3.tx_numAutSRI AS NUMAUT";
                strSQL += " 		 , CASE WHEN a9.co_empgrp IS NOT NULL THEN 'S' ELSE '' END AS st_empRel, a3.tx_codsri AS CODRETF";
                strSQL += " 		 , a3.tx_fecCad ";
                //strSQL+="                , a3.fe_venChq, ";
                strSQL += "                , (select max(fe_venChq) from tbm_pagMovInv as a where a.co_emp=a4.co_emp AND a.co_loc=a4.co_loc AND a.co_tipDoc=a4.co_tipDoc AND a.co_doc=a4.co_doc) as fe_venChq, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then 2 "; //2 = Cod. Iva 12%; Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01' then 2 "; //2 = Cod. Iva 12%
                strSQL += "                       else 3 "; //3 = Cod. Iva 14%
                strSQL += "                end ) as ne_codIva, ";

//                strSQL += "                ( case when a2.ne_tipforpag in (1,19) and a4.nd_tot >= 1000 then '19' "; //1 = Contado; 19 = Tarjeta de credito
//                strSQL += "                       when a2.ne_tipforpag not in (1,19) and a4.nd_tot >= 1000 then '20' ";
//                strSQL += "                       else '' ";
//                strSQL += "                end ) as cod_pag1, ";
//                
                strSQL += "       ( case when a2.ne_tipforpag = 1 and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a2.ne_tipforpag = 4 and a4.nd_tot >= 1000 then 19 "; //19 = Tarjeta de credito
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a2.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 20 "; //20 = Otros con utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a2.ne_tipforpag in (4) and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a2.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 2 "; //2 = Cheque propio (credito)
                strSQL += "              else null ";
                strSQL += "       end ) as cod_pag1, ";

                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then a4.nd_sub * 0.02 "; //Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       else 0 ";
                strSQL += "                end ) as nd_valComSol ";
                strSQL += " 		 FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)";
                strSQL += "		 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += " 		 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc";
                strSQL += " 		 INNER JOIN tbm_cabTipDoc AS b2 ON a4.co_emp=b2.co_emp AND a4.co_loc=b2.co_loc AND a4.co_tipDoc=b2.co_tipDoc";
                strSQL += "                LEFT OUTER JOIN tbm_cabforpag as a2 on a4.co_emp = a2.co_emp and a4.co_forpag = a2.co_forpag ";
                strSQL += "		 WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " 		 AND a4.co_tipDoc IN(2,	38,57,120,185,121,122,106,104,105,118,179,186,119)";
                strSQL += "";//AND a4.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "" + strAux2;
                strSQL += "		 AND a4.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') " + strAuxAux; //tony
                strSQL += " 		 GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor, a4.ne_numDoc, a2.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, a3.nd_basImp";
                strSQL += " 		 , a9.tx_tipide, a4.tx_ruc, b2.co_tipDoc";
                strSQL += " 		 , a3.tx_numser, a3.tx_numChq, a3.tx_numAutSRI, a9.co_empgrp, a3.tx_codsri";
                strSQL += " 		 , a3.tx_fecCad, a3.fe_venChq";
                strSQL += " 		 ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL += " ) AS b1";
                strSQL += " LEFT OUTER JOIN(";
                strSQL += "		 SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a2.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then 2 "; //2 = Cod. Iva 12%; Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01'  then 2 "; //2 = Cod. Iva 12%
                strSQL += "                       else 3 "; //3 = Cod. Iva 14%
                strSQL += "                end ) as ne_codIva, ";
                strSQL += "                ( case when a2.ne_tipforpag in (1,19) and a4.nd_tot >= 1000 then '19' "; //1 = Contado; 19 = Tarjeta de credito
                strSQL += "                       when a2.ne_tipforpag not in (1,19) and a4.nd_tot >= 1000 then '20' ";
                strSQL += "                       else '' ";
                strSQL += "                end ) as cod_pag1, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then a4.nd_sub * 0.02 "; //Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       else 0 ";
                strSQL += "                end ) as nd_valComSol ";
                strSQL += "		 FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)";
                strSQL += "		 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += " 		 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc";
                strSQL += " 		 INNER JOIN tbm_cabTipDoc AS b2 ON a4.co_emp=b2.co_emp AND a4.co_loc=b2.co_loc AND a4.co_tipDoc=b2.co_tipDoc";
                strSQL += "                LEFT OUTER JOIN tbm_cabforpag as a2 on a4.co_emp = a2.co_emp and a4.co_forpag = a2.co_forpag ";
                strSQL += "		 WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " 		 AND a4.co_tipDoc IN(2,	38,57,120,185,121,122,106,104,105,118,179,186,119)";
                strSQL += "" + strAux2;//AND a4.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "		 AND a4.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C')";
                strSQL += "		 AND (a3.co_tipret<>0 AND a3.co_tipret IS NOT NULL ) " + strAuxAux;//AND a3.co_tipret<>13 //tony
                strSQL += "		 GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += "		 , a4.fe_doc, b2.tx_desCor, a4.ne_numDoc, a2.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot";
                strSQL += "		 ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL += " ) AS b2";
                strSQL += " ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL += " WHERE b1.NUMSEC IS NOT NULL AND b2.co_emp IS NULL";
                strSQL += " ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL += " )";
                strSQL += " UNION";
                
                strSQL += " (";
                strSQL += "SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli";
                strSQL += ", b1.fe_doc, b1.NOM_DOC, b1.ne_numDoc, b1.ne_tipforpag, b1.nd_sub, b1.nd_valIva, b1.nd_tot";
                strSQL += ", CASE WHEN b1.nd_valIva=0 THEN 0 ELSE b1.nd_basImp END AS BASEIG";
                strSQL += ", CASE WHEN b1.nd_valIva=0 THEN b1.nd_basImp  ELSE 0 END AS BASEIT";
                strSQL += " , CASE WHEN b1.nd_valIva<>0 THEN b1.nd_sub ELSE 0 END AS BASERET, b1.TIPOID, b1.RUC, b1.TIPCOM";
                strSQL += " , b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel";
                strSQL += " , CASE WHEN b1.nd_valIva=0  THEN CAST('02' AS CHARACTER VARYING) ELSE CAST('01' AS CHARACTER VARYING) END AS COD_CRE";
                strSQL += " , b1.CODRETF";
                strSQL += " , b1.tx_fecCad, b1.fe_venChq, b1.ne_codIva, b1.cod_pag1, b1.nd_valComSol";
                strSQL += " FROM(";
                strSQL += "		 SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, a3.nd_basImp";
                strSQL += " 		 , a9.tx_tipide AS TIPOID, a4.tx_ruc AS RUC, CASE WHEN b2.co_tipDoc=57 THEN '03' ELSE '01' END AS TIPCOM";
                strSQL += " 		 , a4.fe_doc AS FEC_EMI, a4.fe_doc AS FEC_REG, a3.tx_numser AS NUMSER, a3.tx_numChq AS NUMSEC, a3.tx_numAutSRI AS NUMAUT";
                strSQL += " 		 , CASE WHEN a9.co_empgrp IS NOT NULL THEN 'S' ELSE '' END AS st_empRel, a3.tx_codsri AS CODRETF";
                strSQL += " 		 , a3.tx_fecCad, a3.fe_venChq, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then 2 "; //2 = Cod. Iva 12%; Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01' then 2 "; //2 = Cod. Iva 12%
                strSQL += "                       else 3 "; //3 = Cod. Iva 14%
                strSQL += "                end ) as ne_codIva, ";

//                strSQL += "                ( case when a1.ne_tipforpag in (1,19) and a4.nd_tot >= 1000 then '19' "; //1 = Contado; 19 = Tarjeta de credito
//                strSQL += "                       when a1.ne_tipforpag not in (1,19) and a4.nd_tot >= 1000 then '20' ";
//                strSQL += "                       else '' ";
//                strSQL += "                end ) as cod_pag1, ";
                strSQL += "       ( case when a1.ne_tipforpag = 1 and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a1.ne_tipforpag = 4 and a4.nd_tot >= 1000 then 19 "; //19 = Tarjeta de credito
                strSQL += "              when a4.fe_doc >= '2016-09-01'  and a1.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 20 "; //20 = Otros con utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a1.ne_tipforpag in (4) and a4.nd_tot >= 1000 then 1 "; //1 = Sin utilizacion del sistema financiero
                strSQL += "              when a4.fe_doc < '2016-09-01'  and a1.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 2 "; //2 = Cheque propio (credito)
                strSQL += "              else null ";
                strSQL += "       end ) as cod_pag1, ";

                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then a4.nd_sub * 0.02 "; //Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       else 0 ";
                strSQL += "                end ) as nd_valComSol ";
                strSQL += " 		 FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)";
                strSQL += "		 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += " 		 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc";
                strSQL += " 		 INNER JOIN tbm_cabTipDoc AS b2 ON a4.co_emp=b2.co_emp AND a4.co_loc=b2.co_loc AND a4.co_tipDoc=b2.co_tipDoc";
                strSQL += "                LEFT OUTER JOIN tbm_cabforpag as a1 on a4.co_emp = a1.co_emp and a4.co_forpag = a1.co_forpag ";
                strSQL += "		 WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " 		 AND a4.co_tipDoc IN(2,	38,57,120,185,121,122,106,104,105,118,179,186,119)";
                strSQL += "";//AND a4.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'
                strSQL += "" + strAux2;
                strSQL += "                AND a3.co_tipret=13";
                strSQL += "		 AND a4.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') " + strAuxAux; //tony
                strSQL += " 		 GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, a3.nd_basImp";
                strSQL += " 		 , a9.tx_tipide, a4.tx_ruc, b2.co_tipDoc";
                strSQL += " 		 , a3.tx_numser, a3.tx_numChq, a3.tx_numAutSRI, a9.co_empgrp, a3.tx_codsri";
                strSQL += " 		 , a3.tx_fecCad, a3.fe_venChq";
                strSQL += " 		 ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL += " ) AS b1";
                strSQL += " LEFT OUTER JOIN(";
                strSQL += "		 SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += " 		 , a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then 2 "; //2 = Cod. Iva 12%; Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01' then 2 "; //2 = Cod. Iva 12%
                strSQL += "                       else 3 "; //3 = Cod. Iva 14%
                strSQL += "                end ) as ne_codIva, ";
                strSQL += "                ( case when a1.ne_tipforpag in (1,19) and a4.nd_tot >= 1000 then '19' "; //1 = Contado; 19 = Tarjeta de credito
                strSQL += "                       when a1.ne_tipforpag not in (1,19) and a4.nd_tot >= 1000 then '20' ";
                strSQL += "                       else '' ";
                strSQL += "                end ) as cod_pag1, ";
                strSQL += "                ( case when a4.co_emp = 2 and a4.co_loc = 4 and a4.fe_doc >= '2016-06-01' and a4.fe_doc<'2017-06-01' then a4.nd_sub * 0.02 "; //Cod_emp 2 Cod_loc 4 = Castek Manta
                strSQL += "                       else 0 ";
                strSQL += "                end ) as nd_valComSol ";
                strSQL += "		 FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)";
                strSQL += "		 INNER JOIN tbm_pagMovInv AS a3";
                strSQL += " 		 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc";
                strSQL += " 		 INNER JOIN tbm_cabTipDoc AS b2 ON a4.co_emp=b2.co_emp AND a4.co_loc=b2.co_loc AND a4.co_tipDoc=b2.co_tipDoc";
                strSQL += "                LEFT OUTER JOIN tbm_cabforpag as a1 on a4.co_emp = a1.co_emp and a4.co_forpag = a1.co_forpag ";
                strSQL += "		 WHERE a4.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " 		 AND a4.co_tipDoc IN(2,	38,57,120,185,121,122,106,104,105,118,179,186,119)";
                strSQL += "" + strAux2;//AND a4.fe_doc BETWEEN '2013-06-01' AND '2013-06-30'

                strSQL += "		 AND a4.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C')";
                strSQL += "		 AND (a3.co_tipret<>0 AND a3.co_tipret IS NOT NULL AND a3.co_tipret<>13)";//AND a3.co_tipret<>13
                strSQL += "		 GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli";
                strSQL += "		 , a4.fe_doc, b2.tx_desCor, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub, a4.nd_valIva, a4.nd_tot";
                strSQL += "		 ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL += " ) AS b2";
                strSQL += " ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQL += " WHERE b1.NUMSEC IS NOT NULL AND b2.co_emp IS NULL";
                strSQL += " ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc";
                strSQL += " )";
                strSQL += " UNION";
                
                strSQL+= "(SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_cli, b1.tx_nomCli, b1.fe_doc, b1.NOM_DOC, \n" +
                            "        b1.ne_numDoc, b1.ne_tipforpag, b1.nd_sub, b1.nd_valIva, b1.nd_tot, \n" +
                            "        CASE WHEN b1.nd_valIva=0 THEN 0 ELSE b1.nd_basImp END AS BASEIG, \n" +
                            "        CASE WHEN b1.nd_valIva=0 THEN b1.nd_basImp  ELSE 0 END AS BASEIT , \n" +
                            "        CASE WHEN b1.nd_valIva<>0 THEN b1.nd_sub ELSE 0 END AS BASERET, b1.TIPOID, b1.RUC, \n" +
                            "        b1.TIPCOM , b1.FEC_EMI, b1.FEC_REG, b1.NUMSER, b1.NUMSEC, b1.NUMAUT, b1.st_empRel , \n" +
                            "        CASE WHEN b1.nd_valIva=0  THEN CAST('02' AS CHARACTER VARYING) ELSE CAST('01' AS CHARACTER VARYING) END AS COD_CRE , \n" +
                            "        b1.CODRETF , b1.tx_fecCad, b1.fe_venChq, b1.ne_codIva, b1.cod_pag1, b1.nd_valComSol \n" +
                            "  FROM(		 \n" +
                            "       SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli, \n" +
                            //"       a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a1.ne_tipforpag, abs(a4.nd_sub) as nd_sub, abs(a4.nd_valIva) as nd_valIva, abs(a4.nd_tot) as nd_tot, abs(a4.nd_sub)  as nd_basImp, \n" +
                        "       a4.fe_doc, b2.tx_desCor AS NOM_DOC, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub as nd_sub, a4.nd_valIva as nd_valIva, a4.nd_tot as nd_tot, a4.nd_sub  as nd_basImp, \n" +
                            "       a9.tx_tipide AS TIPOID, a9.tx_ide AS RUC, CASE WHEN b2.co_tipDoc=74 THEN '04' ELSE '01' END AS TIPCOM 		 , \n" +
                            "       a4.fe_doc AS FEC_EMI, a4.fe_doc AS FEC_REG, b11.tx_numser AS NUMSER, b11.tx_numChq AS NUMSEC, \n" +
                            "       b11.tx_numAutSRI AS NUMAUT 		 , \n" +
                            "       CASE WHEN a9.co_empgrp IS NOT NULL THEN 'S' ELSE '' END AS st_empRel, a3.tx_codsri AS CODRETF 		 , \n" +
                            "       a3.tx_fecCad, b11.fe_venChq,                 \n" +
                            "       ( case when a4.co_emp = 2 and a4.co_loc = 4 and (a4.fe_doc >= '2016-06-01' or a4.fe_doc<'2017-06-01') then 2 \n" +
                            "              when a4.fe_doc < '2016-06-01' or a4.fe_doc>='2017-06-01' then 2  else 3                 end ) as ne_codIva,        \n" +
                            "       ( case when a1.ne_tipforpag = 1 and a4.nd_tot >= 1000 then 1               \n" +
                            "              when a4.fe_doc >= '2016-09-01'  and a1.ne_tipforpag = 4 and a4.nd_tot >= 1000 then 19               \n" +
                            "              when a4.fe_doc >= '2016-09-01'  and a1.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 20               \n" +
                            "              when a4.fe_doc < '2016-09-01'  and a1.ne_tipforpag in (4) and a4.nd_tot >= 1000 then 1               \n" +
                            "              when a4.fe_doc < '2016-09-01'  and a1.ne_tipforpag not in (1,4) and a4.nd_tot >= 1000 then 2   else null    end ) as cod_pag1,               \n" +
                            "       ( case when a4.co_emp = 2 and a4.co_loc = 4 and a4.fe_doc >= '2016-06-01' and a4.fe_doc<'2017-06-01' then a4.nd_sub * 0.02    else 0  end ) as nd_valComSol  		    FROM (tbm_cabMovInv AS a4 INNER JOIN tbm_cli AS a9 ON a4.co_emp=a9.co_emp AND a4.co_cli=a9.co_cli)		 \n" +
                            "       INNER JOIN tbm_pagMovInv AS a3 ON a4.co_emp=a3.co_emp AND a4.co_loc=a3.co_loc AND a4.co_tipDoc=a3.co_tipDoc AND a4.co_doc=a3.co_doc\n" +
                            "       INNER JOIN tbm_cabTipDoc AS b2 ON a4.co_emp=b2.co_emp AND a4.co_loc=b2.co_loc AND a4.co_tipDoc=b2.co_tipDoc                \n" +
                            "       LEFT OUTER JOIN tbm_cabforpag as a1 on a4.co_emp = a1.co_emp and a4.co_forpag = a1.co_forpag \n" +
                            "       INNER JOIN tbr_detRecDocCabMovInv as b10 ON a4.co_emp=b10.co_emprel and a4.co_loc=b10.co_locrel and a4.co_tipdoc=b10.co_tipdocrel and a4.co_doc=b10.co_docrel\n" +
                            "       INNER JOIN tbm_detRecDoc as b11 ON b10.co_emp=b11.co_emp and b10.co_loc=b11.co_loc and b10.co_tipdoc=b11.co_tipdoc and b10.co_doc=b11.co_doc\n" +
                            " WHERE a4.co_emp= " + objParSis.getCodigoEmpresa()
                            + "AND a4.co_tipDoc IN(74) \n" +
                            //"  AND a4.fe_doc BETWEEN '2016-11-01' AND '2016-11-30' \n" + 
                             strAux2+
                            "  AND a3.co_tipret=0 AND a4.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') and b10.st_reg = 'A' \n" +
                            "  GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc, a4.co_cli, a4.tx_nomCli, \n" +
                            "           a4.fe_doc, b2.tx_desCor, a4.ne_numDoc, a1.ne_tipforpag, a4.nd_sub, \n" +
                            "           a4.nd_valIva, a4.nd_tot, a3.nd_basImp , a9.tx_tipide, a9.tx_ide, \n" +
                            "           b2.co_tipDoc, b11.tx_numser, b11.tx_numChq, b11.tx_numAutSRI, \n" +
                            "           a9.co_empgrp, a3.tx_codsri , a3.tx_fecCad, b11.fe_venChq 		 \n" +
                            "  ORDER BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc ) AS b1 \n" +
                            " WHERE b1.NUMSEC IS not NULL ORDER BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc )";
                
                System.out.println("cargarDetReg 2: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //vecDat.clear();//no se blanquea porque esta con datos dell query anterior listo para ser cargado en el modelo
                while (rst.next()) {
                    if (blnCon) {
                         if (rst.getString("NUMAUT")!=null && !rst.getString("NUMAUT").equals("")) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP, rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC, rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC, rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI, rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI, rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC, rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_NOM_DOC, rst.getString("NOM_DOC"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC, rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_SUB, rst.getString("nd_sub"));
                        vecReg.add(INT_TBL_DAT_VAL_IVA, rst.getString("nd_valIva"));
                        vecReg.add(INT_TBL_DAT_TOT, rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_TIP_IDE, rst.getString("TIPOID"));
                        vecReg.add(INT_TBL_DAT_RUC, rst.getString("RUC"));
                        vecReg.add(INT_TBL_DAT_TIP_COM, rst.getString("TIPCOM"));
                        vecReg.add(INT_TBL_DAT_FEC_EMI, objUti.formatearFecha(rst.getDate("FEC_EMI"), "dd/MM/yyyy"));//formatearFecha(rst.getDate("FEC_REG"), "dd/MM/yyyy")
                        vecReg.add(INT_TBL_DAT_FEC_REG, objUti.formatearFecha(rst.getDate("FEC_REG"), "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_NUM_SER_ORD, rst.getObject("NUMSER") == null ? "" : rst.getString("NUMSER").replaceAll("-", ""));//
                        vecReg.add(INT_TBL_DAT_NUM_SEC_ORD, rst.getString("NUMSEC"));
                        vecReg.add(INT_TBL_DAT_NUM_AUT_ORD, rst.getString("NUMAUT"));
                        vecReg.add(INT_TBL_DAT_COD_CRE, rst.getString("COD_CRE"));
                        vecReg.add(INT_TBL_DAT_BAS_IMP_GRB, rst.getString("BASEIG"));
                        vecReg.add(INT_TBL_DAT_COD_IVA, rst.getString("ne_codIva"));
                        vecReg.add(INT_TBL_DAT_BAS_TAR_CER, rst.getBigDecimal("nd_sub").subtract(rst.getBigDecimal("BASEIG")));
                        vecReg.add(INT_TBL_DAT_IVA, rst.getString("nd_valIva"));
                        vecReg.add(INT_TBL_DAT_BAS_IMP_ICE, "0");
                        vecReg.add(INT_TBL_DAT_COD_ICE, "");
                        vecReg.add(INT_TBL_DAT_ICE, "0");
                        vecReg.add(INT_TBL_DAT_IVA1, "");
                        vecReg.add(INT_TBL_DAT_COD_RET1, "");
                        vecReg.add(INT_TBL_DAT_VAL_RET1, "");
                        vecReg.add(INT_TBL_DAT_IVA2, "");
                        vecReg.add(INT_TBL_DAT_COD_RET2, "");
                        vecReg.add(INT_TBL_DAT_VAL_RET2, "");
                        vecReg.add(INT_TBL_DAT_DEV, "");

                        vecReg.add(INT_TBL_DAT_FEC_CAD, rst.getString("tx_fecCad"));
                        vecReg.add(INT_TBL_DAT_COD_RETF, rst.getString("CODRETF"));
                        vecReg.add(INT_TBL_DAT_VAL_RETF, "");
                        vecReg.add(INT_TBL_DAT_BAS_RET, rst.getString("BASERET"));
                        
                             if (rst.getString("CODRETF")!=null && rst.getString("CODRETF").equals("332")) {
                                  vecReg.add(INT_TBL_DAT_FEC_RET, "");
                                    vecReg.add(INT_TBL_DAT_SER_RET, "");
                                    vecReg.add(INT_TBL_DAT_NUM_RET, "");
                                    vecReg.add(INT_TBL_DAT_AUT_RET, "");

                             }else{
                             vecReg.add(INT_TBL_DAT_FEC_RET, rst.getString("fe_venChq"));
                             vecReg.add(INT_TBL_DAT_SER_RET, rst.getString("NUMSER"));
                             vecReg.add(INT_TBL_DAT_NUM_RET, rst.getString("NUMSEC"));
                             vecReg.add(INT_TBL_DAT_AUT_RET, rst.getString("NUMAUT"));
                             }
                        

                        vecReg.add(INT_TBL_DAT_BAS_RET2, "");
                        vecReg.add(INT_TBL_DAT_COD_RETF2, "");
                        vecReg.add(INT_TBL_DAT_VAL_RETF2, "");
                        vecReg.add(INT_TBL_DAT_BAS_RET3, "");
                        vecReg.add(INT_TBL_DAT_COD_RETF3, "");
                        vecReg.add(INT_TBL_DAT_VAL_RETF3, "");
                        if (rst.getString("co_tipDoc").equals("74")) {
                            vecReg.add(INT_TBL_DAT_TIP_COM2, "01");
                            vecReg.add(INT_TBL_DAT_FEC_EMI2, rst.getString("fe_venChq"));
                            vecReg.add(INT_TBL_DAT_NUM_SER2, rst.getString("NUMSER"));
                            vecReg.add(INT_TBL_DAT_NUM_SEC2, rst.getString("NUMSEC"));
                            vecReg.add(INT_TBL_DAT_NUM_AUT2, rst.getString("NUMAUT"));     
                        }else{
                        vecReg.add(INT_TBL_DAT_TIP_COM2, "");
                        vecReg.add(INT_TBL_DAT_FEC_EMI2, "");
                        vecReg.add(INT_TBL_DAT_NUM_SER2, "");
                        vecReg.add(INT_TBL_DAT_NUM_SEC2, "");
                        vecReg.add(INT_TBL_DAT_NUM_AUT2, "");
                        }
                        vecReg.add(INT_TBL_DAT_CON_CEP, rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_REF, "");
                        //vecReg.add(INT_TBL_DAT_COD_PAG1, "2");
                        vecReg.add(INT_TBL_DAT_COD_PAG1, rst.getString("cod_pag1"));
                        vecReg.add(INT_TBL_DAT_COD_PAG2, "");
                        vecReg.add(INT_TBL_DAT_PAG_EXT, "");
                        vecReg.add(INT_TBL_DAT_COD_PAI, "");
                        vecReg.add(INT_TBL_DAT_CNV, "");
                        vecReg.add(INT_TBL_DAT_RET_IEN, "");
                        vecReg.add(INT_TBL_DAT_TIP_PRO, "");
                        vecReg.add(INT_TBL_DAT_ES_REL, rst.getString("st_empRel"));
                        vecReg.add(INT_TBL_DAT_VAL_COM_SOL, "");
                        vecDat.add(vecReg);
                         }
                    } else {
                        break;
                    }
                }

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                pgrSis.setMaximum(objTblMod.getRowCountTrue());
                datFecIniFacIva14 = objUti.parseDate("2016/06/01", "yyyy/MM/dd"); //Fecha de inicio de Facturacion con IVA 14%
                datFecFinFacIva14 = objUti.parseDate("2017/06/01", "yyyy/MM/dd"); //Fecha de inicio de Facturacion con IVA 14%
                for (i = 0; i < objTblMod.getRowCountTrue(); i++) {
                    pgrSis.setValue(i);
                    intCodEmp = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                    intCodLoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                    intCodTipDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                    intCodDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                    
                    //En la col. INT_TBL_DAT_COD_RETF se va a guardar el Cod. SRI de Ret.Fte. 1% o el de Ret.Fte. 0%
                    strCodRetFte = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF).toString().trim();
                    strBasRetFte = objTblMod.getValueAt(i, INT_TBL_DAT_BAS_RET) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_BAS_RET).toString().trim();

                    //En la col. INT_TBL_DAT_COD_RETF3 tambien se puede guardar el Cod. SRI de Ret.Fte. 1%
                    strCodRetFte3 = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF3) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF3).toString().trim();

                    if ((strCodRetFte.equals("") || strBasRetFte.equals("") || strBasRetFte.equals("0")) && (strCodRetFte3.equals(""))) {  //Si no hay dato en la col. "Cod.Ret.Fte.3" entonces alli si se pone dato en la col. "Cod.Ret.Fte."
                        //Si ya hay dato en la col. "Cod.Ret.Fte.3" no se pone nada en la col. "Cod.Ret.Fte." para no duplicar informacion.
                        blnExiReg = false;
                        //Se va a buscar algunos datos si tbm_pagmovinv.nd_porret = 1 (Ret.Fte. 1%)
                        strSQL = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.nd_porret, a.nd_basImp, a.tx_codsri, sum(a.nd_ValRetF) as nd_ValRetF ";
                        strSQL += "from ( select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a3.nd_porret, a3.nd_basImp, a3.tx_codsri, (a2.nd_abo * (-1)) AS nd_ValRetF ";
                        strSQL += "       from tbm_cabpag as a1 ";
                        strSQL += "       inner join tbm_detpag as a2 on a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipDoc AND a1.co_doc = a2.co_doc ";
                        strSQL += "       inner join tbm_pagmovinv as a3 on a2.co_emp = a3.co_emp AND a2.co_locPag = a3.co_loc AND a2.co_tipDocPag = a3.co_tipDoc AND a2.co_docPag = a3.co_doc AND a2.co_regPag = a3.co_reg ";
                        strSQL += "       where a3.nd_porret = 1 and a1.st_reg not in ('E','I') and a3.st_reg in ('A','C') ";
                        strSQL += "             and a1.co_emp = " + intCodEmp;
                        strSQL += "             and a1.co_loc = " + intCodLoc;
                        strSQL += "             and a1.co_tipdoc = " + intCodTipDoc;
                        strSQL += "             and a1.co_doc = " + intCodDoc;
                        strSQL += "     ) as a ";
                        strSQL += "group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.nd_porret, a.nd_basImp, a.tx_codsri ";
                        rst = stm.executeQuery(strSQL);

                        if (rst.next()) {
                            blnExiReg = true;
                            objTblMod.setValueAt(rst.getString("tx_codsri"), i, INT_TBL_DAT_COD_RETF);
                            objTblMod.setValueAt(rst.getString("nd_ValRetF"), i, INT_TBL_DAT_VAL_RETF);
                            objTblMod.setValueAt(rst.getString("nd_basImp"), i, INT_TBL_DAT_BAS_RET);
                        }

                        if (blnExiReg == false) {  //Se va a buscar algunos datos si tbm_pagmovinv.co_tipret = 13 (Retencion en la fuente varios 0%)
                            strSQL = "select     a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.co_tipret, a2.nd_porret, a1.nd_sub, a2.tx_codsri ";
                            strSQL += "from       tbm_cabmovinv as a1 ";
                            strSQL += "inner join tbm_pagmovinv as a2 on a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipDoc AND a1.co_doc = a2.co_doc ";
                            strSQL += "where      a2.co_tipret = 13 and a1.st_reg = 'A' and a2.st_reg in ('A','C') ";
                            strSQL += "           and a1.co_emp = " + intCodEmp;
                            strSQL += "           and a1.co_loc = " + intCodLoc;
                            strSQL += "           and a1.co_tipdoc = " + intCodTipDoc;
                            strSQL += "           and a1.co_doc = " + intCodDoc;
                            rst = stm.executeQuery(strSQL);

                            if (rst.next()) {
                                strAux = rst.getString("tx_codsri") == null ? "" : rst.getString("tx_codsri");
                                objTblMod.setValueAt(strAux, i, INT_TBL_DAT_COD_RETF);
                                objTblMod.setValueAt(rst.getString("nd_sub"), i, INT_TBL_DAT_BAS_RET);
                            }
                        }
                    } //if ( (strCodRetFte.equals("") || strBasRetFte.equals("") || strBasRetFte.equals("0")) && (strCodRetFte3.equals("")) )

                    //En la col. INT_TBL_DAT_BAS_RET2 se va a guardar la base de la Ret.Fte. 2%
                    strBasRetFte2 = objTblMod.getValueAt(i, INT_TBL_DAT_BAS_RET2) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_BAS_RET2).toString().trim();

                    if (strBasRetFte2.equals("") || strBasRetFte2.equals("0")) {  //Se va a buscar algunos datos si tbm_pagmovinv.nd_porret = 2 (Ret.Fte. 2%)
                        strSQL = "select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.nd_porret, a.nd_basImp, a.tx_codsri, sum(a.nd_ValRetF) as nd_ValRetF ";
                        strSQL += "from ( select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a3.nd_porret, a3.nd_basImp, a3.tx_codsri, (a2.nd_abo * (-1)) AS nd_ValRetF ";
                        strSQL += "       from tbm_cabpag as a1 ";
                        strSQL += "       inner join tbm_detpag as a2 on a1.co_emp = a2.co_emp AND a1.co_loc = a2.co_loc AND a1.co_tipDoc = a2.co_tipDoc AND a1.co_doc = a2.co_doc ";
                        strSQL += "       inner join tbm_pagmovinv as a3 on a2.co_emp = a3.co_emp AND a2.co_locPag = a3.co_loc AND a2.co_tipDocPag = a3.co_tipDoc AND a2.co_docPag = a3.co_doc AND a2.co_regPag = a3.co_reg ";
                        strSQL += "       where a3.nd_porret = 2 and a1.st_reg not in ('E','I') and a3.st_reg in ('A','C') ";
                        strSQL += "             and a1.co_emp = " + intCodEmp;
                        strSQL += "             and a1.co_loc = " + intCodLoc;
                        strSQL += "             and a1.co_tipdoc = " + intCodTipDoc;
                        strSQL += "             and a1.co_doc = " + intCodDoc;
                        strSQL += "     ) as a ";
                        strSQL += "group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.nd_porret, a.nd_basImp, a.tx_codsri ";
                        rst = stm.executeQuery(strSQL);

                        if (rst.next()) {
                            objTblMod.setValueAt(rst.getString("tx_codsri"), i, INT_TBL_DAT_COD_RETF2);
                            objTblMod.setValueAt(rst.getString("nd_ValRetF"), i, INT_TBL_DAT_VAL_RETF2);
                            objTblMod.setValueAt(rst.getString("nd_basImp"), i, INT_TBL_DAT_BAS_RET2);
                        }
                    }

                    strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RETF2) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RETF2).toString().trim();

                    if (!strAuxLoc.equals("")) {
                        bdeAux = new BigDecimal(strAuxLoc);
                        bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                        objTblMod.setValueAt(bdeAux, i, INT_TBL_DAT_VAL_RETF2);
                    }

                    strCodRetFte = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF).toString().trim();
                    strCodRetFte2 = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF2) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF2).toString().trim();
                    strValRet1 = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RET1) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RET1).toString().trim();

                    if (strValRet1.equals("") && strCodRetFte.equals("") && strCodRetFte2.equals("") && strCodRetFte3.equals("")) {
                        objTblMod.setValueAt("", i, INT_TBL_DAT_FEC_RET);
                        objTblMod.setValueAt("", i, INT_TBL_DAT_SER_RET);
                        objTblMod.setValueAt("", i, INT_TBL_DAT_NUM_RET);
                        objTblMod.setValueAt("", i, INT_TBL_DAT_AUT_RET);
                        objTblMod.setValueAt("332", i, INT_TBL_DAT_COD_RETF);
                    }

                     /*
                     strValIva = objTblMod.getValueAt(i, INT_TBL_DAT_IVA) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_IVA).toString().trim();
                     strBasTarCer = objTblMod.getValueAt(i, INT_TBL_DAT_BAS_TAR_CER) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_BAS_TAR_CER).toString().trim();
                     strCodRetFte = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF) == null? "" :objTblMod.getValueAt(i, INT_TBL_DAT_COD_RETF).toString().trim();
                   
                     if (strValIva.equals(""))
                     bdeValIva_BasImpGra = new BigDecimal("0.00");
                     else
                     {  bdeValIva_BasImpGra = new BigDecimal(strValIva);
                     bdeValIva_BasImpGra = objUti.redondearBigDecimal(bdeValIva_BasImpGra, objParSis.getDecimalesMostrar());  
                     }
                     
                     if (strBasTarCer.equals(""))
                     bdeBasTarCer = new BigDecimal("0.00");
                     else
                     {  bdeBasTarCer = new BigDecimal(strBasTarCer);
                     bdeBasTarCer = objUti.redondearBigDecimal(bdeBasTarCer, objParSis.getDecimalesMostrar());
                     }
                   
                     if ( !bdeValIva_BasImpGra.equals(new BigDecimal("0.00")) && !bdeBasTarCer.equals(new BigDecimal("0.00")) )
                     {  //Si val_col_INT_TBL_DAT_IVA <> 0 y val_col_INT_TBL_DAT_BAS_TAR_CER <> 0, significa que la col. 
                     //INT_TBL_DAT_BAS_TAR_CER debe estar en blanco, y ese valor debe estar en la col. INT_TBL_DAT_BAS_IMP_GRB
                     objTblMod.setValueAt("", i, INT_TBL_DAT_BAS_TAR_CER);
                     objTblMod.setValueAt(strBasTarCer, i, INT_TBL_DAT_BAS_IMP_GRB);
                     }
                     */
                    if (strCodRetFte.equals("322")) {  //322 = Cod.Ret.Fuente usado para compañias de seguros
                        strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_SUB) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_SUB).toString().trim();

                        if (strAuxLoc.equals("")) {
                            bdeSub = new BigDecimal("0.00");
                        } else {
                            bdeSub = new BigDecimal(strAuxLoc);
                            bdeSub = objUti.redondearBigDecimal(bdeSub, objParSis.getDecimalesMostrar());
                        }

                        strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA).toString().trim();

                        if (strAuxLoc.equals("")) {
                            bdeValIva_Sub = new BigDecimal("0.00");
                        } else {
                            bdeValIva_Sub = new BigDecimal(strAuxLoc);
                            bdeValIva_Sub = objUti.redondearBigDecimal(bdeValIva_Sub, objParSis.getDecimalesMostrar());
                        }

                        objTblMod.setValueAt(bdeSub, i, INT_TBL_DAT_BAS_IMP_GRB);
                        objTblMod.setValueAt(bdeValIva_Sub, i, INT_TBL_DAT_IVA);

                        //Se va a obtener el valor que ira en la columna "Bas.Ret2""
                        strSQL = "select a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a2.nd_porret, a2.tx_aplret, a2.nd_basImp, a3.nd_sub, (a3.nd_sub - a2.nd_basImp) as nd_dif_sub_basImp ";
                        strSQL += "from tbm_detpag as a1 ";
                        strSQL += "inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                        strSQL += "inner join tbm_cabmovinv as a3 on a2.co_emp = a3.co_emp and a2.co_loc = a3.co_loc and a2.co_tipdoc = a3.co_tipdoc and a2.co_doc = a3.co_doc ";
                        strSQL += "where a2.st_reg in ('A','C') and a3.st_reg = 'A' ";
                        strSQL += "      and a1.co_emp = " + intCodEmp;
                        strSQL += "      and a1.co_loc = " + intCodLoc;
                        strSQL += "      and a1.co_tipdoc = " + intCodTipDoc;
                        strSQL += "      and a1.co_doc = " + intCodDoc;
                        rst = stm.executeQuery(strSQL);

                        if (rst.next()) {
                            bdeAux = rst.getBigDecimal("nd_dif_sub_basImp");
                            bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                            objTblMod.setValueAt(bdeAux, i, INT_TBL_DAT_BAS_RET2);
                            objTblMod.setValueAt("332", i, INT_TBL_DAT_COD_RETF2);
                        }
                    } //if (strCodRetFte.equals("322"))

                    //Se va a realizar el calculo de Bas.Imp.Gra. y Bas.Tar.Cero
                    //----------------------------------------------------------
                    bdeBasImpGra = new BigDecimal("0.00");
                    bdeBasTarCer = new BigDecimal("0.00");
                    blnEsDocPorPag = false;
                    blnFacPrvIgu1 = false;
                    intCodIvaSri = 0;
                    datFecEmi = objUti.parseDate("1900/01/01", "yyyy/MM/dd");
                    
                    //Se va a determinar si el Cod.Tip.Doc corresponde o no a un Documento por pagar
                    strSQL = "select co_emp, co_loc, co_tipdoc, tx_descor, tx_deslar ";
                    strSQL += "from tbm_cabtipdoc ";
                    strSQL += "where co_emp = " + intCodEmp;
                    strSQL += "      and co_loc = " + intCodLoc;
                    strSQL += "      and co_tipdoc = " + intCodTipDoc;
                    rst = stm.executeQuery(strSQL);

                    if (rst.next()) {
                        strAuxLoc = rst.getString("tx_descor");
                        if (strAuxLoc.startsWith("OP")) {
                            blnEsDocPorPag = true;
                        }
                    }

                    if (blnEsDocPorPag == true) 
                    {   //Este query solo tomara en cuenta los Documentos por pagar para realizar el calculo de Bas.Imp.Gra. y Bas.Tar.Cero
                        //Se va a obtener el Por.Iva del Documento por pagar
                        bdePorIva = new BigDecimal("0");
                        strSQL =  "select nd_porIva, fe_doc ";
                        strSQL += "from tbm_cabMovInv ";
                        strSQL += "where co_emp = " + intCodEmp;
                        strSQL += "      and co_loc = " + intCodLoc;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc;
                        strSQL += "      and co_doc = " + intCodDoc;
                        rst = stm.executeQuery(strSQL);
                        
                        if (rst.next())
                        {  bdePorIva = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva"), objParSis.getDecimalesMostrar());
                           strAuxLoc = objUti.formatearFecha(rst.getDate("fe_doc"), "yyyy/MM/dd"); //Se pone en el formato yyyy/MM/dd para la conversion a tipo Date
                           datFecEmi = objUti.parseDate(strAuxLoc, "yyyy/MM/dd");
                        }
                        System.out.println(datFecEmi.compareTo(datFecFinFacIva14));
                        if (bdePorIva.equals(new BigDecimal("12.00")) && datFecEmi.compareTo(datFecIniFacIva14) >= 0 && datFecEmi.compareTo(datFecFinFacIva14)<0)
                        {  intCodIvaSri = 2; //Cod_Iva_Sri 2 = IVA 12%
                        }
                        else if (datFecEmi.compareTo(datFecIniFacIva14) < 0)
                           intCodIvaSri = 2;
                        else if (datFecEmi.compareTo(datFecFinFacIva14)>=0 && bdePorIva.equals(new BigDecimal("12.00")))
                            intCodIvaSri=2;
                        else
                           intCodIvaSri = 3; //Cod_Iva_Sri 3 = IVA 14%
                        
                        strSQL =  "select sum(nd_can * nd_preuni) as nd_basImp, st_iva ";
                        strSQL += "from tbm_detConIntMovInv ";
                        strSQL += "where co_emp = " + intCodEmp;
                        strSQL += "      and co_loc = " + intCodLoc;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc;
                        strSQL += "      and co_doc = " + intCodDoc + " ";
                        strSQL += "group by st_iva";
                    } 
                    else 
                    {   strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA).toString().trim();

                        if (strAuxLoc.equals("")) {
                            bdeValIva_Sub = new BigDecimal("0.00");
                        } else {
                            bdeValIva_Sub = new BigDecimal(strAuxLoc);
                            bdeValIva_Sub = objUti.redondearBigDecimal(bdeValIva_Sub, objParSis.getDecimalesMostrar());
                        }

                        //objTblMod.setValueAt(bdeValIva_Sub, i, INT_TBL_DAT_IVA);
                        intCodEmp_RelRet = 0;
                        intCodLoc_RelRet = 0;
                        intCodTipDoc_RelRet = 0;
                        intCodDoc_RelRet = 0;

                        //De la Retencion, se va a obtener la PK del documento que esta relacionado con dicha Retencion.
                        strSQL = "select distinct a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc ";
                        strSQL += "from tbm_detpag as a1 ";
                        strSQL += "inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                        strSQL += "where a2.st_reg in ('A','C') ";
                        strSQL += "   and a1.co_emp = " + intCodEmp;
                        strSQL += "   and a1.co_loc = " + intCodLoc;
                        strSQL += "   and a1.co_tipdoc = " + intCodTipDoc;
                        strSQL += "   and a1.co_doc = " + intCodDoc;
                        rst = stm.executeQuery(strSQL);
                        if (rst.next()) {
                            intCodEmp_RelRet = rst.getInt("co_emp");
                            intCodLoc_RelRet = rst.getInt("co_loc");
                            intCodTipDoc_RelRet = rst.getInt("co_tipdoc");
                            intCodDoc_RelRet = rst.getInt("co_doc");
                        }
                        
                        //De la Retencion, se va a obtener el Por.Iva del documento que esta relacionado con dicha Retencion.
                        bdePorIva = new BigDecimal("0");
                        if (intCodTipDoc==74) {
                        strSQL =  "select nd_porIva, fe_doc ";
                        strSQL += "from tbm_cabMovInv ";
                        strSQL += "where co_emp = " + intCodEmp;
                        strSQL += "      and co_loc = " + intCodLoc;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc;
                        strSQL += "      and co_doc = " + intCodDoc;
                        }else {
                         strSQL =  "select nd_porIva, fe_doc ";
                        strSQL += "from tbm_cabMovInv ";
                        strSQL += "where co_emp = " + intCodEmp_RelRet;
                        strSQL += "      and co_loc = " + intCodLoc_RelRet;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc_RelRet;
                        strSQL += "      and co_doc = " + intCodDoc_RelRet;
                        }
                       
                        rst = stm.executeQuery(strSQL);
                        if (rst.next())
                        {  bdePorIva = objUti.redondearBigDecimal(rst.getBigDecimal("nd_poriva"), objParSis.getDecimalesMostrar());
                           strAuxLoc = objUti.formatearFecha(rst.getDate("fe_doc"), "yyyy/MM/dd"); //Se pone en el formato yyyy/MM/dd para la conversion a tipo Date
                           datFecEmi = objUti.parseDate(strAuxLoc, "yyyy/MM/dd");
                        }
                        
                        if (bdePorIva.equals(new BigDecimal("12.00")) && intCodEmp_RelRet == 2 && datFecEmi.compareTo(datFecIniFacIva14) >= 0 && datFecEmi.compareTo(datFecFinFacIva14)<0)
                        {  intCodIvaSri = 2; //Cod_Iva_Sri 2 = IVA 12%
                        }
                        else if (datFecEmi.compareTo(datFecIniFacIva14) < 0){
                           intCodIvaSri = 2;}
                        else if (datFecEmi.compareTo(datFecFinFacIva14)>=0 && bdePorIva.equals(new BigDecimal("12.00")))
                            intCodIvaSri=2;
                        else{
                           intCodIvaSri = 3; //Cod_Iva_Sri 3 = IVA 14%
                        }

                        //Se va a obtener la Descripcion corta del Cod.Tip.Doc relacionado con la Retencion
                        strDesCorTipDocLoc = "";
                        strSQL = "select co_emp, co_loc, co_tipdoc, tx_descor, tx_deslar ";
                        strSQL += "from tbm_cabtipdoc ";
                        strSQL += "where co_emp = " + intCodEmp_RelRet;
                        strSQL += "      and co_loc = " + intCodLoc_RelRet;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc_RelRet;
                        rst = stm.executeQuery(strSQL);

                        if (rst.next()) {
                            strDesCorTipDocLoc = rst.getString("tx_descor");
                        }

                        if (strDesCorTipDocLoc.startsWith("OP") || strDesCorTipDocLoc.startsWith("LIQCOM")) {
                            objTblMod.setValueAt(bdeValIva_Sub, i, INT_TBL_DAT_IVA);
                            //En este query se toma en cuenta las Retenciones relacionadas con un Documento por pagar o con una LIQCOM para 
                            //realizar el calculo de Bas.Imp.Gra. y Bas.Tar.Cero
                            strSQL = "select (case when sum(a.nd_tot) is not null then sum(a.nd_tot) else 0 end) as nd_basImp, ";
                            strSQL += "       st_iva ";
                            strSQL += "from ( select round( (b1.nd_can * b1.nd_preuni), 2) as nd_tot, b1.st_iva ";
                            strSQL += "       from tbm_detConIntMovInv as b1 ";
                            strSQL += "       inner join ( select distinct a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc "; //Los campos del Select (cod_emp, cod_loc, cod_tipdoc, co_doc) corresponden a un Documento por pagar o una LIQCOM
                            strSQL += "                    from tbm_detpag as a1 ";
                            strSQL += "                    inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                            strSQL += "                    where a2.st_reg in ('A','C') ";
                            strSQL += "                       and a1.co_emp = " + intCodEmp;
                            strSQL += "                       and a1.co_loc = " + intCodLoc;
                            strSQL += "                       and a1.co_tipdoc = " + intCodTipDoc;
                            strSQL += "                       and a1.co_doc = " + intCodDoc;
                            strSQL += "                  ) as x on b1.co_emp = x.co_emp and b1.co_loc = x.co_loc and b1.co_tipdoc = x.co_tipdoc and b1.co_doc = x.co_doc ";
                            strSQL += "     ) as a ";
                            strSQL += "group by st_iva";

                        } else if (strDesCorTipDocLoc.startsWith("FACCOM")) {  //En este query se toma en cuenta las Retenciones relacionadas con una Orden de compra para realizar el calculo de 
                            //Bas.Imp.Gra. y Bas.Tar.Cero
                            strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_TOT) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_TOT).toString().trim();

                            if (strAuxLoc.equals("")) {
                                bdeAux = new BigDecimal("0.00");
                            } else {
                                bdeAux = new BigDecimal(strAuxLoc);
                                bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                            }

                            //Se va a verificar si la FACCOM tiene asociada mas de una Factura de proveedor
                            strSQL = "select count(b1.*) as cont_reg from tbr_detrecdoccabmovinv as b1 ";
                            strSQL += "inner join ( select distinct a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc "; //Los campos del Select (cod_emp, cod_loc, cod_tipdoc, co_doc) corresponden a una Orden de compra
                            strSQL += "             from tbm_detpag as a1 ";
                            strSQL += "             inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                            strSQL += "             inner join tbm_cabmovinv as a3 on a2.co_emp = a3.co_emp and a2.co_loc = a3.co_loc and a2.co_tipdoc = a3.co_tipdoc and a2.co_doc = a3.co_doc ";
                            strSQL += "             where a2.st_reg in ('A','C') and a3.nd_tot = " + bdeAux; //bdeAux = Val.Col.INT_TBL_DAT_TOT
                            strSQL += "                and a1.co_emp = " + intCodEmp;
                            strSQL += "                and a1.co_loc = " + intCodLoc;
                            strSQL += "                and a1.co_tipdoc = " + intCodTipDoc;
                            strSQL += "                and a1.co_doc = " + intCodDoc;
                            strSQL += "           ) as b2 on b2.co_emp = b1.co_emp and b2.co_loc = b1.co_locrel and b2.co_tipdoc = b1.co_tipdocrel and b2.co_doc = b1.co_docrel ";
                            strSQL += "where b1.st_reg = 'A' ";
                            rst = stm.executeQuery(strSQL);
                            blnFacPrvIgu1 = false; //blnFacPrvIgu1 = Fac.Proveedor es igual a 1

                            if (rst.next()) {
                                if (rst.getInt("cont_reg") == 1) {  //Significa que se encontro solamente una Factura de proveedor asociada a esta FACCOM
                                    blnFacPrvIgu1 = true;
                                }
                            }

                            if (blnFacPrvIgu1 == true)
                            {   objTblMod.setValueAt(bdeValIva_Sub, i, INT_TBL_DAT_IVA);
                                strSQL = "select sum(a.nd_tot) as nd_basImp, st_iva ";
                                strSQL += "from ( select (round( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)), 2 )) as nd_tot, ";
                                strSQL += "              b1.st_ivaCom as st_iva ";
                                strSQL += "       from tbm_detMovInv as b1 ";
                                strSQL += "       inner join ( select distinct a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc ";
                                strSQL += "                    from tbm_detpag as a1 ";
                                strSQL += "                    inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                                strSQL += "                    inner join tbm_cabmovinv as a3 on a2.co_emp = a3.co_emp and a2.co_loc = a3.co_loc and a2.co_tipdoc = a3.co_tipdoc and a2.co_doc = a3.co_doc ";
                                strSQL += "                    where a2.st_reg in ('A','C') and a3.nd_tot = " + bdeAux;
                                strSQL += "                       and a1.co_emp = " + intCodEmp;
                                strSQL += "                       and a1.co_loc = " + intCodLoc;
                                strSQL += "                       and a1.co_tipdoc = " + intCodTipDoc;
                                strSQL += "                       and a1.co_doc = " + intCodDoc;
                                strSQL += "                  ) as x on b1.co_emp = x.co_emp and b1.co_loc = x.co_loc and b1.co_tipdoc = x.co_tipdoc and b1.co_doc = x.co_doc ";
                                strSQL += "     ) as a ";
                                strSQL += "group by st_iva";
                            }
                            else if (blnFacPrvIgu1 == false)
                            {  bdeBasImpGra = new BigDecimal("0");
                               bdeBasTarCer = new BigDecimal("0");
                               bdePorIvaAux = new BigDecimal("0");
                               arlDatOrdCom = new ArrayList();
                               //Se va a verificar todas las FACCOM que esten relacionados con la Retencion
                               strSQL =  "select distinct a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a3.nd_poriva ";
                               strSQL += "from tbm_detpag as a1 ";
                               strSQL += "inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                               strSQL += "inner join tbm_cabmovinv as a3 on a3.co_emp = a2.co_emp and a3.co_loc = a2.co_loc and a3.co_tipdoc = a2.co_tipdoc and a3.co_doc = a2.co_doc ";
                               strSQL += "where a2.st_reg in ('A','C') ";
                               strSQL += "   and a1.co_emp = " + intCodEmp;
                               strSQL += "   and a1.co_loc = " + intCodLoc;
                               strSQL += "   and a1.co_tipdoc = " + intCodTipDoc;
                               strSQL += "   and a1.co_doc = " + intCodDoc;
                               rst = stm.executeQuery(strSQL);
                               
                               while (rst.next())
                               {  arlRegOrdCom = new ArrayList();
                                  arlRegOrdCom.add(INT_ARL_DAT_COD_EMP1, "" + rst.getInt("co_emp"));
                                  arlRegOrdCom.add(INT_ARL_DAT_COD_LOC1, "" + rst.getInt("co_loc"));
                                  arlRegOrdCom.add(INT_ARL_DAT_COD_TIP_DOC1, "" + rst.getInt("co_tipdoc"));
                                  arlRegOrdCom.add(INT_ARL_DAT_COD_DOC1, "" + rst.getInt("co_doc"));
                                  arlRegOrdCom.add(INT_ARL_DAT_POR_IVA1, "" + rst.getBigDecimal("nd_poriva"));
                                  arlDatOrdCom.add(arlRegOrdCom);
                               }
                               
                               k = 0;
                               
                               for (j = 0; j < arlDatOrdCom.size(); j++)
                               {  k++;
                                  intCodEmpAux = objUti.getIntValueAt(arlDatOrdCom, j, INT_ARL_DAT_COD_EMP1);
                                  intCodLocAux = objUti.getIntValueAt(arlDatOrdCom, j, INT_ARL_DAT_COD_LOC1);
                                  intCodTipDocAux = objUti.getIntValueAt(arlDatOrdCom, j, INT_ARL_DAT_COD_TIP_DOC1);
                                  intCodDocAux = objUti.getIntValueAt(arlDatOrdCom, j, INT_ARL_DAT_COD_DOC1);
                                  bdePorIvaAux = objUti.getBigDecimalValueAt(arlDatOrdCom, j, INT_ARL_DAT_POR_IVA1);
                                  
                                  strSQL =  "select sum(a.nd_tot) as nd_basImp, st_iva ";
                                  strSQL += "from ( select (round( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)), 2 )) as nd_tot, ";
                                  strSQL += "              b1.st_ivaCom as st_iva ";
                                  strSQL += "       from tbm_detMovInv as b1 ";
                                  strSQL += "       where b1.co_emp = " + intCodEmpAux;
                                  strSQL += "          and b1.co_loc = " + intCodLocAux;
                                  strSQL += "          and b1.co_tipdoc = " + intCodTipDocAux;
                                  strSQL += "          and b1.co_doc = " + intCodDocAux;
                                  strSQL += "     ) as a ";
                                  strSQL += "group by st_iva";
                                  rst = stm.executeQuery(strSQL);
                                  
                                  while (rst.next()) 
                                  {  if (rst.getString("st_iva").equals("S")) 
                                     {  bdeBasImpGra = bdeBasImpGra.add(rst.getBigDecimal("nd_basImp"));
                                     } 
                                     else 
                                     {  bdeBasTarCer = bdeBasTarCer.add(rst.getBigDecimal("nd_basImp"));
                                     }
                                  }
                               }
                               
                               if (k > 1)
                               {  //Este segmento de codigo solo es aplicado si se encuentra mas de una FACCOM que esten relacionadas con la Retencion
                                  bdeValIva_BasImpGra = new BigDecimal("0");
                                  bdeBasImpGra = objUti.redondearBigDecimal(bdeBasImpGra, objParSis.getDecimalesMostrar());
                                  
                                  if (bdeBasImpGra.compareTo(new BigDecimal("0.00")) > 0)
                                  {  bdeValIva_BasImpGra = bdeBasImpGra.multiply(bdePorIvaAux).divide(new BigDecimal("100"));
                                     bdeValIva_BasImpGra = objUti.redondearBigDecimal(bdeValIva_BasImpGra, objParSis.getDecimalesMostrar());
                                  }
                                  
                                  objTblMod.setValueAt(bdeBasTarCer, i, INT_TBL_DAT_BAS_TAR_CER);
                                  objTblMod.setValueAt(bdeBasImpGra, i, INT_TBL_DAT_BAS_IMP_GRB);
                                  objTblMod.setValueAt(bdeValIva_BasImpGra, i, INT_TBL_DAT_IVA);
                                  //Se pone un query que no devuelva ningun registro
                                  strSQL = "select co_emp from tbm_emp where 1 = 0";
                               }
                               else
                               {  //Este segmento de codigo solo es aplicado si se encuentra UNA SOLA FACCOM que este relacionada con la Retencion
                                  //Se pone un query que no devuelva ningun registro. De esta manera, se conserva los valores iniciales de las col. 
                                  //"Bas.Imp.Gra." y "Iva" (al lado derecho de col. "Bas.Tar,0") obtenidos a traves del query principal.
                                  strSQL = "select co_emp from tbm_emp where 1 = 0";
                               }
                            }
                            else
                            {  //Se pone un query que no devuelva ningun registro
                               strSQL = "select co_emp from tbm_emp where 1 = 0";
                            } //if (blnFacPrvIgu1 == false)
                        } //else if (strDesCorTipDocLoc.startsWith("FACCOM"))
                        else {  //Se pone un query que no devuelva ningun registro
                            strSQL = "select co_emp from tbm_emp where 1 = 0";
                        }
                    } //if (blnEsDocPorPag == true)

                    rst = stm.executeQuery(strSQL);
                    blnExiReg = false;

                    while (rst.next()) {
                        blnExiReg = true;

                        if (rst.getString("st_iva").equals("S")) {
                            bdeBasImpGra = rst.getBigDecimal("nd_basImp");
                        } else {
                            bdeBasTarCer = rst.getBigDecimal("nd_basImp");
                        }
                    }
                    
                    if (blnFacPrvIgu1 == true)
                    {  //Si blnFacPrvIgu1 = true, significa que se encontro solamente una Factura de proveedor asociada a la FACCOM.
                       //Se van a sumar loa valores de las columnas "Iva1" y "Iva2"
                       bdeValIva1_Iva2 = new BigDecimal("0.00");
                       strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_IVA1) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_IVA1).toString().trim();

                       if (!strAuxLoc.equals("")) 
                       {  bdeValIva1_Iva2 = new BigDecimal(strAuxLoc);
                          bdeValIva1_Iva2 = objUti.redondearBigDecimal(bdeValIva1_Iva2, objParSis.getDecimalesMostrar());
                       }
                       
                       strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_IVA2) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_IVA2).toString().trim();

                       if (!strAuxLoc.equals("")) 
                       {  bdeAux = new BigDecimal(strAuxLoc);
                          bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());
                          bdeValIva1_Iva2 = bdeValIva1_Iva2.add(bdeAux);
                       }
                       
                       strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA).toString().trim();

                       if (strAuxLoc.equals("")) 
                       {  bdeValIva_Sub = new BigDecimal("0.00");
                       }
                       else
                       {  bdeValIva_Sub = new BigDecimal(strAuxLoc);
                          bdeValIva_Sub = objUti.redondearBigDecimal(bdeValIva_Sub, objParSis.getDecimalesMostrar());
                       }
                       
                       if (!bdeValIva_Sub.equals(bdeValIva1_Iva2))
                       {
                           if (bdeValIva1_Iva2.compareTo(new BigDecimal(0.00))==1) { //tony //Se agrega validación que si tiene el bdeValIva1_Iva2 igual a 0 entonces no lo reemplazo por ese valor 
                               //Si es diferente, entonces bdeValIva1_Iva2 tiene el valor correcto del Iva. Basado en este valor de Iva, se va a calcular
                                //el valor que va en la col. "Bas.Imp.Gra."
                                bdeAux = bdePorIva.divide(new BigDecimal("100"));
                                bdeBasImpGra = bdeValIva1_Iva2.divide(bdeAux, RoundingMode.HALF_UP);
                                bdeBasImpGra = objUti.redondearBigDecimal(bdeBasImpGra, objParSis.getDecimalesMostrar());
                                objTblMod.setValueAt(bdeValIva1_Iva2, i, INT_TBL_DAT_IVA); //Col. "Iva"
                           }
                           
                       }
                    } //if (blnFacPrvIgu1 == true)

                    if (blnExiReg == true) {
                        objTblMod.setValueAt(bdeBasTarCer, i, INT_TBL_DAT_BAS_TAR_CER);
                        objTblMod.setValueAt(bdeBasImpGra, i, INT_TBL_DAT_BAS_IMP_GRB);
                    }
                    //Fin de calculo de Bas.Imp.Gra. y Bas.Tar.Cero
                    
                    objTblMod.setValueAt(intCodIvaSri, i, INT_TBL_DAT_COD_IVA);
                    
                    if (bdePorIva.equals(new BigDecimal("12.00")) && datFecEmi.compareTo(datFecIniFacIva14) >= 0 && datFecEmi.compareTo(datFecFinFacIva14)<0)
                    {  bdeAux = bdeBasImpGra.multiply(new BigDecimal("0.02")); //Valor de Compensacion Solidaria
                       bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());

                        if (bdeAux.equals(new BigDecimal("0.00")))
                        {  strAuxLoc = "";
                        }
                        else 
                        {  strAuxLoc = bdeAux.toString();
                        }
                        
                        objTblMod.setValueAt(strAuxLoc, i, INT_TBL_DAT_VAL_COM_SOL);
                    }
                    
                    if (!strCodRetFte.equals("") && objUti.isNumero(strCodRetFte) == false) {
                        objTblMod.setValueAt("", i, INT_TBL_DAT_COD_RETF);
                    }
                    if (intCodTipDoc==74) {
                        String strNumSer="", strNumSec="", strNumAut="";
                        strSQL =  "select tx_numchq as NUMSEC,tx_numautsri as NUMAUT,tx_numser as NUMSER ";
                        strSQL += "from tbm_pagMovInv ";
                        strSQL += "where co_emp = " + intCodEmp;
                        strSQL += "      and co_loc = " + intCodLoc;
                        strSQL += "      and co_tipdoc = " + intCodTipDoc;
                        strSQL += "      and co_doc = " + intCodDoc;
                        System.out.println(strSQL);
                        rst = stm.executeQuery(strSQL);
                        if (rst.next())
                        {  
                           strNumSer = rst.getString("NUMSER");
                           strNumSec = rst.getString("NUMSEC"); 
                           strNumAut = rst.getString("NUMAUT");
                        }
                     
                        objTblMod.setValueAt(strNumSer, i, INT_TBL_DAT_NUM_SER_ORD);
                        objTblMod.setValueAt(strNumSec, i, INT_TBL_DAT_NUM_SEC_ORD);
                        objTblMod.setValueAt(strNumAut, i, INT_TBL_DAT_NUM_AUT_ORD);
                    }
                } //for (i = 0; i < objTblMod.getRowCountTrue(); i++)

                rst.close();
                stm.close();
                rst = null;
                stm = null;

                if (blnCon) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } else {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                butCon.setText("Consultar");
                pgrSis.setValue(0);

            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    } //Metodo cargarDetReg()

    /**
     * Esta función permite establecer la conexión
     *
     * @return true: Si se pudo establecer conexión y cargar datos.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() {
        boolean blnRes = true;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                if (cargarDetReg()) {
//                    if(completaColumnaIva()){
                    lblMsgSis.setText("Se encontraron " + objTblMod.getRowCountTrue() + " registros.");
//                    }
                }
                objTblTot.calcularTotales();

                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaRet extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblRet.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_RET_COD:
                    strMsg = "Codigo del Local";
                    break;
                case INT_TBL_DAT_RET_DES_COR:
                    strMsg = "Descripción corta de la retención";
                    break;
                case INT_TBL_DAT_RET_DES_LAR:
                    strMsg = "Descripción larga de la retención";
                    break;
                case INT_TBL_DAT_RET_POR_RET:
                    strMsg = "Porcentaje de retención";
                    break;
                case INT_TBL_DAT_RET_APL:
                    strMsg = "Aplica a";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblRet.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP:
                    strMsg = "Codigo de empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg = "Codigo del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg = "Código de documento";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg = "Código del cliente";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg = "Nombre del cliente";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg = "Fecha del documento al que se le emite la retención";
                    break;
                case INT_TBL_DAT_NOM_DOC:
                    strMsg = "Tipo de documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_DAT_SUB:
                    strMsg = "Subtotal del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_VAL_IVA:
                    strMsg = "Iva del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_TOT:
                    strMsg = "Total del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_TIP_IDE:
                    strMsg = "Tipo de identificación del cliente";
                    break;
                case INT_TBL_DAT_RUC:
                    strMsg = "RUC/Cédula o Pasaporte";
                    break;
                case INT_TBL_DAT_TIP_COM:
                    strMsg = "Tipo de comprobante";
                    break;
                case INT_TBL_DAT_FEC_EMI:
                    strMsg = "Fecha de emisión del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_FEC_REG:
                    strMsg = "Fecha de la emisión de la retención";
                    break;
                case INT_TBL_DAT_NUM_SER_ORD:
                    strMsg = "Número de serie del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_NUM_SEC_ORD:
                    strMsg = "Número de secuencia del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_NUM_AUT_ORD:
                    strMsg = "Número de autorización del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_COD_CRE:
                    strMsg = "Código de crédito o sustento tributario";
                    break;
                case INT_TBL_DAT_BAS_IMP_GRB:
                    strMsg = "Base imponible grabada";
                    break;
                case INT_TBL_DAT_COD_IVA:
                    strMsg = "Código de Iva";
                    break;
                case INT_TBL_DAT_BAS_TAR_CER:
                    strMsg = "Base tarifa cero";
                    break;
                case INT_TBL_DAT_IVA:
                    strMsg = "Iva";
                    break;
                case INT_TBL_DAT_BAS_IMP_ICE:
                    strMsg = "Base imponible del ICE";
                    break;
                case INT_TBL_DAT_COD_ICE:
                    strMsg = "Código del ICE";
                    break;
                case INT_TBL_DAT_ICE:
                    strMsg = "Valor del ICE";
                    break;
                case INT_TBL_DAT_IVA1:
                    strMsg = "Monto IVA bienes";
                    break;
                case INT_TBL_DAT_COD_RET1:
                    strMsg = "Código de porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_RET1:
                    strMsg = "Monto de retención IVA bienes";
                    break;
                case INT_TBL_DAT_IVA2:
                    strMsg = "Monto IVA servicios";
                    break;
                case INT_TBL_DAT_COD_RET2:
                    strMsg = "Código porcentaje de retención IVA-Servicios";
                    break;
                case INT_TBL_DAT_VAL_RET2:
                    strMsg = "Monto retención IVA Servicios";
                    break;
                case INT_TBL_DAT_DEV:
                    strMsg = "Transacción con derecho a devolución";
                    break;
                case INT_TBL_DAT_FEC_CAD:
                    strMsg = "Fecha de caducidad del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_COD_RETF:
                    strMsg = "Código del porcentaje de retención";
                    break;
                case INT_TBL_DAT_VAL_RETF:
                    strMsg = "Monto de la retención en la fuente";
                    break;
                case INT_TBL_DAT_BAS_RET:
                    strMsg = "Base imponible de la retención";
                    break;
                case INT_TBL_DAT_FEC_RET:
                    strMsg = "Fecha de la retención";
                    break;
                case INT_TBL_DAT_SER_RET:
                    strMsg = "Número de serie de la retención";
                    break;
                case INT_TBL_DAT_NUM_RET:
                    strMsg = "Número secuencial de la retención";
                    break;
                case INT_TBL_DAT_AUT_RET:
                    strMsg = "Número de autorización de la retención";
                    break;
                case INT_TBL_DAT_BAS_RET2:
                    strMsg = "BASERET";
                    break;
                case INT_TBL_DAT_COD_RETF2:
                    strMsg = "CODRET2";
                    break;
                case INT_TBL_DAT_VAL_RETF2:
                    strMsg = "VALRETF";
                    break;
                case INT_TBL_DAT_BAS_RET3:
                    strMsg = "BASERET3";
                    break;
                case INT_TBL_DAT_COD_RETF3:
                    strMsg = "CODRET2";
                    break;
                case INT_TBL_DAT_VAL_RETF3:
                    strMsg = "VALRETF";
                    break;
                case INT_TBL_DAT_TIP_COM2:
                    strMsg = "TIPCOM2";
                    break;
                case INT_TBL_DAT_FEC_EMI2:
                    strMsg = "FECEMI2";
                    break;
                case INT_TBL_DAT_NUM_SER2:
                    strMsg = "NUMSER2";
                    break;
                case INT_TBL_DAT_NUM_SEC2:
                    strMsg = "NUMSEC2";
                    break;
                case INT_TBL_DAT_NUM_AUT2:
                    strMsg = "NUMAUT2";
                    break;
                case INT_TBL_DAT_CON_CEP:
                    strMsg = "CONCEPTO";
                    break;
                case INT_TBL_DAT_REF:
                    strMsg = "REFER";
                    break;
                case INT_TBL_DAT_COD_PAG1:
                    strMsg = "CODFPAG1";
                    break;
                case INT_TBL_DAT_COD_PAG2:
                    strMsg = "CODFPAG2";
                    break;
                case INT_TBL_DAT_PAG_EXT:
                    strMsg = "PAGOEXTE";
                    break;
                case INT_TBL_DAT_COD_PAI:
                    strMsg = "CODPAIS";
                    break;
                case INT_TBL_DAT_CNV:
                    strMsg = "CONVENI";
                    break;
                case INT_TBL_DAT_RET_IEN:
                    strMsg = "RETIENE";
                    break;
                case INT_TBL_DAT_TIP_PRO:
                    strMsg = "TIPOPROV";
                    break;
                case INT_TBL_DAT_ES_REL:
                    strMsg = "ESRELACI";
                    break;
                case INT_TBL_DAT_VAL_COM_SOL:
                    strMsg = "VALCOMSOL";
                    break;

                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera
     * del JTable. Se utiliza ésta función especificamente para marcar todas las
     * casillas de verificación de la columna que indica la bodega seleccionada
     * en el el JTable de bodegas.
     */
    private void tblRetMouseClicked(java.awt.event.MouseEvent evt) {
        int i, intNumFil;
        try {
            intNumFil = objTblModRet.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton() == evt.BUTTON1 && evt.getClickCount() == 1 && tblRet.columnAtPoint(evt.getPoint()) == INT_TBL_DAT_RET_CHK) {
                if (blnMarTodChkRet) {
                    for (i = 0; i < intNumFil; i++) {
                        objTblModRet.setChecked(true, i, INT_TBL_DAT_RET_CHK);
                    }
                    blnMarTodChkRet = false;
                } else {
                    for (i = 0; i < intNumFil; i++) {
                        objTblModRet.setChecked(false, i, INT_TBL_DAT_RET_CHK);
                    }
                    blnMarTodChkRet = true;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private boolean completaColumnaIva() {
        boolean blnRes = true;
        BigDecimal bdeBasImpGrb = new BigDecimal("0");
        BigDecimal bdeIva = new BigDecimal("0");
        String strIva = "";
        BigDecimal bdeIvaDoc = new BigDecimal(BigInteger.ZERO);//esto es lo nuevo, si se quita esto, queda como antes
        try {
            for (int i = 0; i < objTblMod.getRowCountTrue(); i++) {
                strIva = objTblMod.getValueAt(i, INT_TBL_DAT_IVA) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_IVA).toString();
                bdeIvaDoc = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA) == null ? "0" : (objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA).toString().equals("") ? "0" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_IVA).toString()));
                if (bdeIvaDoc.compareTo(new BigDecimal(BigInteger.ZERO)) > 0) {
                    bdeBasImpGrb = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_BAS_IMP_GRB) == null ? "0" : objTblMod.getValueAt(i, INT_TBL_DAT_BAS_IMP_GRB).toString());
                    if (strIva.equals("")) {
                        //bdeIva=bdeBasImpGrb.multiply(new BigDecimal("0.12"));
                        bdeIva = bdeBasImpGrb.multiply(bdeIvaGlo);
                        objTblMod.setValueAt(bdeIva, i, INT_TBL_DAT_IVA);
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }
}
