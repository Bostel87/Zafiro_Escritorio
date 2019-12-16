/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_callabDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_callab;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.springframework.scheduling.quartz.AdaptableJobFactory;

/**
 *
 * @author Roberto Flores
 * Guayaquil 16/09/2011
 */
public class Tbm_callabBO {
    private Tbm_callabDAOInterface tbm_callabDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_callabBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_callabDAOInterface = (Tbm_callabDAOInterface) Tbm_callabBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_callabDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_callabDAOInterface");
        }
    }

    /**
     * Graba datos en la tabla tbm_callab
     * @param arrTbm_callab con los Objetos a ser grabados
     * @throws Exception
     */
    public void grabarHorTra(ArrayList<Tbm_callab> arrTbm_callab) throws Exception{
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_callabDAOInterface.grabar(con, arrTbm_callab);
                con.commit();
            }
        }catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }


    public void eliminar(Connection con, int intCoHor, Date datFe_ultMod) throws Exception{
        //Connection con = null;

        try {
            //con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_callabDAOInterface.eliminar(con, intCoHor ,datFe_ultMod);
                con.commit();
            }
        }catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /*public void eliminar(Connection con) throws Exception{
        //Connection con = null;

        try {
            //con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_callabDAOInterface.eliminar(con);
                //con.commit();
            }
        }catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }*/

    /**
     * Graba datos en la tabla tbm_callab
     * @param arrTbm_callab con los Objetos a ser grabados
     * @throws Exception
     *
    public void grabarHorTra(Connection con, ArrayList<Tbm_callab> arrTbm_callab) throws Exception{
        //Connection con = null;

        try {
            //con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                tbm_callabDAOInterface.grabar(con, arrTbm_callab);
                con.commit();
            }
        }catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }*/

    /**
     * Consulta si el nombre del horario ya se encuentra registrado en la base
     * @param strNomHor Código del cargo a consultar
     * @param intCodHor Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public Date consultaAño() throws Exception{
        Date date = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                date = tbm_callabDAOInterface.consultaAño(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return date;
    }

    /**
     * Consulta el maximo año de la tabla tbm_callab
     * @return Retorna el maximo año de existir y 0 en caso contrario
     * @throws Exception
     */
    public int obtenerMaxAño() throws Exception{
        int intMaxAño = 0;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intMaxAño = tbm_callabDAOInterface.obtenerMaxAño(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intMaxAño;
    }



}
