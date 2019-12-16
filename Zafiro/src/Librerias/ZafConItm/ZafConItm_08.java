/*
 * ZafConItm_02.java sdd
 *
 * Created on May 11, 2007, 12:36 PM intTipEmpSel
 */

package Librerias.ZafConItm;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import java.sql.*;
/**
 *
 * @author  Administrator
 */
public class ZafConItm_08 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODEMP=1;                    // CODIGO EMP
    final int INT_TBL_CODLOC=2;                    // CODIGO LOC
    final int INT_TBL_CODTIPDOC=3;                    // CODIGO TIPDOC
    final int INT_TBL_CODOC=4;                    // CODIGO DOC
    final int INT_TBL_CODREG=5;                    // Codigo REG.
    final int INT_TBL_LOCAL=6;                    // NOMBRE LOCAL
    final int INT_TBL_TXCODALT=7;                    // CODIGO ALTERNO ITME
    final int INT_TBL_NOMITM=8;                    // NOMBRE ITEM
    final int INT_TBL_BODEGA=9;                    // BODEGA
    final int INT_TBL_CHKITM=10;                    // BODEGA
    
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    //private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl; 
    //private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    
    private Vector vecDat, vecCab, vecReg;
    private String strAux;
    private ArrayList arlDat;
    private Vector vecAux; 
           
    javax.swing.JDesktopPane dskGen;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
     
    /** Creates new form ZafConItm_02 */
    public ZafConItm_08(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe, String strTit) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         objParSis=obj;
        initComponents();
         arlDat=vector;
         dskGen=dskGe;
         strAux=strTit;
        configurarFrm();
         cargarDatos();  
    }
       
    
       /** Configurar el formulario. */
    private boolean configurarFrm()
    {
        boolean blnRes=true;
        try
        {
            //Inicializar objetos.
            objUti=new ZafUtil();
            
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.TipDoc.");
            vecCab.add(INT_TBL_CODOC,"Cod.Doc.");
            vecCab.add(INT_TBL_CODREG,"Cod.reg.");
            vecCab.add(INT_TBL_LOCAL,"Local");
            vecCab.add(INT_TBL_TXCODALT,"Cod.Atl");
            vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
            vecCab.add(INT_TBL_BODEGA,"Bodega.");
            vecCab.add(INT_TBL_CHKITM,"..");


            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
             tblDat.setRowSelectionAllowed(true);
             tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //objTblMod.setColumnDataType(INT_TBL_VALTOT, objTblMod.INT_COL_DBL, new Integer(0), null);
            
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_LOCAL).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_TXCODALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(105);
            tcmAux.getColumn(INT_TBL_BODEGA).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CHKITM).setPreferredWidth(25);


              Vector vecAux=new Vector();
                vecAux.add("" + INT_TBL_CHKITM);
              objTblMod.setColumnasEditables(vecAux);
              vecAux=null;

	    
             /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODLOC);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODOC);
            arlColHid.add(""+INT_TBL_CODREG);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
         
            tblDat.getTableHeader().setReorderingAllowed(false);
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CHKITM).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_CHKITM).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null;


            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDat);  

            
	    
             
             objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }
    
    
    
    
    
    
     
        
private void cargarDatos(){
 vecDat.clear();
 for(int i=0; i<arlDat.size(); i++){

        vecReg=new Vector();
        vecReg.add(INT_TBL_LIN,"");
        vecReg.add(INT_TBL_CODEMP,    objUti.getStringValueAt(arlDat, i, 0) );
        vecReg.add(INT_TBL_CODLOC,    objUti.getStringValueAt(arlDat, i, 1) );
        vecReg.add(INT_TBL_CODTIPDOC, objUti.getStringValueAt(arlDat, i, 2) );
        vecReg.add(INT_TBL_CODOC,     objUti.getStringValueAt(arlDat, i, 3) );
        vecReg.add(INT_TBL_CODREG,    objUti.getStringValueAt(arlDat, i, 4) );
        vecReg.add(INT_TBL_LOCAL,    objUti.getStringValueAt(arlDat, i, 5) );
        vecReg.add(INT_TBL_TXCODALT, objUti.getStringValueAt(arlDat, i, 6) );
        vecReg.add(INT_TBL_NOMITM,   objUti.getStringValueAt(arlDat, i, 7) );
        vecReg.add(INT_TBL_BODEGA,   objUti.getStringValueAt(arlDat, i, 8) );
        vecReg.add(INT_TBL_CHKITM,   new Boolean(false) );



        vecDat.add(vecReg);
 }    
 objTblMod.setData(vecDat);  
 tblDat.setModel(objTblMod);
 vecDat.clear();
}


    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        ScrollDat = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butGua = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("Título");
        jPanel2.add(lblTit, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(53, 50));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(459, 300));

        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 24));
        panGenCab.setLayout(null);
        jPanel1.add(panGenCab, java.awt.BorderLayout.NORTH);

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
        ScrollDat.setViewportView(tblDat);

        jPanel3.add(ScrollDat, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("General", jPanel1);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

        butGua.setText("Guardar");
        butGua.setPreferredSize(new java.awt.Dimension(92, 25));
        butGua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butGuaActionPerformed(evt);
            }
        });
        jPanel4.add(butGua);

        butCan.setText("Cancelar");
        butCan.setPreferredSize(new java.awt.Dimension(92, 25));
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel4.add(butCan);

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(630, 340, 465, 390);
    }// </editor-fold>//GEN-END:initComponents
   
        
  
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_butCanActionPerformed

    private void butGuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butGuaActionPerformed
        // TODO add your handling code here:
        guardarDatos();
    }//GEN-LAST:event_butGuaActionPerformed

    private void guardarDatos(){
        String sql="";
        java.sql.Statement stm;
        try{
            java.sql.Connection CONN =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try{
               if (CONN!=null){
                    CONN.setAutoCommit(false);
             for(int i=0; i<tblDat.getRowCount();i++){
                if( tblDat.getValueAt(i, INT_TBL_CHKITM).toString().equals("true")  ){

                    sql="UPDATE tbm_detsoldevven SET st_revFalStk='S', st_regrep='M' WHERE co_emp="+tblDat.getValueAt(i, INT_TBL_CODEMP)+" AND " +
                    " co_loc="+tblDat.getValueAt(i, INT_TBL_CODLOC)+" AND  co_tipdoc="+tblDat.getValueAt(i, INT_TBL_CODTIPDOC)+" AND " +
                    " co_doc="+tblDat.getValueAt(i, INT_TBL_CODOC)+" AND co_reg="+tblDat.getValueAt(i, INT_TBL_CODREG);
                    stm = CONN.createStatement();
                    stm.executeUpdate(sql);

             }}
             }
              CONN.commit();
              CONN.close();
              CONN=null;
              javax.swing.JOptionPane.showMessageDialog(null, "Se realizo con exíto la ACTUALIZACION DE PRECION.");
        }catch(SQLException Evt){ CONN.rollback();  objUti.mostrarMsgErr_F1(this, Evt); }
        }catch(Exception e ){ objUti.mostrarMsgErr_F1(this, e); }
      dispose();
    }


      
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollDat;
    private javax.swing.JButton butCan;
    private javax.swing.JButton butGua;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGenCab;
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
        case INT_TBL_LOCAL:
            strMsg="Local.";
            break;
        case INT_TBL_TXCODALT:
            strMsg="Codigo alterno del item.";
            break;
        case INT_TBL_NOMITM:
            strMsg="Nombre del Item.";
            break;
        case INT_TBL_BODEGA:
            strMsg="Bodega. ";
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
