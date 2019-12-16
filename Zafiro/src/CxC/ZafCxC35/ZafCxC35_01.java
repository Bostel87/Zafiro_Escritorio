/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC35_01.java
 *
 * Created on Jun 04, 2010, 4:16:06 PM
 */

package CxC.ZafCxC35;
  
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafCxC35_01 extends javax.swing.JDialog {
   ZafParSis objZafParSis;
   ZafUtil objUti;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
   private ZafMouMotAda objMouMotAda;

   private ZafThreadGUI objThrGUI;
   
    final int INT_TBL_LIN  = 0;  // NUMERO DE LINEA
    final int INT_TBL_COEMP= 1; // CODIGO DE LA EMPRESA
    final int INT_TBL_COLOC= 2; // CODIGO DEL LOCAL
    final int INT_TBL_CODTIP=3;  // CODIGO DEL TIPO DOCUMENTO
    final int INT_TBL_CODDOC=4;  // CODIGO DEL DOCUMENTO
    final int INT_TBL_CODREG=5;  // CODIGO REGISTRO
    final int INT_TBL_DCTDOC=6;  // DESCRIPCION DEL CORTA TIPO DE DOCUMENTO
    final int INT_TBL_DLTDOC=7;  // DESCRIPCION DEL LARGA TIPO DE DOCUMENTO
    final int INT_TBL_NUMDOC=8;  // NOMERO DEL DOCUMENTO
    final int INT_TBL_FECDOC=9;  // FECHA DEL DOCUMENTO
    final int INT_TBL_DIACRE=10;  // DIA DE GRACIA DEL PAGO
    final int INT_TBL_FECVEN=11;  // FECHA DE VENCIMIENTO
    final int INT_TBL_VALDOC=12;  // VALOR DEL PAGO
    final int INT_TBL_VALPEN=13; // VALOR PENDIENTE DE PAGO
    final int INT_TBL_VALCHQ=14;  // VALOR DEL CHEQUE
    

    String strSQL="";
  
    boolean blnEst=false;
    boolean blnAcepta=false;

    String strFacSel="";
    String strNumDocSel="";
    

    /** Creates new form ZafCom35_01 */
    public ZafCxC35_01(java.awt.Frame parent, boolean modal, ZafParSis objParsis ,String strSql  ) {
       super(parent, modal);
       try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();
          
          strSQL=strSql;
           
          initComponents();
           this.setTitle(""+objZafParSis.getNombreMenu() );
           objUti = new ZafUtil();
       
           configurarForm();
  
        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


public boolean acepta(){
    return blnEst;
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
    vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL_CODREG,"Cód.Reg");
    vecCab.add(INT_TBL_DCTDOC,"Des.Cor");
    vecCab.add(INT_TBL_DLTDOC,"Des.Lar");
    vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
    vecCab.add(INT_TBL_DIACRE,"Día.Cre");
    vecCab.add(INT_TBL_FECVEN,"Fec.Ven");
    vecCab.add(INT_TBL_VALDOC,"Val.Doc");
    vecCab.add(INT_TBL_VALPEN,"Val.Pen");
    vecCab.add(INT_TBL_VALCHQ,"Val.Chq");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_VALCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_DIACRE, objTblMod.INT_COL_DBL, new Integer(0), null);



    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_COLOC).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_DCTDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_DLTDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALCHQ).setPreferredWidth(60);
    

    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_COEMP);
    arlColHid.add(""+INT_TBL_CODTIP);
    arlColHid.add(""+INT_TBL_CODDOC);
    arlColHid.add(""+INT_TBL_CODREG);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;

    
     objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_VALDOC ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALPEN ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALCHQ ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DIACRE ).setCellRenderer(objTblCelRenLbl);
     objTblCelRenLbl=null;

    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}



private void cargarDat(){

   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }

}

private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);

    cargarDatCla(strSQL);


   objThrGUI=null;
}
}


/**
 * Se encarga de cargar la informacion en la tabla
 * @param strSql Query que recibe para cargar la tabla
 * @return  true si esta correcto y false  si hay algun error 
 */
private boolean cargarDatCla(String strSql){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  double dlbValTot=0;
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){

               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LIN, "");
                 vecReg.add(INT_TBL_COEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_COLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_CODTIP, rstLoc.getString("co_tipdoc") );
                 vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                 vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                 vecReg.add(INT_TBL_DCTDOC, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_DLTDOC, rstLoc.getString("tx_deslar") );
                 vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
                 vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
                 vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
                 vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
                 vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
                 vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen") );
                 vecReg.add(INT_TBL_VALCHQ, rstLoc.getString("nd_monchq")  );
                
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
        jPanel6 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        panBarEst = new javax.swing.JPanel();
        lblMsgSis = new javax.swing.JLabel();
        panPrgSis = new javax.swing.JPanel();
        pgrSis = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblTit.setText("Listado de facturas a las que aplica el cheque");
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

        jPanel6.setLayout(new java.awt.BorderLayout());
        jPanel4.add(jPanel6, java.awt.BorderLayout.SOUTH);

        jTabbedPane1.addTab("General", jPanel4);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

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
        setBounds((screenSize.width-650)/2, (screenSize.height-400)/2, 650, 400);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

      
        cargarDat();

    }//GEN-LAST:event_formWindowOpened



    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formWindowClosing

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
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
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
        case INT_TBL_COLOC:
            strMsg="Código de local.";
            break;

         case INT_TBL_DCTDOC:
            strMsg="Descripcion corta del tipo de documento.";
            break;

         case INT_TBL_DLTDOC:
            strMsg="Descripcion larga del tipo de documento.";
            break;

         case INT_TBL_NUMDOC:
            strMsg="Número de documento.";
            break;
   
         case INT_TBL_FECDOC:
            strMsg="Fecha de documento.";
            break;

         case INT_TBL_DIACRE:
            strMsg="Días de credito.";
            break;

         case INT_TBL_FECVEN:
            strMsg="Fecha de vencimiento.";
            break;

         case INT_TBL_VALDOC:
            strMsg="Valor del documento.";
            break;

         case INT_TBL_VALPEN:
            strMsg="Valor Pendiente.";
            break;

         case INT_TBL_VALCHQ:
            strMsg="Valor del cheque.";
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
