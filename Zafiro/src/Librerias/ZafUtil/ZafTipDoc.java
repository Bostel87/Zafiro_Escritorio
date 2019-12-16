/*
 * ZafTipDoc.java
 *
 * Created on 25 de agosto de 2005, 12:53
 */

package Librerias.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.util.Vector;

/**
 * Esta clase tiene como objetivo cargar los datos de la base con respecto al Tipo de Documento
 * Verifica tipos de documento predeterminados por programa
 * El constructor recibe el objeto ZafParSis 
 * Contiene ademas de los datos de descripcion, las ctas. ctbles , ultimo numero
 * Estados del Documento para generacion de IVA, Descuentos, Pagos, Calcula Costo
 * Numero de Modulo
 * 
 * @author  jsalazar
 * v0.1 : Se actualizo propiedades con relacion a los nuevos campos de la base (numero modulo, estado calculo costeo)
 * v0.2 :
 * v0.4 : Se crean constructores para uso de devoluciones de venta automáticas. (28/Sep/2016)
 * v0.5 : Se corrige query por campo nuevo co_mnu en tbm_cabTipDoc
 */    
public class ZafTipDoc 
{
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private int co_tipdoc;
    private String tx_descor;
    private String tx_deslar;
    private int co_ctadeb;
    private int co_ctahab;
    private String tx_natdoc;
    private String tx_meringegrfisbod;
    private int ne_ultdoc;
    private int ne_numlin;
    private int ne_modulo;
    private String st_iva;
    private String st_des;
    private String st_genpag;
    private String st_costo;
    private Vector vecDet;
    private int ne_tipResModDoc;
    private int ne_tipResModFecDoc;
    //Constantes
    private final int INT_CTA_CTB = 0;
    private final int INT_NAT_CTA = 1;
    private final int INT_EST_REG = 2;


