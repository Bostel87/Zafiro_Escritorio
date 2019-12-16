/*
 * ZafVen25.java
 *
 * Created on 06 de Agosto de 2008 st_recMerDev
 */ 

package Maestros.ZafMae54;
import Librerias.ZafToolBar.ZafToolBar;
import Librerias.ZafUtil.ZafUtil; /**/
import java.util.Vector;
import Librerias.ZafVenCon.ZafVenCon;  //**************************
import java.util.ArrayList;
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblBus.ZafTblBus;
/**
 *
 * @author  jayapata
 */
public class ZafMae54 extends javax.swing.JInternalFrame {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    mitoolbar objTooBar;
    ZafUtil objUti; /**/
    String strAux="";
    java.sql.Connection CONN_GLO=null;

    ZafVenCon objVenConCla, objVenConGrp; //*****************
    
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblModCla;
    private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk  objTblRenChk, objTblRenChkClaA, objTblRenChkClaI;        //Render: Presentar JButton en JTable.
    private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk  objTblEdiChk, objTblEdiChkClaA, objTblEdiChkClaI;        //Editor: JButton en celda.

   String strCodCla="", strDesCla="";
   String strCodGrp="";
   String strDesGrp="";

   // DATOS DE LA TABLA DE CLASIFICACIONES
   final int INT_TBL_CLALIN=0;
   final int INT_TBL_CHKITM=1;
   final int INT_TBL_CODITM=2;
   final int INT_TBL_CODALT=3;
   final int INT_TBL_NOMITM=4;
   final int INT_TBL_CLAACT=5;
   final int INT_TBL_CLAINA=6;
   final int INT_TBL_CODREGCLA=7;
   //Constantes del ArrayList Elementos Eliminados
   final int INT_ARR_CODCLA   = 0;
   //****************************
    
    /** Creates new form ZafCom33 */
    public ZafMae54(Librerias.ZafParSis.ZafParSis obj) {
        try{ /**/
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
	    
         this.setTitle(objZafParSis.getNombreMenu()+"  v 0.3 "); /**/
         lblTit.setText(objZafParSis.getNombreMenu());  /**/
        

	     objUti = new ZafUtil(); /**/
	     objTooBar = new mitoolbar(this);
	     this.getContentPane().add(objTooBar,"South");

          if( objZafParSis.getTipGrpClaInvPreUsr()=='P')
           radPub.setSelected(true);

         if( objZafParSis.getTipGrpClaInvPreUsr()=='R')
           radPri.setSelected(true);

         
         objTooBar.setVisibleAnular(false);
         objTooBar.setVisibleEliminar(false);
         objTooBar.setVisibleInsertar(false);

	 }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }  /**/
    }
    



private boolean configurarVenConGrp() {
 boolean blnRes=true;
 try {
    ArrayList arlCam=new ArrayList();
    arlCam.add("co_grp");
    arlCam.add("tx_descor");
    arlCam.add("tx_deslar");

    ArrayList arlAli=new ArrayList();
    arlAli.add("Cód.Grp");
    arlAli.add("Des.Cor.");
    arlAli.add("Des.Lar.");

    ArrayList arlAncCol=new ArrayList();
    arlAncCol.add("80");
    arlAncCol.add("110");
    arlAncCol.add("350");

  //Armar la sentencia SQL.   a7.nd_stkTot,
  String Str_Sql="";
  Str_Sql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A' ";
  objVenConGrp=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu(), Str_Sql, arlCam, arlAli, arlAncCol);
  arlCam=null;
  arlAli=null;
  arlAncCol=null;

 }catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}

 
