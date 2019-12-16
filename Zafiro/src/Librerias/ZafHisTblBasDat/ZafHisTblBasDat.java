/*
 * ZafHisTblBasDat.java
 * 
 * Created on 25 de julio de 2017, 04:56 PM
 * v0.1
 */
package Librerias.ZafHisTblBasDat;
import Librerias.ZafUtil.ZafUtil;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Esta clase sirve para insertar registros en las tablas históricas.
 * Hay métodos para insertar registros de manera individual o masivamente.
 * @author Eddye Lino
 */
public class ZafHisTblBasDat
{
    //Variables generales.
    private ZafUtil objUti;
    private Statement stm;
    private ResultSet rst;
    private DatabaseMetaData dmd;
    private ResultSetMetaData rmd;
    private String strSQL;
    
    /**
     * Crea una nueva instancia de la clase ZafHisTblBasDat.
     * Esta clase se utiliza para insertar datos en las tablas históricas.
     */
    public ZafHisTblBasDat()
    {
        //Inicializar objetos.
        objUti=new ZafUtil();
    }
    
    /**
     * Esta función inserta 1 registro en la tabla histórica especificada.
     * <BR>Ejemplos: 
     * <UL>
     * <LI>Se inserta/modifica un item en "tbm_inv" y se hace 1 INSERT en "tbh_inv".
     * <LI>Se insertan/modifican 100 items en "tbm_inv" y se hace 100 INSERTs en "tbh_inv".
     * Hacer 100 inserciones no es eficiente. En esos casos es mejor utilizar la función "insertarHistoricoMasivo".
     * </UL>
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param strNomTbl El nombre de la tabla de donde se van a obtener los datos.
     * @param strNomTblHis El nombre de la tabla histórica.
     * @param strConSQL La condición SQL que sirve para obtener el registro a insertar. Incluye la palabra WHERE.
     * Ejemplo: Si se desea utilizar la tabla "tbm_inv" la condición sería "WHERE co_emp=1 AND co_itm=6603".
     * @return true: Si se pudo insertar el registro en la tabla histórica.
     * <BR>false: En el caso contrario.
     * @see #insertarHistoricoMasivo(java.sql.Connection, java.lang.String, java.lang.String, java.lang.String) 
     */
    public boolean insertarHistoricoIndividual(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL)
    {
        int intNumCam, j, intCodHis;
        String strLisCam="", strLisCamClaPri=""; //Listado de campos sin alias, Listado de campos clave primaria
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " " + strConSQL;
                rst=stm.executeQuery(strSQL);
                rmd=rst.getMetaData();
                intNumCam=rmd.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmd.getColumnName(j);
                }
                rst.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmd=con.getMetaData();
                rst=dmd.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rst.next())
                {
                    if (!strLisCamClaPri.equals(""))
                        strLisCamClaPri+=", ";
                    strLisCamClaPri+=rst.getString("COLUMN_NAME");
                }
                rst.close();
                //Obtener el código del histórico a utilizar.
                strSQL="SELECT MAX(co_his) AS co_his FROM " + strNomTblHis + " " + strConSQL + " GROUP BY " + strLisCamClaPri;
                rst=stm.executeQuery(strSQL);
                if (rst.next())
                    intCodHis=rst.getInt("co_his")+1;
                else
                    intCodHis=1;
                rst.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.
                strSQL="INSERT INTO " + strNomTblHis + "(co_his, " + strLisCam + ")";
                strSQL+=" SELECT " + intCodHis + " AS co_his, " + strLisCam + " FROM " + strNomTbl + " AS a1 " + strConSQL;
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
     * Esta función inserta 1 o más registros en la tabla histórica especificada.
     * Ejemplo: se insertan/actualizan 100 items en "tbm_inv", para no tener que hacer 100 INSERTs en "tbh_inv" se puede utilizar ésta función 1 sóla vez
     * indicando como condición la fecha y usuario de última modificación. De ésta forma se optimiza el proceso ya que en lugar de hacer 100 inserciones se hace 1 sola inserción.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param strNomTbl El nombre de la tabla de donde se van a obtener los datos.
     * @param strNomTblHis El nombre de la tabla histórica.
     * @param strConSQL La condición SQL que sirve para obtener los registros a insertar. Incluye la palabra WHERE y los campos con el alias "a1.".
     * Ejemplo: Si se desea utilizar la tabla "tbm_inv" la condición sería "WHERE a1.fe_ultMod='2017/08/15 14:27:36' AND a1.co_usrMod=1".
     * @return true: Si se pudieron insertar los registros en la tabla histórica.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarHistoricoMasivo(java.sql.Connection con, String strNomTbl, String strNomTblHis, String strConSQL)
    {
        int intNumCam, j;
        String strLisCam="", strLisCamClaPri="", strRelTbl=""; //Listado de campos sin alias, Listado de campos clave primaria, Relación entre las tablas.
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener los campos de la tabla.
                strSQL="SELECT * FROM " + strNomTbl + " AS a1 " + strConSQL;
                rst=stm.executeQuery(strSQL);
                rmd=rst.getMetaData();
                intNumCam=rmd.getColumnCount();
                for(j=1;j<=intNumCam;j++)
                {
                    if (!strLisCam.equals(""))
                        strLisCam+=", ";
                    strLisCam+=rmd.getColumnName(j);
                }
                rst.close();
                //Obtener los campos de la clave primaria de la tabla.
                dmd=con.getMetaData();
                rst=dmd.getPrimaryKeys(null, null, strNomTbl.toLowerCase());
                while (rst.next())
                {
                    if (!strLisCamClaPri.equals(""))
                    {
                        strLisCamClaPri+=", ";
                        strRelTbl+=" AND ";
                    }
                    strLisCamClaPri+="a1." + rst.getString("COLUMN_NAME");
                    strRelTbl+="a1." + rst.getString("COLUMN_NAME") + "=a2." + rst.getString("COLUMN_NAME");
                }
                rst.close();
                //Armar la sentencia SQL para insertar en la tabla histórica.
                strSQL="INSERT INTO " + strNomTblHis + "(co_his, " + strLisCam + ")";
                strSQL+=" SELECT (CASE WHEN MAX(a2.co_his) IS NULL THEN 1 ELSE MAX(a2.co_his)+1 END) AS co_his, a1.*";
                strSQL+=" FROM " + strNomTbl + " AS a1";
                strSQL+=" LEFT OUTER JOIN " + strNomTblHis + " AS a2 ON (" + strRelTbl + ")";
                strSQL+=" " + strConSQL;
                strSQL+=" GROUP BY " + strLisCamClaPri;
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
    
}
