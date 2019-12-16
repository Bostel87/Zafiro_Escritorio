/*
 * Clase Para setear el Sueldo Basico correspondiente al anio  y otras parametrizaciones.
 * Anio 2016 - 366.00 
 * Anio 2017 - 375.00 
 * Anio 2018 - 386.00 
 * Anio 2019 - 394.00 
 * 
 */
package Librerias.ZafRecHum.ZafRecHumPar;

/**
 *
 * @author Tony
 */
public class ZafRecHumPar 
{

    private final double dblSueBas = 394.00;
    private final double dblPorAportePatronal  = 12.15;
    private final double dblPorFonRes = 8.33;
    private String strVer ="v0.5";

    /**
     * Función que retorna el Sueldo Básico.
     * @return 
     */
    public double getSueBas() 
    {
        return dblSueBas;
    }

    /**
     * @param dblSueBas the dblSueBas to set
     */
    //public void setSueBas(double dblSueBas) 
    //{
    //    this.dblSueBas = dblSueBas;
    //}
    
    /**
     * Función que retorna el porcentaje de aporte patronal.
     * @return 
     */
    public double getPorApoPat() 
    {
        return dblPorAportePatronal;
    }
    /**
     * Función que retorna el porcentaje de fondo de reserva.
     * @return 
     */
    public double getPorFonRes() 
    {
        return dblPorFonRes;
    }
    /**
     * Método que permite clonar el objeto
     *
     * @return Retorna objeto clonado o nulo
     */
    @Override
    public Object clone() 
    {
        Object obj = null;
        try 
        {
            obj = super.clone();
        } 
        catch (CloneNotSupportedException ex) {    System.out.println(" no se puede duplicar");      }
        return obj;
    }
}
