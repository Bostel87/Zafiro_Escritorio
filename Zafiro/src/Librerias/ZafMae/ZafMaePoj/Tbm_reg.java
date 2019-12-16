
package Librerias.ZafMae.ZafMaePoj;

/**
 * Pojo contiene campos de la tabla tbm_reg
 * @author Carlos Lainez
 * Guayaquil 13/05/2011
 */
public class Tbm_reg implements Cloneable {

    private int intCo_reg;
    private String strTx_descor;
    private String strTx_deslar;
    private int intNe_mespagdeccuasue;
    private int intNe_mesinicaldeccuasue;
    private int intNe_mesfincaldeccuasue;
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
     * @return the intNe_mespagdeccuasue
     */
    public int getIntNe_mespagdeccuasue() {
        return intNe_mespagdeccuasue;
    }

    /**
     * @param intNe_mespagdeccuasue the intNe_mespagdeccuasue to set
     */
    public void setIntNe_mespagdeccuasue(int intNe_mespagdeccuasue) {
        this.intNe_mespagdeccuasue = intNe_mespagdeccuasue;
    }

    /**
     * @return the intNe_mesinicaldeccuasue
     */
    public int getIntNe_mesinicaldeccuasue() {
        return intNe_mesinicaldeccuasue;
    }

    /**
     * @param intNe_mesinicaldeccuasue the intNe_mesinicaldeccuasue to set
     */
    public void setIntNe_mesinicaldeccuasue(int intNe_mesinicaldeccuasue) {
        this.intNe_mesinicaldeccuasue = intNe_mesinicaldeccuasue;
    }

    /**
     * @return the intNe_mesfincaldeccuasue
     */
    public int getIntNe_mesfincaldeccuasue() {
        return intNe_mesfincaldeccuasue;
    }

    /**
     * @param intNe_mesfincaldeccuasue the intNe_mesfincaldeccuasue to set
     */
    public void setIntNe_mesfincaldeccuasue(int intNe_mesfincaldeccuasue) {
        this.intNe_mesfincaldeccuasue = intNe_mesfincaldeccuasue;
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
