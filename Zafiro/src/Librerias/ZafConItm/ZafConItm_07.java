/*
 * ZafConItm_07.java sdd
 *
 * Created on Mar 24, 2009, 12:36 PM intTipEmpSel
 */

package Librerias.ZafConItm;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;
import java.util.Vector;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafColNumerada.ZafColNumerada;
import Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi;
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.*;
/**
 *
 * @author  DCARDENAS
 */
public class ZafConItm_07 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_NUM_DEP=1;                    // empresa-local.
    final int INT_TBL_FEC_DOC=2;                    // fecha de documento.
    final int INT_TBL_NOM_BAN=3;                    // numero de cotizacion.
    final int INT_TBL_VAL_DEP=4;                    // usuario vendedor
    final int INT_TBL_BUT_BUS=5;                    // boton de busqueda
    final int INT_TBL_COD_EMP=6;                    // Codigo empresa.
    final int INT_TBL_COD_LOC=7;                    // codigo local.
    
    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private ZafTblMod objTblMod;
    private Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut;
    private ZafTblTot objTblTotDocCon;     
    private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl; 
    //private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt; 
    
    private Vector vecDat, vecCab, vecReg;
    private Vector vecAux;
    private String strAux;
    private ArrayList arlDat;
    
           
   //  javax.swing.JDesktopPane dskGen;
    
    /** Creates new form ZafConItm_02 */
    public ZafConItm_07(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         objParSis=obj;
        initComponents();
         arlDat=vector;
//         System.out.println("---ZafThrConDep()---ZafConItm_07---: ");
        // dskGen=dskGe;
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
//            System.out.println("---ZafThrConDep()---ZafConItm_07---configurarFrm---: ");
            objUti=new ZafUtil();
            strAux="Listado de Depositos por Confirmar ";  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_NUM_DEP,"Num.Dep.");
            vecCab.add(INT_TBL_FEC_DOC,"Fec.Doc.");
            vecCab.add(INT_TBL_NOM_BAN,"Nom.Ban.");
            vecCab.add(INT_TBL_VAL_DEP,"Val.Dep.");
            vecCab.add(INT_TBL_BUT_BUS, "..");
            vecCab.add(INT_TBL_COD_EMP,"Cod.Emp.");
            vecCab.add(INT_TBL_COD_LOC,"Cod.Loc.");
            
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
            
            objTblMod.setColumnDataType(INT_TBL_VAL_DEP, objTblMod.INT_COL_DBL, new Integer(0), null);
            
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUM_DEP).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_FEC_DOC).setPreferredWidth(90);
            tcmAux.getColumn(INT_TBL_NOM_BAN).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_VAL_DEP).setPreferredWidth(90);
            
	       
	    tcmAux.getColumn(INT_TBL_BUT_BUS).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_BUS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_BUS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUT_BUS).setPreferredWidth(0);
	    
            tcmAux.getColumn(INT_TBL_COD_EMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_COD_EMP).setPreferredWidth(0);
                       
            tcmAux.getColumn(INT_TBL_COD_LOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_COD_LOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_COD_LOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_COD_LOC).setPreferredWidth(0);
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUT_BUS);  
            objTblMod.setColumnasEditables(vecAux); 
            vecAux=null;
            
	    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VAL_DEP).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
	    
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUT_BUS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
                 
            // new ButCot(tblDat, INT_TBL_BUTBUS);   //*****
            
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDat);  

            
	    int intCol[]={ INT_TBL_VAL_DEP };
            objTblTotDocCon=new ZafTblTot(ScrollDat, spnDocContot , tblDat, tblTotDocCon, intCol);
            
	    
             
             objTblMod.setModoOperacion(objTblMod.INT_TBL_NO_EDI);
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
        System.out.println("---ZafThrConDep()---ZafConItm_07---configurarFrm---cargarDatos---: ");
       vecDat.clear();
        for(int i=0; i<arlDat.size(); i++){
          
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LIN,"");
                        vecReg.add(INT_TBL_NUM_DEP, objUti.getStringValueAt(arlDat, i, 0) );
                        vecReg.add(INT_TBL_FEC_DOC, objUti.getStringValueAt(arlDat, i, 1) );
                        vecReg.add(INT_TBL_NOM_BAN, objUti.getStringValueAt(arlDat, i, 2) );
                        vecReg.add(INT_TBL_VAL_DEP, objUti.getStringValueAt(arlDat, i, 3) ); 
                        vecReg.add(INT_TBL_BUT_BUS, " ");
                        vecReg.add(INT_TBL_COD_EMP, " "); 
                        vecReg.add(INT_TBL_COD_LOC, " "); 
                        vecDat.add(vecReg);
               
            
       }    
                objTblMod.setData(vecDat);  
                tblDat.setModel(objTblMod);
                vecDat.clear();
		
		objTblTotDocCon.calcularTotales();    
                objTblTotDocCon.setValueAt("" +  objTblTotDocCon.getValueAt(0,INT_TBL_VAL_DEP),0,0 ); 
		    
		
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
        spnDocContot = new javax.swing.JScrollPane();
        tblTotDocCon = new javax.swing.JTable();
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

        spnDocContot.setPreferredSize(new java.awt.Dimension(454, 18));

        tblTotDocCon.setModel(new javax.swing.table.DefaultTableModel(
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
        spnDocContot.setViewportView(tblTotDocCon);

        jPanel3.add(spnDocContot, java.awt.BorderLayout.SOUTH);

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

        setBounds(800, 600, 400, 390);
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
    private javax.swing.JScrollPane spnDocContot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTotDocCon;
    // End of variables declaration//GEN-END:variables
    
}
