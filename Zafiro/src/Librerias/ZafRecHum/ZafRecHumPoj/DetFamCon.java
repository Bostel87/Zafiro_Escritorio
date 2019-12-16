
package Librerias.ZafRecHum.ZafRecHumPoj;

/**
 * Pojo contiene la informacion del detalle de los familiares y contactos para la pantalla de empleados ZafRecHum03
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class DetFamCon implements Cloneable {
    private Tbm_contra tbm_contra;
    private Tbm_tipfamcon tbm_tipfamcon;

    /**
     * @return the tbm_contra
     */
    public Tbm_contra getTbm_contra() {
        return tbm_contra;
    }

    /**
     * @param tbm_contra the tbm_contra to set
     */
    public void setTbm_contra(Tbm_contra tbm_contra) {
        this.tbm_contra = tbm_contra;
    }

    /**
     * @return the tbm_tipfamcon
     */
    public Tbm_tipfamcon getTbm_tipfamcon() {
        return tbm_tipfamcon;
    }

    /**
     * @param tbm_tipfamcon the tbm_tipfamcon to set
     */
    public void setTbm_tipfamcon(Tbm_tipfamcon tbm_tipfamcon) {
        this.tbm_tipfamcon = tbm_tipfamcon;
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
