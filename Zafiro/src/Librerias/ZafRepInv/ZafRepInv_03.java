/*
 * ZafRepInv_03.java
 *
 * Created on 21 de julio de 2005, 9:07
 */

package Librerias.ZafRepInv;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafRepInv.ZafRepInv_02;
import Librerias.ZafRepInv.ZafRepInv_04;
import Librerias.ZafInventario.ZafInventario;
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
public class ZafRepInv_03 {
    
    /** Creates a new instance of ZafRepInv_03 */
        private ZafParSis objZafParSis;
        private ZafUtil objUti;
        private ZafRepInv_04 objZafDia;
        private ZafInventario objZafInv;
        private int intCodEmp;
        private int intCodLoc;
        private int intTipDoc;
        //Numero del documento egreso (cotven o factura)
        private int intNumDoc;
        //Numero del documento ingreso (cotcom u order compra)
        private int intNumCot;
        //Numero del orden del documento.
        private int intOrdDoc; 
        private int intNumLine; //Numeo de lineas o registros maximos para ese documento
        private int intNumReg; //Numero de registros en el documento
        private double dblSubTotal;
        private boolean blnEstado; //true para nuevo - false para modificado
        private int intco_cli, intco_pro, intco_ven, intco_com , intco_forpag; //datos adicionales al documento 
        private int intctadeb,intctahab; //cuentas contables del documento
        private double dblivacom, dblivaven;
        public Vector vecRegDet;
        
        //Conexion
        private Connection con,conAux;
        private Statement stm,stmAux;
        private ResultSet rst,rstAux;   
        private String strSQL; 
   
        public ZafRepInv_03(int intparTipDoc, ZafRepInv_02 objparRep, ZafParSis objparSis) 
        {
            objZafParSis = objparSis;
            objUti       = new ZafUtil();
            objZafDia    = new ZafRepInv_04(objparSis);            
            objZafDia .setGlosa("Asiento Generado por Reposicion");            
            objZafInv    = new ZafInventario(new javax.swing.JInternalFrame(),objparSis);
            intCodEmp    = objparRep.getDatCodEmp();
            intCodLoc    = objparRep.getDatCodLoc();
            blnEstado = false;
            intNumLine = 50;
            setTipoDoc (intparTipDoc);   
            AsignarConfDocEmp();
            vecRegDet = new Vector();
        }
        public boolean AgregaDetalle(ZafRepInv_02 objparRep)
        {
            boolean blnAdd = false;
            if (intNumLine > vecRegDet.size()){
                if (objparRep.getDatCodCot()!=0) {
                    intNumDoc = objparRep.getDatCodCot();            
                    intNumCot = objparRep.getDatCotCom();                 
                }
                dblSubTotal += objUti.redondeo(objparRep.getDatStkCan() * objparRep.getDatDscPre(), 6);
                objparRep.AsignarDetItm(objZafParSis);
                vecRegDet.add(objparRep);  
                blnAdd = true;
            }
            return blnAdd;
        }
  
