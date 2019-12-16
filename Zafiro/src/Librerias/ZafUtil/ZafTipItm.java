/*
 * ZafTipItm.java
 *
 * Created on 2 de febrero de 2005, 16:09
 * ver. 1.0 
 */

package Librerias.ZafUtil;

/**
 *
 * @author  Iditrix
 */
public class ZafTipItm {
    Librerias.ZafParSis.ZafParSis objZafParSis; 
    /*
     * Variables Sql
     */
    private java.sql.Connection con;  //Coneccion a la base
    private java.sql.Statement stm;   //Statement 
    private java.sql.ResultSet rst;   //Resultset que tendra los datos 
    
    /** Creates a new instance of ZafTipItm */
    public ZafTipItm(Librerias.ZafParSis.ZafParSis objZafParSis) {
        this.objZafParSis = objZafParSis;
    }
    public boolean isServicio(int cod_itm){
        boolean blnIsServicio=false;
               try{
                   con=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de clientes y obtener 
                *  sus datos .
                */
                  
                    String strSql = "select st_ser from tbm_inv where " +
                                        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "co_itm = " + cod_itm;
                    stm = con.createStatement();
                    rst = stm.executeQuery(strSql);
                    
                    /*
                     * Significa ke encontro el producto
                     */
                    if(rst.next()){
                        blnIsServicio =  (rst.getString("st_ser").charAt(0)=='N')? false: true;
                    }
                    rst.close();
                    stm.close();
                    con.close();
               }
               catch(java.sql.SQLException Evt){
                       return false;
               }

               catch(Exception Evt){
                       return false;
               }
         return blnIsServicio;
    }
    

}
