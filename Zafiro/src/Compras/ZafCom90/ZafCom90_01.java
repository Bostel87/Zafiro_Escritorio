/**
 * Realizado por Ingrid Lino.
 * 09/Marzo/2016
 * v0.1
 */
package Compras.ZafCom90;
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
public class ZafCom90_01 {
	 
        private File file;
        
        private String nombreTab;
        private Label LblCol;
        private final int INT_INC_CAB=3;//FILA DESDE DONDE SE COMIENZA A COLOCAR LOS DATOS DE LA TABLA(FILA 0, FILA 1, FILA 2.....)

        private Label lblCab;
        private ZafUtil objUti;
        private double num;
        private Number numCol;
        private final WritableCellFormat dblFormat = new WritableCellFormat (NumberFormats.FLOAT);

        private ArrayList arlDatExpExc;
        private final int INT_ARL_EXP_EXC_DAT_LIN=0;
        private final int INT_ARL_EXP_EXC_DAT_COD_MAE=1;
        private final int INT_ARL_EXP_EXC_DAT_COD_SIS=2;
        private final int INT_ARL_EXP_EXC_DAT_COD_ALT=3;
        private final int INT_ARL_EXP_EXC_DAT_COD_ALT_DOS=4;
        private final int INT_ARL_EXP_EXC_DAT_NOM_ITM=5;
        private final int INT_ARL_EXP_EXC_DAT_CHK_SER=6;
        private final int INT_ARL_EXP_EXC_DAT_COD_UNI=7;
        private final int INT_ARL_EXP_EXC_DAT_DEC_UNI=8;
        private final int INT_ARL_EXP_EXC_DAT_BUT_UNI=9;
        private final int INT_ARL_EXP_EXC_DAT_PES_KGR=10;
        private final int INT_ARL_EXP_EXC_DAT_PES_KGR_AUX=11;
        private final int INT_ARL_EXP_EXC_DAT_STK_CON=12;
        
        private int intNumColIniIngImp, intNumColFinIngImp;
        private int intNumColIniCosUni, intNumColFinCosUni;
        private int intNumColIniPedEmb, intNumColFinPedEmb;
        private int intNumColIniNotPed, intNumColFinNotPed;
        private int intNumColIniMarNotPed, intNumColFinMarNotPed;
        private int intNumColIniPreReaMar, intNumColFinPreReaMar;    
        private int intNumColIniFac, intNumColFinFac;
        private int intNumColIniMarFac, intNumColFinMarFac;
        private int intNumColIniPre, intNumColFinPre;
        private int intNumColIniCanMarPreLisNew, intNumColFinCanMarPreLisNew;
        private boolean blnKil;
        private boolean blnFac;
        
        //Ingresos por Importación: Pedidos llegados
        private ArrayList arlDatIngImp;    
        private final int INT_ARL_ING_IMP_COD_EMP=0;
        private final int INT_ARL_ING_IMP_COD_LOC=1;
        private final int INT_ARL_ING_IMP_COD_TIP_DOC=2;
        private final int INT_ARL_ING_IMP_COD_DOC=3;
        private final int INT_ARL_ING_IMP_NUM=4;
        private final int INT_ARL_ING_IMP_COL=5;
        private final int INT_ARL_ING_IMP_EST_COL=6;
        private final int INT_ARL_ING_IMP_FEC=7;
        private final int INT_ARL_ING_IMP_COD_EMP_PED_EMB=8;
        private final int INT_ARL_ING_IMP_COD_LOC_PED_EMB=9;
        private final int INT_ARL_ING_IMP_COD_TIP_DOC_PED_EMB=10;
        private final int INT_ARL_ING_IMP_COD_DOC_PED_EMB=11;
        
        private ArrayList arlDatPedEmb;
        private final int INT_ARL_PED_EMB_COD_EMP=0;
        private final int INT_ARL_PED_EMB_COD_LOC=1;
        private final int INT_ARL_PED_EMB_COD_TIP_DOC=2;
        private final int INT_ARL_PED_EMB_COD_DOC=3;
        private final int INT_ARL_PED_EMB_NUM=4;
        private final int INT_ARL_PED_EMB_COL=5;
        private final int INT_ARL_PED_EMB_EST_COL=6;
        private final int INT_ARL_PED_EMB_FEC=7;
        
