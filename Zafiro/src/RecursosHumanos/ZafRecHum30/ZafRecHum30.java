package RecursosHumanos.ZafRecHum30;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRecHum.ZafRecHumVen.ZafVenMotHorSupExt;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Autorizaciones de horas suplementarias/extraordinarias.
 *
 * @author Roberto Flores
 */
public class ZafRecHum30 extends javax.swing.JInternalFrame
{
    final int INT_TBL_DAT_LIN = 0;
    final int INT_TBL_DAT_COEMP = 1;
    final int INT_TBL_DAT_EMP = 2;
    final int INT_TBL_DAT_CODLOC = 3;
    final int INT_TBL_DAT_CODTIPDOC = 4;
    final int INT_TBL_DAT_DESCORTIPDOC = 5;
    final int INT_TBL_DAT_DESLARTIPDOC = 6;
    final int INT_TBL_DAT_CODDOC = 7;
    final int INT_TBL_DAT_NUMDOC = 8;
    final int INT_TBL_DAT_FECDOC = 9;
    final int INT_TBL_DAT_CODEP = 10;
    final int INT_TBL_DAT_DEP = 11;
    final int INT_TBL_DAT_MOTSOL = 12;
    final int INT_TBL_DAT_FECSOL = 13;
    final int INT_TBL_DAT_HORSOLDES = 14;
    final int INT_TBL_DAT_HORSOLHAS = 15;
    final int INT_TBL_DAT_BUTANESOLHSE = 16;
    final int INT_TBL_DAT_CHKAUTPEN = 17;
    final int INT_TBL_DAT_CHKAUTPAR = 18;
    final int INT_TBL_DAT_CHKAUTTOT = 19;
    final int INT_TBL_DAT_HORAUT = 20;
    final int INT_TBL_DAT_BUTANE2 = 21;
    final int INT_TBL_DAT_FEC_HOR_AUT = 22;

    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafColNumerada objColNum;
    private ZafTblMod objTblMod;
    private ZafTblPopMnu objTblPopMnu;                         //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private ZafTblFilCab objTblFilCab;
    private int intCodEmp;                                      //Código de la empresa.
    private int intCodLoc;                                      //Código del local.
    private int intCodTipDoc;                                   //Código del tipo de documento.
    private int intCodDoc;
        private int intCoReg;
    private String strFecha;

    private Vector vecDat, vecCab, vecReg, vecAux;
    private boolean blnCon;                                    //true: Continua la ejecuci�n del hilo.
    private ZafTblCelRenLbl objTblCelRenLblCru, objTblCelRenLblDocRel, objTblCelRenLblNum, objTblCelRenLblCol;
    private ZafTblBus objTblBus;
    private ZafMouMotAda objMouMotAda;
    private ZafTblOrd objTblOrd;
    private ZafSelFec objSelFec;
    private ZafTblCelEdiChk objTblCelEdiChk;
    private ZafTblCelRenChk objTblCelRenChk;
    private ZafTblCelEdiButGen objTblCelEdiButGen;
    private ZafTblCelRenBut objTblCelRenBut;
    private ZafTblCelEdiButDlg objTblCelEdiButDlg;              //Editor: JButton en celda.
    private ZafPerUsr objPerUsr;

    private ZafVenMotHorSupExt zafVenMotHorSupExt;              //Ventana de consulta "Motivos de horas suplementarias/extraordinarias".
    private ZafVenCon objVenConMotHorSupExt;
    private ZafVenCon vcoDep;                                   //Ventana de consulta "Departamentos".
    
    private String strCodDep = "", strDesLarDep = "", strNomDep = "";
    private String strCodMot, strDesLarMot;
    private ZafRecHum30_02 objRecHum30_02;
    public int intPosAct;

    private String strVersion = "v1.12";

