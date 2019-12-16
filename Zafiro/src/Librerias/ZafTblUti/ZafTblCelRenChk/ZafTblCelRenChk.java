/*
 * ZafTblCelRenChk.java
 *
 * Created on 29 de junio de 2005, 08:08 AM
 * v0.3
 */

package Librerias.ZafTblUti.ZafTblCelRenChk;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import javax.swing.event.EventListenerList;

/**
 * Esta clase establece el componente a mostrar en la celda basada en el uso de la interface "TableCellRenderer".
 * La clase JTable utiliza renderizadores para presentar los datos en una celda. Por lo general se utiliza
 * una etiqueta como renderizador. Pero se puede utilizar cualquier clase para renderizar la celda (JButton,
 * JComboBox, JTextField, etc). La presente clase utiliza como renderizador la clase JCheckBox con lo cual en
 * la celda se mostrará una casilla de verificación en vez de mostrar la celda como comúnmente se presenta.
 * <BR>Nota.- Los renderizadores sólo se encargan de la presentación. Para que la casilla de verificación haga algo
 * se debe utilizar editores (javax.swing.table.TableCellEditor).
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelRenChk" es la instancia de "ZafTblCelRenChk".
 * <PRE>
 *           objTblCelRenChk.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
 *               public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
 *                   //Mostrar de color gris las columnas impares.
 *                   if (objTblCelRenChk.getRowRender()%2==0)
 *                   {
 *                       objTblCelRenChk.setBackground(java.awt.Color.WHITE);
 *                   }
 *                   else
 *                   {
 *                       objTblCelRenChk.setBackground(java.awt.Color.LIGHT_GRAY);
 *                   }
 *                   //Mostrar ToolTipText con el contenido de la celda.
 *                   objTblCelRenChk.setToolTipText("Celda seleccionada: " + objTblCelRenChk.isSelected());
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso del método "beforeRender".
 * @author  Eddye Lino
 */
public class ZafTblCelRenChk extends javax.swing.JCheckBox implements javax.swing.table.TableCellRenderer
{
    //Constantes:
    private static final int INT_BEF_REN=0;                     /**Antes de editar: Indica "Before render".*/
    //Variables:
    private ZafConCel objConCel;                                //Configuración de la celda.
    private int intFilRen;                                      //Fila a renderizar.
    private int intColRen;                                      //Columna a renderizar.
    protected EventListenerList objEveLisLis=new EventListenerList();

    /** Crea una nueva instancia de la clase ZafTblCelRenChk. */
    public ZafTblCelRenChk()
    {
        //Configurar la casilla que renderizará la celda.
        this.setOpaque(true);
        this.setBorderPainted(true);
        this.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        this.setSelected(false);
        //Obtener la configuración de la celda.
        objConCel=new ZafConCel();
        objConCel.colPriPla=javax.swing.UIManager.getColor("Table.foreground");
        objConCel.colSegPla=javax.swing.UIManager.getColor("Table.background");
        objConCel.colSelPri=javax.swing.UIManager.getColor("Table.selectionForeground");
        objConCel.colSelSeg=javax.swing.UIManager.getColor("Table.selectionBackground");
        objConCel.borNor=new javax.swing.border.EmptyBorder(1,1,1,1);
//        objConCel.borFoc=javax.swing.UIManager.getBorder("Table.focusCellHighlightBorder");
        objConCel.borFoc=new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 128));
    }
    
    /**
     * Esta función obtiene el componente usado para dibujar la celda. Este método es
     * usado para configurar el render apropiado antes de dibujar la celda.
     * @return El componente mostrado en la celda.
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        //Guardar la fila y columna renderizada.
        intFilRen=row;
        intColRen=column;
        //Generar evento "beforeRender()".
        fireTblCelRenListener(new ZafTblCelRenEvent(this), INT_BEF_REN);
        java.awt.Color colAux;
        //Establecer el color de segundo plano adecuado.
        if (isSelected)
        {
            colAux=objConCel.colSegPla;
            this.setBackground(objConCel.colSelSeg);
            objConCel.colSegPla=colAux;
        }
        else
        {
            this.setBackground(objConCel.colSegPla);
        }
        //Establecer el borde de la celda adecuado.
        if (hasFocus)
        {
            this.setBorder(objConCel.borFoc);
        }
        else 
        {
            this.setBorder(objConCel.borNor);
        }
        //Establecer el valor de la celda.
        if (value instanceof Boolean)
        {
            this.setSelected(((Boolean)value).booleanValue());
        }
        else
        {
            this.setSelected(false);
        }
        this.setFont(table.getFont());
        colAux=null;
        return this;
    }

    /**
     * Esta función adiciona el listener que controlará los eventos de renderización.
     * @param listener El objeto que implementa los métodos de la interface "ZafTblCelRenListener".
     */
    public void addTblCelRenListener(ZafTblCelRenListener listener)
    {
        objEveLisLis.add(ZafTblCelRenListener.class, listener);
    }

    /**
     * Esta función remueve el listener que controlará los eventos de renderización.
     * @param listener El objeto que implementa los métodos de la interface "ZafTblCelRenListener".
     */
    public void removeTblCelRenListener(ZafTblCelRenListener listener)
    {
        objEveLisLis.remove(ZafTblCelRenListener.class, listener);
    }

    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTblCelRenEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_REN</TD><TD>Invoca al métod "beforeRender" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_REN</TD><TD>Invoca al métod "afterRender" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTblCelRenListener(ZafTblCelRenEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTblCelRenListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_REN:
                        ((ZafTblCelRenListener)obj[i+1]).beforeRender(evt);
                        break;
                }
            }
        }
    }

    /**
     * Esta función obtiene la fila a renderizar.
     * @return La fila a renderizar.
     */
    public int getRowRender()
    {
        return intFilRen;
    }

    /**
     * Esta función obtiene la columna a renderizar.
     * @return La columna a renderizar.
     */
    public int getColumnRender()
    {
        return intColRen;
    }

    /**
     * Esta función establece el color de segundo plano de la celda.
     * @param color El color a aplicar a la celda.
     */
    public void setBackground(java.awt.Color color)
    {
        super.setBackground(color);
        if (objConCel!=null)
            objConCel.colSegPla=color;
    }

    /**
     * Esta clase almacena la configuración de las celdas. Por ejemplo: colores de primer plano, de segundo plano,
     * colores de selección, bordes, etc..
     */
    private class ZafConCel
    {
        public java.awt.Color colPriPla;            //Color de primer plano.
        public java.awt.Color colSegPla;            //Color de segundo plano.
        public java.awt.Color colSelPri;            //Color de selección de primer plano.
        public java.awt.Color colSelSeg;            //Color de selección de segundo plano.
        public javax.swing.border.Border borNor;    //Borde normal.
        public javax.swing.border.Border borFoc;    //Borde con foco.
    }
    
    /**
     * Esta función actualiza la interface gráfica.
     * Notifica al <code>UIManager</code> que el look and feel [L&F] a cambiado.
     */
    @Override
    public void updateUI()
    {
        super.updateUI();
        setForeground(null);
        setBackground(null);
    }
    
}
