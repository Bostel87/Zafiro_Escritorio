
package Librerias.ZafMae.ZafMaeVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Tipos de Documentos Digitales
 * @author Carlos Lainez
 * Guayaquil 18/05/2011
 */
public class ZafVenTipDocDig extends ZafVenCon {

    public ZafVenTipDocDig(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_tipdocdig,tx_descor,tx_deslar,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_tipdocdigsis where st_reg like 'A' order by tx_deslar",
                new ArrayList<String>(){{
                    add("co_tipdocdig");
                    add("tx_descor");
                    add("tx_deslar");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Cód.Doc.");
                    add("Nombre");
                }},
                new ArrayList<String>(){{
                    add("40");
                    add("100");
                    add("300");
                }});
    }
}
