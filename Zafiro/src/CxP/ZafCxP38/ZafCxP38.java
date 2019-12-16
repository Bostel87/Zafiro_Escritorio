/*
 * ZafCxP38.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package CxP.ZafCxP38;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafDate.ZafSelectDate;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import Librerias.ZafDate.ZafDatePicker;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import java.sql.*;
import Librerias.ZafTblUti.ZafTblCelEdiButDlg.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
/**
 *
 * @author  Eddye Lino
 */
public class ZafCxP38 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable de filtro para la consulta
    final int INT_TBL_DAT_LIN=0;                        //L�nea.
    final int INT_TBL_DAT_CHK=1;                        //Casilla de verificaci�n.
    final int INT_TBL_DAT_COD_LOC=2;                    //C�digo del local.
    final int INT_TBL_DAT_COD_TIP_DOC=3;                //C�digo del tipo de documento.
    final int INT_TBL_DAT_DES_COR_TIP_DOC=4;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=5;                //Descripci�n larga del tipo de documento.
       
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblEdi objTblEdi;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIPagTot objThrGUIPagTot;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux, strSQLCon;
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private boolean blnHayCam;                          //Determina si hay cambios en el formulario.
    private ZafDocLis objDocLis;
    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    private boolean blnMarTodChkTblEmp=true; 
    
    
    
    
    
    
    private ZafDatePicker dtpFecDoc;
    private int intSig=1;                               //Determina el signo de acuerdo al "Tipo de documento". Puede ser 1 o -1.
    
    //Constantes: Columnas del JTable para datos
    final int INT_TBL_DAT_DET_LIN=0;                        //L�nea.
    final int INT_TBL_DAT_DET_CHK=1;                        //Casilla de verificaci�n.
    final int INT_TBL_DAT_DET_COD_EMP=2;                    //C�digo del local.
    final int INT_TBL_DAT_DET_COD_LOC=3;                    //C�digo del local.
    final int INT_TBL_DAT_DET_COD_TIP_DOC=4;                //C�digo del tipo de documento.
    final int INT_TBL_DAT_DET_DES_COR_TIP_DOC=5;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DET_DES_LAR_TIP_DOC=6;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DET_COD_DOC=7;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DET_NUM_DOC=8;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DET_FEC_DOC=9;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DET_COD_CLI=10;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DET_NOM_CLI=11;                //Descripci�n corta del tipo de documento.
    final int INT_TBL_DAT_DET_SEC_DOC=12;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DET_NUM_PED=13;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DET_NUM_AUT_SRI=14;                //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_DET_FEC_CAD_DOC=15;                //Descripci�n larga del tipo de documento.
    
//    final int INT_TBL_DAT_DET_COD_SRI=16;                //Descripci�n larga del tipo de documento.
    
    
    
    
    
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private ZafVenCon vcoCta;                           //Ventana de consulta "Cuenta".
    private ZafVenCon vcoPrv;                           //Ventana de consulta "Proveedor".
    private ZafVenCon vcoBen;                           //Ventana de consulta "Beneficiario".
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private String strDesCorCta, strDesLarCta;          //Contenido del campo al obtener el foco.
    private String strCodPrv, strDesLarPrv;             //Contenido del campo al obtener el foco.
    private String strCodBen, strNomBen;                //Contenido del campo al obtener el foco.
    //Variables de la clase.
    private String strIdePrv, strDirPrv;                //Campos: RUC y Direcci�n del Beneficiario.
    private ZafSelectDate objSelDat;
    private Vector vecDatDet, vecCabDet, vecRegDet;
    
    private ZafTblMod objTblModDet;
    private ZafTblEdi objTblEdiDet;                        //Editor: Editor del JTable.
