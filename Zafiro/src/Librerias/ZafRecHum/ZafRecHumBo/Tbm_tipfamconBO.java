
package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
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
 * Guayaquil 15/04/2011
 */
public class Tbm_tipfamconBO {
    private Tbm_tipfamconDAOInterface tbm_tipfamconDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_tipfamconBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_tipfamconDAOInterface = (Tbm_tipfamconDAOInterface) Tbm_tipfamconBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAO").newInstance();
        }catch(Exception ex){
            ex.printStackTrace();
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_tipfamcon
     * @param tbm_tipfamcon Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarTipFamCon(Tbm_tipfamcon tbm_tipfamcon) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_tipfamconDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipfamcon.setIntCo_tipfamcon(maxId);
                tbm_tipfamcon.setDatFe_ing(datFecHoy);
                tbm_tipfamcon.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_tipfamconDAOInterface.grabar(con, tbm_tipfamcon);
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
     * Actualiza los datos de la tabla tbm_tipfamcon
     * @param tbm_tipfamcon Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarTipFamCon(Tbm_tipfamcon tbm_tipfamcon) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tipfamcon.setDatFe_ultmod(datFecHoy);
                tbm_tipfamcon.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_tipfamconDAOInterface.actualizar(con, tbm_tipfamcon);
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
     * @param tbm_tipfamcon Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_tipfamcon con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipfamcon> consultarLisGen(Tbm_tipfamcon tbm_tipfamcon) throws Exception{
        List<Tbm_tipfamcon> lisTbm_tipfamcon = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipfamcon = tbm_tipfamconDAOInterface.consultarLisGen(con, tbm_tipfamcon);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipfamcon;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_tipfamcon Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_tipfamcon con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tipfamcon> consultarLisPag(Tbm_tipfamcon tbm_tipfamcon, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_tipfamcon> lisTbm_tipfamcon = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tipfamcon = tbm_tipfamconDAOInterface.consultarLisPag(con, tbm_tipfamcon, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_tipfamcon;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_tipfamcon Objetos con parámetros de consulta
     * @return Retorna un String que indica el tipo de carga familiar para el contacto indicado
     * @throws Exception
     */
    public String consultarTipoCargaFamiliar(Tbm_tipfamcon tbm_tipfamcon) throws Exception{
        String strTx_TipCarFam = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                strTx_TipCarFam = tbm_tipfamconDAOInterface.consultarTipoCargaFamiliar(con, tbm_tipfamcon);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return strTx_TipCarFam;
    }
}
