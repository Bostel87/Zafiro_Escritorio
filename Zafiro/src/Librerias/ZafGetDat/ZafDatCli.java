/**
 * Clase para obtener los datos del cliente de una cotizacion o una factura
 */
package Librerias.ZafGetDat;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DriverManager;

/**
 * @author Sistemas6
 */
public class ZafDatCli 
{
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private java.awt.Component cmpPad;
    private String strSQL, strRes;

    public ZafDatCli(ZafParSis obj, java.awt.Component parent)
    {
        try
        {
            objParSis=(ZafParSis)obj.clone();
            objUti=new ZafUtil();
            cmpPad=parent;
        }
        catch (CloneNotSupportedException e)
        {
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
    }
    
    /**
     * Obtiene el nombre del Cliente al que pertenece al cotizacion tbm_cli.tx_nom 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    public String getNombreClienteCotizacion(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.tx_nom \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("tx_nom");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    }
    
    /**
     * Obtiene la celuda del Cliente al que pertenece al cotizacion tbm_cli.tx_ide 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
      public String getIDClienteCotizacion(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.tx_ide \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("tx_ide");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    }
      
       
    /**
     * Obtiene la Direccion que aparece en la factura al que pertenece al cotizacion tbm_cli.tx_ide 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    public String getDireccionClienteCotizacionGeneraFactura(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.tx_dirCliFac \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("tx_dirCliFac");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    }
      
    /**
     * Obtiene la Direccion que aparece en la Guia al que pertenece al cotizacion tbm_cli.tx_ide 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
     public String getDireccionClienteCotizacionGeneraGuia(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.tx_dirCliGuiRem \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("tx_dirCliGuiRem");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    } 
     
     /**
     * Obtiene el telefono al que pertenece al cotizacion tbm_cli.tx_ide 
     * 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
     public String getTelefonoClienteCotizacion(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.tx_tel \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("tx_tel");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    } 
    
    /**
     * Obtiene el nombre del Cliente al que pertenece al cotizacion tbm_cli.tx_nom 
     * @param CodEmp
     * @param CodLoc
     * @param CodCot
     * @return 
     */
    public String getCodigoClienteCotizacion(int CodEmp, int CodLoc, int CodCot){
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        java.sql.Connection conLoc;
        try{
            conLoc=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (conLoc != null) {
                stmLoc=conLoc.createStatement();
                strSQL="";
                strSQL+=" SELECT a2.co_cli \n";
                strSQL+=" FROM tbm_cabCotVen as a1  \n";
                strSQL+=" INNER JOIN tbm_cli as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cli=a2.co_cli) \n";
                strSQL+=" WHERE a1.co_emp="+CodEmp+" and a1.co_loc="+CodLoc+" and a1.co_cot="+CodCot+" \n";
                rstLoc=stmLoc.executeQuery(strSQL);
                if(rstLoc.next()){
                    strRes=rstLoc.getString("co_cli");
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
            conLoc.close();
            conLoc=null;
        }
        catch(java.sql.SQLException e){
            strRes="ERROR";
            objUti.mostrarMsgErr_F1(cmpPad, e);
        }
        catch(Exception e){
            objUti.mostrarMsgErr_F1(cmpPad, e);
            strRes="ERROR";
        }
        return strRes;
    }
    
    
}
