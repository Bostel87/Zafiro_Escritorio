/*
 * ZafTblCelRenLbl.java
 *
 * Created on 26 de junio de 2005, 10:49 PM
 * v0.4
 */

package Librerias.ZafTblUti.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent;
import javax.swing.event.EventListenerList;
import Librerias.ZafUtil.ZafUtil;

/**
 * Esta clase establece el componente a mostrar en la celda basada en el uso de la clase "DefaultTableCellRenderer".
 * La clase JTable utiliza renderizadores para presentar los datos en una celda. Por lo general se utiliza
 * una etiqueta como renderizador. Pero se puede utilizar cualquier clase para renderizar la celda (JButton,
 * JComboBox, JTextField, etc). La presente clase utiliza como renderizador la clase JLabel a la cual se le
 * puede aplicar diferentes formatos (formatear fechas, formatear números, color de fuente, color de fondo, etc).
 * Ejemplo, un usuario puede ver en pantalla que una celda muestra "156.46" cuando en realidad la celda tiene
 * "156.4584".
 * <BR><BR>Ejemplo de como utilizar los listener:
 * <BR>"objTblCelRenLbl" es la instancia de "ZafTblCelRenLbl".
 * <PRE>
 *           objTblCelRenLbl.addTblCelRenListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenAdapter() {
 *               public void beforeRender(Librerias.ZafTblUti.ZafTblEvt.ZafTblCelRenEvent evt) {
 *                   //Mostrar de color gris las columnas impares.
 *                   if (objTblCelRenLbl.getRowRender()%2==0)
 *                   {
 *                       objTblCelRenLbl.setBackground(java.awt.Color.WHITE);
 *                   }
 *                   else
 *                   {
 *                       objTblCelRenLbl.setBackground(java.awt.Color.LIGHT_GRAY);
 *                       objTblCelRenLbl.setFont(new java.awt.Font(objTblCelRenLbl.getFont().getFontName(), java.awt.Font.BOLD, objTblCelRenLbl.getFont().getSize()));
 *                   }
 *                   //Mostrar ToolTipText con el contenido de la celda.
 *                   objTblCelRenLbl.setToolTipText(objTblCelRenLbl.getText());
 *               }
 *           });
 * </PRE>
 * Nota.- No es necesario implementar todos los métodos. Sólo se debe implementar los métodos que en realidad necesita.
 * <BR>Esta clase sólo hace uso del método "beforeRender".
 * @author  Eddye Lino
 */
