
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Tipos de Contrato.
 * @author Carlos Lainez
 * Guayaquil 05/04/2011
 */
public class ZafVenTipCon extends ZafVenCon {

    public ZafVenTipCon(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_tipcon,tx_deslar,ne_mescon,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_tipcon where st_reg like 'A' order by tx_deslar",
                new ArrayList<String>(){{
                    add("co_tipcon");
                    add("tx_deslar");
                    add("ne_mescon");
                    add("st_reg");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Descripción");
                    add("Meses de Contrato");
                    add("Estado");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("300");
                    add("110");
                    add("50");
                }});
    }
}
