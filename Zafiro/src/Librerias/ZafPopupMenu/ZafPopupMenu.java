/*
 * MiMenu.java
 *
 * Created on 8 de julio de 2004, 16:14
 */

package Librerias.ZafPopupMenu;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.datatransfer.*;
import java.util.*;

/**
 * Menu emergente para trabajar con un JTable, incluye opciones de copiar, pegar, <br>
 * sel. filas, sel. columna, ocultar columna.
 * @author Idi Reyes Marcillo
 */
public class ZafPopupMenu extends javax.swing.JPopupMenu{
    javax.swing.JMenuItem  mnuCopiar,mnuPegar,mnuSelFila, mnuSelCol, mnuSelTodo,
                            mnuInsFila, mnuEliFila, mnuBorFila,
                            mnuOcultaCol, mnuVerTodaCol;
    javax.swing.JMenu mnuMuestraCol;
    /*Listener para el menu de columnas ocultas.*/
    LisMnuMostrar ObjLisMnuMostrar;
    PortaPapelAdapter BufTbl;
    javax.swing.JTable ObjTbl;
    javax.swing.table.DefaultTableModel ObjTblModelo;

    Vector ColOcultas;     
    java.util.Hashtable htbColOcultas;     
    
    javax.swing.JSeparator sep1, sep2, sep3, sep4;  
    int Row_Clk, Col_Clk;
    
