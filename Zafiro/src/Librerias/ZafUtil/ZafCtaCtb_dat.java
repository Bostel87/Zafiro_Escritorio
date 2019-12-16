/*
 * ZafCtaCtb.java
 *
 * Created on 8 de diciembre de 2004, 16:16
 *Version 2.1
 */

package Librerias.ZafUtil;

/**
 *
 * @author  IdiTrix
 */
public class ZafCtaCtb_dat {
         /*
         * Contiene true si es ke no se encontro todas las cuentas para saber si devolver null o no
         */ 
        private boolean isEmpty = true; 
        /*
         * Datos las Cuentas
         */
        private int intCtaHab    = 0;  // CLientes        
        private int intCtaDescVentas  = 0;  // Descuento en Ventas
        private int intCtaDeb      = 0;  // Ventas
        private int intCtaIvaVentas   = 0;  // Iva en Ventas
        private int intCtaIvaCompras  = 0;  //Iva en COMPRAS
        private int intCtaCostoVtas   = 0;  // Costo de Ventas
        private int intCtaExistencia  = 0;  // Existencias en Bodega
        private int intCtaRetFue      = 0;  // Retencion en la fuente
        private int intCtaRetIva      = 0;  // Retencion de Iva 
        
        private Double dblIvaVen=0.00;  /* JM 15/Sep/2016 */
        private Double dblIvaCom=0.00;
        
        private Double dblComSol=0.00;  /* JM 18/Ene/2017 */
        
        public String strVersion = "v 0.01.06";  
        
        private String tx_codctadeb  = "";  
        private String tx_deslardeb  = "";  
        
        private String tx_codctades  = "";  
        private String tx_deslardes  = "";  
        
        private String tx_codctahab  = "";  
        private String tx_deslarhab  = "";  
        
        private String tx_codctaivaven  = "";  
        private String tx_deslarivaven  = "";  
        
        /*
         * Variables Sql
         */
        
        private java.sql.Connection conCta;  //Coneccion a la base donde se encuentra el cliente
        
        private java.sql.Statement stmCtaEmp;   //Statement para la sacar las cuentas de tabla empresas
        private java.sql.ResultSet rstCtaEmp;     //Resultset que tendra los datos 

        private java.sql.Statement stmCtaLoc;   //Statement para la sacar las cuentas de tabla Local
        private java.sql.ResultSet rstCtaLoc;   //Resultset que tendra los datos 

        private java.sql.Statement stmCtaBod;   //Statement para la sacar las cuentas de tabla Bodega
        private java.sql.ResultSet rstCtaBod;   //Resultset que tendra los datos 

        private java.sql.Statement stmCtaTipDoc; //Statement para la sacar las cuentas de tabla Tipos Documentoes
        private java.sql.ResultSet rstCtaTipDoc; //Resultset que tendra los datos
        
        private java.sql.Statement stmCta;   //Statement para obtener numero de cuenta y nombre
        private java.sql.ResultSet rstCta;   //Resultset que tendra los datos 
        
        private Librerias.ZafParSis.ZafParSis objZafParSis;
        private ZafUtil objUti;
        private java.util.Date datFecAux;
    
