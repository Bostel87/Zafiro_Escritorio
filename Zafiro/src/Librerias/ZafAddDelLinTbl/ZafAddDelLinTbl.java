/*
 * ZafAddDelLinTbl.java
 *
 * Created on 19 de agosto de 2005, 10:34
 */

package Librerias.ZafAddDelLinTbl;

/**
     * Permite que el cursor, en una table, se mueva a la siguiente columna 
     * como si estuviera trabajando en excel, y si esta en la ultima fila, 
     * al dar enter agregara una fila en blanco
     * Este clase recibe un JTable y bï¿½sicamente se encarga de aï¿½adir una fila si el usuario
     * presiona la tecla ENTER o eliminar una fila si el usuario presiona ESCAPE.
     * Para aï¿½adir una fila con ENTER sï¿½lo bastarï¿½ estar en la ï¿½ltima fila de JTable, en
     * cualquier celda, la ï¿½nica observaciï¿½n es que debe pertenecer a la ï¿½ltima fila.
     * Para eliminar una fila con la tecla ESCAPE hay que tener en cuenta que la fila no
     * debe contener ningï¿½n valor en ninguna de sus celdas.
     * Author: Idi Reyes Marcillo
     * 
     * @author  IdiTrix
     * @version 2.2
     */
public class ZafAddDelLinTbl {
    private javax.swing.JTable objTbl;
    private int INT_COL_COUNT=0;  // EL NUMERO DE COLUMNAS DE ESTA TABLA 
    private int[] intColNoSeleccion = null;
    
   
    
    /**
     *  Crea una nueva instancia de ZafAddDelLinTbl
     *
     *  @param objTbl JTable al que se desea aï¿½adir o quitar filas
     */    
    public ZafAddDelLinTbl(javax.swing.JTable objTbl) {
        this.objTbl = objTbl;
        setListeners();
    }
    /**
     *  Crea una nueva instancia de ZafAddDelLinTbl
     *
     *  @param objTbl JTable al que se desea aï¿½adir o quitar filas
     *  @param intColNoSeleccion int[]  Un arreglo de enteros que contiene el numero correspondiente <br>
     *                                  al indice de la columna que no sera seleccionada. 
     */    
    public ZafAddDelLinTbl(javax.swing.JTable objTbl,int[] intColNoSeleccion ) {
        this.objTbl = objTbl;
        this.intColNoSeleccion = intColNoSeleccion;
        setListeners();
    }
    
    private void setListeners(){
       
        /*  Registrando el KeyBoard Listener para la tecla enter
         *  para ke se agregue una fila cuando la tecla enter es presionada
         */
        
        
   
        
        objTbl.addKeyListener(  new java.awt.event.KeyListener(){
            public void keyPressed(java.awt.event.KeyEvent e) {
                javax.swing.JTable  tblAddDelLin =  (javax.swing.JTable)e.getSource();
                int tecla = e.getKeyChar();
                javax.swing.table.DefaultTableModel dafMod = (javax.swing.table.DefaultTableModel)tblAddDelLin.getModel();
                
               
                
                if (tecla == 27){
                  
                   
                    if( tblAddDelLin.getSelectedRow()!=-1 && tblAddDelLin.getSelectedColumn()!=-1 ){
                        
                        if(tblAddDelLin.getRowCount()>1){
                            int intRowSel = tblAddDelLin.getSelectedRow();
                            boolean noEliminar = false;
                            for(int idx=0 ; idx<tblAddDelLin.getColumnCount(); idx++){
                                
                                if(dafMod.getColumnClass(idx)==String.class || dafMod.getColumnClass(idx)==Integer.class || dafMod.getColumnClass(idx)==Double.class || dafMod.getColumnClass(idx)==Float.class){
                                    if(dafMod.getValueAt(intRowSel, idx)!=null){
                                        if(!dafMod.getValueAt(intRowSel, idx).toString().equals("")){
                                            noEliminar=true;
                                        }
                                    }
                                }
                            }
                            
                            
                            if(!noEliminar){
                                dafMod.removeRow(intRowSel);
                                tblAddDelLin.setRowSelectionInterval((tblAddDelLin.getRowCount()==intRowSel)?intRowSel-1:intRowSel,(tblAddDelLin.getRowCount()==intRowSel)?intRowSel-1:intRowSel);
                            }
                        }
                    }
                }
                
                 if(java.awt.event.KeyEvent.VK_ENTER==e.getKeyCode()){
                 //Convierto el ENTER en un TAB para que el foco pase a la siguiente celda de la misma fila y no a la siguiente celda de la misma columna.
                   
                    e.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
                    if (((tblAddDelLin.getRowCount() - 1) == tblAddDelLin.getSelectedRow())){
                        System.out.println("Fila ==> "+ tblAddDelLin.getSelectedRow());
                        dafMod.addRow(new java.util.Vector());
                        
                    }                    
                 }  
                
                
                
                
                
            }
            
            public void keyTyped(java.awt.event.KeyEvent e) {
                
                
            }
            
            
            public void keyReleased(java.awt.event.KeyEvent e) {
                    
            }
        });
        
    }
    /**
     * Verifica si la columna pasada como parametro 
     * es una columna que no debe ser seleccionada
     * @param intColToFind int con el indice de la columan de la tabla que queremos verificar si no se debe seleccionar
     * @return boolean  true si no de ser seleccioada<br>
     *                  false si la columna se puede seleccionar
     */
    private boolean isColNoSelecter(int intColToFind){
           if(intColNoSeleccion!=null)
               for(int idxColObli=0; idxColObli<intColNoSeleccion.length; idxColObli++)
                    if(intColNoSeleccion[idxColObli]==intColToFind )
                        return true;
           return false;
    }
    
    
}
