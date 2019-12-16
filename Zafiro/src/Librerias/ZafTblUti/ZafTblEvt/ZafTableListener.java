/*
 * ZafTableListener.java
 *
 * Created on 24 de octubre de 2005, 02:01 PM
 * v0.2
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta interface es utilizada para almacenar los métodos que deberán ser implementados
 * por las clases que deseen trabajar con listeners. Mediante ésta interface se puede
 * sensar acciones que se realizan sobre las celdas de un JTable.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeEdit,
 * <LI>2) beforeConsultar.
 * <LI>3) afterConsultar.
 * <LI>4) afterEdit.
 * </UL>
 * @author  Eddye Lino
 */
public interface ZafTableListener extends java.util.EventListener
{
    
    /**
     * Esta función se ejecuta antes de editar una celda.
     * @param evt El evento generado.
     */
    public void beforeEdit(ZafTableEvent evt);
    
    /**
     * Esta función se ejecuta luego de editar una celda.
     * @param evt El evento generado.
     */
    public void afterEdit(ZafTableEvent evt);
    
    /**
     * Esta función se ejecuta antes de utilizar la ventana de consulta.
     * @param evt El evento generado.
     */
    public void beforeConsultar(ZafTableEvent evt);
    
    /**
     * Esta función se ejecuta luego de utilizar la ventana de consulta.
     * @param evt El evento generado.
     */
    public void afterConsultar(ZafTableEvent evt);

    /**
     * Esta función se ejecuta al realizar la acción sobre la celda.
     * Por lo general es usado cuando el editor es un JButton. Este método es invocado al dar
     * click en el botón o al presionar la tecla SPACE en la celda donde se encuentra el botón.
     * @param evt El evento generado.
     */
    public void actionPerformed(ZafTableEvent evt);
    
}
