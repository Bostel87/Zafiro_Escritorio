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

/**
 *
 * @author  Administrator
 */   
public class ZafConItm_13 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_NOMBODEGR=1; 
    final int INT_TBL_TIPDOCDC=2; 
    final int INT_TBL_NUMGUIA=3; 
    final int INT_TBL_NOMBOD=4;                    // NUMERO DE FACTURA
    final int INT_TBL_CODALT=5;                    // FECHA DEL DOCUMENTO
    final int INT_TBL_NOMITM=6;                    // CODIGO CLIENTE
    final int INT_TBL_CANMAL=7;
 
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl; 
    //private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    
    private Vector vecDat, vecCab, vecReg;
    private String strAux;
    private ArrayList arlDat;
   
           
    javax.swing.JDesktopPane dskGen;
    private ZafMouMotAda objMouMotAda;                  //ToolTipText en TableHeader.
     
    /** Creates new form ZafConItm_02 */
    public ZafConItm_13(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe, String strTit) {  //(java.awt.Frame parent, boolean modal) {
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
            vecCab.add(INT_TBL_NOMBODEGR,"Nom.Bod.Egr");
            vecCab.add(INT_TBL_TIPDOCDC,"TipDoc");
            vecCab.add(INT_TBL_NUMGUIA,"NumGuia");
            vecCab.add(INT_TBL_NOMBOD,"Nom.Bod.Ing.");
            vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
            vecCab.add(INT_TBL_NOMITM,"Nom.itm.");
            vecCab.add(INT_TBL_CANMAL, "Can.MalEst.");
          
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
            
             
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NOMBOD).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(170);
            tcmAux.getColumn(INT_TBL_CANMAL).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NOMBODEGR).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_TIPDOCDC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_NUMGUIA).setPreferredWidth(60);




            objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();//inincializo el renderizador
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);//alineacion del contenido de la celda
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);//formato de la celda, este es numero
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_CANMAL).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;


            tblDat.getTableHeader().setReorderingAllowed(false);
             //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            objMouMotAda=new ZafMouMotAda();
            tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);
        
        
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
        
            
        vecReg.add(INT_TBL_NOMBODEGR, objUti.getStringValueAt(arlDat, i, 0) );
        vecReg.add(INT_TBL_TIPDOCDC, objUti.getStringValueAt(arlDat, i, 1) );
        vecReg.add(INT_TBL_NUMGUIA, objUti.getStringValueAt(arlDat, i, 2) );
        vecReg.add(INT_TBL_NOMBOD, objUti.getStringValueAt(arlDat, i, 3) );
        vecReg.add(INT_TBL_CODALT, objUti.getStringValueAt(arlDat, i, 4) );
        vecReg.add(INT_TBL_NOMITM, objUti.getStringValueAt(arlDat, i, 5) );
        vecReg.add(INT_TBL_CANMAL, objUti.getStringValueAt(arlDat, i, 6) );
       


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

        setBounds(580, 600, 704, 390);
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
        case INT_TBL_NOMBOD:
            strMsg=" Nombre de la bodega ingreso de mercaderia.";
            break;
        case INT_TBL_CODALT:
            strMsg=" Código de alterno del item.";
            break;
        case INT_TBL_NOMITM:
            strMsg=" Nombre del item.";
            break;
        case INT_TBL_CANMAL:
            strMsg="Cantidad en mal estado. ";
            break;


               case INT_TBL_NOMBODEGR:
            strMsg="Nombre de la bodega de salida de mercaderia. ";
            break;

               case INT_TBL_TIPDOCDC:
            strMsg="Descripción corta del tipo de documento. ";
            break;

               case INT_TBL_NUMGUIA:
            strMsg="Número de guia de remisión. ";
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
