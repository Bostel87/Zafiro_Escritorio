/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCon36.java
 *
 * Created on 07/11/2011, 10:45:18 PM
 */
   
package Contabilidad.ZafCon36;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import Librerias.ZafRptSis.ZafRptSis;

/**
 *
 * @author Javier
 */
public class ZafCon36 extends javax.swing.JInternalFrame {
   ZafParSis objZafParSis;
   ZafUtil objUti;

   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl, objTblCelRenLbl01;
   Librerias.ZafInventario.ZafInvItm objInvItm;
    private ZafThreadGUI objThrGUI;
    private ZafThreadGUIRpt objThrGUIRpt;
   private ZafRptSis objRptSis;
   private java.util.Date datFecAux;
   
    final int INT_TBL_LINEA = 0;
    final int INT_TBL_CODEMP =1;
    final int INT_TBL_CODLOC = 2;
    final int INT_TBL_CODTID = 3;
    final int INT_TBL_NOMEMP =4;
    final int INT_TBL_LOCAL = 5;
    final int INT_TBL_NUMSER = 6;
    final int INT_TBL_DCTIPDOC = 7;
    final int INT_TBL_DLTIPDOC = 8;
    final int INT_TBL_BUTHIS = 9;
    final int INT_TBL_ULTDOCIMP = 10;
    final int INT_TBL_MAXDOCAUT =11;
    final int INT_TBL_FECCAD = 12;
    final int INT_TBL_STCAD = 13;
    final int INT_TBL_STKACT = 14;
    final int INT_TBL_PRODIA = 15;
    final int INT_TBL_TOTREP = 16;
    final int INT_TBL_CANSUG = 17;
    final int INT_TBL_CODTIDGRP = 18;
    final int INT_TBL_CODBODGRP=19;


    int intDiasCalPro=25;  // NUMERO DE DIAS DEL MES PARA CALCULOS..

    private Vector vecDat, vecReg;

    final String VERSION = " Ver. 0.2 ";


    /** Creates new form ZafCon36 */
    public ZafCon36(ZafParSis objParsis) {
         try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();

          vecDat=new Vector();

          objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
          objTblCelRenLbl01 = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();

           objRptSis=new ZafRptSis(javax.swing.JOptionPane.getFrameForComponent(this), true, objZafParSis);
           objInvItm = new Librerias.ZafInventario.ZafInvItm(this, objZafParSis); 

          initComponents();
          this.setTitle(""+objZafParSis.getNombreMenu()+VERSION);
          lblTit.setText(""+objZafParSis.getNombreMenu() );
          objUti = new ZafUtil();

        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }



