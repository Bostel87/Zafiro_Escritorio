
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Horarios de Trabajo.
 * @author Roberto Flores
 * Guayaquil 12/09/2011
 */
public class ZafVenHorTra extends ZafVenCon {

    public ZafVenHorTra(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_hor,tx_nom,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_cabhortra where st_reg like 'A' order by co_hor",
                new ArrayList<String>(){{
                    add("co_hor");
                    add("tx_nom");
                    add("st_reg");
                }},
                new ArrayList<String>(){{
                    add("Código de Horario");
                    add("Nombre de Horario");
                    add("Estado");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("200");
                    add("50");
                }});
    }
}