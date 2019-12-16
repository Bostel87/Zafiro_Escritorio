/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Tony Sanginez Horas suplementarias/extraordinarias (Por empleado)
 */
package RecursosHumanos.ZafRecHum11;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafDtePckEdi.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;

public class ZafRecHum11 extends javax.swing.JInternalFrame {

    /* DECLARACION DE VARIABLES USADAS EN EL SISTEMA*/
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafSelFec objSelFec;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PeopuMenú en JTable.
    private ZafTblBus objTblBus;                                //Editor de búsqueda.
    private ZafTblOrd objTblOrd;                                //JTable de ordenamiento.
    private ZafTblTot objTblTot;
    private ZafTblCelRenLbl objTblCelRenLbl;

    private ZafThreadGUI objThrGUI;
    private String strVersion = " v0.4 ";

    // TABLA DE DATOS
    private static final int INT_TBLD_LIN = 0;                                  //Línea
    private static final int INT_TBLD_CODEMP = 1;                               //Código del Empresa.
    private static final int INT_TBLD_CODLOC = 2;                               //Código del local.
    private static final int INT_TBLD_CODTIPDOC = 3;                            //Código del tipo de documento.
    private static final int INT_TBLD_TIPDOC = 4;                               //Descripción corta del tipo de documento.
    private static final int INT_TBLD_CODDOC = 5;                               //Código del documento (Sistema).
    private static final int INT_TBLD_NUMDOC = 6;                               //Número del documento (Preimpreso).
    private static final int INT_TBLD_FECDOC = 7;                               //Fecha del documento.
    private static final int INT_TBLD_BUTSOL = 8;                               //Boton muestra la solicitud de horas extras
    private static final int INT_TBLD_HORENT = 9;                               //Horario de Entrada del empleado
    private static final int INT_TBLD_HORSAL = 10;                              //Horario de Salida del empleado
    private static final int INT_TBLD_SOLDES = 11;                              //Solicitud de horas extras del empleado DESDE esta hora.
    private static final int INT_TBLD_SOLHAS = 12;                              //Solicitud de horas extras del empleado HASTA esta hora.
    private static final int INT_TBLD_AUTDES = 13;                              //Autorizacion de horas extras DESDE esta hora
    private static final int INT_TBLD_AUTHAS = 14;                              //Autorizacion de horas extras HASTA esta hora
    private static final int INT_TBLD_MARENT = 15;                              //Marcacion de ENTRADA registrada del biometrico     
    private static final int INT_TBLD_MARSAL = 16;                              //Marcacion de SALIDA  registrada del biometrico 
    private static final int INT_TBLD_CALHORDES = 17;                           //Calculo de horas Suplementarias/Extraordinarias DESDE esta hora 
    private static final int INT_TBLD_CALHORHAS = 18;                           //Calculo de horas Suplementarias/Extraordinarias HASTA esta hora 
    private static final int INT_TBLD_CALHORSUL = 19;                            //Calculo de horas Suplementarias ($)
    private static final int INT_TBLD_CALHOREXT = 20;                           //Calculo de horas Extraordinarias ($)
    private static final int INT_TBLD_CALVAL = 21;                              //Calculo del valor por concepto de horas extras ($)

    private Vector vecDat, vecReg, vecCab;

    private ZafMouMotAda objMouMotAda;
    private boolean blnCon;
    private String strCodEmp, strDesEmp;                                        //Contenido del campo al obtener el foco.
    private ZafVenCon vcoTra;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strConSQL, strAux;
    private String strCodTra = "";
    private String strNomTra = "";

