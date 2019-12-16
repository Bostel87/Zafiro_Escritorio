/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Contabilidad.ZafCon25;

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
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.write.NumberFormats;

/**
 *
 * @author necronet modificado por Ingrid Lino
 */
public class ZafCon25_01 {

    private File file;
    private JTable table;
    private String nombreTab;
    private Label LblCol;
    private double num;
    private Number numCol;
    private final int INT_INC_CAB = 1;//FILA DESDE DONDE SE COMIENZA A COLOCAR LOS DATOS DE LA TABLA(FILA 0, FILA 1, FILA 2.....)

    private Label lblCab;
    private ZafUtil objUti;
    private final WritableCellFormat dblFormat = new WritableCellFormat(NumberFormats.FLOAT);

    //public ZafCon25_01(JTable table,File file,String nombreTab, String strRuc, String strNumRet, String strFecReg, String strFecEmi, String strTipCom, String strComVta, String strNumAut, String strFecCad, String strBasImp, String strCodRet, String strValRet){
    public ZafCon25_01(JTable table, File file, String nombreTab) {
        try {
            this.file = file;
            this.table = table;
            this.nombreTab = nombreTab;
            objUti = new ZafUtil();
        } catch (Exception e) {
            objUti.mostrarMsgErr_F1(table, e);
        }
    }

    public boolean export() {
        boolean blnRes = false;
        try {
            try {
                if (file.exists()) {
                    file.delete();
                }
                // A partir del objeto File creamos el fichero físicamente
                if (file.createNewFile()) {
                    blnRes = true;
                } else {
                    blnRes = false;
                }
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                blnRes = false;
            }

            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            //WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableWorkbook w = Workbook.createWorkbook(out);
            WritableSheet s = w.createSheet(nombreTab, 0);

            for (int i = 0; i < table.getColumnCount(); i++) {
                lblCab = new Label(i, 0, "" + table.getColumnName(i));
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
//                       Object objeto=table.getValueAt(i, j);
//                       if(objeto!=null){
//                           LblCol = new Label(j, i+INT_INC_CAB, String.valueOf(objeto));
//                           LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
//                           s.addCell(LblCol);
//                       }
//                       else
//                           s.addCell(new Label(j, i+INT_INC_CAB, ""));

                    String objeto = table.getValueAt(i, j) == null ? "" : table.getValueAt(i, j).toString();
                    if (!objeto.toString().equals("")) {
                        if ((j == 10) || (j == 11) || (j == 12) || (j == 22) || (j == 24)) {
                            num = Double.parseDouble(String.valueOf(objeto));
                            numCol = new Number(j, i + INT_INC_CAB, num, dblFormat);
                            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(numCol);
                        } else {
                            LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(objeto));
                            LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(LblCol);
                        }

                    } else {
                        s.addCell(new Label(j, i + INT_INC_CAB, ""));
                    }

                }

            }

//               s.setColumnView(0, 0);
//               s.setColumnView(1, 0);
//               s.setColumnView(2, 0);
//               s.setColumnView(3, 0);
//               s.setColumnView(4, 0);
//               s.setColumnView(5, 0);
//               s.setColumnView(6, 0);
//               s.setColumnView(7, 0);
//               s.setColumnView(8, 0);
//               s.setColumnView(9, 0);
//               s.setColumnView(10, 0);
//               s.setColumnView(11, 0);
//               s.setColumnView(12, 0);
//               s.setColumnView(13, 0);
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

        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }

    public boolean exportGamaRet() {
        boolean blnRes = false;
        try {
            try {
                if (file.exists()) {
                    file.delete();
                }
                // A partir del objeto File creamos el fichero físicamente
                if (file.createNewFile()) {
                    blnRes = true;
                } else {
                    blnRes = false;
                }
            } catch (java.io.IOException ioe) {
                ioe.printStackTrace();
                blnRes = false;
            }

            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            //WritableFont boldRedFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            WritableWorkbook w = Workbook.createWorkbook(out);
            WritableSheet s = w.createSheet(nombreTab, 0);

            for (int i = 0; i < table.getColumnCount(); i++) {
                lblCab = new Label(i, 0, "" + table.getColumnName(i));
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
            }

            for (int i = 0; i < table.getRowCount(); i++) {
                System.out.println("Columna - "+i);
                for (int j = 0; j < table.getColumnCount(); j++) {
                    System.out.println("Fila - "+j);
//                       Object objeto=table.getValueAt(i, j);
//                       if(objeto!=null){
//                           LblCol = new Label(j, i+INT_INC_CAB, String.valueOf(objeto));
//                           LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
//                           s.addCell(LblCol);
//                       }
//                       else
//                           s.addCell(new Label(j, i+INT_INC_CAB, ""));

                    String objeto = table.getValueAt(i, j) == null ? "" : table.getValueAt(i, j).toString();
                    if (!objeto.toString().equals("")) {
                        if ((j == 10) || (j == 11) || (j == 12) || (j == 22) || (j == 24)) {
                            num = Double.parseDouble(String.valueOf(objeto));
                            numCol = new Number(j, i + INT_INC_CAB, num, dblFormat);
                            numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(numCol);
                        } else if ((j == 15) || (j == 19)) {
                            objeto = objeto.replace("-", "");
                            LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(objeto));
                            LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(LblCol);
                        } else if (j == 23) {
                            if (objeto.equals("1")) {
                                objeto = objeto.replace("1", "30B");
                            } else if (objeto.equals("2")) {
                                objeto = objeto.replace("2", "70S");
                            } else if (objeto.equals("3")) {
                                objeto = objeto.replace("3", "100E");
                            } else if (objeto.equals("9")) {
                                objeto = objeto.replace("9", "10B");
                            } else if (objeto.equals("10")) {
                                objeto = objeto.replace("10", "20S");
                            }else if (objeto.equals("727")) {
                                objeto = objeto.replace("727", "50S");
                            }

                            LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(objeto));
                            LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(LblCol);
                        } else if (j == 21) {
                            if (objeto.equals("")) {
                                LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(objeto));
                                LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                                s.addCell(LblCol);
                            } else {
                                Date date = new SimpleDateFormat("dd/MM/yyyy").parse(objeto);
                                String newstring = new SimpleDateFormat("MM/yyyy").format(date);
                                LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(newstring));
                                LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                                s.addCell(LblCol);
                            }
                        } else {
                            LblCol = new Label(j, i + INT_INC_CAB, String.valueOf(objeto));
                            LblCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                            s.addCell(LblCol);
                        }

                    } else {
                        s.addCell(new Label(j, i + INT_INC_CAB, ""));
                    }

                }

            }

//               s.setColumnView(0, 0);
//               s.setColumnView(1, 0);
//               s.setColumnView(2, 0);
//               s.setColumnView(3, 0);
//               s.setColumnView(4, 0);
//               s.setColumnView(5, 0);
//               s.setColumnView(6, 0);
//               s.setColumnView(7, 0);
//               s.setColumnView(8, 0);
//               s.setColumnView(9, 0);
//               s.setColumnView(10, 0);
//               s.setColumnView(11, 0);
//               s.setColumnView(12, 0);
//               s.setColumnView(13, 0);
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
        } catch (Exception e) {
            blnRes = false;
        }
        return blnRes;
    }
}
