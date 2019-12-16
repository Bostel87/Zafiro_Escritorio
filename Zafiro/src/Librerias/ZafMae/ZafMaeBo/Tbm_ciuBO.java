/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_ciuDAOInterface;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

/**
 * Objeto que se encarga de manejar las transacciones a la base de datos en un marco de conexión.
 * @author Roberto Flores
 * Guayaquil 24/10/2011
 */
public class Tbm_ciuBO {

    private Tbm_ciuDAOInterface Tbm_ciuDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;


    public Tbm_ciuBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            //tbm_hortraDAOInterface = (Tbm_hortraDAOInterface) Tbm_hortraBO.class.getClassLoader().loadClass("RecursosHumanos.DAO.Tbm_hortraDAO").newInstance();
            Tbm_ciuDAOInterface = (Tbm_ciuDAOInterface) Tbm_ciuBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_ciuDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_ciuDAO");
        }
    }

    /**
     * Consulta de ciudad por codigo.
     * @param intCodCiu el codigo de la ciudad
     * @return Retorna un string con el nombre de la ciudad perteneciente al codigo ciudad
     * @throws Exception
     */
    public String consultarCiudad(int intCodCiu) throws Exception{
        String strTx_DesLar  = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                strTx_DesLar = Tbm_ciuDAOInterface.consultarCiudad(con, intCodCiu);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return strTx_DesLar;
    }

}
