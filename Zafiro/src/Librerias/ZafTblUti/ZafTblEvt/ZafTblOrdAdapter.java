/*
 * ZafTblOrdAdapter.java
 * 
 * Created on 09 de noviembre de 2017, 01:09 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase es un adaptador que implementa los métodos de la interface "ZafTblOrdListener".
 * Utilizar un adaptador evita tener que implementar todos los métodos de la interface en
 * la clase donde se utiliza la interface. Por ejemplo, se puede tener una interface que
 * contienen 100 métodos y no ncesariamente en la clase donde se utiliza la interface se
 * necesitan utilizar los 100 métodos. En estos casos es ideal el uso de un adaptador ya
 * que por medio del adaptador sólo implemento los métodos que en realidad necesito.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeOrder,
 * <LI>2) afterOrder.
 * </UL>
 * @author Eddye Lino
 */
public abstract class ZafTblOrdAdapter implements ZafTblOrdListener
{
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento que ordena los datos de un JTable al dar click sobre una columna.
     * @param evt El evento generado.
     */
    public void beforeOrder(ZafTblOrdEvent evt)
    {
        
    }
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento que ordena los datos de un JTable al dar click sobre una columna.
     * @param evt El evento generado.
     */
    public void afterOrder(ZafTblOrdEvent evt)
    {
        
    }

}
