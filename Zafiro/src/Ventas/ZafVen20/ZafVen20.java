/*
 * ZafVen20.java
 *
 * Created on 18 de mayo de 2009, 16:15 PM
 */

package Ventas.ZafVen20;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import java.awt.Color;
import Librerias.ZafPerUsr.ZafPerUsr;

/**
 *
 * @author  Eddye Lino
 */
public class ZafVen20 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_COD_MAE=1;                     //Código maestro del item.
    static final int INT_TBL_DAT_COD_SIS=2;                     //Código del item (Sistema).
    static final int INT_TBL_DAT_COD_ALT=3;                     //Código del item (Alterno).
    static final int INT_TBL_DAT_NOM_ITM=4;                     //Nombre del item.
    static final int INT_TBL_DAT_TOT_UNI_VEN=5;                 //Precio unitario de venta 1.
    static final int INT_TBL_DAT_VEN_TOT=6;                 //Precio unitario de venta 1.
    static final int INT_TBL_DAT_POR_VTA=7;                 //Precio unitario de venta 1.
    static final int INT_TBL_DAT_CAL_PAR=8;                 //Precio unitario de venta 1.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLblCol1, objTblCelRenLblCol2, objTblCelRenLblCol3, objTblCelRenLblCol4;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    private boolean blnMarTodChkTblBod;                    //Marcar todas las casillas de verificación del JTable de bodegas.

    private String strCodEmp, strNomEmp, strCodLoc, strNomLoc;
    private ZafVenCon vcoEmp, vcoLoc;


    private int intNumColFinColEst;//numero de columnas estaticas

    private int intNumColAdiColBod;//numero de columnas que se deben adicionar al modelo por vistos buenos
    private int intNumColIniColBod;//numero de columna desde donde empieza la columna adicionada por visto bueno
    private int intNumColFinColBod;//numero de columna hasta donde termina la columna adicionada por visto bueno

    private ZafSelFec objSelFec;

    private BigDecimal bdeSumTotVen;
    private ZafPerUsr objPerUsr;

    /** Crea una nueva instancia de la clase ZafVen20. */
    public ZafVen20(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objPerUsr=new ZafPerUsr(objParSis);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFil = new javax.swing.JPanel();
        panCon = new javax.swing.JPanel();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkVtaCli = new javax.swing.JCheckBox();
        chkVtaGrp = new javax.swing.JCheckBox();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        lblEmp1 = new javax.swing.JLabel();
        txtCodLoc = new javax.swing.JTextField();
        txtNomLoc = new javax.swing.JTextField();
        butLoc = new javax.swing.JButton();
        panRpt = new javax.swing.JPanel();
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
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panCon.setPreferredSize(new java.awt.Dimension(0, 340));
        panCon.setLayout(null);

        panBod.setBorder(javax.swing.BorderFactory.createTitledBorder("Listado de bodegas"));
        panBod.setLayout(new java.awt.BorderLayout());

        tblBod.setModel(new javax.swing.table.DefaultTableModel(
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
        spnBod.setViewportView(tblBod);

        panBod.add(spnBod, java.awt.BorderLayout.CENTER);

        panCon.add(panBod);
        panBod.setBounds(20, 140, 640, 170);

        chkVtaCli.setSelected(true);
        chkVtaCli.setText("Ventas a clientes");
        chkVtaCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkVtaCliActionPerformed(evt);
            }
        });
        panCon.add(chkVtaCli);
        chkVtaCli.setBounds(0, 66, 380, 14);

        chkVtaGrp.setText("Ventas a empresas del grupo");
        panCon.add(chkVtaGrp);
        chkVtaGrp.setBounds(0, 80, 270, 14);

        lblEmp.setText("Empresa:");
        lblEmp.setToolTipText("Vendedor/Comprador");
        panCon.add(lblEmp);
        lblEmp.setBounds(22, 94, 60, 20);

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
        panCon.add(txtCodEmp);
        txtCodEmp.setBounds(88, 94, 60, 20);

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
        panCon.add(txtNomEmp);
        txtNomEmp.setBounds(148, 94, 410, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panCon.add(butEmp);
        butEmp.setBounds(558, 94, 20, 20);

        lblEmp1.setText("Local:");
        lblEmp1.setToolTipText("Vendedor/Comprador");
        panCon.add(lblEmp1);
        lblEmp1.setBounds(22, 114, 60, 20);

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
        panCon.add(txtCodLoc);
        txtCodLoc.setBounds(88, 114, 60, 20);

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
        panCon.add(txtNomLoc);
        txtNomLoc.setBounds(148, 114, 410, 20);

        butLoc.setText("...");
        butLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butLocActionPerformed(evt);
            }
        });
        panCon.add(butLoc);
        butLoc.setBounds(558, 114, 20, 20);

        panFil.add(panCon, java.awt.BorderLayout.PAGE_START);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

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
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

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
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (butCon.getText().equals("Consultar"))
        {
            blnCon=true;
            if (objThrGUI==null)
            {
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

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
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

    private void chkVtaCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkVtaCliActionPerformed
        // TODO add your handling code here:
        if(!chkVtaCli.isSelected()){
            chkVtaCli.setSelected(true);
        }
    }//GEN-LAST:event_chkVtaCliActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butLoc;
    private javax.swing.JCheckBox chkVtaCli;
    private javax.swing.JCheckBox chkVtaGrp;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblEmp1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodLoc;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomLoc;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFec=new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panCon.add(objSelFec);
            objSelFec.setBounds(4, 0, 472, 68);

            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.4");
            lblTit.setText(strAux);
            //Configurar objetos.
            //Configurar las ZafVenCon.
            configurarVenConLoc();
            //Configurar los JTables.
            configurarTblBod();
            configurarTblDat();
            cargarBod();
            agregarColTblDat();

            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();

                txtCodEmp.setEditable(true);
                txtNomEmp.setEditable(true);
                butEmp.setEnabled(true);
            }
            else{
                lblEmp.setVisible(false);
                txtCodEmp.setVisible(false);
                txtNomEmp.setVisible(false);
                butEmp.setVisible(false);
            }

            if(objParSis.getCodigoUsuario()!=1){
                if(objParSis.getCodigoMenu()==861){
                    if(objPerUsr.isOpcionEnabled(862)){//consultar
                        butCon.setVisible(true);
                        butCon.setEnabled(true);
                    }
                    if(objPerUsr.isOpcionEnabled(863)){//cerrar
                        butCer.setVisible(true);
                        butCer.setEnabled(true);
                    }
                }
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
     * Esta función configura el JTable "tblBod".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblBod()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_BOD_LIN,"");
            vecCab.add(INT_TBL_BOD_CHK,"");
            vecCab.add(INT_TBL_BOD_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_BOD_NOM_EMP,"Empresa");
            vecCab.add(INT_TBL_BOD_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_BOD_NOM_BOD,"Bodega");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblModBod=new ZafTblMod();
            objTblModBod.setHeader(vecCab);
            tblBod.setModel(objTblModBod);
            //Configurar JTable: Establecer tipo de selección.
            tblBod.setRowSelectionAllowed(true);
            tblBod.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblBod);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblBod.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblBod.getColumnModel();
            tcmAux.getColumn(INT_TBL_BOD_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_EMP).setPreferredWidth(231);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_BOD_NOM_BOD).setPreferredWidth(231);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_BOD_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblBod.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblBod);
            objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_NOM_EMP, tblBod);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            tblBod.getTableHeader().addMouseMotionListener(new ZafMouMotAdaBod());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblBod.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    tblBodMouseClicked(evt);
                }
            });
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BOD_CHK);
            objTblModBod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
