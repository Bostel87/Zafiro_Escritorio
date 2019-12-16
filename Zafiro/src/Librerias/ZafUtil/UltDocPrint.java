/*
 * UltDocPrint.java    getCodigoDocumento_2
 *
 * Created on 29 de noviembre de 2004, 15:59
 * 
 * Version 4.2 Ingrid Lino: Se adiciona en Verificar_Doc_Asociado() el tipo de documento 228(antes estaba solo 1, ahora se incluyó el 228 por Facturas Electrónicas)
 */
      
package Librerias.ZafUtil;
/**
 *
 * @author       
 */
public class UltDocPrint {
        Librerias.ZafParSis.ZafParSis objZafParSis;
        Librerias.ZafUtil.ZafUtil objUti;
        /*
         * Variables Sql
         */
        private java.sql.Connection conDoc; //Coneccion a la base donde se encuentra el cliente
        private java.sql.Statement stmDoc;   //Statement para la cliente
        private java.sql.ResultSet rstDoc;   //Resultset que tendra los datos 
        private String strVersion="ZafUtil.UltDocPrint v 0.5";
        /** Creates a new instance of UltDocPrint */
        public UltDocPrint(Librerias.ZafParSis.ZafParSis obj){
          try{
                this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
                objUti = new ZafUtil();
                System.out.println(strVersion);
          }catch (CloneNotSupportedException e){
              objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
          }                                     
        }
        
