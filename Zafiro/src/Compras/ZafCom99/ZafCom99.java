/*
 * ZafCom99.java
 *
 * Created on January 17, 2018, 11:47 AM
 */
package Compras.ZafCom99;
import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafStkInv.ZafStkInv;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.UltDocPrint;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author  Gigi
 */
public class ZafCom99 extends javax.swing.JInternalFrame
{   
    //Modelo de la tabla de Com99
    //Constantes: Columnas del JTable.
    private static final int INT_TBL_DAT_LIN=0;             //Línea
    private static final int INT_TBL_DAT_COD_ITM_MAE=1;     //Codigo de item maestro
    private static final int INT_TBL_DAT_COD_ITM_GRP=2;         //Codigo de item de grupo
    private static final int INT_TBL_DAT_COD_ITM_EMP=3;         //Codigo de item de empresa
    private static final int INT_TBL_DAT_COD_ALT_ITM=4;     //Código alterno del item
    private static final int INT_TBL_DAT_COD_LET_ITM=5;     //Código alterno del item
    private static final int INT_TBL_DAT_BUT_ITM=6;         //buton para consulta del item
    private static final int INT_TBL_DAT_NOM_ITM=7;         //Nombre del item
    private static final int INT_TBL_DAT_UNI_MED=8;         //Unidad de medida    
    private static final int INT_TBL_DAT_COD_GRP=9;         //Unidad de medida
    private static final int INT_TBL_DAT_DES_COR_GRP=10;         //Unidad de medida
    private static final int INT_TBL_DAT_DES_LAR_GRP=11;         //Unidad de medida
    private static final int INT_TBL_DAT_CAN_EGR=12;             //Cantidad
    private static final int INT_TBL_DAT_CAN_ING=13;             //Cantidad
    private static final int INT_TBL_DAT_COS_UNI=14;             //Cantidad
    private static final int INT_TBL_DAT_COS_TOT=15;             //Cantidad
    private static final int INT_TBL_DAT_POR_CAL_COS_UNI=16;         //Unidad de medida
    private static final int INT_TBL_DAT_TIP_REL=17;         //Unidad de medida
    private static final int INT_TBL_DAT_CAN_EGR_AUX=18;             //Cantidad
    private static final int INT_TBL_DAT_CAN_ING_AUX=19;             //Cantidad
    private static final int INT_TBL_DAT_NUM_FIL_BLK=20;             //Cantidad
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafDocLis objDocLis;
    private ZafVenCon vcoItm, vcoTipDoc, vcoBod;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafTblFilCab objTblFilCab;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblEgr, objTblCelRenLblIng;
    private ZafTblCelEdiTxt objTblCelEdiTxtEgr, objTblCelEdiTxtIng;
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm, objTblCelEdiTxtVcoLet;
    private MiToolBar objTooBar;
    private ZafDatePicker dtpFecDoc;
    private UltDocPrint  objUltDocPrint;
    private ZafStkInv objStkInv;
    private ZafAsiDia objAsiDia;
    
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private java.util.Date datFecDoc;
    
    private Vector vecDat, vecCab, vecReg;
    private String strSQL, strAux;    
    private boolean blnHayCam;    
    private String strVersion=" v0.1.2";
    
    private String strCodBod,  strNomBod;                //Contenido del campo al obtener el foco.
    private Vector vecAux;
    private String strDesCorTipDoc, strDesLarTipDoc, strTipDocNecAutAnu;
    
    //ArrayList para consultar
    private ArrayList arlRegConDoc, arlDatConDoc;
    private static final int INT_ARL_CON_COD_EMP=0;  
    private static final int INT_ARL_CON_COD_LOC=1;
    private static final int INT_ARL_CON_COD_TIP_DOC=2;
    private static final int INT_ARL_CON_COD_DOC=3;
    private static final int INT_ARL_CON_COD_USR_ING=4; 
    private static final int INT_ARL_CON_COD_USR_MOD=5;
    
    //Indice de la Consulta del programa 
    private int intIndReg;
    private int intSecGrp, intSecEmp;
    private int intSig=1;
    
    //Para actualizar inventario a través de clase ZafStkInv
    private ArrayList arlRegStkInvItm, arlDatStkInvItm;//Solo egresos
    
    private ArrayList arlRegStkInvItmEgrIng, arlDatStkInvItmEgrIng;
    
    private int intNumFilEgr;
    
    
    /** Creates new form ZafCom77 */
    public ZafCom99(ZafParSis obj){
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatConDoc=new ArrayList();
            arlDatStkInvItm=new ArrayList();
            arlDatStkInvItmEgrIng=new ArrayList();
            objStkInv=new Librerias.ZafStkInv.ZafStkInv(objParSis);
            intNumFilEgr=1;
            initComponents();
            configurarFrm();
            agregarDocLis();
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panCab = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        lblBen = new javax.swing.JLabel();
        txtCodBod = new javax.swing.JTextField();
        txtNomBod = new javax.swing.JTextField();
        butBod = new javax.swing.JButton();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc = new javax.swing.JTextField();
        lblValDoc = new javax.swing.JLabel();
        txtValDoc = new javax.swing.JTextField();
        panDet = new javax.swing.JPanel();
        spnDet = new javax.swing.JScrollPane();
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
        panAsiDia = new javax.swing.JPanel();
        panBar = new javax.swing.JPanel();

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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setAutoscrolls(true);

        panGrl.setAutoscrolls(true);
        panGrl.setLayout(new java.awt.BorderLayout());

        panCab.setPreferredSize(new java.awt.Dimension(0, 70));
        panCab.setLayout(null);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCab.add(lblTipDoc);
        lblTipDoc.setBounds(4, 6, 100, 20);
        panCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 6, 10, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(118, 6, 56, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panCab.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(175, 6, 230, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCab.add(butTipDoc);
        butTipDoc.setBounds(406, 6, 20, 20);

        lblBen.setText("Bodega:");
        lblBen.setToolTipText("Bodega en la que se debe hacer el conteo");
        panCab.add(lblBen);
        lblBen.setBounds(4, 26, 70, 20);

        txtCodBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodBodFocusLost(evt);
            }
        });
        txtCodBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodBodActionPerformed(evt);
            }
        });
        panCab.add(txtCodBod);
        txtCodBod.setBounds(118, 26, 56, 20);

        txtNomBod.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomBodFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomBodFocusLost(evt);
            }
        });
        txtNomBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomBodActionPerformed(evt);
            }
        });
        panCab.add(txtNomBod);
        txtNomBod.setBounds(175, 26, 230, 20);

        butBod.setText("...");
        butBod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butBodActionPerformed(evt);
            }
        });
        panCab.add(butBod);
        butBod.setBounds(406, 26, 20, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panCab.add(lblCodDoc);
        lblCodDoc.setBounds(4, 46, 110, 20);
        panCab.add(txtCodDoc);
        txtCodDoc.setBounds(118, 46, 120, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panCab.add(lblFecDoc);
        lblFecDoc.setBounds(432, 6, 110, 20);

        lblNumDoc1.setText("Número documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(432, 27, 100, 20);

        txtNumDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc.setToolTipText("Número de egreso");
        panCab.add(txtNumDoc);
        txtNumDoc.setBounds(550, 26, 120, 20);

        lblValDoc.setText("Valor del documento:");
        lblValDoc.setToolTipText("Código del documento");
        panCab.add(lblValDoc);
        lblValDoc.setBounds(432, 46, 110, 20);

        txtValDoc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        panCab.add(txtValDoc);
        txtValDoc.setBounds(550, 46, 120, 20);

        panGrl.add(panCab, java.awt.BorderLayout.NORTH);

        panDet.setBorder(javax.swing.BorderFactory.createTitledBorder("Items a ingresar:"));
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

        panGrl.add(panDet, java.awt.BorderLayout.CENTER);

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

        panGrl.add(panObs, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGrl);

        panAsiDia.setLayout(new java.awt.BorderLayout());
        tabFrm.addTab("Asiento de diario", panAsiDia);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(0, 50));
        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
// TODO add your handling code here:

}//GEN-LAST:event_formInternalFrameOpened

private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexión si está abierta.
                if (rst!=null)
                {
                    rst.close();
                    stm.close();
                    con.close();
                    rst=null;
                    stm=null;
                    con=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e){
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc = txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
    }//GEN-LAST:event_txtDesCorTipDocFocusGained

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

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc = txtDesLarTipDoc.getText();
        txtDesLarTipDoc.selectAll();
    }//GEN-LAST:event_txtDesLarTipDocFocusGained

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

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

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

    private void txtCodBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodBodActionPerformed
        // TODO add your handling code here:
        txtCodBod.transferFocus();
    }//GEN-LAST:event_txtCodBodActionPerformed

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

    private void txtNomBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomBodActionPerformed
        // TODO add your handling code here:
        txtNomBod.transferFocus();
    }//GEN-LAST:event_txtNomBodActionPerformed

    private void butBodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butBodActionPerformed
        // TODO add your handling code here:
        mostrarVenConBod(0);
    }//GEN-LAST:event_butBodActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butBod;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel lblBen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblValDoc;
    private javax.swing.JPanel panAsiDia;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCab;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panObs;
    private javax.swing.JScrollPane spnDet;
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
    private javax.swing.JTextField txtValDoc;
    // End of variables declaration//GEN-END:variables

    
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);
            objDocLis=new ZafDocLis();
            objUltDocPrint=new Librerias.ZafUtil.UltDocPrint(objParSis);

            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);

            configurarVenConItm();
            configurarTblDat();
            configurarVenConTipDoc();
            configurarVenConBod();

            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtCodDoc.setEditable(false);
            txtValDoc.setBackground(objParSis.getColorCamposSistema());
            txtValDoc.setEditable(false);
            
            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNomBod.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc.setBackground(objParSis.getColorCamposObligatorios());
            
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(550, 4, 120, 20);
            dtpFecDoc.setEnabled(false);
            
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            
            txtCodTipDoc.setVisible(false);
            
