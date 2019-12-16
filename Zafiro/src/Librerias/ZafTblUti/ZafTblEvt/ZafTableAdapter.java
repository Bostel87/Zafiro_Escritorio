/*
 * ZafTableAdapter.java
 *
 * Created on 24 de octubre de 2005, 04:50 PM
 * v0.2
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase es un adaptador que implementa los métodos de la interface "ZafTableListener".
 * Utilizar un adaptador evita tener que implementar todos los métodos de la interface en
 * la clase donde se utiliza la interface. Por ejemplo, se puede tener una interface que
 * contienen 100 métodos y no ncesariamente en la clase donde se utiliza la interface se
 * necesitan utilizar los 100 métodos. En estos casos es ideal el uso de un adaptador ya
 * que por medio del adaptador sólo implemento los métodos que en realidad necesito.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeEdit,
 * <LI>2) beforeConsultar.
 * <LI>3) afterConsultar.
 * <LI>4) afterEdit.
 * </UL>
 * @author Eddye Lino
 */
public abstract class ZafTableAdapter implements ZafTableListener
{
    
    /**
     * Esta función se ejecuta antes de editar una celda.
     * @param evt El evento generado.
     */
    public void beforeEdit(ZafTableEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta luego de editar una celda.
     * @param evt El evento generado.
     */
    public void afterEdit(ZafTableEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta antes de utilizar la ventana de consulta.
     * @param evt El evento generado.
     */
    public void beforeConsultar(ZafTableEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta luego de utilizar la ventana de consulta.
     * @param evt El evento generado.
     */
    public void afterConsultar(ZafTableEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta al realizar la acción sobre la celda.
     * Por lo general es usado cuando el editor es un JButton. Este método es invocado al dar
     * click en el botón o al presionar la tecla SPACE en la celda donde se encuentra el botón.
     * @param evt El evento generado.
     */
    public void actionPerformed(ZafTableEvent evt)
    {
        
    }
    
}
