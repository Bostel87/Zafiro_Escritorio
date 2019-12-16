/*
 * ZafVen25_01.java
 *
 * Created on April 13, 2009, 1:02 PM
 */
   
package Ventas.ZafVen25;
import java.util.Vector;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;

/**
 *
 * @author  jayapata
 */
public class ZafVen25_01 extends javax.swing.JDialog {
    Librerias.ZafParSis.ZafParSis objZafParSis;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod, objTblModDet;
    Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
    ZafUtil objUti;
    //private ZafThreadGUI objThrGUI;
     
    Vector vecCab=new Vector(); 
    public boolean blnEst=false;
    
    final int INT_TBL_LINEA =0; 
    final int INT_TBL_CODEMP=1;
    final int INT_TBL_CODLOC=2;
    final int INT_TBL_TIPDOC=3;
    final int INT_TBL_DIPDOC=4;
    final int INT_TBL_CODDOC=5;
    final int INT_TBL_NUMDOC=6;
    final int INT_TBL_BUTSOL=7;
    final int INT_TBL_AUTSOL=8;
    final int INT_TBL_DENSOL=9;
    final int INT_TBL_CANSOL=10;
    final int INT_TBL_OBSSOLDEV=11;
    
    final int INT_TBL_CODITM=1;
    final int INT_TBL_CODALT=2;
    final int INT_TBL_NOMITM=3;
    final int INT_TBL_MAXDES=4;
    final int INT_TBL_DESSOL=5;
   
    StringBuffer stbins=new StringBuffer(); 
    
    /** Creates new form ZafVen25_01 */
    public ZafVen25_01(java.awt.Frame parent, boolean modal, Librerias.ZafParSis.ZafParSis obj, StringBuffer strDat ) {
        super(parent, modal);
        try{
	    this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
	    initComponents();
            
	    stbins=strDat;
            
            this.setTitle(objZafParSis.getNombreMenu() );
            lblTit.setText(objZafParSis.getNombreMenu());
            
	    objUti = new ZafUtil();
	    
            objTblCelRenLbl = new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            
            
	 }catch (CloneNotSupportedException e){
               objUti.mostrarMsgErr_F1(this, e);
        }
        
        
    }
    
    
   
     

private boolean configuraTblDet(){
       boolean blnRes=false;
       try{
           System.out.println("ZafVen25_01.configuraTblDet");
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODITM,"Cod_Itm");
            vecCab.add(INT_TBL_CODALT,"Cod.Alt.");
	    vecCab.add(INT_TBL_NOMITM,"Nom.Itm.");
	    vecCab.add(INT_TBL_MAXDES,"Max.Des.");
	    vecCab.add(INT_TBL_DESSOL,"Des.Sol.");
	    objTblModDet=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblModDet.setHeader(vecCab);
            tblDet.setModel(objTblModDet);
            //Configurar JTable: Establecer tipo de selección.
            tblDet.setRowSelectionAllowed(true);
            tblDet.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDet,INT_TBL_LINEA);
    	    
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDet.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDet.getColumnModel();
	    
