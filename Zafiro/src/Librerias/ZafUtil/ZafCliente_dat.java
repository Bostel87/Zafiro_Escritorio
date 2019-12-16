/*
 * Clientes_dat.java
 *
 * Created on 25 de noviembre de 2004, 16:47
 */
package Librerias.ZafUtil;

import Librerias.ZafParSis.ZafParSis;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Clase para tener informacion de un cliente <br>
 * Recibe como parametro el codigo del cliente y obtiene la informacion del mismo<br>
 * si el codigo no existe o hubo algun error se puede conprobar con el metodo <strong>isEmpty</strong><br>
 * que retorna true si la clase esta vacia y false si la clase obtuvo datos
 * @author IdiTrix
 */
public class ZafCliente_dat 
{
    //Variables
    private ZafParSis objParSis;
    private ZafUtil objUti;
    private Connection conCli;  //Coneccion a la base donde se encuentra el cliente
    private Statement stmCli;   //Statement para la cliente
    private ResultSet rstCli;   //Resultset que tendra los datos     
    private boolean isEmpty = true;  /* Contiene true si no se encuentra el cliente, para saber si retornar null o no. */

    //Datos del cliente
    private int intCodEmp;
    private int intCodCli = 0;
    private String strNomCli = "";
    private String strDirCli = "";
    private String strTelCli = "";
    private String strIdeCli  = "";
    private int    intCodCiuCli  = 0;
    private double dblMaxDsctoCli = 0;
    private char tipPer;
    private int intCodTipPerCli;
    private int intDiaGraCli;
    
        
    /**
     * Construye un nuevo objeto del tipo CLiente_dat <br>el cual contendra los datos de un cliente enviado como parametro
     * @param intCodigoCliente Codigo de cliente para el que se obtendran los datos
     */        
    public ZafCliente_dat(int intCodigoCliente, ZafParSis objParSis){
        this.objParSis = objParSis;
        intCodEmp = objParSis.getCodigoEmpresa();
        objUti =  new ZafUtil ();
        setCliente(intCodigoCliente);            
    }

    /**
     * Construye un nuevo objeto del tipo CLiente_dat <br>el cual contendra los datos de un cliente enviado como parametro
     */        
    public ZafCliente_dat(ZafParSis objParSis){
        this.objParSis = objParSis;
        intCodEmp = objParSis.getCodigoEmpresa();
        objUti =  new ZafUtil ();
    }
    
    /**
     * @autor: jsalazar
     */
    public ZafCliente_dat(int intCodigoEmpresa, int intCodigoCliente, ZafParSis objParSis){
        this.objParSis = objParSis;           
        intCodEmp = intCodigoEmpresa;
        objUti =  new ZafUtil ();
        setCliente(intCodEmp, intCodigoCliente);
    }
    
    public ZafCliente_dat(ZafParSis objParSis, String strCodigoCliente){
        this.objParSis = objParSis;
        intCodEmp = objParSis.getCodigoEmpresa();
        objUti =  new ZafUtil ();
        setCliente(intCodEmp, strCodigoCliente);
    }

