/*
 * editorCantidad.java
 *
 * Created on 18 de julio de 2005, 15:53
 */

package Librerias.ZafUtil;

/**
 * Clase para manejar el editor de texto
 * @author Iditrix
 * @version 1.1
 */ 
  public abstract class EditorDouble extends javax.swing.DefaultCellEditor implements ActionToDo{
        
        final javax.swing.JTextField txtDouble;
        private int intColEdit;                  //Columna a editar   
        private int intColSigue;                 //Columna que tendra el foco despues de haber editado correctamente la intColEdit
        private javax.swing.JTable tblMaestro ;  // Tabla en donde se trabajara
        private int INT_NUMDEC;                  //Columna que tendra el objeto objRepInv
        private Librerias.ZafUtil.ZafUtil objUtil;
        private boolean isEditableColNext = true;  //Avisa al editor que la columna que se seleccionara 
                                                  //despues de editar la columna actual, se pondra en edicion
        
        private String strVal = "0";             //Contiene el valor actual de la fila
        
        /**
         *  Construye un nuevo objeto que maneja las cantidades
         *  y realiza todas las validaciones segun haya ocurra algun evento o no
         *      @param intColEditar - Columna a editar  (columna ke se edita)
         *      @param intColNext - Columna que tendra el foco despues de haber editado correctamente la intColEdit
         *      @param intNumDec - numero de decimales que tendra la columna
         *      @param tblMaster Tabla en la que se trabajara
         */
        public EditorDouble(int intColEditar, int intColNext, int intNumDec, javax.swing.JTable tblMaster ) {
            super(new javax.swing.JCheckBox());
            intColEdit   = intColEditar;
            intColSigue  = intColNext;
            tblMaestro   = tblMaster;
            INT_NUMDEC = intNumDec;
            txtDouble    = new javax.swing.JTextField();
            objUtil      = new Librerias.ZafUtil.ZafUtil();
            
            /**
             * Listener para que cuando el usuario de click en la celda, se seleccione 
             * todo el contenido de la misma.
             * 
               GANA FOCUS > ANTES: 0
               GANA FOCUS FILA: 0
               Hey stop edicion
               FOCUS LOST ANTES DEL IF
               FOCUS LOST ENTRA IF
               GANA FOCUS > ANTES: 0
               GANA FOCUS FILA: 1
              */
            txtDouble.addFocusListener(new java.awt.event.FocusAdapter() {
//                private int RowEditing = 0 ;
                 public void focusGained(java.awt.event.FocusEvent evt) {
//                    RowEditing = tblMaestro.getEditingRow();
                  }
                public void focusLost(java.awt.event.FocusEvent evt) {
//                    if(RowEditing!= -1 && intColEdit!= -1){
//                        tblMaestro.setValueAt(getValor(strValorDeFilaAct), RowEditing  ,intColEdit);            
//                    }
                  }
             });            
             
            txtDouble.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    String str = txtDouble.getText();
                    preActionToDo();
                    if(objUtil.isNumero(str)){
                        strVal = str;
                        fireEditingStopped();  
                        if(isEditableColNext)
                            tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                        tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                    }
               }
            });
            
            this.editorComponent = txtDouble;
        }
        
        /**
         * Dice a este editor si la columna que se seleccionara despues 
         * de que termine la edicion, se pondra editable o simplemente se 
         * seleccionara<br>
         * De manera predeterminada se pondra true
         */
        public void setEditColNext(boolean isEditableColNext){
            this.isEditableColNext = isEditableColNext;
        }
        
        protected void fireEditingStopped() {
            super.fireEditingStopped();
            posActionToDo();
        }
        public void cancelCellEditing(){
            super.cancelCellEditing();
            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColEdit, false, false);                        
            
        }
        public Object getCellEditorValue() {
            return getValor((txtDouble.getText().trim().equals(""))?"0":txtDouble.getText());
        }
        /**
         * Obtiene un Double a partir de un String
         * @param strVal Valor en String
         * @return Double con el valor redondeado a INT_NUMDEC o el valor en cero si es que lo que digito no es numerico
         */
        private Double getValor(String strValor){
            Double dbl = new Double(0);
            if(objUtil.isNumero(strValor))
                dbl = new Double(objUtil.redondeo(Double.parseDouble(strValor),INT_NUMDEC));
            return dbl;            
        }
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            strVal = (value == null)?"0":value.toString();
            ((javax.swing.JTextField) editorComponent).setText(strVal);
            ((javax.swing.JTextField) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            ((javax.swing.JTextField) editorComponent).selectAll();

            return editorComponent;
        }
        
    }    