 private boolean configurarForm(){
   boolean blnres=false;

    Vector vecCab=new Vector();
    vecCab.clear();

    vecCab.add(INT_TBL_LINEA,"");
    
    vecCab.add(INT_TBL_CODEMP,"Cod.Emp. ");
    vecCab.add(INT_TBL_CODLOC,"Cod.Loc");
    vecCab.add(INT_TBL_CODTID,"CodTipDoc");
    vecCab.add(INT_TBL_NOMEMP,"Empresa. ");
    vecCab.add(INT_TBL_LOCAL,"Local");
    vecCab.add(INT_TBL_NUMSER,"Serie");
    vecCab.add(INT_TBL_DCTIPDOC,"TipDocC");
    vecCab.add(INT_TBL_DLTIPDOC,"TipDocL.");
    vecCab.add(INT_TBL_BUTHIS,"..");
    vecCab.add(INT_TBL_ULTDOCIMP,"UltDocImp");
    vecCab.add(INT_TBL_MAXDOCAUT,"MaxDocAut");
    vecCab.add(INT_TBL_FECCAD,"Fec.Cad ");
    vecCab.add(INT_TBL_STCAD,"");
    vecCab.add(INT_TBL_STKACT,"Stock");
    vecCab.add(INT_TBL_PRODIA,"Promedio");
    vecCab.add(INT_TBL_TOTREP,"Total");
    vecCab.add(INT_TBL_CANSUG,"Sugerido.");
    vecCab.add(INT_TBL_CODTIDGRP,"CodTipDocGrp");
    vecCab.add(INT_TBL_CODBODGRP,"CodBodGrp");

    

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LINEA);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    ZafMouMotAda objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_STKACT, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_PRODIA, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_TOTREP, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_CANSUG, objTblMod.INT_COL_DBL, new Integer(0), null);
   
    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_LOCAL).setPreferredWidth(200);
    tcmAux.getColumn(INT_TBL_NUMSER).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DCTIPDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_DLTIPDOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_ULTDOCIMP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_MAXDOCAUT).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECCAD).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_STKACT).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_PRODIA).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_TOTREP).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_CANSUG).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_BUTHIS).setPreferredWidth(25);


     Vector vecAux=new Vector();
     vecAux.add("" + INT_TBL_BUTHIS);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;


    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_NOMEMP);
    arlColHid.add(""+INT_TBL_STCAD);
    arlColHid.add(""+INT_TBL_CODEMP);
    arlColHid.add(""+INT_TBL_CODLOC);
    arlColHid.add(""+INT_TBL_CODTID);
    arlColHid.add(""+INT_TBL_CODTIDGRP);
    arlColHid.add(""+INT_TBL_CODBODGRP);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;



          objTblCelRenLbl01.setBackground(objZafParSis.getColorCamposObligatorios());
          tcmAux.getColumn(INT_TBL_LOCAL).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_NUMSER).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_DCTIPDOC).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_DLTIPDOC).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_ULTDOCIMP).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_MAXDOCAUT).setCellRenderer(objTblCelRenLbl01);
          tcmAux.getColumn(INT_TBL_FECCAD).setCellRenderer(objTblCelRenLbl01);
          objTblCelRenLbl01.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
            public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
                //Mostrar de color gris las columnas impares.

                int intCell=objTblCelRenLbl01.getRowRender();

                String str=tblDat.getValueAt(intCell, INT_TBL_STCAD).toString();
                if(str.equals("S")){
                    objTblCelRenLbl01.setBackground(java.awt.Color.RED);
                    objTblCelRenLbl01.setFont(new java.awt.Font(objTblCelRenLbl01.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl01.getFont().getSize()));
                    objTblCelRenLbl01.setForeground(java.awt.Color.WHITE);
                }else{
                    objTblCelRenLbl01.setBackground(java.awt.Color.WHITE);
                    objTblCelRenLbl01.setFont(new java.awt.Font(objTblCelRenLbl01.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl01.getFont().getSize()));
                    objTblCelRenLbl01.setForeground(java.awt.Color.BLACK);
                }
               
            }
        });



   
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
 


     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_STKACT ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_PRODIA ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_TOTREP ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_CANSUG ).setCellRenderer(objTblCelRenLbl);
     objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
    public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
        //Mostrar de color gris las columnas impares.

        int intCell=objTblCelRenLbl.getRowRender();

        String str=tblDat.getValueAt(intCell, INT_TBL_STCAD).toString();
        if(str.equals("S")){
        objTblCelRenLbl.setBackground(java.awt.Color.RED);
        objTblCelRenLbl.setFont(new java.awt.Font(objTblCelRenLbl.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
        objTblCelRenLbl.setForeground(java.awt.Color.WHITE);
        }else{
            objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
            objTblCelRenLbl.setFont(new java.awt.Font(objTblCelRenLbl.getFont().getFontName(), java.awt.Font.BOLD,   objTblCelRenLbl.getFont().getSize()));
            objTblCelRenLbl.setForeground(java.awt.Color.BLACK);
        }



    }
});

Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();

   objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUTHIS).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButHis(tblDat, INT_TBL_BUTHIS);   //*****



    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}




private class ButHis extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButHis(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Guía de remisión.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
                  
       int intCodEmp=Integer.parseInt(tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString());
       int intCodLoc=Integer.parseInt(tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString());
       int intCodTipDoc=Integer.parseInt(tblDat.getValueAt(intCol, INT_TBL_CODTID).toString());
       int intCodTipDocGrp = Integer.parseInt(tblDat.getValueAt(intCol, INT_TBL_CODTIDGRP).toString());
       int intCodBodGrp = Integer.parseInt(tblDat.getValueAt(intCol, INT_TBL_CODBODGRP).toString());
                    
       llamarVenHis(intCodEmp,intCodLoc, intCodTipDoc, intCodTipDocGrp, intCodBodGrp );
    }
}

