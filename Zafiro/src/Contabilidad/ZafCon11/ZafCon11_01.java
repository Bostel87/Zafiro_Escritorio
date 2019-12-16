/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Contabilidad.ZafCon11;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
//import jxl.format.Alignment;
import Librerias.ZafUtil.ZafUtil;
import jxl.write.NumberFormats;


/**
 *
 * @author necronet
 * modificado por Ingrid Lino
 */


public class ZafCon11_01 {
	 
        private File file;
        private JTable table;
        private String nombreTab;
        private Label LblCol;
        private final int INT_INC_CAB=1;//FILA DESDE DONDE SE COMIENZA A COLOCAR LOS DATOS DE LA TABLA(FILA 0, FILA 1, FILA 2.....)

        private Label lblCab;
        private ZafUtil objUti;
        private double num;
        private Number numCol;
        private final WritableCellFormat dblFormat = new WritableCellFormat (NumberFormats.FLOAT);
        
        public ZafCon11_01(JTable table,File file,String nombreTab){
            try{
                this.file=file;
                this.table=table;
                this.nombreTab=nombreTab;
                objUti=new ZafUtil();
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(table, e);
            }
	 }
                
         public boolean export(){
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
                 
               DataOutputStream out=new DataOutputStream(new FileOutputStream(file));
               //WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
               WritableWorkbook w = Workbook.createWorkbook(out);
               WritableSheet s = w.createSheet(nombreTab, 0);
               
               for(int i=0;i<table.getColumnCount();i++){
                   lblCab = new Label(i, 0, "" + table.getColumnName(i));
                   lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                   s.addCell(lblCab);
               }

               for(int i=0;i<table.getRowCount();i++){
                   for(int j=0;j<table.getColumnCount();j++){
                       String objeto=table.getValueAt(i, j)==null?"":table.getValueAt(i, j).toString();
                       if(  ! objeto.toString().equals("") ){
                           if( (j==7) || (j==8) || (j==9) || (j==13) || (j==15) || (j==16) || (j==18) ){
                               num = Math.abs(Double.parseDouble(String.valueOf(objeto)));
                               numCol = new Number(j, i+INT_INC_CAB, num, dblFormat);
                               numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                               s.addCell(numCol);
                           }

                           else{
                               LblCol = new Label(j, i+INT_INC_CAB, String.valueOf(objeto));
                               LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                               s.addCell(LblCol);
                           }


                       }
                       else
                           s.addCell(new Label(j, i+INT_INC_CAB, ""));
                   }
               }

               
               
               s.removeColumn(2);
               s.removeColumn(1);
               s.removeColumn(0);



               w.write();
               w.close();
               out.close();
               
               
               
	     }
             catch (Exception e){
                 blnRes=false;
	     }
          return blnRes;
         }
         
  
            
         
         
         
         
         
}
        
        
        
        
        
        
        
        
        