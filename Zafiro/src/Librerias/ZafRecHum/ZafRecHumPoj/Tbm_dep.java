/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumPoj;

/**
 * Pojo contiene la informacion del detalle de los departamentos ZafRecHum23
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public class Tbm_dep implements Cloneable{

    private int intCo_Dep;
    private String strTx_DesCor;
    private String strTx_DesLar;
    private String strSt_Reg;

    /**
     * @return the intCo_Dep
     */
    public int getIntCo_Dep() {
        return intCo_Dep;
    }

    /**
     * @param intCo_Dep the intCo_Dep to set
     */
    public void setIntCo_Dep(int intCo_Dep) {
        this.intCo_Dep = intCo_Dep;
    }

    /**
     * @return the strTx_DesCor
     */
    public String getStrTx_DesCor() {
        return strTx_DesCor;
    }

    /**
     * @param strTx_DesCor the strTx_DesCor to set
     */
    public void setStrTx_DesCor(String strTx_DesCor) {
        this.strTx_DesCor = strTx_DesCor;
    }

    /**
     * @return the strTx_DesLar
     */
    public String getStrTx_DesLar() {
        return strTx_DesLar;
    }

    /**
     * @param strTx_DesLar the strTx_DesLar to set
     */
    public void setStrTx_DesLar(String strTx_DesLar) {
        this.strTx_DesLar = strTx_DesLar;
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
