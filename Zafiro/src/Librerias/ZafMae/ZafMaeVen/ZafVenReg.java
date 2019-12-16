
package Librerias.ZafMae.ZafMaeVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Regiones
 * @author Carlos Lainez
 * Guayaquil 13/05/2011
 */
public class ZafVenReg extends ZafVenCon {

    public ZafVenReg(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_reg,tx_descor,tx_deslar,ne_mespagdeccuasue,ne_mesinicaldeccuasue,ne_mesfincaldeccuasue,st_reg from tbm_reg where st_reg like 'A' order by co_reg",
                new ArrayList<String>(){{
                    add("co_reg");
                    add("tx_descor");
                    add("tx_deslar");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Cód.Reg.");
                    add("Nombre");
                }},
                new ArrayList<String>(){{
                    add("70");
                    add("70");
                    add("300");
                }});
    }
}
