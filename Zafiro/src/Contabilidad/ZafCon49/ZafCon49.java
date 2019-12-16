/*
 * ZafCon03.java
 *
 * Created on 24 de enero de 2006, 11:06
 */

package Contabilidad.ZafCon49;

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.sql.*;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.math.BigDecimal;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
/**
 * @author  ilino
 */
public class ZafCon49 extends javax.swing.JInternalFrame {
     Librerias.ZafUtil.ZafUtil objUti;
     Librerias.ZafParSis.ZafParSis objParSis;
     Vector vecDat, vecCab, vecReg;
     ZafTblMod objTblMod, objTblModTot;
     ZafColNumerada objColNum;
     ZafTblPopMnu objTblPopMnu;
     private ZafTblBus objTblBus;
     private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
     
     private String strSQL,strAux;
     private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
     private ZafVenCon vcoCli;                           //Ventana de consulta "Proveedor".
    
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP=1;
    final int INT_TBL_DAT_COD_LOC=2;
    final int INT_TBL_DAT_COD_TIP_DOC=3;//PARA DESCRIPCION
    final int INT_TBL_DAT_COD_DOC=4;
    final int INT_TBL_DAT_RUC=5;
    final int INT_TBL_DAT_COD_CLI=6;
    final int INT_TBL_DAT_NOM_CLI=7;
    final int INT_TBL_DAT_FEC_DOC=8;
    final int INT_TBL_DAT_NUM_AUT_SRI=9;
    final int INT_TBL_DAT_NUM_SER_DOC=10;
    final int INT_TBL_DAT_NUM_DOC_=11;
    final int INT_TBL_DAT_VAL_SIN_IVA=12;
    final int INT_TBL_DAT_VAL_IVA=13;
    final int INT_TBL_DAT_VAL_TOT=14;
    final int INT_TBL_DAT_VAL_RET=15;
    final int INT_TBL_DAT_EST_REG=16;
    final int INT_TBL_DAT_EST_REG_LET=17;



    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    
    private boolean blnCon; 
    private ZafThreadGUI objThrGUI;

    private Connection con;
    private Statement stm;
    private ResultSet rst;    
    private String strDesCorTipDoc, strDesLarTipDoc;
    
    private ZafSelFec objSelFec;
    private ZafVenCon vcoTipDoc;
    private ZafTblTot objTblTot;
    private ZafTblOrd objTblOrd;


    //Constantes: Columnas del JTable:
    static final int INT_TBL_LOC_LIN=0;                         //Línea.
    static final int INT_TBL_LOC_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_LOC_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_LOC_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_LOC_COD_LOC=4;                     //Código del local.
    static final int INT_TBL_LOC_NOM_LOC=5;                     //Nombre del local.


    private ZafTblMod objTblModLoc;
    private boolean blnMarTodChkTblEmp=true;                    //Marcar todas las casillas de verificación del JTable de empresas.
    private Vector vecAux;
    private ZafTblFilCab objTblFilCab;

    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.

