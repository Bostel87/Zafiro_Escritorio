/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon15;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
import jxl.write.Number;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
//import jxl.format.Alignment;
import Librerias.ZafUtil.ZafUtil;
import jxl.write.NumberFormats;

/**
 *
 * @author necronet
 * modificado por Ingrid Lino
 */

public class ZafExpExc 
{
        private File file;
        private JTable table;
        private String nombreTab;
        private WritableFont fuente;
        private Number numCol;
        private Label LblCol;
        private double num;
        private final int INT_INC_CAB=6;
        private String strNomEmp;
        private String strNumCta;
        private String strNomCta;
        private String strFecDes;
        private String strFecHas;
        private double dblSalAct;
        private double dblSalAnt;
        private double dblSalAcu;
        private Label lblCab;
        private ZafUtil objUti;
        private final WritableCellFormat doubleFormat = new WritableCellFormat (NumberFormats.FLOAT);
        private org.apache.poi.poifs.filesystem.POIFSFileSystem fs;
        
	public ZafExpExc(JTable table,File file,String nombreTab, String nomEmpresa, String numCta, String nomCta, String fecDes, String fecHas, double salAct, double salAnt, double salAcu)
        {
            try
            {
                this.file=file;
                this.table=table;
                this.nombreTab=nombreTab;
                strNomEmp=nomEmpresa;
                strNumCta=numCta;
                strNomCta=nomCta;
                strFecDes=fecDes;
                strFecHas=fecHas;
                dblSalAct=salAct;
                dblSalAnt=salAnt;
                dblSalAcu=salAcu;
                objUti=new ZafUtil();

                System.out.println("EN CONSTRUCTOR ANTERIOR: " + dblSalAnt);
            }
            catch(Exception e){
                objUti.mostrarMsgErr_F1(table, e);
            }
	 }
        
        
         public boolean export()
         {
             boolean blnRes=false;
             try
             {
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
               
               //para la cabecera
               //nombre de empresa
               lblCab = new Label(6, 0, strNomEmp);
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD)));
               s.addCell(lblCab);
               //titulo de cuenta
               lblCab = new Label(1, 1, "CUENTA:   " + strNumCta);
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
               s.addCell(lblCab);
               
               //titulo de nombre de cuenta
               lblCab = new Label(6, 1, "NOMBRE:   " + strNomCta);
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
               s.addCell(lblCab);
               
               //titulo de fecha desde
               lblCab = new Label(1, 2, "FECHA DESDE:   " + strFecDes);
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
               s.addCell(lblCab);
               
               //titulo de fecha hasta
               lblCab = new Label(6, 2, "FECHA HASTA:   " + strFecHas);
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
               s.addCell(lblCab);
               
               //titulo de saldo anterior
               lblCab = new Label(6, 3, "SALDO ANTERIOR: ");
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
               s.addCell(lblCab);
               //valor de saldo anterior
               num = dblSalAnt;
               
               numCol = new Number(8, 3, num, doubleFormat);
               numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
               s.addCell(numCol);
               
               for(int i=0;i<table.getColumnCount();i++){
                   Label lbl=new Label((i), (INT_INC_CAB-1), table.getModel().getColumnName(i));
                   lbl.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
                   s.addCell(lbl);        
               }

               for(int i=0;i<table.getRowCount();i++){
                   for(int j=0;j<table.getColumnCount();j++){
                       Object objeto=table.getValueAt(i, j);
                       if(objeto!=null){
                           if( (j==8) || (j==9)  || (j==10)   ){
                               num = Double.parseDouble(String.valueOf(objeto));
                               numCol = new Number(j, i+INT_INC_CAB, num, doubleFormat);
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
               
               
               //titulo de saldo actual
               lblCab = new Label(6, (table.getRowCount()+1+INT_INC_CAB), "SALDO ACTUAL: ");
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
               s.addCell(lblCab);
               //valor de saldo actual
               num = dblSalAct;
               numCol = new Number(8, (table.getRowCount()+1+INT_INC_CAB), num, doubleFormat);
               numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
               s.addCell(numCol);

               //titulo de saldo acumulado
               lblCab = new Label(6, (table.getRowCount()+2+INT_INC_CAB), "SALDO ACUMULADO: ");
               lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
               s.addCell(lblCab);
               //valor de saldo acumulado
               num = dblSalAcu;
               numCol = new Number(8, (table.getRowCount()+2+INT_INC_CAB), num, doubleFormat);
               numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD)));
               s.addCell(numCol);               
               
               s.setColumnView(0, 0);
               s.setColumnView(2, 11);
               s.setColumnView(3, 0);
               s.setColumnView(4, 10);
               s.setColumnView(5, 8);
               s.setColumnView(6, 8);
               s.setColumnView(7, 30);
               s.setColumnView(8, 12);
               s.setColumnView(9, 12);
               s.setColumnView(10, 0);

               //s.setPageSetup(PageOrientation.PORTRAIT.PORTRAIT , PaperSize.A4, 0.7, 0.7);
               
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
        
        
        
        
        
        
        
        
        