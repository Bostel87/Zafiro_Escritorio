/*
 * ZafImp22.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Importaciones.ZafImp22;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.DriverManager;
import Librerias.ZafRptSis.ZafRptSis;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp22 extends javax.swing.JInternalFrame {
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_EMP_REL=1;
    final int INT_TBL_DAT_COD_LOC_REL=2;
    final int INT_TBL_DAT_COD_TIP_DOC_REL=3;
    final int INT_TBL_DAT_COD_DOC_REL=4;
    final int INT_TBL_DAT_COD_REG_REL=5;
    final int INT_TBL_DAT_COD_ITM_GRP=6;
    final int INT_TBL_DAT_COD_ITM_EMP=7;
    final int INT_TBL_DAT_COD_ITM_MAE=8;
    final int INT_TBL_DAT_COD_ALT_ITM=9;
    final int INT_TBL_DAT_BUT_ANE_UNO=10;
    final int INT_TBL_DAT_NOM_ITM=11;
    final int INT_TBL_DAT_DES_COR_UNI_MED=12;
    final int INT_TBL_DAT_DES_LAR_UNI_MED=13;
    final int INT_TBL_DAT_CAN_ORD_DIS=14;
    final int INT_TBL_DAT_CAN_SEL=15;
    final int INT_TBL_DAT_CAN_SEL_AUX=16;
    final int INT_TBL_DAT_COD_REG=17;
    
    private ZafTblBus objTblBus;
    private ZafDatePicker dtpFecDoc;

    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.

    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMená en JTable.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoBod;                        //Ventana de consulta "Bodega".

    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL,  strAux;
    private Vector vecDat,  vecCab,  vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecucián del hilo.

    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc,  strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private int intSig = 1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.

    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.

    private String strCodBod,  strNomBod;                //Contenido del campo al obtener el foco.

    private MiToolBar objTooBar;
    
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiBut;

    private ZafImp22_01 objImp03_01;

    private ArrayList arlReg, arlDat;
    final int INT_ARL_COD_EMP_REL=0;
    final int INT_ARL_COD_LOC_REL=1;
    final int INT_ARL_COD_TIP_DOC_REL=2;
    final int INT_ARL_COD_DOC_REL=3;
    final int INT_ARL_COD_REG_REL=4;
    final int INT_ARL_COD_ITM_GRP=5;
    final int INT_ARL_COD_ITM_EMP=6;
    final int INT_ARL_COD_ITM_MAE=7;
    final int INT_ARL_COD_ALT_ITM=8;
    final int INT_ARL_NOM_ITM=9;
    final int INT_ARL_DES_COR_UNI_MED=10;
    final int INT_ARL_DES_LAR_UNI_MED=11;
    final int INT_ARL_CAN_ORD_SEL=12;
    final int INT_ARL_CAN_DIS=13;
    
            
    private boolean blnIsRegEli;
    
    private ArrayList arlRegPed, arlDatPed;
    private int INT_ARL_PED_COD_EMP=0;
    private int INT_ARL_PED_COD_LOC=1;
    private int INT_ARL_PED_COD_TIP_DOC=2;
    private int INT_ARL_PED_COD_DOC=3;
    private int INT_ARL_PED_NUM_PED=4;
    private int INT_ARL_PED_IND_SEL=5;
    private int INT_ARL_PED_COD_IMP=6;
        
    private int intCboPedSelCodEmp;
    private int intCboPedSelCodLoc;
    private int intCboPedSelCodTipDoc;
    private int intCboPedSelCodDoc;
    
    //Arreglo que guarda los datos al momento de eliminar la fila a traves del PopUp
    private ArrayList arlRegFilEli, arlDatFilEli;
    static final int INT_CFE_CAN_SEL=0;             
    static final int INT_CFE_CAN_SEL_AUX=1;         
    static final int INT_CFE_COD_ITM_GRP=2;
    static final int INT_CFE_COD_ITM_EMP=3;
    static final int INT_CFE_COD_ITM_MAE=4;
    static final int INT_CFE_COD_REG=5;
    static final int INT_CFE_COD_REG_REL=6;
    
    private ZafThreadGUIVisPre objThrGUIVisPre;
    private ZafRptSis objRptSis;
        
    
    /** Crea una nueva instancia de la clase ZafImp22. */
    public ZafImp22(ZafParSis obj) {
        try{
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
            arlDatPed=new ArrayList();
            arlDat=new ArrayList();
            arlDatFilEli=new ArrayList();
            initComponents();
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
           
        }
        catch (CloneNotSupportedException e) {
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
        panCon = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        lblBen = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        txtNumDoc = new javax.swing.JTextField();
        txtCodDoc = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboPedSel = new javax.swing.JComboBox();
        chkCerDisImp = new javax.swing.JCheckBox();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panObs = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBarBot = new javax.swing.JPanel();

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
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panCon.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 100));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panCabFil.setPreferredSize(new java.awt.Dimension(0, 94));
        panCabFil.setLayout(null);

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
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(118, 6, 56, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 130, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(406, 6, 20, 20);

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
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(175, 6, 230, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(85, 6, 32, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCabFil.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 110, 20);

        lblNumDoc1.setText("Número documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panCabFil.add(lblNumDoc1);
        lblNumDoc1.setBounds(432, 27, 100, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCabFil.add(lblCodDoc);
        lblCodDoc.setBounds(432, 48, 110, 20);

        lblBen.setText("Bodega:");
        lblBen.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCabFil.add(lblBen);
        lblBen.setBounds(4, 26, 70, 20);

        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        panCabFil.add(txtCodBod);
        txtCodBod.setBounds(118, 27, 56, 20);

        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        panCabFil.add(txtNomBod);
        txtNomBod.setBounds(175, 27, 230, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCabFil.add(butBod);
        butBod.setBounds(406, 26, 20, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCabFil.add(txtNumDoc);
        txtNumDoc.setBounds(550, 27, 120, 20);
        panCabFil.add(txtCodDoc);
        txtCodDoc.setBounds(550, 48, 120, 20);

        jLabel1.setText("Pedido:");
        panCabFil.add(jLabel1);
        jLabel1.setBounds(4, 50, 60, 14);

        cboPedSel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPedSelActionPerformed(evt);
            }
        });
        panCabFil.add(cboPedSel);
        cboPedSel.setBounds(118, 48, 286, 20);

        chkCerDisImp.setText("Cerrar proceso de Distribución de la Importación seleccionada");
        panCabFil.add(chkCerDisImp);
        chkCerDisImp.setBounds(0, 70, 390, 23);

        panGenCab.add(panCabFil, java.awt.BorderLayout.NORTH);

        panCon.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panCon.add(panGenDet, java.awt.BorderLayout.CENTER);

        panObs.setPreferredSize(new java.awt.Dimension(34, 70));
        panObs.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panObs.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panObs.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panCon.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panCon);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBarBot.setPreferredSize(new java.awt.Dimension(320, 50));
        panBarBot.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBarBot, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void txtDesLarTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
        if (!txtDesLarTipDoc.getText().equalsIgnoreCase(strDesLarTipDoc)) {
            if (txtDesLarTipDoc.getText().equals("")) {
                txtCodTipDoc.setText("");
                txtDesCorTipDoc.setText("");
            } else {
                mostrarVenConTipDoc(2);
            }
        } else 
            txtDesLarTipDoc.setText(strDesLarTipDoc);
    }//GEN-LAST:event_txtDesLarTipDocFocusLost

    private void txtDesCorTipDocFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusLost
        //Validar el contenido de la celda sálo si ha cambiado.
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
    }//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicacián. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "áEstá seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexián si está abierta.
                if (rst != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                dispose();
            }
        } catch (java.sql.SQLException e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
// TODO add your handling code here:
    txtCodBod.transferFocus();
}//GEN-LAST:event_txtCodBodActionPerformed

private void txtCodBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusGained
// TODO add your handling code here:
    strCodBod = txtCodBod.getText();
    txtCodBod.selectAll();
}//GEN-LAST:event_txtCodBodFocusGained

private void txtCodBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodBodFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodBod.getText().equalsIgnoreCase(strCodBod)) {
        if (txtCodBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(1);
        }
    } else 
        txtCodBod.setText(strCodBod);
}//GEN-LAST:event_txtCodBodFocusLost

private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
// TODO add your handling code here:
    txtNomBod.transferFocus();
}//GEN-LAST:event_txtNomBodActionPerformed

private void txtNomBodFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusGained
// TODO add your handling code here:        
        strNomBod = txtNomBod.getText();
        txtNomBod.selectAll();
}//GEN-LAST:event_txtNomBodFocusGained

private void txtNomBodFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomBodFocusLost
// TODO add your handling code here:
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtNomBod.getText().equalsIgnoreCase(strNomBod)) {
        if (txtNomBod.getText().equals("")) {
            txtCodBod.setText("");
            txtNomBod.setText("");
        } else {
            mostrarVenConBod(2);
        }
    } else 
        txtNomBod.setText(strNomBod);
}//GEN-LAST:event_txtNomBodFocusLost

