/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sistemas4
 */
public class ZafVal {
    
    private ZafImp oi;    
    private String strQuery="";
    private ResultSet rs;
    //private ZafCon oc=new ZafCon();
    private Statement stmSql;
    private  int i=0,c=0;
    private int intRes=0,intResc=0,intRespAut=0,j=0;
    boolean intResp,blnRespDiaGraChq;
    
    
    ZafImpOrd od=new ZafImpOrd();
    
    public ZafVal(ZafImp oi) {
       this.setOi(oi);
    }
    
    public ZafVal(){}
    
    
//    public ZafVal(ZafImp objZafImp, String strConx,String usr, String pwd){
//        this.setOi(objZafImp);
//        this.oc=new ZafCon(strConx, usr, pwd);
//    
//    }

    
    public void ejecuta(Connection con) throws SQLException{

        //ZafMetImp om=new ZafMetImp(oi);
        //ZafMetImp om=new ZafMetImp(oi, this.oc);
        ZafMetImp om=new ZafMetImp(oi);
        intResp=consultarDiGraCli(con);
        blnRespDiaGraChq=consultarDiGraCliChq(con);
        intResc=verificarTipCred(con);
        intRes=verificarTipNoCred(con);
        boolean booFpgChq=consultarForPagChq(con, oi.getFpago(), oi.getEmp());
		boolean booFpgCntEnt=false;
		booFpgCntEnt=consultarForPagConEnt(con, oi.getFpago(), oi.getEmp());//FORMA DE PAGO CONTRA ENTREGA
		
        //intRespAut=verificarEstAutFacElec();
        //<editor-fold defaultstate="collapsed" desc="COMENTADO LUIS PARRALES">
        //cambio LuisParrales, por proceso de contingencia de facturas, elimino espera
//        try {
//          
//          Thread.sleep(4000);
//          } catch (InterruptedException ex) {
//          Logger.getLogger(ZafVal.class.getName()).log(Level.SEVERE, null, ex);
//          }
//        
//        intRespAut=verificarEstAutFacElec();
        
        //        if (intRes==0 && (intResc==1 || intResp==true)) {
        //            om.impresionNormal();
        //        }
        //</editor-fold>
        if (oi.getTipdoc()==1) {
            if (intRes==0 && intResc==1 ) {
                
            //om.impresionNormal(con);
                om.impresionNormal2(con);
            }
        } else {
            int fp=oi.getFpago();
            if (((intRes==0 && (intResc==1 )) || /*intResp==true*/ booFpgCntEnt ||( blnRespDiaGraChq==true && booFpgChq))/*&& intRespAut==1*/) {
                boolean valida=om.validarOD(con);
                try {
                    
                    if (valida==false) {
                    //om.impresionNormal(con);
                     om.impresionNormal2(con);
                    
                }
                } catch (Exception e) {
                    con.rollback();
                }
                
            
            
            }
        }
        System.out.println("el tipo de documento es: "+oi.getTipdoc());
    }
    /**
     * <h2>VERIFICA DIAS DE GRACIA DE UN DETERMINADO CLIENTE</h2>
     * <P>Esta función permite verificar dias de gracia de acuerdo a una empresa y un cliente, para
     * la generación de ordenes de despacho, esto es: si el cliente a consultar tiene días de gracia
     * la orden de despacho se genera automáticamente</P>
     */
    public boolean consultarDiGraCli(Connection con){
      
        int intResp=0;
        try {
//            ZafCon oc=new ZafCon();
            //stmSql=oc.ocon.createStatement(); 
            stmSql=con.createStatement(); 
            strQuery="select ne_diagra from tbm_cli where co_emp="+oi.getEmp()+" and co_cli="+oi.getIntCocli();
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                intResp=rs.getInt("ne_diagra");
                if (intResp!=0) {
                    
                    return true;
                }
                
            }
         stmSql.close();
         rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    
    }//fin de dias de gracia
 
    
    public boolean consultarDiGraCliChq(Connection con){
      
        int intResp=0;
        try {
//            ZafCon oc=new ZafCon();
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="select ne_diagrachqfec from tbm_cli where co_emp="+oi.getEmp()+" and co_cli="+oi.getIntCocli();
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                intResp=rs.getInt("ne_diagrachqfec");
                if (intResp!=0) {
                    
                    return true;
                }
                
            }
         stmSql.close();
         rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    
    }//fin de dias de gracia
 
    
    
