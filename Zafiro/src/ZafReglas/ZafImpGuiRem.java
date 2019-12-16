/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import Librerias.ZafJCE.ZafJCEAlgAES;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.Size2DSyntax;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.OrientationRequested;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import javax.sound.midi.Soundbank;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 * Clase para impresion de las Guias Remision
 * @author CHRISTIAN MATEO.
 */
public class ZafImpGuiRem {
    
    
    /*IMPORTACIONES*/
    private static final String RUTAREPORTEODIMPTTUVAL="/Reportes/Importaciones/ZafImp06/ZafRptImp06.jasper";
    private static final String RUTAREPORTEODIMPTCASUIO="/Reportes/Importaciones/ZafImp06/ZafRptImp06.jasper";
    private static final String RUTAREPORTEODIMPTDIM="/Reportes/Importaciones/ZafImp06/ZafRptImp06.jasper";
    
    private static final String NOMBREIMPRESORAODTUVALTRANS="printOrdDesBodImp";
    private static final String NOMBREIMPRESORAODCASUIOTRANS="printOrdDesBodImp";
    private static final String NOMBREIMPRESORAODDIMTRANS="printOrdDesBodImp";
    
    

    /*INMACONSA*/
    private static final String RUTAREPORTEODINMTUVAL="/Reportes/Compras/ZafCom23/Tuval/Inmaconsa/ZafRptCom23_01.jasper";
    private static final String RUTAREPORTEODINMCASUIO="/Reportes/Compras/ZafCom23/Castek/Inmaconsa/ZafRptCom23_01.jasper";
    private static final String RUTAREPORTEODINMDIM="/Reportes/Compras/ZafCom23/Dimulti/Inmaconsa/ZafRptCom23_01.jasper";

    
    private static final String NOMBREIMPRESORAODINMTUVAL="od_inmaconsa";
    private static final String NOMBREIMPRESORAODINMCASUIO="od_inmaconsa";
    private static final String NOMBREIMPRESORAODINMDIM="od_inmaconsa";
    
    private final static int CODEMPINMTUV=1;    
    private final static int CODLOCINMTUV=10;
    private final static int CODEMPINMCASUIO=2;
    private final static int CODLOCINMCASUIO=5;
    private final static int CODEMPINMDIM=4;
    private final static int CODLOCINMDIM=10;     
    
    public ZafImpGuiRem(){
    
    }
    
