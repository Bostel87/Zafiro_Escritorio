/*
 * ZafAsiDiaListener.java
 *
 * Created on 09 de julio de 2007, 09:18 AM
 * v0.2
 */

package Librerias.ZafEvt;

/**
 * Esta interface es utilizada para almacenar los métodos que deberán ser implementados
 * por las clases que deseen trabajar con listeners. Mediante ésta interface se puede
 * sensar acciones que se realizan sobre la clase ZafAsiDia.
 * <BR>Primero se ejecutan los métodos que inician con el prefijo "before" y al final se ejecutan
 * los métodos que inician con el prefijo "after". Por ejemplo:
 * <UL>
 * <LI>1) Primero se ejecuta el método "beforeInsertar",
 * <LI>2) Luego se ejecuta el método "insertarDiario",
 * <LI>3) Y finalmente se ejecuta el método "afterInsertar",
 * </UL>
 * @author  Eddye Lino
 */
public interface ZafAsiDiaListener extends java.util.EventListener
{
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento "Insertar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeInsertar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento "Insertar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterInsertar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento "Consultar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeConsultar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento "Consultar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterConsultar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento "Modificar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeModificar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento "Modificar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterModificar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento "Eliminar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeEliminar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento "Eliminar" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterEliminar(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento "Anular" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeAnular(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento "Anular" en la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterAnular(ZafAsiDiaEvent evt);

    /**
     * Esta función se ejecuta antes de editar una celda en el JTable de la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void beforeEditarCelda(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de editar una celda en el JTable de la clase "ZafAsiDia".
     * @param evt El evento generado.
     */
    public void afterEditarCelda(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta antes de consultar las cuentas contables que se presentan en la "Ventana de consulta".
     * @param evt El evento generado.
     */
    public void beforeConsultarCuentas(ZafAsiDiaEvent evt);
    
    /**
     * Esta función se ejecuta luego de consultar las cuentas contables que se presentan en la "Ventana de consulta".
     * <BR>Nota.- Sólo se ejecuta esta función cuando el usuario selecciona una de las cuentas.
     * @param evt El evento generado.
     */
    public void afterConsultarCuentas(ZafAsiDiaEvent evt);
    
}
