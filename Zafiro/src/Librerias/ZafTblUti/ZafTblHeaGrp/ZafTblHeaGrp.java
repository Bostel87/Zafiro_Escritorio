/*
 * ZafTblHeaGrp.java
 *
 * Created on 23 de noviembre de 2007, 11:00 PM
 * v0.1
 */

package Librerias.ZafTblUti.ZafTblHeaGrp;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Esta clase permite crear un JTableHeader donde se pueden agrupar las columnas.
 * Ejemplo:
 * <PRE>
 * ----------------------------------------------------------------------------------------------------
 * |                                      Planeta Tierra                                              |
 * |--------------------------------------------------------------------------------------------------|
 * |        América del Sur         |    América del Norte    |              Europa                   |
 * |---------+----------------------------------------------------------------------------------------|
 * | Ecuador | Colombia | Venezuela | México | Estados Unidos | España | Italia | Alemania | Portugal |
 * ----------------------------------------------------------------------------------------------------
 * |                                                                                                  |
 * </PRE>
 * Pasos para utilizarlo:
 * <PRE>
 *   PASO 1:
 *   -------
 *   Importar las librerías.
 *
 *   import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaGrp;
 *   import Librerias.ZafTblUti.ZafTblHeaGrp.ZafTblHeaColGrp;
 *
 *   PASO 2:
 *   -------
 *   Sobreescribir el método createDefaultTableHeader() de JTable.
 *   Haga lo siguiente:
 *       1) Abra NetBeans (Sólo si utiliza NetBeans).
 *       2) En la vista diseño seleccione el JTable en el que desea incorporar ZafTblHeaGrp.
 *       3) En la ventana "Properties" seleccione el grupo "Code".
 *       4) En el grupo "Code" seleccione la propiedad "Custom Creation Code" y de click en el botón correspondiente
 *          a dicha propiedad. Se abrirá un cuadro de diálogo.
 *       5) Pegue el siguiente código:
 *   new javax.swing.JTable() {
 *               protected javax.swing.table.JTableHeader createDefaultTableHeader()
 *               {
 *                   return new ZafTblHeaGrp(columnModel);
 *               }
 *           };
 *       6) De click en el botón "Ok" y verifique en el método "private void initComponents()" que el código aparece como
 *          se muestra a continuación:
 *           tblDat = new javax.swing.JTable() {
 *               protected javax.swing.table.JTableHeader createDefaultTableHeader()
 *               {
 *                   return new ZafTblHeaGrp(columnModel);
 *               }
 *           };
 *
 *   PASO 3:
 *   -------
 *   Crear un objeto del tipo ZafTblHeaGrp y un ZafTblHeaColGrp por cada grupo.
 *   Agregar las columnas adecuadas a cada ZafTblHeaColGrp.
 *   Ejemplo con 2 filas:
 *               //Agrupar las columnas.
 *               ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
 *               objTblHeaGrp.setHeight(16*2);
 *
 *               ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("América del Sur");
 *               objTblHeaColGrpAmeSur.setHeight(16);
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_ECU));
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COL));
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VEN));
 *
 *               ZafTblHeaColGrp objTblHeaColGrpAmeNor=new ZafTblHeaColGrp("América del Norte");
 *               objTblHeaColGrpAmeNor.setHeight(16);
 *               objTblHeaColGrpAmeNor.add(tcmAux.getColumn(INT_TBL_DAT_MEX));
 *               objTblHeaColGrpAmeNor.add(tcmAux.getColumn(INT_TBL_DAT_USA));
 *
 *               ZafTblHeaColGrp objTblHeaColGrpEur=new ZafTblHeaColGrp("Europa");
 *               objTblHeaColGrpEur.setHeight(16);
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ESP));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ITA));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ALE));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_POR));
 *
 *               objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeSur);
 *               objTblHeaGrp.addColumnGroup(objTblHeaColGrpAmeNor);
 *               objTblHeaGrp.addColumnGroup(objTblHeaColGrpEur);
 *
 *   Ejemplo con 3 filas:
 *               //Agrupar las columnas.
 *               ZafTblHeaGrp objTblHeaGrp=(ZafTblHeaGrp)tblDat.getTableHeader();
 *               objTblHeaGrp.setHeight(16*3);
 *
 *               ZafTblHeaColGrp objTblHeaColGrpPlaTie=new ZafTblHeaColGrp("Planeta Tierra");
 *               objTblHeaColGrpPlaTie.setHeight(16);
 *
 *               ZafTblHeaColGrp objTblHeaColGrpAmeSur=new ZafTblHeaColGrp("América del Sur");
 *               objTblHeaColGrpAmeSur.setHeight(16);
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_ECU));
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_COL));
 *               objTblHeaColGrpAmeSur.add(tcmAux.getColumn(INT_TBL_DAT_VEN));
 *
 *               ZafTblHeaColGrp objTblHeaColGrpAmeNor=new ZafTblHeaColGrp("América del Norte");
 *               objTblHeaColGrpAmeNor.setHeight(16);
 *               objTblHeaColGrpAmeNor.add(tcmAux.getColumn(INT_TBL_DAT_MEX));
 *               objTblHeaColGrpAmeNor.add(tcmAux.getColumn(INT_TBL_DAT_USA));
 *
 *               ZafTblHeaColGrp objTblHeaColGrpEur=new ZafTblHeaColGrp("Europa");
 *               objTblHeaColGrpEur.setHeight(16);
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ESP));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ITA));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_ALE));
 *               objTblHeaColGrpEur.add(tcmAux.getColumn(INT_TBL_DAT_POR));
 *
 *               objTblHeaColGrpPlaTie.add(objTblHeaColGrpAmeSur);
 *               objTblHeaColGrpPlaTie.add(objTblHeaColGrpAmeNor);
 *               objTblHeaColGrpPlaTie.add(objTblHeaColGrpEur);
 *
 *               objTblHeaGrp.addColumnGroup(objTblHeaColGrpPlaTie);
 * </PRE>
 * @author  Eddye Lino
 */
