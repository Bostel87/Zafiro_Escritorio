/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafRecHum.ZafRecHumBo;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRecHum.ZafRecHumDao.Tbm_hortraDAOInterface;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_hortra;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Objeto que se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Roberto Flores
 * Guayaquil 08/09/2011
 */
public class Tbm_hortraBO {
    private Tbm_hortraDAOInterface tbm_hortraDAOInterface;
    private ZafParSis zafParSis;
    private ZafUtil zafUti;
    private Tbm_callabBO tbm_callabBO;                          //Coneccion a la Base de Datos

    public Tbm_hortraBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;
        zafUti = new ZafUtil();
        tbm_callabBO = new Tbm_callabBO(zafParSis);
        //Tbm_hortraBO = new Tbm_hortraBO(zafParSis);
        try{
            tbm_hortraDAOInterface = (Tbm_hortraDAOInterface) Tbm_hortraBO.class.getClassLoader().loadClass("Librerias.ZafRecHum.ZafRecHumDao.Tbm_hortraDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafRecHum.ZafRecHumDao.Tbm_hortraDAOInterface");
        }
    }

    /**
     * Graba datos en las tablas tbm_cabhortra & tbm_dethortra.
     * @param tbm_hortra Objeto con datos a ser grabados
     * @throws Exception
     */
    public void grabarHorTra(Tbm_hortra tbm_horTra) throws Exception{
        int maxId = 0;
        int intMaxAño = 0;
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                maxId = tbm_hortraDAOInterface.obtenerMaxId(con)+1;
                intMaxAño = tbm_callabBO.obtenerMaxAño();
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_horTra.setIntCo_hor(maxId);
                tbm_horTra.setDatFe_ing(datFecHoy);
                tbm_horTra.setIntCo_usrIng(zafParSis.getCodigoUsuario());
                List<Tbm_hortra> lisTbm_hortra = tbm_hortraDAOInterface.consultar(con);
                tbm_hortraDAOInterface.grabar(con, lisTbm_hortra , tbm_horTra, datFecHoy, intMaxAño, tbm_callabBO);
                con.commit();
            }
        }catch (SQLException ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            con.rollback();
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }
    }

    /*public void procesarCalendarioLaboral(Date date, Tbm_hortra tbm_hortra, int intMaxAño) throws Exception {

        ArrayList<Tbm_callab> arrTbm_callab = new ArrayList<Tbm_callab>();
        Calendar cal = null;
        int intDiasValidosIniciales;
        int intMesesValidosIniciales;
        List<Tbm_hortra> lisTbm_hortra = null;
        int intCodHor;
        boolean res = false;

        String formato="yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        int intAño = Integer.parseInt(dateFormat.format(date));
        int intRecAño = 0;

        try {


            if(intMaxAño>0){
                for(intRecAño = intAño; intRecAño <= intMaxAño; intRecAño++){

                    formato="MM";
                    dateFormat = new SimpleDateFormat(formato);
                    intMesesValidosIniciales = Integer.parseInt(dateFormat.format(date));

                    formato="dd";
                    dateFormat = new SimpleDateFormat(formato);
                    intDiasValidosIniciales = Integer.parseInt(dateFormat.format(date));//Tbm_hortraBO.consultaMesesValidosIniciales();
                    intCodHor = tbm_hortra.getIntCo_hor(); //Tbm_hortraBO.consultaCodHor();
                    //lisTbm_hortra = Tbm_hortraBO.consultarCalLabAct();

                    int[] diaSemana = {1, 2, 3, 4, 5, 6, 7};
                    int[] intMeses = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
                    int total = 0;
                    int intC = 0;

                    //for(int intRecCodHor = 0; intRecCodHor < intCodHor.length; intRecCodHor++){

                        //armo los horarios
                        String[][] horarios = tbm_hortra.getStrTbl_entsaldet(); //new String[7][3];
                        /*for(int i = 0; i < lisTbm_hortra.size(); i++){
                                //if(intCodHor==lisTbm_hortra.get(i).getIntCo_hor()){
                                    horarios[intC][0] = tbm_hortra.getStrTbl_entsaldet()[0][0];
                                    horarios[intC][1] = tbm_hortra.getStrTbl_entsaldet()[0][1];
                                    horarios[intC][2] = tbm_hortra.getStrTbl_entsaldet()[0][2];
                                    intC++;
                                //}
                        }*/
                        /*intC = 0;

                        int intMesMin = intMesesValidosIniciales-1;//[intRecCodHor]-1;
                        int intMesMax = intMeses[intMeses.length-1];
                        int intDiaMin = intDiasValidosIniciales;//[intRecCodHor];//cal.getActualMinimum(cal.DATE);


                        for(int intMes = intMesMin; intMes <= intMesMax; intMes++){
                            cal = Calendar.getInstance();
                            //set(int year, int month, int date)
                            int intDiaMax = cal.getActualMaximum(cal.DATE);
                            cal.set(intRecAño, intMes, intDiaMin);


                                for(int intDia = intDiaMin; intDia <= intDiaMax; intDia++){

                                    Tbm_callab tmp = new Tbm_callab();
                                    cal.set(intRecAño, intMes, intDia);
                                    intDiaMax = cal.getActualMaximum(cal.DATE);
                                    date = cal.getTime();
                                    System.out.println(date.toString());
                                    int day = date.getDay();
                                    if(day==0){day=6;}else{
                                        day--;
                                    }
                                    tmp.setDteFe_Dia(date);
                                    tmp.setIntCo_Hor(intCodHor);
                                    if(horarios[day][0]==null){
                                        tmp.setStrSt_DiaLab("N");
                                    }else{
                                        tmp.setStrSt_DiaLab("S");
                                    }
                                    tmp.setStrHo_Ent(horarios[day][0]);
                                    tmp.setStrHo_Sal(horarios[day][1]);
                                    tmp.setStrMin_GraEnt(horarios[day][2]);
                                    arrTbm_callab.add(tmp);
                                    //System.out.println(day);
                                    total++;
                                }
                                intDiaMin = 1;
                                System.out.println(total);
                                total = 0;
                        }
                        cal.set(intRecAño, intMeses[0], 1);
                        date = cal.getTime();

                }
            }
            //guardo lo almacenado en el array
            tbm_callabBO.grabarHorTra(arrTbm_callab);
            //setearFecha();
            res = true;

       }catch (Exception ex) {
           res = false;
           //zafUti.mostrarMsgErr_F1(zafRecHum06.this, null);
           ex.printStackTrace();
           throw new Exception(ex.getMessage());
       }
    }*/

    /**
     * Actualiza los datos de las tablas tbm_cabhortra & tbm_dethortra.
     * @param tbm_hortra Objeto con datos a actualizar
     * @throws Exception
     */
    public void actualizarHorTra(Tbm_hortra tbm_horTra, int opcion) throws Exception{
        Connection con = null;
        Date datFecHoy = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                con.setAutoCommit(false);
                datFecHoy = zafUti.getFechaServidor(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos(),zafParSis.getQueryFechaHoraBaseDatos());
                tbm_horTra.setDatFe_ultMod(datFecHoy);
                tbm_horTra.setIntCo_usrMod(zafParSis.getCodigoUsuario());
                tbm_hortraDAOInterface.actualizar(con, tbm_horTra, opcion, tbm_callabBO);
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
     * @param tbm_hortra Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_hortra con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_hortra> consultarLisGen(Tbm_hortra tbm_hortra) throws Exception{
        List<Tbm_hortra> lisTbm_hortra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_hortra = tbm_hortraDAOInterface.consultarLisGen(con, tbm_hortra);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_hortra;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_hortra Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_hortra con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_hortra> consultarLisPag(Tbm_hortra tbm_hortra, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_hortra> lisTbm_hortra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_hortra = tbm_hortraDAOInterface.consultarLisPag(con, tbm_hortra, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_hortra;
    }

    /**
     * Consulta si el nombre del horario ya se encuentra registrado en la base
     * @param strNomHor Código del cargo a consultar
     * @param intCodHor Código del registro
     * @return Retorna true si ya existe y false caso contrario
     * @throws Exception
     */
    public boolean consultarCodHorRep(String strNomHor, int intCodHor) throws Exception{
        boolean blnRep = false;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                blnRep = tbm_hortraDAOInterface.consultarNomHorRep(con, strNomHor, intCodHor);
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

    public int consultarCodHorRep(String strNomHor) throws Exception{
        int intRes = -1;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intRes = tbm_hortraDAOInterface.consultarNomHorRep(con, strNomHor);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intRes;
    }

    public List<Tbm_hortra> consultarCalLabAct() throws Exception{
        List<Tbm_hortra> lisTbm_hortra = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_hortra = tbm_hortraDAOInterface.consultarCalLabAct(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_hortra;
    }

    public int[] consultaCodHor() throws Exception {
        int[] intRes = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intRes = tbm_hortraDAOInterface.consultaCodHor(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intRes;
    }

    public int[] consultaDiasValidosIniciales() throws Exception {
        int[] intRes = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intRes = tbm_hortraDAOInterface.consultaDiasValidosIniciales(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intRes;
    }

    public int[] consultaMesesValidosIniciales() throws Exception {
        int[] intRes = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(), zafParSis.getUsuarioBaseDatos(), zafParSis.getClaveBaseDatos());
            if(con!=null){
                intRes = tbm_hortraDAOInterface.consultaMesesValidosIniciales(con);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return intRes;
    }
}