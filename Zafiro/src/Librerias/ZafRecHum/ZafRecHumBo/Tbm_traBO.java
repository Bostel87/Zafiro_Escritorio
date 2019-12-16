
package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafMae.ZafMaeBo.Tbm_docdigsisBO;
import Librerias.ZafMae.ZafMaeDao.Tbm_ciuDAOInterface;
import Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAOInterface;
import Librerias.ZafMae.ZafMaeDao.Tbm_varDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_ciu;
import Librerias.ZafMae.ZafMaePoj.Tbm_tipdocdigsis;
import Librerias.ZafMae.ZafMaePoj.Tbm_var;
import Librerias.ZafUtil.TuvalFileException;
import Librerias.ZafUtil.ZafFilUti;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_contraDAOInterface;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_docdigtraDAOInterface;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAOInterface;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAOInterface;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.DetDocDig;
import Librerias.ZafRecHum.ZafRecHumPoj.DetFamCon;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_contra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_docdigtra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tipfamcon;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_tra;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 01/04/2011
 * v1.2
 */
public class Tbm_traBO {
    private Tbm_traDAOInterface tbm_traDAOInterface;
    private Tbm_ciuDAOInterface tbm_ciuDAOInterface;
    private Tbm_varDAOInterface tbm_varDAOInterface;
    private Tbm_contraDAOInterface tbm_contraDAOInterface;
    private Tbm_tipfamconDAOInterface tbm_tipfamconDAOInterface;
    private Tbm_docdigtraDAOInterface tbm_docdigtraDAOInterface;
    private Tbm_tipdocdigsisDAOInterface tbm_tipdocdigsisDAOInterface;
    private Tbm_traempDAOInterface tbm_traempDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;

