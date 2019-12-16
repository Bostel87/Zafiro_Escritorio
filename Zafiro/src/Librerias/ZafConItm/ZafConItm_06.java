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
public class ZafConItm_06 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODEMP=1;                    // empresa-local.
    final int INT_TBL_CODLOC=2;                    // fecha de documento.
    final int INT_TBL_FECDOC=3;                    // usuario vendedor
    final int INT_TBL_NOMUSR=4;                    // boton de busqueda
    final int INT_TBL_NUMDOC=5;                    // Codigo empresa.
    
    
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
    public ZafConItm_06(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe, String strTit) {  //(java.awt.Frame parent, boolean modal) {
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
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_NOMUSR,"Usuario.");
            vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
            
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
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(75);
            tcmAux.getColumn(INT_TBL_NOMUSR).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(75);
            
	    
             /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
         
            tblDat.getTableHeader().setReorderingAllowed(false);
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
            
            
	    
            
        
            
            
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
        vecReg.add(INT_TBL_CODEMP, objUti.getStringValueAt(arlDat, i, 0) );
        vecReg.add(INT_TBL_CODLOC, objUti.getStringValueAt(arlDat, i, 1) );
        vecReg.add(INT_TBL_FECDOC, objUti.getStringValueAt(arlDat, i, 2) );
        vecReg.add(INT_TBL_NOMUSR, objUti.getStringValueAt(arlDat, i, 3) ); 
        vecReg.add(INT_TBL_NUMDOC, objUti.getStringValueAt(arlDat, i, 4) ); 
        
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
    private javax.swing.JButton butCan;
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
        case INT_TBL_CODEMP:
            strMsg="Cóidigo Empresa.";
            break;
        case INT_TBL_CODLOC:
            strMsg="Cóidigo Local.";
            break;
        case INT_TBL_FECDOC:
            strMsg="Fecha de la cotización.";
            break;
        case INT_TBL_NOMUSR:
            strMsg="Nombre del Usuario.";
            break;
        case INT_TBL_NUMDOC:
            strMsg="Número del documento. ";
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
