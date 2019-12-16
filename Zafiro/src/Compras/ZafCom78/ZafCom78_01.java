/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCom47_01.java
 *
 * Created on Mar 9, 2010, 3:04:45 PM
 */
         
package Compras.ZafCom78;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author jayapata
 */
public class ZafCom78_01 extends javax.swing.JInternalFrame {

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
    final int INT_TBL_CODLOCREL= 9 ;
    final int INT_TBL_CODTIDREL= 10 ;
    final int INT_TBL_CODDOCREL= 11 ;

    private Vector vecDat, vecReg;

    String strCodEmpGuia="";
    String strCodLocGuia="";
    String strCodTipdocGuia="";
    String strCodDocGuia="";

    /** Creates new form ZafCom47_01 */
    public ZafCom78_01(ZafParSis objZafParsis, String strCodEmp, String strCodLoc, String strCodTipdoc, String strCodDoc ) {
        try{
        this.objParSis = (Librerias.ZafParSis.ZafParSis) objZafParsis.clone();
        objUti = new ZafUtil();

        strCodEmpGuia=strCodEmp;
        strCodLocGuia=strCodLoc;
        strCodTipdocGuia=strCodTipdoc;
        strCodDocGuia=strCodDoc;
        
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
    vecCab.add(INT_TBL_CODLOCREL,"..");
    vecCab.add(INT_TBL_CODTIDREL,"..");
    vecCab.add(INT_TBL_CODDOCREL,"..");

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
    tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_TXDSTI).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(100);
    tcmAux.getColumn(INT_TBL_BUTDOC).setPreferredWidth(25);

    ArrayList arlColHid=new ArrayList();

     arlColHid.add(""+INT_TBL_CODDOC);
     arlColHid.add(""+INT_TBL_CODTID);
     arlColHid.add(""+INT_TBL_CODLOCREL);
     arlColHid.add(""+INT_TBL_CODTIDREL);
     arlColHid.add(""+INT_TBL_CODDOCREL);
//     arlColHid.add(""+INT_TBL_BUTDOC);

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
                ,tblDat.getValueAt(intCol, INT_TBL_CODLOCREL).toString()
                ,tblDat.getValueAt(intCol, INT_TBL_CODTIDREL).toString()
                ,tblDat.getValueAt(intCol, INT_TBL_CODDOCREL).toString()
                );
    }
}



private void llamarVentana(String strCodEmp, String strCodLoc, String strCodTipDoc, String strCodDoc, String strCodLocRel, String strCodTipDocRel, String strCodDocRel ){
       Compras.ZafCom19.ZafCom19 obj1 = new  Compras.ZafCom19.ZafCom19(objParSis, strCodEmp, strCodLoc, strCodTipDoc, strCodDoc, strCodLocRel, strCodTipDocRel, strCodDocRel, 1 );
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

        panTit = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
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

        lblTit.setText("Listado de Confirmaciones");
        panTit.add(lblTit);

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

        butCer.setText("Cerrar");
        butCer.setToolTipText("Cierra la ventana.");
        butCer.setPreferredSize(new java.awt.Dimension(92, 25));
        butCer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCerActionPerformed(evt);
            }
        });
        panBot.add(butCer);

        jPanel2.add(panBot, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-300)/2, 500, 300);
    }// </editor-fold>//GEN-END:initComponents

    private void butCerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCerActionPerformed
               exitForm(null);
}//GEN-LAST:event_butCerActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:

        configurarForm();

        cargarReg();
                
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



private boolean cargarReg(){
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

            strSQL="select * from ( "
            +" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc,  a.co_locrelguirem as co_locrel,  a.co_tipdocrelguirem as co_tipdocrel, a.co_docrelguirem as co_docrel  "
            +" from  tbm_detingegrmerbod as a  "
            +" inner join tbm_cabingegrmerbod as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )   "
            +" INNER JOIN tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
            +" where a.co_emp="+strCodEmpGuia+" and a.co_locrelguirem="+strCodLocGuia+"  and a.co_tipdocrelguirem= "+strCodTipdocGuia+"  "
            +" and a.co_docrelguirem = "+strCodDocGuia+"  and a3.st_reg not in ('E','I')   "
            +" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc , a.co_locrelguirem,  a.co_tipdocrelguirem, a.co_docrelguirem  "
            +" union all  "
            +" select a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc,  x.co_loc as co_locrel,  x.co_tipdoc as co_tipdocrel, x.co_doc as co_docrel "
            +" from ( "
            +" select a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc "
            +"  from tbr_detguirem as a2   "
            +" inner join tbm_detguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc and a3.co_reg=a2.co_reg )  "
            +" inner join tbm_cabguirem as a4 on (a4.co_emp=a3.co_emp and a4.co_loc=a3.co_loc and a4.co_tipdoc=a3.co_tipdoc and a4.co_doc=a3.co_doc  )  "
            +" inner join tbm_cabtipdoc as a5 on (a5.co_emp=a4.co_emp and a5.co_loc=a4.co_loc and a5.co_tipdoc=a4.co_tipdoc)  "
            +" where a2.co_emp="+strCodEmpGuia+" and a2.co_locrel="+strCodLocGuia+" and a2.co_tipdocrel= "+strCodTipdocGuia+" and a2.co_docrel= "+strCodDocGuia+" and a4.st_reg='A'  "
            +" group by a4.co_emp, a4.co_loc, a4.co_tipdoc, a4.co_doc, a5.tx_descor, a4.ne_numdoc, a4.fe_doc  "
            +" order by  a4.ne_numdoc "
            +" ) as x  "
            +" inner join  tbm_detingegrmerbod as a on (a.co_emp=x.co_emp and a.co_locrelguirem=x.co_loc and a.co_tipdocrelguirem=x.co_tipdoc and a.co_docrelguirem=x.co_doc ) "
            +" inner join tbm_cabingegrmerbod as a3 on (a3.co_emp=a.co_emp and a3.co_loc=a.co_loc and a3.co_tipdoc=a.co_tipdoc and a3.co_doc=a.co_doc )   "
            +" INNER JOIN tbm_cabtipdoc as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc )  "
            +" where  a3.st_reg not in ('E','I')   "
            +" group by a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a1.tx_descor,  a3.ne_numdoc, a3.fe_doc , x.co_loc,  x.co_tipdoc, x.co_doc "
            +" ) as x  order by ne_numdoc  ";

            //Limpiar vector de datos.
             vecDat.clear();

             rst=stm.executeQuery(strSQL);
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
                    vecReg.add(INT_TBL_CODLOCREL,rst.getString("co_locrel"));
                    vecReg.add(INT_TBL_CODTIDREL,rst.getString("co_tipdocrel"));
                    vecReg.add(INT_TBL_CODDOCREL,rst.getString("co_docrel"));

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panTit;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables

}
