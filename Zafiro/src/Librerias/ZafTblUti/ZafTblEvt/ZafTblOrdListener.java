/*
 * ZafTblOrdListener.java
 * 
 * Created on 09 de noviembre de 2017, 12:55 AM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblEvt;

/**
 * Esta interface es utilizada para almacenar los métodos que deberán ser implementados
 * por las clases que deseen trabajar con listeners. Mediante ésta interface se puede
 * sensar acciones que se realizan sobre las opciones de un ZafTblOrd.
 * <BR>El orden de ejecución de los métodos es:
 * <UL>
 * <LI>1) beforeOrder,
 * <LI>2) afterOrder.
 * </UL>
 * @author Eddye Lino
 */
public interface ZafTblOrdListener extends java.util.EventListener
{

    /**
     * Esta función se ejecuta antes de ejecutar el evento que ordena los datos de un JTable al dar click sobre una columna.
     * @param evt El evento generado.
     */
    public void beforeOrder(ZafTblOrdEvent evt);
    
    /**
     * Esta función se ejecuta luego de ejecutar el evento que ordena los datos de un JTable al dar click sobre una columna.
     * @param evt El evento generado.
     */
    public void afterOrder(ZafTblOrdEvent evt);

}
