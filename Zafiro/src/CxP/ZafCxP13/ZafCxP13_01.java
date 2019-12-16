/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC01_01.java
 *
 * Created on Jul 20, 2011, 12:23:33 PM
 */

package CxP.ZafCxP13;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author jayapata
 */
public class ZafCxP13_01 extends javax.swing.JDialog {
    private ZafParSis objZafParSis;
    private ZafTblMod objTblMod;
    private ZafTblCelEdiTxt objTblCelEdiTxt;
    private ZafUtil objUti;
    //private ZafThreadGUI objThrGUI;

    private static final int INT_TBL_LINEA =0;
    private static final int INT_TBL_CODCLI=1;
    private static final int INT_TBL_CLIENTE=2;
    private static final int INT_TBL_IDESIS=3;
    private static final int INT_TBL_INDING=4;

    Vector vecCab=new Vector();

    String strCLiError="";

    public boolean blnAcepta = false;

    /** Creates new form ZafCxC01_01 */
    public ZafCxP13_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis ZafParSis, StringBuffer strBuf ) {
      super(parent, modal);
       try{ 
        this.objZafParSis = ZafParSis;
        objUti = new ZafUtil();

        initComponents();

        lblTit.setText(""+objZafParSis.getNombreMenu() );

        configuraTbl();
        cargarDat(strBuf);

       }catch (Exception e){ objUti.mostrarMsgErr_F1(this, e);  }
    }





private boolean configuraTbl(){
       boolean blnRes=false;
       try{
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODCLI, "Cod.Cli");
	    vecCab.add(INT_TBL_CLIENTE,"Cliente.");
	    vecCab.add(INT_TBL_IDESIS, "Ide.Sis.");
	    vecCab.add(INT_TBL_INDING, "Identificación");

	    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);

            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            ZafColNumerada zafColNumerada = new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);

	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();

	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODCLI).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CLIENTE).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_INDING).setPreferredWidth(150);


            /* Aqui se agrega las columnas que van
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_IDESIS);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;

            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_INDING);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            ZafTblEdi zafTblEdi = new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);



            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_INDING).setCellEditor(objTblCelEdiTxt);
            objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                @Override
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

                }
                @Override
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {

                }
            });
            objTblCelEdiTxt=null;

            objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);

           tcmAux=null;
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBut = new javax.swing.JPanel();
        panSubBot = new javax.swing.JPanel();
        butAce = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(475, 20));
        jPanel1.setLayout(null);

        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("jLabel1");
        jPanel1.add(lblTit);
        lblTit.setBounds(10, 0, 450, 14);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.BorderLayout());

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

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel3);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panBut.setLayout(new java.awt.BorderLayout());

        butAce.setFont(new java.awt.Font("SansSerif", 0, 11));
        butAce.setText("Aceptar");
        butAce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butAceActionPerformed(evt);
            }
        });
        panSubBot.add(butAce);

        butCan.setFont(new java.awt.Font("SansSerif", 0, 11));
        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        panSubBot.add(butCan);

        panBut.add(panSubBot, java.awt.BorderLayout.EAST);

        getContentPane().add(panBut, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-483)/2, (screenSize.height-334)/2, 483, 334);
    }// </editor-fold>//GEN-END:initComponents

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:

       strCLiError="";

        if(validaCampos()){
            blnAcepta=true;
            dispose();
        }else{
            MensajeInf(" "+strCLiError+" \n LA IDENTIFICACIÓN NO ES IGUAL \n COMUNIQUESE CON EL DEPARTAMENTO DE CONTABILIDAD.");
            blnAcepta = false;
        }

    }//GEN-LAST:event_butAceActionPerformed


    
public boolean acepta(){
  return blnAcepta;
}


private void MensajeInf(String strMensaje){
    javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    JOptionPane.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
}



private boolean validaCampos(){
 boolean blnRes=false;
 boolean blnCliError=false;
 String strIdeSis="";
 String strIdeIng="";
 try{

   for(int i=0; i<tblDat.getRowCount(); i++){

       strIdeSis= tblDat.getValueAt(i, INT_TBL_IDESIS).toString();
       strIdeIng =tblDat.getValueAt(i, INT_TBL_INDING).toString();

       if(strIdeSis.trim().equals(strIdeIng.trim())){
           //System.out.println(" BIEN");
           blnRes=true;
       }else{
           //System.out.println(" MAL");
           strCLiError+="Cliente : "+tblDat.getValueAt(i, INT_TBL_CODCLI).toString()+"  "+tblDat.getValueAt(i, INT_TBL_CLIENTE).toString()+" \n ";
           blnCliError=true;
       }
   }

   if(blnCliError) blnRes=false;

 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
 return blnRes;
}



    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        cerrarVen();
}//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        cerrarVen();
    }//GEN-LAST:event_formWindowClosing

    


private void cerrarVen(){
    String strMsg = "¿Está Seguro que desea cerrar este programa?";
    javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
    String strTit;
    strTit="Mensaje del sistema Zafiro";
    if(JOptionPane.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 0 ) {
        System.gc();
        blnAcepta=false;
        dispose();
    }
 }






private boolean cargarDat(StringBuffer strBuf){
 boolean blnRes=false;
 Connection conn;
 Statement stmLoc;
 ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
   conn=DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();

      /*strSql="select a1.co_cli, upper(a1.tx_nomcli) as nomcli, a1.tx_ruc from ( "
      + " "+strBuf.toString()+" "
      + " ) as a "
      + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) "
      + " group by a1.co_cli, upper(a1.tx_nomcli), a1.tx_ruc ";
      rstLoc=stmLoc.executeQuery(strSql);*/

      strSql= " select a1.co_cli, upper(a1.tx_nomcli) as nomcli, a1.tx_ruc "
              + " from ( "
              + " "+strBuf.toString()+" " 
              + " ) as a " 
              + " inner join tbm_cabmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " 
              + " group by a1.co_cli, upper(a1.tx_nomcli), a1.tx_ruc "
              + " order by 2 ";
      
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODCLI, rstLoc.getString("co_cli") );
	  vecReg.add(INT_TBL_CLIENTE, rstLoc.getString("nomcli") );
	  vecReg.add(INT_TBL_IDESIS, rstLoc.getString("tx_ruc") );
	  vecReg.add(INT_TBL_INDING, "" );

	 vecData.add(vecReg);
       }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
      rstLoc.close();
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
    
    blnRes=true;
  }}catch(SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;
}








    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butAce;
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBut;
    private javax.swing.JPanel panSubBot;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}