private void ConfigurarTblClasificacion() {
  try{
    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();
    vecCab.add(INT_TBL_CLALIN,"");
    vecCab.add(INT_TBL_CHKITM,"..");
    vecCab.add(INT_TBL_CODITM,"Cod.Itm");
    vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
    vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
    vecCab.add(INT_TBL_CLAACT,"Activo.");
    vecCab.add(INT_TBL_CLAINA,"Inactivo");
    vecCab.add(INT_TBL_CODREGCLA,"Cod.Reg.");

    objTblModCla=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblModCla.setHeader(vecCab);
    tblCla.setModel(objTblModCla);
    tblCla.setRowSelectionAllowed(true);
    //tblCla.setCellSelectionEnabled(true);
    tblCla.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
    new Librerias.ZafColNumerada.ZafColNumerada(tblCla,INT_TBL_CLALIN);

    //tblCla.getModel().addTableModelListener(new LisCambioTblAcc());
    tblCla.getTableHeader().setReorderingAllowed(false);


    tblCla.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblCla.getColumnModel();

    tcmAux.getColumn(INT_TBL_CLALIN).setPreferredWidth(30);
    tcmAux.getColumn(INT_TBL_CHKITM).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(400);
    tcmAux.getColumn(INT_TBL_CLAACT).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_CLAINA).setPreferredWidth(50);

    objTblRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CHKITM).setCellRenderer(objTblRenChk);
    objTblEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHKITM).setCellEditor(objTblEdiChk);
    objTblEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
            if( tblCla.getValueAt( intNumFil, INT_TBL_CHKITM).toString().equals("true") ){
             tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CLAACT);
            }else{
               tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAACT);
               tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAINA);
             }
        }
    });


    objTblRenChkClaA = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    objTblRenChkClaI = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CLAACT).setCellRenderer(objTblRenChkClaA);
    tcmAux.getColumn(INT_TBL_CLAINA).setCellRenderer(objTblRenChkClaI);

    objTblEdiChkClaA = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CLAACT).setCellEditor(objTblEdiChkClaA);

    objTblEdiChkClaI = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CLAINA).setCellEditor(objTblEdiChkClaI);


     objTblEdiChkClaA.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CLAACT);
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAINA);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CHKITM);
         }
    });

    objTblEdiChkClaI.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
        }

        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
            int intNumFil = tblCla.getSelectedRow();
              tblCla.setValueAt(new Boolean(false), intNumFil, INT_TBL_CLAACT);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CLAINA);
              tblCla.setValueAt(new Boolean(true), intNumFil, INT_TBL_CHKITM);

        }
    });


     java.util.ArrayList arlAux=new java.util.ArrayList();
    //Configurar ZafTblMod: Establecer las columnas ELIMINADAS
    arlAux=new java.util.ArrayList();
    arlAux.add("" + INT_TBL_CODREGCLA);
    objTblModCla.setColsSaveBeforeRemoveRow(arlAux);
    arlAux=null;

      ArrayList arlColHid=new ArrayList();
      arlColHid.add(""+INT_TBL_CODREGCLA);
      arlColHid.add(""+INT_TBL_CODITM);
      objTblModCla.setSystemHiddenColumns(arlColHid, tblCla);
      arlColHid=null;

    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_CHKITM);
    vecAux.add("" + INT_TBL_CLAACT);
    vecAux.add("" + INT_TBL_CLAINA);
    objTblModCla.setColumnasEditables(vecAux);
    vecAux=null;

    new ZafTblBus(tblCla);
    new ZafTblOrd(tblCla);

    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblCla);
    tcmAux=null;
    objTblModCla.setModoOperacion(objTblModCla.INT_TBL_EDI);

    new Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu(tblCla);

  }catch(Exception e) {   objUti.mostrarMsgErr_F1(this, e); }
}

private boolean configurarVenConClasificacion() {
    boolean blnRes=true;
    try {
        ArrayList arlCam=new ArrayList();
        arlCam.add("co_grp");
        arlCam.add("nomgrp");
        arlCam.add("co_cla");
        arlCam.add("nomcla");
        ArrayList arlAli=new ArrayList();
        arlAli.add("Cód.Grp");
        arlAli.add("Nom.Grp.");
        arlAli.add("Cód.Cla");
        arlAli.add("Nom.Cla.");
        ArrayList arlAncCol=new ArrayList();
        arlAncCol.add("50");
        arlAncCol.add("200");
        arlAncCol.add("50");
        arlAncCol.add("200");
        //Armar la sentencia SQL.
        String  strSQL="";
        strSQL="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
        " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
        " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  ) AS x ";
        objVenConCla=new ZafVenCon(javax.swing.JOptionPane.getFrameForComponent(this), objZafParSis, objZafParSis.getNombreMenu() , strSQL, arlCam, arlAli, arlAncCol );
        arlCam=null;
        arlAli=null;
        arlAncCol=null;

    }
    catch (Exception e) {
        blnRes=false;
        objUti.mostrarMsgErr_F1(this, e);
    }
    return blnRes;
}

   



public void abrirCon(){
try{
    CONN_GLO=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
}
catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}




