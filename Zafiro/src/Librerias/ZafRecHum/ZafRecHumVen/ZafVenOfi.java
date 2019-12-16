
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Usuarios.
 * @author Roberto Flores
 * Guayaquil 23/03/2012
 */
public class ZafVenOfi extends ZafVenCon {

    public ZafVenOfi(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com), 
                zafParSis,
                strTit,
                "select co_ofi, tx_nom from tbm_ofi where st_reg like 'A' order by co_ofi",
                new ArrayList<String>(){{
                    add("co_ofi");
                    add("tx_nom");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Nombre");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("110");
                }});
    }
}