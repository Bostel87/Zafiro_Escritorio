package Librerias.ZafRecHum.ZafRecHumDao;

import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import Librerias.ZafRecHum.ZafRecHumPoj.Tbm_traemp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/** 
 * Objeto de Acceso a Datos de la tabla tbm_traemp
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 17/06/2011
 * v1.02
 */
public class Tbm_traempDAO implements Tbm_traempDAOInterface {

    /**
     * Graba en la tabla tbm_traemp
     * @param connection
     * @param tbm_traemp Objeto POJO que contiene los datos de la tabla tbm_traemp
     * @throws SQLException
     */
    public void grabar(Connection connection, Tbm_traemp tbm_traemp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "insert into tbm_traemp(co_emp, co_tra) ";
            strSql += "values(?,?)";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_traemp.getIntCo_emp()>0){//modificar a '>=' para que acepte empresa grupo que es de codigo 0
                preSta.setInt(1, tbm_traemp.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //co_tra 2
            if(tbm_traemp.getIntCo_tra()>0){
                preSta.setInt(2, tbm_traemp.getIntCo_tra());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally{
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Actualiza datos de la tabla tbm_traemp
     * @param connection
     * @param tbm_traemp Objeto con los parámetros a actualizar
     * @throws SQLException
     */
    public void actualizar(Connection connection, Tbm_traemp tbm_traemp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try {
            strSql = "update tbm_traemp ";
            strSql += "set co_emp = ?, ";
            strSql += "co_tra = ?";
            strSql += "where co_emp = ? ";
            strSql += "and co_tra = ?";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_traemp.getIntCo_emp()>0){
                preSta.setInt(1, tbm_traemp.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //co_tra 2
            if(tbm_traemp.getIntCo_tra()>0){
                preSta.setInt(2, tbm_traemp.getIntCo_tra());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }

            preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    /**
     * Consulta general de la tabla tbm_traemp
     * @param connection
     * @param tbm_traemp Objeto con parámetros de consulta
     * @return Retorna una lista tipo Tbm_traemp cargada con la información de la base
     * @throws SQLException
     */
    public List<Tbm_traemp> consultarLisGen(Connection connection, Tbm_traemp tbm_traemp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_traemp> lisTbr_traemp = null;

        try{
            strSql = "select * from tbm_traemp ";
            strSql += "where (? is null or co_emp = ?) ";
            strSql += "and (? is null or co_tra = ?) ";
            strSql += "order by co_emp, co_tra";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_traemp.getIntCo_emp()>0){
                preSta.setInt(1, tbm_traemp.getIntCo_emp());
                preSta.setInt(2, tbm_traemp.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_tra 2
            if(tbm_traemp.getIntCo_tra()>0){
                preSta.setInt(3, tbm_traemp.getIntCo_tra());
                preSta.setInt(4, tbm_traemp.getIntCo_tra());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbr_traemp = new ArrayList<Tbm_traemp>();
                do{
                    Tbm_traemp tmp = new Tbm_traemp();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));

                    lisTbr_traemp.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbr_traemp;
    }

    /**
     * Consulta general de la tabla tbm_traemp aplicando pagineo
     * @param connection
     * @param tbm_traemp Objeto con parámetros de consulta
     * @param intRegIni Número inicial de registro del conjunto de registros
     * @param intCanReg Cantidad de registros a consultar a partir del número inicial de registro
     * @return lista de tipo Tbm_traemp con los registros consultados
     * @throws SQLException
     */
    public List<Tbm_traemp> consultarLisPag(Connection connection, Tbm_traemp tbm_traemp, int intRegIni, int intCanReg) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_traemp> lisTbr_traemp = null;

        try{
            strSql = "select * from tbm_traemp ";
            strSql += "where (? is null or co_emp = ?) ";
            strSql += "and (? is null or co_tra = ?) ";
            strSql += "order by co_emp, co_tra";
            strSql += "limit ? offset ?";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_traemp.getIntCo_emp()>0){
                preSta.setInt(1, tbm_traemp.getIntCo_emp());
                preSta.setInt(2, tbm_traemp.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //co_tra 2
            if(tbm_traemp.getIntCo_tra()>0){
                preSta.setInt(3, tbm_traemp.getIntCo_tra());
                preSta.setInt(4, tbm_traemp.getIntCo_tra());
            }else{
                preSta.setNull(3, Types.INTEGER);
                preSta.setNull(4, Types.INTEGER);
            }
            //limit 3
            if(intCanReg > 0 ){
                preSta.setInt(5, intCanReg);
                preSta.setInt(6, intCanReg);
            }else{
                preSta.setNull(5, Types.INTEGER);
                preSta.setNull(6, Types.INTEGER);
            }
            //offset 4
            preSta.setInt(7, intRegIni);

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbr_traemp = new ArrayList<Tbm_traemp>();
                do{
                    Tbm_traemp tmp = new Tbm_traemp();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setIntCo_tra(resSet.getInt("co_tra"));

                    lisTbr_traemp.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbr_traemp;
    }

    //public List<String> consultarLisGenPorCodTra2(Connection connection, int intCodTra, int intCoEmp) throws SQLException {
    public List<String> consultarLisGenPorCodTra2(Connection connection, int intCodTra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<String> lisTbm_emp = null;

        try{

            strSql = "select * from tbm_cabhortra where co_hor in (select co_hor from tbm_traemp where co_tra = ?)";// and co_emp = ?) ";

            preSta = connection.prepareStatement(strSql);
            //intCodTra 1
            if(intCodTra>0){
                preSta.setInt(1, intCodTra);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            /*if(intCoEmp>0){
                preSta.setInt(2, intCoEmp);
            }else{
                preSta.setNull(2, Types.INTEGER);
            }*/

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_emp = new ArrayList<String>();
                do{
                    String str = resSet.getString("tx_nom");
                    lisTbm_emp.add(str);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

    /**
     * Consulta las empresas asignadas al trabajador
     * @param connection
     * @param intCodTra Codigo del Trabajador
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws SQLException
     */
    public List<Tbm_emp> consultarLisGenPorCodTra(Connection connection, int intCodTra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_emp> lisTbm_emp = null;

        try{
            strSql = "select e.* ";
            strSql += "from tbm_emp e, tbm_traemp te ";
            strSql += "where te.co_tra = ? ";
            strSql += "and e.co_emp = te.co_emp ";
            strSql += "and te.st_reg = 'A' ";

            preSta = connection.prepareStatement(strSql);
            //intCodTra 1
            if(intCodTra>0){
                preSta.setInt(1, intCodTra);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_emp = new ArrayList<Tbm_emp>();
                do{
                    Tbm_emp tmp = new Tbm_emp();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setStrTx_ruc(resSet.getString("tx_ruc"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_tel(resSet.getString("tx_tel"));
                    tmp.setStrTx_fax(resSet.getString("tx_fax"));
                    tmp.setStrTx_dirweb(resSet.getString("tx_dirweb"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_tipper(resSet.getString("tx_tipper"));
                    tmp.setBigNd_ivacom(resSet.getBigDecimal("nd_ivacom"));
                    tmp.setBigNd_ivaven(resSet.getBigDecimal("nd_ivaven"));
                    tmp.setIntCo_ctaivacom(resSet.getInt("co_ctaivacom"));
                    tmp.setIntCo_ctaivaven(resSet.getInt("co_ctaivaven"));
                    tmp.setIntCo_ctaban(resSet.getInt("co_ctaban"));
                    tmp.setIntCo_ctacaj(resSet.getInt("co_ctacaj"));
                    tmp.setIntCo_ctares(resSet.getInt("co_ctares"));
                    tmp.setIntCo_ctagas(resSet.getInt("co_ctagas"));
                    tmp.setIntCo_ctaretftecom(resSet.getInt("co_ctaretftecom"));
                    tmp.setIntCo_ctaretfteven(resSet.getInt("co_ctaretfteven"));
                    tmp.setIntCo_ctaretivacom(resSet.getInt("co_ctaretivacom"));
                    tmp.setIntCo_ctaretivaven(resSet.getInt("co_ctaretivaven"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setIntCo_tipper(resSet.getInt("co_tipper"));
                    tmp.setIntCo_ctacomser(resSet.getInt("co_ctacomser"));
                    tmp.setIntCo_ctavenser(resSet.getInt("co_ctavenser"));
                    tmp.setStrSt_regrep(resSet.getString("st_regrep"));
                    tmp.setStrTx_desconesp(resSet.getString("tx_desconesp"));
                    tmp.setBigNd_valfluefe(resSet.getBigDecimal("nd_valfluefe"));
                    tmp.setBigNd_tasint(resSet.getBigDecimal("nd_tasint"));
                    tmp.setIntNe_secultdoctbmcabmovinv(resSet.getInt("ne_secultdoctbmcabmovinv"));
                    tmp.setIntNe_tampaqcoreleenvautestcta(resSet.getInt("ne_tampaqcoreleenvautestcta"));
                    tmp.setStrTx_usrcoreleenvautestcta(resSet.getString("tx_usrcoreleenvautestcta"));
                    tmp.setStrTx_clacoreleenvautestcta(resSet.getString("tx_clacoreleenvautestcta"));
                    tmp.setBigNd_valminajucenaut(resSet.getBigDecimal("nd_valminajucenaut"));
                    tmp.setBigNd_valmaxajucenaut(resSet.getBigDecimal("nd_valmaxajucenaut"));
                    tmp.setBigNd_valminajucenman(resSet.getBigDecimal("nd_valminajucenman"));
                    tmp.setBigNd_valmaxajucenman(resSet.getBigDecimal("nd_valmaxajucenman"));
                    tmp.setStrSt_trainvgencomvenautemprel(resSet.getString("st_trainvgencomvenautemprel"));

                    lisTbm_emp.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

    /**
     * Consulta los datos de la empresa por codigo de trabajador y nombre de empresa
     * @param connection
     * @param intCodTra Codigo del trabajador
     * @param strNomEmp Nombre de la empresa
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws SQLException
     */
    public List<Tbm_emp> consultarLisGenPorCodTraNomEmp(Connection connection, int intCodTra, String strNomEmp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_emp> lisTbm_emp = null;

        try{
            strSql = "select e.* ";
            strSql += "from tbm_emp e, tbm_traemp te ";
            strSql += "where te.co_tra = ? ";
            strSql += "and e.co_emp = te.co_emp ";
            strSql += "and e.st_reg = 'A' ";
            strSql += "and e.tx_nom like ? ";
            
            preSta = connection.prepareStatement(strSql);
            //intCodTra 1
            if(intCodTra>0){
                preSta.setInt(1, intCodTra);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //strNomEmp 2
            if(strNomEmp!=null){
                preSta.setString(2, strNomEmp);
            }else{
                preSta.setNull(2, Types.VARCHAR);
            }

            resSet = preSta.executeQuery();

            if(resSet.next()){
                lisTbm_emp = new ArrayList<Tbm_emp>();
                do{
                    Tbm_emp tmp = new Tbm_emp();
                    tmp.setIntCo_emp(resSet.getInt("co_emp"));
                    tmp.setStrTx_ruc(resSet.getString("tx_ruc"));
                    tmp.setStrTx_nom(resSet.getString("tx_nom"));
                    tmp.setStrTx_dir(resSet.getString("tx_dir"));
                    tmp.setStrTx_tel(resSet.getString("tx_tel"));
                    tmp.setStrTx_fax(resSet.getString("tx_fax"));
                    tmp.setStrTx_dirweb(resSet.getString("tx_dirweb"));
                    tmp.setStrTx_corele(resSet.getString("tx_corele"));
                    tmp.setStrTx_tipper(resSet.getString("tx_tipper"));
                    tmp.setBigNd_ivacom(resSet.getBigDecimal("nd_ivacom"));
                    tmp.setBigNd_ivaven(resSet.getBigDecimal("nd_ivaven"));
                    tmp.setIntCo_ctaivacom(resSet.getInt("co_ctaivacom"));
                    tmp.setIntCo_ctaivaven(resSet.getInt("co_ctaivaven"));
                    tmp.setIntCo_ctaban(resSet.getInt("co_ctaban"));
                    tmp.setIntCo_ctacaj(resSet.getInt("co_ctacaj"));
                    tmp.setIntCo_ctares(resSet.getInt("co_ctares"));
                    tmp.setIntCo_ctagas(resSet.getInt("co_ctagas"));
                    tmp.setIntCo_ctaretftecom(resSet.getInt("co_ctaretftecom"));
                    tmp.setIntCo_ctaretfteven(resSet.getInt("co_ctaretfteven"));
                    tmp.setIntCo_ctaretivacom(resSet.getInt("co_ctaretivacom"));
                    tmp.setIntCo_ctaretivaven(resSet.getInt("co_ctaretivaven"));
                    tmp.setStrTx_obs1(resSet.getString("tx_obs1"));
                    tmp.setStrTx_obs2(resSet.getString("tx_obs2"));
                    tmp.setStrSt_reg(resSet.getString("st_reg"));
                    tmp.setDatFe_ing(resSet.getTimestamp("fe_ing"));
                    tmp.setDatFe_ultmod(resSet.getTimestamp("fe_ultmod"));
                    tmp.setIntCo_usring(resSet.getInt("co_usring"));
                    tmp.setIntCo_usrmod(resSet.getInt("co_usrmod"));
                    tmp.setIntCo_tipper(resSet.getInt("co_tipper"));
                    tmp.setIntCo_ctacomser(resSet.getInt("co_ctacomser"));
                    tmp.setIntCo_ctavenser(resSet.getInt("co_ctavenser"));
                    tmp.setStrSt_regrep(resSet.getString("st_regrep"));
                    tmp.setStrTx_desconesp(resSet.getString("tx_desconesp"));
                    tmp.setBigNd_valfluefe(resSet.getBigDecimal("nd_valfluefe"));
                    tmp.setBigNd_tasint(resSet.getBigDecimal("nd_tasint"));
                    tmp.setIntNe_secultdoctbmcabmovinv(resSet.getInt("ne_secultdoctbmcabmovinv"));
                    tmp.setIntNe_tampaqcoreleenvautestcta(resSet.getInt("ne_tampaqcoreleenvautestcta"));
                    tmp.setStrTx_usrcoreleenvautestcta(resSet.getString("tx_usrcoreleenvautestcta"));
                    tmp.setStrTx_clacoreleenvautestcta(resSet.getString("tx_clacoreleenvautestcta"));
                    tmp.setBigNd_valminajucenaut(resSet.getBigDecimal("nd_valminajucenaut"));
                    tmp.setBigNd_valmaxajucenaut(resSet.getBigDecimal("nd_valmaxajucenaut"));
                    tmp.setBigNd_valminajucenman(resSet.getBigDecimal("nd_valminajucenman"));
                    tmp.setBigNd_valmaxajucenman(resSet.getBigDecimal("nd_valmaxajucenman"));
                    tmp.setStrSt_trainvgencomvenautemprel(resSet.getString("st_trainvgencomvenautemprel"));

                    lisTbm_emp.add(tmp);
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

    /**
     * Elimina fisicamente la asociacion del trabajador con la empresa
     * @param connection
     * @param intCodTra Codigo del trabajador
     * @param intCodEmp Codigo de la empresa
     * @throws SQLException
     */
    public void eliminar(Connection connection, int intCodTra, int intCodEmp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;

        try{
            strSql = "delete from tbm_traemp ";
            strSql += "where co_emp = ? ";
            strSql += "and co_tra = ? ";

            preSta =  connection.prepareStatement(strSql);
            //intCodTra 1
            if(intCodTra>0){
                preSta.setInt(1, intCodTra);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            //intCodEmp 2
            if(intCodEmp>0){
                preSta.setInt(2, intCodEmp);
            }else{
                preSta.setNull(2, Types.INTEGER);
            }

            preSta.executeUpdate();

        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }
    }

    public void actualizarTraEmpCodHor(Connection connection, Tbm_traemp tbr_traemp) throws SQLException {


        String strSql = null;
        PreparedStatement preSta = null;

        try {

            strSql = "update tbm_traemp ";
            strSql += "set st_horfij = ?";
            strSql += ", co_hor = ? ";
            //strSql += "where co_emp = ? ";
            strSql += "where co_tra = ?";

            preSta = connection.prepareStatement(strSql);

            //co_hor 1
            if(tbr_traemp.getIntCo_Hor()>0){
                preSta.setInt(2, tbr_traemp.getIntCo_Hor());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }
            //Para Grabar HorarioFijo N o S bostel
            if(!tbr_traemp.getStrSt_HorFij().isEmpty()){
                preSta.setString(1, tbr_traemp.getStrSt_HorFij());
            }else{
                preSta.setNull(1, Types.VARCHAR);
            }
            //co_emp 2
            /*if(tbr_traemp.getIntCo_emp()>0){
                preSta.setInt(2, tbr_traemp.getIntCo_emp());
            }else{
                preSta.setNull(2, Types.INTEGER);
            }*/
            //co_tra 3
            if(tbr_traemp.getIntCo_tra()>0){
                preSta.setInt(3, tbr_traemp.getIntCo_tra());
            }else{
                preSta.setNull(3, Types.INTEGER);
            
           
            }
             preSta.executeUpdate();
        } finally {
            try{preSta.close();}catch(Throwable ignore){}
        }

    }
  public String consultarLisGenPorCodTraPorHor(Connection connection, int intCodTra) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        String strTbm_empHorFij = null;

        try{

            strSql = "select * from tbm_traemp where co_tra = ?";// and co_emp = ?) ";

            preSta = connection.prepareStatement(strSql);
            //intCodTra 1
            if(intCodTra>0){
                preSta.setInt(1, intCodTra);
            }else{
                preSta.setNull(1, Types.INTEGER);
            }
            /*if(intCoEmp>0){
                preSta.setInt(2, intCoEmp);
            }else{
                preSta.setNull(2, Types.INTEGER);
            }*/

            resSet = preSta.executeQuery();

            if(resSet.next()){
                strTbm_empHorFij = null;
                do{
                    String str = resSet.getString("st_horfij");
                    strTbm_empHorFij=str;
                }while(resSet.next());
            }
        } finally {
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return strTbm_empHorFij;
    }
}
