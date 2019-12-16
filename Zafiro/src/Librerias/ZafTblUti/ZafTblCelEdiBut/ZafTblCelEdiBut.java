/*
 * ZafTblCelEdiBut.java
 *
 * Created on 31 de julio de 2005, 01:14 AM
 */

package Librerias.ZafTblUti.ZafTblCelEdiBut;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase establece el editor a utilizar en la celda basada en el uso de la interface "TableCellEditor".
 * La clase JTable utiliza editores para editar los datos de una celda. Por lo general se utiliza un cuadro
 * de texto como editor. Pero se puede utilizar cualquier clase para editar la celda (JButton, JComboBox,
 * JTextField, etc). La presente clase utiliza como editor la clase JButton. Al dar click en el botón se
 * presenta una ventana de consulta para que el usuario seleccione los datos que desea utilizar en la tabla.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelEdiBut" es la instancia de "ZafTblCelEdiBut".
 * <PRE>
 *           objTblCelEdiBut.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
 *               public void beforeConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   strAux=objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_REF)==null?"":objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_REF).toString();
 *                   objTblCelEdiBut.setCondicionesSQL(" AND a1.tx_desLar LIKE '" + strAux + "%'");
 *               }
 *               public void afterConsultar(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
 *                   if (objTblCelEdiBut.isConsultaAceptada())
 *                       txtGlo.setText("Seleccionó: " + objTblMod.getValueAt(tblDat.getSelectedRow(), INT_TBL_DAT_NUM_CTA));
 *                   else
 *                       txtGlo.setText("Canceló");
 *               }
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
public class ZafTblCelEdiBut extends javax.swing.JButton implements javax.swing.table.TableCellEditor
{
    //Constantes:
    private static final int INT_BEF_EDI=0;     /**Antes de editar: Indica "Before edit".*/
    private static final int INT_AFT_EDI=1;     /**Después de editar: Indica "After edit".*/
    private static final int INT_BEF_CON=2;     /**Antes de consultar: Indica "Before consultar".*/
    private static final int INT_AFT_CON=3;     /**Después de consultar: Indica "After consultar".*/
    //Variables:
    private java.util.LinkedList objLinLis=new java.util.LinkedList();  //Almacena la lista de suscriptores.
    private javax.swing.JTable tblDat;
    private String strTit, strCon, strUsr, strCla, strSQL, strCam, strAli, strConSQL;
    private int intColVen[], intColTbl[];
    private boolean blnConAce;                  //Determina si se aceptó/canceló la consulta.
    private boolean blnCanEdi;                  //Determina si se debe cancelar la edición de la celda.
    protected EventListenerList objEveLisLis=new EventListenerList();
    
    /** 
     * Crea una nueva instancia de la clase ZafTblCelEdiBut.
     * @param titulo El título de la ventana de consulta.
     * @param tabla El objeto JTable a quien devolverá los datos la ventana de consulta.
     * @param stringConexion La cadena que contiene información para conectarse a la base de datos.
     * @param usuario El usuario para conectarse a la base de datos.
     * @param clave La clave del usuario para conectarse a la base de datos.
     * @param sentenciaSQL La sentencia SQL que utilizará la ventana de consulta para obtener los datos.
     * @param campos El nombre de los campos utilizados en la ventana de consulta.
     * Por ejemplo: "co_cli, tx_nom, tx_dir".
     * @param alias El alias de los campos utilizado como título de cada columna en la ventana de consulta.
     * Por ejemplo: "Código, Nombre, Dirección"
     * @param columnasVentana Las columnas de la ventana que se presentarán en el JTable.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     * @param columnasTabla Las columnas del JTable donde se presentarán los campos devueltos por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     * <BR>Por ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>columnasVentana</I></TD><TD><I>columnasTabla</I></TD><TD><I>Significado</I></TD></TR>
     *     <TR><TD>1</TD><TD>1</TD><TD>El campo 1 de la ventana de consulta se mostrará en la columna 1 de la tabla.</TD></TR>
     *     <TR><TD>2</TD><TD>2</TD><TD>El campo 2 de la ventana de consulta se mostrará en la columna 2 de la tabla.</TD></TR>
     *     <TR><TD>3</TD><TD>4</TD><TD>El campo 3 de la ventana de consulta se mostrará en la columna 4 de la tabla.</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Nota.- No es necesario que todas las columnas devueltas por la ventana de consulta sean presentadas en la tabla.
     */
    public ZafTblCelEdiBut(String titulo, javax.swing.JTable tabla, String stringConexion, String usuario, String clave, String sentenciaSQL, String campos, String alias, int columnasVentana[], int columnasTabla[])
    {
        super("...");
        strTit=titulo;
        tblDat=tabla;
        strCon=stringConexion;
        strUsr=usuario;
        strCla=clave;
        strSQL=sentenciaSQL;
        strCam=campos;
        strAli=alias;
        intColVen=columnasVentana;
        intColTbl=columnasTabla;
        strConSQL="";
        blnConAce=false;
        blnCanEdi=false;
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
        return true;
    }
    
