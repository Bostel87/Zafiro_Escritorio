/*
 * ZafCom08.java
 *
 * Created on 16 de enero de 2005, 17:10 PM
 */
package Contabilidad.ZafCon25;

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
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Eddye Lino
 */
public class ZafCon25 extends javax.swing.JInternalFrame {

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
    //datos que pide el reporte
    final int INT_TBL_DAT_RUC = 14;//RUC
    final int INT_TBL_DAT_NUM_RET = 15;//NUMRET
    final int INT_TBL_DAT_FEC_REG = 16;//FEC_REG
    final int INT_TBL_DAT_FEC_EMI = 17;//FEC_EMI
    final int INT_TBL_DAT_TIP_COM = 18;//TIPCOM
    final int INT_TBL_DAT_CMP_VTA = 19;//NUMSER
    final int INT_TBL_DAT_NUM_AUT = 20;//NUMAUT
    final int INT_TBL_DAT_FEC_CAD = 21;//FECCAD
    final int INT_TBL_DAT_BAS_IMP = 22;//BASEIG
    final int INT_TBL_DAT_COD_RET = 23;//CODRET1
    final int INT_TBL_DAT_VAL_RET = 24;//VALRET1

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
    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_EMP = 0;
    final int INT_ARL_COD_LOC = 1;
    final int INT_ARL_COD_TIP_DOC = 2;
    final int INT_ARL_COD_DOC = 3;
    final int INT_ARL_IVA = 4;
    final int INT_ARL_IVA1 = 5;
    final int INT_ARL_COD_RET1 = 6;
    final int INT_ARL_IVA2 = 7;
    final int INT_ARL_COD_RET2 = 8;
    final int INT_ARL_POR_RET_IVA = 9;
    final int INT_ARL_BAS_IMP_IVA = 10;
    final int INT_ARL_EST = 11;

    final int INT_ARL_COD_CLI = 12;
    final int INT_ARL_NOM_CLI = 13;
    final int INT_ARL_FEC_DOC = 14;
    final int INT_ARL_NOM_DOC = 15;
    final int INT_ARL_NUM_DOC = 16;
    final int INT_ARL_FEC_CAD_RET = 17;
    final int INT_ARL_FEC_RET = 18;
    final int INT_ARL_SER_RET = 19;
    final int INT_ARL_NUM_RET = 20;
    final int INT_ARL_AUT_RET = 21;
    final int INT_ARL_COD_DOC_ORD = 22;

    private String strDesCorCta, strDesLarCta;
    private String strDesCorTipDoc, strDesLarTipDoc;
    private ZafVenCon vcoTipDoc, vcoCta;
    private ZafTblCelEdiChk objTblCelEdiChkRet;
    private ZafTblCelRenChk objTblCelRenChkRet;

    private boolean blnPerCon, blnPerCer;
    private ZafPerUsr objPerUsr;
    private String strTipDocUsr;
    private String strVersion = "v0.2.23";

