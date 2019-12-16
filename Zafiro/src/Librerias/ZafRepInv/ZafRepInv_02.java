/*
 * ZafRepInv_02.java
 *
 * Created on 6 de julio de 2005, 11:57
 */

package Librerias.ZafRepInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author  jsalazar
 */

public class ZafRepInv_02 {
    private ZafUtil objUti;
    private int intOriCodReg; //Numero del registro de origen
    private int intOriCodBod; //Codigo de la bodega de origen
    private int intOriCodItm; //Codigo del item de origen
    private String strOriCodAlt;
    private String strOriNomItm;        
    private String strOriUni;
    private double dblOriStkCan; //Cantidad solicitada de origen
    private int intDatCotCom; // Numero del doc. que genera la reposicion
    private int intDatCodCot; // Numero del documento a generar.
    private int intDatCodEmp;
    private int intDatCodLoc;
    private int intDatCodBod;
    private String strDatCodAlt;
    private String strDatNomItm;
    private String strDatUni;
    private int intDatCodItm;
    private double dblDatStkCan;
    private double dblDatDscPre;
    
    //Conexion
    private Connection con;
    private Statement stm;
    private ResultSet rst;   
    private String strSQL; 

    
    /** Creates a new instance of ZafRepInv_02 */
    public ZafRepInv_02() {
        objUti = new ZafUtil();
        intOriCodReg = 0; 
        intOriCodItm = 0;
        intOriCodBod = 0;
        intDatCotCom = 0;
        intDatCodCot = 0;
        intDatCodEmp = 0; 
        intDatCodLoc = 0;
        intDatCodBod = 0;
        intDatCodItm = 0;
        strDatCodAlt = "";
        strDatNomItm = "";
        strOriCodAlt = "";
        strOriNomItm = "";        
        dblDatStkCan = 0;
        dblDatDscPre = 0;
    }
    public void setOriCodReg(int intparCod){                   
        intOriCodReg = intparCod;
    }
    public void setOriCodItm(int intparCod){                   
        intOriCodItm = intparCod;
    }
    public void setOriCodAlt(String strparCod){                   
        strOriCodAlt = strparCod;
    }
    public void setOriNomItm(String strparNom){                   
        strOriNomItm = strparNom;
    }
    public void setOriUniMed(String strparNom){                   
        strOriUni = strparNom;
    }
    public void setOriCodBod(int intparCod){                   
        intOriCodBod = intparCod;
    }
    public void setOriStkCan(double dblparVal){                   
        dblOriStkCan = dblparVal;
    }
    public void setDatCotCom(int intparCod){                   
        intDatCotCom = intparCod;
    }
    public void setDatCodCot(int intparCod){                   
        intDatCodCot = intparCod;
    }
    public void setDatCodEmp(int intparCod){                   
        intDatCodEmp = intparCod;
    }
    public void setDatCodLoc(int intparCod){                   
        intDatCodLoc = intparCod;
    }
    public void setDatCodBod(int intparCod){                   
        intDatCodBod = intparCod;
    }
    public void setDatCodItm(int intparCod){                   
        intDatCodItm = intparCod;
    }
    public void setDatCodAlt(String strparCod){                   
        strDatCodAlt = strparCod;
    }
    public void setDatNomItm(String strparNom){                   
        strDatNomItm = strparNom;
    }
    public void setDatUniMed(String strparNom){                   
        strDatUni = strparNom;
    }
    public void setDatStkCan(double dblparVal){                   
        dblDatStkCan = dblparVal;
    }
    public void setDatDscPre(double dblparVal){                   
        dblDatDscPre = dblparVal;
    }
    public int getOriCodReg( ){                   
        return intOriCodReg;
    }
    public int getOriCodItm( ){                   
        return intOriCodItm;
    }
    public String getOriCodAlt( ){                   
        return strOriCodAlt;
    }
    public String getOriNomItm( ){                   
        return strOriNomItm;
    }
    public String getOriUniMed( ){                   
        return strOriUni;
    }
    public int getOriCodBod( ){                   
        return intOriCodBod;
    }
    public double getOriStkCan( ){                   
        return dblOriStkCan;
    }
    public int getDatCotCom( ){                   
        return intDatCotCom;
    }
    public int getDatCodCot( ){                   
        return intDatCodCot;
    }
    public int getDatCodEmp( ){                   
        return intDatCodEmp;
    }
    public int getDatCodLoc( ){                   
        return intDatCodLoc;
    }
    public int getDatCodBod( ){                   
        return intDatCodBod;
    }
    public String getDatCodAlt( ){                   
        return strDatCodAlt;
    }
    public String getDatNomItm( ){                   
        return strDatNomItm;
    }
    public String getDatUniMed( ){                   
        return strDatUni;
    }    
    public int getDatCodItm( ){                   
        return intDatCodItm;
    }
    public double getDatStkCan( ){                   
        return dblDatStkCan;
    }
    public double getDatDscPre( ){                   
        return dblDatDscPre;
    }
    public void AsignarDetItm(ZafParSis objZafParSis)
    {
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                
            if (con!=null)
            {                
                stm=con.createStatement();
                strSQL = "";
                strSQL = " Select tx_codalt, tx_nomitm, tx_descor From tbm_inv as inv left outer join tbm_var on (co_uni = co_reg) Where inv.st_reg='A' and co_emp =" + objZafParSis.getCodigoEmpresa() + " and co_itm = " + intOriCodItm ;
                //System.out.println ("SQL:"+strSQL);
                rst = stm.executeQuery(strSQL);
                while (rst.next())
                {
                    strDatCodAlt = rst.getString("tx_codalt");
                    strDatNomItm = rst.getString("tx_nomitm");
                    strDatUni    = rst.getString("tx_descor");
                }
                rst.close();
                strSQL = "";
                strSQL = " Select tx_codalt, tx_nomitm, tx_descor From tbm_inv as inv left outer join tbm_var on (co_uni = co_reg) Where inv.st_reg='A' and co_emp =" + intDatCodEmp + " and co_itm = " + intDatCodItm ;
                //System.out.println ("SQL:"+strSQL);
                rst = stm.executeQuery(strSQL);
                while (rst.next())
                {
                    strOriCodAlt = rst.getString("tx_codalt");
                    strOriNomItm = rst.getString("tx_nomitm");
                    strOriUni    = rst.getString("tx_descor");
                }
                rst.close();
                stm.close();
                con.close();
            }
        } catch (java.sql.SQLException e) {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)  {
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }                                
    }
}
