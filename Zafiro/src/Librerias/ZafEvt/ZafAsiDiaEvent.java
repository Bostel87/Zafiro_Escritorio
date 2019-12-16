/*
 * ZafAsiDiaEvent.java
 *
 * Created on 09 de julio de 2007, 09:21 AM
 * v0.1
 */

package Librerias.ZafEvt;

/**
 * Esta clase crea el objeto que será encargado de comunicar la generación del evento
 * al "EventObject".
 * @author  Eddye Lino
 */
public class ZafAsiDiaEvent extends java.util.EventObject
{
    
    /** Creates a new instance of ZafAsiDiaEvent */
    public ZafAsiDiaEvent(Object source)
    {
        super(source);
    }
    
}
