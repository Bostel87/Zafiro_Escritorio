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
  public abstract class EditorCantidad extends javax.swing.DefaultCellEditor implements ActionToDo {
        
        final javax.swing.JTextField txtDouble;
        private int intColEdit;  //Columna a editar   
        private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit
        private int intColRepInv;//Columna que tendra el objeto objRepInv
        private javax.swing.JTable tblMaestro ;// Tabla en donde se trabajara
        private int intDecimales;//Columna que tendra el objeto objRepInv
        private int intColBodega;//Columna que tendra el objeto objRepInv
        private int intColCodItem;//Columna que tendra el objeto objRepInv
        private Librerias.ZafUtil.ZafUtil objUtil;
        private Librerias.ZafParSis.ZafParSis objZafParSis;
        private Librerias.ZafRepInv.ZafRepInv objRepInv;
        private String strVal = "0";//Contiene el valor actual de la fila

        private ZafTipItm      objZafTipItm;      // Para saber si un producto es o no Servicio        
        private Librerias.ZafInventario.ZafInventario objZafInv;   //Clase que maneja los costos y existencias de los productos        
       /**
        * Aviza al usuario si se esta modificando o no, 
        * es nesecario para saber si se valida si hay stock necesario o no para la venta 
        * incluyendo la cantidad que ya habia sido digitada anteriormente.         
        */
        private boolean blnIsModifing=false; 
        /**
         *     Construye un nuevo objeto que maneja las cantidades
         *     y realiza todas las validaciones segun haya ocurra algun evento o no
         * @param intColEditar - Columna a editar  (columna ke se edita)
         * @param intColNext - Columna que tendra el foco despues de haber editado correctamente la intColEdit
         * @param intColObjRepInv - Columna que tendra el objeto objRepInv
         * @param intColBod   - Columna donde esta el codigo de la bodega
         * @param intColCodItm - Columna donde esta el codigo de item 
         * @param intDec - numero de decimales que tendra la columna
         * @param tblMaster Tabla en la que se trabajara
         * @param objZafParSis Objeto Parametros del Sistema
         * @param blnValidCanInTbl Le dice al editor que debe validar el stock actual del producto 
         *                         mas la cantidad que tenia ya digitada en la celda en el caso de 
         *                         las facturas es recomendable poner true, en cotizaciones no es necesario
         */
        public EditorCantidad(int intColEditar, int intColNext, int intColObjRepInv, int intColBod, int intColCodItm, javax.swing.JTable tblMaster, int intDec , Librerias.ZafParSis.ZafParSis objZafParSis, Librerias.ZafRepInv.ZafRepInv objZafRepInv) {
            super(new javax.swing.JCheckBox());
            intColEdit   = intColEditar;
            intColSigue  = intColNext;
            intColRepInv = intColObjRepInv;
            intColBodega = intColBod;
            intColCodItem= intColCodItm;
            tblMaestro   = tblMaster;
            intDecimales = intDec;
            txtDouble    = new javax.swing.JTextField();
            objUtil      = new Librerias.ZafUtil.ZafUtil();
            this.objZafParSis = objZafParSis;
            objRepInv    = objZafRepInv;
            objZafTipItm     = new ZafTipItm(objZafParSis);
            objZafInv = new Librerias.ZafInventario.ZafInventario(new javax.swing.JInternalFrame(), objZafParSis);
            
            /**
             * Listener para que cuando el usuario de click en la celda, se seleccione 
             * todo el contenido de la misma.
             */
            txtDouble.addFocusListener(new java.awt.event.FocusAdapter() {
//                private int ColEditing = 0 ;
//                 public void focusGained(java.awt.event.FocusEvent evt) {
//                    ColEditing = tblMaestro.getEditingRow();
//                  }
//                public void focusLost(java.awt.event.FocusEvent evt) {
//                    if(ColEditing!=-1  && intColEdit!=-1)
//                        tblMaestro.setValueAt(getValor(strVal), ColEditing  ,intColEdit);            
//                  }
             });            
             
            txtDouble.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    //dllgPrueba objPrueba = new dllgPrueba(new javax.swing.JFrame(), true);
                    String str = txtDouble.getText();
                    int intBodActual = Integer.parseInt( (tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColBodega)==null || tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColBodega).toString().equals(""))?"0":tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColBodega).toString());
                    int intCodItmAct = Integer.parseInt(
                         (tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColCodItem)==null || tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColCodItem).toString().equals(""))?"0":tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColCodItem).toString()
                      );
                    
                    /*
                     * Hago cualquier validación si el item no es servicio
                     */
                    if(!objZafTipItm.isServicio( intCodItmAct)){      
                        double dblStockBod = objZafInv.getExistencia(intCodItmAct, intBodActual), 
                               dblCant     = Double.parseDouble(str);
                        preActionToDo();
                        
                        if(blnIsModifing){
                            dblStockBod = dblStockBod+Double.parseDouble(strVal);
                        }
                        
                        if(dblStockBod < dblCant ){            
                            javax.swing.JOptionPane oppMsg = new javax.swing.JOptionPane();
                            String strTit, strMsg;
                            strTit="Mensaje del sistema Zafiro";
                            strMsg="El producto  tiene en esa bodega  : " + dblStockBod + "\nDesea hacer una REPOSICION?" ;
                            java.util.Vector vecRep = (java.util.Vector)tblMaestro.getValueAt(tblMaestro.getSelectedRow(), intColRepInv);                            
                             if(oppMsg.showConfirmDialog(new javax.swing.JFrame(),strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION){
                                if(intCodItmAct>0 && intBodActual>0 && dblCant>0){
                                     vecRep = objRepInv.CrearReposicion(
                                                       intBodActual, 
                                                       intCodItmAct, 
                                                       dblCant, vecRep);
                                   }
                              }//Fin if del Dialogo
                            //System.out.println(vecRep);
                            tblMaestro.setValueAt(vecRep, tblMaestro.getSelectedRow(),intColRepInv);                            
                            if(vecRep != null &&  objUtil.isNumero(str)){
                                tblMaestro.setValueAt(new Double(str), tblMaestro.getSelectedRow(),intColEdit);
                                fireEditingStopped();  
                                posActionToDo();
                                tblMaestro.requestFocus();
                                tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                                tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                            }
                        }else{
                            strVal = str;                        
                            fireEditingStopped();  
                            posActionToDo();
                            tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);                            
                        }
                        
                    }else{
                            strVal = str;                        
                            fireEditingStopped();  
                            posActionToDo();
                            tblMaestro.editCellAt(tblMaestro.getSelectedRow(),intColSigue);
                            tblMaestro.changeSelection(tblMaestro.getSelectedRow(), intColSigue, false, false);
                    }
                        
               }
            });
            
            this.editorComponent = txtDouble;
        }
        
        protected void fireEditingStopped() {
            super.fireEditingStopped();
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
         * @return Double con el valor redondeado a intDecimales o el valor en cero si es que lo que digito no es numerico
         */
        private Double getValor(String strValor){
            Double dbl = new Double(0);
            if(objUtil.isNumero(strValor))
                dbl = new Double(objUtil.redondeo(Double.parseDouble(strValor),intDecimales));
            return dbl;            
        }
        
       /**
        * Aviza al usuario si se esta modificando o no, 
        * es nesecario para saber si se valida si hay stock necesario o no para la venta 
        * incluyendo la cantidad que ya habia sido digitada anteriormente.
        * Su uso es importante para las facturas, pero en las cotizaciones no es necesario<br>
        * Ejemplo: <br>
        *       Si se esta modificando una factura en la cual un producto tiene en stock actual = 10
        *       y en la factura se habian vendido 5, entonces si se modifica esa fila de la tabla
        *       el stock con el que se debe validar es con 15 y no con 10 pues el stock que se habia vendido 
        *       debe regresar al inventario, asi el usuario podria vender mas de 10 unidades incluso 15.           
        */
        public void isModifing(boolean blnIsModifing){
            this.blnIsModifing = blnIsModifing;
        }
        
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column) {
            strVal = (value == null)?"0":value.toString();
            ((javax.swing.JTextField) editorComponent).setText(strVal);
            ((javax.swing.JTextField) editorComponent).setFont( new java.awt.Font("SansSerif", 0, 11));
            ((javax.swing.JTextField) editorComponent).selectAll();

            return editorComponent;
        }
    }    