public void CerrarCon(){
try{
    CONN_GLO.close();
    CONN_GLO=null;
}
catch(java.sql.SQLException  Evt){  objUti.mostrarMsgErr_F1(this, Evt); }
}


    
    
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grpRdaBut = new javax.swing.ButtonGroup();
        panNor = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        panCen = new javax.swing.JPanel();
        tabGen = new javax.swing.JTabbedPane();
        panGenTabGen = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        txtCodCla = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        butCla = new javax.swing.JButton();
        txtDesCla = new javax.swing.JTextField();
        lbltipgrp = new javax.swing.JLabel();
        radPub = new javax.swing.JRadioButton();
        radPri = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        txtCodGrp = new javax.swing.JTextField();
        txtDesGrp = new javax.swing.JTextField();
        butSol = new javax.swing.JButton();
        panGenDat = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCla = new javax.swing.JTable();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
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
                formInternalFrameOpened(evt);
            }
        });

        panNor.setLayout(new java.awt.BorderLayout());

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("titulo"); // NOI18N
        panNor.add(lblTit, java.awt.BorderLayout.CENTER);

        getContentPane().add(panNor, java.awt.BorderLayout.NORTH);

        panCen.setLayout(new java.awt.BorderLayout());

        panGenTabGen.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(100, 75));
        panGenCab.setLayout(null);

        txtCodCla.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodClaActionPerformed(evt);
            }
        });
        txtCodCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodClaFocusLost(evt);
            }
        });
        panGenCab.add(txtCodCla);
        txtCodCla.setBounds(110, 50, 70, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel2.setText("Clasificación:"); // NOI18N
        panGenCab.add(jLabel2);
        jLabel2.setBounds(10, 50, 90, 20);

        butCla.setText(".."); // NOI18N
        butCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butClaActionPerformed(evt);
            }
        });
        panGenCab.add(butCla);
        butCla.setBounds(490, 50, 20, 20);

        txtDesCla.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesCla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesClaActionPerformed(evt);
            }
        });
        txtDesCla.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesClaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesClaFocusLost(evt);
            }
        });
        panGenCab.add(txtDesCla);
        txtDesCla.setBounds(180, 50, 310, 20);

        lbltipgrp.setFont(new java.awt.Font("SansSerif", 0, 11));
        lbltipgrp.setText("Tipo de grupo:");
        panGenCab.add(lbltipgrp);
        lbltipgrp.setBounds(10, 10, 100, 20);

        grpRdaBut.add(radPub);
        radPub.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPub.setText("Público");
        radPub.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPubActionPerformed(evt);
            }
        });
        panGenCab.add(radPub);
        radPub.setBounds(110, 10, 70, 20);

        grpRdaBut.add(radPri);
        radPri.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        radPri.setText("Privado");
        radPri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radPriActionPerformed(evt);
            }
        });
        panGenCab.add(radPri);
        radPri.setBounds(180, 10, 110, 20);

        jLabel3.setFont(new java.awt.Font("SansSerif", 0, 11));
        jLabel3.setText("Grupo:"); // NOI18N
        panGenCab.add(jLabel3);
        jLabel3.setBounds(10, 30, 90, 20);

        txtCodGrp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtCodGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCodGrpActionPerformed(evt);
            }
        });
        txtCodGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCodGrpFocusLost(evt);
            }
        });
        panGenCab.add(txtCodGrp);
        txtCodGrp.setBounds(110, 30, 70, 20);

        txtDesGrp.setBackground(objZafParSis.getColorCamposObligatorios());
        txtDesGrp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDesGrpActionPerformed(evt);
            }
        });
        txtDesGrp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDesGrpFocusLost(evt);
            }
        });
        panGenCab.add(txtDesGrp);
        txtDesGrp.setBounds(180, 30, 310, 20);

        butSol.setText(".."); // NOI18N
        butSol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butSolActionPerformed(evt);
            }
        });
        panGenCab.add(butSol);
        butSol.setBounds(490, 30, 20, 20);

        panGenTabGen.add(panGenCab, java.awt.BorderLayout.NORTH);

        panGenDat.setLayout(new java.awt.BorderLayout());

        tblCla.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tblCla);

        panGenDat.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        panGenTabGen.add(panGenDat, java.awt.BorderLayout.CENTER);

        tabGen.addTab("General", panGenTabGen);

        panCen.add(tabGen, java.awt.BorderLayout.CENTER);

        getContentPane().add(panCen, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
	// TODO add your handling code here:
    }//GEN-LAST:event_formInternalFrameClosed

    private void formInternalFrameClosing(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosing
	// TODO add your handling code here:
        String strMsg = "¿Está Seguro que desea cerrar este programa?";
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        if(oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
           // cerrarObj();
            System.gc();
            dispose();
        }
    }//GEN-LAST:event_formInternalFrameClosing

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
	// TODO add your handling code here:
	   configurarVenConClasificacion();
        
        ConfigurarTblClasificacion();
        configurarVenConGrp();
        

    }//GEN-LAST:event_formInternalFrameOpened

    private void txtCodClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodClaActionPerformed
        // TODO add your handling code here:
        txtCodCla.transferFocus();
}//GEN-LAST:event_txtCodClaActionPerformed

    private void txtCodClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusGained
        // TODO add your handling code here:
        strCodCla=txtCodCla.getText();
        txtCodCla.selectAll();
}//GEN-LAST:event_txtCodClaFocusGained

    private void txtCodClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodClaFocusLost
        // TODO add your handling code here:
        if (!txtCodCla.getText().equalsIgnoreCase(strCodCla)) {
            if(txtCodCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCla.setText("");
            }else
                BuscarCla("co_cla",txtCodCla.getText(),2);
        }else
            txtCodCla.setText(strCodCla);
}//GEN-LAST:event_txtCodClaFocusLost

    private void txtDesClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesClaActionPerformed
        // TODO add your handling code here:
        txtDesCla.transferFocus();
}//GEN-LAST:event_txtDesClaActionPerformed

    private void txtDesClaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesClaFocusGained
        // TODO add your handling code here:
        strDesCla=txtDesCla.getText();
        txtDesCla.selectAll();
}//GEN-LAST:event_txtDesClaFocusGained

    private void txtDesClaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesClaFocusLost
        // TODO add your handling code here:
        if (!txtDesCla.getText().equalsIgnoreCase(strDesCla)) {
            if(txtDesCla.getText().equals("")) {
                txtCodCla.setText("");
                txtDesCla.setText("");
            }else
                BuscarCla("tx_deslar",txtDesCla.getText(),3);
        }else
            txtDesCla.setText(strDesCla);
}//GEN-LAST:event_txtDesClaFocusLost

    private void butClaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butClaActionPerformed
        // TODO add your handling code here:


   String strSql="";
   if(!txtCodGrp.getText().equals("")){
      strSql="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
      " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  and a1.co_grp="+txtCodGrp.getText()+"  ) AS x ";
 
        objVenConCla.setTitle("Listado de Clientes");
        objVenConCla.setSentenciaSQL(strSql);
        objVenConCla.setCampoBusqueda(1);
        objVenConCla.cargarDatos();
        objVenConCla.show();
        if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
            txtCodCla.setText(objVenConCla.getValueAt(3));
            txtDesCla.setText(objVenConCla.getValueAt(4));
           
        }
   }

}//GEN-LAST:event_butClaActionPerformed

    private void radPubActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPubActionPerformed
        // TODO add your handling code here:
        txtCodGrp.setText("");
        txtDesGrp.setText("");
        txtCodCla.setText("");
       txtDesCla.setText("");
    }//GEN-LAST:event_radPubActionPerformed

    private void radPriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radPriActionPerformed
        // TODO add your handling code here:
        txtCodGrp.setText("");
        txtDesGrp.setText("");
        txtCodCla.setText("");
       txtDesCla.setText("");

}//GEN-LAST:event_radPriActionPerformed

    private void txtCodGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCodGrpActionPerformed
        // TODO add your handling code here:
        txtCodGrp.transferFocus();
}//GEN-LAST:event_txtCodGrpActionPerformed

    private void txtCodGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusGained
        // TODO add your handling code here:
        strCodGrp=txtCodGrp.getText();
        txtCodGrp.selectAll();
}//GEN-LAST:event_txtCodGrpFocusGained

    private void txtCodGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCodGrpFocusLost
        // TODO add your handling code here:
        if (!txtCodGrp.getText().equalsIgnoreCase(strCodGrp)) {
            if(txtCodGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("co_grp",txtCodGrp.getText(),0);
        }else
            txtCodGrp.setText(strCodGrp);
}//GEN-LAST:event_txtCodGrpFocusLost

    private void txtDesGrpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDesGrpActionPerformed
        // TODO add your handling code here:
        txtDesGrp.transferFocus();
}//GEN-LAST:event_txtDesGrpActionPerformed

    private void txtDesGrpFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusGained
        // TODO add your handling code here:
        strDesGrp=txtDesGrp.getText();
        txtDesGrp.selectAll();
}//GEN-LAST:event_txtDesGrpFocusGained

    private void txtDesGrpFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDesGrpFocusLost
        // TODO add your handling code here:
        if (!txtDesGrp.getText().equalsIgnoreCase(strDesGrp)) {
            if(txtDesGrp.getText().equals("")) {
                txtCodGrp.setText("");
                txtDesGrp.setText("");
            }else
                BuscarGrp("tx_deslar",txtDesGrp.getText(),2);
        }else
            txtDesGrp.setText(strDesGrp);
}//GEN-LAST:event_txtDesGrpFocusLost

    private void butSolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butSolActionPerformed
        // TODO add your handling code here:

        String strSql="";
        if(radPub.isSelected())
            strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
                    " and tx_tipgrp='P' ";

        if(radPri.isSelected())
            strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
                    " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
                    " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

        objVenConGrp.setTitle("Listado de Clientes");
        objVenConGrp.setSentenciaSQL(strSql);
        objVenConGrp.setCampoBusqueda(1);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
             txtCodCla.setText("");
             txtDesCla.setText("");
        }
}//GEN-LAST:event_butSolActionPerformed




 public void BuscarGrp(String campo,String strBusqueda,int tipo){
  objVenConGrp.setTitle("Listado de Vendedores");

  String strSql="";
   if(radPub.isSelected())
      strSql="SELECT co_grp, tx_descor, tx_deslar FROM tbm_grpclainv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND st_reg='A'" +
      " and tx_tipgrp='P' ";

  if(radPri.isSelected())
     strSql="SELECT a1.co_grp, a1.tx_descor, a1.tx_deslar FROM tbr_grpclainvusr as a " +
     " inner join tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" AND a1.st_reg='A' and a.co_usr="+objZafParSis.getCodigoUsuario()+" ";

  objVenConGrp.setSentenciaSQL(strSql);
  objVenConGrp.cargarDatos();


  if(objVenConGrp.buscar(campo, strBusqueda )) {
      txtCodGrp.setText(objVenConGrp.getValueAt(1));
      txtDesGrp.setText(objVenConGrp.getValueAt(3));
       txtCodCla.setText("");
       txtDesCla.setText("");
  }else{
        objVenConGrp.setCampoBusqueda(tipo);
        objVenConGrp.cargarDatos();
        objVenConGrp.show();
        if (objVenConGrp.getSelectedButton()==objVenConGrp.INT_BUT_ACE) {
            txtCodGrp.setText(objVenConGrp.getValueAt(1));
            txtDesGrp.setText(objVenConGrp.getValueAt(3));
             txtCodCla.setText("");
       txtDesCla.setText("");
        }else{
            txtCodGrp.setText(strCodGrp);
            txtDesGrp.setText(strDesGrp);
             txtCodCla.setText("");
       txtDesCla.setText("");
  }}}



 public void BuscarCla(String campo,String strBusqueda,int tipo){

   String strSql="";
   if(!txtCodGrp.getText().equals("")){
      strSql="SELECT * FROM( SELECT a.co_grp, a1.tx_deslar as nomgrp, a.co_cla, a.tx_deslar as nomcla FROM tbm_clainv as a " +
      " inner JOIN tbm_grpclainv as a1 on (a1.co_emp=a.co_emp and a1.co_grp=a.co_grp) " +
      " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+" and  a.st_reg not in ('E')  and a1.co_grp="+txtCodGrp.getText()+"  ) AS x ";

     
  objVenConCla.setTitle("Listado de Clasificación");

  objVenConCla.setSentenciaSQL(strSql);
  objVenConCla.cargarDatos();
  

  if(objVenConCla.buscar(campo, strBusqueda )) {
       txtCodCla.setText(objVenConCla.getValueAt(3));
       txtDesCla.setText(objVenConCla.getValueAt(4));
       
  }else{
        objVenConCla.setCampoBusqueda(tipo);
        objVenConCla.cargarDatos();
        objVenConCla.show();
        if (objVenConCla.getSelectedButton()==objVenConCla.INT_BUT_ACE) {
             txtCodCla.setText(objVenConCla.getValueAt(3));
             txtDesCla.setText(objVenConCla.getValueAt(4));
             
        }else{
            txtCodCla.setText(strCodCla);
            txtDesCla.setText(strDesCla);
  }}}else{
       txtCodCla.setText("");
       txtDesCla.setText("");
  }}



