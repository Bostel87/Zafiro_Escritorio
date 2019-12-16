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
import Librerias.ZafTblUti.ZafTblTot.ZafTblTot;
import java.sql.*;
/**
 *
 * @author  Administrator
 */
public class ZafConItm_04 extends javax.swing.JDialog {
    
    //Constantes: Columnas del JTable:
    final int INT_TBL_LIN=0;                        //Línea
    final int INT_TBL_CONFIR=1;                    // empresa-local.
    final int INT_TBL_NUMDOC=2;                    // empresa-local.
    final int INT_TBL_FECDOC=3;                    // fecha de documento.
    final int INT_TBL_NOMCLI=4;                    // numero de cotizacion.
    final int INT_TBL_VALPEN=5;                    // usuario vendedor
    final int INT_TBL_BUTBUS=6;                    // boton de busqueda
    final int INT_TBL_CODEMP=7;                    // Codigo empresa.
    final int INT_TBL_CODLOC=8;                    // codigo local.
    
    
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
    public ZafConItm_04(java.awt.Frame parent, boolean modal, java.util.ArrayList vector , ZafParSis obj,  javax.swing.JDesktopPane dskGe) {  //(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
         objParSis=obj;
        initComponents();
         arlDat=vector;
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
            objUti=new ZafUtil();
            strAux="Listado de facturas de contado ";  //objParSis.getNombreMenu();
            this.setTitle(strAux);
            lblTit.setText(strAux);
            //Configurar objetos.
            vecDat=new Vector();    //Almacena los datos
            vecCab=new Vector();   //Almacena las cabeceras
            vecCab.clear();
            vecCab.add(INT_TBL_LIN,"");
            vecCab.add(INT_TBL_CONFIR,"Conf.");
            vecCab.add(INT_TBL_NUMDOC,"Num.doc.");
            vecCab.add(INT_TBL_FECDOC,"Fec.Doc.");
            vecCab.add(INT_TBL_NOMCLI,"Nom.Cli.");
            vecCab.add(INT_TBL_VALPEN,"Val.Pen.");
            vecCab.add(INT_TBL_BUTBUS, "..");
            vecCab.add(INT_TBL_CODEMP,"Cod.Emp.");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
            
            
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
            
            objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
            
                  
            //Configurar JTable: Establecer el ancho de las columnas.
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
            tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(30);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_NOMCLI).setPreferredWidth(120);
            tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(80);
            tcmAux.getColumn(INT_TBL_CONFIR).setPreferredWidth(50);
	       
	    tcmAux.getColumn(INT_TBL_BUTBUS).setWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBUS).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBUS).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_BUTBUS).setPreferredWidth(0);
	    
            tcmAux.getColumn(INT_TBL_CODEMP).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMP).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMP).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODEMP).setPreferredWidth(0);
                       
            tcmAux.getColumn(INT_TBL_CODLOC).setWidth(0);
            tcmAux.getColumn(INT_TBL_CODLOC).setMaxWidth(0);
            tcmAux.getColumn(INT_TBL_CODLOC).setMinWidth(0);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(0);
            
            tblDat.getTableHeader().setReorderingAllowed(false);
            //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
            //Configurar JTable: Establecer columnas editables.
            vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTBUS);  
            objTblMod.setColumnasEditables(vecAux); 
            vecAux=null;
            
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CONFIR).setCellRenderer(objTblCelRenChk);
            objTblCelRenChk=null;
            
            
	    objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objParSis.getFormatoNumero(),true,true);
            tcmAux.getColumn(INT_TBL_VALPEN).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
	    
            
            objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTBUS).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
                 
            // new ButCot(tblDat, INT_TBL_BUTBUS);   //*****
            
            
            //Configurar JTable: Editor de la tabla. 
            new ZafTblEdi(tblDat);  

            
	    int intCol[]={ INT_TBL_VALPEN };
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
    
    
    
