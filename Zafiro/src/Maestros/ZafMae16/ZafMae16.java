/*
 * ZafMae29.java
 * Tipos de documentos por cabecera del programa
 * 
 */
package Maestros.ZafMae16;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafToolBar.ZafToolBar;
import java.sql.Connection;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafColNumerada.ZafColNumerada;
import java.sql.ResultSet;
import java.sql.DriverManager;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import java.util.ArrayList;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;

/**
 * 
 * @author 
 */
public class ZafMae16 extends javax.swing.JInternalFrame {
//Declaración de variables globales para el JTable  

    final int INT_TBL_DAT_LIN=0;               //LINEA DE NUMEROS DE REGISTROS EN LA TABLA
    final int INT_TBL_DAT_CHK=1;               //LINEA PARA CASILLA DE SELECCION DE REGISTROS
    final int INT_TBL_DAT_COD_TIP_DOC=2;       //CODIGO DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_DES_COR=3;           //DESCRIPCION CORTA DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_DES_LAR=4;           //DESCRIPCION LARGA DEL TIPO DE DOCUMENTO
    final int INT_TBL_DAT_CHK_PRE=5;   //CASILLA DE SELECCION DEL DOCUMENTO PREDETERMINADO
    final int INT_TBL_DAT_CHK_AUX=6;

    private ZafTblOrd objTblOrd;
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnHayCam;                               //Determina si hay cambios en el formulario.
    private ZafColNumerada objColNum;
    //Formatear columnas en JTable.
    private ZafMouMotAda objMouMotAda;                       //ToolTipText en TableHeader.
    private Vector vecAux;
    private MiToolBar objTooBar;
    private java.sql.Connection conCab, con;
    private java.sql.Statement stmCab, stm;
    private java.sql.ResultSet rstCab, rst;
    private ZafTblMod objTblMod;
    private ZafTblFilCab objTblFilCab;
    private ZafTblBus objTblBus;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafTblCelRenChk objTblCelRenChk, objTblCelRenChkAux, objTblCelRenChkPre;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk, objTblCelEdiChkAux, objTblCelEdiChkPre;            //Editor: JCheckBox en celda.
    //Ventana de Consulta para el Programa
    private ZafVenCon vcoPrg;                           //Ventana de consulta "Tipo de documento".
    private String strCodPrg, strNomPrg;

    private boolean blnPre;
    private ZafDocLis objDocLis;

