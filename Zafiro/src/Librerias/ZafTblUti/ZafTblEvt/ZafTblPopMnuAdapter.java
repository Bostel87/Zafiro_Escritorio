/*
 * ZafTblPopMnuAdapter.java
 *
 * Created on 10 de mayo de 2007, 09:50 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase es un adaptador que implementa los métodos de la interface "ZafTblPopMnuListener".
 * Utilizar un adaptador evita tener que implementar todos los métodos de la interface en
 * la clase donde se utiliza la interface. Por ejemplo, se puede tener una interface que
 * contienen 100 métodos y no ncesariamente en la clase donde se utiliza la interface se
 * necesitan utilizar los 100 métodos. En estos casos es ideal el uso de un adaptador ya
 * que por medio del adaptador sólo implemento los métodos que en realidad necesito.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeClick,
 * <LI>2) afterClick.
 * </UL>
 * @author Eddye Lino
 */
public abstract class ZafTblPopMnuAdapter implements ZafTblPopMnuListener
{
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento click de la opción seleccionada en el menú.
     * @param evt El evento generado.
     */
    public void beforeClick(ZafTblPopMnuEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento click de la opción seleccionada en el menú.
     * @param evt El evento generado.
     */
    public void afterClick(ZafTblPopMnuEvent evt)
    {
        
    }
    
}
