/*
 * ZafTblMod.java
 *
 * Created on 25 de junio de 2005, 10:26 PM
 * v0.16
 */

package Librerias.ZafTblUti.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;
import java.util.ArrayList;

/**
 * Esta clase crea un modelo para el JTable basada en el uso de la clase "AbstractTableModel".
 * El modelo es el lugar donde se encuentran los datos que presenta el JTable. Por ejemplo,
 * un JTable a la vista del usuario puede tener 100 filas y mostrar 20 de ellas porque la 
 * pantalla no permite mostrar las 100 filas de golpe. Para ver la fila 21 y siguientes deberá
 * hacer un desplazamiento. Cuando se hace esto el JTable tiene que hacer 2 pasos:
 * <UL>
 * <LI>1) Sacar la fila 1.
 * <LI>2) Solicitar al modelo la fila 21.
 * </UL>
 * Este proceso se repite cada vez que el JTable necesita mostrar una nueva fila.
 * <BR>Nota.- Para almacenar la cabecera y los datos del JTable se utiliza la clase "java.util.Vector".
 * <BR>
 * <BR><B>Control de cambios en los datos del modelo.-</B>
 * Cada fila del modelo tiene un estado que es almacenado en la primera columna del modelo. Dicha columna
 * por lo general contiene el número de fila en el JTable. Es decir que no es visible a la vista del usuario.
 * <BR>Los posibles valores son los siguientes:
 * <CENTER>
 * <TABLE BORDER=1>
 *     <TR><TD><I>Valor</I></TD><TD><I>Observación</I></TD></TR>
 *     <TR><TD><I>null</I> o una cadena vacía</TD><TD>La fila no ha sido alterada.</TD></TR>
 *     <TR><TD>I</TD><TD>La fila ha sido insertada.</TD></TR>
 *     <TR><TD>M</TD><TD>La fila ha sido modificada.</TD></TR>
 * </TABLE>
 * </CENTER>
 * <BR>La eliminación de filas es un caso especial ya que al eliminar una fila del modelo se pierde los datos de
 * dicha fila. Para evitar perder los datos más relevantes de las filas eliminadas se utiliza un ArrayList
 * que almacena dichos datos. Puede darse el caso en el que se desee agregar a dicho ArrayList más datos para
 * que sean considerados como eliminados. Para tal efecto existe el método "addRowDataSavedBeforeRemoveRow()".
 * <BR>Los métodos utilizados son:
 * <UL>
 * <LI>getColsSaveBeforeRemoveRow().
 * <LI>setColsSaveBeforeRemoveRow().
 * <LI>addRowDataSavedBeforeRemoveRow().
 * <LI>getDataSavedBeforeRemoveRow().
 * <LI>clearDataSavedBeforeRemoveRow().
 * <LI>initRowsState().
 * </UL>
 * <BR><B>Resaltar ciertas filas en el JTable.-</B>
 * Para resaltar filas en un JTable se debe utilizar como renderizador la clase "Librerias.ZafTblUti.ZafTblFilCab".
 * Por lo general lo único que se necesita de la clase "ZafTblFilCab" es que muestre el número de fila. Pero,
 * hay ocasiones en las que es necesario marcar ciertas filas de manera que el usuario se entere que hay algún
 * problema con dichas filas. Una solución a éste problema es cambiar el color del texto del JButton que se
 * utiliza como renderizador. Además, es posible agregar un ToolTipText al JButton de manera que se muestre
 * una descripción más detallada del problema que tiene dicha fila.
 * <BR>Los métodos utilizados son:
 * <UL>
 * <LI>addElementAtRowHeaderRaise().
 * <LI>clearRowHeaderRaise().
 * <LI>getRowHeaderRaise().
 * <LI>getSizeRowHeaderRaise().
 * </UL>
 * @author  Eddye Lino
 */
public class ZafTblMod extends javax.swing.table.AbstractTableModel
{
    //Constantes:
    public static final int INT_TBL_NO_EDI=0;                   /**Un valor para setModoOperacion: Indica "Tabla no editable".*/
    public static final int INT_TBL_EDI=1;                      /**Un valor para setModoOperacion: Indica "Tabla editable".*/
    public static final int INT_TBL_INS=2;                      /**Un valor para setModoOperacion: Indica "Tabla editable e insertable".*/

    public static final int INT_COL_STR=0;                      /**Un valor para setColumnDataType: Indica "Columna que almacena cadenas".*/
    public static final int INT_COL_INT=1;                      /**Un valor para setColumnDataType: Indica "Columna que almacena números enteros".*/
    public static final int INT_COL_DBL=2;                      /**Un valor para setColumnDataType: Indica "Columna que almacena números reales".*/
    
    public static final int INT_EDI_CEL=0;                      /**Un valor para setTipoEdicion: Indica "Edición de celdas".*/
    public static final int INT_EDI_FIL=1;                      /**Un valor para setTipoEdicion: Indica "Edición de filas".*/
    public static final int INT_EDI_COL=2;                      /**Un valor para setTipoEdicion: Indica "Edición de columnas".*/
    
    public static final int INT_COM_NUM_IGU=0;                  /**Un valor para compareNumericCells: Indica "Igual".*/
    public static final int INT_COM_NUM_DIF=1;                  /**Un valor para compareNumericCells: Indica "Diferente".*/
    public static final int INT_COM_NUM_MAY=2;                  /**Un valor para compareNumericCells: Indica "Mayor que".*/
    public static final int INT_COM_NUM_MAY_IGU=3;              /**Un valor para compareNumericCells: Indica "Mayor o igual que".*/
    public static final int INT_COM_NUM_MEN=4;                  /**Un valor para compareNumericCells: Indica "Menor que".*/
    public static final int INT_COM_NUM_MEN_IGU=5;              /**Un valor para compareNumericCells: Indica "Menor o igual que".*/
    //Variables:
    private ZafUtil objUti;
    private Vector vecCabMod;                                   //Cabecera de la tabla.
    private Vector vecDatMod;                                   //Datos de la tabla.
    private Vector vecFilEdi;                                   //Filas editables.
    private Vector vecColEdi;                                   //Columnas editables.
    private ArrayList arlTipDatCol;                             //Tipo de columna.
    private ArrayList arlColObl;                                //Columnas obligatorias.
    private ArrayList arlColOcuSis;                             //Columnas ocultas por el Sistema.
    private ArrayList arlColOcuUsr;                             //Columnas ocultas por el usuario.
    private java.util.Hashtable htbEstFil;                      //Estado de la fila.
    private ArrayList arlColGuaEli;                             //Columnas a guardar antes de eliminar una fila.
    private ArrayList arlDatGuaEli;                             //Datos guardados antes de eliminar una fila.
    private ArrayList arlFilGuaManEli;                          //Filas guardadas manualmente en el ArrayList arlDatGuaEli.
    private ArrayList arlRowHeaDat;                             //Row Header Data.
    private int intModOpe;                                      //Modo de operación.
    private int intTipEdi;                                      //Tipo de edición.
    private java.awt.Color colFilInc;                           //Color de las filas incompletas.
    private boolean blnHayCam;                                  //Determina si hay cambios.
    private Integer objIntMaxNumFilPer;                         //Almacena el máximo número de filas permitidas.

    /** Crea una nueva instancia de la clase ZafTblMod. */
    public ZafTblMod()
    {
        objUti=new ZafUtil();
        vecCabMod=new Vector();
        vecDatMod=new Vector();
        vecFilEdi=new Vector();
        vecColEdi=new Vector();
        arlTipDatCol=new ArrayList();
        arlColObl=new ArrayList();
        arlColOcuSis=new ArrayList();
        arlColOcuUsr=new ArrayList();
        arlColGuaEli=new ArrayList();
        arlDatGuaEli=new ArrayList();
        arlFilGuaManEli=new ArrayList();
        arlRowHeaDat=new ArrayList();
        htbEstFil=new java.util.Hashtable();
        intModOpe=INT_TBL_NO_EDI;
        intTipEdi=INT_EDI_COL;
        blnHayCam=false;
    }

    /**
     * Esta función obtiene el número total de filas almacenadas en el modelo.
     * @return El número de filas que tiene el modelo.
     */
    public int getRowCount()
    {
        return vecDatMod.size();
    }

    /**
     * Esta función obtiene el número total de columnas almacenadas en el modelo.
     * @return El número de columnas que tiene el modelo.
     */
    public int getColumnCount()
    {
        return vecCabMod.size();
    }

    /**
     * Esta función obtiene el valor que se encuentra almacenado en la celda especificada.
     * @param row La fila de la celda de la que se desea obtener su valor.
     * @param col La columna de la celda de la que se desea obtener su valor.
     * @return El valor almacenado en la celda.
     */
    public Object getValueAt(int row, int col)
    {
        Vector vecAux=(Vector)vecDatMod.elementAt(row);
        if (vecAux==null)
            return null;
        else
            if (vecAux.elementAt(col)==null)
                return null;
            else
                return vecAux.elementAt(col);
    }

    /**
     * Esta función obtiene el nombre de la columna especificada.
     * @param col La columna de la que se desea obtener su nombre.
     * @return El nombre de la columna especificada.
     */
    public String getColumnName(int col)
    {
        if (vecCabMod.elementAt(col)==null)
            return "";
        else
            return vecCabMod.elementAt(col).toString();
    }

    /**
     * Esta función establece la cabecera de la tabla.
     * @param cabecera El vector que contiene los datos de la cabecera.
     */
    public void setHeader(Vector cabecera)
    {
        vecCabMod=(Vector)cabecera.clone();
        //Establecer que todas las columnas de manera predeterminada sean de tipo cadena.
        arlTipDatCol.clear();
        for (int i=0; i<vecCabMod.size(); i++)
        {
            arlTipDatCol.add(null);
            setColumnDataType(i, INT_COL_STR, null, null);
        }
        fireTableStructureChanged();
    }

    /**
     * Esta función obtiene el vector que contiene los datos de la tabla.
     * @return El vector que contiene los datos de la tabla.
     */
    public Vector getData()
    {
        return vecDatMod;
    }
    
    /**
     * Esta función establece los datos de la tabla.
     * @param datos El vector que contiene los datos de la tabla.
     */
    public void setData(Vector datos)
    {
        vecDatMod=(Vector)datos.clone();
        //Limpiar el ArrayList que contiene los datos que se almacenaron antes de eliminar una fila.
        arlDatGuaEli.clear();
        arlFilGuaManEli.clear();
        //Limpiar el ArrayList que contiene las filas a resaltar.
        arlRowHeaDat.clear();
        //Insertar fila si el modo de operación es INT_TBL_INS.
        if (intModOpe==INT_TBL_INS)
            insertRow(false);
        //Limpiar la variable que indica cambios.
        blnHayCam=false;
        fireTableDataChanged();
    }

    /**
     * Esta función establece la cabecera y los datos de la tabla.
     * @param cabecera El vector que contiene los datos de la cabecera.
     * @param datos El vector que contiene los datos de la tabla.
     */
    public void setValues(Vector cabecera, Vector datos)
    {
        vecCabMod=(Vector)cabecera.clone();
        vecDatMod=(Vector)datos.clone();
        //Establecer que todas las columnas de manera predeterminada sean de tipo cadena.
        arlTipDatCol.clear();
        for (int i=0; i<vecCabMod.size(); i++)
        {
            arlTipDatCol.add(null);
            setColumnDataType(i, INT_COL_STR, null, null);
        }
        //Limpiar el ArrayList que contiene los datos que se almacenaron antes de eliminar una fila.
        arlDatGuaEli.clear();
        arlFilGuaManEli.clear();
        //Limpiar el ArrayList que contiene las filas a resaltar.
        arlRowHeaDat.clear();
        //Insertar fila si el modo de operación es INT_TBL_INS.
        if (intModOpe==INT_TBL_INS)
            insertRow();
        //Limpiar la variable que indica cambios.
        blnHayCam=false;
        fireTableStructureChanged();
    }

    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    private void setValueAtCell(Object valor, int row, int col)
    {
        //Validar que no se pueda insertar más filas de las permitidas.
        if (objIntMaxNumFilPer!=null)
        {
            if ((row+1)>objIntMaxNumFilPer.intValue())
            {
                return;
            }
        }
        Vector vecAux=(Vector)vecDatMod.elementAt(row);
        vecAux.setElementAt(valor, col);
        //Almacenar el estado de la fila.
        htbEstFil.put("" + row, "M");
        //Establecer el estado de la fila a "M".
        if (vecAux.get(0)==null)
            vecAux.setElementAt("M", 0);
        else if (vecAux.get(0).toString().equals(""))
            vecAux.setElementAt("M", 0);
        //Establecer la variable que indica cambios.
        blnHayCam=true;
        fireTableCellUpdated(row, col);
    }

    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(Object valor, int row, int col)
    {
        setValueAtCell(valor, row, col);
    }
    
    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(String valor, int row, int col)
    {
        setValueAtCell(valor, row, col);
    }
    
    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(boolean valor, int row, int col)
    {
        setValueAtCell(Boolean.valueOf(valor), row, col);
    }

    /**
     * Esta función determina si la celda especificada es editable. De manera
     * predeterminada las celdas de la tabla no son editables. Si se desea que una
     * celda sea editable se deberá establecer para esa celda su valor a verdadero.
     * @param row La fila de la celda de la que se desea conocer si es editable.
     * @param col La columna de la celda de la que se desea conocer si es editable.
     * @return true: Si la celda es editable.
     * <BR>false: En el caso contrario.
     */
    public boolean isCellEditable(int row, int col)
    {
        boolean blnRes=false;
        if (intModOpe==INT_TBL_NO_EDI)
            blnRes=false;
        else
            switch (intTipEdi)
            {
                case INT_EDI_CEL:
                    break;
                case INT_EDI_FIL:
                    if (vecFilEdi.contains("" + row))
                        blnRes=true;
                    break;
                case INT_EDI_COL:
                    if (vecColEdi.contains("" + col))
                        blnRes=true;
                    break;
            }
        return blnRes;
    }

    /**
     * Esta función obtiene la clase de tipo de dato que almacena la columna especificada.
     * @param col La columna de la que se desea obtener su tipo de dato.
     * @return La clase de tipo de dato de la columna especificada.
     */
    public Class getColumnClass(int col)
    {
        Object obj=getValueAt(0, col);
        if (obj==null)
        {
            return Object.class;
        }
        else
        {
            return obj.getClass();
        }
    }

