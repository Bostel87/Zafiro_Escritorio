/*
 * ZafVen28.java
 *
 * Created on 18 de julio de 2011, 10:10 PM
 */

package Importaciones.ZafImp01;
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
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import java.util.ArrayList;

/**
 *
 * @author  Ingrid Lino
 */
public class ZafImp01 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:

    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP_PED_EMB=1;
    static final int INT_TBL_DAT_COD_LOC_PED_EMB=2;
    static final int INT_TBL_DAT_COD_TIP_DOC_PED_EMB=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB=4;
    static final int INT_TBL_DAT_COD_DOC_PED_EMB=5;
    static final int INT_TBL_DAT_NUM_DOC_PED_EMB=6;
    static final int INT_TBL_DAT_FEC_EMB_PED_EMB=7;
    static final int INT_TBL_DAT_FEC_ARR_PED_EMB=8;
    static final int INT_TBL_DAT_COD_IMP_PED_EMB=9;
    static final int INT_TBL_DAT_NOM_IMP_PED_EMB=10;
    static final int INT_TBL_DAT_COD_EXP_PED_EMB=11;
    static final int INT_TBL_DAT_NOM_EXP_PED_EMB=12;
    //DATOS DE LA FACTURA DE IMPORTACION
    static final int INT_TBL_DAT_COD_EMP_FAC_IMP=13;
    static final int INT_TBL_DAT_COD_LOC_FAC_IMP=14;
    static final int INT_TBL_DAT_COD_TIP_DOC_FAC_IMP=15;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP=16;
    static final int INT_TBL_DAT_COD_DOC_FAC_IMP=17;
    static final int INT_TBL_DAT_NUM_DOC_FAC_IMP=18;
    static final int INT_TBL_DAT_FEC_DOC_FAC_IMP=19;
    static final int INT_TBL_DAT_FEC_VEN_FAC_IMP=20;
    static final int INT_TBL_DAT_VAL_DOC_FAC_IMP=21;
    static final int INT_TBL_DAT_EST_REG_FAC_IMP=22;
    static final int INT_TBL_DAT_FEC_ING_FAC_IMP=23;

           
    //Variables
    private ZafSelFec objSelFecDoc, objSelFecVct;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk;                    //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;                    //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;                                //JTable de totales.
    private ZafVenCon vcoTipDoc;                                   //Ventana de consulta.
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecDatMov, vecCabMov;                        //Para el stock de cada bodega.
    private Vector vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.

    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    private ZafVenCon vcoEmp, vcoExp;
    private String strDesCorTipDoc, strDesLarTipDoc;
    

    private java.util.Date datFecAux;
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp01(ZafParSis obj)
    {
        try
        {

            objParSis=(ZafParSis)obj.clone();
            
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if (!configurarFrm())
                    exitForm();
            }
            else{
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }
            
            
            
            
            
            
            
            
            
            
            
            
        }
        catch (CloneNotSupportedException e)
        {
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
        lblTit = new javax.swing.JLabel();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
        panCon = new javax.swing.JPanel();
        panFec = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panFecVctFac = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        optTod = new javax.swing.JRadioButton();
        lblEmp = new javax.swing.JLabel();
        txtCodEmp = new javax.swing.JTextField();
        txtNomEmp = new javax.swing.JTextField();
        butEmp = new javax.swing.JButton();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        lblCli = new javax.swing.JLabel();
        butCli = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        panRpt = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 18));
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setPreferredSize(new java.awt.Dimension(475, 311));
        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panCon.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCon.setLayout(new java.awt.BorderLayout());

        panFec.setPreferredSize(new java.awt.Dimension(100, 140));
        panFec.setLayout(new java.awt.GridLayout(2, 0));

        panFecDoc.setLayout(new java.awt.BorderLayout());
        panFec.add(panFecDoc);

        panFecVctFac.setLayout(new java.awt.BorderLayout());
        panFec.add(panFecVctFac);

        panCon.add(panFec, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(0, 16, 400, 14);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 2, 400, 14);

        lblEmp.setText("Empresa(Importador):");
        lblEmp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmp);
        lblEmp.setBounds(20, 30, 116, 20);

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
        panFil.add(txtCodEmp);
        txtCodEmp.setBounds(140, 30, 70, 20);

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
        panFil.add(txtNomEmp);
        txtNomEmp.setBounds(210, 30, 360, 20);

        butEmp.setText("...");
        butEmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butEmpActionPerformed(evt);
            }
        });
        panFil.add(butEmp);
        butEmp.setBounds(570, 30, 20, 20);

        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        panFil.add(txtCodExp);
        txtCodExp.setBounds(140, 70, 70, 20);

        txtNomExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExpActionPerformed(evt);
            }
        });
        txtNomExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExpFocusLost(evt);
            }
        });
        panFil.add(txtNomExp);
        txtNomExp.setBounds(210, 70, 360, 20);

        lblCli.setText("Exportador:");
        lblCli.setToolTipText("Vendedor/Comprador");
        panFil.add(lblCli);
        lblCli.setBounds(20, 70, 80, 18);

        butCli.setText("...");
        butCli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCliActionPerformed(evt);
            }
        });
        panFil.add(butCli);
        butCli.setBounds(570, 70, 20, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(20, 50, 116, 20);
        panFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(108, 50, 32, 20);

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
        txtDesCorTipDoc.setBounds(140, 50, 70, 20);

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
        txtDesLarTipDoc.setBounds(210, 50, 360, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panFil.add(butTipDoc);
        butTipDoc.setBounds(570, 50, 20, 20);

        jLabel2.setText("Estado del documento:");
        panFil.add(jLabel2);
        jLabel2.setBounds(20, 94, 130, 14);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A:Activo", "I:Inactivo" }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(150, 92, 240, 20);

        panCon.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panCon);

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

        panRpt.add(spnTot, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        panBar.setPreferredSize(new java.awt.Dimension(320, 42));
        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(304, 26));
        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

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

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 17));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        jPanel6.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        jPanel6.setMinimumSize(new java.awt.Dimension(24, 26));
        jPanel6.setPreferredSize(new java.awt.Dimension(200, 15));
        jPanel6.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        jPanel6.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(jPanel6, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodEmp.setText("");
            txtNomEmp.setText("");
            txtCodTipDoc.setText("");
            txtDesCorTipDoc.setText("");
            txtDesLarTipDoc.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");
            cboEstDoc.setSelectedIndex(0);
        }
    }//GEN-LAST:event_optTodItemStateChanged

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
        if (!txtCodEmp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodEmp.getText().equals("")){
                txtCodEmp.setText("");
                txtNomEmp.setText("");
                objTblMod.removeAllRows();
                txtCodExp.setText("");
                txtNomExp.setText("");
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

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        // TODO add your handling code here:
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        // TODO add your handling code here:
        strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp)){
            if (txtCodExp.getText().equals("")){
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblMod.removeAllRows();
            }
            else
                mostrarVenConExp(1);
        }
        else
            txtCodExp.setText(strCodExp);
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtNomExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomExpActionPerformed
        // TODO add your handling code here:
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        // TODO add your handling code here:
        strNomExp=txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
        // TODO add your handling code here:
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNomExp.getText().equalsIgnoreCase(strNomExp))
        {
            if (txtNomExp.getText().equals(""))
            {
                txtCodExp.setText("");
                txtNomExp.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConExp(2);
            }
        }
        else
            txtNomExp.setText(strNomExp);
    }//GEN-LAST:event_txtNomExpFocusLost

    private void butCliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCliActionPerformed
        // TODO add your handling code here:
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butCliActionPerformed

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
}//GEN-LAST:event_txtDesCorTipDocActionPerformed

    private void txtDesCorTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorTipDocFocusGained
        strDesCorTipDoc=txtDesCorTipDoc.getText();
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
        } else
            txtDesCorTipDoc.setText(strDesCorTipDoc);
}//GEN-LAST:event_txtDesCorTipDocFocusLost

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
}//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void txtDesLarTipDocFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarTipDocFocusGained
        strDesLarTipDoc=txtDesLarTipDoc.getText();
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

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
}//GEN-LAST:event_butTipDocActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCli;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butEmp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblCli;
    private javax.swing.JLabel lblEmp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFecVctFac;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodEmp;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomExp;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(true);
            objSelFecDoc.setCheckBoxChecked(false);
            objSelFecDoc.setTitulo("Fecha del documento");
            
            //Configurar ZafSelFec:
            objSelFecVct=new ZafSelFec();
            panFecVctFac.add(objSelFecVct);
            objSelFecVct.setBounds(4, 0, 472, 68);
            objSelFecVct.setCheckBoxVisible(true);
            objSelFecVct.setCheckBoxChecked(false);
            objSelFecVct.setTitulo("Fecha de vencimiento de la Factura del Proveedor");            
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.3");
            lblTit.setText(strAux);

            //Configurar los JTables.
            configurarTblDat();
            configurarVenConTipDoc();
            configurarVenConExp();

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
            
            txtCodTipDoc.setVisible(false);
            
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
            vecCab=new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP_PED_EMB,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_PED_EMB,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_PED_EMB,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC_PED_EMB,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_PED_EMB,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_EMB_PED_EMB,"Fec.Emb.");
            vecCab.add(INT_TBL_DAT_FEC_ARR_PED_EMB,"Fec.Arr.");
            vecCab.add(INT_TBL_DAT_COD_IMP_PED_EMB,"Cód.Imp.");
            vecCab.add(INT_TBL_DAT_NOM_IMP_PED_EMB,"Importador");
            vecCab.add(INT_TBL_DAT_COD_EXP_PED_EMB,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP_PED_EMB,"Exportador");
            vecCab.add(INT_TBL_DAT_COD_EMP_FAC_IMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_FAC_IMP,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_FAC_IMP,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC_FAC_IMP,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC_FAC_IMP,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC_FAC_IMP,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_VEN_FAC_IMP,"Fec.Ven.");
            vecCab.add(INT_TBL_DAT_VAL_DOC_FAC_IMP,"Valor");
            vecCab.add(INT_TBL_DAT_EST_REG_FAC_IMP,"Est.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_ING_FAC_IMP,"Fec.Ing.Doc.");
            
            
            

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
            
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_PED_EMB).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_PED_EMB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_PED_EMB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_IMP_PED_EMB).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NOM_IMP_PED_EMB).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP_PED_EMB).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP_PED_EMB).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_FAC_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_FAC_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_FAC_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_FAC_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_FAC_IMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC_FAC_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_FAC_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_FAC_IMP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST_REG_FAC_IMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ING_FAC_IMP).setPreferredWidth(70);
            

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
//            tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_IMP_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_FAC_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_FAC_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_FAC_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_FAC_IMP, tblDat);

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
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_FAC_IMP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[]={INT_TBL_DAT_VAL_DOC_FAC_IMP};
            objTblTot=new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);

            //Pedido Embarcado
            ZafTblHeaGrp objTblHeaGrpPedEmb=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpPedEmb.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpPedEmb=null;
            objTblHeaColGrpPedEmb=new ZafTblHeaColGrp("Pedido Embarcado");
            objTblHeaColGrpPedEmb.setHeight(16);
            objTblHeaGrpPedEmb.addColumnGroup(objTblHeaColGrpPedEmb);

            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_FEC_EMB_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ARR_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_IMP_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_NOM_IMP_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_COD_EXP_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_NOM_EXP_PED_EMB));
            
            //Pedido Embarcado
            ZafTblHeaGrp objTblHeaGrpFecImp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecImp.setHeight(16*3);
            ZafTblHeaColGrp objTblHeaColGrpFecImp=null;
            objTblHeaColGrpFecImp=new ZafTblHeaColGrp("Factura de Importación");
            objTblHeaColGrpFecImp.setHeight(16);
            objTblHeaGrpFecImp.addColumnGroup(objTblHeaColGrpFecImp);

            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_COD_EMP_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_COD_LOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_COD_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_NUM_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_FEC_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_FEC_VEN_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_VAL_DOC_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_EST_REG_FAC_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_FEC_ING_FAC_IMP));
            
            
            
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la condición.
                strConSQL="";
                if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                    if(txtCodEmp.getText().length()>0)
                        strConSQL+=" 			WHERE a1.co_emp=" + txtCodEmp.getText() + "";
                    else
                        strConSQL+=" 			WHERE a1.co_emp NOT IN(" + objParSis.getCodigoEmpresaGrupo() + ")";
                }
                else{//si es por empresa
                    strConSQL+=" 			WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                }

                if(txtCodTipDoc.getText().length()>0)
                    strConSQL+=" 			AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
                //fecha de vencimiento de la Factura
                if(objSelFecVct.isCheckBoxChecked()){
                    switch (objSelFecVct.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a1.fe_ven BETWEEN '" + objUti.formatearFecha(objSelFecVct.getFechaDesde(), objSelFecVct.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecVct.getFechaHasta(), objSelFecVct.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a1.fe_ven<='" + objUti.formatearFecha(objSelFecVct.getFechaHasta(), objSelFecVct.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a1.fe_ven>='" + objUti.formatearFecha(objSelFecVct.getFechaDesde(), objSelFecVct.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                //fecha de documento de la Factura
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strConSQL+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strConSQL+=" AND a1.fe_dia<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strConSQL+=" AND a1.fe_dia>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }

                if(txtCodExp.getText().length()>0)
                    strConSQL+="                   AND a4.co_exp=" + txtCodExp.getText() + "";

                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos exepto los eliminados
                    strConSQL+=" AND a1.st_reg NOT IN ('E')";
                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                    strConSQL+=" AND a1.st_reg IN ('A')";
                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los activos
                    strConSQL+=" AND a1.st_reg IN ('I')";

                   


                    
                    
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_dia";
                strSQL+=" , a2.tx_desCor, a2.tx_desLar, a1.tx_numDia, a1.fe_dia, a1.fe_ing, a1.st_reg, a1.st_imp, a1.fe_ven, a1.nd_valDoc";
                strSQL+=" , a3.co_emp AS co_empPedEmb, a3.co_loc AS co_locPedEmb, a3.co_tipDoc AS co_tipDocPedEmb, a3.co_doc AS co_docPedEmb";
                strSQL+=" , a4.ne_numDoc, a4.tx_numDoc2, a4.co_imp, a5.tx_nom AS tx_nomImp";
                strSQL+=" , a4.co_exp, a6.tx_nom AS tx_nomExp, a4.fe_emb, a4.fe_arr, a9.tx_desCor AS tx_desCorTipDocPedEmb";
                strSQL+=" FROM tbm_cabDia AS a1";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a2 ON(a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_cabPedEmbImpCabDia AS a3 ON(a1.co_emp=a3.co_empRel ";
                strSQL+=" AND a1.co_loc=a3.co_locRel AND a1.co_tipDoc=a3.co_tipDocRel AND a1.co_dia=a3.co_diaRel)";
                strSQL+=" INNER JOIN tbm_cabPedEmbImp AS a4 ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" INNER JOIN tbm_emp AS a5 ON(a4.co_imp=a5.co_emp)";
                strSQL+=" INNER JOIN tbm_expImp AS a6 ON(a4.co_exp=a6.co_exp)";

                if(objParSis.getCodigoUsuario()==1){
                    strSQL+="             INNER JOIN tbr_tipDocPrg AS a7";
                    strSQL+="             ON a1.co_emp=a7.co_emp AND a1.co_loc=a7.co_loc AND a1.co_tipDoc=a7.co_tipDoc";
                }
                else{
                    strSQL+="             INNER JOIN tbr_tipDocUsr AS a7";
                    strSQL+="             ON a1.co_emp=a7.co_emp and a1.co_loc=a7.co_loc and a1.co_tipdoc=a7.co_tipdoc";
                }
                
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a9 ON(a4.co_emp=a9.co_emp AND a4.co_loc=a9.co_loc AND a4.co_tipDoc=a9.co_tipDoc)";
                strSQL+=strConSQL;
                strSQL+=" 		 AND a7.co_mnu=" + objParSis.getCodigoMenu() + "";
                if(objParSis.getCodigoUsuario()!=1)
                    strSQL+=" 		 AND a7.co_usr=" + objParSis.getCodigoUsuario() + "";
                    
                strSQL+=" ORDER BY a1.fe_ven, a1.co_emp, a4.co_exp, a4.tx_numDoc2, a1.co_dia";
                System.out.println("cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
//                }
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next()){
                    if (blnCon){
                        
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP_PED_EMB,         rst.getString("co_empPedEmb"));//co_empPedEmb
                        vecReg.add(INT_TBL_DAT_COD_LOC_PED_EMB,         rst.getString("co_locPedEmb"));//co_locPedEmb
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_PED_EMB,     rst.getString("co_tipDocPedEmb"));//co_tipDocPedEmb
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB, rst.getString("tx_desCorTipDocPedEmb"));//tx_desCorTipDocPedEmb
                        vecReg.add(INT_TBL_DAT_COD_DOC_PED_EMB,         rst.getString("co_docPedEmb"));//co_docPedEmb
                        vecReg.add(INT_TBL_DAT_NUM_DOC_PED_EMB,         rst.getString("tx_numDoc2"));//tx_numDoc2
                        vecReg.add(INT_TBL_DAT_FEC_EMB_PED_EMB,         rst.getString("fe_emb"));//fe_emb
                        vecReg.add(INT_TBL_DAT_FEC_ARR_PED_EMB,         rst.getString("fe_arr"));//fe_arr
                        vecReg.add(INT_TBL_DAT_COD_IMP_PED_EMB,         rst.getString("co_imp"));//co_imp
                        vecReg.add(INT_TBL_DAT_NOM_IMP_PED_EMB,         rst.getString("tx_nomImp"));//tx_nomImp
                        vecReg.add(INT_TBL_DAT_COD_EXP_PED_EMB,         rst.getString("co_exp"));//co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP_PED_EMB,         rst.getString("tx_nomExp"));//tx_nomExp
                        vecReg.add(INT_TBL_DAT_COD_EMP_FAC_IMP,         rst.getString("co_emp"));//co_emp
                        vecReg.add(INT_TBL_DAT_COD_LOC_FAC_IMP,         rst.getString("co_loc"));//co_loc
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_FAC_IMP,     rst.getString("co_tipDoc"));//co_tipDoc
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP, rst.getString("tx_desCor"));//tx_desCor
                        vecReg.add(INT_TBL_DAT_COD_DOC_FAC_IMP,         rst.getString("co_dia"));//co_dia
                        vecReg.add(INT_TBL_DAT_NUM_DOC_FAC_IMP,         rst.getString("tx_numDia"));//tx_numDia
                        vecReg.add(INT_TBL_DAT_FEC_DOC_FAC_IMP,         rst.getString("fe_dia"));//fe_dia
                        vecReg.add(INT_TBL_DAT_FEC_VEN_FAC_IMP,         rst.getString("fe_ven"));//fe_ven
                        vecReg.add(INT_TBL_DAT_VAL_DOC_FAC_IMP,         rst.getString("nd_valDoc"));//nd_valDoc
                        vecReg.add(INT_TBL_DAT_EST_REG_FAC_IMP,         rst.getString("st_reg"));//st_reg
                        vecReg.add(INT_TBL_DAT_FEC_ING_FAC_IMP,         rst.getString("fe_ing"));//fe_ing
                        
                        
                        vecDat.add(vecReg);
                    }
                    else
                    {
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
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
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
     * Esta función obtiene la condición SQL adicional para los campos que "Terminan con".
     * La cadena recibida es separada para formar la condición que se agregará la sentencia SQL.
     * Por ejemplo: 
     * Si strCam="a2.tx_codAlt" y strCad="I, S, L" el resultado sería "AND (a2.tx_codalt LIKE '%I' OR a2.tx_codalt LIKE '%S' OR a2.tx_codalt LIKE '%L')"
     * @param strCam El campo que se utilizará para la condición.
     * @param strCad La cadena que se separará para formar la condición.
     * @return La cadena que contiene la condición SQL .
     */
    private String getConSQLAdiCamTer(String strCam, String strCad)
    {
        byte i;
        String strRes="";
        try
        {
            if (strCad.length()>0)
            {
                java.util.StringTokenizer stkAux=new java.util.StringTokenizer(strCad, ",", false);
                i=0;
                while (stkAux.hasMoreTokens())
                {
                    if (i==0)
                        strRes+=" AND (LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    else
                        strRes+=" OR LOWER(" + strCam + ") LIKE '%" + stkAux.nextToken().toLowerCase() + "'";
                    i++;
                }
                strRes+=")";
            }
        }
        catch (java.util.NoSuchElementException e)
        {
            strRes="";
        }
        return strRes;
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
                case INT_TBL_DAT_COD_EMP_PED_EMB:
                    strMsg="Código de la empresa del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_LOC_PED_EMB:
                    strMsg="Código del local del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_PED_EMB:
                    strMsg="Código del tipo de documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC_PED_EMB:
                    strMsg="Descripción corta del tipo de documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_DOC_PED_EMB:
                    strMsg="Código del documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NUM_DOC_PED_EMB:
                    strMsg="Número del documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_FEC_EMB_PED_EMB:
                    strMsg="Fecha del embarque del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_FEC_ARR_PED_EMB:
                    strMsg="Fecha del arribo del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_IMP_PED_EMB:
                    strMsg="Código del Importador del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NOM_IMP_PED_EMB:
                    strMsg="Nombre del Importador del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_EXP_PED_EMB:
                    strMsg="Código del Exportador del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NOM_EXP_PED_EMB:
                    strMsg="Nombre del Exportador del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_EMP_FAC_IMP:
                    strMsg="Código de la empresa(Factura por Importación)";
                    break;
                case INT_TBL_DAT_COD_LOC_FAC_IMP:
                    strMsg="Código del local(Factura por Importación)";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_FAC_IMP:
                    strMsg="Código del tipo de documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC_FAC_IMP:
                    strMsg="Descripción corta del tipo de documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_COD_DOC_FAC_IMP:
                    strMsg="Código del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_NUM_DOC_FAC_IMP:
                    strMsg="Número del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_FEC_DOC_FAC_IMP:
                    strMsg="Fecha del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_FEC_VEN_FAC_IMP:
                    strMsg="Fecha del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_VAL_DOC_FAC_IMP:
                    strMsg="Fecha del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_EST_REG_FAC_IMP:
                    strMsg="Estado del documento(Factura por Importación)";
                    break;
                case INT_TBL_DAT_FEC_ING_FAC_IMP:
                    strMsg="Fecha de ingreso del documento(Factura por Importación)";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butCli.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodEmp.getText())){
                        txtCodEmp.setText(vcoEmp.getValueAt(1));
                        txtNomEmp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butCli.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butCli.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
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
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butCli.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodEmp.setText(vcoEmp.getValueAt(1));
                            txtNomEmp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butCli.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
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
    private boolean mostrarVenConExp(int intTipBus)
    {
        boolean blnRes=true;
        String strSQLTmp="";
        try{               
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoExp.setCampoBusqueda(1);
                    vcoExp.show();
                    if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(0);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtCodExp.setText(strCodExp);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoExp.buscar("a1.tx_nom", txtNomExp.getText()))
                    {
                        txtCodExp.setText(vcoExp.getValueAt(1));
                        txtNomExp.setText(vcoExp.getValueAt(2));
                    }
                    else
                    {
                        vcoExp.setCampoBusqueda(2);
                        vcoExp.setCriterio1(11);
                        vcoExp.cargarDatos();
                        vcoExp.show();
                        if (vcoExp.getSelectedButton()==vcoExp.INT_BUT_ACE)
                        {
                            txtCodExp.setText(vcoExp.getValueAt(1));
                            txtNomExp.setText(vcoExp.getValueAt(2));
                        }
                        else
                        {
                            txtNomExp.setText(strNomExp);
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
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConExp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_exp");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_nom2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Nombre");
            arlAli.add("Nombre alterno");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("225");
            arlAncCol.add("225");
            //Armar la sentencia SQL.
            strSQL="";
            strSQL+="SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
            strSQL+=" FROM tbm_expImp AS a1";
            strSQL+=" WHERE a1.st_reg='A'";
            strSQL+=" ORDER BY a1.tx_nom";
            vcoExp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de exportadores", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            //Configurar columnas.
            vcoExp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
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
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() +"";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" FROM tbr_tipDocUsr AS a2 inner join  tbm_cabTipDoc AS a1";
                strSQL+=" ON (a1.co_emp=a2.co_emp and a1.co_loc=a2.co_loc and a1.co_tipdoc=a2.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario() + "";
//                System.out.println("ERROR EN ASIENTO: " +strSQL);
            }

            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
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
    
}