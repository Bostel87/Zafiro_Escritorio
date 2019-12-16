/*
 * ZafTblOrdEvent.java
 *
 * Created on 09 de noviembre de 2017, 11:47 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento al "EventObject".
 * @author Eddye Lino
 */
public class ZafTblOrdEvent extends java.util.EventObject
{
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblOrdEvent.
     * @param source Origen del evento.
     */
    public ZafTblOrdEvent(Object source)
    {
        super(source);
    }

}