private void llamarVenHis( int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodTipDocGrp, int intCodBodGrp){
 ZafCon36_01 obj1 = new  ZafCon36_01(objZafParSis,  intCodEmp, intCodLoc, intCodTipDoc, intCodTipDocGrp, intCodBodGrp);
 this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
  obj1.show();

}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        PanCab = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        tabFrm = new javax.swing.JTabbedPane();
        TabPanGen = new javax.swing.JPanel();
        PanDetCab = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        spnMrepo = new javax.swing.JSpinner();
        spnMcal = new javax.swing.JSpinner();
        PanDetDet = new javax.swing.JPanel();
        SrcTbl = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        PanPie = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butvpre = new javax.swing.JButton();
        butimp = new javax.swing.JButton();
        butcerr = new javax.swing.JButton();
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
                formInternalFrameOpened(evt);
            }
        });

        lblTit.setText("jLabel1");
        PanCab.add(lblTit);

        getContentPane().add(PanCab, java.awt.BorderLayout.NORTH);

        TabPanGen.setLayout(new java.awt.BorderLayout());

        PanDetCab.setPreferredSize(new java.awt.Dimension(684, 60));
        PanDetCab.setLayout(null);

        jLabel1.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel1.setText("Meses a reponer:");
        PanDetCab.add(jLabel1);
        jLabel1.setBounds(10, 30, 170, 20);

        jLabel2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jLabel2.setText("Meses para calcular el promedio:");
        PanDetCab.add(jLabel2);
        jLabel2.setBounds(10, 10, 170, 20);
        PanDetCab.add(spnMrepo);
        spnMrepo.setBounds(180, 30, 50, 20);
        PanDetCab.add(spnMcal);
        spnMcal.setBounds(180, 10, 50, 20);

        TabPanGen.add(PanDetCab, java.awt.BorderLayout.NORTH);

        PanDetDet.setLayout(new java.awt.BorderLayout());

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
        SrcTbl.setViewportView(tblDat);

        PanDetDet.add(SrcTbl, java.awt.BorderLayout.CENTER);

        TabPanGen.add(PanDetDet, java.awt.BorderLayout.CENTER);

        tabFrm.addTab("General", TabPanGen);

        getContentPane().add(tabFrm, java.awt.BorderLayout.CENTER);

        PanPie.setLayout(new java.awt.BorderLayout());

        butCon.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(90, 23));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel5.add(butCon);

        butvpre.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butvpre.setText("Vis.Previa");
        butvpre.setPreferredSize(new java.awt.Dimension(90, 23));
        butvpre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butvpreActionPerformed(evt);
            }
        });
        jPanel5.add(butvpre);

        butimp.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butimp.setText("Imprimir");
        butimp.setPreferredSize(new java.awt.Dimension(90, 23));
        butimp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butimpActionPerformed(evt);
            }
        });
        jPanel5.add(butimp);

        butcerr.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        butcerr.setText("Cerrar");
        butcerr.setPreferredSize(new java.awt.Dimension(90, 23));
        butcerr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butcerrActionPerformed(evt);
            }
        });
        jPanel5.add(butcerr);

        PanPie.add(jPanel5, java.awt.BorderLayout.EAST);

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

        PanPie.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(PanPie, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-700)/2, (screenSize.height-450)/2, 700, 450);
    }// </editor-fold>//GEN-END:initComponents

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:

      if(validarDat()){

        if (butCon.getText().equals("Consultar")) {

            if (objThrGUI==null) {
                objThrGUI=new ZafThreadGUI();
                objThrGUI.start();
                
            }
        }

      }

    }//GEN-LAST:event_butConActionPerformed