    /** Creates a new instance of ZafCtaCtb */
        public ZafCtaCtb_dat(Librerias.ZafParSis.ZafParSis objZafParSis, int intCoTipDoc) {
               isEmpty = true;
               this.objZafParSis = objZafParSis;
               try{
                   conCta=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   /*
                    *  String para el hacer el query en la tabla de Empresas Local Bodega y Ventas y obtener 
                    *  sus CUentas .
                    */
               
                    String strSqlEmp = "select * from tbm_emp where " +
                                        "co_emp = " + objZafParSis.getCodigoEmpresa();
                    
                    //System.out.println("CUENTA EMPRESA "+ strSqlEmp );
                    
                    String strSqlLoc = "select * from tbm_loc where " +
                                        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "co_loc = " + objZafParSis.getCodigoLocal();

                     //System.out.println("CUENTA LOCAL "+ strSqlLoc );
                     
                    String strSqlBod = "select * from tbm_bod where " +
                                        "co_bod = " + objZafParSis.getCodigoLocal();

                    ///System.out.println("CUENTA BODEGA "+ strSqlBod );
                     
                    String strSqlTipDoc = "select * from tbm_cabtipdoc where " +
                                        "co_emp = " + objZafParSis.getCodigoEmpresa() + " and " +
                                        "co_loc = " + objZafParSis.getCodigoLocal()   + " and " +
                                        "co_tipdoc=" + intCoTipDoc;  

 
                     //System.out.println("CUENTA TIPODOCUMENTO "+ strSqlTipDoc );
                    
                    
                    stmCtaEmp = conCta.createStatement();
                    rstCtaEmp = stmCtaEmp.executeQuery(strSqlEmp);

                    stmCtaBod = conCta.createStatement();
                    rstCtaBod = stmCtaBod.executeQuery(strSqlBod);
                    
                    stmCtaLoc = conCta.createStatement();
                    rstCtaLoc = stmCtaLoc.executeQuery(strSqlLoc);
                    
                    stmCtaTipDoc = conCta.createStatement();
                    rstCtaTipDoc = stmCtaTipDoc.executeQuery(strSqlTipDoc);
                    
                    /*
                     * Significa ke encontro datos de empresa, bodega , local, TipDoc
                     */
                    if(rstCtaEmp.next() && rstCtaLoc.next() && rstCtaBod.next() && rstCtaTipDoc.next()){
                        // Si todos trajeron algun registro significa ke ya no esta vacia la clase
                        isEmpty = false;
                        
                        /*
                         * Procedemos a cargar las cuentas
                         */
                        
                         intCtaHab    = rstCtaTipDoc.getInt("co_ctahab");  // CLientes 
                         intCtaDescVentas  = rstCtaLoc.getInt("co_ctadesven");  // Descuento en Ventas
                         intCtaDeb      = rstCtaTipDoc.getInt("co_ctadeb");  // Ventas
                         intCtaIvaVentas   = rstCtaEmp.getInt("co_ctaivaven");  // Iva en Ventas
                         intCtaIvaCompras   = rstCtaEmp.getInt("co_ctaivacom");  // Iva en Ventas
                         intCtaCostoVtas   = rstCtaLoc.getInt("co_ctacosven");  // Costo de Ventas
                         intCtaExistencia  = rstCtaBod.getInt("co_ctaexi");  // Existencias en Bodega
                         intCtaRetFue      = rstCtaEmp.getInt("co_ctaretfteven");  // Retencion en la fuente
                         intCtaRetIva      = rstCtaEmp.getInt("co_ctaretivaven");  // Retencion de Iva 
                         
                         String sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                          "co_cta = " + intCtaDeb;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctadeb=rstCta.getString(1);
                             tx_deslardeb=rstCta.getString(2);
                          }
                         
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                          "co_cta = " + intCtaDescVentas;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctades=rstCta.getString(1);
                             tx_deslardes=rstCta.getString(2);
                          }
                          
                          
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                          "co_cta = " + intCtaHab;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctahab=rstCta.getString(1);
                             tx_deslarhab=rstCta.getString(2);
                          }
                          