        private void setTipoDoc(int intparTipo)
        {
             intTipDoc = intparTipo;
        }
        public int getTipoDoc()
        {
            return intTipDoc;           
        }
        public void AsignarCodDoc(int intparIndDoc)
        {
            try
            {                       
                conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                
                if (conAux!=null)
                {                
                    stmAux=conAux.createStatement();
                     if (intTipDoc==-1)
                    {  
                            strSQL = "Select count(co_cot) from tbm_cabcotven where co_emp = " + intCodEmp + " and co_loc =" + intCodLoc;              
                            if (objUti.getNumeroRegistro(new javax.swing.JFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                            {
                                strSQL = " Select max(co_cot) as ult_cot from tbm_cabcotven where co_emp = " + intCodEmp + " and co_loc =" + intCodLoc;                
                                //System.out.println ("Sql: "+strSQL);
                                rstAux=stmAux.executeQuery(strSQL);
                                if(rstAux.next())
                                    intNumDoc = Integer.parseInt(rstAux.getString("ult_cot"))+1;                                                        
                                rstAux.close();
                            }else
                                    intNumDoc = 1;                                
                            strSQL = "Select count(co_cot) from tbm_cabcotcom where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal();                
                            if (objUti.getNumeroRegistro(new javax.swing.JFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                            {
                                strSQL = " Select max(co_cot) as ult_cot from tbm_cabcotcom where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc =" + objZafParSis.getCodigoLocal();                
                                //System.out.println ("Sql: "+strSQL);
                                rstAux=stmAux.executeQuery(strSQL);
                                if(rstAux.next())
                                    intNumCot = Integer.parseInt(rstAux.getString("ult_cot"))+ intparIndDoc;                                                        
                                rstAux.close();
                            }else
                                    intNumCot = 1;                                
                    }else{
                        // Para doc. que no son cotizaciones primero se debe cargar el documento y su order AsignarConfDoc(boolean)
                        intNumDoc += intparIndDoc;    
                        intOrdDoc += intparIndDoc;    
                    }
                    stmAux.close();
                    conAux.close();
                }                                
                
            } catch (java.sql.SQLException e) {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            } catch (Exception e)  {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }                    
        }
        public void AsignarConfDocEmp()
        {
            try
            {                                       
                conAux=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                
                if (conAux!=null)
                {                
                    stmAux=conAux.createStatement();
                    strSQL = "";
                    strSQL = " select co_cli , co_pro , co_ven , co_com, co_forpag, nd_ivacom, nd_ivaven ";
                    strSQL += " from tbm_emp as emp left outer join tbm_cabpolrep as cabpol on (emp.co_emp = cabpol.co_emppro)";
                    strSQL += " left outer join tbm_loc as loc on (emp.co_emp = loc.co_emp and cabpol.co_locpro=loc.co_loc)";
                    strSQL += " where emp.co_emp = " + intCodEmp + " and loc.co_loc = " + intCodLoc;                        
                    //System.out.println ("Sql: "+strSQL);
                    rstAux=stmAux.executeQuery(strSQL);
                    if(rstAux.next())
                    {
                        intco_cli = Integer.parseInt(rstAux.getString("co_cli"));                        
                        intco_pro = Integer.parseInt(rstAux.getString("co_pro"));                        
                        intco_ven = Integer.parseInt(rstAux.getString("co_ven"));                        
                        intco_com = Integer.parseInt(rstAux.getString("co_com")); 
                        intco_forpag = Integer.parseInt(rstAux.getString("co_forpag")); 
                        dblivacom = Double.parseDouble(rstAux.getString("nd_ivacom"));
                        dblivaven = Double.parseDouble(rstAux.getString("nd_ivaven"));                                
                    }   
                    else
                    {
                        intco_cli = 0;                        
                        intco_pro = 0;                        
                        intco_ven = 0;                        
                        intco_com = 0;                                 
                        intco_forpag = 0;
                        dblivacom = 0.0;
                        dblivaven = 0.0;                                
                    }
                    rstAux.close();
                    stmAux.close();
                    conAux.close();
                }                    
            } catch (java.sql.SQLException e) {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            } catch (Exception e)  {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }                                
        }
        /**
         * Metodo para asignar la configuracion del documento segun cabtipdoc.
         * Numero de linea, ultimo numero de documento, cuenta debe y haber y verifica si existe un documento pendiente de impresion
         * Para poder seguir ingresando registros.
         * @param: Tipo de Documento para cliente (true) o para proveedor (false)
         */
        public void AsignarConfDoc(boolean blnparCli, boolean blnBandera)
        {
            int intCodDoc=0 ;
            try
            {                                       
                con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());                
                if (conAux!=null)
                {                
                    stm=con.createStatement();
                    if (blnparCli)
                    {
                        strSQL  = "";                    
                        strSQL  = " select emp.co_emp, loc.co_loc, ne_numlin ,co_tipdoccli as co_tipdoc,  co_ctadeb,co_ctahab , co_pro as co_cli ";
                        strSQL += " from tbm_emp as emp left outer join tbm_cabpolrep as cabpol on (emp.co_emp = cabpol.co_empcli) ";
                        strSQL += " left outer join tbm_loc as loc on (emp.co_emp = loc.co_emp and cabpol.co_locpro=loc.co_loc) ";
                        strSQL += " left outer join tbm_cabtipdoc as cabdoc on (loc.co_emp = cabdoc.co_emp and loc.co_loc = cabdoc.co_loc and cabpol.co_tipDocCli = cabdoc.co_tipdoc) ";
                        strSQL += " where emp.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and loc.co_loc = " + objZafParSis.getCodigoLocal() +" and co_emppro = " + intCodEmp + " and co_locpro = " + intCodLoc + " and cabdoc.st_reg='A' ";
                    }else{
                        strSQL  = "";                    
                        strSQL  = " select emp.co_emp, loc.co_loc,ne_numlin ,co_tipdocpro as co_tipdoc,  co_ctadeb,co_ctahab, co_cli ";
                        strSQL += " from tbm_emp as emp left outer join tbm_cabpolrep as cabpol on (emp.co_emp = cabpol.co_emppro)";
                        strSQL += " left outer join tbm_loc as loc on (emp.co_emp = loc.co_emp and cabpol.co_locpro=loc.co_loc)";
                        strSQL += " left outer join tbm_cabtipdoc as cabdoc on (loc.co_emp = cabdoc.co_emp and loc.co_loc = cabdoc.co_loc and cabpol.co_tipdocpro = cabdoc.co_tipdoc)";
                        strSQL += " where emp.co_emp = " + intCodEmp + " and loc.co_loc = " + intCodLoc + "   and co_empcli = "+ objZafParSis.getCodigoEmpresa() +" and co_loccli = " + objZafParSis.getCodigoLocal() +"  and cabdoc.st_reg='A' ";
                    }
                    //System.out.println ("SQL: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if(rst.next()){                        
                        intTipDoc  = Integer.parseInt(rst.getString("co_tipdoc"));
                        intNumLine = Integer.parseInt(rst.getString("ne_numlin"));      
                        intctadeb  = Integer.parseInt(rst.getString("co_ctadeb"));      
                        intctahab  = Integer.parseInt(rst.getString("co_ctahab"));      
                        stmAux     = con.createStatement();
                        strSQL = "";
                        strSQL = " select max(co_doc) as co_doc from tbm_cabmovinv as cabmov where cabmov.co_emp= "+ rst.getString("co_emp") +" and cabmov.co_loc = "+ rst.getString("co_loc") + " and cabmov.co_tipdoc = "+ rst.getString("co_tipdoc") + " and st_reg='R' and co_cli=" + rst.getString("co_cli");
                        //System.out.println ("SQL: "+strSQL);
                        if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0 && blnBandera!=true)
                        {                                                    
                            rstAux = stmAux.executeQuery(strSQL);
                            if (rstAux.next())
                               intCodDoc = Integer.parseInt(rstAux.getString("co_doc"));                            
                            rstAux.close();
                            strSQL = "";
                            strSQL = " Select ne_numlin from tbm_cabtipdoc as cabtip where cabtip.co_emp = "+ rst.getString("co_emp") +" and cabtip.co_loc = "+ rst.getString("co_loc") +" and cabtip.co_tipdoc = "+ rst.getString("co_tipdoc") +" and cabtip.ne_numlin > " ;
                            strSQL +=" (select count(co_reg) as co_reg from tbm_detmovinv as detmov where detmov.co_emp= "+ rst.getString("co_emp") +" and detmov.co_loc = "+ rst.getString("co_loc") +" and detmov.co_tipdoc = "+ rst.getString("co_tipdoc") +" and detmov.co_doc ="+ intCodDoc +")";
                            //System.out.println ("SQL: "+strSQL);
                            if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                            {
                                strSQL = "";
                                strSQL = " Select max(co_reg) as co_reg from tbm_detmovinv as detmov where detmov.co_emp= "+ rst.getString("co_emp") +" and detmov.co_loc = "+ rst.getString("co_loc") +" and detmov.co_tipdoc = "+ rst.getString("co_tipdoc") +" and detmov.co_doc ="+ intCodDoc ;
                                //System.out.println ("SQL: "+strSQL);
                                rstAux = stmAux.executeQuery(strSQL);
                                if (rstAux.next())
                                {
                                    intNumReg = Integer.parseInt(rstAux.getString("co_reg"));                        
                                }
                                rstAux.close();
                                strSQL = "";
                                strSQL = " select ne_orddoc from tbm_cabmovinv  where co_emp = "+ rst.getString("co_emp") +" and  co_loc = "+ rst.getString("co_loc") +" and co_tipdoc = "+ rst.getString("co_tipdoc") +" and co_doc = "+ intCodDoc ;
                                //System.out.println ("SQL: "+strSQL);
                                rstAux = stmAux.executeQuery(strSQL);
                                if (rstAux.next())
                                {                                    
                                    intOrdDoc  =  Integer.parseInt(rstAux.getString("ne_orddoc"));  
                                }
                                rstAux.close();
                                intNumDoc = intCodDoc;         
                                setEstado(true);                
                            }else{                                                                               
                                strSQL = "";
                                strSQL = " select max(ne_orddoc) as ne_orddoc , max(co_doc) as co_doc from tbm_cabmovinv  where co_emp = "+ rst.getString("co_emp") +" and  co_loc = "+ rst.getString("co_loc") +" and co_tipdoc = "+ rst.getString("co_tipdoc");
                                if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                                {
                                    //System.out.println ("SQL1: "+strSQL);
                                    rstAux = stmAux.executeQuery(strSQL);
                                    if (rstAux.next())
                                    {                                    
                                        intOrdDoc  = Integer.parseInt(rstAux.getString("ne_orddoc"));  
                                        intNumDoc  = Integer.parseInt(rstAux.getString("co_doc"));   
                                    }
                                    rstAux.close();                            
                                }else{
                                    intOrdDoc  = 0;
                                    intNumDoc  = 0;
                                }
                            }                                
                        }else{                            
                            strSQL = "";
                            strSQL = " select max(ne_orddoc) as ne_orddoc , max(co_doc) as co_doc from tbm_cabmovinv  where co_emp = "+ rst.getString("co_emp") +" and  co_loc = "+ rst.getString("co_loc") +" and co_tipdoc = "+ rst.getString("co_tipdoc");
                            if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                            {
                                //System.out.println ("SQL2: "+strSQL);
                                rstAux = stmAux.executeQuery(strSQL);
                                if (rstAux.next())
                                {                                    
                                    intOrdDoc  = Integer.parseInt(rstAux.getString("ne_orddoc"));  
                                    intNumDoc  = Integer.parseInt(rstAux.getString("co_doc"));   
                                }
                                rstAux.close();                            
                            }else{
                                intOrdDoc  = 0;
                                intNumDoc  = 0;
                            }
                        }
                        stmAux.close();
                    }else{
                        intNumReg  = 0;
                        intTipDoc  = 0;
                        intNumLine = 0;                        
                        intNumDoc  = 0;                        
                        intctadeb  = 0;      
                        intctahab  = 0;      
                    }
                    rst.close();
                    stm.close();
                    con.close();
                }                                                                
            } catch (java.sql.SQLException e) {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            } catch (Exception e)  {
                //System.out.println(e.toString());
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }                                
        }
        public boolean MoverInventario(java.sql.Connection con, int intCodEmp, int intCodArt, int intCodBod, double dblArtCan, int intAcc )
        {
//            return objZafInv.movInventario(con, intCodEmp, intCodArt, intCodBod,  dblArtCan, intAcc );
            return true;
        }
        public boolean DetalleDiario(int intparCodCta, double dblparValor, String strparTipCta)
        {
            return objZafDia.AddDetalle(intparCodCta, dblparValor, strparTipCta);
        }
        public boolean CrearAsiento(java.sql.Connection conexion, int empresa, int local, int tipoDocumento,int intCodDia)
        {
            return objZafDia.insertarDiario(conexion, empresa, local, tipoDocumento, intCodDia);
        }
        public boolean CrearAsiento(java.sql.Connection conexion, int empresa, int local, int tipoDocumento,int intCodDia,java.util.Date Fecha)
        {
            return objZafDia.insertarDiario(conexion, empresa, local, tipoDocumento, intCodDia,Fecha);
        }
        public void setEstado(boolean blnparEst)
        {
            blnEstado = blnparEst;
        }
        public boolean getEstado()
        {
            return blnEstado;
        }
        public int getNumDoc()
        {
            return intNumDoc;            
        }
        public int getNumCot()
        {
            return intNumCot;            
        }        
        public int getCodEmp()        
        {
            return intCodEmp;            
        }
        public int getCodLoc()
        {
            return intCodLoc;
        }
        public int getForPag()
        {
            return intco_forpag;
        }
        public double getSubTotal()
        {
            return dblSubTotal;
        }
        public int getco_cli()
        {
            return intco_cli;
        }
        public int getco_pro()
        {
            return intco_pro;
        }
        public int getco_ven()
        {
            return intco_ven;
        }
        public int getco_com()
        {
            return intco_com;
        }
        public double getivacom()
        {
            return dblivacom;
        }
        public double getivaven()
        {
            return dblivaven;
        }
        public int getNumLine()
        {
            return intNumLine;
        }
        public int getOrdDoc()
        {
            return intOrdDoc;         
        }
        public int getNumReg()
        {
            return intNumReg;
        }
        public int getCtaDebe()
        {
            return intctadeb;
        }
        public int getCtaHaber()
        {
            return intctahab;
        }
        
}
