/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Importaciones.ZafImp13;

import Importaciones.ZafImp04.ZafImp04;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.lowagie.text.Element;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author Bostel
 */
public class ZafImp13 extends javax.swing.JInternalFrame {

    
    //Constantes Columnas JTable
    static final int INT_TBL_DAT_LIN=0;
    static final int INT_TBL_DAT_COD_EMP=1;
    static final int INT_TBL_DAT_COD_LOC=2;
    static final int INT_TBL_DAT_COD_TIP_DOC=3;
    static final int INT_TBL_DAT_DES_COR_TIP_DOC=4;
    static final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;
    static final int INT_TBL_DAT_COD_DOC=6;
    static final int INT_TBL_DAT_NUM_DOC=7;
    static final int INT_TBL_DAT_NUM_PED=8;
    static final int INT_TBL_DAT_FE_DOC=9;
    static final int INT_TBL_DAT_FEC_EMB=10;
    static final int INT_TBL_DAT_FEC_ARR=11;
    static final int INT_TBL_DAT_COD_EXP=12;
    static final int INT_TBL_DAT_NOM_EXP=13;
    static final int CargoaPagar=16;
    //static final int INT_TBL_DAT_BUT=13;
    
    private static final int INT_NUM_TOT_CES=14;                                 //Número total de columnas estáticas.
    
    
    
    //variables
     private ZafSelFec objSelFecDoc;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafImp objImp;
    private ZafPerUsr objPerUsr;
    private ZafVenCon vcoEmp, vcoExp;
    private Vector vecDat, vecCab, vecReg, vecAux;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;
    private ZafMouMotAda objMouMotAda;
    private ZafTblBus objTblBus;
    private ZafTblFilCab objTblFilCab;
    private ZafTblCelRenLbl objTblCelRenLbl;
    private ZafTblOrd objTblOrd;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private String strCodEmp, strNomEmp, strCodExp,  strNomExp;
    private String strSQL, strAux;
    private boolean blnCon;
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private java.util.Date datFecAux;
    private int  intNumColEst;
    private ZafTblCelEdiButGen objTblCelEdiBut;
    private int intNumColAddCarPag, intNumColIniCarPag, intNumColFinCarPag;
    private ZafTblCelRenLbl objTblCelRenLblCarPag;

    
    private ArrayList arlRegTotCarPag, arlDatTotCarPag;
    final int INT_ARL_TOT_CAR_PAG_TIP=0;
    final int INT_ARL_TOT_CAR_PAG_NOM=1;
    final int INT_ARL_TOT_CAR_PAG_COL=2;
    final int INT_ARL_TOT_CAR_PAG_SUM=3;
    
    
    private ArrayList arlDatCarPag, arlRegCarPag;
    final int INT_ARL_CAR_PAG_COD_CAR_PAG=0;
    final int INT_ARL_CAR_PAG_NOM_CAR_PAG=1;
    final int INT_ARL_CAR_PAG_TIP_CAR_PAG=2;
    final int INT_ARL_CAR_PAG_COL_CAR_PAG=3;

    private String strVer=" v0.1";
    /**
     * Creates new form ZafImp13
     */
    /** Crea una nueva instancia de la clase ZafIndRpt. */
    public ZafImp13(ZafParSis obj) {
        try{
            objParSis=(ZafParSis)obj.clone();
            arlDatCarPag=new ArrayList();
            if (objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                if(!configurarFrm())
                    exitForm();
            }
            else{
              mostrarMsgInf("Este programa sólo puede ser ejecutado desde GRUPO.");
                dispose();  
            }
                    
            
        }catch(CloneNotSupportedException e){
           this.setTitle(this.getTitle() + " [ERROR]");
        }
        
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
    
    //Configurar el Formulario
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Titulo Programa.
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + strVer);
            lblTit.setText(strAux);
            
            //Inicializar objetos.
            objUti=new ZafUtil();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            
            //Obtener los permisos del usuario.
            objPerUsr=new ZafPerUsr(objParSis);
            