//            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Editor de búsqueda.
//            objTblBus=new ZafTblBus(tblBod);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_BOD_COD_EMP).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_BOD_COD_BOD).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblBod);
            tcmAux.getColumn(INT_TBL_BOD_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int i;
                    i=tblBod.getSelectedRow();
                    if (objTblCelEdiChk.isChecked()){
                        //Mostrar columnas.
                        objTblMod.removeSystemHiddenColumn(intNumColIniColBod+i, tblDat);
                    }
                    else{
                        //Ocultar columnas.
                        objTblMod.addSystemHiddenColumn(intNumColIniColBod+i, tblDat);
                    }
                }
            });
            //Configurar JTable: Establecer el modo de operación.
            objTblModBod.setModoOperacion(objTblModBod.INT_TBL_EDI);
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
     * Esta función configura el JTable "tblDat".
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
            vecCab=new Vector(9);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_MAE,"Cód.Mae.");
            vecCab.add(INT_TBL_DAT_COD_SIS,"Cód.Sis.");
            vecCab.add(INT_TBL_DAT_COD_ALT,"Cód.Alt.");
            vecCab.add(INT_TBL_DAT_NOM_ITM,"Nombre");
            vecCab.add(INT_TBL_DAT_TOT_UNI_VEN,"Uni.Ven.");
            vecCab.add(INT_TBL_DAT_VEN_TOT,"");//Ven.Tot.
            vecCab.add(INT_TBL_DAT_POR_VTA,"");//% Vta.
            vecCab.add(INT_TBL_DAT_CAL_PAR,"");//Cal.Par.


            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            //Si es el usuario Administrador (Código=1) habilito el ZafTblPopMnu.
