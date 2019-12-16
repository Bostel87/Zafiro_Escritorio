/*
 * ZafMae41.java
 *
 * Created on July 10, 2007, 11:14 AM
 */

package Contabilidad.ZafCon51;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.SpinnerNumberModel;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import javax.swing.JScrollBar;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
/**
 *
 * @author  ilino
 */
public class ZafCon51 extends javax.swing.JInternalFrame {
    final int intJspValMin=2006;
    final int intJspValMax=9999;
    final int intJspValIni=2007;
    final int intJspValInc=1;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModTot;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    javax.swing.JInternalFrame jfrThis;
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    ZafColNumerada objColNumTblDat, objColNumTblTot;
    Vector vecCab, vecReg, vecDat, vecAux;
    private ZafTblCelRenLbl objTblCelRenLblDat, objTblCelRenLblTot;
    private ZafTblEdi objTblEdiCta;
    private ZafVenCon vcoTipGrp;
    private String strAux, strSQL, strCodGrp, strDesLarGrp;
    private java.util.Date datFecAux;
    private boolean blnHayCam;
    private ZafMouMotAda objMouMotAda;

    //PARA LA TABLA DE CUENTA Y TOTALES DE CUENTAS
    final int INT_TBL_DAT_CTA_LIN=0;
    final int INT_TBL_DAT_CTA_COD_CTA=1;
    final int INT_TBL_DAT_CTA_NUM_CTA=2;
    final int INT_TBL_DAT_CTA_NOM_CTA=3;
    final int INT_TBL_DAT_CTA_MES_ENE_REA=4;
    final int INT_TBL_DAT_CTA_MES_ENE_PRS=5;
    final int INT_TBL_DAT_CTA_MES_ENE_DIF_VAL=6;
    final int INT_TBL_DAT_CTA_MES_ENE_DIF_PRC=7;
    final int INT_TBL_DAT_CTA_MES_FEB_REA=8;
    final int INT_TBL_DAT_CTA_MES_FEB_PRS=9;
    final int INT_TBL_DAT_CTA_MES_FEB_DIF_VAL=10;
    final int INT_TBL_DAT_CTA_MES_FEB_DIF_PRC=11;
    final int INT_TBL_DAT_CTA_MES_MAR_REA=12;
    final int INT_TBL_DAT_CTA_MES_MAR_PRS=13;
    final int INT_TBL_DAT_CTA_MES_MAR_DIF_VAL=14;
    final int INT_TBL_DAT_CTA_MES_MAR_DIF_PRC=15;
    final int INT_TBL_DAT_CTA_MES_ABR_REA=16;
    final int INT_TBL_DAT_CTA_MES_ABR_PRS=17;
    final int INT_TBL_DAT_CTA_MES_ABR_DIF_VAL=18;
    final int INT_TBL_DAT_CTA_MES_ABR_DIF_PRC=19;
    final int INT_TBL_DAT_CTA_MES_MAY_REA=20;
    final int INT_TBL_DAT_CTA_MES_MAY_PRS=21;
    final int INT_TBL_DAT_CTA_MES_MAY_DIF_VAL=22;
    final int INT_TBL_DAT_CTA_MES_MAY_DIF_PRC=23;
    final int INT_TBL_DAT_CTA_MES_JUN_REA=24;
    final int INT_TBL_DAT_CTA_MES_JUN_PRS=25;
    final int INT_TBL_DAT_CTA_MES_JUN_DIF_VAL=26;
    final int INT_TBL_DAT_CTA_MES_JUN_DIF_PRC=27;
    final int INT_TBL_DAT_CTA_MES_JUL_REA=28;
    final int INT_TBL_DAT_CTA_MES_JUL_PRS=29;
    final int INT_TBL_DAT_CTA_MES_JUL_DIF_VAL=30;
    final int INT_TBL_DAT_CTA_MES_JUL_DIF_PRC=31;
    final int INT_TBL_DAT_CTA_MES_AGO_REA=32;
    final int INT_TBL_DAT_CTA_MES_AGO_PRS=33;
    final int INT_TBL_DAT_CTA_MES_AGO_DIF_VAL=34;
    final int INT_TBL_DAT_CTA_MES_AGO_DIF_PRC=35;
    final int INT_TBL_DAT_CTA_MES_SEP_REA=36;
    final int INT_TBL_DAT_CTA_MES_SEP_PRS=37;
    final int INT_TBL_DAT_CTA_MES_SEP_DIF_VAL=38;
    final int INT_TBL_DAT_CTA_MES_SEP_DIF_PRC=39;
    final int INT_TBL_DAT_CTA_MES_OCT_REA=40;
    final int INT_TBL_DAT_CTA_MES_OCT_PRS=41;
    final int INT_TBL_DAT_CTA_MES_OCT_DIF_VAL=42;
    final int INT_TBL_DAT_CTA_MES_OCT_DIF_PRC=43;
    final int INT_TBL_DAT_CTA_MES_NOV_REA=44;
    final int INT_TBL_DAT_CTA_MES_NOV_PRS=45;
    final int INT_TBL_DAT_CTA_MES_NOV_DIF_VAL=46;
    final int INT_TBL_DAT_CTA_MES_NOV_DIF_PRC=47;
    final int INT_TBL_DAT_CTA_MES_DIC_REA=48;
    final int INT_TBL_DAT_CTA_MES_DIC_PRS=49;
    final int INT_TBL_DAT_CTA_MES_DIC_DIF_VAL=50;
    final int INT_TBL_DAT_CTA_MES_DIC_DIF_PRC=51;
    final int INT_TBL_DAT_CTA_TOT_REA=52;
    final int INT_TBL_DAT_CTA_TOT_PRS=53;
    