            //Habilitar/Inhabilitar las opciones según el perfil del usuario.
            if (!objPerUsr.isOpcionEnabled(3236)) //Consultar
            {
                butCon.setVisible(false);
            }
            if (!objPerUsr.isOpcionEnabled(3237)) //Cerrar
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
            if(objParSis.getCodigoEmpresa()==objParSis.getCodigoEmpresaGrupo()){
                configurarVenConEmp();
                txtCodImp.setEditable(true);
                txtCodExp.setEditable(true);
                butImp.setEnabled(true);
            }
            else{
                lblEmpImp.setVisible(false);
                txtCodImp.setVisible(false);
                txtCodExp.setVisible(false);
                butImp.setVisible(false);
            }
                        
            //Configurar los JTables.
           configurarTblDat();
           configurarVenConExp();
           cboEstDoc.setSelectedIndex(1);
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCodExp = new javax.swing.JTextField();
        txtNomExp = new javax.swing.JTextField();
        txtCodImp = new javax.swing.JTextField();
        txtNomImp = new javax.swing.JTextField();
        cboEstDoc = new javax.swing.JComboBox<>();
        butImp = new javax.swing.JButton();
        butExp = new javax.swing.JButton();
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
        setTitle("Titulo de la ventana");
        setPreferredSize(new java.awt.Dimension(468, 672));
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosing(evt);
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

        lblTit.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Titulo de la Ventana");
        getContentPane().add(lblTit, java.awt.BorderLayout.NORTH);

        panFrm.setLayout(new java.awt.BorderLayout());

        tabFrm.setToolTipText("");
        tabFrm.setPreferredSize(new java.awt.Dimension(475, 311));