            objTblModDet.setColumnDataType(INT_TBL_MAXDES, objTblModDet.INT_COL_DBL, new Integer(0), null);
            objTblModDet.setColumnDataType(INT_TBL_DESSOL, objTblModDet.INT_COL_DBL, new Integer(0), null);
            
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODITM).setPreferredWidth(50);
            tcmAux.getColumn(INT_TBL_CODALT).setPreferredWidth(100);
            tcmAux.getColumn(INT_TBL_NOMITM).setPreferredWidth(220);
            tcmAux.getColumn(INT_TBL_MAXDES).setPreferredWidth(60);
            tcmAux.getColumn(INT_TBL_DESSOL).setPreferredWidth(60);
            
            
            Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
            objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
            tcmAux.getColumn(INT_TBL_DESSOL).setCellRenderer(objTblCelRenLbl);
            tcmAux.getColumn(INT_TBL_MAXDES).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
            
            //tcmAux.getColumn(INT_TBL_DESCLI).setCellRenderer(new RenderDecimales() );
             
             
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODITM);
            objTblModDet.setSystemHiddenColumns(arlColHid, tblDet);
            arlColHid=null;
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDet);
            tblDet.getTableHeader().setReorderingAllowed(false);
            
           objTblModDet.setModoOperacion(objTblModDet.INT_TBL_EDI);
           tcmAux=null; 
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    
  
        
    
    
private boolean configuraTbl(){
       boolean blnRes=false;
       try{
           System.out.println("ZafVen25_01.configuraTbl");
            //Configurar JTable: Establecer el modelo.
            vecCab.clear();
            vecCab.add(INT_TBL_LINEA,"");
            vecCab.add(INT_TBL_CODEMP,"Cod_emp");
            vecCab.add(INT_TBL_CODLOC,"Cod.Loc.");
	    vecCab.add(INT_TBL_TIPDOC,"Cod.TipDoc.");
	    vecCab.add(INT_TBL_DIPDOC,"Tip.CorDoc.");
	    vecCab.add(INT_TBL_CODDOC,"Cod.Doc.");
	    vecCab.add(INT_TBL_NUMDOC,"Num.Doc.");
	    vecCab.add(INT_TBL_BUTSOL,"...");
	    vecCab.add(INT_TBL_AUTSOL,"Autorizado.");
	    vecCab.add(INT_TBL_DENSOL,"Denegado.");
	    vecCab.add(INT_TBL_CANSOL,"Cancelado.");
	    vecCab.add(INT_TBL_OBSSOLDEV,"");
                    
            objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
            objTblMod.setHeader(vecCab);
            tblDat.setModel(objTblMod);
            
            //Configurar JTable: Establecer tipo de selección.
            tblDat.setRowSelectionAllowed(true);
            tblDat.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            new Librerias.ZafColNumerada.ZafColNumerada(tblDat,INT_TBL_LINEA);
    	    
	    //Configurar JTable: Establecer el tipo de redimensionamiento de las columnas.
            tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
            javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
	    
            //Configurar JTable: Establecer columnas editables.
            Vector vecAux=new Vector();
            vecAux.add("" + INT_TBL_BUTSOL);
            vecAux.add("" + INT_TBL_AUTSOL);
            vecAux.add("" + INT_TBL_DENSOL);
            vecAux.add("" + INT_TBL_CANSOL);
            objTblMod.setColumnasEditables(vecAux);
            vecAux=null;
            
	    //Tamaño de las celdas
            tcmAux.getColumn(INT_TBL_LINEA).setPreferredWidth(25);
            tcmAux.getColumn(INT_TBL_CODLOC).setPreferredWidth(55);
            tcmAux.getColumn(INT_TBL_DIPDOC).setPreferredWidth(110);
            tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(65);
            tcmAux.getColumn(INT_TBL_BUTSOL).setPreferredWidth(20);
            tcmAux.getColumn(INT_TBL_AUTSOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_DENSOL).setPreferredWidth(70);
            tcmAux.getColumn(INT_TBL_CANSOL).setPreferredWidth(70);
            
            //tcmAux.getColumn(INT_TBL_DESCLI).setCellRenderer(new RenderDecimales() );
             
             
            /* Aqui se agrega las columnas que van 
                ha hacer ocultas
             * */
            ArrayList arlColHid=new ArrayList();
            arlColHid.add(""+INT_TBL_CODEMP);
            arlColHid.add(""+INT_TBL_TIPDOC);
            arlColHid.add(""+INT_TBL_CODDOC);
            arlColHid.add(""+INT_TBL_BUTSOL);
            arlColHid.add(""+INT_TBL_OBSSOLDEV);
            objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
            arlColHid=null;
            
            new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
            tblDat.getTableHeader().setReorderingAllowed(false);
            
            Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut objTblCelRenBut=new Librerias.ZafTblUti.ZafTblCelRenBut.ZafTblCelRenBut();
            tcmAux.getColumn(INT_TBL_BUTSOL).setCellRenderer(objTblCelRenBut);
            objTblCelRenBut=null;
            new ButCotSol(tblDat, INT_TBL_BUTSOL);   //*****
                
            Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
            Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk; 
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_AUTSOL).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_AUTSOL).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_DENSOL);
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_CANSOL);
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null; 
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_DENSOL).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_DENSOL).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_AUTSOL);
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_CANSOL);
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null; 
            
            objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
            tcmAux.getColumn(INT_TBL_CANSOL).setCellRenderer(objTblCelRenChk);
                objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
                tcmAux.getColumn(INT_TBL_CANSOL).setCellEditor(objTblCelEdiChk);
                objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
                public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
                }        
                public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
                    int intCol = tblDat.getSelectedRow();
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_AUTSOL);
                    tblDat.setValueAt( new Boolean(false), intCol, INT_TBL_DENSOL);
                }
               });
            objTblCelRenChk=null;
            objTblCelEdiChk=null; 
            
           objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);
           
           
            //Configurar JTable: Establecer el ListSelectionListener.
            javax.swing.ListSelectionModel lsm=tblDat.getSelectionModel();
            lsm.addListSelectionListener(new ZafLisSel());
            
           tcmAux=null; 
           blnRes=true;
	 }catch(Exception e) {  blnRes=false;   objUti.mostrarMsgErr_F1(this,e);  }
        return blnRes;
      }
    
  




    
      /**
     * Esta clase implementa la interface "ListSelectionListener" para determinar
     * cambios en la selección. Es decir, cada vez que se selecciona una fila
     * diferente en el JTable se ejecutará el "ListSelectionListener".
     */
    private class ZafLisSel implements javax.swing.event.ListSelectionListener
    {
        public void valueChanged(javax.swing.event.ListSelectionEvent e)
        {
            javax.swing.ListSelectionModel lsm=(javax.swing.ListSelectionModel)e.getSource();
            int intMax=lsm.getMaxSelectionIndex();
            String strAux;
            if (!lsm.isSelectionEmpty())
            {  
               
              objTblModDet.removeAllRows();
              consultarDatDet();
              
                    
            }
        }
    }
     


             