    /** Creates new form ZafCon03 */
     public ZafCon49(Librerias.ZafParSis.ZafParSis obj) {
         initComponents();
      try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
        objUti = new Librerias.ZafUtil.ZafUtil();
        this.setTitle(objParSis.getNombreMenu()+ "v0.1");
        if (!configurarFrm())
            exitForm();        
        //this.setBounds(10,10, 625,295);       
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(this, e);
      }
    }
    

    private void mostrarMsgError(String strMensaje){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro.- Contabilidad";            
            obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
     private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
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
        panGrl = new javax.swing.JPanel();
        panCen = new javax.swing.JPanel();
        panFec = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        txtCodTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        cboEstDoc = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        chkExcVenEmp = new javax.swing.JCheckBox();
        panLoc = new javax.swing.JPanel();
        spnLoc = new javax.swing.JScrollPane();
        tblLoc = new javax.swing.JTable();
        chkMosFacOtrMot = new javax.swing.JCheckBox();
        chkNotMosPreFac = new javax.swing.JCheckBox();
        panRep = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTotal = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
        panPie = new javax.swing.JPanel();
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
            }
        });

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 20));
        lblTit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panGrl.setLayout(new java.awt.BorderLayout());

        panCen.setName(""); // NOI18N
        panCen.setPreferredSize(new java.awt.Dimension(10, 300));
        panCen.setLayout(new java.awt.BorderLayout());

        panFec.setPreferredSize(new java.awt.Dimension(0, 80));
        panFec.setLayout(new java.awt.BorderLayout());
        panCen.add(panFec, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblPrv.setText("Cliente:");
        lblPrv.setToolTipText("Proveedor");
        panFil.add(lblPrv);
        lblPrv.setBounds(8, 26, 70, 20);

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
        panFil.add(txtCodPrv);
        txtCodPrv.setBounds(150, 24, 80, 20);

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
        panFil.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(230, 24, 280, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panFil.add(butPrv);
        butPrv.setBounds(510, 24, 20, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(116, 4, 32, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(8, 4, 120, 20);

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
        panFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(150, 4, 80, 20);

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
        panFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(230, 4, 280, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(510, 4, 20, 20);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "Activos", "Inactivos", " " }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(150, 44, 290, 20);

        jLabel1.setText("Estado del Documento:");
        panFil.add(jLabel1);
        jLabel1.setBounds(8, 46, 140, 20);

        chkExcVenEmp.setSelected(true);
        chkExcVenEmp.setText("Excluir las ventas entre las empresas del grupo");
        panFil.add(chkExcVenEmp);
        chkExcVenEmp.setBounds(0, 194, 400, 14);

        panLoc.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de locales"));
        panLoc.setAutoscrolls(true);
        panLoc.setLayout(new java.awt.BorderLayout());

        tblLoc.setModel(new javax.swing.table.DefaultTableModel(
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
        spnLoc.setViewportView(tblLoc);

        panLoc.add(spnLoc, java.awt.BorderLayout.CENTER);

        panFil.add(panLoc);
        panLoc.setBounds(2, 66, 640, 130);

        chkMosFacOtrMot.setText("Mostrar valor de items que no afectan al inventario");
        panFil.add(chkMosFacOtrMot);
        chkMosFacOtrMot.setBounds(0, 224, 390, 14);

        chkNotMosPreFac.setSelected(true);
        chkNotMosPreFac.setText("No mostrar Prefacturas");
        panFil.add(chkNotMosPreFac);
        chkNotMosPreFac.setBounds(0, 209, 330, 14);

        panCen.add(panFil, java.awt.BorderLayout.CENTER);

        panGrl.add(panCen, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGrl);

        panRep.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(452, 266));

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

        panRep.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

        tblTotal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, true, true, true, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        spnTotal.setViewportView(tblTotal);

        panRep.add(spnTotal, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRep);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPie.setMinimumSize(new java.awt.Dimension(50, 33));
        panPie.setPreferredSize(new java.awt.Dimension(0, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

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

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                dispose();
            }
        }
        catch (Exception e){
            dispose();
        }
        
}//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:
        if (butCon.getText().equals("Consultar")){
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }
        }
        else{
            blnCon=false;
        }
}//GEN-LAST:event_butConActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm()
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="áEstá seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            this.dispose();
        }
    }        
    
    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        // TODO add your handling code here:
//        this.dispose();
        exitForm();
        
    }//GEN-LAST:event_butCerActionPerformed

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
    txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
    strCodPrv=txtCodPrv.getText();
    txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
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

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
    txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
    strDesLarPrv=txtDesLarPrv.getText();
    txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
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
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
}//GEN-LAST:event_txtDesLarPrvFocusLost


