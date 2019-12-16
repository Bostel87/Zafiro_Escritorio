/*
 * ZafCon15.java
 * Movimientos de Mayor de Cuentas
 * Created on 24 de enero de 2006, 11:06
 */
package Contabilidad.ZafCon15;

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import Librerias.ZafVenCon.ZafVenCon;
import java.util.ArrayList;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafPerUsr.ZafPerUsr;
import Librerias.ZafRptSis.ZafRptSis;
import java.awt.Color;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author ilino
 */
public class ZafCon15 extends javax.swing.JInternalFrame 
{
    Librerias.ZafUtil.ZafUtil objUti;
    Librerias.ZafParSis.ZafParSis objParSis;
    Vector vecDat, vecCab, vecReg;
    ZafTblMod objTblMod;
    ZafColNumerada objColNum;
    ZafTblPopMnu objTblPopMnu;
    private ZafTblBus objTblBus;
    private ZafTblCelRenLbl objTblCelRenLblHid, objTblCelRenLbl, objTblCelRenLblCol; //Render: Presentar JLabel en JTable.
    private ZafThreadGUIRpt objThrGUIRpt;
    public String strSQLRep;
    private java.util.Date datFecAux;                                                //Auxiliar: Para almacenar fechas.
    private ZafRptSis objRptSis;
    private String strSQL, strAux;
    private String strPeriodoDesde = "";
    private String strPeriodoHasta = "";
    private final String VERSION = " v7.15 ";
    private ZafMouMotAda objMouMotAda;                                                //ToolTipText en TableHeader.
    private ZafVenCon vcoPrv;                                                         //Ventana de consulta "Proveedor".
    private String strFecIniTbl = "";
    private String strFecFinTbl = "";
    final int INT_TBL_LIN = 0;
    final int INT_TBL_NOM_LOC = 1;
    final int INT_TBL_FEC_DIA = 2;
    final int INT_TBL_COD_DIA = 3;
    final int INT_TBL_DES = 4;                                                        //Para Descripción
    final int INT_TBL_NUM_DOC = 5;
    final int INT_TBL_NUM_CHQ = 6;
    final int INT_TBL_NOM_CLI = 7;                                                    //Para Codigo de Cliente o Glosa.
    final int INT_TBL_MON_DEB = 8;
    final int INT_TBL_MON_HAB = 9;
    final int INT_TBL_SAL = 10;
    final int INT_TBL_PER_GEN_DOC = 11;
    final int INT_TBL_EST_REG = 12;
    private String strCodPrv, strDesLarPrv;                                           //Contenido del campo al obtener el foco.
    private String strIdePrv, strDirPrv;                                              //Campos: RUC y Direccián del Beneficiario.
    private boolean blnCon;
    private ZafThreadGUI objThrGUI;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private ZafTblTot objTblTot;                                                      //JTable de totales.
    private double dblSalActExc;
    private double dblSalAntExc;
    private double dblSalAcuExc;
    private java.io.File fil;
    private ZafSelFec objSelFec;
    private String strDesCorCta, strDesLarCta;
    private ZafVenCon vcoCta;
    private ZafTblOrd objTblOrd;                                                      //JTable de ordenamiento.
    private ZafPerUsr objPerUsr;

    /**
     * Creates new form ZafCon15
     */
    public ZafCon15(Librerias.ZafParSis.ZafParSis obj) 
    {
        initComponents();
        try
        {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new Librerias.ZafUtil.ZafUtil();
            this.setTitle(objParSis.getNombreMenu() + VERSION);
            txtCodCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorCta.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarCta.setBackground(objParSis.getColorCamposObligatorios());
            EnabledBoxCtas(true);
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos());
            objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objParSis);
            objPerUsr = new ZafPerUsr(objParSis);

            if (!configurarFrm())
            {
                exitForm();
            }
        } 
        catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
    }

    private void MsgInf(String strMensaje)
    {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Zafiro: Contabilidad";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void MsgError(String strMensaje) {
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Zafiro: Contabilidad";
        obj.showMessageDialog(this, strMensaje, strTit, javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Zafiro";
        return oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panGrl = new javax.swing.JPanel();
        panCen = new javax.swing.JPanel();
        panFec = new javax.swing.JPanel();
        panFil = new javax.swing.JPanel();
        panCta = new javax.swing.JPanel();
        txtDesCorCta = new javax.swing.JTextField();
        txtDesLarCta = new javax.swing.JTextField();
        butCta = new javax.swing.JButton();
        txtCodCta = new javax.swing.JTextField();
        chkNotDebPrv = new javax.swing.JCheckBox();
        lblCta = new javax.swing.JLabel();
        txtNivCta = new javax.swing.JTextField();
        cboEstDoc = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        panPrv = new javax.swing.JPanel();
        pnRep = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        spnTblRep = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        spnTotal = new javax.swing.JScrollPane();
        tblTotal = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblSal = new javax.swing.JLabel();
        lblSalAnt = new javax.swing.JLabel();
        lblSalAcu = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        txtSal = new javax.swing.JTextField();
        txtSalAnt = new javax.swing.JTextField();
        txtSalAcu = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butRepTbl = new javax.swing.JButton();
        butRepJas = new javax.swing.JButton();
        butExpExc = new javax.swing.JButton();
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

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título de la ventana");
        lblTit.setPreferredSize(new java.awt.Dimension(138, 20));
        lblTit.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        panGrl.setLayout(new java.awt.BorderLayout());

        panCen.setName(""); // NOI18N
        panCen.setPreferredSize(new java.awt.Dimension(10, 300));
        panCen.setLayout(new java.awt.BorderLayout());

        panFec.setPreferredSize(new java.awt.Dimension(0, 100));
        panFec.setLayout(new java.awt.BorderLayout());
        panCen.add(panFec, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        panCta.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        panCta.setLayout(null);

        txtDesCorCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorCtaActionPerformed(evt);
            }
        });
        txtDesCorCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorCtaFocusLost(evt);
            }
        });
        panCta.add(txtDesCorCta);
        txtDesCorCta.setBounds(80, 10, 130, 20);

        txtDesLarCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarCtaActionPerformed(evt);
            }
        });
        txtDesLarCta.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarCtaFocusLost(evt);
            }
        });
        panCta.add(txtDesLarCta);
        txtDesLarCta.setBounds(210, 10, 350, 20);

        butCta.setText("...");
        butCta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCtaActionPerformed(evt);
            }
        });
        panCta.add(butCta);
        butCta.setBounds(560, 10, 20, 20);
        panCta.add(txtCodCta);
        txtCodCta.setBounds(60, 10, 20, 20);

        chkNotDebPrv.setText("No presentar Nota de débito de proveedores");
        panCta.add(chkNotDebPrv);
        chkNotDebPrv.setBounds(10, 114, 350, 14);

        lblCta.setText("Cuenta:");
        panCta.add(lblCta);
        lblCta.setBounds(10, 10, 60, 20);
        panCta.add(txtNivCta);
        txtNivCta.setBounds(580, 10, 40, 20);

        cboEstDoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Todos", "A: Activo", "I: Anulado" }));
        panCta.add(cboEstDoc);
        cboEstDoc.setBounds(140, 54, 230, 20);

        jLabel1.setText("Estado del documento:");
        panCta.add(jLabel1);
        jLabel1.setBounds(10, 56, 130, 14);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panCta.add(lblPrv);
        lblPrv.setBounds(10, 30, 70, 20);

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
        panCta.add(txtCodPrv);
        txtCodPrv.setBounds(80, 30, 80, 20);

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
        panCta.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(160, 30, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panCta.add(butPrv);
        butPrv.setBounds(424, 30, 20, 20);

        panFil.add(panCta, java.awt.BorderLayout.CENTER);

        panPrv.setPreferredSize(new java.awt.Dimension(100, 80));
        panPrv.setLayout(null);
        panFil.add(panPrv, java.awt.BorderLayout.SOUTH);

        panCen.add(panFil, java.awt.BorderLayout.CENTER);

        panGrl.add(panCen, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panGrl);

        pnRep.setLayout(new java.awt.BorderLayout());

        jPanel8.setLayout(new java.awt.BorderLayout());

        spnTblRep.setPreferredSize(new java.awt.Dimension(452, 266));

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
        spnTblRep.setViewportView(tblDat);

        jPanel8.add(spnTblRep, java.awt.BorderLayout.CENTER);

        spnTotal.setPreferredSize(new java.awt.Dimension(320, 35));

        tblTotal.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTotal.setViewportView(tblTotal);

        jPanel8.add(spnTotal, java.awt.BorderLayout.SOUTH);

        pnRep.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(72, 64));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(460, 50));
        jPanel2.setLayout(new java.awt.GridLayout(3, 0));

        lblSal.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSal.setText("Saldo:  ");
        jPanel2.add(lblSal);

        lblSalAnt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSalAnt.setText("Saldo Anterior:  ");
        jPanel2.add(lblSalAnt);

        lblSalAcu.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSalAcu.setText("Saldo Acumulado:  ");
        jPanel2.add(lblSalAcu);

        jPanel1.add(jPanel2, java.awt.BorderLayout.WEST);

        jPanel3.setPreferredSize(new java.awt.Dimension(100, 50));
        jPanel3.setLayout(new java.awt.GridLayout(3, 0));

        txtSal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(txtSal);

        txtSalAnt.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSalAnt.setFocusCycleRoot(true);
        jPanel3.add(txtSalAnt);

        txtSalAcu.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jPanel3.add(txtSalAcu);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel4.setPreferredSize(new java.awt.Dimension(30, 10));
        jPanel1.add(jPanel4, java.awt.BorderLayout.EAST);

        jPanel5.setMinimumSize(new java.awt.Dimension(10, 6));
        jPanel5.setPreferredSize(new java.awt.Dimension(10, 6));
        jPanel1.add(jPanel5, java.awt.BorderLayout.NORTH);

        pnRep.add(jPanel1, java.awt.BorderLayout.SOUTH);

        tabFrm.addTab("Reporte", pnRep);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPie.setMinimumSize(new java.awt.Dimension(50, 33));
        panPie.setPreferredSize(new java.awt.Dimension(0, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 3));

        butRepTbl.setText("Consultar");
        butRepTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepTblActionPerformed(evt);
            }
        });
        panBot.add(butRepTbl);

        butRepJas.setText("Vista preliminar");
        butRepJas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butRepJasActionPerformed(evt);
            }
        });
        panBot.add(butRepJas);

        butExpExc.setText("Exportar");
        butExpExc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butExpExcActionPerformed(evt);
            }
        });
        panBot.add(butExpExc);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panPie.add(panBot, java.awt.BorderLayout.CENTER);

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

        panPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panPie, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm, java.awt.BorderLayout.CENTER);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try 
        {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "¿Está seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                if (con != null) {
                    con.close();
                    con = null;
                }
                dispose();
            }
        } 
        catch (java.sql.SQLException e) {
            dispose();
        }
}//GEN-LAST:event_exitForm

    private void butRepTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepTblActionPerformed
        //Realizar acción de acuerdo a la etiqueta del botán ("Consultar" o "Detener").
        txtSal.setText("");
        txtSalAnt.setText("");
        txtSalAcu.setText("");

        if (butRepTbl.getText().equals("Consultar")) 
        {
            if (isCamVal()) 
            {
                blnCon = true;
                if (objThrGUI == null)
                {
                    objThrGUI = new ZafThreadGUI();
                    objThrGUI.start();
                }
            }
        } 
        else 
        {
            blnCon = false;
        }
    }//GEN-LAST:event_butRepTblActionPerformed

 
    /**
     * Cerrar la aplicacián.
     */
    private void exitForm() 
    {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "¿Está seguro que desea cerrar el programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            if (fil.exists()) {
                fil.delete();
            }
            this.dispose();
        }
    }

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
         exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void butRepJasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butRepJasActionPerformed
        
        cargarRepote(1); //Rose
