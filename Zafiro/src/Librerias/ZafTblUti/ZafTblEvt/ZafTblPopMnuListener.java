/*
 * ZafTblPopMnuListener.java
 *
 * Created on 10 de mayo de 2007, 09:36 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta interface es utilizada para almacenar los métodos que deberán ser implementados
 * por las clases que deseen trabajar con listeners. Mediante ésta interface se puede
 * sensar acciones que se realizan sobre las opciones de un ZafTblPopMnu.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeClick,
 * <LI>2) afterClick.
 * </UL>
 * @author Eddye Lino
 */
public interface ZafTblPopMnuListener extends java.util.EventListener
{
    
    /**
     * Esta función se ejecuta antes de ejecutar el evento click de la opción seleccionada en el menú.
     * @param evt El evento generado.
     */
    public void beforeClick(ZafTblPopMnuEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento click de la opción seleccionada en el menú.
     * @param evt El evento generado.
     */
    public void afterClick(ZafTblPopMnuEvent evt);

}
