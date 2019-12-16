/*
 * ZafTblOrd.java
 *
 * Created on 18 de febrero de 2006, 13:10 PM
 * v0.5
 */

package Librerias.ZafTblUti.ZafTblOrd;
import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdListener;
import Librerias.ZafTblUti.ZafTblEvt.ZafTblOrdEvent;
import javax.swing.event.EventListenerList;
import java.util.Vector;

public class ZafTblOrd
{
    //Constantes:
    public static final int INT_TBL_SIN=0;                      /**Un valor para setTipoOrdenamiento: Indica "Sin ordenar".*/
    public static final int INT_TBL_ASC=1;                      /**Un valor para setTipoOrdenamiento: Indica "Orden ascendente".*/
    public static final int INT_TBL_DES=2;                      /**Un valor para setTipoOrdenamiento: Indica "Orden descendente".*/
    private static final int INT_BEF_ORD=0;                     /**Antes de editar: Indica "Before click".*/
    private static final int INT_AFT_ORD=1;                     /**Después de editar: Indica "After click".*/
    //Variables:
    private javax.swing.JTable tblDat;
    private ZafTblMod objTblMod;                                //Modelo del JTable.
    private ZafCom objCom;                                      //Objeto Comparator utilizado para realizar el ordenamiento.
    private ZafHeaRenLbl objHeaRenLbl;                          //Header: Render para mostrar el ordenamiento.
    private int intUltColOrd;                                   //Indice de la última columna ordenada.
    private int intUltTipOrd;                                   //Ultimo tipo de ordenamiento.
    private boolean blnCanOrd;                                  //Determina si se debe cancelar el ordenamiento.
    private int intColCli;                                      //Columna donde se dió click.
    protected EventListenerList objEveLisLis=new EventListenerList();
    
