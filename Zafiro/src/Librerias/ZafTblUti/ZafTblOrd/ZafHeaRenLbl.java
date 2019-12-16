/*
 * ZafTblCelRenLbl.java
 *
 * Created on 18 de febrero de 2005, 14:50 PM
 * v0.2
 */

package Librerias.ZafTblUti.ZafTblOrd;
import javax.swing.table.TableCellRenderer;

/**
 * Esta clase renderiza el JTable que se muestra en las celdas de la cabecera del JTable.
 * Cuando se da click en una de las columnas de la cabecera del JTable se muestra un icono que indica
 * el tipo de ordenamiento que se ha llevado a cabo.
 * @author  Eddye Lino
 */
public class ZafHeaRenLbl implements javax.swing.table.TableCellRenderer
{
    //Constantes:
    public static final int INT_TBL_SIN=0;   /**Un valor para setTipoOrdenamiento: Indica "Sin ordenar".*/
    public static final int INT_TBL_ASC=1;   /**Un valor para setTipoOrdenamiento: Indica "Orden ascendente".*/
    public static final int INT_TBL_DES=2;   /**Un valor para setTipoOrdenamiento: Indica "Orden descendente".*/
    //Variables:
    private TableCellRenderer objTblCelRen;
    private int intColOrd;                  //Columna a ordenar.
    private int intTipOrd;                  //Tipo de ordenamiento.
    
    /** 
     * Crea una nueva instancia de la clase ZafHeaRenLbl.
     * @param render Renderizador.
     */
    public ZafHeaRenLbl(TableCellRenderer render)
    {
        objTblCelRen=render;
        intTipOrd=0;
    }
    
    /**
     * Esta función obtiene el componente usado para dibujar la celda. Este método es
     * usado para configurar el render apropiado antes de dibujar la celda.
     * @return El componente mostrado en la celda.
     */
    @Override
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        javax.swing.JLabel lblAux=null;
        java.awt.Component objCom=objTblCelRen.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (objCom instanceof javax.swing.JLabel)
        {
            lblAux=(javax.swing.JLabel)objCom;
            //Acercar el texto al icono.
            lblAux.setIconTextGap(0);
            //Mostrar el texto al lado derecho de la etiqueta.
            lblAux.setHorizontalTextPosition(javax.swing.JLabel.RIGHT);
            lblAux.setPreferredSize(new java.awt.Dimension(0,16));
            if (column==intColOrd)
            {
                switch (intTipOrd)
                {
                    case INT_TBL_SIN:
                        lblAux.setIcon(null);
                        break;
                    case INT_TBL_ASC:
                        lblAux.setIcon(new Librerias.ZafTblUti.ZafTblOrd.ZafIcoFle(false, 12));
                        break;
                    case INT_TBL_DES:
                        lblAux.setIcon(new Librerias.ZafTblUti.ZafTblOrd.ZafIcoFle(true, 12));
                        break;
                }
            }
            else
                lblAux.setIcon(null);
        }
        return lblAux;
    }
    
    /**
     * Esta función establece el tipo de ordenamiento que se debe aplicar a la columna especificada.
     * @param columna La columna a ordenar.
     * @param tipoOrdenamiento El tipo de ordenamiento que se debe aplicar a la columna.
     * Puede tomar los siguientes valores:
     * <UL>
     * <LI>0: Sin ordenar (INT_TBL_SIN).</LI>
     * <LI>1: Orden ascendente (INT_TBL_ASC).</LI>
     * <LI>2: Orden descendente (INT_TBL_DES).</LI>
     * </UL>
     */
    public void setTipoOrdenamiento(int columna, int tipoOrdenamiento)
    {
        intColOrd=columna;
        intTipOrd=tipoOrdenamiento;
    }
    
    /**
     * Esta función obtiene el tipo de ordenamiento aplicado a la tabla.
     * @return El tipo de ordenamiento aplicado a la tabla.
     * Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>0: Sin ordenar (INT_TBL_SIN).</LI>
     * <LI>1: Orden ascendente (INT_TBL_ASC).</LI>
     * <LI>2: Orden descendente (INT_TBL_DES).</LI>
     * </UL>
     */
    public int getTipoOrdenamiento()
    {
        return intTipOrd;
    }
    
}