    /**
     * Esta función intercambia las filas especificadas.
     * @param origen La fila que se va a intercambiar con la fila destino.
     * @param destino La fila con la que se va a intercambiar la fila origen.
     * @return true: Si se pudo intercambiar las filas.
     * <BR>false: En el caso contrario.
     */
    public boolean intercambiarFilas(int origen, int destino)
    {
        //Validar que no se pueda intercambiar con la última fila si el modo de operación es INT_TBL_INS.
        if ( ((origen==vecDatMod.size()-1) || (destino==vecDatMod.size()-1)) && (intModOpe==INT_TBL_INS) )
            return false;
        if ( (origen>=0) && (origen<=(getRowCount()-1)) && (destino>=0) && (destino<=(getRowCount()-1)) )
        {
            Vector vecAux=new Vector();
            vecAux=(Vector)vecDatMod.get(destino);
            vecDatMod.set(destino, (Vector)vecDatMod.get(origen));
            vecDatMod.set(origen, vecAux);
            vecAux=null;
            fireTableDataChanged();
            return true;
        }
        return false;
    }

    /**
     * Esta función obtiene el estado de la fila especificada. Hay ocasiones en las que
     * es necesario determinar el estado de una fila y en función de eso determinar la acción
     * a tomar. Por ejemplo, se puede tener 1000 filas y modificar 2 de ellas con lo que
     * comunmente se procede a actualizar las 1000 filas (O sea, se hacen 1000 updates). Esto
     * estaría mal ya que sólo se debería actualizar las 2 filas que en realidad se modificaron.
     * @param row La fila de la que se desea obtener el estado.
     * @return Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>N: La fila no ha sido alterada
     * <LI>I: La fila ha sido insertada
     * <LI>E: La fila ha sido eliminada
     * <LI>M: La fila ha sido modificada
     * </UL>
     * @deprecated No se recomienda el uso de éste método ya que fue reemplazado por el nuevo esquema
     * donde en vez de grabar el estado de la fila en un "Hashtable" se graba dicho estado en la primera
     * celda de cada fila. Los estados pueden ser: M=Fila modificada, I=Fila insertada.
     * @see #getColsSaveBeforeRemoveRow()
     * @see #setColsSaveBeforeRemoveRow(java.util.ArrayList cols)
     * @see #getDataSavedBeforeRemoveRow()
     * @see #clearDataSavedBeforeRemoveRow()
     * @see #initRowsState()
     */
    public char getEstadoFila(int row)
    {
        char chrRes='O';
        try
        {
            chrRes=htbEstFil.get("" + row).toString().charAt(0);
        }
        catch (NullPointerException e)
        {
            chrRes='O';
        }
        catch (Exception e)
        {
            chrRes='O';
        }
        return chrRes;
    }

    /**
     * Esta función inicializa el estado de todas las filas. Es decir, que todas las filas
     * vuelven a su estado inicial. Esta función se debe utilizar cuando se haya realizado
     * alguna operación (actualización, eliminación, etc) ya que sino se considerará que la fila
     * continua en el estado que se encontraba antes de realizar dicha operación. Por ejemplo,
     * si realizó una actualización lo más conveniente es llamar a ésta función luego de haber hecho
     * dicha operación porque si la idea es modificar unas cuantas filas y grabar y repetir este
     * proceso hasta modificar todas las filas que se tenían que modificar. Lo recomendable es
     * que luego de grabar los cambios que se hicieron a las filas se utilice ésta función para
     * que la próxima vez que se vaya a grabar los cambios sólo se grabe las nuevas filas que han
     * sido alteradas.
     * @deprecated No se recomienda el uso de éste método ya que fue reemplazado por el nuevo esquema
     * donde en vez de grabar el estado de la fila en un "Hashtable" se graba dicho estado en la primera
     * celda de cada fila. Los estados pueden ser: M=Fila modificada, I=Fila insertada.
     * @see #getColsSaveBeforeRemoveRow()
     * @see #setColsSaveBeforeRemoveRow(java.util.ArrayList cols)
     * @see #getDataSavedBeforeRemoveRow()
     * @see #clearDataSavedBeforeRemoveRow()
     * @see #initRowsState()
     */
    public void inicializarEstadoFilas()
    {
        htbEstFil.clear();
    }

    /**
     * Esta función obtiene el número total de filas alteradas. Por ejemplo, se pueden
     * tener en total 100 filas de las cuales se han modificado 5, insertado 2 y
     * eliminado 3. Con lo cual ésta función devolvería 10 porque de las 100 filas sólo
     * 10 de ellas han sido alteradas de alguna manera.
     * @return El número de filas alteradas
     * @deprecated No se recomienda el uso de éste método ya que fue reemplazado por el nuevo esquema
     * donde en vez de grabar el estado de la fila en un "Hashtable" se graba dicho estado en la primera
     * celda de cada fila. Los estados pueden ser: M=Fila modificada, I=Fila insertada.
     * @see #getColsSaveBeforeRemoveRow()
     * @see #setColsSaveBeforeRemoveRow(java.util.ArrayList cols)
     * @see #getDataSavedBeforeRemoveRow()
     * @see #clearDataSavedBeforeRemoveRow()
     * @see #initRowsState()
     */
    public int getNumeroFilasAlteradas()
    {
        return htbEstFil.size();
    }

    /**
     * Esta función obtiene el indice de las filas alteradas. Por ejemplo, se pueden
     * tener en total 100 filas de las cuales se han alterado sólo las filas 5, 8,
     * 9, 10 y 25 con lo cual ésta función devolvería una enumeración cuyo contenido
     * sería 5, 8, 9, 10 y 25 ya que dichas filas han sido alteradas.
     * @return Una enumeración que contiene el indice de las filas alteradas
     * @deprecated No se recomienda el uso de éste método ya que fue reemplazado por el nuevo esquema
     * donde en vez de grabar el estado de la fila en un "Hashtable" se graba dicho estado en la primera
     * celda de cada fila. Los estados pueden ser: M=Fila modificada, I=Fila insertada.
     * @see #getColsSaveBeforeRemoveRow()
     * @see #setColsSaveBeforeRemoveRow(java.util.ArrayList cols)
     * @see #getDataSavedBeforeRemoveRow()
     * @see #clearDataSavedBeforeRemoveRow()
     * @see #initRowsState()
     */
    public java.util.Enumeration getFilasAlteradas()
    {
        return htbEstFil.keys();
    }
    
    /**
     * Esta función establece el tipo de edición permitido en la tabla. Puede tomar
     * los siguientes valores:
     * <UL>
     * <LI>0: Edición de celdas (INT_EDI_CEL).
     * <LI>1: Edición de filas (INT_EDI_FIL).
     * <LI>2: Edición de columnas (INT_EDI_COL).
     * </UL>
     * <BR>Nota.- La opción predeterminada es edición de columnas (INT_EDI_COL).
     * @param tipoEdicion El tipo de edición.
     */
    public void setTipoEdicion(int tipoEdicion)
    {
        intTipEdi=tipoEdicion;
    }

    /**
     * Esta función obtiene las columnas que son editables en la tabla.
     * Por ejemplo: se puede tener 5 columnas de donde sólo las columnas 2 y 4 son editables.
     * @return Un vector que contiene las columnas editables.
     */
    public Vector getColumnasEditables()
    {
        return vecColEdi;
    }
    
    /**
     * Esta función establece las columnas que serán editables en la tabla.
     * @param columnas El vector que contiene las columnas editables.
     * <BR>Nota.-Si la función recibe  <I>null</I> todas las columnas dejan de ser editables.
     */
    public void setColumnasEditables(Vector columnas)
    {
        if (columnas==null)
            vecColEdi.clear();
        else
            vecColEdi=(Vector)columnas.clone();
    }

