/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_varDAOInterface;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Objeto que se encarga de manejar las transacciones a la base de datos en un marco de conexión.
 * @author Roberto Flores
 * Guayaquil 24/10/2011
 */
public class Tbm_varBO {

    private Tbm_varDAOInterface Tbm_varDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;


    public Tbm_varBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            Tbm_varDAOInterface = (Tbm_varDAOInterface) Tbm_varBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_varDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_varDAO");
        }
    }

    /**
     * Consulta de estado civil por codigo.
     * @param intEstCiv el codigo del estado civil.
     * @return Retorna un string con el nombre del estado civil relacionado al codigo.
     * @throws Exception
     */
    public String consultarEstadoCivil(int intEstCiv) throws Exception{
        String strTx_DesLar  = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                strTx_DesLar = Tbm_varDAOInterface.consultarEstadoCivil(con, intEstCiv);
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

    /**
     * Consulta de estado civil por descripcion larga.
     * @param strEstCiv descripcion larga del estado civil.
     * @return Retorna un int con el codigo del estado civil relacionado a la descripcion larga.
     * @throws Exception
     */
    public String consultarEstadoCivil(String strEstCiv) throws Exception{
        //int intEstCiv  = 0;
        String strResEstCiv  = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                strResEstCiv = Tbm_varDAOInterface.consultarEstadoCivil(con, strEstCiv);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return strResEstCiv;
    }

}
