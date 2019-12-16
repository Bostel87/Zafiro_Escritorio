/*
 * ZafConItm_15.java 
 *
 * Created on July 02, 2012
 */

package Librerias.ZafConItm;

import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafTableColBut.ZafTableColBut_uni;
import Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
/**
 *
 * @author efloresa
 * LISTADO DE ORDENES DE DESPACHO CON ITEMS FALTANTES/DAÑADOS
 */
public class ZafConItm_15 extends JDialog {
    
    //Constantes: Columnas del JTable:
    private static final int INT_TBL_LIN        =0;                    //Línea
    private static final int INT_TBL_CODEMP     =1;                    //
    private static final int INT_TBL_CODLOC     =2;                    //
    private static final int INT_TBL_CODTIPDOC  =3;                    // 
    private static final int INT_TBL_CODDOC     =4;                    // 
    private static final int INT_TBL_DESCOR     =5;                    //
    private static final int INT_TBL_DESLAR     =6;                    // 
    private static final int INT_TBL_FECDOC     =7;                    // 
    private static final int INT_TBL_NOMCLIDES  =8;                    // 
    private static final int INT_TBL_NUMORDDES  =9;                    // 
    private static final int INT_TBL_NUMFAC  =10;                   // 
    private static final int INT_TBL_CONINV     =11;                   // 
    private static final int INT_TBL_FALTANTES  =12;                   // 
    private static final int INT_TBL_DANIADOS   =13;                   //     
    private static final int INT_TBL_BUTBUS     =14;
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private ZafTblCelRenBut objTblCelRenBut;
       
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strAux;
    private ArrayList arlDat;    
           
    JDesktopPane dskGen;
    
    /** Creates new form ZafConItm_15 */
    public ZafConItm_15(Frame parent, boolean modal, ArrayList vector , ZafParSis obj, JDesktopPane dskGe) { 
        super(parent, modal);
        objParSis=obj;
        initComponents();
        arlDat=vector;
        dskGen=dskGe;
        configurarFrm();
        cargarDatos();  
    }       
    
    /** Configurar el formulario. */
    private boolean configurarFrm() {
        boolean blnRes=true;
        try {
            //Inicializar objetos.
            objUti=new ZafUtil();
            strAux="Listado de Ordenes de Despacho con Faltantes/Dañados ";  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            vecCab.add(INT_TBL_CODTIPDOC,"Cod.Tip.Doc");
            vecCab.add(INT_TBL_CODDOC,"Cod.Doc");
            vecCab.add(INT_TBL_DESCOR, "Des.Cor");
            vecCab.add(INT_TBL_DESLAR,"Des.Lar");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc");
            vecCab.add(INT_TBL_NOMCLIDES,"Nom.Cli.");
            vecCab.add(INT_TBL_NUMORDDES,"Ord.Des.");
            vecCab.add(INT_TBL_NUMFAC,"Num.Fac.");
            vecCab.add(INT_TBL_CONINV,"Estado");
            vecCab.add(INT_TBL_FALTANTES,"Faltantes.");
            vecCab.add(INT_TBL_DANIADOS,"Dañados");
            //vecCab.add(INT_TBL_BUTBUS,"..");
            
            objTblMod=new ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            //Configurar JTable: Establecer la fila de cabecera.
            ZafColNumerada zafColNumerada = new ZafColNumerada(tblDat,INT_TBL_LIN);
            //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DESCOR).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NUMORDDES).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_NOMCLIDES).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_CONINV).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FALTANTES).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_DANIADOS).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMFAC).setPreferredWidth(50);
            //tcmAux.getColumn(INT_TBL_BUTBUS).setPreferredWidth(20);
               
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_CODTIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            arlColHid.add(""+INT_TBL_DESLAR);
            //arlColHid.add(""+INT_TBL_NUMFAC);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;         
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            //Configurar JTable: Establecer columnas editables.
            /*vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTBUS);  
            objTblMod.setColumnasEditables(vecAux); 
            vecAux=null;            
            
            objTblCelRenBut=new ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBUS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            
            ButCot butCot = new ButCot(tblDat, INT_TBL_BUTBUS);*/   
            
            //Configurar JTable: Editor de la tabla.
            ZafTblEdi zafTblEdi = new ZafTblEdi(tblDat);            
             
             objTblMod.setModoOperacion(ZafTblMod.INT_TBL_EDI);
            //Libero los objetos auxiliares.
            tcmAux=null;
        }catch(Exception e) {
            blnRes=false;
            objUti.mostrarMsgErr_F1(this, e);
        }
        return blnRes;
    }   
    
    private class ButCot extends ZafTableColBut_uni{
        public ButCot(JTable tbl, int intIdx){
            super(tbl,intIdx, "Cotización.");           
        }
        
        @Override
        public void butCLick() {
           int intCol = tblDat.getSelectedRow();
        }
    }       
    
    private void cargarDatos(){
        vecDat.clear();
        for(int i=0; i<arlDat.size(); i++){
            vecReg=new Vector();
            vecReg.add(INT_TBL_LIN,"");
            vecReg.add(INT_TBL_CODEMP, objUti.getStringValueAt(arlDat, i, 0) );
            vecReg.add(INT_TBL_CODLOC, objUti.getStringValueAt(arlDat, i, 1) );
            vecReg.add(INT_TBL_CODTIPDOC, objUti.getStringValueAt(arlDat, i, 2) );
            vecReg.add(INT_TBL_CODDOC, objUti.getStringValueAt(arlDat, i, 3) ); 
            vecReg.add(INT_TBL_DESCOR, objUti.getStringValueAt(arlDat, i, 4) ); 
            vecReg.add(INT_TBL_DESLAR, objUti.getStringValueAt(arlDat, i, 5) ); 
            vecReg.add(INT_TBL_FECDOC, objUti.getStringValueAt(arlDat, i, 6) ); 
            vecReg.add(INT_TBL_NOMCLIDES, objUti.getStringValueAt(arlDat, i, 7) ); 
            vecReg.add(INT_TBL_NUMORDDES, objUti.getStringValueAt(arlDat, i, 8) ); 
            vecReg.add(INT_TBL_NUMFAC, objUti.getStringValueAt(arlDat, i, 9) ); 
            vecReg.add(INT_TBL_CONINV, objUti.getStringValueAt(arlDat, i, 10) ); 
            vecReg.add(INT_TBL_FALTANTES, objUti.getStringValueAt(arlDat, i, 11) ); 
            vecReg.add(INT_TBL_DANIADOS, objUti.getStringValueAt(arlDat, i, 12) ); 
            //vecReg.add(INT_TBL_BUTBUS, "...");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        panBar = new javax.swing.JPanel();
        panBot = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel2.setLayout(new java.awt.BorderLayout());

        lblTit.setFont(new java.awt.Font("MS Sans Serif", 1, 14)); // NOI18N
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

        panBot.add(jPanel4, java.awt.BorderLayout.EAST);

        panBar.add(panBot, java.awt.BorderLayout.CENTER);

        jPanel2.add(panBar, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel2, java.awt.BorderLayout.NORTH);

        setBounds(800, 600, 523, 390);
    }// </editor-fold>//GEN-END:initComponents
  
    
    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        dispose();
        Runtime.getRuntime().gc();
    }//GEN-LAST:event_butCanActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        dispose();
        Runtime.getRuntime().gc();
    }//GEN-LAST:event_formWindowClosing
          
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
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
