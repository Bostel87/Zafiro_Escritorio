/*
 * ZafRepInv_04.java
 *
 * Created on 26 de julio de 2005, 9:18
 */

package Librerias.ZafRepInv;
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
 * @author  jsalazar
 */
public class ZafRepInv_04 {
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private int intCodDia;
    private String strGlo; //Glosa
    private Vector vecRegDia;
    
    //Conexion
    private Connection con;
    private Statement stm;
    private ResultSet rst;   
    private String strSQL; 
    // Variables para sentencias insert, update y delete
    private java.sql.PreparedStatement pstDia;
        
    
    /** Creates a new instance of ZafRepInv_04 
     *   Creada para generarcion de Asiento de Diario automatico.     
     */
    public ZafRepInv_04(ZafParSis obj) {
        objZafParSis = obj;
        objUti    = new ZafUtil();
        vecRegDia = new Vector();
    }
  /**
     * Esta función obtiene la glosa del diario.
     * @return La glosa del diario.
     */
    public String getGlosa()
    {
        return strGlo;
    }
    
    /**
     * Esta función establece la glosa del diario.
     * @param glosa La glosa del diario.
     */
    public void setGlosa(String glosa)
    {
        strGlo=glosa;
    }
    
    /**
     * Esta función inserta el asiento de diario en la base de datos. 
     * @param conexion El objeto que contiene la conexión a la base de datos.
     * @param empresa El código de la empresa.
     * @param local El código del local.
     * @param tipoDocumento El código del tipo de documento.
     * @param codigodiario: Para diario de documentos ya existentes en caso de reposicion no genera nuevo diario sino usa existente
     * @return true: Si se pudo insertar el asiento de diario.
     * <BR>false: En el caso contrario.
     */
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento,int intCodDia,java.util.Date Fecha)
    {
        if (insertarCab(conexion, empresa, local, tipoDocumento, String.valueOf(intCodDia), Fecha))
            if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                return true;            
        return false;
    }
    public boolean insertarDiario(java.sql.Connection conexion, int empresa, int local, int tipoDocumento,int intCodDia)
    {
        if (insertarDet(conexion, empresa, local, tipoDocumento, intCodDia))
                return true;            
        return false;
    }
 
    /**
     * Esta función inserta la cabecera del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param strNumDia El número de diario. Por lo general es el número preimpreso.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> la función obtendrá el número de diario de la base de datos.
     * <LI>Si el parámetro recibido es diferente a <I>null</I> utilizará el número de diario recibido.
     * </UL>
     * @param datFecDia La fecha del diario.
     * <BR>Se pueden presentar los siguientes casos:
     * <UL>
     * <LI>Si el parámetro recibido es <I>null</I> la función obtendrá la fecha del servidor de base de datos.
     * <LI>Si el parámetro recibido es diferente a <I>null</I> utilizará la fecha recibida.
     * </UL>
     * @return true: Si se pudo insertar la cabecera del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarCab(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, String strNumDia, java.util.Date datFecDia)
    {
        int intCodUsr, intNumDia;
        java.util.Date datFecSer;
        boolean blnRes=true;
        try
        {
            intCodUsr=objZafParSis.getCodigoUsuario();
            if (con!=null)
            {
                stm=con.createStatement();
                //Obtener el código del último registro.
                if (strNumDia==null)
                {
                    strSQL="";
                    strSQL+="SELECT MAX(a1.co_dia)";
                    strSQL+=" FROM tbm_cabDia AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intCodDia=objUti.getNumeroRegistro(new javax.swing.JPanel(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
                    if (intCodDia==-1)
                        return false;
                    intCodDia++;
                }else
                    intCodDia = Integer.parseInt(strNumDia);
                if (strNumDia==null)
                {
                    //Obtener el número de diario.
                    strSQL="";
                    strSQL+="SELECT a1.ne_ultDoc";
                    strSQL+=" FROM tbm_cabTipDoc AS a1";
                    strSQL+=" WHERE a1.co_emp=" + intCodEmp;
                    strSQL+=" AND a1.co_loc=" + intCodLoc;
                    strSQL+=" AND a1.co_tipDoc=" + intTipDoc;
                    intNumDia=objUti.getNumeroRegistro(new javax.swing.JPanel(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL);
                    if (intNumDia==-1)
                        return false;
                    intNumDia++;
                    strNumDia="" + intNumDia;
                }
                //Obtener la fecha del servidor.
                datFecSer=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                if (datFecSer==null)
                    return false;
                //Armar la sentencia SQL.
                strSQL="";
                strSQL+="INSERT INTO tbm_cabDia (co_emp, co_loc, co_tipDoc, co_dia,  fe_dia, tx_glo";
                strSQL+=", st_reg, fe_ing, fe_ultMod, co_usrIng, co_usrMod)";
                strSQL+=" VALUES (";
                strSQL+="" + intCodEmp;
                strSQL+=", " + intCodLoc;
                strSQL+=", " + intTipDoc;
                strSQL+=", " + intCodDia;
                //Si la fecha recibida es null asigno la fecha del servidor.
                if (datFecDia==null)
                    strSQL+=", '" + objUti.formatearFecha(datFecSer, objZafParSis.getFormatoFechaBaseDatos()) + "'";
                else
                    strSQL+=", '" + objUti.formatearFecha(datFecDia, objZafParSis.getFormatoFechaBaseDatos()) + "'";
                strSQL+=", " + objUti.codificar(strGlo);
                strSQL+=", 'A'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", '" + objUti.formatearFecha(datFecSer, objZafParSis.getFormatoFechaHoraBaseDatos()) + "'";
                strSQL+=", " + intCodUsr;
                strSQL+=", " + intCodUsr;
                strSQL+=")";
                System.out.println("SQL:"+strSQL);
                pstDia = con.prepareStatement(strSQL);
                pstDia.executeUpdate(strSQL);
                stm.close();                
                datFecSer=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JPanel(), e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JPanel(), e);
        }
        return blnRes;
    }
    
    /**
     * Esta función inserta el detalle del registro.
     * @param con El objeto que contiene la conexión a la base de datos.
     * @param intCodEmp El código de la empresa.
     * @param intCodLoc El código del local.
     * @param intTipDoc El código del tipo de documento.
     * @param intCodDia El código del diario.
     * @return true: Si se pudo insertar el detalle del registro.
     * <BR>false: En el caso contrario.
     */
    private boolean insertarDet(java.sql.Connection con, int intCodEmp, int intCodLoc, int intTipDoc, int intCodDia)
    {
        int i,Ind=1;
        boolean blnRes=true;
        try
        {
            if (con!=null)
            {
                stm=con.createStatement();
                strSQL="";
                strSQL=" Select max(co_reg) as co_reg from tbm_detdia where co_emp = "+ intCodEmp + " and co_loc= "+ intCodLoc+"and co_tipdoc= "+ intTipDoc +" and co_dia="+ intCodDia;
                System.out.println("SQL:"+strSQL);
                if (objUti.getNumeroRegistro(new javax.swing.JPanel(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), strSQL)>0)
                {    
                    rst = stm.executeQuery(strSQL);
                    if (rst.next()) Ind = Integer.parseInt(rst.getString("co_reg"))+1;
                    rst.close();
                }
                for (i=0;i<vecRegDia.size();i++)
                {
                    ZafRegDia objTmp = (ZafRegDia) vecRegDia.get(i);
                    //Armar la sentencia SQL.
                    strSQL="";
                    strSQL+="INSERT INTO tbm_detDia (co_emp, co_loc, co_tipDoc, co_dia, co_reg, co_cta "; 
                    if (objTmp.strTipCta.equals("D"))
                    {
                        strSQL+=", nd_monDeb)";
                    }else{
                        strSQL+=", nd_monHab)";
                    }

                    strSQL+=" VALUES (";
                    strSQL+="" + intCodEmp;
                    strSQL+=", " + intCodLoc;
                    strSQL+=", " + intTipDoc;
                    strSQL+=", " + intCodDia;
                    strSQL+=", " + (i+Ind);
                    strSQL+=", " + objTmp.intCodCta;
                    strSQL+=", " + objUti.redondeo(objTmp.dblValor, 3);
                    strSQL+=")";
                    System.out.println("SQL:"+strSQL);
                    pstDia = con.prepareStatement(strSQL);
                    pstDia.executeUpdate(strSQL);
                }
                stm.close();
                stm=null;
            }
        }
        catch (java.sql.SQLException e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JPanel(), e);
        }
        catch (Exception e)
        {
            blnRes=false;
            objUti.mostrarMsgErr_F1(new javax.swing.JPanel(), e);
        }
        return blnRes;
    }
    
    
    public boolean AddDetalle(int intparCodCta, double dblparValor, String strparTipCta)
    {
        if (vecRegDia!=null) {
            vecRegDia.add(new ZafRepInv_04.ZafRegDia(intparCodCta,dblparValor,strparTipCta));
            return true;
        }
        return false;
    }
    
    /**
     * Clase interna para creacion de registros automaticos del diario.
     * Recibe parametros :
     * @param: int Codigo de Cuenta.
     * @param: double Valor del Registro.
     * @param: string Tipo de la cuenta D/H
     */
    private class ZafRegDia
    {
        private int intCodCta;
        private double dblValor;
        private String strTipCta;
        public ZafRegDia(int intparCodCta, double dblparValor, String strparTipCta)
        {
            intCodCta = intparCodCta;
            dblValor = dblparValor;
            strTipCta = strparTipCta;
        }
    }
}
