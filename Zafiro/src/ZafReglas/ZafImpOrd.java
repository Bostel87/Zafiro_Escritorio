/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ZafReglas;

import java.awt.print.PrinterJob;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.Sides;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.view.JasperViewer;



public class ZafImpOrd {
    private ZafClassImpGuia_01 objEnvMail;
    private String[] strRutaRpt2;
    private String strNomImpBod2;
    
    //arreglar getdirectorio.

    public ZafImpOrd() {
    }
    
    
    
/**
 * <h2>impresionGuiaRemAutBod2</h2>
 * Metodo de impresión de la guía de remisión, después de la confirmación de egreso de mercadería
 */    
public boolean impresionGuiaRemAutBod2(java.sql.Connection conn, int intCodEmp, int intCodLoc, int intCodTipDoc, int intCodDoc,int codbod){
  String DIRECCION_REPORTE_GUIA="";
  String strDirSis="";
  String strRutSubRpt="";
  int cobodgru;
  
  ZafImpGuiRem imp=new ZafImpGuiRem();
  try{
    if(conn!=null){
     
        ZafGuiRemDAO objGuiRem=new ZafGuiRemDAO();
        strDirSis=getDirectorioSistemaImpOrd();
        cobodgru=objGuiRem.obtenerBodGru(conn, intCodEmp, codbod);
        ZafGenGuiRem objGenGuiRem=new ZafGenGuiRem();
        strRutaRpt2=objGenGuiRem.obtenerRptImpOD(cobodgru);
        
        DIRECCION_REPORTE_GUIA=strDirSis+strRutaRpt2[0];
        strNomImpBod2=strRutaRpt2[1];
      
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
        
        
        javax.print.attribute.standard.PrinterName printerName=new javax.print.attribute.standard.PrinterName( strNomImpBod2 , null);
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
        byte[] pdfFile=JasperExportManager.exportReportToPdf(reportGuiaRem);
        JasperViewer.viewReport(reportGuiaRem);

        PrintRequestAttributeSet aset=new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A4);

        aset.add(Sides.ONE_SIDED);
        strNomImpBod2="\\\\http://172.16.8.4:631\\imp_sistemas";
        //aset.add(new PrinterName(strNomImpBod2, Locale.US));
        PrintService[] pservices=PrinterJob.lookupPrintServices();
        if (pservices.length>0) {
            int indice=imp.traeIndice(pservices, strNomImpBod2);
            DocPrintJob pj = pservices[indice].createPrintJob();
            System.out.println("la impresora seleccionada es: "+pservices[indice].getName());
            Doc doc=new SimpleDoc(pdfFile, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
                //pj.print(doc, aset);
        }*/
        
    }
  }catch (JRException e) {  
      e.printStackTrace();
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }catch (Exception e) {  
      e.printStackTrace();
      objEnvMail.enviarCorreo(" Error el imprimir en bodega 2.."+e.toString() ); 
  }
 return true;
}
    
    
    
    
    private String getDirectorioSistemaImpOrd()
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
    
}