    /**
     * Crea un nuevo zafPopupMenu para trabajar con un JTable enviado por parametro
     * @param Obj un JTable ya iniciado, al que se le asignara el menu emergente ej:<br>
     * zafPopupMenu objMenu = new zafPopupMenu(tblPrueba);
     */
    public ZafPopupMenu(javax.swing.JTable Obj) {
        ObjTbl = Obj;
        ObjTblModelo = (javax.swing.table.DefaultTableModel)Obj.getModel();
        ColOcultas = new Vector();
        htbColOcultas = new java.util.Hashtable();
        
        ObjLisMnuMostrar = new LisMnuMostrar();
            ObjTbl.addMouseListener(new java.awt.event.MouseAdapter() {
             public void mouseClicked(java.awt.event.MouseEvent evt) {
                    Presenta(evt);
                }
            });
        
        ObjTbl.setRowSelectionAllowed(true);
        ObjTbl.setColumnSelectionAllowed(true);
        ObjTbl.getTableHeader().setReorderingAllowed(false);

        BufTbl = new PortaPapelAdapter(ObjTbl);    
        
        mnuCopiar = new javax.swing.JMenuItem("Copiar");
            mnuCopiar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuCopiarActionPerformed(evt);
                }
            });        

        mnuPegar = new javax.swing.JMenuItem("Pegar");
            mnuPegar.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuPegarActionPerformed(evt);
                }
            });        

        sep1 = new javax.swing.JSeparator();//Separador

        mnuSelFila = new javax.swing.JMenuItem("Seleccionar Fila");
            mnuSelFila.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuSelFilaActionPerformed(evt);
                }
            });        

        mnuSelCol = new javax.swing.JMenuItem("Seleccionar Columna");
            mnuSelCol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuSelColActionPerformed(evt);
                }
            });        

        mnuSelTodo = new javax.swing.JMenuItem("Seleccionar Todo");
            mnuSelTodo.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuSelTodoActionPerformed(evt);
                }
            });        

        sep2 = new javax.swing.JSeparator(); //Separador
        
        mnuInsFila = new javax.swing.JMenuItem("Insertar Fila");
            mnuInsFila.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuInsFilaActionPerformed();
                }
            });        

        mnuEliFila = new javax.swing.JMenuItem("Eliminar Fila");
            mnuEliFila.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuEliFilaActionPerformed();
                }
            });        

        mnuBorFila = new javax.swing.JMenuItem("Borrar Contenido");
            mnuBorFila.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuBorFilaActionPerformed(evt);
                }
            });        

        sep3 = new javax.swing.JSeparator(); //Separador
        sep4 = new javax.swing.JSeparator(); //Separador
        
        mnuOcultaCol = new javax.swing.JMenuItem("Ocultar Columna");
            mnuOcultaCol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuOcultaColActionPerformed(evt);
                }
            });        

        mnuMuestraCol = new javax.swing.JMenu("Mostrar Columna");
        
        mnuVerTodaCol = new javax.swing.JMenuItem("Todas las Columnas");
        mnuMuestraCol.add(mnuVerTodaCol); 
        
        
            mnuVerTodaCol.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    mnuVerTodaColActionPerformed(evt);
                }
            });        
        
        
        add(mnuCopiar);
        add(mnuPegar);
        add(sep1);
        add(mnuSelFila);
        add(mnuSelCol);
        add(mnuSelTodo);
        add(sep2);
        add(mnuInsFila);
        add(mnuEliFila);
        add(mnuBorFila);
        add(sep3);
        add(mnuOcultaCol);
        add(mnuMuestraCol);
    }
    private void mnuCopiarActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Copiar
         */
        BufTbl.copiar();        
    }    
    private void mnuPegarActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en pegar
         */
        BufTbl.pegar();
    }    
    private void mnuSelFilaActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en seleccionar fila
         */
        ObjTbl.setRowSelectionInterval(Row_Clk, Row_Clk);
        ObjTbl.setColumnSelectionInterval(0 ,ObjTbl.getColumnCount() -1);
    }    
    private void mnuSelColActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Seleccionar Columna
         */
        ObjTbl.setRowSelectionInterval(0, ObjTbl.getRowCount()-1);
        ObjTbl.setColumnSelectionInterval(Col_Clk ,Col_Clk);
    }    
    private void mnuSelTodoActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Seleccionar Todo
         */
        ObjTbl.setRowSelectionInterval(0, ObjTbl.getRowCount()-1);
        ObjTbl.setColumnSelectionInterval(0 ,ObjTbl.getColumnCount() -1);
    }    
    private void mnuInsFilaActionPerformed() {
        /*
         * Ud dio click en Insertar Fila
         */
//        Vector Arbolito = new Vector() ;
//        
//        Arbolito.addElement(new javax.swing.JTree());
//        Arbolito.addElement(new Boolean(true));
        //Arbolito.addElement(new javax.swing.JTree());
        //Arbolito.addElement(new javax.swing.JTree());
        
        ObjTblModelo.insertRow(Row_Clk, new Vector());  
        
    }    
    private void mnuEliFilaActionPerformed() {
        /*
         * Ud dio click en Eliminar Fila
         */
        ObjTblModelo.removeRow(Row_Clk);
    }    
    private void mnuBorFilaActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Borrar contenido Fila
         *    Uso lo que hace Eliminar Fila y luego Insertar Fila
         */
        mnuEliFilaActionPerformed();
        mnuInsFilaActionPerformed();
    }    
    
    private void mnuOcultaColActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Ocultar Columna
         *    
         */
         ocultaCol(Col_Clk);
    }    
    
    public void ocultaCol(int intColumna){
        Vector ColDat = new Vector();
        javax.swing.table.TableColumn Prb = ObjTbl.getColumnModel().getColumn(intColumna);
        
        //Guardando el Ancho de la Columna
            ColDat.addElement(new Integer(Prb.getWidth()));
            ColDat.addElement(new Integer(Prb.getMaxWidth()));
            ColDat.addElement(new Integer(Prb.getMinWidth()));
            ColDat.addElement(new Integer(Prb.getPreferredWidth()));
            
        /*Guardando en el hashtable los datos de la columna 
         *y la posicion de la columna como identificador*/  
            htbColOcultas.put(new Integer(intColumna) , ColDat);

        /*Actualizando el Vector de Columnas Ocultas*/
            ColOcultas.addElement(new String((intColumna+1) + ". " + Prb.getHeaderValue().toString()) );
        
        /*Asignando anchos de manera que la columna quede oculta*/
            Prb.setWidth(0);
            Prb.setMaxWidth(0);
            Prb.setMinWidth(0);
            Prb.setResizable(false); 
            Prb.setPreferredWidth(0);
    }
    private void mnuVerTodaColActionPerformed(java.awt.event.ActionEvent evt) {
        /*
         * Ud dio click en Mostrar todas las columnas que estaban ocultas
         */
        verColOcultas();
    }    

    public void verColOcultas(){
        for(int idx=0; idx<ColOcultas.size() ;idx++)
             ColVisible( Integer.parseInt(ColOcultas.elementAt(idx).toString().substring(0,   ColOcultas.elementAt(idx).toString().indexOf(".")))-1  );   
        
        ColOcultas.removeAllElements();
    }
    private void MenuMostrar(){
        /*
         * Metodo para actualizar el menu de "Mostrar Columnas"
         *    
         */
        mnuMuestraCol.removeAll();
        mnuMuestraCol.add(mnuVerTodaCol);
        mnuMuestraCol.add(sep4);
        
        for(int idx=0; idx<ColOcultas.size();idx++)
            mnuMuestraCol.add(new javax.swing.JMenuItem( ColOcultas.elementAt(idx).toString())).addActionListener(ObjLisMnuMostrar);
        
        if((ObjTbl.getColumnCount()-ColOcultas.size()) ==1)
           mnuOcultaCol.setEnabled(false);
        else
           mnuOcultaCol.setEnabled(true);
        
    }    
    
