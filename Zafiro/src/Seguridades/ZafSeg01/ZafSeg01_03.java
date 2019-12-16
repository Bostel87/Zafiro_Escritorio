/*
 * ZafSeg01_03.java
 *
 * Created on 1 de noviembre de 2004, 05:28 PM
 */

package Seguridades.ZafSeg01;

/**
 *
 * @author  Eddye Lino
 */
public class ZafSeg01_03 extends javax.swing.JMenu
{
    private int intCodMnu;
    private String strTipMnu;
    private String strAcc;
    
    /** Crea una nueva instancia de la clase ZafSeg01_03. */
    public ZafSeg01_03() 
    {
        
    }

    /**
     * Obtiene el tipo de menú. Los tipos posibles son: JMenu, JMenuItem, JCheckBoxMenuItem,llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @return La cadena que contiene la acción a realizar.
     */
    public int getCodigoMenu()
    {
        return intCodMnu;
    }

    /**
     * Establece la acción que debe realizar el menú. Las acciones posibles son: llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @param accion La acción a realizar.
     */
    public void setCodigoMenu(int codigoMenu)
    {
        intCodMnu=codigoMenu;
    }

    /**
     * Obtiene el tipo de menú. Los tipos posibles son: JMenu, JMenuItem, JCheckBoxMenuItem,llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @return La cadena que contiene la acción a realizar.
     */
    public String getTipoMenu()
    {
        return strTipMnu;
    }

    /**
     * Establece la acción que debe realizar el menú. Las acciones posibles son: llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @param accion La acción a realizar.
     */
    public void setTipoMenu(String tipoMenu)
    {
        strTipMnu=tipoMenu;
    }
    
    /**
     * Obtiene la acción que debe realizar el menú. Las acciones posibles son: llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @return La cadena que contiene la acción a realizar.
     */
    public String getAccion()
    {
        return strAcc;
    }

    /**
     * Establece la acción que debe realizar el menú. Las acciones posibles son: llamar un JComponent
     * del tipo JFrame, JInternalFrame, etc. o llamar una función. Por ejemplo, si se quiere llamar
     * una cotización se debe llamar a un JInternalFrame mientras que si se quiere utilizar el menú
     * para salir de la aplicación se debe invocar a la función salir.
     * @param accion La acción a realizar.
     */
    public void setAccion(String accion)
    {
        strAcc=accion;
    }
    
}
