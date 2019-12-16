/*
 * ZafCon06.java
 *
 *  Created on 02 de noviembre de 2005, 11:25 PM
 */
package Importaciones.ZafImp27;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import Librerias.ZafVenCon.ZafVenCon;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafImp.ZafImp;
import Librerias.ZafSelFec.ZafSelFec;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
import Librerias.ZafTblUti.ZafTblCelEdiButGen.ZafTblCelEdiButGen;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import java.sql.SQLException;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafImp.ZafAjuInv;
import java.math.BigDecimal;

  

/**
 *
 * @author  Eddye Lino
 */
public class ZafImp27 extends javax.swing.JInternalFrame 
{
    //Constantes: Columnas del JTable.
    final int INT_TBL_DAT_LIN=0;                        //Línea.
    final int INT_TBL_DAT_CHK=1;                        //Checkbox.
    final int INT_TBL_DAT_COD_EMP_ING_IMP=2;            //Casilla de verificaci�n.
    final int INT_TBL_DAT_COD_LOC_ING_IMP=3;            //Código del local.
    final int INT_TBL_DAT_COD_TIP_DOC_ING_IMP=4;        //Código del tipo de documento.
    final int INT_TBL_DAT_COD_DOC_ING_IMP=5;            //Código del documento (Sistema).
    final int INT_TBL_DAT_COD_EMP_AJU=6;                //Casilla de verificaci�n.
    final int INT_TBL_DAT_COD_LOC_AJU=7;                //Código del local.
    final int INT_TBL_DAT_COD_TIP_DOC_AJU=8;            //Código del tipo de documento.
    final int INT_TBL_DAT_COD_DOC_AJU=9;                //Código del documento (Sistema).
    final int INT_TBL_DAT_DES_COR_TIP_DOC=10;            //Código del registro (Sistema).
    final int INT_TBL_DAT_DES_LAR_TIP_DOC=11;           //Código del registro (Sistema).
    final int INT_TBL_DAT_NUM_DOC=12;                   //Número del documento.
    final int INT_TBL_DAT_NUM_AJU=13;                   //Descripci�n larga del tipo de documento.
    final int INT_TBL_DAT_FEC_DOC=14;                   //Fecha de vencimiento.
    final int INT_TBL_DAT_VAL_DOC=15;                   //Valor pendiente.
    final int INT_TBL_DAT_BUT=16;                       //Valor pendiente.
   
    private ZafColNumerada objColNum;
    private ZafTblBus objTblBus;

    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblMod;
    private ZafTblCelRenLbl objTblCelRenLbl;            //Render: Presentar JLabel en JTable.
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
    private ZafTblPopMnu objTblPopMnu;                  //PopupMenu: Establecer PeopuMen� en JTable.
    private ZafTblCelRenChk objTblCelRenChkVisBue;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChkVisBue;            //Editor: JCheckBox en celda.
    private ZafVenCon vcoTipDoc;                        //Ventana de consulta "Tipo de documento".
    private Connection con, conCab;
    private Statement stm, stmCab;
    private ResultSet rst, rstCab;
    private String strSQL, strAux;
    private Vector vecDat, vecCab, vecReg;
    private boolean blnCon;                             //true: Continua la ejecuci�n del hilo.
    private ZafDocLis objDocLis;
    private String strDesCorTipDoc, strDesLarTipDoc;    //Contenido del campo al obtener el foco.
    private ZafThreadGUI objThrGUI;
    
    private int numColModIni;

    //Arreglo que contiene los labels de "Aprobar" y "Reprobar" en tbr_visBueUsrTipDoc
    private ArrayList arlRegVisBueAutDen, arlDatVisBueAutDen;
    final int INT_ARL_COD_VIS_BUE_AUT_DEN=0;
    final int INT_ARL_LAB_VIS_BUE_AUT_DEN=1;
    final int INT_ARL_COL_VIS_BUE_AUT_DEN=2;
    final int INT_ARL_EST_EDI_VIS_BUE_AUT_DEN=3;    
    
    private ZafImp objImp;
    private int intNumColAdiVisBueAutDen, intNumColIniVisBue, intNumColFinVisBue;    
    
    private ArrayList arlRegCodVisBueDB, arlDatCodVisBueDB;
    final int INT_ARL_VIS_BUE_DB_COD_EMP_DB=0;
    final int INT_ARL_VIS_BUE_DB_COD_LOC_DB=1;
    final int INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB=2;
    final int INT_ARL_VIS_BUE_DB_COD_DOC_DB=3;
    final int INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB=4;
    final int INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB=5;
    final int INT_ARL_VIS_BUE_DB_COD_USR_DB=6;
    
    private ZafTblOrd objTblOrd;                        //JTable de ordenamiento.
    
    private ZafVenCon vcoIngImp;
    
    private int intCodEmpIngImp, intCodEmpAju;
    private int intCodLocIngImp, intCodLocAju;
    private int intCodTipDocIngImp, intCodTipDocAju;
    private int intCodDocIngImp, intCodDocAju;
    
    private String strNumDocIngImp, strNumPedIngImp;
    private ZafSelFec objSelFecDoc;
    
    private boolean blnHayCam;
    private Vector vecAux;
    
    private ZafTblCelRenBut objTblCelRenButAju, objTblCelRenButAjuDin;
    private ZafTblCelEdiButGen objTblCelEdiButGenAju, objTblCelEdiButGenAjuDin;
    
    private ZafAjuInv objAjuInv;
    private int intNumColAdiBlk;
    private int intCodVisBueTipDocUsr;
    
    private ArrayList arlRegVisBueMovInv, arlDatVisBueMovInv;
    final int INT_ARL_VIS_BUE_MOV_INV_COD_EMP=0;
    final int INT_ARL_VIS_BUE_MOV_INV_COD_LOC=1;
    final int INT_ARL_VIS_BUE_MOV_INV_COD_TIP_DOC=2;
    final int INT_ARL_VIS_BUE_MOV_INV_COD_DOC=3;
    final int INT_ARL_VIS_BUE_MOV_INV_COD_VIS_BUE=4;
    final int INT_ARL_VIS_BUE_MOV_INV_EST_VIS_BUE=5;
    
