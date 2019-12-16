/*
 * ActionToDo.java
 *
 * Created on 20 de julio de 2005, 15:25
 */

package Librerias.ZafUtil;

/**
 * Accion a realizar cuando el usuario de enter en el editor
 * Esto se realizara de forma adicional a lo que esta en el codigo de este editor
 * @author  Iditrix
 * @version 1.0
 */
public interface ActionToDo{
    /**
     * Accion que se realizara antes de que termine la edicion
     * de la celda.
     */
    void preActionToDo();

    /**
     * Accion que se realizara despues que termine la edicion
     * de la celda.
     */
    void posActionToDo();
}