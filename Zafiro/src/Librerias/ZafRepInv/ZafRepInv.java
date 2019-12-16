/** 
 * ZafRepInv.java
 *
 * Created on 28 de junio de 2005, 16:01
 */

package Librerias.ZafRepInv;
import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import Librerias.ZafRepInv.ZafRepInv_01;
import Librerias.ZafRepInv.ZafRepInv_02;
import Librerias.ZafRepInv.ZafRepInv_03;
import Librerias.ZafUtil.ZafCliente_dat;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/** *
 *
 * @author  jsalazar
 */
/**
 * Clase creada para generar reposiciones de inventario intercompañias
 * Genera documentos automaticos de cotizacion, ordenes de compra y facturas segun el caso.
 * Por lo que mueve inventario , genera pagos y asientos contables.
 * Toda la transaccion es automatizada y dependiente de las politicas de reposicion configuradas.
 *
 */
public class ZafRepInv{
    private ZafParSis objZafParSis;
    private ZafUtil objUti;   
    private ZafRepInv objZafRepInv;
    private ZafRepInv_01 objRepDlg;
    private ZafRepInv_02 objRepData;
    //private ZafThreadGUI objThrGUI;
    
    
    private Connection con,conAux;
    private Statement stm,stmAux;
    private ResultSet rst,rstAux;   
    private String strSQL; 
    /*
     * Utilizo el vector vecConfDoc para almacenar la configuracion de comandos del Documento acciones como:
     *   calculos de costos, crear Ordenes de Compra, Facturas o Cotizaciones ; ademas de visualizacion de JDialog para reposiciones
     *   automaticas y permisos de modificacion
     * Utilizo el vector vecDat solo para cargar y enviar la coleccion de objetos ZafRepInv_02 que contienen los datos de la reposicion
     * Utilizo el vector vecConfItm para los datos de item que origina la reposicion , datos como:
     *    numero de registro de la tabla que proviene, codigo del item, codigo de la bodega y cantidad solicitada para la venta.
     * Utilizo el vector vecRepDat para cargar todos los objetos ZafRepInv_02 proveniente del documento origen.
     */
    private Vector vecConfDoc=null, vecDat=null, vecConfItm=null,vecRepDat=null;
    //Utilizo intNumDoc para el numero del documento a ser generado.
    //Utilizo intNumCot para el numero de la cotizacion.
    private int intTipDoc=0,intNumCot=0, intNumDoc=0;
    /*
     * Utilizo vecDocCli para almacenar los documentos generados al cliente para relazionarlos despues.
     * Utilizo vecDocPro para almacenar los documentos generados al proveedor para relacionarlos luego.
     */
    private Vector vecDocCli = null, vecDocPro = null;
    
    //Constantes del vector de configuracion del documento
    final int INT_CONF_REP_NEW = 0;
    final int INT_CONF_CAL_COS = 1;
    final int INT_CONF_GRA_ORD = 2;
    final int INT_CONF_GRA_FAC = 3;
    final int INT_CONF_GRA_TEM = 4;
    final int INT_CONF_PER_MOD = 5;
    final int INT_CONF_VER_DLG = 6;
    
    // Constantes del vector de item    
    final int INT_DAT_COD_BOD = 0;
    final int INT_DAT_COD_ITM = 1;
    final int INT_DAT_STK_CAN = 2;
    final int INT_DAT_NUM_REG = 3;
    
    // Variables para sentencias insert, update y delete
    private java.sql.PreparedStatement pstCot;
    private java.sql.PreparedStatement pstDocPro;
    private java.sql.PreparedStatement pstDocCli;

    /** 
     *  Creates a new instance of ZafRepInv   
     *  Recibe el objeto ZafParSis e inicializa los vectores
     *  Utilizo el vector vecConfDoc para almacenar la configuracion de comandos del Documento acciones como:
     *   calculos de costos, crear Ordenes de Compra, Facturas o Cotizaciones ; ademas de visualizacion de JDialog para reposiciones
     *   automaticas y permisos de modificacion
     *  Utilizo el vector vecDat solo para cargar y enviar la coleccion de objetos ZafRepInv_02 que contienen los datos de la reposicion
     *  Utilizo el vector vecConfItm para los datos de item que origina la reposicion , datos como:
     *    numero de registro de la tabla que proviene, codigo del item, codigo de la bodega y cantidad solicitada para la venta.
     *  Utilizo el vector vecRepDat para cargar todos los objetos ZafRepInv_02 proveniente del documento origen.
     */
    public ZafRepInv(ZafParSis obj) {
        objZafRepInv = this;
        objZafParSis = obj;
        objUti = new ZafUtil();
        vecConfItm = new Vector();
        vecConfDoc = new Vector();          
        vecRepDat  = new Vector();
        vecConfDoc.add(INT_CONF_REP_NEW, new Integer(1));   
    }   
    public void verData()
    {
        System.out.println("Leyendo datos...");
        if (!(vecDat==null))
        {
            for (int i=0; i<vecDat.size(); i++)
            {            
                ZafRepInv_02 obj = (ZafRepInv_02)vecDat.get(i);
                System.out.print("\n i:");        
                System.out.print("\t doc: "+obj.getDatCodCot());        
                System.out.print("\t,emp: "+obj.getDatCodEmp());        
                System.out.print("\t,loc: "+obj.getDatCodLoc());                    
                System.out.print("\t,bod: "+obj.getDatCodBod());                                
                System.out.print("\t,"+obj.getDatCodItm());                                            
                System.out.print("\t,"+obj.getDatStkCan());                    
                System.out.print("\t,"+obj.getDatDscPre());                 
            }   
            System.out.print("...\n");
        }
    }
    /**  Recibe como parametros:
         *  int tipo doc (ej: -1 .- Cotizacion , 1.- Factura )
         *  .Configura el vector : vecConfDoc segun el tipo de documento
         *  boolean Calcula_Costos (opcional)
         *  boolean Graba_OC (opcional)
         *  boolean Graba_Fac (opcional)
         *  boolean Graba_Temp (opcional)
         *  boolean Modificar (opcional)
         *  boolean ver objeto JDialog (opcional)
     */        
    
