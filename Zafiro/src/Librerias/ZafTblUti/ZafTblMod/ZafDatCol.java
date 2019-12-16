/*
 * ZafDatCol.java
 *
 * Created on 5 de agosto de 2007, 09:20 PM
 */


package Librerias.ZafTblUti.ZafTblMod;

/**
 *
 * @author  Eddye Lino
 */
public class ZafDatCol implements java.io.Serializable
{
    private int intCol, intAncMin, intAncMax, intAncPre, intAnc;
    
    /** Creates a new instance of ZafDatCol */
    public ZafDatCol()
    {
        
    }
    
    /**
     * Esta función obtiene la columna.
     * @return La columna.
     */
    public int getColumna()
    {
        return intCol;
    }
    
    /**
     * Esta función establece la columna.
     * @param columna La columna.
     */
    public void setColumna(int columna)
    {
        intCol=columna;
    }
    
    /**
     * Esta función obtiene el ancho mínimo de la columna.
     * @return El ancho mínimo de la columna.
     */
    public int getAnchoMinimo()
    {
        return intAncMin;
    }
    
    /**
     * Esta función establece el ancho mínimo de la columna.
     * @param ancho El ancho mínimo de la columna.
     */
    public void setAnchoMinimo(int ancho)
    {
        intAncMin=ancho;
    }
    
    /**
     * Esta función obtiene el ancho máximo de la columna.
     * @return El ancho máximo de la columna.
     */
    public int getAnchoMaximo()
    {
        return intAncMax;
    }
    
    /**
     * Esta función establece el ancho máximo de la columna.
     * @param ancho El ancho máximo de la columna.
     */
    public void setAnchoMaximo(int ancho)
    {
        intAncMax=ancho;
    }
    
    /**
     * Esta función obtiene el ancho preferido de la columna.
     * @return El ancho preferido de la columna.
     */
    public int getAnchoPreferido()
    {
        return intAncPre;
    }
    
    /**
     * Esta función establece el ancho preferido de la columna.
     * @param ancho El ancho preferido de la columna.
     */
    public void setAnchoPreferido(int ancho)
    {
        intAncPre=ancho;
    }
    
    /**
     * Esta función obtiene el ancho de la columna.
     * @return El ancho de la columna.
     */
    public int getAncho()
    {
        return intAnc;
    }
    
    /**
     * Esta función establece el ancho de la columna.
     * @param ancho El ancho de la columna.
     */
    public void setAncho(int ancho)
    {
        intAnc=ancho;
    }
    
}