    /**
     * Metodo que imprime guias de Remision de Clientes que no retiran y siempre que esten autorizadas por el SRI.
     * Sigue el mismo proceso del servicio anterior salvo que ahora pasa por una cola de impresion usando el JAVA PRINTER SERVICE.
     * @param con conexion acceso a datos.
     * @param intCodEmpGui codigo de la Empresa de la Guia de remision.
     * @param intCodLocGui codigo del local de la Guia de remision.
     * @param intCodTipGui codigo del tipo del documento de la Guia de remision.
     * @param intCodDocGui codigo del documento de la guia de Remision.
     * @return boolean indicando si se pudo imprimir el trabajo y ha sido enviado a la cola de Impresion.
     */  
    public boolean imprimirGuiRemCliNotRet_autSRI(Connection con, int intCodEmpGui, int intCodLocGui, int intCodTipGui, int intCodDocGui,String strRtaRpt, String strNomImp, int intCodBodGrp){
        boolean blnRes=true;
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
        String strRepUtiEmp="";
        String strNomPrn=""; 
        String strSQL="";
        String strSQLRep="", strSQLSubRep="";
        Connection conGuiRemCliNotRetAutSri;        
        Statement stmGuiRemCliNotRetAutSri;
        ResultSet rstGuiRemCliNotRetAutSri;
        
        Connection conGuiRemCliNotRetAutSri_estImp;
        Statement stmGuiRemCliNotRetAutSri_estImp;
        
        
        try{
            ZafJCEAlgAES objJCEAlgAES=new ZafJCEAlgAES();
            
            if( (con!=null) ){
                System.out.println("------------imprimirGuiRemCliNotRet_autSRI------------------------");
                strDirSis=getDirectorioSistema();        
                DIRECCION_REPORTE_GUIA=strDirSis+strRtaRpt;
                System.out.println("imprimirGuiRemCliNotRet-Ruta Reporte GR ->  "+DIRECCION_REPORTE_GUIA);
                
                stmGuiRemCliNotRetAutSri=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY );
                //Query que retorna todas las GR pendientes de imprimir y que han sido autorizadas por el SRI                
                //co_mnu=2205 es de confirmacion de egreso normal, sin cliente retira
                strSQL="";
                strSQL+="SELECT a.co_mnu, CASE WHEN a.co_mnu=2205 THEN 'N' WHEN a.co_mnu=2915 THEN 'S' END AS st_cliRet,";
                strSQL+=" a1.co_emp, a1.co_loc, a1.co_tipdoc, a1.co_doc, a1.ne_numDoc";
                strSQL+=" FROM tbr_bodEmpBodGrp as a6";
                strSQL+=" inner join tbm_bod as a7 on (a7.co_emp=a6.co_emp and a7.co_bod=a6.co_bod  )";
                strSQL+=" inner join tbm_cabguirem as a1 on ( a1.co_emp=a7.co_emp and a1.co_ptopar=a7.co_bod )";
                strSQL+=" inner join tbm_cabingegrmerbod as a on ( a1.co_emp=a.co_emp and a1.co_loc=a.co_locrelguirem and a1.co_tipdoc=a.co_tipdocrelguirem and a1.co_doc=a.co_docrelguirem )";
                strSQL+=" WHERE /*( a6.co_empGrp=0 AND a6.co_bodGrp = " + intCodBodGrp + "  )  AND*/";
                strSQL+=" a1.ne_numdoc!=0 and a1.st_reg = 'A' AND a1.st_imp='N'";
                strSQL+=" and a.st_reg='A' and a.tx_tipCon in ('P','T') ";
                strSQL+=" AND a1.st_autfacele='C'";
                strSQL+=" and  a.co_mnu = 2205 ";
                strSQL+=" and a1.co_emp="+ intCodEmpGui;
                strSQL+=" and a1.co_loc="+ intCodLocGui;
                strSQL+=" and a1.co_tipdoc="+intCodTipGui;
                strSQL+=" and a1.co_doc="+intCodDocGui;
                
                System.out.println("imprimirGuiRemCliNotRet_autSRI: " + strSQL);
                
                rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                int cnt=0;
                while(!(rstGuiRemCliNotRetAutSri.next())&& cnt<=10){
                    rstGuiRemCliNotRetAutSri=stmGuiRemCliNotRetAutSri.executeQuery(strSQL);
                    Thread.sleep(1000);
                    cnt++;
                }
                System.out.println("contador de tiempo "+cnt);
                rstGuiRemCliNotRetAutSri.beforeFirst();
                while(rstGuiRemCliNotRetAutSri.next()){
                    
                    //Query del reporte
                    strSQL="";
                    strSQL+="select a1.co_emp, a1.co_loc, a1.co_tipDoc, a1.co_doc";
                    strSQL+=", a1.tx_numautfacele, a1.fe_autfacele, a1.tx_claaccfacele";
                    strSQL+=" , a1.fe_initra, a1.fe_tertra, a1.fe_ing, trim(a1.tx_obs2) as obs2,";
                    strSQL+=" case when a1.tx_numped is null then '' else a1.tx_numped end as tx_numped,";
                    strSQL+=" emp.tx_nom, tipdoc.tx_descor, a1.fe_ing, a1.tx_datdocoriguirem,a1.co_ptopar, a1.tx_ptopar,";
                    strSQL+=" case when a1.co_ptopar =15 then 'INMACONSA' else '' end as puntopartida,";
                    strSQL+=" emp.tx_nom  as empresa, a1.tx_telclides, a1.tx_rucclides,";
                    strSQL+=" (substring(a1.tx_numserdocori,1,3) || '-' || substring(a1.tx_numserdocori,5,3) || '-' || to_char(ne_numdoc, '000000000')) as tx_numdoc,";
                    strSQL+=" a1.ne_numdoc, a1.fe_doc, a1.tx_nomclides, a1.co_clides,";
                    strSQL+=" a1.tx_nomven, a1.tx_dirclides, a1.tx_vehret, a1.tx_choret, a3.tx_deslar,";
                    strSQL+=" a1.tx_ciuclides as cuidad, emp.tx_desconesp as contri1, emp.tx_nom  as empresa,";
                    strSQL+=" (select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kgramo,";
                    strSQL+=" (select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo from tbm_detguirem as x";
                    strSQL+=" inner join tbm_inv as inv on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                    strSQL+=" WHERE x.co_emp=a1.co_emp and x.co_loc=a1.co_loc  and x.co_tipdoc=a1.co_tipdoc and x.co_doc=a1.co_doc ) as kilo,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_idetra ELSE tra.tx_ide END AS tx_idetra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_nomtra ELSE tra.tx_nom || ' ' || tra.tx_ape END AS tx_nomtra,";
                    strSQL+=" CASE WHEN veh.tx_pla is null THEN a1.tx_plavehtra ELSE veh.tx_pla END AS tx_plaveh";
                    strSQL+=" FROM tbm_cabguirem as a1";
                    strSQL+=" LEFT OUTER JOIN tbm_var as a3 ON (a1.co_forret = a3.co_reg )";
                    strSQL+=" inner JOIN tbm_loc as loc ON ( a1.co_emp = loc.co_emp and  a1.co_loc=loc.co_loc)";
                    strSQL+=" inner JOIN tbm_ciu as ciu ON ( loc.co_ciu = ciu.co_ciu)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a1.co_emp)";
                    strSQL+=" inner JOIN tbm_cabtipdoc as tipdoc ON ( tipdoc.co_emp = a1.co_emp and tipdoc.co_loc=a1.co_loc and tipdoc.co_tipdoc=a1.co_tipdoc)";
                    strSQL+=" LEFT JOIN tbm_veh as veh on (veh.co_veh = a1.co_veh)";
                    strSQL+=" LEFT JOIN tbm_tra as tra on (tra.co_tra = a1.co_cho)";
                    strSQL+=" WHERE a1.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and a1.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and a1.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and a1.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" AND a1.st_autfacele='C' AND a1.st_imp='N'";
                    strSQLRep=strSQL;
                    System.out.println("strSQLRep: " + strSQLRep);

                    //Query del subReporte
                    strSQL="";
                    strSQL+="select case when inv.nd_pesitmkgr is null then '(P0)' else case  when inv.nd_pesitmkgr <= 0 then '(P0)' else '' end end as tiepes,";
                    strSQL+=" a2.tx_obs1,emp.tx_nom,a2.tx_codalt,";
                    strSQL+=" CASE WHEN trim(a2.tx_nomitm)=trim(inv.tx_nomitm) THEN a2.tx_nomitm ELSE a2.tx_nomitm || ' (**) ' END as tx_nomitm,";
                    strSQL+=" abs(a2.nd_can) as can,a2.tx_unimed";
                    strSQL+=" FROM tbm_cabguirem as DocCab";
                    strSQL+=" INNER JOIN tbm_detguirem as a2";
                    strSQL+=" ON (DocCab.co_emp=a2.co_emp and DocCab.co_loc=a2.co_loc and DocCab.co_tipdoc=a2.co_tipdoc and DocCab.co_doc=a2.co_doc)";
                    strSQL+=" inner join tbm_inv as inv on(a2.co_emp=inv.co_emp and a2.co_itm=inv.co_itm)";
                    strSQL+=" inner JOIN tbm_emp as emp ON ( emp.co_emp = a2.co_emp)";
                    strSQL+=" WHERE DocCab.co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                    strSQL+=" and DocCab.co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                    strSQL+=" and DocCab.co_tipdoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                    strSQL+=" and DocCab.co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                    strSQL+=" order by a2.co_reg";
                    strSQLSubRep=strSQL;
                    System.out.println("strSQLSubRep: " + strSQLSubRep);
                    
                    try{
                        System.out.println("Ruta Reporte GUIREM ->  "+DIRECCION_REPORTE_GUIA);
                        Map mapPar = new HashMap();                
                        mapPar.put("strSQLRep", strSQLRep);
                        mapPar.put("strSQLSubRep", strSQLSubRep);
                        mapPar.put("SUBREPORT_DIR", strDirSis);
                        mapPar.put("imgCodEmp", rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        mapPar.put("imgRut", strDirSis);
                        
                        System.out.println("SUBREPORT_DIR: " + strDirSis);
                        System.out.println("imgCodEmp: " + rstGuiRemCliNotRetAutSri.getString("co_emp"));
                        System.out.println("imgRut: " + strDirSis);                        
                        
                        
                        PrintRequestAttributeSet objPriReqAttSet=new HashPrintRequestAttributeSet();
                        objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

//                        objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH) ); 
//                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                        if( (intCodBodGrp==1) || (intCodBodGrp==5) || (intCodBodGrp==11) || (intCodBodGrp==2) || (intCodBodGrp==3)  )//california, manta, sto domingo, via a daule, quito
                            objPriReqAttSet.add(new Copies(3));
                        
                        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        PrinterName printerName=new PrinterName( strNomImp , null); 
                        PrintServiceAttributeSet printServiceAttributeSet=new HashPrintServiceAttributeSet(); 
                        printServiceAttributeSet.add(printerName);        
                        JRPrintServiceExporter objJRPSerExp=new JRPrintServiceExporter(); 
                        objJRPSerExp.setParameter(JRExporterParameter.JASPER_PRINT, reportGuiaRem); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE); 
                        objJRPSerExp.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE); 
                        objJRPSerExp.exportReport(); 
                        objPriReqAttSet=null;                        
                        
                        /*
                        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
                        JasperViewer.viewReport(reportGuiaRem);

                        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
                        aset.add(MediaSizeName.ISO_A4);
                        
                        if(intCodBodGrp==1)
                           aset.add(new Copies(3));                        
                        aset.add(Sides.ONE_SIDED);
                       

                        PrintService[] pservices=PrinterJob.lookupPrintServices();
                        if (pservices.length>0) {
                            int indice=traeIndice(pservices, strNomImp);
                            DocPrintJob pj = pservices[indice].createPrintJob();
                            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                            //pj.print(doc, aset);
                        }  
                        */
                        
                        
                        try{
                            //con.setAutoCommit(false);

                            stmGuiRemCliNotRetAutSri_estImp=con.createStatement();
                            strSQL="";
                            strSQL+="UPDATE tbm_cabGuiRem";
                            strSQL+=" SET st_imp='S'";
                            strSQL+=" WHERE co_emp=" + rstGuiRemCliNotRetAutSri.getInt("co_emp") + "";
                            strSQL+=" AND co_loc=" + rstGuiRemCliNotRetAutSri.getInt("co_loc") + "";
                            strSQL+=" AND co_tipDoc=" + rstGuiRemCliNotRetAutSri.getInt("co_tipDoc") + "";
                            strSQL+=" AND co_doc=" + rstGuiRemCliNotRetAutSri.getInt("co_doc") + "";
                            strSQL+=";";
                            stmGuiRemCliNotRetAutSri_estImp.executeUpdate(strSQL);
                            //con.commit();

                            stmGuiRemCliNotRetAutSri_estImp.close();
                            stmGuiRemCliNotRetAutSri_estImp=null;


                        }catch(java.sql.SQLException e){
                            blnRes=false;
                            con.rollback();
                        }
                
                        
                    }
                    catch (Exception e){
                        blnRes=false;
                        //conGuiRemCliNotRetAutSri_estImp.rollback();
                        e.printStackTrace();
                    }       
                    
                }
                //conGuiRemCliNotRetAutSri.close();
                conGuiRemCliNotRetAutSri=null;
                System.out.println("jjj");
                stmGuiRemCliNotRetAutSri.close();
                stmGuiRemCliNotRetAutSri=null;
                rstGuiRemCliNotRetAutSri.close();
                rstGuiRemCliNotRetAutSri=null;
                
