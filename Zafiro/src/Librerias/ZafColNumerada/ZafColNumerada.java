/*
 * ZafColNumerada.java
 *
 * Created on 8 de octubre de 2004, 16:53
 */

package Librerias.ZafColNumerada;

/**
 *
 * @author  root
 */
public class ZafColNumerada {
    
    /** Creates a new instance of ZafColNumerada */
    public ZafColNumerada(javax.swing.JTable tblWithButNum, int intColumna) {
        tblWithButNum.getColumnModel().getColumn(intColumna).setCellRenderer(new CustomButtonRenderer()); 
        tblWithButNum.getColumnModel().getColumn(intColumna).setCellEditor(new CustomButtonEditor());        
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
            
            this.setText(""+(row+1));
            this.setFont(new java.awt.Font("SansSerif", 0, 11));
            this.setBorder(new javax.swing.border.EtchedBorder());
            return ((javax.swing.JButton)this);
        }
        
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
            
            ((javax.swing.JButton) editorComponent).setText(""+(row+1));
            ((javax.swing.JButton) editorComponent).setFont(new java.awt.Font("SansSerif", 0, 11));
            ((javax.swing.JButton) editorComponent).setBorder(new javax.swing.border.EtchedBorder());

            return editorComponent;
        }
        
    }
    
}
