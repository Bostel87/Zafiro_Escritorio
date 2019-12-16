/*
 * ZafTblCelRenListener.java
 *
 * Created on 23 de junio de 2008, 10:00 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta interface es utilizada para almacenar los métodos que deberán ser implementados
 * por las clases que deseen trabajar con listeners. Mediante ésta interface se puede
 * sensar acciones que se realizan sobre las celdas de un JTable.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeRender,
 * </UL>
 * @author  Eddye Lino
 */
public interface ZafTblCelRenListener extends java.util.EventListener
{
    
    /**
     * Esta función se ejecuta antes de renderizar una celda.
     * @param evt El evento generado.
     */
    public void beforeRender(ZafTblCelRenEvent evt);
    
}
