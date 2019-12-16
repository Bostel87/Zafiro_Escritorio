/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Librerias.ZafUtil;

import java.io.*;

import java.util.Random;

import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import org.apache.poi.hssf.record.*;

import org.apache.poi.hssf.model.*;

import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.hssf.util.*;

import Librerias.ZafParSis.ZafParSis;

import java.util.Vector;

import Librerias.ZafTblUti.ZafTblMod.ZafTblMod;


/**
 *
 * @author TUVAL\dcardenas
 */
public class ZafImpDatXml 
{
    /* Declaracion de Variables */
    
    private ZafParSis objZafParSis;
    private ZafUtil objUti;
    private Vector vecDet, vecDetDiario;
    private ZafTblMod objTblMod;
    private java.util.Date datFecAux;                   //Auxiliar: Para almacenar fechas.    
    ///private java.io.FileOutputStream out;
    
    
    public ZafImpDatXml(ZafParSis obj)
    {
        objZafParSis = obj;
        this.objZafParSis = obj;
        
        objUti = new ZafUtil();        
    }
    
    
    
    ///public boolean EjecutarFuncion(int intFil, int intCol, Vector vector)
    public boolean EjecutarFuncion(int intFil[], int intCol[], String strArr[][], String strRuta)
    //public boolean EjecutarFuncion(int intFil[], int intCol[], Vector vector)
    {
        boolean blnRes=true;
        int intFilSel[], intColSel[], intVal=0, i=0,j=0;
        Object objAux;
        StringBuffer stb=new StringBuffer();
        java.util.StringTokenizer strTok;
        String result[][], strAux;
        
        
        try
        {
            short rownum;
            
            //Obtener la fecha del servidor.
            datFecAux=objUti.getFechaServidor(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos(), objZafParSis.getQueryFechaHoraBaseDatos());
            if (datFecAux==null)
                return false;
            
            strAux=objUti.formatearFecha(datFecAux, objZafParSis.getFormatoFechaHoraBaseDatos());
                        
            // create a destination file

            //FileOutputStream out = new FileOutputStream("/home/TUVAL/dcardenas/Desktop/JAVA_EXCEL/ARCHIVO_"+strAux+".xls");
            FileOutputStream out = new FileOutputStream(""+strRuta+".xls");

            // create a new workbook object; note that the workbook

            // and the file are two separate things until the very

            // end, when the workbook is written to the file.

            HSSFWorkbook wb = new HSSFWorkbook( );

            // create a new worksheet

            HSSFSheet ws = wb.createSheet( );

            // create a row object reference for later use

            HSSFRow r = null;

            // create a cell object reference

            HSSFCell c = null;

            // create two cell styles - formats

            //need to be defined before they are used

            HSSFCellStyle cs1 = wb.createCellStyle( );

            HSSFCellStyle cs2 = wb.createCellStyle( );

            HSSFDataFormat df = wb.createDataFormat( );

            // create two font objects for formatting

            HSSFFont f1 = wb.createFont( );

            HSSFFont f2 = wb.createFont( );

            //set font 1 to 10 point bold type

            f1.setFontHeightInPoints((short) 10);

            f1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            //set font 2 to 10 point red type

            f2.setFontHeightInPoints((short) 10);

            f2.setColor( (short)HSSFFont.COLOR_RED );

            //for cell style 1, use font 1 and set data format

            cs1.setFont(f1);

            cs1.setDataFormat(df.getFormat("#,##0.0"));

            //for cell style 2, use font 2, set a thin border, text format

            cs2.setBorderBottom(cs2.BORDER_THIN);

            cs2.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

            cs2.setFont(f2);

            // set the sheet name in Unicode

//            wb.setSheetName(0, "LIBRO_1", HSSFWorkbook.ENCODING_UTF_16 );
            wb.setSheetName(0, "LIBRO_1");
            

            // create a sheet with 10 rows (0-9)

            ///for (rownum = (short) 0; rownum < 20; rownum++)
            //for (rownum = (short) 0; rownum < intFil.length; rownum++)
            
            System.out.println("--FILA--intFil.length: " + intFil.length);
            System.out.println("--COLUMNA--intCol.length: " + intCol.length);
            
            for (i=0; i < intFil.length; i++)
            {

                // create a row

                //r = ws.createRow(rownum);
                r = ws.createRow(i);

                //r.setRowNum(( short ) rownum);

                // create six cells (0-5) (the += 2 becomes apparent later

                ///for (short cellnum = (short) 0; cellnum < 10; cellnum += 1)
                //for (short cellnum = (short) 0; cellnum < intCol.length; cellnum += 1)
                for (j = 1; j < intCol.length; j++)
                {                 
                    // create a numeric cell

                    //c = r.createCell(cellnum);
                    c = r.createCell((short)j);

                    // fill with numbers based on position

                    ///c.setCellValue(rownum * 10 + cellnum + (((double) rownum / 10) + ((double) cellnum / 100)));                 
                    c.setCellValue(strArr[i][j]);

                    // create a string cell

                    //c = r.createCell((short) (cellnum + 1));
                    c = r.createCell((short) (j + 1));
                    //c = r.createCell((short) (j + 0));

                    // on every other row (this is why +=2)

                    //if ((rownum % 2) == 0)
                    if ((i % 2) == 0)

                    {

                        // set this cell to the first cell style we defined

                        c.setCellStyle(cs1);

                        // set the cell’s string value to “Test”

//                        c.setEncoding( HSSFCell.ENCODING_UTF_16 );

    //                    c.setCellValue("Test");

                    }

                    else

                    {

                        c.setCellStyle(cs2);

                        // set the cell’s string value to “1… 2… 3…”

//                        c.setEncoding( HSSFCell.ENCODING_UTF_16 );

    //                    c.setCellValue("1… 2… 3…");

                    }

                }

            }
            
            // use some formulas

            // advance a row

            //rownum++;
            i++;

            //r = ws.createRow(rownum);
            r = ws.createRow(i);

            //create formulas.

            ///for (short cellnum = (short) 0; cellnum < 10; cellnum += 1)
//            for (j = 0; j < intCol.length; j++)
//            {
//
//                //produce SUMs for appropriate columns
//                
//                ///int column= 65+cellnum;//original///
//                //int column= 65+cellnum;
//                int column= 65+j;
//
//                char columnLabel=(char)column;
//
//                ///String formula="SUM("+columnLabel+"1:"+columnLabel+"20)";
//                String strvalFil = ""+(intFil);
//                ///System.out.println("---ZafAjuAutSis---strvalFil: " + strvalFil);
////                System.out.println("---ZafAjuAutSis---columnLabel: " + columnLabel);
//                
////                String formula="SUM("+columnLabel+"1:"+columnLabel+strvalFil+")";
//
//                c = r.createCell(cellnum);
//
//                c.setCellStyle(cs1);
//
////                c.setCellFormula(formula);
//
//            }

            // write the workbook to the output stream,

            // remembering to close our file

            wb.write(out);

            out.close( );
        }
        catch (Exception e)
        {
            blnRes=false;
            System.out.println(e.toString());
            objUti.mostrarMsgErr_F1(new javax.swing.JInternalFrame(),e);
        }
        return blnRes;
    }
    
}