private boolean validarDat(){
  boolean blnRes=false;

   if( Integer.parseInt(spnMcal.getModel().getValue().toString()) > 0 ){
    if( Integer.parseInt(spnMrepo.getModel().getValue().toString()) > 0 ){

       blnRes=true;

    }else  MensajeInf("NÚMERO DE MESES A REPONER TIENE QUE SER MAYOR A 0 ");
   }else MensajeInf("NÚMERO DE MESES PARA CALCULAR PROMEDIO TIENE QUE SER MAYOR A 0 ");


 return blnRes;
}


private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }


private class ZafThreadGUI extends Thread{
 public void run(){

  lblMsgSis.setText("Obteniendo datos...");
  pgrSis.setIndeterminate(true);

   cargarDetReg();
   
    //Establecer el foco en el JTable sólo cuando haya datos.
    if(tblDat.getRowCount()>0)
    {
        tblDat.setRowSelectionInterval(0, 0);
        tblDat.requestFocus();
    }
       
    pgrSis.setValue(0);
    butCon.setText("Consultar");

    pgrSis.setIndeterminate(false);

    objThrGUI=null;

}
}


private boolean cargarDetReg(){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstLoc, rstLoc01;
 String strSql="";
 int intUlDocImp=0, intCodGrpTipDoc=0, intCodBodGrp=0;
 int intMaxDoc=0;
 double dblStock=0;
 int intDocPro=0;
 int intDiasProCal=0;
 int intTotDiaRep=0;
 double dblDocPro=0;
 double dblTotRep=0;
 java.util.Date datFecSis, datFec;
 try{
    butCon.setText("Detener");
    conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
    if (conn!=null){
        stmLoc=conn.createStatement();
        stmLoc01=conn.createStatement();
        //Obtener la condición.

           int intNumCalPro = Integer.parseInt(spnMcal.getModel().getValue().toString());

           int intNunMesRep = Integer.parseInt(spnMrepo.getModel().getValue().toString());

           intTotDiaRep = intNunMesRep * intDiasCalPro;
           intDiasProCal = intNumCalPro * intDiasCalPro;

           datFecSis = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos() );

           String strFechaFin=""+(datFecSis.getYear() + 1900)+"-"+(datFecSis.getMonth()+1)+"-"+datFecSis.getDate()+"";
           datFecSis.setMonth((datFecSis.getMonth())-intNumCalPro);
           String strFechaIni=""+(datFecSis.getYear() + 1900)+"-"+(datFecSis.getMonth()+1)+"-"+datFecSis.getDate()+"";

            System.out.println("strFechaIni->  "+strFechaIni  );
           System.out.println("strFechaFin-> "+strFechaFin  );


           datFecSis.setMonth((datFecSis.getMonth())+ ( intNumCalPro + intNunMesRep ) );
           String strFechaCalRep= strFechaFin ; //""+(datFecSis.getYear() + 1900)+"-"+(datFecSis.getMonth()+1)+"-"+datFecSis.getDate()+"";
       
           System.out.println("strFechaCalRep-> "+strFechaCalRep  );



       strSql="select a.co_emp, a.co_loc,  a1.co_tipdoc, a.co_grptipdoc, a2.tx_nom as nomloc, a2.tx_secdoc , a1.tx_descor, a1.tx_deslar, a1.ne_ultdoc "
       + " ,a4.co_bodgrp  "
       + " from tbm_cabGrpTipDoc as a "
       + " inner join tbm_cabTipDoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_grptipdoc=a.co_grptipdoc  ) "
       + " inner join tbm_loc as a2 on (a2.co_Emp=a1.co_emp and a2.co_loc=a1.co_loc) "
       + "  inner join tbr_bodloc as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.st_reg='P' )"
       + "  inner join tbr_bodEmpBodGrp as a4 on ( a4.co_emp=a3.co_emp and a4.co_bod=a3.co_bod ) "
       + ""
       + "where a.st_forpreimp in ('A','N')  and a1.st_almdatautsri='S' "
       + " order by a.co_emp, a.co_loc, a1.co_tipdoc ";

       //System.out.println("-->"+ strSql );

       rstLoc=stmLoc.executeQuery(strSql);
       vecDat.clear();
       while (rstLoc.next()){

           intUlDocImp=0;
           intMaxDoc=0;
           dblStock=0;
           intDocPro=0;

            vecReg=new Vector();
            vecReg.add(INT_TBL_LINEA,"");

            vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp"));
            vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc"));
            vecReg.add(INT_TBL_CODTID,  rstLoc.getString("co_tipdoc"));
                
            vecReg.add(INT_TBL_NOMEMP, "");
            vecReg.add(INT_TBL_LOCAL, rstLoc.getString("nomloc"));
            vecReg.add(INT_TBL_NUMSER, rstLoc.getString("tx_secdoc"));
            vecReg.add(INT_TBL_DCTIPDOC,  rstLoc.getString("tx_descor"));
            vecReg.add(INT_TBL_DLTIPDOC,  rstLoc.getString("tx_deslar"));
            vecReg.add(INT_TBL_BUTHIS, "");


            vecReg.add(INT_TBL_ULTDOCIMP,  rstLoc.getString("ne_ultdoc"));

            intUlDocImp=rstLoc.getInt("ne_ultdoc");

            intCodGrpTipDoc=rstLoc.getInt("co_grptipdoc");
            intCodBodGrp = rstLoc.getInt("co_bodgrp");


             strSql="select ne_numdochas, tx_feccadfac from tbm_datautsri   where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" and ne_numdochas = "
             + " ( select max(ne_numdochas)   from tbm_datautsri   where co_emp="+rstLoc.getInt("co_emp")+" and co_loc="+rstLoc.getInt("co_loc")+" and co_tipdoc="+rstLoc.getInt("co_tipdoc")+" )  ";
             rstLoc01=stmLoc01.executeQuery(strSql);
             if (rstLoc01.next()){
                vecReg.add(INT_TBL_MAXDOCAUT, rstLoc01.getString("ne_numdochas"));
                vecReg.add(INT_TBL_FECCAD, rstLoc01.getString("tx_feccadfac"));

                int intDias= diaRep(conn, strFechaCalRep,  objUti.formatearFecha(rstLoc01.getString("tx_feccadfac"), "dd/MM/yyyy", "yyyy-MM-dd") );
          
                vecReg.add(INT_TBL_STCAD, (intDias < 60?"S":"N")  );

                intMaxDoc=rstLoc01.getInt("ne_numdochas");
           }else{
                 vecReg.add(INT_TBL_MAXDOCAUT, "");
                 vecReg.add(INT_TBL_FECCAD, "");
                 vecReg.add(INT_TBL_STCAD, "N" );
           }
             rstLoc01.close();
             rstLoc01=null;

            dblStock = intMaxDoc - intUlDocImp;

            vecReg.add(INT_TBL_STKACT, ""+dblStock );


              if(intCodGrpTipDoc==4){  // PARA GUÍAS
                 dblDocPro=_getPromedioDocGui(conn, intCodBodGrp, strFechaIni, strFechaFin, intDiasProCal, intNumCalPro  );
              }else if(intCodGrpTipDoc==5){  // PARA RETENCIONES
                 dblDocPro=_getPromedioDocCabPag(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), strFechaIni, strFechaFin, intDiasProCal , intNumCalPro  );
              }else{
                  dblDocPro=_getPromedioDoc(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), strFechaIni, strFechaFin, intDiasProCal, intNumCalPro );
              }



            vecReg.add(INT_TBL_PRODIA, ""+dblDocPro );

            dblTotRep = dblDocPro * intTotDiaRep;


            vecReg.add(INT_TBL_TOTREP, ""+dblTotRep);


           dblTotRep =  dblTotRep-dblStock;
           if( dblTotRep < 0 ) dblTotRep=0;

            vecReg.add(INT_TBL_CANSUG,""+dblTotRep);

            vecReg.add(INT_TBL_CODTIDGRP,""+intCodGrpTipDoc);
            vecReg.add(INT_TBL_CODBODGRP,""+intCodBodGrp);



            vecDat.add(vecReg);

    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
    conn.close();
    conn=null;
    //Asignar vectores al modelo.
    objTblMod.setData(vecDat);
    tblDat.setModel(objTblMod);
    vecDat.clear();

    lblMsgSis.setText("Se encontraron " + tblDat.getRowCount() + " registros.");
   
}}catch (java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
  catch (Exception e){ blnRes=false;  objUti.mostrarMsgErr_F1(this, e); }
return blnRes;
}