    /**
     * Crea una nueva instancia de la clase ZafTblOrd.
     * @param tabla El objeto JTable sobre el cual trabajará el ZafTblOrd.
     */
    public ZafTblOrd(javax.swing.JTable tabla)
    {
        tblDat=tabla;
        objTblMod=(Librerias.ZafTblUti.ZafTblMod.ZafTblMod)tblDat.getModel();
        objCom=new ZafCom();
        blnCanOrd=false;
        intUltColOrd=-1;
        intUltTipOrd=INT_TBL_SIN;
        intColCli=-1;
        objHeaRenLbl=new ZafHeaRenLbl(tblDat.getTableHeader().getDefaultRenderer());
        tblDat.getTableHeader().setDefaultRenderer(objHeaRenLbl);
        tblDat.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                thisMouseClicked(evt);
            }
        });
    }
    
    private void thisMouseClicked(java.awt.event.MouseEvent evt)
    {
        //Ordenar sólo si se da 1 click con el botón izquierdo.
        if (evt.getButton()==java.awt.event.MouseEvent.BUTTON1 && evt.getClickCount()==1)
        {
            intColCli=tblDat.columnAtPoint(evt.getPoint());
            ordenar(intColCli);
        }
    }
    
    /**
     * Esta función determina si la cadena que se recibe como argumento es un valor numérico o no. 
     * @param numero La cadena que contiene el número que se desea evaluar.
     * @return true: Si el valor es un número.
     * <BR>false: En el caso contrario.
     */
    private boolean isNumero(String numero)
    {
        double dblNum;
        boolean blnRes=true;
        try
        {
            dblNum=Double.parseDouble(numero);
        }
        catch (NumberFormatException e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
    * Esta clase implementa la interface "Comparator" la cual es utilizada para indicar
    * el modo que se utilizará para comparar objetos.
    */
    private class ZafCom implements java.util.Comparator
    {
        private int intCol;
        private boolean blnAsc;
        
        public ZafCom()
        {
            intCol=-1;
            blnAsc=true;
        }
        
        /**
         * Este constructor establece la columna que se debe ordenar ascendentemente/descendentemente. 
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public ZafCom(int columna, boolean ascendente)
        {
            intCol=columna;
            blnAsc=ascendente;
        }

        /**
         * Esta función establece la columna que se debe ordenar ascendentemente/descendentemente. 
         * @param columna La columna a ordenar.
         * @param ascendente Puede tomar los siguientes valores:
         * <BR>true: Para que la columna se ordene ascendentemente.
         * <BR>false: Para que la columna se ordene descendentemente.
         */
        public void setColumnaOrdenar(int columna, boolean ascendente)
        {
            intCol=columna;
            blnAsc=ascendente;
        }
        
        /**
         * Esta función compara los objetos que recibe y devuelve el resultado de la comparación.
         * @param o1 El objeto a comparar.
         * @param o2 El otro objeto a comparar.
         * @return Puede tomar los siguientes valores:
         * <UL>
         * <LI>-1: Si o1<o2.
         * <LI>0: Si o1=o2.
         * <LI>1: Si o1>o2.
         * </UL>
         * Nota.- Se intercambia el -1 con el 1 para cambiar de ascendente a descendente.
         */
        public int compare(Object o1, Object o2)
        {
            int intRes=0;
            double dblNum1, dblNum2;
            Vector vecFil1, vecFil2;
            vecFil1=(Vector)o1;
            vecFil2=(Vector)o2;
            Object objCel1, objCel2;
            objCel1=vecFil1.get(intCol);
            objCel2=vecFil2.get(intCol);
//            //Convertir las cadenas vacías a null.
//            if (objCel1 instanceof String && ((String)objCel1).length()==0)
//            {
//                objCel1=null;
//            }
//            if (objCel2 instanceof String && ((String)objCel2).length()==0)
//            {
//                objCel2=null;
//            }
            //Comparar el contenido de las celdas.
            if (objCel1==null && objCel2==null)
            {
                intRes=0;
            }
            else if (objCel1==null)
            {
                if (blnAsc)
                    intRes=-1;
                else
                    intRes=1;
            }
            else if (objCel2==null)
            {
                if (blnAsc)
                    intRes=1;
                else
                    intRes=-1;
            }
            else if (isNumero(objCel1.toString()) && isNumero(objCel2.toString()))
            {
                //Comparar los números como números caso contrario serán considerados como cadena.
                dblNum1=Double.parseDouble(objCel1.toString());
                dblNum2=Double.parseDouble(objCel2.toString());
                if (blnAsc)
                {
                    if (dblNum1<dblNum2)
                        intRes=-1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=1;
                }
                else
                {
                    if (dblNum1<dblNum2)
                        intRes=1;
                    else if (dblNum1==dblNum2)
                        intRes=0;
                    else
                        intRes=-1;
                }
            }
            else if (objCel1 instanceof String)
            {
                //Comparar cadenas.
                if (blnAsc)
                    intRes=objCel1.toString().compareToIgnoreCase(objCel2.toString());
                else
                    intRes=objCel2.toString().compareToIgnoreCase(objCel1.toString());
            }
            else if (objCel1 instanceof Comparable)
            {
                if (blnAsc)
                    intRes=((Comparable)objCel1).compareTo(objCel2);
                else
                    intRes=((Comparable)objCel2).compareTo(objCel1);
            }
            return intRes;
        }
        
    }
    
    /**
     * Esta función adiciona el listener que controlará los eventos de ordenamiento que se generen sobre el "ZafTblOrd".
     * @param listener El objeto que implementa los métodos de la interface "ZafTblOrdListener".
     */
    public void addTblOrdListener(ZafTblOrdListener listener)
    {
        objEveLisLis.add(ZafTblOrdListener.class, listener);
    }

    /**
     * Esta función remueve el listener que controlará los eventos de ordenamiento que se generen sobre el "ZafTblOrd".
     * @param listener El objeto que implementa los métodos de la interface "ZafTblOrdListener".
     */
    public void removeTblOrdListener(ZafTblOrdListener listener)
    {
        objEveLisLis.remove(ZafTblOrdListener.class, listener);
    }
    
    /**
     * Esta función dispara el listener adecuado de acuerdo a los argumentos recibidos.
     * @param evt El objeto "ZafTblOrdEvent".
     * @param metodo El método que será invocado.
     * Puede tomar uno de los siguientes valores:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Tipo de campo</I></TD><TD><I>Método</I></TD></TR>
     *     <TR><TD>INT_BEF_ORD</TD><TD>Invoca al métod "beforeOrder" de la interface.</TD></TR>
     *     <TR><TD>INT_AFT_ORD</TD><TD>Invoca al métod "afterOrder" de la interface.</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    private void fireTblOrdListener(ZafTblOrdEvent evt, int metodo)
    {
        int i;
        Object[] obj=objEveLisLis.getListenerList();
        //Cada listener ocupa 2 elementos:
        //1)Es el listener class.
        //2)Es la instancia del listener.
        for (i=0;i<obj.length;i+=2)
        {
            if (obj[i]==ZafTblOrdListener.class)
            {
                switch (metodo)
                {
                    case INT_BEF_ORD:
                        ((ZafTblOrdListener)obj[i+1]).beforeOrder(evt);
                        break;
                    case INT_AFT_ORD:
                        ((ZafTblOrdListener)obj[i+1]).afterOrder(evt);
                        break;
                }
            }
        }
    }

    /**
     * Esta función cancela el ordenamiento de los datos de la tabla.
     */
    public void cancelarOrdenamiento()
    {
        blnCanOrd=true;
    }
    
    /**
     * Esta función devuelve la columna en la que se dió click.
     * El hecho de dar click en una columna no implica que se vayan a ordenar los datos.
     * Se puede poner una condición en el evento "beforeOrder" que determine si se ordenan o no los datos.
     * Para cancelar el ordenamiento se debe utilizar del método "cancelarOrdenamiento()".
     * @return La columna en la que se dió click.
     */
    public int getColumnaClick()
    {
        return intColCli;
    }

    /**
     * Esta función permite ordenar los datos de la tabla de acuerdo a la columna especificada.
     * Si se desea cancelar el ordenamiento se puede utilizar el evento "beforeOrder" en conjunto con el método "cancelarOrdenamiento()".
     * @param intCol La columna por la cual se ordenará los datos.
     * @return true: Si se pudo ordenar los datos.
     * <BR>false: En el caso contrario.
     */
    public boolean ordenar(int intCol)
    {
        boolean blnRes=true;
        try
        {
            //Permitir de manera predeterminada el ordenamiento.
            blnCanOrd=false;
            //Generar evento "beforeOrder()".
            fireTblOrdListener(new ZafTblOrdEvent(this), INT_BEF_ORD);
            //Permitir/Cancelar el ordenamiento de acuerdo a "cancelarOrdenamiento".
            if (blnCanOrd)
                return true;
            if (intCol!=intUltColOrd)
                intUltTipOrd=INT_TBL_SIN;
            intUltColOrd=intCol;
            switch (intUltTipOrd)
            {
                case INT_TBL_SIN:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd,INT_TBL_ASC);
                    objCom.setColumnaOrdenar(intUltColOrd,true);
                    java.util.Collections.sort(objTblMod.getData(),objCom);
                    objTblMod.fireTableDataChanged();
                    intUltTipOrd=INT_TBL_ASC;
                    break;
                case INT_TBL_ASC:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_DES);
                    objCom.setColumnaOrdenar(intUltColOrd,false);
                    java.util.Collections.sort(objTblMod.getData(),objCom);
                    objTblMod.fireTableDataChanged();
                    intUltTipOrd=INT_TBL_DES;
                    break;
                case INT_TBL_DES:
                    objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_SIN);
                    intUltTipOrd=INT_TBL_SIN;
                    break;
            }
            //Generar evento "afterClick()".
            fireTblOrdListener(new ZafTblOrdEvent(this), INT_AFT_ORD);
            tblDat.getTableHeader().repaint();
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el tipo de ordenamiento aplicado a la tabla.
     * @return El tipo de ordenamiento aplicado a la tabla.
     * Puede retornar uno de los siguientes valores:
     * <UL>
     * <LI>0: Sin ordenar (INT_TBL_SIN).</LI>
     * <LI>1: Orden ascendente (INT_TBL_ASC).</LI>
     * <LI>2: Orden descendente (INT_TBL_DES).</LI>
     * </UL>
     */
    public int getTipoOrdenamiento()
    {
        return objHeaRenLbl.getTipoOrdenamiento();
    }
    
    /**
     * Esta función cambia el icono que se presentar en la cabecera de la columna luego del ordenamiento.
     * @param columna La columna a la que se desea cambiar el icono de ordenamiento.
     * @param intTipOrd El tipo de ordenamiento.
     */
    public void cambiarIconoOrdenamiento(int columna, int intTipOrd)
    {
        //Si se envía un columna negativa se asume que no se quiere presentar ningún indicador.
        if (columna<0)
        {
            intTipOrd=INT_TBL_DES;
        }
        intUltColOrd=columna;
        switch (intTipOrd)
        {
            case INT_TBL_SIN:
                objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_SIN);
                intUltTipOrd=INT_TBL_ASC;
                break;
            case INT_TBL_ASC:
                objHeaRenLbl.setTipoOrdenamiento(intUltColOrd,INT_TBL_ASC);
                intUltTipOrd=INT_TBL_DES;
                break;
            case INT_TBL_DES:
                objHeaRenLbl.setTipoOrdenamiento(intUltColOrd, INT_TBL_DES);
                intUltTipOrd=INT_TBL_SIN;
                break;
        }
        tblDat.getTableHeader().repaint();
    }
    
}