    private Connection conAju;
    private ZafTblCelRenChk objTblCelRenChk;            //Render: Presentar JCheckBox en JTable.
    private ZafTblCelEdiChk objTblCelEdiChk;            //Editor: JCheckBox en celda.

    
    /** Crea una nueva instancia de la clase ZafCon06. */
    public ZafImp27(ZafParSis obj){
        try{
            initComponents();
            //Inicializar objetos.
            objParSis=(ZafParSis)obj.clone();
            objImp=new ZafImp(objParSis, javax.swing.JOptionPane.getFrameForComponent(this));
            arlDatVisBueAutDen=new ArrayList();
            intCodEmpAju=-1;
            intCodLocAju=-1;
            intCodTipDocAju=-1;
            intCodDocAju=-1;
            
            if (!configurarFrm())
                exitForm();
            agregarDocLis();
            
            
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
        tabFrm = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        panFecDoc = new javax.swing.JPanel();
        panCabFilDoc = new javax.swing.JPanel();
        panCabFil = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtCodIngImp = new javax.swing.JTextField();
        txtNumDocIngImp = new javax.swing.JTextField();
        txtNumPedIngImp = new javax.swing.JTextField();
        butPedImp = new javax.swing.JButton();
        lblTipDoc = new javax.swing.JLabel();
        txtCodTipDoc = new javax.swing.JTextField();
        txtDesCorTipDoc = new javax.swing.JTextField();
        txtDesLarTipDoc = new javax.swing.JTextField();
        butTipDoc = new javax.swing.JButton();
        panDet = new javax.swing.JPanel();
        panGenDet = new javax.swing.JPanel();
        spnDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable() {
            protected javax.swing.table.JTableHeader createDefaultTableHeader()
            {
                return new ZafTblHeaGrp(columnModel);
            }
        };
        panPieFrm = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butGua = new javax.swing.JButton();
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
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        panFrm.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        panFrm.add(lblTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 150));
        panGenCab.setLayout(new java.awt.BorderLayout());

        panFecDoc.setPreferredSize(new java.awt.Dimension(0, 70));
        panFecDoc.setLayout(new java.awt.BorderLayout());
        panGenCab.add(panFecDoc, java.awt.BorderLayout.NORTH);

        panCabFilDoc.setPreferredSize(new java.awt.Dimension(100, 28));
        panCabFilDoc.setLayout(new java.awt.BorderLayout());

        panCabFil.setBorder(javax.swing.BorderFactory.createTitledBorder("Datos Pedido"));
        panCabFil.setLayout(null);

        jLabel1.setText("Pedido:");
        panCabFil.add(jLabel1);
        jLabel1.setBounds(8, 38, 80, 14);
        panCabFil.add(txtCodIngImp);
        txtCodIngImp.setBounds(104, 32, 20, 20);

        txtNumDocIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumDocIngImpFocusLost(evt);
            }
        });
        txtNumDocIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumDocIngImpActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumDocIngImp);
        txtNumDocIngImp.setBounds(124, 32, 78, 20);

        txtNumPedIngImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNumPedIngImpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNumPedIngImpFocusLost(evt);
            }
        });
        txtNumPedIngImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNumPedIngImpActionPerformed(evt);
            }
        });
        panCabFil.add(txtNumPedIngImp);
        txtNumPedIngImp.setBounds(202, 32, 210, 20);

        butPedImp.setText("...");
        butPedImp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                butPedImpFocusLost(evt);
            }
        });
        butPedImp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butPedImpActionPerformed(evt);
            }
        });
        panCabFil.add(butPedImp);
        butPedImp.setBounds(412, 32, 20, 20);

        lblTipDoc.setText("Tipo de documento:");
        lblTipDoc.setToolTipText("Tipo de documento");
        panCabFil.add(lblTipDoc);
        lblTipDoc.setBounds(8, 14, 110, 20);
        panCabFil.add(txtCodTipDoc);
        txtCodTipDoc.setBounds(104, 12, 20, 20);

        txtDesCorTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesCorTipDocFocusLost(evt);
            }
        });
        txtDesCorTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesCorTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtDesCorTipDoc);
        txtDesCorTipDoc.setBounds(124, 12, 78, 20);

        txtDesLarTipDoc.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesLarTipDocFocusLost(evt);
            }
        });
        txtDesLarTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesLarTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(txtDesLarTipDoc);
        txtDesLarTipDoc.setBounds(202, 12, 210, 20);

        butTipDoc.setText("...");
        butTipDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butTipDocActionPerformed(evt);
            }
        });
        panCabFil.add(butTipDoc);
        butTipDoc.setBounds(412, 12, 20, 20);

        panCabFilDoc.add(panCabFil, java.awt.BorderLayout.CENTER);

        panGenCab.add(panCabFilDoc, java.awt.BorderLayout.CENTER);

        jPanel1.add(panGenCab, java.awt.BorderLayout.NORTH);

        panDet.setLayout(new java.awt.BorderLayout());

        panGenDet.setLayout(new java.awt.BorderLayout());

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

        panGenDet.add(spnDat, java.awt.BorderLayout.CENTER);

        panDet.add(panGenDet, java.awt.BorderLayout.CENTER);

        jPanel1.add(panDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", jPanel1);

        panFrm.add(tabFrm, java.awt.BorderLayout.CENTER);

        panPieFrm.setPreferredSize(new java.awt.Dimension(0, 60));
        panPieFrm.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        butCon.setMnemonic('C');
        butCon.setText("Consultar");
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        panBot.add(butCon);

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(79, 23));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        panBot.add(butGua);

        butCer.setMnemonic('r');
        butCer.setText("Cerrar");
        butCer.setPreferredSize(new java.awt.Dimension(79, 23));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        panPieFrm.add(panBot, java.awt.BorderLayout.CENTER);

        panBarEst.setPreferredSize(new java.awt.Dimension(320, 20));
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

        panPieFrm.add(panBarEst, java.awt.BorderLayout.SOUTH);

        panFrm.add(panPieFrm, java.awt.BorderLayout.SOUTH);

        getContentPane().add(panFrm);

        setBounds(0, 0, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

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
                cerrarCnx();
                dispose();
            }
        }
        catch (java.sql.SQLException e)
        {
            dispose();
        }
    }//GEN-LAST:event_exitForm

private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
    // TODO add your handling code here:
    cerrarCnx();
    if(isCamVal()){
        if (butCon.getText().equals("Consultar")){
            //objTblTotales.isActivo(false);
            blnCon=true;
            if (objThrGUI==null){
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
            }            
        }
        else{
            blnCon=false;
        }        
    }
}//GEN-LAST:event_butConActionPerformed