private class LisMnuMostrar implements ActionListener
{
		public void actionPerformed(ActionEvent e)
		{
			JMenuItem objCom = (JMenuItem)e.getSource();
                        
                        /*Obtengo el numero de la ubicacion de la columna */
                         int idx = Integer.parseInt(e.getActionCommand().substring(0,e.getActionCommand().indexOf(".")))-1;

                         /* Utilizo la funcion creeada para hacer visible   *
                          * la culmna pasada por parametro                  */
                         ColVisible(idx);

                }
}
public void ColVisible(int idx){

    /* Obtengo el ancho, maxwidth, minwidth y PreferrerdWidth, *
     * que tenia la columna antes de ser ocultada; y se lo     *
     * asigno nuevamente.                                      */
    java.util.Vector vecDatosCol = (Vector)htbColOcultas.get(new Integer(idx));
    /* 
     * Si es ke la columna estaba oculta se procede a mostrarla
     * caso contrario no se realiza nada.
     */
    if(vecDatosCol!=null){
          ObjTbl.getColumnModel().getColumn(idx).setWidth( 
                 Integer.parseInt(vecDatosCol.elementAt(0).toString())
           );
           ObjTbl.getColumnModel().getColumn(idx).setMaxWidth( 
                 Integer.parseInt(vecDatosCol.elementAt(1).toString())
           );
           ObjTbl.getColumnModel().getColumn(idx).setMinWidth( 
                 Integer.parseInt(vecDatosCol.elementAt(2).toString())
           );
                            
           ObjTbl.getColumnModel().getColumn(idx).setPreferredWidth( 
                 Integer.parseInt(vecDatosCol.elementAt(3).toString())
           );
                            
           ObjTbl.getColumnModel().getColumn(idx).setResizable(true); 
           
           htbColOcultas.remove(new Integer(idx));

        /*Quito el elemento del Vector de columnas ocultas para que no 
         *aparezca en el menu de col. ocultas.                      */ 
          ColOcultas.removeElement((idx+1) + ". " + ObjTbl.getColumnModel().getColumn(idx).getHeaderValue().toString() );
    }  
}

private void Presenta(java.awt.event.MouseEvent evt) {
          if(evt.getButton()>=2){
                MenuMostrar();
                
                this.show(evt.getComponent(),evt.getX(),evt.getY());
                //Obtengo el Punto donde el usuario dio click..
                Point Punto = evt.getPoint();
                //Obteniendo la fila y la columna que corresponde al lugar del clik 
                Row_Clk = ObjTbl.rowAtPoint(Punto);
                Col_Clk = ObjTbl.columnAtPoint(Punto);
                
                /*Verificando si existe alguna seleccion de usuario ya realizada
                 *si el usuario tiene seleccionada ya un intervalo de celdas
                 *entonces no hacemos nada ... caso contrario 
                 *Seleccionamos la celda donde dio click
                 */
                if( !ObjTbl.isCellSelected(Row_Clk , Col_Clk) ){
                    ObjTbl.setRowSelectionInterval(Row_Clk, Row_Clk);
                    ObjTbl.setColumnSelectionInterval(Col_Clk, Col_Clk );
                    }
          }

    }        
    
}


