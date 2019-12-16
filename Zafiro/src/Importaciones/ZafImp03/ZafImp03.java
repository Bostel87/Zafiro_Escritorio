/*
 * ZafImp03.java
 *
 * Created on 22 de agosto del 2013
 */
package Importaciones.ZafImp03;
import Librerias.ZafImp.ZafAjuInv;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafImp.ZafSegAjuInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
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
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
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
public class ZafImp03 extends javax.swing.JInternalFrame
{
    //Constantes: Columnas del JTable:
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    static final int INT_TBL_DAT_COD_DOC=6;
    static final int INT_TBL_DAT_NUM_DOC=7;
    static final int INT_TBL_DAT_FEC_DOC=8;
    static final int INT_TBL_DAT_FEC_EMB=9;
    static final int INT_TBL_DAT_FEC_ARR=10;
    static final int INT_TBL_DAT_COD_EXP=11;
    static final int INT_TBL_DAT_NOM_EXP=12;
    static final int INT_TBL_DAT_TOT_FOB_CFR=13;
    static final int INT_TBL_DAT_TOT_ARA_FOD_IVA=14;
    static final int INT_TBL_DAT_EST=15;
    static final int INT_TBL_DAT_CHK_LIS=16;
    static final int INT_TBL_DAT_CHK_CIE=17;
    static final int INT_TBL_DAT_BUT=18;
    static final int INT_TBL_DAT_CHK_PED_EMB=19;
    static final int INT_TBL_DAT_BUT_PED_EMB=20;
    static final int INT_TBL_DAT_CHK_ING_IMP=21;
    static final int INT_TBL_DAT_BUT_ING_IMP=22;
    static final int INT_TBL_DAT_CHK_DOC_AJU=23;
    static final int INT_TBL_DAT_BUT_DOC_AJU=24;
    
    //Variables
    private ZafSelFec objSelFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafPerUsr objPerUsr;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkPedEmb, objTblCelRenChkIngImp, objTblCelRenChkDocAju;   //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkPedEmb, objTblCelEdiChkIngImp, objTblCelEdiChkDocAju;   //Editor: JCheckBox en celda.
    private ZafTblCelRenBut objTblCelRenBut, objTblCelRenButPedEmb, objTblCelRenButIngImp, objTblCelRenButDocAju;   //Render: Presentar Button en JTable.
    private ZafTblCelEdiButGen objTblCelEdiButGen, objTblCelEdiButGenPedEmb, objTblCelEdiButGenIngImp, objTblCelEdiButGenDocAju; //Editor: Button en celda.
    private ZafMouMotAda objMouMotAda;                          //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafAjuInv objAjuInv;   
    private ZafSegAjuInv objSegAjuInv;
    private ZafImp objImp;
    private ZafVenCon vcoEmp, vcoExp;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    
    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                     //true: Continua la ejecución del hilo.
    
    private int intCodTipDocNotPed, intCodDocNotPed;
    private int intCodEmpAju, intCodLocAju, intCodTipDocAju,  intCodDocAju;
    private int intRowsDocAju=-1;
    
