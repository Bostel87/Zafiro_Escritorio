/*
 * ZafTblTot.java
 *
 * Created on 08 de enero de 2006, 07:56 PM
 * v0.5
 */

package Librerias.ZafTblUti.ZafTblTot;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafTblUti.ZafTblFilCab.ZafTblFilCab;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl;
import Librerias.ZafTblUti.ZafTblPopMnu.ZafTblPopMnu;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.util.ArrayList;

/**
 * Esta clase permite configurar un JTable de totales. Se configura lo siguiente:
 * <UL>
 * <LI>Al redimensionar una columna se redimensiona la columna respectiva en el JTable de totales.</LI>
 * <LI>Al hacer un desplazamiento ya sea en el JTable de datos o en el JTable de totales se hace un
 * desplazamiento en el otro JTable.</LI>
 * <LI>Permite calcular los totales de las columnas especificadas.</LI>
 * </UL>
 * @author  Eddye Lino
 */
public class ZafTblTot
{
    //Variables.
    private ZafUtil objUti;
    private ZafTblFilCab objTblFilCab;
    private ZafTblMod objTblModDat, objTblModTot;               //Modelo del JTable.
    private ZafTblCelRenLbl objTblCelRenLbl;                    //Render: Presentar JLabel en JTable.
    private ZafTblPopMnu objTblPopMnu;                          //PopupMenu: Establecer PopupMenú en JTable.
    //Variables de la clase.
    private JTable tblDat, tblTot;
    private JScrollBar barHorDat, barHorTot, barVerDat;
    private ArrayList<Integer> arlColTot;                       //Columnas de totales.
    private String strForNum;                                   //Formato que se utilizará para mostrar los números.
    private int intNumTotFil;                                   //Número total de filas que tiene la tabla.
    private int intNumDec;

    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnasTotales[])
    {
        this(panelDatos, panelTotales, datos, totales, pasarArrayToArrayList(columnasTotales), null, 1);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param formato El formato que se utilizará para mostrar los números en el JTable de totales.
     * Ejemplos: ###,###,###.##, ###,###,##0.00, etc.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> se utilizará el formato "###,###,##0.00".
     * <LI>Si el parámetro recibido es diferente a <I>null</I> se utilizará el formato recibido.
     * </UL>
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnasTotales[], String formato)
    {
        this(panelDatos, panelTotales, datos, totales, pasarArrayToArrayList(columnasTotales), formato, 1);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param filas El número de filas que se presentaran en el JTable de totales.
     * De forma predetermina se presenta 1 fila pero de ser necesario se pueden presentar más filas.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnasTotales[], int filas)
    {
        this(panelDatos, panelTotales, datos, totales, pasarArrayToArrayList(columnasTotales), null, filas);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param formato El formato que se utilizará para mostrar los números en el JTable de totales.
     * Ejemplos: ###,###,###.##, ###,###,##0.00, etc.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> se utilizará el formato "###,###,##0.00".
     * <LI>Si el parámetro recibido es diferente a <I>null</I> se utilizará el formato recibido.
     * </UL>
     * @param filas El número de filas que se presentaran en el JTable de totales.
     * De forma predetermina se presenta 1 fila pero de ser necesario se pueden presentar más filas.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, int columnasTotales[], String formato, int filas)
    {
        this(panelDatos, panelTotales, datos, totales, pasarArrayToArrayList(columnasTotales), formato, filas);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, ArrayList<Integer> columnasTotales)
    {
        this(panelDatos, panelTotales, datos, totales, columnasTotales, null, 1);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param formato El formato que se utilizará para mostrar los números en el JTable de totales.
     * Ejemplos: ###,###,###.##, ###,###,##0.00, etc.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> se utilizará el formato "###,###,##0.00".
     * <LI>Si el parámetro recibido es diferente a <I>null</I> se utilizará el formato recibido.
     * </UL>
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, ArrayList<Integer> columnasTotales, String formato)
    {
        this(panelDatos, panelTotales, datos, totales, columnasTotales, formato, 1);
    }
    
    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param filas El número de filas que se presentaran en el JTable de totales.
     * De forma predetermina se presenta 1 fila pero de ser necesario se pueden presentar más filas.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, ArrayList<Integer> columnasTotales, int filas)
    {
        this(panelDatos, panelTotales, datos, totales, columnasTotales, null, filas);
    }

    /**
     * Este constructor crea una nueva instancia de la clase ZafTblTot.
     * @param panelDatos El JScrollPane que contiene el JTable de datos.
     * @param panelTotales El JScrollPane que contiene el JTable de totales.
     * @param datos El JTable de datos.
     * @param totales El JTable de totales.
     * @param columnasTotales Las columnas a las que se le calculará el total.
     * @param formato El formato que se utilizará para mostrar los números en el JTable de totales.
     * Ejemplos: ###,###,###.##, ###,###,##0.00, etc.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> se utilizará el formato "###,###,##0.00".
     * <LI>Si el parámetro recibido es diferente a <I>null</I> se utilizará el formato recibido.
     * </UL>
     * @param filas El número de filas que se presentaran en el JTable de totales.
     * De forma predetermina se presenta 1 fila pero de ser necesario se pueden presentar más filas.
     */
    public ZafTblTot(JScrollPane panelDatos, JScrollPane panelTotales, JTable datos, JTable totales, ArrayList<Integer> columnasTotales, String formato, int filas)
    {
        //Asignar los paràmetros recibidos a las variables de Clase.
        this.tblDat=datos;
        this.tblTot=totales;
        this.arlColTot=columnasTotales;
        if (formato==null)
            this.strForNum="###,###,##0.00";
        else
            this.strForNum=formato;
        this.intNumTotFil=filas;
        intNumDec=6;
        //Inicializar objetos.
        objUti=new ZafUtil();
        objTblModDat=(ZafTblMod)tblDat.getModel();
        //Establecer el mismo número de columnas para el JTable de totales.
        objTblModTot=new ZafTblMod();
        objTblModTot.setSize(intNumTotFil, tblDat.getColumnCount());
        tblTot.setModel(objTblModTot);
        //Configurar JTable: Establecer el menú de contexto.
        objTblPopMnu=new ZafTblPopMnu(tblTot);
        objTblPopMnu.setInsertarFilaEnabled(false);
        objTblPopMnu.setInsertarFilasEnabled(false);
        objTblPopMnu.setEliminarFilaEnabled(false);
        objTblPopMnu.setCopiaAvanzadaEnabled(false);
        //Establecer el tipo de redimensionamiento del JTable de totales.
        tblTot.setAutoResizeMode(tblDat.getAutoResizeMode());
        javax.swing.table.TableColumnModel tcmAux=tblTot.getColumnModel();
        //Configurar JTable: Establecer la fila de cabecera.
        objTblFilCab=new ZafTblFilCab(tblTot);
        tcmAux.getColumn(0).setCellRenderer(objTblFilCab);
        //Configurar JTable: Renderizar celdas.
        for (int intAux : arlColTot)
        {
            objTblCelRenLbl=new ZafTblCelRenLbl();
            objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
            objTblCelRenLbl.setTipoFormato(ZafTblCelRenLbl.INT_FOR_NUM);
            objTblCelRenLbl.setFormatoNumerico(strForNum);
            tcmAux.getColumn(intAux).setCellRenderer(objTblCelRenLbl);
            objTblCelRenLbl=null;
        }
        //Configurar JTable: Igualar el ancho de las columnas del JTable de totales con el JTable de totales.
        for (int j=0; j<tblDat.getColumnCount(); j++)
        {
            if (objTblModDat.isSystemHiddenColumn(j))
            {
                //Si la columna está oculta por el Sistema asignar primero el ancho antes de ocultarla.
                tcmAux.getColumn(j).setWidth(objTblModDat.getWidthSystemHiddenColumn(j));
                tcmAux.getColumn(j).setMaxWidth(objTblModDat.getWidthSystemHiddenColumn(j));
                tcmAux.getColumn(j).setMinWidth(objTblModDat.getWidthSystemHiddenColumn(j));
                tcmAux.getColumn(j).setPreferredWidth(objTblModDat.getWidthSystemHiddenColumn(j));
                objTblModTot.addSystemHiddenColumn(j, tblTot);
            }
            if (objTblModDat.isUserHiddenColumn(j))
            {
                //Si la columna está oculta por el Usuario asignar primero el ancho antes de ocultarla.
                tcmAux.getColumn(j).setWidth(objTblModDat.getWidthUserHiddenColumn(j));
                tcmAux.getColumn(j).setMaxWidth(objTblModDat.getWidthUserHiddenColumn(j));
                tcmAux.getColumn(j).setMinWidth(objTblModDat.getWidthUserHiddenColumn(j));
                tcmAux.getColumn(j).setPreferredWidth(objTblModDat.getWidthUserHiddenColumn(j));
                objTblModTot.addUserHiddenColumn(j, tblTot);
            }
            else
            {
                tcmAux.getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
            }
            tcmAux.getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
        }
        //Evitar que aparezca la barra de desplazamiento horizontal y vertical en el JTable de totales.
        panelTotales.setHorizontalScrollBarPolicy(javax.swing.JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelTotales.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        //Cuando se migró a Java8 algunas tablas de totales presentaban una pequeña fila de cabecera a pesar de que se ponía "tblTot.setTableHeader(null);".
        //Para corregir dicho problema fue necesario poner el alto de la cabecera a cero. Otra forma de corregir el problema es con "tblTot.getTableHeader().setUI(null);".
        tblTot.getTableHeader().setPreferredSize(new java.awt.Dimension(tblTot.getTableHeader().getWidth(), 0));
        tblTot.setTableHeader(null);
        //Adicionar el listener que controla el redimensionamiento de las columnas.
        ZafTblColModLis objTblColModLis=new ZafTblColModLis();
        tblDat.getColumnModel().addColumnModelListener(objTblColModLis);
        //Adicionar el listener que controla el desplazamiento del JTable de datos y totales.
        barHorDat=panelDatos.getHorizontalScrollBar();
        barHorTot=panelTotales.getHorizontalScrollBar();
        barHorDat.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                barHorTot.setValue(evt.getValue());
            }
        });
        barHorTot.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(java.awt.event.AdjustmentEvent evt) {
                barHorDat.setValue(evt.getValue());
            }
        });
        //Se inhabilita la barra vertical con la finalidad que cuando sea presentada sólo sirva para ocupar espacio.
        panelTotales.getVerticalScrollBar().setEnabled(false);
        //Si en la tabla de datos se presenta un VerticalScrollBar también se presenta un VerticalScrollBar en la tabla de totales.
        barVerDat=panelDatos.getVerticalScrollBar();
        barVerDat.addComponentListener(new java.awt.event.ComponentListener() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e)
            {
                
            }

            @Override
            public void componentMoved(java.awt.event.ComponentEvent e)
            {
                
            }

            @Override
            public void componentShown(java.awt.event.ComponentEvent e)
            {
                panelTotales.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            }

            @Override
            public void componentHidden(java.awt.event.ComponentEvent e)
            {
                panelTotales.setVerticalScrollBarPolicy(javax.swing.JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            }
        });
        //Libero los objetos auxiliares.
        tcmAux=null;
    }
    
    /**
     * Esta función calcula el total de las columnas especificadas.
     * @return true: Si se pudo calcular el total de las columnas especificadas sin que ocurra ningún error.
     * <BR>false: En el caso contrario.
     */
    public boolean calcularTotales()
    {
        int intNumFil, intNumCol, i, j;
        boolean blnRes=true;
        try
        {
            intNumFil=tblDat.getRowCount();
            intNumCol=arlColTot.size();
            double dblTot[]=new double[intNumCol];
            for (i=0; i<intNumFil; i++)
            {
                for (j=0; j<intNumCol; j++)
                    dblTot[j]+=objUti.parseDouble(tblDat.getValueAt(i, arlColTot.get(j)));
            }
            for (j=0; j<intNumCol; j++)
                tblTot.setValueAt("" + objUti.redondear(dblTot[j], intNumDec), 0, arlColTot.get(j));
            //Limpiar la celda (0, 0). Al asignar el valor con "setValueAt" dicha celda queda marcada con estado "M".
            objTblModTot.clearCell(0, 0, false);
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el valor que se encuentra almacenado en la celda especificada.
     * @param row La fila de la celda de la que se desea obtener su valor.
     * @param col La columna de la celda de la que se desea obtener su valor.
     * @return El valor almacenado en la celda.
     */
    public Object getValueAt(int row, int col)
    {
        return objTblModTot.getValueAt(row, col);
    }
    
    /**
     * Esta función establece el valor para la celda especificada.
     * @param valor El valor que se almacenará en la celda.
     * @param row La fila de la celda en la que se desea establecer su valor.
     * @param col La columna de la celda en la que se desea establecer su valor.
     */
    public void setValueAt(Object valor, int row, int col)
    {
        objTblModTot.setValueAt(valor, row, col);
    }
    
    /**
     * Esta función obtiene el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @return El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public int getNumeroDecimales()
    {
        return intNumDec;
    }
    
    /**
     * Esta función establece el número máximo de decimales que almacena la celda. Cuando se
     * ingresa un número con más decimales de los permitidos se redondeará automáticamente
     * al máximo número de decimales permitidos.
     * @param decimales El número máximo de decimales que almacena la celda.
     * <BR>Nota.-De manera predeterminada la celda permite como máximo 6 decimales.
     */
    public void setNumeroDecimales(int decimales)
    {
        intNumDec=decimales;
    }

    /**
    * Esta función permite insertar filas al final del modelo de datos.
    * @param numeroFilas El número de filas que se desea adicionar al modelo.
    * @return true: Si se pudo realizar la inserción
    * <BR>false: En el caso contrario.
    */

    public boolean insertMoreRows(int numeroFilas)
    {
        boolean blnRes=true;
        try
        {
            for(int i=0; i<numeroFilas; i++)
            {
                objTblModTot.insertRow();
            }
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
    * Esta función presenta el numero de filas en el modelo de datos.
    * @return int: El número de filas en el modelo de datos
    */
    public int getRowCountTrue()
    {
        int intNumFilModDat=-1;
        try
        {
            intNumFilModDat=objTblModTot.getRowCountTrue();
        }
        catch(Exception e)
        {
            
        }
        return intNumFilModDat;
    }

    /**
     * Esta función elimina todas las filas de la tabla.
     * @return true: Si se pudieron eliminar todas las filas.
     * <BR>false: En el caso contrario.
     */
    public boolean removeAllRows()
    {
        boolean blnRes=true;
        try
        {
            objTblModTot.removeAllRows();
        }
        catch(Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función pasa los datos de un arreglo a un ArrayList.
     * @param arreglo El arreglo que contiene los datos a pasar.
     * @return Un ArrayList con los datos del arreglo.
     */
    private static ArrayList pasarArrayToArrayList(int arreglo[])
    {
        ArrayList<Integer> arlDat=new ArrayList();
        try
        {
            for (int intAux : arreglo)
            {
                arlDat.add(intAux);
            }
        }
        catch (Exception e)
        {
            arlDat=null;
            javax.swing.JOptionPane.showMessageDialog(null, e.toString(), "Mensaje del Sistema", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        }
        return arlDat;
    }
    
    /**
     * Esta función permite igualar las columnas de la tabla de totales de acuerdo a las columnas de la tabla de datos.
     * @return true: Si se pudieron igualar las columnas.
     * <BR>false: En el caso contrario.
     */
    public boolean igualarAnchoColumnas()
    {
        boolean blnRes=true;
        try
        {
            javax.swing.table.TableColumnModel tcmAux=tblTot.getColumnModel();
            //Configurar JTable: Igualar el ancho de las columnas de los JTables.
            for (int j=0; j<tblDat.getColumnCount(); j++)
            {
                tcmAux.getColumn(j).setWidth(tblDat.getColumnModel().getColumn(j).getWidth());
                tcmAux.getColumn(j).setMaxWidth(tblDat.getColumnModel().getColumn(j).getMaxWidth());
                tcmAux.getColumn(j).setMinWidth(tblDat.getColumnModel().getColumn(j).getMinWidth());
                tcmAux.getColumn(j).setPreferredWidth(tblDat.getColumnModel().getColumn(j).getPreferredWidth());
                tcmAux.getColumn(j).setResizable(tblDat.getColumnModel().getColumn(j).getResizable());
            }
            tcmAux=null;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    private class ZafTblColModLis implements javax.swing.event.TableColumnModelListener
    {
        
        @Override
        public void columnAdded(javax.swing.event.TableColumnModelEvent e)
        {
            
        }
        
        @Override
        public void columnMarginChanged(javax.swing.event.ChangeEvent e)
        {
            int intColSel, intAncCol;
            if (tblDat.getTableHeader().getResizingColumn()!=null)
            {
                intColSel=tblDat.getTableHeader().getResizingColumn().getModelIndex();
                if (intColSel>=0)
                {
                    intAncCol=tblDat.getColumnModel().getColumn(intColSel).getPreferredWidth();
                    tblTot.getColumnModel().getColumn(intColSel).setPreferredWidth(intAncCol);
                }
            }
        }
        
        @Override
        public void columnMoved(javax.swing.event.TableColumnModelEvent e)
        {

        }
        
        @Override
        public void columnRemoved(javax.swing.event.TableColumnModelEvent e)
        {

        }
        
        @Override
        public void columnSelectionChanged(javax.swing.event.ListSelectionEvent e)
        {

        }
    }
    
}
