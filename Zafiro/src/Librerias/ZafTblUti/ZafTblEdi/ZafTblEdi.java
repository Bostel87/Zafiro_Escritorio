/*
 * ZafTblEdi.java
 *
 * Created on 26 de julio de 2005, 08:28 AM
 * v0.2
 */

package Librerias.ZafTblUti.ZafTblEdi;

/**
 * Esta clase crea un editor para el JTable proporcionandole las siguientes funcionalidades:
 * <UL>
 * <LI>Al presionar la tecla ENTER el foco se desplaza a la siguiente celda de la misma fila
 * y no a la siguiente celda de la misma columna que es lo común.
 * <LI>Inserta una fila en blanco cuando se escribe algo o se presiona ENTER en la última fila.
 * <LI>Elimina la fila seleccionada cuando la fila está vacía y se presiona la tecla DELETE o ESCAPE.
 * </UL>
 * @author  Eddye Lino
 */
public class ZafTblEdi
{
    private javax.swing.JTable tblDat;
    
    /** Crea una nueva instancia de la clase ZafTblEdi. */
    public ZafTblEdi(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        tblDat.getModel().addTableModelListener(new javax.swing.event.TableModelListener() {
            public void tableChanged(javax.swing.event.TableModelEvent evt) {
                tblDatTableChanged(evt);
            }
        });
    }
    
    /**
     * Esta función maneja los eventos de teclado para hacer algo al presionar ENTER, DELETE o SUPRIMIR.
     */
    private void tblDatKeyPressed(java.awt.event.KeyEvent evt)
    {
        int intFilSel, intNumFil;
        Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
        if (!evt.isActionKey())
        {
            //Inicializar variables.
            intFilSel=tblDat.getSelectedRow();
            intNumFil=tblDat.getRowCount();
            objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
            switch (evt.getKeyCode())
            {
                case java.awt.event.KeyEvent.VK_ENTER:
                    //Convierto el ENTER en un TAB para que el foco pase a la siguiente celda de la misma fila y no a la siguiente celda de la misma columna.
                    evt.setKeyCode(java.awt.event.KeyEvent.VK_TAB);
                    if ( (objTblMod.getModoOperacion()==objTblMod.INT_TBL_INS) && (intFilSel==(intNumFil-1)) )
                    {
                        objTblMod.insertRow();
                    }
                    break;
                case java.awt.event.KeyEvent.VK_ESCAPE:
                case java.awt.event.KeyEvent.VK_DELETE:
                    //Si es la última fila evaluo si está vacía para eliminarla.
                    if (intFilSel!=(intNumFil-1))
                    {
                        if (objTblMod.isRowEmpty(intFilSel))
                        {
                            objTblMod.removeRow(intFilSel);
                            tblDat.setRowSelectionInterval(intFilSel, intFilSel);
                        }
                    }
                    break;
            }
        }
        objTblMod=null;
    }
    
    /**
     * Esta función inserta una fila al final cuando se detecta un cambio en el
     * contenido de alguna de las celdas de la última fila.
     */
    private void tblDatTableChanged(javax.swing.event.TableModelEvent e)
    {
        int intFilSel, intNumFil;
        Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
        //Inicializar variables.
        intFilSel=tblDat.getSelectedRow();
        intNumFil=tblDat.getRowCount();
        objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
        if ( (objTblMod.getModoOperacion()==objTblMod.INT_TBL_INS) && (intFilSel==(intNumFil-1)) )
            if (!objTblMod.isRowEmpty(intFilSel))
                objTblMod.insertRow();
        objTblMod=null;
    }
    
    /**
     * Esta función selecciona la celda que se encuentra en la columna especificada.
     * @param columna Columna que se desea seleccionar.
     * @return true: Si se pudo seleccionar la celda.
     * <BR>false: En el caso contrario.
     */
    public boolean seleccionarCelda(final int columna)
    {
        boolean blnRes=true;
        try
        {
            //Ubicar el foco en la columna "Código". Sin el "invokeLater" no funciona.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    tblDat.setColumnSelectionInterval(columna, columna);
                }
            });
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función selecciona la celda que se encuentra en la fila y columna especificada.
     * @param fila Fila que se desea seleccionar.
     * @param columna Columna que se desea seleccionar.
     * @return true: Si se pudo seleccionar la celda.
     * <BR>false: En el caso contrario.
     */
    public boolean seleccionarCelda(final int fila, final int columna)
    {
        boolean blnRes=true;
        try
        {
            //Ubicar el foco en la columna "Código". Sin el "invokeLater" no funciona.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run()
                {
                    tblDat.setRowSelectionInterval(fila, fila);
                    tblDat.setColumnSelectionInterval(columna, columna);
                    tblDat.changeSelection(fila, columna, true, true);
                }
            });
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
}
