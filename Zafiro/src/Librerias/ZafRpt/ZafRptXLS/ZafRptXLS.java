/*
 * POI.java
 *
 * Created on 5 de agosto de 2005, 11:30
 */

package Librerias.ZafRpt.ZafRptXLS;
import Librerias.ZafParSis.ZafParSis;
//Impresion POI
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.hssf.util.HSSFColor.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import java.io.FileOutputStream;
import java.io.FileInputStream;

import Librerias.ZafUtil.ZafUtil;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author  JSALAZAR
 */

/**
 * Metodo para impresion de documentos y reportes
 * Documento Contienen Cabecera, Detalle y Pie
 * Reporte Contiene Cabecera, Cuerpo y Pie
  */
public class ZafRptXLS {
    ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Connection con;
    private Statement stm;
    private ResultSet rst,rstCab,rstDet;    
    private String strSQL,strNameFile;
    private int intTipoDoc,  intNumDoc;
    POIFSFileSystem fs;
    HSSFWorkbook wb ;
    HSSFSheet sheet;   
    
    //Constantes style texto
    private final String FONT_GARAMOND = "Garamond";
    private final String FONT_ARIAL = "Arial";
    private final String FONT_COURIER_NEW = "Courier New";
    private final String FONT_TIMES_NEW_ROMAN = "Times New Roman";
    private final String FONT_MS_SANS_SERIF = "Microsoft Sans Serif Regular";
    private final short SIZE_8 = 8;
    private final short SIZE_9 = 9;
    private final short SIZE_10 = 10;
    private final short SIZE_11 = 11;
    private final short SIZE_12 = 12;
    private final short COLOR_RED = HSSFFont.COLOR_RED;
    private final short COLOR_BLACK = HSSFFont.COLOR_NORMAL;
    private final short NEGRITA = HSSFFont.BOLDWEIGHT_BOLD;
    private final short NORMAL =  HSSFFont.BOLDWEIGHT_NORMAL;
    private final boolean CON_CURSIVA = true;
    private final boolean SIN_CURSIVA = false;
    private final int ENTERO = 1;    
    private final int DOUBLE = 2;
    private final int CADENA = 3;
    private final int FECHA  = 4;
    
    
    private final String VERSION = " v0.1.3";    
    /** Creates a new instance of POI */
    public ZafRptXLS(ZafParSis obj) {
        objZafParSis  = obj; 
        objUti = new ZafUtil();
    }
    /**
     * Metodo que setea el tipo de documento y el numero del documento.
     */
    public boolean GenerarDoc(int intparTipoDoc, int intparNumDoc)
    {
        boolean blnResp = false;
        try
        {
           intTipoDoc = intparTipoDoc;
           intNumDoc = intparNumDoc;
           switch (intTipoDoc)
           {
               case -1: //Cotizaciones en Venta
                   blnResp = FormatoCotizacion();
                   break;
               case -2: //Cotizaciones en Compra
                   blnResp = FormatoCotizacionCompra();
                   break;
               case 1: //Factura
                   if (objZafParSis.getCodigoEmpresa()==3)
                       blnResp = FormatoFacturaNOSITOL();
                   else
                       blnResp = FormatoFactura();
                   break;
               case 2: // O/C
                   blnResp = FormatoCompras();
                   break;
               case 3: // Devolucion Ventas
                   blnResp = FormatoDevolucion();
                   break;
               case 4: // Devolucion Ventas
                   blnResp = FormatoDevolucionCompras();
                   break;
               case 11 :  
                   blnResp = FormatoIE();
                   break;
               case 12 :                 
                   blnResp = FormatoIE();
                   break;
               case 13 :                 
                   blnResp = FormatoIE();
                   break;
               case 14 :                 
                   blnResp = FormatoIE();
                   break;
               case 15 :                 
                   blnResp = FormatoIE();
                   break;
               case 16 :                 
                   blnResp = FormatoIE();
                   break;
               case 17 :                 
                   blnResp = FormatoIE();
                   break;                   
           }
        }catch(Exception Evt){
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);                    
        }
        return blnResp;
    }
        
    private void setCellValue(short rowNum, short colNum, String strValor, int TipoObj, short align)
    {
        HSSFCellStyle style;
        //HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();        
        HSSFRow row;
        HSSFCell cell;        
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        style = wb.createCellStyle();
        style.setAlignment(align);                
        switch (TipoObj)
        {
            case 1: // Numerico sin decimal
                cell.setCellValue(Integer.parseInt(strValor));
                break;
            case 2: // Numerico Decimal
                cell.setCellValue(Double.parseDouble(strValor));
                style.setDataFormat(format.getFormat("###,##0.00"));                
                break;
            case 3: //Cadena
                cell.setCellValue(strValor.toUpperCase());
                break;
            case 4: //Fecha
                cell.setCellValue(strValor);
                //System.out.println(objUti.formatearFecha(objUti.parseDate(strValor,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()));
                break;
        }
        cell.setCellStyle(style);
    }
    private void setCellValue(short rowNum, short colNum, String strValor, int TipoObj, short align,short TipoStyle,boolean blnItalic, short size)
    {   
        HSSFCellStyle style;
        //HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();        
        HSSFRow row;
        HSSFCell cell;        
        // Create a new font and alter it.
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName("Courier New");        
        font.setBoldweight(TipoStyle);
        font.setItalic(blnItalic);

        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        style = wb.createCellStyle();
        style.setAlignment(align);                
        style.setFont(font);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        switch (TipoObj)
        {
            case 1: // Numerico sin decimal
                cell.setCellValue(Integer.parseInt(strValor));
                break;
            case 2: // Numerico Decimal
                cell.setCellValue(Double.parseDouble(strValor));
                style.setDataFormat(format.getFormat("###,##0.00"));                
                break;
            case 3: //Cadena
                cell.setCellValue(strValor.toUpperCase());
                break;
            case 4: //Fecha
                cell.setCellValue(strValor);
                //System.out.println(objUti.formatearFecha(objUti.parseDate(strValor,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()));
                break;
        }
        cell.setCellStyle(style);
    }

    private void setCellValue(short rowNum, short colNum, String strValor, int TipoObj, short align,short TipoStyle,boolean blnItalic,short size,String TipoFont)
    { 
        HSSFCellStyle style;
        //HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();        
        HSSFRow row;
        HSSFCell cell;        
        // Create a new font and alter it.
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(TipoFont);        
        font.setBoldweight(TipoStyle);
        font.setItalic(blnItalic);

        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        style = wb.createCellStyle();
        style.setAlignment(align);                
        style.setFont(font);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        switch (TipoObj)
        {
            case 1: // Numerico sin decimal
                cell.setCellValue(Integer.parseInt(strValor));
                break;
            case 2: // Numerico Decimal
                cell.setCellValue(Double.parseDouble(strValor));
                style.setDataFormat(format.getFormat("###,##0.00"));                
                break;
            case 3: //Cadena
                cell.setCellValue(strValor.toUpperCase());
                break;
            case 4: //Fecha
                cell.setCellValue(strValor);
                //System.out.println(objUti.formatearFecha(objUti.parseDate(strValor,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()));
                break;
        }
        cell.setCellStyle(style);
    }

    private void setCellValueFac(short rowNum, short colNum, String strValor, int TipoObj, short align,short TipoStyle,boolean blnItalic,short size,String TipoFont)
    {   HSSFCellStyle style;
        //HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();        
        HSSFRow row;
        HSSFCell cell;        
        // Create a new font and alter it.
        HSSFFont font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName(TipoFont);        
        font.setBoldweight(TipoStyle);
        font.setItalic(blnItalic);

        row = sheet.createRow(rowNum);
        row.setHeight( (short) 240);
        cell = row.createCell(colNum);
        style = wb.createCellStyle();
        style.setAlignment(align);                
        style.setFont(font);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        switch (TipoObj)
        {
            case 1: // Numerico sin decimal
                cell.setCellValue(Integer.parseInt(strValor));
                break;
            case 2: // Numerico Decimal
                cell.setCellValue(Double.parseDouble(strValor));
                style.setDataFormat(format.getFormat("###,##0.00"));                
                break;
            case 3: //Cadena
                cell.setCellValue(strValor.toUpperCase());
                break;
            case 4: //Fecha
                cell.setCellValue(strValor);
                //System.out.println(objUti.formatearFecha(objUti.parseDate(strValor,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()));
                break;
        }
        cell.setCellStyle(style);
    }
    
    private void setCellValue(short rowNum, short colNum, String strValor, int TipoObj)
    {
        HSSFCellStyle style;
        //HSSFCellStyle cellStyle = wb.createCellStyle();
        HSSFDataFormat format = wb.createDataFormat();        
        HSSFRow row;
        HSSFCell cell;        
        row = sheet.createRow(rowNum);
        cell = row.createCell(colNum);
        style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);            
        switch (TipoObj)
        {
            case 1: // Numerico sin decimal
                cell.setCellValue(Integer.parseInt(strValor));
                break;
            case 2: // Numerico Decimal
                cell.setCellValue(Double.parseDouble(strValor));
                style.setDataFormat(format.getFormat("###,##0.00"));            
                break;
            case 3: //Cadena
                cell.setCellValue(strValor.toUpperCase());
                break;
            case 4: //Fecha
                cell.setCellValue(strValor);
                //System.out.println(objUti.formatearFecha(objUti.parseDate(strValor,"yyyy/MM/dd"), objZafParSis.getFormatoFechaBaseDatos()));
                break;
        }
        //cell.setCellStyle(style);
    }
    private void GenerarFile()
    {
        try
        {
            strNameFile = "C:\\Zafiro\\Reportes\\Doc" + intTipoDoc+ intNumDoc + objUti.getFechaServidor(objZafParSis.getStringConexion(),objZafParSis.getUsuarioBaseDatos(),objZafParSis.getClaveBaseDatos(),objZafParSis.getQueryFechaHoraBaseDatos(),objZafParSis.getFormatoFechaBaseDatos())+new java.util.Random()+".xls" ; 
            FileOutputStream fileOut = new FileOutputStream(strNameFile,true);            
            wb.write(fileOut);            
            //java.io.RandomAccessFile rf= new java.io.RandomAccessFile(strNameFile,"r");
            //rf.writeUTF(fileOut.toString());
            fileOut.close();        
        }catch(Exception e){
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
    }
    private void imprimir(String strCoord)
    {
        try 
        {
            if (wb!=null)
            {
                wb.setPrintArea(0, strCoord);
                //sets the print area for the first sheet
                //Alternatively:
                //wb.setPrintArea(0, 0, 1, 0, 0) is equivalent to using the name reference (See the JavaDocs for more details)  
            }
            Runtime.getRuntime().exec(("C:/Archivos de programa/Microsoft Office/Office/Excel.exe "+ strNameFile));            
        }
            catch (java.io.IOException e) {
            System.err.println("Error: " + e.getMessage());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }

    }
    /**
     * Metodo para formato Cotizacion
     */
    private boolean FormatoCotizacion()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoCotizacion.xls";
        double dblvalor=0;
        java.sql.Statement stmAux;
        java.sql.ResultSet rstAux;
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select co_cot,fe_cot,cotven.co_cli,tx_ide as tx_ruc,cli.tx_nom as tx_nomcli,cli.tx_dir as tx_dircli,cli.tx_tel , cotven.co_ven,usr.tx_nom as tx_nomven,tx_ate,cotven.co_forpag,tx_des as tx_desforpag,nd_sub,nd_poriva,nd_valdes,cotven.tx_obs1,cotven.tx_obs2 ";
               strSQL += " from tbm_cabcotven as cotven left outer join tbm_cli as cli on (cotven.co_emp = cli.co_emp and cotven.co_cli = cli.co_cli) left outer join tbm_cabforpag as forpag on ( cotven.co_emp= forpag.co_emp and cotven.co_forpag = forpag.co_forpag)  left outer join tbm_usr  as usr on (cotven.co_ven = usr.co_usr)" ;
               strSQL += " where cotven.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cotven.co_loc = "+ objZafParSis.getCodigoLocal() +" and cotven.co_cot = "+ intNumDoc;
               //System.out.println (strSQL);
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      
                      setCellValue((short)1,(short)4,rstCab.getString("co_cot"),ENTERO,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_12,FONT_ARIAL);                                                                                        
                      setCellValue((short)3,(short)1,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_CENTER, NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)3,(short)2,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)3,(short)6,(rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli"),CADENA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)1,(short)9,rstCab.getString("fe_cot"),FECHA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                                                                    
                      setCellValue((short)5,(short)1,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                                           
                      setCellValue((short)7,(short)1,(rstCab.getString("tx_tel")==null)?"":rstCab.getString("tx_tel"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                                           
                      setCellValue((short)9,(short)1,(rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                      
                      setCellValue((short)37,(short)1,(rstCab.getString("tx_desforpag")==null)?"":rstCab.getString("tx_desforpag"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                                                                    
                      setCellValue((short)38,(short)1,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)49,(short)1,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                      
                      setCellValue((short)41,(short)1,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                        
                      dblvalor = Double.parseDouble(rstCab.getString("nd_sub"));
                      setCellValue((short)37,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);  
                      dblvalor = dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100);
                      setCellValue((short)38,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);  
                      dblvalor += (Double.parseDouble(rstCab.getString("nd_sub")));
                      setCellValue((short)40,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);  
                      
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detcotven  where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_cot = "+ intNumDoc;
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 11;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            strSQL = "";
                            strSQL = " Select tx_descor from tbm_detcotven as detcot left outer join tbm_inv as inv on (detcot.co_emp = inv.co_emp and detcot.co_itm = inv.co_itm) left outer join tbm_var as var on (inv.co_uni = var.co_reg)" +
                                     " where detcot.co_emp  = " + objZafParSis.getCodigoEmpresa() +" and detcot.co_loc = " + objZafParSis.getCodigoLocal() + " and detcot.co_cot = " + intNumDoc +" and detcot.co_itm = " + rstDet.getString("co_itm") ;
                            stmAux = con.createStatement();
                            rstAux = stmAux.executeQuery(strSQL);
                            if (rstAux.next())
                            {
                                setCellValue((short)intFila,(short)6,(rstAux.getString("tx_descor")==null)?"":rstAux.getString("tx_descor"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                              
                            }
                            rstAux.close();
                            stmAux.close();
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                                                                                                           
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_8,FONT_ARIAL);                                                                          
                            setCellValue((short)intFila,(short)5,rstDet.getString("nd_can"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_8,FONT_ARIAL);                                              
                            setCellValue((short)intFila,(short)7,rstDet.getString("nd_preuni"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_8,FONT_ARIAL);
                            dblvalor = objUti.redondeo(Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_preuni"))*(Double.parseDouble((rstDet.getString("nd_pordes")==null)?"0":rstDet.getString("nd_pordes")) /100),6);
                            setCellValue((short)intFila,(short)8,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_8,FONT_ARIAL);
                            dblvalor = objUti.redondeo(Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_preuni")),6)-dblvalor;
                            setCellValue((short)intFila,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_8,FONT_ARIAL);
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();      
                   imprimir("$A$1:$L$40");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    /**
     * Metodo para formato Cotizacion
     */
    private boolean FormatoCotizacionCompra()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoCotCompra.xls";
        double dblvalor=0;
        java.sql.ResultSet rstItm;
        java.sql.Statement stmItm;  
        Librerias.ZafUtil.ZafCliente_dat objCliente = new Librerias.ZafUtil.ZafCliente_dat(objZafParSis,objZafParSis.getNombreEmpresa());
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select co_cot,fe_cot,cotven.co_prv,tx_ide as tx_ruc,cli.tx_nom as tx_nomcli,cli.tx_dir as tx_dircli,cli.tx_tel , cotven.co_com,usr.tx_nom as tx_nomven,tx_ate,cotven.co_forpag,tx_des as tx_desforpag,nd_sub,nd_poriva,nd_valdes,cotven.tx_obs1,cotven.tx_obs2  ";
               strSQL += " from tbm_cabcotcom as cotven left outer join tbm_cli as cli on (cotven.co_emp = cli.co_emp and cotven.co_prv = cli.co_cli) left outer join tbm_cabforpag as forpag on ( cotven.co_emp= forpag.co_emp and cotven.co_forpag = forpag.co_forpag)  left outer join tbm_usr  as usr on (cotven.co_com = usr.co_usr) " ;
               strSQL += " where cotven.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cotven.co_loc = "+ objZafParSis.getCodigoLocal() +" and cotven.co_cot = "+ intNumDoc;
               System.out.println (strSQL);
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      setCellValue((short)0,(short)0,(objCliente.getNombre()==null?"":objCliente.getNombre()) ,CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,(short)18);
                      setCellValue((short)0,(short)3,((objCliente.getDireccion())==null?"":objCliente.getDireccion())+ "  - Telf:" + ((objCliente.getTelefono())==null?"":objCliente.getTelefono()) ,CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_8);
                      setCellValue((short)1,(short)0,"R.U.C.#" + (objCliente.getIdentificacion()==null?"":objCliente.getIdentificacion()),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,CON_CURSIVA,SIZE_12);
                      setCellValue((short)1,(short)7,rstCab.getString("co_cot"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)3,(short)10,rstCab.getString("fe_cot"),FECHA,HSSFCellStyle.ALIGN_CENTER);                        
                      setCellValue((short)3,(short)1,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_CENTER);
                      setCellValue((short)3,(short)2,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)5,(short)1,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                           
                      setCellValue((short)7,(short)1,(rstCab.getString("tx_tel")==null)?"":rstCab.getString("tx_tel"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                           
                      setCellValue((short)9,(short)1,(rstCab.getString("tx_ate")==null)?"":rstCab.getString("tx_ate"),CADENA,HSSFCellStyle.ALIGN_LEFT);                      
                      setCellValue((short)25,(short)1,(rstCab.getString("tx_desforpag")==null)?"":rstCab.getString("tx_desforpag"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                    
                      setCellValue((short)26,(short)1,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)27,(short)1,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_LEFT);                      
                      setCellValue((short)28,(short)1,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_LEFT);                        
                      dblvalor = Double.parseDouble(rstCab.getString("nd_sub"));
                      setCellValue((short)25,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor = dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100);
                      setCellValue((short)26,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor += (Double.parseDouble(rstCab.getString("nd_sub")));
                      setCellValue((short)28,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                        
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detcotcom  where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_cot = "+ intNumDoc;
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 12;
                       dblvalor =0;
                       stmItm = con.createStatement(); 
                       while (rstDet.next())
                       {
                            strSQL = "";
                            strSQL = " select tx_codalt, tx_descor from  tbm_inv as inv left outer join tbm_var as var on (inv.co_uni = var.co_reg) ";
                            strSQL += " where inv.co_emp  = " + objZafParSis.getCodigoEmpresa() + " and  inv.co_itm = " + rstDet.getInt("co_itm");
                            rstItm = stmItm.executeQuery(strSQL);
                            if (rstItm.next())
                            {
                                setCellValue((short)intFila,(short)0,(rstItm.getString("tx_codalt")==null)?"":rstItm.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                                setCellValue((short)intFila,(short)6,(rstItm.getString("tx_descor")==null)?"":rstItm.getString("tx_descor"),CADENA,HSSFCellStyle.ALIGN_CENTER);
                            }
                            rstItm.close();                                                   
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                          
                            setCellValue((short)intFila,(short)5,rstDet.getString("nd_can"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                                              
                            setCellValue((short)intFila,(short)7,rstDet.getString("nd_cosuni"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);
                            dblvalor = objUti.redondeo(Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_cosuni"))*(Double.parseDouble((rstDet.getString("nd_pordes")==null)?"0":rstDet.getString("nd_pordes")) /100),6);
                            setCellValue((short)intFila,(short)8,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT);
                            dblvalor = objUti.redondeo(Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_cosuni")),6)-dblvalor;
                            setCellValue((short)intFila,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                            
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();      
                   imprimir("$A$0:$L$31");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    /**
     * Metodo para formato facturas
     */
    private boolean FormatoFactura()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoFactura.xls";
        double dblvalor=0;
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {                                            
                      setCellValue((short)1,(short)0,rstCab.getString("ne_numdoc"),CADENA,HSSFCellStyle.ALIGN_CENTER,NORMAL,SIN_CURSIVA,SIZE_10,FONT_ARIAL);
                      setCellValue((short)1,(short)1,rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_CENTER,NORMAL,SIN_CURSIVA,SIZE_10,FONT_ARIAL);                        
                      setCellValue((short)1,(short)5,(rstCab.getString("tx_numped")==null)?"0":rstCab.getString("tx_numped"),ENTERO,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_COURIER_NEW);                                              
                      setCellValue((short)1,(short)6,(rstCab.getString("ne_numgui")==null)?"0":rstCab.getString("ne_numgui"),ENTERO,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_COURIER_NEW);                                              
                      setCellValue((short)1,(short)8,(rstCab.getString("tx_desforpag")==null)?"":rstCab.getString("tx_desforpag"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_COURIER_NEW);
                      setCellValue((short)3,(short)0,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)3,(short)5,(rstCab.getString("tx_telcli")==null)?"":rstCab.getString("tx_telcli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)7,(short)0,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                     
                      setCellValue((short)5,(short)5,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)7,(short)6,(rstCab.getString("tx_ciucli")==null)?"":rstCab.getString("tx_ciucli"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_ARIAL);                      
                      setCellValue((short)7,(short)7,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_ARIAL);  
                      setCellValue((short)8,(short)9,(rstCab.getString("tx_desmottra")==null)?"":rstCab.getString("tx_desmottra"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_ARIAL);                      
                      dblvalor =objUti.redondeo(Double.parseDouble(rstCab.getString("nd_sub"))*-1,6);
                      setCellValue((short)27,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_COURIER_NEW);  
                      dblvalor = objUti.redondeo(dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100),6);
                      setCellValue((short)28,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_COURIER_NEW);  
                      dblvalor += objUti.redondeo((Double.parseDouble(rstCab.getString("nd_sub"))*-1),6);
                      setCellValue((short)30,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_12,FONT_COURIER_NEW);  
                      setCellValue((short)31,(short)0,rstCab.getString("co_doc"),CADENA,HSSFCellStyle.ALIGN_RIGHT);
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc;
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 10;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            setCellValueFac((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_GARAMOND); 
                            dblvalor = Double.parseDouble(rstDet.getString("nd_can"))*-1;
                            setCellValueFac((short)intFila,(short)1,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL); 
                            setCellValueFac((short)intFila,(short)2,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL); 
                            setCellValueFac((short)intFila,(short)3,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL); 
                            dblvalor = Double.parseDouble(rstDet.getString("nd_preuni"));
                            setCellValueFac((short)intFila,(short)6,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                             
                            setCellValueFac((short)intFila,(short)7,(rstDet.getString("nd_pordes")==null)?"0":rstDet.getString("nd_pordes"),CADENA,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL); 
                            dblvalor = (rstDet.getDouble("nd_can") * rstDet.getDouble("nd_preuni")*-1)-(rstDet.getString("nd_pordes")=="0"?0:(rstDet.getDouble("nd_can")*rstDet.getDouble("nd_preuni")*-1)*rstDet.getDouble("nd_pordes")/100);
                            setCellValueFac((short)intFila,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_11,FONT_ARIAL); 
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();                   
                   imprimir("$A$1:$J$26");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    /**
     * Metodo para formato facturas NOSITOL
     */
    private boolean FormatoFacturaNOSITOL()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoFacturaNOSITOL.xls";
        double dblvalor=0;
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {                                            
                      setCellValue((short)5,(short)1,rstCab.getString("ne_numdoc"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_10,FONT_ARIAL);
                      setCellValue((short)1,(short)8,rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_8,FONT_ARIAL);                        
                      //setCellValue((short)1,(short)5,(rstCab.getString("tx_numped")==null)?"0":rstCab.getString("tx_numped"),ENTERO,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_COURIER_NEW);                                              
                      setCellValue((short)5,(short)8,(rstCab.getString("ne_numgui")==null)?"0":rstCab.getString("ne_numgui"),ENTERO,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_8,FONT_COURIER_NEW);                                              
                      setCellValue((short)2,(short)8,(rstCab.getString("tx_desforpag")==null)?"":rstCab.getString("tx_desforpag"),CADENA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_8,FONT_COURIER_NEW);
                      setCellValue((short)7,(short)1,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)7,(short)6,(rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)9,(short)1,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);                     
                      setCellValue((short)7,(short)8,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_ARIAL);
                      setCellValue((short)9,(short)8,(rstCab.getString("tx_telcli")==null)?"":rstCab.getString("tx_telcli"),CADENA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_8,FONT_ARIAL);                      
                      setCellValue((short)4,(short)8,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_8,FONT_ARIAL);  
                      setCellValue((short)34,(short)0,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)36,(short)0,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_LEFT);                      

                      //setCellValue((short)8,(short)9,(rstCab.getString("tx_desmottra")==null)?"":rstCab.getString("tx_desmottra"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_9,FONT_ARIAL);                      
                      dblvalor =objUti.redondeo(Double.parseDouble(rstCab.getString("nd_sub"))*-1,6);
                      setCellValue((short)34,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_COURIER_NEW);  
                      dblvalor = objUti.redondeo(dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100),6);
                      setCellValue((short)35,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NEGRITA,SIN_CURSIVA,SIZE_11,FONT_COURIER_NEW);  
                      dblvalor += objUti.redondeo((Double.parseDouble(rstCab.getString("nd_sub"))*-1),6);
                      setCellValue((short)37,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_12,FONT_COURIER_NEW);  
                      setCellValue((short)38,(short)0,rstCab.getString("co_doc"),CADENA,HSSFCellStyle.ALIGN_RIGHT);
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc;
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 12;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            dblvalor = Double.parseDouble(rstDet.getString("nd_can"))*-1;
                            setCellValue((short)intFila,(short)5,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_LEFT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            setCellValue((short)intFila,(short)6,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL);                             
                            dblvalor = Double.parseDouble(rstDet.getString("nd_preuni"));
                            setCellValue((short)intFila,(short)7,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            setCellValue((short)intFila,(short)8,(rstDet.getString("nd_pordes")==null)?"0":rstDet.getString("nd_pordes"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            //dblvalor = Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_preuni"))*-1;
                            dblvalor = (rstDet.getDouble("nd_can") * rstDet.getDouble("nd_preuni")*-1)-(rstDet.getString("nd_pordes")=="0"?0:(rstDet.getDouble("nd_can")*rstDet.getDouble("nd_preuni")*-1)*rstDet.getDouble("nd_pordes")/100);
                            setCellValue((short)intFila,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT,NORMAL,SIN_CURSIVA,SIZE_9,FONT_ARIAL); 
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();                   
                   imprimir("$A$1:$J$39");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    /**
     * Metodo para formato O/C
     */
    private boolean FormatoCompras()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoCompra.xls";
        double dblvalor=0;
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      
                      setCellValue((short)2,(short)0,(rstCab.getString("tx_descor")==null)?"":rstCab.getString("tx_descor"),CADENA,HSSFCellStyle.ALIGN_CENTER);
                      setCellValue((short)2,(short)1,(rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc"),CADENA,HSSFCellStyle.ALIGN_CENTER);                      
                      setCellValue((short)4,(short)5,(rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)4,(short)2,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)4,(short)7,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_CENTER);
                      setCellValue((short)6,(short)2,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT);                     
                      setCellValue((short)6,(short)7,(rstCab.getString("fe_doc")==null)?"":rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_CENTER);                                                                                          
                      dblvalor = Double.parseDouble(rstCab.getString("nd_sub"));
                      setCellValue((short)33,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor = dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100);
                      setCellValue((short)34,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor += (Double.parseDouble(rstCab.getString("nd_sub")));
                      setCellValue((short)35,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      setCellValue((short)28,(short)0,"Forma de Pago:"+((rstCab.getString("tx_desforpag")==null)?"":rstCab.getString("tx_desforpag")),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                                          
                      setCellValue((short)35,(short)0,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_LEFT);  
                      setCellValue((short)33,(short)0,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                    
                      setCellValue((short)34,(short)0,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_LEFT);  
                      setCellValue((short)37,(short)0,rstCab.getString("co_tipdoc")+" - " +rstCab.getString("co_doc"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                    
                   }
                   //Detalle
                   strSQL = "";
                   //se corrige el query para ordenes de compras ordenado por registro
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc +" order by co_reg";
                 
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 10;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                          
                            setCellValue((short)intFila,(short)1,rstDet.getString("nd_can"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                                              
                            setCellValue((short)intFila,(short)2,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                              
                            setCellValue((short)intFila,(short)3,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                              
                            dblvalor = Double.parseDouble(rstDet.getString("nd_cosuni"));
                            setCellValue((short)intFila,(short)6,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_LEFT);
                            setCellValue((short)intFila,(short)7,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT);
                            //dblvalor = Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_cosuni"));
                            dblvalor = (rstDet.getDouble("nd_can") * rstDet.getDouble("nd_cosuni"))-(rstDet.getString("nd_pordes")=="0"?0:(rstDet.getDouble("nd_can")*rstDet.getDouble("nd_cosuni"))*rstDet.getDouble("nd_pordes")/100);
                            setCellValue((short)intFila,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();                   
                   imprimir("$A$1:$J$40");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
     /**
     * Metodo para formato Devoluciones
     */
    private boolean FormatoDevolucion()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoDevolucion.xls";
        double dblvalor=0;
        try
        {            
            //strNameFile = "C:\\Zafiro\\Reportes\\Devolucion.xls";
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      
                      setCellValue((short)1,(short)3,"NC #"+((rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc")),CADENA,HSSFCellStyle.ALIGN_CENTER);
//                      setCellValue((short)1,(short)4,"DOC #"+((rstCab.getString("co_doc")==null)?"":rstCab.getString("co_doc")),CADENA,HSSFCellStyle.ALIGN_CENTER);    
                      setCellValue((short)3,(short)1,(rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)3,(short)2,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)3,(short)8,(rstCab.getString("fe_doc")==null)?"":rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_CENTER);                                                                                          
                      setCellValue((short)5,(short)1,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)5,(short)3,(rstCab.getString("tx_telcli")==null)?"":rstCab.getString("tx_telcli"),CADENA,HSSFCellStyle.ALIGN_RIGHT);                                           
                      setCellValue((short)5,(short)8,(rstCab.getString("ne_numcot")==null)?"":rstCab.getString("ne_numcot"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)7,(short)1,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                           
                      dblvalor = Double.parseDouble(rstCab.getString("nd_sub"));
                      setCellValue((short)36,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor = dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100);
                      setCellValue((short)38,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor += (Double.parseDouble(rstCab.getString("nd_sub")));
                      setCellValue((short)39,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                        
                      setCellValue((short)39,(short)0,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_CENTER);  
                      setCellValue((short)34,(short)0,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                    
                      setCellValue((short)35,(short)0,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_CENTER);  
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc+ " and nd_can>0";
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 10;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                          
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                                                                                                   
                            setCellValue((short)intFila,(short)5,rstDet.getString("nd_can"),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                                              
                            setCellValue((short)intFila,(short)6,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER); 
                            dblvalor = Double.parseDouble(rstDet.getString("nd_preuni"));
                            setCellValue((short)intFila,(short)7,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_LEFT);
                            setCellValue((short)intFila,(short)9,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT);
                            dblvalor = java.lang.Math.abs(rstDet.getDouble("nd_can")) * rstDet.getDouble("nd_preuni") - ((java.lang.Math.abs(rstDet.getDouble("nd_can")) * rstDet.getDouble("nd_preuni") * (rstDet.getDouble("nd_pordes")/100)));
                            setCellValue((short)intFila,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();                   
                   imprimir("$A$1:$J$40");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
     /**
     * Metodo para formato DevolucionesCompras
     */
    private boolean FormatoDevolucionCompras()
    {
        boolean blnResp= false;
        String strFile="C:\\Zafiro\\Reportes\\FormatoDevolucion.xls";
        double dblvalor=0;
        try
        {            
            //strNameFile = "C:\\Zafiro\\Reportes\\Devolucion.xls";
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      
                      setCellValue((short)1,(short)3,(rstCab.getString("tx_descor")==null)?"":rstCab.getString("tx_descor"),CADENA,HSSFCellStyle.ALIGN_CENTER);
                      setCellValue((short)1,(short)4,(rstCab.getString("co_doc")==null)?"":rstCab.getString("co_doc"),CADENA,HSSFCellStyle.ALIGN_CENTER);    
                      setCellValue((short)3,(short)1,(rstCab.getString("co_cli")==null)?"":rstCab.getString("co_cli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)3,(short)2,(rstCab.getString("tx_nomcli")==null)?"":rstCab.getString("tx_nomcli"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)3,(short)8,(rstCab.getString("fe_doc")==null)?"":rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_CENTER);                                                                                          
                      setCellValue((short)5,(short)1,(rstCab.getString("tx_ruc")==null)?"":rstCab.getString("tx_ruc"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)5,(short)3,(rstCab.getString("tx_telcli")==null)?"":rstCab.getString("tx_telcli"),CADENA,HSSFCellStyle.ALIGN_RIGHT);                                           
                      setCellValue((short)5,(short)8,(rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc"),CADENA,HSSFCellStyle.ALIGN_LEFT);
                      setCellValue((short)7,(short)1,(rstCab.getString("tx_dircli")==null)?"":rstCab.getString("tx_dircli"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                           
                      dblvalor = Double.parseDouble(rstCab.getString("nd_sub"))*-1;
                      setCellValue((short)36,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor = dblvalor*(Double.parseDouble(rstCab.getString("nd_poriva"))/100);
                      setCellValue((short)38,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);  
                      dblvalor += (Double.parseDouble(rstCab.getString("nd_sub")))*-1;
                      setCellValue((short)39,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                        
                      setCellValue((short)39,(short)0,(rstCab.getString("tx_nomven")==null)?"":rstCab.getString("tx_nomven"),CADENA,HSSFCellStyle.ALIGN_CENTER);  
                      setCellValue((short)34,(short)0,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                    
                      setCellValue((short)35,(short)0,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_CENTER);  
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc+ " and (nd_can*-1)>0";
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       int intFila = 10;
                       dblvalor =0;
                       while (rstDet.next())
                       {
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                          
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                                                                                                   
                            dblvalor = rstDet.getDouble("nd_can")*-1;
                            setCellValue((short)intFila,(short)5,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                                              
                            setCellValue((short)intFila,(short)6,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER); 
                            dblvalor = Double.parseDouble(rstDet.getString("nd_cosuni"));
                            setCellValue((short)intFila,(short)7,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_LEFT);
                            setCellValue((short)intFila,(short)9,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT);
                            dblvalor = Double.parseDouble(rstDet.getString("nd_can")) * Double.parseDouble(rstDet.getString("nd_cosuni"))*-1;
                            setCellValue((short)intFila,(short)10,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);
                            intFila++;
                       }
                       rstDet.close();
                   }
                   GenerarFile();                   
                   imprimir("$A$1:$J$40");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    /**
     * Metodo para formato Doc I/E
     */
    private boolean FormatoIE()
    {
        boolean blnResp= false;
        String strFile="";
        if (objZafParSis.getCodigoUsuario()==30)
            strFile="C:\\Zafiro\\Reportes\\FormatoDocIE2.xls";
        else
            strFile="C:\\Zafiro\\Reportes\\FormatoDocIE.xls";
        double dblvalor=0;
        int intFila = 8;
        double dblSubtotal=0,dblTotal,dblIva,dblPorIva;        
        try
        {            
            fs = new POIFSFileSystem(new FileInputStream(strFile));
            wb = new HSSFWorkbook(fs);            
            sheet = wb.getSheetAt(0); //sheet = wb.createSheet("Documento");        
            
           java.sql.Connection con = DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos()); 
           if (con!=null)
           {
               stm=con.createStatement(); 
               strSQL = "";
               strSQL = " select * from tbm_cabmovinv as cabmov left outer join tbm_cabtipdoc as cabtip on (cabmov.co_emp = cabtip.co_emp and cabmov.co_tipdoc = cabtip.co_tipdoc) where cabmov.co_emp = "+ objZafParSis.getCodigoEmpresa() +" and cabmov.co_loc = "+ objZafParSis.getCodigoLocal() +" and cabmov.co_tipdoc = " + intTipoDoc +" and cabmov.co_doc = "+ intNumDoc;
               if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
               {
                   //Cabecera
                   rstCab = stm.executeQuery(strSQL);
                   if (rstCab.next())
                   {
                      setCellValue((short)0,(short)1,(rstCab.getString("tx_deslar")==null)?"":rstCab.getString("tx_deslar"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_12,FONT_TIMES_NEW_ROMAN);
                      setCellValue((short)4,(short)0,(rstCab.getString("tx_descor")==null)?"":rstCab.getString("tx_descor"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);
                      setCellValue((short)4,(short)2,(rstCab.getString("ne_numdoc")==null)?"":rstCab.getString("ne_numdoc"),CADENA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);
                      setCellValue((short)2,(short)1,objZafParSis.getNombreEmpresa(),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);
                      setCellValue((short)3,(short)1,objZafParSis.getNombreLocal(),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);
                      setCellValue((short)2,(short)5,(rstCab.getString("fe_doc")==null)?"":rstCab.getString("fe_doc"),FECHA,HSSFCellStyle.ALIGN_CENTER,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);                                                                                          
                   }
                   //Detalle
                   strSQL = "";
                   strSQL = " select * from tbm_detmovinv where co_emp = "+ objZafParSis.getCodigoEmpresa() +" and co_loc = "+ objZafParSis.getCodigoLocal() +" and co_tipdoc = " + intTipoDoc +" and co_doc = "+ intNumDoc;
                   if (objUti.getNumeroRegistro(new javax.swing.JInternalFrame(), objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(),strSQL)>0)
                   {
                       rstDet = stm.executeQuery(strSQL);
                       while (rstDet.next())
                       {
                            setCellValue((short)intFila,(short)0,(rstDet.getString("tx_codalt")==null)?"":rstDet.getString("tx_codalt"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                                                          
                            setCellValue((short)intFila,(short)1,(rstDet.getString("tx_nomitm")==null)?"":rstDet.getString("tx_nomitm"),CADENA,HSSFCellStyle.ALIGN_LEFT);                                                                          
                            setCellValue((short)intFila,(short)5,(rstDet.getString("tx_unimed")==null)?"":rstDet.getString("tx_unimed"),CADENA,HSSFCellStyle.ALIGN_CENTER);                                              
                            setCellValue((short)intFila,(short)6,String.valueOf(java.lang.Math.abs(rstDet.getDouble("nd_can"))),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);                                           
                            setCellValue((short)intFila,(short)7,rstDet.getString("nd_cosuni"),DOUBLE,HSSFCellStyle.ALIGN_LEFT);
                            if (objZafParSis.getCodigoUsuario()==30){
                                setCellValue((short)intFila,(short)8,(rstDet.getString("nd_pordes")==null)?"0%":rstDet.getString("nd_pordes")+"%",CADENA,HSSFCellStyle.ALIGN_RIGHT);
                                dblvalor = java.lang.Math.abs(rstDet.getDouble("nd_can")) * rstDet.getDouble("nd_cosuni") - ((java.lang.Math.abs(rstDet.getDouble("nd_can")) * rstDet.getDouble("nd_cosuni") * (rstDet.getDouble("nd_pordes")/100)));
                                setCellValue((short)intFila,(short)9,String.valueOf(dblvalor),DOUBLE,HSSFCellStyle.ALIGN_RIGHT);
                                dblSubtotal = objUti.redondear(dblSubtotal + dblvalor,objZafParSis.getDecimalesBaseDatos());
                            }
                            intFila++;
                       }
                       intFila++;
                       if (objZafParSis.getCodigoUsuario()==30){
                           setCellValue((short)intFila,(short)9,"SUBTOTAL: "+String.valueOf(objUti.redondear(dblSubtotal,objZafParSis.getDecimalesMostrar())),CADENA,HSSFCellStyle.ALIGN_RIGHT);
                           Librerias.ZafUtil.ZafTipDoc objTipDoc = new Librerias.ZafUtil.ZafTipDoc(objZafParSis);
                           objTipDoc.cargarTipoDoc(rstCab.getInt("co_tipdoc"));
                           if (objTipDoc.getst_iva().equals("S"))
                                dblIva = objUti.redondear(dblSubtotal * (rstCab.getDouble("nd_poriva")>0?(rstCab.getDouble("nd_poriva")/100):0),objZafParSis.getDecimalesMostrar());
                           else
                               dblIva = 0;
                           dblTotal = objUti.redondear(dblSubtotal+dblIva,objZafParSis.getDecimalesMostrar());
                           
                           setCellValue((short)(intFila+1),(short)9,"IVA 12%: "+String.valueOf(dblIva),CADENA,HSSFCellStyle.ALIGN_RIGHT);
                           setCellValue((short)(intFila+2),(short)9,"TOTAL: "+String.valueOf(dblTotal),CADENA,HSSFCellStyle.ALIGN_RIGHT);
                       }
                       setCellValue((short)intFila++,(short)1,(rstCab.getString("tx_obs1")==null)?"":rstCab.getString("tx_obs1"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);                                                                    
                       setCellValue((short)intFila++,(short)1,(rstCab.getString("tx_obs2")==null)?"":rstCab.getString("tx_obs2"),CADENA,HSSFCellStyle.ALIGN_LEFT,NEGRITA,SIN_CURSIVA,SIZE_10,FONT_COURIER_NEW);  
                       
                       rstDet.close();
                   }
                   GenerarFile();        
                   if (objZafParSis.getCodigoUsuario()==30)
                       imprimir("$A$1:$K$"+(intFila+1));                   
                   else
                       imprimir("$A$1:$I$24");
                   rstCab.close();                            
                   blnResp= true;
               }                                  
               stm.close();
               con.close();
           }               
        }catch(SQLException Evt){    
            blnResp = false;
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), Evt);
        }catch(Exception e){
            blnResp= false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(), e);
        }        
        return blnResp;
    }
    private void Pie()
    {
    }
}

    