//    
//    private class ButCot extends Librerias.ZafTableColBut.ZafTableColBut_uni{
//        public ButCot(javax.swing.JTable tbl, int intIdx){
//            super(tbl,intIdx, "Cotización.");
//           
//        }
//        public void butCLick() {
//           int intCol = tblDat.getSelectedRow();
//           String strCodEmp = tblDat.getValueAt(intCol,  INT_TBL_CODEMP ).toString();
//           String strCodLoc = tblDat.getValueAt(intCol,  INT_TBL_CODLOC ).toString();
//           String strCodCot = tblDat.getValueAt(intCol,  INT_TBL_NUMCOT ).toString();
//          
//            invocarClase("Ventas.ZafVen01.ZafVen01", strCodEmp, strCodLoc, strCodCot );
//                    
//        }
//    }
//    
//     
     
     
//   private boolean invocarClase(String clase, String strCodemp, String strCodloc, String strCodcot )
//    {
//        boolean blnRes=true;
//        try
//        {
//            //Obtener el constructor de la clase que se va a invocar.
//            Class objVen=Class.forName(clase);
//            Class objCla[]=new Class[4];
//            objCla[0]= objParSis.getClass();
//            objCla[1]= new Integer(0).getClass();
//            objCla[2]= new Integer(0).getClass();
//            objCla[3]= new Integer(0).getClass();
//             
//            System.out.println("Clase : "+objCla );
//            java.lang.reflect.Constructor objCon=objVen.getConstructor(objCla);
//            //Inicializar el constructor que se obtuvo.
//            Object objObj[]=new Object[4];
//            objObj[0]=objParSis;
//            objObj[1]= new Integer(Integer.parseInt(strCodemp));
//            objObj[2]= new Integer(Integer.parseInt(strCodloc));
//            objObj[3]= new Integer(Integer.parseInt(strCodcot));
//            
//                
//            javax.swing.JInternalFrame ifrVen;
//            ifrVen=(javax.swing.JInternalFrame)objCon.newInstance(objObj);
//            
//            dskGen.add(ifrVen,javax.swing.JLayeredPane.DEFAULT_LAYER);
//            ifrVen.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
//                public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
//                }
//                public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
//                    System.gc();
//                }
//                public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
//                }
//                public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
//                }
//                public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
//                }
//                public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
//                }
//                public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
//                }
//            });
//            ifrVen.show();
//        }
//        catch (ClassNotFoundException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (NoSuchMethodException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (SecurityException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (InstantiationException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalAccessException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (IllegalArgumentException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        catch (java.lang.reflect.InvocationTargetException e) 
//        {
//            blnRes=false;
//            objUti.mostrarMsgErr_F1(this, e);
//        }
//        return blnRes;
//    }
//     
     
     
     
        
    private void cargarDatos(){
       vecDat.clear();
        for(int i=0; i<arlDat.size(); i++){
          
                        vecReg=new Vector();
                        vecReg.add(INT_TBL_LIN,"");
                        vecReg.add(INT_TBL_CONFIR, new Boolean( objUti.getStringValueAt(arlDat, i, 0).toString().equals("P")?false:true) );
                        vecReg.add(INT_TBL_NUMDOC, objUti.getStringValueAt(arlDat, i, 1) );
                        vecReg.add(INT_TBL_FECDOC, objUti.getStringValueAt(arlDat, i, 2) );
                        vecReg.add(INT_TBL_NOMCLI, objUti.getStringValueAt(arlDat, i, 3) );
                        vecReg.add(INT_TBL_VALPEN, objUti.getStringValueAt(arlDat, i, 4) ); 
                        vecReg.add(INT_TBL_BUTBUS, " ");
                        vecReg.add(INT_TBL_CODEMP, " "); 
                        vecReg.add(INT_TBL_CODLOC, " "); 
                        vecDat.add(vecReg);
               
            
       }    
                objTblMod.setData(vecDat);  
                tblDat.setModel(objTblMod);
                vecDat.clear();
		
		objTblTotDocCon.calcularTotales();    
                objTblTotDocCon.setValueAt("" +  objTblTotDocCon.getValueAt(0,INT_TBL_VALPEN),0,0 ); 
		    
		
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
    private javax.swing.JScrollPane spnDocContot;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblTotDocCon;
    // End of variables declaration//GEN-END:variables
    
}
