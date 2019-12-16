/*
 * ZafCom05.java
 *
 * Created on 19 de febrero de 2006, 19:14 PM
 */
package Compras.ZafCom05;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 *
 * @author  Eddye Lino
 */
public class ZafCom05 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_BOD_LIN=0;                         //Línea.
    static final int INT_TBL_BOD_CHK=1;                         //Casilla de verificación.
    static final int INT_TBL_BOD_COD_EMP=2;                     //Código de la empresa.
    static final int INT_TBL_BOD_NOM_EMP=3;                     //Nombre de la empresa.
    static final int INT_TBL_BOD_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_BOD_NOM_BOD=5;                     //Nombre de la bodega.
    
    static final int INT_TBL_DAT_LIN=0;                         //Línea
    static final int INT_TBL_DAT_CODSEG=1;                      //Código de seguimiento.
    static final int INT_TBL_DAT_COD_LOC=2;                     //Código del local.
    static final int INT_TBL_DAT_NOM_LOC=3;                     //Nombre del local.
    static final int INT_TBL_DAT_COD_BOD=4;                     //Código de la bodega.
    static final int INT_TBL_DAT_NOM_BOD=5;                     //Nombre de la bodega.
    static final int INT_TBL_DAT_COD_TIP_DOC=6;                 //Código del tipo de documento.
    static final int INT_TBL_DAT_DEC_TIP_DOC=7;                 //Descripción corta del tipo de documento.
    static final int INT_TBL_DAT_DEL_TIP_DOC=8;                 //Descripción larga del tipo de documento.
    static final int INT_TBL_DAT_COD_DOC=9;                     //Código del documento.
    static final int INT_TBL_DAT_NUM_DOC=10;                    //Número del documento.
    static final int INT_TBL_DAT_NUM_GUI_REM=11;                //Número de guia de remision.
    static final int INT_TBL_DAT_NUM_ORD_DES=12;                //Número de orden de despacho
    static final int INT_TBL_DAT_FEC_DOC=13;                    //Fecha del documento.
    static final int INT_TBL_DAT_CAN_ING=14;                    //Cantidad ingresada.
    static final int INT_TBL_DAT_CAN_EGR=15;                    //Cantidad egresada.
    static final int INT_TBL_DAT_COS_UNI=16;                    //Costo unitario.
    static final int INT_TBL_DAT_PRE_VTA=17;                    //Precio unitario de venta.
    static final int INT_TBL_DAT_COS_TOT=18;                    //Costo total.
    static final int INT_TBL_DAT_SAL_UNI=19;                    //Saldo en unidades.
    static final int INT_TBL_DAT_SAL_VAL=20;                    //Saldo en valores.
    static final int INT_TBL_DAT_COS_PRO=21;                    //Costo promedio.
    static final int INT_TBL_DAT_COD_CLI=22;                    //Código del cliente.
    static final int INT_TBL_DAT_NOM_CLI=23;                    //Nombre del cliente.
    static final int INT_TBL_DAT_EST_REG=24;                    //Estado del registro.
    
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblMod objTblModBod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                        //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    private ZafTblTot objTblTot;                        //JTable de totales.
    private ZafVenCon vcoItm;                           //Ventana de consulta "Item".
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecución del hilo.
    private String strCodAlt, strCodLetItm, strNomItm;  //Contenido del campo al obtener el foco.
    private boolean blnMarTodChkTblBod=true;            //Marcar todas las casillas de verificación del JTable de bodegas.
    
    /**
     * Crea una nueva instancia de la clase ZafCom05.
     * @param obj El objeto ZafParSis.
     */
    public ZafCom05(ZafParSis obj) 
    {
        try
        {
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            configurarFrm();
        }
        catch (CloneNotSupportedException e)
        {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    public ZafCom05(ZafParSis obj, String strCodItm, String strCodAlt, String strNomItm)
    {
        this(obj);
        txtCodItm.setText(strCodItm);
        txtCodAlt.setText(strCodAlt);
        //txtCodLetItm.setText(strCodLetItm);
        txtNomItm.setText(strNomItm);
        butConActionPerformed(null);
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
        lblItm = new javax.swing.JLabel();
        txtCodItm = new javax.swing.JTextField();
        txtCodAlt = new javax.swing.JTextField();
        txtNomItm = new javax.swing.JTextField();
        butItm = new javax.swing.JButton();
        panBod = new javax.swing.JPanel();
        spnBod = new javax.swing.JScrollPane();
        tblBod = new javax.swing.JTable();
        chkMosAjuItm = new javax.swing.JCheckBox();
        chkMosMovEmpGrp = new javax.swing.JCheckBox();
        txtCodLetItm = new javax.swing.JTextField();
        panRpt = new javax.swing.JPanel();
        panRptReg = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        panRptCanIngEgr = new javax.swing.JPanel();
        panRptCanIng = new javax.swing.JPanel();
        lblCanIng = new javax.swing.JLabel();
        txtCanIng = new javax.swing.JTextField();
        panRptCanEgr = new javax.swing.JPanel();
        lblCanEgr = new javax.swing.JLabel();
        txtCanEgr = new javax.swing.JTextField();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panFil.setLayout(null);

        lblItm.setText("Item:");
        lblItm.setToolTipText("Beneficiario");
        panFil.add(lblItm);
        lblItm.setBounds(4, 4, 70, 20);
        panFil.add(txtCodItm);
        txtCodItm.setBounds(30, 4, 56, 20);

        txtCodAlt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodAltActionPerformed(evt);
            }
        });
        txtCodAlt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodAltFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodAltFocusLost(evt);
            }
        });
        panFil.add(txtCodAlt);
        txtCodAlt.setBounds(86, 4, 90, 20);

        txtNomItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomItmActionPerformed(evt);
            }
        });
        txtNomItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomItmFocusLost(evt);
            }
        });
        panFil.add(txtNomItm);
        txtNomItm.setBounds(239, 4, 405, 20);

        butItm.setText("...");
        butItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butItmActionPerformed(evt);
            }
        });
        panFil.add(butItm);
        butItm.setBounds(644, 4, 20, 20);

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

        panFil.add(panBod);
        panBod.setBounds(4, 24, 660, 92);

        chkMosAjuItm.setText("Mostrar los movimientos correspondientes al tipo de documento 76=AJUITM");
        panFil.add(chkMosAjuItm);
        chkMosAjuItm.setBounds(4, 116, 500, 20);

        chkMosMovEmpGrp.setText("Mostrar los movimientos entre las empresas del grupo");
        panFil.add(chkMosMovEmpGrp);
        chkMosMovEmpGrp.setBounds(4, 136, 500, 20);

        txtCodLetItm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodLetItmActionPerformed(evt);
            }
        });
        txtCodLetItm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodLetItmFocusLost(evt);
            }
        });
        panFil.add(txtCodLetItm);
        txtCodLetItm.setBounds(176, 4, 63, 20);

        tabFrm.addTab("Filtro", panFil);

        panRpt.setLayout(new java.awt.BorderLayout());

        panRptReg.setLayout(new java.awt.BorderLayout(0, 1));

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

        panRptReg.add(spnDat, java.awt.BorderLayout.CENTER);

        spnTot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTot.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        spnTot.setViewportView(tblTot);

        panRptReg.add(spnTot, java.awt.BorderLayout.SOUTH);

        panRpt.add(panRptReg, java.awt.BorderLayout.CENTER);

        panRptCanIngEgr.setPreferredSize(new java.awt.Dimension(10, 40));
        panRptCanIngEgr.setLayout(new java.awt.GridLayout(2, 1));

        panRptCanIng.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        lblCanIng.setText("Cantidad por ingresar:");
        lblCanIng.setPreferredSize(new java.awt.Dimension(140, 20));
        panRptCanIng.add(lblCanIng);

        txtCanIng.setEditable(false);
        txtCanIng.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanIng.setPreferredSize(new java.awt.Dimension(100, 20));
        panRptCanIng.add(txtCanIng);

        panRptCanIngEgr.add(panRptCanIng);

        panRptCanEgr.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));

        lblCanEgr.setText("Cantidad por egresar:");
        lblCanEgr.setPreferredSize(new java.awt.Dimension(140, 20));
        panRptCanEgr.add(lblCanEgr);

        txtCanEgr.setEditable(false);
        txtCanEgr.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCanEgr.setPreferredSize(new java.awt.Dimension(100, 20));
        panRptCanEgr.add(txtCanEgr);

        panRptCanIngEgr.add(panRptCanEgr);

        panRpt.add(panRptCanIngEgr, java.awt.BorderLayout.SOUTH);

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
        setBounds((screenSize.width-700)/2, (screenSize.height-550)/2, 700, 550);
    }// </editor-fold>//GEN-END:initComponents

    private void butItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butItmActionPerformed
        mostrarVenConItm(0);
    }//GEN-LAST:event_butItmActionPerformed

    private void txtNomItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomItm.getText().equalsIgnoreCase(strNomItm))
        {
            if (txtNomItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(3);
            }
        }
        else
            txtNomItm.setText(strNomItm);
    }//GEN-LAST:event_txtNomItmFocusLost

    private void txtNomItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomItmFocusGained
        strNomItm=txtNomItm.getText();
        txtNomItm.selectAll();
    }//GEN-LAST:event_txtNomItmFocusGained

    private void txtNomItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomItmActionPerformed
        txtNomItm.transferFocus();
    }//GEN-LAST:event_txtNomItmActionPerformed

    private void txtCodAltFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodAlt.getText().equalsIgnoreCase(strCodAlt))
        {
            if (txtCodAlt.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(1);
            }
        }
        else
            txtCodAlt.setText(strCodAlt);
    }//GEN-LAST:event_txtCodAltFocusLost

    private void txtCodAltFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodAltFocusGained
        strCodAlt=txtCodAlt.getText();
        txtCodAlt.selectAll();
    }//GEN-LAST:event_txtCodAltFocusGained

    private void txtCodAltActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodAltActionPerformed
        txtCodAlt.transferFocus();
    }//GEN-LAST:event_txtCodAltActionPerformed

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        if (isCamVal())
        {
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
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void txtCodLetItmFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusGained
        strCodLetItm=txtCodLetItm.getText();
        txtCodLetItm.selectAll();
    }//GEN-LAST:event_txtCodLetItmFocusGained

    private void txtCodLetItmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodLetItmFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodLetItm.getText().equalsIgnoreCase(strCodLetItm))
        {
            if (txtCodLetItm.getText().equals(""))
            {
                txtCodItm.setText("");
                txtCodAlt.setText("");
                txtCodLetItm.setText("");
                txtNomItm.setText("");
            }
            else
            {
                mostrarVenConItm(2);
            }
        }
        else
            txtCodLetItm.setText(strCodLetItm);
    }//GEN-LAST:event_txtCodLetItmFocusLost

    private void txtCodLetItmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodLetItmActionPerformed
        txtCodLetItm.transferFocus();
    }//GEN-LAST:event_txtCodLetItmActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butItm;
    private javax.swing.JCheckBox chkMosAjuItm;
    private javax.swing.JCheckBox chkMosMovEmpGrp;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCanEgr;
    private javax.swing.JLabel lblCanIng;
    private javax.swing.JLabel lblItm;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBod;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JPanel panRptCanEgr;
    private javax.swing.JPanel panRptCanIng;
    private javax.swing.JPanel panRptCanIngEgr;
    private javax.swing.JPanel panRptReg;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnBod;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblBod;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCanEgr;
    private javax.swing.JTextField txtCanIng;
    private javax.swing.JTextField txtCodAlt;
    private javax.swing.JTextField txtCodItm;
    private javax.swing.JTextField txtCodLetItm;
    private javax.swing.JTextField txtNomItm;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.26");
            lblTit.setText(strAux);
            //Configurar objetos.
            txtCodItm.setVisible(false);
            txtCodAlt.setBackground(objParSis.getColorCamposObligatorios());
            txtCodLetItm.setBackground(objParSis.getColorCamposObligatorios());
            txtNomItm.setBackground(objParSis.getColorCamposObligatorios());
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                switch (objParSis.getCodigoMenu())
                {
                    case 237: //Kardex de inventario...
                        panBod.setVisible(false);
                        chkMosAjuItm.setBounds(4, 24, 500, 20);
                        chkMosMovEmpGrp.setBounds(4, 44, 500, 20);
                        break;
                    case 886: //Kardex de inventario (Unidades)...
                    case 907: //Kardex físico de inventario (Unidades)...
                    default:
                        chkMosAjuItm.setVisible(false);
                        chkMosMovEmpGrp.setBounds(4, 116, 500, 20);
                        break;
                }
            }
            else
            {
                switch (objParSis.getCodigoMenu())
                {
                    case 237: //Kardex de inventario...
                        panBod.setVisible(false);
                        break;
                    case 886: //Kardex de inventario (Unidades)...
                    case 907: //Kardex físico de inventario (Unidades)...
                    default:
                        break;
                }
                chkMosMovEmpGrp.setVisible(false);
                chkMosAjuItm.setVisible(false);
            }
            txtCanIng.setBackground(objParSis.getColorCamposSistema());
            txtCanEgr.setBackground(objParSis.getColorCamposSistema());
            //Configurar las ZafVenCon.
            configurarVenConItm();
            //Configurar los JTables.
            configurarTblBod();
            cargarBod();
            configurarTblDat();
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
            switch (objParSis.getCodigoMenu())
            {
                case 237: //Kardex de inventario...
                case 886: //Kardex de inventario (Unidades)...
                    break;
                case 907: //Kardex físico de inventario (Unidades)...
                    objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_COD_EMP, tblBod);
                    objTblModBod.addSystemHiddenColumn(INT_TBL_BOD_NOM_EMP, tblBod);
                    break;
            }
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
            objTblCelEdiChk=null;
            
            //Configurar JTable: Establecer el ListSelectionListener.
