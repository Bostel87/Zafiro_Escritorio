
package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_comsecDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_comsec;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 24/03/2011
 */
public class Tbm_comsecBO {
    private Tbm_comsecDAOInterface tbm_comsecDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_comsecBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_comsecDAOInterface = (Tbm_comsecDAOInterface) Tbm_comsecBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_comsecDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_comsecDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_comsec
     * @param tbm_comSec Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarComSec(Tbm_comsec tbm_comSec) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_comsecDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_comSec.setIntCo_comsec(maxId);
                tbm_comSec.setDatFe_ing(datFecHoy);
                tbm_comSec.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_comsecDAOInterface.grabar(con, tbm_comSec);
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
     * Actualiza los datos de la tabla tbm_comsec
     * @param tbm_comSec Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarComSec(Tbm_comsec tbm_comSec) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_comSec.setDatFe_ultmod(datFecHoy);
                tbm_comSec.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_comsecDAOInterface.actualizar(con, tbm_comSec);
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
     * @param tbm_comsec Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_comsec con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_comsec> consultarLisGen(Tbm_comsec tbm_comsec) throws Exception{
        List<Tbm_comsec> lisTbm_comsec = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_comsec = tbm_comsecDAOInterface.consultarLisGen(con, tbm_comsec);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_comsec;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_comsec Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_comsec con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_comsec> consultarLisPag(Tbm_comsec tbm_comsec, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_comsec> lisTbm_comsec = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_comsec = tbm_comsecDAOInterface.consultarLisPag(con, tbm_comsec, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_comsec;
    }

    /**
     * Consulta si el código de cargo ya se encuentra registrado en la base
     * @param strCodCar Código del cargo a consultar
     * @param intCodComSec Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public boolean consultarCodCarRep(String strCodCar, int intCodComSec) throws Exception{
        boolean blnRep = false;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                blnRep = tbm_comsecDAOInterface.consultarCodCarRep(con, strCodCar, intCodComSec);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return blnRep;
    }

}
