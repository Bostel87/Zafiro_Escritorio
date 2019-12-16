/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ZafCxC11_01.java
 *
 * Created on Sep 21, 2009, 4:16:06 PM
 */

package CxC.ZafCxC11;

import Librerias.ZafParSis.ZafParSis;
import Librerias.ZafUtil.ZafUtil;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Vector;
import java.sql.DriverManager;
import java.util.ArrayList;

/**
 *
 * @author jayapata
 */
public class ZafCxC11_01 extends javax.swing.JDialog {
   ZafParSis objZafParSis;
   ZafUtil objUti;
   private Librerias.ZafTblUti.ZafTblMod.ZafTblMod objTblMod;
   private Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk objTblCelRenChk;
   private Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk objTblCelEdiChk;
   private Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl objTblCelRenLbl;
   private Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt objTblCelEdiTxt;
   private ZafMouMotAda objMouMotAda;

   private ZafThreadGUI objThrGUI;
   
    final int INT_TBL_LIN  = 0;  // NUMERO DE LINEA
    final int INT_TBL_CHK  = 1;  // SELECCION DE LA FACTURA
    final int INT_TBL_COEMP= 2; // CODIGO DE LA EMPRESA
    final int INT_TBL_COLOC= 3; // CODIGO DEL LOCAL
    final int INT_TBL_CODTIP=4;  // CODIGO DEL TIPO DOCUMENTO
    final int INT_TBL_CODDOC=5;  // CODIGO DEL DOCUMENTO
    final int INT_TBL_CODREG=6;  // CODIGO REGISTRO
    final int INT_TBL_DCTDOC=7;  // DESCRIPCION DEL TIPO DE DOCUMENTO
    final int INT_TBL_NUMDOC=8;  // NOMERO DEL DOCUMENTO
    final int INT_TBL_FECDOC=9;  // FECHA DEL DOCUMENTO
    final int INT_TBL_DIACRE=10;  // DIA DE GRACIA DEL PAGO
    final int INT_TBL_FECVEN=11;  // FECHA DE VENCIMIENTO
    final int INT_TBL_POR_RET=12; // PORCENTAJE DE RETENCION
    final int INT_TBL_VALDOC=13;  // VALOR DEL PAGO
    final int INT_TBL_VALABO=14;  // VALOR ABONADO
    final int INT_TBL_VALPEN=15; // VALOR PENDIENTE DE PAGO
    final int INT_TBL_VALAPL=16;  // VALOR QUE SE APLICARA
    final int INT_TBL_NUMCHQ=17;  // NUMERO DE CHEQUE
    final int INT_TBL_MONCHQ=18;  //  MONTO DEL CHEQUE
   

    String strSQL="";
  
    boolean blnEst=false;
    boolean blnAcepta=false;

    private String Str_RegSel[];
    String strFacSel="";
    String strNumDocSel="";
    BigDecimal dblMonChq=new BigDecimal(BigInteger.ZERO);
    BigDecimal dblMinAjuCenAut=new BigDecimal(BigInteger.ZERO);
    BigDecimal dblMaxAjuCenAut=new BigDecimal(BigInteger.ZERO);


