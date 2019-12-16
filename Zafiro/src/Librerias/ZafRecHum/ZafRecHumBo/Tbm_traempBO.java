
package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAOInterface;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 17/06/2011
 */
public class Tbm_traempBO {
    private Tbm_traempDAOInterface tbm_traempDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;
    private Tbm_traDAOInterface tbm_traDAOInterface;

    public Tbm_traempBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_traempDAOInterface = (Tbm_traempDAOInterface) Tbm_traempBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAOInterface");
        }
        try{
            tbm_traDAOInterface = (Tbm_traDAOInterface) Tbm_traempBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbr_traemp
     * @param tbr_traemp Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarTraEmp(Tbm_traemp tbr_traemp) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_traempDAOInterface.grabar(con, tbr_traemp);
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
     * Actualiza los datos de la tabla tbr_traemp
     * @param tbr_traemp Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarTraEmp(Tbm_traemp tbr_traemp) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_traempDAOInterface.actualizar(con, tbr_traemp);
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

    public void actualizarTraEmpCodHor(Tbm_traemp tbr_traemp) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_traempDAOInterface.actualizarTraEmpCodHor(con, tbr_traemp);
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
    
    
    
    public void actualizarTraEmpCodHor(Connection con, Tbm_traemp tbr_traemp) throws Exception{
//        Connection con = null;

        try {
            //con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                //con.setAutoCommit(false);
                tbm_traempDAOInterface.actualizarTraEmpCodHor(con, tbr_traemp);
                //con.commit();
            }
        } catch (SQLException ex) {
            //con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            //con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            //con.close();
        }
    }    
    

    /**
     * Consulta General según parámetros ingresados por pantalla
     * @param tbr_traemp Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_traemp con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_traemp> consultarLisGen(Tbm_traemp tbr_traemp) throws Exception{
        List<Tbm_traemp> lisTbr_traemp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbr_traemp = tbm_traempDAOInterface.consultarLisGen(con, tbr_traemp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbr_traemp;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbr_traemp Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_traemp con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_traemp> consultarLisPag(Tbm_traemp tbr_traemp, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_traemp> lisTbr_traemp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbr_traemp = tbm_traempDAOInterface.consultarLisPag(con, tbr_traemp, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbr_traemp;
    }

    /**
     * Consulta General de Trabajadores por rangos de nombres y apellidos
     * @param strNomDes Nombre desde donde inicia la busqueda
     * @param strNomHas Nombre hasta donde finaliza la busqueda
     * @param strApeDes Apellido desde donde inicia la busqueda
     * @param strApeHas Apellido hasta donde finaliza la busqueda
     * @return Retorna una lista de tipo Tbm_tra con los datos
     * @throws Exception
     */
    public List<Tbm_tra> consultarLisGenTraDesHas(String strNomDes, String strNomHas, String strApeDes, String strApeHas) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarDesHas(con, strNomDes, strNomHas, strApeDes, strApeHas);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarLisGenTraDesHas(String strNomDes, String strNomHas, String strApeDes, String strApeHas, int intCodEmp) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarDesHas(con, strNomDes, strNomHas, strApeDes, strApeHas, intCodEmp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tra;
    }

    /**
     * Consulta de las empresas configuradas por Trabajador
     * @param tbr_emp Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_traemp con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_emp> consultarLisGenPorCodTra(int intCodTra) throws Exception{
        List<Tbm_emp> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_traempDAOInterface.consultarLisGenPorCodTra(con, intCodTra);
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

   // public List<String> consultarLisGenPorCodTra2(int intCodTra, int intCoEmp) throws Exception{
     public List<String> consultarLisGenPorCodTra2(int intCodTra) throws Exception{
        List<String> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                //lisTbm_emp = tbm_traempDAOInterface.consultarLisGenPorCodTra2(con, intCodTra, intCoEmp);
                lisTbm_emp = tbm_traempDAOInterface.consultarLisGenPorCodTra2(con, intCodTra);
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

    public List<Tbm_emp> consultarLisGenEmpPorCodTraNomEmp(int intCodTra, String strNomEmp) throws Exception{
        List<Tbm_emp> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_traempDAOInterface.consultarLisGenPorCodTraNomEmp(con, intCodTra, strNomEmp);
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

    public void eliminarTraEmp(int intCodEmp, int intCodTra) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                tbm_traempDAOInterface.eliminar(con, intCodEmp, intCodTra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

    }
    public String consultarLisGenPorCodTraPorHor(int intCodTra) throws Exception{
        String strTbm_empHorFij = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                //lisTbm_emp = tbm_traempDAOInterface.consultarLisGenPorCodTra2(con, intCodTra, intCoEmp);
                strTbm_empHorFij = tbm_traempDAOInterface.consultarLisGenPorCodTraPorHor(con, intCodTra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return strTbm_empHorFij;
    }
}