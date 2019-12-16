/*
 * ZafTblColGrp.java
 *
 * Created on 23 de noviembre de 2007, 11:49 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblHeaGrp;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.Component;
import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Esta clase contiene las columnas que son agrupadas bajo un grupo determinado.
 * Por ejemplo:
 *           ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("América del Sur");
 *           objTblHeaColGrpAmeSur.setHeight(16);
 *           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_ECU));
 *           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COL));
 *           objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VEN));
 * @author  Eddye Lino
 */
public class ZafTblHeaColGrp
{
    //Variables:
    protected TableCellRenderer tcr;                            //Renderizador utilizado en el ZafTblHeaColGrp.
    protected String strTxt;                                    //Texto presentado en el ZafTblHeaColGrp.
    protected Vector vec;                                       //Almacena los objetos agrupados en el ZafTblHeaColGrp.
    protected int intMar;                                       //Margen.
    private int intAltHeaColGrp;                                //Alto del ZafTblHeaColGrp. 
    
    /**
     * Este constructor crea una instancia de la clase ZafTblHeaColGrp.
     * @param text El texto que aparecerá en la columna grupo.
     */
    public ZafTblHeaColGrp(String text)
    {
        this(null, text);
    }

    /**
     * Este constructor crea una instancia de la clase ZafTblHeaColGrp.
     * @param renderer El renderizador que se debe utilizar.
     * @param text El texto que aparecerá en la columna grupo.
     */
    public ZafTblHeaColGrp(TableCellRenderer renderer, String text)
    {
        intMar=0;
        if (renderer==null)
        {
            tcr=new DefaultTableCellRenderer() {
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
                {
                    JTableHeader header=table.getTableHeader();
                    if (header!=null)
                    {
                        setForeground(header.getForeground());
                        setBackground(header.getBackground());
                        setFont(header.getFont());
                    }
                    setHorizontalAlignment(JLabel.CENTER);
                    setText((value==null)?"":value.toString());
                    setBorder(UIManager.getBorder("TableHeader.cellBorder"));
                    return this;
                }
            };
        }
        else
        {
            tcr=renderer;
        }
        strTxt=text;
        vec=new Vector();
        //Inicializar la variable que almacena el alto de la celda grupo.
        intAltHeaColGrp=16;
    }

    /**
     * Esta función agrega los objetos que se deben agrupar en el ZafTblHeaColGrp.
     * @param obj El objeto a agrupar.
     * <BR>Ejemplos:
     * <PRE>
     * Recibe un objeto del tipo "TableColumn" cuando se va a agrupar columnas del JTable.
     *     ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("América del Sur");
     *     objTblHeaColGrpAmeSur.setHeight(16);
     *     objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_ECU));
     *     objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COL));
     *     objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VEN));
     * Recibe un objeto del tipo "ZafTblHeaColGrp" cuando se va a agrupar grupos de columnas.
     *     objTblHeaColGrpPlaTie.add(objTblHeaColGrpAmeSur);
     *     objTblHeaColGrpPlaTie.add(objTblHeaColGrpAmeNor);
     *     objTblHeaColGrpPlaTie.add(objTblHeaColGrpEur);
     * Nota.- objTblHeaColGrpAmeSur, objTblHeaColGrpAmeNor y objTblHeaColGrpEur son grupos de columnas.
     * </PRE>
     */
    public void add(Object obj)
    {
        if (obj==null)
        {
            return;
        }
        vec.addElement(obj);
    }

    /**
     * Esta función obtiene el grupo al que pertenece la columna especificada.
     * @param c El objeto TableColumn.
     * @param g El vector.
     * @return Un vector con los objetos agrupados.
     */
    public Vector getColumnGroups(TableColumn c, Vector g)
    {
        Object obj;
        Vector vecGrp;
        Enumeration enu;
        g.addElement(this);
        if (vec.contains(c))
            return g;    
        enu=vec.elements();
        while (enu.hasMoreElements())
        {
            obj=enu.nextElement();
            if (obj instanceof ZafTblHeaColGrp)
            {
                vecGrp=(Vector)((ZafTblHeaColGrp)obj).getColumnGroups(c,(Vector)g.clone());
                if (vecGrp!=null)
                    return vecGrp;
            }
        }
        return null;
    }

    /**
     * Esta función obtiene el renderizador utilizado por el ZafTblHeaColGrp.
     * @return el renderizador utilizado por el ZafTblHeaColGrp.
     */
    public TableCellRenderer getHeaderRenderer()
    {
        return tcr;
    }

    /**
     * Esta función establece el renderizador que debe utilizar el ZafTblHeaColGrp.
     * @param El renderizador que debe utilizar el ZafTblHeaColGrp.
     */
    public void setHeaderRenderer(TableCellRenderer renderer)
    {
        if (renderer!=null)
        {
            tcr=renderer;
        }
    }

    /**
     * Esta función obtiene el texto que aparece en el ZafTblHeaColGrp.
     * @return El texto que aparece en el ZafTblHeaColGrp.
     */
    public Object getHeaderValue()
    {
        return strTxt;
    }

    /**
     * Esta función obtiene el tamaño del ZafTblHeaColGrp.
     * @param table El objeto JTable en el cual está ubicado el ZafTblHeaColGrp.
     * @return Un objeto del tipo Dimension que contiene las dimensiones del ZafTblHeaColGrp.
     */
    public Dimension getSize(JTable table)
    {
        int intAlt, intAnc;
        Object obj;
        TableColumn tbc;
        Enumeration enu;
        Component objCom=tcr.getTableCellRendererComponent(table, getHeaderValue(), false, false,-1, -1);
        intAlt=objCom.getPreferredSize().height;
        intAnc=0;
        enu=vec.elements();
        while (enu.hasMoreElements())
        {
            obj=enu.nextElement();
            if (obj instanceof TableColumn)
            {
                tbc=(TableColumn)obj;
                intAnc+=tbc.getWidth();
                intAnc+=intMar;
            }
            else
            {
                intAnc+=((ZafTblHeaColGrp)obj).getSize(table).width;
            }
        }
        return new Dimension(intAnc, intAlt);
    }

    /**
     * Esta función establece el margen de ZafTblHeaColGrp.
     * @param margin El margen a establecer.
     */
    public void setColumnMargin(int margin)
    {
        Object obj;
        intMar=margin;
        Enumeration enu;
        enu=vec.elements();
        while (enu.hasMoreElements())
        {
            obj=enu.nextElement();
            if (obj instanceof ZafTblHeaColGrp)
            {
                ((ZafTblHeaColGrp)obj).setColumnMargin(margin);
            }
        }
    }

    /**
     * Esta función obtiene la altura del ZafTblHeaColGrp.
     * @return La altura del ZafTblHeaColGrp.
     */
    public int getHeight()
    {
        return intAltHeaColGrp;
    }
    
    /**
     * Esta función establece la altura del ZafTblHeaColGrp.
     * @param height La altura del ZafTblHeaGrp.
     * <BR>El alto de cada fila es 16.
     */
    public void setHeight(int height)
    {
        intAltHeaColGrp=height;
    }
    
}