    /**
     * Recupera y carga a la clase Cliente _dat, la informacion respectiva al<br>
     * al cliente enviado como parametro
     * @return <pre>
     * true  .- Si los datos del cliente fueron cargados con exito
     * false .- Si el codigo de cliente no se encontro o no se pudo cargar los datos
     * </pre>
     * @param cod_cli Codigo del cliente del cual se recuperaran los datos
     */        
    public boolean setCliente(int cod_cli)
    {
        intCodCli = cod_cli;
        isEmpty = true;

        try{
            conCli=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stmCli = conCli.createStatement();
            
            //Armar sentencia SQL: Obtiene datos de clientes.
            String strSql = "select * from tbm_cli where co_emp = " + objParSis.getCodigoEmpresa() + " and co_cli = " + intCodCli;
            rstCli = stmCli.executeQuery(strSql);
            if(rstCli.next()){
                strNomCli = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                strDirCli = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                strTelCli = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                strIdeCli = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                intCodCiuCli = rstCli.getInt("co_ciu");
                dblMaxDsctoCli  = rstCli.getDouble("nd_maxDes");
                tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                intCodTipPerCli = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                intDiaGraCli = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                isEmpty = false;
            }
            rstCli.close();
            stmCli.close();
            conCli.close();
        }
        catch(java.sql.SQLException Evt){
            System.out.println(Evt.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
        }
        catch(Exception Evt){
            System.out.println(Evt.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
        }
        return true;
    }
    
    /**
     * @autor: jsalazar
     */
    public boolean setCliente(int intCodigoEmpresa, int intCodigoCliente)
    {
        intCodCli = intCodigoCliente;
        isEmpty = true;
        try{
            conCli=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stmCli = conCli.createStatement();
            
            //Armar sentencia SQL: Obtiene datos de clientes.
            String strSql = "select * from tbm_cli where co_emp = " + intCodigoEmpresa + " and co_cli = " + intCodCli;
            rstCli = stmCli.executeQuery(strSql);
            if(rstCli.next()){
                strNomCli = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                strDirCli = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                strTelCli = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                strIdeCli = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                intCodCiuCli  = rstCli.getInt("co_ciu");
                dblMaxDsctoCli  = rstCli.getDouble("nd_maxDes");
                tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                intCodTipPerCli = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                intDiaGraCli = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                isEmpty = false;
            }
            rstCli.close();
            stmCli.close();
            conCli.close();
        }
        catch(java.sql.SQLException Evt){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
        }

        catch(Exception Evt){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
        }
        return true;
    }        
    
    public boolean setCliente(int intCodigoEmpresa, String strCodigoCliente)
    {
        isEmpty = true;
        try{
            conCli=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            stmCli = conCli.createStatement();

            //Armar sentencia SQL: Obtiene datos de clientes.
            String strSql = "select * from tbm_cli where co_emp = " + intCodigoEmpresa + " and co_cli = " + strCodigoCliente;
            System.out.println(strSql);
            rstCli = stmCli.executeQuery(strSql);
            if(rstCli.next()){
                strNomCli = ((rstCli.getString("tx_nom")==null)?"":rstCli.getString("tx_nom"));
                strDirCli = ((rstCli.getString("tx_dir")==null)?"":rstCli.getString("tx_dir"));
                strTelCli = ((rstCli.getString("tx_tel")==null)?"":rstCli.getString("tx_tel"));
                strIdeCli = ((rstCli.getString("tx_ide")==null)?"":rstCli.getString("tx_ide"));
                intCodCiuCli  = rstCli.getInt("co_ciu");
                dblMaxDsctoCli  = rstCli.getDouble("nd_maxDes");
                tipPer  = rstCli.getString("tx_tipPer").charAt(0);
                intCodTipPerCli = ((rstCli.getString("co_tipper")==null?0:rstCli.getInt("co_tipper")));
                intDiaGraCli = ((rstCli.getString("ne_diagra")==null?0:rstCli.getInt("ne_diagra")));
                isEmpty = false;
            }
            rstCli.close();
            stmCli.close();
            conCli.close();
       }
       catch(java.sql.SQLException Evt){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
       }

       catch(Exception Evt){
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
            return false;
       }
       return true;
    }    
    
    public boolean isEmpty (){
        return isEmpty;
    }
    public String getNombre (){
        return (isEmpty)?null:strNomCli;
    }
    public String getDireccion (){
        return (isEmpty)?null:strDirCli;
    }
    public String getTelefono (){
        return (isEmpty)?null:strTelCli;
    }
    public String getIdentificacion  (){
        return (isEmpty)?null:strIdeCli;
    }
    public int  getCodciudad  (){
        return (isEmpty)?-1:intCodCiuCli;
    }
    public double  getMaxdescuento(){
        return (isEmpty)?-1:dblMaxDsctoCli;
    }
    public char  getTipPer(){
        return (isEmpty)?' ':tipPer;
    }
    public int getCodTipPer(){
        return (isEmpty()?0:intCodTipPerCli);
    }
    public int getDiaGracia(){
        return (isEmpty()?0:intDiaGraCli);
    }


}