        private ArrayList arlDatNotPed;    
        private final int INT_ARL_NOT_PED_COD_EMP=0;
        private final int INT_ARL_NOT_PED_COD_LOC=1;
        private final int INT_ARL_NOT_PED_COD_TIP_DOC=2;
        private final int INT_ARL_NOT_PED_COD_DOC=3;
        private final int INT_ARL_NOT_PED_NUM=4;
        private final int INT_ARL_NOT_PED_COL=5;
        private final int INT_ARL_NOT_PED_EST_COL=6;
        private final int INT_ARL_NOT_PED_FEC=7;
        private final int INT_ARL_NOT_PED_COD_EMP_PED_EMB=8;
        private final int INT_ARL_NOT_PED_COD_LOC_PED_EMB=9;
        private final int INT_ARL_NOT_PED_COD_TIP_DOC_PED_EMB=10;
        private final int INT_ARL_NOT_PED_COD_DOC_PED_EMB=11;
        
        
    
        public ZafCom90_01(ArrayList arreglo,File file, String nombreTab
                , int numColIniIngImp, int numColFinIngImp
                , int numColIniCosUni, int numColFinCosUni
                , int numColIniPedEmb, int numColFinPedEmb
                , int numColIniNotPed, int numColFinNotPed
                , int numColIniMarNotPed, int numColFinMarNotPed
                , int numColIniPreReaMar, int numColFinPreReaMar
                , int numColIniFac, int numColFinFac
                , int numColIniMarFac, int numColFinMarFac
                , int numColIniPre, int numColFinPre
                , int numColIniCanMarPreLisNew, int numColFinCanMarPreLisNew
                , ArrayList arlDatIngImpRef, ArrayList arlDatPedEmbRef, ArrayList arlDatNotPedRef
                , boolean blnKilRef, boolean blnFacRef
                
        ){
            try{
                this.file=file;
                this.arlDatExpExc=arreglo;
                this.nombreTab=nombreTab;
                objUti=new ZafUtil();
                
                intNumColIniIngImp=numColIniIngImp;
                intNumColFinIngImp=numColFinIngImp;
                intNumColIniCosUni=numColIniCosUni;
                intNumColFinCosUni=numColFinCosUni;
                intNumColIniPedEmb=numColIniPedEmb;
                intNumColFinPedEmb=numColFinPedEmb;
                intNumColIniNotPed=numColIniNotPed;
                intNumColFinNotPed=numColFinNotPed;
                intNumColIniMarNotPed=numColIniMarNotPed;
                intNumColFinMarNotPed=numColFinMarNotPed;
                intNumColIniPreReaMar=numColIniPreReaMar;
                intNumColFinPreReaMar=numColFinPreReaMar;    
                intNumColIniFac=numColIniFac;
                intNumColFinFac=numColFinFac;
                intNumColIniMarFac=numColIniMarFac;
                intNumColFinMarFac=numColFinMarFac;
                intNumColIniPre=numColIniPre;
                intNumColFinPre=numColFinPre;
                intNumColIniCanMarPreLisNew=numColIniCanMarPreLisNew;
                intNumColFinCanMarPreLisNew=numColFinCanMarPreLisNew;
                arlDatIngImp=arlDatIngImpRef;
                arlDatPedEmb=arlDatPedEmbRef;
                arlDatNotPed=arlDatNotPedRef;
                blnKil=blnKilRef;
                blnFac=blnFacRef;
                
            }
            catch(Exception e){
                //objUti.mostrarMsgErr_F1(this, e);
                System.out.println("Error ZafCom90_01: " + e);
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
                lblCab = new Label(0, 2, "Lín.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(1, 2, "Cód.Mae.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(2, 2, "Cód.Sis.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);                
                lblCab = new Label(3, 2, "Cód.Alt.Itm.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(4, 2, "Cód.Let.Itm.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
               
                lblCab = new Label(5, 2, "Item");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                lblCab = new Label(6, 2, "Sel.Ser.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(7, 2, "Cód.Uni.Med.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(8, 2, "Uni.Med.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(9, 2, "Bot.Uni.Med.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                lblCab = new Label(10, 2, "Peso(kg).");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(11, 2, "Peso(kg).Aux.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                lblCab = new Label(12, 2, "Stk.");
                lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                s.addCell(lblCab);
                
                
                //dinamicas
                int k=13;
                int l=0;
                String strNomTmp="";
                for(int j=intNumColIniIngImp; j<intNumColFinIngImp;j++){
                    lblCab = new Label(k, 0, (j==intNumColIniIngImp?"Ped.Lle.":null));
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 1, "" + objUti.getStringValueAt(arlDatIngImp, l, INT_ARL_ING_IMP_NUM) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 2, "" + objUti.getStringValueAt(arlDatIngImp, l, INT_ARL_ING_IMP_FEC) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    l++;
                    System.out.println("k: " + k);
                }
                for(int j=intNumColIniCosUni; j<intNumColFinCosUni;j++){
                    lblCab = new Label(k, 2, "Cos.Uni.");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                l=0;
                for(int j=intNumColIniPedEmb; j<intNumColFinPedEmb;j++){
                    lblCab = new Label(k, 0, (j==intNumColIniPedEmb?"Ped. Emb.":null));
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 1, "" + objUti.getStringValueAt(arlDatPedEmb, l, INT_ARL_PED_EMB_NUM) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 2, "" + objUti.getStringValueAt(arlDatPedEmb, l, INT_ARL_PED_EMB_FEC) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    l++;
                    System.out.println("k: " + k);
                }
                l=0;
                for(int j=intNumColIniNotPed; j<intNumColFinNotPed;j++){
                    lblCab = new Label(k, 0, (j==intNumColIniNotPed?"Ped.Por.Emb.":null));
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 1, "" + objUti.getStringValueAt(arlDatNotPed, l, INT_ARL_NOT_PED_NUM) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 2, "" + objUti.getStringValueAt(arlDatNotPed, l, INT_ARL_NOT_PED_FEC) + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    l++;
                    System.out.println("k: " + k);
                }
                for(int j=intNumColIniMarNotPed; j<intNumColFinMarNotPed;j++){
                    lblCab = new Label(k, 2, "Mar.");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                strNomTmp="";
                System.out.println("intNumColIniPreReaMar: " + intNumColIniPreReaMar);
                System.out.println("intNumColFinPreReaMar: " + intNumColFinPreReaMar);
                for(int j=intNumColIniPreReaMar; j<intNumColFinPreReaMar;j++){
                    strNomTmp=(j==intNumColIniPreReaMar?"Pre.Vta.":(j==(intNumColIniPreReaMar+1)?"Pre.Vta.Rea.Kil.(IVA)":(j==(intNumColIniPreReaMar+2)?"Mar.Pre.Vta.Rea.":"Mar.Pre.Vta.Min.")));
                    lblCab = new Label(k, 1, (j==intNumColIniPreReaMar?"Precio de Venta":null));
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 2, "" + strNomTmp + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                strNomTmp="";
                System.out.println("intNumColIniFac: " + intNumColIniFac);
                System.out.println("intNumColFinFac: " + intNumColFinFac);
                for(int j=intNumColIniFac; j<intNumColFinFac;j++){
                    strNomTmp=(j==intNumColIniFac?"Fac.Cos.":"Pre.Vta.Fac.");
                    lblCab = new Label(k, 1, (j==intNumColIniFac?"Datos con factor":null));
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    lblCab = new Label(k, 2, "" + strNomTmp + "");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                strNomTmp="";
                System.out.println("intNumColIniMarFac: " + intNumColIniMarFac);
                System.out.println("intNumColFinMarFac: " + intNumColFinMarFac);
                for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
                    lblCab = new Label(k, 2, "Mar.Fac.");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                
                System.out.println("intNumColIniPre: " + intNumColIniPre);
                for(int j=intNumColIniPre; j<intNumColFinPre;j++){
                    //lblCab = new Label(k, 2, "Pre.Vta.Obj.Kil.(IVA)");
                    strNomTmp=(j==intNumColIniPre?"Pre.Vta.Obj.Kil.(IVA)":"Pre.Vta.Lis.Cal.");
                    lblCab = new Label(k, 2, strNomTmp);
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                strNomTmp="";
                l=0;
                System.out.println("intNumColIniCanMarPreLisNew: " + intNumColIniCanMarPreLisNew);
                for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
                    lblCab = new Label(k, 2, "Mar.Pre.Lis.");
                    //strNomTmp=(j==intNumColIniCanMarPreLisNew?"Pre.Vta.Lis.Cal.":"Mar.Pre.Lis.");
                    lblCab.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD)));
                    s.addCell(lblCab);
                    k++;
                    System.out.println("k: " + k);
                }
                

                
                
               //datos
               for(int i=0;i<arlDatExpExc.size();i++){
//                    System.out.println("***i: " + i);
                    for(int j=INT_ARL_EXP_EXC_DAT_LIN;j<intNumColFinCanMarPreLisNew;j++){
//                        System.out.println("j: " + j);
                        String objeto=objUti.getObjectValueAt(arlDatExpExc, i, j).toString()==null?"":(objUti.getStringValueAt(arlDatExpExc, i, j));                        
                            //System.out.println("objeto: " + objeto);
                            if(  ! objeto.toString().equals("") ){
                                if( (j==10) || (j==11) || (j==12) ){
                                    num = Double.parseDouble(String.valueOf(objeto));
                                    numCol = new Number(j, i+INT_INC_CAB, num, dblFormat);
                                    numCol.setCellFormat(new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD)));
                                    s.addCell(numCol);
                                }
                                else if( (j>=intNumColIniIngImp) && (j<intNumColFinCanMarPreLisNew)  ){
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
               //ocultar colummas
               s.setColumnView(0, 0);//linea
               s.setColumnView(1, 0);//codigo maestro
               s.setColumnView(2, 0);//codigo sistema
               s.setColumnView(6, 0);//servicio
               s.setColumnView(7, 0);//codigo de unidad de medida
               s.setColumnView(9, 0);//boton
               s.setColumnView(11, 0);//peso auxiliar
               if(!blnKil){
                   //ocultar
//                   for(int j=intNumColIniPre; j<intNumColFinPre;j++){
//                       s.setColumnView(j, 0); //Pre.Vta.Obj.Kil.(IVA) 
//                   }
//                   for(int j=intNumColIniCanMarPreLisNew; j<intNumColFinCanMarPreLisNew;j++){
//                       s.setColumnView(j, 0); //"Pre.Vta.Lis.Cal."          "Mar.Pre.Lis."
//                   }
                   //remover
                   for(int j=(intNumColFinCanMarPreLisNew-1); j>=intNumColIniCanMarPreLisNew;j--){ 
                       s.removeColumn(j); //"Pre.Vta.Lis.Cal."          "Mar.Pre.Lis."
                   }
                   for(int j=(intNumColFinPre-1); j>=intNumColIniPre;j--){
                       s.removeColumn(j); //Pre.Vta.Obj.Kil.(IVA) 
                   }

               }
               if(!blnFac){
                   //ocultar
//                   for(int j=intNumColIniFac; j<intNumColFinFac;j++){
//                       s.setColumnView(j, 0); //"Fac.Cos."            "Pre.Vta.Fac."
//                   }
//                   for(int j=intNumColIniMarFac; j<intNumColFinMarFac;j++){
//                       s.setColumnView(j, 0); //"Mar.Fac."
//                   }
                   //remover
                   for(int j=(intNumColFinMarFac-1); j>=intNumColIniMarFac;j--){
                       s.removeColumn(j); //"Mar.Fac."
                   }
                   for(int j=(intNumColFinFac-1); j>=intNumColIniFac;j--){
                       s.removeColumn(j); //"Fac.Cos."            "Pre.Vta.Fac."
                   }

               }
               
               //remover columnas
               
               w.write();
               w.close();
               out.close();
               
               
               
	     }
             catch (Exception e){
                 System.out.println("Exception cargar datos:" + e);
                 blnRes=false;
	     }
          return blnRes;
         }
         
  
            
         
         
         
         
         
}
        
        
        
        
        
        
        
        
        