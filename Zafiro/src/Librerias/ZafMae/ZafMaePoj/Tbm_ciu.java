
package Librerias.ZafMae.ZafMaePoj;

/**
 * Pojo contiene campos de la tabla tbm_ciu
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class Tbm_ciu implements Cloneable {
    private int intCo_ciu;
    private String strTx_descor;
    private String strTx_deslar;
    private int intCo_pai;
    private String strSt_reg;

    /**
     * @return the intCo_ciu
     */
    public int getIntCo_ciu() {
        return intCo_ciu;
    }

    /**
     * @param intCo_ciu the intCo_ciu to set
     */
    public void setIntCo_ciu(int intCo_ciu) {
        this.intCo_ciu = intCo_ciu;
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
     * @return the intCo_pai
     */
    public int getIntCo_pai() {
        return intCo_pai;
    }

    /**
     * @param intCo_pai the intCo_pai to set
     */
    public void setIntCo_pai(int intCo_pai) {
        this.intCo_pai = intCo_pai;
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