        /**
         * Verifica si es o no el documento por reposicion<br>
         * enviado como parametro
         * @return <pre>
         * true  .- Si el documento existe en la tabla de movinv
         * false .- Si el documento NO existe en la tabla de movinv
         * </pre>
         * @param intNumDoc Nomero de documento a evaluar
         * @param intCodTipDoc codigo de tipo de documento a evaluar
         */        
        public boolean getDocReposicion(int intCodTipDoc, int intNumDoc){
               boolean blnExiste = false;
               
               try{
                   conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   if (conDoc!=null)
                   {
                       /*
                        *  String para el hacer el query en la tabla de movinv y verificar si existe o no ese 
                        *  numero de documento.
                        */                    
                        String strSql = "SELECT count(*) FROM tbm_cabmovinv where " +
                                      " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                      " and co_loc = " + objZafParSis.getCodigoLocal() +
                                      " and co_tipdoc = " + intCodTipDoc +
                                      " and ne_numdoc = " + intNumDoc + " and st_reg = 'R'";

                        stmDoc = conDoc.createStatement();
                        rstDoc = stmDoc.executeQuery(strSql);
                        if(rstDoc.next()){

                            blnExiste = true;
                        }
                        rstDoc.close();
                        stmDoc.close();
                        conDoc.close();
                   }
               }catch(java.sql.SQLException Evt){
                       return false;
               }catch(Exception Evt){
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
* @param conn  Conexión de la base de datos 
* @param intNumDoc Nomero de documento a evaluar
* @param intCodTipDoc codigo de tipo de documento a evaluar
*/        
public boolean getExisteDoc(java.sql.Connection conn, String strCodTipDoc, String strNumDoc){
  boolean blnExiste = false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  try{
   if(conn!=null){
     stmLoc=conn.createStatement();  
     String strSql = "SELECT ne_numdoc as numdoc FROM tbm_cabmovinv where " +
     " co_emp ="+objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal() +
     " and co_tipdoc="+strCodTipDoc+" and ne_numdoc = "+strNumDoc;
     rstLoc=stmLoc.executeQuery(strSql);
     if(rstLoc.next())
          blnExiste = true;
     rstLoc.close();
     rstLoc=null;
     stmLoc.close();
     stmLoc=null;
 }}catch(java.sql.SQLException e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
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
         */        
        public boolean getExisteDoc(int intNumDoc, int intCodTipDoc){
             boolean blnExiste = false;
              try{
                   conDoc=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                   if(conDoc!=null){
                    String strSql = "SELECT ne_numdoc as numdoc FROM tbm_cabmovinv where " +
                                   " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                                   " and co_loc = " + objZafParSis.getCodigoLocal() +
                                   " and co_tipdoc = " + intCodTipDoc +
                                   " and ne_numdoc = " + intNumDoc;
                    
                    stmDoc = conDoc.createStatement();
                    rstDoc = stmDoc.executeQuery(strSql);
                    
                    if(rstDoc.next())
                        blnExiste = true;
                
                    rstDoc.close();
                    stmDoc.close();
                    conDoc.close();
                    conDoc=null;
                  }
              }catch(java.sql.SQLException e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
               catch(Exception e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
            return blnExiste;
        }
        
   
        

/**
* Verifica si existe o no el numero de documento <br>
* enviado como parametro
* @return <pre>
* true  .- Si el numero de documento existe en la tabla de movinv
* false .- Si el numero de documento NO existe en la tabla de movinv
* </pre>
* @param conn Conexion de la base de datos. 
* @param strCodTipDoc Código tipo documento 
* @param strCodDoc codigo del documento a evaluar
* @param strNumDoc Numero del documento que no se tomara en cuenta para la buskeda
*/        
public boolean getExisteDoc(java.sql.Connection conn, String strCodTipDoc, String strCodDoc, String strNumDoc){
   boolean blnExiste = false;
   java.sql.Statement stmLoc;
   java.sql.ResultSet rstLoc;
   try{
       stmLoc=conn.createStatement();
       String strSql = "SELECT ne_numdoc as numdoc FROM tbm_cabmovinv where " +
       " co_emp = " + objZafParSis.getCodigoEmpresa()+" and co_loc = " + objZafParSis.getCodigoLocal() +
       " and co_tipdoc="+strCodTipDoc+" and ne_numdoc="+strNumDoc+" and co_doc <> "+strCodDoc; 
       rstLoc= stmLoc.executeQuery(strSql);
       if(rstLoc.next())
           blnExiste = true;
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
  }catch(java.sql.SQLException e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
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
             
              }catch(java.sql.SQLException e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
               catch(Exception e){  blnExiste = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
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
       /**
        * Metodo para almacenar el ultimo codigo de documento (ne_ultdoc) en la tabla de tipos de documento
        * La verificacion especifica que se actualize mientras el codigo recibido no sea menor al ult_codigo en la base.
        * @param Conexion .- con
        * @param Codigo Empresa.- intCodEmp
        * @param Codigo Local.- intCodLoc
        * @param Tipo de Documento .- intCodTipDoc
        * @param Numero de Secuencia .- intNumDoc
        * @return  true Si se realizo con exito el proceso ; incluso si no se actualizo por ser una secuencia menor a la actual
        *          false Si no existe el tipo de documento o existio un error durante el proceso
        * @autor: jsalazar 
        * @fecha: 02/Dic/2005
        */
        public boolean setSaveUltDoc(java.sql.Connection con, int intCodEmp,int intCodLoc, int intCodTipDoc, int intNumDoc){
            boolean blnRes = false;
            try{
                if (getUltDoc(intCodTipDoc)<intNumDoc){
                             String sql = "Update tbm_cabTipDoc set " +
                                " ne_ultDoc = " + intNumDoc +  "" +
                                " where " +
                                " co_emp = " + intCodEmp + " and "+  
                                " co_loc = " + intCodLoc   + " and "+  
                                " co_tipDoc = " +  intCodTipDoc;      
                         System.out.println("---=====>>> "+  sql );                           
   
                        java.sql.PreparedStatement pstReg = con.prepareStatement(sql);
                        
                        
                        /* Ejecutando el Update */
                        pstReg.executeUpdate();                                    
                }
                blnRes = true;
            }catch(java.sql.SQLException e){
                    blnRes = false;    
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }catch(Exception e){
                   blnRes = false;              
                   objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return blnRes;
        }
    
        public int getNumeroOrdenDocumento(){ 
            int intOrdDoc = 1;  
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                
                        java.sql.Statement stm =con.createStatement();
                        String strSQL= "SELECT Max(ne_orddoc)+1 as ne_orddoc  FROM tbm_cabMovInv where " +
                          " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                          " and co_loc = " + objZafParSis.getCodigoLocal() ;
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intOrdDoc = rst.getInt("ne_orddoc");
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
            return intOrdDoc;
        }
        
        
        
          
    /** Esta funcion permite saber si la Factura esta confirnada o no 
     * @param conn:  Coneccion de la base 
     * @param intCodEmp: Codigo de la empresa 
     * @param intCodLoc: Codigo del local 
     * @param strCodTipDoc: Codigo tipo de documento 
     * @param strCodDoc: Codigo del documento 
     * @return true : si esta confirmado
     * @return false: si no esta confirmado
     * 
     * @autor: jayapata 
     * @fecha: 22/Jul/2008
     */ 
       public boolean verificarsiesconfirmado(java.sql.Connection conn, int intCodEmp, int intCodLoc, String strCodTipDoc, String strCodDoc){
         boolean blnRes=false; 
         String strSql="";
         java.sql.Statement stmLoc;
         java.sql.ResultSet rstLoc;
         String srtMesj="";
         try{
            if(conn!=null){
                stmLoc=conn.createStatement();
                
                strSql = "SELECT  st_coninv, st_autingegrinvcon FROM  tbm_cabMovInv WHERE " +
                         "co_emp = "+intCodEmp+" AND co_loc = "+intCodLoc+" AND  co_tipDoc="+strCodTipDoc+" AND co_doc="+strCodDoc;
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                 if((rstLoc.getString("st_coninv").equals("D")) || (rstLoc.getString("st_coninv").equals("C"))  || (rstLoc.getString("st_coninv").equals("E")) ){
                   if(rstLoc.getString("st_autingegrinvcon").equals("N")){
                          srtMesj="El documento está CONFIRMADO o en proceso de confirmación. \n"+ 
                          " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
                          " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                    }else blnRes=true;
                   }else  blnRes=true;
                 }else  srtMesj="No Existe La Factura. ";
                
                if(!srtMesj.equals(""))  MensajeInf(srtMesj);
                
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
                
            }}catch(java.sql.SQLException e){ blnRes=false;  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);   }
          return blnRes;                           
        }
        
      
        
        

public boolean verificarsiesconfirmado(java.sql.Connection conn, String strCodEmp, String strCodLoc, String cotipdoc, String coddoc){
 boolean blnRes=true;
  try{
    if (conn!=null){

  String strSQL = "select st_coninv, st_autingegrinvcon FROM  tbm_cabMovInv  " +
  " where co_emp = "+strCodEmp+" and co_loc="+strCodLoc+" and co_tipDoc="+cotipdoc+" and co_doc="+coddoc;
  java.sql.Statement stm2 = conn.createStatement();
  java.sql.ResultSet rst;          
  rst = stm2.executeQuery(strSQL);
  if(rst.next()){
     if( (rst.getString("st_coninv").equals("D")) || (rst.getString("st_coninv").equals("C"))  || (rst.getString("st_coninv").equals("E")) ){
         if(rst.getString("st_autingegrinvcon").equals("N")){
         String srt="El documento está CONFIRMADO o en proceso de confirmación. \n"+ 
              " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
              " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
         MensajeInf( srt );
         blnRes=false;
     }}
  }
  rst.close();
  rst=null;
  stm2.close();
  stm2=null;
    
  }}catch(java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }  
  return blnRes;                           
}


         
        

public boolean verificarsiesconfirmado(String coddoc,String cotipdoc){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stm2, stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{
 
 conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
 if(conn!=null){
   stm2 = conn.createStatement();
   stmLoc = conn.createStatement();


       strSQL="SELECT distinct(a1.st_coninv) as stconinv FROM  tbm_detguirem as a " +
       " inner join  tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
       " where a.co_emprel="+objZafParSis.getCodigoEmpresa()+" and a.co_locrel="+objZafParSis.getCodigoLocal()+" and  a.co_docrel="+coddoc+"  and  a.co_tipDocrel="+cotipdoc;
       
       rstLoc = stmLoc.executeQuery(strSQL);
       if(rstLoc.next()){
            if( (rstLoc.getString("stconinv").equals("E")) || (rstLoc.getString("stconinv").equals("C")) ){
                  String srt="El documento está CONFIRMADO o en proceso de confirmación. \n"+
                  " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
                  " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                  MensajeInf( srt );
                  blnRes=false;
            }
       }
       rstLoc.close();
       rstLoc=null;


stm2.close();
stm2=null;
stmLoc.close();
stmLoc=null;

  conn.close();
}}catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  blnRes=false; }
  return blnRes;
}



/**
 * Funcion que permite saber si la guia remision tiene guia secundaria.
 * @param coddoc
 * @param cotipdoc
 * @return
 */
public boolean verificarGuiRemSiTieGuiSec(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=true;
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{
 if(conn!=null){
   stmLoc = conn.createStatement();

       strSQL="SELECT  a2.co_docrel  FROM  tbm_detguirem as a " +
       " inner join  tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
       " inner join  tbr_cabguirem as a2 on (a2.co_emp=a1.co_emp and a2.co_locrel=a1.co_loc and a2.co_tipdocrel=a1.co_tipdoc and a2.co_docrel=a1.co_doc ) "+
       " inner join  tbm_cabguirem as a3 on (a3.co_emp=a2.co_emp and a3.co_loc=a2.co_loc and a3.co_tipdoc=a2.co_tipdoc and a3.co_doc=a2.co_doc ) " +
       " where a.co_emprel="+intCodEmp+" and a.co_locrel="+intCodLoc+"  and  a.co_tipDocrel="+intCodTipDoc+" and  a.co_docrel="+intCodDoc+" "
       + " and a3.st_reg='A' ";
       rstLoc = stmLoc.executeQuery(strSQL);
       if(rstLoc.next()){
                  String srt="LA ORDEN DE DESPACHO DE ESTA FACTURA TIENE GENERADA GUÍA DE REMISIÓN.\n"+
                  " NO ES POSIBLE ANULAR EL DOCUMENTO. ";    
                  MensajeInf( srt );
                  blnRes=false;
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
      
}}catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  blnRes=false; }
  return blnRes;
}




public boolean verificarsiesconfirmado(String codemp,String coloc, String coddoc,String cotipdoc){
 boolean blnRes=true;
 java.sql.Connection conn;
 java.sql.Statement stm2, stmLoc;
 java.sql.ResultSet rstLoc;
 String strSQL="";
 try{

 conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
 if(conn!=null){
   stm2 = conn.createStatement();
   stmLoc = conn.createStatement();

   
       strSQL="SELECT distinct(a1.st_coninv) as stconinv FROM  tbm_detguirem as a " +
       " inner join  tbm_cabguirem as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
       " where a.co_emprel="+codemp+" and a.co_locrel="+coloc+" and  a.co_docrel="+coddoc+"  and  a.co_tipDocrel="+cotipdoc;

       rstLoc = stmLoc.executeQuery(strSQL);
       if(rstLoc.next()){
            if( (rstLoc.getString("stconinv").equals("E")) || (rstLoc.getString("stconinv").equals("C")) ){
                  String srt="El documento está CONFIRMADO o en proceso de confirmación. \n"+
                  " No es posible anular un documento que está confirmado o en proceso de confirmación. \n"+
                  " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                  MensajeInf( srt );
                  blnRes=false;
            }
       }
       rstLoc.close();
       rstLoc=null;


stm2.close();
stm2=null;
stmLoc.close();
stmLoc=null;

  conn.close();
}}catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  blnRes=false; }
  return blnRes;
}


       
       
    /** Esta funcion permite saber si la Factura que tiene transferencia automatica
     *  y si ya esta en la guia de remicion  
      * @param coddoc: Codigo del documento 
     *  @param cotipdoc: Codigo de tipo documento 
     * @return true : si existe y ya esta en guía de remisión  
     *         false: si no existe ó no esta esta en la guía de remisión
     * 
     * @autor: jayapata 
     * @fecha: 22/Jul/2008
     */
     public boolean isVerificarGuiaRemTrans(int codEmp, int CodLoc,  String coddoc,String cotipdoc){
       boolean blnRes=false;   
        try{  
                 java.sql.Connection conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn!=null){
                               
                    String strSQL = "select distinct(b1.ne_numdoc) as ne_numdoc  from tbr_cabmovinv as a " +
                    " inner join tbm_detguirem as b on (b.co_emprel=a.co_emprel and b.co_locrel=a.co_locrel and b.co_tipdocrel=a.co_tipdocrel and b.co_docrel=a.co_docrel ) " +
                    " inner join tbm_cabguirem as b1 on (b1.co_emp=b.co_emp and b1.co_loc=b.co_loc and b1.co_tipdoc=b.co_tipdoc and b1.co_doc=b.co_doc )  " +
                    "  where " +
                    " a.co_emp = " + codEmp + " and "+  
                    " a.co_loc = " + CodLoc  + " and "+  
                    " a.co_doc = " +  coddoc   + " and "+  
                    " a.co_tipDoc = " + cotipdoc+" and  " +
                    " a.co_emprel="+objZafParSis.getCodigoEmpresaGrupo()+" and b1.st_reg not in ('I','E')";
                    
                      java.sql.Statement stm2 = conn.createStatement();
                      java.sql.ResultSet rst;          
                      rst = stm2.executeQuery(strSQL);
                      if(rst.next()){
                             String srt="El documento tiene GUÍA REMISION No "+rst.getString("ne_numdoc")+". \n"+ 
                                  " No es posible anular un documento que tiene guía de remisión. \n"+
                                  " Comuníquese con la persona que realiza las confirmaciones para poder anular el documento.";
                             MensajeInf( srt );
                             blnRes=true;
                        }
                      rst.close();
                      rst=null;
                      stm2.close();
                      stm2=null;
                       conn.close();
                       conn=null;
                    }    
             }catch(java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  } 
           return blnRes;                           
        }
        
           
/**
 * Permite saber si tiene generado orden de compra para cosenco caso de items de servicio.
 * @param intCodEmp
 * @param intCodLoc
 * @param intCodTipDoc
 * @param intCodDoc
 * @return  true si puede anular <br>  false  no se puede anular
 */
public boolean isGenOCCosenco(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc ){
 boolean blnRes=true;
 java.sql.Statement stmLoc=null;
 java.sql.ResultSet rstLoc=null;
 String strSQL="";
 try{
  if(conn!=null){
    stmLoc=conn.createStatement();
      
    strSQL = "select a.co_emprel, a.co_locrel, a.co_tipdocrel, a.co_docrel from tbr_detmovinv as a "
    + " inner join tbm_detmovinv as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc and a1.co_reg=a.co_reg ) "
    + " inner join tbm_inv as a2 on (a2.co_emp=a1.co_emp and a2.co_itm=a1.co_itm) "
    + " where a.co_emp="+intCodEmp+" and a.co_loc="+intCodLoc+" and a.co_tipdoc="+intCodTipDoc+" and a.co_doc="+intCodDoc+" and a2.st_ser='S' "
    + " ";
    rstLoc=stmLoc.executeQuery(strSQL);
    if(rstLoc.next()){
         String srt="NO SE PUEDE ANULAR PORQUE HAY ITENS DE SERVICIO YA GENERADO ORDEN DE COMPRA PARA COSENCO..";
         MensajeInf( srt );
         blnRes=false;
    }
    rstLoc.close();
    rstLoc=null;
    stmLoc.close();
    stmLoc=null;
 }}catch(java.sql.SQLException e){ blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
 return blnRes;
}


       
       /* JM: Facven FacVen (Se Agrega Egresos de bodega prefacturas, 10/Oct/2017) */
         
        //**********************************************************************************
          public boolean Verificar_Doc_Asociado(String coddoc,String cotipdoc){
             boolean blnres=true;
              try{ int intVal=0; 
                java.sql.Connection conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn!=null){ 
                     String sql = "select count(*) from  tbr_cabMovInv where co_emp="+objZafParSis.getCodigoEmpresa()+" and  co_tipdocrel="+cotipdoc+" and co_locrel="+objZafParSis.getCodigoLocal()+" and co_docrel="+coddoc+" and st_reg='A' and co_tipdoc NOT IN(1,228,294)"; 
                     java.sql.Statement stm = conn.createStatement();
                      java.sql.ResultSet rst;   
                     rst = stm.executeQuery(sql);
                     if(rst.next()){
                             intVal = rst.getInt(1);
                             if(intVal>0){
                                   blnres=false;
                                   String srt="El documento tiene Nota de Credito. \n"+ 
                                   " No es posible anular un documento que tiene Nota de Credito. ";
                                   MensajeInf( srt );
                               }
                     } else    
                         blnres=false;
                   conn.close();
                  }    
               }catch(java.sql.SQLException e){    
                       objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);   
                        blnres=false;
               }
             return blnres;                           
           } 
        //*****************************************************************************
        
        
        
        
         //**********************************************************************************
          public boolean Verificar_Doc_Asociado_OC_anu_Fac(java.sql.Connection conn, String coddoc,String cotipdoc, String coTipdocFac){
             boolean blnres=true;
              try{ int intVal=0; 
                //java.sql.Connection conn = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                if (conn!=null){ 
                     String sql = "select count(*) from  tbr_cabMovInv where co_emp="+objZafParSis.getCodigoEmpresa()+" and  " +
                     " co_tipdocrel="+cotipdoc+" and co_locrel="+objZafParSis.getCodigoLocal()+" and co_docrel="+coddoc+" " +
                     " and st_reg='A'  and co_tipdoc<>"+coTipdocFac; 
                     java.sql.Statement stm = conn.createStatement();
                      java.sql.ResultSet rst;   
                     rst = stm.executeQuery(sql);
                     if(rst.next()){
                             intVal = rst.getInt(1);
                             if(intVal>0){
                                   blnres=false;
                                   String srt="El documento tiene Nota de Credito. \n"+ 
                                   " No es posible anular un documento que tiene Nota de Credito. ";
                                   MensajeInf( srt );
                               }
                     } else    
                         blnres=false;
                   ///conn.close();
                  }    
               }catch(java.sql.SQLException e){    
                       objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);   
                        blnres=false;
               }
             return blnres;                           
           } 
        //*****************************************************************************
        
        
        
/**
* Esta funcion permite retornar el numero secuencial de la empresa en la que estamos
* @param con : recive la coneccion de la base
* @return retorna el numero de secuencial de la empresa
*/

public int getNumeroOrdenEmpresa_anterior(java.sql.Connection con ){
int intOrdDoc = 1;
try{
    try{
        if (con!=null){

            java.sql.Statement stm =con.createStatement();
            String strSQL= "SELECT Max(ne_secemp)+1 as ne_orddoc  FROM tbm_cabMovInv where " +
              " co_emp = " + objZafParSis.getCodigoEmpresa();
            java.sql.ResultSet rst = stm.executeQuery(strSQL);
            if(rst.next()){
                intOrdDoc = rst.getInt("ne_orddoc");
            }
            rst.close();
            stm.close();
        }
    }catch(java.sql.SQLException e){
        con.close();
        objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
    }
}catch(Exception e){
    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
}
return intOrdDoc;
}




  





       
        /**
         * Esta funcion permite retornar el numero secuencial de la empresa en la que estamos
         * @param con : recive la coneccion de la base
         * @return retorna el numero de secuencial de la empresa
         */
         public int getNumeroOrdenEmpresa(java.sql.Connection con, int intCodEmp ){ 
            int intOrdDoc = 1;
            try{
                try{
                    if (con!=null){
                
                        java.sql.Statement stm =con.createStatement();
                        String strSQL= "SELECT Max(ne_secemp)+1 as ne_orddoc  FROM tbm_cabMovInv WHERE co_emp="+intCodEmp; 
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intOrdDoc = rst.getInt("ne_orddoc");
                        }
                        rst.close();
                        stm.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return intOrdDoc;
        }
           
         
              
/**
 *  Función que se encargar de validar si el cliente es una empresa de las compañias como tuval, dimulti en estos 
 *  casos no debe actualizar stock de grupo.
 * 
 * @param strCodCli Código del cliente
 * @param con Conexion de la base
 * @return 1: Si Alcualiza grupo
 * @return 0: No alcualiza grupo
 */         
public int ValidarCodigoCliente(int intCodEmp, String strCodCli,java.sql.Connection con){
 int intTipCli=1;  
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
  if(con!=null){ 
   stmLoc=con.createStatement();
   strSql="select round(nd_par1) as nd_par1  from tbr_paremp where co_emp="+intCodEmp+" and co_par=1"; 
   rstLoc = stmLoc.executeQuery(strSql);
   if(rstLoc.next()){
     if(rstLoc.getInt("nd_par1")==1){
       ///******************************* TUVAL ******************************
        if(intCodEmp==1){
        if( (strCodCli.equals("603")) || (strCodCli.equals("2600")) || (strCodCli.equals("1039"))  ) {
              intTipCli=0;   //no actualiza grupo                      
        }else{
               intTipCli=1;      
        }}        
        ///******************************* CASTECK ******************************
        if(intCodEmp==2){
        if( (strCodCli.equals("2854")) || (strCodCli.equals("2105"))  || (strCodCli.equals("789")) ) {
             intTipCli=0;   //no actualiza grupo                      
         }else{
               intTipCli=1;      
        }}        
        ///******************************* NOSITOL  ******************************
         if(intCodEmp==3){
         if( (strCodCli.equals("2858")) || (strCodCli.equals("453")) || (strCodCli.equals("832"))  ) {
               intTipCli=0;   //no actualiza grupo                      
         }else{
               intTipCli=1;      
        }}          
        ///*******************************  DIMULTI  ******************************
          if(intCodEmp==4){
          if( (strCodCli.equals("3117")) || (strCodCli.equals("498")) || (strCodCli.equals("2294"))  ) {
                intTipCli=0;   //no actualiza grupo                      
         }else{
              intTipCli=1;      
        }}          
  }}
 }}catch(java.sql.SQLException e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }          
 return intTipCli;           
}
         
         
         
         
/**
 *  Función que se encargar de validar si el cliente es una empresa de las compañias como tuval, dimulti en estos 
 *  casos no debe actualizar stock de grupo.
 * 
 * @param strCodCli Código del cliente
 * @param con Conexion de la base
 * @return 1: Si Alcualiza grupo
 * @return 0: No alcualiza grupo
 */         
public int ValidarCodigoCliente(String strCodCli,java.sql.Connection con){
 int intTipCli=1;  
 java.sql.Statement stmLoc;
 java.sql.ResultSet rstLoc;
 String strSql="";
 try{
  if(con!=null){ 
   stmLoc=con.createStatement();
   strSql="select round(nd_par1) as nd_par1  from tbr_paremp where co_emp="+objZafParSis.getCodigoEmpresa()+" and co_par=1"; 
   rstLoc = stmLoc.executeQuery(strSql);
   if(rstLoc.next()){
     if(rstLoc.getInt("nd_par1")==1){
       ///******************************* TUVAL ******************************
        if(objZafParSis.getCodigoEmpresa()==1){
        if( (strCodCli.equals("603")) || (strCodCli.equals("2600")) || (strCodCli.equals("1039"))  ) {
              intTipCli=0;   //no actualiza grupo                      
        }else{
               intTipCli=1;      
        }}        
        ///******************************* CASTECK ******************************
        if(objZafParSis.getCodigoEmpresa()==2){
        if( (strCodCli.equals("2854")) || (strCodCli.equals("2105"))  || (strCodCli.equals("789")) ) {
             intTipCli=0;   //no actualiza grupo                      
         }else{
               intTipCli=1;      
        }}        
        ///******************************* NOSITOL  ******************************
         if(objZafParSis.getCodigoEmpresa()==3){
         if( (strCodCli.equals("2858")) || (strCodCli.equals("453")) || (strCodCli.equals("832"))  ) {
               intTipCli=0;   //no actualiza grupo                      
         }else{
               intTipCli=1;      
        }}          
        ///*******************************  DIMULTI  ******************************
          if(objZafParSis.getCodigoEmpresa()==4){
          if( (strCodCli.equals("3117")) || (strCodCli.equals("498")) || (strCodCli.equals("2294"))  ) {
                intTipCli=0;   //no actualiza grupo                      
         }else{
              intTipCli=1;      
        }}          
  }}
 }}catch(java.sql.SQLException e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
   catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }          
 return intTipCli;           
}






  
/**
 * Esta funcion permite extraer el numero de secuencia de documento.
 * @return numero de secuencia de la grupo.
 */
public int getNumSecDoc(java.sql.Connection conn, int intCodEmp ){
 int intNumSec=0;
 java.sql.Statement stmLoc, stmLoc01;
 java.sql.ResultSet rstloc;
 String strSql="";
 try{
  
    if(conn!=null){
      stmLoc=conn.createStatement();
      stmLoc01=conn.createStatement();

      strSql="SELECT CASE WHEN ne_secUltDocTbmCabMovInv IS NULL THEN 0 ELSE (ne_secUltDocTbmCabMovInv+1) END AS numsec " +
      " FROM tbm_emp WHERE co_emp="+intCodEmp;
      rstloc=stmLoc.executeQuery(strSql);
      if(rstloc.next())
          intNumSec=rstloc.getInt("numsec");
      rstloc.close();
      rstloc=null;

      strSql="UPDATE tbm_emp SET  ne_secUltDocTbmCabMovInv="+intNumSec+" WHERE co_emp="+intCodEmp;
      System.out.println("strSql:  "+ strSql );
      stmLoc01.executeUpdate(strSql);

      stmLoc.close();
      stmLoc=null;
      stmLoc01.close();
      stmLoc01=null;

   }}catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
     catch(Exception e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
return intNumSec;
}




                
           
              
         /**
         * Esta funcion permite retornar el numero secuencial del grupo 
         * @param con : recive la coneccion de la base
         * @return retorna el numero de secuencial del grupo
         */
          public int getNumeroOrdenGrupo_anterior(java.sql.Connection con){
            int intgrp = 1;
            try{
                try{
                    if (con!=null){
                
                        java.sql.Statement stm =con.createStatement();
                        String strSQL= "SELECT Max(ne_secgrp)+1 as grupo  FROM tbm_cabMovInv";
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intgrp = rst.getInt(1);
                        }
                        rst.close();
                        stm.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return intgrp;
        }
            
          
          
          
          
          
       /**
        * Esta Funcion permite obtener el codigo maximo sumado +1 de la tabla tbm_cabmovinv filtrado por empresa local y tipoDocumento.
        * @param  con : recive la coneccion de la base
        * @param  intCodTipDoc : recive el tipo de documento 
        *
        * @return   : retorna el codigo de documento
        */ 
         public int getCodigoDocumento(java.sql.Connection con, int intCodTipDoc){ 
            int intDoc = -1;
            java.sql.Statement stm; 
            java.sql.ResultSet rst;
            try{
                 if (con!=null){
                        stm =con.createStatement();
                        String strSQL= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabMovInv WHERE " +
                          " co_emp ="+objZafParSis.getCodigoEmpresa()+" AND co_loc ="+objZafParSis.getCodigoLocal()+" AND co_tipdoc ="+intCodTipDoc;
                        
                        rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intDoc = rst.getInt("codoc");
                        }
                        rst.close();
                        stm.close();
                    }
                }catch(java.sql.SQLException e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
                 catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
            return intDoc;
        }  
          
         
         
         
         
         
            
       /**
        * Esta Funcion permite obtener el codigo maximo sumado +1 de la tabla tbm_cabpag filtrado por empresa local y tipoDocumento.
        * @param  con : recive la coneccion de la base
        * @param  intCodTipDoc : recive el tipo de documento 
        *
        * @return   : retorna el codigo de documento
        */   
         public int getCodDocTbmCabPag(java.sql.Connection con, int intCodTipDoc){ 
            int intDoc = 1;
            java.sql.Statement stm; 
            java.sql.ResultSet rst;
            try{
                 if (con!=null){
                        stm =con.createStatement();
                        String strSQL= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabpag WHERE " +
                          " co_emp ="+objZafParSis.getCodigoEmpresa()+" AND co_loc ="+objZafParSis.getCodigoLocal()+" AND co_tipdoc ="+intCodTipDoc;
                        
                        rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intDoc = rst.getInt("codoc");
                        }
                        rst.close();
                        stm.close();
                    }
                }catch(java.sql.SQLException e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
                 catch(Exception e){ objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
            return intDoc;
        }  
          
         
         
          
          
        public int getCodigoDocumento(int intCodTipDoc){ 
            int intDoc = 1;
            try{
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                
                        java.sql.Statement stm =con.createStatement();
                        String strSQL= "SELECT case when Max(co_doc) is null then  1 else Max(co_doc)+1  end  as codoc  FROM tbm_cabMovInv where " +
                          " co_emp = " + objZafParSis.getCodigoEmpresa() + 
                          " and co_loc = " + objZafParSis.getCodigoLocal() +
                          " and co_tipdoc = " + intCodTipDoc ;
                        
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if(rst.next()){
                            intDoc = rst.getInt("codoc");
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
            return intDoc;
        }
        
        
        
        
        
        
        /**
         * Metodo para verificar si un documento tiene pagos asociados.
         *  @param: Codigo Empresa
         *  @param: Codigo Local
         *  @param: Tipo de Documento
         *  @param: Codigo Documento
         *  @return true si existen pago
         *          false si no existen pagos
         *  @autor: jsalazar  
         *
         */
        public boolean isPagoDocumento(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
            boolean blnRes = false;
            try{                
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        String strSQL = " select abs(sum(nd_abo)) as saldo from tbm_pagmovinv " +
                                        " where co_emp = " + intCodEmp + " and co_loc = " + intCodLoc + " and co_tipdoc = "+ intCodTipDoc +" and co_doc= " + intCodDoc;
                        java.sql.Statement stm = con.createStatement();
                        System.out.println(strSQL);
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        if (rst.next()){
                           if (rst.getDouble("saldo")>0) {
                               MensajeInf("<html>El documento registra <FONT COLOR=\"blue\">pagos</FONT> asociados por valor de <FONT COLOR=\"blue\">$"+rst.getDouble("saldo")+"</font></html>");
                               blnRes = true;
                           }
                        }
                        rst.close();
                        stm.close();
                        con.close();
                    }  
                }catch(java.sql.SQLException e){
                    con.close();
                    blnRes = false;
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                blnRes = false;
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return blnRes;

        }
          
        
        
        
        
         /**
         * Metodo para verificar si un documento tiene pagos asociados.
         *  @param: Codigo Empresa
         *  @param: Codigo Local
         *  @param: Tipo de Documento
         *  @param: Codigo Documento
         *  @return true si existen pago
         *          false si no existen pagos
         *  @autor: jayapata 
         *   
         */
        public boolean isPagoDocumento_aso(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
            boolean blnRes = false;
            try{                   
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        String strSQL = " select a.nd_abo from tbm_detpag as a" +
                        " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)" +
                        " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                        " and b.st_reg not in ('I','E')";
                          
                        java.sql.Statement stm = con.createStatement();
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        
                      //  System.out.println(">>> "+ strSQL );
                           
                        if (rst.next()){
                               MensajeInf("<html>Este documento <FONT COLOR=\"blue\"> tiene Pagos aplicados </FONT> </html>");
                               blnRes = true;
                        }
                        rst.close();
                        stm.close();
                        con.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    blnRes = false;
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                blnRes = false;
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return blnRes;

        }
        



/**
* Metodo para verificar si un documento tiene pagos de retencion.
*  @param: Codigo Empresa
*  @param: Codigo Local
*  @param: Tipo de Documento
*  @param: Codigo Documento
*  @return true si existen pago
*          false si no existen pagos
*  @autor: jayapata
*
*/
public boolean isPagoDocRet(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
boolean blnRes = false;
try{
 java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
 if(con!=null){
    String strSQL="SELECT a.co_doc FROM tbm_pagmovinv as a " +
    " inner join tbm_detpag as a1 on (a1.co_emp=a.co_emp and a1.co_locpag=a.co_loc and a1.co_tipdocpag=a.co_tipdoc and a1.co_docpag=a.co_doc and a1.co_regpag=a.co_reg) "+
    " inner join tbm_cabpag as a2 on (a2.co_emp=a1.co_emp and a2.co_loc=a1.co_loc and a2.co_tipdoc=a1.co_tipdoc and a2.co_doc=a1.co_doc ) "+
    " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+intCodLoc+" AND a.co_tipdoc="+intCodTipDoc+" AND a.co_doc="+intCodDoc+"  AND  a.nd_porret != 0  AND a2.st_reg not in ('I','E') ";
    java.sql.Statement stm = con.createStatement();
    java.sql.ResultSet rst = stm.executeQuery(strSQL);
    if(rst.next()){
      // MensajeInf("<html>La Order de Compra <FONT COLOR=\"blue\"> tiene Retenciones  aplicados. </FONT> </html>");
       blnRes = true;
    }
    rst.close();
    rst=null;
    stm.close();
    stm=null;
    con.close();
    con=null;
}}catch(java.sql.SQLException e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
  catch(Exception e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
return blnRes;
}


  
/**
* Metodo para verificar si el valor pendiente de pago es >= a la dev.
*  @param: de conexcion a la base.
*  @param: Codigo Empresa
*  @param: Codigo Local
*  @param: Tipo de Documento
*  @param: Codigo Documento
*  @param: Valor de la Devolucion
*  @return true si realiza cruze     false no se realiza cruze
*  @autor: jayapata
*/
public boolean isCruceComDev(java.sql.Connection conn, int intCodEmp, String strCodLoc, String strCodTipDoc, int intCodDoc, double dblValDev ){
boolean blnRes = false;
try{
 if(conn!=null){
    String strSQL="SELECT * FROM ( " +
    " SELECT (sum(a.mo_pag) - sum(abs(nd_abo))) as valpenpag FROM tbm_pagmovinv as a " +
    " WHERE a.co_emp="+intCodEmp+" AND a.co_loc="+strCodLoc+" AND a.co_tipdoc="+strCodTipDoc+" AND a.co_doc="+intCodDoc+" AND a.st_reg in ('A','C') " +
    " ) AS x WHERE valpenpag >= "+dblValDev;
    java.sql.Statement stm = conn.createStatement();
    java.sql.ResultSet rst = stm.executeQuery(strSQL);
    if(rst.next()){
       blnRes = true;
    }
    rst.close();
    rst=null;
    stm.close();
    stm=null;
}}catch(java.sql.SQLException e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
  catch(Exception e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
return blnRes;
}









        
        /**
         *  Función que permite verificar si un documento tiene pagos asociados.
         *  @param Conn: concecion de la base
         *  @param intCodEmp: Codigo Empresa
         *  @param intCodLoc: Codigo Local
         *  @param intCodTipDoc: Tipo de Documento
         *  @param intCodDoc: Codigo Documento
         * 
         *  @return true : si existen pago
         *  @return false : si no existen pagos
         * 
         *  @autor: jayapata 
         */
        public boolean isPagoDocumento_aso(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc){
            boolean blnRes = false;
            String strSQL="";
            java.sql.Statement stm;
            java.sql.ResultSet rst;
            try{                   
                 if (conn!=null){
                     stm = conn.createStatement();
                     
                        strSQL = " select a.nd_abo from tbm_detpag as a" +
                        " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)" +
                        " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                        " and b.st_reg not in ('I','E')";
                       
                        rst = stm.executeQuery(strSQL);
                        if (rst.next()){
                               MensajeInf("<html>Este documento <FONT COLOR=\"blue\"> tiene Pagos aplicados </FONT> </html>");
                               blnRes = true;
                        }
                        rst.close();
                        rst=null;
                        stm.close();
                        stm=null;
                }}catch(java.sql.SQLException e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }      
                  catch(Exception e){ blnRes = false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  } 
            return blnRes;
        }
        
        
        
        
       /**
         * Metodo para verificar si un documento tiene pagos asociados.
         *  @param Conn: concecion de la base 
         *  @param: Codigo Empresa
         *  @param: Codigo Local
         *  @param: Tipo de Documento
         *  @param: Codigo Documento
         *  @param: Numero de Documento
         *  @return true si existen pago
         *          false si no existen pagos
         *  @autor: jayapata 
         *   
         */
        public boolean isPagoDocumento_aso(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String NumDoc ){
            boolean blnRes = false;
            java.sql.Statement stmLoc;
            java.sql.ResultSet rstLoc; 
            String strSQL=""; 
            try{                   
                 if (conn!=null){
                     stmLoc=conn.createStatement();
                     
                      strSQL = " select a.nd_abo from tbm_detpag as a" +
                      " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)" +
                      " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                      " and b.st_reg not in ('I','E')";
                      //System.out.println(" >> "+strSQL );
                      rstLoc = stmLoc.executeQuery(strSQL);
                      
                      if(rstLoc.next()){
                          MensajeInf("<html>La Orden de Compra # "+NumDoc+" <FONT COLOR=\"blue\"> tiene Pagos aplicados </FONT> </html>");
                       }else blnRes = true;
                        rstLoc.close();
                        rstLoc=null;
                        stmLoc.close();
                        stmLoc=null;
            
              }}catch(java.sql.SQLException e){ blnRes = false;  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }     
                catch(Exception e){ blnRes = false;  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e); }
            return blnRes;
        }
        
        
        
        
        /**
         * Metodo para verificar si un documento tiene pagos asociados.
         *  @param: Codigo Empresa
         *  @param: Codigo Local
         *  @param: Tipo de Documento
         *  @param: Codigo Documento
         *  @param: Numero de Documento
         *  @return true si existen pago
         *          false si no existen pagos
         *  @autor: jayapata 
         *   
         */
        public boolean isPagoDocumento_aso(int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String NumDoc ){
            boolean blnRes = false;
            try{                   
                java.sql.Connection con = java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());
                try{
                    if (con!=null){
                        String strSQL = " select a.nd_abo from tbm_detpag as a" +
                        " inner join tbm_cabpag as b on (a.co_emp=b.co_emp and a.co_loc=b.co_loc and a.co_tipdoc=b.co_tipdoc and a.co_doc=b.co_doc)" +
                        " where a.co_emp=" + intCodEmp + " and a.co_locpag=" + intCodLoc + " and a.co_docpag="+ intCodDoc+" and a.co_tipdocpag="+ intCodTipDoc +" " +
                        " and b.st_reg not in ('I','E')";
                          
                        java.sql.Statement stm = con.createStatement();
                        java.sql.ResultSet rst = stm.executeQuery(strSQL);
                        
                           
                        if (rst.next()){
                               MensajeInf("<html>La Orden de Compra # "+NumDoc+" <FONT COLOR=\"blue\"> tiene Pagos aplicados </FONT> </html>");
                               blnRes = true;
                        }
                        rst.close();
                        stm.close();
                        con.close();
                    }
                }catch(java.sql.SQLException e){
                    con.close();
                    blnRes = false;
                    objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                    
                }
            }catch(Exception e){
                blnRes = false;
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
            return blnRes;

        }
        
        
            


public boolean vericaPag(java.sql.Connection conn, int intCodEmp, String strCodLocm, String strCodTipDoc, int intCodDoc, double dblSalDev, double dblTotDev ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       strSql="SELECT pag, dif, (100-((dif*100)/pag)) as pordes FROM( " +
       " SELECT (pag-"+dblTotDev+") as pag, (pag-"+(dblSalDev+dblTotDev)+") as dif FROM (  " +
       " select sum(mo_pag) as pag from tbm_pagmovinv WHERE co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C') "+
       " ) AS x ) AS x where dif > 0  ";
       System.out.println(".--->" + strSql );
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
          blnRes =true ;
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
 return blnRes;
}

public boolean vericaPagCru(java.sql.Connection conn, int intCodEmp, String strCodLocm, String strCodTipDoc, int intCodDoc, double dblSalDev, double dblTotDev ){
  boolean blnRes=false;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       strSql="SELECT pag, dif, (100-((dif*100)/pag)) as pordes FROM( " +
        " SELECT (pag-"+dblTotDev+") as pag, (pag-"+(dblSalDev+dblTotDev)+") as dif FROM (  " +
       " select sum(mo_pag) as pag from tbm_pagmovinv WHERE co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C') "+
       " ) AS x ) AS x where dif > 0  ";
       //System.out.println(".--->" + strSql );
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){
          blnRes = vericaRestrucPag(conn, intCodEmp, strCodLocm, strCodTipDoc, intCodDoc, rstLoc.getDouble("pordes") );
       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;

 }}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
 return blnRes;
}

private boolean vericaRestrucPag(java.sql.Connection conn, int intCodEmp, String strCodLocm, String strCodTipDoc, int intCodDoc, double dblPorDes ){
  boolean blnRes=true;
  java.sql.Statement stmLoc,stmLoc01, stmLoc02;
  java.sql.ResultSet rstLoc,rstLoc01;
  String strSql="";
  int intCodReg=0;
  int intNumRegPag=0;
  double dblValTotoRet=0;
  double dblValTotPag=0;
  double dblValTotPagN=0;

  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();
       stmLoc02=conn.createStatement();


        strSql="SELECT " +
        " ( select  sum(mo_pag) as pag  from tbm_pagmovinv  " +
        " where co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C')  " +
        " ) as pagtot " +
        " ,(  SELECT  sum( (mo_pag - round( (round((nd_basimp-( nd_basimp*( "+dblPorDes+" /100))),2)*(nd_porret/100)) ,2) ) ) as  pag " +
        " from tbm_pagmovinv " +
        " where co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C') " +
        " and nd_porret!=0 " +
        " ) as pagret " +
        " ,( " +
        " select  count(co_reg) from tbm_pagmovinv " +
        " where  co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+" and co_doc="+intCodDoc+" and st_reg in ('A','C')  " +
        " and nd_porret=0  ) as cant ";
       // System.out.println(".--->" + strSql );
        rstLoc=stmLoc.executeQuery(strSql);
        while(rstLoc.next()){
          dblValTotPag=rstLoc.getDouble("pagtot");
          dblValTotoRet=rstLoc.getDouble("pagret");
          intNumRegPag=rstLoc.getInt("cant");
        }
        rstLoc.close();
        rstLoc=null;

       dblValTotoRet=(dblValTotoRet/intNumRegPag);

       strSql="SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg, a.st_reg, a.nd_porret, a.mo_pag, " +
       " round( (round((a.nd_basimp-( a.nd_basimp*("+dblPorDes+"/100))),2)*(a.nd_porret/100)) ,2)  as pag  , " +
       " round((a.nd_basimp-( a.nd_basimp*( "+dblPorDes+" /100))),2) as ndbasimp " +
       " , round( (a.mo_pag+abs("+dblValTotoRet+")) , 2) as pagsinret " +
       " FROM tbm_pagmovinv as a" +
       " WHERE a.co_emp="+intCodEmp+" and a.co_loc="+strCodLocm+" and a.co_tipdoc="+strCodTipDoc+" " +
       " and a.co_doc="+intCodDoc+" and a.st_reg in ('A','C') order by a.co_reg ";
       //System.out.println(".--->" + strSql );
       rstLoc=stmLoc.executeQuery(strSql);
       while(rstLoc.next()){

            strSql="SELECT (max(co_reg)+1) as coreg FROM tbm_pagmovinv " +
            " where co_emp="+rstLoc.getString("co_emp")+" and co_loc="+rstLoc.getString("co_loc")+" and co_tipdoc="+rstLoc.getString("co_tipdoc")+" and co_doc="+rstLoc.getString("co_doc");
            rstLoc01=stmLoc01.executeQuery(strSql);
            while(rstLoc01.next()){
               intCodReg=rstLoc01.getInt("coreg");
            }
            rstLoc01.close();
            rstLoc01=null;

            strSql="";
            if( rstLoc.getString("st_reg").equals("A")){
              strSql=" UPDATE tbm_pagmovinv SET st_reg='F' WHERE co_emp="+rstLoc.getString("co_emp")+" and co_loc="+rstLoc.getString("co_loc")+" " +
              " and co_tipdoc="+rstLoc.getString("co_tipdoc")+" and co_doc="+rstLoc.getString("co_doc")+" AND co_reg="+rstLoc.getString("co_reg");
            }else if( rstLoc.getString("st_reg").equals("C")){
              strSql=" UPDATE tbm_pagmovinv SET st_reg='I' WHERE co_emp="+rstLoc.getString("co_emp")+" and co_loc="+rstLoc.getString("co_loc")+" " +
              " and co_tipdoc="+rstLoc.getString("co_tipdoc")+" and co_doc="+rstLoc.getString("co_doc")+" AND co_reg="+rstLoc.getString("co_reg");
            }

            if( rstLoc.getDouble("nd_porret") != 0 ){

                strSql +=" ; INSERT INTO  tbm_pagmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, "+
                " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,  "+
                " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "+
                " fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, "+
                " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp)  "+
                " SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodReg+", ne_diacre, fe_ven, "+
                " co_tipret, nd_porret, tx_aplret, "+rstLoc.getDouble("pag")+", ne_diagra, nd_abo, st_sop,  "+
                " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "+
                " fe_venchq, nd_monchq, co_prochq, 'C', st_regrep, fe_ree, co_usrree, "+
                " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, "+rstLoc.getDouble("ndbasimp")+" "+
                " FROM tbm_pagmovinv "+
                " where co_emp="+rstLoc.getString("co_emp")+" and co_loc="+rstLoc.getString("co_loc")+" and co_tipdoc="+rstLoc.getString("co_tipdoc")+" and co_doc="+rstLoc.getString("co_doc")+" AND co_reg="+rstLoc.getString("co_reg");

                dblValTotPagN += rstLoc.getDouble("pag");
            }else{
                 strSql +=" ; INSERT INTO  tbm_pagmovinv(co_emp, co_loc, co_tipdoc, co_doc, co_reg, ne_diacre, fe_ven, "+
                " co_tipret, nd_porret, tx_aplret, mo_pag, ne_diagra, nd_abo, st_sop,  "+
                " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "+
                " fe_venchq, nd_monchq, co_prochq, st_reg, st_regrep, fe_ree, co_usrree, "+
                " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, nd_basimp)  "+
                " SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodReg+", ne_diacre, fe_ven, "+
                " co_tipret, nd_porret, tx_aplret, "+rstLoc.getDouble("pagsinret")+", ne_diagra, nd_abo, st_sop,  "+
                " st_entsop, st_pos, co_banchq, tx_numctachq, tx_numchq, fe_recchq,  "+
                " fe_venchq, nd_monchq, co_prochq, 'C', st_regrep, fe_ree, co_usrree, "+
                " tx_comree, st_autpag, co_ctaautpag, tx_obs1, tx_codsri, "+rstLoc.getDouble("ndbasimp")+" "+
                " FROM tbm_pagmovinv "+
                " where co_emp="+rstLoc.getString("co_emp")+" and co_loc="+rstLoc.getString("co_loc")+" and co_tipdoc="+rstLoc.getString("co_tipdoc")+" and co_doc="+rstLoc.getString("co_doc")+" AND co_reg="+rstLoc.getString("co_reg");

                 strSql +=" ; UPDATE tbm_detpag set co_regpag="+intCodReg+" FROM ( " +
                 " SELECT * from ( " +
                 " SELECT a.co_emp, a.co_loc, a.co_tipdoc, a.co_doc, a.co_reg , co_regpag ,case when  a1.st_reg in ('I','E') then 'N' else 'S' end as streg " +
                 " from tbm_detpag as a " +
                 " inner join tbm_cabpag as a1 on (a1.co_emp=a.co_emp and a1.co_loc=a.co_loc and a1.co_tipdoc=a.co_tipdoc and a1.co_doc=a.co_doc ) " +
                 " WHERE a.co_emp="+rstLoc.getString("co_emp")+" and a.co_locpag="+rstLoc.getString("co_loc")+" and a.co_tipdocpag="+rstLoc.getString("co_tipdoc")+" and a.co_docpag="+rstLoc.getString("co_doc")+" and a.co_regpag="+rstLoc.getString("co_reg")+" " +
                 " ) as x WHERE streg='S' " +
                 " ) as x WHERE tbm_detpag.co_emp=x.co_emp and tbm_detpag.co_loc=x.co_loc and tbm_detpag.co_tipdoc=x.co_tipdoc and tbm_detpag.co_doc=x.co_doc and tbm_detpag.co_reg=x.co_reg ";

                
                dblValTotPagN += rstLoc.getDouble("pagsinret");

            }

            stmLoc02.executeUpdate(strSql);
            if( !actualizarAutPago(conn, rstLoc.getInt("co_emp"), rstLoc.getInt("co_loc"), rstLoc.getInt("co_tipdoc"), rstLoc.getInt("co_doc"), rstLoc.getInt("co_reg"), intCodReg ) ){
                blnRes=false; 
                break;
            }

       }
       rstLoc.close();
       rstLoc=null;


       dblValTotPagN = dblValTotPagN-dblValTotPag;
       if(dblValTotPagN != 0 ){
         strSql=" UPDATE tbm_pagmovinv SET mo_pag = mo_pag-"+dblValTotPagN+" " +
         " WHERE co_emp="+intCodEmp+" and co_loc="+strCodLocm+" and co_tipdoc="+strCodTipDoc+"  and co_doc="+intCodDoc+" and co_reg="+intCodReg;
         stmLoc02.executeUpdate(strSql);
       }


       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;
       stmLoc02.close();
       stmLoc02=null;
      
 }}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
 return blnRes;
}



private boolean actualizarAutPago(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodReg, int intCodRegNue ){
 boolean blnRes=true;
  java.sql.Statement stmLoc,stmLoc01;
  java.sql.ResultSet rstLoc;
  String strSql="";
  try{
     if(conn!=null){
       stmLoc=conn.createStatement();
       stmLoc01=conn.createStatement();

       strSql="SELECT co_emp, co_loc, co_tipdoc, co_doc  " +
       " FROM tbm_autpagmovinv WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg="+intCodReg+" and st_reg='A' ";
       rstLoc=stmLoc.executeQuery(strSql);
       if(rstLoc.next()){

         strSql="INSERT INTO tbm_autpagmovinv (co_emp, co_loc, co_tipdoc, co_doc, co_reg, co_aut, fe_autpag, co_usrautpag, tx_comautpag, co_ctaautpag, st_reg, st_regrep) " +
         "  SELECT co_emp, co_loc, co_tipdoc, co_doc, "+intCodRegNue+", co_aut, fe_autpag, co_usrautpag, tx_comautpag, co_ctaautpag, st_reg, st_regrep " +
         " FROM tbm_autpagmovinv WHERE co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg="+intCodReg+" and st_reg='A'  ;" +
         " " +
         " UPDATE tbm_autpagmovinv SET st_reg='I' WHERE  co_emp="+intCodEmp+" and co_loc="+intCodLoc+" and co_tipdoc="+intCodTipDoc+" and co_doc="+intCodDoc+" and co_reg="+intCodReg+" ; ";
         stmLoc01.executeUpdate(strSql);

       }
       rstLoc.close();
       rstLoc=null;
       stmLoc.close();
       stmLoc=null;
       stmLoc01.close();
       stmLoc01=null;

      
 }}catch(java.sql.SQLException Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
   catch(Exception Evt) { blnRes=false; objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt); }
 return blnRes;
}


          
        
        
        
    private void MensajeInf(String strMsg){
            javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
            String strTit;
            strTit="Zafiro";
            obj.showMessageDialog(new javax.swing.JInternalFrame(),strMsg,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
        
}
