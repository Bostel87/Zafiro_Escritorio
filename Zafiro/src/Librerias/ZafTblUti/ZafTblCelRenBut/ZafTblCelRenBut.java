/*
 * ZafTblCelRenBut.java
 *
 * Created on 26 de junio de 2005, 09:37 PM
 * v0.2
 */

package Librerias.ZafTblUti.ZafTblCelRenBut;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase establece el componente a mostrar en la celda basada en el uso de la interface "TableCellRenderer".
 * La clase JTable utiliza renderizadores para presentar los datos en una celda. Por lo general se utiliza
 * una etiqueta como renderizador. Pero se puede utilizar cualquier clase para renderizar la celda (JButton,
 * JComboBox, JTextField, etc). La presente clase utiliza como renderizador la clase JButton con lo cual en
 * la celda se mostrará un botón en vez de mostrar la celda como comúnmente se presenta.
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelRenBut" es la instancia de "ZafTblCelRenBut".
 * <PRE>
 *           objTblCelRenBut.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
 *               public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
 *                   if (objTblMod.getValueAt(objTblCelRenBut.getRowRender(), INT_TBL_DAT_NOM_CLI).toString().startsWith("A"))
 *                   {
 *                       objTblCelRenBut.setText("");
 *                       objTblCelRenBut.setBackground(java.awt.Color.YELLOW);
 *                   }
 *                   else
 *                   {
 *                       objTblCelRenBut.setText("...");
 *                       objTblCelRenBut.setBackground(java.awt.Color.BLUE);
 *                   }
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso del método "beforeRender".
 * <BR>Nota.- Los renderizadores sólo se encargan de la presentación. Para que el botón haga algo
 * se debe utilizar editores (javax.swing.table.TableCellEditor).
 * @author  Eddye Lino
 */
public class ZafTblCelRenBut extends javax.swing.JButton implements javax.swing.table.TableCellRenderer
{
    //Constantes:
    private static final int INT_BEF_REN=0;             /**Antes de editar: Indica "Before render".*/
    //Variables:
    private int intFilRen;                              //Fila a renderizar.
    private int intColRen;                              //Columna a renderizar.
    protected EventListenerList objEveLisLis=new EventListenerList();
    
    /** Crea una nueva instancia de la clase ZafTblCelRenBut. */
    public ZafTblCelRenBut()
    {
        this.setText("...");
    }
    
    /**
     * Esta función obtiene el componente usado para dibujar la celda. Este método es
     * usado para configurar el render apropiado antes de dibujar la celda.
     * @return El componente mostrado en la celda.
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        intFilRen=row;
        intColRen=column;
        //Generar evento "beforeRender()".
        fireTblCelRenListener(new ZafTblCelRenEvent(this), INT_BEF_REN);
        return this;
    }

    /**
     * Esta función adiciona el listener que controlará los eventos de renderización.
     * @param listener El objeto que implementa los métodos de la interface "ZafTblCelRenListener".
     */
    public void addTblCelRenListener(ZafTblCelRenListener listener)
    {
        objEveLisLis.add(ZafTblCelRenListener.class, listener);
    }

    /**
     * Esta función remueve el listener que controlará los eventos de renderización.
     * @param listener El objeto que implementa los métodos de la interface "ZafTblCelRenListener".
     */
    public void removeTblCelRenListener(ZafTblCelRenListener listener)
    {
        objEveLisLis.remove(ZafTblCelRenListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTblCelRenEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_REN</TD><TD>Invoca al métod "beforeRender" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_REN</TD><TD>Invoca al métod "afterRender" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTblCelRenListener(ZafTblCelRenEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTblCelRenListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_REN:
                        ((ZafTblCelRenListener)obj[i+1]).beforeRender(evt);
                        break;
                }
            }
        }
    }
    
    /**
     * Esta función obtiene la fila a renderizar.
     * @return La fila a renderizar. 
     */
    public int getRowRender()
    {
        return intFilRen;
    }

    /**
     * Esta función obtiene la columna a renderizar.
     * @return La columna a renderizar. 
     */
    public int getColumnRender()
    {
        return intColRen;
    }
    
}
