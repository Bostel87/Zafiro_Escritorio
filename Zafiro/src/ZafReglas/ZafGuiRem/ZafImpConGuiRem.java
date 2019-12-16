/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas.ZafGuiRem;

import Librerias.ZafJCE.ZafJCEAlgAES;
import ZafReglas.ZafClassImpGuia_01;
import ZafReglas.ZafGuiRemDAO;
import ZafReglas.ZafImpGuiRem;
import java.awt.print.PrinterJob;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.ResultSet;
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
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author postgres
 */
public class ZafImpConGuiRem {
    
    private ZafClassImpGuia_01 objEnvMail;
    private String[] strRutaRpt2;
    private String strNomImpBod2;

    public ZafImpConGuiRem() {
        
    }

    
////<editor-fold defaultstate="collapsed" desc="CODIGO EN VEREMOS">
//    public boolean impresionGuiaRemCon(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,int codbod){
//        String DIRECCION_REPORTE_GUIA="";
//        String strDirSis="";
//        String strRutSubRpt="";
//        int cobodgru;
//        
//        ZafImpGuiRem imp=new ZafImpGuiRem();
//        try{
//            if(conn!=null){
//                
//                ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
//                strDirSis=getDirectorioSistemaGuiRem();
//                cobodgru=objGuiRem.obtenerBodGru(conn, intCodEmp, codbod);
//                //ZafReglas.ZafGenGuiRem objGenGuiRem=new ZafReglas.ZafGenGuiRem();
//                //strRutaRpt2=objGenGuiRem.obtenerRptImpOD(cobodgru);
//                strRutaRpt2=obtenerRptImpGuiRem(cobodgru);
//                
//                DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
//                strNomImpBod2=strRutaRpt2[1];
//                
//                strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_08.jasper"));
//                
//                //System.out.println("Ruta Reporte OD ->  "+strRutSubRpt );
//                System.out.println("Normal 2 Ruta Reporte guirem ->  "+DIRECCION_REPORTE_GUIA );
//                
//                Map parameters = new HashMap();
//                parameters.put("co_emp", new Integer(intCodEmp) );
//                parameters.put("co_loc", new Integer(intCodLoc) );
//                parameters.put("co_tipdoc", new Integer(intCodTipDoc) );
//                parameters.put("co_doc",  new Integer(intCodDoc) );
//                
//                parameters.put("SUBREPORT_DIR", strRutSubRpt );
//                
//                javax.print.attribute.PrintRequestAttributeSet objPriReqAttSet=new javax.print.attribute.HashPrintRequestAttributeSet();
//                objPriReqAttSet.add(javax.print.attribute.standard.MediaSizeName.ISO_A4);
//                
//                JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, parameters,  conn);
//                
//                /*
//                javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
//                javax.print.attribute.PrintServiceAttributeSet printServiceAttributeSet=new javax.print.attribute.HashPrintServiceAttributeSet();
//                printServiceAttributeSet.add(printerName);
//                net.sf.jasperreports.engine.export.JRPrintServiceExporter objJRPSerExp=new net.sf.jasperreports.engine.export.JRPrintServiceExporter();
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.JRExporterParameter.JASPER_PRINT, reportGuiaRem);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, objPriReqAttSet);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//                objJRPSerExp.setParameter(net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//                objJRPSerExp.exportReport();
//                objPriReqAttSet=null;
//                */
//                
//                byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
//                JasperViewer.viewReport(reportGuiaRem);
//                
//                PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
//                aset.add(MediaSizeName.ISO_A4);
//                
//                aset.add(Sides.ONE_SIDED);
//                strNomImpBod2="imp_sistemas";
//                PrintService[] pservices=PrinterJob.lookupPrintServices();
//                if (pservices.length>0) {
//                    int indice=imp.traeIndice(pservices, strNomImpBod2);
//                    DocPrintJob pj = pservices[indice].createPrintJob();
//                    System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
//                    Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
//                    pj.print(doc, aset);
//                }
//                
//            }
//        }catch (JRException e) {
//            objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() );
//        }catch (Exception e) {
//            objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() );
//        }
//        return true;
//    }
////</editor-fold>
    
    
    private String getDirectorioSistemaGuiRem()
    {
        String strRes="";
        try
        {
            URL urlArc=this.getClass().getResource("/Librerias/ZafUtil/ZafUtil.class");
            if (urlArc!=null)
            {
                //Utilizar "decode" porque los espacios en blanco que puede incluir la ruta del archivo son
                //reemplazados con "%20" por el método "getPath()" y eso trae problemas al usar "FileInputStream".
                strRes=URLDecoder.decode(urlArc.getPath(),"UTF-8");
                strRes=strRes.substring(0, strRes.lastIndexOf("/Zafiro"));
                if (strRes.indexOf("file:")!=-1){
                    strRes=strRes.substring(5);
                }
                
            }
        }
        catch (Exception e)
        {
            strRes=null;
            objEnvMail.enviarCorreo(" "+e.toString() );
        }
        return strRes;
    }    
    
    
    private String[] obtenerRptImpGuiRem(int intCodBodGrp){
        String strRtaRptOD="",strNomImp="",strEmpNumGui="",strLocNumGui="";
        String[] strArrRet=new String[4];
        if(intCodBodGrp==1){//TUVAL
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_califormia";  
            strEmpNumGui="1";
            strLocNumGui="4";
            
        }else if(intCodBodGrp==2){//DIMULTI
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Dimulti/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_dimulti";
            strEmpNumGui="4";
            strLocNumGui="3";            
            
        }else if(intCodBodGrp==3){//QUITO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Quito/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_quito";    
            strEmpNumGui="2";
            strLocNumGui="1";                        
            
        }else if(intCodBodGrp==5){//MANTA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Manta/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_manta";   
            strEmpNumGui="2";
            strLocNumGui="4";                                    
            
        }else if(intCodBodGrp==6){//IMPORTACIONES
            strRtaRptOD="/Reportes/Compras/ZafCom23/Tuval/ZafRptCom23_01.jasperXXX";
            strNomImp="PrintBodega2XXX";   
            strEmpNumGui="1";
            strLocNumGui="6";
            
            
        }else if(intCodBodGrp==11){//STO DOMINGO
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/SantoDomingo/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_stodgo";      
            strEmpNumGui="2";
            strLocNumGui="6";            
            
        }else if(intCodBodGrp==15){//INMACONSA
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_inmaconsa";   
            strEmpNumGui="1";
            strLocNumGui="10";
            
        }else if(intCodBodGrp==28){//CUENCA
            //strRtaRptOD="/Reportes/Compras/ZafCom23/Castek/Cuenca/ZafRptCom23_08.jasper";
            strRtaRptOD="/Reportes/Compras/ZafCom23/ZafRptCom23_08.jasper";
            strNomImp="guias_cuenca";   
            strEmpNumGui="2";
            strLocNumGui="10";
            
        
        }
        strArrRet[0]=strRtaRptOD;
        strArrRet[1]=strNomImp;
        strArrRet[2]=strEmpNumGui;
        strArrRet[3]=strLocNumGui;        
        
        return strArrRet;
    }
    
