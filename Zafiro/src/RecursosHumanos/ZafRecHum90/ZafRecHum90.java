package RecursosHumanos.ZafRecHum90;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;

/**
 * Cambio de empleado a otra empresa..
 *
 * @author Tony Sanginez 17/07/2017
 */
public class ZafRecHum90 extends javax.swing.JInternalFrame {

    private ZafParSis objParSis;
    private String strCodTra = "";
    private String strNomTra = "";
    private String strNomEmp = "";
    private String strEst = "";

    private ZafUtil objUti;

    private Connection conGru, conCos;
    private Statement stmGru, stmCos;

    private ZafVenCon vcoTra;
    private String strVersion = "  v0.3";
    private String strCorEle="sistemas2@tuvalsa.com;sistemas4@tuvalsa.com;rrhh@tuvalsa.com;contador@tuvalsa.com";

    /**
     * Creates new form revisionTecMer
     */
    public ZafRecHum90(Librerias.ZafParSis.ZafParSis obj) {
        try {
            this.objParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            if (objParSis.getCodigoEmpresa() != objParSis.getCodigoEmpresaGrupo()) {
                initComponents();
                objUti = new ZafUtil();
                this.setTitle(objParSis.getNombreMenu() + strVersion);
                lblTit.setText(objParSis.getNombreMenu());
                configurarVenConTra();
            } else {
                mostrarMsgInf("Este programa sólo puede ser ejecutado por EMPRESAS.");
                dispose();
            }

        } catch (CloneNotSupportedException e) {
            objUti.mostrarMsgErr_F1(this, e);
            e.printStackTrace();
        }
    }

    public void abrirCon() {
        try {
            conGru = java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.1.2:5432/Zafiro2006", objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            conCos = java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.1.2:5432/dbCosenco", objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            //conGru = java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.7:5432/Zafiro2006_16_Jul_2017", objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            //conCos = java.sql.DriverManager.getConnection("jdbc:postgresql://172.16.8.7:5432/dbCosenco_27_Jul_2017", objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    public void CerrarCon() {
        try {
            conGru.close();
            conCos.close();
            conGru = null;
            conCos = null;
        } catch (java.sql.SQLException Evt) {
            objUti.mostrarMsgErr_F1(this, Evt);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        buttonGroup1 = new javax.swing.ButtonGroup();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panTabGen = new javax.swing.JPanel();
        PanTabGen = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        butTra = new javax.swing.JButton();
        txtCodTra = new javax.swing.JTextField();
        txtNomTra = new javax.swing.JTextField();
        panOri = new javax.swing.JPanel();
        txtNomEmp = new javax.swing.JTextField();
        panDes = new javax.swing.JPanel();
        rdbTuv = new javax.swing.JRadioButton();
        rdbCas = new javax.swing.JRadioButton();
        rdbDim = new javax.swing.JRadioButton();
        rdbCos = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtEst = new javax.swing.JTextField();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        butGua = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

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

        panCen.setLayout(new java.awt.BorderLayout());

        tabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        tabGen.setPreferredSize(new java.awt.Dimension(115, 100));

        panTabGen.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        panTabGen.setPreferredSize(new java.awt.Dimension(100, 90));
        panTabGen.setLayout(new java.awt.BorderLayout());

        PanTabGen.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        PanTabGen.setFocusCycleRoot(true);
        PanTabGen.setPreferredSize(new java.awt.Dimension(100, 220));
        PanTabGen.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Empleado:"); // NOI18N
        PanTabGen.add(lblTipDoc);
        lblTipDoc.setBounds(10, 10, 120, 20);

        butTra.setText("jButton1"); // NOI18N
        butTra.setPreferredSize(new java.awt.Dimension(20, 20));
        butTra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTraActionPerformed(evt);
            }
        });
        PanTabGen.add(butTra);
        butTra.setBounds(370, 10, 20, 20);

        txtCodTra.setBackground(objParSis.getColorCamposObligatorios());
        txtCodTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        txtCodTra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
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
        PanTabGen.add(txtCodTra);
        txtCodTra.setBounds(90, 10, 70, 20);

        txtNomTra.setBackground(objParSis.getColorCamposObligatorios());
        txtNomTra.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
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
        PanTabGen.add(txtNomTra);
        txtNomTra.setBounds(160, 10, 210, 20);

        panOri.setBorder(javax.swing.BorderFactory.createTitledBorder("Origen"));

        txtNomEmp.setEditable(false);
        txtNomEmp.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout panOriLayout = new javax.swing.GroupLayout(panOri);
        panOri.setLayout(panOriLayout);
        panOriLayout.setHorizontalGroup(
            panOriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panOriLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtNomEmp, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addContainerGap())
        );
        panOriLayout.setVerticalGroup(
            panOriLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panOriLayout.createSequentialGroup()
                .addComponent(txtNomEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
        );

        PanTabGen.add(panOri);
        panOri.setBounds(30, 40, 150, 60);
        panOri.getAccessibleContext().setAccessibleName("");

        panDes.setBorder(javax.swing.BorderFactory.createTitledBorder("Destino"));

        buttonGroup1.add(rdbTuv);
        rdbTuv.setSelected(true);
        rdbTuv.setText("TUVAL S.A.");

        buttonGroup1.add(rdbCas);
        rdbCas.setText("CASTEK S.A.");

        buttonGroup1.add(rdbDim);
        rdbDim.setText("DIMULTI S.A.");

        buttonGroup1.add(rdbCos);
        rdbCos.setText("INDUSTRIAS COSENCO S.A.");

        javax.swing.GroupLayout panDesLayout = new javax.swing.GroupLayout(panDes);
        panDes.setLayout(panDesLayout);
        panDesLayout.setHorizontalGroup(
            panDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rdbTuv)
                    .addComponent(rdbCas)
                    .addComponent(rdbDim)
                    .addComponent(rdbCos))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        panDesLayout.setVerticalGroup(
            panDesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panDesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rdbTuv)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbCas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbDim)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rdbCos)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        PanTabGen.add(panDes);
        panDes.setBounds(230, 40, 200, 150);

        jLabel1.setText("Estado:");
        PanTabGen.add(jLabel1);
        jLabel1.setBounds(40, 120, 50, 20);

        txtEst.setEditable(false);
        txtEst.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtEst.setBorder(null);
        PanTabGen.add(txtEst);
        txtEst.setBounds(80, 110, 30, 40);

        panTabGen.add(PanTabGen, java.awt.BorderLayout.PAGE_START);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
        panBarEst.setLayout(new java.awt.BorderLayout());

        lblMsgSis.setText("Listo");
        lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

        panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
        panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
        panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

        pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
        pgrSis.setBorderPainted(false);
        pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
        pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
        panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

        panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

        panTabGen.add(panBarEst, java.awt.BorderLayout.SOUTH);

        jPanel1.setPreferredSize(new java.awt.Dimension(296, 26));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(110, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel1.add(butGua);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel1.add(butCer);

        panTabGen.add(jPanel1, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        panTit.setPreferredSize(new java.awt.Dimension(100, 30));

        lblTit.setText("titulo"); // NOI18N
        panTit.add(lblTit);

        getContentPane().add(panTit, java.awt.BorderLayout.PAGE_START);

        setBounds(0, 0, 456, 361);
    }// </editor-fold>//GEN-END:initComponents


private void txtCodTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodTraActionPerformed
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
            txtNomEmp.setText("");
            txtEst.setText("");
        } else {
            BuscarTra("a1.co_tra", txtCodTra.getText(), 0);
        }
    } else {
        txtCodTra.setText(strCodTra);
    }
}//GEN-LAST:event_txtCodTraFocusLost

    public void BuscarTra(String campo, String strBusqueda, int tipo) {
         vcoTra.setTitle("Listado de Empleados");
        if (vcoTra.buscar(campo, strBusqueda)) {
            txtCodTra.setText(vcoTra.getValueAt(1));
            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
            txtNomEmp.setText(vcoTra.getValueAt(4));
            txtEst.setText(vcoTra.getValueAt(5));
        } else {
            vcoTra.setCampoBusqueda(tipo);
            vcoTra.cargarDatos();
            vcoTra.show();
            if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                txtCodTra.setText(vcoTra.getValueAt(1));
                txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                txtNomEmp.setText(vcoTra.getValueAt(4));
                txtEst.setText(vcoTra.getValueAt(5));
            } else {
                txtCodTra.setText(strCodTra);
                txtNomTra.setText(strNomTra);
                txtNomEmp.setText(strNomEmp);
                txtEst.setText(strEst);
            }
        }
    }