    /**
     * Esta función determina si la columna especificada es editable.
     * @param col La columna de la que se desea conocer si es editable.
     * @return true: Si la columna es editable.
     * <BR>false: En el caso contrario.
     */
    public boolean isColumnaEditable(int col)
    {
        boolean blnRes=false;
        try
        {
            if (vecColEdi!=null)
            {
                if (vecColEdi.contains("" + col))
                    blnRes=true;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función establece las filas que serán editables en la tabla.
     * @param filas El vector que contiene las filas editables.
     */
    public void setFilasEditables(Vector filas)
    {
        vecFilEdi.clear();
        vecFilEdi=filas;
    }
    
    /**
     * Esta función establece el número de filas que tendrá la tabla. Pueden presentarse
     * los siguientes casos:
     * <UL>
     * <LI>Si el número de filas a establecer es igual al número de filas actual: La función no hace nada.
     * <LI>Si el número de filas a establecer es menor al número de filas actual: La función elimina las filas del final.
     * <LI>Si el número de filas a establecer es mayor al número de filas actual: La función inserta filas al final.
     * </UL>
     * @param filas El número de filas que tendrá la tabla.
     */
    public void setRowCount(int filas)
    {
        int intNumFil;
        intNumFil=getRowCountTrue();
        if (filas==intNumFil)
            return;
        else
        {
            if (filas<intNumFil)
            {
                //Eliminar filas.
                vecDatMod.setSize(filas);
                fireTableRowsDeleted(intNumFil-filas+1,intNumFil-1);
            }
            else
            {
                //Insertar filas.
                insertRows(filas-intNumFil, intNumFil);
            }
        }
    }

    /**
     * Esta función inserta una fila en blanco al final de la tabla.
     * @return true: Si se pudo insertar la fila.
     * <BR>false: En el caso contrario.
     */
    public boolean insertRow()
    {
        return insertRows(1, vecDatMod.size());
    }
    
    /**
     * Esta función inserta una fila en blanco en la tabla.
     * @param posicion La posición donde se insertará la fila.
     * <BR>Nota.- Las filas empiezan desde la posición cero.
     * @return true: Si se pudo insertar la fila.
     * <BR>false: En el caso contrario.
     */
    public boolean insertRow(int posicion)
    {
        return insertRows(1, posicion);
    }

    /**
     * Esta función inserta una fila en blanco al final de la tabla. La inserción de dicha fila está condicionada
     * por el valor del parámetro "validarFilas" y por la configuración establecida en el método "setMaxRowsAllowed".
     * Ejemplo: Si la tabla no tiene establecido un máximo número de filas permitidas no importa el valor del
     * parámetro "validarFilas". Pero si la tabla tiene establecido un máximo número de filas el parámetro
     * "validarFilas" servirá para ver si se debe validar o no la fila que se está insertando.
     * Esta funcion fue utilizada en el metodo "setData(Vector datos)" para evitar que cuando se consultaba un
     * documento ya guardado apareciera el mensaje "No es posible insertar más filas.\nLa tabla está configurada
     * para trabajar con un máximo de n filas".
     * @param validarFilas Valor booleano que determina si se debe validar o no el máximo número de filas permitidas.
     * @return true: Si se pudo insertar la fila.
     * <BR>false: En el caso contrario.
     */
    private boolean insertRow(boolean validarFilas)
    {
        return insertRows(1, vecDatMod.size(), validarFilas);
    }

    /**
     * Esta función inserta filas en blanco en la tabla.
     * @param filas El número de filas que se insertaran.
     * @param posicion La posición a partir de donde se insertaran las filas.
     * <BR>Nota.- Las filas empiezan desde la posición cero.
     * @return true: Si se pudo insertar la fila.
     * <BR>false: En el caso contrario.
     */
    public boolean insertRows(int filas, int posicion)
    {
        return insertRows(filas, posicion, true);
    }

    /**
     * Esta función inserta filas en blanco en la tabla. La inserción de dichas filas está condicionada
     * por el valor del parámetro "validarFilas" y por la configuración establecida en el método "setMaxRowsAllowed".
     * Ejemplo: Si la tabla no tiene establecido un máximo número de filas permitidas no importa el valor del
     * parámetro "validarFilas". Pero si la tabla tiene establecido un máximo número de filas el parámetro
     * "validarFilas" servirá para ver si se debe validar o no las filas que se están insertando.
     * @param filas El número de filas que se insertaran.
     * @param posicion La posición a partir de donde se insertaran las filas.
     * <BR>Nota.- Las filas empiezan desde la posición cero.
     * @param validarFilas Valor booleano que determina si se debe validar o no el máximo número de filas permitidas.
     * @return true: Si se pudo insertar la fila.
     * <BR>false: En el caso contrario.
     */
    private boolean insertRows(int filas, int posicion, boolean validarFilas)
    {
        int intNumCol, i, j;
        Vector vecAux;
        boolean blnRes=true;
        try
        {
            //Se puso ésta condición porque para el método "setData(Vector datos)" no habia que validar.
            if (validarFilas)
            {
                //Validar que no se pueda insertar más filas de las permitidas.
                if (objIntMaxNumFilPer!=null)
                {
                    if ((vecDatMod.size() + filas - (intModOpe==INT_TBL_INS?1:0))>objIntMaxNumFilPer.intValue())
                    {
                        javax.swing.JOptionPane.showMessageDialog(null, "No es posible insertar más filas.\nLa tabla está configurada para trabajar con un máximo de " + objIntMaxNumFilPer.intValue() + " filas.", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        return false;
                    }
                }
            }
            intNumCol=getColumnCount();
            for (i=0;i<filas;i++)
            {
                vecAux=new Vector();
                for (j=0;j<intNumCol;j++)
                {
                    //Establecer el estado de la fila a "I".
                    if (j==0)
                        vecAux.add("I");
                    else
                        vecAux.add(null);
                }
                vecDatMod.insertElementAt(vecAux, posicion + i);
                vecAux=null;
            }
            //Establecer la variable que indica cambios.
            blnHayCam=true;
            fireTableRowsInserted(posicion, posicion + filas - 1);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función establece las filas que serán editables en la tabla.
     * @param filas El vector que contiene las filas editables.
     */
    private void insertRowData(int posicion)
    {
        //En construcción.
    }
    
    /**
     * Esta función establece las filas que serán editables en la tabla.
     * @param filas El vector que contiene las filas editables.
     */
    private void insertRowsData(int posicion)
    {
        //En construcción.
    }
    
    /**
     * Esta función elimina todas las filas de la tabla.
     */
    public void removeAllRows()
    {
        int intNumRow;
        intNumRow=getRowCount();
        vecDatMod.clear();
        //Insertar una fila al final sólo si el modo de operación es INT_TBL_INS.
        if (intModOpe==INT_TBL_INS)
            insertRow();
        fireTableDataChanged();
    }
    
    /**
     * Esta función elimina una fila de la tabla.
     * @param posicion La posición de la fila que se desea eliminar.
     * <BR>Nota.- Las filas empiezan desde la posición cero.
     * @return true: Si se pudo eliminar la fila.
     * <BR>false: En el caso contrario.
     */
    public boolean removeRow(int posicion)
    {
        return removeRows(posicion, posicion);
    }
    
    /**
     * Esta función elimina una o más filas de la tabla.
     * @param desde La posición desde donde se eliminarán las filas.
     * @param hasta La posición hasta donde se eliminarán las filas.
     * <BR>Nota.- Las filas empiezan desde la posición cero.
     * @return true: Si se pudieron eliminar todas las filas del rango especificado.
     * <BR>false: En el caso contrario.
     */
    public boolean removeRows(int desde, int hasta)
    {
        int i, j;
        boolean blnRes=true;
        try
        {
            if (intModOpe==INT_TBL_INS)
            {
                //No permitir eliminar la última fila si el modo de operación es INT_TBL_INS.
                if (hasta==vecDatMod.size()-1)
                    hasta--;
                else
                {
                    for (i=desde;i<=hasta;i++)
                    {
                        //Almacenar los datos relevantes de las filas eliminadas sólo cuando sea necesario.
                        if (arlColGuaEli.size()>0 && !(getValueAt(desde,0)==null?"":getValueAt(desde,0)).toString().equals("I"))
                        {
                            //Almacenar la fila antes de eliminarla.
                            ArrayList arlAux=new ArrayList();
                            for (j=0; j<arlColGuaEli.size();j++)
                            {
                                arlAux.add(getValueAt(desde, Integer.parseInt(arlColGuaEli.get(j).toString())));
                            }
                            arlDatGuaEli.add(arlAux);
                            arlAux=null;
                        }
                        //Eliminar del ArrayList que contiene las filas a resaltar las filas eliminadas en el modelo.
                        if (arlRowHeaDat.size()>0)
                        {
                            ArrayList arlAux;
                            for (j=0;j<arlRowHeaDat.size();j++)
                            {
                                arlAux=(ArrayList)arlRowHeaDat.get(j);
                                if (Integer.parseInt(arlAux.get(0).toString())==desde)
                                {
                                    arlRowHeaDat.remove(j);
                                    break;
                                }
                            }
                            arlAux=null;
                        }
                        vecDatMod.removeElementAt(desde);
                    }
                    //Actualizar el ArrayList que contiene las filas a resaltar.
                    if (arlRowHeaDat.size()>0)
                    {
                        ArrayList arlAux;
                        int intFil;
                        for (j=0;j<arlRowHeaDat.size();j++)
                        {
                            arlAux=(ArrayList)arlRowHeaDat.get(j);
                            intFil=Integer.parseInt(arlAux.get(0).toString());
                            if (intFil>=desde)
                            {
                                arlAux.set(0,"" +(intFil-(hasta-desde+1)));
                                arlRowHeaDat.set(j,arlAux);
                            }
                        }
                        arlAux=null;
                    }
                    //Establecer la variable que indica cambios.
                    blnHayCam=true;
                    fireTableRowsDeleted(desde, hasta);
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene la fila especificada. Dicha fila es un vector donde
     * cada elemento del vector representa una celda de la fila. Esta función es
     * útil cuando se necesita obtener toda una fila y no sólo el valor de una
     * celda o de toda la tabla.
     * @param row El número de la fila que se desea obtener.
     * @return La fila especificada.
     * <BR>Nota.- Si no se pudo obtener la fila la función devolverá <I>null</I>.
     * Por ejemplo: puede ser que la tabla sólo tenga 10 filas y se desee obtener
     * la fila 30. Esto error provoca que la función devuelva <I>null</I>.
     */
    public Object getRow(int row)
    {
        try
        {
            return vecDatMod.elementAt(row);
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    
    /**
     * Esta función determina si la fila especificada esta vacía. Se considera que una
     * fila está vacía si el contenido de sus celdas es <I>null</I> o una cadena vacía.
     * @param row La fila de la que se desea conocer si está vacía.
     * @return true: Si la fila está vacía.
     * <BR>false: En el caso contrario.
     */
    public boolean isRowEmpty(int row)
    {
        int i;
        Vector vecAux;
        boolean blnRes=true;
        try
        {
            vecAux=(Vector)vecDatMod.elementAt(row);
            //Se evalua desde i=1 porque la columna cero contiene el estado de la fila (M=Modificada, I=Insertada).
            for (i=1; i<vecAux.size(); i++)
            {
                if ( (vecAux.elementAt(i)!=null) && (!vecAux.elementAt(i).toString().equals("")) )
                {
                    blnRes=false;
                    break;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función elimina todas las filas vacías de la tabla. Se considera que una
     * fila está vacía si el contenido de sus celdas es <I>null</I> o una cadena vacía.
     * @return El número de filas que se eliminaron.
     */
    public int removeEmptyRows()
    {
        int i, j;
        int intRes=0;
        //Si el modo de operación es INT_TBL_INS no se permite eliminar la última fila.
        if (intModOpe==INT_TBL_INS)
            j=vecDatMod.size()-1;
        else
            j=vecDatMod.size();
        for (i=0; i<j; i++)
        {
            if (isRowEmpty(i-intRes))
            {
                vecDatMod.removeElementAt(i-intRes);
                intRes++;
            }
        }
        fireTableDataChanged();
        return intRes;
    }
    
    /**
     * Esta función obtiene el modo de operación de la tabla. Puede devolver
     * los siguientes valores:
     * <UL>
     * <LI>0: Tabla no editable (INT_TBL_NO_EDI).
     * <LI>1: Tabla editable (INT_TBL_EDI).
     * <LI>2: Tabla editable e insertable (INT_TBL_INS).
     * </UL>
     * <BR>Nota.- La opción predeterminada es que la tabla no sea editable (INT_TBL_NO_EDI).
     * @return El modo de operación de la tabla.
     */
    public int getModoOperacion()
    {
        return intModOpe;
    }
    
    /**
     * Esta función establece el modo de operación de la tabla. Puede tomar
     * los siguientes valores:
     * <UL>
     * <LI>0: Tabla no editable (INT_TBL_NO_EDI).
     * <LI>1: Tabla editable (INT_TBL_EDI).
     * <LI>2: Tabla editable e insertable (INT_TBL_INS).
     * </UL>
     * <BR>Nota.- La opción predeterminada es que la tabla no sea editable (INT_TBL_NO_EDI).
     * @param modo El tipo de edición.
     * <BR>Nota.- Si establece la tabla a INT_TBL_EDI o INT_TBL_INS debe utilizar el método
     * <I>setTipoEdicion</I> para indicar el tipo de edición que se va a permitir.
     */
    public void setModoOperacion(int modo)
    {
        //Insertar fila si el nuevo modo de operación es INT_TBL_INS.
        if ( (intModOpe!=INT_TBL_INS) && (modo==INT_TBL_INS) )
        {
            insertRow(false);
            intModOpe=modo;
        }
        else
        {
            if (intModOpe==INT_TBL_INS)
            {
                intModOpe=modo;
                removeRow(vecDatMod.size()-1);
            }
            else
                intModOpe=modo;
        }
    }
    
    /**
     * Esta función obtiene el número total de filas almacenadas en el modelo.
     * La función <I>getRowCount</I> devuelve el número total de filas que tiene 
     * la tabla mientras que <I>getRowCountTrue</I> devuelve el número de filas
     * que realmente contienen datos. Cuando se está en el modo INT_TBL_INS el
     * editor agrega una fila auxiliar al final de la tabla. Esta fila no debe
     * ser considerada por el usuario y es por esa razón que ésta función devuelve
     * el número total de filas del modelo exluyendo dicha fila auxiliar.
     * @return El número de filas con datos que tiene el modelo.
     */
    public int getRowCountTrue()
    {
        if (intModOpe==INT_TBL_INS)
            return (vecDatMod.size()-1);
        else
            return vecDatMod.size();
    }
    
    /**
     * Esta función determina si la celda especificada esta marcada. Se utiliza
     * cuando el editor de la celda es una casilla de verificación.
     * @param row La fila de la celda de la que se desea conocer si está marcada.
     * @param col La columna de la celda de la que se desea conocer si está marcada.
     * @return true: Si la celda está marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isChecked(int row, int col)
    {
        Object objAux;
        boolean blnRes=true;
        try
        {
            objAux=((Vector)vecDatMod.get(row)).get(col);
            if (objAux==null)
                blnRes=false;
            else
            {
                if (objAux instanceof Boolean)
                    blnRes=((Boolean)objAux).booleanValue();
                else
                    blnRes=false;
            }
            objAux=null;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función establece si se debe marcar/desmarcar la celda especificada.
     * Se utiliza cuando el editor de la celda es una casilla de verificación.
     * @param checked Valor booleano que determina si se debe marcar/desmarcar la celda.
     * @param row La fila de la celda que se desea marcar/desmarcar.
     * @param col La columna de la celda que se desea marcar/desmarcar.
     */
    public void setChecked(boolean checked, int row, int col)
    {
        setValueAt(Boolean.valueOf(checked), row, col);
    }
    
    /**
     * Esta función mueve una fila de una posición a otra.
     * @param origen La posición de la fila que se va a mover.
     * @param destino La posición a la que se va a mover la fila.
     * @return true: Si se pudo mover la fila.
     * <BR>false: En el caso contrario.
     */
    public boolean moveRow(int origen, int destino)
    {
        boolean blnRes=true;
        try
        {
            //Validar que no se pueda mover una fila a la última fila si el modo de operación es INT_TBL_INS.
            if ( ((origen==vecDatMod.size()-1) || (destino==vecDatMod.size()-1)) && (intModOpe==INT_TBL_INS) )
                blnRes=false;
            else
            {
                if ( (origen>=0) && (origen<=(getRowCount()-1)) && (destino>=0) && (destino<=(getRowCount()-1)) )
                {
                    if (origen<destino)
                    {
                        //Mover hacia abajo.
                        vecDatMod.insertElementAt(vecDatMod.get(origen), destino+1);
                        vecDatMod.removeElementAt(origen);
                        fireTableDataChanged();
                    }
                    else
                    {
                        //Mover hacia arriba.
                        vecDatMod.insertElementAt(vecDatMod.get(origen), destino);
                        vecDatMod.removeElementAt(origen+1);
                        fireTableDataChanged();
                    }
                }
                else
                    blnRes=false;
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función mueve varias filas de una posición a otra dentro de la misma tabla.
     * @param origen Arreglo con las posiciones de las filas que se van a mover.
     * @param destino La posición incial a la que se van a mover las filas.
     * @return true: Si se pudieron mover las filas.
     * <BR>false: En el caso contrario.
     */
    public boolean moveRows(int origen[], int destino)
    {
        boolean blnRes=true;
        try
        {
            int intConAdd=0, intConEli=0;
            int intFilDes=destino, intConEliArr=origen.length;
            //Validar que no se pueda mover una fila a la última fila si el modo de operación es INT_TBL_INS.
            for (int j=0; j<origen.length; j++)
            {
                if ( ((origen[j]==vecDatMod.size()-1) || (destino==vecDatMod.size()-1)) && (intModOpe==INT_TBL_INS) )
                        blnRes=false;
                else
                {
                    if ( (origen[j]>=0) && (origen[j]<=(getRowCount()-1)) && (destino>=0) && (destino<=(getRowCount()-1))) 
                    {
                        //Valida que no se mueva cuando la fila destino es una de las filas origen.
                        for (int i = 0; i < origen.length; i++) 
                        {
                            if (origen[i] == destino) 
                                blnRes = false;
                        }
                    }
                    else
                        blnRes=false;
                }
            }
            if (blnRes)
            {
                //Insert Rows
                for (int j=0; j<origen.length; j++)
                {
                    if (origen[j]<intFilDes)
                    {
                        //Mover hacia abajo
                        vecDatMod.insertElementAt(vecDatMod.get(origen[j]), intFilDes);
                    }
                    else
                    {
                        //Mover hacia arriba
                        vecDatMod.insertElementAt(vecDatMod.get(origen[j]+intConAdd), intFilDes);
                        intConAdd++;
                    }
                    intFilDes++;
                }
                //Remove Rows
                for(int j=0; j<origen.length; j++)
                {
                    if (origen[j]<destino)
                    {
                        //Mover hacia abajo
                        vecDatMod.removeElementAt(origen[j]-intConEli);
                        intConEli++;
                    }
                    else
                    {
                        //Mover hacia arriba
                        vecDatMod.removeElementAt(origen[j]+intConEliArr);
                        intConEliArr--;
                    }
                }
               fireTableDataChanged(); 
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función establece el tipo de dato que almacenan las celdas de la columna
     * especificada. Es posible indicar el rango de valores que admiten las celdas de
     * la columna especificada. Por ejemplo, se puede establecer que la columna del DEBE
     * de un asiento de diario admita valores reales positivos haciendo lo siguiente:
     * <CENTER>objTblMod.setColumnDataType(INT_TBL_DAT_DEB, objTblMod.INT_COL_DBL, new Integer(0), null);</CENTER>
     * @param col La columna a la que se va a establecer el tipo de dato.
     * @param type El tipo de dato de la columna.
     * <UL>
     * <LI>0: Columna que almacena cadenas (INT_COL_STR).
     * <LI>1: Columna que almacena números enteros (INT_COL_INT).
     * <LI>2: Columna que almacena números reales (INT_COL_DBL).
     * </UL>
     * <BR>Nota.- La opción predeterminada de las columnas de un JTable es INT_COL_STR.
     * @param min El valor mínimo que admiten las celdas de la columna especificada.
     * @param max El valor máximo que admiten las celdas de la columna especificada.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>min</I></TD><TD><I>max</I></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>null</TD><TD>null</TD><TD>Las celdas admiten cualquier número entero/decimal.</TD></TR>
     *     <TR><TD>null</TD><TD>valor</TD><TD>Las celdas admiten números enteros/decimales menores o iguales que "max".</TD></TR>
     *     <TR><TD>valor</TD><TD>null</TD><TD>Las celdas admiten números enteros/decimales mayores o iguales que "min".</TD></TR>
     *     <TR><TD>valor</TD><TD>valor</TD><TD>Las celdas admiten números enteros/decimales mayores o iguales que "min" y menores o iguales que "max".</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public void setColumnDataType(int col, int type, Integer min, Integer max)
    {
        ArrayList arlReg=new ArrayList(3);
        try
        {
            arlReg.add("" + type);
            arlReg.add(min);
            arlReg.add(max);
            arlTipDatCol.set(col, arlReg);
        }
        catch (IndexOutOfBoundsException e)
        {
            ;
        }
        finally
        {
            arlReg=null;
        }
    }
    
    /**
     * Esta función obtiene el tipo de dato que almacena la columna especificada.
     * @param col La columna de la que se va a obtener el tipo de dato.
     * @return El tipo de dato que almacena la columna especificada.
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>0: Columna que almacena cadenas (INT_COL_STR).
     * <LI>1: Columna que almacena números enteros (INT_COL_INT).
     * <LI>2: Columna que almacena números reales (INT_COL_DBL).
     * </UL>
     */
    public int getColumnDataType(int col)
    {
        return Integer.parseInt(((ArrayList)arlTipDatCol.get(col)).get(0).toString());
    }
    
    /**
     * Esta función obtiene el valor mínimo que pueden almacenar las celdas de la columna especificada.
     * @param col La columna de la que se va a obtener el valor mínimo.
     * @return El valor mínimo que almacena la columna especificada.
     */
    public Integer getColumnDataTypeMin(int col)
    {
        return (Integer)((ArrayList)arlTipDatCol.get(col)).get(1);
    }
    
    /**
     * Esta función obtiene el valor máximo que pueden almacenar las celdas de la columna especificada.
     * @param col La columna de la que se va a obtener el valor máximo.
     * @return El valor máximo que almacena la columna especificada.
     */
    public Integer getColumnDataTypeMax(int col)
    {
        return (Integer)((ArrayList)arlTipDatCol.get(col)).get(2);
    }
    
    /**
     * Esta función obtiene las columnas que son obligatorias en la tabla.
     * Por ejemplo: se puede tener 5 columnas de donde sólo las columnas 2 y 4 son obligatorias.
     * @return Las columnas obligatorias.
     */
    public ArrayList getColumnasObligatorias()
    {
        return arlColObl;
    }
    
    /**
     * Esta función establece las columnas que serán obligatorias en la tabla.
     * Por ejemplo: se puede tener 5 columnas de donde sólo las columnas 2 y 4 son obligatorias.
     * @param columnas El ArrayList que contiene las columnas obligatorias.
     * <BR>Nota.-Si la función recibe  <I>null</I> ninguna columna será obligatoria.
     */
    public void setColumnasObligatorias(ArrayList columnas)
    {
        if (columnas==null)
            arlColObl.clear();
        else
            arlColObl=(ArrayList)columnas.clone();
    }
    
    /**
     * Esta función determina si la tabla contiene datos válidos. Para determinar si
     * los datos son válidos se basa en las columnas obligatorias. Es decir, valida que
     * ninguna de las celdas de las columnas obligatorias esté vacía o contenga <I>null</I>.
     * @return true: Si todas las celdas de las columnas obligatorias tiene datos válidos.
     * <BR>false: En el caso contrario.
     */
    public boolean isAllRowsComplete()
    {
        int intCol, i, j;
        boolean blnRes=true;
        try
        {
            for (i=0; i<getRowCountTrue(); i++)
            {
                for (j=0; j<arlColObl.size(); j++)
                {
                    intCol=Integer.parseInt(arlColObl.get(j).toString());
                    if (getValueAt(i, intCol)==null || getValueAt(i, intCol).equals(""))
                    {
                        blnRes=false;
                        break;
                    }
                }
                //Salir del bucle si ya se encontró una fila incompleta.
                if (blnRes==false)
                    break;
            }
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el color de fondo de las filas que están incompletas en la tabla.
     * Para determinar si una fila está incompleta se evalua las columnas obligatorias. Se
     * considera que una fila está incompleta si al menos una de las celdas obligatorias está
     * vacía o contiene <I>null</I>.
     * @return El color de fondo de las filas incompletas.
     */
    public java.awt.Color getBackgroundIncompleteRows()
    {
        return colFilInc;
    }
    
    /**
     * Esta función establece el color de fondo de las filas que están incompletas en la tabla.
     * Para determinar si una fila está incompleta se evalua las columnas obligatorias. Se
     * considera que una fila está incompleta si al menos una de las celdas obligatorias está
     * vacía o contiene <I>null</I>.
     * @param color El color de fondo de las filas incompletas.
     */
    public void setBackgroundIncompleteRows(java.awt.Color color)
    {
        colFilInc=color;
    }

    /**
     * Esta función determina si la fila especificada esta completa. Se considera que
     * una fila está completa si todas las celdas obligatorias están llenas.
     * @param row La fila de la que se desea conocer si está completa.
     * @return true: Si la fila está completa.
     * <BR>false: En el caso contrario.
     */
    public boolean isRowComplete(int row)
    {
        int intCelVac, intCelOblCom, j;
        intCelVac=0;
        intCelOblCom=0;
        boolean blnRes=true;
        try
        {
            for (j=0; j<vecCabMod.size(); j++)
            {
                //No se evalua la columna cero porque es la columna del sistema donde se almacena el estado de la fila. Es decir: M=Modificada, I=Insertada.
                if (j==0)
                    intCelVac++;
                else
                {
                    if (getValueAt(row, j)==null || getValueAt(row, j).equals(""))
                        intCelVac++;
                    else
                    {
                        if (arlColObl.contains("" + j))
                            intCelOblCom++;
                    }
                }
            }
            if (intCelVac==vecCabMod.size())
                blnRes=true;
            else
                if (intCelOblCom==arlColObl.size())
                    blnRes=true;
                else
                    blnRes=false;
        }
        catch (NumberFormatException e)
        {
            blnRes=true;
        }
        return blnRes;
    }

    /**
     * Esta función determina si el modelo de datos ha cambiado. Se considera que
     * el modelo de datos ha cambiado por alguno de los siguientes casos:
     * <UL>
     * <LI>Si una de las celdas ha sido modificada,
     * <LI>Si se ha insertado una fila.
     * <LI>Si se ha eliminado una fila.
     * </UL>
     * @return true: Si el modelo de datos ha cambiado.
     * <BR>false: En el caso contrario.
     */
    public boolean isDataModelChanged()
    {
        return blnHayCam;
    }
    
    /**
     * Esta función establece si el modelo de datos ha cambiado. Se considera que
     * el modelo de datos ha cambiado por alguno de los siguientes casos:
     * <UL>
     * <LI>Si una de las celdas ha sido modificada.
     * <LI>Si se ha insertado una fila.
     * <LI>Si se ha eliminado una fila.
     * </UL>
     * @param change El estado que determina si el modelo de datos ha cambiado.
     */
    public void setDataModelChanged(boolean change)
    {
        blnHayCam=change;
    }
    
    /**
     * Esta función obtiene el indice de las columnas que se almacenarán antes de eliminar
     * una fila en la tabla. Se recomienda su uso cuando se necesite conocer algún dato de la fila(s)
     * que han sido eliminada(s).
     * @return Un ArrayList que contiene el indice de las columnas que se almacenarán antes de
     * eliminar la fila.
     */
    public ArrayList getColsSaveBeforeRemoveRow()
    {
        return arlColGuaEli;
    }
    
    /**
     * Esta función establece el indice de las columnas que se almacenarán antes de eliminar
     * una fila en la tabla. Se recomienda su uso cuando se necesite conocer algún dato de la fila(s)
     * que han sido eliminada(s).
     * @param cols El ArrayList que contiene el indice de las columnas que se almacenarán antes de
     * eliminar la fila.
     * <BR>Nota.-Si la función recibe  <I>null</I> no se almacenará ningún dato de la fila eliminada.
     */
    public void setColsSaveBeforeRemoveRow(ArrayList cols)
    {
        if (cols==null)
            arlColGuaEli.clear();
        else
            arlColGuaEli=(ArrayList)cols.clone();
    }
    
    /**
     * Esta función agrega manualmente datos al ArrayList que contiene los datos que fueron almacenados automaticamente
     * antes de eliminar una fila. Si sólo se desea eliminar los datos que fueron agregados con éste método se deberá utilizar
     * el método "clearManualDataSavedBeforeRemoveRow" ya que si se utiliza el método "clearDataSavedBeforeRemoveRow" no sólo
     * se eliminaran los datos que fueron agregados manualmente sino tambien los que fueron guardados automáticamente antes
     * de eliminar una fila.
     * @param row Un ArrayList que contiene los datos de la fila que se desea adicionar manualmente.
     */
    public void addRowDataSavedBeforeRemoveRow(ArrayList row)
    {
        arlFilGuaManEli.add("" + arlDatGuaEli.size());
        arlDatGuaEli.add(row);
    }
    
    /**
     * Esta función obtiene un ArrayList con los datos que se almacenaron automáticamente antes de eliminar una fila
     * así como los datos que se agregaron manualmente utilizando el método "addRowDataSavedBeforeRemoveRow".
     * @return Un ArrayList que contiene los datos que se almacenaron automáticamente y manualmente.
     * eliminar la fila.
     */
    public ArrayList getDataSavedBeforeRemoveRow()
    {
        return arlDatGuaEli;
    }
    
    /**
     * Esta función elimina todos los datos que se encuentran en el ArrayList.
     * Es decir, se eliminan los datos que se almacenaron automáticamente antes de eliminar una fila
     * y también los datos que se agregaron manualmente utilizando el método "addRowDataSavedBeforeRemoveRow".
     */
    public void clearDataSavedBeforeRemoveRow()
    {
        arlDatGuaEli.clear();
        arlFilGuaManEli.clear();
    }

    /**
     * Esta función remueve los datos que fueron almacenados manualmente en el ArrayList.
     * Con éste método sólo se eliminan los datos que se agregaron con el método "addRowDataSavedBeforeRemoveRow".
     */
    public void clearManualDataSavedBeforeRemoveRow()
    {
        for (int i=0; i<arlFilGuaManEli.size(); i++)
            arlDatGuaEli.remove(Integer.parseInt(arlFilGuaManEli.get(i).toString())-i);
        arlFilGuaManEli.clear();
    }

    /**
     * Esta función inicializa el estado de todas las filas. Es decir, que todas las filas
     * vuelven a su estado inicial. Esta función se debe utilizar cuando se haya realizado
     * alguna operación (actualización, eliminación, etc) ya que sino se considerará que la fila
     * continua en el estado que se encontraba antes de realizar dicha operación. Por ejemplo,
     * si realizó una actualización lo más conveniente es llamar a ésta función luego de haber hecho
     * dicha operación porque si la idea es modificar unas cuantas filas y grabar y repetir este
     * proceso hasta modificar todas las filas que se tenían que modificar. Lo recomendable es
     * que luego de grabar los cambios que se hicieron a las filas se utilice ésta función para
     * que la próxima vez que se vaya a grabar los cambios sólo se grabe las nuevas filas que han
     * sido alteradas.
     */
    public void initRowsState()
    {
        int i;
        try
        {
            //No inicializar el estado de la última fila si el modo de operación es INT_TBL_INS.
            if (intModOpe==INT_TBL_INS)
            {
                for (i=0;i<vecDatMod.size()-1;i++)
                {
                    ((Vector)vecDatMod.elementAt(i)).setElementAt(null, 0);
                }
            }
            else
            {
                for (i=0;i<vecDatMod.size();i++)
                {
                    ((Vector)vecDatMod.elementAt(i)).setElementAt(null, 0);
                }
            }
            blnHayCam=false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            ;
        }
    }
    
    
    /**
     * Esta función inicializa el estado de todas las filas. Es decir, que todas las filas
     * vuelven a su estado inicial. Esta función se debe utilizar cuando se haya realizado
     * alguna operación (actualización, eliminación, etc) ya que sino se considerará que la fila
     * continua en el estado que se encontraba antes de realizar dicha operación. Por ejemplo,
     * si realizó una actualización lo más conveniente es llamar a ésta función luego de haber hecho
     * dicha operación porque si la idea es modificar unas cuantas filas y grabar y repetir este
     * proceso hasta modificar todas las filas que se tenían que modificar. Lo recomendable es
     * que luego de grabar los cambios que se hicieron a las filas se utilice ésta función para
     * que la próxima vez que se vaya a grabar los cambios sólo se grabe las nuevas filas que han
     * sido alteradas.
     */
    public void initRowsState(int fila){
        int i=fila;
        try{
            //No inicializar el estado de la última fila si el modo de operación es INT_TBL_INS.
            if (intModOpe==INT_TBL_INS){
                if(i<(vecDatMod.size()-1)){
                    ((Vector)vecDatMod.elementAt(i)).setElementAt(null, 0);
                }
            }
            else{
                    ((Vector)vecDatMod.elementAt(i)).setElementAt(null, 0);
            }
            blnHayCam=false;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            ;
        }
    }
    
    
    
    /**
     * Esta función establece el tamaño de la tabla. Es decir, el número de filas y columnas
     * que debe tener el JTable. Cada celda del JTable se llena con un valor <I>null</I>.
     * @param rows El número de filas que contendrá el JTable.
     * @param cols El número de columnas que contendrá el JTable.
     */
    public void setSize(int rows, int cols)
    {
        Vector vecReg;
        try
        {
            vecCabMod.setSize(cols);
            for (int i=0;i<rows;i++)
            {
                vecReg=new Vector();
                vecReg.setSize(cols);
                vecDatMod.add(vecReg);
            }
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            vecCabMod.setSize(0);
        }
    }
    
    /**
     * Esta función agrega la fila(s) que se desea(n) resaltar de las demás filas de cabecera.
     * Al marcar filas con éste método el renderizador (JButton) se comporta de la siguiente manera:
     * <UL>
     * <LI>El color del texto del JButton se cambia a color rojo.
     * <LI>El JButton muestra un ToolTipText.
     * </UL>
     * @param row El indice de la fila que se desea resaltar.
     * @param toolTipText La cadena que se desea mostrar en el JButton como toolTipText.
     */
    public void addElementAtRowHeaderRaise(int row, String toolTipText)
    {
        ArrayList arlAux=new ArrayList();
        arlAux.add("" + row);
        arlAux.add(toolTipText);
        arlRowHeaDat.add(arlAux);
    }
    
    /**
     * Esta función elimina todas las filas del ArrayList que contiene las filas a resaltar.
     * Al utilizar éste método ninguna fila quedará resaltada.
     */
    public void clearRowHeaderRaise()
    {
        arlRowHeaDat.clear();
    }
    
    /**
     * Esta función obtiene el ArrayList que contiene las filas a resaltar.
     * @return Un ArrayList que contiene los datos de las filas que se resaltaron.
     */
    public ArrayList getRowHeaderRaise()
    {
        return arlRowHeaDat;
    }
    
    /**
     * Esta función obtiene el número de filas a resaltar.
     * @return El número de filas a resaltar.
     */
    public int getSizeRowHeaderRaise()
    {
        return arlRowHeaDat.size();
    }
    
    /**
     * Esta función agrega una columna al final del JTable.
     * @param tabla El JTable al que se va a agregar la columna.
     * @param columna La columna a agregar.
     * <BR>Nota.- Se recomienda utilizar el nuevo método "addColumn(Object headerValue)".
     * @see #addColumn(java.lang.Object)
     */
    public void addColumn(javax.swing.JTable tabla, javax.swing.table.TableColumn columna)
    {
        tabla.addColumn(columna);
        vecCabMod.addElement(columna.getHeaderValue());
        //Establecer que la columna agregada de manera predeterminada sea de tipo cadena.
        arlTipDatCol.add(null);
        setColumnDataType(vecCabMod.size()-1, INT_COL_STR, null, null);
    }
    
    /**
     * Esta función elimina la columna especificada del JTable.
     * @param tabla El JTable del que se va a eliminar la columna.
     * @param columna La columna a eliminar.
     * <BR>Nota.- Se recomienda utilizar el nuevo método "removeColumn(int columna)".
     * @see #removeColumn(int)
     */
    public void removeColumn(javax.swing.JTable tabla, int columna)
    {
        javax.swing.table.TableColumn tblColColEli=tabla.getColumnModel().getColumn(columna);        
        tabla.removeColumn(tblColColEli);
        vecCabMod.removeElement(tblColColEli.getHeaderValue());
    }
    
    /**
     * Esta función obtiene las columnas ocultas por el Sistema.
     * Por ejemplo: se puede tener 5 columnas en donde la columna 3 fue oculta por el Sistema.
     * @return Un ArrayList que contiene objetos del tipo <I>ZafDatCol</I>. Cada objeto ZafDatCol 
     * contiene información sobre la columna que se encuentra oculta.
     */
    public ArrayList getSystemHiddenColumns()
    {
        return arlColOcuSis;
    }
    
    /**
     * Esta función establece las columnas especificadas en el listado de columnas ocultas por el Sistema.
     * @param columnas El ArrayList que contiene las columnas a ocultar.
     * @param tabla El objeto JTable donde se ocultarán las columnas especificadas.
     * @return true: Si se pudo ocultar cada una de las columnas especificadas.
     * <BR>false: En el caso contrario.
     * <BR>Nota.- Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean setSystemHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            //Mostrar las columnas que estaban ocultas.
            removeAllSystemHiddenColumns(tabla);
            //Ocultar las columnas especificadas.
            addSystemHiddenColumns(columnas, tabla);
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve todas las columna del listado de columnas ocultas por el Sistema.
     * Es decir, hace visibles todas las columnas ocultas por el Sistema.
     * @param tabla El objeto JTable que contiene las columnas ocultas a hacer visibles.
     * @return true: Si se pudo remover todas las columnas del listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean removeAllSystemHiddenColumns(javax.swing.JTable tabla)
    {
        int i;
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            for (i=0; i<arlColOcuSis.size(); i++)
                arlAux.add("" + ((ZafDatCol)arlColOcuSis.get(i)).getColumna());
            blnRes=removeSystemHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve todas las columna del listado de columnas del modelo.
     * Es decir, hace visibles todas las columnas ocultas por el Sistema.
     * @param tabla El objeto JTable que contiene las columnas ocultas a hacer visibles.
     * @return true: Si se pudo remover todas las columnas del listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean removeAllColumns(javax.swing.JTable tabla)
    {
        int i;
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            for (i=0; i<arlColOcuSis.size(); i++)
                arlAux.add("" + ((ZafDatCol)arlColOcuSis.get(i)).getColumna());
            blnRes=removeSystemHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función agrega la columna especificada al listado de columnas ocultas por el Sistema.
     * @param columna La columna a agregar al listado de columnas ocultas por el Sistema.
     * @param tabla El objeto JTable donde se ocultará la columna especificada.
     * @return true: Si se pudo agregar la columna especificada al listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean addSystemHiddenColumn(int columna, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + columna);
            blnRes=addSystemHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve la columna especificada del listado de columnas ocultas por el Sistema.
     * @param columna La columna a remover del listado de columnas ocultas por el Sistema.
     * @param tabla El objeto JTable que contiene las columnas ocultas a hacer visibles.
     * @return true: Si se pudo remover la columna especificada del listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean removeSystemHiddenColumn(int columna, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + columna);
            blnRes=removeSystemHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función agrega las columnas especificadas al listado de columnas ocultas por el Sistema.
     * @param columnas El ArrayList que contiene las columnas a agregar al listado de columnas ocultas por el Sistema.
     * @param tabla El objeto JTable donde se ocultarán las columnas especificadas.
     * @return true: Si se pudo agregar la columna especificada al listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     * <BR>Nota.-Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean addSystemHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        int i, intColOcu, intPos;
        ZafDatCol objDatCol;
        boolean blnRes=true;
        try
        {
            if (columnas==null)
            {
                blnRes=false;
            }
            else
            {
                javax.swing.table.TableColumn tbcAux;
                //Ocultar las columnas especificadas.
                for (i=0; i<columnas.size(); i++)
                {
                    intColOcu=Integer.parseInt(columnas.get(i).toString());
                    //Determinar si la columna especificada ya está oculta.
                    intPos=getPosColBus(intColOcu, 0);
                    //Adicionar la columna sólo si no se encuentra oculta.
                    if (intPos==-1)
                    {
                        tbcAux=tabla.getColumnModel().getColumn(intColOcu);
                        //Almacenar los valores antes de ocultar la columna.
                        objDatCol=new ZafDatCol();
                        objDatCol.setColumna(intColOcu);
                        objDatCol.setAnchoMinimo(tbcAux.getMinWidth());
                        objDatCol.setAnchoMaximo(tbcAux.getMaxWidth());
                        objDatCol.setAnchoPreferido(tbcAux.getPreferredWidth());
                        objDatCol.setAncho(tbcAux.getWidth());
                        //Ocultar la columna.
                        tbcAux.setMinWidth(0);
                        tbcAux.setMaxWidth(0);
                        tbcAux.setPreferredWidth(0);
                        tbcAux.setWidth(0);
                        tbcAux.setResizable(false);
                        //Adicionar al ArrayList la columna que se ocultó.
                        arlColOcuSis.add(objDatCol);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve las columnas especificadas del listado de columnas ocultas por el Sistema.
     * @param columnas El ArrayList que contiene las columnas a remover del listado de columnas ocultas por el Sistema.
     * @param tabla El objeto JTable que contiene las columnas coultas a hacer visibles.
     * @return true: Si se pudo remover la columna especificada del listado de columnas ocultas por el Sistema.
     * <BR>false: En el caso contrario.
     * <BR>Nota.-Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean removeSystemHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        int i, j, intCol, intPos;
        ZafDatCol objDatCol;
        boolean blnRes=true;
        try
        {
            if (columnas==null)
            {
                blnRes=false;
            }
            else
            {
                javax.swing.table.TableColumn tbcAux;
                //Mostrar las columnas que estaban ocultas.
                for (j=0; j<columnas.size(); j++)
                {
                    intCol=Integer.parseInt(columnas.get(j).toString());
                    //Buscar la posición en la que se encuentra la columna oculta a mostrar.
                    intPos=getPosColBus(intCol, 0);
                    if (intPos!=-1)
                    {
                        objDatCol=(ZafDatCol)arlColOcuSis.get(intPos);
                        tbcAux=tabla.getColumnModel().getColumn(intCol);
                        tbcAux.setMinWidth(objDatCol.getAnchoMinimo());
                        tbcAux.setMaxWidth(objDatCol.getAnchoMaximo());
                        tbcAux.setPreferredWidth(objDatCol.getAnchoPreferido());
                        tbcAux.setWidth(objDatCol.getAncho());
                        tbcAux.setResizable(true);
                        //Remover del ArrayList la columna oculta que se removió.
                        arlColOcuSis.remove(intPos);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }

    /**
     * Esta función determina si la columna especificada es una de las columnas ocultas por el Sistema.
     * @param columna La columna que se saber si fue oculta por el Sistema.
     * @return true: Si la columna fue oculta por el Sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean isSystemHiddenColumn(int columna)
    {
        boolean blnRes=true;
        try
        {
            blnRes=(getPosColBus(columna, 0)==-1?false:true);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }

    /**
     * Esta función obtiene la posición en la que se encuentra la columna que se busca.
     * @param columna La columna oculta a buscar.
     * @param intOpc La opción que determina donde buscar.
     * <BR>Los posibles valores son los siguientes:
     * <UL>
     * <LI>0: Buscar la columna especificada en el ArrayList de columnas ocultas por el Sistema.
     * <LI>1: Buscar la columna especificada en el ArrayList de columnas ocultas por el Usuario.
     * </UL>
     * @return La posición en la que fue encontrada la columna 
     * <BR>Nota.- La función devolverá <I>-1</I> en los siguientes casos:
     * <UL>
     * <LI>No se encontró la columna.
     * <LI>Ocurrió una excepción.
     * </UL>
     */
    private int getPosColBus(int columna, int intOpc)
    {
        int i;
        int intRes=-1;
        try
        {
            switch (intOpc)
            {
                case 0: //Columnas ocultas por el Sistema.
                    //Buscar la posición en la que se encuentra la columna oculta a mostrar.
                    for (i=0; i<arlColOcuSis.size(); i++)
                    {
                        if (((ZafDatCol)arlColOcuSis.get(i)).getColumna()==columna)
                        {
                            intRes=i;
                            break;
                        }
                    }
                    break;
                case 1: //Columnas ocultas por el Usuario.
                    //Buscar la posición en la que se encuentra la columna oculta a mostrar.
                    for (i=0; i<arlColOcuUsr.size(); i++)
                    {
                        if (((ZafDatCol)arlColOcuUsr.get(i)).getColumna()==columna)
                        {
                            intRes=i;
                            break;
                        }
                    }
                    break;
            }
        }
        catch(Exception e)
        {
            intRes=-1;
        }
        return intRes;
    }
    
    /**
     * Esta función obtiene las columnas ocultas por el Usuario.
     * Por ejemplo: se puede tener 5 columnas en donde la columna 3 fue oculta por el Usuario.
     * @return Un ArrayList que contiene objetos del tipo <I>ZafDatCol</I>. Cada objeto ZafDatCol 
     * contiene información sobre la columna que se encuentra oculta.
     */
    public ArrayList getUserHiddenColumns()
    {
        return arlColOcuUsr;
    }
    
    /**
     * Esta función establece las columnas especificadas en el listado de columnas ocultas por el Usuario.
     * @param columnas El ArrayList que contiene las columnas a ocultar.
     * @param tabla El objeto JTable donde se ocultarán las columnas especificadas.
     * @return true: Si se pudo ocultar cada una de las columnas especificadas.
     * <BR>false: En el caso contrario.
     * <BR>Nota.- Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean setUserHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            //Mostrar las columnas que estaban ocultas.
            removeAllUserHiddenColumns(tabla);
            //Ocultar las columnas especificadas.
            addUserHiddenColumns(columnas, tabla);
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve todas las columna del listado de columnas ocultas por el Usuario.
     * Es decir, hace visibles todas las columnas ocultas por el Usuario.
     * @param tabla El objeto JTable que contiene las columnas ocultas a hacer visibles.
     * @return true: Si se pudo remover todas las columnas del listado de columnas ocultas por el Usuario.
     * <BR>false: En el caso contrario.
     */
    public boolean removeAllUserHiddenColumns(javax.swing.JTable tabla)
    {
        int i;
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            for (i=0; i<arlColOcuUsr.size(); i++)
                arlAux.add("" + ((ZafDatCol)arlColOcuUsr.get(i)).getColumna());
            blnRes=removeUserHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función agrega la columna especificada al listado de columnas ocultas por el Usuario.
     * @param columna La columna a agregar al listado de columnas ocultas por el Usuario.
     * @param tabla El objeto JTable donde se ocultará la columna especificada.
     * @return true: Si se pudo agregar la columna especificada al listado de columnas ocultas por el Usuario.
     * <BR>false: En el caso contrario.
     */
    public boolean addUserHiddenColumn(int columna, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + columna);
            blnRes=addUserHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve la columna especificada del listado de columnas ocultas por el Usuario.
     * @param columna La columna a remover del listado de columnas ocultas por el Usuario.
     * @param tabla El objeto JTable que contiene las columnas ocultas a hacer visibles.
     * @return true: Si se pudo remover la columna especificada del listado de columnas ocultas por el Usuario.
     * <BR>false: En el caso contrario.
     */
    public boolean removeUserHiddenColumn(int columna, javax.swing.JTable tabla)
    {
        boolean blnRes=true;
        try
        {
            ArrayList arlAux=new ArrayList();
            arlAux.add("" + columna);
            blnRes=removeUserHiddenColumns(arlAux, tabla);
            arlAux=null;
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función agrega las columnas especificadas al listado de columnas ocultas por el Usuario.
     * @param columnas El ArrayList que contiene las columnas a agregar al listado de columnas ocultas por el Usuario.
     * @param tabla El objeto JTable donde se ocultarán las columnas especificadas.
     * @return true: Si se pudo agregar la columna especificada al listado de columnas ocultas por el Usuario.
     * <BR>false: En el caso contrario.
     * <BR>Nota.-Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean addUserHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        int i, intColOcu, intPos;
        ZafDatCol objDatCol;
        boolean blnRes=true;
        try
        {
            if (columnas==null)
            {
                blnRes=false;
            }
            else
            {
                javax.swing.table.TableColumn tbcAux;
                //Ocultar las columnas especificadas.
                for (i=0; i<columnas.size(); i++)
                {
                    intColOcu=Integer.parseInt(columnas.get(i).toString());
                    //Determinar si la columna especificada ya está oculta.
                    intPos=getPosColBus(intColOcu, 0);
                    //Adicionar la columna sólo si no se encuentra oculta.
                    if (intPos==-1)
                    {
                        tbcAux=tabla.getColumnModel().getColumn(intColOcu);
                        //Almacenar los valores antes de ocultar la columna.
                        objDatCol=new ZafDatCol();
                        objDatCol.setColumna(intColOcu);
                        objDatCol.setAnchoMinimo(tbcAux.getMinWidth());
                        objDatCol.setAnchoMaximo(tbcAux.getMaxWidth());
                        objDatCol.setAnchoPreferido(tbcAux.getPreferredWidth());
                        objDatCol.setAncho(tbcAux.getWidth());
                        //Ocultar la columna.
                        tbcAux.setMinWidth(0);
                        tbcAux.setMaxWidth(0);
                        tbcAux.setPreferredWidth(0);
                        tbcAux.setWidth(0);
                        tbcAux.setResizable(false);
                        //Adicionar al ArrayList la columna que se ocultó.
                        arlColOcuUsr.add(objDatCol);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }
    
    /**
     * Esta función remueve las columnas especificadas del listado de columnas ocultas por el Usuario.
     * @param columnas El ArrayList que contiene las columnas a remover del listado de columnas ocultas por el Usuario.
     * @param tabla El objeto JTable que contiene las columnas coultas a hacer visibles.
     * @return true: Si se pudo remover la columna especificada del listado de columnas ocultas por el Usuario.
     * <BR>false: En el caso contrario.
     * <BR>Nota.-Si alguno de los parámetros recibidos es <I>null</I> la función devolverá <I>false</I>.
     */
    public boolean removeUserHiddenColumns(ArrayList columnas, javax.swing.JTable tabla)
    {
        int i, j, intCol, intPos;
        ZafDatCol objDatCol;
        boolean blnRes=true;
        try
        {
            if (columnas==null)
            {
                blnRes=false;
            }
            else
            {
                javax.swing.table.TableColumn tbcAux;
                //Mostrar las columnas que estaban ocultas.
                for (j=0; j<columnas.size(); j++)
                {
                    intCol=Integer.parseInt(columnas.get(j).toString());
                    //Buscar la posición en la que se encuentra la columna oculta a mostrar.
                    intPos=getPosColBus(intCol, 0);
                    if (intPos!=-1)
                    {
                        objDatCol=(ZafDatCol)arlColOcuUsr.get(intPos);
                        tbcAux=tabla.getColumnModel().getColumn(intCol);
                        tbcAux.setMinWidth(objDatCol.getAnchoMinimo());
                        tbcAux.setMaxWidth(objDatCol.getAnchoMaximo());
                        tbcAux.setPreferredWidth(objDatCol.getAnchoPreferido());
                        tbcAux.setWidth(objDatCol.getAncho());
                        tbcAux.setResizable(true);
                        //Remover del ArrayList la columna oculta que se removió.
                        arlColOcuUsr.remove(intPos);
                    }
                }
            }
        }
        catch(Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }

    /**
     * Esta función determina si la columna especificada es una de las columnas ocultas por el Usuario.
     * @param columna La columna que se saber si fue oculta por el Usuario.
     * @return true: Si la columna fue oculta por el Usuario.
     * <BR>false: En el caso contrario.
     */
    public boolean isUserHiddenColumn(int columna)
    {
        boolean blnRes=true;
        try
        {
            blnRes=(getPosColBus(columna, 1)==-1?false:true);
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println("Excepción: " + e.toString());
        }
        return blnRes;
    }

    /**
     * Esta función agrega más columnas editables a la tabla.
     * @param columnas El vector que contiene las columnas editables a agregar.
     */
    public void addColumnasEditables(Vector columnas)
    {
        int i;
        try
        {
            if (columnas!=null)
            {
                for (i=0; i<columnas.size(); i++)
                {
                    vecColEdi.addElement(columnas.get(i));
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excepción: " + e.toString());
        }
    }
    
    /**
     * Esta función establece el valor que debe quedar seleccionado a través del combobox.
     * Se utiliza cuando el editor de la celda es un combo box.
     * @param value Valor String que determina el valor con el que quedará la celda.
     * @param row La fila de la celda que se desea cambiar valor.
     * @param col La columna de la celda que se desea cambiar valor.
     */
    public void setSelectedItem(String value, int row, int col){
        setValueAt(new String(value), row, col);
    }

    /**
     * Esta función determina si el valor de la celda especificada es igual a la cadena recibida.
     * Los valores nulos son considerados como si fueran cadenas vacías.
     * @param intRow La fila de la celda de la que se desea conocer si es igual a la cadena recibida.
     * @param intCol La columna de la celda de la que se desea conocer si es igual a la cadena recibida.
     * @param strVal La cadena que se va a comparar con la celda especificada.
     * @return true: Si la cadena recibida es igual al valor de la celda especificada.
     * <BR>false: En el caso contrario.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Celda</I></TD><TD><I>Cadena</I></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>null</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>null</TD><TD>""</TD><TD>true</TD></TR>
     *     <TR><TD>null</TD><TD>"Hola"</TD><TD>false</TD></TR>
     *     <TR><TD>""</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>""</TD><TD>""</TD><TD>true</TD></TR>
     *     <TR><TD>""</TD><TD>"Hola"</TD><TD>false</TD></TR>
     *     <TR><TD>"Hola"</TD><TD>null</TD><TD>false</TD></TR>
     *     <TR><TD>"Hola"</TD><TD>""</TD><TD>false</TD></TR>
     *     <TR><TD>"Hola"</TD><TD>"Hola"</TD><TD>true</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public boolean isValueAtEqualToString(int intRow, int intCol, String strVal)
    {
        Object objAux;
        boolean blnRes=true;
        try
        {
            objAux=getValueAt(intRow, intCol);
            if (objAux==null)
            {
                if (strVal==null)
                {
                    blnRes=true;
                }
                else
                {
                    if (strVal.equals(""))
                        blnRes=true;
                    else
                        blnRes=false;
                }
            }
            else
            {
                if (objAux.toString().equals(""))
                {
                    if (strVal==null)
                    {
                        blnRes=true;
                    }
                    else
                    {
                        if (objAux.toString().equals(""))
                        {
                            blnRes=true;
                        }
                        else
                        {
                            blnRes=false;
                        }
                    }
                }
                else
                {
                    if (objAux.toString().equals(strVal))
                        blnRes=true;
                    else
                        blnRes=false;
                }
            }
            objAux=null;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función determina si el valor de la celda especificada es igual al BigDecimal recibido.
     * Los valores nulos son considerados como si fueran cero.
     * @param intRow La fila de la celda de la que se desea conocer si es igual al BigDecimal recibido.
     * @param intCol La columna de la celda de la que se desea conocer si es igual al BigDecimal recibido.
     * @param bgdVal El BigDecimal que se va a comparar con la celda especificada.
     * @return true: Si el BigDecimal recibido es igual al valor de la celda especificada.
     * <BR>false: En el caso contrario.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Celda</I></TD><TD><I>BigDecimal</I></TD><TD><I>Conclusión</I></TD></TR>
     *     <TR><TD>null</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>null</TD><TD>0</TD><TD>true</TD></TR>
     *     <TR><TD>null</TD><TD>3.14</TD><TD>false</TD></TR>
     *     <TR><TD>0</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>0</TD><TD>0</TD><TD>true</TD></TR>
     *     <TR><TD>0</TD><TD>3.14</TD><TD>false</TD></TR>
     *     <TR><TD>3.14</TD><TD>null</TD><TD>false</TD></TR>
     *     <TR><TD>3.14</TD><TD>0</TD><TD>false</TD></TR>
     *     <TR><TD>3.14</TD><TD>3.14</TD><TD>true</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public boolean isValueAtEqualToBigDecimal(int intRow, int intCol, java.math.BigDecimal bgdVal)
    {
        java.math.BigDecimal bgdAux;
        boolean blnRes=true;
        try
        {
            bgdAux=objUti.getBigDecimalValueAt(vecDatMod, intRow, intCol);
            if (bgdAux==null)
            {
                if (bgdVal==null)
                {
                    blnRes=true;
                }
                else
                {
                    if (bgdVal.compareTo(java.math.BigDecimal.ZERO)==0)
                        blnRes=true;
                    else
                        blnRes=false;
                }
            }
            else
            {
                if (bgdAux.compareTo(java.math.BigDecimal.ZERO)==0)
                {
                    if (bgdVal==null)
                    {
                        blnRes=true;
                    }
                    else
                    {
                        if (bgdAux.compareTo(java.math.BigDecimal.ZERO)==0)
                        {
                            blnRes=true;
                        }
                        else
                        {
                            blnRes=false;
                        }
                    }
                }
                else
                {
                    if (bgdAux.compareTo(bgdVal)==0)
                        blnRes=true;
                    else
                        blnRes=false;
                }
            }
            bgdAux=null;
        }
        catch (ArrayIndexOutOfBoundsException e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función determina si está marcada alguna fila.
     * @param col La columna en la cual se desea saber si hay alguna fila marcada.
     * @return true: Si hay alguna fila marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckedAnyRow(int col)
    {
        int i, intNumTotFil;
        boolean blnRes=false;
        try
        {
            intNumTotFil=getRowCountTrue();
            for (i=0; i<intNumTotFil; i++)
            {
                if (isChecked(i, col))
                {
                    blnRes=true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
        
    /**
     * Esta función permite determinar si la fila especificada tiene marcada alguna columna.
     * @param row La fila en la cual se desea saber si hay alguna columna marcada.
     * @return true: Si hay alguna columna marcada.
     * <BR>false: En el caso contrario.
     */
    public boolean isCheckedAnyColumn(int row)
    {
        int i, intNumTotCol;
        boolean blnRes=false;
        try
        {
            intNumTotCol=getColumnCount();
            for (i=0; i<intNumTotCol; i++)
            {
                if (isChecked(row, i))
                {
                    blnRes=true;
                    break;
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función obtiene el número total de filas que se encuentran marcadas.
     * @param col La columna en la cual se desea saber las filas marcadas.
     * @return El número total de filas marcadas.
     */
    public int getRowCountChecked(int col)
    {
        int i, intNumTotFil;
        int intRes=0;
        try
        {
            intNumTotFil=getRowCountTrue();
            for (i=0; i<intNumTotFil; i++)
            {
                if (isChecked(i, col))
                {
                    intRes++;
                }
            }
        }
        catch (Exception e)
        {
            intRes=0;
        }
        return intRes;
    }

    /**
     * Esta función obtiene el máximo número de filas que puede tener la tabla.
     * Ejemplo: Si max=5 quiere decir que en la tabla sólo se permitirá insertar hasta 5 filas.
     * Cuando se trate de insertar la sexta fila aparecerá un mensaje indicando que no es posible
     * insertar más de 5 filas.
     * @return El máximo número de filas que admitirá la tabla.
     * <BR>Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI><I>null</I>: Indica que la tabla no tiene límite de filas. Es decir, se pueden insertar todas las filas que se deseen.
     * <LI>Un valor diferente a <I>null</I>: Indica que la tabla si tiene límite de filas. Es decir, sólo se pueden insertar el número de filas especificado.
     * </UL>
     */
    public Integer getMaxRowsAllowed()
    {
        return objIntMaxNumFilPer;
    }

    /**
     * Esta función establece el máximo número de filas que puede tener la tabla.
     * Cuando se asigna el máximo número de filas permitidas se valida que las filas que tiene actualmente la tabla
     * no sea mayor al máximo que se desea establecer. Si llegara a ser mayor el Sistema preguntará al usuario si desea
     * continuar. En cuyo caso se eliminarán las filas que están de más. De aquí en adelante dicho máximo será utilizado
     * para validar que no se inserten más filas de las permitidas.
     * @param max El máximo número de filas que admitirá la tabla.
     * <BR>Los posibles valores son los siguientes:
     * <UL>
     * <LI><I>null</I>: La tabla no tiene límite de filas. Es decir, se pueden insertar todas las filas que se deseen.
     * <LI>Un valor diferente a <I>null</I>: La tabla si tiene límite de filas. Es decir, sólo se pueden insertar el número de filas establecido.
     * </UL>
     * @return true: Si se estableció el máximo número de filas permitidas.
     * <BR>false: En el caso contrario.
     * @see #setMaxRowsAllowed(java.lang.Integer, boolean)
     */
    public boolean setMaxRowsAllowed(Integer max)
    {
        return setMaxRowsAllowed(max, true);
    }

    /**
     * Esta función establece el máximo número de filas que puede tener la tabla.
     * Cuando se asigna el máximo número de filas permitidas se valida que las filas que tiene actualmente la tabla
     * no sea mayor al máximo que se desea establecer. Si llegara a ser mayor el Sistema preguntará al usuario si desea
     * continuar. En cuyo caso se eliminarán las filas que están de más. De aquí en adelante dicho máximo será utilizado
     * para validar que no se inserten más filas de las permitidas.
     * @param max El máximo número de filas que admitirá la tabla.
     * <BR>Los posibles valores son los siguientes:
     * <UL>
     * <LI><I>null</I>: La tabla no tiene límite de filas. Es decir, se pueden insertar todas las filas que se deseen.
     * <LI>Un valor diferente a <I>null</I>: La tabla si tiene límite de filas. Es decir, sólo se pueden insertar el número de filas establecido.
     * </UL>
     * @param validarFilas Valor booleano que determina si al momento de asignar el máximo se debe validar las filas que tiene actualmente la tabla.
     * <BR>Los posibles valores son los siguientes:
     * <UL>
     * <LI>true: Si se debe validar las filas que tiene actualmente la tabla.
     * <LI>false: No se debe validar las filas que tiene actualmente la tabla.
     * </UL>
     * Nota.- Asignar "false" sólo afecta a ésta función. Es decir, si más adelante se desea insertar una fila el Sistema validará que no se sobrepase
     * el máximo número de filas establecido.
     * @return true: Si se estableció el máximo número de filas permitidas.
     * <BR>false: En el caso contrario.
     */
    public boolean setMaxRowsAllowed(Integer max, boolean validarFilas)
    {
        boolean blnRes=true;
        try
        {
            if (validarFilas)
            {
                if (max==null)
                {
                    objIntMaxNumFilPer=max;
                }
                else
                {
                    //Validar si hay más filas de las permitidas.
                    if ((vecDatMod.size() - (intModOpe==INT_TBL_INS?1:0))>max.intValue())
                    {
                        if (javax.swing.JOptionPane.YES_OPTION==javax.swing.JOptionPane.showConfirmDialog(null, "Hay más filas de las permitidas.\nLa tabla está configurada para trabajar con un máximo de " + max.intValue() + " filas.\nSi continua se eliminarán las filas que estan de más.\n¿Está seguro que desea continuar?", "Mensaje del sistema Zafiro", javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE))
                        {
                            this.setRowCount(max.intValue());
                            //Insertar una fila al final sólo si el modo de operación es INT_TBL_INS.
                            if (intModOpe==INT_TBL_INS)
                                insertRow();
                            objIntMaxNumFilPer=max;
                        }
                        else
                        {
                            return false;
                        }
                    }
                    else
                        objIntMaxNumFilPer=max;
                }
            }
            else
            {
                objIntMaxNumFilPer=max;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función compara las celdas especificadas.
     * <BR>Los valores <I>null</I> son considerados como una cadena vacía.
     * <BR>No existe diferencia entre mayúsculas y minúsculas.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>&nbsp;</I><TD><I>Celda1</I></TD><TD><I>Celda2</I></TD><TD><I>Resultado</I></TD></TR>
     *     <TR><TD>Caso1</TD><TD>null</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>Caso3</TD><TD>null</TD><TD>""</TD><TD>true</TD></TR>
     *     <TR><TD>Caso4</TD><TD>null</TD><TD>"hola"</TD><TD>false</TD></TR>
     *     <TR><TD>Caso7</TD><TD>""</TD><TD>null</TD><TD>true</TD></TR>
     *     <TR><TD>Caso8</TD><TD>"hola"</TD><TD>null</TD><TD>false</TD></TR>
     *     <TR><TD>Caso14</TD><TD>"hola"</TD><TD>"hola"</TD><TD>true</TD></TR>
     *     <TR><TD>Caso15</TD><TD>"hola"</TD><TD>"Hola"</TD><TD>true</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param row1 La fila de la celda que se quiere comparar.
     * @param col1 La columna de la celda que se quiere comparar.
     * @param row2 La fila de la celda con la que se quiere comparar.
     * @param col2 La columna de la celda con la que se quiere comparar.
     * @return true: Si las celdas son iguales.
     * <BR>false: En el caso contrario.
     */
    public boolean compareStringCells(int row1, int col1, int row2, int col2)
    {
        return compareStringCells(row1, col1, row2, col2, true, false);
    }

    /**
     * Esta función compara las celdas especificadas.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>&nbsp;</I></TD><TD><I>Celda1</I></TD><TD><I>Celda2</I></TD><TD><I>nullAsEmptyString</I></TD><TD><I>caseSensitive</I></TD><TD><I>Resultado</I></TD></TR>
     *     <TR><TD>Caso1</TD><TD>null</TD><TD>null</TD><TD>true</TD><TD>true/false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso2</TD><TD>null</TD><TD>null</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso3</TD><TD>null</TD><TD>""</TD><TD>true</TD><TD>true/false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso4</TD><TD>null</TD><TD>"hola"</TD><TD>true</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso5</TD><TD>null</TD><TD>""</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso6</TD><TD>null</TD><TD>"hola"</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso7</TD><TD>""</TD><TD>null</TD><TD>true</TD><TD>true/false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso8</TD><TD>"hola"</TD><TD>null</TD><TD>true</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso9</TD><TD>""</TD><TD>null</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso10</TD><TD>"hola"</TD><TD>null</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso11</TD><TD>"hola"</TD><TD>"hola"</TD><TD>true/false</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso12</TD><TD>"hola"</TD><TD>"Hola"</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso13</TD><TD>"hola"</TD><TD>"adios"</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso14</TD><TD>"hola"</TD><TD>"hola"</TD><TD>true/false</TD><TD>false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso15</TD><TD>"hola"</TD><TD>"Hola"</TD><TD>true/false</TD><TD>false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso16</TD><TD>"hola"</TD><TD>"adios"</TD><TD>true/false</TD><TD>false</TD><TD>false</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param row1 La fila de la celda que se quiere comparar.
     * @param col1 La columna de la celda que se quiere comparar.
     * @param row2 La fila de la celda con la que se quiere comparar.
     * @param col2 La columna de la celda con la que se quiere comparar.
     * @param nullAsEmptyString Determina si los valores null deben ser considerados como una cadena vacía.
     * @param caseSensitive Determina si se debe diferenciar entre mayúsculas y minúsculas.
     * @return true: Si las celdas son iguales.
     * <BR>false: En el caso contrario.
     */
    public boolean compareStringCells(int row1, int col1, int row2, int col2, boolean nullAsEmptyString, boolean caseSensitive)
    {
        Object objValCel1, objValCel2;
        String strValCel1, strValCel2;
        boolean blnRes=false;
        try
        {
            objValCel1=getValueAt(row1, col1);
            objValCel2=getValueAt(row2, col2);
            if (objValCel1==null)
            {
                if (objValCel2==null)
                {
                    if (nullAsEmptyString)
                        blnRes=true; //Caso 1 (OK)
                    else
                        blnRes=false; //Caso 2
                }
                else
                {
                    if (nullAsEmptyString)
                    {
                        strValCel1="";
                        strValCel2=objValCel2.toString();
                        if (strValCel1.equals(strValCel2))
                            blnRes=true; //Caso 3 (OK)
                        else
                            blnRes=false; //Caso 4 (OK)
                    }
                    else
                    {
                        blnRes=false; //Caso 5 y 6
                    }
                }
            }
            else
            {
                if (objValCel2==null)
                {
                    if (nullAsEmptyString)
                    {
                        strValCel1=objValCel1.toString();
                        strValCel2="";
                        if (strValCel1.equals(strValCel2))
                            blnRes=true; //Caso 7 (OK)
                        else
                            blnRes=false; //Caso 8 (OK)
                    }
                    else
                    {
                        blnRes=false; //Caso 9 y 10
                    }
                }
                else
                {
                    strValCel1=objValCel1.toString();
                    strValCel2=objValCel2.toString();
                    if (caseSensitive)
                    {
                        if (strValCel1.equals(strValCel2))
                            blnRes=true; //Caso 11
                        else
                            blnRes=false; //Caso 12 y 13
                    }
                    else
                    {
                        if (strValCel1.equalsIgnoreCase(strValCel2))
                            blnRes=true; //Caso 14 y 15 (OK)
                        else
                            blnRes=false; //Caso 16
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función compara los valores BigDecimal especificados.
     * @param bgdVal1 El valor BigDecimal que se quiere comparar.
     * @param bgdVal2 El valor BigDecimal con el que se quiere comparar.
     * @param intOpe El operador que se utilizará para la comparación.
     * <UL>
     * <LI>0: Igual (INT_COM_NUM_IGU).
     * <LI>1: Diferente (INT_COM_NUM_DIF).
     * <LI>2: Mayor que (INT_COM_NUM_MAY).
     * <LI>3: Mayor o igual que (INT_COM_NUM_MAY_IGU).
     * <LI>4: Menor que (INT_COM_NUM_MEN).
     * <LI>5: Menor o igual que (INT_COM_NUM_MEN_IGU).
     * </UL>
     * @return true: Si la comparación es verdadera.
     * <BR>false: En el caso contrario.
     */
    private boolean compararValBgd(java.math.BigDecimal bgdVal1, java.math.BigDecimal bgdVal2, int intOpe)
    {
        boolean blnRes=false;
        try
        {
            switch (intOpe)
            {
                case INT_COM_NUM_IGU: //0: Igual (INT_COM_NUM_IGU)
                    if (bgdVal1.compareTo(bgdVal2)==0)
                        blnRes=true;
                    break;
                case INT_COM_NUM_DIF: //1: Diferente (INT_COM_NUM_DIF)
                    if (bgdVal1.compareTo(bgdVal2)!=0)
                        blnRes=true;
                    break;
                case INT_COM_NUM_MAY: //2: Mayor que (INT_COM_NUM_MAY)
                    if (bgdVal1.compareTo(bgdVal2)>0)
                        blnRes=true;
                    break;
                case INT_COM_NUM_MAY_IGU: //3: Mayor o igual que (INT_COM_NUM_MAY_IGU)
                    if (bgdVal1.compareTo(bgdVal2)>=0)
                        blnRes=true;
                    break;
                case INT_COM_NUM_MEN: //4: Menor que (INT_COM_NUM_MEN)
                    if (bgdVal1.compareTo(bgdVal2)<0)
                        blnRes=true;
                    break;
                case INT_COM_NUM_MEN_IGU: //5: Menor o igual que (INT_COM_NUM_MEN_IGU)
                    if (bgdVal1.compareTo(bgdVal2)<=0)
                        blnRes=true;
                    break;
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función compara las celdas especificadas.
     * <BR>Los valores <I>null</I> y las cadenas vacías son considerados como cero.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>&nbsp;</I></TD><TD><I>Celda1</I></TD><TD><I>Celda2</I></TD><TD><I>Operador</I></TD><TD><I>Resultado</I></TD></TR>
     *     <TR><TD>Caso1</TD><TD>null</TD><TD>null</TD><TD>=</TD><TD>true</TD></TR>
     *     <TR><TD>Caso3</TD><TD>null</TD><TD>""</TD><TD>=</TD><TD>true</TD></TR>
     *     <TR><TD>Caso4</TD><TD>null</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso5</TD><TD>null</TD><TD>50</TD><TD>=</TD><TD>false</TD></TR>
     *     <TR><TD>Caso6</TD><TD>null</TD><TD>50</TD><TD>&lt;</TD><TD>true</TD></TR>
     *     <TR><TD>Caso14</TD><TD>""</TD><TD>null</TD><TD>=</TD><TD>true</TD></TR>
     *     <TR><TD>Caso15</TD><TD>""</TD><TD>null</TD><TD>&lt;&gt;</TD><TD>false</TD></TR>
     *     <TR><TD>Caso16</TD><TD>"hola"</TD><TD>null</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso17</TD><TD>50</TD><TD>null</TD><TD>=</TD><TD>false</TD></TR>
     *     <TR><TD>Caso18</TD><TD>50</TD><TD>null</TD><TD>&gt;</TD><TD>true</TD></TR>
     *     <TR><TD>Caso27</TD><TD>""</TD><TD>""</TD><TD>=</TD><TD>true</TD></TR>
     *     <TR><TD>Caso28</TD><TD>""</TD><TD>""</TD><TD>&lt;&gt;</TD><TD>false</TD></TR>
     *     <TR><TD>Caso30</TD><TD>""</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso31</TD><TD>""</TD><TD>50</TD><TD>=</TD><TD>false</TD></TR>
     *     <TR><TD>Caso32</TD><TD>""</TD><TD>50</TD><TD>&lt;</TD><TD>true</TD></TR>
     *     <TR><TD>Caso35</TD><TD>"hola"</TD><TD>""</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso36</TD><TD>50</TD><TD>""</TD><TD>=</TD><TD>false</TD></TR>
     *     <TR><TD>Caso37</TD><TD>50</TD><TD>""</TD><TD>&gt;</TD><TD>true</TD></TR>
     *     <TR><TD>Caso39</TD><TD>"hola"</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso40</TD><TD>"hola"</TD><TD>50</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso41</TD><TD>50</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>false</TD></TR>
     *     <TR><TD>Caso42</TD><TD>50</TD><TD>50</TD><TD>=</TD><TD>true</TD></TR>
     *     <TR><TD>Caso43</TD><TD>50</TD><TD>10</TD><TD>&lt;</TD><TD>false</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param row1 La fila de la celda que se quiere comparar.
     * @param col1 La columna de la celda que se quiere comparar.
     * @param row2 La fila de la celda con la que se quiere comparar.
     * @param col2 La columna de la celda con la que se quiere comparar.
     * @param operador El operador que se utilizará para la comparación.
     * <UL>
     * <LI>0: Igual (INT_COM_NUM_IGU).
     * <LI>1: Diferente (INT_COM_NUM_DIF).
     * <LI>2: Mayor que (INT_COM_NUM_MAY).
     * <LI>3: Mayor o igual que (INT_COM_NUM_MAY_IGU).
     * <LI>4: Menor que (INT_COM_NUM_MEN).
     * <LI>5: Menor o igual que (INT_COM_NUM_MEN_IGU).
     * </UL>
     * @return true: Si la comparación es verdadera.
     * <BR>false: En el caso contrario.
     */
    public boolean compareNumericCells(int row1, int col1, int row2, int col2, int operador)
    {
        return compareNumericCells(row1, col1, row2, col2, operador, true, true);
    }

    /**
     * Esta función compara las celdas especificadas.
     * <BR>Se pueden presentar los siguientes casos:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>&nbsp;</I></TD><TD><I>Celda1</I></TD><TD><I>Celda2</I></TD><TD><I>Operador</I></TD><TD><I>nullAsZero</I></TD><TD><I>emptyStringAsZero</I></TD><TD><I>Resultado</I></TD></TR>
     *     <TR><TD>Caso1</TD><TD>null</TD><TD>null</TD><TD>=</TD><TD>true</TD><TD>true/false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso2</TD><TD>null</TD><TD>null</TD><TD>=</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso3</TD><TD>null</TD><TD>""</TD><TD>=</TD><TD>true</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso4</TD><TD>null</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso5</TD><TD>null</TD><TD>50</TD><TD>=</TD><TD>true</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso6</TD><TD>null</TD><TD>50</TD><TD>&lt;</TD><TD>true</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso7</TD><TD>null</TD><TD>""</TD><TD>Cualquiera</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso8</TD><TD>null</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso9</TD><TD>null</TD><TD>50</TD><TD>=</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso10</TD><TD>null</TD><TD>50</TD><TD>&lt;</TD><TD>true</TD><TD>false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso11</TD><TD>null</TD><TD>""</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso12</TD><TD>null</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso13</TD><TD>null</TD><TD>50</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso14</TD><TD>""</TD><TD>null</TD><TD>=</TD><TD>true</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso15</TD><TD>""</TD><TD>null</TD><TD>&lt;&gt;</TD><TD>true</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso16</TD><TD>"hola"</TD><TD>null</TD><TD>Cualquiera</TD><TD>true</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso17</TD><TD>50</TD><TD>null</TD><TD>=</TD><TD>true</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso18</TD><TD>50</TD><TD>null</TD><TD>&gt;</TD><TD>true</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso19</TD><TD>""</TD><TD>null</TD><TD>Cualquiera</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso20</TD><TD>"hola"</TD><TD>null</TD><TD>Cualquiera</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso21</TD><TD>50</TD><TD>null</TD><TD>=</TD><TD>true</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso22</TD><TD>50</TD><TD>null</TD><TD>&gt;</TD><TD>true</TD><TD>false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso23</TD><TD>""</TD><TD>null</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso24</TD><TD>"hola"</TD><TD>null</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso25</TD><TD>50</TD><TD>null</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso26</TD><TD>50</TD><TD>null</TD><TD>Cualquiera</TD><TD>false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso27</TD><TD>""</TD><TD>""</TD><TD>=</TD><TD>true/false</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso28</TD><TD>""</TD><TD>""</TD><TD>&lt;&gt;</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso29</TD><TD>""</TD><TD>""</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso30</TD><TD>""</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso31</TD><TD>""</TD><TD>50</TD><TD>=</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso32</TD><TD>""</TD><TD>50</TD><TD>&lt;</TD><TD>true/false</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso33</TD><TD>""</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso34</TD><TD>""</TD><TD>50</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso35</TD><TD>"hola"</TD><TD>""</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso36</TD><TD>50</TD><TD>""</TD><TD>=</TD><TD>true/false</TD><TD>true</TD><TD>false</TD></TR>
     *     <TR><TD>Caso37</TD><TD>50</TD><TD>""</TD><TD>&gt;</TD><TD>true/false</TD><TD>true</TD><TD>true</TD></TR>
     *     <TR><TD>Caso38</TD><TD>"hola"</TD><TD>""</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso39</TD><TD>"hola"</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso40</TD><TD>"hola"</TD><TD>50</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso41</TD><TD>50</TD><TD>"hola"</TD><TD>Cualquiera</TD><TD>true/false</TD><TD>true/false</TD><TD>false</TD></TR>
     *     <TR><TD>Caso42</TD><TD>50</TD><TD>50</TD><TD>=</TD><TD>true/false</TD><TD>true/false</TD><TD>true</TD></TR>
     *     <TR><TD>Caso43</TD><TD>50</TD><TD>10</TD><TD>&lt;</TD><TD>true/false</TD><TD>true/false</TD><TD>false</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param row1 La fila de la celda que se quiere comparar.
     * @param col1 La columna de la celda que se quiere comparar.
     * @param row2 La fila de la celda con la que se quiere comparar.
     * @param col2 La columna de la celda con la que se quiere comparar.
     * @param operador El operador que se utilizará para la comparación.
     * @param nullAsZero Determina si los valores null deben ser considerados como cero.
     * @param emptyStringAsZero Determina si las cadenas vacías deben ser considerados como cero.
     * <UL>
     * <LI>0: Igual (INT_COM_NUM_IGU).
     * <LI>1: Diferente (INT_COM_NUM_DIF).
     * <LI>2: Mayor que (INT_COM_NUM_MAY).
     * <LI>3: Mayor o igual que (INT_COM_NUM_MAY_IGU).
     * <LI>4: Menor que (INT_COM_NUM_MEN).
     * <LI>5: Menor o igual que (INT_COM_NUM_MEN_IGU).
     * </UL>
     * @return true: Si la comparación es verdadera.
     * <BR>false: En el caso contrario.
     */
    public boolean compareNumericCells(int row1, int col1, int row2, int col2, int operador, boolean nullAsZero, boolean emptyStringAsZero)
    {
        Object objValCel1, objValCel2;
        java.math.BigDecimal bgdValCel1, bgdValCel2;
        boolean blnRes=false;
        try
        {
            objValCel1=getValueAt(row1, col1);
            objValCel2=getValueAt(row2, col2);
            if (objValCel1==null)
            {
                if (objValCel2==null)
                {
                    if (nullAsZero)
                        blnRes=true; //Caso 1 (OK)
                    else
                        blnRes=false; //Caso 2 (OK)
                }
                else
                {
                    if (nullAsZero)
                    {
                        bgdValCel1=java.math.BigDecimal.ZERO;
                        if (emptyStringAsZero)
                        {
                            if (objValCel2.toString().equals(""))
                            {
                                blnRes=true; //Caso 3 (OK)
                            }
                            else if (objUti.isNumero(objValCel2.toString()))
                            {
                                bgdValCel2=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel2.toString()));
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 5 y 6 (OK)
                            }
                            else
                            {
                                blnRes=false; //Caso 4 (OK)
                            }
                        }
                        else
                        {
                            if (objUti.isNumero(objValCel2.toString()))
                            {
                                bgdValCel2=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel2.toString()));
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 9 y 10 (OK)
                            }
                            else
                            {
                                blnRes=false; //Caso 7 y 8 (OK)
                            }
                        }
                    }
                    else
                    {
                        blnRes=false; //Caso 11 al 13 (OK)
                    }
                }
            }
            else
            {
                if (objValCel2==null)
                {
                    if (nullAsZero)
                    {
                        bgdValCel2=java.math.BigDecimal.ZERO;
                        if (emptyStringAsZero)
                        {
                            if (objValCel1.toString().equals(""))
                            {
                                bgdValCel1=java.math.BigDecimal.ZERO;
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 14 y 15 (OK)
                            }
                            else if (objUti.isNumero(objValCel1.toString()))
                            {
                                bgdValCel1=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel1.toString()));
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 17 y 18 (OK)
                            }
                            else
                            {
                                blnRes=false; //Caso 16 (OK)
                            }
                        }
                        else
                        {
                            if (objUti.isNumero(objValCel1.toString()))
                            {
                                bgdValCel1=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel1.toString()));
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 21 y 22 (OK)
                            }
                            else
                            {
                                blnRes=false; //Caso 19 y 20 (OK)
                            }
                        }
                    }
                    else
                    {
                        blnRes=false; //Caso 23 al 26 (OK)
                    }
                }
                else
                {
                    if (objValCel1.toString().equals(""))
                    {
                        if (objValCel2.toString().equals(""))
                        {
                            if (emptyStringAsZero)
                            {
                                bgdValCel1=java.math.BigDecimal.ZERO;
                                bgdValCel2=java.math.BigDecimal.ZERO;
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 27 y 28
                            }
                            else
                            {
                                blnRes=false; //Caso 29
                            }
                        }
                        else
                        {
                            if (emptyStringAsZero)
                            {
                                bgdValCel1=java.math.BigDecimal.ZERO;
                                if (objUti.isNumero(objValCel2.toString()))
                                {
                                    bgdValCel2=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel2.toString()));
                                    blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 31 y 32
                                }
                                else
                                {
                                    blnRes=false; //Caso 30
                                }
                            }
                            else
                            {
                                blnRes=false; //Caso 33 y 34
                            }
                        }
                    }
                    else
                    {
                        if (objValCel2.toString().equals(""))
                        {
                            if (emptyStringAsZero)
                            {
                                bgdValCel2=java.math.BigDecimal.ZERO;
                                if (objUti.isNumero(objValCel1.toString()))
                                {
                                    bgdValCel1=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel1.toString()));
                                    blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 36 y 37
                                }
                                else
                                {
                                    blnRes=false; //Caso 35
                                }
                            }
                            else
                            {
                                blnRes=false; //Caso 38
                            }
                        }
                        else
                        {
                            if (objUti.isNumero(objValCel1.toString()) && objUti.isNumero(objValCel2.toString()))
                            {
                                bgdValCel1=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel1.toString()));
                                bgdValCel2=java.math.BigDecimal.valueOf(Double.parseDouble(objValCel2.toString()));
                                blnRes=compararValBgd(bgdValCel1, bgdValCel2, operador); //Caso 42 al 43
                            }
                            else
                            {
                                blnRes=false; //Caso 39 al 41
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función agrega una columna al final del JTable.
     * @param headerValue El título de la columna.
     * Si el valor es <I>null</I> el título será "Columna N".
     * <BR>Ejemplo:
     * <UL>
     * <LI>Si se agrega la columna 3 el título será "Columna 3".</LI>
     * <LI>Si se agrega la columna 5 el título será "Columna 5".</LI>
     * </UL>
     * Nota.- Por alguna razón desconocida luego de agregar la columna no aparece.
     * Para que aparezca debe llamar el método "updateComponentTreeUI".
     * <BR>Ejemplo:
     * <BR>objTblMod.addColumn("Cliente");
     * <BR>javax.swing.SwingUtilities.updateComponentTreeUI(tblDat);
     */
    public void addColumn(Object headerValue)
    {
        //Asignar el título a la cabecera.
        if (headerValue==null)
            vecCabMod.add("Columna " + vecCabMod.size());
        else
            vecCabMod.add(headerValue);
        //Agregar las celdas de la nueva columna.
        for (Object objAux : vecDatMod)
        {
            ((Vector)objAux).add(null);
        }
        //Establecer que la columna agregada de manera predeterminada sea de tipo cadena.
        arlTipDatCol.add(null);
        setColumnDataType(vecCabMod.size()-1, INT_COL_STR, null, null);
        fireTableStructureChanged();
    }
    
    /**
     * Esta función elimina la columna especificada del JTable.
     * @param columna La columna a eliminar.
     * Nota.- Si la columna no desaparece puede llamar al método "updateComponentTreeUI".
     * <BR>Ejemplo:
     * <BR>objTblMod.removeColumn(3);
     * <BR>javax.swing.SwingUtilities.updateComponentTreeUI(tblDat);
     */
    public void removeColumn(int columna)
    {
        //Eliminar la columna de la cabecera.
        vecCabMod.remove(columna);
        //Eliminar las celdas de la columna a eliminar.
        for (Object objAux : vecDatMod)
        {
            ((Vector)objAux).remove(columna);
        }
        fireTableStructureChanged();
    }

    /**
     * Esta función obtiene el ancho de la columna oculta por el Sistema.
     * @param columna La columna de la que se desea obtener el ancho.
     * @return El ancho de la columna oculta especificada.
     */
    public int getWidthSystemHiddenColumn(int columna)
    {
        int intRes=0;
        try
        {
            intRes=((ZafDatCol)arlColOcuSis.get(getPosColBus(columna, 0))).getAnchoPreferido();
        }
        catch (Exception e)
        {
            intRes=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return intRes;
    }
    
    /**
     * Esta función obtiene el ancho de la columna oculta por el Usuario.
     * @param columna La columna de la que se desea obtener el ancho.
     * @return El ancho de la columna oculta especificada.
     */
    public int getWidthUserHiddenColumn(int columna)
    {
        int intRes=0;
        try
        {
            intRes=((ZafDatCol)arlColOcuUsr.get(getPosColBus(columna, 1))).getAnchoPreferido();
        }
        catch (Exception e)
        {
            intRes=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return intRes;
    }
    
    /**
     * Esta función asigna <I>null</I> a la celda especificada.
     * @param row Fila.
     * @param col Columna.
     * @param markRowAsModified En la celda cero se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
     * <UL>
     * <LI>true: 1) No permite borrar el contenido de la celda cero. 2) Luego de borrar se marca la fila como modificada.</LI>
     * <LI>false: 1) Si permite borrar el contenido de la celda cero. 2) Luego de borrar NO se marca la fila como modificada.</LI>
     * </UL>
     * @return true: Si se pudo ejecutar la función con éxito.
     * <BR>false: En el caso contrario.
     */
    public boolean clearCell(int row, int col, boolean markRowAsModified)
    {
        boolean blnRes=true;
        try
        {
            if (markRowAsModified)
            {
                //No borrar la celda cero porque ahí se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
                if (col>0)
                    setValueAtCell(null, row, col);
            }
            else
            {
                ((Vector)vecDatMod.get(row)).set(col, null);
                fireTableCellUpdated(row, col);
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función asigna <I>null</I> a todas las celdas de la fila especificada.
     * @param row La fila a borrar.
     * @param markRowAsModified En la celda cero se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
     * <UL>
     * <LI>true: 1) No permite borrar el contenido de la celda cero. 2) Luego de borrar se marca la fila como modificada.</LI>
     * <LI>false: 1) Si permite borrar el contenido de la celda cero. 2) Luego de borrar NO se marca la fila como modificada.</LI>
     * </UL>
     * @return true: Si se pudo ejecutar la función con éxito.
     * <BR>false: En el caso contrario.
     */
    public boolean clearRow(int row, boolean markRowAsModified)
    {
        int j, intNumTotCol;
        boolean blnRes=true;
        try
        {
            intNumTotCol=vecCabMod.size();
            if (markRowAsModified)
            {
                //No borrar la celda cero porque ahí se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
                for (j=1; j<intNumTotCol; j++)
                {
                    setValueAt(null, row, j);
                }
            }
            else
            {
                for (j=0; j<intNumTotCol; j++)
                {
                    ((Vector)vecDatMod.get(row)).set(j, null);
                    fireTableCellUpdated(row, j);
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * Esta función asigna <I>null</I> a todas las celdas de la columna especificada.
     * @param col La columna a borrar.
     * @param markRowAsModified En la celda cero se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
     * <UL>
     * <LI>true: 1) No permite borrar el contenido de la celda cero. 2) Luego de borrar se marca la fila como modificada.</LI>
     * <LI>false: 1) Si permite borrar el contenido de la celda cero. 2) Luego de borrar NO se marca la fila como modificada.</LI>
     * </UL>
     * @return true: Si se pudo ejecutar la función con éxito.
     * <BR>false: En el caso contrario.
     */
    public boolean clearColumn(int col, boolean markRowAsModified)
    {
        int i, intNumTotFil;
        boolean blnRes=true;
        try
        {
            intNumTotFil=vecDatMod.size();
            if (markRowAsModified)
            {
                //No borrar la columna cero porque ahí se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
                if (col>0)
                {
                    for (i=0; i<intNumTotFil; i++)
                    {
                        setValueAt(null, i, col);
                    }
                }
            }
            else
            {
                for (i=0; i<intNumTotFil; i++)
                {
                    ((Vector)vecDatMod.get(i)).set(col, null);
                    fireTableCellUpdated(i, col);
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función asigna <I>null</I> a todas las celdas de tabla.
     * @param markRowAsModified En la celda cero se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
     * <UL>
     * <LI>true: 1) No permite borrar el contenido de la celda cero. 2) Luego de borrar se marca la fila como modificada.</LI>
     * <LI>false: 1) Si permite borrar el contenido de la celda cero. 2) Luego de borrar NO se marca la fila como modificada.</LI>
     * </UL>
     * @return true: Si se pudo ejecutar la función con éxito.
     * <BR>false: En el caso contrario.
     */
    public boolean clearAllRows(boolean markRowAsModified)
    {
        int i, j, intNumTotFil, intNumTotCol;
        boolean blnRes=true;
        try
        {
            intNumTotFil=vecDatMod.size();
            intNumTotCol=vecCabMod.size();
            if (markRowAsModified)
            {
                for (i=0; i<intNumTotFil; i++)
                {
                    //No borrar la celda cero porque ahí se guarda el estado que indica si la fila ha sido insertada, modificada, etc.
                    for (j=1; j<intNumTotCol; j++)
                    {
                        setValueAt(null, i, j);
                    }
                }
            }
            else
            {
                for (i=0; i<intNumTotFil; i++)
                {
                    for (j=0; j<intNumTotCol; j++)
                    {
                        ((Vector)vecDatMod.get(i)).set(j, null);
                        fireTableCellUpdated(i, j);
                    }
                }
            }
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
}