private boolean cargarTabCla(){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.ResultSet rst;
 java.sql.Statement stm;
 String strSql="";
 String strAuxInv="";
 String strConInv="";
 try{
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
     stm=conn.createStatement();

    if(objZafParSis.getCodigoUsuario()!=1){
        strAuxInv = ",CASE WHEN (" +
        " (trim(SUBSTR (UPPER(tx_codalt), length(tx_codalt) ,1))  IN (" +
        " SELECT UPPER(trim(tx_cad))  FROM tbm_reginv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_loc="+objZafParSis.getCodigoLocal()+" " +
        " AND co_tipdoc= 2   AND co_usr="+objZafParSis.getCodigoUsuario()+" AND st_reg='A' " +
        " ))) THEN 'S' ELSE 'N' END  as isterL";

        strConInv=" WHERE isterL='S' ";
    }

     strSql="SELECT * FROM ( SELECT * "+strAuxInv+" FROM ( SELECT a.co_emp, a.co_itm, a.tx_codalt, a.tx_nomitm " +
     " ,CASE WHEN a1.st_reg IN ('A') THEN 'S' ELSE 'N' END AS estact " +
     " ,CASE WHEN a1.st_reg IN ('I') THEN 'S' ELSE 'N' END AS estIna " +
     " ,CASE WHEN a1.co_cla IS NULL THEN 'N' ELSE 'S' END AS est " +
     " FROM tbm_inv AS a " +
     " LEFT JOIN tbr_invcla as a1 on (a1.co_emp=a.co_emp and a1.co_itm=a.co_itm and a1.co_cla="+txtCodCla.getText()+" ) " +
     " WHERE a.co_emp="+objZafParSis.getCodigoEmpresa()+"  and a.st_reg NOT IN ('T','U') ORDER BY a.tx_codalt " +
     " ) AS x ) AS x  "+strConInv;


     rst = stm.executeQuery(strSql);
     Vector vecData = new Vector();
     while(rst.next()){
         java.util.Vector vecReg = new java.util.Vector();
         vecReg.add(INT_TBL_CLALIN, "");
         vecReg.add(INT_TBL_CHKITM, new Boolean( rst.getString("est").equals("S")) );
         vecReg.add(INT_TBL_CODITM, rst.getString("co_itm") );
         vecReg.add(INT_TBL_CODALT, rst.getString("tx_codalt") );
         vecReg.add(INT_TBL_NOMITM, rst.getString("tx_nomitm") );
         vecReg.add(INT_TBL_CLAACT, new Boolean( rst.getString("estact").equals("S")) );
         vecReg.add(INT_TBL_CLAINA, new Boolean( rst.getString("estIna").equals("S")) );
         vecReg.add(INT_TBL_CODREGCLA, null );
        vecData.add(vecReg);
     }
     objTblModCla.setData(vecData);
     tblCla.setModel(objTblModCla);
    rst.close();
    rst=null;
    stm.close();
    stm=null;
    conn.close();
    conn=null;
    
 }}catch (java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
   catch (Exception e){ blnRes=false; objUti.mostrarMsgErr_F1(this, e); }
 return blnRes;
}



    
  
    
    
 
     
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCla;
    private javax.swing.JButton butSol;
    private javax.swing.ButtonGroup grpRdaBut;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JLabel lbltipgrp;
    private javax.swing.JPanel panCen;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JPanel panGenDat;
    private javax.swing.JPanel panGenTabGen;
    private javax.swing.JPanel panNor;
    private javax.swing.JRadioButton radPri;
    private javax.swing.JRadioButton radPub;
    private javax.swing.JTabbedPane tabGen;
    private javax.swing.JTable tblCla;
    private javax.swing.JTextField txtCodCla;
    private javax.swing.JTextField txtCodGrp;
    private javax.swing.JTextField txtDesCla;
    private javax.swing.JTextField txtDesGrp;
    // End of variables declaration//GEN-END:variables
    
      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    
    
      
      