        panCon.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 80));

        javax.swing.GroupLayout panFecDocLayout = new javax.swing.GroupLayout(panFecDoc);
        panFecDoc.setLayout(panFecDocLayout);
        panFecDocLayout.setHorizontalGroup(
            panFecDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 652, Short.MAX_VALUE)
        );
        panFecDocLayout.setVerticalGroup(
            panFecDocLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 80, Short.MAX_VALUE)
        );

        panCon.add(panFecDoc, java.awt.BorderLayout.PAGE_START);

        panFil.setMinimumSize(new java.awt.Dimension(0, 0));
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
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(12, 7, 620, 23);

        bgrFil.add(optFil);
        optFil.setText("Solo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(12, 30, 620, 23);

        lblEmpImp.setText("Empresa(Importador):");
        lblEmpImp.setToolTipText("Vendedor/Comprador");
        panFil.add(lblEmpImp);
        lblEmpImp.setBounds(40, 60, 140, 20);

        jLabel3.setText("Exportador:");
        panFil.add(jLabel3);
        jLabel3.setBounds(40, 80, 140, 20);

        jLabel4.setText("Estado de Documento:");
        panFil.add(jLabel4);
        jLabel4.setBounds(40, 100, 140, 20);

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

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "A:Activo", "I:Inactivo" }));
        panFil.add(cboEstDoc);
        cboEstDoc.setBounds(180, 100, 210, 20);

        butImp.setText("...");
        butImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butImpActionPerformed(evt);
            }
        });
        panFil.add(butImp);
        butImp.setBounds(610, 60, 20, 20);

        butExp.setText("...");
        butExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpActionPerformed(evt);
            }
        });
        panFil.add(butExp);
        butExp.setBounds(610, 80, 20, 20);

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
        ));
        spnDat.setViewportView(tblDat);

        panRpt.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);
        tabFrm.getAccessibleContext().setAccessibleName("Filtro");

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

        getContentPane().add(panBar, java.awt.BorderLayout.PAGE_END);

        setBounds(0, 0, 667, 448);
    }// </editor-fold>//GEN-END:initComponents

    private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_optFilActionPerformed

    private void butImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butImpActionPerformed
        strCodEmp=txtCodImp.getText();
        mostrarVenConEmp(0);
    }//GEN-LAST:event_butImpActionPerformed

    private void butExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpActionPerformed
        strCodExp=txtCodExp.getText();
        mostrarVenConExp(0);
    }//GEN-LAST:event_butExpActionPerformed

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

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm(null);
    }//GEN-LAST:event_formInternalFrameClosing

    private void txtCodExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodExpActionPerformed
        txtCodExp.transferFocus();
    }//GEN-LAST:event_txtCodExpActionPerformed

    private void txtCodExpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusGained
         strCodExp=txtCodExp.getText();
        txtCodExp.selectAll();
    }//GEN-LAST:event_txtCodExpFocusGained

    private void txtCodExpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodExpFocusLost
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
        if (txtCodExp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtCodExpFocusLost

    private void txtCodImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodImpActionPerformed
        txtCodImp.transferFocus();
    }//GEN-LAST:event_txtCodImpActionPerformed

    private void txtCodImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusGained
        strCodEmp=txtCodImp.getText();
        txtCodImp.selectAll();
    }//GEN-LAST:event_txtCodImpFocusGained

    private void txtCodImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodImpFocusLost
         if (!txtCodImp.getText().equalsIgnoreCase(strCodEmp)){
            if (txtCodImp.getText().equals("")){
                txtCodImp.setText("");
                txtNomImp.setText("");
                objTblMod.removeAllRows();
                //txtCodExp.setText("");
                //txtNomExp.setText("");
            }
            else
                mostrarVenConEmp(1);
        }
        else
            txtCodImp.setText(strCodEmp);
          if (txtCodImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
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
         if (txtNomImp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomImpFocusLost

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
         if (txtNomExp.getText().length()>0)
        {
            optFil.setSelected(true);
            optTod.setSelected(false);
        }
    }//GEN-LAST:event_txtNomExpFocusLost

    private void optTodItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_optTodItemStateChanged
        if(optTod.isSelected()){
            txtCodImp.setText("");
            txtNomImp.setText("");
            txtCodExp.setText("");
            txtNomExp.setText("");   
            cboEstDoc.setSelectedIndex(1);
        }
    }//GEN-LAST:event_optTodItemStateChanged

    private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
        if (optTod.isSelected())
            optFil.setSelected(false);
    }//GEN-LAST:event_optTodActionPerformed
    private void exitForm() 
    {
        dispose();
    }   
    //Metodo para configurar las columnas de los cargos de Importaciones
    private boolean configurarColumnasCargosImportacion()
    {
        boolean blnRes= false;
       try {
           //Metodo para eliminar columnas adicionas cargadas previamente 
            if(eliminaColumnasAdicionadas()){
                 intNumColAddCarPag=numeroColumnasAddCarPag();//Obtengo el numero de columas que voy adicionar del cargo a pagar 
                if(agregarColumnasAdicionadasCargos()){
                           blnRes=true;
               }
           }

       } catch (Exception e) {
           blnRes=false;         
           objUti.mostrarMsgErr_F1(this, e);
       }

      return blnRes;
    }
    //Metodo para obtener los valores de los cargos a pagar del pedido
    private boolean getValorCargosPagarPedido(){
        boolean blnRes=true;
        String strCodEmp="";
        String strCodLoc="";
        String strCodTipDoc="";
        String strCodDoc="";
        int intColCarPag=-1;
        int co_carpag;
        BigDecimal bdeValCarPag=BigDecimal.ZERO;
        BigDecimal bd1=BigDecimal.ZERO;
        BigDecimal Num = new BigDecimal(100);
        BigDecimal bdeSumTotValCfr=BigDecimal.ZERO;
        try{
            if(con!=null){
                datFecAux=objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos());
                System.out.println("arlDatCarPag: " + arlDatCarPag.toString());
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strCodEmp=(objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_EMP).toString());
                    strCodLoc=(objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_LOC).toString());
                    strCodTipDoc=(objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString());
                    strCodDoc=(objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_DOC).toString());
                    
                    stm=con.createStatement();
                    
                    for(int j=0; j<arlDatCarPag.size(); j++){
                        intColCarPag=objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COL_CAR_PAG);
                        strSQL="";
                        strSQL+=" (";
                        strSQL+=" (SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg, a1.co_carPag, a1.nd_valCarPag,sum(a3.nd_valcfr) as ISD,a2.tx_tipCarPag ";
                        strSQL+="    FROM tbm_carPagPedEmbImp AS a1 INNER JOIN tbm_detpedembimp as a3 on";
                        strSQL+=" a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc and a1.co_doc=a3.co_doc ";
                        strSQL+="   INNER JOIN tbm_carPagImp AS a2 ON a1.co_carPag=a2.co_carPag ";
                        strSQL+=" WHERE a1.co_emp=" + strCodEmp + "";
                        strSQL+=" AND a1.co_loc=" + strCodLoc + "";
                        strSQL+=" AND a1.co_tipDoc=" + strCodTipDoc + "";
                        strSQL+=" AND a1.co_doc=" + strCodDoc + "";
                        strSQL+=" AND a1.co_carPag=" + objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COD_CAR_PAG) + "";
                        strSQL+="   group by a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.co_reg,a2.tx_tipCarPag    )";
                        strSQL+=" )";
                        rst=stm.executeQuery(strSQL);
                        if(rst.next()){
                            //Obtengo el valor del campo nd_valCarPag  
                            bdeValCarPag=new BigDecimal(rst.getObject("nd_valCarPag")==null?"0":(rst.getString("nd_valCarPag").equals("")?"":rst.getString("nd_valCarPag"))); 
                            // Le asigno el codigo del cargo a pagar a la variable co_carpag
                            co_carpag= objUti.getIntValueAt(arlDatCarPag, j, INT_ARL_CAR_PAG_COD_CAR_PAG) ;
                            //Le indico cuando debe obtener el valor de la columna ISD y realizar la operacion
                            if(co_carpag == 23){
                                bdeSumTotValCfr= new BigDecimal(rst.getObject("ISD")==null?"0":(rst.getString("ISD").equals("")?"":rst.getString("ISD")));
                                //bd1 = bdeSumTotValCfr.multiply(objImp.getPorcentajeISD().divide(Num));
                                bd1 = bdeSumTotValCfr.multiply(objImp.getPorcentajeISD(objParSis.getCodigoEmpresaGrupo(), 1));//, objImp.INT_COD_CFG_IMP_ISD, datFecAux iba dentro del metodo se saco marcaba error
                                //Agrego a la tabla el valor obtenido redondeado
                                objTblMod.setValueAt(objUti.redondearBigDecimal(bd1, 2), i, intColCarPag);
                                break;
                            }
                            //Asigno el valor de los otros impuestos a la tabla redondeado
                            objTblMod.setValueAt(objUti.redondearBigDecimal(bdeValCarPag, 2), i, intColCarPag);
                        }
                    }
                    stm.close();
                    stm=null;
                    rst.close();
                    rst=null;
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
    
    
    private boolean cargarReg(){
        boolean blnRes=true;
        try{           
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                if(configurarColumnasCargosImportacion()){
                    if(cargarDat()){
                        if(getValorCargosPagarPedido()){
                            objTblMod.initRowsState();
                            if (blnCon)
                                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                            else
                                lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                            butCon.setText("Consultar");
                            pgrSis.setIndeterminate(false);
                        }                        
                    }                    
                }
                con.close();
                con=null;
            }
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }  
     private boolean agregarColumnasAdicionadasCargos(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
      javax.swing.table.TableColumn tbc1;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*4);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
        ZafTblHeaColGrp objTblHeaColGrpFecEstEmb=null;
        intNumColIniCarPag=intNumColEst;//numero de columnas que tiene el modelo antes de adicionar las columnas
        String strNomCarPag="";
        try{
            objTblCelRenLblCarPag=new ZafTblCelRenLbl();
            objTblCelRenLblCarPag.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCarPag.setTipoFormato(objTblCelRenLblCarPag.INT_FOR_NUM);
            objTblCelRenLblCarPag.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
                   
            objTblCelRenLblCarPag.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstItmEli="";
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru=new java.awt.Color(255,221,187);//228,228,203
                }
            });
            
            for (int i=0; i<intNumColAddCarPag; i++){
                strNomCarPag="" + objUti.getStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_NOM_CAR_PAG);
                    
                objUti.setStringValueAt(arlDatCarPag, i, INT_ARL_CAR_PAG_COL_CAR_PAG, "" + (intNumColIniCarPag+i));                
                tbc=new javax.swing.table.TableColumn(intNumColIniCarPag+i);
               // tbc1=new javax.swing.table.TableColumn(intNumColIniCarPag+1);
                //tbc.setHeaderValue("<HTML><DIV ALIGN=center>" + strNomPed + "<BR>" + strFecEstPedEmb + "</DIV></HTML>");
                //tbc.setHeaderValue(strNomPed);
               // String strBtn="Pago del Impuesto";
                tbc.setHeaderValue(strNomCarPag);
               //tbc1.setHeaderValue(strBtn);
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(60);
                //tbc1.setPreferredWidth(125);
                //Configurar JTable: Establecer columnas editables.
