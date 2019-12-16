package RecursosHumanos.ZafRecHum74;

import Librerias.ZafAsiDia.ZafAsiDia;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumDao.RRHHDao;
import Librerias.ZafRecHum.ZafVenFun.Mail;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import com.tuval.utilities.archivos.ArchivosTuval;
import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Autorización de roles de pago...
 *
 * @author Roberto Flores Guayaquil, 24/09/2013
 */
public class ZafRecHum74 extends javax.swing.JInternalFrame {

    private final int INT_TBL_DAT_LIN = 0;
    private final int INT_TBL_DAT_CHKREVFAL = 1;
    private final int INT_TBL_DAT_FECREVFAL = 2;
    private final int INT_TBL_DAT_BUTREVFAL = 3;
    private final int INT_TBL_DAT_CODEMP = 4;
    private final int INT_TBL_DAT_CODLOC = 5;
    private final int INT_TBL_DAT_CODTIPDOC = 6;
    private final int INT_TBL_DAT_DESCORTIPDOC = 7;
    private final int INT_TBL_DAT_CODDOC = 8;
    private final int INT_TBL_DAT_NUMDOC = 9;
    private final int INT_TBL_DAT_FECDOC = 10;
    private final int INT_TBL_DAT_BUTROL = 11;
    private final int INT_TBL_DAT_CHKAUT = 12;
    private final int INT_TBL_DAT_FECAUT = 13;
    private final int INT_TBL_DAT_BUTGENARCBCO = 14;
    private final int INT_TBL_DAT_FECGENARCBCO = 15;
    private final int INT_TBL_DAT_CHKCIEROL = 16;
    private final int INT_TBL_DAT_FECCIEROL = 17;
    private final int INT_TBL_DAT_BUTREVFALIESS = 18;

    private ZafAsiDia objAsiDia = null;

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru;
    private ZafTblBus objTblBus;

    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;

    private ZafTblCelRenChk objTblCelRenChkRRHH;
    private ZafTblCelEdiChk objTblCelEdiChkRRHH;

    private ZafTblCelRenChk objTblCelRenChkGer;
    private ZafTblCelEdiChk objTblCelEdiChkGer;

    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenButGenArcBco;

    private ZafPerUsr objPerUsr;

    private String strVersion = "v1.13";

    private boolean blnPas2 = false;
    private boolean blnPas3 = false;
    private boolean blnPas4 = false;

    private ArrayList<String> arrLstAni = null;
    private ArrayList<String> arrLstMes = null;

    private ArrayList<String> arrLstMeses = null;

    private int intNePer = 0;
    private int intAño = 0;
    private int intMes = 0;

    /**Periodo de la ultima quicena generada*/
    private int intUltPer = 0;
    /**Año de la ultima quicena generada*/
    private int intUltAni = 0;
    /**Mes de la ultima quicena generada*/
    private int intUltMes = 0;
    private int intCoTipDoc = 0;

    private String strMes;
    private boolean blnRRHHEnvCor = false;
    private boolean blnGerGenEnvCor = false;
    private boolean blnGerAdmEnvCor = false;

    public ZafRecHum74(ZafParSis obj) {
        try {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);

            butCon.setVisible(false);
            butGua.setVisible(false);
            butCer.setVisible(false);

            if (objPerUsr.isOpcionEnabled(3752)) {
                butCon.setVisible(true);
            }

            if (objPerUsr.isOpcionEnabled(3753)) {
                butGua.setVisible(true);
            }

            if (objPerUsr.isOpcionEnabled(3754)) {
                butCer.setVisible(true);
            }

            if (!configurarFrm()) {
                exitForm();
            }

            agregarColTblDat();
            //David
            cargarAnios();
            EventoCombo objEvent = new EventoCombo();
            cboPer.addItemListener(objEvent);
            cboPerMes.addItemListener(objEvent);
            cboPerAAAA.addItemListener(objEvent);
//            asignacionPeriodo();
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    class EventoCombo implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            objTblMod.removeAllRows();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrFil = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        tabFrm = new javax.swing.JTabbedPane();
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
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        PanTabGen = new javax.swing.JPanel();
        butPer = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cboPer = new javax.swing.JComboBox();
        cboPerAAAA = new javax.swing.JComboBox();
        cboPerMes = new javax.swing.JComboBox();
        lblTit = new javax.swing.JLabel();

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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

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

        tabFrm.addTab("General", panRpt);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setPreferredSize(new java.awt.Dimension(385, 26));
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

        butGua.setText("Guardar");
        butGua.setToolTipText("Cierra la ventana.");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

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

        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 70));
        PanTabGen.setLayout(null);

        butPer.setText(".."); // NOI18N
        butPer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPerActionPerformed(evt);
            }
        });
        PanTabGen.add(butPer);
        butPer.setBounds(420, 40, 20, 20);

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel7.setText("Período:"); // NOI18N
        PanTabGen.add(jLabel7);
        jLabel7.setBounds(10, 40, 110, 20);

        cboPer.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Período", "Primera quincena", "Fin de mes" }));
        PanTabGen.add(cboPer);
        cboPer.setBounds(310, 40, 110, 20);

        cboPerAAAA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Año" }));
        cboPerAAAA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboPerAAAAActionPerformed(evt);
            }
        });
        PanTabGen.add(cboPerAAAA);
        cboPerAAAA.setBounds(140, 40, 70, 20);

        cboPerMes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mes", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));
        PanTabGen.add(cboPerMes);
        cboPerMes.setBounds(210, 40, 100, 20);

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        PanTabGen.add(lblTit);
        lblTit.setBounds(0, 10, 684, 14);

        panFrm.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents
                   /*Permite obtener un log de la tabla tbm_grpvar
     *
     */

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed

        //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;

            if (objThrGUI == null && asignacionPeriodo()) {
                objThrGUI = new ZafThreadGUI();
                objThrGUI.start();
            }
        } else {
            blnCon = false;
        }
    }//GEN-LAST:event_butConActionPerformed

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
//        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las
     * opciones Si y No. El usuario es quien determina lo que debe hacer el
     * sistema seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta función muestra un mensaje de error al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique que los datos no se
     * grabaron y que debe comunicar de este particular al administrador del
     * sistema.
     */
    private void mostrarMsgErr(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        javax.swing.JOptionPane.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private boolean guardarDat(Connection con) {
        boolean blnRes = false;
        java.sql.Statement stmLoc = null;
        java.sql.Statement stmLocAux = null;
        java.sql.ResultSet rstLoc = null;
        String strSql = "";

        try {
            if (con != null) {
                stmLoc = con.createStatement();
                stmLocAux = con.createStatement();
                int intFilSel = tblDat.getSelectedRow();
                if (blnPas2) {
                    strSql = "";
                    strSql += " SELECT b.co_emp , b.ne_ani , b.ne_mes , b.ne_per , st_revfal , fe_autrevfal  " + "\n";
                    strSql += " FROM tbm_feccorrolpag  b   " + "\n";
                    strSql += " LEFT OUTER JOIN tbm_cabrolpag a on (a.co_emp=b.co_emp and a.ne_ani=b.ne_ani and a.ne_mes=b.ne_mes and b.ne_per=a.ne_per)   " + "\n";
                    strSql += " WHERE b.co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + "\n";
                    strSql += " and b.ne_ani =  " + intAño + "\n";
                    strSql += " and b.ne_mes = " + intMes + "\n";
                    strSql += " and b.ne_per  = " + intNePer;

                    System.out.println("veryfiedblnPas2: " + strSql);
                    rstLoc = stmLoc.executeQuery(strSql);
                    while (rstLoc.next()) {
                        String strStAutRecHum = rstLoc.getString("st_revfal");
                        if (strStAutRecHum == null) {
                            strSql = "";
                            strSql += "update tbm_feccorrolpag  set st_revfal='S' , fe_autrevfal = current_timestamp, co_usrrevfal = " + objParSis.getCodigoUsuario() + " ,  \n";
                            strSql += " tx_comrevfal = " + objUti.codificar(objParSis.getDireccionIP()) + " \n";
                            strSql += " where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + " \n";
                            strSql += " and ne_ani = " + rstLoc.getString("ne_ani") + " \n";
                            strSql += " and ne_ani = " + rstLoc.getString("ne_ani") + " \n";
                            strSql += " and ne_mes = " + rstLoc.getString("ne_mes") + " \n";
                            strSql += "and ne_per = " + rstLoc.getString("ne_per") + " \n";
                            System.out.println("updateblnPas2: " + strSql);
                            stmLocAux.executeUpdate(strSql);
                            blnRes = true;

                            strSql = "";
                            strSql += "update tbm_datgeningegrmentra set ne_numfalrev = ne_numfal" + "\n";
                            strSql += "where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + "\n";
                            strSql += "and ne_ani = " + rstLoc.getString("ne_ani") + "\n";
                            strSql += "and ne_mes = " + rstLoc.getString("ne_mes") + "\n";
                            strSql += "and ne_per = 0";
                            System.out.println("updateblnPas2: " + strSql);
                            stmLocAux.executeUpdate(strSql);
                            blnRes = true;
                            blnRRHHEnvCor = true;
                            blnGerGenEnvCor = false;
                            blnGerAdmEnvCor = false;
                            if (!blnEnvioCorreo(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_DESCORTIPDOC).toString())) {
                                return false;
                            }
                        }
                    }
                }

                for (int i = 0; i < tblDat.getRowCount(); i++) {
                    Object obj = tblDat.getValueAt(i, INT_TBL_DAT_LIN);
                    if (obj != null) {

                        if ((Boolean) tblDat.getValueAt(i, INT_TBL_DAT_CHKAUT) == true) {//&& tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString()!=null
                            blnPas3 = true;
                            blnPas2 = false;
                        } else {
                            blnPas3 = false;
                        }

                        if (blnPas3) {
                            strSql = "";
                            strSql += "select  st_autger, fe_autger \n";
                            strSql += " from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(i, INT_TBL_DAT_CODEMP).toString() + " \n";
                            strSql += " and co_loc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODLOC).toString() + " \n";
                            strSql += " and co_tipdoc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOC).toString() + " \n";
                            strSql += " and co_doc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODDOC).toString() + " \n";
                            strSql += " and ne_numdoc = " + tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString() + " \n";
                            System.out.println("veryfiedblnPas2: " + strSql);
                            rstLoc = stmLoc.executeQuery(strSql);
                            while (rstLoc.next()) {
                                String strStAutGer = rstLoc.getString("st_autger");
                                if (strStAutGer == null) {
                                    strSql = "";
                                    strSql += "update tbm_cabrolpag  set st_autger='S' , fe_autger = current_timestamp , co_usrautger = " + objParSis.getCodigoUsuario() + " ,  \n";
                                    strSql += " tx_comautger = " + objUti.codificar(objParSis.getDireccionIP()) + " , \n";
                                    strSql += " st_reg = 'A' \n";
                                    strSql += " where co_emp = " + tblDat.getValueAt(i, INT_TBL_DAT_CODEMP).toString() + " \n";
                                    strSql += " and co_loc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODLOC).toString() + " \n";
                                    strSql += " and co_tipdoc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOC).toString() + " \n";
                                    strSql += " and co_doc = " + tblDat.getValueAt(i, INT_TBL_DAT_CODDOC).toString() + " \n";
                                    strSql += " and ne_numdoc = " + tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString() + " \n";
                                    System.out.println("updateblnPas2: " + strSql);
                                    stmLocAux.executeUpdate(strSql);
                                    blnRes = true;

                                    //st_reg = 'A' para el asiento de diario
                                    strSql = "";
                                    strSql += "update tbm_cabdia set st_reg = 'A' " + " \n";
                                    strSql += "where co_emp =  " + tblDat.getValueAt(i, INT_TBL_DAT_CODEMP).toString() + " \n";
                                    strSql += "and co_loc =  " + tblDat.getValueAt(i, INT_TBL_DAT_CODLOC).toString() + " \n";
                                    strSql += "and co_tipdoc =  " + tblDat.getValueAt(i, INT_TBL_DAT_CODTIPDOC).toString() + " \n";
                                    strSql += "and co_dia =  " + tblDat.getValueAt(i, INT_TBL_DAT_CODDOC).toString() + " \n";
                                    strSql += "and tx_numdia = '" + tblDat.getValueAt(i, INT_TBL_DAT_NUMDOC).toString() + "'\n";
                                    System.out.println("updateTbmCabDia: " + strSql);
                                    stmLocAux.executeUpdate(strSql);
                                    blnRes = true;
                                    blnRRHHEnvCor = false;
                                    blnGerGenEnvCor = true;
                                    blnGerAdmEnvCor = false;
                                    if (!blnEnvioCorreo(objTblMod.getValueAt(i, INT_TBL_DAT_DESCORTIPDOC).toString())) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                stmLoc.close();
                stmLoc = null;
            } catch (Throwable ignore) {
            }
            try {
                stmLocAux.close();
                stmLocAux = null;
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
                rstLoc = null;
            } catch (Throwable ignore) {
            }

        }
        return blnRes;
    }

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        //David
        if (0==cboPer.getSelectedIndex() || 0==cboPerMes.getSelectedIndex() || 0==cboPerAAAA.getSelectedIndex())
        {
            mostrarMsgInf("Por favor, seleccione el Año, Mes y Periodo.");
            return;
        }
//------------------------------------------------------------------------------
        //Eddye: Temporal hasta que se mejore.
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==1 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==1 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==2 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==2 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==3 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==3 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==4 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==4 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==5 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==5 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==6 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==6 && cboPer.getSelectedIndex()==2))
        if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==7 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==7 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==8 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==8 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==9 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==9 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==10 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==10 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==11 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==11 && cboPer.getSelectedIndex()==2))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==12 && cboPer.getSelectedIndex()==1))
        //if (!(cboPerAAAA.getSelectedIndex()==1 && cboPerMes.getSelectedIndex()==12 && cboPer.getSelectedIndex()==2))
        {
            mostrarMsgInf("Período invalido. Comuníquese con su administrador del Sistema.");
            return;
        }