    private JScrollBar barTblCtaDat, barTblCtaTot;
    private ZafThreadGUI objThrGUI;
    private JScrollBar barDatCta, barDatTot;
        
    //PARA CARGAR LA INFORMACION COMPLETA AL INICIAR EL FORMULARIO PERO PARA GRUPOS DE CUENTAS
    private ArrayList arlRegIniFrm, arlDatIniFrm;
    private boolean blnCon;                     //true: Continua la ejecución del hilo.
    private ZafTblTot objTblTot;                        //JTable de totales.
    
    /** Creates new form ZafMae41 */
    public ZafCon51(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            this.objParSis=obj;
            jfrThis=this;
            objParSis=(ZafParSis)obj.clone();
            jspAni.setModel(new SpinnerNumberModel(intJspValIni, intJspValMin, intJspValMax, intJspValInc));

            if (!configurarFrm())
                exitForm();
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
    private void initComponents() {//GEN-BEGIN:initComponents
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        panCabTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCabGrp = new javax.swing.JPanel();
        lblTitTipDoc = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        txtDesLarGrp = new javax.swing.JTextField();
        butGrp = new javax.swing.JButton();
        lblAni = new javax.swing.JLabel();
        jspAni = new javax.swing.JSpinner();
        panTblDat = new javax.swing.JPanel();
        panTblCta = new javax.swing.JPanel();
        panTblCtaDat = new javax.swing.JPanel();
        spnTblCtaDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panTblCtaTot = new javax.swing.JPanel();
        spnTblCtaTot = new javax.swing.JScrollPane();
        tblTotCta = new javax.swing.JTable();
        panTblDetCta = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCan = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

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

        jPanel1.setLayout(new java.awt.BorderLayout());

        panCab.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(10, 50));
        panCabTit.setLayout(new java.awt.BorderLayout());

        panCabTit.setPreferredSize(new java.awt.Dimension(10, 20));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panCabTit.add(lblTit, java.awt.BorderLayout.CENTER);

        panCab.add(panCabTit, java.awt.BorderLayout.NORTH);

        panCabGrp.setLayout(null);

        lblTitTipDoc.setText("Grupo:");
        panCabGrp.add(lblTitTipDoc);
        lblTitTipDoc.setBounds(4, 10, 60, 15);

        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });

        panCabGrp.add(txtCodGrp);
        txtCodGrp.setBounds(64, 6, 57, 20);

        txtDesLarGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarGrpActionPerformed(evt);
            }
        });
        txtDesLarGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarGrpFocusLost(evt);
            }
        });

        panCabGrp.add(txtDesLarGrp);
        txtDesLarGrp.setBounds(122, 6, 310, 20);

        butGrp.setText("...");
        butGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGrpActionPerformed(evt);
            }
        });

        panCabGrp.add(butGrp);
        butGrp.setBounds(432, 5, 30, 23);

        lblAni.setText("A\u00f1o:");
        panCabGrp.add(lblAni);
        lblAni.setBounds(520, 8, 40, 15);

        panCabGrp.add(jspAni);
        jspAni.setBounds(570, 6, 80, 20);

        panCab.add(panCabGrp, java.awt.BorderLayout.CENTER);

        jPanel1.add(panCab, java.awt.BorderLayout.NORTH);

        panTblDat.setLayout(new java.awt.BorderLayout());

        panTblCta.setLayout(new java.awt.BorderLayout());

        panTblCta.setBorder(new javax.swing.border.EtchedBorder());
        panTblCta.setPreferredSize(new java.awt.Dimension(0, 140));
        panTblCtaDat.setLayout(new java.awt.BorderLayout());

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
        spnTblCtaDat.setViewportView(tblDat);

        panTblCtaDat.add(spnTblCtaDat, java.awt.BorderLayout.CENTER);

        panTblCta.add(panTblCtaDat, java.awt.BorderLayout.CENTER);

        panTblCtaTot.setLayout(new java.awt.BorderLayout());

        panTblCtaTot.setPreferredSize(new java.awt.Dimension(10, 35));
        tblTotCta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTblCtaTot.setViewportView(tblTotCta);

        panTblCtaTot.add(spnTblCtaTot, java.awt.BorderLayout.CENTER);

        panTblCta.add(panTblCtaTot, java.awt.BorderLayout.SOUTH);

        panTblDat.add(panTblCta, java.awt.BorderLayout.CENTER);

        panTblDetCta.setLayout(new java.awt.BorderLayout());

        panTblDetCta.setBorder(new javax.swing.border.EtchedBorder());
        panTblDetCta.setPreferredSize(new java.awt.Dimension(0, 96));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setPreferredSize(new java.awt.Dimension(10, 26));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setViewportView(txaObs1);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Observaci\u00f3n:   ");
        jPanel2.add(jLabel1, java.awt.BorderLayout.WEST);

        jPanel4.add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel3.setPreferredSize(new java.awt.Dimension(10, 50));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel5.setPreferredSize(new java.awt.Dimension(169, 30));
        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });

        jPanel5.add(butCon);

        butCan.setMnemonic('a');
        butCan.setText("Cancelar");
        jPanel5.add(butCan);

        jPanel3.add(jPanel5, java.awt.BorderLayout.NORTH);

        panBarEst.setLayout(new java.awt.BorderLayout());

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setLayout(new java.awt.BorderLayout(2, 2));

        jPanel6.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        pgrSis.setBorder(new javax.swing.border.EtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel4.add(jPanel3, java.awt.BorderLayout.SOUTH);

        panTblDetCta.add(jPanel4, java.awt.BorderLayout.CENTER);

        panTblDat.add(panTblDetCta, java.awt.BorderLayout.SOUTH);

        jPanel1.add(panTblDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("tab1", jPanel1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if(isCamVal()){
            if (butCon.getText().equals("Consultar")){
                //objTblTotales.isActivo(false);
                blnCon=true;
                if (objThrGUI==null){
                    objThrGUI=new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
            else
                blnCon=false;            
        }

    }//GEN-LAST:event_butConActionPerformed

    private void butGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGrpActionPerformed
        // TODO add your handling code here:
        mostrarVenConGrp(0);
    }//GEN-LAST:event_butGrpActionPerformed

    private void txtDesLarGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarGrpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarGrp.getText().equalsIgnoreCase(strDesLarGrp))
        {
            if (txtDesLarGrp.getText().equals(""))
            {
                txtCodGrp.setText("");
            }
            else
            {
                mostrarVenConGrp(2);
            }
        }
        else
            txtDesLarGrp.setText(strDesLarGrp);
    }//GEN-LAST:event_txtDesLarGrpFocusLost

    private void txtDesLarGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarGrpFocusGained
        // TODO add your handling code here:
        strDesLarGrp=txtDesLarGrp.getText();
        txtDesLarGrp.selectAll();
    }//GEN-LAST:event_txtDesLarGrpFocusGained

    private void txtDesLarGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarGrpActionPerformed
        // TODO add your handling code here:
        txtDesLarGrp.transferFocus();
    }//GEN-LAST:event_txtDesLarGrpActionPerformed

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp))
        {
            if (txtCodGrp.getText().equals(""))
                txtDesLarGrp.setText("");
            else{
                mostrarVenConGrp(1);
            }
        }
        else{
            txtCodGrp.setText(strCodGrp);
        }
    }//GEN-LAST:event_txtCodGrpFocusLost

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
    }//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // TODO add your handling code here:
        txtCodGrp.transferFocus();
    }//GEN-LAST:event_txtCodGrpActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
            dispose();
        }        
    }//GEN-LAST:event_exitForm
    

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }     
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGrp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jspAni;
    private javax.swing.JLabel lblAni;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblTitTipDoc;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panCabGrp;
    private javax.swing.JPanel panCabTit;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panTblCta;
    private javax.swing.JPanel panTblCtaDat;
    private javax.swing.JPanel panTblCtaTot;
    private javax.swing.JPanel panTblDat;
    private javax.swing.JPanel panTblDetCta;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnTblCtaDat;
    private javax.swing.JScrollPane spnTblCtaTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTotCta;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtDesLarGrp;
    // End of variables declaration//GEN-END:variables
 
    
    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            //Inicializar objetos.
            objUti=new ZafUtil();
            this.setTitle(objParSis.getNombreMenu() + " v0.1.1");
            lblTit.setText(objParSis.getNombreMenu());
            txtCodGrp.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarGrp.setBackground(objParSis.getColorCamposObligatorios());
                       
            if ( ! configuraTablaDatosCtas() )
                blnRes=false;
            configurarVenConGrp();
            arlDatIniFrm=new ArrayList();
            arlDatIniFrm.clear();
            vecDat=new Vector();
            vecDat.clear();
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    
    private boolean configuraTablaDatosCtas(){
        boolean blnRes=true;
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos           
            vecCab=new Vector(54);  //Almacena las cabeceras
            vecCab.clear();  
            vecCab.add(INT_TBL_DAT_CTA_LIN,"");
            vecCab.add(INT_TBL_DAT_CTA_COD_CTA," CÓD.CTA.");
            vecCab.add(INT_TBL_DAT_CTA_NUM_CTA,"NÚM.CTA");
            vecCab.add(INT_TBL_DAT_CTA_NOM_CTA,"NOMBRE");
            vecCab.add(INT_TBL_DAT_CTA_MES_ENE_REA,"ENERO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_ENE_PRS,"ENERO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL,"ENERO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC,"ENERO-DIF.PORC.");
            vecCab.add(INT_TBL_DAT_CTA_MES_FEB_REA,"FEBRERO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_FEB_PRS,"FEBRERO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL,"FEBRERO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC,"FEBRERO-DIF.PORC.");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAR_REA,"MARZO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAR_PRS,"MARZO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL,"MARZO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC,"MARZO-DIF.PORC.");
            vecCab.add(INT_TBL_DAT_CTA_MES_ABR_REA,"ABRIL-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_ABR_PRS,"ABRIL-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL,"ABRIL-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC,"ABRIL-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAY_REA,"MAYO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAY_PRS,"MAYO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL,"MAYO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC,"MAYO-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUN_REA,"JUNIO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUN_PRS,"JUNIO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL,"JUNIO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC,"JUNIO-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUL_REA,"JULIO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUL_PRS,"JULIO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL,"JULIO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC,"JULIO-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_AGO_REA,"AGOSTO-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_AGO_PRS,"AGOSTO-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL,"AGOSTO-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC,"AGOSTO-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_SEP_REA,"SEPTIEMBRE-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_SEP_PRS,"SEPTIEMBRE-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL,"SEPTIEMBRE-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC,"SEPTIEMBRE-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_OCT_REA,"OCTUBRE-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_OCT_PRS,"OCTUBRE-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL,"OCTUBRE-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC,"OCTUBRE-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_NOV_REA,"NOVIEMBRE-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_NOV_PRS,"NOVIEMBRE-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL,"NOVIEMBRE-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC,"NOVIEMBRE-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_MES_DIC_REA,"DICIEMBRE-REAL");
            vecCab.add(INT_TBL_DAT_CTA_MES_DIC_PRS,"DICIEMBRE-PRESUPUESTADO");
            vecCab.add(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL,"DICIEMBRE-DIF.VALOR");
            vecCab.add(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC,"DICIEMBRE-DIF.PORC");
            vecCab.add(INT_TBL_DAT_CTA_TOT_REA,"TOTAL-REAL");
            vecCab.add(INT_TBL_DAT_CTA_TOT_PRS,"TOTAL-PRESUPUESTADO");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CTA_MES_ENE_REA, INT_TBL_DAT_CTA_MES_ENE_PRS, INT_TBL_DAT_CTA_MES_FEB_REA,INT_TBL_DAT_CTA_MES_FEB_PRS,INT_TBL_DAT_CTA_MES_MAR_REA,INT_TBL_DAT_CTA_MES_MAR_PRS,INT_TBL_DAT_CTA_MES_ABR_REA,INT_TBL_DAT_CTA_MES_ABR_PRS,INT_TBL_DAT_CTA_MES_MAY_REA,INT_TBL_DAT_CTA_MES_MAY_PRS,INT_TBL_DAT_CTA_MES_JUN_REA,INT_TBL_DAT_CTA_MES_JUN_PRS,INT_TBL_DAT_CTA_MES_JUL_REA,INT_TBL_DAT_CTA_MES_JUL_PRS,INT_TBL_DAT_CTA_MES_AGO_REA,INT_TBL_DAT_CTA_MES_AGO_PRS,INT_TBL_DAT_CTA_MES_SEP_REA,INT_TBL_DAT_CTA_MES_SEP_PRS,INT_TBL_DAT_CTA_MES_OCT_REA,INT_TBL_DAT_CTA_MES_OCT_PRS,INT_TBL_DAT_CTA_MES_NOV_REA,INT_TBL_DAT_CTA_MES_NOV_PRS,INT_TBL_DAT_CTA_MES_DIC_REA,INT_TBL_DAT_CTA_MES_DIC_PRS,INT_TBL_DAT_CTA_TOT_REA,INT_TBL_DAT_CTA_TOT_PRS};
            objTblTot=new ZafTblTot(spnTblCtaDat, spnTblCtaTot, tblDat, tblTotCta, intCol);            
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNumTblDat=new ZafColNumerada(tblDat,INT_TBL_DAT_CTA_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CTA_LIN).setPreferredWidth(10);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_REA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_PRS).setPreferredWidth(100);
            
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMaxWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMinWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(0);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setResizable(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
           
            //Configurar JTable: Editor de la tabla.
            objTblEdiCta=new ZafTblEdi(tblDat);            
                        
            objTblCelRenLblDat=new ZafTblCelRenLbl();
            objTblCelRenLblDat.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblDat.setTipoFormato(objTblCelRenLblDat.INT_FOR_NUM);
            objTblCelRenLblDat.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC).setCellRenderer(objTblCelRenLblDat);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_PRS).setCellRenderer(objTblCelRenLblDat);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_REA).setCellRenderer(objTblCelRenLblDat);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_PRS).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_REA).setCellRenderer(objTblCelRenLblDat);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_PRS).setCellRenderer(objTblCelRenLblDat);
            objTblCelRenLblDat=null;