public class ZafTblCelRenLbl extends javax.swing.JLabel implements javax.swing.table.TableCellRenderer
{
    //Constantes:
    public static final int INT_FOR_GEN=0;                      /**Un valor para setTipoFormato: Indica "Formato general".*/
    public static final int INT_FOR_NUM=1;                      /**Un valor para setTipoFormato: Indica "Formato numérico".*/
    public static final int INT_FOR_FEC=2;                      /**Un valor para setTipoFormato: Indica "Formato de fechas".*/
    private static final int INT_BEF_REN=0;                     /**Antes de editar: Indica "Before render".*/
    //Variables:
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private int intTipFor;                                      //Tipo de formato de celda.
    private String strForCel;                                   //Formato de la celda.
    private boolean blnMosCer;                                  //Mostrar cero.
    private boolean blnNegRoj;                                  //Mostrar negativos en rojo.
    private ZafUtil objUti;                                     //Utilidades.
    private ZafConCel objConCel;                                //Configuración de la celda.
    private int intFilRen;                                      //Fila a renderizar.
    private int intColRen;                                      //Columna a renderizar.
    protected EventListenerList objEveLisLis=new EventListenerList();

    
    /** Crea una nueva instancia de la clase ZafTblCelRenLbl. */
    public ZafTblCelRenLbl()
    {
        //Configurar la etiqueta que renderizará la celda.
        this.setOpaque(true);
        this.setBorder(new javax.swing.border.EmptyBorder(1,1,1,1));
        //Obtener la configuración de la celda.
        objConCel=new ZafConCel();
        objConCel.colPriPla=javax.swing.UIManager.getColor("Table.foreground");
        objConCel.colSegPla=javax.swing.UIManager.getColor("Table.background");
        objConCel.colSelPri=javax.swing.UIManager.getColor("Table.selectionForeground");
        objConCel.colSelSeg=javax.swing.UIManager.getColor("Table.selectionBackground");
        objConCel.borNor=new javax.swing.border.EmptyBorder(1,1,1,1);
//        objConCel.borFoc=javax.swing.UIManager.getBorder("Table.focusCellHighlightBorder");
        objConCel.borFoc=new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 128));
        //Establecer la configuración inicial de la clase.
        intTipFor=INT_FOR_GEN;
        strForCel="";
        blnMosCer=true;
        blnNegRoj=false;
        objUti=new ZafUtil();
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
        String strAux;
        strAux=(value==null)?"":value.toString();
        //Establecer el color de segundo plano adecuado.
        if (isSelected)
        {
            colAux=objConCel.colSegPla;
            this.setBackground(objConCel.colSelSeg);
            objConCel.colSegPla=colAux;
        }
        else
        {
            //Validar que la fila esté completa.
            objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)table.getModel();
            if (objTblMod.isRowComplete(row))
            {
                this.setBackground(objConCel.colSegPla);
            }
            else
            {
                colAux=objConCel.colSegPla;
                this.setBackground(objTblMod.getBackgroundIncompleteRows());
                objConCel.colSegPla=colAux;
            }
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
        //Establecer el tipo de formato de la celda.
        switch (intTipFor)
        {
            case INT_FOR_GEN:
                break;
            case INT_FOR_NUM:
                if (objUti.isNumero(strAux))
                {
                    strAux=objUti.formatearNumero(strAux,strForCel,blnMosCer);
                    //Cambiar el color de los números negativos a color rojo.
                    if ( (Double.parseDouble(value.toString())<0) && (blnNegRoj==true) )
                    {
                        colAux=objConCel.colPriPla;
                        this.setForeground(java.awt.Color.red);
                        objConCel.colPriPla=colAux;
                    }
                    else
                    {
                        this.setForeground(objConCel.colPriPla);
                    }
                }
                break;
            case INT_FOR_FEC:
                break;
        }
        this.setFont(table.getFont());
        this.setText(strAux);
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
     * Esta función establece el color de primer plano de la celda.
     * @param color El color a aplicar a la celda.
     */
    public void setForeground(java.awt.Color color)
    {
        super.setForeground(color);
        if (objConCel!=null)
            objConCel.colPriPla=color;
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
     * Esta función obtiene el tipo de formato aplicado a la celda. Puede retornar
     * los siguientes valores:
     * <UL>
     * <LI>0: Formato general (INT_FOR_GEN).
     * <LI>1: Formato numérico (INT_FOR_NUM).
     * <LI>2: Formato de fechas (INT_FOR_FEC).
     * </UL>
     * <BR>Nota.- La opción predeterminada es formato general (INT_FOR_GEN).
     * @return El tipo de formato aplicado a la celda.
     */
    public int getTipoFormato()
    {
        return intTipFor;
    }
    
    /**
     * Esta función establece el tipo de formato de la celda. Una celda puede tener un formato
     * general, un formato para los números y otro para las fechas. Por ejemplo, una celda puede
     * tener "2005/07/25" como formato general. Pero se le puede aplicar un formato de fechas y
     * mostraría "25/Jul/2005" si el patrón aplicado es "dd/MMM/yyyy". Puede tomar
     * los siguientes valores:
     * <UL>
     * <LI>0: Formato general (INT_FOR_GEN).
     * <LI>1: Formato numérico (INT_FOR_NUM).
     * <LI>2: Formato de fechas (INT_FOR_FEC).
     * </UL>
     * <BR>Nota.- La opción predeterminada es formato general (INT_FOR_GEN).
     * @param tipoFormato El tipo de formato de la celda.
     */
    public void setTipoFormato(int tipoFormato)
    {
        intTipFor=tipoFormato;
    }

    /**
     * Esta función obtiene el formato numérico aplicado a la celda.
     * @return La cadena que contiene el formato numérico. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     */
    public String getFormatoNumerico()
    {
        return strForCel;
    }
    
    /**
     * Esta función establece el formato numérico aplicado a la celda.
     * @param formato El formato que se debe aplicar a la celda. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     */
    public void setFormatoNumerico(String formato)
    {
        strForCel=formato;
    }
    
    /**
     * Esta función establece el formato numérico aplicado a la celda.
     * @param formato El formato que se debe aplicar a la celda. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param mostrarCero Se aplica si el número a formatear es un cero.
     * Puede tomar los siguientes valores:
     * <BR>true: Se mostrará el cero formateado.
     * <BR>false: Se mostrará una cadena vacía.
     */
    public void setFormatoNumerico(String formato, boolean mostrarCero)
    {
        strForCel=formato;
        blnMosCer=mostrarCero;
    }
    
    /**
     * Esta función establece el formato numérico aplicado a la celda.
     * @param formato El formato que se debe aplicar a la celda. Podría ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param mostrarCero Se aplica si el número a formatear es un cero.
     * Puede tomar los siguientes valores:
     * <BR>true: Se mostrará el cero formateado.
     * <BR>false: Se mostrará una cadena vacía.
     * @param negativoRojo Se aplica si el número a formatear es negativo.
     * Puede tomar los siguientes valores:
     * <BR>true: Se mostrará el número de color rojo.
     * <BR>false: Se mostrará el número de color normal.
     */
    public void setFormatoNumerico(String formato, boolean mostrarCero, boolean negativoRojo)
    {
        strForCel=formato;
        blnMosCer=mostrarCero;
        blnNegRoj=negativoRojo;
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