    /* Crea una nueva instancia de la clase ZafCon01. */
    public ZafMae16(ZafParSis obj) {
        try {
            initComponents();
            //Inicializar objetos.
            objParSis = (ZafParSis) obj.clone();
        } catch (CloneNotSupportedException e) {
            this.setTitle(this.getTitle() + " [ERROR]");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bgrTipCta = new javax.swing.ButtonGroup();
        bgrNatCta = new javax.swing.ButtonGroup();
        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        panDat = new javax.swing.JPanel();
        panCabDoc = new javax.swing.JPanel();
        lblTipDoc = new javax.swing.JLabel();
        txtCodPrg = new javax.swing.JTextField();
        txtNomPrg = new javax.swing.JTextField();
        butPrg = new javax.swing.JButton();
        panCenDat = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Título de la ventana");
        setPreferredSize(new java.awt.Dimension(465, 518));
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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Información del registro actual");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        tabFrm.setPreferredSize(new java.awt.Dimension(459, 473));

        panDat.setPreferredSize(new java.awt.Dimension(600, 80));
        panDat.setLayout(new java.awt.BorderLayout());

        panCabDoc.setPreferredSize(new java.awt.Dimension(610, 30));
        panCabDoc.setLayout(null);

        lblTipDoc.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        lblTipDoc.setText("Programa :");
        panCabDoc.add(lblTipDoc);
        lblTipDoc.setBounds(4, 4, 60, 20);

        txtCodPrg.setMaximumSize(null);
        txtCodPrg.setPreferredSize(new java.awt.Dimension(70, 20));
        txtCodPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodPrgActionPerformed(evt);
            }
        });
        txtCodPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodPrgFocusLost(evt);
            }
        });
        panCabDoc.add(txtCodPrg);
        txtCodPrg.setBounds(64, 4, 70, 20);

        txtNomPrg.setMaximumSize(null);
        txtNomPrg.setPreferredSize(new java.awt.Dimension(70, 20));
        txtNomPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomPrgActionPerformed(evt);
            }
        });
        txtNomPrg.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNomPrgFocusLost(evt);
            }
        });
        panCabDoc.add(txtNomPrg);
        txtNomPrg.setBounds(134, 4, 510, 20);

        butPrg.setFont(new java.awt.Font("SansSerif", 1, 12));
        butPrg.setText("...");
        butPrg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrgActionPerformed(evt);
            }
        });
        panCabDoc.add(butPrg);
        butPrg.setBounds(644, 4, 20, 20);

        panDat.add(panCabDoc, java.awt.BorderLayout.NORTH);

        panCenDat.setAutoscrolls(true);
        panCenDat.setMinimumSize(new java.awt.Dimension(24, 24));
        panCenDat.setPreferredSize(new java.awt.Dimension(454, 404));
        panCenDat.setLayout(new java.awt.BorderLayout());

        spnDat.setBorder(null);
        spnDat.setPreferredSize(new java.awt.Dimension(454, 404));

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

        panCenDat.add(spnDat, java.awt.BorderLayout.CENTER);

        panDat.add(panCenDat, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", panDat);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());
        panFrm.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Esta funcion permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de bï¿½squeda determina si se debe hacer
     * una busqueda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se estï¿½ buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opciï¿½n que desea utilizar.
     * @param intTipBus El tipo de busqueda a realizar.
     * @return true: Si no se presenta ningun problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrg(int intTipBus) {
        boolean blnRes = true;
        try {
            switch (intTipBus) {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrg.setCampoBusqueda(1);
                    vcoPrg.show();
                    if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    }
                    break;
                case 1: //Búsqueda directa por "Código".
                    if (vcoPrg.buscar("a1.co_mnu", txtCodPrg.getText())) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    } else {
                        vcoPrg.setCampoBusqueda(0);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                        } else {
                            txtCodPrg.setText(strCodPrg);
                        }
                    }

                    break;
                case 2: //Búsqueda directa por "Nombre".
                    if (vcoPrg.buscar("a1.tx_nom", txtNomPrg.getText())) {
                        txtCodPrg.setText(vcoPrg.getValueAt(1));
                        txtNomPrg.setText(vcoPrg.getValueAt(2));
                    } else {
                        vcoPrg.setCampoBusqueda(1);
                        vcoPrg.setCriterio1(11);
                        vcoPrg.cargarDatos();
                        vcoPrg.show();
                        if (vcoPrg.getSelectedButton() == vcoPrg.INT_BUT_ACE) {
                            txtCodPrg.setText(vcoPrg.getValueAt(1));
                            txtNomPrg.setText(vcoPrg.getValueAt(2));
                        } else {
                            txtNomPrg.setText(strNomPrg);
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

    /** Cerrar la aplicación. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try {
            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
            strTit = "Mensaje del sistema Zafiro";
            strMsg = "ï¿½Estï¿½ seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE) == javax.swing.JOptionPane.YES_OPTION) {
                //Cerrar la conexión si está abierta.
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                dispose();
            }
        } catch (java.sql.SQLException e) {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void butPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrgActionPerformed
    if (objTooBar.getEstado() == 'n') {
        strCodPrg = txtCodPrg.getText();
        mostrarVenConPrg(0);
        if (!txtCodPrg.getText().equals("")) {
            //Cargar los tipos de documentos sólo si ha cambiado el programa.
            if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
                cargarDetReg();
            }
        }
    } else {
        strCodPrg = txtCodPrg.getText();
        mostrarVenConPrg(0);
        if (!txtCodPrg.getText().equals("")) {
        }
    }
}//GEN-LAST:event_butPrgActionPerformed

private void txtNomPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusLost
    if (!txtNomPrg.getText().equalsIgnoreCase(strNomPrg)) {
        if (txtNomPrg.getText().equals("")) {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrg(2);
            if (txtCodPrg.getText().equals("")) {
                objTblMod.removeAllRows();
            }
        }
    } else {
        txtNomPrg.setText(strNomPrg);
    }
}//GEN-LAST:event_txtNomPrgFocusLost

private void txtNomPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNomPrgFocusGained
    strNomPrg = txtNomPrg.getText();
    txtNomPrg.selectAll();
}//GEN-LAST:event_txtNomPrgFocusGained

private void txtNomPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomPrgActionPerformed
    txtNomPrg.transferFocus();
}//GEN-LAST:event_txtNomPrgActionPerformed

private void txtCodPrgFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusLost
    if (!txtCodPrg.getText().equalsIgnoreCase(strCodPrg)) {
        if (txtCodPrg.getText().equals("")) {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            objTblMod.removeAllRows();
        } else {
            mostrarVenConPrg(1);
            if (txtCodPrg.getText().equals("")) {
                objTblMod.removeAllRows();
            }
        }
    } else {
        txtCodPrg.setText(strCodPrg);
    }
}//GEN-LAST:event_txtCodPrgFocusLost

private void txtCodPrgFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrgFocusGained
    strCodPrg = txtCodPrg.getText();
    txtCodPrg.selectAll();
}//GEN-LAST:event_txtCodPrgFocusGained

private void txtCodPrgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrgActionPerformed
    txtCodPrg.transferFocus();
}//GEN-LAST:event_txtCodPrgActionPerformed

private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
    configurarFrm();
    configurarTblDat();
    agregarDocLis();
}//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicación. */
    private void exitForm() {
        dispose();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup bgrNatCta;
    private javax.swing.ButtonGroup bgrTipCta;
    private javax.swing.JButton butPrg;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panCabDoc;
    private javax.swing.JPanel panCenDat;
    private javax.swing.JPanel panDat;
    private javax.swing.JPanel panFrm;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodPrg;
    private javax.swing.JTextField txtNomPrg;
    // End of variables declaration//GEN-END:variables

    /**
     * Esta función permite consultar los registros de acuerdo al criterio seleccionado.
     * @return true: Si se pudo consultar los registros.
     * <BR>false: En el caso contrario.
     */
    private boolean consultarReg() {
        int intCodEmp, intCodLoc;
        boolean blnRes=true;
        strAux="";
        try{
            intCodEmp=objParSis.getCodigoEmpresa();
            intCodLoc=objParSis.getCodigoLocal();
            conCab=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conCab!=null) {
                stmCab=conCab.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                if( ! txtCodPrg.getText().equals(""))
                    strAux+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";

                if (objParSis.getCodigoMenu()==358) {
                    strSQL= "";
                    strSQL+="SELECT a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom";
                    strSQL+=" FROM tbm_mnuSis AS a1 INNER JOIN tbr_tipDocPrg AS a2 ON (a1.co_mnu=a2.co_mnu) ";
                    strSQL+=" WHERE a2.co_emp=" + intCodEmp;
                    strSQL+=" AND a2.co_loc=" + intCodLoc;
                    strSQL+=strAux;
                    strSQL+=" GROUP BY a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom";
                    strSQL+=" ORDER by a1.co_mnu";
                }
                if (objParSis.getCodigoMenu() == 1804) {
                    strSQL = "";
                    strSQL += "SELECT a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom";
                    strSQL += " FROM tbm_mnuSis AS a1 INNER JOIN tbr_tipDocDetPrg AS a2 ON (a1.co_mnu=a2.co_mnu) ";
                    strSQL += " WHERE a2.co_emp=" + intCodEmp;
                    strSQL += " AND a2.co_loc=" + intCodLoc;
                    strSQL+=strAux;
                    strSQL += " GROUP BY a2.co_emp, a2.co_loc, a1.co_mnu, a1.tx_nom";
                    strSQL += " Order by a1.co_mnu ";
                }
                rstCab = stmCab.executeQuery(strSQL);
                if (rstCab.next()) {
                    rstCab.last();
                    objTooBar.setMenSis("Se encontraron " + rstCab.getRow() + " Registros");
                    rstCab.first();
                    cargarReg();
                } else {
                    mostrarMsgInf("No se ha encontrado ningún registro que cumpla el criterio de búsqueda especificado.");
                    limpiarFrm();
                    objTooBar.setEstado('l');
                    objTooBar.setMenSis("Listo");
                }
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
     * Esta función inserta el registro en la base de datos.
     * @return true: Si se pudo insertar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarReg() {
        boolean blnRes = false;
        try {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if(con!=null){
                if(insertarDet()){
                    con.commit();
                    blnRes=true;
                }
                else{
                    con.rollback();
                }
                con.close();
                con = null;
            }
        }
        catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite insertar el detalle de un registro.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet() {
        boolean blnRes=true;
        String strLin="";
        String strUpd="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                    strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                    if(strLin.equals("M")){
                        if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK)) &&  (objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUX)) ){//se lo esta modificando
                            if(objParSis.getCodigoMenu()==358){//tbr_tipdocprg
                                strSQL="";
                                strSQL+=" UPDATE tbr_tipdocprg";
                                strSQL+=" SET st_reg=x.st_reg FROM(";
                                strSQL+="       SELECT co_emp, co_loc, co_tipDoc, co_mnu";
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE))
                                    strSQL+="       , 'S' AS st_reg";
                                else
                                    strSQL+="       , 'N' AS st_reg";
                                strSQL+="           FROM tbr_tipdocprg";
                                strSQL+="           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="           AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="           AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="           AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+=")  AS x";
                                strSQL+=" WHERE tbr_tipdocprg.co_emp=x.co_emp";
                                strSQL+=" AND tbr_tipdocprg.co_loc=x.co_loc";
                                strSQL+=" AND tbr_tipdocprg.co_tipDoc=x.co_tipDoc";
                                strSQL+=" AND tbr_tipdocprg.co_mnu=x.co_mnu";
                                strSQL+=" AND EXISTS( ";
                                strSQL+="               SELECT co_emp, co_loc, co_tipDoc, co_mnu FROM tbr_tipdocprg";
                                strSQL+="               WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="               AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="               AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="               AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+=");";
                                strUpd+=strSQL;
                            }
                            else if(objParSis.getCodigoMenu()==1804){//tbr_tipdocDetprg
                                strSQL="";
                                strSQL+=" UPDATE tbr_tipdocDetprg";
                                strSQL+=" SET st_reg=x.st_reg FROM(";
                                strSQL+="       SELECT co_emp, co_loc, co_tipDoc, co_mnu";
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE))
                                    strSQL+="       , 'S' AS st_reg";
                                else
                                    strSQL+="       , 'N' AS st_reg";
                                strSQL+="           FROM tbr_tipdocDetprg";
                                strSQL+="           WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="           AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="           AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="           AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+=")  AS x";
                                strSQL+=" WHERE tbr_tipdocDetprg.co_emp=x.co_emp";
                                strSQL+=" AND tbr_tipdocDetprg.co_loc=x.co_loc";
                                strSQL+=" AND tbr_tipdocDetprg.co_tipDoc=x.co_tipDoc";
                                strSQL+=" AND tbr_tipdocDetprg.co_mnu=x.co_mnu";
                                strSQL+=" AND EXISTS( ";
                                strSQL+="               SELECT co_emp, co_loc, co_tipDoc, co_mnu FROM tbr_tipdocDetprg";
                                strSQL+="               WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="               AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="               AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="               AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+=");";
                                strUpd+=strSQL;
                            }

                        }

                        if( (objTblMod.isChecked(i, INT_TBL_DAT_CHK)) &&  ( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK_AUX)) ){//se lo esta seleccionando por primera vez, por tanto es INSERCION
                            if(objParSis.getCodigoMenu()==358){//tbr_tipdocprg
                                strSQL="";
                                strSQL+=" INSERT INTO tbr_tipdocprg(";
                                strSQL+=" co_emp, co_loc, co_tipdoc, co_mnu, st_reg)";
                                strSQL+=" (";
                                strSQL+="   SELECT *FROM(";
                                strSQL+="               SELECT CAST(" + objParSis.getCodigoEmpresa() + "AS smallint) AS co_emp,";
                                strSQL+="                      CAST(" + objParSis.getCodigoLocal() + " AS smallint) AS co_loc,";
                                strSQL+="                      CAST(" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + " AS smallint) AS co_tipDoc,";//co_tipdoc
                                strSQL+="                      CAST(" + txtCodPrg.getText() + " AS smallint) AS co_mnu, ";//co_mnu
                                strSQL+="                      CAST(";
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE))
                                    strSQL+="'S'";//st_reg
                                else
                                    strSQL+="'N'";//st_reg
                                strSQL+="                           AS character(1)) AS st_reg";

                                strSQL+="               FROM tbr_tipdocprg";
                                strSQL+="               WHERE";
                                strSQL+="               NOT EXISTS(";
                                strSQL+="                       SELECT co_emp, co_loc, co_tipdoc, co_mnu";
                                strSQL+="                       FROM tbr_tipdocprg";
                                strSQL+="                       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="                       AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="                       AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="                       AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+="               )";
                                strSQL+=") AS x";
                                strSQL+=" GROUP BY co_emp, co_loc, co_tipDoc, co_mnu, st_reg";
                                strSQL+=");";
                                strUpd+=strSQL;
                            }
                            else if(objParSis.getCodigoMenu()==1804){//tbr_tipdocDetprg
                                strSQL="";
                                strSQL+=" INSERT INTO tbr_tipdocDetprg(";
                                strSQL+=" co_emp, co_loc, co_tipdoc, co_mnu, st_reg)";
                                strSQL+=" (";
                                strSQL+="   SELECT *FROM(";
                                strSQL+="               SELECT CAST(" + objParSis.getCodigoEmpresa() + "AS smallint) AS co_emp,";
                                strSQL+="                      CAST(" + objParSis.getCodigoLocal() + " AS smallint) AS co_loc,";
                                strSQL+="                      CAST(" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + " AS smallint) AS co_tipDoc,";//co_tipdoc
                                strSQL+="                      CAST(" + txtCodPrg.getText() + " AS smallint) AS co_mnu, ";//co_mnu
                                strSQL+="                      CAST(";
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE))
                                    strSQL+="'S'";//st_reg
                                else
                                    strSQL+="'N'";//st_reg
                                strSQL+="                           AS character(1)) AS st_reg";

                                strSQL+="               FROM tbr_tipdocDetprg";
                                strSQL+="               WHERE";
                                strSQL+="               NOT EXISTS(";
                                strSQL+="                       SELECT co_emp, co_loc, co_tipdoc, co_mnu";
                                strSQL+="                       FROM tbr_tipdocDetprg";
                                strSQL+="                       WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";
                                strSQL+="                       AND co_loc=" + objParSis.getCodigoLocal() + "";
                                strSQL+="                       AND co_tipdoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                strSQL+="                       AND co_mnu=" + txtCodPrg.getText() + "";
                                strSQL+="               )";
                                strSQL+=") AS x";
                                strSQL+=" GROUP BY co_emp, co_loc, co_tipDoc, co_mnu, st_reg";
                                strSQL+=");";
                                strUpd+=strSQL;
                            }



                        }
                    }
                }
                System.out.println("insertarDet: " + strUpd);
                stm.executeUpdate(strUpd);
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funcián permite cargar la cabecera del registro seleccionado.
     * @return true: Si se pudo cargar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarCabReg() {
        int intPosRel;
        boolean blnRes = true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null) {
                stm=con.createStatement();
                strSQL= "";
                strSQL+="SELECT a1.co_mnu, a1.tx_nom";
                strSQL+=" FROM tbm_mnuSis AS a1";
                strSQL+=" WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!=''";
                strSQL+=" AND a1.st_reg<>'E'";
                strSQL+=" AND a1.co_mnu=" + rstCab.getString("co_mnu") + "";
                strSQL+=" ORDER BY a1.tx_nom";
                rst=stm.executeQuery(strSQL);
                if(rst.next()) {
                    txtCodPrg.setText(rst.getString("co_mnu"));
                    txtNomPrg.setText(rst.getString("tx_nom"));
                }
                else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                    blnRes=false;
                }
            }
            rst.close();
            stm.close();
            con.close();
            rst = null;
            stm = null;
            con = null;
            //Mostrar la posicián relativa del registro.
            intPosRel = rstCab.getRow();
            rstCab.last();
            objTooBar.setPosicionRelativa("" + intPosRel + " / " + rstCab.getRow());
            rstCab.absolute(intPosRel);
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función permite cargar el detalle del registro seleccionado.
     * @return true: Si se pudo cargar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarDetReg() {
        boolean blnRes = true;
        try{
            objTblMod.removeAllRows();
            //System.out.println("");
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null) {
                stm=con.createStatement();
                if(objTooBar.getEstado()=='n'){
                    if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, '' AS st_reg, '' AS co_mnu";
                        strSQL+=" FROM tbm_cabTipDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN ";
                        strSQL+=" tbr_tipDocPrg AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.st_reg NOT IN('E','I')";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.tx_desCor";
                    }
                    else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, '' AS st_reg, '' AS co_mnu";
                        strSQL+=" FROM tbm_cabTipDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN ";
                        strSQL+=" tbr_tipDocDetPrg AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a1.st_reg NOT IN('E','I')";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                        strSQL+=" ORDER BY a1.co_emp, a1.co_loc, a1.tx_desCor";
                    }

                }
                else{
                    if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a2.st_reg, a2.co_mnu";
                        strSQL+=" FROM tbm_cabTipDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN";
                        strSQL+=" tbr_tipDocPrg AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a2.co_mnu=" + txtCodPrg.getText() + "";
                        strSQL+=" AND a1.st_reg NOT IN('E','I')";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar,a2.st_reg, a2.co_mnu";
                        if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                            strSQL+=" UNION";
                            strSQL+=" (";
                            strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.tx_desLar, '' AS st_reg, 0 AS co_mnu FROM(";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		FROM tbm_cabTipDoc AS a1";
                            strSQL+=" 		LEFT OUTER JOIN";
                            strSQL+=" 		tbr_tipDocPrg AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                            strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" 		AND a1.st_reg NOT IN('E','I')";
                            strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		EXCEPT";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		FROM tbm_cabTipDoc AS a1";
                            strSQL+=" 		LEFT OUTER JOIN";
                            strSQL+=" 		tbr_tipDocPrg AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                            strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" 		AND a2.co_mnu=" + txtCodPrg.getText() + "";
                            strSQL+=" 		AND a1.st_reg NOT IN('E','I')";
                            strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 	) AS b1";
                            strSQL+=" )";
                        }
                        strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_mnu";
                    }
                    else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                        strSQL="";
                        strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar, a2.st_reg, a2.co_mnu";
                        strSQL+=" FROM tbm_cabTipDoc AS a1";
                        strSQL+=" LEFT OUTER JOIN";
                        strSQL+=" tbr_tipDocDetPrg AS a2";
                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                        strSQL+=" AND a2.co_mnu=" + txtCodPrg.getText() + "";
                        strSQL+=" AND a1.st_reg NOT IN('E','I')";
                        strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar,a2.st_reg, a2.co_mnu";
                        if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') ){
                            strSQL+=" UNION";
                            strSQL+=" (";
                            strSQL+=" 	SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.tx_desCor, b1.tx_desLar, '' AS st_reg, 0 AS co_mnu FROM(";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		FROM tbm_cabTipDoc AS a1";
                            strSQL+=" 		LEFT OUTER JOIN";
                            strSQL+=" 		tbr_tipDocDetPrg AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                            strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" 		AND a1.st_reg NOT IN('E','I')";
                            strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		EXCEPT";
                            strSQL+=" 		SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 		FROM tbm_cabTipDoc AS a1";
                            strSQL+=" 		LEFT OUTER JOIN";
                            strSQL+=" 		tbr_tipDocDetPrg AS a2";
                            strSQL+=" 		ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                            strSQL+=" 		WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                            strSQL+=" 		AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                            strSQL+=" 		AND a2.co_mnu=" + txtCodPrg.getText() + "";
                            strSQL+=" 		AND a1.st_reg NOT IN('E','I')";
                            strSQL+=" 		GROUP BY a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.tx_desCor, a1.tx_desLar";
                            strSQL+=" 	) AS b1";
                            strSQL+=" )";
                        }
                        strSQL+=" ORDER BY co_emp, co_loc, co_tipDoc, co_mnu";
                    }


                }
                System.out.println("cargarDetReg: " + strSQL);
                rst = stm.executeQuery(strSQL);
                //Limpiar vector de datos.
                vecDat.clear();
                //Obtener los registros.
                objTooBar.setMenSis("Cargando datos...");
                while (rst.next()) {
                    vecReg = new Vector();
                    vecReg.add(INT_TBL_DAT_LIN,         "");
                    vecReg.add(INT_TBL_DAT_CHK,         null);
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC, "" + rst.getObject("co_tipDoc")==null?"":rst.getString("co_tipDoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR,     "" + rst.getObject("tx_desCor")==null?"":rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR,     "" + rst.getObject("tx_desLar")==null?"":rst.getString("tx_desLar"));
                    vecReg.add(INT_TBL_DAT_CHK_PRE,     null);
                    vecReg.add(INT_TBL_DAT_CHK_AUX,     null);

                    strAux=rst.getObject("co_mnu")==null?"0":(rst.getString("co_mnu").equals("")?"0":rst.getString("co_mnu"));
                    if( ! strAux.equals("0")){
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_AUX);
                    }

                    strAux=rst.getObject("st_reg")==null?"":rst.getString("st_reg");
                    if(strAux.equals("S"))
                        vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK_PRE);

                    vecDat.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();
                rst = null;
                stm = null;
                con = null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                objTooBar.setMenSis("Listo");
                objTblMod.initRowsState();
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
     * Esta función permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm() {
        boolean blnRes = true;
        try {
            txtCodPrg.setText("");
            txtNomPrg.setText("");
            objTblMod.removeAllRows();
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite eliminar el detalle de un registro.
     * @return true: Si se pudo eliminar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarDet() {
        boolean blnRes = true;
        String strLin="";
        String strUpd="";
        try {
            if (con!=null) {
                stm=con.createStatement();
                //Armar la sentencia SQL.
//                if(objParSis.getCodigoMenu()==358){
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                            if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m')){
                                if(strLin.equals("M")){
                                    if( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                                        if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                                            strSQL="";
                                            strSQL+=" DELETE FROM tbr_tipDocPrg";
                                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                                            strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                                            strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                            strSQL+=";";
                                            strUpd+=strSQL;
                                        }
                                        else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                                            strSQL="";
                                            strSQL+=" DELETE FROM tbr_tipDocDetPrg";
                                            strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                            strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                                            strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                                            strSQL+=" AND co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                            strSQL+=";";
                                            strUpd+=strSQL;
                                        }
                                    }
                                }
                            }
                            else if( (objTooBar.getEstado()=='e') || (objTooBar.getEstado()=='y')){
                                if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                                    strSQL="";
                                    strSQL+=" DELETE FROM tbr_tipDocPrg";
                                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                                    strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                                    strSQL+=";";
                                    strUpd+=strSQL;
                                }
                                else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                                    strSQL="";
                                    strSQL+=" DELETE FROM tbr_tipDocDetPrg";
                                    strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa();
                                    strSQL+=" AND co_loc=" + objParSis.getCodigoLocal();
                                    strSQL+=" AND co_mnu=" + txtCodPrg.getText();
                                    strSQL+=";";
                                    strUpd+=strSQL;
                                }

                            }
                    }
