/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Librerias.ZafEstFacEle;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafEstFacEle.ZafCabEstFacEle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author: Dennis Betancourt
 */
public class ZafEstFacEle
{
    ZafParSis objParSis;
    Connection con;
    ZafCabEstFacEle objZafCabEstFacEle;
    
    public ZafEstFacEle(ZafCabEstFacEle objCabCon, Connection con)
    {   this.con = con;
        objZafCabEstFacEle = objCabCon;
    }
    
    public ZafEstFacEle(Connection con)
    {
    
    }
    
    public String getEstadoDocEle()
    {
        Statement stm;
        ResultSet rst;
        String strRes = "", strQry;
        
        try
        {
            stm = con.createStatement();
            strQry =  "SELECT st_autfacele ";
            strQry += "FROM " + objZafCabEstFacEle.getNomTbl() + " ";
            strQry += "WHERE co_emp = "+objZafCabEstFacEle.getCodEmp();
            strQry += " and co_loc = "+objZafCabEstFacEle.getCodLoc();
            strQry += " and co_tipdoc = "+objZafCabEstFacEle.getCodTipDoc();
            strQry += " and co_doc = "+objZafCabEstFacEle.getCodDoc();
            
            rst = stm.executeQuery(strQry);
            
            if (rst.next())
            {
               strRes = rst.getString("st_autfacele") == null? "" :rst.getString("st_autfacele");
            }
            
        }
        
        catch(Exception ex)
        {
            ex.printStackTrace();
            strRes = "ERROR";
        }
        
        return strRes;    
    } //Metodo getEstadoDocEle()
}