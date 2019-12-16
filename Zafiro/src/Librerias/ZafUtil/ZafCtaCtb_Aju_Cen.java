/*
 * ZafCtaCtb.java
 *
 * Created on 8 de diciembre de 2004, 16:16   
 *Ver 2.0
 */
 
package Librerias.ZafUtil;
import Librerias.ZafUtil.ZafUtil;
/**
 * Clase que contienen todas las cuentas contables por empresa
 * @author : jsalazar
 * ultima version 16/feb/2006  
 */  
public class ZafCtaCtb_Aju_Cen 
{
         /*
         * Contiene true si es ke no se encontro todas las cuentas para saber si devolver null o no
         */ 
        private boolean isEmpty = true; 
        private boolean isEmpty2 = true; 
        private int CodEmpresa;
        private int CodLocal;
        /*
         * Datos las Cuentas
         */
        private int intCtaDescVentas;  // Descuento en Ventas
        private int intCtaDescCom;  // Descuento en Compras
        private int intCtaIvaVentas;  // Iva en Ventas
        private int intCtaIvaCompras;  //Iva en COMPRAS
        private int intCtaCostoVtas;  // Costo de Ventas
        private int intCtaRetFueCom;  // Retencion en la fuente
        private int intCtaRetIvaCom;  // Retencion de Iva 
        private int intCtaRetFueVta;  // Retencion en la fuente
        private int intCtaRetIvaVta;  // Retencion de Iva 
        private int intBanco;
        private int intCaja;
        private int intRes;
        private int intGas;
        private double dblPorIvaCom;
        private double dblPorIvaVen;        
        private ZafUtil objUti;
        private Librerias.ZafParSis.ZafParSis objZafParSis;
        private java.util.Vector vecCtaExi;        
        private final int INT_CODBOD = 0;
        private final int INT_CTABOD = 1;
        private final int INT_PREBOD = 2;
    
        private final int INT_NUMCTA = 3;
        private final int INT_NOMCTA = 4;
    
        private String strNumCtaIva;
        private String strNomCtaIva;

       
        private int intCtaCompras;  // Descuento en Ventas
        private String strNumCtaCom;
        private String strNomCtaCom;
        private int intCtaComprasAju;
        private String strNumCtaComAju;
        private String strNomCtaComAju;
        
        
        
    /** Creates a new instance of ZafCtaCtb */
    
    public ZafCtaCtb_Aju_Cen(Librerias.ZafParSis.ZafParSis obj,int intCodTipDoc, String tipCta)
    {
      try{
               isEmpty = true;
               objZafParSis  = (Librerias.ZafParSis.ZafParSis) obj.clone();
               CodEmpresa = objZafParSis.getCodigoEmpresa();
               CodLocal   = objZafParSis.getCodigoLocal();
               objUti = new ZafUtil();
               vecCtaExi = new java.util.Vector();
               cargarCtasEmpLoc();
               cargarCtasBodega();              
               cargarCtasEmp_Hab(intCodTipDoc);
               cargarCtasEmp_Deb(intCodTipDoc);
               
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
      }
               
    }
    
        
        
     public ZafCtaCtb_Aju_Cen(Librerias.ZafParSis.ZafParSis obj,int intCodTipDoc)
    {
      try{
               isEmpty = true;
               objZafParSis  = (Librerias.ZafParSis.ZafParSis) obj.clone();
               CodEmpresa = objZafParSis.getCodigoEmpresa();
               CodLocal   = objZafParSis.getCodigoLocal();
               objUti = new ZafUtil();
               vecCtaExi = new java.util.Vector();
               cargarCtasEmpLoc();
               cargarCtasBodega();              
               cargarCtasEmp_Hab(intCodTipDoc);
               cargarCtasEmp_Deb(intCodTipDoc);
               
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
      }
               
    }
        
    
    
    
    public ZafCtaCtb_Aju_Cen(Librerias.ZafParSis.ZafParSis obj)
    {  
      try{
               isEmpty = true;
               objZafParSis  = (Librerias.ZafParSis.ZafParSis) obj.clone();
               CodEmpresa = objZafParSis.getCodigoEmpresa();
               CodLocal   = objZafParSis.getCodigoLocal();
               objUti = new ZafUtil();
               vecCtaExi = new java.util.Vector();
               cargarCtasEmpLoc();
               cargarCtasBodega();
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
      }
               
    }
    