                           sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                          "co_cta = " + intCtaIvaVentas;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctaivaven=rstCta.getString(1);
                             tx_deslarivaven=rstCta.getString(2);
                          }
                         
                    }
                     

                    rstCta.close();
                    rstCtaEmp.close();
                    rstCtaLoc.close();
                    rstCtaBod.close();
                    rstCtaTipDoc.close();
                    
                    stmCta.close();
                    stmCtaEmp.close();
                    stmCtaLoc.close();
                    stmCtaBod.close();
                    stmCtaTipDoc.close();
                    
                    conCta.close();
                    conCta = null;
               }
               catch(java.sql.SQLException Evt){
                     isEmpty = true;
               }

               catch(Exception Evt){
                     isEmpty = true;
               }
    }
    
        
        private static final int INT_ARL_COD_CFG_IVA=2;
        private static final int INT_ARL_COD_CFG_IVA_COM=1;  
        private static final int INT_ARL_COD_CFG_IVA_VEN=2;
        private static final int INT_ARL_COD_CFG_COM_SOL=3;
        
        private String strNumCtaIva,strNomCtaIva;
        
        /* 6/Mayo/2016 JoséMario Marín  */
        public ZafCtaCtb_dat(Librerias.ZafParSis.ZafParSis objZafParSis,int intCodEmp, int intCodLoc, int intCoTipDoc) {
               isEmpty = true;
               objUti=new ZafUtil();
               java.sql.Statement stmLocIvaVen,stmLocIvaCom,stmComSol;
               java.sql.ResultSet rstLocIvaVen,rstLocIvaCom,rstComSol;
               this.objZafParSis = objZafParSis;
               try{
                   System.out.println("ZafCtaCtb_dat " + strVersion);
                   
                    datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
                
                    conCta=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   /*
                    *  String para el hacer el query en la tabla de Empresas Local Bodega y Ventas y obtener 
                    *  sus CUentas .
                    */
                   
                    String strSqlEmp = "select * from tbm_emp where " +
                                        "co_emp = " + intCodEmp;
                    
                    System.out.println("CUENTA EMPRESA "+ strSqlEmp );
                    
                    String strSqlLoc = "select * from tbm_loc where " +
                                        "co_emp = " + intCodEmp + " and " +
                                        "co_loc = " +intCodLoc;

                     System.out.println("CUENTA LOCAL "+ strSqlLoc );
                     
//                    String strSqlBod = "select * from tbm_bod where " +
//                                        "co_bod = " +intCodLoc;
                    String strSqlBod =" SELECT * "
                            +         " FROM tbm_bod as a1  "
                            +         " INNER JOIN tbr_bodLoc as a2 ON (a1.co_emp=a2.co_emp AND a1.co_bod=a2.co_bod) "
                            +         " WHERE a2.co_emp="+intCodEmp+" and a2.co_loc="+intCodLoc+" AND a2.st_reg='P' ";
                     System.out.println("CUENTA BODEGA "+ strSqlBod );
                     
                    String strSqlTipDoc = "select * from tbm_cabtipdoc where " +
                                        " co_emp = " + intCodEmp + " and " +
                                        " co_loc = " +intCodLoc+ " and " +
                                        " co_tipdoc=" + intCoTipDoc;  
                     System.out.println("CUENTA TIPODOCUMENTO "+ strSqlTipDoc );
                    
                    String strCtaIvaVen = "";
                    strCtaIvaVen+=" select co_cta as co_ctaIvaVen,nd_porImp as nd_porImpIvaVen from tbm_cfgImpLoc ";
                    strCtaIvaVen+=" where co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND st_reg='A' ";
                    strCtaIvaVen+=" AND co_imp="+INT_ARL_COD_CFG_IVA_VEN+" ";
                    strCtaIvaVen+=" AND (CASE WHEN fe_vigHas IS NULL THEN '"+datFecAux+"'>=fe_vigDes ELSE '"+datFecAux+"'>=fe_vigDes AND '"+datFecAux+"'<=fe_vigHas END) ";
                    System.out.println("strCtaIvaVen " + strCtaIvaVen);
                    
                    String strCtaIvaCom = "";
                    strCtaIvaCom+=" SELECT a1.co_cta as co_ctaIvaCom,a1.nd_porImp as nd_porImpIvaCom, a2.tx_codCta, a2.tx_desLar as txNomCta   ";
                    strCtaIvaCom+=" FROM tbm_cfgImpLoc as a1";
                    strCtaIvaCom+=" INNER JOIN tbm_plaCta as a2 ON (a1.co_emp=a2.co_emp AND a1.co_cta=a2.co_cta) ";
                    strCtaIvaCom+=" WHERE a1.co_emp="+intCodEmp+" AND a1.co_loc="+intCodLoc+" AND a1.st_reg='A' ";
                    strCtaIvaCom+=" AND a1.co_imp="+INT_ARL_COD_CFG_IVA_COM+" ";
                    strCtaIvaCom+=" AND (CASE WHEN a1.fe_vigHas IS NULL THEN '"+datFecAux+"'>=a1.fe_vigDes ELSE '"+datFecAux+"'>=a1.fe_vigDes AND '"+datFecAux+"'<=a1.fe_vigHas END) ";
                    System.out.println("strCtaIvaCom " + strCtaIvaCom);
                     
                    String strComSol="";
                    strComSol+=" SELECT nd_porImp as nd_porComSol  \n";
                    strComSol+=" FROM tbm_cfgImpLoc \n";
                    strComSol+=" WHERE co_emp="+intCodEmp+" AND co_loc="+intCodLoc+" AND st_reg='A' \n";
                    strComSol+=" AND co_imp="+INT_ARL_COD_CFG_COM_SOL+" \n";
                    strComSol+=" AND (CASE WHEN fe_vigHas IS NULL THEN '"+datFecAux+"'>=fe_vigDes ELSE '"+datFecAux+"'>=fe_vigDes AND '"+datFecAux+"'<=fe_vigHas END) \n";
                    strComSol+=" \n";
                    System.out.println("COMPENSACION "+ strComSol);
                    
                    
                    stmComSol = conCta.createStatement();
                    rstComSol = stmComSol.executeQuery(strComSol);
                     
                    stmLocIvaVen = conCta.createStatement();
                    rstLocIvaVen = stmLocIvaVen.executeQuery(strCtaIvaVen);
                    
                    stmLocIvaCom = conCta.createStatement();
                    rstLocIvaCom = stmLocIvaCom.executeQuery(strCtaIvaCom);
                     
                    stmCtaEmp = conCta.createStatement();
                    rstCtaEmp = stmCtaEmp.executeQuery(strSqlEmp);

                    stmCtaBod = conCta.createStatement();
                    rstCtaBod = stmCtaBod.executeQuery(strSqlBod);
                    
                    stmCtaLoc = conCta.createStatement();
                    rstCtaLoc = stmCtaLoc.executeQuery(strSqlLoc);
                    
                    stmCtaTipDoc = conCta.createStatement();
                    rstCtaTipDoc = stmCtaTipDoc.executeQuery(strSqlTipDoc);
                    
                    /*
                     * Significa que encontro datos de empresa, bodega , local, TipDoc  JM
                     */
                    if(rstCtaEmp.next() && rstCtaLoc.next() && rstCtaBod.next() && 
                            rstCtaTipDoc.next() && rstLocIvaVen.next() && rstLocIvaCom.next()   ){
                        // Si todos trajeron algun registro significa ke ya no esta vacia la clase
                        if( intCodEmp==2 && intCodLoc==4){  // SOLO PARA MANTA
                            if(rstComSol.next()){
                                dblComSol= rstComSol.getDouble("nd_porComSol"); // Compensacion Solidaria del IVA Ejm: 2.00
                            }
                        }
                        else{
                             dblComSol=0.00; 
                        } 
                            
                        isEmpty = false;
                        
                        /*
                         * Procedemos a cargar las cuentas
                         */
                        
                         intCtaHab         = rstCtaTipDoc.getInt("co_ctahab");  // CLientes 
                         intCtaDescVentas  = rstCtaLoc.getInt("co_ctadesven");  // Descuento en Ventas
                         intCtaDeb         = rstCtaTipDoc.getInt("co_ctadeb");  // Ventas
                         intCtaIvaVentas   = rstLocIvaVen.getInt("co_ctaivaven");  // Iva en Ventas
                         intCtaIvaCompras  = rstLocIvaCom.getInt("co_ctaivacom");  // Iva en Ventas
                         intCtaCostoVtas   = rstCtaLoc.getInt("co_ctacosven");  // Costo de Ventas
                         intCtaExistencia  = rstCtaBod.getInt("co_ctaexi");  // Existencias en Bodega
                         intCtaRetFue      = rstCtaEmp.getInt("co_ctaretfteven");  // Retencion en la fuente
                         intCtaRetIva      = rstCtaEmp.getInt("co_ctaretivaven");  // Retencion de Iva 
                         
                         dblIvaVen        = rstLocIvaVen.getDouble("nd_porImpIvaVen"); // Iva en Ventas Ejm: 12.00
                         
                        dblIvaCom    = rstLocIvaCom.getDouble("nd_porImpIvaCom"); // Iva en Ventas Ejm: 12.00
                        strNumCtaIva = (rstLocIvaCom.getString("tx_codcta")==null?"":rstLocIvaCom.getString("tx_codcta"));
                        strNomCtaIva = (rstLocIvaCom.getString("txNomCta")==null?"":rstLocIvaCom.getString("txNomCta"));
                         
                         String sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + intCodEmp  + " and " +
                                          "co_cta = " + intCtaDeb;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctadeb=rstCta.getString(1);
                             tx_deslardeb=rstCta.getString(2);
                          }
                         
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " + intCodEmp+ " and " +
                                          "co_cta = " + intCtaDescVentas;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctades=rstCta.getString(1);
                             tx_deslardes=rstCta.getString(2);
                          }
                          
                          
                          sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp+ " and " +
                                          "co_cta = " + intCtaHab;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctahab=rstCta.getString(1);
                             tx_deslarhab=rstCta.getString(2);
                          }
                          
                           sql = "select tx_codcta,tx_deslar from tbm_placta where " +
                                          "co_emp = " +intCodEmp+ " and " +
                                          "co_cta = " + intCtaIvaVentas;
                          stmCta = conCta.createStatement();
                          rstCta = stmCta.executeQuery(sql);
                          if(rstCta.next()){
                             tx_codctaivaven=rstCta.getString(1);
                             tx_deslarivaven=rstCta.getString(2);
                          }
                         
                    }
                     

                    rstCta.close();
                    rstCtaEmp.close();
                    rstCtaLoc.close();
                    rstCtaBod.close();
                    rstCtaTipDoc.close();
                    
                    rstLocIvaVen.close();
                    rstLocIvaCom.close();
                    
                    rstLocIvaVen = null;
                    rstLocIvaCom = null;
                    
                    stmLocIvaVen.close();
                    stmLocIvaVen = null;
                    
                    stmLocIvaCom.close();
                    stmLocIvaCom = null;
                    
                    
                    stmCta.close();
                    stmCtaEmp.close();
                    stmCtaLoc.close();
                    stmCtaBod.close();
                    stmCtaTipDoc.close();
                    
                    conCta.close();
                    conCta = null;
               }
               catch(java.sql.SQLException Evt){
                     isEmpty = true;
               }

               catch(Exception Evt){
                     isEmpty = true;
               }
    }
        
        
        //**************COSAS NUEVAS IMPLEMENTADAS POR JAVIER AYAPATA
        public String getCtaCodDeb(){
            return (isEmpty)?"":tx_codctadeb;
        }
         public String getCtaNomDeb(){
            return (isEmpty)?"":tx_deslardeb;
        }
         
         public String getCtaCodDes(){
            return (isEmpty)?"":tx_codctades;
        }
         public String getCtaNomDes(){
            return (isEmpty)?"":tx_deslardes;
        }
         
         public String getCtaCodHab(){
            return (isEmpty)?"":tx_codctahab;
        }
         public String getCtaNomHab(){
            return (isEmpty)?"":tx_deslarhab;
        } 
         
        public String getCtaCodIvaVen(){
            return (isEmpty)?"":tx_codctaivaven;
        }
         public String getCtaNomIvaVen(){
            return (isEmpty)?"":tx_deslarivaven;
        }   
         
       //***********************************  
         
        public int getCtaHab (){
            return (isEmpty)?-1:intCtaHab;
        }
        public int getCtaDescVentas (){
            return (isEmpty)?-1:intCtaDescVentas;
        }
        public int getCtaDeb(){
            return (isEmpty)?-1:intCtaDeb;
        }
        public int getCtaIvaVentas(){
            return (isEmpty)?-1:intCtaIvaVentas;
        }
        public int getCtaIvaCompras(){
            return (isEmpty)?-1:intCtaIvaCompras;
        }
        public String getNumCtaIva(){
            return (isEmpty)?"":strNumCtaIva;
        }
        public String getNomCtaIva(){
            return (isEmpty)?"":strNomCtaIva;
        }
        
        public int getCtaCostoVtas(){
            return (isEmpty)?-1:intCtaCostoVtas;
        }
        public int getCtaExistencia(){
            return (isEmpty)?-1:intCtaExistencia;
        }
        public int getCtaRetFue(){
            return (isEmpty)?-1:intCtaRetFue;
        }
        public int getCtaRetIva(){
            return (isEmpty)?-1:intCtaRetIva;
        }
        /*----*/
        public Double getPorIvaVen(){
            return (isEmpty)?-1.00:dblIvaVen;
        }
        public Double getPorIvaCom(){
            return (isEmpty)?-1.00:dblIvaCom;
        }
        public Double getPorComSol(){
            return (isEmpty)?-1.00:dblComSol;
        }
        public String getNomCta(int intCodCta){
            String strNomCta = null;
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                  String strSql = "select * from tbm_placta where " +
                                  "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                  "co_cta = " + intCodCta ;
                  java.sql.Statement stm = con.createStatement();
                  java.sql.ResultSet rst = stm.executeQuery(strSql);
                  if(rst.next()){
                    strNomCta = rst.getString("tx_deslar");  
                  }
                  rst.close();
                  stm.close();
                  con.close();
                  con=null;
             }catch(java.sql.SQLException Evt){
                  return null;
              }

              catch(Exception Evt){
                  return null;
              }
           return strNomCta;
        }
        public String getCodAltCta(int intCodCta){
             try{
                  java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                  String strSql = "select tx_codcta from tbm_placta where " +
                                  "co_emp = " + objZafParSis.getCodigoEmpresa()  + " and " +
                                  "co_cta = " + intCodCta ;
                  
                  
                 // System.out.println("GETCODALTCTA >> "+ strSql );
                  
                  java.sql.Statement stm = con.createStatement();
                  java.sql.ResultSet rst = stm.executeQuery(strSql);
                  if(rst.next()){
                    return rst.getString("tx_codcta");  
                  }
                  rst.close();
                  stm.close();
                  con.close();
                  
             }catch(java.sql.SQLException Evt){
                  return null;
              }

              catch(Exception Evt){
                  return null;
              }
           return null;
        }  
        
        

