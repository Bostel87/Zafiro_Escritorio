/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafCorElePrg;


/** 
 *
 * @author jayapata
 * ver 0.4
 */
public class ZafCorElePrg {

    Librerias.ZafParSis.ZafParSis objZafParSis;
    Librerias.ZafUtil.ZafUtil objUti;
    javax.swing.JInternalFrame jIntfra=null;
    boolean blnEstPro=true; // estado de produccion  true =  correos reales  false= correo de prueba
    String strCorEleTo="efloresavelino@hotmail.com";

    public ZafCorElePrg(Librerias.ZafParSis.ZafParSis obj, javax.swing.JInternalFrame jfrthis ) {
        try{
            this.objZafParSis = (Librerias.ZafParSis.ZafParSis) obj.clone();
            objUti = new Librerias.ZafUtil.ZafUtil();
            jIntfra = jfrthis;
        }catch (CloneNotSupportedException e){ 
            objUti.mostrarMsgErr_F1(jfrthis, e); 
        }
    }

    public String _getCorEleCotVen_01(){
        if(blnEstPro) strCorEleTo="gerencia_ventas@castek.ec";

        return strCorEleTo;
    }

    public String _getCorEleCotVen_02(){
        if(blnEstPro) strCorEleTo=" bodegacastekquito@castek.ec";

        return strCorEleTo;
    }

    public String _getCorEleCotVen_03(){
        if(blnEstPro) strCorEleTo="administracion@castek.ec";

        return strCorEleTo;
    }

    public String _getCorEleConfIngEgr_01(){
        if(blnEstPro) strCorEleTo="operaciones@tuvalsa.com";

        return strCorEleTo;
    }

    public String _getCorEleConfIngRel_01(){
        if(blnEstPro) strCorEleTo="operaciones@tuvalsa.com";

        return strCorEleTo;
    }

    public String _getCorEleFacVen_01(int intCodEmp, int intCodBodPre){
        if(blnEstPro) strCorEleTo=_getCorEleToSistemas( intCodEmp,  intCodBodPre);

        return strCorEleTo;
    }

    public String _getCorEleFacVen_02(){
        if(blnEstPro) strCorEleTo="fullchasin@tuvalsa.com";

        return strCorEleTo;
    }


    public String _getCorEleAutMerNunRec_01(){
        if(blnEstPro) strCorEleTo="gerenciatuval@tuvalsa.com";  //    alfredo

        return strCorEleTo;
    } 

    /**  
    * obtiene el correo electronico al cual tiene que ser enviado.
    * @return
    */
    private String _getCorEleToSistemas(int intCodEmp, int intCodBodPre ){
        String strCorEle="";
        java.sql.Connection  conn;
        java.sql.Statement stm;
        java.sql.ResultSet rst;
        String strSql="";
        try{
            conn =  java.sql.DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
            if(conn != null ){
                stm=conn.createStatement();

                strSql="select tx_corEle from tbm_bod where co_emp="+intCodEmp+"  and co_bod="+intCodBodPre+"   ";
                rst=stm.executeQuery(strSql);
                if(rst.next()){
                    strCorEle=rst.getString("tx_corEle");
                }
                rst.close();
                rst=null;
                stm.close();
                stm=null;
                conn.close();
                conn=null;
            }
        }catch(java.sql.SQLException e)  {   System.out.println(""+e );    }
        catch(Exception Evt)  {   System.out.println(""+Evt ); }
        return strCorEle;
    }

    /**
    * Funcion que permite obtener el correo de electronico del area de ventas
    * @param intTipEmp tipo de empresa el cual se buscara <br> 1 TUVAL <BR> 2 QUITO <BR> 3 MANTA <BR> 4 SANTO DOMINGO <BR> 5 DIMULTI 
    * @return un String
    */
    public String _getCoreleVentas(int intTipEmp){
        try{
            switch(intTipEmp){
                case 1:
                    if(blnEstPro) strCorEleTo="ventas_tuval@tuvalsa.com";
                    break;
                case 2:
                    if(blnEstPro) strCorEleTo="ventas_castek_quito@castek.ec";
                    break;     
                case 3:
                    if(blnEstPro) strCorEleTo="ventas_castek_manta@castek.ec";
                    break;
                case 4:
                    if(blnEstPro) strCorEleTo="ventas_castek_stobgo@castek.ec";
                    break;     
                case 5:
                    if(blnEstPro) strCorEleTo="ventas_dimulti@dimulti.com";
                    break;     
            }

        }catch(Exception Evt)  {   
            System.out.println(""+Evt ); 
        }
        return strCorEleTo;
    }

    public String getCorEleBod(){
        if(blnEstPro) {
            if (objZafParSis.getCodigoEmpresa()==1) strCorEleTo="bodega@tuvalsa.com";  // bodega TUVAL
            if (objZafParSis.getCodigoEmpresa()==2){
                if(objZafParSis.getCodigoLocal()==1) strCorEleTo="bodegacastekquito@castek.ec";  // bodega CASTEK UIO
                if(objZafParSis.getCodigoLocal()==4) strCorEleTo="bodegacastekmanta@castek.ec";  // bodega CASTEK MAN
                if(objZafParSis.getCodigoLocal()==6) strCorEleTo="ventas_stodgo@castek.ec";  // bodega CASTEK SDO
            }                  
            if (objZafParSis.getCodigoEmpresa()==4) strCorEleTo="bodegadimulti@dimulti.com";  // bodega DIMULTI           
        }

        return strCorEleTo;
    } 

    
}
