
package Importaciones.ZafImp32;
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
import Librerias.ZafUtil.ZafUtil;
import jxl.write.NumberFormats;

/**
 *
 * @author necronet
 * modificado por Ingrid Lino
 */
public class ZafImp32_03
{
    private File file;
    private JTable table;
    private String nombreTab;
    private Label LblCol;
    private double num;
    private Number numCol;
    private final int INT_INC_CAB=1;//FILA DESDE DONDE SE COMIENZA A COLOCAR LOS DATOS DE LA TABLA(FILA 0, FILA 1, FILA 2.....)

    private Label lblCab;
    private ZafUtil objUti;
    private final WritableCellFormat dblFormat = new WritableCellFormat (NumberFormats.FLOAT);

    public ZafImp32_03(JTable table,File file,String nombreTab){
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
                        if( (j==10) || (j==11) || (j==12) || (j==22) || (j==24)  ){
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
                    else
                        s.addCell(new Label(j, i+INT_INC_CAB, ""));
                }
            }

            s.removeColumn(13);
            s.removeColumn(12);
            s.removeColumn(11);
            s.removeColumn(10);
            s.removeColumn(9);
            s.removeColumn(8);
            s.removeColumn(7);
            s.removeColumn(6);
            s.removeColumn(5);
            s.removeColumn(4);
            s.removeColumn(3);
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
        
        
        
        
        
        
        
        
        