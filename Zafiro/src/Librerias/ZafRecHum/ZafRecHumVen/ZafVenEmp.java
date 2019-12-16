
package Librerias.ZafRecHum.ZafRecHumVen;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafVenCon.ZafVenCon;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Ventana de Consulta de Empresas.
 * @author Roberto Flores
 * Guayaquil 28/02/2011
 */
public class ZafVenEmp extends ZafVenCon {

    public ZafVenEmp(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com), 
                zafParSis,
                strTit,
                "select co_emp, tx_nom, tx_ruc,st_reg from tbm_emp where st_reg like 'A' order by co_emp",
                new ArrayList<String>(){{
                    add("co_emp");
                    add("tx_nom");
                    add("tx_ruc");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Empresa");
                    add("RUC");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("300");
                    add("110");
                //}},
                //new int[]{1});
                }});
    }

}
