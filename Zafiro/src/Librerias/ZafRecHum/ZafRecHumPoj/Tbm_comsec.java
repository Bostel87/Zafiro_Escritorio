
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_comsec
 * @author Carlos Lainez
 * Guayaquil 24/03/2011
 */
public class Tbm_comsec implements Cloneable {

    private int intCo_comsec;
    //private String strTx_codcar;
    private String strTx_nomcomsec;
    //private BigDecimal bigNd_minsec;
    private String strTx_obs1;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;

    /**
     * @return the intCo_comsec
     */
    public int getIntCo_comsec() {
        return intCo_comsec;
    }

    /**
     * @param intCo_comsec the intCo_comsec to set
     */
    public void setIntCo_comsec(int intCo_comsec) {
        this.intCo_comsec = intCo_comsec;
    }

    /**
     * @return the strTx_codcar
     *
    public String getStrTx_codcar() {
        return strTx_codcar;
    }*/

    /**
     * @param strTx_codcar the strTx_codcar to set
     *
    public void setStrTx_codcar(String strTx_codcar) {
        this.strTx_codcar = strTx_codcar;
    }*/

    /**
     * @return the strTx_nomcomsec
     */
    public String getStrTx_nomcomsec() {
        return strTx_nomcomsec;
    }

    /**
     * @param strTx_nomcomsec the strTx_nomcomsec to set
     */
    public void setStrTx_nomcomsec(String strTx_nomcomsec) {
        this.strTx_nomcomsec = strTx_nomcomsec;
    }

    /**
     * @return the bigNd_minsec
     *
    public BigDecimal getBigNd_minsec() {
        return bigNd_minsec;
    }*/

    /**
     * @param bigNd_minsec the bigNd_minsec to set
     *
    public void setBigNd_minsec(BigDecimal bigNd_minsec) {
        this.bigNd_minsec = bigNd_minsec;
    }*/

    /**
     * @return the strTx_obs1
     */
    public String getStrTx_obs1() {
        return strTx_obs1;
    }

    /**
     * @param strTx_obs1 the strTx_obs1 to set
     */
    public void setStrTx_obs1(String strTx_obs1) {
        this.strTx_obs1 = strTx_obs1;
    }

    /**
     * @return the strSt_reg
     */
    public String getStrSt_reg() {
        return strSt_reg;
    }

    /**
     * @param strSt_reg the strSt_reg to set
     */
    public void setStrSt_reg(String strSt_reg) {
        this.strSt_reg = strSt_reg;
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

}