    /**
     * Metodo Polimorfico para poder obtener las cuentas de la empresa - local enviados
     */
    public ZafCtaCtb_Aju_Cen(Librerias.ZafParSis.ZafParSis obj, int Empresa, int Local){
      try{
               isEmpty = true;
               objZafParSis  = (Librerias.ZafParSis.ZafParSis) obj.clone();
               CodEmpresa = Empresa;
               CodLocal   = Local;
               objUti = new ZafUtil();
               vecCtaExi = new java.util.Vector();
               cargarCtasEmpLoc();
               cargarCtasBodega();
      }catch (CloneNotSupportedException e){
          objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
      }        
    }
    
    
    
     public void cargarCtasEmp_Hab(int intCodTipDoc){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
           stm = con.createStatement();
         
         String sql = "SELECT a.co_ctahab ,b.tx_codcta,b.tx_deslar FROM tbm_cabtipdoc as a" +
                      " inner join tbm_placta as b on (a.co_Emp=b.co_Emp and a.co_ctahab=b.co_cta)" +
                      " where a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.co_loc ="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc ="+intCodTipDoc+"  and a.st_reg='A'";
            rst = stm.executeQuery(sql);
           
          ///System.out.println(" QUERY PARA CODCTA DEL HABER " + sql );
           
              
            while (rst.next())
            {
              intCtaCompras = (rst.getString("co_ctahab")==null?0:rst.getInt("co_ctahab"));
              strNumCtaCom =  (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
              strNomCtaCom =  (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));
              isEmpty2 = false;
            }
           
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt); }
    } 
    
    
     
     
      public void cargarCtasEmp_Deb(int intCodTipDoc){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
           stm = con.createStatement();
         
         String sql = "SELECT a.co_ctadeb ,b.tx_codcta,b.tx_deslar FROM tbm_cabtipdoc as a" +
                      " inner join tbm_placta as b on (a.co_Emp=b.co_Emp and a.co_ctadeb=b.co_cta)" +
                      " where a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.co_loc ="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc ="+intCodTipDoc+"  and a.st_reg='A'";
            rst = stm.executeQuery(sql);
           
           ///System.out.println(" QUERY PARA CODCTA DEL DEBE " + sql );
             
                
            while (rst.next())
            {
              intCtaComprasAju = (rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb"));
              strNumCtaComAju =  (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
              strNomCtaComAju =  (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));
              isEmpty2 = false;
            }
           
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt); }
    } 
    
    
     
     
      public void cargarCtasEmpDevCom(int intCodTipDoc){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
           stm = con.createStatement();
         
         String sql = "SELECT a.co_ctadeb ,b.tx_codcta,b.tx_deslar FROM tbm_cabtipdoc as a" +
                      " inner join tbm_placta as b on (a.co_Emp=b.co_Emp and a.co_ctadeb=b.co_cta)" +
                      " where a.co_emp ="+objZafParSis.getCodigoEmpresa()+" and a.co_loc ="+objZafParSis.getCodigoLocal()+" and a.co_tipdoc ="+intCodTipDoc+"  and a.st_reg='A'";
            rst = stm.executeQuery(sql);
           
          // System.out.println(" CONEXION A BASE 22 " + sql );
           
            
            while (rst.next())
            {
              intCtaCompras = (rst.getString("co_ctadeb")==null?0:rst.getInt("co_ctadeb"));
              strNumCtaCom =  (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
              strNomCtaCom =  (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));
              isEmpty2 = false;
            }
           
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt); }
    } 
    
     
    /**
     *  <PRE> 
     *  Metodo que permite cargar datos sobre cuentas de existencia
     *  Almacena  el codigo de la bodega, la cuenta contable y el estado (si es PREDETERMINADO)
     *  Ademas los porcentajes de Iva en Compras y Ventas en un Vector</PRE>
     */
    public void cargarCtasBodega(){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst; 
        String strSQL;
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
           stm = con.createStatement();
          
           strSQL = "";
          // strSQL = " Select bod.co_bod,bodloc.st_reg ,co_ctaexi from tbm_emp as emp left outer join tbm_loc as loc on (emp.co_emp  = loc.co_emp) left outer join tbr_bodloc as bodloc on (loc.co_emp  = bodloc.co_emp and loc.co_loc = bodloc.co_loc) left outer join tbm_bod as bod on (bodloc.co_emp =bod.co_emp and bodloc.co_bod = bod.co_bod)";
           //strSQL += " where emp.co_emp = " + CodEmpresa + " and loc.co_loc = " + CodLocal + " and bod.st_reg='A' ";
 
           

       strSQL = " Select bod.co_bod,bodloc.st_reg , bod.co_ctaexi, pla.tx_codcta, pla.tx_deslar  from tbm_emp as emp left outer join tbm_loc as loc on (emp.co_emp  = loc.co_emp) ";
       strSQL += " left outer join tbr_bodloc as bodloc on (loc.co_emp  = bodloc.co_emp and loc.co_loc = bodloc.co_loc) ";   
       strSQL += " left outer join tbm_bod as bod on (bodloc.co_emp =bod.co_emp and bodloc.co_bod = bod.co_bod) ";
       strSQL += " inner join tbm_placta as pla on (emp.co_emp  = pla.co_emp and bod.co_ctaexi = pla.co_cta ) ";
       strSQL += " where emp.co_emp = " + CodEmpresa + " and loc.co_loc = " + CodLocal + " and bod.st_reg='A' ";
 

           
           
          // System.out.println(" CONEXION A BASE 7 " +strSQL );
           
           rst = stm.executeQuery(strSQL);
           vecCtaExi.clear();
           while (rst.next())
           {
               java.util.Vector vecReg = new java.util.Vector();
               vecReg.add(INT_CODBOD,new Integer(rst.getString("co_bod")));
               vecReg.add(INT_CTABOD,new Integer((rst.getString("co_ctaexi")==null?"0":rst.getString("co_ctaexi"))));
               vecReg.add(INT_PREBOD,rst.getString("st_reg"));
               
               vecReg.add(INT_NUMCTA,rst.getString("tx_codcta"));
               vecReg.add(INT_NOMCTA,rst.getString("tx_deslar"));
               
               
               vecCtaExi.add(vecReg);
           }
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){
             objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){
             objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }
    }
    /**
     * Carga las cuentas contables de la empresa, local
     * Ej:  Iva en compras, ventas, Gas, Banco...
     */
    public void cargarCtasEmpLoc(){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());           
           stm = con.createStatement();
           
           
           strSQL = "";
           strSQL = " Select emp.co_ctaivacom,co_ctaivaven,co_ctaban,co_ctacaj,co_ctares,co_ctagas,co_ctaretftecom,co_ctaretfteven,co_ctaretivacom,co_ctaretivaven,co_ctadescom,co_ctadesven,co_ctacosven,nd_ivacom,nd_ivaven, pla.tx_codcta,pla.tx_deslar from tbm_emp as emp left outer join tbm_loc as loc on (emp.co_emp  = loc.co_emp) ";
           strSQL += " inner join tbm_placta as pla on (emp.co_emp=pla.co_emp and  emp.co_ctaivacom=pla.co_cta)";
           strSQL += " where emp.co_emp = " + CodEmpresa + " and loc.co_loc = " + CodLocal;
         
           ///System.out.println(" CONEXION A BASE  " +strSQL );
           
           rst = stm.executeQuery(strSQL);
           while (rst.next())
           {
               intCtaIvaCompras = (rst.getString("co_ctaivacom")==null?0:rst.getInt("co_ctaivacom"));
               intCtaIvaVentas = (rst.getString("co_ctaivaven")==null?0:rst.getInt("co_ctaivaven"));
               intBanco = (rst.getString("co_ctaban")==null?0:rst.getInt("co_ctaban")); 
               intCaja  = (rst.getString("co_ctacaj")==null?0:rst.getInt("co_ctacaj")); 
               intRes   = (rst.getString("co_ctares")==null?0:rst.getInt("co_ctares")); 
               intGas   = (rst.getString("co_ctagas")==null?0:rst.getInt("co_ctagas"));
               intCtaCostoVtas = (rst.getString("co_ctacosven")==null?0:rst.getInt("co_ctacosven"));               
               intCtaRetFueCom = (rst.getString("co_ctaretftecom")==null?0:rst.getInt("co_ctaretftecom"));
               intCtaRetFueVta = (rst.getString("co_ctaretfteven")==null?0:rst.getInt("co_ctaretfteven"));
               intCtaRetIvaCom = (rst.getString("co_ctaretivacom")==null?0:rst.getInt("co_ctaretivacom")); 
               intCtaRetIvaVta = (rst.getString("co_ctaretivaven")==null?0:rst.getInt("co_ctaretivaven")); 
               intCtaDescVentas = (rst.getString("co_ctadesven")==null?0:rst.getInt("co_ctadesven"));
               intCtaDescCom = (rst.getString("co_ctadescom")==null?0:rst.getInt("co_ctadescom"));
               dblPorIvaCom  = (rst.getString("nd_ivacom")==null?0:rst.getDouble("nd_ivacom"));
               dblPorIvaVen  = (rst.getString("nd_ivaven")==null?0:rst.getDouble("nd_ivaven"));
               strNumCtaIva = (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));
               strNomCtaIva = (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));
               
               isEmpty = false;
           }
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){
             objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){
             objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }
    }


    ///*****************************************************PARA OBTENER NUMERO Y NOMBRE DE LA CUENTA 
    
       public String getNumCtaIva(){
            return (isEmpty)?"":strNumCtaIva;
        }
    
        public String getNomCtaIva(){
            return (isEmpty)?"":strNomCtaIva;
        }
        
        public int getCtaCompra (){
            return (isEmpty2)?-1:intCtaCompras;
        }
    
         public String getNumCtaCom(){
            return (isEmpty2)?"":strNumCtaCom;
        }
    
        public String getNomCtaCom(){
            return (isEmpty2)?"":strNomCtaCom;
        }
        
        
        public int getCtaCompraAju (){
            return (isEmpty2)?-1:intCtaComprasAju;
        }
        
        
          public String getNumCtaComAju(){
            return (isEmpty2)?"":strNumCtaComAju;
        }
    
        public String getNomCtaComAju(){
            return (isEmpty2)?"":strNomCtaComAju;
        }
        
    //********************************************************************************************
    
    
    
    
    
        public int getCtaDescVentas (){
            return (isEmpty)?-1:intCtaDescVentas;
        }
        public int getCtaIvaVentas(){
            return (isEmpty)?-1:intCtaIvaVentas;
        }
        public int getCtaIvaCompras(){
           //System.out.println(">>>>> "+isEmpty + " >>** " + intCtaIvaCompras );
            return (isEmpty)?-1:intCtaIvaCompras;
        }
        public double getPorcentajeIvaVentas(){
            return (isEmpty)?0:dblPorIvaVen;
        }
        public double getPorcentajeIvaCompras(){
            return (isEmpty)?0:dblPorIvaCom;
        }
        public int getCtaCostoVtas(){
            return (isEmpty)?-1:intCtaCostoVtas;
        }
        /**
         *<PRE>
         * Recibe el codigo de la bodega , 
         * Devuelve la cuenta contable. (Entero)
         *</PRE>
         */
        public int getCtaExistencia(int intCodBod){
            for (int i=0;i<vecCtaExi.size();i++)
            {
                if (intCodBod==Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CODBOD).toString())))
                {
                  // System.out.println("CUENTA ENCONTRADA.........");
                    return Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CTABOD).toString()));
                }
            }
            return 0;
        }

        
        
        
        
        
         public String getCtaNumExistencia(int intCodBod){
            for (int i=0;i<vecCtaExi.size();i++)
            {
                if (intCodBod==Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CODBOD).toString())))
                {
                  // System.out.println("CUENTA ENCONTRADA.........");
                    return (((java.util.Vector)vecCtaExi.get(i)).get(INT_NUMCTA).toString());
                }
            }
            return "";
        }
        
        
         public String getCtaNomExistencia(int intCodBod){
            for (int i=0;i<vecCtaExi.size();i++)
            {
                if (intCodBod==Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CODBOD).toString())))
                {
                  // System.out.println("CUENTA ENCONTRADA.........");
                    return (((java.util.Vector)vecCtaExi.get(i)).get(INT_NOMCTA).toString());
                }
            }
            return "";
        }
        
        
        
        
        
        /**
         * Regresa el vector con las bodegas de la empresa
         * Posicion 0 : codigo de bodega
         *          1 : cuenta contable de la bodega
         *          2 : estado bodega predeterminada S/N  
         */
        public java.util.Vector getBodegaLocal(){
            return vecCtaExi;
        }
        
        /**
         *<PRE>
         * Devuelve el codigo de la bodega predeterminada para la empresa (Entero)
         *</PRE>
         */
        public int getBodPredeterminada(){
            for (int i=0;i<vecCtaExi.size();i++)
            {
                if (((java.util.Vector)vecCtaExi.get(i)).get(INT_PREBOD).toString().equals("P"))
                {
                    return Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CODBOD).toString()));
                }
            }
            return 0;
        }
        /**
         *<PRE>
         * Devuelve la cuenta contable de la bodega prederminada para la empresa. (Entero)
         *</PRE>
         */        
        public int getCtaBodPredeterminada(){
            for (int i=0;i<vecCtaExi.size();i++)
            {
                if (((java.util.Vector)vecCtaExi.get(i)).get(INT_PREBOD).toString().equals("P"))
                {
                    return Integer.parseInt((((java.util.Vector)vecCtaExi.get(i)).get(INT_CTABOD).toString()));
                }
            }
            return 0;
        }
        public int getCtaRetFueVta(){
            return (isEmpty)?-1:intCtaRetFueVta;
        }
        public int getCtaRetFueCom(){
            return (isEmpty)?-1:intCtaRetFueVta;
        }        
        public int getCtaRetIvaVta(){
            return (isEmpty)?-1:intCtaRetIvaVta;
        }
        public int getCtaRetIvaCom(){
            return (isEmpty)?-1:intCtaRetIvaCom;
        }   
        /**
         *<PRE>
         * Recibe el codigo de la cuenta contable 
         * Devuelve la descripcion de la cuenta segun el plan de cuentas de la empresa. (cadena)
         * En caso de Error regresa: null
         *</PRE>
         */        
        public String getNomCta(int intCodCta){     
            String strResp=null;
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
             
                  try{
                      if (con!=null)
                      {
                          String strSql = "select tx_deslar from tbm_placta where " +
                                          "co_emp = " + CodEmpresa  + " and " +
                                          "co_cta = " + intCodCta ;
                          
                         //  System.out.println(" CONEXION A BASE 2 " +strSql );
                          
                          java.sql.Statement stm = con.createStatement();
                          java.sql.ResultSet rst = stm.executeQuery(strSql);
                          if(rst.next()){
                            strResp= rst.getString("tx_deslar");  
                          }
                          rst.close();
                          stm.close();
                          con.close();
                      }
                 }catch(java.sql.SQLException e){
                      strResp= "[Error]";
                      con.close();
                      objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
                  }
             }catch(Exception e){
                  strResp= "[Error]";
                  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
              }
           return strResp;
        }
        /**   
         *<PRE>  
         * Recibe el codigo de la cuenta contable 
         * Devuelve el numero contable segun el plan de cuentas de la empresa. (cadena)
         * En caso de Error regresa: null
         *</PRE>
         */        
        public String getNumCtaCtb(int intCodCta){            
            String strResp=null;
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                
                  try{
                      if (con!=null)
                      {
                          String strSql = "select tx_codcta from tbm_placta where " +
                                          "co_emp = " + CodEmpresa  + " and " +
                                          "co_cta = " + intCodCta ;
                           
                       //   System.out.println(" CONEXION A BASE 3 "+ strSql );
                           
                          java.sql.Statement stm = con.createStatement();
    //                      System.out.println(strSql);
                          java.sql.ResultSet rst = stm.executeQuery(strSql);
                          if(rst.next()){
                            strResp= rst.getString("tx_codcta");  
                          }
                          rst.close();
                          stm.close();
                          con.close();
                      }
                 }catch(java.sql.SQLException e){
                      strResp= "[Error]";
                      con.close();
                      objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
                  }
             }catch(Exception e){
                  strResp= "[Error]";
                  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
             }
           return strResp;
        }        
