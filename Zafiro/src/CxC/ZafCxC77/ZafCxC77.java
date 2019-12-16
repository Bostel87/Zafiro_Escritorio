/*
 * ZafCxC77.java
 *
 * Created on September 20, 2007, 1:52 PM
 */

package CxC.ZafCxC77;

import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;

/**
 *
 * @author  ilino
 */
public class ZafCxC77 extends javax.swing.JInternalFrame {
private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
//    private ZafSelectDate objSelDat;
    private ZafSelFec objSelFec;
    private ZafMouMotAda objMouMotAda;
    private String strCodCli, strDesLarCli;
    private boolean blnCon;
    private ZafTblEdi objTblEdi;
    private ZafTblBus objTblBus;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafTblOrd objTblOrd;
    private java.util.Date datFecAux;
    private String strIdeCli, strDirCli;
    private String strAux;
    private ZafVenCon vcoCli, vcoEmp, vcoLoc;
    private ZafThreadGUI objThrGUI;
    
    //TABLA DE CABECERA
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_FEC_DOC=3;
    final int INT_TBL_DAT_DAT_CHQ_NUM_CHQ=4;
    final int INT_TBL_DAT_DAT_CHQ_VAL_CHQ=5;
    final int INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ=6;

    final int INT_TBL_DAT_SOL_CAN_CHK=7;
    final int INT_TBL_DAT_SOL_DIA=8;
    final int INT_TBL_DAT_SOL_FEC=9;
    final int INT_TBL_DAT_SOL_OBS=10;
    final int INT_TBL_DAT_SOL_OBS_BUT=11;
    final int INT_TBL_DAT_SOL_COD_USU=12;
    final int INT_TBL_DAT_SOL_ALI_USU=13;

    final int INT_TBL_DAT_AUT_CHK=14;
    final int INT_TBL_DAT_DEN_CHK=15;
    final int INT_TBL_DAT_AUT_DIA=16;
    final int INT_TBL_DAT_AUT_FEC=17;
    final int INT_TBL_DAT_AUT_OBS=18;
    final int INT_TBL_DAT_AUT_OBS_BUT=19;
    final int INT_TBL_DAT_AUT_COD_USU=20;
    final int INT_TBL_DAT_AUT_ALI_USU=21;
    
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab;

    private ZafTblCelRenChk objTblCelRenChkCan;
    private ZafTblCelEdiChk objTblCelEdiChkCan;

    private ZafTblCelRenChk objTblCelRenChkAut;
    private ZafTblCelEdiChk objTblCelEdiChkAut;

    private ZafTblCelRenChk objTblCelRenChkDen;
    private ZafTblCelEdiChk objTblCelEdiChkDen;

    private ZafTblCelRenBut objTblCelRenButObsSol;
    private ZafTblCelEdiButDlg objTblCelEdiButObsSol;

    private ZafTblCelRenBut objTblCelRenButObsAut;
    private ZafTblCelEdiButDlg objTblCelEdiButObsAut;

    private ZafCxC77_01 objCxC77_01, objCxC77_02;


    private String strCodEmp, strCodLoc, strNomEmp, strNomLoc;
    
    /** Creates new form ZafCxC77 */
    public ZafCxC77(ZafParSis obj) {
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            if (!configurarFrm())
                exitForm(null);
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }


