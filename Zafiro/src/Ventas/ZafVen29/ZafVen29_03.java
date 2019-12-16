/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom47_01.java
 *
 * Created on Mar 9, 2010, 3:04:45 PM
 */
        
package Ventas.ZafVen29;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;
/**
 *
 * @author jayapata
 */
public class ZafVen29_03 extends javax.swing.JInternalFrame {

    ZafParSis objParSis;
    ZafUtil objUti;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;

     // TABLA DE DATOS
    final int INT_TBL_LIN= 0 ;
    final int INT_TBL_CODEMP= 1 ;
    final int INT_TBL_CODLOC= 2 ;
    final int INT_TBL_CODTID= 3 ;
    final int INT_TBL_CODDOC= 4 ;
    final int INT_TBL_TXDSTI= 5 ;
    final int INT_TBL_NUMDOC= 6 ;
    final int INT_TBL_FECDOC= 7 ;
    final int INT_TBL_BUTDOC= 8 ;
    final int INT_TBL_CODMNU=9;

    private Vector vecDat, vecReg;

    String strSQL="";

    String strEstReg="A";


    /** Creates new form ZafCom47_01 */
    public ZafVen29_03(ZafParSis objZafParsis, String strSql ) {
        try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();

        strSQL=strSql;
     
        initComponents();

        vecDat=new Vector();

        this.setTitle(objParSis.getNombreMenu() );
        
       }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }







private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CODEMP,"Cód.Emp ");
    vecCab.add(INT_TBL_CODLOC,"Cód.Loc");
    vecCab.add(INT_TBL_CODTID,"Cód.Tip.Doc");
    vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL_TXDSTI,"Des.TipDoc");
    vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
    vecCab.add(INT_TBL_BUTDOC,"..");
    vecCab.add(INT_TBL_CODMNU,"..");


    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(70);
    tcmAux.getColumn(INT_TBL_TXDSTI).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_BUTDOC).setPreferredWidth(25);

    ArrayList arlColHid=new ArrayList();

     arlColHid.add(""+INT_TBL_CODDOC);
     arlColHid.add(""+INT_TBL_CODTID);
     arlColHid.add(""+INT_TBL_CODMNU);
    

    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;


    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
    vecAux.add("" + INT_TBL_BUTDOC);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);

    objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
    tcmAux.getColumn(INT_TBL_BUTDOC).setCellRenderer(objTblCelRenBut);
    objTblCelRenBut=null;
    new ButDoc(tblDat, INT_TBL_BUTDOC);   //*****

    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}



private class ButDoc extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButDoc(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Documento.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentana( tblDat.getValueAt(intCol, INT_TBL_CODEMP).toString()
                ,tblDat.getValueAt(intCol, INT_TBL_CODLOC).toString()
                ,tblDat.getValueAt(intCol, INT_TBL_CODTID).toString()
                ,tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString()
                , (tblDat.getValueAt(intCol, INT_TBL_CODMNU)==null?"":tblDat.getValueAt(intCol, INT_TBL_CODMNU).toString() )
                );
    }
}



private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc,  String strCodMnu ){
      // Compras.ZafCom19.ZafCom19 obj1 = new  Compras.ZafCom19.ZafCom19(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodLocRel, strCodTipDocRel, strCodDocRel, inTipConfEI );

       if( strCodMnu.equals("1898") || strCodMnu.equals("1888") || strCodMnu.equals("1908")  ){
         Ventas.ZafVen27.ZafVen27 obj1 = new Ventas.ZafVen27.ZafVen27( objParSis , Integer.parseInt(strCodEmp) , Integer.parseInt(strCodLoc), Integer.parseInt(strCodTipDoc), Integer.parseInt(strCodDoc) , Integer.parseInt(strCodMnu)  );
         this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
         obj1.show();
       }else{
          Ventas.ZafVen28.ZafVen28 obj1 = new Ventas.ZafVen28.ZafVen28( objParSis , strCodEmp , strCodLoc, strCodTipDoc, strCodDoc  );
          this.getParent().add(obj1,javax.swing.JLayeredPane.DEFAULT_LAYER);
          obj1.show();
       }
}




    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTit = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        chkEst = new javax.swing.JCheckBox();
        jPanel3 = new javax.swing.JPanel();
        butCon = new javax.swing.JButton();
        butCer = new javax.swing.JButton();

        setClosable(true);
        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
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
        getContentPane().add(panTit, java.awt.BorderLayout.NORTH);

        jPanel1.setLayout(new java.awt.BorderLayout());

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

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Datos", jPanel1);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
        jTabbedPane1.getAccessibleContext().setAccessibleName("Datos");

        jPanel2.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        chkEst.setText("Mostrar los que están anulados");
        panBot.add(chkEst);

        jPanel2.add(panBot, java.awt.BorderLayout.WEST);

        butCon.setText("Consultar");
        butCon.setPreferredSize(new java.awt.Dimension(92, 25));
        butCon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butConActionPerformed(evt);
            }
        });
        jPanel3.add(butCon);

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        jPanel3.add(butCer);

        jPanel2.add(jPanel3, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-523)/2, (screenSize.height-358)/2, 523, 358);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
               exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        configurarForm();

        cargarReg(strSQL);
                
    }//GEN-LAST:event_formInternalFrameOpened

    private void exitForm(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_exitForm
        // TODO add your handling code here:

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }
        
    }//GEN-LAST:event_exitForm

    private void butConActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butConActionPerformed
        // TODO add your handling code here:

        if(chkEst.isSelected()) strEstReg="I";
        else strEstReg="A";

         cargarReg(strSQL);

    }//GEN-LAST:event_butConActionPerformed



private boolean cargarReg(String strSql){
    java.sql.Connection con;
    java.sql.Statement stm;
    java.sql.ResultSet rst;
    boolean blnRes=true;
    String strSQL="";
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();

                String strAux= strSql + " and a.st_reg='"+strEstReg+"' order by a.ne_numdoc";
        
             vecDat.clear();

             rst=stm.executeQuery(strAux);
             while (rst.next()){

                    vecReg=new Vector();
                    vecReg.add(INT_TBL_LIN,"");
                    vecReg.add(INT_TBL_CODEMP,rst.getString("co_emp"));
                    vecReg.add(INT_TBL_CODLOC,rst.getString("co_loc"));
                    vecReg.add(INT_TBL_CODTID,rst.getString("co_tipdoc"));
                    vecReg.add(INT_TBL_CODDOC,rst.getString("co_doc"));
                    vecReg.add(INT_TBL_TXDSTI,rst.getString("tx_descor"));
                    vecReg.add(INT_TBL_NUMDOC,rst.getString("ne_numdoc"));
                    vecReg.add(INT_TBL_FECDOC,rst.getString("fe_doc"));
                    vecReg.add(INT_TBL_BUTDOC,"..");
                    vecReg.add(INT_TBL_CODMNU,rst.getString("co_mnu"));
                  
                    vecDat.add(vecReg);
            }
            rst.close();
            stm.close();
            con.close();
            rst=null;
            stm=null;
            con=null;
            //Asignar vectores al modelo.
            objTblMod.setData(vecDat);
            tblDat.setModel(objTblMod);
            vecDat.clear();

            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCer;
    private javax.swing.JButton butCon;
    private javax.swing.JCheckBox chkEst;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}
