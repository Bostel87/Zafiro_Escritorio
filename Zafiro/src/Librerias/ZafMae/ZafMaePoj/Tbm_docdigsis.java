
package Librerias.ZafMae.ZafMaePoj;

/**
 * Pojo contiene campos de la tabla tbm_docdigsis
 * @author Carlos Lainez
 * Guayaquil 24/05/2011
 */
public class Tbm_docdigsis implements Cloneable {
    private int intCo_emp;
    private int intCo_loc;
    private int intCo_mnu;
    private String strTx_rutabs;
    private String strTx_rutrel;

    /**
     * @return the intCo_emp
     */
    public int getIntCo_emp() {
        return intCo_emp;
    }

    /**
     * @param intCo_emp the intCo_emp to set
     */
    public void setIntCo_emp(int intCo_emp) {
        this.intCo_emp = intCo_emp;
    }

    /**
     * @return the intCo_loc
     */
    public int getIntCo_loc() {
        return intCo_loc;
    }

    /**
     * @param intCo_loc the intCo_loc to set
     */
    public void setIntCo_loc(int intCo_loc) {
        this.intCo_loc = intCo_loc;
    }

    /**
     * @return the intCo_mnu
     */
    public int getIntCo_mnu() {
        return intCo_mnu;
    }

    /**
     * @param intCo_mnu the intCo_mnu to set
     */
    public void setIntCo_mnu(int intCo_mnu) {
        this.intCo_mnu = intCo_mnu;
    }

    /**
     * @return the strTx_rutabs
     */
    public String getStrTx_rutabs() {
        return strTx_rutabs;
    }

    /**
     * @param strTx_rutabs the strTx_rutabs to set
     */
    public void setStrTx_rutabs(String strTx_rutabs) {
        this.strTx_rutabs = strTx_rutabs;
    }

    /**
     * @return the strTx_rutrel
     */
    public String getStrTx_rutrel() {
        return strTx_rutrel;
    }

    /**
     * @param strTx_rutrel the strTx_rutrel to set
     */
    public void setStrTx_rutrel(String strTx_rutrel) {
        this.strTx_rutrel = strTx_rutrel;
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
