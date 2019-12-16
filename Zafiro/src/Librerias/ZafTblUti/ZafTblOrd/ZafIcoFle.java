/*
 * ZafIcoFle.java
 *
 * Created on 18 de febrero de 2006, 05:53 PM
 */

package Librerias.ZafTblUti.ZafTblOrd;

/**
 * Esta clase crea el icono que se muestra por lo general en la cabecera de un JTable al ordenar
 * los datos de acuerdo a la columna en la que se dió click. Con ésta clase es posible crear 2
 * tipos de iconos. Uno que indica que los datos se han ordenado ascendentemente y otro que indica
 * que los datos se han ordenado descendentemente.
 * @author Sun Microsystems Inc.
 * Modificado Eddye Lino.
 */
public class ZafIcoFle implements javax.swing.Icon
{
    private boolean descending;
    private int size;
    
    /**
     * Este constructor establece si se debe crear una flecha ascendente o descendente.
     * @param descendente Un valor booleano que determina el tipo de flecha a crear.
     * Puede tomar los siguientes valores:
     * <BR>true: Se creará una flecha descendente.
     * <BR>false: Se creará una flecha ascendente.
     * @param tamanio El tamaño del icono a crear.
     */
    public ZafIcoFle(boolean descendente, int tamanio)
    {
        this.descending=descendente;
        this.size=tamanio;
    }

    public void paintIcon(java.awt.Component c, java.awt.Graphics g, int x, int y)
    {
        java.awt.Color color=((c==null)?java.awt.Color.GRAY:c.getBackground());
        // In a compound sort, make each succesive triangle 20% smaller than the previous one.
        int dx=(int)(size/2*Math.pow(0.8, 0));
        int dy=descending?dx:-dx;
        // Align icon (roughly) with font baseline.
        y=y + 5*size/6 + (descending?-dy:0);
        int shift=descending?1:-1;
        g.translate(x, y);
        // Right diagonal.
        g.setColor(color.darker());
        g.drawLine(dx/2, dy, 0, 0);
        g.drawLine(dx/2, dy + shift, 0, shift);
        // Left diagonal.
        g.setColor(color.brighter());
        g.drawLine(dx/2, dy, dx, 0);
        g.drawLine(dx/2, dy + shift, dx, shift);
        // Horizontal line.
        if (descending)
        {
            g.setColor(color.darker().darker());
        }
        else
        {
            g.setColor(color.brighter().brighter());
        }
        g.drawLine(dx, 0, 0, 0);
        g.setColor(color);
        g.translate(-x, -y);
    }

    public int getIconWidth()
    {
        return size;
    }

    public int getIconHeight()
    {
        return size;
    }
    
}