/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_depDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_dep;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;



/**
 * Objeto que se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Roberto Flores
 * Guayaquil 21/11/2011
 */
public class Tbm_depBO {

    private Tbm_depDAOInterface tbm_depDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_depBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_depDAOInterface = (Tbm_depDAOInterface) Tbm_depBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_depDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_depDAO");
        }
    }

    /**
     * Graba datos en la tabla tbm_dep
     * @param tbm_dep Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabar(Tbm_dep tbm_dep) throws Exception{
        int maxId = 0;
        Connection con = null;
        //Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_depDAOInterface.obtenerMaxId(con)+1;
                //datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_dep.setIntCo_Dep(maxId);
                //tbm_carlab.setDatFe_ing(datFecHoy);
                //tbm_carlab.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_depDAOInterface.grabar(con, tbm_dep);
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
     * Actualiza los datos de las tablas tbm_dep
     * @param tbm_dep Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizar(Tbm_dep tbm_dep) throws Exception{
        Connection con = null;
        //Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                //datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                //tbm_carlab.setDatFe_ultmod(datFecHoy);
                //tbm_dep.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_depDAOInterface.actualizar(con, tbm_dep);
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
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_dep Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_dep con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_dep> consultarLisPag(Tbm_dep tbm_dep, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_dep> lisTbm_dep = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_dep = tbm_depDAOInterface.consultarLisPag(con, tbm_dep, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_dep;
    }

}
