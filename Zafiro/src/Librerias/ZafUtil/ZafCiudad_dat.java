/*
 * Ciudad_dat.java
 *
 * Created on 25 de noviembre de 2004, 17:33
 */

package Librerias.ZafUtil;

/**
 *
 * @author  IdiTrix
 */
public class ZafCiudad_dat {
        /*
         * Contiene true si es ke no se encontro esa ciudad para saber si devolver null o no
         */ 
        private boolean isEmpty = true; 
        /*
         * Datos de la ciudad
         */
        private int codigo = 0;
        private String nombrecorto = "";//tx_desCor
        private String nombrelargo = "";//tx_desLar
        /*
         * Variables Sql
         */
        private java.sql.Connection conCiu;  //Coneccion a la base donde se encuentra la ciudad
        private java.sql.Statement stmCiu;   //Statement para la ciudad
        private java.sql.ResultSet rstCiu;   //Resultset que tendra los datos de la ciudad

        Librerias.ZafParSis.ZafParSis objZafParSis;
        /**
         * Construye un nuevo objeto del tipo Ciudad_dat <br>el cual contendra los datos de un Ciudad enviado como parametro
         * @param cod_ciu Codigo de Ciudad para el que se obtendran los datos
         */        
        public ZafCiudad_dat(int cod_ciu, Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;
            setCiudad(cod_ciu);
        }

        /**
         * Construye un nuevo objeto del tipo Ciudad_dat <br>el cual contendra los datos de un Ciudad enviado como parametro
         */        
        public ZafCiudad_dat( Librerias.ZafParSis.ZafParSis objZafParSis){
            this.objZafParSis = objZafParSis;
        }

    
        /**
         * Recupera y carga a la clase Ciudad _dat, la informacion respectiva al<br>
         * al Ciudad enviado como parametro
         * @return <pre>
         * true  .- Si los datos de Ciudad fueron cargados con exito
         * false .- Si el codigo de Ciudad no se encontro o no se pudo cargar los datos
         * </pre>
         * @param cod_Ciu Codigo de la Ciudad del cual se recuperaran los datos
         */        
        public boolean setCiudad(int cod_ciu){
               codigo = cod_ciu;
               isEmpty = true;
               
               try{
                    conCiu=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   
                    stmCiu = conCiu.createStatement();
                    rstCiu = stmCiu.executeQuery("Select * from tbm_Ciu where co_Ciu = " + codigo );
                    /*
                     * Significa ke encontro el Ciudad
                     */
                    if(rstCiu.next()){
                        nombrecorto = rstCiu.getString("tx_desCor");
                        nombrelargo = rstCiu.getString("tx_desLar");
                        isEmpty = false;
                    }
                    rstCiu.close();
                    stmCiu.close();
                    conCiu.close();

               }
               catch(java.sql.SQLException Evt){
                       return false;
               }

               catch(Exception Evt){
                       return false;
               }
               return true;
        }
        public boolean isEmpty (){
            return isEmpty;
        }
        public String getNombrecorto (){
            return (isEmpty)?null:nombrecorto;
        }
        public String getNombrelargo (){
            return (isEmpty)?null:nombrelargo;
        }

}
