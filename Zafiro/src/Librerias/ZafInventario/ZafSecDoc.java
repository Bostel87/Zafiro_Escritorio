/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
     
package Librerias.ZafInventario;

import java.sql.DriverManager;

/**
 *
 * @author jayapata   Mail: jaya_gar@hotmail.com     
 * 
 * @Version: 0.1
 */
public class ZafSecDoc {
   Librerias.ZafParSis.ZafParSis objZafParSis;
   Librerias.ZafUtil.ZafUtil objUti;
   javax.swing.JInternalFrame jIntfra=null;
   javax.swing.JDialog jDialo=null;
   int INTCODEMPGRP=0;
   java.sql.Connection conGlo;
   private String strVersion="ZafSecDoc v 0.2";
   private String strSql="";
  
      /** Creates a new instance of ZafInv */
    public ZafSecDoc(javax.swing.JInternalFrame jfrthis, Librerias.ZafParSis.ZafParSis obj){
      try{  
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jIntfra = jfrthis;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
            System.out.println(strVersion);
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jfrthis, e);  }        
    }
 
    public ZafSecDoc(javax.swing.JDialog jDial, Librerias.ZafParSis.ZafParSis obj){
       try{  
           this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            jDialo = jDial;
            objUti = new Librerias.ZafUtil.ZafUtil();
            INTCODEMPGRP=objZafParSis.getCodigoEmpresaGrupo();
            System.out.println(strVersion);
        }catch (CloneNotSupportedException e){ objUti.mostrarMsgErr_F1(jDial, e);  }        
    } 
    
      
    

    /**    
    * Funcion que abre conección con la base de datos.
    * @param INTCODREGCEN 1 si hay coneccion remota 0 no hay
    */
   public void abrirConnBD(int INTCODREGCEN){
    try{
      if(INTCODREGCEN != 0)
          conGlo=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(INTCODREGCEN), objZafParSis.getUsuarioBaseDatos(INTCODREGCEN), objZafParSis.getClaveBaseDatos(INTCODREGCEN));
       else
           conGlo=java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos());


     }catch(java.sql.SQLException e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
        catch(Exception e){  objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  }
   }

   

    /**
     * Funcion que permite cerrar la coneccion de la base.
     */
    public void cerrarConnBD(){
        try{
            if(conGlo != null){
                conGlo.close();
                conGlo=null;
            }
        }
        catch(java.sql.SQLException e){  
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  
        }
        catch(Exception e){  
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  
        }
    }


    /**
     * Esta funcion permite extraer el numero de secuencia de documento.
     * @return numero de secuencia de la grupo.
     */
    public int getNumSecDoc(int intCodEmp ){
        int intNumSec=0;
        java.sql.Statement stmLoc, stmLoc01;
        java.sql.ResultSet rstloc;
        String strSql="";
        try{
            stmLoc=conGlo.createStatement();
            stmLoc01=conGlo.createStatement();

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
        }catch(java.sql.SQLException e){  
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  
        }
        catch(Exception e){  
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);  
        }
        return intNumSec;
    }
    
    /**
     * Se guardan varios documentos con un solo commit, por lo cual necesite un metodo que revisara todas las transacciones
     * que se guardan en  un seguimiento despues del commit.
     * @param padre
     * @param objParSis
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */

    public boolean secuenciaEmpresaSegunSeguimiento(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                stmLoc = conLoc.createStatement();
                strSql="";
                strSql+=" SELECT a1.co_seg,a1.co_reg, a1.co_empRelCabMovInv, a1.co_locRelCabMovInv, a1.co_tipDocRelCabMovInv, a1.co_docRelCabMovInv \n";
                strSql+=" FROM tbm_cabSegMovInv as a1  \n";
                strSql+=" INNER JOIN ( \n";
                strSql+="       SELECT a2.co_seg \n";
                strSql+="       FROM tbm_cabMovInv as a1  \n";
                strSql+="       INNER JOIN tbm_cabSegMovInv as a2 ON (a1.co_emp=a2.co_empRelCabMovInv AND a1.co_loc=a2.co_locRelCabMovInv AND \n";
                strSql+="                                             a1.co_tipDoc=a2.co_tipDocRelCabMovInv AND a1.co_doc=a2.co_docRelCabMovInv)  \n";
                strSql+="       WHERE a1.co_emp="+CodEmp+" AND a1.co_loc="+CodLoc+" and a1.co_tipDoc="+CodTipDoc+" AND a1.co_doc="+CodDoc+" \n";
                strSql+=" ) AS a2 ON (a1.co_seg=a2.co_seg) \n";
                strSql+=" WHERE a1.co_empRelCabMovInv IS NOT NULL  \n";
                strSql+=" ORDER BY co_reg \n";
                System.out.println("secuenciaEmpresaSegunSeguimiento: "+strSql);
                rstLoc  = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(!secuenciaEmpresa(padre,objParSis,rstLoc.getInt("co_empRelCabMovInv"),rstLoc.getInt("co_locRelCabMovInv"),rstLoc.getInt("co_tipDocRelCabMovInv"),rstLoc.getInt("co_docRelCabMovInv"))){
                        blnRes=false;
                    }
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
		conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }

    
    
    /**
     * Metodo para revisar si la secuencia de empresa esta dupicada, en caso de estarla se modifica la secuencia de la duplicada, 
     * se modifican todas las secuencias posteriores tambien, 
     * @param padre
     * @param objParSis
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    public boolean secuenciaEmpresa(java.awt.Component padre, Librerias.ZafParSis.ZafParSis objParSis, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=false;
        java.sql.Connection conLoc;
        try{
            conLoc = DriverManager.getConnection(objParSis.getStringConexion(), objParSis.getUsuarioBaseDatos(), objParSis.getClaveBaseDatos());
            if(conLoc!=null){
                if(IsSecuenciaEmpresaRepetida(padre, conLoc, CodEmp,CodLoc,CodTipDoc,CodDoc)){
                    if(modificarSecuenciasEmpresas(padre,conLoc,CodEmp,CodLoc,CodTipDoc,CodDoc)){
                        //PENDIENTE COSTEO
                        blnRes=true;
                    }
                }
                else{
                    blnRes=true;
                }
                conLoc.close();
                conLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Revision de secuencia repetida
     * @param padre
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    private boolean IsSecuenciaEmpresaRepetida(java.awt.Component padre, java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=false;
        java.sql.Statement stmLoc;
        java.sql.ResultSet rstLoc;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement();
                strSql="";
                strSql+=" SELECT count(co_emp) as cuenta \n";
                strSql+=" FROM tbm_cabMovInv as a1 \n";
                strSql+=" WHERE co_emp="+CodEmp+" and  \n";
                strSql+="       ne_secEmp=(SELECT ne_secEmp \n";
                strSql+="                  FROM tbm_cabMovInv as a1  \n";
                strSql+="                  WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" and co_tipDoc="+CodTipDoc+" and co_doc="+CodDoc+" \n";
                strSql+="                 )   \n";
                rstLoc = stmLoc.executeQuery(strSql);
                if(rstLoc.next()){
                    if(rstLoc.getInt("cuenta")>1){
                        blnRes=true;
                    }  
                }
                rstLoc.close();
                rstLoc=null;
                stmLoc.close();
                stmLoc=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }
    
    /**
     * Modifica las secuencias 
     * @param padre
     * @param conExt
     * @param CodEmp
     * @param CodLoc
     * @param CodTipDoc
     * @param CodDoc
     * @return 
     */
    
    private boolean modificarSecuenciasEmpresas(java.awt.Component padre, java.sql.Connection conExt, int CodEmp, int CodLoc, int CodTipDoc, int CodDoc){
        boolean blnRes=true;
        java.sql.Statement stmLoc, stmUpd;
        java.sql.ResultSet rstLoc;
        int j=0, intSecEmp=-1;
        try{
            if(conExt!=null){
                stmLoc = conExt.createStatement(); stmUpd = conExt.createStatement();
                strSql="";
                strSql+=" SELECT ne_secEmp,fe_doc , fe_ing, co_emp, co_loc, co_tipDoc, co_doc \n";
                strSql+=" FROM tbm_cabMovInv as a1  \n";
                strSql+=" WHERE co_emp="+CodEmp+" and  \n";
                strSql+="       ne_secEmp>=(SELECT ne_secEmp \n";
                strSql+="                   FROM tbm_cabMovInv as a1  \n";
                strSql+="                   WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+" and co_tipDoc="+CodTipDoc+" and co_doc="+CodDoc+" \n";
                strSql+="                   )  \n";
                strSql+=" ORDER BY fe_doc ,  ne_secEmp  \n";
                System.out.println("modificarSecuenciasEmpresas: "+strSql);
                rstLoc = stmLoc.executeQuery(strSql);
                while(rstLoc.next()){
                    if(j>0){
                        strSql="";
                        strSql+=" UPDATE tbm_cabMovInv SET ne_secEmp = ne_secEmp + 1  \n";
                        strSql+=" WHERE co_emp="+CodEmp+" AND co_loc="+CodLoc+"  \n";
                        strSql+="       co_tipDoc="+CodTipDoc+" AND co_doc="+CodDoc+" ;  \n";
                        System.out.println("UPDATE modificarSecuenciasEmpresas: "+strSql);
                        stmUpd.executeUpdate(strSql);
                    }
                    j++;
                    intSecEmp = rstLoc.getInt("ne_secEmp");
                }
                rstLoc.close();
                rstLoc=null;
                intSecEmp=intSecEmp+1;
                strSql=" UPDATE tbm_emp SET ne_secUltDocTbmCabMovInv ="+intSecEmp+" WHERE co_emp="+CodEmp+"; ";
                System.out.println("modificarSecuenciasEmpresas = ne_secUltDocTbmCabMovInv: "+strSql);
                stmUpd.executeUpdate(strSql);
                stmLoc.close();
                stmLoc=null;
                stmUpd.close();
                stmUpd=null;
            }
        }
        catch(java.sql.SQLException e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        catch (Exception e){
            blnRes=false;
            objUti.mostrarMsgErr_F1(padre, e);
        }
        return blnRes;
    }



}
