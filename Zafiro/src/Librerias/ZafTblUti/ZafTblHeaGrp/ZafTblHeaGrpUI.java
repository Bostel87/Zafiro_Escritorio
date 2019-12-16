/*
 * ZafTblHeaGrpUI.java
 *
 * Created on 23 de noviembre de 2007, 11:15 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblHeaGrp;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Dimension;
import java.util.Hashtable;
import java.util.Enumeration;

/**
 * Esta clase se encarga de redibujar la cabecera de las columnas para que aparezcan agrupadas.
 * @author  Eddye Lino
 */
public class ZafTblHeaGrpUI extends javax.swing.plaf.basic.BasicTableHeaderUI
{
    javax.swing.JLabel lblHea=new javax.swing.JLabel();
    
    public void paint(java.awt.Graphics g, javax.swing.JComponent c)
    {
        int intCol, intColMar, intGrpHei;
        Rectangle recCliBou, recCelRec, recGrpRec;
        Enumeration enu, enuGrpCol;
        Dimension dimSiz, dimGrpSiz;
        TableColumn tbc;
        ZafTblHeaColGrp objTblHeaColGrp;
        recCliBou=g.getClipBounds();
        if (header.getColumnModel()==null)
            return;
        ((ZafTblHeaGrp)header).setColumnMargin();
        intCol=0;
        dimSiz=header.getSize();
        recCelRec=new Rectangle(0, 0, dimSiz.width, dimSiz.height);
        Hashtable h=new Hashtable();
        intColMar=header.getColumnModel().getColumnMargin();
        enu=header.getColumnModel().getColumns();
        while (enu.hasMoreElements())
        {
            recCelRec.height=dimSiz.height;
            recCelRec.y=0;
            tbc=(TableColumn)enu.nextElement();
            enuGrpCol=((ZafTblHeaGrp)header).getColumnGroups(tbc);
            if (enuGrpCol!=null)
            {
                intGrpHei=0;
                while (enuGrpCol.hasMoreElements())
                {
                    objTblHeaColGrp=(ZafTblHeaColGrp)enuGrpCol.nextElement();
                    recGrpRec=(Rectangle)h.get(objTblHeaColGrp);
                    if (recGrpRec==null)
                    {
                        recGrpRec=new Rectangle(recCelRec);
                        dimGrpSiz=objTblHeaColGrp.getSize(header.getTable());
                        recGrpRec.width=dimGrpSiz.width;
                        recGrpRec.height=objTblHeaColGrp.getHeight();
                        h.put(objTblHeaColGrp, recGrpRec);
                    }
                    paintCell(g, recGrpRec, objTblHeaColGrp);
                    intGrpHei+=recGrpRec.height;
                    recCelRec.height=dimSiz.height-intGrpHei;
                    recCelRec.y=intGrpHei;
                }
            }
            recCelRec.width=tbc.getWidth() + intColMar - 1;
            if (recCelRec.intersects(recCliBou))
            {
                paintCell(g, recCelRec, intCol);
            }
            recCelRec.x+=recCelRec.width;
            intCol++;
        }
    }

    private void paintCell(java.awt.Graphics g, Rectangle cellRect, int columnIndex)
    {
        TableColumn tbc=header.getColumnModel().getColumn(columnIndex);
        TableCellRenderer tcr=tbc.getHeaderRenderer();
        tcr=new DefaultTableCellRenderer()
        {
            public Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                lblHea.setForeground(table.getTableHeader().getForeground());
                lblHea.setBackground(table.getTableHeader().getBackground());
                lblHea.setFont(table.getTableHeader().getFont());
                lblHea.setHorizontalAlignment(javax.swing.JLabel.CENTER);
                lblHea.setVerticalAlignment(javax.swing.JLabel.TOP);
                lblHea.setText(value.toString());
                lblHea.setBorder(javax.swing.UIManager.getBorder("TableHeader.cellBorder"));
                return lblHea;
            }
        };
        Component objCom=tcr.getTableCellRendererComponent(header.getTable(), tbc.getHeaderValue(), false, false, -1, columnIndex);
        objCom.setBackground(javax.swing.UIManager.getColor("control"));
        rendererPane.add(objCom);
        rendererPane.paintComponent(g, objCom, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    private void paintCell(java.awt.Graphics g, Rectangle cellRect,ZafTblHeaColGrp cGroup)
    {
        TableCellRenderer tcr=cGroup.getHeaderRenderer();
        Component objCom=tcr.getTableCellRendererComponent(header.getTable(), cGroup.getHeaderValue(),false, false, -1, -1);
        rendererPane.add(objCom);
        rendererPane.paintComponent(g, objCom, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
    }

    private int getHeaderHeight()
    {
        int intAlt, intAltCel, intCol;
        TableColumn tbc;
        TableCellRenderer tcr;
        Component objCom;
        Enumeration enu;
        ZafTblHeaColGrp objTblHeaColGrp;
        intAlt=0;
        TableColumnModel tcm=header.getColumnModel();
        for (intCol=0; intCol<tcm.getColumnCount(); intCol++)
        {
            tbc=tcm.getColumn(intCol);
            tcr=tbc.getHeaderRenderer();
            if(tcr==null)
            {
                return ((ZafTblHeaGrp)header).getHeight();
            }
            objCom=tcr.getTableCellRendererComponent(header.getTable(), tbc.getHeaderValue(), false, false,-1, intCol);
            intAltCel=objCom.getPreferredSize().height;
            enu=((ZafTblHeaGrp)header).getColumnGroups(tbc);
            if (enu!=null)
            {
                while (enu.hasMoreElements())
                {
                    objTblHeaColGrp=(ZafTblHeaColGrp)enu.nextElement();
                    intAltCel+=objTblHeaColGrp.getSize(header.getTable()).height;
                }
            }
            intAlt=Math.max(intAlt, intAltCel);
        }
        return intAlt;
    }

    private Dimension createHeaderSize(long width)
    {
        TableColumnModel tcm=header.getColumnModel();
        width+=tcm.getColumnMargin() * tcm.getColumnCount();
        if (width>Integer.MAX_VALUE)
        {
            width=Integer.MAX_VALUE;
        }
        return new Dimension((int)width, getHeaderHeight());
    }

    public Dimension getPreferredSize(javax.swing.JComponent c)
    {
        long lngAnc=0;
        TableColumn tbc;
        Enumeration enu=header.getColumnModel().getColumns();
        while (enu.hasMoreElements())
        {
            tbc=(TableColumn)enu.nextElement();
            lngAnc=lngAnc + tbc.getPreferredWidth();
        }
        return createHeaderSize(lngAnc);
    }

}