public class mitoolbar extends ZafToolBar{
 public mitoolbar(javax.swing.JInternalFrame jfrThis){
    super(jfrThis, objZafParSis);
 }

	
public boolean anular() {
  boolean blnRes=false;

  return blnRes;
}

	
      
     
	
        public void clickAceptar() {
            setEstadoBotonMakeFac();
        }
        
	
	
public void clickAnterior() {
 try{
      refrescaDatos();
  } catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}
        


        
        public void clickAnular() {

        }
        
        
        public void clickConsultar() {
            clnTextos();
         
	}
        
   
        
        
        
public void clickFin(){
 try{
      refrescaDatos();
  } catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}
        

 



public void clickInicio(){
try{
      refrescaDatos();
  } catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}




public void clickInsertar() {

}

        
        
        
        public void setEstadoBotonMakeFac(){
            switch(getEstado()) {
                case 'l'://Estado 0 => Listo
                    break;
                case 'x'://Estado click modificar
                    break;
                case 'c'://Estado Consultar
                    break;
                case 'y':
                    break;
                case 'z':
                    break;
                default:
                    break;
            }
        }
          
        
        
public void clickSiguiente(){ 
 try{
      refrescaDatos();
  } catch (Exception e){ objUti.mostrarMsgErr_F1(this, e); }
}

        


public void clickEliminar() {

}
      
       
        
        
public boolean eliminar() {
  boolean blnRes=false;
   return blnRes;
}

        
    
         
      
        

