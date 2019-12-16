/*
 * ZafTblCelEdiButDlg.java
 *
 * Created on 18 de marzo de 2005, 22:14 PM
 * v0.4
 */

package Librerias.ZafTblUti.ZafTblCelEdiButDlg;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import javax.swing.event.EventListenerList;
import javax.swing.JDialog;

/**
 * Esta clase establece el editor a utilizar en la celda basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JButton. Al dar click en el botón se
 * presenta el JDialog que es recibido como parámetro en el constructor. La diferencia de ésta clase con 
 * la clase "ZafTblCelEdiBut" es que ésta clase presenta un JDialog cualquiera mientras que la clase
 * "ZafTblCelEdiBut" siempre muestra la clase "ZafConsulta".
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelEdiButDlg" es la instancia de "ZafTblCelEdiButDlg".
 * <PRE>
 *           objTblCelEdiButDlg.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
 *               public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (tblDat.getSelectedRow()==2)
 *                       objTblCelEdiBut.setCancelarEdicion(true);
 *                   else
 *                       objTblCelEdiBut.setCancelarEdicion(false);
 *               }
 *               public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   //Escriba su códico aquí...
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * @author  Eddye Lino
 */
public class ZafTblCelEdiButDlg extends javax.swing.JButton implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private JDialog dlgGen;
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    protected EventListenerList objEveLisLis=new EventListenerList();
    
    /** 
     * Crea una nueva instancia de la clase ZafTblCelEdiButDlg.
     * @param dialogo El JDialog que se mostrará al dar click en el botón.
     */
    public ZafTblCelEdiButDlg(javax.swing.JDialog dialogo)
    {
        super("...");
        dlgGen=dialogo;
        blnCanEdi=false;
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thisActionPerformed(evt);
            }
        });
        //Agregar el "FocusListener" que avisará al JTable que se ha cancelado la edición de la celda.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                setValCelEdi(false);
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
        return this.getText();
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
                //"getSelectedColumn()" en la clase donde se llamaba al ZafTblCelEdiButVco no devolvía la columna en la que se daba
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
        return true;
    }
    
    /**
     * Nombre de la función: Mostrar ventana de consulta.
     * Muestra la ventana de consulta para que el usuario seleccione la opción que desee.
     */
    private void mostrarVenCon()
    {
        //Cargar el JDialog.
        dlgGen.setVisible(true);
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
    private void setValCelEdi(boolean hayCambios)
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
                objCelEdiLis.editingCanceled(evt);
        }
    }
    
    /**
     * Esta función valida los datos que se ingresan en la celda. Si los datos son válidos
     * se cargan los datos adicionales. Caso contrario, se muestra la ventana de consulta.
     */
    private void thisActionPerformed(java.awt.event.ActionEvent evt)
    {
        mostrarVenCon();
        setValCelEdi(true);
        //Generar evento "afterEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);
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