/*
 * ZafTblCelEdiCmbBox.java
 *
 * Created on 02 de agosto de 2005, 13:35 PM
 * modificado el 07/10/2010 versión v0.2
 */

package Librerias.ZafTblUti.ZafTblCelEdiCbo;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import java.awt.event.FocusEvent;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;


/**
 * @author  Eddye Lino
 */
public class ZafTblCelEdiCbo extends javax.swing.JComboBox implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/

    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private int intConCel;
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    protected EventListenerList objEveLisLis=new EventListenerList();

    /** Crea una nueva instancia de la clase ZafTblCelEdiCmbBox. */
    public ZafTblCelEdiCbo()
    {        
        this(null);
    }
    
    /** Crea una nueva instancia de la clase ZafTblCelEdiCmbBox. */
    public ZafTblCelEdiCbo(javax.swing.JTable tabla)
    {
        //super (new String [] {"joven", "hombre", "viejo"});
        tblDat=tabla;
        blnCanEdi=false;
        //Configurar la casilla que editorá la celda.
        this.setOpaque(false);
                
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisActionPerformed(evt);
                //Asignar el foco porque cuando se presenta un JOptionPane se pierde el foco.
                if (tblDat!=null)
                    tblDat.requestFocus();
            }
             public void focusGained (FocusEvent evt) {;}
             public void focusLost (FocusEvent evt){
                 thisFocusLost(evt);
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
    public Object getCellEditorValue(){
        if(this.getSelectedIndex()!= (-1) )
            return this.getSelectedItem();
         return null;
    }
    
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. Por ejemplo, si el 
     * editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
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
            this.setSelectedIndex(intConCel);
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
//         if (anEvent instanceof MouseEvent) {
//             MouseEvent e = (MouseEvent)anEvent;
//             return e.getID() != MouseEvent.MOUSE_CLICKED;
//         }

        
        return true;
    }
    
    /**
     * Esta función debe devolver "true" cuando los datos que está introduciendo el usuario son correctos y false
     * en el caso contrario. Por ejemplo, puede ser que se esté utilizando un editor para ingreso de fechas y el
     * usuario ha ingresado 31/Feb/2005 en cuyo caso deberíamos devolver "false" para obligar al usuario a que
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
                this.setSelectedItem(intConCel);
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
        setValCelEdi(true);
        //Generar evento "afterEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);

    }

    /**
     * Esta función valida los datos que se ingresan en la celda. Si los datos son válidos
     * se cargan los datos adicionales. Caso contrario, se muestra la ventana de consulta.
     */
    private void thisFocusLost(java.awt.event.FocusEvent evt)
    {
        //Validar el contenido de la celda sólo si ha cambiado.
        setValCelEdi(true);
        //Generar evento "afterEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);

    }
  
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
      * Si cambiado es true, se avisa a los suscriptores de que se ha terminado
      * la edición. Si es false, se avisa de que se ha cancelado la edición.
      */
     protected void isEditable(boolean cambiado){
         ChangeEvent evento = new ChangeEvent (this);
         for (int i=0; i<objLinLis.size(); i++){
             CellEditorListener aux = (CellEditorListener)objLinLis.get(i);
             if (cambiado)
                aux.editingStopped(evento);
             else
                aux.editingCanceled(evento);
         }
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