 private boolean validaCampos(){
  
  if(txtCodCla.getText().equals("") ){
    tabGen.setSelectedIndex(0);
    MensajeInf("El campo <<  Clasificación  >> es obligatorio.\nEscoja y vuelva a intentarlo.");
    txtCodCla.requestFocus();
    return false;
    }
                 
             
             
   return true;  
 }
        
 
 
 
 
        
        
public boolean insertar() {
  boolean blnRes=false;
 return blnRes;
}


 
        
 

    
       
   



public boolean modificar(){
  boolean blnRes=false;
  java.sql.Connection conn;
  try{
    if(validaCampos()){
      conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
      conn.setAutoCommit(false);

          if(actualizarClaInv(conn, txtCodCla.getText(), txtCodGrp.getText()  )){
            conn.commit();
            blnRes=true;
          }else conn.rollback();

       conn.close();
       conn=null;
     }

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception  Evt){ blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}

//public boolean actualizarClaInv(java.sql.Connection conMod, String strCodCla ){
// boolean blnRes=true;
// java.sql.Statement stm;
// String strEst="A";
// String strSql="";
// int intStrBuf=0;
// StringBuffer stbSqlCla;
// try{
// if(conMod!=null){
//  stm=conMod.createStatement();
//
//  stbSqlCla=new StringBuffer();
//
//  strSql="DELETE FROM tbr_invcla WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" AND co_cla="+strCodCla;
//  stm.executeUpdate( strSql );
//
// for(int x=0; x<tblCla.getRowCount(); x++){
//  if(tblCla.getValueAt(x, INT_TBL_CHKITM) != null){
//   if(tblCla.getValueAt(x, INT_TBL_CHKITM).toString().equals("true")){
//
//    strEst="A";
//    if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
//     if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
//           strEst="A";
//    }
//    if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
//        if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
//           strEst="I";
//    }
//
//      strSql=" INSERT INTO tbr_invcla(co_emp, co_itm, co_cla ,st_reg, st_regrep, fe_ing ) VALUES" +
//      " ("+objZafParSis.getCodigoEmpresa()+","+tblCla.getValueAt(x, INT_TBL_CODITM) +", "+strCodCla+" "+
//      " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) ; ";
//      stbSqlCla.append( strSql+ " ; ");
//      intStrBuf=1;
//
// }}}
//
// if( intStrBuf==1 )
//   stm.executeUpdate(stbSqlCla.toString());
//
// stbSqlCla=null;
// stm.close();
// stm=null;
//}}catch (java.sql.SQLException evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt); }
//  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt);  }
// return blnRes;
//}
//

public boolean actualizarClaInv(java.sql.Connection conMod, String strCodCla,  String strCodGrp ){
 boolean blnRes=true;
 java.sql.ResultSet rstLoc;
 java.sql.Statement stmLoc;
 String strEst="A";
 String strSqlInv="";
 String strSql="";
 int intStrBuf=0;
 StringBuffer stbSqlCla;
 try{
 if(conMod!=null){
  stmLoc=conMod.createStatement();

   stbSqlCla=new StringBuffer();

   strSql="DELETE FROM tbr_invcla WHERE co_cla="+strCodCla+" and co_grp="+strCodGrp;
   stmLoc.executeUpdate( strSql );

   for(int x=0; x<tblCla.getRowCount(); x++){

   if(tblCla.getValueAt(x, INT_TBL_CHKITM) != null){
   if(tblCla.getValueAt(x, INT_TBL_CHKITM).toString().equals("true")){

    strEst="A";
    if(tblCla.getValueAt( x, INT_TBL_CLAACT)!=null){
     if(tblCla.getValueAt( x, INT_TBL_CLAACT).toString().equalsIgnoreCase("true"))
           strEst="A";
    }
    if(tblCla.getValueAt( x, INT_TBL_CLAINA)!=null){
        if(tblCla.getValueAt( x, INT_TBL_CLAINA).toString().equalsIgnoreCase("true"))
           strEst="I";
    }

    strSqlInv="SELECT co_emp , co_itm FROM tbm_equInv  WHERE "+
    " co_itmMae=(SELECT co_itmMae FROM tbm_equInv WHERE co_emp="+objZafParSis.getCodigoEmpresa()+" "+
    " AND co_itm="+tblCla.getValueAt(x, INT_TBL_CODITM)+")  ";
    rstLoc = stmLoc.executeQuery(strSqlInv);
    while(rstLoc.next()){

      strSql=" INSERT INTO tbr_invcla(co_emp, co_itm, co_grp,  co_cla ,st_reg, st_regrep, fe_ing ) VALUES" +
      " ("+rstLoc.getInt("co_emp")+","+ rstLoc.getInt("co_itm") +", "+strCodGrp+", "+strCodCla+" "+
      " ,'"+strEst+"', 'I' ,"+objZafParSis.getFuncionFechaHoraBaseDatos()+"  ) ; ";
      stbSqlCla.append( strSql+ " ; ");
      intStrBuf=1;
        
    }
    rstLoc.close();
    rstLoc=null;


  }}}

 if( intStrBuf==1 )
   stmLoc.executeUpdate(stbSqlCla.toString());

 stbSqlCla=null;
 stmLoc.close();
 stmLoc=null;
}}catch (java.sql.SQLException evt){ blnRes=false;   MensajeInf("Error. Al Modificar Clasificaciónes posible duplicidad de datos.\nVerifique los datos he intente nuevamente."); }
  catch (Exception evt){ blnRes=false; objUti.mostrarMsgErr_F1(this, evt);  }
 return blnRes;
}







      private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


        
 public void  clnTextos(){
     txtCodCla.setText("");
     txtDesCla.setText("");

     objTblModCla.removeAllRows();
 }

    
        
      
        public boolean cancelar() {
            boolean blnRes=true;
            
         
            clnTextos();
             
            return blnRes;
        }
        
        
        
        
        public boolean aceptar() {
            return true;
        }
        
        public boolean afterAceptar() {
            return true;
        }
        
        public boolean afterAnular() {
            return true;
        }
        
        public boolean afterCancelar() {
            
            return true;
        }
        
        public boolean afterConsultar() {
          
            return true;
        }
        
        public boolean afterEliminar() {
            return true;
        }
        
        public boolean afterImprimir() {
            return true;
        }
        
        public boolean afterInsertar() {
            this.setEstado('w');
         
            return true;
        }
        
        public boolean afterModificar() {
          
           this.setEstado('w');
           
            return true;
        }
        
        public boolean afterVistaPreliminar() {
            return true;
        }
        
        
        
        
        
   
        
          /**
     * Esta función muestra un mensaje "showConfirmDialog". Presenta las opciones
     * Si, No y Cancelar. El usuario es quien determina lo que debe hacer el sistema
     * seleccionando una de las opciones que se presentan.
     */
    private int mostrarMsgCon(String strMsg) {
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        return oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_CANCEL_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE);
    }
        
        
        
        
        
        
          /**
     * Esta función se encarga de agregar el listener "DocumentListener" a los objTooBars
     * de tipo texto para poder determinar si su contenido a cambiado o no.
     */
    private boolean isRegPro() {
        boolean blnRes=true;
        String strAux="¿Desea guardar los cambios efectuados a éste registro?\n";
        strAux+="Si no guarda los cambios perderá toda la información que no haya guardado.";
        switch (mostrarMsgCon(strAux)) {
            case 0: //YES_OPTION
                switch (objTooBar.getEstado()) {
                    case 'n': //Insertar
                        blnRes=objTooBar.insertar();
                        break;
                    case 'm': //Modificar
//                        blnRes=objTooBar.modificar();
                        break;
                }
                break;
            case 1: //NO_OPTION
                blnRes=true;
                break;
            case 2: //CANCEL_OPTION
                blnRes=false;
                break;
        }
        return blnRes;
    }
        
          
        
        
        