    /** Creates new form ZafCom58_01 */
    public ZafCxC11_01(java.awt.Frame parent, boolean modal, ZafParSis objParsis ,String strSql, BigDecimal dblMonChqTbl, BigDecimal dblMinAjuCenAutRec, BigDecimal dblMaxAjuCenAutRec  ) {
       super(parent, modal);
       try{
          this.objZafParSis = (Librerias.ZafParSis.ZafParSis) objParsis.clone();
          
          strSQL=strSql;
           
          initComponents();
           this.setTitle(""+objZafParSis.getNombreMenu() );
           lblTit.setText(""+objZafParSis.getNombreMenu() );
           objUti = new ZafUtil();

           dblMonChq=dblMonChqTbl;
           dblMinAjuCenAut=dblMinAjuCenAutRec;
           dblMaxAjuCenAut=dblMaxAjuCenAutRec;

           configurarForm();

           Str_RegSel = new String[3];

           
        }catch (CloneNotSupportedException e){  objUti.mostrarMsgErr_F1(this, e); }
    }


public boolean acepta(){
    return blnEst;
}


/**
 * confirgura la tabla donde se presenta la informacion de las facturas pendientes de pago. 
 * @return true si todo esta bien   false si hay algun error
 */
private boolean configurarForm(){
 boolean blnres=false;

    Vector vecCab=new Vector();    //Almacena las cabeceras
    vecCab.clear();

    vecCab.add(INT_TBL_LIN,"");
    vecCab.add(INT_TBL_CHK," ");
    vecCab.add(INT_TBL_COEMP,"Cód.Emp");
    vecCab.add(INT_TBL_COLOC,"Cód.Loc");
    vecCab.add(INT_TBL_CODTIP,"Cód.TipDoc");
    vecCab.add(INT_TBL_CODDOC,"Cód.Doc");
    vecCab.add(INT_TBL_CODREG,"Cód.Reg");
    vecCab.add(INT_TBL_DCTDOC,"Des.Cor");
    vecCab.add(INT_TBL_NUMDOC,"Num.Doc");
    vecCab.add(INT_TBL_FECDOC,"Fec.Doc");

    if(objZafParSis.getCodigoMenu()==1749) vecCab.add(INT_TBL_DIACRE,"Por.Ret");
    else  vecCab.add(INT_TBL_DIACRE,"Día.Cre");

    vecCab.add(INT_TBL_FECVEN,"Fec.Ven");
    vecCab.add(INT_TBL_POR_RET,"Por.Ret.");
    vecCab.add(INT_TBL_VALDOC,"Val.Doc");
    vecCab.add(INT_TBL_VALABO,"Val.Abo");
    vecCab.add(INT_TBL_VALPEN,"Val.Pen");
    vecCab.add(INT_TBL_VALAPL,"Val.Apl");
    vecCab.add(INT_TBL_NUMCHQ,"Num.Chq");
    vecCab.add(INT_TBL_MONCHQ,"Mon.Chq");

    objTblMod=new Librerias.ZafTblUti.ZafTblMod.ZafTblMod();
    objTblMod.setHeader(vecCab);
    tblDat.setModel(objTblMod);

    //Configurar JTable: Establecer la fila de cabecera.
    new Librerias.ZafColNumerada.ZafColNumerada(tblDat, INT_TBL_LIN);

    //Configurar JTable: Mostrar ToolTipText en la cabecera de las columnas.
    objMouMotAda=new ZafMouMotAda();
    tblDat.getTableHeader().addMouseMotionListener(objMouMotAda);


     //Configurar ZafTblMod: Establecer el tipo de dato de las columnas.
    objTblMod.setColumnDataType(INT_TBL_VALDOC, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_VALABO, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_VALPEN, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_VALAPL, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_MONCHQ, objTblMod.INT_COL_DBL, new Integer(0), null);
    objTblMod.setColumnDataType(INT_TBL_DIACRE, objTblMod.INT_COL_DBL, new Integer(0), null);



    tblDat.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    javax.swing.table.TableColumnModel tcmAux=tblDat.getColumnModel();
    tblDat.getTableHeader().setReorderingAllowed(false);
    //Tamaño de las celdas
    tcmAux.getColumn(INT_TBL_LIN).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_CHK).setPreferredWidth(25);
    tcmAux.getColumn(INT_TBL_DCTDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_NUMDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_FECDOC).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_DIACRE).setPreferredWidth(40);
    tcmAux.getColumn(INT_TBL_FECVEN).setPreferredWidth(80);
    tcmAux.getColumn(INT_TBL_POR_RET).setPreferredWidth(50);
    tcmAux.getColumn(INT_TBL_VALDOC).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALABO).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALPEN).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_VALAPL).setPreferredWidth(60);
    tcmAux.getColumn(INT_TBL_MONCHQ).setPreferredWidth(60);


    ArrayList arlColHid=new ArrayList();
    arlColHid.add(""+INT_TBL_COEMP);
    arlColHid.add(""+INT_TBL_COLOC);
    arlColHid.add(""+INT_TBL_CODTIP);
    arlColHid.add(""+INT_TBL_CODDOC);
    arlColHid.add(""+INT_TBL_CODREG);
    arlColHid.add(""+INT_TBL_NUMCHQ);
    arlColHid.add(""+INT_TBL_MONCHQ);
    objTblMod.setSystemHiddenColumns(arlColHid, tblDat);
    arlColHid=null;


    ////*****tblAnMe.getTableHeader().addMouseMotionListener(objTblMod);
    //Configurar JTable: Establecer columnas editables.
    Vector vecAux=new Vector();
     vecAux.add("" + INT_TBL_CHK);
    // if(!(objZafParSis.getCodigoMenu()==1749))
        vecAux.add("" + INT_TBL_VALAPL);
    objTblMod.setColumnasEditables(vecAux);
    vecAux=null;
    //Configurar JTable: Editor de la tabla.
    new Librerias.ZafTblUti.ZafTblEdi.ZafTblEdi(tblDat);
  
    objTblCelRenChk = new Librerias.ZafTblUti.ZafTblCelRenChk.ZafTblCelRenChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellRenderer(objTblCelRenChk);
    objTblCelRenChk=null;


    objTblCelEdiChk = new Librerias.ZafTblUti.ZafTblCelEdiChk.ZafTblCelEdiChk();
    tcmAux.getColumn(INT_TBL_CHK).setCellEditor(objTblCelEdiChk);
    objTblCelEdiChk.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){

        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
           int intNunFil = tblDat.getSelectedRow();
           
           if(tblDat.getValueAt(intNunFil, INT_TBL_CHK).toString().equals("true")){
            String strValPen = (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString()));
            tblDat.setValueAt(strValPen, intNunFil, INT_TBL_VALAPL);
           }else{
                tblDat.setValueAt("0", intNunFil, INT_TBL_VALAPL);
           }

            subTot();
        }
    });


    objTblCelEdiTxt=new Librerias.ZafTblUti.ZafTblCelEdiTxt.ZafTblCelEdiTxt(tblDat);
    tcmAux.getColumn(INT_TBL_VALAPL).setCellEditor(objTblCelEdiTxt);
    objTblCelEdiTxt.addTableEditorListener(new Librerias.ZafTblUti.ZafTblEvt.ZafTableAdapter() {
        public void beforeEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt){
            int intNunFil = tblDat.getSelectedRow();

           
        }
        public void afterEdit(Librerias.ZafTblUti.ZafTblEvt.ZafTableEvent evt) {
          int intNunFil = tblDat.getSelectedRow();

            BigDecimal dblValApl = objUti.redondearBigDecimal( (tblDat.getValueAt(intNunFil, INT_TBL_VALAPL)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALAPL).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALAPL).toString())), 2);
            BigDecimal dblValPen = objUti.redondearBigDecimal( (tblDat.getValueAt(intNunFil, INT_TBL_VALPEN)==null?"0":(tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).equals("")?"0":tblDat.getValueAt(intNunFil, INT_TBL_VALPEN).toString())), 2);
          
           if(objZafParSis.getCodigoMenu()==1749){

              BigDecimal dblDif=objUti.redondearBigDecimal((dblValPen.subtract(dblValApl)), 4);
               if( !(( dblDif.compareTo(dblMinAjuCenAut)>=0 ) && ( dblDif.compareTo(dblMaxAjuCenAut)<=0  ))   ){
                   MensajeInf("EL VALOR APLICADO ESTA FUERA DE RANGO DE AJUSTE DE CENTAVOS.");
                   tblDat.setValueAt("", intNunFil, INT_TBL_VALAPL );
                   tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHK );
               }else{
                  if(dblValApl.compareTo(new BigDecimal(BigInteger.ZERO))>0 )
                      tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHK );
                  else
                      tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHK );
               }

               
           }else{
            if(dblValApl.compareTo(dblValPen)>0 ){
                MensajeInf("EL VALOR QUE ESTA APLICANDO ES MAYOR AL VALOR PENDIENTE..");
                tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHK );
                
                //MensajeInf("NO PUEDE SER MAYOR EL VALOR APLICADO CON EL VALOR PENDIENTE..");
                //tblDat.setValueAt("", intNunFil, INT_TBL_VALAPL );
            }else{  
                
                if(dblValApl.compareTo(new BigDecimal(BigInteger.ZERO)) > 0 )
                    tblDat.setValueAt( new Boolean(true), intNunFil, INT_TBL_CHK );
                else
                    tblDat.setValueAt( new Boolean(false), intNunFil, INT_TBL_CHK );
            }
           }

          subTot();
         }
    });

    
     objTblCelRenLbl=new Librerias.ZafTblUti.ZafTblCelRenLbl.ZafTblCelRenLbl();
     objTblCelRenLbl.setBackground(objZafParSis.getColorCamposObligatorios());
     objTblCelRenLbl.setHorizontalAlignment(javax.swing.JLabel.RIGHT);
     objTblCelRenLbl.setTipoFormato(objTblCelRenLbl.INT_FOR_NUM);
     objTblCelRenLbl.setFormatoNumerico(objZafParSis.getFormatoNumero(),false,true);
     tcmAux.getColumn( INT_TBL_POR_RET).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALDOC ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALABO ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALPEN ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_VALAPL ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_MONCHQ ).setCellRenderer(objTblCelRenLbl);
     tcmAux.getColumn( INT_TBL_DIACRE ).setCellRenderer(objTblCelRenLbl);
     objTblCelRenLbl=null;

    objTblMod.setModoOperacion(objTblMod.INT_TBL_EDI);

     return blnres;
}