    public Tbm_traBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        try{
            tbm_traDAOInterface = (Tbm_traDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traDAOInterface");
        }
        try{
            tbm_ciuDAOInterface = (Tbm_ciuDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_ciuDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafMae.MaeDao.Tbm_ciuDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.MaeDao.Tbm_ciuDAOInterface");
        }
        try{
            tbm_varDAOInterface = (Tbm_varDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_varDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafMae.MaeDao.Tbm_varDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.MaeDao.Tbm_varDAOInterface");
        }
        try{
            tbm_contraDAOInterface = (Tbm_contraDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_contraDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_contraDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_contraDAOInterface");
        }
        try{
            tbm_tipfamconDAOInterface = (Tbm_tipfamconDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_tipfamconDAOInterface");
        }
        try{
            tbm_docdigtraDAOInterface = (Tbm_docdigtraDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_docdigtraDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_docdigtraDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_docdigtraDAOInterface");
        }
        try{
            tbm_tipdocdigsisDAOInterface = (Tbm_tipdocdigsisDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_tipdocdigsisDAOInterface");
        }
        try{
            tbm_traempDAOInterface = (Tbm_traempDAOInterface) Tbm_traBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAO").newInstance();
        }catch(Exception ex){
            System.out.println("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAOInterface");
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_traempDAOInterface");
        }
    }

    /**
     * Consulta todos los horarios de trabajo
     * @return Retorna una lista de tipo Tbm_hortra con los datos de la consulta
     * @throws Exception
     */
    public List<Tbm_hortra> consultarLisGenCabHorTra() throws Exception{
        List<Tbm_hortra> lisTbm_emp = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_emp = tbm_traDAOInterface.consultarLisGenCabHorTra(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_emp;
    }
    
    public void grabarTra(Connection con,Connection consec,Tbm_tra tbm_tra, List<Tbm_contra> lisTbm_contra, List<DetDocDig> lisDetDocDig, String strNomPan) throws Exception{
        int maxId = 0;
        //Connection con = null;
        //Connection consec = null;
        Date datFecHoy = null;
        Tbm_docdigsisBO tbm_docdigsisBO = null;

        try {
            tbm_docdigsisBO = new Tbm_docdigsisBO(zafParSis);
            //con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                //con.setAutoCommit(false);
                maxId = tbm_traDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tra.setIntCo_tra(maxId);
                tbm_tra.setDatFe_ing(datFecHoy);
                tbm_tra.setIntCo_usring(zafParSis.getCodigoUsuario());
                
                tbm_traDAOInterface.grabar(con, consec,tbm_tra,zafParSis);

                //Graba empleado por empresa - verifica existencia
                Tbm_traemp tbr_traemp = new Tbm_traemp();
                tbr_traemp.setIntCo_emp(zafParSis.getCodigoEmpresa());
                tbr_traemp.setIntCo_tra(tbm_tra.getIntCo_tra());

                List<Tbm_traemp> lisTbr_traemp = null;
                lisTbr_traemp = tbm_traempDAOInterface.consultarLisGen(con, tbr_traemp);
                if(lisTbr_traemp==null){
                    tbm_traempDAOInterface.grabar(con, tbr_traemp);
                }
                
                //Graba lista de contactos
                if(lisTbm_contra!=null){
                    int maxIdCon = 0;
                    for(Tbm_contra tmp:lisTbm_contra){
                        maxIdCon = 0;
                        maxIdCon = tbm_contraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                        tmp.setIntCo_tra(tbm_tra.getIntCo_tra());
                        tmp.setIntCo_reg(maxIdCon);
                        tmp.setStrSt_reg("A");
                        tmp.setDatFe_ing(datFecHoy);
                        tmp.setIntCo_usring(zafParSis.getCodigoUsuario());
                        tbm_contraDAOInterface.grabar(con, tmp);
                    }
                }
                //Graba lista de documentos digitales
                if(lisDetDocDig!=null){
                    int maxIdDoc = 0;
                    ZafFilUti zafFileUtil = new ZafFilUti();
                    for(DetDocDig tmp:lisDetDocDig){
                        maxIdDoc = 0;
                        maxIdDoc = tbm_docdigtraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                        tmp.getTbm_docdigtra().setIntCo_tra(tbm_tra.getIntCo_tra());
                        tmp.getTbm_docdigtra().setIntCo_reg(maxIdDoc);

                        int[] indCodCom = {tbm_tra.getIntCo_tra()};
                        int intTipDoc = tmp.getTbm_docdigtra().getIntCo_tipdocdig();
                        int intSec = tmp.getTbm_docdigtra().getIntCo_reg();
                        String strExtArcDig = tmp.getStrArcLoc().substring(tmp.getStrArcLoc().lastIndexOf("."), tmp.getStrArcLoc().length());
                        tmp.getTbm_docdigtra().setStrTx_nomarc(tbm_docdigsisBO.getNomDocDig(strNomPan, indCodCom, intTipDoc, intSec, strExtArcDig));
                        tmp.getTbm_docdigtra().setStrSt_reg("A");
                        tmp.getTbm_docdigtra().setDatFe_ing(datFecHoy);
                        tmp.getTbm_docdigtra().setIntCo_usring(zafParSis.getCodigoUsuario());
                        tbm_docdigtraDAOInterface.grabar(con, tmp.getTbm_docdigtra());
                        //zafFileUtil.copiarArcDir(tmp.getStrArcLoc(), zafParSis.getRutDocDig(), tmp.getTbm_docdigtra().getStrTx_nomarc());
                        String[] args = new String[2];
                        tbm_docdigsisBO.getRutDocDig(args);
                        //String strIp = args[0];
                        String strArr[] = args[0].split("/");
                        
                        String strIp = strArr[2];
                        String strRutSer = strArr[3]+args[1];
                        //System.out.println("IP:" + strIp);
                        //System.out.println("RUTA:" + strRutSer);
                        /*String strIp = "172.16.1.7";
                        String strRutSer = "/home/jflores/Desktop/ZafRecHum03/img/";*/
                        zafFileUtil.subirArcSocket(tmp.getStrArcLoc(), strIp, strRutSer, tmp.getTbm_docdigtra().getStrTx_nomarc());
                    }
                }
                //con.commit();
                //consec.commit();
            }
        } catch (SQLException ex) {
            /*con.rollback();
            consec.rollback();*/
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (TuvalFileException ex) {
            /*con.rollback();
            consec.rollback();*/
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            /*con.rollback();
            consec.rollback();*/
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            //con.close();con=null;consec.close();consec=null;
        }
    }     

    /**
     * Graba datos en la tabla tbm_tra
     * @param tbm_tra Objeto con datos a ser grabados
     * @param lisTbm_contra List con datos del detalle de familiares y contactos
     * @param lisTbm_docdigtra List con datos del detalle de documentos digitales
     * @throws Exception
     */
    public void grabarTra(Tbm_tra tbm_tra, List<Tbm_contra> lisTbm_contra, List<DetDocDig> lisDetDocDig, String strNomPan) throws Exception{
        int maxId = 0;
        Connection con = null;
        Date datFecHoy = null;
        Tbm_docdigsisBO tbm_docdigsisBO = null;

        try {
            tbm_docdigsisBO = new Tbm_docdigsisBO(zafParSis);
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_traDAOInterface.obtenerMaxId(con)+1;
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tra.setIntCo_tra(maxId);
                tbm_tra.setDatFe_ing(datFecHoy);
                tbm_tra.setIntCo_usring(zafParSis.getCodigoUsuario());
                tbm_traDAOInterface.grabar(con, tbm_tra,zafParSis);

                //Graba empleado por empresa - verifica existencia
                Tbm_traemp tbr_traemp = new Tbm_traemp();
                tbr_traemp.setIntCo_emp(zafParSis.getCodigoEmpresa());
                tbr_traemp.setIntCo_tra(tbm_tra.getIntCo_tra());

                List<Tbm_traemp> lisTbr_traemp = null;
                lisTbr_traemp = tbm_traempDAOInterface.consultarLisGen(con, tbr_traemp);
                if(lisTbr_traemp==null){
                    tbm_traempDAOInterface.grabar(con, tbr_traemp);
                }
                
                //Graba lista de contactos
                if(lisTbm_contra!=null){
                    int maxIdCon = 0;
                    for(Tbm_contra tmp:lisTbm_contra){
                        maxIdCon = 0;
                        maxIdCon = tbm_contraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                        tmp.setIntCo_tra(tbm_tra.getIntCo_tra());
                        tmp.setIntCo_reg(maxIdCon);
                        tmp.setStrSt_reg("A");
                        tmp.setDatFe_ing(datFecHoy);
                        tmp.setIntCo_usring(zafParSis.getCodigoUsuario());
                        tbm_contraDAOInterface.grabar(con, tmp);
                    }
                }
                //Graba lista de documentos digitales
                if(lisDetDocDig!=null){
                    int maxIdDoc = 0;
                    ZafFilUti zafFileUtil = new ZafFilUti();
                    for(DetDocDig tmp:lisDetDocDig){
                        maxIdDoc = 0;
                        maxIdDoc = tbm_docdigtraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                        tmp.getTbm_docdigtra().setIntCo_tra(tbm_tra.getIntCo_tra());
                        tmp.getTbm_docdigtra().setIntCo_reg(maxIdDoc);

                        int[] indCodCom = {tbm_tra.getIntCo_tra()};
                        int intTipDoc = tmp.getTbm_docdigtra().getIntCo_tipdocdig();
                        int intSec = tmp.getTbm_docdigtra().getIntCo_reg();
                        String strExtArcDig = tmp.getStrArcLoc().substring(tmp.getStrArcLoc().lastIndexOf("."), tmp.getStrArcLoc().length());
                        tmp.getTbm_docdigtra().setStrTx_nomarc(tbm_docdigsisBO.getNomDocDig(strNomPan, indCodCom, intTipDoc, intSec, strExtArcDig));
                        tmp.getTbm_docdigtra().setStrSt_reg("A");
                        tmp.getTbm_docdigtra().setDatFe_ing(datFecHoy);
                        tmp.getTbm_docdigtra().setIntCo_usring(zafParSis.getCodigoUsuario());
                        tbm_docdigtraDAOInterface.grabar(con, tmp.getTbm_docdigtra());
                        //zafFileUtil.copiarArcDir(tmp.getStrArcLoc(), zafParSis.getRutDocDig(), tmp.getTbm_docdigtra().getStrTx_nomarc());
                        String[] args = new String[2];
                        tbm_docdigsisBO.getRutDocDig(args);
                        //String strIp = args[0];
                        String strArr[] = args[0].split("/");
                        
                        String strIp = strArr[2];
                        String strRutSer = strArr[3]+args[1];
                        //System.out.println("IP:" + strIp);
                        //System.out.println("RUTA:" + strRutSer);
                        /*String strIp = "172.16.1.7";
                        String strRutSer = "/home/jflores/Desktop/ZafRecHum03/img/";*/
                        zafFileUtil.subirArcSocket(tmp.getStrArcLoc(), strIp, strRutSer, tmp.getTbm_docdigtra().getStrTx_nomarc());
                    }
                }
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (TuvalFileException ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }
    }

    /**
     * Actualiza los datos de la tabla tbm_tra
     * @param tbm_tra Objeto con datos a actualizar
     * @param lisTbm_contra Lista con datos del detalle de famliares y contactos
     * @param lisTbm_contrabk Lista con datos originales del detalle de famliares y contactos
     * @param lisTbm_docdigtra Lista con datos del detalle de documentos digitales
     * @param lisTbm_docdigtrabk Lista con datos originales del detalle de documentos digitales
     * @throws Exception
     */
    public void actualizarTra(Tbm_tra tbm_tra, List<Tbm_contra> lisTbm_contra, List<Tbm_contra> lisTbm_contrabk, List<DetDocDig> lisDetDocDig, List<Tbm_docdigtra> lisTbm_docdigtrabk, String strNomPan, ZafParSis objZafParSis) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tra.setDatFe_ultmod(datFecHoy);
                tbm_tra.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_traDAOInterface.actualizar(con, tbm_tra, objZafParSis);

                //Actualiza lista de contactos
                actualizaCon(con, tbm_tra, lisTbm_contra, lisTbm_contrabk, datFecHoy);
                //Actualiza lista de documentos digitales
                actualizaDocDig(con, tbm_tra, lisDetDocDig, lisTbm_docdigtrabk, datFecHoy, strNomPan);
                con.commit();
            }
        } catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (TuvalFileException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }
    }
    
    /**
     * Actualiza los datos de la tabla tbm_tra
     * @param tbm_tra Objeto con datos a actualizar
     * @throws Exception
     */
    public void anular(Tbm_tra tbm_tra, ZafParSis objZafParSis) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_tra.setDatFe_ultmod(datFecHoy);
                tbm_tra.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                tbm_traDAOInterface.anular(con, tbm_tra, objZafParSis);
                con.commit();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }
    }

    private void actualizaCon(Connection con, Tbm_tra tbm_tra, List<Tbm_contra> lisTbm_contra, List<Tbm_contra> lisTbm_contrabk, Date datFecHoy) throws Exception{
        if(lisTbm_contra!=null){
            for(int i=0;i<lisTbm_contra.size();i++){
                Tbm_contra tmp=lisTbm_contra.get(i);
                //Actualizar
                if(tmp.getIntCo_reg()>0){
                    
                    if((tmp.getStrTx_nom()!=null && !tmp.getStrTx_nom().equals(lisTbm_contrabk.get(i).getStrTx_nom()))||
                    tmp.getIntCo_tipfamcon()!=lisTbm_contrabk.get(i).getIntCo_tipfamcon()||
                    (tmp.getStrTx_tel1()!=null && !tmp.getStrTx_tel1().equals(lisTbm_contrabk.get(i).getStrTx_tel1()))||
                    (tmp.getStrTx_tel2()!=null && !tmp.getStrTx_tel2().equals(lisTbm_contrabk.get(i).getStrTx_tel2()))||
                    (tmp.getDatFe_nac()!=null && !tmp.getDatFe_nac().equals(lisTbm_contrabk.get(i).getDatFe_nac()))||
                    (tmp.getStrSt_llacaseme()!=null && !tmp.getStrSt_llacaseme().equals(lisTbm_contrabk.get(i).getStrSt_llacaseme()))||
                    (tmp.getStrTx_obs1()!=null && !tmp.getStrTx_obs1().equals(lisTbm_contrabk.get(i).getStrTx_obs1()))){
                        tmp.setStrSt_reg("A");
                        tmp.setDatFe_ing(lisTbm_contrabk.get(i).getDatFe_ing());
                        tmp.setDatFe_ultmod(datFecHoy);
                        tmp.setIntCo_usring(lisTbm_contrabk.get(i).getIntCo_usring());
                        tmp.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                        tbm_contraDAOInterface.actualizar(con, tmp);
                    }
                }else{
                    //Insertar
                    int maxIdCon = 0;
                    maxIdCon = tbm_contraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                    tmp.setIntCo_tra(tbm_tra.getIntCo_tra());
                    tmp.setIntCo_reg(maxIdCon);
                    tmp.setStrSt_reg("A");
                    tmp.setDatFe_ing(datFecHoy);
                    tmp.setIntCo_usring(zafParSis.getCodigoUsuario());
                    tbm_contraDAOInterface.grabar(con, tmp);
                }
            }
            //Eliminar
            if(lisTbm_contrabk != null){
                boolean blnDel=false;
                for(int i=0;i<lisTbm_contrabk.size();i++){//old
                    Tbm_contra tmp = lisTbm_contrabk.get(i);
                    for(Tbm_contra tmp2:lisTbm_contra){//new
                        blnDel=(tmp.getIntCo_reg()==tmp2.getIntCo_reg());
                        if(blnDel){
                            break;
                        }
                    }
                    if(!blnDel){
                        tmp.setStrSt_reg("I");
                        tmp.setDatFe_ing(lisTbm_contrabk.get(i).getDatFe_ing());
                        tmp.setDatFe_ultmod(datFecHoy);
                        tmp.setIntCo_usring(lisTbm_contrabk.get(i).getIntCo_usring());
                        tmp.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                        tbm_contraDAOInterface.actualizar(con, tmp);
                    }
                }
            }
        }
    }

    private void actualizaDocDig(Connection con, Tbm_tra tbm_tra, List<DetDocDig> lisDetDocDig, List<Tbm_docdigtra> lisTbm_docdigtrabk, Date datFecHoy, String strNomPan) throws Exception{
        if(lisDetDocDig!=null){
            Tbm_docdigsisBO tbm_docdigsisBO = new Tbm_docdigsisBO(zafParSis);
            ZafFilUti zafFileUtil = new ZafFilUti();

            for(int i=0;i<lisDetDocDig.size();i++){
                Tbm_docdigtra tmp=lisDetDocDig.get(i).getTbm_docdigtra();
                //Actualizar
                if(tmp.getIntCo_reg()>0){
                    if((tmp.getIntCo_tipdocdig()!=lisTbm_docdigtrabk.get(i).getIntCo_tipdocdig())||
                    (tmp.getStrTx_obs1()!=null && !tmp.getStrTx_obs1().equals(lisTbm_docdigtrabk.get(i).getStrTx_obs1()))){
                        tmp.setStrSt_reg("A");
                        tmp.setDatFe_ing(lisTbm_docdigtrabk.get(i).getDatFe_ing());
                        tmp.setDatFe_ultmod(datFecHoy);
                        tmp.setIntCo_usring(lisTbm_docdigtrabk.get(i).getIntCo_usring());
                        tmp.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                        tbm_docdigtraDAOInterface.actualizar(con, tmp);
                    }
                }
                else{
                    //Insertar
                    int maxIdDoc = 0;
                    maxIdDoc = tbm_docdigtraDAOInterface.obtenerMaxId(con, tbm_tra.getIntCo_tra())+1;
                    tmp.setIntCo_tra(tbm_tra.getIntCo_tra());
                    tmp.setIntCo_reg(maxIdDoc);

                    int[] indCodCom = {tbm_tra.getIntCo_tra()};
                    int intTipDoc = tmp.getIntCo_tipdocdig();
                    int intSec = tmp.getIntCo_reg();
                    String strExtArcDig = lisDetDocDig.get(i).getStrArcLoc().substring(lisDetDocDig.get(i).getStrArcLoc().lastIndexOf("."), lisDetDocDig.get(i).getStrArcLoc().length());
                    tmp.setStrTx_nomarc(tbm_docdigsisBO.getNomDocDig(strNomPan, indCodCom, intTipDoc, intSec, strExtArcDig));
                    
                    tmp.setStrSt_reg("A");
                    tmp.setDatFe_ing(datFecHoy);
                    tmp.setIntCo_usring(zafParSis.getCodigoUsuario());
                    tbm_docdigtraDAOInterface.grabar(con, tmp);
                    //zafFileUtil.copiarArc(lisDetDocDig.get(i).getStrArcLoc(), zafParSis.getRutDocDig(), tmp.getStrTx_nomarc());
                    String[] args = new String[2];
                    tbm_docdigsisBO.getRutDocDig(args);
                    String strIp = args[0];
                    //String strRutSer = args[1];
                    String strRutSer = "/Zafiro"+args[1];
                    String[] strAuxIpSer=args[0].split("/");
                    //String strIpSer="172.16.1.2";
                    String strIpSer=strAuxIpSer[2];
                    zafFileUtil.subirArcSocket(lisDetDocDig.get(i).getStrArcLoc(), strIpSer, strRutSer, tmp.getStrTx_nomarc());
                }
            }
            //Eliminar
            if(lisTbm_docdigtrabk != null){
                boolean blnDel=false;
                for(int i=0;i<lisTbm_docdigtrabk.size();i++){//old
                    Tbm_docdigtra tmp = lisTbm_docdigtrabk.get(i);
                    for(DetDocDig tmp2:lisDetDocDig){//new
                        blnDel=(tmp.getIntCo_reg()==tmp2.getTbm_docdigtra().getIntCo_reg());
                        if(blnDel){
                            break;
                        }
                    }
                    if(!blnDel){
                        tmp.setStrSt_reg("I");
                        tmp.setDatFe_ing(lisTbm_docdigtrabk.get(i).getDatFe_ing());
                        tmp.setDatFe_ultmod(datFecHoy);
                        tmp.setIntCo_usring(lisTbm_docdigtrabk.get(i).getIntCo_usring());
                        tmp.setIntCo_usrmod(zafParSis.getCodigoUsuario());
                        tbm_docdigtraDAOInterface.actualizar(con, tmp);
                    }
                }
            }
        }
    }

    /**
     * Consulta General según parámetros ingresados por pantalla
     * @param tbm_tra Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_tra con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tra> consultarLisGen(Tbm_tra tbm_tra) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarLisGen(con, tbm_tra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarLisGen(Tbm_tra tbm_tra, int intCodEmp) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarLisGen(con, tbm_tra , intCodEmp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_tra;
    }

    public List<Tbm_tra> consultarLisGen(int intCodEmp) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarLisGen(con, intCodEmp);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_tra;
    }

    /**
     * Consulta General del Detalle de Familiares y Contactos el cual consta de dos objetos pojo
     * @param tbm_contra Objeto con parámetros de consulta
     * @return Retorna una lista de tipo DetFamCon con uno o varios registros
     * @throws Exception
     */
    public List<DetFamCon> consultarLisGenDetFamCon(Tbm_contra tbm_contra) throws Exception{
        List<DetFamCon> lisDetFamCon = null;
        Connection con = null;

        try {
            
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                List<Tbm_contra> lisTbm_contra = tbm_contraDAOInterface.consultarLisGen(con, tbm_contra);
                if(lisTbm_contra!=null){
                    lisDetFamCon = new ArrayList<DetFamCon>();
                    //Bloque para consultar contactos y los tipos de familiares
                    for(int i=0;i<lisTbm_contra.size();i++){
                        Tbm_contra tbm_contratmp = lisTbm_contra.get(i);
                        lisDetFamCon.add(new DetFamCon());
                        lisDetFamCon.get(i).setTbm_contra(tbm_contratmp);

                        Tbm_tipfamcon tbm_tipfamconpar = new Tbm_tipfamcon();
                        tbm_tipfamconpar.setIntCo_tipfamcon(tbm_contratmp.getIntCo_tipfamcon());

                        List<Tbm_tipfamcon> lisTbm_tipfamcon = tbm_tipfamconDAOInterface.consultarLisGen(con, tbm_tipfamconpar);
                        if(lisTbm_tipfamcon!=null){
                            lisDetFamCon.get(i).setTbm_tipfamcon(lisTbm_tipfamcon.get(0));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisDetFamCon;
    }

    /**
     * Consulta General del Detalle de Documentos Digitales el cual consta de dos objetos pojo
     * @param tbm_docdigtra Objeto con parámetros de consulta
     * @return Retorna una lista de tipo DetDocDig con uno o varios registros
     * @throws Exception
     */
    public List<DetDocDig> consultarLisGenDetDocDig(Tbm_docdigtra tbm_docdigtra) throws Exception{
        List<DetDocDig> lisDetDocDig = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                List<Tbm_docdigtra> lisTbm_docdigtra = tbm_docdigtraDAOInterface.consultarLisGen(con, tbm_docdigtra);
                if(lisTbm_docdigtra!=null){
                    lisDetDocDig = new ArrayList<DetDocDig>();
                    //Bloque para consultar los documentos digitales y los tipos de documentos
                    for(int i=0;i<lisTbm_docdigtra.size();i++){
                        Tbm_docdigtra tbm_docdigtratmp = lisTbm_docdigtra.get(i);
                        lisDetDocDig.add(new DetDocDig());
                        lisDetDocDig.get(i).setTbm_docdigtra(tbm_docdigtratmp);

                        Tbm_tipdocdigsis tbm_tipdocdigsispar = new Tbm_tipdocdigsis();
                        tbm_tipdocdigsispar.setIntCo_tipdocdig(tbm_docdigtratmp.getIntCo_tipdocdig());

                        List<Tbm_tipdocdigsis> lisTbm_tipdocdigsis = tbm_tipdocdigsisDAOInterface.consultarLisGen(con, tbm_tipdocdigsispar);
                        if(lisTbm_tipdocdigsis!=null){
                            lisDetDocDig.get(i).setTbm_tipdocdigsis(lisTbm_tipdocdigsis.get(0));
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisDetDocDig;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_tra Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_tra con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_tra> consultarLisPag(Tbm_tra tbm_tra, int intNumPag, int intCanReg, ZafParSis objZafParSis) throws Exception{
        List<Tbm_tra> lisTbm_tra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_tra = tbm_traDAOInterface.consultarLisPag(con, tbm_tra, intNumPag, intCanReg, objZafParSis);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_tra;
    }

    /**
     * Consulta general o específica de ciudades
     * @param tbm_ciu Objeto con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_ciu con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_ciu> consultarLisTbm_ciu(Tbm_ciu tbm_ciu) throws Exception{
        List<Tbm_ciu> lisTbm_ciu = null;
        Connection con = null;

        try{
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_ciu = tbm_ciuDAOInterface.consultarLisGen(con, tbm_ciu);
            }
        } catch(SQLException ex){
            throw new Exception(ex.getMessage());
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_ciu;
    }

    /**
     * Consulta el estado civil del empleado
     * @param tbm_var Objeto con parámetros para la consulta
     * @return Retorna una list de tipo Tbm_var con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_var> consultarLisTbm_var(Tbm_var tbm_var) throws Exception{
        List<Tbm_var> lisTbm_var = null;
        Connection con = null;

        try{
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_var = tbm_varDAOInterface.consultarLisGen(con, tbm_var);
            }
        } catch(SQLException ex){
            throw new Exception(ex.getMessage());
        } catch(Exception ex){
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return lisTbm_var;
    }

    //public boolean consultarCedRep(int intCodTra, String strCedTra) throws Exception{
    public boolean consultarCedRep(String strCedTra) throws Exception{
        boolean blnRep = false;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                //blnRep = tbm_traDAOInterface.consultarCedRep(con, intCodTra, strCedTra);
                blnRep = tbm_traDAOInterface.consultarCedRep(con, strCedTra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();con=null;
        }

        return blnRep;
    }

}