private void butTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTraActionPerformed
    strCodTra = txtCodTra.getText();
    mostrarVenConTra(0);
}//GEN-LAST:event_butTraActionPerformed

    private boolean mostrarVenConTra(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoTra.setCampoBusqueda(1);
                    vcoTra.show();
                    if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        txtNomEmp.setText(vcoTra.getValueAt(4));
                        txtEst.setText(vcoTra.getValueAt(5));
                    }
                    break;
                case 1: //Búsqueda directa por "Número de cuenta".
                    if (vcoTra.buscar("a1.co_tra", txtCodTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        txtNomEmp.setText(vcoTra.getValueAt(4));
                        txtEst.setText(vcoTra.getValueAt(5));
                    } else {
                        vcoTra.setCampoBusqueda(0);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                            txtNomEmp.setText(vcoTra.getValueAt(4));
                            txtEst.setText(vcoTra.getValueAt(5));
                        } else {
                            txtCodTra.setText(strCodTra);
                        }
                    }
                    break;
                case 2: //Búsqueda directa por "Descripción larga".
                    if (vcoTra.buscar("a1.tx_ape", txtNomTra.getText())) {
                        txtCodTra.setText(vcoTra.getValueAt(1));
                        txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                        txtNomEmp.setText(vcoTra.getValueAt(4));
                        txtEst.setText(vcoTra.getValueAt(5));
                    } else {
                        vcoTra.setCampoBusqueda(1);
                        vcoTra.setCriterio1(11);
                        vcoTra.cargarDatos();
                        vcoTra.show();
                        if (vcoTra.getSelectedButton() == vcoTra.INT_BUT_ACE) {
                            txtCodTra.setText(vcoTra.getValueAt(1));
                            txtNomTra.setText(vcoTra.getValueAt(2) + " " + vcoTra.getValueAt(3));
                            txtNomEmp.setText(vcoTra.getValueAt(4));
                            txtEst.setText(vcoTra.getValueAt(5));
                        } else {
                            txtNomTra.setText(strNomTra);
                            txtNomEmp.setText(strNomEmp);
                            txtEst.setText(strEst);
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
    private void txtNomTraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomTraActionPerformed
        // TODO add your handling code here:
        txtNomTra.transferFocus();
    }//GEN-LAST:event_txtNomTraActionPerformed

    private void txtNomTraFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusGained
        // TODO add your handling code here:
        strNomTra = txtNomTra.getText();
        strNomEmp = txtNomEmp.getText();
        strEst = txtEst.getText();
        txtNomTra.selectAll();
    }//GEN-LAST:event_txtNomTraFocusGained

    private void txtNomTraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomTraFocusLost
        // TODO add your handling code here:
        if (txtNomTra.isEditable()) {
            //Validar el contenido de la celda sólo si ha cambiado.
            if (!txtNomTra.getText().equalsIgnoreCase(strNomTra)) {
                if (txtNomTra.getText().equals("")) {
                    txtCodTra.setText("");
                    txtNomTra.setText("");
                    txtNomEmp.setText("");
                    txtEst.setText("");
                } else {
                    mostrarVenConTra(2);
                }
            } else {
                txtNomTra.setText(strNomTra);
                txtNomEmp.setText(strNomEmp);
                txtEst.setText(strEst);
            }
        }
    }//GEN-LAST:event_txtNomTraFocusLost

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
        exitForm(null);
    }//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
        exitForm(null);
    }//GEN-LAST:event_formInternalFrameClosing

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        if (mostrarMsgCon("¿Está seguro que desea realizar esta operación?") == 0) {
            if (validaCampos()) {
                if (guardar()) {
                    lblMsgSis.setText("Guardando datos...");
                    pgrSis.setIndeterminate(true);
                    mostrarMsgInf("SE HA GUARDADO CON EXITO.");
                    lblMsgSis.setText("Listo");
                    pgrSis.setIndeterminate(false);
                } else {
                    //mostrarMsgInf("Ocurrió un error al realizar la operación GUARDAR.\nIntente realizar la operación nuevamente.\nSi el problema persiste notifiquelo a su administrador del sistema. TS");
                    lblMsgSis.setText("Error...");
                    pgrSis.setIndeterminate(false);
                }
            }
        }
    }//GEN-LAST:event_butGuaActionPerformed
    /**
     * Cerrar la aplicacián.
     */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {
        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        strTit = "Mensaje del sistema Zafiro";
        strMsg = "Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
            dispose();
        }
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría
     * utilizar para mostrar al usuario un mensaje que indique el campo que es
     * invalido y que debe llenar o corregir.
     *
     * @param strMsg El mensaje que se desea mostrar en el cuadro de diálogo.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private int mostrarMsgCon(String strMsg) {
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        return javax.swing.JOptionPane.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanTabGen;
    private javax.swing.JButton butCer;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butTra;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panDes;
    private javax.swing.JPanel panOri;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JPanel panTabGen;
    private javax.swing.JPanel panTit;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JRadioButton rdbCas;
    private javax.swing.JRadioButton rdbCos;
    private javax.swing.JRadioButton rdbDim;
    private javax.swing.JRadioButton rdbTuv;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTextField txtCodTra;
    private javax.swing.JTextField txtEst;
    private javax.swing.JTextField txtNomEmp;
    private javax.swing.JTextField txtNomTra;
    // End of variables declaration//GEN-END:variables

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
            arlCam.add("a1.nomEmp");
            arlCam.add("a1.st_reg");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Apellidos");
            arlAli.add("Nombres");
            arlAli.add("Nom.Emp.");
            arlAli.add("Estado");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("150");
            arlAncCol.add("150");
            arlAncCol.add("150");
            arlAncCol.add("40");

            String strSQL = "";
            if (objParSis.getCodigoEmpresa() == objParSis.getCodigoEmpresaGrupo()) {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg,c.tx_nom as nomEmp from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) inner join tbm_emp c on (b.co_emp=c.co_emp) "
                        + "order by (a.tx_ape || ' ' || a.tx_nom)";
            } else {
                strSQL = "select a.co_tra,a.tx_ape,a.tx_nom,b.st_reg,c.tx_nom as nomEmp from tbm_tra a inner join tbm_traemp b on(a.co_tra=b.co_tra) inner join tbm_emp c on (b.co_emp=c.co_emp)"
                        + " where b.co_emp = " + objParSis.getCodigoEmpresa()
                        + " order by (a.tx_ape || ' ' || a.tx_nom)";

            }

            //Ocultar columnas.
            int intColOcu[] = new int[1];
            vcoTra = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Empleados", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            intColOcu = null;
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean validaCampos() {
        if (txtCodTra.getText().equals("")) {
            MensajeValidaCampo("Cliente");
            txtCodTra.requestFocus();
            return false;
        }
        if (rdbTuv.isSelected()) {
            if (txtNomEmp.getText().trim().equals(rdbTuv.getText()) && txtEst.getText().equals("A")) {
                JOptionPane.showMessageDialog(this, "El empleado ya se encuentra activo y no se puede trasladar a la misma empresa.", "Mensaje del Sistema Zafiro", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else if (rdbCas.isSelected()) {
            if (txtNomEmp.getText().trim().equals(rdbCas.getText()) && txtEst.getText().equals("A")) {
                JOptionPane.showMessageDialog(this, "El empleado ya se encuentra activo y no se puede trasladar a la misma empresa.", "Mensaje del Sistema Zafiro", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else if (rdbDim.isSelected()) {
            if (txtNomEmp.getText().trim().equals(rdbDim.getText()) && txtEst.getText().equals("A")) {
                JOptionPane.showMessageDialog(this, "El empleado ya se encuentra activo y no se puede trasladar a la misma empresa.", "Mensaje del Sistema Zafiro", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        } else if (rdbCos.isSelected()) {
            if (txtNomEmp.getText().trim().equals(rdbCos.getText()) && txtEst.getText().equals("A")) {
                JOptionPane.showMessageDialog(this, "El empleado ya se encuentra activo y no se puede trasladar a la misma empresa.", "Mensaje del Sistema Zafiro", JOptionPane.INFORMATION_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void MensajeValidaCampo(String  strNomCampo) {
        String strMsg;
        strMsg = "El campo <<" + strNomCampo + ">> es obligatorio.\nEscriba un(a) " + strNomCampo + " valido(a) y vuelva a intentarlo.";
        JOptionPane.showMessageDialog(this, strMsg, "Mensaje del Sistema Zafiro", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean guardar() {
        boolean booRes = false;
        boolean booGru = false;
        boolean booCos = false;
        String strSql;
        Calendar calendario = new GregorianCalendar();
        int intMes = calendario.get(Calendar.MONTH) + 1;
        int intAno = calendario.get(Calendar.YEAR);
        try {
            abrirCon();
            conGru.setAutoCommit(false);
            conCos.setAutoCommit(false);
            //DESDE COSENCO O HACIA COSENCO
            if (!verificaYaActivadoOtraEmpresa(conGru, conCos, Integer.valueOf(txtCodTra.getText()))) {
                if (txtNomEmp.getText().contains("COSENCO") || rdbCos.isSelected()) {
                if (conGru != null && conCos != null) {
                    try {
                        stmGru = conGru.createStatement();
                        stmCos = conCos.createStatement();
                        //VA DESDE GRUPO HACIA COSENCO O DE COSENCO A COSENCO
                        if (rdbCos.isSelected()) {
                            //<editor-fold defaultstate="collapsed" desc="VA DESDE COSENCO A COSENCO. SOLO ACTIVACIÓN">  
                            //VA DESDE COSENCO A COSENCO. SOLO ACTIVACIÓN. SE CAMBIA EL ESTADO DE INACTIVO A ACTIVO. EN EL PASO 1
                            //SE NULLEAN LOS DATOS DEL TRAEMP EN EL PASO 2
                            //EL PASO 3 NO SE TOMA EN CUENTA YA QUE SOLO ES ACTIVACIÓN
                            //VERIFICA SI YA EXISTEN LOS RUBROS DE INGEGRTRAEMP Y SE INSERTAN DEL EMPLEADO EDDY LINO DE TUVAL SE CREA UNA COPIA PASO 4
                            //VERIFICA SI YA EXISTEN LOS RUBROS DE DATGENINGEGRTRAEMP Y SE INSERTAN DEL EMPLEADO EDDY LINO DE TUVAL SE CREA UNA COPIA PASO 4
                            //ASIGNA EL PORCENTAJE DE LOS RUBROS PASO 5
                            // EL PASO 6 SOLOE ES PARA CASOS COMO GRUPO A COSENCO O COSENCO GRUPO QUE NO SE HAYAN INGRESADO ANTES A TBM_TRA
                            if (txtNomEmp.getText().trim().equals(rdbCos.getText()) && txtEst.getText().equals("I")) {
                                System.out.println("CASO 1: COSENCO A COSENCO, INACTIVO A ACTIVO...");
                                //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                strSql = "";
                                strSql += " UPDATE tbm_traEmp ";
                                strSql += " SET st_reg='A'";
                                strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += " AND co_tra=" + txtCodTra.getText();
                                System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + objParSis.getCodigoEmpresa() + " trabajador inactivo: " + strSql);
                                stmCos.executeUpdate(strSql);
                                //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                strSql = "";
                                strSql += " UPDATE tbm_traEmp \n";
                                strSql += "SET co_ofi=x.co_ofi, \n";
                                strSql += "    co_dep=x.co_dep, \n";
                                strSql += "    co_jef=x.co_jef, \n";
                                strSql += "    co_hor=x.co_hor, \n";
                                strSql += "    st_recAlm=x.st_recAlm, \n";
                                strSql += "    co_car=x.co_car, \n";
                                strSql += "    st_reg='A', \n";
                                strSql += "    fe_ing=CURRENT_TIMESTAMP, \n";
                                strSql += "    fe_ultMod=NULL, \n";
                                strSql += "    co_usrIng=" + objParSis.getCodigoUsuario() + ", \n";
                                strSql += "    co_usrMod=NULL, \n";
                                strSql += "    tx_tipModSue=null, \n";
                                strSql += "    co_pla=x.co_pla, \n";
                                strSql += "    tx_numCtaBan=x.tx_numCtaBan, \n";
                                strSql += "    tx_tipCtaBan=x.tx_tipCtaBan, \n";
                                strSql += "    st_recFonRes=x.st_recFonRes, \n";
                                strSql += "    tx_forRecFonRes=x.tx_forRecFonRes, \n";
                                strSql += "    nd_valAlm=x.nd_valAlm, \n";
                                strSql += "    nd_apoPerIES=x.nd_apoPerIES, \n";
                                strSql += "    fe_iniCon=NULL, \n";
                                strSql += "    fe_finPerPruCon=NULL, \n";
                                strSql += "    fe_finCon=NULL, \n";
                                strSql += "    st_perPruCon='N', \n";
                                strSql += "    fe_reaFinCon=NULL, \n";
                                strSql += "    tx_forRecAlm=x.tx_forRecAlm, \n";
                                strSql += "    tx_tipTra=x.tx_tipTra\n";
                                strSql += "FROM (SELECT *\n";
                                strSql += "      FROM  tbm_traemp\n";
                                strSql += "      WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += "      and co_tra=" + txtCodTra.getText() + " ) AS x\n";
                                strSql += "WHERE tbm_traemp.co_tra=x.co_tra and tbm_traemp.co_emp=" + obtieneCodEmpresaDestino();
                                System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + objParSis.getCodigoEmpresa() + " trabajador inactivo y ya existia en " + objParSis.getCodigoEmpresa() + ": " + strSql);//
                                stmCos.executeUpdate(strSql);
                                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"
                                if (!verificaIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    if (obtenerIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                        System.out.println("PASO 4:");
                                    }
                                }
                                if (!verificaDatGenIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    if (obtenerDatGenIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                        System.out.println("PASO 4:");
                                    }
                                }
                                //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                strSql = "";
                                //Primera Quincena
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                //Fin de mes
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                stmCos.executeUpdate(strSql);
                                stmCos.close();
                                stmCos = null;
                                booCos = true;
                                booGru = true;
                                booRes = true;
                            }
                            // </editor-fold>     
                            //<editor-fold defaultstate="collapsed" desc="VA DESDE GRUPO A COSENCO">  
                            else{
                                //VERIFICA PRIMERO SI EN COSENCO EL TRABAJADOR YA EXISTIA ANTES
                                if (verificaTrabajadorEmpresas(conCos, Integer.valueOf(txtCodTra.getText()))) {
                                    System.out.println("CASO 2: GRUPO A COSENCO, EMPLEADO YA SE EXISTIA..");
                                    //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='I'";
                                    strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                    System.out.println("PASO 1.0:" + strSql);
                                    stmGru.executeUpdate(strSql);

                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='A'";
                                    strSql += " WHERE co_emp=" + obtieneCodEmpresaDestino();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 1.1:" + strSql);

                                    //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                    System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + obtieneCodEmpresaDestino() + " trabajador activo y ya existia en " + obtieneCodEmpresaDestino() + ": " + strSql);//
                                    if (!obtenerTraEmpOtraEmpresa(conGru, conCos)) {
                                    }

                                    //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                    if (!verificaTrabajadorSueldos(conCos, Integer.valueOf(txtCodTra.getText()))) {
                                        if (obtenerSueTraOtraEmpresa(conCos, conGru)) {
                                            strSql = "";
                                            strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                            System.out.println("PASO 3:" + strSql);
                                            stmCos.executeUpdate(strSql);
                                        }
                                    }
                                    //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"
                                    if (!verificaIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        if (obtenerIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                            System.out.println("PASO 4.0:");
                                        }
                                    }
                                    if (!verificaDatGenIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        if (obtenerDatGenIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                            System.out.println("PASO 4.1:");
                                        }
                                    }
                                    //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                    strSql = "";
                                    //Primera Quincena
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                    //Fin de mes
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 5:" + strSql);
                                    
                                    //PASO 6: Si no existe copiar los datos del empleado 
                                    if (!obtenerTra(conGru, conCos)) {
                                        System.out.println("PASO 6: OCURRIO UN PROBLEMA");
                                        return false;
                                    }
                                    stmCos.close();
                                    stmCos = null;
                                    booGru = true;
                                    booCos = true;
                                    booRes = true;
                                } 
                                   
                            // SI ENTRA POR AQUI ES PORQUE EL EMPLEADO NO EXISTIA ANTES EN COSENCO...
                            else {
                                    System.out.println("CASO 3: GRUPO A COSENCO, EMPLEADO NO EXISTIA..");
                                    //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='I'";
                                    strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + "; ";
                                    System.out.println("PASO 1.0:" + strSql);
                                    stmGru.executeUpdate(strSql);
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_traEmp (co_emp, co_tra, fe_ing, co_usrIng)  ";
                                    strSql += " VALUES(" + obtieneCodEmpresaDestino() + ", " + txtCodTra.getText() + ", CURRENT_TIMESTAMP, " + objParSis.getCodigoUsuario() + ")";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 1.1:" + strSql);
                                    
                                    //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                    if (!obtenerTraEmpOtraEmpresa(conGru, conCos)) {
                                        System.out.println("PASO 2: OCURRIÓ UN ERROR");
                                        return false;
                                    }

                                    //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                    if (!verificaTrabajadorSueldos(conCos, Integer.valueOf(txtCodTra.getText()))) {
                                        if (obtenerSueTraOtraEmpresa(conGru, conCos)) {
                                            strSql = "";
                                            strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                            stmGru.executeUpdate(strSql);
                                            System.out.println("PASO 3:" + strSql);
                                        }
                                    }
                                    
                                    //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"
                                    if (!verificaIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        if (obtenerIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                            System.out.println("PASO 4.0:");
                                        }
                                    }
                                    if (!verificaDatGenIngEgrMenTra(conCos, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        if (obtenerDatGenIngEgrMenTraOtraEmpresa(conGru, conCos, intAno, intMes)) {
                                            System.out.println("PASO 4.1:");
                                        }
                                    }
                                    
                                    //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                    strSql = "";
                                    //Primera Quincena
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                    //Fin de mes
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 5:" + strSql);
                                    //PASO 6: Si no existe copiar los datos del empleado 
                                    if (!obtenerTra(conGru, conCos)) {
                                        System.out.println("Error paso 6");
                                        return false;
                                    }
                                    stmCos.close();
                                    stmGru.close();
                                    stmCos = null;
                                    stmGru = null;
                                    booGru = true;
                                    booCos = true;
                                    booRes = true;
                                }
                            }// </editor-fold>     
                        } 
                        else {
                            //<editor-fold defaultstate="collapsed" desc="VA DESDE COSENCO HACIA GRUPO">    
                            //ESTO ES REDUNDANTE PERO SOLO SE HACE PARA TENER UNA GUIA Y QUE NO SE PIERDAN EN EL CODIGO
                            if ((rdbCas.isSelected()) || (rdbTuv.isSelected()) || (rdbDim.isSelected())) {
                                 //VERIFICA PRIMERO SI EN GRUPO EL TRABAJADOR YA EXISTIA ANTES
                                System.out.println("CASO 4: COSENCO A GRUPO, EMPLEADO YA EXISTIA..");
                                if (verificaTrabajadorEmpresas(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                    //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='I'";
                                    strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 1.0:" + strSql);
                                    
                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='A'";
                                    strSql += " WHERE co_emp=" + obtieneCodEmpresaDestino();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 1.1:" + strSql);
                                    
                                    //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                    if (!obtenerTraEmpOtraEmpresa(conCos, conGru)) {
                                         System.out.println("PASO 2: OCURRIÓ UN ERROR");
                                         return false;
                                    }
                                    //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                    if (!verificaTrabajadorSueldos(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                        if (obtenerSueTraOtraEmpresa(conCos, conGru)) {
                                            strSql = "";
                                            strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                            stmGru.executeUpdate(strSql);
                                            System.out.println("PASO 3:" + strSql);
                                        }
                                    }
                                    //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"
                                    if (!verificaIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        strSql = "";
                                        strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                                        strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
                                        strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
                                        strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                        stmGru.executeUpdate(strSql);
                                        System.out.println("PASO 4.0:" + strSql);
                                    }

                                    if (!verificaDatGenIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        strSql = "";
                                        strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                                        strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
                                        strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                        stmGru.executeUpdate(strSql);
                                        System.out.println("PASO 4.1:" + strSql);
                                    }
                                    //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                    strSql = "";
                                    //Primera Quincena
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                    //Fin de mes
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 5:" + strSql);
                                    stmGru.close();
                                    stmCos.close();
                                    stmCos = null;
                                    stmGru = null;
                                    booGru = true;
                                    booCos = true;
                                    booRes = true;
                                } 
                                else {
                                    //SI VIENE POR AQUI ES PORQUE EL TRABAJADOR NO EXISTIA EN GRUPO
                                    System.out.println("CASO 5: COSENCO A GRUPO, EMPLEADO NO EXISTIA..");
                                    //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                    strSql = "";
                                    strSql += " UPDATE tbm_traEmp ";
                                    strSql += " SET st_reg='I'";
                                    strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSql += " AND co_tra=" + txtCodTra.getText() + "; ";
                                    stmCos.executeUpdate(strSql);
                                    System.out.println("PASO 1.0:" + strSql);
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_traEmp (co_emp, co_tra, fe_ing, co_usrIng)  ";
                                    strSql += " VALUES(" + obtieneCodEmpresaDestino() + ", " + txtCodTra.getText() + ", CURRENT_TIMESTAMP, " + objParSis.getCodigoUsuario() + ")";
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 1.1:" + strSql);
                                    //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                    if (!obtenerTraEmpOtraEmpresa(conCos, conGru)) {
                                        System.out.println("PASO 2: OCURRIÓ UN ERROR");
                                        return false;
                                    }
                                    //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                    if (!verificaTrabajadorSueldos(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                        if (obtenerSueTraOtraEmpresa(conCos, conGru)) {
                                            strSql = "";
                                            strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                            stmCos.executeUpdate(strSql);
                                            System.out.println("PASO 3:" + strSql);
                                        }
                                    }
                                //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"

                                    if (!verificaIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        strSql = "";
                                        strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                                        strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
                                        strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
                                        strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                        stmGru.executeUpdate(strSql);
                                        System.out.println("PASO 4.0:" + strSql);
                                    }
                                    if (!verificaDatGenIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                        strSql = "";
                                        strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                                        strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
                                        strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                        stmGru.executeUpdate(strSql);
                                        System.out.println("PASO 4.1:" + strSql);
                                    }

                                    //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                    strSql = "";
                                    //Primera Quincena
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                    //Fin de mes
                                    strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                            + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 5:" + strSql);
                                    //PASO 6: Si no existe copiar los datos del empleado 
                                    if (!obtenerTra(conCos, conGru)) {
                                        System.out.println("Error paso 6");
                                        return false;
                                    }
                                    stmGru.close();
                                    stmCos.close();
                                    stmGru = null;
                                    stmCos = null;
                                    booGru = true;
                                    booCos = true;
                                    booRes = true;
                                }
                            }
                                // </editor-fold> 
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            else {
                //<editor-fold defaultstate="collapsed" desc="SOLO EN GRUPO TRANSACCIONES EN GRUPO TUVAL A CASTEK, CASTEK A TUVAL, ETC">    
                if (conGru != null) {
                    try {
                        stmGru = conGru.createStatement();
                        if ((txtNomEmp.getText().trim().equals(rdbTuv.isSelected() ? rdbTuv.getText() : rdbCas.isSelected() ? rdbCas.getText() : rdbDim.isSelected() ? rdbDim.getText() : rdbTuv.getText()) && txtEst.getText().equals("I"))) {
                            //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                            strSql = "";
                            strSql += " UPDATE tbm_traEmp ";
                            strSql += " SET st_reg='A'";
                            strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                            strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                            System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + objParSis.getCodigoEmpresa() + " trabajador inactivo: " + strSql);
                            stmGru.executeUpdate(strSql);
                            //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                            strSql = "";
                            strSql += " UPDATE tbm_traEmp \n";
                            strSql += "SET co_ofi=x.co_ofi, \n";
                            strSql += "    co_dep=x.co_dep, \n";
                            strSql += "    co_jef=x.co_jef, \n";
                            strSql += "    co_hor=x.co_hor, \n";
                            strSql += "    st_recAlm=x.st_recAlm, \n";
                            strSql += "    co_car=x.co_car, \n";
                            strSql += "    st_reg='A', \n";
                            strSql += "    fe_ing=CURRENT_TIMESTAMP, \n";
                            strSql += "    fe_ultMod=NULL, \n";
                            strSql += "    co_usrIng=" + objParSis.getCodigoUsuario() + ", \n";
                            strSql += "    co_usrMod=NULL, \n";
                            strSql += "    tx_tipModSue=null, \n";
                            strSql += "    co_pla=x.co_pla, \n";
                            strSql += "    tx_numCtaBan=x.tx_numCtaBan, \n";
                            strSql += "    tx_tipCtaBan=x.tx_tipCtaBan, \n";
                            strSql += "    st_recFonRes=x.st_recFonRes, \n";
                            strSql += "    tx_forRecFonRes=x.tx_forRecFonRes, \n";
                            strSql += "    nd_valAlm=x.nd_valAlm, \n";
                            strSql += "    nd_apoPerIES=x.nd_apoPerIES, \n";
                            strSql += "    fe_iniCon=NULL, \n";
                            strSql += "    fe_finPerPruCon=NULL, \n";
                            strSql += "    fe_finCon=NULL, \n";
                            strSql += "    st_perPruCon='N', \n";
                            strSql += "    fe_reaFinCon=NULL, \n";
                            strSql += "    tx_forRecAlm=x.tx_forRecAlm, \n";
                            strSql += "    tx_tipTra=x.tx_tipTra\n";
                            strSql += "FROM (SELECT *\n";
                            strSql += "      FROM  tbm_traemp\n";
                            strSql += "      WHERE co_emp=" + objParSis.getCodigoEmpresa();
                            strSql += "      and co_tra=" + txtCodTra.getText() + " ) AS x\n";
                            strSql += "WHERE tbm_traemp.co_tra=x.co_tra and tbm_traemp.co_emp=" + obtieneCodEmpresaDestino();
                            System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + objParSis.getCodigoEmpresa() + " trabajador inactivo y ya existia en " + objParSis.getCodigoEmpresa() + ": " + strSql);//
                            stmGru.executeUpdate(strSql);
                            //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                            //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"
                            if (!verificaIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                strSql = "";
                                strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                                strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
                                strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
                                strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 4:" + strSql);
                            }
                            if (!verificaDatGenIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                strSql = "";
                                strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                                strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
                                strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 4:" + strSql);
                            }
                            //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                            strSql = "";
                            //Primera Quincena
                            strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                    + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                    + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                            //Fin de mes
                            strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                    + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                    + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                            stmGru.executeUpdate(strSql);
                            stmGru.close();
                            stmGru = null;
                            booGru = true;
                            booRes = true;
                        } //
                        else if ((rdbCas.isSelected()) || (rdbTuv.isSelected()) || (rdbDim.isSelected()) && (!txtNomEmp.getText().trim().equals(rdbTuv.isSelected() ? rdbTuv.getText() : rdbCas.isSelected() ? rdbCas.getText() : rdbDim.isSelected() ? rdbDim.getText() : rdbTuv.getText()))) {
                            if (verificaTrabajadorEmpresas(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                strSql = "";
                                strSql += " UPDATE tbm_traEmp ";
                                strSql += " SET st_reg='I'";
                                strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                strSql += " UPDATE tbm_traEmp ";
                                strSql += " SET st_reg='A'";
                                strSql += " WHERE co_emp=" + obtieneCodEmpresaDestino();
                                strSql += " AND co_tra=" + txtCodTra.getText() + ";";
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 1:" + strSql);
                                //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                strSql = "";
                                strSql += "UPDATE tbm_traEmp \n";
                                strSql += "SET co_ofi=x.co_ofi, \n";
                                strSql += "    co_dep=x.co_dep, \n";
                                strSql += "    co_jef=x.co_jef, \n";
                                strSql += "    co_hor=x.co_hor, \n";
                                strSql += "    st_recAlm=x.st_recAlm, \n";
                                strSql += "    co_car=x.co_car, \n";
                                strSql += "    st_reg='A', \n";
                                strSql += "    fe_ing=CURRENT_TIMESTAMP, \n";
                                strSql += "    fe_ultMod=NULL, \n";
                                strSql += "    co_usrIng=" + objParSis.getCodigoUsuario() + ", \n";
                                strSql += "    co_usrMod=NULL, \n";
                                strSql += "    tx_tipModSue=null, \n";//Se cambia para que se ponga en ingreso de sueldos
                                strSql += "    co_pla=x.co_pla, \n";
                                strSql += "    tx_numCtaBan=x.tx_numCtaBan, \n";
                                strSql += "    tx_tipCtaBan=x.tx_tipCtaBan, \n";
                                strSql += "    st_recFonRes=x.st_recFonRes, \n";
                                strSql += "    tx_forRecFonRes=x.tx_forRecFonRes, \n";
                                strSql += "    nd_valAlm=x.nd_valAlm, \n";
                                strSql += "    nd_apoPerIES=x.nd_apoPerIES, \n";
                                strSql += "    fe_iniCon=NULL, \n";
                                strSql += "    fe_finPerPruCon=NULL, \n";
                                strSql += "    fe_finCon=NULL, \n";
                                strSql += "    st_perPruCon='N', \n";
                                strSql += "    fe_reaFinCon=NULL, \n";
                                strSql += "    tx_forRecAlm=x.tx_forRecAlm, \n";
                                strSql += "    tx_tipTra=x.tx_tipTra\n";
                                strSql += "FROM (SELECT *\n";
                                strSql += "      FROM  tbm_traemp\n";
                                strSql += "      WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += "      and co_tra=" + txtCodTra.getText() + " ) AS x\n";
                                strSql += "WHERE tbm_traemp.co_tra=x.co_tra and tbm_traemp.co_emp=" + obtieneCodEmpresaDestino();
                                System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + obtieneCodEmpresaDestino() + " trabajador activo y ya existia en " + obtieneCodEmpresaDestino() + ": " + strSql);//
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 2:" + strSql);
                                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                if (!verificaTrabajadorSueldos(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                    strSql = "";
                                    strSql += "INSERT INTO tbm_sueTra (co_emp, co_rub, co_tra, nd_valRub, co_cta) "
                                            + "SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, co_rub, " + txtCodTra.getText() + " AS co_tra, NULL AS nd_valRub, NULL AS co_cta "
                                            + "FROM tbm_sueTra "
                                            + "WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText()
                                            + "ORDER BY co_rub;";
                                    stmGru.executeUpdate(strSql);
                                }
                                strSql = "";
                                strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 3:" + strSql);
                                //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"

                                if (!verificaIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                                    strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
                                    strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
                                    strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 4:" + strSql);
                                }
                                if (!verificaDatGenIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                                    strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
                                    strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 4:" + strSql);
                                }
                                //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                strSql = "";
                                //Primera Quincena
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                //Fin de mes
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 5:" + strSql);
                                stmGru.close();
                                stmGru = null;
                                booGru = true;
                                booRes = true;
                            } else {
                                //PASO 1: ASIGNAR A LA EMPRESA A LA CUAL SE LO VA A CAMBIAR O ACTIVAR.
                                strSql = "";
                                strSql += " UPDATE tbm_traEmp ";
                                strSql += " SET st_reg='I'";
                                strSql += " WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += " AND co_tra=" + txtCodTra.getText() + "; ";
                                strSql += " INSERT INTO tbm_traEmp (co_emp, co_tra, fe_ing, co_usrIng)  ";
                                strSql += " VALUES(" + obtieneCodEmpresaDestino() + ", " + txtCodTra.getText() + ", CURRENT_TIMESTAMP, " + objParSis.getCodigoUsuario() + ")";
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 1:" + strSql);
                                //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                                strSql = "";
                                strSql += "UPDATE tbm_traEmp \n";
                                strSql += "SET co_ofi=x.co_ofi, \n";
                                strSql += "    co_dep=x.co_dep, \n";
                                strSql += "    co_jef=x.co_jef, \n";
                                strSql += "    co_hor=x.co_hor, \n";
                                strSql += "    st_recAlm=x.st_recAlm, \n";
                                strSql += "    co_car=x.co_car, \n";
                                strSql += "    st_reg='A', \n";
                                strSql += "    fe_ing=CURRENT_TIMESTAMP, \n";
                                strSql += "    fe_ultMod=NULL, \n";
                                strSql += "    co_usrIng=" + objParSis.getCodigoUsuario() + ", \n";
                                strSql += "    co_usrMod=NULL, \n";
                                strSql += "    tx_tipModSue=null, \n";
                                strSql += "    co_pla=x.co_pla, \n";
                                strSql += "    tx_numCtaBan=x.tx_numCtaBan, \n";
                                strSql += "    tx_tipCtaBan=x.tx_tipCtaBan, \n";
                                strSql += "    st_recFonRes=x.st_recFonRes, \n";
                                strSql += "    tx_forRecFonRes=x.tx_forRecFonRes, \n";
                                strSql += "    nd_valAlm=x.nd_valAlm, \n";
                                strSql += "    nd_apoPerIES=x.nd_apoPerIES, \n";
                                strSql += "    fe_iniCon=NULL, \n";
                                strSql += "    fe_finPerPruCon=NULL, \n";
                                strSql += "    fe_finCon=NULL, \n";
                                strSql += "    st_perPruCon='N', \n";
                                strSql += "    fe_reaFinCon=NULL, \n";
                                strSql += "    tx_forRecAlm=x.tx_forRecAlm, \n";
                                strSql += "    tx_tipTra=x.tx_tipTra\n";
                                strSql += "FROM (SELECT *\n";
                                strSql += "      FROM  tbm_traemp\n";
                                strSql += "      WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                strSql += "      and co_tra=" + txtCodTra.getText() + " ) AS x\n";
                                strSql += "WHERE tbm_traemp.co_tra=x.co_tra and tbm_traemp.co_emp=" + obtieneCodEmpresaDestino();
                                System.out.println("Caso " + objParSis.getCodigoEmpresa() + " A " + obtieneCodEmpresaDestino() + " trabajador activo y ya existia en " + obtieneCodEmpresaDestino() + ": " + strSql);//
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 2:" + strSql);
                                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                                if (!verificaTrabajadorSueldos(conGru, Integer.valueOf(txtCodTra.getText()))) {
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_sueTra (co_emp, co_rub, co_tra, nd_valRub, co_cta) "
                                            + " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, co_rub, " + txtCodTra.getText() + " AS co_tra, NULL AS nd_valRub, NULL AS co_cta "
                                            + "FROM tbm_sueTra "
                                            + "WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText()
                                            + "ORDER BY co_rub;";
                                    stmGru.executeUpdate(strSql);
                                }
                                strSql = "";
                                strSql += "DELETE FROM tbm_sueTra WHERE co_emp=" + objParSis.getCodigoEmpresa() + " AND co_tra= " + txtCodTra.getText();
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 3:" + strSql);
                                //PASO 4: COPIAR LOS RUBROS DE LA TABLA DE PAGOS. SE COPIA LOS RUBROS DE OTRO EMPLEADO. EN ESTE CASO SE COPIO  DE "19:EDDYE FRANCISCO LINO MATAMOROS"

                                if (!verificaIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                                    strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
                                    strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
                                    strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 4:" + strSql);
                                }
                                if (!verificaDatGenIngEgrMenTra(conGru, Integer.valueOf(txtCodTra.getText()), intMes, intAno)) {
                                    strSql = "";
                                    strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                                    strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
                                    strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
                                    stmGru.executeUpdate(strSql);
                                    System.out.println("PASO 4:" + strSql);
                                }

                                //PASO 5: ASIGNAR EL PORCENTAJE QUE SE VA A PAGAR AL EMPLEADO.
                                strSql = "";
                                //Primera Quincena
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=40.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=1 AND co_rub IN (33);";
                                //Fin de mes
                                strSql += " UPDATE tbm_ingegrmentra SET nd_porRubPag=60.00  WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (1, 2);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=100.00 WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (3, 4, 5, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32);\n"
                                        + " UPDATE tbm_ingegrmentra SET nd_porRubPag=0.00   WHERE co_emp=" + obtieneCodEmpresaDestino() + " AND co_tra IN (" + txtCodTra.getText() + ") AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " AND ne_per=2 AND co_rub IN (33);";
                                stmGru.executeUpdate(strSql);
                                System.out.println("PASO 5:" + strSql);
                                stmGru.close();
                                stmGru = null;
                                booGru = true;
                                booRes = true;
                            }
                        }
                    } catch (SQLException ex) {
                        if (conGru != null) {
                            try {
                                conGru.rollback();
                                conGru.close();
                                conGru = null;
                                booRes = false;
                            } catch (SQLException ex1) {
                                ex1.printStackTrace();
                                booRes = false;
                            }
                        }
                        if (conCos != null) {
                            try {
                                conCos.rollback();
                                conCos.close();
                                conCos = null;
                                booRes = false;
                            } catch (SQLException ex1) {
                                ex1.printStackTrace();
                                booRes = false;
                            }
                        }
                        ex.printStackTrace();
                        booRes = false;
                    }
                }
                // </editor-fold> 
            }
            
            if (booGru) {
                conGru.commit();
            }
            if (booCos) {
                conCos.commit();
            }
            }else{
                mostrarMsgInf("El empleado ya se encuentra activo en otra empresa.");
            }
            String strEmpDestino="";
            if (rdbTuv.isSelected()) {
                strEmpDestino=rdbTuv.getText();
            }else if (rdbCas.isSelected()) {
                strEmpDestino=rdbCas.getText();
            }else if (rdbDim.isSelected()) {
                strEmpDestino=rdbDim.getText();
            }else if (rdbCos.isSelected()) {
                strEmpDestino=rdbCos.getText();
            }
            
            enviarCorreoMasivo(strCorEle,"Traslado/Activación de Empleado"," <br> Se ha realizado un TRASLADO/ACTIVACION del empleado " +txtNomTra.getText() + " con CODIGO="+txtCodTra.getText() + " a la empresa "+strEmpDestino+"     <br>             Fecha="  + objUti.getFechaServidor(objParSis.getStringConexion(), 
                                            objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), objParSis.getQueryFechaHoraBaseDatos()) + " <br><br>                                                     Este correo es informativo, favor no responder a esta DIRECCION de correo ya que no se encuentra habilitada para recibir mensajes.\n" +
                                            "<br>Si requiere mayor INFORMACION sobre el contenido de este mensaje llame a nuestras oficinas que con mucho gusto lo atenderemos.");
            CerrarCon();
        } catch (SQLException ex) {
            if (conGru != null) {
                try {
                    conGru.rollback();
                    conGru.close();
                    conGru = null;
                    booRes = false;
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
            }
            if (conCos != null) {
                try {
                    conCos.rollback();
                    conCos.close();
                    conCos = null;
                    booRes = false;
                } catch (SQLException ex1) {
                    ex1.printStackTrace();
                }
            }
            ex.printStackTrace();
            booRes = false;
        }
        return booRes;
    }

    private boolean verificaTrabajadorEmpresas(Connection con, int intCodTra) {
        boolean booRes = false;
        try {
            Statement stm;
            ResultSet rst;
            String strSql = "";
            stm = con.createStatement();
            strSql = " SELECT * FROM tbm_traEmp";
            strSql += " WHERE co_tra= " + intCodTra;
            strSql += " and co_emp= " + obtieneCodEmpresaDestino();
            rst = stm.executeQuery(strSql);
            while (rst.next()) {
                booRes = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return booRes;
    }

    private int obtieneCodEmpresaDestino() {
        int intCodEmp = 0;
        if (rdbTuv.isSelected() || rdbCos.isSelected()) {
            intCodEmp = 1;
        } else if (rdbCas.isSelected()) {
            intCodEmp = 2;
        } else if (rdbDim.isSelected()) {
            intCodEmp = 4;
        }
        return intCodEmp;
    }
    /**
     * Se busca en la empresa destino..
     * @param con
     * @param intCodTra
     * @return 
     */
    private boolean verificaTrabajadorSueldos(Connection conDes, int intCodTra) {
        boolean booRes = false;
        try {
            Statement stm;
            ResultSet rst;
            String strSql = "";
            stm = conDes.createStatement();
            strSql = " SELECT * FROM tbm_sueTra";
            strSql += " WHERE co_tra= " + intCodTra;
            strSql += " and co_emp= " + obtieneCodEmpresaDestino();
            rst = stm.executeQuery(strSql);
            while (rst.next()) {
                booRes = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return booRes;
    }
    /**
     * Verifica si los rubros ya se encuentran ingresados en la empresa destino.
     * @param con
     * @param intCodTra
     * @param intMes
     * @param intAni
     * @return 
     */
    private boolean verificaIngEgrMenTra(Connection conDes, int intCodTra, int intMes, int intAni) {
        boolean booRes = false;
        try {
            Statement stm;
            ResultSet rst;
            String strSql = "";
            stm = conDes.createStatement();
            strSql = " select * from tbm_ingegrmentra \n";
            strSql += " WHERE co_tra= " + intCodTra;
            strSql += " and co_emp= " + obtieneCodEmpresaDestino();
            strSql += " and ne_mes= " + intMes;
            strSql += " and ne_ani= " + intAni;
            rst = stm.executeQuery(strSql);
            if (rst.next()) {
                booRes = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return booRes;
    }

    private boolean verificaDatGenIngEgrMenTra(Connection conDes, int intCodTra, int intMes, int intAni) {
        boolean booRes = false;
        try {
            Statement stm;
            ResultSet rst;
            String strSql = "";
            stm = conDes.createStatement();
            strSql = " select * from tbm_datgeningegrmentra \n";
            strSql += " WHERE co_tra= " + intCodTra;
            strSql += " and co_emp= " + obtieneCodEmpresaDestino();
            strSql += " and ne_mes= " + intMes;
            strSql += " and ne_ani= " + intAni;
            rst = stm.executeQuery(strSql);
            if (rst.next()) {
                booRes = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return booRes;
    }
    /**
     * Obtiene los datos de tbm_traemp para pasarlos a otra empresa..
     * @param conOri
     * @param conDes
     * @return 
     */
    private boolean obtenerTraEmpOtraEmpresa(Connection conOri, Connection conDes) {
        boolean blnRes = false;
        Statement stmOri, stmDes;
        ResultSet rst;
        String strSql;
        try {
            stmOri = conOri.createStatement();
            stmDes = conDes.createStatement();
            strSql = " ";
            strSql += "  SELECT *";
            strSql += "  FROM  tbm_traemp \n";
            strSql += "  WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSql += "  and co_tra=" + txtCodTra.getText();
            rst = stmOri.executeQuery(strSql);
            if (rst.next()) {
                //PASO 2: AGREGAR LOS DATOS QUE NO SE COPIARON AL ASIGNARLO A OTRA EMPRESA.
                strSql = "";
                strSql += " UPDATE tbm_traEmp \n";
                strSql += " SET co_ofi=" + rst.getString("co_ofi");
                strSql += "    ,co_dep=" + rst.getString("co_dep");
                strSql += "    ,co_jef=" + rst.getString("co_jef");
                strSql += "    ,co_hor= " + rst.getString("co_hor");
                strSql += "    ,st_recAlm=" + objUti.codificar(rst.getString("st_recAlm"));
                strSql += "    ,co_car=" + rst.getString("co_car");
                strSql += "    ,st_reg='A' \n";
                strSql += "    ,fe_ing=CURRENT_TIMESTAMP \n";
                strSql += "    ,fe_ultMod=NULL \n";
                strSql += "    ,co_usrIng=" + objParSis.getCodigoUsuario();
                strSql += "    ,co_usrMod=NULL \n";
                strSql += "    ,tx_tipModSue=null";// + objUti.codificar(rst.getString("tx_tipModSue"));
                strSql += "    ,co_pla=" + rst.getString("co_pla");
                strSql += "    ,tx_numCtaBan=" + objUti.codificar(rst.getString("tx_numCtaBan"));
                strSql += "    ,tx_tipCtaBan=" + objUti.codificar(rst.getString("tx_tipCtaBan"));
                strSql += "    ,st_recFonRes=" + objUti.codificar(rst.getString("st_recFonRes"));
                strSql += "    ,tx_forRecFonRes=" + objUti.codificar(rst.getString("tx_forRecFonRes"));
                strSql += "    ,nd_valAlm=" + rst.getString("nd_valAlm");
                strSql += "    ,nd_apoPerIES=" + rst.getString("nd_apoPerIES");
                strSql += "    ,fe_iniCon=NULL \n";
                strSql += "    ,fe_finPerPruCon=NULL \n";
                strSql += "    ,fe_finCon=NULL \n";
                strSql += "    ,st_perPruCon='N' \n";
                strSql += "    ,fe_reaFinCon=NULL \n";
                strSql += "    ,tx_forRecAlm=" + objUti.codificar(rst.getString("tx_forRecAlm"));
                strSql += "    ,tx_tipTra=" + objUti.codificar(rst.getString("tx_tipTra"));
                strSql += " WHERE co_tra= " + txtCodTra.getText();
                strSql += " and co_emp=" + obtieneCodEmpresaDestino();
                stmDes.executeUpdate(strSql);
                System.out.println("PASO 2:" + strSql);
                blnRes = true;
            }
            rst.close();
            rst = null;
            stmOri.close();
            stmOri = null;
            stmDes.close();
            stmDes = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Este query sirve para obtener los sueldos de la empresa origen y va a la empresa destino..
     * @param conOri
     * @param conDes
     * @return 
     */
    private boolean obtenerSueTraOtraEmpresa(Connection conOri, Connection conDes) {
        boolean blnRes = false;
        Statement stmOri, stmDes;
        ResultSet rst;
        String strSql;
        try {
            stmOri = conOri.createStatement();
            stmDes = conDes.createStatement();
            strSql = " ";
            strSql += "  SELECT *";
            strSql += "  FROM  tbm_sueTra \n";
            strSql += "  WHERE co_emp=" + objParSis.getCodigoEmpresa();
            strSql += "  and co_tra=" + txtCodTra.getText();
            rst = stmOri.executeQuery(strSql);
            strSql = "";
            while (rst.next()) {
                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                strSql += " INSERT INTO tbm_sueTra (co_emp, co_rub, co_tra, nd_valRub, co_cta) ";
                strSql += " VALUES (" + obtieneCodEmpresaDestino() + ", " + rst.getString("co_rub") + ", " + txtCodTra.getText() + ", null, null ); ";
                blnRes = true;
            }
            stmDes.executeUpdate(strSql);
            rst.close();
            rst = null;
            stmOri.close();
            stmOri = null;
            stmDes.close();
            stmDes = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean obtenerIngEgrMenTraOtraEmpresa(Connection conOri, Connection conDes, int intAno, int intMes) {
        boolean blnRes = false;
        Statement stmOri, stmDes;
        ResultSet rst;
        String strSql;
        try {
            stmOri = conOri.createStatement();
            stmDes = conDes.createStatement();
            strSql = " ";
            strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText();
            strSql += " AS co_tra, ne_ani, ne_mes, ne_per, co_rub, NULL AS nd_valrub, ";
            strSql += " NULL AS nd_porrubpag, st_rolpaggen, nd_valrubpag, NULL AS nd_valrubsindes, nd_valrubmesant, nd_valrubmessig\n";
            strSql += " FROM tbm_ingEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
            rst = stmOri.executeQuery(strSql);
            strSql = "";
            while (rst.next()) {
                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                strSql += " INSERT INTO tbm_ingegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, co_rub, nd_valrub, nd_porrubpag, st_rolpaggen, nd_valrubpag, nd_valrubsindes, nd_valrubmesant, nd_valrubmessig)\n";
                strSql += " VALUES (" + obtieneCodEmpresaDestino() + ", " + txtCodTra.getText() + ", " + intAno + ", " + intMes;
                strSql += " ," + rst.getString("ne_per");
                strSql += " ," + rst.getString("co_rub");
                strSql += " ," + rst.getString("nd_valrub");
                strSql += " ," + rst.getString("nd_porrubpag");
                strSql += " ," + objUti.codificar(rst.getString("st_rolpaggen"));
                strSql += " ," + rst.getString("nd_valrubpag");
                strSql += " ," + rst.getString("nd_valrubsindes");
                strSql += " ," + rst.getString("nd_valrubmesant");
                strSql += " ," + rst.getString("nd_valrubmessig");
                strSql += " ); ";
            }
            stmDes.executeUpdate(strSql);
            blnRes = true;
            rst.close();
            rst = null;
            stmOri.close();
            stmOri = null;
            stmDes.close();
            stmDes = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    private boolean obtenerDatGenIngEgrMenTraOtraEmpresa(Connection conOri, Connection conDes, int intAno, int intMes) {
        boolean blnRes = false;
        Statement stmOri, stmDes;
        ResultSet rst;
        String strSql;
        try {
            stmOri = conOri.createStatement();
            stmDes = conDes.createStatement();
            strSql = " ";
            strSql += " SELECT " + obtieneCodEmpresaDestino() + " AS co_emp, " + txtCodTra.getText() + " AS co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon";
            strSql += " FROM tbm_datGenIngEgrMenTra WHERE co_tra=19 AND ne_ani=" + intAno + " AND ne_mes=" + intMes + " ORDER BY co_emp, co_tra, ne_ani, ne_mes, ne_per;";//Se quema el codigo de 19=EDDYE FRANCISCO LINO MATAMOROS
            rst = stmOri.executeQuery(strSql);
            strSql = "";
            while (rst.next()) {
                //PASO 3: COPIAR LOS SUELDOS. OJO: COMO YA EXISTIA ANTES LOS SUELDOS YA SE ENCUENTRAN INGRESADOS Y NO HABRIA QUE REALIZAR ESTE PASO
                strSql += " INSERT INTO tbm_datgeningegrmentra(co_emp, co_tra, ne_ani, ne_mes, ne_per, tx_obs1, st_rolpaggen, ne_numdialab, ne_numfal, ne_numfalrev, nd_comvenies, nd_comvenbon)\n";
                strSql += " VALUES (" + obtieneCodEmpresaDestino() + ", " + txtCodTra.getText() + ", " + intAno + ", " + intMes;
                strSql += " ," + rst.getString("ne_per");
                strSql += " ," + objUti.codificar(rst.getString("tx_obs1"));
                strSql += " ," + objUti.codificar(rst.getString("st_rolpaggen"));
                strSql += " ," + rst.getString("ne_numdialab");
                strSql += " ," + rst.getString("ne_numfal");
                strSql += " ," + rst.getString("ne_numfalrev");
                strSql += " ," + rst.getString("nd_comvenies");
                strSql += " ," + rst.getString("nd_comvenbon");
                strSql += " ); ";
            }
            stmDes.executeUpdate(strSql);
            blnRes = true;
            rst.close();
            rst = null;
            stmOri.close();
            stmOri = null;
            stmDes.close();
            stmDes = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    /**
     * Este query sirve para obtener los datos del trabajador como el nombre apellidos etc si es que el empleado nunca ha estado antes en la empresa
     * a la cual va a ser cambiado. Casos Cosenco a Grupo o Grupo a Cosenco..
     * @param conOri
     * @param conDes
     * @return 
     */
    private boolean obtenerTra(Connection conOri, Connection conDes) {
        boolean blnRes = false;
        Statement stmOri, stmDes;
        ResultSet rst;
        String strSql;
        try {
            stmOri = conOri.createStatement();
            stmDes = conDes.createStatement();
            strSql = " ";
            strSql += "  SELECT *";
            strSql += "  FROM  tbm_Tra \n";
            strSql += "  WHERE co_tra=" + txtCodTra.getText();
            rst = stmOri.executeQuery(strSql);
            
            while (rst.next()) {
                //PASO 6: COPIAR DATOS TRABAJADOR SI ES QUE NO EXISTE
                strSql =  "";
                strSql += " UPDATE tbm_tra";
                strSql += "   set tx_ide= " + objUti.codificar(rst.getString("tx_ide"));
                strSql += "       ,tx_nom="+ objUti.codificar(rst.getString("tx_nom"));
                strSql += "       ,tx_ape= "+ objUti.codificar(rst.getString("tx_ape"));
                strSql += "       ,tx_dir= "+ objUti.codificar(rst.getString("tx_dir"));
                strSql += "       ,tx_refdir= "+ objUti.codificar(rst.getString("tx_refdir"));
                strSql += "       ,tx_tel1="+ objUti.codificar(rst.getString("tx_tel1"));
                strSql += "       ,tx_tel2=" + objUti.codificar(rst.getString("tx_tel2"));
                strSql += "       ,tx_corele="+ objUti.codificar(rst.getString("tx_corele"));
                strSql += "       ,tx_sex="+ objUti.codificar(rst.getString("tx_sex"));
                strSql += "       ,fe_nac="+ objUti.codificar(rst.getString("fe_nac"));
                strSql += "       ,co_ciunac="+ objUti.codificar(rst.getString("co_ciunac"));
                strSql += "       ,co_estciv=" + objUti.codificar(rst.getString("co_estciv"));
                strSql += "       ,ne_numhij=" + objUti.codificar(rst.getString("ne_numhij"));
                strSql += "       ,st_reg='A'" ;
                strSql += "       ,tx_obs1=NULL " ;
                strSql += "       ,tx_obs2=NULL" ;
                strSql += "       ,fe_ing=CURRENT_TIMESTAMP " ;
                strSql += "       ,fe_ultMod=NULL ";
                strSql += "       ,co_usrIng=1 " ;
                strSql += "       ,co_usrMod=NULL" ;
                strSql += "   where co_tra= " + txtCodTra.getText();
                blnRes = true;
            }
            stmDes.executeUpdate(strSql);
            rst.close();
            rst = null;
            stmOri.close();
            stmOri = null;
            stmDes.close();
            stmDes = null;
        } catch (java.sql.SQLException e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            blnRes = false;
            e.printStackTrace();
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    private boolean verificaYaActivadoOtraEmpresa(Connection conGru,Connection conCos, int intCodTra) {
        boolean booRes = false;
        try {
            Statement stmGru1,stmCos1;
            ResultSet rstGru,rstCos;
            String strSql = "";
            stmGru1 = conGru.createStatement();
            stmCos1 = conCos.createStatement();
            if (txtNomEmp.getText().contains("COSENCO")) {
                strSql = "  select * \n";
                strSql +="  from tbm_traemp\n";
                strSql += " WHERE co_tra= " + intCodTra;
                //strSql += " and co_emp= " + objParSis.getCodigoEmpresa();
                strSql += " and st_reg='A' ";
                rstGru = stmGru1.executeQuery(strSql);
                if (rstGru.next()) {
                    return true;
                }
            }else if(rdbCos.isSelected()){
                strSql = "  select * \n";
                strSql +="  from tbm_traemp\n";
                strSql += " WHERE co_tra= " + intCodTra;
                strSql += " and co_emp not in( " + objParSis.getCodigoEmpresa() +")";
                strSql += " and st_reg='A' ";
                rstGru = stmGru1.executeQuery(strSql);
                if (rstGru.next()) {
                    return true;
                }
                strSql = "  select * \n";
                strSql +="  from tbm_traemp\n";
                strSql += " WHERE co_tra= " + intCodTra;
                //strSql += " and co_emp= " + objParSis.getCodigoEmpresa();
                strSql += " and st_reg='A' ";
                rstCos = stmCos1.executeQuery(strSql);
                if (rstCos.next()) {
                    return true;
                }
            }else{
                strSql = "  select * \n";
                strSql +="  from tbm_traemp\n";
                strSql += " WHERE co_tra= " + intCodTra;
                strSql += " and co_emp not in ( " + objParSis.getCodigoEmpresa() + ")";
                strSql += " and st_reg='A' ";
                rstGru = stmGru1.executeQuery(strSql);
                if (rstGru.next()) {
                    return true;
                }
                strSql = "  select * \n";
                strSql +="  from tbm_traemp\n";
                strSql += " WHERE co_tra= " + intCodTra;
                //strSql += " and co_emp= " + objParSis.getCodigoEmpresa();
                strSql += " and st_reg='A' ";
                rstCos = stmCos1.executeQuery(strSql);
                if (rstCos.next()) {
                    return true;
                }
            }
            
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return booRes;
    }
    /**
    * TSANGINEZ - ENVIAR CORREOS A VARIAS DIRECCIONES AL MISMO TIEMPO.
    * @param strCorEleTo - Cuentas de correo a las que se enviara el email; estas deben estar separadas por ";". Ej: cuentaFicticia@tuvalsa.com; cuentaFicticia2@tuvalsa.com
    * @param subject - Asunto
    * @param strMsj - Cuerpo del mensaje de correo. 
    */
    public void enviarCorreoMasivo(String strCorEleTo, String subject, String strMsj ){
        try {

            String server = "mail.tuvalsa.com";
            String userName = "zafiro@tuvalsa.com";
            String password = "#tuv*sis28/10=";
            String fromAddres = "zafiro@tuvalsa.com";
            String toAddres = strCorEleTo;

            String cc = "";
            String bcc = "";
            boolean htmlFormat = false;
            String body = strMsj;

            sendMailTuvMas(server, userName, password, fromAddres, toAddres, cc, bcc, htmlFormat, subject, body);

        }catch (Exception e) {  
            e.printStackTrace();    
        } 
    }
    public boolean sendMailTuvMas(String server, String userName, String password, String fromAddress, String toAddress, String ccAddress, String bccAddress, boolean htmlFormat, String subject, String body) {
        boolean blnRes=false;
        try{

           Properties props = System.getProperties();
//           props.put("mail.smtp.auth", "true");
//           props.put("mail.smtp.ssl.trust", server);
//           props.put("mail.smtp.port", "587");
//           props.put("mail.smtp.starttls.enable", "true");
           //System.out.println("---2---");
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.ssl.trust", server);
           props.put("mail.smtp.host", server);
           props.put("mail.smtp.user", userName);
           props.put("mail.smtp.password", password);
           props.put("mail.smtp.port", "587");
           props.put("mail.smtp.auth", "true");  

            Authenticator auth = new ZafRecHum90.MyAuthenticator();

            // Get session
            Session session = Session.getDefaultInstance(props, auth);
            session.setDebug(true);

            // Define message
            MimeMessage message = new MimeMessage(session);

            // Set the from address
            message.setFrom(new InternetAddress(fromAddress));

            // Set the to addresses
            StringTokenizer tokens = new StringTokenizer(toAddress, ";");
            while (tokens.hasMoreTokens())
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(tokens.nextToken()));
            tokens=null;
            
            if (! ccAddress.equals("")){
                tokens = new StringTokenizer(ccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.CC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }
            
            if (! bccAddress.equals("")){
                tokens = new StringTokenizer(bccAddress, ";");
                while (tokens.hasMoreTokens())
                    message.addRecipient(Message.RecipientType.BCC, new InternetAddress(tokens.nextToken()));
                tokens=null;
            }

            // Set the subject
            message.setSubject(subject);

            MimeBodyPart  cuerpoCorreo = new MimeBodyPart();
            cuerpoCorreo.setContent(body, "text/html");

            MimeMultipart multipart= new MimeMultipart();
            multipart.addBodyPart(cuerpoCorreo);

            message.setContent(multipart);

            message.saveChanges();

            Transport.send(message);

            blnRes=true;

        }catch(MessagingException e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }catch(Exception e){ 
            blnRes=false;  
            e.printStackTrace(); 
        }
        return blnRes;
    }
    static class MyAuthenticator extends Authenticator {
        PasswordAuthentication l = new PasswordAuthentication("zafiro@tuvalsa.com", "#tuv*sis28/10=");
        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            return l;
        }
    }
}