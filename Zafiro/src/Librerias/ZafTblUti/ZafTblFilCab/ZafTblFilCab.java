/*
 * ZafTblFilCab.java
 *
 * Created on 25 de febrero de 2006, 22:23 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafUtil.ZafUtil;
import java.util.ArrayList;

/**
 * Esta clase permite crear una fila de cabecera. La clase se encarga de mostrar un JButton como renderizador.
 * Cada fila presenta un botón que contiene como etiqueta el número de fila respectivo.
 * <BR>Nota.- Los renderizadores sólo se encargan de la presentación. Para que el botón haga algo
 * se debe utilizar editores (javax.swing.table.TableCellEditor).
 * @author  Eddye Lino
 */
public class ZafTblFilCab extends javax.swing.JButton implements javax.swing.table.TableCellRenderer
{
    //Variables:
    private javax.swing.JTable tblDat;
    private ZafTblMod objTblMod;
    private ZafUtil objUti;
    
    /** Crea una nueva instancia de la clase ZafTblFilCab. */
    public ZafTblFilCab(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        objTblMod=(ZafTblMod)tblDat.getModel();
        objUti=new ZafUtil();
        this.setBorder(new javax.swing.border.EtchedBorder());
        this.setFont(new java.awt.Font(tblDat.getTableHeader().getFont().getFontName(), tblDat.getTableHeader().getFont().getStyle(), 11));
    }
    
    /**
     * Esta función obtiene el componente usado para dibujar la celda. Este método es
     * usado para configurar el render apropiado antes de dibujar la celda.
     * @return El componente mostrado en la celda.
     */
    public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        int i;
        boolean blnExiFil=false;
        ArrayList arlAux=objTblMod.getRowHeaderRaise();
        for (i=0;i<arlAux.size();i++)
        {
            if (objUti.getIntValueAt(arlAux,i,0)==row)
            {
                blnExiFil=true;
                break;
            }
            else
            {
                blnExiFil=false;
            }
        }
        if (blnExiFil)
        {
            this.setForeground(java.awt.Color.RED);
            this.setToolTipText(objUti.getStringValueAt(arlAux,i,1));
        }
        else
        {
            this.setForeground(java.awt.Color.BLACK);
            this.setToolTipText(null);
        }
        arlAux=null;
        this.setText("" + (row+1));
        return this;
    }

}