/**
 * calcula el total de las facturas seleccionados
 */
private void subTot(){
   BigDecimal dlbValTot=new BigDecimal(BigInteger.ZERO);

   for(int i=0; i<tblDat.getRowCount(); i++){
     if(tblDat.getValueAt(i, INT_TBL_CHK).toString().equals("true")){

          String strValPen = (tblDat.getValueAt(i, INT_TBL_VALAPL)==null?"0":(tblDat.getValueAt(i, INT_TBL_VALAPL).equals("")?"0":tblDat.getValueAt(i, INT_TBL_VALAPL).toString()));
          dlbValTot=dlbValTot.add(new BigDecimal(strValPen));

     }}

   if( dlbValTot.compareTo(dblMonChq) >0  ){
      this.MensajeInf("HA SOBREPASADO EL VALOR DEL CHEQUE CORRIGA E INTENTELO DE NUEVO ");
   }

   dlbValTot =  objUti.redondearBigDecimal(dlbValTot, 3 );
   txtValTotFac.setText(""+dlbValTot);
}



private void cargarDat(){

   if (objThrGUI==null)
    {
        objThrGUI=new ZafThreadGUI();
        objThrGUI.start();
    }

}

private class ZafThreadGUI extends Thread
{
 public void run(){

    lblMsgSis.setText("Obteniendo datos...");
    pgrSis.setIndeterminate(true);

    cargarDatCla(strSQL);


   objThrGUI=null;
}
}


