/*
 * ZafTableEvent.java
 *
 * Created on 26 de octubre de 2005, 11:38 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento al "EventObject".
 * @author Eddye Lino
 */
public class ZafTableEvent extends java.util.EventObject
{
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTableEvent.
     * @param source Origen del evento.
     */
    public ZafTableEvent(Object source)
    {
        super(source);
    }
    
}