private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
// TODO add your handling code here:
    exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void txtNumDocIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusGained
        strNumDocIngImp=txtNumDocIngImp.getText();
        txtNumDocIngImp.selectAll();
    }//GEN-LAST:event_txtNumDocIngImpFocusGained

    private void txtNumDocIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumDocIngImpFocusLost
        if (!txtNumDocIngImp.getText().equalsIgnoreCase(strNumDocIngImp))
        {
            if (txtNumDocIngImp.getText().equals(""))
            {
                txtCodIngImp.setText("");
                txtNumDocIngImp.setText("");
                txtNumPedIngImp.setText("");

            }
            else
            {
                mostrarVenConIngImp(1);
            }
        }
        else
        txtNumDocIngImp.setText(strNumDocIngImp);
    }//GEN-LAST:event_txtNumDocIngImpFocusLost

    private void txtNumDocIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumDocIngImpActionPerformed
        txtNumDocIngImp.transferFocus();
    }//GEN-LAST:event_txtNumDocIngImpActionPerformed

    private void txtNumPedIngImpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedIngImpFocusGained
        strNumPedIngImp=txtNumPedIngImp.getText();
        txtNumPedIngImp.selectAll();
    }//GEN-LAST:event_txtNumPedIngImpFocusGained

    private void txtNumPedIngImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNumPedIngImpFocusLost
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!txtNumPedIngImp.getText().equalsIgnoreCase(strNumPedIngImp))
        {
            if (txtNumPedIngImp.getText().equals(""))
            {
                txtCodIngImp.setText("");
                txtNumPedIngImp.setText("");
                txtNumDocIngImp.setText("");
            }
            else
            {
                mostrarVenConIngImp(2);
            }
        }
        else
        txtNumPedIngImp.setText(strNumPedIngImp);
    }//GEN-LAST:event_txtNumPedIngImpFocusLost

    private void txtNumPedIngImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNumPedIngImpActionPerformed
        txtNumPedIngImp.transferFocus();
    }//GEN-LAST:event_txtNumPedIngImpActionPerformed

    private void butPedImpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_butPedImpFocusLost
        // TODO add your handling code here:
        //        if(txtCodIngImp.getText().length()>0)//Se seleccionó un Ingreso por Importación
        //            if(!isValAjuIngImp())
        //                mostrarMsgInf("<HTML>No se pudo realizar la operación correctamente.<BR>Verifique y vuelva a intentarlo.</HTML>");
        //

    }//GEN-LAST:event_butPedImpFocusLost

    private void butPedImpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butPedImpActionPerformed
        mostrarVenConIngImp(0);
    }//GEN-LAST:event_butPedImpActionPerformed

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

    private void txtDesCorTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesCorTipDocActionPerformed
        txtDesCorTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesCorTipDocActionPerformed

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

    private void txtDesLarTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesLarTipDocActionPerformed
        txtDesLarTipDoc.transferFocus();
    }//GEN-LAST:event_txtDesLarTipDocActionPerformed

    private void butTipDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butTipDocActionPerformed
        mostrarVenConTipDoc(0);
    }//GEN-LAST:event_butTipDocActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        int intNumFilSel=0;
        for(int i=0; i<objTblMod.getRowCountTrue(); i++){
            if(objTblMod.isChecked(i, INT_TBL_DAT_CHK)){
                intNumFilSel++;
            }
        }
        if(intNumFilSel==1){
            guardarCnx();
            mostrarMsgInf("<HTML>La operación GUARDAR se realizó con éxito.</HTML>");
            cerrarCnx();
            cargarReg();
        }
        else if(intNumFilSel>1){
            mostrarMsgInf("<HTML>Sólo se puede realizar un documento a la vez.</HTML>");
        }
        else{
            mostrarMsgInf("<HTML>No se han realizado cambios.<BR>Verifique y vuelva a intentarlo.</HTML>");
        }        
    }//GEN-LAST:event_butGuaActionPerformed


    /** Cerrar la aplicaci�n. */
    private void exitForm() 
    {
        cerrarCnx();
        dispose();
    }    
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butGua;
    private javax.swing.JButton butPedImp;
    private javax.swing.JButton butTipDoc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTipDoc;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panCabFil;
    private javax.swing.JPanel panCabFilDoc;
    private javax.swing.JPanel panDet;
    private javax.swing.JPanel panFecDoc;
    private javax.swing.JPanel panFrm;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDet;
    private javax.swing.JPanel panPieFrm;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JScrollPane spnDat;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    private javax.swing.JTextField txtCodIngImp;
    private javax.swing.JTextField txtCodTipDoc;
    private javax.swing.JTextField txtDesCorTipDoc;
    private javax.swing.JTextField txtDesLarTipDoc;
    private javax.swing.JTextField txtNumDocIngImp;
    private javax.swing.JTextField txtNumPedIngImp;
    // End of variables declaration//GEN-END:variables

    /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try{
            arlDatCodVisBueDB=new ArrayList();
            arlDatVisBueMovInv=new ArrayList();
            objUti=new ZafUtil();
            String strFecSis = objUti.getFechaServidor(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos(),objParSis.getQueryFechaHoraBaseDatos(),objParSis.getFormatoFechaHoraBaseDatos());
            
            strAux=objParSis.getNombreMenu();
            this.setTitle(strAux + " v0.1.2");
            lblTit.setText(strAux);
            //Configurar las ZafVenCon.
            configurarIngImp();
            //Configurar los JTables.
            configurarTblDat();

            //Configurar ZafSelFec:
            objSelFecDoc=new ZafSelFec();
            panFecDoc.add(objSelFecDoc);
            objSelFecDoc.setBounds(4, 0, 472, 68);
            objSelFecDoc.setCheckBoxVisible(true);
            objSelFecDoc.setCheckBoxChecked(false);
            objSelFecDoc.setTitulo("Fecha del documento"); 
            
            
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
            //Configurar JTable: Establecer el modelo.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector(17);  //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_DAT_LIN,"");
            vecCab.add(INT_TBL_DAT_CHK,"");
            vecCab.add(INT_TBL_DAT_COD_EMP_ING_IMP,"Cód.Emp.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_LOC_ING_IMP,"Cód.Loc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP,"Cód.Tip.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_DOC_ING_IMP,"Cód.Doc.Ing.Imp.");
            vecCab.add(INT_TBL_DAT_COD_EMP_AJU,"Cód.Emp.Aju.");
            vecCab.add(INT_TBL_DAT_COD_LOC_AJU,"Cód.Loc.Aju.");
            vecCab.add(INT_TBL_DAT_COD_TIP_DOC_AJU,"Cód.Tip.Doc.Aju.");
            vecCab.add(INT_TBL_DAT_COD_DOC_AJU,"Cód.Doc.Aju.");
            vecCab.add(INT_TBL_DAT_DES_COR_TIP_DOC,"Tip.Doc.");
            vecCab.add(INT_TBL_DAT_DES_LAR_TIP_DOC,"Tipo de documento");
            vecCab.add(INT_TBL_DAT_NUM_DOC,"Núm.Doc.");
            vecCab.add(INT_TBL_DAT_NUM_AJU,"Núm.Aju.");
            vecCab.add(INT_TBL_DAT_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_DAT_VAL_DOC,"Val.Doc.");
            vecCab.add(INT_TBL_DAT_BUT,"");
            
            objTblMod=new ZafTblMod();
            
            objTblMod.setHeader(vecCab);
            //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
            objTblMod.setColumnDataType(INT_TBL_DAT_VAL_DOC, objTblMod.INT_COL_DBL, new Integer(0), null);
                        
            //Configurar JTable: Establecer el modelo de la tabla.
            tblDat.setModel(objTblMod);
            //Configurar JTable: Establecer tipo de selecci�n.
            tblDat.setRowSelectionAllowed(true);
            tblDat.getTableHeader().setReorderingAllowed(false);
            tblDat.getTableHeader().addMouseMotionListener(new ZafMouMotAda());
            //Configurar JTable: Establecer los listener para el TableHeader.
            tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    //tblEmpMouseClicked(evt);
                }
            });


            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_DAT_LIN);
            
            
            //Configurar JTable: Establecer el men� de contexto.
            objTblPopMnu=new ZafTblPopMnu(tblDat);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_DAT_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_AJU).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_AJU).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_AJU).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_AJU).setPreferredWidth(40);
            tcmAux.getColumn(INT_TBL_DAT_DES_COR_TIP_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_DES_LAR_TIP_DOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DAT_NUM_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_NUM_AJU).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_FEC_DOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setPreferredWidth(30);
            
            //Configurar JTable: Establecer las columnas que no se pueden redimensionar.
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_ING_IMP).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_EMP_AJU).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_LOC_AJU).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_TIP_DOC_AJU).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_COD_DOC_AJU).setResizable(false);
            tcmAux.getColumn(INT_TBL_DAT_BUT).setResizable(false);
            
            //Configurar JTable: Ocultar columnas del sistema.
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_ING_IMP, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_EMP_AJU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_LOC_AJU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_TIP_DOC_AJU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_COD_DOC_AJU, tblDat);
            objTblMod.addSystemHiddenColumn(INT_TBL_DAT_BUT, tblDat);
            
            
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            //Configurar JTable: Establecer la fila de cabecera.
            objTblFilCab=new ZafTblFilCab(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_LIN).setCellRenderer(objTblFilCab);
            
            //Configurar JTable: Editor de b�squeda.
            objTblBus=new ZafTblBus(tblDat);
            
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_DAT_BUT);
            objTblMod.setColumnasEditables(vecAux);
            objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            vecAux=null;
                        
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DAT_VAL_DOC).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //para el boton de observacion
            objTblCelRenButAju=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellRenderer(objTblCelRenButAju);
            
            //(ZafParSis obj, Connection conexion, int intCodEmpDoc, int intCodLocDoc, int intCodTipDoc, int intCodDoc, int intCodMnu, char chrModTooBar
            
            objTblCelEdiButGenAju= new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGenAju);
           
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChk=new ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;

            //Configurar JTable: Editor de celdas.
            objTblCelEdiChk=new ZafTblCelEdiChk(tblDat);
            tcmAux.getColumn(INT_TBL_DAT_CHK).setCellEditor(objTblCelEdiChk);
            objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
            });
            

            objTblCelEdiButGenAju=new ZafTblCelEdiButGen();
            tcmAux.getColumn(INT_TBL_DAT_BUT).setCellEditor(objTblCelEdiButGenAju);
            objTblCelEdiButGenAju.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                boolean blnEdiCol=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    System.out.println("--------------------intColSel: " + intColSel);
                    
                    if(objTblMod.getRowCountChecked(INT_TBL_DAT_CHK)>1)
                        objTblCelEdiButGenAju.setCancelarEdicion(true);
                    else{
                        objTblCelEdiButGenAju.setCancelarEdicion(false);
                        
                        intCodEmpAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_AJU).toString());
                        intCodLocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC_AJU).toString());
                        intCodTipDocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC_AJU).toString());
                        intCodDocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC_AJU).toString());

                        intCodEmpIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_ING_IMP).toString());
                        intCodLocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC_ING_IMP).toString());
                        intCodTipDocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC_ING_IMP).toString());
                        intCodDocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC_ING_IMP).toString());
                    }
                    
                    if(intCodEmpAju!=-1){
                        abrirCnx();
                        objAjuInv.setDatAju(conAju, intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp
                                , intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, getCodVisBueUsr());
                    }                    
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    System.out.println("actionPerformed");
                    if(intCodEmpAju!=-1){
                        objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);

                        if (tblDat.getSelectedColumn()==INT_TBL_DAT_BUT){
                            objAjuInv.setVisible(true);
                        }
                        if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_ACE){
                            if(objAjuInv.isAjuModChg()){
                                objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                                objTblMod.setChecked(true, intFilSel, (intColSel+1));
                            }
                        }
                        else if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_DEN){
//                            if(objAjuInv.isAjuModChg()){
                                objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                                objTblMod.setChecked(true, intFilSel, (intColSel+2));
//                            }
                        }
                        else{//Para boton cerrar y cualquier otro caso 
                            objTblMod.setChecked(false, intFilSel, INT_TBL_DAT_CHK);
                        }
                        tblDat.requestFocus();
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    System.out.println("afterEdit");
                    if(intCodEmpAju!=-1){
                        tblDat.requestFocus();
                    }
                }
            });
            configurarVenConTipDoc();

            txtCodTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesCorTipDoc.setBackground(objParSis.getColorCamposObligatorios());
            txtDesLarTipDoc.setBackground(objParSis.getColorCamposObligatorios());            
            
            
            //Libero los objetos auxiliares.
            tcmAux=null;
            numColModIni=tblDat.getColumnCount();
            
        
            
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
            
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener{
        public void columnAdded(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnMarginChanged(javax.swing.event.ChangeEvent e){
            int intColSel, intAncCol;
            //PARA CUENTAS
            if (tblDat.getTableHeader().getResizingColumn()!=null){
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0){
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                }
            }
        }
        
        public void columnMoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e){
        }
        
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e){
        }
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
            txtCodIngImp.setText("");
            txtNumDocIngImp.setText("");
            txtNumPedIngImp.setText("");
            objTblMod.removeAllRows();
        }
        catch (Exception e)
        {
            blnRes=false;
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
   private void agregarDocLis()
    {
        txtCodIngImp.getDocument().addDocumentListener(objDocLis);
        txtNumDocIngImp.getDocument().addDocumentListener(objDocLis);
        txtNumPedIngImp.getDocument().addDocumentListener(objDocLis);
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
                case INT_TBL_DAT_COD_EMP_ING_IMP:
                    strMsg="Código de empresa de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_LOC_ING_IMP:
                    strMsg="Código de local de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_ING_IMP:
                    strMsg="Código de tipo de documento de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_DOC_ING_IMP:
                    strMsg="Código de documento de Ingreso por Importación";
                    break;
                case INT_TBL_DAT_COD_EMP_AJU:
                    strMsg="Código de empresa de ajuste";
                    break;
                case INT_TBL_DAT_COD_LOC_AJU:
                    strMsg="Código de local de ajuste";
                    break;
                case INT_TBL_DAT_COD_TIP_DOC_AJU:
                    strMsg="Código de tipo de documento de ajuste";
                    break;
                case INT_TBL_DAT_COD_DOC_AJU:
                    strMsg="Código de documento de ajuste";
                    break;
                case INT_TBL_DAT_DES_COR_TIP_DOC:
                    strMsg="Descripción corta del tipo de documento de ajuste";
                    break;
                case INT_TBL_DAT_DES_LAR_TIP_DOC:
                    strMsg="Descripción larga del tipo de documento de ajuste";
                    break;
                case INT_TBL_DAT_NUM_DOC:
                    strMsg="Número de documento de ajuste";
                    break;
                case INT_TBL_DAT_NUM_AJU:
                    strMsg="Número de Ajuste";
                    break;
                case INT_TBL_DAT_FEC_DOC:
                    strMsg="Fecha de documento";
                    break;
                    case INT_TBL_DAT_VAL_DOC:
                    strMsg="Valor documento";
                    break;
                default:
                    strMsg="";
                    break;
            }
            tblDat.getTableHeader().setToolTipText(strMsg);
        }
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
                tabFrm.setSelectedIndex(0);
                tblDat.setRowSelectionInterval(0, 0);
                tblDat.requestFocus();
            }
            objThrGUI=null;
        }
    }

   
    /**
     * Función que permite cargar las columnas de vistos buenos para los labels "Aprobar" y "Reprobar"
     * @return true Si se pudo cargar la información
     * <BR> false Caso contrario
     */
    private boolean generaLabColVisBueAutDen(){
        boolean blnRes=true;
        strAux="";
        intNumColAdiBlk=0;
        try{
            if(con!=null){
                arlDatVisBueAutDen.clear();
                stm=con.createStatement();                
                strSQL="";
                strSQL+=" SELECT co_visBue, CAST('Aprobar' AS CHARACTER VARYING) AS tx_desLar";//bdeNumColAdiBlk++
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";//intCodEmpAju
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";//intCodLocAju
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";//intCodTipDocAju
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                intNumColAdiBlk++;
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('Reprobar' AS CHARACTER VARYING) AS tx_desLar";//bdeNumColAdiBlk++
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";//intCodEmpAju
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";//intCodLocAju
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";//intCodTipDocAju
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                intNumColAdiBlk++;
                strSQL+=" UNION ";
                strSQL+=" SELECT co_visBue, CAST('' AS CHARACTER VARYING) AS tx_desLar";//bdeNumColAdiBlk++
                strSQL+=" FROM tbr_visBueUsrTipDoc";
                strSQL+=" WHERE co_emp=" + objParSis.getCodigoEmpresa() + "";//intCodEmpAju
                strSQL+=" AND co_loc=" + objParSis.getCodigoLocal() + "";//intCodLocAju
                strSQL+=" AND co_tipDoc=" + txtCodTipDoc.getText() + "";//intCodTipDocAju
                strSQL+=" AND st_reg='A'";
                strSQL+=" GROUP BY co_visBue";
                intNumColAdiBlk++;
                strSQL+=" ORDER BY co_visBue, tx_desLar ASC";
                //System.out.println("SQL generaNumColVisBue: " + strSQL);
                rst=stm.executeQuery(strSQL);                
                for(int i=0; rst.next(); i++){
                    arlRegVisBueAutDen=new ArrayList();
                    arlRegVisBueAutDen.add(INT_ARL_COD_VIS_BUE_AUT_DEN, "" + rst.getInt("co_visBue"));
                    arlRegVisBueAutDen.add(INT_ARL_LAB_VIS_BUE_AUT_DEN, "" + rst.getString("tx_desLar"));
                    arlRegVisBueAutDen.add(INT_ARL_COL_VIS_BUE_AUT_DEN, "");
                    arlRegVisBueAutDen.add(INT_ARL_EST_EDI_VIS_BUE_AUT_DEN, "");
                    arlDatVisBueAutDen.add(arlRegVisBueAutDen);
                }
                
                stm.close();
                rst.close();
                stm=null;
                rst=null;
            }
        }
        catch (java.sql.SQLException e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        catch (Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Función que permite eliminar las columnas dinámicas consultadas en tiempo de ejecución
     *  @return true Si se pudo cargar la información
     *  <BR> false Caso contrario
     */
    private boolean eliminaColTblDatAdicionadas(){
        boolean blnRes=true;
        try{
            objTblMod.removeAllRows();            
     
            for (int i=(objTblMod.getColumnCount()-1); i>=numColModIni; i--){               
               objTblMod.removeColumn(tblDat, i);
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
    } 
   
   
    /**
     * Función que permite agregar las columnas dinámicas en tiempo de ejecución
     * @return true Si se pudo cargar la información
     * <BR> false Caso contrario
     */
    private boolean configurarColAdd(){
        boolean blnRes=true;
        try{
           if(con!=null){
               if(eliminaColTblDatAdicionadas()){
                   if((generaLabColVisBueAutDen())){
                        if(agregarColVisBue()){
                            objTblMod.removeAllRows();
                            
                        }    
                   }
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
     * Función que permite agregar las columnas dinámicas de los vistos buenos
     * @return true Si se pudo cargar la información
     * <BR> false Caso contrario
     */
    private boolean agregarColVisBue(){
        boolean blnRes=true;
        strAux="";
        javax.swing.table.TableColumn tbc;
        ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
        objTblHeaGrp.setHeight(16*3);
        ZafTblHeaColGrp objTblHeaColGrpTit=null;
       
        intNumColAdiVisBueAutDen=arlDatVisBueAutDen.size();//numeero de columnas que se van a adicionar        
        intNumColIniVisBue=numColModIni;//numero de columnas que tiene el modelo antes de adicionar las columnas
        int intNumVisBueLab=0;
        int j=intNumColAdiBlk;
        try{            
            //Configurar JTable: Renderizar celdas.
            objTblCelRenChkVisBue=new ZafTblCelRenChk();
            objTblCelEdiChkVisBue=new ZafTblCelEdiChk(tblDat);
            
            
            objTblCelRenButAjuDin=new ZafTblCelRenBut();
            objTblCelEdiButGenAjuDin=new ZafTblCelEdiButGen();

            for(int i=0; i<intNumColAdiVisBueAutDen; i++){
                String strSubTit="" + objUti.getStringValueAt(arlDatVisBueAutDen, i, INT_ARL_LAB_VIS_BUE_AUT_DEN);
                                
                tbc=new javax.swing.table.TableColumn(intNumColIniVisBue+i);
                tbc.setHeaderValue(strSubTit);
                objUti.setStringValueAt(arlDatVisBueAutDen, i, INT_ARL_COL_VIS_BUE_AUT_DEN, String.valueOf(intNumColIniVisBue+i));
                //Configurar JTable: Establecer el ancho de la columna.
                tbc.setPreferredWidth(40);             

                if(j==intNumColAdiBlk){
                    objUti.setStringValueAt(arlDatVisBueAutDen, i, INT_ARL_EST_EDI_VIS_BUE_AUT_DEN, "S");
                    
                    intNumVisBueLab++;
                    objTblHeaColGrpTit=new ZafTblHeaColGrp("Visto Bueno " + intNumVisBueLab);
                    objTblHeaColGrpTit.setHeight(16);
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenButAjuDin);
                    tbc.setCellEditor(objTblCelEdiButGenAjuDin);//objTblCelEdiChkVisBue
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc);
                }
                else{
                    //Configurar JTable: Renderizar celdas.
                    tbc.setCellRenderer(objTblCelRenChkVisBue);
                    tbc.setCellEditor(objTblCelEdiChkVisBue);                
                    //Configurar JTable: Agregar la columna al JTable.
                    objTblMod.addColumn(tblDat, tbc); 
                }
                
                if(j<=intNumColAdiBlk){
                    if(j==1)
                        j=intNumColAdiBlk;
                    else
                        j--;
                }
                
                objTblHeaColGrpTit.add(tbc);
                objTblHeaGrp.addColumnGroup(objTblHeaColGrpTit);
            }
            
            intNumColFinVisBue=objTblMod.getColumnCount();
            
            
            objTblCelRenChkVisBue.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
                public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                }
            });
            
            objTblCelEdiChkVisBue.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                        
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {                    
                }

            });
            objTblCelEdiButGenAjuDin.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                int intFilSel=-1;
                int intColSel=-1;
                boolean blnEdiCol=false;
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    intFilSel=tblDat.getSelectedRow();
                    intColSel=tblDat.getSelectedColumn();
                    System.out.println("----****intColSel: " + intColSel);
                    //Valida que exista un solo registro seleccionado
                    if(objTblMod.getRowCountChecked(INT_TBL_DAT_CHK)>1)
                        objTblCelEdiButGenAju.setCancelarEdicion(true);
                    else{
                        objTblCelEdiButGenAju.setCancelarEdicion(false);
                                                                        
                        if(isValCelEdi(intColSel)){
                            if(isValCelEdiVisBuePre(intFilSel, intColSel)){
                                objTblCelEdiButGenAjuDin.setCancelarEdicion(false);
                                blnEdiCol=true;
                            }
                            else{
                                objTblCelEdiButGenAjuDin.setCancelarEdicion(true);
                                blnEdiCol=false;
                                mostrarMsgInf("El visto bueno previo aún no ha sido concedido.");
                            }
                        }
                        else{
                            objTblCelEdiButGenAjuDin.setCancelarEdicion(true);
                            blnEdiCol=false;
                            mostrarMsgInf("Ud. no cuenta con permisos para conceder este visto bueno.");
                        }
                        System.out.println("----****blnEdiCol: " + blnEdiCol);
                        if(blnEdiCol){
                            intCodEmpAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_AJU).toString());
                            intCodLocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC_AJU).toString());
                            intCodTipDocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC_AJU).toString());
                            intCodDocAju=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC_AJU).toString());

                            intCodEmpIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_EMP_ING_IMP).toString());
                            intCodLocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_LOC_ING_IMP).toString());
                            intCodTipDocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_TIP_DOC_ING_IMP).toString());
                            intCodDocIngImp=Integer.parseInt(objTblMod.getValueAt(intFilSel, INT_TBL_DAT_COD_DOC_ING_IMP).toString());

                            if(intCodEmpAju!=-1){
                                abrirCnx();
                                objAjuInv.setDatAju(conAju,intCodEmpIngImp, intCodLocIngImp, intCodTipDocIngImp, intCodDocIngImp
                                        , intCodEmpAju, intCodLocAju, intCodTipDocAju, intCodDocAju, getCodVisBueUsr());
                            } 
                        }
                        
                        
                    }
                    
                    
                    
                }
                public void actionPerformed(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                    if(blnEdiCol){
                        System.out.println("actionPerformed-1");
                        objAjuInv.setVisible(true);
                        System.out.println("DESPUES VISIBLE");
                        
                        if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_ACE){
                            System.out.println("ACEPTAR");
                            if(objAjuInv.isAjuModChg()){
                                System.out.println("objAjuInv.isAjuModChg");
                                objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                                objTblMod.setChecked(true, intFilSel, (intColSel+1));
                            }
                        }
                        else if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_DEN){
                            System.out.println("DENEGAR");
//                            if(objAjuInv.isAjuModChg()){
                                objTblMod.setChecked(true, intFilSel, INT_TBL_DAT_CHK);
                                objTblMod.setChecked(true, intFilSel, (intColSel+2));
//                            }
                        }
                        else{
                            System.out.println("CERRAR");
                            objTblMod.setChecked(false, intFilSel, INT_TBL_DAT_CHK);
                        }

                        System.out.println("actionPerformed-2");
                    }

                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    if(blnEdiCol){
                        System.out.println("afterEdit");
                    }
                    //cargarReg();
                    tblDat.requestFocus();
                }
            });
            
            //System.out.println("**arlDatVisBueAutDen: " + arlDatVisBueAutDen.toString());

            objTblHeaGrp=null;
            objTblHeaColGrpTit=null;

        }
        catch(Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }

    /**
     * Función que permite cargar los datos a consultar
     * @return true Si se pudo cargar la información
     * <BR> false Caso contrario
     */
    private boolean cargarReg(){
         boolean blnRes=true;
         try{
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(con!=null){
                objAjuInv=new ZafAjuInv(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, objImp.INT_COD_MNU_PRG_AJU_INV, 'm'); 
                                
                if(configurarColAdd()){
                    if(cargarDetReg()){
                            if(cargarVisBueTipDoc()){

                            }
                    }
                }
                con.close();
                con=null;
            }            
         }
        catch (Exception e){
            blnRes=false;
        }
        return blnRes;
     }
   
   
    /**
     * Función que permite cargar información de los documentos a los que se ha concedido visto bueno según los usuarios configurados para cada tipo de documento configurado
     * @return true Si se pudo cargar la información
     * <BR> false Caso contrario
     */
    private boolean cargarArregloVistosBuenosDB(){
        boolean blnRes=true;
        arlDatCodVisBueDB.clear();
        try{
            if(con!=null){
                stm=con.createStatement();
                strSQL="";
                strSQL+="SELECT b1.co_emp, b1.co_loc, b1.co_tipDoc, b1.co_doc, b1.co_visBue, b1.st_reg, b1.co_usr";
                strSQL+=" FROM tbm_visBueMovInv AS b1";
                strSQL+=" WHERE b1.co_emp=" + intCodEmpAju + "";
                strSQL+=" AND b1.co_loc=" + intCodLocAju + "";
                strSQL+=" AND b1.co_tipDoc=" + intCodTipDocAju + "";
                strSQL+=" AND b1.co_doc=" + intCodDocAju + "";
 //               strSQL+=" AND b1.co_usr=" + objParSis.getCodigoUsuario() + "";
                strSQL+=" ORDER BY b1.co_doc, b1.co_visBue";
//                System.out.println("cargarArregloVistosBuenosDB: " + strSQL);
                rst=stm.executeQuery(strSQL);
                while(rst.next()){
                    arlRegCodVisBueDB=new ArrayList();
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_EMP_DB,     "" + rst.getString("co_emp"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_LOC_DB,     "" + rst.getString("co_loc"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_TIP_DOC_DB, "" + rst.getString("co_tipDoc"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_DOC_DB,     "" + rst.getString("co_doc"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_VIS_BUE_DB, "" + rst.getString("co_visBue"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_EST_VIS_BUE_DB, "" + rst.getString("st_reg"));
                    arlRegCodVisBueDB.add(INT_ARL_VIS_BUE_DB_COD_USR_DB,     "" + rst.getString("co_usr"));
                    arlDatCodVisBueDB.add(arlRegCodVisBueDB);
                }
                stm.close();
                stm=null;
                rst.close();
                rst=null;
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
     * mostrar los "Pedidos".
     */
    private boolean configurarIngImp()
    {
        boolean blnRes=true;
        try
        {
            //Listado de campos.
            ArrayList arlCam=new ArrayList();
            arlCam.add("a2.co_empRel");
            arlCam.add("a2.co_locRel");
            arlCam.add("a2.co_tipDocRel");
            arlCam.add("a2.co_docRel");
            arlCam.add("a1.co_emp");
            arlCam.add("a1.co_loc");
            arlCam.add("a1.co_tipDoc");
            arlCam.add("a1.co_doc");
            arlCam.add("a3.tx_desCor");
            arlCam.add("a1.ne_numDoc");
            arlCam.add("a1.tx_numDoc2");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Cód.Emp.Ing.Imp.");
            arlAli.add("Cód.Loc.Ing.Imp.");
            arlAli.add("Cód.Tip.Doc.Ing.Imp.");
            arlAli.add("Cód.Doc.Ing.Imp.");
            arlAli.add("Cód.Emp.Aju.");
            arlAli.add("Cód.Loc.Aju.");
            arlAli.add("Cód.Tip.Doc..Aju.");
            arlAli.add("Cód.Doc.Aju.");
            arlAli.add("Tip.Doc.");
            arlAli.add("Núm.Doc.");
            arlAli.add("Núm.Aju.");            
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("40");
            arlAncCol.add("50");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("40");
            arlAncCol.add("50");
            arlAncCol.add("50");
            arlAncCol.add("40");
            arlAncCol.add("70");
            arlAncCol.add("70");
            arlAncCol.add("70");

            //Armar la sentencia SQL.
            strSQL="";
            strSQL+=" SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel";
            strSQL+=" , a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
            strSQL+=" , a3.tx_desCor, a3.tx_desLar";
            strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
            strSQL+=" INNER JOIN tbr_cabMovInv AS a2";
            strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
            strSQL+=" WHERE a1.st_aut='P' AND a1.st_reg='O' AND a1.st_ingImp IS NULL";
//            System.out.println("configurarIngImp: " + strSQL);
            
            //Ocultar columnas.
            int intColOcu[]=new int[9];
            intColOcu[0]=1;
            intColOcu[1]=2;
            intColOcu[2]=3;
            intColOcu[3]=4;
            intColOcu[4]=5;
            intColOcu[5]=6;
            intColOcu[6]=7;
            intColOcu[7]=8;
            intColOcu[8]=10;
            
            vcoIngImp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de Ingresos por Importación", strSQL, arlCam, arlAli, arlAncCol,intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
            //Configurar columnas.
            vcoIngImp.setConfiguracionColumna(1, javax.swing.JLabel.RIGHT);
            vcoIngImp.setConfiguracionColumna(2, javax.swing.JLabel.RIGHT);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    

    /**
     * Función que permite cargar información del Ingreso por Importación/Compras Locales
     * @param intTipBus
     * @return 
     */
    private boolean mostrarVenConIngImp(int intTipBus){
        boolean blnRes=true;
        try{
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoIngImp.setCampoBusqueda(5);
                    vcoIngImp.setVisible(true);
                    if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));
                        
                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(2).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        
                        intCodEmpAju=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        intCodLocAju=Integer.parseInt(vcoIngImp.getValueAt(6).toString());
                        intCodTipDocAju=Integer.parseInt(vcoIngImp.getValueAt(7).toString());
                        intCodDocAju=Integer.parseInt(vcoIngImp.getValueAt(8).toString());
                    }
                    break;
                case 1: //Búsqueda directa por "Número Documento".
                    if (vcoIngImp.buscar("a1.ne_numDoc", txtNumDocIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(2).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        
                        intCodEmpAju=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        intCodLocAju=Integer.parseInt(vcoIngImp.getValueAt(6).toString());
                        intCodTipDocAju=Integer.parseInt(vcoIngImp.getValueAt(7).toString());
                        intCodDocAju=Integer.parseInt(vcoIngImp.getValueAt(8).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(4);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));
                            
                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(2).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());

                            intCodEmpAju=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            intCodLocAju=Integer.parseInt(vcoIngImp.getValueAt(6).toString());
                            intCodTipDocAju=Integer.parseInt(vcoIngImp.getValueAt(7).toString());
                            intCodDocAju=Integer.parseInt(vcoIngImp.getValueAt(8).toString());
                        }
                        else
                        {
                            txtNumDocIngImp.setText(strNumDocIngImp); 
                        }
                    }
                    break;
                case 3: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.tx_numDoc2", txtNumPedIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(2).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        
                        intCodEmpAju=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        intCodLocAju=Integer.parseInt(vcoIngImp.getValueAt(6).toString());
                        intCodTipDocAju=Integer.parseInt(vcoIngImp.getValueAt(7).toString());
                        intCodDocAju=Integer.parseInt(vcoIngImp.getValueAt(8).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(5);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));

                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(2).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());

                            intCodEmpAju=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                            intCodLocAju=Integer.parseInt(vcoIngImp.getValueAt(6).toString());
                            intCodTipDocAju=Integer.parseInt(vcoIngImp.getValueAt(7).toString());
                            intCodDocAju=Integer.parseInt(vcoIngImp.getValueAt(8).toString());
                        }
                        else
                        {
                            txtNumPedIngImp.setText(strNumPedIngImp);
                        }
                    }
                    break;
                case 5: //Búsqueda directa por "Descripción larga".
                    if (vcoIngImp.buscar("a1.co_doc", txtCodIngImp.getText()))
                    {
                        txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                        txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                        txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));

                        intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                        intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                        intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                        intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                    }
                    else
                    {
                        vcoIngImp.setCampoBusqueda(4);
                        vcoIngImp.setCriterio1(11);
                        vcoIngImp.cargarDatos();
                        vcoIngImp.setVisible(true);
                        if (vcoIngImp.getSelectedButton()==ZafVenCon.INT_BUT_ACE)
                        {
                            txtCodIngImp.setText(vcoIngImp.getValueAt(1));
                            txtNumDocIngImp.setText(vcoIngImp.getValueAt(9));
                            txtNumPedIngImp.setText(vcoIngImp.getValueAt(10));
                            
                            intCodEmpIngImp=Integer.parseInt(vcoIngImp.getValueAt(1).toString());
                            intCodLocIngImp=Integer.parseInt(vcoIngImp.getValueAt(3).toString());
                            intCodTipDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(4).toString());
                            intCodDocIngImp=Integer.parseInt(vcoIngImp.getValueAt(5).toString());
                        }
                        else
                        {
                            txtNumPedIngImp.setText(strNumPedIngImp);
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
    

   private boolean cargarDetReg(){
        int intNumTotReg, i;
        boolean blnRes=true;
        try{
            butCon.setText("Detener");
            lblMsgSis.setText("Obteniendo datos...");
            if (con!=null){
                strAux="";
                if(txtCodIngImp.getText().length()>0){
                    strAux+=" AND a2.co_empRel=" + intCodEmpIngImp + " AND a2.co_locRel=" + intCodLocIngImp + "";
                    strAux+=" AND a2.co_tipDocRel=" + intCodTipDocIngImp + " AND a2.co_docRel=" + intCodDocIngImp + "";
                }
                
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
                stm=con.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_empRel, a2.co_locRel, a2.co_tipDocRel, a2.co_docRel";
                strSQL+=" , a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.ne_numDoc, a1.tx_numDoc2";
                strSQL+=" , a1.fe_doc, a1.nd_tot, a3.tx_desCor, a3.tx_desLar";
                strSQL+=" FROM (tbm_cabMovInv AS a1 INNER JOIN tbm_cabTipDoc AS a3 ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc)";
                strSQL+=" INNER JOIN tbr_cabMovInv AS a2";
                strSQL+=" ON a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc";
                strSQL+=" WHERE a1.st_aut IN('P','E') AND a1.st_reg='O' AND a1.st_ingImp='U'";
                strSQL+=strAux;
                strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
//                if(txtCodTipDoc.getText().equals(""+objImp.INT_COD_TIP_DOC_AJU_ING_IMP)){
//                    strSQL+=" AND a1.co_tipDoc=14";
//                }
//                else if(txtCodTipDoc.getText().equals(""+objImp.INT_COD_TIP_DOC_AJU_COM_LOC)){
//                    strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
//                }                
                System.out.println("SQL cargarDetReg: " + strSQL);
                rst=stm.executeQuery(strSQL);
                vecDat.clear();
                lblMsgSis.setText("Cargando datos...");
                pgrSis.setMinimum(0);
                //pgrSis.setMaximum(intNumTotReg);
                pgrSis.setValue(0);
                i=0;                

                while (rst.next()){
                    if (blnCon){
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_DAT_LIN,"");
                        vecReg.add(INT_TBL_DAT_CHK,null);
                        vecReg.add(INT_TBL_DAT_COD_EMP_ING_IMP,       rst.getString("co_empRel"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_ING_IMP,       rst.getString("co_locRel"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_ING_IMP,   rst.getString("co_tipDocRel"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_ING_IMP,       rst.getString("co_docRel"));
                        vecReg.add(INT_TBL_DAT_COD_EMP_AJU,           rst.getString("co_emp"));
                        vecReg.add(INT_TBL_DAT_COD_LOC_AJU,           rst.getString("co_loc"));
                        vecReg.add(INT_TBL_DAT_COD_TIP_DOC_AJU,       rst.getString("co_tipdoc"));
                        vecReg.add(INT_TBL_DAT_COD_DOC_AJU,           rst.getString("co_doc"));
                        vecReg.add(INT_TBL_DAT_DES_COR_TIP_DOC,       rst.getString("tx_desCor"));
                        vecReg.add(INT_TBL_DAT_DES_LAR_TIP_DOC,       rst.getString("tx_desLar"));
                        vecReg.add(INT_TBL_DAT_NUM_DOC,               rst.getString("ne_numDoc"));
                        vecReg.add(INT_TBL_DAT_NUM_AJU,               rst.getString("tx_numDoc2"));
                        vecReg.add(INT_TBL_DAT_FEC_DOC,               rst.getString("fe_doc"));
                        vecReg.add(INT_TBL_DAT_VAL_DOC,               rst.getString("nd_tot"));
                        vecReg.add(INT_TBL_DAT_BUT,                   null);
                        
                        for (int j=intNumColIniVisBue; j<intNumColFinVisBue; j++){
                            vecReg.add(j,     null);
                        }
                        vecDat.add(vecReg);
                        i++;
                        pgrSis.setValue(i);                        
                        //lblMsgSis.setText("Se encontraron " + rst.getRow() + " registros.");                        
                    }
                    else{
                        break;
                    }                    
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
                
                
                
                //inicio edicion columna
                vecAux=new Vector();
                int intColVisBueArl=-1;
                String strColEdiBotVisBue="";

                for(int h=0; h<arlDatVisBueAutDen.size(); h++){
                    intColVisBueArl=objUti.getIntValueAt(arlDatVisBueAutDen, h, INT_ARL_COL_VIS_BUE_AUT_DEN);
                    strColEdiBotVisBue=objUti.getStringValueAt(arlDatVisBueAutDen, h, INT_ARL_EST_EDI_VIS_BUE_AUT_DEN);
                    if(strColEdiBotVisBue.equals("S")){                                
                        for(int j=intNumColIniVisBue; j<intNumColFinVisBue;j++){
                            if(intColVisBueArl==j){
                                vecAux.add("" + j);
                                break;
                            }
                        }
                    }
                }
                objTblMod.setColumnasEditables(vecAux);
                vecAux=null;
                //fin edicion columna
                
                
                
                

                //Asignar vectores al modelo.
                objTblMod.setData(vecDat);
                tblDat.setModel(objTblMod);
                vecDat.clear();
                lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");

                pgrSis.setValue(0);
                butCon.setText("Consultar");
                
                objTblMod.initRowsState();
            }
        }
        catch (java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Esta función determina si los campos son válidos.
     * @return true: Si los campos son válidos.
     * <BR>false: En el caso contrario.
     */
    private boolean isCamVal(){
        if (txtDesCorTipDoc.getText().equals("")){
            tabFrm.setSelectedIndex(0);
            mostrarMsgInf("<HTML>El campo <FONT COLOR=\"blue\">Tipo de documento</FONT> es obligatorio.<BR>Seleccione un tipo de documento y vuelva a intentarlo.</HTML>");
            txtDesCorTipDoc.requestFocus();
            return false;
        }        
        return true;
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
            arlCam.add("a1.tx_natDoc");
            arlCam.add("a1.st_necautanudoc");
            //Alias de los campos.
            ArrayList arlAli=new ArrayList();
            arlAli.add("Código");
            arlAli.add("Tip.Doc.");
            arlAli.add("Tipo de documento");
            arlAli.add("Ult.Doc.");
            arlAli.add("Nat.Doc.");
            arlAli.add("Nec.Aut.Anu.");
            //Ancho de las columnas.
            ArrayList arlAncCol=new ArrayList();
            arlAncCol.add("50");
            arlAncCol.add("80");
            arlAncCol.add("334");
            arlAncCol.add("80");
            arlAncCol.add("80");
            arlAncCol.add("80");
            //Armar la sentencia SQL.
            if(objParSis.getCodigoUsuario()==1){
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbm_cabTipDoc AS a1 ";
                strSQL+=" INNER JOIN tbr_tipDocPrg AS a3";
                strSQL+=" ON a1.co_emp=a3.co_emp AND a1.co_loc=a3.co_loc AND a1.co_tipDoc=a3.co_tipDoc";
                strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu();
                strSQL+=" ORDER BY a1.tx_desCor";
            }
            else{
                strSQL="";
                strSQL+="SELECT a1.co_tipdoc, a1.tx_desCor, a1.tx_desLar, a1.ne_ultDoc";
                strSQL+=" ,a1.tx_natDoc, CASE WHEN a1.st_necautanudoc IS NULL THEN '' ELSE a1.st_necautanudoc END AS st_necautanudoc";
                strSQL+=" FROM tbr_tipDocUsr AS a3 inner join  tbm_cabTipDoc AS a1 ";
                strSQL+=" ON (a1.co_emp=a3.co_emp and a1.co_loc=a3.co_loc and a1.co_tipdoc=a3.co_tipdoc)";
                strSQL+=" WHERE ";
                strSQL+=" a3.co_emp=" + objParSis.getCodigoEmpresa() + "";
                strSQL+=" AND a3.co_loc=" + objParSis.getCodigoLocal() + "";
                strSQL+=" AND a3.co_mnu=" + objParSis.getCodigoMenu() + "";
                strSQL+=" AND a3.co_usr=" + objParSis.getCodigoUsuario() + "";
            }
            //Ocultar columnas.
            int intColOcu[]=new int[2];
            intColOcu[0]=5;
            intColOcu[1]=6;
            vcoTipDoc=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objParSis, "Listado de tipos de documentos", strSQL, arlCam, arlAli, arlAncCol, intColOcu);
            arlCam=null;
            arlAli=null;
            arlAncCol=null;
            intColOcu=null;
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
     * Esta función permite utilizar la "Ventana de Consulta" para seleccionar un
     * registro de la base de datos. El tipo de básqueda determina si se debe hacer
     * una básqueda directa (No se muestra la ventana de consulta a menos que no
     * exista lo que se está buscando) o presentar la ventana de consulta para que
     * el usuario seleccione la opcián que desea utilizar.
     * @param intTipBus El tipo de básqueda a realizar.
     * @return true: Si no se presentá ningán problema.
     * <BR>false: En el caso contrario.
     */
    private boolean mostrarVenConTipDoc(int intTipBus){
        boolean blnRes=true;
        try{            
            switch (intTipBus){
                case 0: //Mostrar la ventana de consulta.
                    vcoTipDoc.setCampoBusqueda(1);
                    vcoTipDoc.show();
                    if (vcoTipDoc.getSelectedButton()==vcoTipDoc.INT_BUT_ACE){
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
                case 3: //Básqueda directa por "Descripcián corta".
                    if (vcoTipDoc.buscar("a1.co_tipDoc", txtCodTipDoc.getText()))
                    {
                        txtCodTipDoc.setText(vcoTipDoc.getValueAt(1));
                        txtDesCorTipDoc.setText(vcoTipDoc.getValueAt(2));
                        txtDesLarTipDoc.setText(vcoTipDoc.getValueAt(3));
                    }
                    break;
            }  
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }


    /**
     * Función que permite conocer si la columna del visto bueno seleccionado es editable o no es editable
     * @param columna Es la columna que se desea validar la edición
     * @return 
     */
    private boolean isValCelEdi(int columna){
        boolean blnRes=false;
        int intCodVisBueUsrPerDb=-1, intCodVisBueTbl=-1;
        int intColArl=-1, intColTbl=columna;
        try{
            intCodVisBueUsrPerDb=getCodVisBueUsr();
            for(int i=0; i<arlDatVisBueAutDen.size(); i++){
                intCodVisBueTbl=objUti.getIntValueAt(arlDatVisBueAutDen, i, INT_ARL_COD_VIS_BUE_AUT_DEN);
                intColArl=objUti.getIntValueAt(arlDatVisBueAutDen, i, INT_ARL_COL_VIS_BUE_AUT_DEN);                
                if(intCodVisBueUsrPerDb==intCodVisBueTbl){
                    if(intColArl==intColTbl){
                        blnRes=true;
                        break;
                    }
                }
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    /**
     * Función que permite conocer si la columna del visto bueno seleccionado es editable o no es editable dependiendo que el visto bueno previo se haya concedido
     * @param columna Es la columna que se desea validar la edición
     * @return 
     */
    private boolean isValCelEdiVisBuePre(int fila, int columna){
        boolean blnRes=false;
        String strNecVisBuePreUsr="";//, intCodVisBueTbl=-1;
        int intFilSelVisBue=fila;
        int intColSelVisBue=columna;
        try{
            strNecVisBuePreUsr=getNecVisBuePreUsr();
            if(strNecVisBuePreUsr.equals("S")){
                if(objTblMod.isChecked(intFilSelVisBue, (intColSelVisBue-1))){//Columna Denegar del Visto Bueno anterior
                    blnRes=true;
                }
                if(objTblMod.isChecked(intFilSelVisBue, (intColSelVisBue-2))){//Columna Autorizar del Visto Bueno anterior
                    blnRes=true;
                }
            }
            else{
                blnRes=true;
            }
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    

   /**
    * Función que devuelve el codigo del visto bueno asociado a un usuario
    * @return true Si se pudo cargar la información
    * <BR> false Caso contrario
    */
   private int getCodVisBueUsr(){
       Connection conCodVisBueUsr;
       Statement stmCodVisBueUsr;
       ResultSet rstCodVisBueUsr;
       try{
           conCodVisBueUsr=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conCodVisBueUsr!=null){
               stmCodVisBueUsr=conCodVisBueUsr.createStatement();
               strSQL="";
               strSQL+=" SELECT a1.co_visBue, a1.co_usr, a1.st_necVisBuePre, a1.nd_valAut";
               strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
               strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" AND a1.st_reg='A'";
               System.out.println("getCodVisBueUsr: " + strSQL);
               rstCodVisBueUsr=stmCodVisBueUsr.executeQuery(strSQL);
               while(rstCodVisBueUsr.next()){
                   intCodVisBueTipDocUsr=rstCodVisBueUsr.getInt("co_visBue");
                   System.out.println("intCodVisBueTipDocUsr: " + intCodVisBueTipDocUsr);
               }
               stmCodVisBueUsr.close();
               stmCodVisBueUsr=null;
               rstCodVisBueUsr.close();
               rstCodVisBueUsr=null;
               conCodVisBueUsr.close();
               conCodVisBueUsr=null;
           }
           
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return intCodVisBueTipDocUsr;
   }

   
   /**
    * Función que devuelve datos asociados al visto bueno de usuario
    * @return true Si se pudo cargar la información
    * <BR> false Caso contrario
    */
   private String getNecVisBuePreUsr(){
       Connection conCodVisBueUsr;
       Statement stmCodVisBueUsr;
       ResultSet rstCodVisBueUsr;
       String strNecVisBuePreUsr="";
       try{
           conCodVisBueUsr=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
           if(conCodVisBueUsr!=null){
               stmCodVisBueUsr=conCodVisBueUsr.createStatement();
               strSQL="";
               strSQL+=" SELECT a1.co_visBue, a1.co_usr, a1.st_necVisBuePre, a1.nd_valAut";
               strSQL+=" FROM tbr_visBueUsrTipDoc AS a1";
               strSQL+=" WHERE a1.co_emp=" + objParSis.getCodigoEmpresa() + "";
               strSQL+=" AND a1.co_loc=" + objParSis.getCodigoLocal() + "";
               strSQL+=" AND a1.co_tipDoc=" + txtCodTipDoc.getText() + "";
               strSQL+=" AND a1.co_usr=" + objParSis.getCodigoUsuario() + "";
               strSQL+=" AND a1.st_reg='A'";
               System.out.println("getCodVisBueUsr: " + strSQL);
               rstCodVisBueUsr=stmCodVisBueUsr.executeQuery(strSQL);
               while(rstCodVisBueUsr.next()){
                   strNecVisBuePreUsr=rstCodVisBueUsr.getString("st_necVisBuePre");
                   System.out.println("strNecVisBuePreUsr: " + strNecVisBuePreUsr);
               }
               stmCodVisBueUsr.close();
               stmCodVisBueUsr=null;
               rstCodVisBueUsr.close();
               rstCodVisBueUsr=null;
               conCodVisBueUsr.close();
               conCodVisBueUsr=null;
           }
           
       }
       catch(java.sql.SQLException e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       catch(Exception e){
           objUti.mostrarMsgErr_F1(this, e);
       }
       return strNecVisBuePreUsr;
   }
   
   
   
   
   private boolean cargarVisBueTipDoc(){
        boolean blnRes=true;
        int intCodVisBueTbl=-1, intCodVisBueDb=-1;
        String strEstAutDenDb="";
        int intColAdi=-1;
        try{
            if(con!=null){
                stm=con.createStatement();
                for(int h=0; h<objTblMod.getRowCountTrue(); h++){
                    arlDatVisBueMovInv.clear();
                    
                    strSQL="";
                    strSQL+=" SELECT a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc, a1.co_visBue, a1.st_reg";
                    strSQL+=" FROM tbm_visBueMovInv AS a1";
                    strSQL+=" WHERE a1.co_emp=" + objTblMod.getValueAt(h, INT_TBL_DAT_COD_EMP_AJU) + " AND a1.co_loc=" + objTblMod.getValueAt(h, INT_TBL_DAT_COD_LOC_AJU) + "";
                    strSQL+=" AND a1.co_tipDoc=" + objTblMod.getValueAt(h, INT_TBL_DAT_COD_TIP_DOC_AJU) + " AND a1.co_doc=" + objTblMod.getValueAt(h, INT_TBL_DAT_COD_DOC_AJU) + "";
                    //System.out.println("getVisBueTipDoc: " + strSQL);
                    rst=stm.executeQuery(strSQL);
                    while(rst.next()){
                        arlRegVisBueMovInv=new ArrayList();
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_COD_EMP, rst.getInt("co_emp"));
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_COD_LOC, rst.getInt("co_loc"));
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_COD_TIP_DOC, rst.getInt("co_tipDoc"));
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_COD_DOC, rst.getInt("co_doc"));
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_COD_VIS_BUE, rst.getInt("co_visBue"));
                        arlRegVisBueMovInv.add(INT_ARL_VIS_BUE_MOV_INV_EST_VIS_BUE, rst.getString("st_reg"));
                        arlDatVisBueMovInv.add(arlRegVisBueMovInv);
                    }
                    
                    for(int i=0; i<arlDatVisBueMovInv.size(); i++){//informacion de la base de datos
                        intCodVisBueDb=objUti.getIntValueAt(arlDatVisBueMovInv, i, INT_ARL_VIS_BUE_MOV_INV_COD_VIS_BUE);//Visto bueno de la base de datos
                        strEstAutDenDb=objUti.getStringValueAt(arlDatVisBueMovInv, i, INT_ARL_VIS_BUE_MOV_INV_EST_VIS_BUE);//Visto bueno de la base de datos

                        for(int j=0; j<arlDatVisBueAutDen.size(); j++){//informacion de columnas adicionadas
                            intCodVisBueTbl=objUti.getIntValueAt(arlDatVisBueAutDen, j, INT_ARL_COD_VIS_BUE_AUT_DEN);
                            intColAdi=objUti.getIntValueAt(arlDatVisBueAutDen, j, INT_ARL_COL_VIS_BUE_AUT_DEN);

                            if(intCodVisBueDb==intCodVisBueTbl){
                                //Preguntar si está "Autorizado o Denegado"
                                if(strEstAutDenDb.equals("A")){
                                    objTblMod.setChecked(true, h, (intColAdi+1));
                                }
                                else if(strEstAutDenDb.equals("D")){
                                    objTblMod.setChecked(true, h, (intColAdi+2));
                                }
                                else
                                    blnRes=false;
                                break;
                            }
                        }
                    }
                    rst.close();
                    rst=null;
                }
                stm.close();
                stm=null;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
   }
   
 
   
   private boolean abrirCnx(){
        boolean blnRes=false;
        try{
            System.out.println("abrirCnx");
            conAju=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conAju!=null){
                conAju.setAutoCommit(false);
                blnRes=true;
            }           
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
   }
   
   /**
    * Función que permite ejecutar las sentencias SQL que se encuentran en la conexión enviada al JDialogo
    * @param opcion
    * @return 
    */
   private boolean guardarCnx(){
        boolean blnRes=false;
        try{
            System.out.println("guardarCnx");
            
            if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_ACE){
                System.out.println("ACEPTAR - COMMIT");
                conAju.commit();
                blnRes=true;                
            }
            else if(objAjuInv.getSelectedButton()==objAjuInv.INT_BUT_DEN){
                System.out.println("DENEGAR - COMMIT");
                conAju.commit();
                blnRes=true;
            }
            else{
                System.out.println("CERRAR");
                blnRes=true;
            }
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
   }
   
   
   private boolean cerrarCnx(){
        boolean blnRes=false;
        try{
            System.out.println("cerrarCnx");
            if(conAju!=null){
                conAju.close();
                conAju=null;
                blnRes=true;
            }           
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(this, e);
            blnRes=false;
        }
        return blnRes;
   }
   
   
}