//                }
                stm.executeUpdate(strUpd);
                stm.close();
                stm = null;
            }
        }
        catch (java.sql.SQLException e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /** Configurar el formulario */
    private boolean configurarFrm() {
        boolean blnRes = true;
        try {
            //Inicializar objetos.
            objUti = new ZafUtil();
            objDocLis=new ZafDocLis();
            objTooBar = new MiToolBar(this);
            objTooBar.setVisibleAnular(false);
            panBar.add(objTooBar);
            this.setTitle(objParSis.getNombreMenu() + " v0.1.1");
            lblTit.setText(objParSis.getNombreMenu());
            //Configurar objetos.
            txtCodPrg.setBackground(objParSis.getColorCamposObligatorios());
            txtNomPrg.setBackground(objParSis.getColorCamposObligatorios());
            //Configurar ZafVenCon.
            configurarVenConTipPrg();
            //Configurar los JTables.
            configurarTblDat();
        }
        catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta función configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat() {
        boolean blnRes = true;
        try {
            blnPre=false;
            //Configurar JTable: Establecer el modelo.
            vecDat= new Vector();    //Almacena los datos
            vecCab=new Vector(6);   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN, "");
            vecCab.add(INT_TBL_DAT_CHK, "");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC, "Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR, "Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR, "Tipo de documento");
            vecCab.add(INT_TBL_DAT_CHK_PRE, "Pre.");
            vecCab.add(INT_TBL_DAT_CHK_AUX, "Aux.");

            //Configurar JTable: Establecer el modelo de la tabla.
            objTblMod = new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
//            Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux = tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR).setPreferredWidth(400);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUX).setPreferredWidth(80);
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda = new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer columnas editables.
            vecAux = new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            vecAux.add("" + INT_TBL_DAT_CHK_PRE);
            objTblMod.setColumnasEditables(vecAux);
            vecAux = null;
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);

            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);

            objTblCelRenLbl = new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl = null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkAux = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUX).setCellRenderer(objTblCelRenChkAux);
            objTblCelRenChkAux=null;

            objTblCelRenChkPre = new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setCellRenderer(objTblCelRenChkPre);
            objTblCelRenChkPre=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if( (objTooBar.getEstado()=='x') || (objTooBar.getEstado()=='m') )
                        blnPre=true;
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK)){
                        if(! blnPre){
                            objTblMod.setChecked(true, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE);
                            blnPre=true;
                        }
                    }
                    else
                        objTblMod.setChecked(false, tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE);
                }
            });

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkAux = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_AUX).setCellEditor(objTblCelEdiChkAux);
            objTblCelEdiChkAux.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });

            objTblCelEdiChkPre = new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK_PRE).setCellEditor(objTblCelEdiChkPre);
            objTblCelEdiChkPre.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK))
                        objTblCelEdiChkPre.setCancelarEdicion(false);
                    else
                        objTblCelEdiChkPre.setCancelarEdicion(true);
                            
                }

                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(objTblMod.isChecked(tblDat.getSelectedRow(), INT_TBL_DAT_CHK_PRE)){
                        blnPre=true;
                        quitarPredeterminadoAnterior(tblDat.getSelectedRow());
                    }
                    else
                        blnPre=false;

                }
            });
            objTblBus = new ZafTblBus(tblDat);
            objTblOrd = new ZafTblOrd(tblDat);


            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_CHK_AUX, tblDat);



            // Libero los objetos auxiliares.
            tcmAux = null;
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    private boolean quitarPredeterminadoAnterior(int fila){
        boolean blnRes=true;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)){
                    if(i!=fila)
                        objTblMod.setChecked(false, i, INT_TBL_DAT_CHK_PRE);
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }




    /**
     * Esta función configura la "Ventana de consulta" que será utilizada para
     * mostrar los "Programas".
     */
    private boolean configurarVenConTipPrg() {
        boolean blnRes = true;
        try {
            //Listado de campos.
            ArrayList arlCam = new ArrayList();
            arlCam.add("a1.co_mnu");
            arlCam.add("a1.tx_nom");
            //Alias de los campos.
            ArrayList arlAli = new ArrayList();
            arlAli.add("Código");
            arlAli.add("Programa");
            //Ancho de las columnas.
            ArrayList arlAncCol = new ArrayList();

            arlAncCol.add("50");
            arlAncCol.add("494");
            //Armar la sentencia SQL.
            strSQL = "";
            strSQL += "SELECT a1.co_mnu, a1.tx_nom";
            strSQL += " FROM tbm_mnuSis AS a1";
            strSQL += " WHERE (a1.tx_tipMnu='C'or a1.tx_tipMnu='R') and a1.tx_nom!=''";
            strSQL += " AND a1.st_reg<>'E'";
            strSQL += " ORDER BY a1.tx_nom";
            vcoPrg = new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de programas", strSQL, arlCam, arlAli, arlAncCol);
            arlCam = null;
            arlAli = null;
            arlAncCol = null;
            //Configurar columnas.
            vcoPrg.setConfiguracionColumna(1, javax.swing.JLabel.LEFT);
            vcoPrg.setConfiguracionColumna(2, javax.swing.JLabel.LEFT);
        } catch (Exception e) {
            blnRes = false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /* para adicionar el tooltip
     * 
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter {

        public void mouseMoved(java.awt.event.MouseEvent evt) {
            int intCol = tblDat.columnAtPoint(evt.getPoint());
            String strMsg = "";
            switch (intCol) {
                case INT_TBL_DAT_LIN:
                    strMsg = "";
                    break;
                case INT_TBL_DAT_CHK:
                    strMsg = "Autorizar tipo de documento";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg = "Código del Tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR:
                    strMsg = "Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR:
                    strMsg = "Descripción larga del tipo de documento";
                    break;
                case INT_TBL_DAT_CHK_PRE:
                    strMsg = "Tipo de documento predeterminado";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }

    /**
     * Esta función muestra un mensaje informativo al usuario. Se podría utilizar
     * para mostrar al usuario un mensaje que indique el campo que es invalido y que
     * debe llenar o corregir.
     */
    private void mostrarMsgInf(String strMsg) {
        javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
        String strTit;
        strTit = "Mensaje del sistema Zafiro";
        oppMsg.showMessageDialog(this, strMsg, strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

 
    /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {

        boolean blnRes = true;
        strAux = "¿Desea guardar los cambios efectuados a este registro?\n";
        strAux += "Si no guarda los cambios perdera toda la informacion que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes = objTooBar.beforeInsertar();
                        if(blnRes)
                            blnRes = objTooBar.insertar();
                        break;
                    case 'm': //Modificar
                        blnRes = objTooBar.beforeModificar();
                        if(blnRes)
                            blnRes = objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnHayCam = false;
                blnRes = true;
                break;
            case 2: //CANCEL_OPTION
                blnRes = false;
                break;
        }
        return blnRes;
    }

    private int mostrarMsgCon(String strMsg) {
        String strTit = "Zafiro";
        javax.swing.JOptionPane obj = new javax.swing.JOptionPane();
        return obj.showConfirmDialog(this, strMsg, strTit, javax.swing.JOptionPane.YES_NO_CANCEL_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     *Esta función valida si los campos obligatorios han sido completados correctamente 
     *@return True si los campos obligatorios se llenaron correctamente.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal() {
        System.out.println("iscamval");
        if(objTooBar.getEstado()=='n'){
            if(existeTipoDocumentoIngresado()){
                tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
                mostrarMsgInf("<HTML>El programa <FONT COLOR=\"blue\">" + txtNomPrg.getText() + "</FONT> ya fue ingresado.<BR>Seleccione un programa aún no ingresado y vuelva a intentarlo.</HTML>");
                txtCodPrg.requestFocus();
                return false;
            }
        }

        if(existeTipoDocumentoAsociadoUsuario()){
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El programa <FONT COLOR=\"blue\">" + txtNomPrg.getText() + "</FONT> tiene usuarios asociados a algún Tipo de Documento que ha sido quitado.<BR>Seleccione un Tipo de Documento diferente o quite los usuarios relacionados a este programa y vuelva a intentarlo.</HTML>");
            txtCodPrg.requestFocus();
            return false;
        }

        if (txtCodPrg.getText().equals("")) {
            tabFrm.setSelectedIndex(0);//ubica el tab correspondiente
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Número de documento</FONT> es obligatorio.<BR>Escriba un número de tipo de documento y vuelva a intentarlo.</HTML>");
            txtCodPrg.requestFocus();
            return false;
        }

        if (txtNomPrg.getText().equals("")) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML> El campo <FONT COLOR=\"blue\">Nombre del documento</FONT> es obligatorio.<BR>Escriba un nombre de documento para la cuenta y vuelva a intentarlo.</HTML>");
            txtNomPrg.requestFocus();
            return false;
        }

        if ( ! isPredeterminado()) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>Se requiere haber seleccionado un tipo de documento como predeterminado.<BR>Seleccione algún tipo de documento como predeterminado y vuelva a intentarlo.</HTML>");
            return false;
        }

        if (objTblMod.getRowCountChecked(INT_TBL_DAT_CHK)<=0) {
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>No se ha seleccionado ningún tipo de documento.<BR>Seleccione algún tipo de documento y vuelva a intentarlo o cancele el proceso.</HTML>");
            return false;
        }
        return true;
    }


    private boolean existeTipoDocumentoIngresado(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                    strSQL="";
                    strSQL+="SELECT a1.co_mnu FROM tbr_tipDocPrg AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                }
                else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                    strSQL="";
                    strSQL+="SELECT a1.co_mnu FROM tbr_tipDocDetPrg AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                }
                rst=stm.executeQuery(strSQL);
                if(rst.next()){
                    blnRes=true;
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
            }

        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }


    private boolean existeTipoDocumentoAsociadoUsuario(){
        boolean blnRes=false;
        String strLin="";
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoMenu()==358){//tbr_tipDocPrg
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                            if(  (objTooBar.getEstado()=='x')  || (objTooBar.getEstado()=='m')){
                                if(strLin.equals("M")){
                                    if( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                                        strSQL="";
                                        strSQL+="SELECT a2.co_usr FROM tbr_tipDocPrg AS a1 INNER JOIN tbr_tipDocUsr AS a2";
                                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                        strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                                        strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                        strSQL+=" AND a2.co_usr IS NOT NULL";
                                        System.out.println("existeTipoDocumentoAsociadoUsuario: " + strSQL);
                                        rst=stm.executeQuery(strSQL);
                                        if(rst.next()){
                                            blnRes=true;
                                            break;
                                        }
                                        rst.close();
                                        rst=null;
                                    }
                                }
                            }
                            else{
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                                    strSQL="";
                                    strSQL+="SELECT a2.co_usr FROM tbr_tipDocPrg AS a1 INNER JOIN tbr_tipDocUsr AS a2";
                                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                    strSQL+=" AND a2.co_usr IS NOT NULL";
                                    System.out.println("existeTipoDocumentoAsociadoUsuario: " + strSQL);
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        blnRes=true;
                                        break;
                                    }
                                    rst.close();
                                    rst=null;
                                }
                            }



                        
                    }
                }
                else if(objParSis.getCodigoMenu()==1804){//tbr_tipDocDetPrg
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                            if(  (objTooBar.getEstado()=='x')  || (objTooBar.getEstado()=='m')){
                                if(strLin.equals("M")){
                                    if( ! objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                                        strSQL="";
                                        strSQL+="SELECT a2.co_usr FROM tbr_tipDocDetPrg AS a1 INNER JOIN tbr_tipDocDetUsr AS a2";
                                        strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                                        strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                        strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                        strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                                        strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                        strSQL+=" AND a2.co_usr IS NOT NULL";
                                        System.out.println("existeTipoDocumentoAsociadoUsuario: " + strSQL);
                                        rst=stm.executeQuery(strSQL);
                                        if(rst.next()){
                                            blnRes=true;
                                            break;
                                        }
                                        rst.close();
                                        rst=null;
                                    }
                                }
                            }
                            else{
                                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                                    strSQL="";
                                    strSQL+="SELECT a2.co_usr FROM tbr_tipDocDetPrg AS a1 INNER JOIN tbr_tipDocDetUsr AS a2";
                                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_mnu=a2.co_mnu";
                                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                                    strSQL+=" AND a1.co_mnu=" + txtCodPrg.getText() + "";
                                    strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC) + "";
                                    strSQL+=" AND a2.co_usr IS NOT NULL";
                                    System.out.println("existeTipoDocumentoAsociadoUsuario: " + strSQL);
                                    rst=stm.executeQuery(strSQL);
                                    if(rst.next()){
                                        blnRes=true;
                                        break;
                                    }
                                    rst.close();
                                    rst=null;
                                }
                            }




                    }
                }
                con.close();
                con=null;
                stm.close();
                stm=null;

            }

        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=true;
        }
        return blnRes;
    }





    /**
     * Esta función permite cargar el registro seleccionado.
     * @return true: Si se pudo cargar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarReg() {
        boolean blnRes = true;
        try {
            if (cargarCabReg()) {
                if (cargarDetReg()) {
                }
            }
            blnHayCam = false;
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función permite validar la selecciòn obligatoria de un documento 
     * predeterminado antes de realizar la respectiva inserción, modificación.
     * @return true: Si existe un documento predeterminado seleccionado
     * <BR>false: En el caso contrario.
     */
    private boolean isPredeterminado() {
        boolean blnRes=false;
        try {
            for (int i=0; i<objTblMod.getRowCountTrue(); i++) {
                if (objTblMod.isChecked(i, INT_TBL_DAT_CHK_PRE)) {
                    blnRes = true;
                    break;
                }
            }
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
            blnRes = false;
        }
        return blnRes;
    }

    /**
     * Esta función elimina el registro de la base de datos.
     * @return true: Si se pudo eliminar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean eliminarReg() {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if (eliminarDet()) {
                    con.commit();
                    blnRes = true;
                } else {
                    con.rollback();
                }
            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

 
    /**
     * Esta función actualiza el registro en la base de datos.
     * @return true: Si se pudo actualizar el registro.
     * <BR>false: En el caso contrario.
     */
    private boolean actualizarReg() {
        boolean blnRes = false;
        try {
            con = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            con.setAutoCommit(false);
            if (con != null) {
                if(eliminarDet()){
                    if (insertarDet()) {
                        con.commit();
                        blnRes = true;
                    }
                    else
                        con.rollback();
                }
                else
                    con.rollback();

            }
            con.close();
            con = null;
        } catch (java.sql.SQLException e) {
            objUti.mostrarMsgErr_F1(this, e);
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    public class MiToolBar extends ZafToolBar {

        public MiToolBar(javax.swing.JInternalFrame jfrThis) {
            super(jfrThis, objParSis);
        }

        public void clickInsertar() {
            try {
                if (blnHayCam || objTblMod.isDataModelChanged()) {
                    isRegPro();
                }
                if (rstCab != null) {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab = null;
                    stmCab = null;
                    conCab = null;
                }
                limpiarFrm();
                cargarDetReg();
                objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
                txtCodPrg.requestFocus();
                //Inicializar las variables que indican cambios.
                objTblMod.setDataModelChanged(false);
                blnHayCam = false;
                blnPre=false;
            }
            catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public boolean insertar() {
            if (!insertarReg()) {
                return false;
            }
            return true;
        }

        public void clickConsultar() {
            switch (objTooBar.getEstado()) {
                case 'c':
                case 'x':
                case 'y':
                case 'z':
                    txtCodPrg.requestFocus();
                    break;
                case 'j':
                    cargarDetReg();
                    break;
            }
            blnHayCam = false;
        }

        public boolean consultar() {
            consultarReg();
            return true;
        }

        public void clickModificar() {
            cargarReg();
            txtCodPrg.setEditable(false);
            txtNomPrg.setEditable(false);
            butPrg.setEnabled(false);
            // objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            // cargarDetReg();

            //Inicializar las variables que indican cambios.
            objTblMod.setDataModelChanged(false);
            blnHayCam = false;
        }

        public boolean modificar() {
            if (!actualizarReg()) {
                return false;
            }
            return true;
        }

        public void clickEliminar() {
                cargarDetReg();
        }

        public boolean eliminar() {
            try {
                if (!eliminarReg()) {
                    return false;
                }
                //Desplazarse al siguiente registro si es posible.
                if (!rstCab.isLast()) {
                    rstCab.next();
                    cargarReg();
                } else {
                    objTooBar.setEstadoRegistro("Eliminado");
                    limpiarFrm();
                }
                blnHayCam = false;
                blnPre=false;
            } catch (java.sql.SQLException e) {
                return true;
            }
            return true;
        }

        public void clickAnular() {
            cargarDetReg();
        }

        public boolean anular() {
            return true;
        }

        public void clickInicio() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (isRegPro()) {
                            rstCab.first();
                            cargarReg();
                        }
                    } else {
                        rstCab.first();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickAnterior() {
            try {
                if (!rstCab.isFirst()) {
                    if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (isRegPro()) {
                            rstCab.previous();
                            cargarReg();
                        }
                    } else {
                        rstCab.previous();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickSiguiente() {
            try {
                if (!rstCab.isLast()) {
                    if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (isRegPro()) {
                            rstCab.next();
                            cargarReg();
                        }
                    } else {
                        rstCab.next();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickFin() {
            try {
                if (!rstCab.isLast()) {
                    if (blnHayCam || objTblMod.isDataModelChanged()) {
                        if (isRegPro()) {
                            rstCab.last();
                            cargarReg();
                        }
                    } else {
                        rstCab.last();
                        cargarReg();
                    }
                }
            } catch (java.sql.SQLException e) {
                objUti.mostrarMsgErr_F1(this, e);
            } catch (Exception e) {
                objUti.mostrarMsgErr_F1(this, e);
            }
        }

        public void clickImprimir() {
        }

        public void clickVisPreliminar() {
        }

        public void clickAceptar() {
        }

        public void clickCancelar() {

        }


        public boolean vistaPreliminar() {
            return true;
        }

        public boolean afterVistaPreliminar() {
            return true;
        }

        public boolean imprimir() {
            return true;
        }

        public boolean afterAceptar() {
            return true;
        }

        public boolean afterInsertar() {
            blnHayCam = false;
            objTooBar.setEstado('w');
            consultarReg();
            return true;
        }

        public boolean afterModificar() {

            blnHayCam = false;
            objTooBar.setEstado('w');
            cargarReg();

            return true;
        }

        public boolean afterImprimir() {
            return true;
        }

        public boolean afterEliminar() {
            blnHayCam = false;
            return true;
        }

        public boolean afterCancelar() {
            return true;
        }

        public boolean afterAnular() {
            return true;
        }

        public boolean aceptar() {
            return true;
        }

        public boolean cancelar() {
            boolean blnRes=true;
            try{
            if(objTblMod.isDataModelChanged())
                blnHayCam=true;
                if (blnHayCam){
                    if (objTooBar.getEstado()=='n' || objTooBar.getEstado()=='m'){
                        if (!isRegPro())
                            return false;
                    }
                }
                if (rstCab!=null){
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
            }
            catch (java.sql.SQLException e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            catch (Exception e){
                objUti.mostrarMsgErr_F1(this, e);
            }
            limpiarFrm();
            blnHayCam=false;
            objTblMod.initRowsState();
            return blnRes;
        }

        public boolean afterConsultar() {
            return true;
        }

        public boolean beforeInsertar() {
            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeConsultar() {
            return true;
        }

        public boolean beforeModificar() {
            strAux = objTooBar.getEstadoRegistro();
            if (strAux.equals("Eliminado")) {
                mostrarMsgInf("El documento está ELIMINADO.\nNo es posible modificar un documento eliminado.");
                return false;
            }
            if (strAux.equals("Anulado")) {
                mostrarMsgInf("El documento está ANULADO.\nNo es posible modificar un documento anulado.");
                return false;
            }

            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeEliminar() {
            if (!isCamVal()) {
                return false;
            }
            return true;
        }

        public boolean beforeAnular() {
            return true;
        }

        public boolean beforeImprimir() {
            return true;
        }

        public boolean beforeVistaPreliminar() {
            return true;
        }

        public boolean beforeAceptar() {
            return true;
        }

        public boolean beforeCancelar() {
            return true;
        }
    }

    class ZafDocLis implements javax.swing.event.DocumentListener {

        public void changedUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) {
            blnHayCam = true;
        }
    }


    

    /**
     * Esta funcián se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis()
    {
        txtCodPrg.getDocument().addDocumentListener(objDocLis);
        txtNomPrg.getDocument().addDocumentListener(objDocLis);
    }


}



