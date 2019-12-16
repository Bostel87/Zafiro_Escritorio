/*
 * tblColBut.java
 *
 * Created on 8 de octubre de 2004, 14:31
 */

package Librerias.ZafTableColBut;

/**
 *
 * @author  root
 */


public abstract class ZafTableColBut implements Click{
    String strTexto = ".."; // Texto en el Boton
    /** Creates a new instance of tblColBut */
    public ZafTableColBut(javax.swing.JTable tblWithBut, int intColumna ) {
  
        tblWithBut.getColumnModel().getColumn(intColumna).setCellRenderer(new CustomButtonRenderer()); 
        tblWithBut.getColumnModel().getColumn(intColumna).setCellEditor(new CustomButtonEditor());        
        
    }
    /*
     * Asigna o cambia el Texto que aperecera en el Boton
     */
    public void setText(String strText){
        strTexto=strText;
    }
    /*
     * Obtiene el Texto que aperece en el Boton
     */
    public String getText(){
        return strTexto;
    }
    
    class CustomButtonEditor extends javax.swing.DefaultCellEditor {
        
        final javax.swing.JButton mybutton;
        
        javax.swing.JFrame frame;
        
        CustomButtonEditor() {
            super(new javax.swing.JCheckBox());
            
            mybutton = new javax.swing.JButton();
            this.editorComponent = mybutton;
            this.clickCountToStart = 1;
            this.frame=frame;
            
            mybutton.setOpaque(true);
            
            mybutton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    System.out.println("Executando action del commando");
                    butCLick();
                    fireEditingStopped();
                }
            });
        }
        
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
        
        public Object getCellEditorValue() {
            return new String(mybutton.getText());
        }
        
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            
            if(value == null)
                ((javax.swing.JButton) editorComponent).setText("");
            else
                ((javax.swing.JButton) editorComponent).setText(strTexto);
            ((javax.swing.JButton) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            return editorComponent;
        }
        
    }    
    
    class CustomButtonRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {
        
        public CustomButtonRenderer() {
            setOpaque(true);
        }
        
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
         /*
          *Para poner colores en el botton
          */
            //      if (isSelected) {
            //         this.setForeground(table.getSelectionForeground());
            //         this.setBackground(table.getSelectionBackground());
            //      } else {
            //         this.setForeground(table.getForeground());
            //         this.setBackground(table.getBackground());
            //      }
            
            this.setText(strTexto);
            this.setFont(new java.awt.Font("SansSerif", 0, 11));
            return ((javax.swing.JButton)this);
        }
        
    }
    
}

//    interface Click{
//       public void butCLick();//Metodo Que Implementa el CLick en el Boton de la columna
//     }     
