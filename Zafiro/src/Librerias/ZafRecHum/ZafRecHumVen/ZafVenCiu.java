
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
public class ZafVenCiu extends ZafVenCon {

    public ZafVenCiu(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_ciu, tx_descor, tx_deslar from tbm_ciu where st_reg like 'A' order by co_ciu",
                new ArrayList<String>(){{
                    add("co_ciu");
                    add("tx_descor");
                    add("tx_deslar");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Descripción corta");
                    add("Descripción larga");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("70");
                    add("100");
                }});
    }
}
