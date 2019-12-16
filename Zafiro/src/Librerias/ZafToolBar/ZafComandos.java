/*
 * ZafComandos.java
 *
 * Created on 3 de agosto de 2004, 16:57
 */

package Librerias.ZafToolBar;
/**
 * Interface que contiene los métodos que que deberán ser implementados por los botones
 * de la clase ZafToolbar
 * @author jnaranjo
 * Revisión: 26/Sep/2005 Eddye Lino
 */
public interface ZafComandos
{    
    /**
     * En la implementación de esta interface, el método presionarBotPri() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Ir al primer elemento"
     */    
    void clickInicio();
    
    /**
     * En la implementación de esta interface, el método presionarBotAnt() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Ir al elemento anterior"
     */    
    void clickAnterior();

    /**
     * En la implementación de esta interface, el método presionarBotAde() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Ir al siguiente elemento"
     */    
    void clickSiguiente();

    /**
     * En la implementación de esta interface, el método presionarBotUlt() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Ir al último elemento"
     */    
    void clickFin();

    /**
     * En la implementación de esta interface, el método presionarBotNue() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Nuevo"
     */    
    void clickInsertar();
    
    /**
     * En la implementación de esta interface, el método presionarBotCon() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Consultar"
     */    
    void clickConsultar();
    
    /**
     * En la implementación de esta interface, el método presionarBotMod() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Modificar"
     */    
    void clickModificar();
    
    /**
     * En la implementación de esta interface, el método presionarBotEli() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Eliminar"
     */    
    void clickEliminar();
    
    /**
     * En la implementación de esta interface, el método presionarBotAnu() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Nuevo"
     */    
    void clickAnular();
    
    /**
     * En la implementación de esta interface, el método presionarBotImp() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Imprimir"
     */    
    void clickImprimir();
    
    /**
     * En la implementación de esta interface, el método presionarBotVis() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Vista Preliminar"
     */    
    void clickVisPreliminar();
    
    /**
     * En la implementación de esta interface, el método presionarBotAce() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Aceptar"
     */    
    void clickAceptar();
    
    /**
     * En la implementación de esta interface, el método presionarBotCan() contendrá todas
     * las operaciones que se deberán realizar al dar click en el Botón "Cancelar"
     */    
    void clickCancelar();
    
    boolean insertar();
    
    boolean consultar();
    
    boolean modificar();
    
    boolean eliminar();
    
    boolean anular();
    
    boolean imprimir();
    
    boolean vistaPreliminar();
    
    boolean aceptar();

    boolean cancelar();
    
    boolean beforeInsertar();
    
    boolean beforeConsultar();
    
    boolean beforeModificar();
    
    boolean beforeEliminar();
    
    boolean beforeAnular();
    
    boolean beforeImprimir();
    
    boolean beforeVistaPreliminar();
    
    boolean beforeAceptar();
    
    boolean beforeCancelar();
    
    boolean afterInsertar();
    
    boolean afterConsultar();
    
    boolean afterModificar();
    
    boolean afterEliminar();
    
    boolean afterAnular();
    
    boolean afterImprimir();
    
    boolean afterVistaPreliminar();
    
    boolean afterAceptar();
    
    boolean afterCancelar();
    
}