private int diaRep(java.sql.Connection conn, String strFecDes, String strFecHas){
  int intDiaRep=0;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

    strSql=" select (date('"+strFecHas+"') - date('"+strFecDes+"'))  as dias ";
   // System.out.println("--> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){
        intDiaRep = rstLoc.getInt("dias");
     }
     rstLoc.close();
     rstLoc=null;

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt){ intDiaRep=-1;  System.out.println(""+Evt );  }
    catch(Exception Evt){ intDiaRep=-1;  System.out.println(""+Evt );  }
  return  intDiaRep;
}



 private double _getPromedioDoc(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, String strFechaIni, String strFechaFin, int intDiasProCal, int intNumMesPro ){
  double intProDoc=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     strSql=" select  maximoDoc, dias, tot, round((tot/dias),2) as totdia, round(((tot/dias)*  "+intDiasProCal+" ),2) as totmax  from (   "
     + "  select MAX(ne_numdoc) as  maximoDoc , round(count(ne_numdoc),2) as tot,  ( abs(date('"+strFechaIni+"') - date('"+strFechaFin+"')) - ( "+intNumMesPro+"* 5 ) ) as dias from tbm_cabmovinv  "
     + "  where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and fe_doc between '"+strFechaIni+"' and  '"+strFechaFin+"'  "
     + "  ) as x  ";
   // System.out.println("--> "+strSql);
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

        intProDoc=rstLoc.getDouble("totdia");   // totmax
//        intTotDoc=rstLoc.getDouble("tot");
//        dblPorDia=rstLoc.getDouble("totdia");
//        intUltDocGen=rstLoc.getInt("maximoDoc");

     }
     rstLoc.close();
     rstLoc=null;

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
    catch(Exception Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
  return  intProDoc;
}

