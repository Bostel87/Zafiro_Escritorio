
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_docdigtra
 * @author Carlos Lainez
 * Guayaquil 19/05/2011
 */
public class Tbm_docdigtra implements Cloneable {

    private int intCo_tra;
    private int intCo_reg;
    private int intCo_tipdocdig;
    private String strTx_nomarc;
    private String strTx_obs1;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;

    /**
     * @return the intCo_tra
     */
    public int getIntCo_tra() {
        return intCo_tra;
    }

    /**
     * @param intCo_tra the intCo_tra to set
     */
    public void setIntCo_tra(int intCo_tra) {
        this.intCo_tra = intCo_tra;
    }

    /**
     * @return the intCo_reg
     */
    public int getIntCo_reg() {
        return intCo_reg;
    }

    /**
     * @param intCo_reg the intCo_reg to set
     */
    public void setIntCo_reg(int intCo_reg) {
        this.intCo_reg = intCo_reg;
    }

    /**
     * @return the intCo_tipdocdig
     */
    public int getIntCo_tipdocdig() {
        return intCo_tipdocdig;
    }

    /**
     * @param intCo_tipdocdig the intCo_tipdocdig to set
     */
    public void setIntCo_tipdocdig(int intCo_tipdocdig) {
        this.intCo_tipdocdig = intCo_tipdocdig;
    }

    /**
     * @return the strTx_nomarc
     */
    public String getStrTx_nomarc() {
        return strTx_nomarc;
    }

    /**
     * @param strTx_nomarc the strTx_nomarc to set
     */
    public void setStrTx_nomarc(String strTx_nomarc) {
        this.strTx_nomarc = strTx_nomarc;
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
