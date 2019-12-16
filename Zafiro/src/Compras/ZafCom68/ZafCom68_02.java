/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package Compras.ZafCom68;


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
import java.math.BigDecimal;
import java.util.ArrayList;
import jxl.write.NumberFormats;


/**
 *
 * @author necronet
 * modificado por Ingrid Lino
 */


public class ZafCom68_02 {
	 
        private File file;
        private ArrayList arlDatExpExc;
        private String nombreTab;
        private Label LblCol;
        private final int INT_INC_CAB=1;//FILA DESDE DONDE SE COMIENZA A COLOCAR LOS DATOS DE LA TABLA(FILA 0, FILA 1, FILA 2.....)

        private Label lblCab;
        private ZafUtil objUti;
        private double num;
        private Number numCol;
        private final WritableCellFormat dblFormat = new WritableCellFormat (NumberFormats.FLOAT);

        private final int INT_ARL_EXP_EXC_COD_ALT_ITM=0;
        private final int INT_ARL_EXP_EXC_COD_ALT_ITM2=1;
        private final int INT_ARL_EXP_EXC_NOM_ITM=2;
        private final int INT_ARL_EXP_EXC_PES_UNI=3;
        private final int INT_ARL_EXP_EXC_CAN_REP_LSC=4;
        private final int INT_ARL_EXP_EXC_PES_TOT_ITM=5;

        
        
        public ZafCom68_02(ArrayList arreglo,File file,String nombreTab){
            try{
                this.file=file;
                this.arlDatExpExc=arreglo;
                this.nombreTab=nombreTab;
                objUti=new ZafUtil();
            }
            catch(Exception e){
                //objUti.mostrarMsgErr_F1(this, e);
                System.out.println("Error ZafCom68_02: " + e);
            }
	 }
                
         public boolean export(){
             boolean blnRes=false;
             int intFilExcSinCer=0;
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
               
                lblCab = new Label(0, 0, "Código Item");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(1, 0, "Código Item 2");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
               
                lblCab = new Label(2, 0, "Nombre Item");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(3, 0, "Peso Item");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(4, 0, "Cantidad Reponer");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(5, 0, "Peso total");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                

                
               for(int i=0;i<arlDatExpExc.size();i++){
                   
                    for(int j=INT_ARL_EXP_EXC_COD_ALT_ITM;j<(INT_ARL_EXP_EXC_PES_TOT_ITM+1);j++){
                        
                        String objeto=objUti.getObjectValueAt(arlDatExpExc, i, j).toString()==null?"":(objUti.getStringValueAt(arlDatExpExc, i, j));
                        

                            if(  ! objeto.toString().equals("") ){
                                if( (j==3) || (j==4) || (j==5) ){
                                    num = Double.parseDouble(String.valueOf(objeto));
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
                            else{
                                s.addCell(new Label(j, i+INT_INC_CAB, ""));
                            }

                    }
    
                   
                   

               }
               
//               s.setColumnView(0, 0);
//               s.removeColumn(100);
              


               
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
        
        
        
        
        
        
        
        
        