//            if (objParSis.getCodigoUsuario()==1)
//            {
                objTblPopMnu=new ZafTblPopMnu(tblDat);
//            }
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setPreferredWidth(257);
            tcmAux.getColumn(INT_TBL_DAT_TOT_UNI_VEN).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_VEN_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_POR_VTA).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAL_PAR).setPreferredWidth(60);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_MAE, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_SIS, tblDat);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus=new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);


            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_GEN);
            tcmAux.getColumn(INT_TBL_DAT_COD_MAE).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COD_SIS).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            //para color
            objTblCelRenLblCol1=new ZafTblCelRenLbl();
            objTblCelRenLblCol1.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLblCol1.setTipoFormato(objTblCelRenLblCol1.INT_FOR_GEN);
            objTblCelRenLblCol1.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_COD_ALT).setCellRenderer(objTblCelRenLblCol1);
            tcmAux.getColumn(INT_TBL_DAT_NOM_ITM).setCellRenderer(objTblCelRenLblCol1);
            objTblCelRenLblCol1.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColBckWht;
                java.awt.Color colFonColBckGrn;
                String strLin="";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColBckWht=new java.awt.Color(255,255,255);
                    colFonColBckGrn=new java.awt.Color(228,228,203);

                    strLin=objTblMod.getValueAt(objTblCelRenLblCol1.getRowRender(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(objTblCelRenLblCol1.getRowRender(), INT_TBL_DAT_LIN).toString();

                    if(strLin.equals("M")){
                        objTblCelRenLblCol1.setBackground(colFonColBckGrn);
                    }
                    else{
                        objTblCelRenLblCol1.setBackground(colFonColBckWht);
                    }
                }
            });

            //objTblCelRenLbl=null;

            objTblCelRenLblCol2=new ZafTblCelRenLbl();
            objTblCelRenLblCol2.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCol2.setTipoFormato(objTblCelRenLblCol2.INT_FOR_NUM);
            objTblCelRenLblCol2.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_TOT_UNI_VEN).setCellRenderer(objTblCelRenLblCol2);
            tcmAux.getColumn(INT_TBL_DAT_VEN_TOT).setCellRenderer(objTblCelRenLblCol2);
            tcmAux.getColumn(INT_TBL_DAT_POR_VTA).setCellRenderer(objTblCelRenLblCol2);
            tcmAux.getColumn(INT_TBL_DAT_CAL_PAR).setCellRenderer(objTblCelRenLblCol2);
            objTblCelRenLblCol2.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColBckWht;
                java.awt.Color colFonColBckGrn;
                String strLin="";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColBckWht=new java.awt.Color(255,255,255);
                    colFonColBckGrn=new java.awt.Color(228,228,203);

                    strLin=objTblMod.getValueAt(objTblCelRenLblCol2.getRowRender(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(objTblCelRenLblCol2.getRowRender(), INT_TBL_DAT_LIN).toString();

                    if(strLin.equals("M")){
                        objTblCelRenLblCol2.setBackground(colFonColBckGrn);
                    }
                    else{
                        objTblCelRenLblCol2.setBackground(colFonColBckWht);
                    }
                }
            });

            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);


            chkVtaGrp.setVisible(false);
            chkVtaGrp.setEnabled(false);


            //Libero los objetos auxiliares.
            tcmAux=null;
            intNumColFinColEst=objTblMod.getColumnCount();
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null){
                stm=con.createStatement();
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1){
                    //Armar la sentencia SQL.
                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    }
                    else{
                        strSQL="";
                        strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                        strSQL+=" FROM tbm_emp AS a1 ";
                        strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                        strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS b2 ON (a2.co_emp=b2.co_empGrp AND a2.co_bod=b2.co_bodGrp)";
                        strSQL+=" INNER JOIN tbr_bodLoc AS b3 ON (b2.co_emp=b3.co_emp AND b2.co_bod=b3.co_bod)";
                        strSQL+=" INNER JOIN tbm_loc AS b4 ON(b3.co_emp=b4.co_emp AND b3.co_loc=b4.co_loc)";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo() + "";
                        //strSQL+=" AND b2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        //strSQL+=" AND b3.st_reg='P'";
                        strSQL+=" AND b4.st_reg NOT In('E','I')";
                        strSQL+=" GROUP BY a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom";
                        strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    }
                    System.out.println("cargarBod:" +strSQL);
                    rst=stm.executeQuery(strSQL);
                }
                else{
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                    strSQL+=" FROM tbm_emp AS a1";
                    strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                    strSQL+=" INNER JOIN (";
                    strSQL+="           SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod";
                    strSQL+="           FROM tbr_bodLocPrgUsr AS b1";
                    strSQL+="           INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)";
                    strSQL+="           WHERE b1.co_mnu=" + objParSis.getCodigoMenu();
                    strSQL+="           AND b1.co_usr=" + objParSis.getCodigoUsuario();
                    strSQL+="           AND b1.st_reg IN ('A','P')";
                    if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                        strSQL+="           AND b1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+="           AND b1.co_loc=" + objParSis.getCodigoLocal() + "";

                    }
                    strSQL+="           GROUP BY b2.co_empGrp, b2.co_bodGrp";
                    strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                    strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                    System.out.println("bodegas: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                while (rst.next())
                {
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_BOD_LIN,"");
                    vecReg.add(INT_TBL_BOD_CHK,new Boolean(true));
                    vecReg.add(INT_TBL_BOD_COD_EMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_BOD_NOM_EMP,rst.getString("tx_nom"));
                    vecReg.add(INT_TBL_BOD_COD_BOD,rst.getString("co_bod"));
                    vecReg.add(INT_TBL_BOD_NOM_BOD,rst.getString("a2_tx_nom"));
                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
                //Asignar vectores al modelo.
                objTblModBod.setData(vecDat);
                tblBod.setModel(objTblModBod);
                vecDat.clear();
                blnMarTodChkTblBod=false;
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
     * Esta función permite agregar columnas al "tblDat" de acuerdo al "tblEmp".
     * @return true: Si se pudo agregar las columnas al JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean agregarColTblDat()
    {
        int i, intNumFil;
        javax.swing.table.TableColumn tbc;
        String strCodBod="";
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*2);
        ZafTblHeaColGrp objTblHeaColGrpBod=null;
        //java.awt.Color colFonCol;
        
        boolean blnRes=true;
        intNumColIniColBod=intNumColFinColEst;
        try{
            intNumFil=objTblModBod.getRowCountTrue();

            objTblHeaColGrpBod=new ZafTblHeaColGrp("Stock");
            objTblHeaColGrpBod.setHeight(16);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpBod);



            objTblCelRenLblCol3=new ZafTblCelRenLbl();
            objTblCelRenLblCol3.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCol3.setTipoFormato(objTblCelRenLblCol3.INT_FOR_NUM);
            objTblCelRenLblCol3.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            
            objTblCelRenLblCol3.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColBckWht;
                java.awt.Color colFonColBckGrn;
                String strLin="";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColBckWht=new java.awt.Color(255,255,255);
                    colFonColBckGrn=new java.awt.Color(228,228,203);

                    strLin=objTblMod.getValueAt(objTblCelRenLblCol3.getRowRender(), INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(objTblCelRenLblCol3.getRowRender(), INT_TBL_DAT_LIN).toString();

                    if(strLin.equals("M")){
                        objTblCelRenLblCol3.setOpaque(true);
                        objTblCelRenLblCol3.setBackground(colFonColBckGrn);
                    }
                    else{
                        objTblCelRenLblCol3.setOpaque(true);
                        objTblCelRenLblCol3.setBackground(colFonColBckWht);
                    }
                }
            });

            for (i=0; i<intNumFil; i++){
                //Establecer el color de fondo de las columnas.
//                if ((i%2)==0)
//                    colFonCol=new java.awt.Color(255,221,187);
//                else
//                    colFonCol=new java.awt.Color(228,228,203);

                //Agrupar las columnas.
                if (!strCodBod.equals(objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString())){
                    strCodBod=objTblModBod.getValueAt(i, INT_TBL_BOD_COD_BOD).toString();
                    tbc=new javax.swing.table.TableColumn(intNumColIniColBod+i);
                    tbc.setHeaderValue(objTblModBod.getValueAt(i, INT_TBL_BOD_NOM_BOD).toString());
                    //Configurar JTable: Establecer el ancho de la columna.
                    tbc.setPreferredWidth(80);

                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenLblCol3);
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                }
                objTblHeaColGrpBod.add(tblDat.getColumnModel().getColumn(intNumColIniColBod+i));
                //Ocultar las columnas si la empresa no está seleccionada.
                if (!objTblModBod.isChecked(i, INT_TBL_BOD_CHK)){
                    //Ocultar columnas.
                    objTblMod.addSystemHiddenColumn(intNumColIniColBod+i, tblDat);
                }
            }

            intNumColFinColBod=objTblMod.getColumnCount();



        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intNumFilTblBod, j;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                switch (objSelFec.getTipoSeleccion()){
                    case 0: //Búsqueda por rangos
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
               

                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT f1.co_itmMae, f1.co_itm, f1.tx_codAlt, f1.tx_nomItm, f1.nd_totUniVen, f1.nd_venTot ";
                for (j=0; j<intNumFilTblBod; j++){
                    strSQL+=", b" + (j+2) + "_nd_stkAct";

                }
                strSQL+=" FROM(";
                strSQL+="SELECT b1.co_itmMae, b1.co_itm, b1.tx_codAlt, b1.tx_nomItm, ((c1.nd_totUniVen) *(-1)) AS nd_totUniVen, c1.nd_venTot";
                for (j=0; j<intNumFilTblBod; j++){
                    if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                        strSQL+=", b" + (j+2) + ".nd_stkAct AS b" + (j+2) + "_nd_stkAct";
                    }
                    else{
                        strSQL+=", Null AS b" + (j+2) + "_nd_stkAct";
                    }
                }
                strSQL+=" FROM (";
                strSQL+=" SELECT a2.co_itmMae, a1.co_emp, a1.co_itm, a1.tx_codAlt, a1.tx_nomItm, a3.tx_desCor ";
                strSQL+=" FROM tbm_inv AS a1";
                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                strSQL+=" LEFT OUTER JOIN tbm_var AS a3 ON (a1.co_uni=a3.co_reg)";

                if(txtCodEmp.getText().length()>0){//se toma la empresa del filtro en el formulario
                    strSQL+=" WHERE a1.co_emp=" + txtCodEmp.getText();
                }
                else{
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                }
               
                strSQL+=" AND a1.st_reg='A'";

                strSQL+=" ) AS b1";
                //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                if (objParSis.getCodigoUsuario()==1 || objParSis.getCodigoUsuario()==24){
                    for (j=0; j<intNumFilTblBod; j++){
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                            strSQL+=" LEFT OUTER JOIN (";
                            strSQL+="           SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct";
                            strSQL+="           FROM tbm_invBod AS a1";
                            strSQL+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+="           INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+="           WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+="           AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+="           GROUP BY a2.co_itmMae";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }
                    
                    strSQL+=" LEFT OUTER JOIN (";
                    strSQL+="             SELECT b1.co_itmMae, SUM(b1.nd_can) AS nd_totUniVen, -SUM(b1.nd_venTot) AS nd_venTot FROM(";
                    strSQL+="                      SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                    strSQL+="                      ,a2.co_itm, (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END ) AS nd_can, a4.co_itmMae, a2.co_bod, a5.co_bodGrp, CASE WHEN a2.nd_tot IS NULL THEN 0 ELSE a2.nd_tot END AS nd_venTot";
                    strSQL+="                      FROM (     (tbm_cabMovInv AS a1  INNER JOIN tbm_cli AS a7 ON a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli";
                    strSQL+="                                     )";
                    strSQL+="                             INNER JOIN tbm_detMovInv AS a2";
                    strSQL+="                            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                    strSQL+="                            AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="                            INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+="                            INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+="                            INNER JOIN tbr_bodEmpBodGrp AS a5 ON a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod)";
                    strSQL+="                          INNER JOIN tbr_tipDocPrg AS a6";
                    strSQL+="                          ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc";
                    strSQL+=strAux;
                    strSQL+="                     AND a1.st_reg NOT IN('E','I')";
                    strSQL+="                   AND a5.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";//0
                    strSQL+="                   AND a6.co_mnu=" + objParSis.getCodigoMenu() + "";
                    if(chkVtaCli.isSelected()){
                        strSQL+="                     AND a7.co_empgrp IS NULL";
                    }

                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(txtCodEmp.getText().length()>0)
                            strSQL+=" 			AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else
                            strSQL+=" 			AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    }
                    else{//si es por empresa
                        strSQL+=" 			AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    }

                    if(txtCodLoc.getText().length()>0){
                        strSQL+=" AND a1.co_loc=" + txtCodLoc.getText() + "";
                    }

                    strSQL+="                     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,a2.co_itm";
                    strSQL+="                     , a2.nd_can, a4.co_itmMae, a2.co_bod, a5.co_bodGrp,a2.nd_tot,a1.st_tipDev";
                    strSQL+="                     ORDER BY a1.ne_numDoc";
                    strSQL+="             ) AS b1";
                    strSQL+="             GROUP BY b1.co_itmMae";
                    strSQL+=" ) AS c1";
                    strSQL+=" ON b1.co_itmMae=c1.co_itmMae";


                }
                else{
//                    for (j=0; j<intNumFilTblBod; j++){
//                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
//                            strSQL+=" LEFT OUTER JOIN (";
//                            strSQL+="           SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct";
//                            strSQL+="           FROM tbm_invBod AS a1";
//                            strSQL+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
//                            strSQL+="           INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
//                            strSQL+="           INNER JOIN tbr_bodLocPrgUsr AS a4 ON (a3.co_emp=a4.co_emp AND a3.co_bod=a4.co_bod)";
//                            strSQL+="           WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
//                            strSQL+="           AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
//                            strSQL+="           AND a4.co_mnu=" + objParSis.getCodigoMenu();
//                            strSQL+="           AND a4.co_usr=" + objParSis.getCodigoUsuario();
//                            strSQL+="           AND a4.st_reg IN ('A','P') ";
//
//
//                            if(txtCodLoc.getText().length()>0)
//                                strSQL+="           AND a4.co_loc=" + txtCodLoc.getText() + "";
//                            else{
//                                if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
//                                    strSQL+="           AND a4.co_loc=" + objParSis.getCodigoLocal() + "";
//                                }
//                            }
//
//                            strSQL+="           GROUP BY a2.co_itmMae";
//                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
//                        }
//                    }


                    for (j=0; j<intNumFilTblBod; j++){
                        if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK)){
                            strSQL+=" LEFT OUTER JOIN (";
                            strSQL+="           SELECT a2.co_itmMae, SUM(a1.nd_stkAct) AS nd_stkAct";
                            strSQL+="           FROM tbm_invBod AS a1";
                            strSQL+="           INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+="           INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+="           WHERE a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP);
                            strSQL+="           AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD);
                            strSQL+="           GROUP BY a2.co_itmMae";
                            strSQL+=" ) AS b" + (j+2) + " ON (b1.co_itmMae=b" + (j+2) + ".co_itmMae)";
                        }
                    }





                    strSQL+=" LEFT OUTER JOIN (";
                    strSQL+="             SELECT b1.co_itmMae, SUM(b1.nd_can) AS nd_totUniVen, -SUM(b1.nd_venTot) AS nd_venTot FROM(";
                    strSQL+="                      SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc";
                    strSQL+="                      ,a2.co_itm, (CASE WHEN (a1.st_tipDev IS NULL OR a1.st_tipDev='C') THEN a2.nd_can ELSE 0 END ) AS nd_can, a4.co_itmMae, a2.co_bod, a5.co_bodGrp, CASE WHEN a2.nd_tot IS NULL THEN 0 ELSE a2.nd_tot END AS nd_venTot";

                    strSQL+="                      FROM (     ((tbm_cabMovInv AS a1 INNER JOIN tbr_locusr AS f1 ON a1.co_emp=f1.co_emp AND a1.co_loc=f1.co_loc) INNER JOIN tbm_cli AS a7 ON a1.co_emp=a7.co_emp AND a1.co_cli=a7.co_cli";

                    strSQL+="                                     )";
                    strSQL+="                             INNER JOIN tbm_detMovInv AS a2";
                    strSQL+="                            ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc";
                    strSQL+="                            AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                    strSQL+="                            INNER JOIN tbm_inv AS a3 ON a2.co_emp=a3.co_emp AND a2.co_itm=a3.co_itm";
                    strSQL+="                            INNER JOIN tbm_equInv AS a4 ON a3.co_emp=a4.co_emp AND a3.co_itm=a4.co_itm";
                    strSQL+="                            INNER JOIN tbr_bodEmpBodGrp AS a5 ON a2.co_emp=a5.co_emp AND a2.co_bod=a5.co_bod)";
                    strSQL+="                          INNER JOIN tbr_tipDocUsr AS a6";
                    strSQL+="                          ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc";
                    strSQL+=strAux;
                    strSQL+="                     AND a1.st_reg NOT IN('E','I')";
                    strSQL+="                   AND a5.co_empGrp=" + objParSis.getCodigoEmpresaGrupo() + "";//0
                    strSQL+="                   AND a6.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+="                   AND a6.co_usr=" + objParSis.getCodigoUsuario() + "";
                    if(chkVtaCli.isSelected()){
                        strSQL+="                     AND a7.co_empgrp IS NULL";
                    }

                    if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                        if(txtCodEmp.getText().length()>0)
                            strSQL+=" 			AND a1.co_emp=" + txtCodEmp.getText() + "";
                        else
                            strSQL+=" 			AND a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                    }
                    else{//si es por empresa
                        strSQL+=" 			AND a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    }

                    if(txtCodLoc.getText().length()>0){
                        strSQL+=" AND a1.co_loc=" + txtCodLoc.getText() + "";
                    }
                    else{
                        if(objParSis.getCodigoEmpresa()!=objParSis.getCodigoEmpresaGrupo()){
                            strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        }
                    }
                    strSQL+="                     GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc,a2.co_itm";
                    strSQL+="                     , a2.nd_can, a4.co_itmMae, a2.co_bod, a5.co_bodGrp,a2.nd_tot, a1.st_tipDev";
                    strSQL+="                     ORDER BY a1.ne_numDoc";
                    strSQL+="             ) AS b1";
                    strSQL+="             GROUP BY b1.co_itmMae";
                    strSQL+=" ) AS c1";
                    strSQL+=" ON b1.co_itmMae=c1.co_itmMae";
                }
                strSQL+=" ORDER BY b1.tx_codAlt";

                strSQL+=" ) AS f1";
                strSQL+=" ORDER BY CASE WHEN f1.nd_venTot IS NULL THEN 0 ELSE f1.nd_venTot END DESC";//, f1.tx_codAlt

                System.out.println("cargarDetalle: " + strSQL);
                rst=stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                bdeSumTotVen=new BigDecimal(BigInteger.ZERO);

                BigDecimal bdeVenTotRst=new BigDecimal(BigInteger.ZERO);

                while(rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_MAE,rst.getString("co_itmMae"));
                        vecReg.add(INT_TBL_DAT_COD_SIS,rst.getString("co_itm"));
                        vecReg.add(INT_TBL_DAT_COD_ALT,rst.getString("tx_codAlt"));
                        vecReg.add(INT_TBL_DAT_NOM_ITM,rst.getString("tx_nomItm"));
                        vecReg.add(INT_TBL_DAT_TOT_UNI_VEN,rst.getString("nd_totUniVen"));
                        vecReg.add(INT_TBL_DAT_VEN_TOT,rst.getString("nd_venTot"));
                        vecReg.add(INT_TBL_DAT_POR_VTA,"0");
                        vecReg.add(INT_TBL_DAT_CAL_PAR,"0");


                        bdeVenTotRst=new BigDecimal((rst.getObject("nd_venTot")==null?"0":(rst.getString("nd_venTot").equals("")?"0":rst.getString("nd_venTot"))));


                        bdeSumTotVen=bdeSumTotVen.add(bdeVenTotRst);//contiene la sumatoria
                        for (j=0; j<intNumFilTblBod; j++){
                            vecReg.add((intNumColIniColBod+j),rst.getString("b" + (j+2) + "_nd_stkAct"));
                        }
                        vecDat.add(vecReg);
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

                objTblMod.initRowsState();

                //calculo del porcentaje de venta
                BigDecimal bdeVenTotItm=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdePorVtaItm=new BigDecimal(BigInteger.ZERO);
                BigDecimal bdePorParItm=new BigDecimal(BigInteger.ZERO);
                
                for(int k=0; k<objTblMod.getRowCountTrue(); k++){
                    bdeVenTotItm=new BigDecimal(objTblMod.getValueAt(k, INT_TBL_DAT_VEN_TOT)==null?"0":(objTblMod.getValueAt(k, INT_TBL_DAT_VEN_TOT).toString().equals("")?"0":objTblMod.getValueAt(k, INT_TBL_DAT_VEN_TOT).toString()));
                    bdePorVtaItm=(bdeVenTotItm.divide(bdeSumTotVen, objParSis.getDecimalesBaseDatos(), BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal("100"));

                    objTblMod.setValueAt(bdePorVtaItm, k, INT_TBL_DAT_POR_VTA);
                    //calculo de pareto
                    bdePorParItm=bdePorParItm.add(bdePorVtaItm);
                    //System.out.println("bdePorParItm: " + bdePorParItm);
                    objTblMod.setValueAt(bdePorParItm, k, INT_TBL_DAT_CAL_PAR);
                    if(bdePorParItm.compareTo(new BigDecimal("80"))>0){
                        break;
                    }
                    //mostrarMsgInf("bla");
                }




                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);


                borrarContenidoColumnasCalculo();


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
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
    }

   
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt){
        int i, intNumFil;
        try{
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK){
                if (blnMarTodChkTblBod){
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                        objTblMod.removeSystemHiddenColumn(intNumColIniColBod+i, tblDat);
                    }
                    blnMarTodChkTblBod=false;
                }
                else{
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++){
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                        objTblMod.addSystemHiddenColumn(intNumColIniColBod+i, tblDat);
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
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
    private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            if (!cargarDetReg())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable sólo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
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
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            if (intCol>intNumColIniColBod)
                intCol=(intCol-intNumColIniColBod)%intNumColFinColBod+intNumColIniColBod;
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_COD_MAE:
                    strMsg="Código maestro del item";
                    break;
                case INT_TBL_DAT_COD_SIS:
                    strMsg="Código del item (Sistema)";
                    break;
                case INT_TBL_DAT_COD_ALT:
                    strMsg="Código alterno del item";
                    break;
                case INT_TBL_DAT_NOM_ITM:
                    strMsg="Nombre del item";
                    break;
                case INT_TBL_DAT_TOT_UNI_VEN:
                    strMsg="Total de unidades vendidas";
                    break;
                case INT_TBL_DAT_VEN_TOT:
                    strMsg="Venta total";
                    break;
                case INT_TBL_DAT_POR_VTA:
                    strMsg="Porcentaje de venta";
                    break;
                case INT_TBL_DAT_CAL_PAR:
                    strMsg="Cálculo de Pareto";
                    break;

                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAdaBod extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblBod.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_BOD_LIN:
                    strMsg="";
                    break;
                case INT_TBL_BOD_COD_EMP:
                    strMsg="Código de la empresa";
                    break;
                case INT_TBL_BOD_NOM_EMP:
                    strMsg="Nombre de la empresa";
                    break;
                case INT_TBL_BOD_COD_BOD:
                    strMsg="Código de la bodega";
                    break;
                case INT_TBL_BOD_NOM_BOD:
                    strMsg="Nombre de la bodega";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblBod.getTableHeader().setToolTipText(strMsg);
        }
    }


    private boolean configurarVenConEmp(){
        boolean blnRes=true;
        String strTitVenCon="";
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
            //Armar la sentencia SQL.

            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1 INNER JOIN tbr_usremp AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            vcoEmp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoEmp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e){
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
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
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
                        txtCodLoc.setText("");
                        txtNomLoc.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodLoc.setText("");
                            txtNomLoc.setText("");
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




    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Locales".
     */
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
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
     private boolean mostrarVenConLoc(int intTipBus){
        boolean blnRes=true;
        String strConAdd="";
        try{
            if(txtCodEmp.getText().length()>0){
                strConAdd+=" AND a1.co_emp=" + txtCodEmp.getText() + "";
            }
            strConAdd+=" GROUP BY a1.co_loc, a1.tx_nom";
            strConAdd+=" ORDER BY a1.tx_nom";
            vcoLoc.setCondicionesSQL(strConAdd);

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
                        if (vcoLoc.getSelectedButton()==vcoLoc.INT_BUT_ACE){
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



        
    private boolean borrarContenidoColumnasCalculo(){
        boolean blnRes=true;
        String strLin="";
        Object objNul=null;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){

                strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();

                if(strLin.equals("M")){
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_VEN_TOT);
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_POR_VTA);
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_CAL_PAR);
                }
                else{
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_VEN_TOT);
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_POR_VTA);
                    objTblMod.setValueAt(objNul, i, INT_TBL_DAT_CAL_PAR);
                    objTblMod.setValueAt("P", i, INT_TBL_DAT_LIN);
                }
            }

            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_VEN_TOT, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_POR_VTA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CAL_PAR, tblDat);

        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }




}