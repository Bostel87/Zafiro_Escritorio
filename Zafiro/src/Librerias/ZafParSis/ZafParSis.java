/*
 * ZafParSis.java
 *
 * Created on 20 de septiembre de 2004, 01:14 PM
 * v0.17
 */

package Librerias.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafJCE.ZafJCEAlgAES;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.util.Vector;
import java.awt.Color;
import java.util.Date;
import java.util.ArrayList;
import java.math.BigDecimal;

/**
 * Esta clase contiene información que es necesaria para todo el sistema.
 * La clase lo primero que hace es leer un archivo de configuraciones para obtener información
 * básica sobre el sistema. La información que obtiene es: 
 * <BR>
 * <UL>
 * <LI>Driver de conexión</LI>
 * <LI>String de conexión</LI>
 * <LI>Usuario de conexión</LI>
 * <LI>Contraseña de conexión</LI>
 * <LI>Color de campos que son generados automáticamente por el sistema</LI>
 * <LI>Color de campos obligatorios</LI>
 * </UL>
 * Con la información básica que obtiene se conecta a la base de datos del sistema para
 * obtener información sobre las empresas que maneja el sistema.
 * La información que obtiene es la siguiente:
 * <BR>
 * <UL>
 * <LI>Driver de conexión</LI>
 * <LI>String de conexión</LI>
 * <LI>Usuario de conexión</LI>
 * <LI>Contraseña de conexión</LI>
 * <LI>Código de la empresa</LI>
 * <LI>Nombre de la empresa</LI>
 * <LI>Fecha de inicio</LI>
 * <LI>Fecha de fin</LI>
 * <LI>Estado del registro</LI>
 * </UL>
 * @author ilino
 */

public class ZafParSis implements Cloneable
{
    /*Simbología:
     *  PEM: Parámetros de la tabla "tbr_parEmp".
     *  PUS: Parámetros de la tabla "tbr_parUsr".
     **/
    //Constantes
    static final int INT_IND_PRE=0;                 //Indice predeterminado
    static final int INT_DRV_CON=0;                 //Posición del driver de conexión
    static final int INT_STR_CON=1;                 //Posición del string de conexión
    static final int INT_USR_CON=2;                 //Posición del usuario de la base de datos
    static final int INT_PWD_CON=3;                 //Posición de la clave del usuario
    static final int INT_COD_EMP=4;                 //Posición del código de la empresa
    static final int INT_NOM_EMP=5;                 //Posición del nombre de la empresa
    static final int INT_COD_LOC=6;                 //Posición del código del local
    static final int INT_FEC_INI=7;                 //Periodo de la base de datos: Fecha inicial
    static final int INT_FEC_FIN=8;                 //Periodo de la base de datos: Fecha final
    static final int INT_EST_REG=9;                 //Posición del estado de registro
    
    static final int INT_PEM_COD_EMP=0;             //Código de la empresa.
    static final int INT_PEM_COD_PAR=1;             //Código del parámetro.
    static final int INT_PEM_VA1_PAR=2;             //Valor 1 del parámetro.
    static final int INT_PEM_VA2_PAR=3;             //Valor 2 del parámetro.
    
    static final int INT_PUS_COD_EMP=0;             //Código de la empresa.
    static final int INT_PUS_COD_LOC=1;             //Código del local.
    static final int INT_PUS_COD_PAR=2;             //Código del parámetro.
    static final int INT_PUS_COD_USR=3;             //Código del usuario.
    static final int INT_PUS_VA1_PAR=4;             //Valor 1 del parámetro.
    static final int INT_PUS_VA2_PAR=5;             //Valor 2 del parámetro.
    //Variables
    private String strDirSis;                       //Directorio del sistema.
    private int intFilSel;                          //Indica la fila que contiene los datos     
    private int intCodLoc;                          //Código del local
    private String strNomLoc;                       //Nombre del local
    private int intCodUsr;                          //Código del usuario
    private String strNomUsr;                       //Nombre del usuario
    private Vector vecCon;                          //Vector de  conexión
    private Vector vecReg;                          //Registro del vector de conexión
    private Color colCamSis;                        //Color de los campos generados automáticamente por el sistema
    private Color colCamObl;                        //Color de los campos obligatorios que debe llemar el usuario
    private int intCodMnu;                          //Código del menú
    private String strNomMnu;                       //Nombre del menú
    private String strQueFecHorBasDat;              //Query para obtener la fecha y la hora de la base de datos.
    private String strFunFecHorBasDat;              //Función para obtener la fecha y la hora de la base de datos
    private String strForFecHorBasDat;              //Formato de fecha y hora utilizado por la base de datos.
    private String strForFecBasDat;                 //Formato de fecha utilizado por la base de datos.
    private String strForFec;                       //Formato de fechas
    private String strForNum;                       //Formato de números
    private int intNumDecMos;                       //Número de decimales a mostrar
    private int intNumDecBasDat;                    //Número de decimales que almacena la base de datos.
    private String strEmpPre;                       //Empresa predeterminada.
    private boolean blnLocPre;                      //Usar local predeterminado.
    private boolean blnIsCosenco;                   //Indica si es la Empresa Cosenco
    private int intCodEmpGrp;                       //Código de la empresa grupo.
    private String strTipRutRpt;                    //Tipo de ruta de los reportes.
    private ZafUtil objUti;
    private ZafJCEAlgAES objJCEAlgAES;
    private Connection con;
    private Statement stm;
    private ResultSet rst;
    private String strSQL;
    private int intAniCreSis[];
    private Date datFecSer;
    //Variables de la clase.
    private ArrayList arlParTbrParEmp;              //Parámetros de la tabla "tbr_parEmp".
    private ArrayList arlParTbrParUsr;              //Parámetros de la tabla "tbr_parUsr".
    private String strDirIpEjeSis;                  //Dirección IP de la computadora donde se está ejecutando el Sistema.
    private String strNomComEjeSis;                 //Nombre de la computadora donde se está ejecutando el Sistema.
    private String strMacAdd;                       //MAC Address.
    private char chrTipGrpClaInvPreUsr;             //Tipo de grupo de clasificación de inventario predeterminado del usuario.
    private int intCodEmp;                          //Se lo creo para el programa de Transferencias bancarias del exterior desde (ZafCon02 por Grupo).
    private BigDecimal bgdPorIvaCom, bgdPorIvaVen;  //Porcentaje de IVA en Compras, Porcentaje de IVA en Ventas.
    private int intCodCtaIvaCom, intCodCtaIvaVen;   //Código de cuenta IVA en Compras, Código de cuenta IVA en Ventas.

