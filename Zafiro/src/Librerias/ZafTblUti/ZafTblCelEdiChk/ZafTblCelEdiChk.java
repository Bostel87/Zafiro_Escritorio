/*
 * ZafTblCelEdiChk.java
 *
 * Created on 02 de agosto de 2005, 13:35 PM
 * v0.4
 */

package Librerias.ZafTblUti.ZafTblCelEdiChk;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase establece el editor a utilizar en la celda basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JCheckBox.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelEdiChk" es la instancia de "ZafTblCelEdiChk".
 * <PRE>
 *           objTblCelEdiChk=new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
 *           tcmAux.getColumn(INT_TBL_DAT_REF).setCellEditor(objTblCelEdiChk);
 *           objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
 *               public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (tblDat.getSelectedRow()==2)
 *                       objTblCelEdiChk.setCancelarEdicion(true);
 *                   else
 *                       objTblCelEdiChk.setCancelarEdicion(false);
 *               }
 *               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (objTblCelEdiChk.isChecked())
 *                       System.out.println("La casilla fue marcada.");
 *                   else
 *                       System.out.println("La casilla fue desmarcada.");
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso de los métodos "beforeEdit" y "afterEdit". Los métodos "beforeConsultar" y "afterConsultar"
 * no son utilizados por ésta clase.
 * @author  Eddye Lino
 */
public class ZafTblCelEdiChk extends javax.swing.JCheckBox implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private boolean blnConCel;                  //Contenido de la celda antes de ser editada.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    protected EventListenerList objEveLisLis=new EventListenerList();

    /** Crea una nueva instancia de la clase ZafTblCelEdiChk. */
    public ZafTblCelEdiChk()
    {
        this(null);
    }
    
    /** Crea una nueva instancia de la clase ZafTblCelEdiChk. */
    public ZafTblCelEdiChk(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        blnCanEdi=false;
        //Configurar la casilla que editorá la celda.
        this.setOpaque(false);
        this.setBorderPainted(false);
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setSelected(false);
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisActionPerformed(evt);
                //Asignar el foco porque cuando se presenta un JOptionPane se pierde el foco.
                if (tblDat!=null)
                    tblDat.requestFocus();
            }
        });
    }
    
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
    public void addCellEditorListener(javax.swing.event.CellEditorListener l)
    {
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
        return Boolean.valueOf(this.isSelected());
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        if (value==null)
            this.setSelected(false);
        else if (value instanceof Boolean)
            this.setSelected(((Boolean)value).booleanValue());
        else
            this.setSelected(false);
        blnConCel=this.isSelected();
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
            if (((java.awt.event.MouseEvent)anEvent).getClickCount()!=1)
                return false;
            else
            {
                //El código "((javax.swing.JTable)anEvent.getSource())" lo utilizo para obtener la tabla sobre la que se dió click.
                //Con éste código selecciono la celda en la cual se dió click. Ocurría que cuando se utilizaba el método
                //"getSelectedColumn()" en la clase donde se llamaba al ZafTblCelEdiChk no devolvía la columna en la que se daba
                //click sino la última columna donde se había dado click. Por eso fue necesario poner éste código.
                int intFilSel=((javax.swing.JTable)anEvent.getSource()).rowAtPoint(((java.awt.event.MouseEvent)anEvent).getPoint());
                int intColSel=((javax.swing.JTable)anEvent.getSource()).columnAtPoint(((java.awt.event.MouseEvent)anEvent).getPoint());
                ((javax.swing.JTable)anEvent.getSource()).setRowSelectionInterval(intFilSel, intFilSel);
                ((javax.swing.JTable)anEvent.getSource()).setColumnSelectionInterval(intColSel, intColSel);
            }
        }
        //Permitir de manera predeterminada la edición de la celda.
        blnCanEdi=false;
        //Generar evento "beforeEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_EDI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (blnCanEdi)
        {
            this.setSelected(blnConCel);
            return false;
        }
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
        int i;
        for (i=0;i<objLinLis.size();i++)
        {
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLinLis.get(i);
            if (hayCambios)
                objCelEdiLis.editingStopped(evt);
            else
            {
                objCelEdiLis.editingCanceled(evt);
                this.setSelected(blnConCel);
            }
        }
    }
    
    /**
     * Esta función valida los datos que se ingresan en la celda. Si los datos son válidos
     * se cargan los datos adicionales. Caso contrario, se muestra la ventana de consulta.
     */
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {
        //Validar el contenido de la celda sólo si ha cambiado.
        if (this.isSelected()!=blnConCel)
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
    
    /**
     * Esta función determina si la celda fue marcada o desmarcada.
     * @return true: Si la celda fue marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isChecked()
    {
        return this.isSelected();
    }

    /**
     * Esta función establece si se debe marcar/desmarcar la celda especificada.
     * @param checked Valor booleano que determina si se debe marcar/desmarcar la celda.
     * @param row La fila de la celda que se desea marcar/desmarcar.
     * @param col La columna de la celda que se desea marcar/desmarcar.
     * Nota.- Este método sólo funciona si se ha utilizado el constructur
     * "ZafTblCelEdiChk(javax.swing.JTable tabla)".
     */
    public void setChecked(boolean checked, int row, int col)
    {
        if (tblDat!=null)
        {
            objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
            objTblMod.setValueAt(Boolean.valueOf(true), row, col);
            objTblMod=null;
        }
    }
    
}