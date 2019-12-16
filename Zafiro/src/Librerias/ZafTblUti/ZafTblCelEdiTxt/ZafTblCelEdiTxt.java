/*
 * ZafTblCelEdiTxt.java
 *
 * Created on 25 de julio de 2005, 03:02 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblCelEdiTxt;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase establece el editor a utilizar en la celda basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JTextField.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelEdiTxt" es la instancia de "ZafTblCelEdiTxt".
 * <PRE>
 *           objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
 *               public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (tblDat.getSelectedRow()==2)
 *                       objTblCelEdiTxt.setCancelarEdicion(true);
 *                   else
 *                       objTblCelEdiTxt.setCancelarEdicion(false);
 *               }
 *               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   System.out.println("El usuario digitó " + objTblCelEdiTxt.getText());
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso de los métodos "beforeEdit" y "afterEdit". Los métodos "beforeConsultar" y "afterConsultar"
 * no son utilizados por ésta clase.
 * @author  Eddye Lino
 */
public class ZafTblCelEdiTxt extends javax.swing.JTextField implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    private static final int INT_BEF_CON=2;     /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;     /**Después de consultar: Indica "After consultar".*/
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private int intNumDec;
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    private boolean blnActLisEje;               //Determina si se ejecutó el "actionPerformed".
    protected EventListenerList objEveLisLis=new EventListenerList();

    /** Crea una nueva instancia de la clase ZafTblCelEdiTxt. */
    public ZafTblCelEdiTxt()
    {
        this(null);
    }
    
    /** Crea una nueva instancia de la clase ZafTblCelEdiTxt. */
    public ZafTblCelEdiTxt(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        intNumDec=6;
        blnCanEdi=false;
        this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisActionPerformed(evt);
                blnActLisEje=true;
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
                //Cuando se edita la celda con doble click la clase invoca el "actionPerformed" y luego "focusLost".
                //Para evitar que se repita la llamada a "thisActionPerformed" se agregó la variable "blnActLisEje".
                if (!blnActLisEje)
                    thisActionPerformed(null);
                blnActLisEje=false;
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
        return this.getText();
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        this.setText((value==null)?"":value.toString());
        strConCel=this.getText();
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
        //Permitir de manera predeterminada la edición de la celda.
        blnCanEdi=false;
        //Generar evento "beforeEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_EDI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (blnCanEdi)
            return false;
        else
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
                    objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
                    intCol=tblDat.getSelectedColumn();
                    switch (objTblMod.getColumnDataType(intCol))
                    {
                        case 0: //Columna que almacena cadenas.
                            objCelEdiLis.editingStopped(evt);
                            break;
                        case 1: //Columna que almacena números enteros. 
                            if (this.getText().equals(""))
                                objCelEdiLis.editingStopped(evt);
                            else
                            {
                                try
                                {
                                    //Convertir el valor a int.
                                    this.setText("" + Integer.parseInt(this.getText()));
                                    //Validar que el valor se encuentre entre el mínimo y el máximo.
                                    if (objTblMod.getColumnDataTypeMin(intCol)!=null)
                                    {
                                        if (objTblMod.getColumnDataTypeMax(intCol)!=null)
                                        {
                                            //Validar que el valor sea >=Min y <=Max.
                                            if (Integer.parseInt(this.getText())>=objTblMod.getColumnDataTypeMin(intCol).intValue() && Integer.parseInt(this.getText())<=objTblMod.getColumnDataTypeMax(intCol).intValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                        else
                                        {
                                            //Validar que el valor sea >=Min.
                                            if (Integer.parseInt(this.getText())>=objTblMod.getColumnDataTypeMin(intCol).intValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (objTblMod.getColumnDataTypeMax(intCol)!=null)
                                        {
                                            //Validar que el valor sea <=Max.
                                            if (Integer.parseInt(this.getText())<=objTblMod.getColumnDataTypeMax(intCol).intValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                        else
                                        {
                                            //No se valida rango.
                                            objCelEdiLis.editingStopped(evt);
                                        }
                                    }
                                }
                                catch (NumberFormatException e)
                                {
                                    objCelEdiLis.editingCanceled(evt);
                                    this.setText(strConCel);
                                }
                            }
                            break;
                        case 2: //Columna que almacena números reales.
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
                                    //Validar que el valor se encuentre entre el mínimo y el máximo.
                                    if (objTblMod.getColumnDataTypeMin(intCol)!=null)
                                    {
                                        if (objTblMod.getColumnDataTypeMax(intCol)!=null)
                                        {
                                            //Validar que el valor sea >=Min y <=Max.
                                            if (Double.parseDouble(this.getText())>=objTblMod.getColumnDataTypeMin(intCol).doubleValue() && Double.parseDouble(this.getText())<=objTblMod.getColumnDataTypeMax(intCol).doubleValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                        else
                                        {
                                            //Validar que el valor sea >=Min.
                                            if (Double.parseDouble(this.getText())>=objTblMod.getColumnDataTypeMin(intCol).doubleValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                    }
                                    else
                                    {
                                        if (objTblMod.getColumnDataTypeMax(intCol)!=null)
                                        {
                                            //Validar que el valor sea <=Max.
                                            if (Double.parseDouble(this.getText())<=objTblMod.getColumnDataTypeMax(intCol).doubleValue())
                                                objCelEdiLis.editingStopped(evt);
                                            else
                                            {
                                                objCelEdiLis.editingCanceled(evt);
                                                this.setText(strConCel);
                                            }
                                        }
                                        else
                                        {
                                            //No se valida rango.
                                            objCelEdiLis.editingStopped(evt);
                                        }
                                    }
                                }
                                catch (NumberFormatException e)
                                {
                                    objCelEdiLis.editingCanceled(evt);
                                    this.setText(strConCel);
                                }
                            }
                            break;
                    }
                }
            }
            else
            {
                objCelEdiLis.editingCanceled(evt);
                this.setText(strConCel);
            }
        }
    }
    
    /**
     * Esta función se ejecuta cuando se hace el "ActionPerformed" del JTextField.
     */
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (!this.getText().equals(strConCel))
        {
            setValCelEdi(true);
            //Generar evento "afterEdit()".
            fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);
        }
        else
        {
            setValCelEdi(false);
        }
    }
    
    /**
     * Esta función hace que se seleccione el texto del JTextField cuando el JTextField
     * obtiene el foco.
     */
    private void thisFocusGained(java.awt.event.FocusEvent evt)
    {
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
    
    /**
     * Esta función adiciona el listener que controlará los eventos de edición.
     * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
     */
    public void addTableEditorListener(ZafTableListener listener)
    {
        objEveLisLis.add(ZafTableListener.class, listener);
    }

    /**
     * Esta función adiciona el listener que controlará los eventos de edición.
     * @param listener El objeto que implementa los métodos de la interface "ZafTableListener".
     */
    public void removeTableEditorListener(ZafTableListener listener)
    {
        objEveLisLis.remove(ZafTableListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTableEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_EDI</TD><TD>Invoca al métod "beforeEdit" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_EDI</TD><TD>Invoca al métod "afterEdit" de la interface.</TD></TR>
     *     <TR><TD>INT_BEF_CON</TD><TD>Invoca al métod "beforeConsultar" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_CON</TD><TD>Invoca al métod "afterConsultar" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTableEditorListener(ZafTableEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTableListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_EDI:
                        ((ZafTableListener)obj[i+1]).beforeEdit(evt);
                        break;
                    case INT_AFT_EDI:
                        ((ZafTableListener)obj[i+1]).afterEdit(evt);
                        break;
                    case INT_BEF_CON:
                        ((ZafTableListener)obj[i+1]).beforeConsultar(evt);
                        break;
                    case INT_AFT_CON:
                        ((ZafTableListener)obj[i+1]).afterConsultar(evt);
                        break;
                }
            }
        }
    }
 
    /**
     * Esta función establece si se debe cancelar la edición de la celda.
     * @param edicion Puede tomar los siguientes valores:
     * <UL>
     * <LI>true: Cancela la edición de la celda.
     * <LI>false: Permite la edición de la celda.
     * </UL>
     */
    public void setCancelarEdicion(boolean edicion)
    {
        blnCanEdi=edicion;
    }
    
}