//            javax.swing.ListSelectionModel lsm=tblBod.getSelectionModel();
//            lsm.addListSelectionListener(new ZafLisSelLisCre());
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
            vecCab=new Vector(25);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CODSEG,"Cód.Seg.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_NOM_LOC,"Local");
            vecCab.add(INT_TBL_DAT_COD_BOD,"Cód.Bod.");
            vecCab.add(INT_TBL_DAT_NOM_BOD,"Bodega");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEC_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DEL_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_GUI_REM,"Núm.Gui.Rem.");
            vecCab.add(INT_TBL_DAT_NUM_ORD_DES,"Núm.Ord.Des.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CAN_ING,"Ingreso");
            vecCab.add(INT_TBL_DAT_CAN_EGR,"Egreso");
            vecCab.add(INT_TBL_DAT_COS_UNI,"Cos.Uni.");
            vecCab.add(INT_TBL_DAT_PRE_VTA,"Pre.Vta.");
            vecCab.add(INT_TBL_DAT_COS_TOT,"Cos.Tot.");
            vecCab.add(INT_TBL_DAT_SAL_UNI,"Sal.Uni.");
            vecCab.add(INT_TBL_DAT_SAL_VAL,"Sal.Val.");
            vecCab.add(INT_TBL_DAT_COS_PRO,"Cos.Pro.");
            vecCab.add(INT_TBL_DAT_COD_CLI,"Cód.Cli.");
            vecCab.add(INT_TBL_DAT_NOM_CLI,"Cliente/Proveedor");
            vecCab.add(INT_TBL_DAT_EST_REG,"Est.Reg.");
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODSEG).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_BOD).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_BOD).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DEC_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DEL_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_GUI_REM).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_ORD_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SAL_UNI).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_SAL_VAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COS_PRO).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_CLI).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(139);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG).setPreferredWidth(55);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_BOD, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_NUM_GUI_REM, tblDat);
            switch (objParSis.getCodigoMenu())
            {
                case 237: //Kardex de inventario...
                    break;
                case 886: //Kardex de inventario (Unidades)...
                case 907: //Kardex físico de inventario (Unidades)...
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_UNI, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_PRE_VTA, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_TOT, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_SAL_VAL, tblDat);
                    objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COS_PRO, tblDat);
                    tcmAux.getColumn(INT_TBL_DAT_NOM_CLI).setPreferredWidth(105);
                    break;
            }
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
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_CAN_ING).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_CAN_EGR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_PRE_VTA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_TOT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_UNI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_SAL_VAL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_COS_PRO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_CAN_ING, INT_TBL_DAT_CAN_EGR, INT_TBL_DAT_SAL_UNI};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
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
     * Esta función permite consultar las bodegas de acuerdo al siguiente criterio:
     * El listado de bodegas se presenta en función de la empresa a la que se ingresa (Empresa Grupo u otra empresa)
     * , el usuario que ingresa (Administrador u otro usuario) y el menú desde el cual es llamado  (237, 886 o 907).
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarBod()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        switch (objParSis.getCodigoMenu())
                        {
                            case 237: //Kardex de inventario...
                            case 886: //Kardex de inventario (Unidades)...
                            case 907: //Kardex físico de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                        }
                    }
                    else
                    {
                        switch (objParSis.getCodigoMenu())
                        {
                            case 237: //Kardex de inventario...
                            case 886: //Kardex de inventario (Unidades)...
                            case 907: //Kardex físico de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                                strSQL+=" AND a3.st_reg IN ('A','P')";
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                        }
                    }
                }
                else
                {
                    //Si es el usuario Administrador (Código=1) tiene acceso a todas las bodegas.
                    if (objParSis.getCodigoUsuario()==1)
                    {
                        switch (objParSis.getCodigoMenu())
                        {
                            case 237: //Kardex de inventario...
                            case 886: //Kardex de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                            case 907: //Kardex físico de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresaGrupo();
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                        }
                    }
                    else
                    {
                        switch (objParSis.getCodigoMenu())
                        {
                            case 237: //Kardex de inventario...
                            case 886: //Kardex de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" INNER JOIN tbr_bodLocPrgUsr AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                                strSQL+=" WHERE a3.co_emp=" + objParSis.getCodigoEmpresa();
                                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal();
                                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario();
                                strSQL+=" AND a3.st_reg IN ('A','P')";
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                            case 907: //Kardex físico de inventario (Unidades)...
                                //Armar la sentencia SQL.
                                strSQL="";
                                strSQL+="SELECT a1.co_emp, a1.tx_nom, a2.co_bod, a2.tx_nom AS a2_tx_nom";
                                strSQL+=" FROM tbm_emp AS a1";
                                strSQL+=" INNER JOIN tbm_bod AS a2 ON (a1.co_emp=a2.co_emp)";
                                strSQL+=" INNER JOIN (";
                                strSQL+=" SELECT b2.co_empGrp AS co_emp, b2.co_bodGrp AS co_bod";
                                strSQL+=" FROM tbr_bodLocPrgUsr AS b1";
                                strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod)";
                                strSQL+=" WHERE b1.co_emp=" + objParSis.getCodigoEmpresa();
                                strSQL+=" AND b1.co_loc=" + objParSis.getCodigoLocal();
                                strSQL+=" AND b1.co_mnu=" + objParSis.getCodigoMenu();
                                strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario();
                                strSQL+=" AND b1.st_reg IN ('A','P')";
                                strSQL+=" GROUP BY b2.co_empGrp, b2.co_bodGrp";
                                strSQL+=" ) AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_bod=a3.co_bod)";
                                strSQL+=" ORDER BY a1.co_emp, a2.co_bod";
                                rst=stm.executeQuery(strSQL);
                                break;
                        }
                    }
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        int intCodEmp, intNumFilTblBod, i, j;
        BigDecimal bgdCan, bgdSalUni;
        String strEstCosUniCal, strEstReg, strEstRegItm;
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            intCodEmp=objParSis.getCodigoEmpresa();
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strAux="";
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    switch (objParSis.getCodigoMenu())
                    {
                        case 237: //Kardex de inventario...
                        case 886: //Kardex de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT y.co_Seg, (CASE WHEN x.st_regItm='I' THEN 'I' ELSE x.st_regMov END ) AS st_reg, * FROM (";
                            strSQL+="   SELECT a1.st_reg AS st_regMov, (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) AS st_regItm, a2.co_Emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar";
                            strSQL+="        , a2.co_doc, a1.ne_numDoc, a1.ne_numGui, a1.ne_numOrdDes,  a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a2.nd_cosUniGrp AS nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni, a2.nd_pordes";
                            strSQL+="        , a2.nd_cosTotGrp AS nd_cosTot, a2.nd_exiGrp AS nd_exi, a2.nd_valExiGrp AS nd_valExi, a2.nd_cosProGrp AS nd_cosPro";
                            strSQL+="        , a1.co_cli, a1.tx_nomCli, a1.ne_secGrp, a2.co_Reg";
                            strSQL+="   FROM tbm_cabMovInv AS a1";
                            strSQL+="   INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                            strSQL+="   INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)";
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+="   INNER JOIN ( ";
                            strSQL+="      SELECT co_emp, co_bod, tx_nom FROM tbm_bod WHERE (" + strAux + ")";
                            strSQL+="      UNION ALL";
                            strSQL+="      SELECT b1.co_emp, b1.co_bod, b1.tx_nom FROM tbm_bod AS b1 INNER JOIN tbr_bodEmpBodGrp AS b2 ON (b1.co_emp=b2.co_emp AND b1.co_bod=b2.co_bod) ";
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (b2.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND b2.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (b2.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND b2.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+="      WHERE"+strAux;
                            strSQL+="   ) AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)";
                            strSQL+="   INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                            strSQL+="   INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itmAct=a6.co_itm)";
                            strSQL+="   WHERE a6.co_itmMae=( SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp+" AND co_itm=" + txtCodItm.getText()+"  )";
                            strSQL+="   AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                            strSQL+="   AND ( (a1.fe_doc<'2009/05/01'"; //Periodo antes del 2009/05/01 (Excluir: compras y ventas entre empresas, transferencias de empresas).
                            strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=1 and b.co_EmpDes!=1)  AND a1.co_cli IS NOT NULL)";
                            strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=2 and b.co_EmpDes!=2)  AND a1.co_cli IS NOT NULL)";
                            strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=4 and b.co_EmpDes!=4)  AND a1.co_cli IS NOT NULL)";

                            //Excluir las transferencias de cada empresa.
                            strSQL+="   AND NOT (a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + " AND a1.co_tipDoc=46)";
                            //strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                            if (chkMosMovEmpGrp.isSelected())
                            {
                                strSQL+=" ) OR (a1.fe_doc>='2009/05/01') )";  //Periodo después del 2009/05/01 (Considerar todo).
                            }
                            else
                            {
                                strSQL+=" ) OR (a1.fe_doc>='2009/05/01'";  //Periodo después del 2009/05/01 (Considerar todo).
                                strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=1 and b.co_EmpDes!=1)  AND a1.co_cli IS NOT NULL)";
                                strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=2 and b.co_EmpDes!=2)  AND a1.co_cli IS NOT NULL)";
                                strSQL+="   AND NOT ( a1.co_cli in (select co_cliEmpOrg from tbm_cfgEmpRel as b where b.st_reg='A' AND b.co_empOrg=4 and b.co_EmpDes!=4)  AND a1.co_cli IS NOT NULL)";
                                strSQL+="   ) )";
                            }
                            //Excluir los "INBOVA: Ingreso a bodega en valores" y "EGBOVA: Egreso de bodega en valores".
                            strSQL+="   AND a1.co_tipDoc NOT IN (140, 141)";
                            //Excluir los tipos de documentos 76=AJUITM.
                            if (!chkMosAjuItm.isSelected())
                            {
                                strSQL+="   AND NOT a1.co_tipDoc=76";
                            }
                            strSQL+="   ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                            strSQL+=" ) as x ";
                            strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as y ";
                            strSQL+=" ON (y.co_empRelCabMovInv=x.co_emp AND y.co_locRelCabMovInv=x.co_loc AND y.co_tipDocRelCabMovInv=x.co_tipDoc AND y.co_DocRelCabMovInv=x.co_Doc )";
                            strSQL+=" ORDER BY x.fe_doc, x.ne_secGrp, x.co_reg";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 907: //Kardex físico de inventario (Unidades)...
                            lblMsgSis.setText("Listo");
                            stm.close();
                            con.close();
                            stm=null;
                            con=null;
                            butCon.setText("Consultar");
                            pgrSis.setIndeterminate(false);
                            mostrarMsgInf("Esta opción todavía no está desarrollada...");
                            return true;
