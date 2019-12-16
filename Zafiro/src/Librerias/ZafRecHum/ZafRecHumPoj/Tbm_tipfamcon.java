
package Librerias.ZafRecHum.ZafRecHumPoj;

import java.util.Date;

/**
 * Pojo contiene campos de la tabla tbm_tipfamcon
 * @author Carlos Lainez
 * Guayaquil 15/04/2011
 */
public class Tbm_tipfamcon implements Cloneable {
    private int intCo_tipfamcon;
    private String strTx_deslar;
    private String strTx_tipcarfam;
    private String strTx_obs1;
    private String strSt_reg;
    private Date datFe_ing;
    private Date datFe_ultmod;
    private int intCo_usring;
    private int intCo_usrmod;

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
     * @return the strTx_tipcarfam
     */
    public String getStrTx_tipcarfam() {
        return strTx_tipcarfam;
    }

    /**
     * @param strTx_tipcarfam the strTx_tipcarfam to set
     */
    public void setStrTx_tipcarfam(String strTx_tipcarfam) {
        this.strTx_tipcarfam = strTx_tipcarfam;
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