//************************************************************************************************************************************************************    
    
    
   public boolean imprimirGuiRemCliNotRet_autSRI(Connection con, int intCodEmpGui, int intCodLocGui, int intCodTipGui, int intCodDocGui, int intCodBod){
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
        
        
        ZafImpGuiRem imp=new ZafImpGuiRem();
        
          String strRutSubRpt="";
          int cobodgru;
        
        
        
        try{
            ZafJCEAlgAES objJCEAlgAES=new ZafJCEAlgAES();
            
            if( (con!=null) ){
                
                ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
                strDirSis=getDirectorioSistemaGuiRem();
                //cobodgru=objGuiRem.obtenerBodGru(con, intCodEmpGui, intCodBod);
                //ZafReglas.ZafGenGuiRem objGenGuiRem=new ZafReglas.ZafGenGuiRem();
                //strRutaRpt2=objGenGuiRem.obtenerRptImpOD(cobodgru);
                strRutaRpt2=obtenerRptImpGuiRem(intCodBod);
                
                DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
                strNomImpBod2=strRutaRpt2[1];
      
                strRutSubRpt=DIRECCION_REPORTE_GUIA.substring(0, DIRECCION_REPORTE_GUIA.lastIndexOf("ZafRptCom23_08.jasper"));
                
                
                //strDirSis=getDirectorioSistemaGuiRem();        
                //DIRECCION_REPORTE_GUIA=strDirSis+strRtaRpt;
                System.out.println("imprimirGuiRemCliNotRet-Ruta Reporte GR ->  "+DIRECCION_REPORTE_GUIA);
                
                //stmGuiRemCliNotRetAutSri=con.createStatement();
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
                strSQL+=" WHERE /*( a6.co_empGrp=0 AND a6.co_bodGrp = " + intCodBod + "  )  AND*/";
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

                        /*JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
                        JasperViewer.viewReport(reportGuiaRem);

                        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
                        aset.add(MediaSizeName.ISO_A4);
                        
                        if(intCodBod==1)
                           aset.add(new Copies(3));                        
                        aset.add(Sides.ONE_SIDED);*/
                        
                        

                        PrintRequestAttributeSet objPriReqAttSet=new HashPrintRequestAttributeSet(); 
                        //objPriReqAttSet.add(MediaSizeName.ISO_A4); 
                        //sirve para el area de impresion, si NO se lo coloca la impresion de las Guias sale despues de 3 cm hacia abajo aprox. no presentando la informacion de arriba del reporte.
                        objPriReqAttSet.add(new MediaPrintableArea(0f, 0f, 8.5f, 11f, Size2DSyntax.INCH) ); 
                        objPriReqAttSet.add(OrientationRequested.PORTRAIT);
                        objPriReqAttSet.add(new Copies(3));
                        
                        
                        JasperPrint reportGuiaRem =JasperFillManager.fillReport(DIRECCION_REPORTE_GUIA, mapPar,  con); 
                        PrinterName printerName=new PrinterName( strNomImpBod2 , null); 
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
                        
                        
                        
                       

                        /*PrintService[] pservices=PrinterJob.lookupPrintServices();
                        if (pservices.length>0) {
                            int indice=imp.traeIndice(pservices, strNomImpBod2);
                            DocPrintJob pj = pservices[indice].createPrintJob();
                            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
                            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                            //pj.print(doc, aset);
                        } */                        
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
    
    
    
}//fin de clase