private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
    // TODO add your handling code here:
    txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
    // TODO add your handling code here:
        strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
    // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
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
    // TODO add your handling code here:
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    // TODO add your handling code here:
        strDesLarTipDoc=txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
    // TODO add your handling code here:
        //Validar el contenido de la celda sálo si ha cambiado.
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
    // TODO add your handling code here:
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed
    


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
                    vcoCli.setCampoBusqueda(2);
                    vcoCli.show();
                    if (vcoCli.getSelectedButton()==vcoCli.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
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
                            txtDesLarPrv.setText(vcoCli.getValueAt(3));
                            objTblMod.removeAllRows();
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoCli.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
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
                            txtDesLarPrv.setText(vcoCli.getValueAt(3));
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

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JCheckBox chkExcVenEmp;
    private javax.swing.JCheckBox chkMosFacOtrMot;
    private javax.swing.JCheckBox chkNotMosPreFac;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panLoc;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnLoc;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblLoc;
    private javax.swing.JTable tblTotal;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    // End of variables declaration//GEN-END:variables

    private boolean isCamVal(){
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }

        return true;
    }
    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFec.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux+"  v0.2.3");
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodCta.setVisible(false);
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(18);  //Almacena las cabeceras
            vecCab.clear();                                    
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"COD.EMP.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"COD.LOC.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"COD.TIP.DOC.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"COD.DOC.");
            vecCab.add(INT_TBL_DAT_RUC,"RUC");
            vecCab.add(INT_TBL_DAT_COD_CLI,"COD.CLI.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"NOM.CLI.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"FEC.DOC.");
            vecCab.add(INT_TBL_DAT_NUM_AUT_SRI,"NUM.AUT.SRI.");
            vecCab.add(INT_TBL_DAT_NUM_SER_DOC,"NUM.SER.DOC.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_,"NUM.DOC.");
            vecCab.add(INT_TBL_DAT_VAL_SIN_IVA,"VAL.SIN.IVA.");
            vecCab.add(INT_TBL_DAT_VAL_IVA,"IVA");
            vecCab.add(INT_TBL_DAT_VAL_TOT,"TOT.");
            vecCab.add(INT_TBL_DAT_VAL_RET,"VAL.RTE.");
            vecCab.add(INT_TBL_DAT_EST_REG,"EST.");
            vecCab.add(INT_TBL_DAT_EST_REG_LET,"EST.LET");

            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);


            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_RUC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT_SRI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SER_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_SIN_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG_LET).setPreferredWidth(30);


            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_EST_REG_LET, tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT_SRI).setCellRenderer(objTblCelRenLbl);
    	    tcmAux.getColumn(INT_TBL_DAT_NUM_SER_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);

            tcmAux.getColumn(INT_TBL_DAT_VAL_SIN_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setCellRenderer(objTblCelRenLbl);
            
            objTblCelRenLbl=null;
            //Libero los objetos auxiliares.
            tcmAux=null;


            //PARA TOTALES
//            objTblModTot=new ZafTblMod();
//            objTblModTot.setHeader(vecCab);
//            tblTotal.setModel(objTblModTot);
            //Configurar JTable: Establecer el ancho de las columnas.

            //Configurar JTable: Establecer relacián entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_SIN_IVA, INT_TBL_DAT_VAL_IVA, INT_TBL_DAT_VAL_TOT,INT_TBL_DAT_VAL_RET};
            objTblTot=new ZafTblTot(spnDat, spnTotal, tblDat, tblTotal, intCol);

            tblTotal.setRowSelectionAllowed(true);
            tblTotal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            tcmAux=tblTotal.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_RUC).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AUT_SRI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_SER_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_SIN_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_IVA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_VAL_TOT).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_VAL_RET).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG_LET).setPreferredWidth(30);





