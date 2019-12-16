/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Departamentos.
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public class ZafVenDep extends ZafVenCon {

    public ZafVenDep(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com),
                zafParSis,
                strTit,
                "select co_dep,tx_descor,tx_deslar,st_reg from tbm_dep where st_reg like 'A' order by co_dep",
                new ArrayList<String>(){{
                    add("co_dep");
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
                    add("110");
                    add("110");
                    add("50");
                }});
    }

}