    /**
     * Crea una nueva instancia de la clase ZafIndRpt.
     */
    public ZafCon25(ZafParSis obj) {
        try {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);

            if (!configurarFrm()) {
                exitForm();
            }

            arlDat = new ArrayList();
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
        jPanel2 = new javax.swing.JPanel();
        lblTitTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panCorRpt = new javax.swing.JPanel();
        chkForGamRet = new javax.swing.JCheckBox();
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

        jPanel2.setPreferredSize(new java.awt.Dimension(100, 80));
        jPanel2.setLayout(null);

        lblTitTipDoc.setText("Tipo de documento:");
        jPanel2.add(lblTitTipDoc);
        lblTitTipDoc.setBounds(20, 38, 106, 14);
        jPanel2.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(104, 36, 30, 20);

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
        jPanel2.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(134, 36, 80, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        jPanel2.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(214, 36, 296, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        jPanel2.add(butTipDoc);
        butTipDoc.setBounds(510, 36, 20, 20);

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
        lblPrv.setBounds(21, 57, 100, 16);

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
        txtCodPrv.setBounds(134, 57, 80, 20);

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
        txtDesLarPrv.setBounds(214, 57, 296, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        jPanel2.add(butPrv);
        butPrv.setBounds(510, 57, 20, 20);

        panFil.add(jPanel2, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

        panCorRpt.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panCorRpt.setPreferredSize(new java.awt.Dimension(560, 90));

        chkForGamRet.setSelected(true);
        chkForGamRet.setText("Formato GamaRet");

        javax.swing.GroupLayout panCorRptLayout = new javax.swing.GroupLayout(panCorRpt);
        panCorRpt.setLayout(panCorRptLayout);
        panCorRptLayout.setHorizontalGroup(
            panCorRptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panCorRptLayout.createSequentialGroup()
                .addContainerGap(525, Short.MAX_VALUE)
                .addComponent(chkForGamRet, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );
        panCorRptLayout.setVerticalGroup(
            panCorRptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panCorRptLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(chkForGamRet, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel3.add(panCorRpt, java.awt.BorderLayout.NORTH);
        panCorRpt.getAccessibleContext().setAccessibleName("Codigo");

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

        tblDat.setToolTipText("Doble click o ENTER para abrir la opción seleccionada.");
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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents


    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (isCamVal()) {
            if (butCon.getText().equals("Consultar")) {
                blnCon = true;
                if (objThrGUI == null) {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            } else {
                blnCon = false;
            }
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
        if (!txtDesCorTipDoc.getText().equalsIgnoreCase(strDesCorTipDoc)) {
            if (txtDesCorTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesLarTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(1);
            }
        } else {
            txtDesCorTipDoc.setText(strDesCorTipDoc);
        }
        if (txtDesCorTipDoc.getText().length() > 0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        // TODO add your handling code here:
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        // TODO add your handling code here:
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else {
            txtDesLarTipDoc.setText(strDesLarTipDoc);
        }
        if (txtDesLarTipDoc.getText().length() > 0) {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
}//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        // TODO add your handling code here:
        mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        // TODO add your handling code here:
        if (optTod.isSelected()) {
            optFil.setSelected(false);
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
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
            //filExc=new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon25\\ZafCon25.xls");
            if (System.getProperty("os.name").equals("Linux")) {
                filExc = new java.io.File("/tmp/ZafCon25.xls");
            } else {
                filExc = new java.io.File("C:\\Zafiro\\Reportes\\Contabilidad\\ZafCon25\\ZafCon25.xls");
            }
            String strNomHoj = "Hoja1";

            ZafCon25_01 objCon25_01 = new ZafCon25_01(tblDat, filExc, strNomHoj);
            if (chkForGamRet.isSelected()) {
                if (objCon25_01.exportGamaRet()) {
                    mostrarMsgInf("El archivo se cargó correctamente.");
                    //Process pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon25/ZafCon25.xls");
                    if (System.getProperty("os.name").equals("Linux")) {
                        pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCon25.xls");
                        System.out.println("LINUX: " + pro.toString());
                    } else {
                        pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon25/ZafCon25.xls");
                        System.out.println("WINDOWS: " + pro.toString());
                    }
                } else {
                    mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
                }
            } else {
                if (objCon25_01.export()) {
                    mostrarMsgInf("El archivo se cargó correctamente.");
                    //Process pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon25/ZafCon25.xls");
                    if (System.getProperty("os.name").equals("Linux")) {
                        pro = Runtime.getRuntime().exec("oocalc /tmp/ZafCon25.xls");
                        System.out.println("LINUX: " + pro.toString());
                    } else {
                        pro = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Contabilidad/ZafCon25/ZafCon25.xls");
                        System.out.println("WINDOWS: " + pro.toString());
                    }
                } else {
                    mostrarMsgInf("Falló la carga del archivo. Puede ser que el archivo este abierto.");
                }
            }

        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }//GEN-LAST:event_butExpActionPerformed

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Tipos de documentos".
     */
    private boolean configurarVenConTipDoc() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tipdoc");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.ne_ultDoc");
            arlCam.add("a1.tx_natDoc");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Alias.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");

            strSQL = "";
            strSQL += "SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
            strSQL += " FROM tbm_cabTipDoc AS a1";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();

            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConTipDoc(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
            }

            if (txtCodTipDoc.getText().length() > 0) {
                optFil.setSelected(true);
                optTod.setSelected(false);
            }

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

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
    private javax.swing.JButton butTipDoc;
    private javax.swing.JCheckBox chkForGamRet;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTitTipDoc;
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
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    // End of variables declaration//GEN-END:variables

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            blnPerCon = false;
            blnPerCer = false;

            if (objParSis.getCodigoUsuario() == 1) {
                blnPerCon = true;
                blnPerCer = true;
            } else {
                if (objPerUsr.isOpcionEnabled(1396)) {
                    blnPerCon = true;
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
            vecCab = new Vector(25);    //Almacena las cabeceras
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
            vecCab.add(INT_TBL_DAT_NUM_RET, "Núm.Ret.");
            vecCab.add(INT_TBL_DAT_FEC_REG, "Fec.Reg.");
            vecCab.add(INT_TBL_DAT_FEC_EMI, "Fec.Emi.");
            vecCab.add(INT_TBL_DAT_TIP_COM, "Tip.Com.");
            vecCab.add(INT_TBL_DAT_CMP_VTA, "Com.Vta.");
            vecCab.add(INT_TBL_DAT_NUM_AUT, "Núm.Aut.");
            vecCab.add(INT_TBL_DAT_FEC_CAD, "Fec.Cad.");
            vecCab.add(INT_TBL_DAT_BAS_IMP, "Bas.Imp.");
            vecCab.add(INT_TBL_DAT_COD_RET, "Cód.Ret.");
            vecCab.add(INT_TBL_DAT_VAL_RET, "Val.Ret");

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
            tcmAux.getColumn(INT_TBL_DAT_NUM_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_REG).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_TIP_COM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CMP_VTA).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FEC_CAD).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_RET).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(100);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            //objTblCelRenLbl.setForeground(Color.BLACK);
            tcmAux.getColumn(INT_TBL_DAT_SUB).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_BAS_IMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setCellRenderer(objTblCelRenLbl);

            //para color
            objTblCelRenLblCol = new ZafTblCelRenLbl();
            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblCol.setTipoFormato(objTblCelRenLblCol.INT_FOR_GEN);
            objTblCelRenLblCol.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            //objTblCelRenLblCol.setBackground(colFonColCru);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setCellRenderer(objTblCelRenLblCol);
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    if (objTblMod.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_DAT_LIN).equals("I")) {
                        objTblCelRenLblCol.setForeground(colFonColCru);
                    } else {
                        objTblCelRenLblCol.setForeground(Color.BLACK);
                    }
                }
            });

            tblDat.getTableHeader().setReorderingAllowed(false);

            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);

            configurarVenConPrv();
            configurarVenConTipDoc();

            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodTipDoc.setEnabled(false);

            configurarFrmRet();

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[] = {INT_TBL_DAT_VAL_RET};
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

            objTblModRet.setModoOperacion(objTblMod.INT_TBL_EDI);

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
            arlAli.add("Cádigo");
            arlAli.add("Identificacián");
            arlAli.add("Nombre");
            arlAli.add("Direccián");
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
        int i, intCodEmp, intCodLoc, intCodTipDoc, intCodDoc, intCodEmp_RelRet, intCodLoc_RelRet, intCodTipDoc_RelRet, intCodDoc_RelRet;
        boolean blnRes = true;
        strAux = "";
        vecDat.clear();
        String strRuc = "", strNumDoc, strAuxLoc, strDesCorTipDocLoc, strCodRetSri, strTipIde;
        int intRuc = 0;
        String strNumRet = "";
        int intNumRet = 0;
        String strComVta = "";
        int intComVta = 0;
        String strNumAutSri = "";
        int intNumAutSri = 0;
        String strBasImp = "";
        int intBasImp = 0;
        String strCodRet = "";
        int intCodRet = 0;
        String strValRet = "";
        int intValRet = 0;
        long lngNumDoc;
        BigDecimal bdePorRet, bdeBasImp, bdeBasImpCal, bdeAux, bdeValRetCal1, bdeValRetCal2, bdeValRetDetPag, bdeValRetDetTbl;

        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null) {
                if (!txtCodTipDoc.getText().equals("")) {
                    strAux += " AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                } else {
                    if (getTipDocCab()) {
                        strAux += " AND a1.co_tipDoc IN(" + strTipDocUsr + ")";
                    } else {
                        return false;
                    }
                }

                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Básqueda por rangos
                        strAux += " AND a8.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux += " AND a8.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux += " AND a8.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                        break;
                    case 3: //Todo.
                        break;
                }

                if (!txtCodPrv.getText().equals("")) {
                    strAux += " AND a6.co_cli=" + txtCodPrv.getText() + "";
                }

                stm = con.createStatement();
                lblMsgSis.setText("Cargando datos...");
//                strSQL="";
//                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a4.co_cli, a4.tx_nomCli,";
//                strSQL+=" a4.fe_doc, c1.tx_desCor AS NOM_DOC, a4.ne_numDoc, a4.co_doc AS co_docOrd, (a4.nd_sub)*(-1) AS nd_sub, (a4.nd_valIva)*(-1) AS nd_valIva, (a4.nd_tot)*(-1) AS nd_tot,";
//                strSQL+=" b1.tx_tipide AS TIPOID, a4.tx_ruc AS RUC,  CASE WHEN a3.tx_numChq IS NULL THEN '' ELSE a3.tx_numChq END AS tx_numChq, a4.tx_secDoc, a1.ne_numdoc1, a4.fe_doc AS FEC_REG,";
//                strSQL+=" a4.fe_doc AS FEC_EMI, CASE WHEN a4.co_tipDoc=1 THEN '01'  WHEN a4.co_tipDoc IN(3,28) THEN '04'  ELSE '' END AS TIPCOM,";
//                strSQL+=" b3.tx_numserfac, a4.ne_numDoc, a4.tx_numautsri AS NUMAUT,";
//                strSQL+=" a4.tx_fecCad AS FECCAD,";
//                strSQL+=" CASE WHEN a3.nd_porRet IN(1,2) THEN (a4.nd_sub)*(-1)";
//                strSQL+="       WHEN a3.nd_porRet IN(30,100) THEN (a4.nd_valIva)*(-1) END AS nd_basImp,";
//                strSQL+=" a2.tx_codsri,a3.nd_porRet, a2.nd_abo";
//                strSQL+=" FROM (tbm_cabPag AS a1";
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS b2 ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
//                strSQL+=" INNER JOIN tbm_detPag AS a2 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
//                strSQL+=" INNER JOIN tbm_pagMovInv AS a3 	ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";
//                strSQL+=" INNER JOIN (     (";
//                strSQL+=" (tbm_cabMovInv AS a4 LEFT OUTER JOIN tbm_datautsri AS b3";
//                strSQL+=" ON a4.co_emp=b3.co_emp AND a4.co_loc=b3.co_loc AND a4.co_tipDoc=b3.co_tipDoc";
//                strSQL+=" AND a4.ne_numDoc BETWEEN b3.ne_numdocdes AND b3.ne_numdochas) ";
//                strSQL+="  INNER JOIN";
//                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
//                    strSQL+=" tbm_cli AS b1 ON a4.co_emp=b1.co_emp AND a4.co_cli=b1.co_cli)";
//                }
//                else{
//                    strSQL+=" (tbm_cli AS b1 INNER JOIN tbr_cliLoc AS d1 ON b1.co_emp=d1.co_emp AND b1.co_loc=d1.co_loc) ";
//                    strSQL+=" ON a4.co_emp=b1.co_emp AND a4.co_cli=b1.co_cli)";
//                }
//                strSQL+=" INNER JOIN tbm_cabTipDoc AS c1 ON a4.co_emp=c1.co_emp AND a4.co_loc=c1.co_loc AND a4.co_tipDoc=c1.co_tipDoc)";
//                strSQL+=" 	 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                strSQL+="" + strAux;
//                strSQL+=" AND a1.st_reg NOT IN('E','I') AND a3.st_reg IN('A','C') AND a4.st_reg NOT IN('E','I')";
//                strSQL+=" ORDER BY a1.ne_numDoc1, a1.co_emp, a1.co_loc, a4.co_doc, a3.nd_porRet";

                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a8.co_cli, a8.tx_nomCli,";
                strSQL += " a8.fe_doc, a9.tx_desCor AS NOM_DOC, a8.ne_numDoc, a8.co_doc AS co_docOrd, (a8.nd_sub)*(-1) AS nd_sub";
                strSQL += ", (a8.nd_valIva)*(-1) AS nd_valIva, (a8.nd_tot)*(-1) AS nd_tot,";
                strSQL += "a6.tx_tipide AS TIPOID, a8.tx_ruc AS RUC,  CASE WHEN a3.tx_numChq IS NULL THEN '' ELSE a3.tx_numChq END AS tx_numRetCli";
                strSQL += ", a1.ne_numdoc1, a8.fe_doc AS FEC_REG,";
                strSQL += " a8.fe_doc AS FEC_EMI, CASE WHEN a8.co_tipDoc IN(1,228) THEN '01'  WHEN a8.co_tipDoc IN(3,28,229) THEN '04'  ELSE '' END AS TIPCOM";
                strSQL += " , a3.co_emp, a3.co_loc, a3.co_tipdoc, a3.co_doc";
                strSQL += ", a3.tx_numser AS tx_numSerRetCli, a3.tx_numAutSri AS tx_numAutRetCli";
                strSQL += ", a8.ne_numDoc";
                strSQL += ",a3.tx_fecCad AS FECCAD,";

                //strSQL+=" CASE WHEN a3.nd_porRet IN(1,2) THEN (a8.nd_sub)*(-1)";
                //strSQL+="        WHEN a3.nd_porRet IN(30,100) THEN (a8.nd_valIva)*(-1) END AS nd_basImp,";
                
                //--- Inicio bloque comentado el 11/Jul/2015 ---
                //Esta linea fue comentada en la version del programa 0.2.9 (11/Jul/2015) debido a que se consideraba el valor de 10 en la condicion 
                //del campo tbm_pagMovInv.nd_porret, correspondente a "Retencion en la fuente 10%". Se tuvo que quitar este valor de 10 en la condicion 
                //debido a que ahora ha aparecido el valor de 10 para "Retencion al IVA 10%" y esto iba a ocasionar confusion.
                //
                //strSQL+=" CASE WHEN a3.nd_porRet IN(1,2,8,10) THEN";
                //--- Fin bloque comentado el 11/Jul/2015 ------
                
                //--- Inicio bloque comentado el 19/Dic/2016 ---
                //Esta linea fue comentada en la version del programa 0.2.11 (19/Dic/2016) debido a que en ciertos casos la Base Imponible no
                //se calculaba correctamente.
                //
                //strSQL+=" CASE WHEN a3.nd_porRet IN(1,2,8) THEN";
                //strSQL+="                                       (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ELSE (a8.nd_sub)*(-1) END)";
                //strSQL+="      WHEN a3.nd_porRet IN(30,70,100,10,20) THEN ";
                //strSQL+="                                       (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ELSE (a8.nd_valIva)*(-1) END)";
                //--- Fin bloque comentado el 19/Dic/2016 ------
                
                strSQL += "( CASE WHEN a3.nd_porRet IN(1) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += " 	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "	   	             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_sub)*(-1) ";
                strSQL += "                WHEN a3.tx_codsri = '310' THEN ( select sum(b1.nd_tot * -1) ";
                strSQL += "	                                            from tbm_detMovInv as b1 ";
                strSQL += "	                                            inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                                            where b2.st_ser = 'T' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc "; //tony cambio para que tambien tome en cuenta transporte con bien en retenciones de %1
                strSQL += "                                               ) ";
                strSQL += "                ELSE ( select sum(b1.nd_tot * -1) ";
                strSQL += "	                  from tbm_detMovInv as b1 ";
                strSQL += "	                  inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                  where b2.st_ser = 'N' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc "; //tony cambio para que tambien tome en cuenta transporte con bien en retenciones de %1
                strSQL += "                     ) ";
                strSQL += "          END) ";
                
                strSQL += "       WHEN a3.nd_porRet IN(2,8) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += "	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "		             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_sub)*(-1) ";
                strSQL += "                ELSE ( select sum(b1.nd_tot * -1) ";
                strSQL += "	                from tbm_detMovInv as b1 ";
                strSQL += "	                inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                where b2.st_ser = 'S' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "                     ) ";
                strSQL += "          END) ";

                strSQL += "       WHEN a3.nd_porRet IN(10) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += " 	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "		             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_valIva)*(-1) ";
                strSQL += "                ELSE ( select sum(x.nd_valiva * -1) ";
                strSQL += "                       from ( select b1.st_ivaCom, ";
                strSQL += "                                 ( case when b1.st_ivaCom = 'S' ";
                strSQL += "                                           then round( sum( ";
                strSQL += "                                                            ( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)) ) * (b3.nd_poriva / 100) "; //Se calcula el IVA
                strSQL += "                                                          ) ";
                strSQL += "                                                     , 2) ";
                strSQL += "                                        when b1.st_ivaCom = 'N' then 0 ";
                strSQL += "                                 end ) as nd_valiva ";
                strSQL += "	                       from tbm_detMovInv as b1 ";
                strSQL += "	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                       inner join tbm_cabmovinv as b3 on b1.co_emp = b3.co_emp and b1.co_loc = b3.co_loc and b1.co_tipdoc = b3.co_tipdoc and b1.co_doc = b3.co_doc ";
                strSQL += "	                       where b2.st_ser = 'N' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "	                       group by b1.st_ivaCom ";
                strSQL += "	                     ) as x ";
                strSQL += "                      ) ";
                strSQL += "          END) ";

                strSQL += "       WHEN a3.nd_porRet IN(20,50) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += " 	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "		             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_valIva)*(-1) ";
                strSQL += "                ELSE ( select sum(x.nd_valiva * -1) ";
                strSQL += "                       from ( select b1.st_ivaCom, ";
                strSQL += "                                 ( case when b1.st_ivaCom = 'S' ";
                strSQL += "                                           then round( sum( ";
                strSQL += "                                                            ( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)) ) * (b3.nd_poriva / 100) "; //Se calcula el IVA
                strSQL += "                                                          ) ";
                strSQL += "                                                     , 2) ";
                strSQL += "                                        when b1.st_ivaCom = 'N' then 0 ";
                strSQL += "                                 end ) as nd_valiva ";
                strSQL += "	                       from tbm_detMovInv as b1 ";
                strSQL += "	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                       inner join tbm_cabmovinv as b3 on b1.co_emp = b3.co_emp and b1.co_loc = b3.co_loc and b1.co_tipdoc = b3.co_tipdoc and b1.co_doc = b3.co_doc ";
                strSQL += "	                       where b2.st_ser = 'S' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "	                       group by b1.st_ivaCom ";
                strSQL += "	                     ) as x ";
                strSQL += "                     ) ";
                strSQL += "          END) ";

                strSQL += "       WHEN a3.nd_porRet IN(30) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += " 	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "		             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_valIva)*(-1) ";
                strSQL += "                ELSE ( select sum(x.nd_valiva * -1) ";
                strSQL += "                       from ( select b1.st_ivaCom, ";
                strSQL += "                                 ( case when b1.st_ivaCom = 'S' ";
                strSQL += "                                           then round( sum( ";
                strSQL += "                                                            ( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)) ) * (b3.nd_poriva / 100) "; //Se calcula el IVA
                strSQL += "                                                          ) ";
                strSQL += "                                                     , 2) ";
                strSQL += "                                        when b1.st_ivaCom = 'N' then 0 ";
                strSQL += "                                 end ) as nd_valiva ";
                strSQL += "	                       from tbm_detMovInv as b1 ";
                strSQL += "	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                       inner join tbm_cabmovinv as b3 on b1.co_emp = b3.co_emp and b1.co_loc = b3.co_loc and b1.co_tipdoc = b3.co_tipdoc and b1.co_doc = b3.co_doc ";
                strSQL += "	                       where b2.st_ser = 'N' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "	                       group by b1.st_ivaCom ";
                strSQL += "	                     ) as x ";
                strSQL += "                      ) ";
                strSQL += "          END) ";

                strSQL += "       WHEN a3.nd_porRet IN(70) THEN ";
                strSQL += "          (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ";
                strSQL += "                WHEN ( select count(a.*) ";
                strSQL += "                       from ( select distinct st_ser ";
                strSQL += "                              from tbm_detMovInv as b1 ";
                strSQL += " 	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "                              where b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "		             ) as a ";
                strSQL += "                     ) = 1 THEN (a8.nd_valIva)*(-1) ";
                strSQL += "                ELSE ( select sum(x.nd_valiva * -1) ";
                strSQL += "                       from ( select b1.st_ivaCom, ";
                strSQL += "                                 ( case when b1.st_ivaCom = 'S' ";
                strSQL += "                                           then round( sum( ";
                strSQL += "                                                            ( (b1.nd_can * b1.nd_preuni) - (b1.nd_can * b1.nd_preuni * (b1.nd_pordes / 100)) ) * (b3.nd_poriva / 100) "; //Se calcula el IVA
                strSQL += "                                                          ) ";
                strSQL += "                                                     , 2) ";
                strSQL += "                                        when b1.st_ivaCom = 'N' then 0 ";
                strSQL += "                                 end ) as nd_valiva ";
                strSQL += "	                       from tbm_detMovInv as b1 ";
                strSQL += "	                       inner join tbm_inv as b2 on b1.co_emp = b2.co_emp and b1.co_itm = b2.co_itm ";
                strSQL += "	                       inner join tbm_cabmovinv as b3 on b1.co_emp = b3.co_emp and b1.co_loc = b3.co_loc and b1.co_tipdoc = b3.co_tipdoc and b1.co_doc = b3.co_doc ";
                strSQL += "	                       where b2.st_ser = 'S' and b1.co_emp = a8.co_emp and b1.co_loc = a8.co_loc and b1.co_tipdoc = a8.co_tipdoc and b1.co_doc = a8.co_doc ";
                strSQL += "	                       group by b1.st_ivaCom ";
                strSQL += "	                     ) as x ";
                strSQL += "                     ) ";
                strSQL += "          END) ";

                strSQL += "       WHEN a3.nd_porRet IN(100) THEN (CASE WHEN a3.nd_basImp<>0 THEN (a3.nd_basImp)*(-1) ELSE (a8.nd_valIva)*(-1) END) ";

                strSQL += "END ) AS nd_basImp,";

                strSQL += "a3.tx_codsri,a3.nd_porRet, a2.nd_abo, b3.tx_numserfac";
                strSQL += " FROM (tbm_cabPag AS a1 INNER JOIN tbm_cabTipDoc AS b2";
                strSQL += " 	ON a1.co_emp=b2.co_emp AND a1.co_loc=b2.co_loc AND a1.co_tipDoc=b2.co_tipDoc)";
                strSQL += "  INNER JOIN tbm_detPag AS a2";
                strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL += " INNER JOIN tbm_pagMovInv AS a3";
                strSQL += " ON a2.co_emp=a3.co_emp AND a2.co_locPag=a3.co_loc AND a2.co_tipDocPag=a3.co_tipDoc AND a2.co_docPag=a3.co_doc AND a2.co_regPag=a3.co_reg";

                strSQL += " INNER JOIN (   (        tbm_cabMovInv AS a8 LEFT OUTER JOIN tbm_datautsri AS b3";
                strSQL += "			 ON a8.co_emp=b3.co_emp AND a8.co_loc=b3.co_loc AND a8.co_tipDoc=b3.co_tipDoc";
                strSQL += "			 AND a8.ne_numDoc BETWEEN b3.ne_numdocdes AND b3.ne_numdochas";
                strSQL += "		)";
                strSQL += "		INNER JOIN tbm_cabTipDoc AS a9";
                strSQL += "		ON a8.co_emp=a9.co_emp AND a8.co_loc=a9.co_loc AND a8.co_tipDoc=a9.co_tipDoc)";
                strSQL += " ON a3.co_emp=a8.co_emp AND a3.co_loc=a8.co_loc AND a3.co_tipDoc=a8.co_tipDoc AND a3.co_doc=a8.co_doc";
                strSQL += "";
                strSQL += "";

                strSQL += "  INNER JOIN";
                if (objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())) {
                    strSQL += " tbm_cli AS a6 ON a8.co_emp=a6.co_emp AND a8.co_cli=a6.co_cli";
                } else {
                    strSQL += " tbm_cli AS a6 INNER JOIN tbr_cliLoc AS d1 ON a6.co_emp=d1.co_emp AND a6.co_loc=d1.co_loc";
                }
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "" + strAux;
                strSQL += " AND a1.st_reg NOT IN('E','I') AND a2.st_reg not in('E','I') AND a3.st_reg IN('A','C') AND a8.st_reg NOT IN('E','I')";
                strSQL += " ORDER BY a1.ne_numDoc1, a1.co_emp, a1.co_loc, a8.co_doc, a3.nd_porRet";
                System.out.println("strSQL  fallo: " + strSQL);
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {
                    if (blnCon) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        vecReg.add(INT_TBL_DAT_COD_EMP, "" + rst.getObject("co_emp") == null ? "" : rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC, "" + rst.getObject("co_loc") == null ? "" : rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + rst.getObject("co_tipDoc") == null ? "" : rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC, "" + rst.getObject("co_doc") == null ? "" : rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI, "" + rst.getObject("co_cli") == null ? "" : rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI, "" + rst.getObject("tx_nomCli") == null ? "" : rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC, "" + rst.getObject("fe_doc") == null ? "" : rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_NOM_DOC, "" + rst.getObject("NOM_DOC") == null ? "" : rst.getString("NOM_DOC"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC, "" + rst.getObject("ne_numDoc") == null ? "" : rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_SUB, "" + rst.getObject("nd_sub") == null ? "" : rst.getString("nd_sub"));
                        vecReg.add(INT_TBL_DAT_VAL_IVA, "" + rst.getObject("nd_valIva") == null ? "" : rst.getString("nd_valIva"));
                        vecReg.add(INT_TBL_DAT_TOT, "" + rst.getObject("nd_tot") == null ? "" : rst.getString("nd_tot"));
                        //vecReg.add(INT_TBL_DAT_TIP_IDE, "" + rst.getObject("TIPOID") == null ? "" : rst.getString("TIPOID"));
                        
                        strTipIde = rst.getObject("TIPOID") == null ? "" : rst.getString("TIPOID");
                        vecReg.add(INT_TBL_DAT_TIP_IDE, strTipIde);

                        strRuc = rst.getObject("RUC") == null ? "" : (rst.getString("RUC").equals("null") ? "" : rst.getString("RUC"));
                        intRuc = strRuc.length();
                        
                        if (!strTipIde.equals("R"))
                        {
                           int j;
                           j = 0; //DBN_prueba
                        }
                        
                        while (intRuc < 13 && strTipIde.equals("R"))
                        {
                            strRuc = "0" + strRuc;
                            intRuc = strRuc.length();
                        }

                        strNumRet = ((rst.getObject("tx_numSerRetCli") == null ? "" : rst.getString("tx_numSerRetCli")) + (rst.getObject("tx_numRetCli") == null ? "" : (rst.getString("tx_numRetCli"))));
                        intNumRet = strNumRet.length();

                        //strComVta=(rst.getObject("tx_numserfac")==null?"":rst.getString("tx_numserfac") ) + "" + (rst.getObject("ne_numDoc")==null?"":rst.getString("ne_numDoc"));
                        strComVta = rst.getObject("tx_numserfac") == null ? "" : rst.getString("tx_numserfac");
                        strNumDoc = rst.getObject("ne_numDoc") == null ? "" : rst.getString("ne_numDoc");

                        if (!strNumDoc.equals("")) {
                            lngNumDoc = Long.parseLong(strNumDoc);
                            strNumDoc = "-" + String.format("%09d", lngNumDoc); // => "000000001"
                        }

                        strComVta += strNumDoc;

//                        intComVta=strComVta.length();
//                        while(intComVta<13){
//                            strComVta="0" + strComVta;
//                            intComVta=strComVta.length();
//                        }
                        strNumAutSri = rst.getObject("tx_numAutRetCli") == null ? "" : rst.getString("tx_numAutRetCli");
                        intNumAutSri = strNumAutSri.length();
                        while (intNumAutSri < 10) {
                            strNumAutSri = "0" + strNumAutSri;
                            intNumAutSri = strNumAutSri.length();
                        }

                        strBasImp = rst.getObject("nd_basImp") == null ? "" : "" + objUti.redondear(rst.getString("nd_basImp"), objParSis.getDecimalesMostrar());
                        intBasImp = strBasImp.length();
                        while (intBasImp < 15) {
                            strBasImp = "0" + strBasImp;
                            intBasImp = strBasImp.length();
                        }
                        strCodRet = rst.getObject("tx_codsri") == null ? "" : rst.getString("tx_codsri");
                        strValRet = rst.getObject("nd_abo") == null ? "" : "" + objUti.redondear(rst.getString("nd_abo"), objParSis.getDecimalesMostrar());
                        intValRet = strValRet.length();
                        while (intValRet < 15) {
                            strValRet = "0" + strValRet;
                            intValRet = strValRet.length();
                        }

                        vecReg.add(INT_TBL_DAT_RUC, "" + strRuc);
                        vecReg.add(INT_TBL_DAT_NUM_RET, "" + strNumRet);
                        vecReg.add(INT_TBL_DAT_FEC_REG, "" + rst.getObject("FEC_REG") == null ? "" : objUti.formatearFecha(rst.getString("FEC_REG"), "yyyy-MM-dd", "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_FEC_EMI, "" + rst.getObject("FEC_EMI") == null ? "" : objUti.formatearFecha(rst.getString("FEC_EMI"), "yyyy-MM-dd", "dd/MM/yyyy"));
                        vecReg.add(INT_TBL_DAT_TIP_COM, "" + rst.getObject("TIPCOM") == null ? "" : rst.getString("TIPCOM"));
                        vecReg.add(INT_TBL_DAT_CMP_VTA, "" + strComVta);
                        vecReg.add(INT_TBL_DAT_NUM_AUT, "" + strNumAutSri);
                        vecReg.add(INT_TBL_DAT_FEC_CAD, "" + rst.getObject("FECCAD") == null ? "" : rst.getString("FECCAD"));
                        vecReg.add(INT_TBL_DAT_BAS_IMP, "" + strBasImp);
                        vecReg.add(INT_TBL_DAT_COD_RET, "" + strCodRet);
                        vecReg.add(INT_TBL_DAT_VAL_RET, "" + strValRet);
                        vecDat.add(vecReg);
                    } else {
                        break;
                    }
                    lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                }

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                pgrSis.setMaximum(objTblMod.getRowCountTrue());
                
                for (i = 0; i < objTblMod.getRowCountTrue(); i++) {
                    pgrSis.setValue(i);
                    intCodEmp = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                    intCodLoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                    intCodTipDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                    intCodDoc = Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                    strDesCorTipDocLoc = objTblMod.getValueAt(i, INT_TBL_DAT_NOM_DOC).toString();
                    strCodRetSri = objTblMod.getValueAt(i, INT_TBL_DAT_COD_RET).toString();

                    if (strDesCorTipDocLoc.startsWith("FACVEN")) {
                        strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RET) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_VAL_RET).toString().trim();

                        if (strAuxLoc.equals("")) {
                            bdeValRetDetTbl = new BigDecimal("0.00");
                        } else {
                            bdeValRetDetTbl = new BigDecimal(strAuxLoc);
                            bdeValRetDetTbl = objUti.redondearBigDecimal(bdeValRetDetTbl, objParSis.getDecimalesMostrar());
                        }

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
                        strSQL += "   and a1.nd_abo = " + bdeValRetDetTbl;
                        rst = stm.executeQuery(strSQL);

                        if (rst.next()) {
                            intCodEmp_RelRet = rst.getInt("co_emp");
                            intCodLoc_RelRet = rst.getInt("co_loc");
                            intCodTipDoc_RelRet = rst.getInt("co_tipdoc");
                            intCodDoc_RelRet = rst.getInt("co_doc");
                        }

                        bdePorRet = new BigDecimal("0");

                        if (strCodRetSri.equals("312")) {  //312 = Retencion en la Fuente 1%
                            bdePorRet = new BigDecimal("1");
                        } else if (strCodRetSri.equals("344")) {  //344 = Retencion en la Fuente 2%
                            bdePorRet = new BigDecimal("2");
                        } else if (strCodRetSri.equals("320")) {  //320 = Retencion en la Fuente 8%
                            bdePorRet = new BigDecimal("8");
                        } else if (strCodRetSri.equals("9")) {  //9 = Retencion de IVA 10%
                            bdePorRet = new BigDecimal("10");
                        } else if (strCodRetSri.equals("10")) {  //10 = Retencion de IVA 20%
                            bdePorRet = new BigDecimal("20");
                        } else if (strCodRetSri.equals("16")) {  //16 = Retencion de IVA 50%
                            bdePorRet = new BigDecimal("50");
                        } else if (strCodRetSri.equals("1") || strCodRetSri.equals("721")) {  //1, 721 = Retencion de IVA 30%
                            bdePorRet = new BigDecimal("30");
                        } else if (strCodRetSri.equals("2") || strCodRetSri.equals("723")) {  //2, 723 = Retencion de IVA 70%
                            bdePorRet = new BigDecimal("70");
                        } else if (strCodRetSri.equals("3") || strCodRetSri.equals("725")) {  //3, 725 = Retencion de IVA 100%
                            bdePorRet = new BigDecimal("100");
                        }

                        if (!bdePorRet.equals(new BigDecimal("0"))) {
                            bdeValRetDetPag = new BigDecimal("0");

                            //Se va a buscar el valor de retencion en tbm_detpag.nd_abo
                            strSQL = "select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipret, a2.nd_porret, a1.nd_abo ";
                            strSQL += "from tbm_detpag as a1 ";
                            strSQL += "inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
                            strSQL += "where  a2.st_reg in ('A','C') ";
                            strSQL += "       and a2.co_emp = " + intCodEmp_RelRet;
                            strSQL += "       and a2.co_loc = " + intCodLoc_RelRet;
                            strSQL += "       and a2.co_tipdoc = " + intCodTipDoc_RelRet;
                            strSQL += "       and a2.co_doc = " + intCodDoc_RelRet;
                            strSQL += "       and a2.nd_porret = " + bdePorRet;
                            strSQL += "       and a1.nd_abo = " + bdeValRetDetTbl;

//                       //Tony cambio para que tomen en cuenta los ajustes de ctvs  
//                         strSQL =  "select a5.nd_abo+case when (select COUNT(*) ";
//                         strSQL += "  from tbm_detpag as a1 ";
//                         strSQL += " inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
//                         strSQL += " where  a2.st_reg in ('A','C') ";
//                         strSQL += "       and a2.co_emp = " + intCodEmp_RelRet;
//                         strSQL += "       and a2.co_loc = " + intCodLoc_RelRet;
//                         strSQL += "       and a2.co_tipdoc = " + intCodTipDoc_RelRet;
//                         strSQL += "       and a2.co_doc = " + intCodDoc_RelRet;
//                         strSQL += "       and a2.nd_porret = " + bdePorRet;
//                         strSQL += "       and a1.co_tipdoc = 80)>0 then (select a1.nd_abo ";
//                         strSQL += " from tbm_detpag as a1 ";
//                         strSQL += " inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
//                         strSQL += " where  a2.st_reg in ('A','C') ";
//                         strSQL += "       and a2.co_emp = " + intCodEmp_RelRet;
//                         strSQL += "       and a2.co_loc = " + intCodLoc_RelRet;
//                         strSQL += "       and a2.co_tipdoc = " + intCodTipDoc_RelRet;
//                         strSQL += "       and a2.co_doc = " + intCodDoc_RelRet;
//                         strSQL += "       and a2.nd_porret = " + bdePorRet;
//                         strSQL += "       and a1.co_tipdoc = 80) else 0 end as nd_abo  ";
//                         strSQL += " from (select a2.co_emp, a2.co_loc, a2.co_tipdoc, a2.co_doc, a2.co_tipret, a2.nd_porret, a1.nd_abo,a1.co_tipdoc";
//                         strSQL += " from tbm_detpag as a1 ";
//                         strSQL += " inner join tbm_pagmovinv as a2 on a2.co_emp = a1.co_emp and a2.co_loc = a1.co_locpag and a2.co_tipdoc = a1.co_tipdocpag and a2.co_doc = a1.co_docpag and a2.co_reg = a1.co_regpag ";
//                         strSQL += " where  a2.st_reg in ('A','C') ";
//                         strSQL += "       and a2.co_emp = " + intCodEmp_RelRet;
//                         strSQL += "       and a2.co_loc = " + intCodLoc_RelRet;
//                         strSQL += "       and a2.co_tipdoc = " + intCodTipDoc_RelRet;
//                         strSQL += "       and a2.co_doc = " + intCodDoc_RelRet;
//                         strSQL += "       and a2.nd_porret = " + bdePorRet;
//                         strSQL += "       and a1.nd_abo = " + bdeValRetDetTbl + ") as a5";
                            rst = stm.executeQuery(strSQL);

                            if (rst.next()) {
                                bdeValRetDetPag = rst.getBigDecimal("nd_abo");
                                bdeValRetDetPag = objUti.redondearBigDecimal(bdeValRetDetPag, objParSis.getDecimalesMostrar());
                            }

                            //Se va a obtener la Base Imponible del JTable
                            strAuxLoc = objTblMod.getValueAt(i, INT_TBL_DAT_BAS_IMP) == null ? "" : objTblMod.getValueAt(i, INT_TBL_DAT_BAS_IMP).toString().trim();

                            if (strAuxLoc.equals("")) {
                                bdeBasImp = new BigDecimal("0.00");
                            } else {
                                bdeBasImp = new BigDecimal(strAuxLoc);
                                bdeBasImp = objUti.redondearBigDecimal(bdeBasImp, objParSis.getDecimalesMostrar());
                            }

                            //Se va a calcular la Base Imponible basado en el valor de retencion de tbm_detpag.nd_abo
                            bdeBasImpCal = (bdeValRetDetPag.multiply(new BigDecimal("100"))).divide(bdePorRet, RoundingMode.HALF_UP);

                            //Se va a calcular la Retencion basada en la Base Imponible calculada del valor de tbm_detpag.nd_abo
                            bdeValRetCal1 = (bdeBasImpCal.multiply(bdePorRet)).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
                            bdeValRetCal1 = objUti.redondearBigDecimal(bdeValRetCal1, objParSis.getDecimalesMostrar());

                            //Se va a calcular la Retencion basada en la Base Imponible del JTable
                            bdeValRetCal2 = (bdeBasImp.multiply(bdePorRet)).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
                            bdeValRetCal2 = objUti.redondearBigDecimal(bdeValRetCal2, objParSis.getDecimalesMostrar());

                            bdeAux = (bdeValRetCal1.subtract(bdeValRetCal2)).abs();
                            bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());

                            if (bdeAux.compareTo(new BigDecimal("0.02")) <= 0) {  //Si la diferencia de la Retencion (obtenido de la Base Imponible calculada del valor de tbm_detpag.nd_abo) 
                                //comparado con el valor de la Retencion (obtenido de la Base Imponible del JTable) es <= a 0.02 entonces se 
                                //considera que la Base Imponible del JTable esta bien.
                                bdeValRetCal1 = (bdeBasImp.multiply(bdePorRet)).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
                                bdeValRetCal1 = objUti.redondearBigDecimal(bdeValRetCal1, objParSis.getDecimalesMostrar());

                                bdeAux = (bdeValRetDetPag.subtract(bdeValRetCal1)).abs();
                                bdeAux = objUti.redondearBigDecimal(bdeAux, objParSis.getDecimalesMostrar());

                                if (bdeAux.compareTo(new BigDecimal("0.02")) <= 0) {  //Si la diferencia del Valor de retencion calculado comparado con el valor de tbm_detpag.nd_abo es <= a 0.02
                                    //entonces se debera tomar en cuenta el valor de tbm_detpag.
                                    bdeAux = bdeValRetDetPag;
                                } else {  //Si la diferencia del Valor de retencion calculado comparado con el valor de tbm_detpag.nd_abo es mayor a 0.02
                                    //entonces se debera tomar en cuenta el valor calculado.
                                    bdeAux = bdeValRetCal1;
                                }
                            } else {  //Si la diferencia de la Retencion (obtenido de la Base Imponible calculada del valor de tbm_detpag.nd_abo) 
                                //comparado con el valor de la Retencion (obtenido de la Base Imponible del JTable) es > a 0.02
                                //entonces la Base Imponible del JTable esta mal. Por tanto, hay que tomar como correctos los valores calculados de 
                                //la Base Imponible y Retencion.
                                bdeValRetCal1 = (bdeBasImpCal.multiply(bdePorRet)).divide(new BigDecimal("100"), RoundingMode.HALF_UP);
                                bdeValRetCal1 = objUti.redondearBigDecimal(bdeValRetCal1, objParSis.getDecimalesMostrar());
                                bdeAux = bdeValRetCal1;
                                //Tony agregue esto cuando aplican una retencion para servicio y bien combinados sin dividirlos CASO En particular
                                int res, res2;
                                res = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SUB).toString()).subtract(bdeBasImpCal).compareTo(new BigDecimal(0.49));
                                res2 = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SUB).toString()).subtract(bdeBasImpCal).compareTo(new BigDecimal(0.00));
                                if (res == -1 && res2 == 1) {
                                    bdeBasImpCal = new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_SUB).toString());
                                }
                                objTblMod.setValueAt(bdeBasImpCal, i, INT_TBL_DAT_BAS_IMP);
                            } //if (bdeAux.compareTo(new BigDecimal("0.02")) <= 0)

                            objTblMod.setValueAt(bdeAux, i, INT_TBL_DAT_VAL_RET);
                        } //if (! bdePorRet.equals(new BigDecimal("0")))
                    } //if (strDesCorTipDocLoc.startsWith("FACVEN"))
                } //for (i = 0; i < objTblMod.getRowCountTrue(); i++)

                rst.close();
                stm.close();
                rst = null;
                stm = null;
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
                    objTblTot.calcularTotales();
                }
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
                case INT_TBL_DAT_NUM_RET:
                    strMsg = "Número de retención";
                    break;
                case INT_TBL_DAT_FEC_REG:
                    strMsg = "Fecha de la emisión de la retención";
                    break;
                case INT_TBL_DAT_FEC_EMI:
                    strMsg = "Fecha de emisión del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_TIP_COM:
                    strMsg = "Tipo de comprobante";
                    break;
                case INT_TBL_DAT_CMP_VTA:
                    strMsg = "Comprobante de venta";
                    break;
                case INT_TBL_DAT_NUM_AUT:
                    strMsg = "Número de autorización del documento";
                    break;
                case INT_TBL_DAT_FEC_CAD:
                    strMsg = "Fecha de caducidad del documento al que se emite la retención";
                    break;
                case INT_TBL_DAT_BAS_IMP:
                    strMsg = "Base imponible grabada";
                    break;
                case INT_TBL_DAT_VAL_RET:
                    strMsg = "Valor de retención";
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

    private boolean getTipDocCab() {
        boolean blnRes = true;
        strTipDocUsr = "";
        try {
            if (con != null) {
                stm = con.createStatement();
                if (objParSis.getCodigoUsuario() == 1) {
                    strSQL = "";
                    strSQL += "SELECT co_tipdoc FROM tbr_tipdocprg";
                    strSQL += " WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL += " AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL += " AND co_mnu=" + objParSis.getCodigoMenu() + "";
                } else {
                    strSQL = "";
                    strSQL += "SELECT a1.co_tipdoc FROM tbr_tipdocprg AS a1";
                    strSQL += " INNER JOIN tbr_tipdocusr AS a2";
                    strSQL += " ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                    strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL += " AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                }
                rst = stm.executeQuery(strSQL);
                for (int i = 0; rst.next(); i++) {
                    if (i == 0) {
                        strTipDocUsr = "" + (rst.getObject("co_tipdoc") == null ? "" : rst.getString("co_tipdoc"));
                    } else {
                        strTipDocUsr += "," + (rst.getObject("co_tipdoc") == null ? "" : rst.getString("co_tipdoc"));
                    }
                }
                stm.close();
                stm = null;
                rst.close();
                rst = null;

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

    private boolean isCamVal() {
        boolean blnRes = true;
        int intCon_S_O = 0;
        int intCon_I = 0;
        String strAplRet = "";
        int intRetChk = 0;
        try {
            for (int i = 0; i < objTblModRet.getRowCountTrue(); i++) {
                if (objTblModRet.isChecked(i, INT_TBL_DAT_RET_CHK)) {
                    intRetChk++;
                    strAplRet = objTblModRet.getValueAt(i, INT_TBL_DAT_RET_APL) == null ? "" : objTblModRet.getValueAt(i, INT_TBL_DAT_RET_APL).toString();
                    if ((strAplRet.equals("S")) || (strAplRet.equals("O"))) {
                        if (intCon_S_O == 0) {
                            intCon_S_O++;
                        }
                    } else if (strAplRet.equals("I")) {
                        if (intCon_I == 0) {
                            intCon_I++;
                        }
                    }
                }
            }
            if (intRetChk == 0) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar al menos un tipo de retención.<BR>Seleccione algun tipo de retención y vuelva a intentarlo</HTML>");
                blnRes = false;
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

}
