
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Trabajadores.
 * @author Carlos Lainez
 * Guayaquil 04/04/2011
 */
public class ZafVenTra extends ZafVenCon {

    public ZafVenTra(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com), 
                zafParSis,
                strTit,
                //"select co_tra,tx_ide,tx_nom,tx_ape,tx_dir,tx_refdir,tx_tel1,tx_tel2,tx_tel3,tx_corele,tx_sex,fe_nac,co_ciunac,co_estciv,ne_numhij,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_tra where st_reg like 'A' order by tx_ape",
                "select co_tra,tx_ide,tx_nom,tx_ape,tx_dir,tx_refdir,tx_tel1,tx_tel2,tx_tel3,tx_corele,tx_sex,fe_nac,co_ciunac,co_estciv,ne_numhij,tx_obs1,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod from tbm_tra where st_reg like 'A' order by co_tra",
                new ArrayList<String>(){{
                    add("co_tra");
                    add("tx_ide");
                    add("tx_ape");
                    add("tx_nom");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Cédula");
                    add("Apellidos");
                    add("Nombres");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("110");
                    add("300");
                    add("300");
                //}},
                //new int[]{1});
                }});
    }
}