/**
* PortaPapelAdapter 
* Permite Copiar o Pegar al Portapapeles
* usando ctrl+c o ctrl+v y permite o usando sus metodos
* PortaPapelAdapter.copiar()
* PortaPapelAdapter.pegar()
*/
class PortaPapelAdapter implements ActionListener
   {
   private String rowstring,value;
   private Clipboard system;
   private StringSelection stsel;
   private JTable jTable1 ;
   /**
    * Es construido con un JTable de swing 1.4
    * para trabajar con la clase Clipboard
    * 
    */

public PortaPapelAdapter(JTable myJTable)
   {
      jTable1 = myJTable;
      KeyStroke copy = KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK,false);
      // Identifying the copy KeyStroke user can modify this
      // to copy on some other Key combination.
      KeyStroke paste = KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK,false);
      // Identifying the Paste KeyStroke user can modify this
      //to copy on some other Key combination.
jTable1.registerKeyboardAction(this,"Copy",copy,JComponent.WHEN_FOCUSED);

jTable1.registerKeyboardAction(this,"Paste",paste,JComponent.WHEN_FOCUSED);
      system = Toolkit.getDefaultToolkit().getSystemClipboard();
   }
   /**
    * Public Accessor methods for the Table on which this adapter acts.
    */
public JTable getJTable() {return jTable1;}
public void setJTable(JTable jTable1) {this.jTable1=jTable1;}
   /**
    * This method is activated on the Keystrokes we are listening to
    * in this implementation. Here it listens for Copy and Paste ActionCommands.
    * Selections comprising non-adjacent cells result in invalid selection and
    * then copy action cannot be performed.
    * Paste is done by aligning the upper left corner of the selection with the
    * 1st element in the current selection of the JTable.
    */
public void actionPerformed(ActionEvent e){
      if (e.getActionCommand().compareTo("Copy")==0)
         copiar();
      if (e.getActionCommand().compareTo("Paste")==0)
         pegar();
   }
public void copiar(){
    StringBuffer sbf=new StringBuffer();
         // Check to ensure we have selected only a contiguous block of
         // cells
         int numcols=jTable1.getSelectedColumnCount();
         int numrows=jTable1.getSelectedRowCount();
         int[] rowsselected=jTable1.getSelectedRows();
         int[] colsselected=jTable1.getSelectedColumns();
         
         if (!((numrows-1==rowsselected[rowsselected.length-1]-rowsselected[0] &&
                numrows==rowsselected.length) &&
(numcols-1==colsselected[colsselected.length-1]-colsselected[0] &&
                numcols==colsselected.length)))
         {
            JOptionPane.showMessageDialog(null, "No se puede copiar esa Seleccion",
                                          "No se puede copiar esa Seleccion",
                                          JOptionPane.ERROR_MESSAGE);
            return;
         }
         for (int i=0;i<numrows;i++)
         {
            for (int j=0;j<numcols;j++)
            {
               sbf.append(jTable1.getValueAt(rowsselected[i],colsselected[j]));
               if (j<numcols-1) sbf.append("\t");
            }
            sbf.append("\n");
         }
         stsel  = new StringSelection(sbf.toString());
         system = Toolkit.getDefaultToolkit().getSystemClipboard();
         system.setContents(stsel,stsel);
}
public void pegar(){
              System.out.println("Trying to Paste");
          int startRow=(jTable1.getSelectedRows())[0];
          int startCol=(jTable1.getSelectedColumns())[0];
          try
          {
             String trstring= (String)(system.getContents(this).getTransferData(DataFlavor.stringFlavor));
             System.out.println("String is:"+trstring);
             StringTokenizer st1=new StringTokenizer(trstring,"\n");
             for(int i=0;st1.hasMoreTokens();i++)
             {
                rowstring=st1.nextToken();
                StringTokenizer st2=new StringTokenizer(rowstring,"\t");
                for(int j=0;st2.hasMoreTokens();j++)
                {
                   value=(String)st2.nextToken();
                   if (startRow+i< jTable1.getRowCount()  &&
                       startCol+j< jTable1.getColumnCount())
                      jTable1.setValueAt(value,startRow+i,startCol+j);
                   System.out.println("Putting "+ value+"atrow="+startRow+i+"column="+startCol+j);
               }
            }
         }
         catch(Exception ex){ex.printStackTrace();}
}

}
