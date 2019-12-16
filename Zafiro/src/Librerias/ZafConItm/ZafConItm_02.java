/*
 * ZafConItm_02.java
 *
 * Created on May 11, 2007, 12:36 PM
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
public class ZafConItm_02 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CODITM=1;                    //Código del documento.
    final int INT_TBL_CODALT=2;                    //Código del documento.
    final int INT_TBL_DESITM=3;                    //Descripcion de retiro.
    final int INT_TBL_PRECIO=4;                    // PRECIO DEL ITEM
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafColNumerada objColNum;
    private ZafTblEdi objTblEdi;  
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl; 
    private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt; 
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strSQL, strAux;
    private ArrayList arlDat, arlReg;
     
    /** Creates new form ZafConItm_02 */
    public ZafConItm_02(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         objParSis=obj;
        initComponents();
         arlDat=vector;
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
            strAux="Listado de Item que no tienen precio";  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODITM,"Código..");
            vecCab.add(INT_TBL_CODALT,"Código..");
            vecCab.add(INT_TBL_DESITM,"Descripción.");
            vecCab.add(INT_TBL_PRECIO, "Pre.Vta.");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
             tblDat.setRowSelectionAllowed(true);
             tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            objColNum=new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            
            objTblMod.setColumnDataType(INT_TBL_PRECIO, objTblMod.INT_COL_DBL, new Integer(0), null);
            
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DESITM).setPreferredWidth(200);
            tcmAux.getColumn(INT_TBL_PRECIO).setPreferredWidth(60);
               
            
            tcmAux.getColumn(INT_TBL_CODITM).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(0);
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_PRECIO);  
            objTblMod.setColumnasEditables(vecAux); 
            vecAux=null;
            
            //Configurar JTable: Editor de la tabla. 
            objTblEdi=new ZafTblEdi(tblDat);  

            
            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_PRECIO).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            
            
            objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
            tcmAux.getColumn(INT_TBL_PRECIO).setCellEditor(objTblCelEdiTxt);
             
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
                        vecReg.add(INT_TBL_CODITM, objUti.getStringValueAt(arlDat, i, 2) );
                        vecReg.add(INT_TBL_CODALT, objUti.getStringValueAt(arlDat, i, 0) );
                        vecReg.add(INT_TBL_DESITM, objUti.getStringValueAt(arlDat, i, 1) );
                        vecReg.add(INT_TBL_PRECIO, "" ); 
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
    private void initComponents() {//GEN-BEGIN:initComponents
        jPanel2 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panGenCab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14));
        lblTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTit.setText("T\u00edtulo");
        jPanel2.add(lblTit, java.awt.BorderLayout.NORTH);

        jTabbedPane1.setMinimumSize(new java.awt.Dimension(53, 50));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(459, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        panGenCab.setLayout(null);

        panGenCab.setPreferredSize(new java.awt.Dimension(0, 24));
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
        jScrollPane1.setViewportView(tblDat);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

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

        jButton1.setText("Guardar");
        jButton1.setPreferredSize(new java.awt.Dimension(92, 25));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.add(jButton1);

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(630, 340, 394, 380);
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        guardarDatos();
    }//GEN-LAST:event_jButton1ActionPerformed
   
        
    private void guardarDatos(){
        String strCodItm="", sql="";
        java.sql.Statement stm;
        double bldPre=0;
        try{
            java.sql.Connection CONN =DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            try{
               if (CONN!=null){
                    CONN.setAutoCommit(false);
             for(int i=0; i<tblDat.getRowCount();i++){
                 if( ! tblDat.getValueAt(i, INT_TBL_PRECIO).toString().trim().equals("")  ){
                   strCodItm = tblDat.getValueAt(i, INT_TBL_CODITM).toString();
                   bldPre = Double.parseDouble(tblDat.getValueAt(i, INT_TBL_PRECIO).toString()); 
                    
                    sql="UPDATE tbm_inv SET nd_prevta1="+bldPre+" FROM (" +
                    " SELECT co_emp , co_itm FROM tbm_equInv  WHERE co_itmMae="+strCodItm+" "+   //(SELECT co_itmMae FROM tbm_equInv WHERE co_emp=0  AND co_itm="+strCodItm+") " +
                    " ) AS x WHERE tbm_inv.co_emp=x.co_Emp and tbm_inv.co_itm=x.co_itm";
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
    
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
            dispose();
    }//GEN-LAST:event_butCanActionPerformed
    
      
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JPanel panBar;
    private javax.swing.JPanel panBot;
    private javax.swing.JPanel panGenCab;
    private javax.swing.JTable tblDat;
    // End of variables declaration//GEN-END:variables
    
}
