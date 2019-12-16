
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
public class ZafVenUsr extends ZafVenCon {

    public ZafVenUsr(Component com, ZafParSis zafParSis, String strTit) {
        super(JOptionPane.getFrameForComponent(com), 
                zafParSis,
                strTit,
                //"SELECT u.co_usr,u.tx_nom,g.tx_desLar,u.tx_usr,u.st_usrSis,u.st_reg,g.co_grp FROM tbm_usr as u, tbm_grpUsr as g WHERE g.co_grp = u.co_grpUsr and u.st_reg like 'A' and u.co_usr not in (1) order by u.co_usr",
                "SELECT u.co_usr,u.tx_nom,g.tx_desLar,u.tx_usr FROM tbm_usr as u, tbm_grpUsr as g WHERE g.co_grp = u.co_grpUsr and u.st_reg like 'A' and u.co_usr not in (1) order by u.co_usr",
                new ArrayList<String>(){{
                    add("co_usr");
                    add("tx_nom");
                    add("tx_desLar");
                    add("tx_usr");
                }},
                new ArrayList<String>(){{
                    add("Código");
                    add("Nombre");
                    add("Grupo");
                    add("Usuario");
                }},
                new ArrayList<String>(){{
                    add("50");
                    add("110");
                    add("300");
                    add("300");
                }});
    }
}