public class ZafTblHeaGrp extends javax.swing.table.JTableHeader
{
    private int intAltHeaGrp;                   //Altura del ZafTblHeaGrp.
    protected Vector vecColGrp;                 //Vector donde se almacena los grupos de columnas.
    
    /**
     * Este constructor crea una instancia de la clase ZafTblHeaGrp.
     * @param tcm El objeto TableColumnModel que contiene el modelo de columnas del JTable.
     */
    public ZafTblHeaGrp(javax.swing.table.TableColumnModel tcm)
    {
        super(tcm);
        //Establecer como altura predeterminada 32.
        intAltHeaGrp=32;
        setUI(new ZafTblHeaGrpUI());
    }
    
    /**
     * Esta función sobreescribe el método "updateUI" para hacer que funcione agrupando columnas.
     */
    public void updateUI()
    {
        setUI(new ZafTblHeaGrpUI());
    }

    /**
     * Esta función agrega el grupo de columnas especificado al ZafTblHeaGrp.
     * @param columnGroup El grupo de columnas a agregar.
     */
    public void addColumnGroup(ZafTblHeaColGrp columnGroup)
    {
        if (vecColGrp==null)
        {
            vecColGrp=new Vector();
        }
        vecColGrp.addElement(columnGroup);
    }

    /**
     * Esta función obtiene el grupo al que pertenece la columna especificada.
     * @param tbc La columna de la que se desea saber el grupo al que pertenece.
     * @return Una enumeración que contiene los grupos de columnas.
     */
    public Enumeration getColumnGroups(javax.swing.table.TableColumn tbc)
    {
        Vector vecAux;
        Enumeration enu;
        ZafTblHeaColGrp objTblHeaColGrp;
        if (vecColGrp==null)
            return null;
        enu=vecColGrp.elements();
        while (enu.hasMoreElements())
        {
            objTblHeaColGrp=(ZafTblHeaColGrp)enu.nextElement();
            vecAux=(Vector)objTblHeaColGrp.getColumnGroups(tbc, new Vector());
            if (vecAux!=null)
            {
                return vecAux.elements();
            }
        }
        return null;
    }

    /**
     * Esta función establece el margen del ZafTblHeaGrp.
     */
    public void setColumnMargin()
    {
        int intColMar;
        Enumeration enu;
        ZafTblHeaColGrp objTblHeaColGrp;
        if (vecColGrp==null)
            return;
        intColMar=getColumnModel().getColumnMargin()-1;
        enu=vecColGrp.elements();
        while (enu.hasMoreElements())
        {
            objTblHeaColGrp=(ZafTblHeaColGrp)enu.nextElement();
            objTblHeaColGrp.setColumnMargin(intColMar);
        }
    }
    
    /**
     * Esta función obtiene la altura del ZafTblHeaGrp.
     * @return La altura del ZafTblHeaGrp.
     */
    public int getHeight()
    {
        return intAltHeaGrp;
    }
    
    /**
     * Esta función establece la altura del ZafTblHeaGrp.
     * @param height La altura del ZafTblHeaGrp.
     * <BR>El alto de cada fila es 16. Para determinar el alto del ZafTblHeaGrp se debe multiplicar el número de filas por 16. Por ejemplo:
     * <CENTER>
     * <TABLE BORDER=1>
     *     <TR><TD><I>Número de filas</I></TD><TD><I>Alto del ZafTblHeaGrp</I></TD></TR>
     *     <TR><TD>1</TD><TD>1*16=16</TD></TR>
     *     <TR><TD>2</TD><TD>2*16=32</TD></TR>
     *     <TR><TD>3</TD><TD>3*16=48</TD></TR>
     *     <TR><TD>...</TD><TD>...</TD></TR>
     * </TABLE>
     * </CENTER>
     */
    public void setHeight(int height)
    {
        intAltHeaGrp=height;
    }
    
}
