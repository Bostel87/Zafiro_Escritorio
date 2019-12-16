
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_contra
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_contra implements Cloneable {

    private int intCo_tra;
    private int intCo_reg;
    private String strTx_nom;
    private String strTx_tel1;
    private String strTx_tel2;
    private String strSt_llacaseme;
    private String strTx_obs1;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;
    private Date DatFe_nac;
    private int intCo_tipfamcon;

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
     * @return the strTx_nom
     */
    public String getStrTx_nom() {
        return strTx_nom;
    }

    /**
     * @param strTx_nom the strTx_nom to set
     */
    public void setStrTx_nom(String strTx_nom) {
        this.strTx_nom = strTx_nom;
    }

    /**
     * @return the strTx_tel1
     */
    public String getStrTx_tel1() {
        return strTx_tel1;
    }

    /**
     * @param strTx_tel1 the strTx_tel1 to set
     */
    public void setStrTx_tel1(String strTx_tel1) {
        this.strTx_tel1 = strTx_tel1;
    }

    /**
     * @return the strTx_tel2
     */
    public String getStrTx_tel2() {
        return strTx_tel2;
    }

    /**
     * @param strTx_tel2 the strTx_tel2 to set
     */
    public void setStrTx_tel2(String strTx_tel2) {
        this.strTx_tel2 = strTx_tel2;
    }

    /**
     * @return the strSt_llacaseme
     */
    public String getStrSt_llacaseme() {
        return strSt_llacaseme;
    }

    /**
     * @param strSt_llacaseme the strSt_llacaseme to set
     */
    public void setStrSt_llacaseme(String strSt_llacaseme) {
        this.strSt_llacaseme = strSt_llacaseme;
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
     * @return the DatFe_nac
     */
    public Date getDatFe_nac() {
        return DatFe_nac;
    }

    /**
     * @param DatFe_nac the DatFe_nac to set
     */
    public void setDatFe_nac(Date DatFe_nac) {
        this.DatFe_nac = DatFe_nac;
    }

    /**
     * @return the intCo_tipfamcon
     */
    public int getIntCo_tipfamcon() {
        return intCo_tipfamcon;
    }

    /**
     * @param intCo_tipfamcon the intCo_tipfamcon to set
     */
    public void setIntCo_tipfamcon(int intCo_tipfamcon) {
        this.intCo_tipfamcon = intCo_tipfamcon;
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
