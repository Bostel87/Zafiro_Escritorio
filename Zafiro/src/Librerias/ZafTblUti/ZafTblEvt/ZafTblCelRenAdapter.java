/*
 * ZafTblCelRenAdapter.java
 *
 * Created on 23 de junio de 2008, 10:23 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase es un adaptador que implementa los métodos de la interface "ZafTblCelRenListener".
 * Utilizar un adaptador evita tener que implementar todos los métodos de la interface en
 * la clase donde se utiliza la interface. Por ejemplo, se puede tener una interface que
 * contienen 100 métodos y no ncesariamente en la clase donde se utiliza la interface se
 * necesitan utilizar los 100 métodos. En estos casos es ideal el uso de un adaptador ya
 * que por medio del adaptador sólo implemento los métodos que en realidad necesito.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeRender,
 * </UL>
 * @author Eddye Lino
 */
public abstract class ZafTblCelRenAdapter implements ZafTblCelRenListener
{
    
    /**
     * Esta función se ejecuta antes de renderizar una celda.
     * @param evt El evento generado.
     */
    public void beforeRender(ZafTblCelRenEvent evt)
    {
        
    }
    
}