        public boolean consultar() {
                /*
                 * Esto Hace en caso de que el modo de operacion sea Consulta
                 */
           return _consultar(FilSql());
        }
        
        
        
          
        
private boolean _consultar(String strFil){
  boolean blnRes=true;

  if(validaCampos()){
       cargarTabCla();
  }else  blnRes=false;

    System.gc();
    return blnRes;
}
    
   


private boolean refrescaDatos(){
    boolean blnRes=false;
 
  return blnRes;
}

      
          

private String FilSql() {
    String sqlFiltro = "";
    //Agregando filtro por Numero de Cotizacion

   

    return sqlFiltro ;
}

        
        
    

public void clickModificar(){
 
    java.awt.Color colBack;
    colBack = txtCodCla.getBackground();
    txtCodCla.setEditable(false);
    txtCodCla.setBackground(colBack);
    colBack=null;

    colBack = txtDesCla.getBackground();
    txtDesCla.setEditable(false);
    txtDesCla.setBackground(colBack);
    colBack=null;

    butCla.setEnabled(false);

    colBack = txtCodGrp.getBackground();
    txtCodGrp.setEditable(false);
    txtCodGrp.setBackground(colBack);

    txtDesGrp.setEditable(false);
    txtDesGrp.setBackground(colBack);
    colBack=null;

    butSol.setEnabled(false);

    radPub.setEnabled(false);
    radPri.setEnabled(false);

   
    
 this.setEnabledConsultar(false);

}



        
        
        
        
       
       
        //******************************************************************************************************
        
        public boolean vistaPreliminar(){
           
            return true;
        }
        
        
        
        
        
        
        
        
        
        public boolean imprimir(){
            
            return true;
        }
        
        
        //******************************************************************************************************
        
        
        public void clickImprimir(){
        }
        public void clickVisPreliminar(){
        }
        
        public void clickCancelar(){
         
        }
        
        public void cierraConnections(){
            
        }
        
        
        public boolean beforeAceptar() {
            return true;
        }
        public boolean beforeAnular() {
            return true;
        }
        public boolean beforeCancelar() {
            return true;
        }
        public boolean beforeConsultar() {
            return true;
        }
        public boolean beforeEliminar() {
            return true;
        }
        public boolean beforeImprimir() {
          
            return true;
        }
        public boolean beforeInsertar() {
            return true;
        }
        public boolean beforeModificar() {
            return true;
        }
        public boolean beforeVistaPreliminar() {
       
            return true;
        }
        
      
        
        
    }
    
  
      
     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
    
    
}
