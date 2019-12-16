
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Tipos de Familiares y Contactos
 * @author Carlos Lainez
 * Guayaquil 18/04/2011
 */
public class ZafVenTipFamCon extends ZafVenCon {

    public ZafVenTipFamCon(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_tipfamcon,tx_deslar,tx_tipcarfam,tx_obs1,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_tipfamcon where st_reg like 'A' order by tx_deslar",
                new ArrayList<String>(){{
                    add("co_tipfamcon");
                    add("tx_deslar");
                    add("tx_tipcarfam");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Descripción");
                    add("Tipo Carga");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("300");
                    add("50");
                }},
                new int[]{3});
    }

}