/**
 * Optiene promedio de guías de remisión
 * @param conn
 * @param intCodBodGrp
 * @param strFechaIni
 * @param strFechaFin
 * @return
 */
private double _getPromedioDocGui(java.sql.Connection conn, int intCodBodGrp, String strFechaIni, String strFechaFin, int intDiasProCal, int intNumMesPro ){
  double intProDoc=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

     strSql=" select  maximoDoc, dias, tot, round((tot/dias),2) as totdia, round(((tot/dias)*  "+intDiasProCal+" ),2) as totmax  from (  "
     + " select MAX(a.ne_numdoc) as  maximoDoc , round(count(a.ne_numdoc),2) as tot,  ( abs(date('"+strFechaIni+"') - date('"+strFechaFin+"')) - ( "+intNumMesPro+"* 5 ) ) as dias "
     + " from tbm_cabguirem as a "
     + " INNER JOIN tbr_bodEmpBodGrp AS a6 ON (a6.co_emp=a.co_emp AND a6.co_bod=a.co_ptopar) "
     + " where  a.fe_doc between '"+strFechaIni+"' and  '"+strFechaFin+"' and a.ne_numdoc > 0 "
     + " AND ( a6.co_empGrp=0 AND a6.co_bodGrp=( "+intCodBodGrp+" ) ) "
     + " ) as x  ";


     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

        intProDoc=rstLoc.getDouble("totdia");
//        intTotDoc=rstLoc.getDouble("tot");
//        dblPorDia=rstLoc.getDouble("totdia");
//        intUltDocGen=rstLoc.getInt("maximoDoc");

     }
     rstLoc.close();
     rstLoc=null;

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
    catch(Exception Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
  return  intProDoc;
}

 private double _getPromedioDocCabPag(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, String strFechaIni, String strFechaFin, int intDiasProCal, int intNumMesPro ){
  double intProDoc=-1;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();

      strSql=" select  maximoDoc, dias, tot, round((tot/dias),2) as totdia, round(((tot/dias)*  "+intDiasProCal+" ),2) as totmax  from (  "
     + " select MAX(ne_numdoc1) as  maximoDoc , round(count(ne_numdoc1),2) as tot,  ( abs(date('"+strFechaIni+"') - date('"+strFechaFin+"')) - ( "+intNumMesPro+"* 5 ) ) as dias from tbm_cabpag "
     + " where co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and fe_doc between '"+strFechaIni+"' and  '"+strFechaFin+"'  "
     + " ) as x  ";

     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next()){

        intProDoc=rstLoc.getDouble("totdia");
//        intTotDoc=rstLoc.getDouble("tot");
//        dblPorDia=rstLoc.getDouble("totdia");
//        intUltDocGen=rstLoc.getInt("maximoDoc");

     }
     rstLoc.close();
     rstLoc=null;

     stmLoc.close();
     stmLoc=null;

  }}catch(java.sql.SQLException Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
    catch(Exception Evt){ intProDoc=-1;  System.out.println(""+Evt );  }
  return  intProDoc;
}




    private void butvpreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butvpreActionPerformed
        // TODO add your handling code here:

        cargarRepote(1);

}//GEN-LAST:event_butvpreActionPerformed



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
                    butvpre.setEnabled(false);
                    generarRpt(1);
                    butvpre.setEnabled(true);
                    break;
                case 1: //Botón "Vista Preliminar".
                    butvpre.setEnabled(false);
                    generarRpt(2);
                    butvpre.setEnabled(true);
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
        String strRutRpt, strNomRpt, strNomUsr="";;
        int i, intNumTotRpt;
        boolean blnRes=true;
        String sqlAux="";
        try
        {
            objRptSis.cargarListadoReportes();
            objRptSis.setVisible(true);
             strNomUsr=objZafParSis.getNombreUsuario();


             datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecAux==null)
                    return false;

            String strFecHorSer=objUti.formatearFecha(datFecAux, "dd/MM/yyyy   HH:mm:ss");

            if (objRptSis.getOpcionSeleccionada()==objRptSis.INT_OPC_ACE)
            {

                intNumTotRpt=objRptSis.getNumeroTotalReportes();
                for (i=0;i<intNumTotRpt;i++)
                {
                    if (objRptSis.isReporteSeleccionado(i))
                    {
                        strRutRpt=objRptSis.getRutaReporte(i);
                        strNomRpt=objRptSis.getNombreReporte(i);
                        //Inicializar los parametros que se van a pasar al reporte.

                        java.util.Map mapPar=new java.util.HashMap();

                        switch (Integer.parseInt(objRptSis.getCodigoReporte(i)))
                        {
                            case 182:

                                sqlAux= _getSqlTbl();

                                //System.out.println(""+ sqlAux );

                                mapPar.put("strSql",  sqlAux );
                                mapPar.put("strCamAudRpt", ""+ this.getClass().getName() + "      " +  strNomRpt +"   " + objZafParSis.getNombreUsuario() + "      " + strFecHorSer +"      v0.1    " );
                                mapPar.put("strFecRpt", strFecHorSer );

                                // $P!{strSql}

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;

                            default:

                                sqlAux= _getSqlTblHis();
                                
                                //System.out.println(""+ sqlAux );

                                mapPar.put("strSql",  sqlAux );
                                mapPar.put("strCamAudRpt", ""+ this.getClass().getName() + "      " +  strNomRpt +"   " + objZafParSis.getNombreUsuario() + "      " + strFecHorSer +"      v0.1    " );
                                mapPar.put("strFecRpt", strFecHorSer );

                                mapPar.put("SUBREPORT_DIR", strRutRpt);

                                // $P!{strSql}

                                objRptSis.generarReporte(strRutRpt, strNomRpt, mapPar, intTipRpt);
                                break;
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }



private String _getSqlTbl(){
  String strSqlTbl="";
  int intEst=0;
  try{

      int intCanRegtbl= tblDat.getRowCount();
      if(intCanRegtbl <= 0 ){
          MensajeInf("No hay datos en Tabla para Cargar reporte...");
      }else{

       for(int i=0; i<tblDat.getRowCount(); i++){

           if(intEst==1)  strSqlTbl+="  UNION ALL ";
           strSqlTbl+="SELECT  "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_LOCAL))+"' as Local , "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_NUMSER))+"' as NumSer,  "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_DCTIPDOC))+"' as DesCTipDoc , "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_DLTIPDOC))+"' as DesLTipDoc,  "
           + " "+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_ULTDOCIMP))+" as UltDocImp , "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_MAXDOCAUT))+"' as MaxDoc,  "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_FECCAD))+"' as FacCad,  "
            + " "+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_STKACT))+" as Stock,  "

           + " "+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_PRODIA))+" as ProDia,  "
           + " "+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_TOTREP))+"  as TotRep,  "
           + " "+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CANSUG))+" as CanSug  "

           + " ";
           intEst=1;
          
       }
      }
      
    }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e);  }
  return strSqlTbl;
}
 