/**
         *<PRE>
         * Recibe el codigo de la cuenta contable 
         * Devuelve la descripcion de la cuenta segun el plan de cuentas de la empresa. (cadena)
         * En caso de Error regresa: null
         *</PRE>
         */        
        public String getNomCta(int intCodEmp,int intCodCta){     
            String strResp=null;
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
              //  System.out.println(" CONEXION A BASE 4");
                  try{
                      if (con!=null)
                      {
                          String strSql = "select tx_deslar from tbm_placta where " +
                                          "co_emp = " + intCodEmp  + " and " +
                                          "co_cta = " + intCodCta ;
                          java.sql.Statement stm = con.createStatement();
                          java.sql.ResultSet rst = stm.executeQuery(strSql);
                          if(rst.next()){
                            strResp= rst.getString("tx_deslar");  
                          }
                          rst.close();
                          stm.close();
                          con.close();
                      }
                 }catch(java.sql.SQLException e){
                      strResp= "[Error]";
                      con.close();
                      objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
                  }
             }catch(Exception e){
                  strResp= "[Error]";
                  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
              }
           return strResp;
        }
        /**
         *<PRE>
         * Recibe el codigo de la cuenta contable 
         * Devuelve el numero contable segun el plan de cuentas de la empresa. (cadena)
         * En caso de Error regresa: null
         *</PRE>
         */        
        public String getNumCtaCtb(int intCodEmp ,int intCodCta){            
            String strResp=null;
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               // System.out.println(" CONEXION A BASE 5");
                  try{
                      if (con!=null)
                      {
                          String strSql = "select tx_codcta from tbm_placta where " +
                                          "co_emp = " + intCodEmp  + " and " +
                                          "co_cta = " + intCodCta ;
                          java.sql.Statement stm = con.createStatement();
    //                      System.out.println(strSql);
                          java.sql.ResultSet rst = stm.executeQuery(strSql);
                          if(rst.next()){
                            strResp= rst.getString("tx_codcta");  
                          }
                          rst.close();
                          stm.close();
                          con.close();
                      }
                 }catch(java.sql.SQLException e){
                      strResp= "[Error]";
                      con.close();
                      objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
                  }
             }catch(Exception e){
                  strResp= "[Error]";
                  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
             }
           return strResp;
        }        
        /**<pre>
         * Busca el codigo maestro de equivalencia segun empresa y codigo de cuenta recibidos.
         * Regresa el codigo si lo encuentra o cero sino lo encuentra.</pre>
         */
        public int buscarCodMaestroCtaCtb(int intparEmp, int intparCta){
            int intCodRes =0;
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
             //   System.out.println(" CONEXION A BASE 6");
                try{
                    if (con!=null){
                        java.sql.Statement stm = con.createStatement();
                        String strSQL = "";
                        strSQL = " Select co_ctamae from tbm_equplacta where co_emp =  " + intparEmp +" and co_cta = " + intparCta;
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if (rst.next()){
                            intCodRes = rst.getInt("co_ctamae");
                        }
                        rst.close();
                        stm.close();
                        con.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
                }
            }catch(Exception e){
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return intCodRes;
        }
}