                //conGuiRemCliNotRetAutSri_estImp.close();
                conGuiRemCliNotRetAutSri_estImp=null;
            
            }
        }
        catch (Exception e){ 
            blnRes=false;
            e.printStackTrace();
        }
        return blnRes;
    }
    
    
    /**
     * Busca y Obtiene el servicio de impresion enviado por parametro.
     * @param pservices arreglo de servicios de impresion de la maquina cliente.
     * @param p nombre del servicio de impresion a imprimir.
     * @return int con el indice del arreglo donde se encontro el servicio de impresion.
     */
    public int traeIndice(PrintService[] pservices, String p){
        int ind=0;

        for (int i = 0; i < pservices.length; i++) {
                System.out.println("printer "+pservices[i].getName());
                if (pservices[i].getName().equals(p)) {
                        System.out.println("LA IMPRESORA QUE PASA..."+pservices[i].getName());
                        ind=i;
                        break;
                }
        }
        return ind;
    }//fin de traeInd 
    
    
    /**
     * Obtiene el directorio del Sistema y sirve para encontrar la ruta de los reportes de Guia y Despacho
     * @return String con la ruta de Acceso.
     */
    public String getDirectorioSistema()
    {
        String strRes="";
        try
        {
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            /*System.out.println("primera ruta: "+urlArc);*/
            //URL urlArc=this.getClass().getResource("Librerias\\ZafUtil");
            System.out.println("primera ruta: "+urlArc);
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Zafiro"));
                if (strRes.indexOf("file:")!=-1)
                    strRes=strRes.substring(5);
            }
        }
        catch (Exception e)
        {
            strRes=null;
        }
        return strRes;
    }  
    

    /**
     * Imprime la orden de despacho.
     * @param conn conexion de acceso a datos a imprimir.
     * @param intCodEmp codigo de la empresa de la orden de despacho a imprimir.
     * @param intCodLoc codigo del local de la orden de despacho a imprimir.
     * @param intCodTipDoc codigo del tipo de documento de la orden de despacho a imprimir.
     * @param intCodDoc codigo del documento de la orden de despacho a imprimir.
     * @return boolean retorna true si no hubo problemas al imprimir, caso contrario false.
     */
    public boolean impresionGuiaRemAutBod2(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, String rtaRpte,String nomImp ){
      String DIRECCION_REPORTE_GUIA="";
      String strDirSis="";
      String strRutSubRpt="";
      try{
        if(conn!=null){
           strDirSis= getDirectorioSistema();
           DIRECCION_REPORTE_GUIA=strDirSis+rtaRpte;
           //DIRECCION_REPORTE_GUIA="D:\\Users\\sistemas5\\Documents\\zafirosvn"+EnumServ01Tuval.RUTAREPORTE2.getValor();
            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));

            //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
            System.out.println("Normal 2 Ruta Reporte OD ->  "+DIRECCION_REPORTE_GUIA );

            Map parameters = new HashMap();
            parameters.put("co_emp", new Integer(intCodEmp) );
            parameters.put("co_loc", new Integer(intCodLoc) );
            parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
            parameters.put("co_doc",  new Integer(intCodDoc) );

            parameters.put("SUBREPORT_DIR", strRutSubRpt );
            
            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
            objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);

            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( nomImp , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
            objPriReqAttSet=null;            

            /*
            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
            byte[] bytArrRep=JasperExportManager.exportReportToPdf(reportGuiaRem);
            
            JasperViewer.viewReport(reportGuiaRem);

            PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
            aset.add(MediaSizeName.ISO_A4);
            aset.add(Sides.ONE_SIDED);

            nomImp="EPSON_LX-300_USB_1";
            PrintService[] pservices=PrinterJob.lookupPrintServices();
            if (pservices.length>0) {
                //int indice=traeIndice(pservices, "\\\\http://172.16.8.241:631\\sistemas_inmaconsa");//Busca la impresora entre los servicios disponibles
                int indice=traeIndice(pservices, nomImp);
                DocPrintJob pj = pservices[indice].createPrintJob();
                System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                Doc doc=new SimpleDoc(bytArrRep, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                //pj.print(doc, aset);
            }   */                      
        }
      }catch (JRException e) {
          e.printStackTrace();
      }catch (Exception e) {
          e.printStackTrace();
      }
     return true;
    }    
    
    
    
    /*public static void main(String[] args){
        ZafImpGuiRem impRem=new ZafImpGuiRem();
        //impRem.imprimirGuiRemCliNotRet_autSRI(1, 4, 231, 42);        
        String strStrConnDb="jdbc:postgresql://172.16.8.7:5432/Zafiro2006_18_Feb_2015";                             
        ZafJCEAlgAES objJCEAlgAES=new ZafJCEAlgAES();
        String strUsrConnDb= objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", "2C055DFEB76E2FDDE5645B646BBCE417");
        String strClaConnDb= objJCEAlgAES.desencriptar("C8F9AA5FC934A4FA4A2B73A84DD84DA3", "400BB4380E66F5E3B606EE42C349C28B", "DB70AA4FD4BF789A079C083E3F9B4156");
        Connection conGuiRemCliNotRetAutSri=null;
        try{
            conGuiRemCliNotRetAutSri=DriverManager.getConnection(strStrConnDb, strUsrConnDb, strClaConnDb);
            //impRem.impresionGuiaRemAutBod2(conGuiRemCliNotRetAutSri, 1, 4, 102, 37383);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }*/
    

    /**
     * Método que imprime las ordenes de Despacho por transferencias (IMPORTACIONES).
     * @param con1 Conexion acceso a la base de datos.
     * @param intCodEmp_Imp Codigo de la empresa importaciones.
     * @param intCodLoc_Imp Codigo del local de importaciones.
     * @param intCodEmp Codigo de la empresa OD.
     * @param intCodLoc Codigo del local de la OD.
     * @param intCodTipDoc Codigo del tipo de documento de la OD.
     * @param intCodDoc Codigo del documento de la OD.
     * @return Boolean 
     */    
    public boolean impresionOrdDesImp(Connection con1, int intCodEmp_Imp, int intCodLoc_Imp, /*int intCodEmp, int intCodLoc,*/ int intCodTipDoc, int intCodDoc){
        String DIRECCION_REPORTE_GUIA="";
        String strDirSis="";
//        String strRutSubRpt="";
        String strRepUtiEmp="";
        String strNomPrn="";
        String strSQLRep="";
        String strSQLSubRep="";
        try{
            if(con1!=null){
                strDirSis= getDirectorioSistema();
                if(intCodEmp_Imp==1){
                    if(intCodLoc_Imp==6){
                        //strRepUtiEmp=RUTAREPORTEODIMPTTUVAL;
                        strRepUtiEmp=RUTAREPORTEODINMTUVAL;
                        strNomPrn=NOMBREIMPRESORAODTUVALTRANS;
                    }

                }
                else if(intCodEmp_Imp==2){
                    if(intCodLoc_Imp==9){
                        //strRepUtiEmp=RUTAREPORTEODIMPTCASUIO;
                        strRepUtiEmp=RUTAREPORTEODINMCASUIO;
                        strNomPrn=NOMBREIMPRESORAODCASUIOTRANS;
                    }
                }
                else if(intCodEmp_Imp==4){
                    if(intCodLoc_Imp==4){
                        //strRepUtiEmp=RUTAREPORTEODIMPTDIM;
                        strRepUtiEmp=RUTAREPORTEODINMDIM;
                        strNomPrn=NOMBREIMPRESORAODDIMTRANS;
                    }
                }

/*
                strSQLRep="";
                strSQLRep+="SELECT d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc, d1.feimpguia, d1.fe_ing, d1.obs2,";
                strSQLRep+=" d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem, d1.tx_ptopar, ";
                strSQLRep+=" d1.empresa, d1.tx_telclides, d1.tx_rucclides, d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, ";
                strSQLRep+=" d1.co_clides, d1.tx_nomven, d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar,";
                strSQLRep+=" d1.cuidad, d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil,";
                strSQLRep+=" d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                strSQLRep+=" FROM ( SELECT";
                strSQLRep+="        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc,";
                strSQLRep+="        CURRENT_TIMESTAMP as feimpguia, b1.fe_ing, trim(b1.tx_obs2) as obs2,";
                strSQLRep+="        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.tx_datdocoriguirem,";
                strSQLRep+="        b1.tx_ptopar, b1.tx_telclides, b1.tx_rucclides,";
                strSQLRep+="        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                strSQLRep+="        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar,";
                strSQLRep+="        b1.tx_ciuclides as cuidad,";
                strSQLRep+="        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                strSQLRep+="        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                strSQLRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLRep+=" 	) as nd_kgr,";
                strSQLRep+="        (";
                strSQLRep+=" 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                strSQLRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLRep+=" 	) as nd_kil";
                strSQLRep+=" 	, c3.co_emp AS co_empIngImp, c3.co_loc AS co_locIngImp, c3.co_tipDoc AS co_tipDocIngImp, c3.co_doc AS co_docIngImp, c3.tx_numdoc2 AS tx_numIngImp";
                strSQLRep+="        FROM (tbm_cabguirem as b1 INNER JOIN tbm_detGuiRem AS b2 ON b1.co_emp=b2.co_emp AND b1.co_loc=b2.co_loc AND b1.co_tipDoc=b2.co_tipDoc AND b1.co_doc=b2.co_doc";
                strSQLRep+=" 			INNER JOIN tbm_detMovInv AS b3 ON b2.co_empRel=b3.co_emp AND b2.co_locRel=b3.co_loc AND b2.co_tipDocRel=b3.co_tipDoc AND b2.co_docRel=b3.co_doc AND b2.co_regRel=b3.co_reg";
                strSQLRep+=" 			INNER JOIN tbm_cabMovInv AS c1 ON b3.co_Emp=c1.co_emp AND b3.co_loc=c1.co_loc AND b3.co_tipDoc=c1.co_tipDoc AND b3.co_doc=c1.co_doc";
                strSQLRep+=" 			INNER JOIN tbr_cabMovInv AS c2 ON c1.co_emp=c2.co_emp AND c1.co_loc=c2.co_loc AND c1.co_tipDoc=c2.co_tipDoc AND c1.co_doc=c2.co_doc";
                strSQLRep+=" 			INNER JOIN tbm_cabMovInv AS c3 ON c2.co_empRel=c3.co_emp AND c2.co_locRel=c3.co_loc AND c2.co_tipDocRel=c3.co_tipDoc AND c2.co_docRel=c3.co_doc AND c3.co_tipDoc=14";
                strSQLRep+=" 	    )";
                strSQLRep+="        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                strSQLRep+="        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                strSQLRep+="        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                strSQLRep+="        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                strSQLRep+="        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                strSQLRep+="        WHERE b1.co_emp=" + intCodEmp_Imp + "";
                strSQLRep+="        and b1.co_loc=" + intCodLoc_Imp + "";
                strSQLRep+="        and b1.co_tipdoc=" + intCodTipDoc + "";
                strSQLRep+="        and b1.co_doc=" + intCodDoc + "";
                strSQLRep+="     ) as d1";
                strSQLRep+=" GROUP BY d1.co_emp, d1.co_loc, d1.co_tipdoc, d1.co_doc,";
                strSQLRep+=" d1.feimpguia, d1.fe_ing, d1.obs2,";
                strSQLRep+=" d1.tx_numped, d1.tx_nom, d1.tx_descor, d1.fe_ing, d1.tx_datdocoriguirem,";
                strSQLRep+=" d1.tx_ptopar, d1.empresa, d1.tx_telclides, d1.tx_rucclides,";
                strSQLRep+=" d1.ne_numorddes, d1.fe_doc, d1.tx_nomclides, d1.co_clides, d1.tx_nomven,";
                strSQLRep+=" d1.tx_dirclides, d1.tx_vehret, d1.tx_choret, d1.tx_deslar, d1.cuidad,";
                strSQLRep+=" d1.contri1, d1.empresa, d1.nd_kgr, d1.nd_kil, d1.co_empIngImp, d1.co_locIngImp, d1.co_tipDocIngImp, d1.co_docIngImp, d1.tx_numIngImp";
                System.out.println("strSQLRep: " + strSQLRep);

                strSQLSubRep="";
                strSQLSubRep+="select *";
                strSQLSubRep+=" from ( select";
                strSQLSubRep+="        b1.co_emp, b1.co_loc, b1.co_tipdoc, b1.co_doc, b2.co_reg,";
                strSQLSubRep+="        current_timestamp as feimpguia, b1.fe_ing, b2.tx_obs1, trim(b1.tx_obs2) as obs2,";
                strSQLSubRep+="        b1.tx_numped, b8.tx_nom, b9.tx_descor, b1.fe_ing, b1.tx_datdocoriguirem,";
                strSQLSubRep+="        b1.tx_ptopar, b8.tx_nom  as empresa, b1.tx_telclides, b1.tx_rucclides,";
                strSQLSubRep+="        b1.ne_numorddes, b1.fe_doc, b1.tx_nomclides, b1.co_clides, b1.tx_nomven,";
                strSQLSubRep+="        b1.tx_dirclides, b1.tx_vehret, b1.tx_choret, b4.tx_deslar, b2.tx_codalt,";
                strSQLSubRep+="        b2.tx_nomitm, abs(b2.nd_can) AS nd_can, b2.tx_unimed, b1.tx_ciuclides as cuidad,";
                strSQLSubRep+="        b8.tx_desconesp as contri1, b8.tx_nom  as empresa,";
                strSQLSubRep+="        (	select sum( (inv.nd_pesitmkgr*abs(x.nd_can)) ) as kgramo";
                strSQLSubRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLSubRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLSubRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLSubRep+=" 	) as nd_kgr,";
                strSQLSubRep+="        (";
                strSQLSubRep+=" 		select sum( (((inv.nd_pesitmkgr*abs(x.nd_can))*2.2)/100)  ) as kilo";
                strSQLSubRep+=" 		from tbm_detguirem as x inner join tbm_inv as inv";
                strSQLSubRep+=" 		on(x.co_emp=inv.co_emp and x.co_itm=inv.co_itm)";
                strSQLSubRep+=" 		WHERE x.co_emp=b1.co_emp and x.co_loc=b1.co_loc  and x.co_tipdoc=b1.co_tipdoc and x.co_doc=b1.co_doc";
                strSQLSubRep+=" 	) as nd_kil";
                strSQLSubRep+="        FROM tbm_cabguirem as b1 INNER JOIN tbm_detguirem as b2";
                strSQLSubRep+="        ON(b1.co_emp=b2.co_emp and b1.co_loc=b2.co_loc and b1.co_tipdoc=b2.co_tipdoc and b1.co_doc=b2.co_doc)";
                strSQLSubRep+="        inner join tbm_detmovinv as b3";
                strSQLSubRep+="        on (b3.co_emp=b2.co_emprel and b3.co_loc=b2.co_locrel and b3.co_tipdoc=b2.co_tipdocrel and b3.co_doc=b2.co_docrel and b3.co_reg=b2.co_regrel )";
                strSQLSubRep+="        LEFT OUTER JOIN tbm_var as b4 ON(b1.co_forret = b4.co_reg )";
                strSQLSubRep+="        inner join tbm_inv as b5 on(b2.co_emp=b5.co_emp and b2.co_itm=b5.co_itm)";
                strSQLSubRep+="        inner JOIN tbm_loc as b6 ON( b1.co_emp = b6.co_emp and  b1.co_loc=b6.co_loc)";
                strSQLSubRep+="        inner JOIN tbm_ciu as b7 ON( b6.co_ciu = b7.co_ciu)";
                strSQLSubRep+="        inner JOIN tbm_emp as b8 ON( b8.co_emp = b1.co_emp)";
                strSQLSubRep+="        inner JOIN tbm_cabtipdoc as b9 ON( b9.co_emp = b1.co_emp and b9.co_loc=b1.co_loc and b9.co_tipdoc=b1.co_tipdoc)";
                strSQLSubRep+="        WHERE b1.co_emp=" + intCodEmp_Imp + "";
                strSQLSubRep+="        and b1.co_loc=" + intCodLoc_Imp + "";
                strSQLSubRep+="        and b1.co_tipdoc=" + intCodTipDoc + "";
                strSQLSubRep+="        and b1.co_doc=" + intCodDoc + "";
                strSQLSubRep+="     ) as x";
                strSQLSubRep+=" order by co_reg";
                System.out.println("strSQLSubRep: " + strSQLSubRep);*/

                DIRECCION_REPORTE_GUIA=strDirSis+strRepUtiEmp;
                String strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));
                Map parameters = new HashMap();
                parameters.put("co_emp", new Integer(intCodEmp_Imp) );
                parameters.put("co_loc", new Integer(intCodLoc_Imp) );
                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
                parameters.put("co_doc",  new Integer(intCodDoc) );
                //parameters.put("strSQLRep", strSQLRep);
                //parameters.put("strSQLSubRep", strSQLSubRep);
                //parameters.put("SUBREPORT_DIR", (strDirSis + "/Reportes/Importaciones/ZafImp06/"));                
                parameters.put("SUBREPORT_DIR", strRutSubRpt);                
                
                
                
                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con1);
                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomPrn , null);
                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
                printServiceAttributeSet.add(printerName);
                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
                objJRPSerExp.exportReport();
                objPriReqAttSet=null;
                
                