private String _getSqlTblHis(){
  String strSqlTbl="";
  int intEst=0;
  try{

      int intCanRegtbl= tblDat.getRowCount();
      if(intCanRegtbl <= 0 ){
          MensajeInf("No hay datos en Tabla para Cargar reporte...");
      }else{

       for(int i=0; i<tblDat.getRowCount(); i++){

           if(intEst==1)  strSqlTbl+="  UNION ALL ";
           strSqlTbl+="SELECT  "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODEMP))+"' as coemp , "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODLOC))+"' as coloc,  "
           + " '"+objInvItm.getStringDatoValidado(tblDat.getValueAt(i, INT_TBL_CODTID))+"' as cotipdoc  "


           + " ";
           intEst=1;

       }
      }

    }catch(Exception e){ objUti.mostrarMsgErr_F1(this, e);  }
  return strSqlTbl;
}


    protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }




    
    private void butimpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butimpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_butimpActionPerformed

    private void butcerrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butcerrActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_butcerrActionPerformed

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:
         exitForm();
    }//GEN-LAST:event_exitForm

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        configurarForm();
    }//GEN-LAST:event_formInternalFrameOpened



     private void exitForm(){

     String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanCab;
    private javax.swing.JPanel PanDetCab;
    private javax.swing.JPanel PanDetDet;
    private javax.swing.JPanel PanPie;
    private javax.swing.JScrollPane SrcTbl;
    private javax.swing.JPanel TabPanGen;
    private javax.swing.JButton butCon;
    private javax.swing.JButton butcerr;
    private javax.swing.JButton butimp;
    private javax.swing.JButton butvpre;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JSpinner spnMcal;
    private javax.swing.JSpinner spnMrepo;
    private javax.swing.JTabbedPane tabFrm;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

    
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LINEA:
            strMsg="";
            break;
        case INT_TBL_NOMEMP:
            strMsg="Nombre de la Empresa.";
            break;
        case INT_TBL_LOCAL:
            strMsg="Nombre del Local.";
            break;

        case INT_TBL_NUMSER:
            strMsg="Número de serie ";
            break;

        case INT_TBL_DCTIPDOC:
            strMsg="Descripción corta del tipo de documento ";
            break;
        case INT_TBL_DLTIPDOC:
            strMsg="Descripción larga del tipo de documento ";
            break;
        case INT_TBL_BUTHIS:
            strMsg="Presenta el historico.";
            break;
        case INT_TBL_ULTDOCIMP:
            strMsg="Ultimo documento impreso.";
            break;
        case INT_TBL_MAXDOCAUT:
            strMsg="Máximo documento autorizado.";
            break;
        case INT_TBL_FECCAD:
            strMsg="Fecha de caducidad";
            break;
        case INT_TBL_STCAD:
            strMsg="Stock Actual.";
            break;
        case INT_TBL_PRODIA:
            strMsg="Promedio diario.";
            break;

       case INT_TBL_TOTREP:
           strMsg="Total a reponer.";
           break;


       case INT_TBL_CANSUG:
           strMsg="Cantidad sugerida a reponer.";
           break;

        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}






}
