
package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_regDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_reg;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 13/05/2011
 */
public class Tbm_regBO {
    private Tbm_regDAOInterface tbm_regDAOInterface;
    private ZafParSis zafParSis;

    public Tbm_regBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;

        try{
            tbm_regDAOInterface = (Tbm_regDAOInterface) Tbm_regBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_regDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_regDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_reg
     * @param tbm_reg Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarReg(Tbm_reg tbm_reg) throws Exception{
        int maxId = 0;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_regDAOInterface.obtenerMaxId(con)+1;
                tbm_reg.setIntCo_reg(maxId);
                tbm_regDAOInterface.grabar(con, tbm_reg);
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Actualiza los datos de la tabla tbm_reg
     * @param tbm_reg Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarReg(Tbm_reg tbm_reg) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_regDAOInterface.actualizar(con, tbm_reg);
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /**
     * Consulta General según parámetros ingresados por pantalla
     * @param tbm_reg Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_reg con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_reg> consultarLisGen(Tbm_reg tbm_reg) throws Exception{
        List<Tbm_reg> lisTbm_reg = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_reg = tbm_regDAOInterface.consultarLisGen(con, tbm_reg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_reg;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_reg Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_reg con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_reg> consultarLisPag(Tbm_reg tbm_reg, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_reg> lisTbm_reg = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_reg = tbm_regDAOInterface.consultarLisPag(con, tbm_reg, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_reg;
    }

}