    public ZafRecHum30(ZafParSis obj)
    {
        try 
        {
            initComponents();
            this.objParSis = obj;
            objParSis = (ZafParSis) obj.clone();
            objUti = new ZafUtil();
            objPerUsr = new ZafPerUsr(objParSis);

            zafVenMotHorSupExt = new ZafVenMotHorSupExt(JOptionPane.getFrameForComponent(this), objParSis, "Listado de Motivos de horas suplementarias/extraordinarias");

            if (!configurarFrm()) {
                exitForm();
            }
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
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
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panFecCor = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        optFil = new javax.swing.JRadioButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodMot = new javax.swing.JTextField();
        txtDesLarMot = new javax.swing.JTextField();
        butMot = new javax.swing.JButton();
        lblLoc = new javax.swing.JLabel();
        txtCodDep = new javax.swing.JTextField();
        txtDesLarDep = new javax.swing.JTextField();
        butDep = new javax.swing.JButton();
        chkMosDocTotAut = new javax.swing.JCheckBox();
        chkMosDocPenAut = new javax.swing.JCheckBox();
        chkMosDocParAut = new javax.swing.JCheckBox();
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
        btnAut = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panFecCor.setPreferredSize(new java.awt.Dimension(100, 100));
        panFecCor.setLayout(null);
        jPanel1.add(panFecCor, java.awt.BorderLayout.NORTH);

        panFil.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        panFil.setPreferredSize(new java.awt.Dimension(0, 200));
        panFil.setLayout(null);

        optTod.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optTod.setText("Todos los documentos");
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panFil.add(optTod);
        optTod.setBounds(0, 4, 400, 14);

        optFil.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        optFil.setSelected(true);
        optFil.setText("Sólo los documentos que cumplan el criterio seleccionado");
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil);
        optFil.setBounds(0, 20, 400, 14);

        lblTipDoc.setText("Motivo:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panFil.add(lblTipDoc);
        lblTipDoc.setBounds(14, 70, 70, 20);

        txtCodMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodMotActionPerformed(evt);
            }
        });
        txtCodMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodMotFocusLost(evt);
            }
        });
        panFil.add(txtCodMot);
        txtCodMot.setBounds(126, 70, 56, 20);

        txtDesLarMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarMotActionPerformed(evt);
            }
        });
        txtDesLarMot.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarMotFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarMotFocusLost(evt);
            }
        });
        panFil.add(txtDesLarMot);
        txtDesLarMot.setBounds(182, 70, 264, 20);

        butMot.setText("...");
        butMot.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butMotActionPerformed(evt);
            }
        });
        panFil.add(butMot);
        butMot.setBounds(446, 70, 20, 20);

        lblLoc.setText("Departamento:");
        lblLoc.setToolTipText("Proveedor");
        panFil.add(lblLoc);
        lblLoc.setBounds(14, 48, 100, 20);

        txtCodDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodDepActionPerformed(evt);
            }
        });
        txtCodDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodDepFocusLost(evt);
            }
        });
        panFil.add(txtCodDep);
        txtCodDep.setBounds(126, 49, 56, 20);

        txtDesLarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarDepActionPerformed(evt);
            }
        });
        txtDesLarDep.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarDepFocusLost(evt);
            }
        });
        panFil.add(txtDesLarDep);
        txtDesLarDep.setBounds(182, 49, 264, 20);

        butDep.setText("...");
        butDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butDepActionPerformed(evt);
            }
        });
        panFil.add(butDep);
        butDep.setBounds(446, 49, 20, 20);

        chkMosDocTotAut.setText("Mostrar los documentos que están autorizados totalmente");
        chkMosDocTotAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocTotAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocTotAut);
        chkMosDocTotAut.setBounds(10, 160, 440, 20);

        chkMosDocPenAut.setSelected(true);
        chkMosDocPenAut.setText("Mostrar los documentos que están pendientes de autorizar");
        chkMosDocPenAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocPenAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocPenAut);
        chkMosDocPenAut.setBounds(10, 120, 430, 20);

        chkMosDocParAut.setSelected(true);
        chkMosDocParAut.setText("Mostrar los documentos que están autorizados parcialmente");
        chkMosDocParAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkMosDocParAutActionPerformed(evt);
            }
        });
        panFil.add(chkMosDocParAut);
        chkMosDocParAut.setBounds(10, 140, 440, 20);

        jPanel1.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", jPanel1);

        panRpt.setLayout(new java.awt.BorderLayout());

        spnDat.setPreferredSize(new java.awt.Dimension(453, 418));

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

        btnAut.setText("Autorizar");
        btnAut.setPreferredSize(new java.awt.Dimension(92, 25));
        btnAut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAutActionPerformed(evt);
            }
        });
        panBot.add(btnAut);

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

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents


    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        //Realizar acción de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
        objTblMod.removeAllRows();
        lblMsgSis.setText("");
        if (butCon.getText().equals("Consultar")) {
            blnCon = true;
            if (objThrGUI == null) {
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
     * Cerrar la aplicación.
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

private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
    if (optTod.isSelected()) 
    {
        optFil.setSelected(false);
        txtCodDep.setText("");
        txtDesLarDep.setText("");
        txtCodMot.setText("");
        txtCodMot.setText("");
        txtDesLarMot.setText("");
        chkMosDocPenAut.setSelected(true);
        chkMosDocParAut.setSelected(true);
        chkMosDocTotAut.setSelected(true);
    } 
    else
    {
        optFil.setSelected(true);
    }
}//GEN-LAST:event_optTodActionPerformed

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
    if (optFil.isSelected()) {
        optTod.setSelected(false);
        chkMosDocTotAut.setSelected(false);
    } 
    else 
    {
        optTod.setSelected(true);
    }
}//GEN-LAST:event_optFilActionPerformed

private void txtCodMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodMotActionPerformed
    java.sql.Connection conn;
    java.sql.Statement stmLoc;
    java.sql.ResultSet rstLoc;
    String strSql = "";

    try 
    {
        conn = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

        if (conn != null && (txtCodMot.getText().compareTo("") != 0)) 
        {
            stmLoc = conn.createStatement();
            strSql = "select co_mot, tx_deslar from tbm_motHorSupExt where st_reg like 'A' and co_mot =  " + txtCodMot.getText();
            rstLoc = stmLoc.executeQuery(strSql);

            if (rstLoc.next()) {
                txtDesLarMot.setText(rstLoc.getString("tx_deslar"));
                txtDesLarMot.setHorizontalAlignment(2);
            } else {
                mostrarMsgInf("No se encontraron datos con los criterios de búsqueda. \nEspecifique otros criterios y vuelva a intentarlo");
                txtDesLarMot.setText("");
                txtCodMot.setText("");
            }

            rstLoc.close();
            rstLoc = null;
            stmLoc.close();
            stmLoc = null;
            conn.close();
            conn = null;
        }
    } catch (java.sql.SQLException Evt) {
        objUti.mostrarMsgErr_F1(this, Evt);
    }
}//GEN-LAST:event_txtCodMotActionPerformed

private void txtCodMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusGained
    txtCodMot.selectAll();
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_txtCodMotFocusGained

private void txtCodMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodMotFocusLost
    if (!txtDesLarMot.getText().equalsIgnoreCase(strDesLarMot)) {
        if (txtDesLarMot.getText().equals("")) {
            txtCodMot.setText("");
            txtDesLarMot.setText("");
        } else {
            BuscarMotHorSupExt("a.tx_deslar", txtDesLarMot.getText(), 1);
        }
    } else {
        txtDesLarMot.setText(strDesLarMot);
    }
}//GEN-LAST:event_txtCodMotFocusLost

private void txtDesLarMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarMotActionPerformed
    txtDesLarMot.transferFocus();
}//GEN-LAST:event_txtDesLarMotActionPerformed

private void txtDesLarMotFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusGained
    strDesLarMot = txtDesLarMot.getText();
    txtDesLarMot.selectAll();
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_txtDesLarMotFocusGained

private void txtDesLarMotFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarMotFocusLost
    if (!txtDesLarMot.getText().equalsIgnoreCase(strDesLarMot)) {
        if (txtDesLarMot.getText().equals("")) {
            txtCodMot.setText("");
            txtDesLarMot.setText("");
        } else {
            BuscarMotHorSupExt("a.tx_deslar", txtDesLarMot.getText(), 1);
        }
    } else {
        txtDesLarMot.setText(strDesLarMot);
    }
}//GEN-LAST:event_txtDesLarMotFocusLost