//    private ZafThreadGUI objThrGUI;
    private ZafTblCelRenChk objTblCelRenChkDet;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkDet;            //Editor: JCheckBox en celda.
    private ZafMouMotAdaDet objMouMotAdaDet;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnuDet;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafTblFilCab objTblFilCabDet;
    
    private ZafTblCelRenBut objTblCelRenButAne;
    private ZafTblCelEdiButDlg objTblCelEdiButAne;
    private ZafTblBus objTblBusDet;
    private java.util.Date datFecAux;
    private ZafThreadGUIPro objThrGUIPro;
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblCelEdiTxt objTblCelEdiTxt;                    //Editor: JTextField en celda.
    
    private String[][] objValIng=new String[1][4];
    
    
    
    /** Crea una nueva instancia de la clase ZafCxP38. */
    public ZafCxP38(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            
        }
        catch (CloneNotSupportedException e){
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

        panFrm = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panPie = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butPro = new javax.swing.JButton();
        butCer = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();
        tabFrm = new javax.swing.JTabbedPane();
        panGen = new javax.swing.JPanel();
        optTod = new javax.swing.JRadioButton();
        panFil = new javax.swing.JPanel();
        optFil = new javax.swing.JRadioButton();
        panConFil = new javax.swing.JPanel();
        panFilEsp = new javax.swing.JPanel();
        panConFilDocPrv = new javax.swing.JPanel();
        panFilTipDoc = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panFilDocPrv = new javax.swing.JPanel();
        panFilPrv = new javax.swing.JPanel();
        lblPrv = new javax.swing.JLabel();
        txtCodPrv = new javax.swing.JTextField();
        txtDesLarPrv = new javax.swing.JTextField();
        butPrv = new javax.swing.JButton();
        chkRet = new javax.swing.JCheckBox();
        panFilFec = new javax.swing.JPanel();
        panRep = new javax.swing.JPanel();
        panDet = new javax.swing.JPanel();
        spnTblDet = new javax.swing.JScrollPane();
        tblDatDet = new javax.swing.JTable();

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

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        panPie.setPreferredSize(new java.awt.Dimension(100, 50));
        panPie.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butPro.setMnemonic('P');
        butPro.setText("Procesar");
        butPro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butProActionPerformed(evt);
            }
        });
        panBot.add(butPro);

        butCer.setMnemonic('r');
        butCer.setText("Cerrar");
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

        panGen.setLayout(new java.awt.BorderLayout());

        optTod.setSelected(true);
        optTod.setActionCommand("Todos los registros");
        optTod.setIconTextGap(2);
        optTod.setLabel("Todos los registros");
        optTod.setPreferredSize(new java.awt.Dimension(113, 18));
        optTod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optTodActionPerformed(evt);
            }
        });
        panGen.add(optTod, java.awt.BorderLayout.NORTH);

        panFil.setLayout(new java.awt.BorderLayout());

        optFil.setLabel("Sólo los registros que cumplan el criterio seleccionado");
        optFil.setPreferredSize(new java.awt.Dimension(281, 18));
        optFil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optFilActionPerformed(evt);
            }
        });
        panFil.add(optFil, java.awt.BorderLayout.NORTH);

        panConFil.setLayout(new java.awt.BorderLayout());

        panFilEsp.setPreferredSize(new java.awt.Dimension(30, 100));
        panConFil.add(panFilEsp, java.awt.BorderLayout.WEST);

        panConFilDocPrv.setLayout(new java.awt.BorderLayout());

        panFilTipDoc.setLayout(new java.awt.BorderLayout());

        spnDat.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de documento"));

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

        panFilTipDoc.add(spnDat, java.awt.BorderLayout.CENTER);

        panConFilDocPrv.add(panFilTipDoc, java.awt.BorderLayout.CENTER);

        panFilDocPrv.setPreferredSize(new java.awt.Dimension(100, 140));
        panFilDocPrv.setLayout(new java.awt.BorderLayout());

        panFilPrv.setPreferredSize(new java.awt.Dimension(100, 46));
        panFilPrv.setLayout(null);

        lblPrv.setText("Proveedor:");
        lblPrv.setToolTipText("Proveedor");
        panFilPrv.add(lblPrv);
        lblPrv.setBounds(0, 2, 100, 20);

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
        panFilPrv.add(txtCodPrv);
        txtCodPrv.setBounds(100, 2, 56, 20);

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
        panFilPrv.add(txtDesLarPrv);
        txtDesLarPrv.setBounds(156, 2, 264, 20);

        butPrv.setText("...");
        butPrv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPrvActionPerformed(evt);
            }
        });
        panFilPrv.add(butPrv);
        butPrv.setBounds(420, 2, 20, 20);

        chkRet.setSelected(true);
        chkRet.setText("Presentar sólo retenciones");
        panFilPrv.add(chkRet);
        chkRet.setBounds(0, 24, 370, 16);

        panFilDocPrv.add(panFilPrv, java.awt.BorderLayout.NORTH);

        panFilFec.setLayout(new java.awt.BorderLayout());
        panFilDocPrv.add(panFilFec, java.awt.BorderLayout.CENTER);

        panConFilDocPrv.add(panFilDocPrv, java.awt.BorderLayout.SOUTH);

        panConFil.add(panConFilDocPrv, java.awt.BorderLayout.CENTER);

        panFil.add(panConFil, java.awt.BorderLayout.CENTER);

        panGen.add(panFil, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Filtro", panGen);

        panRep.setLayout(new java.awt.BorderLayout());

        panDet.setLayout(new java.awt.BorderLayout());

        tblDatDet.setModel(new javax.swing.table.DefaultTableModel(
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
        spnTblDet.setViewportView(tblDatDet);

        panDet.add(spnTblDet, java.awt.BorderLayout.CENTER);

        panRep.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("Reporte", panRep);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        getContentPane().add(panFrm);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        configurarFrm();
        agregarDocLis();
    }//GEN-LAST:event_formInternalFrameOpened

    /** Cerrar la aplicaci�n. */
    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        String strTit, strMsg;
        try
        {
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="�Est� seguro que desea cerrar este programa?";
            if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
            {
                //Cerrar la conexi�n si est� abierta.
                if (rstCab!=null)
                {
                    rstCab.close();
                    stmCab.close();
                    conCab.close();
                    rstCab=null;
                    stmCab=null;
                    conCab=null;
                }
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void optFilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optFilActionPerformed
// TODO add your handling code here:
    if(optFil.isSelected()){
        optTod.setSelected(false);
        objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
    }
    else{
        objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
        optTod.setSelected(true);
    }
}//GEN-LAST:event_optFilActionPerformed

private void optTodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optTodActionPerformed
// TODO add your handling code here:
    if(optTod.isSelected()){
        optFil.setSelected(false);
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            objTblMod.setChecked(true, i, INT_TBL_DAT_CHK);
        }
        objTblModDet.removeAllRows();
    }
    else
        optFil.setSelected(true);
}//GEN-LAST:event_optTodActionPerformed

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
// TODO add your handling code here:
    //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
    if(isChangeTable(0)){
        if (butCon.getText().equals("Consultar")){
            //objTblTotales.isActivo(false);
            blnCon=true;
            if (objThrGUIPagTot==null){
                objThrGUIPagTot=new ZafThreadGUIPagTot();
                objThrGUIPagTot.start();
            }            
        }
        else{
            blnCon=false;
        }
    }
    else{
        mostrarMsgInf("<HTML>El tipo de documento no se ha seleccionado.<BR>Seleccione por lo menos un Tipo de documento y vuelva a intentarlo.</HTML>");
    }
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
// TODO add your handling code here:
    exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

private void txtCodPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodPrvActionPerformed
txtCodPrv.transferFocus();
}//GEN-LAST:event_txtCodPrvActionPerformed

private void txtCodPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusGained
strCodPrv=txtCodPrv.getText();
        txtCodPrv.selectAll();
}//GEN-LAST:event_txtCodPrvFocusGained

private void txtCodPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtCodPrv.getText().equalsIgnoreCase(strCodPrv)){
            if (txtCodPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
            }
            else{
                mostrarVenConPrv(1);
            }
        }
        else
            txtCodPrv.setText(strCodPrv);
}//GEN-LAST:event_txtCodPrvFocusLost

private void txtDesLarPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarPrvActionPerformed
txtDesLarPrv.transferFocus();
}//GEN-LAST:event_txtDesLarPrvActionPerformed

private void txtDesLarPrvFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusGained
strDesLarPrv=txtDesLarPrv.getText();
        txtDesLarPrv.selectAll();
}//GEN-LAST:event_txtDesLarPrvFocusGained

private void txtDesLarPrvFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesLarPrvFocusLost
//Validar el contenido de la celda s�lo si ha cambiado.
        if (!txtDesLarPrv.getText().equalsIgnoreCase(strDesLarPrv)){
            if (txtDesLarPrv.getText().equals("")){
                txtCodPrv.setText("");
                txtDesLarPrv.setText("");
            }
            else{
                mostrarVenConPrv(2);
            }
        }
        else
            txtDesLarPrv.setText(strDesLarPrv);
}//GEN-LAST:event_txtDesLarPrvFocusLost

private void butPrvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPrvActionPerformed
strCodPrv=txtCodPrv.getText();
        mostrarVenConPrv(0);
}//GEN-LAST:event_butPrvActionPerformed

