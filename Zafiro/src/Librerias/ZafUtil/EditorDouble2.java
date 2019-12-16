/*
 * EditorCantidad3.java
 *
 * Created on 6 de octubre de 2005, 10:32
 */

package Librerias.ZafUtil; 

/**
 *
 * @author  IdiTrix
 */
public abstract class EditorDouble2 extends javax.swing.JTextField implements javax.swing.table.TableCellEditor, Librerias.ZafUtil.ActionToDo
{
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private javax.swing.table.DefaultTableModel objTblMod;
    private int intNumDec;
    
    private boolean blnIsInActionPerfomed=false;    
    
    private int intColEdit;  //Columna a editar   
    private int intColSigue; //Columna que tendra el foco despues de haber editado correctamente la intColEdit    

   
    /** Crea una nueva instancia de la clase ZafTblCelEdiTxt. */
    public EditorDouble2(int intColEditar, int intColNext, javax.swing.JTable tabla)
    {
        intColEdit   = intColEditar;
        intColSigue  = intColNext;        
        tblDat=tabla;
        intNumDec=6;
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisActionPerformed(evt);
            }
        });
        //Seleccionar el texto cuando se obtenga el foco.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                thisFocusGained(evt);
            }
        });
        //Agregar el "FocusListener" que avisará al JTable que se ha cancelado la edición de la celda.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                if(!blnIsInActionPerfomed)
                    thisActionPerformed(null);
            }
        });
    }
    
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
    public void addCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        this.selectAll();
        objLinLis.add(l);
    }
    
    public void cancelCellEditing()
    {
        
    }
    
    /**
     * Esta función devuelve el nuevo valor que debe aparecer en la celda que se estaba editando.
     * Por ejemplo, si el editor era un "JComboBox" devolverá la opción que se seleccionó en
     * dicho combo.
     */
    public Object getCellEditorValue()
    {
        return getValor(this.getText());
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        strConCel =  (value==null)?null:value.toString() ;
        this.setText((value==null)?"":value.toString());
        return this;
    }
    
    /**
     * Esta función devuelve "true" si se desea que la celda sea editable y "false" en caso contrario.
     * Si se desea que apenas se de click en la celda aparezca el editor se debe quitar la validación
     * y sólo dejar la línea "return true;".
     */
    public boolean isCellEditable(java.util.EventObject anEvent)
    {
        //Validar que sólo se muestre el editor cuando se da doble click.
        if (anEvent instanceof java.awt.event.MouseEvent) 
        {
            if (((java.awt.event.MouseEvent)anEvent).getClickCount()!=2)
                return false;
        }            
        return true;
    }
    
    /**
     * Esta función remueve el listener que notifica cuando el editor se detiene
     * o cancele la edición de la celda.
     */
    public void removeCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        objLinLis.remove(l);
    }
    
    /**
     * Esta función devuelve "true" si se desea que la fila de la celda que se está editando sea
     * seleccionada y "false" en caso contrario.
     */
    public boolean shouldSelectCell(java.util.EventObject anEvent)
    {
        return true;
    }
    
    /**
     * Esta función debe devolver "true" cuando los datos que está introduciendo el usuario son correctos y false
     * en el caso contrario. Por ejemplo, puede ser que se esté utilizando un editor para ingreso de fechas y el
     * usuario ha ingresado 31/Feb/2005 en cuyo caso deveríamos devolver "false" para obligar al usuario a que
     * ingrese una fecha válida.
     */
    public boolean stopCellEditing()
    {
        super.fireActionPerformed();
        return true;
    }
    
    /**
     * Nombre de la función: Establecer valor de la celda editada.
     * Establece el valor de la celda luego de utilizar el editor siempre y cuando el argumento que
     * reciba sea verdadero en caso contrario deja el valor que tenía la celda antes de ser editada.
     * @param hayCambios El estado de la celda que se está editando.
     * <BR>True: Si el contenido de la celda ha cambiado se avisa a los suscriptores de que se ha 
     * terminado la edición y que hay que establecer el nuevo valor de la celda.
     * <BR>False: Se avisa a los suscriptores que se ha cancelado la edición y que se debe dejar
     * el valor que tenía la celda tal y como estaba antes de la edición de la misma.
     */
    protected void setValCelEdi(boolean hayCambios)
    {
        javax.swing.event.ChangeEvent evt=new javax.swing.event.ChangeEvent(this);
        javax.swing.event.CellEditorListener objCelEdiLis;
        int intCol, i;
        for (i=0;i<objLinLis.size();i++)
        {
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLinLis.get(i);
            if (hayCambios)
            {
                //Si no se recibió el JTable no se valida nada.
                if (tblDat==null)
                {
                    objCelEdiLis.editingStopped(evt);
                }
                else
                {
                    objTblMod=(javax.swing.table.DefaultTableModel)tblDat.getModel();
                    intCol=tblDat.getSelectedColumn();

                            if (this.getText().equals(""))
                                objCelEdiLis.editingStopped(evt);
                            else
                            {
                                try
                                {
                                    //Redondear a n decimales.
                                    java.math.BigDecimal objBigDec=new java.math.BigDecimal(this.getText());
                                    objBigDec=objBigDec.setScale(intNumDec, java.math.BigDecimal.ROUND_HALF_UP);
                                    this.setText("" + objBigDec.doubleValue());
                                    objBigDec=null;
                                   
//                                        //Validar que el valor sea <=Max.
//                                        if (Double.parseDouble(this.getText())<=objTblMod.getColumnDataTypeMax(intCol).doubleValue())
//                                            objCelEdiLis.editingStopped(evt);
//                                        else
//                                            objCelEdiLis.editingCanceled(evt);
//                                        //No se valida rango.
                                    preActionToDo();
                                    objCelEdiLis.editingStopped(evt);
                                    posActionToDo();   

                                }
                                catch (NumberFormatException e)
                                {
                                    objCelEdiLis.editingCanceled(evt);
                                }
                            }
                    
                }
            }
            else
                objCelEdiLis.editingCanceled(evt);
        }
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "ActionPerformed" del JTextField.
     */
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {
        blnIsInActionPerfomed=true;
        //Validar el contenido de la celda sólo si ha cambiado.
        Double objDblAct = getValor(this.getText());
        Double objDblConCel = getValor(strConCel);
        if(objDblConCel== null  ){
            if(objDblAct !=null)
                setValCelEdi(true);
        }else{
            if(this.getText()==null || this.getText().equals("")){
                tblDat.setValueAt(null,tblDat.getSelectedRow(),intColEdit);
            }else{
                if(objDblAct !=null)
                    if (objDblConCel.doubleValue() != objDblAct.doubleValue() )
                        setValCelEdi(true);
            }
        }
        
        setValCelEdi(false);
        blnIsInActionPerfomed=false;        
    }
    
    /**
     * Esta función hace que se seleccione el texto del JTextField cuando el JTextField
     * obtiene el foco.
     */
    private void thisFocusGained(java.awt.event.FocusEvent evt)
    {
        strConCel=this.getText();
        this.selectAll();
    }
    
    /**
     * Esta función obtiene el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @return El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public int getNumeroDecimales()
    {
        return intNumDec;
    }
    
    /**
     * Esta función establece el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @param decimales El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public void setNumeroDecimales(int decimales)
    {
        intNumDec=decimales;
    }

    private Double getValor(String strValToDbl){
        try{
            java.math.BigDecimal objBigDec=new java.math.BigDecimal(strValToDbl);
            objBigDec=objBigDec.setScale(intNumDec, java.math.BigDecimal.ROUND_HALF_UP);
            return new Double(objBigDec.doubleValue());
        }
        catch (NumberFormatException e)
        {
            return null;                        
          }
        catch (Exception e)
        {
            return null;                        
          }
    }    
    
}
