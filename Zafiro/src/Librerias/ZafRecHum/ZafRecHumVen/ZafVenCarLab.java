
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Cargos Laborales.
 * @author Roberto Flores
 * Guayaquil 17/11/2011
 */
public class ZafVenCarLab extends ZafVenCon {

    public ZafVenCarLab(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_car,tx_nomcar,co_comsec,tx_codcomsec,tx_nomcarcomsec,nd_minsec,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_carlab where st_reg like 'A' order by co_car",
                new ArrayList<String>(){{
                    add("co_car");
                    add("tx_nomcar");
                    add("st_reg");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Nombre");
                    add("Estado");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("110");
                    add("50");
                }});
    }
}
