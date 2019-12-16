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
import Librerias.ZafTblUti.ZafTblOrd.ZafTblOrd;
import java.sql.*;
/**
 *
 * @author  Administrator
 */
public class ZafConItm_11 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_NOMEMP=1;                    // empresa-local.
    final int INT_TBL_DTIPDOC=2;                    // fecha de documento.
    final int INT_TBL_FECDOC=3;                    // usuario vendedor
    final int INT_TBL_NUMDOC=4;                    // Codigo empresa.
    final int INT_TBL_ESTADO=5;                    // boton de busqueda

      
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod, objTblModIng;
    //private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl; 
    //private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    
    private Vector vecDat, vecCab, vecReg;
    private String strAux;
    private ArrayList arlDat;
    private Vector vecAux; 
           
    javax.swing.JDesktopPane dskGen;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.

    String strSqlEgr="";
    String strSqlIng="";

    /** Creates new form ZafConItm_02 */
    public ZafConItm_11(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe, String strTit, String SQLEgr, String SQLIng ) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         objParSis=obj;
        initComponents();
         arlDat=vector;
         dskGen=dskGe;
         strAux=strTit;

          objUti=new ZafUtil();
         this.setTitle(strAux);
         lblTit.setText(strAux);

         strSqlEgr=SQLEgr;
         strSqlIng=SQLIng;

         configurarFrmEgr();
         configurarFrmIng();
         cargarDato();
    }
       
    
       /** Configurar el formulario. */
    private boolean configurarFrmEgr()
    {
        boolean blnRes=true;
        try{
                
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DTIPDOC,"Des.Tip.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_ESTADO,"Estado");
              
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblEgr.setModel(objTblMod);
               
            //Configurar JTable: Establecer tipo de selección.
             tblEgr.setRowSelectionAllowed(true);
             tblEgr.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblEgr,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblEgr.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            //objTblMod.setColumnDataType(INT_TBL_VALTOT, objTblMod.INT_COL_DBL, new Integer(0), null);

            new ZafTblOrd(tblEgr);
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblEgr.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(75);
            
	    
            tblEgr.getTableHeader().setReorderingAllowed(false);
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblEgr.getTableHeader().addMouseMotionListener(objMouMotAda);
 
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
    
      private boolean configurarFrmIng()
    {
        boolean blnRes=true;
        try{
           
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_NOMEMP,"Empresa");
            vecCab.add(INT_TBL_DTIPDOC,"Des.Tip.Doc");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
            vecCab.add(INT_TBL_ESTADO,"Estado");

            objTblModIng=new ZafTblMod();
            objTblModIng.setHeader(vecCab);
            tblIng.setModel(objTblModIng);

            //Configurar JTable: Establecer tipo de selección.
             tblIng.setRowSelectionAllowed(true);
             tblIng.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            new ZafColNumerada(tblIng,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblIng.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

            //objTblMod.setColumnDataType(INT_TBL_VALTOT, objTblMod.INT_COL_DBL, new Integer(0), null);

              new ZafTblOrd(tblIng);

            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblIng.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMEMP).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_DTIPDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_ESTADO).setPreferredWidth(75);


            tblIng.getTableHeader().setReorderingAllowed(false);
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            ZafMouMotAdaIng objMouMotAdaIng=new ZafMouMotAdaIng();
            tblIng.getTableHeader().addMouseMotionListener(objMouMotAdaIng);
 
             objTblModIng.setModoOperacion(objTblModIng.INT_TBL_EDI);
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

     
    
 
private void cargarDato(){
 java.sql.Connection conn;
 try{
     conn = DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
     if(conn!=null){
       
        cargarEgr(conn);
        cargarIng(conn);
         
       conn.close();
       conn=null;
         
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}

private void cargarEgr(java.sql.Connection conn){
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 try{
     if(conn!=null){
         vecDat.clear();
         stm = conn.createStatement();
         rst = stm.executeQuery(strSqlEgr);
         while(rst.next()){

            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_NOMEMP, rst.getString("tx_nom") );
            vecReg.add(INT_TBL_DTIPDOC, rst.getString("tx_descor") );
            vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc") );
            vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc") );
            vecReg.add(INT_TBL_ESTADO, rst.getString("estado") );
            vecDat.add(vecReg);

         }
         objTblMod.setData(vecDat);
         tblEgr.setModel(objTblMod);
         vecDat.clear();

         rst.close();
         rst=null;
         stm.close();
         stm=null;
         
  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
}
             
private void cargarIng(java.sql.Connection conn){
 java.sql.Statement stm;
 java.sql.ResultSet rst;
 try{
     if(conn!=null){
         vecDat.clear();
         stm = conn.createStatement();
         rst = stm.executeQuery(strSqlIng);
         while(rst.next()){

            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_NOMEMP, rst.getString("tx_nom") );
            vecReg.add(INT_TBL_DTIPDOC, rst.getString("tx_descor") );
            vecReg.add(INT_TBL_FECDOC, rst.getString("fe_doc") );
            vecReg.add(INT_TBL_NUMDOC, rst.getString("ne_numdoc") );
            vecReg.add(INT_TBL_ESTADO, rst.getString("estado") );
            vecDat.add(vecReg);

         }
         objTblModIng.setData(vecDat);
         tblIng.setModel(objTblModIng);
         vecDat.clear();

         rst.close();
         rst=null;
         stm.close();
         stm=null;

  }}catch(java.sql.SQLException e)  {   objUti.mostrarMsgErr_F1(this, e);  }
    catch(Exception Evt)  {  objUti.mostrarMsgErr_F1(this, Evt);  }
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
        jPanel3 = new javax.swing.JPanel();
        ScrollDat = new javax.swing.JScrollPane();
        tblEgr = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        ScrollDat1 = new javax.swing.JScrollPane();
        tblIng = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
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

        jPanel3.setLayout(new java.awt.BorderLayout());

        tblEgr.setModel(new javax.swing.table.DefaultTableModel(
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
        ScrollDat.setViewportView(tblEgr);

        jPanel3.add(ScrollDat, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Egresos", jPanel1);

        jPanel5.setLayout(new javax.swing.BoxLayout(jPanel5, javax.swing.BoxLayout.LINE_AXIS));

        jPanel6.setLayout(new java.awt.BorderLayout());

        tblIng.setModel(new javax.swing.table.DefaultTableModel(
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
        ScrollDat1.setViewportView(tblIng);

        jPanel6.add(ScrollDat1, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel6);

        jTabbedPane1.addTab("Ingresos", jPanel5);

        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        panBar.setLayout(new java.awt.BorderLayout());

        panBot.setLayout(new java.awt.BorderLayout());

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

        setBounds(800, 600, 450, 390);
    }// </editor-fold>//GEN-END:initComponents
   
        
  
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
      
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane ScrollDat;
    private javax.swing.JScrollPane ScrollDat1;
    private javax.swing.JButton butCan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JTable tblEgr;
    private javax.swing.JTable tblIng;
    // End of variables declaration//GEN-END:variables
    
    
    
    
private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblEgr.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_NOMEMP:
            strMsg="Nombre Empresa.";
            break;
        case INT_TBL_DTIPDOC:
            strMsg="Descripcion del tipo del documento.";
            break;
        case INT_TBL_FECDOC:
            strMsg="Fecha del documento.";
            break;
        case INT_TBL_NUMDOC:
            strMsg="Número del documento.";
            break;
        case INT_TBL_ESTADO:
            strMsg="Estado de confirmación. ";
            break;
            
        default:
            strMsg="";
            break;
    }
    tblEgr.getTableHeader().setToolTipText(strMsg);
}
}
      

private class ZafMouMotAdaIng extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblIng.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_NOMEMP:
            strMsg="Nombre Empresa.";
            break;
        case INT_TBL_DTIPDOC:
            strMsg="Descripcion del tipo del documento.";
            break;
        case INT_TBL_FECDOC:
            strMsg="Fecha del documento.";
            break;
        case INT_TBL_NUMDOC:
            strMsg="Número del documento.";
            break;
        case INT_TBL_ESTADO:
            strMsg="Estado de confirmación. ";
            break;

        default:
            strMsg="";
            break;
    }
    tblIng.getTableHeader().setToolTipText(strMsg);
}
}

     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }
    
    
    
    
}