private void butMotActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butMotActionPerformed
    try 
    {
        zafVenMotHorSupExt.limpiar();
        zafVenMotHorSupExt.setCampoBusqueda(0);
        zafVenMotHorSupExt.setVisible(true);

        if (zafVenMotHorSupExt.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
            txtCodMot.setText(zafVenMotHorSupExt.getValueAt(1));
            txtDesLarMot.setText(zafVenMotHorSupExt.getValueAt(3));
        }
    } catch (Exception ex) {
        objUti.mostrarMsgErr_F1(this, ex);
    }
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_butMotActionPerformed

private void txtCodDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodDepActionPerformed
    txtCodDep.transferFocus();
}//GEN-LAST:event_txtCodDepActionPerformed

private void txtCodDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusGained
    strCodDep = txtCodDep.getText();
    txtCodDep.selectAll();
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_txtCodDepFocusGained

private void txtCodDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodDepFocusLost
    if (!txtCodDep.getText().equalsIgnoreCase(strCodDep)) {
        if (txtCodDep.getText().equals("")) {
            txtCodDep.setText("");
            txtDesLarDep.setText("");
        } else {
            BuscarDep("a1.co_dep", txtCodDep.getText(), 0);
        }
    } else {
        txtCodDep.setText(strCodDep);
    }
}//GEN-LAST:event_txtCodDepFocusLost

    public void BuscarDep(String campo, String strBusqueda, int tipo) 
    {
        vcoDep.setTitle("Listado de Departamentos");
        if (vcoDep.buscar(campo, strBusqueda)) {
            txtCodDep.setText(vcoDep.getValueAt(1));
            txtDesLarDep.setText(vcoDep.getValueAt(3));
        } else {
            vcoDep.setCampoBusqueda(tipo);
            vcoDep.cargarDatos();
            vcoDep.show();
            if (vcoDep.getSelectedButton() == vcoDep.INT_BUT_ACE) {
                txtCodDep.setText(vcoDep.getValueAt(1));
                txtDesLarDep.setText(vcoDep.getValueAt(3));
            } else {
                txtCodDep.setText(strCodDep);
                txtDesLarDep.setText(strDesLarDep);
            }
        }
    }

private void txtDesLarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarDepActionPerformed
    txtDesLarDep.transferFocus();
}//GEN-LAST:event_txtDesLarDepActionPerformed

private void txtDesLarDepFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusGained
    strDesLarDep = txtDesLarDep.getText();
    txtDesLarDep.selectAll();
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_txtDesLarDepFocusGained

private void txtDesLarDepFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarDepFocusLost
    if (txtDesLarDep.isEditable()) {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtDesLarDep.getText().equalsIgnoreCase(strNomDep)) {
            if (txtDesLarDep.getText().equals("")) {
                txtCodDep.setText("");
                txtDesLarDep.setText("");
            } else {
                mostrarVenConDep(2);
            }
        } else {
            txtDesLarDep.setText(strNomDep);
        }
    }
}//GEN-LAST:event_txtDesLarDepFocusLost