private class ButCotSol extends Librerias.ZafTableColBut.ZafTableColBut_uni{
    public ButCotSol(javax.swing.JTable tbl, int intIdx){
        super(tbl,intIdx, "Ver Solicitud de Devolución.");
    }
    public void butCLick() {
       int intCol = tblDat.getSelectedRow();
        llamarVentanaSol(intCol);
    }
}
private void llamarVentanaSol(int intCol){
   int intCodEmp = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODEMP  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODEMP  ).toString()) );
   int intCodLoc = Integer.parseInt( (tblDat.getValueAt(intCol,  INT_TBL_CODLOC  )==null?"0":tblDat.getValueAt(intCol,  INT_TBL_CODLOC  ).toString()) );
   int intCodTipDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_TIPDOC).toString() );
   int intCodDoc=Integer.parseInt( tblDat.getValueAt(intCol, INT_TBL_CODDOC).toString() );
           
   Ventas.ZafVen11.ZafVen11 obj = new Ventas.ZafVen11.ZafVen11(objZafParSis,  intCodEmp, intCodLoc,  intCodTipDoc , intCodDoc );
   this.getParent().add(obj,javax.swing.JLayeredPane.DEFAULT_LAYER);
   obj.show(); 
                 
}
    
    
    
    
    
    
    
 public boolean acepta(){
        return blnEst;
}    
    
    
    
    
    
    
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTit = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblDat = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDet = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        butace = new javax.swing.JButton();
        butCan = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        lblTit.setText("jLabel1");
        jPanel1.add(lblTit);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerLocation(120);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

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

        jSplitPane1.setTopComponent(jScrollPane1);

        tblDet.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblDet);

        jSplitPane1.setRightComponent(jScrollPane2);

        jPanel2.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jPanel5.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jPanel5);

        butace.setText("Aceptar");
        butace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butaceActionPerformed(evt);
            }
        });
        jPanel4.add(butace);

        butCan.setText("Cancelar");
        butCan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                butCanActionPerformed(evt);
            }
        });
        jPanel4.add(butCan);

        jPanel3.add(jPanel4);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-500)/2, (screenSize.height-320)/2, 500, 320);
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        
        configuraTbl();
        configuraTblDet();
        
        consultarDat();
       
        
    }//GEN-LAST:event_formWindowOpened

    private void butCanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butCanActionPerformed
        // TODO add your handling code here:
        
       blnEst=false; 
       salir();
        
    }//GEN-LAST:event_butCanActionPerformed

    
     private void salir(){
         dispose();
     }
     
    
    private void butaceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butaceActionPerformed
        // TODO add your handling code here:
        
        
        String strMsg2 = "¿ ESTÁ SEGURO QUE DESEA REALIZAR ESTA OPERACIÓN ?";
        javax.swing.JOptionPane oppMsg2=new javax.swing.JOptionPane();
        String strTit2="Mensaje del sistema Zafiro";
        if(!(oppMsg2.showConfirmDialog(this,strMsg2,strTit2,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE) == 1 ) ) {
            
            guardarDat();
            blnEst=true; 
            salir();
            
        }

    }//GEN-LAST:event_butaceActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        blnEst=false; 
        salir();       
    }//GEN-LAST:event_formWindowClosing
    
    
    
    
   