private void butProActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butProActionPerformed
// TODO add your handling code here:
        // TODO add your handling code here:
    if(isChangeTable(1)){
        if(isCamVal()){
            String strTit, strMsg;
            javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
            strTit="Mensaje del sistema Zafiro";
            strMsg="<HTML>¿Está seguro que desea realizar esta operación?<BR>";
            strMsg+="</HTML>";
            //Realizar acci�n de acuerdo a la etiqueta del bot�n ("Consultar" o "Detener").
            if (butPro.getText().equals("Procesar")){
                if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                    blnCon=true;
                    lblMsgSis.setText("Procesando...");
                    if (objThrGUIPro==null){
                        objThrGUIPro=new ZafThreadGUIPro();
                        objThrGUIPro.start();
                    }
                }   
            }
            else{
                blnCon=false;
    //            System.out.println("EN EL BOTON POR ELSE: ");
            }
        }
    }
    else{
        mostrarMsgInf("<HTML>No se han realizado cambios.<BR> Verifique y vuelva a intentarlo.</HTML>");
    }

}//GEN-LAST:event_butProActionPerformed

    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butPro;
    private javax.swing.JButton butPrv;
    private javax.swing.JCheckBox chkRet;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblPrv;
    private javax.swing.JLabel lblTit;
    private javax.swing.JRadioButton optFil;
    private javax.swing.JRadioButton optTod;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panConFil;
    private javax.swing.JPanel panConFilDocPrv;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFil;
    private javax.swing.JPanel panFilDocPrv;
    private javax.swing.JPanel panFilEsp;
    private javax.swing.JPanel panFilFec;
    private javax.swing.JPanel panFilPrv;
    private javax.swing.JPanel panFilTipDoc;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGen;
    private javax.swing.JPanel panPie;
    private javax.swing.JPanel panRep;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JScrollPane spnTblDet;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDatDet;
    private javax.swing.JTextField txtCodPrv;
    private javax.swing.JTextField txtDesLarPrv;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();

            objDocLis=new ZafDocLis();
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.1");//para codigo con autorizaciones es 0.16 y para produccion 0.13.1
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarVenConPrv();
            //Configurar los JTables.
            if(configurarTblDat())
                if(cargarTipoDocumentoUsuario())
                    configurarTblDet();
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            objTblOrd=new ZafTblOrd(tblDatDet);
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Esta funci�n configura el JTable "tblDat".
     * @return true: Si se pudo configurar el JTable.
     * <BR>false: En el caso contrario.
     */
    private boolean configurarTblDat()
    {
        boolean blnRes=true;
        try
        {
            
            objSelDat=new ZafSelectDate(new javax.swing.JFrame(),"d/m/y");
            objSelDat.chkSetSelected(true);
            panFilFec.add(objSelDat);
            objSelDat.setEnabled(false);
            objSelDat.setBounds(2, 2, 560, 80);
            objSelDat.chkSetSelected(false);
            
            
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(6);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_LOC,"Cód.Loc.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC,"Cód.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Ali.Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Nom.Tip.Doc.");
            
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(180);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_CHK);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdi=new ZafTblEdi(tblDat);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
//                    if (objTblCelEdiChk.isChecked()){
                        if(optTod.isSelected()){
                            optTod.setSelected(false);
                            optFil.setSelected(true);
                        }
//                    }
                }
            });
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
       
    
    private boolean configurarTblDet(){
        boolean blnRes=true;
        try{
            
            
            //Configurar JTable: Establecer el modelo.
            vecDatDet=new Vector();    //Almacena los datos
            vecCabDet=new Vector(16);  //Almacena las cabeceras
            vecCabDet.clear();
            vecCabDet.add(INT_TBL_DAT_DET_LIN,"");
            vecCabDet.add(INT_TBL_DAT_DET_CHK,"");
            vecCabDet.add(INT_TBL_DAT_DET_COD_EMP,"Cod.Emp.");
            vecCabDet.add(INT_TBL_DAT_DET_COD_LOC,"Cod.Loc.");
            vecCabDet.add(INT_TBL_DAT_DET_COD_TIP_DOC,"Cod.Tip.Doc.");
            vecCabDet.add(INT_TBL_DAT_DET_DES_COR_TIP_DOC,"Des.Cor.Tip.Doc");
            vecCabDet.add(INT_TBL_DAT_DET_DES_LAR_TIP_DOC,"Des.Lar.Tip.Doc");
            vecCabDet.add(INT_TBL_DAT_DET_COD_DOC,"Cod.Doc.");
            vecCabDet.add(INT_TBL_DAT_DET_NUM_DOC,"Num.Doc.");
            vecCabDet.add(INT_TBL_DAT_DET_FEC_DOC,"Fec.Doc.");
            
            vecCabDet.add(INT_TBL_DAT_DET_COD_CLI,"Cod.Cli.");
            vecCabDet.add(INT_TBL_DAT_DET_NOM_CLI,"Nom.Cli.");
            
            
            vecCabDet.add(INT_TBL_DAT_DET_SEC_DOC,"Sec.Doc.");
            vecCabDet.add(INT_TBL_DAT_DET_NUM_PED,"Num.Ped");
            vecCabDet.add(INT_TBL_DAT_DET_NUM_AUT_SRI,"Num.Aut.SRI.");
            vecCabDet.add(INT_TBL_DAT_DET_FEC_CAD_DOC,"Fec.Cad.");
            
            
//            vecCabDet.add(INT_TBL_DAT_DET_COD_SRI,"Cod.SRI");
            
            
            
            
            
            objTblModDet=new ZafTblMod();
            objTblModDet.setHeader(vecCabDet);
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDatDet.setModel(objTblModDet);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDatDet.setRowSelectionAllowed(true);
            tblDatDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnuDet=new ZafTblPopMnu(tblDatDet);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDatDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDatDet.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_CHK).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_EMP).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_LOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_TIP_DOC).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_DET_DES_COR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DET_DES_LAR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_DOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_DET_FEC_DOC).setPreferredWidth(80);
            
            tcmAux.getColumn(INT_TBL_DAT_DET_COD_CLI).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DET_NOM_CLI).setPreferredWidth(150);
            
            
            tcmAux.getColumn(INT_TBL_DAT_DET_SEC_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_PED).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_AUT_SRI).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_DET_FEC_CAD_DOC).setPreferredWidth(80);
            
//            tcmAux.getColumn(INT_TBL_DAT_DET_COD_SRI).setPreferredWidth(40);
            
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_DET_CHK).setResizable(false);
            //Configurar JTable: Establecer el tipo de reordenamiento de columnas.
            tblDatDet.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAdaDet=new ZafMouMotAdaDet();
            tblDatDet.getTableHeader().addMouseMotionListener(objMouMotAdaDet);
           