//                vecAux=new Vector();
//                vecAux.add("" + INT_TBL_DAT_BUT);
//                objTblMod.setColumnasEditables(vecAux);
//                vecAux=null;
             //  objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
////                
              objTblCelRenBut=new ZafTblCelRenBut();
              //tbc1.setCellRenderer(objTblCelRenBut);
            //tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
             objTblCelEdiButGen=new ZafTblCelEdiButGen();
               //tbc1.setCellEditor(objTblCelEdiButGen);
           // tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
//            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    intFilSel=tblDat.getSelectedRow();
//                }
//                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    //mostrarFormularioPedido(intFilSel);
//                    System.out.println("Hello World");
//                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                }
//            });
            
            
            
            //objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                //tbc1.setResizable(false);
                //Configurar JTable: Renderizar celdas.
               tbc.setCellRenderer(objTblCelRenLblCarPag);
             
//                tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
//                objTblCelEdiButGen=new ZafTblCelEdiButGen();
//                tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
              
               //tbc1.setCellRenderer(objTblFilCab);
               // tbc1.setCellEditor(objTblCelEdiBut);
                //Configurar JTable: Agregar la columna al JTable.
                objTblMod.addColumn(tblDat,tbc);
               // objTblMod.addColumn(tblDat,tbc1);
                if(i==0){
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Impuestos a Pagar");
                    objTblHeaColGrpTit.setHeight(16);
                    objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
                }
                objTblHeaColGrpTit.add(tbc);
               // objTblHeaColGrpTit.add(tbc1);
            }
            
          
            intNumColFinCarPag=objTblMod.getColumnCount();

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;
            objTblHeaColGrpFecEstEmb=null;


        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }
    
    
    private int numeroColumnasAddCarPag(){
        String strLin;
        int intNumColAdd=0;
        arlDatCarPag.clear();
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                //Armar la sentencia SQL.
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT a1.co_carpag, a1.tx_nom, a1.tx_tipcarpag, a1.tx_obs1, a1.st_reg";
                strSQL+=" FROM tbm_carpagimp AS a1";
                strSQL+=" WHERE a1.st_imp = 'S'";
                strSQL+=" ORDER BY a1.ne_ubi";
                //System.out.println("numeroColumnasAddCarPag: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegCarPag=new ArrayList();
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COD_CAR_PAG, rst.getInt("co_carpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_NOM_CAR_PAG, rst.getString("tx_nom"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_TIP_CAR_PAG, rst.getString("tx_tipcarpag"));
                    arlRegCarPag.add(INT_ARL_CAR_PAG_COL_CAR_PAG, "");
                    arlDatCarPag.add(arlRegCarPag);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }
            intNumColAdd=arlDatCarPag.size();
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return intNumColAdd;
   }
    
 
    
    
     private boolean eliminaColumnasAdicionadas()
    {
        boolean blnRes=true;
        try
        {
            for (int i=(objTblMod.getColumnCount()-1); i>=(intNumColEst); i--)
            {
                objTblMod.removeColumn(tblDat, i);                
            }
        }
        catch(Exception e){  
            objUti.mostrarMsgErr_F1(this, e);  
            System.err.println("eliminaColumnasAdicionadas"); 
            blnRes=false;      }
        return blnRes;
    
    }
     
    private boolean cargarDat(){
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
                    switch (objSelFecDoc.getTipoSeleccion()){
                        case 0: //Búsqueda por rangos
                            strAux+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 1: //Fechas menores o iguales que "Hasta".
                            strAux+=" AND a1.fe_doc<='" + objUti.formatearFecha(objSelFecDoc.getFechaHasta(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 2: //Fechas mayores o iguales que "Desde".
                            strAux+=" AND a1.fe_doc>='" + objUti.formatearFecha(objSelFecDoc.getFechaDesde(), objSelFecDoc.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                            break;
                        case 3: //Todo.
                            break;
                    }
                }
                
                 if (!txtCodImp.getText().equals(""))
                    strAux+=" AND a1.co_imp=" + txtCodImp.getText() + "";
                if (!txtCodExp.getText().equals(""))
                    strAux+=" AND a1.co_exp=" + txtCodExp.getText() + "";
                
                if(cboEstDoc.getSelectedIndex()==0)//se presentan todos exepto los eliminados
                    strAux+=" AND a1.st_reg  IN ('A','E','I')";
                else if(cboEstDoc.getSelectedIndex()==1)//se presentan solo los activos
                    strAux+=" AND a1.st_reg IN ('A')";
                else if(cboEstDoc.getSelectedIndex()==2)//se presentan solo los inactivos
                    strAux+=" AND a1.st_reg IN ('I')";
                
                
               
                   
                //Armar sentencia SQL
                
                 strSQL="";
                strSQL+="      SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a1.co_doc, a1.ne_numDoc      ,  a1.tx_numDoc2, ";
                strSQL+="       a1.fe_doc, a1.fe_emb, a1.co_mesArr, a1.fe_arr, a1.co_mesPue, a1.fe_pue, a1.co_imp, a1.tx_nomImp, a1.co_exp, a1.tx_nomExp  ";
                strSQL+="       FROM( ";
                strSQL+="          SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.fe_doc             , b1.fe_emb, '' AS co_mesArr, ";
                strSQL+="          b1.fe_arr, '' AS co_mesPue,b1.fe_pue, b1.ne_numDoc,  b1.tx_numDoc2, b1.tx_desCor, b1.tx_desLar             , ";
                strSQL+="         b1.co_imp, b1.tx_nomImp, b1.co_exp, b1.tx_nomExp        ";
                strSQL+="           FROM( 	       ";
                    strSQL+="              SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, ''||a1.fe_emb AS fe_emb, ";
                    strSQL+="      ''||a1.fe_arr AS fe_arr, ''||a1.fe_pue AS fe_pue, a1.ne_numDoc,  a1.tx_numDoc2, a6.tx_desCor , ";
                    strSQL+="          a6.tx_desLar  	            , a1.co_imp, a7.tx_nom AS tx_nomImp, a1.co_exp, a8.tx_nom AS tx_nomExp, ";
                    strSQL+="          a1.st_reg,a3.st_imp,a9.st_isdCos	       ";
                    strSQL+="           FROM ( ( (tbm_cabPedEmbImp AS a1 INNER JOIN tbm_expImp AS a8 ON a1.co_exp=a8.co_exp) INNER JOIN tbm_emp AS a7 ON a1.co_imp=a7.co_emp) 				";
                    strSQL+=" 	         INNER JOIN tbm_cabTipDoc AS a6 ON a1.co_emp=a6.co_emp AND a1.co_loc=a6.co_loc AND a1.co_tipDoc=a6.co_tipDoc 	       )           ";
                    strSQL+=" 	     INNER JOIN tbm_detPedEmbImp AS a5 ON (a1.co_emp=a5.co_emp AND a1.co_loc=a5.co_loc AND a1.co_tipDoc=a5.co_tipDoc AND a1.co_doc=a5.co_doc)          ";
                    strSQL+="           INNER JOIN tbm_inv AS a10 ON (a1.co_emp=a10.co_emp AND a5.co_itm=a10.co_itm) INNER JOIN tbm_grpInvImp as a4 on a10.co_grpImp=a4.co_grp";
                    strSQL+=" 	                INNER JOIN tbm_pararaimp as a9 ON a4.co_parara=a9.co_parara  inner JOIN tbm_carpagpedembimp AS a2 ON a5.co_emp=a2.co_emp ";
                    strSQL+=" 	           AND a5.co_loc=a2.co_loc AND a5.co_tipDoc=a2.co_tipdoc AND a5.co_doc=a2.co_doc INNER JOIN tbm_carpagimp as a3 ON a2.co_carpag=a3.co_carpag 	       ";
                    strSQL+="              WHERE a3.st_imp = 'S' AND a9.st_isdCos = 'S' AND a10.tx_codAlt like '%I'        ";
                    strSQL+="          " + strAux;
                    strSQL+=" 	           GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc , a1.fe_emb, a1.fe_arr,a1.fe_pue 	            , ";
                    strSQL+=" 	                a1.ne_numDoc,a1.tx_numDoc2,a6.tx_desCor,a6.tx_desLar,a1.co_imp, a7.tx_nom,a1.co_exp, a8.tx_nom, ";
                    strSQL+=" 	                a1.st_reg,a3.st_imp,a9.st_isdCos 	       ";
                    strSQL+=" 	                ORDER BY co_emp, co_loc, co_tipDoc, co_doc 	     ) AS b1 INNER JOIN( SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_reg, a1.nd_valTotFobCfr 	       ";                    
                    strSQL+=" 	           FROM tbm_detPedEmbImp AS a1        ) AS b2  ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc 	     ";
                    strSQL+=" 	        GROUP BY b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc,b1.fe_doc, b1.fe_emb,b1.fe_arr, b1.fe_pue, ";
                    strSQL+=" 	         b1.ne_numDoc,  b1.tx_numDoc2, b1.tx_desCor, b1.tx_desLar, b1.co_imp,b1.tx_nomImp,b1.co_exp,b1.tx_nomExp,b1.st_reg ) AS a1 ";
                    strSQL+=" 	           ORDER BY a1.fe_emb,a1.co_emp,a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.fe_doc, a1.ne_numDoc,  a1.tx_numDoc2";

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
                        vecReg.add(INT_TBL_DAT_COD_EMP,         rst.getString("co_emp"));//co_emp
                        vecReg.add(INT_TBL_DAT_COD_LOC,         rst.getString("co_loc"));//co_loc
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     rst.getString("co_tipDoc"));//co_tipDoc
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, rst.getString("tx_desCor"));//tx_desCorTipDoc
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, rst.getString("tx_desLar"));//tx_desLarTipDoc
                        vecReg.add(INT_TBL_DAT_COD_DOC,         rst.getString("co_doc"));//co_doc
                        vecReg.add(INT_TBL_DAT_NUM_DOC,         rst.getString("ne_numDoc"));//ne_numDoc
                        vecReg.add(INT_TBL_DAT_NUM_PED,         rst.getString("tx_numDoc2"));//tx_numDoc2
                        vecReg.add(INT_TBL_DAT_FE_DOC,          rst.getString  ("Fe_Doc"));//fe_doc
                        vecReg.add(INT_TBL_DAT_FEC_EMB,         rst.getString("fe_emb"));//fe_emb
                        vecReg.add(INT_TBL_DAT_FEC_ARR,         rst.getString("fe_arr"));//fe_arr
                        vecReg.add(INT_TBL_DAT_COD_EXP,         rst.getString("co_exp"));//co_exp
                        vecReg.add(INT_TBL_DAT_NOM_EXP,         rst.getString("tx_nomExp"));//tx_nomExp
                        //vecReg.add(INT_TBL_DAT_BUT,        null);//Btn
                        
                        //columnas dinámicas de Cargos a Pagar
                        for(int j=intNumColIniCarPag; j<intNumColFinCarPag;j++){
                             vecReg.add(j,     "");
                        }
                                                
                        vecDat.add(vecReg);                        
                    }
                    else
                    {
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
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
            if (!cargarReg())
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
    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {                          
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
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
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }    
    //Metodo para configurar las columnas estaticas del JTable
        private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(INT_NUM_TOT_CES);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_COD_EMP,"Cód.Emp.");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_COD_DOC,"Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_PED,"Núm.Ped.");
            vecCab.add(INT_TBL_DAT_FE_DOC,"Fec.Doc");
            vecCab.add(INT_TBL_DAT_FEC_EMB,"Fec.Emb.");
            vecCab.add(INT_TBL_DAT_FEC_ARR,"Fec.Arr.");
            vecCab.add(INT_TBL_DAT_COD_EXP,"Cód.Exp.");
            vecCab.add(INT_TBL_DAT_NOM_EXP,"Exportador");
           // vecCab.add(INT_TBL_DAT_BUT,                         null);

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
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(54);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(15);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_PED).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FE_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_EMB).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_ARR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_COD_EXP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_NOM_EXP).setPreferredWidth(150);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EXP, tblDat);
            //objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_DOC_AJU, tblDat);  
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafImp13.ZafMouMotAda();
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
//            tcmAux.getColumn(INT_TBL_DAT_TOT_FOB_CFR).setCellRenderer(objTblCelRenLbl);
//            tcmAux.getColumn(INT_TBL_DAT_TOT_ARA_FOD_IVA).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd=new ZafTblOrd(tblDat);
            //Vector Aux Para el boton
