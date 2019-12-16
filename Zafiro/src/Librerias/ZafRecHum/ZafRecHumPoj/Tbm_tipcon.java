
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_tipcon
 * @author Carlos Lainez
 * Guayaquil 29/03/2011
 */
public class Tbm_tipcon implements Cloneable {

    private int intCo_tipcon;
    private String strTx_deslar;
    private int intNe_mescon;
    private String strTx_obs1;
    private String strSt_reg;
    private Date DatFe_ing;
    private Date DatFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;
    private int intNe_diapru;

    /**
     * @return the intCo_tipcon
     */
    public int getIntCo_tipcon() {
        return intCo_tipcon;
    }

    /**
     * @param intCo_tipcon the intCo_tipcon to set
     */
    public void setIntCo_tipcon(int intCo_tipcon) {
        this.intCo_tipcon = intCo_tipcon;
    }

    /**
     * @return the strTx_deslar
     */
    public String getStrTx_deslar() {
        return strTx_deslar;
    }

    /**
     * @param strTx_deslar the strTx_deslar to set
     */
    public void setStrTx_deslar(String strTx_deslar) {
        this.strTx_deslar = strTx_deslar;
    }

    /**
     * @return the intNe_mescon
     */
    public int getIntNe_mescon() {
        return intNe_mescon;
    }

    /**
     * @param intNe_mescon the intNe_mescon to set
     */
    public void setIntNe_mescon(int intNe_mescon) {
        this.intNe_mescon = intNe_mescon;
    }

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
     * @return the DatFe_ing
     */
    public Date getDatFe_ing() {
        return DatFe_ing;
    }

    /**
     * @param DatFe_ing the DatFe_ing to set
     */
    public void setDatFe_ing(Date DatFe_ing) {
        this.DatFe_ing = DatFe_ing;
    }

    /**
     * @return the DatFe_ultmod
     */
    public Date getDatFe_ultmod() {
        return DatFe_ultmod;
    }

    /**
     * @param DatFe_ultmod the DatFe_ultmod to set
     */
    public void setDatFe_ultmod(Date DatFe_ultmod) {
        this.DatFe_ultmod = DatFe_ultmod;
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
     * @return the intNe_diapru
     */
    public int getIntNe_diapru() {
        return intNe_diapru;
    }

    /**
     * @param intNe_diapru the intNe_diapru to set
     */
    public void setIntNe_diapru(int intNe_diapru) {
        this.intNe_diapru = intNe_diapru;
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
