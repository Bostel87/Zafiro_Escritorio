/*
 * ZafObtConCen.java
 *
 * Created on 1 de febrero de 2007, 11:48 AM
 */

package Librerias.ZafObtConCen;
import Librerias.ZafUtil.ZafUtil;
import java.sql.*;
/**
 *
 * @author  Javier Ayapata
 * ver 0.1
 */
public class ZafObtConCen {
    Librerias.ZafParSis.ZafParSis  objZafParSis;
    Librerias.ZafUtil.ZafUtil  objUti;
    Connection CONN_LOCAL=null;
    public int intCodReg=0;
    /** Creates a new instance of ZafObtConCen */
    public ZafObtConCen(Librerias.ZafParSis.ZafParSis ZafParsis) {
           this.objZafParSis =  ZafParsis;
           objUti = new ZafUtil();
          
           ObtenerConeccion(objZafParSis.getStringConexionCentral(), objZafParSis.getUsuarioConexionCentral(), objZafParSis.getClaveConexionCentral() );
                    
         
    }
    
     
    private void  ObtenerConeccion(String str_conloc, String str_user , String str_clacon ){
        try
        {
          Abrir_Conexion(str_conloc, str_user , str_clacon );
          Statement stm=CONN_LOCAL.createStatement();
          ResultSet rst;
          String sql = "SELECT co_regdes FROM tbm_cfgBasDatRep where co_grp=5";
          rst=stm.executeQuery(sql);
          
          while(rst.next()){
             intCodReg=rst.getInt("co_regdes");
          }
          stm.close();
          stm=null;
          rst.close();
          rst=null;
          Cerrar_Conexion();
        }
        catch (java.sql.SQLException e) { objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e); }
        catch (Exception e) { objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e); }
    }
    
    
     private void Abrir_Conexion(String str_strcon, String str_usrcon, String str_cla){
        try{
            //System.out.println("ABRIR CONEXION...."); 
            CONN_LOCAL=DriverManager.getConnection( str_strcon, str_usrcon, str_cla );          
            CONN_LOCAL.setAutoCommit(false);
           }
           catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
       
    }
    
    
    
     private void Cerrar_Conexion(){
        try{
           ///System.out.println("CERRANDO CONEXION....");
            CONN_LOCAL.close();
            CONN_LOCAL=null; 
        }
           catch(SQLException  Evt){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
    }
    
    
}
