
package Librerias.ZafMae.ZafMaeVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Estado Civil.
 * @author Carlos Lainez
 * Guayaquil 04/04/2011
 */
public class ZafVenEstCiv extends ZafVenCon {

    public ZafVenEstCiv(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_reg, tx_desCor, tx_desLar, co_grp, tx_tipUniMed, tx_obs1, st_reg from tbm_var where co_grp = 1 and st_reg like 'A' order by tx_desLar",
                new ArrayList<String>(){{
                    add("co_grp");
                    add("co_reg");
                    add("tx_desLar");
                }},
                new ArrayList<String>(){{
                    add("Cód.Grp.");
                    add("Código");
                    add("Estado Civil");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("50");
                    add("300");
                }},
                new int[]{1});
    }
}