private void butDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butDepActionPerformed
    mostrarVenConDep(0);
    optFil.setSelected(true);
    optTod.setSelected(false);
}//GEN-LAST:event_butDepActionPerformed

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
    private boolean mostrarVenConDep(int intTipBus) 
    {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoDep.setCampoBusqueda(2);
                    vcoDep.setVisible(true);
                    if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    }
                    break;
                case 1: //Búsqueda directa por "Codigo de Departamento".
                    vcoDep.setVisible(true);
                    if (vcoDep.buscar("a1.co_dep", txtCodDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(1);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {

                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesLarDep.setText(strNomDep);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    vcoDep.setCampoBusqueda(2);
                    if (vcoDep.buscar("a1.tx_desLar", txtDesLarDep.getText())) {
                        txtCodDep.setText(vcoDep.getValueAt(1));
                        txtDesLarDep.setText(vcoDep.getValueAt(3));
                    } else {
                        vcoDep.setCampoBusqueda(2);
                        vcoDep.setCriterio1(11);
                        vcoDep.cargarDatos();
                        vcoDep.setVisible(true);
                        if (vcoDep.getSelectedButton() == ZafVenCon.INT_BUT_ACE) {
                            txtCodDep.setText(vcoDep.getValueAt(1));
                            txtDesLarDep.setText(vcoDep.getValueAt(3));
                        } else {
                            txtDesLarDep.setText(strNomDep);
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

private void chkMosDocPenAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocPenAutActionPerformed
    habilitarChkBox(true, false);
}//GEN-LAST:event_chkMosDocPenAutActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        Configura_ventana_consulta();
    }//GEN-LAST:event_formInternalFrameOpened

    private void chkMosDocParAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocParAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocParAutActionPerformed

    private void chkMosDocTotAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkMosDocTotAutActionPerformed
        habilitarChkBox(true, false);
    }//GEN-LAST:event_chkMosDocTotAutActionPerformed

    private void btnAutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAutActionPerformed
        //Bostel Poner codigo para autorizar masivamente
        java.sql.Connection con;
       try{
           
           java.sql.Statement stmLoc , stmLocCab = null;
           java.sql.ResultSet resSet = null, resSetCab = null;
           String strSql = "";
           String strAut = "";
           String strParTot = "";
            String strObs = "AUTORIZADO MASIVO";
           
           con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           con.setAutoCommit(false);
           if (con != null){
           String strHoAutGLBDES = "";
           String strHoAutGLBHAS = "";
           stmLoc = con.createStatement();
           stmLocCab = con.createStatement();
           
          
           
            for (int intFila = 0; intFila < tblDat.getRowCount(); intFila++) {
             String strEstDoc = "";
             //Object fil =  
            // Object fil = tblDat.getValueAt(intFila, INT_TBL_DAT_COEMP);
            String emp = tblDat.getValueAt(intFila, INT_TBL_DAT_COEMP).toString();
           String loc = tblDat.getValueAt(intFila, INT_TBL_DAT_CODLOC).toString();
           String tipdoc = tblDat.getValueAt(intFila, INT_TBL_DAT_CODTIPDOC).toString();
           String codoc = tblDat.getValueAt(intFila,INT_TBL_DAT_CODDOC).toString();
           String fech = tblDat.getValueAt(intFila, INT_TBL_DAT_FECSOL).toString();
             //Check en estado pendiente
            if ((Boolean) tblDat.getValueAt(intFila, INT_TBL_DAT_CHKAUTPEN)) {
                strEstDoc = "P";
                
                intCodEmp = Integer.valueOf(emp);
                intCodLoc = Integer.valueOf(loc);
                intCodTipDoc = Integer.valueOf(tipdoc);
                intCodDoc = Integer.valueOf(codoc);
                strFecha = fech;
                
                
                
                
                //Hora desde solicitud de horas ext/sup
                Object objHoAutDes = tblDat.getValueAt(intFila, INT_TBL_DAT_HORSOLDES);
                String strHoAutDes = "";
                Object objHoAutHas = tblDat.getValueAt(intFila, INT_TBL_DAT_HORSOLHAS);
                 String strHoAutHas = "";
                if (objHoAutDes != null && objHoAutHas !=null) {
                            strHoAutDes = objUti.codificar(objHoAutDes.toString());
                            strHoAutHas = objUti.codificar(objHoAutHas.toString());
                            strHoAutGLBDES = strHoAutDes;
                            strHoAutGLBHAS = strHoAutHas;
                            strAut = "'A'";
                            strParTot = "A";
                           
                            int usu = objParSis.getCodigoUsuario();
                            
//                       if (usu == 24) {
//                           intCoReg = 2;
//                       }  else if (usu == 246)  {
//                          intCoReg = 1;
//                       }
                    strSql = "update tbm_autSolHorSupExt set st_aut=" + strAut + " ,ho_autdes=" + strHoAutDes + " ,ho_authas=" + strHoAutHas + " ,tx_obsaut= '" + strObs + "', "
                                    + "fe_aut=CURRENT_TIMESTAMP, co_usraut =" + objParSis.getCodigoUsuario() + " , tx_comaut = '" + objParSis.getDireccionIP() + "' "
                                    + "where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc + " and co_reg= "+Co_reg(objParSis.getCodigoUsuario(),intFila);
                                    stmLoc.executeUpdate(strSql);
                          
                    strSql = "update tbm_cabSolHorSupExt set ho_autdes=" + strHoAutDes + ",ho_authas=" + strHoAutHas + " where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                                    + " and co_doc=" + intCodDoc;
                            stmLoc.executeUpdate(strSql);
                            
                    strSql = "select * from tbm_autSolHorSupExt where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc
                        + " and co_doc=" + intCodDoc + " and st_aut like 'A'";
                    resSet = stmLoc.executeQuery(strSql);
                    
                    strSql = "update tbm_cabSolHorSupExt set st_aut='T',ho_autdes=" + strHoAutGLBDES + ", ho_authas=" + strHoAutGLBHAS + ", fe_aut=current_timestamp";
                        strSql += " where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc + " and co_tipdoc=" + intCodTipDoc;
                        strSql += " and co_doc=" + intCodDoc;
                        strParTot = "T";
                        stmLoc.executeUpdate(strSql);
                        
                         strSql = "select co_tra from tbm_detsolhorsupext where co_emp=" + intCodEmp + " and co_loc=" + intCodLoc
                                + " and co_tipdoc=" + intCodTipDoc + " and co_doc=" + intCodDoc;//+" and co_reg="+tblDat.getValueAt(intFil, INT_TBL_COREG).toString();
                        resSet = stmLoc.executeQuery(strSql);
                        while (resSet.next()) {
                            String strCo_Tra = resSet.getString("co_tra");

                            strSql = "select * from tbm_cabconasitra where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                            resSetCab = stmLocCab.executeQuery(strSql);

                            if (resSetCab.next()) {
                                strSql = "update tbm_cabconasitra set st_autHorSupExt = 'S', ho_supExtAut = " + strHoAutGLBHAS + " "
                                        + "where fe_dia = '" + strFecha + "' and co_tra = " + strCo_Tra;
                                stmLocCab.executeUpdate(strSql);
                            } else {
                                strSql = "insert into tbm_cabconasitra (co_tra,fe_dia,st_autHorSupExt,ho_supExtAut) values(" + strCo_Tra + ", '" + strFecha + "', "
                                        + "'S', " + strHoAutGLBHAS + " )";
                                System.out.println(strSql);
                                stmLocCab.executeUpdate(strSql);
                            }
               
                        }
                        
                       }
       
            }
            //Check en estado parcial
            if ((Boolean) tblDat.getValueAt(intFila, INT_TBL_DAT_CHKAUTPAR)) {
                strEstDoc = "A";
                
                intCodEmp = Integer.valueOf(emp);
                intCodLoc = Integer.valueOf(loc);
                intCodTipDoc = Integer.valueOf(tipdoc);
                intCodDoc = Integer.valueOf(codoc);
                
                mostrarMsgInf("Esta Solicitud "+intCodDoc+" No puede ser aturizada por esta via. Se encuentra en estado Parcial");
                
                
            }
            //Check en estado Total
            if ((Boolean) tblDat.getValueAt(intFila, INT_TBL_DAT_CHKAUTTOT)) {
                strEstDoc = "T";
                
                
                
            }

        }
            mostrarMsgInf("AUTORIZADO MASIVO COMPLETADO");
           stmLoc.close();
           stmLoc = null;
           resSet.close();
           resSet = null;
           stmLocCab.close();
           stmLocCab = null;
           con.commit();
           con.close();
           con = null;
           
       }//Aqui caso que si sea null
           
        }catch(Exception e){
            System.out.println("Error" +e);
        }
        
        
    }//GEN-LAST:event_btnAutActionPerformed

    private void habilitarChkBox(boolean blnOptFil, boolean blnOptTod) {
        optFil.setSelected(blnOptFil);
        optTod.setSelected(blnOptTod);
    }

    /**
     * Cerrar la aplicaci�n.
     */
    private void exitForm() {
        dispose();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrFil;
    private javax.swing.JButton btnAut;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butDep;
    private javax.swing.JButton butMot;
    private javax.swing.JCheckBox chkMosDocParAut;
    private javax.swing.JCheckBox chkMosDocPenAut;
    private javax.swing.JCheckBox chkMosDocTotAut;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblLoc;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panFecCor;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panRpt;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    public javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodDep;
    private javax.swing.JTextField txtCodMot;
    private javax.swing.JTextField txtDesLarDep;
    private javax.swing.JTextField txtDesLarMot;
    // End of variables declaration//GEN-END:variables

    private void setLocationRelativeTo(Object object) 
    {
      //   throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class ZafThreadGUI extends Thread 
    {
        public void run() {
            if (!cargarReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount() > 0) {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI = null;
        }
    }

    public void BuscarMotHorSupExt(String campo, String strBusqueda, int tipo) 
    {
        objVenConMotHorSupExt.setTitle("Listado de Motivos de Horas Suplementarias/Extraordinarias");
        if (objVenConMotHorSupExt.buscar(campo, strBusqueda)) {
            txtCodMot.setText(objVenConMotHorSupExt.getValueAt(1));
            txtDesLarMot.setText(objVenConMotHorSupExt.getValueAt(2));
        } else {
            objVenConMotHorSupExt.setCampoBusqueda(tipo);
            objVenConMotHorSupExt.cargarDatos();
            objVenConMotHorSupExt.show();
            if (objVenConMotHorSupExt.getSelectedButton() == objVenConMotHorSupExt.INT_BUT_ACE) {
                txtCodMot.setText(objVenConMotHorSupExt.getValueAt(1));
                txtDesLarMot.setText(objVenConMotHorSupExt.getValueAt(2));
            } else {
                txtCodMot.setText(strCodMot);
                txtDesLarMot.setText(strDesLarMot);
            }
        }
    }

    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Departamentos".
     */
    private boolean configurarVenConDep() 
    {
        boolean blnRes = true;
        try 
        {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_dep");
            arlCam.add("a1.tx_desCor");
            arlCam.add("a1.tx_desLar");
            //  arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción corta");
            arlAli.add("Descripción larga");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("110");
            arlAncCol.add("110");
            //  arlAncCol.add("40");

            String strSQL = "";
            if (objParSis.getCodigoUsuario() == 1) {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep";
            } else {
                strSQL = "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where co_dep in(select co_dep from tbr_depprgusr where co_usr=" + objParSis.getCodigoUsuario() + " "
                        + //"and co_mnu="+objParSis.getCodigoMenu()+" and st_reg like 'A')";
                        "and co_mnu=" + objParSis.getCodigoMenu() + ")";
            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoDep = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado Departamentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoDep.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoDep.setConfiguracionColumna(4, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean configurarVenConMotHorSupExt() 
    {
        boolean blnRes = true;
        try 
        {
            ArrayList arlCam = new ArrayList();
            arlCam.add("a.co_mot");
            arlCam.add("a.tx_deslar");
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Descripción larga");
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("70");
            arlAncCol.add("470");
            //Armar la sentencia SQL.
            String strSQL = "";
            strSQL = "select a.co_mot, a.tx_deslar  from tbm_motHorSupExt as a"
                    + " where a.st_reg not in ('I','E') order by a.tx_deslar";
            objVenConMotHorSupExt = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objParSis.getNombreMenu(), strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() 
    {
        boolean blnRes = true;
        try 
        {

            strAux = objParSis.getNombreMenu() + " " + strVersion;
            this.setTitle(strAux);
            lblTit.setText(strAux);
            lblTit.setForeground(Color.BLACK);

            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(15);    //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_COEMP, "Cód.Emp.");
            vecCab.add(INT_TBL_DAT_EMP, "Empresa");
            vecCab.add(INT_TBL_DAT_CODLOC, "Cód.Loc.");
            vecCab.add(INT_TBL_DAT_CODTIPDOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESCORTIPDOC, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DESLARTIPDOC, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_CODDOC, "Cód.Doc.");
            vecCab.add(INT_TBL_DAT_NUMDOC, "Núm.Doc.");
            vecCab.add(INT_TBL_DAT_FECDOC, "Fec.Doc.");
            vecCab.add(INT_TBL_DAT_CODEP, "Cód.Dep.");
            vecCab.add(INT_TBL_DAT_DEP, "Departamento");

            vecCab.add(INT_TBL_DAT_MOTSOL, "Motivo");
            vecCab.add(INT_TBL_DAT_FECSOL, "Fec.Sol.");
            vecCab.add(INT_TBL_DAT_HORSOLDES, "Hor.Sol. Des.");
            vecCab.add(INT_TBL_DAT_HORSOLHAS, "Hor.Sol. Has.");
            vecCab.add(INT_TBL_DAT_BUTANESOLHSE, "");

            vecCab.add(INT_TBL_DAT_CHKAUTPEN, "Pendiente");
            vecCab.add(INT_TBL_DAT_CHKAUTPAR, "Parcial");
            vecCab.add(INT_TBL_DAT_CHKAUTTOT, "Total");
            vecCab.add(INT_TBL_DAT_HORAUT, "Hor.Aut.");
            vecCab.add(INT_TBL_DAT_BUTANE2, "");
            vecCab.add(INT_TBL_DAT_FEC_HOR_AUT, "Fec. Aut.");

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
            tcmAux.getColumn(INT_TBL_DAT_COEMP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_EMP).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_CODLOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_CODTIPDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DESCORTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DESLARTIPDOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CODDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_FECDOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_CODEP).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DEP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DAT_MOTSOL).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_DAT_FECSOL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_DAT_HORSOLDES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_HORSOLHAS).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTPEN).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTPAR).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_CHKAUTTOT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_HORAUT).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_FEC_HOR_AUT).setPreferredWidth(150);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COEMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODLOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESCORTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_DESLARTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODEP, tblDat);

            tblDat.getTableHeader().setReorderingAllowed(false);
            objTblBus = new ZafTblBus(tblDat);

            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            objTblOrd = new ZafTblOrd(tblDat);

            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            objSelFec.setCheckBoxChecked(false);
            panFecCor.add(objSelFec);
            objSelFec.setBounds(4, 20, 472, 72);

            //*****************************************************************************
            Librerias.ZafDate.ZafDatePicker txtFecDoc;
            txtFecDoc = new Librerias.ZafDate.ZafDatePicker(((javax.swing.JFrame) this.getParent()), "d/m/y");
            txtFecDoc.setPreferredSize(new java.awt.Dimension(70, 20));
            txtFecDoc.setHoy();
            java.util.Calendar objFec = java.util.Calendar.getInstance();
            Librerias.ZafDate.ZafDatePicker dtePckPag = new Librerias.ZafDate.ZafDatePicker(new javax.swing.JFrame(), "d/m/y");
            int fecDoc[] = txtFecDoc.getFecha(objSelFec.getFechaDesde());
            if (fecDoc != null) {
                objFec.set(java.util.Calendar.DAY_OF_MONTH, fecDoc[0]);
                objFec.set(java.util.Calendar.MONTH, fecDoc[1] - 1);
                objFec.set(java.util.Calendar.YEAR, fecDoc[2]);
            }
            java.util.Calendar objFecPagActual = objFec.getInstance();
            objFecPagActual.setTime(objFec.getTime());
            objFecPagActual.add(java.util.Calendar.DATE, -30);

            dtePckPag.setAnio(objFecPagActual.get(java.util.Calendar.YEAR));
            dtePckPag.setMes(objFecPagActual.get(java.util.Calendar.MONTH) + 1);
            dtePckPag.setDia(objFecPagActual.get(java.util.Calendar.DAY_OF_MONTH));
            String fecha = objUti.formatearFecha(dtePckPag.getText(), "dd/MM/yyyy", "yyyy/MM/dd");
            java.util.Date fe1 = objUti.parseDate(fecha, "yyyy/MM/dd");

            objSelFec.setFechaDesde(objUti.formatearFecha(fe1, "dd/MM/yyyy"));

            txtCodMot.setVisible(true);
            txtCodMot.setEditable(true);
            txtCodMot.setEnabled(true);

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
            vecAux.add("" + INT_TBL_DAT_BUTANESOLHSE);
            vecAux.add("" + INT_TBL_DAT_BUTANE2);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;

            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

            if ((objPerUsr.isOpcionEnabled(3065))) {
                butCon.setEnabled(true);
            }
            if ((objPerUsr.isOpcionEnabled(3066))) {
                butCer.setEnabled(true);
            }

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODTIPDOC, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CODDOC, tblDat);

            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut zafTblDocCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setCellRenderer(zafTblDocCelRenBut);

            //Configurar JTable: Editor de celdas.
            objTblCelEdiButGen = new Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUTANESOLHSE).setCellEditor(objTblCelEdiButGen);
            objTblCelEdiButGen.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel, intColSel;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {
                            case INT_TBL_DAT_BUTANESOLHSE:

                                break;
                        }
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intColSel = tblDat.getSelectedColumn();
                    if (intFilSel != -1) {
                        switch (intColSel) {

                            case INT_TBL_DAT_BUTANESOLHSE:

                                llamarPantSolHSE(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COEMP).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODLOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODTIPDOC).toString(), objTblMod.getValueAt(intFilSel, INT_TBL_DAT_CODDOC).toString()
                                );

                                break;

                        }
                    }
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPEN).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPEN)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPAR).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTPAR).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPAR)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTPAR)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenChk = new ZafTblCelRenChk();
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTTOT).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk = null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tblDat.getColumnModel().getColumn(INT_TBL_DAT_CHKAUTTOT).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                String strLin = "";

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    strLin = objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN) == null ? "" : objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_LIN).toString();
                    if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTTOT)) {
                        if (strLin.equals("M")) {
                            objTblCelEdiChk.setCancelarEdicion(false);
                        } else {
                            if (objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHKAUTTOT)) {
                                objTblCelEdiChk.setCancelarEdicion(true);
                            } else {
                                objTblCelEdiChk.setCancelarEdicion(false);
                            }
                        }
                    }
                }
            });

            objTblCelRenBut = new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut = null;

            objRecHum30_02 = new ZafRecHum30_02(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objTblCelEdiButDlg = new ZafTblCelEdiButDlg(objRecHum30_02);
            tcmAux.getColumn(INT_TBL_DAT_BUTANE2).setCellEditor(objTblCelEdiButDlg);
            objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel;
                int intCol;

                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel = tblDat.getSelectedRow();
                    intCol = tblDat.getSelectedRow();
                    if (intFilSel != -1) {

                        intPosAct = intFilSel;
                        String strEstDoc = "";
                        if ((Boolean) tblDat.getValueAt(intCol, INT_TBL_DAT_CHKAUTPEN)) {
                            strEstDoc = "P";
                        }
                        if ((Boolean) tblDat.getValueAt(intCol, INT_TBL_DAT_CHKAUTPAR)) {
                            strEstDoc = "A";
                        }
                        if ((Boolean) tblDat.getValueAt(intCol, INT_TBL_DAT_CHKAUTTOT)) {
                            strEstDoc = "T";
                        }

                        objRecHum30_02.setParDlg(objParSis, ZafRecHum30.this, Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_COEMP).toString()), Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_CODLOC).toString()), Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_CODTIPDOC).toString()), Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_CODDOC).toString()), Integer.valueOf(tblDat.getValueAt(intCol, INT_TBL_DAT_NUMDOC).toString()), tblDat.getValueAt(intCol, INT_TBL_DAT_FECSOL).toString(), strEstDoc
                        );
                        objRecHum30_02.cargarReg();
                    }
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            //Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private void llamarPantSolHSE(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc) {
        RecursosHumanos.ZafRecHum27.ZafRecHum27 obj1 = new RecursosHumanos.ZafRecHum27.ZafRecHum27(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni 
    {
        public ButDoc(javax.swing.JTable tbl, int intIdx) {
            super(tbl, intIdx, "Documento.");
        }

        public void butCLick() 
        {
            int intCol = tblDat.getSelectedRow();
            llamarVentana(String.valueOf(objParSis.getCodigoEmpresa()), tblDat.getValueAt(intCol, INT_TBL_DAT_CODLOC).toString(), tblDat.getValueAt(intCol, INT_TBL_DAT_CODTIPDOC).toString(), tblDat.getValueAt(intCol, INT_TBL_DAT_CODDOC).toString(), tblDat.getValueAt(intCol, INT_TBL_DAT_NUMDOC).toString(), tblDat.getValueAt(intCol, INT_TBL_DAT_FECSOL).toString()
            );

        }
    }

    private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strNumDoc, String strFecha) 
    {
        RecursosHumanos.ZafRecHum30.ZafRecHum30_01 obj1 = new RecursosHumanos.ZafRecHum30.ZafRecHum30_01(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strNumDoc, strFecha);
        this.getParent().add(obj1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj1.show();
    }

    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar
     * eventos de del mouse (mover el mouse; arrastrar y soltar). Se la usa en
     * el sistema para mostrar el ToolTipText adecuado en la cabecera de las
     * columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter 
    {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_COEMP:
                    strMsg = "Código de la empresa";
                    break;
                case INT_TBL_DAT_EMP:
                    strMsg = "Empresa";
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
                case INT_TBL_DAT_DESLARTIPDOC:
                    strMsg = "Descripción larga del Tipo de Documento";
                    break;
                case INT_TBL_DAT_CODDOC:
                    strMsg = "Código del documento";
                    break;
                case INT_TBL_DAT_NUMDOC:
                    strMsg = "Número de Solicitud";
                    break;
                case INT_TBL_DAT_FECDOC:
                    strMsg = "Fecha del documento";
                    break;
                case INT_TBL_DAT_CODEP:
                    strMsg = "Código del departamento";
                    break;
                case INT_TBL_DAT_DEP:
                    strMsg = "Departamento";
                    break;
                case INT_TBL_DAT_MOTSOL:
                    strMsg = "Motivo de la solicitud";
                    break;
                case INT_TBL_DAT_FECSOL:
                    strMsg = "Fecha solicitada";
                    break;
                case INT_TBL_DAT_HORSOLDES:
                    strMsg = "Hora solicitada desde";
                    break;
                case INT_TBL_DAT_HORSOLHAS:
                    strMsg = "Hora solicitada hasta";
                    break;
                case INT_TBL_DAT_BUTANESOLHSE:
                    strMsg = "Muestra la solicitud";
                    break;
                case INT_TBL_DAT_CHKAUTPEN:
                    strMsg = "Autorización pendiente";
                    break;
                case INT_TBL_DAT_CHKAUTPAR:
                    strMsg = "Autorización parcial";
                    break;
                case INT_TBL_DAT_CHKAUTTOT:
                    strMsg = "Autorización total";
                    break;
                case INT_TBL_DAT_HORAUT:
                    strMsg = "Horas autorizadas";
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
    private void mostrarMsgInf(String strMsg) 
    {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean cargarReg() 
    {
        boolean blnRes = true;
        try 
        {
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

    private boolean cargarDetReg() 
    {
        boolean blnRes = true;
        int i;
        strAux = "";
        String strEstConf = "";
        int intNumReg = 0;

        try 
        {
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            if (con != null) 
            {
                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //B�squeda por rangos
                        strAux += " AND (a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        strAux += " AND (a1.fe_doc<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        strAux += " AND (a1.fe_doc>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                //para no presentar documentos entre compañias
                /*if(chkMosDocTotAut.isSelected()){
                 strAux+=" AND NOT (a1.co_emp=1 AND a1.co_cli IN(3515,3516,602,603,2600,1039))";
                 strAux+=" AND NOT (a1.co_emp=2 AND a1.co_cli IN(2853, 2854,446,447,2105,789,790))";
                 strAux+=" AND NOT (a1.co_emp=3 AND a1.co_cli IN(2857,2858,452,453,2107,832))";
                 strAux+=" AND NOT (a1.co_emp=4 AND a1.co_cli IN(3116,3117,497,498,2294,886,887))";
                 }*/
                
                if (optFil.isSelected()) 
                {
                    if (chkMosDocPenAut.isSelected()) {
                        if (strEstConf.equals("")) {
                            strEstConf += "'P'";
                        } else {
                            strEstConf += ",'P'";
                        }
                    }
                    if (chkMosDocParAut.isSelected()) {
                        if (strEstConf.equals("")) {
                            strEstConf += "'A'";
                        } else {
                            strEstConf += ",'A'";
                        }
                    }
                    if (chkMosDocTotAut.isSelected()) {
                        if (strEstConf.equals("")) {
                            strEstConf += "'T'";
                        } else {
                            strEstConf += ",'T'";
                        }
                    }

                    if (!strEstConf.equals("")) {
                        strAux += " AND st_aut IN (" + strEstConf + ") ";
                    }

                    if (!txtCodDep.getText().toString().equals("")) {
                        strAux += " AND a1.co_dep=" + txtCodDep.getText() + "";
                    }

                    if (!txtCodMot.getText().toString().equals("")) {
                        strAux += " AND a1.co_mot=" + txtCodMot.getText() + "";
                    }
                }
                
                 if (optTod.isSelected()) 
                 {
                    strEstConf = " 'P', 'A', 'T' ";   
                     
                    if (!strEstConf.equals("")) {
                        strAux += " AND st_aut IN (" + strEstConf + ") ";
                    }
                 
                 }

                stm = con.createStatement();

                strSQL = "";

                if (objParSis.getCodigoUsuario() == 1) 
                {
                    strSQL += "SELECT a1.co_emp,d.tx_nom as empresa,a1.co_loc,a1.co_tipdoc,a1.co_doc, a1.ne_numdoc, a1.co_dep,c.tx_deslar as departamento,  " + "\n";
                    strSQL += "a1.fe_doc, a1.fe_aut,b.tx_deslar ,a1.fe_sol, a1.ho_soldes, a1.ho_solhas, a1.st_aut,a1.ho_authas,a2.tx_descor,a2.tx_deslar as deslartipdoc " + "\n";
                    strSQL += "from tbm_cabsolhorsupext a1 " + "\n";
                    strSQL += "INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc " + "\n";
                    strSQL += "inner join tbm_motHorSupExt b on (a1.co_mot = b.co_mot) " + "\n";
                    strSQL += "inner join tbm_dep c on (a1.co_dep=c.co_dep) " + "\n";
                    strSQL += "inner join tbm_emp d on (d.co_emp=a1.co_emp) " + "\n";
                    strSQL += " where a1.st_reg like 'A' "+strAux +  "\n";
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                        strSQL += "and a1.co_emp = " + objParSis.getCodigoEmpresa() + "\n";
                        strSQL += "and a1.co_loc = " + objParSis.getCodigoLocal() + "\n";
                    }
                    strSQL += "GROUP BY a1.co_emp,empresa,a1.co_dep,c.tx_deslar,a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.fe_doc,a1.fe_aut, " + "\n";
                    strSQL += "b.tx_deslar ,a1.fe_sol, a1.ho_solhas, a1.st_aut,a1.ho_soldes,a1.ho_authas,a2.tx_descor,a2.tx_deslar " + "\n";
                    strSQL += "ORDER BY a1.fe_doc , a1.co_emp , ne_numdoc";
                } 
                else 
                {
                    strSQL += "SELECT a1.co_emp,e.tx_nom as empresa,a1.co_loc,a1.co_tipdoc,a1.co_doc, a1.ne_numdoc, a1.co_dep,d.tx_deslar as departamento,  " + "\n";
                    strSQL += "a1.fe_doc, a1.fe_aut,b.tx_deslar ,a1.fe_sol, a1.ho_soldes, a1.ho_solhas, a1.st_aut,a1.ho_authas,a2.tx_descor,a2.tx_deslar as deslartipdoc " + "\n";
                    strSQL += "from tbm_cabsolhorsupext a1 " + "\n";
                    strSQL += "INNER JOIN tbm_cabTipDoc AS a2 ON a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_loc AND a2.co_tipDoc=a1.co_tipDoc " + "\n";
                    strSQL += "inner join tbm_motHorSupExt b on (a1.co_mot = b.co_mot) " + "\n";
                    strSQL += "inner join tbr_depprgusr c on(a1.co_dep=c.co_dep and c.co_dep in (select co_dep from tbr_depprgusr where co_usr = " + objParSis.getCodigoUsuario() + "\n";
                    strSQL += "and co_mnu=" + objParSis.getCodigoMenu() + " )) " + "\n";
                    strSQL += "inner join tbm_dep d on (a1.co_dep=d.co_dep) " + "\n";
                    strSQL += "inner join tbm_emp e on (e.co_emp=a1.co_emp) " + "\n";
                    strSQL += " where a1.st_reg like 'A' "+strAux + "\n";
                    if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                        strSQL += "and a1.co_emp = " + objParSis.getCodigoEmpresa() + "\n";
                        strSQL += "and a1.co_loc = " + objParSis.getCodigoLocal() + "\n";
                    }
                    strSQL += "group by a1.co_emp,empresa,a1.co_dep,d.tx_deslar,a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numdoc, a1.fe_doc, a1.fe_aut, " + "\n";
                    strSQL += "b.tx_deslar ,a1.fe_sol, a1.ho_soldes , a1.ho_solhas, a1.st_aut,a1.ho_authas,a2.tx_descor,a2.tx_deslar " + "\n";
                    strSQL += "ORDER BY a1.fe_doc , a1.co_emp , ne_numdoc";
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
                        vecReg.add(INT_TBL_DAT_COEMP, rst.getInt("co_emp"));
                        vecReg.add(INT_TBL_DAT_EMP, rst.getString("empresa"));
                        vecReg.add(INT_TBL_DAT_CODLOC, rst.getInt("co_loc"));
                        vecReg.add(INT_TBL_DAT_CODTIPDOC, rst.getInt("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_DESCORTIPDOC, rst.getString("tx_descor"));
                        vecReg.add(INT_TBL_DAT_DESLARTIPDOC, rst.getString("deslartipdoc"));
                        vecReg.add(INT_TBL_DAT_CODDOC, rst.getInt("co_doc"));
                        vecReg.add(INT_TBL_DAT_NUMDOC, rst.getInt("ne_numdoc"));
                        vecReg.add(INT_TBL_DAT_FECDOC, rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_CODEP, rst.getInt("co_dep"));
                        vecReg.add(INT_TBL_DAT_DEP, rst.getString("departamento"));

                        vecReg.add(INT_TBL_DAT_MOTSOL, rst.getString("tx_deslar"));
                        vecReg.add(INT_TBL_DAT_FECSOL, rst.getString("fe_Sol"));
                        vecReg.add(INT_TBL_DAT_HORSOLDES, rst.getString("ho_soldes"));
                        vecReg.add(INT_TBL_DAT_HORSOLHAS, rst.getString("ho_solhas"));
                        vecReg.add(INT_TBL_DAT_BUTANESOLHSE, "...");

                        String strStAut = rst.getString("st_aut");

                        if (strStAut.compareTo("A") == 0) {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, true);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, false);
                        } else if (strStAut.compareTo("T") == 0) {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, true);
                        } else {
                            vecReg.add(INT_TBL_DAT_CHKAUTPEN, true);
                            vecReg.add(INT_TBL_DAT_CHKAUTPAR, false);
                            vecReg.add(INT_TBL_DAT_CHKAUTTOT, false);
                        }

                        vecReg.add(INT_TBL_DAT_HORAUT, rst.getString("ho_authas"));
                        vecReg.add(INT_TBL_DAT_BUTANE2, "...");
                        vecReg.add(INT_TBL_DAT_FEC_HOR_AUT, rst.getString("fe_aut"));
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

    public void Configura_ventana_consulta() 
    {
        configurarVenConDep();
        configurarVenConMotHorSupExt();
    }
   //Metodo para saber en que jerarquia grabar
    public int Co_reg (int co_usr, int fila){
        
       String strSql ="";
       int co_reg = 0;
       java.sql.ResultSet resSet;
       java.sql.Statement stmLoc;
         try 
        {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) {
                stmLoc = con.createStatement();             
                 strSql = "select co_reg from tbm_usrauthorsupextdep where co_usr = "+ co_usr + " and co_dep =" + Integer.valueOf(tblDat.getValueAt(fila, INT_TBL_DAT_CODEP).toString()) ;
                resSet = stmLoc.executeQuery(strSql);             
                while (resSet.next()) {                      
                    co_reg = resSet.getInt("co_reg");            
                    }    
            }
            stmLoc = null;
            stmLoc.close();
            resSet = null;
            resSet.close();
            con.close();
            con = null;
        } catch (Exception e) {
           // blnRes = false;
        }
        return co_reg;
    } 
}