/**
     * Metodo que retorna el numero de la cta contable
     * Se usa para el cambio de iva del 12 al 14 % 
     * @param intEmp Codigo de la empresa.
     * @param intCtaCtb Codigo de la cta contable.
     */
    public String obtenerNumCta(int intEmp, int intCtaCtb){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        String strNumCta="";
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());           
           stm = con.createStatement();
           
           strSQL = "";
           strSQL = " Select pl.tx_codcta " ;
           strSQL+= " from tbm_placta as pl";           
           strSQL+= " where pl.co_emp = " + intEmp + " and pl.co_cta = " + intCtaCtb;
             
           rst = stm.executeQuery(strSQL);
           while (rst.next())
           {
               strNumCta = (rst.getString("tx_codcta")==null?"":rst.getString("tx_codcta"));               
           }
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){
             Evt.printStackTrace();//objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){
             Evt.printStackTrace();//objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }
       return strNumCta;
    }  
    
    
/**
     * Metodo que retorna el nombre largo de la cta contable
     * Se usa para el cambio de iva del 12 al 14 % 
     * @param intEmp Codigo de la empresa.
     * @param intCtaCtb Codigo de la cta contable.
     */
    public String obtenerNomCta(int intEmp, int intCtaCtb){
        java.sql.Connection con;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSQL;
        String strNomCta="";
       try{
           con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());           
           stm = con.createStatement();
           
           strSQL = "";
           strSQL = " Select pl.tx_deslar " ;
           strSQL+= " from tbm_placta as pl";           
           strSQL+= " where pl.co_emp = " + intEmp + " and pl.co_cta = " + intCtaCtb;
             
           rst = stm.executeQuery(strSQL);
           while (rst.next())
           {
               strNomCta = (rst.getString("tx_deslar")==null?"":rst.getString("tx_deslar"));               
           }
           rst.close();
           stm.close();
           con.close();
           rst=null;
           stm=null;
           con=null;
       } catch(java.sql.SQLException Evt){
             Evt.printStackTrace();//objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }catch(Exception Evt){
             Evt.printStackTrace();//objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),Evt);
       }
       return strNomCta;
    }        
        
        
        
        
        
}
