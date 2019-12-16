
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Comisiones Sectoriales.
 * @author Carlos Lainez
 * Guayaquil 04/04/2011
 */
public class ZafVenComSec extends ZafVenCon {

    public ZafVenComSec(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_comsec,tx_nomcomsec,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_comsec where st_reg like 'A' order by co_comsec",
                new ArrayList<String>(){{
                    add("co_comsec");
                    //add("tx_codcar");
                    add("tx_nomcomsec");
                    //add("nd_minsec");
                    add("st_reg");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    //add("Código de Cargo");
                    add("Nombre de Cargo");
                    //add("Mínimo Sectorial");
                    add("Estado");
                }},
                new ArrayList<String>(){{
                    add("50");
                    //add("110");
                    add("300");
                    //add("50");
                    add("50");
                }});
    }
}