//                if(System.getProperty("os.name").equals("Linux")){

               /* JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con1); 
                byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
                JasperViewer.viewReport(reportGuiaRem);

                PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
                aset.add(MediaSizeName.ISO_A4);
                        
                aset.add(Sides.ONE_SIDED);
                       

                PrintService[] pservices=PrinterJob.lookupPrintServices();
                if (pservices.length>0) {
                    int indice=traeIndice(pservices, strNomPrn);
                    DocPrintJob pj = pservices[indice].createPrintJob();
                    System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                    Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                    //pj.print(doc, aset);
                }
                */
                
                
//                }
//                else{
//                    JasperPrint report = JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters, con1);
//                    JasperManager.printReport(report,false);
                    ////JasperViewer.viewReport(report, false);
                   // System.out.println("Se imprimio la OD");
//                }
            }
        }
        catch (JRException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }     
    
    /**
     * Metodo que realiza la impresion de OD en transferencia para INMACONSA.
     * @param con Conexion de acceso a Datos.
     * @param intCodEmp codigo de la empresa.
     * @param intCodLoc codigo del local.
     * @param intCodTipDoc codigo del tipo de documento.
     * @param intCodDoc codigo del documento.
     * @param intCodEmpFacCli_FacRel codigo de empresa CLIENTE RETIRA.
     * @return Boolean 
     */
    public boolean impresionOrdDesTransInm( Connection con, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc, int intCodEmpFacCli_FacRel){
        Boolean booRetImp=true;
        String strRutaReporte="";
        String strNombreImpresora="";
        String strDirSis="";
        String DIRECCION_REPORTE_GUIA;
        String strRutSubRpt;
        try{
        strDirSis= getDirectorioSistema();
        if(intCodEmp==intCodEmpFacCli_FacRel){
            if(intCodEmp==CODEMPINMTUV){
                strRutaReporte=RUTAREPORTEODINMTUVAL;
                strNombreImpresora=NOMBREIMPRESORAODINMTUVAL;
            }else if(intCodEmp==CODEMPINMCASUIO){
                strRutaReporte=RUTAREPORTEODINMCASUIO;
                strNombreImpresora=NOMBREIMPRESORAODINMCASUIO;
            }else if(intCodEmp==CODEMPINMDIM){
                strRutaReporte=RUTAREPORTEODINMDIM;
                strNombreImpresora=NOMBREIMPRESORAODINMDIM;
            }
            
            DIRECCION_REPORTE_GUIA=strDirSis+strRutaReporte;
            strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_01.jasper"));
            
            Map parameters = new HashMap();
            parameters.put("co_emp", new Integer(intCodEmp) );
            parameters.put("co_loc", new Integer(intCodLoc) );
            parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
            parameters.put("co_doc",  new Integer(intCodDoc) );                
            parameters.put("SUBREPORT_DIR", strRutSubRpt );                  
            
            
            javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
            //objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
            objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH));//sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
            objPriReqAttSet.add(OrientationRequested.PORTRAIT);                
            JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con);
            //JasperPrint reportGuiaRem =JasperFillManager.fillReport(strRutSubRpt, parameters,  conn);
            //javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
            javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNombreImpresora , null);
            javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
            printServiceAttributeSet.add(printerName);
            net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            objJRPSerExp.exportReport();
            objPriReqAttSet=null;            
            
            /*
            JasperPrint rptODInm =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  con); 
            byte[] pdfFile=JasperExportManager.exportReportToPdf(rptODInm);
            JasperViewer.viewReport(rptODInm);

            PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
            aset.add(MediaSizeName.ISO_A4);

            aset.add(Sides.ONE_SIDED);*/

            //  PrintService[] pservices=PrinterJob.lookupPrintServices();
            //  if (pservices.length>0) {
            //  int indice=traeIndice(pservices, strNombreImpresora);
            //  DocPrintJob pj = pservices[indice].createPrintJob();
            //  System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
            //  Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
            //  pj.print(doc, aset);
            // }            
            
        }
        }catch(JRException ex){
            ex.printStackTrace();
            booRetImp=false;
        }catch(Exception ex){
            ex.printStackTrace();
            booRetImp=false;
        }
        
        return booRetImp;
    }    
    
}