//            if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                objTooBar.setVisibleInsertar(true);
//            }
            
            objAsiDia=new ZafAsiDia(objParSis);
            objAsiDia.addAsiDiaListener(new Librerias.ZafEvt.ZafAsiDiaAdapter() {
                public void beforeConsultarCuentas(Librerias.ZafEvt.ZafAsiDiaEvent evt) {
                    if (txtCodTipDoc.getText().equals(""))
                        objAsiDia.setCodigoTipoDocumento(-1);
                    else
                        objAsiDia.setCodigoTipoDocumento(Integer.parseInt(txtCodTipDoc.getText()));
                }
            });

            panAsiDia.add(objAsiDia,java.awt.BorderLayout.CENTER);
            
            
            vecDat=new Vector();    //Almacena los datos

            panBar.add(objTooBar);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe algún cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentará un mensaje advirtiendo que si no guarda los cambios los perderá.
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
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodBod.getDocument().addDocumentListener(objDocLis);
        txtNomBod.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc.getDocument().addDocumentListener(objDocLis);
        txtValDoc.getDocument().addDocumentListener(objDocLis);
        txaObs1.getDocument().addDocumentListener(objDocLis);
        txaObs2.getDocument().addDocumentListener(objDocLis);
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
            vecCab = new Vector(21);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COD_ITM_MAE, "Cod.Itm.Mae.");
            vecCab.add(INT_TBL_DAT_COD_ITM_GRP, "Cod.Itm.Grp.");
            vecCab.add(INT_TBL_DAT_COD_ITM_EMP, "Cod.Itm.Emp.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM, "Cod.Alt.");
            vecCab.add(INT_TBL_DAT_COD_LET_ITM, "Cod.Let.");            
            vecCab.add(INT_TBL_DAT_BUT_ITM, "");
            vecCab.add(INT_TBL_DAT_NOM_ITM, "Item");
            vecCab.add(INT_TBL_DAT_UNI_MED, "Unidad");
            vecCab.add(INT_TBL_DAT_COD_GRP, "Cód.Grp.");
            vecCab.add(INT_TBL_DAT_DES_COR_GRP, "Des.Cor.Grp.");
            vecCab.add(INT_TBL_DAT_DES_LAR_GRP, "Des.Lar.Grp.");
            vecCab.add(INT_TBL_DAT_CAN_EGR, "Can.Egr.");
            vecCab.add(INT_TBL_DAT_CAN_ING, "Can.Ing.");
            vecCab.add(INT_TBL_DAT_COS_UNI, "Cos.Uni.");
            vecCab.add(INT_TBL_DAT_COS_TOT, "Cos.Tot.");
            vecCab.add(INT_TBL_DAT_POR_CAL_COS_UNI, "Por.Cal.Cos.Uni.");
            vecCab.add(INT_TBL_DAT_TIP_REL, "Tip.Rel.");
            vecCab.add(INT_TBL_DAT_CAN_EGR_AUX, "Can.Egr.Aux.");
            vecCab.add(INT_TBL_DAT_CAN_ING_AUX, "Can.Ing.Aux.");
            vecCab.add(INT_TBL_DAT_NUM_FIL_BLK, "Núm.Fil.Blk.");
            
            
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);

            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_CAN_EGR);
            arlAux.add("" + INT_TBL_DAT_CAN_ING);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;            
            
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            objTblPopMnu.setBorrarContenidoEnabled(true);
            objTblPopMnu.setPegarEnabled(true);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            
             objTblPopMnu.addTblPopMnuListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuAdapter() {
                int intFilSel=-1;
//                int intFilEli[];
                public void beforeClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    if (objTblPopMnu.isClickEliminarFila()){
                        intFilSel=tblDat.getSelectedRow();
                        objTblMod.removeEmptyRows();
                        if(eliminaGrpCnv(intFilSel)){
                            
                        }
                            //tblDat.getSelectionModel().addSelectionInterval (11, 12);
                    }
                }
                public void afterClick(Librerias.ZafTblUti.ZafTblEvt.ZafTblPopMnuEvent evt) {
                    
                    if(objTblPopMnu.isClickEliminarFila()){
//                        if(isEliGrpCnv(intFilEli)){//se debe eliminar
//                        }

                    }
                }
            });
            
            
            
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(26);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(230);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_GRP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_GRP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_GRP).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_POR_CAL_COS_UNI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_TIP_REL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_AUX).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_NUM_FIL_BLK).setPreferredWidth(50);            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            //Configurar JTable: Editor de básqueda.
            objTblBus=new ZafTblBus(tblDat);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_POR_CAL_COS_UNI).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblEgr=new ZafTblCelRenLbl();
            objTblCelRenLblEgr.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblEgr.setTipoFormato(objTblCelRenLblEgr.INT_FOR_NUM);
            objTblCelRenLblEgr.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblEgr.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLblEgr);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR_AUX).setCellRenderer(objTblCelRenLblEgr);
            objTblCelRenLblEgr=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblIng=new ZafTblCelRenLbl();
            objTblCelRenLblIng.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblIng.setTipoFormato(objTblCelRenLblIng.INT_FOR_NUM);
            objTblCelRenLblIng.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            objTblCelRenLblIng.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLblIng);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING_AUX).setCellRenderer(objTblCelRenLblIng);
            objTblCelRenLblIng=null;
            
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            objTblCelEdiTxtEgr=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellEditor(objTblCelEdiTxtEgr);
            objTblCelEdiTxtEgr.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                BigDecimal bdeCanEgrAnt=BigDecimal.ZERO;
                BigDecimal bdeCanEgrSig=BigDecimal.ZERO;
                String strTipRelEgr="";
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    bdeCanEgrAnt=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString()));
                    strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
                    if(strTipRelEgr.equals("I"))
                        objTblCelEdiTxtEgr.setCancelarEdicion(true);
                    else
                        objTblCelEdiTxtEgr.setCancelarEdicion(false);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                  
                    bdeCanEgrSig=new BigDecimal(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_CAN_EGR).toString()));
                    
                    if(bdeCanEgrSig.compareTo(BigDecimal.ZERO)<=0){
                        objTblMod.setValueAt(bdeCanEgrAnt, intFil, INT_TBL_DAT_CAN_EGR);
                    }
                    calcularCostoTotalEgreso(intFil);
                    calcularCostosIngresos(intFil, bdeCanEgrSig);
                    setValorDoc();
                    generaDiarioAjuste();
                }
            });
             
            objTblCelEdiTxtIng=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellEditor(objTblCelEdiTxtIng);
            objTblCelEdiTxtIng.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                int intCol=-1;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                  
                }
            });
            
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_ITM_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_GRP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_TIP_REL, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_CAL_COS_UNI, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_EGR_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAN_ING_AUX, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_FIL_BLK, tblDat);
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_LET_ITM);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_CAN_EGR);
                        
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[14];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            intColVen[6]=7;
            intColVen[7]=8;
            intColVen[8]=9;
            intColVen[9]=10;
            intColVen[10]=11;
            intColVen[11]=12;
            intColVen[12]=13;
            intColVen[13]=14;
            int intColTbl[]=new int[14];
            intColTbl[0]=INT_TBL_DAT_COD_ITM_MAE;
            intColTbl[1]=INT_TBL_DAT_COD_ITM_GRP;
            intColTbl[2]=INT_TBL_DAT_COD_ITM_EMP;
            intColTbl[3]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[4]=INT_TBL_DAT_COD_LET_ITM;
            intColTbl[5]=INT_TBL_DAT_NOM_ITM;
            intColTbl[6]=INT_TBL_DAT_UNI_MED;            
            intColTbl[7]=INT_TBL_DAT_COD_GRP;
            intColTbl[8]=INT_TBL_DAT_DES_COR_GRP;
            intColTbl[9]=INT_TBL_DAT_DES_LAR_GRP;
            intColTbl[10]=INT_TBL_DAT_CAN_EGR;
            intColTbl[11]=INT_TBL_DAT_TIP_REL;
            intColTbl[12]=INT_TBL_DAT_POR_CAL_COS_UNI;
            intColTbl[13]=INT_TBL_DAT_COS_TOT;

            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiTxtVcoItm.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(2);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoItm.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);                        
                        
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
                        //txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                  
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
                        generaDiarioAjuste();
                        //calcular_totalCostos();
                    }
                }
            });
            
            objTblCelEdiTxtVcoLet=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_LET_ITM).setCellEditor(objTblCelEdiTxtVcoLet);
            objTblCelEdiTxtVcoLet.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiTxtVcoLet.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(3);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiTxtVcoLet.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);                        
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);
                        
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
//                        txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                        
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
                        generaDiarioAjuste();
                        //calcular_totalCostos();
                    }
                }
            });
            

            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil=-1;
                String strSQLAdi="";
                String strLin="";
                BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDat.getSelectedRow();
                    strLin=objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_LIN).toString();
                    //INSERCION
                    objTblCelEdiButVcoItm.setCancelarEdicion(false);
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoItm.setCampoBusqueda(3);
                    vcoItm.setCriterio1(11);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if (objTblCelEdiButVcoItm.isConsultaAceptada()){
                        objTblMod.setValueAt(vcoItm.getValueAt(1), intFil, INT_TBL_DAT_COD_ITM_MAE);
                        /*Rose 24Ene2018: Se realiza cambio en consulta de datos del item,
                          se estaba leyendo como código del item por empreasa, el código del item por grupo 
                          y generaba problemas de inventario a Eddy Carvajal al momento de insertar.*/
                        
                        //objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        //objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        objTblMod.setValueAt(vcoItm.getValueAt(3), intFil, INT_TBL_DAT_COD_ITM_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(2), intFil, INT_TBL_DAT_COD_ITM_EMP);
                        
                        objTblMod.setValueAt(vcoItm.getValueAt(4), intFil, INT_TBL_DAT_COD_ALT_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(5), intFil, INT_TBL_DAT_COD_LET_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(6), intFil, INT_TBL_DAT_NOM_ITM);
                        objTblMod.setValueAt(vcoItm.getValueAt(7), intFil, INT_TBL_DAT_UNI_MED);
                        objTblMod.setValueAt(vcoItm.getValueAt(8), intFil, INT_TBL_DAT_COD_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(9), intFil, INT_TBL_DAT_DES_COR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(10), intFil, INT_TBL_DAT_DES_LAR_GRP);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR);
                        objTblMod.setValueAt(vcoItm.getValueAt(11), intFil, INT_TBL_DAT_CAN_EGR_AUX);
                        objTblMod.setValueAt(vcoItm.getValueAt(12), intFil, INT_TBL_DAT_TIP_REL);
                        objTblMod.setValueAt(vcoItm.getValueAt(13), intFil, INT_TBL_DAT_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(14), intFil, INT_TBL_DAT_POR_CAL_COS_UNI);
                        objTblMod.setValueAt(vcoItm.getValueAt(15), intFil, INT_TBL_DAT_COS_TOT);
                        objTblMod.setValueAt(intNumFilEgr, intFil, INT_TBL_DAT_NUM_FIL_BLK);
                   
                        bdeCosTotEgr=objUti.redondearBigDecimal(((vcoItm.getValueAt(15).equals("")?"0":vcoItm.getValueAt(15))), objParSis.getDecimalesMostrar());
                        //txtValDoc.setText(bdeCosTotEgr.abs().toString());
                        
                        
                        objTblMod.insertRow();
                        cargarItemIngreso(vcoItm.getValueAt(8), vcoItm.getValueAt(13));
                        intNumFilEgr++;
                        setValorDoc();
                        generaDiarioAjuste();
                        //calcular_totalCostos();
                    }
                }
            });
            intColVen=null;
            intColTbl=null;
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            
            //Libero los objetos auxiliares.
            tcmAux = null;
        }
        catch(Exception e) {
            blnRes = false;
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
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_COD_ITM_MAE:
                    strMsg = "Código del item maestro";
                    break;
                case INT_TBL_DAT_COD_ITM_GRP:
                    strMsg = "Código del item de grupo";
                    break;
                case INT_TBL_DAT_COD_ITM_EMP:
                    strMsg = "Código del item de empresa";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg = "Código alterno del item";
                    break;
                case INT_TBL_DAT_COD_LET_ITM:
                    strMsg = "Código en letras del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg = "Nombre del item";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg = "Unidad de medida";
                    break;
                    
                case INT_TBL_DAT_COD_GRP:
                    strMsg = "Código de grupo";
                    break;
                case INT_TBL_DAT_DES_COR_GRP:
                    strMsg = "Descripción corta de grupo";
                    break;
                case INT_TBL_DAT_DES_LAR_GRP:
                    strMsg = "Descripción larga de grupo";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg = "Cantidad de Egreso";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg = "Cantidad de Ingreso";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg = "Costo Unitario";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg = "Costo Total";
                    break;
                case INT_TBL_DAT_POR_CAL_COS_UNI:
                    strMsg = "Porcentaje de cálculo de costo unitario";
                    break;
                case INT_TBL_DAT_TIP_REL:
                    strMsg = "Tipo de relación del inventario";
                    break;
                case INT_TBL_DAT_CAN_EGR_AUX:
                    strMsg = "Cantidad de Egreso Auxiliar";
                    break;
                case INT_TBL_DAT_CAN_ING_AUX:
                    strMsg = "Cantidad de Ingreso Auxiliar";
                    break;
                case INT_TBL_DAT_NUM_FIL_BLK:
                    strMsg = "Número de movimiento del bloque del item que genera el egreso";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }


    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Items".
     */
    private boolean configurarVenConItm()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("co_itmMae");
            arlCam.add("co_itmEmp");
            arlCam.add("co_itmGrp");
            arlCam.add("tx_codAlt");
            arlCam.add("tx_codAlt2");
            arlCam.add("tx_nomItm");
            arlCam.add("tx_desCor");
            arlCam.add("co_grp");
            arlCam.add("tx_desCorGrp");
            arlCam.add("tx_desLarGrp");
            arlCam.add("nd_can");
            arlCam.add("tx_tipRel");
            arlCam.add("nd_cosUniEmp");
            arlCam.add("nd_porCalCos");
            arlCam.add("nd_cosTotEgr");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.Mae.");
            arlAli.add("Cód.Itm.Emp.");
            arlAli.add("Cód.Itm.Grp.");
            arlAli.add("Cód.Alt.");
            arlAli.add("Cód.Let."); 
            arlAli.add("Nom.Itm."); 
            arlAli.add("Uni.Med.");  
            arlAli.add("Cód.Grp.");  
            arlAli.add("Des.Cor.Grp.");  
            arlAli.add("Nom.Grp.");  
            arlAli.add("Can.Egr.");
            arlAli.add("Tip.Rel.");
            arlAli.add("Cos.Uni.Emp.");
            arlAli.add("Por.Cal.Cos.");
            arlAli.add("Cos.Tot.Egr.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("80");
            arlAncCol.add("50");
            arlAncCol.add("176");
            arlAncCol.add("50");
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("20");
            arlAncCol.add("55");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            arlAncCol.add("60");
            
            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
//            strSQL+=" FROM tbm_inv AS a1";
//            strSQL+=" INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
//            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
//            strSQL+=" AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
//            strSQL+=" ORDER BY a1.tx_codAlt";


            strSQL="";
            strSQL+=" SELECT b1.co_itmMae, b1.co_itmGrp, b3.co_itmEmp, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_desCor";
            strSQL+=" , b2.co_grp, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel/*, b1.nd_cosUniGrp*/, ((b3.nd_cosUniEmp)*(-1)) AS nd_cosUniEmp, b2.nd_porCalCos";
            strSQL+=" , ((((b3.nd_cosUniEmp * b2.nd_porCalCos)/b2.nd_can)*b2.nd_can))*(-1) AS nd_cosTotEgr";
            strSQL+=" FROM(";
            strSQL+="       SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmGrp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor, a1.nd_cosUni AS nd_cosUniGrp";
            strSQL+="       FROM tbm_inv AS a1";
            strSQL+="       INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+="       LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+="       WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
            strSQL+="       AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
            strSQL+="       ORDER BY a1.tx_codAlt";
            strSQL+=" ) AS b1";
            strSQL+=" INNER JOIN(";
            strSQL+="       SELECT a1.co_emp, a1.co_grp,  a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a1.st_reg";
            strSQL+="       , a2.co_reg, a2.co_itm, a3.co_itmMae, a2.nd_can, a2.tx_tipRel, a2.st_reg, a2.nd_porCalCos";
            strSQL+="       FROM tbm_cabCfgConInv AS a1 INNER JOIN tbm_detCfgConInv AS a2";
            strSQL+="       ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
            strSQL+="       INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
            strSQL+=" 	    WHERE a2.tx_tipRel='E' AND a1.st_reg='A' AND a2.st_reg='A'";
            strSQL+=" ) AS b2";
            strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
            strSQL+=" INNER JOIN(";
            strSQL+="       SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a1.nd_cosUni AS nd_cosUniEmp";
            strSQL+="	    FROM tbm_inv AS a1";
            strSQL+=" 	    INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
            strSQL+=" 	    WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+="       AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
            strSQL+=" 	ORDER BY a1.tx_codAlt";
            strSQL+=" ) AS b3";
            strSQL+=" ON b1.co_itmMae=b3.co_itmMae";
            strSQL+=" ORDER BY b2.co_grp, b1.tx_codAlt";
            System.out.println("vcoItm: "+strSQL);
            //Ocultar Columnas
            int intColOcu[]=new int[6];
            intColOcu[0]=1;  // co_itmMae
            intColOcu[1]=2;  // co_itmGrp
            intColOcu[2]=3;  // co_itmEmp
            intColOcu[3]=8;  // co_grp
            intColOcu[4]=10;  // tx_desLarGrp
            intColOcu[5]=12;  // nd_porCalCos
//            intColOcu[6]=14;  // nd_cosTotEgr

            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Items a egresar", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            vcoItm.setConfiguracionColumna(11, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setConfiguracionColumna(13, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
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
     * Esta función muestra un mensaje de advertencia al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notifíquelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }
    
    
    /**
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm(){
        boolean blnRes=true;
        try{
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodBod.setText("");
            txtNomBod.setText("");
            txtCodDoc.setText("");
            txtNumDoc.setText("");
            txtValDoc.setText("");
            txaObs1.setText("");
            txaObs2.setText("");
            objTblMod.removeAllRows();
        }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta función muestra el tipo de documento predeterminado del programa.
     * @return true: Si se pudo mostrar el tipo de documento predeterminado.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarTipDocPre()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
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
                    strTipDocNecAutAnu=rst.getString("st_necautanudoc");
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    /**
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(insertar_tbmCabMovInv()){
                    if(insertar_tbmDetMovInv()){
                        //fin :   inserta documento para realizar conteo
                        if (objAsiDia.insertarDiario(con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), txtCodDoc.getText(), txtNumDoc.getText(), objUti.parseDate(dtpFecDoc.getText(),"dd/MM/yyyy"))){
                            costearItm();
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabMovInv(){
        int intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecDoc==null)
                    return false;

                getSecuencias();

                //Obtener el código del último registro.
                strSQL="";
                strSQL+=" SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                //System.out.println("insertar_tbmCabMovInv.tbm_cabMovInv: " + strSQL);
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabMovInv(";
                strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,ne_secGrp,ne_secEmp,ne_numCot,ne_numDoc,tx_numDoc2,";
                strSQL+="        tx_numPed,ne_numOrdDes,ne_numGui,co_dia,fe_doc,fe_ven,co_cli,tx_ruc,tx_nomCli,tx_dirCli,";
                strSQL+="        tx_telCli,tx_ciuCli,co_com,tx_nomVen,tx_ate,co_forPag,tx_desForPag,nd_sub,nd_porIva,";
                strSQL+="        nd_valIva,nd_tot,nd_pesTotKgr,tx_ptoPar,tx_tra,co_motTra,tx_desMotTra,co_cta,co_motDoc,";
                strSQL+="        co_mnu,st_imp,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultMod,co_usrIng,co_usrMod,tx_comIng,";
                strSQL+="        tx_comMod,fe_con,tx_obs3,co_forRet,tx_vehRet,tx_choRet,ne_numVecRecDoc,fe_ultRecDoc,";
                strSQL+="        tx_obsSolAut,tx_obsAutSol,st_aut,ne_valAut,st_tipDev,st_conInv,st_docGenDevMerMalEst,";
                strSQL+="        st_regRep,ne_numDocRee,st_creGuiRem,st_conInvTraAut,co_locRelSolDevVen,co_tipDocRelSolDevVen,";
                strSQL+="        co_docRelSolDevVen,st_excDocConVenCon,fe_autExcDocConVenCon,co_usrAutExcDocConVenCon,";
                strSQL+="        tx_comAutExcDocConVenCon,co_ben,tx_benChq,st_docConMerSalDemDebFac,st_autAnu,fe_autAnu,";
                strSQL+="        co_usrAutAnu,tx_comAutAnu,co_ptoDes,st_emiChqAntRecFacPrv,st_docMarLis,st_itmSerPro,";
                strSQL+="        ne_tipNotPed,co_exp,co_empRelPedEmbImp,co_locRelPedEmbImp,co_tipDocRelPedEmbImp,co_docRelPedEmbImp, st_cieDis, st_ingImp)";
                strSQL+=" VALUES (";
                strSQL+=" " + objParSis.getCodigoEmpresa() + ""; //co_emp
                strSQL+=", " + objParSis.getCodigoLocal() + ""; //co_loc
                strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc                
                strSQL+=", " + txtCodDoc.getText(); //co_doc
                strSQL+=", " + intSecGrp;//ne_secGrp
                strSQL+=", " + intSecEmp;//ne_secEmp
                strSQL+=", Null";//ne_numCot
                strSQL+=", " + objUti.codificar(txtNumDoc.getText(),2) + "";//ne_numDoc
                strSQL+=", Null";//tx_numDoc2
                strSQL+=", Null";//tx_numPed
                strSQL+=", Null";//ne_numOrdDes
                strSQL+=", Null";//ne_numGui
                strSQL+=", " + txtCodDoc.getText();//co_dia
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                strSQL+=", Null";//fe_ven
                strSQL+=", Null";//co_cli
                strSQL+=", Null";//tx_ruc
                strSQL+=", Null";//tx_nomCli
                strSQL+=", Null";//tx_dirCli
                strSQL+=", Null";//tx_telCli
                strSQL+=", Null";//tx_ciuCli
                strSQL+=", Null";//co_com
                strSQL+=", Null";//tx_nomVen
                strSQL+=", Null";//tx_ate
                strSQL+=", Null";//co_forPag
                strSQL+=", Null";//tx_desForPag
                strSQL+=", " + txtValDoc.getText();//nd_sub
                strSQL+=", 0";//nd_porIva
                strSQL+=", 0";//nd_valIva
                strSQL+=", " + txtValDoc.getText();//nd_tot
                strSQL+=", Null";//nd_pesTotKgr
                strSQL+=", Null";//tx_ptoPar
                strSQL+=", Null";//tx_tra
                strSQL+=", Null";//co_motTra
                strSQL+=", Null";//tx_desMotTra
                strSQL+=", Null";//co_cta
                strSQL+=", Null";//co_motDoc
                strSQL+=", " + objParSis.getCodigoMenu();//co_mnu
                strSQL+=", 'N'";//st_imp
                strSQL+=", " + objUti.codificar(txaObs1.getText());//tx_obs1
                strSQL+=", " + objUti.codificar(txaObs2.getText());//tx_obs2
                strSQL+=", 'A'";//st_reg
                strAux=objUti.formatearFecha(datFecDoc, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=", '" + strAux + "'";//fe_ing
                strSQL+=", '" + strAux + "'";//fe_ultMod
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrIng
                strSQL+=", " + objParSis.getCodigoUsuario();//co_usrMod
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comIng
                strSQL+=", " + objUti.codificar(objParSis.getNombreComputadoraConDirIP());//tx_comMod
                strSQL+=", Null";//fe_con
                strSQL+=", Null";//tx_obs3
                strSQL+=", Null";//co_forRet
                strSQL+=", Null";//tx_vehRet
                strSQL+=", Null";//tx_choRet
                strSQL+=", 0";//ne_numVecRecDoc
                strSQL+=", Null";//fe_ultRecDoc
                strSQL+=", Null";//tx_obsSolAut
                strSQL+=", Null";//tx_obsAutSol
                strSQL+=", Null";//st_aut
                strSQL+=", Null";//ne_valAut
                strSQL+=", 'C'";//st_tipDev
                strSQL+=", 'C'";//st_conInv
                strSQL+=", Null";//st_docGenDevMerMalEst
                strSQL+=", 'I'";//st_regRep
                strSQL+=", Null";//ne_numDocRee
                strSQL+=", 'N'";//st_creGuiRem
                strSQL+=", 'N'";//st_conInvTraAut
                strSQL+=", Null";//co_locRelSolDevVen
                strSQL+=", Null";//co_tipDocRelSolDevVen
                strSQL+=", Null";//co_docRelSolDevVen
                strSQL+=", Null";//st_excDocConVenCon
                strSQL+=", Null";//fe_autExcDocConVenCon
                strSQL+=", Null";//co_usrAutExcDocConVenCon
                strSQL+=", Null";//tx_comAutExcDocConVenCon
                strSQL+=", Null";//co_ben
                strSQL+=", Null";//tx_benChq
                strSQL+=", 'N'";//st_docConMerSalDemDebFac
                strSQL+=", Null";//st_autAnu
                strSQL+=", Null";//fe_autAnu
                strSQL+=", Null";//co_usrAutAnu
                strSQL+=", Null";//tx_comAutAnu
                strSQL+=", Null";//co_ptoDes             objUti.codificar(txtCodBod.getText())
                strSQL+=", Null";//st_emiChqAntRecFacPrv
                strSQL+=", Null";//st_docMarLis
                strSQL+=", Null";//st_itmSerPro
                strSQL+=", Null"; //ne_tipNotPed
                strSQL+=", Null";//co_exp
                strSQL+=", Null";//co_empRelPedEmbImp
                strSQL+=", Null";//co_locRelPedEmbImp
                strSQL+=", Null";//co_tipDocRelPedEmbImp
                strSQL+=", Null";//co_docRelPedEmbImp
                strSQL+=", Null";//st_cieDis               
                strSQL+=", Null";//st_ingImp
                strSQL+=");";
                System.out.println("insertar_tbmCabMovInv: " + strSQL);
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
     * Esta función permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetMovInv(){
        boolean blnRes=true;
        String strSQLIns="";
        int j=1;
        //int intCodBodEmp=-1;
        BigDecimal bdeCanEgr=BigDecimal.ZERO;
        BigDecimal bdeCanIng=BigDecimal.ZERO;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        BigDecimal bdeSigCanEgr=BigDecimal.ZERO;
        String strTipRelEgr="";
        int intNumFil=0;
        try{
            if (con!=null){
                stm=con.createStatement();
                //intCodBodEmp=getCodigoBodegaEmpresa();
                arlDatStkInvItm.clear();
                arlDatStkInvItmEgrIng.clear();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detMovInv(";
                    strSQL+="        co_emp,co_loc,co_tipDoc,co_doc,co_reg,ne_numFil,co_itm,co_itmAct,tx_codAlt,tx_codAlt2,";
                    strSQL+="        tx_nomItm,tx_uniMed,co_bod,tx_nomBodOrgDes,nd_can,nd_canOrg,nd_canDev,nd_cosUni,nd_preUniVenLis,";
                    strSQL+="        nd_porDesVenMax,nd_preUni,nd_porDes,st_ivaCom,nd_tot,nd_cosTot,nd_exi,nd_valExi,nd_cosPro,";
                    strSQL+="        nd_cosUniGrp,nd_cosTotGrp,nd_exiGrp,nd_valExiGrp,nd_cosProGrp,st_merIngEgrFisBod,nd_canCon,";
                    strSQL+="        nd_canNunRec,nd_canTotNunRecPro,nd_canTotMalEst,nd_canTotMalEstPro,tx_obs1,co_usrCon,";
                    strSQL+="        co_locRelSolDevVen,co_tipDocRelSolDevVen,co_docRelSolDevVen,co_regRelSolDevVen,co_locRelSolSalTemMer,";
                    strSQL+="        co_tipDocRelSolSalTemMer,co_docRelSolSalTemMer,co_regRelSolSalTemMer,st_cliRetEmpRel,tx_obsCliRetEmpRel,";
                    strSQL+="        nd_ara,nd_preUniImp,nd_valTotFobCfr,nd_cantonmet,nd_valFle,nd_valCfr,nd_valTotAra,nd_valTotGas,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+=" " + objParSis.getCodigoEmpresa() + ""; //co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + ""; //co_loc
                    strSQL+=", " + txtCodTipDoc.getText(); //co_tipdoc
                    strSQL+=", " + txtCodDoc.getText(); //co_doc
                    strSQL+=", " + j; //co_reg
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK) + "";//ne_numFil
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itm
                    strSQL+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP);//co_itmAct
                    strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ALT_ITM) + "'";//tx_codAlt
                    strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM) + "'";//tx_codAlt2
                    strSQL+=", " + objUti.codificar(objTblMod.getValueAt(i, INT_TBL_DAT_NOM_ITM)) + "";//tx_nomItm
                    strSQL+=", '" + objTblMod.getValueAt(i, INT_TBL_DAT_UNI_MED) + "'";//tx_uniMed
                    strSQL+=", " + txtCodBod.getText() + "";//co_bod
                    strSQL+=", '" + txtNomBod.getText() + "'";//tx_nomBodOrgDes
                    
                    //Cantidad
                    bdeCanEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_EGR).toString()));
                    bdeCanIng=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING).toString()));                    
                    bdeSigCanEgr=(bdeCanEgr.compareTo(bdeCanIng)>0?new BigDecimal("-1"):new BigDecimal("1"));
                    bdeCanEgrIng=(bdeCanEgr.compareTo(bdeCanIng)>0?(bdeCanEgr.multiply(bdeSigCanEgr)):(bdeCanIng.multiply(bdeSigCanEgr)));
                    
                    strSQL+=", " + bdeCanEgrIng + "";//nd_can
                    strSQL+=", Null";//nd_canOrg
                    strSQL+=", 0";//nd_canDev
                    strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4).abs() + "";//nd_cosUni
                    strSQL+=", Null";//nd_preUniVenLis
                    strSQL+=", Null";//nd_porDesVenMax
                    strSQL+=", Null";//nd_preUni
                    strSQL+=", 0";//nd_porDes
                    strSQL+=", 'N'";//st_ivaCom
                    strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_tot 'precio al publico'
                    strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosTot 'precio de compra para vender'
                    strSQL+=", 0";//nd_exi
                    strSQL+=", 0";//nd_valExi
                    strSQL+=", 0";//nd_cosPro
                    strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosUniGrp
                    strSQL+=", " + objUti.redondearBigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()), /*objParSis.getDecimalesBaseDatos()*/ 4) + "";//nd_cosTotGrp
                    strSQL+=", 0";//nd_exiGrp
                    strSQL+=", 0";//nd_valExiGrp
                    strSQL+=", 0";//nd_cosProGrp                    
                    strSQL+=", 'S'";//st_merIngEgrFisBod
                    strSQL+=", 0";//nd_canCon
                    strSQL+=", 0";//nd_canNunRec
                    strSQL+=", Null";//nd_canTotNunRecPro
                    strSQL+=", Null";//nd_canTotMalEst
                    strSQL+=", Null";//nd_canTotMalEstPro
                    strSQL+=", Null";//tx_obs1
                    strSQL+=", Null";//co_usrCon
                    strSQL+=", Null";//co_locRelSolDevVen
                    strSQL+=", Null";//co_tipDocRelSolDevVen
                    strSQL+=", Null";//co_docRelSolDevVen
                    strSQL+=", Null";//co_regRelSolDevVen
                    strSQL+=", Null";//co_locRelSolSalTemMer
                    strSQL+=", Null";//co_tipDocRelSolSalTemMer
                    strSQL+=", Null";//co_docRelSolSalTemMer
                    strSQL+=", Null";//co_regRelSolSalTemMer
                    strSQL+=", Null";//st_cliRetEmpRel
                    strSQL+=", Null";//tx_obsCliRetEmpRel
                    strSQL+=", Null";//nd_ara
                    strSQL+=", Null";//nd_preUniImp
                    strSQL+=", Null";//nd_valTotFobCfr
                    strSQL+=", Null";//nd_cantonmet
                    strSQL+=", Null";//nd_valFle
                    strSQL+=", Null";//nd_valCfr
                    strSQL+=", Null";//nd_valTotAra
                    strSQL+=", Null";//nd_valTotGas
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";                    
                    strSQLIns+=strSQL;
                    j++;
                    if(objTooBar.getEstado()=='n'){
                        strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                        if(strTipRelEgr.equals("E")){
                            arlRegStkInvItm = new ArrayList();
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_GRP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_LET_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM));
                            arlRegStkInvItm.add(objStkInv.INT_ARL_CAN_ITM,     bdeCanEgrIng);//el valor está en (+) y en (-)
                            arlRegStkInvItm.add(objStkInv.INT_ARL_COD_BOD_EMP, txtCodBod.getText());
                            arlDatStkInvItm.add(arlRegStkInvItm);
                        }
                        //se almacena todo, sea ingreso o egreso
                        arlRegStkInvItmEgrIng=new ArrayList();
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_GRP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_EMP, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_ITM_MAE, objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_LET_ITM, objTblMod.getValueAt(i, INT_TBL_DAT_COD_LET_ITM));
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_CAN_ITM,     bdeCanEgrIng);//el valor está en (+) y en (-)
                        arlRegStkInvItmEgrIng.add(objStkInv.INT_ARL_COD_BOD_EMP, txtCodBod.getText());
                        arlDatStkInvItmEgrIng.add(arlRegStkInvItmEgrIng);
                        
                    }
                }
                if(objTooBar.getEstado()=='n'){
                    if(objStkInv.validaStk_Dis_InvBod(con, objParSis.getCodigoEmpresa(), arlDatStkInvItm, 1, "+", 0)){//valida stock de inventario (Egreso)
                        if(objStkInv.validaStk_Dis_InvBod(con, objParSis.getCodigoEmpresa(), arlDatStkInvItm, 1, "+", 10)){//valida disponible de inventario (Egreso)
                            if(objStkInv.actualizaInventario(con, objParSis.getCodigoEmpresa(), 0, "+", 1, arlDatStkInvItmEgrIng)){//tbm_inv  (egreso e ingreso)
                                    if(objStkInv.actualizaDisponible(con, objParSis.getCodigoEmpresa(), 10, "+", 1, arlDatStkInvItmEgrIng)){ // disponible (ingreso/egreso)
                                        arlDatStkInvItm.clear();
                                    }
                                    else{
                                        mostrarMsgInf("Es posible que existan problemas de disponible");
                                        blnRes=false;
                                    }
                            }
                            else{
                                mostrarMsgInf("<HTML>Existen problemas de stock de inventario.<BR> Verifique el item : <FONT COLOR=\"blue\">" + objStkInv.getStrCodAltItmSinStk() + "</FONT>.</HTML>");
                                blnRes=false;
                            }
                        }
                        else{
                            mostrarMsgInf("Es posible que existan problemas de disponible de inventario");
                            blnRes=false;
                        }
                    }
                    else{
                        mostrarMsgInf("<HTML>Existen problemas de inventario de bodega.<BR> Verifique y vuelva a intentarlo.</HTML>");
                        blnRes=false;
                    }
                }
                System.out.println("insertar_tbmDetMovInv: " + strSQLIns);
                stm.executeUpdate(strSQLIns);
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
     * Esta función permite costear los items de inventario.
     * @return true: Si se pudo recostear.
     * <BR>false: En el caso contrario.
     */
    private boolean costearItm(){
        int i;
        boolean blnRes=true;
        try{
            if (con!=null){
                switch (objTooBar.getEstado()){                
                    case 'n':
                        System.out.println("costearDocumento");
                        objUti.costearDocumento(this, objParSis, con, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), Integer.parseInt(txtCodTipDoc.getText()), Integer.parseInt(txtCodDoc.getText()));
                        break;
                    case 'm':
                        //Validar la fecha  del documento sólo si se encuentra en el modo "Modificar".
                        //Validar si se utiliza la fecha que tenia el documento inicialmente o la que tiene actualmente.
                        if (datFecDoc.compareTo(objUti.parseDate(dtpFecDoc.getText(), "dd/MM/yyyy"))>0){
                            for (i=0;i<objTblMod.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            }
                        }
                        else{
                            for (i=0;i<objTblMod.getRowCountTrue();i++){
                                System.out.println("recostearItm");
                                //recostea la empresa
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                                //recostea el grupo
                                objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"), "dd/MM/yyyy");
                            }
                        }
                        break;
                    default:
                        for (i=0;i<objTblMod.getRowCountTrue();i++) {
                            System.out.println("recostearItm");
                            //recostea la empresa
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresa(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_EMP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
                            //recostea el grupo
                            objUti.recostearItm2009DesdeFecha(this, objParSis, con, objParSis.getCodigoEmpresaGrupo(), objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_GRP).toString(), dtpFecDoc.getText(), "dd/MM/yyyy");
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
     * Función que devuelve el código de bodega por empresa
     * @param conexion Conexión a la base de datos
     * @return el Código de la bodega por empresa
     */
    private int getCodigoBodegaEmpresa(){
        int intCodBodEmp=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_bod, a1.co_empgrp, a1.co_bodgrp";
                strSQL+=" FROM tbr_bodempbodgrp AS a1";
                strSQL+=" WHERE a1.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.co_bodGrp=" + txtCodBod.getText() + "";
                strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    intCodBodEmp=rst.getInt("co_bod");
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intCodBodEmp;
    }
    
    
    /**
     * Función que permite obtener las secuencias de grupo y secuencias de empresa
     * @return true Si se pudo realizar la operación
     */
    private boolean getSecuencias(){
        boolean blnRes=true;
        try{
            if(con!=null){
                intSecGrp=objUltDocPrint.getNumSecDoc(con, objParSis.getCodigoEmpresaGrupo());//la empresa es el grupo
                intSecEmp=objUltDocPrint.getNumSecDoc(con, objParSis.getCodigoEmpresa());//la empresa-importador
           }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }

    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if(txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        if(txtCodBod.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Bodega</FONT> es obligatorio.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
            txtNomBod.requestFocus();
            return false;
        }
        
        if (txtNumDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un Número de documento y vuelva a intentarlo.</HTML>");
            txtNumDoc.requestFocus();
            return false;
        }
        if(objTooBar.getEstado()=='n'){
            if(!objTblMod.isDataModelChanged()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
                return false;
            }

            if(objTblMod.getRowCountTrue()<=0){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                objTblMod.removeEmptyRows();
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>La tabla de Items y valores no ha tenido cambios.<BR>No hay datos nuevos por guardar.</HTML>");
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
        if(isExisteItemRepetido()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Un item fue ingresado varias veces.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        
        if(!isPerIngDocCon()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se ha excedido el número máximo de egresos de un item con fecha actual.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false;  
        }
        if(isCosUniCero()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Existen costos unitarios en cero.<BR>Verifique y vuelva a intentarlo.</HTML>");
            return false; 
        }
        
        objTblMod.removeEmptyRows();
        
        return true;
    }

    /**
     * Función que permite determinar si algún item fue ingresado varias veces en el modelo de datos
     * @return true: Si el item fue ingresado varias veeces
     * <BR> false: Caso contrario
     */
    private boolean isExisteItemRepetido(){
        boolean blnRes=false;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                for(int j=(i+1); j<objTblMod.getRowCountTrue(); j++){
                    if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_GRP, j, INT_TBL_DAT_COD_GRP)){
                        if(objTblMod.compareStringCells(i, INT_TBL_DAT_COD_ITM_MAE, j, INT_TBL_DAT_COD_ITM_MAE)){
                            blnRes=true;
                            break;
                        }
                    }


                }
                if(blnRes)
                    break;
            }
            
        }
        catch (Exception e){
            blnRes=true;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
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
     * Esta funcián permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if (txtCodTipDoc.getText().equals("")){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                    strSQL+=", a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
                    strSQL+=" FROM (          (      (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                    strSQL+=" 			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                    strSQL+=" 			)";
                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                    strSQL+="   )";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    if(objParSis.getCodigoUsuario()==1){
                        strSQL+=" INNER JOIN tbr_tipDocPrg AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    }
                    else{
                        strSQL+=" INNER JOIN tbr_tipDocUsr AS a5 ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                        strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + " AND a5.co_usr=" + objParSis.getCodigoUsuario() + "";
                    }

                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.co_bod= " + strAux + "";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                    strSQL+=", a1.co_usrIng, a15.tx_usr AS tx_nomUsrIng";
                    strSQL+=" , a1.co_usrMod, a16.tx_usr AS tx_nomUsrMod, a1.co_mnu, a6.co_bod";
                    strSQL+=" FROM (          (      (tbm_cabMovInv AS a1 INNER JOIN tbm_detMovInv AS a6";
                    strSQL+=" 			ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                    strSQL+=" 			)";
                    strSQL+=" 		 LEFT OUTER JOIN tbm_usr AS a15 ON a1.co_usrIng=a15.co_usr)";
                    strSQL+="		 LEFT OUTER JOIN tbm_usr AS a16 ON a1.co_usrMod=a16.co_usr";
                    strSQL+="   )";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" WHERE a1.co_mnu=" + objParSis.getCodigoMenu() + "";

                    strSQL+=" AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strAux=txtCodBod.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a6.co_bod= " + strAux + "";

                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc = " + strAux + "";
                
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                strAux=txtNumDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc = " + strAux.replaceAll("'", "''") + "";
                
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc = " + strAux.replaceAll("'", "''") + "";

                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc";
                strSQL+=" , a1.co_usrIng, a15.tx_usr, a1.co_usrMod, a16.tx_usr, a1.co_mnu, a6.co_bod";
                strSQL+=" ORDER BY a1.co_tipDoc, a1.co_doc";
                System.out.println("consultarReg:  " + strSQL);
                rstCab=stmCab.executeQuery(strSQL);
                arlDatConDoc = new ArrayList();
                while(rstCab.next()){
                    arlRegConDoc = new ArrayList();
                    arlRegConDoc.add(INT_ARL_CON_COD_EMP,       rstCab.getInt("co_emp"));
                    arlRegConDoc.add(INT_ARL_CON_COD_LOC,       rstCab.getInt("co_loc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_TIP_DOC,   rstCab.getString("co_tipDoc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_DOC,       rstCab.getString("co_doc"));
                    arlRegConDoc.add(INT_ARL_CON_COD_USR_ING,   rstCab.getString("co_usrIng"));
                    arlRegConDoc.add(INT_ARL_CON_COD_USR_MOD,   rstCab.getString("co_usrMod"));
                    arlDatConDoc.add(arlRegConDoc);
                }
                stmCab.close();
                stmCab=null;
                rstCab.close();
                rstCab=null;
                conCab.close();
                conCab=null;
                
                if(arlDatConDoc.size()>0){
                    objTooBar.setMenSis("Se encontraron " + (arlDatConDoc.size()) + " registros");
                    intIndReg=arlDatConDoc.size()-1;
                    cargarReg();
                }
                else{
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
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
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if(cargarCabReg()){
                if(cargarDetReg()){
                    if(objAsiDia.consultarDiario( objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP)
                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC)
                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC)
                                                    , objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC))){
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
     * Esta función permite cargar la cabecera del registro seleccionado.
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
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a2.tx_natDoc";
                strSQL+=" , a1.nd_tot, a1.tx_obs1, a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a6.co_bod, a7.tx_nom AS tx_nomBod";
                strSQL+=" FROM (  tbm_cabMovInv AS a1";
                strSQL+="           INNER JOIN (tbm_detMovInv AS a6";
                strSQL+=" 	               INNER JOIN tbm_bod AS a7 ON a6.co_emp=a7.co_emp AND a6.co_bod=a7.co_bod";
                strSQL+="                       INNER JOIN tbr_bodEmpBodGrp AS a8 ON a7.co_emp=a8.co_emp AND a7.co_bod=a8.co_bod";
                strSQL+=" 	    )";
                strSQL+="  	    ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc AND a1.co_doc=a6.co_doc";
                strSQL+="   )";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+="  WHERE a1.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP);
                strSQL+="  AND a1.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC);
                strSQL+="  AND a1.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC);
                strSQL+="  AND a1.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC);
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.fe_doc,a1.ne_numdoc, a2.tx_natDoc";
                strSQL+=" , a1.nd_tot, a1.tx_obs1";
                strSQL+=" , a1.tx_obs2, a1.st_reg, a2.tx_desCor, a2.tx_desLar, a2.tx_natDoc, a6.co_bod, a7.tx_nom";
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
                    strAux=rst.getString("co_bod");//código de bodega de la empresa
                    txtCodBod.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nomBod");//nombre de bodega de la empresa
                    txtNomBod.setText((strAux==null)?"":strAux);
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    datFecDoc=rst.getDate("fe_doc");
                    strAux=rst.getString("ne_numdoc");
                    txtNumDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getObject("nd_tot")==null?"0":(rst.getString("nd_tot").equals("")?"0":rst.getString("nd_tot"));
                    txtValDoc.setText("" + (objUti.redondearBigDecimal(strAux, objParSis.getDecimalesMostrar())));
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                }
                else{
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
            
            //Mostrar la posición relativa del registro.
            intPosRel = intIndReg+1;
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + (arlDatConDoc.size()) );
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
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        BigDecimal bdeCanEgrIng=BigDecimal.ZERO;
        try{
            objTblMod.removeAllRows();
            if (!txtCodTipDoc.getText().equals("")){
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+=" SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.ne_numFil, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2";
                    strSQL+=" , a2.tx_nomItm, a2.tx_uniMed, a2.co_bod, a2.tx_nomBodOrgDes, a2.nd_can, a2.nd_cosUni, a2.nd_tot, a2.nd_cosTot";
                    strSQL+=" FROM tbm_detMovInv AS a2";
                    strSQL+=" WHERE a2.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP) + "";
                    strSQL+=" AND a2.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC) + "";
                    strSQL+=" AND a2.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC) + "";
                    strSQL+=" AND a2.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC) + "";
                    strSQL+=" ORDER BY a2.ne_numFil, a2.co_reg";
                    

                      //COMO NO EXISTE RELACION CON EL CODIGO DE GRUPO, SI EXISTE EL MISMO ITEM DE EGRESO CONFIGURADO MAS DE UNA VEZ
                      //, ESE MISMO NUMERO DE VECES SE VERA EN CADA DOCUMENTO INGRESADO, YA QUE NO HAY FORMA DE DISCRIMINAR EL ITEM POR CONFIGURACION YA QUE NO HAY RELACION EN tbm_detMovInv con el grupo de conversión.
//                    strSQL="";
//                    strSQL+=" SELECT b1.co_emp, b1.co_itmMae, b1.co_itmGrp, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_desCor";
//                    strSQL+=" , b2.co_grp, b2.co_reg, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.tx_tipRel, b2.nd_porCalCos, b3.co_itmEmp";
//                    strSQL+=" , b3.co_reg, b3.ne_numFil, b3.tx_codAlt, b3.tx_codAlt2, b3.tx_nomItm, b3.tx_uniMed, b3.nd_can, b3.nd_cosUni, b3.nd_cosTot";
//                    strSQL+=" FROM(";
//                    strSQL+="    SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmGrp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
//                    strSQL+="    FROM tbm_inv AS a1";
//                    strSQL+="    INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
//                    strSQL+="    LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
//                    strSQL+="    WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
//                    strSQL+="    AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
//                    strSQL+="    ORDER BY a1.tx_codAlt";
//                    strSQL+=" ) AS b1";
//                    strSQL+=" INNER JOIN(";
//                    strSQL+="    SELECT a1.co_emp, a1.co_grp, a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a1.st_reg";
//                    strSQL+="    , a2.co_reg, a2.co_itm, a3.co_itmMae, a2.nd_can, a2.tx_tipRel, a2.st_reg, a2.nd_porCalCos";
//                    strSQL+="    FROM tbm_cabCfgConInv AS a1 INNER JOIN tbm_detCfgConInv AS a2";
//                    strSQL+="    ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
//                    strSQL+="    INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
//                    strSQL+="    WHERE /*a2.tx_tipRel='I' AND*/ a1.st_reg='A' AND a2.st_reg='A'";
//                    strSQL+=" ) AS b2";
//                    strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
//                    strSQL+=" INNER JOIN(";
//                    strSQL+=" 	SELECT b2.co_emp, b2.co_loc, b2.co_tipDoc, b2.co_doc, b2.co_reg, b2.ne_numFil, b1.co_itmEmp, b2.tx_codAlt, b2.tx_codAlt2";
//                    strSQL+=" 	, b2.tx_nomItm, b2.tx_uniMed, b2.co_bod, b2.tx_nomBodOrgDes, b2.nd_can, b2.nd_cosUni, b2.nd_tot, b2.nd_cosTot, b2.co_itmMae";
//                    strSQL+=" 	FROM(";
//                    strSQL+=" 		   SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm";
//                    strSQL+=" 		   FROM tbm_inv AS a1 INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
//                    strSQL+=" 		   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
//                    strSQL+="                    AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
//                    strSQL+=" 		   ORDER BY a1.tx_codAlt";
//                    strSQL+=" 	) AS b1";
//                    strSQL+=" 	INNER JOIN(";
//                    strSQL+=" 		SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg, a2.ne_numFil, a2.co_itm, a2.tx_codAlt, a2.tx_codAlt2";
//                    strSQL+=" 		, a2.tx_nomItm, a2.tx_uniMed, a2.co_bod, a2.tx_nomBodOrgDes, a2.nd_can, a2.nd_cosUni, a2.nd_tot, a2.nd_cosTot, a1.co_itmMae";
//                    strSQL+=" 		FROM tbm_detMovInv AS a2 INNER JOIN tbm_equInv AS a1 ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
//                    strSQL+="           WHERE a2.co_emp=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_EMP) + "";
//                    strSQL+="           AND a2.co_loc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_LOC) + "";
//                    strSQL+="           AND a2.co_tipDoc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_TIP_DOC) + "";
//                    strSQL+="           AND a2.co_doc=" + objUti.getIntValueAt(arlDatConDoc, intIndReg, INT_ARL_CON_COD_DOC) + "";
//                    strSQL+=" 		ORDER BY a2.ne_numFil, a2.co_reg";
//                    strSQL+=" 	) AS b2";
//                    strSQL+=" 	ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
//                    strSQL+=" ) AS b3";
//                    strSQL+=" ON b1.co_itmMae=b3.co_itmMae";
//                    strSQL+=" ORDER BY b3.ne_numFil, b3.co_reg";
                    System.out.println("cargarDetReg: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    //Limpiar vector de datos.
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
//          segundo query inicio
//                        vecReg.add(INT_TBL_DAT_LIN,"");
//                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    rst.getString("co_itmMae"));
//                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,    rst.getString("co_itmGrp"));
//                        vecReg.add(INT_TBL_DAT_COD_ITM_EMP,    rst.getString("co_itmEmp"));
//                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
//                        vecReg.add(INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
//                        vecReg.add(INT_TBL_DAT_BUT_ITM,        null);
//                        vecReg.add(INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
//                        vecReg.add(INT_TBL_DAT_UNI_MED,        rst.getString("tx_uniMed"));                        
//                        vecReg.add(INT_TBL_DAT_COD_GRP,        rst.getString("co_grp"));
//                        vecReg.add(INT_TBL_DAT_DES_COR_GRP,    rst.getString("tx_desCorGrp"));
//                        vecReg.add(INT_TBL_DAT_DES_LAR_GRP,    rst.getString("tx_desLarGrp"));                        
//                        bdeCanEgrIng=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
//                        
//                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
//                            vecReg.add(INT_TBL_DAT_CAN_EGR,   "0");
//                            vecReg.add(INT_TBL_DAT_CAN_ING,   bdeCanEgrIng);
//                        }
//                        else{//Egreso
//                            vecReg.add(INT_TBL_DAT_CAN_EGR,   bdeCanEgrIng.abs());
//                            vecReg.add(INT_TBL_DAT_CAN_ING,   "0");
//                        }                        
//                        vecReg.add(INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
//                        vecReg.add(INT_TBL_DAT_COS_TOT,        rst.getString("nd_cosTot"));
//                        vecReg.add(INT_TBL_DAT_POR_CAL_COS_UNI,rst.getString("nd_porCalCos"));
//                        vecReg.add(INT_TBL_DAT_TIP_REL,        rst.getString("tx_tipRel"));
//                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
//                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   "0");
//                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   bdeCanEgrIng);
//                        }
//                        else{//Egreso
//                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   bdeCanEgrIng);
//                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   "0");
//                        }
//                        vecReg.add(INT_TBL_DAT_NUM_FIL_BLK,    rst.getString("ne_numFil"));
//                        vecDat.add(vecReg);
                //segundo query fin
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM_MAE,    "");
                        vecReg.add(INT_TBL_DAT_COD_ITM_GRP,    "");
                        vecReg.add(INT_TBL_DAT_COD_ITM_EMP,    rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,    rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_COD_LET_ITM,    rst.getString("tx_codAlt2"));
                        vecReg.add(INT_TBL_DAT_BUT_ITM,        null);
                        vecReg.add(INT_TBL_DAT_NOM_ITM,        rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,        rst.getString("tx_uniMed"));                        
                        vecReg.add(INT_TBL_DAT_COD_GRP,        "");
                        vecReg.add(INT_TBL_DAT_DES_COR_GRP,    "");
                        vecReg.add(INT_TBL_DAT_DES_LAR_GRP,    "");                        
                        bdeCanEgrIng=new BigDecimal(rst.getObject("nd_can")==null?"0":(rst.getString("nd_can").equals("")?"0":rst.getString("nd_can")));
                        
                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR,   "0");
                            vecReg.add(INT_TBL_DAT_CAN_ING,   bdeCanEgrIng);
                        }
                        else{//Egreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR,   bdeCanEgrIng.abs());
                            vecReg.add(INT_TBL_DAT_CAN_ING,   "0");
                        }                        
                        vecReg.add(INT_TBL_DAT_COS_UNI,        rst.getString("nd_cosUni"));
                        vecReg.add(INT_TBL_DAT_COS_TOT,        rst.getString("nd_cosTot"));
                        vecReg.add(INT_TBL_DAT_POR_CAL_COS_UNI,"");
                        vecReg.add(INT_TBL_DAT_TIP_REL,        "");
                        if(bdeCanEgrIng.compareTo(BigDecimal.ZERO)>0){//Ingreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   "0");
                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   bdeCanEgrIng);
                        }
                        else{//Egreso
                            vecReg.add(INT_TBL_DAT_CAN_EGR_AUX,   bdeCanEgrIng);
                            vecReg.add(INT_TBL_DAT_CAN_ING_AUX,   "0");
                        }
                        vecReg.add(INT_TBL_DAT_NUM_FIL_BLK,    rst.getString("ne_numFil"));
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
                    vecDat.clear();
                }
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    /**
     * Esta función obtiene la descripción larga del estado del registro.
     * @param estado El estado del registro. Por ejemplo: A, I, etc.
     * @return La descripción larga del estado del registro.
     * <BR>Nota.- Si la cadena recibida es <I>null</I> la función devuelve una cadena vacía.
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
                case 'P':
                    estado="Pendiente de autorizar";
                    break;
                case 'D':
                    estado="Autorización denegada";
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
     * Esta función configura la "Ventana de consulta" que será utilizada para
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
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
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

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
                        
                        txtCodBod.setText("");
                        txtNomBod.setText("");
                    }
                    break;
                case 1: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");
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
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
                            if (objTooBar.getEstado()=='n')
                            {
                                strAux=vcoTipDoc.getValueAt(4);
                                txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                            txtNumDoc.selectAll();
                            txtNumDoc.requestFocus();
                            
                            txtCodBod.setText("");
                            txtNomBod.setText("");
                        }
                        else
                        {
                            txtDesLarTipDoc.setText(strDesLarTipDoc);
                        }
                    }
                    break;
                case 3: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));

                        if (objTooBar.getEstado()=='n'){
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        strTipDocNecAutAnu=vcoTipDoc.getValueAt(6);
                        txtNumDoc.selectAll();
                        txtNumDoc.requestFocus();
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
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar las "Bodegas".
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
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" GROUP BY a1.co_bod, a1.tx_nom";
                strSQL+=" ORDER BY a1.co_bod, a1.tx_nom";
            }
            else{
//                strSQL="";
//                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
//                strSQL+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodLocPrgUsr AS a2";
//                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
//                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";


                strSQL="";
                strSQL+=" SELECT a1.co_bod, a1.tx_nom";
                strSQL+=" FROM tbm_bod AS a1 INNER JOIN tbr_bodLocPrgUsr AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" GROUP BY a1.co_bod, a1.tx_nom";
                strSQL+=" ORDER BY a1.co_bod, a1.tx_nom";
            }


            vcoBod = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
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
        try{
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

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    
    
    
    
    
    /**
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Además de los botones de navegación
     * que permiten desplazarse al primero, anterior, siguiente y último registro.
     */
    private class MiToolBar extends ZafToolBar{
        public MiToolBar(javax.swing.JInternalFrame ifrFrm){
            super(ifrFrm, objParSis);
        }

        public void clickInicio() {
            try{
                if(arlDatConDoc.size()>0){
                    if(intIndReg>0){
                        if((blnHayCam)){
                            if(isRegPro()) {
                                intIndReg=0;
                                cargarReg();
                            }
                        }
                        else{
                            intIndReg=0;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try{
                if(arlDatConDoc.size()>0){
                    if(intIndReg>0){
                        if ((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg--;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg--;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try{
                 if(arlDatConDoc.size()>0){
                    if(intIndReg < arlDatConDoc.size()-1){
                        if ((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg++;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg++;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try{
                 if(arlDatConDoc.size()>0){
                    if(intIndReg<arlDatConDoc.size()-1){
                        if((blnHayCam)){
                            if (isRegPro()) {
                                intIndReg=arlDatConDoc.size()-1;
                                cargarReg();
                            }
                        }
                        else {
                            intIndReg=arlDatConDoc.size()-1;
                            cargarReg();
                        }
                    }
                }
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickInsertar() {
            try{
                if ((blnHayCam)){
                    isRegPro();
                }

                //para insertar no se los puede cambiar
                txtCodTipDoc.setEditable(false);
                txtCodDoc.setEditable(false);
                txtValDoc.setEditable(false);
                
                limpiarFrm();
                mostrarTipDocPre();
                datFecDoc=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setEnabled(true);
                dtpFecDoc.setText(objUti.formatearFecha(datFecDoc,"dd/MM/yyyy"));
                txtDesCorTipDoc.selectAll();
                txtDesCorTipDoc.requestFocus();
                
//                objTblMod.setModoOperacion(objTooBar.getEstado());
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickConsultar(){
            txtDesCorTipDoc.setEditable(true);
            txtDesLarTipDoc.setEditable(true);
            butTipDoc.setEnabled(true);
            txtCodBod.setEditable(true);
            txtNomBod.setEditable(true);
            butBod.setEnabled(true);
            txtCodDoc.setEditable(true);
            dtpFecDoc.setEnabled(true);
            txtNumDoc.setEditable(true);
            txtValDoc.setEditable(false);
        }

        public void clickModificar(){
        }

        public void clickEliminar(){
        }

        public void clickAnular(){
        }

        public void clickImprimir(){
        }

        public void clickVisPreliminar(){
        }

        public void clickAceptar(){
        }

        public void clickCancelar(){
        }

        public boolean insertar(){
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public boolean modificar(){
            return true;
        }

        public boolean eliminar(){
            return true;
        }

        public boolean anular(){
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean vistaPreliminar() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
                if((blnHayCam)){
                    if(objTooBar.getEstado()=='n'){
                        if (!isRegPro())
                            return false;
                    }
                }
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
        }

        public boolean beforeInsertar() {
            if (!isCamVal())
                return false;

            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            return true;
        }

        public boolean beforeEliminar() {
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }

        public boolean afterInsertar() {
            this.setEstado('w');
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean afterModificar() {
            return true;
        }

        public boolean afterEliminar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        
    }


    
    private boolean cargarItemIngreso(String codigoGrupo, String costoUnitarioEgreso){
        boolean blnRes=true;
        Connection conItmIng;
        Statement stmItmIng;
        ResultSet rstItmIng;
        int intFilAdd=-1;
        BigDecimal bdeCosUniEgr=new BigDecimal(costoUnitarioEgreso==null?"0":(costoUnitarioEgreso.equals("")?"0":costoUnitarioEgreso)).abs();
        try{
            conItmIng=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conItmIng!=null){
                stmItmIng=conItmIng.createStatement();
                intFilAdd=(objTblMod.getRowCountTrue());
                System.out.println("intFilAdd: " + intFilAdd);
                
                strSQL="";
                strSQL+=" SELECT c1.nd_cosUniEgr, c1.co_emp, c1.co_itmMae, c1.co_itm, c1.tx_codAlt, c1.tx_codAlt2, c1.tx_nomItm, c1.tx_desCor";
                strSQL+=" , c1.co_grp, c1.co_reg, c1.tx_desCorGrp, c1.tx_desLarGrp, c1.nd_can, c1.tx_tipRel, c1.nd_porCalCos, c1.co_itmEmp";
                
                
                strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE ((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can) END AS nd_cosUniIng";
                strSQL+=" , CASE WHEN (c1.nd_cosUniEgr IS NULL OR c1.nd_cosUniEgr=0) THEN 0 ELSE (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can) END AS nd_cosTotIng";
                
                
                //strSQL+="  , ((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can) AS nd_cosUniIng";
                //strSQL+=" , (((c1.nd_cosUniEgr * c1.nd_porCalCos)/c1.nd_can)*c1.nd_can) AS nd_cosTotIng";
                
                strSQL+=" FROM(";
                strSQL+="       SELECT " + bdeCosUniEgr + " AS nd_cosUniEgr, b1.co_emp, b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_codAlt2 , b1.tx_nomItm, b1.tx_desCor";
                strSQL+="       , b2.co_grp, b2.co_reg, b2.tx_desCorGrp, b2.tx_desLarGrp, b2.nd_can, b2.tx_tipRel, b2.nd_porCalCos, b3.co_itmEmp";
                strSQL+="       FROM(";
                strSQL+="           SELECT a1.co_emp, a3.co_itmMae, a3.co_itm, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm, a2.tx_desCor";
                strSQL+="           FROM tbm_inv AS a1";
                strSQL+="           INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                strSQL+="           LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
                strSQL+="           WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+="           AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
                strSQL+="           ORDER BY a1.tx_codAlt";
                strSQL+="       ) AS b1";
                strSQL+="       INNER JOIN(";
                strSQL+="           SELECT a1.co_emp, a1.co_grp, a1.tx_desCor AS tx_desCorGrp, a1.tx_desLar AS tx_desLarGrp, a1.tx_obs1, a1.st_reg";
                strSQL+="           , a2.co_reg, a2.co_itm, a3.co_itmMae, a2.nd_can, a2.tx_tipRel, a2.st_reg, a2.nd_porCalCos";
                strSQL+="           FROM tbm_cabCfgConInv AS a1 INNER JOIN tbm_detCfgConInv AS a2";
                strSQL+="           ON a1.co_emp=a2.co_emp AND a1.co_grp=a2.co_grp";
                strSQL+="           INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                strSQL+="           WHERE a2.tx_tipRel='I' AND a1.st_reg='A' AND a2.st_reg='A'";
                strSQL+="       ) AS b2";
                strSQL+="       ON b1.co_emp=b2.co_emp AND b1.co_itmMae=b2.co_itmMae";
                strSQL+="       INNER JOIN(";
                strSQL+="           SELECT a1.co_emp, a3.co_itmMae, a3.co_itm AS co_itmEmp, a1.tx_codAlt, a1.tx_codAlt2 , a1.tx_nomItm";
                strSQL+="           FROM tbm_inv AS a1";
                strSQL+="           INNER JOIN tbm_equInv as a3 ON (a1.co_emp=a3.co_emp AND a1.co_itm=a3.co_itm)";
                strSQL+="           WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="           AND a1.st_reg='A' AND (a1.tx_codAlt like '%I' OR a1.tx_codAlt like '%S' )";
                strSQL+="           ORDER BY a1.tx_codAlt";
                strSQL+="       ) AS b3";
                strSQL+="       ON b1.co_itmMae=b3.co_itmMae";
                strSQL+="       ORDER BY b2.co_reg";
                strSQL+=" ) AS c1";
                strSQL+=" WHERE c1.co_grp=" + codigoGrupo + "";
                strSQL+=" ORDER BY c1.co_reg";
                System.out.println("ingreso: " + strSQL);
                rstItmIng=stmItmIng.executeQuery(strSQL);
                while(rstItmIng.next()){
                    //intFilAdd++;
                    objTblMod.insertRow(intFilAdd);
                    objTblMod.setValueAt(rstItmIng.getString("co_itmMae"), intFilAdd, INT_TBL_DAT_COD_ITM_MAE);
                    objTblMod.setValueAt(rstItmIng.getString("co_itm"), intFilAdd, INT_TBL_DAT_COD_ITM_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("co_itmEmp"), intFilAdd, INT_TBL_DAT_COD_ITM_EMP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_codAlt"), intFilAdd, INT_TBL_DAT_COD_ALT_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_codAlt2"), intFilAdd, INT_TBL_DAT_COD_LET_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_nomItm"), intFilAdd, INT_TBL_DAT_NOM_ITM);
                    objTblMod.setValueAt(rstItmIng.getString("tx_desCor"), intFilAdd, INT_TBL_DAT_UNI_MED);
                    objTblMod.setValueAt(rstItmIng.getString("co_grp"), intFilAdd, INT_TBL_DAT_COD_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_desCorGrp"), intFilAdd, INT_TBL_DAT_DES_COR_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("tx_desLarGrp"), intFilAdd, INT_TBL_DAT_DES_LAR_GRP);
                    objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_ING);
                    objTblMod.setValueAt(rstItmIng.getString("nd_can"), intFilAdd, INT_TBL_DAT_CAN_ING_AUX);
                    objTblMod.setValueAt(rstItmIng.getString("tx_tipRel"), intFilAdd, INT_TBL_DAT_TIP_REL);
                    objTblMod.setValueAt(rstItmIng.getString("nd_porCalCos"), intFilAdd, INT_TBL_DAT_POR_CAL_COS_UNI);
                    objTblMod.setValueAt(objUti.redondearBigDecimal(rstItmIng.getString("nd_cosUniIng"), objParSis.getDecimalesBaseDatos()), intFilAdd, INT_TBL_DAT_COS_UNI);
                    objTblMod.setValueAt(objUti.redondearBigDecimal(rstItmIng.getString("nd_cosTotIng"), objParSis.getDecimalesBaseDatos()), intFilAdd, INT_TBL_DAT_COS_TOT);
                    objTblMod.setValueAt(intNumFilEgr, intFilAdd, INT_TBL_DAT_NUM_FIL_BLK);
                }
                rstItmIng.close();
                rstItmIng=null;
                stmItmIng.close();
                stmItmIng=null;
                conItmIng.close();
                conItmIng=null;
            }
            
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
  
    /**
     * Función que permite generar el asiento de diario
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean generaDiarioAjuste(){
        boolean blnRes=true;
        
        Vector vecDatDia=new Vector();
        Vector vecRegDia=new Vector();
        final int INT_VEC_DIA_LIN=0;
        final int INT_VEC_DIA_COD_CTA=1;
        final int INT_VEC_DIA_NUM_CTA=2;
        final int INT_VEC_DIA_BUT_CTA=3;
        final int INT_VEC_DIA_NOM_CTA=4;
        final int INT_VEC_DIA_DEB=5;
        final int INT_VEC_DIA_HAB=6;
        final int INT_VEC_DIA_REF=7;
        final int INT_VEC_DIA_EST_CON=8;

        BigDecimal bdeValDoc=new BigDecimal(txtValDoc.getText().equals("")?"0":txtValDoc.getText());

        Connection conGenDiaAju;
        Statement stmGenDiaAju;
        ResultSet rstGenDiaAju;
        
        try{
            ///para generar el asiento de diario
            objAsiDia.inicializar();
            vecDatDia.clear();
            conGenDiaAju=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conGenDiaAju!=null){
                stmGenDiaAju=conGenDiaAju.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_cfg, b1.tx_nom, b1.co_loc, b1.co_tipdoc, b1.tx_tipMov, b1.tx_obs1, b1.co_mnu";
                strSQL+=" , b1.co_ctaDeb, b1.tx_codCtaDeb, b1.tx_desLarDeb, b1.st_reg, b2.co_ctaHab, b2.tx_codCtaHab, b2.tx_desLarHab";
                strSQL+=" FROM(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cfg, a1.tx_nom, a1.co_loc, a1.co_tipdoc, a1.tx_tipMov, a1.tx_obs1, a1.co_mnu";
                strSQL+=" 	, a1.co_ctaDeb, a2.tx_codCta AS tx_codCtaDeb, a2.tx_desLar AS tx_desLarDeb, a1.st_reg";
                strSQL+=" 	FROM tbm_cfgTipDocCtaPrg AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";                
                strSQL+="       ORDER BY a1.co_cfg";
                strSQL+=" ) AS b1";
                strSQL+=" INNER JOIN(";
                strSQL+=" 	SELECT a1.co_emp, a1.co_cfg, a1.tx_nom, a1.co_loc, a1.co_tipdoc, a1.tx_tipMov, a1.tx_obs1, a1.co_mnu";
                strSQL+=" 	, a1.co_ctaHab, a2.tx_codCta AS tx_codCtaHab, a2.tx_desLar AS tx_desLarHab, a1.st_reg";
                strSQL+=" 	FROM tbm_cfgTipDocCtaPrg AS a1 INNER JOIN tbm_plaCta AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_ctaDeb=a2.co_cta";
                strSQL+=" 	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="       AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       AND a1.co_tipdoc=" + txtCodTipDoc.getText() + "";
                strSQL+="       ORDER BY a1.co_cfg";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_cfg=b2.co_cfg";
                System.out.println("generaDiarioAjuste: " + strSQL);
                rstGenDiaAju=stmGenDiaAju.executeQuery(strSQL);
                while(rstGenDiaAju.next()){
                    //Debe
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA,  rstGenDiaAju.getInt("co_ctaDeb"));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA,  rstGenDiaAju.getString("tx_codCtaDeb"));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA,  "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,  rstGenDiaAju.getString("tx_desLarDeb"));
                    vecRegDia.add(INT_VEC_DIA_DEB,      bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_HAB,      0);
                    vecRegDia.add(INT_VEC_DIA_REF,      "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON,  "");
                    vecDatDia.add(vecRegDia);
                    //Haber
                    vecRegDia=new java.util.Vector();
                    vecRegDia.add(INT_VEC_DIA_LIN, "");
                    vecRegDia.add(INT_VEC_DIA_COD_CTA,  rstGenDiaAju.getInt("co_ctaHab"));
                    vecRegDia.add(INT_VEC_DIA_NUM_CTA,  rstGenDiaAju.getString("tx_codCtaHab"));
                    vecRegDia.add(INT_VEC_DIA_BUT_CTA,  "");
                    vecRegDia.add(INT_VEC_DIA_NOM_CTA,  rstGenDiaAju.getString("tx_desLarHab"));
                    vecRegDia.add(INT_VEC_DIA_DEB,      0);
                    vecRegDia.add(INT_VEC_DIA_HAB,      bdeValDoc);
                    vecRegDia.add(INT_VEC_DIA_REF,      "");
                    vecRegDia.add(INT_VEC_DIA_EST_CON,  "");
                    vecDatDia.add(vecRegDia);
                }
                
                conGenDiaAju.close();
                conGenDiaAju=null;
                stmGenDiaAju.close();
                stmGenDiaAju=null;
                rstGenDiaAju.close();
                rstGenDiaAju=null;
            }
            System.out.println("vecDatDia: " + vecDatDia.toString());
            objAsiDia.setDetalleDiario(vecDatDia);
//                System.out.println("getDetalleDiario: " + objAsiDia.getDetalleDiario());
            objAsiDia.setGeneracionDiario((byte)2);
            actualizarGlo();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

   
    /**
     * Esta función se utiliza para actualizar la glosa del asiento de diario.
     * Los usuarios necesitaban que aparecieran ciertos datos del documento en la glosa.
     */
    private void actualizarGlo()
    {
        strAux="";
        strAux+=" Ajuste: " + txtDesCorTipDoc.getText();
        strAux+=" # " + txtNumDoc.getText();
        objAsiDia.setGlosa(strAux);
    }
    
    
    private boolean calcularCostoTotalEgreso(int filaEgreso){
        boolean blnRes=true;
        int intFilSelEgr=filaEgreso;
        BigDecimal bdeCosUniEgr=BigDecimal.ZERO;
        BigDecimal bdeCanEgr=BigDecimal.ZERO;
        BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
        
        try{
            bdeCanEgr=new BigDecimal(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR)==null?"0":(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR).toString().equals("")?"0":objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_CAN_EGR).toString()));
            bdeCosUniEgr=new BigDecimal(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(intFilSelEgr, INT_TBL_DAT_COS_UNI).toString()));
            bdeCosTotEgr=objUti.redondearBigDecimal((bdeCanEgr.multiply(bdeCosUniEgr)), objParSis.getDecimalesBaseDatos());
            System.out.println("bdeCosTotEgr:" + bdeCosTotEgr);
            System.out.println("intFilSelEgr:" + intFilSelEgr);
            objTblMod.setValueAt(bdeCosTotEgr, intFilSelEgr, INT_TBL_DAT_COS_TOT);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    
    
    private boolean calcularCostosIngresos(int intFil, BigDecimal bdeCanEgrSig){
        boolean blnRes=true;
        String strTipRelIng="";
        int intNumFilItmEgr=-1;
        int intNumFilItmIng=-1;
        BigDecimal bdeCanIngAnt=BigDecimal.ZERO;
        BigDecimal bdeCanIngAct=BigDecimal.ZERO;
        BigDecimal bdeCanIngNue=BigDecimal.ZERO;
        BigDecimal bdeCosUniIng=BigDecimal.ZERO;
        BigDecimal bdeCosTotIng=BigDecimal.ZERO;
        String strTipRelEgr="";
        try{
            System.out.println("se llama: " + intNumFilItmEgr);
            intNumFilItmEgr=Integer.parseInt(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(intFil, INT_TBL_DAT_NUM_FIL_BLK).toString()));
            strTipRelEgr=objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(intFil, INT_TBL_DAT_TIP_REL).toString();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                intNumFilItmIng=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString()));
                if(intNumFilItmEgr==intNumFilItmIng){
                    strTipRelIng=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                    if(strTipRelIng.equals("I")){
                        bdeCanIngAct=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_CAN_ING_AUX).toString()));
                        bdeCosUniIng=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                        bdeCanIngNue=bdeCanIngAct.multiply(bdeCanEgrSig);
                        objTblMod.setValueAt(bdeCanIngNue, i, INT_TBL_DAT_CAN_ING);

                        bdeCosTotIng=bdeCanIngNue.multiply(bdeCosUniIng);

                        objTblMod.setValueAt(bdeCosTotIng, i, INT_TBL_DAT_COS_TOT);
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
    
    
    
//    no esta calculando el acumulado de costos totales de egresos y colocarlo en valor de dcoumento.
    
    private boolean setValorDoc(){
        boolean blnRes=true;
        String strTipRelEgr="";
        BigDecimal bdeCosTotEgr=BigDecimal.ZERO;
        BigDecimal bdeCosTotEgrAcu=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelEgr.equals("E")){
                    bdeCosTotEgr=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_TOT).toString()));
                    bdeCosTotEgrAcu=bdeCosTotEgrAcu.add(bdeCosTotEgr);
                }
            }
            bdeCosTotEgrAcu=objUti.redondearBigDecimal(bdeCosTotEgrAcu, objParSis.getDecimalesMostrar());
            txtValDoc.setText(bdeCosTotEgrAcu.abs().toString());
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }


    

    /**
     * Función que permite eliminar el grupo de conversión asociado al item seleccionado para eliminar
     * @param filaEli Fila seleccionada
     * @return true Si se pudo realizar la operación
     * <BR> false Caso contrario
     */
    private boolean eliminaGrpCnv(int filaEli){
        boolean blnRes=true;
        int intNumFilGrp=Integer.parseInt(objTblMod.getValueAt(filaEli, INT_TBL_DAT_NUM_FIL_BLK).toString());
        int intNumFil=-1;
        int j=0;
        try{
            System.out.println("intNumFilGrp: " + intNumFilGrp);
            for(int i=(objTblMod.getRowCountTrue()-1); i>=0; i--){
                System.out.println("i: " + i);
                intNumFil=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString());
                if(intNumFilGrp==intNumFil){
                    tblDat.getSelectionModel().addSelectionInterval (i, i);
                    System.out.println("dentro - i: " + i);
                }
            }
            
            
            
            
//            System.out.println("intNumFilGrp: " + intNumFilGrp);
//            for(int i=(objTblMod.getRowCountTrue()-2); i>=0; i--){
//                System.out.println("i: " + i);
//                intNumFil=Integer.parseInt(objTblMod.getValueAt(i, INT_TBL_DAT_NUM_FIL_BLK).toString());
//                if(intNumFilGrp==intNumFil){
//                    objTblMod.removeRow(i);
//                }
//            }
//            objTblMod.removeEmptyRows();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    //validar que no exista la descripcion corta ya en la DB
    
    /**
     * Función que permite ingresar máximo 3 documentos de conversión asociados al item
     * @return true Si se pudo realizar la operación - Es menor o igual a 3
     * <BR> false Caso contrario
     */
    private boolean isPerIngDocCon(){
        boolean blnRes=false;
        String strTipRelEgr="";
        String strCodItmMae="";
        int j=0;
        Connection conPerIng;
        Statement stmPerIng;
        ResultSet rstPerIng;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                strTipRelEgr=objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_TIP_REL).toString();
                if(strTipRelEgr.equals("E")){
                    if(j==0){
                        strCodItmMae=objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString() + "";
                    }
                    else
                        strCodItmMae+=", " + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM_MAE).toString() + "";
                    j++;
                }
            }
            conPerIng=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conPerIng!=null){
                stmPerIng=conPerIng.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_itmMae, a1.ne_numItm";
                strSQL+=" FROM(";
                strSQL+="   SELECT a1.co_emp, a1.co_loc, a3.co_itmMae, COUNT(a3.co_itmMae) AS ne_numItm";
                strSQL+="   FROM tbm_cabMovInv AS a1";
                strSQL+="   INNER JOIN (tbm_detMovInv AS a2 INNER JOIN tbm_equInv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm)";
                strSQL+="   ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="   WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="   AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="   AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                strSQL+="   AND a1.fe_doc=CURRENT_DATE";
                strSQL+="   AND a1.st_reg='A'";
                strSQL+="   AND a3.co_itmMae IN(" + strCodItmMae + ")";
                strSQL+="   GROUP BY a1.co_emp, a1.co_loc, a3.co_itmMae";
                strSQL+=" ) AS a1";
                strSQL+=" WHERE a1.ne_numItm>3";
                System.out.println("strSQL: " + strSQL);
                rstPerIng=stmPerIng.executeQuery(strSQL);
                if(rstPerIng.next())
                    blnRes=false;
                else
                    blnRes=true;
                
                conPerIng.close();
                conPerIng=null;
                stmPerIng.close();
                stmPerIng=null;
                rstPerIng.close();
                rstPerIng=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    
    /**
     * Función que permite validar que no exista costos unitarios en cero.
     * @return 
     */
    private boolean isCosUniCero(){
        boolean blnRes=false;
        BigDecimal bdeCosUni=BigDecimal.ZERO;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                bdeCosUni=new BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI)==null?"0":(objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString().equals("")?"0":objTblMod.getValueAt(i, INT_TBL_DAT_COS_UNI).toString()));
                if(bdeCosUni.compareTo(BigDecimal.ZERO)==0){
                    blnRes=true;
                    break;
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }
    
    
}