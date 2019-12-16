/*
 * ZafTblCelRenEvent.java
 *
 * Created on 23 de junio de 2008, 09:58 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento al "EventObject".
 * @author Eddye Lino
 */
public class ZafTblCelRenEvent extends java.util.EventObject
{
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblCelRenEvent.
     * @param source Origen del evento.
     */
    public ZafTblCelRenEvent(Object source)
    {
        super(source);
    }
    
}