    public void setTipoDoc(int intparTipDoc)
    {        
        intTipDoc = intparTipDoc;        
        switch (intTipDoc)
        {            
            case -1:
                vecConfDoc.add(INT_CONF_CAL_COS, new Boolean(true));//calcula_precio
                vecConfDoc.add(INT_CONF_GRA_ORD, new Boolean(false));//graba_oc
                vecConfDoc.add(INT_CONF_GRA_FAC, new Boolean(false));//graba_fac
                vecConfDoc.add(INT_CONF_GRA_TEM, new Boolean(true));//graba_temp
                vecConfDoc.add(INT_CONF_PER_MOD, new Boolean(true));//modificar  
                vecConfDoc.add(INT_CONF_VER_DLG, new Boolean(false));//ver objeto JDialog
                break;
            case 1:
                vecConfDoc.add(INT_CONF_CAL_COS, new Boolean(true));//calcula_precio
                vecConfDoc.add(INT_CONF_GRA_ORD, new Boolean(true));//graba_oc
                vecConfDoc.add(INT_CONF_GRA_FAC, new Boolean(true));//graba_fac
                vecConfDoc.add(INT_CONF_GRA_TEM, new Boolean(false));//graba_temp
                vecConfDoc.add(INT_CONF_PER_MOD, new Boolean(false));//modificar 
                vecConfDoc.add(INT_CONF_VER_DLG, new Boolean(false));//ver objeto JDialog
                break;
            default:
                vecConfDoc.add(INT_CONF_CAL_COS, new Boolean(false));//calcula_precio
                vecConfDoc.add(INT_CONF_GRA_ORD, new Boolean(false));//graba_oc
                vecConfDoc.add(INT_CONF_GRA_FAC, new Boolean(false));//graba_fac
                vecConfDoc.add(INT_CONF_GRA_TEM, new Boolean(false));//graba_temp
                vecConfDoc.add(INT_CONF_PER_MOD, new Boolean(false));//modificar                      
                vecConfDoc.add(INT_CONF_VER_DLG, new Boolean(false));//ver objeto JDialog
                break;
        }        
    }
    public void setTipoDoc(int intparTipDoc,boolean blnparCalCos ,boolean blnparGraOC,boolean blnparGraFac,boolean blnparGraTem , boolean blnparMod, boolean blnparVerDlg)
    {        
        intTipDoc = intparTipDoc;            
        vecConfDoc.add(INT_CONF_CAL_COS, new Boolean(blnparCalCos));//calcula_precio
        vecConfDoc.add(INT_CONF_GRA_ORD, new Boolean(blnparGraOC));//graba_oc
        vecConfDoc.add(INT_CONF_GRA_FAC, new Boolean(blnparGraFac));//graba_fac
        vecConfDoc.add(INT_CONF_GRA_TEM, new Boolean(blnparGraTem));//graba_temp
        vecConfDoc.add(INT_CONF_PER_MOD, new Boolean(blnparMod));//modificar                       
        vecConfDoc.add(INT_CONF_VER_DLG, new Boolean(blnparVerDlg));//ver objeto JDialog
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad calcula_precio
     * Valor Booelan 
     */
    public void setConfDoc_CalCos(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_CAL_COS);
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad graba_orden de compra 
     * Valor Booelan 
     */
    public void setConfDoc_GraOrd(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_GRA_ORD);
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad graba_factura proveedor
     * Valor Booelan 
     */
    public void setConfDoc_GraFac(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_GRA_FAC);
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad graba_cotizacion
     * Valor Booelan 
     */
    public void setConfDoc_GraTem(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_GRA_TEM);
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad permitir modificar       
     * Valor Booelan 
     */
    public void setConfDoc_PerMod(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_PER_MOD);
    }
    /**
     * Metodo para setear el vector de Configuracion del Documento a generar.
     * Propiedad ver objeto JDialog
     * Valor Booelan 
     */
    public void setConfDoc_VerDlg(boolean blnparConf)
    {
        vecConfDoc.setElementAt(new Boolean(blnparConf),INT_CONF_VER_DLG);
    }
    /**
     * Metodo para setear el tipo de documento a generar.
     * Si es cotizacion valor: -1 sino el valor es el codigo del tipo de documento.
     */
    public int getTipoDoc()
    {
        return intTipDoc;
    }
    /**
     * Metodo para setear la propiedad que contiene el Numero de Documento a generar por parte del cliente 
     */
    public void setNumDoc(int intparNumDoc)
    {
        intNumDoc = intparNumDoc;
    }
    private int getNumDoc()
    {
        return intNumDoc;
    }
    
/**   
  * Metodo para configurar Tipo de accion que esta realizando con el documento
  *      case 1: //Nuevo
  *      case 2: //Modificacion
  *      case 3: //Consulta
  */
    public void setTipCommand(int intparTip)
    {
        vecConfDoc.setElementAt(new Integer(intparTip),INT_CONF_REP_NEW);
    }
    private int getTipCommand()
    {
         return  Integer.parseInt(vecConfDoc.get(INT_CONF_REP_NEW).toString());  
    }
    /** 
     * Metodo que almacena todos los objetos ZafRepInv_02 creados en los documentos
     * recibe el numero de registro y  el vector que contiene los objetos por registros.
     */
    public boolean addData(int index, Vector vecparReg)
    {
        boolean blnAdd = false;
        try{            
          if (!(vecparReg==null))
          {          
            for (int i=0;i<vecparReg.size();i++)
            {
                ZafRepInv_02 objRep = (ZafRepInv_02)vecparReg.get(i);
                objRep.setOriCodReg(index);                
                vecRepDat.add(objRep);
                //System.out.println("Saving...reg#"+i);                
            }
            blnAdd = true;
          }
        } catch (Exception e)  {
            blnAdd = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } 
        return blnAdd;
    }
    /**
     * Metodo para limpiar o borrar el vector que contiene todos los objetos de reposicion.
     */
    public boolean clearData()
    {
        boolean blnclrDat = true;
        try{
            if (!(vecRepDat==null))
                vecRepDat.clear();
            else
                vecRepDat = new Vector();
        } catch (Exception e)  {
            blnclrDat = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } 
        return blnclrDat;
    }
    /**
     * Metodo que verifica si existen reposiciones cargadas.
     */
    public boolean isllenoReposicion()
    {
       if (vecRepDat!=null)
           if (vecRepDat.size()>0)   
               return true;
       return false;
    }    
    /**
     * Metodo que devuelve la cantidad que se repuso del item 
     */
    public double getCantidadSolicitada(Vector vecRep)
    {
        double dblResp = 0;
        try
        {
           if (vecRep!=null)
           {
               if (vecRep.size()>0)   
               {
                   for (int i=0;i<vecRep.size();i++)
                   {
                        ZafRepInv_02 objDatTmp = (ZafRepInv_02) vecRep.get(i);
                        dblResp += objDatTmp.getDatStkCan();
                   }
               }
           }
        } catch (Exception e)  {
            dblResp = 0;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } 
        return dblResp;

    }
    /**
     * Metodo que permite realizar el almacenamiento de la informacion.
     * Verifica que tipo de documento va a generar Cotizacion, O/C, Factura
     */
    public boolean GrabaDoc(java.sql.Connection con)
    {
        boolean blnGraDoc = false;
        try{            
            if (!(vecRepDat==null) && (vecRepDat.size()>0) && intTipDoc!=0 && intNumDoc!=0)
            {
                if (Boolean.valueOf(vecConfDoc.get(INT_CONF_GRA_TEM).toString()).booleanValue())
                    if(!(GrabarTemp(con)))  return false;                    
                if (Boolean.valueOf(vecConfDoc.get(INT_CONF_GRA_ORD).toString()).booleanValue()) 
                    if(!(CrearDocCli(con))) return false;
                if (Boolean.valueOf(vecConfDoc.get(INT_CONF_GRA_FAC).toString()).booleanValue()) 
                    if(!(CrearDocPro(con))) return false;
//                if (vecDocCli!=null || vecDocPro!=null)
//                    if (!CrearRelDoc(con)) return false;
                blnGraDoc = true;
            }else{
                if (vecRepDat==null || vecRepDat.size()<=0) MensajeError("Sistema Zafiro.- Grabando Reposicion","Error de carga.\n No existe reposicion almacenada.");
                if (intTipDoc==0) MensajeError("Sistema Zafiro.- Grabando Reposicion","Requiere cargar parametro de tipo de documento a generar") ;
                if (intNumDoc==0) MensajeError("Sistema Zafiro.- Grabando Reposicion","Requiere cargar parametro de numero de documento a generar") ;
            }
        } catch (Exception e)  {
            blnGraDoc = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } 
        return blnGraDoc;
    }
    /**
     * Metodo para crear los documentos del cliente
     * Es dependiente del tipo de documento a generar al cliente final
     * Mueve Inventario, Genera Diario
     * Agrega detalle a la order de compra cliente mientra exista lineas de impresion configurada en las politicas de reposicion.
     */    
    private boolean CrearDocCli(java.sql.Connection conSave)
    { 
        boolean blnOrdCom = false;
        boolean blnFind, blnBandera=false;     
        double dblvalor,dblCabSub=0,dblRetFuente=0,dblRetIva=0;
        int intInd = 0,intobj[];
        ZafRepInv_02 objRepTmp;
        ZafRepInv_03 objCabDoc;
        ZafRepInv_03 objCabTmp;        
        Vector vecCabDoc= new Vector();                
        try
        {     
            //Carga y Genera Doc.Cliente :  Tipo objeto CabDoc dentro de un vector.
            for (int i=0;i<vecRepDat.size();i++)
            {
                objRepTmp = (ZafRepInv_02) vecRepDat.get(i);                
                if (!(vecCabDoc==null))
                {
                    blnFind = false;
                    for (int j=0; j<vecCabDoc.size();j++)
                    {
                        objCabTmp = (ZafRepInv_03) vecCabDoc.get(j);
                        if (objCabTmp.getCodEmp() == objRepTmp.getDatCodEmp()  &&  objCabTmp.getCodLoc() == objRepTmp.getDatCodLoc()){                            
                            // && objCabTmp.getNumDoc() == objRepTmp.getDatCodCot()                            
                            if (objCabTmp.getNumLine()> objCabTmp.vecRegDet.size()+ objCabTmp.getNumReg()){     
                                blnFind = objCabTmp.AgregaDetalle(objRepTmp); 
                                break;
                            }                                                                
                        }                        
                    }
                    if (!(blnFind))
                    {
                        objCabDoc = new ZafRepInv_03 (objZafRepInv.getTipoDoc(),objRepTmp,objZafParSis);
                        objCabDoc.AgregaDetalle(objRepTmp);
                        objCabDoc.AsignarConfDoc(true,blnBandera);
                        if (objCabDoc.getEstado()) blnBandera=true;                        
                        //System.out.println("num: "+objCabDoc.getNumDoc()+" line: "+objCabDoc.getNumLine()+" ord: "+objCabDoc.getOrdDoc()+ " reg: "+objCabDoc.getNumReg());
                        vecCabDoc.add(objCabDoc);
                    }
                }                
            }                        
            stm=conSave.createStatement();            
            for (int i=0; i<vecCabDoc.size();i++)
            {   
                dblCabSub=0;
                objCabDoc = ((ZafRepInv_03)vecCabDoc.get(i));                        
                if (!objCabDoc.getEstado()){
                    intInd++;
                    objCabDoc. AsignarCodDoc(intInd);               
                }
                /* Cargamos cuentas deudoras provenientes del detalle 
                 * para reposiciones cliente se obtiene la bodega
                 * segun el tipo de documento sera el asiento... (temporalmente estatico)
                 */
                for (int j=0;j<objCabDoc.vecRegDet.size();j++)
                {
                        objRepTmp = (ZafRepInv_02) objCabDoc.vecRegDet.get(j);                           
                        strSQL = "";
                        strSQL = " Select co_ctaexi  as co_cta from tbm_bod where co_emp= " + objZafParSis.getCodigoEmpresa() + " and co_bod = " + objRepTmp.getOriCodBod() ;
                        //System.out.println ("SQL: "+strSQL);
                        if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)                        
                        {
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                            {
                                    dblvalor = objUti.redondeo((objRepTmp.getDatStkCan()*objRepTmp.getDatDscPre()),6);
                                    objCabDoc.DetalleDiario(Integer.parseInt(rst.getString("co_cta")),dblvalor,"D");
                            }
                            rst.close();
                        }else{
                            MensajeError("Sistema Zafiro.- Reposicion para Cliente","No existe cuenta contable para bodegas de la empresa: "+objZafParSis.getNombreEmpresa());
                            return false;
                        }
                }                
                /* Cargamos cuentas acreedoras provenientes del cabecera 
                 * para reposiciones cliente se obtiene la proveedor e iva (retenciones si fuera el caso)
                 ** segun el tipo de documento sera el asiento... (temporalmente estatico)
                 */
                //Iva
                strSQL = "";
                strSQL = " select co_ctaivacom as co_cta from tbm_emp where co_emp= " + objZafParSis.getCodigoEmpresa();
                //System.out.println ("SQL: "+strSQL);
                if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                {
                    rst=stm.executeQuery(strSQL);
                    if(rst.next())
                    {
                            dblvalor = objUti.redondeo(objCabDoc.getSubTotal()*(objCabDoc.getivacom()/100),6);
                            objCabDoc.DetalleDiario(Integer.parseInt(rst.getString("co_cta")),dblvalor,"D");
                    }                        
                    rst.close();
                }else{
                    MensajeError("Sistema Zafiro .- Reposicion Para Cliente","No existe cuenta contable de IVA en compras para la empresa: " + objZafParSis.getNombreEmpresa());
                    return false;             
                }
                //Proveedor
                dblvalor = objUti.redondeo(objCabDoc.getSubTotal()+(objCabDoc.getSubTotal()*(objCabDoc.getivacom()/100)),6);
                 objCabDoc.DetalleDiario(objCabDoc.getCtaHaber(),dblvalor,"H");
                //System.out.println("doccli: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getNumDoc() +" emp:"+((ZafRepInv_03)vecCabDoc.get(i)).getCodEmp()  + " loc: " + ((ZafRepInv_03)vecCabDoc.get(i)).getCodLoc() + " sub total: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getSubTotal());                
            }    
            stm.close();
            java.util.Date datFecha = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos());
            if (!(conSave==null))
            {
                conSave.setAutoCommit(false);
                ZafCliente_dat objCliente;
                //Librerias.ZafDate.ZafDatePicker objDate= new Librerias.ZafDate.ZafDatePicker(datFecha.toString(),new javax.swing.JFrame(),"y/m/d");
                String datFecReg;
                for (int i=0;i<vecCabDoc.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecCabDoc.get(i);
                    objCliente = new ZafCliente_dat(objZafParSis.getCodigoEmpresa(),objCabTmp.getco_pro(),objZafParSis);
                    strSQL  = "";
                    if (!objCabTmp.getEstado())//Nuevo Documento
                    {
                        strSQL = " Insert into tbm_cabmovinv (co_emp, co_loc, co_tipdoc, co_doc, ne_orddoc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli,tx_dircli, co_com,tx_nomven, co_forpag, tx_desforpag, nd_sub, nd_poriva, nd_tot,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod)";
                        strSQL += " values( " + objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal()+ "," +objCabTmp.getTipoDoc()+ "," +objCabTmp.getNumDoc()+ "," +objCabTmp.getOrdDoc()+ ",0,'" + objUti.formatearFecha(datFecha, objZafParSis.getFormatoFechaBaseDatos()) + "'," +objCabTmp.getco_pro()+ ",'" +objCliente.getIdentificacion()+ "','" +objCliente.getNombre()+ "','" + objCliente.getDireccion()+ "'," +objCabTmp.getco_com()+ "," + null+ "," +objCabTmp.getForPag()+ "," +null+ "," + objUti.redondeo(objCabTmp.getSubTotal(),6)+ "," +objCabTmp.getivacom()+ "," +objUti.redondeo((objCabTmp.getSubTotal()+(objCabTmp.getSubTotal()*(objCabTmp.getivacom()/100))),6)+ ",'Documento Generado por Reposicion','R','"+objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos())+ "','" +objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos())+ "'," +objZafParSis.getCodigoUsuario()+ "," +objZafParSis.getCodigoUsuario()+ ")" ;
                    }else{ //Anterior requiere actualizacion de cabecera
                        
                        strSQL = " Update tbm_cabmovinv set  nd_sub=nd_sub + " +objCabTmp.getSubTotal()+ " , nd_tot= nd_tot + "+objUti.redondeo((objCabTmp.getSubTotal()+(objCabTmp.getSubTotal()*objCabTmp.getivacom())),6)+" where co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()+ " and co_tipdoc = " +objCabTmp.getTipoDoc()+ " and co_doc = " +objCabTmp.getNumDoc()+ " ";
                    }
                    //System.out.println("SQL:"+strSQL);
                    pstDocCli = conSave.prepareStatement(strSQL);
                    pstDocCli.executeUpdate();                    
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        objRepTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);                        
                        strSQL = "";
                        strSQL = " Insert into tbm_detmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,co_itm,tx_codalt,tx_nomitm,co_bod,tx_unimed,nd_can,nd_cosuni)";                        
                        strSQL += " values( " +  objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal()+ "," +objCabTmp.getTipoDoc()+ "," +objCabTmp.getNumDoc()+ "," + (objCabTmp.getNumReg()+j+1)+ "," +objRepTmp.getOriCodItm()+ ",'" +objRepTmp.getOriCodAlt()+ "','" +objRepTmp.getOriNomItm()+ "'," +objRepTmp.getOriCodBod()+",'"+ objRepTmp.getOriUniMed()+ "'," +objRepTmp.getDatStkCan()+ "," +objRepTmp.getDatDscPre()+ ")" ;
                        System.out.println("SQL: " + strSQL);
                        pstDocCli = conSave.prepareStatement(strSQL);
                        pstDocCli.executeUpdate();
                        if (!objCabTmp.MoverInventario(conSave,objZafParSis.getCodigoEmpresa(),objRepTmp.getOriCodItm(),objRepTmp.getOriCodBod(),objRepTmp.getDatStkCan(),1))  
                            return false;                        
                    }                    
                    //Registro de Pagos de Movimiento
                    if (objCabTmp.getEstado())
                    {
                        strSQL = "";
                        strSQL = " Delete from tbm_pagmovinv where co_emp= " +  objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoLocal()+ " and co_tipdoc = " +objCabTmp.getTipoDoc()+ " and co_doc=" +objCabTmp.getNumDoc();
                        //System.out.println("SQL: " + strSQL);
                        pstDocCli = conSave.prepareStatement(strSQL);
                        pstDocCli.executeUpdate();                        
                    }                    
                    strSQL  = "";
                    strSQL  = " select nd_sub from tbm_cabmovinv ";
                    strSQL += " where co_emp = " + objZafParSis.getCodigoEmpresa() + " and  co_loc = " + objZafParSis.getCodigoLocal()+ " and co_tipdoc = " +objCabTmp.getTipoDoc()+ " and co_doc = " +objCabTmp.getNumDoc();
                    //System.out.println ("SQL: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        dblCabSub = Double.parseDouble(rst.getString("nd_sub"));
                    }                    
                    dblRetFuente = objUti.redondeo(dblCabSub/100,6);                    
                    if (objCliente.getTipPer()=='C') dblRetIva  = objUti.redondeo((dblCabSub*(objCabTmp.getivacom()/100))*(30/100),6);
                    dblvalor =dblCabSub + (dblCabSub*(objCabTmp.getivacom()/100)) - (dblRetFuente+dblRetIva);
                    strSQL  = "";
                    strSQL  = " select ne_diacre, ne_numpag from tbm_cabforpag as cabpag left outer join tbm_detforpag as detpag on (cabpag.co_emp = detpag.co_emp and cabpag.co_forpag = detpag.co_forpag) ";
                    strSQL += " where cabpag.co_emp = " +  objZafParSis.getCodigoEmpresa() + " and cabpag.co_forpag = " + objCabTmp.getForPag();
                    //System.out.println ("SQL: "+strSQL);
                    rst=stm.executeQuery(strSQL);
		    int IntIndPag=0;
                    while(rst.next())
                    {
                        dblvalor = (dblCabSub + (dblCabSub*(objCabTmp.getivacom()/100)) - (dblRetFuente+dblRetIva) )/ Integer.parseInt(rst.getString("ne_numpag"));
                        datFecReg =""+ (1900+datFecha.getYear())+"/"+ datFecha.getMonth()+"/"+(datFecha.getDay() + Integer.parseInt(rst.getString("ne_diacre")));            
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objZafParSis.getCodigoEmpresa() +","+ objZafParSis.getCodigoLocal() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ rst.getRow() +"," + rst.getString("ne_diacre") +",'"+ objUti.formatearFecha(objUti.parseDate(datFecReg,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()) +"',"+ objUti.redondeo(dblvalor,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocCli = conSave.prepareStatement(strSQL);
                        pstDocCli.executeUpdate();                                                
			IntIndPag++;                        
                    }
                    if (dblRetFuente>0)
                    {
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,nd_porret,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objZafParSis.getCodigoEmpresa() +","+ objZafParSis.getCodigoLocal() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ (++IntIndPag) +",1,'"+ objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos()) +"',"+ objUti.redondeo(dblRetFuente,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocCli = conSave.prepareStatement(strSQL);
                        pstDocCli.executeUpdate();                                                
                    }
                    if (dblRetIva>0)
                    {
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,nd_porret,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objZafParSis.getCodigoEmpresa() +","+ objZafParSis.getCodigoLocal() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ (++IntIndPag) +",1,'"+ objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos()) +"',"+ objUti.redondeo(dblRetIva,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocCli = conSave.prepareStatement(strSQL);
                        pstDocCli.executeUpdate();                                                
                    }
                    //Creacion de Diario Contable
                    if (!objCabTmp.getEstado()){                        
                        if (!(objCabTmp.CrearAsiento(conSave,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objCabTmp.getTipoDoc(),objCabTmp.getNumDoc(),null))) 
                            return false;
                    }else{
                        if (!(objCabTmp.CrearAsiento(conSave,objZafParSis.getCodigoEmpresa(),objZafParSis.getCodigoLocal(),objCabTmp.getTipoDoc(),objCabTmp.getNumDoc())))
                            return false;
                    }
                    
                }
            }
            vecDocCli = vecCabDoc;
            blnOrdCom = true;
        } catch (java.sql.SQLException e) {
            blnOrdCom = false;                        
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);            
       } catch (Exception e)  {
            blnOrdCom = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
       }        
        
        return blnOrdCom;
    }
    /**
     * Metodo para crear los documentos del proveedor 
     * Es dependiente del tipo de documento a generar al cliente final
     * Mueve Inventario, Genera Diario
     * Agrega detalle a la factura de proveedor mientra exista lineas de impresion configurada en las politicas de reposicion.
     */
    private boolean CrearDocPro(java.sql.Connection conSave)
    {
        boolean blnFacPro = false;
        boolean blnFind, blnBandera=false;     
        double dblvalor,dblCabSub=0,dblRetFuente=0,dblRetIva=0;
        int intInd = 0;
        ZafRepInv_02 objRepTmp;
        ZafRepInv_03 objCabDoc;
        ZafRepInv_03 objCabTmp;        
        Vector vecCabDoc= new Vector();                
        try
        {     
            //Carga y Genera Doc.Cliente :  Tipo objeto CabDoc dentro de un vector.
            for (int i=0;i<vecRepDat.size();i++)
            {
                objRepTmp = (ZafRepInv_02) vecRepDat.get(i);                
                if (!(vecCabDoc==null))
                {
                    blnFind = false;
                    for (int j=0; j<vecCabDoc.size();j++)
                    {
                        objCabTmp = (ZafRepInv_03) vecCabDoc.get(j);
                        if (objCabTmp.getCodEmp() == objRepTmp.getDatCodEmp()  &&  objCabTmp.getCodLoc() == objRepTmp.getDatCodLoc()){                            
                            // && objCabTmp.getNumDoc() == objRepTmp.getDatCodCot()                            
                            if (objCabTmp.getNumLine()> objCabTmp.vecRegDet.size()+ objCabTmp.getNumReg()){     
                                blnFind = objCabTmp.AgregaDetalle(objRepTmp); 
                                break;
                            }                                                                
                        }                        
                    }
                    if (!(blnFind))
                    {
                        objCabDoc = new ZafRepInv_03 (objZafRepInv.getTipoDoc(),objRepTmp,objZafParSis);
                        objCabDoc.AgregaDetalle(objRepTmp);
                        objCabDoc.AsignarConfDoc(false,blnBandera);
                        if (objCabDoc.getEstado()) blnBandera=true;                        
                        //System.out.println("num: "+objCabDoc.getNumDoc()+" line: "+objCabDoc.getNumLine()+" ord: "+objCabDoc.getOrdDoc()+ " reg: "+objCabDoc.getNumReg());
                        vecCabDoc.add(objCabDoc);
                    }
                }                
            }                     
            stm=conSave.createStatement();            
            for (int i=0; i<vecCabDoc.size();i++)
            {   
                
                objCabDoc = ((ZafRepInv_03)vecCabDoc.get(i));                        
                if (!objCabDoc.getEstado()){
                    intInd++;
                    objCabDoc. AsignarCodDoc(intInd);               
                }
                /* Cargamos cuentas acreedoras provenientes del cabecera 
                 * para reposiciones cliente se obtiene la proveedor e iva (retenciones si fuera el caso)
                 ** segun el tipo de documento sera el asiento... (temporalmente estatico)
                 */
                //Cliente
                dblvalor = objUti.redondeo(objCabDoc.getSubTotal()+(objCabDoc.getSubTotal()*(objCabDoc.getivaven()/100)),6);
                objCabDoc.DetalleDiario(objCabDoc.getCtaDebe(),dblvalor,"D");
                //Ventas
                dblvalor = objUti.redondeo(objCabDoc.getSubTotal(),6);
                objCabDoc.DetalleDiario(objCabDoc.getCtaHaber(),dblvalor,"H");
                //Iva
                strSQL = "";
                strSQL = " select co_ctaivaven as co_cta from tbm_emp where co_emp= " + objCabDoc.getCodEmp();
                //System.out.println ("SQL: "+strSQL);
                rst=stm.executeQuery(strSQL);
                if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                {
                    if(rst.next())
                    {
                            dblvalor = objUti.redondeo(objCabDoc.getSubTotal()*(objCabDoc.getivaven()/100),6);
                            objCabDoc.DetalleDiario(Integer.parseInt(rst.getString("co_cta")),dblvalor,"H");
                    }
                    rst.close();
                }else{
                    MensajeError("Mensaje Zafiro.- Reposicion Para Proveedor","No existe cuenta contable de IVA en ventas para el proveedor:"+objCabDoc.getco_pro());
                    return false;             
                }
                
                /* Cargamos cuentas acreedora provenientes del detalle 
                 * para reposiciones cliente se obtiene la bodega
                 * segun el tipo de documento sera el asiento... (temporalmente estatico)
                 */
                for (int j=0;j<objCabDoc.vecRegDet.size();j++)
                {
                        objRepTmp = (ZafRepInv_02) objCabDoc.vecRegDet.get(j);                           
                        strSQL = "";
                        strSQL = " Select co_ctaexi  as co_cta from tbm_bod where co_emp= " + objCabDoc.getCodEmp() + " and co_bod = " + objRepTmp.getDatCodBod();
                        //System.out.println ("SQL: "+strSQL);
                        if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                        {
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                            {                                
                                    //dblvalor = objUti.redondeo((objRepTmp.getDatStkCan()*objRepTmp.getDatDscPre()),6);
                                    dblvalor = 0;
                                    objCabDoc.DetalleDiario(Integer.parseInt(rst.getString("co_cta")),dblvalor,"H");
                            }                                             
                            rst.close();
                        }else{
                            MensajeError("Sistema Zafiro.- Reposicion Para Proveedor"," No existe cuenta contable para bodegas del proveedor: "+objCabDoc.getco_pro());
                            return false;
                        }
                        strSQL = "";
                        strSQL = " select co_ctacosven as co_cta from tbm_loc where co_emp= " + objCabDoc.getCodEmp() + " and co_loc = " + objCabDoc.getCodLoc();
                        //System.out.println ("SQL: "+strSQL);
                        if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(),objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),strSQL)>0)
                        {
                            rst=stm.executeQuery(strSQL);
                            if(rst.next())
                            {
                                    //dblvalor = objUti.redondeo((objRepTmp.getDatStkCan()*objRepTmp.getDatDscPre()),6);
                                    dblvalor=0;
                                    objCabDoc.DetalleDiario(Integer.parseInt(rst.getString("co_cta")),dblvalor,"H");
                            }
                            rst.close();
                        }else{
                            MensajeError("Mensaje Zafiro.- Reposicion Para Proveedor","No existe cuenta contable para costos del local para proveedor: "+objCabDoc.getco_pro());
                            return false;                            
                        }                                                        
                            
                }                
                //System.out.println("doccli: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getNumDoc() +" emp:"+((ZafRepInv_03)vecCabDoc.get(i)).getCodEmp()  + " loc: " + ((ZafRepInv_03)vecCabDoc.get(i)).getCodLoc() + " sub total: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getSubTotal());                
            }    
            stm.close();
            java.util.Date datFecha = objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos());
            if (!(conSave==null))
            {
                conSave.setAutoCommit(false);
                ZafCliente_dat objCliente;                
                String datFecReg;
                for (int i=0;i<vecCabDoc.size();i++)
                {
                    dblCabSub=0;
                    objCabTmp = (ZafRepInv_03) vecCabDoc.get(i);
                    objCliente = new ZafCliente_dat(objCabTmp.getCodEmp(),objCabTmp.getco_cli(),objZafParSis);
                    strSQL  = "";
                    if (!objCabTmp.getEstado())//Nuevo Documento
                    {
                        strSQL  = " Insert into tbm_cabmovinv (co_emp, co_loc, co_tipdoc, co_doc, ne_orddoc, ne_numdoc, fe_doc, co_cli, tx_ruc, tx_nomcli,tx_dircli, co_com,tx_nomven, co_forpag, tx_desforpag, nd_sub, nd_poriva, nd_tot,tx_obs2,st_reg,fe_ing,fe_ultmod,co_usring,co_usrmod)";
                        strSQL += " values( " + objCabTmp.getCodEmp() + "," + objCabTmp.getCodLoc() + "," + objCabTmp.getTipoDoc()+ "," +objCabTmp.getNumDoc()+ "," + objCabTmp.getOrdDoc() + ",0,'" + objUti.formatearFecha(datFecha, objZafParSis.getFormatoFechaBaseDatos()) + "'," +objCabTmp.getco_cli()+ ",'" +objCliente.getIdentificacion()+ "','" +objCliente.getNombre()+ "','" + objCliente.getDireccion()+ "'," +objCabTmp.getco_ven()+ "," + null+ "," +objCabTmp.getForPag()+ "," +null+ "," + objUti.redondeo(objCabTmp.getSubTotal()*-1,6)+ "," +objCabTmp.getivaven()+ "," +objUti.redondeo((objCabTmp.getSubTotal()+(objCabTmp.getSubTotal()*(objCabTmp.getivaven()/100)))*-1,6)+ ",'Documento Generado por Reposicion','R','"+objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos())+ "','" +objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos())+ "'," +objZafParSis.getCodigoUsuario()+ "," +objZafParSis.getCodigoUsuario()+ ")" ;
                    }else{ //Anterior requiere actualizacion de cabecera
                        
                        strSQL = " Update tbm_cabmovinv set  nd_sub=nd_sub + " + objUti.redondeo(objCabTmp.getSubTotal()*-1,6) + " , nd_tot= nd_tot + "+objUti.redondeo((objCabTmp.getSubTotal()+(objCabTmp.getSubTotal()*objCabTmp.getivaven())),6)+" where co_emp = " + objCabTmp.getCodEmp() + " and  co_loc = " + objCabTmp.getCodLoc() + " and co_tipdoc = " + objCabTmp.getTipoDoc() + " and co_doc = " +objCabTmp.getNumDoc();
                    }
                    //System.out.println("SQL:"+strSQL);
                    pstDocPro = conSave.prepareStatement(strSQL);
                    pstDocPro.executeUpdate();                    
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        objRepTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);                        
                        strSQL = "";
                        strSQL = " Insert into tbm_detmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,co_itm,tx_codalt,tx_nomitm,co_bod,tx_unimed,nd_can,nd_preuni)";                        
                        strSQL += " values( " +  objCabTmp.getCodEmp() + "," + objCabTmp.getCodLoc() + "," +objCabTmp.getTipoDoc()+ "," +objCabTmp.getNumDoc()+ "," + (objCabTmp.getNumReg()+j+1)+ "," + objRepTmp.getDatCodItm() + ",'" +objRepTmp.getDatCodAlt() + "','" +objRepTmp.getDatNomItm() + "'," +objRepTmp.getDatCodBod()+",'"+ objRepTmp.getDatUniMed()+ "'," +(objRepTmp.getDatStkCan()*-1)+ "," +objRepTmp.getDatDscPre()+ ")" ;
                        //System.out.println("SQL: " + strSQL);
                        pstDocPro = conSave.prepareStatement(strSQL);
                        pstDocPro.executeUpdate();
                        if (!objCabTmp.MoverInventario(conSave,objCabTmp.getCodEmp(),objRepTmp.getDatCodItm(),objRepTmp.getDatCodBod(),objRepTmp.getDatStkCan(),-1))  
                            return false;                        
                    }                    
                    //Registro de Pagos de Movimiento
                    if (objCabTmp.getEstado())
                    {
                        strSQL = "";
                        strSQL = " Delete from tbm_pagmovinv where co_emp= " +  objCabTmp.getCodEmp() + " and co_loc = " + objCabTmp.getCodLoc()+ " and co_tipdoc = " +objCabTmp.getTipoDoc()+ " and co_doc=" +objCabTmp.getNumDoc();
                        //System.out.println("SQL: " + strSQL);
                        pstDocPro = conSave.prepareStatement(strSQL);
                        pstDocPro.executeUpdate();                        
                    }                    
                    strSQL  = "";
                    strSQL  = " select nd_sub from tbm_cabmovinv ";
                    strSQL += " where co_emp = " + objCabTmp.getCodEmp() + " and  co_loc = " + objCabTmp.getCodLoc() + " and co_tipdoc = " + objCabTmp.getTipoDoc() + " and co_doc = " +objCabTmp.getNumDoc();
                    //System.out.println ("SQL: "+strSQL);
                    rst=stm.executeQuery(strSQL);
                    if (rst.next()){
                        dblCabSub = Double.parseDouble(rst.getString("nd_sub"));
                    }
                    dblRetFuente = objUti.redondeo(dblCabSub/100,2);                    
                    if (objCliente.getTipPer()=='C') dblRetIva  = objUti.redondeo((dblCabSub*(objCabTmp.getivaven()/100))*(30/100),2);
                    dblvalor = dblCabSub + (dblCabSub*(objCabTmp.getivaven()/100)) - (dblRetFuente+dblRetIva);
                    strSQL  = "";
                    strSQL  = " select ne_diacre, ne_numpag from tbm_cabforpag as cabpag left outer join tbm_detforpag as detpag on (cabpag.co_emp = detpag.co_emp and cabpag.co_forpag = detpag.co_forpag) ";
                    strSQL += " where cabpag.co_emp = " +  objCabTmp.getCodEmp() + " and cabpag.co_forpag = " + objCabTmp.getForPag();
                    //System.out.println ("SQL: "+strSQL);
                    rst=stm.executeQuery(strSQL);
		    int IntIndPag=0;
                    while(rst.next())
                    {
                        dblvalor = (dblCabSub + (dblCabSub*(objCabTmp.getivaven()/100)) - (dblRetFuente+dblRetIva) )/ Integer.parseInt(rst.getString("ne_numpag"));
                        datFecReg =""+ (1900+datFecha.getYear())+"/"+ datFecha.getMonth()+"/"+(datFecha.getDay() + Integer.parseInt(rst.getString("ne_diacre")));              
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,ne_diacre,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objCabTmp.getCodEmp()  +","+ objCabTmp.getCodLoc() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ rst.getRow() +"," + rst.getString("ne_diacre") +",'"+ objUti.formatearFecha(objUti.parseDate(datFecReg,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()) +"',"+ objUti.redondeo(dblvalor*-1,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocPro = conSave.prepareStatement(strSQL);
                        pstDocPro.executeUpdate();                                                
			IntIndPag++;                        
                    }
                    if (dblRetFuente>0)
                    {
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,nd_porret,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objCabTmp.getCodEmp() +","+ objCabTmp.getCodLoc() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ (++IntIndPag) +",1,'"+ objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos()) +"',"+ objUti.redondeo(dblRetFuente*-1,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocPro = conSave.prepareStatement(strSQL);
                        pstDocPro.executeUpdate();                                                
                    }
                    if (dblRetIva>0)
                    {
                        strSQL  = " Insert into tbm_pagmovinv (co_emp,co_loc,co_tipdoc,co_doc,co_reg,nd_porret,fe_ven,mo_pag)";                        
                        strSQL += " values ("+  objCabTmp.getCodEmp()  +","+ objCabTmp.getCodLoc() +","+ objCabTmp.getTipoDoc() +","+ objCabTmp.getNumDoc() +","+ (++IntIndPag) +",1,'"+ objUti.formatearFecha(datFecha,objZafParSis.getFormatoFechaHoraBaseDatos()) +"',"+ objUti.redondeo(dblRetIva*-1,6)+")";
                        //System.out.println("SQL: " + strSQL);
                        pstDocPro = conSave.prepareStatement(strSQL);
                        pstDocPro.executeUpdate();                                                
                    }
                    //Creacion de Diario Contable
                    if (!objCabTmp.getEstado()){                        
                        if (!(objCabTmp.CrearAsiento(conSave,objCabTmp.getCodEmp(),objCabTmp.getCodLoc(),objCabTmp.getTipoDoc(),objCabTmp.getNumDoc(),null))) 
                            return false;
                    }else{
                        if (!(objCabTmp.CrearAsiento(conSave,objCabTmp.getCodEmp(),objCabTmp.getCodLoc(),objCabTmp.getTipoDoc(),objCabTmp.getNumDoc())))
                            return false;
                    }                    
                }
            }
            vecDocPro = vecCabDoc;
            blnFacPro = true;
        } catch (java.sql.SQLException e) {
            blnFacPro = false;                        
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);            
       } catch (Exception e)  {
            blnFacPro = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
       }               
        return blnFacPro;        
    }
    /**
     * Metodo para crear relaciones entre los documentos cliente final - empresa
     * empresa - proveedor
     * @return true si se realizo con exito o si no fue necesario hacerlo
     * @return false si tuvo algun problema
     */
    public boolean CrearRelDoc(java.sql.Connection conSave)
    {
        ZafRepInv_03 objCabTmp,objCabTmp2;
        ZafRepInv_02 objDetTmp,objDetTmp2;        
        boolean blnRel = true,blnFind;
        int k,m;
        try{
            if (vecDocCli!=null){
                for (int i=0;i<vecDocCli.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecDocCli.get(i);
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        objDetTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);
                        strSQL = "";                                                
                        strSQL = " Insert into tbr_repinvven (co_empcli, co_loccli, co_tipdoccli, co_doccli, co_regcli, co_emp, co_loc, co_tipdoc, co_doc, co_reg)";                        
                        strSQL += " values ("+ objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal()+ "," + intTipDoc + "," + intNumDoc + "," + objDetTmp.getOriCodReg() + "," +objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + objCabTmp.getTipoDoc()+ "," + objCabTmp.getNumDoc() + "," + (objCabTmp.getNumReg()+j+1)  +  ")";
                        //System.out.println("SQL_cli: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();                        
                    }
                }     
            }
            
            if (vecDocPro!=null){
                for (int i=0;i<vecDocPro.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecDocPro.get(i);                    
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        objDetTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);
                        blnFind = false;
                        k=0;
                        while (k<vecDocCli.size() && !(blnFind))
                        {
                            objCabTmp2 = (ZafRepInv_03) vecDocCli.get(k);
                            m=0;
                            while (m<objCabTmp2.vecRegDet.size() && !(blnFind))
                            {
                                objDetTmp2 = (ZafRepInv_02) objCabTmp2.vecRegDet.get(m);
                                if (objDetTmp.getDatCodEmp() == objDetTmp2.getDatCodEmp() && objDetTmp.getDatCodLoc()==objDetTmp2.getDatCodLoc() && objDetTmp.getDatCodItm() == objDetTmp2.getDatCodItm() && objDetTmp.getOriCodReg() == objDetTmp2.getOriCodReg())
                                {
                                    strSQL = "";
                                    strSQL = " Insert into tbr_repinvven (co_empcli, co_loccli, co_tipdoccli, co_doccli, co_regcli, co_emp, co_loc, co_tipdoc, co_doc, co_reg)";                        
                                    strSQL += " values ("+ objDetTmp.getDatCodEmp() + "," + objDetTmp.getDatCodLoc() + "," + objCabTmp.getTipoDoc()+ "," + objCabTmp.getNumDoc() + "," + (objCabTmp.getNumReg()+j+1) + "," +objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal()+ "," + objCabTmp2.getTipoDoc() + "," + objCabTmp2.getNumDoc()+ "," + (objCabTmp2.getNumReg()+m+1)  +  ")";
                                    //System.out.println("SQL_pro: " + strSQL);
                                    pstCot = conSave.prepareStatement(strSQL);
                                    pstCot.executeUpdate();       
                                    blnFind = true;
                                }   
                                m++;
                            }
                            k++;
                        }
                    }
                }     
            }
       } catch (java.sql.SQLException e) {
            blnRel = false;                        
            //System.out.println(e.toString());            
            try{
                conSave.rollback();
                conSave.close();
            }catch(SQLException Evts){
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);            
            }            
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);                        
       } catch (Exception e)  {
            blnRel = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
       }               
        return blnRel;        
    }
    /**
     * Metodo para crear cotizaciones de compra y venta
     * No mueve inventario ni genera asiento contable
     * Genera 1 cotizacion en compra por cada cotizacion en venta para cliente final solicitada.
     * A diferencia de los metodos GrabaDocCli y GrabaDocPro
     */
    private boolean GrabarTemp(java.sql.Connection conSave)
    {
        boolean blnGraTmp= false,blnFind;        
        ZafRepInv_02 objRepTmp;
        ZafRepInv_03 objCabDoc;
        ZafRepInv_03 objCabTmp ;
        Vector vecCabDoc= new Vector();                
        try
        {              
            //Carga y Genera Doc.Cotizacion :  Tipo objeto CabDoc dentro de un vector.
            for (int i=0;i<vecRepDat.size();i++)
            {
                objRepTmp = (ZafRepInv_02) vecRepDat.get(i);                
                if (!(vecCabDoc==null))
                {
                    blnFind = false;
                    for (int j=0; j<vecCabDoc.size();j++)
                    {
                        objCabTmp = (ZafRepInv_03) vecCabDoc.get(j);
                        if (objCabTmp.getCodEmp() == objRepTmp.getDatCodEmp()  &&  objCabTmp.getCodLoc() == objRepTmp.getDatCodLoc()){                            
                            if (objCabTmp.AgregaDetalle(objRepTmp))
                                    blnFind = true;
                            break;                            
                        }                        
                    }
                    if (!(blnFind))
                    {
                        objCabDoc = new ZafRepInv_03 (objZafRepInv.getTipoDoc(),objRepTmp,objZafParSis);
                        objCabDoc.AgregaDetalle(objRepTmp);
                        vecCabDoc.add(objCabDoc);
                    }
                }                
            }
            int IndDoc = 0;
            for (int i=0; i<vecCabDoc.size();i++)
            {           
                objCabDoc = ((ZafRepInv_03)vecCabDoc.get(i));
                if (objCabDoc.getNumDoc()==0){
                    objCabDoc.setEstado(true);
                    IndDoc++;
                    objCabDoc. AsignarCodDoc(IndDoc);
                }
                //System.out.println("cot: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getNumDoc() +" emp:"+((ZafRepInv_03)vecCabDoc.get(i)).getCodEmp()  + " loc: " + ((ZafRepInv_03)vecCabDoc.get(i)).getCodLoc() + " sub total: "+ ((ZafRepInv_03)vecCabDoc.get(i)).getSubTotal());                
            }                       
            String strFecha = objUti.formatearFecha(objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos()),objZafParSis.getFormatoFechaHoraBaseDatos());
            if (!(conSave==null))
            {
                conSave.setAutoCommit(false);
                //Elimino cotizacion anteriores (compra y venta).
                if (Integer.parseInt(vecConfDoc.get(INT_CONF_REP_NEW).toString())==2) //2.- Modifica
                {                    
                    strSQL  = "";
                    /*  Recuperacion de documentos para eliminacion. */
                    strSQL  = " select rep1.co_empegr, rep1.co_locegr, rep1.co_cotegr,  rep1.co_coting ";
                    strSQL += " from (select  co_emping, co_locing,co_coting from tbr_repinvcot where co_empegr = "+ objZafParSis.getCodigoEmpresa() +" and co_locegr = "+ objZafParSis.getCodigoLocal() + " and co_cotegr = " + intNumDoc + " group by co_emping, co_locing,co_coting ) as rep2   ";
                    strSQL += " left outer join tbr_repinvcot as rep1 on (rep1.co_emping =rep2.co_emping and rep1.co_locing = rep2.co_locing and rep1.co_coting = rep2.co_coting) " ;
                    strSQL += " where  rep1.co_cotegr <> " + intNumDoc;
                    strSQL += " group by rep1.co_empegr, rep1.co_locegr,rep1.co_cotegr, rep1.co_coting ";
                    strSQL += " order by rep1.co_coting ";
                    //System.out.println("SQL: " + strSQL);
                    stm = conSave.createStatement();
                    rst = stm.executeQuery(strSQL);
                    while (rst.next())
                    {
                        strSQL = "";
                        strSQL = " Delete from tbr_repinvcot where co_emping = " + objZafParSis.getCodigoEmpresa() + " and co_locing = " + objZafParSis.getCodigoLocal() + " and co_coting = " + rst.getString("co_coting");                        
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();        
                        strSQL = "";
                        strSQL = " Delete from tbm_detcotven where co_emp = " + rst.getString("co_empegr") + " and co_loc = " + rst.getString("co_locegr") + " and co_cot = " + rst.getString("co_cotegr");                        
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();
                        strSQL = "";
                        strSQL = " Delete from tbm_cabcotven where co_emp = " + rst.getString("co_empegr") + " and co_loc = " + rst.getString("co_locegr") + " and co_cot = " + rst.getString("co_cotegr");                        
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();
                        strSQL = "";
                        strSQL = " Delete from tbm_detcotcom where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoLocal() + " and co_cot = " + rst.getString("co_coting");                        
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();
                        strSQL = "";                        
                        strSQL = " Delete from tbm_cabcotcom where co_emp = " + objZafParSis.getCodigoEmpresa() + " and co_loc = " + objZafParSis.getCodigoLocal() + " and co_cot = " + rst.getString("co_coting");                        
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();

                    }
                } 
                //Grabo Nuevas Cotizacion en venta                
                for (int i=0;i<vecCabDoc.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecCabDoc.get(i);
                    strSQL = "";
                    strSQL = " Insert into tbm_cabcotven (co_emp , co_loc , co_cot, fe_cot, co_cli, co_ven,  co_forpag, nd_sub, nd_poriva, nd_valdes,  tx_obs2,  fe_ing, fe_ultmod,co_usring,co_usrmod)";                        
                    strSQL += " values ("+ objCabTmp.getCodEmp() + "," + objCabTmp.getCodLoc() + "," + objCabTmp.getNumDoc() + ",'"+strFecha +"', " + objCabTmp.getco_cli() + "," +  objCabTmp.getco_ven()  + "," + objCabTmp.getForPag()  + "," + objCabTmp.getSubTotal() + "," + objCabTmp.getivaven() + ",0,'Cotizacion Automatica Generada por Reposicion','"+strFecha +"','"+  strFecha +"'," +objZafParSis.getCodigoUsuario() + ","+ objZafParSis.getCodigoUsuario() + ")";
                    //System.out.println("SQL: " + strSQL);
                    pstCot = conSave.prepareStatement(strSQL);
                    pstCot.executeUpdate();
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        ZafRepInv_02 objDetTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);                        
                        strSQL = "";
                        strSQL = " Insert into tbm_detcotven (co_emp, co_loc, co_cot, co_reg, co_itm, tx_codalt, tx_nomitm, nd_can, nd_preuni, nd_pordes,co_bod)";                        
                        strSQL += " values ("+ objDetTmp.getDatCodEmp() + "," + objDetTmp.getDatCodLoc() + "," + objCabTmp.getNumDoc() + "," + (j+1)  + "," + objDetTmp.getDatCodItm() + ",'"+ objDetTmp.getDatCodAlt() +"','" + objDetTmp.getDatNomItm() +"'," + objDetTmp.getDatStkCan()+ "," + objUti.redondeo(objDetTmp.getDatDscPre(),6)+ ",0," + objDetTmp.getDatCodBod()  + ")";
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();
                        
                    }
                }
                //Grabo nuevas cotizacion en compra               
                for (int i=0;i<vecCabDoc.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecCabDoc.get(i);
                    strSQL = "";
                    strSQL = " Insert into tbm_cabcotcom (co_emp , co_loc , co_cot, fe_cot, co_prv, co_com,  co_forpag, nd_sub, nd_poriva, nd_valdes,  tx_obs2,  fe_ing, fe_ultmod,co_usring,co_usrmod)";                        
                    strSQL += " values ("+ objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + (objCabTmp.getNumCot()) + ",'"+strFecha +"', " + objCabTmp.getco_pro()  + "," +  objCabTmp.getco_com() + "," + objCabTmp.getForPag()  + "," + objCabTmp.getSubTotal() + "," + objCabTmp.getivacom() + ",0,'Cotizacion Automatica Generada por Reposicion','"+strFecha +"','"+  strFecha +"'," +objZafParSis.getCodigoUsuario() + ","+ objZafParSis.getCodigoUsuario() + ")";
                    //System.out.println("SQL: " + strSQL);
                    pstCot = conSave.prepareStatement(strSQL);
                    pstCot.executeUpdate();                    
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        ZafRepInv_02 objDetTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);
                        strSQL = "";
                        strSQL = " Insert into tbm_detcotcom (co_emp, co_loc, co_cot, co_reg, co_itm,tx_codalt, tx_nomitm, nd_can, nd_cosuni, nd_pordes,co_bod)";                        
                        strSQL += " values ("+ objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + (objCabTmp.getNumCot()) + "," + (j+1)  + "," + objDetTmp.getOriCodItm() + ",'"+ objDetTmp.getOriCodAlt() + "','"+ objDetTmp.getOriNomItm()+ "'," + objDetTmp.getDatStkCan()+ "," + objUti.redondeo(objDetTmp.getDatDscPre(),6)+ ",0," + objDetTmp.getOriCodBod()  + ")";
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();
                        
                    }
                }                
                //Crea Relacion Cotizacion compra-venta                
                for (int i=0;i<vecCabDoc.size();i++)
                {
                    objCabTmp = (ZafRepInv_03) vecCabDoc.get(i);
                    for (int j=0;j<objCabTmp.vecRegDet.size();j++)
                    {
                        ZafRepInv_02 objDetTmp = (ZafRepInv_02) objCabTmp.vecRegDet.get(j);
                        strSQL = "";
                        strSQL = " Insert into tbr_repinvcot (co_empegr, co_locegr, co_cotegr, co_regegr, co_emping, co_locing, co_coting, co_reging)";                        
                        strSQL += " values ("+ objDetTmp.getDatCodEmp() + "," + objDetTmp.getDatCodLoc() + "," + objCabTmp.getNumDoc() + "," + (j+1) + "," +objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + objCabTmp.getNumCot() + "," + (j+1)  +  ")";
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();                                  
                        strSQL = "";                                                
                        strSQL = " Insert into tbr_repinvcot (co_empegr, co_locegr, co_cotegr, co_regegr, co_emping, co_locing, co_coting, co_reging)";                        
/*intNumDoc o objDetTmp.getDatCotCom(); pendiente de analisis.                        
                        if (objDetTmp.getDatCotCom()!=0)
                            strSQL += " values ("+ objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + objDetTmp.getDatCotCom() + "," + objDetTmp.getOriCodReg() + "," +objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + objCabTmp.getNumCot() + "," + (j+1)  +  ")";
                        else
 */
                            strSQL += " values ("+ objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + intNumDoc + "," + objDetTmp.getOriCodReg() + "," +objZafParSis.getCodigoEmpresa() + "," + objZafParSis.getCodigoLocal() + "," + objCabTmp.getNumCot() + "," + (j+1)  +  ")";
                        //System.out.println("SQL: " + strSQL);
                        pstCot = conSave.prepareStatement(strSQL);
                        pstCot.executeUpdate();                        
                    }
                }     
                blnGraTmp = true;
            }
        } catch (java.sql.SQLException e) {
            blnGraTmp = false;            
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);            
       } catch (Exception e)  {
            blnGraTmp = false;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
       }        
        return blnGraTmp;
        
    }
    /**
     * Metodo Polimorfico para crear las reposiciones mostrando un JDialog
     *  Recibe el codigo de la bodega , codigo del item y cantidad solicitada
     *  Devuelve el vector resultante de la reposicion.
     */
    public Vector CrearReposicion(int intparCodBod,int intparCodItm,double intparCanSol)
    {
        Vector vecCreHil=null;        
        vecConfItm.add(INT_DAT_COD_BOD, new Integer(intparCodBod));
        vecConfItm.add(INT_DAT_COD_ITM, new Integer(intparCodItm));
        vecConfItm.add(INT_DAT_STK_CAN, new Double(intparCanSol));        
        vecDat = null;        
        callJDialog();
        vecCreHil = getCargarData();        
        return vecCreHil;
        
    }
    /**
     * Metodo Polimorfico para crear las reposiciones mostrando un JDialog
     *  Recibe el codigo de la bodega , codigo del item , cantidad solicitada y el vector de datos ya creado para ese item
     *  Devuelve el vector resultante de la reposicion.
     */
    public Vector CrearReposicion(int intparCodBod,int intparCodItm,double intparCanSol,Vector vecparData)
    {
        Vector vecCreHil=null;
        vecConfItm.add(INT_DAT_COD_BOD,new Integer(intparCodBod));
        vecConfItm.add(INT_DAT_COD_ITM,new Integer(intparCodItm));
        vecConfItm.add(INT_DAT_STK_CAN,new Double(intparCanSol));
        vecDat = vecparData;
        callJDialog();
        vecCreHil = getCargarData();        
        return vecCreHil;
    }
    /**
     * Metodo Creado para cargar el objeto JDialog para visualizar las bodegas.
     *  Verifica estado del vector vecConfDoc (blnparVerDlg) para visualizar o no JDialog
     *  
     */
    private void callJDialog()
    {
        try{
            objZafRepInv.objRepDlg = new ZafRepInv_01(new javax.swing.JFrame(),objZafParSis, objZafRepInv.vecConfDoc, vecConfItm , vecDat);
            if (Boolean.valueOf(vecConfDoc.get(INT_CONF_VER_DLG).toString()).booleanValue()){
                if (objRepDlg.cargarBodegaReposicion())
                    objZafRepInv.objRepDlg.show();
            }
            setCargarData();
            //verData();
        } catch (Exception e)  {
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }                            
    }
    /**
     * Metodo que permite cargar el vector resultante de la reposicion generada del JDialog 
     * Es de uso local.
     */
    private void setCargarData()
    {        
        vecDat = objZafRepInv.objRepDlg.getData();        
        if (vecDat==null)System.out.println("Vacio...");
        else System.out.println("Cargado...");   
    }
    /**
     * Metodo para setear el vector que contiene los objetos reposicion de un item-registro.
     */
    public void setCargarData(Vector vecparData)
    {
        vecDat = vecparData;
//        if (vecDat==null) System.out.println("Vacio...");
//        else System.out.println("Cargado...");   
    }
    /**
     * Metodo que devuelve el vector resultante de la ultima reposicion.
     */
    public Vector getCargarData()
    {
        return vecDat;
    }
    /**
     * Metodo para cargar las reposiciones De un registro deterimado basado en un documento ya grabado.
     *  Ademas del numero del registro se requerira el Tipo de documento y el numero del documentp
     * Retorna el vector resultante de la busqueda o null si no lo encuentra
     */
    public Vector getCargarData(int intparTipDoc, int intparNumDoc,int intparNumReg)
    {
        Vector vecCarDoc = null;
        try
        {
            con=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos());            
            if (con!=null)
            {
                stm=con.createStatement();
                stmAux=con.createStatement();
                strSQL = "";                
                if (intparTipDoc == -1){
                    //Caso Cotizacion
                    intNumDoc = intparNumDoc;
                    strSQL  = "";
                    strSQL  = " Select co_coting , co_reging  , co_itm , co_bod ";
                    strSQL += " from tbr_repinvcot  as rep left outer join tbm_detcotcom as detcot on (rep.co_emping = detcot.co_emp  and rep.co_locing = detcot.co_loc and rep.co_coting = detcot.co_cot) ";
                    strSQL += " where co_empegr = " + objZafParSis.getCodigoEmpresa() + " and co_locegr = " + objZafParSis.getCodigoLocal() + " and co_cotegr = " + intparNumDoc + " and co_regegr = " + intparNumReg ;
                    strSQL += " group by co_coting , co_reging  , co_itm , co_bod ";
                    //System.out.println("SQL: " + strSQL);
                    rst = stm.executeQuery(strSQL);
                    while (rst.next())
                    {
                        strSQL  = "";
                        strSQL  = " Select co_cotegr as co_cot, co_empegr as co_emp, co_locegr as co_loc, detcot.co_bod, invbod.nd_stkact , detcot.nd_can, detcot.nd_preuni as nd_pre, invbod.co_itm "; 
                        strSQL += " from tbm_emp as emp  left outer join tbr_repinvcot as repcot on (emp.co_emp=repcot.co_empegr) ";
                        strSQL += " left outer join tbm_detcotven  as detcot on (repcot.co_empegr=detcot.co_emp and repcot.co_locegr = detcot.co_loc and repcot.co_cotegr=detcot.co_cot and repcot.co_regegr= detcot.co_reg )  ";
                        strSQL += " left outer join tbm_loc as loc on (detcot.co_emp = loc.co_emp and detcot.co_loc = loc.co_loc) ";
                        strSQL += " left outer join tbm_bod as bod on (loc.co_emp = bod.co_emp and detcot.co_bod = bod.co_bod)  ";
                        strSQL += " left outer join tbr_bodloc as bodloc on (bod.co_emp=bodloc.co_emp and loc.co_loc = bodloc.co_loc and bod.co_bod = bodloc.co_bod) ";
                        strSQL += " left outer join tbm_invbod  as invbod on (bod.co_emp = invbod.co_emp and bodloc.co_bod = invbod.co_bod and detcot.co_itm=invbod.co_itm)  ";
                        strSQL += " where co_emping = " + objZafParSis.getCodigoEmpresa() + " and co_locing = " + objZafParSis.getCodigoLocal() + " and co_coting = " + Integer.parseInt(rst.getString("co_coting")) + " and co_reging = " + Integer.parseInt(rst.getString("co_reging")) + " and co_empegr<>" + objZafParSis.getCodigoEmpresa();
                        //System.out.println("SQL: " + strSQL);
                        rstAux = stmAux.executeQuery(strSQL);                        
                        while (rstAux.next())
                        {
                            if (vecCarDoc==null) vecCarDoc = new Vector();
                            ZafRepInv_02 objRepDatTmp = new ZafRepInv_02();
                            objRepDatTmp.setDatCotCom(Integer.parseInt(rst.getString("co_coting")));
                            objRepDatTmp.setOriCodBod(Integer.parseInt(rst.getString("co_bod")));
                            objRepDatTmp.setOriCodItm(Integer.parseInt(rst.getString("co_itm")));
                            objRepDatTmp.setDatCodCot(Integer.parseInt(rstAux.getString("co_cot")));
                            objRepDatTmp.setDatCodEmp(Integer.parseInt(rstAux.getString("co_emp")));
                            objRepDatTmp.setDatCodLoc(Integer.parseInt(rstAux.getString("co_loc")));
                            objRepDatTmp.setDatCodBod(Integer.parseInt(rstAux.getString("co_bod")));
                            objRepDatTmp.setDatCodItm(Integer.parseInt(rstAux.getString("co_itm")));
                            objRepDatTmp.setDatStkCan(Double.parseDouble(rstAux.getString("nd_can")));
                            objRepDatTmp.setDatDscPre(Double.parseDouble(rstAux.getString("nd_pre")));
                            vecCarDoc.add(objRepDatTmp);
                        }                 
                        rstAux.close();
                    }
                    stmAux.close();
                }else{
                    // Caso Factura
                    intNumDoc = intparNumDoc;
                    strSQL  = "";
                    strSQL  = " Select rep.co_tipdoc, rep.co_doc , rep.co_reg , co_itm , co_bod ";
		    strSQL += " from tbr_repinvven  as rep left outer join tbm_detmovinv as detcot on (rep.co_emp = detcot.co_emp  and rep.co_loc = detcot.co_loc and rep.co_tipdoc = detcot.co_tipdoc and rep.co_doc = detcot.co_doc and rep.co_reg = detcot.co_reg )";
		    strSQL += " where co_empcli= " + objZafParSis.getCodigoEmpresa() + " and co_loccli = " + objZafParSis.getCodigoLocal() + " and co_tipdoccli = " + intparTipDoc + " and co_doccli = " + intparNumDoc + " and co_regcli = " + intparNumReg ;
		    strSQL += " group by rep.co_tipdoc, rep.co_doc , rep.co_reg  , co_itm , co_bod ";
                    //System.out.println("SQL: " + strSQL);
                    rst = stm.executeQuery(strSQL);
                    while (rst.next())
                    {
                        strSQL  = "";
                        strSQL  = " Select co_doccli as co_cot, co_empcli as co_emp, co_loccli as co_loc, detcot.co_bod, invbod.nd_stkact , detcot.nd_can, detcot.nd_preuni as nd_pre, invbod.co_itm "; 
                        strSQL += " from tbm_emp as emp  left outer join tbr_repinvven as repcot on (emp.co_emp=repcot.co_empcli) ";
                        strSQL += " left outer join tbm_detmovinv as detcot on (repcot.co_empcli = detcot.co_emp  and repcot.co_loccli = detcot.co_loc and repcot.co_tipdoccli = detcot.co_tipdoc and repcot.co_doccli = detcot.co_doc  and repcot.co_regcli = detcot.co_reg)";
                        strSQL += " left outer join tbm_loc as loc on (detcot.co_emp = loc.co_emp and detcot.co_loc = loc.co_loc) ";
                        strSQL += " left outer join tbm_bod as bod on (loc.co_emp = bod.co_emp and detcot.co_bod = bod.co_bod)  ";
                        strSQL += " left outer join tbr_bodloc as bodloc on (bod.co_emp=bodloc.co_emp and loc.co_loc = bodloc.co_loc and bod.co_bod = bodloc.co_bod) ";
                        strSQL += " left outer join tbm_invbod  as invbod on (bod.co_emp = invbod.co_emp and bodloc.co_bod = invbod.co_bod and detcot.co_itm=invbod.co_itm)  ";
                        strSQL += " where repcot.co_emp = " + objZafParSis.getCodigoEmpresa() + "  and repcot.co_loc = " + objZafParSis.getCodigoLocal() + " and repcot.co_tipdoc = " + Integer.parseInt(rst.getString("co_tipdoc")) + " and repcot.co_doc = " + Integer.parseInt(rst.getString("co_doc")) + " and repcot.co_reg = " + Integer.parseInt(rst.getString("co_reg")) + " and co_empcli<>" + objZafParSis.getCodigoEmpresa();                        
                        //System.out.println("SQL: " + strSQL);
                        rstAux = stmAux.executeQuery(strSQL);                        
                        while (rstAux.next())
                        {
                            if (vecCarDoc==null) vecCarDoc = new Vector();
                            ZafRepInv_02 objRepDatTmp = new ZafRepInv_02();
                            objRepDatTmp.setDatCotCom(Integer.parseInt(rst.getString("co_doc")));
                            objRepDatTmp.setOriCodBod(Integer.parseInt(rst.getString("co_bod")));
                            objRepDatTmp.setOriCodItm(Integer.parseInt(rst.getString("co_itm")));
                            objRepDatTmp.setDatCodCot(Integer.parseInt(rstAux.getString("co_cot")));
                            objRepDatTmp.setDatCodEmp(Integer.parseInt(rstAux.getString("co_emp")));
                            objRepDatTmp.setDatCodLoc(Integer.parseInt(rstAux.getString("co_loc")));
                            objRepDatTmp.setDatCodBod(Integer.parseInt(rstAux.getString("co_bod")));
                            objRepDatTmp.setDatCodItm(Integer.parseInt(rstAux.getString("co_itm")));
                            objRepDatTmp.setDatStkCan(Double.parseDouble(rstAux.getString("nd_can")));
                            objRepDatTmp.setDatDscPre(Double.parseDouble(rstAux.getString("nd_pre")));
                            vecCarDoc.add(objRepDatTmp);
                        }                 
                        rstAux.close();
                    }
                    stmAux.close();
                }                                    
                rst.close();
                stm.close();
                con.close();            
            }            
        } catch (java.sql.SQLException e) {
            vecCarDoc = null;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        } catch (Exception e)  {
            vecCarDoc = null;
            //System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }        
        return vecCarDoc;        
    }
   private void MensajeError(String strTit,String strMsg){
        javax.swing.JOptionPane objMsgErr=new javax.swing.JOptionPane();
        objMsgErr.showMessageDialog(new javax.swing.JInternalFrame(),strMsg,strTit, javax.swing.JOptionPane.ERROR_MESSAGE);        
    }
    
    private void MensajeInf(String strTit,String strMsg){
        javax.swing.JOptionPane objMsgErr=new javax.swing.JOptionPane();
        objMsgErr.showMessageDialog(new javax.swing.JInternalFrame(),strMsg,strTit, javax.swing.JOptionPane.INFORMATION_MESSAGE);        
    }

 /* 
  private class ZafThreadGUI extends Thread
    {
        public void run()
        {
            try{
                objZafRepInv.objRepDlg = new ZafRepInv_01(new javax.swing.JFrame(),objZafParSis, objZafRepInv.vecConfDoc, vecConfItm , vecDat);
                if (Boolean.valueOf(vecConfDoc.get(INT_CONF_VER_DLG).toString()).booleanValue())
                    objZafRepInv.objRepDlg.show();
                setCargarData();
                //verData();
                objThrGUI=null;
            }catch (Exception e)   {
                objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
            }
        }
    }
   */ 
}
