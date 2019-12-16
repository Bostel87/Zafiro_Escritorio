
package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipconDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipcon;
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
 * Guayaquil 29/03/2011
 */
public class Tbm_tipconBO {
    private Tbm_tipconDAOInterface tbm_tipconDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_tipconBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_tipconDAOInterface = (Tbm_tipconDAOInterface) Tbm_tipconBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipconDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipconDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_tipcon
     * @param tbm_tipcon Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarTipCon(Tbm_tipcon tbm_tipcon) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_tipconDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipcon.setIntCo_tipcon(maxId);
                tbm_tipcon.setDatFe_ing(datFecHoy);
                tbm_tipcon.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_tipconDAOInterface.grabar(con, tbm_tipcon);
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
     * Actualiza los datos de la tabla tbm_tipcon
     * @param tbm_tipcon Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarComSec(Tbm_tipcon tbm_tipcon) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipcon.setDatFe_ultmod(datFecHoy);
                tbm_tipcon.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_tipconDAOInterface.actualizar(con, tbm_tipcon);
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
     * @param tbm_tipcon Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_tipcon con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipcon> consultarLisGen(Tbm_tipcon tbm_tipcon) throws Exception{
        List<Tbm_tipcon> lisTbm_tipcon = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipcon = tbm_tipconDAOInterface.consultarLisGen(con, tbm_tipcon);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipcon;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_tipcon Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_tipcon con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipcon> consultarLisPag(Tbm_tipcon tbm_tipcon, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_tipcon> lisTbm_tipcon = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipcon = tbm_tipconDAOInterface.consultarLisPag(con, tbm_tipcon, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipcon;
    }
}
