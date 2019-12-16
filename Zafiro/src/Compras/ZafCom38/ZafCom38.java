/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Compras.ZafCom38;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelEdiTxtVco.ZafTblCelEdiTxtVco;
import Librerias.ZafTblUti.ZafTblCelEdiButVco.ZafTblCelEdiButVco;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import Librerias.ZafToolBar.ZafToolBar;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.*;
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
public class ZafCom38 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;
    final int INT_TBL_DAT_COD_ITM=1;
    final int INT_TBL_DAT_COD_ALT_ITM=2;
    final int INT_TBL_DAT_BUT_ITM=3;
    final int INT_TBL_DAT_NOM_ITM=4;
    final int INT_TBL_DAT_STK=5;
    final int INT_TBL_DAT_COD_UNI_MED=6;
    final int INT_TBL_DAT_UNI_MED=7;
    final int INT_TBL_DAT_COD_BOD=8;
    final int INT_TBL_DAT_BUT_BOD=9;
    final int INT_TBL_DAT_NOM_BOD=10;
    final int INT_TBL_DAT_CAN=11;
    
    //Variables generales.
    private ZafDatePicker dtpFecDoc, dtpFecRei;
    private String strFecDocIni;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;            //Editor: JTextField en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCli, vcoVen;                           //Ventana de consulta "Proveedor".
    private MiToolBar objTooBar;
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
    
    
    private ZafTblCelRenBut objTblCelRenButItm;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoItm;
    private ZafTblCelEdiButVco objTblCelEdiButVcoItm;
    private ZafVenCon vcoItm;

    private ZafTblCelRenBut objTblCelRenButBod;
    private ZafTblCelEdiTxtVco objTblCelEdiTxtVcoBod;
    private ZafTblCelEdiButVco objTblCelEdiButVcoBod;
    private ZafVenCon vcoBod;
    
    private String strCodVen,strDesLarVen;
    private String strEstImpDoc;
    
    private int intCodEmp;
    private int intCodLoc;
    private int intCodMnu;
    
    //private String strEstDoc;//para saber si el documento esta pendiente de autorizar o no esta pendiente de autorizar
    private String strEstCreEgr;//para saber si el documento ya genero el documento de egreso
    private String strNomRep;
    
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafCom38(ZafParSis obj)
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            configurarFrm();
            agregarDocLis();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }
    
    
    public ZafCom38(ZafParSis obj, int codEmp, int codLoc, int codTipDoc, int codDoc, int codMnu){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            //formInternalFrameOpened(null);
            configurarFrm();
            agregarDocLis();
            intCodEmp=codEmp;
            intCodLoc=codLoc;
            intCodMnu=codMnu;
            if(intCodMnu==2049){
                lblFecRei.setVisible(false);
                dtpFecRei.setVisible(false);
                dtpFecRei.setEnabled(false);
                txtCodPrv.setVisible(false);
                txtCodPrv.setEditable(false);
                txtCodPrv.setEnabled(false);
                txtDesLarPrv.setVisible(false);
                txtDesLarPrv.setVisible(false);
                txtDesLarPrv.setEditable(false);
                txtDesLarPrv.setEnabled(false);
                butPrv.setVisible(false);
                butPrv.setEnabled(false);
            }
            txtCodTipDoc.setText("" + codTipDoc);
            txtCodDoc.setText("" + codDoc);
            objTooBar.setEstado('c');
            objTooBar.consultar();
            objTooBar.setEstado('w');
            objTooBar.setVisibleAnular(false);
            objTooBar.setVisibleEliminar(false);
            objTooBar.setVisibleImprimir(false);
            objTooBar.setVisibleInsertar(false);
            objTooBar.setVisibleModificar(false);
            objTooBar.setVisibleVistaPreliminar(false);
            
            
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
        panGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtDesCorTipDoc = new javax.swing.JTextField();
        lblTipDoc = new javax.swing.JLabel();
        lblCodDoc = new javax.swing.JLabel();
        txtCodDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        txtDesLarTipDoc = new javax.swing.JTextField();
        lblFecDoc = new javax.swing.JLabel();
        lblNumDoc1 = new javax.swing.JLabel();
        txtNumDoc1 = new javax.swing.JTextField();
        txtCodTipDoc = new javax.swing.JTextField();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        lblFecRei = new javax.swing.JLabel();
        lblVen = new javax.swing.JLabel();
        txtDesCorVen = new javax.swing.JTextField();
        txtDesLarVen = new javax.swing.JTextField();
        butVen = new javax.swing.JButton();
        txtCodVen = new javax.swing.JTextField();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panGenTot = new javax.swing.JPanel();
        panGenTotLbl = new javax.swing.JPanel();
        lblObs1 = new javax.swing.JLabel();
        lblObs2 = new javax.swing.JLabel();
        panGenTotObs = new javax.swing.JPanel();
        spnObs1 = new javax.swing.JScrollPane();
        txaObs1 = new javax.swing.JTextArea();
        spnObs2 = new javax.swing.JScrollPane();
        txaObs2 = new javax.swing.JTextArea();
        panBar = new javax.swing.JPanel();

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 94));
        panGenCab.setRequestFocusEnabled(false);
        panGenCab.setLayout(null);

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
        txtDesCorTipDoc.setBounds(118, 4, 60, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panGenCab.add(lblTipDoc);
        lblTipDoc.setBounds(0, 4, 118, 20);

        lblCodDoc.setText("Código del documento:");
        lblCodDoc.setToolTipText("Código del documento");
        panGenCab.add(lblCodDoc);
        lblCodDoc.setBounds(0, 70, 122, 20);
        panGenCab.add(txtCodDoc);
        txtCodDoc.setBounds(122, 70, 80, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panGenCab.add(butTipDoc);
        butTipDoc.setBounds(432, 4, 20, 20);

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
        txtDesLarTipDoc.setBounds(178, 4, 254, 20);

        lblFecDoc.setText("Fecha del documento:");
        lblFecDoc.setToolTipText("Fecha del documento");
        panGenCab.add(lblFecDoc);
        lblFecDoc.setBounds(454, 4, 124, 20);

        lblNumDoc1.setText("Número de documento:");
        lblNumDoc1.setToolTipText("Número alterno 1");
        panGenCab.add(lblNumDoc1);
        lblNumDoc1.setBounds(454, 44, 126, 20);

        txtNumDoc1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtNumDoc1.setToolTipText("Número de egreso");
        txtNumDoc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDoc1FocusLost(evt);
            }
        });
        panGenCab.add(txtNumDoc1);
        txtNumDoc1.setBounds(580, 44, 102, 20);
        panGenCab.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(90, 4, 32, 20);

        lblPrv.setText("Cliente:");
        lblPrv.setToolTipText("Proveedor");
        panGenCab.add(lblPrv);
        lblPrv.setBounds(0, 26, 100, 20);

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
        txtCodPrv.setBounds(118, 26, 60, 20);

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
        txtDesLarPrv.setBounds(178, 26, 254, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panGenCab.add(butPrv);
        butPrv.setBounds(432, 26, 20, 20);

        lblFecRei.setText("Fecha de reingreso:");
        lblFecRei.setToolTipText("Fecha de vencimiento");
        panGenCab.add(lblFecRei);
        lblFecRei.setBounds(454, 24, 124, 20);

        lblVen.setText("Solicitante:");
        lblVen.setToolTipText("Vendedor");
        panGenCab.add(lblVen);
        lblVen.setBounds(0, 48, 80, 20);

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
        txtDesCorVen.setBounds(118, 48, 60, 20);

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
        txtDesLarVen.setBounds(178, 48, 254, 20);

        butVen.setText("...");
        butVen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butVenActionPerformed(evt);
            }
        });
        panGenCab.add(butVen);
        butVen.setBounds(432, 48, 20, 20);
        panGenCab.add(txtCodVen);
        txtCodVen.setBounds(86, 48, 32, 20);

        panGen.add(panGenCab, java.awt.BorderLayout.NORTH);

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

        panGen.add(panGenDet, java.awt.BorderLayout.CENTER);

        panGenTot.setPreferredSize(new java.awt.Dimension(34, 70));
        panGenTot.setLayout(new java.awt.BorderLayout());

        panGenTotLbl.setPreferredSize(new java.awt.Dimension(100, 30));
        panGenTotLbl.setLayout(new java.awt.GridLayout(2, 1));

        lblObs1.setText("Observación1:");
        lblObs1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs1);

        lblObs2.setText("Observación2:");
        lblObs2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        panGenTotLbl.add(lblObs2);

        panGenTot.add(panGenTotLbl, java.awt.BorderLayout.WEST);

        panGenTotObs.setLayout(new java.awt.GridLayout(2, 1));

        txaObs1.setLineWrap(true);
        spnObs1.setViewportView(txaObs1);

        panGenTotObs.add(spnObs1);

        txaObs2.setLineWrap(true);
        spnObs2.setViewportView(txaObs2);

        panGenTotObs.add(spnObs2);

        panGenTot.add(panGenTotObs, java.awt.BorderLayout.CENTER);

        panGen.add(panGenTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("General", panGen);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    /** Cerrar la aplicaci�n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
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
        if (!txtCodPrv.getText().equals(""))
        {
            //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
//            if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))
                //cargarDocPen();
        }
}//GEN-LAST:event_butPrvActionPerformed

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv))
        {
            if (txtDesLarPrv.getText().equals(""))
            {
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
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
            }
            else
            {
                mostrarVenConPrv(1);
                //Cargar los documentos pendientes s�lo si ha cambiado el beneficiario.
                 if( (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv))   ){
                    //cargarDocPen();
                 }
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

private void txtNumDoc1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtNumDoc1.getText().equalsIgnoreCase(strNumDoc1))
        {
        }
}//GEN-LAST:event_txtNumDoc1FocusLost

private void txtNumDoc1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDoc1FocusGained
strNumDoc1=txtNumDoc1.getText();
        txtNumDoc1.selectAll();
}//GEN-LAST:event_txtNumDoc1FocusGained

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

private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
    strDesLarTipDoc=txtDesLarTipDoc.getText();
    txtDesLarTipDoc.selectAll();
}//GEN-LAST:event_txtDesLarTipDocFocusGained

private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
    txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
    mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

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

private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
strDesCorTipDoc=txtDesCorTipDoc.getText();
        txtDesCorTipDoc.selectAll();
}//GEN-LAST:event_txtDesCorTipDocFocusGained

private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JButton butVen;
    private javax.swing.JLabel lblCodDoc;
    private javax.swing.JLabel lblFecDoc;
    private javax.swing.JLabel lblFecRei;
    private javax.swing.JLabel lblNumDoc1;
    private javax.swing.JLabel lblObs1;
    private javax.swing.JLabel lblObs2;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lblVen;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panGenTot;
    private javax.swing.JPanel panGenTotLbl;
    private javax.swing.JPanel panGenTotObs;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnObs1;
    private javax.swing.JScrollPane spnObs2;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextArea txaObs1;
    private javax.swing.JTextArea txaObs2;
    private javax.swing.JTextField txtCodDoc;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtCodVen;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesCorVen;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtDesLarVen;
    private javax.swing.JTextField txtNumDoc1;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafDatePicker:
            dtpFecDoc=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecDoc.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecDoc.setText("");
            panGenCab.add(dtpFecDoc);
            dtpFecDoc.setBounds(580, 4, 102, 20);
            
            
            
            dtpFecRei=new ZafDatePicker(javax.swing.JOptionPane.getFrameForComponent(this),"d/m/y");
            dtpFecRei.setBackground(objParSis.getColorCamposObligatorios());
            dtpFecRei.setText("");
            panGenCab.add(dtpFecRei);
            dtpFecRei.setBounds(580, 24, 102, 20);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objTooBar=new MiToolBar(this);

            
            objDocLis=new ZafDocLis();
            panBar.add(objTooBar);
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1");//para codigo con autorizaciones es 0.16 y para produccion 0.13.1
            lblTit.setText(strAux);
            txtCodDoc.setBackground(objParSis.getColorCamposSistema());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtCodVen.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorVen.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarVen.setBackground(objParSis.getColorCamposObligatorios());
            txtCodPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarPrv.setBackground(objParSis.getColorCamposObligatorios());
            txtNumDoc1.setBackground(objParSis.getColorCamposObligatorios());
            
            txtCodTipDoc.setVisible(false);
            //Configurar las ZafVenCon.
            configurarVenConTipDoc();
            configurarVenConPrv();
            configurarVenConVen();
            configurarVenConItm();
            configurarVenConBod();
            //Configurar los JTables.
            configurarTblDat();
            
            if(objParSis.getCodigoMenu()==2049){
                lblFecRei.setVisible(false);
                dtpFecRei.setVisible(false);
                dtpFecRei.setEnabled(false);
                lblPrv.setVisible(false);
                txtCodPrv.setVisible(false);
                txtCodPrv.setEditable(false);
                txtCodPrv.setEnabled(false);
                txtDesLarPrv.setVisible(false);
                txtDesLarPrv.setVisible(false);
                txtDesLarPrv.setEditable(false);
                txtDesLarPrv.setEnabled(false);
                butPrv.setVisible(false);
                butPrv.setEnabled(false);
            }
            txtCodVen.setVisible(false);

            if(System.getProperty("os.name").equals("Linux")){
                strNomRep="//Zafiro//Reportes_impresos//RptZafCom38.jrxml";
            }
            else{
                strNomRep="C://Zafiro//Reportes_impresos//RptZafCom38.jrxml";
            }
            
            
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
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_ITM,"COD.ITM.");
            vecCab.add(INT_TBL_DAT_COD_ALT_ITM,"COD.ALT.ITM.");
            vecCab.add(INT_TBL_DAT_BUT_ITM,"");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"NOM.ITM.");
            vecCab.add(INT_TBL_DAT_STK,"STK.ITM.");
            vecCab.add(INT_TBL_DAT_COD_UNI_MED,"COD.UNI.MED.");
            vecCab.add(INT_TBL_DAT_UNI_MED,"UNI.MED");
            vecCab.add(INT_TBL_DAT_COD_BOD,"COD.BOD.");
            vecCab.add(INT_TBL_DAT_BUT_BOD,"");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"NOM.BOD");
            vecCab.add(INT_TBL_DAT_CAN,"CAN.");
            
            
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
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
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_STK).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_COD_UNI_MED).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_UNI_MED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(150);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setPreferredWidth(70);
            
            
            
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setResizable(false);
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
            vecAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            vecAux.add("" + INT_TBL_DAT_BUT_ITM);
            vecAux.add("" + INT_TBL_DAT_COD_BOD);
            vecAux.add("" + INT_TBL_DAT_BUT_BOD);
            vecAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_COD_ITM);
            arlAux.add("" + INT_TBL_DAT_COD_ALT_ITM);
            arlAux.add("" + INT_TBL_DAT_NOM_ITM);
            arlAux.add("" + INT_TBL_DAT_COD_BOD);
            arlAux.add("" + INT_TBL_DAT_NOM_BOD);
            arlAux.add("" + INT_TBL_DAT_CAN);
            objTblMod.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblMod.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_COD_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CAN).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });
            
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_CAN, objTblMod.INT_COL_DBL, new Integer(0), null);
            objTblMod.setColumnDataType(INT_TBL_DAT_STK, objTblMod.INT_COL_DBL, new Integer(0), null);
            
            
            
            //para la busqueda cuando se digita el codigo alterno del item
            objTblCelRenButItm=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellRenderer(objTblCelRenButItm);
            objTblCelRenButItm=null;
            //Configurar JTable: Editor de celdas.
            int intColVen[]=new int[6];
            intColVen[0]=1;
            intColVen[1]=2;
            intColVen[2]=3;
            intColVen[3]=4;
            intColVen[4]=5;
            intColVen[5]=6;
            int intColTbl[]=new int[6];
            intColTbl[0]=INT_TBL_DAT_COD_ITM;
            intColTbl[1]=INT_TBL_DAT_COD_ALT_ITM;
            intColTbl[2]=INT_TBL_DAT_NOM_ITM;
            intColTbl[3]=INT_TBL_DAT_STK;
            intColTbl[4]=INT_TBL_DAT_COD_UNI_MED;
            intColTbl[5]=INT_TBL_DAT_UNI_MED;
            objTblCelEdiTxtVcoItm=new ZafTblCelEdiTxtVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT_ITM).setCellEditor(objTblCelEdiTxtVcoItm);
            objTblCelEdiTxtVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            });            
            //para cuando se da click en el boton de item
            objTblCelEdiButVcoItm=new ZafTblCelEdiButVco(tblDat, vcoItm, intColVen, intColTbl);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ITM).setCellEditor(objTblCelEdiButVcoItm);
            objTblCelEdiButVcoItm.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            });
            
            
            //para la busqueda cuando se digita el codigo alterno del item
            objTblCelRenButBod=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setCellRenderer(objTblCelRenButBod);
            objTblCelRenButBod=null;
            //Configurar JTable: Editor de celdas.
            int intColVenBod[]=new int[2];
            intColVenBod[0]=1;
            intColVenBod[1]=2;
            int intColTblBod[]=new int[2];
            intColTblBod[0]=INT_TBL_DAT_COD_BOD;
            intColTblBod[1]=INT_TBL_DAT_NOM_BOD;
            objTblCelEdiTxtVcoBod=new ZafTblCelEdiTxtVco(tblDat, vcoBod, intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setCellEditor(objTblCelEdiTxtVcoBod);
            objTblCelEdiTxtVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
            });
            
            //para cuando se da click en el boton de item
            objTblCelEdiButVcoBod=new ZafTblCelEdiButVco(tblDat, vcoBod, intColVenBod, intColTblBod);
            tcmAux.getColumn(INT_TBL_DAT_BUT_BOD).setCellEditor(objTblCelEdiButVcoBod);
            objTblCelEdiButVcoBod.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strAux=objUti.parseString(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_COD_ITM));
                    if (strAux.equals("")){
                        mostrarMsgInf("Para seleccionar la bodega primero debe indicar el item.");
                        objTblCelEdiButVcoBod.setCancelarEdicion(true);
                    }
                }
                public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    vcoBod.setCondicionesSQL(" AND a2.co_itm=" + strAux);
                    vcoBod.cargarDatos();
                }
            });
            
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_STK, tblDat);
            
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
     * Esta clase crea la barra de herramientas para el sistema. Dicha barra de herramientas
     * contiene los botones que realizan las diferentes operaciones del sistema. Es decir,
     * insertar, consultar, modificar, eliminar, etc. Adem�s de los botones de navegaci�n
     * que permiten desplazarse al primero, anterior, siguiente y �ltimo registro.
     */
    private class MiToolBar extends ZafToolBar
    {
        public MiToolBar(javax.swing.JInternalFrame ifrFrm)
        {
            super(ifrFrm, objParSis);
        }

        public boolean anular(){
            if (!anularReg())
                return false;
            objTooBar.setEstadoRegistro("Anulado");
            blnHayCam=false;
            return true;
        }

        public void clickAceptar()
        {
            
        }
        
        public void clickAnterior() 
        {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam  || objTblMod.isDataModelChanged()){
                        if (isRegPro()){
                            rstCab.previous();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else{
                        rstCab.previous();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickAnular()
        {
            cargarReg();
        }

        public void clickCancelar()
        {
            
        }

        public void clickConsultar() 
        {
        }

        public void clickEliminar()
        {
            cargarDetReg();
        }

        public void clickFin() 
        {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam  || objTblMod.isDataModelChanged()){
                        if (isRegPro()){
                            rstCab.last();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else{
                        rstCab.last();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickImprimir(){
            

            
            
        }

        public void clickInicio()
        {
            try{
                if (!rstCab.isFirst()){
                    if (blnHayCam || objTblMod.isDataModelChanged()){
                        if (isRegPro()){
                            rstCab.first();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else{
                        rstCab.first();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickInsertar()
        {
            try
            {
                if (blnHayCam || objTblMod.isDataModelChanged())
                {
                    isRegPro();
                }
                if (rstCab!=null)
                {
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
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                dtpFecRei.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
                datFecAux=null;
                mostrarTipDocPre();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                //Inicializar las variables que indican cambios.
                blnHayCam=false;
                dtpFecDoc.setEnabled(true);
                txtCodVen.setText(""+objParSis.getCodigoUsuario());
                txtDesCorVen.setText(objParSis.getNombreUsuario());
                txtDesLarVen.setText(nombreCompletoUsuario(txtCodVen.getText()));
                dtpFecRei.setEnabled(true);

            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickModificar(){
            if(strEstCreEgr.equals("N")){
                objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
                
                txtCodTipDoc.setEditable(false);
                txtDesCorTipDoc.setEditable(false);
                txtDesLarTipDoc.setEditable(false);
                butTipDoc.setEnabled(false);
                txtCodVen.setEditable(false);
                txtDesCorVen.setEditable(false);
                txtDesLarVen.setEditable(false);
                butVen.setEnabled(false);
                txtCodDoc.setEditable(false);
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                dtpFecRei.setEnabled(false);
                if(camposInactivosPermisoModifi()){
                }
            }
            else{
                inactivarForm();
            }
        }

        public void clickSiguiente()
        {
            try{
                if (!rstCab.isLast()){
                    if (blnHayCam || objTblMod.isDataModelChanged()){
                        if (isRegPro()){
                            rstCab.next();
                            cargarReg();
                            if(camposInactivosPermisoModifi()){
                            }
                        }
                    }
                    else{
                        rstCab.next();
                        cargarReg();
                        if(camposInactivosPermisoModifi()){
                        }
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

        public void clickVisPreliminar() 
        {
            
        }

        public boolean consultar() 
        {
            consultarReg();
            return true;
        }

        public boolean eliminar()
        {
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
            catch (java.sql.SQLException e)
            {
                return true;
            }
            return true;
        }

        public boolean insertar()
        {
            if (!insertarReg())
                return false;
            return true;
        }

        public boolean modificar()
        {
            if (!actualizarReg())
                return false;
            return true;
        }
        
        public boolean cancelar()
        {
            boolean blnRes=true;
            try
            {
                if (blnHayCam || objTblMod.isDataModelChanged())
                {
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m')
                    {
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e)
            {
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            return blnRes;
            
            
        }
        
        public boolean vistaPreliminar(){
            Connection conIns;
            try{
                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try{
                    if(conIns!=null){
                        // Second, create a map of parameters to pass to the report.
                        Map parameters = new HashMap();
                        parameters.put("co_emp",    "" + objParSis.getCodigoEmpresa());
                        parameters.put("co_loc",    "" + objParSis.getCodigoLocal());
                        parameters.put("co_tipDoc", "" + txtCodTipDoc.getText());
                        parameters.put("co_doc",    "" + txtCodDoc.getText());
                        parameters.put("tx_usr",    "" + objParSis.getNombreUsuario());
                        datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                        parameters.put("fe_imp",    "" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()));
                        
                        String GLO_strNomEmp = objParSis.getNombreEmpresa();
                        String strNomEmp = GLO_strNomEmp.substring(0,3);
                        
                        
                        if(strNomEmp.toUpperCase().trim().equals("TUV")){
                            parameters.put("imgEmp", "1");//LOGO DE TUVAL
                        }
                        else if(strNomEmp.toUpperCase().trim().equals("CAS")){
                            parameters.put("imgEmp", "2");//LOGO DE TUVAL
                        }
                        else if(strNomEmp.toUpperCase().trim().equals("DIM")){
                            parameters.put("imgEmp", "4");//LOGO DE TUVAL
                        }
                        else{
                            parameters.put("imgEmp", "99");//LOGO - COSENCO
                        }
                        
                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRep);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
                         JasperViewer.viewReport(report, false);

                        //JasperPrint subreport = JasperFillManager.fillReport(subjasperReport, parameters, conIns);
                        //JasperViewer.viewReport(subreport, true);
                    }
                    conIns.close();
                    conIns=null;
                }
                catch (JRException e){
                    System.out.println("Excepci�n: " + e.toString());
                }
            }
            catch(SQLException ex){
                System.out.println("Error al conectarse a la base");
            }
            return true;
            
        }
        
        public boolean aceptar()
        {
            return true;
        }
        
        public boolean imprimir(){
            String strNomRep;
            try{
                Connection conIns;
                Statement stmIns;
                if(System.getProperty("os.name").equals("Linux")){
                    strNomRep="//Zafiro//Reportes_impresos//RptZafCom38.jrxml";
                }
                else{
                    strNomRep="C://Zafiro//Reportes_impresos//RptZafCom38.jrxml";
                }

                conIns =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
                try{
                    if(conIns!=null){
                        stmIns=conIns.createStatement();
                        // Second, create a map of parameters to pass to the report.
                        Map parameters = new HashMap();
                        parameters.put("co_emp",    "" + objParSis.getCodigoEmpresa());
                        parameters.put("co_loc",    "" + objParSis.getCodigoLocal());
                        parameters.put("co_tipDoc", "" + txtCodTipDoc.getText());
                        parameters.put("co_doc",    "" + txtCodDoc.getText());
                        parameters.put("tx_usr",    "" + objParSis.getCodigoUsuario());
                        parameters.put("fe_imp",    "" + objUti.getFecha(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos()));

                        //para llamar al reporte maestro
                         JasperDesign jasperDesign = JRXmlLoader.load(strNomRep);
                         JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);       
                         JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
                         JasperPrintManager.printReport(report, false);             


                        strSQL="";
                        strSQL+="UPDATE tbm_cabsolsaltemmer";
                        strSQL+=" SET st_imp='S'";
                        strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                        strSQL+=" AND co_doc=" + txtCodDoc.getText() + "";
                        stmIns.executeUpdate(strSQL);

                        stmIns.close();
                        stmIns=null;


                        //JasperPrint subreport = JasperFillManager.fillReport(subjasperReport, parameters, conIns);
                        //JasperViewer.viewReport(subreport, true);
                    }
                    conIns.close();
                    conIns=null;
                }
                catch (JRException e){
                    System.out.println("Excepci�n: " + e.toString());
                }
            }
            catch(SQLException ex){
                System.out.println("Error al conectarse a la base");
            }
            return true;
        }
        
        public boolean beforeInsertar(){
            boolean blnRes=true;
                if (!isCamVal())
                    blnRes=false;
            return blnRes;
        }
        
        public boolean beforeConsultar()
        {
            return true;
        }

        public boolean beforeModificar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento est� ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado"))
            {
                mostrarMsgInf("El documento est� ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }
            if (!isCamVal())
                return false;
            return true;
        }

        public boolean beforeEliminar()
        {
            strAux=objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado"))
            {
                mostrarMsgInf("El documento ya est� ELIMINADO.\nNo es posible eliminar un documento eliminado.");
                return false;
            }
            return true;
        }

        public boolean beforeAnular()
        {
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
            if(   (strEstCreEgr.equals("P")) || (strEstCreEgr.equals("T"))  ){
                mostrarMsgInf("El documento ya tiene procesos relacionados. \nNo se puede anular un documentos con procesos relacionados.");
                return false;
            }
            return true;
        }

        public boolean beforeImprimir(){
            boolean blnRes=true;
//            if(strEstImpDoc.equals("N")){
//            }
//            else{
//                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
//                blnRes=false;
//            }
            return blnRes;
        }

        public boolean beforeVistaPreliminar()
        {
            boolean blnRes=true;
//            if(strEstImpDoc.equals("N")){
//            }
//            else{
//                mostrarMsgInf("<HTML>El documento tiene estado impreso.<BR>No se puede imprimir un documento impreso si no se cuenta con los permisos respectivos.<BR>Si necesita imprimir el documento, solicite al Administrador del Sistema le conceda los permisos respectivos.</HTML>");
//                blnRes=false;
//            }
            return blnRes;
        }

        public boolean beforeAceptar()
        {
            return true;
        }
        
        public boolean beforeCancelar()
        {
            return true;
        }
        
        public boolean afterInsertar()
        {
            objTblMod.initRowsState();
            blnHayCam=false;
            objTooBar.setEstado('w');
            consultarReg();
            blnHayCam=false;
            return true;
        }

        public boolean afterConsultar()
        {
            return true;
        }

        public boolean afterModificar()
        {
            blnHayCam=false;
            strFecDocIni=dtpFecDoc.getText();
            objTblMod.setModoOperacion(objTblMod.INT_TBL_INS);
            return true;
        }

        public boolean afterEliminar()
        {
            return true;
        }

        public boolean afterAnular()
        {
            return true;
        }

        public boolean afterImprimir()
        {
            return true;
        }

        public boolean afterVistaPreliminar()
        {
            return true;
        }

        public boolean afterAceptar()
        {
            return true;
        }
        
        public boolean afterCancelar()
        {
            return true;
        }
        
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
        
        //Validar el "Tipo de documento".
        if (txtCodTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Escriba o seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }
        
        if(objParSis.getCodigoMenu()==2039){
            //Validar el "Cliente".
            if (txtCodPrv.getText().equals("")){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Cliente</FONT> es obligatorio.<BR>Escriba o seleccione un cliente y vuelva a intentarlo.</HTML>");
                txtCodPrv.requestFocus();
                return false;
            }
            //Validar el "Fecha de reingreso".
            if (!dtpFecRei.isFecha()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha de reingreso</FONT> es obligatorio.<BR>Escriba o seleccione una fecha de reingreso para el documento y vuelva a intentarlo.</HTML>");
                dtpFecRei.requestFocus();
                return false;
            }
        }

        
        //Validar el "Solicitante".
        if (txtCodVen.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Solicitante</FONT> es obligatorio.<BR>Escriba o seleccione el solicitante y vuelva a intentarlo.</HTML>");
            txtCodVen.requestFocus();
            return false;
        }
        
        
        
        //Validar el "Fecha del documento".
        if (!dtpFecDoc.isFecha()){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Fecha del documento</FONT> es obligatorio.<BR>Escriba o seleccione una fecha para el documento y vuelva a intentarlo.</HTML>");
            dtpFecDoc.requestFocus();
            return false;
        }
        else{
            intTipCamFec=canChangeDate();
            switch(intTipCamFec){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(objTooBar.getEstado()=='n'){//insertar
                            datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());

                            dtpFecDoc.setText(objUti.formatearFecha(datFecAux,"dd/MM/yyyy"));
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
                        else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            String strMsj="";
                            strMsj+="<HTML>EL documento se guardará pero tenga en cuenta las siguientes consideraciones: ";
                            strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                            strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                            strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                            strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios a la fecha del documento.";
                            strMsj+="<BR>      El documento se guardará con la fecha inicialmente almacenada así ud. coloque otra fecha.";
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

                        }
                    }
                    break;
                case 1://no puede cambiarla para nada
                    if(objTooBar.getEstado()=='n'){//insertar
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
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) != 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha del documento no puede ser cambiada.<BR>Ud. no tiene permiso para cambiar la fecha.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }

                    break;
                case 2://la fecha puede ser menor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
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
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) > 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es mayor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha posterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 3://la fecha puede ser mayor o igual a la q se presenta
                    if(objTooBar.getEstado()=='n'){//insertar
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
                    }
                    else if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        strFecDocTmp="";
                        strFecDocTmp=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy", "dd/MM/yyyy");                        
                        if(  (objUti.parseDate(strFecDocTmp, "dd/MM/yyyy")).compareTo( objUti.parseDate(strFecDocIni, "dd/MM/yyyy") ) < 0 ){
                            dtpFecDoc.setText(objUti.formatearFecha(strFecDocIni,"dd/MM/yyyy", "dd/MM/yyyy"));
                            mostrarMsgInf("<HTML>La fecha de modificación del documento es menor a la fecha ingresada inicialmente en el documento.<BR>Ud. no tiene permiso para colocar fecha anterior a la fecha ingresada inicialmente.<BR>Verifique y vuelva a intentarlo.</HTML>");
                            datFecAux=null;
                            return false;
                        }  
                    }
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        }
        
        
        
        
        //Validar que el "C�digo alterno" no se repita.
            if (!txtNumDoc1.getText().equals("")){
                strSQL="";
                strSQL+="SELECT a1.ne_numdoc";
                strSQL+=" FROM tbm_cabSolSalTemMer AS a1 ";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                strSQL+=" AND a1.ne_numdoc='" + txtNumDoc1.getText().replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                if (objTooBar.getEstado()=='m')
                    strSQL+=" AND a1.co_doc<>" + txtCodDoc.getText();
                if (!objUti.isCodigoUnico(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL))
                {
                    tabFrm.setSelectedIndex(0);
                    mostrarMsgInf("<HTML>El número de documento <FONT COLOR=\"blue\">" + txtNumDoc1.getText() + "</FONT> ya existe.<BR>Escriba otro número de documento y vuelva a intentarlo.</HTML>");
                    txtNumDoc1.selectAll();
                    txtNumDoc1.requestFocus();
                    return false;
                }
            }
            else{
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El número de documento es obligatorio.<BR>Escriba un número de documento y vuelva a intentarlo.</HTML>");
                txtNumDoc1.selectAll();
                txtNumDoc1.requestFocus();
                return false;
            }
        
        
        
            //Validar que el JTable de detalle esté completo.
            objTblMod.removeEmptyRows();
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if (!objTblMod.isRowComplete(i)){
                    mostrarMsgInf("<HTML>El documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.changeSelection(i, INT_TBL_DAT_CAN, true, true);
                    tblDat.requestFocus();
                    //i=objTblMod.getRowCountTrue();
                    return false;
                }
            }
        
            
            
            if( ! isStockSuficiente()){
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>El stock de algunos items no es suficiente.<BR>Verifique y vuelva a intentarlo.</HTML>");
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
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodPrv.setText("");
            strIdePrv="";
            txtDesLarPrv.setText("");
            strDirPrv="";
            dtpFecDoc.setText("");
            dtpFecRei.setText("");
            txtCodDoc.setText("");
            txtNumDoc1.setText("");
            txtCodVen.setText("");
            txtDesCorVen.setText("");
            txtDesLarVen.setText("");
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.removeAllRows();
            txaObs1.setText("");
            txaObs2.setText("");
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
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
                strSQL+=" a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, ";
                strSQL+=" a1.ne_ultDoc, a1.tx_natDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1";
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
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_cli='S'";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
                
            }
            else{
                if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                    strSQL="";
                    strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                    strSQL+=" FROM tbm_cli AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                    strSQL+=" AND a1.st_cli='S'";
                    strSQL+=" AND a1.st_reg='A'";
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
     * Esta funci�n muestra el tipo de documento predeterminado del programa.
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
                    txtNumDoc1.setText("" + (rst.getInt("ne_ultDoc")+1));
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
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
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
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
                    }
                    break;
                case 1: //B�squeda directa por "Descripci�n corta".
                    if (vcoTipDoc.buscar("a1.tx_desCor", txtDesCorTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
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
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
                        }
                        else
                        {
                            txtDesCorTipDoc.setText(strDesCorTipDoc);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoTipDoc.buscar("a1.tx_desLar", txtDesLarTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                        if (objTooBar.getEstado()=='n')
                        {
                            strAux=vcoTipDoc.getValueAt(4);
                            txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                        }
                        intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                        txtNumDoc1.selectAll();
                        txtNumDoc1.requestFocus();
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
                                txtNumDoc1.setText("" + (objUti.isNumero(strAux)?Integer.parseInt(strAux)+1:1));
                            }
                            intSig=(vcoTipDoc.getValueAt(5).equals("I")?1:-1);
                            txtNumDoc1.selectAll();
                            txtNumDoc1.requestFocus();
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
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoCli.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoCli.getValueAt(1));
                        strIdePrv=vcoCli.getValueAt(2);
                        txtDesLarPrv.setText(vcoCli.getValueAt(3));
                        strDirPrv=vcoCli.getValueAt(4);
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
        txtCodTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesCorTipDoc.getDocument().addDocumentListener(objDocLis);
        txtDesLarTipDoc.getDocument().addDocumentListener(objDocLis);
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
        txtCodDoc.getDocument().addDocumentListener(objDocLis);
        txtNumDoc1.getDocument().addDocumentListener(objDocLis);
    }   

    /**
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro()
    {
        boolean blnRes=true;
        strAux="Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux))
        {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado())
                {
                    case 'n': //Insertar
                        if (!isCamVal())
                            blnRes=false;
                        else
                            blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam=false;
                objTblMod.initRowsState();
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
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
                    estado="Pendiente de impresi�n";
                    break;
                case 'C':
                    estado="Pendiente confirmaci�n de inventario";
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
                case INT_TBL_DAT_COD_ITM:
                    strMsg="Código del Item";
                    break;
                case INT_TBL_DAT_COD_ALT_ITM:
                    strMsg="Código Alterno del Item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del Item";
                    break;
                case INT_TBL_DAT_STK:
                    strMsg="Stock Actual de Item";
                    break;
                case INT_TBL_DAT_COD_UNI_MED:
                    strMsg="Código de Unidad de Medida";
                    break;
                case INT_TBL_DAT_UNI_MED:
                    strMsg="Unidad de Medida";
                    break;
                case INT_TBL_DAT_COD_BOD:
                    strMsg="Código de Bodega";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Nombre de Bodega";
                    break;
                case INT_TBL_DAT_CAN:
                    strMsg="Cantidad";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
    
    
    private int canTipoModificacion(){
        Connection conChaDat;
        Statement stmChaDat;
        ResultSet rstChaDat;
        int intTipModTipDocUsr=0;
        try{
            conChaDat=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conChaDat!=null){
                stmChaDat=conChaDat.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    intTipModTipDocUsr=3;
                }
                else{
                    strSQL="";
                    strSQL+="SELECT ne_tipresmoddoc";
                    strSQL+=" FROM tbr_tipdocusr";
                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";
                    strSQL+=" AND co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND co_usr=" + objParSis.getCodigoUsuario() + "";
                    rstChaDat=stmChaDat.executeQuery(strSQL);
                    while(rstChaDat.next()){
                        intTipModTipDocUsr=rstChaDat.getInt("ne_tipresmoddoc");
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
        return intTipModTipDocUsr;
        
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
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_usr AS a1 ";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_usr, a1.tx_usr, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_usr AS a1 INNER JOIN tbr_usrEmp AS a2";
                strSQL+=" ON a1.co_usr=a2.co_usr";
                strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a1.st_reg='A'";
                strSQL+=" AND a2.st_ven='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }

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
            arlCam.add("a1.co_itm");
            arlCam.add("a1.tx_codAlt");
            arlCam.add("a1.tx_nomItm");
            arlCam.add("nd_stkact");
            arlCam.add("co_uni");
            arlCam.add("a4.tx_descor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código Item");
            arlAli.add("Código Alterno Item");
            arlAli.add("Nombre del Item");
            arlAli.add("Stock del Item");
            arlAli.add("Código de Unidad de Medida");
            arlAli.add("Nombre de Unidad de Medida");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("290");
            arlAncCol.add("70");
            arlAncCol.add("30");
            arlAncCol.add("50");
            //Armar la sentencia SQL.
//            strSQL="";
//            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a1.nd_stkact, a1.co_uni, a2.tx_descor";
//            strSQL+=" FROM tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a2";
//            strSQL+=" ON a1.co_uni=a2.co_reg";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_reg IN('A') AND a1.nd_stkact>0";
//            strSQL+=" ORDER BY a1.tx_codAlt";
            strSQL="";
            strSQL+="SELECT a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, SUM(a2.nd_stkact) AS nd_stkact, a4.co_reg AS co_uni, a4.tx_descor";
            strSQL+=" FROM (tbm_inv AS a1 LEFT OUTER JOIN tbm_var AS a4 ON a1.co_uni=a4.co_reg) INNER JOIN tbm_invBod AS a2";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
            strSQL+=" INNER JOIN tbr_bodLoc AS a3";
            strSQL+=" ON a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + " AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" GROUP BY a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a4.co_reg, a4.tx_descor";
            strSQL+=" HAVING SUM(a2.nd_stkact)>0";
            strSQL+=" ORDER BY a1.tx_nomItm, a1.co_itm, a1.tx_codAlt, a4.co_reg, a4.tx_descor";

            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=5;
            
            //Ocultar columnas.
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de items", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            
            
            
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoItm.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT, vcoItm.INT_FOR_NUM, objParSis.getFormatoNumero(),false,true);
            vcoItm.setCampoBusqueda(1);
            vcoItm.setCriterio1(7);
            intColOcu=null;
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
     
     
    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Bodegas".
     */
    private boolean configurarVenConBod()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_bod");
            arlCam.add("a1.tx_nom");
            arlCam.add("a2.nd_stkAct");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código Bodega");
            arlAli.add("Nombre de Bodega");
            arlAli.add("Stock de Bodega");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_bod, a1.tx_nom, a2.nd_stkAct";
            strSQL+=" FROM tbm_bod AS a1";
            strSQL+=" INNER JOIN tbm_invBod AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod)";
            strSQL+=" INNER JOIN tbr_bodLoc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
            strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
            strSQL+=" AND a1.st_reg='A'";
            strSQL+=" AND a3.st_reg IN('A','P')";
            //Ocultar columnas.
            vcoBod=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de bodegas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoBod.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoBod.setCampoBusqueda(0);
            vcoBod.setCriterio1(7);
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
     * Esta funci�n inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg()
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (insertar_tbmCabSolSalTemMer()){
                    if (insertar_tbmDetSolSalTemMer()){
                        if(actualiza_tbmCabTipDoc()){
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
     * Esta funci�n permite insertar la cabecera de un registro.
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmCabSolSalTemMer(){
        int intCodUsr, intUltReg;
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                intCodUsr=objParSis.getCodigoUsuario();
                //Obtener el c�digo del �ltimo registro.
                strSQL="";
                strSQL+="SELECT MAX(a1.co_doc)";
                strSQL+=" FROM tbm_cabSolSalTemMer AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText();
                intUltReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intUltReg==-1)
                    return false;
                intUltReg++;
                txtCodDoc.setText("" + intUltReg);
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabSolSalTemMer(";
                strSQL+="co_emp,co_loc,co_tipDoc,co_doc,";
                strSQL+="fe_doc,fe_rei,ne_numDoc,co_cli,";
                strSQL+="co_usrSol,co_mnu,st_imp,st_aut,";
                strSQL+="tx_obsAut,fe_aut,st_creDocEgr,";
                strSQL+="fe_creTodDocEgr,";
                strSQL+="st_conTotMerEgr,fe_conTotMerEgr,";
                strSQL+="st_exiMerSinEgr,st_creDocIng,fe_creTodDocIng,";
                strSQL+="st_conTotMerIng,fe_conTotMerIng,st_exiMerSinIng,";
                strSQL+="st_facMerSalDem,st_merSalDemFac,tx_obs1,";
                strSQL+="tx_obs2,st_reg,fe_ing,fe_ultMod,";
                strSQL+="co_usrIng,co_usrMod,st_regRep)";
                strSQL+="VALUES(";
                strSQL+=" " + objParSis.getCodigoEmpresa() + "";//co_emp
                strSQL+="," + objParSis.getCodigoLocal() + "";//co_loc
                strSQL+="," + txtCodTipDoc.getText() + "";//co_tipDoc
                strSQL+="," + txtCodDoc.getText() + "";//co_doc
                strSQL+=", '" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_doc
                if(objParSis.getCodigoMenu()==2039)
                    strSQL+=", '" + objUti.formatearFecha(dtpFecRei.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";//fe_rei
                else
                    strSQL+=", Null";//fe_rei
                strSQL+="," + txtNumDoc1.getText() + "";//ne_numDoc
                if(objParSis.getCodigoMenu()==2039)
                    strSQL+="," + txtCodPrv.getText() + "";//co_cli
                else
                    strSQL+=", Null";//co_cli
                strSQL+="," + txtCodVen.getText() + "";//co_usrSol
                strSQL+="," + objParSis.getCodigoMenu() + "";//co_mnu
                strSQL+=",'N'";//st_imp
                strSQL+=",'P'";//st_aut
                strSQL+=", Null";//tx_obsAut
                strSQL+=", Null";//fe_aut
                strSQL+=",'N'";//st_creDocEgr
                strSQL+=",Null";//fe_creDocEgr
                strSQL+=",'N'";//st_conTotMerEgr
                strSQL+=",Null";//fe_conTotMerEgr
                strSQL+=",'N'";//st_exiMerSinEgr
                strSQL+=",'N'";//st_creDocIng
                strSQL+=",Null";//fe_creDocIng
                strSQL+=",'N'";//st_conTotMerIng
                strSQL+=",Null";//fe_conTotMerIng
                strSQL+=",'N'";//st_exiMerSinIng
                strSQL+=",'N'";//st_facMerSalDem
                strSQL+=",'N'";//st_merSalDemFac
                strSQL+="," + objUti.codificar(txaObs1.getText()) + "";//tx_obs1
                strSQL+="," + objUti.codificar(txaObs2.getText()) + "";//tx_obs2
                strSQL+=",'A'";//st_reg
                strAux=objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos());
                strSQL+=",'" + strAux + "'";//fe_ing
                strSQL+=",'" + strAux + "'";//fe_ultMod
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrIng
                strSQL+="," + objParSis.getCodigoUsuario() + "";//co_usrMod
                strSQL+=",'I'";//st_regRep
                strSQL+=");";
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
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
     * Esta funci�n permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertar_tbmDetSolSalTemMer(){
        int j;
        boolean blnRes=true;
        String strSQLTmp;
        try{
            if (con!=null){
                stm=con.createStatement();
                j=1;
                strSQLTmp="";
                for (int i=0;i<objTblMod.getRowCountTrue();i++){
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detSolSalTemMer(";
                    strSQL+="co_emp,co_loc,co_tipDoc,co_doc,co_reg,co_itm,co_bod,nd_can,";
                    strSQL+="nd_canTotEgr,nd_canNunEgr,nd_canTotIng,nd_canNunIng,st_regRep)";
                    strSQL+=" VALUES (";
                    strSQL+="" + objParSis.getCodigoEmpresa() + "";//co_emp
                    strSQL+=", " + objParSis.getCodigoLocal() + "";//co_loc
                    strSQL+=", " + txtCodTipDoc.getText() + "";//co_tipDoc
                    strSQL+=", " + txtCodDoc.getText() + "";//co_doc
                    strSQL+=", " + j + "";//co_reg
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_ITM);//co_itm
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_COD_BOD) + "";//co_bod
                    strSQL+=", " + objTblMod.getValueAt(i,INT_TBL_DAT_CAN) + "";//nd_can
                    strSQL+=", " + new java.math.BigDecimal(0.00) + "";//nd_canTotEgr
                    strSQL+=", " + new java.math.BigDecimal(0.00) + "";//nd_canNunEgr
                    strSQL+=", " + new java.math.BigDecimal(0.00) + "";//nd_canTotIng
                    strSQL+=", " + new java.math.BigDecimal(0.00) + "";//nd_canNunIng
                    strSQL+=", 'I'";//st_regRep
                    strSQL+=");";
                    strSQLTmp+=strSQL;
                    j++;
                }
                stm.executeUpdate(strSQLTmp);
                stm.close();
                stm=null;
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
    
    
    
    private boolean isStockSuficiente(){
        boolean blnRes=true;
        Connection conStk;
        Statement stmStk;
        ResultSet rstStk;
        java.math.BigDecimal bdeStkNec=new java.math.BigDecimal(0);
        java.math.BigDecimal bdeStkHay=new java.math.BigDecimal(0);
        String strMsgStk="";
        String strNomBod="";
        objTblMod.clearRowHeaderRaise();
        try{
            conStk=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conStk!=null){
                stmStk=conStk.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, SUM(a2.nd_stkact) AS nd_stkact, a2.co_bod, c1.tx_nom";
                    strSQL+=" FROM tbm_inv AS a1 INNER JOIN (tbm_invBod AS a2 INNER JOIN tbm_bod AS c1 ON a2.co_emp=c1.co_emp AND a2.co_bod=c1.co_bod)";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_itm=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_ITM) + "";
                    strSQL+=" AND a2.co_bod=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_BOD) + "";
                    strSQL+=" GROUP BY a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a2.co_bod, c1.tx_nom";
                    strSQL+=" HAVING SUM(a2.nd_stkact)>0";
                    strSQL+=" ORDER BY a1.tx_nomItm, a1.co_itm, a1.tx_codAlt, a2.co_bod";
                    rstStk=stmStk.executeQuery(strSQL);
                    bdeStkNec=new java.math.BigDecimal(objTblMod.getValueAt(i, INT_TBL_DAT_CAN).toString());
                    strMsgStk="";                    
                    if(rstStk.next()){
                        bdeStkHay=rstStk.getBigDecimal("nd_stkact");
                        strNomBod=rstStk.getString("tx_nom");
                        if(bdeStkHay.compareTo(bdeStkNec) < 0){
                            strMsgStk+="<HTML>El stock no cubre lo necesitado";
                            strMsgStk+="<BR>El stock en la " + strNomBod + " es: " + rstStk.getBigDecimal("nd_stkact") + "";
                            strMsgStk+="</HTML>";
                            objTblMod.addElementAtRowHeaderRaise(i, strMsgStk);
                            blnRes=false;
                        }
                    }
                    else{
                        strMsgStk+="<HTML>No hay stock en la bodega seleccionada";
                        strMsgStk+="</HTML>";
                        objTblMod.addElementAtRowHeaderRaise(i, strMsgStk);
                        blnRes=false;
                    }
                    rstStk.close();
                    
                }
                stmStk.close();
                stmStk=null;
                rstStk=null;
                conStk.close();
                conStk=null;
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
     * Esta funci�n permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg(){
        boolean blnRes=true;
        try{
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null){
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                //Validar que s�lo se muestre los documentos asignados al programa.
                if (txtCodTipDoc.getText().equals("")){
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabSolSalTemMer AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ";
                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ";
                    strSQL+=" ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli AND a4.st_reg='A')";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS b5 ";
                    strSQL+=" ON (a1.co_usrSol=b5.co_usr)";
                    strSQL+=" LEFT OUTER JOIN tbr_tipDocPrg AS a5 ";
                    strSQL+=" ON (a2.co_emp=a5.co_emp AND a2.co_loc=a5.co_loc AND a2.co_tipDoc=a5.co_tipDoc)";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a5.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a5.st_reg NOT IN('I','E')";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=" FROM tbm_cabSolSalTemMer AS a1";
                    strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ";
                    strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                    strSQL+=" LEFT OUTER JOIN tbm_cli AS a4 ";
                    strSQL+=" ON (a1.co_emp=a4.co_emp AND a1.co_cli=a4.co_cli)";
                    strSQL+=" LEFT OUTER JOIN tbm_usr AS a5 ";
                    strSQL+=" ON (a1.co_usrSol=a5.co_usr)";
                    
                    if(objParSis.getCodigoMenu()==2059){
                        strSQL+=" WHERE a1.co_emp=" + intCodEmp + "";
                        strSQL+=" AND a1.co_loc=" + intCodLoc + "";
                    }
                    else{
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    }
                }
                strAux=txtCodTipDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_tipDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
                if(objParSis.getCodigoMenu()==2039){
                    strAux=txtCodPrv.getText();
                    if (!strAux.equals(""))
                        strSQL+=" AND a1.co_cli LIKE '" + strAux.replaceAll("'", "''") + "'";
                }

                strAux=txtCodVen.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_usrSol LIKE '" + strAux.replaceAll("'", "''") + "'";
                strAux=txtCodDoc.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_doc LIKE '" + strAux.replaceAll("'", "''") + "'";

                
                if (dtpFecDoc.isFecha())
                    strSQL+=" AND a1.fe_doc='" + objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";
                
                
                if(objParSis.getCodigoMenu()==2039){
                    if (dtpFecRei.isFecha())
                        strSQL+=" AND a1.fe_rei='" + objUti.formatearFecha(dtpFecRei.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos()) + "'";                    
                }


                strAux=txtNumDoc1.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.ne_numDoc LIKE '" + strAux.replaceAll("'", "''") + "'";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" ORDER BY a1.co_loc, a1.co_tipDoc, a1.co_doc";
                rstCab=stmCab.executeQuery(strSQL);
                if (rstCab.next())
                {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " registros");
                    rstCab.first();
                    cargarReg();
                    strSQLCon=strSQL;
                }
                else
                {
                    mostrarMsgInf("No se ha encontrado ning�n registro que cumpla el criterio de b�squeda especificado.");
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
     * Esta funci�n permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg(){
        boolean blnRes=true;
        try{
            if (cargarCabReg()){
                if (cargarDetReg()){
                    blnHayCam=false;
                    objTblMod.initRowsState();
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
     * Esta funci�n permite cargar la cabecera del registro seleccionado.
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
                strSQL+="SELECT a1.co_tipDoc, a2.tx_desCor, a2.tx_desLar, ";
                strSQL+=" a1.co_doc, a1.fe_doc, a1.fe_rei, a1.ne_numDoc";
                strSQL+=" , a1.co_cli, a3.tx_ide AS tx_ruc, a3.tx_nom AS tx_nomCli, a3.tx_dir AS tx_dirCli, ";
                strSQL+=" a1.tx_obs1, a1.tx_obs2, a1.st_reg, a1.st_imp";
                strSQL+=" , a4.co_usr, a4.tx_usr, a4.tx_nom, a1.st_aut, a1.st_credocegr, a1.co_mnu";
                strSQL+=" FROM tbm_cabSolSalTemMer AS a1";
                strSQL+=" LEFT OUTER JOIN tbm_cabTipDoc AS a2 ";
                strSQL+=" ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" LEFT OUTER JOIN tbm_cli AS a3 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp AND a1.co_cli=a3.co_cli AND a3.st_reg='A')";
                strSQL+=" LEFT OUTER JOIN tbm_usr AS a4 ";
                strSQL+=" ON (a1.co_usrSol=a4.co_usr AND a4.st_reg='A')";
                strSQL+=" WHERE a1.co_emp=" + rstCab.getString("co_emp");
                strSQL+=" AND a1.co_loc=" + rstCab.getString("co_loc");
                strSQL+=" AND a1.co_tipDoc=" + rstCab.getString("co_tipDoc");
                strSQL+=" AND a1.co_doc=" + rstCab.getString("co_doc");
                rst=stm.executeQuery(strSQL);
                if (rst.next()){
                    strAux=rst.getString("co_tipDoc");
                    txtCodTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desCor");
                    txtDesCorTipDoc.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_desLar");
                    txtDesLarTipDoc.setText((strAux==null)?"":strAux);
                    
                    
                    strAux=rst.getString("co_cli");
                    txtCodPrv.setText((strAux==null)?"":strAux);
                    strIdePrv=rst.getString("tx_ruc");
                    strAux=rst.getString("tx_nomCli");
                    txtDesLarPrv.setText((strAux==null)?"":strAux);
                    strDirPrv=rst.getString("tx_dirCli");
                    
                    strAux=rst.getString("co_usr");
                    txtCodVen.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_usr");
                    txtDesCorVen.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_nom");
                    txtDesLarVen.setText((strAux==null)?"":strAux);
                    
                    strAux=rst.getString("co_doc");
                    txtCodDoc.setText((strAux==null)?"":strAux);
                    
                    
                    dtpFecDoc.setText(objUti.formatearFecha(rst.getDate("fe_doc"),"dd/MM/yyyy"));
                    
                    strFecDocIni=dtpFecDoc.getText();
                    strEstImpDoc=rst.getString("st_imp");
                    

                    dtpFecRei.setText(objUti.formatearFecha(rst.getDate("fe_rei"),"dd/MM/yyyy"));

                    
                    
                    strAux=rst.getString("ne_numDoc");
                    txtNumDoc1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs1");
                    txaObs1.setText((strAux==null)?"":strAux);
                    strAux=rst.getString("tx_obs2");
                    txaObs2.setText((strAux==null)?"":strAux);
                    //Mostrar el estado del registro.
                    strAux=rst.getString("st_reg");
                    objTooBar.setEstadoRegistro(getEstReg(strAux));
                    //strEstDoc=rst.getString("st_aut");
                    strEstCreEgr=rst.getString("st_credocegr");
                    
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
            //Mostrar la posici�n relativa del registro.
            intPosRel=rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
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
     * Esta funci�n permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();
                con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                if (con!=null){
                    stm=con.createStatement();
                    strSQL="";
                    strSQL+="SELECT a2.co_itm, x.tx_codAlt, x.tx_nomItm, x.nd_stkAct, x.co_reg, x.tx_desCor, x.co_bod, x.tx_nom, a2.nd_can";
                    strSQL+=" FROM tbm_cabSolSalTemMer AS a1";
                    strSQL+=" INNER JOIN tbm_detSolSalTemMer AS a2 ";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+=" INNER JOIN(";
                    strSQL+=" 	SELECT b1.co_emp, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, b2.co_reg, b2.tx_descor, b4.co_bod, b4.tx_nom, b3.nd_stkAct";
                    strSQL+=" 	FROM  (tbm_inv AS b1 LEFT OUTER JOIN tbm_var AS b2 ON b1.co_uni=b2.co_reg)";
                    strSQL+=" 	   INNER JOIN tbm_invBod AS b3 ON b1.co_emp=b3.co_emp AND b1.co_itm=b3.co_itm";
                    strSQL+=" 		INNER JOIN tbm_bod AS b4 ON b3.co_emp=b4.co_emp AND b3.co_bod=b4.co_bod) AS x";
                    strSQL+=" ON a2.co_emp=x.co_emp AND a2.co_itm=x.co_itm AND a2.co_bod=x.co_bod";
                    strSQL+=" AND a2.co_emp=" + rstCab.getString("co_emp");
                    strSQL+=" AND a2.co_loc=" + rstCab.getString("co_loc");
                    strSQL+=" AND a2.co_tipDoc=" + rstCab.getString("co_tipDoc");
                    strSQL+=" AND a2.co_doc=" + rstCab.getString("co_doc");
                    strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_doc, a2.co_reg";
                    rst=stm.executeQuery(strSQL);
                    vecDat.clear();
                    //Obtener los registros.
                    while (rst.next()){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_ITM,         rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT_ITM,     rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_BUT_ITM,         "");
                        vecReg.add(INT_TBL_DAT_NOM_ITM,         rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_STK,             rst.getString("nd_stkAct"));
                        vecReg.add(INT_TBL_DAT_COD_UNI_MED,     rst.getString("co_reg"));
                        vecReg.add(INT_TBL_DAT_UNI_MED,         rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_COD_BOD,         rst.getString("co_bod"));
                        vecReg.add(INT_TBL_DAT_BUT_BOD,         "");
                        vecReg.add(INT_TBL_DAT_NOM_BOD,         rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_DAT_CAN,             rst.getString("nd_can"));
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
    
    
    
    
    private void inactivarForm(){
        txtCodTipDoc.setEditable(false);
        txtDesCorTipDoc.setEditable(false);
        txtDesLarTipDoc.setEditable(false);
        butTipDoc.setEnabled(false);
        txtCodPrv.setEditable(false);
        txtDesLarPrv.setEditable(false);
        butPrv.setEnabled(false);
        txtCodVen.setEditable(false);
        txtDesCorVen.setEditable(false);
        txtDesLarVen.setEditable(false);
        butVen.setEnabled(false);
        txtCodDoc.setEditable(false);
        dtpFecDoc.setEnabled(false);
        dtpFecRei.setEnabled(false);
        txtNumDoc1.setEditable(false);
        txaObs1.setEditable(false);
        txaObs2.setEditable(false);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        
    }
    
    
    
    private boolean camposInactivosPermisoModifi(){
        boolean blnRes=true;
        try{
            int intTipModDoc;
            String strFecDocTmp;
            intTipModDoc=canTipoModificacion();
            switch(intTipModDoc){
                case 0://esto lo coloque en caso que el registro no se encuentre en tbr_tipDocUsr porque devuelve 0 la función.
                    if(objParSis.getCodigoUsuario()!=1){
                        if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butPrv.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            dtpFecRei.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));

                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else{
                                String strMsj="";
                                strMsj+="<HTML>EL documento no se puede modificar por las siguientes razones:";
                                strMsj+="<BR>      Ud no cuenta con el permiso adecuado para trabajar con este documento.";
                                strMsj+="<BR>      Por el momento está trabajando con el Tipo de Documento predeterminado.";
                                strMsj+="<BR>      Solicite a su Administrador del Sistema le conceda los permisos adecuados.";
                                strMsj+="<BR>      Mientras no los solicite, ud no podrá hacerle cambios al documento.";
                                strMsj+="</HTML>";
                                mostrarMsgInf(strMsj);
                            }

                        }
                    }
                    break;
                case 1://no puede modificar nada, incluyendo fecha del documento
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butPrv.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            dtpFecRei.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                        if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                        }
                        else
                            mostrarMsgInf("<HTML>Ud. no cuenta con ningún tipo de permiso para Modificar.<BR>.Solicite a su Adminsitrador del Sistema dicho permiso y vuelva a intentarlo.</HTML>");
                    }
                    
                    break;
                case 2://modificación parcial la modificación de la fecha dependerá de si se cuenta con este permiso
                    if(    (objTooBar.getEstado()=='x')  ||  (objTooBar.getEstado()=='m')   ){//modificar
                        if( ! strEstImpDoc.equals("S")){//si el documento no está impreso se puede modificar, la modif. de fecha depende de tbr_tipDocUsr.ne_tipresmodfecdoc
                            dtpFecDoc.setEnabled(true);
                            dtpFecRei.setEnabled(false);
                            txtNumDoc1.setEditable(true);
                            txtCodPrv.setEditable(true);
                            txtDesLarPrv.setEditable(true);
                            butPrv.setEnabled(true);
                            txaObs1.setEditable(true);
                            txaObs2.setEditable(true);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                        }
                        else{//si el documento está impreso no se permite modificar
                            txtCodPrv.setEditable(false);
                            txtDesLarPrv.setEditable(false);
                            butPrv.setEnabled(false);
                            dtpFecDoc.setEnabled(false);
                            dtpFecRei.setEnabled(false);
                            txtNumDoc1.setEditable(false);
                            txaObs1.setEditable(false);
                            txaObs2.setEditable(false);
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            txtNumDoc1.setBackground(new java.awt.Color(255, 255, 255));
                            objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
                            if(   (objTooBar.getOperacionSeleccionada().equals("i")) || (objTooBar.getOperacionSeleccionada().equals("f"))  ||  (objTooBar.getOperacionSeleccionada().equals("a"))  || (objTooBar.getOperacionSeleccionada().equals("s"))    ){
                            }
                            else
                                mostrarMsgInf("<HTML>El documento consultado no se puede modificar porque ya está impreso.</HTML>");
                        }
                    }
                    break;
                case 3://modificación completa, la modificación de la fecha dependerá de si se cuenta con este permiso
                    dtpFecDoc.setEnabled(true);
                    break;
                default:
                    break;
            }
            objTooBar.setOperacionSeleccionada("n");
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    
    
    
    
    
    
    /**
     * Esta funci�n actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (actualizar_tbmCabSolSalTemMer()){
                    if (eliminar_tbmDetSolSalTemMer()){
                        if (insertar_tbmDetSolSalTemMer()){
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
     * Esta funci�n permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizar_tbmCabSolSalTemMer(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabsolsaltemmer";
                strAux=objUti.formatearFecha(dtpFecDoc.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                strSQL+="    SET fe_doc='" + strAux + "',";
                strAux=objUti.formatearFecha(dtpFecRei.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
                
                if(objParSis.getCodigoMenu()==2039)
                    strSQL+="        fe_rei='" + strAux + "',";
                else
                    strSQL+="        fe_rei=Null,";
                
                strSQL+="        ne_numdoc=" + objUti.codificar(txtNumDoc1.getText(),2) + ",";
                strSQL+="        co_cli=" + objUti.codificar(txtCodPrv.getText(),2) + ",";
                strSQL+="        tx_obs1=" + objUti.codificar(txaObs1.getText()) + ",";
                strSQL+="        tx_obs2=" + objUti.codificar(txaObs2.getText()) + ",";
                strSQL+="        fe_ultmod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "',";
                strSQL+="        co_usrmod=" + objParSis.getCodigoUsuario() + ",";
                strSQL+="        st_regrep='M'";
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
     * Esta funci�n permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmDetSolSalTemMer(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="DELETE FROM tbm_detSolSalTemMer";
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
    
    /**
     * Esta funci�n anula el registro de la base de datos.
     * @return true: Si se pudo anular el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anularReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (anular_tbmCabSolSalTemMer()){
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
     * Esta funci�n permite anular la cabecera de un registro.
     * @return true: Si se pudo anular la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean anular_tbmCabSolSalTemMer(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabSolSalTemMer";
                strSQL+=" SET st_reg='I'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
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
     * Esta funci�n elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con!=null){
                if (eliminar_tbmCabSolSalTemMer()){
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
     * Esta funci�n permite eliminar la cabecera de un registro.
     * @return true: Si se pudo eliminar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminar_tbmCabSolSalTemMer(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Obtener la fecha del servidor.
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabSolSalTemMer";
                strSQL+=" SET st_reg='E'";
                strSQL+=", fe_ultMod='" + objUti.formatearFecha(datFecAux, objParSis.getFormatoFechaHoraBaseDatos()) + "'";
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
     * Esta funci�n permite actualizar la cabecera de un registro.
     * @return true: Si se pudo actualizar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualiza_tbmCabTipDoc(){
        boolean blnRes=true;
        try{
            if (con!=null){
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="UPDATE tbm_cabTipDoc";
                strSQL+="    SET ne_ultDoc=ne_ultDoc+1";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText();
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
    
    
    
    
    
}