    private String strSQL, strAux;
    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    private String strVersion=" v0.1.4";

    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp03(ZafParSis obj) 
    {
        try 
        {
            objParSis = (ZafParSis) obj.clone();

            if(objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo())  
            {
                initComponents();
                if (!configurarFrm()) 
                {
                    exitForm();
                }
            } 
            else 
            {
                mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();
            }

        } 
        catch (CloneNotSupportedException e) {    this.setTitle(this.getTitle() + " [ERROR]"); }
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
        panFecDoc = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblEmpImp = new javax.swing.JLabel();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        butImp = new javax.swing.JButton();
        lblExp = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        butExp = new javax.swing.JButton();
        lblEstDoc = new javax.swing.JLabel();
        cboEstDoc = new javax.swing.JComboBox();
        chkMosPedEmbAso = new javax.swing.JCheckBox();
        chkMosIngImpAso = new javax.swing.JCheckBox();
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
        panPrgSis = new javax.swing.JPanel();
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

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panCon.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panFil.setPreferredSize(new java.awt.Dimension(0, 250));
        panFil.setLayout(null);

        bgrFil.add(optTod);
        optTod.setSelected(true);
        optTod.setText("Todos los documentos");
        optTod.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                optTodItemStateChanged(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(10, 10, 400, 20);

        bgrFil.add(optFil);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        panFil.add(optFil);
        optFil.setBounds(10, 30, 400, 20);

        lblEmpImp.setText("Empresa(Importador):");
        lblEmpImp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(40, 60, 140, 20);

        txtCodImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodImpFocusLost(evt);
            }
        });
        txtCodImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodImpActionPerformed(evt);
            }
        });
        panFil.add(txtCodImp);
        txtCodImp.setBounds(180, 60, 70, 20);

        txtNomImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomImpFocusLost(evt);
            }
        });
        txtNomImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomImpActionPerformed(evt);
            }
        });
        panFil.add(txtNomImp);
        txtNomImp.setBounds(250, 60, 360, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(610, 60, 20, 20);

        lblExp.setText("Exportador:");
        lblExp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblExp);
        lblExp.setBounds(40, 80, 140, 20);

        txtCodExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodExpFocusLost(evt);
            }
        });
        txtCodExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodExpActionPerformed(evt);
            }
        });
        panFil.add(txtCodExp);
        txtCodExp.setBounds(180, 80, 70, 20);

        txtNomExp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomExpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomExpFocusLost(evt);
            }
        });
        txtNomExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomExpActionPerformed(evt);
            }
        });
        panFil.add(txtNomExp);
        txtNomExp.setBounds(250, 80, 360, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panFil.add(butExp);
        butExp.setBounds(610, 80, 20, 20);

        lblEstDoc.setText("Estado del documento:");
        panFil.add(lblEstDoc);
        lblEstDoc.setBounds(40, 100, 140, 20);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A:Activo", "I:Inactivo" }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(180, 100, 210, 20);

        chkMosPedEmbAso.setSelected(true);
        chkMosPedEmbAso.setText("Mostrar pedidos embarcados asociados");
        chkMosPedEmbAso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosPedEmbAsoActionPerformed(evt);
            }
        });
        panFil.add(chkMosPedEmbAso);
        chkMosPedEmbAso.setBounds(40, 140, 290, 20);

        chkMosIngImpAso.setSelected(true);
        chkMosIngImpAso.setText("Mostrar ingresos por importación asociados");
        chkMosIngImpAso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosIngImpAsoActionPerformed(evt);
            }
        });
        panFil.add(chkMosIngImpAso);
        chkMosIngImpAso.setBounds(40, 160, 290, 20);

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

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout());

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panBar.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panBar, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if (optTod.isSelected())
        {
            txtCodImp.setText("");
            txtNomImp.setText("");
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

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
        if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp))
        {
            if (txtCodImp.getText().equals("")){
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblMod.removeAllRows();
                txtCodExp.setText("");
                txtNomExp.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodImp.setText(strCodEmp);
    }//GEN-LAST:event_txtCodImpFocusLost

    private void txtNomImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomImpActionPerformed
        txtNomImp.transferFocus();
    }//GEN-LAST:event_txtNomImpActionPerformed

    private void txtNomImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusGained
        strNomEmp=txtNomImp.getText();
        txtNomImp.selectAll();
    }//GEN-LAST:event_txtNomImpFocusGained

    private void txtNomImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomImpFocusLost
        if (!txtNomImp.getText().equalsIgnoreCase(strNomEmp))
        {
            if (txtNomImp.getText().equals(""))
            {
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblMod.removeAllRows();
            }
            else
            {
                mostrarVenConEmp(2);
            }
        }
        else
            txtNomImp.setText(strNomEmp);
    }//GEN-LAST:event_txtNomImpFocusLost

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        strCodEmp=txtCodImp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
        strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtCodExp.getText().equalsIgnoreCase(strCodExp))
        {
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
        txtNomExp.transferFocus();
    }//GEN-LAST:event_txtNomExpActionPerformed

    private void txtNomExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusGained
        strNomExp=txtNomExp.getText();
        txtNomExp.selectAll();
    }//GEN-LAST:event_txtNomExpFocusGained

    private void txtNomExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomExpFocusLost
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

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

    private void chkMosPedEmbAsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosPedEmbAsoActionPerformed
        if(chkMosPedEmbAso.isSelected())
        {
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CHK_PED_EMB, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_PED_EMB, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_PED_EMB, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_PED_EMB, tblDat);
        }
    }//GEN-LAST:event_chkMosPedEmbAsoActionPerformed

    private void chkMosIngImpAsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosIngImpAsoActionPerformed
        if(chkMosIngImpAso.isSelected())
        {
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_CHK_ING_IMP, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_ING_IMP, tblDat);
            objTblMod.removeSystemHiddenColumn(INT_TBL_DAT_BUT_DOC_AJU, tblDat);
        }
        else
        {
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT_DOC_AJU, tblDat);
        }
    }//GEN-LAST:event_chkMosIngImpAsoActionPerformed

    /** Cerrar la aplicación. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butImp;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JCheckBox chkMosIngImpAso;
    private javax.swing.JCheckBox chkMosPedEmbAso;
    private javax.swing.JLabel lblEmpImp;
    private javax.swing.JLabel lblEstDoc;
    private javax.swing.JLabel lblExp;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCon;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodExp;
    private javax.swing.JTextField txtCodImp;
    private javax.swing.JTextField txtNomExp;
    private javax.swing.JTextField txtNomImp;
    // End of variables declaration//GEN-END:variables
   
    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
                        
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3206))
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3207))
            {
                butCer.setVisible(false);
            }
                        
            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(false);
            objSelFecDoc.setCheckBoxChecked(true);
            objSelFecDoc.setTitulo("Fecha del documento");    

            //Configurar objetos.
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo())
            {
                configurarVenConEmp();
                txtCodImp.setEditable(true);
                txtNomImp.setEditable(true);
                butImp.setEnabled(true);
            }
            else
            {
                lblEmpImp.setVisible(false);
                txtCodImp.setVisible(false);
                txtNomImp.setVisible(false);
                butImp.setVisible(false);
            }
                        
            //Configurar los JTables.
            configurarTblDat();
            configurarVenConExp();
            
        }
        catch(Exception e) {    blnRes=false; objUti.mostrarMsgErr_F1(this, e);  }
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
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_FEC_EMB,"Fec.Emb.");
            vecCab.add(INT_TBL_DAT_FEC_ARR,"Fec.Arr.");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
            vecCab.add(INT_TBL_DAT_TOT_FOB_CFR,"Tot.Fob.Cfr.");
            vecCab.add(INT_TBL_DAT_TOT_ARA_FOD_IVA,"Tot.Ara.Fod.Iva.");
            vecCab.add(INT_TBL_DAT_EST,"Est.");
            vecCab.add(INT_TBL_DAT_CHK_LIS,"Lis.");
            vecCab.add(INT_TBL_DAT_CHK_CIE,"Cie.");
            vecCab.add(INT_TBL_DAT_BUT,"Not.");
            vecCab.add(INT_TBL_DAT_CHK_PED_EMB,"Est.");
            vecCab.add(INT_TBL_DAT_BUT_PED_EMB,"");
            vecCab.add(INT_TBL_DAT_CHK_ING_IMP,"Est.");
            vecCab.add(INT_TBL_DAT_BUT_ING_IMP,"");
            vecCab.add(INT_TBL_DAT_CHK_DOC_AJU,"Est.");
            vecCab.add(INT_TBL_DAT_BUT_DOC_AJU,"Aju.");

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
            
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(19);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(57);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(68);
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA_FOD_IVA).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_EST).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_CHK_LIS).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_EMB).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT_PED_EMB).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ING_IMP).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT_ING_IMP).setPreferredWidth(35);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DOC_AJU).setPreferredWidth(28);
            tcmAux.getColumn(INT_TBL_DAT_BUT_DOC_AJU).setPreferredWidth(35);

            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            //tcmAux.getColumn(INT_TBL_DAT_BUT_CTA).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_DOC_AJU, tblDat);  
            
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
            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA_FOD_IVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);

            //Pedido Embarcado
            ZafTblHeaGrp objTblHeaGrpPedEmb=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpPedEmb.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpPedEmb=null;
            objTblHeaColGrpPedEmb=new ZafTblHeaColGrp("Embarque");
            objTblHeaColGrpPedEmb.setHeight(16);
            objTblHeaGrpPedEmb.addColumnGroup(objTblHeaColGrpPedEmb);

            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_CHK_PED_EMB));
            objTblHeaColGrpPedEmb.add(tcmAux.getColumn(INT_TBL_DAT_BUT_PED_EMB));
            
            //Ingreso por importación
            ZafTblHeaGrp objTblHeaGrpFecImp=(ZafTblHeaGrp)tblDat.getTableHeader();
            objTblHeaGrpFecImp.setHeight(16*2);
            ZafTblHeaColGrp objTblHeaColGrpFecImp=null;
            objTblHeaColGrpFecImp=new ZafTblHeaColGrp("Ingreso");
            objTblHeaColGrpFecImp.setHeight(16);
            objTblHeaGrpFecImp.addColumnGroup(objTblHeaColGrpFecImp);

            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_CHK_ING_IMP));
            objTblHeaColGrpFecImp.add(tcmAux.getColumn(INT_TBL_DAT_BUT_ING_IMP));  
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT);
            vecAux.add("" + INT_TBL_DAT_BUT_PED_EMB);
            vecAux.add("" + INT_TBL_DAT_BUT_ING_IMP);
            vecAux.add("" + INT_TBL_DAT_BUT_DOC_AJU);
            
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_LIS).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_LIS).setCellEditor(objTblCelEdiChk);
            
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_CIE).setCellEditor(objTblCelEdiChk);
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkPedEmb=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_EMB).setCellRenderer(objTblCelRenChkPedEmb);
            objTblCelRenChkPedEmb=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkPedEmb=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PED_EMB).setCellEditor(objTblCelEdiChkPedEmb);        
            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkIngImp=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_ING_IMP).setCellRenderer(objTblCelRenChkIngImp);
            objTblCelRenChkIngImp=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkIngImp=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_ING_IMP).setCellEditor(objTblCelEdiChkIngImp);     
                        
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDocAju=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_DOC_AJU).setCellRenderer(objTblCelRenChkDocAju);
            objTblCelRenChkDocAju=null;
            
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDocAju=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_DOC_AJU).setCellEditor(objTblCelEdiChkDocAju);     
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE NOTA DE PEDIDO
            objTblCelRenBut=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenBut.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT:
                            if(objTblMod.isChecked(objTblCelRenBut.getRowRender(), INT_TBL_DAT_CHK_LIS))
                               objTblCelRenBut.setText("...");
                            else
                               objTblCelRenBut.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGen=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_LIS))
                        objTblCelEdiButGen.setCancelarEdicion(false);
                    else
                        objTblCelEdiButGen.setCancelarEdicion(true);
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_LIS))
                        mostrarFormularioNotaPedido(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE PEDIDO EMBARCADO
            objTblCelRenButPedEmb=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_PED_EMB).setCellRenderer(objTblCelRenButPedEmb);
            objTblCelRenButPedEmb.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButPedEmb.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_PED_EMB:
                            if(objTblMod.isChecked(objTblCelRenButPedEmb.getRowRender(), INT_TBL_DAT_CHK_PED_EMB))
                               objTblCelRenButPedEmb.setText("...");
                            else
                               objTblCelRenButPedEmb.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGenPedEmb=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_PED_EMB).setCellEditor(objTblCelEdiButGenPedEmb);
            objTblCelEdiButGenPedEmb.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_PED_EMB))
                        objTblCelEdiButGenPedEmb.setCancelarEdicion(false);
                    else
                        objTblCelEdiButGenPedEmb.setCancelarEdicion(true);
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_PED_EMB))
                        mostrarFormularioPedidoEmbarcado(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE INGRESO POR IMPORTACION
            objTblCelRenButIngImp=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ING_IMP).setCellRenderer(objTblCelRenButIngImp);
            objTblCelRenButIngImp.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButIngImp.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_ING_IMP:
                            if(objTblMod.isChecked(objTblCelRenButIngImp.getRowRender(), INT_TBL_DAT_CHK_ING_IMP))
                               objTblCelRenButIngImp.setText("...");
                            else
                               objTblCelRenButIngImp.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGenIngImp=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_ING_IMP).setCellEditor(objTblCelEdiButGenIngImp);
            objTblCelEdiButGenIngImp.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_ING_IMP))
                        objTblCelEdiButGenIngImp.setCancelarEdicion(false);
                    else
                        objTblCelEdiButGenIngImp.setCancelarEdicion(true);
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_ING_IMP))
                        mostrarFormularioIngresoImportacion(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });            
            
            //PARA EL BOTON QUE LLAMA A LA VENTANA DE DOCUMENTOS DE AJUSTES RELACIONADOS A LOS INGRESOS POR IMPORTACIÓN.
            objTblCelRenButDocAju=new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_DOC_AJU).setCellRenderer(objTblCelRenButDocAju);
            objTblCelRenButDocAju.addTblCelRenListener(new ZafTblCelRenAdapter() 
            {
                @Override
                public void beforeRender(ZafTblCelRenEvent evt) 
                {
                    switch (objTblCelRenButDocAju.getColumnRender())
                    {
                        case INT_TBL_DAT_BUT_DOC_AJU:
                            if(objTblMod.isChecked(objTblCelRenButDocAju.getRowRender(), INT_TBL_DAT_CHK_DOC_AJU))
                               objTblCelRenButDocAju.setText("...");
                            else
                               objTblCelRenButDocAju.setText("");
                        break;
                    }
                }
            });
            objTblCelEdiButGenDocAju=new ZafTblCelEdiButGen();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT_DOC_AJU).setCellEditor(objTblCelEdiButGenDocAju);
            objTblCelEdiButGenDocAju.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_DOC_AJU))
                        objTblCelEdiButGenDocAju.setCancelarEdicion(false);
                    else
                        objTblCelEdiButGenDocAju.setCancelarEdicion(true);
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(intFilSel, INT_TBL_DAT_CHK_DOC_AJU))
                        mostrarFormularioDocumentoAjuste(intFilSel);
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            
        }
        catch(Exception e)  {    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);   }
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
                    strMsg="Código de la empresa de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha del documento de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_FEC_EMB:
                    strMsg="Fecha del embarque de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_FEC_ARR:
                    strMsg="Fecha del arribo de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del Exportador de la Nota de Pedido";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del Exportador de la Nota de Pedido";
                    break;                    
                case INT_TBL_DAT_TOT_FOB_CFR:
                    strMsg="Total FOB / CFR";
                    break;
                case INT_TBL_DAT_TOT_ARA_FOD_IVA:
                    strMsg="Total de arancel, fodinfa e Iva";
                    break;
                case INT_TBL_DAT_EST:
                    strMsg="Estado de la nota de pedido";
                    break;
                case INT_TBL_DAT_CHK_LIS:
                    strMsg="Nota de pedido lista";
                    break;
                case INT_TBL_DAT_CHK_CIE:
                    strMsg="Nota de pedido esta cerrada";
                    break;
                case INT_TBL_DAT_BUT:
                    strMsg="Muestra formulario de la nota de pedido";
                    break;
                case INT_TBL_DAT_CHK_PED_EMB:
                    strMsg="Existe pedido embarcado asociado";
                    break;
                case INT_TBL_DAT_BUT_PED_EMB:
                    strMsg="Muestra formulario del pedido embarcado";
                    break;
                case INT_TBL_DAT_CHK_ING_IMP:
                    strMsg="Existe ingreso por importación asociado";
                    break;
                case INT_TBL_DAT_BUT_ING_IMP:
                    strMsg="Muestra formulario del ingreso por importación";
                    break;
                case INT_TBL_DAT_CHK_DOC_AJU:
                    strMsg="Existe documento de ajuste asociado";
                    break;
                case INT_TBL_DAT_BUT_DOC_AJU:
                    strMsg="Muestra documentos de ajustes del pedido";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
     private boolean mostrarVenConEmp(int intTipBus)
     {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoEmp.setCampoBusqueda(2);
                    vcoEmp.show();
                    if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText()))
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else
                        {
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText()))
                    {
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else
                    {
                        vcoEmp.setCampoBusqueda(2);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE)
                        {
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else
                        {
                            txtNomImp.setText(strNomEmp);
                        }
                    }
                    break;
            }
        }
        catch (Exception e)   {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);    }
        return blnRes;
    }

    private boolean configurarVenConEmp()
    {
        boolean blnRes=true;
        String strTitVenCon="";
        try
        {
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

            if(objParSis.getCodigoUsuario()==1)
            {
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
                strSQL+=" FROM tbm_emp AS a1";
                strSQL+=" WHERE a1.co_emp<>" + objParSis.getCodigoEmpresaGrupo() + "";
                strSQL+=" AND a1.st_reg NOT IN('I','E')";
                strSQL+=" ORDER BY a1.co_emp";
            }
            else
            {
                strSQL="";
                strSQL+=" SELECT a1.co_emp, a1.tx_nom";
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
        catch (Exception e){    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);  }
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
                case 1: //Búsqueda directa por "Número de cuenta".
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
                case 2: //Búsqueda directa por "Descripción larga".
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
        catch (Exception e)   {    blnRes=false;   objUti.mostrarMsgErr_F1(this, e);         }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Exportadores".
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
            strSQL+=" SELECT a1.co_exp, a1.tx_nom, a1.tx_nom2";
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
        catch (Exception e)  {     blnRes=false;    objUti.mostrarMsgErr_F1(this, e);  }
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

    private void mostrarFormularioNotaPedido(int fila)
    {
        String strCodLoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
        String strCodTipDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
        String strCodDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
                                
        Compras.ZafCom75.ZafCom75 objCom_75=new Compras.ZafCom75.ZafCom75(objParSis, Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
        this.getParent().add(objCom_75,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objCom_75.setVisible(true);
        objCom_75=null;
    }
        
    private void mostrarFormularioPedidoEmbarcado(int fila)
    {
        try
        {
            getNumeroPedidosEmbarcadosAsociados(fila);
            ZafImp03_01 objImp03_01=new ZafImp03_01(objParSis);//datos de nota de pedido
            objImp03_01.setCodigoTipoDocumentoNotaPedido(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()));
            objImp03_01.setCodigoDocumentoNotaPedido(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString()));
            objImp03_01.cargarDocumentos();
            
            if(objImp03_01.getNumeroRegistros()==1)
            {
                //se carga el programa Pedido Embarcado
                Compras.ZafCom76.ZafCom76 objCom_76=new Compras.ZafCom76.ZafCom76(objParSis, objImp03_01.getCodigoTipoDocumentoPedidoEmbarcado(), objImp03_01.getCodigoDocumentoPedidoEmbarcado());
                this.getParent().add(objCom_76,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objCom_76.setVisible(true);
                objCom_76=null;
            }
            else
            {
                this.getParent().add(objImp03_01,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objImp03_01.setVisible(true);
                objImp03_01=null;
            }
        }
        catch (Exception e){     objUti.mostrarMsgErr_F1(this, e);   }
    }
        
    private boolean getNumeroPedidosEmbarcadosAsociados(int fila)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.tx_desLar";
                strSQL+="      , b1.co_tipDocNotPed, b1.co_docNotPed, COUNT(b1.co_docNotPed) AS ne_numRegNotPed ";
                strSQL+=" FROM(";
                strSQL+="	SELECT a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+=" 	     , a5.tx_desCor, a5.tx_desLar, a4.ne_numDoc, a4.tx_numDoc2";
                strSQL+=" 	     , a1.co_tipDoc AS co_tipDocNotPed, a1.co_doc AS co_docNotPed";
                strSQL+=" 	FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+=" 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" 	INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+=" 	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipDocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQL+=" 	INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+=" 	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+=" 	INNER JOIN tbm_cabTipDoc AS a5";
                strSQL+=" 	ON a4.co_emp=a5.co_emp AND a4.co_loc=a5.co_loc AND a4.co_tipDoc=a5.co_tipDoc";
                strSQL+=" 	WHERE a4.st_reg NOT IN('E','I')";
                strSQL+=" 	AND a1.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP) + " AND a1.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC) + "";
                strSQL+="       AND a1.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC) + " AND a1.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC) + "";
                strSQL+=" 	GROUP BY a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+="	       , a5.tx_desCor, a5.tx_desLar, a4.ne_numDoc, a4.tx_numDoc2, a1.co_tipDoc, a1.co_doc";
                strSQL+="	ORDER BY a1.co_doc, a4.co_emp, a4.co_loc, a4.co_tipDoc, a4.co_doc";
                strSQL+=" ) AS b1";
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.tx_desLar, b1.co_tipDocNotPed, b1.co_docNotPed";
                strSQL+=" ORDER BY b1.co_docNotPed";
                rst=stm.executeQuery(strSQL);
                if(rst.next())
                {
                    if(rst.getInt("ne_numRegNotPed")<=1)
                    {
                        intCodTipDocNotPed=rst.getInt("co_tipDocNotPed");
                        intCodDocNotPed=rst.getInt("co_docNotPed");
                    }
                    else
                    {
                        intCodTipDocNotPed=0;
                        intCodDocNotPed=0;
                    }
                }
                
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }            
        }
        catch (java.sql.SQLException e){    blnRes=false;    objUti.mostrarMsgErr_F1(this, e);    }
        catch (Exception e){  blnRes=false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
    }     
    
    private void mostrarFormularioIngresoImportacion(int fila)
    {
        try
        {
            ZafImp03_02 objImp03_02;
            objImp03_02=new ZafImp03_02(objParSis);//datos de nota de pedido
            
            objImp03_02.setCodigoTipoDocumentoNotaPedido(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString()));
            objImp03_02.setCodigoDocumentoNotaPedido(Integer.parseInt(objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString()));
            objImp03_02.cargarDocumentos();
            
            if(objImp03_02.getNumeroRegistros()==1)
            {
                //se carga el programa Ingreso Importacion.
                Compras.ZafCom77.ZafCom77 objCom_77=new Compras.ZafCom77.ZafCom77(objParSis, objImp03_02.getCodigoEmpresaIngresoImportacion(), objImp03_02.getCodigoLocalIngresoImportacion(), objImp03_02.getCodigoTipoDocumentoIngresoImportacion(), objImp03_02.getCodigoDocumentoIngresoImportacion());
                this.getParent().add(objCom_77,javax.swing.JLayeredPane.DEFAULT_LAYER);
                //se limpia el objeto
                objCom_77.setVisible(true);
                objCom_77=null;
            }
            else
            {
                this.getParent().add(objImp03_02,javax.swing.JLayeredPane.DEFAULT_LAYER);
                objImp03_02.setVisible(true);
            }
        }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);   }
    }    
 
       
    //<editor-fold defaultstate="collapsed" desc="/* Botón Muestra Documentos de Ajustes */">
    private void mostrarFormularioDocumentoAjuste(int fila)
    {
        try
        {
            if(getDocumentosAjuste(fila))
            {
                if(intRowsDocAju==1)
                {
                    con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con != null)
                    {
                        //Se carga el Ajuste de Inventario
                        objAjuInv = new ZafAjuInv( javax.swing.JOptionPane.getFrameForComponent(this), objParSis, con, intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, objImp.INT_COD_MNU_PRG_AJU_INV, 'c'); 
                        objAjuInv.show();
                        //this.getParent().add(objAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                        //objAjuInv.setVisible(true);
                        objAjuInv=null; 
                              
                        con.close();
                        con=null;
                    }
                }
                else
                {
                    //Se carga el seguimiento de documento de ajustes.
                    objSegAjuInv=new ZafSegAjuInv(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis, strSQL, false);
                    objSegAjuInv.show();
                    objSegAjuInv=null;
                                        
                    //this.getParent().add(objSegAjuInv,javax.swing.JLayeredPane.DEFAULT_LAYER);
                    //objSegAjuInv.setVisible(true);
                }
            }
        }
        catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
        catch (Exception e){    objUti.mostrarMsgErr_F1(this, e);   }
    }   
    //</editor-fold>
    
    
    private boolean getDocumentosAjuste(int fila)
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.fe_doc, a1.tx_numDoc2";
                strSQL+="      , 0 as co_itmMae, 0 as nd_Can, 0 as nd_canSol, 0 as nd_CanTrs, a.st_Aut ";     
                strSQL+=" FROM ( ";
                strSQL+="    SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="        , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel ";
                strSQL+="        , a1.st_aut ";
                //strSQL+="        , CASE WHEN a1.st_ingImp IS NULL THEN 'P' ELSE CASE WHEN a1.st_ingImp ='T' THEN 'A'";
                //strSQL+="         ELSE CASE WHEN a1.st_ingImp IN ('D','C') THEN 'D' ELSE 'P' END END END AS st_aut ";    
                strSQL+="    FROM tbr_cabMovInv as a ";
                strSQL+="    INNER JOIN tbm_cabMovInv as a1 ON a1.co_emp=a.co_emp AND a1.co_loc=a.co_loc AND a1.co_tipDoc=a.co_tipDoc AND a1.co_doc=a.co_doc ";
                strSQL+="    INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc ";
                strSQL+="    WHERE a1.co_tipDoc in (select co_tipDoc from tbr_tipDocPrg where co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+="                           and co_loc=" + objParSis.getCodigoLocal()+" and co_mnu= "+objImp.INT_COD_MNU_PRG_AJU_INV+") ";
                strSQL+="    AND a1.st_reg NOT IN('E'/*,'I'*/) ";  //Se comenta para que presente los ajustes denegados, los mismos que tienen st_Reg='I' 31Ago2017
                strSQL+="    GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar ";
                strSQL+="           , a1.ne_numDoc, a1.fe_doc, a.co_empRel, a.co_locRel, a.co_tipDocRel, a.co_docRel, a1.st_ingImp ";
                strSQL+=" ) as a ";
                strSQL+=" INNER JOIN tbm_CabMovInv as a1 ON a1.co_emp=a.co_empRel AND a1.co_loc=a.co_locRel AND a1.co_tipDoc=a.co_tipDocRel AND a1.co_doc=a.co_docRel ";
                strSQL+=" INNER JOIN tbm_detPedEmbImp as a2 ON a2.co_emp=a1.co_empRelPedEmbImp AND a2.co_loc=a1.co_locRelPedEmbImp AND a2.co_tipDoc=a1.co_tipDocRelPedEmbImp AND a2.co_doc=a1.co_docRelPedEmbImp "; 
                strSQL+=" INNER JOIN tbm_detNotPedImp AS a3 ON a3.co_emp=a2.co_emp AND a3.co_loc=a2.co_locrel AND a3.co_tipdoc=a2.co_tipDocrel AND a3.co_doc=a2.co_docrel AND a3.co_reg=a2.co_regrel ";
                strSQL+=" WHERE a3.co_emp=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_EMP); 
                strSQL+=" AND a3.co_loc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC);
                strSQL+=" AND a3.co_tipDoc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC);
                strSQL+=" AND a3.co_doc=" + objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC);
                strSQL+=" GROUP BY a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc, a.tx_desCor, a.tx_desLar, a.ne_numDoc, a.fe_doc, a1.tx_numDoc2, a.st_Aut";
                strSQL+=" ORDER BY a1.tx_numDoc2, a.co_emp, a.co_loc, a.co_tipDoc, a.co_Doc";
                rst=stm.executeQuery(strSQL);
                intRowsDocAju=0;
                while(rst.next())
                {
                    intCodEmpAju = rst.getInt("co_emp");
                    intCodLocAju = rst.getInt("co_loc");
                    intCodTipDocAju = rst.getInt("co_tipDoc");
                    intCodDocAju = rst.getInt("co_doc");
                    intRowsDocAju=rst.getRow();
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                con.close();
                con=null;
            }
        }
        catch (java.sql.SQLException e) {   blnRes= false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e){  blnRes= false;   objUti.mostrarMsgErr_F1(this, e);  }
        return blnRes;
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
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg()
    {
        boolean blnRes=true;
        String strFilFec="";
        try
        {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            strAux="";
            if (con!=null)
            {
                stm=con.createStatement();
                //fecha de documento
                if(objSelFecDoc.isCheckBoxChecked()){
                    switch (objSelFecDoc.getTipoSeleccion())
                    {
                        case 0: //Búsqueda por rangos
                            strFilFec+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strFilFec+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strFilFec+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                strSQL="";
                strSQL+=" SELECT  b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_desCor, b1.tx_desLar";
                strSQL+="       , b1.ne_numDoc, b1.tx_numDoc2, b1.fe_doc, b1.co_mesemb, b1.fe_mesEmb, b1.co_mesarr, b1.fe_mesArr";
                strSQL+="	, b1.co_exp, b1.tx_nomExp, b1.nd_valDoc, b1.nd_valCarPag, b1.st_reg, b1.st_cie, b1.st_notpedlis, b3.st_tieAju";
                strSQL+="	, b2.co_emp AS co_empPedEmb, b2.co_locRel AS co_locRelPedEmb, b2.co_tipDocRel AS co_tipDocRelPedEmb, b2.co_docRel AS co_docRelPedEmb";
                strSQL+="	, b3.co_emp AS co_empIngImp, b3.co_locRel AS co_locRelIngImp, b3.co_tipDocRel AS co_tipDocRelIngImp, b3.co_docRel AS co_docRelIngImp";
                strSQL+=" FROM(";
                strSQL+="	SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc, a1.ne_aniemb, a1.co_mesemb";
                strSQL+="	      , (  CASE WHEN a1.ne_aniemb IS NOT NULL THEN (a1.ne_aniemb) WHEN a1.ne_aniemb IS NULL THEN EXTRACT('year' FROM a1.fe_doc) END || '-' ||";			
                strSQL+="		   CASE WHEN a1.co_mesemb IN(1,2)   THEN '01' WHEN a1.co_mesemb IN(3,4)   THEN '02'";
                strSQL+="			WHEN a1.co_mesemb IN(5,6)   THEN '03' WHEN a1.co_mesemb IN(7,8)   THEN '04'";
                strSQL+="			WHEN a1.co_mesemb IN(9,10)  THEN '05' WHEN a1.co_mesemb IN(11,12) THEN '06'";
                strSQL+="			WHEN a1.co_mesemb IN(13,14) THEN '07' WHEN a1.co_mesemb IN(15,16) THEN '08'";
                strSQL+="			WHEN a1.co_mesemb IN(17,18) THEN '09' WHEN a1.co_mesemb IN(19,20) THEN '10'";
                strSQL+="			WHEN a1.co_mesemb IN(21,22) THEN '11' WHEN a1.co_mesemb IN(23,24) THEN '12'";
                strSQL+="		   END || '-' || '01' ) AS fe_mesEmb";
                strSQL+="	      , a1.co_mesarr";
                strSQL+="	      , (  CASE WHEN a1.ne_aniemb IS NOT NULL THEN (a1.ne_aniemb) WHEN a1.ne_aniemb IS NULL THEN EXTRACT('year' FROM a1.fe_doc) END || '-' ||";				
                strSQL+="		   CASE WHEN a1.co_mesarr IN(1,2)   THEN '01' WHEN a1.co_mesarr IN(3,4)   THEN '02'";
                strSQL+="			WHEN a1.co_mesarr IN(5,6)   THEN '03' WHEN a1.co_mesarr IN(7,8)   THEN '04'";
                strSQL+="			WHEN a1.co_mesarr IN(9,10)  THEN '05' WHEN a1.co_mesarr IN(11,12) THEN '06'";
                strSQL+="			WHEN a1.co_mesarr IN(13,14) THEN '07' WHEN a1.co_mesarr IN(15,16) THEN '08'";
                strSQL+="			WHEN a1.co_mesarr IN(17,18) THEN '09' WHEN a1.co_mesarr IN(19,20) THEN '10'";
                strSQL+="			WHEN a1.co_mesarr IN(21,22) THEN '11' WHEN a1.co_mesarr IN(23,24) THEN '12'";
                strSQL+="		    END || '-' || '01' ) AS fe_mesArr";
                strSQL+="	      , a5.co_exp, a3.tx_nom as tx_nomExp, a1.nd_valDoc, SUM(a4.nd_valCarPag) AS nd_valCarPag, a1.st_reg, a1.st_cie, a1.st_notpedlis";
                strSQL+="	FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_cabTipDoc AS a2 ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                strSQL+="	INNER JOIN tbm_detNotPedImp AS a5 ON a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipdoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc";                
                strSQL+="	INNER JOIN tbm_expImp AS a3 ON a5.co_exp=a3.co_exp";
                strSQL+="	INNER JOIN tbm_carPagNotPedImp AS a4 ON a1.co_emp=a4.co_emp AND a1.co_loc=a4.co_loc AND a1.co_tipdoc=a4.co_tipDoc AND a1.co_doc=a4.co_doc";
                strSQL+="	WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+="	AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+="       " + strFilFec;

                strAux=txtCodImp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a1.co_imp=" + strAux + "";
                strAux=txtCodExp.getText();
                if (!strAux.equals(""))
                    strSQL+=" AND a5.co_exp=" + strAux + "";
                
                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos exepto los eliminados
                    strSQL+=" AND a1.st_reg NOT IN ('E')";
                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                    strSQL+=" AND a1.st_reg IN ('A')";
                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los inactivos
                    strSQL+=" AND a1.st_reg IN ('I')";

                strSQL+="	AND a4.co_carPag IN(1,4,9)";
                strSQL+="	GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a2.tx_desCor, a2.tx_desLar, a1.ne_numDoc, a1.tx_numDoc2, a1.fe_doc";
                strSQL+="	       , a1.ne_aniemb, a1.co_mesemb, a1.co_mesarr, a5.co_exp, a3.tx_nom, a1.nd_valDoc, a1.st_reg, a1.st_cie, a1.st_notpedlis";
                strSQL+=" ) AS b1";
                //Pedidos Embarcados.
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+="	SELECT a3.co_emp, /*a3.co_loc, a3.co_tipDoc, a3.co_doc,*/ a3.co_locRel, a3.co_tipDocRel, a3.co_docRel";
                strSQL+="	FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="	INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipDocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQL+="	INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+="	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+="	WHERE a4.st_reg NOT IN('E','I')";
                strSQL+="	GROUP BY a3.co_emp, /*a3.co_loc, a3.co_tipDoc, a3.co_doc,*/ a3.co_locRel, a3.co_tipDocRel, a3.co_docRel";
                strSQL+=" ) AS b2";
                strSQL+=" ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_locRel AND b1.co_tipDoc=b2.co_tipDocRel AND b1.co_doc=b2.co_docRel";
                //Ingresos por Importación.
                strSQL+=" LEFT OUTER JOIN(";
                strSQL+=" 	SELECT a3.co_emp, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel ";
                strSQL+="	      ,CASE WHEN a5.st_cieAjuInvIngImp IS NULL THEN 'N' ELSE a5.st_cieAjuInvIngImp END as st_tieAju";
                strSQL+="	FROM tbm_cabNotPedImp AS a1 INNER JOIN tbm_detNotPedImp AS a2";
                strSQL+="	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+="	INNER JOIN tbm_detPedEmbImp AS a3";
                strSQL+="	ON a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_locrel AND a2.co_tipdoc=a3.co_tipDocrel AND a2.co_doc=a3.co_docrel AND a2.co_reg=a3.co_regrel";
                strSQL+="	INNER JOIN tbm_cabPedEmbImp AS a4";
                strSQL+="	ON a3.co_emp=a4.co_emp AND a3.co_loc=a4.co_loc AND a3.co_tipDoc=a4.co_tipDoc AND a3.co_doc=a4.co_doc";
                strSQL+="	INNER JOIN tbm_cabMovInv AS a5";
                strSQL+="	ON a4.co_emp=a5.co_empRelPedEmbImp AND a4.co_loc=a5.co_locRelPedEmbImp";
                strSQL+="	AND a4.co_tipDoc=a5.co_tipDocRelPedEmbImp AND a4.co_doc=a5.co_docRelPedEmbImp";
                strSQL+="	WHERE a4.st_reg NOT IN('E','I') AND a5.st_reg NOT IN('E','I')";
                strSQL+="	GROUP BY a3.co_emp, a3.co_locRel, a3.co_tipDocRel, a3.co_docRel, a5.st_cieAjuInvIngImp ";
                strSQL+=" ) AS b3";                
                strSQL+=" ON b1.co_emp=b3.co_emp AND b1.co_loc=b3.co_locRel AND b1.co_tipDoc=b3.co_tipDocRel AND b1.co_doc=b3.co_docRel";
                strSQL+=" GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.tx_desCor, b1.tx_desLar";
                strSQL+="        , b1.ne_numDoc, b1.tx_numDoc2, b1.fe_doc, b1.co_mesemb, b1.fe_mesEmb, b1.co_mesarr, b1.fe_mesArr";
                strSQL+="	 , b1.co_exp, b1.tx_nomExp, b1.nd_valDoc, b1.nd_valCarPag, b1.st_reg, b1.st_cie, b1.st_notpedlis";
                strSQL+="	 , b2.co_emp, b2.co_locRel, b2.co_tipDocRel, b2.co_docRel";
                strSQL+="	 , b3.co_emp, b3.co_locRel, b3.co_tipDocRel, b3.co_docRel, b3.st_tieAju";
                strSQL+=" ORDER BY b1.tx_numDoc2";
                System.out.println("cargarDetReg: "+strSQL);
                rst=stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                while (rst.next())
                {
                    if (blnCon)
                    {
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));       //co_empNotPed
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));       //co_locNotPed
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));    //co_tipDocPedEmb
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));    //tx_desCorTipDocNotPed
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));    //tx_desCorTipDocNotPed
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));       //co_docNotPed
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("tx_numDoc2"));   //tx_numDoc2
                        vecReg.add(INT_TBL_DAT_FEC_DOC,         rst.getString("fe_doc"));       //fe_doc
                        vecReg.add(INT_TBL_DAT_FEC_EMB,         rst.getString("fe_mesEmb"));    //fe_emb
                        vecReg.add(INT_TBL_DAT_FEC_ARR,         rst.getString("fe_mesArr"));    //fe_arr
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));       //co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));    //tx_nomExp
                        vecReg.add(INT_TBL_DAT_TOT_FOB_CFR,     rst.getString("nd_valDoc"));    //nd_valDoc
                        vecReg.add(INT_TBL_DAT_TOT_ARA_FOD_IVA, rst.getString("nd_valCarPag")); //nd_valCarPag
                        vecReg.add(INT_TBL_DAT_EST,             rst.getString("st_reg"));       //st_reg
                        vecReg.add(INT_TBL_DAT_CHK_LIS,         null);                          //Chk.Not.Ped.Lis.
                        vecReg.add(INT_TBL_DAT_CHK_CIE,         (rst.getString("st_cie").equals("S")?true:false));//st_cie
                        vecReg.add(INT_TBL_DAT_BUT,             null);                          //Btn.Not.Ped.
                        vecReg.add(INT_TBL_DAT_CHK_PED_EMB,     null);                          //Tie.Ped.Emb.
                        vecReg.add(INT_TBL_DAT_BUT_PED_EMB,     null);                          //Btn.Ped.Emb.
                        vecReg.add(INT_TBL_DAT_CHK_ING_IMP,     null);                          //Chk Ing.Imp.
                        vecReg.add(INT_TBL_DAT_BUT_ING_IMP,     null);                          //Btn.Ing.Imp.
                        vecReg.add(INT_TBL_DAT_CHK_DOC_AJU,     null);                          //Chk.Doc.Aju.
                        vecReg.add(INT_TBL_DAT_BUT_DOC_AJU,     null);                          //Btn.Doc.Aju.
                        
                        if(!  (  (rst.getObject("st_notpedlis")==null)  || (rst.getString("st_notpedlis").equals("N"))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_LIS);
                        
                        if(!  (  (rst.getObject("co_empPedEmb")==null)  || (rst.getString("co_empPedEmb").equals(""))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_PED_EMB);
                        
                        if(!  (  (rst.getObject("co_empIngImp")==null)  || (rst.getString("co_empIngImp").equals(""))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_ING_IMP);
                        
                        if(!  (  (rst.getObject("st_tieAju")==null)  || (rst.getString("st_tieAju").equals("N"))  )  )
                            vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_DOC_AJU);      
                        
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
                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        }
        catch (java.sql.SQLException e) {    blnRes=false;  objUti.mostrarMsgErr_F1(this, e);   }
        catch (Exception e)    {    blnRes=false;      objUti.mostrarMsgErr_F1(this, e);   }
        return blnRes;
    }
    
    
    
    
    
}