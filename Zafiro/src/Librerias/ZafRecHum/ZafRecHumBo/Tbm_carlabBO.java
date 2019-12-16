/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_carlabDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_carlab;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;



/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Roberto Flores
 * Guayaquil 16/11/2011
 */
public class Tbm_carlabBO {
    
    private Tbm_carlabDAOInterface tbm_carlabDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_carlabBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        //tbm_callabBO = new Tbm_callabBO(zafParSis);
        //Tbm_hortraBO = new Tbm_hortraBO(zafParSis);
        try{
            tbm_carlabDAOInterface = (Tbm_carlabDAOInterface) Tbm_carlabBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_carlabDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_carlabDAO");
        }
    }

    /**
     * Graba datos en la tabla tbm_carlab
     * @param tbm_carlab Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabar(Tbm_carlab tbm_carlab) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_carlabDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_carlab.setIntCo_Car(maxId);
                tbm_carlab.setDatFe_ing(datFecHoy);
                tbm_carlab.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_carlabDAOInterface.grabar(con, tbm_carlab);
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
     * Actualiza los datos de las tablas tbm_carlab
     * @param tbm_carlab Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizar(Tbm_carlab tbm_carlab) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_carlab.setDatFe_ultmod(datFecHoy);
                tbm_carlab.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_carlabDAOInterface.actualizar(con, tbm_carlab);
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
     * Consulta si el nombre del horario ya se encuentra registrado en la base
     * @param strNomHor Código del cargo a consultar
     * @param intCodHor Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public boolean consultarCodNomCarLab(String strNomHor, int intCodHor) throws Exception{
        boolean blnRep = false;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                blnRep = tbm_carlabDAOInterface.consultarCodNomCarLab(con, strNomHor, intCodHor);
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

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_carlab Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_carlab con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_carlab> consultarLisPag(Tbm_carlab tbm_carlab, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_carlab> lisTbm_carlab = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_carlab = tbm_carlabDAOInterface.consultarLisPag(con, tbm_carlab, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_carlab;
    }

}
