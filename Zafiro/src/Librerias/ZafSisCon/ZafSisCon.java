/*
 * ZafSisCon.java   
 * 
 * Created on 13 de diciembre de 2005, 10:05 AM
 * v0.7
 */

package Librerias.ZafSisCon;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
/**
 *
 * @author  Eddye Lino
 */
public class ZafSisCon
{
    //Variables generales.
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL, strAux;
    private Vector vecDat, vecReg;
    //Variables de la clase.
    private int intCodMaeItm;           //Código maestro del Item.
    private double dblStkCon;           //Stock consolidado.
    private int INT_EMP_GRUPO;
    
    
    /** Crea una nueva instancia de la clase ZafSisCon */
    public ZafSisCon(ZafParSis obj)
    {
        //Inicializar objetos.
        objParSis=obj;
        INT_EMP_GRUPO = objParSis.getCodigoEmpresaGrupo();
        objUti=new ZafUtil();
        vecDat=new Vector();
    }
    
    /**
     * Esta función obtiene el stock consolidado del item. El stock consolidado
     * se lo toma de la tabla "tbm_invMae".
     * @param codigoEmpresa El código de la empresa.
     * @param codigoItem El código del item.
     * @return El stock consolidado del item.
     * Nota.- El stock no se lo calcula. Sólo se lo toma de la tabla "tbm_invMae".
     */
    public double getStockConsolidado(int codigoEmpresa, int codigoItem)
    {
        double dblRes=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
//                strSQL+="SELECT a1.nd_stkAct";
//                strSQL+=" FROM tbm_invMae As a1";
//                strSQL+=" INNER JOIN tbm_equInv AS a2 ON (a1.co_itmMae=a2.co_itmMae)";
//                strSQL+=" WHERE a2.co_emp=" + codigoEmpresa;
//                strSQL+=" AND a2.co_itm=" + codigoItem;
                strSQL =" Select nd_stkact From tbm_inv " +
                " Where co_emp ="+INT_EMP_GRUPO+" and co_itm = " +
                " (Select co_itm From tbm_equinv " +
                " Where co_emp="+INT_EMP_GRUPO+" and co_itmmae =" +
                " (Select co_itmmae from tbm_equinv where co_emp ="+ codigoEmpresa + " and co_itm="+ codigoItem+"))";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblRes=rst.getDouble("nd_stkAct");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return dblRes;
    }
    
