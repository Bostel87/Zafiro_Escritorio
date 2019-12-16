/*
 * ZafLocPrgUsr.java
 *
 * Created on 13 de Noviembre del 2015, 09:47
 */
package Librerias.ZafUtil;

import Librerias.ZafParSis.ZafParSis;
import java.util.Vector;

/**
 * Esta clase tiene el objetivo de cargar los locales permitidos dentro de un programa.
 * Admin: Tiene acceso a todos los locales existentes (tbm_loc). 
 * Otros Usuarios: Los locales que tenga configurado en un determinado programa. (tbr_locPrgUsr)
 *
 * @author sistemas 9
 */
public class ZafLocPrgUsr
{
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private String strVer = "v0.2";
  
    public ZafLocPrgUsr(ZafParSis obj)
    {
        //Inicializar objetos.
        objParSis=obj;
        //System.out.println("ZafLocPrgUsr "+strVer);
    }

    /**
     * Valida si el usuario tiene acceso a locales dentro del programa.
     * especificado.
     *
     * @return true: Usuario tiene locales permitidos en el programa
     * <BR> false: caso contrario.
     */
    public boolean validaLocUsr() 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL = "";
        boolean blnRes = false;
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = cargarLocUsr(1);
                rst = stm.executeQuery(strSQL);
                while (rst.next()) 
                {
                    blnRes = true;
                }
                //System.out.println("validaLocUsr: "+strSQL);
                rst.close();
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {   blnRes = false;    } 
        catch (Exception e) {    blnRes = false;    }
        
        return blnRes;
    }


    /**
     * Función que genera query de consulta de locales existentes.
     * @param intTipo El tipo de consulta de locales. Puede recibir uno de los siguientes valores:
     * <UL>
     * <LI> 1: Genera Query solamente con código local.
     * <LI> 2: Genera Query con todos los datos del local (Código, Nombre, Estado de Registro ).
     * </UL>
     * @return string: Consulta Locales Existentes de acuerdo al intTipo indicado.
     * <BR> false: En el caso contrario.
     */
    public String cargarLoc(int intTipo) 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += " SELECT a.co_loc ";
                if (intTipo == 2) {  strSQL += ", a.tx_nom, a.st_reg, a.co_Emp, a1.tx_nom as Emp ";   }
                strSQL += " FROM tbm_loc as a ";
                strSQL += " INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp) ";
                strSQL += " WHERE a.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " ORDER BY a.co_emp, a.co_loc ";

                //System.out.println("cargarLoc: " + strSQL);
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e); } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);    }
        
        return strSQL;
    }


    /**
     * Función que genera query de consulta de los locales a los que tiene
     * acceso un usuario en un determinado programa.
     *
     * @param intTipo El tipo de consulta de locales. Puede recibir uno de los
     * siguientes valores:
     * <UL>
     * <LI> 1: Genera Query solamente con código local.
     * <LI> 2: Genera Query con todos los datos del local (Código, Nombre, Estado de Registro ).
     * </UL>
     * @return string: Consulta Locales Existentes de acuerdo al intTipo
     * indicado.
     * <BR> false: En el caso contrario.
     */
    public String cargarLocUsr(int intTipo) 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                stm = con.createStatement();
                strSQL += " SELECT a.co_loc ";
                if (intTipo == 2) {  strSQL += " , a.tx_nom, a.st_reg, a.co_Emp, a1.tx_nom as Emp ";    }
                strSQL += " FROM tbm_loc as a  ";
                strSQL += " INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp) ";
                strSQL += " INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel) ";
                strSQL += " WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " ORDER BY a.co_emp, a.co_loc  ";

                //System.out.println("cargarLocUsr: " + strSQL);
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);  } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);  }
        
        return strSQL;
    }

     /**
     * Función que genera query de consulta de los locales a los que tiene
     * acceso un usuario en un determinado programa de acuerdo a la empresa relacionada.
     * Por lo general esta función se utiliza para la consulta de los locales en el Grupo.
     * @param intTipo El tipo de consulta de locales. Puede recibir uno de los siguientes valores:
     * <UL>
     * <LI> 1: Genera Query solamente con código local.
     * <LI> 2: Genera Query con todos los datos del local (Código, Nombre, Estado de Registro ).
     * </UL>
     * @param intEmpRel Empresa relacionada seleccionada por el usuario, de acuerdo a esta empresa se consultan los locales.
     * @return string: Consulta Locales Existentes de acuerdo al intTipo
     * indicado.
     * <BR> false: En el caso contrario.
     */
    public String cargarLocUsrEmpRel(int intTipo, int intEmpRel) 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null)
            {
                stm = con.createStatement();
                strSQL += " SELECT a.co_loc ";
                if (intTipo == 2) {  strSQL += " , a.tx_nom, a.st_reg, a.co_Emp, a1.tx_nom as Emp ";    }
                strSQL += " FROM tbm_loc as a  ";
                strSQL += " INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp) ";
                strSQL += " INNER JOIN tbr_locPrgUsr as a2 ON (a.co_Emp=a2.co_empRel AND a.co_loc=a2.co_locRel) ";
                strSQL += " WHERE a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL += " AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL += " AND a2.co_mnu=" + objParSis.getCodigoMenu();
                strSQL += " AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL += " AND a2.co_empRel=" + intEmpRel;
                strSQL += " ORDER BY  a2.co_empRel, a2.co_locRel ";

                //System.out.println("cargarLocUsrEmpRel: " + strSQL);
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);  } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);  }
        
        return strSQL;
    }
    
    /**
     * Función que genera query de consulta de locales existentes.
     * Se utiliza para consultar los locales de todas las empresas distintas a Grupo.
     * @param intTipo El tipo de consulta de locales. Puede recibir uno de los siguientes valores:
     * <UL>
     * <LI> 1: Genera Query solamente con código local.
     * <LI> 2: Genera Query con todos los datos del local (Código, Nombre, Estado de Registro ).
     * </UL>
     * @param intFilEmp Indica si existe filtro por empresa o indica el código de la empresa seleccionada por el usuario. Puede recibir los siguientes valores:
     * <UL>
     * <LI> 0: Indica que NO existe filtro de empresa seleccionada y por tanto genera Query con todos los locales existentes en las empresas.
     * <LI> Otros valores: Indican que SI existe filtro de empresa seleccionada y por tanto genera Query de acuerdo al código de la empresa intFilEmp.
     * </UL>
     * @return string: Consulta Locales Existentes de acuerdo al intTipo indicado.
     * <BR> false: En el caso contrario.
     */
    public String cargarLocGrp(int intTipo, int intFilEmp) 
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        String strSQL = "";
        try 
        {
            con = java.sql.DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con != null) 
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL += " SELECT a.co_loc ";
                if (intTipo == 2) {  strSQL += " , a.tx_nom, a.st_reg, a.co_emp, a1.tx_nom as Emp ";   }
                strSQL += " FROM tbm_loc as a ";
                strSQL += " INNER JOIN tbm_emp as a1 ON (a.co_Emp=a1.co_emp) ";
                strSQL += " WHERE a.co_emp<>" + objParSis.getCodigoEmpresaGrupo();
                strSQL += " AND a1.st_reg!='I'";
                if (intFilEmp != 0) {  strSQL += " AND a.co_emp="+intFilEmp;   }
                strSQL += " ORDER BY a.co_emp, a.co_loc ";

                //System.out.println("cargarLocGrp: " + strSQL);
                stm.close();
                con.close();
            }
        } 
        catch (java.sql.SQLException e) {    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e); } 
        catch (Exception e) {  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);    }
        
        return strSQL;
    }
}