//        if (isCamVal()) {
//            if (!imprimir()) {
//                mostrarMsgInf("<HTML>No se encontró ningún movimiento para esta cuenta<BR>en el período especificado.</HTML>");
//            }
//        }
    }//GEN-LAST:event_butRepJasActionPerformed

    private void butCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCtaActionPerformed
        mostrarVenConCta(0);
    }//GEN-LAST:event_butCtaActionPerformed

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
    txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
    strCodPrv = txtCodPrv.getText();
    txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)) {
        if (txtCodPrv.getText().equals("")) {
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrv(1);
        }
    } else {
        txtCodPrv.setText(strCodPrv);
    }
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
    txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
    strDesLarPrv = txtDesLarPrv.getText();
    txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)) {
        if (txtDesLarPrv.getText().equals("")) {
            txtCodPrv.setText("");
            txtDesLarPrv.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrv(2);
        }
    } else {
        txtDesLarPrv.setText(strDesLarPrv);
    }
}//GEN-LAST:event_txtDesLarPrvFocusLost

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
    strCodPrv = txtCodPrv.getText();
    mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void butExpExcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butExpExcActionPerformed
    try 
    {
        String strNomEmp = objParSis.getNombreEmpresa();
        String strNumCta = txtDesCorCta.getText();
        String strNomCta = txtDesLarCta.getText();
        //String strFecDes=objUti.formatearFecha(txtFecIni.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
        //String strFecHas=objUti.formatearFecha(txtFecFin.getText(),"dd/MM/yyyy",objParSis.getFormatoFechaBaseDatos());
        String strFecDes = objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
        String strFecHas = objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos());
        double dblSalActImp = dblSalActExc;
        double dblSalAntImp = dblSalAntExc;
        double dblSalAcuImp = dblSalAcuExc;
        fil = new java.io.File("C:\\Zafiro\\Reportes\\Excel\\MMC.xls");
        //fil=new java.io.File("C:\\MMC.xls");
        String strNomHoj = "Hoja1";

        ZafExpExc objExc = new ZafExpExc(tblDat, fil, strNomHoj, strNomEmp, strNumCta, strNomCta, strFecDes, strFecHas, dblSalActImp, dblSalAntImp, dblSalAcuImp);

        if (objExc.export()) 
        {
            mostrarMsgInf("El archivo se cargó correctamente.");
            //Runtime.getRuntime().exec( "C:\\Zafiro\\Reportes_impresos\\Excel\\MMC.xls"); 
            //Process p = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes_impresos/Excel/MMC.xls");
            Process p = Runtime.getRuntime().exec("cmd /c start C:/Zafiro/Reportes/Excel/MMC.xls");
        } 
        else 
        {
            mostrarMsgInf("Falló la carga del archivo");
        }
    } catch (Exception e) {
        objUti.mostrarMsgErr_F1(this, e);
    }
}//GEN-LAST:event_butExpExcActionPerformed

private void txtDesCorCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorCtaActionPerformed
    txtDesCorCta.transferFocus();
    setPuntosCta();
}//GEN-LAST:event_txtDesCorCtaActionPerformed

private void txtDesCorCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusGained
    strDesCorCta = txtDesCorCta.getText();
    txtDesCorCta.selectAll();
}//GEN-LAST:event_txtDesCorCtaFocusGained

private void txtDesCorCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesCorCtaFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesCorCta.getText().equalsIgnoreCase(strDesCorCta)) {
        if (txtDesCorCta.getText().equals("")) {
            txtCodCta.setText("");
            txtDesLarCta.setText("");
            txtNivCta.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConCta(1);
        }
    } else {
        txtDesCorCta.setText(strDesCorCta);
    }

}//GEN-LAST:event_txtDesCorCtaFocusLost

private void txtDesLarCtaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarCtaActionPerformed
    txtDesLarCta.transferFocus();
}//GEN-LAST:event_txtDesLarCtaActionPerformed