    public ZafCxC77(ZafParSis obj, String rucCliente){
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            objParSis.setCodigoMenu(324);//el 324 es el codigo de menu de Historial de transacciones de clientes...CxC.ZafCxC19.ZafCxC19
            initComponents();
            if (!configurarFrm())
                exitForm(null);
            butCon.setVisible(true);
            butCon.setEnabled(true);
            butCer.setVisible(true);
            butCer.setEnabled(true);
            txtRucCli.setText(""+rucCliente);
            mostrarVenConCli(3);
            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        catch (CloneNotSupportedException e){
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }



    public ZafCxC77(ZafParSis obj, Integer codigoEmpresa, Integer codigoCliente){
        try{
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
            objParSis.setCodigoMenu(324);//el 324 es el codigo de menu de Historial de transacciones de clientes...CxC.ZafCxC19.ZafCxC19
            objParSis.setCodigoEmpresa(Integer.parseInt(""+codigoEmpresa));
            initComponents();
            if (!configurarFrm())
                exitForm(null);
            butCon.setVisible(true);
            butCon.setEnabled(true);
            butCer.setVisible(true);
            butCer.setEnabled(true);
            txtCodCli.setText(""+codigoCliente);
            mostrarVenConCli(1);
            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
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
        panInfFilCli = new javax.swing.JPanel();
        lblCli = new javax.swing.JLabel();
        txtCodCli = new javax.swing.JTextField();
        txtNomCli = new javax.swing.JTextField();
        butCli = new javax.swing.JButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        txtRucCli = new javax.swing.JTextField();
        chkMosColSolPos = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        chkTieSolPosPnd = new javax.swing.JCheckBox();
        chkTieSolPosCan = new javax.swing.JCheckBox();
        chkTieSolPosAut = new javax.swing.JCheckBox();
        chkTieSolPosDen = new javax.swing.JCheckBox();
        panRep = new javax.swing.JPanel();
        panFilGrlCli = new javax.swing.JPanel();
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

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panInfFilCli.setPreferredSize(new java.awt.Dimension(10, 172));
        panInfFilCli.setLayout(null);

        lblCli.setText("Cliente:");
        lblCli.setToolTipText("Cliente");
        panInfFilCli.add(lblCli);
        lblCli.setBounds(10, 6, 80, 20);

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
        panInfFilCli.add(txtCodCli);
        txtCodCli.setBounds(90, 6, 56, 20);

        txtNomCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomCliActionPerformed(evt);
            }
        });
        txtNomCli.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomCliFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomCliFocusLost(evt);
            }
        });
        panInfFilCli.add(txtNomCli);
        txtNomCli.setBounds(147, 6, 264, 20);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panInfFilCli.add(butCli);
        butCli.setBounds(411, 6, 20, 20);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Cliente");
        panInfFilCli.add(lblEmp);
        lblEmp.setBounds(10, 27, 80, 20);

        txtCodEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodEmpActionPerformed(evt);
            }
        });
        txtCodEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodEmpFocusLost(evt);
            }
        });
        panInfFilCli.add(txtCodEmp);
        txtCodEmp.setBounds(90, 27, 56, 20);

        txtNomEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomEmpActionPerformed(evt);
            }
        });
        txtNomEmp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomEmpFocusLost(evt);
            }
        });
        panInfFilCli.add(txtNomEmp);
        txtNomEmp.setBounds(147, 27, 264, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panInfFilCli.add(butEmp);
        butEmp.setBounds(411, 27, 20, 20);

        lblLoc.setText("Local:");
        lblLoc.setToolTipText("Cliente");
        panInfFilCli.add(lblLoc);
        lblLoc.setBounds(10, 48, 80, 20);

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
        panInfFilCli.add(txtCodLoc);
        txtCodLoc.setBounds(90, 48, 56, 20);

        txtNomLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomLocActionPerformed(evt);
            }
        });
        txtNomLoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomLocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomLocFocusLost(evt);
            }
        });
        panInfFilCli.add(txtNomLoc);
        txtNomLoc.setBounds(147, 48, 264, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panInfFilCli.add(butLoc);
        butLoc.setBounds(411, 48, 20, 20);
        panInfFilCli.add(txtRucCli);
        txtRucCli.setBounds(460, 8, 120, 20);

        chkMosColSolPos.setText("Mostrar las columnas correspondientes a la solicitud de postergación");
        chkMosColSolPos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosColSolPosActionPerformed(evt);
            }
        });
        panInfFilCli.add(chkMosColSolPos);
        chkMosColSolPos.setBounds(4, 270, 450, 23);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Mostrar los cheques que:"));
        jPanel1.setLayout(null);

        chkTieSolPosPnd.setText("Tienen solicitud de postergación pendiente");
        jPanel1.add(chkTieSolPosPnd);
        chkTieSolPosPnd.setBounds(6, 20, 340, 14);

        chkTieSolPosCan.setText("Tienen solicitud de postergación cancelada");
        jPanel1.add(chkTieSolPosCan);
        chkTieSolPosCan.setBounds(6, 36, 340, 14);

        chkTieSolPosAut.setSelected(true);
        chkTieSolPosAut.setText("Tienen solicitud de postergación autorizada");
        jPanel1.add(chkTieSolPosAut);
        chkTieSolPosAut.setBounds(6, 52, 320, 14);

        chkTieSolPosDen.setText("Tienen solicitud de postergación denegada");
        jPanel1.add(chkTieSolPosDen);
        chkTieSolPosDen.setBounds(6, 68, 330, 14);

        panInfFilCli.add(jPanel1);
        jPanel1.setBounds(20, 160, 360, 100);

        tabFrm.addTab("Filtro", panInfFilCli);

        panRep.setLayout(new java.awt.BorderLayout());

        panFilGrlCli.setPreferredSize(new java.awt.Dimension(452, 402));
        panFilGrlCli.setLayout(new java.awt.BorderLayout());

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

        panFilGrlCli.add(spnDat, java.awt.BorderLayout.CENTER);

        panRep.add(panFilGrlCli, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

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
        // TODO add your handling code here:
        //configurarFrm();
//        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        mostrarVenConCli(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtNomCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomCli.getText().equalsIgnoreCase(strDesLarCli))
        {
            if (txtNomCli.getText().equals(""))
            {
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConCli(2);
            }
        }
        else
            txtNomCli.setText(strDesLarCli);
    }//GEN-LAST:event_txtNomCliFocusLost

    private void txtNomCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomCliFocusGained
        // TODO add your handling code here:
        strDesLarCli=txtNomCli.getText();
        txtNomCli.selectAll();
    }//GEN-LAST:event_txtNomCliFocusGained

    private void txtNomCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomCliActionPerformed
        // TODO add your handling code here:
        txtNomCli.transferFocus();
    }//GEN-LAST:event_txtNomCliActionPerformed

    private void txtCodCliFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodCli.getText().equalsIgnoreCase(strCodCli)){
            if (txtCodCli.getText().equals("")){
                txtCodCli.setText("");
                txtNomCli.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConCli(1);
        }
        else
            txtCodCli.setText(strCodCli);
    }//GEN-LAST:event_txtCodCliFocusLost

    private void txtCodCliFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodCliFocusGained
        // TODO add your handling code here:
        strCodCli=txtCodCli.getText();
        txtCodCli.selectAll();
    }//GEN-LAST:event_txtCodCliFocusGained

    private void txtCodCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodCliActionPerformed
        // TODO add your handling code here:
        txtCodCli.transferFocus();
    }//GEN-LAST:event_txtCodCliActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodEmpActionPerformed
        // TODO add your handling code here:
        txtCodEmp.transferFocus();
    }//GEN-LAST:event_txtCodEmpActionPerformed

    private void txtCodEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusGained
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        txtCodEmp.selectAll();
    }//GEN-LAST:event_txtCodEmpFocusGained

    private void txtCodEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodEmpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodEmp.getText().equals("")){
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodEmp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodEmpFocusLost

    private void txtNomEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomEmpActionPerformed
        // TODO add your handling code here:
        txtNomEmp.transferFocus();
    }//GEN-LAST:event_txtNomEmpActionPerformed

    private void txtNomEmpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusGained
        // TODO add your handling code here:
        strNomEmp=txtNomEmp.getText();
        txtNomEmp.selectAll();
    }//GEN-LAST:event_txtNomEmpFocusGained

    private void txtNomEmpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomEmpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomEmp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomEmp.getText().equals(""))
            {
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomEmp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomEmpFocusLost

    private void butEmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butEmpActionPerformed
        // TODO add your handling code here:
        strCodEmp=txtCodEmp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butEmpActionPerformed

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
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLoc.getText().equalsIgnoreCase(strCodLoc)){
            if (txtCodLoc.getText().equals("")){
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConLoc(1);
        }
        else
            txtCodLoc.setText(strCodLoc);
    }//GEN-LAST:event_txtCodLocFocusLost

    private void txtNomLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomLocActionPerformed
        // TODO add your handling code here:
        txtNomLoc.transferFocus();
    }//GEN-LAST:event_txtNomLocActionPerformed

    private void txtNomLocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusGained
        // TODO add your handling code here:
        strNomLoc=txtNomLoc.getText();
        txtNomLoc.selectAll();
    }//GEN-LAST:event_txtNomLocFocusGained

    private void txtNomLocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomLocFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomLoc.getText().equalsIgnoreCase(strNomLoc))
        {
            if (txtNomLoc.getText().equals(""))
            {
                txtCodLoc.setText("");
                txtNomLoc.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConLoc(2);
            }
        }
        else
            txtNomLoc.setText(strNomLoc);
    }//GEN-LAST:event_txtNomLocFocusLost

    private void butLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butLocActionPerformed
        // TODO add your handling code here:
        strCodLoc=txtCodLoc.getText();
        mostrarVenConLoc(0);
    }//GEN-LAST:event_butLocActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar accián de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar")) {
            blnCon=true;
            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();

            }
        } else {
            blnCon=false;
        }
}//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void chkMosColSolPosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosColSolPosActionPerformed
        // TODO add your handling code here:
        if(!chkMosColSolPos.isSelected()){
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_CAN_CHK, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_DIA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_FEC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_OBS, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_OBS_BUT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_COD_USU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_ALI_USU, tblDat);
        }
        else{
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_CAN_CHK, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_DIA, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_FEC, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_OBS, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_OBS_BUT, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_COD_USU, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_SOL_ALI_USU, tblDat);
        }
    }//GEN-LAST:event_chkMosColSolPosActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(){
        dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.JCheckBox chkMosColSolPos;
    private javax.swing.JCheckBox chkTieSolPosAut;
    private javax.swing.JCheckBox chkTieSolPosCan;
    private javax.swing.JCheckBox chkTieSolPosDen;
    private javax.swing.JCheckBox chkTieSolPosPnd;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFilGrlCli;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panInfFilCli;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodCli;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtNomCli;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    private javax.swing.JTextField txtRucCli;
    // End of variables declaration//GEN-END:variables

    private boolean configurarFrm(){
        boolean blnRes=true;
        try{
            objUti=new ZafUtil();           
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            panInfFilCli.add(objSelFec);
            objSelFec.setBounds(4,80, 472, 72);
            objSelFec.setCheckBoxVisible(true);
            objSelFec.setTitulo("Fecha del documento");
            objSelFec.setCheckBoxChecked(false);
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.1");
            lblTit.setText(strAux);
            configurarVenConCli();
            configurarVenConEmp();
            configurarVenConLoc();
            configurarTablaGrlCli();
                       
            if(objParSis.getCodigoUsuario()!=1){
                butCon.setVisible(false);
                butCon.setEnabled(false);
                butCer.setVisible(false);
                butCer.setEnabled(false);
            }

            lblEmp.setVisible(true);
            txtCodCli.setBackground(objParSis.getColorCamposObligatorios());
            txtNomCli.setBackground(objParSis.getColorCamposObligatorios());
            txtRucCli.setVisible(false);

            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
                lblLoc.setVisible(false);
                txtCodLoc.setVisible(false);
                txtNomLoc.setVisible(false);
                butLoc.setVisible(false);
            }
            else
                 txtCodCli.setEditable(false);


            
            if(objParSis.getCodigoMenu()==324){
                if(objPerUsr.isOpcionEnabled(678)){//consultar
                    butCon.setVisible(true);
                    butCon.setEnabled(true);
                }
                if(objPerUsr.isOpcionEnabled(679)){//cerrar
                    butCer.setVisible(true);
                    butCer.setEnabled(true);
                }
            }









        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    private boolean configurarTablaGrlCli(){
        boolean blnRes=true;           
        try{
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(22);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_DAT_CHQ_NUM_CHQ,"Num.Chq.");//sin iva
            vecCab.add(INT_TBL_DAT_DAT_CHQ_VAL_CHQ,"Val.Chq.");//sin iva
            vecCab.add(INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ,"Fec.Ven.");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_CAN_CHK,"Cancelar");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_DIA,"Días");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_FEC,"Fecha");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_OBS,"Observación");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_OBS_BUT,"");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_COD_USU,"Cód.Usu.");//sin iva
            vecCab.add(INT_TBL_DAT_SOL_ALI_USU,"Usuario");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_CHK,"Autorizar");//sin iva
            vecCab.add(INT_TBL_DAT_DEN_CHK,"Denegar");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_DIA,"Días");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_FEC,"Fecha");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_OBS,"Observación");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_OBS_BUT,"");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_COD_USU,"Cód.Usu.");//sin iva
            vecCab.add(INT_TBL_DAT_AUT_ALI_USU,"Usuario");//sin iva

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);

            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_VAL_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_SOL_CAN_CHK).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SOL_DIA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_SOL_FEC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_SOL_OBS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SOL_OBS_BUT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_SOL_COD_USU).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_SOL_ALI_USU).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DEN_CHK).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_AUT_DIA).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_AUT_FEC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_AUT_OBS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_AUT_OBS_BUT).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USU).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_AUT_ALI_USU).setPreferredWidth(60);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_VAL_CHQ).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Configurar JTable: Ocultar columnas del sistema.
            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
                objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            }

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SOL_COD_USU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_AUT_COD_USU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_FEC_DOC, tblDat);

            //datos del cheque
            ZafTblHeaGrp objTblHeaGrpDatChq=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpDatChq.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpDatChq=null;
            objTblHeaColGrpDatChq=new ZafTblHeaColGrp("Datos del cheque");
            objTblHeaColGrpDatChq.setHeight(16);
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_NUM_CHQ));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_VAL_CHQ));
            objTblHeaColGrpDatChq.add(tcmAux.getColumn(INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ));
            objTblHeaGrpDatChq.addColumnGroup(objTblHeaColGrpDatChq);

            //solicitud
            ZafTblHeaGrp objTblHeaGrpSol=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpSol.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpSol=null;
            objTblHeaColGrpSol=new ZafTblHeaColGrp("Solicitud");
            objTblHeaColGrpSol.setHeight(16);
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_CAN_CHK));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_DIA));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_FEC));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_OBS));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_OBS_BUT));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_COD_USU));
            objTblHeaColGrpSol.add(tcmAux.getColumn(INT_TBL_DAT_SOL_ALI_USU));
            objTblHeaGrpSol.addColumnGroup(objTblHeaColGrpSol);

            //autorizacion
            ZafTblHeaGrp objTblHeaGrpAut=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpAut.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpAut=null;
            objTblHeaColGrpAut=new ZafTblHeaColGrp("Autorización");
            objTblHeaColGrpAut.setHeight(16);
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_CHK));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_DEN_CHK));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_DIA));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_FEC));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_OBS));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_OBS_BUT));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_COD_USU));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBL_DAT_AUT_ALI_USU));
            objTblHeaGrpAut.addColumnGroup(objTblHeaColGrpAut);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAut=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setCellRenderer(objTblCelRenChkAut);
            objTblCelRenChkAut=null;


            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAut=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_AUT_CHK).setCellEditor(objTblCelEdiChkAut);

            objTblCelEdiChkAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkCan=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_SOL_CAN_CHK).setCellRenderer(objTblCelRenChkCan);
            objTblCelRenChkCan=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkCan=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_SOL_CAN_CHK).setCellEditor(objTblCelEdiChkCan);

            objTblCelEdiChkCan.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {

            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDen=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_DEN_CHK).setCellRenderer(objTblCelRenChkDen);
            objTblCelRenChkDen=null;


            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDen=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_DEN_CHK).setCellEditor(objTblCelEdiChkDen);

            objTblCelEdiChkDen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
      
            });



            //para el boton de observqacion
            objTblCelRenButObsSol=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_SOL_OBS_BUT).setCellRenderer(objTblCelRenButObsSol);
            objCxC77_01=new ZafCxC77_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButObsSol= new ZafTblCelEdiButDlg(objCxC77_01);
            tcmAux.getColumn(INT_TBL_DAT_SOL_OBS_BUT).setCellEditor(objTblCelEdiButObsSol);

            objTblCelEdiButObsSol.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strObs2="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strObs2=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SOL_OBS)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_SOL_OBS).toString();
                    objCxC77_01.setContenido(strObs2);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            //para el boton de observqacion
            objTblCelRenButObsAut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_AUT_OBS_BUT).setCellRenderer(objTblCelRenButObsAut);
            objCxC77_02=new ZafCxC77_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButObsAut= new ZafTblCelEdiButDlg(objCxC77_02);
            tcmAux.getColumn(INT_TBL_DAT_AUT_OBS_BUT).setCellEditor(objTblCelEdiButObsAut);

            objTblCelEdiButObsAut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strObs2="";
                String strNueObs="";
                String strEstValAplReg="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strObs2=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_AUT_OBS)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_AUT_OBS).toString();
                    objCxC77_02.setContenido(strObs2);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });


            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_SOL_OBS_BUT);
            vecAux.add("" + INT_TBL_DAT_AUT_OBS_BUT);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;


            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);


            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
  
   
    private boolean configurarVenConCli(){
        boolean blnRes=true;
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
            //Armar la sentencia SQL.

            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT 0 AS co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" GROUP BY a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" ORDER BY a1.tx_nom";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" ORDER BY a1.tx_nom";
                }

            }
            else{
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    strSQL="";
                    strSQL+="SELECT 0 AS co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                    strSQL+=" WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" GROUP BY a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" ORDER BY a2.tx_nom";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a2.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                    strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.st_cli='S' AND st_reg IN('A')";
                    strSQL+=" ORDER BY a2.tx_nom";
                }

            }




            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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




    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_emp");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");

            strSQL="";
            strSQL+="SELECT a1.co_emp, a1.tx_nom";
            strSQL+=" FROM tbm_emp AS a1";
            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp";
            }
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                strSQL+=" AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
            else
                strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";

            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }


            strSQL+=" ORDER BY a1.tx_nom";
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de empresas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConLoc(){
        boolean blnRes=true;
        try{
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_loc");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("414");

            strSQL="";
            strSQL+="SELECT a1.co_loc, a1.tx_nom";
            strSQL+=" FROM tbm_loc AS a1";
            if(objParSis.getCodigoUsuario()!=1){
                strSQL+=" INNER JOIN tbr_locusr AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
            }
            strSQL+=" WHERE a1.st_reg NOT IN('I','E')";
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                strSQL+=" AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
            else
                strSQL+=" AND a1.co_emp IN(" + objParSis.getCodigoEmpresa() + ")";

            strSQL+=" ORDER BY a1.tx_nom";
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
    
     private boolean mostrarVenConCli(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(0);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE){
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtCodCli.setText(strCodCli);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_nom", txtNomCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(2);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtNomCli.setText(strDesLarCli);
                        }
                    }
                    break;

                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoCli.buscar("a1.tx_ide", txtRucCli.getText())){
                        txtCodCli.setText(vcoCli.getValueAt(1));
                        strIdeCli=vcoCli.getValueAt(2);
                        txtRucCli.setText(vcoCli.getValueAt(2));
                        txtNomCli.setText(vcoCli.getValueAt(3));
                        strDirCli=vcoCli.getValueAt(4);
                    }
                    else{
                        vcoCli.setCampoBusqueda(1);
                        vcoCli.setCriterio1(11);
                        vcoCli.cargarDatos();
                        vcoCli.show();
                        if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                        {
                            txtCodCli.setText(vcoCli.getValueAt(1));
                            strIdeCli=vcoCli.getValueAt(2);
                            txtRucCli.setText(vcoCli.getValueAt(2));
                            txtNomCli.setText(vcoCli.getValueAt(3));
                            strDirCli=vcoCli.getValueAt(4);
                        }
                        else{
                            txtRucCli.setText(strIdeCli);
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


     private boolean mostrarVenConEmp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoCli.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtCodEmp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                        }
                        else{
                            txtNomEmp.setText(strNomEmp);
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


     private boolean mostrarVenConLoc(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoLoc.setCampoBusqueda(2);
                    vcoLoc.show();
                    if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoLoc.buscar("a1.co_loc", txtCodLoc.getText())){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else{
                        vcoLoc.setCampoBusqueda(0);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoCli.getSelectedButton()==vcoLoc.INT_BUT_ACE){
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
                            txtCodLoc.setText(strCodLoc);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoLoc.buscar("a1.tx_nom", txtNomLoc.getText())){
                        txtCodLoc.setText(vcoLoc.getValueAt(1));
                        txtNomLoc.setText(vcoLoc.getValueAt(2));
                    }
                    else{
                        vcoLoc.setCampoBusqueda(2);
                        vcoLoc.setCriterio1(11);
                        vcoLoc.cargarDatos();
                        vcoLoc.show();
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE)
                        {
                            txtCodLoc.setText(vcoLoc.getValueAt(1));
                            txtNomLoc.setText(vcoLoc.getValueAt(2));
                        }
                        else{
                            txtNomLoc.setText(strNomLoc);
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


     
    private class ZafThreadGUI extends Thread{
        public void run(){

            if (isCamVal()){
                //Limpiar objetos.
                objTblMod.removeAllRows();
                if (!cargarReg()){
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
    }



    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(cargarDetReg()){
                                    
                }
                con.close();
                con=null;
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



    
    private boolean cargarDetReg(){
        boolean blnRes=true;
        strAux="";
        int i;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
           
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" , a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.nd_monchq";
                strSQL+=" , a3.co_pos, a3.st_solpos, a3.ne_diasolpos, (a2.fe_venchq + a3.ne_diasolpos) AS fe_pos";
                strSQL+=" , a3.tx_obssolpos, a3.fe_ingsolpos, a3.co_usringsolpos, a4.tx_usr AS tx_usrSolPos, a3.tx_comingsolpos";
                strSQL+=", a3.ne_diaautpos, a3.tx_obsautpos, a3.fe_ingautpos, a3.co_usringautpos, a5.co_usr AS co_usrAutPos, a5.tx_usr AS tx_usrAutPos ";
                strSQL+=" , a3.tx_comingautpos";

                strSQL+=" FROM tbm_cabRecDoc AS a1 INNER JOIN (tbm_detRecDoc AS a2";
                strSQL+=" 				INNER JOIN tbr_detRecDocpagMovInv AS a6";
                strSQL+=" 				ON a2.co_emp=a6.co_emp AND a2.co_loc=a6.co_loc AND a2.co_tipDoc=a6.co_tipDoc";
                strSQL+=" 				AND a2.co_doc=a6.co_doc AND a2.co_reg=a6.co_reg";


                strSQL+="				INNER JOIN tbm_pagMovInv AS a7";
                strSQL+=" 				ON a6.co_empRel=a7.co_emp AND a6.co_locRel=a7.co_loc AND a6.co_tipDocRel=a7.co_tipDoc";
                strSQL+=" 				AND a6.co_docRel=a7.co_doc";


                strSQL+=" 				INNER JOIN tbm_cabMovInv AS a9";
                strSQL+=" 				ON a9.co_emp=a7.co_emp AND a9.co_loc=a7.co_loc AND a9.co_tipDoc=a7.co_tipDoc";
                strSQL+=" 				AND a9.co_doc=a7.co_doc";
                strSQL+="                               INNER JOIN tbm_cli AS a8";
                strSQL+=" 				ON a9.co_emp=a8.co_emp AND a9.co_cli=a8.co_cli";
                strSQL+=" )";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" INNER JOIN (   (tbm_posChqRecDoc AS a3 LEFT OUTER JOIN tbm_usr AS a4";
                strSQL+=" 		ON a3.co_usringsolpos=a4.co_usr)";
                strSQL+=" 	    LEFT OUTER JOIN tbm_usr AS a5 ON a3.co_usringautpos=a5.co_usr)";
                strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_doc=a3.co_doc";
                strSQL+=" AND a2.co_reg=a3.co_reg";






                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+=" 			WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                else
                    strSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                    strSQL+="                   AND a8.tx_ide='" + txtRucCli.getText() + "'";
                else
                    strSQL+="                   AND a8.co_cli=" + txtCodCli.getText() + "";

                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    if(txtCodEmp.getText().length()>0)
                        strSQL+=" 			AND a1.co_emp=" + txtCodEmp.getText() + "";
                    if(txtCodLoc.getText().length()>0)
                        strSQL+=" 			AND a1.co_loc=" + txtCodLoc.getText() + "";
                }


                if(objSelFec.isCheckBoxChecked()){
                    switch (objSelFec.getTipoSeleccion()){
                        case 0: //Básqueda por rangos
                            strSQL+=" 			AND a2.fe_venchq BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strSQL+=" 			AND a2.fe_venchq<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strSQL+=" 			AND a2.fe_venchq>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }

                strSQL+=" 			and a9.st_reg not in ('E','I')";
                strSQL+=" 			and a7.st_reg  in ('A','C')";



                int intAnd=0;

                if(chkTieSolPosPnd.isSelected()){
                    intAnd++;
                        strSQL+=" AND (a3.st_solpos IN('P')";
                }

                if(chkTieSolPosCan.isSelected()){
                    if(intAnd==0){
                        strSQL+=" AND (a3.st_solpos IN('C')";
                        intAnd++;
                    }
                    else
                        strSQL+=" OR a3.st_solpos IN('C')";
                }

                if(chkTieSolPosAut.isSelected()){
                    if(intAnd==0){
                        strSQL+=" AND (a3.st_solpos IN('A')";
                        intAnd++;
                    }
                    else
                        strSQL+=" OR a3.st_solpos IN('A')";
                }


                if(chkTieSolPosDen.isSelected()){
                    if(intAnd==0){
                        strSQL+=" AND (a3.st_solpos IN('D')";
                        intAnd++;
                    }
                    else
                        strSQL+=" OR a3.st_solpos IN('D')";
                }

                strSQL+=")";
                strSQL+=" group by a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" , a2.tx_numchq, a2.fe_recchq, a2.fe_venchq, a2.nd_monchq";
                strSQL+=" , a3.co_pos, a3.st_solpos, a3.ne_diasolpos, a2.fe_venchq, a3.ne_diasolpos";
                strSQL+=" , a3.tx_obssolpos, a3.fe_ingsolpos, a3.co_usringsolpos, a4.tx_usr, a3.tx_comingsolpos";
                strSQL+=" , a3.ne_diaautpos, a3.tx_obsautpos, a3.fe_ingautpos, a3.co_usringautpos, a5.co_usr";
                strSQL+=" , a3.tx_comingautpos, a5.tx_usr";

                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg, a3.co_pos";

                System.out.println("CARGARDETREG: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i=0;
                
                int j=0;
                
                
                while (rst.next()){
                    j++;
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,"");
                    vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));
                    vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_FEC_DOC,     rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DAT_CHQ_NUM_CHQ, rst.getString("tx_numchq"));
                    vecReg.add(INT_TBL_DAT_DAT_CHQ_VAL_CHQ, rst.getString("nd_monchq"));
                    vecReg.add(INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ,         rst.getString("fe_venchq"));
                    if(rst.getString("st_solpos").equals("C")){
                        vecReg.add(INT_TBL_DAT_SOL_CAN_CHK,     new Boolean(true));
                    }
                    else
                        vecReg.add(INT_TBL_DAT_SOL_CAN_CHK,     null);
                    
                    vecReg.add(INT_TBL_DAT_SOL_DIA,         rst.getString("ne_diasolpos"));
                    vecReg.add(INT_TBL_DAT_SOL_FEC,         rst.getString("fe_pos"));
                    vecReg.add(INT_TBL_DAT_SOL_OBS,         rst.getString("tx_obssolpos"));
                    vecReg.add(INT_TBL_DAT_SOL_OBS_BUT,     "");
                    vecReg.add(INT_TBL_DAT_SOL_COD_USU,     rst.getString("co_usringsolpos"));
                    vecReg.add(INT_TBL_DAT_SOL_ALI_USU,     rst.getString("tx_usrSolPos"));
                    if(rst.getString("st_solpos").equals("A")){
                        vecReg.add(INT_TBL_DAT_AUT_CHK,         new Boolean(true));
                    }
                    else
                        vecReg.add(INT_TBL_DAT_AUT_CHK,         null);

                    if(rst.getString("st_solpos").equals("D")){
                        vecReg.add(INT_TBL_DAT_DEN_CHK,         new Boolean(true));
                    }
                    else
                        vecReg.add(INT_TBL_DAT_DEN_CHK,         null);
                    
                    vecReg.add(INT_TBL_DAT_AUT_DIA,         rst.getString("ne_diaautpos"));
                    vecReg.add(INT_TBL_DAT_AUT_FEC,         rst.getString("fe_ingautpos"));
                    vecReg.add(INT_TBL_DAT_AUT_OBS,         rst.getString("tx_obsautpos"));
                    vecReg.add(INT_TBL_DAT_AUT_OBS_BUT,         null);
                    vecReg.add(INT_TBL_DAT_AUT_COD_USU,     rst.getString("co_usrAutPos"));
                    vecReg.add(INT_TBL_DAT_AUT_ALI_USU,     rst.getString("tx_usrAutPos"));




                    vecDat.add(vecReg);
                    i++;
                    pgrSis.setValue(i);
                }
//                System.out.println("VECDAT CONTIENE: " + vecDat.toString());
                System.out.println("I CONTIENE: " + i);

                rst.close();
                stm.close();
                rst=null;
                stm=null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
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
 


    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal()
    {
        if (txtNomCli.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
            txtNomCli.requestFocus();
            return false;
        }
        return true;
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
                case INT_TBL_DAT_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_DAT_CHQ_NUM_CHQ:
                    strMsg="Número de cheque";
                    break;
                case INT_TBL_DAT_DAT_CHQ_VAL_CHQ:
                    strMsg="Valor del cheque";
                    break;
                case INT_TBL_DAT_DAT_CHQ_FEC_VEN_CHQ:
                    strMsg="Fecha de vencimiento del cheque";
                    break;
                case INT_TBL_DAT_SOL_CAN_CHK:
                    strMsg="Cancelar";
                    break;
                case INT_TBL_DAT_SOL_DIA:
                    strMsg="Días de postergación solicitados";
                    break;
                case INT_TBL_DAT_SOL_FEC:
                    strMsg="Fecha de postergación solicitada";
                    break;
                case INT_TBL_DAT_SOL_OBS:
                    strMsg="Observación de la solicitud de postergación";
                    break;
                case INT_TBL_DAT_SOL_COD_USU:
                    strMsg="Código de usuario de la solicitud";
                    break;
                case INT_TBL_DAT_SOL_ALI_USU:
                    strMsg="Usuario de la solicitud";
                    break;
                case INT_TBL_DAT_AUT_CHK:
                    strMsg="Autorizar";
                    break;
                case INT_TBL_DAT_DEN_CHK:
                    strMsg="Denegar";
                    break;
                case INT_TBL_DAT_AUT_DIA:
                    strMsg="Días de postergación autorizados";
                    break;
                case INT_TBL_DAT_AUT_FEC:
                    strMsg="Fecha de postergación autorizada";
                    break;
                case INT_TBL_DAT_AUT_OBS:
                    strMsg="Observación de la autorización";
                    break;
                case INT_TBL_DAT_AUT_COD_USU:
                    strMsg="Código de usuario de la autorización";
                    break;
                case INT_TBL_DAT_AUT_ALI_USU:
                    strMsg="Usuario de la autorización";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }




}