//                            break;
                    }
                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    switch (objParSis.getCodigoMenu())
                    {
                        case 237: //Kardex de inventario...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT y.co_Seg, (CASE WHEN x.st_regItm='I' THEN 'I' ELSE x.st_regMov END ) AS st_reg, * FROM (";
                            strSQL+="    SELECT a1.st_reg AS st_regMov, (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) AS st_RegItm, a2.co_emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar";
                            strSQL+="         , a2.co_doc, a1.ne_numDoc, a1.ne_numGui, a1.ne_numOrdDes, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a2.nd_cosUni, a2.nd_preUni*(1-a2.nd_pordes/100) AS nd_preUni, a2.nd_pordes";
                            strSQL+="         , a2.nd_cosTot, a2.nd_exi, a2.nd_valExi, a2.nd_cosPro, a1.co_cli, a1.tx_nomCli, a1.ne_secEmp, a2.co_reg";
                            strSQL+="    FROM tbm_cabMovInv AS a1";
                            strSQL+="    INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                            strSQL+="    INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)";
                            strSQL+="    INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)";
                            strSQL+="    INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                            strSQL+="    WHERE a1.co_emp=" + intCodEmp;
                            strSQL+="    AND a2.co_itm=" + txtCodItm.getText();
                            strSQL+="    AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                            strSQL+="    ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg";
                            strSQL+=" ) as x ";
                            strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as y ";
                            strSQL+=" ON (y.co_empRelCabMovInv=x.co_emp AND y.co_locRelCabMovInv=x.co_loc AND y.co_tipDocRelCabMovInv=x.co_tipDoc AND y.co_DocRelCabMovInv=x.co_Doc )";
                            strSQL+=" ORDER BY x.fe_doc, x.ne_secEmp, x.co_reg";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 886: //Kardex de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT y.co_Seg, (CASE WHEN x.st_regItm='I' THEN 'I' ELSE x.st_regMov END ) AS st_reg, * FROM (";
                            strSQL+="   SELECT a1.st_reg AS st_regMov, (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) AS st_RegItm, a2.co_emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar";
                            strSQL+="         , a2.co_doc, a1.ne_numDoc, a1.ne_numGui, a1.ne_numOrdDes, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a1.co_cli, a1.tx_nomCli, a1.ne_secEmp, a2.co_reg";
                            strSQL+="   FROM tbm_cabMovInv AS a1";
                            strSQL+="   INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                            strSQL+="   INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)";
                            strSQL+="   INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)";
                            strSQL+="   INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                            strSQL+="   WHERE a1.co_emp=" + intCodEmp;
                            strSQL+="   AND a2.co_itm=" + txtCodItm.getText();
                            strSQL+="   AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";                            
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (a2.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a2.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (a2.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a2.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+="   AND (" + strAux + ")";
                            strSQL+="   ORDER BY a1.fe_doc, a1.ne_secEmp, a2.co_reg";
                            strSQL+=" ) as x ";
                            strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as y ";
                            strSQL+=" ON (y.co_empRelCabMovInv=x.co_emp AND y.co_locRelCabMovInv=x.co_loc AND y.co_tipDocRelCabMovInv=x.co_tipDoc AND y.co_DocRelCabMovInv=x.co_Doc )";
                            strSQL+=" ORDER BY x.fe_doc, x.ne_secEmp, x.co_reg";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 907: //Kardex físico de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT y.co_Seg, (CASE WHEN x.st_regItm='I' THEN 'I' ELSE x.st_regMov END ) AS st_reg, * FROM (";
                            strSQL+="   SELECT a1.st_reg AS st_regMov, (CASE WHEN a2.st_Reg IS NULL THEN 'A' ELSE a2.st_Reg END ) AS st_RegItm, a2.co_Emp, a2.co_loc, a3.tx_nom AS a3_tx_nom, a2.co_bod, a4.tx_nom AS a4_tx_nom, a2.co_tipDoc, a5.tx_desCor, a5.tx_desLar";
                            strSQL+="        , a2.co_doc, a1.ne_numDoc, a1.ne_numGui, a1.ne_numOrdDes, a1.fe_doc, a5.st_cosUniCal, a2.nd_can, a1.co_cli, a1.tx_nomCli, a1.ne_secGrp, a2.co_reg";
                            strSQL+="   FROM tbm_cabMovInv AS a1";
                            strSQL+="   INNER JOIN tbm_detMovInv as a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                            strSQL+="   INNER JOIN tbm_loc AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc)";
                            strSQL+="   INNER JOIN tbm_bod AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_bod=a4.co_bod)";
                            strSQL+="   INNER JOIN tbm_cabTipDoc AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc)";
                            strSQL+="   INNER JOIN tbm_equInv AS a6 ON (a2.co_emp=a6.co_emp AND a2.co_itm=a6.co_itm)";
                            strSQL+="   INNER JOIN tbr_bodEmpBodGrp AS a7 ON (a2.co_emp=a7.co_emp AND a2.co_bod=a7.co_bod)";
                            strSQL+="   WHERE a6.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() +")";
                            strSQL+="   AND (a1.st_tipDev IS NULL OR a1.st_tipDev='C')";
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (a7.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a7.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (a7.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a7.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+="   AND (" + strAux + ")";
                            strSQL+="   ORDER BY a1.fe_doc, a1.ne_secGrp, a2.co_reg";
                            strSQL+=" ) as x ";
                            strSQL+=" LEFT OUTER JOIN tbm_cabSegMovInv as y ";
                            strSQL+=" ON (y.co_empRelCabMovInv=x.co_emp AND y.co_locRelCabMovInv=x.co_loc AND y.co_tipDocRelCabMovInv=x.co_tipDoc AND y.co_DocRelCabMovInv=x.co_Doc )";
                            strSQL+=" ORDER BY x.fe_doc, x.ne_secGrp, x.co_reg";
                            rst=stm.executeQuery(strSQL);
                            break;
                    }
                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                bgdSalUni=BigDecimal.ZERO;
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        strEstReg=rst.getString("st_reg");
                        vecReg.add(INT_TBL_DAT_CODSEG, rst.getString("co_seg"));
                        vecReg.add(INT_TBL_DAT_COD_LOC,rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_NOM_LOC,rst.getString("a3_tx_nom"));
                        vecReg.add(INT_TBL_DAT_COD_BOD,rst.getString("co_bod"));
                        vecReg.add(INT_TBL_DAT_NOM_BOD,rst.getString("a4_tx_nom"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,rst.getString("co_tipDoc"));
                        vecReg.add(INT_TBL_DAT_DEC_TIP_DOC,rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DEL_TIP_DOC,rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_COD_DOC,rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_NUM_GUI_REM,rst.getString("ne_numGui"));
                        vecReg.add(INT_TBL_DAT_NUM_ORD_DES,rst.getString("ne_numOrdDes"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,rst.getString("fe_doc"));
                        strEstCosUniCal=rst.getString("st_cosUniCal");
                        bgdCan=rst.getBigDecimal("nd_can");
                        if (bgdCan.compareTo(BigDecimal.ZERO)>=0)
                        {
                            vecReg.add(INT_TBL_DAT_CAN_ING,"" + bgdCan);
                            vecReg.add(INT_TBL_DAT_CAN_EGR,null);
                            vecReg.setElementAt(null,INT_TBL_DAT_NUM_GUI_REM);
                            vecReg.setElementAt(null,INT_TBL_DAT_NUM_ORD_DES);
                        }
                        else
                        {
                            vecReg.add(INT_TBL_DAT_CAN_ING,null);
                            vecReg.add(INT_TBL_DAT_CAN_EGR,"" + bgdCan.abs());
                        }
                        switch (objParSis.getCodigoMenu())
                        {
                            case 237: //Kardex de inventario...
                                if (strEstCosUniCal.equals("S"))
                                {
                                    vecReg.add(INT_TBL_DAT_COS_UNI,rst.getString("nd_cosUni"));
                                    vecReg.add(INT_TBL_DAT_PRE_VTA,rst.getString("nd_preUni"));
                                }
                                else
                                {
                                    vecReg.add(INT_TBL_DAT_COS_UNI,rst.getBigDecimal("nd_cosUni").multiply(BigDecimal.ONE.subtract(rst.getBigDecimal("nd_porDes").divide(new BigDecimal("100")))));
                                    vecReg.add(INT_TBL_DAT_PRE_VTA,null);
                                }
                                vecReg.add(INT_TBL_DAT_COS_TOT,rst.getString("nd_cosTot"));
                                vecReg.add(INT_TBL_DAT_SAL_UNI,rst.getString("nd_exi"));
                                vecReg.add(INT_TBL_DAT_SAL_VAL,rst.getString("nd_valExi"));
                                vecReg.add(INT_TBL_DAT_COS_PRO,rst.getString("nd_cosPro"));
                                break;
                            case 886: //Kardex de inventario (Unidades)...
                            case 907: //Kardex físico de inventario (Unidades)...
                                vecReg.add(INT_TBL_DAT_COS_UNI,null);
                                vecReg.add(INT_TBL_DAT_PRE_VTA,null);
                                vecReg.add(INT_TBL_DAT_COS_TOT,null);
                                if ( "ARCF".indexOf(strEstReg)!=-1 )
                                    bgdSalUni=bgdSalUni.add(bgdCan);
                                vecReg.add(INT_TBL_DAT_SAL_UNI,bgdSalUni);
                                vecReg.add(INT_TBL_DAT_SAL_VAL,null);
                                vecReg.add(INT_TBL_DAT_COS_PRO,null);
                                break;
                        }
                        vecReg.add(INT_TBL_DAT_COD_CLI,rst.getString("co_cli"));
                        vecReg.add(INT_TBL_DAT_NOM_CLI,rst.getString("tx_nomCli"));
                        vecReg.add(INT_TBL_DAT_EST_REG,strEstReg);
                        if ("ARCF".indexOf(strEstReg)==-1)
                        {
                            vecReg.setElementAt(null,INT_TBL_DAT_CAN_ING);
                            vecReg.setElementAt(null,INT_TBL_DAT_CAN_EGR);
                            vecReg.setElementAt(null,INT_TBL_DAT_COS_UNI);
                            vecReg.setElementAt(null,INT_TBL_DAT_PRE_VTA);
                            vecReg.setElementAt(null,INT_TBL_DAT_COS_TOT);
                        }
                        vecDat.add(vecReg);
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                
                //Obtener las cantidades que estan por ingresar y egresar.
                if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
                {
                    //Obtener los datos del "Grupo".
                    switch (objParSis.getCodigoMenu())
                    {
                        case 237: //Kardex de inventario...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                            strSQL+=" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() + ")";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 886: //Kardex de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                            strSQL+=" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() + ")";
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+=" AND (" + strAux + ")";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 907: //Kardex físico de inventario (Unidades)...
                            break;
                    }
                }
                else
                {
                    //Obtener los datos de la "Empresa seleccionada".
                    switch (objParSis.getCodigoMenu())
                    {
                        case 237: //Kardex de inventario...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                            strSQL+=" AND a1.co_itm=" + txtCodItm.getText();
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 886: //Kardex de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                            strSQL+=" AND a1.co_itm=" + txtCodItm.getText();
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (a1.co_emp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a1.co_bod=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+=" AND (" + strAux + ")";
                            rst=stm.executeQuery(strSQL);
                            break;
                        case 907: //Kardex físico de inventario (Unidades)...
                            //Armar la sentencia SQL.
                            strSQL="";
                            strSQL+=" SELECT SUM(a1.nd_canIngBod) AS nd_canIngBod, SUM(a1.nd_canEgrBod) AS nd_canEgrBod";
                            strSQL+=" FROM tbm_invBod AS a1";
                            strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_itm=a2.co_itm)";
                            strSQL+=" INNER JOIN tbr_bodEmpBodGrp AS a3 ON (a1.co_emp=a3.co_emp AND a1.co_bod=a3.co_bod)";
                            strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                            strSQL+=" AND a2.co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=" + intCodEmp + " AND co_itm=" + txtCodItm.getText() + ")";
                            //Obtener las bodegas a consultar.
                            intNumFilTblBod=objTblModBod.getRowCountTrue();
                            i=0;
                            strAux="";
                            for (j=0; j<intNumFilTblBod; j++)
                            {
                                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                                {
                                    if (i==0)
                                        strAux+=" (a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    else
                                        strAux+=" OR (a3.co_empGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_EMP) + " AND a3.co_bodGrp=" + objTblModBod.getValueAt(j, INT_TBL_BOD_COD_BOD) + ")";
                                    i++;
                                }
                            }
                            strSQL+=" AND (" + strAux + ")";
                            rst=stm.executeQuery(strSQL);
                            break;
                    }
                }
                txtCanIng.setText("");
                txtCanEgr.setText("");
                if (rst.next())
                {
                    txtCanIng.setText(objUti.formatearNumero(rst.getString("nd_canIngBod"), objParSis.getFormatoNumero(), true));
                    txtCanEgr.setText(objUti.formatearNumero(rst.getString("nd_canEgrBod"), objParSis.getFormatoNumero(), true));
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
                //Calcular totales.
                objTblTot.calcularTotales();
                objTblTot.setValueAt("" + (objUti.parseDouble(objTblTot.getValueAt(0,INT_TBL_DAT_CAN_ING))-objUti.parseDouble(objTblTot.getValueAt(0,INT_TBL_DAT_CAN_EGR))), 0, INT_TBL_DAT_SAL_UNI);
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                lblTit.setText(txtCodAlt.getText() + ": " + txtNomItm.getText());
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
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
        int intNumFilTblBod, i, j;
        //Validar el "Item".
        if (txtCodItm.getText().equals(""))
        {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Item</FONT> es obligatorio.<BR>Escriba o seleccione un item y vuelva a intentarlo.</HTML>");
            txtCodAlt.requestFocus();
            return false;
        }
        //Validar que esté seleccionada al menos una bodega si es la empresa grupo.
        if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
        {
            intNumFilTblBod=objTblModBod.getRowCountTrue();
            i=0;
            for (j=0; j<intNumFilTblBod; j++)
            {
                if (objTblModBod.isChecked(j, INT_TBL_BOD_CHK))
                {
                    i++;
                    break;
                }
            }
            if (i==0)
            {
                tabFrm.setSelectedIndex(0);
                mostrarMsgInf("<HTML>Debe seleccionar al menos una bodega.<BR>Seleccione una bodega y vuelva a intentarlo.</HTML>");
                tblBod.requestFocus();
                return false;
            }
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
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si y No. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
    
    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se grabaron
     * y que debe comunicar de este particular al administrador del sistema.
     */
    private void mostrarMsgErr(String strMsg)
    {
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.ERROR_MESSAGE);
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
            arlCam.add("d1.co_itm");
            arlCam.add("d1.tx_codAlt");
            arlCam.add("d1.tx_codAlt2");
            arlCam.add("d1.tx_nomItm");
            arlCam.add("d4.tx_desCor");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Itm.");
            arlAli.add("Cód.Alt.Itm.");
            arlAli.add("Cód.Alt.2");
            arlAli.add("Nombre");
            arlAli.add("Unidad");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("350");
            arlAncCol.add("60");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a1.co_itm, a1.tx_codAlt, a1.tx_codAlt2, a1.tx_nomItm, a2.tx_desCor";
            strSQL+=" FROM tbm_inv AS a1";
            strSQL+=" LEFT OUTER JOIN tbm_var AS a2 ON (a1.co_uni=a2.co_reg)";
            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            strSQL+=" ORDER BY a1.tx_codAlt";
            vcoItm=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de inventario", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoItm.setConfiguracionColumna(5, javax.swing.JLabel.CENTER);
            vcoItm.setCampoBusqueda(1);
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
     * registro de la base de datos. El tipo de búsqueda determina si se debe hacer
     * una búsqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opción que desea utilizar.
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConItm(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoItm.setCampoBusqueda(1);
                    vcoItm.setVisible(true);
                    if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo alterno".
                    if (vcoItm.buscar("a1.tx_codAlt", txtCodAlt.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(1);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodAlt.setText(strCodAlt);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Código Alterno 2 ".
                    if (vcoItm.buscar("a1.tx_codAlt2", txtCodLetItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(2);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtCodLetItm.setText(strCodLetItm);
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Nombre del item".
                    if (vcoItm.buscar("a1.tx_nomItm", txtNomItm.getText()))
                    {
                        txtCodItm.setText(vcoItm.getValueAt(1));
                        txtCodAlt.setText(vcoItm.getValueAt(2));
                        txtCodLetItm.setText(vcoItm.getValueAt(3));
                        txtNomItm.setText(vcoItm.getValueAt(4));
                    }
                    else
                    {
                        vcoItm.setCampoBusqueda(3);
                        vcoItm.setCriterio1(11);
                        vcoItm.cargarDatos();
                        vcoItm.setVisible(true);
                        if (vcoItm.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodItm.setText(vcoItm.getValueAt(1));
                            txtCodAlt.setText(vcoItm.getValueAt(2));
                            txtCodLetItm.setText(vcoItm.getValueAt(3));
                            txtNomItm.setText(vcoItm.getValueAt(4));
                        }
                        else
                        {
                            txtNomItm.setText(strNomItm);
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
    private void tblBodMouseClicked(java.awt.event.MouseEvent evt)
    {
        int i, intNumFil;
        try
        {
            intNumFil=objTblModBod.getRowCountTrue();
            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblBod.columnAtPoint(evt.getPoint())==INT_TBL_BOD_CHK)
            {
                if (blnMarTodChkTblBod)
                {
                    //Mostrar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(true, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=false;
                }
                else
                {
                    //Ocultar todas las columnas.
                    for (i=0; i<intNumFil; i++)
                    {
                        objTblModBod.setChecked(false, i, INT_TBL_BOD_CHK);
                    }
                    blnMarTodChkTblBod=true;
                }
            }
        }
        catch (Exception e)
        {
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
            switch (intCol)
            {
                case INT_TBL_DAT_LIN:
                    strMsg="";
                    break;
                case INT_TBL_DAT_CODSEG:
                    strMsg="Código de Seguimiento";
                    break;
                case INT_TBL_DAT_NOM_LOC:
                    strMsg="Local";
                    break;
                case INT_TBL_DAT_NOM_BOD:
                    strMsg="Bodega";
                    break;
                case INT_TBL_DAT_DEC_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DEL_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_NUM_GUI_REM:
                    strMsg="Número de guia de remisión";
                    break;
                case INT_TBL_DAT_NUM_ORD_DES:
                    strMsg="Número de orden de despacho";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento";
                    break;
                case INT_TBL_DAT_CAN_ING:
                    strMsg="Cantidad ingresada";
                    break;
                case INT_TBL_DAT_CAN_EGR:
                    strMsg="Cantidad egresada";
                    break;
                case INT_TBL_DAT_COS_UNI:
                    strMsg="Costo unitario";
                    break;
                case INT_TBL_DAT_PRE_VTA:
                    strMsg="Precio unitario de venta";
                    break;
                case INT_TBL_DAT_COS_TOT:
                    strMsg="Costo total";
                    break;
                case INT_TBL_DAT_SAL_UNI:
                    strMsg="Saldo en unidades";
                    break;
                case INT_TBL_DAT_SAL_VAL:
                    strMsg="Saldo en valores";
                    break;
                case INT_TBL_DAT_COS_PRO:
                    strMsg="Costo promedio";
                    break;
                case INT_TBL_DAT_COD_CLI:
                    strMsg="Código del cliente/proveedor";
                    break;
                case INT_TBL_DAT_NOM_CLI:
                    strMsg="Nombre del cliente/proveedor";
                    break;
                case INT_TBL_DAT_EST_REG:
                    strMsg="Estado del registro";
                    break;
                default:
                    strMsg=null;
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
    
}