/**
 * Se encarga de cargar la informacion en la tabla
 * @param strSql Query que recibe para cargar la tabla
 * @return  true si esta correcto y false  si hay algun error 
 */
private boolean cargarDatCla(String strSql){
  boolean blnRes=false;
  java.sql.Connection conn;
  java.sql.Statement stmLoc;
  java.sql.ResultSet rstLoc;
  double dlbValTot=0;
  BigDecimal bdeAux = new BigDecimal("0");
  
   try{
      conn=DriverManager.getConnection(objZafParSis.getStringConexion(), objZafParSis.getUsuarioBaseDatos(), objZafParSis.getClaveBaseDatos() );
      if(conn!=null){
           stmLoc=conn.createStatement();
           java.util.Vector vecData = new java.util.Vector();


         //  System.out.println("--> "+ strSql );

           rstLoc=stmLoc.executeQuery(strSql);
           while(rstLoc.next()){


               java.util.Vector vecReg = new java.util.Vector();
                 vecReg.add(INT_TBL_LIN, "");
                 vecReg.add(INT_TBL_CHK,  new Boolean( (rstLoc.getString("est").equals("S")?true:false) ) );
                 vecReg.add(INT_TBL_COEMP, rstLoc.getString("co_emp") );
                 vecReg.add(INT_TBL_COLOC, rstLoc.getString("co_loc") );
                 vecReg.add(INT_TBL_CODTIP, rstLoc.getString("co_tipdoc") );
                 vecReg.add(INT_TBL_CODDOC, rstLoc.getString("co_doc") );
                 vecReg.add(INT_TBL_CODREG, rstLoc.getString("co_reg") );
                 vecReg.add(INT_TBL_DCTDOC, rstLoc.getString("tx_descor") );
                 vecReg.add(INT_TBL_NUMDOC, rstLoc.getString("ne_numdoc") );
                 vecReg.add(INT_TBL_FECDOC, rstLoc.getString("fe_doc") );
                 vecReg.add(INT_TBL_DIACRE, rstLoc.getString("ne_diacre") );
                 vecReg.add(INT_TBL_FECVEN, rstLoc.getString("fe_ven") );
                 
                 bdeAux = new BigDecimal(rstLoc.getObject("nd_porRet") == null? "0" : (rstLoc.getString("nd_porRet")));
                 bdeAux = objUti.redondearBigDecimal(bdeAux, objZafParSis.getDecimalesMostrar());
                 vecReg.add(INT_TBL_POR_RET, "" + bdeAux);
                 
                 vecReg.add(INT_TBL_VALDOC, rstLoc.getString("mo_pag") );
                 vecReg.add(INT_TBL_VALABO, rstLoc.getString("nd_abo") );
                 vecReg.add(INT_TBL_VALPEN, rstLoc.getString("valpen") );
                 vecReg.add(INT_TBL_VALAPL, rstLoc.getString("valapl")  );
                 
                 vecReg.add(INT_TBL_NUMCHQ, rstLoc.getString("tx_numchq") );
                 vecReg.add(INT_TBL_MONCHQ, rstLoc.getString("nd_monchq")  );

                 dlbValTot +=rstLoc.getDouble("valapl");

               vecData.add(vecReg);
       

           }
           rstLoc.close();
           rstLoc=null;

           objTblMod.setData(vecData);
           tblDat .setModel(objTblMod);

           dlbValTot =  objUti.redondear(dlbValTot, 2 );
           txtValTotFac.setText(""+dlbValTot);

        lblMsgSis.setText("Listo");
        pgrSis.setValue(0);
        pgrSis.setIndeterminate(false);

      stmLoc.close();
      stmLoc=null;
      conn.close();
      conn=null;

   }}catch(java.sql.SQLException Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
     catch(Exception Evt) { blnRes=false;  objUti.mostrarMsgErr_F1(this, Evt);  }
    System.gc();
    return blnRes;
}





    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanel1 = new javax.swing.JPanel();
      lblTit = new javax.swing.JLabel();
      jPanel2 = new javax.swing.JPanel();
      jTabbedPane1 = new javax.swing.JTabbedPane();
      jPanel4 = new javax.swing.JPanel();
      jScrollPane1 = new javax.swing.JScrollPane();
      tblDat = new javax.swing.JTable();
      jPanel6 = new javax.swing.JPanel();
      jPanel7 = new javax.swing.JPanel();
      txtValTotFac = new javax.swing.JTextField();
      jPanel3 = new javax.swing.JPanel();
      jPanel5 = new javax.swing.JPanel();
      butAce = new javax.swing.JButton();
      jButton2 = new javax.swing.JButton();
      panBarEst = new javax.swing.JPanel();
      lblMsgSis = new javax.swing.JLabel();
      panPrgSis = new javax.swing.JPanel();
      pgrSis = new javax.swing.JProgressBar();

      setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
      addWindowListener(new java.awt.event.WindowAdapter() {
         public void windowClosing(java.awt.event.WindowEvent evt) {
            formWindowClosing(evt);
         }
         public void windowOpened(java.awt.event.WindowEvent evt) {
            formWindowOpened(evt);
         }
      });

      lblTit.setText("jLabel1");
      jPanel1.add(lblTit);

      getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

      jPanel2.setLayout(new java.awt.BorderLayout());

      jPanel4.setLayout(new java.awt.BorderLayout());

      tblDat.setModel(new javax.swing.table.DefaultTableModel(
         new Object [][] {
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null},
            {null, null, null, null}
         },
         new String [] {
            "Title 1", "Title 2", "Title 3", "Title 4"
         }
      ));
      jScrollPane1.setViewportView(tblDat);

      jPanel4.add(jScrollPane1, java.awt.BorderLayout.CENTER);

      jPanel6.setLayout(new java.awt.BorderLayout());

      txtValTotFac.setEditable(false);
      txtValTotFac.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
      txtValTotFac.setText("0.00");
      txtValTotFac.setPreferredSize(new java.awt.Dimension(80, 20));
      jPanel7.add(txtValTotFac);

      jPanel6.add(jPanel7, java.awt.BorderLayout.EAST);

      jPanel4.add(jPanel6, java.awt.BorderLayout.SOUTH);

      jTabbedPane1.addTab("General", jPanel4);

      jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

      getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

      jPanel3.setLayout(new java.awt.BorderLayout());

      butAce.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      butAce.setText("Aceptar");
      butAce.setPreferredSize(new java.awt.Dimension(90, 23));
      butAce.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            butAceActionPerformed(evt);
         }
      });
      jPanel5.add(butAce);

      jButton2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
      jButton2.setText("Cancelar");
      jButton2.setPreferredSize(new java.awt.Dimension(90, 23));
      jButton2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
         }
      });
      jPanel5.add(jButton2);

      jPanel3.add(jPanel5, java.awt.BorderLayout.EAST);

      panBarEst.setPreferredSize(new java.awt.Dimension(320, 19));
      panBarEst.setLayout(new java.awt.BorderLayout());

      lblMsgSis.setText("Listo");
      lblMsgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      panBarEst.add(lblMsgSis, java.awt.BorderLayout.CENTER);

      panPrgSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      panPrgSis.setMinimumSize(new java.awt.Dimension(24, 26));
      panPrgSis.setPreferredSize(new java.awt.Dimension(200, 15));
      panPrgSis.setLayout(new java.awt.BorderLayout(2, 2));

      pgrSis.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));
      pgrSis.setBorderPainted(false);
      pgrSis.setDebugGraphicsOptions(javax.swing.DebugGraphics.NONE_OPTION);
      pgrSis.setPreferredSize(new java.awt.Dimension(100, 16));
      panPrgSis.add(pgrSis, java.awt.BorderLayout.CENTER);

      panBarEst.add(panPrgSis, java.awt.BorderLayout.EAST);

      jPanel3.add(panBarEst, java.awt.BorderLayout.SOUTH);

      getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

      java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
      setBounds((screenSize.width-686)/2, (screenSize.height-400)/2, 686, 400);
   }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:

      
        cargarDat();

    }//GEN-LAST:event_formWindowOpened

    private void butAceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_butAceActionPerformed
        // TODO add your handling code here:


     if( (objUti.redondearBigDecimal( txtValTotFac.getText(), 2)).compareTo(dblMonChq)>0 ){
       this.MensajeInf("HA SOBREPASADO EL VALOR DEL CHEQUE CORRIGA E INTENTELO DE NUEVO ");

     }else{


       strFacSel="";
       strNumDocSel="";
       
       for(int i=0; i<tblDat.getRowCount(); i++){

         if(tblDat.getValueAt(i, INT_TBL_CHK).toString().equals("true")){

            if(!strFacSel.equals("")) strFacSel+=" UNION ALL ";
                            
            strFacSel+="SELECT "+tblDat.getValueAt(i, INT_TBL_COEMP).toString()+" as coemp,"+tblDat.getValueAt(i, INT_TBL_COLOC).toString()+" as coloc,"+
            tblDat.getValueAt(i, INT_TBL_CODTIP).toString()+" as cotipdoc,"+tblDat.getValueAt(i, INT_TBL_CODDOC).toString()+" as codoc," +
            ""+tblDat.getValueAt(i, INT_TBL_CODREG).toString()+" as coreg, "+tblDat.getValueAt(i, INT_TBL_VALAPL).toString()+" as valapl "+
            " ,"+tblDat.getValueAt(i, INT_TBL_VALPEN).toString()+" as valpen ";

            if(!strNumDocSel.equals("")) strNumDocSel+=",";
            strNumDocSel+=tblDat.getValueAt(i, INT_TBL_NUMDOC).toString();
         }
       }

       
        
         Str_RegSel[0] = strFacSel;
         Str_RegSel[1] = strNumDocSel;
         Str_RegSel[2] = txtValTotFac.getText();


         blnAcepta = true;
         blnEst=true;
         
         dispose();
     }
        
}//GEN-LAST:event_butAceActionPerformed


    public String GetCamSel(int Idx){
        if(!(Str_RegSel==null)){
            if(Idx <= 0 || Idx > Str_RegSel.length)
                return "El parametro debe ser entre 1 y " + Integer.toString(Str_RegSel.length) ;
            else
                return Str_RegSel[Idx-1];
        }else{
            return "";
        }

    }



    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        exitForm();
    }//GEN-LAST:event_formWindowClosing

    /**
     * Para salir de la pantalla en donde estamos y pide confirmacion de salidad.
     */
    private void exitForm(){

        String strTit, strMsg;
        javax.swing.JOptionPane oppMsg=new javax.swing.JOptionPane();
        strTit="Mensaje del sistema Zafiro";
        strMsg="¿Está seguro que desea cerrar este programa?";
        if (oppMsg.showConfirmDialog(this,strMsg,strTit,javax.swing.JOptionPane.YES_NO_OPTION,javax.swing.JOptionPane.QUESTION_MESSAGE)==javax.swing.JOptionPane.YES_OPTION)
        {
            dispose();
        }

    }

