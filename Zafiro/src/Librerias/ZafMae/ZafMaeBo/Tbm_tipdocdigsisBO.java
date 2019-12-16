
package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import Librerias.ZafParSis.ZafParSis;
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
 * Guayaquil 18/05/2011
 */
public class Tbm_tipdocdigsisBO {
    private Tbm_tipdocdigsisDAOInterface tbm_tipdocdigsisDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_tipdocdigsisBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_tipdocdigsisDAOInterface = (Tbm_tipdocdigsisDAOInterface) Tbm_tipdocdigsisBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_tipdocdigsisDAOInterface
     * @param tbm_tipdocdigsisDAOInterface Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarTipDoc(Tbm_tipdocdigsis tbm_tipdocdigsis) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_tipdocdigsisDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipdocdigsis.setIntCo_tipdocdig(maxId);
                tbm_tipdocdigsis.setDatFe_ing(datFecHoy);
                tbm_tipdocdigsis.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_tipdocdigsisDAOInterface.grabar(con, tbm_tipdocdigsis);
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
     * Actualiza los datos de la tabla tbm_tipdocdigsis
     * @param tbm_tipdocdigsis Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarTipDoc(Tbm_tipdocdigsis tbm_tipdocdigsis) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipdocdigsis.setDatFe_ultmod(datFecHoy);
                tbm_tipdocdigsis.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_tipdocdigsisDAOInterface.actualizar(con, tbm_tipdocdigsis);
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
     * @param tbm_tipdocdigsis Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_tipdocdigsis con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipdocdigsis> consultarLisGen(Tbm_tipdocdigsis tbm_tipdocdigsis) throws Exception{
        List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipdocdigsis = tbm_tipdocdigsisDAOInterface.consultarLisGen(con, tbm_tipdocdigsis);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipdocdigsis;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_tipdocdigsis Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_tipdocdigsis con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipdocdigsis> consultarLisPag(Tbm_tipdocdigsis tbm_tipdocdigsis, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipdocdigsis = tbm_tipdocdigsisDAOInterface.consultarLisPag(con, tbm_tipdocdigsis, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipdocdigsis;
    }
}