private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
// TODO add your handling code here:
    mostrarVenConBod(0);
}//GEN-LAST:event_butBodActionPerformed

    private void cboPedSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPedSelActionPerformed
        // TODO add your handling code here:        
        int intIndSelTrs=-1;
        for(int j=0; j<arlDatPed.size(); j++){
            intIndSelTrs=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_IND_SEL);
            if(cboPedSel.getSelectedIndex()==(intIndSelTrs+1)){                
                intCboPedSelCodEmp=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_EMP);
                intCboPedSelCodLoc=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_LOC);
                intCboPedSelCodTipDoc=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_TIP_DOC);
                intCboPedSelCodDoc=objUti.getIntValueAt(arlDatPed, j, INT_ARL_PED_COD_DOC);
                break;
            }
        }
    }//GEN-LAST:event_cboPedSelActionPerformed


    /** Cerrar la aplicacián. */
    private void exitForm() {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboPedSel;
    private javax.swing.JCheckBox chkCerDisImp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodBod;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomBod;
    private javax.swing.JTextField txtNumDoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //arlDatItmModTbl=new ArrayList();
            objUti = new ZafUtil();
            //String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            //Configurar ZafDatePicker:
            //DESDE
            dtpFecDoc = new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this), "d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCabFil.add(dtpFecDoc);
            dtpFecDoc.setBounds(550, 6, 120, 20);
            
            objImp03_01=new ZafImp22_01(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);

            //Inicializar objetos.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.4");
            lblTit.setText(strAux);
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());

            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());

            txtCodTipDoc.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            //Configurar los JTables.
            configurarTblDat();

            objTooBar=new MiToolBar(this);
            panBarBot.add(objTooBar);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(18);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_EMP_REL, "Cód.Emp.Rel");
            vecCab.add(INT_TBL_DAT_COD_LOC_REL, "Cód.Loc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_REL, "Cód.Tip.Doc.Rel.");
            vecCab.add(INT_TBL_DAT_COD_DOC_REL, "Cód.Doc.Rel");
            vecCab.add(INT_TBL_DAT_COD_REG_REL, "Cód.Reg.Rel.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cód.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cód.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cód.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Código");
            vecCab.add(INT_TBL_DAT_BUT_ANE_UNO, "...");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_DES_COR_UNI_MED, "Uni.Med.");
            vecCab.add(INT_TBL_DAT_DES_LAR_UNI_MED, "UNI.MED.");
            vecCab.add(INT_TBL_DAT_CAN_ORD_DIS, "Can.Ord.Dis.");  
            vecCab.add(INT_TBL_DAT_CAN_SEL, "Can.Sel.");  
            vecCab.add(INT_TBL_DAT_CAN_SEL_AUX, "Can.Sel.Aux");
            vecCab.add(INT_TBL_DAT_COD_REG, "Cód.Reg.");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_EMP_REL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_LOC_REL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_TIP_DOC_REL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_DOC_REL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_REG_REL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_ITM_GRP, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_COD_ITM_EMP, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_ORD_DIS, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_SEL, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN_SEL_AUX, objTblMod.INT_COL_DBL, new Integer(0), null);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            
             objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilEli[];
                
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()){
                        intFilEli=tblDat.getSelectedRows();
                        if( (objTooBar.getEstado()=='x')   ||   (objTooBar.getEstado()=='m') ){
                            for(int p=(intFilEli.length-1);p>=0; p--){
                                //se guardan los datos necesarios en un arraylist para guardar en frio esos items eliminados.
                                arlRegFilEli=new ArrayList();
                                arlRegFilEli.add(INT_CFE_CAN_SEL,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_SEL));
                                arlRegFilEli.add(INT_CFE_CAN_SEL_AUX,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_CAN_SEL_AUX));
                                arlRegFilEli.add(INT_CFE_COD_ITM_GRP,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_GRP));
                                arlRegFilEli.add(INT_CFE_COD_ITM_EMP,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_EMP));
                                arlRegFilEli.add(INT_CFE_COD_ITM_MAE,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_ITM_MAE));
                                arlRegFilEli.add(INT_CFE_COD_REG,           "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_REG));
                                arlRegFilEli.add(INT_CFE_COD_REG_REL,       "" + objTblMod.getValueAt(intFilEli[p], INT_TBL_DAT_COD_REG_REL));

                                arlDatFilEli.add(arlRegFilEli);
                            }
                        }
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {                    
                    if (objTblPopMnu.isClickEliminarFila()){
                    }
                }
            });
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_REL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ANE_UNO).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_UNI_MED).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ORD_DIS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_SEL_AUX).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG).setPreferredWidth(40);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_REL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_REL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_REL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_REL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_REG_REL).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setResizable(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_REG_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus = new ZafTblBus(tblDat);

            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_UNO);
            vecAux.add("" + INT_TBL_DAT_CAN_SEL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;

            //Libero los objetos auxiliares.
            tcmAux = null;            
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_UNO).setCellRenderer(objTblCelRenBut);
            objTblCelEdiBut= new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ANE_UNO).setCellEditor(objTblCelEdiBut);
            
            objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=0;
                String strLin="";
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("actionPerformed");                    
                    strLin=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if((objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                        if(strLin.equals("I")){
                            objTblCelEdiBut.setCancelarEdicion(false);
                            if( isCamposCabecera() ){
                                objImp03_01.setVisible(true);
                            }
                            else{
                                objImp03_01.setVisible(false);
                            }
                        }
                        else
                            objTblCelEdiBut.setCancelarEdicion(true);
                    }
                    else{
                        if( isCamposCabecera() ){
                            objImp03_01.setVisible(true);
                        }
                        else{
                            objImp03_01.setVisible(false);
                        }
                    }
                }
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("beforeEdit");
                    objTblCelEdiBut.setCancelarEdicion(false);
                    objImp03_01.setCodigoBodega(txtCodBod.getText());
                    objImp03_01.setNombreBodega(txtNomBod.getText());
                    System.out.println("beforeEdit3");
                    objImp03_01.setItemsSeleccionados(cargarDatosSeleccionados());
                    System.out.println("beforeEdit4");
                    //se envia la informacion del pedido para cargar solo los items de ese pedido, esto sirve para las bodegas que no sean la de Importaciones
                    objImp03_01.setCodEmpPedidoSeleccionado(intCboPedSelCodEmp);
                    objImp03_01.setCodLocPedidoSeleccionado(intCboPedSelCodLoc);
                    objImp03_01.setCodTipDocPedidoSeleccionado(intCboPedSelCodTipDoc);
                    objImp03_01.setCodDocPedidoSeleccionado(intCboPedSelCodDoc);
                    intFilSel=tblDat.getSelectedRow();
                };
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    arlDat=objImp03_01.getArlDat();                    
                    System.out.println("ARRAYLIST EN CLASE PRINCIPAL: " + arlDat.toString());
                    cargarDatosAnexo(intFilSel);
                    arlDat.clear();
                    objImp03_01.getArlDat().clear();
