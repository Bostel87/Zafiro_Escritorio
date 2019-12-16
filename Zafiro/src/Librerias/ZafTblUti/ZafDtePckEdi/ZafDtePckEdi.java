/*
 * ZafDtePckEdi.java
 * Created on 27 de julio de 2005, 10:00
 * Modificado: v0.3 - 16/Ago/2019 por Rosa Zambrano
 * <BR> Se agrega evento afterEdit y formato de fecha y-m-d
 */
package Librerias.ZafTblUti.ZafDtePckEdi;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafUtil.ZafUtil;
import javax.swing.event.EventListenerList;

/**
 *
 * Clase "DatePicker en JTable"
 */
public class ZafDtePckEdi extends Librerias.ZafDate.ZafDatePicker implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    private static final int INT_BEF_CON=2;     /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;     /**Después de consultar: Indica "After consultar".*/
    
    //Variables
    private java.util.LinkedList objLnkLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    protected EventListenerList objEveLisLis=new EventListenerList();
    private ZafUtil objUti;
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    private String strConCel;                   //Contenido de la celda antes de ser editada.
    
    
    /**
     * Construye un nuevo DatePicker
     * <BR> Si la celda no tiene ninguna fecha seleccionada, el editor establece como valor predeterminado la fecha actual del sistema.
     * <BR> Caso contrario, el editor seleccionarÃ¡ todo el valor para poder borrarlo al digitar.
     * @param strFormat Recibe el formato con el que se desea presentar la fecha seleccionada.
     * <BR>Por ejemplo:
     *          <CENTER>
     *          <TABLE BORDER=1>
     *              <TR><TD><I>Formato Fecha</I></TD><TD><I>Descripcion</I></TD></TR>
     *              <TR><TD>yyyy-MM-dd</TD><TD>Year-Month-Day</TD></TR>
     *              <TR><TD>yyyy/MM/dd</TD><TD>Year/Month/Day</TD></TR>
     *              <TR><TD>yyyy.MM.dd</TD><TD>Year.Month.Day</TD></TR>
     *              <TR><TD>dd-MMM-yyyy</TD><TD>Day-Month-Year</TD></TR>
     *              <TR><TD>dd/MMM/yyyy</TD><TD>Day/Month/Year</TD></TR>
     *              <TR><TD>dd.MMM.yyyy</TD><TD>Day.Month.Year</TD></TR>
     *          </TABLE>
     *          </CENTER>
     */
    public ZafDtePckEdi(String strFormat)
    {   
        super(new javax.swing.JFrame(), strFormat);
        objUti = new ZafUtil();        
        
        //this.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0)));
        //Agregar el "ActionListener" que avisará al JTable que el contenido de la celda ha cambiado.
        this.addActionListener(new java.awt.event.ActionListener() { 
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setValCelEdi(isFecha());
            }
        });
        
        //Seleccionar el texto cuando se obtenga el foco.
        this.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                thisFocusGained(evt);
            }
        });
    }
    
    /**
     * Esta función hace que se seleccione el texto del JTextField cuando el JTextField
     * obtiene el foco.
     */
    private void thisFocusGained(java.awt.event.FocusEvent evt)
    {
        System.out.println("thisFocusGained");
        this.selectAll();
    }    
     
    /**
     * Esta función adiciona el listener que notificará cuando el editor se detenga
     * o cancele la edición de la celda.
     */
    public void addCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        System.out.println("addCellEditorListener");
        this.requestFocus();  
        objLnkLis.add(l);
    }
    
    public void cancelCellEditing()
    {
        System.out.println("cancelCellEditing");
    }
    
    /**
     * Esta función devuelve el nuevo valor que debe aparecer en la celda que se estaba editando.
     * Por ejemplo, si el editor era un "JComboBox" devolverá la opción que se seleccionó en
     * dicho combo.
     */
    public Object getCellEditorValue()
    {
        System.out.println("getCellEditorValue");
        return this.getText();
    }
    
    /**
     * Esta función devuelve el "Component" utilizado para editar la celda. 
     * Por ejemplo, si el editor es un "JComboBox" devolverá el combo que se utilizará como editor de la celda.
     */
    public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value, boolean isSelected, int row, int column)
    {
        System.out.println("getTableCellEditorComponent: "+value);
        if(value==null)
            this.setHoy();             //Si NO existe fecha, se asigna la fecha del dia actual.
        else
            this.setText(""+value);    //SI existe fecha, se asigna la misma fecha. La fecha puede ser la fecha que estaba antes o la fecha nueva seleccionada.            

        strConCel=this.getText();
        return this;
    }
    
    /**
     * Esta función remueve el listener que notifica cuando el editor se detiene
     * o cancele la edición de la celda.
     */
    public void removeCellEditorListener(javax.swing.event.CellEditorListener l)
    {
        objLnkLis.remove(l);
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
     * Esta función que devuelve true o false para detener la edición de la celda.
     * <BR> True: Cuando los datos que está introduciendo el usuario son correctos.
     * <BR> False: Caso Contrario.
     * <BR> Por ejemplo, puede ser que se esté utilizando un editor para ingreso de fechas y el usuario ha ingresado 31/Feb/2005
     * En cuyo caso deberíamos devolver "false" para obligar al usuario a que ingrese una fecha válida.
     */
    public boolean stopCellEditing() 
    {
        System.out.println("stopCellEditing");
        //setValCelEdi(false);
        setValCelEdi(true);
        return true;
    }
    
    /**
     * Nombre de la función: Establece valor de la celda editada.
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
        System.out.println("setValCelEdi");
        javax.swing.event.ChangeEvent evt=new javax.swing.event.ChangeEvent(this);
        javax.swing.event.CellEditorListener objCelEdiLis;
        for (int i=0;i<objLnkLis.size();i++)
        {
            objCelEdiLis=(javax.swing.event.CellEditorListener)objLnkLis.get(i);
            if (hayCambios){
                objCelEdiLis.editingStopped(evt);
                fireTableEditorListener(new ZafTableEvent(this), INT_AFT_EDI);
            }
            else{
                objCelEdiLis.editingStopped(evt);      //Indica que finaliza la edición. Indiferente de si existen o no cambios.
            }
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
     * Esta función establece el color de primer plano de la celda.
     * @param color El color a aplicar a la celda.
     */
    public void setForeground(java.awt.Color color)
    {
        super.setForeground(color);
    }
    
    /**
     * Esta función establece el color de segundo plano de la celda.
     * @param color El color a aplicar a la celda.
     */
    public void setBackground(java.awt.Color color)
    {
        super.setBackground(color);
    }    

    
    
}