    /**
     * Nombre de la función: Mostrar ventana de consulta.
     * Muestra la ventana de consulta para que el usuario seleccione la opción que desee.
     */
    private void mostrarVenCon()
    {
        //Generar evento "beforeConsultar()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_CON);
        //Crear e inicializar el objeto "ZafConsulta".
        Librerias.ZafConsulta.ZafConsulta objVenCon;
        objVenCon=new Librerias.ZafConsulta.ZafConsulta(javax.swing.JOptionPane.getFrameForComponent(this), strAli, strCam, strSQL + strConSQL, "", strCon, strUsr, strCla);
        objVenCon.setTitle(strTit);
        //Inicializar la variable que almacena si se aceptó/canceló la consulta.
        blnConAce=true;
        objVenCon.show();
        if (objVenCon.acepto())
        {
            int i, intFilSel;
            intFilSel=tblDat.getSelectedRow();
            for (i=0; i<intColVen.length; i++)
                tblDat.setValueAt(objVenCon.GetCamSel(intColVen[i]), intFilSel, intColTbl[i]);
        }
        else
        {
            blnConAce=false;
        }
        objVenCon=null;
        //Generar evento "afterConsultar()".
        fireTableEditorListener(new ZafTableEvent(this), INT_AFT_CON);
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
        tblDat.requestFocus();
    }
    
    /**
     * Esta función se ejecuta cuando el editor obtiene el foco.
     */
    private void thisFocusGained(java.awt.event.FocusEvent evt)
    {
        //Permitir de manera predeterminada la edición de la celda.
        blnCanEdi=false;
        //Generar evento "beforeEdit()".
        fireTableEditorListener(new ZafTableEvent(this), INT_BEF_EDI);
        //Permitir/Cancelar la edición de acuerdo a "setCancelarEdicion".
        if (blnCanEdi)
            setValCelEdi(false);
        this.requestFocus();
    }
    
    /**
     * Esta función obtiene el título de la ventana de consulta.
     * @return El título de la ventana de consulta.
     */
    public String getTituloVentana()
    {
        return strTit;
    }
    
    /**
     * Esta función establece el título de la ventana de consulta.
     * @param titulo El título de la ventana de consulta.
     */
    public void setTituloVentana(String titulo)
    {
        strTit=titulo;
    }
    
    /**
     * Esta función obtiene la sentencia SQL utilizada por la ventana de consulta.
     * @return La sentencia SQL utilizada por la ventana de consulta.
     */
    public String getSentenciaSQL()
    {
        return strSQL;
    }
    
    /**
     * Esta función establece la sentencia SQL utilizada por la ventana de consulta.
     * @param sentenciaSQL La sentencia SQL utilizada por la ventana de consulta.
     */
    public void setSentenciaSQL(String sentenciaSQL)
    {
        strSQL=sentenciaSQL;
    }
    
    /**
     * Esta función obtiene los campos utilizados por la ventana de consulta.
     * @return Los campos utilizados por la ventana de consulta.
     */
    public String getCampos()
    {
        return strCam;
    }
    