private void txtDesLarCtaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusGained
    strDesLarCta = txtDesLarCta.getText();
    txtDesLarCta.selectAll();
}//GEN-LAST:event_txtDesLarCtaFocusGained

private void txtDesLarCtaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarCtaFocusLost
    //Validar el contenido de la celda sálo si ha cambiado.
    if (!txtDesLarCta.getText().equalsIgnoreCase(strDesLarCta)) {
        if (txtDesLarCta.getText().equals("")) {
            txtCodCta.setText("");
            txtDesCorCta.setText("");
            txtNivCta.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConCta(2);
        }
    } else {
        txtDesLarCta.setText(strDesLarCta);
    }
}//GEN-LAST:event_txtDesLarCtaFocusLost

    /**
     * Esta funcián permite utilizar la "Ventana de Consulta" para seleccionar
     * un registro de la base de datos. El tipo de básqueda determina si se debe
     * hacer una básqueda directa (No se muestra la ventana de consulta a menos
     * que no exista lo que se está buscando) o presentar la ventana de consulta
     * para que el usuario seleccione la opcián que desea utilizar.
     *
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema. <BR>false: En el caso
     * contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    } else {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv = vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv = vcoPrv.getValueAt(4);
                            objTblMod.removeAllRows();
                        } else {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //Básqueda directa por "Descripcián larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText())) {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv = vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv = vcoPrv.getValueAt(4);
                        objTblMod.removeAllRows();
                    } else {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton() == vcoPrv.INT_BUT_ACE) {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv = vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv = vcoPrv.getValueAt(4);
                            objTblMod.removeAllRows();
                        } else {
                            txtDesLarPrv.setText(strDesLarPrv);
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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCta;
    private javax.swing.JButton butExpExc;
    private javax.swing.JButton butPrv;
    private javax.swing.JButton butRepJas;
    private javax.swing.JButton butRepTbl;
    private javax.swing.JComboBox cboEstDoc;
    private javax.swing.JCheckBox chkNotDebPrv;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblCta;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblSal;
    private javax.swing.JLabel lblSalAcu;
    private javax.swing.JLabel lblSalAnt;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panCta;
    private javax.swing.JPanel panFec;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGrl;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panPrv;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JPanel pnRep;
    private javax.swing.JScrollPane spnTblRep;
    private javax.swing.JScrollPane spnTotal;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTotal;
    private javax.swing.JTextField txtCodCta;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtDesCorCta;
    private javax.swing.JTextField txtDesLarCta;
    private javax.swing.JTextField txtDesLarPrv;
    private javax.swing.JTextField txtNivCta;
    private javax.swing.JTextField txtSal;
    private javax.swing.JTextField txtSalAcu;
    private javax.swing.JTextField txtSalAnt;
    // End of variables declaration//GEN-END:variables

    private boolean isCamVal() {      
        if (txtCodCta.getText().equals("")) 
        {
            MsgError("Ingrese la cuenta a consultar.");
            return false;
        }
        return true;
        
//      int intFecIni[] = txtFecIni.getFecha(txtFecIni.getText());        
//	int intFecFin[] = txtFecFin.getFecha(txtFecFin.getText());
//        
//        if (intFecIni[2]==intFecFin[2]){
//            if (intFecIni[1]>intFecFin[1]){
//                MsgError("Verifique el mes ingresado.");
//                return false;                
//            }
//        }
//        
//        if (intFecIni[2]>intFecFin[2]){
//            MsgError("La fecha final no puede ser menor que la fecha inicial.");
//            return false;
//        } 
    }

//<editor-fold defaultstate="collapsed" desc="/* Borrar Función imprimir() */">  
//    private boolean imprimir()  //Rose1234 Borrar
//    { 
//        String strFecIniCtaRes = "";
//        String strNomRep = "";
//
//        boolean blnRes = true;
//        double dblSalAcu = 0.00, dblSalAct = 0.00, dblSalAnt = 0.00;
////        strFechaInicial = ""+ objUti.formatearFecha(txtFecIni.getText(),"dd/MM/yyyy","yyyy-MM-dd")+"";
////        strFechaFinal   = ""+ objUti.formatearFecha(txtFecFin.getText(),"dd/MM/yyyy","yyyy-MM-dd")+"";            
//
//        Connection conIns;
//        try {
//            conIns = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
//
//            if ((txtNivCta.getText().equals("4")) || (txtNivCta.getText().equals("5")) || (txtNivCta.getText().equals("6")) || (txtNivCta.getText().equals("7")) || (txtNivCta.getText().equals("8"))) {
//                java.util.Date datAux = Date.valueOf(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
//                strFecIniCtaRes = "" + (datAux.getYear() + 1900) + "-01-01";
//                dblSalAnt = retSalAnt(conIns, strFecIniCtaRes);
//            } else {
//                dblSalAnt = retSalAnt(conIns);
//            }
// 
//            dblSalAct = retSalAct(conIns);
//            dblSalAcu = dblSalAnt + dblSalAct;
//
//            try {
//                if (conIns != null) {
//                    if (System.getProperty("os.name").equals("Linux")) {
//                        strNomRep = "//zafiro//reportes_impresos//RptZafCon15.jrxml";
//                    } else {
//                        //strNomRep="C://Zafiro//Reportes_impresos//RptZafCon15.jrxml";
//                        strNomRep = "C:/Zafiro/Reportes/Contabilidad/ZafCon15/RptZafCon15.jrxml";
//
//                    }
//
//
//
//                    if (txtCodPrv.getText().equals(""))
//                    {
//                        JasperDesign jasDesSinPrv = JasperManager.loadXmlDesign(strNomRep);
//                        JasperReport jasperReport = JasperManager.compileReport(jasDesSinPrv);
//                        Map parameters = new HashMap();
//                        parameters.put("co_emp", ("" + objParSis.getCodigoEmpresa()));
//                        parameters.put("tx_nomemp", objParSis.getNombreEmpresa());
//                        parameters.put("fecha_rep", "" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos()));
//                        parameters.put("fecha_ini", objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
//                        parameters.put("fecha_fin", objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
//                        parameters.put("tx_codcta", txtDesCorCta.getText());
//                        parameters.put("tx_descta", txtDesLarCta.getText());
//                        parameters.put("co_codcta", txtCodCta.getText());
//                        parameters.put("nd_saldoant", new Double(dblSalAnt));
//                        parameters.put("nd_saldoAct", new Double(dblSalAct));
//                        parameters.put("nd_saldoacum", new Double(objUti.redondear(dblSalAnt + dblSalAct, objParSis.getDecimalesMostrar())));
//                        parameters.put("co_prv", (txtCodPrv.getText().equals("") ? new Integer("0") : (txtCodPrv.getText().equals("null") ? new Integer("0") : new Integer(txtCodPrv.getText()))));
//                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                        //JasperManager.printReport(report, false);               
//
//                        //para vista preliminar
//                        JasperViewer.viewReport(report, false);
//                        //JasperExportManager.exportReportToPdfFile(report, "C://Zafiro//Reportes_impresos//Rpt_ZafCon15.pdf");
//
//                    } else {
//                        JasperDesign jasDesConPrv = JasperManager.loadXmlDesign(strNomRep);
//                        JasperReport jasperReport = JasperManager.compileReport(jasDesConPrv);
//                        Map parameters = new HashMap();
//                        parameters.put("co_emp", ("" + objParSis.getCodigoEmpresa()));
//                        parameters.put("tx_nomemp", objParSis.getNombreEmpresa());
//                        parameters.put("fecha_rep", "" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos()));
//                        parameters.put("fecha_ini", objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
//                        parameters.put("fecha_fin", objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
//                        parameters.put("tx_codcta", txtDesCorCta.getText());
//                        parameters.put("tx_descta", txtDesLarCta.getText());
//                        parameters.put("co_codcta", txtCodCta.getText());
//                        parameters.put("nd_saldoant", new Double(dblSalAnt));
//                        parameters.put("nd_saldoAct", new Double(dblSalAct));
//                        parameters.put("nd_saldoacum", new Double(objUti.redondear(dblSalAnt + dblSalAct, objParSis.getDecimalesMostrar())));
//                        parameters.put("co_prv", (txtCodPrv.getText().equals("") ? new Integer("0") : (txtCodPrv.getText().equals("null") ? new Integer("0") : new Integer(txtCodPrv.getText()))));
//
//                        JasperPrint report = JasperFillManager.fillReport(jasperReport, parameters, conIns);
//                        //para vista preliminar
//                        JasperViewer.viewReport(report, false);
//                        //JasperExportManager.exportReportToPdfFile(report, "C://Zafiro//Reportes_impresos//Rpt_ZafCon15.pdf");
//                    }
//
//
//                    conIns.close();
//                    conIns = null;
//                }
//            } catch (JRException e) {
//                System.out.println("Excepción: " + e.toString());
//            }
//
//        } catch (SQLException ex) {
//            System.out.println("Error al conectarse a la base");
//        }
//        return true;
//    }
//</editor-fold>
    
    private void EnabledBoxCtas(boolean blnEstado) {
        txtCodCta.setEnabled(blnEstado);
        txtDesCorCta.setEnabled(blnEstado);
        txtDesLarCta.setEnabled(blnEstado);
        butCta.setVisible(blnEstado);
    }

    /**
     * Configurar el formulario.
     */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Configurar ZafSelFec:
            objSelFec = new ZafSelFec();
            objSelFec.setCheckBoxVisible(false);
            panFec.add(objSelFec);
            objSelFec.setBounds(4, 4, 472, 72);
            //objSelFec.setFlechaDerechaHabilitada(false);
            objSelFec.setFlechaIzquierdaHabilitada(false);

            txtCodCta.setVisible(false);
            txtCodCta.setEditable(false);
            txtCodCta.setEnabled(false);
            cboEstDoc.setSelectedIndex(1);

            //Inicializar objetos.
            objUti = new ZafUtil();
            strAux = objParSis.getNombreMenu();
            this.setTitle(strAux + VERSION);
            lblTit.setText(strAux);
            //Configurar objetos.
            //txtCodCta.setVisible(false);
            //Configurar JTable: Establecer el modelo.
            vecDat = new Vector();    //Almacena los datos
            vecCab = new Vector(12);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN, "");
            vecCab.add(INT_TBL_NOM_LOC,"Nom.Loc.");
            vecCab.add(INT_TBL_FEC_DIA, "Fec.Doc.");
            vecCab.add(INT_TBL_COD_DIA, "Cód.Dia.");
            vecCab.add(INT_TBL_DES, "Tipo Doc.");
            vecCab.add(INT_TBL_NUM_DOC, "Núm.Doc.");
            vecCab.add(INT_TBL_NUM_CHQ, "Núm.Cheq.");
            vecCab.add(INT_TBL_NOM_CLI, "Cliente/Proveedor");
            vecCab.add(INT_TBL_MON_DEB, "Debe");
            vecCab.add(INT_TBL_MON_HAB, "Haber");
            vecCab.add(INT_TBL_SAL, "Saldo");
            vecCab.add(INT_TBL_PER_GEN_DOC, "Per.Gen.Doc.");
            vecCab.add(INT_TBL_EST_REG, "Est.Reg.");


            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            int intCol[] = {INT_TBL_MON_DEB, INT_TBL_MON_HAB, INT_TBL_SAL};
            objTblTot = new ZafTblTot(spnTblRep, spnTotal, tblDat, tblTotal, intCol);

            //Configurar JTable: Establecer tipo de seleccián.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum = new ZafColNumerada(tblDat, INT_TBL_LIN);
            //Configurar JTable: Establecer el mená de contexto.
            objTblPopMnu = new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOM_LOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FEC_DIA).setPreferredWidth(90);