    /**  
     * Esta función obtiene el stock consolidado del item. El stock consolidado
     * se lo toma de la tabla "tbm_invMae".
     * @param codigoEmpresa El código de la empresa.
     * @param codigoItem El código del item.
     * @return El stock consolidado del item.
     * Nota.- El stock no se lo calcula. Sólo se lo toma de la tabla "tbm_invMae".
     */
    public double getValorConsolidado(int codigoEmpresa, int codigoItem)
    {
        double dblRes=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
//                strSQL="";
//                strSQL+="SELECT SUM(nd_can*nd_cosUni*(1-nd_porDes/100)) AS nd_valExi";
//                strSQL+=" FROM tbm_detMovInv";
//                strSQL+=" WHERE co_emp=" + codigoEmpresa;
//                strSQL+=" AND co_itm=" + codigoItem;
                strSQL="";
                strSQL+=" SELECT SUM(a2.nd_can*a2.nd_cosUni*(1-a2.nd_porDes/100)) AS nd_valExi";
                strSQL+=" FROM tbm_cabMovInv AS a1";
                strSQL+=" INNER JOIN tbm_detMovInv AS a2 ON (a1.co_emp=a2.co_emp AND a1.co_loc=a2.co_loc AND a1.co_tipDoc=a2.co_tipDoc AND a1.co_doc=a2.co_doc)";
                strSQL+=" WHERE a1.co_emp=" + codigoEmpresa;
                strSQL+=" AND a2.co_itm=" + codigoItem;
                strSQL+=" AND a1.st_reg IN ('A','R','C','F')";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblRes=rst.getDouble("nd_valExi");
                }
               // System.out.println("nd_valExi=" + dblRes);
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return dblRes;
    }
    
    /**
     * Esta función obtiene el costo unitario consolidado del item. El costo consolidado
     * se lo toma de la tabla "tbm_invMae".
     * @param codigoEmpresa El código de la empresa.
     * @param codigoItem El código del item.
     * @return El costo unitario consolidado del item.
     * Nota.- El costo no se lo calcula. Sólo se lo toma de la tabla "tbm_invMae".
     */
    public double getCostoConsolidado(int codigoEmpresa, int codigoItem)
    {
        double dblRes=0;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
               
                strSQL = " Select nd_cosuni " +
                         " From tbm_inv where co_emp ="+ INT_EMP_GRUPO +"  and co_itm = " +
                         " (Select co_itm from tbm_equinv " +
                         "  where co_emp="+ INT_EMP_GRUPO +" and co_itmmae =(Select co_itmmae from tbm_equinv where co_emp ="+ codigoEmpresa +" and co_itm="+ codigoItem +"))";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblRes=rst.getDouble("nd_cosUni");
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return dblRes;
    }
    
    /**
     * 
     * @param conIns
     * @param codigoEmpresa
     * @param codigoItem
     * @return 
     */
    public double getCostoConsolidado2(java.sql.Connection conIns, int codigoEmpresa, int codigoItem)
    {
        double dblRes=0;
        try
        {
            if (conIns!=null)
            {
                stm=conIns.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
               
                strSQL = " Select nd_cosuni " +
                         " From tbm_inv where co_emp ="+ INT_EMP_GRUPO +"  and co_itm = " +
                         " (Select co_itm from tbm_equinv " +
                         "  where co_emp="+ INT_EMP_GRUPO +" and co_itmmae =(Select co_itmmae from tbm_equinv where co_emp ="+ codigoEmpresa +" and co_itm="+ codigoItem +"))";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblRes=rst.getDouble("nd_cosUni");
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        return dblRes;
    }
    
    /**
     * Esta función determina si existe el stock suficiente de un item en las
     * bodegas asociadas. Por ejemplo, si se desea vender 10 plumnas y hay 4 en
     * la bodega 2 de Tuval y 8 en la bodega 1 de Nositol la función devolverá
     * verdadero. Caso contrario devolverá falso (Se asume la bodega 2 de Tuval
     * y 1 de Nositol están asociadas a la empresa de la que se desea hacer la venta).
     * @param empresa El código de la empresa.
     * @param local El código del local. Es el que determina las bodegas asociadas.
     * @param item El código del item.
     * @param cantidad La cantidad que se desea evaluar si existe en las bodegas asociadas.
     * @return true: Si existe el stock para la venta.
     * <BR>false: En el caso contrario.
     */
    public boolean existeStockItem_nueva_bod(int intCodBod, int empresa, int local, int item, int intValAtk, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                
                     strSQL = "select nd_stkact from tbm_invBod where co_Emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                rst=stm.executeQuery(strSQL);
               if(intValAtk!=0){
                if (rst.next())
                {
                   // intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
               }else  return true;
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }  
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param con
     * @param con_remota
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param intValAtk
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva_bod_solo_OC(java.sql.Connection con, java.sql.Connection con_remota, int intCodBod, int empresa, int local, int item, int intValAtk, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                
                if (con_remota!=null) stm=con_remota.createStatement();
                   else stm=con.createStatement();
                  
                strSQL = "select nd_stkact from tbm_invBod where co_Emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                rst=stm.executeQuery(strSQL);
               if(intValAtk!=0){
                if (rst.next())
                {
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
               }else  return true;
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }}
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param intValAtk
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva_2(int intCodBod, int empresa, int local, int item,  int intValAtk,  double cantidad )
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL = "select nd_stkact from tbm_invbod where  co_emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                rst=stm.executeQuery(strSQL);
                if(intValAtk!=0){
                 if (rst.next())
                 {
                     dblStkCon=rst.getDouble("nd_stkAct");
                     System.out.println("==> "+ dblStkCon +" >= "+ cantidad  + " -------> " + intValAtk);
                     if (dblStkCon >= cantidad)
                        return true;
                 }
                }else  return true;
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param con
     * @param con_remota
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param intValAtk
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva_2_solo_OC(java.sql.Connection con, java.sql.Connection con_remota, int intCodBod, int empresa, int local, int item,  int intValAtk,  double cantidad )
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
             
                if(con_remota!=null) stm=con_remota.createStatement();
                  else stm=con.createStatement();
                    
                strSQL = "select nd_stkact from tbm_invbod where  co_emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                rst=stm.executeQuery(strSQL);
                if(intValAtk!=0){
                 if (rst.next())
                 {
                     dblStkCon=rst.getDouble("nd_stkAct");
                     System.out.println("==> "+ dblStkCon +" >= "+ cantidad  + " -------> " + intValAtk);
                     if (dblStkCon >= cantidad)
                        return true;
                 }
                }else  return true;
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }}
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva(int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+INT_EMP_GRUPO+"  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp="+INT_EMP_GRUPO+" and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+")";
                
                //System.out.println("EXISTE STOCK>> ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   // intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param con_remota
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva_solo_OC(java.sql.Connection con, java.sql.Connection con_remota, int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {   
           if (con!=null)
            {
                if(con_remota!=null) stm=con_remota.createStatement();
                  else stm=con.createStatement();
                    
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+INT_EMP_GRUPO+"  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp="+INT_EMP_GRUPO+" and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+")";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   // intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItem_2(int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

               strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param con
     * @param con_remota
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItem_2_solo_FAC(java.sql.Connection con, java.sql.Connection con_remota, int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                if(con_remota!=null) stm=con_remota.createStatement();
                else stm=con.createStatement();
                      
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close(); 
                rst=null;
                stm=null;
        }}
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param con
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItemGrupo_Factura(java.sql.Connection con, int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

               strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
          
                   
          //    System.out.println("EXISTE STOCK FACTURA ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param con
     * @param con_remota
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItemGrupo_Factura_solo_FAC(java.sql.Connection con,  java.sql.Connection con_remota, int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {
                if(con_remota!=null) stm=con_remota.createStatement();
                  else  stm=con.createStatement();
                     
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
                  
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  //System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItemEmpresa_Factura(java.sql.Connection con, int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

               strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+empresa+" " +
                        " and co_itm = "+item+" and co_bod = "+intCodBod; 
                   
         //     System.out.println("EXISTE STOCK FACTURA ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param con_remota
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @return 
     */
    public boolean existeStockItemEmpresa_Factura_solo_FAC(java.sql.Connection con, java.sql.Connection con_remota, int empresa, int local, int item, double cantidad, double intCodBod)
    {
        boolean blnRes=false;
        try
        {       
               if(con_remota!=null) stm=con_remota.createStatement();
                else stm=con.createStatement();
               
               strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+empresa+" " +
                        " and co_itm = "+item+" and co_bod = "+intCodBod; 
                   
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                  dblStkCon=rst.getDouble("nd_stkAct");
                  System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_bod(int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                            
             /*   strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
               
              **/
                strSQL = "select nd_stkact from tbm_invBod where co_Emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                
            //   System.out.println("EXISTE STOCK ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    //intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param conRemota
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_bod_solo_OC(java.sql.Connection con, java.sql.Connection conRemota, int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
           // if (conRemota!=null)
           // {
               if (conRemota!=null) stm=conRemota.createStatement();
                 else  stm=con.createStatement();
                       
                //Armar la sentencia SQL.
                strSQL = "select nd_stkact from tbm_invBod where co_Emp="+empresa+" and co_bod="+intCodBod+" and co_itm="+item;
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
           // }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem(int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            con=DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
               /*
                strSQL = " Select co_itmmae,nd_stkact " +
                         " From tbm_invbod as a1 INNER JOIN tbm_equinv as a2 on (a1.co_emp = a2.co_emp and a1.co_itm = a2.co_itm)" +
                         " Where a1.co_emp="+INT_EMP_GRUPO+" and a1.co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp="+INT_EMP_GRUPO+" and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp =" + empresa +" and co_itm=" + item +") )" +
                         " and co_bod = (select  co_bodper from tbr_bodemp where co_emp=" + empresa +" and co_loc = " + local +" and co_empper="+INT_EMP_GRUPO+" and st_reg='P')";
                */
                
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
                
               // System.out.println("EXISTE STOCK ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    //intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                con.close();
                rst=null;
                stm=null;
                con=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param con_remota
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_solo_OC(java.sql.Connection con, java.sql.Connection con_remota, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            //if (con_remota!=null)
           // {
               
              if(con_remota!=null) stm=con_remota.createStatement();
                 else  stm=con.createStatement();
                
                //Armar la sentencia SQL.
                strSQL="";
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp=(select co_empper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+") and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select  co_bodper from tbr_bodemp where co_emp="+empresa+" and co_loc="+local+")";
                
               // System.out.println("EXISTE STOCK ==>>  "+ strSQL);
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
           // }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función obtiene el código maestro del item.
     * Nota.- Esta función no se conecta a la base de datos para obtener el código maestro.
     * Se aprovecha de la función "existeStockItem" para obtener el código. Es decir,
     * que para utilizar ésta función primero se debe haber llamado a la función
     * "existeStockItem".
     * @return El código maestro del item.
     */
    public int getCodigoMaestroItem()
    {
        return intCodMaeItm;
    }
    
    /**
     * Esta función obtiene el stock consolidado del item.
     * Nota.- Esta función no se conecta a la base de datos para obtener el stock.
     * Se aprovecha de la función "existeStockItem" para obtener el stock. Es decir,
     * que para utilizar ésta función primero se debe haber llamado a la función
     * "existeStockItem".
     * @return El stock consolidado del item.
     */
    public double getStockConsolidado()
    {
        return dblStkCon;
    }
    
    /**
     * Esta función actualiza el stock consolidado y el costo del item. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param item El código del item.
     * @param cantidad La cantidad que se desea sumar/restar al inventario consolidado.
     * @return true: Si se pudo actualizar el inventario.
     * <BR>false: En el caso contrario. 
     */
    public boolean actualizarInventario(java.sql.Connection conexion, int empresa,int local, int item, double cantidad, int intCodBod)
    {
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad +
                        " where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
               
                
                //System.out.println("-1--===> "+ strSQL);
                
                
                stm.executeUpdate(strSQL);

                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+")";
                
                  //"(select  co_bodper from tbr_bodemp where co_emp="+ empresa +" and co_loc = "+ local +" and co_empper=" + INT_EMP_GRUPO + " and st_reg='P')";
                     
                
                  
               //  System.out.println("-2--===> "+ strSQL);
                 
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param conexion
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @param strEstFisBod
     * @param strMerIngEgr
     * @param strTipIngEgr
     * @return 
     */
    public boolean actualizarInventario_2(java.sql.Connection conexion, int empresa,int local, int item, double cantidad,int intCodBod, String strEstFisBod, String strMerIngEgr,  String strTipIngEgr )
    {
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad +
                        " where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
               // System.out.println("GR1 --===> "+ strSQL);
                
                
              String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(cantidad > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+cantidad+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(cantidad > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+cantidad+") ";
                   } 
                 }} 
           
              
                stm.executeUpdate(strSQL);
                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " "+strAux+" "+
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+") ";
                        
                
              //  System.out.println(" GR 2 --===> "+ strSQL);
                 
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param conexion
     * @param con_remota
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @param strEstFisBod
     * @param strMerIngEgr
     * @param strTipIngEgr
     * @return 
     */
    public boolean actualizarInventario_2_solo_OC(java.sql.Connection conexion, java.sql.Connection con_remota, int empresa,int local, int item, double cantidad,int intCodBod, String strEstFisBod, String strMerIngEgr, String strTipIngEgr  )
    {
        boolean blnRes=true;
        try
        { 
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                java.sql.Statement stm_remota; 
                
                //Armar la sentencia SQL.
                strSQL =" Update tbm_inv " +
                        " SET  st_regrep='M',  nd_stkact = nd_stkact + " + cantidad +
                        "  where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
               // System.out.println("GR1 --===> "+ strSQL);
                  
                stm.executeUpdate(strSQL);
                if(con_remota!=null){
                  stm_remota = con_remota.createStatement();  
                  stm_remota.executeUpdate(strSQL);  // REMOTAMENTE
                  stm_remota.close();
                }
                
                String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(cantidad > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+cantidad+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(cantidad > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+cantidad+") ";
                   } 
                 }} 
                
                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " "+strAux+" "+
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+") ";
                        
                
              //  System.out.println(" GR 2 --===> "+ strSQL);
                 
                stm.executeUpdate(strSQL);
                ///stm_remota.executeUpdate(strSQL);  // REMOTAMENTE
                 if(con_remota!=null){
                  stm_remota = con_remota.createStatement();  
                  stm_remota.executeUpdate(strSQL);  // REMOTAMENTE
                  stm_remota.close();
                }
                
                stm_remota=null;
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * 
     * @param conexion
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @param intCodBod
     * @param strEstFisBod
     * @param strMerIngEgr
     * @param strTipIngEgr
     * @return 
     */
    public boolean actualizarInventario_nuevo(java.sql.Connection conexion, int empresa,int local, int item, double cantidad, int intCodBod, String strEstFisBod, String strMerIngEgr, String strTipIngEgr)
    {
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                
                 String strAux="";
                if(strEstFisBod.equals("N")){
                 if(strMerIngEgr.equals("S")){
                   if(strTipIngEgr.equals("I")){ 
                       if(cantidad > 0) strAux=" ,nd_caningbod=nd_caningbod+abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_caningbod=nd_caningbod-abs("+cantidad+") ";
                   }
                   if(strTipIngEgr.equals("E")){
                       if(cantidad > 0) strAux=" ,nd_canegrbod=nd_canegrbod-abs("+cantidad+") ";
                       if(cantidad < 0) strAux=" ,nd_canegrbod=nd_canegrbod+abs("+cantidad+") ";
                   } 
                 }} 
                 
                 
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad +
                        " where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
          
                //System.out.println("-1--===> "+ strSQL);
                
                stm.executeUpdate(strSQL);

                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " "+strAux+" "+
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+ empresa +" and co_bod="+intCodBod+")";
                      
                
                 System.out.println("-2--===> "+ strSQL);
                 
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
     * Esta función actualiza el stock consolidado y el costo del item. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param documento El código del documento.
     * @param item El código del item.
     * @param cantidad La cantidad que se desea sumar/restar al inventario consolidado.
     * @param cantidadReal La cantidad que tiene el documento. Este campo es igual al campo "cantidad" cuando se inserta
     * un registro y es diferente cuando se hace una modificación. Por ejemplo: se puede haber grabado una O/C con 30 plumas
     * y se modifica la O/C dejandola con 25 plumas. En este caso "cantidad=-5" y "cantidadReal=30".
     * @return true: Si se pudo actualizar el inventario.
     * <BR>false: En el caso contrario.
     */
    public boolean actualizarInventario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int documento, int item, double cantidad, double cantidadReal, double costo, double descuento, boolean costear)
    {
        double dblStkCon, dblValExi;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                dblStkCon=getStockConsolidado(empresa, item);
                dblValExi=getValorConsolidado(empresa, item);
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad ;
                if (costear)
                    if ((dblStkCon + cantidadReal)==0)
                        strSQL+=", nd_cosUni=0";
                    else
                        strSQL+=", nd_cosUni=" + (dblValExi+cantidadReal*costo*(1-descuento/100)) + "/(nd_stkAct+" + cantidadReal + ")";

                        
                strSQL +=" where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
                stm.executeUpdate(strSQL);

              //  System.out.println("11 ==> "+ strSQL);
                
                
                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         "(select  co_bodper from tbr_bodemp where co_emp="+ empresa +" and co_loc = "+ local +" and co_empper=" + INT_EMP_GRUPO + " and st_reg='P')";
                
                
               // System.out.println("22 ==> "+ strSQL);
                
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param conexion
     * @param empresa
     * @param local
     * @param tipoDocumento
     * @param documento
     * @param item
     * @param cantidad
     * @param cantidadReal
     * @param costo
     * @param descuento
     * @param costear
     * @param intCodBod
     * @return 
     */
    public boolean actualizarInventario_2(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int documento, int item, double cantidad, double cantidadReal, double costo, double descuento, boolean costear, int intCodBod)
    {
        double dblStkCon, dblValExi;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                dblStkCon=getStockConsolidado(empresa, item);
                dblValExi=getValorConsolidado(empresa, item);
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad ;
                if (costear)
                    if ((dblStkCon + cantidadReal)==0)
                        strSQL+=", nd_cosUni=0";
                    else
                        strSQL+=", nd_cosUni=" + (dblValExi+cantidadReal*costo*(1-descuento/100)) + "/(nd_stkAct+" + cantidadReal + ")";

                        
                strSQL +=" where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
                stm.executeUpdate(strSQL);

              // System.out.println("11 ==> "+ strSQL);
                
                
                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" +
                         " (select  co_bodgrp from tbr_bodempbodgrp where co_emp="+ empresa +" and co_bod = "+intCodBod+")";
                       
                       
                
              // System.out.println("22 ==> "+ strSQL);
                
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param conexion
     * @param empresa
     * @param local
     * @param tipoDocumento
     * @param documento
     * @param item
     * @param cantidad
     * @param cantidadReal
     * @param costo
     * @param descuento
     * @param costear
     * @param intCodBod
     * @return 
     */
    public boolean actualizarInventario_nuevo(java.sql.Connection conexion, int empresa, int local, int tipoDocumento, int documento, int item, double cantidad, double cantidadReal, double costo, double descuento, boolean costear,int intCodBod)
    {
        double dblStkCon, dblValExi;
        boolean blnRes=true;
        try
        {
            if (conexion!=null)
            {
                dblStkCon=getStockConsolidado(empresa, item);
                dblValExi=getValorConsolidado(empresa, item);
                stm=conexion.createStatement();
                //Armar la sentencia SQL.
                strSQL="";

                strSQL =" Update tbm_inv " +
                        " set nd_stkact = nd_stkact + " + cantidad ;
                if (costear)
                    if ((dblStkCon + cantidadReal)==0)
                        strSQL+=", nd_cosUni=0";
                    else
                        strSQL+=", nd_cosUni=" + (dblValExi+cantidadReal*costo*(1-descuento/100)) + "/(nd_stkAct+" + cantidadReal + ")";

                        
                strSQL +=" where co_emp=" + INT_EMP_GRUPO + " and co_itm = " +
                        " (Select co_itm from tbm_equinv where co_emp= "+ INT_EMP_GRUPO+" and co_itmmae IN " +
                        " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )";
                stm.executeUpdate(strSQL);

               // System.out.println("11 ==> "+ strSQL);
                
                
                strSQL = " Update tbm_invbod " +
                         " set nd_stkact = nd_stkact + " + cantidad +
                         " where co_emp=" + INT_EMP_GRUPO + " and co_itm =" +
                         " (Select co_itm from tbm_equinv where co_emp=" + INT_EMP_GRUPO + " and co_itmmae IN " +
                         " (Select co_itmmae from tbm_equinv where co_emp ="+ empresa +" and co_itm="+ item +") )" +
                         " and co_bod =" + 
                         "(select co_bodgrp from  tbr_bodempbodgrp where co_emp="+ empresa +" and co_bod="+intCodBod+")";
                         //"(select  co_bodper from tbr_bodemp where co_emp="+ empresa +" and co_loc = "+ local +" and co_empper=" + INT_EMP_GRUPO + " and st_reg='P')";
                
                
                //System.out.println("22 ==> "+ strSQL);
                
                stm.executeUpdate(strSQL);
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
    /**
       * 
       * @param Conn
       * @param intCodBod
       * @param empresa
       * @param local
       * @param item
       * @param cantidad
       * @return 
       */
    public boolean existeStockItem_nueva_varios(java.sql.Connection Conn ,int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            if (Conn!=null)
            {
                stm=Conn.createStatement();
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+INT_EMP_GRUPO+"  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp="+INT_EMP_GRUPO+" and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+")";
                
              // System.out.println("EXISTE STOCK>> ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   // intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("Aqui ==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }

    /**
     * 
     * @param con
     * @param intCodBod
     * @param empresa
     * @param local
     * @param item
     * @param cantidad
     * @return 
     */
    public boolean existeStockItem_nueva2(java.sql.Connection con, int intCodBod, int empresa, int local, int item, double cantidad)
    {
        boolean blnRes=false;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                
                strSQL = "select  nd_stkact  from tbm_invbod   where co_emp="+INT_EMP_GRUPO+"  and co_itm ="+
                         " (Select co_itm from tbm_equinv where co_emp="+INT_EMP_GRUPO+" and co_itmmae IN "+
                         " (Select co_itmmae from tbm_equinv where co_emp ="+empresa+" and co_itm="+item+") ) "+
                         " and co_bod =  "+
                         " (select co_bodgrp from  tbr_bodempbodgrp where co_emp="+empresa+" and co_bod="+intCodBod+")";
                
                //System.out.println("EXISTE STOCK>> ==>>  "+ strSQL);
                
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                   // intCodMaeItm=rst.getInt("co_itmMae");
                    dblStkCon=rst.getDouble("nd_stkAct");
                    System.out.println("==> "+ dblStkCon +" >= "+ cantidad );
                    if (dblStkCon >= cantidad)
                        return true;
                }
                rst.close();
                stm.close();
                rst=null;
                stm=null;
        }}
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return blnRes;
    }
    
}