//            //Configurar JTable: Establecer los listener para el TableHeader.
//            tblDatDet.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
//                public void mouseClicked(java.awt.event.MouseEvent evt) {
//                    tblEmpMouseClicked(evt);
//                }
//            });  
            
            
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCabDet=new ZafTblFilCab(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_LIN).setCellRenderer(objTblFilCabDet);
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DET_CHK);
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;
            //Configurar JTable: Editor de la tabla.
            objTblEdiDet=new ZafTblEdi(tblDatDet);
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkDet=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_DET_CHK).setCellRenderer(objTblCelRenChkDet);
            objTblCelRenChkDet=null;
                        
            //Configurar JTable: Editor de celdas.
            objTblCelEdiChkDet=new ZafTblCelEdiChk(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_CHK).setCellEditor(objTblCelEdiChkDet);
            
            
            
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_DET_CHK);
            vecAux.add("" + INT_TBL_DAT_DET_SEC_DOC);
            vecAux.add("" + INT_TBL_DAT_DET_FEC_CAD_DOC);
            vecAux.add("" + INT_TBL_DAT_DET_NUM_AUT_SRI);
            vecAux.add("" + INT_TBL_DAT_DET_NUM_PED);
            
            objTblModDet.setColumnasEditables(vecAux);
            vecAux=null;
            objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);

            objTblBusDet=new ZafTblBus(tblDatDet);
            
            //Configurar ZafTblMod: Establecer las columnas obligatorias.
            java.util.ArrayList arlAux=new java.util.ArrayList();
            arlAux.add("" + INT_TBL_DAT_DET_SEC_DOC);
            arlAux.add("" + INT_TBL_DAT_DET_FEC_CAD_DOC);
            arlAux.add("" + INT_TBL_DAT_DET_NUM_AUT_SRI);
            arlAux.add("" + INT_TBL_DAT_DET_NUM_PED);
            objTblModDet.setColumnasObligatorias(arlAux);
            arlAux=null;
            //Configurar ZafTblMod: Establecer el color de fondo de las filas incompletas.
            objTblModDet.setBackgroundIncompleteRows(objParSis.getColorCamposObligatorios());

            //Configurar JTable: Renderizar celdas.
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            tcmAux.getColumn(INT_TBL_DAT_DET_SEC_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DET_FEC_CAD_DOC).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_AUT_SRI).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_PED).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;

            
            //PARA EL CAMPO DE SECUENCIA
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_SEC_DOC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil, intCol;
                String strDatFilColSelBef, strDatFilColSelAft;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelBef=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelAft=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                    if( ! strDatFilColSelBef.equals(strDatFilColSelAft)){
                        if( ! objTblModDet.isChecked(intFil, INT_TBL_DAT_DET_CHK)){
                            objTblModDet.setChecked(true, intFil, INT_TBL_DAT_DET_CHK);
                        }
                    }
                }
            });
            objTblCelEdiTxt=null;
            
            //PARA EL CAMPO DE FECHA DE CADUCIDAD
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_FEC_CAD_DOC).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil, intCol;
                String strDatFilColSelBef, strDatFilColSelAft;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelBef=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelAft=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                    if( ! strDatFilColSelBef.equals(strDatFilColSelAft)){
                        if( ! objTblModDet.isChecked(intFil, INT_TBL_DAT_DET_CHK)){
                            objTblModDet.setChecked(true, intFil, INT_TBL_DAT_DET_CHK);
                        }
                    }
                }
            });
            objTblCelEdiTxt=null;
            
            //PARA EL CAMPO DE NUMERO DE AUT. DEL SRI
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_AUT_SRI).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil, intCol;
                String strDatFilColSelBef, strDatFilColSelAft;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelBef=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelAft=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                    if( ! strDatFilColSelBef.equals(strDatFilColSelAft)){
                        if( ! objTblModDet.isChecked(intFil, INT_TBL_DAT_DET_CHK)){
                            objTblModDet.setChecked(true, intFil, INT_TBL_DAT_DET_CHK);
                        }
                    }
                }
            });
            objTblCelEdiTxt=null;
            
            //PARA EL CAMPO DE NUMERO DE PEDIDO
            objTblCelEdiTxt=new ZafTblCelEdiTxt(tblDatDet);
            tcmAux.getColumn(INT_TBL_DAT_DET_NUM_PED).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFil, intCol;
                String strDatFilColSelBef, strDatFilColSelAft;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelBef=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFil=tblDatDet.getSelectedRow();
                    intCol=tblDatDet.getSelectedColumn();
                    strDatFilColSelAft=objTblModDet.getValueAt(intFil, intCol)==null?"":objTblModDet.getValueAt(intFil, intCol).toString();
                    
                    if( ! strDatFilColSelBef.equals(strDatFilColSelAft)){
                        if( ! objTblModDet.isChecked(intFil, INT_TBL_DAT_DET_CHK)){
                            objTblModDet.setChecked(true, intFil, INT_TBL_DAT_DET_CHK);
                        }
                    }
                }
            });
            objTblCelEdiTxt=null;
            
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;            
            

            
        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    


    /**
     * Esta funci�n muestra un mensaje informativo al usuario. Se podr�a utilizar
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
     * Esta funci�n muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Esta funci�n muestra un mensaje de advertencia al usuario. Se podr�a utilizar
     * para mostrar al usuario un mensaje que indique que los datos se han cargado
     * con errores y que debe revisar dichos datos.
     */
    private void mostrarMsgAdv(String strMsg)
    {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if (strMsg.equals(""))
            strMsg="<HTML>Este registro tiene <FONT COLOR=\"red\">problemas</FONT>. <BR>Notif�quelo a su administrador del sistema.</HTML>";
        oppMsg.showMessageDialog(this,strMsg,strTit,javax.swing.JOptionPane.WARNING_MESSAGE);
    }


    
    /**
     * Esta funci�n permite limpiar el formulario.
     * @return true: Si se pudo limpiar la ventana sin ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean limpiarFrm()
    {
        boolean blnRes=true;
        try
        {
            txtCodPrv.setText("");
            strIdePrv="";
            txtDesLarPrv.setText("");
            strDirPrv="";
            objTblMod.removeAllRows();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }



    
    /**
     * Esta funci�n configura la "Ventana de consulta" que ser� utilizada para
     * mostrar los "Proveedores".
     */
    private boolean configurarVenConPrv()
    {
        boolean blnRes=true;
        String strTitVenCon="";
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a1.co_cli");
            arlCam.add("a1.tx_ide");
            arlCam.add("a1.tx_nom");
            arlCam.add("a1.tx_dir");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("C�digo");
            arlAli.add("Identificaci�n");
            arlAli.add("Nombre");
            arlAli.add("Direcci�n");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("414");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            strSQL="";
//            strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
//            strSQL+=" FROM tbm_cli AS a1";
//            strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
//            strSQL+=" AND a1.st_prv='S'";
//            strSQL+=" ORDER BY a1.tx_nom";
            strTitVenCon="Listado de Proveedores";
            if(objUti.utilizarClientesEmpresa(objParSis, objParSis.getCodigoEmpresa(), objParSis.getCodigoLocal(), objParSis.getCodigoUsuario())){
                strSQL="";
                strSQL+="SELECT a1.co_cli, a1.tx_ide, a1.tx_nom, a1.tx_dir";
                strSQL+=" FROM tbm_cli AS a1";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.st_prv='S'";
                strSQL+=" ORDER BY a1.tx_nom";
            }
            else{
                strSQL="";
                strSQL+="SELECT a2.co_cli, a2.tx_ide, a2.tx_nom, a2.tx_dir";
                strSQL+=" FROM tbr_cliLoc AS a1 INNER JOIN tbm_cli AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a2.st_prv='S'";
                strSQL+=" ORDER BY a2.tx_nom";
            }
            
            
            
            
            
            //Ocultar columnas.
            int intColOcu[]=new int[1];
            intColOcu[0]=4;
            vcoPrv=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, strTitVenCon, strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoPrv.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    




    

    
    /**
     * Esta funci�n permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de b�squeda determina si se debe hacer
     * una b�squeda directa (No se muestra la ventana de consulta a menos que no 
     * exista lo que se est� buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opci�n que desea utilizar.
     * @param intTipBus El tipo de b�squeda a realizar.
     * @return true: Si no se present� ning�n problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConPrv(int intTipBus)
    {
        boolean blnRes=true;
        try
        {
            switch (intTipBus)
            {
                case 0: //Mostrar la ventana de consulta.
                    vcoPrv.setCampoBusqueda(2);
                    vcoPrv.show();
                    if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    break;
                case 1: //B�squeda directa por "N�mero de cuenta".
                    if (vcoPrv.buscar("a1.co_cli", txtCodPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(0);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else
                        {
                            txtCodPrv.setText(strCodPrv);
                        }
                    }
                    break;
                case 2: //B�squeda directa por "Descripci�n larga".
                    if (vcoPrv.buscar("a1.tx_nom", txtDesLarPrv.getText()))
                    {
                        txtCodPrv.setText(vcoPrv.getValueAt(1));
                        strIdePrv=vcoPrv.getValueAt(2);
                        txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                        strDirPrv=vcoPrv.getValueAt(4);
                    }
                    else
                    {
                        vcoPrv.setCampoBusqueda(2);
                        vcoPrv.setCriterio1(11);
                        vcoPrv.cargarDatos();
                        vcoPrv.show();
                        if (vcoPrv.getSelectedButton()==vcoPrv.INT_BUT_ACE)
                        {
                            txtCodPrv.setText(vcoPrv.getValueAt(1));
                            strIdePrv=vcoPrv.getValueAt(2);
                            txtDesLarPrv.setText(vcoPrv.getValueAt(3));
                            strDirPrv=vcoPrv.getValueAt(4);
                        }
                        else
                        {
                            txtDesLarPrv.setText(strDesLarPrv);
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
     * Esta clase implementa la interface DocumentListener que observa los cambios que
     * se presentan en los objetos de tipo texto. Por ejemplo: JTextField, JTextArea, etc.
     * Se la usa en el sistema para determinar si existe alg�n cambio que se deba grabar
     * antes de abandonar uno de los modos o desplazarse a otro registro. Por ejemplo: si
     * se ha hecho cambios a un registro y quiere cancelar o moverse a otro registro se
     * presentar� un mensaje advirtiendo que si no guarda los cambios los perder�.
     */
    class ZafDocLis implements javax.swing.event.DocumentListener 
    {
        public void changedUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void insertUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }

        public void removeUpdate(javax.swing.event.DocumentEvent evt) 
        {
            blnHayCam=true;
        }
    }

    /**
     * Esta funci�n se encarga de agregar el listener "DocumentListener" a los objetos
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private void agregarDocLis(){
        txtCodPrv.getDocument().addDocumentListener(objDocLis);
        txtDesLarPrv.getDocument().addDocumentListener(objDocLis);
    }   



    /**
     * Esta clase hereda de la clase MouseMotionAdapter que permite manejar eventos de
     * del mouse (mover el mouse; arrastrar y soltar).
     * Se la usa en el sistema para mostrar el ToolTipText adecuado en la cabecera de
     * las columnas. Es necesario hacerlo porque el ancho de las columnas a veces
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_COD_LOC:
                    strMsg="Código del Local";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC:
                    strMsg="Código del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripci�n larga del tipo de documento";
                    break;
                default:
                    strMsg="";
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
     * resulta muy corto para mostrar leyendas que requieren m�s espacio.
     */
    private class ZafMouMotAdaDet extends java.awt.event.MouseMotionAdapter
    {
        public void mouseMoved(java.awt.event.MouseEvent evt)
        {
            int intCol=tblDat.columnAtPoint(evt.getPoint());
            String strMsg="";
            switch (intCol)
            {
                case INT_TBL_DAT_DET_COD_EMP:
                    strMsg="Código de empresa";
                    break;
                case INT_TBL_DAT_DET_COD_LOC:
                    strMsg="Código del local";
                    break;
                case INT_TBL_DAT_DET_COD_TIP_DOC:
                    strMsg="Código de tipo de documento";
                    break;
                case INT_TBL_DAT_DET_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del Tipo de documento";
                    break;
                case INT_TBL_DAT_DET_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del Tipo de documento";
                    break;
                case INT_TBL_DAT_DET_COD_DOC:
                    strMsg="Código de documento";
                    break;
                case INT_TBL_DAT_DET_NUM_DOC:
                    strMsg="Número de documento";
                    break;
                case INT_TBL_DAT_DET_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                case INT_TBL_DAT_DET_COD_CLI:
                    strMsg="Código del proveedor";
                    break;
                case INT_TBL_DAT_DET_NOM_CLI:
                    strMsg="Nombre del proveedor";
                    break;
                
                case INT_TBL_DAT_DET_SEC_DOC:
                    strMsg="Secuencia del documento";
                    break;
                case INT_TBL_DAT_DET_NUM_PED:
                    strMsg="Número de pedido";
                    break;
               case INT_TBL_DAT_DET_NUM_AUT_SRI:
                    strMsg="Número de Autorización del S.R.I.";
                    break;
                case INT_TBL_DAT_DET_FEC_CAD_DOC:
                    strMsg="Fecha de caducidad del documento";
                    break;
//                case INT_TBL_DAT_DET_COD_SRI:
//                    strMsg="Código del S.R.I.";
//                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
    }
    
    
    


    private boolean cargarTipoDocumentoUsuario(){
        boolean blnRes=true;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();
                if(objParSis.getCodigoUsuario()==1){
                    strSQL="";
                    strSQL+="SELECT a2.co_loc, a2.co_tipdoc, a2.tx_desCor, a2.tx_desLar, a2.co_ctaDeb, a2.co_ctaHab";
                    strSQL+=" FROM ";
                    strSQL+=" tbm_cabTipDoc AS a2";
                    strSQL+=" WHERE a2.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a2.st_reg NOT IN('I','E')";
                }
                else{
                    strSQL="";
                    strSQL+="SELECT a2.co_loc, a2.co_tipdoc, a2.tx_desCor, a2.tx_desLar, a2.co_ctaDeb, a2.co_ctaHab";
                    strSQL+=" FROM tbr_tipDocUsr AS a1";
                    strSQL+=" INNER JOIN tbm_cabTipDoc AS a2";
                    strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc";
                    strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                    strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
                    strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
                    strSQL+=" AND a1.co_mnu=" + objParSis.getCodigoMenu() + "";
                    strSQL+=" AND a2.st_reg NOT IN('I','E')";
                }

                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    vecReg=new Vector();
                    vecReg.add(INT_TBL_DAT_LIN, "");
                    vecReg.add(INT_TBL_DAT_CHK, "");
                    vecReg.add(INT_TBL_DAT_COD_LOC,         "" + rst.getString("co_loc"));
                    vecReg.add(INT_TBL_DAT_COD_TIP_DOC,     "" + rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC, "" + rst.getString("tx_desCor"));
                    vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC, "" + rst.getString("tx_desLar"));
                    vecReg.setElementAt(new Boolean(true), INT_TBL_DAT_CHK);
                    vecDat.add(vecReg);
                }
                con.close();
                con=null;
                stm.close();
                stm=null;
                rst.close();
                rst=null;
                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
            }
            
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }

    
   

  
    
    
    /**
     * Esta clase crea un hilo que permite manipular la interface gr�fica de usuario (GUI).
     * Por ejemplo: se la puede utilizar para cargar los datos en un JTable donde la idea
     * es mostrar al usuario lo que est� ocurriendo internamente. Es decir a medida que se
     * llevan a cabo los procesos se podr�a presentar mensajes informativos en un JLabel e
     * ir incrementando un JProgressBar con lo cual el usuario estar�a informado en todo
     * momento de lo que ocurre. Si se desea hacer �sto es necesario utilizar �sta clase
     * ya que si no s�lo se apreciar�a los cambios cuando ha terminado todo el proceso.
     */
    
 

    
    private class ZafThreadGUIPagTot extends Thread
    {
        public void run()
        {
            if (!cargarRegPagTot())
            {
                //Inicializar objetos si no se pudo cargar los datos.
                lblMsgSis.setText("Listo");
                pgrSis.setValue(0);
                butCon.setText("Consultar");
            }
            //Establecer el foco en el JTable s�lo cuando haya datos.
            if (tblDat.getRowCount()>0)
            {
                tabFrm.setSelectedIndex(1);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUIPagTot=null;
        }
    }
    
    private String getTipoDocConPagTot(){
        String strTipDocCon="";
        int p=0;
        try{
            for(int i=0; i<objTblMod.getRowCountTrue();i++){
                if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                    if(p==0){
                        strTipDocCon+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString() + "";
                        p++;
                    }
                    else{
                        strTipDocCon+=",";
                        strTipDocCon+="" + objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_COD_TIP_DOC).toString() + "";
                    }
                }
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
        }
        return strTipDocCon;
        
    }
    
    
    
    private boolean cargarRegPagTot(){
        boolean blnRes=true;
        int intNumTotReg, i;
        String strSecDocTmp, strNumPed, strNumAut, strFecCad;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");

            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                stm=con.createStatement();

                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS b3 ON a1.co_emp=b3.co_emp AND a1.co_cli=b3.co_cli)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(chkRet.isSelected())
                    strSQL+=" INNER JOIN tbm_cabTipRet AS a4 ON a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND (a2.mo_pag + a2.nd_abo) > 0";
                if(chkRet.isSelected())
                    strSQL+=" AND (a2.nd_porRet > 0) ";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN('A','C')";
                strSQL+=" AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                if(objSelDat.chkIsSelected())
                    strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                if( ! txtCodPrv.getText().toString().equals(""))
                    strSQL+=" AND a1.co_cli IN(" + txtCodPrv.getText() + ")";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, ";
                strSQL+=" a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc, a1.co_cli, b3.tx_nom, ";
                strSQL+=" a1.tx_secDoc, a1.tx_fecCad, a1.tx_numAutSRI, a1.tx_numPed";
                intNumTotReg=objUti.getNumeroRegistro(this, objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos(), strSQL);
                if (intNumTotReg==-1)
                    return false;
                
                
                strSQL="";
                strSQL+="SELECT a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc, a1.fe_doc,";
                strSQL+=" a1.co_cli, b3.tx_nom, TRIM(a1.tx_secDoc) AS tx_secDoc, TRIM(a1.tx_fecCad) AS tx_fecCad, TRIM(a1.tx_numAutSRI) AS tx_numAutSRI, TRIM(a1.tx_numPed) AS tx_numPed";
                strSQL+=" FROM ((tbm_cabMovInv AS a1 INNER JOIN tbm_cli AS b3 ON a1.co_emp=b3.co_emp AND a1.co_cli=b3.co_cli)";
                strSQL+=" INNER JOIN tbm_cabTipDoc AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbm_pagMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc ";
                strSQL+=" AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                if(chkRet.isSelected())
                    strSQL+=" INNER JOIN tbm_cabTipRet AS a4 ON a2.co_emp=a4.co_emp AND a2.co_tipRet=a4.co_tipRet";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND (a2.mo_pag + a2.nd_abo) > 0";
                if(chkRet.isSelected())
                    strSQL+=" AND (a2.nd_porRet > 0) ";
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                strSQL+=" AND a2.st_reg IN('A','C')";
                //strSQL+=" AND (a1.tx_secDoc IS NULL OR a1.tx_fecCad IS NULL OR a1.tx_numAutSRI IS NULL OR a1.tx_numPed IS NULL)";
                strSQL+=" AND a1.co_tipDoc IN(" + getTipoDocConPagTot() + ")";
                if(objSelDat.chkIsSelected())
                    strSQL+=" AND a1.fe_doc BETWEEN '" + objUti.formatearFecha(objSelDat.getDateFrom(), "dd/MM/yyyy", "yyyy/MM/dd") + "' AND '" + objUti.formatearFecha(objSelDat.getDateTo(), "dd/MM/yyyy", "yyyy/MM/dd") + "'";
                if( ! txtCodPrv.getText().toString().equals(""))
                    strSQL+=" AND a1.co_cli IN(" + txtCodPrv.getText() + ")";
                strSQL+=" GROUP BY a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, ";
                strSQL+=" a3.tx_desCor, a3.tx_desLar, a1.ne_numDoc, a1.fe_doc, a1.co_cli, b3.tx_nom, ";
                strSQL+=" a1.tx_secDoc, a1.tx_fecCad, a1.tx_numAutSRI, a1.tx_numPed";
                strSQL+=" ORDER BY b3.tx_nom, a1.co_tipdoc";
                System.out.println("SQL: " + strSQL);
                rst=stm.executeQuery(strSQL);
                
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;
                
                while(rst.next()){
                    if (blnCon){
                        vecRegDet=new Vector();
                        vecRegDet.add(INT_TBL_DAT_DET_LIN, "");
                        vecRegDet.add(INT_TBL_DAT_DET_CHK, "");
                        vecRegDet.add(INT_TBL_DAT_DET_COD_EMP,          "" + rst.getString("co_emp"));
                        vecRegDet.add(INT_TBL_DAT_DET_COD_LOC,          "" + rst.getString("co_loc"));
                        vecRegDet.add(INT_TBL_DAT_DET_COD_TIP_DOC,      "" + rst.getString("co_tipdoc"));
                        vecRegDet.add(INT_TBL_DAT_DET_DES_COR_TIP_DOC,  "" + rst.getString("tx_desCor"));
                        vecRegDet.add(INT_TBL_DAT_DET_DES_LAR_TIP_DOC,  "" + rst.getString("tx_desLar"));
                        vecRegDet.add(INT_TBL_DAT_DET_COD_DOC,          "" + rst.getString("co_doc"));
                        vecRegDet.add(INT_TBL_DAT_DET_NUM_DOC,          "" + rst.getString("ne_numDoc"));
                        vecRegDet.add(INT_TBL_DAT_DET_FEC_DOC,          "" + rst.getString("fe_doc"));
                        
                        vecRegDet.add(INT_TBL_DAT_DET_COD_CLI,          "" + rst.getString("co_cli"));
                        vecRegDet.add(INT_TBL_DAT_DET_NOM_CLI,          "" + rst.getString("tx_nom"));
                        
                        
                        strSecDocTmp=rst.getObject("tx_secDoc")==null?"":(rst.getObject("tx_secDoc").equals("null")?"":rst.getString("tx_secDoc"));
                        strNumPed=rst.getObject("tx_numPed")==null?"":(rst.getObject("tx_numPed").equals("null")?"":rst.getString("tx_numPed"));
                        strNumAut=rst.getObject("tx_numAutSRI")==null?"":(rst.getObject("tx_numAutSRI").equals("null")?"":rst.getString("tx_numAutSRI"));
                        strFecCad=rst.getObject("tx_fecCad")==null?"":(rst.getObject("tx_fecCad").equals("null")?"":rst.getString("tx_fecCad"));
                        vecRegDet.add(INT_TBL_DAT_DET_SEC_DOC,          "" + strSecDocTmp);
                        vecRegDet.add(INT_TBL_DAT_DET_NUM_PED,          "" + strNumPed);
                        vecRegDet.add(INT_TBL_DAT_DET_NUM_AUT_SRI,      "" + strNumAut);
                        vecRegDet.add(INT_TBL_DAT_DET_FEC_CAD_DOC,      "" + strFecCad);
                        
                        if(    strSecDocTmp.equals("") && strNumPed.equals("")  && strNumAut.equals("") && strFecCad.equals("")   )
                            vecRegDet.setElementAt(new Boolean(false), INT_TBL_DAT_DET_CHK);
                        else
                            vecRegDet.setElementAt(new Boolean(true), INT_TBL_DAT_DET_CHK);
                        
                        vecDatDet.add(vecRegDet);
                        
                        i++;
                        pgrSis.setValue(i);
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros");
                    }
                    else
                    {                        
                        break;
                    }

                }
                blnMarTodChkTblEmp=false;
                
                
                
                //Asignar vectores al modelo.
                objTblModDet.setData(vecDatDet);
                tblDatDet.setModel(objTblModDet);
                vecDatDet.clear();
                
                
                
                pgrSis.setValue(0);
                butCon.setText("Consultar");

                if (blnCon)
                    lblMsgSis.setText("Se encontraron " + tblDatDet.getRowCount() + " registros.");
                else
                    lblMsgSis.setText("Interrupción del usuario. Sólo se procesaron " + tblDatDet.getRowCount() + " registros.");
                butCon.setText("Consultar");
                pgrSis.setIndeterminate(false);
                con.close();
                con=null;
                rst.close();
                rst=null;
                stm.close();
                stm=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**La funci�n ZafThreadGUIPro almacena en una tabla temporal los datos necesarios para
     *luego poder realizar el proceso de unificaci�n de clientes en todas las compa��as
     */
    private class ZafThreadGUIPro extends Thread{
        public void run(){
            try{
                if(procesar()){
                    mostrarMsgInf("<HTML>El proceso guardar se realizó con éxito.</HTML>");
                    lblMsgSis.setText("Listo");

                }
                else{
                    mostrarMsgInf("<HTML>El proceso guardar no se pudo realizar</HTML>");
                }
            }
            catch(Exception e){
                
            }
            
            objThrGUIPro=null;
            //Inicializar objetos si no se pudo cargar los datos.
            butPro.setText("Procesar");
            
            
        }
    }
    
    private boolean procesar(){
        boolean blnRes=false;
        try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                if(actualizar_tbmCabMovInv()){
                    con.commit();
                    blnRes=true;
                }
                else
                    con.rollback();
                con.close();
                con=null;
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    private boolean actualizar_tbmCabMovInv(){
        boolean blnRes=true;
        String strLin="";
        String strSQLGrl="";
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int i=0;i<objTblModDet.getRowCountTrue();i++){
                    strLin=objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN).toString();
                    if(strLin.equals("M")){
                        if(objTblModDet.isChecked(i, INT_TBL_DAT_DET_CHK)){
                            strSQL="";
                            strSQL+=" UPDATE tbm_cabMovInv ";
                            strSQL+=" SET tx_secDoc=" + objUti.codificar(objTblModDet.getValueAt(i, INT_TBL_DAT_DET_SEC_DOC)) + "";
                            strSQL+=", tx_fecCad=" + objUti.codificar(objTblModDet.getValueAt(i, INT_TBL_DAT_DET_FEC_CAD_DOC)) + "";
                            strSQL+=", tx_numAutSRI=" + objUti.codificar(objTblModDet.getValueAt(i, INT_TBL_DAT_DET_NUM_AUT_SRI)) + "";
                            strSQL+=", tx_numPed=" + objUti.codificar(objTblModDet.getValueAt(i, INT_TBL_DAT_DET_NUM_PED)) + "";
                            strSQL+=" WHERE co_emp=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_EMP) + "";
                            strSQL+=" AND co_loc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_LOC) + "";
                            strSQL+=" AND co_tipDoc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_TIP_DOC) + "";
                            strSQL+=" AND co_doc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_DOC) + "";
                            strSQL+=";";
                            strSQLGrl+=strSQL;
                        }
                        else{
                            strSQL="";
                            strSQL+=" UPDATE tbm_cabMovInv ";
                            strSQL+=" SET tx_secDoc=Null";
                            strSQL+=", tx_fecCad=Null";
                            strSQL+=", tx_numAutSRI=Null";
                            strSQL+=", tx_numPed=Null";
                            strSQL+=" WHERE co_emp=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_EMP) + "";
                            strSQL+=" AND co_loc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_LOC) + "";
                            strSQL+=" AND co_tipDoc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_TIP_DOC) + "";
                            strSQL+=" AND co_doc=" + objTblModDet.getValueAt(i, INT_TBL_DAT_DET_COD_DOC) + "";
                            strSQL+=";";
                            strSQLGrl+=strSQL;
                        }
                    }
                }
                System.out.println("actualizar_tbmCabMovInv: " + strSQLGrl);
                stm.executeUpdate(strSQLGrl);
                stm.close();
                stm=null;
                
            }
        }
        catch(java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    

    private boolean isCamVal(){
                //Validar que el JTable de detalle esté completo.
        objTblModDet.removeEmptyRows();
        String strLin="";
        for(int i=0; i<objTblModDet.getRowCountTrue(); i++){
            strLin=objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN).toString();
            if(strLin.equals("M")){
                if(objTblModDet.isChecked(i, INT_TBL_DAT_DET_CHK)){
                    if (!objTblModDet.isRowComplete(i)){
                        mostrarMsgInf("<HTML>El documento contiene filas que están incompletas.<BR>Verifique el contenido de dichas filas y vuelva a intentarlo.</HTML>");
                        tblDatDet.setRowSelectionInterval(0, 0);
                        tblDatDet.changeSelection(i, INT_TBL_DAT_DET_SEC_DOC, true, true);
                        tblDatDet.requestFocus();
                        return false;
                    }
                }
                
            }
        }

        return true;
    }
    
    
    private boolean isChangeTable(int caso){
        boolean blnRes=true;
        String strLin="";
        int intCamModTbl=0;
        int intTbl=caso;
        try{
            switch(intTbl){
                case 0://boton de consultar-se observa la tabla donde estan los tipos de documentos del filtro
                    for(int i=0; i<objTblMod.getRowCountTrue(); i++){
                        strLin=objTblMod.getValueAt(i, INT_TBL_DAT_LIN)==null?"":objTblMod.getValueAt(i, INT_TBL_DAT_LIN).toString();
                        if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                            intCamModTbl++;
                        }
                    }
                    break;
                case 1://para el boton procesar-se observa la tabla de los datos cargados del reporte
                    for(int i=0; i<objTblModDet.getRowCountTrue(); i++){
                        strLin=objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN)==null?"":objTblModDet.getValueAt(i, INT_TBL_DAT_DET_LIN).toString();
                        if(strLin.equals("M")){
                            if(objTblModDet.isChecked(i, INT_TBL_DAT_DET_CHK)){
                                intCamModTbl++;
                            }
                        }
                    }
                    break;
            }
            if(intCamModTbl==0){
                blnRes=false;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    
    /**
     * Esta función se ejecuta cuando se hace el "MouseClicked" en la cabecera del JTable.
     * Se utiliza ésta función especificamente para marcar todas las casillas de verificación
     * de la columna que indica la bodega seleccionada en el el JTable de bodegas.
     */
//    private void tblEmpMouseClicked(java.awt.event.MouseEvent evt){
//        int i, intNumFil;
//        int intColSel=tblDatDet.getSelectedColumn();
//        try
//        {
//            intNumFil=objTblModDet.getRowCountTrue();
//            //Marcar la casilla sólo si se da 1 click con el botón izquierdo.
//            if (evt.getButton()==evt.BUTTON1 && evt.getClickCount()==1 && tblDatDet.columnAtPoint(evt.getPoint())==intColSel)
//            {
//                if (blnMarTodChkTblEmp){
//                    for (i=1; i<intNumFil; i++){
//                        //objTblModDet.setChecked(true, i, intColSel);
//                        //se copian los valores
//                        objTblModDet.setValueAt(objTblModDet.getValueAt(0, INT_TBL_DAT_DET_SEC_DOC), i, INT_TBL_DAT_DET_SEC_DOC);
//                        objTblModDet.setValueAt(objTblModDet.getValueAt(0, INT_TBL_DAT_DET_NUM_PED), i, INT_TBL_DAT_DET_NUM_PED);
//                        objTblModDet.setValueAt(objTblModDet.getValueAt(0, INT_TBL_DAT_DET_NUM_AUT_SRI), i, INT_TBL_DAT_DET_NUM_AUT_SRI);
//                        objTblModDet.setValueAt(objTblModDet.getValueAt(0, INT_TBL_DAT_DET_FEC_CAD_DOC), i, INT_TBL_DAT_DET_FEC_CAD_DOC);
//                    }
//                    blnMarTodChkTblEmp=false;
//                }
//                else
//                {
//                    for (i=0; i<intNumFil; i++){
//                        objTblModDet.setValueAt("", i, INT_TBL_DAT_DET_SEC_DOC);
//                        objTblModDet.setValueAt("", i, INT_TBL_DAT_DET_NUM_PED);
//                        objTblModDet.setValueAt("", i, INT_TBL_DAT_DET_NUM_AUT_SRI);
//                        objTblModDet.setValueAt("", i, INT_TBL_DAT_DET_FEC_CAD_DOC);
//
//                    }
//                    blnMarTodChkTblEmp=true;
//                }
//            }
//        }
//        catch (Exception e)
//        {
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//    }    
    
    
    
    
    
}