    /** Creates a new instance of ZafCom03_TipDoc */
    public ZafTipDoc(ZafParSis obj) {
        objZafParSis = obj;
        objUti = new ZafUtil();
        vecDet = new Vector();
        DocumentoPredeterminado();            
    }
    
    
     public ZafTipDoc(ZafParSis obj,int intVal) {
        objZafParSis = obj;
        objUti = new ZafUtil();
        vecDet = new Vector();
        DocumentoPredeterminado_usr();    
        DocumentoPredeterminadoUsr();
    }
    
    
    /**
     * Datos estandar por documento
     * @param: intTipo .-  Es el codigo del tipo de documento a cargar.(Numerico Entero)
     * @return : boolean true.- Si la cargar se hizo con exito
     *                   false .- Si tuvo algun error.
     */
    public boolean cargarTipoDoc(int intTipo)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        boolean blnRes = false;
        try{
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL = " select st_meringegrfisbod, co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod ";
                strSQL += " from tbm_cabtipdoc where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipo +" and st_reg='A'";
//                System.out.println(strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));
                    setst_meringegrfisbod(rst.getString("st_meringegrfisbod"));
                    blnRes=cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }
        }catch(java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }catch(Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
    
    
    /**
     * Datos estandar por documento
     * @param: intTipo .-  Es el codigo del tipo de documento a cargar.(Numerico Entero)
     * @param: blnfiltro.- true si solo quiere documentos en estado A y falso si los consulta todos
     * @return : boolean true.- Si la cargar se hizo con exito
     *                   false .- Si tuvo algun error.
     */
    public boolean cargarTipoDoc(int intTipo,boolean blnFiltro)
    {
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        boolean blnRes = false;
        try{
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();
                strSQL = "";
                strSQL = " select co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,ne_mod,st_cosunical ";
                strSQL += " from tbm_cabtipdoc where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipo;
                if (blnFiltro) strSQL +=" and st_reg='A'";
//                System.out.println(strSQL);
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));
                    blnRes=cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }
        }catch(java.sql.SQLException e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }catch(Exception e){
            blnRes = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    /**
    * Carga Tipo de documento predeterminado por programa
    * @return : boolean true.- Si la cargar se hizo con exito
    *                   false .- Si tuvo algun error.
    */
    public boolean DocumentoPredeterminado(int intcodmm)
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                         "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + intcodmm + " and docprg.st_reg = 'S'";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));                    
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }
        catch(java.sql.SQLException e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch(Exception e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    

    /**
     * Rose: Carga Tipo de documento predeterminado por programa
     * @param intCodEmp Código de Empresa
     * @param intCodLoc Código de Local
     * @param intCodMnu Código de Menú
     * @return 
     */
    public boolean DocumentoPredeterminado(int intCodEmp, int intCodLoc, int intCodMnu)
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                         "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                strSQL += " where docprg.co_emp = " + intCodEmp +" and docprg.co_loc = " +intCodLoc+" and docprg.co_mnu = " + intCodMnu + " and docprg.st_reg = 'S'";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));                    
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }
        catch(java.sql.SQLException e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch(Exception e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    
     
     
     
    public boolean DocumentoPredeterminado_usr()
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                
                if(objZafParSis.getCodigoUsuario()==1){
                strSQL = " select cabtip.st_meringegrfisbod, cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                         "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + " and docprg.st_reg = 'S'";
                }else{
                       strSQL = " select cabtip.st_meringegrfisbod, cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                         "  from tbr_tipDocUsr as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                       strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + " " +
                           " and docprg.co_usr="+objZafParSis.getCodigoUsuario()+" and docprg.st_reg = 'S'";
                      
                }
                // System.out.println(""+ strSQL );
                rst = stm.executeQuery(strSQL);
                if (rst.next())   
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical")); 
                    setst_meringegrfisbod(rst.getString("st_meringegrfisbod"));
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }catch(java.sql.SQLException e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    
    
    
    
    
    
    public boolean DocumentoPredeterminado()
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                strSQL = " select cabtip.st_meringegrfisbod, cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                         "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + " and docprg.st_reg = 'S'";
                rst = stm.executeQuery(strSQL);
                if (rst.next())
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));    
                    setst_meringegrfisbod(rst.getString("st_meringegrfisbod"));
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }catch(java.sql.SQLException e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    
    
    public boolean DocumentoPredeterminadoUsr()
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                             "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                    strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + " and docprg.st_reg = 'S'";
                    System.out.println("---DocumentoPredeterminadoUsr()---ADMIN--- "+ strSQL );
                }
                else
                {
                    strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod, docprg.ne_tipResModDoc, docprg.ne_tipResModFecDoc" +
                      "  from tbr_tipDocUsr as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                    strSQL += " where docprg.co_emp = " + objZafParSis.getCodigoEmpresa() +" and docprg.co_loc = " + objZafParSis.getCodigoLocal() +" and docprg.co_mnu = " + objZafParSis.getCodigoMenu() + " " +
                        " and docprg.co_usr="+objZafParSis.getCodigoUsuario()+" and docprg.st_reg = 'S'";
                    System.out.println("---DocumentoPredeterminadoUsr()---USR--- "+ strSQL );
                }
                // System.out.println(""+ strSQL );
                rst = stm.executeQuery(strSQL);
                if (rst.next())   
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));
                    
                    if(objZafParSis.getCodigoUsuario()!=1)
                    {
                        setne_tipResModDoc(rst.getInt("ne_tipResModDoc"));
                        setne_tipResModFecDoc(rst.getInt("ne_tipResModFecDoc"));
                    }
                    
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }
        catch(java.sql.SQLException e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch(Exception e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    
    /**
     * Rose: Carga Documento Predeterminado por Usuario
     * @param intCodEmp
     * @param intCodLoc
     * @param intCodMnu
     * @return 
     */
    public boolean DocumentoPredeterminadoUsr(int intCodEmp, int intCodLoc, int intCodMnu)
    {
        boolean blnRes = true;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try
        {
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                
                if(objZafParSis.getCodigoUsuario()==1)
                {
                    strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod" +
                             "  from tbr_tipdocprg as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                    strSQL += " where docprg.co_emp = " + intCodEmp +" and docprg.co_loc = " + intCodLoc +" and docprg.co_mnu = " + intCodMnu + " and docprg.st_reg = 'S'";
                    System.out.println("---DocumentoPredeterminadoUsr()---ADMIN--- "+ strSQL );
                }
                else
                {
                    strSQL = " select cabtip.co_tipdoc,tx_descor,tx_deslar,co_ctadeb,co_ctahab,tx_natdoc,ne_ultdoc,ne_numlin,st_iva,st_des,st_genpag,st_cosunical,ne_mod, docprg.ne_tipResModDoc, docprg.ne_tipResModFecDoc" +
                      "  from tbr_tipDocUsr as docprg left outer join tbm_cabtipdoc as cabtip on (docprg.co_emp = cabtip.co_emp and docprg.co_loc = cabtip.co_loc and docprg.co_tipdoc = cabtip.co_tipdoc)";
                    strSQL += " where docprg.co_emp = " + intCodEmp +" and docprg.co_loc = " + intCodLoc +" and docprg.co_mnu = " + intCodMnu + " " +
                        " and docprg.co_usr="+objZafParSis.getCodigoUsuario()+" and docprg.st_reg = 'S'";
                    System.out.println("---DocumentoPredeterminadoUsr()---USR--- "+ strSQL );
                }
                // System.out.println(""+ strSQL );
                rst = stm.executeQuery(strSQL);
                if (rst.next())   
                {
                    setco_tipdoc(rst.getInt("co_tipdoc"));
                    settx_descor(rst.getString("tx_descor"));
                    settx_deslar(rst.getString("tx_deslar"));
                    setco_ctadeb((rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb")));
                    setco_ctahab((rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab")));
                    settx_natdoc(rst.getString("tx_natdoc"));
                    setne_ultdoc(rst.getInt("ne_ultdoc"));   
                    setne_numlin(rst.getInt("ne_numlin"));
                    setst_iva(rst.getString("st_iva"));
                    setst_des(rst.getString("st_des"));
                    setst_genpag(rst.getString("st_genpag"));                    
                    setne_modulo(rst.getInt("ne_mod"));
                    setst_calculacosto(rst.getString("st_cosunical"));
                    
                    if(objZafParSis.getCodigoUsuario()!=1)
                    {
                        setne_tipResModDoc(rst.getInt("ne_tipResModDoc"));
                        setne_tipResModFecDoc(rst.getInt("ne_tipResModFecDoc"));
                    }
                    
                    blnRes = cargarDetTipDoc();
                }
                rst.close();
                stm.close();
                con.close();
            }            
        }
        catch(java.sql.SQLException e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        catch(Exception e) 
        {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    } 
    
    private boolean cargarDetTipDoc()
    {
        boolean blnRes;
        String strSQL;
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        try{
            con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
            if (con!=null)
            {
                stm = con.createStatement();                
                strSQL = "";
                strSQL = " Select co_cta,tx_ubicta,st_reg from tbm_dettipdoc " +
                         " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoLocal() + " and co_tipdoc=" + getco_tipdoc();
                rst = stm.executeQuery(strSQL);
                vecDet = new Vector();
                while(rst.next())
                {
                    Vector vecReg =  new Vector();
                    vecReg.add(INT_CTA_CTB, new Integer(rst.getInt("co_cta")));
                    vecReg.add(INT_NAT_CTA,rst.getString("tx_ubicta"));
                    vecReg.add(INT_EST_REG,rst.getString("st_reg"));
                    vecDet.add(vecReg);
                }
                rst.close();
                stm.close();
                con.close();                
            }
            blnRes=true;    
        }catch(java.sql.SQLException e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }catch(Exception e) {
            blnRes=false;    
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;

    }
    private boolean isEmpty(int intVal){
        if (String.valueOf(intVal)==null) return true;
        else return false;
    }
    private boolean isEmpty(String strVal){
        if (strVal==null) return true;
        else return false;        
    }
    
    private void setco_tipdoc(int intVal)
    {
        co_tipdoc = intVal;
    }
    private void settx_descor(String strVal)
    {
        tx_descor = strVal;
    }
    private void settx_deslar(String strVal)
    {
        tx_deslar = strVal;
    }
    private void setco_ctadeb(int intVal)
    {
        co_ctadeb = intVal;
    }
    private void setco_ctahab(int intVal)
    {
        co_ctahab = intVal;
    }
    private void settx_natdoc(String strVal)
    {
        tx_natdoc = strVal;
    }
    
    private void setst_meringegrfisbod(String strVal)
    {
        tx_meringegrfisbod = strVal;
    }
    
    
    public String getst_meringegrfisbod()
    {
        return (isEmpty(tx_meringegrfisbod))?"":tx_meringegrfisbod;
    }
      
    
            
    private void setne_ultdoc(int intVal)
    {
        ne_ultdoc = intVal;
    }
    private void setne_numlin(int intVal)
    {
        ne_numlin = intVal;
    }    
    private void setne_modulo(int intVal)
    {
        ne_modulo = intVal;
    }    

    private void setst_iva(String strVal)
    {
        st_iva = strVal;
    }
    private void setst_des(String strVal)
    {
        st_des = strVal;
    }
    private void setst_genpag(String strVal)
    {
        st_genpag = strVal;
    }
    private void setst_calculacosto(String strVal)
    {
        st_costo = strVal;
    }
    //Metodos de consulta
    public int getco_tipdoc()
    {
        return (isEmpty(co_tipdoc))?-1:co_tipdoc;
    }
    public String gettx_descor()
    {
        return (isEmpty(tx_descor))?"":tx_descor;
    }
    public String gettx_deslar()
    {
        return (isEmpty(tx_deslar))?"":tx_deslar;
    }
    public int getco_ctadeb()
    {
        return (isEmpty(co_ctadeb))?-1:co_ctadeb;
    }
    public int getco_ctahab()
    {
        return (isEmpty(co_ctahab))?-1:co_ctahab;
    }
    public String gettx_natdoc()
    {
        return (isEmpty(tx_natdoc))?"":tx_natdoc;
    }
    public int getne_ultdoc()
    {
        return (isEmpty(ne_ultdoc))?-1:ne_ultdoc;
    }
    public int getne_numlin()
    {
        return (isEmpty(ne_numlin))?-1:ne_numlin;
    }
    public int getne_modulo()
    {
        return (isEmpty(ne_modulo))?-1:ne_modulo;
    }
    
    public String getst_iva()
    {
        return (isEmpty(st_iva))?"":st_iva;
    }
    public String getst_des()
    {
        return (isEmpty(st_des))?"":st_des;
    }
    public String getst_genpag()
    {
        return (isEmpty(st_genpag))?"":st_genpag;
    }
    public String getst_calculacosto()
    {
        return (isEmpty(st_costo))?"":st_costo;
    }  
    
    //// Para validar en Tipo de Documento por usuario ////
    private void setne_tipResModDoc(int intVal)
    {
        ne_tipResModDoc = intVal;
    }
    public int getne_tipResModDoc()
    {
        return (isEmpty(ne_tipResModDoc))?-1:ne_tipResModDoc;
    }
    
    //// Para validar Fecha de modificacion ///
    private void setne_tipResModFecDoc(int intVal)
    {
        ne_tipResModFecDoc = intVal;
    }
    public int getne_tipResModFecDoc()
    {
        return (isEmpty(ne_tipResModFecDoc))?-1:ne_tipResModFecDoc;
    }
    
    //ne_tipResModFecDoc
    /**
     *<PRE>
     * Devuelve las cuenta contable asignadas a ese tipo de documento. (Vector)
     *</PRE>
     */
    public Vector getCtaDetalle(){
        return vecDet;
    }
    /**
     *<PRE>
     * Devuelve naturaleza de la cuenta predeterminada para la empresa (cadena)
     * Si no tiene cuenta predeterminada retorna null
     *</PRE>
     */
    public String getNaturalezaCtaPredeterminada(){
        if (isCtasDetalle()){        
            for (int i=0;i<vecDet.size();i++)
            {
                if (((java.util.Vector)vecDet.get(i)).get(INT_EST_REG).toString().equals("S"))
                {
                    return ((java.util.Vector)vecDet.get(i)).get(INT_NAT_CTA).toString();
                }
            }
        }
        return null;
    }
    /**
     *<PRE>
     * Devuelve la cuenta contable de la bodega prederminada para la empresa. (Entero)
     *</PRE>
     */        
    public int getCtaDetPredeterminada(){
        if (isCtasDetalle()){
            for (int i=0;i<vecDet.size();i++)
            {
                if (((java.util.Vector)vecDet.get(i)).get(INT_EST_REG).toString().equals("P"))
                {
                    return Integer.parseInt((((java.util.Vector)vecDet.get(i)).get(INT_CTA_CTB).toString()));
                }
            }
        }
        return 0;
    }
    /**
     * Metodo que verifica si tiene cuentas de detalle el tipo de documento cargado
     * @return : true.- Si tiene al menos una cuenta
     *           false.- Si no tiene cuentas de detalle.
     */
    public boolean isCtasDetalle()
    {
        if (vecDet==null || vecDet.size()<=0)
            return false;
        else
            return true;        
    }
}