private boolean guardarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 String strSql="";
 String strAut="P";
 try{
     System.out.println("zafVen25_01.guardarDat ");
     conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
     if(conn!=null){
        conn.setAutoCommit(false);
        stmLoc=conn.createStatement();
        for(int i=0; i<tblDat.getRowCount(); i++){
             if( (tblDat.getValueAt(i, INT_TBL_AUTSOL).toString().equals("true")) || 
                 (tblDat.getValueAt(i, INT_TBL_DENSOL).toString().equals("true")) || 
                 (tblDat.getValueAt(i, INT_TBL_CANSOL).toString().equals("true")) ){
           
                    if((tblDat.getValueAt(i, INT_TBL_AUTSOL).toString().equals("true")))
                        strAut="A";
                    else if((tblDat.getValueAt(i, INT_TBL_DENSOL).toString().equals("true")))
                        strAut="D";
                    else if((tblDat.getValueAt(i, INT_TBL_CANSOL).toString().equals("true")))
                        strAut="C";
                    
                    strSql="UPDATE tbm_cabsoldevven SET  st_aut='"+strAut+"', tx_obsaut='"+(tblDat.getValueAt(i, INT_TBL_OBSSOLDEV)==null?"":tblDat.getValueAt(i, INT_TBL_OBSSOLDEV).toString() )+"' " +
                           ", fe_aut="+objZafParSis.getFuncionFechaHoraBaseDatos()+"  WHERE co_emp="+tblDat.getValueAt(i, INT_TBL_CODEMP).toString()+" AND co_loc="+tblDat.getValueAt(i, INT_TBL_CODLOC).toString()+" " +
                           " AND co_tipdoc="+tblDat.getValueAt(i, INT_TBL_TIPDOC).toString()+" AND co_doc="+tblDat.getValueAt(i, INT_TBL_CODDOC).toString();
                    System.out.println("zafVen25_01.guardarDat " + strSql);
                    stmLoc.executeUpdate(strSql);
              }
        }
        stmLoc.close();
        stmLoc=null;
        conn.commit(); 
        conn.close();
        conn=null;
        blnRes=true;
    }
 }
 catch(java.sql.SQLException Evt) {
     blnRes=false; 
     objUti.mostrarMsgErr_F1(this, Evt);  
 }
 catch(Exception Evt) { 
     blnRes=false; 
     objUti.mostrarMsgErr_F1(this, Evt); 
 }
 return blnRes;          
}   
    
    
       
    
    
