/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumPoj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_carlab
 * @author Roberto Flores
 * Guayaquil 19/05/2011
 */
public class Tbm_carlab implements Cloneable{



    private int intCo_Car;
    private String strTx_NomCar;
    private int intCo_ComSec;
    private String strTx_NomComSec;
    private String strTx_CodComSec;
    private String strTx_NomCarComSec;
    private BigDecimal bigNd_MinSec;
    private String strTx_Obs1;
    private String strSt_Reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;
    

    /**
     * @return the intCo_Car
     */
    public int getIntCo_Car() {
        return intCo_Car;
    }

    /**
     * @param intCo_Car the intCo_Car to set
     */
    public void setIntCo_Car(int intCo_Car) {
        this.intCo_Car = intCo_Car;
    }

    /**
     * @return the strTx_NomCar
     */
    public String getStrTx_NomCar() {
        return strTx_NomCar;
    }

    /**
     * @param strTx_NomCar the strTx_NomCar to set
     */
    public void setStrTx_NomCar(String strTx_NomCar) {
        this.strTx_NomCar = strTx_NomCar;
    }

    /**
     * @return the intCo_ComSec
     */
    public int getIntCo_ComSec() {
        return intCo_ComSec;
    }

    /**
     * @param intCo_ComSec the intCo_ComSec to set
     */
    public void setIntCo_ComSec(int intCo_ComSec) {
        this.intCo_ComSec = intCo_ComSec;
    }

    /**
     * @return the strTx_CodComSec
     */
    public String getStrTx_CodComSec() {
        return strTx_CodComSec;
    }

    /**
     * @param strTx_CodComSec the strTx_CodComSec to set
     */
    public void setStrTx_CodComSec(String strTx_CodComSec) {
        this.strTx_CodComSec = strTx_CodComSec;
    }

    /**
     * @return the strTx_NomCarComSec
     */
    public String getStrTx_NomCarComSec() {
        return strTx_NomCarComSec;
    }

    /**
     * @param strTx_NomCarComSec the strTx_NomCarComSec to set
     */
    public void setStrTx_NomCarComSec(String strTx_NomCarComSec) {
        this.strTx_NomCarComSec = strTx_NomCarComSec;
    }

    /**
     * @return the bigNd_MinSec
     */
    public BigDecimal getBigNd_MinSec() {
        return bigNd_MinSec;
    }

    /**
     * @param bigNd_MinSec the bigNd_MinSec to set
     */
    public void setBigNd_MinSec(BigDecimal bigNd_MinSec) {
        this.bigNd_MinSec = bigNd_MinSec;
    }

    /**
     * @return the strTx_Obs1
     */
    public String getStrTx_Obs1() {
        return strTx_Obs1;
    }

    /**
     * @param strTx_Obs1 the strTx_Obs1 to set
     */
    public void setStrTx_Obs1(String strTx_Obs1) {
        this.strTx_Obs1 = strTx_Obs1;
    }

    /**
     * @return the strSt_Reg
     */
    public String getStrSt_Reg() {
        return strSt_Reg;
    }

    /**
     * @param strSt_Reg the strSt_Reg to set
     */
    public void setStrSt_Reg(String strSt_Reg) {
        this.strSt_Reg = strSt_Reg;
    }

    /**
     * @return the datFe_ing
     */
    public Date getDatFe_ing() {
        return datFe_ing;
    }

    /**
     * @param datFe_ing the datFe_ing to set
     */
    public void setDatFe_ing(Date datFe_ing) {
        this.datFe_ing = datFe_ing;
    }

    /**
     * @return the datFe_ultmod
     */
    public Date getDatFe_ultmod() {
        return datFe_ultmod;
    }

    /**
     * @param datFe_ultmod the datFe_ultmod to set
     */
    public void setDatFe_ultmod(Date datFe_ultmod) {
        this.datFe_ultmod = datFe_ultmod;
    }

    /**
     * @return the intCo_usring
     */
    public int getIntCo_usring() {
        return intCo_usring;
    }

    /**
     * @param intCo_usring the intCo_usring to set
     */
    public void setIntCo_usring(int intCo_usring) {
        this.intCo_usring = intCo_usring;
    }

    /**
     * @return the intCo_usrmod
     */
    public int getIntCo_usrmod() {
        return intCo_usrmod;
    }

    /**
     * @param intCo_usrmod the intCo_usrmod to set
     */
    public void setIntCo_usrmod(int intCo_usrmod) {
        this.intCo_usrmod = intCo_usrmod;
    }

    /**
     * Método que permite clonar el objeto
     * @return Retorna objeto clonado o nulo
     */
    @Override
    public Object clone(){
        Object obj=null;
        try{
            obj=super.clone();
        }catch(CloneNotSupportedException ex){
            System.out.println(" no se puede duplicar");
        }
        return obj;
    }

    /**
     * @return the strTx_NomComSec
     */
    public String getStrTx_NomComSec() {
        return strTx_NomComSec;
    }

    /**
     * @param strTx_NomComSec the strTx_NomComSec to set
     */
    public void setStrTx_NomComSec(String strTx_NomComSec) {
        this.strTx_NomComSec = strTx_NomComSec;
    }
}