     public boolean consultarForPagChq(Connection con, int intCodForPag, int intCodEmp){
      
        int intResp=0;
        try {
//            ZafCon oc=new ZafCon();
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="select co_forpag from tbm_cabforpag where upper(tx_des) like '%CHEQUE%' and co_forpag="+intCodForPag+" and co_emp="+intCodEmp;
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                intResp=rs.getInt("co_forpag");
                if (intResp!=0) {
                    return true;
                }
                
            }
         stmSql.close();
         rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return false;
    
    }//fin de dias de gracia    
	
	
/**
     * Metodo que consulta las formas de pago contraEntrega.
     * @param con conexion .
     * @param intCodForPag codigo de forma de pago.
     * @param intCodEmp codigo de empresa.
     * @return  boolean indica si es FP CONTRAENTREGA.
     */
    public boolean consultarForPagConEnt(Connection con, int intCodForPag, int intCodEmp){
        Statement stmSql=null;
        String strQuery="";
        ResultSet rs=null;
        int intResp=0;
        try {
            stmSql=con.createStatement();
            strQuery="select co_forpag from tbm_cabforpag where upper(tx_des) like 'CONTRAENTREGA%' and co_forpag="+intCodForPag+" and co_emp="+intCodEmp;
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                intResp=rs.getInt("co_forpag");
                if (intResp > 0) {
                    return true;
                }
                
            }
         stmSql.close();
         rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }        
        return false;
    
    }//fin de Consulta de forma de pago ContraEntrega	
    
    
    
    /**
     * <h1>FORMA DE PAGO DEL CLIENTE</h1>
     * <P>Esta función permite extraer la forma de pago del cliente registrado a una empresa determinada</P>
     * @return 
     */
    private int traerForPagCli(Connection con){
		int fp=0;
		
		try {
			//ZafCon oc=new ZafCon();
			//stmSql=oc.ocon.createStatement();
                        stmSql=con.createStatement();
			strQuery="select co_forpag from tbm_cli where co_cli="+oi.getIntCocli()+" and co_emp="+oi.getEmp();
			rs=stmSql.executeQuery(strQuery);
			if (rs.next()) {
				fp=rs.getInt("co_forpag");
			}
			stmSql.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return fp;
	}//fin de traefp
    
   
	
/**
 * <h2>FUNCION QUE VERIFICA TIPO DE FORMA DE PAGO DEL CLIENTE, QUE NO ES CREDITO</h2>
 * Esta función permite la búsqueda de la forma de pago que viene en el objeto <Strong>OI</Strong>
 * que viene de la capa de usuario (formulario de ingreso de datos de la cotización)
 * @return 
 */	
    private int verificarTipNoCred(Connection con){
        int res=0;
        try {
            stmSql=con.createStatement();
            strQuery="select * from tbm_cabforpag where tx_des not in (select tx_des from tbm_cabforpag  where co_emp="+oi.getEmp()+" and ( tx_des like 'Crédito%' or tx_des like 'Credito%')) and co_emp="+oi.getEmp()+" and st_reg='A' and co_forpag="+oi.getFpago();
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                res=1;
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        
        return res;
    }//fin de verifica forma de pago
    
    /**
     * <h2>FUNCION QUE VERIFICA SI LA FORMA DE PAGO DEL CLIENTE ES CREDITO DE ALGUN TIPO</h2>
     * Función que permite saber si la forma de pago del cliente ingresada por el objeto <Strong>OI</Strong> 
     * se encuentra entre las formas de pago con crédito de algún tipo, esto es crédito 15 días, crédito 30 días, etc.
     * @return 
     */
    public int verificarTipCred(Connection con){
        int res=0;
        try {
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="select * from tbm_cabforpag where  st_reg='A' and co_emp="+oi.getEmp()+" and ( tx_des like 'Crédito%' or tx_des like 'Credito%') and co_forpag="+oi.getFpago();
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                res=1;
            }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }//fin de verifica 
    
    
    /**<h2>FUNCION DE VERIFICACION DE ESTADO DE FACTURA ELECTRONICA</h2>
     * Esta función permite la verificación de autorización de la factura electrónica para proceder a emitir la 
     * orden de despacho, si esta con estado "C", se procede, sino se detiene la genereración de la OD.
     * @return 
     */
    private int verificarEstAutFacElec(Connection con){
    
        int res=0;
        String st;
        try {
            stmSql=con.createStatement();
            strQuery="select st_autfacele from tbm_cabmovinv where co_tipdoc = "+oi.getTipdoc()+" and co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and ne_numdoc="+oi.getNumdoc();
            System.out.println("consulta de verificacion de estado de factura: "+strQuery);
            rs=stmSql.executeQuery(strQuery);
                if (rs.next()) {
                    st=rs.getString("st_autfacele");
                    System.out.println("los datos de la verificacion son tipdoc: "+oi.getTipdoc()+" emp: "+oi.getEmp()+" loc: "+oi.getLoc()+" numDoc: "+oi.getNumdoc());
                    System.out.println("el estado de la factura elec es:"+rs.getString("st_autfacele"));


                    if (st.equals("C")|| st.equals("A")) {
                        res=1;
                  
                    }
                }
            stmSql.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return res;
    }//fin de facelec
    
    /**
     * <h2>Trae Código del documento generado al facturar</h2>
     * Metodo para la extracción del código del documento de una factura generada, se utiliza en ZafVen01
     * después de la validación de la forma de pago del cliente y si es alguna forma de crédito, la OD se imprime 
     * automáticamente
     */
    public int traerCodigoDoc(Connection con){
        int r=0;
        
        try {
            //stmSql=oc.ocon.createStatement();
            stmSql=con.createStatement();
            strQuery="select co_doc from tbm_cabmovinv where co_tipdoc = "+oi.getTipdoc()+" and co_emp="+oi.getEmp()+" and co_loc="+oi.getLoc()+" and ne_numdoc="+oi.getNumdoc();
            rs=stmSql.executeQuery(strQuery);
            if (rs.next()) {
                r=rs.getInt("co_doc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return r;
    }
    
    
    
    public void validarFecChqFac(){
    
        
    
    
    }//fin de validacion de cheques
    
    
//************************************AREA DE ENCAPSULAMIENTO***************************************//
    public ZafImp getOi() {
        return oi;
    }

    public void setOi(ZafImp oi) {
        this.oi = oi;
    }
   
    
}
