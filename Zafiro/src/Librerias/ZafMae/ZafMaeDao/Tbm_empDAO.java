
package Librerias.ZafMae.ZafMaeDao;


import Librerias.ZafMae.ZafMaePoj.Tbm_emp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto de Acceso a Datos de la tabla Tbm_emp
 * Mayormente usada por el BO
 * @author Carlos Lainez
 * Guayaquil 22/06/2011
 */
public class Tbm_empDAO implements Tbm_empDAOInterface {

    public int obtenerMaxId(Connection connection) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void grabar(Connection connection, Tbm_emp tbm_emp) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void actualizar(Connection connection, Tbm_emp tbm_emp) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Consulta las empresas que coincidan con los criterios de busqueda
     * @param connection
     * @param tbm_emp Plantilla con los criterios de busqueda
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws SQLException
     */
    public List<Tbm_emp> consultarLisGen(Connection connection, Tbm_emp tbm_emp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_emp> lisTbm_emp = null;

        try{
            strSql = "select * from tbm_emp ";
            strSql += "where (? is null or co_emp = ?) ";
            strSql += "and (? is null or lower(tx_ruc) like lower(?)) ";
            strSql += "and (? is null or lower(tx_nom) like lower(?)) ";
            strSql += "and (? is null or lower(tx_dir) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tel) like lower(?)) ";
            strSql += "and (? is null or lower(tx_fax) like lower(?)) ";
            strSql += "and (? is null or lower(tx_dirweb) like lower(?)) ";
            strSql += "and (? is null or lower(tx_corele) like lower(?)) ";
            strSql += "and (? is null or lower(tx_tipper) like lower(?)) ";
            strSql += "and (? is null or nd_ivacom = ?) ";
            strSql += "and (? is null or nd_ivaven = ?) ";
            strSql += "and (? is null or co_ctaivacom = ?) ";
            strSql += "and (? is null or co_ctaivaven = ?) ";
            strSql += "and (? is null or co_ctaban = ?) ";
            strSql += "and (? is null or co_ctacaj = ?) ";
            strSql += "and (? is null or co_ctares = ?) ";
            strSql += "and (? is null or co_ctagas = ?) ";
            strSql += "and (? is null or co_ctaretftecom = ?) ";
            strSql += "and (? is null or co_ctaretfteven = ?) ";
            strSql += "and (? is null or co_ctaretivacom = ?) ";
            strSql += "and (? is null or co_ctaretivaven = ?) ";
            strSql += "and (? is null or lower(tx_obs1) like lower(?)) ";
            strSql += "and (? is null or lower(tx_obs2) like lower(?)) ";
            strSql += "and (? is null or lower(st_reg) like(?) ";
            strSql += "and (? is null or fe_ing = ?) ";
            strSql += "and (? is null or fe_ultmod = ?) ";
            strSql += "and (? is null or co_usring = ?) ";
            strSql += "and (? is null or co_usrmod = ?) ";
            strSql += "and (? is null or co_tipper = ?) ";
            strSql += "and (? is null or co_ctacomser = ?) ";
            strSql += "and (? is null or co_ctavenser = ?) ";
            strSql += "and (? is null or lower(st_regrep) like lower(?)) ";
            strSql += "and (? is null or lower(tx_desconesp) like lower(?)) ";
            strSql += "and (? is null or nd_valfluefe = ?) ";
            strSql += "and (? is null or nd_tasint = ?) ";
            strSql += "and (? is null or ne_secultdoctbmcabmovinv = ?) ";
            strSql += "and (? is null or ne_tampaqcoreleenvautestcta = ?) ";
            strSql += "and (? is null or lower(tx_usrcoreleenvautestcta) like lower(?)) ";
            strSql += "and (? is null or lower(tx_clacoreleenvautestcta) like lower(?)) ";
            strSql += "and (? is null or nd_valminajucenaut = ?) ";
            strSql += "and (? is null or nd_valmaxajucenaut = ?) ";
            strSql += "and (? is null or nd_valminajucenman = ?) ";
            strSql += "and (? is null or nd_valmaxajucenman = ?) ";
            strSql += "and (? is null or lower(st_trainvgencomvenautemprel) like lower(?)) ";
            strSql += "order by co_emp";

            preSta = connection.prepareStatement(strSql);
            //co_emp 1
            if(tbm_emp.getIntCo_emp()>0){
                preSta.setInt(1, tbm_emp.getIntCo_emp());
                preSta.setInt(2, tbm_emp.getIntCo_emp());
            }else{
                preSta.setNull(1, Types.INTEGER);
                preSta.setNull(2, Types.INTEGER);
            }
            //tx_ruc 2
            if(tbm_emp.getStrTx_ruc()!=null){
                preSta.setString(3, tbm_emp.getStrTx_ruc());
                preSta.setString(4, tbm_emp.getStrTx_ruc());
            }else{
                preSta.setNull(3, Types.VARCHAR);
                preSta.setNull(4, Types.VARCHAR);
            }
            //tx_nom 3
            if(tbm_emp.getStrTx_nom()!=null){
                preSta.setString(5, tbm_emp.getStrTx_nom());
                preSta.setString(6, tbm_emp.getStrTx_nom());
            }else{
                preSta.setNull(5, Types.VARCHAR);
                preSta.setNull(6, Types.VARCHAR);
            }
            //tx_dir 4
            if(tbm_emp.getStrTx_dir()!=null){
                preSta.setString(7, tbm_emp.getStrTx_dir());
                preSta.setString(8, tbm_emp.getStrTx_dir());
            }else{
                preSta.setNull(7, Types.VARCHAR);
                preSta.setNull(8, Types.VARCHAR);
            }
            //tx_tel 5
            if(tbm_emp.getStrTx_tel()!=null){
                preSta.setString(9, tbm_emp.getStrTx_tel());
                preSta.setString(10, tbm_emp.getStrTx_tel());
            }else{
                preSta.setNull(9, Types.VARCHAR);
                preSta.setNull(10, Types.VARCHAR);
            }
            //tx_fax 6
            if(tbm_emp.getStrTx_fax()!=null){
                preSta.setString(11, tbm_emp.getStrTx_fax());
                preSta.setString(12, tbm_emp.getStrTx_fax());
            }else{
                preSta.setNull(11, Types.VARCHAR);
                preSta.setNull(12, Types.VARCHAR);
            }
            //tx_dirweb 7
            if(tbm_emp.getStrTx_dirweb()!=null){
                preSta.setString(13, tbm_emp.getStrTx_dirweb());
                preSta.setString(14, tbm_emp.getStrTx_dirweb());
            }else{
                preSta.setNull(13, Types.VARCHAR);
                preSta.setNull(14, Types.VARCHAR);
            }
            //tx_corele 8
            if(tbm_emp.getStrTx_corele()!=null){
                preSta.setString(15, tbm_emp.getStrTx_corele());
                preSta.setString(16, tbm_emp.getStrTx_corele());
            }else{
                preSta.setNull(15, Types.VARCHAR);
                preSta.setNull(16, Types.VARCHAR);
            }
            //tx_tipper 9
            if(tbm_emp.getStrTx_tipper()!=null){
                preSta.setString(17, tbm_emp.getStrTx_tipper());
                preSta.setString(18, tbm_emp.getStrTx_tipper());
            }else{
                preSta.setNull(17, Types.VARCHAR);
                preSta.setNull(18, Types.VARCHAR);
            }
            //nd_ivacom 10
            if(tbm_emp.getBigNd_ivacom()!=null && tbm_emp.getBigNd_ivacom().longValue()>0){
                preSta.setBigDecimal(19, tbm_emp.getBigNd_ivacom());
                preSta.setBigDecimal(20, tbm_emp.getBigNd_ivacom());
            }else{
                preSta.setNull(19, Types.NUMERIC);
                preSta.setNull(20, Types.NUMERIC);
            }
            //nd_ivaven 11
            if(tbm_emp.getBigNd_ivaven()!=null && tbm_emp.getBigNd_ivaven().longValue()>0){
                preSta.setBigDecimal(21, tbm_emp.getBigNd_ivaven());
                preSta.setBigDecimal(22, tbm_emp.getBigNd_ivaven());
            }else{
                preSta.setNull(21, Types.NUMERIC);
                preSta.setNull(22, Types.NUMERIC);
            }
            //co_ctaivacom 12
            if(tbm_emp.getIntCo_ctaivacom()>0){
                preSta.setInt(23, tbm_emp.getIntCo_ctaivacom());
                preSta.setInt(24, tbm_emp.getIntCo_ctaivacom());
            }else{
                preSta.setNull(23, Types.INTEGER);
                preSta.setNull(24, Types.INTEGER);
            }
            //co_ctaivaven 13
            if(tbm_emp.getIntCo_ctaivaven()>0){
                preSta.setInt(25, tbm_emp.getIntCo_ctaivaven());
                preSta.setInt(26, tbm_emp.getIntCo_ctaivaven());
            }else{
                preSta.setNull(25, Types.INTEGER);
                preSta.setNull(26, Types.INTEGER);
            }
            //co_ctaban 14
            if(tbm_emp.getIntCo_ctaban()>0){
                preSta.setInt(27, tbm_emp.getIntCo_ctaban());
                preSta.setInt(28, tbm_emp.getIntCo_ctaban());
            }else{
                preSta.setNull(27, Types.INTEGER);
                preSta.setNull(28, Types.INTEGER);
            }
            //co_ctacaj 15
            if(tbm_emp.getIntCo_ctacaj()>0){
                preSta.setInt(29, tbm_emp.getIntCo_ctacaj());
                preSta.setInt(30, tbm_emp.getIntCo_ctacaj());
            }else{
                preSta.setNull(29, Types.INTEGER);
                preSta.setNull(30, Types.INTEGER);
            }
            //co_ctares 16
            if(tbm_emp.getIntCo_ctares()>0){
                preSta.setInt(31, tbm_emp.getIntCo_ctares());
                preSta.setInt(32, tbm_emp.getIntCo_ctares());
            }else{
                preSta.setNull(31, Types.INTEGER);
                preSta.setNull(32, Types.INTEGER);
            }
            //co_ctagas 17
            if(tbm_emp.getIntCo_ctagas()>0){
                preSta.setInt(33, tbm_emp.getIntCo_ctagas());
                preSta.setInt(34, tbm_emp.getIntCo_ctagas());
            }else{
                preSta.setNull(33, Types.INTEGER);
                preSta.setNull(34, Types.INTEGER);
            }
            //co_ctaretftecom 18
            if(tbm_emp.getIntCo_ctaretftecom()>0){
                preSta.setInt(35, tbm_emp.getIntCo_ctaretftecom());
                preSta.setInt(36, tbm_emp.getIntCo_ctaretftecom());
            }else{
                preSta.setNull(35, Types.INTEGER);
                preSta.setNull(36, Types.INTEGER);
            }
            //co_ctaretfteven 19
            if(tbm_emp.getIntCo_ctaretfteven()>0){
                preSta.setInt(37, tbm_emp.getIntCo_ctaretfteven());
                preSta.setInt(38, tbm_emp.getIntCo_ctaretfteven());
            }else{
                preSta.setNull(37, Types.INTEGER);
                preSta.setNull(38, Types.INTEGER);
            }
            //co_ctaretivacom 20
            if(tbm_emp.getIntCo_ctaretivacom()>0){
                preSta.setInt(39, tbm_emp.getIntCo_ctaretivacom());
                preSta.setInt(40, tbm_emp.getIntCo_ctaretivacom());
            }else{
                preSta.setNull(39, Types.INTEGER);
                preSta.setNull(40, Types.INTEGER);
            }
            //co_ctaretivaven 21
            if(tbm_emp.getIntCo_ctaretivaven()>0){
                preSta.setInt(41, tbm_emp.getIntCo_ctaretivaven());
                preSta.setInt(42, tbm_emp.getIntCo_ctaretivaven());
            }else{
                preSta.setNull(41, Types.INTEGER);
                preSta.setNull(42, Types.INTEGER);
            }
            //tx_obs1 22
            if(tbm_emp.getStrTx_obs1()!=null){
                preSta.setString(43, tbm_emp.getStrTx_obs1());
                preSta.setString(44, tbm_emp.getStrTx_obs1());
            }else{
                preSta.setNull(43, Types.VARCHAR);
                preSta.setNull(44, Types.VARCHAR);
            }
            //tx_obs2 23
            if(tbm_emp.getStrTx_obs2()!=null){
                preSta.setString(45, tbm_emp.getStrTx_obs2());
                preSta.setString(46, tbm_emp.getStrTx_obs2());
            }else{
                preSta.setNull(45, Types.VARCHAR);
                preSta.setNull(46, Types.VARCHAR);
            }
            //st_reg 24
            if(tbm_emp.getStrSt_reg()!=null){
                preSta.setString(47, tbm_emp.getStrSt_reg());
                preSta.setString(48, tbm_emp.getStrSt_reg());
            }else{
                preSta.setNull(47, Types.VARCHAR);
                preSta.setNull(48, Types.VARCHAR);
            }
            //fe_ing 25
            if(tbm_emp.getDatFe_ing()!=null){
                preSta.setTimestamp(49, new Timestamp(tbm_emp.getDatFe_ing().getTime()));
                preSta.setTimestamp(50, new Timestamp(tbm_emp.getDatFe_ing().getTime()));
            }else{
                preSta.setNull(49, Types.TIMESTAMP);
                preSta.setNull(50, Types.TIMESTAMP);
            }
            //fe_ultmod 26
            if(tbm_emp.getDatFe_ultmod()!=null){
                preSta.setTimestamp(51, new Timestamp(tbm_emp.getDatFe_ultmod().getTime()));
                preSta.setTimestamp(52, new Timestamp(tbm_emp.getDatFe_ultmod().getTime()));
            }else{
                preSta.setNull(51, Types.TIMESTAMP);
                preSta.setNull(52, Types.TIMESTAMP);
            }
            //co_usring 27
            if(tbm_emp.getIntCo_usring()>0){
                preSta.setInt(53, tbm_emp.getIntCo_usring());
                preSta.setInt(54, tbm_emp.getIntCo_usring());
            }else{
                preSta.setNull(53, Types.INTEGER);
                preSta.setNull(54, Types.INTEGER);
            }
            //co_usrmod 28
            if(tbm_emp.getIntCo_usrmod()>0){
                preSta.setInt(55, tbm_emp.getIntCo_usrmod());
                preSta.setInt(56, tbm_emp.getIntCo_usrmod());
            }else{
                preSta.setNull(55, Types.INTEGER);
                preSta.setNull(56, Types.INTEGER);
            }
            //co_tipper 29
            if(tbm_emp.getIntCo_tipper()>0){
                preSta.setInt(57, tbm_emp.getIntCo_tipper());
                preSta.setInt(58, tbm_emp.getIntCo_tipper());
            }else{
                preSta.setNull(57, Types.INTEGER);
                preSta.setNull(58, Types.INTEGER);
            }
            //co_ctacomser 30
            if(tbm_emp.getIntCo_ctacomser()>0){
                preSta.setInt(59, tbm_emp.getIntCo_ctacomser());
                preSta.setInt(60, tbm_emp.getIntCo_ctacomser());
            }else{
                preSta.setNull(59, Types.INTEGER);
                preSta.setNull(60, Types.INTEGER);
            }
            //co_ctavenser 31
            if(tbm_emp.getIntCo_ctavenser()>0){
                preSta.setInt(61, tbm_emp.getIntCo_ctavenser());
                preSta.setInt(62, tbm_emp.getIntCo_ctavenser());
            }else{
                preSta.setNull(61, Types.INTEGER);
                preSta.setNull(62, Types.INTEGER);
            }
            //st_regrep 32
            if(tbm_emp.getStrSt_regrep()!=null){
                preSta.setString(63, tbm_emp.getStrSt_regrep());
                preSta.setString(64, tbm_emp.getStrSt_regrep());
            }else{
                preSta.setNull(63, Types.VARCHAR);
                preSta.setNull(64, Types.VARCHAR);
            }
            //tx_desconesp 33
            if(tbm_emp.getStrTx_desconesp()!=null){
                preSta.setString(65, tbm_emp.getStrTx_desconesp());
                preSta.setString(66, tbm_emp.getStrTx_desconesp());
            }else{
                preSta.setNull(65, Types.VARCHAR);
                preSta.setNull(66, Types.VARCHAR);
            }
            //nd_valfluefe 34
            if(tbm_emp.getBigNd_valfluefe()!=null && tbm_emp.getBigNd_valfluefe().longValue()>0){
                preSta.setBigDecimal(67, tbm_emp.getBigNd_valfluefe());
                preSta.setBigDecimal(68, tbm_emp.getBigNd_valfluefe());
            }else{
                preSta.setNull(67, Types.NUMERIC);
                preSta.setNull(68, Types.NUMERIC);
            }
            //nd_tasint 35
            if(tbm_emp.getBigNd_tasint()!=null && tbm_emp.getBigNd_tasint().longValue()>0){
                preSta.setBigDecimal(69, tbm_emp.getBigNd_tasint());
                preSta.setBigDecimal(70, tbm_emp.getBigNd_tasint());
            }else{
                preSta.setNull(69, Types.NUMERIC);
                preSta.setNull(70, Types.NUMERIC);
            }
            //ne_secultdoctbmcabmovinv 36
            if(tbm_emp.getIntNe_secultdoctbmcabmovinv()>0){
                preSta.setInt(71, tbm_emp.getIntNe_secultdoctbmcabmovinv());
                preSta.setInt(72, tbm_emp.getIntNe_secultdoctbmcabmovinv());
            }else{
                preSta.setNull(71, Types.INTEGER);
                preSta.setNull(72, Types.INTEGER);
            }
            //ne_tampaqcoreleenvautestcta 37
            if(tbm_emp.getIntNe_tampaqcoreleenvautestcta()>0){
                preSta.setInt(73, tbm_emp.getIntNe_tampaqcoreleenvautestcta());
                preSta.setInt(74, tbm_emp.getIntNe_tampaqcoreleenvautestcta());
            }else{
                preSta.setNull(73, Types.INTEGER);
                preSta.setNull(74, Types.INTEGER);
            }
            //tx_usrcoreleenvautestcta 38
            if(tbm_emp.getStrTx_usrcoreleenvautestcta()!=null){
                preSta.setString(75, tbm_emp.getStrTx_usrcoreleenvautestcta());
                preSta.setString(76, tbm_emp.getStrTx_usrcoreleenvautestcta());
            }else{
                preSta.setNull(75, Types.VARCHAR);
                preSta.setNull(76, Types.VARCHAR);
            }
            //tx_clacoreleenvautestcta 39
            if(tbm_emp.getStrTx_clacoreleenvautestcta()!=null){
                preSta.setString(77, tbm_emp.getStrTx_clacoreleenvautestcta());
                preSta.setString(78, tbm_emp.getStrTx_clacoreleenvautestcta());
            }else{
                preSta.setNull(77, Types.VARCHAR);
                preSta.setNull(78, Types.VARCHAR);
            }
            //nd_valminajucenaut 40
            if(tbm_emp.getBigNd_valminajucenaut()!=null && tbm_emp.getBigNd_valminajucenaut().longValue()>0){
                preSta.setBigDecimal(79, tbm_emp.getBigNd_valminajucenaut());
                preSta.setBigDecimal(80, tbm_emp.getBigNd_valminajucenaut());
            }else{
                preSta.setNull(79, Types.NUMERIC);
                preSta.setNull(80, Types.NUMERIC);
            }
            //nd_valmaxajucenaut 41
            if(tbm_emp.getBigNd_valmaxajucenaut()!=null && tbm_emp.getBigNd_valmaxajucenaut().longValue()>0){
                preSta.setBigDecimal(83, tbm_emp.getBigNd_valmaxajucenaut());
                preSta.setBigDecimal(84, tbm_emp.getBigNd_valmaxajucenaut());
            }else{
                preSta.setNull(83, Types.NUMERIC);
                preSta.setNull(84, Types.NUMERIC);
            }
            //nd_valminajucenman 42
            if(tbm_emp.getBigNd_valminajucenman()!=null && tbm_emp.getBigNd_valminajucenman().longValue()>0){
                preSta.setBigDecimal(83, tbm_emp.getBigNd_valminajucenman());
                preSta.setBigDecimal(84, tbm_emp.getBigNd_valminajucenman());
            }else{
                preSta.setNull(83, Types.NUMERIC);
                preSta.setNull(84, Types.NUMERIC);
            }
            //nd_valmaxajucenman 43
            if(tbm_emp.getBigNd_valmaxajucenman()!=null && tbm_emp.getBigNd_valmaxajucenman().longValue()>0){
                preSta.setBigDecimal(85, tbm_emp.getBigNd_valmaxajucenman());
                preSta.setBigDecimal(86, tbm_emp.getBigNd_valmaxajucenman());
            }else{
                preSta.setNull(85, Types.NUMERIC);
                preSta.setNull(86, Types.NUMERIC);
            }
            //st_trainvgencomvenautemprel 44
            if(tbm_emp.getStrSt_trainvgencomvenautemprel()!=null){
                preSta.setString(87, tbm_emp.getStrSt_trainvgencomvenautemprel());
                preSta.setString(88, tbm_emp.getStrSt_trainvgencomvenautemprel());
            }else{
                preSta.setNull(87, Types.VARCHAR);
                preSta.setNull(88, Types.VARCHAR);
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
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

    public List<Tbm_emp> consultarLisPag(Connection connection, Tbm_emp tbm_emp, int intRegIni, int intCanReg) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Consulta todas las empresas menos la empresa grupo
     * @param connection
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws SQLException
     */
    public List<Tbm_emp> consultarLisGenSinEmpGru(Connection connection) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_emp> lisTbm_emp = null;

        try{
            strSql = "select * from tbm_emp ";
            strSql += "where st_reg = 'A'  ";
            strSql += "and co_emp <> 0 ";
            strSql += "order by co_emp";

            preSta = connection.prepareStatement(strSql);

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
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

    /**
     * Consulta la empresa que coincida con el nombre especificado
     * @param connection
     * @param strNomEmp Nombre de la empresa a buscar
     * @return Retorna una lista de tipo Tbm_emp con los datos de la consulta
     * @throws SQLException
     */
    public List<Tbm_emp> consultarLisGenPorNomEmp(Connection connection, String strNomEmp) throws SQLException {
        String strSql = null;
        PreparedStatement preSta = null;
        ResultSet resSet = null;
        List<Tbm_emp> lisTbm_emp = null;

        try{
            strSql = "select * from tbm_emp ";
            strSql += "where tx_nom like ? ";
            strSql += "and st_reg = 'A' ";
            strSql += "order by co_emp";

            preSta = connection.prepareStatement(strSql);
            //strNomEmp 1
            if(strNomEmp!=null){
                preSta.setString(1, strNomEmp);
            }else{
                preSta.setNull(1, Types.VARCHAR);
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
        } finally{
            try{resSet.close();}catch(Throwable ignore){}
            try{preSta.close();}catch(Throwable ignore){}
        }

        return lisTbm_emp;
    }

}