//------------------------------------------------------------------------------
//        int anioSelecionado=Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
//        if (intUltPer!=cboPer.getSelectedIndex() || intUltMes!=cboPerMes.getSelectedIndex() || intUltAni!=anioSelecionado)
//        {
//            int intAuxPer=intUltPer;
//            int intAuxAni=intUltAni;
//            int intAuxMes=intUltMes;
//            if (cboPer.getSelectedIndex()==2)
//            {
//                intAuxPer=1;
//                if (intMes==12)
//                {
//                    intAuxAni++;
//                    intAuxMes=1;
//                }
//                else
//                {
//                    intAuxMes++;
//                }
//            }
//            else
//            {
//                intAuxPer=2;
//            }
//            if (intAuxAni<anioSelecionado)
//            {
//                mostrarMsgInf("Periodo es muy elevado");
//                return;
//            }
//            if (intAuxAni==anioSelecionado && intAuxMes<cboPerMes.getSelectedIndex())
//            {
//                mostrarMsgInf("Periodo es muy elevado");
//                return;
//            }
//            if (intAuxAni==anioSelecionado && intAuxMes==cboPerMes.getSelectedIndex() && cboPer.getSelectedIndex()>intAuxPer)
//            {
//                mostrarMsgInf("Periodo es muy elevado");
//                return;
//            }
//        }
        //Fin
        Connection con=null;
        try
        {
            if (objTblMod.isDataModelChanged())
            {
                if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0)
                {
                    con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                    if (con != null)
                    {
                        con.setAutoCommit(false);
                        if (guardarDat(con))
                        {
                            con.commit();
                            mostrarMsgInf("La operación GUARDAR se realizó con éxito.");
                            objThrGUI = null;
                            if (objThrGUI == null)
                            {
                                objThrGUI = new ZafThreadGUI();
                                objThrGUI.start();
                            }
                        }
                        else
                        {
                            con.rollback();
                            mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
                        }
                    }
                }
            }
            else
            {
                mostrarMsgInf("No ha realizado ningún cambio que se pueda guardar.");
            }
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(this, e);
        }
        finally
        {
            try
            {
                con.close();
                con=null;
            }
            catch (Throwable ignore)
            {
                
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed

    private void butPerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPerActionPerformed

    }//GEN-LAST:event_butPerActionPerformed

    private void cboPerAAAAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboPerAAAAActionPerformed

    }//GEN-LAST:event_cboPerAAAAActionPerformed

    private boolean blnEnvioCorreo(String strDesCorTipDoc) {
        boolean blnRes = true;
        try {
            Mail mail2 = new Mail();
            String strCorEleTo = "gerenciageneral@tuvalsa.com;geren_admin@tuvalsa.com;contador@tuvalsa.com;vicepresidencia@tuvalsa.com;";
            //  String strCorEleTo="sistemas2@tuvalsa.com;contador@tuvalsa.com;";
            String strCorEleCC = null;
            String strCorEleCCO = "sistemas2@tuvalsa.com;";
            String strMensCorEle = " <HTML> <body> <BR/><BR/>";
            strMensCorEle += "<TR>Estimados usuarios, </TR></BR></BR>";
            String strAux = "";
            String strSt = "";
            String strAsunto = "";

            if (intNePer == 1) {
                strAux = "1ERA QUINCENA " + strDesCorTipDoc + " " + objParSis.getNombreEmpresa() + " DEL MES DE " + setMes() + "/" + intAño;
            } else {
                strAux = "2DA QUINCENA " + strDesCorTipDoc + " " + objParSis.getNombreEmpresa() + " DEL MES DE " + setMes() + "/" + intAño;
            }

            if (strDesCorTipDoc.trim().compareTo("ROLPAG") == 0) {
                strCorEleTo += "rrhh@tuvalsa.com;";
            }

            if (blnRRHHEnvCor) {

                strMensCorEle += "<TR>El d&#237;a de hoy el &#225;rea de Recursos Humanos ha realizado la autorizaci&#243;n  para poder generar el " + strDesCorTipDoc + ": </TR></BR></BR>";
                strMensCorEle += "<TR>EMPRESA: " + objParSis.getNombreEmpresa() + "</TR></BR>";
                strMensCorEle += "<TR>PERIODO: " + strAux + "</TR></BR></BR>";
                strMensCorEle += "<TR>A continuaci&#243;n el &#225;rea contable realizar&#225; la supervisi&#243;n y el registro del mismo en el sistema.</TR></BR></BR></BR>";
                strMensCorEle += "<TR>Gracias por su atenci&#243;n.</TR></BR>";
                strSt = " Estado: revisado por RRHH";
                this.blnRRHHEnvCor = false;
            }

            if (blnGerGenEnvCor) {

                strMensCorEle += "<TR>El " + strDesCorTipDoc + " ha sido autorizado por la Gerencia General. </TR></BR></BR>";
                strMensCorEle += "<TR>A continuaci&#243;n el &#225;rea administrativa podr&#225; generar los archivos de texto para la acreeditaci&#243;n bancaria.</TR></BR></BR></BR>";
                strMensCorEle += "<TR>Gracias por su atenci&#243;n.</TR></BR>";
                strSt = " Estado: APROBADO por Gerencia General";
                this.blnGerGenEnvCor = false;
            }

            if (blnGerAdmEnvCor) {

                strMensCorEle += "<TR>La Gerencia Administrativa ha generado los archivos de texto para la acreeditaci&#243;n bancaria. </TR></BR></BR>";
                strMensCorEle += "<TR>Finalmente la Gerencia General deber&#225; dar su visto bueno en el Portal del Banco.</TR></BR></BR></BR>";
                strMensCorEle += "<TR>Gracias por su atenci&#243;n.</TR></BR>";
                strSt = " Estado: ARCHIVO GENERADO por Gerencia Administrativa";
                this.blnGerGenEnvCor = false;
            }

            strAsunto = "NOTIFICACIONES RRHH - " + objParSis.getNombreMenu() + " - " + strAux + " " + strSt;
            mail2.enviarCorreoMasivo(strCorEleTo, strCorEleCC, strCorEleCCO, strAsunto, strMensCorEle);
        } catch (Exception Evt) {
            blnRes = false;
            Evt.printStackTrace();
            objUti.mostrarMsgErr_F1(this, Evt);
        }
        return blnRes;
    }

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butPer;
    private javax.swing.JComboBox cboPer;
    private javax.swing.JComboBox cboPerAAAA;
    private javax.swing.JComboBox cboPerMes;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ZafThreadGUI extends Thread {

        public void run() {
            if (!cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {

            strAux = objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(15);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHKREVFAL, "Revisado");
            vecCab.add(INT_TBL_DAT_FECREVFAL, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTREVFAL, "Muestra el \"Listado de faltas de empleados (Por mes)...\"");
            vecCab.add(INT_TBL_DAT_CODEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_BUTROL, "Muestra el \"Listado de faltas de empleados (Por mes)...\"");

            vecCab.add(INT_TBL_DAT_CHKAUT, "Autorizar");
            vecCab.add(INT_TBL_DAT_FECAUT, "Fecha");
            vecCab.add(INT_TBL_DAT_BUTGENARCBCO, "Archivo");
            vecCab.add(INT_TBL_DAT_FECGENARCBCO, "Fec.Gen.Arc.");

            vecCab.add(INT_TBL_DAT_CHKCIEROL, "Cerrado");
            vecCab.add(INT_TBL_DAT_FECCIEROL, "Fecha");

            vecCab.add(INT_TBL_DAT_BUTREVFALIESS, "");

            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            objColNum = new ZafColNumerada(tblDat, INT_TBL_DAT_LIN);
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //Tama�o de las celdas
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHKREVFAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECREVFAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFAL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CODEMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUTROL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setPreferredWidth(50);

            tcmAux.getColumn(INT_TBL_DAT_FECAUT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCBCO).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECGENARCBCO).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CHKCIEROL).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_FECCIEROL).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFALIESS).setPreferredWidth(70);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);

            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDat);

            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblCru = new ZafTblCelRenLbl();
            objTblCelRenLblCru.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCru.setTipoFormato(objTblCelRenLblCru.INT_FOR_NUM);
            objTblCelRenLblCru.setFormatoNumerico("####", false, true);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setCellRenderer(objTblCelRenLblCru);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setCellRenderer(objTblCelRenLblCru);
            objTblCelRenLblCru = null;

            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHKREVFAL);
            vecAux.add("" + INT_TBL_DAT_BUTREVFAL);
            vecAux.add("" + INT_TBL_DAT_BUTROL);
            vecAux.add("" + INT_TBL_DAT_CHKAUT);
            vecAux.add("" + INT_TBL_DAT_BUTGENARCBCO);
            vecAux.add("" + INT_TBL_DAT_BUTREVFALIESS);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFAL).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();//llamarPantallaListadoFaltasEmpleados
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFAL).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTREVFAL:

                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTREVFAL:

                                llamarPantallaListadoFaltasEmpleados(String.valueOf(objParSis.getCodigoEmpresa()),
                                        arrLstAni.get(intFilSel),
                                        arrLstMes.get(intFilSel));

                                break;
                        }
                    }
                }
            });

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTROL).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTROL).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTROL:

                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTROL:

                                boolean blnAd = false;
                                Object obj = tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC);
                                if (obj.toString().compareTo("0") == 0) {
                                    blnAd = false;
                                } else {
                                    blnAd = true;
                                }
                                if (blnAd) {
                                    llamarPantallaRol(String.valueOf(objParSis.getCodigoEmpresa()), String.valueOf(objParSis.getCodigoLocal()), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString()
                                    );
                                }

                                break;
                        }
                    }
                }
            });

            objTblCelRenChkRRHH = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKREVFAL).setCellRenderer(objTblCelRenChkRRHH);
            objTblCelEdiChkRRHH = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKREVFAL).setCellEditor(objTblCelEdiChkRRHH);
            objTblCelEdiChkRRHH.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql = "";
                    String strBlqIni = "";
                    intFilSel = tblDat.getSelectedRow();
                    try {
                        con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if (con != null) {
                            con.setAutoCommit(false);
                            stm = con.createStatement();
                            strSql += " SELECT case (b.st_revfal is null) when true then false::boolean else true::boolean end as blnAut";
                            strSql += " FROM tbm_cabrolpag a";
                            strSql += " LEFT OUTER JOIN tbm_feccorrolpag b on (b.co_emp=a.co_emp and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_per=a.ne_per)";
                            strSql += " WHERE a.co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                            strSql += " AND a.co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                            strSql += " AND a.co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                            strSql += " AND a.co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                            strSql += " AND a.ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                            rst = stm.executeQuery(strSql);
                            while (rst.next()) {
                                boolean bln = (Boolean) rst.getBoolean("blnAut");
                                if (!(bln)) {//falso no esta autorizado //verdadero esta autorizado
                                    strBlqIni = "B";
                                } else {
                                    strBlqIni = "";
                                }
                            }
                        }

                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHKREVFAL);
                        boolean blnAut = (Boolean) objAut;

                        if (blnAut) {
                            objTblCelEdiChkRRHH.setCancelarEdicion(true);
                            objTblCelEdiChkGer.setCancelarEdicion(true);
                        } else {
                            if (blnAut && strBlqIni.compareTo("B") == 0) {
                                objTblCelEdiChkRRHH.setCancelarEdicion(true);
                                objTblCelEdiChkGer.setCancelarEdicion(true);
                            } else {
                                if (objPerUsr.isOpcionEnabled(3755)) {
                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                } else {
                                    objTblCelEdiChkRRHH.setCancelarEdicion(true);
                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            rst.close();
                            rst = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            stm.close();
                            stm = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            con.close();
                            con = null;
                        } catch (Throwable ignore) {
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    objTblCelEdiChkGer.setCancelarEdicion(true);
                    if ((Boolean) tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKREVFAL) == true) {
                        blnPas2 = true;
                        blnPas3 = false;
                    } else {
                        blnPas2 = false;
                        blnPas3 = false;
                    }
                }
            });

            objTblCelRenChkGer = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setCellRenderer(objTblCelRenChkGer);
            objTblCelEdiChkGer = new ZafTblCelEdiChk();
            tcmAux.getColumn(INT_TBL_DAT_CHKAUT).setCellEditor(objTblCelEdiChkGer);
            objTblCelEdiChkGer.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intPosRelColCodEmp;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.ResultSet rst = null;
                    String strSql = "";
                    String strBlqIni = "";
                    intFilSel = tblDat.getSelectedRow();

                    try {
                        con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                        if (con != null) {
                            con.setAutoCommit(false);
                            stm = con.createStatement();
                            strSql += " select case (st_autger is null) when true then false::boolean else true::boolean end as blnAut from tbm_cabrolpag a";
                            strSql += " LEFT OUTER JOIN tbm_feccorrolpag b on (b.co_emp=a.co_emp and b.ne_ani= a.ne_ani and b.ne_mes=a.ne_mes and b.ne_per=a.ne_per)";
                            strSql += " where a.co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                            strSql += " and a.co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                            strSql += " and a.co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                            strSql += " and a.co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                            strSql += " and a.ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                            rst = stm.executeQuery(strSql);
                            while (rst.next()) {
                                boolean bln = (Boolean) rst.getBoolean("blnAut");
                                if (!(bln)) {//falso no esta autorizado //verdadero esta autorizado
                                    strBlqIni = "";
                                } else {
                                    strBlqIni = "B";
                                }
                            }
                        }

                        Object objAut = objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT);
                        boolean blnAut = (Boolean) objAut;

                        boolean blnAd = false;
                        Object obj = tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC);
                        if (obj.toString().compareTo("0") == 0) {
                            blnAd = false;
                        } else {
                            blnAd = true;
                        }

                        if (blnAut && strBlqIni.compareTo("B") == 0 && blnAd) {
                            objTblCelEdiChkRRHH.setCancelarEdicion(true);
                            objTblCelEdiChkGer.setCancelarEdicion(true);
                        } else {
                            if (objPerUsr.isOpcionEnabled(3756)) {
//                                boolean bln = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                                boolean bln = ((Boolean) tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKREVFAL) && blnAd);
                                if (bln) {
                                    blnPas2 = true;
                                    if (!blnPas2) {
                                        objTblCelEdiChkGer.setCancelarEdicion(true);
                                    } else {
                                        blnPas3 = true;
                                        blnPas2 = false;
                                    }
                                } else {
                                    objTblCelEdiChkGer.setCancelarEdicion(true);
                                }
                            } else {
                                objTblCelEdiChkGer.setCancelarEdicion(true);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            rst.close();
                            rst = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            stm.close();
                            stm = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            con.close();
                            con = null;
                        } catch (Throwable ignore) {
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelRenButGenArcBco = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTGENARCBCO).setCellRenderer(zafTblDocCelRenBut);

            ZafTableColBut_uni zafTableColBut_uni = new ZafTableColBut_uni(tblDat, INT_TBL_DAT_BUTGENARCBCO, "...") {
                int intFilSel = tblDat.getSelectedRow();

                public void butCLick() {
                    int intFilSel = tblDat.getSelectedRow();
//                    blnPas2 = (Boolean)tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUTRRHH);
                    blnPas3 = (Boolean) tblDat.getValueAt(intFilSel, INT_TBL_DAT_CHKAUT);

                    java.sql.Connection con = null;
                    java.sql.Statement stm = null;
                    java.sql.Statement stmAux = null;
                    java.sql.Statement stmAux2 = null;
                    java.sql.ResultSet rst = null;
                    java.sql.ResultSet rstAux = null;
                    boolean blnRes = true;
                    String strSql = "";
                    try {
                        if (objPerUsr.isOpcionEnabled(3757)) {
                            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
                            if (con != null) {
                                con.setAutoCommit(false);
                                stm = con.createStatement();
                                stmAux = con.createStatement();
                                stmAux2 = con.createStatement();

                                blnPas4 = true;
                                if (1 == 1) {
//                                        if(blnPas3&&blnPas4){

                                    String strArc = directorioArchivoTXT();

                                    if (strArc != null) {

                                        strSql = "";
                                        strSql += " SELECT a.co_emp , a.ne_ani , a.ne_mes , a.ne_per , st_revfal , fe_autrevfal ";
                                        strSql += " FROM tbm_cabrolpag a";
                                        strSql += " LEFT OUTER JOIN tbm_feccorrolpag b on (b.co_emp=a.co_emp and b.ne_ani=a.ne_ani and b.ne_mes=a.ne_mes and b.ne_per=a.ne_per)";
                                        strSql += " WHERE a.co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                        strSql += " AND a.co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                        strSql += " AND a.co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                        strSql += " AND a.co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                        strSql += " AND a.ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();

                                        rstAux = stmAux.executeQuery(strSql);
                                        String strNeAni = "";
                                        String strNeMes = "";
                                        String strNePer = "";
                                        if (rstAux.next()) {
                                            strNeAni = rstAux.getString("ne_ani");
                                            strNeMes = rstAux.getString("ne_mes");
                                            strNePer = rstAux.getString("ne_per");
                                        }

                                        if (generaArchivoBAN(con, strArc, strNeAni, strNeMes, strNePer)) {

                                            strSql = "";
                                            strSql += " select fe_genarcban as  fe_genarcban ";
                                            strSql += " from tbm_cabrolpag where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString();
                                            strSql += " and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString();
                                            strSql += " and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString();
                                            strSql += " and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString();
                                            strSql += " and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString();
                                            rstAux = stmAux.executeQuery(strSql);
                                            while (rstAux.next()) {
                                                String strFeGenArcBan = rstAux.getString("fe_genarcban");
                                                if (strFeGenArcBan == null) {
                                                    strSql = "";
                                                    strSql += "update tbm_cabrolpag  set fe_genarcban = current_timestamp , co_usrgenarcban = " + objParSis.getCodigoUsuario() + " , \n";
                                                    strSql += " tx_comgenarcban = " + objUti.codificar(objParSis.getDireccionIP()) + " \n";
                                                    strSql += " where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + " \n";
                                                    strSql += " and co_loc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString() + " \n";
                                                    strSql += " and co_tipdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString() + " \n";
                                                    strSql += " and co_doc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString() + " \n";
                                                    strSql += " and ne_numdoc = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString() + " \n";
                                                    System.out.println("updateblnPas2: " + strSql);
                                                    stmAux2.executeUpdate(strSql);

                                                    if (cerrarPeriodo(con, tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString())) {
                                                        blnRes = true;
//                                                                    blnPas5=true;
                                                        blnRRHHEnvCor = false;
                                                        blnGerGenEnvCor = false;
                                                        blnGerAdmEnvCor = true;
                                                    }
                                                }
                                            }
                                        } else {
                                            con.rollback();
                                            blnRes = false;
                                            mostrarMsgErr("ARCHIVO NO FUE GENERADO");
                                        }
                                    } else {
                                        con.rollback();
                                        blnRes = false;
                                        mostrarMsgErr("NO SE HA ELEGIDO UN DIRECTORIO VALIDO");
                                    }
                                } else {
                                    con.rollback();
                                    blnRes = false;
                                    mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA LA ACREEDITACION BANCARIA");
                                }
                            }
                            if (blnRes) {
                                if (blnEnvioCorreo(objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_DESCORTIPDOC).toString())) {
                                    con.commit();
                                    mostrarMsgInf("ARCHIVO GENERADO CORRECTAMENTE");
                                } else {
                                    con.rollback();
                                }
                            } else {
                                con.rollback();
                            }
                            objThrGUI = null;
                            if (objThrGUI == null) {
                                objThrGUI = new ZafThreadGUI();
                                objThrGUI.start();
                            }
                        } else {
                            mostrarMsgInf("NO TIENE PERMISOS PARA GENERAR EL ARCHIVO PARA LA ACREEDITACION BANCARIA");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        objUti.mostrarMsgErr_F1(ZafRecHum74.this, e);
                    } finally {
                        try {
                            rst.close();
                            rst = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            stm.close();
                            stm = null;
                        } catch (Throwable ignore) {
                        }
                        try {
                            con.close();
                            con = null;
                        } catch (Throwable ignore) {
                        }
                    }
                }
            };

            zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFALIESS).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();//llamarPantallaListadoFaltasEmpleados
            tcmAux.getColumn(INT_TBL_DAT_BUTREVFALIESS).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTREVFALIESS:

                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTREVFALIESS:

                                llamarPantallaListadoFaltasEmpleados(String.valueOf(objParSis.getCodigoEmpresa()),
                                        arrLstAni.get(intFilSel),
                                        arrLstMes.get(intFilSel));

                                break;
                        }
                    }
                }
            });

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private String directorioArchivoTXT() {
        boolean blnRes = true;
        String strArc = null;
        try {
            JFileChooser objFilCho = new JFileChooser();
            objFilCho.setDialogTitle("Guardar");
            objFilCho.setFileSelectionMode(JFileChooser.FILES_ONLY);
            if (!System.getProperty("os.name").equals("Linux")) {
                objFilCho.setCurrentDirectory(new File("C:\\"));
            } else {
                objFilCho.setCurrentDirectory(new File("/tmp"));
            }
            FileNameExtensionFilter objFilNamExt = new FileNameExtensionFilter("Archivos TXT", "txt");
            objFilCho.setFileFilter(objFilNamExt);
            if (objFilCho.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                strArc = objFilCho.getSelectedFile().getPath();
                //Si no tiene la extensión "txt" agregarsela.
                if (!strArc.toLowerCase().endsWith(".txt")) {
                    strArc += ".txt";
                }
            } else {
                System.out.println("El usuario canceló");
            }
        } catch (Exception e) {
            System.out.println("Excepción: " + e.toString());
            blnRes = false;
        }
        return strArc;
    }

    private boolean agregarColTblDat() {

        int i, intNumFil, intNumColTblDat;
        ZafTblHeaGrp objTblHeaGrp = (ZafTblHeaGrp) tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16 * 2);
        ZafTblHeaColGrp objTblHeaColGrpEmp = null;
        java.awt.Color colFonCol;
        boolean blnRes = true;

        try {
            intNumFil = objTblMod.getRowCountTrue();
            intNumColTblDat = objTblMod.getColumnCount();

//            objTblHeaColGrpEmp=new ZafTblHeaColGrp("");
//            objTblHeaColGrpEmp.setHeight(16);
//            
            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 1: Revisión de faltas");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_LIN));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKREVFAL));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECREVFAL));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTREVFAL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 2: Roles de pago");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODEMP));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODLOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_DESCORTIPDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CODDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_NUMDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECDOC));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTROL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 3: Autorización");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUT));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECAUT));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 4: Banco");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTGENARCBCO));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECGENARCBCO));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Paso 5: Cierre");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKCIEROL));
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_FECCIEROL));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);

            objTblHeaColGrpEmp = new ZafTblHeaColGrp("Pas 6: IESS");
            objTblHeaColGrpEmp.setHeight(16);
            objTblHeaColGrpEmp.add(tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTREVFALIESS));
            objTblHeaGrp.addColumnGroup(objTblHeaColGrpEmp);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private String setMes() {
        String strMes = "";

        int intPosMes = intMes - 1;
//        strContraPartida+=arrLstMeses.get(intPosMes);
//        strMesArc=arrLstMeses.get(intPosMes);
//        this.strMes = strMesArc;

        arrLstMeses = new ArrayList<String>();
        arrLstMeses.add("ENERO");
        arrLstMeses.add("FEBRERO");
        arrLstMeses.add("MARZO");
        arrLstMeses.add("ABRIL");
        arrLstMeses.add("MAYO");
        arrLstMeses.add("JUNIO");
        arrLstMeses.add("JULIO");
        arrLstMeses.add("AGOSTO");
        arrLstMeses.add("SEPTIEMBRE");
        arrLstMeses.add("OCTUBRE");
        arrLstMeses.add("NOVIEMBRE");
        arrLstMeses.add("DICIEMBRE");

        return arrLstMeses.get(intPosMes).toString();
    }

    private boolean generaArchivoBAN(Connection con, String strArc, String ne_ani, String ne_mes, String ne_per) {

        boolean blnRes = true;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "";

        String strTab = "	";

        int intFilSel = tblDat.getSelectedRow();

        int intCodTipDoc = -1;

        int intCodEmpRelBan = -1;
        int intCodLocRelBan = -1;
        int intTipDocRelBan = 30;//DIGECO
        int intCodDocRelBan = -1;

        String strMesArc = "";
        String strAniArc = "";

        double dblValTot = 0;

        String strCodigoOrientacion = "PA";
        String strContraPartida = "";//1ERA QUINC MAY 2012;
        String strMoneda = "USD";
        String strValor = "";
        String strFormaPago = "CTA";
        String strTipoCuenta = "";
        String strNumeroCuenta = "";
        String strReferencia = strContraPartida;
        String strTipoIDBeneficiario = "C";
        String strNumeroIDClienteBeneficiario = "";//cedula
        String strNombreBeneficiario = "";
        String strCodigoInstitucionFinanciera = "32";

        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stm = con.createStatement();
                Calendar cal = Calendar.getInstance();
                File archivoSueldos = null;

                String strRutArcLog = strArc;
                String strQFDM = "";

                if (ne_per.compareTo("1") == 0) {
                    strQFDM += "1ERA QUINC ";
                } else if (ne_mes.compareTo("2") == 0) {
                    strQFDM += "2DA QUINC ";
                }

                arrLstMeses = new ArrayList<String>();
                arrLstMeses.add("ENERO");
                arrLstMeses.add("FEBRERO");
                arrLstMeses.add("MARZO");
                arrLstMeses.add("ABRIL");
                arrLstMeses.add("MAYO");
                arrLstMeses.add("JUNIO");
                arrLstMeses.add("JULIO");
                arrLstMeses.add("AGOSTO");
                arrLstMeses.add("SEPTIEMBRE");
                arrLstMeses.add("OCTUBRE");
                arrLstMeses.add("NOVIEMBRE");
                arrLstMeses.add("DICIEMBRE");

                if (ne_mes != null) {
                    int intPosMes = Integer.parseInt(ne_mes) - 1;
                    strContraPartida += arrLstMeses.get(intPosMes);
                    strMesArc = arrLstMeses.get(intPosMes);
//                    this.strMes = strMesArc;
                }

                if (ne_ani != null) {
                    strContraPartida += ne_ani;
                    strAniArc = ne_ani;
                }

//                strContraPartida=strQFDM+cal.get(Calendar.YEAR);
                strReferencia = strContraPartida;

                archivoSueldos = new File(strRutArcLog);
                archivoSueldos.delete();
                ArchivosTuval logSueldos = new ArchivosTuval(strRutArcLog);
                String strLog = new String("windows-1252");

                strSql = "";
                strSql += "select a2.co_tra , a4.tx_ide as cedula, (a4.tx_ape || ' ' || a4.tx_nom) as empleado , a3.tx_tipctaban, a3.tx_numctaban, sum(case nd_valrub is null when true then 0 else a2.nd_valrub end) as nd_valPag --a4.tx_ide as cedula, (a4.tx_ape || ' ' || a4.tx_nom) as empleado,  sum(a2.nd_valrub) as nd_valPag, a3.tx_tipctaban, a3.tx_numctaban " + "\n";
                strSql += "from tbm_cabrolpag a1 " + "\n";
                strSql += "inner join tbm_detrolpag a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc) " + "\n";
                strSql += "inner join tbm_traemp a3 on (a3.co_emp=a2.co_emp and a3.co_tra=a2.co_tra) " + "\n";
                strSql += "inner join tbm_tra a4 on (a4.co_tra=a3.co_tra) " + "\n";
                strSql += "where a1.co_emp = " + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + " \n";
                strSql += "and a1.co_loc = " + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString() + " \n";
                strSql += "and a1.co_tipdoc = " + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString() + " \n";
                strSql += "and a1.co_doc = " + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString() + " \n";
                strSql += "and a1.ne_numdoc = " + objTblMod.getValueAt(intFilSel, INT_TBL_DAT_NUMDOC).toString() + " \n";
                strSql += "and a3.tx_tipctaban is not null" + "\n";
                strSql += "group by a2.co_tra , cedula, empleado, a3.tx_tipctaban, a3.tx_numctaban" + "\n";
                strSql += "ORDER BY empleado ";
                rst = stm.executeQuery(strSql);

                intCodEmpRelBan = Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString());
                intCodLocRelBan = Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString());
                intCodTipDoc = Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString());

                dblValTot = 0;

                while (rst.next()) {

                    strTipoCuenta = rst.getString("tx_tipctaban");
                    strNumeroCuenta = rst.getString("tx_numctaban");

                    if (strTipoCuenta.equals("A")) {
                        strTipoCuenta = "AHO";
                    } else {
                        strTipoCuenta = "CTE";
                    }

                    strNumeroIDClienteBeneficiario = rst.getString("cedula");

                    strValor = objUti.parseString(objUti.redondear(rst.getString("nd_valPag"), objParSis.getDecimalesMostrar()));
                    dblValTot += objUti.redondear(rst.getString("nd_valPag"), objParSis.getDecimalesMostrar());

                    strValor = retornaValorStr(strValor);

                    strNombreBeneficiario = rst.getString("empleado");
                    if (Double.parseDouble(strValor) > 0) {
                        strLog = strCodigoOrientacion + strTab + strContraPartida + strTab + strMoneda + strTab + strValor + strTab + strFormaPago + strTab + strTipoCuenta + strTab;
                        strLog += strNumeroCuenta + strTab + strReferencia + strTab + strTipoIDBeneficiario + strTab + strNumeroIDClienteBeneficiario + strTab;
                        strLog += strNombreBeneficiario + strTab + strCodigoInstitucionFinanciera;
                        System.out.println(strLog);
                        logSueldos.println(strLog);
                    }
                }
            }

            if (blnRes && ne_per.compareTo("2") == 0) {
                String strFecGenArc = "";
                Object obj = tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_FECGENARCBCO);
                if (obj == null) {
                    this.strMes = strMesArc;
                    if (generaAsientoDiarioRelacionadoBanco(intCodTipDoc, intCodEmpRelBan, intCodLocRelBan, intTipDocRelBan, ne_ani, ne_per, dblValTot)) {
                        if (objAsiDia.isDiarioCuadrado()) {

                            int intCoDia = -1;

                            strSql = "";
                            strSql += "select max(co_dia)+1 as max  from tbm_cabdia " + "\n";
                            strSql += "where co_emp = " + intCodEmpRelBan + "\n";
                            strSql += "and co_loc = " + intCodLocRelBan + "\n";
                            strSql += "and co_tipdoc= " + intTipDocRelBan;

                            rst = stm.executeQuery(strSql);
                            if (rst.next()) {
                                intCoDia = rst.getInt("max");
                            }

                            Date d = new Date();
                            if (objAsiDia.insertarDiario(con, intCodEmpRelBan, intCodLocRelBan, intTipDocRelBan, "" + intCoDia, new Date())) {

                                strSql = "";
                                strSql += "update tbm_feccorrolpag SET " + " \n";
                                if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString().compareTo("192") == 0) {
                                    strSql += " co_locRelBan = " + intCodLocRelBan + " , \n";
                                    strSql += " co_tipDocRelBan  = " + intTipDocRelBan + " , \n";
                                    strSql += " co_docRelBan  = " + intCoDia + " \n";
                                } else if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString().compareTo("199") == 0) {
                                    strSql += " co_locRelBanBon = " + intCodLocRelBan + " , \n";
                                    strSql += " co_tipDocRelBanBon  = " + intTipDocRelBan + " , \n";
                                    strSql += " co_docRelBanBon  = " + intCoDia + " \n";
                                } else if (tblDat.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_CODTIPDOC).toString().compareTo("202") == 0) {
                                    strSql += " co_locRelBanMov = " + intCodLocRelBan + " , \n";
                                    strSql += " co_tipDocRelBanMov  = " + intTipDocRelBan + " , \n";
                                    strSql += " co_docRelBanMov  = " + intCoDia + " \n";
                                }
                                strSql += " where co_emp = " + tblDat.getValueAt(intFilSel, INT_TBL_DAT_CODEMP).toString() + " \n";
                                strSql += " and ne_ani = " + ne_ani + " \n";
                                strSql += " and ne_mes = " + ne_mes + " \n";
                                strSql += " and ne_per = " + ne_per;
                                stm.executeUpdate(strSql);
                                blnRes = true;
                            } else {
                                blnRes = false;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        } finally {
            try {
                rst.close();
                rst = null;
            } catch (Throwable ignore) {
            }
            try {
                stm.close();
                stm = null;
            } catch (Throwable ignore) {
            }
            try {
                con.close();
                con = null;
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }

    private boolean generaAsientoDiarioRelacionadoBanco(int intCodTipDoc, int intCodEmpRelBan, int intCodLocRelBan, int intTipDocRelBan, String strAño, String strNePer, double dblValTot) {
        boolean blnRes = true;

        Vector vecAsiDia;
        Vector vecTipDoc, vecDetDiario = null; //Vector que contiene el codigo de tipos de documentos    

        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        String strTipDocDes = "";

        BigDecimal bgdDebe, bgdHaber;
        int INT_LINEA = 0; //0) Línea: Se debe asignar una cadena vacía o null. 
        int INT_VEC_CODCTA = 1; //1) Código de la cuenta (Sistema). 
        int INT_VEC_NUMCTA = 2; //2) Número de la cuenta (Preimpreso). 
        int INT_VEC_BOTON = 3; //3) Botón de consulta: Se debe asignar una cadena vacía o null. 
        int INT_VEC_NOMCTA = 4; //4) Nombre de la cuenta. 
        int INT_VEC_DEBE = 5; //5) Debe. 
        int INT_VEC_HABER = 6; //6) Haber. 
        int INT_VEC_REF = 7; //7) Referencia: Se debe asignar una cadena vacía o null
        int INT_VEC_NUEVO = 8;

        try {

            objAsiDia = new ZafAsiDia(objParSis);
            objAsiDia.inicializar();

            vecAsiDia = new Vector();

            if (vecDetDiario == null) {
                vecDetDiario = new java.util.Vector();
            } else {
                vecDetDiario.removeAllElements();
            }

            java.util.Vector vecReg = new Vector();

            if (intCodTipDoc == 192) {
                strTipDocDes = "PAGOS";
            } else if (intCodTipDoc == 199) {
                strTipDocDes = "PRESTAMOS FUNCIONARIOS";
            } else if (intCodTipDoc == 202) {
                strTipDocDes = "REEMBOLSO POR GASTOS";
            }

            if (strNePer.compareTo("1") == 0) {
                if (intCodTipDoc == 192) {
                    objAsiDia.setGlosa("ACREEDITACIóN BANCARIA - ROL DE " + strTipDocDes + " - 1ERA QUINCENA " + this.strMes + "/" + strAño + "");
                } else {
                    objAsiDia.setGlosa("ACREEDITACIóN BANCARIA - " + strTipDocDes + " - 1ERA QUINCENA " + this.strMes + "/" + strAño + "");
                }

            } else if (strNePer.compareTo("2") == 0) {
                if (intCodTipDoc == 192) {
                    objAsiDia.setGlosa("ACREEDITACIóN BANCARIA - ROL DE " + strTipDocDes + " - 2DA QUINCENA " + this.strMes + "/" + strAño + "");
                } else {
                    objAsiDia.setGlosa("ACREEDITACIóN BANCARIA - " + strTipDocDes + " - 1ERA QUINCENA " + this.strMes + "/" + strAño + "");
                }

            }

            //tony validación para cosenco
            boolean blnIsCosenco=false;
            blnIsCosenco = (objParSis.getNombreEmpresa().toUpperCase().indexOf("COSENCO")> -1) ? true: false;
            
            String strCodCtaDeb = "";
            String strDesCorDeb = "";
            String strDesLarDeb = "";

            String strCodCtaHab = "";
            String strDesCorHab = "";
            String strDesLarHab = "";
            if (!blnIsCosenco) {
                
            
            if (intCodEmpRelBan == 1) {

                strCodCtaDeb = "1074";
                strDesCorDeb = "2.01.08.01.01";
                strDesLarDeb = "REMUNERACIONES POR PAGAR";

                strCodCtaHab = "3236";
                strDesCorHab = "1.01.01.02.14";
                strDesLarHab = "BANCO INTERNACIONAL";

            } else if (intCodEmpRelBan == 2) {

                strCodCtaDeb = "255";
                strDesCorDeb = "2.01.08.01.01";
                strDesLarDeb = "REMUNERACIONES POR PAGAR";

                strCodCtaHab = "1352";
                strDesCorHab = "1.01.01.02.15";
                strDesLarHab = "BANCO INTERNACIONAL CTA CTE#140060887 5";

            } else if (intCodEmpRelBan == 4) {

                strCodCtaDeb = "1020";
                strDesCorDeb = "2.01.08.01.01";
                strDesLarDeb = "REMUNERACIONES POR PAGAR";

                strCodCtaHab = "2384";
                strDesCorHab = "1.01.01.02.16";
                strDesLarHab = "BANCO INTERNACIONAL CTA CTE#140060886 7";

            }
            }else{
            if (intCodEmpRelBan == 1) {

                strCodCtaDeb = "1074";
                strDesCorDeb = "2.01.08.01.01";
                strDesLarDeb = "REMUNERACIONES POR PAGAR";

                strCodCtaHab = "1969";
                strDesCorHab = "1.01.01.02.09";
                strDesLarHab = "BANCO INTERNACIONAL CTA CTE # 140060907 3";
            }
            }
            vecReg = new Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, strCodCtaDeb);
            vecReg.add(INT_VEC_NUMCTA, strDesCorDeb);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strDesLarDeb);
            vecReg.add(INT_VEC_DEBE, dblValTot);
            vecReg.add(INT_VEC_HABER, new Double(0));
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            vecReg = new Vector();
            vecReg.add(INT_LINEA, null);
            vecReg.add(INT_VEC_CODCTA, strCodCtaHab);
            vecReg.add(INT_VEC_NUMCTA, strDesCorHab);
            vecReg.add(INT_VEC_BOTON, null);
            vecReg.add(INT_VEC_NOMCTA, strDesCorHab);

            vecReg.add(INT_VEC_DEBE, new Double(0));
            vecReg.add(INT_VEC_HABER, dblValTot);
            vecReg.add(INT_VEC_REF, null);
            vecReg.add(INT_VEC_NUEVO, null);
            vecDetDiario.add(vecReg);

            objAsiDia.setDetalleDiario(vecDetDiario);

        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }

        return blnRes;
    }

    public boolean cerrarPeriodo(java.sql.Connection con, String strCoTipDoc) {
        boolean blnRes = false;
        String strSql = "";
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;

        try {
            if (con != null) {

                stmLoc = con.createStatement();
                String strCoGrp = "";

                if (strCoTipDoc.compareTo("192") == 0) {
                    strCoGrp = "1";
                } else if (strCoTipDoc.compareTo("199") == 0) {
                    strCoGrp = "2";
                } else if (strCoTipDoc.compareTo("202") == 0) {
                    strCoGrp = "3";
                }

                strSql = "update tbm_ingegrmentra set st_rolpaggen='S'";
                strSql += " where co_emp = " + objParSis.getCodigoEmpresa();
                strSql += " and ne_ani = " + intAño;
                strSql += " and ne_mes = " + intMes;
                if (intNePer == 1) {
                    strSql += " and ne_per = " + intNePer;
                } else {
                    strSql += " and ne_per IN  (1,2)";
                }

                strSql += " and co_rub in (select distinct co_rub from tbm_rubrolpag where co_grp=" + strCoGrp + ")";
                System.out.println("query actualizacion st_rolpaggen rol pagos: " + strSql);
                stmLoc.executeUpdate(strSql);
                blnRes = true;
            }
        } catch (java.sql.SQLException Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            Evt.printStackTrace();
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } finally {
            try {
                stmLoc.close();
            } catch (Throwable ignore) {
            }
            try {
                rstLoc.close();
            } catch (Throwable ignore) {
            }
        }
        return blnRes;
    }

    private String retornaValorStr(String strValor) {
        String cadena = strValor;
        String cadena2 = "";
        int car = cadena.indexOf(".");
        int limite = 0;
        if (car > 0) {
            cadena2 = cadena.substring(car + 1);
        }

        limite = (cadena2.length() > 0) ? cadena2.length() : 2;

        if (limite % 2 != 0) {
            for (int i = 0; i < limite; i++) {
                cadena += "0";
            }
        }

        cadena = cadena.replace(".", "");
        strValor = cadena;
        return strValor;
    }

    private void llamarPantallaRol(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNeNumDoc) {
        RecursosHumanos.ZafRecHum37.ZafRecHum37 obj1 = new RecursosHumanos.ZafRecHum37.ZafRecHum37(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strNeNumDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    private void llamarPantallaListadoFaltasEmpleados(String strCodEmp, String strNeAni, String strNeMes) {
        RecursosHumanos.ZafRecHum42.ZafRecHum42 obj1 = new RecursosHumanos.ZafRecHum42.ZafRecHum42(objParSis, strCodEmp, strNeAni, strNeMes);
//        if(obj1.existeFaltasPeriodo()){
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
//        }
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;

                case INT_TBL_DAT_CHKREVFAL:
                    strMsg = "Faltas revisadas";
                    break;
                case INT_TBL_DAT_FECREVFAL:
                    strMsg = "Fecha de revisión de faltas";
                    break;
                case INT_TBL_DAT_BUTREVFAL:
                    strMsg = "Muestra el \"Listado de faltas de empleados (Por mes)...\"";
                    break;
                case INT_TBL_DAT_CODEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_CODLOC:
                    strMsg = "Código del local";
                    break;
                case INT_TBL_DAT_CODTIPDOC:
                    strMsg = "Código del Tipo de Documento";
                    break;
                case INT_TBL_DAT_DESCORTIPDOC:
                    strMsg = "Descripción corta del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOC:
                    strMsg = "Número de documento";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_BUTROL:
                    strMsg = "Muestra el \"Rol de pagos\"";
                    break;
                case INT_TBL_DAT_CHKAUT:
                    strMsg = "Autorizar";
                    break;
                case INT_TBL_DAT_FECAUT:
                    strMsg = "Fecha de autorización";
                    break;
                case INT_TBL_DAT_BUTGENARCBCO:
                    strMsg = "Generación del archivo para el Banco";
                    break;
                case INT_TBL_DAT_FECGENARCBCO:
                    strMsg = "Fecha de generación del archivo";
                    break;
                case INT_TBL_DAT_CHKCIEROL:
                    strMsg = "Rol de pagos cerrado";
                    break;
                case INT_TBL_DAT_FECCIEROL:
                    strMsg = "Fecha de cierre del rol de pagos";
                    break;
                case INT_TBL_DAT_BUTREVFALIESS:
                    strMsg = "Muestra el \"Listado de faltas de empleados (Por mes)...\"";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean cargarReg() {
        boolean blnRes = true;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                if (cargarDetReg()) {
                }
                con.close();
                con = null;
            }
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    private boolean asignacionPeriodo() {
        boolean blnRes = false;
        java.sql.Connection con = null;
        java.sql.Statement stm = null;
        java.sql.ResultSet rst = null;
        String strSql = "";
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                con.setAutoCommit(false);
                //David ->consultar fecha de la base de Datos
//                RRHHDao dao = new RRHHDao(objUti, objParSis);
//                String AniMesPer = dao.conAnioUltimoRolGenerado(con, objParSis.getCodigoEmpresa());
//                intAño = Integer.valueOf(AniMesPer.split(";")[0]);
//                intMes = Integer.valueOf(AniMesPer.split(";")[1]);
//                intNePer = Integer.valueOf(AniMesPer.split(";")[2]);
//                if (intNePer == 2) {
//                    intNePer = 1;
//                    if (intMes == 12) {
//                        intAño++;
//                        intMes = 1;
//                    } else {
//                        intMes++;
//                    }
//                } else {
//                    intNePer = 2;
//                }
//                if (cboPerAAAA.getSelectedIndex() == 0) {
//                    mostrarMsgInf("Seleccione el año.");
//                    return false;
//                }
//                if (cboPerMes.getSelectedIndex() == 0) {
//                    mostrarMsgInf("Seleccione el Mes.");
//                    return false;
//                }
//                if (cboPer.getSelectedIndex() == 0) {
//                    mostrarMsgInf("Seleccione el Periodo.");
//                    return false;
//                }
                intAño = Integer.valueOf(cboPerAAAA.getSelectedItem().toString());
                intMes = cboPerMes.getSelectedIndex();
                intNePer = cboPer.getSelectedIndex();
//                stm=con.createStatement();
//                strSQL="select case when min(ne_per) is null then -1 else min(ne_per) END  as ne_pero from tbm_ingegrmentra where st_rolpaggen is null and ne_per not in (0)";
//                strSQL+=" and co_emp = " + objParSis.getCodigoEmpresa();
//                System.out.println("sql ne_per : "+strSQL);
//                rst=stm.executeQuery(strSQL);
//                if(rst.next()){
//                    intNePer=rst.getInt("ne_pero");
//                }
////------------------------------------------------------------------------------
//                //intNePer=2; //Eddye
////------------------------------------------------------------------------------
//                strSQL="select case when min(ne_ani) is null then 0 else min(ne_ani) END as ne_anio from tbm_ingegrmentra where st_rolpaggen is null";
//                strSQL+=" and co_emp = " + objParSis.getCodigoEmpresa();
//                System.out.println("sql ne_ani : "+strSQL);
//                rst=stm.executeQuery(strSQL);
//                if(rst.next()){
//                    intAño=rst.getInt("ne_anio");
//                }
//                strSQL="select case when min(ne_mes) is null then 0 else min(ne_mes) END as ne_meso from tbm_ingegrmentra where st_rolpaggen is null";
//                strSQL+=" and ne_per not in (0) and co_emp = " + objParSis.getCodigoEmpresa();
//                System.out.println("sql ne_meMejora en consulta query para cargar roles de pago guardados...s : "+strSQL);
//                rst=stm.executeQuery(strSQL);
//
//                if(rst.next()){
//                    intMes=rst.getInt("ne_meso");
//                    blnRes=true;
//                }
                blnRes = true;
            }
        } catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        } finally {
            try {
                stm.close();
                stm = null;
            } catch (Throwable ignore) {
            }
            try {
                con.close();
                con = null;
            } catch (Throwable ignore) {
            }
        }

        return blnRes;
    }

    private boolean cargarDetReg() {
        boolean blnRes = true;
        int i;
        int intNumReg = 0;

        try {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null) {
                stm = con.createStatement();

                arrLstAni = new ArrayList<String>();
                arrLstMes = new ArrayList<String>();
                strSQL = "";

                if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {

                    strSQL += "SELECT a1.co_emp, a3.co_loc, a2.co_tipdoc, a3.co_doc, a3.ne_numdoc, a1.ne_ani , a1.ne_mes , a1.ne_per , a3.st_reg, a3.fe_doc, a3.fe_ing, " + "\n";
                    strSQL += "a1.st_revfal , a1.fe_autrevfal, a1.co_usrrevfal, a1.st_cie ,  " + "\n";
                    strSQL += "case ((a1.ne_per=2) and (a3.co_doc is not null) and (a1.st_cie is not null)) when true then (EXTRACT(YEAR FROM current_date)||'-'||EXTRACT(MONTH FROM current_date)||'-'||'04') else null end as fe_cie,  " + "\n";
                    strSQL += "a3.st_autrechum, a3.fe_autrechum, a3.co_usrautrechum, a3.st_autger, a3.fe_autger, a3.co_usrautger, a3.tx_comautger,   " + "\n";
                    strSQL += "a3.fe_genarcmrl, a3.co_usrgenarcmrl, a3.tx_comgenarcmrl, a3.fe_genarcban, a3.co_usrgenarcban, a3.tx_comgenarcban,    " + "\n";
                    strSQL += "a2.tx_descor, a2.tx_deslar   " + "\n";
                    strSQL += "from   tbm_feccorrolpag a1 " + "\n";
                    strSQL += "LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a2.co_emp=a1.co_emp) " + "\n";
                    strSQL += "LEFT OUTER JOIN tbm_cabrolpag a3 ON (a3.co_emp=a1.co_emp and a3.co_tipdoc=a2.co_tipdoc and a3.ne_ani=a1.ne_ani and a3.ne_mes=a1.ne_mes and a3.ne_per=a1.ne_per and a3.st_reg='A')  " + "\n";
                    strSQL += "where a1.co_emp= " + objParSis.getCodigoEmpresa() + "\n";
                    strSQL += "and a2.co_loc= " + objParSis.getCodigoLocal() + "\n";
                    strSQL += "and a1.ne_ani = " + intAño + "\n";
                    strSQL += "and a1.ne_mes = " + intMes + "\n";
                    strSQL += "and a1.ne_per = " + intNePer + "\n";
//                    strSQL+="and a1.ne_ani = 2014 \n";
//                    strSQL+="and a1.ne_mes = 2 \n";
//                    strSQL+="and a1.ne_per =  2 \n";
                    if (objParSis.getCodigoUsuario() == 220) {
                        strSQL += "and a2.co_tipdoc IN ( 192 ) " + "\n";
                    } else {
                        strSQL += "and a2.co_tipdoc IN ( 192 , 199 , 202 ) " + "\n";
                    }

                    strSQL += "ORDER BY  a3.fe_doc , a2.co_tipdoc ";
                } else {

                    strSQL += "SELECT a1.co_emp, a3.co_loc, a2.co_tipdoc, a3.co_doc, a3.ne_numdoc, a1.ne_ani , a1.ne_mes , a1.ne_per , a3.st_reg, a3.fe_doc, a3.fe_ing, " + "\n";
                    strSQL += "a1.st_revfal , a1.fe_autrevfal, a1.co_usrrevfal, a1.st_cie ,  " + "\n";
                    strSQL += "case ((a1.ne_per=2) and (a3.co_doc is not null) and (a1.st_cie is not null)) when true then (EXTRACT(YEAR FROM current_date)||'-'||EXTRACT(MONTH FROM current_date)||'-'||'04') else null end as fe_cie,  " + "\n";
                    strSQL += "a3.st_autrechum, a3.fe_autrechum, a3.co_usrautrechum, a3.st_autger, a3.fe_autger, a3.co_usrautger, a3.tx_comautger,   " + "\n";
                    strSQL += "a3.fe_genarcmrl, a3.co_usrgenarcmrl, a3.tx_comgenarcmrl, a3.fe_genarcban, a3.co_usrgenarcban, a3.tx_comgenarcban,    " + "\n";
                    strSQL += "a2.tx_descor, a2.tx_deslar   " + "\n";
                    strSQL += "from   tbm_feccorrolpag a1 " + "\n";
                    strSQL += "LEFT OUTER JOIN tbm_cabTipDoc AS a2 ON (a2.co_emp=a1.co_emp) " + "\n";
                    strSQL += "LEFT OUTER JOIN tbm_cabrolpag a3 ON (a3.co_emp=a1.co_emp and a3.co_tipdoc=a2.co_tipdoc and a3.ne_ani=a1.ne_ani and a3.ne_mes=a1.ne_mes and a3.ne_per=a1.ne_per and a3.st_reg='A')  " + "\n";
                    strSQL += "where a1.co_emp= " + objParSis.getCodigoEmpresa() + "\n";
                    strSQL += "and a2.co_loc= " + objParSis.getCodigoLocal() + "\n";
                    if (objParSis.getCodigoUsuario() == 220) {
                        strSQL += "and a2.co_tipdoc IN ( 192 ) " + "\n";
                    } else {
                        strSQL += "and a2.co_tipdoc IN ( 192 , 199 , 202 ) " + "\n";
                    }

                    strSQL += "and a1.ne_ani = " + intAño + "\n";
                    strSQL += "and a1.ne_mes = " + intMes + "\n";
                    strSQL += "and a1.ne_per = " + intNePer + "\n";
//                    strSQL+="and a1.ne_ani = 2014 \n";
//                    strSQL+="and a1.ne_mes = 2 \n";
//                    strSQL+="and a1.ne_per =  2 \n";
                    strSQL += "ORDER BY  a3.fe_doc , a2.co_tipdoc ";
                }

                System.out.println("SQL cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);

                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setValue(0);
                i = 0;

                lblMsgSis.setText("Listo");

                if (rst.next()) {
                    vecDat = new Vector();
                    do {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_DAT_LIN, "");
                        String strAux = rst.getString("st_revfal");
                        if (strAux == null) {
                            vecReg.add(INT_TBL_DAT_CHKREVFAL, false);
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKREVFAL, true);
                        }

                        vecReg.add(INT_TBL_DAT_FECREVFAL, rst.getString("fe_autrevfal"));
                        vecReg.add(INT_TBL_DAT_BUTREVFAL, "...");

                        vecReg.add(INT_TBL_DAT_CODEMP, rst.getInt("co_emp"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rst.getInt("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC, rst.getInt("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC, rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_CODDOC, rst.getInt("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUMDOC, rst.getInt("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC, rst.getString("fe_doc"));

                        vecReg.add(INT_TBL_DAT_BUTROL, "...");

                        strAux = rst.getString("st_autger");
                        boolean blnGer = false;
                        if (strAux == null) {
                            vecReg.add(INT_TBL_DAT_CHKAUT, false);
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKAUT, true);
                            blnGer = true;
                        }

                        vecReg.add(INT_TBL_DAT_FECAUT, rst.getString("fe_autger"));

                        vecReg.add(INT_TBL_DAT_BUTGENARCBCO, "...");
                        String strFeGenArcBan = rst.getString("fe_genarcban");
                        vecReg.add(INT_TBL_DAT_FECGENARCBCO, strFeGenArcBan);
                        boolean blnGenArcBan = false;
                        if (strFeGenArcBan != null) {
                            blnGenArcBan = true;
                        }

//                        strAux=rst.getString("st_cie");
                        String strFecCieRol = rst.getString("fe_cie");
                        boolean bln1 = false;
                        Object objCoDoc = rst.getString("co_doc");
                        if (objCoDoc != null) {
                            bln1 = true;
                        }
                        boolean bln2 = false;
                        Object objStCie = rst.getString("st_cie");
                        if (objCoDoc != null) {
                            bln2 = true;
                        }

                        if (strFecCieRol != null) {
                            if (bln1 && bln2 && blnGer && blnGenArcBan) {
                                vecReg.add(INT_TBL_DAT_CHKCIEROL, true);
                                String[] strarrFecCieRol = strFecCieRol.toString().split("-");
                                if (strarrFecCieRol[1].length() == 1) {
                                    strarrFecCieRol[1] += "0";
                                }
                                strFecCieRol = strarrFecCieRol[0] + "-" + strarrFecCieRol[1] + "-" + strarrFecCieRol[2];
                                vecReg.add(INT_TBL_DAT_FECCIEROL, strFecCieRol);
                            } else {
                                vecReg.add(INT_TBL_DAT_CHKCIEROL, false);
                                vecReg.add(INT_TBL_DAT_FECCIEROL, null);
                            }
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKCIEROL, false);
                            vecReg.add(INT_TBL_DAT_FECCIEROL, null);
                        }

                        vecReg.add(INT_TBL_DAT_BUTREVFALIESS, "...");

                        arrLstAni.add(i, rst.getString("ne_ani"));
                        arrLstMes.add(i, rst.getString("ne_mes"));

                        vecDat.add(vecReg);
                        i++;
                    } while (rst.next());
                }

                pgrSis.setValue(i);
                intNumReg = i;

                rst.close();
                stm.close();
                rst = null;
                stm = null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                objTblMod.initRowsState();

                lblMsgSis.setText("Se encontraron " + intNumReg + " registros.");
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

    private boolean cargarAnios() {
        boolean blnRes = true;
        java.sql.Connection con = null;
        java.sql.Statement stmLoc = null;
        java.sql.ResultSet rstLoc = null;
        String strSQL = "";
        try {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                RRHHDao dao = new RRHHDao(objUti, objParSis);
                String cadena = dao.conAnioUltimoRolGenerado(con, objParSis.getCodigoEmpresa(), true);
                intUltAni = Integer.valueOf(cadena.split(";")[0]);
                intUltMes = Integer.valueOf(cadena.split(";")[1]);
                intUltPer = Integer.valueOf(cadena.split(";")[2]);

                stmLoc = con.createStatement();
                strSQL = "select distinct ne_ani from tbm_ingEgrMenTra order by ne_ani desc";
                rstLoc = stmLoc.executeQuery(strSQL);
                Calendar c = Calendar.getInstance();
                int aux = -1;
                if (c.get(Calendar.MONTH) == 12) {
                    aux = 0;
                }
                while (rstLoc.next()) {
                    if (aux == 0) {
                        aux = rstLoc.getInt("ne_ani") + 1;
                        cboPerAAAA.addItem(aux);
                    }
                    cboPerAAAA.addItem(rstLoc.getString("ne_ani"));
                    if (intUltAni == rstLoc.getInt("ne_ani")) {
                        cboPerAAAA.setSelectedIndex(cboPerAAAA.getItemCount() - 1);
                    }
                }
                //Pre cargar mes
                for (int i = 0; i < cboPerMes.getItemCount(); i++) {
                    if (i == intUltMes) {
                        cboPerMes.setSelectedIndex(i);
                        break;
                    }
                }
                //Pre cargar Periodo
                for (int i = 0; i < cboPer.getItemCount(); i++) {
                    if (i == intUltPer) {
                        cboPer.setSelectedIndex(i);
                        break;
                    }
                }
            }
        } catch (java.sql.SQLException Evt) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, Evt);
        } catch (Exception Evt) {
            blnRes = false;
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
        return blnRes;
    }
}
