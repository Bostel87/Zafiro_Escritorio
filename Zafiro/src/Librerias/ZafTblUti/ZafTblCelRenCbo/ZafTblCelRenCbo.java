/*
 * ZafTblCelRenCmbBox.java
 *
 * Created on 29 de junio de 2005, 08:08 AM
 * modificado v0.2 al 07/Oct/2010
 */
package Librerias.ZafTblUti.ZafTblCelRenCbo;

import java.awt.Color;
import java.awt.Component;
import javax.swing.table.TableCellRenderer;

/**
 * Esta clase establece el componente a mostrar en la celda basada en el uso de la interface "TableCellRenderer".
 * La clase JTable utiliza renderizadores para presentar los datos en una celda. Por lo general se utiliza
 * una etiqueta como renderizador. Pero se puede utilizar cualquier clase para renderizar la celda (JButton,
 * JComboBox, JTextField, etc). La presente clase utiliza como renderizador la clase JCheckBox con lo cual en
 * la celda se mostrará una casilla de verificación en vez de mostrar la celda como comúnmente se presenta.
 * <BR>Nota.- Los renderizadores sólo se encargan de la presentación. Para que la casilla de verificación haga algo
 * se debe utilizar editores (javax.swing.table.TableCellEditor).
 * @author  Eddye Lino
 */
public class ZafTblCelRenCbo extends javax.swing.JComboBox implements javax.swing.table.TableCellRenderer
{
    //Variables:
    private ZafConCel objConCel;                //Configuración de la celda.
    
    public ZafTblCelRenCbo(){
//        super();
//        setOpaque(true);
        this.setOpaque(true);
        //this.setSelectedIndex(0);
        objConCel=new ZafConCel();
        objConCel.colPriPla=javax.swing.UIManager.getColor("Table.foreground");
        objConCel.colSegPla=javax.swing.UIManager.getColor("Table.background");
        objConCel.colSelPri=javax.swing.UIManager.getColor("Table.selectionForeground");
        objConCel.colSelSeg=javax.swing.UIManager.getColor("Table.selectionBackground");
        objConCel.borNor=new javax.swing.border.EmptyBorder(1,1,1,1);
        objConCel.borFoc=new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 128));
    }
    
    
    
    /**
     * Esta función obtiene el componente usado para dibujar la celda. Este método es
     * usado para configurar el render apropiado antes de dibujar la celda.
     * @return El componente mostrado en la celda.
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        java.awt.Color colAux;
	if (row == (-1) && column == (-1) ) {
            this.setBackground(objConCel.colSegPla);
        }
	else
            if (isSelected) {
//                setForeground(table.getSelectionForeground());
//                super.setBackground(table.getSelectionBackground());

                colAux=objConCel.colSegPla;
                this.setBackground(objConCel.colSelSeg);
                objConCel.colSegPla=colAux;

            }
            else{
//                setForeground(table.getForeground());
//                setBackground(table.getBackground());
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


     	setBorder(null);
        removeAllItems();
        addItem( value );

        //Establecer el valor de la celda.
        if (value instanceof Integer)
        {
            this.setSelectedItem(((Integer)value));
        }
        else
        {
            this.setSelectedIndex(0);
        }
        this.setFont(table.getFont());
        colAux=null;


        return this;

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
    
}