    /**
     * Este constructor crea una instancia de la clase ZafParSis.
     */
    public ZafParSis() 
    {
        try
        {
            intCodEmp=-1;
            //Inicializar objetos.
            objUti=new ZafUtil();
            objJCEAlgAES=new ZafJCEAlgAES();
            vecCon=new Vector();
            arlParTbrParEmp=new ArrayList();
            arlParTbrParUsr=new ArrayList();
            //Obtener los datos de la computadora sobre la que se ejecuta el Sistema
            java.net.InetAddress objIneAdd;
            objIneAdd=java.net.InetAddress.getLocalHost();
            strNomComEjeSis=objIneAdd.getHostName();
            strDirIpEjeSis=objIneAdd.getHostAddress();
            //Obtener la MAC Address.
            java.net.NetworkInterface objNetInt;
            objNetInt=java.net.NetworkInterface.getByInetAddress(objIneAdd);
            byte[] bytMacAdd=objNetInt.getHardwareAddress();
            StringBuilder objStrBui=new StringBuilder();
            for (int i=0; i<bytMacAdd.length; i++)
            {
                objStrBui.append(String.format("%02X%s", bytMacAdd[i], (i < bytMacAdd.length - 1) ? "-":""));
            }
            strMacAdd=objStrBui.toString();
            //Obtener la configuración básica del sistema.
            if (getConfiguracion())
            {
                getListadoEmpresas();
                datFecSer=objUti.getFechaServidor(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos(), getQueryFechaHoraBaseDatos());
            }
        }
        catch (java.net.UnknownHostException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (java.net.SocketException e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            objUti.mostrarMsgErr_F1(null, e);
        }
    }

    /**
     * Lee un archivo de configuraciones y obtiene la información básica del sistema.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean getConfiguracion()
    {
        boolean blnRes=true;
        java.util.StringTokenizer stkAux;
        int intRGB[]=new int[3];
        int i;
        try
        {
            intFilSel=INT_IND_PRE;
            //Obtener la ruta del sistema.
            strDirSis=objUti.getDirectorioSistema();
            //Leer archivo de configuración "ZafParSis.properties".
            java.util.Properties proArc=new java.util.Properties();
            java.io.FileInputStream fis=new java.io.FileInputStream(strDirSis + "/config/ZafParSis.properties");
            proArc.load(fis);
            fis.close();
            vecReg=new Vector();
            //Leer las propiedades.
            vecReg.add(proArc.getProperty("DriverConexion"));
            vecReg.add(proArc.getProperty("StringConexion"));
//            vecReg.add(proArc.getProperty("UsuarioConexion"));
//            vecReg.add(proArc.getProperty("ClaveConexion"));
            vecReg.add(objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", proArc.getProperty("UsuarioConexion")));
            vecReg.add(objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", proArc.getProperty("ClaveConexion")));
            vecCon.add(vecReg);
            strQueFecHorBasDat=proArc.getProperty("QueryFechaHoraBaseDatos");
            strFunFecHorBasDat=proArc.getProperty("FuncionFechaHoraBaseDatos");
            strForFecHorBasDat=proArc.getProperty("FormatoFechaHoraBaseDatos");
            strForFecBasDat=proArc.getProperty("FormatoFechaBaseDatos");
            //Obtener el color de fondo de los campos del sistema.
            i=0;
            stkAux=new java.util.StringTokenizer(proArc.getProperty("ColorCamposSistema"),",",false);
            if (stkAux.countTokens()==3)
            {
                while (stkAux.hasMoreTokens())
                {
                    intRGB[i]=Integer.parseInt(stkAux.nextToken());
                    i++;
                }
            }
            else
            {
                intRGB[0]=255;
                intRGB[1]=255;
                intRGB[2]=255;
            }
            colCamSis=new java.awt.Color(intRGB[0],intRGB[1],intRGB[2]);
            //Obtener el color de fondo de los campos obligatorios.
            i=0;
            stkAux=new java.util.StringTokenizer(proArc.getProperty("ColorCamposObligatorios"),",",false);
            if (stkAux.countTokens()==3)
            {
                while (stkAux.hasMoreTokens())
                {
                    intRGB[i]=Integer.parseInt(stkAux.nextToken());
                    i++;
                }
            }
            else
            {
                intRGB[0]=255;
                intRGB[1]=255;
                intRGB[2]=255;
            }
            colCamObl=new java.awt.Color(intRGB[0],intRGB[1],intRGB[2]);
            strForFec=proArc.getProperty("FormatoFechas");
            strForNum=proArc.getProperty("FormatoNumeros");
            intNumDecMos=Integer.parseInt(proArc.getProperty("NumeroDecimalesMostrar"));
            intNumDecBasDat=Integer.parseInt(proArc.getProperty("NumeroDecimalesBaseDatos"));
            strEmpPre=proArc.getProperty("EmpresaPredeterminada");
            blnLocPre=(proArc.getProperty("UsarLocalPredeterminado").equals("S")?true:false);
            intCodEmpGrp=Integer.parseInt(proArc.getProperty("EmpresaGrupo"));
            strTipRutRpt=proArc.getProperty("TipoRutaReportes");
            proArc=null;
        }
        catch (java.io.IOException e)
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
     * Obtiene información sobre las bases de datos de las empresas que maneja el sistema.
     * @return true: Si no se presentó ningún problema.
     * <BR>false: En el caso contrario.
     */
    private boolean getListadoEmpresas()
    {
        String strDrvCon, strStrCon, strUsrCon, strPwdCon, strAux;
        strDrvCon=((Vector)vecCon.get(intFilSel)).get(INT_DRV_CON).toString();
        strStrCon=((Vector)vecCon.get(intFilSel)).get(INT_STR_CON).toString();
        strUsrCon=((Vector)vecCon.get(intFilSel)).get(INT_USR_CON).toString();
        strPwdCon=((Vector)vecCon.get(intFilSel)).get(INT_PWD_CON).toString();
        boolean blnRes=true;
        Vector vecFec;
        int i;
        try
        {
            if (objUti.cargarDrv_F1(null, strDrvCon))
            {
                strSQL="";
                strSQL+=" SELECT a1.tx_drvCon, a1.tx_strCon, a1.tx_usrCon, a1.tx_claCon";
                strSQL+=", a1.co_emp, a1.tx_nom, a1.co_loc, a1.fe_ini, a1.fe_fin, a1.st_reg";
                strSQL+=" FROM tbm_basDat AS a1";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a1.co_reg";
                con = DriverManager.getConnection(strStrCon,strUsrCon,strPwdCon);
                if (con != null)
                {
                    stm = con.createStatement();
                    rst = stm.executeQuery(strSQL);
                    i=1;
                    while (rst.next())
                    {
                        vecReg=new Vector();
                        strAux=rst.getString("tx_drvCon");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_strCon");
                        vecReg.add((strAux==null)?"":strAux);
//                        strAux=rst.getString("tx_usrCon");
//                        vecReg.add((strAux==null)?"":strAux);
//                        strAux=rst.getString("tx_claCon");
//                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_usrCon");
                        vecReg.add((strAux==null)?"":objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", strAux));
                        strAux=rst.getString("tx_claCon");
                        vecReg.add((strAux==null)?"":objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", strAux));
                        strAux=rst.getString("co_emp");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("tx_nom");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("co_loc");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("fe_ini");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("fe_fin");
                        vecReg.add((strAux==null)?"":strAux);
                        strAux=rst.getString("st_reg");
                        vecReg.add((strAux==null)?"":strAux);
                        vecCon.add(vecReg);
                        i++;
                    }
                    rst.close(); 
                    stm.close();
                    con.close();
                }
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
     * Obtiene el indice de la fila seleccionada en el vector. Este indice se utiliza para determinar
     * la fila que contiene los datos de la empresa con la que debe trabajar el sistema.
     * @return La fila seleccionada
     */
    public int getFilaSeleccionada()
    {
        return intFilSel;
    }
    
    /**
     * Establece el indice de la fila seleccionada en el vector. Este indice se utiliza para determinar
     * la fila que contiene los datos de la empresa con la que debe trabajar el sistema.
     * Una vez que el usuario eloje una empresa y acepta se cargan los datos de la empresa seleccionada.
     * Ejemplo: Cargar el porcentaje de IVA en compras y ventas que maneja la empresa selecionada.
     * @param fila La fila del vector de la que se va a obtener los datos.
     */
    public void setFilaSeleccionada(int fila)
    {
        intFilSel=fila;
    }

    /**
     * Obtiene el driver de conexión.
     * @return La cadena que contiene el driver de conexión.
     */
    public String getDriverConexion()
    {
        return ((Vector)vecCon.get(intFilSel)).get(INT_DRV_CON).toString();
    }

    /**
     * Obtiene el driver de conexión que se encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el driver de conexión.
     * @return La cadena que contiene el driver de conexión.
     */
    public String getDriverConexion(int fila)
    {
        return ((Vector)vecCon.get(fila)).get(INT_DRV_CON).toString();
    }
    
    /**
     * Obtiene el string de conexión a la base de datos.
     * @return La cadena que contiene el string de conexión.
     */
    public String getStringConexion()
    {
        return ((Vector)vecCon.get(intFilSel)).get(INT_STR_CON).toString();
    }

    /**
     * Obtiene el string de conexión a la base de datos que se encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el string de conexión.
     * @return La cadena que contiene el string de conexión.
     */
    public String getStringConexion(int fila)
    {
        return ((Vector)vecCon.get(fila)).get(INT_STR_CON).toString();
    }
    
    /**
     * Obtiene el usuario a utilizar para conectarse a la base de datos.
     * @return La cadena que contiene el usuario de conexión.
     */    
    public String getUsuarioBaseDatos()
    {
        return ((Vector)vecCon.get(intFilSel)).get(INT_USR_CON).toString();
    }

    /**
     * Obtiene el usuario a utilizar para conectarse a la base de datos que se encuentra 
     * en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el usuario de conexión.
     * @return La cadena que contiene el usuario de conexión.
     */
    public String getUsuarioBaseDatos(int fila)
    {
        return ((Vector)vecCon.get(fila)).get(INT_USR_CON).toString();
    }
    
    /**
     * Obtiene la clave del usuario a utilizar para conectarse a la base de datos.
     * @return La cadena que contiene la clave del usuario de conexión.
     */    
    public String getClaveBaseDatos()
    {
        return ((Vector)vecCon.get(intFilSel)).get(INT_PWD_CON).toString();
    }

    /**
     * Obtiene la clave del usuario a utilizar para conectarse a la base de datos que se 
     * encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener la clave del usuario de conexión.
     * @return La cadena que contiene la clave del usuario de conexión.
     */    
    public String getClaveBaseDatos(int fila)
    {
        return ((Vector)vecCon.get(fila)).get(INT_PWD_CON).toString();
    }
    
    /**
     * Obtiene el código de la empresa que seleccionó el usuario al ingresar al sistema.
     * @return El valor que contiene el código de la empresa.
     */    
    public int getCodigoEmpresa()
    {
        if(intCodEmp==-1)
            intCodEmp=Integer.parseInt(((Vector)vecCon.get(intFilSel)).get(INT_COD_EMP).toString());
        return intCodEmp;
    }


    /**
     * Establece el código de la empresa.
     * @param codigoEmpresa El código de la empresa.
     */
    public void setCodigoEmpresa(int codigoEmpresa)
    {
        intCodEmp=codigoEmpresa;
    }

    /**
     * Obtiene el código de la empresa que se encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el código de la empresa.
     * @return El valor que contiene el código de la empresa.
     */    
    public int getCodigoEmpresa(int fila)
    {
        return Integer.parseInt(((Vector)vecCon.get(fila)).get(INT_COD_EMP).toString());
    }
    
    /**
     * Obtiene el nombre de la empresa que seleccionó el usuario al ingresar al sistema..
     * @return La cadena que contiene el nombre de la empresa.
     */    
    public String getNombreEmpresa()
    {
        return ((Vector)vecCon.get(intFilSel)).get(INT_NOM_EMP).toString();
    }

    /**
     * Obtiene el nombre de la empresa que se encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el nombre de la empresa.
     * @return La cadena que contiene el nombre de la empresa.
     */    
    public String getNombreEmpresa(int fila)
    {
        return ((Vector)vecCon.get(fila)).get(INT_NOM_EMP).toString();
    }
    
    /**
     * Obtiene la fecha inicial en la que se comenzó a utilizar la base de datos seleccionada.
     * @return Un arreglo que contiene la fecha. El arreglo contiene 3 elementos que representan
     * el año, mes y día.
     */    
    public int[] getFechaInicial()
    {
        int intFec[]=new int[3];
        intFec[0]=2050;
        intFec[1]=11;
        intFec[2]=28;
        return intFec;
    }

    /**
     * Obtiene la fecha inicial en la que se comenzó a utilizar la base de datos que se encuentra
     * en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener la fecha inicial.
     * @return Un arreglo que contiene la fecha. El arreglo contiene 3 elementos que representan
     * el año, mes y día.
     */    
    public int[] getFechaInicial(int fila)
    {
        int intFec[]=new int[3];
        intFec[0]=2050;
        intFec[1]=11;
        intFec[2]=28;
//        Si funciona:
//        intFec[0]=Integer.parseInt(((Vector)((Vector)vecCon.get(fila)).get(INT_FEC_INI)).get(0).toString());
//        intFec[1]=Integer.parseInt(((Vector)((Vector)vecCon.get(fila)).get(INT_FEC_INI)).get(1).toString());
//        intFec[2]=Integer.parseInt(((Vector)((Vector)vecCon.get(fila)).get(INT_FEC_INI)).get(2).toString());
        return intFec;
    }
    
    /**
     * Obtiene la fecha final en la que se terminó de utilizar la base de datos seleccionada.
     * @return Un arreglo que contiene la fecha. El arreglo contiene 3 elementos que representan
     * el año, mes y día.
     */    
    public int[] getFechaFinal()
    {
        int intFec[]=new int[3];
        intFec[0]=2050;
        intFec[1]=11;
        intFec[2]=28;
        return intFec;
    }
    
    /**
     * Obtiene la fecha final en la que se terminó de utilizar la base de datos que se encuentra
     * en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener la fecha final.
     * @return Un arreglo que contiene la fecha. El arreglo contiene 3 elementos que representan
     * el año, mes y día.
     */    
    public int[] getFechaFinal(byte fila)
    {
        int intFec[]=new int[3];
        intFec[0]=2050;
        intFec[1]=11;
        intFec[2]=28;
        return intFec;
    }

    /**
     * Obtiene el código del local que seleccionó el usuario al ingresar al sistema.
     * @return El valor que contiene el código del local.
     */
    public int getCodigoLocal()
    {
        return intCodLoc;
    }

    /**
     * Establece el código del local que seleccionó el usuario al ingresar al sistema.
     * @param codigoLocal El valor que contiene el código del local.
     */
    public void setCodigoLocal(int codigoLocal)
    {
        intCodLoc=codigoLocal;
    }

    /**
     * Obtiene el nombre del local que seleccionó el usuario al ingresar al sistema.
     * @return La cadena que contiene el nombre del local.
     */
    public String getNombreLocal()
    {
        return strNomLoc;
    }

    /**
     * Establece el nombre del local que seleccionó el usuario al ingresar al sistema.
     * @param nombreLocal La cadena que contiene el nombre del local que se va a establecer.
     */
    public void setNombreLocal(String nombreLocal)
    {
        strNomLoc=nombreLocal;
    }
    
    /**
     * Obtiene el código del usuario del sistema.
     * @return El valor que contiene el código del usuario.
     */    
    public int getCodigoUsuario()
    {
        return intCodUsr;
    }

    /**
     * Establece el código del usuario del sistema.
     * @param codigoUsuario El código del usuario.
     */    
    public void setCodigoUsuario(int codigoUsuario)
    {
        intCodUsr=codigoUsuario;
    }

    /**
     * Obtiene el nombre del usuario del sistema.
     * @return La cadena que contiene el usuario.
     */    
    public String getNombreUsuario()
    {
        return strNomUsr;
    }

    /**
     * Establece el nombre del usuario del sistema.
     * @param nombreUsuario El nombre del usuario.
     */    
    public void setNombreUsuario(String nombreUsuario)
    {
        strNomUsr=nombreUsuario;
    }

    /**
     * Obtiene el color de fondo de los campos que son generados automáticamente por el sistema.
     * @return El color de los campos generados por el sistema.
     */
    public java.awt.Color getColorCamposSistema()
    {
        return colCamSis;
    }

    /**
     * Establece el color de fondo de los campos que son generados automáticamente por el sistema.
     * @param colorCamposSistema El color de los campos del sistema.
     */
    public void setColorCamposSistema(java.awt.Color colorCamposSistema)
    {
        colCamSis=colorCamposSistema;
    }
    
    /**
     * Obtiene el color de fondo de los campos que son obligatorios.
     * @return El color de los campos obligatorios.
     */
    public java.awt.Color getColorCamposObligatorios()
    {
        return colCamObl;
    }

    /**
     * Establece el color de fondo de los campos obligatorios.
     * @param colorCamposObligatorios El color de los campos obligatorios.
     */
    public void setColorCamposObligatorios(java.awt.Color colorCamposObligatorios)
    {
        colCamObl=colorCamposObligatorios;
    }
    
    /**
     * Obtiene el código del menú del sistema que seleccionó el usuario. El código del menú es
     * necesario para determinar las opciones a las que tendrá acceso el usuario en el formulario. 
     * Por ejemplo: El usuario elino podría seleccionar el menú "Cotizaciones de venta" y tener
     * acceso a insertar, consultar y modificar para ese menú.
     * @return El valor que contiene el código del menú.
     */    
    public int getCodigoMenu()
    {
        return intCodMnu;
    }

    /**
     * Establece el código del menú del sistema que seleccionó el usuario. El código del menú es
     * necesario para determinar las opciones a las que tendrá acceso el usuario en el formulario. 
     * Por ejemplo: El usuario elino podría seleccionar el menú "Cotizaciones de venta" y tener
     * acceso a insertar, consultar y modificar para ese menú.
     * @param codigoMenu El código del menú.
     */    
    public void setCodigoMenu(int codigoMenu)
    {
        intCodMnu=codigoMenu;
    }

    /**
     * Obtiene el nombre del menú que seleccionó el usuario. El nombre del menú se presenta
     * en la barra de título del formulario de manera que coincida el nombre del menú con el
     * título del formulario asociado al menú.
     * @return La cadena que contiene el nombre del menú.
     */    
    public String getNombreMenu()
    {
        return strNomMnu;
    }

    /**
     * Establece el nombre del menú que seleccionó el usuario. El nombre del menú se presenta
     * en la barra de título del formulario de manera que coincida el nombre del menú con el
     * título del formulario asociado al menú.
     * @param nombreMenu El nombre del menú.
     */    
    public void setNombreMenu(String nombreMenu)
    {
        strNomMnu=nombreMenu;
    }
    
    /**
     * Obtener número de filas: Obtiene el número de filas que tiene el vector de conexión.
     * @return El valor que contiene el número de filas del vector.
     */
    public int getNumeroFilas()
    {
        return vecCon.size();
    }

    /**
     * Obtiene el formato de fecha que el sistema debe utilizar para presentar fechas. Podría
     * ser "dd/MM/yyyy", "dd/MMM/yyyy", "yyyy/MM/dd", etc.
     * @return La cadena que contiene el formato de fecha que el sistema debe mostrar.
     */    
    public String getFormatoFecha()
    {
        return strForFec;
    }
    
    /**
     * Establece el formato de fecha que el sistema debe utilizar para presentar fechas. Podría
     * ser "dd/MM/yyyy", "dd/MMM/yyyy", "yyyy/MM/dd", etc.
     * @param formatoFecha El formato de fecha que el sistema debe utilizar para mostrar fechas.
     */    
    public void setFormatoFecha(String formatoFecha)
    {
        strForFec=formatoFecha;
    }
    
    /**
     * Obtiene el formato de número que el sistema debe utilizar para presentar números. Podría
     * ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @return La cadena que contiene el formato de número que el sistema debe mostrar.
     */    
    public String getFormatoNumero()
    {
        return strForNum;
    }
    
    /**
     * Establece el formato de número que el sistema debe utilizar para presentar números. Podría
     * ser "###,###.###", "###.##", "$###,###.##", "\u00a5###,###.###", etc.
     * @param formatoNumero El formato de número que el sistema debe utilizar para mostrar números.
     */    
    public void setFormatoNumero(String formatoNumero)
    {
        strForNum=formatoNumero;
    }
    
    /**
     * Obtiene el número de decimales que debe mostrar el sistema. Por ejemplo, internamente el sistema
     * puede manejar 2 o más decimales. Pero, en pantalla se puede mostrar sólo 2 decimales.
     * @return El valor que contiene el número de decimales que se va a mostrar.
     */
    public int getDecimalesMostrar()
    {
        return intNumDecMos;
    }
    
    /**
     * Establece el número de decimales que debe mostrar el sistema. Por ejemplo, internamente el sistema
     * puede manejar 2 o más decimales. Pero, en pantalla se puede mostrar sólo 2 decimales.
     * @param decimales El número de decimales que se va a mostrar.
     */    
    public void setDecimalesMostrar(int decimales)
    {
        intNumDecMos=decimales;
    }
    
    /**
     * Obtiene el número de decimales que almacena la base de datos.
     * @return El valor que contiene el número de decimales que almacena la base de datos.
     */
    public int getDecimalesBaseDatos()
    {
        return intNumDecBasDat;
    }
    
    /**
     * Establece el número de decimales que almacena la base de datos.
     * @param decimales El número de decimales que almacena la base de datos.
     */    
    public void setDecimalesBaseDatos(int decimales)
    {
        intNumDecBasDat=decimales;
    }
    
    /**
     * Obtiene la sentencia SQL utilizada para obtener la fecha y hora de la base de datos. Podría
     * devolver una de las siguientes cadenas dependiendo de la base de datos con la que se trabaje.
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Base de datos</I></TD><TD><I>Sentencia SQL</I></TD></TR>
     * <TR><TD>Microsoft SQL Server</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>PostgreSQL</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>MySQL</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>Oracle</TD><TD>SELECT SYSDATE FROM dual</TD></TR>
     * </TABLE>
     * </CENTER>
     * @return La cadena que contiene la sentencia SQL que obtendrá la fecha y hora de la base de datos.
     */
    public String getQueryFechaHoraBaseDatos()
    {
        return strQueFecHorBasDat;
    }
    
    /**
     * Establece la sentencia SQL utilizada para obtener la fecha y hora de la base de datos. Podría
     * recibir una de las siguientes cadenas dependiendo de la base de datos con la que se trabaje.
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Base de datos</I></TD><TD><I>Sentencia SQL</I></TD></TR>
     * <TR><TD>Microsoft SQL Server</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>PostgreSQL</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>MySQL</TD><TD>SELECT CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>Oracle</TD><TD>SELECT SYSDATE FROM dual</TD></TR>
     * </TABLE>
     * </CENTER>
     * @param query La cadena que contiene la sentencia SQL que se establecerá para obtener la fecha y hora de la base de datos.
     */
    public void setQueryFechaHoraBaseDatos(String query)
    {
        strQueFecHorBasDat=query;
    }
    
    /**
     * Obtiene la función utilizada para obtener la fecha y hora de la base de datos. Podría
     * devolver una de las siguientes cadenas dependiendo de la base de datos con la que se trabaje.
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Base de datos</I></TD><TD><I>Función</I></TD></TR>
     * <TR><TD>Microsoft SQL Server</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>PostgreSQL</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>MySQL</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Se podría utilizar para insertar un registro en una tabla de la base de datos. Por ejemplo:
     * <BR><CENTER>INSERT INTO tbm_usr(co_usr, tx_cla, fe_ing) VALUES('elino', '123', CURRENT_TIMESTAMP)</CENTER>
     * <BR>Si no se lo hace así se tendría que hacer 2 pasos:
     * <UL>
     * <LI>1) Hacer un query para obtener la fecha de la base de datos.
     * <LI>2) Hacer el query para insertar el registro en la base de datos.
     * </UL>
     * @return La cadena que contiene la función que obtendrá la fecha y hora de la base de datos.
     */
    public String getFuncionFechaHoraBaseDatos()
    {
        return strFunFecHorBasDat;
    }
    
    /**
     * Establece la función utilizada para obtener la fecha y hora de la base de datos. Podría
     * recibir una de las siguientes cadenas dependiendo de la base de datos con la que se trabaje.
     * <BR><BR>
     * <CENTER>
     * <TABLE BORDER=1>
     * <TR><TD><I>Base de datos</I></TD><TD><I>Función</I></TD></TR>
     * <TR><TD>Microsoft SQL Server</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>PostgreSQL</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * <TR><TD>MySQL</TD><TD>CURRENT_TIMESTAMP</TD></TR>
     * </TABLE>
     * </CENTER>
     * <BR>Se podría utilizar para insertar un registro en una tabla de la base de datos. Por ejemplo:
     * <BR><CENTER>INSERT INTO tbm_usr(co_usr, tx_cla, fe_ing) VALUES('elino', '123', CURRENT_TIMESTAMP)</CENTER>
     * <BR>Si no se lo hace así se tendría que hacer 2 pasos:
     * <UL>
     * <LI>1) Hacer un query para obtener la fecha de la base de datos.
     * <LI>2) Hacer el query para insertar el registro en la base de datos.
     * </UL>
     * @param funcion La cadena que contiene la función que obtendrá la fecha y hora de la base de datos.
     */
    public void setFuncionFechaHoraBaseDatos(String funcion)
    {
        strFunFecHorBasDat=funcion;
    }

    /**
     * Obtiene el formato de fecha y hora que el sistema debe utilizar para trabajar con la base de datos. Por lo general
     * las bases de datos manejan el formato "yyyy-MM-dd HH:mm:ss". Pero, podrían manejar un formato diferente.
     * @return La cadena que contiene el formato de fecha y hora que el sistema debe utilizar para trabajar con la base de datos.
     */
    public String getFormatoFechaHoraBaseDatos()
    {
        return strForFecHorBasDat;
    }
    
    /**
     * Establece el formato de fecha y hora que el sistema debe utilizar para trabajar con la base de datos. Por lo general
     * las bases de datos manejan el formato "yyyy-MM-dd HH:mm:ss". Pero, podrían manejar un formato diferente.
     * @param formato La cadena que contiene el formato de fecha y hora que el sistema debe utilizar para trabajar con la base de datos.
     */
    public void setFormatoFechaHoraBaseDatos(String formato)
    {
        strForFecHorBasDat=formato;
    }

    /**
     * Obtiene el formato de fecha que el sistema debe utilizar para trabajar con la base de datos. Por lo general
     * las bases de datos manejan el formato "yyyy-MM-dd". Pero, podrían manejar un formato diferente.
     * @return La cadena que contiene el formato de fecha que el sistema debe utilizar para trabajar con la base de datos.
     */
    public String getFormatoFechaBaseDatos()
    {
        return strForFecBasDat;
    }
    
    /**
     * Establece el formato de fecha que el sistema debe utilizar para trabajar con la base de datos. Por lo general
     * las bases de datos manejan el formato "yyyy-MM-dd". Pero, podrían manejar un formato diferente.
     * @param formato La cadena que contiene el formato de fecha que el sistema debe utilizar para trabajar con la base de datos.
     */
    public void setFormatoFechaBaseDatos(String formato)
    {
        strForFecBasDat=formato;
    }

    /**
     * Obtiene la empresa predeterminada. Este valor es utilizado para determinar si el sistema
     * debe permitir que el usuario seleccione la empresa en la que desea trabajar o si el sistema debe de
     * forma predeterminada decidir la empresa en la que debe trabajar el usuario.
     * @return El código de la empresa predeterminada.
     */
    public String getEmpresaPredeterminada()
    {
        return strEmpPre;
    }
    
    /**
     * Establece la empresa predeterminada. Este valor es utilizado para determinar si el sistema
     * debe permitir que el usuario seleccione la empresa en la que desea trabajar o si el sistema debe de
     * forma predeterminada decidir la empresa en la que debe trabajar el usuario.
     * @param empresa El código de la empresa predeterminada.
     */
    public void setEmpresaPredeterminada(String empresa)
    {
        strEmpPre=empresa;
    }
    
    /**
     * Determina si se debe utilizar el local predeterminado.
     * @return true: Si se debe mostrar sólo el local predeterminado.
     * <BR>false: Si se debe mostrar todos los locales.
     */
    public boolean usarLocalPredeterminado()
    {
        return blnLocPre;
    }
    
    /**
     * Establece si se debe utilizar el local predeterminado.
     * @param indicador El valor que determina si se debe utilizar sólo el local predeterminado.
     * Puede ocurrir lo siguiente:
     * <UL>
     * <LI>true: El sistema sólo mostrará el local predeterminado para la empresa seleccionada.
     * <LI>false: El sistema mostrará todas las bodegas de la empresa.
     * </UL>
     */
    public void setUsarLocalPredeterminado(boolean indicador)
    {
        blnLocPre=indicador;
    }
    
    /**
     * Obtiene el código de la empresa grupo. El código de la empresa grupo sirve
     * para determinar la empresa que consolida lo que se ha realizado en todas
     * las empresas del grupo.
     * @return El código de la empresa grupo.
     */
    public int getCodigoEmpresaGrupo()
    {
        return intCodEmpGrp;
    }

    /**
     * Establece el código de la empresa grupo. El código de la empresa grupo sirve
     * para determinar la empresa que consolida lo que se ha realizado en todas
     * las empresas del grupo.
     * @param codigoGrupo El código de la empresa grupo.
     */
    public void setCodigoEmpresaGrupo(int codigoGrupo)
    {
        intCodEmpGrp=codigoGrupo;
    }
    
    /**
     * Obtiene el tipo de ruta utilizado para los reportes.
     * @return El tipo de ruta utilizado para los reportes. Puede devolver uno de los siguiente valores:
     * <UL>
     * <LI>A: Ruta absoluta (Obtiene su valor del campo "tbm_rptSis.tx_rutAbsRpt").
     * <LI>R: Ruta relativa (Obtiene su valor del directorio donde se encuentra el archivo "Zafiro.jar").
     * </UL>
     */
    public String getTipoRutaReportes()
    {
        return strTipRutRpt;
    }

    /**
     * Establece el tipo de ruta utilizado para los reportes.
     * @param tipoRutaReportes El tipo de ruta utilizado para los reportes. Puede recibir uno de los siguiente valores:
     * <UL>
     * <LI>A: Ruta absoluta (Obtiene su valor del campo "tbm_rptSis.tx_rutAbsRpt").
     * <LI>R: Ruta relativa (Obtiene su valor del directorio donde se encuentra el archivo "Zafiro.jar").
     * </UL>
     */
    public void setTipoRutaReportes(String tipoRutaReportes)
    {
        strTipRutRpt=tipoRutaReportes;
    }
    
    /**
     * Obtiene el directorio donde está ubicado el sistema.
     * <BR>Por ejemplo:
     * <UL>
     * <LI>Windows: C:\Zafiro
     * <LI>Linux: /Zafiro
     * </UL>
     * La ruta del sistema es utilizada para determinar la ubicación de otros directorios.
     * <BR>Por ejemplo, si se desea obtener la ruta del directorio de configuración se tendría
     * que agregar "config" a la ruta del sistema. Es decir, RutaSistema + "/config".
     * @return El directorio donde está ubicado el sistema.
     */
    public String getDirectorioSistema()
    {
        return strDirSis;
    }
    
    /**
     * Establece el directorio donde está ubicado el sistema.
     * <BR>Por ejemplo:
     * <UL>
     * <LI>Windows: C:\Zafiro
     * <LI>Linux: /Zafiro
     * </UL>
     * La ruta del sistema es utilizada para determinar la ubicación de otros directorios.
     * <BR>Por ejemplo, si se desea obtener la ruta del directorio de configuración se tendría
     * que agregar "config" a la ruta del sistema. Es decir, RutaSistema + "/config".
     * @param directorio El directorio donde está ubicado el sistema.
     */
    public void setDirectorioSistema(String directorio)
    {
        strDirSis=directorio;
    }
    
    /**
     * Obtiene una copia del objeto. Se utiliza cuando se desea realizar copias de objetos. Por lo general los objetos
     * en Java son pasados por referencia. Esto a veces trae problemas porque se puede pasar un objeto a una función
     * y desear alterar el objeto que recibió la función. Pero, como lo que se recibe es una referencia al hacer una
     * modificación ocurrirá que dicha modificación también afectará al objeto que se envió. Para evitar éste tipo de
     * problemas se puede utilizar el método clonar con lo cual se creará una copia local que no afectará en nada al
     * objeto que se pasó a la función.
     * @return La copia del objeto.
     */
    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
    
    /**
     * Esta función permite cargar el listado de años que han sido creados en el sistema.
     * @return true: Si se pudo cargar el listado de años creados.
     * <BR>false: En el caso contrario.
     * <BR><BR>Nota.- Este listado de años creados es utilizado para validar que no se pueda
     * ingresar ningún documento mientras no se haya creado el año en el sistema. Por ejemplo:
     * digamos que estamos 02/Ene/2008. El administrador del sistema debería haber utilizado
     * el programa respectivo para crear el año 2008. Mientras el administrador no cree dicho
     * año no se le permitirá ingresar, actualizar o eliminar ningún documento del año 2008.
     */
    public boolean cargarAniosCreadosSistema()
    {
        return cargarAniCreSis();
    }
    
    /**
     * Esta función permite cargar los años creados en el sistema.
     * @return true: Si se pudo cargar los años creados en el sistema.
     * <BR>false: En el caso contrario.
     */
    private boolean cargarAniCreSis()
    {
        int i;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener la cantidad de registros a procesar.
                strSQL="";
                strSQL+="SELECT COUNT(*)";
                strSQL+=" FROM tbm_aniCreSis";
                strSQL+=" WHERE co_emp=" + getCodigoEmpresa();
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intAniCreSis=new int[rst.getInt(1)];
                    rst.close();
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="SELECT a1.ne_ani";
                    strSQL+=" FROM tbm_aniCreSis AS a1";
                    strSQL+=" WHERE a1.co_emp=" + getCodigoEmpresa();
                    strSQL+=" ORDER BY a1.ne_ani";
                    rst=stm.executeQuery(strSQL);
                    i=0;
                    while (rst.next())
                    {
                        intAniCreSis[i]=rst.getInt("ne_ani");
                        i++;
                    }
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
     * Obtiene el listado de años creados en el sistema.
     * @return El arreglo de enteros que contiene los años creados en el sistema.
     */
    public int[] getAniosCreadosSistema()
    {
        return intAniCreSis;
    }
    
    /**
     * Esta función determina si el año especificado ya ha sido creado en el sistema.
     * Por ejemplo, si en el listado de años creados en el sistema están los años 2005, 2006, 2007
     * y se recibe el año 2008 como parámetro la función devolverá "false" porque el año recibido
     * no está incluido en los años creados en el sistema.
     * @param anio El año a evaluar.
     * @return true: Si el año especificado ya ha sido creado en el sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean isAnioDocumentoCreadoSistema(int anio)
    {
        int i;
        for (i=intAniCreSis.length-1; i>=0; i--)
        {
            if (anio==intAniCreSis[i])
                return true;
        }
        return false;
    }
    
    /**
     * Obtiene la fecha y hora del servidor de base de datos en el momento en que se ingresa al sistema.
     * <BR>Posible uso de esta función:
     * <UL>
     * <LI>En la mayoría de los documentos se necesita presentar la fecha del documento cuando se da click
     * en el botón INSERTAR. La fecha que se debe presentar es la fecha del servidor y no la fecha local.
     * Un gran porcentaje presenta la fecha local y el otro porcentaje se conecta a la base de datos para obtener
     * la fecha del servidor. Para evitar que se realice una conexión a la base de datos cada vez que se da click
     * en el botón INSERTAR se recomienda el uso del método "getFechaHoraServidorIngresarSistema()" ya que este
     * método sólo se conecta a la base de datos para obtener la fecha y hora del servidor cuando se ingresa al
     * sistema. Es decir, sólo se conecta a la base de datos una sóla vez.
     * </UL>
     * @return La fecha y hora del servidor de base de datos en el momento en que se ingresa al sistema.
     */
    public Date getFechaHoraServidorIngresarSistema()
    {
        return datFecSer;
    }
    
    public String getDriverConexionCentral()
    {
        String strDrvCon;
        strDrvCon=((Vector)vecCon.get(0)).get(INT_DRV_CON).toString();
        return strDrvCon;
    }
    
    public String getStringConexionCentral()
    {
        String strStrCon;
        strStrCon=((Vector)vecCon.get(0)).get(INT_STR_CON).toString();
        return strStrCon;
    }    
    
    public String getUsuarioConexionCentral()
    {
        String strUsrCon;
        strUsrCon=((Vector)vecCon.get(0)).get(INT_USR_CON).toString();
        return strUsrCon;
    }    
    
    public String getClaveConexionCentral()
    {
        String strPwdCon;
        strPwdCon=((Vector)vecCon.get(0)).get(INT_PWD_CON).toString();
        return strPwdCon;
    }
    
    /**
     * Obtiene el código del local que se encuentra en la fila especificada.
     * @param fila La fila del vector de la que se va a obtener el código del local.
     * @return El valor que contiene el código del local.
     */
    public int getCodigoLocal(int fila)
    {
        return Integer.parseInt(((Vector)vecCon.get(fila)).get(INT_COD_LOC).toString());
    }
    
    /**
     * Esta función carga los parámetros de configuración del Sistema. Los parámetros se los carga de las tablas
     * "tbr_parEmp" y "tbr_parUsr". Dichos parámetros de configuración sirven para determinar la forma en la
     * que el sistema funcionará.
     * Por ejemplo, el parámetro de configuración "tbm_parSis.co_par=3" se utiliza para determinar si se debe presentar
     * todo el listado de clientes (Es decir, leer la tabla tbm_cli) o si sólo se debe mostrar los clientes asignados
     * al local en el cual se está trabajando (Es decir, leer la tabla tbr_cliLoc).
     * @return true: Si ze pudieron cargar los parámetros de configuración del sistema.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarParametrosSistema()
    {
        ArrayList arlReg;
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener datos de la tabla "tbr_parEmp".
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_par, a2.nd_par1, a2.nd_par2";
                strSQL+=" FROM tbm_parSis AS a1";
                strSQL+=" INNER JOIN tbr_parEmp AS a2 ON (a1.co_par=a2.co_par)";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a2.co_emp, a2.co_par";
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    arlReg=new ArrayList();
                    arlReg.add(INT_PEM_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_PEM_COD_PAR,rst.getString("co_par"));
                    arlReg.add(INT_PEM_VA1_PAR,rst.getString("nd_par1"));
                    arlReg.add(INT_PEM_VA2_PAR,rst.getString("nd_par2"));
                    arlParTbrParEmp.add(arlReg);
                }
                rst.close();
                //Obtener datos de la tabla "tbr_parUsr".
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a2.co_emp, a2.co_loc, a2.co_par, a2.co_usr, a2.nd_par1, a2.nd_par2";
                strSQL+=" FROM tbm_parSis AS a1";
                strSQL+=" INNER JOIN tbr_parUsr AS a2 ON (a1.co_par=a2.co_par)";
                strSQL+=" WHERE a1.st_reg='A'";
                strSQL+=" ORDER BY a2.co_emp, a2.co_loc, a2.co_par, a2.co_usr";
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    arlReg=new ArrayList();
                    arlReg.add(INT_PUS_COD_EMP,rst.getString("co_emp"));
                    arlReg.add(INT_PUS_COD_LOC,rst.getString("co_loc"));
                    arlReg.add(INT_PUS_COD_PAR,rst.getString("co_par"));
                    arlReg.add(INT_PUS_COD_USR,rst.getString("co_usr"));
                    arlReg.add(INT_PUS_VA1_PAR,rst.getString("nd_par1"));
                    arlReg.add(INT_PUS_VA2_PAR,rst.getString("nd_par2"));
                    arlParTbrParUsr.add(arlReg);
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
     * Obtiene los valores que tiene asignado el parámetro especificado en la tabla "tbr_parEmp".
     * @param codigoEmpresa El código de la empresa.
     * @param codigoParametro El código del parámetro.
     * @return Un ArrayList que contiene los valores que tiene asignado el parámetro especificado.
     * Cuando se creó ésta función la tabla "tbr_parEmp" sólo contaba con 2 campos donde se podía establecer
     * valores para el parámetro (nd_par1 y nd_par2). Pero, en un futuro puede ser que aparezcan otros campos
     * (nd_par3, nd_par4, etc).
     */
    public ArrayList getValoresParametroTbrParEmp(int codigoEmpresa, int codigoParametro)
    {
        int i;
        ArrayList arlRes=null;
        try
        {
            for (i=0; i<arlParTbrParEmp.size(); i++)
            {
                if (objUti.getIntValueAt(arlParTbrParEmp, i, INT_PEM_COD_EMP)==codigoEmpresa && objUti.getIntValueAt(arlParTbrParEmp, i, INT_PEM_COD_PAR)==codigoParametro)
                {
                    arlRes=new ArrayList();
                    arlRes.add(objUti.getObjectValueAt(arlParTbrParEmp, i, INT_PEM_VA1_PAR));
                    arlRes.add(objUti.getObjectValueAt(arlParTbrParEmp, i, INT_PEM_VA2_PAR));
                    break;
                }
            }
        }
        catch (Exception e)
        {
            arlRes=null;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return arlRes;
    }
    
    /**
     * Obtiene los valores que tiene asignado el parámetro especificado en la tabla "tbr_parUsr".
     * @param codigoEmpresa El código de la empresa.
     * @param codigoLocal El código del local.
     * @param codigoParametro El código del parámetro.
     * @param codigoUsuario El código del usuario.
     * @return Un ArrayList que contiene los valores que tiene asignado el parámetro especificado.
     * Cuando se creó ésta función la tabla "tbr_parUsr" sólo contaba con 2 campos donde se podía establecer
     * valores para el parámetro (nd_par1 y nd_par2). Pero, en un futuro puede ser que aparezcan otros campos
     * (nd_par3, nd_par4, etc).
     */
    public ArrayList getValoresParametroTbrParUsr(int codigoEmpresa, int codigoLocal, int codigoParametro, int codigoUsuario)
    {
        int i;
        ArrayList arlRes=null;
        try
        {
            for (i=0; i<arlParTbrParUsr.size(); i++)
            {
                if (objUti.getIntValueAt(arlParTbrParUsr, i, INT_PUS_COD_EMP)==codigoEmpresa && objUti.getIntValueAt(arlParTbrParUsr, i, INT_PUS_COD_LOC)==codigoLocal && objUti.getIntValueAt(arlParTbrParUsr, i, INT_PUS_COD_PAR)==codigoParametro && objUti.getIntValueAt(arlParTbrParUsr, i, INT_PUS_COD_USR)==codigoUsuario)
                {
                    arlRes=new ArrayList();
                    arlRes.add(objUti.getObjectValueAt(arlParTbrParUsr, i, INT_PUS_VA1_PAR));
                    arlRes.add(objUti.getObjectValueAt(arlParTbrParUsr, i, INT_PUS_VA2_PAR));
                    break;
                }
            }
        }
        catch (Exception e)
        {
            arlRes=null;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return arlRes;
    }

    /**
     * Obtiene la dirección IP de la computadora donde se está ejecutando el Sistema.
     * Ejemplo: 172.16.1.15
     * @return La dirección IP de la computadora donde se está ejecutando el Sistema.
     */
    public String getDireccionIP()
    {
        return strDirIpEjeSis;
    }
    
    /**
     * Obtiene el nombre de la computadora donde se está ejecutando el Sistema.
     * Ejemplo: cobranzas
     * @return El nombre de la computadora donde se está ejecutando el Sistema.
     */
    public String getNombreComputadora()
    {
        return strNomComEjeSis;
    }
    
    /**
     * Obtiene el nombre de la computadora incluyendo la dirección IP donde se está ejecutando el Sistema.
     * Ejemplo: cobranzas (172.16.1.15)
     * @return El nombre de la computadora incluyendo la dirección IP donde se está ejecutando el Sistema.
     */
    public String getNombreComputadoraConDirIP()
    {
        return strNomComEjeSis + " (" + strDirIpEjeSis + ")";
    }

    /**
     * Obtiene el tipo de grupo de clasificación de inventario predeterminado del usuario.
     * Para clasificar el inventario se crearon "Grupos de clasificaciones" y "Clasificaciones".
     * Cuando un usuario va a crear un "Grupo" aparecen 2 botones de radio (Público y Privado)
     * que determinan si el grupo que se va a crear es público o privado. Este método devuelve
     * el valor que se encuentra almacenado en "tbm_usr.tx_tipGrpClaInvPre" el cual se utiliza
     * para marcar el botón de radio "Público" o el botón de radio "Privado" según sea el caso.
     * @return El tipo de grupo de clasificación de inventario predeterminado del usuario.
     */
    public char getTipGrpClaInvPreUsr()
    {
        return this.chrTipGrpClaInvPreUsr;
    }

    /**
     * Establece el tipo de grupo de clasificación de inventario predeterminado del usuario.
     * Para clasificar el inventario se crearon "Grupos de clasificaciones" y "Clasificaciones".
     * Cuando un usuario va a crear un "Grupo" aparecen 2 botones de radio (Público y Privado)
     * que determinan si el grupo que se va a crear es público o privado. Este método devuelve
     * el valor que se encuentra almacenado en "tbm_usr.tx_tipGrpClaInvPre" el cual se utiliza
     * para marcar el botón de radio "Público" o el botón de radio "Privado" según sea el caso.
     * @param chrTipGrpClaInvPreUsr El tipo de grupo de clasificación de inventario predeterminado del usuario.
     */
    public void setTipGrpClaInvPreUsr(char chrTipGrpClaInvPreUsr)
    {
        this.chrTipGrpClaInvPreUsr=chrTipGrpClaInvPreUsr;
    }

    /**
     * Obtiene la MAC Address de la computadora donde se está ejecutando el Sistema.
     * Ejemplo: E0-69-95-57-19-9C
     * @return La MAC Address de la computadora donde se está ejecutando el Sistema.
     */
    public String getMACAddress()
    {
        return strMacAdd;
    }
    
    /**
     * Esta función carga los impuestos del Sistema para la empresa y local seleccionado. Dichos datos se los obtiene de la tabla "tbm_cfgImpLoc".
     * Los datos que se obtiene son los siguientes:
     * <UL>
     * <LI>IVA en Compras: Porcentaje (getPorcentajeIvaCompras())</LI>
     * <LI>IVA en Compras: Código de la cuenta contable (getCodigoCuentaContableIvaCompras())</LI>
     * <LI>IVA en Ventas: Porcentaje (getPorcentajeIvaVentas())</LI>
     * <LI>IVA en Ventas: Código de la cuenta contable (getCodigoCuentaContableIvaVentas())</LI>
     * </UL>
     * @return true: Si se pudo cargar los datos.
     * <BR>false: En el caso contrario.
     */
    public boolean cargarConfiguracionImpuestos()
    {
        boolean blnRes=true;
        try
        {
            con=DriverManager.getConnection(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="SELECT a1.co_imp, a1.nd_porImp, a1.co_cta";
                strSQL+=" FROM tbm_cfgImpLoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + getCodigoEmpresa() + " AND a1.co_loc=" + getCodigoLocal() + " AND a1.co_imp IN (1, 2)";
                strSQL+=" AND (CASE WHEN a1.fe_vigHas IS NULL THEN CURRENT_DATE>=fe_vigDes ELSE CURRENT_DATE>=fe_vigDes AND CURRENT_DATE<=fe_vigHas END)";
                strSQL+=" ORDER BY a1.co_imp";
                rst=stm.executeQuery(strSQL);
                while (rst.next())
                {
                    switch (rst.getInt("co_imp"))
                    {
                        case 1: //1=IVA en Compras
                            bgdPorIvaCom=rst.getBigDecimal("nd_porImp");
                            intCodCtaIvaCom=rst.getInt("co_cta");
                            break;
                        case 2: //2=IVA en Ventas
                            bgdPorIvaVen=rst.getBigDecimal("nd_porImp");
                            intCodCtaIvaVen=rst.getInt("co_cta");
                            break;
                    }
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
     * Obtiene el porcentaje de IVA en Compras para la empresa y local seleccionado a la fecha actual.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @return El porcentaje de IVA en Compras.
     */
    public BigDecimal getPorcentajeIvaCompras()
    {
        return bgdPorIvaCom;
    }
    
    /**
     * Obtiene el porcentaje de IVA en Ventas para la empresa y local seleccionado a la fecha actual.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @return El porcentaje de IVA en Ventas.
     */
    public BigDecimal getPorcentajeIvaVentas()
    {
        return bgdPorIvaVen;
    }
    
    /**
     * Obtiene el código de la cuenta contable del IVA en Compras para la empresa y local seleccionado a la fecha actual.
     * Dicho código se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @return El código de la cuenta contable de IVA en Compras.
     */
    public int getCodigoCuentaContableIvaCompras()
    {
        return intCodCtaIvaCom;
    }
    
    /**
     * Obtiene el código de la cuenta contable del IVA en Ventas para la empresa y local seleccionado a la fecha actual.
     * Dicho código se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @return El código de la cuenta contable de IVA en Ventas.
     */
    public int getCodigoCuentaContableIvaVentas()
    {
        return intCodCtaIvaVen;
    }
    
    /**
     * Obtiene el porcentaje de impuesto para la empresa y local en la fecha especificada.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param intCodEmp Código de la empresa
     * @param intCodLoc Código del local
     * @param intCodImp Código del impuesto
     * @param datFec Fecha en la que se desea obtener el dato
     * @return El porcentaje de impuesto.
     */
    public BigDecimal getPorImp(int intCodEmp, int intCodLoc, int intCodImp, Date datFec)
    {
        String strFec;
        BigDecimal bgdRes=null;
        try
        {
            strFec=objUti.formatearFecha(datFec, getFormatoFechaBaseDatos());
            con=DriverManager.getConnection(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.nd_porImp";
                strSQL+=" FROM tbm_cfgImpLoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND a1.co_loc=" + intCodLoc + " AND a1.co_imp=" + intCodImp;
                strSQL+=" AND (CASE WHEN a1.fe_vigHas IS NULL THEN '" + strFec + "'>=fe_vigDes ELSE '" + strFec + "'>=fe_vigDes AND '" + strFec + "'<=fe_vigHas END)";
                strSQL+=" ORDER BY a1.co_imp";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    bgdRes=rst.getBigDecimal("nd_porImp");
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
            bgdRes=null;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            bgdRes=null;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return bgdRes;
    }
    
    /**
     * Obtiene el código de la cuenta contable del impuesto para la empresa y local en la fecha especificada.
     * Dicho código se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param intCodEmp Código de la empresa
     * @param intCodLoc Código del local
     * @param intCodImp Código del impuesto
     * @param datFec Fecha en la que se desea obtener el dato
     * @return El código de la cuenta contable del impuesto.
     */
    public int getCodCtaConImp(int intCodEmp, int intCodLoc, int intCodImp, Date datFec)
    {
        String strFec;
        int intRes=-1;
        try
        {
            strFec=objUti.formatearFecha(datFec, getFormatoFechaBaseDatos());
            con=DriverManager.getConnection(getStringConexion(), getUsuarioBaseDatos(), getClaveBaseDatos());
            if (con!=null)
            {
                stm=con.createStatement();
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+=" SELECT a1.co_cta";
                strSQL+=" FROM tbm_cfgImpLoc AS a1";
                strSQL+=" WHERE a1.co_emp=" + intCodEmp + " AND a1.co_loc=" + intCodLoc + " AND a1.co_imp=" + intCodImp;
                strSQL+=" AND (CASE WHEN a1.fe_vigHas IS NULL THEN '" + strFec + "'>=fe_vigDes ELSE '" + strFec + "'>=fe_vigDes AND '" + strFec + "'<=fe_vigHas END)";
                strSQL+=" ORDER BY a1.co_imp";
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                {
                    intRes=rst.getInt("co_cta");
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
            intRes=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        catch (Exception e)
        {
            intRes=-1;
            objUti.mostrarMsgErr_F1(null, e);
        }
        return intRes;
    }
    
    /**
     * Obtiene el porcentaje de IVA en Compras para la empresa y local en la fecha especificada.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param codigoEmpresa Código de la empresa.
     * @param codigoLocal Código del local.
     * @param fecha Fecha en la que se desea obtener el dato.
     * @return El porcentaje de IVA en Compras.
     */
    public BigDecimal getPorcentajeIvaCompras(int codigoEmpresa, int codigoLocal, Date fecha)
    {
        return getPorImp(codigoEmpresa, codigoLocal, 1, fecha);
    }

    /**
     * Obtiene el porcentaje de IVA en Ventas para la empresa y local en la fecha especificada.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param codigoEmpresa Código de la empresa
     * @param codigoLocal Código del local
     * @param fecha Fecha en la que se desea obtener el dato
     * @return El porcentaje de IVA en Ventas.
     */
    public BigDecimal getPorcentajeIvaVentas(int codigoEmpresa, int codigoLocal, Date fecha)
    {
        return getPorImp(codigoEmpresa, codigoLocal, 2, fecha);
    }

    /**
     * Obtiene el código de la cuenta contable del IVA en Compras para la empresa y local en la fecha especificada.
     * Dicho código se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param codigoEmpresa Código de la empresa
     * @param codigoLocal Código del local
     * @param fecha Fecha en la que se desea obtener el dato
     * @return El código de la cuenta contable del IVA en Compras.
     */
    public int getCodigoCuentaContableIvaCompras(int codigoEmpresa, int codigoLocal, Date fecha)
    {
        return getCodCtaConImp(codigoEmpresa, codigoLocal, 1, fecha);
    }

    /**
     * Obtiene el código de la cuenta contable del IVA en Ventas para la empresa y local en la fecha especificada.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param codigoEmpresa Código de la empresa
     * @param codigoLocal Código del local
     * @param fecha Fecha en la que se desea obtener el dato
     * @return El código de la cuenta contable del IVA en Ventas.
     */
    public int getCodigoCuentaContableIvaVentas(int codigoEmpresa, int codigoLocal, Date fecha)
    {
        return getCodCtaConImp(codigoEmpresa, codigoLocal, 2, fecha);
    }

    /*AGREGADO POR COMPENSACION SOLIDARIA*/
    /**
     * Obtiene el porcentaje de COMPENSACION SOLIDARIA en Ventas para la empresa y local en la fecha especificada.
     * Dicho porcentaje se lo obtiene de la tabla "tbm_cfgImpLoc".
     * @param codigoEmpresa Codigo de la empresa.
     * @param codigoLocal Codigo del local.
     * @param fecha Fecha en la que se desea obtener el dato.
     * @return  El porcentaje de Compensacion solidaria en ventas.
     */
    public BigDecimal getPorcentajeCmpSolVentas(int codigoEmpresa, int codigoLocal, Date fecha)
    {
        return getPorImp(codigoEmpresa, codigoLocal, 3, fecha);
    }
    /*AGREGADO POR COMPENSACION SOLIDARIA*/

    /**
     * Determina si es la Empresa Cosenco
     * @return true: Si es la empresa Cosenco
     * <BR>false: Caso contrario.
     */
    public boolean isCosenco()
    {
        blnIsCosenco = (getCodigoEmpresa()!=getCodigoEmpresaGrupo())?((getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false):false;   
        //blnIsCosenco = ((getNombreEmpresa().toUpperCase().indexOf("COSENCO") > -1)?true:false);   
        System.out.println("Cosenco: "+blnIsCosenco);
        return blnIsCosenco;
    }

}