//                    generaCodigoRegistro();
                }
            });
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_ORD_DIS).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_SEL).setCellRenderer(objTblCelRenLbl);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_SEL_AUX).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_ORD_DIS).setCellEditor(objTblCelEdiTxt);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CAN_SEL).setCellEditor(objTblCelEdiTxt);

            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener {

        public void columnAdded(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnMarginChanged(javax.swing.event.ChangeEvent e) {
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn() != null) {
                intColSel = tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel >= 0) {
                    intAncCol = tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }

        public void columnMoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnRemoved(javax.swing.event.TableColumnModelEvent e) {
        }

        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e) {
        }
    }

    /**
     * Esta funcián determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() {
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if(!chkCerDisImp.isSelected()){
            if (txtCodBod.getText().equals("")) {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Escriba o seleccione una bodega y vuelva a intentarlo.</HTML>");
                txtNomBod.requestFocus();
                return false;
            }
        }

                    
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        
        if (txtNumDoc.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }

        if(!chkCerDisImp.isSelected()){
            if(objTblMod.getRowCountTrue()<=0){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe ingresar por lo menos un item en la orden de distribución.</HTML>");
                return false;
            }
        }

        
        if( (intCboPedSelCodEmp==0)||(intCboPedSelCodLoc==0)||(intCboPedSelCodTipDoc==0)||(intCboPedSelCodDoc==0)){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Debe seleccionar algún Pedido.</HTML>");
            return false;
        }
        
        return true;
    }

    /**
     * Esta funcián muestra un mensaje informativo al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta funcián muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funcián muestra un mensaje de advertencia al usuario. Se podráa utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        if (strMsg.equals("")) {
            strMsg = "<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifáquelo a su administrador del sistema.</HTML>";
        }
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.WARNING_MESSAGE);
    }

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
            arlAli.add("Cádigo");
            arlAli.add("Tip.Doc.");
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
            //Armar la sentencia SQL.
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbm_cabTipDoc AS a1, tbr_tipDocPrg AS a2";
                strSQL += " WHERE a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL += " AND a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.co_loc=" + objParSis.getCodigoLocal();
//                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a1.st_reg NOT IN('I','E')";
                strSQL += " ORDER BY a1.tx_desCor";
            } else {
                strSQL = "";
                strSQL += "SELECT DISTINCT(a1.co_tipdoc) AS co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                strSQL += " FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL += " ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL += " WHERE ";
                strSQL += " a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL += " AND a1.st_reg NOT IN('I','E')";
//                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
            }


            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 5;
            vcoTipDoc = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoTipDoc.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoTipDoc.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);


        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Responsables de Conteo".
     */
    private boolean configurarVenConBod() {
        boolean blnRes = true;
        try {            
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            //Armar la sentencia SQL.
            strSQL="";
            if(objParSis.getCodigoUsuario()==1){
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";                
            }
            else{//no se coloca en el filtro al usuario porque no es el usuario q ingresa al sistema, sino q quiero q salgan todos los que estan configurados en la tabla "tbr_bodtipdocprgusr"
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodtipdocprgusr AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
            }
            strSQL+=" GROUP BY a1.co_bod, a1.tx_nom";
            strSQL+=" ORDER BY a1.co_bod, a1.tx_nom";
            System.out.println("configurarVenConBod : " + strSQL);
            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas por Usuarios", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
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
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".

                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    } else {
                        vcoTipDoc.setCampoBusqueda(1);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        } else {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }

                    break;
                case 2: //Básqueda directa por "Descripcián larga".

                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText())) {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        strAux = vcoTipDoc.getValueAt(4);
                        intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                    } else {
                        vcoTipDoc.setCampoBusqueda(2);
                        vcoTipDoc.setCriterio1(11);
                        vcoTipDoc.cargarDatos();
                        vcoTipDoc.show();
                        if (vcoTipDoc.getSelectedButton() == vcoTipDoc.INT_BUT_ACE) {
                            txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                            txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                            txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                            strAux = vcoTipDoc.getValueAt(4);
                            intSig = (vcoTipDoc.getValueAt(5).equals("I") ? 1 : -1);
                        } else {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConBod(int intTipBus) {
        boolean blnRes = true;
        try {
            if(configurarVenConBod()){
                switch (intTipBus) {
                    case 0: //Mostrar la ventana de consulta.

                        vcoBod.setCampoBusqueda(2);
                        vcoBod.show();
                        if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        }
                        break;
                    case 1: //Básqueda directa por "Námero de cuenta".

                        if (vcoBod.buscar("a1.co_bod", txtCodBod.getText())) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            vcoBod.setCampoBusqueda(0);
                            vcoBod.setCriterio1(11);
                            vcoBod.cargarDatos();
                            vcoBod.show();
                            if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                                txtCodBod.setText(vcoBod.getValueAt(1));
                                txtNomBod.setText(vcoBod.getValueAt(2));
                            } else {
                                txtCodBod.setText(strCodBod);
                            }
                        }
                        break;
                    case 2: //Básqueda directa por "Descripcián larga".

                        if (vcoBod.buscar("a1.tx_nom", txtNomBod.getText())) {
                            txtCodBod.setText(vcoBod.getValueAt(1));
                            txtNomBod.setText(vcoBod.getValueAt(2));
                        } else {
                            vcoBod.setCampoBusqueda(1);
                            vcoBod.setCriterio1(11);
                            vcoBod.cargarDatos();
                            vcoBod.show();
                            if (vcoBod.getSelectedButton() == vcoBod.INT_BUT_ACE) {
                                txtCodBod.setText(vcoBod.getValueAt(1));
                                txtNomBod.setText(vcoBod.getValueAt(2));
                            } else {
                                txtNomBod.setText(strNomBod);
                            }
                        }
                        break;
                }
            }

        } catch (Exception e) {
            blnRes = false;
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

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis() {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_EMP_REL:
                    strMsg = "Código de empresa relacionado";
                    break;
                case INT_TBL_DAT_COD_LOC_REL:
                    strMsg = "Código de local relacionado";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_REL:
                    strMsg = "Código de tipo de documento relacionado";
                    break;
                case INT_TBL_DAT_COD_DOC_REL:
                    strMsg = "Código de documento relacionado";
                    break;
                case INT_TBL_DAT_COD_REG_REL:
                    strMsg = "Código de registro relacionado";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código de item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código de item de empresa";
                    break;
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código maestro de item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno de item";
                    break;
                case INT_TBL_DAT_BUT_ANE_UNO:
                    strMsg = "Anexo 1";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre de Item";
                    break;
                case INT_TBL_DAT_DES_COR_UNI_MED:
                    strMsg = "Alias de unidad de Medida";
                    break;
                case INT_TBL_DAT_DES_LAR_UNI_MED:
                    strMsg = "Unidad de Medida";
                    break;
                case INT_TBL_DAT_CAN_ORD_DIS:
                    strMsg = "Cantidad disponible para distrbuir";
                    break;                    
                case INT_TBL_DAT_CAN_SEL:
                    strMsg = "Cantidad a que se va a distribuir a la bodega seleccionada";
                    break;
                case INT_TBL_DAT_CAN_SEL_AUX:
                    strMsg = "Cantidad a que se va a distribuir a la bodega seleccionada Auxiliar";
                    break;
                case INT_TBL_DAT_COD_REG:
                    strMsg = "Código del registro";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    private boolean isCamposCabecera(){
            try{
                if(txtCodTipDoc.getText().equals("")){
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
                    return false;
                }
                

                if(txtCodBod.getText().equals("")){
                    mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
                    return false;
                }
          
                
                

            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            return true;
        }
    

    private class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame ifrFrm) {
            super(ifrFrm, objParSis);
        }
        
        public boolean aceptar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterInsertar() {
            //Configurar JFrame de acuerdo al estado del registro.
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterModificar() {
            blnHayCam=false;
            objTooBar.setEstado('w');
            cargarReg();
            objTooBar.afterConsultar();
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean anular() {
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeAnular() {
            boolean blnRes=true;
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
            
            if (!isCamVal())
                blnRes=false;
            
            return blnRes;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeEliminar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")){
                mostrarMsgInf("El documento ya esta ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                blnRes=false;
            }
            
            if (!isCamVal())
                blnRes=false;
            
            return blnRes;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeInsertar() {
            boolean blnRes=true;
                if (!isCamVal())
                    blnRes=false;
            return blnRes;
        }

        public boolean beforeModificar() {
            boolean blnRes=true;
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                blnRes=false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                blnRes=false;
            }
            if (!isCamVal())
                blnRes=false;
            return blnRes;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public void clickAceptar() {
            
        }

        public void clickAnterior() {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.previous();
                            cargarReg();
                            
                        }
                    }
                    else{
                        rstCab.previous();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnular() {
            cargarDetReg();
        }

        public void clickCancelar() {
            
        }

        public void clickConsultar() {
            
        }

        public void clickEliminar() {
            
        }

        public void clickFin() {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.last();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.last();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir() {
            
        }

        public void clickInicio() {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.first();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.first();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try{
                if (blnHayCam){
                    isRegPro();
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                limpiarFrm();
                txtCodDoc.setEditable(false);
//                txtMonDoc.setEditable(false);
                datFecAux=null;
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                //datFecAux=null;
                mostrarTipDocPre();
                
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                txtNumDoc.selectAll();
                txtNumDoc.requestFocus();
                cargarPedidoEmbarcado();
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
        }

        public void clickModificar() {
            txtDesCorTipDoc.setEditable(false);
            txtDesLarTipDoc.setEditable(false);
            butTipDoc.setEnabled(false);
            txtNumDoc.selectAll();
            txtNumDoc.requestFocus();
            txtCodDoc.setEditable(false);
            cboPedSel.setEnabled(false);
            
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT_ANE_UNO);
            vecAux.add("" + INT_TBL_DAT_CAN_SEL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);//para permitir eliminar registros e insertar registros
            arlDatFilEli.clear();
            
        }

        public void clickSiguiente() {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam){
                        if (isRegPro()){
                            rstCab.next();
                            cargarReg();
                        }
                    }
                    else{
                        rstCab.next();
                        cargarReg();
                    }
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickVisPreliminar() {
            
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean eliminar() {
            try{
                if (!eliminarReg())
                    return false;
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()){
                    rstCab.next();
                    cargarReg();
                }
                else{
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam=false;
            }
            catch (java.sql.SQLException e){
                return true;
            }
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean insertar() {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar() {
            if (!actualizarReg())
                return false;
            return true;
        }

        public boolean vistaPreliminar() {
            if (objThrGUIVisPre==null){
                objThrGUIVisPre=new ZafThreadGUIVisPre();
                objThrGUIVisPre.setIndFunEje(1);
                objThrGUIVisPre.start();
            }
            return true;
        }
 
        
    }
    
    
    /**
     * Esta funcián muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocPrg";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc, a1.tx_natDoc";
                    strSQL+=", CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND a1.co_tipDoc=";
                    strSQL+=" (";
                    strSQL+=" SELECT co_tipDoc";
                    strSQL+=" FROM tbr_tipDocUsr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+=" AND st_reg='S'";
                    strSQL+=" )";
                }
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    txtCodTipDoc.setText(rst.getString("co_tipDoc"));
                    txtDesCorTipDoc.setText(rst.getString("tx_desCor"));
                    txtDesLarTipDoc.setText(rst.getString("tx_desLar"));
                    txtNumDoc.setText("" + (rst.getInt("ne_ultDoc")+1));
                    intSig=(rst.getString("tx_natDoc").equals("I")?1:-1);
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
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="áDesea guardar los cambios efectuados a áste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la informacián que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
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
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodBod.setText("");
            txtNomBod.setText("");
            dtpFecDoc.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
            cboPedSel.removeAllItems();
            arlDatPed.clear();
            intCboPedSelCodEmp=0;
            intCboPedSelCodLoc=0;
            intCboPedSelCodTipDoc=0;
            intCboPedSelCodDoc=0;
            arlDatFilEli.clear();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean cargarDatosAnexo(int fila){
        boolean blnRes=true;
        int intTam=arlDat.size();
        int intFil=fila;
        try{
            System.out.println("arlDat: " + arlDat.toString());
            
            for(int i=0; i<arlDat.size(); i++){
                objTblMod.insertRow(intFil);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_EMP_REL), intFil, INT_TBL_DAT_COD_EMP_REL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_LOC_REL), intFil, INT_TBL_DAT_COD_LOC_REL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_TIP_DOC_REL), intFil, INT_TBL_DAT_COD_TIP_DOC_REL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_DOC_REL), intFil, INT_TBL_DAT_COD_DOC_REL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_REG_REL), intFil, INT_TBL_DAT_COD_REG_REL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ITM_GRP), intFil, INT_TBL_DAT_COD_ITM_GRP);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ITM_EMP), intFil, INT_TBL_DAT_COD_ITM_EMP);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ITM_MAE), intFil, INT_TBL_DAT_COD_ITM_MAE);                
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_COD_ALT_ITM), intFil, INT_TBL_DAT_COD_ALT_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_NOM_ITM), intFil, INT_TBL_DAT_NOM_ITM);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_DES_COR_UNI_MED), intFil, INT_TBL_DAT_DES_COR_UNI_MED);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_DES_LAR_UNI_MED), intFil, INT_TBL_DAT_DES_LAR_UNI_MED);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_CAN_ORD_SEL), intFil, INT_TBL_DAT_CAN_SEL);
                objTblMod.setValueAt(objUti.getStringValueAt(arlDat, i, INT_ARL_CAN_DIS), intFil, INT_TBL_DAT_CAN_ORD_DIS);
                intFil++;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
        
    }


    /**
     * Esta funcián inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                con.setAutoCommit(false);
                if(txtCodBod.getText().length()>0){//se genera una orden de distribucion para la bodega de INMACONSA
                    
                    if (insertar_tbmCabOrdDis()){
                        if (insertar_tbmDetOrdDis()){
                            if(actualizar_tbmCabTipDoc()){
                                if(chkCerDisImp.isSelected()){
                                    if (insertar_tbmCabOrdDis_Inmaconsa()){
                                        if (insertar_tbmDetOrdDis_Inmaconsa()){
                                            if(actualizar_tbmCabTipDoc()){
                                                con.commit();
                                                blnRes=true;
                                            }
                                            else{
                                                con.commit();
                                                blnRes=true;
                                            }
                                        }
                                        else{
                                            con.commit();
                                            blnRes=true;
                                        }
                                    }
                                    else{
                                        con.commit();
                                        blnRes=true;
                                    }
                                }
                                else{
                                    con.commit();
                                    blnRes=true;
                                }
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
                else{
                    if(chkCerDisImp.isSelected()){
                        if (insertar_tbmCabOrdDis_Inmaconsa()){
                            if (insertar_tbmDetOrdDis_Inmaconsa()){
                                if(actualizar_tbmCabTipDoc()){
                                    con.commit();
                                    blnRes=true;
                                }
                                else{
                                    con.commit();
                                    blnRes=true;
                                }
                            }
                            else{
                                con.commit();
                                blnRes=true;
                            }
                        }
                        else{
                            con.commit();
                            blnRes=true;
                        }
                    }
                    else{
                        con.commit();
                        blnRes=true;
                    }
                }
                
                


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
    
    private boolean insertar_tbmCabOrdDis(){
        boolean blnRes=true;
        int intUltReg;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_caborddis AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intUltReg=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                }
                else{
                    intUltReg=0;
                }
                intUltReg++;
                
                txtCodDoc.setText(""+intUltReg);
                strSQL="";
                strSQL+="INSERT INTO tbm_caborddis(";
                strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, ";
                strSQL+=" tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ";
                strSQL+=" st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL+="" + intUltReg + ",";//co_doc
                strSQL+="'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="" + txtNumDoc.getText() + ",";//ne_numdoc
                strSQL+="" + txtCodBod.getText() + ", ";//co_bod
                strSQL+=" Null, ";//tx_obs1
                strSQL+=" Null,";//tx_obs2
                strSQL+="'A',";//st_reg
                strSQL+="CURRENT_TIMESTAMP,";//fe_ing
                strSQL+="CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="'I'";//st_regrep
                strSQL+=");";
                System.out.println("insertar_tbmCabOrdDis: " + strSQL);
                stm.executeUpdate(strSQL);
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
    
    
    private boolean insertar_tbmCabOrdDis_Inmaconsa(){
        boolean blnRes=true;
        int intUltReg;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc) AS co_doc";
                strSQL+=" FROM tbm_caborddis AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intUltReg=rst.getObject("co_doc")==null?0:rst.getInt("co_doc");
                }
                else{
                    intUltReg=0;
                }
                intUltReg++;
                
                txtCodDoc.setText(""+intUltReg);
                strSQL="";
                strSQL+="INSERT INTO tbm_caborddis(";
                strSQL+=" co_emp, co_loc, co_tipdoc, co_doc, fe_doc, ne_numdoc, co_bod, ";
                strSQL+=" tx_obs1, tx_obs2, st_reg, fe_ing, fe_ultmod, co_usring, co_usrmod, ";
                strSQL+=" st_regrep)";
                strSQL+=" VALUES (";
                strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                strSQL+="" + intUltReg + ",";//co_doc
                strSQL+="'" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "',";//fe_doc
                strSQL+="" + txtNumDoc.getText() + ",";//ne_numdoc
                strSQL+="15, ";//co_bod
                strSQL+=" Null, ";//tx_obs1
                strSQL+=" 'Se cerró la distribución el: '" + " || CURRENT_TIMESTAMP,";//tx_obs2
                strSQL+="'A',";//st_reg
                strSQL+="CURRENT_TIMESTAMP,";//fe_ing
                strSQL+="CURRENT_TIMESTAMP,";//fe_ultmod
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usring
                strSQL+="" + objParSis.getCodigoUsuario() + ",";//co_usrmod
                strSQL+="'I'";//st_regrep
                strSQL+=");";
                strSQL+="UPDATE tbm_cabMovInv";
                strSQL+=" SET st_cieDis='S'";
                strSQL+=" WHERE co_emp=" + intCboPedSelCodEmp + "";
                strSQL+=" AND co_loc=" + intCboPedSelCodLoc + "";
                strSQL+=" AND co_tipDoc=" + intCboPedSelCodTipDoc + "";
                strSQL+=" AND co_doc=" + intCboPedSelCodDoc + "";
                strSQL+=";";
                System.out.println("insertar_tbmCabOrdDis_Inmaconsa: " + strSQL);
                stm.executeUpdate(strSQL);
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
    
    
    
    
    private boolean insertar_tbmDetOrdDis(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bdeCanDis=new BigDecimal("0");
        BigDecimal bdeCanSel=new BigDecimal("0");
        int intCodItmMaeSel=0;
        int j=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    bdeCanDis=new BigDecimal("0");
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detorddis(";
                    strSQL+=" co_emp, co_loc, co_tipdoc,";
                    strSQL+=" co_doc, co_reg, co_itm, nd_can";
                    strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + ",";//co_emp
                    strSQL+="" + objParSis.getCodigoLocal() + ",";//co_loc
                    strSQL+="" + txtCodTipDoc.getText() + ",";//co_tipdoc
                    strSQL+="" + txtCodDoc.getText() + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP) + ",";//co_itm
                    intCodItmMaeSel=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                    bdeCanDis=isCantidadDisponible(intCodItmMaeSel);
                    bdeCanSel=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString())   );
                    
                    if(bdeCanDis.compareTo(bdeCanSel)<0){
                        mostrarMsgInf("<HTML>El item " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM) + " no tiene disponible la cantidad seleccionada.<BR>Verifique y vuelva a intentarlo</HTML>");
                        blnRes=false;
                        break;
                    }                    
                    strSQL+="" + bdeCanSel + ",";//nd_can
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL) + ",";//co_empRel
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL) + ",";//co_locRel
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL) + ",";//co_tipDocRel
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL) + ",";//co_docRel
                    strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL) + "";//co_regRel
                    strSQL+=");";
//                    strSQL+="UPDATE tbm_detMovInv";
//                    strSQL+=" SET nd_canuti=(CASE WHEN nd_canuti IS NULL THEN 0 ELSE nd_canuti END) + " + bdeCanSel + "";
//                    strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL) + "";
//                    strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL) + "";
//                    strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                    strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL) + "";
//                    strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL) + ";";
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertar_tbmDetOrdDis: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
   
    
    private boolean insertar_tbmDetOrdDis_Inmaconsa(){
        boolean blnRes=true;
        String strSQLUpd="";
        int j=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT " + objParSis.getCodigoEmpresa() + " AS co_emp,";//co_emp
                strSQL+="" + objParSis.getCodigoLocal() + " AS co_loc,";//co_loc
                strSQL+="" + txtCodTipDoc.getText() + " AS co_tipdoc,";//co_tipdoc
                strSQL+="" + txtCodDoc.getText() + " AS co_doc,";//co_doc
                strSQL+="0 AS co_reg,";//co_reg
                strSQL+=" b1.co_itmGrp,";//co_itm                             
                strSQL+=" b1.nd_canPenInm,";//nd_can
                strSQL+=" b1.co_empRel,";//co_empRel
                strSQL+=" b1.co_locRel,";//co_locRel
                strSQL+=" b1.co_tipDocRel,";//co_tipDocRel
                strSQL+=" b1.co_docRel,";//co_docRel
                strSQL+=" b1.co_regRel";//co_regRel                                        
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_emp AS co_empRel, b1.co_loc AS co_locRel, b1.co_tipDoc AS co_tipDocRel, b1.co_doc AS co_docRel, b1.co_reg AS co_regRel";
                strSQL+=" 	, b1.co_itm AS co_itmEmp, b1.co_itmGrp, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae";
                strSQL+=" 	, b1.nd_canPedImp AS nd_canPedImp, (CASE WHEN b2.nd_canOrdUti IS NULL THEN 0 ELSE b2.nd_canOrdUti  END  )";
                strSQL+=" 	, b1.nd_canPedImp - (CASE WHEN b2.nd_canOrdUti IS NULL THEN 0 ELSE b2.nd_canOrdUti  END  )  AS nd_canPenInm";
                strSQL+=" 	FROM(";
                strSQL+=" 	       SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.co_itmGrp, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae, b1.nd_canPedImp";
                strSQL+=" 	       FROM(";   
                

                        strSQL+="       SELECT b1.*, b2.co_itmGrp FROM(";

                        strSQL+=" 		       SELECT a7.co_emp, a7.co_loc, a7.co_tipDoc, a7.co_doc, a8.co_reg, a8.co_itm, a6.co_itmMae";
                        strSQL+=" 		       , a4.tx_codAlt, a4.tx_nomItm, a8.nd_can AS nd_canPedImp";
                        strSQL+=" 		       FROM tbm_cabMovInv AS a7 INNER JOIN  tbm_detMovInv AS a8";
                        strSQL+=" 		       ON(a7.co_emp=a8.co_emp AND a7.co_loc=a8.co_loc AND a7.co_tipDoc=a8.co_tipDoc AND a7.co_doc=a8.co_doc )";
                        strSQL+=" 		       INNER JOIN tbm_inv AS a4 ON(a8.co_emp=a4.co_emp AND a8.co_itm=a4.co_itm)";
                        strSQL+=" 		       INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                        strSQL+=" 		       WHERE a7.co_emp=" + intCboPedSelCodEmp + "";
                        strSQL+=" 		       AND a7.co_loc=" + intCboPedSelCodLoc + "";
                        strSQL+=" 		       AND a7.co_tipDoc=" + intCboPedSelCodTipDoc + "";
                        strSQL+=" 		       AND a7.co_doc=" + intCboPedSelCodDoc + "";
                        strSQL+=" 		       AND a7.st_reg='A'";
                        strSQL+="		       ORDER BY a7.co_emp, a7.co_loc, a7.co_tipDoc, a7.co_doc, a8.co_reg";


                        strSQL+="       ) AS b1";
                        strSQL+="       INNER JOIN";
                        strSQL+="                (";
                        strSQL+="                         SELECT a6.co_itm AS co_itmGrp, a6.co_itmMae FROM tbm_inv AS a4 INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                        strSQL+="                         WHERE a6.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        strSQL+="                 ) AS b2";
                        strSQL+="                 ON b1.co_itmMae=b2.co_itmMae";

                
                
                strSQL+=" 	       ) AS b1";
                strSQL+=" 	       GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.co_itmGrp, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae, b1.nd_canPedImp";
                               
                
                
                strSQL+=" 	) AS b1";
                strSQL+=" 	LEFT OUTER JOIN(";
                strSQL+=" 	       SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel, a2.co_itm, a6.co_itmMae, SUM(a2.nd_can) AS nd_canOrdUti";
                strSQL+=" 	       FROM tbm_caborddis AS a1 INNER JOIN tbm_detorddis AS a2";
                strSQL+=" 	       ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQL+=" 	       INNER JOIN tbm_inv AS a4 ON(a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                strSQL+=" 	       INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+=" 	       WHERE a2.co_empRel=" + intCboPedSelCodEmp + "";
                strSQL+=" 	       AND a2.co_locRel=" + intCboPedSelCodLoc + "";
                strSQL+=" 	       AND a2.co_tipDocRel=" + intCboPedSelCodTipDoc + "";
                strSQL+=" 	       AND a2.co_docRel=" + intCboPedSelCodDoc + "";
                strSQL+=" 	       AND a1.st_reg='A'";
                strSQL+=" 	       GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel, a2.co_itm, a6.co_itmMae";
                strSQL+=" 	) AS b2";
                strSQL+=" 	ON b1.co_emp=b2.co_empRel AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                strSQL+=" 	GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae, b1.co_itmGrp";
                strSQL+=" 	, b1.nd_canPedImp, b2.nd_canOrdUti";
                strSQL+=" ) AS b1";
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    j++;
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detorddis(";
                    strSQL+=" co_emp, co_loc, co_tipdoc,";
                    strSQL+=" co_doc, co_reg, co_itm, nd_can";
                    strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                    strSQL+=" VALUES(";
                    strSQL+="" + rst.getString("co_emp") + ",";//co_emp
                    strSQL+="" + rst.getString("co_loc") + ",";//co_loc
                    strSQL+="" + rst.getString("co_tipdoc") + ",";//co_tipdoc
                    strSQL+="" + rst.getString("co_doc") + ",";//co_doc
                    strSQL+="" + j + ",";//co_reg
                    strSQL+="" + rst.getString("co_itmGrp") + ",";//co_itm
                    strSQL+="" + rst.getString("nd_canPenInm") + ",";//nd_can
                    strSQL+="" + rst.getString("co_empRel") + ",";//co_emprel
                    strSQL+="" + rst.getString("co_locRel") + ",";//co_locrel
                    strSQL+="" + rst.getString("co_tipDocRel") + ",";//co_tipdocrel
                    strSQL+="" + rst.getString("co_docRel") + ",";//co_docrel
                    strSQL+="" + rst.getString("co_regRel") + "";//co_regrel
                    strSQL+=");";
                    strSQLUpd+=strSQL;
                }
                System.out.println("insertar_tbmDetOrdDis_Inmconsa: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
   
    
    
    
    
    private BigDecimal isCantidadDisponible(int codItmMae){
        boolean blnRes=true;
        String strSQLIsCanDis="";
        Statement stmIsCanDis;
        ResultSet rstIsCanDis;
        BigDecimal bdeCanDisItmSel=new BigDecimal("0");
        try{
            
            if(con!=null){
                stmIsCanDis=con.createStatement();
                strSQLIsCanDis="";
//                strSQLIsCanDis+="   SELECT b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae";
//                strSQLIsCanDis+="   , b1.nd_canPedImp - (CASE WHEN SUM(b1.nd_canOrdUti) IS NULL THEN 0 ELSE SUM(b1.nd_canOrdUti)  END  )  AS nd_canOrdDis";
//                strSQLIsCanDis+="   , b1.nd_canPedImp AS nd_canPedImp, b1.co_regPed FROM(";
//                strSQLIsCanDis+=" 	SELECT a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel, a8.co_itm, a2.co_reg, a6.co_itmMae";
//                strSQLIsCanDis+=" 	, a4.tx_codAlt, a4.tx_nomItm, a5.tx_desCor, a5.tx_desLar, a8.co_reg AS co_regPed";
//                strSQLIsCanDis+="  	 , a2.nd_can AS nd_canOrdUti, a8.nd_can AS nd_canPedImp";
//                strSQLIsCanDis+="  	 FROM tbm_cabMovInv AS a7 INNER JOIN  tbm_detMovInv AS a8";
//                strSQLIsCanDis+="  	 ON(a7.co_emp=a8.co_emp AND a7.co_loc=a8.co_loc AND a7.co_tipDoc=a8.co_tipDoc AND a7.co_doc=a8.co_doc )";
//                strSQLIsCanDis+="  	 LEFT OUTER JOIN tbm_caborddis AS a1 ";
//                strSQLIsCanDis+="  	 ON(a7.co_emp=a1.co_empRel AND a7.co_loc=a1.co_locRel AND a7.co_tipDoc=a1.co_tipDocRel AND a7.co_doc=a1.co_docRel AND a1.st_reg='A')";
//                strSQLIsCanDis+=" 	 LEFT OUTER JOIN tbm_detorddis AS a2";
//                strSQLIsCanDis+=" 	 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc AND a2.co_itm=a8.co_itm";
//                strSQLIsCanDis+="  	 INNER JOIN (tbm_invBod AS a3 INNER JOIN";
//                strSQLIsCanDis+="  						 (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg) ";
//                strSQLIsCanDis+="  								     INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
//                strSQLIsCanDis+="  								ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm)";
//                strSQLIsCanDis+="  	 ON a8.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod AND a8.co_itm=a3.co_itm";
//                strSQLIsCanDis+=" 	 WHERE a7.co_emp=" + intCboPedSelCodEmp + "";
//                strSQLIsCanDis+=" 	 AND a7.co_loc=" + intCboPedSelCodLoc + "";
//                strSQLIsCanDis+=" 	 AND a7.co_tipDoc=" + intCboPedSelCodTipDoc + "";
//                strSQLIsCanDis+=" 	 AND a7.co_doc=" + intCboPedSelCodDoc + "";
//                strSQLIsCanDis+=" 	 AND a6.co_itmMae=" + codItmMae + "";
//                strSQLIsCanDis+=" 	 AND a7.st_reg='A'";
//                strSQLIsCanDis+="  	 GROUP BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel,a2.co_reg";
//                strSQLIsCanDis+="  	 , a8.co_itm, a6.co_itmMae, a4.tx_codAlt, a4.tx_nomItm, a5.tx_desCor, a5.tx_desLar, a2.nd_can, a8.nd_can, a8.co_reg";
//                strSQLIsCanDis+="  	 ORDER BY a1.co_empRel, a1.co_locRel, a1.co_tipDocRel, a1.co_docRel";
//                strSQLIsCanDis+=") AS b1";
//                strSQLIsCanDis+=" GROUP BY b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_itm, b1.co_itmMae, b1.nd_canPedImp, b1.tx_codAlt, b1.tx_nomItm, b1.co_regPed";
                
                
                strSQLIsCanDis+="       SELECT b1.co_emp AS co_empRel, b1.co_loc AS co_locRel, b1.co_tipDoc AS co_tipDocRel, b1.co_doc AS co_docRel, b1.co_reg AS co_regRel";
                strSQLIsCanDis+="       , b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae";
                strSQLIsCanDis+="       , b1.nd_canPedImp - (CASE WHEN SUM(b2.nd_canOrdUti) IS NULL THEN 0 ELSE SUM(b2.nd_canOrdUti)  END  )  AS nd_canOrdDis";
                strSQLIsCanDis+="       , b1.nd_canPedImp AS nd_canPedImp";
                strSQLIsCanDis+="       FROM(";
                strSQLIsCanDis+="               SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae, b1.nd_canPedImp";
                strSQLIsCanDis+="               FROM(";
                strSQLIsCanDis+="                       SELECT a7.co_emp, a7.co_loc, a7.co_tipDoc, a7.co_doc, a8.co_reg, a8.co_itm, a6.co_itmMae";
                strSQLIsCanDis+="                       , a4.tx_codAlt, a4.tx_nomItm, a8.nd_can AS nd_canPedImp";
                strSQLIsCanDis+="                       FROM tbm_cabMovInv AS a7 INNER JOIN  tbm_detMovInv AS a8";
                strSQLIsCanDis+="                       ON(a7.co_emp=a8.co_emp AND a7.co_loc=a8.co_loc AND a7.co_tipDoc=a8.co_tipDoc AND a7.co_doc=a8.co_doc )";
                strSQLIsCanDis+="                       INNER JOIN tbm_inv AS a4 ON(a8.co_emp=a4.co_emp AND a8.co_itm=a4.co_itm)";
                strSQLIsCanDis+="                       INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQLIsCanDis+="                       WHERE a7.co_emp=" + intCboPedSelCodEmp + "";
                strSQLIsCanDis+="                       AND a7.co_loc=" + intCboPedSelCodLoc + "";
                strSQLIsCanDis+="                       AND a7.co_tipDoc=" + intCboPedSelCodTipDoc + "";
                strSQLIsCanDis+="                       AND a7.co_doc=" + intCboPedSelCodDoc + "";
                strSQLIsCanDis+="                       AND a6.co_itmMae=" + codItmMae + "";
                strSQLIsCanDis+="                       AND a7.st_reg='A'";
                strSQLIsCanDis+="                       ORDER BY a7.co_emp, a7.co_loc, a7.co_tipDoc, a7.co_doc, a8.co_reg";
                strSQLIsCanDis+="               ) AS b1";
                strSQLIsCanDis+="               GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae, b1.nd_canPedImp";
                strSQLIsCanDis+="       ) AS b1";
                strSQLIsCanDis+="       LEFT OUTER JOIN(";
                strSQLIsCanDis+="               SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel, a2.co_itm, a6.co_itmMae, a2.nd_can AS nd_canOrdUti";
                strSQLIsCanDis+="               FROM tbm_caborddis AS a1 INNER JOIN tbm_detorddis AS a2";
                strSQLIsCanDis+="               ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc AND a2.co_doc=a1.co_doc";
                strSQLIsCanDis+="               INNER JOIN tbm_inv AS a4 ON(a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm)";
                strSQLIsCanDis+="               INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQLIsCanDis+="               WHERE a2.co_empRel=" + intCboPedSelCodEmp + "";
                strSQLIsCanDis+="               AND a2.co_locRel=" + intCboPedSelCodLoc + "";
                strSQLIsCanDis+="               AND a2.co_tipDocRel=" + intCboPedSelCodTipDoc + "";
                strSQLIsCanDis+="               AND a2.co_docRel=" + intCboPedSelCodDoc + "";
                strSQLIsCanDis+="               AND a1.st_reg='A'";
                strSQLIsCanDis+="       ) AS b2";
                strSQLIsCanDis+="       ON b1.co_emp=b2.co_empRel AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel AND b1.co_reg=b2.co_regRel";
                strSQLIsCanDis+="       GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_reg, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae";
                strSQLIsCanDis+="       , b1.nd_canPedImp";
                System.out.println("isCantidadDisponible:  " + strSQLIsCanDis);
                rstIsCanDis=stmIsCanDis.executeQuery(strSQLIsCanDis);
                if(rstIsCanDis.next())
                    bdeCanDisItmSel=rstIsCanDis.getBigDecimal("nd_canOrdDis");
                stmIsCanDis.close();
                stmIsCanDis=null;
                rstIsCanDis.close();
                rstIsCanDis=null;
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
        return bdeCanDisItmSel;
    }
    
    
    
    private boolean actualizar_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+=" SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                System.out.println("actualizar_tbmCabTipDoc: " + strSQL);
                stm.executeUpdate(strSQL);
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
    
    
    /**
     * Esta funcián obtiene la descripcián larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripcián larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la funcián devuelve una cadena vacáa.
     */
    private String getEstReg(String estado){
        if (estado==null)
            estado="";
        else
            switch (estado.charAt(0)){
                case 'A':
                    estado="Activo";
                    break;
                case 'I':
                    estado="Anulado";
                    break;
                case 'E':
                    estado="Eliminado";
                    break;
                case 'R':
                    estado="Pendiente de impresión";
                    break;
                default:
                    estado="Desconocido";
                    break;
            }
            
        return estado;
    }
    
    
    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            txtCodBod.setText("");
            txtNomBod.setText("");
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (txtCodTipDoc.getText().equals(""))
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_caborddis AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu();
                }
                else
                {
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_caborddis AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc=" + strAux + "";
                
                strAux=txtCodBod.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_bod=" + strAux + "";
                                
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";

                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc=" + strAux + "";
                
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc=" + strAux + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg: " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ningán registro que cumpla el criterio de básqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta funcián permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        blnIsRegEli=false;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    if(cargarPedidoEmbarcado()){

                    }
                }
            }
            blnHayCam=false;
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg(){
        int intPosRel;
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc,";
                strSQL+=" a1.tx_obs1, a1.tx_obs2, a1.st_reg, b1.co_bod, b1.tx_nom AS tx_nomBod";
                strSQL+=" , a3.co_empRel, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel";
                strSQL+=" FROM tbm_caborddis AS a1";
                strSQL+=" INNER JOIN tbm_detorddis AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc AND a1.co_doc=a3.co_doc";
                strSQL+=" INNER JOIN tbm_bod AS b1 ON(a1.co_emp=b1.co_emp AND a1.co_bod=b1.co_bod)";
                strSQL+="                             INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";                
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" GROUP BY a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc, a1.tx_obs1, a1.tx_obs2, a1.st_reg";
                strSQL+=" , b1.co_bod, b1.tx_nom, a3.co_empRel, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel";
                System.out.println("cargarCabReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_natDoc");
                    intSig=(strAux.equals("I")?1:-1);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_bod");
                    txtCodBod.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBod");
                    txtNomBod.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    
                    intCboPedSelCodEmp=rst.getInt("co_empRel");
                    intCboPedSelCodLoc=rst.getInt("co_locRel");
                    intCboPedSelCodTipDoc=rst.getInt("co_tipDocRel");
                    intCboPedSelCodDoc=rst.getInt("co_docRel");                   
                    
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    if(objTooBar.getEstadoRegistro().equals("Eliminado")){
                        limpiarFrm();
                        blnIsRegEli=true;
                    }
                }
                else
                {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Mostrar la posicián relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta funcián permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT c1.co_empRel, c1.co_locRel, c1.co_tipDocRel, c1.co_docRel, c1.co_regRel, c1.co_itm AS co_itmGrp, c2.co_itm AS co_itmEmp, c1.co_itmMae, c1.tx_codAlt";
                strSQL+=" , c1.tx_nomItm, c1.tx_desCor, c1.tx_desLar, c1.nd_can, c1.co_reg, c1.nd_canPed, c2.nd_canOrdUti, (c1.nd_canPed - c2.nd_canOrdUti) AS nd_canOrdDis";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_regRel";
                strSQL+=" 	 , b1.co_itm, b1.co_itmMae, b1.tx_codAlt, b1.tx_nomItm, b1.tx_desCor, b1.tx_desLar, b1.nd_can, b1.co_reg, b1.nd_canPed";
                strSQL+=" 	 FROM(";
                strSQL+=" 		 SELECT a7.co_emp AS co_empRel, a7.co_loc AS co_locRel, a7.co_tipDoc AS co_tipDocRel, a7.co_doc AS co_docRel, a7.co_reg AS co_regRel";
                strSQL+=" 		    , a2.co_itm, a6.co_itmMae, a4.tx_codAlt, a4.tx_nomItm, a5.tx_desCor, a5.tx_desLar, a2.nd_can";
                strSQL+=" 		    , a2.co_reg, a7.nd_can AS nd_canPed";
                strSQL+=" 		    FROM tbm_caborddis AS a1 INNER JOIN ";
                strSQL+=" 			   (tbm_detorddis AS a2";
                strSQL+=" 				   INNER JOIN (tbm_inv AS a4 INNER JOIN tbm_var AS a5 ON a4.co_uni=a5.co_reg)";
                strSQL+=" 				   ON a2.co_emp=a4.co_emp AND a2.co_itm=a4.co_itm";
                strSQL+=" 				   INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+=" 			   )";
                strSQL+=" 			ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 			INNER JOIN tbm_detMovInv AS a7 ON a2.co_empRel=a7.co_emp AND a2.co_locRel=a7.co_loc AND a2.co_tipDocRel=a7.co_tipDoc AND a2.co_docRel=a7.co_doc AND a2.co_regRel=a7.co_reg";
                strSQL+=" 			INNER JOIN tbm_cabMovInv AS a8 ON a7.co_emp=a8.co_emp AND a7.co_loc=a8.co_loc AND a7.co_tipDoc=a8.co_tipDoc AND a7.co_doc=a8.co_doc";
                strSQL+=" 		 WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" 		 AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" 		 AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" 		 AND a1.co_doc=" + rstCab.getString("co_doc");
                strSQL+=" 		 ORDER BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.co_reg";
                strSQL+=" 	) AS b1";
                strSQL+=" ) AS c1";
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_regRel, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm ,b1.co_itmMae";
                strSQL+=" 	, (CASE WHEN SUM(b1.nd_canOrdUti) IS NULL THEN 0 ELSE SUM(b1.nd_canOrdUti)  END  )  AS nd_canOrdUti";
                strSQL+=" 	, b1.nd_canPedImp AS nd_canPedImp FROM(";
                strSQL+=" 		SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 		, a8.co_itm, a6.co_itmMae, a4.tx_codAlt, a4.tx_nomItm";
                strSQL+=" 		 , a2.nd_can AS nd_canOrdUti, a8.nd_can AS nd_canPedImp";
                strSQL+=" 		 FROM tbm_cabMovInv AS a7 INNER JOIN  (tbm_detMovInv AS a8";
                strSQL+=" 							INNER JOIN tbm_inv AS a4 ";
                strSQL+=" 							ON a8.co_emp=a4.co_emp AND a8.co_itm=a4.co_itm";
                strSQL+=" 							INNER JOIN tbm_equInv AS a6 ON a4.co_emp=a6.co_emp AND a4.co_itm=a6.co_itm";
                strSQL+="							)";
                strSQL+=" 		 ON(a7.co_emp=a8.co_emp AND a7.co_loc=a8.co_loc AND a7.co_tipDoc=a8.co_tipDoc AND a7.co_doc=a8.co_doc )";
                strSQL+=" 		 INNER JOIN (tbm_detorddis AS a2";
                strSQL+=" 					INNER JOIN tbm_caborddis AS a1 ";
                strSQL+=" 					ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc  ";
                strSQL+="				)";
                strSQL+=" 		 ON(a8.co_emp=a2.co_empRel AND a8.co_loc=a2.co_locRel AND a8.co_tipDoc=a2.co_tipDocRel AND a8.co_doc=a2.co_docRel AND a8.co_reg=a2.co_regRel)";
                strSQL+=" 		 WHERE a2.co_empRel=" + intCboPedSelCodEmp;
                strSQL+="		 AND a2.co_locRel=" + intCboPedSelCodLoc;
                strSQL+=" 		 AND a2.co_tipDocRel=" + intCboPedSelCodTipDoc;
                strSQL+=" 		 AND a2.co_docRel=" + intCboPedSelCodDoc;
                strSQL+=" 		 AND a7.st_reg='A' AND a1.st_reg='A'";
                strSQL+=" 		 GROUP BY a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 		 , a8.co_itm, a6.co_itmMae, a4.tx_codAlt, a4.tx_nomItm, a2.nd_can, a8.nd_can, a8.co_reg";
                strSQL+=" 		 ORDER BY a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel, a2.co_regRel";
                strSQL+=" 	) AS b1";
                strSQL+=" 	GROUP BY b1.co_empRel, b1.co_locRel, b1.co_tipDocRel, b1.co_docRel, b1.co_regRel, b1.co_itm, b1.co_itmMae";
                strSQL+=" 	, b1.nd_canPedImp, b1.tx_codAlt, b1.tx_nomItm";
                strSQL+=" ) AS c2";
                strSQL+=" ON c1.co_empRel=c2.co_empRel AND c1.co_locRel=c2.co_locRel AND c1.co_tipDocRel=c2.co_tipDocRel AND c1.co_docRel=c2.co_docRel AND c1.co_regRel=c2.co_regRel";
                strSQL+=" ORDER BY c1.co_reg";
                System.out.println("EN CARGARDETREG: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,                 "");
                    vecReg.add(INT_TBL_DAT_COD_EMP_REL,         "" + rst.getString("co_empRel"));
                    vecReg.add(INT_TBL_DAT_COD_LOC_REL,         "" + rst.getString("co_locRel"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC_REL,     "" + rst.getString("co_tipDocRel"));
                    vecReg.add(INT_TBL_DAT_COD_DOC_REL,         "" + rst.getString("co_docRel"));
                    vecReg.add(INT_TBL_DAT_COD_REG_REL,         "" + rst.getString("co_regRel"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_GRP,         "" + rst.getString("co_itmGrp"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_EMP,         "" + rst.getString("co_itmEmp"));
                    vecReg.add(INT_TBL_DAT_COD_ITM_MAE,         "" + rst.getString("co_itmMae"));
                    vecReg.add(INT_TBL_DAT_COD_ALT_ITM,         "" + rst.getString("tx_codAlt"));
                    vecReg.add(INT_TBL_DAT_BUT_ANE_UNO,"");
                    vecReg.add(INT_TBL_DAT_NOM_ITM,             "" + rst.getString("tx_nomItm"));
                    vecReg.add(INT_TBL_DAT_DES_COR_UNI_MED,     "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_UNI_MED,     "" + rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_CAN_ORD_DIS,         ""  + rst.getString("nd_canOrdDis"));
                    vecReg.add(INT_TBL_DAT_CAN_SEL,             ""  + rst.getString("nd_can"));                    
                    vecReg.add(INT_TBL_DAT_CAN_SEL_AUX,         ""  + rst.getString("nd_can"));
                    vecReg.add(INT_TBL_DAT_COD_REG,             ""  + rst.getString("co_reg"));
                    
                    vecDat.add(vecReg);
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
                if(blnIsRegEli){
                    objTblMod.removeAllRows();
                }
                vecDat.clear();

                objTblMod.initRowsState();
                
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
     * Esta funcián actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if(actualizar_tbmCabOrdDis()){
                    if(actualizar_tbmDetOrdDis()){
                        con.commit();
                        blnRes=true;
                    }
                    else
                        con.rollback();             
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funcián permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabOrdDis(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strAux=objParSis.getFuncionFechaHoraBaseDatos();
                strSQL="";
                strSQL+="UPDATE tbm_caborddis";
                strSQL+=" SET fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", ne_numDoc=" + objUti.codificar(txtNumDoc.getText(),2);
                strSQL+=", co_bod=" + txtCodBod.getText();
                strSQL+=", tx_obs1=" + objUti.codificar(txaObs1.getText());
                strSQL+=", tx_obs2=" + objUti.codificar(txaObs2.getText());
                strSQL+=", fe_ultMod=" + strAux + "";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta funcián permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmConInvModifica(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_conInv";
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_locRel=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDocRel=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_docRel=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta funcián anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                con.setAutoCommit(false);
                if (anular_tbmCabOrdDis()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
            }
            con.close();
            con=null;
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    /**
     * Esta funcián permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabOrdDis(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabOrdDis";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
                datFecAux=null;
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
     * Esta funcián elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                con.setAutoCommit(false);
                if (eliminar_tbmCabOrdDis()){
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
     * Esta funcián permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabOrdDis(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabOrdDis";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod=CURRENT_TIMESTAMP";
                strSQL+=", co_usrMod=" + objParSis.getCodigoUsuario();
                strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND co_doc=" + rstCab.getString("co_doc");
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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

    private boolean cargarPedidoEmbarcado(){
        boolean blnRes=true;
        int i=-1;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            cboPedSel.removeAllItems();
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" , a1.ne_tipnotped, a1.co_exp, 	a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp AS co_imp";
                strSQL+=" , a3.tx_nom AS tx_nomImp, a5.co_exp, a5.tx_nom AS tx_nomExp, a5.tx_nom2 AS tx_aliExp, a8.co_bodGrp AS co_bod, a7.tx_nom AS tx_nomBod";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN ";
                strSQL+=" 		(      tbm_detMovInv AS a6";                
                strSQL+=" 			INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                strSQL+=" 			INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                strSQL+=" 		)";
                strSQL+=" 		ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+="       )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_emp AS a3 ON (a1.co_emp=a3.co_emp)";
                strSQL+=" LEFT OUTER JOIN tbm_expImp AS a5 ON(a1.co_exp=a5.co_exp)";
                if(objTooBar.getEstado()=='n'){
                    strSQL+=" WHERE a1.co_tipDoc IN(14,245)";
                    cboPedSel.addItem("----------");
                }
                else{
                    strSQL+=" WHERE a1.co_emp=" + intCboPedSelCodEmp + "";
                    strSQL+=" AND a1.co_loc=" + intCboPedSelCodLoc + "";
                    strSQL+=" AND a1.co_tipDoc=" + intCboPedSelCodTipDoc + "";
                    strSQL+=" AND a1.co_doc=" + intCboPedSelCodDoc + "";
                }
                
                strSQL+=" AND a1.st_reg='A'";
                if(objTooBar.getEstado()=='n'){
                    strSQL+=" AND a1.st_cieDis='N'";
                }
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a1.tx_numDoc2";
                strSQL+=" , a1.ne_tipnotped, a1.co_exp, 	a1.nd_tot, a1.nd_pestotkgr, a1.st_imp, a1.tx_obs1";
                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a3.co_emp";
                strSQL+=" , a3.tx_nom, a5.co_exp, a5.tx_nom, a5.tx_nom2, a8.co_bodGrp, a7.tx_nom";
                strSQL+=" ORDER BY a1.tx_numDoc2";
                System.out.println("cargarPedidoEmbarcado:: " + strSQL);
                rst=stm.executeQuery(strSQL);                
                                
                arlDatPed.clear();
                for(i=0;rst.next(); i++){
                    cboPedSel.addItem("" + rst.getString("tx_numDoc2"));
                    //para saber cual pedido se ha seleccionado
                    arlRegPed=new ArrayList();
                    arlRegPed.add(INT_ARL_PED_COD_EMP,      rst.getString("co_emp"));
                    arlRegPed.add(INT_ARL_PED_COD_LOC,      rst.getString("co_loc"));
                    arlRegPed.add(INT_ARL_PED_COD_TIP_DOC,  rst.getString("co_tipDoc"));
                    arlRegPed.add(INT_ARL_PED_COD_DOC,      rst.getString("co_doc"));
                    arlRegPed.add(INT_ARL_PED_NUM_PED,      rst.getString("tx_numDoc2"));
                    arlRegPed.add(INT_ARL_PED_IND_SEL,      i);
                    arlRegPed.add(INT_ARL_PED_COD_IMP,      rst.getString("co_imp"));
                    arlDatPed.add(arlRegPed);
                }
                cboPedSel.setSelectedIndex(0);
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

    
    
    

    private boolean actualizar_tbmDetOrdDis(){
        boolean blnRes=true;
        String strSQLUpd="";
        BigDecimal bdeCanDis=new BigDecimal("0");
        BigDecimal bdeCanSelAct=new BigDecimal("0");
        BigDecimal bdeCanSelAnt=new BigDecimal("0");
        int intCodItmMaeSel=0;
        String strLin="";
        int intUltReg=0;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT MAX(a1.co_reg) AS co_reg";
                strSQL+=" FROM tbm_detorddis AS a1";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                    intUltReg=rst.getObject("co_reg")==null?0:rst.getInt("co_reg");
                
                //primero elimino los registros que han sido eliminados
                for(int i=0; i<arlDatFilEli.size(); i++){
                    bdeCanSelAnt=new BigDecimal(objUti.getObjectValueAt(arlDatFilEli, i, INT_CFE_CAN_SEL_AUX)==null?"0":(objUti.getStringValueAt(arlDatFilEli, i, INT_CFE_CAN_SEL_AUX).equals("")?"0":objUti.getStringValueAt(arlDatFilEli, i, INT_CFE_CAN_SEL_AUX))   );
                    strSQL="";
//                    strSQL+="UPDATE tbm_detMovInv";
//                    strSQL+=" SET nd_canUti=nd_canUti - " + bdeCanSelAnt + "";
//                    strSQL+=" WHERE co_emp=" + intCboPedSelCodEmp + "";
//                    strSQL+=" AND co_loc=" + intCboPedSelCodLoc + "";
//                    strSQL+=" AND co_tipDoc=" + intCboPedSelCodTipDoc + "";
//                    strSQL+=" AND co_doc=" + intCboPedSelCodDoc + "";
//                    strSQL+=" AND co_reg=" + objUti.getIntValueAt(arlDatFilEli, i, INT_CFE_COD_REG_REL) + "";//registro de tbm_detMovInv
//                    strSQL+=";";
                    strSQL+=" DELETE FROM tbm_detorddis";
                    strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                    strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                    strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                    strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                    strSQL+=" AND co_reg=" + objUti.getIntValueAt(arlDatFilEli, i, INT_CFE_COD_REG) + "";//registro de tbm_detorddis
                    strSQL+=";";
                    strSQLUpd+="" + strSQL;
                }
                
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("I")){//son nuevos registros
                        intUltReg++;
                        bdeCanDis=new BigDecimal("0");
                        strSQL="";
                        strSQL+="INSERT INTO tbm_detorddis(";
                        strSQL+=" co_emp, co_loc, co_tipdoc,";
                        strSQL+=" co_doc, co_reg, co_itm, nd_can";
                        strSQL+=" , co_emprel, co_locrel, co_tipdocrel, co_docrel, co_regrel)";
                        strSQL+=" VALUES (";
                        strSQL+="" + rstCab.getString("co_emp") + ",";//co_emp
                        strSQL+="" + rstCab.getString("co_loc") + ",";//co_loc
                        strSQL+="" + rstCab.getString("co_tipDoc") + ",";//co_tipdoc
                        strSQL+="" + rstCab.getString("co_doc") + ",";//co_doc
                        strSQL+="" + intUltReg + ",";//co_reg
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP) + ",";//co_itm
                        intCodItmMaeSel=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                        bdeCanDis=isCantidadDisponible(intCodItmMaeSel);
                        bdeCanSelAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString())   );


                        
                        
                        
                        if(bdeCanDis.compareTo(bdeCanSelAct)<0){
                            mostrarMsgInf("<HTML>El item " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM) + " no tiene disponible la cantidad seleccionada.<BR>Verifique y vuelva a intentarlo</HTML>");
                            blnRes=false;
                            break;
                        }                    
                        strSQL+="" + bdeCanSelAct + ",";//nd_can
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL) + ",";//co_empRel
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL) + ",";//co_locRel
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL) + ",";//co_tipDocRel
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL) + ",";//co_docRel
                        strSQL+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL) + "";//co_regRel
                        strSQL+=");";
//                        strSQL+="UPDATE tbm_detMovInv";
//                        strSQL+=" SET nd_canuti=(CASE WHEN nd_canuti IS NULL THEN 0 ELSE nd_canuti END) + " + bdeCanSelAct + "";
//                        strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL) + "";
//                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL) + "";
//                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL) + "";
//                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL) + ";";
                        strSQLUpd+=strSQL;
                    }
                    else if(strLin.equals("M")){// los registros que existen en la DB y que si se han modificados (el estado cambio a "M")
                        
                        intCodItmMaeSel=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString());
                        bdeCanDis=isCantidadDisponible(intCodItmMaeSel);
                        bdeCanSelAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL).toString())   );
                        bdeCanSelAnt=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL_AUX).equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL_AUX).toString())   );

                        
                        
                        System.out.println("bdeCanDis: " + bdeCanDis);
                        System.out.println("bdeCanSelAct: " + bdeCanSelAct);
                        System.out.println("bdeCanSelAnt: " + bdeCanSelAnt);
                        
                        
                        if((bdeCanDis.add(bdeCanSelAnt)).compareTo(bdeCanSelAct)<0){
                            mostrarMsgInf("<HTML>El item " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM) + " no tiene disponible la cantidad seleccionada.<BR>Verifique y vuelva a intentarlo</HTML>");
                            blnRes=false;
                            break;
                        }
                        
                        strSQL="";
//                        strSQL+="UPDATE tbm_detMovInv";
//                        strSQL+=" SET nd_canuti=(CASE WHEN nd_canuti IS NULL THEN 0 ELSE nd_canuti END) - " + bdeCanSelAnt + " + " + bdeCanSelAct;
//                        strSQL+=" WHERE co_emp=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL) + "";
//                        strSQL+=" AND co_loc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL) + "";
//                        strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL) + "";
//                        strSQL+=" AND co_doc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL) + "";
//                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL) + ";";
                        strSQL+=" UPDATE tbm_detOrdDis";
                        strSQL+=" SET nd_can=" + bdeCanSelAct;
                        strSQL+=" WHERE co_emp=" + rstCab.getString("co_emp") + "";
                        strSQL+=" AND co_loc=" + rstCab.getString("co_loc") + "";
                        strSQL+=" AND co_tipDoc=" + rstCab.getString("co_tipDoc") + "";
                        strSQL+=" AND co_doc=" + rstCab.getString("co_doc") + "";
                        strSQL+=" AND co_reg=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG) + ";";
                        strSQLUpd+=strSQL;
                    }
                    else{//son registros ya guardados y q no se le han hecho modificaciones (el estado esta en blanco) 
                        
                    }                   
                    
                }
                System.out.println("insertar_tbmDetOrdDis: " + strSQLUpd);
                stm.executeUpdate(strSQLUpd);
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
    
        
    private ArrayList cargarDatosSeleccionados(){
        String strLin="";
        arlDat.clear();
        String strCodEmp="";
        try{
            System.out.println("strCodEmp 1 ");
            for(int i=0;i<objTblMod.getRowCountTrue(); i++){
                System.out.println("strCodEmp 2 ");
                strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                System.out.println("strCodEmp 3 ");
                strCodEmp=objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL).toString();
                System.out.println("strCodEmp: " + strCodEmp);
                if(strLin.equals("I")){
                    if( ! strCodEmp.equals("")){
                        arlReg=new ArrayList();
                        arlReg.add(INT_ARL_COD_EMP_REL, objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP_REL));
                        arlReg.add(INT_ARL_COD_LOC_REL, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC_REL));
                        arlReg.add(INT_ARL_COD_TIP_DOC_REL, objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC_REL));
                        arlReg.add(INT_ARL_COD_DOC_REL, objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC_REL));
                        arlReg.add(INT_ARL_COD_REG_REL, objTblMod.getValueAt(i, INT_TBL_DAT_COD_REG_REL));
                        arlReg.add(INT_ARL_COD_ITM_GRP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                        arlReg.add(INT_ARL_COD_ITM_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                        arlReg.add(INT_ARL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                        arlReg.add(INT_ARL_COD_ALT_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM));
                        arlReg.add(INT_ARL_NOM_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM));
                        arlReg.add(INT_ARL_DES_COR_UNI_MED, objTblMod.getValueAt(i, INT_TBL_DAT_DES_COR_UNI_MED));
                        arlReg.add(INT_ARL_DES_LAR_UNI_MED, objTblMod.getValueAt(i, INT_TBL_DAT_DES_LAR_UNI_MED));
                        arlReg.add(INT_ARL_CAN_ORD_SEL, objTblMod.getValueAt(i, INT_TBL_DAT_CAN_SEL));
                        arlReg.add(INT_ARL_CAN_DIS, objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ORD_DIS));
                        arlDat.add(arlReg);
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return arlDat;
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
    private class ZafThreadGUIVisPre extends Thread{
        private int intIndFun;
        public ZafThreadGUIVisPre(){
            intIndFun=0;
        }
        public void run(){
            switch (intIndFun){
                case 0: //Botón "Imprimir".
                    generarRpt(0);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIVisPre=null;
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
        String strRutRpt, strNomRpt;
        int i, intNumTotRpt;
        boolean blnRes=true;
        strAux="";
        String strSQLRep="", strSQLSubRep="";
        Statement stmIns;
        ResultSet rstIns;
        try
        {
            if(conCab!=null){
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE){
                    //Obtener la fecha y hora del servidor.
                    datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                    if (datFecAux==null)
                        return false;
                    datFecAux=null;
                    intNumTotRpt=objRptSis.getNumeroTotalReportes();
                    for (i=0;i<intNumTotRpt;i++){
                        if (objRptSis.isReporteSeleccionado(i)){

                            stmIns=conCab.createStatement();
                            strSQL="";
                            strSQL+="SELECT d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, d1.ne_numdoc, d1.feimpguia, d1.obs2, d1.tx_numdoc2";
                            strSQL+=" , d1.tx_nom, d1.fe_ing, d1.co_bod, d1.empresa, d1.fe_doc, d1.nd_kgr, d1.nd_kil";
                            strSQL+=", d1.co_bodOri, d1.tx_nomBodOri, d1.co_bodDes, d1.tx_nomBodDes";
                            strSQL+=" FROM( ";
                            strSQL+="       SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b1.ne_numdoc, b2.co_reg, CURRENT_TIMESTAMP AS feimpguia, TRIM(b1.tx_obs2) AS obs2,";
                            strSQL+="       c1.tx_numdoc2, b8.tx_nom, b1.fe_ing, b1.co_bod, b8.tx_nom  as empresa, b1.fe_doc, c2.tx_codalt, c2.tx_nomitm, abs(b2.nd_can) AS nd_can, c2.tx_unimed,";
                            strSQL+="       (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                            strSQL+=" 		from tbm_detOrdDis as x inner join tbm_inv as inv";
                            strSQL+="		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                            strSQL+="		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                            strSQL+=" 	) as nd_kgr,";
                            strSQL+=" 	(";
                            strSQL+=" 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                            strSQL+=" 		from tbm_detOrdDis as x inner join tbm_inv as inv";
                            strSQL+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                            strSQL+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                            strSQL+="	) as nd_kil";
                            strSQL+="   , c2.co_bod AS co_bodOri, c3.tx_nom AS tx_nomBodOri, b1.co_bod AS co_bodDes, c4.tx_nom AS tx_nomBodDes";
                            strSQL+="	FROM (tbm_cabOrdDis as b1 INNER JOIN tbm_bod AS c4 ON b1.co_emp=c4.co_emp AND b1.co_bod=c4.co_bod)";
                            strSQL+="	INNER JOIN (tbm_detOrdDis as b2";
                            strSQL+="						INNER JOIN (tbm_detMovInv AS c2  INNER JOIN tbm_bod AS c3 ON c2.co_emp=c3.co_emp AND c2.co_bod=c3.co_bod)";
                            strSQL+=" 						ON b2.co_empRel=c2.co_emp AND b2.co_locRel=c2.co_loc AND b2.co_tipDocRel=c2.co_tipDoc AND b2.co_docRel=c2.co_doc AND b2.co_regRel=c2.co_reg";
                            strSQL+="						INNER JOIN tbm_cabMovInv AS c1";
                            strSQL+="						ON c2.co_emp=c1.co_emp AND c2.co_loc=c1.co_loc AND c2.co_tipDoc=c1.co_tipDoc AND c2.co_doc=c1.co_doc";
                            strSQL+="					    )";
                            strSQL+=" 	ON(b1.co_emp=b2.co_emp and b1.co_loc=b2.co_loc and b1.co_tipdoc=b2.co_tipdoc and b1.co_doc=b2.co_doc)";
                            strSQL+=" 	INNER JOIN tbm_inv as b5 on(b2.co_emp=b5.co_emp and b2.co_itm=b5.co_itm)";
                            strSQL+=" 	INNER JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                            strSQL+=" 	INNER JOIN tbm_emp as b8 ON( b8.co_emp = c1.co_emp)";
                            strSQL+=" 	WHERE b1.co_emp=" + rstCab.getString("co_emp") + "";
                            strSQL+=" 	AND b1.co_loc=" + rstCab.getString("co_loc") + "";
                            strSQL+=" 	AND b1.co_tipdoc=" + rstCab.getString("co_tipDoc") + "";
                            strSQL+=" 	AND b1.co_doc=" + rstCab.getString("co_doc") + "";
                            strSQL+=" 	AND b1.st_reg='A' AND c1.st_reg='A'";
                            strSQL+=" ) AS d1";
                            strSQL+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, d1.ne_numdoc, d1.feimpguia, d1.obs2, d1.tx_numdoc2";
                            strSQL+=", d1.tx_nom, d1.fe_ing, d1.co_bod, d1.empresa, d1.fe_doc, d1.nd_kgr, d1.nd_kil";
                            strSQL+=", d1.co_bodOri, d1.tx_nomBodOri, d1.co_bodDes, d1.tx_nomBodDes";
                            strSQL+=";";
                            strSQLRep=strSQL;
                            System.out.println("strSQLRep: " + strSQLRep);

                            
                            
                            strSQL="";
                            strSQL+=" SELECT * FROM(";
                            strSQL+="	SELECT b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b2.co_reg, CURRENT_TIMESTAMP AS feimpguia";
                            strSQL+=" 	, b5.tx_codalt, c2.tx_nomitm, abs(b2.nd_can) AS nd_can, c2.tx_unimed,";
                            strSQL+=" 	(	SELECT sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                            strSQL+=" 		FROM tbm_detOrdDis AS x INNER JOIN tbm_inv AS inv";
                            strSQL+=" 		ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                            strSQL+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                            strSQL+=" 	) AS nd_kgr,";
                            strSQL+="	(	SELECT SUM( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) AS kilo";
                            strSQL+=" 		FROM tbm_detOrdDis AS x INNER JOIN tbm_inv AS inv";
                            strSQL+="		ON(x.co_emp=inv.co_emp AND x.co_itm=inv.co_itm)";
                            strSQL+=" 		WHERE x.co_emp=b1.co_emp AND x.co_loc=b1.co_loc  AND x.co_tipdoc=b1.co_tipdoc AND x.co_doc=b1.co_doc";
                            strSQL+="	) AS nd_kil";
                            strSQL+="	FROM tbm_cabOrdDis as b1 INNER JOIN (tbm_detOrdDis as b2";
                            strSQL+="						INNER JOIN tbm_detMovInv AS c2";
                            strSQL+="						ON b2.co_empRel=c2.co_emp AND b2.co_locRel=c2.co_loc AND b2.co_tipDocRel=c2.co_tipDoc AND b2.co_docRel=c2.co_doc AND b2.co_regRel=c2.co_reg";
                            strSQL+="						INNER JOIN tbm_cabMovInv AS c1";
                            strSQL+="						ON c2.co_emp=c1.co_emp AND c2.co_loc=c1.co_loc AND c2.co_tipDoc=c1.co_tipDoc AND c2.co_doc=c1.co_doc";
                            strSQL+="					    )";
                            strSQL+="	ON(b1.co_emp=b2.co_emp and b1.co_loc=b2.co_loc and b1.co_tipdoc=b2.co_tipdoc and b1.co_doc=b2.co_doc)";
                            strSQL+="	INNER JOIN tbm_inv AS b5 ON(b2.co_emp=b5.co_emp AND b2.co_itm=b5.co_itm)";
                            strSQL+=" 	INNER JOIN tbm_loc AS b6 ON( b1.co_emp = b6.co_emp AND  b1.co_loc=b6.co_loc)";
                            strSQL+=" 	INNER JOIN tbm_emp AS b8 ON( b8.co_emp = c1.co_emp)";
                            strSQL+=" 	WHERE b1.co_emp=" + rstCab.getString("co_emp") + "";
                            strSQL+="	AND b1.co_loc=" + rstCab.getString("co_loc") + "";
                            strSQL+=" 	AND b1.co_tipdoc=" + rstCab.getString("co_tipDoc") + "";
                            strSQL+=" 	AND b1.co_doc=" + rstCab.getString("co_doc") + "";
                            strSQL+="	AND b1.st_reg='A' AND c1.st_reg='A'";
                            strSQL+=") AS x";
                            strSQL+=" ORDER BY co_reg";
                            strSQL+=";";
                            strSQLSubRep=strSQL;
                            System.out.println("strSQLSubRep: " + strSQLSubRep);

                            java.util.Map mapPar = new java.util.HashMap();
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))){
                                case 438://nota de pedido incompleta(se envia al seguro y a ....)
                                    strRutRpt=objRptSis.getRutaReporte(i);
                                    strNomRpt=objRptSis.getNombreReporte(i);
                                    //Inicializar los parametros que se van a pasar al reporte.
                                    mapPar.put("strSQLRep", strSQLRep);
                                    mapPar.put("strSQLSubRep", strSQLSubRep);
                                    mapPar.put("SUBREPORT_DIR", strRutRpt);
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                                default:
                                    break;

                            }
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

    
    
    
}