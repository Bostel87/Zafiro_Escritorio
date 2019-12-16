package Librerias.ZafGetDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 *
 * @author Sistemas9
 */
public class ZafDatLocInm
{    
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private int intCodLocInm;
    private int intCodLocPre;
    private String strVersion=" v 0.1";
    
    public ZafDatLocInm(ZafParSis obj)
    {
        try
        {
            //System.out.println("ZafDatLocInm "+strVersion);
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
        }
        catch (CloneNotSupportedException e)
        {
            System.out.println("Error");
        }
    }
    
    /**
     * Función que retorna el local de Inmaconsa, correspondiente al local indicado.
     * @return intCodLocInm Código del local de Inmaconsa.
     * <BR>Ejemplo: Si el local enviado es Tuval California, esta función retorna el Código del local de Inmaconsa enlazado, es decir, "Tuval - Inmaconsa"</BR>
     */
    public boolean cargarLocInm()
    {
        boolean blnRes=true;
        intCodLocInm=-1;
        try
        {
            if(objParSis.getCodigoEmpresa()==1 ) /* TUVAL */
            { 
                if(objParSis.getCodigoLocal()==4 ){  //Tuval - California
                    intCodLocInm=12;
                }
            }
            else if(objParSis.getCodigoEmpresa()==4)  /* DIMULTI */
            {
                if(objParSis.getCodigoLocal()==3){         //Dimulti - Vía a Daule.
                    intCodLocInm=10;
                }
                else if(objParSis.getCodigoLocal()==12){    //Dimulti - Durán.
                    intCodLocInm=13;
                }
            }
            else if(objParSis.getCodigoEmpresa()==2) /* CASTEK */
            { 
                if(objParSis.getCodigoLocal()==1){     //Castek - Quito 
                    intCodLocInm=11;
                }
                else if(objParSis.getCodigoLocal()==4){ //Castek - Manta
                    intCodLocInm=12;
                }
                else if(objParSis.getCodigoLocal()==6){  //Castek - Santo Domingo
                    intCodLocInm=13;
                }
                else if(objParSis.getCodigoLocal()==10){  //Castek - Cuenca
                    intCodLocInm=14;
                }
            }
            if(intCodLocInm<0){
                blnRes=false;
            }    
        }
        catch (Exception e) {
            return blnRes;
        }
        return blnRes;
    }
        
    /**
     * Función que retorna el local predeterminado, correspondiente al local de Inmaconsa indicado.
     * @return intCodLocPreInm Código del local predeterminado para el local de Inmaconsa indicado.
     * <BR>Ejemplo: Si el local enviado es Tuval Inmaconsa, esta función retorna el Código del local de Predeterminado enlazado, es decir, "Tuval - California"</BR>
     */
    public boolean cargarLocPre()
    {
        boolean blnRes=true;
        intCodLocPre=-1;
        try
        {
            if(objParSis.getCodigoEmpresa()==1 ) /* TUVAL */
            { 
                if(objParSis.getCodigoLocal()==12 ){ 
                    intCodLocPre=4;  //Tuval - California
                }
            }
            else if(objParSis.getCodigoEmpresa()==4)  /* DIMULTI */
            {
                if(objParSis.getCodigoLocal()==10){        
                    intCodLocPre=3;  //Dimulti - Vía a Daule.
                }
                else if(objParSis.getCodigoLocal()==13){    
                    intCodLocPre=12; //Dimulti - Durán.
                }
            }
            else if(objParSis.getCodigoEmpresa()==2) /* CASTEK */
            { 
                if(objParSis.getCodigoLocal()==11){     
                    intCodLocPre=1;              //Castek - Quito 
                }
                else if(objParSis.getCodigoLocal()==12){ 
                    intCodLocPre=4;             //Castek - Manta
                }
                else if(objParSis.getCodigoLocal()==13){  
                    intCodLocPre=6;             //Castek - Santo Domingo
                }
                else if(objParSis.getCodigoLocal()==14){  
                    intCodLocPre=10;           //Castek - Cuenca
                }
            }
            if(intCodLocPre<0){
                blnRes=false;
            }    
        }
        catch (Exception e) {
            return blnRes;
        }
        return blnRes;
    }
    
    /**
     * Obtiene el local de Inmaconsa del local indicado.
     * @return intCodLocInm: Código del local de Inmaconsa.
     */
    public int getLocInm(){
        return intCodLocInm;
    }
    
    /**
     * Establece el local de inmaconsa del local indicado.
     * @param locInm 
     */
    public void setLocInm(int locInm){
        intCodLocInm=locInm;
    }
       
    /**
     * Obtiene el local predeterminado al que pertenece el local de Inmaconsa.
     * @return intCodLocPreInm: Código del local de Inmaconsa.
     */
    public int getLocPre(){
        return intCodLocPre;
    }
    
    /**
     * Establece el local predeterminado al que pertenece el local de Inmaconsa.
     * @param locPre
     */
    public void setLocPre(int locPre){
        intCodLocPre=locPre;
    }
    
    
}