//            tcmAux.getColumn(INT_TBL_COD_DIA).setPreferredWidth(80);                        
            tcmAux.getColumn(INT_TBL_DES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_MON_DEB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_MON_HAB).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_SAL).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_PER_GEN_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_EST_REG).setPreferredWidth(30);

            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);

            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_COD_DIA, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_SAL, tblDat);
            if (!objPerUsr.isOpcionEnabled(2598)) {
                objTblMod.addSystemHiddenColumn(INT_TBL_PER_GEN_DOC, tblDat);
            }

            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);

            java.awt.Color colFonCol;
            colFonCol = new java.awt.Color(250, 250, 250);

            //campos ocultos
            //Configurar JTable: Renderizar celdas.
            objTblCelRenLblHid = new ZafTblCelRenLbl();
            objTblCelRenLblHid.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblHid.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_COD_DIA).setCellRenderer(objTblCelRenLblHid);
            tcmAux.getColumn(INT_TBL_NUM_DOC).setCellRenderer(objTblCelRenLblHid);
            tcmAux.getColumn(INT_TBL_NUM_CHQ).setCellRenderer(objTblCelRenLblHid);
            objTblCelRenLblHid.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLblHid.getRowRender(), INT_TBL_EST_REG).toString();
                    if (strEstReg.equals("A")) {
                        objTblCelRenLblHid.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLblHid.setForeground(Color.RED);
                    }
                }
            });

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.LEFT);
            objTblCelRenLbl.setBackground(colFonCol);
            tcmAux.getColumn(INT_TBL_NOM_LOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_FEC_DIA).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DES).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_NOM_CLI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_PER_GEN_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_EST_REG).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLbl.getRowRender(), INT_TBL_EST_REG).toString();
                    System.out.println("strEstReg: " + strEstReg);
                    if (strEstReg.equals("A")) {
                        objTblCelRenLbl.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLbl.setForeground(Color.RED);
                    }
                }
            });

            objTblCelRenLblCol = new ZafTblCelRenLbl();
            objTblCelRenLblCol.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLblCol.setTipoFormato(objTblCelRenLblCol.INT_FOR_NUM);
            objTblCelRenLblCol.setFormatoNumerico(objParSis.getFormatoNumero(), false, true);
            tcmAux.getColumn(INT_TBL_MON_DEB).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_MON_HAB).setCellRenderer(objTblCelRenLblCol);
            tcmAux.getColumn(INT_TBL_SAL).setCellRenderer(objTblCelRenLblCol);
            objTblCelRenLblCol.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                java.awt.Color colFonColCru;
                String strEstReg = "";

                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                    colFonColCru = new java.awt.Color(0, 153, 153);
                    strEstReg = objTblMod.getValueAt(objTblCelRenLblCol.getRowRender(), INT_TBL_EST_REG).toString();
                    System.out.println("strEstReg: " + strEstReg);
                    if (strEstReg.equals("A")) {
                        objTblCelRenLblCol.setForeground(Color.BLACK);
                    } else {
                        objTblCelRenLblCol.setForeground(Color.RED);
                    }
                }
            });


            //Libero los objetos auxiliares.
            tcmAux = null;

            //para totales
            tblTotal.setRowSelectionAllowed(true);
            tblTotal.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
            //objColNum=new ZafColNumerada(tblTotal,INT_TBL_DAT_LIN);            
            tblTotal.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            tblTotal.getColumnModel().getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tblTotal.getColumnModel().getColumn(INT_TBL_NOM_LOC).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_FEC_DIA).setPreferredWidth(90);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setPreferredWidth(80);
            tblTotal.getColumnModel().getColumn(INT_TBL_DES).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NUM_DOC).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NUM_CHQ).setPreferredWidth(60);
            tblTotal.getColumnModel().getColumn(INT_TBL_NOM_CLI).setPreferredWidth(170);
            tblTotal.getColumnModel().getColumn(INT_TBL_MON_DEB).setPreferredWidth(80);
            tblTotal.getColumnModel().getColumn(INT_TBL_MON_HAB).setPreferredWidth(80);
            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setPreferredWidth(70);
            tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setPreferredWidth(90);

            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_COD_DIA).setResizable(false);

            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setMaxWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setMinWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setPreferredWidth(0);
            tblTotal.getColumnModel().getColumn(INT_TBL_SAL).setResizable(false);

            if (!objPerUsr.isOpcionEnabled(2598)) 
            {
                tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setWidth(0);
                tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setMaxWidth(0);
                tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setMinWidth(0);
                tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setPreferredWidth(0);
                tblTotal.getColumnModel().getColumn(INT_TBL_PER_GEN_DOC).setResizable(false);
            }
           //fin       
            configurarVenConPrv();
            txtNivCta.setEnabled(false);
            txtNivCta.setVisible(false);
            txtNivCta.setEditable(false);

            configurarVenConCta();
            if (objParSis.getCodigoUsuario() != 1) {
                chkNotDebPrv.setVisible(false);
                chkNotDebPrv.setEnabled(false);
            }

            txtSal.setEnabled(false);
            txtSal.setEditable(false);
            txtSalAnt.setEnabled(false);
            txtSalAnt.setEditable(false);
            txtSalAcu.setEnabled(false);
            txtSalAcu.setEditable(false);

            txtSal.setBackground(new java.awt.Color(255, 255, 255));
            txtSalAnt.setBackground(new java.awt.Color(255, 255, 255));
            txtSalAcu.setBackground(new java.awt.Color(255, 255, 255));

            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);

        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
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
                case INT_TBL_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_NOM_LOC:
                    strMsg = "Nombre Local";
                    break;
                case INT_TBL_FEC_DIA:
                    strMsg = "Fecha Diario";
                    break;
                case INT_TBL_COD_DIA:
                    strMsg = "Código Diario";
                    break;
                case INT_TBL_DES:
                    strMsg = "Descripción";
                    break;
                case INT_TBL_NUM_DOC:
                    strMsg = "Número de Documento";
                    break;
                case INT_TBL_NUM_CHQ:
                    strMsg = "Número del Cheque";
                    break;
                case INT_TBL_NOM_CLI:
                    strMsg = "Nombre del Cliente";
                    break;
                case INT_TBL_MON_DEB:
                    strMsg = "Monto del Debe";
                    break;
                case INT_TBL_MON_HAB:
                    strMsg = "Monto del Haber";
                    break;
                case INT_TBL_SAL:
                    strMsg = "Saldos";
                    break;
                case INT_TBL_PER_GEN_DOC:
                    strMsg = "Persona que generó el documento";
                    break;
                case INT_TBL_EST_REG:
                    strMsg = "Estado del Registro";
                    break;
                default:
                    strMsg = "";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    private class ZafThreadGUI extends Thread {

        public void run() {
            if (!cargarDetReg()) {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butRepTbl.setText("Consultar");
            }
//            //Establecer el foco en el JTable sálo cuando haya datos.
//            if (tblDat.getRowCount()>0)
//            {
//                tabFrm.setSelectedIndex(1);
//                tblDat.setRowSelectionInterval(0, 0);
//                tblDat.requestFocus();
//            }
            objThrGUI = null;
        }
    }

    /**
     * Esta funcián permite consultar los registros de acuerdo al criterio
     * seleccionado.
     *
     * @return true: Si se pudo consultar los registros. <BR>false: En el caso
     * contrario.
     */
    private boolean cargarDetReg() 
    {
        int i;
        boolean blnRes = true;
        double dblSalAcu = 0.00, dblSalAct = 0.00, dblSalAnt = 0.00;
        strAux = "";
        String strFecIniCtaRes = "";
        String strAuxTmp = "";
        String strCodUsr = "";
        String strFilEstReg = "";
        try {
            butRepTbl.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            switch (objSelFec.getTipoSeleccion()) {
                case 0: //Básqueda por rangos
                    strAuxTmp += " AND (a2.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 1: //Fechas menores o iguales que "Hasta".
                    strAuxTmp += " AND (a2.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 2: //Fechas mayores o iguales que "Desde".
                    strAuxTmp += " AND (a2.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                    break;
                case 3: //Todo.
                    break;
            }
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());

            if (cboEstDoc.getSelectedIndex() == 0) {//Todos
                strFilEstReg = " AND a2.st_reg IN('A','I')";
            } else if (cboEstDoc.getSelectedIndex() == 1) {//Activos
                strFilEstReg = " AND a2.st_reg='A'";
            } else if (cboEstDoc.getSelectedIndex() == 2) {//Inactivos
                strFilEstReg = " AND a2.st_reg='I'";
            }

            if (chkNotDebPrv.isSelected()) {
                strAux += " AND a2.co_tipDoc NOT IN (74)";
            }
            if (con != null) {
                stm = con.createStatement();
                //Obtener el número total de registros.
                strSQL = "";
                strSQL += "SELECT x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia,";
                strSQL += " 	x.fe_dia, x.fe_ing,  x.descrip as descrip,";
                strSQL += " 	CASE WHEN y.tx_nomCli IS NULL THEN x.tx_glo";
                strSQL += "       WHEN y.tx_nomCli = (cast('' as character varying)) THEN x.tx_glo";
                strSQL += " 	ELSE y.tx_nomCli END AS tx_nomCli,";
                strSQL += " 	CASE WHEN y.ne_numDoc IS NULL THEN x.ne_numDoc";
                strSQL += " 	ELSE y.ne_numDoc END AS ne_numDoc, ";
                strSQL += "       CASE WHEN y.ne_numDoc2 IS NULL THEN x.tx_numDia";
                strSQL += "       ELSE y.ne_numDoc2 END AS ne_numDoc2";
                strSQL += " 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb)              END AS monDeb";
                strSQL += " 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monHab)              END AS monHab";
                strSQL += " 	, CASE WHEN x.st_reg='I' THEN 0 ELSE SUM(x.monDeb - x.monHab)   END AS saldo";
                strSQL += "       , x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg,x.tx_nom";
                strSQL += " FROM(	";
                strSQL += " 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,";
                strSQL += "	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, a2.tx_numDia as ne_numDoc, ''||Null as tx_numDia, ";
                strSQL += "         a2.tx_glo as tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb, ";
                strSQL += " 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL += " 	,d1.co_usr AS co_usrDia, d1.tx_nom AS tx_nomDia, a2.st_reg,a99.tx_nom FROM (tbm_cabDia AS a2 LEFT OUTER JOIN tbm_usr AS d1 ON a2.co_usrIng=d1.co_usr) INNER JOIN tbm_loc as a99 on(a2.co_emp=a99.co_emp and a2.co_loc=a99.co_loc) INNER JOIN tbm_detDia AS a3 ";
                strSQL += " 	ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL += " 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL += "       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "       AND a3.co_cta=" + txtCodCta.getText() + "";
                //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
                strSQL += "" + strAuxTmp;
                //strSQL+=" 	AND a2.st_reg='A'" + strAux + "";
                strSQL += " 	" + strFilEstReg + " " + strAux + "";

                strSQL += " 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a2.tx_numDia, a2.tx_numDia, a2.tx_glo, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg,a99.tx_nom";
                strSQL += " 	ORDER BY fe_dia, descrip, ne_numDoc) AS x";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) {
                    strSQL += " INNER JOIN(";
                } else {
                    strSQL += " LEFT OUTER JOIN(";
                }
                strSQL += " 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, ";
                strSQL += " 	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc as ne_numDoc, ''||Null AS ne_numDoc2, a1.tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb,";
                strSQL += " 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL += " 	, d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg FROM (tbm_cabMovInv AS a1 LEFT OUTER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
                strSQL += " 	INNER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
                strSQL += " 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL += " 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL += "       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "       AND a3.co_cta=" + txtCodCta.getText() + "";
                //strSQL+="       AND (a2.fe_dia BETWEEN"  + strFechaInicial + " AND " + strFechaFinal + ")";
                strSQL += "" + strAuxTmp;
                //strSQL+=" 	AND a2.st_reg='A'" + strAux + "";
                strSQL += " 	" + strFilEstReg + " " + strAux + "";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) 
                {
                    //strSQL += " AND a1.co_cli LIKE '" + strCodPrv.replaceAll("'", "''") + "'";
                    strSQL += " AND a1.co_cli = " + strCodPrv.replaceAll("'", "");
                }
                strSQL += " 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,a2.fe_dia,a2.fe_ing, a4.tx_desCor, a1.ne_numDoc, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, d1.co_usr, d1.tx_nom, a2.st_reg";
                strSQL += " 	UNION ALL";
                strSQL += " 	SELECT a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia,";
                strSQL += " 	a2.fe_dia, a2.fe_ing, a4.tx_desCor as descrip, ''||a1.ne_numDoc1 as ne_numDoc, ''||a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END) as monDeb, ";
                strSQL += " 	SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as monHab, SUM(CASE WHEN a3.nd_monDeb IS NULL THEN 0 ELSE a3.nd_monDeb END)-SUM(CASE WHEN a3.nd_monHab IS NULL THEN 0 ELSE a3.nd_monHab END) as saldo";
                strSQL += " 	, d1.co_usr AS co_usrDoc, d1.tx_nom AS tx_nomDoc, a2.st_reg FROM (tbm_cabPag AS a1 INNER JOIN tbm_usr AS d1 ON a1.co_usrIng=d1.co_usr)";
                strSQL += " 	left OUTER JOIN tbm_cabDia AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_dia)";
                strSQL += " 	INNER JOIN tbm_detDia AS a3 ON (a2.co_emp=a3.co_emp AND a2.co_loc=a3.co_loc AND a2.co_tipDoc=a3.co_tipDoc AND a2.co_dia=a3.co_dia)";
                strSQL += " 	INNER JOIN tbm_cabTipDoc AS a4 ON (a2.co_emp=a4.co_emp AND a2.co_loc=a4.co_loc AND a2.co_tipDoc=a4.co_tipDoc)";
                strSQL += "       WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL += "       AND a3.co_cta=" + txtCodCta.getText() + "";
                strSQL += "" + strAuxTmp;
                //strSQL+=" 	AND a2.st_reg='A'" + strAux + "";
                strSQL += " 	" + strFilEstReg + " " + strAux + "";
                strCodPrv = txtCodPrv.getText();
                if (!strCodPrv.equals("")) 
                {
                   //strSQL += " AND a1.co_cli LIKE '" + strCodPrv.replaceAll("'", "''") + "'";
                    strSQL += " AND a1.co_cli = " + strCodPrv.replaceAll("'", "");
                }
                strSQL += " 	GROUP BY a2.co_emp, a2.co_loc, a2.co_tipDoc, a2.co_dia, a2.fe_dia, a2.fe_ing, a4.tx_desCor, a1.ne_numDoc1, a1.ne_numDoc2, a1.tx_nomCli, a2.tx_glo, d1.co_usr, d1.tx_nom, a2.st_reg";
                strSQL += " 	) AS y";
                strSQL += " ON x.co_emp=y.co_emp AND x.co_loc=y.co_loc AND x.co_tipDoc=y.co_tipDoc AND x.co_dia=y.co_dia";

                strSQL += " GROUP BY x.co_emp, x.co_loc, x.co_tipDoc, x.co_dia,";
                strSQL += " 	x.fe_dia, x.fe_ing, y.tx_nomCli, x.descrip, x.ne_numDoc, y.ne_numDoc, x.tx_numDia";
                strSQL += " 	, x.tx_nomCli,x.tx_glo, y.ne_numDoc2, x.co_usrDia, x.tx_nomDia, y.co_usrDoc, y.tx_nomDoc, x.st_reg,x.tx_nom";
                strSQL += " ORDER BY x.fe_dia, x.fe_ing, x.descrip, x.ne_numDoc";
                strSQLRep=strSQL;
                System.out.println("cargarDetReg: " + strSQL);

                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i = 0;
                while (rst.next()) 
                {
                    if (blnCon) {
                        vecReg = new Vector();
                        vecReg.add(INT_TBL_LIN, "");
                        vecReg.add(INT_TBL_NOM_LOC,rst.getString("tx_nom"));
                        vecReg.add(INT_TBL_FEC_DIA, rst.getString("fe_dia"));
                        vecReg.add(INT_TBL_COD_DIA, rst.getString("co_dia"));
                        vecReg.add(INT_TBL_DES, rst.getString("descrip"));
                        vecReg.add(INT_TBL_NUM_DOC, rst.getString("ne_numdoc"));
                        vecReg.add(INT_TBL_NUM_CHQ, rst.getString("ne_numdoc2"));
                        vecReg.add(INT_TBL_NOM_CLI, rst.getString("tx_nomcli"));
                        vecReg.add(INT_TBL_MON_DEB, rst.getString("monDeb"));
                        vecReg.add(INT_TBL_MON_HAB, rst.getString("monHab"));
                        vecReg.add(INT_TBL_SAL, rst.getString("saldo"));
                        vecReg.add(INT_TBL_PER_GEN_DOC, "");

                        strCodUsr = rst.getObject("co_usrDia") == null ? "" : rst.getString("co_usrDia");
                        if (strCodUsr.equals("")) {
                            vecReg.setElementAt("" + rst.getString("tx_nomDoc"), INT_TBL_PER_GEN_DOC);
                        } else {
                            vecReg.setElementAt("" + rst.getString("tx_nomDia"), INT_TBL_PER_GEN_DOC);
                        }


                        vecReg.add(INT_TBL_EST_REG, rst.getString("st_reg"));
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);
                    } else {
                        break;
                    }
                }
                rst.close();
                stm.close();
                rst = null;
                stm = null;

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                if (tblDat.getRowCount() == tblDat.getRowCount()) {
                    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
                }
                pgrSis.setValue(0);
                butRepTbl.setText("Consultar");
                objTblTot.calcularTotales();
                tabFrm.setSelectedIndex(1);
                //Establecer el foco en el JTable sálo cuando haya datos.
                if (tblDat.getRowCount() > 0) 
                {
                    tblDat.setRowSelectionInterval(0, 0);
                    tblDat.requestFocus();
                }

                //PARA SALDO ACTUAL
                dblSalAct = retSalAct(con);
                //txtSal.setText("" + objUti.redondear(dblSalAct,objParSis.getDecimalesMostrar()));
                dblSalActExc = Double.parseDouble("" + objUti.redondear(dblSalAct, objParSis.getDecimalesMostrar()));
                txtSal.setText("" + objUti.formatearNumero(dblSalAct, "###,###.##", true));

                //PARA SALDO ANTERIOR
                if ((txtNivCta.getText().equals("4")) || (txtNivCta.getText().equals("5")) || (txtNivCta.getText().equals("6")) || (txtNivCta.getText().equals("7")) || (txtNivCta.getText().equals("8")))
                {
                    java.util.Date datAux = Date.valueOf(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                    strFecIniCtaRes = "" + (datAux.getYear() + 1900) + "/01/01";
                    dblSalAnt = retSalAnt(con, strFecIniCtaRes);
                } 
                else 
                {
                    dblSalAnt = retSalAnt(con);
                }
                //txtSalAnt.setText("" + objUti.redondear(dblSalAnt,objParSis.getDecimalesMostrar()));
                dblSalAntExc = Double.parseDouble("" + objUti.redondear(dblSalAnt, objParSis.getDecimalesMostrar()));
                txtSalAnt.setText("" + objUti.formatearNumero(dblSalAnt, "###,###.##", true));
                txtSalAnt.setEnabled(false);

                //PARA SALDO ACUMULADO
                dblSalAcu = dblSalAnt + dblSalAct;
                //txtSalAcu.setText("" + objUti.redondear(dblSalAcu,objParSis.getDecimalesMostrar()));
                dblSalAcuExc = Double.parseDouble("" + objUti.redondear(dblSalAcu, objParSis.getDecimalesMostrar()));
                txtSalAcu.setText("" + objUti.formatearNumero(dblSalAcu, "###,###.##", true));
                txtSalAcu.setEnabled(false);

                con.close();
                con = null;
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
    
    //Rose
    private void cargarRepote(int intTipo){
   if (objThrGUIRpt==null)
    {
        objThrGUIRpt=new ZafThreadGUIRpt();
        objThrGUIRpt.setIndFunEje(intTipo);
        objThrGUIRpt.start();
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
    private class ZafThreadGUIRpt extends Thread
    {
        private int intIndFun;

        public ZafThreadGUIRpt()
        {
            intIndFun=0;
        }

        public void run()
        {
            switch (intIndFun)
            {
                case 0: //Botón "Imprimir".
                    generarRpt(1);
                    break;
                case 1: //Botón "Vista Preliminar".
                    generarRpt(2);
                    break;
            }
            objThrGUIRpt=null;
        }

        /**
         * Esta función establece el indice de la función a ejecutar. En la clase Thread
         * se pueden ejecutar diferentes funciones. Esta función sirve para determinar
         * la función que debe ejecutar el Thread.
         * @param indice El indice de la función a ejecutar.
         */
        public void setIndFunEje(int indice)
        {
            intIndFun=indice;
        }
    }
    
     /**
     * Esta función permite generar el reporte de acuerdo al criterio seleccionado.
     * @param intTipRpt El tipo de reporte a generar.
     * <BR>Puede tomar uno de los siguientes valores:
     * <UL>
     * <LI>0: Impresión directa.
     * <LI>1: Impresión directa (Cuadro de dialogo de impresión).
     * <LI>2: Vista preliminar.
     * </UL>
     * @return true: Si se pudo generar el reporte.
     * <BR>false: En el caso contrario.
     */
   private boolean generarRpt(int intTipRpt)
    {
        Connection conIns;
        String strRutRpt, strNomRpt, strNomEmp, strFecIniCtaRes;        
        int i, intNumTotRpt;
        boolean blnRes = true;
        double dblSalAcu = 0.00, dblSalAct = 0.00, dblSalAnt = 0.00;
        strAux = "";

        try 
        {
            conIns = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            
            //Saldo Anterior
            if ((txtNivCta.getText().equals("4")) || (txtNivCta.getText().equals("5")) || (txtNivCta.getText().equals("6")) || (txtNivCta.getText().equals("7")) || (txtNivCta.getText().equals("8")))
            {
                java.util.Date datAux = Date.valueOf(objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                strFecIniCtaRes = "" + (datAux.getYear() + 1900) + "-01-01";
                dblSalAnt = retSalAnt(conIns, strFecIniCtaRes);
            } else {
                dblSalAnt = retSalAnt(conIns);
            }
            dblSalAct = retSalAct(conIns);
            dblSalAcu = dblSalAnt + dblSalAct;

            if (conIns != null) 
            {
                objRptSis.cargarListadoReportes();
                objRptSis.setVisible(true);
                if (objRptSis.getOpcionSeleccionada() == ZafRptSis.INT_OPC_ACE) {
                    intNumTotRpt = objRptSis.getNumeroTotalReportes();
                    for (i = 0; i < intNumTotRpt; i++) {
                        if (objRptSis.isReporteSeleccionado(i)) {
                            switch (Integer.parseInt(objRptSis.getCodigoReporte(i))) {
                                case 0:
                                default: 
                                    strRutRpt = objRptSis.getRutaReporte(i);
                                    strNomRpt = objRptSis.getNombreReporte(i);

                                    //Inicializar los parametros que se van a pasar al reporte.
                                    java.util.Map mapPar = new java.util.HashMap();
                                    mapPar.put("co_emp", objParSis.getCodigoEmpresa());
                                    mapPar.put("tx_nomemp", objParSis.getNombreEmpresa());
                                    mapPar.put("fecha_rep", "" + objUti.getFechaServidor(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos(), objParSis.getFormatoFechaHoraBaseDatos()));
                                    mapPar.put("fecha_ini", objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                                    mapPar.put("fecha_fin", objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()));
                                    mapPar.put("co_codcta", new Integer(txtCodCta.getText()));
                                    mapPar.put("tx_codcta", txtDesCorCta.getText());
                                    mapPar.put("tx_descta", txtDesLarCta.getText());
                                    mapPar.put("nd_saldoant", new BigDecimal(dblSalAnt));
                                    mapPar.put("nd_saldoAct", new BigDecimal(dblSalAct));
                                    mapPar.put("nd_saldoacum", new BigDecimal(objUti.redondear(dblSalAnt + dblSalAct, objParSis.getDecimalesMostrar())));
                                    mapPar.put("co_prv", (txtCodPrv.getText().equals("") ? new Integer("0") : (txtCodPrv.getText().equals("null") ? new Integer("0") : new Integer(txtCodPrv.getText()))));
                                    mapPar.put("strCamAudRpt", "" + objParSis.getNombreUsuario() );
                                    objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                    break;
                            }
                        }
                    }
                }
                conIns.close();
                conIns = null;
            }
        }
        catch (Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);          
        }
        return blnRes;
    }
    

    public double retSalAnt(Connection conSalAnt)
    {
        String que;
        double dblSalAnt = 0.00;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try 
        {
            if (conSalAnt != null)
            {
                stmSal = conSalAnt.createStatement();

                if (chkNotDebPrv.isSelected()) 
                {
                    strAux += " AND a1.co_tipDoc NOT IN(74)";
                }

                //Obtener Saldo Anterior
                que = "";
                que += " SELECT SUM(salAnt) AS salAnt FROM(";
                que += " 	SELECT sum( (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAnt ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";
                //que+=" AND a1.fe_dia<'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";


                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Básqueda por rangos
                        que += " AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        System.out.println("1");
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                    case 2: //Fechas mayores o iguales que "Desde".
                        que += " AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += " 	UNION ";
                que += " 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";
                //que+=" AND a1.fe_dia<'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";

                switch (objSelFec.getTipoSeleccion()) 
                {
                    case 0: //Básqueda por rangos
                        que += " AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                    case 2: //Fechas mayores o iguales que "Desde".
                        que += " AND (a1.fe_dia <'" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) 
                {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += ") AS x";
                System.out.println("SALDO ANTERIOR SIN PARAM FECHA: " + que);
                rstSal = stmSal.executeQuery(que);
                if (rstSal.next()) {
                    dblSalAnt = rstSal.getDouble("salAnt");
                }

                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }

        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return dblSalAnt;
    }

    public double retSalAnt(Connection conSalAnt, String fechaInicialCtasResultado) {
        String que;
        double dblSalAnt = 0.00;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try {
            conSalAnt = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conSalAnt != null) {
                stmSal = conSalAnt.createStatement();

                if (chkNotDebPrv.isSelected()) {
                    strAux += " AND a1.co_tipDoc NOT IN(74)";
                }

                //Obtener Saldo Anterior
                que = "";
                que += "SELECT SUM(salAnt) AS salAnt FROM(";
                que += " 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";
                que += " and a1.fe_dia >= '" + fechaInicialCtasResultado + "' AND a1.fe_dia < '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += " 	UNION ";
                que += " 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAnt ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";
                que += " and a1.fe_dia >= '" + fechaInicialCtasResultado + "' AND a1.fe_dia < '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += " ) AS x";
                System.out.println("retSalAnt: " + que);
                rstSal = stmSal.executeQuery(que);
                if (rstSal.next()) {
                    dblSalAnt = rstSal.getDouble("salAnt");
                }
                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return dblSalAnt;
    }

    public double retSalAct(Connection conSalAct) {
        String que;
        double dblSalAct = 0.00;
        strAux = "";
        ResultSet rstSal;
        Statement stmSal;
        try {
            if (conSalAct != null) {
                if (chkNotDebPrv.isSelected()) {
                    strAux += " AND a1.co_tipDoc NOT IN(74)";
                }
                stmSal = conSalAct.createStatement();
                
                //Obtener Saldo Actual
                que = "";
                que += "SELECT SUM(salAct) AS salAct FROM(";
                que += " 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END) ) as salAct ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabPag AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";


                switch (objSelFec.getTipoSeleccion()) {
                    case 0: //Básqueda por rangos
                        que += " AND (a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 1: //Fechas menores o iguales que "Hasta".
                        que += " AND (a1.fe_dia<='" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 2: //Fechas mayores o iguales que "Desde".
                        que += " AND (a1.fe_dia>='" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "')";
                        break;
                    case 3: //Todo.
                        break;
                }

                //que+=" AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += " 	UNION ";
                que += " 	SELECT sum(  (CASE WHEN a2.nd_mondeb IS NULL THEN 0 ELSE a2.nd_mondeb END)-(CASE WHEN a2.nd_monhab IS NULL THEN 0 ELSE a2.nd_monhab END)  ) as salAct ";
                que += " 	FROM (tbm_cabdia as a1 LEFT OUTER JOIN tbm_cabMovInv AS b1 ON a1.co_emp=b1.co_emp AND a1.co_loc=b1.co_loc AND a1.co_tipDoc=b1.co_tipDoc AND a1.co_dia=b1.co_doc)";
                que += " 	 INNER JOIN tbm_detdia AS a2 ";
                que += " 	ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipdoc=a2.co_tipdoc AND a1.co_dia=a2.co_dia ";
                que += " where a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                que += " and a2.co_cta=" + txtCodCta.getText() + "";
                que += " AND a1.fe_dia BETWEEN '" + objUti.formatearFecha(objSelFec.getFechaDesde(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "' AND '" + objUti.formatearFecha(objSelFec.getFechaHasta(), objSelFec.getFormatoFecha(), objParSis.getFormatoFechaBaseDatos()) + "'";
                que += " and a1.st_reg='A'";
                que += strAux;
                if (txtCodPrv.getText().length() > 0) {
                    que += " AND b1.co_cli=" + txtCodPrv.getText() + "";
                }
                que += " ) AS x";
                System.out.println("retSalAct: " + que);
                rstSal = stmSal.executeQuery(que);
                if (rstSal.next()) {
                    dblSalAct = rstSal.getDouble("salAct");
                }
                stmSal.close();
                stmSal = null;
                rstSal.close();
                rstSal = null;
            }

        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return dblSalAct;
    }

    /**
     * Esta funcián configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Identificación");
            arlAli.add("Nombre");
            arlAli.add("Dirección");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
            strSQL += " FROM tbm_cli AS a1";
            strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
            //strSQL+=" AND a1.st_prv='S'";
            strSQL += " ORDER BY a1.tx_nom";
            //Ocultar columnas.
            int intColOcu[] = new int[1];
            intColOcu[0] = 4;
            vcoPrv = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de proveedores", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura la "Ventana de consulta" que serï¿½ utilizada
     * para mostrar las "Cuentas".
     */
    private boolean configurarVenConCta() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_cta");
            arlCam.add("a1.tx_codCta");
            arlCam.add("a1.tx_desLar");
            arlCam.add("a1.tx_niv1");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Cuenta");
            arlAli.add("Nombre");
            arlAli.add("Nivel");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("60");
            arlAncCol.add("80");
            arlAncCol.add("400");
            arlAncCol.add("40");
            //Armar la sentencia SQL.
            strSQL = "";

            if (objParSis.getCodigoUsuario() == 1) {
                strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL += " FROM tbm_plaCta AS a1";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a1.tx_tipCta='D'";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " ORDER BY a1.tx_codCta";
            } else {
                strSQL += "SELECT a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL += " FROM tbm_placta as a1";
                //strSQL+=" INNER JOIN tbr_ctatipdocusr as a2 ON (a1.co_emp=a2.co_emp and a1.co_cta=a2.co_cta)";
                strSQL += " WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                //strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a1.tx_tipCta='D'";
                strSQL += " AND a1.st_reg='A'";
                strSQL += " GROUP BY a1.co_cta, a1.tx_codCta, a1.tx_desLar, a1.tx_niv1";
                strSQL += " ORDER BY a1.tx_codCta";
            }
            vcoCta = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de cuentas contables", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoCta.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoCta.setCampoBusqueda(1);
            vcoCta.setCriterio1(7);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean mostrarVenConCta(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoCta.setCampoBusqueda(1);
                    vcoCta.show();
                    if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        txtNivCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    }
                    break;
                case 1: //Básqueda directa por "Námero de cuenta".
                    if (vcoCta.buscar("a1.tx_codCta", txtDesCorCta.getText())) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        txtNivCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    } else {
                        vcoCta.setCampoBusqueda(1);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            txtNivCta.setText(vcoCta.getValueAt(4));
                            objTblMod.removeAllRows();
                        } else {
                            txtDesCorCta.setText(strDesCorCta);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripcián larga".
                    if (vcoCta.buscar("a1.tx_desLar", txtDesLarCta.getText())) {
                        txtCodCta.setText(vcoCta.getValueAt(1));
                        txtDesCorCta.setText(vcoCta.getValueAt(2));
                        txtDesLarCta.setText(vcoCta.getValueAt(3));
                        txtNivCta.setText(vcoCta.getValueAt(4));
                        objTblMod.removeAllRows();
                    } else {
                        vcoCta.setCampoBusqueda(2);
                        vcoCta.setCriterio1(11);
                        vcoCta.cargarDatos();
                        vcoCta.show();
                        if (vcoCta.getSelectedButton() == vcoCta.INT_BUT_ACE) {
                            txtCodCta.setText(vcoCta.getValueAt(1));
                            txtDesCorCta.setText(vcoCta.getValueAt(2));
                            txtDesLarCta.setText(vcoCta.getValueAt(3));
                            txtNivCta.setText(vcoCta.getValueAt(4));
                            objTblMod.removeAllRows();
                        } else {
                            txtDesLarCta.setText(strDesLarCta);
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

    private void setPuntosCta() {
        strAux = txtDesCorCta.getText();
        String strCodCtaOri = strAux;
        String strCodCtaDes = "";
        char chrCtaOri;
        //obtengo la longitud de mi cadena
        int intLonCodCta = strCodCtaOri.length();
        int intLonCodCtaMen = intLonCodCta - 1;
        //PARA CUANDO LOS TRES ULTIMOS DIGITOS SE LOS DEBE TOMAR COMO UN NIVEL DIFERENTE
        int intLonCodCtaMenTreDig = intLonCodCta - 2;
        if (strCodCtaOri.length() <= 1) {
            return;
        } else {
            chrCtaOri = strCodCtaOri.charAt(1);
            if (chrCtaOri != '.') {
                for (int i = 0; i < strCodCtaOri.length(); i++) {
                    if (i == 0) {
                        strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                        strCodCtaDes = strCodCtaDes + ".";
                    } else {
                        if ((strCodCtaOri.length() % 2) == 0) {
                            if (((i % 2) == 0) && (i < intLonCodCtaMenTreDig)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                strCodCtaDes = strCodCtaDes + ".";
                            }
                            if (((i % 2) == 0) && (i == intLonCodCtaMenTreDig)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                            } else {
                                if ((i % 2) != 0) {
                                    strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                }
                            }
                        } else {
                            if (((i % 2) == 0) && (i != intLonCodCtaMen)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                strCodCtaDes = strCodCtaDes + ".";
                            }
                            if (((i % 2) == 0) && (i == intLonCodCtaMen)) {
                                strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                            } else {
                                if ((i % 2) != 0) {
                                    strCodCtaDes = strCodCtaDes + strCodCtaOri.charAt(i);
                                }
                            }
                        }
                    }
                }
                txtDesCorCta.setText(strCodCtaDes);
            }
        }
    }
}
