/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafRecHum.ZafRecHumPoj;

/**
 * Guayaquil, 11/06/2013
 * @author Roberto Flores
 */
public class Provisiones {
    
    private String nombre;
    private int intCoCta;
    private String strNumCta;
    private int intTipCta; // 1 es debe , 2 es haber
    private int intCoPla; // 1 admin, 2 ventas, -1 no aplica
    private double ndValBase;

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the intCoCta
     */
    public int getIntCoCta() {
        return intCoCta;
    }

    /**
     * @param intCoCta the intCoCta to set
     */
    public void setIntCoCta(int intCoCta) {
        this.intCoCta = intCoCta;
    }

    /**
     * @return the strNumCta
     */
    public String getStrNumCta() {
        return strNumCta;
    }

    /**
     * @param strNumCta the strNumCta to set
     */
    public void setStrNumCta(String strNumCta) {
        this.strNumCta = strNumCta;
    }

    /**
     * @return the intTipCta
     */
    public int getIntTipCta() {
        return intTipCta;
    }

    /**
     * @param intTipCta the intTipCta to set
     */
    public void setIntTipCta(int intTipCta) {
        this.intTipCta = intTipCta;
    }

    /**
     * @return the intCoPla
     */
    public int getIntCoPla() {
        return intCoPla;
    }

    /**
     * @param intCoPla the intCoPla to set
     */
    public void setIntCoPla(int intCoPla) {
        this.intCoPla = intCoPla;
    }

    /**
     * @return the ndValBase
     */
    public double getNdValBase() {
        return ndValBase;
    }

    /**
     * @param ndValBase the ndValBase to set
     */
    public void setNdValBase(double ndValBase) {
        this.ndValBase = ndValBase;
    }

}
