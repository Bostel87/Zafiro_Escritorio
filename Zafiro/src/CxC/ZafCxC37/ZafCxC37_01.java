/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC37_01.java
 *
 * Created on Nov 29, 2010, 2:07:03 PM
 */

package CxC.ZafCxC37;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafCxC37_01 extends javax.swing.JDialog {
   ZafParSis objZafParSis;
   ZafUtil objUti;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
   private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
  private ZafMouMotAda objMouMotAda;

    final int INT_TBL_LIN  = 0;  // NUMERO DE LINEA
    final int INT_TBL_COEMP= 1; // CODIGO DE LA EMPRESA
    final int INT_TBL_COLOC= 2; // CODIGO DEL LOCAL
    final int INT_TBL_CODTIP=3;  // CODIGO DEL TIPO DOCUMENTO
    final int INT_TBL_CODMNU=4;  // CODIGO DEL MENU
    final int INT_TBL_DCTDOC=5;  // DESCRIPCION DEL TIPO DE DOCUMENTO CORTA
    final int INT_TBL_DLTDOC=6;  // DESCRIPCION DEL TIPO DE DOCUMENTO LARGA
    final int INT_TBL_SELPRE=7;  // DOCUMENTO PREDETERMINADO
  

    /** Creates new form ZafCxC37_01 */
    public ZafCxC37_01(java.awt.Frame parent, boolean modal, ZafParSis objParsis ) {
        super(parent, modal);
        try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();

          initComponents();
          this.setTitle(""+objZafParSis.getNombreMenu() );
          lblTit.setText("ASIGNACIÓN DEL BANCO PREDETERMINADO");
          objUti = new ZafUtil();

          configurarForm();
          cargarDat();

        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


/**
 * confirgura la tabla donde se presenta la informacion de las facturas pendientes de pago.
 * @return true si todo esta bien   false si hay algun error
 */
private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_COEMP,"Cód.Emp");
    vecCab.add(INT_TBL_COLOC,"Cód.Loc");
    vecCab.add(INT_TBL_CODTIP,"Cód.TipDoc");
    vecCab.add(INT_TBL_CODMNU,"Cód.Doc");
    vecCab.add(INT_TBL_DCTDOC,"Des.Cor");
    vecCab.add(INT_TBL_DLTDOC,"Des.Lar");
    vecCab.add(INT_TBL_SELPRE,"Prede.");
   

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODTIP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DCTDOC).setPreferredWidth(110);
    tcmAux.getColumn(INT_TBL_DLTDOC).setPreferredWidth(180);
    tcmAux.getColumn(INT_TBL_SELPRE).setPreferredWidth(50);
  
    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_COEMP);
    arlColHid.add(""+INT_TBL_COLOC);
    arlColHid.add(""+INT_TBL_CODMNU);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;


    ////*****tblAnMe.getTableHeader().addMouseMotionListener(objTblMod);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_SELPRE);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_SELPRE).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;


    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_SELPRE).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intNunFil = tblDat.getSelectedRow();

           for(int i=0; i<tblDat.getRowCount(); i++){
             if(!(intNunFil==i)) tblDat.setValueAt(new Boolean(false), i, INT_TBL_SELPRE );
           }

        }
    });


    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}



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


           strSql="Select  b.co_emp, b.co_loc, a.co_tipdoc, b.co_mnu, a.tx_descor,a.tx_deslar,  b.st_reg  from tbr_tipdocdetprg as b " +
           " left outer join tbm_cabtipdoc as a on (b.co_emp = a.co_emp and b.co_loc = a.co_loc and b.co_tipdoc = a.co_tipdoc)   " +
           " where  b.co_emp = "+objZafParSis.getCodigoEmpresa()+" and  b.co_loc ="+objZafParSis.getCodigoLocal()+" and  b.co_mnu =  1831 " +
           " order by  a.tx_deslar ";

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LIN, "");
                 vecReg.add(INT_TBL_COEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_COLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_CODTIP, rstLoc.getString("co_tipdoc") );
                 vecReg.add(INT_TBL_CODMNU, rstLoc.getString("co_mnu") );
                 vecReg.add(INT_TBL_DCTDOC, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_DLTDOC, rstLoc.getString("tx_deslar") );
                 vecReg.add(INT_TBL_SELPRE,  new Boolean( (rstLoc.getString("st_reg").equals("S")?true:false) ) );

               vecData.add(vecReg);

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
        pgrSis.setIndeterminate(false);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTit.setText("jLabel1");
        jPanel1.add(lblTit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

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

        jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel4);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.setPreferredSize(new java.awt.Dimension(90, 23));
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        jPanel5.add(butAce);

        jButton2.setFont(new java.awt.Font("SansSerif", 0, 11));
        jButton2.setText("Cancelar");
        jButton2.setPreferredSize(new java.awt.Dimension(90, 23));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);

        jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

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

        jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-450)/2, (screenSize.height-350)/2, 450, 350);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:

   guardarDat();
    dispose();

    }//GEN-LAST:event_butAceActionPerformed


 private boolean guardarDat(){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  String strSql="";
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
          
           for(int i=0; i<tblDat.getRowCount(); i++){
               strSql="UPDATE tbr_tipdocdetprg SET st_reg='"+(tblDat.getValueAt( i, INT_TBL_SELPRE ).toString().equals("true")?'S':'N')+"' " +
               " WHERE co_emp="+tblDat.getValueAt( i, INT_TBL_COEMP).toString()+"" +
               " and co_loc="+tblDat.getValueAt( i, INT_TBL_COLOC).toString()+" and co_tipdoc="+tblDat.getValueAt( i, INT_TBL_CODTIP).toString()+" " +
               " and co_mnu="+tblDat.getValueAt( i, INT_TBL_CODMNU).toString()+" ";
               //System.out.println("-> "+ strSql );
                stmLoc.executeUpdate(strSql);

               if(tblDat.getValueAt( i, INT_TBL_SELPRE ).toString().equals("true")){
                 strSql =" UPDATE tbr_tipdocdetusr SET st_reg='N'  " +
                 " WHERE co_emp="+tblDat.getValueAt( i, INT_TBL_COEMP).toString()+"" +
                 " and co_loc="+tblDat.getValueAt( i, INT_TBL_COLOC).toString()+" " +
                 " and co_mnu="+tblDat.getValueAt( i, INT_TBL_CODMNU).toString()+" ";

                 strSql+=" ; UPDATE tbr_tipdocdetusr SET st_reg='S'  " +
                 " WHERE co_emp="+tblDat.getValueAt( i, INT_TBL_COEMP).toString()+"" +
                 " and co_loc="+tblDat.getValueAt( i, INT_TBL_COLOC).toString()+" and co_tipdoc="+tblDat.getValueAt( i, INT_TBL_CODTIP).toString()+" " +
                 " and co_mnu="+tblDat.getValueAt( i, INT_TBL_CODMNU).toString()+" ";
                 stmLoc.executeUpdate(strSql);
               }


              
           }

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}




    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        exitForm();
}//GEN-LAST:event_jButton2ActionPerformed

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
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
    private javax.swing.JButton butAce;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblMsgSis;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBarEst;
    private javax.swing.JPanel panPrgSis;
    private javax.swing.JProgressBar pgrSis;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_CODTIP:
            strMsg="Código.";
            break;

        case INT_TBL_DCTDOC:
            strMsg="Descripción corta.";
            break;

        case INT_TBL_DLTDOC:
            strMsg="Descripción larga. ";
            break;

        case INT_TBL_SELPRE:
            strMsg="Predeterminado..";
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