//PARA LA TABLA DE TOTALES                                 
            objTblModTot=new ZafTblMod();
            objTblModTot.setHeader(vecCab);                        
            tblTotCta.setModel(objTblModTot);
            //Configurar JTable: Establecer tipo de selección.
            tblTotCta.setRowSelectionAllowed(true);
            tblTotCta.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            objColNumTblTot=new ZafColNumerada(tblTotCta,INT_TBL_DAT_CTA_LIN);
            objTblPopMnu=new ZafTblPopMnu(tblTotCta);
            tblTotCta.setAutoResizeMode(tblDat.getAutoResizeMode());
            tcmAux=tblTotCta.getColumnModel();        
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblTotCta.setAutoResizeMode(tblDat.getAutoResizeMode());
            tcmAux=tblTotCta.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_CTA_LIN).setPreferredWidth(10);
//            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NUM_CTA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_NOM_CTA).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_REA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_PRS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_REA).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_PRS).setPreferredWidth(100);
            
            //Configurar JTable: Ocultar columnas del sistema.
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setPreferredWidth(0);
            tcmAux.getColumn(INT_TBL_DAT_CTA_COD_CTA).setResizable(false);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblTotCta);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblTot=new ZafTblCelRenLbl();
            objTblCelRenLblTot.setHorizontalAlignment(javax.swing.JLabel.RIGHT);            
            objTblCelRenLblTot.setTipoFormato(objTblCelRenLblTot.INT_FOR_NUM);
            objTblCelRenLblTot.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC).setCellRenderer(objTblCelRenLblTot);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_PRS).setCellRenderer(objTblCelRenLblTot);            
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_REA).setCellRenderer(objTblCelRenLblTot);                        
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_PRS).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_REA).setCellRenderer(objTblCelRenLblTot);
            tcmAux.getColumn(INT_TBL_DAT_CTA_TOT_PRS).setCellRenderer(objTblCelRenLblTot);
            objTblCelRenLblTot=null;
            
            //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
            for (int j=0; j<tblDat.getColumnCount(); j++){
                tcmAux.getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;  
//////FIN TOTALES///
            objTblModTot.insertRow();
            objTblMod.insertRow();
        return blnRes;
    }
    
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }    
    
    private boolean configurarVenConGrp(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_grp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre Grupo");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("80");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_grp, a1.tx_nom";
            strSQL+=" FROM tbm_grpCta AS a1";
            strSQL+=" WHERE ";
            strSQL+=" a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" AND st_reg not in ('I', 'E')";
            strSQL+=" ORDER BY a1.co_grp";
            vcoTipGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoTipGrp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipGrp.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
        
    private boolean mostrarVenConGrp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipGrp.setCampoBusqueda(1);
                    vcoTipGrp.show();
                    if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE){
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Descripción corta".
                    if (vcoTipGrp.buscar("a1.co_grp", txtCodGrp.getText()))
                    {
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                    }
                    else
                    {
                        vcoTipGrp.setCampoBusqueda(0);
                        vcoTipGrp.setCriterio1(11);
                        vcoTipGrp.cargarDatos();
                        vcoTipGrp.show();
                        if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE)
                        {
                            txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                            txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                        }
                        else
                        {
                            txtCodGrp.setText(strCodGrp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTipGrp.buscar("a1.tx_nom", txtDesLarGrp.getText()))
                    {
                        txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                        txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                    }
                    else
                    {
                        vcoTipGrp.setCampoBusqueda(1);
                        vcoTipGrp.setCriterio1(11);
                        vcoTipGrp.cargarDatos();
                        vcoTipGrp.show();
                        if (vcoTipGrp.getSelectedButton()==vcoTipGrp.INT_BUT_ACE)
                        {
                            txtCodGrp.setText(vcoTipGrp.getValueAt(1));
                            txtDesLarGrp.setText(vcoTipGrp.getValueAt(2));
                        }
                        else
                        {
                            txtDesLarGrp.setText(strDesLarGrp);
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
    
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
        public void mouseMoved(java.awt.event.MouseEvent evt){
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol){
                case INT_TBL_DAT_CTA_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CTA_COD_CTA:
                    strMsg="Código que se le da a la cuenta en Zafiro";
                    break;
                case INT_TBL_DAT_CTA_NUM_CTA:
                    strMsg="Número de la Cuenta Contable";
                    break;
                case INT_TBL_DAT_CTA_NOM_CTA:
                    strMsg="Nombre de la Cuenta Contable";
                    break;
                case INT_TBL_DAT_CTA_MES_ENE_REA:
                    strMsg="Saldo al mes de Enero Real";
                    break;
                case INT_TBL_DAT_CTA_MES_ENE_PRS:
                    strMsg="Saldo al mes de Enero Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_ENE_DIF_VAL:
                    strMsg="Diferencia en valores al mes Enero";
                    break;
                case INT_TBL_DAT_CTA_MES_ENE_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Enero";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB_REA:
                    strMsg="Saldo al mes de Febrero Real";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB_PRS:
                    strMsg="Saldo al mes de Febrero Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB_DIF_VAL:
                    strMsg="Diferencia en valores al mes Febrero";
                    break;
                case INT_TBL_DAT_CTA_MES_FEB_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Febrero";
                    break;
                case INT_TBL_DAT_CTA_MES_MAR_REA:
                    strMsg="Saldo al mes de Marzo Real";
                    break;
                case INT_TBL_DAT_CTA_MES_MAR_PRS:
                    strMsg="Saldo al mes de Marzo Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_MAR_DIF_VAL:
                    strMsg="Diferencia en valores al mes Marzo";
                    break;
                case INT_TBL_DAT_CTA_MES_MAR_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Marzo";
                    break;
                case INT_TBL_DAT_CTA_MES_ABR_REA:
                    strMsg="Saldo al mes de Abril Real";
                    break;
                case INT_TBL_DAT_CTA_MES_ABR_PRS:
                    strMsg="Saldo al mes de Abril Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_ABR_DIF_VAL:
                    strMsg="Diferencia en valores al mes Abril";
                    break;
                case INT_TBL_DAT_CTA_MES_ABR_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Abril";
                    break;
                case INT_TBL_DAT_CTA_MES_MAY_REA:
                    strMsg="Saldo al mes de Mayo Real";
                    break;
                case INT_TBL_DAT_CTA_MES_MAY_PRS:
                    strMsg="Saldo al mes de Mayo Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_MAY_DIF_VAL:
                    strMsg="Diferencia en valores al mes Mayo";
                    break;
                case INT_TBL_DAT_CTA_MES_MAY_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Mayo";
                    break;
                case INT_TBL_DAT_CTA_MES_JUN_REA:
                    strMsg="Saldo al mes de Junio Real";
                    break;
                case INT_TBL_DAT_CTA_MES_JUN_PRS:
                    strMsg="Saldo al mes de Junio Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_JUN_DIF_VAL:
                    strMsg="Diferencia en valores al mes Junio";
                    break;
                case INT_TBL_DAT_CTA_MES_JUN_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Junio";
                    break;
                case INT_TBL_DAT_CTA_MES_JUL_REA:
                    strMsg="Saldo al mes de Julio Real";
                    break;
                case INT_TBL_DAT_CTA_MES_JUL_PRS:
                    strMsg="Saldo al mes de Julio Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_JUL_DIF_VAL:
                    strMsg="Diferencia en valores al mes Julio";
                    break;
                case INT_TBL_DAT_CTA_MES_JUL_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Julio";
                    break;
                case INT_TBL_DAT_CTA_MES_AGO_REA:
                    strMsg="Saldo al mes de Agosto Real";
                    break;
                case INT_TBL_DAT_CTA_MES_AGO_PRS:
                    strMsg="Saldo al mes de Agosto Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_AGO_DIF_VAL:
                    strMsg="Diferencia en valores al mes Agosto";
                    break;
                case INT_TBL_DAT_CTA_MES_AGO_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Agosto";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP_REA:
                    strMsg="Saldo al mes de Septiembre Real";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP_PRS:
                    strMsg="Saldo al mes de Septiembre Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP_DIF_VAL:
                    strMsg="Diferencia en valores al mes Septiembre";
                    break;
                case INT_TBL_DAT_CTA_MES_SEP_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Septiembre";
                    break;
                case INT_TBL_DAT_CTA_MES_OCT_REA:
                    strMsg="Saldo al mes de Octubre Real";
                    break;
                case INT_TBL_DAT_CTA_MES_OCT_PRS:
                    strMsg="Saldo al mes de Octubre Presupuestado";
                    break;
                case INT_TBL_DAT_CTA_MES_OCT_DIF_VAL:
                    strMsg="Diferencia en valores al mes Octubre";
                    break;
                case INT_TBL_DAT_CTA_MES_OCT_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Octubre";
                    break;
                case INT_TBL_DAT_CTA_MES_NOV_REA:
                    strMsg="Saldo al mes de Noviembre Real";
                    break;
                case INT_TBL_DAT_CTA_MES_NOV_PRS:
                    strMsg="Saldo al mes de Noviembre Presupuesto";
                    break;
                case INT_TBL_DAT_CTA_MES_NOV_DIF_VAL:
                    strMsg="Diferencia en valores al mes Noviembre";
                    break;
                case INT_TBL_DAT_CTA_MES_NOV_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Noviembre";
                    break;
                case INT_TBL_DAT_CTA_MES_DIC_REA:
                    strMsg="Saldo al mes de Diciembre Real";
                    break;
                case INT_TBL_DAT_CTA_MES_DIC_PRS:
                    strMsg="Saldo al mes de Diciembre Presupuesto";
                    break;
                case INT_TBL_DAT_CTA_MES_DIC_DIF_VAL:
                    strMsg="Diferencia en valores al mes Diciembre";
                    break;
                case INT_TBL_DAT_CTA_MES_DIC_DIF_PRC:
                    strMsg="Diferencia en porcentaje al mes Diciembre";
                    break;
                case INT_TBL_DAT_CTA_TOT_REA:
                    strMsg="Total Real";
                    break;
                case INT_TBL_DAT_CTA_TOT_PRS:
                    strMsg="Total Presupuestado";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }     
    
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTotCta.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
    }     

    private int mostrarMsgCon(String strMsg){
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
 
    
    private class ZafThreadGUI extends Thread{
        public void run(){
            if (!cargarDetReg()){
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }
        
    private boolean cargarDetReg(){
        String strNiv;
        int intCodEmp, intNumTotReg, i;
        boolean blnRes=true;
        double dblNumVta=0.00;
        double dblValNet=0.00;
        String strCodPer="";
        strAux="";
        double dblTmpRea=0.00, dblTmpPre=0.00;
        txaObs1.setText("");
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                if( ! (txtCodGrp.getText().toString().equals("")))
                    strAux=" AND a3.co_grp=" + txtCodGrp.getText()  + "";
                
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM";
                strSQL+=" (";
                strSQL+=" SELECT a2.co_emp, a2.co_cta, a2.nd_val, a4.tx_codCta, a4.tx_desLar, a5.nd_salCta";
                strSQL+=" FROM tbm_detEstFinPre AS a2 INNER JOIN tbr_ctaGrpCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                strSQL+=" INNER JOIN tbm_plaCta as a4 ON(a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_salCta as a5 ON(a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                strSQL+=" " + strAux + "";
                strSQL+=" AND a2.ne_mes=1";
                strSQL+=" AND a5.co_per=" + jspAni.getValue() + "01";
                strSQL+=" ) AS b1";
                for(int f=2; f<=12;f++){
                    strSQL+=" LEFT OUTER JOIN";
                    strSQL+=" (";
                    strSQL+=" SELECT a2.co_emp, a2.co_cta, a2.nd_val, a4.tx_codCta, a4.tx_desLar, a5.nd_salCta";
                    strSQL+=" FROM tbm_detEstFinPre AS a2 INNER JOIN tbr_ctaGrpCta AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                    strSQL+=" INNER JOIN tbm_plaCta as a4 ON(a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta)";
                    strSQL+=" LEFT OUTER JOIN tbm_salCta as a5 ON(a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta)";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                    strSQL+="" +  strAux + "";
                    strSQL+=" AND a2.ne_mes=" + f + "";
                    strCodPer=f<=9?"0"+f:""+f;
                    strSQL+=" AND a5.co_per=" + jspAni.getValue() + "" + strCodPer + "";
                    strSQL+=" ) AS b" + f + " ON (b1.co_emp=b" + f + ".co_emp AND b1.co_cta=b" + f + ".co_cta)";
                }
                System.out.println("COUNT cargarDetReg: "+strSQL);
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                //PARA FACTURAS Y NOTAS DE CREDITO
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b1.co_cta, b1.tx_codcta, b1.tx_desLar, b1.co_grp, b1.tx_nom, b1.tx_obs1,";
                strSQL+=" b1.nd_salCta AS salEneRea, b1.nd_val AS salEnePre, b2.nd_salCta AS salFebRea, b2.nd_val AS salFebPre,";
                strSQL+=" b3.nd_salCta AS salMarRea, b3.nd_val AS salMarPre, b4.nd_salCta AS salAbrRea, b4.nd_val AS salAbrPre,";
                strSQL+=" b5.nd_salCta AS salMayRea, b5.nd_val AS salMayPre, b6.nd_salCta AS salJunRea, b6.nd_val AS salJunPre,";
                strSQL+=" b7.nd_salCta AS salJulRea, b7.nd_val AS salJulPre, b8.nd_salCta AS salAgoRea, b8.nd_val AS salAgoPre,";
                strSQL+=" b9.nd_salCta AS salSepRea, b9.nd_val AS salSepPre, b10.nd_salCta AS salOctRea, b10.nd_val AS salOctPre,";
                strSQL+=" b11.nd_salCta AS salNovRea, b11.nd_val AS salNovPre, b12.nd_salCta AS salDicRea, b12.nd_val AS salDicPre";
                strSQL+=" FROM";
                strSQL+=" (";
                strSQL+=" SELECT a2.co_emp, a2.co_cta, a2.nd_val, a4.tx_codCta, a4.tx_desLar, a5.nd_salCta, a3.co_grp, a6.tx_nom, a7.tx_obs1";
                strSQL+=" FROM tbm_cabEstFinPre as a7 INNER JOIN tbm_detEstFinPre AS a2 ON a7.co_emp=a2.co_emp AND a7.ne_ani=a2.ne_ani";
                strSQL+=" INNER JOIN (tbr_ctaGrpCta AS a3 INNER JOIN tbm_grpCta AS a6 ON a3.co_emp=a6.co_emp AND a3.co_grp=a6.co_grp) ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                strSQL+=" INNER JOIN tbm_plaCta as a4 ON(a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta)";
                strSQL+=" LEFT OUTER JOIN tbm_salCta as a5 ON(a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta AND a5.co_per=" + jspAni.getValue() + "01)";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                strSQL+=" " + strAux + "";
                strSQL+=" AND a2.ne_mes=1";
                strSQL+=" AND a5.co_per=" + jspAni.getValue() + "01";
                strSQL+=" ) AS b1";
                for(int f=2; f<=12;f++){
                    strSQL+=" LEFT OUTER JOIN";
                    strSQL+=" (";
                    strSQL+=" SELECT a2.co_emp, a2.co_cta, a2.nd_val, a4.tx_codCta, a4.tx_desLar, a5.nd_salCta, a3.co_grp, a6.tx_nom, a7.tx_obs1";
                    strSQL+=" FROM tbm_cabEstFinPre as a7 INNER JOIN tbm_detEstFinPre AS a2 ON a7.co_emp=a2.co_emp AND a7.ne_ani=a2.ne_ani";
                    strSQL+=" INNER JOIN (tbr_ctaGrpCta AS a3 INNER JOIN tbm_grpCta AS a6 ON a3.co_emp=a6.co_emp AND a3.co_grp=a6.co_grp) ON (a2.co_emp=a3.co_emp AND a2.co_cta=a3.co_cta)";
                    strSQL+=" INNER JOIN tbm_plaCta as a4 ON(a3.co_emp=a4.co_emp AND a3.co_cta=a4.co_cta)";
                    strCodPer=f<=9?"0"+f:""+f;
                    strSQL+=" LEFT OUTER JOIN tbm_salCta as a5 ON(a4.co_emp=a5.co_emp AND a4.co_cta=a5.co_cta AND a5.co_per=" + jspAni.getValue() + "" + strCodPer + ")";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.ne_ani=" + jspAni.getValue() + "";
                    strSQL+="" +  strAux + "";
                    strSQL+=" AND a2.ne_mes=" + f + "";
                    
                    strSQL+=" AND a5.co_per=" + jspAni.getValue() + "" + strCodPer + "";
                    strSQL+=" ) AS b" + f + " ON (b1.co_emp=b" + f + ".co_emp AND b1.co_cta=b" + f + ".co_cta)";
                }
                System.out.println("SQL cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);

                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                

                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_CTA_LIN,"");
                        vecReg.add(INT_TBL_DAT_CTA_COD_CTA,"" + rst.getString("co_cta"));
                        vecReg.add(INT_TBL_DAT_CTA_NUM_CTA,"" + rst.getString("tx_codCta"));
                        vecReg.add(INT_TBL_DAT_CTA_NOM_CTA,"" + rst.getString("tx_desLar"));
                        //ENERO
                        dblTmpRea=rst.getDouble("salEneRea");
                        dblTmpPre=rst.getDouble("salEnePre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_ENE_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_ENE_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_ENE_DIF_VAL,"" + (dblTmpPre - dblTmpRea) );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_ENE_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        
                        //FEBRERO
                        dblTmpRea=rst.getDouble("salFebRea");
                        dblTmpPre=rst.getDouble("salFebPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_FEB_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_FEB_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_FEB_DIF_VAL,"" + ( dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_FEB_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //MARZO
                        dblTmpRea=rst.getDouble("salMarRea");
                        dblTmpPre=rst.getDouble("salMarPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAR_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAR_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAR_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_MAR_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //ABRIL
                        dblTmpRea=rst.getDouble("salAbrRea");
                        dblTmpPre=rst.getDouble("salAbrPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_ABR_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_ABR_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_ABR_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_ABR_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //MAYO
                        dblTmpRea=rst.getDouble("salMayRea");
                        dblTmpPre=rst.getDouble("salMayPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAY_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAY_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_MAY_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_MAY_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //JUNIO
                        dblTmpRea=rst.getDouble("salJunRea");
                        dblTmpPre=rst.getDouble("salJunPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUN_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUN_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUN_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_JUN_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //JULIO
                        dblTmpRea=rst.getDouble("salJulRea");
                        dblTmpPre=rst.getDouble("salJulPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUL_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUL_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_JUL_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_JUL_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //AGOSTO
                        dblTmpRea=rst.getDouble("salAgoRea");
                        dblTmpPre=rst.getDouble("salAgoPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_AGO_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_AGO_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_AGO_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_AGO_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //SEPTIEMBRE
                        dblTmpRea=rst.getDouble("salSepRea");
                        dblTmpPre=rst.getDouble("salSepPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_SEP_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_SEP_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_SEP_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_SEP_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //OCTUBRE
                        dblTmpRea=rst.getDouble("salOctRea");
                        dblTmpPre=rst.getDouble("salOctPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_OCT_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_OCT_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_OCT_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_OCT_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //NOVIEMBRE
                        dblTmpRea=rst.getDouble("salNovRea");
                        dblTmpPre=rst.getDouble("salNovPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_NOV_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_NOV_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_NOV_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_NOV_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        //DICIEMBRE
                        dblTmpRea=rst.getDouble("salDicRea");
                        dblTmpPre=rst.getDouble("salDicPre");
                        vecReg.add(INT_TBL_DAT_CTA_MES_DIC_REA,"" + dblTmpRea);
                        vecReg.add(INT_TBL_DAT_CTA_MES_DIC_PRS,"" + dblTmpPre);
                        vecReg.add(INT_TBL_DAT_CTA_MES_DIC_DIF_VAL,"" + (dblTmpPre - dblTmpRea)  );
                        if(dblTmpRea>0)
                            vecReg.add(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC,"" + Math.abs(( ((dblTmpRea - dblTmpPre))*100)/(dblTmpRea))   );
                        else
                            vecReg.add(INT_TBL_DAT_CTA_MES_DIC_DIF_PRC,"" + 0.00   );
                        dblTmpRea=0.00;
                        dblTmpPre=0.00;
                        vecReg.add(INT_TBL_DAT_CTA_TOT_REA,"");
                        vecReg.add(INT_TBL_DAT_CTA_TOT_PRS,"");
                        vecDat.add(vecReg);
                        
                        
                        i++;
                        pgrSis.setValue(i);                        
//                        lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");
                        
                        txtCodGrp.setText("" + rst.getString("co_grp"));
                        txtDesLarGrp.setText("" + rst.getString("tx_nom"));
                        txaObs1.setText("" + rst.getString("tx_obs1")==null?"":rst.getString("tx_obs1"));
                    }
                    else
                        break;
                }
                System.out.println("VERIFICAR: " + vecDat.toString());
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                if(calculaTotalesReales())
                    calculaTotalesPresupuestados();
                objTblTot.calcularTotales();
                if (intNumTotReg==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros.");
                else
                    lblMsgSis.setText("Se encontraron " + intNumTotReg + " registros pero sólo se procesaron " + tblDat.getRowCount() + ".");
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");
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
    
    private boolean calculaTotalesReales(){
        boolean blnRes=true;
        double dblSumValRea=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
            for(int j=INT_TBL_DAT_CTA_MES_ENE_REA;j<=INT_TBL_DAT_CTA_MES_DIC_REA;j++){
                dblSumValRea+=Double.parseDouble(""+objTblMod.getValueAt(i, j));
                j+=3;
            }
            objTblMod.setValueAt(""+dblSumValRea, i, INT_TBL_DAT_CTA_TOT_REA);
            dblSumValRea=0.00;
        }
        return blnRes;
    }
    
    
    private boolean calculaTotalesPresupuestados(){
        boolean blnRes=true;
        double dblSumValPrs=0.00;
        for(int i=0;i<objTblMod.getRowCountTrue();i++){
            for(int j=INT_TBL_DAT_CTA_MES_ENE_PRS;j<=INT_TBL_DAT_CTA_MES_DIC_PRS;j++){
                dblSumValPrs+=Double.parseDouble(""+objTblMod.getValueAt(i, j));
                j+=3;
            }
            objTblMod.setValueAt(""+dblSumValPrs, i, INT_TBL_DAT_CTA_TOT_PRS);
            dblSumValPrs=0.00;
        }
        return blnRes;
    }
    

        
    private boolean isCamVal(){
        if (txtCodGrp.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Grupo</FONT> es obligatorio.<BR>Escriba o seleccione un grupo y vuelva a intentarlo.</HTML>");
            txtDesLarGrp.requestFocus();
            return false;
        }
        return true;
    }    
    
    
    
}