/**
 * Mensaje que presenta el sistema
 * @param strMensaje recibe el mensaje a mostrar
 */
       private void MensajeInf(String strMensaje){
        javax.swing.JOptionPane obj =new javax.swing.JOptionPane();
        String strTit;
        strTit="Mensaje del sistema Zafiro";
        obj.showMessageDialog(this,strMensaje,strTit,javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }
    












   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton butAce;
   private javax.swing.JButton jButton2;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private javax.swing.JPanel jPanel3;
   private javax.swing.JPanel jPanel4;
   private javax.swing.JPanel jPanel5;
   private javax.swing.JPanel jPanel6;
   private javax.swing.JPanel jPanel7;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JTabbedPane jTabbedPane1;
   private javax.swing.JLabel lblMsgSis;
   private javax.swing.JLabel lblTit;
   private javax.swing.JPanel panBarEst;
   private javax.swing.JPanel panPrgSis;
   private javax.swing.JProgressBar pgrSis;
   private javax.swing.JTable tblDat;
   private javax.swing.JTextField txtValTotFac;
   // End of variables declaration//GEN-END:variables


private class ZafMouMotAda extends java.awt.event.MouseMotionAdapter{
 public void mouseMoved(java.awt.event.MouseEvent evt){
    int intCol=tblDat.columnAtPoint(evt.getPoint());
    String strMsg="";
    switch (intCol){
        case INT_TBL_LIN:
            strMsg="";
            break;
        case INT_TBL_CHK:
            strMsg="Selección.";
            break;
     
       

        default:
            strMsg="";
            break;
    }
    tblDat.getTableHeader().setToolTipText(strMsg);
}
}


     protected void finalize() throws Throwable
    {   System.gc();
        super.finalize();
    }




}
