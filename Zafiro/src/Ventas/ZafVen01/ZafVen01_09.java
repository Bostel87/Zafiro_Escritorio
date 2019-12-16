/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafVen01_09.java
 *
 * Created on Dec 10, 2009, 3:00:49 PM
 */

package Ventas.ZafVen01;


/**
 *
 * @author jayapata
 */

import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
import java.sql.DriverManager;


public class ZafVen01_09 extends javax.swing.JInternalFrame {

    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;


    final int INT_TBL_LIN = 0;
    final int INT_TBL_CODEMP = 1;
    final int INT_TBL_CODLOC=  2;
    final int INT_TBL_CODCOT = 3;
    final int INT_TBL_FECCOT = 4;
    final int INT_TBL_TOTCOT = 5;
    final int INT_TBL_FECMOD = 6;
    final int INT_TBL_BOTCOT = 7;
    final int INT_TBL_CODHIS = 8;

    int intCodEmpCot=0;
    int intCodLocCot=0;
    int intCodCotCot=0;


    /** Creates new form ZafVen01_09 */
    public ZafVen01_09(Librerias.ZafParSis.ZafParSis obj, int intCodEmp, int  intCodLoc , int intCodCot ) {
       
       try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new ZafUtil();
            this.setTitle(objZafParSis.getNombreMenu() );

            intCodEmpCot=intCodEmp;
            intCodLocCot=intCodLoc;
            intCodCotCot=intCodCot;

            initComponents();

            lblTit.setText( objZafParSis.getNombreMenu() ); 

        }catch (CloneNotSupportedException e){
            objUti.mostrarMsgErr_F1(this, e);
        }
    }





private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CODEMP,"Cod.Emp ");
    vecCab.add(INT_TBL_CODLOC,"Cod.Loc");
    vecCab.add(INT_TBL_CODCOT,"Cod.Cot");
    vecCab.add(INT_TBL_FECCOT,"Fec.Cot");
    vecCab.add(INT_TBL_TOTCOT,"Tot.Cot");
    vecCab.add(INT_TBL_FECMOD,"Fec.Mod");
    vecCab.add(INT_TBL_BOTCOT,"..");
    vecCab.add(INT_TBL_CODHIS,"Cod.His");


    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    ZafMouMotAdaCot objMouMotAda=new ZafMouMotAdaCot();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_TOTCOT, objTblMod.INT_COL_DBL, new Integer(0), null);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODCOT).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_FECCOT).setPreferredWidth(120);
   // tcmAux.getColumn(INT_TBL_TOTCOT).setPreferredWidth(120);
    tcmAux.getColumn(INT_TBL_FECMOD).setPreferredWidth(150);
    tcmAux.getColumn(INT_TBL_BOTCOT).setPreferredWidth(25);

     objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_TOTCOT ).setCellRenderer(objTblCelRenLbl);
     objTblCelRenLbl=null;


    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_CODEMP);
    arlColHid.add(""+INT_TBL_CODLOC);
    arlColHid.add(""+INT_TBL_CODHIS);
    arlColHid.add(""+INT_TBL_TOTCOT);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;

      //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
     vecAux.add("" + INT_TBL_BOTCOT);
   objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
  
     //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BOTCOT).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButDat(tblDat, INT_TBL_BOTCOT);   //*****

   
    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}


private class ButDat extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButDat(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Cotización de Ventas.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaDat(intCol);
    }
}
private void llamarVentanaDat(int intCol){

    int intCodEmp = Integer.parseInt( tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString());
    int intCodLoc = Integer.parseInt( tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODLOC  ).toString());
    int intCodCot = Integer.parseInt( tblDat.getValueAt(intCol,  INT_TBL_CODCOT  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODCOT  ).toString());
    int intCodHis = Integer.parseInt( tblDat.getValueAt(intCol,  INT_TBL_CODHIS  )==null?"":tblDat.getValueAt(intCol,  INT_TBL_CODHIS  ).toString());



        Ventas.ZafVen01.ZafVen01_His obj = new Ventas.ZafVen01.ZafVen01_His(objZafParSis, new Integer(intCodEmp), new Integer(intCodLoc), new  Integer(intCodCot), new  Integer(intCodHis)  );
        this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
        obj.setLocation(500, 500);
        obj.show();


}



    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
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
        jPanel1.add(lblTit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

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
        jScrollPane1.setViewportView(tblDat);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        jButton2.setText("Cancelar");
        jButton2.setPreferredSize(new java.awt.Dimension(90, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-465)/2, (screenSize.height-380)/2, 465, 380);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        dispose();
}//GEN-LAST:event_jButton2ActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:


        configurarForm();
        
        cargarDat();

        
    }//GEN-LAST:event_formInternalFrameOpened









private boolean cargarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

           strSql="select co_emp, co_loc, co_cot, fe_cot, nd_tot, fe_ultmod, co_his  from tbh_cabcotven where co_emp="+intCodEmpCot+" " +
           " and co_loc="+intCodLocCot+" and co_cot="+intCodCotCot+" order by fe_ultmod ";

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){
               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LIN, "");
                 vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_CODCOT, rstLoc.getString("co_cot") );
                 vecReg.add(INT_TBL_FECCOT, rstLoc.getString("fe_cot") );
                 vecReg.add(INT_TBL_TOTCOT, rstLoc.getString("nd_tot") );
                 vecReg.add(INT_TBL_FECMOD, rstLoc.getString("fe_ultmod") );
                 vecReg.add(INT_TBL_BOTCOT, ".." );
                 vecReg.add(INT_TBL_CODHIS, rstLoc.getString("co_his") );

                vecData.add(vecReg);
           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}







    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables




private class ZafMouMotAdaCot extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_CODEMP:
            strMsg="Cóidigo Empresa.";
            break;
        case INT_TBL_CODLOC:
            strMsg="Cóidigo local.";
            break;
        case INT_TBL_CODCOT:
            strMsg="Cóidigo de Cotización.";
            break;
        case INT_TBL_FECCOT:
            strMsg="Fecha de proveedor.";
            break;
        case INT_TBL_TOTCOT:
            strMsg="Total de Cotización.";
            break;
        case INT_TBL_FECMOD:
            strMsg="Fecha de ultima de Modificación de Cotización.";
            break;

         case INT_TBL_BOTCOT:
            strMsg="Ver la Cotización de Ventas.";
            break;


        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}



     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }




}