private boolean consultarDat(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
     System.out.println("ZafVen25_01.consultarDat");
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();
      
      strSql="SELECT co_emp, co_loc, co_tipdoc, tx_descor, co_doc, ne_numdoc , tx_obs FROM ( "+stbins.toString()+" "+
      " ) AS x   GROUP BY co_emp, co_loc, co_tipdoc, tx_descor, co_doc, ne_numdoc, tx_obs  " +
      "  ORDER BY co_emp, co_loc, ne_numdoc "; 
      System.out.println("ZafVen25_01.consultaDat " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODEMP, rstLoc.getString("co_emp") );
	  vecReg.add(INT_TBL_CODLOC, rstLoc.getString("co_loc") );
	  vecReg.add(INT_TBL_TIPDOC, rstLoc.getString("co_tipdoc") );
	  vecReg.add(INT_TBL_DIPDOC, rstLoc.getString("tx_descor") );
	  vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
	  vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
	  vecReg.add(INT_TBL_BUTSOL,"..");
	  vecReg.add(INT_TBL_AUTSOL, new Boolean(true) );
	  vecReg.add(INT_TBL_DENSOL, new Boolean(false) );
	  vecReg.add(INT_TBL_CANSOL, new Boolean(false) );
          vecReg.add(INT_TBL_OBSSOLDEV, rstLoc.getString("tx_obs") );
         vecData.add(vecReg);
       }
         objTblMod.setData(vecData);
         tblDat .setModel(objTblMod);
      rstLoc.close(); 
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    
    
    
    
private boolean consultarDatDet(){
 boolean blnRes=false;
 java.sql.Connection conn;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 Vector vecData;
 try{
     System.out.println("ZafVen25_01.consultarDatDet ");
   conn=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
   if(conn!=null){
      stmLoc=conn.createStatement();
      vecData = new Vector();
      
      strSql=" SELECT * FROM ( " +
      " SELECT a1.co_itm, a1.tx_codalt, a1.tx_nomitm, a2.nd_pordesvenmax , a1.nd_candev  " +
      " ,round(( a2.nd_preunivenlis * (1-( a2.nd_pordesvenmax /100))),2) as preLis " +
      " ,round(( a2.nd_preuni * (1-( (a2.nd_pordes+a1.nd_candev) /100))),2) as preVta " +
      " FROM tbm_detsoldevven AS a1 " +
      " INNER JOIN tbm_detmovinv AS a2 ON(a2.co_emp=a1.co_emp AND a2.co_loc=a1.co_locrel AND a2.co_tipdoc=a1.co_tipdocrel AND a2.co_doc=a1.co_docrel AND a2.co_reg=a1.co_reg) " +
      " WHERE a1.co_emp="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODEMP)+" and a1.co_loc="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODLOC)+" " +
      " and a1.co_tipdoc="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_TIPDOC)+" and a1.co_doc="+objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_CODDOC)+" " +
      "  ) AS x WHERE  preLis > preVta  ";  
      System.out.println("ZafVen25_01.consultarDatDet " + strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      rstLoc=stmLoc.executeQuery(strSql);
      while(rstLoc.next()){
          java.util.Vector vecReg = new java.util.Vector();
          vecReg.add(INT_TBL_LINEA,"");
          vecReg.add(INT_TBL_CODITM, rstLoc.getString("co_itm") );
	  vecReg.add(INT_TBL_CODALT, rstLoc.getString("tx_codalt") );
	  vecReg.add(INT_TBL_NOMITM, rstLoc.getString("tx_nomitm") );
	  vecReg.add(INT_TBL_MAXDES, rstLoc.getString("nd_pordesvenmax") );
	  vecReg.add(INT_TBL_DESSOL, rstLoc.getString("nd_candev") );
         vecData.add(vecReg);
       }
         objTblModDet.setData(vecData);
         tblDet .setModel(objTblModDet);
      rstLoc.close(); 
      rstLoc=null;
      stmLoc.close();
      stmLoc=null;
    conn.close();
    conn=null;
    blnRes=true;
  }}catch(java.sql.SQLException Evt) {  blnRes=false; objUti.mostrarMsgErr_F1(this, Evt);  }
    catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(this, Evt); }
 return blnRes;          
}   
    
    
         
 
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton butCan;
    private javax.swing.JButton butace;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblTit;
    private javax.swing.JTable tblDat;
    private javax.swing.JTable tblDet;
    // End of variables declaration//GEN-END:variables
    
}