//              vecAux=new Vector();
//            vecAux.add("" + INT_TBL_DAT_BUT);
//            objTblMod.setColumnasEditables(vecAux);
//            vecAux=null;
            //Libero los objetos auxiliares.
            tcmAux=null;
            //Esto hace que el boton pueda ser presionado setModoOperacion
//            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
//          
//              objTblCelRenBut=new ZafTblCelRenBut();
//                tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenBut);
//                objTblCelEdiButGen=new ZafTblCelEdiButGen();
//                 tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGen);
//                objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
//                int intFilSel;
//                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    intFilSel=tblDat.getSelectedRow();
//                }
//                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    //mostrarFormularioPedido(intFilSel);
//                    System.out.println("Hola Mundo");
//                }
//                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                }
//            });
            
            intNumColEst = objTblMod.getColumnCount();
            
        }
        catch(Exception e)
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
                    if (vcoEmp.buscar("a1.co_emp", txtCodImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        //txtCodExp.setText("");
                        //txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(0);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtCodImp.setText(strCodEmp);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoEmp.buscar("a1.tx_nom", txtNomImp.getText())){
                        txtCodImp.setText(vcoEmp.getValueAt(1));
                        txtNomImp.setText(vcoEmp.getValueAt(2));
                        txtCodExp.setEditable(true);
                        txtNomExp.setEditable(true);
                        butExp.setEnabled(true);
                        txtCodExp.setText("");
                        txtNomExp.setText("");
                    }
                    else{
                        vcoEmp.setCampoBusqueda(1);
                        vcoEmp.setCriterio1(11);
                        vcoEmp.cargarDatos();
                        vcoEmp.show();
                        if (vcoEmp.getSelectedButton()==vcoEmp.INT_BUT_ACE){
                            txtCodImp.setText(vcoEmp.getValueAt(1));
                            txtNomImp.setText(vcoEmp.getValueAt(2));
                            txtCodExp.setEditable(true);
                            txtNomExp.setEditable(true);
                            butExp.setEnabled(true);
                            txtCodExp.setText("");
                            txtNomExp.setText("");
                        }
                        else{
                            txtNomImp.setText(strNomEmp);
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
                case 1: //Básqueda directa por "Número de cuenta".
                    if (vcoExp.buscar("a1.co_exp", txtCodExp.getText()) )
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
                        vcoExp.setCampoBusqueda(1);
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
             
             private void mostrarFormularioPedidoEmbarcado(int fila){
        //String strCodLoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_LOC).toString();
        String strCodTipDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_TIP_DOC).toString();
        String strCodDoc=objTblMod.getValueAt(fila, INT_TBL_DAT_COD_DOC).toString();
                                
        Importaciones.ZafImp33.ZafImp33 objImp_33=new Importaciones.ZafImp33.ZafImp33(objParSis, Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc));
        this.getParent().add(objImp_33,javax.swing.JLayeredPane.DEFAULT_LAYER);
        objImp_33.setVisible(true);
        objImp_33=null;
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
                    strMsg="Código de la empresa del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del local del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_DOC:
                    strMsg="Código del documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número del documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NUM_PED:
                    strMsg="Número del pedido (Pedido Embarcado)";
                    break;    
                case INT_TBL_DAT_FE_DOC:
                    strMsg="Fecha del documento del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_FEC_EMB:
                    strMsg="Fecha del embarque del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_FEC_ARR:
                    strMsg="Fecha del arribo del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_COD_EXP:
                    strMsg="Código del Exportador del Pedido Embarcado";
                    break;
                case INT_TBL_DAT_NOM_EXP:
                    strMsg="Nombre del Exportador del Pedido Embarcado";
                    break;
                default:
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
        
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butExp;
    private javax.swing.JButton butImp;
    private javax.swing.JComboBox<String> cboEstDoc;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel lblEmpImp;
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
}