//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblTotal);
//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblTotal);
//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblTotal);
//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblTotal);
//            objTblModTot.addSystemHiddenColumn(INT_TBL_DAT_COD_CLI, tblTotal);

            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_DAT_EST_REG_LET).setPreferredWidth(0);


            configurarVenConPrv();
            configurarVenConTipDoc();
            configurarTblLoc();
            cargarLoc();


            txtCodTipDoc.setVisible(false);
            txtCodTipDoc.setEditable(false);
            txtCodTipDoc.setEnabled(false);


        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }





    /**
     * Esta función configura el JTable "tblLoc".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblLoc()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LOC_LIN,"");
            vecCab.add(INT_TBL_LOC_CHK,"");
            vecCab.add(INT_TBL_LOC_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_LOC_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_LOC_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_LOC_NOM_LOC,"Local");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModLoc=new ZafTblMod();
            objTblModLoc.setHeader(vecCab);
            tblLoc.setModel(objTblModLoc);
            //Configurar JTable: Establecer tipo de selección.
            tblLoc.setRowSelectionAllowed(true);
            tblLoc.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblLoc);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblLoc.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblLoc.getColumnModel();
            tcmAux.getColumn(INT_TBL_LOC_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_EMP).setPreferredWidth(221);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_LOC_NOM_LOC).setPreferredWidth(221);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_LOC_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblLoc.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
//            objTblModLoc.addSystemHiddenColumn(INT_TBL_LOC_COD_EMP, tblLoc);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblLoc.getTableHeader().addMouseMotionListener(new ZafMouMotAdaLoc());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblLoc.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblLocMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_LOC_CHK);
            objTblModLoc.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblLoc);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_LOC_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_LOC_COD_LOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblLoc);
            tcmAux.getColumn(INT_TBL_LOC_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiChk.isChecked())
                    {
//                        objTblMod.setValueAt(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_VAL_PEN), tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                    else
                    {
//                        objTblMod.setValueAt(null, tblDat.getSelectedRow(), INT_TBL_DAT_ABO_DOC);
                    }
                }
            });

            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblLoc.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
            //Configurar JTable: Establecer el modo de operación.
            objTblModLoc.setModoOperacion(objTblModLoc.INT_TBL_EDI);
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
                    strMsg="Código Empresa";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código Tipo Documento";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código Documento";
                    break;
                case INT_TBL_DAT_RUC:
                    strMsg="RUC";
                    break;                    
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código Cliente";
                    break;                    
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Razón Social";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de Emisión Comprobante de Venta";
                    break;
                case INT_TBL_DAT_NUM_AUT_SRI:
                    strMsg="No. Autorización del Comprobante de Venta SRI";
                    break;
                case INT_TBL_DAT_NUM_SER_DOC:
                    strMsg="No. de Serie del Comprobante de Venta";
                    break;
                case INT_TBL_DAT_NUM_DOC_:
                    strMsg="No. Secuencial del Comprobante de Venta Factura";
                    break;
                case INT_TBL_DAT_VAL_SIN_IVA:
                    strMsg="Valor sin Iva";
                    break;
                case INT_TBL_DAT_VAL_IVA:
                    strMsg="Valor de Iva";
                    break;
                case INT_TBL_DAT_VAL_TOT:
                    strMsg="Total del Comprobante de Venta";
                    break;
                case INT_TBL_DAT_VAL_RET:
                    strMsg="Valor Retención en la Fuente";
                    break;
                case INT_TBL_DAT_EST_REG:
                    strMsg="Estado del Registro";
                    break;
                case INT_TBL_DAT_EST_REG_LET:
                    strMsg="Estado del Registro para colocar valores 0 de retención cuando este anulado doc.";
                    break;


                default:
                    break;




            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    private class ZafThreadGUI extends Thread{
        public void run(){
            if (!cargarDetReg()){
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sálo cuando haya datos.
            if (tblDat.getRowCount()>0){
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }



    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        int i, intNumTotReg;
        strAux="";
        int intCodEmpRst, intCodLocRst, intCodTipDocRst, intCodDocRst;
        String strEstDoc="";
        int intNumFilTblBod;
        String strAuxBod, strConSQL="";
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
           
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if( ! txtCodTipDoc.getText().equals(""))
                strAux+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
            if( ! txtCodPrv.getText().equals(""))
                strAux+=" AND a1.co_cli=" + txtCodPrv.getText() + "";
            switch(cboEstDoc.getSelectedIndex()){
                case 0:
                    strAux+=" AND a1.st_reg IN('A','I','C','F','O')";//todos
                    break;
                case 1:
                    strAux+=" AND a1.st_reg IN('A','C','F','O')";//activos
                    break;
                case 2:
                    strAux+=" AND a1.st_reg IN('I')";//inactivos
                    break;
            }


            switch (objSelFec.getTipoSeleccion()){
                case 0: //Básqueda por rangos
                    strAux+=" AND (a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAux+=" AND (a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAux+=" AND (a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 3: //Todo.
                    break;
            }


                 
            
            //Obtener los locales a consultar.
            intNumFilTblBod=objTblModLoc.getRowCountTrue();
            i=0;
            strAuxBod="";
            for (int j=0; j<intNumFilTblBod; j++)
            {
                if (objTblModLoc.isChecked(j, INT_TBL_LOC_CHK))
                {
                    if (i==0)
                        strAuxBod+=" (a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    else
                        strAuxBod+=" OR (a1.co_loc=" + objTblModLoc.getValueAt(j, INT_TBL_LOC_COD_LOC) + ")";
                    i++;
                }
            }
            if (!strAuxBod.equals(""))
                strConSQL+=" AND (" + strAuxBod + ")";








            if (chkExcVenEmp.isSelected())
            {
                strAux+=" AND CASE WHEN a1.co_emp=1 THEN a1.co_cli NOT IN(3515,3516,602,603,2600,1039)";
                strAux+=" 	   WHEN a1.co_emp=2 THEN a1.co_cli NOT IN(2853,2854,446,447,2105,789,790)";
                strAux+=" 	   WHEN a1.co_emp=3 THEN a1.co_cli NOT IN(2857,2858,452,453,2107,832)";
                strAux+="   	   WHEN a1.co_emp=4 THEN a1.co_cli NOT IN(3116,3117,497,498,2294,886,887)";
                strAux+=" END";
            }

            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="                 SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,";
                strSQL+="                         a1.tx_ruc, a1.co_cli, a1.tx_nomCli, a1.fe_doc,";
                strSQL+="                         a3.tx_numAutSri, a3.tx_numSerFac, a1.ne_numDoc,";               
                strSQL+="	CASE WHEN a1.st_reg NOT IN('A') THEN 0 ";
                strSQL+=" 	ELSE (";
                strSQL+=" 		CASE WHEN a1.co_tipdoc=1 THEN ( (round(sum(round(  ((abs(round(a2.nd_can,6))*round(a2.nd_preuni,6))   )  ,6)),2))";
                strSQL+=" 		-(round(sum(round(  ((abs(round(a2.nd_can,6))*round(a2.nd_preuni,6))*(round(a2.nd_pordes,2)/100)   )  ,6)),2)))";
                strSQL+="		      WHEN (a1.co_tipdoc IN(3,99) AND a1.st_tipdev='C') THEN (round(sum(round(  ((abs(nd_can)*nd_preuni))  ,3)),3)   ";
                strSQL+="		      - round(sum(round(  ((abs(nd_can)*nd_preuni)*(round(a2.nd_pordes,2)/100))  ,3)),3)     )*-1";
                strSQL+="		      WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='P') THEN nd_sub*-1";
                strSQL+="		      WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='V') THEN (round(sum(round(  ((nd_can*nd_canorg))  ,3)),3)";
                strSQL+=" 		      - round(sum(round(  ((nd_can*nd_canorg*(round(a2.nd_pordes,2)/100)))  ,3)),3) )*-1";
                //strSQL+=" 		      ELSE a1.nd_sub END";
                strSQL+=" 		      ELSE ";
                strSQL+="                           (";
                strSQL+="                           CASE WHEN a1.co_tipdoc IN(3,99) THEN (a1.nd_sub*-1)";
                strSQL+="                           ELSE a1.nd_sub END";
                strSQL+="                           )";
                strSQL+=" 		      END";
                
                strSQL+=" 	) END AS nd_sub, ";
                strSQL+=" 	CASE WHEN a1.st_reg='I' THEN 0 ";
                strSQL+=" 	ELSE (";
                strSQL+=" 		CASE WHEN a1.co_tipdoc=1 THEN (round(sum(case when a2.st_ivacom='S' then";
                strSQL+=" 			( round( ((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)*0.12)";
                strSQL+=" 				when a2.st_ivacom='N' then ( ( (abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100)))*0) end),3)) ";
                strSQL+=" 			WHEN (a1.co_tipdoc IN(3,99) AND a1.st_tipdev='C') THEN (round(sum(case when a2.st_ivacom='S' then";
                strSQL+=" 			( round( ((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)*0.12)";
                strSQL+=" 				when a2.st_ivacom='N' then ( ( (abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100)))*0) end),3)*-1) ";
                strSQL+=" 			WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='P') THEN (nd_valiva) *-1";
                strSQL+=" 			WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='V') THEN (round(sum(case when a2.st_ivacom='S' then ( ((round((round(  ((nd_can*nd_canorg))  ,3)),3)-round((round((round(  ((nd_can*nd_canorg))  ,3)),3)";
                strSQL+=" 				*(nd_pordes/100)),3)))*0.12) when a2.st_ivacom='N' then ( ((round((round(  ((nd_can*nd_canorg))  ,3)),3)";
                strSQL+=" 				-round((round((round(  ((nd_can*nd_canorg))  ,3)),3)*(nd_pordes/100)),3)))*0) end ),3)*-1)     ";
                //strSQL+=" 		ELSE a1.nd_valIva END";
                
                strSQL+=" 		      ELSE ";
                strSQL+="                           (";
                strSQL+="                           CASE WHEN a1.co_tipdoc IN(3,99) THEN (a1.nd_valIva*-1)";
                strSQL+="                           ELSE a1.nd_valIva END";
                strSQL+="                           )";
                strSQL+=" 		      END";
                
                
                strSQL+=" 	) END AS nd_valIva, 	";
                strSQL+=" 	CASE WHEN a1.st_reg='I' THEN 0 ";
                strSQL+=" 	ELSE(";
                strSQL+=" 		CASE WHEN a1.co_tipdoc=1 THEN";
                strSQL+=" 				round( round(sum( round(((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)  ),3)  +";
                strSQL+=" 				round(sum(case when a2.st_ivacom='S' then ( round( ((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)*0.12)";
                strSQL+=" 				when a2.st_ivacom='N' then ( ( (abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100)))*0) end),3) ,3 )";
                strSQL+=" 			WHEN (a1.co_tipdoc IN(3,99) AND a1.st_tipdev='C') THEN";
                strSQL+=" 				round( round(sum( round(((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)  ),3)  +";
                strSQL+=" 				round(sum(case when a2.st_ivacom='S' then ( round( ((abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100))),3)*0.12)";
                strSQL+=" 				when a2.st_ivacom='N' then ( ( (abs(nd_can)*nd_preuni)-((abs(nd_can)*nd_preuni)*(nd_pordes/100)))*0) end),3) ,3 )*-1";
                strSQL+=" 			WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='P') THEN (((nd_sub-(round(sum(round(  ((nd_preuni*((nd_canorg)/100)))  ,3)),3)*0))+nd_valiva)*-1) ";
                strSQL+=" 			WHEN (a1.co_tipDoc IN(3,99) AND a1.st_tipdev='V') THEN";
                strSQL+=" 				( (sum((round((round(  ((nd_can*nd_canorg))  ,3)),3)-round((round((round(  ((nd_can*nd_canorg))  ,3)),3)*(nd_pordes/100)),3)))*-1) +";
                strSQL+=" 				round(sum(case when a2.st_ivacom='S' then ( ((round((round(  ((nd_can*nd_canorg))  ,3)),3)-round((round((round(  ((nd_can*nd_canorg))  ,3)),3)";
                strSQL+=" 				*(nd_pordes/100)),3)))*0.12) when a2.st_ivacom='N' then ( ((round((round(  ((nd_can*nd_canorg))  ,3)),3)-round((round((round(  ((nd_can*nd_canorg))  ,3)),3)";
                strSQL+=" 				*(nd_pordes/100)),3)))*0) end ),3)*-1)";
                //strSQL+=" 		ELSE a1.nd_tot END";
                
                strSQL+=" 		      ELSE ";
                strSQL+="                           (";
                strSQL+="                           CASE WHEN a1.co_tipdoc IN(3,99) THEN (a1.nd_tot*-1)";
                strSQL+="                           ELSE a1.nd_tot END";
                strSQL+="                           )";
                strSQL+=" 		      END";
                
                strSQL+=" 	) END AS nd_tot";
                strSQL+="           ,a1.st_reg";
               
                strSQL+="                   FROM (tbm_cabMovInv AS a1 ";
                strSQL+="                   	INNER JOIN tbm_detMovInv AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="                   	INNER JOIN tbm_inv AS a4 ON a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm";
                strSQL+="                     )";
                strSQL+="                LEFT OUTER JOIN tbm_datAutSri AS a3 ";
                strSQL+=" 		 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+="                  AND a1.ne_numDoc BETWEEN a3.ne_numDocDes AND a3.ne_numDocHas AND a3.st_reg='A'";
                if(objParSis.getCodigoUsuario()==1){
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="" + strConSQL;
                    strSQL+=" AND a1.co_tipDoc IN(";
                    strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocPrg ";
                    strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+="" + strConSQL;
                    strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" )";
                }
                else{
                    if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="" + strConSQL;
                        strSQL+=" AND a1.co_tipDoc IN(";
                        strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocUsr ";
                        strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="" + strConSQL;
                        strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                        strSQL+="                           AND co_usr=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" )";
                    }
                    else{
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="" + strConSQL;
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.co_tipDoc IN(";
                        strSQL+="                       SELECT co_tipDoc FROM tbr_tipDocUsr ";
                        strSQL+="                           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="" + strConSQL;
                        //strSQL+="                           AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+="                           AND co_mnu=" + objParSis.getCodigoMenu() + "";
                        strSQL+="                           AND co_usr=" + objParSis.getCodigoUsuario() + "";
                        strSQL+=" )";
                    }
                }
                strSQL+=strAux;
                
                if(!chkMosFacOtrMot.isSelected())
                    strSQL+=" AND a4.st_ser NOT IN('O')";
                
                if(chkNotMosPreFac.isSelected())
                    strSQL+=" AND a1.ne_numDoc<>0";
                
                
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc,";
                strSQL+=" a1.tx_ruc, a1.co_cli, a1.tx_nomCli, a1.fe_doc,";
                strSQL+=" a3.tx_numAutSri, a3.tx_numSerFac, a1.ne_numDoc,";
                strSQL+=" a1.st_reg, a1.nd_sub, a1.nd_valIva, a1.nd_tot";
                strSQL+=" ,a1.st_tipdev";
                strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.ne_numDoc, a1.fe_doc";
                System.out.println("SQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setIndeterminate(true);
                pgrSis.setValue(0);
                i=0;
                while (rst.next()){
                    if (blnCon){

                        intCodEmpRst=rst.getInt("co_emp");
                        intCodLocRst=rst.getInt("co_loc");
                        intCodTipDocRst=rst.getInt("co_tipdoc");
                        intCodDocRst=rst.getInt("co_doc");


                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,""      + intCodEmpRst);
                        vecReg.add(INT_TBL_DAT_COD_LOC,""      + intCodLocRst);
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,""  + intCodTipDocRst);
                        vecReg.add(INT_TBL_DAT_COD_DOC,""      + intCodDocRst);
                        vecReg.add(INT_TBL_DAT_RUC,""          + rst.getString("tx_ruc"));
                        vecReg.add(INT_TBL_DAT_COD_CLI,""      + rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,""      + rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,""      + objUti.formatearFecha(rst.getString("fe_doc"), "yyyy-MM-dd", "dd/MM/yy"));
                        vecReg.add(INT_TBL_DAT_NUM_AUT_SRI,""  + rst.getObject("tx_numAutSri")==null?"":rst.getString("tx_numAutSri"));
                        vecReg.add(INT_TBL_DAT_NUM_SER_DOC,""  + rst.getObject("tx_numSerFac")==null?"":rst.getString("tx_numSerFac"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC_,""     + rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_VAL_SIN_IVA,""  + (rst.getDouble("nd_sub")));
                        vecReg.add(INT_TBL_DAT_VAL_IVA,""      + (rst.getDouble("nd_valIva")));
                        vecReg.add(INT_TBL_DAT_VAL_TOT,""      + (rst.getDouble("nd_tot")));
                        //vecReg.add(INT_TBL_DAT_VAL_RET,""      + getValorRetencion(intCodEmpRst, intCodLocRst, intCodTipDocRst, intCodDocRst));
                        vecReg.add(INT_TBL_DAT_VAL_RET,""      + "");
                        strEstDoc=rst.getString("st_reg");
                        if(strEstDoc.equals("I"))
                            vecReg.add(INT_TBL_DAT_EST_REG,"   "   + "Anulado");
                        else
                            vecReg.add(INT_TBL_DAT_EST_REG,"   "   + "Activo");
                        vecReg.add(INT_TBL_DAT_EST_REG_LET,""   + strEstDoc);

                        vecDat.add(vecReg); 
                        i++;
                        pgrSis.setValue(i);
                    }
                    else{
                        break;
                    }
                }
                pgrSis.setIndeterminate(false);
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                
                
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();



                if (tblDat.getRowCount()==tblDat.getRowCount())
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
                tabFrm.setSelectedIndex(1);
                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount()>0){
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }

                if( ! getValorRetencion()){
                    mostrarMsgInf("Los datos de retención no se cargaron correctamente");
                }

                objTblTot.calcularTotales();

                con.close();
                con=null;
                blnMarTodChkTblEmp=false;
                
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


    private boolean getValorRetencion(){
        boolean blnRes=true;
        BigDecimal bdeValRet=new BigDecimal(0);
        Connection conRet;
        Statement stmRet;
        ResultSet rstRet;
        String strEst="";
        try{
            conRet=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conRet!=null){
                stmRet=conRet.createStatement();
                lblMsgSis.setText("Cargando datos de retención...");
                for(int i=0; i<objTblMod.getRowCountTrue();i++){
                    strEst=objTblMod.getValueAt(i, INT_TBL_DAT_EST_REG_LET)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_EST_REG_LET).toString();
                    if(strEst.equals("I")){
                        bdeValRet=new BigDecimal(0);
                        objTblMod.setValueAt(bdeValRet, i, INT_TBL_DAT_VAL_RET);
                    }
                    else{
                        strSQL="";
                        strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, SUM(a2.mo_pag) AS nd_valRet";
                        strSQL+=" FROM tbm_pagMovInv AS a2";
                        strSQL+=" WHERE a2.nd_porRet IN(1,2) AND a2.st_reg IN('A','C')";
                        strSQL+=" AND a2.co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP) + "";
                        strSQL+=" AND a2.co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC) + "";
                        strSQL+=" AND a2.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                        strSQL+=" AND a2.co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC) + "";
                        strSQL+=" GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc";
                        rstRet=stmRet.executeQuery(strSQL);
                        if(rstRet.next()){
                            bdeValRet=rstRet.getBigDecimal("nd_valRet").abs();
                        }
                        objTblMod.setValueAt(bdeValRet, i, INT_TBL_DAT_VAL_RET);
                        bdeValRet=new BigDecimal(0);
                        rstRet.close();
                        rstRet=null;
                    }
                }
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                conRet.close();
                conRet=null;
                stmRet.close();
                stmRet=null;
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
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
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else{
                strSQL="";
                strSQL+="SELECT a2.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.st_cli='S'";
                strSQL+=" ORDER BY a2.tx_nom";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoCli=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de clientes", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
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
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, ";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar,";
                strSQL+=" CASE WHEN a2.ne_ultDoc IS NULL THEN a1.ne_ultDoc ELSE a2.ne_ultDoc END AS ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc";
                strSQL+=" ,CASE WHEN a2.ne_ultDoc IS NULL THEN 'L' ELSE 'G' END AS tblActNumDoc, a2.co_grpTipDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  (tbm_cabTipDoc AS a1 LEFT OUTER JOIN tbm_cabGrpTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_grpTipDoc=a2.co_grpTipDoc)";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
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
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
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
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblLocMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModLoc.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblLoc.columnAtPoint(evt.getPoint())==INT_TBL_LOC_CHK)
            {
                if (blnMarTodChkTblEmp)
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(true, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=false;
                }
                else
                {
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModLoc.setChecked(false, i, INT_TBL_LOC_CHK);
                    }
                    blnMarTodChkTblEmp=true;
                }
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }


    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaLoc extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblLoc.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_LOC_LIN:
                    strMsg="";
                    break;
                case INT_TBL_LOC_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_LOC_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_LOC_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_LOC_NOM_LOC:
                    strMsg="Nombre del local";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblLoc.getTableHeader().setToolTipText(strMsg);
        }
    }



    /**
     * Esta función permite consultar los locales de acuerdo al siguiente criterio:
     * <UL>
     * <LI>Si se ingresa a la empresa "Grupo" se muestran todos los locales.
     * <LI>Si se ingresa a cualquier otra empresa se muestran sólo los locales pertenecientes a la empresa seleccionada.
     * </UL>
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarLoc()
    {
        int intCodEmp;
        boolean blnRes=true;
        try
        {
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todos los locales.
                if (objParSis.getCodigoUsuario()==1)
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                else
                {
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_loc, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_loc AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN tbr_locUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a3.co_usr=" + objParSis.getCodigoUsuario() + ")";
                    if (objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo())
                    {
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                        strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    }
                    strSQL+=" AND a2.st_reg IN ('A', 'P')";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_loc";
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LOC_LIN,"");
                    vecReg.add(INT_TBL_LOC_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_LOC_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_LOC_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_LOC_COD_LOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_LOC_NOM_LOC,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModLoc.setData(vecDat);
                tblLoc.setModel(objTblModLoc);
                vecDat.clear();
                blnMarTodChkTblEmp=false;
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





}
