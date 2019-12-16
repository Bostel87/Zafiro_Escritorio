/*
 * ZafPerUsr.java
 *
 * Created on 7 de noviembre de 2004, 11:32 AM
 * v0.5
 */

package Librerias.ZafPerUsr;
import Librerias.ZafParSis.ZafParSis;
import java.util.ArrayList;
/**
 * Esta clase es utilizada para determinar las opciones a las que tiene acceso el usuario.
 * Las opciones a las que puede tener acceso el usuario son: 
 * <BR>
 * <UL>
 * <LI>Insertar
 * <LI>Consultar
 * <LI>Modificar
 * <LI>Eliminar
 * <LI>Anular
 * <LI>Imprimir
 * <LI>Vista preliminar
 * <LI>Aceptar
 * <LI>Cancelar
 * </UL>
 * Para determinar las opciones a las que tiene acceso el usuario debe recibir un objeto
 * de la clase ZafParSis. Este objeto le servirá para obtener la siguiente información:
 * <BR>
 * <UL>
 * <LI>Código de la empresa
 * <LI>Código del local
 * <LI>Código del usuario
 * <LI>Código del menú
 * </UL>
 * @author  Eddye Lino
 */
public class ZafPerUsr 
{
    private ZafParSis objParSis;
    private boolean blnIns;
    private boolean blnCon;
    private boolean blnMod;
    private boolean blnEli;
    private boolean blnAnu;
    private boolean blnImp;
    private boolean blnVis;
    private boolean blnAce;
    private boolean blnCan;
    private ArrayList arlOpcPer;                                //Opciones permitidas.
    
    /**
     * Crea una nueva instancia de la clase ZafPerUsr.
     * @param obj El objeto del que se va a obtener: código de la empresa, código del local,
     * código del usuario y código del menú.
     */
    public ZafPerUsr(ZafParSis obj)
    {
        //Inicializar objetos.
        objParSis=obj;
        arlOpcPer=new ArrayList();
        //Si es el usuario Administrador (Código=1) tiene acceso a todas las opciones.
        if (objParSis.getCodigoUsuario()==1)
        {
            blnIns=true;
            blnCon=true;
            blnMod=true;
            blnEli=true;
            blnAnu=true;
            blnImp=true;
            blnVis=true;
            blnAce=true;
            blnCan=true;
        }
        else
        {
            blnIns=false;
            blnCon=false;
            blnMod=false;
            blnEli=false;
            blnAnu=false;
            blnImp=false;
            blnVis=false;
            blnAce=false;
            blnCan=false;
            getPerUsr();
        }
    }
    
    /** Obtiene las opciones a las que tiene acceso el usuario. */
    private boolean getPerUsr()
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        boolean blnRes=true;
        try
        {
            con=java.sql.DriverManager.getConnection(objParSis.getStringConexion(),objParSis.getUsuarioBaseDatos(),objParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();
                strSQL="";
                strSQL+=" SELECT a1.co_mnu, a1.tx_tipMnu";
                strSQL+=" FROM tbm_mnuSis AS a1";
                strSQL+=" INNER JOIN tbr_perUsr AS a2 ON (a1.co_mnu=a2.co_mnu)";
                strSQL+=" WHERE a1.ne_pad=" + objParSis.getCodigoMenu();
                strSQL+=" AND a2.co_emp=" + objParSis.getCodigoEmpresa();
                strSQL+=" AND a2.co_loc=" + objParSis.getCodigoLocal();
                strSQL+=" AND a2.co_usr=" + objParSis.getCodigoUsuario();
                strSQL+=" ORDER BY a1.ne_ubi";
                rst=stm.executeQuery(strSQL);
                //Limpiar ArrayList de opciones permitidas.
                arlOpcPer.clear();
                while (rst.next())
                {
                    arlOpcPer.add(rst.getString("co_mnu"));
                    switch (rst.getInt("tx_tipMnu"))
                    {
                        case 1:
                            blnIns=true;
                            break;
                        case 2:
                            blnCon=true;
                            break;
                        case 3:
                            blnMod=true;
                            break;
                        case 4:
                            blnEli=true;
                            break;
                        case 5:
                            blnAnu=true;
                            break;
                        case 6:
                            blnImp=true;
                            break;
                        case 7:
                            blnVis=true;
                            break;
                        case 8:
                            blnAce=true;
                            break;
                        case 9:
                            blnCan=true;
                            break;
                    }
                }
                rst.close(); 
                stm.close();
                con.close();
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
        }
        catch (Exception e)
        {
            blnRes=false;
        }
        return blnRes;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Insertar".
     * @return true: Si tiene acceso a la opción "Insertar".
     * <BR>false: En el caso contrario.
     */
    public boolean isInsertarEnabled()
    {
        return blnIns;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Consultar".
     * @return true: Si tiene acceso a la opción "Consultar".
     * <BR>false: En el caso contrario.
     */
    public boolean isConsultarEnabled()
    {
        return blnCon;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Modificar".
     * @return true: Si tiene acceso a la opción "Modificar".
     * <BR>false: En el caso contrario.
     */
    public boolean isModificarEnabled()
    {
        return blnMod;
    }
    
    /**
     * Esta función determina si el usuario tiene acceso a la opción "Eliminar".
     * @return true: Si tiene acceso a la opción "Eliminar".
     * <BR>false: En el caso contrario.
     */    
    public boolean isEliminarEnabled()
    {
        return blnEli;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Anular".
     * @return true: Si tiene acceso a la opción "Anular".
     * <BR>false: En el caso contrario.
     */    
    public boolean isAnularEnabled()
    {
        return blnAnu;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Imprimir".
     * @return true: Si tiene acceso a la opción "Imprimir".
     * <BR>false: En el caso contrario.
     */    
    public boolean isImprimirEnabled()
    {
        return blnImp;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Vista preliminar".
     * @return true: Si tiene acceso a la opción "Vista preliminar".
     * <BR>false: En el caso contrario.
     */
    public boolean isVistaPreliminarEnabled()
    {
        return blnVis;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Aceptar".
     * @return true: Si tiene acceso a la opción "Aceptar".
     * <BR>false: En el caso contrario.
     */
    public boolean isAceptarEnabled()
    {
        return blnAce;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción "Cancelar".
     * @return true: Si tiene acceso a la opción "Cancelar".
     * <BR>false: En el caso contrario.
     */
    public boolean isCancelarEnabled()
    {
        return blnCan;
    }

    /**
     * Esta función determina si el usuario tiene acceso a la opción correpondiente al código de menú especificado.
     * @param codigoMenu El código del menú del que se desea conocer si tiene acceso o no.
     * @return true: Si el usuario tiene acceso a la opción correpondiente al código de menú especificado.
     * <BR>false: En el caso contrario.
     */
    public boolean isOpcionEnabled(int codigoMenu)
    {
        //Si es el usuario Administrador (Código=1) tiene acceso a todas las opciones.
        if (objParSis.getCodigoUsuario()==1)
            return true;
        else
            return arlOpcPer.contains("" + codigoMenu);
    }
  

}