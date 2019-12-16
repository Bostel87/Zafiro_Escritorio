
package Librerias.ZafMae.ZafMaeBo;

import Librerias.ZafMae.ZafMaeDao.Tbm_docdigsisDAOInterface;
import Librerias.ZafMae.ZafMaePoj.Tbm_docdigsis;
import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Objeto se encarga de manejar las transacciones a la Base de Datos en un marco de conexión.
 * Recibe información del Bean, realiza todas las transacciones del evento del Bean usando los DAO de cada tabla.
 * @author Carlos Lainez
 * Guayaquil 24/05/2011
 */
public class Tbm_docdigsisBO {
    private Tbm_docdigsisDAOInterface tbm_docdigsisDAOInterface;
    private ZafParSis zafParSis;

    public Tbm_docdigsisBO(ZafParSis zafParSis) throws Exception {
        this.zafParSis = zafParSis;

        try{
            tbm_docdigsisDAOInterface = (Tbm_docdigsisDAOInterface) Tbm_docdigsisBO.class.getClassLoader().loadClass("Librerias.ZafMae.ZafMaeDao.Tbm_docdigsisDAO").newInstance();
        }catch(Exception ex){
            throw new Exception("Problemas al cargar la interfaz Librerias.ZafMae.ZafMaeDao.Tbm_docdigsisDAOInterface");
        }
    }

    /**
     * Consulta General según parámetros ingresados por pantalla
     * @param tbm_docdigsis Objetos con parámetros de consulta
     * @return Retorna una lista de tipo Tbm_docdigsis con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_docdigsis> consultarLisGen(Tbm_docdigsis tbm_docdigsis) throws Exception{
        List<Tbm_docdigsis> lisTbm_docdigsis = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_docdigsis = tbm_docdigsisDAOInterface.consultarLisGen(con, tbm_docdigsis);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_docdigsis;
    }

    /**
     * Consulta General con pagineo según parámetros ingresados por pantalla
     * @param tbm_docdigsis Objetos con parámetros de consulta
     * @param intNumPag Número de página a consultar
     * @param intCanReg Cantidad de registros que retorna la consulta
     * @return Retorna una lista de tipo Tbm_docdigsis con uno o varios registros
     * @throws Exception
     */
    public List<Tbm_docdigsis> consultarLisPag(Tbm_docdigsis tbm_docdigsis, int intNumPag, int intCanReg) throws Exception{
        List<Tbm_docdigsis> lisTbm_docdigsis = null;
        Connection con = null;

        try {
            con = DriverManager.getConnection(zafParSis.getStringConexion(),zafParSis.getUsuarioBaseDatos(),zafParSis.getClaveBaseDatos());
            if(con!=null){
                lisTbm_docdigsis = tbm_docdigsisDAOInterface.consultarLisPag(con, tbm_docdigsis, intNumPag, intCanReg);
            }
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            con.close();
        }

        return lisTbm_docdigsis;
    }

    /**
     * Retorna parametros de localizacion de los archivos en el servidor
     * @param args args[0] retorna la ip del servidor y args[1] retorna la ruta del archivo
     * @throws Exception
     */
    //public void getRutDocDig(String[] args) throws Exception{
    public String[] getRutDocDig(String[] args) throws Exception{
        //String strRutDocDig = null;
        List<Tbm_docdigsis> lisTmp = null;
        Tbm_docdigsis tbm_docdigsispar = null;
        Tbm_docdigsis tbm_docdigsis = null;

        try{
            tbm_docdigsispar = new Tbm_docdigsis();
            tbm_docdigsis = new Tbm_docdigsis();

            tbm_docdigsispar.setIntCo_emp(zafParSis.getCodigoEmpresa());
            tbm_docdigsispar.setIntCo_loc(zafParSis.getCodigoLocal());
            tbm_docdigsispar.setIntCo_mnu(zafParSis.getCodigoMenu());
            lisTmp = consultarLisGen(tbm_docdigsispar);

            if(lisTmp != null){
                tbm_docdigsis = lisTmp.get(0);
                /*if(System.getProperty("os.name").equals("Linux")){
                    strRutDocDig = tbm_docdigsis.getStrTx_rutrel();
                }else{
                    strRutDocDig = tbm_docdigsis.getStrTx_rutabs()+tbm_docdigsis.getStrTx_rutrel();
                }*/
                //strRutDocDig = tbm_docdigsis.getStrTx_rutrel();
                args[0] = tbm_docdigsis.getStrTx_rutabs();
                args[1] = tbm_docdigsis.getStrTx_rutrel();
            }
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception(ex.getMessage());
        }

        return args;
    }

    /**
     * Retorna nombre formateado para el documento digitalizado
     * @param strNomPan Nombre de la pantalla donde se sube el archivo
     * @param intCodCom Codigo compuesto del propietario del documento
     * @param intTipDocDig Codigo del tipo de documento
     * @param intSec Secuencia
     * @param strExt Extension del documento
     * @return
     */
    public String getNomDocDig(String strNomPan, int[] intCodCom, int intTipDocDig, int intSec, String strExt){
        String strNomArcDoc = null;
        String strChaSep = "_";
        String strChaCod = "c";
        String strChaTip = "t";
        String strChaSec = "s";

        strNomArcDoc = strNomPan+strChaSep;
        for(int num:intCodCom){
            strNomArcDoc += strChaCod+num;
            strNomArcDoc += strChaSep;
        }
        strNomArcDoc += strChaTip+intTipDocDig+strChaSep+strChaSec+intSec+strExt;

        return strNomArcDoc;
    }
}