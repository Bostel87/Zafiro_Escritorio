/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RecursosHumanos.ZafRecHum37.ExcelTableExporter;


import java.io.*;
import java.util.ArrayList;
import javax.swing.*;
import jxl.write.*;
import jxl.*;

/**
 *
 * @author sistemas2
 */
public class ExcelTableExporter {
    
    private File file;

    private JTable table;

    private String nombreTab;
    
    private ArrayList<String> arrLst=null;
    
    private Label lblCab;
    
    private final int INT_INC_CAB=6;

    public ExcelTableExporter(JTable table,File file,String nombreTab,ArrayList<String> arrLst){

        this.file=file;

        this.table=table;

        this.nombreTab=nombreTab;

        this.arrLst=arrLst;
    }
    
    public boolean export(){

//    try
//
//    {

        boolean blnRes=false;
        try{
                try{
                    if(file.exists())
                        file.delete();
                    // A partir del objeto File creamos el fichero físicamente
                    if (file.createNewFile()){
                        blnRes=true;
                    }
                    else{
                        blnRes=false;
                    }
                }
                catch (java.io.IOException ioe) {
                  ioe.printStackTrace();
                  blnRes=false;
                }
        
        //Nuestro flujo de salida para apuntar a donde vamos a escribir

        DataOutputStream out=new DataOutputStream(new FileOutputStream(file));

        //Representa nuestro archivo en excel y necesita un OutputStream para saber donde va locoar los datos

        WritableWorkbook w = Workbook.createWorkbook(out);

        //Como excel tiene muchas hojas esta crea o toma la hoja

        //Coloca el nombre del "tab" y el indice del tab

        WritableSheet s = w.createSheet(nombreTab, 0);
               
        for(int i=0;i<table.getColumnCount();i++){
                   Label lbl=new Label((i), (INT_INC_CAB-1), table.getModel().getColumnName(i));
                   lbl.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
                   s.addCell(lbl);        
               }
               
        for(int i=0;i< table.getColumnCount();i++){ 
            for(int j=0;j<table.getRowCount();j++){ 
                Object objeto=table.getValueAt(j,i); 
                s.addCell(new Label(i, (INT_INC_CAB)+j, String.valueOf(objeto))); 
            } 
        } 
        //Como excel tiene muchas hojas esta crea o toma la hoja 
        //Coloca el nombre del "tab" y el indice del tab 


        w.write(); 
        
        //Cerramos el WritableWorkbook y DataOutputStream

        w.close();

        out.close();

        //si todo sale bien salimos de aqui con un true  

        return true;

        }catch(IOException ex){ex.printStackTrace();}

        catch(WriteException ex){ex.printStackTrace();}

        //Si llegamos hasta aqui algo salio mal

        return false;

    }
    
}
