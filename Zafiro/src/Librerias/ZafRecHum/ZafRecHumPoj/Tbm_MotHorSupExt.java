/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumPoj;

/**
 * Pojo contiene la informacion del detalle de los motivos de horas suplementarias/extraordinarias ZafRecHum29
 * @author Roberto Flores
 * Guayaquil 26/03/2012
 */
public class Tbm_MotHorSupExt implements Cloneable{
   
    private int intCo_mot;
    private String strTx_DesCor;
    private String strTx_DesLar;
    private String strSt_Reg;

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

    /**
     * @return the intCo_mot
     */
    public int getIntCo_mot() {
        return intCo_mot;
    }

    /**
     * @param intCo_mot the intCo_mot to set
     */
    public void setIntCo_mot(int intCo_mot) {
        this.intCo_mot = intCo_mot;
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

}