    /**
     * Esta función establece los campos utilizados por la ventana de consulta.
     * @param campos Los campos utilizados por la ventana de consulta.
     */
    public void setCampos(String campos)
    {
        strCam=campos;
    }
    
    /**
     * Esta función obtiene el alias de los campos utilizado como cabecera
     * de las columnas de la ventana de consulta.
     * @return Los campos utilizados por la ventana de consulta.
     */
    public String getAliasCampos()
    {
        return strAli;
    }
    
    /**
     * Esta función establece el alias de los campos utilizado como cabecera
     * de las columnas de la ventana de consulta.
     * @param campos Los campos utilizados por la ventana de consulta.
     */
    public void setAliasCampos(String alias)
    {
        strAli=alias;
    }
    
    /**
     * Esta función obtiene el arreglo que contiene las columnas de la ventana
     * de consulta que se utilizarán en la tabla.
     * @return Las columnas de la ventana de consulta que se utilizarán en la tabla.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     */
    public int[] getColumnasVentana()
    {
        return intColVen;
    }
    
    /**
     * Esta función establece el arreglo que contiene las columnas de la ventana
     * de consulta que se utilizarán en la tabla.
     * @param columnas Las columnas de la ventana de consulta que se utilizarán en la tabla.
     * <BR>Nota.- Las columnas de la ventana empiezan desde la posición 1.
     */
    public void setColumnasVentana(int columnas[])
    {
        intColVen=columnas;
    }
    
    /**
     * Esta función obtiene el arreglo que contiene las columnas de la tabla
     * donde se presentarán los campos devueltos por la ventana de consulta.
     * @return Las columnas de la tabla donde se presentarán los campos devueltos
     * por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     */
    public int[] getColumnasTabla()
    {
        return intColTbl;
    }
    
    /**
     * Esta función establece el arreglo que contiene las columnas de la tabla
     * donde se presentarán los campos devueltos por la ventana de consulta.
     * @param columnas Las columnas de la tabla donde se presentarán los campos
     * devueltos por la ventana de consulta.
     * <BR>Nota.- Las columnas de la tabla empiezan desde la posición 0.
     */
    public void setColumnasTabla(int columnas[])
    {
        intColTbl=columnas;
    }
    
    /**
     * Esta función obtiene las condiciones adicionales que se pueden agregar antes
     * de efectuar la consulta a la base de datos. En la mayoría de los casos es
     * suficiente el uso del método "setCampoBusqueda". Pero, hay ocasiones en las
     * que hay que adicionar otras condiciones de manera dinámica. Es en estos casos
     * donde se debe utilizar el método "setCondicionesSQL" en conjunto con el método
     * "beforeConsultar" de la interface "ZafTableListener".
     * @return Las condiciones adicionales que se agregarán a la sentencia SQL.
     */
    public String getCondicionesSQL()
    {
        return strConSQL;
    }
    
    /**
     * Esta función establece las condiciones adicionales que se pueden agregar antes
     * de efectuar la consulta a la base de datos. En la mayoría de los casos es
     * suficiente el uso del método "setCampoBusqueda". Pero, hay ocasiones en las
     * que hay que adicionar otras condiciones de manera dinámica. Es en estos casos
     * donde se debe utilizar el método "setCondicionesSQL" en conjunto con el método
     * "beforeConsultar" de la interface "ZafTableListener".
     * <BR>Por ejemplo, podríamoms necesitar que se busque los clientes de acuerdo a
     * la ciudad seleccionada en otra celda. Para éste ejemplo la función debería
     * recibir " AND co_ciu=3". Donde "3" representa la ciudad que se encuentra
     * seleccionada en la otra celda.
     * @param condiciones Las condiciones adicionales que se agregarán a la sentencia SQL.
     */
    public void setCondicionesSQL(String condiciones)
    {
        strConSQL=condiciones;
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
     * Esta función determina si la consulta ha sido aceptada o cancelada.
     * @return true: Si se aceptó la consulta.
     * <BR>false: En el caso contrario.
     */
    public boolean isConsultaAceptada()
    {
        return blnConAce;
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