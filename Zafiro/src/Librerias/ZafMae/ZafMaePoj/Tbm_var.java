/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMae.ZafMaePoj;

/**
 * Pojo contiene campos de la tabla tbm_var
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_var implements Cloneable {

    private int intCo_reg;
    private String strTx_descor;
    private String strTx_deslar;
    private int intCo_grp;
    private String strTx_tipunimed;
    private String strTx_obs1;
    private String strSt_reg;

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
     * @return the strTx_descor
     */
    public String getStrTx_descor() {
        return strTx_descor;
    }

    /**
     * @param strTx_descor the strTx_descor to set
     */
    public void setStrTx_descor(String strTx_descor) {
        this.strTx_descor = strTx_descor;
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
     * @return the intCo_grp
     */
    public int getIntCo_grp() {
        return intCo_grp;
    }

    /**
     * @param intCo_grp the intCo_grp to set
     */
    public void setIntCo_grp(int intCo_grp) {
        this.intCo_grp = intCo_grp;
    }

    /**
     * @return the strTx_tipunimed
     */
    public String getStrTx_tipunimed() {
        return strTx_tipunimed;
    }

    /**
     * @param strTx_tipunimed the strTx_tipunimed to set
     */
    public void setStrTx_tipunimed(String strTx_tipunimed) {
        this.strTx_tipunimed = strTx_tipunimed;
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
