/*
 * UltDocPrint.java
 *
 * Created on 29 de noviembre de 2004, 15:59
 */

package Ventas.ZafVen02;

/**
 *
 * @author  root
 */
public class UltDocPrint {
        Librerias.ZafParSis.ZafParSis objZafParSis;
        /*
         * Variables Sql
         */
        private java.sql.Connection conDoc;  //Coneccion a la base donde se encuentra el cliente
        private java.sql.Statement stmDoc;   //Statement para la cliente
        private java.sql.ResultSet rstDoc;   //Resultset que tendra los datos 
        
        /** Creates a new instance of UltDocPrint */
        public UltDocPrint(Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;
        }
        
        /**
         * Verifica si existe o no el numero de documento <br>
         * enviado como parametro
         * @return <pre>
         * true  .- Si el numero de documento existe en la tabla de movinv
         * false .- Si el numero de documento NO existe en la tabla de movinv
         * </pre>
         * @param intNumDoc Nomero de documento a evaluar
         * @param intCodTipDoc codigo de tipo de documento a evaluar
         */        
        public boolean getExisteDoc(int intNumDoc, int intCodTipDoc){
               boolean blnExiste = false;
               
               try{
                   conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de movinv y verificar si existe o no ese 
                *  numero de documento.
                */
               
                    
                    String strSql = "SELECT ne_numdoc as numdoc FROM tbm_cabmovinv where " +
                                  " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                  " and co_loc = " + objZafParSis.getCodigoLocal() +
                                  " and co_tipdoc = " + intCodTipDoc +
                                  " and ne_numdoc = " + intNumDoc;
                    
                    stmDoc = conDoc.createStatement();
                    rstDoc = stmDoc.executeQuery(strSql);
                    
                    /*
                     * Significa ke existe ese numero documento para el tipo de documento
                     */
                    if(rstDoc.next()){
                        
                        blnExiste = true;
                    }
                    rstDoc.close();
                    stmDoc.close();
                    conDoc.close();
               }
               catch(java.sql.SQLException Evt){
                       return false;
               }

               catch(Exception Evt){
                       return false;
               }
               return blnExiste;
        }
        /**
         * Verifica si existe o no el numero de documento <br>
         * enviado como parametro
         * @return <pre>
         * true  .- Si el numero de documento existe en la tabla de movinv
         * false .- Si el numero de documento NO existe en la tabla de movinv
         * </pre>
         * @param intNumDoc Nomero de documento a evaluar
         * @param intCodTipDoc codigo de tipo de documento a evaluar
         * @param intNumMov Numero del movimiento ke no se tomara en cuenta para la buskeda
         */        
        public boolean getExisteDoc(int intNumDoc, int intCodTipDoc, int intNumMov){
               boolean blnExiste = false;
               
               try{
                   conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de movinv y verificar si existe o no ese 
                *  numero de documento.
                */
               
                    
                    String strSql = "SELECT ne_numdoc as numdoc FROM tbm_cabmovinv where " +
                                  " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                  " and co_loc = " + objZafParSis.getCodigoLocal() +
                                  " and co_tipdoc = " + intCodTipDoc +
                                  " and ne_numdoc = " + intNumDoc  +
                                  " and co_doc   <> " + intNumMov; 
                    
                    stmDoc = conDoc.createStatement();
                    rstDoc = stmDoc.executeQuery(strSql);
                    
                    /*
                     * Significa ke existe ese numero documento para el tipo de documento
                     */
                    if(rstDoc.next()){
                        
                        blnExiste = true;
                    }
                    rstDoc.close();
                    stmDoc.close();
                    conDoc.close();
               }
               catch(java.sql.SQLException Evt){
                       return false;
               }

               catch(Exception Evt){
                       return false;
               }
               return blnExiste;
        }
        
        
        /**
         * Obtiene el ultimo numero de documento  <br>
         * enviado como parametro
         * @return <pre>
         *       UN entero con el ultimo numero de documento pasado por parametro      
         * -1    .- Si el tipo de documento no existe en la tabla de cabtipdoc
         *          o si hubo algun problema al conetarse a la base
         *   
         * </pre>
         * @param intCodTipDoc codigo de tipo de documento a evaluar
         */        
        public int getUltDoc(int intCodTipDoc){
            int intUltDoc = -1;
               try{
                   conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
               /*
                *  String para el hacer el query en la tabla de cabTipDoc y Obtener el ultimo 
                *  numero de documento.
                */
               
                    
                    String strSql = "SELECT ne_ultDoc as ultdoc FROM tbm_cabTipDoc where " +
                                  " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                  " and co_loc = " + objZafParSis.getCodigoLocal() +
                                  " and co_tipDoc = " + intCodTipDoc;
                    
                    stmDoc = conDoc.createStatement();
                    rstDoc = stmDoc.executeQuery(strSql);
                    
                    /*
                     * Significa ke existe ese documento
                     */
                    if(rstDoc.next()){
                        intUltDoc = rstDoc.getInt("ultdoc");
                    }
                    rstDoc.close();
                    stmDoc.close();
                    conDoc.close();
               }
               catch(java.sql.SQLException Evt){
                       return -1;
               }

               catch(Exception Evt){
                       return -1;
               }
               return intUltDoc;
            
        }
        
    
}
