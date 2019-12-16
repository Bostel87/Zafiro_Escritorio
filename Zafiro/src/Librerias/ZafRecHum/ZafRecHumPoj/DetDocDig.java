
package Librerias.ZafRecHum.ZafRecHumPoj;

import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;



/**
 * Pojo contiene la informacion del detalle de los documentos digitales para la pantalla de empleados ZafRecHum03
 * @author Carlos Lainez
 * Guayaquil 20/05/2011
 */
public class DetDocDig implements Cloneable {
    private Tbm_docdigtra tbm_docdigtra;
    private Tbm_tipdocdigsis tbm_tipdocdigsis;
    private String strArcLoc;
    private String strNomArc;
    private String strRutArc;

    /**
     * @return the tbm_docdigtra
     */
    public Tbm_docdigtra getTbm_docdigtra() {
        return tbm_docdigtra;
    }

    /**
     * @param tbm_docdigtra the tbm_docdigtra to set
     */
    public void setTbm_docdigtra(Tbm_docdigtra tbm_docdigtra) {
        this.tbm_docdigtra = tbm_docdigtra;
    }

    /**
     * @return the tbm_tipdocdigsis
     */
    public Tbm_tipdocdigsis getTbm_tipdocdigsis() {
        return tbm_tipdocdigsis;
    }

    /**
     * @param tbm_tipdocdigsis the tbm_tipdocdigsis to set
     */
    public void setTbm_tipdocdigsis(Tbm_tipdocdigsis tbm_tipdocdigsis) {
        this.tbm_tipdocdigsis = tbm_tipdocdigsis;
    }

    /**
     * @return the strArcLoc
     */
    public String getStrArcLoc() {
        return strArcLoc;
    }

    /**
     * @return the strNomArc
     */
    public String getStrNomArc() {
        return strNomArc;
    }

    /**
     * @param strNomArc the strNomArc to set
     */
    public void setStrNomArc(String strNomArc) {
        this.strNomArc = strNomArc;
    }

    /**
     * @return the strRutArc
     */
    public String getStrRutArc() {
        return strRutArc;
    }

    /**
     * @param strRutArc the strRutArc to set
     */
    public void setStrRutArc(String strRutArc) {
        this.strRutArc = strRutArc;
    }

    /**
     * @param strArcLoc the strArcLoc to set
     */
    public void setStrArcLoc(String strArcLoc) {
        this.strArcLoc = strArcLoc;
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
