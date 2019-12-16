/*
 * ZaftblTotales.java
 *
 * Created on 15 de febrero de 2005, 14:10
 */

package Librerias.ZafTblTotales;

/**
 * Calcula totales de columnas de 2 tablas, tambien maneja el scrollbar y las vistas<br>
 * de forma que mientras mueves el srollbar horizontal de la tabla donde van los totales<br>
 * tambien se actualice la vista en la tabla principal.
 * @author IdiTrix
 * @version 1.0
 */
public class ZafTblTotales {
       javax.swing.JScrollPane spnDetalle;
       javax.swing.JScrollPane spnTotales;
       javax.swing.JTable  tblDetalle;
       javax.swing.JTable  tblTotales;
       javax.swing.JPanel  panTablas;       
       javax.swing.JInternalFrame  jfrMain;
       private int intColTotales[]; // Columnas a las que se calculara el total
       private boolean blnActivo=false;
       private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.

       
       /**
        * Crea un nuevo ZafTblTotales, es necesario que las tablas enviadas como parametros esten dentro de un scrollpane (c/una), <br>
        * y que esos scrollpane esten dentro del mismo JPanel.
        * @param tblDetalle Tabla donde estan todos los datos
        * @param tblTotales Tabla donde iran los totales
        */
       public ZafTblTotales(javax.swing.JTable tblDetalle, javax.swing.JTable tblTotales) {
            this.tblDetalle = tblDetalle;
            this.tblTotales = tblTotales;
            try{
                 java.awt.Container ctnTmp = tblDetalle.getParent();
                 //Obteniendo el ScrollPane de la tabla de detalle
                 while(!(ctnTmp instanceof javax.swing.JScrollPane)){
                     ctnTmp = ctnTmp.getParent();
                 }
                 spnDetalle = (javax.swing.JScrollPane) ctnTmp;

                 //Obteniendo el JPanel que contiene a las tablas
                 while(!(ctnTmp instanceof javax.swing.JPanel)){
                     ctnTmp = ctnTmp.getParent();
                 }
                 panTablas = (javax.swing.JPanel) ctnTmp;

                 //Obteniendo el JInternalFrame o JFrame
                 while(!(ctnTmp instanceof javax.swing.JInternalFrame) && ctnTmp != null){
                     ctnTmp = ctnTmp.getParent();
                 }
                 jfrMain = (ctnTmp==null)?null:(javax.swing.JInternalFrame) ctnTmp;

                 //Obteniendo el ScrollPane de la tabla de totales
                 ctnTmp = tblTotales.getParent();
                 while(!(ctnTmp instanceof javax.swing.JScrollPane)){
                     ctnTmp = ctnTmp.getParent();
                 }
                 spnTotales = (javax.swing.JScrollPane) ctnTmp;
                 ((javax.swing.table.DefaultTableModel)tblTotales.getModel()).setRowCount(1);
                 if(jfrMain != null){
                       jfrMain.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
                            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                                spnTotales.getColumnHeader().setVisible(false); 
                            }
                            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                            }
                            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {

                            }
                            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
                            }
                            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
                            }
                            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
                            }
                            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                            }
                        });        
                 }else{
                            spnTotales.getColumnHeader().setVisible(false); 
                 }

                 /*
                  *
                  */
                 spnDetalle.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                 spnTotales.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);         
                 tblDetalle.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);  
                 tblTotales.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
                 spnTotales.setPreferredSize(new java.awt.Dimension(320, 35));

                 ResizeTbl();
                 setListenerTotal();
                 
            } 
             catch(Exception Evt)
             {
                 System.out.println(Evt.toString());
              }     
        
    }

    private void ResizeTbl(){
         //Asignando tamaÃ±o y definiendo ke no se pueda cambiar el tamaÃ±o 
         tblDetalle.getTableHeader().setReorderingAllowed(false);
         if(tblTotales.getColumnCount() <=tblDetalle.getColumnCount()){
             for(int intCol=0 ; intCol< tblDetalle.getColumnCount();intCol++)
                tblTotales.getColumnModel().getColumn(intCol).setPreferredWidth(tblDetalle.getColumnModel().getColumn(intCol).getPreferredWidth());
         }
         
   }    
    private void setListenerTotal(){
        /*   
         *   Se esta Cambiando el tamaï¿½o de una columna intCol en la Tabla principal
         *   para lo cual se cambia el tamaï¿½o de la columna de 
         *   la tabla totales
         */
        resizeCol objResizeCol =  new resizeCol();
        objLinLis.add(objResizeCol);

        /*
         *  Se esta utilizando el scroll bar horizontal del spnTotales
         *  asi ke actualizamos el area visible en la tabla principal
         */
        
        VistaTotal objVistaTotal = new VistaTotal();
        objLinLis.add(objVistaTotal);

        /*
         *  Se esta da un tamaï¿½o mas grande o pequeï¿½o al spnTotales en el 
         *  momento ke aperece o desaparece la scrollbar horizontal
         */
        
        TamanoTotal objTamanoTotal = new TamanoTotal();
        objLinLis.add(objTamanoTotal);

        /*
         *  
         *  Asi ke actualizamos el area visible en la tabla total
         */
        AreaVisibleTotal objAreaVisibleTotal = new AreaVisibleTotal();
        objLinLis.add(objAreaVisibleTotal);
        
    }
    
    private void addListeners(){
        javax.swing.JScrollBar sbrTotal = spnTotales.getHorizontalScrollBar();
        javax.swing.JScrollBar sbrCentro = spnDetalle.getHorizontalScrollBar();

        tblDetalle.addComponentListener((java.awt.event.ComponentAdapter)objLinLis.get(0) );
        sbrTotal.addAdjustmentListener((java.awt.event.AdjustmentListener)objLinLis.get(1));      
        sbrTotal.addComponentListener((java.awt.event.ComponentAdapter)objLinLis.get(2));
        sbrCentro.addAdjustmentListener((java.awt.event.AdjustmentListener)objLinLis.get(3));      
        
    }

    private void removeListeners(){
        javax.swing.JScrollBar sbrTotal = spnTotales.getHorizontalScrollBar();
        javax.swing.JScrollBar sbrCentro = spnDetalle.getHorizontalScrollBar();
        
        tblDetalle.removeComponentListener((java.awt.event.ComponentAdapter)objLinLis.get(0));
        sbrTotal.removeAdjustmentListener((java.awt.event.AdjustmentListener)objLinLis.get(1));      
        sbrTotal.removeComponentListener((java.awt.event.ComponentAdapter)objLinLis.get(2));
        sbrCentro.removeAdjustmentListener((java.awt.event.AdjustmentListener)objLinLis.get(3));      
    }
    
    private class resizeCol extends java.awt.event.ComponentAdapter{
        public void componentResized(java.awt.event.ComponentEvent evt) {
            if(tblDetalle.getTableHeader().getResizingColumn()!=null) {
                int intCol = tblDetalle.getTableHeader().getResizingColumn().getModelIndex();
                if(intCol>=0 || intCol<tblDetalle.getColumnModel().getColumnCount())
                    tblTotales.getColumnModel().getColumn(intCol).setPreferredWidth(tblDetalle.getColumnModel().getColumn(intCol).getPreferredWidth());
            }
        }
    }
    private class VistaTotal implements java.awt.event.AdjustmentListener{
        public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {

            java.awt.Point pntVista = spnDetalle.getViewport().getViewPosition();
            pntVista.x = spnTotales.getViewport().getViewPosition().x;
            spnDetalle.getViewport().setViewPosition(pntVista);
            spnDetalle.getViewport().repaint(spnDetalle.getViewport().getViewRect());

       }
    }
    
    private class TamanoTotal extends java.awt.event.ComponentAdapter {
        public void componentHidden(java.awt.event.ComponentEvent evt){    
                spnTotales.setPreferredSize(new java.awt.Dimension(spnTotales.getWidth(), 19));
                spnTotales.doLayout();
                panTablas.repaint();
                panTablas.doLayout();
         }       
        public void componentShown(java.awt.event.ComponentEvent evt){
                spnTotales.setPreferredSize(new java.awt.Dimension(spnTotales.getWidth(), 35));
                panTablas.repaint();
                panTablas.doLayout();
        }
    }
    
    private class AreaVisibleTotal implements java.awt.event.AdjustmentListener{
        public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
            if(blnActivo){
                java.awt.Point pntVista = spnTotales.getViewport().getViewPosition();
                pntVista.x = spnDetalle.getViewport().getViewPosition().x;
                spnTotales.getViewport().setViewPosition(pntVista);
                spnTotales.getViewport().repaint(spnTotales.getViewport().getViewRect());                    
//              spnTotales.getViewport().repaint(spnTotales.getViewport().getViewRect());
//              panTablas.repaint();
//               panTablas.doLayout();
//              spnTotales.getViewport().repaint(spnTotales.getViewport().getViewRect());
            }
       }
    }

    /**
     * Designa cuales son las columnas a las que se calculara el total.
     * @param intColTotales Un arreglo de enteros donde estarn los numeros correspondientes <br>
     * a las columnas a ser calculadas
     */    
    public void setColTotales(int intColTotales[]){
        this.intColTotales = intColTotales;
    }
    /**
     * Calcula total(es) de la(s) columna(s) que se designaron(metodo setColTotales) a calcular
     */    
    public void calcularTotal(){
        double dblTotales[] =    new double[intColTotales.length];
        //Inicializando dblTotales 
        for(int i = 0 ; i < dblTotales.length ; i++ )
              dblTotales[i] = 0;
        
        //CAlculando los totales de la tabla
        for(int intRow = 0 ; intRow < tblDetalle.getRowCount() ; intRow ++ ){
               for(int intCols = 0 ; intCols < intColTotales.length ; intCols++ ){
                   dblTotales[intCols] += getDouble(tblDetalle.getValueAt( intRow ,intColTotales[intCols] ));
                }
         }
        
        //Poniendo los totales en la tabla
        for(int i = 0 ; i < dblTotales.length ; i++ ){
              tblTotales.setValueAt(new Double(dblTotales[i]),0,intColTotales[i]);
        }
    }
    private double getDouble(Object obj){
        if(obj==null)
            return 0;
        String str = obj + "";
        return (str.equals(""))?0:Double.parseDouble(str);
    }
    /*
     * True si es que los listener van a estar activos
     * False si es que los listener no van a hacer nada
     */
    public void isActivo(boolean blnIsActivo){
        if(blnActivo != blnIsActivo){
            if(!blnIsActivo){
                removeListeners();
            }else{
                addListeners();
            }
            blnActivo = blnIsActivo;
        }
    }
}
