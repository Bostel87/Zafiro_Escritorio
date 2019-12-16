/*
 * ZafTblBus.java
 *
 * Created on 7 de mayo de 2005, 11:39 PM
 * v0.2
 */
package Librerias.ZafTblUti.ZafTblBus;

/**
 * Esta clase crea un editor de búsqueda para el JTable. La búsqueda se realiza en todas las
 * celdas de la columna seleccionada empezando desde la fila seleccionada.
 * Se pueden presentar los siguientes casos:
 * <UL>
 * <LI>Si la cadena buscada no se encuentra se inicializa la cadena de búsqueda.
 * <LI>Si la cadena buscada se encuentra se ubica el foco en la celda donde fue encontrada.
 * <LI>Si se presiona la tecla ESCAPE o se cambia de celda se inicializa la cadena de búsqueda.
 * </UL>
 * @author  Eddye Lino
 */
public class ZafTblBus
{
    private javax.swing.JTable tblDat;                          //JTable donde se va a buscar.
    private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
    private String strCadBus="";                                //Cadena que se va a buscar en las celdas de la columna seleccionada en el JTable.
    private int intFilCad, intColCad;                           //Fila y columna donde se encontró la cadena buscada.
    
    /** Crea una nueva instancia de la clase ZafTblBus.*/
    public ZafTblBus(javax.swing.JTable obj)
    {
        tblDat=obj;
        tblDat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblDatKeyPressed(evt);
            }
        });
        objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
    }
    
    private void tblDatKeyPressed(java.awt.event.KeyEvent evt)
    {
        if ((evt.getKeyChar()==evt.CHAR_UNDEFINED) || (evt.getKeyCode()==evt.VK_ESCAPE) || (evt.getKeyCode()==evt.VK_ENTER) || (evt.getKeyCode()==0))
        {
            strCadBus="";
        }
        else
        {
            if (!objTblMod.isColumnaEditable(tblDat.getSelectedColumn()))
            {
                //Si la celda actual es diferente a la celda donde se encontró la cadena biscada se limpia la cadena de búsqueda.
                if (intFilCad!=tblDat.getSelectedRow() || intColCad!=tblDat.getSelectedColumn())
                    strCadBus="";
                strCadBus+=evt.getKeyChar();
                buscarCad(strCadBus);
            }
        }
    }
    
    /**
     * Esta función busca la cadena que recibe como argumento y selecciona la fila donde
     * se encontró la cadena. La idea es que el usuario se ubique en una de las celdas de
     * la columna donde desea buscar la cadena y comience a digitar la cadena que desea 
     * buscar.
     * @param strCad La cadena que se va a buscar.
     */
    private void buscarCad(String strCad)
    {
        boolean blnCadExi=false;
        int intFil, intCol, i;
        String strCadCel;
        intFil=tblDat.getSelectedRow();
        intCol=tblDat.getSelectedColumn();
        //Buscar la cadena desde la fila seleccionada hacia abajo.
        for (i=intFil;i<tblDat.getRowCount();i++)
        {
            strCadCel=(tblDat.getValueAt(i,intCol)==null)?"":tblDat.getValueAt(i,intCol).toString();
            if (strCadCel.toLowerCase().startsWith(strCad))
            {
                //Seleccionar la fila donde se encontró el valor buscado.
                tblDat.setRowSelectionInterval(i, i);
                //Ubicar el foco en la fila seleccionada.
                tblDat.changeSelection(i, intCol, true, true);
                //Guardar la posición donde se encontró la cadena.
                intFilCad=i;
                intColCad=intCol;
                blnCadExi=true;
                break;
            }
        }
        //Si la cadena no se encontró desde la fila seleccionada se busca desde la primera fila.
        if (!blnCadExi)
        {
            for (i=0;i<tblDat.getRowCount();i++)
            {
                strCadCel=(tblDat.getValueAt(i,intCol)==null)?"":tblDat.getValueAt(i,intCol).toString();
                if (strCadCel.toLowerCase().startsWith(strCad))
                {
                    //Seleccionar la fila donde se encontró la cadena buscada.
                    tblDat.setRowSelectionInterval(i, i);
                    //Ubicar el foco en la fila seleccionada.
                    tblDat.changeSelection(i, intCol, true, true);
                    //Guardar la posición donde se encontró la cadena.
                    intFilCad=i;
                    intColCad=intCol;
                    blnCadExi=true;
                    break;
                }
            }
            //Inicializar la cadena de búsqueda si la cadena no existe.
            if (!blnCadExi)
                strCadBus="";
        }
    }

}
