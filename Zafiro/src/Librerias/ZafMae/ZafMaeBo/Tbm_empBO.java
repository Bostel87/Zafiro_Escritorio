
package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_empDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 23/06/2011
 */
public class Tbm_empBO {
    private Tbm_empDAOInterface tbm_empDAOInterface;
    private ZafParSis zafParSis;

    public Tbm_empBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        try{
            tbm_empDAOInterface = (Tbm_empDAOInterface) Tbm_empBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_empDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_empDAOInterface");
        }
    }

    /**
     * Consulta General según parámetros ingresados por pantalla
     * @param tbm_emp Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_emp con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_emp> consultarLisGen(Tbm_emp tbm_emp) throws Exception{
        List<Tbm_emp> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_empDAOInterface.consultarLisGen(con, tbm_emp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_emp;
    }

    /**
     * Consulta todas las empresas sin incluir la empresa grupo
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws Exception
     */
    public List<Tbm_emp> consultarLisGenSinEmpGru() throws Exception{
        List<Tbm_emp> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_empDAOInterface.consultarLisGenSinEmpGru(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_emp;
    }

    /**
     * Consulta empresa por nombre
     * @param strNomEmp Nombre de la empresa a buscar
     * @return Retorna una lista de tipo Tbm_emp con los datos de la empresa
     * @throws Exception
     */
    public List<Tbm_emp> consultarLisGenPorNomEmp(String strNomEmp) throws Exception{
        List<Tbm_emp> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_empDAOInterface.consultarLisGenPorNomEmp(con, strNomEmp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_emp;
    }
}
