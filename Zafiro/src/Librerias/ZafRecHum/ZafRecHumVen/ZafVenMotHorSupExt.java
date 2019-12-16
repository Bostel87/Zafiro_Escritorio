package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Motivos de horas suplementarias/extraordinarias.
 * @author Roberto Flores
 * Guayaquil 29/03/2012
 */
public class ZafVenMotHorSupExt extends ZafVenCon {

    public ZafVenMotHorSupExt(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com), 
                zafParSis,
                strTit,
                "select co_mot, tx_descor, tx_deslar, st_reg from tbm_mothorsupext where st_reg like 'A' order by co_mot",
                new ArrayList<String>(){{
                    add("co_mot");
                    add("tx_descor");
                    add("tx_deslar");
                    add("st_reg");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Descripción corta");
                    add("Descripción larga");
                    add("Estado");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("120");
                    add("200");
                    add("50");
                }});
    }
}