    /**
     * Creates new form ZafRecHum11
     */
    /**
     * Crea una nueva instancia de la clase ZafRecHum11.
     */
    public ZafRecHum11(ZafParSis objZafParsis) {
        try {

            this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
            initComponents();
            objUti = new ZafUtil();
            objTblCelRenLbl = new ZafTblCelRenLbl();
            
            vecDat = new Vector();
            txtCodTra.setBackground(objParSis.getColorCamposObligatorios());
            txtNomTra.setBackground(objParSis.getColorCamposObligatorios());
            txtSueEmp.setBackground(objParSis.getColorCamposSistema());
            txtSueEmp.setEditable(false);
            this.setTitle(objParSis.getNombreMenu() + " " + strVersion);
            lblTit.setText(objParSis.getNombreMenu());
            //Cargar Anio y Mes
            cargarPeriodo();
            //Configurar las ZafVenCon. Ventanas de consulta
            configurarFrm();
            configurarVenConTra();
        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panFilCab = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        butTra = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        txtSueEmp = new javax.swing.JTextField();
        PanReport = new javax.swing.JPanel();
        spnTot = new javax.swing.JScrollPane();
        tblTot = new javax.swing.JTable();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {             protected javax.swing.table.JTableHeader createDefaultTableHeader()             {                 return new ZafTblHeaGrp(columnModel);             }         };
        jPanel1 = new javax.swing.JPanel();
        PanBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGuardar = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(700, 450));

        lblTit.setText("jLabel1");
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        panFilCab.setAutoscrolls(true);
        panFilCab.setPreferredSize(new java.awt.Dimension(0, 400));
        panFilCab.setLayout(null);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Empleado:");
        panFilCab.add(jLabel7);
        jLabel7.setBounds(20, 40, 70, 20);

        txtCodTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodTraFocusLost(evt);
            }
        });
        txtCodTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodTraActionPerformed(evt);
            }
        });
        panFilCab.add(txtCodTra);
        txtCodTra.setBounds(90, 40, 50, 20);

        txtNomTra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomTraFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomTraFocusLost(evt);
            }
        });
        txtNomTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomTraActionPerformed(evt);
            }
        });
        panFilCab.add(txtNomTra);
        txtNomTra.setBounds(140, 40, 340, 20);

        butTra.setText("...");
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        panFilCab.add(butTra);
        butTra.setBounds(480, 40, 20, 23);

        jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel8.setText("Periodo:");
        panFilCab.add(jLabel8);
        jLabel8.setBounds(20, 80, 70, 20);

        panFilCab.add(cboPerAAAA);
        cboPerAAAA.setBounds(90, 80, 80, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        panFilCab.add(cboPerMes);
        cboPerMes.setBounds(170, 80, 120, 20);

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel9.setText("Sueldo:");
        panFilCab.add(jLabel9);
        jLabel9.setBounds(20, 120, 70, 20);
        panFilCab.add(txtSueEmp);
        txtSueEmp.setBounds(90, 120, 100, 20);

        tabFrm.addTab("Filtro", panFilCab);

        PanReport.setLayout(new java.awt.BorderLayout());

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

        PanReport.add(spnTot, java.awt.BorderLayout.SOUTH);

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
        tblDat.setToolTipText("");
        tblDat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDatMouseClicked(evt);
            }
        });
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        spnDat.setViewportView(tblDat);

        PanReport.add(spnDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", PanReport);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.BorderLayout());

        PanBot.setToolTipText("");

        butCon.setText("Consultar");
        butCon.setToolTipText("Ejecuta la consulta de acuerdo al filtro especificado.");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        PanBot.add(butCon);

        butGuardar.setText("Guardar");
        butGuardar.setToolTipText("");
        butGuardar.setEnabled(false);
        butGuardar.setPreferredSize(new java.awt.Dimension(92, 25));
        butGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuardarActionPerformed(evt);
            }
        });
        PanBot.add(butGuardar);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        PanBot.add(butCer);

        jPanel1.add(PanBot, java.awt.BorderLayout.EAST);

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

        jPanel1.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botón ("Consultar" o "Detener").
        if (objThrGUI == null) {
            objThrGUI = new ZafThreadGUI();
            objThrGUI.start();
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm();
    }//GEN-LAST:event_butCerActionPerformed

    private void butGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuardarActionPerformed

    }//GEN-LAST:event_butGuardarActionPerformed

    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        strNomTra = txtNomTra.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                    txtSueEmp.setText("");
                } else {
                    mostrarVenConTra(2);
                }
                if (txtCodTra.getText().length() > 0) {
                    cargarSueEmp();
                }
            } else {
                txtNomTra.setText(strNomTra);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
        // TODO add your handling code here:
        txtCodTra.transferFocus();
    }//GEN-LAST:event_txtCodTraActionPerformed

    private void txtCodTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusGained
        strCodTra = txtCodTra.getText();
        txtCodTra.selectAll();
    }//GEN-LAST:event_txtCodTraFocusGained

    private void txtCodTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodTraFocusLost
        if (!txtCodTra.getText().equalsIgnoreCase(strCodTra)) {
            if (txtCodTra.getText().equals("")) {
                txtCodTra.setText("");
                txtNomTra.setText("");
                txtSueEmp.setText("");
            } else {
                BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
                /*metodo que carga el sueldo del empleado */
            }
            if (txtCodTra.getText().length() > 0) {
                cargarSueEmp();
            }
        } else {
            txtCodTra.setText(strCodTra);
            if (txtCodTra.getText().length() > 0) {
                cargarSueEmp();
            }
        }
    }//GEN-LAST:event_txtCodTraFocusLost

    private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
        strCodTra = txtCodTra.getText();
        if (mostrarVenConTra(0)) {
            if (txtCodTra.getText().length() > 0) {
                cargarSueEmp();
            }
        }
    }//GEN-LAST:event_butTraActionPerformed

    private void tblDatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblDatKeyPressed
        //Abrir la opción seleccionada al presionar ENTER.
        if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
            if (txtCodTra.getText().length() > 0) {
                cargarSueEmp();
            }
            //evt.setKeyCode(0);
            evt.consume();
            butTraActionPerformed(null);
        }
    }//GEN-LAST:event_tblDatKeyPressed

    private void tblDatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDatMouseClicked

    }//GEN-LAST:event_tblDatMouseClicked

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Configurar ZafSelFec:
            //Inicializar objetos.
            objUti = new ZafUtil();
            //Obbtener los permisos del usuario.
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + strVersion);
            lblTit.setText(strAux);
            //Configurar objetos.
            txtSueEmp.setVisible(true);
            //Configurar el combo "Estado de registro".
            //Configurar las ZafVenCon.
            configurarVenConTra();
            //Configurar los JTables.
            configurarTblDat();
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    //**    cerrar la aplicacion    ** //
    private void exitForm() {
        dispose();
    }

    /**
     * Cerrar la aplicación.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {
        String strTit, strMsg;
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanBot;
    private javax.swing.JPanel PanReport;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGuardar;
    private javax.swing.JButton butTra;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panFilCab;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTot;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTot;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtNomTra;
    private javax.swing.JTextField txtSueEmp;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función configura el JTable "tblDat".
     *
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(24);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBLD_LIN, "");
            vecCab.add(INT_TBLD_CODEMP, "Cód.Emp.");
            vecCab.add(INT_TBLD_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBLD_CODTIPDOC, "Cód.Tip.Doc");
            vecCab.add(INT_TBLD_TIPDOC, "Tip.Doc");
            vecCab.add(INT_TBLD_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBLD_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBLD_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBLD_BUTSOL, "...");
            vecCab.add(INT_TBLD_HORENT, "Entrada");
            vecCab.add(INT_TBLD_HORSAL, "Salida");
            vecCab.add(INT_TBLD_SOLDES, "Desde");
            vecCab.add(INT_TBLD_SOLHAS, "Hasta");
            vecCab.add(INT_TBLD_AUTDES, "Desde");
            vecCab.add(INT_TBLD_AUTHAS, "Hasta");
            vecCab.add(INT_TBLD_MARENT, "Entrada");
            vecCab.add(INT_TBLD_MARSAL, "Salida");
            vecCab.add(INT_TBLD_CALHORDES, "Desde");
            vecCab.add(INT_TBLD_CALHORHAS, "Hasta");
            vecCab.add(INT_TBLD_CALHORSUL, "Hor.Sup");
            vecCab.add(INT_TBLD_CALHOREXT, "Hor.Ext");
            vecCab.add(INT_TBLD_CALVAL, "Valor");
            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBLD_LIN);
            //Configurar JTable: Establecer el menú de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBLD_LIN).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBLD_CODEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_CODLOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBLD_CODTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBLD_TIPDOC).setPreferredWidth(88);
            tcmAux.getColumn(INT_TBLD_CODDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLD_NUMDOC).setPreferredWidth(88);
            tcmAux.getColumn(INT_TBLD_FECDOC).setPreferredWidth(88);
            tcmAux.getColumn(INT_TBLD_BUTSOL).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBLD_HORENT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_HORSAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_SOLDES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_SOLHAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_AUTDES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_AUTHAS).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_MARENT).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_MARSAL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBLD_CALHORDES).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLD_CALHORHAS).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLD_CALHORSUL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLD_CALHOREXT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBLD_CALVAL).setPreferredWidth(70);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBLD_BUTSOL).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBLD_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBLD_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBLD_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBLD_CODDOC, tblDat);

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Editor de búsqueda.
            objTblBus = new ZafTblBus(tblDat);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab = new ZafTblFilCab(tblDat);
            //Agrupar las columnas.
            ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
            objTblHeaGrp.setHeight(16 * 2);
            ZafTblHeaColGrp objTblHeaColGrpSolHorSupExt;
            objTblHeaColGrpSolHorSupExt = new ZafTblHeaColGrp("Solicitudes de horas Suplementarias/Extraordinarias");
            objTblHeaColGrpSolHorSupExt.setHeight(16);
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_CODEMP));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_CODLOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_CODTIPDOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_TIPDOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_CODDOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_NUMDOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_FECDOC));
            objTblHeaColGrpSolHorSupExt.add(tcmAux.getColumn(INT_TBLD_BUTSOL));
            ZafTblHeaColGrp objTblHeaColGrpHorario = new ZafTblHeaColGrp("Horario");
            objTblHeaColGrpHorario.setHeight(16);
            objTblHeaColGrpHorario.add(tcmAux.getColumn(INT_TBLD_HORENT));
            objTblHeaColGrpHorario.add(tcmAux.getColumn(INT_TBLD_HORSAL));
            ZafTblHeaColGrp objTblHeaColGrpSoli = new ZafTblHeaColGrp("Solicitado");
            objTblHeaColGrpSoli.setHeight(16);
            objTblHeaColGrpSoli.add(tcmAux.getColumn(INT_TBLD_SOLDES));
            objTblHeaColGrpSoli.add(tcmAux.getColumn(INT_TBLD_SOLHAS));
            ZafTblHeaColGrp objTblHeaColGrpAut = new ZafTblHeaColGrp("Autorizado");
            objTblHeaColGrpAut.setHeight(16);
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBLD_AUTDES));
            objTblHeaColGrpAut.add(tcmAux.getColumn(INT_TBLD_AUTHAS));
            ZafTblHeaColGrp objTblHeaColGrpMarca = new ZafTblHeaColGrp("Marcado");
            objTblHeaColGrpMarca.setHeight(16);
            objTblHeaColGrpMarca.add(tcmAux.getColumn(INT_TBLD_MARENT));
            objTblHeaColGrpMarca.add(tcmAux.getColumn(INT_TBLD_MARSAL));
            ZafTblHeaColGrp objTblHeaColGrpCalSupExt = new ZafTblHeaColGrp("Calculo de horas Suplementarias/Extraordinarias");
            objTblHeaColGrpCalSupExt.setHeight(16);
            objTblHeaColGrpCalSupExt.add(tcmAux.getColumn(INT_TBLD_CALHORDES));
            objTblHeaColGrpCalSupExt.add(tcmAux.getColumn(INT_TBLD_CALHORHAS));
            objTblHeaColGrpCalSupExt.add(tcmAux.getColumn(INT_TBLD_CALHORSUL));
            objTblHeaColGrpCalSupExt.add(tcmAux.getColumn(INT_TBLD_CALHOREXT));
            objTblHeaColGrpCalSupExt.add(tcmAux.getColumn(INT_TBLD_CALVAL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSolHorSupExt);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpHorario);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpSoli);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpAut);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpMarca);
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpCalSupExt);

            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objTblPopMnu.setInsertarFilaEnabled(false);
            objTblPopMnu.setInsertarFilasEnabled(false);
            objTblPopMnu.setEliminarFilaEnabled(false);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBLD_CALHORDES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLD_CALHORHAS).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLD_CALHORSUL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLD_CALHOREXT).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBLD_CALVAL).setCellRenderer(objTblCelRenLbl);

            Vector vecAux = new Vector();
            vecAux.add("" + INT_TBLD_BUTSOL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBLD_BUTSOL).setCellRenderer(zafTblDocCelRenBut);
            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBLD_BUTSOL, "...") {
                @Override
                public void butCLick() {
                    int intSelFil = tblDat.getSelectedRow();
                    llamarPantSoliHoraExt(tblDat.getValueAt(intSelFil, INT_TBLD_CODEMP).toString(),
                            tblDat.getValueAt(intSelFil, INT_TBLD_CODLOC).toString(),
                            tblDat.getValueAt(intSelFil, INT_TBLD_CODTIPDOC).toString(),
                            tblDat.getValueAt(intSelFil, INT_TBLD_CODDOC).toString());
                }
            };

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblCelRenLbl = null;
            //Configurar JTable: Establecer la clase que controla el ordenamiento.
            objTblOrd = new ZafTblOrd(tblDat);
            //Configurar JTable: Establecer relación entre el JTable de datos y JTable de totales.
            int intCol[] = {INT_TBLD_CALVAL};
            objTblTot = new ZafTblTot(spnDat, spnTot, tblDat, tblTot, intCol);
            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /*metodo usado para llamar a  al programa de Solicitud de horas Suplementarias/Extraordinarias */
    private void llamarPantSoliHoraExt(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        RecursosHumanos.ZafRecHum27.ZafRecHum27 obj1 = new RecursosHumanos.ZafRecHum27.ZafRecHum27(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de búsqueda determina si se debe
     * hacer una búsqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opción que desea utilizar.
     *
     * @param intTipBus El tipo de búsqueda a realizar.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    public void BuscarTra(String campo, String strBusqueda, int tipo) {
        configurarVenConTra();
        vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            } else {
                txtCodTra.setText(strCodEmp);
                txtNomTra.setText(strDesEmp);
            }
        }
    }

    /**
     * Metodo que carga el sueldo del Empleado.
     *
     * @return
     */
    private boolean cargarSueEmp() {
        boolean blnRes = true;
        String strSueEmp = "";
        String strSQL = "";
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                strSQL = "select distinct co_tra, nd_valrub as sueldo from tbm_sueTra where co_rub=1 and co_tra=" + txtCodTra.getText();
                rst = stm.executeQuery(strSQL);
                while (rst.next()) {
                    strSueEmp = rst.getString("sueldo");
                }
                rst.close();
                txtSueEmp.setText(strSueEmp);
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rst.close();
            } catch (Throwable ignore) {
            }
            try {
                stm.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    /**
     * Esta función permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes = true;
        GregorianCalendar calRanIni = new GregorianCalendar();
        GregorianCalendar calRanFin = new GregorianCalendar();
        SimpleDateFormat sdfFecha = new SimpleDateFormat("EEE, dd/MM/yyyy");
        try {
            pgrSis.setIndeterminate(true);
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null && txtCodTra.getText().compareTo("") != 0) {
                calRanIni.set(Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), cboPerMes.getSelectedIndex(), -2);
                calRanFin.set(Integer.valueOf(cboPerAAAA.getSelectedItem().toString()), cboPerMes.getSelectedIndex() + 1, -3);
                stm = con.createStatement();
                //Obtener la condición.
                strConSQL = " AND CAST(a1.fe_aut AS DATE)>'" + objUti.formatearFecha(calRanIni.getTime(), objParSis.getFormatoFechaBaseDatos()) + "'";
                strConSQL += " AND CAST(a1.fe_aut AS DATE)<='" + objUti.formatearFecha(calRanFin.getTime(), objParSis.getFormatoFechaBaseDatos()) + "'";
                if (txtCodTra.getText().length() > 0) {
                    strConSQL += " AND a2.co_tra=" + txtCodTra.getText();
                }

                //Armar la sentencia SQL.
                strSQL = "";
                strSQL += "SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.ne_numDoc, a6.tx_desLar,\n"
                        + "	to_char(a1.fe_doc, 'YYYY-MM-DD') as fe_doc,\n"
                        + "	to_char(a4.ho_ent, 'HH12:MI:SS')as entrada,to_char(a4.ho_sal, 'HH12:MI:SS') as salida, a1.ho_solDes, a1.ho_solHas, \n"
                        + "	a1.ho_autDes, a1.ho_autHas,a5.ho_ent,a5.ho_sal,\n"
                        + "	(case when ((a1.ho_autdes)<(a5.ho_ent)) then a5.ho_ent else a1.ho_autdes end) as horaExEnt, "
                        + "	(case when ((a1.ho_authas)<(a5.ho_sal)) then a1.ho_authas else a5.ho_sal end) as horaExSal,\n"
                        + "	(case when EXTRACT(DOW FROM a1.fe_doc) in(6,0) then 'Extraordinaria' else 'Suplementaria' end) as tipoHora,\n" + //Se agregó el día domingo como extraordinaria anteriormente solo estaban los sabados. Tony
                        "	(case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "		                           else (a5.ho_sal-a1.ho_autdes) end) as horas,\n"
                        + "	((extract(hours from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "					   when ((a1.ho_authas)>(a5.ho_sal)) and"
                        + " ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "		                           else (a5.ho_sal-a1.ho_autdes) end)  )*60) +\n"
                        + "	(extract(minutes from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "		                           else (a5.ho_sal-a1.ho_autdes) end)  ))+\n"
                        + "	(extract(seconds from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "		                           else (a5.ho_sal-a1.ho_autdes) end)  )/60)) as minutosTotales\n"
                        + "FROM tbm_cabSolHorSupExt AS a1\n"
                        + "INNER JOIN tbm_detSolHorSupExt AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n"
                        + "INNER JOIN TBM_TRAeMP as a3 on (a3.co_tra=a2.co_tra)\n"
                        + "INNER JOIN tbm_detHorTra as a4 on(a4.co_hor=a3.co_hor)\n"
                        + "INNER JOIN tbm_cabConAsiTra AS a5 ON (a2.co_tra=a5.co_tra AND a1.fe_doc=a5.fe_dia) "
                        + "INNER JOIN tbm_cabTipDoc as a6 on (a1.co_tipDoc=a6.co_tipDoc and a1.co_emp=a6.co_emp and a1.co_loc=a6.co_loc) "
                        + "WHERE a1.st_reg='A' and EXTRACT(DOW FROM a1.fe_doc)=a4.ne_dia ";
                strSQL += strConSQL;
                //Se agregó un union para poder visualizar las horas extras de los domingos. tony
                strSQL += " UNION\n"
                        + "                            SELECT distinct a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc,a1.ne_numDoc, a6.tx_desLar,\n"
                        + "                            	to_char(a1.fe_doc, 'YYYY-MM-DD') as fe_doc,\n"
                        + "                            	 '' as entrada,'' as salida,\n"
                        + "                            	 a1.ho_solDes, a1.ho_solHas, \n"
                        + "                            	a1.ho_autDes, a1.ho_autHas,a5.ho_ent,a5.ho_sal,\n"
                        + "                            	(case when ((a1.ho_autdes)<(a5.ho_ent)) then a5.ho_ent else a1.ho_autdes end) as horaExEnt,  \n"
                        + "                            	(case when ((a1.ho_authas)<(a5.ho_sal)) then a1.ho_authas else a5.ho_sal end) as horaExSal,\n"
                        + "                            	(case when EXTRACT(DOW FROM a1.fe_doc) in(6,0) then 'Extraordinaria' else 'Suplementaria' end) as tipoHora,\n"
                        + "                            	(case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "                            					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "                            		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "                            		                           else (a5.ho_sal-a1.ho_autdes) end) as horas,\n"
                        + "                            	((extract(hours from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "                            					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "                            		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "                            		                           else (a5.ho_sal-a1.ho_autdes) end)  )*60) +\n"
                        + "                            	(extract(minutes from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "                            					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "                            		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "                            		                           else (a5.ho_sal-a1.ho_autdes) end)  ))+\n"
                        + "                            	(extract(seconds from (case when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)<(a5.ho_ent)) then (a1.ho_authas-a5.ho_ent)\n"
                        + "                            					   when ((a1.ho_authas)>(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a5.ho_sal-a1.ho_autdes)\n"
                        + "                            		                           when ((a1.ho_authas)<(a5.ho_sal)) and ((a1.ho_autdes)>(a5.ho_ent)) then (a1.ho_authas-a1.ho_autdes) \n"
                        + "                            		                           else (a5.ho_sal-a1.ho_autdes) end)  )/60)) as minutosTotales\n"
                        + "                            FROM tbm_cabSolHorSupExt AS a1\n"
                        + "                            INNER JOIN tbm_detSolHorSupExt AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)\n"
                        + "                            INNER JOIN TBM_TRAeMP as a3 on (a3.co_tra=a2.co_tra)\n"
                        + "                            INNER JOIN tbm_detHorTra as a4 on(a4.co_hor=a3.co_hor)\n"
                        + "                            INNER JOIN tbm_cabConAsiTra AS a5 ON (a2.co_tra=a5.co_tra AND a1.fe_doc=a5.fe_dia)  \n"
                        + "                            INNER JOIN tbm_cabTipDoc as a6 on (a1.co_tipDoc=a6.co_tipDoc and a1.co_emp=a6.co_emp and a1.co_loc=a6.co_loc)  \n"
                        + "                            WHERE a1.st_reg='A' and EXTRACT(DOW FROM a1.fe_doc)=0 ";
                strSQL += strConSQL;
                strSQL += " ORDER BY fe_doc;";
                rst = stm.executeQuery(strSQL);

                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                //Metodo para verificar si en la hora obtenida de lunes a viernes se trabajo en horario de 00:00 a 06:00 que eso equivale a horas extraordinarias
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                String horaExtDes = "00:00:00";
                String horaExtHas = "06:00:00";
                String horaNueva = "";

                Date dateExtDes, dateExtHas, dateNueva;
                dateExtDes = dateFormat.parse(horaExtDes);
                dateExtHas = dateFormat.parse(horaExtHas);

                while (rst.next()) {
                    java.util.Vector vecReg = new java.util.Vector();
                    vecReg.add(INT_TBLD_LIN, "");
                    vecReg.add(INT_TBLD_CODEMP, rst.getString("co_emp"));
                    vecReg.add(INT_TBLD_CODLOC, rst.getString("co_loc"));
                    vecReg.add(INT_TBLD_CODTIPDOC, rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBLD_TIPDOC, rst.getString("tx_desLar"));
                    vecReg.add(INT_TBLD_CODDOC, rst.getString("co_doc"));
                    vecReg.add(INT_TBLD_NUMDOC, rst.getString("ne_numDoc"));
                    vecReg.add(INT_TBLD_FECDOC, sdfFecha.format(rst.getDate("fe_doc")));
                    vecReg.add(INT_TBLD_BUTSOL, "...");
                    vecReg.add(INT_TBLD_HORENT, rst.getString("entrada"));
                    vecReg.add(INT_TBLD_HORSAL, rst.getString("salida"));
                    vecReg.add(INT_TBLD_SOLDES, rst.getString("ho_solDes"));
                    vecReg.add(INT_TBLD_SOLHAS, rst.getString("ho_solHas"));
                    vecReg.add(INT_TBLD_AUTDES, rst.getString("ho_autDes"));
                    vecReg.add(INT_TBLD_AUTHAS, rst.getString("ho_autHas"));
                    vecReg.add(INT_TBLD_MARENT, rst.getString("ho_ent"));
                    vecReg.add(INT_TBLD_MARSAL, rst.getString("ho_sal"));
                    if (rst.getString("ho_ent") != null) {
                        vecReg.add(INT_TBLD_CALHORDES, rst.getString("horaExEnt"));
                    } else {
                        vecReg.add(INT_TBLD_CALHORDES, "");
                    }
                    if (rst.getString("ho_sal") != null) {
                        vecReg.add(INT_TBLD_CALHORHAS, rst.getString("horaExSal"));
                    } else {
                        vecReg.add(INT_TBLD_CALHORHAS, "");
                    }
                    if (rst.getString("tipoHora").equals("Extraordinaria")) {
                        vecReg.add(INT_TBLD_CALHORSUL, "00:00:00");
                        vecReg.add(INT_TBLD_CALHOREXT, rst.getString("horas"));
                    } else {

                        horaNueva = rst.getString("horaExEnt");
                        if (horaNueva != null) {
                            dateNueva = dateFormat.parse(horaNueva);
                            if ((dateExtDes.compareTo(dateNueva) <= 0) && (dateExtHas.compareTo(dateNueva) >= 0)) {
                                String strHorasSobra = rst.getString("horas");
                                String strHoras = restarHoras(horaNueva, horaExtHas);
                                if (vecReg.get(INT_TBLD_CALHORDES) == "" || vecReg.get(INT_TBLD_CALHORHAS) == "") {
                                    vecReg.add(INT_TBLD_CALHORSUL, "");
                                    vecReg.add(INT_TBLD_CALHOREXT, "");
                                } else {
                                    vecReg.add(INT_TBLD_CALHORSUL, restarHoras(strHoras, strHorasSobra));
                                    vecReg.add(INT_TBLD_CALHOREXT, restarHoras(horaNueva, horaExtHas));
                                }
                            } else {
                                if (vecReg.get(INT_TBLD_CALHORDES) == "" || vecReg.get(INT_TBLD_CALHORHAS) == "") {
                                    vecReg.add(INT_TBLD_CALHORSUL, "");
                                    vecReg.add(INT_TBLD_CALHOREXT, "");
                                } else {
                                    vecReg.add(INT_TBLD_CALHORSUL, rst.getString("horas"));
                                    vecReg.add(INT_TBLD_CALHOREXT, "00:00:00");
                                }
                            }
                        } else {
                            vecReg.add(INT_TBLD_CALHORSUL, "00:00:00");
                            vecReg.add(INT_TBLD_CALHOREXT, "00:00:00");
                        }

                    }
                    if (rst.getString("ho_ent") == null || rst.getString("ho_sal") == null) {
                        vecReg.add(INT_TBLD_CALVAL, 0.00);
                    } else {
                        vecReg.add(INT_TBLD_CALVAL, calculoHorasExtra(txtSueEmp.getText().toString(), vecReg.get(INT_TBLD_CALHOREXT).toString(), vecReg.get(INT_TBLD_CALHORSUL).toString()));
                    }
                    vecDat.add(vecReg);
                }
                blnCon = true;
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                //Calcular totales.
                objTblTot.calcularTotales();
                if (blnCon) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                } else {
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDat.getRowCount() + " registros.");
                }
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Metodo para restar dos horas en formato string.
     *
     * @param strHoraMenor
     * @param strHoraMayor
     * @return
     */
    private String restarHoras(String strHoraMenor, String strHoraMayor) {
        String strHoraResultado = "";
        GregorianCalendar gcHoraTotal = new GregorianCalendar(2000, 01, 01, 0, 0, 0);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String[] h1 = strHoraMenor.split(":");
        String[] h2 = strHoraMayor.split(":");
        int intSegundosTotalesHoraMenor = Integer.parseInt(h1[2]) + Integer.parseInt(h1[1]) * 60 + Integer.parseInt(h1[0]) * 3600;
        int intSegundosTotalesHoraMayor = Integer.parseInt(h2[2]) + Integer.parseInt(h2[1]) * 60 + Integer.parseInt(h2[0]) * 3600;
        int intTotalSegundos = intSegundosTotalesHoraMayor - intSegundosTotalesHoraMenor;
        gcHoraTotal.set(Calendar.SECOND, intTotalSegundos);
        strHoraResultado = dateFormat.format(gcHoraTotal.getTime());
        return strHoraResultado;
    }

    /**
     * Metodo para calcular las horas extras utilizando el sueldo del empleado y
     * las horas que ha trabajado. Recibe las horas Suplementarias y
     * Extraordinarias.
     *
     * @param strSueldo
     * @param strHoraExt
     * @param strHoraSup
     * @return
     */
    private String calculoHorasExtra(String strSueldo, String strHoraExt, String strHoraSup) {
        String strTotal = "";
        double dblSueldo, dblTotal, dblValSegExt, dblValSegSup;
        String[] h1 = strHoraExt.split(":");
        String[] h2 = strHoraSup.split(":");
        int intSegTotHorExt = Integer.parseInt(h1[2]) + Integer.parseInt(h1[1]) * 60 + Integer.parseInt(h1[0]) * 3600;
        int intSegTotHorSup = Integer.parseInt(h2[2]) + Integer.parseInt(h2[1]) * 60 + Integer.parseInt(h2[0]) * 3600;
        dblSueldo = Double.parseDouble(strSueldo);
        dblValSegSup = (dblSueldo / 866400) * 1.5;
        dblValSegExt = (dblSueldo / 866400) * 2;
        dblTotal = (dblValSegSup) * intSegTotHorSup + dblValSegExt * intSegTotHorExt;
        strTotal = String.valueOf(dblTotal);
        return strTotal;
    }

    private class ZafThreadGUI extends Thread {

        @Override
        public void run() {
            lblMsgSis.setText("Obteniendo datos...");
            pgrSis.setIndeterminate(true);
            if (validarCamObl()) {
                if (!cargarDetReg()) {
                    //Inicializar objetos si no se pudo cargar los datos.
                    lblMsgSis.setText("Listo");
                    pgrSis.setValue(0);
                    butCon.setText("Consultar");
                }
                if (tblDat.getRowCount() > 0) {
                    tabFrm.setSelectedIndex(1);
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }
            } else {
                lblMsgSis.setText("Listo");
                pgrSis.setIndeterminate(false);
                pgrSis.setValue(0);
            }
            objThrGUI = null;

        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren más espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBLD_LIN:
                    strMsg = "";
                    break;
                case INT_TBLD_CODEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBLD_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBLD_CODTIPDOC:
                    strMsg = "Código del tipo de documento";
                    break;
                case INT_TBLD_TIPDOC:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBLD_CODDOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBLD_NUMDOC:
                    strMsg = "Número del documento";
                    break;
                case INT_TBLD_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBLD_BUTSOL:
                    strMsg = "Muestra la Solicitud de horas suplementarias/extraordinarias";
                    break;
                case INT_TBLD_HORENT:
                    strMsg = "Hora de entrada";
                    break;
                case INT_TBLD_HORSAL:
                    strMsg = "Hora de Salida";
                    break;
                case INT_TBLD_SOLDES:
                    strMsg = "Solicitud Desde";
                    break;
                case INT_TBLD_SOLHAS:
                    strMsg = "Solicitud Hasta";
                    break;
                case INT_TBLD_AUTDES:
                    strMsg = "Autorizacion Desde";
                    break;
                case INT_TBLD_AUTHAS:
                    strMsg = "Autorizacion Hasta";
                    break;
                case INT_TBLD_MARENT:
                    strMsg = "Marcacion de entrada";
                    break;
                case INT_TBLD_MARSAL:
                    strMsg = "Marcacion de salida";
                    break;
                case INT_TBLD_CALHORDES:
                    strMsg = "Desde";
                    break;
                case INT_TBLD_CALHORHAS:
                    strMsg = "Hasta";
                    break;
                case INT_TBLD_CALHORSUL:
                    strMsg = "Horas suplementarias";
                    break;
                case INT_TBLD_CALHOREXT:
                    strMsg = "Horas extraordinarias";
                    break;
                default:
                    strMsg = "Valor de horas suplementarias/extraordinarias";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Esta funcion permite carga el ultimo periodo. (Anio y Mes)
     */
    private void cargarPeriodo() {
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";

        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stmLoc = con.createStatement();
                strSQL = " select  ne_ani,ne_mes from tbm_ingegrmentra ";
                strSQL = strSQL + " group by ne_ani,ne_mes order by ne_ani desc, ne_mes desc";
                rstLoc = stmLoc.executeQuery(strSQL);
                DefaultComboBoxModel model = new DefaultComboBoxModel();//Creo un model para luego cargarlo en un combobox
                String strMes = "";
                Boolean blnUltFec = true;
                while (rstLoc.next()) {
                    if (blnUltFec) {
                        strMes = rstLoc.getString("ne_mes");
                        blnUltFec = false;
                    }
                    String strAnio = rstLoc.getString("ne_ani");
                    if (model.getIndexOf(strAnio) == -1) { //Pregunto si existe el anio para que no se repita al momento de llenarlo en el combobox
                        model.addElement(strAnio);
                        cboPerAAAA.addItem(strAnio);
                    }
                }
                int intMes = Integer.valueOf(strMes);
                cboPerMes.setSelectedIndex(intMes - 1);
            }
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                con.close();
            } catch (Throwable ignore) {
            }
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Empleados".
     */
    private boolean configurarVenConTra() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_tra");
            arlCam.add("a1.tx_ape");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");

            String strSQL = "";
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
                        + "group by a.co_tra,a.tx_ape,a.tx_nom "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) "
                        + "where co_emp = " + objParSis.getCodigoEmpresa() + " "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            }

            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = false;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        blnRes = true;
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        blnRes = true;
                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                            blnRes = true;
                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        blnRes = true;
                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));

                        } else {
                            txtNomTra.setText(strNomTra);
                            blnRes = true;
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
     * Valida los campos obligatorios de la ventana
     *
     * @return Retorna true si campos obligatorios están llenos y false si no lo
     * están
     */
    private boolean validarCamObl() {
        boolean blnOk = true;
        if (txtCodTra.getText().length() == 0) {
            txtCodTra.requestFocus();
            txtCodTra.selectAll();
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Empleado</FONT> es obligatorio.<BR>Escriba o seleccione una empleado y vuelva a intentarlo.</HTML>");
            return false;
        }
        return blnOk;
    }
}
