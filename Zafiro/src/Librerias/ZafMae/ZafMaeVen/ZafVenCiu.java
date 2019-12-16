
package Librerias.ZafMae.ZafMaeVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Ciudades.
 * @author Carlos Lainez
 * Guayaquil 04/04/2011
 */
public class ZafVenCiu extends ZafVenCon {

    public ZafVenCiu(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_ciu, tx_desCor, tx_desLar, co_pai, st_reg from tbm_ciu where co_pai=6 and st_reg like 'A' order by tx_desLar",
                new ArrayList<String>(){{
                    add("co_pai");
                    add("co_ciu");
                    add("tx_desLar");
                }},
                new ArrayList<String>(){{
                    add("Cód.Pais");
                    add("Cód.Ciu.");
                    add("Ciudad");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("50");
                    add("300");
                }},
                new int[] {1});
    }
}
